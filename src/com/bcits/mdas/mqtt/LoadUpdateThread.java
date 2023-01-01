package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.entity.AmrLoadRawEntity;
import com.bcits.mdas.entity.AmrLoadRawEntity.KeyRawLoad;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.service.AmrLoadRawService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.utility.FilterUnit;

public class LoadUpdateThread  implements Runnable {
	
	@Autowired
	private AmrLoadService amrLoadService;
	
	@Autowired
	private AmrLoadRawService amrLoadRawService;

	@Autowired
	private ModemCommunicationService modemCommunicationService;

	@Autowired
	private MasterMainService mainService;

	AmrLoadEntity amiLoad;
	String data = null;
	String imei = null, meterNumber = null;

	LoadUpdateThread(String data) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				FilterUnit.filterConfig.getServletContext());
		this.data = data;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void run() {
		
		try {
			
				JSONObject obj = new JSONObject(data.toString());
				
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
				if(!obj.optString("ModemTime").isEmpty() || !"null".equalsIgnoreCase(obj.optString("ModemTime"))) {
					rawEntity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
				}
				rawEntity.setImei(getString(imei));
				rawEntity.setFrequency(getDouble(obj.optString("Frequency")));
				rawEntity.setPowerFactor(getDouble(obj.optString("Power_Factor")));				
				rawEntity.setKw(getDouble(obj.optString("kW")));
				rawEntity.setKva(getDouble(obj.optString("kVA")));
				rawEntity.setAverageCurrent(obj.optString("average_current"));
				rawEntity.setAverageVoltage(obj.optString("average_voltage"));
				rawEntity.setTransId(getString(obj.optString("TransID")));
//				rawEntity.setNetKwh(getDouble(obj.optString("NetKWH")));
//				rawEntity.setStructureSize(getInteger(obj.optString("structureSize")));			
				if (amrLoadRawService.update(rawEntity) instanceof AmrLoadRawEntity) {
//					 System.out.println("========================INSERTED LOAD");
				}
					
				//Data Insertion into Main table
				AmrLoadEntity entity = new AmrLoadEntity();
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
				entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(obj.optString("ReadTime"))));
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
				if(!obj.optString("ModemTime").isEmpty() || !"null".equalsIgnoreCase(obj.optString("ModemTime"))) {
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
//					 System.out.println("========================INSERTED LOAD");
				}

		
//				try {
//					new UpdateMasterMain(FilterUnit.filterConfig, imei, meterNumber).start();
//				} catch (Exception e) {
//					e.getMessage();
//				}
//				try {
//					new Thread(new UpdateModemCommunication(imei, meterNumber, "loadjs")).start();
//				} catch (Exception e) {
//					e.getMessage();
//				}
				
				
				try {
//					updateCommunication(imei, meterNumber, "loadjs");
//					updateMasterMain(imei, meterNumber);
					updateCommunicationBasedonReadTime(imei, meterNumber, "loadjs",obj.optString("ReadTime"));
//					updateMasterMainBasedonReadTime(imei, meterNumber,obj.optString("ReadTime"));
				} catch (Exception e) {
					e.printStackTrace();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	Date getDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return new Date(format.parse(value).getTime());
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}
	

	public void updateMasterMainBasedonReadTime(String imei, String meterNumber, String rDate) {
		try {

			MasterMainEntity mainEntity = mainService.getEntityByMtrNO(meterNumber);
			if (mainEntity != null) {
				mainEntity.setLast_communicated_date(getTimeStamp(rDate));
				mainService.update(mainEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateCommunicationBasedonReadTime(String imei, String meterNumber, String topic, String rDate) {

		try {
			Timestamp time = new Timestamp(new Date().getTime());
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			Date today = format.parse(format.format(rDate));

			ModemCommunication modem = modemCommunicationService.find((new KeyCommunication(meterNumber, getDate(rDate))));

			if (null == modem) {
				modem = new ModemCommunication();
				modem.setMyKey(new KeyCommunication(meterNumber, getDate(rDate)));
				modem.setImei(imei);
				modem.setTotalCommunication(Long.valueOf(0));
				modem.setTotalBill(Long.valueOf(0));
				modem.setTotalEvent(Long.valueOf(0));
				modem.setTotalInst(Long.valueOf(0));
				modem.setTotalLoad(Long.valueOf(0));

			}
			modem.setImei(imei);
			modem.setLastCommunication(getTimeStamp(rDate));
			modem.setTotalCommunication(modem.getTotalCommunication() + 1);
			modem.setLast_inserttime(time);

			switch (topic) {
			case Subscriber.TOPIC_BILL:
				modem.setLastSyncBill(getTimeStamp(rDate));
				modem.setTotalBill(modem.getTotalBill() + 1);
				break;
			case Subscriber.TOPIC_EVENT:
				modem.setLastSyncEvent(getTimeStamp(rDate));
				modem.setTotalEvent(modem.getTotalEvent() + 1);
				break;
			case Subscriber.TOPIC_INSTANT:
				modem.setLastSyncInst(getTimeStamp(rDate));
				modem.setTotalInst(modem.getTotalInst() + 1);
				break;
			case Subscriber.TOPIC_LOAD:
				modem.setLastSyncLoad(getTimeStamp(rDate));
				modem.setTotalLoad(modem.getTotalLoad() + 1);
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
			//e.printStackTrace();
		}
		return null;
	}


}
