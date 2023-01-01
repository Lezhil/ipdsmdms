package com.bcits.utility.amr;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class AmrMapLoadProfile {

    String KEY_LOADS ="COLUMN_1.0.99.1.0.255";
	String KEY_EVENT ="COLUMN_0.0.99.98.2.255";
	String KEY_BILLS ="COLUMN_1.0.98.1.0.255";
	String KEY_MAN_SPECIFIC="COLUMN_1.0.98.128.13.255"; // FOR METER CLASS 
	
	public  void getColumnDetailsLoad(JSONObject json){
		try {
			
			final String OBIS_DATETIME= "0.0.1.0.0.255";
			final String OBIS_CURRENTR ="1.0.31.27.0.255";
			final String OBIS_CURRENTY= "1.0.51.27.0.255";
			final String OBIS_CURRENTB ="1.0.71.27.0.255"; 
			final String OBIS_VOLTAGER ="1.0.32.27.0.255";
			final String OBIS_VOLTAGEY= "1.0.52.27.0.255";
			final String OBIS_VOLTAGEB ="1.0.72.27.0.255";
			final String OBIS_KWH =	    "1.0.1.29.0.255";
			final String OBIS_KVARHLAG ="1.0.5.29.0.255";
			final String OBIS_KVARHLEAD="1.0.8.29.0. 255";
			final String OBIS_KVAH =	"1.0.9.29.0.255";
			
		//	{"V2":"0","V3":"0","V4":"0","V5":"0","V0":"05/02/16 12:00:00","V1":"0"}
			String COLUMN_STRUCTURE= json.getString("COLUMN_STRUCTURE");
			JSONArray COLUMN_ARRAY= new JSONArray(COLUMN_STRUCTURE);
			String LOAD_STRUCTURE = COLUMN_ARRAY.getJSONObject(0).getString(KEY_LOADS);
			
		//	System.out.println(COLUMN_STRUCTURE);
			System.out.println("===================>"+LOAD_STRUCTURE);
			String [] columns=LOAD_STRUCTURE.split("\\|");
			System.out.println("LENGTH OF COLUMN: "+columns.length);
			JSONArray loadSurvey=new JSONArray(json.getString("LOAD_SURVEY_DATA"));
			
			
			System.out.println(loadSurvey);
			
			
			for (int i = 0; i < loadSurvey.length(); i++) {
				JSONObject loadObj= loadSurvey.getJSONObject(i);
				//System.out.println(columns.toString());
				System.out.println(loadObj);
				
				String dateTime="-",currentR="-",currentY="-",currentB="-",voltageR="-",voltageY="-",voltageB="-",kWh="-",kvarhLag="-",kvahLead="-",kvah="-";
				 
				System.out.println("LENGTH OF COLUMN: "+columns.length);
				for (int k = 0; k < columns.length; k++) {
					System.out.println(k+ "  k value");
					switch (columns[k].trim()) {
					case OBIS_DATETIME:
						dateTime=loadObj.getString("V"+k);
						break;
					case OBIS_CURRENTR:
						currentR=loadObj.getString("V"+k);
						break;
					case OBIS_CURRENTY:
						currentY=loadObj.getString("V"+k);
						break;
					case OBIS_CURRENTB:
						currentB=loadObj.getString("V"+k);
						break;
					case OBIS_VOLTAGER:
						voltageR=loadObj.getString("V"+k);
						break;
					case OBIS_VOLTAGEY:
						voltageY=loadObj.getString("V"+k);
						break;
					case OBIS_VOLTAGEB:
						voltageB=loadObj.getString("V"+k);
						break;
					case OBIS_KWH:
						kWh=loadObj.getString("V"+k);
						break;
					case OBIS_KVARHLAG:
						kvarhLag=loadObj.getString("V"+k);
						break;
					case OBIS_KVARHLEAD:
						kvahLead=loadObj.getString("V"+k);
						break;
					case OBIS_KVAH:
						kvah=loadObj.getString("V"+k);
						break;
					}
					
				}
				
				System.out.println("dateTime  :  "+dateTime);
				System.out.println("currentR  :  "+currentR);
				System.out.println("currentY  :  "+currentY);
				System.out.println("currentB  :  "+currentB);
				System.out.println("voltageR  :  "+voltageR);
				System.out.println("voltageY  :  "+voltageY);
				System.out.println("voltageB  :  "+voltageB);
				System.out.println("kWh 	  :  "+kWh);
				System.out.println("kvarhLag  :  "+kvarhLag);
				System.out.println("kvahLead  :  "+kvahLead);
				System.out.println("kvah 	  :  "+kvah);
				System.out.println("\n- - - - - - - - - - - - - - -");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void getColumnDetailsEvent(JSONObject json){
		try {
		//	{"V2":"0","V3":"0","V4":"0","V5":"0","V0":"05/02/16 12:00:00","V1":"0"}
			String COLUMN_STRUCTURE= json.getString("COLUMN_STRUCTURE");
			JSONArray COLUMN_ARRAY= new JSONArray(COLUMN_STRUCTURE);
			String STRUCTURE = COLUMN_ARRAY.getJSONObject(0).getString(KEY_EVENT);
			
		//	System.out.println(COLUMN_STRUCTURE);
			System.out.println("===================>"+STRUCTURE);
			String [] columns=STRUCTURE.split("\\|");
			System.out.println("LENGTH OF COLUMN: "+columns.length);
			JSONArray eventHistory=new JSONArray(json.getString("EVENT_HISTORY"));
			
			
			System.out.println(eventHistory);
			
			
			for (int i = 0; i < eventHistory.length(); i++) {
				JSONObject loadObj= eventHistory.getJSONObject(i);
				//System.out.println(columns.toString());
				System.out.println(loadObj);
				
				String dateTime="-",eventCode="-";
				 
				   
				final String OBIS_DATETIME=    "0.0.1.0.0.255";
				final String OBIS_EVENT_CODE1= "0.0.96.11.2.255";  // FROM GENUS 
				final String OBIS_EVENT_CODE2= "0.0.96.11.0.255";  //COMMON FROM DOCUMENT
				System.out.println("LENGTH OF COLUMN: "+columns.length);
				for (int k = 0; k < columns.length; k++) {
					System.out.println(k+ "  k value");
					switch (columns[k].trim()) {
					case OBIS_DATETIME:
						dateTime=loadObj.getString("V"+k);
						break;
					case OBIS_EVENT_CODE1:
						eventCode=loadObj.getString("V"+k);
						break;
					case OBIS_EVENT_CODE2:
						eventCode=loadObj.getString("V"+k);
						break;
					}
					
				}
				
				System.out.println("dateTime  :  "+dateTime);
				System.out.println("eventCode :  "+eventCode);
				System.out.println("\n- - - - - - - - - - - - - - -");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
