package com.bcits.mdas.controller;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.entity.OnDemandTransaction;
import com.bcits.mdas.jsontoobject.OnDemand;
import com.bcits.mdas.jsontoobject.RegisterValues;
import com.bcits.mdas.jsontoobject.Samples;
import com.bcits.mdas.jsontoobject.SearchMeterJson;
import com.bcits.mdas.mqtt.UpdateCommunicationNew;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrDailyLoadService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.OnDemandServiceInst;
import com.bcits.mdas.service.OnDemandServiceTransactionLog;
import com.bcits.mdas.service.OnDemandServicenamepla;
import com.bcits.mdas.service.OndemandTranService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Controller
public class HESOnDemandProfileController {
	@Autowired
	HESController hesc;
	@Autowired
	OnDemandServiceInst ons;
	@Autowired
	OnDemandServicenamepla odp;
	@Autowired
	OnDemandServiceTransactionLog odtl;
	@Autowired
	AmrEventsService oCurEvt;
	@Autowired
	public AmrBillsService amrBillsService;
	@Autowired
	public AmrLoadService amrLoadService;
	@Autowired
	public OndemandTranService ods;
	
	@Autowired
	private ModemCommunicationService modemCommunication;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private AmrDailyLoadService amrDailyLoadService;
	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;


	@RequestMapping(value = "/onDemandProfile/INSTANTANEOUS/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String instantaneous(HttpServletRequest req,
			@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
		
		SimpleDateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd'T' HH:mm:ss'+'0SSS", Locale.US);
		stadate =todaysDate.format(new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L));
		stopdate =  todaysDate.format(new Date());
		
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.println(timeStamp );

		System.out.println("stopdate"  +stopdate+"METER-"+meterId);
		//stadate = ""
		
		String s = hesc.onDemandProfileAMI(profileType, meterId, stadate, stopdate);
		// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
		if(s==null){
			return "NoData";
			
		}
		else{
		Gson gson = new Gson();
		
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();
		MasterMainEntity mmEntity = masterMainService.getEntityByMtrNO(meterId);
		System.out.println("#########"+mmEntity.getMtrno());
		for (int i = 0; i < sam.length; i++) {

			AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
            OnDemandTransaction odt=new OnDemandTransaction();
			entity.setTimeStamp(new Timestamp(new Date().getTime()));

			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Timestamp t=null;
			entity.setFlag("OD");
			RegisterValues[] rv = sa.getRegisterValues();
			for (int r = 0; r < rv.length; r++) {
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {
				
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
					entity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));
					t=new Timestamp(format.parse(s1[0]).getTime());
					
				}
				if (obc.equalsIgnoreCase("1.0.31.7.0.255")) {
					entity.setiR(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));

				} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) {
					entity.setiY(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));

				} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) {
					entity.setiB(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
				} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) {
					entity.setvR(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) {
					entity.setvY(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) {
					entity.setvB(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
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
					entity.setFrequency(Double.parseDouble(rv[r].getFormattedValue().replace("Hz", "")));
				} else if (obc.equalsIgnoreCase("1.0.9.7.0.255")) {
					entity.setKva(Double.parseDouble(rv[r].getFormattedValue().replace("VA", "")));
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
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
					entity.setBillingDate(new Timestamp(format.parse(s1[0]).getTime()));
					
				} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
					entity.setKwh(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
				} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {
					entity.setKwhExp(Double.parseDouble(rv[r]
							.getFormattedValue().replace("Wh", "")));
				} else if (obc.equalsIgnoreCase("1.0.5.8.0.255")) {
					entity.setKvarhLag(Double.parseDouble(rv[r]
							.getFormattedValue().replace("varh", "")));
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
					entity.setKvah(Double.parseDouble(rv[r].getFormattedValue().replace("VAh", "")));
				} else if (obc.equalsIgnoreCase("1.0.10.8.0.255")) {
					entity.setKvahExp(Double.parseDouble(rv[r]
							.getFormattedValue().replace("VAh", "")));
				} else if (obc.equalsIgnoreCase("1.0.1.6.0.255")
						&& atr.equalsIgnoreCase("2")) {
					entity.setMdKw(Double.parseDouble(rv[r].getFormattedValue().replace("W", "")));
				} else if (obc.equalsIgnoreCase("1.0.1.6.0.255")
						&& atr.equalsIgnoreCase("5")) {
					entity.setDateMdKw(rv[r].getFormattedValue());
				} else if (obc.equalsIgnoreCase("1.0.9.6.0.255")
						&& atr.equalsIgnoreCase("2")) {
					entity.setMdKva(Double.parseDouble(rv[r]
							.getFormattedValue().replace("VA", "")));
				} else if (obc.equalsIgnoreCase("1.0.9.6.0.255")
						&& atr.equalsIgnoreCase("5")) {
					entity.setDateMdKva(rv[r].getFormattedValue());
				} else if (obc.equalsIgnoreCase("1.0.12.7.0.255")) {
					entity.setPowerVoltage(rv[r].getFormattedValue().replace("V", ""));
				} else if (obc.equalsIgnoreCase("1.0.11.7.0.255")) {
					entity.setPhaseCurrent(rv[r].getFormattedValue().replace("A", ""));
				} else if (obc.equalsIgnoreCase("1.0.91.7.0.255")) {
					entity.setNeutralCurrent(rv[r].getFormattedValue().replace("A", ""));
				} else if (obc.equalsIgnoreCase("0.0.94.91.14.255")) {
					entity.setTotalPowerOnDuration(Integer.parseInt(rv[r]
							.getFormattedValue().replace(" min.", "")));
				}
				
				
				
			}
			entity.setMyKey(new AMIKeyInstantaneous(meterId, t));
           ons.customupdateBySchema(entity, "postgresMdas");
           odt.setType("Instantaneous");
           odt.setMeterNumber(meterId);
           odt.setOndemTime(new Timestamp(new Date().getTime()));
           ods.customupdateBySchema(odt, "postgresMdas");
		} 
		try {
   			new UpdateCommunicationNew(meterId, "instjs",modemCommunication).start();
   			mmEntity.setLast_communicated_date( new Date());
   			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
		return "Succ";
		}
	}
	
	@RequestMapping(value = "/onDemandProfile/NAMEPLATE/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })

	public @ResponseBody String namePlateProfile(HttpServletRequest req,
			@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
		String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,
				stopdate);
		if(s==null){
			return "NoData";
			
		}
		// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
		Gson gson = new Gson();
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();

		for (int i = 0; i < sam.length; i++) {

			NamePlate entity = new NamePlate();
			OnDemandTransaction odt=new OnDemandTransaction();
			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
					);
			
			RegisterValues[] rv = sa.getRegisterValues();
			for (int r = 0; r < rv.length; r++) {
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				if (obc.equalsIgnoreCase("0.0.96.1.0.255")) {
				
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
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
				}/* else if (obc.equalsIgnoreCase("1.0.0.4.2.255")) {
					entity.setName_plate2(rv[r].getFormattedValue());
				} else if (obc.equalsIgnoreCase("1.0.0.4.3.255")) {
					entity.setName_plate3(rv[r].getFormattedValue());
				} */else if (obc.equalsIgnoreCase("0.0.96.1.4.255")) {
					entity.setYear_of_manufacture(rv[r].getFormattedValue());
				} 
			}

			entity.setFlag("OD");
				odp.customupdateBySchema(entity, "postgresMdas");
				odt.setType("nameplate");
		           odt.setMeterNumber(meterId);
		           odt.setOndemTime(new Timestamp(new Date().getTime()));
		           ods.customupdateBySchema(odt, "postgresMdas");

			
		}
		try {
   			new UpdateCommunicationNew(meterId, "loadjs",modemCommunication).start();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
		return "Succ";

	}
	//Below code is for parsing BILLING data added By Amit.N
			@RequestMapping(value = "/onDemandProfile/BILLING/{profileType}/{meterId}/{stadate}/{stopdate}", method = {RequestMethod.POST, RequestMethod.GET })
	
			public @ResponseBody String billingProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,@PathVariable String stadate, @PathVariable String stopdate)throws ParseException {
				String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,
						stopdate);
				if(s==null){
					return "NoData";
					
				}
				Gson gson = new Gson();
				OnDemand ins = gson.fromJson(s, OnDemand.class);
				Samples[] sam = ins.getSamples();
				MasterMainEntity mmEntity = masterMainService.getEntityByMtrNO(meterId);
				for (int i = 0; i < sam.length; i++) {

					AmrBillsEntity abe = new AmrBillsEntity();
					KeyBills kbEntity = new KeyBills();
					OnDemandTransaction odt=new OnDemandTransaction();
					abe.setTimeStamp(new Timestamp(new Date().getTime()));

					Samples sa = sam[i];
					String time = sa.getTime();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
							Locale.ENGLISH);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
							);
					
					
					
					//abe.setMyKey(new KeyBills(getString(meterId), getTimeStamp(rv[r].getFormattedValue())));
