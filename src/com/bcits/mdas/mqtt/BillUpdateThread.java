/**
 * 
 */
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

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.utility.FilterUnit;

/**
 * @author Tarik
 *
 */
public class BillUpdateThread implements Runnable {

	@Autowired
	private AmrBillsService amrBillsService;

	@Autowired
	private ModemCommunicationService modemCommunicationService;

	@Autowired
	private MasterMainService mainService;

	AmrBillsEntity amrBill;
	String data = null;
	String imei = null, meterNumber = null;

	BillUpdateThread(String data) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				FilterUnit.filterConfig.getServletContext());
		this.data = data;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void run() {

		try {

			JSONObject obj = new JSONObject(data.toString());
			String ClientID = obj.optString("ClientID");
			String transID = obj.optString("TransID");			
			meterNumber = obj.optString("MeterNumber");
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
//			entity.setMeterId(getString(METER_ID));
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
			entity.setFlag(getString(ClientID));
			
			

			if (amrBillsService.update(entity) instanceof AmrBillsEntity) {

			}
			
			try {
//				updateCommunication(imei, meterNumber, "billjs");
//				updateMasterMain(imei, meterNumber);
				updateCommunicationBasedonReadTime(imei, meterNumber, "billjs",obj.optString("BillingDate"));
//				updateMasterMainBasedonReadTime(imei, meterNumber,obj.optString("BillingDate"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
//			try {
//				new UpdateMasterMain(FilterUnit.filterConfig, imei, meterNumber).start();
//			} catch (Exception e) {
//				e.getMessage();
//			}
//			try {
//				new Thread(new UpdateModemCommunication(imei, meterNumber, "billjs")).start();
//			} catch (Exception e) {
//				e.getMessage();
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	Date getDate(String value) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return new Date(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
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
