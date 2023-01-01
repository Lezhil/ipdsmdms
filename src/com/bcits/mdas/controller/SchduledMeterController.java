package com.bcits.mdas.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.LoadSurveyEstimated;
import com.bcits.entity.TodDefinitionEntity;
import com.bcits.firebase.FireMessageCordova;
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
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.MeterAlarmsEntity;
import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.entity.SchduledMeterTrackEntity;
import com.bcits.mdas.jsontoobject.MeterAlarms;
import com.bcits.mdas.jsontoobject.RegisterValues;
import com.bcits.mdas.jsontoobject.SchMetMain;
import com.bcits.mdas.mqtt.UpdateCommunicationNew;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrDailyLoadService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.AndroidService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterAlarmsService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.OnDemandServiceInst;
import com.bcits.mdas.service.OnDemandServicenamepla;
import com.bcits.mdas.service.SchduledMeterService;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.LoadEstimationService;
import com.bcits.service.Tod_definitionService;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class SchduledMeterController {
	@Autowired
	HESController hc;
	@Autowired
	AndroidService androidService;
	@Autowired
	SchduledMeterService sms;
	@Autowired
	OnDemandServiceInst odsi;
	@Autowired
	OnDemandServicenamepla odp;
	@Autowired
	AmrEventsService oCurEvt;
	
	@Autowired
	private LoadEstimationService loadEstimationService;

	@Autowired
	public AmrBillsService amrBillsService;

	@Autowired
	public AmrLoadService amrLoadService;

	@Autowired
	private ModemCommunicationService modemCommunication;

	@Autowired
	private MeterAlarmsService mas;

	@Autowired
	private MasterMainService masterMainService;
	@Autowired
	private AmrDailyLoadService adls;
	@Autowired
	private Tod_definitionService tds;

	/*
	 * @Scheduled(cron = "20 26 10 * * ?") public void test1() throws
	 * ParseException{ smc(2310298); }
	 */
	Long count = 0L;




	//@Scheduled(cron = "0 0/5 * * * ?")
	public void smc() throws ParseException {
		/*
		 * public void smc(int i) throws ParseException { int j=i; String s =
		 * sms.meterSamples(10000, i);
		 */
		try {
			count = (long) sms.highcount();
			if (count != null) {
				System.out.println("Total count is :" + count);
				count++;
			} else {
				count = 1L;
			}
		} catch (NullPointerException e) {
			count = 1L;
		}
		/* int i=sms.highcount(); */

		String s = sms.meterSamples(10000, count);
		System.out.println(s.length());

		if (s.equalsIgnoreCase("[]")) {

		} else {
			try {
				parseData(s);
				System.out.println("Finished upto count :"+(count+3000));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				//writeDataToFile(s);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	private void parseData(String s) throws ParseException {
		Gson gson = new Gson();
		/* type1 */
		// SchMetMain[] navigationArray = gson.fromJson(s, SchMetMain[].class);
		/* type2 */
		@SuppressWarnings("serial")
		Type collectionType = new TypeToken<List<SchMetMain>>() {
		}.getType();
		List<SchMetMain> smm = gson.fromJson(s, collectionType);
		for (SchMetMain scMM : smm) {
			SchduledMeterTrackEntity smte = new SchduledMeterTrackEntity();
			// billing
			if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"1.0.98.1.0.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Billing");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					billingProfile(scMM);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// bulk load
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"1.0.99.1.0.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Bulk Load");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					bulkLoadProfile(scMM);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// daily load
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"1.0.99.2.0.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Daily Load");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					dailyLoadProfile(scMM);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// power event log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.2.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Power Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					powerEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// controled event log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.6.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Controled Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					controledEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// voltage event log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.0.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Voltage Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					voltageEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// current event log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.1.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Current Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					currentEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// other event log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.4.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Other Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					otherEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// instantaneous
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"1.0.94.91.0.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Instantaneous");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					instantaneous(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Name plate
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.94.91.10.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Name Plate");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					namePlateProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}	

			}
			// Transaction Event Log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.3.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Transaction Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					transactionEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Non Roll Over Event Log
			else if (scMM.getFormattedProfileObisCode().equalsIgnoreCase(
					"0.0.99.98.5.255")) {
				smte.setSeqId(Integer.parseInt(scMM.getMeterSampleId()));
				smte.setType("Non Roll Over Event Log");
				smte.setMeterNumber(scMM.getDeviceId());
				try {
					nonRollOverEventLogProfile(scMM);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// j=smte.getSeqId();
			sms.customsaveBySchema(smte, "postgresMdas");

			// ////System.out.println(i);
		}
		// smc(j);
	}

	@Async
	public void instantaneous(SchMetMain scMM) throws ParseException {

		AMIInstantaneousEntity entity = new AMIInstantaneousEntity();

		entity.setTimeStamp(new Timestamp(new Date().getTime()));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		entity.setFlag(scMM.getMeterSampleId());
		String meterId = scMM.getDeviceId();
		RegisterValues[] rv = scMM.getRegisterValues();

		// ////System.out.println("#########"+mmEntity.getMtrno());
		for (int r = 0; r < rv.length; r++) {

			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();
			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {

				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				entity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));
				entity.setMyKey(new AMIKeyInstantaneous(scMM.getDeviceId(),
						new Timestamp(format.parse(s1[0]).getTime())));
			}
			if (obc.equalsIgnoreCase("1.0.31.7.0.255")) {
				entity.setiR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));

			} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) {
				entity.setiY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));

			} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) {
				entity.setiB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) {
				entity.setvR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) {
				entity.setvY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) {
				entity.setvB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) {
				entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) {
				entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) {
				entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.13.7.0.255")) {
				entity.setPfThreephase(Double.parseDouble(rv[r]
						.getFormattedValue()));

			} else if (obc.equalsIgnoreCase("1.0.14.7.0.255")) {
				entity.setFrequency(Double.parseDouble(rv[r]
						.getFormattedValue().replace("Hz", "")));
			} else if (obc.equalsIgnoreCase("1.0.9.7.0.255")) {
				entity.setKva(Double.parseDouble(rv[r].getFormattedValue()
						.replace("VA", "")));
			} else if (obc.equalsIgnoreCase("1.0.1.7.0.255")) {
				entity.setSignedActivePower(Double.parseDouble(rv[r]
						.getFormattedValue().replace("W", "")));
			} else if (obc.equalsIgnoreCase("1.0.3.7.0.255")) {
				entity.setSignedReactivePower(Double.parseDouble(rv[r]
						.getFormattedValue().replace("var", "")));
			} else if (obc.equalsIgnoreCase("0.0.96.7.0.255")) {
				entity.setPowerOffCount(Integer.parseInt(rv[r]
						.getFormattedValue()));
			} else if (obc.equalsIgnoreCase("0.0.94.91.8.255")) {
				entity.setPowerOffDuration(Integer.parseInt(rv[r]
						.getFormattedValue().replace("min.", "").trim()));
			} else if (obc.equalsIgnoreCase("0.0.94.91.0.255")) {
				entity.setTamperCount(Integer.parseInt(rv[r]
						.getFormattedValue()));
			} else if (obc.equalsIgnoreCase("0.0.0.1.0.255")) {
				entity.setBillingCount(Integer.parseInt(rv[r]
						.getFormattedValue()));
			} else if (obc.equalsIgnoreCase("0.0.96.2.0.255")) {
				entity.setProgrammingCount(Integer.parseInt(rv[r]
						.getFormattedValue()));
			} else if (obc.equalsIgnoreCase("0.0.0.1.2.255")) {
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				if (s1[0].equalsIgnoreCase("")) {

				} else {
					entity.setBillingDate(new Timestamp(format.parse(s1[0])
							.getTime()));
				}

			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
				entity.setKwh(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {
				entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			} else if (obc.equalsIgnoreCase("1.0.5.8.0.255")) {
				entity.setKvarhLag(Double.parseDouble(rv[r].getFormattedValue()
						.replace("varh", "")));
			} else if (obc.equalsIgnoreCase("1.0.6.8.0.255")) {
				entity.setReactiveImpActiveExp(Double.parseDouble(rv[r]
						.getFormattedValue().replace("varh", "")));
			} else if (obc.equalsIgnoreCase("1.0.7.8.0.255")) {
				entity.setReactiveExpActiveExp(Double.parseDouble(rv[r]
						.getFormattedValue().replace("varh", "")));
			} else if (obc.equalsIgnoreCase("1.0.8.8.0.255")) {
				entity.setKvarhLead(Double.parseDouble(rv[r]
						.getFormattedValue().replace("varh", "")));
			} else if (obc.equalsIgnoreCase("1.0.9.8.0.255")) {
				entity.setKvah(Double.parseDouble(rv[r].getFormattedValue()
						.replace("VAh", "")));
			} else if (obc.equalsIgnoreCase("1.0.10.8.0.255")) {
				entity.setKvahExp(Double.parseDouble(rv[r].getFormattedValue()
						.replace("VAh", "")));
			} else if (obc.equalsIgnoreCase("1.0.1.6.0.255")
					&& atr.equalsIgnoreCase("2")) {
				entity.setMdKw(Double.parseDouble(rv[r].getFormattedValue()
						.replace("W", "")));
			} else if (obc.equalsIgnoreCase("1.0.1.6.0.255")
					&& atr.equalsIgnoreCase("5")) {
				entity.setDateMdKw(rv[r].getFormattedValue());
			} else if (obc.equalsIgnoreCase("1.0.9.6.0.255")
					&& atr.equalsIgnoreCase("2")) {
				entity.setMdKva(Double.parseDouble(rv[r].getFormattedValue()
						.replace("VA", "")));
			} else if (obc.equalsIgnoreCase("1.0.9.6.0.255")
					&& atr.equalsIgnoreCase("5")) {
				entity.setDateMdKva(rv[r].getFormattedValue());
			} else if (obc.equalsIgnoreCase("1.0.12.7.0.255")) {
				entity.setPowerVoltage(rv[r].getFormattedValue().replace("V",
						""));
			} else if (obc.equalsIgnoreCase("1.0.11.7.0.255")) {
				entity.setPhaseCurrent(rv[r].getFormattedValue().replace("A",
						""));
			} else if (obc.equalsIgnoreCase("1.0.91.7.0.255")) {
				entity.setNeutralCurrent(rv[r].getFormattedValue().replace("A",
						""));
			} else if (obc.equalsIgnoreCase("0.0.94.91.14.255")) {
				entity.setTotalPowerOnDuration(Integer.parseInt(rv[r]
						.getFormattedValue().replace(" min.", "")));
			}

		}
		// //System.out.println(entity);
		// odsi.customsaveBySchema(entity, "postgresMdas");
		try {
			odsi.customupdateBySchema(entity, "postgresMdas");
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			new UpdateCommunicationNew(meterId, "instjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService.getEntityByMtrNO(meterId);
			if(mmEntity!=null) {
				mmEntity.setLast_communicated_date(new Date());
				masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "Succ";

	}

	@Async
	public void namePlateProfile(SchMetMain scMM) throws ParseException {

		// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();

		NamePlate entity = new NamePlate();
		String meterId = scMM.getDeviceId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setFlag(scMM.getMeterSampleId());
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();
			if (obc.equalsIgnoreCase("0.0.96.1.0.255")) {

				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				entity.setMeter_serial_number(rv[r].getFormattedValue().trim());
			}
			if (obc.equalsIgnoreCase("0.0.96.1.2.255")) {
				entity.setDevice_id(rv[r].getFormattedValue());

			} else if (obc.equalsIgnoreCase("0.0.96.1.1.255")) {
				entity.setManufacturer_name(rv[r].getFormattedValue());

			} else if (obc.equalsIgnoreCase("1.0.0.2.0.255")) {
				entity.setFirmware_version(rv[r].getFormattedValue());
			} else if (obc.equalsIgnoreCase("0.0.94.91.9.255")) {
				entity.setMeter_type(rv[r].getFormattedValue());
			} else if (obc.equalsIgnoreCase("0.0.94.91.11.255")) {
				entity.setMeter_catagory(rv[r].getFormattedValue());
			} else if (obc.equalsIgnoreCase("0.0.94.91.12.255")) {
				entity.setCurrent_rating(rv[r].getFormattedValue());
			} /*
			 * else if (obc.equalsIgnoreCase("1.0.0.4.2.255")) {
			 * entity.setName_plate2(rv[r].getFormattedValue()); } else if
			 * (obc.equalsIgnoreCase("1.0.0.4.3.255")) {
			 * entity.setName_plate3(rv[r].getFormattedValue()); }
			 */else if (obc.equalsIgnoreCase("0.0.96.1.4.255")) {
				entity.setYear_of_manufacture(rv[r].getFormattedValue());
			}
		}

		// odp.customsaveBySchema(entity, "postgresMdas");
		odp.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "idenjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "Succ";

	}

	@Async
	public void transactionEventLogProfile(SchMetMain scMM)
			throws ParseException {
		AmrEventsEntity entity = new AmrEventsEntity();
		Timestamp event_time = null;
		String eventCode = "";
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String meterId = scMM.getDeviceId();
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {

			entity.setTimeStamp(new Timestamp(new Date().getTime()));
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.3.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();
			} else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}

		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Async
	public void billingProfile(SchMetMain scMM) throws ParseException {

		AmrBillsEntity abe = new AmrBillsEntity();
		KeyBills kbEntity = new KeyBills();
		abe.setFlag(scMM.getMeterSampleId());
		abe.setTimeStamp(new Timestamp(new Date().getTime()));
		String meterId = scMM.getDeviceId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// abe.setMyKey(new KeyBills(getString(meterId),
		// getTimeStamp(rv[r].getFormattedValue())));
		// abe.setMyKey(new setMyKey(meterId, dateFormat
		// .parse(dateFormat.format(new Date()))));

		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.0.1.2.255")) {

				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				abe.setMyKey(new KeyBills(scMM.getDeviceId(), new Timestamp(
						format.parse(s1[0]).getTime())));
				kbEntity.setReadTime(new Timestamp(format.parse(s1[0])
						.getTime()));

			} else if (obc.equalsIgnoreCase("1.0.13.0.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				abe.setSysPfBilling(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				abe.setKwh(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.1.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz1(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.2.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz2(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.3.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz3(value);
			}

			else if (obc.equalsIgnoreCase("1.0.1.8.4.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz4(value);
			}

			else if (obc.equalsIgnoreCase("1.0.1.8.5.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz5(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.6.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz6(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.7.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz7(value);
			} else if (obc.equalsIgnoreCase("1.0.1.8.8.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKwhTz8(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvah(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.1.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz1(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.2.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz2(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.3.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz3(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.4.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz4(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.5.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz5(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.6.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz6(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.7.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz7(value);
			} else if (obc.equalsIgnoreCase("1.0.9.8.8.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");

				double value = Double.parseDouble(splitStr[0]);
				abe.setKvahTz8(value);
			}
			// From Here
			else if (obc.equalsIgnoreCase("1.0.1.6.0.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setOccDateKw(null);
				} else if (str.contains("+")) {
					abe.setOccDateKw(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setDemandKw(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setOccDateKw(str); }else if
				 * (str.contains("0test")) { abe.setOccDateKw(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setDemandKw(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.1.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz1(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz1(parseDate(str));
				} else {
					String[] splitStr = str.split("\\s+");
					try {
						double value = Double.parseDouble(splitStr[0]);
						abe.setKwTz1(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz1(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz1(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz1(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.2.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz2(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz2(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					try {
						double value = Double.parseDouble(splitStr[0]);
						abe.setKwTz2(value);						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz2(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz2(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz2(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.3.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz3(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz3(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz3(value);
				}
				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz3(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz3(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz3(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.4.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz4(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz4(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz4(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz4(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz4(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz4(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.1.6.5.255")) {
				String str = rv[r].getFormattedValue();
				if (str.equals("")) {
					abe.setDateKwTz5(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz5(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz5(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz5(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz5(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz5(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.1.6.6.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz6(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz6(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz6(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz6(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz6(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz6(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.7.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKwTz7(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz7(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz7(value);
				}
				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz7(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz7(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz7(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.1.6.8.255")) {
				String str = rv[r].getFormattedValue();
				if (str.equals("")) {
					abe.setDateKwTz8(null);
				} else if (str.contains("+")) {
					abe.setDateKwTz8(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKwTz8(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKwTz8(str); }else if
				 * (str.contains("0test")) { abe.setDateKwTz8(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKwTz8(value);
				 * 
				 * }
				 */
			}
			// From 2nd here

			else if (obc.equalsIgnoreCase("1.0.9.6.0.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKva(null);
				} else if (str.contains("+")) {
					abe.setDateKva(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKva(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKva(str); }else if
				 * (str.contains("0test")) { abe.setDateKva(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKva(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.9.6.1.255")) {
				String str = rv[r].getFormattedValue();
				if (str.equals("")) {
					abe.setDateKvaTz1(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz1(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz1(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz1(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz1(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz1(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.9.6.2.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKvaTz2(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz2(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz2(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz2(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz2(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz2(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.9.6.3.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKvaTz3(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz3(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz3(value);
				}
				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz3(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz3(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz3(value);
				 * 
				 * }
				 */

			} else if (obc.equalsIgnoreCase("1.0.9.6.4.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKvaTz4(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz4(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz4(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz4(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz4(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz4(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.9.6.5.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKvaTz5(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz5(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz5(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz5(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz5(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz5(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.9.6.6.255")) {
				String str = rv[r].getFormattedValue();

				if (str.equals("")) {
					abe.setDateKvaTz6(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz6(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz6(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz6(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz6(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz6(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.9.6.7.255")) {
				String str = rv[r].getFormattedValue();
				if (str.equals("")) {
					abe.setDateKvaTz7(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz7(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz7(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz7(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz7(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz7(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("1.0.9.6.8.255")) {
				String str = rv[r].getFormattedValue();
				if (str.equals("")) {
					abe.setDateKvaTz8(null);
				} else if (str.contains("+")) {
					abe.setDateKvaTz8(parseDate(str));

				} else {
					String[] splitStr = str.split("\\s+");
					double value = Double.parseDouble(splitStr[0]);
					abe.setKvaTz8(value);
				}

				/*
				 * if(str.equals("")){ str = str.replace("", "0test"); }
				 * if(str.contains("+")) { abe.setDateKvaTz8(str); }else if
				 * (str.contains("0test")) { abe.setDateKvaTz8(null); }else {
				 * String[] splitStr = str.split("\\s+"); double value =
				 * Double.parseDouble(splitStr[0]); abe.setKvaTz8(value);
				 * 
				 * }
				 */
			} else if (obc.equalsIgnoreCase("0.0.94.91.13.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setBillPowerOnDuration(splitStr[0]);
			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setBillKwhExport(splitStr[0]);
			} else if (obc.equalsIgnoreCase("1.0.10.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setBillKvahExport(splitStr[0]);
			} else if (obc.equalsIgnoreCase("1.0.5.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setReactiveImpActiveImp(splitStr[0]);
			} else if (obc.equalsIgnoreCase("1.0.6.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setReactiveImpActiveExp(splitStr[0]);

			} else if (obc.equalsIgnoreCase("1.0.7.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setReactiveExpActiveExp(splitStr[0]);

			} else if (obc.equalsIgnoreCase("1.0.8.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				// double value = Double.parseDouble(splitStr[0]);
				abe.setReactiveExpActiveImp(splitStr[0]);

			}
		}
		// amrBillsService.customsavemdas(abe);
		amrBillsService.customupdateBySchema(abe, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "billjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void bulkLoadProfile(SchMetMain scMM) throws ParseException {

		AmrLoadEntity entity = new AmrLoadEntity();
		// KeyLoad kentity = new KeyLoad();
		entity.setFlag(scMM.getMeterSampleId());
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		String meterId = scMM.getDeviceId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/*
		 * entity.setMyKey(new KeyInstantaneous(meterId, dateFormat
		 * .parse(dateFormat.format(new Date()))));
		 */
		String[] st = scMM.getSampleTime().replace("T", " ").split("\\+");
		entity.setSampleTime(new Timestamp(format.parse(st[0]).getTime()));
		String[] ct = scMM.getCreateTime().replace("T", " ").split("\\+");
		entity.setCreateTime(new Timestamp(format.parse(ct[0]).getTime()));
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {
				// entity.setTimeStamp(new Timestamp(new Date().getTime()));
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				entity.setMyKey(new KeyLoad(scMM.getDeviceId(), new Timestamp(
						format.parse(s1[0]).getTime())));
				// kentity.setReadTime(new
				// Timestamp(format.parse(s1[0]).getTime()));

			} else if (obc.equalsIgnoreCase("1.0.31.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setiR(value);

			} else if (obc.equalsIgnoreCase("1.0.51.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setiY(value);
			} else if (obc.equalsIgnoreCase("1.0.71.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setiB(value);
			} else if (obc.equalsIgnoreCase("1.0.32.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setvR(value);
			} else if (obc.equalsIgnoreCase("1.0.52.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setvY(value);
			} else if (obc.equalsIgnoreCase("1.0.72.27.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setvB(value);
			} else if (obc.equalsIgnoreCase("1.0.1.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKwh(value);
			} else if (obc.equalsIgnoreCase("1.0.2.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setBlockEnergyKwhExp(value);
			} else if (obc.equalsIgnoreCase("1.0.5.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvarhQ1(value);
				entity.setKvarhLag(value);
			} else if (obc.equalsIgnoreCase("1.0.6.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvarhQ2(value);
			} else if (obc.equalsIgnoreCase("1.0.7.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvarhQ3(value);
			} else if (obc.equalsIgnoreCase("1.0.8.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvarhQ4(value);
				entity.setKvarhLead(value);
			} else if (obc.equalsIgnoreCase("1.0.9.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvah(value);
			} else if (obc.equalsIgnoreCase("1.0.10.29.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setBlockEnergyKvahExp(value);
			} else if (obc.equalsIgnoreCase("1.0.12.27.0.255")) {
				entity.setAverageVoltage(rv[r].getFormattedValue().replace("V",
						""));
			} else if (obc.equalsIgnoreCase("1.0.11.27.0.255")) {
				entity.setAverageCurrent(rv[r].getFormattedValue().replace("A",
						""));
			} else if (obc.equalsIgnoreCase("1.0.91.27.0.255")) {
				entity.setNeutralCurrent(rv[r].getFormattedValue().replace("A",
						""));
			}

		}
		if (entity.getKwh() != null) {
			entity.setKw(entity.getKwh() * 2);
		}
		if (entity.getKvah() != null) {
			entity.setKva(entity.getKvah() * 2);
		}
		if (entity.getKvarhLag() != null) {
			entity.setKvarLag(entity.getKvarhLag() * 2);
		}
		if (entity.getKvarhLead() != null) {
			entity.setKvarLead(entity.getKvarhLead() * 2);
		}
		if (entity.getKwh() != null && entity.getKvah() != null) {
			entity.setPowerFactor(entity.getKwh() / entity.getKvah());
		}
		if (entity.getKwhExp() != null) {
			entity.setKwExp(entity.getKwhExp() * 2);
		}

		// amrLoadService.customsavemdas(entity);
		amrLoadService.customupdateBySchema(entity, "postgresMdas");
		LoadSurveyEstimated les=new LoadSurveyEstimated();
		
		ObjectMapper mapper=new ObjectMapper();
		String json="";
		try {
			json = mapper.writeValueAsString(entity);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*Gson gson=new Gson();
		LoadSurveyEstimated est=gson.fromJson(json, LoadSurveyEstimated.class);
		loadEstimationService.customsaveBySchema(est, "postgresMdas");*/
		try {
			new UpdateCommunicationNew(meterId, "loadjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Async
	public void dailyLoadProfile(SchMetMain scMM) throws ParseException {

		AmrLoadEntity entity = new AmrLoadEntity();
		KeyLoad kentity = new KeyLoad();
		DailyKeyLoad dkl = new DailyKeyLoad();
		AmrDailyLoadEntity adlentity = new AmrDailyLoadEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		adlentity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setFlag(scMM.getMeterSampleId());
		adlentity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String meterId = scMM.getDeviceId();
		Timestamp ts = null;
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();
			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {

				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				ts = new Timestamp(format.parse(s1[0]).getTime());

				kentity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));

			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKwhImp(value);
				adlentity.setCum_active_import_energy(value);

			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKwhExp(value);
				adlentity.setCum_active_export_energy(value);

			} else if (obc.equalsIgnoreCase("1.0.9.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvahImp(value);
				adlentity.setCum_apparent_import_energy(value);
			} else if (obc.equalsIgnoreCase("1.0.10.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				entity.setKvahExp(value);
				adlentity.setCum_apparent_export_energy(value);
			} else if (obc.equalsIgnoreCase("1.0.5.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);
				adlentity.setCum_reactive_energy5(value);
			} else if (obc.equalsIgnoreCase("1.0.6.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);

				adlentity.setCum_reactive_energy6(value);
			} else if (obc.equalsIgnoreCase("1.0.7.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);

				adlentity.setCum_reactive_energy7(value);
			} else if (obc.equalsIgnoreCase("1.0.8.8.0.255")) {
				String str = rv[r].getFormattedValue();
				String[] splitStr = str.split("\\s+");
				double value = Double.parseDouble(splitStr[0]);

				adlentity.setCum_reactive_energy8(value);
			}

		}
		entity.setMyKey(new KeyLoad(scMM.getDeviceId(), ts));
		adlentity.setMyKey(new DailyKeyLoad(scMM.getDeviceId(), ts));
		// amrLoadService.customupdateBySchema(entity, "postgresMdas");

		adls.customupdateBySchema(adlentity, "postgresMdas");

		try {
			new UpdateCommunicationNew(meterId, "loadjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// OnDemand Current Event Log

	public void currentEventLogProfile(SchMetMain scMM) throws ParseException {

		AmrEventsEntity entity = new AmrEventsEntity();
		entity.setFlag(scMM.getMeterSampleId());
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		String meterId = scMM.getDeviceId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp event_time = null;
		String eventCode = "";
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.1.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();
			}
			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}
			if (obc.equalsIgnoreCase("1.0.31.7.0.255")) { // i_r
				entity.setiR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) { // i_y
				entity.setiY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) { // i_b
				entity.setiB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));

			} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) { // v_r
				entity.setvR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) { // v_y
				entity.setvY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) { // v_b
				entity.setvB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));

			} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) { // pf_r
				entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) { // pf_y
				entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) { // pf_b
				entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));

			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) { // kwh
				entity.setKwh(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) { // kwh_exp
				entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			}
		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void voltageEventLogProfile(SchMetMain scMM) throws ParseException {

		AmrEventsEntity entity = new AmrEventsEntity();

		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp event_time = null;
		String eventCode = "";
		String meterId = scMM.getDeviceId();
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.0.255")) { // eventcode
				eventCode = rv[r].getFormattedValue();
			}
			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}
			if (obc.equalsIgnoreCase("1.0.31.7.0.255")) { // i_r
				entity.setiR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) { // i_y
				entity.setiY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) { // i_b
				entity.setiB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));

			} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) { // v_r
				entity.setvR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) { // v_y
				entity.setvY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) { // v_b
				entity.setvB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));

			} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) { // pf_r
				entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) { // pf_y
				entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) { // pf_b
				entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));

			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) { // kwh
				entity.setKwh(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) { // kwh_exp
				entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			}
		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void otherEventLogProfile(SchMetMain scMM) throws ParseException {

		AmrEventsEntity entity = new AmrEventsEntity();
		String meterId = scMM.getDeviceId();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp event_time = null;
		String eventCode = "";
		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.4.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();
			}
			if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}
			if (obc.equalsIgnoreCase("1.0.31.7.0.255")) { // i_r
				entity.setiR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) { // i_y
				entity.setiY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));
			} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) { // i_b
				entity.setiB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("A", "")));

			} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) { // v_r
				entity.setvR(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) { // v_y
				entity.setvY(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));
			} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) { // v_b
				entity.setvB(Double.parseDouble(rv[r].getFormattedValue()
						.replace("V", "")));

			} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) { // pf_r
				entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) { // pf_y
				entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
			} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) { // pf_b
				entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));

			} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) { // kwh
				entity.setKwh(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) { // kwh_exp
				entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue()
						.replace("Wh", "")));
			}
		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Async
	public void controledEventLogProfile(SchMetMain scMM) throws ParseException {
		AmrEventsEntity entity = new AmrEventsEntity();
		String meterId = scMM.getDeviceId();
		Timestamp event_time = null;
		String eventCode = "";
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {

			entity.setTimeStamp(new Timestamp(new Date().getTime()));
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.6.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();
			} else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}

		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);

		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Async
	public void nonRollOverEventLogProfile(SchMetMain scMM)
			throws ParseException {
		AmrEventsEntity entity = new AmrEventsEntity();
		Timestamp event_time = null;
		String eventCode = "";
		String meterId = scMM.getDeviceId();
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		RegisterValues[] rv = scMM.getRegisterValues();
		for (int r = 0; r < rv.length; r++) {

			entity.setTimeStamp(new Timestamp(new Date().getTime()));
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.5.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();
			} else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}

		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void powerEventLogProfile(SchMetMain scMM) throws ParseException {
		AmrEventsEntity entity = new AmrEventsEntity();
		Timestamp event_time = null;
		String meterId = scMM.getDeviceId();
		String eventCode = "";
		entity.setFlag(scMM.getMeterSampleId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		RegisterValues[] rv = scMM.getRegisterValues();
		String pft = null;
		for (int r = 0; r < rv.length; r++) {

			entity.setTimeStamp(new Timestamp(new Date().getTime()));
			String obc = rv[r].getFormattedRegisterObisCode();
			String atr = rv[r].getAttributeId();

			if (obc.equalsIgnoreCase("0.0.96.11.2.255")) { // eventCode
				eventCode = rv[r].getFormattedValue();

			} else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) { // eventtime
				String[] s1 = rv[r].getFormattedValue().replace("T", " ")
						.split("\\+");
				pft = s1[0];
				event_time = new Timestamp(format.parse(s1[0]).getTime());
			}

		}
		entity.setMyKey(new KeyEvent(scMM.getDeviceId(), eventCode, event_time));
		// oCurEvt.customSave(entity);
		oCurEvt.customupdateBySchema(entity, "postgresMdas");
		try {
			new UpdateCommunicationNew(meterId, "evntjs", modemCommunication)
					.start();
			MasterMainEntity mmEntity = masterMainService
					.getEntityByMtrNO(meterId);
			mmEntity.setLast_communicated_date(new Date());
			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/smc", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String smcview(Model model, HttpServletRequest req,
			@RequestParam("event") int len) {

		String s = " select seq_id,type,\n"
				+ "case when A.type='Current Event Log' then (select time_stamp from meter_data.events where flag not in ('OD') and to_number(flag, '999999999') in (A.seq_id))\n"
				+ "  when A.type='Bulk Load' then (select time_stamp from meter_data.load_survey where flag not in ('OD') and to_number(flag, '999999999') in (A.seq_id))\n"
				+ "when A.type='Daily Load' then (select rtc_date_time from meter_data.daily_load where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Non Roll Over Event Log' then (select time_stamp from meter_data.events where flag not in ('OD') and   to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Billing' then (select time_stamp from meter_data.bill_history where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Power Event Log' then (select time_stamp from meter_data.events where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Transaction Event Log' then (select time_stamp from meter_data.events where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Voltage Event Log' then (select time_stamp from meter_data.events where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Other Event Log' then (select time_stamp from meter_data.events where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ "  when A.type='Instantaneous' then (select time_stamp from meter_data.amiinstantaneous where  iflag not in ('OD') and  to_number(iflag, '999999999') in (A.seq_id))\n"
				+ " when A.type='Controled Event Log' then (select time_stamp from meter_data.events where  flag not in ('OD') and  to_number(flag, '999999999') in (A.seq_id))\n"
				+ "END as Schedule_time,meter_number\n"
				+ "from \n"
				+ "(select seq_id,type,meter_number from meter_data.sch_meter_track WHERE seq_id<=(select max(seq_id) from meter_data.sch_meter_track) ORDER BY seq_id DESC  LIMIT "
				+ len + " ) A";
		List<Object[]> l = amrLoadService
				.getCustomEntityManager("postgresMdas").createNativeQuery(s)
				.getResultList();
		model.addAttribute("tracklist", l);
		return "smc";
	}

	@RequestMapping(value = "/seqView/{seqId}/{type}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object seqView(@PathVariable String seqId, @PathVariable String type) {
		String s = "";
		String s1 = "";
		List<Object[]> l = new ArrayList<>();
		if (type.equalsIgnoreCase("Instantaneous")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'amiinstantaneous'";

			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "kWh", "kVAh", "kVA", "Ir", "Iy", "Ib",
					"Vr", "Vy", "Vb", "PF 3Phase", "Frequency", "kWh Exp",
					"kVAh Exp", " kVArh Lag", " kVArh Lead", "Power Off Count",
					"Power Off Duration(Min)", "Programming Count",
					"Tamper Count", "MD kW", "kW MD Date", "MD Kva",
					"kVA MD Date", "Signed Active Power",
					"Signed Reactive Power", "Billing Count", "Billing Date",
					"Reactive Imp Active Exp", "Reactive Exp Active Exp",
					"Voltage", "Phase Current", "Total Power on duration(Min)"

			};
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS') as time_stamp,	to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') as read_time,	meter_number,	(kwh/1000) as kwh,	(kvah/1000) as kvah,	(kva/1000) as kva,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b,		pf_threephase,	frequency,	kwh_exp,	kvah_exp,	kvarh_lag,	kvarh_lead,	power_off_count,	upper((to_char((ceil(power_off_duration/60000)-1),'999999') || ' Minutes') ||	(to_char((power_off_duration/1000),'999999') || ' Seconds')),	programming_count,	tamper_count,	(md_kw/1000) as md_kw ,	replace(replace(date_md_kw, 'T', ' '),'+05:30','') as date_md_kw,	(md_kva/1000) as md_kva ,	replace(replace(date_md_kva, 'T', ' '),'+05:30','') as date_md_kva,	(signed_active_power/1000) as signed_active_power ,	signed_reactive_power,	billing_count,	to_char(billing_date, 'dd-MM-YYYY'),	reactive_imp_active_exp,	reactive_exp_active_exp, SUBSTR(power_voltage, 1, 5) AS PV ,SUBSTR(phase_current, 1, 5) AS PC, total_power_on_duration   from meter_data.amiinstantaneous where iflag='"
					+ seqId + "'";

			l.add(o);

		} else if (type.equalsIgnoreCase("Bulk Load")
				) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'load_survey'";

			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "Ir", "Iy", "Ib", "Vr", "Vy", "Vb", "kWh",
					"kVAh", "kVArh Q1", "kVArh Q2", "kVArh Q3", "kVArh Q4",
					"Block Energy kWh Exp", "Block Energy kVAh Exp",
					"Average Voltage", "Average Current", "Neutral Current" };

			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS') as timestamp,	to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') as readtime,	meter_number,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b,	(kwh/1000) as kwh,	(kvah/1000) as kvah,	kvarh_q1,	kvarh_q2,	kvarh_q3,	kvarh_q4,	block_energy_kwh_exp,	block_energy_kvah_exp, SUBSTR(average_voltage, 1,5) AS AV,SUBSTR(average_current, 1,5) AS AC , SUBSTR(neutral_current, 1,5) AS NC from meter_data.load_survey where flag='"
					+ seqId + "'";
		}else if (type.equalsIgnoreCase("DailyLoad")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'load_survey'";
			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "cum Active Import Energy", "cum Active Export Energy", "cum Apparent Import Energy", "cum Apparent Export Energy", "cum Reactive Energy5", "cum Reactive Energy6", "cum Reactive Energy7","cum Reactive Energy8"};
					
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	to_char(rtc_date_time, 'YYYY-MM-DD HH24:MI:SS'),	mtrno,	cum_active_import_energy,	cum_active_export_energy,	cum_apparent_import_energy,	cum_apparent_export_energy,	cum_reactive_energy5,	cum_reactive_energy6,	cum_reactive_energy7,	cum_reactive_energy8 from meter_data.daily_load "
					+ "where flag='"+seqId+"' ";
		}	 
		
		
		else if (type.equalsIgnoreCase("Billing")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'bill_history'";
			Object[] o = new Object[] { "Saved Time", "Meter Number",
					"Billing Time", "PF", "kWh TZ1", "kWh TZ2", "kWh TZ3",
					"kWh TZ4", "kWh TZ5", "kWh TZ6", "kWh TZ7", "kWh TZ8",
					"kWh", "kVArh Lag", "kVArh Lead", "kVAh", "kVAh TZ1",
					"kVAh TZ2", "kVAh TZ3", "kVAh TZ4", "kVAh TZ5", "kVAh TZ6",
					"kVAh TZ7", "kVAh TZ8", "Demand kW", "kW TZ1", "kW TZ2",
					"kW TZ3", "kW TZ4", "kW TZ5", "kW TZ6", "kW TZ7", "kW TZ8",
					"kVA", "kVA TZ1", "kVA TZ2", "kVA TZ3", "kVA TZ4",
					"kVA TZ5", "kVA TZ6", "kVA TZ7", "kVA TZ8",
					"Power On Duration", "Bill kWh Export", "Bill kVAh Export",
					"Reactive Imp Active Imp", "Reactive Imp Active Exp",
					"Reactive Exp Active Exp", "Reactive Exp Active Imp" };
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS') as timestamp,	meter_number,	to_char(billing_date, 'YYYY-MM-DD HH24:MI:SS') as billingdate,	sys_pf_billing,	(kwh_tz1/1000) as kwh_tz1 ,	(kwh_tz2/1000) as kwh_tz2 ,	(kwh_tz3/1000) as kwh_tz3,	(kwh_tz4/1000) as kwh_tz4,	(kwh_tz5/1000) as kwh_tz5,	(kwh_tz6/1000) as kwh_tz6,	(kwh_tz7/1000),	(kwh_tz8/1000),	(kwh/1000),	kvarh_lag,	kvarh_lead,	kvah,	kvah_tz1,	kvah_tz2,	kvah_tz3,	kvah_tz4,	kvah_tz5,	kvah_tz6,	kvah_tz7,	kvah_tz8,	(demand_kw/1000) as demand_kw,	(kw_tz1/1000) as kw_tz1,	(kw_tz2/1000) kw_tz2,	(kw_tz3/1000) as kw_tz3,	(kw_tz4/1000) as kw_tz4,	(kw_tz5/1000) as kw_tz5,	(kw_tz6/1000) as kw_tz6,	(kw_tz7/1000),	(kw_tz8/1000),	kva,	kva_tz1,	kva_tz2,	kva_tz3,	kva_tz4,	kva_tz5,	kva_tz6,	kva_tz7,	kva_tz8,	(to_number(bill_power_on_duration,'99999')/1000),	(to_number(bill_kwh_export,'999999')/1000),	bill_kvah_export,	reactive_imp_active_imp,	reactive_imp_active_exp,	reactive_exp_active_exp,	reactive_exp_active_imp from meter_data.bill_history where flag='"
					+ seqId + "'";
		} else if (type.equalsIgnoreCase("Current Event Log")
				|| type.equalsIgnoreCase("Non Roll Over Event Log")
				|| type.equalsIgnoreCase("Power Event Log")
				|| type.equalsIgnoreCase("Transaction Event Log")
				|| type.equalsIgnoreCase("Voltage Event Log")
				|| type.equalsIgnoreCase("Other Event Log")
				|| type.equalsIgnoreCase("Controled Event Log")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'events'";
			Object[] o = new Object[] { "Time Stamp", "Meter Number",
					"Event Code", "Ir", "Iy", "Ib", "Vr", "Vy", "Vb", "PFr",
					"PFy", "PFb", "kWh", "kWh Exp", "Event Time" };
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	meter_number,	event_code,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b,	pf_r,	pf_y,	pf_b,	(kwh/1000) as kwh,	(kwh_exp/1000) as kwh_exp,	to_char(event_time, 'dd-MM-YYYY hh:mm:ss') from meter_data.events where flag='"
					+ seqId + "'";
		}
		// List<Object[]>
		// l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
		List<Object[]> l1 = amrLoadService
				.getCustomEntityManager("postgresMdas").createNativeQuery(s1)
				.getResultList();
		List<List<Object[]>> al = new ArrayList<>();
		al.add(l);
		al.add(l1);
		return al;

	}

	public String parseDate(String obisDate) {

		// //System.out.println("===>"+obisDate.replace("+",","));
		String[] strr = obisDate.replace("+", ",").split(",");

		SimpleDateFormat inputFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat outputFormat = new SimpleDateFormat(
				"dd/MMM/yyyy HH:mm:ss a");
		Date date = null;
		try {
			date = inputFormat.parse(strr[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// hardcode here and check
		String validDate = outputFormat.format(date);

		return validDate;

	}

	@RequestMapping(value = "/viewODRequest/{seqId}/{type}/{time}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object viewODRequest(@PathVariable String seqId, @PathVariable String type,@PathVariable String time) {
		String s = "";
		String s1 = "";
		List<Object[]> l = new ArrayList<>();

		if (type.equalsIgnoreCase("Instantaneous")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'amiinstantaneous'";
			s1 = "select to_char(time_stamp,'YYYY-MM-DD HH24:MI:SS'),	to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') as readtime,	meter_number, kwh, kvah, kva,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b,		pf_threephase,	frequency,	kwh_exp,	kvah_exp,	kvarh_lag,	kvarh_lead,	power_off_count,	upper((to_char((ceil(power_off_duration/60000)-1),'999999') || ' Minutes') ||	(to_char((power_off_duration/1000),'999999') || ' Seconds')),	programming_count,	tamper_count,	(md_kw/1000),	replace(replace(date_md_kw, 'T', ' '),'+05:30',''),	(md_kva/1000),	replace(replace(date_md_kva, 'T', ' '),'+05:30',''),	(signed_active_power/1000),	signed_reactive_power,	billing_count,	to_char(billing_date, 'YYYY-MM-DD') as billdate, reactive_imp_active_exp, SUBSTR(power_voltage, 1, 5) AS PV, SUBSTR(phase_current, 1, 5) AS PC, total_power_on_duration  from meter_data.amiinstantaneous where   id = '"+time+"' and iflag ='OD'";
			
			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "kWh", "kVAh", "kVA", "Ir", "Iy", "Ib",
					"Vr", "Vy", "Vb", "PF 3Phase", "Frequency", "kWh Exp",
					"kVAh Exp", "kVRAh Lag", "kVRAh Lead", "Power Off Count",
					"Power Off Duration", "Programming Count", "Tamper Count",
					"MD Kw", "kW MD Date", "MD Kva", "Kva MD Date",
					"Signed Active Power", "Signed Reactive Power",
					"Billing Count", "Billing Date", "Reactive Imp Active Exp",
					"Voltage", "Phase Current", "Total Power on duration(min)" };
			l.add(o);
		

		} else if (type.equalsIgnoreCase("Load")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'load_survey'";
			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "Ir", "Iy", "Ib", "Vr", "Vy", "Vb", "kWh",
					"kVAh", "kVArh Q1", "kVArh Q2", "kVArh Q3", "kVArh Q4",
					"Block Energy kWh Exp", "Block Energy kVAh Exp",
					"Average Voltage", "Average Current" };
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') as readtime,	meter_number,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b, kwh,	kvah,	kvarh_q1,	kvarh_q2,	kvarh_q3,	kvarh_q4,	block_energy_kwh_exp,	block_energy_kvah_exp, SUBSTR(average_voltage, 1,5) AS AV,SUBSTR(average_current, 1,5) AS AC  from meter_data.load_survey where  id = '"
					+ time + "' and flag ='OD'   ";
		} else if (type.equalsIgnoreCase("DailyLoad")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'load_survey'";
			Object[] o = new Object[] { "Saved Time", "Read Time",
					"Meter Number", "cum Active Import Energy", "cum Active Export Energy", "cum Apparent Import Energy", "cum Apparent Export Energy", "cum Reactive Energy5", "cum Reactive Energy6", "cum Reactive Energy7","cum Reactive Energy8"};
					
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	to_char(rtc_date_time, 'YYYY-MM-DD HH24:MI:SS'),	mtrno,	cum_active_import_energy,	cum_active_export_energy,	cum_apparent_import_energy,	cum_apparent_export_energy,	cum_reactive_energy5,	cum_reactive_energy6,	cum_reactive_energy7,	cum_reactive_energy8 from meter_data.daily_load where  id = '"
					+ time + "' and flag ='OD'   ";
		}		
		else if (type.equalsIgnoreCase("Bill History")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'bill_history'";
			Object[] o = new Object[] { "Saved Time", "Meter Number",
					"Billing Time", "pF", "kWh TZ1", "kWh TZ2", "kWh TZ3",
					"kWh TZ4", "kWh TZ5", "kWh TZ6", "kWh TZ7", "kWh TZ8",
					"kWh", "kVrAh Lag", "kVrAh Lead", "kVAh", "kVAh TZ1",
					"kVAh TZ2", "kVAh TZ3", "kVAh TZ4", "kVAh TZ5", "kVAh TZ6",
					"kVAh TZ7", "kVAh TZ8", "Demand kW", "kW TZ1", "kW TZ2",
					"kW TZ3", "kW TZ4", "kW TZ5", "kW TZ6", "kW TZ7", "kW TZ8",
					"kVA", "kVA TZ1", "kVA TZ2", "kVA TZ3", "kVA TZ4",
					"kVA TZ5", "kVA TZ6", "kVA TZ7", "kVA TZ8",
					"Power On Duration", "Bill KWH Export", "Bill KVAH Export",
					"Reactive Imp Active Imp", "Reactive Imp Active Exp",
					"Reactive Exp Active Exp", "Reactive Exp Active Imp" };
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	meter_number,	to_char(billing_date, 'YYYY-MM-DD HH24:MI:SS') as billdate,	sys_pf_billing,	(kwh_tz1/1000) as kwh_tz1,	(kwh_tz2/1000) as kwh_tz2,	(kwh_tz3/1000) as kwh_tz3,	(kwh_tz4/1000)as kwh_tz4,	(kwh_tz5/1000) askwh_tz5,	(kwh_tz6/1000) kwh_tz6,	(kwh_tz7/1000)as kwh_tz7,	(kwh_tz8/1000) kwh_tz8,	(kwh/1000) as kwh,	kvarh_lag,	kvarh_lead,	kvah,	kvah_tz1,	kvah_tz2,	kvah_tz3,	kvah_tz4,	kvah_tz5,	kvah_tz6,	kvah_tz7,	kvah_tz8,	(demand_kw/1000) as demand_kw ,	(kw_tz1/1000) as kw_tz1,	(kw_tz2/1000) as kw_tz2,	(kw_tz3/1000) as kw_tz3,	(kw_tz4/1000) as kw_tz4,	(kw_tz5/1000),	(kw_tz6/1000) as kw_tz6,	(kw_tz7/1000) as kw_tz7,	(kw_tz8/1000) as  kw_tz8,	kva,	kva_tz1,	kva_tz2,	kva_tz3,	kva_tz4,	kva_tz5,	kva_tz6,	kva_tz7,	kva_tz8,	to_number(bill_power_on_duration,'99999')/1000 ,to_number(bill_kwh_export,'999999')/1000,	reactive_imp_active_imp,	reactive_imp_active_exp,	reactive_exp_active_exp,	reactive_exp_active_imp from meter_data.bill_history where    id = '"
					+ time + "' and flag ='OD'  ";
		} else if (type.equalsIgnoreCase("Event")
				|| type.equalsIgnoreCase("Non Roll Over Event Log")
				|| type.equalsIgnoreCase("Power Event Log")
				|| type.equalsIgnoreCase("Transaction Event Log")
				|| type.equalsIgnoreCase("Voltage Event Log")
				|| type.equalsIgnoreCase("Other Event Log")
				|| type.equalsIgnoreCase("Controled Event Log")) {
			// s="SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema = 'meter_data'   AND table_name   = 'events'";
			Object[] o = new Object[] { "Time Stamp", "Meter Number",
					"Event Code", "Ir", "Iy", "Ib", "Vr", "Vy", "Vb", "PFr",
					"PFy", "PFb", "kWh", "kWh Exp", "Event Time" };
			l.add(o);
			s1 = "select to_char(time_stamp, 'YYYY-MM-DD HH24:MI:SS'),	meter_number,	event_code,	i_r,	i_y,	i_b,	v_r,	v_y,	v_b,	pf_r,	pf_y,	pf_b, kwh, kwh_exp,	to_char(event_time, 'YYYY-MM-DD HH24:MI:SS') as eventtime from meter_data.events where    id = '"
					+ time + "' and flag ='OD'  ";
		}
		// List<Object[]>
		// l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
//	    System.out.println("Ondemand SQL" +s1);
		List<Object[]> l1 = amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s1).getResultList();
		List<List<Object[]>> al = new ArrayList<>();
		al.add(l);
		al.add(l1);
		return al;

	}

	Long acount = 0L;

	//@Scheduled(cron = "0 0/1 * * * ?")
	public void sma() throws ParseException {
		try {
			BigDecimal mid = (BigDecimal) mas
					.getCustomEntityManager("postgresMdas")
					.createNativeQuery(
							"select max(meter_alarm_id) from meter_data.meter_alarm")
					.getSingleResult();
			acount = mid.longValue();
			//System.err.println(mid+"--"+acount);
			if (acount != null) {
				// System.out.println("Total count is :" + count);
				acount++;
			} else {
				acount = 1L;
			}
		} catch (NullPointerException e) {
			acount = 1L;
		}
		// BigDecimal mid=(BigDecimal)
		// mas.getCustomEntityManager("postgresMdas").createNativeQuery("select max(meter_alarm_id) from meter_data.meter_alarm").getSingleResult();
		String s = HESController.meterAlarms(acount.intValue() , 1000);
		Gson gson = new Gson();
		MeterAlarms[] ins = gson.fromJson(s, MeterAlarms[].class);

		for (MeterAlarms ma : ins) {
			MeterAlarmsEntity m = new MeterAlarmsEntity();
			m.setMeterNumber(ma.getMeterId());
			m.setMeterAlarmId(Integer.parseInt(ma.getMeterAlarmId()));
			String ts = ma.getAlarmTime().replace("T", " ");
			String[] ss = ts.split("\\+");

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date parsedDate = format.parse(ss[0]);
			m.setAlarmTime(new java.sql.Timestamp(parsedDate.getTime()));
			m.setSequenceNumber(ma.getSequenceNumber());
			String sn = "";
			for (String sa : ma.getAlarmActive()) {
				sn = sn.concat(sa + ",");
			}
			m.setAlarmActive(sn.substring(0, sn.length() - 1));
			mas.customSave(m);
			String kno="";
			try {
			List<Object[]> l = (List<Object[]>) oCurEvt
					.getCustomEntityManager("postgresMdas")
					.createNativeQuery(
							"select accno,sdocode,kno,mtrno from meter_data.master_main where mtrno='"
									+ ma.getMeterId() + "'")
					.getResultList();
			 kno=l.get(0)[2].toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			if(!kno.equalsIgnoreCase("")) {
			try {
				//Notification.requestPermission().then(function(sn) {
				if (sn.contains("METER_BILLING_INCREMENT")) {
					//NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="BILLING";
					String message="The billing counter has been incremented. Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_BILLING_INCREMENT",
					 * "The billing counter has been incremented." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_CONNECT_DISCONNECT")) {
					//NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="METER CONNECT OR DISCONNECT";
					String message="The meter load has been connected / disconnected. Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_CONNECT_DISCONNECT",
					 * "The meter load has been connected / disconnected." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_COVER_OPEN")) {
					/* NotificationController n = new NotificationController(); */
					//Notification n = new Notification(title, options);
					
					String title="Tamper Alert";
					String message="The meter cover has been opened (tampering). Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_COVER_OPEN",
					 * "The meter cover has been opened (tampering)." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_EARTH_LEAKAGE")) {
					/* NotificationController n = new NotificationController(); */
					//Notification n = new Notification(title, options);
					
					String title="EARTH Alert";
					String message="Earth leakage detected. Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_EARTH_LEAKAGE", "Earth leakage detected." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_MAGNETIC_INFLUENCE")) {
					/* NotificationController n = new NotificationController(); */
					//Notification n = new Notification(title, options);
					
					String title="Tamper";
					String message="Magnetic influence detected (tampering).  Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_MAGNETIC_INFLUENCE",
					 * "Magnetic influence detected (tampering)." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_NEUTRAL_DISTURBANCE")) {
					//NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="Neutral Alert";
					String message="Neutral disturbance detected (tampering).  Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_NEUTRAL_DISTURBANCE",
					 * "Neutral disturbance detected (tampering)." + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				
				if (sn.contains("METER_OVER_CURRENT")) {
					//NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="Over Current Alert";
					String message="One of the phases is over current.  Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_OVER_CURRENT", "One of the phases is over current" +
					 * new java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_OVER_VOLTAGE")) {
					//NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="Over Voltage";
					String message="One of the phases is over voltage.  Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_OVER_VOLTAGE", "One of the phases is over voltage" +
					 * new java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				
				if (sn.contains("METER_UNDER_VOLTAGE")) {
					NotificationController n = new NotificationController();
					//Notification n = new Notification(title, options);
					
					String title="Lower Voltage";
					String message="One of the phases is under voltage.   Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_UNDER_VOLTAGE", "One of the phases is under voltage" +
					 * new java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				if (sn.contains("METER_LAST_GASP")) {
					//NotificationController n = new NotificationController();
					
					String title="Power Fail";
					String message="Meter has experienced a power failure. Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_LAST_GASP", "Meter has experienced a power failure." +
					 * new java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				if (sn.contains("METER_FIRST_BREATH")) {
					NotificationController n = new NotificationController();
					
					String title="Power On";
					String message="Meter has powered on following an outage. Occurrence Time:-"+m.getAlarmTime();
					triggerAmiNotificationToMobileWeb(kno,title,message);
					/*
					 * n.messageNotify("METER_FIRST_BREATH",
					 * "Meter has powered on following an outage" + new
					 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
					 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString());
					 */
				}
				/*
				 * if (sn.contains("METER_CONNECT_DISCONNECT")) { NotificationController n = new
				 * NotificationController(); List<Object[]> l = (List<Object[]>) oCurEvt
				 * .getCustomEntityManager("postgresMdas") .createNativeQuery(
				 * "select accno,sdocode,kno,mtrno from meter_data.master_main where mtrno='" +
				 * ma.getMeterId() + "'") .getResultList(); String kno=l.get(2).toString();
				 * String title=""; String message=ma.getAlarmActive().toString();
				 * triggerAmiNotificationToMobileWeb(kno,title,message);
				 * 
				 * n.notification("The billing counter has been incremented." + new
				 * java.sql.Timestamp(parsedDate.getTime()), l.get(0)[0].toString(),
				 * l.get(0)[1].toString(), l.get(0)[2].toString(), l.get(0)[3].toString()); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}

	}

	private String  triggerAmiNotificationToMobileWeb(String kno, String title, String message) {
		String query="select fcm_token,customer_contact_no,customer_email_id from meter_data.ncpt_customers where fcm_token is not null and  customer_login_name in ("
				+" select consumerlogin from meter_data.ncpt_registerrrno where knum='"+kno+"')";


		List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		if (list.size() > 0) {
			String status="TOKEN_NOT_AVAILABLE";
			for (Object[] object : list) {

				String token = (object[0]==null)? null :String.valueOf(object[0]).trim();
//				String phone = (object[1]==null)? null :String.valueOf(object[1]).trim();//FOR FUTURE SMS SERVICE
//				String email = (object[2]==null)? null :String.valueOf(object[2]).trim();//FOR FUTURE EMAIL SERVICE
				System.out.println(token);

				if(token!=null){
					FireMessageCordova f;
					try {
						f = new FireMessageCordova(title, message, kno, kno);
					
						status = f.sendToToken(token);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.err.println(status);
				}
			}
			return status;
		}
		else {
			return "NO_REGISTERED_USERS";
		}
		
	}

	@RequestMapping(value = "/meterAlarm")
	public String meterAlarm(Model model) {
		List<Object[]> l = mas
				.getCustomEntityManager("postgresMdas")
				.createNativeQuery(
						"select m.meter_number,mm.subdivision,mm.customer_name,mm.customer_address,m.alarm_time,m.alarm_active from meter_data.meter_alarm m,meter_data.master_main mm where m.meter_number=mm.mtrno order by m.alarm_time DESC")
				.getResultList();
		model.addAttribute("ma", l);

		return "meterAlarm";

	}

	@RequestMapping(value = "/getMeterAlarmByDate")
	public @ResponseBody
	Object getMeterAlarmByDate(HttpServletRequest request) {

		String time = request.getParameter("load_time");
		String sql = "SELECT * from meter_data.meter_alarm WHERE alarm_time>'"
				+ time + "' ORDER BY alarm_time ASC;";
		List<Object[]> l = mas.getCustomEntityManager("postgresMdas")
				.createNativeQuery(sql).getResultList();

		return l;
		// return null;

	}

	private void writeDataToFile(String data) {

		// //System.out.println(data);
		String fileName = "HESData";
		try {
			PrintWriter fileWriter = null;
			try {
				fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(
						getFolderPath() + "/" + fileName + ".txt", true)));
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
			String month = FilterUnit.logFolder + "/"
					+ new SimpleDateFormat("yyyyMM").format(new Date());
			String day = month + "/"
					+ new SimpleDateFormat("yyyyMMdd").format(new Date());
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

	//@Scheduled(cron = "0 0 0/1 * * ?")
	@Transactional
	public void systemGenServiceOrder() {

		String sql = "Insert into meter_data.service_orders (accno,meterno,kno,transaction_time,circle,division,subdivision,sdocode,so_type,request_type,comments,status)\n"
				+ "(select accno,mtrno,kno,CURRENT_TIMESTAMP,circle,division,subdivision,sdocode,'Tamper','Disconnection','Event','Created' from meter_data.master_main where mtrno in(select meter_number from meter_data.events where to_number(event_code,'9999') in (select event_code from meter_data.so_config where status='Active') \n"
				+ "and time_stamp>=(CURRENT_TIMESTAMP-INTERVAL '1 hour') ))";
		int l = mas.getCustomEntityManager("postgresMdas")
				.createNativeQuery(sql).executeUpdate();
	}

	// @Scheduled(cron = "0 0/1 * * * ?")// test
	//@Scheduled(cron = "0 1 * * * ?")
	@Transactional
	public void dailyConsumption() {
		String sql = "insert into meter_data.daily_consumption (location_code,kno,date,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time)\n" +
				"				 select mema.sdocode,mema.kno,(dl.rtc_date_time - INTERVAL '1 day'),mema.mtrno,\n" +
				"				 ((dl.cum_active_import_energy -(select cum_active_import_energy from meter_data.daily_load where to_char(rtc_date_time,'yyyy-MM-dd')=to_char(dl.rtc_date_time - INTERVAL '1 day','yyyy-MM-dd') and mtrno=dl.mtrno ))*to_number(mema.mf,'99.9'))/1000 as kwh_i,\n" +
				"				 ((dl.cum_active_export_energy -(select cum_active_export_energy from meter_data.daily_load where to_char(rtc_date_time,'yyyy-MM-dd')=to_char(dl.rtc_date_time - INTERVAL '1 day','yyyy-MM-dd') and mtrno=dl.mtrno ))*to_number(mema.mf,'99.9'))/1000 as kwh_e,\n" +
				"				 ((dl.cum_apparent_import_energy -(select cum_apparent_import_energy from meter_data.daily_load where to_char(rtc_date_time,'yyyy-MM-dd')=to_char(dl.rtc_date_time - INTERVAL '1 day','yyyy-MM-dd') and mtrno=dl.mtrno ))*to_number(mema.mf,'99.9'))/1000 as kvah_i,\n" +
				"				 ((dl.cum_apparent_export_energy -(select cum_apparent_export_energy from meter_data.daily_load where to_char(rtc_date_time,'yyyy-MM-dd')=to_char(dl.rtc_date_time - INTERVAL '1 day','yyyy-MM-dd') and mtrno=dl.mtrno ))*to_number(mema.mf,'99.9'))/1000 as kvah_e,CURRENT_TIMESTAMP as time_stamp\n" +
				"				 from meter_data.daily_load dl ,meter_data.master_main mema where to_char(dl.rtc_date_time,'yyyy-MM-dd')=to_char(CURRENT_DATE,'yyyy-MM-dd') and dl.mtrno=mema.mtrno ";
		int l = mas.getCustomEntityManager("postgresMdas")
				.createNativeQuery(sql).executeUpdate();
	}

	@RequestMapping(value = "/getMeterByDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Object getMeterByDate(@RequestParam String zone,
			@RequestParam String circle, @RequestParam String division,
			@RequestParam String sdoname, @RequestParam String fdate,
			@RequestParam String tdate) {

		String sql = "select ma.meter_number,mm.subdivision,mm.customer_name,mm.customer_address,ma.alarm_time,ma.alarm_active \n"
				+ "FROM meter_data.meter_alarm ma INNER JOIN meter_data.master_main mm  ON ma.meter_number =mm.mtrno \n"
				+ "where mm.zone like '"
				+ zone
				+ "' and mm.circle like '"
				+ circle
				+ "' and division like '"
				+ division
				+ "' \n"
				+ "and subdivision like '"
				+ sdoname
				+ "' AND to_char(ma.alarm_time,'yyyy-MM-dd')>='"
				+ fdate
				+ "' AND to_char(ma.alarm_time,'yyyy-MM-dd')<='" + tdate + "'";
		List<Object[]> l = mas.getCustomEntityManager("postgresMdas")
				.createNativeQuery(sql).getResultList();
		return l;

	}

//	@Scheduled(cron = "0 0 0 3 * ?")
	@Transactional
	public void monthlyConsumption() {
		/*String sql = "INSERT INTO meter_data.monthly_consumption(location_code,kno,mtrno,billmonth,kwh_imp,kwh_exp,kvah_imp,kvah_exp,kvarh_imp_act_imp,kvarh_imp_act_exp,kvarh_exp_act_imp,kvarh_exp_act_exp,create_time)\n" +
				"				select \n" +
				"				 mema.sdocode,mema.kno,mema.mtrno,to_number((to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM')),'999999') as billmonth,\n" +
				"				(bh.kwh-(select max(kwh) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as kwh,\n" +
				"				(to_number(bh.bill_kwh_export,'99999')-(select max(to_number(bill_kwh_export,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as kwh_export,\n" +
				"				(bh.kvah-(select max(kvah) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as kvah,\n" +
				"				(to_number(bh.bill_kvah_export,'99999')-(select max(to_number(bill_kvah_export,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as kvah_export,\n" +
				"				(to_number(bh.reactive_imp_active_imp,'99999')-(select max(to_number(reactive_imp_active_imp,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as reactive_imp_active_imp,\n" +
				"				(to_number(bh.reactive_imp_active_exp,'99999')-(select max(to_number(reactive_imp_active_exp,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as reactive_imp_active_exp,\n" +
				"				(to_number(bh.reactive_exp_active_imp,'99999')-(select max(to_number(reactive_exp_active_imp,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as reactive_exp_active_imp,\n" +
				"				(to_number(bh.reactive_exp_active_exp,'99999')-(select max(to_number(reactive_exp_active_exp,'99999')) from meter_data.bill_history where to_char(billing_date,'HH24:MI:SS')='00:00:00' and meter_number=mema.mtrno and to_char(billing_date,'yyyyMM')=to_char((bh.billing_date-INTERVAL '1 MONTH'),'yyyyMM'  )))/1000 as reactive_exp_active_exp,CURRENT_TIMESTAMP as time_stamp\n" +
				"				 from meter_data.bill_history bh ,meter_data.master_main mema where  billing_date=CURRENT_DATE and \n" +
				"				bh.meter_number=mema.mtrno ";*/
//	String	sql="insert into meter_data.monthly_consumption (billmonth,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time)  \n" +
//				"select B.month,B.meter_number,(B.kwhimp*B.mf) AS kwh_imp,(B.kwhexp*B.mf) AS kwh_exp,\n" +
//				"(B.kvahimp*B.mf) AS kvah_imp,(B.kvahexp*B.mf) AS kvah_exp,B.time_stamp\n" +
//				"FROM\n" +
//				"(select A.month,A.meter_number,A.kwhimp,A.kwhexp,A.kvahimp,A.kvahexp,\n" +
//				"m.mtrno,COALESCE(cast(m.mf AS NUMERIC),1) AS mf,A.time_stamp from \n" +
//				"(select to_number((to_char((read_time-INTERVAL '0 MONTH'),'yyyyMM')),'999999') as month,\n" +
//				"meter_number,\n" +
//				"sum(kwh_imp) AS kwhimp ,sum(kwh_exp) AS kwhexp,sum(kvah_exp) AS kvahexp,sum(kvah_imp) AS kvahimp,CURRENT_TIMESTAMP as time_stamp\n" +
//				"from meter_data.load_survey l WHERE  to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')\n" +
//				"GROUP BY month,meter_number)A,\n" +
//				"meter_data.master_main m\n" +
//				"WHERE m.mtrno=A.meter_number)B";
	
	String sql="select B.month,B.meter_number,(B.kwhimp*B.mf) AS kwh_imp,(B.kwhexp*B.mf) AS kwh_exp,\n" +
			"(B.kvahimp*B.mf) AS kvah_imp,(B.kvahexp*B.mf) AS kvah_exp,B.time_stamp\n" +
			"FROM\n" +
			"(select A.month,A.meter_number,A.kwhimp,A.kwhexp,A.kvahimp,A.kvahexp,\n" +
			"m.mtrno,COALESCE(cast(m.mf AS NUMERIC),1) AS mf,A.time_stamp from\n" +
			"(\n" +
			"select to_number((to_char((read_time-INTERVAL '0 MONTH'),'yyyyMM')),'999999') as month,\n" +
			"meter_number,\n" +
			"sum(kwh) AS kwhimp ,sum(kwh_exp) AS kwhexp,sum(kvah_exp) AS kvahexp,sum(kvah) AS kvahimp,CURRENT_TIMESTAMP as time_stamp\n" +
			"from meter_data.load_survey l WHERE  to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')\n" +
			"GROUP BY month,meter_number)A,\n" +
			"meter_data.master_main m\n" +
			"WHERE m.mtrno=A.meter_number AND\n" +
			"m.mtrno NOT IN(select distinct mtrno from meter_data.monthly_consumption WHERE billmonth=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')))B";
	
		//System.out.println("monthly-- consumption qry-:"+sql);
		int l = mas.getCustomEntityManager("postgresMdas")
				.createNativeQuery(sql).executeUpdate();
	}

//		@Scheduled(cron = "0 0 0 2 * ?")
		@Transactional
		public void loadSurveyMonthlyConsumption() {
		/*
		 * String sql =
		 * "insert into meter_data.load_survey_monthly_consumption (accno,kno,mtrno,billmonth,kwh,kvah,kva)\n"
		 * +
		 * "(select m.accno,m.kno,c.meter_number,min(to_number(c.billmonth,'999999')) as billmonth,sum(c.kwh) as kwh,sum(c.kvah) as kvah,sum(c.kva) as kva  from \n"
		 * +
		 * "(select a.meter_number,to_char(a.read_time,'yyyyMM') as billmonth,sum(a.kwh)/1000 as kwh,sum(a.kvah)/1000 as kvah,sum(a.kva)/1000 as kva from meter_data.load_survey a, \n"
		 * +
		 * "(select meter_number,billing_date from meter_data.bill_history where to_char(billing_date,'HH24:MI:ss')='00:00:00' and to_char(billing_date,'yyyyMMdd')=to_char(current_timestamp,'yyyyMMdd')\n"
		 * +
		 * ") b  where a.meter_number=b.meter_number and a.read_time<=b.billing_date and a.read_time>b.billing_date- interval '1 month'   group by a.meter_number,to_char(a.read_time,'yyyyMM') order by to_char(a.read_time,'yyyyMM') desc ) c  left join meter_data.master_main m  on c.meter_number=m.mtrno group by c.meter_number,m.accno,m.kno)"
		 * ;
		 */String sql="insert into meter_data.load_survey_monthly_consumption (accno,kno,mtrno,billmonth,kwh,kvah,kva)\n" +
					"				select mma.accno,mma.kno,b.meter_number,to_char(CURRENT_DATE -interval '1 month','yyyyMM') as bm,b.kwh,b.kvah,b.kva from (select a.meter_number,sum(a.kwh) as kwh,sum(a.kvah) as kvah,sum(a.kva) as kva from meter_data.load_survey a where a.read_time>to_timestamp((to_char(CURRENT_DATE -interval '1 month','yyyy-MM-')||'01 00:00:00'),'yyyy-MM-dd HH24:MI:SS') and a.read_time<=to_timestamp((to_char(CURRENT_DATE ,'yyyy-MM-')||'01 00:00:00'),'yyyy-MM-dd HH24:MI:SS')  GROUP BY a.meter_number ) b LEFT JOIN meter_data.master_main mma on b.meter_number=mma.mtrno";
			
			int l = mas.getCustomEntityManager("postgresMdas")
					.createNativeQuery(sql).executeUpdate();
		}
	//@Scheduled(cron="0 0 1 * * ?")
	@Transactional
		public void todWiseDailyDataAggregation() {
		List<TodDefinitionEntity> l=tds.getAllTODSlots();
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();   
		 String cd=dateFormat.format(date).toString();
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DATE, -1);
		    Date yesterday = calendar.getTime();
		    String yd=dateFormat.format(yesterday).toString();
		    
		
		  if(l.size()==6) {
			  String tod1sm=l.get(0).getStart_time();
		  String[] tod1sa=tod1sm.split(":");
		  String tod1s;
		  if(tod1sa[1].equalsIgnoreCase("00")) {
			   tod1s=tod1sa[0].concat(":30");  
		  }
		  else {
			  tod1s= String.valueOf(Integer.parseInt(tod1sa[0])+1)+":00";
		  }
		  String tod1e=l.get(0).getEnd_time();
		  String tod2sm=l.get(1).getStart_time();
		  String[] tod2sa=tod2sm.split(":");
		  String tod2s;
		  if(tod2sa[1].equalsIgnoreCase("00")) {
			   tod2s=tod2sa[0].concat(":30");  
		  }
		  else {
			  tod2s= String.valueOf(Integer.parseInt(tod2sa[0])+1)+":00";
		  }
		  
		    String tod2e=l.get(1).getEnd_time();
		    String tod3sm=l.get(2).getStart_time();
			  String[] tod3sa=tod3sm.split(":");
			  String tod3s;
			  if(tod3sa[1].equalsIgnoreCase("00")) {
				   tod3s=tod3sa[0].concat(":30");  
			  }
			  else {
				  tod3s= String.valueOf(Integer.parseInt(tod3sa[0])+1)+":00";
			  }
			  
			    String tod3e=l.get(2).getEnd_time();
			    String tod4sm=l.get(3).getStart_time();
				  String[] tod4sa=tod4sm.split(":");
				  String tod4s;
				  if(tod4sa[1].equalsIgnoreCase("00")) {
					   tod4s=tod4sa[0].concat(":30");  
				  }
				  else {
					  tod4s= String.valueOf(Integer.parseInt(tod4sa[0])+1)+":00";
				  }
				  
				    String tod4e=l.get(3).getEnd_time();
				    String tod5sm=l.get(4).getStart_time();
					  String[] tod5sa=tod5sm.split(":");
					  String tod5s;
					  if(tod5sa[1].equalsIgnoreCase("00")) {
						   tod5s=tod5sa[0].concat(":30");  
					  }
					  else {
						  tod5s= String.valueOf(Integer.parseInt(tod5sa[0])+1)+":00";
					  }
					  
					    String tod5e=l.get(4).getEnd_time();
					    String tod6sm=l.get(5).getStart_time();
						  String[] tod6sa=tod6sm.split(":");
						  String tod6s;
						  String tod6ef = null;
						  if(tod6sa[1].equalsIgnoreCase("00")) {
							   tod6s=tod6sa[0].concat(":30");  
						  }
						  else {
							  tod6s= String.valueOf(Integer.parseInt(tod6sa[0])+1)+":00";
						  }
						  String tod6e = l.get(5).getEnd_time();
						  if(tod6e.equalsIgnoreCase("24:00")) {
							  tod6e="00:00";
							  tod6ef="24:00";
						  }
						  else {
							  cd=yd;
							  
						  }
						    
			    
		  String sql="insert into meter_data.tod_wise_daily_data_aggregation(meter_number,tod1s,tod1e,kwh1,kvah1,tod2s,tod2e,kwh2,kvah2,tod3s,tod3e,kwh3,kvah3,tod4s,tod4e,kwh4,kvah4,tod5s,tod5e,kwh5,kvah5,tod6s,tod6e,kwh6,kvah6,tod_gdate\r\n" + 
					")  (select i.meter_number,i.tod1s,i.tod1e,i.kwh1/1000 as kwh1 ,i.kvah1/1000 as kvah1,i.tod2s,i.tod2e,i.kwh2/1000 as kwh2 ,i.kvah2/1000 as kvah2,i.tod3s,i.tod3e,i.kwh3/1000 as kwh3 ,i.kvah3/1000 as kvah3,i.tod4s,i.tod4e,i.kwh4/1000 as kwh4 ,i.kvah4/1000 as kvah4,i.tod5s,i.tod5e,i.kwh5/1000 as kwh5 ,i.kvah5/1000 as kvah5,j.tod6s,j.tod6e,j.kwh6/1000 as kwh6,j.kvah6/1000 as kvah6,'"+yd+"' from (select g.*,h.tod5s,h.tod5e,h.kwh5,h.kvah5 from (select e.*,f.tod4s,f.tod4e,f.kwh4,f.kvah4 from (select c.*,d.tod3s,d.tod3e,d.kwh3,d.kvah3 from (select a.meter_number,a.tod1s,a.tod1e,a.kwh1,a.kvah1,b.tod2s,b.tod2e,b.kwh2,b.kvah2 from (select meter_number,'"+tod1sm+"' as tod1s,'"+tod1e+"' as tod1e,sum(kwh) as kwh1,sum(kvah) as kvah1  from meter_data.load_survey where read_time between '"+yd+" "+tod1s+":00' and '"+yd+" "+tod1e+":00' GROUP BY meter_number) a LEFT JOIN\n" +
					"(select meter_number,'"+tod2sm+"' as tod2s,'"+tod2e+"' as tod2e,sum(kwh) as kwh2,sum(kvah) as kvah2   from meter_data.load_survey where read_time between '"+yd+" "+tod2s+":00' and '"+yd+" "+tod2e+":00' GROUP BY meter_number) b on a.meter_number=b.meter_number) c LEFT JOIN\n" +
					"(select meter_number,'"+tod3sm+"' as tod3s,'"+tod3e+"' as tod3e,sum(kwh) as kwh3,sum(kvah) as kvah3   from meter_data.load_survey where read_time between '"+yd+" "+tod3s+":00' and '"+yd+" "+tod3e+":00' GROUP BY meter_number) d on c.meter_number=d.meter_number) e LEFT JOIN\n" +
					"(select meter_number,'"+tod4sm+"' as tod4s,'"+tod4e+"' as tod4e,sum(kwh) as kwh4,sum(kvah) as kvah4   from meter_data.load_survey where read_time between '"+yd+" "+tod4s+":00' and '"+yd+" "+tod4e+":00' GROUP BY meter_number) f\n" +
					"on e.meter_number=f.meter_number) g LEFT JOIN\n" +
					"(select meter_number,'"+tod5sm+"' as tod5s,'"+tod5e+"' as tod5e,sum(kwh) as kwh5,sum(kvah) as kvah5   from meter_data.load_survey where read_time between '"+yd+" "+tod5s+":00' and '"+yd+" "+tod5e+":00' GROUP BY meter_number) h on g.meter_number=h.meter_number) i LEFT JOIN\n" +
					"(select meter_number,'"+tod6sm+"' as tod6s,'"+tod6ef+"' as tod6e,sum(kwh) as kwh6,sum(kvah) as kvah6   from meter_data.load_survey where read_time between '"+yd+" "+tod6s+":00' and '"+cd+" "+tod6e+":00' GROUP BY meter_number) j on\n" +
					"i.meter_number=j.meter_number)";
			int i = mas.getCustomEntityManager("postgresMdas")
					.createNativeQuery(sql).executeUpdate();
		}
		}
	
	  //  @Scheduled(cron = "0 0 13/20 * * ?")
		@Transactional	
		public void dailyValidationProcess() {
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Date date = new Date();
		 //  System.out.println(dateFormat.format(date));
		   String toDate=dateFormat.format(date).toString();
	
		   Calendar calendar = Calendar.getInstance();
		   calendar.add(Calendar.DATE, -1);
		   Date yesterday = calendar.getTime();
		 //  System.out.println(dateFormat.format(yesterday).toString());
		   
		   VEEProcessController vpc =new VEEProcessController();	
		
		  vpc.rtcFailureProcess(toDate, toDate); vpc.missingLoadDataProcess(toDate,
		  toDate); vpc.incmpltloadDataProcess(toDate, toDate);
		  vpc.missingEventDataProcess(toDate, toDate);
		 vpc.invalidPowerfactorProcess(toDate, toDate);
		 vpc.theresoldFreqCheckProcess(toDate, toDate);
		 
		   vpc.spikeCheckProcess(toDate, toDate);
		}
		
//		@Scheduled(cron = "0 0 0 1 * ?")
		@Transactional
		public void monthlyValidationProcess() {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
		//	System.out.println(dateFormat.format(date));
			String toDate=dateFormat.format(date).toString();
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			Date previousmonth = calendar.getTime();
		//	System.out.println("Previous Date: -"+dateFormat.format(previousmonth).toString());
			String previousmonthDate=dateFormat.format(previousmonth).toString();
			
			
			VEEProcessController vpc =new VEEProcessController();
			vpc.lessKVAValueProcess(previousmonthDate, toDate);
//			vpc.theresoldFreqCheckProcess(previousmonthDate, toDate);
//			vpc.revenueProtValidProcess(previousmonthDate, toDate);
//			vpc.highConsValidProcess(previousmonthDate, toDate);
//			vpc.lowConsValidProcess(previousmonthDate, toDate);
//			vpc.zeroConsValidProcess(previousmonthDate, toDate);
//			vpc.energySumCheckProcess(previousmonthDate, toDate);
//			vpc.negativeConsumptionProcess(previousmonthDate, toDate);
			
			
			EstProcessController epc= new EstProcessController();
			epc.averageofIPProcess(previousmonthDate, toDate);
			//epc.lastYearValueProcess(previousmonthDate, toDate);
		}
		
		@Transactional
		@RequestMapping(value = "/load_survey_datapush", method = { RequestMethod.GET,RequestMethod.POST })
		public void load_survey_datapush() {
			
			String qry="";
			 List<Object[]> list = new ArrayList<>();
			 String readTime = null, meterNumber= null;
			 double i_b = 0 , v_b = 0;
			
			try{
				
				qry="select * from meter_data.load_survey_datapush";
				list = mas.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				
				System.out.println(list);
				
				for(int i=0;i<list.size();i++){
					Object[] li=(Object[]) list.get(i);
				
					meterNumber = li[0].toString();
					readTime = li[1].toString();
					i_b = Double.parseDouble(li[2].toString());
					v_b = Double.parseDouble(li[3].toString());
					
					System.out.println("Hi................"+meterNumber);
					System.out.println("Hi................"+readTime);
					System.out.println("Hi................"+v_b);
					System.out.println("Hi................"+i_b);
					
				AmrLoadEntity entity = new AmrLoadEntity();
				
				entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(readTime)));
				
				entity.setiB(i_b);
				entity.setvB(v_b);
				amrLoadService.customUpdate(entity);
				}		
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}

		Timestamp getTimeStamp(String value) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			try {
				return new Timestamp(format.parse(value).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
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
		
		
}
