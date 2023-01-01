/**
 * 
 */
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
import com.bcits.mdas.utility.FilterUnit;

/**
 * @author User
 *
 */
public class UpdateModemCommunication implements Runnable {

	String imei;
	String meterNumber;
	String topic;
	@Autowired
	ModemCommunicationService modemCommunication;
	ModemCommunication modemStatus;

	UpdateModemCommunication(String imei, String meterNumber, String topic) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, FilterUnit.filterConfig.getServletContext());
		this.imei = imei;
		this.meterNumber = meterNumber;
		this.topic = topic;

	}

	@Override
	public void run() {

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
//				 System.out.println("========================UPDATED MODEM COMMUNICATION");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