//					abe.setMyKey(new setMyKey(meterId, dateFormat
//							.parse(dateFormat.format(new Date()))));
					RegisterValues[] rv = sa.getRegisterValues();
					for (int r = 0; r < rv.length; r++) {
						String obc = rv[r].getFormattedRegisterObisCode();
						String atr = rv[r].getAttributeId();

							if(obc.equalsIgnoreCase("0.0.0.1.2.255")){
								
														
								String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
								abe.setMyKey(new KeyBills(meterId, new Timestamp(format.parse(s1[0]).getTime())));
								kbEntity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));
														
							}else if (obc.equalsIgnoreCase("1.0.13.0.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								abe.setSysPfBilling(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwh(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.1.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz1(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.2.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz2(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.3.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz3(value);
							}
							 
							else if(obc.equalsIgnoreCase("1.0.1.8.4.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz4(value);
							}
						
							else if(obc.equalsIgnoreCase("1.0.1.8.5.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz5(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.6.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz6(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.7.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz7(value);
							}
							else if(obc.equalsIgnoreCase("1.0.1.8.8.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKwhTz8(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvah(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.1.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz1(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.2.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz2(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.3.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz3(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.4.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz4(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.5.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz5(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.6.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz6(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.7.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz7(value);
							}
							else if(obc.equalsIgnoreCase("1.0.9.8.8.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								
								double value = Double.parseDouble(splitStr[0]);
								abe.setKvahTz8(value);
							}
							//From Here 
							else if(obc.equalsIgnoreCase("1.0.1.6.0.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setOccDateKw(null);
									}else if
									(str.contains("+"))
									{			
										abe.setOccDateKw(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setDemandKw(value);
									}
								
								
							/*	if(str.equals("")){					
								str = str.replace("", "0test");
								}					
								if(str.contains("+"))
								{
								abe.setOccDateKw(str);
								}else if (str.contains("0test")) {
									abe.setOccDateKw(null);
								}else {					
								String[] splitStr = str.split("\\s+");
								double value = Double.parseDouble(splitStr[0]);
								abe.setDemandKw(value);
								
								}*/
												
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.1.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKwTz1(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz1(parseDate(str));
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz1(value);
									}
								
								
								
							/*	
								if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz1(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz1(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz1(value);
									
									}*/
							
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.2.255")){
								String str =rv[r].getFormattedValue();
								
								
								
								if(str.equals("")){					
									abe.setDateKwTz2(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz2(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz2(value);
									}
							/*
								if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz2(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz2(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz2(value);
									
									}*/
								
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.3.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKwTz3(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz3(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz3(value);
									}
								/*
								if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz3(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz3(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz3(value);
									
									}*/
								
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.4.255")){
								String str =rv[r].getFormattedValue();
								
								
								if(str.equals("")){					
									abe.setDateKwTz4(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz4(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz4(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz4(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz4(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz4(value);
									
									}
								*/
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.5.255")){
								String str =rv[r].getFormattedValue();
								if(str.equals("")){					
									abe.setDateKwTz5(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz5(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz5(value);
									}
								
								
/*								if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz5(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz5(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz5(value);
									
									}
								*/
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.6.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKwTz6(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz6(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz6(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz6(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz6(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz6(value);
									
									}*/
								
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.7.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKwTz7(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz7(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz7(value);
									}
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz7(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz7(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz7(value);
									
									}*/
								
							}
							else if(obc.equalsIgnoreCase("1.0.1.6.8.255")){
								String str =rv[r].getFormattedValue();
								if(str.equals("")){					
									abe.setDateKwTz8(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKwTz8(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz8(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKwTz8(str);
									}else if (str.contains("0test")) {
										abe.setDateKwTz8(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKwTz8(value);
									
									}
								*/
							}
							//From 2nd here
							
							else if(obc.equalsIgnoreCase("1.0.9.6.0.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKva(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKva(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKva(value);
									}
								
							/*	if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKva(str);
									}else if (str.contains("0test")) {
										abe.setDateKva(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKva(value);
									
									}*/
								
						
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.1.255")){
								String str =rv[r].getFormattedValue();
								if(str.equals("")){					
									abe.setDateKvaTz1(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz1(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz1(value);
									}
								
								
							/*	if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz1(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz1(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz1(value);
									
									}*/
							
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.2.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKvaTz2(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz2(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz2(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz2(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz2(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz2(value);
									
									}*/
								
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.3.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKvaTz3(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz3(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz3(value);
									}
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz3(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz3(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz3(value);
									
									}*/
										
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.4.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKvaTz4(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz4(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz4(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz4(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz4(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz4(value);
									
									}*/
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.5.255")){
								String str =rv[r].getFormattedValue();
							
								if(str.equals("")){					
									abe.setDateKvaTz5(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz5(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz5(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz5(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz5(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz5(value);
									
									}*/
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.6.255")){
								String str =rv[r].getFormattedValue();
								
								if(str.equals("")){					
									abe.setDateKvaTz6(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz6(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz6(value);
									}
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz6(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz6(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz6(value);
									
									}*/
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.7.255")){
								String str =rv[r].getFormattedValue();
								if(str.equals("")){					
									abe.setDateKvaTz7(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz7(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz7(value);
									}
								
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz7(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz7(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz7(value);
									
									}*/
							}
							else if(obc.equalsIgnoreCase("1.0.9.6.8.255")){
								String str =rv[r].getFormattedValue();
								if(str.equals("")){					
									abe.setDateKvaTz8(null);
									}else if
									(str.contains("+"))
									{			
										abe.setDateKvaTz8(parseDate(str));
										
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz8(value);
									}
								
								
								/*if(str.equals("")){					
									str = str.replace("", "0test");
									}					
									if(str.contains("+"))
									{
									abe.setDateKvaTz8(str);
									}else if (str.contains("0test")) {
										abe.setDateKvaTz8(null);
									}else {					
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									abe.setKvaTz8(value);
									
									}*/
							}else if(obc.equalsIgnoreCase("0.0.94.91.13.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setBillPowerOnDuration(splitStr[0]);
							}
							else if(obc.equalsIgnoreCase("1.0.2.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setBillKwhExport(splitStr[0]);
							}
							else if(obc.equalsIgnoreCase("1.0.10.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setBillKvahExport(splitStr[0]);
							}
							else if(obc.equalsIgnoreCase("1.0.5.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setReactiveImpActiveImp(splitStr[0]);
							}
							else if(obc.equalsIgnoreCase("1.0.6.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setReactiveImpActiveExp(splitStr[0]);
								
							}
							else if(obc.equalsIgnoreCase("1.0.7.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setReactiveExpActiveExp(splitStr[0]);
								
							}
							else if(obc.equalsIgnoreCase("1.0.8.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");
								//double value = Double.parseDouble(splitStr[0]);
								abe.setReactiveExpActiveImp(splitStr[0]);
							}
							}
					abe.setFlag("OD");
					amrBillsService.customupdateBySchema(abe,"postgresMdas");
					odt.setType("billing");
			           odt.setMeterNumber(meterId);
			           odt.setOndemTime(new Timestamp(new Date().getTime()));
			           ods.customupdateBySchema(odt, "postgresMdas");
				} 
				 try {
			   			new UpdateCommunicationNew(meterId, "billjs",modemCommunication).start();
			   			mmEntity.setLast_communicated_date( new Date());
			   			masterMainService.customupdateBySchema(mmEntity, "postgresMdas");
			   		} catch (Exception e) {
			   			e.printStackTrace();
			   		}
			
				return "Succ";
			}
			
			
			@RequestMapping(value = "/onDemandProfile/BULKLOAD/{profileType}/{meterId}/{stadate}/{stopdate}", method = {RequestMethod.POST, RequestMethod.GET })
		
			public @ResponseBody String bulkLoadProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,@PathVariable String stadate, @PathVariable String stopdate)throws ParseException {
				
				
				if (profileType.equalsIgnoreCase("BULKLOAD")){
					
					String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,
							stopdate);
					if(s==null){
						return "NoData";
						
					}
					// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
					Gson gson = new Gson();
					OnDemand ins = gson.fromJson(s, OnDemand.class);
					Samples[] sam = ins.getSamples();

					for (int i = 0; i < sam.length; i++) {

						AmrLoadEntity entity = new AmrLoadEntity();
						OnDemandTransaction odt=new OnDemandTransaction();
						KeyLoad kentity = new KeyLoad();
						entity.setTimeStamp(new Timestamp(new Date().getTime()));
							
						Samples sa = sam[i];
						String time = sa.getTime();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
								Locale.ENGLISH);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
								);
						String[] s1 = null;
					
						/*entity.setMyKey(new KeyInstantaneous(meterId, dateFormat
								.parse(dateFormat.format(new Date()))));*/
						RegisterValues[] rv = sa.getRegisterValues();
						for (int r = 0; r < rv.length; r++) {
							String obc = rv[r].getFormattedRegisterObisCode();
							String atr = rv[r].getAttributeId();
							
							if(obc.equalsIgnoreCase("0.0.1.0.0.255")){
								//	entity.setTimeStamp(new Timestamp(new Date().getTime()));
								s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
								
								kentity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));
									
								}else if (obc.equalsIgnoreCase("1.0.31.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setiR(value);

								}else if(obc.equalsIgnoreCase("1.0.51.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setiY(value);
								}else if(obc.equalsIgnoreCase("1.0.71.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setiB(value);
								}else if(obc.equalsIgnoreCase("1.0.32.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setvR(value);
								}
								else if(obc.equalsIgnoreCase("1.0.52.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setvY(value);
								}else if(obc.equalsIgnoreCase("1.0.72.27.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setvB(value);
								}else if(obc.equalsIgnoreCase("1.0.1.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setKwh(value);
								}else if(obc.equalsIgnoreCase("1.0.2.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setBlockEnergyKwhExp(value);
								}else if(obc.equalsIgnoreCase("1.0.5.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setKvarhQ1(value);
									entity.setKvarhLag(value);
								}else if(obc.equalsIgnoreCase("1.0.6.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setKvarhQ2(value);
								}else if(obc.equalsIgnoreCase("1.0.7.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setKvarhQ3(value);
								}else if (obc.equalsIgnoreCase("1.0.8.29.0.255")) {
									String str = rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");
									double value = Double.parseDouble(splitStr[0]);
									entity.setKvarhQ4(value);
									entity.setKvarhLead(value);
								} 
								else if(obc.equalsIgnoreCase("1.0.9.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setKvah(value);
								}else if(obc.equalsIgnoreCase("1.0.10.29.0.255")){
									String str =rv[r].getFormattedValue();
									String[] splitStr = str.split("\\s+");						
									double value = Double.parseDouble(splitStr[0]);
									entity.setBlockEnergyKvahExp(value);
								}//new 
								else if(obc.equalsIgnoreCase("1.0.12.27.0.255")){
									entity.setAverageVoltage(rv[r].getFormattedValue().replace("V", ""));
								}
								else if(obc.equalsIgnoreCase("1.0.11.27.0.255")){
									entity.setAverageCurrent(rv[r].getFormattedValue().replace("A", ""));
								}
								else if(obc.equalsIgnoreCase("1.0.91.27.0.255")){
									entity.setNeutralCurrent(rv[r].getFormattedValue().replace("A", ""));
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
						entity.setMyKey(new KeyLoad(meterId,  new Timestamp(format.parse(s1[0]).getTime())));
							entity.setFlag("OD");
							amrLoadService.customupdateBySchema(entity,"postgresMdas");
							odt.setType("bulkload");
					           odt.setMeterNumber(meterId);
					           odt.setOndemTime(new Timestamp(new Date().getTime()));
					           ods.customupdateBySchema(odt, "postgresMdas");
						}
						
						}
						
					return "Success";
				}
			//Below code is to parse Daily load to Load survey table 
			@RequestMapping(value = "/onDemandProfile/DAILYLOAD/{profileType}/{meterId}/{stadate}/{stopdate}", method = {RequestMethod.POST, RequestMethod.GET })
	
			public @ResponseBody String dailyLoadProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,@PathVariable String stadate, @PathVariable String stopdate)throws ParseException {
				String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,
						stopdate);
				if(s==null){
					return "NoData";
					
				}
				// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
				Gson gson = new Gson();
				OnDemand ins = gson.fromJson(s, OnDemand.class);
				Samples[] sam = ins.getSamples();

				for (int i = 0; i < sam.length; i++) {

					AmrDailyLoadEntity entity = new AmrDailyLoadEntity();
					KeyLoad kentity = new KeyLoad();
					OnDemandTransaction odt=new OnDemandTransaction();
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
						
					Samples sa = sam[i];
					String time = sa.getTime();
					String[] s1 = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
							Locale.ENGLISH);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
							);
					
				
					/*entity.setMyKey(new KeyInstantaneous(meterId, dateFormat
							.parse(dateFormat.format(new Date()))));*/
					RegisterValues[] rv = sa.getRegisterValues();
					for (int r = 0; r < rv.length; r++) {
						String obc = rv[r].getFormattedRegisterObisCode();
						String atr = rv[r].getAttributeId();
						if(obc.equalsIgnoreCase("0.0.1.0.0.255")){
							//	entity.setTimeStamp(new Timestamp(new Date().getTime()));
								 s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
								//entity.setMyKey(new KeyLoad(meterId,  new Timestamp(format.parse(s1[0]).getTime())));
								kentity.setReadTime(new Timestamp(format.parse(s1[0]).getTime()));
							}else if (obc.equalsIgnoreCase("1.0.1.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_active_import_energy(value);

							}else if(obc.equalsIgnoreCase("1.0.2.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_active_export_energy(value);
								
								
							}else if(obc.equalsIgnoreCase("1.0.9.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_apparent_import_energy(value);
								
							}else if(obc.equalsIgnoreCase("1.0.10.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_apparent_export_energy(value);
							}
						
						//Setting reactive Energy
							else if(obc.equalsIgnoreCase("1.0.5.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_reactive_energy5(value);
							}
						
							else if(obc.equalsIgnoreCase("1.0.6.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_reactive_energy6(value);
							}
						
							else if(obc.equalsIgnoreCase("1.0.7.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_reactive_energy7(value);
							}
						
							else if(obc.equalsIgnoreCase("1.0.8.8.0.255")){
								String str =rv[r].getFormattedValue();
								String[] splitStr = str.split("\\s+");						
								double value = Double.parseDouble(splitStr[0]);
								entity.setCum_reactive_energy8(value);
							}
						
						
							
						}
					entity.setMyKey(new DailyKeyLoad(meterId,  new Timestamp(format.parse(s1[0]).getTime())));
					entity.setFlag("OD");
					amrDailyLoadService.customupdateBySchema(entity,"postgresMdas");
						odt.setType("dailyload");
				           odt.setMeterNumber(meterId);
				           odt.setOndemTime(new Timestamp(new Date().getTime()));
				           ods.customupdateBySchema(odt, "postgresMdas");
					}
				try {
		   			new UpdateCommunicationNew(meterId, "loadjs",modemCommunication).start();
		   		} catch (Exception e) {
		   			e.printStackTrace();
		   		}
				return "Success";
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
				SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss",Locale.ENGLISH);
				try {
					
					System.out.println("This is the format ----->"+new Timestamp(format.parse(value).getTime()));
					
					return  new Timestamp(format.parse(value).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		
		
	@RequestMapping(value = "/onDemandProfile/TRANSACTIONEVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	
	public @ResponseBody String transactionEventLogProfile(HttpServletRequest req,
			@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
		
		//System.out.println("--comes to HES on Demand Profile CurrentEventLOG--");
		//System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
		String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
		//System.out.println("result=="+s);
		if(s==null){
			return "NoData";
			
		}
		Gson gson = new Gson();
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();
		//System.out.println("sam length-->"+sam.length);
		
		for (int i = 0; i < sam.length; i++) {

			AmrEventsEntity entity = new AmrEventsEntity();
			OnDemandTransaction odt=new OnDemandTransaction();
			Timestamp event_time=null;
			String eventCode="";

			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			RegisterValues[] rv = sa.getRegisterValues();
			for (int r = 0; r < rv.length; r++) {
				
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				
				
				if (obc.equalsIgnoreCase("0.0.96.11.3.255")) {  //eventCode
					eventCode=rv[r].getFormattedValue();
				}
				else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
					event_time=new Timestamp(format.parse(s1[0]).getTime());
				}
				
				} 
			 entity.setFlag("OD");
			entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
			oCurEvt.customupdateBySchema(entity,"postgresMdas");
			odt.setType("Transaction Event Log");
	           odt.setMeterNumber(meterId);
	           odt.setOndemTime(new Timestamp(new Date().getTime()));
	           ods.customupdateBySchema(odt, "postgresMdas");
	          
			}
		try {
   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
		return "Succ";
		
		}
	
	@RequestMapping(value = "/onDemandProfileAMI", method = {RequestMethod.POST, RequestMethod.GET })
			
	public String instantaneousm(HttpServletRequest req, ModelMap model) {
		
		//String modemID=req.getParameter("mIDOndem");
		
		/*
		 * String
		 * s="select * from (select meter_number,'Instantaneous',time_stamp,id from meter_data.amiinstantaneous where iflag='OD'"
		 * +
		 * " UNION select meter_number,'Load',time_stamp,id from meter_data.load_survey where flag='OD'"
		 * +
		 * " union select meter_number,'Bill History',time_stamp,id from meter_data.bill_history where flag='OD' "
		 * +
		 * "union select meter_number,'Event',time_stamp,id from meter_data.events where flag='OD' "
		 * +
		 * "union select mtrno,'DailyLoad',time_stamp,id from meter_data.daily_load where flag='OD') A ORDER BY A.time_stamp desc;"
		 * ;
		 */
		 
		
	
			
		
		  String s= "(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction);";
		  System.out.println(s);
		  
		  
		  
		  List<Object[]>  l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList(); 
		 
		  model.addAttribute("ondedata", l);
		 
	
		return "onDemandProfile";
	}
	
	
	//@SuppressWarnings({ "unused", "unchecked" })
		@Transactional
		@RequestMapping(value = "/onDemandProfileAMITransData", method = {RequestMethod.POST, RequestMethod.GET })	
		public  @ResponseBody Object  transdataTest(HttpServletRequest req, Model model) {
		
			

			
			String modemID=req.getParameter("mIDOndem");
			String s="";
			String qry="";
			String qry1="";
			//String msg = null;
		   List<Object[]> l=null;
			
			
			try {  
				  
				
				  if(modemID!="") {
					  
					   qry= "INSERT INTO meter_data.ondemand_transaction(id,type,meternumber,time_stamp)		\r\n" + 
						  		"		\r\n" + 
						  		"(select id,type,meter_number,time_stamp from (select id,'Instantaneous' as type,meter_number,time_stamp from meter_data.amiinstantaneous where iflag='OD' and meter_number='"+modemID+"'\r\n" + 
						  		"				  UNION select id,'Load' as type,meter_number,time_stamp from meter_data.load_survey where flag='OD' and meter_number='"+modemID+"'\r\n" + 
						  		"				  union select id,'Bill History' as type,meter_number,time_stamp from meter_data.bill_history where flag='OD' and meter_number='"+modemID+"'\r\n" + 
						  		"				 union select id,'Event' as type,meter_number,time_stamp from meter_data.events where flag='OD' and meter_number='"+modemID+"'\r\n" + 
						  		"				 union select id, 'DailyLoad' as type,mtrno,time_stamp from meter_data.daily_load where flag='OD' and mtrno='"+modemID+"' ) A ORDER BY A.time_stamp desc);";
					  
					  System.out.println(qry);
					  
			
					   int ls = entityManager.createNativeQuery(qry).executeUpdate(); 
					 // List<Object[]> ls=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
					  //System.out.println(ls);
				  } if(modemID!=""){
					  
					    qry="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction where meternumber='"+modemID+"');";
						System.out.println("11"+qry);
						 l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
						 System.out.println("inside else");
						//msg = "synched";

					}else {
						 s="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction);";
							System.out.println("B"+s);
							l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
				}
				  
				 
				 
	  			}catch (NoResultException nre) {
					nre.printStackTrace();
				}
				  
			
			// System.out.println(s);
			// l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
			 
			 
			/*if(modemID!="") {
				
				   qry= "INSERT INTO meter_data.ondemand_transaction(id,type,meternumber,time_stamp)		\r\n" + 
					  		"		\r\n" + 
					  		"(select id,type,meter_number,time_stamp from (select id,'Instantaneous' as type,meter_number,time_stamp from meter_data.amiinstantaneous where iflag='OD' and meter_number='"+modemID+"'\r\n" + 
					  		"				  UNION select id,'Load' as type,meter_number,time_stamp from meter_data.load_survey where flag='OD' and meter_number='"+modemID+"'\r\n" + 
					  		"				  union select id,'Bill History' as type,meter_number,time_stamp from meter_data.bill_history where flag='OD' and meter_number='"+modemID+"'\r\n" + 
					  		"				 union select id,'Event' as type,meter_number,time_stamp from meter_data.events where flag='OD' and meter_number='"+modemID+"'\r\n" + 
					  		"				 union select id, 'DailyLoad' as type,mtrno,time_stamp from meter_data.daily_load where flag='OD' and mtrno='"+modemID+"' ) A ORDER BY A.time_stamp desc);";
				   qry1="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction);";
				   
				  System.out.println(qry);
				  System.out.println(qry1);
				 // l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
				}
			
			if(modemID=="") {
				
			 s= "(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction);";
			 System.out.println(s);
			}else if(modemID!=""){
				s="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction where meternumber='"+modemID+"');";
				 System.out.println(s);
			}*/
			   
			
			 //l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
				
			// model.addAttribute("ondedata", l);	 
				
	
			 return l; 
			
			
		}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/onDemandProfileAMITransDataTest", method = {RequestMethod.POST, RequestMethod.GET })	
	public  @ResponseBody Object  transdata(HttpServletRequest req, Model model) {
	
		

		String qry;
		String modemID=req.getParameter("mIDOndem");
		String s="";
		 List<Object[]> l=null;
		String msg = null;
			
		
		  			try {  
					  
					  
			
		  				s="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction where meternumber='"+modemID+"');";
						  l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
						  System.out.println("A"+s);
						
					  
					  
					/*  if(modemID!="") {
							
							s="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction where meternumber='"+modemID+"');";
							  l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
							  System.out.println("A"+s);
							
						}else {
							
							 s="(select meternumber,type,time_stamp,id from meter_data.ondemand_transaction);";
								System.out.println("B"+s);
								l=amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
							
						}*/
					 
		  			}catch (NoResultException nre) {
						nre.printStackTrace();
					}
					  
					
		
			 
			 return l;
		
		
	}
	
	
	
	
	
	//OnDemand Current Event Log
	@RequestMapping(value = "/onDemandProfile/CURRENTEVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public @ResponseBody String currentEventLogProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
		
		System.out.println("--comes to HES on Demand Profile CurrentEventLOG--");
		System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
		String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
		System.out.println("result=="+s);
		if(s==null){
			return "NoData";
			
		}
		Gson gson = new Gson();
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();
		System.out.println("sam length-->"+sam.length);
		
		for (int i = 0; i < sam.length; i++) {

			AmrEventsEntity entity = new AmrEventsEntity();
			OnDemandTransaction odt=new OnDemandTransaction();
			entity.setTimeStamp(new Timestamp(new Date().getTime()));

			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp event_time=null;
			String eventCode="";
			RegisterValues[] rv = sa.getRegisterValues();
			for (int r = 0; r < rv.length; r++) {
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				
				
				if (obc.equalsIgnoreCase("0.0.96.11.1.255")) {  //eventCode
					eventCode=rv[r].getFormattedValue();
				}
				if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
					event_time=new Timestamp(format.parse(s1[0]).getTime());
				}
				if (obc.equalsIgnoreCase("1.0.31.7.0.255")) {         //i_r
					entity.setiR(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
				} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) {  //i_y
					entity.setiY(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
				} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) {  //i_b
					entity.setiB(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
					
				} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) {  //v_r
					entity.setvR(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) {  //v_y
					entity.setvY(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) {   //v_b
					entity.setvB(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) {   //pf_r
					entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
				} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) {   //pf_y
					entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
				} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) {   //pf_b
					entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));
					
				} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {    //kwh 
					entity.setKwh(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
				} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {   //kwh_exp
					entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
				} 
				} 
			entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
			entity.setFlag("OD");
			//oCurEvt.customSave(entity);
			oCurEvt.customupdateBySchema(entity, "postgresMdas");
			odt.setType("Current Event Log");
	           odt.setMeterNumber(meterId);
	           odt.setOndemTime(new Timestamp(new Date().getTime()));
	           ods.customupdateBySchema(odt, "postgresMdas");
			}
		try {
   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
		return "Succ";
		
		}
	
	//OnDemand Voltage Event Log
	@RequestMapping(value = "/onDemandProfile/VOLTAGEEVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public @ResponseBody String voltageEventLogProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
		
		System.out.println("--comes to HES on Demand Profile VoltageEventLOG--");
		System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
		String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
		System.out.println("result=="+s);
		if(s==null){
			return "NoData";
			
		}
		Gson gson = new Gson();
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();
		for (int i = 0; i < sam.length; i++) {

			AmrEventsEntity entity = new AmrEventsEntity();
			OnDemandTransaction odt=new OnDemandTransaction();
			entity.setTimeStamp(new Timestamp(new Date().getTime()));

			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			RegisterValues[] rv = sa.getRegisterValues();
			Timestamp event_time=null;
			String eventCode="";
			for (int r = 0; r < rv.length; r++) {
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				if (obc.equalsIgnoreCase("0.0.96.11.0.255")) {  //eventcode
					eventCode=rv[r].getFormattedValue();
				}
				if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
					String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
					event_time=new Timestamp(format.parse(s1[0]).getTime());
				}
				if (obc.equalsIgnoreCase("1.0.31.7.0.255")) {         //i_r
					entity.setiR(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
				} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) {  //i_y
					entity.setiY(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
				} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) {  //i_b
					entity.setiB(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
					
				} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) {  //v_r
					entity.setvR(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) {  //v_y
					entity.setvY(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
				} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) {   //v_b
					entity.setvB(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
					
				} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) {   //pf_r
					entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
				} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) {   //pf_y
					entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
				} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) {   //pf_b
					entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));
					
				} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {    //kwh 
					entity.setKwh(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
				} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {   //kwh_exp
					entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
				} 
				} 
			System.out.println(meterId+ " -- "+eventCode+" -- "+event_time);
			entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
			entity.setFlag("OD");
			//oCurEvt.customSave(entity);
			oCurEvt.customupdateBySchema(entity, "postgresMdas");
			odt.setType("Voltage Event Log");
	           odt.setMeterNumber(meterId);
	           odt.setOndemTime(new Timestamp(new Date().getTime()));
	           ods.customupdateBySchema(odt, "postgresMdas");
			}
		try {
   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
		return "Succ";
		
		}
	
	
	//OnDemand OTHEREVENTLOG
		@RequestMapping(value = "/onDemandProfile/OTHEREVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		@Transactional
		public @ResponseBody String otherEventLogProfile(HttpServletRequest req,@PathVariable String profileType, @PathVariable String meterId,
				@PathVariable String stadate, @PathVariable String stopdate)
				throws ParseException {
			
			String value="Succ";
			System.out.println("--comes to HES on Demand Profile otherEventLOG--");
			System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
			String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
			System.out.println("result other event=="+s);
			if(s==null){
				return "NoData";
				
			}
			Gson gson = new Gson();
			OnDemand ins = gson.fromJson(s, OnDemand.class);
			Samples[] sam = ins.getSamples();
			System.out.println("sam length-->"+sam.length);
			
			for (int i = 0; i < sam.length; i++) {

				AmrEventsEntity entity = new AmrEventsEntity();
				OnDemandTransaction odt=new OnDemandTransaction();
				entity.setTimeStamp(new Timestamp(new Date().getTime()));

				Samples sa = sam[i];
				String time = sa.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Timestamp event_time=null;
				String eventCode="";
				RegisterValues[] rv = sa.getRegisterValues();
				for (int r = 0; r < rv.length; r++) {
					String obc = rv[r].getFormattedRegisterObisCode();
					String atr = rv[r].getAttributeId();
					
					
					if (obc.equalsIgnoreCase("0.0.96.11.4.255")) {  //eventCode
						eventCode=rv[r].getFormattedValue();
					}
					if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
						String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
						event_time=new Timestamp(format.parse(s1[0]).getTime());
					}
					if (obc.equalsIgnoreCase("1.0.31.7.0.255")) {         //i_r
						entity.setiR(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
					} else if (obc.equalsIgnoreCase("1.0.51.7.0.255")) {  //i_y
						entity.setiY(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
					} else if (obc.equalsIgnoreCase("1.0.71.7.0.255")) {  //i_b
						entity.setiB(Double.parseDouble(rv[r].getFormattedValue().replace("A", "")));
						
					} else if (obc.equalsIgnoreCase("1.0.32.7.0.255")) {  //v_r
						entity.setvR(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
					} else if (obc.equalsIgnoreCase("1.0.52.7.0.255")) {  //v_y
						entity.setvY(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
					} else if (obc.equalsIgnoreCase("1.0.72.7.0.255")) {   //v_b
						entity.setvB(Double.parseDouble(rv[r].getFormattedValue().replace("V", "")));
						
					} else if (obc.equalsIgnoreCase("1.0.33.7.0.255")) {   //pf_r
						entity.setPfR(Double.parseDouble(rv[r].getFormattedValue()));
					} else if (obc.equalsIgnoreCase("1.0.53.7.0.255")) {   //pf_y
						entity.setPfY(Double.parseDouble(rv[r].getFormattedValue()));
					} else if (obc.equalsIgnoreCase("1.0.73.7.0.255")) {   //pf_b
						entity.setPfB(Double.parseDouble(rv[r].getFormattedValue()));
						
					} else if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {    //kwh 
						entity.setKwh(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
					} else if (obc.equalsIgnoreCase("1.0.2.8.0.255")) {   //kwh_exp
						entity.setKwhExp(Double.parseDouble(rv[r].getFormattedValue().replace("Wh", "")));
					} 
					} 
				entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
				entity.setFlag("OD");
				//oCurEvt.customSave(entity);
				oCurEvt.customupdateBySchema(entity, "postgresMdas");
				odt.setType("Other Event Log");
		           odt.setMeterNumber(meterId);
		           odt.setOndemTime(new Timestamp(new Date().getTime()));
		           ods.customupdateBySchema(odt, "postgresMdas");
				}
			try {
	   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}
			if(sam.length==0)
			{
				value="NoData";
			}
			return value;
			
			}
		
		@RequestMapping(value = "/onDemandProfile/NONROLLOVEREVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		
		public @ResponseBody String nonRollOverEventLogProfile(HttpServletRequest req,
				@PathVariable String profileType, @PathVariable String meterId,
				@PathVariable String stadate, @PathVariable String stopdate)
				throws ParseException {
			
			//System.out.println("--comes to HES on Demand Profile CurrentEventLOG--");
			//System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
			String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
			//System.out.println("result=="+s);
			if(s==null){
				return "NoData";
				
			}
			Gson gson = new Gson();
			OnDemand ins = gson.fromJson(s, OnDemand.class);
			Samples[] sam = ins.getSamples();
			//System.out.println("sam length-->"+sam.length);
			
			for (int i = 0; i < sam.length; i++) {

				AmrEventsEntity entity = new AmrEventsEntity();
				OnDemandTransaction odt=new OnDemandTransaction();
				Timestamp event_time=null;
				String eventCode="";

				Samples sa = sam[i];
				String time = sa.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				RegisterValues[] rv = sa.getRegisterValues();
				for (int r = 0; r < rv.length; r++) {
					
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					String obc = rv[r].getFormattedRegisterObisCode();
					String atr = rv[r].getAttributeId();
					
					
					if (obc.equalsIgnoreCase("0.0.96.11.5.255")) {  //eventCode
						eventCode=rv[r].getFormattedValue();
					}
					else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
						String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
						event_time=new Timestamp(format.parse(s1[0]).getTime());
					}
					
					} 
				entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
				entity.setFlag("OD");
				oCurEvt.customupdateBySchema(entity,"postgresMdas");
				odt.setType("Non Roll Over Event Log");
		           odt.setMeterNumber(meterId);
		           odt.setOndemTime(new Timestamp(new Date().getTime()));
		           ods.customupdateBySchema(odt, "postgresMdas");
		           
				}
			try {
	   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}
			return "Succ";
			
			}
		@RequestMapping(value = "/onDemandProfile/CONTROLEDEVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		
		public @ResponseBody String controledEventLogProfile(HttpServletRequest req,
				@PathVariable String profileType, @PathVariable String meterId,
				@PathVariable String stadate, @PathVariable String stopdate)
				throws ParseException {
			
			//System.out.println("--comes to HES on Demand Profile CurrentEventLOG--");
			//System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
			String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
			//System.out.println("result=="+s);
			if(s==null){
				return "NoData";
				
			}
			Gson gson = new Gson();
			OnDemand ins = gson.fromJson(s, OnDemand.class);
			Samples[] sam = ins.getSamples();
			//System.out.println("sam length-->"+sam.length);
			
			for (int i = 0; i < sam.length; i++) {

				AmrEventsEntity entity = new AmrEventsEntity();
				OnDemandTransaction odt=new OnDemandTransaction();
				Timestamp event_time=null;
				String eventCode="";

				Samples sa = sam[i];
				String time = sa.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				RegisterValues[] rv = sa.getRegisterValues();
				for (int r = 0; r < rv.length; r++) {
					
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					String obc = rv[r].getFormattedRegisterObisCode();
					String atr = rv[r].getAttributeId();
					
					
					if (obc.equalsIgnoreCase("0.0.96.11.6.255")) {  //eventCode
						eventCode=rv[r].getFormattedValue();
					}
					else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
						String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
						event_time=new Timestamp(format.parse(s1[0]).getTime());
					}
					
					} 
		
				entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
				entity.setFlag("OD");
				oCurEvt.customupdateBySchema(entity,"postgresMdas");
				odt.setType("Controled Event Log");
		           odt.setMeterNumber(meterId);
		           odt.setOndemTime(new Timestamp(new Date().getTime()));
		           ods.customupdateBySchema(odt, "postgresMdas");
				}
			try {
	   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}
			return "Succ";
			
			}
		@RequestMapping(value = "/onDemandProfile/POWEREVENTLOG/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		
		public @ResponseBody String powerEventLogProfile(HttpServletRequest req,
				@PathVariable String profileType, @PathVariable String meterId,
				@PathVariable String stadate, @PathVariable String stopdate)
				throws ParseException {
			
			//System.out.println("--comes to HES on Demand Profile CurrentEventLOG--");
			//System.out.println("profileType :"+profileType+" meterId :"+meterId+ " stadate:"+stadate+" stopdate:"+stopdate);
			String s = hesc.onDemandProfileAMI(profileType, meterId, stadate,stopdate);
			//System.out.println("result=="+s);
			if(s==null){
				return "NoData";
				
			}
			Gson gson = new Gson();
			OnDemand ins = gson.fromJson(s, OnDemand.class);
			Samples[] sam = ins.getSamples();
			//System.out.println("sam length-->"+sam.length);
			
			for (int i = 0; i < sam.length; i++) {

				AmrEventsEntity entity = new AmrEventsEntity();
				OnDemandTransaction odt=new OnDemandTransaction();
				Timestamp event_time=null;
				String eventCode="";

				Samples sa = sam[i];
				String time = sa.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				RegisterValues[] rv = sa.getRegisterValues();
				for (int r = 0; r < rv.length; r++) {
					
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					String obc = rv[r].getFormattedRegisterObisCode();
					String atr = rv[r].getAttributeId();
					
					
					if (obc.equalsIgnoreCase("0.0.96.11.2.255")) {  //eventCode
						eventCode=rv[r].getFormattedValue();
					}
					else if (obc.equalsIgnoreCase("0.0.1.0.0.255")) {  //eventtime
						String[] s1=rv[r].getFormattedValue().replace("T", " ").split("\\+");
						event_time=new Timestamp(format.parse(s1[0]).getTime());
					}
					
					} 
		
				entity.setMyKey(new KeyEvent(meterId,eventCode,event_time));
				entity.setFlag("OD");
				oCurEvt.customupdateBySchema(entity,"postgresMdas");
				odt.setType("Power Event Log");
		           odt.setMeterNumber(meterId);
		           odt.setOndemTime(new Timestamp(new Date().getTime()));
		           ods.customupdateBySchema(odt, "postgresMdas");
				}
			try {
	   			new UpdateCommunicationNew(meterId, "evntjs",modemCommunication).start();
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}
			return "Succ";
			
			}
		

		@RequestMapping(value = "/onDemandProfile/METERSEARCH/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		@Transactional
		public @ResponseBody
		String meterSearchProfile(HttpServletRequest req,
				@PathVariable String profileType, @PathVariable String meterId,
				@PathVariable String stadate, @PathVariable String stopdate)
				throws ParseException {

			NamePlate entity = new NamePlate();
			String s = hesc.searchMeters();
			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<SearchMeterJson>>() {
			}.getType();
			List<SearchMeterJson> smm = gson.fromJson(s, collectionType);
			for (Iterator iterator = smm.iterator(); iterator.hasNext();) {

				SearchMeterJson searchMeterJson = (SearchMeterJson) iterator.next();
				entity.setMeter_serial_number(searchMeterJson.getMeterId());
				entity.setManufacturer_name(searchMeterJson.getVendor());
				entity.setFirmware_version(searchMeterJson.getFirmwareVersion());
				odp.customupdatemdas(entity);
			}
			return "Succ";

		}
	
		public String parseDate(String obisDate){
			
			
			String [] strr=obisDate.replace("+",",").split(",");
			
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss a");
			Date date = null;
			try {
				date = inputFormat.parse(strr[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//hardcode here and check
			String validDate = outputFormat.format(date);
			
			return validDate;
			
		}

		 @RequestMapping(value = "/getHESType/{id}", method = { RequestMethod.GET, RequestMethod.POST})
			public @ResponseBody String getHESType(@PathVariable("id") String id,HttpServletRequest request) 
			{
			  String hesValue = null;
				String qry = "Select hes_type from meter_data.master_main where mtrno = '"+id+"' "; 
			  try {
				  hesValue = 	(String) amrLoadService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
				  return hesValue;	
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return "No HES type";
		  
			}	  
		
		
		 
		 @RequestMapping(value = "/newOnDemandProfileAMI", method = {RequestMethod.POST, RequestMethod.GET })
			public String newOnDemandProfileAMI(HttpServletRequest req, Model model) {
				
				return "onDemandProfileNew";
			}
		 
		 
		 
	}
	


