/*
 Created By, REMITH
 */
package com.bcits.utility.amr;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlCdfCreator {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Element elementMain;
    String timeStamp;

    XmlHelper helper;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
    
    public  boolean createXML(JSONObject meter,String xmlFileName) {
        try {
        	helper = new XmlHelper();
        	String meterMake =meter.getString("MANUFACTURER").toUpperCase(Locale.ENGLISH);
        	String makeVersion = meter.getString("FIRMWARE_VERSION").toUpperCase(Locale.ENGLISH).trim();
        	String meterNumber = meter.getString("METER_NUMBER").toUpperCase(Locale.ENGLISH).trim();
        	String meterYear = meter.getString("YEAR_OF_MANUFACTURE").toUpperCase(Locale.ENGLISH).trim();
        	
//        	System.out.println("MANUFACTURER : "+meterMake);
//        	System.out.println("METER MAKE VERSION : "+makeVersion);
        	System.out.println("METER NUMBER : "+meterNumber);
        	 
        	helper.setScalar(meter); 
        	
        	timeStamp = dateFormat.format(Calendar.getInstance().getTime());
        	
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
//ROOT, UTILITYTYPE AND AUTHENTICATOR-------------------------------------------
            // ROOT CDF elements
            Element elCDF = doc.createElement("CDF");
            doc.appendChild(elCDF);
// UTILITYTYPE element----------------------------------------------------------
            elementMain = doc.createElement("UTILITYTYPE");
            elCDF.appendChild(elementMain);
// set attribute to elUTILITYTYPE
            Attr attr = doc.createAttribute("CODE");
            attr.setValue("1");
            elementMain.setAttributeNode(attr);
// AUTHENTICATOR element -------------------------------------------------------
            Element elAUTHENTICATOR = doc.createElement("AUTHENTICATOR");
            elAUTHENTICATOR.appendChild(doc.createTextNode(" "));
            elCDF.appendChild(elAUTHENTICATOR);

            
//D1 NAME PLATE ----------------------------------------------------------------
            D1(meter);
//D2 INSTANTANEOUS ----------------------------------------------------------------
            D2(meter);
//D3 BILL HISTORY ----------------------------------------------------------------
            D3(meter,meterMake,makeVersion);
//D4 LOAD SURVEY ----------------------------------------------------------------
            D4(meter,meterMake,makeVersion);
//D5 EVENT HISTORY ----------------------------------------------------------------
            D5(meter,meterMake,makeVersion);    

            // Write the content into xml file---------------------------------------------
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
// FILE NAME--------------------------------------------------------------------
            //String readingTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
           
            StreamResult result = new StreamResult(new File(xmlFileName));
// CREATE XML FILE-------------------------------------------------------------- 
            transformer.transform(source, result);

//            System.out.println("File '"+xmlFileName+"' created successfully");

            return true;
        } catch (Exception e) {
           e.printStackTrace();
            return false;
        }
    }

    private void D1(JSONObject meter) { //XXX
        //D1 TAG  
        try {
            
            Element D1 = doc.createElement("D1");

            Element G1 = doc.createElement("G1");
            G1.appendChild(doc.createTextNode(meter.getString("METER_NUMBER")));            //METER NUMBER
            D1.appendChild(G1);

            Element G2 = doc.createElement("G2");
            G2.appendChild(doc.createTextNode(meter.getString("METER_DATE")));              //MTER DATE
            D1.appendChild(G2);

            Element G3 = doc.createElement("G3");
            G3.appendChild(doc.createTextNode(meter.getString("CREATED_TIME")));             //READING TIME AND DATE
            D1.appendChild(G3);

            Element G4 = doc.createElement("G4");
            G4.appendChild(doc.createTextNode(timeStamp));                                   //DATE OF MRI DUMPING TO PC. (Here same time)
            D1.appendChild(G4);

            Element G7 = doc.createElement("G7");
            G7.appendChild(doc.createTextNode(meter.getString("INTERNAL_PT_RATIO")));        //PT RATIO 
            D1.appendChild(G7);

            Element G8 = doc.createElement("G8");
            G8.appendChild(doc.createTextNode(meter.getString("INTERNAL_CT_RATIO")));        //CT RATIO 
            D1.appendChild(G8);

           /* Element G13 = doc.createElement("G13");
            G13.appendChild(doc.createTextNode("???"));                             //METER CLASS
            D1.appendChild(G13);*/

           /* Element G14 = doc.createElement("G14");
            G14.appendChild(doc.createTextNode("???"));                             //METER RATING. (5-6, 5-20)
            D1.appendChild(G14);*/

            Element G15 = doc.createElement("G15");
            G15.appendChild(doc.createTextNode(meter.getString("METER_TYPE")));      //METER TYPE HT(3ph-3W), HT(3ph-4W),LT(3ph-3W), LT(3ph4W),WC(3ph-4W),WC(1ph-2W)
            D1.appendChild(G15);

           /* Element G16 = doc.createElement("G16");
            G16.appendChild(doc.createTextNode("???"));                             //METER SCALING (Primary/Secondary)
            D1.appendChild(G16);*/

            Element G17 = doc.createElement("G17");
            String firmware =meter.getString("FIRMWARE_VERSION");
            
            if(meter.getString("MANUFACTURER").toUpperCase().contains("GENUS")){
            	firmware=firmware.split("\\.")[0];
            }
            G17.appendChild(doc.createTextNode(firmware));        //Meter Program Name including version number
            D1.appendChild(G17);

            Element G19 = doc.createElement("G19");
            G19.appendChild(doc.createTextNode(meter.getString("CUMULATIVE_BILLING_COUNT"))); //Cumulative successful meter reading count 
            D1.appendChild(G19);

            Element G20 = doc.createElement("G20");
            G20.appendChild(doc.createTextNode(helper.getMinute(meter.getString("DEMAND_INTEGRATION_PERIOD"))));     //Meter Demand integration period(Possible values - 1 mt, 2mts, 5 mts,15mts, 30mts,60mts)
            D1.appendChild(G20);

            Element G22 = doc.createElement("G22");
            Attr attrG22 = doc.createAttribute("CODE");

            attrG22.setValue(helper.getManufactureCode(meter.getString("MANUFACTURER")));       //METER CODE AmrMethods.getManufactureCode(meter.getString("MANUFACTURER"))
            G22.setAttributeNode(attrG22);
            Attr attrG22Name = doc.createAttribute("NAME");
            attrG22Name.setValue(meter.getString("MANUFACTURER"));                    //MANUFACTURER NAME
            G22.setAttributeNode(attrG22Name);
            G22.appendChild(doc.createTextNode(" "));
            D1.appendChild(G22);

           /* Element G30 = doc.createElement("G30");
            G30.appendChild(doc.createTextNode("???"));                             //Version number of interoperability document to which the common format
            D1.appendChild(G30);*/

            Element G31 = doc.createElement("G31");
            G31.appendChild(doc.createTextNode(meter.getString("APP_VERSION")));    //Version number of API frome which the common format is generated (Here Bsmart App)
            D1.appendChild(G31);

            Element G32 = doc.createElement("G32");
            G32.appendChild(doc.createTextNode(meter.getString("CUMULATIVE_BILLING_COUNT"))); //Value of cumulative maximum demand reset count. 
            D1.appendChild(G32);

           /* Element G33 = doc.createElement("G33");
            G33.appendChild(doc.createTextNode("???"));                             // MIOS membership ID issued by MIOS forum and as received in reading result file generated by Read API
            D1.appendChild(G33);*/

            elementMain.appendChild(D1);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
//boolean showlog=true;
    private void D2(JSONObject meter) { //D2 INSTANTANEOUS PARAMETERS XXX
        try {
            Element D2 = doc.createElement("D2");
            
            String voltage1 = helper.getRoundedValue(meter.getString("VOLTAGE_1"),"0.00"); 
            String voltage2 = helper.getRoundedValue(meter.getString("VOLTAGE_2"),"0.00"); 
            String voltage3 = helper.getRoundedValue(meter.getString("VOLTAGE_3"),"0.00"); 
            
            String current1 = helper.getRoundedValue(meter.getString("LINE_CURRENT_1"),"0.000"); 
            String current2 = helper.getRoundedValue(meter.getString("LINE_CURRENT_2"),"0.000"); 
            String current3 = helper.getRoundedValue(meter.getString("LINE_CURRENT_3"),"0.000"); 
            
            String pf1 = helper.getRoundedValue(meter.getString("POWER_FACOR_1"),"0.00"); 
            String pf2 = helper.getRoundedValue(meter.getString("POWER_FACOR_2"),"0.00"); 
            String pf3 = helper.getRoundedValue(meter.getString("POWER_FACOR_3"),"0.00"); 
            
            String pfAvg =helper.getRoundedValue(meter.getString("PF_3_PHASE"),"0.00"); 
            String frequency = helper.getRoundedValue(meter.getString("FREQUENCY"),"0.00"); 
            String kwh = helper.getRoundedValue(meter.getString("KWH"),"0.000"); 
            String signedPower = helper.getRoundedValue(meter.getString("SIGNED_ACTIVE_POWER"),"0.000"); 
            
       /*     String phaseSequence = "FORWARD";
			try {
				double signedPow= Double.parseDouble(signedPower);
				if(signedPow<0){
					phaseSequence="REVERSE";
				}else{
					phaseSequence="FORWARD";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
            
            D2.appendChild(createD2Attr(helper.PARAM_VOLTAGE_R, voltage1, "V")); //R-Phase phase to neutral instantaneous voltage  //DONE
            D2.appendChild(createD2Attr(helper.PARAM_VOLTAGE_Y, voltage2, "V")); //Y-Phase phase to neutral instantaneous voltage  //DONE
            D2.appendChild(createD2Attr(helper.PARAM_VOLTAGE_B, voltage3, "V")); //B-Phase phase to neutral instantaneous voltage  //DONE
            D2.appendChild(createD2Attr(helper.PARAM_CURRENT_R, current1, "A"));  //R-Phase instantaneous Line Current //DONE
            D2.appendChild(createD2Attr(helper.PARAM_CURRENT_Y, current2, "A"));  //Y-Phase instantaneous Line Current //DONE
            D2.appendChild(createD2Attr(helper.PARAM_CURRENT_B, current3, "A"));  //B-Phase instantaneous Line Current //DONE
            D2.appendChild(createD2Attr(helper.PARAM_PF_R, pf1, ""));    //R-Phase instantaneous Power Factor //DONE
            D2.appendChild(createD2Attr(helper.PARAM_PF_Y, pf2, ""));    //Y-Phase instantaneous Power Factor //DONE
            D2.appendChild(createD2Attr(helper.PARAM_PF_B, pf3, ""));    //B-Phase instantaneous Power Factor //DONE
            D2.appendChild(createD2Attr("P4-4-1-0-0", pfAvg, ""));    //Average PF   //DONE
            D2.appendChild(createD2Attr("P3-2-4-1-0", signedPower, "Kw"));  //System active total instantaneous power (ACTIVE POWER VALUE) //DONE
            D2.appendChild(createD2Attr(helper.PARAM_KWH, kwh, "K"));     //KWH //DONE
//            D2.appendChild(createD2Attr("P3-3-4-1-0", meter.getString("CUMULATIVE_ENERGY_KVARH_LAG"), "kVAr"));   //System reactive instantaneous power  
            D2.appendChild(createD2Attr("P3-4-4-1-0", meter.getString("KVA"), "kVA"));    //System apparent instantaneous power //DONE
            D2.appendChild(createD2Attr("P9-1-0-0-0", frequency, "Htz"));    //Instantaneous frequency //DONE
//            D2.appendChild(createD2Attr("P8-1-0-0-0", phaseSequence, ""));    //Phase Sequence (Calculating using active power value) //DONE

          /*  
            	System.out.println(meter.getString("VOLTAGE_1"));
            	System.out.println(meter.getString("VOLTAGE_2"));
            	System.out.println(meter.getString("VOLTAGE_3"));
            	System.out.println(meter.getString("LINE_CURRENT_1"));
            	System.out.println(meter.getString("LINE_CURRENT_2"));
            	System.out.println(meter.getString("LINE_CURRENT_3"));
            	System.out.println(meter.getString("KWH"));
            	System.out.println(meter.getString("CUMULATIVE_ENERGY_KVARH_LAG"));
            	System.out.println(meter.getString("FREQUENCY"));
            	showlog=false;
           */
            elementMain.appendChild(D2);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private Element createD2Attr(String code, String value, String unit) {
        Element G22 = doc.createElement("INSTPARAM");

        try {
            Attr attrG22 = doc.createAttribute("CODE");
            attrG22.setValue(code);
            G22.setAttributeNode(attrG22);

            Attr attrG2Val = doc.createAttribute("VALUE");
            attrG2Val.setValue(value);
            G22.setAttributeNode(attrG2Val);

            Attr attrG22Name = doc.createAttribute("UNIT");
            attrG22Name.setValue(unit);
            G22.setAttributeNode(attrG22Name);

            G22.appendChild(doc.createTextNode(" "));
        } catch (Exception e) {
            e.printStackTrace();

        }

        return G22;
    }

  /*  private void D6() { // BLANK
        Element D6 = doc.createElement("D6"); 
        D6.appendChild(doc.createTextNode(" "));
        elUTILITYTYPE.appendChild(D6);
    }

    private void D7() { // BLANK
        Element D7 = doc.createElement("D7"); 
        D7.appendChild(doc.createTextNode(" "));
        elUTILITYTYPE.appendChild(D7);
    }*/

    private boolean  D3(JSONObject meter,String meterMake, String makeVersion) { // D3 TAG - BILL HISTORY XXX
     try {	
    	 JSONArray billHistory=new JSONArray(meter.getString("BILLING_HISTORY"));
		  
		  if(billHistory.length()<=0)
		  { 
			  System.err.println("NO BILL HISTORY FOUND ");
			  return false;
		  }
		
			final String KEY_BILLS = "COLUMN_1.0.98.1.0.255";
		
			
			String COLUMN_STRUCTURE= meter.getString("COLUMN_STRUCTURE").trim();
			JSONArray COLUMN_ARRAY= new JSONArray(COLUMN_STRUCTURE);
			String STRUCTURE = COLUMN_ARRAY.getJSONObject(0).getString(KEY_BILLS); // GETTING COLUMN STRUCTURE OF BILLS
			
			String [] columns=STRUCTURE.split("\\|");
			ArrayList<ModelBills> billArray= new ArrayList<ModelBills>();

			for (int i = 0; i < billHistory.length(); i++) {
				JSONObject loadObj= billHistory.getJSONObject(i);
	
				String dateTime="",kWh="", kvah="",kva="",kvaDate="",pf="";
				 
				for (int k = 0; k < columns.length; k++) { 
					switch (columns[k].trim()) {
					case XmlHelper.OBIS_BILL_DATE:
						dateTime=loadObj.getString("V"+k);
						break;
					case XmlHelper.OBIS_BILL_KWH:
						kWh=loadObj.getString("V"+k);
						kWh=helper.getRoundedBillHistory(kWh, helper.ENERGY, meterMake,makeVersion);
						break;
					 case XmlHelper.OBIS_BILL_KVAH:
						 kvah=loadObj.getString("V"+k);
						 kvah=helper.getRoundedBillHistory(kvah, helper.ENERGY, meterMake,makeVersion);
							break;
					 case XmlHelper.OBIS_BILL_MAX_DEMAND_KVA:
						String val =loadObj.getString("V"+k);
						 if(helper.isDate(val)){
							 kvaDate=val;
						 }else{
							 kva=helper.getRoundedBillHistory(val, helper.KVA, meterMake,makeVersion);;
						 }
							break;
					 case XmlHelper.OBIS_BILL_PF: 
						 pf=loadObj.getString("V"+k);
						 pf=helper.getRoundedBillHistory(pf, helper.PF, meterMake,makeVersion);
							break;
					 
					}
				  
				}
				
//				System.out.println("BILL dateTime  :  "+AmrMethods.getOctetStringDates(dateTime));
//				System.out.println("BILL kWh       :  "+kWh);
//				System.out.println("BILL kVAh        :  "+kvah);
//				System.out.println("\n- - - - - - - - - - - - - - -");
			 
				ModelBills bill = new ModelBills();
				bill.setDate(dateFormat.parse(AmrMethods.getOctetStringDates(dateTime)));
				bill.setkWh(kWh);
				bill.setKvah(kvah);
				bill.setPf(pf);
				bill.setKva(kva);
				bill.setKvaDate(kvaDate);
				
				billArray.add(bill);
				
			}
		
			Collections.sort(billArray);     // ORDRE BY DATE
			Collections.reverse(billArray);  // REVERSE ARRAY TO GET LAST DATE FIRST
			
			  Element D3 = doc.createElement("D3");  
			  
			 for (int i = 0; i < billArray.size(); i++) {
				 ModelBills bill=billArray.get(i);
				 
				   String num = (i >= 10) ? String.valueOf(i) : "0" + String.valueOf(i);//ROUNDING TO 2 Digit Eg. B3-03
	                String d3SubName = "D3-" + num;
	                D3.appendChild(D3Sub(bill, d3SubName));
			 }
		  
       
            elementMain.appendChild(D3);
            return true;
        } catch (Exception e) {
          e.printStackTrace();

        }
        return false;
    }

    private Element D3Sub(ModelBills bill, String elementName) {
        Element el = doc.createElement(elementName);
        try {
        	
            Attr attrG22 = doc.createAttribute("DATETIME");
            attrG22.setValue(dateFormat.format(bill.getDate()));
            el.setAttributeNode(attrG22);

            el.appendChild(D3B3(helper.PARAM_KWH, bill.getkWh(), "K","B3")); //kwhValue  B3 
            el.appendChild(D3B3(helper.PARAM_KVAH, bill.getKvah(), "K","B3")); //kvahValue B3 
            el.appendChild(D3B5(helper.PARAM_KVA, bill.getKva(), bill.getKvaDate(),"K", "B5"));
            el.appendChild(D3B3(helper.PARAM_PF,bill.getPf(), null,"B9")); //PF B9 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return el;
    }
 

    private Element D3B3(String paramCode, String value, String unit,String tag) {
        Element G22 = doc.createElement(tag);
        try {
            Attr attrG22 = doc.createAttribute("PARAMCODE");
            attrG22.setValue(paramCode);
            G22.setAttributeNode(attrG22);

            Attr attrG2Val = doc.createAttribute("VALUE");
            attrG2Val.setValue(value);
            G22.setAttributeNode(attrG2Val);
            if(unit!=null){//IF UNIT IS NULL, REMOVE THAT TAG. FOR Example PF doesn't have UNIT
            Attr attrG22Name = doc.createAttribute("UNIT");
            attrG22Name.setValue(unit);
            G22.setAttributeNode(attrG22Name);
            }
            G22.appendChild(doc.createTextNode(" "));
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return G22;
    }
    private Element D3B5(String paramCode, String value,String ocDate, String unit,String tag) {
    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="K"></B5> KVA MD
        Element G22 = doc.createElement(tag);
        try {
            Attr attrG22 = doc.createAttribute("PARAMCODE");
            attrG22.setValue(paramCode);
            G22.setAttributeNode(attrG22);

            Attr attrG2Val = doc.createAttribute("VALUE");
            attrG2Val.setValue(value);
            G22.setAttributeNode(attrG2Val);
            
            Attr occDate = doc.createAttribute("OCCDATE");
            occDate.setValue(ocDate);
            G22.setAttributeNode(occDate);
            
            Attr attrG22Name = doc.createAttribute("UNIT");
            attrG22Name.setValue(unit);
            G22.setAttributeNode(attrG22Name);
            
            G22.appendChild(doc.createTextNode(" "));
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return G22;
    }

	private boolean D4(JSONObject meter, String meterMake, String makeVersion) {// D4 LOAD SURVEY DATA  XXX
		try {
			JSONArray loadSurvey = new JSONArray(meter.getString("LOAD_SURVEY_DATA"));
			if (loadSurvey.length() <= 0) {
				System.err.println("NO LOAD SURVEY DATA FOUND");
				return false;
			}

//			System.out.println(loadSurvey.length() + "      TOTAL COUNT OF LOAD SURVEY");
			
			final String KEY_LOADS = "COLUMN_1.0.99.1.0.255";

			String COLUMN_STRUCTURE = meter.getString("COLUMN_STRUCTURE");
			JSONArray COLUMN_ARRAY = new JSONArray(COLUMN_STRUCTURE);
			String LOAD_STRUCTURE = COLUMN_ARRAY.getJSONObject(0).getString(KEY_LOADS);
			String[] columns = LOAD_STRUCTURE.split("\\|");
			ArrayList<String> days = helper.getDays(loadSurvey, columns, XmlHelper.OBIS_LOAD_DATETIME);
			
			Element D4 = doc.createElement("D4");
			Attr attr = doc.createAttribute("INTERVALPERIOD");
			attr.setValue("30");
			D4.setAttributeNode(attr);

		
			ArrayList<ModelLoadSurvey> loadArray = new ArrayList<ModelLoadSurvey>();
			for (int i = 0; i < loadSurvey.length(); i++) { // IDENTIFYING VALUES AND PUTTING TO ARRAYLIST
  
				String dateTime = "", kvah = "", kWh = "", currentR = "", currentY = "", currentB = "", voltageR = "", voltageY = "", voltageB = "", kvarhLag = "", kvarhLead = "", pf = "", kw = "", kva = "";
			
				JSONObject loadObj = loadSurvey.getJSONObject(i); 
				for (int k = 0; k < columns.length; k++) {
					String obisCode=columns[k].trim();
					switch (obisCode) {
					case XmlHelper.OBIS_LOAD_DATETIME:
						dateTime = loadObj.getString("V" + k);
						break;
					case XmlHelper.OBIS_LOAD_CURRENTR:
						currentR = loadObj.getString("V" + k);
						currentR = helper.getRoundedLoadProfile(currentR, helper.CURRENT, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_CURRENTY:
						currentY = loadObj.getString("V" + k);
						currentY = helper.getRoundedLoadProfile(currentY, helper.CURRENT, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_CURRENTB:
						currentB = loadObj.getString("V" + k);
						currentB = helper.getRoundedLoadProfile(currentB, helper.CURRENT, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_VOLTAGER:
						voltageR = loadObj.getString("V" + k);
						voltageR = helper.getRoundedLoadProfile(voltageR, helper.VOLTAGE, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_VOLTAGEY:
						voltageY = loadObj.getString("V" + k);
						voltageY = helper.getRoundedLoadProfile(voltageY, helper.VOLTAGE, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_VOLTAGEB:
						voltageB = loadObj.getString("V" + k);
						voltageB = helper.getRoundedLoadProfile(voltageB, helper.VOLTAGE, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_KWH:
						kWh = loadObj.getString("V" + k).trim();
						kWh = helper.getRoundedLoadProfile(kWh, helper.ENERGY, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_KVARHLAG:
						kvarhLag = loadObj.getString("V" + k);
						kvarhLag = helper.getRoundedLoadProfile(kvarhLag, helper.ENERGY, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_KVARHLEAD:
						kvarhLead = loadObj.getString("V" + k);
						kvarhLead = helper.getRoundedLoadProfile(kvarhLead, helper.ENERGY, meterMake, makeVersion, obisCode);
						break;
					case XmlHelper.OBIS_LOAD_KVAH:
						kvah = loadObj.getString("V" + k).trim();
						kvah = helper.getRoundedLoadProfile(kvah, helper.ENERGY, meterMake, makeVersion, obisCode);
						break;
					}

				}

				if (!kWh.isEmpty() && !kvah.isEmpty()) {// CALCULATING PF
					if (kWh.equals("0") && kvah.equals("0")) {
						pf = "0";
					} else {
						pf = String.valueOf(Double.parseDouble(kWh) / Double.parseDouble(kvah));
						pf = helper.getRoundedValue(pf, "0.000");
					}
				}

				if (!kWh.isEmpty()) {// CALCULATING KW
					kw = String.valueOf(Double.parseDouble(kWh) * 2);
					kw = helper.getRoundedValue(kw, "0.0000");
				}

				if (!kvah.isEmpty()) {// CALCULATING KVA
					kva = String.valueOf(Double.parseDouble(kvah) * 2);
					kva = helper.getRoundedValue(kva, "0.0000");
				}

				/*
				 * System.out.println("kWh 	  :  "+kWh);
				 * System.out.println("kvah 	  :  "+kvah);
				 * System.out.println("kva 	  :  "+kva);
				 * System.out.println("kw 	  :  "+kw);
				 * System.out.println("pf 	  :  "+pf);
				 */

				// System.out.println("dateTime : "+dateTime);
				// System.out.println("currentR : "+currentR);
				// System.out.println("currentY : "+currentY);
				// System.out.println("currentB : "+currentB);
				// System.out.println("voltageR : "+voltageR);
				// System.out.println("voltageY : "+voltageY);
				// System.out.println("voltageB : "+voltageB);
				// System.out.println("kWh : "+kWh);
				// System.out.println("kvarhLag : "+kvarhLag);
				// System.out.println("kvahLead : "+kvahLead);
				// System.out.println("kvah : "+kvah);
				// System.out.println("pf : "+pf);
				// System.out.println("\n- - - - - - - - - - - - - - -");
				ModelLoadSurvey load = new ModelLoadSurvey();
				try {
					load.setDate(dateFormat.parse(dateTime));
				} catch (Exception e) {//IF COMING IN HEX DATE FORMAT
					load.setDate(dateFormat.parse(AmrMethods.getOctetStringDates(dateTime)));
				}
				load.setCurrentB(currentB);
				load.setCurrentR(currentR);
				load.setCurrentY(currentY);
				load.setKva(kva);
				load.setKvah(kvah);
				load.setKvarhLag(kvarhLag);
				load.setKvarhLead(kvarhLead);
				load.setKw(kw);
				load.setkWh(kWh);
				load.setPf(pf);
				load.setVoltageB(voltageB);
				load.setVoltageR(voltageR);
				load.setVoltageY(voltageY);

				loadArray.add(load);

			}

			Collections.sort(loadArray); // ORDRE BY DATE
			// Collections.reverse(loadArray); // REVERSE ARRAY TO GET LAST DATE
			// FIRST

			for (String day : days) {// DATE ARRAY

				Element el = doc.createElement("DAYPROFILE");

				Attr attrG22 = doc.createAttribute("DATE");
				attrG22.setValue(day);
				el.setAttributeNode(attrG22);
				int periodCount = 1;
				
				for (int i = 0; i < loadArray.size(); i++) {
					ModelLoadSurvey load = loadArray.get(i);
					String dateTime = dateFormat.format(load.getDate());
					
					if (day.trim().equals(dateTime.split(" ")[0].trim())) {
						el.appendChild(createD4Ip(String.valueOf(periodCount++), load));

						/*if (periodCount == 49) { // BREAKING THE LOOP AFTER GETTTING 48 INTERVALS
							break;
						}*/

					}
				}

				D4.appendChild(el);
			}

			elementMain.appendChild(D4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Element createD4Ip(String number,ModelLoadSurvey load) {
        Element el = doc.createElement("IP");
        try {
            Attr attrG22 = doc.createAttribute("INTERVAL");
            attrG22.setValue(number);
            el.setAttributeNode(attrG22);
            
            
            if(!load.getVoltageR().isEmpty() && !load.getVoltageB().isEmpty()){
            	el.appendChild(createD4Parameter(helper.PARAM_VOLTAGE_R, load.getVoltageR(), "V")); //Voltage r
                el.appendChild(createD4Parameter(helper.PARAM_VOLTAGE_Y, load.getVoltageY(), "V")); //Voltage y
                el.appendChild(createD4Parameter(helper.PARAM_VOLTAGE_B, load.getVoltageB(), "V")); //Voltage b
            }
            el.appendChild(createD4Parameter(helper.PARAM_CURRENT_R, load.getCurrentR(), "A")); //Current r
            el.appendChild(createD4Parameter(helper.PARAM_CURRENT_Y, load.getCurrentY(), "A")); //Current r
            el.appendChild(createD4Parameter(helper.PARAM_CURRENT_B, load.getCurrentB(), "A")); //Current r
            el.appendChild(createD4Parameter(helper.PARAM_KW ,load.getKw(), "K")); // KW
            el.appendChild(createD4Parameter(helper.PARAM_KVA, load.getKva(), "K")); // KVA
            el.appendChild(createD4Parameter(helper.PARAM_KWH, load.getkWh(), "K")); // KWH
            el.appendChild(createD4Parameter(helper.PARAM_KVAH, load.getKvah(), "K")); // KVAH
            if(!load.getKvarhLag().isEmpty() && !load.getKvarhLead().isEmpty()){
            el.appendChild(createD4Parameter("P7-2-5-2-0", load.getKvarhLag(), "K")); //KVARH (LAG)
            el.appendChild(createD4Parameter("P7-2-6-2-0", load.getKvarhLead(), "K")); //KVARH (LEAD) 
            }
            el.appendChild(createD4Parameter("P4-4-4-1-0", load.pf, "")); //POWER FACTOR
            
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return el;
    }

    private Element createD4Parameter(String paramCode, String value, String unit) {
        Element G22 = doc.createElement("PARAMETER");
        try {
            Attr attrG22 = doc.createAttribute("PARAMCODE");
            attrG22.setValue(paramCode);
            G22.setAttributeNode(attrG22);

            Attr attrG2Val = doc.createAttribute("VALUE");
            attrG2Val.setValue(value);
            G22.setAttributeNode(attrG2Val);

            Attr attrG22Name = doc.createAttribute("UNIT");
            attrG22Name.setValue(unit);
            G22.setAttributeNode(attrG22Name);

            G22.appendChild(doc.createTextNode(" "));
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return G22;
    }

      private boolean D5(JSONObject meter,String meterMake, String makeVersion) { //D5 EVENTS XXX
        try {
 
			JSONArray eventHistory=new JSONArray(meter.getString("EVENT_HISTORY"));
			  
			  if(eventHistory.length()<=0)
			  {
				  System.err.println("NO EVENT HISTORY FOUND ");
				  return false;
			  }
			
				final String OBIS_EVENT_0= "0.0.96.11.0.255";  //EVENTS VOLTAGE RELATED
				final String OBIS_EVENT_1= "0.0.96.11.1.255";  //EVENTS CURRENT
				final String OBIS_EVENT_2= "0.0.96.11.2.255";  //EVENTS POWER ON OFF
				final String OBIS_EVENT_3= "0.0.96.11.3.255";  //EVENTS TRANSACTION
				final String OBIS_EVENT_4= "0.0.96.11.4.255";  //EVENTS OTHER
				final String OBIS_EVENT_5= "0.0.96.11.5.255";  //EVENTS NON-ROLLOVER
				final String OBIS_EVENT_6= "0.0.96.11.6.255";  //EVENTS CONTROL
			
				 Element D5 = doc.createElement("D5"); // D5 START
				 
					String COLUMN_STRUCTURE= meter.getString("COLUMN_STRUCTURE");
		  			JSONArray COLUMN_ARRAY= new JSONArray(COLUMN_STRUCTURE);
		  			JSONObject OBJ_COLUMNS = COLUMN_ARRAY.getJSONObject(0);
		  			 
		  			
				for(int eventType = 0; eventType < 6; eventType++) {
					String KEY_COLUMN = "COLUMN_0.0.99.98."+eventType+".255";
					
					if(OBJ_COLUMNS.has(KEY_COLUMN)) {
						
						
						String STRUCTURE = OBJ_COLUMNS.getString(KEY_COLUMN); // GETTING COLUMN STRUCTURE OF EVENTS
						
						String [] columns=STRUCTURE.split("\\|");
						
						 
						for (int i = 0; i < eventHistory.length(); i++) {
							JSONObject loadObj= eventHistory.getJSONObject(i);
							
							String dateTime="",eventCode="",currentR="",currentY="",currentB="",voltageR="",voltageY="",voltageB="",pfR="",pfY="",pfB="",cumulativeEnergy="";
							 
							for (int k = 0; k <columns.length; k++) {
								String OBIS_CODE=columns[k].trim();
								String VALUE_KEY =eventType+"-V"+k;
								
								if(!loadObj.has(VALUE_KEY))
								{
									continue; // IF THIS KEY NOT EXIST, CONTINUE WITH NEXT VALUE 
								}
								
								
								if(OBIS_CODE.equals(OBIS_EVENT_0) || OBIS_CODE.equals(OBIS_EVENT_1)|| OBIS_CODE.equals(OBIS_EVENT_2)|| OBIS_CODE.equals(OBIS_EVENT_3)|| OBIS_CODE.equals(OBIS_EVENT_4)||OBIS_CODE.equals(OBIS_EVENT_5)|| OBIS_CODE.equals(OBIS_EVENT_6))
								{
								 eventCode = loadObj.getString(VALUE_KEY).trim();
									
								}
								switch (OBIS_CODE) { 
								case XmlHelper.OBIS_EVENT_DATETIME:
									dateTime=loadObj.getString(VALUE_KEY).trim();
									break;
								case XmlHelper.OBIS_EVENT_CURRENT_R:
									currentR=loadObj.getString(VALUE_KEY).trim();
									currentR=helper.getRoundedSnapShots(currentR,helper.CURRENT,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_CURRENT_Y:
									currentY=loadObj.getString(VALUE_KEY);
									currentY=helper.getRoundedSnapShots(currentY,helper.CURRENT,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_CURRENT_B:
									currentB=loadObj.getString(VALUE_KEY).trim();
									currentB=helper.getRoundedSnapShots(currentB,helper.CURRENT,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_VOLTAGE_R:
									voltageR=loadObj.getString(VALUE_KEY).trim();
									voltageR=helper.getRoundedSnapShots(voltageR,helper.VOLTAGE,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_VOLTAGE_Y:
									voltageY=loadObj.getString(VALUE_KEY).trim();
									voltageY=helper.getRoundedSnapShots(voltageY,helper.VOLTAGE,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_VOLTAGE_B:
									voltageB=loadObj.getString(VALUE_KEY).trim();
									voltageB=helper.getRoundedSnapShots(voltageB,helper.VOLTAGE,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_PF_R:
									pfR=loadObj.getString(VALUE_KEY).trim();
									pfR=helper.getRoundedSnapShots(pfR,helper.PF,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_PF_Y:
									pfY=loadObj.getString(VALUE_KEY).trim();
									pfY=helper.getRoundedSnapShots(pfY,helper.PF,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_PF_B:
									pfB=loadObj.getString(VALUE_KEY).trim();
									pfB=helper.getRoundedSnapShots(pfB,helper.PF,meterMake,makeVersion,OBIS_CODE);
									break;
								case XmlHelper.OBIS_EVENT_KWH:
									cumulativeEnergy=loadObj.getString(VALUE_KEY).trim();
									cumulativeEnergy=helper.getRoundedSnapShots(cumulativeEnergy,helper.ENERGY,meterMake,makeVersion,OBIS_CODE);
									break;
								}
								
							}
							
							if(eventCode.isEmpty())
							{
								continue; //IF EVENT CODE IS NOT THERE, CONTINUE THE LOOP WITH NEXT VALUE
							}
							
//							System.out.println("dateTime  :  "+dateTime);
//							System.err.println("DLMS eventCode :  "+eventCode);
//							System.out.println("cumulativeEnergy  :  "+cumulativeEnergy);
//							System.out.println("currentR  :  "+currentR);
//							System.out.println("currentY  :  "+currentY);
//							System.out.println("currentB  :  "+currentB);
//							System.out.println("voltageR  :  "+voltageR);
//							System.out.println("voltageY  :  "+voltageY);
//							System.out.println("voltageB  :  "+voltageB);
//							System.out.println("pfR  :  "+pfR);
//							System.out.println("pfY  :  "+pfY);
//							System.out.println("pfB  :  "+pfB);
//							System.out.println("\n- - - - - - - - - - - - - - -");
							
					       D5.appendChild(d5Tag(KEY_COLUMN,eventCode, dateTime,cumulativeEnergy,currentR,currentY,currentB,voltageR,voltageY,voltageB,pfR,pfY,pfB));
							
						}
					}
				}
				
				
            elementMain.appendChild(D5);
            return true;
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return false; 
    }

    private Element d5Tag(String KEY_COLUMN,String code, String time,String cumulativeEnergy,String currentR,String currentY,String currentB,String voltageR,String voltageY,String voltageB,String pfR,String pfY,String pfB) {
        Element event = doc.createElement("EVENT");  
        try {
        	String [] codeAndStatus= helper.getCdfCodeAndStatus(code,KEY_COLUMN);
        	String eventCode=codeAndStatus[0];
        	String eventStatus   =codeAndStatus[1];
        
        	
            Attr cod = doc.createAttribute("CODE"); 
            cod.setValue(eventCode);
            event.setAttributeNode(cod);

            Attr attrG22 = doc.createAttribute("STATUS");
            attrG22.setValue(eventStatus);
            event.setAttributeNode(attrG22);

            Attr attrG22Name = doc.createAttribute("TIME");
            attrG22Name.setValue(time);
            event.setAttributeNode(attrG22Name);
            
            
            if(!KEY_COLUMN.equals("COLUMN_0.0.99.98.2.255")) //NON POWER RELATED
			{
            	event.appendChild(createD5SnapShot(helper.PARAM_KWH, cumulativeEnergy, "K")); //CUMULATIVE ENERGY KWH
            	event.appendChild(createD5SnapShot(helper.PARAM_VOLTAGE_R, voltageR, "V")); //Voltage R
            	event.appendChild(createD5SnapShot(helper.PARAM_VOLTAGE_Y, voltageY, "V")); //Voltage Y
            	event.appendChild(createD5SnapShot(helper.PARAM_VOLTAGE_B, voltageB, "V")); //Voltage B
            	
            	event.appendChild(createD5SnapShot(helper.PARAM_CURRENT_R, currentR, "A")); //Voltage R
            	event.appendChild(createD5SnapShot(helper.PARAM_CURRENT_Y, currentY, "A")); //Voltage Y
            	event.appendChild(createD5SnapShot(helper.PARAM_CURRENT_B, currentB, "A")); //Voltage B
            	
            	event.appendChild(createD5SnapShot(helper.PARAM_PF_R, pfR, "NA")); //PF R
            	event.appendChild(createD5SnapShot(helper.PARAM_PF_Y, pfY, "NA")); //PF Y
            	event.appendChild(createD5SnapShot(helper.PARAM_PF_B, pfB, "NA")); //PF B
			}
            
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return event;
    }
    
	private Element createD5SnapShot(String paramCode, String value, String unit) {
        Element G22 = doc.createElement("SNAPSHOT");
        try {
            Attr attrG22 = doc.createAttribute("PARAMCODE");
            attrG22.setValue(paramCode);
            G22.setAttributeNode(attrG22);

            Attr attrG2Val = doc.createAttribute("VALUE");
            attrG2Val.setValue(value);
            G22.setAttributeNode(attrG2Val);

            Attr attrG22Name = doc.createAttribute("UNIT");
            attrG22Name.setValue(unit);
            G22.setAttributeNode(attrG22Name);

            G22.appendChild(doc.createTextNode(" "));
        } catch (Exception e) {
        	  e.printStackTrace();
        }
        return G22;
    }
 
}
