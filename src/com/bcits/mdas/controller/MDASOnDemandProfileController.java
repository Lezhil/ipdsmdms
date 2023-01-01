/**
 * 
 */
package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrEventsEntity.KeyEvent;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadRawEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.entity.AmrLoadRawEntity.KeyRawLoad;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.entity.OnDemandTransaction;
import com.bcits.mdas.mqtt.Subscriber;
import com.bcits.mdas.service.AmiInstantaneousService;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrLoadRawService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.OnDemandServiceInst;
import com.bcits.mdas.service.OnDemandServiceTransactionLog;
import com.bcits.mdas.service.OnDemandServicenamepla;
import com.bcits.mdas.service.OndemandTranService;

/**
 * @author Tarik
 *
 */
@Controller
public class MDASOnDemandProfileController {

	@Autowired
	HESController hesc;

	@Autowired
	OnDemandServiceInst ons;

	@Autowired
	OnDemandServicenamepla odp;

	@Autowired
	OnDemandServiceTransactionLog odtl;

	@Autowired
	private AmrEventsService amrEventsService;

	@Autowired
	public AmrBillsService amrBillsService;

	@Autowired
	public AmrLoadService amrLoadService;
	
	@Autowired
	private AmrLoadRawService amrLoadRawService;

	@Autowired
	public OndemandTranService ods;

	@Autowired
	private ModemCommunicationService modemCommunication;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private AmiInstantaneousService amiInstantaneousService;

