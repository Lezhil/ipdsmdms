/**
 * 
 */
package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrEventsEntity.KeyEvent;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.utility.FilterUnit;

/**
 * @author Tarik
 *
 */
public class EventUpdateThread implements Runnable {

	@Autowired
	private AmrEventsService amrEventsService;

	@Autowired
	private ModemCommunicationService modemCommunicationService;

	@Autowired
	private MasterMainService mainService;

	AmrEventsEntity amiEvent;
	String data = null;
	String imei = null, meterNumber = null;

	EventUpdateThread(String data) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				FilterUnit.filterConfig.getServletContext());
		this.data = data;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void run() {

		try {
				JSONObject obj =  new JSONObject(data.toString());
				imei = obj.optString("Imei");
				meterNumber = obj.optString("MeterNumber");
				
				// Below parameter is not mapped into table
				String Status_Code = obj.optString("TamperCount");
				String EventLog_Seq_No = obj.optString("EventLog_Seq_No");
				String Vry = obj.optString("Vry"); 
				String Vby = obj.optString("Vby");
				String ClientID = obj.optString("ClientID");

				AmrEventsEntity entity = new AmrEventsEntity();
				entity.setMyKey(new KeyEvent(getString(meterNumber), getString(obj.optString("EventCode")), getTimeStamp(obj.optString("EventTime"))));
				entity.setTimeStamp(new Timestamp(new Date().getTime()));
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
				if(!obj.optString("ModemTime").isEmpty() || !"null".equalsIgnoreCase(obj.optString("ModemTime"))) {
					entity.setModemTime(getTimeStamp(obj.optString("ModemTime")));
				}
				entity.setTransId(getString(obj.optString("TransID")));
				
				//Not Req For New Integration				
//				entity.setEnergyKvah(getDouble(obj.optString("Energy_KVAH")));
//				entity.setEnergyKwh(getDouble(obj.optString("Energy_KWH")));
//				entity.setEnergyKwhExport(getDouble(obj.optString("Energy_KWH_Export")));
//				entity.setEnergyKwhImport(getDouble(obj.optString("Energy_KWH_Import")));	
//				entity.setKvah(getDouble(obj.optString("KVAH")));
//				entity.setStructureSize(getInteger(obj.optString("StructureSize")));
							
						
				try {
					if (amrEventsService.update(entity) instanceof AmrEventsEntity) {
//						 System.out.println("========================INSERTED EVENT Count= "+ i++);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
//					updateCommunication(imei, meterNumber, "evntjs");
//					updateMasterMain(imei, meterNumber);
					updateCommunicationBasedonReadTime(imei, meterNumber, "evntjs",obj.optString("EventTime"));
//					updateMasterMainBasedonReadTime(imei, meterNumber,obj.optString("EventTime"));
				} catch (Exception e) {
					e.printStackTrace();
				}

//				try {
//					new UpdateMasterMain(FilterUnit.filterConfig, imei, meterNumber).start();
//				} catch (Exception e) {
//				}
//				try {
//					new Thread(new UpdateModemCommunication(imei, meterNumber, "evntjs")).start();
//				} catch (Exception e) {
//				}
//				System.gc();

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
