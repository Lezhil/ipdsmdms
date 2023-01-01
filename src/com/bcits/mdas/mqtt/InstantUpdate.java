/**
 * 
 */
package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.FilterConfig;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.service.AmiInstantaneousService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.utility.FilterUnit;

/**
 * @author Tarik
 *
 */
public class InstantUpdate implements Runnable {

	@Autowired
	private AmiInstantaneousService amiInstantaneousService;

	@Autowired
	private MasterMainService mainService;
	
	@Autowired
	private ModemCommunicationService modemCommunicationService;

	AMIInstantaneousEntity amiInst;
	String data = null;
	String Imei = null, MeterNumber = null;

	InstantUpdate(String data) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				FilterUnit.filterConfig.getServletContext());
		this.data = data;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void run() {

		try {
			JSONObject obj = new JSONObject(data.toString());
			MeterNumber = obj.optString("MeterNumber");
			Imei = obj.optString("Imei");		
			
			// Below parameter is not mapped into table
			String KVARH_Q2 = obj.optString("KVARH_Q2"); //Not used
			String KVARH_Q3 = obj.optString("KVARH_Q3"); //Not used		
			String MDKW_Exp = obj.optString("MDKW_Exp"); //Not used
			String Date_MDKW_Exp = obj.optString("Date_MDKW_Exp"); //Not used
			String MDKVA_Exp = obj.optString("MDKVA_Exp"); //Not used
			String Date_MDKVA_Exp = obj.optString("Date_MDKVA_Exp"); //Not used
			
			
//			if (Imei == null || "null".equalsIgnoreCase(Imei) || "".equalsIgnoreCase(Imei)) {
//				try {
//					MasterMainEntity m = mainService.getFeederData(MeterNumber).get(0);
//					if (m != null) {
//						Imei = m.getModem_sl_no();
//					}
//				} catch (Exception e) {
//					e.getMessage();
//				}
//			}

			
			AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
			entity.setMyKey(new AMIKeyInstantaneous(MeterNumber, getTimeStamp(obj.optString("ReadTime"))));
			entity.setTimeStamp(new Timestamp(new Date().getTime()));
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
			

//			if (amiInstantaneousService.customupdateBySchema(entity,"postgresMdas") instanceof AMIInstantaneousEntity) {
//			}
			
			if (amiInstantaneousService.update(entity) instanceof AMIInstantaneousEntity) {
//			 System.out.println("UPDATED INSTATNTANEOUS=========== ");
		    }
			
			try {
//				updateCommunication(Imei, MeterNumber, "instjs");
//				updateMasterMain(Imei, MeterNumber);
				
				updateCommunicationBasedonReadTime(Imei, MeterNumber, "instjs",obj.optString("ReadTime"));
//				updateMasterMainBasedonReadTime(Imei, MeterNumber,obj.optString("ReadTime"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			

//			try {
////				if (Imei != null) {
//					new UpdateMasterMain(FilterUnit.filterConfig, Imei, MeterNumber).start();
////				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			try {
//				new Thread(new UpdateModemCommunication(Imei, MeterNumber, "instjs")).start();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.gc();

	}
	
	Date getDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return new Date(format.parse(value).getTime());
		} catch (ParseException e) {
//			e.printStackTrace();
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
//			e.printStackTrace();
		}
		return null;
	}

}
