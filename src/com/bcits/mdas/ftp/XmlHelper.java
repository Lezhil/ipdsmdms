package com.bcits.mdas.ftp;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;

public class XmlHelper {
    
	
	  
	   //INSTANTANEOUS TABLE BY RASHMI
	   final String PARAM_KWHI ="P7_1_5_1";
	   final String PARAM_KWHE ="P7_1_6_1";
	   final String PARAM_KVARHI ="P7_2_7";
	   final String PARAM_KVARHE ="P7_2_8";
	   final String PARAM_KVAHI ="P7_2_7";
	   final String PARAM_KVAHE ="P7_3_6";
	   final String PARAM_KWI ="P7_4_5";
	   final String PARAM_KWE ="P7_4_6";
	   final String PARAM_KVARI ="P7_5_7";
	   final String PARAM_KVARE ="P7_5_20";
	   final String PARAM_KVAI ="P7_6";
	   final String PARAM_KVAE ="P7_6_20";
	   final String PARAM_VR ="P5_1";
	   final String PARAM_VY ="P5_2";
	   final String PARAM_VB ="P5_3";
	   final String PARAM_VRYB ="P1_1_7";
	   final String PARAM_IR ="P2_3_1";
	   final String PARAM_IY ="P2_3_2";
	   final String PARAM_IB ="P2_3_3";
	   final String PARAM_IRYB ="P2_3_5";
	   final String PARAM_PF ="P4-4-1-0-0";
	   
	   
	   //newlly added for Instantaneous
	   final String D2_PARAM_v_r="P1-2-1-1-0";
	   final String D2_PARAM_v_y="P1-2-2-1-0";
	   final String D2_PARAM_v_b="P1-2-3-1-0";
	   final String D2_PARAM_i_r="P2-1-1-1-0";
	   final String D2_PARAM_i_y="P2-1-2-1-0";
	   final String D2_PARAM_i_b="P2-1-3-1-0";
	   final String D2_PARAM_pf_r="P4-1-1-0-0";
	   final String D2_PARAM_pf_y="P4-2-1-0-0";
	   final String D2_PARAM_pf_b="P4-3-1-0-0";
	   final String D2_PARAM_pf_threephase="P4-4-1-0-0";
	   final String D2_PARAM_frequency="P9-1-0-0-0";
	   final String D2_PARAM_kwh="P7-1-5-2-0";
	   final String D2_PARAM_kvah="P7-3-5-2-0";
	   final String D2_PARAM_kvah_lag="P7-2-1-2-0";
	   final String D2_PARAM_kvah_lead="P7-2-4-2-0";
	   final String D2_PARAM_kvar="P3-3-4-1-0";
	   final String D2_PARAM_power_off_count="P11-1-2-0-0";
	   final String D2_PARAM_power_off_duration="P11-4-0-0-0";
	   final String D2_PARAM_Md_Kw="P7-4-5-2-4";
	   final String D2_PARAM_md_kva="P7-6-5-2-4";
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	 //FOR LOAD SURVEY TABLE BY RASHMI
	   final String LOAD_PARAM_KWHI ="P7_1_5_1";
	   final String LOAD_PARAM_KWHE ="P7_1_6_1";
	   final String LOAD_PARAM_KVARHI ="P7_2_7";
	   final String LOAD_PARAM_KVARHE ="P7_2_8";
	   final String LOAD_PARAM_KVAHI ="P7_2_7";
	   final String LOAD_PARAM_KVAHE ="P7_3_6";
	   final String LOAD_PARAM_VR ="P1-2-1-4-0";
	   final String LOAD_PARAM_VY ="P1-2-2-4-0";
	   final String LOAD_PARAM_VB ="P1-2-3-4-0";
	   final String LOAD_PARAM_VRYB ="P1_1_7";
	   final String LOAD_PARAM_IR ="P2-1-1-4-0";
	   final String LOAD_PARAM_IY ="P2-1-2-4-0";
	   final String LOAD_PARAM_IB ="P2-1-3-4-0";
	   final String LOAD_PARAM_IRYB ="P2_1_5";
	   
	   
	   //Event Table
	   final String EVENT_PARAM_KWHI ="P7_1_5_1";
	   final String EVENT_PARAM_KWHE ="P7_1_6_1";
	   final String EVENT_PARAM_KVARHI ="P7_2_7";
	   final String EVENT_PARAM_KVARHE ="P7_2_8";
	   final String EVENT_PARAM_KVAHI ="P7_2_7";
	   final String EVENT_PARAM_KVAHE ="P7_3_6";
	   final String EVENT_PARAM_KWI ="P7_4_5";
	   final String EVENT_PARAM_KWE ="P7_4_6";
	   final String EVENT_PARAM_KVARI ="P7_5_7";
	   final String EVENT_PARAM_KVARE ="P7_5_20";
	   final String EVENT_PARAM_KVAI ="P7_6";
	   final String EVENT_PARAM_KVAE ="P7_6_20";
	   final String EVENT_PARAM_VR ="P1_1_1";
	   final String EVENT_PARAM_VY ="P1_1_2";
	   final String EVENT_PARAM_VB ="P1_1_3";
	   final String EVENT_PARAM_VRYB ="P1_1_7";
	   final String EVENT_PARAM_IR ="P2_1_1";
	   final String EVENT_PARAM_IY ="P2_1_2";
	   final String EVENT_PARAM_IB ="P2_1_3";
	   final String EVENT_PARAM_IRYB ="P2_1_5";
	   final String EVENT_PARAM_PF ="P4_1";
	   
	   
	   
