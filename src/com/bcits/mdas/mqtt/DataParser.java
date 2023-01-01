package com.bcits.mdas.mqtt;

import javax.servlet.FilterConfig;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.bcits.mdas.entity.ModemCommunication;

class DataParser {

	private FilterConfig filterConfig;

	DataParser(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	boolean parseData(String topic, MqttMessage data) {

		switch (topic) {
		case Subscriber.TOPIC_BILL:
			new UpdateBills(filterConfig, topic, data).start();
			break;
		case Subscriber.TOPIC_EVENT:
			new UpdateEvent(filterConfig, topic, data).start();
			break;
		case Subscriber.TOPIC_INSTANT:
			new UpdateInstantaneous(filterConfig, topic, data).start();
			break;
		case Subscriber.TOPIC_LOAD:
			new UpdateLoad(filterConfig, topic, data).start();
			break;
		case Subscriber.TOPIC_IDEN:
			parseIden(data, topic);
			break;
		case Subscriber.TOPIC_DIAG:
			new UpdateDiagnosis(filterConfig, topic, data).start(); 
			break;
		}
		

		return false;
	}

	private boolean parseIden(MqttMessage data, String topic) {
		// {"MeterType":"HPL","MeterNumber":"00209015","DataType":"IDENTIFY","Size":"00164","FirmWare":"R7.01","IMEI":"864502030206577","IMSI":"\r\n4040705445154","SimNum":"89910725401345154536","MobileNum":"1111111111","Signal":"11","Temp":"0","SocketFailCount":"0","PdpFailCount":"0","ReadTime":"25/Oct/2017
		// 14:48:47
		// PM","MeterVersion":"HPLCTH1XXX03","CT":"200","PT":"300","LoadIntegPeriod":"15","ProtocolType":"DLMS
		// "}

		//System.out.println("IDENTIFICATION DATA=======");
		try {
			JSONObject obj = new JSONObject(data.toString());
			String MeterType = obj.optString("MeterType");// MAKE
			String MeterNumber = obj.optString("MeterNumber");
			String DataType = obj.optString("DataType");
			String FirmWare = obj.optString("FirmWare");
			String IMEI = obj.optString("IMEI");
			String SimNum = obj.optString("SimNum");
			String Signal = obj.optString("Signal");
			String MeterVersion = obj.optString("MeterVersion");
			String IMSI = obj.optString("IMSI");
			String MobileNum = obj.optString("MobileNum");
			String Temp = obj.optString("Temp");
			String SocketFailCount = obj.optString("SocketFailCount");
			String PdpFailCount = obj.optString("PdpFailCount");
			String ReadTime = obj.optString("ReadTime");
			String CT = obj.optString("CT");
			String PT = obj.optString("PT");
			String LoadIntegPeriod = obj.optString("LoadIntegPeriod");
			String ProtocolType = obj.optString("ProtocolType");

		/*	System.out.println("MeterType = " + MeterType);
			System.out.println("MeterNumber = " + MeterNumber);
			System.out.println("DataType = " + DataType);
			System.out.println("FirmWare = " + FirmWare);
			System.out.println("IMEI = " + IMEI);
			System.out.println("SimNum = " + SimNum);
			System.out.println("Signal = " + Signal);
			System.out.println("MeterVersion = " + MeterVersion);
			System.out.println("IMSI = " + IMSI);
			System.out.println("MobileNum = " + MobileNum);
			System.out.println("Temp = " + Temp);
			System.out.println("SocketFailCount = " + SocketFailCount);
			System.out.println("PdpFailCount = " + PdpFailCount);
			System.out.println("ReadTime = " + ReadTime);
			System.out.println("CT = " + CT);
			System.out.println("PT = " + PT);
			System.out.println("LoadIntegPeriod = " + LoadIntegPeriod);
			System.out.println("ProtocolType = " + ProtocolType);*/

			ModemCommunication modem = new ModemCommunication();
			modem.setSignal(Signal);
			modem.setTemperature(Temp);
			modem.setMeterConnFailCount(SocketFailCount);
			modem.setGprsConnFailCount(PdpFailCount);

			new UpdateCommunication(filterConfig, IMEI, MeterNumber, topic, modem).start();// TODO
			new UpdateMasterMain(filterConfig, IMEI, MeterNumber).start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
