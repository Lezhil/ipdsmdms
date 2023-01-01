package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.FilterConfig;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.entity.ModemDiagnosisEntity;
import com.bcits.mdas.service.ModemDiagnosisService;

class UpdateDiagnosis extends MasterThread {

	@Autowired
	private ModemDiagnosisService modemDiag;

	UpdateDiagnosis(FilterConfig filterConfig, String topic, MqttMessage data) {
		super(filterConfig, topic, data);
	}

	@Override
	public void run() {
		super.run();

		// {"MeterType":"SE1","MeterNumber":"13401030","DataType":"DIAGINFO","Size":74,"TransId":"0000000000000",
		// "DiagType":"METERSTS","Status":"AARJ","Imei":"861835034168010","TimeStamp":"30/Nov/2017 17:45:03 PM"}
		try {
			//System.out.println("DIAGNOSIS DATA=======");

			JSONObject obj = new JSONObject(data.toString());
			String MeterType = obj.optString("MeterType");
			String MeterNumber = obj.optString("MeterNumber");
			String DataType = obj.optString("DataType");
			String DiagType = obj.optString("DiagType");
			String Imei = obj.optString("Imei");
			String Status = obj.optString("Status");
			String TimeStamp = obj.optString("TimeStamp");

			/*System.out.println("MeterType = " + MeterType);
			System.out.println("MeterNumber = " + MeterNumber);
			System.out.println("DataType = " + DataType);
			System.out.println("DiagType = " + DiagType);
			System.out.println("Imei = " + Imei);
			System.out.println("Status = " + Status);
			System.out.println("TimeStamp = " + TimeStamp);*/

			ModemDiagnosisEntity modem = new ModemDiagnosisEntity();
			modem.setDataType(DataType);
			modem.setDate(new Date());
			modem.setDiagType(DiagType);
			modem.setImei(Imei);
			modem.setMeterNumber(MeterNumber);
			modem.setMeterType(MeterType);
			modem.setStatus(Status);
			modem.setTimeStamp(new Timestamp(new Date().getTime()));
			modem.setTrackedTime(getTimeStamp(TimeStamp));

			if (modemDiag.customupdatemdas(modem) instanceof ModemDiagnosisEntity) {
			//	System.out.println("========================INSERTED DIAGNOSIS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
