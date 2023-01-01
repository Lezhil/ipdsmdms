package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.service.ModemCommunicationService;

public class UpdateCommunicationNew extends Thread {

	String meterNumber;
	String topic;

	ModemCommunicationService modemCommunication;

	public UpdateCommunicationNew(String meterNumber, String topic, ModemCommunicationService modemCommunication) {
		this.meterNumber = meterNumber;
		this.topic = topic;
		this.modemCommunication = modemCommunication;
	}

	@Override
	public void run() {
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = format.parse(format.format(new Date()));
			ModemCommunication modem = null;
			try {

				modem = modemCommunication.customfind((new KeyCommunication(meterNumber, today)));
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}

			if (null == modem) {
				modem = new ModemCommunication();
				modem.setMyKey(new KeyCommunication(meterNumber, today));
				modem.setTotalCommunication(Long.valueOf(0));
				modem.setTotalBill(Long.valueOf(0));
				modem.setTotalEvent(Long.valueOf(0));
				modem.setTotalInst(Long.valueOf(0));
				modem.setTotalLoad(Long.valueOf(0));

			}

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

			}

			try {
				modemCommunication.customupdateBySchema(modem, "postgresMdas");
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}

			// System.out.println("========================UPDATED MODEM COMMUNICATION");

		} catch (Exception e) {
			e.printStackTrace();
			// e.get
			System.out.println(e.getMessage());

		}
		super.run();
	}
}