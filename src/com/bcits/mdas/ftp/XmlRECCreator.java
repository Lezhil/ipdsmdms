package com.bcits.mdas.ftp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;

 public class XmlRECCreator {

	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	Element elementMain;
	String timeStamp;
	XmlHelper helper;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

	//public String createXML(String fileName, AmrInstantaneousEntity amrInstantaneousEntity, List<AmrBillsEntity> amrBillsEntity, List<AmrLoadEntity> amrLoadEntity, List<AmrEventsEntity> amrEventsEntity,Object feederMasterEntity) {
	public String createXML(String fileName, AmrInstantaneousEntity amrInstantaneousEntity, List<AmrBillsEntity> amrBillsEntity, List<AmrLoadEntity> amrLoadEntity, List<AmrEventsEntity> amrEventsEntity,Object feederMasterEntity) {
		try {
 
			helper = new XmlHelper();

			timeStamp = dateFormat.format(Calendar.getInstance().getTime());

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			// ROOT, UTILITYTYPE AND
			// AUTHENTICATOR-------------------------------------------
			// ROOT CDF elements
			Element elCDF = doc.createElement("CDF");
			doc.appendChild(elCDF);
			// UTILITYTYPE
			// element----------------------------------------------------------
			elementMain = doc.createElement("UTILITYTYPE");
			elCDF.appendChild(elementMain);
			// set attribute to elUTILITYTYPE
			Attr attr = doc.createAttribute("CODE");
			attr.setValue("1");
			elementMain.setAttributeNode(attr);
			// AUTHENTICATOR element
			// -------------------------------------------------------
			Element elAUTHENTICATOR = doc.createElement("AUTHENTICATOR");
			elAUTHENTICATOR.appendChild(doc.createTextNode(" "));
			elCDF.appendChild(elAUTHENTICATOR);

			D1(amrInstantaneousEntity,feederMasterEntity);

			D2(amrInstantaneousEntity);

			D3(amrBillsEntity);
			D4(amrLoadEntity);

			D5(amrEventsEntity);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(fileName));

			transformer.transform(source, result);

			return CreateXmlAndUpload.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "XML CREATION : "+e.getMessage();
		}
	}

	 private void D3(List<AmrBillsEntity> amrBillsEntityList)  {
			if (amrBillsEntityList.size()!=0)
			{	
			 Collections.sort(amrBillsEntityList);     // ORDRE BY DATE
			// Collections.reverse(amrBillsEntityList);
				Element D3 = doc.createElement("D3");  
				  int i=0;
				  int j=1;
				  //System.out.println("Days with duplicates"+amrBillsEntityList.size());
				 for (AmrBillsEntity amrBillsEntityOBJ:amrBillsEntityList) 
				 {
					// if(j==13) {break;}
					 
					
					 
					   String num = (i >= 10) ? String.valueOf(i) : "0" + String.valueOf(i);//ROUNDING TO 2 Digit Eg. B3-03
		                String d3SubName = "D3-" + num;
		                
		                String b2Date=null;
		                /*System.out.println("b2Date---out---->"+b2Date);
		                System.out.println("i-----Bill------>"+i);
		                System.out.println("amrBillsEntityList.size()---------->"+Integer.toString(amrBillsEntityList.size()));*/
		               
		                if(i<amrBillsEntityList.size()-1) {
			                b2Date=amrBillsEntityList.get(i+1).getMyKey().getReadTime().toString();
			                //System.out.println("b2Date------->"+b2Date);
		                }
		               
		               
		                	D3.appendChild(D3Sub(amrBillsEntityOBJ, d3SubName,amrBillsEntityOBJ.getMyKey().getReadTime().toString(),b2Date));
		              
		                i++;j++;
					 
			    }
				
				 elementMain.appendChild(D3); 
					
			}
			else
			{
				//System.out.println("Size of Bill array is zero");
			}
		}
		 
		 private Element D3Sub(AmrBillsEntity bill, String elementName,String day,String b2Date) {
		        Element el = doc.createElement(elementName);
		        
		        
		      
		        try {
		        	Attr attrG21 = doc.createAttribute("MECHANISM");
		            attrG21.setValue("Auto");
		            el.setAttributeNode(attrG21);

		            Attr attrG22 = doc.createAttribute("DATETIME");
		            attrG22.setValue(getFormatedDateEvent(day));
		            el.setAttributeNode(attrG22);
		         // IF IT IS NOT THE LAST BILLING DATA 
		            if(b2Date!=null) {
		            	
						     el.appendChild(D3B2(getFormatedDateEvent(b2Date),"B2")); 
						
		        	            }
		            
		           if(bill.getKvah()!=null)el.appendChild(D3B3("P7-3-5-2-0",String.valueOf( bill.getKvah()), "k","B3")); 
		           if(bill.getKwh()!=null)el.appendChild(D3B3("P7-1-5-2-0", String.valueOf(bill.getKwh()), "k","B3"));
		           if(bill.getKvarhLead()!=null)el.appendChild(D3B3("P7-2-1-2-0", String.valueOf(bill.getKvarhLead()), "k","B3"));
		           if(bill.getKvarhLag()!=null)el.appendChild(D3B3("P7-2-4-2-0", String.valueOf(bill.getKvarhLag()), "k","B3"));
		           
		           if(bill.getKvahTz1()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz1()), "k","B4","1"));
		           if(bill.getKvahTz2()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz2()), "k","B4","2"));
		           if(bill.getKvahTz3()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz3()), "k","B4","3"));
		           if(bill.getKvahTz4()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz4()), "k","B4","4"));
		           if(bill.getKvahTz5()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz5()), "k","B4","5"));
		           if(bill.getKvahTz6()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz6()), "k","B4","6"));
		           if(bill.getKvahTz7()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz7()), "k","B4","7"));
		           if(bill.getKvahTz8()!=null)el.appendChild(D3B4("P7-3-5-2-0", String.valueOf(bill.getKvahTz8()), "k","B4","8"));
		           if(bill.getKwhTz1()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz1()), "k","B4","1"));
		           if(bill.getKwhTz2()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz2()), "k","B4","2"));
		           if(bill.getKwhTz3()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz3()), "k","B4","3"));
		           if(bill.getKwhTz4()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz4()), "k","B4","4"));
		           if(bill.getKwhTz5()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz5()), "k","B4","5"));
		           if(bill.getKwhTz6()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz6()), "k","B4","6"));
		           if(bill.getKwhTz7()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz7()), "k","B4","7"));
		           if(bill.getKwhTz8()!=null)el.appendChild(D3B4("P7-1-5-2-0", String.valueOf(bill.getKwhTz8()), "k","B4","8"));
		           
		           if(bill.getKva()!=null)el.appendChild(D3B5("P7-6-5-2-0", String.valueOf(bill.getKva()),bill.getDateKva(), "k","B5"));
		           if(bill.getDemandKw()!=null)el.appendChild(D3B5("P7-4-5-2-0", String.valueOf(bill.getDemandKw()),bill.getOccDateKw(), "k","B5"));
		           
		           if(bill.getKvaTz1()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz1()),bill.getDateKvaTz1(), "k","B6","1"));
		           if(bill.getKvaTz2()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz2()),bill.getDateKvaTz2(), "k","B6","2"));
		           if(bill.getKvaTz3()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz3()),bill.getDateKvaTz3(), "k","B6","3"));
		           if(bill.getKvaTz4()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz4()),bill.getDateKvaTz4(), "k","B6","4"));
		           if(bill.getKvaTz5()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz5()),bill.getDateKvaTz5(), "k","B6","5"));
		           if(bill.getKvaTz6()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz6()),bill.getDateKvaTz6(), "k","B6","6"));
		           if(bill.getKvaTz7()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz7()),bill.getDateKvaTz7(), "k","B6","7"));
		           if(bill.getKvaTz8()!=null)el.appendChild(D3B6("P7-6-5-2-0", String.valueOf(bill.getKvaTz8()),bill.getDateKvaTz8(), "k","B6","8"));
		           if(bill.getKwTz1()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz1()),bill.getDateKwTz1(), "k","B6","1"));
		           if(bill.getKwTz2()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz2()),bill.getDateKwTz2(), "k","B6","2"));
		           if(bill.getKwTz3()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz3()),bill.getDateKwTz3(), "k","B6","3"));
		           if(bill.getKwTz4()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz4()),bill.getDateKwTz4(), "k","B6","4"));
		           if(bill.getKwTz5()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz5()),bill.getDateKwTz5(), "k","B6","5"));
		           if(bill.getKwTz6()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz6()),bill.getDateKwTz6(), "k","B6","6"));
		           if(bill.getKwTz7()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz7()),bill.getDateKwTz7(), "k","B6","7"));
		           if(bill.getKwTz8()!=null)el.appendChild(D3B6("P7-4-5-2-0", String.valueOf(bill.getKwTz8()),bill.getDateKwTz8(), "k","B6","8"));
		           
		           
		           if(bill.getSysPfBilling()!=null)el.appendChild(D3B9("P4-4-4-1-0", String.valueOf(bill.getSysPfBilling()),"B9"));
		           
		           
		           
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return el;
		    }
		 //======================
		 private Element D3B2(String b2Date,String tag) {
		        Element G22 = doc.createElement(tag);
		        try {
		        	
		            Attr attrG22 = doc.createAttribute("MECHANISM");
		            attrG22.setValue("Auto");
		            G22.setAttributeNode(attrG22);
		           
		            Attr attrG2Val = doc.createAttribute("DATETIME");
		            attrG2Val.setValue(b2Date);
		            G22.setAttributeNode(attrG2Val);
		            
		            G22.appendChild(doc.createTextNode(" "));
		        } catch (Exception e) {
		        	  e.printStackTrace();
		        }
		        return G22;
		    }
		 //===============
		 private Element D3B3(String paramCode, String value, String unit,String tag) {
		    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="k"></B5> KVA MD
		        Element G22 = doc.createElement(tag);
		        try {
		            Attr attrG22 = doc.createAttribute("PARAMCODE");
		            attrG22.setValue(paramCode);
		            G22.setAttributeNode(attrG22);
		            
		            Attr attrG22Name = doc.createAttribute("UNIT");
		            attrG22Name.setValue(unit);
		            G22.setAttributeNode(attrG22Name);

		            Attr attrG2Val = doc.createAttribute("VALUE");
		            attrG2Val.setValue(value);
		            G22.setAttributeNode(attrG2Val);
		            
		           		            
		            G22.appendChild(doc.createTextNode(" "));
		        } catch (Exception e) {
		        	  e.printStackTrace();
		        }
		        return G22;
		    }
		 //===============
		 private Element D3B4(String paramCode, String value, String unit,String tag,String TOD) {
		    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="k"></B5> KVA MD
		        Element G22 = doc.createElement(tag);
		        try {
		            Attr attrG22 = doc.createAttribute("PARAMCODE");
		            attrG22.setValue(paramCode);
		            G22.setAttributeNode(attrG22);
		            
		            Attr attrG22Name = doc.createAttribute("UNIT");
		            attrG22Name.setValue(unit);
		            G22.setAttributeNode(attrG22Name);

		            Attr attrG2Val = doc.createAttribute("VALUE");
		            attrG2Val.setValue(value);
		            G22.setAttributeNode(attrG2Val);
		            
		         		            
		            Attr attrG22TOD = doc.createAttribute("TOD");
		            attrG22TOD.setValue(TOD);
		            G22.setAttributeNode(attrG22TOD);
		            
		            G22.appendChild(doc.createTextNode(" "));
		        } catch (Exception e) {
		        	  e.printStackTrace();
		        }
		        return G22;
		    }
		 //===============
		 
		 private Element D3B5(String paramCode, String value,String ocDate, String unit,String tag) {
		    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="k"></B5> KVA MD
		        Element G22 = doc.createElement(tag);
		        try {
		            Attr attrG22 = doc.createAttribute("PARAMCODE");
		            attrG22.setValue(paramCode);
		            G22.setAttributeNode(attrG22);

		            Attr attrG2Val = doc.createAttribute("VALUE");
		            attrG2Val.setValue(value);
		            G22.setAttributeNode(attrG2Val);
		            
		            Attr occDate = doc.createAttribute("OCCDATE");
		            occDate.setValue(getFormatedDate(ocDate));
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
		 //===============
		 
		 private Element D3B6(String paramCode, String value,String ocDate, String unit,String tag,String TOD) {
		    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="k"></B5> KVA MD
		        Element G22 = doc.createElement(tag);
		        try {
		            Attr attrG22 = doc.createAttribute("PARAMCODE");
		            attrG22.setValue(paramCode);
		            G22.setAttributeNode(attrG22);

		            Attr attrG2Val = doc.createAttribute("VALUE");
		            attrG2Val.setValue(value);
		            G22.setAttributeNode(attrG2Val);
		            
		            Attr occDate = doc.createAttribute("OCCDATE");
		            occDate.setValue(getFormatedDate(ocDate));
		            G22.setAttributeNode(occDate);
		            
		            Attr attrG22Unit = doc.createAttribute("UNIT");
		            attrG22Unit.setValue(unit);
		            G22.setAttributeNode(attrG22Unit);
		            
		            Attr attrG22TOD = doc.createAttribute("TOD");
		            attrG22TOD.setValue(TOD);
		            G22.setAttributeNode(attrG22TOD);
		            
		            G22.appendChild(doc.createTextNode(" "));
		        } catch (Exception e) {
		        	  e.printStackTrace();
		        }
		        return G22;
		    }
		 //===============
		 
		 private Element D3B9(String paramCode, String value,String tag) {
		    	//<B5 PARAMCODE="P7-6-5-2-4" VALUE="0.526" OCCDATE="05-12-2016 18:00:00" UNIT="k"></B5> KVA MD
		        Element G22 = doc.createElement(tag);
		        try {
		            Attr attrG22 = doc.createAttribute("PARAMCODE");
		            attrG22.setValue(paramCode);
		            G22.setAttributeNode(attrG22);

		            Attr attrG2Val = doc.createAttribute("VALUE");
		            attrG2Val.setValue(value);
		            G22.setAttributeNode(attrG2Val);
		            
		        
		            
		            
		            
		            G22.appendChild(doc.createTextNode(" "));
		        } catch (Exception e) {
		        	  e.printStackTrace();
		        }
		        return G22;
		    }
		 //===============

		private void D1(AmrInstantaneousEntity amrInstantaneousEntity, Object feederMasterEntity) {
		        //D1 TAG  
		        try {
		            
		        	Object[] masterData=(Object[]) feederMasterEntity;
					String g7=(null!=masterData[0])? masterData[0].toString():""; //PT RATIO
					String g8=(null!=masterData[1])? masterData[1].toString():""; //ct_ratio
					String g22=(null!=masterData[2])? masterData[2].toString():""; //mtrmake
					String g17=(null!=masterData[3])? masterData[3].toString():""; //mtr_firmware
					String g1177=(null!=masterData[4])? masterData[4].toString():""; //year_of_man
					String g1194=(null!=masterData[5])? masterData[5].toString():""; //dlms meter type
					 
		        	
		            Element D1 = doc.createElement("D1");
		           
		            Element G1 = doc.createElement("G1");
		            G1.appendChild(doc.createTextNode(amrInstantaneousEntity.getMyKey().getMeterNumber())); 
		            D1.appendChild(G1);

		            Element G2 = doc.createElement("G2");
		            G2.appendChild(doc.createTextNode(dateFormat.format(amrInstantaneousEntity.getReadTime())));              //MTER DATE
		            D1.appendChild(G2);

		           Element G3 = doc.createElement("G3");
		           G3.appendChild(doc.createTextNode(dateFormat.format(amrInstantaneousEntity.getModemTime())));             //READING TIME AND DATE
		           D1.appendChild(G3);

		           Element G4 = doc.createElement("G4");
		           G4.appendChild(doc.createTextNode(dateFormat.format(amrInstantaneousEntity.getTimeStamp())));             //READING TIME AND DATE
		           D1.appendChild(G4);
		           
		           Element G7 = doc.createElement("G7");
		           G7.appendChild(doc.createTextNode(g7));             //READING TIME AND DATE
		           D1.appendChild(G7);
		           
		           Element G8 = doc.createElement("G8");
		           G8.appendChild(doc.createTextNode(g8));             //READING TIME AND DATE
		           D1.appendChild(G8);
		           
		           Element G22 = doc.createElement("G22");
		           G22.appendChild(doc.createTextNode(g22));             //READING TIME AND DATE
		           D1.appendChild(G22);
		           
		           Element G17 = doc.createElement("G17");
		           G17.appendChild(doc.createTextNode(g17));             //READING TIME AND DATE
		           D1.appendChild(G17);
		           
		           Element G1177 = doc.createElement("G1177");
		           G1177.appendChild(doc.createTextNode(g1177));             //READING TIME AND DATE
		           D1.appendChild(G1177);
		           
		           Element G1194 = doc.createElement("G1194");
		           G1194.appendChild(doc.createTextNode(g1194));             //READING TIME AND DATE
		           D1.appendChild(G1194);
		           
		         
		            elementMain.appendChild(D1);
		        } catch (Exception e) {
		           e.printStackTrace();
		        }

		    }
		 
		 private void D2(AmrInstantaneousEntity amrInstantaneousEntity) { //D2 INSTANTANEOUS PARAMETERS 
		        try {
		            Element D2 = doc.createElement("D2");
		            
		           /* D2.appendChild(createD2Attr(helper.D2_PARAM_v_r,  String.valueOf(amrInstantaneousEntity.getvR()), "V"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_v_y,  String.valueOf(amrInstantaneousEntity.getvY()) , "V"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_v_b,  String.valueOf(amrInstantaneousEntity.getvB()) , "V"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_i_r,  String.valueOf(amrInstantaneousEntity.getiR()), "A"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_i_y,  String.valueOf(amrInstantaneousEntity.getiY()) , "A"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_i_b,  String.valueOf(amrInstantaneousEntity.getiB()) , "A"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_pf_r,  String.valueOf(amrInstantaneousEntity.getPfR()), "NA"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_pf_y, String.valueOf( amrInstantaneousEntity.getPfY()) , "NA"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_pf_b, String.valueOf( amrInstantaneousEntity.getPfB()) , "NA"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_pf_threephase,  String.valueOf(amrInstantaneousEntity.getPfThreephase() ), "NA"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_frequency,  String.valueOf(amrInstantaneousEntity.getPfB()) , "HZ"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_kwh,  String.valueOf(amrInstantaneousEntity.getKwh()), "k"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_kvah, String.valueOf( amrInstantaneousEntity.getKvah()), "k"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_kvah_lag,  String.valueOf(amrInstantaneousEntity.getKvarhLag()), "k"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_kvah_lead, String.valueOf( amrInstantaneousEntity.getKvarhLead()) , "k"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_kvar,  String.valueOf(amrInstantaneousEntity.getKvar()), "k"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_power_off_count, String.valueOf( amrInstantaneousEntity.getPowerOffCount()) , "NA"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_power_off_duration,  String.valueOf(amrInstantaneousEntity.getPowerOffDuration()) , "min"));
		            
		            D2.appendChild(createD2Attr(helper.D2_PARAM_Md_Kw,  String.valueOf(amrInstantaneousEntity.getMdKw()) , "k"));
		            D2.appendChild(createD2Attr(helper.D2_PARAM_md_kva, String.valueOf( amrInstantaneousEntity.getMdKva()) , "k"));*/
		            //Doing for L&T
		            if(amrInstantaneousEntity.getvR()!=null)D2.appendChild(createD2Attr("P1-2-1-1-0",  String.valueOf(amrInstantaneousEntity.getvR()), "V"));
		            if(amrInstantaneousEntity.getPfR()!=null)D2.appendChild(createD2Attr("P2-1-1-1-0",  String.valueOf(amrInstantaneousEntity.getiR()) , "A"));
		            if(amrInstantaneousEntity.getvY()!=null)D2.appendChild(createD2Attr("P1-2-2-1-0",  String.valueOf(amrInstantaneousEntity.getvY()) , "V"));
		            if(amrInstantaneousEntity.getPfY()!=null)D2.appendChild(createD2Attr("P2-1-2-1-0",  String.valueOf(amrInstantaneousEntity.getiY()) , "A"));
		            if(amrInstantaneousEntity.getvB()!=null)D2.appendChild(createD2Attr("P1-2-3-1-0",  String.valueOf(amrInstantaneousEntity.getvB()) , "V"));
		            if(amrInstantaneousEntity.getPfB()!=null)D2.appendChild(createD2Attr("P2-1-3-1-0",  String.valueOf(amrInstantaneousEntity.getiB()) , "A"));
		            
		            if(amrInstantaneousEntity.getPfR()!=null)D2.appendChild(createD2Attr("P4-1-1-0-0",  String.valueOf(amrInstantaneousEntity.getPfR()), "NA"));
		            if(amrInstantaneousEntity.getPfY()!=null)D2.appendChild(createD2Attr("P4-2-1-0-0",  String.valueOf(amrInstantaneousEntity.getPfY()), "NA"));
		            if(amrInstantaneousEntity.getPfB()!=null)D2.appendChild(createD2Attr("P4-3-1-0-0",  String.valueOf(amrInstantaneousEntity.getPfB()), "NA"));
		            if(amrInstantaneousEntity.getPfThreephase()!=null)D2.appendChild(createD2Attr("P4-4-1-0-0",  String.valueOf(amrInstantaneousEntity.getPfThreephase() ), "NA"));
		            
		            if(amrInstantaneousEntity.getPfB()!=null)D2.appendChild(createD2Attr("P9-1-0-0-0",  String.valueOf(amrInstantaneousEntity.getPfB()) , "HZ"));
		            
		            if(amrInstantaneousEntity.getiRAngle()!=null)D2.appendChild(createD2Attr("P3-4-4-1-0",  String.valueOf(amrInstantaneousEntity.getiRAngle()) , "k"));
		            if(amrInstantaneousEntity.getiYAngle()!=null)D2.appendChild(createD2Attr("P3-2-4-1-0",  String.valueOf(amrInstantaneousEntity.getiYAngle()) , "k"));
		            if(amrInstantaneousEntity.getiBAngle()!=null)D2.appendChild(createD2Attr("P3-3-4-1-0",  String.valueOf(amrInstantaneousEntity.getiBAngle()) , "k")); 
		            
		            if(amrInstantaneousEntity.getKwh()!=null)D2.appendChild(createD2Attr("P7-1-5-2-0",  String.valueOf(amrInstantaneousEntity.getKwh()) , "k"));
		            if(amrInstantaneousEntity.getKvarhLag()!=null)D2.appendChild(createD2Attr("P7-2-1-2-0",  String.valueOf(amrInstantaneousEntity.getKvarhLag()) , "k"));
		            if(amrInstantaneousEntity.getKvarhLead()!=null)D2.appendChild(createD2Attr("P7-2-4-2-0",  String.valueOf(amrInstantaneousEntity.getKvarhLead()) , "k"));
		            if(amrInstantaneousEntity.getKvah()!=null)D2.appendChild(createD2Attr("P7-3-5-2-0",  String.valueOf(amrInstantaneousEntity.getKvah()) , "k"));
		            
		            if(amrInstantaneousEntity.getPowerOffCount()!=null)D2.appendChild(createD2Attr("P11-1-2-0-0",  String.valueOf(amrInstantaneousEntity.getPowerOffCount()) , "NA"));
		            if(amrInstantaneousEntity.getPowerOffDuration()!=null)D2.appendChild(createD2Attr("P11-4-0-0-0",  String.valueOf(amrInstantaneousEntity.getPowerOffDuration()) , "min"));
		            if(amrInstantaneousEntity.getTamperCount()!=null)D2.appendChild(createD2Attr("1200",  String.valueOf(amrInstantaneousEntity.getTamperCount()) , "NA"));
		            if(amrInstantaneousEntity.getMdResetCount()!=null)D2.appendChild(createD2Attr("1199",  String.valueOf(amrInstantaneousEntity.getMdResetCount()) , "NA"));
		            D2.appendChild(createD2Attr("1189",  "" , "NA"));//TO-DO
		            if(amrInstantaneousEntity.getMdResetDate()!=null)D2.appendChild(createD2Attr("4",  getFormatedDate(amrInstantaneousEntity.getMdResetDate()), "NA"));
		            
		            if(amrInstantaneousEntity.getMdKw()!=null)D2.appendChild(createD2Attr("P7-4-5-2-4",  String.valueOf(amrInstantaneousEntity.getMdKw()) , "k"));
		            if(amrInstantaneousEntity.getMdKva()!=null)D2.appendChild(createD2Attr("P7-6-5-2-4",  String.valueOf(amrInstantaneousEntity.getMdKva()) , "k"));
		            
		            
		            
		            elementMain.appendChild(D2);
		        } catch (Exception e) {
		           e.printStackTrace();
		        }
		        	     	    }
		  private Element createD2Attr(String code, String value, String unit) {
	          Element G22 = doc.createElement("INSTPARAM");
	          try {
	        	  Attr attrG22Name = doc.createAttribute("UNIT");
	              attrG22Name.setValue(unit);
	              G22.setAttributeNode(attrG22Name);
	              
	             

	              Attr attrG2Val = doc.createAttribute("VALUE");
	              attrG2Val.setValue(value);
	              G22.setAttributeNode(attrG2Val);

	              Attr attrG22 = doc.createAttribute("CODE");
	              attrG22.setValue(code);
	              G22.setAttributeNode(attrG22);

	              G22.appendChild(doc.createTextNode(" "));
	          } catch (Exception e) {
	              e.printStackTrace();

	          }

	          return G22;
	      }
		 
		  @SuppressWarnings("null")
		private void D4(List<AmrLoadEntity> amrLoadEntityList) throws DOMException, Exception
		  {
			  List<AmrLoadEntity> amrLoadEntityList2=amrLoadEntityList;
			  
			   Element D4 = doc.createElement("D4");
				Attr attr = doc.createAttribute("INTERVALPERIOD");
				attr.setValue("30");
				D4.setAttributeNode(attr);
				
				
				ArrayList<String> days = helper.getDays(amrLoadEntityList2);
				int i=1;
				for (String day : days) //This array contains past 7 days data but we are showing only 5 days
				{// DATE ARRAY

					if(i==1)//TO skip  last 5th day except last interval
					{
						i++;
						continue;
					}
					i++;
					int periodCount = 1;
					Element el = doc.createElement("DAYPROFILE");
					
					Attr attrTest = doc.createAttribute("DATE");
					attrTest.setValue(getFormatedDate1(day));
					el.setAttributeNode(attrTest);
		        	
					//System.out.println("VALUE OF I FOR DATE ARRAY::"+i);
					if(i!=8)//To skip the current date entries except first interval
					{
						for (AmrLoadEntity amrLoadEntity1 : amrLoadEntityList2)// amrLoadEntityList2 having single meter 4 days data(each day 4*48=270) entries
						{
							String dateTime =String.valueOf( amrLoadEntity1.getMyKey().getReadTime());
							// System.out.println(" IMEI::"+amrLoadEntity1.getImei()+"   getReadTime::"+amrLoadEntity1.getMyKey().getReadTime()+"  meterNo::"+amrLoadEntity1.getMyKey().getMeterNumber());
							String innDate=dateTime.split(" ")[0].trim();
							SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd");
							Date newInnDate= formatInput.parse(innDate);
							String formatedDate=formatOutput .format(newInnDate);

							if(day.equals(formatedDate))
							{
								el.appendChild(createD4Ip(String.valueOf(periodCount++), amrLoadEntity1));

							}

						}
					}
					else
					{
						break;
					}
					
					D4.appendChild(el);
					
				}
				

				 elementMain.appendChild(D4);
					

	             }
		  
		  
		  private Element createD4Ip(String number,AmrLoadEntity loadObject)
		  {
			  
		        Element el = doc.createElement("IP");
		        
		        try {
		        	 Attr attrG22 = doc.createAttribute("INTERVAL");
		            attrG22.setValue(number);
		            el.setAttributeNode(attrG22);
		     
		            if(loadObject.getKvah()!=null)el.appendChild(createD4Parameter("P7-3-5-2-0",String.valueOf(loadObject.getKvah()) , "k"));  //TO-DO
					if(loadObject.getKwh()!=null)el.appendChild(createD4Parameter("P7-1-5-2-0",String.valueOf(loadObject.getKwh()) , "k")); 
					
					if(loadObject.getKvarhLag()!=null)el.appendChild(createD4Parameter("P7-2-1-2-0",String.valueOf(loadObject.getKvarhLag()) , "k"));
					if(loadObject.getKvarhLead()!=null)el.appendChild(createD4Parameter("P7-2-4-2-0",String.valueOf(loadObject.getKvarhLead()) , "k"));
					
		            if(loadObject.getvR()!=null)el.appendChild(createD4Parameter("P1-2-1-4-0" ,String.valueOf(loadObject.getvR()), "V"));
		            if(loadObject.getvY()!=null)el.appendChild(createD4Parameter("P1-2-2-4-0", String.valueOf(loadObject.getvY()), "V"));
		            if(loadObject.getvB()!=null)el.appendChild(createD4Parameter("P1-2-3-4-0", String.valueOf(loadObject.getvB()), "V"));
		          /*  el.appendChild(createD4Parameter(helper.LOAD_PARAM_VRYB, "", "k")); // KVAH
*/		        	if(loadObject.getiR()!=null)el.appendChild(createD4Parameter("P2-1-1-4-0", String.valueOf(loadObject.getiR()), "A"));
		            if(loadObject.getiY()!=null)el.appendChild(createD4Parameter("P2-1-2-4-0", String.valueOf(loadObject.getiY()), "A")); 
		            if(loadObject.getiB()!=null)el.appendChild(createD4Parameter("P2-1-3-4-0", String.valueOf(loadObject.getiB()), "A"));
		          //  el.appendChild(createD4Parameter(helper.LOAD_PARAM_IRYB,"", ""));
		            
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

		  private void D5(List<AmrEventsEntity> amrEventsList)
		  {
			  
			
			  Element D5 = doc.createElement("D5"); 
			  if(amrEventsList.size()<=0)
			  {
				  //System.err.println("NO EVENT HISTORY FOUND ");
			  }
			  
			  for(AmrEventsEntity amrEvents:amrEventsList) 
			  {
				  D5.appendChild(d5Tag(amrEvents));  
				 
			  }
			  
			  elementMain.appendChild(D5);
		  }


		private Element d5Tag(AmrEventsEntity amrEvents) {
			Element event = doc.createElement("EVENT"); 
			
	    
			 try{
				
				String[] codeAndStatus=helper.getCdfCodeAndStatus(amrEvents.getMyKey().getEventCode());
				/*String codeValue=codeAndStatus[0];
				String statusValue=codeAndStatus[1];*/
				String codeValue=amrEvents.getMyKey().getEventCode();
				String statusValue=codeAndStatus[1];
				
	        Attr cod = doc.createAttribute("CODE"); 
	        cod.setValue(codeValue);
	        event.setAttributeNode(cod);

	        Attr attrG22 = doc.createAttribute("STATUS");
	        attrG22.setValue(statusValue);
	        event.setAttributeNode(attrG22);

	     
	        
	        Attr attrG22Name = doc.createAttribute("TIME");
	        attrG22Name.setValue(getFormatedDateEvent(amrEvents.getMyKey().getEventTime().toString()));
	        event.setAttributeNode(attrG22Name);
	        
	        
	    /*    <SNAPSHOT UNIT="V" VALUE="0" PARAMCODE="P1-2-1-1-0"/>
	        <SNAPSHOT UNIT="V" VALUE="6000" PARAMCODE="P1-2-2-1-0"/>
	        <SNAPSHOT UNIT="V" VALUE="6000" PARAMCODE="P1-2-3-1-0"/>
	        <SNAPSHOT UNIT="A" VALUE="0" PARAMCODE="P2-1-1-1-0"/>
	        <SNAPSHOT UNIT="A" VALUE="0" PARAMCODE="P2-1-2-1-0"/>
	        <SNAPSHOT UNIT="A" VALUE="0" PARAMCODE="P2-1-3-1-0"/>
	        <SNAPSHOT UNIT="NA" VALUE="0" PARAMCODE="P4-1-1-0-0"/>
	        <SNAPSHOT UNIT="NA" VALUE="0" PARAMCODE="P4-2-1-0-0"/>
	        <SNAPSHOT UNIT="NA" VALUE="0" PARAMCODE="P4-3-1-0-0"/>
	        <SNAPSHOT UNIT="k" VALUE="66" PARAMCODE="P7-1-5-2-0"/>*/

	        
	        if(amrEvents.getvR()!=null)event.appendChild(createD5SnapShot("P1-2-1-1-0", String.valueOf(amrEvents.getvR()), "V")); 
	        if(amrEvents.getvY()!=null)event.appendChild(createD5SnapShot("P1-2-2-1-0", String.valueOf(amrEvents.getvY()), "V")); 
	        if(amrEvents.getvB()!=null)event.appendChild(createD5SnapShot("P1-2-3-1-0", String.valueOf(amrEvents.getvB()), "V")); 
	        if(amrEvents.getiR()!=null)event.appendChild(createD5SnapShot("P2-1-1-1-0", String.valueOf(amrEvents.getiR()), "A")); 
	        if(amrEvents.getiY()!=null)event.appendChild(createD5SnapShot("P2-1-2-1-0", String.valueOf(amrEvents.getiY()), "A")); 
	        if(amrEvents.getiB()!=null)event.appendChild(createD5SnapShot("P2-1-3-1-0", String.valueOf(amrEvents.getiB()), "A")); 
	        if(amrEvents.getPfR()!=null)event.appendChild(createD5SnapShot("P4-1-1-0-0", String.valueOf(amrEvents.getPfR()), "NA")); 
	        if(amrEvents.getPfY()!=null)event.appendChild(createD5SnapShot("P4-2-1-0-0", String.valueOf(amrEvents.getPfY()), "NA")); 
	        if(amrEvents.getPfB()!=null)event.appendChild(createD5SnapShot("P4-3-1-0-0", String.valueOf(amrEvents.getPfB()), "NA")); 
	        if(amrEvents.getKwh()!=null)event.appendChild(createD5SnapShot("P7-1-5-2-0", String.valueOf(amrEvents.getKwh()), "k")); 
	        
	        
	        
	     /*   event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAHI, amrEvents.getKwhImp().toString(), "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAHE, amrEvents.getKwhExp().toString(), "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVARHI, "", "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVARHE, "", "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAHI, "", "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAHE, "", "")); 
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KWI, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KWE, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVARI, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVARE, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAI, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_KVAE, "", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_VR,  amrEvents.getvR().toString(), ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_VY,  amrEvents.getvY().toString(), ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_VB,  amrEvents.getvB().toString(), ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_VRYB,"", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_IR,  amrEvents.getiR().toString(), ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_IB,  amrEvents.getiB().toString(), ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_IRYB,"", ""));
	        event.appendChild(createD5SnapShot(helper.EVENT_PARAM_PF,"", ""));*/
	        
	        
	        
			}
	        
	     catch (Exception e) {
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
		
		String getFormatedDate(String value) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			try {
				return dateFormat.format(format.parse(value)); 
			} catch (Exception e) {
			}
			return value;
		}

		String getFormatedDate1(String value) throws Exception {
			SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatOutput = new SimpleDateFormat("dd-MM-yyyy");
			Date newDate= formatInput.parse(value);
			String formatedDate=formatOutput .format(newDate);
			//System.out.println("formatedDate==="+formatedDate);
			return formatedDate;
		}
		String getFormatedDateEvent(String value) throws Exception {
			SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat formatOutput = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date newDate= formatInput.parse(value);
			String formatedDate=formatOutput .format(newDate);
		//	System.out.println("formatedDate==="+formatedDate);
			return formatedDate;
		}
		
		
	}
		