package com.bcits.utility.amr;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Locale;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class XmlHelper {
       
	//KEY STRINGS OF PARAMETERS
	   final String ENERGY = "KWH";
	   final String CURRENT = "CURRENT";
	   final String VOLTAGE = "VOLTAGE";
	   final String PF="PF";
	   final String KVA = "KVA";
	   
	   // KEY STRINGS OF METERS
	   final String SECURE ="SECURE";
	   final String EHL ="EHL";
	   final String LNT ="L&T";
	   final String LARSEN ="LARSEN";
	   final String GENUS ="GENUS";
	   final String HPL ="HPL";
	  
	   // METER MAKE VERSIONS
	   final String GENUS_HC101="HC101";
	   final String GENUS_HC001="HC001";
	   final String GENUS_LC002="LC002";
	   final String GENUS_LC501="LC501";
	   final String GUNUS_LC001="LC001";
	   final String SECURE_DLM2506="DLM2506";
	   final String SECURE_M1XXG02="M1XXG02";
	   final String SECURE_M1XXG03="M1XXG03";
	  
	   
	   //EVENT MAIN KEYS
	   final String KEY_EVENT_VOLTAGE ="COLUMN_0.0.99.98.0.255"; // EVENTS VOLTAGE RELATED
	   final String KEY_EVENT_CURRENT ="COLUMN_0.0.99.98.1.255"; // EVENTS CURRENT
	   final String KEY_EVENT_POWER   ="COLUMN_0.0.99.98.2.255"; // EVENTS POWER
	   final String KEY_EVENT_TRANSACTION ="COLUMN_0.0.99.98.3.255"; // EVENTS TRANSACTION
	   final String KEY_EVENT_OTHER ="COLUMN_0.0.99.98.4.255"; // EVENTS OTHER
	   final String KEY_EVENT_NON_ROLLOVER ="COLUMN_0.0.99.98.5.255"; // EVENTS NON-ROLLOVER
	   final String KEY_EVENT_CONTROL ="COLUMN_0.0.99.98.6.255"; // EVENTS CONTROL
	   
	   // KEY PARAMCODES
	   final String PARAM_KWH = "P7-1-5-1-0";
	   final String PARAM_KVAH = "P7-3-5-1-0";
	   final String PARAM_PF = "P4-4-4-0-0"; //kwh/kvah
	   final String PARAM_KW = "P7-4-5-1-0"; //kwh*2
	   final String PARAM_KVA = "P7-6-5-1-0"; //kvah*2

	   final String PARAM_VOLTAGE_R = "P1-2-1-1-0";
	   final String PARAM_VOLTAGE_Y = "P1-2-2-1-0";
	   final String PARAM_VOLTAGE_B = "P1-2-3-1-0";

	   final String PARAM_CURRENT_R = "P2-1-1-1-0";
	   final String PARAM_CURRENT_Y = "P2-1-2-1-0";
	   final String PARAM_CURRENT_B = "P2-1-3-1-0";
	   
	   final String PARAM_PF_R = "P4-1-1-0-0";
	   final String PARAM_PF_Y = "P4-2-1-0-0";
	   final String PARAM_PF_B = "P4-3-1-0-0";
	   
	   final String OBIS_PF="1.0.13.7.0.255";
	   final String OBIS_VOLTAGE_R="1.0.32.7.0.255";
	   final String OBIS_CURRENT_R="1.0.31.7.0.255";
	   final String OBIS_KWH = "1.0.1.8.0.255";
	   
	   public static final String OBIS_BILL_DATE = "0.0.0.1.2.255";
	   public static final String OBIS_BILL_KWH = "1.0.1.8.0.255";
	   public static final String OBIS_BILL_KVAH = "1.0.9.8.0.255";
	   public static final String OBIS_BILL_MAX_DEMAND_KVA = "1.0.9.6.0.255";
	   public static final String OBIS_BILL_PF = "1.0.13.0.0.255";
	   
	   public static final String OBIS_LOAD_DATETIME = "0.0.1.0.0.255";
	   public static final String OBIS_LOAD_CURRENTR = "1.0.31.27.0.255";
	   public static final String OBIS_LOAD_CURRENTY = "1.0.51.27.0.255";
	   public static final String OBIS_LOAD_CURRENTB = "1.0.71.27.0.255";
	   public static final String OBIS_LOAD_VOLTAGER = "1.0.32.27.0.255";
	   public static final String OBIS_LOAD_VOLTAGEY = "1.0.52.27.0.255";
	   public static final String OBIS_LOAD_VOLTAGEB = "1.0.72.27.0.255";
	   public static final String OBIS_LOAD_KWH		 = "1.0.1.29.0.255";
	   public static final String OBIS_LOAD_KVARHLAG = "1.0.5.29.0.255";
	   public static final String OBIS_LOAD_KVARHLEAD = "1.0.8.29.0.255";
	   public static final String OBIS_LOAD_KVAH 	= "1.0.9.29.0.255";
	   
	   public static final String OBIS_EVENT_DATETIME= "0.0.1.0.0.255";
	   public static final String OBIS_EVENT_KWH  =    "1.0.1.8.0.255";
	   public static final String OBIS_EVENT_CURRENT_R="1.0.31.7.0.255";
	   public static final String OBIS_EVENT_CURRENT_Y="1.0.51.7.0.255";
	   public static final String OBIS_EVENT_CURRENT_B="1.0.71.7.0.255";
	   public static final String OBIS_EVENT_VOLTAGE_R="1.0.32.7.0.255";
	   public static final String OBIS_EVENT_VOLTAGE_Y="1.0.52.7.0.255";
	   public static final String OBIS_EVENT_VOLTAGE_B="1.0.72.7.0.255";
	   public static final String OBIS_EVENT_PF_R="1.0.33.7.0.255";
	   public static final String OBIS_EVENT_PF_Y="1.0.53.7.0.255";
	   public static final String OBIS_EVENT_PF_B="1.0.73.7.0.255";
		
	   
	   String getRoundedSnapShots(String reading,String type, String meterMake,String mekeVersion,String obisCode) {//XXX EVENTS
		 	
				switch (type) {
				case CURRENT:// CURRENT RELATED EVENTS
					
					if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){
//						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.0000");
					
						if(mekeVersion.equals(SECURE_M1XXG03) || mekeVersion.equals(SECURE_M1XXG02)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.0000");
						}
						else if(mekeVersion.equals(SECURE_DLM2506)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*10)).toString(), "0.0000");
						}
						else{
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.0000");
						}
						
					}
					else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode))).toString(), "0.0000");
					}
					else if(meterMake.contains(GENUS)){
						if(mekeVersion.contains("HC101.594")){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*10)).toString(), "0.0000");

						}else{
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode))).toString(), "0.0000");
						}
						/*
						if(mekeVersion.contains(GENUS_HC101)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.01)).toString(), "0.0000");// DIVIDE BY 100
						}
						else if(mekeVersion.contains(GENUS_LC002)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.001)).toString(), "0.0000");// DIVIDE BY 1000

						}
					*/}
					
					break;
				case VOLTAGE:// VOLTAGE RELATED EVENTS
					
					if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
