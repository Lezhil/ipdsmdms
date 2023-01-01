package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.service.ModemCommunicationService;

class UpdateCommunication extends Thread{
	
	String imei;String meterNumber;String topic;
	
	@Autowired
	ModemCommunicationService modemCommunication;
	ModemCommunication modemStatus;
	
	UpdateCommunication(FilterConfig filterConfig, String imei,String meterNumber, String topic, ModemCommunication modemStatus) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		this.imei=imei;
		this.meterNumber=meterNumber;
		this.topic=topic;
		this.modemStatus=modemStatus;
	}
	
	@Override
	public void run() {
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
			Date today= format.parse(format.format(new Date()));
			
			ModemCommunication modem = modemCommunication.customfind((new KeyCommunication(imei, today)));
			
			//System.err.println(imei+" MODEM  "+modem);

			if(null==modem){
				modem= new ModemCommunication();
				modem.setMyKey(new KeyCommunication(imei, today));
				//modem.setMeterNumber(meterNumber);
				modem.setTotalCommunication(Long.valueOf(0));
				modem.setTotalBill(Long.valueOf(0));
				modem.setTotalEvent(Long.valueOf(0));
				modem.setTotalInst(Long.valueOf(0));
				modem.setTotalLoad(Long.valueOf(0));

			}
				//modem.setMeterNumber(meterNumber);
				modem.setLastCommunication(time);
				modem.setTotalCommunication(modem.getTotalCommunication()+1);
				
			switch (topic) {
			case Subscriber.TOPIC_BILL:
				modem.setLastSyncBill(time);
				modem.setTotalBill(modem.getTotalBill()+1);
				break;
			case Subscriber.TOPIC_EVENT:
				modem.setLastSyncEvent(time);
				modem.setTotalEvent(modem.getTotalEvent()+1);
				break;
			case Subscriber.TOPIC_INSTANT:
				modem.setLastSyncInst(time);
				modem.setTotalInst(modem.getTotalInst()+1);
				break;
			case Subscriber.TOPIC_LOAD:
				modem.setLastSyncLoad(time);
				modem.setTotalLoad(modem.getTotalLoad()+1);
				break;
			case Subscriber.TOPIC_IDEN:
				if(null!=modemStatus){
				modem.setSignal(modemStatus.getSignal());
				modem.setTemperature(modemStatus.getTemperature());
				modem.setMeterConnFailCount(modemStatus.getMeterConnFailCount());
				modem.setGprsConnFailCount(modemStatus.getGprsConnFailCount());
				}
				break;
			}

			
			if(modemCommunication.customupdateBySchema(modem,"postgresMdas") instanceof ModemCommunication){
				//System.out.println("========================UPDATED MODEM COMMUNICATION");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
}