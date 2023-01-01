package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.FilterConfig;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

class MasterThread extends Thread {
	String topic;
	MqttMessage data;
	FilterConfig filterConfig;

	MasterThread(FilterConfig filterConfig, String topic, MqttMessage data) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		this.topic = topic;
		this.data = data;
		this.filterConfig = filterConfig;
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
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		try {
			return new Timestamp(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