	@RequestMapping(value = "/MDASonDemandProfile/INSTANTANEOUS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String instantaneous(HttpServletRequest request) throws ParseException {

		String hesType = request.getParameter("hesType").trim();
		String meterNo = request.getParameter("meterId").trim();
		String fromdate = request.getParameter("fromdate").substring(0, 10);
		String todate = request.getParameter("todate").substring(0, 10);

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

//		System.out.println(timeStamp);
//		System.out.println("hesType= " + hesType);
//		System.out.println("todate= " + todate);
//		System.out.println("fromdate= " + fromdate);
//		System.out.println("METER= " + meterNo);

		String s = hesc.onDemandProfile_TNEB("INSTANTANEOUS",hesType, meterNo, fromdate,todate);

		if (s == null) {
			return "NoData";
		} else {

			try {

				JSONArray recs = new JSONArray(s.toString());
				String Imei = null, MeterNumber = null;
				String transID=null;

				for (int i = 0; i < recs.length(); i++) {
					JSONObject obj = recs.getJSONObject(i);
					
					// Old Integration
//					String iRAngle = obj.optString("iRAngle");
//					String iYAngle = obj.optString("iYAngle");
//					String iBAngle = obj.optString("iBAngle");
//					String KWH_Exp = obj.optString("KWH_Exp");
//					String KWH_Imp = obj.optString("KWH_Imp");
//					String ModemTime = obj.optString("ModemTime");
//					MeterNumber = obj.optString("MeterNumber");
//					Imei = obj.optString("Imei");
//
//					if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
//						try {
//							MasterMainEntity m = masterMainService.getFeederData(MeterNumber).get(0);
//							if (m != null) {
//								Imei = m.getModem_sl_no();
//							}
//						} catch (Exception e) {
//							e.getMessage();
//						}
//					}
//
//					String Ir = obj.optString("Ir");
//					String Iy = obj.optString("Iy");
//					String Ib = obj.optString("Ib");
//					String PFr = obj.optString("PFr");
//					String PFy = obj.optString("PFy");
//					String PFb = obj.optString("PFb");
//					String KWH = obj.optString("KWH");
//					String KVAH = obj.optString("KVAH");
//					transID = obj.optString("TransID");
//					String KVAH_Imp = obj.optString("KVAH_Imp");
//					String ReadTime = obj.optString("ReadTime");
//					String Vrn = obj.optString("Vrn");
//					String Vyn = obj.optString("Vyn");
//					String Vbn = obj.optString("Vbn");
//					String ThreePhasePF = obj.optString("ThreePhasePF");
//					String Frequency = obj.optString("Frequency");
//					String KVA = obj.optString("KVA");
//					String Power_KW = obj.optString("Power_KW");
//					String KVAR = obj.optString("KVAR");
//					String PowerOffCount = obj.optString("PowerOffCount");
//					String TamperCount = obj.optString("TamperCount");
//					String MDResetCount = obj.optString("MDResetCount");
//					String ProgrammCount = obj.optString("ProgrammCount");
//					String MDResetDate = obj.optString("MDResetDate");
//					String MDKW = obj.optString("MDKW");
//					String Date_MDKW = obj.optString("Date_MDKW");
//					String MDKVA = obj.optString("MDKVA");
//					String Date_MDKVA = obj.optString("Date_MDKVA");
//					String KVARH_Lag = obj.optString("KVARH_Lag");
//					String KVARH_Lead = obj.optString("KVARH_Lead");
//					String PowerOffDuration = obj.optString("PowerOffDuration");
//					String KVAH_Exp = obj.optString("KVAH_Exp");
//
//					// NEW
//					String ClientID = obj.optString("ClientID");
//					String Vry = obj.optString("Vry");
//					String Vby = obj.optString("Vby");
//					String Phase_Sequence = obj.optString("Phase_Sequence");
//					String VoltAngRY = obj.optString("VoltAngRY");
//					String VoltAngRB = obj.optString("VoltAngRB");
//					String VoltAngYB = obj.optString("VoltAngYB");
//					String total_power_on_duration = obj.optString("total_power_on_duration");
//					String neutral_current = obj.optString("neutral_current");
//
//					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//
//					AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
//					entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(ReadTime)));
//					entity.setTimeStamp(new Timestamp(new Date().getTime()));
//					entity.setFlag("OD");
//					entity.setDateMdKva(getString(Date_MDKVA));
//					entity.setDateMdKw(getString(Date_MDKW));
//					entity.setFrequency(getDouble(Frequency));
//					entity.setiB(getDouble(Ib));
//					entity.setiBAngle(getDouble(iBAngle));
//					entity.setImei(getString(Imei));
//					entity.setiR(getDouble(Ir));
//					entity.setiRAngle(getDouble(iRAngle));
//					entity.setiY(getDouble(Iy));
//					entity.setiYAngle(getDouble(iYAngle));
//					entity.setKva(getDouble(KVA));
//					entity.setKvah(getDouble(KVAH));
//					entity.setKvahExp(getDouble(KVAH_Exp));
//					entity.setKvahImp(getDouble(KVAH_Imp));
//					entity.setKvar(getDouble(KVAR));
//					entity.setKvarhLag(getDouble(KVARH_Lag));
//					entity.setKvarhLead(getDouble(KVARH_Lead));
//					entity.setKwh(getDouble(KWH));
//					entity.setKwhExp(getDouble(KWH_Exp));
//					entity.setKwhImp(getDouble(KWH_Imp));
//					entity.setMdKva(getDouble(MDKVA));
//					entity.setMdKw(getDouble(MDKW));
//					entity.setMdResetCount(getInteger(MDResetCount));
//					entity.setMdResetDate(getString(MDResetDate));
//					entity.setModemTime(getTimeStamp(ModemTime));
//					entity.setPfB(getDouble(PFb));
//					entity.setPfR(getDouble(PFr));
//					entity.setPfThreephase(getDouble(ThreePhasePF));
//					entity.setPfY(getDouble(PFy));
//					entity.setPowerKw(getDouble(Power_KW));
//					entity.setPowerOffCount(getInteger(PowerOffCount));
//					entity.setPowerOffDuration(getInteger(PowerOffDuration));
//					entity.setProgrammingCount(getInteger(ProgrammCount));
//					entity.setReadTime(getTimeStamp(ReadTime));
//					entity.setTamperCount(getInteger(TamperCount));
//					entity.setTransId(getString(transID));
//					entity.setvB(getDouble(Vbn));
//					entity.setvR(getDouble(Vrn));
//					entity.setvY(getDouble(Vyn));
//
//					if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
//						// System.out.println("UPDATED INSTATNTANEOUS=========== ");
//					}
					
					
					//New Integration Based on DLMS Parameter
					
					MeterNumber = obj.optString("MeterNumber");
					Imei = obj.optString("Imei");		
					transID = obj.optString("TransID");
					// Below parameter is not mapped into table
					String KVARH_Q2 = obj.optString("KVARH_Q2"); //Not used
					String KVARH_Q3 = obj.optString("KVARH_Q3"); //Not used		
					String MDKW_Exp = obj.optString("MDKW_Exp"); //Not used
					String Date_MDKW_Exp = obj.optString("Date_MDKW_Exp"); //Not used
					String MDKVA_Exp = obj.optString("MDKVA_Exp"); //Not used
					String Date_MDKVA_Exp = obj.optString("Date_MDKVA_Exp"); //Not used				
					
					if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
						try {
							MasterMainEntity m = masterMainService.getFeederData(MeterNumber).get(0);
							if (m != null) {
								Imei = m.getModem_sl_no();
							}
						} catch (Exception e) {
							e.getMessage();
						}
					}

					
					AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
					entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(obj.optString("ReadTime"))));
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setFlag("OD");
					entity.setDateMdKva(getString(obj.optString("Date_MDKVA")));
					entity.setDateMdKw(getString(obj.optString("Date_MDKW")));
					entity.setFrequency(getDouble(obj.optString("Frequency")));
					entity.setiB(getDouble(obj.optString("Ib")));
					entity.setImei(getString(Imei));
					entity.setiR(getDouble(obj.optString("Ir")));
					entity.setiY(getDouble(obj.optString("Iy")));
					entity.setKva(getDouble(obj.optString("KVA")));
					entity.setKvah(getDouble(obj.optString("KVAH")));
					entity.setKvahExp(getDouble(obj.optString("KVAH_Exp")));
					entity.setKvar(getDouble(obj.optString("KVAR")));
					entity.setKwhExp(getDouble(obj.optString("KWH_Exp")));
					entity.setKwhImp(getDouble(obj.optString("KWH_Imp")));
					entity.setMdKva(getDouble(obj.optString("MDKVA")));
					entity.setMdKw(getDouble(obj.optString("MDKW")));
					entity.setMdResetCount(getInteger(obj.optString("MDResetCount")));
					entity.setMdResetDate(getString(obj.optString("MDResetDate")));
					entity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
					entity.setPfB(getDouble(obj.optString("PFb")));
					entity.setPfR(getDouble(obj.optString("PFr")));
					entity.setPfThreephase(getDouble(obj.optString("ThreePhasePF")));
					entity.setPfY(getDouble(obj.optString("PFy")));
					entity.setPowerKw(getDouble(obj.optString("Power_KW")));
					entity.setPowerOffCount(getInteger(obj.optString("PowerOffCount")));
					entity.setPowerOffDuration(getInteger(obj.optString("PowerOffDuration")));
					entity.setProgrammingCount(getInteger(obj.optString("ProgrammCount")));
					entity.setReadTime(getTimeStamp(obj.optString("ReadTime")));
					entity.setTamperCount(getInteger(obj.optString("TamperCount")));
					entity.setTransId(getString(obj.optString("TransID")));
					entity.setvB(getDouble(obj.optString("Vbn")));
					entity.setvR(getDouble(obj.optString("Vrn")));
					entity.setvY(getDouble(obj.optString("Vyn")));
					entity.setKvahImp(getDouble(obj.optString("KVAH")));			
					entity.setKvarhLag(getDouble(obj.optString("KVARH_Q1")));
					entity.setKvarhLead(getDouble(obj.optString("KVARH_Q4")));
					entity.setKwh(getDouble(obj.optString("KWH")));	
					entity.setV_ry_angle(getInteger(obj.optString("VoltAngRY")));
					entity.setV_rb_angle(getInteger(obj.optString("VoltAngRB")));
					entity.setV_yb_angle(getInteger(obj.optString("VoltAngYB")));
					entity.setNeutralCurrent(obj.optString("Neutral_Current"));
					entity.setBillingDate(getTimeStamp(obj.optString("Date_Last_Bill")));	
					
					if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
//					 System.out.println("UPDATED INSTATNTANEOUS=========== ");
					}		
					
				}

				try {
					updateCommunication(Imei, MeterNumber, "instjs");
					updateMasterMain(Imei, MeterNumber);
					OnDemandTransaction odt = new OnDemandTransaction();
					odt.setType("Instantaneous");
					odt.setTransID(transID);
					odt.setMeterNumber(meterNo);
					odt.setOndemTime(new Timestamp(new Date().getTime()));
					ods.customupdateBySchema(odt, "postgresMdas");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "Succ";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}

	@RequestMapping(value = "/MDASonDemandProfile/BILLING", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String billing(HttpServletRequest request) throws ParseException {

		String hesType = request.getParameter("hesType").trim();
		String meterNo = request.getParameter("meterId").trim();
		String fromdate = request.getParameter("fromdate").substring(0, 10);
		String todate = request.getParameter("todate").substring(0, 10);

//		System.out.println("hesType= " + hesType);
//		System.out.println("todate= " + todate);
//		System.out.println("fromdate= " + fromdate);
//		System.out.println("METER= " + meterNo);

		String s = hesc.onDemandProfile_TNEB("BILLING",hesType, meterNo, fromdate,todate);
//		String s = "";
		if (s == null) {
			return "NoData";
		} else {

			try {

				JSONArray recs = new JSONArray(s.toString());
				String imei = null, meterNumber = null;
				String transID=null;
				for (int i = 0; i < recs.length(); i++) {

					JSONObject obj = recs.getJSONObject(i);
		
					// Old Integration
//					transID = obj.optString("TransID");
//					String METER_ID = obj.optString("METER_ID");
//					meterNumber = obj.optString("meterNumber");
//					imei = obj.optString("IMEI");
//
//					if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//						try {
//							MasterMainEntity m = masterMainService.getFeederData(meterNumber).get(0);
//							if (m != null) {
//								imei = m.getModem_sl_no();
//							}
//						} catch (Exception e) {
//							e.getMessage();
//						}
//					}
//
//					String serverTime = obj.optString("serverTime");
//					String billingDate = obj.optString("billingDate");
//					String sys_PF_Billing = obj.optString("sys_PF_Billing");
//					String kWh = obj.optString("kWh");
//					String kWh_TZ1 = obj.optString("kWh_TZ1");
//					String kWh_TZ2 = obj.optString("kWh_TZ2");
//					String kWh_TZ3 = obj.optString("kWh_TZ3");
//					String kWh_TZ4 = obj.optString("kWh_TZ4");
//					String kWh_TZ5 = obj.optString("kWh_TZ5");
//					String kWh_TZ6 = obj.optString("kWh_TZ6");
//					String kWh_TZ7 = obj.optString("kWh_TZ7");
//					String kWh_TZ8 = obj.optString("kWh_TZ8");
//					String kvarhLag = obj.optString("kvarhLag");
//					String kvarhLead = obj.optString("kvarhLead");
//					String kVAh = obj.optString("kVAh");
//					String kVAh_TZ1 = obj.optString("kVAh_TZ1");
//					String kVAh_TZ2 = obj.optString("kVAh_TZ2");
//					String kVAh_TZ3 = obj.optString("kVAh_TZ3");
//					String kVAh_TZ4 = obj.optString("kVAh_TZ4");
//					String kVAh_TZ5 = obj.optString("kVAh_TZ5");
//					String kVAh_TZ6 = obj.optString("kVAh_TZ6");
//					String kVAh_TZ7 = obj.optString("kVAh_TZ7");
//					String kVAh_TZ8 = obj.optString("kVAh_TZ8");
//					String Demand_kW = obj.optString("Demand_kW");
//					String OccDate_kW = obj.optString("OccDate_kW");
//					String kW_TZ1 = obj.optString("kW_TZ1");
//					String Date_kW_TZ1 = obj.optString("Date_kW_TZ1");
//					String kW_TZ2 = obj.optString("kW_TZ2");
//					String Date_kW_TZ2 = obj.optString("Date_kW_TZ2");
//					String kW_TZ3 = obj.optString("kW_TZ3");
//					String Date_kW_TZ3 = obj.optString("Date_kW_TZ3");
//					String kW_TZ4 = obj.optString("kW_TZ4");
//					String Date_kW_TZ4 = obj.optString("Date_kW_TZ4");
//					String kW_TZ5 = obj.optString("kW_TZ5");
//					String Date_kW_TZ5 = obj.optString("Date_kW_TZ5");
//					String kW_TZ6 = obj.optString("kW_TZ6");
//					String Date_kW_TZ6 = obj.optString("Date_kW_TZ6");
//					String kW_TZ7 = obj.optString("kW_TZ7");
//					String Date_kW_TZ7 = obj.optString("Date_kW_TZ7");
//					String kW_TZ8 = obj.optString("kW_TZ8");
//					String Date_kW_TZ8 = obj.optString("Date_kW_TZ8");
//					String kVA = obj.optString("kVA");
//					String Date_kVA = obj.optString("Date_kVA");
//					String kVA_TZ1 = obj.optString("kVA_TZ1");
//					String Date_kVA_TZ1 = obj.optString("Date_kVA_TZ1");
//					String kVA_TZ2 = obj.optString("kVA_TZ2");
//					String Date_kVA_TZ2 = obj.optString("Date_kVA_TZ2");
//					String kVA_TZ3 = obj.optString("kVA_TZ3");
//					String Date_kVA_TZ3 = obj.optString("Date_kVA_TZ3");
//					String kVA_TZ4 = obj.optString("kVA_TZ4");
//					String Date_kVA_TZ4 = obj.optString("Date_kVA_TZ4");
//					String kVA_TZ5 = obj.optString("kVA_TZ5");
//					String Date_kVA_TZ5 = obj.optString("Date_kVA_TZ5");
//					String kVA_TZ6 = obj.optString("kVA_TZ6");
//					String Date_kVA_TZ6 = obj.optString("Date_kVA_TZ6");
//					String kVA_TZ7 = obj.optString("kVA_TZ7");
//					String Date_kVA_TZ7 = obj.optString("Date_kVA_TZ7");
//					String kVA_TZ8 = obj.optString("kVA_TZ8");
//					String Date_kVA_TZ8 = obj.optString("Date_kVA_TZ8");
//
//					// NEW
//					String ClientID = obj.optString("ClientID");
//					String date_kw_tz1 = obj.optString("date_kw_tz1");
//					String date_kw_tz2 = obj.optString("date_kw_tz2");
//					String date_kw_tz3 = obj.optString("date_kw_tz3");
//					String date_kw_tz4 = obj.optString("date_kw_tz4");
//					String date_kw_tz5 = obj.optString("date_kw_tz5");
//					String date_kw_tz6 = obj.optString("date_kw_tz6");
//					String date_kw_tz7 = obj.optString("date_kw_tz7");
//					String date_kw_tz8 = obj.optString("date_kw_tz8");
//					String bill_power_on_duration = obj.optString("bill_power_on_duration");
//					String bill_kwh_export = obj.optString("bill_kwh_export");
//
//					AmrBillsEntity entity = new AmrBillsEntity();
//					entity.setMyKey(new KeyBills(getString(meterNumber), getTimeStamp(billingDate)));
//					entity.setTimeStamp(new Timestamp(new Date().getTime()));
//					entity.setFlag("OD");
//					entity.setDateKva(getString(Date_kVA));
//					entity.setDateKvaTz1(getString(Date_kVA_TZ1));
//					entity.setDateKvaTz2(getString(Date_kVA_TZ2));
//					entity.setDateKvaTz3(getString(Date_kVA_TZ3));
//					entity.setDateKvaTz4(getString(Date_kVA_TZ4));
//					entity.setDateKvaTz5(getString(Date_kVA_TZ5));
//					entity.setDateKvaTz6(getString(Date_kVA_TZ6));
//					entity.setDateKvaTz7(getString(Date_kVA_TZ7));
//					entity.setDateKvaTz8(getString(Date_kVA_TZ8));
//					entity.setDateKwTz1(getString(Date_kW_TZ1));
//					entity.setDateKwTz2(getString(Date_kW_TZ2));
//					entity.setDateKwTz3(getString(Date_kW_TZ3));
//					entity.setDateKwTz4(getString(Date_kW_TZ4));
//					entity.setDateKwTz5(getString(Date_kW_TZ5));
//					entity.setDateKwTz6(getString(Date_kW_TZ6));
//					entity.setDateKwTz7(getString(Date_kW_TZ7));
//					entity.setDateKwTz8(getString(Date_kW_TZ8));
//					entity.setDemandKw(getDouble(Demand_kW));
//					entity.setImei(getString(imei));
//					entity.setKva(getDouble(kVA));
//					entity.setKvah(getDouble(kVAh));
//					entity.setKvahTz1(getDouble(kVAh_TZ1));
//					entity.setKvahTz2(getDouble(kVAh_TZ2));
//					entity.setKvahTz3(getDouble(kVAh_TZ3));
//					entity.setKvahTz4(getDouble(kVAh_TZ4));
//					entity.setKvahTz5(getDouble(kVAh_TZ5));
//					entity.setKvahTz6(getDouble(kVAh_TZ6));
//					entity.setKvahTz7(getDouble(kVAh_TZ7));
//					entity.setKvahTz8(getDouble(kVAh_TZ8));
//					entity.setKvarhLag(getDouble(kvarhLag));
//					entity.setKvarhLead(getDouble(kvarhLead));
//					entity.setKvaTz1(getDouble(kVA_TZ1));
//					entity.setKvaTz2(getDouble(kVA_TZ2));
//					entity.setKvaTz3(getDouble(kVA_TZ3));
//					entity.setKvaTz4(getDouble(kVA_TZ4));
//					entity.setKvaTz5(getDouble(kVA_TZ5));
//					entity.setKvaTz6(getDouble(kVA_TZ6));
//					entity.setKvaTz7(getDouble(kVA_TZ7));
//					entity.setKvaTz8(getDouble(kVA_TZ8));
//					entity.setKwh(getDouble(kWh));
//					entity.setKwhTz1(getDouble(kWh_TZ1));
//					entity.setKwhTz2(getDouble(kWh_TZ2));
//					entity.setKwhTz3(getDouble(kWh_TZ3));
//					entity.setKwhTz4(getDouble(kWh_TZ4));
//					entity.setKwhTz5(getDouble(kWh_TZ5));
//					entity.setKwhTz6(getDouble(kWh_TZ6));
//					entity.setKwhTz7(getDouble(kWh_TZ7));
//					entity.setKwhTz8(getDouble(kWh_TZ8));
//					entity.setMeterId(getString(METER_ID));
//					entity.setOccDateKw(getString(OccDate_kW));
//					entity.setServerTime(getTimeStamp(serverTime));
//					entity.setSysPfBilling(getDouble(sys_PF_Billing));
//					entity.setTransId(getString(transID));
//					entity.setKwTz1(getDouble(kW_TZ1));
//					entity.setKwTz2(getDouble(kW_TZ2));
//					entity.setKwTz3(getDouble(kW_TZ3));
//					entity.setKwTz4(getDouble(kW_TZ4));
//					entity.setKwTz5(getDouble(kW_TZ5));
//					entity.setKwTz6(getDouble(kW_TZ6));
//					entity.setKwTz7(getDouble(kW_TZ7));
//					entity.setKwTz8(getDouble(kW_TZ8));
//
//					if (amrBillsService.update(entity) instanceof AmrBillsEntity) {
////						 System.out.println("========================INSERTED BILL");
//					}
					
					//New Integration based on DLMS
					
					String ClientID = obj.optString("ClientID");
					transID = obj.optString("TransID");
					meterNumber = obj.optString("MeterNumber");
					imei = obj.optString("Imei");

					if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
						try {
							MasterMainEntity m = masterMainService.getFeederData(meterNumber).get(0);
							if (m != null) {
								imei = m.getModem_sl_no();
							}
						} catch (Exception e) {
							e.getMessage();
						}
					}
					String billingDate = obj.optString("BillingDate"); //Done
					String sys_PF_Billing = obj.optString("SYS_PF_Billing"); //Done
					
					String kWh = obj.optString("kWh"); //Done
					String kWh_TZ1 = obj.optString("kWh_TZ1"); //Done
					String kWh_TZ2 = obj.optString("kWh_TZ2"); //Done
					String kWh_TZ3 = obj.optString("kWh_TZ3"); //Done
					String kWh_TZ4 = obj.optString("kWh_TZ4"); //Done
					String kWh_TZ5 = obj.optString("kWh_TZ5"); //Done
					String kWh_TZ6 = obj.optString("kWh_TZ6"); //Done
					String kWh_TZ7 = obj.optString("kWh_TZ7"); //Done
					String kWh_TZ8 = obj.optString("kWh_TZ8"); //Done
					
					String kVAh = obj.optString("kVAh"); //Done
					String kVAh_TZ1 = obj.optString("kVAh_TZ1"); //Done
					String kVAh_TZ2 = obj.optString("kVAh_TZ2"); //Done
					String kVAh_TZ3 = obj.optString("kVAh_TZ3"); //Done
					String kVAh_TZ4 = obj.optString("kVAh_TZ4"); //Done
					String kVAh_TZ5 = obj.optString("kVAh_TZ5"); //Done
					String kVAh_TZ6 = obj.optString("kVAh_TZ6"); //Done
					String kVAh_TZ7 = obj.optString("kVAh_TZ7"); //Done
					String kVAh_TZ8 = obj.optString("kVAh_TZ8"); //Done
					
					String kW = obj.optString("kW"); 					//Done
					String Date_kW = obj.optString("Date_kW"); 			//Done
					String kW_TZ1 = obj.optString("kW_TZ1");			//Done
					String Date_kW_TZ1 = obj.optString("Date_kW_TZ1"); 	//Done
					String kW_TZ2 = obj.optString("kW_TZ2"); 			//Done
					String Date_kW_TZ2 = obj.optString("Date_kW_TZ2"); 	//Done
					String kW_TZ3 = obj.optString("kW_TZ3"); 			//Done
					String Date_kW_TZ3 = obj.optString("Date_kW_TZ3"); 	//Done
					String kW_TZ4 = obj.optString("kW_TZ4"); 			//Done
					String Date_kW_TZ4 = obj.optString("Date_kW_TZ4"); 	//Done
					String kW_TZ5 = obj.optString("kW_TZ5"); 			//Done
					String Date_kW_TZ5 = obj.optString("Date_kW_TZ5"); 	//Done
					String kW_TZ6 = obj.optString("kW_TZ6");			//Done
					String Date_kW_TZ6 = obj.optString("Date_kW_TZ6"); 	//Done
					String kW_TZ7 = obj.optString("kW_TZ7"); 			//Done
					String Date_kW_TZ7 = obj.optString("Date_kW_TZ7"); 	//Done
					String kW_TZ8 = obj.optString("kW_TZ8"); 		   	//Done
					String Date_kW_TZ8 = obj.optString("Date_kW_TZ8"); 	//Done
					
					
					String kVA = obj.optString("kVA");  				 //Done
					String Date_kVA = obj.optString("Date_kVA"); 		 //Done
					String kVA_TZ1 = obj.optString("kVA_TZ1"); 			 //Done
					String Date_kVA_TZ1 = obj.optString("Date_kVA_TZ1"); //Done
					String kVA_TZ2 = obj.optString("kVA_TZ2"); 			 //Done
					String Date_kVA_TZ2 = obj.optString("Date_kVA_TZ2"); //Done
					String kVA_TZ3 = obj.optString("kVA_TZ3"); 			 //Done
					String Date_kVA_TZ3 = obj.optString("Date_kVA_TZ3"); //Done
					String kVA_TZ4 = obj.optString("kVA_TZ4"); 			 //Done
					String Date_kVA_TZ4 = obj.optString("Date_kVA_TZ4"); //Done
					String kVA_TZ5 = obj.optString("kVA_TZ5"); 			 //Done
					String Date_kVA_TZ5 = obj.optString("Date_kVA_TZ5"); //Done
					String kVA_TZ6 = obj.optString("kVA_TZ6"); 			 //Done
					String Date_kVA_TZ6 = obj.optString("Date_kVA_TZ6"); //Done
					String kVA_TZ7 = obj.optString("kVA_TZ7"); 			 //Done
					String Date_kVA_TZ7 = obj.optString("Date_kVA_TZ7"); //Done
					String kVA_TZ8 = obj.optString("kVA_TZ8");			 //Done
					String Date_kVA_TZ8 = obj.optString("Date_kVA_TZ8"); //Done
					
					// NEW Parameter
					String Power_On_Duration = obj.optString("Power_On_Duration"); 	//Done
					String KWH_Exp = obj.optString("KWH_Exp"); 						//Done
					String KVAH_Exp = obj.optString("KVAH_Exp"); 					//Done
					
					
					String KVARH_Q1 = obj.optString("KVARH_Q1");		//Done
					String KVARH_Q2 = obj.optString("KVARH_Q2"); 
					String KVARH_Q3 = obj.optString("KVARH_Q3"); 							
					String KVARH_Q4 = obj.optString("KVARH_Q4"); 		//Done
					
					String kWh_Exp_TZ1 = obj.optString("kWh_Exp_TZ1"); //Done
					String kWh_Exp_TZ2 = obj.optString("kWh_Exp_TZ2"); //Done
					String kWh_Exp_TZ3 = obj.optString("kWh_Exp_TZ3"); //Done
					String kWh_Exp_TZ4 = obj.optString("kWh_Exp_TZ4"); //Done
					String kWh_Exp_TZ5 = obj.optString("kWh_Exp_TZ5"); //Done
					String kWh_Exp_TZ6 = obj.optString("kWh_Exp_TZ6"); //Done
					String kWh_Exp_TZ7 = obj.optString("kWh_Exp_TZ7"); //Done
					String kWh_Exp_TZ8 = obj.optString("kWh_Exp_TZ8"); //Done
					
					String kVAh_Exp_TZ1 = obj.optString("kVAh_Exp_TZ1"); //Done
					String kVAh_Exp_TZ2 = obj.optString("kVAh_Exp_TZ2"); //Done
					String kVAh_Exp_TZ3 = obj.optString("kVAh_Exp_TZ3"); //Done
					String kVAh_Exp_TZ4 = obj.optString("kVAh_Exp_TZ4"); //Done
					String kVAh_Exp_TZ5 = obj.optString("kVAh_Exp_TZ5"); //Done
					String kVAh_Exp_TZ6 = obj.optString("kVAh_Exp_TZ6"); //Done
					String kVAh_Exp_TZ7 = obj.optString("kVAh_Exp_TZ7"); //Done
					String kVAh_Exp_TZ8 = obj.optString("kVAh_Exp_TZ8"); //Done
					
					
					String kW_Exp = obj.optString("kW_Exp"); 			//Done 
					String kW_Exp_TZ1 = obj.optString("kW_Exp_TZ1"); 	//Done	 
					String kW_Exp_TZ2 = obj.optString("kW_Exp_TZ2"); 	//Done	 
					String kW_Exp_TZ3 = obj.optString("kW_Exp_TZ3");	//Done	 
					String kW_Exp_TZ4 = obj.optString("kW_Exp_TZ4"); 	//Done	 
					String kW_Exp_TZ5 = obj.optString("kW_Exp_TZ5");	//Done	 
					String kW_Exp_TZ6 = obj.optString("kW_Exp_TZ6");	//Done	 
					String kW_Exp_TZ7 = obj.optString("kW_Exp_TZ7"); 	//Done	 
					String kW_Exp_TZ8 = obj.optString("kW_Exp_TZ8"); 	//Done
					
					String Date_kW_Exp = obj.optString("Date_kW_Exp");			//Done
					String Date_kW_Exp_TZ1 = obj.optString("Date_kW_Exp_TZ1");	//Done
					String Date_kW_Exp_TZ2 = obj.optString("Date_kW_Exp_TZ2");	//Done
					String Date_kW_Exp_TZ3 = obj.optString("Date_kW_Exp_TZ3");	//Done
					String Date_kW_Exp_TZ4 = obj.optString("Date_kW_Exp_TZ4");	//Done
					String Date_kW_Exp_TZ5 = obj.optString("Date_kW_Exp_TZ5");	//Done
					String Date_kW_Exp_TZ6 = obj.optString("Date_kW_Exp_TZ6");	//Done
					String Date_kW_Exp_TZ7 = obj.optString("Date_kW_Exp_TZ7");	//Done
					String Date_kW_Exp_TZ8 = obj.optString("Date_kW_Exp_TZ8"); 	//Done
					
					String kVA_Exp = obj.optString("kVA_Exp"); 					//Done
					String kVA_Exp_TZ1 = obj.optString("kVA_Exp_TZ1");			//Done	
					String kVA_Exp_TZ2 = obj.optString("kVA_Exp_TZ2");			//Done
					String kVA_Exp_TZ3 = obj.optString("kVA_Exp_TZ3");			//Done
					String kVA_Exp_TZ4 = obj.optString("kVA_Exp_TZ4");			//Done
					String kVA_Exp_TZ5 = obj.optString("kVA_Exp_TZ5");			//Done	
					String kVA_Exp_TZ6 = obj.optString("kVA_Exp_TZ6");			//Done	
					String kVA_Exp_TZ7 = obj.optString("kVA_Exp_TZ7");			//Done	
					String kVA_Exp_TZ8 = obj.optString("kVA_Exp_TZ8");			//Done
					
					String Date_kVA_Exp = obj.optString("Date_kVA_Exp");			//Done
					String Date_kVA_Exp_TZ1 = obj.optString("Date_kVA_Exp_TZ1");	//Done
					String Date_kVA_Exp_TZ2 = obj.optString("Date_kVA_Exp_TZ2");	//Done
					String Date_kVA_Exp_TZ3 = obj.optString("Date_kVA_Exp_TZ3");	//Done
					String Date_kVA_Exp_TZ4 = obj.optString("Date_kVA_Exp_TZ4");	//Done
					String Date_kVA_Exp_TZ5 = obj.optString("Date_kVA_Exp_TZ5");	//Done
					String Date_kVA_Exp_TZ6 = obj.optString("Date_kVA_Exp_TZ6");	//Done
					String Date_kVA_Exp_TZ7 = obj.optString("Date_kVA_Exp_TZ7");	//Done
					String Date_kVA_Exp_TZ8 = obj.optString("Date_kVA_Exp_TZ8");	//Done
					
					String TamperCount = obj.optString("TamperCount");				//Done
					String serverTime = obj.optString("serverTime");				//Done
					
					AmrBillsEntity entity = new AmrBillsEntity();
					entity.setMyKey(new KeyBills(getString(meterNumber), getTimeStamp(billingDate)));
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setDateKva(getString(Date_kVA));
					entity.setFlag("OD");
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
					entity.setDemandKw(getDouble(kW));
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
					entity.setKvarhLag(getDouble(KVARH_Q1));
					entity.setKvarhLead(getDouble(KVARH_Q4));
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
					entity.setOccDateKw(getString(Date_kW));
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
							
					//New Parameter mapped
					entity.setBillPowerOnDuration(Power_On_Duration);
					entity.setBillKwhExport(KWH_Exp);
					entity.setBillKvahExport(KVAH_Exp);
					
					entity.setKwh_exp_tz1(getDouble(kWh_Exp_TZ1));
					entity.setKwh_exp_tz2(getDouble(kWh_Exp_TZ2));
					entity.setKwh_exp_tz3(getDouble(kWh_Exp_TZ3));
					entity.setKwh_exp_tz4(getDouble(kWh_Exp_TZ4));
					entity.setKwh_exp_tz5(getDouble(kWh_Exp_TZ5));
					entity.setKwh_exp_tz6(getDouble(kWh_Exp_TZ6));
					entity.setKwh_exp_tz7(getDouble(kWh_Exp_TZ7));
					entity.setKwh_exp_tz8(getDouble(kWh_Exp_TZ8));
					
					entity.setKvah_exp_tz1(getDouble(kVAh_Exp_TZ1));
					entity.setKvah_exp_tz2(getDouble(kVAh_Exp_TZ2));
					entity.setKvah_exp_tz3(getDouble(kVAh_Exp_TZ3));
					entity.setKvah_exp_tz4(getDouble(kVAh_Exp_TZ4));
					entity.setKvah_exp_tz5(getDouble(kVAh_Exp_TZ5));
					entity.setKvah_exp_tz6(getDouble(kVAh_Exp_TZ6));
					entity.setKvah_exp_tz7(getDouble(kVAh_Exp_TZ7));
					entity.setKvah_exp_tz8(getDouble(kVAh_Exp_TZ8));
					
					entity.setKw_exp(getDouble(kW_Exp));
					entity.setKw_exp_tz1(getDouble(kW_Exp_TZ1));
					entity.setKw_exp_tz2(getDouble(kW_Exp_TZ2));
					entity.setKw_exp_tz3(getDouble(kW_Exp_TZ3));
					entity.setKw_exp_tz4(getDouble(kW_Exp_TZ4));
					entity.setKw_exp_tz5(getDouble(kW_Exp_TZ5));
					entity.setKw_exp_tz6(getDouble(kW_Exp_TZ6));
					entity.setKw_exp_tz7(getDouble(kW_Exp_TZ7));		
					entity.setKw_exp_tz8(getDouble(kW_Exp_TZ8));
					
					entity.setDate_kw_exp(getString(Date_kW_Exp));
					entity.setDate_kw_exp_tz1(getString(Date_kW_Exp_TZ1));
					entity.setDate_kw_exp_tz2(getString(Date_kW_Exp_TZ2));
					entity.setDate_kw_exp_tz3(getString(Date_kW_Exp_TZ3));
					entity.setDate_kw_exp_tz4(getString(Date_kW_Exp_TZ4));
					entity.setDate_kw_exp_tz5(getString(Date_kW_Exp_TZ5));
					entity.setDate_kw_exp_tz6(getString(Date_kW_Exp_TZ6));
					entity.setDate_kw_exp_tz7(getString(Date_kW_Exp_TZ7));
					entity.setDate_kw_exp_tz8(getString(Date_kW_Exp_TZ8));	
					entity.setKva_exp(getDouble(kVA_Exp));
					entity.setKva_exp_tz1(getDouble(kVA_Exp_TZ1));
					entity.setKva_exp_tz2(getDouble(kVA_Exp_TZ2));
					entity.setKva_exp_tz3(getDouble(kVA_Exp_TZ3));
					entity.setKva_exp_tz4(getDouble(kVA_Exp_TZ4));
					entity.setKva_exp_tz5(getDouble(kVA_Exp_TZ5));
					entity.setKva_exp_tz6(getDouble(kVA_Exp_TZ6));
					entity.setKva_exp_tz7(getDouble(kVA_Exp_TZ7));		
					entity.setKva_exp_tz8(getDouble(kVA_Exp_TZ8));
					entity.setDate_kva_exp(getString(Date_kVA_Exp));
					entity.setDate_kva_exp_tz1(getString(Date_kVA_Exp_TZ1));
					entity.setDate_kva_exp_tz2(getString(Date_kVA_Exp_TZ2));
					entity.setDate_kva_exp_tz3(getString(Date_kVA_Exp_TZ3));
					entity.setDate_kva_exp_tz4(getString(Date_kVA_Exp_TZ4));
					entity.setDate_kva_exp_tz5(getString(Date_kVA_Exp_TZ5));
					entity.setDate_kva_exp_tz6(getString(Date_kVA_Exp_TZ6));
					entity.setDate_kva_exp_tz7(getString(Date_kVA_Exp_TZ7));
					entity.setDate_kva_exp_tz8(getString(Date_kVA_Exp_TZ8));			
					entity.setTamper_count(getString(TamperCount));	
					entity.setReactiveImpActiveImp(getString(KVARH_Q1));
					entity.setReactiveExpActiveImp(getString(KVARH_Q2));
					entity.setReactiveExpActiveExp(getString(KVARH_Q3));
					entity.setReactiveImpActiveExp(getString(KVARH_Q4));
					
					if (amrBillsService.update(entity) instanceof AmrBillsEntity) {

					}
					
					
				}

				try {
					updateCommunication(imei, meterNumber, "billjs");
					updateMasterMain(imei, meterNumber);
					OnDemandTransaction odt = new OnDemandTransaction();
					odt.setType("Billing");
					odt.setTransID(transID);
					odt.setMeterNumber(meterNo);
					odt.setOndemTime(new Timestamp(new Date().getTime()));
					ods.customupdateBySchema(odt, "postgresMdas");
				} catch (Exception e) {
					// TODO: handle exception
				}

				return "Succ";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}

	@RequestMapping(value = "/MDASonDemandProfile/BULKLOAD", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String bulkload(HttpServletRequest request) throws ParseException {

		String hesType = request.getParameter("hesType").trim();
		String meterNo = request.getParameter("meterId").trim();
		String fromdate = request.getParameter("fromdate").substring(0, 10);
		String todate = request.getParameter("todate").substring(0, 10);

//		System.out.println("hesType= " + hesType);
//		System.out.println("todate= " + todate);
//		System.out.println("fromdate= " + fromdate);
//		System.out.println("METER= " + meterNo);

		String s = hesc.onDemandProfile_TNEB("BULKLOAD",hesType, meterNo, fromdate,todate);
		if (s == null) {
			return "NoData";
		} else {

			try {
				String imei = null, meterNumber = null;
				String transID=null;
				JSONArray recs = new JSONArray(s.toString());
				for (int i = 0; i < recs.length(); i++) {
					JSONObject obj = recs.getJSONObject(i);
					
					
//					transID = obj.optString("TransID");
//					String structureSize = obj.optString("structureSize");
//					String readTime = obj.optString("TimeStamp");
//					String KWH_Exp = obj.optString("KWH_Exp");
//					String KWH_Imp = obj.optString("KWH_Imp");
//					String ModemTime = obj.optString("ModemTime");
//					meterNumber = obj.optString("MeterNumber");
//					imei = obj.optString("Imei");
//
//					if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//						try {
//							MasterMainEntity m = masterMainService.getFeederData(meterNumber).get(0);
//							if (m != null) {
//								imei = m.getModem_sl_no();
//							}
//						} catch (Exception e) {
//							e.getMessage();
//						}
//					}
//
//					String Ir = obj.optString("Ir");
//					String Iy = obj.optString("Iy");
//					String Ib = obj.optString("Ib");
//					String Vr = obj.optString("Vr");
//					String Vy = obj.optString("Vy");
//					String Vb = obj.optString("Vb");
//					String KWH = obj.optString("KWH");
//					String KVAH = obj.optString("KVAH");
//					String KVARH_Q1 = obj.optString("KVARH_Q1");
//					String KVARH_Q3 = obj.optString("KVARH_Q3");
//					String KVARH_Q4 = obj.optString("KVARH_Q4");
//					String KVARH_Q2 = obj.optString("KVARH_Q2");
//					String KVARH_Lag = obj.optString("KVARH_Lag");
//					String KVARH_Lead = obj.optString("KVARH_Lead");
//					String Frequnecy = obj.optString("Frequnecy");
//					String NetKWH = obj.optString("NetKWH");
//					String pf = obj.optString("power_factor");
//					
//
//					// NEW
//					String ClientID = obj.optString("ClientID");
//					String Vry = obj.optString("Vry");
//					String Vby = obj.optString("Vby");
//					String kW = obj.optString("kW");
//					String kVA = obj.optString("kVA");
//					String kVArLag = obj.optString("kVArLag");
//					String kVarLead = obj.optString("kVarLead");
//					String neutral_current = obj.optString("neutral_current");
//					String pf_threephase = obj.optString("pf_threephase");
//					String average_voltage = obj.optString("average_voltage");
//					String average_current = obj.optString("average_current");
//					String netkwh = obj.optString("netkwh");
//					
//					System.out.println("Data ClientID: "+ClientID+" Vry: "+Vry+" Vby: "+Vby+"Data transID: "+transID+" KWH_Exp: "+KWH_Exp+" readTime: "+readTime+" meterNumber: "+meterNumber);
//
//					AmrLoadEntity entity = new AmrLoadEntity();
//					entity.setTimeStamp(new Timestamp(new Date().getTime()));
//					entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(readTime)));
//					entity.setFlag("OD");
//					entity.setFrequency(getDouble(Frequnecy));
//					entity.setiB(getDouble(Ib));
//					entity.setImei(getString(imei));
//					entity.setiR(getDouble(Ir));
//					entity.setiY(getDouble(Iy));
//					entity.setKvah(getDouble(KVAH));
//					entity.setKvarhLag(getDouble(KVARH_Lag));
//					entity.setKvarhLead(getDouble(KVARH_Lead));
//					entity.setKvarhQ1(getDouble(KVARH_Q1));
//					entity.setKvarhQ2(getDouble(KVARH_Q2));
//					entity.setKvarhQ3(getDouble(KVARH_Q3));
//					entity.setKvarhQ4(getDouble(KVARH_Q4));
//					entity.setKwh(getDouble(KWH));
//					entity.setKwhExp(getDouble(KWH_Exp));
//					entity.setKwhImp(getDouble(KWH_Imp));
//					entity.setModemTime(getTimeStamp(ModemTime));
//					entity.setNetKwh(getDouble(NetKWH));
//					entity.setStructureSize(getInteger(structureSize));
//					entity.setTransId(getString(transID));
//					entity.setvB(getDouble(Vb));
//					entity.setvR(getDouble(Vr));
//					entity.setvY(getDouble(Vy));
//					entity.setPowerFactor(pf == null ? null : pf.equals("") ? null : Double.parseDouble(pf));
//					if (amrLoadService.update(entity) instanceof AmrLoadEntity) {
////						 System.out.println("========================INSERTED LOAD");
//					}
					
					
					//New Integration based on DLMS
					transID = obj.optString("TransID");
					meterNumber = obj.optString("MeterNumber");
					imei = obj.optString("Imei");	
					
					// Below parameter is not mapped into table
					String Status_Code = obj.optString("Status_Code");
					String Vry = obj.optString("Vry"); 
					String Vby = obj.optString("Vby"); 
					//Data Insertion into Raw table
					AmrLoadRawEntity rawEntity = new AmrLoadRawEntity();
					rawEntity.setTimeStamp(new Timestamp(new Date().getTime()));
					rawEntity.setMyKey(new KeyRawLoad(getString(meterNumber), getTimeStamp(obj.optString("ReadTime"))));
					rawEntity.setFlag("OD");
					rawEntity.setiR(getDouble(obj.optString("Ir")));
					rawEntity.setiY(getDouble(obj.optString("Iy")));
					rawEntity.setiB(getDouble(obj.optString("Ib")));	
					rawEntity.setvR(getDouble(obj.optString("Vrn")));
					rawEntity.setvY(getDouble(obj.optString("Vyn")));
					rawEntity.setvB(getDouble(obj.optString("Vbn")));
					rawEntity.setKwh(getDouble(obj.optString("KWH")));
					rawEntity.setKwhImp(getDouble(obj.optString("KWH")));
					rawEntity.setKwhExp(getDouble(obj.optString("KWH_Exp")));			
					rawEntity.setKvarhQ1(getDouble(obj.optString("KVARH_Q1")));
					rawEntity.setKvarhQ2(getDouble(obj.optString("KVARH_Q2")));
					rawEntity.setKvarhQ3(getDouble(obj.optString("KVARH_Q3")));
					rawEntity.setKvarhQ4(getDouble(obj.optString("KVARH_Q4")));	
					rawEntity.setKvarhLag(getDouble(obj.optString("KVARH_Q1")));
					rawEntity.setKvarhLead(getDouble(obj.optString("KVARH_Q4")));				
					rawEntity.setKvah(getDouble(obj.optString("KVAH")));
					rawEntity.setKvahImp(getDouble(obj.optString("KVAH")));
					rawEntity.setKvahExp(getDouble(obj.optString("KVAH_Exp")));		
					rawEntity.setNeutralCurrent(obj.optString("Neutral_Current"));
					if(!obj.optString("ModemTime").isEmpty()) {
						rawEntity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
					}
					rawEntity.setImei(getString(imei));
					rawEntity.setFrequency(getDouble(obj.optString("Frequnecy")));
					rawEntity.setPowerFactor(getDouble(obj.optString("Power_Factor")));				
					rawEntity.setKw(getDouble(obj.optString("kW")));
					rawEntity.setKva(getDouble(obj.optString("kVA")));
					rawEntity.setAverageCurrent(obj.optString("average_current"));
					rawEntity.setAverageVoltage(obj.optString("average_voltage"));
					rawEntity.setTransId(getString(obj.optString("TransID")));
//					rawEntity.setNetKwh(getDouble(obj.optString("NetKWH")));
//					rawEntity.setStructureSize(getInteger(obj.optString("structureSize")));			
					if (amrLoadRawService.update(rawEntity) instanceof AmrLoadRawEntity) {
//						 System.out.println("========================INSERTED LOAD");
					}
						
					//Data Insertion into Main table
					AmrLoadEntity entity = new AmrLoadEntity();
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(obj.optString("ReadTime"))));
					entity.setFlag("OD");
					entity.setiR(getDouble(obj.optString("Ir")));
					entity.setiY(getDouble(obj.optString("Iy")));
					entity.setiB(getDouble(obj.optString("Ib")));	
					entity.setvR(getDouble(obj.optString("Vrn")));
					entity.setvY(getDouble(obj.optString("Vyn")));
					entity.setvB(getDouble(obj.optString("Vbn")));
					entity.setKwh(getDouble(obj.optString("KWH")));
					entity.setKwhImp(getDouble(obj.optString("KWH")));
					entity.setKwhExp(getDouble(obj.optString("KWH_Exp")));			
					entity.setKvarhQ1(getDouble(obj.optString("KVARH_Q1")));
					entity.setKvarhQ2(getDouble(obj.optString("KVARH_Q2")));
					entity.setKvarhQ3(getDouble(obj.optString("KVARH_Q3")));
					entity.setKvarhQ4(getDouble(obj.optString("KVARH_Q4")));	
					entity.setKvarhLag(getDouble(obj.optString("KVARH_Q1")));
					entity.setKvarhLead(getDouble(obj.optString("KVARH_Q4")));				
					entity.setKvah(getDouble(obj.optString("KVAH")));
					entity.setKvahImp(getDouble(obj.optString("KVAH")));
					entity.setKvahExp(getDouble(obj.optString("KVAH_Exp")));		
					entity.setNeutralCurrent(obj.optString("Neutral_Current"));
					if(!obj.optString("ModemTime").isEmpty()) {
						entity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
					}
					entity.setImei(getString(imei));
					entity.setFrequency(getDouble(obj.optString("Frequnecy")));
					entity.setPowerFactor(getDouble(obj.optString("Power_Factor")));
					entity.setKw(getDouble(obj.optString("kW")));
					entity.setKva(getDouble(obj.optString("kVA")));
					entity.setAverageCurrent(obj.optString("average_current"));
					entity.setAverageVoltage(obj.optString("average_voltage"));
					entity.setTransId(getString(obj.optString("TransID")));
					if (amrLoadService.update(entity) instanceof AmrLoadEntity) {
//						 System.out.println("========================INSERTED LOAD");
					}
					

				}

				try {
					updateCommunication(imei, meterNumber, "loadjs");
					updateMasterMain(imei, meterNumber);
					OnDemandTransaction odt = new OnDemandTransaction();
					odt.setType("Bulk Load");
					odt.setTransID(transID);
					odt.setMeterNumber(meterNo);
					odt.setOndemTime(new Timestamp(new Date().getTime()));
					ods.customupdateBySchema(odt, "postgresMdas");
				} catch (Exception e) {
					e.printStackTrace();
				}

				return "Succ";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}

	@RequestMapping(value = "/MDASonDemandProfile/EVENTLOGS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String eventlogs(HttpServletRequest request) throws ParseException {

		String hesType = request.getParameter("hesType").trim();
		String meterNo = request.getParameter("meterId").trim();
		String fromdate = request.getParameter("fromdate").substring(0, 10);
		String todate = request.getParameter("todate").substring(0, 10);

//		System.out.println("hesType= " + hesType);
//		System.out.println("todate= " + todate);
//		System.out.println("fromdate= " + fromdate);
//		System.out.println("METER= " + meterNo);

		String s = hesc.onDemandProfile_TNEB("EVENTLOGS",hesType, meterNo, fromdate,todate);
//		String s = "";
		if (s == null) {
			return "NoData";
		} else {

			try {

				String imei = null, meterNumber = null;
				String transID=null;
				JSONArray recs = new JSONArray(s.toString());
				for (int i = 0; i < recs.length(); i++) {
					JSONObject obj = recs.getJSONObject(i);

					//Old Integration
//					imei = obj.optString("Imei");
//
//					if (imei == null || "null".equalsIgnoreCase(imei) || "".equalsIgnoreCase(imei)) {
//						try {
//							MasterMainEntity m = masterMainService.getFeederData(meterNumber).get(0);
//							if (m != null) {
//								imei = m.getModem_sl_no();
//							}
//						} catch (Exception e) {
//							e.getMessage();
//						}
//					}
//
//					String eventTime = obj.optString("TimeStamp");
//					String ModemTime = obj.optString("ModemTime");
//					String EventCode = obj.optString("EventCode");
//					String Ir = obj.optString("Ir");
//					String Iy = obj.optString("Iy");
//					String Ib = obj.optString("Ib");
//					String Vr = obj.optString("Vr");
//					String Vy = obj.optString("Vy");
//					String Vb = obj.optString("Vb");
//					String PFr = obj.optString("PFr");
//					String PFy = obj.optString("PFy");
//					String PFb = obj.optString("PFb");
//					String Energy_KWH = obj.optString("Energy_KWH");
//					String Energy_KWH_Import = obj.optString("Energy_KWH_Import");
//					String Energy_KWH_Export = obj.optString("Energy_KWH_Export");
//					String Energy_KVAH = obj.optString("Energy_KVAH");
//					transID = obj.optString("TransID");
//					String StructureSize = obj.optString("StructureSize");
//					String KWH_Export = obj.optString("KWH_Export");
//					String KWH_Import = obj.optString("KWH_Import");
//					meterNumber = obj.optString("meterNumber");
//					String KWH = obj.optString("KWH");
//					String KVAH = obj.optString("KVAH");
//
//					// NEW
//					String ClientID = obj.optString("ClientID");
//					String Vry = obj.optString("Vry");
//					String Vby = obj.optString("Vby");
//
//					AmrEventsEntity entity = new AmrEventsEntity();
//					entity.setMyKey(
//							new KeyEvent(getString(meterNumber), getString(EventCode), getTimeStamp(eventTime)));
//					entity.setTimeStamp(new Timestamp(new Date().getTime()));
//					entity.setFlag("OD");
//					entity.setEnergyKvah(getDouble(Energy_KVAH));
//					entity.setEnergyKwh(getDouble(Energy_KWH));
//					entity.setEnergyKwhExport(getDouble(Energy_KWH_Export));
//					entity.setEnergyKwhImport(getDouble(Energy_KWH_Import));
//
//					entity.setiB(getDouble(Ib));
//					entity.setImei(getString(imei));
//					entity.setiR(getDouble(Ir));
//					entity.setiY(getDouble(Iy));
//					entity.setKvah(getDouble(KVAH));
//					entity.setKwh(getDouble(KWH));
//					entity.setKwhExp(getDouble(KWH_Export));
//					entity.setKwhImp(getDouble(KWH_Import));
//					if (ModemTime == null || "null".equalsIgnoreCase(ModemTime) || "".equalsIgnoreCase(ModemTime)) {	
//						entity.setModemTime(getTimeStamp(ModemTime));
//						System.out.println("ModemTime NULL= "+ModemTime);
//					}else {
//						System.out.println("ModemTime Not NULL= "+ModemTime);
//					}
//						
//					entity.setPfB(getDouble(PFb));
//					entity.setPfR(getDouble(PFr));
//					entity.setPfY(getDouble(PFy));
//					entity.setStructureSize(getInteger(StructureSize));
//					entity.setTransId(getString(transID));
//					entity.setvB(getDouble(Vb));
//					entity.setvR(getDouble(Vr));
//					entity.setvY(getDouble(Vy));
//
//					if (amrEventsService.update(entity) instanceof AmrEventsEntity) {
////						 System.out.println("========================INSERTED EVENT");
//					}
					
					
					//New Integration based on DLMS paramneter
					imei = obj.optString("Imei");
					meterNumber = obj.optString("MeterNumber");
					transID = obj.optString("TransID");
					
					// Below parameter is not mapped into table
					String Status_Code = obj.optString("TamperCount");
					String EventLog_Seq_No = obj.optString("EventLog_Seq_No");
					String Vry = obj.optString("Vry"); 
					String Vby = obj.optString("Vby");
					String ClientID = obj.optString("ClientID");

					AmrEventsEntity entity = new AmrEventsEntity();
					entity.setMyKey(new KeyEvent(getString(meterNumber), getString(obj.optString("EventCode")), getTimeStamp(obj.optString("EventTime"))));
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setFlag("OD");
					entity.setiR(getDouble(obj.optString("Ir")));
					entity.setiY(getDouble(obj.optString("Iy")));
					entity.setiB(getDouble(obj.optString("Ib")));
					entity.setvR(getDouble(obj.optString("Vrn")));
					entity.setvY(getDouble(obj.optString("Vyn")));
					entity.setvB(getDouble(obj.optString("Vbn")));
					entity.setPfR(getDouble(obj.optString("PFr")));
					entity.setPfY(getDouble(obj.optString("PFy")));
					entity.setPfB(getDouble(obj.optString("PFb")));
					entity.setKwh(getDouble(obj.optString("KWH")));
					entity.setKwhImp(getDouble(obj.optString("KWH")));
					entity.setKwhExp(getDouble(obj.optString("KWH_Export")));	
					entity.setImei(getString(imei));
					if(!obj.optString("ModemTime").isEmpty()) {
						entity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
					}
					entity.setTransId(getString(obj.optString("TransID")));
							
					try {
						if (amrEventsService.update(entity) instanceof AmrEventsEntity) {
//							 System.out.println("========================INSERTED EVENT Count= "+ i++);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}


				}

				try {
					updateCommunication(imei, meterNumber, "evntjs");
					updateMasterMain(imei, meterNumber);
					OnDemandTransaction odt = new OnDemandTransaction();
					odt.setType("Event Logs");
					odt.setTransID(transID);
					odt.setMeterNumber(meterNo);
					odt.setOndemTime(new Timestamp(new Date().getTime()));
					ods.customupdateBySchema(odt, "postgresMdas");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

				return "Succ";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
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

	public void updateCommunication(String imei, String meterNumber, String topic) {

		try {
			Timestamp time = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = format.parse(format.format(new Date()));

			ModemCommunication modem = modemCommunication.find((new KeyCommunication(meterNumber, today)));

			if (null == modem) {
				modem = new ModemCommunication();
				modem.setMyKey(new KeyCommunication(meterNumber, today));
				modem.setImei(imei);
				modem.setTotalCommunication(Long.valueOf(0));
				modem.setTotalBill(Long.valueOf(0));
				modem.setTotalEvent(Long.valueOf(0));
				modem.setTotalInst(Long.valueOf(0));
				modem.setTotalLoad(Long.valueOf(0));

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
			case Subscriber.TOPIC_IDEN:

				break;
			}

			if (modemCommunication.update(modem) instanceof ModemCommunication) {
				// System.out.println("========================UPDATED MODEM COMMUNICATION");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateMasterMain(String imei, String meterNumber) {
		try {

			MasterMainEntity mainEntity = masterMainService.getEntityByImeiNoAndMtrNO(imei, meterNumber);
			if (mainEntity != null) {
				/* mainEntity.setMtrmake(mtrMake); */
				mainEntity.setLast_communicated_date(new Date());
				masterMainService.update(mainEntity);
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