	   //Billing History table
	   
	           //1 BillingEnergies
	   final String BILLING_KWHI ="P7_1_5_1";
	   final String BILLING_KWHE ="P7_1_6_1";
	   final String BILLING_KVARHI ="P7_2_7";
	   final String BILLING_KVARHE ="P7_2_8";
	   final String BILLING_KVAHI ="P7_2_7";
	   final String BILLING_KVAHE ="P7_3_6";
	   
	  
	    
	
	   
	   //3 BillingDemand
	   
	   final String BILLING_KWI ="P7_4_5";
	   final String BILLING_KWI_OCCDATE ="P7_4_5";
	   final String BILLING_KWE ="P7_4_6";
	   final String BILLING_KWE_OCCDATE ="P7_4_6";
	   final String BILLING_KVARI ="P7_5_7";
	   final String BILLING_KVARI_OCCDATE ="P7_5_7";
	   final String BILLING_KVARE ="P7_5_8";
	   final String BILLING_KVARE_OCCDATE ="P7_5_8";
	   final String BILLING_KVAI ="P7_6";
	   final String BILLING_KVAI_OCCDATE ="P7_6";
	   final String BILLING_KVAE ="P7_6_20";
	   final String BILLING_KVAE_OCCDATE ="P7_6_20";
	   
	 ////////////B3///////////////
	   final String BILLING_KWh ="P7-1-5-2-0";
	   final String BILLING_kvarh_lag ="P7-2-1-2-0";
	   final String BILLING_kvarh_lead ="P7-2-4-2-0";
	   
	   ///////////////////B4///////////
	   final String BILLING_kvah_tz1 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz2 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz3 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz4 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz5 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz6 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz7 ="P7-3-5-2-0";
	   final String BILLING_kvah_tz8 ="P7-3-5-2-0";
	   
	   //////////////////B4////////////////
	   final String BILLING_kwh_tz1 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz2 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz3 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz4 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz5 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz6 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz7 ="P7-1-5-2-0";
	   final String BILLING_kwh_tz8 ="P7-1-5-2-0";
	
	   //////////////////B9////////////////
	   final String BILLING_Sys_pf_billing ="P4-4-4-1-0";
	   