//						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.00");
						if(mekeVersion.equals(SECURE_M1XXG03) || mekeVersion.equals(SECURE_M1XXG02)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.00");
						}
						else if(mekeVersion.equals(SECURE_DLM2506)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*10)).toString(), "0.00");
						}
						else{
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode)*1000)).toString(), "0.00");
						}
						
					}
					else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode))).toString(), "0.00");
					}
					else if(meterMake.contains(GENUS)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(obisCode))).toString(), "0.00");
					}
					
					break;
				case PF:// POWER FACTOR RELATED EVENTS
					if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
						if(mekeVersion.equals(SECURE_M1XXG03) || mekeVersion.equals(SECURE_M1XXG02)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_PF))).toString(), "0.000");
						}
						else if(mekeVersion.equals(SECURE_DLM2506)){
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.01)).toString(), "0.000");
						}
						else{
							return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_PF))).toString(), "0.000");
						}
						
					}
					else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.01)).toString(), "0.000");
					}
					else if(meterMake.contains(GENUS)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_PF))).toString(), "0.000");
					}
					
					break;
				case ENERGY:// KWH RELATED EVENTS
					
					if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_EVENT_KWH)*.001)).toString(), "0.000"); //scalar/1000
					}
					else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_EVENT_KWH)*.001)).toString(), "0.000");//scalar/1000
					}
					else if(meterMake.contains(GENUS)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_EVENT_KWH)*.001)).toString(), "0.000");//scalar/1000			
						}
					
					break;
				}
			
				return reading ; 
			} 
		
		
		
		String getRoundedLoadProfile(String reading,String type, String meterMake,String mekeVersion, String obisCode) {//XXX LOAD SURVEY
		 	
			switch (type) {
			case CURRENT:// CURRENT RELATED LOAD
				
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
					if(mekeVersion.equals(SECURE_M1XXG03) || mekeVersion.equals(SECURE_M1XXG02)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R)*1000)).toString(), "0.0000");
						}
					else if(mekeVersion.equals(SECURE_DLM2506)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R)*10)).toString(), "0.0000");
					}
					else{
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R)*1000)).toString(), "0.0000");
					}
				}
				else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R))).toString(), "0.0000");
				}
				else if(meterMake.contains(GENUS)){
					if(mekeVersion.contains("HC101.594")){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R)*10)).toString(), "0.0000");// DIVIDE BY 1000

					}else{
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_CURRENT_R))).toString(), "0.0000");// DIVIDE BY 1000
					}
				}
				
				break;
			case VOLTAGE:// VOLTAGE RELATED LOAD
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_VOLTAGE_R)*1000)).toString(), "0.0");
				}
				else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
 
				}
				else if(meterMake.contains(GENUS)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_VOLTAGE_R))).toString(), "0.0");
				}
				
				break;
			case ENERGY:// ENERGY RELATED LOAD
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){
					
					if(mekeVersion.equals(SECURE_M1XXG03) || mekeVersion.equals(SECURE_M1XXG02)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001*.1)).toString(), "0.000");//value*scalar/1000/10
						}
					else if(mekeVersion.equals(SECURE_DLM2506)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001*.01)).toString(), "0.000");//value*scalar/1000/100
					}
					else{
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001*.1)).toString(), "0.000");//value*scalar/1000/10
					}
					
				/*	DLM2506
					331 =3.3100
					value*scalar/1000/100

					M1XXG02 and M1XXG03
					469=46.900
					value*scalar/1000/10*/
					
				}
				else if(meterMake.contains(LNT) || meterMake.contains(LARSEN)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(0.00001)).toString(), "0.000");// DIVIDE BY 100
				}
				else if(meterMake.contains(GENUS)){
					
					if(mekeVersion.contains(GENUS_HC001)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001*10)).toString(), "0.000");// value*scalar/1000*10
					}else if(mekeVersion.contains(GENUS_HC101)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001)).toString(), "0.000");//value*scalar/1000
					}else{
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_KWH)*.001)).toString(), "0.000");// value*scalar/1000
					}
					
					/*
					if(mekeVersion.contains(GENUS_HC101)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.01)).toString(), "0.000");// DIVIDE BY 100
					}
					else if(mekeVersion.contains(GENUS_LC002)){
						return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(.001)).toString(), "0.000");// DIVIDE BY 1000

					}
				*/}
				
				break;
			}
				return  reading; 
		} 
		
		
		String getRoundedBillHistory(String reading,String type, String meterMake,String mekeVersion) {//XXX BILL HISTORY
		 	
			switch (type) {
				
			case PF:// POWER FACTOR RELATED LOAD
				
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){  
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_PF))).toString(), "0.000");
				}
				else if(meterMake.contains(GENUS)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_PF))).toString(), "0.000");
				}
				
				break;
			case ENERGY:// ENERGY RELATED LOAD
				
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_BILL_KWH)*.001)).toString(), "0.000");
				}
				else if(meterMake.contains(GENUS)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_BILL_KWH)*.001)).toString(), "0.000");
				}
				
				break;
			case KVA:// ENERGY RELATED LOAD
				
				if(meterMake.contains(SECURE) || meterMake.trim().equals(EHL)){ 
					System.out.println("============================== "+reading);
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_BILL_MAX_DEMAND_KVA)*.001)).toString(), "0.000");//scalar/1000
				}
				else if(meterMake.contains(GENUS)){
					return getRoundedValue(new BigDecimal(reading).multiply(new BigDecimal(getScalar(OBIS_BILL_MAX_DEMAND_KVA)*.001)).toString(), "0.000");//scalar/1000
				}
				
				break;
			}
				return reading;
		} 
	
	

	String getManufactureCode(String manufacturer) {
		if (manufacturer.contains(SECURE) || manufacturer.trim().equalsIgnoreCase(EHL)) {
			return "1";
		} else if (manufacturer.contains(LNT) || manufacturer.contains(LARSEN)) {
			return "3";
		} else if (manufacturer.contains(GENUS)) {
			return "4";
		} else if (manufacturer.contains(HPL)) {
			return "6";
		} else {
			return "0";
		}

		// 1 SECURE //3 LNT //4 Genus //else HPL
	}

	ArrayList<String> getDays(JSONArray loadSurvey, String[] columns, String OBIS_DATETIME) {
		ArrayList<String> days = new ArrayList<String>();
		try {
			for (int i = 0; i < loadSurvey.length(); i++) { // PARSING LOAD SURVEY
				JSONObject loadObj = loadSurvey.getJSONObject(i);
				for (int k = 0; k < columns.length; k++) { // PARSING COLUMNS
					if (columns[k].trim().equals(OBIS_DATETIME)) {
						String dateTime = loadObj.getString("V" + k);
						days.add(dateTime.split(" ")[0].trim());
					}
				}
			}
			days = new ArrayList<String>(new LinkedHashSet<String>(days));
			
			Collections.sort(days, new Comparator<String>() { // SORTING DATE
		        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
		        @Override
		        public int compare(String o1, String o2) {
		            try {
		                return f.parse(o1).compareTo(f.parse(o2));
		            } catch (ParseException e) {
		                throw new IllegalArgumentException(e);
		            }
		        }
		    });
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	String getMinute(String string) {
		try {
			int minutes = (Integer.parseInt(string.trim()) % 3600) / 60;
			return minutes + ""; // IF IT IS PROPER INTEGER, RETURN MINUTE
		} catch (Exception e) {
			return string; // IF IT IS NOT PROPER INTEGER, RETURN SAME STRING
		}
	}


	public boolean isDate(String value) {
    	 try {
			new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH).parse(value);
			return true;
		} catch (Exception e) {
			return false;//IF THE VALUE IS NOT A DATE, COMES IN CATCH
		}
	}
	
	public String[] getCdfCodeAndStatus(String code, String kEY_COLUMN) {
		String []codeAndStatus= new String[2];
		String eventCode=code;
		String eventStatus="0";
		
		try {
			
			switch (kEY_COLUMN) {
			
			case KEY_EVENT_VOLTAGE: // 1 to 12
				switch (code) {
				case "1":// R-Phase — Voltage Missing — Occurrence 
					eventCode="1";
					eventStatus="0";
					break;
				case "2":// R-Phase — Voltage Missing — Restoration 
					eventCode="1";
					eventStatus="1";
					break;
				case "3": // Y-Phase — Voltage Missing — Occurrence 
					eventCode="2";
					eventStatus="0";
					break;
				case "4": // Y-Phase — Voltage Missing — Restoration 
					eventCode="2";
					eventStatus="1";
					break;
				case "5": // B-Phase — Voltage Missing — Occurrence
					eventCode="3";
					eventStatus="0";
					break;
				case "6": // B-Phase — Voltage Missing — Restoration 
					eventCode="3";
					eventStatus="1";
					break;
				case "7": // Over voltage in any phase — Occurrence    
					eventCode="33";
					eventStatus="0";
					break;
				case "8": // Over voltage in any phase — Restoration 
					eventCode="33";
					eventStatus="1";
					break;
				case "9": // Low Voltage in any Phase — Occurrence 
					eventCode="58";
					eventStatus="0";
					break;
				case "10": // Low voltage in any phase — Restoration 
					eventCode="58";
					eventStatus="1";
					break;
				case "11": // Voltage unbalance — Occurrence 
					eventCode="14";
					eventStatus="0";
					break;
				case "12": // Voltage unbalance — Restoration 
					eventCode="14";
					eventStatus="1";
					break;
				}
				break;
			case KEY_EVENT_CURRENT: //51  to 68
				switch (code) {
				case "51"://R Phase — Current reverse — Occurrence 
					eventCode="4";
					eventStatus="0";
					break;
				case "52"://R Phase — Current reverse — Restoration 
					eventCode="4";
					eventStatus="1";
					break;
				case "53": //Y Phase — Current reverse — Occurrence 
					eventCode="5";
					eventStatus="0";
					break;
				case "54": //Y Phase — Current reverse — Restoration 
					eventCode="5";
					eventStatus="1";
					break;
				case "55": //B Phase — Current reverse — Occurrence
					eventCode="6";
					eventStatus="0";
					break;
				case "56": //B Phase — Current reverse — Restoration 
					eventCode="6";
					eventStatus="1";
					break;
				case "57": //  Phase — R CT open — Occurrence
					eventCode="24";
					eventStatus="0";
					break;
				case "58": //   Phase — R CT open — Restoration
					eventCode="24";
					eventStatus="1";
					break;
				case "59": //  Phase — Y CT open — Occurrence
					eventCode="25";
					eventStatus="0";
					break;
				case "60": //  Phase — Y CT open — Restoration
					eventCode="25";
					eventStatus="1";
					break;
				case "61": //  Phase — B CT open — Occurrence
					eventCode="26";
					eventStatus="0";
					break; 
				case "62": //  Phase — B CT open — Restoration
					eventCode="26";
					eventStatus="1";
					break;
				case "63": //Current unbalance — Occurrence  
					eventCode="7";
					eventStatus="0";
					break;
				case "64": //Current unbalance — Restoration
					eventCode="7";
					eventStatus="1";
					break;
				case "65": //Current bypass — Occurrence
					//TODO
					break;
				case "66": //Current bypass — Restoration 
					//TODO
					break;
				case "67": //Over current in any phase — Occurrence  
					eventCode="11";
					eventStatus="0";
					break;
				case "68": //Over current in any phase — Restoration 
					eventCode="11";
					eventStatus="1";
					break;
				}
				break;
				
			case KEY_EVENT_POWER: // 101 and 102
				
				eventCode="13";
				if(code.equals("101")){
					eventStatus="0"; // POWER OFF
				}else{
					eventStatus="1"; // POWER ON
				}
				
				break;
				
			case KEY_EVENT_TRANSACTION: // 151 to 160 TAMPER
				switch (code) {
				case "151"://Real Time Clock – Date and Time  151 is 3 in original XML
					//TODO
					break;
				case "152"://Demand Integration Period 
					//TODO
					break;
				case "153": //Profile Capture Period 
					//TODO
					break;
				case "154": //Single-action Schedule for Billing Dates 
					//TODO
					break;
				case "155": //Activity Calendar for Time Zones 
					//TODO
					break;
				case "157": //New firmware activated 
					//TODO
					break;
				case "158": //Load limit (kW) set
					//TODO
					break;
				case "159": //Enabled - load limit function 
					//TODO
					break;
				case "160": //Disabled – load limit function 
					//TODO
					break;
				}
				break;
			case KEY_EVENT_OTHER: // 201 to 206
				
				switch (code) {
				case "201"://Abnormal External Magnetic Influence – Occurrence 
					eventCode="27";
					eventStatus="0"; 
					break;
				case "202"://Abnormal External Magnetic Influence – Restoration 
					eventCode="27";
					eventStatus="1"; 
					break;
				case "203": //Neutral disturbance – Occurrence 
					eventCode="28";
					eventStatus="0"; 
					break;
				case "204": //Neutral disturbance – Restoration 
					eventCode="28";
					eventStatus="1"; 
					break;
				case "205": //Low PF — Occurrence   
					eventCode="12";
					eventStatus="0"; 
					break;
				case "206": //Low PF — Restoration 
					eventCode="12";
					eventStatus="1"; 
					break;
				}
				
				break;
			case KEY_EVENT_NON_ROLLOVER: // only 251  
				eventCode="60"; //METER COVER OPEN  
				break;
			case KEY_EVENT_CONTROL: // 301  and 302
				if(code.equals("301")){ //Load switch status - Disconnected
					//TODO
				}else{                  //Load switch status - Connected
					//TODO
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
				codeAndStatus[0]=eventCode; // FIRST POSITION FOR EVENT CODE,DEFAULT ORIGINAL CODE
				codeAndStatus[1]=eventStatus; // SECOND POSITION FOR STATUS, DEFAULT 0
				
		return  codeAndStatus;
	}
	
	 JSONArray SCALARS= new JSONArray();
	public void setScalar(JSONObject meter) {
		try {
//    		System.out.println("SETTING SCALER UNITS==============================");
			String COLUMN_STRUCTURE= meter.getString("COLUMN_STRUCTURE").trim();
			JSONArray COLUMN_ARRAY= new JSONArray(COLUMN_STRUCTURE);
			String scalar = COLUMN_ARRAY.getJSONObject(0).getString("SCALAR"); // GETTING SCALARS
			SCALARS=new JSONArray(scalar);
			System.out.println(SCALARS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public double getScalar(String obisCode) {
		try {
			for (int i = 0; i < SCALARS.length(); i++) {
				JSONObject obj = SCALARS.getJSONObject(i);
			
				String obis;
			
				if(obj.has("subObis")){
					obis=obj.getString("subObis").trim(); // GIVE PRIORITY TO PROFILE GENERIC OBIS CODE
				}else if(obj.has("obis")){
					obis=obj.getString("obis").trim(); // PROFILE GENERIC OBIS CODE NOT EXIST, TAKING INSTANTANEOUS OBIS
				}else{
					System.err.println(obisCode +" SCALAR NOT FOUND");
					return 1; // IF OBIS CODE NOT FOUND, RETURN 1 AS SCALAR 
				}
				
				if (obis.equals(obisCode)) {
					
					String scalar=obj.getString("scalar").trim();

			/*		 switch (obisCode) {
					case OBIS_EVENT_KWH:
						return Double.parseDouble(scalar)/1000;
					case OBIS_BILL_MAX_DEMAND_KVA:
						return Double.parseDouble(scalar)/1000;
					}
					*/
					return Double.parseDouble(scalar);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	String getRoundedValue(String value, String format) {
		try {
			DecimalFormat df = new DecimalFormat(format);
			if (value.isEmpty()) {
				return value;
			} else {
				return df.format(Double.parseDouble(value));
			}
		} catch (Exception e) {
			return value;
		}

	}
	/*
	 * scalar =scalar/1000
	 * if(scalar==1){
		scalr=.001;
	}if(scalar==10){
		scalr=.01;
	}if(scalar==1000){
		scalr=1;
	} if(scalar==0.1){
		scalr=.0001;
	} */
	
}
