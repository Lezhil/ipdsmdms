package com.bcits.mdas.mqtt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.entity.AmrDailyLoadEntity;
import com.bcits.mdas.entity.AmrDailyLoadEntity.DailyKeyLoad;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrEventsEntity.KeyEvent;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.entity.InitialMeterInfo;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.entity.ModemDiagnosisEntity;
import com.bcits.mdas.entity.PowerOnOffDetailsEntity;
import com.bcits.mdas.entity.PowerOnOffEntity;
import com.bcits.mdas.service.AmiInstantaneousService;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrDailyLoadService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.InitialMeterInfoService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.ModemDiagnosisService;
import com.bcits.mdas.service.PowerOnOffDetailsService;
import com.bcits.mdas.service.PowerOnOffService;
import com.bcits.mdas.utility.FilterUnit;

@Controller
public class ParseController {

	private FilterConfig filterConfig;

	@Autowired
	private AmrBillsService amrBillsService;

	@Autowired
	private AmrEventsService amrEventsService;

	@Autowired
	private AmrInstantaneousService instantaneousService;
	@Autowired
	private AmrLoadService amrLoadService;

	@Autowired
	private ModemDiagnosisService modemDiag;

	@Autowired
	private ModemCommunicationService modemCommunicationService;

	@Autowired
	private AmiInstantaneousService amiInstantaneousService;

	@Autowired
	private MasterMainService mainService;

	@Autowired
	private AmrDailyLoadService amrDailyLoadService;

	@Autowired
	private InitialMeterInfoService initialMeterInfoService;
	
	@Autowired
	private PowerOnOffService powerOnOffService;
	
	@Autowired
	private PowerOnOffDetailsService powerOnOffDetailsService;
	
	public static long instaCount=0, loadCount=0, billCount=0, eventCount=0, dailyLoadCount=0,initalMeterCount=0, powerOnOffCount=0;
	
	