    //////////////////B6////////////////
	   final String BILLING_Kva_tz1 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz2 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz3 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz4 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz5 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz6 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz7 ="P7-3-5-2-0";
	   final String BILLING_Kva_tz8 ="P7-3-5-2-0";
	   
	   
	   ////////////////////////B6///////////
	   final String BILLING_kw_tz1 ="P7-1-5-2-0";
	   final String BILLING_kw_tz2 ="P7-1-5-2-0";
	   final String BILLING_kw_tz3 ="P7-1-5-2-0";
	   final String BILLING_kw_tz4 ="P7-1-5-2-0";
	   final String BILLING_kw_tz5 ="P7-1-5-2-0";
	   final String BILLING_kw_tz6 ="P7-1-5-2-0";
	   final String BILLING_kw_tz7 ="P7-1-5-2-0";
	   final String BILLING_kw_tz8 ="P7-1-5-2-0";
	   
	   ////////////////B5////////////
	   final String BILLING_kva ="P7-6-5-2-0";
	   final String BILLING_demand_kw  ="P7-4-5-2-0";
	   
	   
	   
	   
	   
	   
	   
	   
	   
		
	  public ArrayList<String> getDays(List<AmrLoadEntity> amrLoadEntity) {
			ArrayList<String> days = new ArrayList<String>();
			
				for(AmrLoadEntity amrLoadEntity1:amrLoadEntity)
				{	
					
//REDUCE 30 MINUTES FOR MAKING 00.30 AS THE FIRST INTERVAL IN XML
					Calendar cal = Calendar.getInstance();
					cal.setTime(amrLoadEntity1.getMyKey().getReadTime());
					cal.add(Calendar.MINUTE, -30);//Reduce 30 minutes to the load survey date to make compatible with XML time
					Timestamp readTime = new Timestamp(cal.getTime().getTime());
					amrLoadEntity1.setMyKey(new KeyLoad(amrLoadEntity1.getMyKey().getMeterNumber(),readTime));
					
					String dateTime =String.valueOf( amrLoadEntity1.getMyKey().getReadTime());
				 /*System.out.println(dateTime+" DATE TIME");*/
							days.add(dateTime.split(" ")[0].trim());
							
				}
				days = new ArrayList<String>(new LinkedHashSet<String>(days));
				
				Collections.sort(days, new Comparator<String>() 
						{ // SORTING DATE 2017-08-23
			        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			        @Override
			        public int compare(String o1, String o2) {
			            try {
			                return f.parse(o1).compareTo(f.parse(o2));
			            } catch (ParseException e) {
			                throw new IllegalArgumentException(e);
			            }
			        }
			    });
	   return days;
	  
	
}
	   ArrayList<String> getDays1(List<AmrBillsEntity> amrBillsEntity) {
			ArrayList<String> days = new ArrayList<String>();
			
				for(AmrBillsEntity AmrBillsEntity1:amrBillsEntity)
				{	String dateTime =String.valueOf( AmrBillsEntity1.getMyKey().getReadTime());
				// System.out.println(dateTime+" DATE TIME");
							days.add(dateTime.split(" ")[0].trim());
							days = new ArrayList<String>(new LinkedHashSet<String>(days));
							
							Collections.sort(days, new Comparator<String>() 
									{ // SORTING DATE 2017-08-23
						        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
						        @Override
						        public int compare(String o1, String o2) {
						            try {
						                return f.parse(o1).compareTo(f.parse(o2));
						            } catch (ParseException e) {
						                throw new IllegalArgumentException(e);
						            }
						        }
						    });
				}

	   return days;
	  
	
}
	   
	   public String[] getCdfCodeAndStatus(String code) {
			String []codeAndStatus= new String[2];
			String eventCode=code;
			String eventStatus="0";
			/*System.out.println(code+"           code");*/
			try {
				
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
					default :
					           eventCode="100";
					           eventStatus="1"; 
					         break;			
				
				
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
					codeAndStatus[0]=eventCode; // FIRST POSITION FOR EVENT CODE,DEFAULT ORIGINAL CODE
					codeAndStatus[1]=eventStatus; // SECOND POSITION FOR STATUS, DEFAULT 0
					
			return  codeAndStatus;
		}





}


