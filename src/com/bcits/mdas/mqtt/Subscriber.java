package com.bcits.mdas.mqtt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.FilterConfig;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.bcits.mdas.utility.FilterUnit;

public class Subscriber implements MqttCallback {

	private static MqttClient client;
	private FilterConfig filterConfig;
	
	public static final String TOPIC_INSTANT = "instjs";
	public static final String TOPIC_IDEN = "idenjs";
	public static final String TOPIC_BILL = "billjs";
	public static final String TOPIC_LOAD = "loadjs";
	public static final String TOPIC_EVENT = "evntjs";
	public static final String TOPIC_DIAG = "diagjs";
	public static final String TOPIC_DAILYLOAD = "midNightData";
	
	public static final String TOPIC_RAW_TSYN = "tsyn";//1.1.	Time Synchronization : (tsyn)
	public static final String TOPIC_RAW_TR = "tr/";//1.2.	Time Synchronization Response: (tr/)
	public static final String TOPIC_RAW_SCAN = "scan";//1.3.	Scan Information : (scan)
	public static final String TOPIC_RAW_SR = "sr/";//1.4.	Scan Response Information : (sr/)
	public static final String TOPIC_RAW_IDEN = "iden";//1.5.	Identification Data : (iden)
	public static final String TOPIC_RAW_INST = "inst";//1.6.	Instantaneous Data : (inst)
	public static final String TOPIC_RAW_BILL = "bill";//1.7.	Energy Data : (bill)
	public static final String TOPIC_RAW_LOAD = "load";//1.8.	Load Survey Data : (load)
	public static final String TOPIC_RAW_EVNT = "evnt";//1.9.	Event/Tamper Data : (evnt)
	public static final String TOPIC_RAW_DIAG = "diag";//1.10.	Diagnostics Data : (diag)
	public static final String TOPIC_RAW_INSC = "insc";//1.11.	Instantaneous Scalar Data : (insc)
	public static final String TOPIC_RAW_BISC = "bisc";//1.12.	Energy Scalar Data : (bisc)
	public static final String TOPIC_RAW_LOSC = "losc";//1.13.	Load Survey Scalar Data : (losc)
	public static final String TOPIC_RAW_EVSC = "evsc";//1.14.	Events/Tamper Scalar Data : (evsc)
	public static final String TOPIC_RAW_IR = "Ir/";//1.15.	Scalar Request Data : (Ir/)
	public static final String TOPIC_RAW_LLAT = "llat";//1.16.	Latest Load Request Data : (llat)
	
	public Subscriber(FilterConfig filterConfig) {
		this.filterConfig=filterConfig;
			//BCITSLogger.logger.info("STARTING MQTT SUBSCRIBER");
	}

	public void getMeterData() {
		try {
			Random rand = new Random(); 
			String clientId=   rand.nextInt(99999999)+"BCITS_CLIENT_"+rand.nextInt(99999999); 
			System.out.println("CLENT ID === "+clientId);
			client = new MqttClient(FilterUnit.mqttURL,clientId);
			client.connect();
			client.setCallback(this);

			/*String topics[] = { TOPIC_EVENT, TOPIC_LOAD, TOPIC_BILL, TOPIC_IDEN, TOPIC_INSTANT,TOPIC_TEST };
			client.subscribe(topics);*/
	/*		String topicsRaw[] = { 
					TOPIC_RAW_TSYN,TOPIC_RAW_TR,TOPIC_RAW_SCAN,TOPIC_RAW_SR,TOPIC_RAW_IDEN,TOPIC_RAW_INST,
					TOPIC_RAW_BILL,TOPIC_RAW_LOAD,TOPIC_RAW_EVNT,TOPIC_RAW_DIAG,TOPIC_RAW_INSC,
					TOPIC_RAW_BISC,TOPIC_RAW_LOSC,TOPIC_RAW_EVSC,TOPIC_RAW_IR,TOPIC_RAW_LLAT,TOPIC_EVENT, TOPIC_LOAD, TOPIC_BILL, TOPIC_IDEN, TOPIC_INSTANT, TOPIC_DIAG};
*/			String topicsRaw[] = {TOPIC_EVENT, TOPIC_LOAD, TOPIC_BILL, TOPIC_IDEN, TOPIC_INSTANT, TOPIC_DIAG};
			
			client.subscribe(topicsRaw);

			
			String [] modemImeis={"864502030401285","861835034171725","861835034186293","861835034043692","861835034161197","864502030422729","861835034183027","861835033995975","861835034135431","861835034168010","861835034183548","861835034161882","861835034130598"};
			
			for (String modemNumber : modemImeis) {
				MqttMessage message = new MqttMessage();
			/*	message.setPayload(("3gc SETT;04:164.100.59.158;").getBytes());
				message.setQos(1);
				client.publish("ir/"+modemNumber, message);*/
				
				message.setPayload((modemNumber+";3GC;SCAN_PARAM").getBytes());
				message.setQos(1);
				client.publish("scan", message);
			
			/*	message= new MqttMessage();
				message.setPayload(("3GC;PULL;LOAD;"+modemNumber+";(17:11:01:00:00:00:;17:11:15:00:00:00:;)").getBytes());
				message.setQos(1);
				client.publish("LOAD", message);*/
				
				
			}
			 
			//BCITSLogger.logger.info("CONNECTED TO MQTT ");
		} catch (MqttException e) {
			e.printStackTrace();
			 //BCITSLogger.logger.info("EXCEPTION CONNECTION MQTT "+e.getMessage());
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection Lost " + cause.getMessage());
		 new Subscriber(filterConfig).getMeterData();// STARTING AGAIN
	}

	@Override
	public void messageArrived(String topic, MqttMessage data) throws Exception {
		 //BCITSLogger.logger.info("Topic: " + topic);
		 writeTrace(topic, data.toString()); //WRITING TO FILE
		if (data != null && !data.toString().isEmpty()) {
			DataParser parser = new DataParser(filterConfig);
			if (parser.parseData(topic, data)) {
					//System.out.println("SUCCESSFULLY PARSED");
			}
		}else{
			 //System.out.println("DATA EMPTY OR NULL =============================");
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Delivery Complete " + token.toString());
		  //BCITSLogger.logger.info("Delivery Complete " + token.toString());

	}
	
	public static String sendMqttMessage(String topic, String payLoad) {
		try {
			MqttMessage mqMsg = new MqttMessage();
			mqMsg.setPayload(payLoad.getBytes());
			mqMsg.setQos(1);
			client.publish(topic, mqMsg);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		}
		
	}
	
	private void writeTrace(String fileName, String data) {
		try {
			PrintWriter fileWriter = null;
			try {
					fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(getFolderPath()+"/"+fileName + ".txt", true)));
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
	
	private String getFolderPath(){
		try {
			String month=FilterUnit.logFolder+"/"+new SimpleDateFormat("yyyyMM").format(new Date());
			String day=month+"/"+new SimpleDateFormat("yyyyMMdd").format(new Date());
			if(FilterUnit.folderExists(month)){
				if(FilterUnit.folderExists(day)){
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}