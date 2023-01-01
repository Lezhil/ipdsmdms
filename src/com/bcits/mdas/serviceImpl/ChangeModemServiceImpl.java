package com.bcits.mdas.serviceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bcits.entity.D9Data;
import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.ChangeModemDetailsEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.entity.AmrEventsEntity.KeyEvent;
import com.bcits.mdas.entity.AmrInstantaneousEntity.KeyInstantaneous;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.entity.ModemCommunication.KeyCommunication;
import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.service.AmiInstantaneousService;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.ChangeModemService;
import com.bcits.mdas.service.CmriUploadStatusService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.NamePlateService;
import com.bcits.mdas.utility.ParamCodeValidator;
import com.bcits.service.D9DataService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ChangeModemServiceImpl extends GenericServiceImpl<ChangeModemDetailsEntity> implements ChangeModemService {

	String meterNumber = "";
	String imeiNo = "";
	Timestamp G2 = null, G3 = null, G4 = null;
	String mtrMake = "";
	String mfCode = "";
	String intervalperiod="";
	// adding name_plate clumns
	Double ptratio=null;
	Double ctratio=null;
	String current_Rating=null;
	String meter_type=null;
	String firmware_version=null;
	
	@Autowired
	private AmrBillsService amrBillsService;
	
	@Autowired
	private AmiInstantaneousService instantaneousService;

	@Autowired
	private AmrEventsService eventsService;
	
	@Autowired
	private AmrLoadService loadService;
	
	@Autowired
	private ModemCommunicationService communicationService;
	
	@Autowired
	private CmriUploadStatusService cmriuploadservice;
	
	@Autowired
	private D9DataService d9DataService;
	
	@Autowired
	private NamePlateService namePlateService;
	
	@Override
	@Transactional(propagation=Propagation.NEVER)
	public HashMap<String,Object> parseTheFile(Document doc,ModelMap modelmap) throws ParseException {
		
		//FOR THE STATUS INTO THE UPLOAD STATUS TABLE
		HashMap<String,Object> tabUpdatedMap=new HashMap<>();
		HashMap<String,Object> uploadSummary=new HashMap<>();
		
		NodeList cdfList = doc.getElementsByTagName("CDF");
		for (int cdfCount = 0; cdfCount < cdfList.getLength(); cdfCount++) {
			Node tempNodeFor = cdfList.item(cdfCount);// CDF Node
			if (tempNodeFor.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNodeFor.hasChildNodes()) {
					NodeList subcdfList = tempNodeFor.getChildNodes();
					ModemCommunication modemCommunication=new ModemCommunication();
					for (int subCdfCount = 0; subCdfCount < subcdfList.getLength(); subCdfCount++) {
						Node utility = subcdfList.item(subCdfCount);// Utility Node
						//System.out.println(utility.getNodeName());
						
						if (utility.getNodeType() == Node.ELEMENT_NODE) {
							
							if (utility.hasChildNodes()) {
								NodeList utilitySubList = utility.getChildNodes();
								
								for (int utilitySubListCount = 0; utilitySubListCount < utilitySubList
										.getLength(); utilitySubListCount++) {
									Node utilitySubNode = utilitySubList.item(utilitySubListCount);
									if (utilitySubNode.getNodeType() == Node.ELEMENT_NODE) {
										
										String utilitySubNodeName = utilitySubNode.getNodeName();
										if ("D1".equalsIgnoreCase(utilitySubNodeName)) {

											if (utilitySubNode.hasChildNodes()) {
												NodeList D1List = utilitySubNode.getChildNodes();
												//List<String> list = parseD1(D1List);
												Map<String, String> map=parseD1(D1List);
												//meterNumber = list.get(0);
												meterNumber = map.get("G1");
												try {
													/*G2 = new Timestamp(
															(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(list.get(1)))
																	.getTime());*/
													if(map.get("G2")!=null && !map.get("G2").equals(""));
													{
														
														G2 = new Timestamp(
																(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(map.get("G2")))
																.getTime());
													}
												}catch (Exception e) {
													e.printStackTrace();
												}
												
												try {
													/*G3 = new Timestamp(
															(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(list.get(2)))
																	.getTime());*/
													if(map.get("G3")!=null && !map.get("G3").equals(""))
													{
														G3 = new Timestamp(
																(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(map.get("G3")))
																		.getTime());
													}
													
												}catch (Exception e) {
													e.printStackTrace();
												}
												
												try {
													/*G4 = new Timestamp(
															(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(list.get(3)))
																	.getTime());*/
													if(map.get("G4")!=null && !map.get("G4").equals(""))
													{
														G4 = new Timestamp(
																(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(map.get("G4")))
																.getTime());
													}
												}catch (Exception e) {
													e.printStackTrace();
													G4=G3;
												}
												
												/*mtrMake = list.get(5);
												mfCode = list.get(4);*/
												mtrMake = map.get("NAME");
												mfCode = map.get("CODE");
												try {
														if(map.get("G7")!=""&& map.get("G7")!=null);
														{
															String temp=map.get("G7");
															if(temp!=""&& temp!=null){
															ptratio=Double.parseDouble(temp);
															}
														}
														if(map.get("G8")!=""&& map.get("G8")!=null);
														{
															String temp=map.get("G8");
															ctratio=Double.parseDouble(temp);
														}
												
												} catch (Exception e) {
													e.printStackTrace();
												}
												current_Rating=map.get("G13");
												meter_type=map.get("G14");
												firmware_version=map.get("G17");
											}
											
											
											//System.out.println(meterNumber + "===" + mtrMake + "===" + G2 + "===" + G3
													//+ "===" + G4);
											//CHECKING  FOR THE DUPLICATES FROM
											Date d1=new Date();
											DateFormat dateFormat1=new SimpleDateFormat("yyyyMM");
											String presentMonth = dateFormat1.format(Calendar.getInstance().getTime());
							
											NamePlate namePlateData=new NamePlate();
											namePlateData.setMeter_serial_number(meterNumber);
											namePlateData.setPt_ratio(ptratio);
											namePlateData.setCt_ratio(ctratio);
											namePlateData.setCurrent_rating(current_Rating);
											namePlateData.setMeter_type(meter_type);
											namePlateData.setFirmware_version(firmware_version);
											namePlateData.setFlag("CMRI");
											namePlateData.setManufacturer_name(mtrMake);
											namePlateData.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
											
											/*try {
												namePlateService.customupdateBySchema(namePlateData,"postgresMdas");
											} catch (Exception e) {
												e.printStackTrace();
											}*/
											
											try
											{
												List<?> mtrExstlist=cmriuploadservice.getMeterExistInMDAS(meterNumber);
												List<?> list=cmriuploadservice.getcmriUploadData(meterNumber, presentMonth);
												
												if(!(mtrExstlist.size()>0))
												{
													uploadSummary.put("meterno", meterNumber);
													uploadSummary.put("status", "meterDoesNotExist");
													
													/*tabUpdatedMap.put("meterDoesNotExist",0);
													tabUpdatedMap.put("modemCommunicationUpdation",0);
													tabUpdatedMap.put("eventsUpdation",0);
													tabUpdatedMap.put("LoadSurveyUpdation",0);
													tabUpdatedMap.put("billdataUpdation",0);
													
													tabUpdatedMap.put("instanteneousUpdation",0);
													tabUpdatedMap.put("parsed", 0);
													tabUpdatedMap.put("duplicate",1);
													tabUpdatedMap.put("meternumber",meterNumber);
													tabUpdatedMap.put("filedate",G3);
													tabUpdatedMap.put("uploadStatus","file already Parsed");
													imeiNo=null;
													return tabUpdatedMap;*/
													return uploadSummary;
												}
												else if(list.size()>0){
													uploadSummary.put("meterno", meterNumber);
													uploadSummary.put("status", "Duplicate");
													return uploadSummary;
												}
											}catch(Exception e)
											{
												
												uploadSummary.put("meterno", meterNumber);
												uploadSummary.put("status", e.getMessage());
												/*tabUpdatedMap.put("meterDoesNotExist",0);
												tabUpdatedMap.put("modemCommunicationUpdation",0);
												tabUpdatedMap.put("eventsUpdation",0);
												tabUpdatedMap.put("LoadSurveyUpdation",0);
												tabUpdatedMap.put("billdataUpdation",0);
												tabUpdatedMap.put("instanteneousUpdation",0);
												tabUpdatedMap.put("parsed", 0);
												tabUpdatedMap.put("duplicate",1);
												tabUpdatedMap.put("meternumber",meterNumber);
												tabUpdatedMap.put("filedate",G3);
												tabUpdatedMap.put("uploadStatus","file already Parsed");
												imeiNo=null;
												tabUpdatedMap.put("failReason",e.getMessage());
												return tabUpdatedMap;*/
											}
								
											KeyCommunication keyCommunication=null;
											if(meterNumber!=null && meterNumber!="" && G2!=null)
											{
												 keyCommunication=new KeyCommunication(meterNumber,G2);
												 modemCommunication.setRead_from("CMRI");
												 modemCommunication.setMyKey(keyCommunication);
												 modemCommunication.setLastCommunication(new Timestamp(G2.getTime()));
											}
											try {
												namePlateService.customupdateBySchema(namePlateData,"postgresMdas");
											} catch (Exception e) {
												e.printStackTrace();
											}
											
										} 
										// END OF D1 IF CONDITION
										else if ("D2".equalsIgnoreCase(utilitySubNodeName)) {

											if (utilitySubNode.hasChildNodes()) {
												NodeList D2List = utilitySubNode.getChildNodes();
												AMIInstantaneousEntity amrInstantaneousEntity=parseD2(D2List);
												ObjectMapper objectMapper = new ObjectMapper(); 
												try {
													String obj = objectMapper.writeValueAsString(amrInstantaneousEntity);
													//System.out.println("Instantaneous ENTITY : -- "+ obj);
												} catch (Exception e) {
													e.printStackTrace();
												} 
												try {
													
													//instantaneousService.customupdateBySchema(amrInstantaneousEntity,"postgresMdas");
													/*instantaneousService.customupdatemdas(amrInstantaneousEntity);*/
													tabUpdatedMap.put("instanteneousUpdation",1);
												}catch (Exception e) {
													e.printStackTrace();
													tabUpdatedMap.put("instanteneousUpdation",0);
												}
												modemCommunication.setLastSyncInst(amrInstantaneousEntity.getTimeStamp());
											}

										}  
										// END OF D2 IF CONDITION
										else if ("D33".equalsIgnoreCase(utilitySubNodeName)) {

											if (utilitySubNode.hasChildNodes()) {
												NodeList D3List = utilitySubNode.getChildNodes();
												
												List<AmrBillsEntity> list =parseD3(D3List);
												for (Iterator iterator = list.iterator(); iterator.hasNext();) {
													AmrBillsEntity amrBillsEntity = (AmrBillsEntity) iterator.next();
													try {
														
														ObjectMapper om=new ObjectMapper();
														//System.out.println("Billing Data-->"+om.writeValueAsString(amrBillsEntity));
														
														amrBillsService.customupdateBySchema(amrBillsEntity,"postgresMdas");
														//amrBillsService.customupdatemdas(amrBillsEntity);
														tabUpdatedMap.put("billdataUpdation",1);
													}catch (Exception e) {
														e.printStackTrace();
														tabUpdatedMap.put("billdataUpdation",0);
													}
													modemCommunication.setLastSyncBill(amrBillsEntity.getTimeStamp());
												}
											}

										} else if ("D44".equalsIgnoreCase(utilitySubNodeName)) {
											System.out.println("Enter into D4 tag");
											if (utilitySubNode.hasAttributes()) {
												NamedNodeMap nodeMap = utilitySubNode.getAttributes();
												String attr = "", value = "";
												for (int i = 0; i < nodeMap.getLength(); i++) {
													Node node = nodeMap.item(i);
													if (node.getNodeName().equalsIgnoreCase("INTERVALPERIOD")) {
														intervalperiod=node.getNodeValue();
													}
												}
											}
											if (utilitySubNode.hasChildNodes()) {
												NodeList D4List = utilitySubNode.getChildNodes();
												List<AmrLoadEntity> list=parseD4(D4List);
												
												for (Iterator iterator = list.iterator(); iterator.hasNext();) {
													AmrLoadEntity amrLoadEntity = (AmrLoadEntity) iterator.next();
													
													ObjectMapper objectMapper = new ObjectMapper(); 
													try {
														String obj = objectMapper.writeValueAsString(amrLoadEntity);
														//System.out.println("LOAD ENTITY : -- "+ obj);
													} catch (Exception e) {
														e.printStackTrace();
													}
													try {
														loadService.customupdateBySchema(amrLoadEntity,"postgresMdas");
														//loadService.customupdatemdas(amrLoadEntity);
														tabUpdatedMap.put("LoadSurveyUpdation",1);
													}catch (Exception e) {
														e.printStackTrace();
														tabUpdatedMap.put("LoadSurveyUpdation",0);
													}
													modemCommunication.setLastSyncLoad(amrLoadEntity.getTimeStamp());
												}
											}
										}
										else if ("D55".equalsIgnoreCase(utilitySubNodeName)) {
											if (utilitySubNode.hasChildNodes()) {
												NodeList D5List = utilitySubNode.getChildNodes();
												List<AmrEventsEntity> list=parseD5(D5List);
												for (Iterator iterator = list.iterator(); iterator.hasNext();) {
													AmrEventsEntity amrEventsEntity = (AmrEventsEntity) iterator.next();
													
													try {
														ObjectMapper objectMapper=new ObjectMapper();
														String obj = objectMapper.writeValueAsString(amrEventsEntity);
														//System.out.println("EVENT ENTITY : -- "+ obj);
														eventsService.customupdateBySchema(amrEventsEntity,"postgresMdas");
														//eventsService.customupdatemdas(amrEventsEntity);
														tabUpdatedMap.put("eventsUpdation",1);
													} catch (Exception e) {
														e.printStackTrace();
														tabUpdatedMap.put("eventsUpdation",0);
													}
													modemCommunication.setLastSyncEvent(amrEventsEntity.getTimeStamp());
												}
											}
										}
										else if ("D92".equalsIgnoreCase(utilitySubNodeName)) {

											if (utilitySubNode.hasChildNodes()) {
												NodeList D9List = utilitySubNode.getChildNodes();
												List<D9Data> d9data=parseD9(D9List);
												ObjectMapper objectMapper = new ObjectMapper(); 
												try {
													String obj = objectMapper.writeValueAsString(d9data);
													//System.out.println("Transaction D9 ENTITY : -- "+ obj);
												} catch (Exception e) {
													e.printStackTrace();
												} 
											/*	try {
													
													instantaneousService.customupdateBySchema(amrInstantaneousEntity,"postgresMdas");
													instantaneousService.customupdatemdas(d9data);
													tabUpdatedMap.put("instanteneousUpdation",1);
												}catch (Exception e) {
													e.printStackTrace();
													tabUpdatedMap.put("instanteneousUpdation",0);
												}
												modemCommunication.setLastSyncInst(d9data.getTimeStamp());*/
											}
										} 
									}
									
								}
								/*try {
									ObjectMapper objectMapper = new ObjectMapper();
									String obj = objectMapper.writeValueAsString(modemCommunication);
									System.out.println("Modem communication 1--: "+obj);
									communicationService.customupdateBySchema(modemCommunication,"postgresMdas");
									//communicationService.customupdatemdas(modemCommunication);
								}catch (Exception e) {
									e.printStackTrace();
									try{
										if(modemCommunication.getMyKey()!=null) {
											communicationService.customsaveBySchema(modemCommunication, "postgresMdas");
											//communicationService.customsavemdas(modemCommunication);
											tabUpdatedMap.put("modemCommunicationUpdation",1);
										}
									}catch (Exception e1) {
										tabUpdatedMap.put("modemCommunicationUpdation",0);
									}*/
								}
							
						}
						
					}
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						String obj = objectMapper.writeValueAsString(modemCommunication);
						communicationService.customupdateBySchema(modemCommunication,"postgresMdas");
					}catch (Exception e) {
						e.printStackTrace();
						try{
							if(modemCommunication.getMyKey()!=null) {
								communicationService.customsaveBySchema(modemCommunication, "postgresMdas");
								tabUpdatedMap.put("modemCommunicationUpdation",1);
							}
						}catch (Exception e1) {
							tabUpdatedMap.put("modemCommunicationUpdation",0);
						}
					}
				}
			}
		}
		
		uploadSummary.put("meterno", meterNumber);
		uploadSummary.put("g2date",G2);
		uploadSummary.put("g3date",G3);
		uploadSummary.put("status", "parsed");
		return uploadSummary;
		//modelmap.put("updationData",tabUpdatedMap);
		/*tabUpdatedMap.put("parsed", 1);
		tabUpdatedMap.put("meterDoesNotExist", 0);
		tabUpdatedMap.put("duplicate",0);
		tabUpdatedMap.put("meternumber",meterNumber);
		tabUpdatedMap.put("filedate",G3);
		tabUpdatedMap.put("uploadStatus","file parsed");
		tabUpdatedMap.put("failReason",null);
		//return "parsed";
		return tabUpdatedMap;*/
	}

	private Map<String, String> parseD1(NodeList D1List) {

		List<String> list = new ArrayList<>();
		Map<String, String> map=new HashMap<>();
		for (int D1ListCount = 0; D1ListCount < D1List.getLength(); D1ListCount++) {
			Node D1SubNode = D1List.item(D1ListCount);
			if (D1SubNode.getNodeType() == Node.ELEMENT_NODE) {

				String nodeName = D1SubNode.getNodeName();
				String nodeValue = D1SubNode.getTextContent();

				if (nodeName.equalsIgnoreCase("G1")) {
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
					// meterNumber=nodeValue;
				}

				else if (nodeName.equalsIgnoreCase("G2")) {
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if (nodeName.equalsIgnoreCase("G3")) {
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if (nodeName.equalsIgnoreCase("G4")) {
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}

				else if (nodeName.equalsIgnoreCase("G22")) {
					if (D1SubNode.hasAttributes()) {
						NamedNodeMap nodeMap = D1SubNode.getAttributes();
						// String manufacturerCode = "",manufacturerName = "";

						for (int i = 0; i < nodeMap.getLength(); i++) {
							String d1AttrId = "", value = "";
							Node node = nodeMap.item(i);
							// System.out.println(" Parameter attr name : " + node.getNodeName()+" attr
							// value : " + node.getNodeValue());

							if (node.getNodeName().equalsIgnoreCase("CODE")) {
								list.add(node.getNodeValue());
								map.put(node.getNodeName(), node.getNodeValue());
							} else if (node.getNodeName().equalsIgnoreCase("NAME")) {
								list.add((node.getNodeValue().split(" "))[0]);
								map.put(node.getNodeName(), node.getNodeValue());
							}

						}
					} // End has Attribute
				}
				//newly added
				else if(nodeName.equalsIgnoreCase("G7")){ //ptratio
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if(nodeName.equalsIgnoreCase("G8")){ //ctratio
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if(nodeName.equalsIgnoreCase("G13")){ //current Rating
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if(nodeName.equalsIgnoreCase("G14")){ //meter type
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}
				else if(nodeName.equalsIgnoreCase("G17")){ //firmware version
					list.add(nodeValue);
					map.put(nodeName, nodeValue);
				}

			}
		}

		return map;
	}
	
	
	public AMIInstantaneousEntity parseD2(NodeList D2List) {
		
		AMIInstantaneousEntity amrInstantaneousEntity=new AMIInstantaneousEntity();
		AMIKeyInstantaneous myKey=new AMIKeyInstantaneous();
		myKey.setMeterNumber(meterNumber);
		myKey.setRdate(G2);
		amrInstantaneousEntity.setImei(imeiNo);
		if(imeiNo==null||imeiNo=="")
		{
			
		}
		amrInstantaneousEntity.setReadTime(G3);
		amrInstantaneousEntity.setRead_from("CMRI");
		amrInstantaneousEntity.setMyKey(myKey);
		List<String> list=new ArrayList<>();
		for (int D2ListCount = 0; D2ListCount < D2List.getLength(); D2ListCount++) {
			Node D2SubNode = D2List.item(D2ListCount);
			if (D2SubNode.getNodeType() == Node.ELEMENT_NODE) {

				String nodeName = D2SubNode.getNodeName();
				String nodeValue = D2SubNode.getTextContent();
				//System.err.println(nodeName + "-----" + nodeValue);
				
				if (D2SubNode.hasAttributes()) {
					NamedNodeMap nodeMap = D2SubNode.getAttributes();
					String code = "",value = "", unit = "",PARAMCODE=null;
					for (int i = 0; i < nodeMap.getLength(); i++) {
						
						Node node = nodeMap.item(i);
						//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());

						if(node.getNodeName().equalsIgnoreCase("CODE"))
    						code = node.getNodeValue();
    		
    					else if(node.getNodeName().equalsIgnoreCase("VALUE")) 
    						value = node.getNodeValue();
    					else if(node.getNodeName().equalsIgnoreCase("UNIT")) 
    						unit = node.getNodeValue();
    					else if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
                             PARAMCODE=node.getNodeValue();
					}
					//System.out.println("CODE : "+code+" , VALUE : "+value+" , UNIT : "+unit);
						
					Double val=null;
					boolean number = value.matches("-?\\d+(\\.\\d+)?");
					if(number){
						 val=value==null?0:Double.parseDouble(value);
					}
					ParamCodeValidator validator=new ParamCodeValidator();
                     if(code!=null) {
                     String code1=validator.readD2tags(code);
                     if("i_r".equals(code1)) {
                    amrInstantaneousEntity.setiR(val);
                     }
                     else if ("i_y".equals(code1)) {
                    	 amrInstantaneousEntity.setiY(val);
                     }
                     else if ("i_b".equals(code1)) {
                    	 amrInstantaneousEntity.setiB(val);
                     }
                     else if("v_r".equals(code1)) {
                    	 amrInstantaneousEntity.setvR(val);
                     }
                     else if ("v_y".equals(code1)) {
                    	 amrInstantaneousEntity.setvY(val);
                     }
                     else if ("v_b".equals(code1)) {
                    	 amrInstantaneousEntity.setvB(val);
                     }
                     else if ("kvarh_lag".equals(code1)) {
                    	 amrInstantaneousEntity.setKvarhLag(val);
                     }
                     else if ("kvarh_lead".equals(code1)) {
                    	 amrInstantaneousEntity.setKvarhLead(val);
                     }
                     else if ("kwh".equals(code1)) {
                    	 amrInstantaneousEntity.setKwh(val);
                     }
                     else if ("kvah".equals(code1)) {
                    	 amrInstantaneousEntity.setKvah(val);
                     }
                     else if ("frequency".equals(code1)) {
                    	 amrInstantaneousEntity.setFrequency(val);
                     }
                     else if("r_pf".equals(code1))
                     {
                    	 amrInstantaneousEntity.setPfR(val);
                     }
                     else if("y_pf".equals(code1))
                     {
                    	 amrInstantaneousEntity.setPfY(val);
                     }
                     else if("b_pf".equals(code1))
                     {
                    	 amrInstantaneousEntity.setPfB(val);
                     }
                     else if("phase_sequence".equals(code1)){
                    	 amrInstantaneousEntity.setPhase_sequence(value);
                     }
                     else if("signed_active_power".equals(code1)){
                    	 amrInstantaneousEntity.setSignedActivePower(val);
                     }
                     else if("signed_reactive_power".equals(code1)){
                    	 amrInstantaneousEntity.setSignedReactivePower(val);
                     }
                     else if("kVA".equals(code1)){
                    	 amrInstantaneousEntity.setKva(val);
                     }
                    /* else if("rphase_kw".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("yphase_kw".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("bphase_kw".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("total_kw".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     
                     else if("rphase_kvar".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("yphase_kvar".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("bphase_kvar".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("total_kvar".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("total_kva".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("angle1".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("angle2".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }
                     else if("angle3".equals(code1))
                     {
                    	 amrInstantaneousEntity.set
                     }*/
    				amrInstantaneousEntity.setTimeStamp(new Timestamp(new Date().getTime()));
					
				}
				
			}
		

		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			String obj = objectMapper.writeValueAsString(amrInstantaneousEntity);
			//System.out.println(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
	
	}
		return amrInstantaneousEntity;
	}
	public List<AmrBillsEntity> parseD3(NodeList D3List) {
		List<AmrBillsEntity> list = new ArrayList<>();
		ParamCodeValidator validator=new ParamCodeValidator(mtrMake, mfCode);
		
		for (int d3subCount = 0; d3subCount < D3List.getLength(); d3subCount++) {
			AmrBillsEntity billsEntity = new AmrBillsEntity();
			billsEntity.setImei(imeiNo);
			if(imeiNo==null||imeiNo=="")
			{
			}
			billsEntity.setRead_from("CMRI");
			if(G4!=null)
			{
				billsEntity.setServerTime(new Timestamp(G4.getTime()));
			}
			if(G2!=null)
			{
				
				billsEntity.setTimeStamp(new Timestamp(new Date().getTime()));
			}
			Node d3subNode = D3List.item(d3subCount);
			if (d3subNode.getNodeType() == Node.ELEMENT_NODE) {

				//System.out.println("D3SubNode------  " + d3subNode.getNodeName());
				Date datetime = null;
				if (d3subNode.hasAttributes()) {
					NamedNodeMap nodeMap = d3subNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						String d1AttrId = "", value = "";
						Node node = nodeMap.item(i);
						if (node.getNodeName().equalsIgnoreCase("DATETIME")) {
							try {
								datetime = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(node.getNodeValue());
							} catch (Exception e) {
								e.printStackTrace();
								datetime=G3;
							} 
							//System.err.println(node.getNodeName() + "----" + datetime);
						}

					}
				}
				KeyBills bills=new KeyBills(meterNumber, new Timestamp(datetime.getTime()));
				billsEntity.setMyKey(bills);
				if (d3subNode.hasChildNodes()) {
					NodeList BNodeList = d3subNode.getChildNodes();
					for (int BnodeCount = 0; BnodeCount < BNodeList.getLength(); BnodeCount++) {
						Node BNode = BNodeList.item(BnodeCount);
						//System.out.println(BNode.getNodeName());
						String nodeBName = BNode.getNodeName();
						if ("B3".equalsIgnoreCase(nodeBName)) {
							String code = "",param="", value = "", unit = "";
							
							if (BNode.hasAttributes()) {
								NamedNodeMap BnodeMap = BNode.getAttributes();
								for (int t = 0; t < BnodeMap.getLength(); t++) {
									Node nodeB = BnodeMap.item(t);
									if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
										param = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
										value = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("UNIT"))
										unit = nodeB.getNodeValue();

								}
							}
							code=validator.getD3RecCode(param);
							
							// System.err.println(code+" --- "+unit+" ---- "+value);
							if ("P7-3-5-2-0".equalsIgnoreCase(code)) {
								try {
								
								if ("k".equalsIgnoreCase(unit)) {
									billsEntity.setKvah(Double.parseDouble(value));
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setKvah((Double.parseDouble(value)*1000));
								}
								
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							if ("P7-1-5-2-0".equalsIgnoreCase(code)) {
								try {
								
								if ("k".equalsIgnoreCase(unit)) {
									billsEntity.setKwh(Double.parseDouble(value));
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setKwh((Double.parseDouble(value))*1000);
								}
								
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							if ("P7-2-1-2-0".equalsIgnoreCase(code)) {
								try {
								
								if ("k".equalsIgnoreCase(unit)) {
									billsEntity.setKvarhLead(Double.parseDouble(value));
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setKvarhLead((Double.parseDouble(value))*1000);
								}
								
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							if ("P7-2-4-2-0".equalsIgnoreCase(code)) {
								try {
								if ("k".equalsIgnoreCase(unit)) {
									billsEntity.setKvarhLag(Double.parseDouble(value));
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setKvarhLag((Double.parseDouble(value))*1000);
								}
								
							} catch (Exception e) {
								// TODO: handle exception
							}
							}

						} else if ("B4".equalsIgnoreCase(nodeBName)) {

							String code = "",param="", value = "", unit = "", tod = "";

							if (BNode.hasAttributes()) {
								NamedNodeMap BnodeMap = BNode.getAttributes();
								for (int t = 0; t < BnodeMap.getLength(); t++) {
									Node nodeB = BnodeMap.item(t);
									if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
										param = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
										value = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("UNIT"))
										unit = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("TOD"))
										tod = nodeB.getNodeValue();
								}
							}
							code=validator.getD3RecCode(param);
	
								if ("P7-3-5-2-0".equalsIgnoreCase(code)) {
									try {
									
									if ("k".equalsIgnoreCase(unit)) {
										
									} else if("M".equalsIgnoreCase(unit)) {
										value=(Double.parseDouble(value)*1000)+"";
									}
									
									switch (tod) {
									case "1":
										billsEntity.setKvahTz1(Double.parseDouble(value));
										break;
									case "2":
										billsEntity.setKvahTz2(Double.parseDouble(value));
										break;
									case "3":
										billsEntity.setKvahTz3(Double.parseDouble(value));
										break;
									case "4":
										billsEntity.setKvahTz4(Double.parseDouble(value));
										break;
									case "5":
										billsEntity.setKvahTz5(Double.parseDouble(value));
										break;
									case "6":
										billsEntity.setKvahTz6(Double.parseDouble(value));
										break;
									case "7":
										billsEntity.setKvahTz7(Double.parseDouble(value));
										break;
									case "8":
										billsEntity.setKvahTz8(Double.parseDouble(value));
										break;
	
									default:
										break;
									}
									
									} catch (Exception e) {
										// TODO: handle exception
									}
										
									
								}
								else if ("P7-1-5-2-0".equalsIgnoreCase(code)) {
									
									if ("k".equalsIgnoreCase(unit)) {
										
									}
									else if("M".equalsIgnoreCase(unit)) {
										value=(Double.parseDouble(value)*1000)+"";
									}
									
									switch (tod) {
									case "1":
										billsEntity.setKwhTz1(Double.parseDouble(value));
										break;
									case "2":
										billsEntity.setKwhTz2(Double.parseDouble(value));
										break;
									case "3":
										billsEntity.setKwhTz3(Double.parseDouble(value));
										break;
									case "4":
										billsEntity.setKwhTz4(Double.parseDouble(value));
										break;
									case "5":
										billsEntity.setKwhTz5(Double.parseDouble(value));
										break;
									case "6":
										billsEntity.setKwhTz6(Double.parseDouble(value));
										break;
									case "7":
										billsEntity.setKwhTz7(Double.parseDouble(value));
										break;
									case "8":
										billsEntity.setKwhTz8(Double.parseDouble(value));
										break;
	
									default:
										break;
									}
								}
						}
						
						else if ("B5".equalsIgnoreCase(nodeBName)) {

							String code = "",param="", value = "", unit = "", occ = "";
							
							if (BNode.hasAttributes()) {
								NamedNodeMap BnodeMap = BNode.getAttributes();
								for (int t = 0; t < BnodeMap.getLength(); t++) {
									Node nodeB = BnodeMap.item(t);
									if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
										param = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
										value = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("UNIT"))
										unit = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("OCCDATE"))
										occ = nodeB.getNodeValue();
								}
								
							}
							code=validator.getD3RecCode(param);
							
							if ("P7-6-5-2-0".equalsIgnoreCase(code)) {
								if ("k".equalsIgnoreCase(unit)) {
									billsEntity.setKva(Double.parseDouble(value));
									billsEntity.setDateKva(occ);
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setKva((Double.parseDouble(value))*1000);
									billsEntity.setDateKva(occ);
								}
							} 
							
							if ("P7-4-5-2-0".equalsIgnoreCase(code)) {
								if ("k".equalsIgnoreCase(unit)) {
									try {
										billsEntity.setDemandKw(Double.parseDouble(value));
										billsEntity.setOccDateKw(occ);
									} catch (Exception e) {
										
									}
									
								} else if("M".equalsIgnoreCase(unit)) {
									billsEntity.setDemandKw((Double.parseDouble(value))*1000);
									billsEntity.setOccDateKw(occ);
								}
							}
							
						} //B5 END
						
						
						else if ("B6".equalsIgnoreCase(nodeBName)) {

							String code = "",param="", value = "", unit = "", occ = "", tod="";
							if (BNode.hasAttributes()) {
								NamedNodeMap BnodeMap = BNode.getAttributes();
								for (int t = 0; t < BnodeMap.getLength(); t++) {
									Node nodeB = BnodeMap.item(t);
									if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
										param = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
										value = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("UNIT"))
										unit = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("OCCDATE"))
										occ = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("TOD"))
										tod = nodeB.getNodeValue();
								}
							}
							code=validator.getD3RecCode(param);
							
							if ("P7-6-5-2-0".equalsIgnoreCase(code)) {
								
								if ("k".equalsIgnoreCase(unit)) {
									
								} else if("M".equalsIgnoreCase(unit)) {
									value=(Double.parseDouble(value)*1000)+"";
								}
								
								
								switch (tod) {
								case "1":
									billsEntity.setKvaTz1(Double.parseDouble(value));
									billsEntity.setDateKvaTz1(occ);
									break;
								case "2":
									billsEntity.setKvaTz2(Double.parseDouble(value));
									billsEntity.setDateKvaTz2(occ);
									break;
								case "3":
									billsEntity.setKvaTz3(Double.parseDouble(value));
									billsEntity.setDateKvaTz3(occ);
									break;
								case "4":
									billsEntity.setKvaTz4(Double.parseDouble(value));
									billsEntity.setDateKvaTz4(occ);
									break;
								case "5":
									billsEntity.setKvaTz5(Double.parseDouble(value));
									billsEntity.setDateKvaTz5(occ);
									break;
								case "6":
									billsEntity.setKvaTz6(Double.parseDouble(value));
									billsEntity.setDateKvaTz6(occ);
									break;
								case "7":
									billsEntity.setKvaTz7(Double.parseDouble(value));
									billsEntity.setDateKvaTz7(occ);
									break;
								case "8":
									billsEntity.setKvaTz8(Double.parseDouble(value));
									billsEntity.setDateKvaTz8(occ);
									break;

								default:
									break;
								}
										
									
							}
								
								if ("P7-4-5-2-0".equalsIgnoreCase(code)) {
									
									if ("k".equalsIgnoreCase(unit)) {
										
									}
									else if("M".equalsIgnoreCase(unit)) {
										value=(Double.parseDouble(value)*1000)+"";
									}
									switch (tod) {
									case "1":
										billsEntity.setKwTz1(Double.parseDouble(value));
										billsEntity.setDateKwTz1(occ);
										break;
									case "2":
										billsEntity.setKwTz2(Double.parseDouble(value));
										billsEntity.setDateKwTz2(occ);
										break;
									case "3":
										billsEntity.setKwTz3(Double.parseDouble(value));
										billsEntity.setDateKwTz3(occ);
										break;
									case "4":
										billsEntity.setKwTz4(Double.parseDouble(value));
										billsEntity.setDateKwTz4(occ);
										break;
									case "5":
										billsEntity.setKwTz5(Double.parseDouble(value));
										billsEntity.setDateKwTz5(occ);
										break;
									case "6":
										billsEntity.setKwTz6(Double.parseDouble(value));
										billsEntity.setDateKwTz6(occ);
										break;
									case "7":
										billsEntity.setKwTz7(Double.parseDouble(value));
										billsEntity.setDateKwTz7(occ);
										break;
									case "8":
										billsEntity.setKwTz8(Double.parseDouble(value));
										billsEntity.setDateKwTz8(occ);
										break;

									default:
										break;
									}
								}
							
						}// B6 END
						
						else if ("B9".equalsIgnoreCase(nodeBName)) {

							String code = "",param="", value = "";

							if (BNode.hasAttributes()) {
								NamedNodeMap BnodeMap = BNode.getAttributes();
								for (int t = 0; t < BnodeMap.getLength(); t++) {
									Node nodeB = BnodeMap.item(t);
									if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
										param = nodeB.getNodeValue();
									else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
										value = nodeB.getNodeValue();
									
								}
							}
							code=validator.getD3RecCode(param);
							
							if ("P4-4-4-1-0".equalsIgnoreCase(code)) {
								if(!value.isEmpty())
								{
									billsEntity.setSysPfBilling(Double.parseDouble(value));
								}else
								{
									billsEntity.setSysPfBilling(null);
								}
								
							}
							
						} // B9 END

					}

				}

			}
			
			ObjectMapper objectMapper = new ObjectMapper(); 
			try {
				String obj = objectMapper.writeValueAsString(billsEntity);
				//System.out.println(d3subNode.getNodeName()+" -- : -- "+ obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			list.add(billsEntity);
			
			
		}
		return list;
	}
	
	public List<AmrLoadEntity> parseD4(NodeList D4List) throws DOMException, ParseException {
		List<AmrLoadEntity> list=new ArrayList<>();
		
        for (int d4subCount = 0; d4subCount < D4List.getLength(); d4subCount++) {
            Node d4subNode = D4List.item(d4subCount);
            
            if (d4subNode.getNodeType() == Node.ELEMENT_NODE) {
                //System.out.println("\nD4  SubNode"+d4subCount+"===>"+d4subNode.getNodeName());
            	Calendar calendar =Calendar.getInstance();
            	
                if (d4subNode.hasAttributes()) {
                    NamedNodeMap nodeMap = d4subNode.getAttributes();
                    Date datetime=null;
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if (node.getNodeName().equalsIgnoreCase("DATE")) {
                            datetime = new SimpleDateFormat("dd-MM-yyyy").parse(node.getNodeValue());
                            //System.out.println("datetime======>"+datetime);
                    		calendar.setTime(datetime);
                        }
                    }
                }
                if (d4subNode.hasChildNodes()) {
                    NodeList IPNodeList = d4subNode.getChildNodes();
                    for (int IPnodeCount = 0; IPnodeCount < IPNodeList.getLength(); IPnodeCount++) {
                    	
                    	
                    	Node IPNode = IPNodeList.item(IPnodeCount);
                        String ipnode=IPNode.getNodeName();
                        if("IP".equalsIgnoreCase(ipnode)) {
                            if(IPNode.hasChildNodes()){
                                NodeList insideNodeList = IPNode.getChildNodes();
                                AmrLoadEntity loadEntity=new AmrLoadEntity();
                                Double val=null;
                                for(int i=0;i<insideNodeList.getLength();i++){
                                    Node inside = insideNodeList.item(i);
                                    String VALUE=null,UNIT=null,PARAMCODE=null;
                                    if(inside.hasAttributes()){
                                        NamedNodeMap inside2 = inside.getAttributes();
                                        for(int j=0;j<inside2.getLength();j++){
                                            Node inside3 = inside2.item(j);
                                            if(inside3.getNodeName().equalsIgnoreCase("VALUE"))
                                                VALUE=inside3.getNodeValue(); 
                                            if(inside3.getNodeName().equalsIgnoreCase("UNIT"))
                                                UNIT=inside3.getNodeValue(); 
                                            if(inside3.getNodeName().equalsIgnoreCase("PARAMCODE"))
                                                PARAMCODE=inside3.getNodeValue(); 
                                        }
                                        
                                        //System.out.println("VALUE="+VALUE+"  UNIT="+UNIT+"  PARAMCODE="+PARAMCODE);
                                    }
                                   
                                    //Double val=(VALUE==""?0:Double.parseDouble(VALUE));
                                    if(VALUE==null) {
                                 	   val=(VALUE==null?0:Double.parseDouble(VALUE));
                                    }else {
                                 	   val=(VALUE==""?0:Double.parseDouble(VALUE));
                                    }
                                    ParamCodeValidator validator=new ParamCodeValidator();
                                    try {
                                    if(PARAMCODE!=null) {
                                    
                                    String code=validator.readD4tags(PARAMCODE);
                                    if("i_r".equals(code)) {
                                    	loadEntity.setiR(val);
                                    }
                                    else if ("i_y".equals(code)) {
                                    	loadEntity.setiY(val);
                                    }
                                    else if ("i_b".equals(code)) {
                                    	loadEntity.setiB(val);
                                    }
                                    else if("v_r".equals(code)) {
                                    	loadEntity.setvR(val);
                                    }
                                    else if ("v_y".equals(code)) {
                                    	loadEntity.setvY(val);
                                    }
                                    else if ("v_b".equals(code)) {
                                    	loadEntity.setvB(val);
                                    }
                                    else if ("kvarh_lag".equals(code)) {
                                    	loadEntity.setKvarhLag(val);
                                    }
                                    else if ("kvarh_lead".equals(code)) {
                                    	loadEntity.setKvarhLead(val);
                                    }
                                    else if ("kwh".equals(code)) {
                                    	loadEntity.setKwh(val);
                                    }
                                    else if ("kvah".equals(code)) {
                                    	loadEntity.setKvah(val);
                                    }
                                    else if ("frequency".equals(code)) {
                                    	loadEntity.setFrequency(val);
                                    }
                                    
                                    }
                                	
									} catch (Exception e) {
										// TODO: handle exception
									}
                                }
                                loadEntity.setImei(imeiNo);
                                
                                loadEntity.setRead_from("CMRI");
                                loadEntity.setTimeStamp(new Timestamp(new Date().getTime()));
                                loadEntity.setKvarhQ1(0.0);
                                loadEntity.setKvarhQ2(0.0);
                                loadEntity.setKvarhQ3(0.0);
                                loadEntity.setKvarhQ4(0.0);
                                loadEntity.setNetKwh(0.0);
                                if(G4!=null)
                                {
                                	loadEntity.setModemTime(new Timestamp(G4.getTime()));
                                }
                                KeyLoad myKey=new KeyLoad();
                                myKey.setMeterNumber(meterNumber);
                                myKey.setReadTime(new Timestamp((calendar.getTime()).getTime())); /////
                                loadEntity.setMyKey(myKey);
                                loadEntity.setTransId("0000000000000");
                                loadEntity.setKwhImp(0.0);
                                loadEntity.setKwhExp(0.0);
                                loadEntity.setStructureSize(0);
                                list.add(loadEntity);
                            }
                        }
                        calendar.add(Calendar.MINUTE, 30);
                    }
                }
            }
        }
        return list;
    }
	
	public List<AmrEventsEntity> parseD5(NodeList d5List) {
		List<AmrEventsEntity> list=new ArrayList<>();
		
		for (int D5ListCount = 0; D5ListCount < d5List.getLength(); D5ListCount++) {
			Node D5SubNode = d5List.item(D5ListCount);
			if (D5SubNode.getNodeType() == Node.ELEMENT_NODE) {

				String nodeName = D5SubNode.getNodeName();
				String nodeValue = D5SubNode.getTextContent();
				//System.err.println(nodeName + "-----" + nodeValue);
				AmrEventsEntity eventsEntity=new AmrEventsEntity();
				KeyEvent mykey=new KeyEvent();
				if (D5SubNode.hasAttributes()) {
					NamedNodeMap nodeMap = D5SubNode.getAttributes();
					String code = "",time = "", status = "";
					
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());

						if(node.getNodeName().equalsIgnoreCase("CODE"))
    						code = node.getNodeValue();
    		
    					else if(node.getNodeName().equalsIgnoreCase("STATUS")) 
    						status = node.getNodeValue();
    					else if(node.getNodeName().equalsIgnoreCase("TIME")) 
    						time = node.getNodeValue();
					}
					Date event_time;
					try {
						event_time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(time);
						mykey.setEventTime(new Timestamp(event_time.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
						event_time=G3;
					}
					mykey.setEventCode(code);
					mykey.setMeterNumber(meterNumber);
					
					eventsEntity.setMyKey(mykey);
					eventsEntity.setTimeStamp(new Timestamp(new Date().getTime()));
					eventsEntity.setRead_from("CMRI");
				    if(imeiNo==null||imeiNo=="")
        			{
				    	//eventsEntity.setRead_from("CMRI");
        			}
					//System.out.println("CODE : "+code+" , TIME : "+time+" , STATUS : "+status);
					
					if (D5SubNode.hasChildNodes()) {
						NodeList D5SubNodeList = D5SubNode.getChildNodes();
						for (int d5SubListCount = 0; d5SubListCount < D5SubNodeList.getLength(); d5SubListCount++) {
							
							Node snapshot = D5SubNodeList.item(d5SubListCount);
							if("SNAPSHOT".equalsIgnoreCase(snapshot.getNodeName())) {
								String param="", value = "", unit = "";
								
								if (snapshot.hasAttributes()) {
									NamedNodeMap BnodeMap = snapshot.getAttributes();
									for (int t = 0; t < BnodeMap.getLength(); t++) {
										Node nodeB = BnodeMap.item(t);
										if (nodeB.getNodeName().equalsIgnoreCase("PARAMCODE"))
											param = nodeB.getNodeValue();
										else if (nodeB.getNodeName().equalsIgnoreCase("VALUE"))
											value = nodeB.getNodeValue();
										else if (nodeB.getNodeName().equalsIgnoreCase("UNIT"))
											unit = nodeB.getNodeValue();

									}
								}
								
								//System.err.println("PARAMCODE : "+param+" , VALUE : "+value+" , UNIT : "+unit);
								Double val=null;
								if(!value.isEmpty())
								{
									val=Double.parseDouble(value);
								}
                                ParamCodeValidator validator=new ParamCodeValidator();
                                if(param!=null) {
	                                String code1=validator.readD4tags(param);
	                                if("i_r".equals(code1)) {
	                                	eventsEntity.setiR(val);
	                                }
	                                else if ("i_y".equals(code1)) {
	                                	eventsEntity.setiY(val);
	                                }
	                                else if ("i_b".equals(code1)) {
	                                	eventsEntity.setiB(val);
	                                }
	                                else if("v_r".equals(code1)) {
	                                	eventsEntity.setvR(val);
	                                }
	                                else if ("v_y".equals(code1)) {
	                                	eventsEntity.setvY(val);
	                                }
	                                else if ("v_b".equals(code1)) {
	                                	eventsEntity.setvB(val);
	                                }
	                                else if("pf_r".equals(code1)) {
	                                	eventsEntity.setPfR(val);
	                                }
	                                else if ("pf_y".equals(code1)) {
	                                	eventsEntity.setPfY(val);
	                                }
	                                else if ("pf_b".equals(code1)) {
	                                	eventsEntity.setPfB(val);
	                                }
	                                else if ("kwh".equals(code1)) {
	                                	eventsEntity.setKwh(val);
	                                }
	                                else if ("kvah".equals(code1)) {
	                                	eventsEntity.setKvah(val);
	                                }
                                }
							}
						}
					}
				}
				list.add(eventsEntity);
			}
		}
		return list;
	}
	
	public List<D9Data> parseD9(NodeList d9List) throws ParseException {

	 String checkDatePattern = "";
	 NodeList subnodeListD9 = ((Node) d9List).getChildNodes();
	
		 for (int countD9 = 0; countD9 < subnodeListD9.getLength(); countD9++)
		 {
			 Node tempNodeD9 = subnodeListD9.item(countD9);
			 if(tempNodeD9.getNodeType()== Node.ELEMENT_NODE)
			 {
				 String nodeName = tempNodeD9.getNodeName();
				 if (tempNodeD9.hasAttributes()) 
	    			{
					 
					 	NamedNodeMap nodeMap = tempNodeD9.getAttributes();
	    	 			String transactionCode = "", dateTime = "",cdfId="";
	    	 			for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
	    	 			{
	    	 				Node node = nodeMap.item(nodeMapIndex);
	    	 				if(node.getNodeName().equalsIgnoreCase("CODE"))
	    						transactionCode = node.getNodeValue();
	    					else if(node.getNodeName().equalsIgnoreCase("DATETIME"))
	    						dateTime = node.getNodeValue();
	    	 				
	    	 			}
	    	 			
	    	 			String time1 = "";
	    	 			D9Data d9 = new D9Data();
	    				if(dateTime.length() > 2)
						{
							checkDatePattern = dateTime.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
								try {
									time1 =  time1 = sdf2.format(sdf2.parse(dateTime));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								d9.setTransactionDate(sdf2.parse(time1));
							}
							else if(checkDatePattern.startsWith("/"))
							{
								SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy");
								time1 = sdf4.format(sdf4.parse(dateTime));
							}
							else{
								time1= "";
							}
						}
						else
						{
							time1= "";
						}
	    				//d9.setCdfId(Integer.parseInt(cdfId));
	    				d9.setTransactionCode(transactionCode);
	    				//d9.setRead_from("CMRI");
	    				//d9DataService.customupdatemdas(d9);
	    				//
	    				System.out.println("trasaction table saved");
	    			}
			 }
			 
		 }//End ForLoop
		
	 System.out.println("Insert into D9 completed");
	return null; 

	}

	@Override
	public String getLatestMonthMM()
	{
		Date dt=new Date();
		String sql="SELECT max(rdngmonth) FROM meter_data.metermaster";
		String date=String.valueOf(getCustomEntityManager("postgresMdas").createNativeQuery(sql).getSingleResult());
	/*	try {
			dt=new SimpleDateFormat("yyyyMM").parse(String.valueOf(date));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}*/
		
		return date;
	}
	
}