	@RequestMapping(value = "/getPacketInfo", method = { RequestMethod.GET })
	public @ResponseBody Object getPacketInfo() {
		 
			return "instaCount="+instaCount+", loadCount="+loadCount+", billCount="+billCount+", eventCount="+eventCount+", dailyLoadCount="+dailyLoadCount+",initalMeterCount="+initalMeterCount+", powerOnOffCount="+powerOnOffCount;
	}
	
	
	@RequestMapping(value = "/dataParserUpdateBills", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateBills(@RequestBody String js) {
		billCount++;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
		try {
			JSONArray recs = new JSONArray(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("billjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				try {
//					executor.execute(new BillUpdateThread(obj.toString()));
					Future<?> taskStatus = executor.submit(new BillUpdateThread(obj.toString()));
				} catch (Exception e) {
					e.getMessage();
				}
			}
//			executor.shutdown();
			return "success";
		} catch (Exception e) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("billjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}
		finally {
			executor.shutdown();
		}
	}
	



	//@Async
	@RequestMapping(value = "/dataParserUpdateEvent", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateEvent(@RequestBody String js) {
	//	System.out.println("------***********-------Inside Update Event Webservice------***********-------");
		eventCount++;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
		try {
			JSONArray recs = new JSONArray(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("eventjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println("Json Length count= " + recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				try {
//					executor.execute(new EventUpdateThread(obj.toString()));
					Future<?> taskStatus = executor.submit(new EventUpdateThread(obj.toString()));
				} catch (Exception e) {
					e.getMessage();
				}
			}
//			executor.shutdown();
			return "success";
		} catch (Exception e) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("eventjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}finally {
			executor.shutdown();
		}

	}
	

	

	@RequestMapping(value = "/dataParserUpdateInstantaneous", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateInstantaneous(@RequestBody String js) {
		instaCount++;	
		try {
			JSONArray recs = new JSONArray(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("instjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				try {
					
					String TransID = obj.optString("TransID");
					String ClientID = obj.optString("ClientID");
					String MeterNumber = obj.optString("MeterNumber");
					String ReadTime = obj.optString("ReadTime");
					String Ir = obj.optString("Ir");
					String Iy = obj.optString("Iy");
					String Ib = obj.optString("Ib");
					String Vrn = obj.optString("Vrn");
					String Vyn = obj.optString("Vyn");
					String Vbn = obj.optString("Vbn");
					String PFr = obj.optString("PFr");
					String PFy = obj.optString("PFy");
					String PFb = obj.optString("PFb");
					String ThreePhasePF = obj.optString("ThreePhasePF");
					String Frequency = obj.optString("Frequency");
					String KVA = obj.optString("KVA");
					String Power_KW = obj.optString("Power_KW");
					String KVAR = obj.optString("KVAR");
					String KWH_Imp = obj.optString("KWH_Imp");
					String KWH_Exp = obj.optString("KWH_Exp");
					String KVARH_Q1 = obj.optString("KVARH_Q1");
					
					String KVARH_Q2 = obj.optString("KVARH_Q2"); //Not used
					String KVARH_Q3 = obj.optString("KVARH_Q3"); //Not used
									
					String KVARH_Q4 = obj.optString("KVARH_Q4");
					String KVAH = obj.optString("KVAH");
					String KVAH_Exp = obj.optString("KVAH_Exp");
					String MDKW = obj.optString("MDKW");
					String Date_MDKW = obj.optString("Date_MDKW");
					String MDKVA = obj.optString("MDKVA");
					String Date_MDKVA = obj.optString("Date_MDKVA");
					
					String MDKW_Exp = obj.optString("MDKW_Exp"); //Not used
					String Date_MDKW_Exp = obj.optString("Date_MDKW_Exp"); //Not used
					String MDKVA_Exp = obj.optString("MDKVA_Exp"); //Not used
					String Date_MDKVA_Exp = obj.optString("Date_MDKVA_Exp"); //Not used
					
					String VoltAngRY = obj.optString("VoltAngRY");
					String VoltAngRB = obj.optString("VoltAngRB");
					String VoltAngYB = obj.optString("VoltAngYB");	
					String neutral_current = obj.optString("Neutral_Current");	
					String ModemTime = obj.optString("ModemTime");
					
					//Still Not Sending Below Keys
					String PowerOffCount = obj.optString("PowerOffCount");
					String PowerOffDuration = obj.optString("PowerOffDuration");
					String TamperCount = obj.optString("TamperCount");
					String MDResetCount = obj.optString("MDResetCount");
					String ProgrammCount = obj.optString("ProgrammCount");					
					String Imei = obj.optString("Imei");
					String MDResetDate = obj.optString("MDResetDate");				
					String date_Last_Bill = obj.optString("Date_Last_Bill");
					
					
					if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
						try {
							MasterMainEntity m = mainService.getFeederData(MeterNumber).get(0);
							if (m != null) {
								Imei = m.getModem_sl_no();
							}
						} catch (Exception e) {
							e.getMessage();
						}
					}
					
//					String iRAngle = obj.optString("iRAngle");
//					String iYAngle = obj.optString("iYAngle");
//					String iBAngle = obj.optString("iBAngle");
//					String KWH = obj.optString("KWH");
//					String KVAH_Imp = obj.optString("KVAH_Imp");
//					String KVARH_Lag = obj.optString("KVARH_Lag");
//					String KVARH_Lead = obj.optString("KVARH_Lead");
//					String Vry = obj.optString("Vry");
//					String Vby = obj.optString("Vby");
//					String Phase_Sequence = obj.optString("Phase_Sequence");
					
					AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
					entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(ReadTime)));
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setDateMdKva(getString(Date_MDKVA));
					entity.setDateMdKw(getString(Date_MDKW));
					entity.setFrequency(getDouble(Frequency));
					entity.setiB(getDouble(Ib));
					entity.setImei(getString(Imei));
					entity.setiR(getDouble(Ir));
					entity.setiY(getDouble(Iy));
					entity.setKva(getDouble(KVA));
					entity.setKvah(getDouble(KVAH));
					entity.setKvahExp(getDouble(KVAH_Exp));
					entity.setKvar(getDouble(KVAR));
					entity.setKwhExp(getDouble(KWH_Exp));
					entity.setKwhImp(getDouble(KWH_Imp));
					entity.setMdKva(getDouble(MDKVA));
					entity.setMdKw(getDouble(MDKW));
					entity.setMdResetCount(getInteger(MDResetCount));
					entity.setMdResetDate(getString(MDResetDate));
					entity.setModemTime(getTimeStamp(ModemTime));
					entity.setPfB(getDouble(PFb));
					entity.setPfR(getDouble(PFr));
					entity.setPfThreephase(getDouble(ThreePhasePF));
					entity.setPfY(getDouble(PFy));
					entity.setPowerKw(getDouble(Power_KW));
					entity.setPowerOffCount(getInteger(PowerOffCount));
					entity.setPowerOffDuration(getInteger(PowerOffDuration));
					entity.setProgrammingCount(getInteger(ProgrammCount));
					entity.setReadTime(getTimeStamp(ReadTime));
					entity.setTamperCount(getInteger(TamperCount));
					entity.setTransId(getString(TransID));
					entity.setvB(getDouble(Vbn));
					entity.setvR(getDouble(Vrn));
					entity.setvY(getDouble(Vyn));
					entity.setKvahImp(getDouble(KVAH));
					entity.setKvarhLag(getDouble(KVARH_Q1));
					entity.setKvarhLead(getDouble(KVARH_Q4));
					entity.setKwh(getDouble(KWH_Imp));
					
					entity.setV_ry_angle(getInteger(VoltAngRY));
					entity.setV_rb_angle(getInteger(VoltAngRB));
					entity.setV_yb_angle(getInteger(VoltAngYB));
					entity.setNeutralCurrent(neutral_current);
					entity.setBillingDate(getTimeStamp(date_Last_Bill));					
					
//					entity.setiBAngle(getDouble(iBAngle));
//					entity.setiRAngle(getDouble(iRAngle));
//					entity.setiYAngle(getDouble(iYAngle));
					

					if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
//						 System.out.println("UPDATED INSTATNTANEOUS=========== ");
					}

					try {
						updateCommunication(Imei, MeterNumber, "instjs");
						updateMasterMain(Imei, MeterNumber);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				} catch (Exception e) {
					e.getMessage();
				}
			}
			return "success";
		} catch (Exception e) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("instjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}

	}
	

//	@Async
	@RequestMapping(value = "/dataParserUpdateInstant", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateInstantaneous_Genus(@RequestBody String js) {
		instaCount++;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
		try {
			JSONArray recs = new JSONArray(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("instjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				try {
//					 new Thread(new InstantUpdate(obj.toString())).start();
//					executor.execute(new InstantUpdate(obj.toString()));
					
//					Future<?> taskStatus = executor.submit(() -> {
//						  //System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
//						  new InstantUpdate(obj.toString()).run();
//					});
					
					Future<?> taskStatus = executor.submit(new InstantUpdate(obj.toString()));
					
				} catch (Exception e) {
					e.getMessage();
				}
			}
			
			return "success";
		} catch (Exception e) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("instjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		} finally {
			executor.shutdown();
		}

	}
	
	

	
//	@Async
	@RequestMapping(value = "/dataParserUpdateLoad", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateLoad(@RequestBody String js) {
		loadCount++;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
		try {
			JSONArray recs = new JSONArray(js.toString());
			try {			
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	          //  DateFormat dateFormatDate = new SimpleDateFormat("dd_MM_yyyy");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	           // String dt_date = dateFormatDate.format(date);
				writeTrace("loadjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				try {
//					executor.execute(new LoadUpdateThread(obj.toString()));
					Future<?> taskStatus = executor.submit(new LoadUpdateThread(obj.toString()));
				} catch (Exception e) {
					e.getMessage();
				}
			}
//			executor.shutdown();
//			try {
//				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//				} catch (InterruptedException e) {
//				  e.printStackTrace();
//			}
			return "success";
			
		} catch (Exception e) {		
			try {			
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("loadjs"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}finally {
			executor.shutdown();
		}

	}
	
	

	
	
	
	//@Async
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/syncInitialMeterInformation", method = { RequestMethod.POST })
	public @ResponseBody Object syncInitialMeterInfromation(@RequestBody String js) {
		//System.out.println("------***********-------Inside Update Diag Webservice------***********-------");

		initalMeterCount++;
		String status = "";
		try {

			JSONObject main = new JSONObject(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("meterInfo"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String clientid = main.optString("ClientID");
			String datatype = main.optString("DataType");
			String data = main.optString("Data");

			JSONArray recs = new JSONArray(data);
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);

				String region = obj.optString("Region");
				String regioncode = obj.optString("RegionCode");
				String circle = obj.optString("Circle");
				String circlecode = obj.optString("CircleCode");
				String subdivision = obj.optString("Subdivision");
				String subdivisioncode = obj.optString("SubdivisionCode");
				String section = obj.optString("Section");
				String sectioncode = obj.optString("SectionCode");
				String substation = obj.optString("Substation");
				String substationcode = obj.optString("SubstationCode");
				String meterid = obj.optString("MeterId");
				String metertype = obj.optString("MeterType");
				String meteripaddress = obj.optString("MeterIPAddress");
				String externalctratio = obj.optString("ExternalCtRatio");
				String firmwareversion = obj.optString("FirmwareVersion");
				String simid = obj.optString("SimId");
				String ctwc = obj.optString("CTWC");
				String wire = obj.optString("Wire");
				String meterclass = obj.optString("Class");
				String currentrating = obj.optString("CurrentRating");
				String voltagerating = obj.optString("VoltageRating");
				String communicationmedium = obj.optString("CommunicationMedium");
				String operatingmode = obj.optString("OperatingMode");
				String manufacturername = obj.optString("ManufacturerName");
				String meterprotocol = obj.optString("MeterProtocol");
				String metercommunicationmethodology = obj.optString("MeterCommunicationMethodology");
				String isnetmeteringactive = obj.optString("IsNetMeteringActive");
				String communicationmodulefirmwareversion = obj.optString("CommunicationModuleFirmwareVersion");
				String meterconstant = obj.optString("MeterConstant");
				String communicationmode = obj.optString("CommunicationMode");
				String townname = obj.optString("TownName");
				String towncode = obj.optString("TownCode");
				String manufacturerid = obj.optString("ManufacturerId");
				String manufactureyear = obj.optString("ManufactureYear");
				String mf = obj.optString("MF");
				String lattitude = obj.optString("Lattitude");
				String longitude = obj.optString("Longitude");
				String metermodel = obj.optString("MeterModel");
				String capacity = obj.optString("Capacity");
				String ptratio = obj.optString("PTRatio");
				String metercommissioning = obj.optString("MeterCommissioning");
				String ipperiod = obj.optString("IpPeriod");
				String ct_ratio = obj.optString("CTRatio");
				String type = obj.optString("Type");
				String boundaryname = obj.optString("BoundaryName");
				String boundaryid = obj.optString("BoundaryID");
				String boundarytype = obj.optString("BoundaryType");
				String feedercode = obj.optString("FeederCode");
				String feedername = obj.optString("FeederName");
				String dtcode = obj.optString("DTCode");
				String dtname = obj.optString("DTName");
				String installationdate = obj.optString("InstallationDate");
				String division = obj.optString("Division");
				String division_code = obj.optString("DivisionCode");
				String meterdigit = (obj.optString("MeterDigit").isEmpty() || obj.optString("MeterDigit") == null ? "8"
						: obj.optString("MeterDigit"));
				String feeder_type = obj.optString("FeederType");//FeederType=  IPDS/Non IPDS

				InitialMeterInfo info = new InitialMeterInfo();
				info.setClientid(clientid);
				info.setData_type(datatype);

				if ("MasterInfo".equals(datatype)) {
					info.setRegion(region);
					info.setRegioncode(regioncode);

					info.setDivision(division);
					info.setDivision_code(division_code);

					info.setCircle(circle);
					info.setCirclecode(circlecode);
					info.setSubdivision(subdivision);
					info.setSubdivisioncode(subdivisioncode);
					info.setSection(section);
					info.setSectioncode(sectioncode);
					info.setSubstation(substation);
					info.setSubstationcode(substationcode);
					info.setTownname(townname);
					info.setTowncode(towncode);
					info.setManufacturerid(manufacturerid);
					info.setManufactureyear(manufactureyear);
					info.setMf(mf);
					info.setLattitude(lattitude);
					info.setLongitude(longitude);
					info.setMetermodel(metermodel);
					info.setCapacity(capacity);
					info.setPtratio(ptratio);
					info.setMetercommissioning(metercommissioning);
					info.setIpperiod(ipperiod);
					info.setCt_ratio(ct_ratio);
					info.setType(type);
					info.setBoundaryname(boundaryname);
					info.setBoundaryid(boundaryid);
					info.setBoundarytype(boundarytype);
					info.setFeedercode(feedercode);
					info.setFeedername(feedername);
					info.setDtcode(dtcode);
					info.setDtname(dtname);
					info.setInstallationdate(getTimeStamp(installationdate));
					info.setMeterdigit(Integer.parseInt(meterdigit));
					info.setFeeder_type(feeder_type);
					if(clientid.equalsIgnoreCase("2")) {
						info.setSim_ipaddress(getString(obj.optString("SIM_IPAddress")));
						info.setSim_mobile_no(getString(obj.optString("SIM_Mobile_No")));			
						info.setImei_no(getString(obj.optString("IMEI_No")));
						
						info.setMeter_purchase_details(getString(obj.optString("Meter_Purchase_OrderNo_And_Date")));
						info.setDt_kva_rating(getString(obj.optString("DT_KVA_Rating")));
						info.setDt_hv_voltage(getString(obj.optString("DT_HV_Voltage")));
						info.setDt_lv_voltage(getString(obj.optString("DT_LV_Voltage")));
						info.setDt_hv_amperes(getString(obj.optString("DT_HV_Amperes")));
						info.setDt_lv_amperes(getString(obj.optString("DT_LV_Amperes")));
						info.setDt_freq(getString(obj.optString("DT_Freq")));
						info.setDt_serial_no(getString(obj.optString("DT_Serial_No")));
						info.setDt_make(getString(obj.optString("DT_Make")));
						info.setDt_manufactured_monthyear(getString(obj.optString("DT_Manufactured_MonthYear")));
						info.setDt_purchase_details(getString(obj.optString("DT_Purchase_OrderNo_And_Date")));
						info.setDt_specification_std(getString(obj.optString("DT_Specification_STD")));

						
					}
				}

				info.setMeterid(meterid);
				info.setMetertype(metertype);
				info.setMeteripaddress(meteripaddress);
				info.setExternalctratio(externalctratio);
				info.setFirmwareversion(firmwareversion);
				info.setSimid(simid);
				info.setCtwc(ctwc);
				info.setWire(wire);
				info.setMeterclass(meterclass);
				info.setCurrentrating(currentrating);
				info.setVoltagerating(voltagerating);
				info.setCommunicationmedium(communicationmedium);
				info.setOperatingmode(operatingmode);
				info.setManufacturername(manufacturername);
				info.setMeterprotocol(meterprotocol);
				info.setMetercommunicationmethodology(metercommunicationmethodology);
				info.setIsnetmeteringactive(isnetmeteringactive);
				info.setCommunicationmodulefirmwareversion(communicationmodulefirmwareversion);
				info.setMeterconstant(meterconstant);
				info.setCommunicationmode(communicationmode);

				info.setSync_status(0);
				info.setTime_stamp(new Date());
				
				
				
				try {
					if (initialMeterInfoService.update(info) instanceof InitialMeterInfo) {
						status = "success";
					}
				} catch (Exception e) {
					try {			
						DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			            Date date = new Date();
			            String dt = dateFormat.format(date);
			            writeErrorTrace("meterInfo"+"_"+dt.replace(":", "_"), js);
					} catch (Exception ee) {
						ee.printStackTrace();
					}
					e.printStackTrace();
					status = "failed";
				}
				
//				try {
//					initialMeterInfoService.save(info);
//					status = "success";
//				} catch (Exception e) {
//					e.printStackTrace();
//					status = "failed";
//
//				}

			}
			return status;

		} catch (Exception e) {
			try {			
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("meterInfo"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}

	}
	
	
	
	
//	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/powerOnOffService", method = { RequestMethod.POST })
	public @ResponseBody Object powerOnOffService(@RequestBody String js) {
		//System.out.println("------***********-------Inside Update Diag Webservice------***********-------");
		powerOnOffCount++;
		String failedFeederCode="";
		try {
			writeTrace("powerOnOff", js);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = "";
		try {

			JSONObject main = new JSONObject(js.toString());
			String clientid = main.optString("ClientID");
			String transID = main.optString("TransID");
			String data = main.optString("FeederDetails");

			JSONArray recs = new JSONArray(data);
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);

				String region = obj.optString("Region");
				String regioncode = obj.optString("RegionCode");
				String circle = obj.optString("Circle");
				String circlecode = obj.optString("CircleCode");
				String townname = obj.optString("Town");
				String towncode = obj.optString("TownCode");
				String substation = obj.optString("Substation");
				String substationcode = obj.optString("SubstationCode");
				
				String dcuNumber = obj.optString("DCUNumber");
				String dcuCode = obj.optString("DCUCode");
				String feedercode = obj.optString("FeederCode");
				String feedername = obj.optString("FeederName");
				String feeder_type = obj.optString("FeederType");//FeederType=  IPDS/Non IPDS
				String meterid = obj.optString("MeterId");
				String date = obj.optString("Date");				
				int totalNoOccurenceTdy = Integer.parseInt(obj.optString("TotalOccurenceToday"));
				int totalPowerFailureDuration = Integer.parseInt(obj.optString("TotalPowerFailureDuration"));	
				String individualDetails = obj.optString("IndividualDetails");	
				
//				System.out.println("totalNoOccurenceTdy---"+totalNoOccurenceTdy+" totalPowerFailureDuration---"+totalPowerFailureDuration);

				PowerOnOffEntity info = new PowerOnOffEntity();
				
				info.setClientid(clientid);
				info.setTransId(transID);

				info.setRegion(region);
				info.setRegioncode(regioncode);
				info.setCircle(circle);
				info.setCirclecode(circlecode);
				info.setTown(townname);
				info.setTowncode(towncode);
				info.setSubstation(substation);
				info.setSubstationcode(substationcode);

				info.setDcuno(dcuNumber);
				info.setDcucode(dcuCode);
				info.setFeedercode(feedercode);
				info.setFeedername(feedername);
				info.setFeeder_type(feeder_type);
				info.setMeterid(meterid);
				info.setDate(getDate(date));
				
				info.setTotalNoOccurence(totalNoOccurenceTdy);
				info.setTotalPowerFailureDuration(totalPowerFailureDuration);
				info.setTime_stamp(new Timestamp(System.currentTimeMillis()));
				
				try {
					//powerOnOffService.customsave(info);
					//failedFeederCode+=feedercode+" ";
					failedFeederCode+=feedercode+" ";
					if (powerOnOffService.customSave(info) instanceof PowerOnOffEntity) {
				//	if (info != null) {
						if(info.getTotalNoOccurence()>=1) {
							JSONArray rec = new JSONArray(individualDetails);
							for (int j = 0; j < rec.length(); j++) {
								PowerOnOffDetailsEntity infodetails = new PowerOnOffDetailsEntity();
								JSONObject occObj = rec.getJSONObject(j);
								String occurenceTime = occObj.optString("OccurenceTime");
								String restorationTime = occObj.optString("RestorationTime");
								int duration = Integer.parseInt(occObj.optString("Duration"));
								
								//failedFeederCode+=info.getFeedercode()+" ";
								infodetails.setFkeyid(info.getId());
								infodetails.setFeedercode(info.getFeedercode());
								infodetails.setDate(info.getDate());
								infodetails.setMeterid(info.getMeterid());
								infodetails.setOccurence_time(getTimeStamp(occurenceTime));
								infodetails.setRestoration_time(getTimeStamp(restorationTime));
								infodetails.setTime_stamp(new Timestamp(System.currentTimeMillis()));
								infodetails.setDuration(duration);
								
								try {
									if (powerOnOffDetailsService.customSave(infodetails) instanceof PowerOnOffDetailsEntity) {
										status = "Success";
									}
								}catch(Exception e) {
									status = "Failed";
									e.printStackTrace();
								}
								
							} 
						}else {
							status = "Success";
						}
					}
				} catch (Exception e) {
					
					e.printStackTrace();
					status = "Failed-Unable To Parse- "+failedFeederCode+" Feeder";
				}

			}
			return status;

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}
	

	
	
	@Transactional
	@RequestMapping(value = "/dataParserUpdateMidNightData", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateMidNightData(@RequestBody String js) {
		// System.out.println("------***********-------Inside UpdateMidNightData Webservice------------");
		dailyLoadCount++;
		try {
			String imei = null, meterNumber = null;
			JSONArray recs = new JSONArray(js.toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
				writeTrace("midNightData"+"_"+dt.replace(":", "_"), js);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				String ClientID = obj.optString("ClientID");
				meterNumber = obj.optString("MeterNumber");
				String rtc_date_time = obj.optString("ReadTime");
				String cum_active_import_energy = obj.optString("KWH");
				String cum_active_export_energy = obj.optString("KWH_Exp");
				String cum_apparent_import_energy = obj.optString("KVAH");
				String cum_apparent_export_energy = obj.optString("KVAH_Exp");		
				String cum_reactive_energy5 = obj.optString("KVARH_Q1");
				String cum_reactive_energy6 = obj.optString("KVARH_Q2");
				String cum_reactive_energy7 = obj.optString("KVARH_Q3");
				String cum_reactive_energy8 = obj.optString("KVARH_Q4");
				String kVArh_High = obj.optString("kVArh_High");
				String kVArh_Low = obj.optString("kVArh_Low");
				

				AmrDailyLoadEntity middata = new AmrDailyLoadEntity();
				if (!cum_active_import_energy.equalsIgnoreCase("null")) {
					middata.setCum_active_import_energy(getDouble(cum_active_import_energy));
				}
				if (!cum_active_export_energy.equalsIgnoreCase("null")) {
					middata.setCum_active_export_energy(getDouble(cum_active_export_energy));
				}
				if (!cum_apparent_import_energy.equalsIgnoreCase("null")) {
					middata.setCum_apparent_import_energy(getDouble(cum_apparent_import_energy));
				}
				if (!cum_apparent_export_energy.equalsIgnoreCase("null")) {
					middata.setCum_apparent_export_energy(getDouble(cum_apparent_export_energy));
				}
				if (!cum_reactive_energy5.equalsIgnoreCase("null")) {
					middata.setCum_reactive_energy5(getDouble(cum_reactive_energy5));
				}
				if (!cum_reactive_energy6.equalsIgnoreCase("null")) {
					middata.setCum_reactive_energy6(getDouble(cum_reactive_energy6));
				}
				if (!cum_reactive_energy7.equalsIgnoreCase("null")) {
					middata.setCum_reactive_energy7(getDouble(cum_reactive_energy7));
				}
				if (!cum_reactive_energy8.equalsIgnoreCase("null")) {
					middata.setCum_reactive_energy8(getDouble(cum_reactive_energy8));
				}
				if (!kVArh_High.equalsIgnoreCase("null")) {
					middata.setKvarh_low(getDouble(kVArh_High));
				}
				if (!kVArh_Low.equalsIgnoreCase("null")) {
					middata.setKvrah_high(getDouble(kVArh_Low));
				}
				middata.setFlag(ClientID); // 1 for Analogics , 2 for Genus
				middata.setTimeStamp(new Timestamp(new Date().getTime()));
				middata.setMyKey(new DailyKeyLoad(meterNumber, getTimeStamp(rtc_date_time)));
				try {
					if (amrDailyLoadService.update(middata) instanceof AmrDailyLoadEntity) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
//				try {
//					updateCommunication(imei, meterNumber, "midNightData");
//					updateMasterMain(imei, meterNumber);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

			}
			return "success";
		} catch (Exception e) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	            Date date = new Date();
	            String dt = dateFormat.format(date);
	            writeErrorTrace("midNightData"+"_"+dt.replace(":", "_"), js);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return "failed";
		}

	}


	@RequestMapping(value = "/dataParserUpdateDiagnosis", method = { RequestMethod.POST })
	public @ResponseBody Object UpdateDiagnosis(@RequestBody String js) {
		// System.out.println("------***********-------Inside Update Diag
		// Webservice------***********-------");
		try {

			JSONObject obj = new JSONObject(js.toString());
			String MeterType = obj.optString("MeterType");
			String MeterNumber = obj.optString("MeterNumber");
			String DataType = obj.optString("DataType");
			String DiagType = obj.optString("DiagType");
			String Imei = obj.optString("Imei");

			if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
				try {
					MasterMainEntity m = mainService.getFeederData(MeterNumber).get(0);
					if (m != null) {
						Imei = m.getModem_sl_no();
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}

			String Status = obj.optString("Status");
			String TimeStamp = obj.optString("TimeStamp");

			ModemDiagnosisEntity modem = new ModemDiagnosisEntity();
			modem.setDataType(DataType);
			modem.setDate(new Date());
			modem.setDiagType(DiagType);
			modem.setImei(Imei);
			modem.setMeterNumber(MeterNumber);
			modem.setMeterType(MeterType);
			modem.setStatus(Status);
			modem.setTimeStamp(new Timestamp(new Date().getTime()));
			modem.setTrackedTime(getTimeStamp(TimeStamp));

			if (modemDiag.update(modem) instanceof ModemDiagnosisEntity) {
				return "success";
			}
			return "failed";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	public void updateCommunication(String imei, String meterNumber, String topic) {

		try {
			Timestamp time = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = format.parse(format.format(new Date()));

			ModemCommunication modem = modemCommunicationService.find((new KeyCommunication(meterNumber, today)));

//			 System.err.println(imei+" MODEM "+modem);

			if (null == modem) {
				modem = new ModemCommunication();
				modem.setMyKey(new KeyCommunication(meterNumber, today));
				modem.setImei(imei);
				modem.setTotalCommunication(Long.valueOf(0));
				modem.setTotalBill(Long.valueOf(0));
				modem.setTotalEvent(Long.valueOf(0));
				modem.setTotalInst(Long.valueOf(0));
				modem.setTotalLoad(Long.valueOf(0));
				modem.setTotal_dailyload(Long.valueOf(0));

			}
			modem.setImei(imei);
			modem.setLastCommunication(time);
			modem.setTotalCommunication(modem.getTotalCommunication() + 1);

			switch (topic) {
			case Subscriber.TOPIC_BILL:
				modem.setLastSyncBill(time);
				modem.setTotalBill(modem.getTotalBill() + 1);
				break;
			case Subscriber.TOPIC_EVENT:
				modem.setLastSyncEvent(time);
				modem.setTotalEvent(modem.getTotalEvent() + 1);
				break;
			case Subscriber.TOPIC_INSTANT:
				modem.setLastSyncInst(time);
				modem.setTotalInst(modem.getTotalInst() + 1);
				break;
			case Subscriber.TOPIC_LOAD:
				modem.setLastSyncLoad(time);
				modem.setTotalLoad(modem.getTotalLoad() + 1);
				break;
			case Subscriber.TOPIC_DAILYLOAD:
				modem.setLast_sync_dailyload(time);
				modem.setTotal_dailyload(modem.getTotal_dailyload() + 1);
				break;
			case Subscriber.TOPIC_IDEN:

				break;
			}

			if (modemCommunicationService.update(modem) instanceof ModemCommunication) {
//				 System.out.println("========================UPDATED MODEM COMMUNICATION");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateMasterMain(String imei, String meterNumber) {
		try {

			// MasterMainEntity mainEntity = mainService.getEntityByImeiNoAndMtrNO(imei,
			// meterNumber);
			MasterMainEntity mainEntity = mainService.getEntityByMtrNO(meterNumber);
			if (mainEntity != null) {
				/* mainEntity.setMtrmake(mtrMake); */
				mainEntity.setLast_communicated_date(new Date());
				mainService.update(mainEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Double getDouble(String value) {// GET DOUBLE VALUE WITH MAX 3
		// DECIMALS
		try {
			double number = Math.round(Double.parseDouble(value) * 1000);
			return number / 1000;
		} catch (Exception e) {
			return null;
		}
	}

	Integer getInteger(String value) {// GET DOUBLE VALUE WITH MAX 3
		// DECIMALS
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return null;
		}
	}

	String getString(String string) {
		try {
			string = string.trim();
			if (string.isEmpty() || string.equalsIgnoreCase("null")) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return string;
	}

	Timestamp getTimeStamp(String value) {
		// SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			return new Timestamp(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	Date getDate(String value) {
		// SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/parseJson/{type}/{date}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object parseJson(@PathVariable String type, @PathVariable String date, ModelMap model,
			HttpServletRequest request) {
		System.out.println(type + "-----" + date);
		try {
			String[] dt = date.split("-");

			String yr = dt[0];
			String month = dt[1];
			String date1 = dt[2];

			String yrmnth = yr + month;

//			String fileName = "/usr/local/apache-tomcat-7.0.12/bin/MIP_uploads/LOGS/"+yrmnth+"/"+yrmnth+date1+"/"+type+".txt";
			String fileName = FilterUnit.logFolder + "/" + yrmnth + "/" + yrmnth + date1 + "/" + type + ".txt";
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);

			BufferedReader br = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			int count = 1;
			while ((line = br.readLine()) != null) {
				if ("billjs".equals(type)) {
					list.add(saveBill(line));
				} else if ("loadjs".equals(type)) {
					list.add(saveLoad(line));
				} else if ("instjs".equals(type)) {
					list.add(saveInstantenious(line));
				} else if ("eventjs".equals(type)) {
					list.add(saveEvent(line));
				}
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Map<String, String> saveBill(String bill) {
		Map<String, String> map = new HashMap();
		String mtrno = "";
		try {
			JSONArray recs = new JSONArray(bill);
			String imei = null, meterNumber = null;

			for (int i = 0; i < recs.length(); i++) {

				JSONObject obj = recs.getJSONObject(i);
				String transID = obj.optString("transID");
				String METER_ID = obj.optString("METER_ID");
				meterNumber = obj.optString("meterNumber");
				imei = obj.optString("IMEI");
				mtrno = meterNumber;
				if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
					try {
						MasterMainEntity m = mainService.getFeederData(meterNumber).get(0);
						if (m != null) {
							imei = m.getModem_sl_no();
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}

				String serverTime = obj.optString("serverTime");
				String billingDate = obj.optString("billingDate");
				String sys_PF_Billing = obj.optString("sys_PF_Billing");
				String kWh = obj.optString("kWh");
				String kWh_TZ1 = obj.optString("kWh_TZ1");
				String kWh_TZ2 = obj.optString("kWh_TZ2");
				String kWh_TZ3 = obj.optString("kWh_TZ3");
				String kWh_TZ4 = obj.optString("kWh_TZ4");
				String kWh_TZ5 = obj.optString("kWh_TZ5");
				String kWh_TZ6 = obj.optString("kWh_TZ6");
				String kWh_TZ7 = obj.optString("kWh_TZ7");
				String kWh_TZ8 = obj.optString("kWh_TZ8");
				String kvarhLag = obj.optString("kvarhLag");
				String kvarhLead = obj.optString("kvarhLead");
				String kVAh = obj.optString("kVAh");
				String kVAh_TZ1 = obj.optString("kVAh_TZ1");
				String kVAh_TZ2 = obj.optString("kVAh_TZ2");
				String kVAh_TZ3 = obj.optString("kVAh_TZ3");
				String kVAh_TZ4 = obj.optString("kVAh_TZ4");
				String kVAh_TZ5 = obj.optString("kVAh_TZ5");
				String kVAh_TZ6 = obj.optString("kVAh_TZ6");
				String kVAh_TZ7 = obj.optString("kVAh_TZ7");
				String kVAh_TZ8 = obj.optString("kVAh_TZ8");
				String Demand_kW = obj.optString("Demand_kW");
				String OccDate_kW = obj.optString("OccDate_kW");
				String kW_TZ1 = obj.optString("kW_TZ1");
				String Date_kW_TZ1 = obj.optString("Date_kW_TZ1");
				String kW_TZ2 = obj.optString("kW_TZ2");
				String Date_kW_TZ2 = obj.optString("Date_kW_TZ2");
				String kW_TZ3 = obj.optString("kW_TZ3");
				String Date_kW_TZ3 = obj.optString("Date_kW_TZ3");
				String kW_TZ4 = obj.optString("kW_TZ4");
				String Date_kW_TZ4 = obj.optString("Date_kW_TZ4");
				String kW_TZ5 = obj.optString("kW_TZ5");
				String Date_kW_TZ5 = obj.optString("Date_kW_TZ5");
				String kW_TZ6 = obj.optString("kW_TZ6");
				String Date_kW_TZ6 = obj.optString("Date_kW_TZ6");
				String kW_TZ7 = obj.optString("kW_TZ7");
				String Date_kW_TZ7 = obj.optString("Date_kW_TZ7");
				String kW_TZ8 = obj.optString("kW_TZ8");
				String Date_kW_TZ8 = obj.optString("Date_kW_TZ8");
				String kVA = obj.optString("kVA");
				String Date_kVA = obj.optString("Date_kVA");
				String kVA_TZ1 = obj.optString("kVA_TZ1");
				String Date_kVA_TZ1 = obj.optString("Date_kVA_TZ1");
				String kVA_TZ2 = obj.optString("kVA_TZ2");
				String Date_kVA_TZ2 = obj.optString("Date_kVA_TZ2");
				String kVA_TZ3 = obj.optString("kVA_TZ3");
				String Date_kVA_TZ3 = obj.optString("Date_kVA_TZ3");
				String kVA_TZ4 = obj.optString("kVA_TZ4");
				String Date_kVA_TZ4 = obj.optString("Date_kVA_TZ4");
				String kVA_TZ5 = obj.optString("kVA_TZ5");
				String Date_kVA_TZ5 = obj.optString("Date_kVA_TZ5");
				String kVA_TZ6 = obj.optString("kVA_TZ6");
				String Date_kVA_TZ6 = obj.optString("Date_kVA_TZ6");
				String kVA_TZ7 = obj.optString("kVA_TZ7");
				String Date_kVA_TZ7 = obj.optString("Date_kVA_TZ7");
				String kVA_TZ8 = obj.optString("kVA_TZ8");
				String Date_kVA_TZ8 = obj.optString("Date_kVA_TZ8");

				AmrBillsEntity entity = new AmrBillsEntity();
				entity.setMyKey(new KeyBills(getString(meterNumber), getTimeStamp(billingDate)));
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
				entity.setDateKva(getString(Date_kVA));
				entity.setDateKvaTz1(getString(Date_kVA_TZ1));
				entity.setDateKvaTz2(getString(Date_kVA_TZ2));
				entity.setDateKvaTz3(getString(Date_kVA_TZ3));
				entity.setDateKvaTz4(getString(Date_kVA_TZ4));
				entity.setDateKvaTz5(getString(Date_kVA_TZ5));
				entity.setDateKvaTz6(getString(Date_kVA_TZ6));
				entity.setDateKvaTz7(getString(Date_kVA_TZ7));
				entity.setDateKvaTz8(getString(Date_kVA_TZ8));
				entity.setDateKwTz1(getString(Date_kW_TZ1));
				entity.setDateKwTz2(getString(Date_kW_TZ2));
				entity.setDateKwTz3(getString(Date_kW_TZ3));
				entity.setDateKwTz4(getString(Date_kW_TZ4));
				entity.setDateKwTz5(getString(Date_kW_TZ5));
				entity.setDateKwTz6(getString(Date_kW_TZ6));
				entity.setDateKwTz7(getString(Date_kW_TZ7));
				entity.setDateKwTz8(getString(Date_kW_TZ8));
				entity.setDemandKw(getDouble(Demand_kW));
				entity.setImei(getString(imei));
				entity.setKva(getDouble(kVA));
				entity.setKvah(getDouble(kVAh));
				entity.setKvahTz1(getDouble(kVAh_TZ1));
				entity.setKvahTz2(getDouble(kVAh_TZ2));
				entity.setKvahTz3(getDouble(kVAh_TZ3));
				entity.setKvahTz4(getDouble(kVAh_TZ4));
				entity.setKvahTz5(getDouble(kVAh_TZ5));
				entity.setKvahTz6(getDouble(kVAh_TZ6));
				entity.setKvahTz7(getDouble(kVAh_TZ7));
				entity.setKvahTz8(getDouble(kVAh_TZ8));
				entity.setKvarhLag(getDouble(kvarhLag));
				entity.setKvarhLead(getDouble(kvarhLead));
				entity.setKvaTz1(getDouble(kVA_TZ1));
				entity.setKvaTz2(getDouble(kVA_TZ2));
				entity.setKvaTz3(getDouble(kVA_TZ3));
				entity.setKvaTz4(getDouble(kVA_TZ4));
				entity.setKvaTz5(getDouble(kVA_TZ5));
				entity.setKvaTz6(getDouble(kVA_TZ6));
				entity.setKvaTz7(getDouble(kVA_TZ7));
				entity.setKvaTz8(getDouble(kVA_TZ8));
				entity.setKwh(getDouble(kWh));
				entity.setKwhTz1(getDouble(kWh_TZ1));
				entity.setKwhTz2(getDouble(kWh_TZ2));
				entity.setKwhTz3(getDouble(kWh_TZ3));
				entity.setKwhTz4(getDouble(kWh_TZ4));
				entity.setKwhTz5(getDouble(kWh_TZ5));
				entity.setKwhTz6(getDouble(kWh_TZ6));
				entity.setKwhTz7(getDouble(kWh_TZ7));
				entity.setKwhTz8(getDouble(kWh_TZ8));
				entity.setMeterId(getString(METER_ID));
				entity.setOccDateKw(getString(OccDate_kW));
				entity.setServerTime(getTimeStamp(serverTime));
				entity.setSysPfBilling(getDouble(sys_PF_Billing));
				entity.setTransId(getString(transID));
				entity.setKwTz1(getDouble(kW_TZ1));
				entity.setKwTz2(getDouble(kW_TZ2));
				entity.setKwTz3(getDouble(kW_TZ3));
				entity.setKwTz4(getDouble(kW_TZ4));
				entity.setKwTz5(getDouble(kW_TZ5));
				entity.setKwTz6(getDouble(kW_TZ6));
				entity.setKwTz7(getDouble(kW_TZ7));
				entity.setKwTz8(getDouble(kW_TZ8));

				if (amrBillsService.update(entity) instanceof AmrBillsEntity) {

				}

				/*
				 * ObjectMapper om=new ObjectMapper();
				 * System.out.println(om.writeValueAsString(entity));
				 */

			}

			// if (imei != null) {
			updateCommunication(imei, meterNumber, "billjs");
			// }

			// if (imei != null) {
			updateMasterMain(imei, meterNumber);
			// }

			map.put(mtrno, "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(mtrno, "failed");
			return map;
		}
	}

	public Map<String, String> saveLoad(String load) {
		Map<String, String> map = new HashMap();
		String mtrno = "";
		try {
			String imei = null, meterNumber = null;
			JSONArray recs = new JSONArray(load);
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				// System.out.println("==================================== PARSE
				// LOADSURVEY"+i);
				String transID = obj.optString("transID");
				String structureSize = obj.optString("structureSize");
				String readTime = obj.optString("TimeStamp");
				String KWH_Exp = obj.optString("KWH_Exp");
				String KWH_Imp = obj.optString("KWH_Imp");
				String ModemTime = obj.optString("ModemTime");
				meterNumber = obj.optString("MeterNumber");
				imei = obj.optString("Imei");
				mtrno = meterNumber;
				if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
					try {
						MasterMainEntity m = mainService.getFeederData(meterNumber).get(0);
						if (m != null) {
							imei = m.getModem_sl_no();
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}

				String Ir = obj.optString("Ir");
				String Iy = obj.optString("Iy");
				String Ib = obj.optString("Ib");
				String Vr = obj.optString("Vr");
				String Vy = obj.optString("Vy");
				String Vb = obj.optString("Vb");
				String KWH = obj.optString("KWH");
				String KVAH = obj.optString("KVAH");
				String KVARH_Q1 = obj.optString("KVARH_Q1");
				String KVARH_Q3 = obj.optString("KVARH_Q3");
				String KVARH_Q4 = obj.optString("KVARH_Q4");
				String KVARH_Q2 = obj.optString("KVARH_Q2");
				String KVARH_Lag = obj.optString("KVARH_Lag");
				String KVARH_Lead = obj.optString("KVARH_Lead");
				String Frequnecy = obj.optString("Frequnecy");
				String NetKWH = obj.optString("NetKWH");

				AmrLoadEntity entity = new AmrLoadEntity();
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
				entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(readTime)));
				entity.setFrequency(getDouble(Frequnecy));
				entity.setiB(getDouble(Ib));
				entity.setImei(getString(imei));
				entity.setiR(getDouble(Ir));
				entity.setiY(getDouble(Iy));
				entity.setKvah(getDouble(KVAH));
				entity.setKvarhLag(getDouble(KVARH_Lag));
				entity.setKvarhLead(getDouble(KVARH_Lead));
				entity.setKvarhQ1(getDouble(KVARH_Q1));
				entity.setKvarhQ2(getDouble(KVARH_Q2));
				entity.setKvarhQ3(getDouble(KVARH_Q3));
				entity.setKvarhQ4(getDouble(KVARH_Q4));
				entity.setKwh(getDouble(KWH));
				entity.setKwhExp(getDouble(KWH_Exp));
				entity.setKwhImp(getDouble(KWH_Imp));
				// entity.setModemTime(getTimeStamp(ModemTime));
				try {
					entity.setModemTime(getTimeStamp(ModemTime));
				} catch (Exception e) {
					e.getMessage();
					entity.setModemTime(new Timestamp(new Date().getTime()));
				}

				entity.setNetKwh(getDouble(NetKWH));
				entity.setStructureSize(getInteger(structureSize));
				entity.setTransId(getString(transID));
				entity.setvB(getDouble(Vb));
				entity.setvR(getDouble(Vr));
				entity.setvY(getDouble(Vy));

				if (amrLoadService.update(entity) instanceof AmrLoadEntity) {
					// System.out.println("========================INSERTED LOAD");
				}

				/*
				 * ObjectMapper om=new ObjectMapper();
				 * System.out.println(om.writeValueAsString(entity));
				 */

			}

			// if (imei != null) {
			updateCommunication(imei, meterNumber, "loadjs");
			updateMasterMain(imei, meterNumber);
			// }
			map.put(mtrno, "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(mtrno, "failed");
			return map;
		}
	}

	public Map<String, String> saveEvent(String event) {
		Map<String, String> map = new HashMap();
		String mtrno = "";
		// System.out.println("------***********-------Inside Update Event
		// Webservice------***********-------");
		// System.out.println(js);
		try {
			String imei = null, meterNumber = null;
			JSONArray recs = new JSONArray(event);
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);

				imei = obj.optString("Imei");

				if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
					try {
						MasterMainEntity m = mainService.getFeederData(meterNumber).get(0);
						if (m != null) {
							imei = m.getModem_sl_no();
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}

				String eventTime = obj.optString("TimeStamp");
				String ModemTime = obj.optString("ModemTime");
				String EventCode = obj.optString("EventCode");
				String Ir = obj.optString("Ir");
				String Iy = obj.optString("Iy");
				String Ib = obj.optString("Ib");
				String Vr = obj.optString("Vr");
				String Vy = obj.optString("Vy");
				String Vb = obj.optString("Vb");
				String PFr = obj.optString("PFr");
				String PFy = obj.optString("PFy");
				String PFb = obj.optString("PFb");
				String Energy_KWH = obj.optString("Energy_KWH");
				String Energy_KWH_Import = obj.optString("Energy_KWH_Import");
				String Energy_KWH_Export = obj.optString("Energy_KWH_Export");
				String Energy_KVAH = obj.optString("Energy_KVAH");
				String TransID = obj.optString("TransID");
				String StructureSize = obj.optString("StructureSize");
				String KWH_Export = obj.optString("KWH_Export");
				String KWH_Import = obj.optString("KWH_Import");
				meterNumber = obj.optString("meterNumber");
				String KWH = obj.optString("KWH");
				String KVAH = obj.optString("KVAH");
				mtrno = meterNumber;
				AmrEventsEntity entity = new AmrEventsEntity();
				entity.setMyKey(new KeyEvent(getString(meterNumber), getString(EventCode), getTimeStamp(eventTime)));
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
				entity.setEnergyKvah(getDouble(Energy_KVAH));
				entity.setEnergyKwh(getDouble(Energy_KWH));
				entity.setEnergyKwhExport(getDouble(Energy_KWH_Export));
				entity.setEnergyKwhImport(getDouble(Energy_KWH_Import));

				entity.setiB(getDouble(Ib));
				entity.setImei(getString(imei));
				entity.setiR(getDouble(Ir));
				entity.setiY(getDouble(Iy));
				entity.setKvah(getDouble(KVAH));
				entity.setKwh(getDouble(KWH));
				entity.setKwhExp(getDouble(KWH_Export));
				entity.setKwhImp(getDouble(KWH_Import));
				try {
					entity.setModemTime(getTimeStamp(ModemTime));
				} catch (Exception e) {
					e.getMessage();
					entity.setModemTime(new Timestamp(new Date().getTime()));
				}
				entity.setPfB(getDouble(PFb));
				entity.setPfR(getDouble(PFr));
				entity.setPfY(getDouble(PFy));
				entity.setStructureSize(getInteger(StructureSize));
				entity.setTransId(getString(TransID));
				entity.setvB(getDouble(Vb));
				entity.setvR(getDouble(Vr));
				entity.setvY(getDouble(Vy));

				if (amrEventsService.update(entity) instanceof AmrEventsEntity) {
					// System.out.println("========================INSERTED EVENT");
				}
				/*
				 * ObjectMapper om=new ObjectMapper();
				 * System.out.println(om.writeValueAsString(entity));
				 */

			}

			updateCommunication(imei, meterNumber, "evntjs");

			// if (imei != null) {
			updateMasterMain(imei, meterNumber);
			// }
			map.put(mtrno, "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(mtrno, "failed");
			return map;
		}
	}

	public Map<String, String> saveInstantenious(String inst) {
		Map<String, String> map = new HashMap();
		String mtrno = "";
		// System.out.println("------***********-------Inside Update Instantenious
		// Webservice------***********-------");
		// System.out.println(js);
		try {
			// System.out.println("INSTANTANEOUS=======");
			JSONObject obj = new JSONObject(inst);
			String iRAngle = obj.optString("iRAngle");
			String iYAngle = obj.optString("iYAngle");
			String iBAngle = obj.optString("iBAngle");
			String KWH_Exp = obj.optString("KWH_Exp");
			String KWH_Imp = obj.optString("KWH_Imp");
			String ModemTime = obj.optString("ModemTime");
			String MeterNumber = obj.optString("MeterNumber");
			String Imei = obj.optString("Imei");
			mtrno = MeterNumber;
			if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
				try {
					MasterMainEntity m = mainService.getFeederData(MeterNumber).get(0);
					if (m != null) {
						Imei = m.getModem_sl_no();
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}

			String Ir = obj.optString("Ir");
			String Iy = obj.optString("Iy");
			String Ib = obj.optString("Ib");
			String PFr = obj.optString("PFr");
			String PFy = obj.optString("PFy");
			String PFb = obj.optString("PFb");
			String KWH = obj.optString("KWH");
			String KVAH = obj.optString("KVAH");
			String TransID = obj.optString("TransID");
			String KVAH_Imp = obj.optString("KVAH_Imp");
			String ReadTime = obj.optString("ReadTime");
			String Vrn = obj.optString("Vrn");
			String Vyn = obj.optString("Vyn");
			String Vbn = obj.optString("Vbn");
			String ThreePhasePF = obj.optString("ThreePhasePF");
			String Frequency = obj.optString("Frequency");
			String KVA = obj.optString("KVA");
			String Power_KW = obj.optString("Power_KW");
			String KVAR = obj.optString("KVAR");
			String PowerOffCount = obj.optString("PowerOffCount");
			String TamperCount = obj.optString("TamperCount");
			String MDResetCount = obj.optString("MDResetCount");
			String ProgrammCount = obj.optString("ProgrammCount");
			String MDResetDate = obj.optString("MDResetDate");
			String MDKW = obj.optString("MDKW");
			String Date_MDKW = obj.optString("Date_MDKW");
			String MDKVA = obj.optString("MDKVA");
			String Date_MDKVA = obj.optString("Date_MDKVA");
			String KVARH_Lag = obj.optString("KVARH_Lag");
			String KVARH_Lead = obj.optString("KVARH_Lead");
			String PowerOffDuration = obj.optString("PowerOffDuration");
			String KVAH_Exp = obj.optString("KVAH_Exp");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			// AmrInstantaneousEntity entity = new AmrInstantaneousEntity();
			AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
			// entity.setMyKey(new KeyInstantaneous(MeterNumber,
			// dateFormat.parse(dateFormat.format(new Date()))));
			entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(ReadTime)));
			entity.setTimeStamp(new Timestamp(new Date().getTime()));
			entity.setDateMdKva(getString(Date_MDKVA));
			entity.setDateMdKw(getString(Date_MDKW));
			entity.setFrequency(getDouble(Frequency));
			entity.setiB(getDouble(Ib));
			entity.setiBAngle(getDouble(iBAngle));
			entity.setImei(getString(Imei));
			entity.setiR(getDouble(Ir));
			entity.setiRAngle(getDouble(iRAngle));
			entity.setiY(getDouble(Iy));
			entity.setiYAngle(getDouble(iYAngle));
			entity.setKva(getDouble(KVA));
			entity.setKvah(getDouble(KVAH));
			entity.setKvahExp(getDouble(KVAH_Exp));
			entity.setKvahImp(getDouble(KVAH_Imp));
			entity.setKvar(getDouble(KVAR));
			entity.setKvarhLag(getDouble(KVARH_Lag));
			entity.setKvarhLead(getDouble(KVARH_Lead));
			entity.setKwh(getDouble(KWH));
			entity.setKwhExp(getDouble(KWH_Exp));
			entity.setKwhImp(getDouble(KWH_Imp));
			entity.setMdKva(getDouble(MDKVA));
			entity.setMdKw(getDouble(MDKW));
			entity.setMdResetCount(getInteger(MDResetCount));
			entity.setMdResetDate(getString(MDResetDate));
			// entity.setModemTime(getTimeStamp(ModemTime));
			try {
				entity.setModemTime(getTimeStamp(ModemTime));
			} catch (Exception e) {
				e.getMessage();
				entity.setModemTime(new Timestamp(new Date().getTime()));
			}
			entity.setPfB(getDouble(PFb));
			entity.setPfR(getDouble(PFr));
			entity.setPfThreephase(getDouble(ThreePhasePF));
			entity.setPfY(getDouble(PFy));
			entity.setPowerKw(getDouble(Power_KW));
			entity.setPowerOffCount(getInteger(PowerOffCount));
			entity.setPowerOffDuration(getInteger(PowerOffDuration));
			entity.setProgrammingCount(getInteger(ProgrammCount));
			entity.setReadTime(getTimeStamp(ReadTime));
			entity.setTamperCount(getInteger(TamperCount));
			entity.setTransId(getString(TransID));
			entity.setvB(getDouble(Vbn));
			entity.setvR(getDouble(Vrn));
			entity.setvY(getDouble(Vyn));

			if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
				// System.out.println("UPDATED INSTATNTANEOUS=========== ");
			}
			// if (Imei != null) {
			updateCommunication(Imei, MeterNumber, "instjs");
			updateMasterMain(Imei, MeterNumber);
			// }

			/*
			 * ObjectMapper om=new ObjectMapper();
			 * System.out.println(om.writeValueAsString(entity));
			 */
			map.put(mtrno, "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(mtrno, "failed");
			return map;
		}
	}

	private void writeTrace(String fileName, String data) {
		try {
			PrintWriter fileWriter = null;
			try {
				fileWriter = new PrintWriter(
						new BufferedWriter(new FileWriter(getFolderPath() + "/" + fileName + ".txt", true)));
				fileWriter.println(data);
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getFolderPath() {
		try {
			String month = FilterUnit.logFolder + "/" + new SimpleDateFormat("yyyyMM").format(new Date());
			String day = month + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			if (FilterUnit.folderExists(month)) {
				if (FilterUnit.folderExists(day)) {
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeErrorTrace(String fileName, String data) {
		try {
			PrintWriter fileWriter = null;
			try {
				fileWriter = new PrintWriter(
						new BufferedWriter(new FileWriter(getErrorFolderPath() + "/" + fileName + ".txt", true)));
				fileWriter.println(data);
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String getErrorFolderPath() {
		try {
			String month = FilterUnit.logErrorFolder + "/" + new SimpleDateFormat("yyyyMM").format(new Date());
			String day = month + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			if (FilterUnit.folderExists(month)) {
				if (FilterUnit.folderExists(day)) {
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	


	@RequestMapping(value = "/demoservice", method = { RequestMethod.POST })
	public @ResponseBody Object demoservice(@RequestBody String js) {
		String status = "";
		try {

			JSONObject main = new JSONObject(js.toString());
			String TransId = main.optString("TransId");
			String ClientId = main.optString("ClientId");
			String ClientSecret = main.optString("ClientSecret");
			String MeterNumber = main.optString("MeterNumber");
			String FromDate = main.optString("FromDate");
			String ToDate = main.optString("ToDate");
			String OnDemandType = main.optString("OnDemandType");
			System.out.println("Json= " + js.toString());
			status = "{\"response\":\"ACK\",\"errorMessage\":\"\"}";
		} catch (Exception e) {
			e.printStackTrace();
			status = "failed";

		}
		return status;

	}

	@RequestMapping(value = "/demoserviceMtrChange", method = { RequestMethod.POST })
	public @ResponseBody Object demoserviceMtrChange(@RequestBody String js) {
		String status = "";
		try {
			System.out.println("call server server");
			System.out.println("Inside sevice json data--" + js);

			JSONObject main = new JSONObject(js.toString());
			String NewMeterInitialKvah = main.optString("NewMeterInitialKvah");
			String IpdsScheme = main.optString("IpdsScheme");
			String OldMaximumDemand = main.optString("OldMaximumDemand");
			String NewMeterType = main.optString("NewMeterType");
			String OldMeterNumber = main.optString("OldMeterNumber");
			String OldKvah = main.optString("OldKvah");
			String NewMeterMake = main.optString("NewMeterMake");
			String NewMeterNuber = main.optString("NewMeterNuber");
			String OldKwh = main.optString("OldKwh");
			String NewMeterInitialKwh = main.optString("NewMeterInitialKwh");
			String OldKwhDialCompletion = main.optString("OldKwhDialCompletion");
			String InstalledDate = main.optString("InstalledDate");
			String OldMeterReleaseDate = main.optString("OldMeterReleaseDate");
			String OldKvahDialCompletion = main.optString("OldKvahDialCompletion");
			String NewMeterCapacity = main.optString("NewMeterCapacity");
			String NewMeterMf = main.optString("NewMeterMf");
			System.out.println("Meter change Json= " + js.toString());
			status = "{\"response\":\"ACK\",\"errorMessage\":\"\"}";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failed";

		}
		return status;

	}
	
	
//  @RequestMapping(value = "/dataParserUpdateBills", method = { RequestMethod.POST })
//	public @ResponseBody Object UpdateBills(@RequestBody String js) {
//
//		try {
//			writeTrace("billjs", js);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//		try {
//			JSONArray recs = new JSONArray(js.toString());
//			String imei = null, meterNumber = null;
//
//			for (int i = 0; i < recs.length(); i++) {
//
//				JSONObject obj = recs.getJSONObject(i);
//				String transID = obj.optString("transID");
//				String METER_ID = obj.optString("METER_ID");
//				meterNumber = obj.optString("meterNumber");
//				imei = obj.optString("IMEI");
//
//				if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//					try {
//						MasterMainEntity m = mainService.getFeederData(meterNumber).get(0);
//						if (m != null) {
//							imei = m.getModem_sl_no();
//						}
//					} catch (Exception e) {
//						e.getMessage();
//					}
//				}
//
//				String serverTime = obj.optString("serverTime");
//				String billingDate = obj.optString("billingDate");
//				String sys_PF_Billing = obj.optString("sys_PF_Billing");
//				String kWh = obj.optString("kWh");
//				String kWh_TZ1 = obj.optString("kWh_TZ1");
//				String kWh_TZ2 = obj.optString("kWh_TZ2");
//				String kWh_TZ3 = obj.optString("kWh_TZ3");
//				String kWh_TZ4 = obj.optString("kWh_TZ4");
//				String kWh_TZ5 = obj.optString("kWh_TZ5");
//				String kWh_TZ6 = obj.optString("kWh_TZ6");
//				String kWh_TZ7 = obj.optString("kWh_TZ7");
//				String kWh_TZ8 = obj.optString("kWh_TZ8");
//				String kvarhLag = obj.optString("kvarhLag");
//				String kvarhLead = obj.optString("kvarhLead");
//				String kVAh = obj.optString("kVAh");
//				String kVAh_TZ1 = obj.optString("kVAh_TZ1");
//				String kVAh_TZ2 = obj.optString("kVAh_TZ2");
//				String kVAh_TZ3 = obj.optString("kVAh_TZ3");
//				String kVAh_TZ4 = obj.optString("kVAh_TZ4");
//				String kVAh_TZ5 = obj.optString("kVAh_TZ5");
//				String kVAh_TZ6 = obj.optString("kVAh_TZ6");
//				String kVAh_TZ7 = obj.optString("kVAh_TZ7");
//				String kVAh_TZ8 = obj.optString("kVAh_TZ8");
//				String Demand_kW = obj.optString("Demand_kW");
//				String OccDate_kW = obj.optString("OccDate_kW");
//				String kW_TZ1 = obj.optString("kW_TZ1");
//				String Date_kW_TZ1 = obj.optString("Date_kW_TZ1");
//				String kW_TZ2 = obj.optString("kW_TZ2");
//				String Date_kW_TZ2 = obj.optString("Date_kW_TZ2");
//				String kW_TZ3 = obj.optString("kW_TZ3");
//				String Date_kW_TZ3 = obj.optString("Date_kW_TZ3");
//				String kW_TZ4 = obj.optString("kW_TZ4");
//				String Date_kW_TZ4 = obj.optString("Date_kW_TZ4");
//				String kW_TZ5 = obj.optString("kW_TZ5");
//				String Date_kW_TZ5 = obj.optString("Date_kW_TZ5");
//				String kW_TZ6 = obj.optString("kW_TZ6");
//				String Date_kW_TZ6 = obj.optString("Date_kW_TZ6");
//				String kW_TZ7 = obj.optString("kW_TZ7");
//				String Date_kW_TZ7 = obj.optString("Date_kW_TZ7");
//				String kW_TZ8 = obj.optString("kW_TZ8");
//				String Date_kW_TZ8 = obj.optString("Date_kW_TZ8");
//				String kVA = obj.optString("kVA");
//				String Date_kVA = obj.optString("Date_kVA");
//				String kVA_TZ1 = obj.optString("kVA_TZ1");
//				String Date_kVA_TZ1 = obj.optString("Date_kVA_TZ1");
//				String kVA_TZ2 = obj.optString("kVA_TZ2");
//				String Date_kVA_TZ2 = obj.optString("Date_kVA_TZ2");
//				String kVA_TZ3 = obj.optString("kVA_TZ3");
//				String Date_kVA_TZ3 = obj.optString("Date_kVA_TZ3");
//				String kVA_TZ4 = obj.optString("kVA_TZ4");
//				String Date_kVA_TZ4 = obj.optString("Date_kVA_TZ4");
//				String kVA_TZ5 = obj.optString("kVA_TZ5");
//				String Date_kVA_TZ5 = obj.optString("Date_kVA_TZ5");
//				String kVA_TZ6 = obj.optString("kVA_TZ6");
//				String Date_kVA_TZ6 = obj.optString("Date_kVA_TZ6");
//				String kVA_TZ7 = obj.optString("kVA_TZ7");
//				String Date_kVA_TZ7 = obj.optString("Date_kVA_TZ7");
//				String kVA_TZ8 = obj.optString("kVA_TZ8");
//				String Date_kVA_TZ8 = obj.optString("Date_kVA_TZ8");
//
//				// NEW
//				String ClientID = obj.optString("ClientID");
//				String date_kw_tz1 = obj.optString("date_kw_tz1");
//				String date_kw_tz2 = obj.optString("date_kw_tz2");
//				String date_kw_tz3 = obj.optString("date_kw_tz3");
//				String date_kw_tz4 = obj.optString("date_kw_tz4");
//				String date_kw_tz5 = obj.optString("date_kw_tz5");
//				String date_kw_tz6 = obj.optString("date_kw_tz6");
//				String date_kw_tz7 = obj.optString("date_kw_tz7");
//				String date_kw_tz8 = obj.optString("date_kw_tz8");
//				String bill_power_on_duration = obj.optString("bill_power_on_duration");
//				String bill_kwh_export = obj.optString("bill_kwh_export");
//
//				AmrBillsEntity entity = new AmrBillsEntity();
//				entity.setMyKey(new KeyBills(getString(meterNumber), getTimeStamp(billingDate)));
//				entity.setTimeStamp(new Timestamp(new Date().getTime()));
//				entity.setDateKva(getString(Date_kVA));
//				entity.setDateKvaTz1(getString(Date_kVA_TZ1));
//				entity.setDateKvaTz2(getString(Date_kVA_TZ2));
//				entity.setDateKvaTz3(getString(Date_kVA_TZ3));
//				entity.setDateKvaTz4(getString(Date_kVA_TZ4));
//				entity.setDateKvaTz5(getString(Date_kVA_TZ5));
//				entity.setDateKvaTz6(getString(Date_kVA_TZ6));
//				entity.setDateKvaTz7(getString(Date_kVA_TZ7));
//				entity.setDateKvaTz8(getString(Date_kVA_TZ8));
//				entity.setDateKwTz1(getString(Date_kW_TZ1));
//				entity.setDateKwTz2(getString(Date_kW_TZ2));
//				entity.setDateKwTz3(getString(Date_kW_TZ3));
//				entity.setDateKwTz4(getString(Date_kW_TZ4));
//				entity.setDateKwTz5(getString(Date_kW_TZ5));
//				entity.setDateKwTz6(getString(Date_kW_TZ6));
//				entity.setDateKwTz7(getString(Date_kW_TZ7));
//				entity.setDateKwTz8(getString(Date_kW_TZ8));
//				entity.setDemandKw(getDouble(Demand_kW));
//				entity.setImei(getString(imei));
//				entity.setKva(getDouble(kVA));
//				entity.setKvah(getDouble(kVAh));
//				entity.setKvahTz1(getDouble(kVAh_TZ1));
//				entity.setKvahTz2(getDouble(kVAh_TZ2));
//				entity.setKvahTz3(getDouble(kVAh_TZ3));
//				entity.setKvahTz4(getDouble(kVAh_TZ4));
//				entity.setKvahTz5(getDouble(kVAh_TZ5));
//				entity.setKvahTz6(getDouble(kVAh_TZ6));
//				entity.setKvahTz7(getDouble(kVAh_TZ7));
//				entity.setKvahTz8(getDouble(kVAh_TZ8));
//				entity.setKvarhLag(getDouble(kvarhLag));
//				entity.setKvarhLead(getDouble(kvarhLead));
//				entity.setKvaTz1(getDouble(kVA_TZ1));
//				entity.setKvaTz2(getDouble(kVA_TZ2));
//				entity.setKvaTz3(getDouble(kVA_TZ3));
//				entity.setKvaTz4(getDouble(kVA_TZ4));
//				entity.setKvaTz5(getDouble(kVA_TZ5));
//				entity.setKvaTz6(getDouble(kVA_TZ6));
//				entity.setKvaTz7(getDouble(kVA_TZ7));
//				entity.setKvaTz8(getDouble(kVA_TZ8));
//				entity.setKwh(getDouble(kWh));
//				entity.setKwhTz1(getDouble(kWh_TZ1));
//				entity.setKwhTz2(getDouble(kWh_TZ2));
//				entity.setKwhTz3(getDouble(kWh_TZ3));
//				entity.setKwhTz4(getDouble(kWh_TZ4));
//				entity.setKwhTz5(getDouble(kWh_TZ5));
//				entity.setKwhTz6(getDouble(kWh_TZ6));
//				entity.setKwhTz7(getDouble(kWh_TZ7));
//				entity.setKwhTz8(getDouble(kWh_TZ8));
//				entity.setMeterId(getString(METER_ID));
//				entity.setOccDateKw(getString(OccDate_kW));
//				entity.setServerTime(getTimeStamp(serverTime));
//				entity.setSysPfBilling(getDouble(sys_PF_Billing));
//				entity.setTransId(getString(transID));
//				entity.setKwTz1(getDouble(kW_TZ1));
//				entity.setKwTz2(getDouble(kW_TZ2));
//				entity.setKwTz3(getDouble(kW_TZ3));
//				entity.setKwTz4(getDouble(kW_TZ4));
//				entity.setKwTz5(getDouble(kW_TZ5));
//				entity.setKwTz6(getDouble(kW_TZ6));
//				entity.setKwTz7(getDouble(kW_TZ7));
//				entity.setKwTz8(getDouble(kW_TZ8));
//
//				if (amrBillsService.update(entity) instanceof AmrBillsEntity) {
//
//				}
//				try {
//					updateCommunication(imei, meterNumber, "billjs");
//					updateMasterMain(imei, meterNumber);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//			}		
//			return "success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "failed";
//		}
//
//  }
	
	
	
	//	@RequestMapping(value = "/dataParserUpdateEvent", method = { RequestMethod.POST })
//	public @ResponseBody Object UpdateEvent(@RequestBody String js) {
//		//System.out.println("------***********-------Inside Update Event Webservice------***********-------");
//		//System.out.println(js);
//		
//		try {
//			writeTrace("eventjs", js);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			String imei = null, meterNumber = null;
//			JSONArray recs = new JSONArray(js.toString());
//			for (int i = 0; i < recs.length(); i++) {
//				JSONObject obj = recs.getJSONObject(i);
//				
//				imei = obj.optString("Imei");
//				
//				if(imei==null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//					try {
//						MasterMainEntity m=mainService.getFeederData(meterNumber).get(0);
//						if(m!=null) {
//							imei=m.getModem_sl_no();
//						}
//					}catch (Exception e) {
//						e.getMessage();
//					}
//				}
//				
//				String eventTime = obj.optString("TimeStamp");
//				String ModemTime = obj.optString("ModemTime");
//				String EventCode = obj.optString("EventCode");
//				String Ir = obj.optString("Ir");
//				String Iy = obj.optString("Iy");
//				String Ib = obj.optString("Ib");
//				String Vr = obj.optString("Vr");
//				String Vy = obj.optString("Vy");
//				String Vb = obj.optString("Vb");
//				String PFr = obj.optString("PFr");
//				String PFy = obj.optString("PFy");
//				String PFb = obj.optString("PFb");
//				String Energy_KWH = obj.optString("Energy_KWH");
//				String Energy_KWH_Import = obj.optString("Energy_KWH_Import");
//				String Energy_KWH_Export = obj.optString("Energy_KWH_Export");
//				String Energy_KVAH = obj.optString("Energy_KVAH");
//				String TransID = obj.optString("TransID");
//				String StructureSize = obj.optString("StructureSize");
//				String KWH_Export = obj.optString("KWH_Export");
//				String KWH_Import = obj.optString("KWH_Import");
//				meterNumber = obj.optString("meterNumber");
//				String KWH = obj.optString("KWH");
//				String KVAH = obj.optString("KVAH");
//
//				//NEW
//				String ClientID = obj.optString("ClientID");
//				String Vry=obj.optString("Vry");
//				String Vby=obj.optString("Vby");
//
//				AmrEventsEntity entity = new AmrEventsEntity();
//				entity.setMyKey(new KeyEvent(getString(meterNumber), getString(EventCode), getTimeStamp(eventTime)));
//				entity.setTimeStamp(new Timestamp(new Date().getTime()));
//				entity.setEnergyKvah(getDouble(Energy_KVAH));
//				entity.setEnergyKwh(getDouble(Energy_KWH));
//				entity.setEnergyKwhExport(getDouble(Energy_KWH_Export));
//				entity.setEnergyKwhImport(getDouble(Energy_KWH_Import));
//			
//				entity.setiB(getDouble(Ib));
//				entity.setImei(getString(imei));
//				entity.setiR(getDouble(Ir));
//				entity.setiY(getDouble(Iy));
//				entity.setKvah(getDouble(KVAH));
//				entity.setKwh(getDouble(KWH));
//				entity.setKwhExp(getDouble(KWH_Export));
//				entity.setKwhImp(getDouble(KWH_Import));
//				entity.setModemTime(getTimeStamp(ModemTime));
//				entity.setPfB(getDouble(PFb));
//				entity.setPfR(getDouble(PFr));
//				entity.setPfY(getDouble(PFy));
//				entity.setStructureSize(getInteger(StructureSize));
//				entity.setTransId(getString(TransID));
//				entity.setvB(getDouble(Vb));
//				entity.setvR(getDouble(Vr));
//				entity.setvY(getDouble(Vy));
//
//				if (amrEventsService.update(entity) instanceof AmrEventsEntity) {
//					// System.out.println("========================INSERTED EVENT");
//				}
//				try {
//					updateCommunication(imei, meterNumber, "evntjs");
//					updateMasterMain(imei, meterNumber);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//					
//			return "success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "failed";
//		}
//
//	}
	
	
	//	@RequestMapping(value = "/dataParserUpdateLoad", method = { RequestMethod.POST })
//	public @ResponseBody Object UpdateLoad(@RequestBody String js) {
//		// System.out.println("------***********-------Inside Update Load
//		// Webservice------***********-------");
//		// System.out.println(js);
//
//		try {
//			writeTrace("loadjs", js);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			String imei = null, meterNumber = null;
//			JSONArray recs = new JSONArray(js.toString());
//			for (int i = 0; i < recs.length(); i++) {
//				JSONObject obj = recs.getJSONObject(i);
//				// System.out.println("==================================== PARSE
//				// LOADSURVEY"+i);
//				String transID = obj.optString("transID");
//				String structureSize = obj.optString("structureSize");
//				String readTime = obj.optString("TimeStamp");
//				String KWH_Exp = obj.optString("KWH_Exp");
//				String KWH_Imp = obj.optString("KWH_Imp");
//				String ModemTime = obj.optString("ModemTime");
//				meterNumber = obj.optString("MeterNumber");
//				imei = obj.optString("Imei");
//
//				if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//					try {
//						MasterMainEntity m = mainService.getFeederData(meterNumber).get(0);
//						if (m != null) {
//							imei = m.getModem_sl_no();
//						}
//					} catch (Exception e) {
//						e.getMessage();
//					}
//				}
//
//				String Ir = obj.optString("Ir");
//				String Iy = obj.optString("Iy");
//				String Ib = obj.optString("Ib");
//				String Vr = obj.optString("Vr");
//				String Vy = obj.optString("Vy");
//				String Vb = obj.optString("Vb");
//				String KWH = obj.optString("KWH");
//				String KVAH = obj.optString("KVAH");
//				String KVARH_Q1 = obj.optString("KVARH_Q1");
//				String KVARH_Q3 = obj.optString("KVARH_Q3");
//				String KVARH_Q4 = obj.optString("KVARH_Q4");
//				String KVARH_Q2 = obj.optString("KVARH_Q2");
//				String KVARH_Lag = obj.optString("KVARH_Lag");
//				String KVARH_Lead = obj.optString("KVARH_Lead");
//				String Frequnecy = obj.optString("Frequnecy");
//				String NetKWH = obj.optString("NetKWH");
//				String pf = obj.optString("power_factor");
//
//				// NEW
//				String ClientID = obj.optString("ClientID");
//				String Vry = obj.optString("Vry");
//				String Vby = obj.optString("Vby");
//				String kW = obj.optString("kW");
//				String kVA = obj.optString("kVA");
//				String kVArLag = obj.optString("kVArLag");
//				String kVarLead = obj.optString("kVarLead");
//				String neutral_current = obj.optString("neutral_current");
//				String pf_threephase = obj.optString("pf_threephase");
//				String average_voltage = obj.optString("average_voltage");
//				String average_current = obj.optString("average_current");
//				String netkwh = obj.optString("netkwh");
//
//				AmrLoadEntity entity = new AmrLoadEntity();
//				entity.setTimeStamp(new Timestamp(new Date().getTime()));
//				entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(readTime)));
//				entity.setFrequency(getDouble(Frequnecy));
//				entity.setiB(getDouble(Ib));
//				entity.setImei(getString(imei));
//				entity.setiR(getDouble(Ir));
//				entity.setiY(getDouble(Iy));
//				entity.setKvah(getDouble(KVAH));
//				entity.setKvarhLag(getDouble(KVARH_Lag));
//				entity.setKvarhLead(getDouble(KVARH_Lead));
//				entity.setKvarhQ1(getDouble(KVARH_Q1));
//				entity.setKvarhQ2(getDouble(KVARH_Q2));
//				entity.setKvarhQ3(getDouble(KVARH_Q3));
//				entity.setKvarhQ4(getDouble(KVARH_Q4));
//				entity.setKwh(getDouble(KWH));
//				entity.setKwhExp(getDouble(KWH_Exp));
//				entity.setKwhImp(getDouble(KWH_Imp));
//				entity.setModemTime(getTimeStamp(ModemTime));
//				entity.setNetKwh(getDouble(NetKWH));
//				entity.setStructureSize(getInteger(structureSize));
//				entity.setTransId(getString(transID));
//				entity.setvB(getDouble(Vb));
//				entity.setvR(getDouble(Vr));
//				entity.setvY(getDouble(Vy));
//				entity.setPowerFactor(pf == null ? null : pf.equals("") ? null : Double.parseDouble(pf));
//				if (amrLoadService.update(entity) instanceof AmrLoadEntity) {
//					// System.out.println("========================INSERTED LOAD");
//				}
//
//				/*
//				 * ObjectMapper om=new ObjectMapper();
//				 * System.out.println(om.writeValueAsString(entity));
//				 */
//
//			}
//
//			try {
//				updateCommunication(imei, meterNumber, "loadjs");
//				updateMasterMain(imei, meterNumber);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//
//			return "success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "failed";
//		}
//
//	}


//   @RequestMapping(value = "/dataParserUpdateInstantFiveDays", method = { RequestMethod.POST })
//	public @ResponseBody Object UpdateInstantFiveDays(@RequestBody String js) {
//		try {
//			writeTrace("instjs_FiveDays", js);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//		Date todayDate = new Date();
//
//		try {
//			// current date before 5 Days
//			Calendar currentDateBefore5Days = Calendar.getInstance();
//			currentDateBefore5Days.add(Calendar.DATE, -6);
//
//			// current date
//			Calendar currentDate = Calendar.getInstance();
//			currentDate.add(Calendar.DATE, 0);
//
//			JSONObject obj = new JSONObject(js.toString());
//
//			Date date = dateFormatter.parse(obj.optString("ReadTime").toString());
//
//			// Accept only Current date to previous 5 days data
//			if (date.after(currentDateBefore5Days.getTime()) && date.before(currentDate.getTime())) {
//				System.out.println("DateToValidate : Within 5 days");
//
//				String iRAngle = obj.optString("iRAngle");
//				String iYAngle = obj.optString("iYAngle");
//				String iBAngle = obj.optString("iBAngle");
//				String KWH_Exp = obj.optString("KWH_Exp");
//				String KWH_Imp = obj.optString("KWH_Imp");
//				String ModemTime = obj.optString("ModemTime");
//				String MeterNumber = obj.optString("MeterNumber");
//				String Imei = obj.optString("Imei");
//
//				if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
//					try {
//						MasterMainEntity m = mainService.getFeederData(MeterNumber).get(0);
//						if (m != null) {
//							Imei = m.getModem_sl_no();
//						}
//					} catch (Exception e) {
//						e.getMessage();
//					}
//				}
//
//				String Ir = obj.optString("Ir");
//				String Iy = obj.optString("Iy");
//				String Ib = obj.optString("Ib");
//				String PFr = obj.optString("PFr");
//				String PFy = obj.optString("PFy");
//				String PFb = obj.optString("PFb");
//				String KWH = obj.optString("KWH");
//				String KVAH = obj.optString("KVAH");
//				String TransID = obj.optString("TransID");
//				String KVAH_Imp = obj.optString("KVAH_Imp");
//				String ReadTime = obj.optString("ReadTime");
//				String Vrn = obj.optString("Vrn");
//				String Vyn = obj.optString("Vyn");
//				String Vbn = obj.optString("Vbn");
//				String ThreePhasePF = obj.optString("ThreePhasePF");
//				String Frequency = obj.optString("Frequency");
//				String KVA = obj.optString("KVA");
//				String Power_KW = obj.optString("Power_KW");
//				String KVAR = obj.optString("KVAR");
//				String PowerOffCount = obj.optString("PowerOffCount");
//				String TamperCount = obj.optString("TamperCount");
//				String MDResetCount = obj.optString("MDResetCount");
//				String ProgrammCount = obj.optString("ProgrammCount");
//				String MDResetDate = obj.optString("MDResetDate");
//				String MDKW = obj.optString("MDKW");
//				String Date_MDKW = obj.optString("Date_MDKW");
//				String MDKVA = obj.optString("MDKVA");
//				String Date_MDKVA = obj.optString("Date_MDKVA");
//				String KVARH_Lag = obj.optString("KVARH_Lag");
//				String KVARH_Lead = obj.optString("KVARH_Lead");
//				String PowerOffDuration = obj.optString("PowerOffDuration");
//				String KVAH_Exp = obj.optString("KVAH_Exp");
//
//				// NEW
//				String ClientID = obj.optString("ClientID");
//				String Vry = obj.optString("Vry");
//				String Vby = obj.optString("Vby");
//				String Phase_Sequence = obj.optString("Phase_Sequence");
//				String VoltAngRY = obj.optString("VoltAngRY");
//				String VoltAngRB = obj.optString("VoltAngRB");
//				String VoltAngYB = obj.optString("VoltAngYB");
//				String total_power_on_duration = obj.optString("total_power_on_duration");
//				String neutral_current = obj.optString("neutral_current");
//
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//
//				AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
//				entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(ReadTime)));
//				entity.setTimeStamp(new Timestamp(new Date().getTime()));
//				entity.setDateMdKva(getString(Date_MDKVA));
//				entity.setDateMdKw(getString(Date_MDKW));
//				entity.setFrequency(getDouble(Frequency));
//				entity.setiB(getDouble(Ib));
//				entity.setiBAngle(getDouble(iBAngle));
//				entity.setImei(getString(Imei));
//				entity.setiR(getDouble(Ir));
//				entity.setiRAngle(getDouble(iRAngle));
//				entity.setiY(getDouble(Iy));
//				entity.setiYAngle(getDouble(iYAngle));
//				entity.setKva(getDouble(KVA));
//				entity.setKvah(getDouble(KVAH));
//				entity.setKvahExp(getDouble(KVAH_Exp));
//				entity.setKvahImp(getDouble(KVAH_Imp));
//				entity.setKvar(getDouble(KVAR));
//				entity.setKvarhLag(getDouble(KVARH_Lag));
//				entity.setKvarhLead(getDouble(KVARH_Lead));
//				entity.setKwh(getDouble(KWH));
//				entity.setKwhExp(getDouble(KWH_Exp));
//				entity.setKwhImp(getDouble(KWH_Imp));
//				entity.setMdKva(getDouble(MDKVA));
//				entity.setMdKw(getDouble(MDKW));
//				entity.setMdResetCount(getInteger(MDResetCount));
//				entity.setMdResetDate(getString(MDResetDate));
//				entity.setModemTime(getTimeStamp(ModemTime));
//				entity.setPfB(getDouble(PFb));
//				entity.setPfR(getDouble(PFr));
//				entity.setPfThreephase(getDouble(ThreePhasePF));
//				entity.setPfY(getDouble(PFy));
//				entity.setPowerKw(getDouble(Power_KW));
//				entity.setPowerOffCount(getInteger(PowerOffCount));
//				entity.setPowerOffDuration(getInteger(PowerOffDuration));
//				entity.setProgrammingCount(getInteger(ProgrammCount));
//				entity.setReadTime(getTimeStamp(ReadTime));
//				entity.setTamperCount(getInteger(TamperCount));
//				entity.setTransId(getString(TransID));
//				entity.setvB(getDouble(Vbn));
//				entity.setvR(getDouble(Vrn));
//				entity.setvY(getDouble(Vyn));
//
//				if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
//					// System.out.println("UPDATED INSTATNTANEOUS=========== ");
//				}
//
//				try {
//					updateCommunication(Imei, MeterNumber, "instjs");
//					updateMasterMain(Imei, MeterNumber);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				return "success";
//			} else {
//
//				return "Data is not last 5 Days Data";
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "failed";
//		}
//
//	}

}
