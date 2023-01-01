package com.bcits.controller;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class test 
{
	  public static void main(String str[]) throws JSONException
	    {
		/*
		 * String jsonString =
		 * "{\"stat\": { \"sdr\": \"aa:bb:cc:dd:ee:ff\", \"rcv\": \"aa:bb:cc:dd:ee:ff\", \"time\": \"UTC in millis\", \"type\": 1, \"subt\": 1, \"argv\": [{\"type\": 1, \"val\":\"stackoverflow\"}]}}"
		 * ; JSONObject jsonObject = new JSONObject(jsonString); JSONObject newJSON =
		 * jsonObject.getJSONObject("stat"); System.out.println(newJSON.toString());
		 * jsonObject = new JSONObject(newJSON.toString());
		 * System.out.println(jsonObject.getString("rcv"));
		 * System.out.println(jsonObject.getJSONArray("argv"));
		 */
		  
		 String value="2019-09-20 12:02:17";
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Timestamp t1= new Timestamp(format.parse(value).getTime());
					Timestamp t2= new Timestamp(format1.parse(value).getTime());
					System.out.println("t1-->"+t1);
					System.out.println("t2-->"+t2);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
	    }
	


	

		 
		
		
	
	       
		 
	   
	


