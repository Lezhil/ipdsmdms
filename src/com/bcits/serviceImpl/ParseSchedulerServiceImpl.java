package com.bcits.serviceImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bcits.entity.BatchStatusEntity;
import com.bcits.entity.CDFData;
import com.bcits.entity.D1_data;
import com.bcits.entity.D2Data;
import com.bcits.entity.D3Data;
import com.bcits.entity.D4CdfData;
import com.bcits.entity.D4Data;
import com.bcits.entity.D5Data;
import com.bcits.service.CdfDataService;
import com.bcits.service.D1DataService;
import com.bcits.service.D2DataService;
import com.bcits.service.D3DataService;
import com.bcits.service.D4DtataService;
import com.bcits.service.D4LoadDataService;
import com.bcits.service.D5DataService;
import com.bcits.service.D5SnashotService;
import com.bcits.service.D9DataService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.ParseSchedulerService;
import com.bcits.service.UserAccessTypeService;
import com.bcits.service.XmlImportService;

@Service
public class ParseSchedulerServiceImpl extends GenericServiceImpl<BatchStatusEntity> implements ParseSchedulerService{
	

	@Autowired
	private CdfDataService cdfDataService;
	
	@Autowired
	private D1DataService d1DataService;
	
	@Autowired
	private D2DataService d2DataService;
	
	@Autowired
	private D3DataService d3DataService;
	
	@Autowired
	private D4DtataService d4DtataService;
	
	@Autowired
	private D5DataService d5DataService;
	
	@Autowired
	private D5SnashotService d5SnashotService;
	
	@Autowired
	private D9DataService d9DataService;
	
	@Autowired
	private D4LoadDataService d4LoadDataService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private XmlImportService xmlImportService;
	
	@Autowired
	private UserAccessTypeService userAccessTypeService;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String parseTheMobileFile(Document doc,String billmonthParam,ModelMap model,String unZipFIlePath,String filename)
	{

	System.out.println("enter to parse method for mobile scheduler Impl ");
	
	
 String   meterNumber = "";
 String mainStatus = "";
 int cdfId ;
 String manufacturerCode = "",manufacturerName = "";
 
 int billmonth = Integer.parseInt(billmonthParam);
 Date d = new Date();
 
 String meterClass = "", meterType = "", kwhValue = "", kvahValue = "", kvaValue = "", kvaValueB6 = "", pfValue = "";
//D2 variables
 float rPhaseVal = 0, yPhaseVal = 0, bPhaseVal = 0;
 float rPhaseLineVal = 0, yPhaseLineVal = 0, bPhaseLineVal = 0;
 float rPhaseActiveVal = 0, yPhaseActiveVal = 0, bPhaseActiveVal = 0;
 float rPhasePFVal = 0, yPhasePFVal = 0, bPhasePFVal = 0;
 float avgPFVal = 0, activePowerVal = 0;
 String phaseSequence = "";

//D5 Snapshot variables
 float rPhaseVal1 = 0, yPhaseVal1 = 0, bPhaseVal1 = 0;
 float rPhaseLineVal1 = 0, yPhaseLineVal1 = 0, bPhaseLineVal1 = 0;
 float rPhaseActiveVal1 = 0, yPhaseActiveVal1 = 0, bPhaseActiveVal1 = 0;
 float rPhasePFVal1 = 0, yPhasePFVal1 = 0, bPhasePFVal1 = 0;
 float avgPFVal1 = 0, activePowerVal1 = 0;
 String phaseSequence1 = "";
 float d5_kwh = 0;
 float d2_kwh=0;
 
 String kwhValue2 = "", kvahValue2 = "", kvahValue3 = "", kvaValue2 = "", kvaValue3 = "";
 String d3_01_dateTime = "", d3_02_dateTime = "", d3_03_dateTime = "";
 
 //

 double maxKva = 0, minKva = 0, sumKwh = 0, sumPf = 0,sumKva = 0;
 double ctrn = 0, ctrd = 0, mf = 0, cd = 0;
 double cd_20 = 0, cd_40 = 0, cd_60 = 0;
 int pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0, dayProfileCount = 0, pfNoLoad=0, pfBlackOut = 0;
 

 int kva1_lt_cd20 = 0, kva1_lt_cd40 = 0, kva1_lt_cd60 = 0, kva1_gt_cd60 = 0;
 int kva2_lt_cd20 = 0, kva2_lt_cd40 = 0, kva2_lt_cd60 = 0, kva2_gt_cd60 = 0;
 int kva3_lt_cd20 = 0, kva3_lt_cd40 = 0, kva3_lt_cd60 = 0, kva3_gt_cd60 = 0;
 int kva4_lt_cd20 = 0, kva4_lt_cd40 = 0, kva4_lt_cd60 = 0, kva4_gt_cd60 = 0;
//for D3 dates
	
 String d3_01_month = billmonth+"";
 String d3_02_month = "";
 String d3_03_month = "";
 String d3_01_energy = "0", d3_02_energy = "0", d3_03_energy = "0";
 String d1DateForCheckin = "";
 
 //D4 Variables
 String intervalPeriod = "0";
 String d4KvaValue = "",d4KwhValue = "",d4PfValue = "";
 String d4vrValue = "",d4vyValue = "",d4vbValue = "",d4arValue= "",d4ayValue="",d4abValue="";
	
 //All SimplaDateFormats
 SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
 SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
 SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
 SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy");
 SimpleDateFormat yearCheck = new SimpleDateFormat("yyyy");
 
 

	try{
		boolean prevMonthFlag = false;
		 Calendar cal = Calendar.getInstance();
			
		 String yearSearch = cal.get(Calendar.YEAR)+"";
		 cal.setTime(sdfBillDate.parse(billmonth+""));
		 cal.add(Calendar.MONTH, -1);
		 d3_02_month = sdfBillDate.format(cal.getTime());
		 cal.add(Calendar.MONTH, -1);
		 d3_03_month = sdfBillDate.format(cal.getTime());
		 
		
		NodeList nodeListForMetrNo = doc.getElementsByTagName("CDF");
		System.out.println("nodeListForMetrNo=="+nodeListForMetrNo.getLength());
		for(int count = 0; count < nodeListForMetrNo.getLength();count++)
		{
			
			Node tempNodeForMetrNo = nodeListForMetrNo.item(count);//CDF Node
			if(tempNodeForMetrNo.getNodeType() == Node.ELEMENT_NODE)
			{
				if(tempNodeForMetrNo.hasChildNodes())
				{
					NodeList subnodeListForMetrNo = tempNodeForMetrNo.getChildNodes();
					for(int subCount = 0 ; subCount < subnodeListForMetrNo.getLength();subCount++)
					{
						//System.out.println("subnodeListForMetrNo=="+subnodeListForMetrNo.getLength());
						 Node subChildNode = subnodeListForMetrNo.item(subCount);//Utility Node
						 if(subChildNode.getNodeType() == Node.ELEMENT_NODE)
						 {
							 
							 if(subChildNode.hasChildNodes())
							 { 
								 NodeList dataNodeList = subChildNode.getChildNodes();
								 for(int dataSubCount = 0 ; dataSubCount < dataNodeList.getLength();dataSubCount++)
								 {
									 //System.out.println("dataNodeList=="+dataNodeList.getLength());
									 
									 Node d1DataNode = dataNodeList.item(dataSubCount);
									 if(d1DataNode.getNodeType() == Node.ELEMENT_NODE)
									 {
										 if(d1DataNode.hasChildNodes())
										 {
											 if(d1DataNode.getNodeName().equalsIgnoreCase("D1"))//Check the D1 Tag
											 {
												 NodeList d1SubChildNodeList = d1DataNode.getChildNodes();
												 
												// System.out.println("d1SubChildNodeList=="+d1SubChildNodeList.getLength());
												 
												 for(int subd1 = 0; subd1 < d1SubChildNodeList.getLength();subd1++)
												 {
													
													 
													Node d1ChildNode =  d1SubChildNodeList.item(subd1);
													if(d1ChildNode.getNodeType() == Node.ELEMENT_NODE)
													{
														String nodeNameForMetrNo = d1ChildNode.getNodeName();
														String nodeValueForMetrNo = d1ChildNode.getTextContent();
														if(nodeNameForMetrNo.equalsIgnoreCase("G1"))
														{
															 meterNumber = nodeValueForMetrNo;
																
															 System.out.println(" G1 : meter number "+meterNumber.trim());	
															 List<CDFData> countData = cdfDataService.findAll(meterNumber, billmonth);
															 System.out.println("cdf count data=="+countData.size());
															 if(countData.size() == 0)
															 {
																 List masterList = masterService.getMeterDataInformation(meterNumber, billmonth+"");
																System.out.println(masterList.size());
												
																 if(masterList.size() > 0)
																 {
																	 System.out.println("inside masterlist");
																	 
																	 String accno="";
																	 
																	 for(Iterator<?> iterator1=  masterList.iterator(); iterator1.hasNext();)
								    			    			 		{
																		 System.out.println("insss for");
																		 Object[] obj=(Object[]) iterator1.next();
																		  accno = (String)obj[0];
																		  if(obj[1]!=null )
																		  {
																			  
																			ctrn = (Double)obj[1];
																		  }
																		  if(obj[2]!=null )
																		  {
																			  
																			  ctrd = (Double)obj[2];
																		  }
																		  if(obj[3]!=null )
																		  {
																			  
																			  cd = (Double)obj[3];
																		  }
																		  if(obj[4]!=null )
																		  {
																			  
																			  mf = (Double)obj[4];
																		  }
																			
								    			    			 		}
																	 
																	 
																	 
																	/* Object[] obj = (Object[]) masterList.get(0);
																		String accno = (String)obj[0];
																		ctrn = (Double)obj[1];
																		ctrd = (Double)obj[2];
																		cd = (Double)obj[3];
																		mf = (Double)obj[4];*/
																		if(mf > 0)
																		{
																			cd_20 = ((((cd/mf)*20)/100)/2);
																			cd_40 = ((((cd/mf)*40)/100)/2);
																			cd_60 = ((((cd/mf)*60)/100)/2);
																			
																			
																		}
																
																int prevdata = cdfDataService.findPrevDataD4(meterNumber);
																if(prevdata > 0)
																{
																	 prevMonthFlag = true;
																}
																CDFData cdf = new CDFData();
																cdf.setMeterNo(meterNumber);cdf.setAccountNo(accno);cdf.setBillmonth(billmonth);
																cdf.setReadDate(d);cdf.setDbDate(d);
																cdfDataService.save(cdf);
																cdfId = cdfDataService.getRecentCdfId(meterNumber, billmonth);
																
																
																
																//System.out.println("-----------account Number : "+ obj[0]);
																
																NodeList nodeList = doc.getElementsByTagName("CDF");
																for(int count1 = 0; count1 < nodeList.getLength();count1++)
																{
																	Node tempNodeFor = nodeList.item(count1);//CDF Node
																	if(tempNodeFor.getNodeType() == Node.ELEMENT_NODE)
																	{
																		if(tempNodeFor.hasChildNodes())
																		{
																			NodeList subnodeList = tempNodeFor.getChildNodes();
																			for(int subCount1 = 0 ; subCount1 < subnodeListForMetrNo.getLength();subCount1++)
																			{
																				 Node subChildNodeForData = subnodeList.item(subCount1);//Utility Node
																				 if(subChildNodeForData.getNodeType() == Node.ELEMENT_NODE)
																				 {
																					 if(subChildNodeForData.hasChildNodes())
																					 { 
																						 NodeList dataNodeListFordb = subChildNodeForData.getChildNodes();
																						 for(int dataSubCount1 = 0 ; dataSubCount1 < dataNodeListFordb.getLength();dataSubCount1++)
																						 {
																							 Node d1DataNodeData = dataNodeListFordb.item(dataSubCount1);
																							 if(d1DataNodeData.getNodeType() == Node.ELEMENT_NODE)
																							 {
																								 if(d1DataNodeData.hasChildNodes())
																								 {
																									 if(d1DataNodeData.getNodeName().equalsIgnoreCase("D1"))//Check The D1 Tag
																									 {
																										 
																										 System.out.println("enter to d1 node");
																												
																										 String g2Date = "";
																										 NodeList d1SubChildNodeListdb = d1DataNodeData.getChildNodes();
																										 System.out.println("d1SubChildNodeListdb.getLength()===="+d1SubChildNodeListdb.getLength());
																										 for(int subd11 = 0; subd11 < d1SubChildNodeListdb.getLength();subd11++)
																										 {
																											 Node d1Maindata = d1SubChildNodeListdb.item(subd11);
																											 if(d1Maindata.getNodeType() == Node.ELEMENT_NODE)
																											 {
																												 String nodeName = d1Maindata.getNodeName();
																												 String nodeValue = d1Maindata.getTextContent();
																												 	if(nodeName.equalsIgnoreCase("G2"))
																													{
																												 		g2Date = nodeValue;
																														d1DateForCheckin = sdfBillDate.format(sdf2.parse(nodeValue));
																													}
																													if(nodeName.equalsIgnoreCase("G13"))
																													{
																														meterClass = nodeValue;
																													}
																													if(nodeName.equalsIgnoreCase("G15"))
																													{
																														meterType = nodeValue;
																													}
																													if(nodeName.equalsIgnoreCase("G22"))
																													{
																														if (d1Maindata.hasAttributes()) 
																														{
																															NamedNodeMap nodeMap = d1Maindata.getAttributes();
																															//String manufacturerCode = "",manufacturerName = "";
																															
																															for (int i = 0; i < nodeMap.getLength(); i++) 
																															{
																																String d1AttrId = "", value = "";
																																Node node = nodeMap.item(i);
																																//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());
																																
																																if(node.getNodeName().equalsIgnoreCase("CODE")) 
																																{
																																	manufacturerCode = node.getNodeValue();
																																}
																																else if(node.getNodeName().equalsIgnoreCase("NAME")) 
																																{
																																	manufacturerName = node.getNodeValue();
																																}															    						
																																																																				
																															}
																															
																														}//End has Attribute
																													}
																												 
																											 }
																										 }//End For loop
																										 
																										 D1_data d1 = new D1_data();
																										 d1.setCdfId(cdfId);d1.setManufacturerCode(manufacturerCode);
																										 d1.setManufacturerName(manufacturerName);
																										 d1.setMeterClass(meterClass);d1.setMeterType(meterType);
																										 d1.setMeterDate(g2Date);
																										 d1DataService.save(d1);
																										 System.out.println("Insert into D1 completed");
																									 }// Ends the D1 Tag
																									 
																									 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D2"))//Check The D2 Tag
																									 {
																										 System.out.println("enter in to d2");
																										NodeList d2SubChildNodeList = d1DataNodeData.getChildNodes();
																										System.out.println("d2SubChildNodeList.getLength()"+d2SubChildNodeList.getLength());
																										for(int d2sub = 0;d2sub<d2SubChildNodeList.getLength();d2sub++)
																										{
																											 Node d2tempNode123 = d2SubChildNodeList.item(d2sub);
																											 if (d2tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																											 {
																												 String nodeName = d2tempNode123.getNodeName();
																												 String nodeValue = d2tempNode123.getTextContent();
																												 
																												 if(d2tempNode123.hasAttributes())
																												 {
																													 NamedNodeMap nodeMap = d2tempNode123.getAttributes();
																									    				String code = "",value = "", unit = "";
																									    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																									    				{
																									    					Node node = nodeMap.item(nodeMapIndex);
																									    					//System.out.println("attr name : " + node.getNodeName());
																									    					//System.out.println("attr value : " + node.getNodeValue());
																									    					if(node.getNodeName().equalsIgnoreCase("CODE"))
																									    						code = node.getNodeValue();
																									    					else if(node.getNodeName().equalsIgnoreCase("VALUE")) 
																									    						value = node.getNodeValue();
																									    					else if(node.getNodeName().equalsIgnoreCase("UNIT")) 
																									    						unit = node.getNodeValue();													    																	    					
																									    				}
																									    				
																									    				if(value.equalsIgnoreCase(""))
																									    				{
																									    					value = "0";
																									    				}
																									    				if(code.equalsIgnoreCase("P1-2-1-1-0") && unit.equalsIgnoreCase("V"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					rPhaseVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P1-2-2-1-0") && unit.equalsIgnoreCase("V"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					yPhaseVal = temp;
																									    					
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P1-2-3-1-0") && unit.equalsIgnoreCase("V"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					bPhaseVal = temp;
																									    					
																									    					
																									    				}
																									    				
																									    				else if(code.equalsIgnoreCase("P2-1-1-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					
																									    					float temp = Float.parseFloat(value);
																									    					rPhaseLineVal = temp;
																									    					System.out.println("come to P2-1-1-1-0 value== "+rPhaseLineVal);
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P2-1-2-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					yPhaseLineVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P2-1-3-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					bPhaseLineVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P2-2-1-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					rPhaseActiveVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P2-2-2-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					yPhaseActiveVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P2-2-3-1-0") && unit.equalsIgnoreCase("A"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					bPhaseActiveVal = temp;
																									    					
																									    					
																									    				}
																									    				
																									    				else if(code.equalsIgnoreCase("P4-1-1-0-0"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					rPhasePFVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P4-2-1-0-0"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					yPhasePFVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P4-3-1-0-0"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					
																									    					bPhasePFVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P4-4-1-0-0"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					avgPFVal = temp;
																									    					
																									    				}
																									    				else if((code.equalsIgnoreCase("P3-1-4-1-0") || code.equalsIgnoreCase("P3-2-4-1-0")) && unit.equalsIgnoreCase("K"))
																									    				{
																									    					float temp = Float.parseFloat(value);
																									    					activePowerVal = temp;
																									    					
																									    				}
																									    				else if(code.equalsIgnoreCase("P8-1-0-0-0"))
																									    					
																									    				{
																									    					phaseSequence = value;
																									    				}
																									    				// reading D2_KWH value
																									    				
																									    			else  if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-18-2-0"))
																										    				{
																									    						float temp = Float.parseFloat(value);
																									    						d2_kwh=temp;
																									    						//System.out.println("==inside 1 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																										    				}
																									    					
																									    				
																									    				
																									    					else if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||
																									    							code.equalsIgnoreCase("P7-1-5-2-0") || code.equalsIgnoreCase("P7-1-13-2-0"))
																											    				{
																									    						float temp = Float.parseFloat(value);
																									    						d2_kwh=temp;
																									    						//System.out.println("==inside 2 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																											    				}
																									    				
																									    				
																									    					else if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																											    				{
																									    						//System.out.println("Inside P7-1-5-2-0 kwh value");
																																		
																									    						
																									    						float temp=Float.parseFloat(value);
																									    						d2_kwh=temp;
																									    						//System.out.println("==inside 3 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																											    				}
																									    				
																									    				
																									    				else if(code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-13-1-0") ||code.equalsIgnoreCase("P7-1-18-2-0"))
																											    				{
																									    						float temp = Float.parseFloat(value);
																									    						d2_kwh=temp;
																									    						//System.out.println("==inside 4 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																											    				}
																									    				
																									    					
																												 }
																											 }
																										}//End ForLoop
																										D2Data d2 = new D2Data();
																										d2.setCdfId(cdfId);d2.setbPhaseVal(bPhaseVal);d2.setrPhaseVal(rPhaseVal);d2.setyPhaseVal(yPhaseVal);
																										d2.setrPhaseLineVal(rPhaseLineVal);d2.setyPhaseLineVal(yPhaseLineVal);d2.setbPhaseLineVal(bPhaseLineVal);
																										d2.setrPhaseActiveVal(rPhaseActiveVal);d2.setyPhaseActiveVal(yPhaseActiveVal);d2.setbPhaseActiveVal(bPhaseActiveVal);
																										d2.setrPhasePfVal(rPhasePFVal);d2.setyPhasePfVal(yPhasePFVal);d2.setbPhasePfVal(bPhasePFVal);
																										d2.setAvgPfVal(avgPFVal);d2.setActivePowerVal(activePowerVal);d2.setPhaseSequence(phaseSequence);
																										d2.setMf(mf);
																										d2.setD2_kwh(d2_kwh);
																										d2DataService.save(d2);
																										System.out.println("Insert into D2 completed");
																									 }// Ends the D2 Tag
																									 
																									 //D3 Tag starts
																									 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D3"))//Check The D3 Tag
																									 {
																										 
																										 System.out.println("enter in to D3 node");
																										 
																										 String flag = "0";
																										 String flag1 = "0";
																										 String flag2 = "0";
																										 NodeList d3SubNodeList = d1DataNodeData.getChildNodes();
																										 String d3_01_temp = "", d3_02_temp = "", d3_03_temp = "";
																										 
																										 System.out.println("d3SubNodeList.getLength()=="+d3SubNodeList.getLength());
																										 for(int countD3 = 0; countD3 < d3SubNodeList.getLength();countD3++)
																										 {
																											 String d3TagCount = "", dateTime = "", mechanism = "",  tagAttrId = "";
																											 String d3Id = "", d3AttrId = "", attrValue = "";
																											 Node d3subNode = d3SubNodeList.item(countD3);
																											 if(d3subNode.getNodeType() == Node.ELEMENT_NODE)
																											 {
																												 
																												 d3TagCount = d3subNode.getNodeName();
																												 if(d3subNode.hasAttributes())
																												 {
																													 NamedNodeMap nodeMap = d3subNode.getAttributes();
																									    			
																									    	 			for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																									    				{
																									    					Node node = nodeMap.item(nodeMapIndex);
																									    					
																									    					if(node.getNodeName().equalsIgnoreCase("DATETIME"))
																									    						dateTime = node.getNodeValue();
																									    					else if(node.getNodeName().equalsIgnoreCase("MECHANISM"))
																									    						mechanism = node.getNodeValue();
																									    				}
																									    	 			if(d3TagCount.equalsIgnoreCase("D3-01"))
																								    					{
																									    					d3_01_dateTime = dateTime;
																									    					
																									    					System.out.println("Timeeeeeeee Length "+d3_01_dateTime.length());
																									    					if(d3_01_dateTime.length()==0)
																									    					{
																									    						d3_01_temp = "000000";
																									    						
																									    					}
																									    					else if(d3_01_dateTime.length() >= 14)
																									    					{
																									    					
																									    						try{
																									    								d3_01_temp =  sdfBillDate.format(sdf2.parse(d3_01_dateTime));
																									    						}
																									    						catch (Exception e) {
																																	// TODO: handle exception
																									    							if((sdfBillDate.format(sdf4.parse(d3_01_dateTime))).startsWith("0"))
																									    							{
																									    								
																									    								String append = sdfBillDate.format(sdf4.parse(d3_01_dateTime));
																									    								append = append.substring(2);
																									    								append = "20"+append;
																									    								System.out.println("append String is : "+append);
																									    								d3_01_temp = append;	
																									    							}
																									    							
																									    							else
																									    							{
																									    								d3_01_temp =  sdfBillDate.format(sdf4.parse(d3_01_dateTime));
																									    							}
																																}
																									    					}
																									    					
																									    					else
																									    					{
																									    						d3_01_temp =  sdfBillDate.format(sdf3.parse(d3_01_dateTime));
																									    					}
																									    					
																									    					
																								    					}
																									    	 			else if(d3TagCount.equalsIgnoreCase("D3-02"))
																								    					{
																									    					d3_02_dateTime = dateTime;
																									    					if(d3_02_dateTime.length()==0)
																									    					{
																									    						d3_02_temp = "000000";
																									    						
																									    					}
																									    					else if(d3_02_dateTime.length() >= 14)
																									    					{
																									    						
																									    						try{
																									    							
																									    							d3_02_temp =  sdfBillDate.format(sdf2.parse(d3_02_dateTime));
																									    						}
																									    						catch (Exception e) {
																																	// TODO: handle exception
																									    							if((sdfBillDate.format(sdf4.parse(d3_02_dateTime))).startsWith("0"))
																									    							{
																									    								
																									    								String append = sdfBillDate.format(sdf4.parse(d3_02_dateTime));
																									    								append = append.substring(2);
																									    								append = "20"+append;
																									    								System.out.println("append String is : "+append);
																									    								d3_02_temp = append;	
																									    							}
																									    							
																									    							else
																									    							{
																									    								d3_02_temp =  sdfBillDate.format(sdf4.parse(d3_02_dateTime));
																									    							}
																																}
																									    					
																									    					}
																									    					else
																									    					{
																									    						d3_02_temp =  sdfBillDate.format(sdf3.parse(d3_02_dateTime));
																									    					}
																									    					
																									    					
																								    					}
																									    	 			else if(d3TagCount.equalsIgnoreCase("D3-03"))
																								    					{
																									    					d3_03_dateTime = dateTime;
																									    					
																									    					if(d3_03_dateTime.length() == 0)
																									    					{
																									    						d3_03_temp = "000000";
																									    						
																									    					}
																									    					
																									    					else if(d3_03_dateTime.length() >= 14)
																									    					{
																									    				
																										    				try
																										    				{
																								    							
																								    							d3_03_temp =  sdfBillDate.format(sdf2.parse(d3_03_dateTime));
																								    						}
																								    						catch (Exception e) {
																																// TODO: handle exception
																								    							if((sdfBillDate.format(sdf4.parse(d3_03_dateTime))).startsWith("0"))
																								    							{
																								    								
																								    								String append = sdfBillDate.format(sdf4.parse(d3_03_dateTime));
																								    								append = append.substring(2);
																								    								append = "20"+append;
																								    								System.out.println("append String is : "+append);
																								    								d3_03_temp = append;
																								    							}
																								    							
																								    							else
																								    							{
																								    								d3_03_temp =  sdfBillDate.format(sdf4.parse(d3_03_dateTime));
																								    							}
																															}
																								    					
																								    					
																									    					}
																									    					else
																									    					{
																									    						d3_03_temp =  sdfBillDate.format(sdf3.parse(d3_03_dateTime));
																									    					}
																								    					}
																												 }//ends hasAttributes
																												 NodeList subTempNodeListD3 = d3subNode.getChildNodes();
																												 for(int subCountD3 = 0; subCountD3 < subTempNodeListD3.getLength(); subCountD3++)
																												 {
																													 Node subTempNode123 = subTempNodeListD3.item(subCountD3);
																													 if (subTempNode123.getNodeType() == Node.ELEMENT_NODE) 
																													 {
																														String subNodeName = subTempNode123.getNodeName();//B1, B2 ..
																														String subNodeValue = subTempNode123.getTextContent();
																														String code = "", value = "", unit = "", tod = "";
																														if(subTempNode123.hasAttributes()) 
																										    			{
																															NamedNodeMap nodeMap = subTempNode123.getAttributes();
																										    				String attributeId = "", attributeValue = "";
																										    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																										    				{
																										    					Node node = nodeMap.item(nodeMapIndex);
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) || (d3TagCount.equalsIgnoreCase("D3-02")) || (d3TagCount.equalsIgnoreCase("D3-03")))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																										    						}
																										    						
																										    					}
																										    				}
																										    				
																										    				  
																										    					//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																																	
																										    					//values for updating MeterMaster
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-18-1-0"))
																										    							{
																										    								kwhValue2 = value;
																										    								System.out.println("kwhValue2==="+kwhValue2);
																																					
																										    							}
																										    							
																										    							if(code.equalsIgnoreCase("P7-3-13-1-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-18-0-0"))
																										    							{
																										    								kvahValue2 = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-5-0-0")||code.equalsIgnoreCase("P7-3-5-1-0"))
																										    							{
																										    								kvahValue3 = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-18-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-6-18-0-0"))
																										    							{
																										    								kvaValue2 = value;
																										    							}
																																		if(code.equalsIgnoreCase("P7-4-18-0-0"))
																										    							{
																										    								kvaValue3 = value;
																										    							}
																																		if(code.equalsIgnoreCase("P7-6-5-1-0"))
																										    							{
																										    								kvaValue3 = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    						
																										    					}//end of values for updating MeterMaster
																										    					
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")))
																										    							{
																										    								d3_01_energy = value;
																										    								System.out.println("d3_01_energy-------1=="+d3_01_energy);
																																					
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							System.out.println("D3-02 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")))
																										    							{
																										    								d3_02_energy = value;
																										    								System.out.println("d3_02_energy=="+d3_02_energy);
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							System.out.println("D3-03 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")))
																										    							{
																										    								d3_03_energy = value;
																										    								System.out.println("d3_03_energy=="+d3_03_energy);
																										    							}
																										    						}
																										    					}
																										    				//End Secure 
																										    				
																										    				
																										    				
																										    				else
																										    				{
																										    					//values for updating MeterMaster
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-13-1-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-13-2-0")||code.equalsIgnoreCase("P7-3-13-1-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-13-2-0")||code.equalsIgnoreCase("P7-6-13-1-0") ||code.equalsIgnoreCase("P7-6-18-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    					}//end of values for updating MeterMaster
																										    					//for HPL
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    						System.out.println("D3-01 B3 valuesqqqqqqq - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_01_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_02_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_03_energy = value;
																										    							}
																										    						}
																										    					}
																										    					
																										    				}
																										    			}
																														
																													 }
																												 }
																												 
																											 }
																											 
																										 }//End ForLoop
																										 D3Data d3 = new D3Data();
																										 d3.setCdfId(cdfId);
																										 System.out.println("d3_01_energy=="+d3_01_energy);
																										 System.out.println("d3_02_energy=="+d3_02_energy);
																										 System.out.println("d3_03_energy=="+d3_03_energy);
																												
																										 if(d3_01_energy.equalsIgnoreCase("") || d3_01_energy.equalsIgnoreCase(null))
																										 {
																											 d3_01_energy = "0";
																										 }
																										 if(d3_02_energy.equalsIgnoreCase("") || d3_02_energy.equalsIgnoreCase(null))
																										 {
																											 d3_02_energy = "0";
																										 }
																										 if(d3_03_energy.equalsIgnoreCase("") || d3_03_energy.equalsIgnoreCase(null))
																										 {
																											 d3_03_energy = "0";
																										 }
																										 d3.setD3_01_Energy(Float.parseFloat(d3_01_energy));
																										 d3.setD3_02_Energy(Float.parseFloat(d3_02_energy));
																										 d3.setD3_03_Energy(Float.parseFloat(d3_03_energy));
																										 d3DataService.save(d3);
																										 
																										 System.out.println("Insert into D3 completed");
																									 }//End Of D3
																									 
																									 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D4"))//Check The D4 Tag
																									 {

																										 
																										 System.out.println("enter in to D4 node --==-- mobileee");
																										 
																										 String checkDatePattern = "";
																										 NodeList subnodeListD4 = d1DataNodeData.getChildNodes();
																										 if (d1DataNodeData.hasAttributes()) 
																							    			{
																							    	 			NamedNodeMap nodeMap = d1DataNodeData.getAttributes();
																							    				//String code = "",value = "", unit = "";
																							    	 			System.out.println("nodeMap.getLength()=="+nodeMap.getLength());
																							    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																							    				{
																							    					Node node = nodeMap.item(nodeMapIndex);
																							    					intervalPeriod = node.getNodeValue();
																							    					//System.out.println("attr name : " + node.getNodeName()+" attr value : " + intervalPeriod);
																							    				}
																							    			}
																										 
																										 for (int countD4 = 0; countD4 < subnodeListD4.getLength(); countD4++) 
																											{
																											    String dayProfileDate = "";
																												Node tempNodeD4 = subnodeListD4.item(countD4);
																												 if (tempNodeD4.getNodeType() == Node.ELEMENT_NODE) 
																												 {
																													 String nodeName = tempNodeD4.getNodeName();
																													 String nodeValue = tempNodeD4.getTextContent();
																													 if (tempNodeD4.hasAttributes()) 
																										    			{
																										    	 			NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																										    	 			
																										    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																										    				{
																										    					Node node = nodeMap.item(nodeMapIndex);
																										    					dayProfileDate = node.getNodeValue();
																										    					//System.out.println(" profile date attr name : " + node.getNodeName()+" attr value : " + dayProfileDate);
																										    																		    																	    																	    					
																										    				}													    				
																										    			}
																													 
																													 if(dayProfileDate.length() > 2)
																														{
																															checkDatePattern = dayProfileDate.substring(2);
																															if(checkDatePattern.startsWith("-"))
																															{
																																dayProfileDate = sdf3.format(sdf3.parse(dayProfileDate));
																															}
																															else if(checkDatePattern.startsWith("/"))
																															{
																																dayProfileDate = sdf4.format(sdf4.parse(dayProfileDate));
																																
																															}
																															else
																															{
																																 dayProfileDate = "";
																															}
																														}
																														else
																														{
																															 dayProfileDate = "";
																														}
																													 if(prevMonthFlag)
																													 {
																														 Calendar cal1 = Calendar.getInstance();
																														 if(dayProfileDate.length() > 2)
																															{
																															 String checkForDatePatter = dayProfileDate.substring(2);
																															 if(checkForDatePatter.startsWith("-"))
																																{
																																 cal1.setTime(sdf3.parse(dayProfileDate));
																																}
																																else if(checkForDatePatter.startsWith("/"))
																																{
																																	 cal1.setTime(sdf4.parse(dayProfileDate));
																																}
																																else
																																{
																																	 dayProfileDate = "";
																																}
																															}
																														 
																														cal1.add(Calendar.MONTH, 1);
																														 String profileDateYearMonth = sdfBillDate.format(cal1.getTime());
																														 if(profileDateYearMonth.equalsIgnoreCase(billmonth+""))//Checking Profile Date is Equal to this Month
																														 {
																															 int kwhFlag = 0;
																															 NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																															 for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																{
																																 	String ipInterval = "0";
																																	int ipIntervalNum = 0;
																																	 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																	 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																	 {
																																		 String subNodeName = subTempNode.getNodeName();//IP ..
																																		 String subNodeValue = subTempNode.getTextContent();
																																		 if(subTempNode.hasAttributes())
																																		 {
																																			 NamedNodeMap nodeMap = subTempNode.getAttributes();
																															    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																															    				{
																															    					Node node = nodeMap.item(nodeMapIndex);
																															    					ipInterval = node.getNodeValue();
																															    				}
																																			 
																																		 }
																																		 
																																		 
																																	 }
																																	 ipIntervalNum = Integer.parseInt(ipInterval);
																																	 boolean sumKwhFlag = true;
																																	 boolean sumKvaFlag = true;
																																	 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																	 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++)
																																	 {
																																		 
																																		 String paramCode = "", paramValue = "";
																																		 Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																		 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																		 {
																																			String subNodeName = subTempNodeIP.getNodeName();//IFLAG, PARAMETER
																																			String subNodeValue = subTempNodeIP.getTextContent();
																																			if(subTempNodeIP.hasAttributes()) 
																															    			{
																																				NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																															    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																															    				{
																															    					
																															    					Node node = nodeMap.item(nodeMapIndex);
																															    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																															    					{
																															    						paramCode = node.getNodeValue();
																															    					}
																															    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																															    					{
																															    						paramValue = node.getNodeValue();
																															    					}
																															    					
																															    					
																															    				}
																															    				
																															    			}
																																			
																																		 }	
																																		
																																						//FOR HPL
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-6-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								// System.out.println("come to hpp====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 //*2
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 d4KwhValue = paramValue;
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 }
																																								// System.err.println(" coming d4vrrrrrrr");
																																								 //D4VRphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4vrValue = paramValue;
																																									System.err.println("d4vrValue--"+d4vrValue);
																																								 }
																																								 //D4VYphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vyValue= paramValue;
																																									// System.err.println("d4vyValue--"+d4vyValue);
																																								 }
																																								 //D4VBphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vbValue= paramValue;
																																									// System.err.println("d4vbValue--"+d4vbValue);
																																								 }
																																								//D4ARphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4arValue = paramValue;
																																									//System.err.println("d4arValue--"+d4arValue);
																																								 }
																																								 //D4AYphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4ayValue= paramValue;
																																									// System.err.println("d4ayValue--"+d4ayValue);
																																								 }
																																								 //D4ABphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4abValue= paramValue;
																																									// System.err.println("d4abValue--"+d4abValue);
																																								 }
																																								
																																						// System.err.println("d4 abbbbbb");
																																					 //End HPL
																																					 
																																					//Load Utilization values
																																					 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																					 //HPL
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")
																																							 ||paramCode.equalsIgnoreCase("P7-6-13-1-0") || paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 if(Integer.parseInt(intervalPeriod) > 15)
																																						 {
																																							 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																							 {
																																								 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva1_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva1_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva1_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva1_gt_cd60++;
																																								 }
																																							 }
																																							 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																							 {
																																								 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva2_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva2_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva2_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva2_gt_cd60++;
																																								 }
																																							 }
																																						 }
																																						 else{
																																							 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																							 {

																																								 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva1_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva1_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva1_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva1_gt_cd60++;
																																								 }
																																							 
																																							 }
																																							 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																							 {

																																								 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva2_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva2_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva2_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva2_gt_cd60++;
																																								 }
																																							 
																																							 }
																																						 }
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						if(paramValue.equalsIgnoreCase("") )
																																						{
																																							paramValue = "0";
																																						}
																																						
																																						 float tempPf = Float.parseFloat(paramValue);
																																							
																																						 sumPf = sumPf + tempPf;
																																						 
																																						 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																						 d4PfValue = paramValue;
																																						 if(tempPf == 0)
																																						 {
																																							pfNoLoad++; 
																																						 }
																																						 if(tempPf == -1)
																																						 {
																																							pfBlackOut++; 
																																						 }
																																						 if((tempPf != 0) && (tempPf != -1))
																																						 {
																																							 if(tempPf <= 0.5F)
																																							 {
																																								 pfLt05++;
																																							 }
																																							 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																							 {
																																								 pf05To07++;
																																								// pfVal1 = pfVal1 + tempPf + ",";
																																							 }
																																							 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																							 {
																																								 pf07To09++;
																																								 //pfVal2 = pfVal2 + tempPf + ",";
																																							 }
																																							 else if(tempPf >0.9F)
																																							 {
																																								 pfGt09++;
																																							 }
																																						 }
																																						 /*if(tempPf > maxPf)
																																						 {
																																							 maxPf = tempPf;
																																						 }*/
																																						
																																					 }
																																		 }
																																	 D4CdfData d4 = new D4CdfData();
																																	 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																	 d4.setIpInterval(Integer.parseInt(ipInterval));
																																	 
																																	 d4.setArValue(d4arValue);d4.setAbValue(d4abValue);d4.setAyValue(d4ayValue);
																																	 d4.setVrValue(d4vrValue);d4.setVbValue(d4vbValue);d4.setVyValue(d4vyValue);
																																	 if(dayProfileDate.length() > 2)
																																		{
																																		 String checkForDatePatter1 = dayProfileDate.substring(2);
																																		 if(checkForDatePatter1.startsWith("-"))
																																			{
																																			 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																			 
																																			}
																																			else if(checkForDatePatter1.startsWith("/"))
																																			{
																																				d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																				 
																																			}
																																			else
																																			{
																																				 dayProfileDate = "";
																																			}
																																		}
																																	 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																	// System.out.println("d4KvaValue-->"+d4KvaValue+"  d4KwhValue-->"+d4KwhValue+" d4PfValue-->"+d4PfValue);
																																	 d4.setKvaValue(d4KvaValue);d4.setKwhValue(d4KwhValue);d4.setPfValue(d4PfValue);
																																	 d4.setIntervalPeriod(intervalPeriod);
																																	 d4LoadDataService.save(d4);
																																	 d4KvaValue = "";
																																	 d4KwhValue = "";
																																	 d4PfValue = "";
																																	 ipInterval = "";
																																	 
																																}//IP ForLoop
																															//assign all variables to zero
																																
																																//Power Factor Report
																																int intervalVal = Integer.parseInt(intervalPeriod);
																																
																																maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																minKva = Math.round(minKva * 1000.0)/1000.0;
																																sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																
																																D4Data d4 = new D4Data();
																																d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																if(dayProfileDate.length() > 2)
																																{
																																 String checkForDatePatter1 = dayProfileDate.substring(2);
																																 if(checkForDatePatter1.startsWith("-"))
																																	{
																																	 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																	 
																																	}
																																	else if(checkForDatePatter1.startsWith("/"))
																																	{
																																		d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																		 
																																	}
																																	else
																																	{
																																		 dayProfileDate = "";
																																	}
																																}
																															    
																															    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																														        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																														        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																														        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																														        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																														        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																														        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																														        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																														        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																														        d4DtataService.save(d4);
																														        kwhFlag = 0;
																																maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																
																																kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																														 }
																													 }//If Previous Data Der
																													 else
																													 {
																														    int kwhFlag = 0;
																															NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																															for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																															{
																																String ipInterval = "0";
																																int ipIntervalNum = 0;
																																 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																 {
																																	 if(subTempNode.hasAttributes()) 
																														    			{
																														    	 			NamedNodeMap nodeMap = subTempNode.getAttributes();
																														    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																														    				{
																														    					Node node = nodeMap.item(nodeMapIndex);
																														    					ipInterval = node.getNodeValue();
																														    					//System.out.println(" IP interval attr name : " + node.getNodeName()+" attr value : " + ipInterval);
																														    																				    					
																														    				}
																														    			}
																																	 
																																 }
																																 ipIntervalNum = Integer.parseInt(ipInterval);
																																 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																 boolean sumKwhFlag = true;
																																 boolean sumKvaFlag = true;
																																 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++) 
																																	{
																																	    String tagAttrId = "";
																																		String paramCode = "", paramValue = "";
																																		Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																		 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																		 {
																																			 if(subTempNodeIP.hasAttributes()) 
																																    			{
																																				 NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																    				{
																																    					Node node = nodeMap.item(nodeMapIndex);
																																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																    					{
																																    						paramCode = node.getNodeValue();
																																    					}
																																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																    					{
																																    						paramValue = node.getNodeValue();
																																    					}
																																    				}
																																				 
																																    			}
																																			 
																																		 }
																																		 
																																		

																																				//FOR HPL
																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-6-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 d4KvaValue = paramValue;
																																						// System.out.println("d4KvaValue===="+d4KvaValue);
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						 //System.out.println("d4KvaValue===="+d4KvaValue);
																																								
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					  //System.err.println(" coming d4zzzz");
																																					 //D4VRphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4vrValue = paramValue;
																																						//System.err.println("d4vrValuezz--"+d4vrValue);
																																					 }
																																					 //D4VYphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vyValue= paramValue;
																																						 //System.err.println("d4vyValuezz--"+d4vyValue);
																																					 }
																																					 //D4VBphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vbValue= paramValue;
																																						// System.err.println("d4vbValuezz--"+d4vbValue);
																																					 }
																																					//D4ARphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4arValue = paramValue;
																																						//System.err.println("d4arValuezz--"+d4arValue);
																																					 }
																																					 //D4AYphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4ayValue= paramValue;
																																						// System.err.println("d4ayValuezz--"+d4ayValue);
																																					 }
																																					 //D4ABphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4abValue= paramValue;
																																						// System.err.println("d4abValue--"+d4abValue);
																																					 }
																																					
																																			 //System.err.println("d4 zzz");
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																						//KWH
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 float tempKwh = Float.parseFloat(paramValue);
																																							 d4KwhValue = paramValue;
																																							 if(sumKwhFlag)
																																							 {
																																								 sumKwh = sumKwh + tempKwh;
																																								 sumKwhFlag = false;
																																							 }
																																							 kwhFlag = 1;
																																							 /*if(tempKwh > maxKwh)
																																							 {
																																								 maxKwh = tempKwh;
																																							 }*/
																																							 
																																						 }
																																			
																																		//Load Utilization values
																																		 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																				 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																		 //HPL
																																		 if(paramCode.equalsIgnoreCase("P7-6-5-1-0"))
																																		 {
																																			 if( paramValue == "")
																																			 {
																																				 paramValue = "0";
																																			 }
																																			 float tempKva = Float.parseFloat(paramValue);
																																			 if(Integer.parseInt(intervalPeriod) > 15)
																																			 {
																																				 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																				 {
																																					 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																					 if(tempKva <= cd_20)
																																					 {
																																						 kva1_lt_cd20++;
																																					 }
																																					 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																					 {
																																						 kva1_lt_cd40++;
																																					 }
																																					 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																					 {
																																						 kva1_lt_cd60++;
																																					 }
																																					 else if(tempKva > cd_60)
																																					 {
																																						 kva1_gt_cd60++;
																																					 }
																																				 }
																																				 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																				 {
																																					 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																					 if(tempKva <= cd_20)
																																					 {
																																						 kva2_lt_cd20++;
																																					 }
																																					 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																					 {
																																						 kva2_lt_cd40++;
																																					 }
																																					 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																					 {
																																						 kva2_lt_cd60++;
																																					 }
																																					 else if(tempKva > cd_60)
																																					 {
																																						 kva2_gt_cd60++;
																																					 }
																																				 }
																																			 }
																																			 else{
																																				 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																				 {

																																					 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																					 if(tempKva <= cd_20)
																																					 {
																																						 kva1_lt_cd20++;
																																					 }
																																					 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																					 {
																																						 kva1_lt_cd40++;
																																					 }
																																					 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																					 {
																																						 kva1_lt_cd60++;
																																					 }
																																					 else if(tempKva > cd_60)
																																					 {
																																						 kva1_gt_cd60++;
																																					 }
																																				 
																																				 }
																																				 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																				 {

																																					 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																					 if(tempKva <= cd_20)
																																					 {
																																						 kva2_lt_cd20++;
																																					 }
																																					 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																					 {
																																						 kva2_lt_cd40++;
																																					 }
																																					 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																					 {
																																						 kva2_lt_cd60++;
																																					 }
																																					 else if(tempKva > cd_60)
																																					 {
																																						 kva2_gt_cd60++;
																																					 }
																																				 
																																				 }
																																			 }
																																		 }
																																		 
																																		 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																		 {
																																			 //System.out.println("====== "+Float.parseFloat(paramValue));
																																			if(paramValue.equalsIgnoreCase("") )
																																			{
																																				paramValue = "0";
																																			}
																																			
																																			 float tempPf = Float.parseFloat(paramValue);
																																				
																																			 sumPf = sumPf + tempPf;
																																			 d4PfValue = paramValue;
																																			 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																			 
																																			 if(tempPf == 0)
																																			 {
																																				pfNoLoad++; 
																																			 }
																																			 if(tempPf == -1)
																																			 {
																																				pfBlackOut++; 
																																			 }
																																			 if((tempPf != 0) && (tempPf != -1))
																																			 {
																																				 if(tempPf <= 0.5F)
																																				 {
																																					 pfLt05++;
																																				 }
																																				 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																				 {
																																					 pf05To07++;
																																					// pfVal1 = pfVal1 + tempPf + ",";
																																				 }
																																				 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																				 {
																																					 pf07To09++;
																																					 //pfVal2 = pfVal2 + tempPf + ",";
																																				 }
																																				 else if(tempPf >0.9F)
																																				 {
																																					 pfGt09++;
																																				 }
																																			 }
																																			 /*if(tempPf > maxPf)
																																			 {
																																				 maxPf = tempPf;
																																			 }*/
																																			
																																		 }
																																	}
																																 D4CdfData d4 = new D4CdfData();
																																 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																 d4.setIpInterval(Integer.parseInt(ipInterval));
																																 d4.setArValue(d4arValue);d4.setAbValue(d4abValue);d4.setAyValue(d4ayValue);
																																 d4.setVrValue(d4vrValue);d4.setVbValue(d4vbValue);d4.setVyValue(d4vyValue);
																																 if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter1 = dayProfileDate.substring(2);
																																	 if(checkForDatePatter1.startsWith("-"))
																																		{
																																		 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 
																																		}
																																		else if(checkForDatePatter1.startsWith("/"))
																																		{
																																			d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																			 
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																 d4.setKvaValue(d4KvaValue);d4.setKwhValue(d4KwhValue);d4.setPfValue(d4PfValue);
																																 d4.setIntervalPeriod(intervalPeriod);
																																 d4LoadDataService.save(d4);
																																 d4KvaValue = "";
																																 d4KwhValue = "";
																																 d4PfValue = "";
																																 ipInterval = "";
																															}
																															//assign all variables to zero
																															
																															//Power Factor Report
																															int intervalVal = Integer.parseInt(intervalPeriod);
																															
																															maxKva = Math.round(maxKva * 1000.0)/1000.0;
																															minKva = Math.round(minKva * 1000.0)/1000.0;
																															sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																															sumPf = Math.round(sumPf * 1000.0)/1000.0;
																															sumKva = Math.round(sumKva * 1000.0)/1000.0;
																															
																															D4Data d4 = new D4Data();
																															d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																															if(dayProfileDate.length() > 2)
																															{
																															 String checkForDatePatter1 = dayProfileDate.substring(2);
																															 if(checkForDatePatter1.startsWith("-"))
																																{
																																 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																 
																																}
																																else if(checkForDatePatter1.startsWith("/"))
																																{
																																	d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																	 
																																}
																																else
																																{
																																	 dayProfileDate = "";
																																}
																															}
																														    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																													        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																													        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																													        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																													        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																													        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																													        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																													        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																													        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																													        d4DtataService.save(d4);
																													        kwhFlag = 0;
																															maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																															pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																															
																															kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																															kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																															kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																															kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																														 
																													 }
																												}
																											}//End For Loop
																										 System.out.println("Insert into D4 completed mooobbiile");
																									 
																									 }// Ends the  D4 Tag
																									 
																									 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D5"))//Check The D5 Tag
																									 {
																											
																										 System.out.println("enter to d5 node");
																												
																										 NodeList subnodeListD5 = d1DataNodeData.getChildNodes();
																										 
																										 if(d1DateForCheckin.equalsIgnoreCase(billmonth+""))
																											{
																											 System.out.println("subnodeListD5.getLength()=="+subnodeListD5.getLength());
																												for (int countD5 = 0; countD5 < subnodeListD5.getLength(); countD5++) 
																												{
																													String checkDatePattern = "";
																													String d5ReadTagAttrId = "0",d5Id = "0";
																													 Node tempNodeD5 = subnodeListD5.item(countD5);
																													 if(tempNodeD5.getNodeType()==Node.ELEMENT_NODE)
																													 {
																														 String nodeName = tempNodeD5.getNodeName();
																														 String code = "", time = "",status = "",duration="",oldtime="";
																														 if(nodeName.equalsIgnoreCase("EVENT"))
																									    					{
																															 
																															 if (tempNodeD5.hasAttributes()) 
																												    			{
																																  NamedNodeMap nodeMap = tempNodeD5.getAttributes();
																																  for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																												    				{
																																	    String d5AttrReadAttributeId = "0",value = "";
																												    					Node node = nodeMap.item(nodeMapIndex);
																												    					value = node.getNodeValue();
																												    					if(node.getNodeName().equalsIgnoreCase("CODE"))
																												    					{
																												    						code = node.getNodeValue();
																												    					}
																												    					else if(node.getNodeName().equalsIgnoreCase("TIME"))
																												    					{
																												    						time = node.getNodeValue();
																												    					}
																												    					else if(node.getNodeName().equalsIgnoreCase("STATUS"))
																												    					{
																												    						status = node.getNodeValue();
																												    					}
																												    					else if(node.getNodeName().equalsIgnoreCase("DURATION"))
																												    					{
																												    						duration=node.getNodeValue();
																												    					}
																																	  
																												    				}
																																  
																																 
																												    			}
																															 if(status.equalsIgnoreCase("0"))
																										    					{
																																   //oldtime=time;
																																   model.addAttribute("oldtime",time);
																										    					}
																															 
																															    if(status.equalsIgnoreCase("1") && time=="")
																															    {
																																    oldtime= (String) model.get("oldtime");
																																	String oldtimeFormat=oldtime;
																																	String timeduration=duration; 
																																	String time11=oldtimeFormat.substring(11);
																																	String time2=timeduration.substring(4);
																																	String s3=oldtimeFormat.substring(0,11);
																																	
																																	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
																																	timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
																																	
																																	Date date1 = timeFormat.parse(time11);
																																	Date date2 = timeFormat.parse(time2);
																																	
																																	long sum = date1.getTime() + date2.getTime();
																																	
																																	String date3 = timeFormat.format(new Date(sum));
																																	time=s3+date3;
																																	System.out.println("status & time----"+status+"   "+time);
																															    }
																															 	String query = "";
																																String time1 = "";
																																int d5Status = 0;
																																D5Data d5 = new D5Data();
																																if(time.length() > 2)
																																{
																																	checkDatePattern = time.substring(2);
																																	if(checkDatePattern.startsWith("-"))
																																	{
																																		time1 =  time1 = sdf2.format(sdf2.parse(time));
																																		//d5.setEventTime(sdf2.parse(time1));
																																		System.out.println("event time="+sdf2.parse(time));
																																	}
																																	else if(checkDatePattern.startsWith("/"))
																																	{
																																		time1 = sdf4.format(sdf4.parse(time));
																																	}
																																	else{
																																		time1= "";
																																	}
																																}
																																else
																																{
																																	time1= "";
																																}
																																
																																	if(code.equalsIgnoreCase("") || code.equalsIgnoreCase(null))
																																	{
																																		code = "0";
																																	}
																					
																																
																																d5.setCdfId(cdfId);d5.setEventCode(Integer.parseInt(code));d5.setEventStatus(status);
																																/*if(time1 != "" || time1 != null)
																																{
																																	
																																}*/
																																
																																
																																
																																//Inserting SnapShots for Each Event 
																																if(tempNodeD5.hasChildNodes())
																																{
																																	
																																	NodeList d5SnapshotList = tempNodeD5.getChildNodes();
																																	for(int d5sub = 0;d5sub<d5SnapshotList.getLength();d5sub++)
																																	{
																																		
																																		 Node d5tempNode123 = d5SnapshotList.item(d5sub);
																																		 if (d5tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																																		 {
																																			 String subNodeName = d5tempNode123.getNodeName();
																																			 String subNodeValue = d5tempNode123.getTextContent();
																																			 
																																			 if(d5tempNode123.hasAttributes())
																																			 {
																																				 NamedNodeMap nodeMap = d5tempNode123.getAttributes();
																																    				String snapCode = "",snapValue = "", snapUnit = "";
																																    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																    				{
																																    					Node node = nodeMap.item(nodeMapIndex);
																																    					//System.out.println("attr name : " + node.getNodeName());
																																    					//System.out.println("attr value : " + node.getNodeValue());
																																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																    						snapCode = node.getNodeValue();
																																    					else if(node.getNodeName().equalsIgnoreCase("VALUE")) 
																																    						snapValue = node.getNodeValue();
																																    					else if(node.getNodeName().equalsIgnoreCase("UNIT")) 
																																    						snapUnit = node.getNodeValue();													    																	    					
																																    				}
																																    				//System.out.println("==inside snapCode  ===>"+snapCode+"==>"+snapValue+"==>"+snapUnit);
																																    				//System.out.println("==inside manufacturerName  ===>"+manufacturerCode+"==>"+manufacturerName);
																																    				if(snapValue.equalsIgnoreCase(""))
																																    				{
																																    					snapValue = "0";
																																    				}
																																    				if(snapCode.equalsIgnoreCase("P1-2-1-1-0") && snapUnit.equalsIgnoreCase("V"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					rPhaseVal1 = temp;
																																    					/*if(temp > rPhaseVal1)
																																    					{
																																    						rPhaseVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P1-2-2-1-0") && snapUnit.equalsIgnoreCase("V"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					yPhaseVal1 = temp;
																																    					/*if(temp > yPhaseVal1)
																																    					{
																																    						yPhaseVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P1-2-3-1-0") && snapUnit.equalsIgnoreCase("V"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					bPhaseVal1 = temp;
																																    					/*if(temp > bPhaseVal1)
																																    					{
																																    						bPhaseVal1 = temp;
																																    					}*/
																																    				}
																																    				
																																    				else if(snapCode.equalsIgnoreCase("P2-1-1-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					
																																    					rPhaseLineVal1 = temp;
																																    					/*if(temp > rPhaseLineVal1)
																																    					{
																																    						rPhaseLineVal1 = temp;
																																    						//System.out.println("==inside rPhaseLineVal1 1 ===>"+snapValue+"==>"+temp+"==>"+rPhaseLineVal1);
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P2-1-2-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					yPhaseLineVal1 = temp;
																																    					
																																    					/*if(temp > yPhaseLineVal1)
																																    					{
																																    						yPhaseLineVal1 = temp;
																																    						//System.out.println("==inside yPhaseLineVal1 2 ===>"+snapValue+"==>"+temp+"==>"+yPhaseLineVal1);
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P2-1-3-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					bPhaseLineVal1 = temp;
																																    					/*if(temp > bPhaseLineVal1)
																																    					{
																																    						bPhaseLineVal1 = temp;
																																    						//System.out.println("==inside bPhaseLineVal1 3 ===>"+snapValue+"==>"+temp+"==>"+bPhaseLineVal1);
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-1-1-0-0") && snapUnit.equalsIgnoreCase("A"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					System.out
																																								.println();
																																    					rPhaseActiveVal1 = temp;
																																    					/*if(temp > rPhaseActiveVal1)
																																    					{
																																    						rPhaseActiveVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-2-1-0-0")&& snapUnit.equalsIgnoreCase("A"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					
																																    					yPhaseActiveVal1 = temp;
																																    					/*if(temp > yPhaseActiveVal1)
																																    					{
																																    						yPhaseActiveVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-3-1-0-0") && snapUnit.equalsIgnoreCase("A"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					bPhaseActiveVal1 = temp;
																																    					/*if(temp > bPhaseActiveVal1)
																																    					{
																																    						bPhaseActiveVal1 = temp;
																																    					}*/
																																    				}
																																    				
																																    				else if(snapCode.equalsIgnoreCase("P4-1-1-0-0") || snapCode.equalsIgnoreCase("P4-1-4-0-0"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					
																																    					rPhasePFVal1 = temp;
																																    					/*if(temp > rPhasePFVal1)
																																    					{
																																    						rPhasePFVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-2-1-0-0") || snapCode.equalsIgnoreCase("P4-2-4-0-0"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					yPhasePFVal1 = temp;
																																    					/*if(temp > yPhasePFVal1)
																																    					{
																																    						yPhasePFVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-3-1-0-0") || snapCode.equalsIgnoreCase("P4-3-4-0-0"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					bPhasePFVal1 = temp;
																																    					/*if(temp > bPhasePFVal1)
																																    					{
																																    						bPhasePFVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P4-4-1-0-0") || snapCode.equalsIgnoreCase("P4-4-4-0-0"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					avgPFVal1 = temp;
																																    					/*if(temp > avgPFVal1)
																																    					{
																																    						avgPFVal1 = temp;
																																    					}*/
																																    				}
																																    				else if((snapCode.equalsIgnoreCase("P3-1-4-1-0") || snapCode.equalsIgnoreCase("P3-2-4-1-0")) && snapCode.equalsIgnoreCase("K"))
																																    				{
																																    					float temp = Float.parseFloat(snapValue);
																																    					activePowerVal1 = temp;
																																    					/*if(temp > activePowerVal1)
																																    					{
																																    						activePowerVal1 = temp;
																																    					}*/
																																    				}
																																    				else if(snapCode.equalsIgnoreCase("P8-1-0-0-0"))
																																    				{
																																    					phaseSequence1 = snapValue;
																																    				}
																																    				
																																    				/*if(manufacturerCode.equalsIgnoreCase("1"))
																																    				{
																																    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||snapCode.equalsIgnoreCase("P7-1-18-2-0"))
																																	    				{
																																    						float temp = Float.parseFloat(snapValue);
																																    						d5_kwh=temp;
																																    						//System.out.println("==inside 1 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																	    				}
																																    					
																																    				}*/
																																    				/*else if(manufacturerCode.equalsIgnoreCase("4"))
																																    				{
																																    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||
																																    					   snapCode.equalsIgnoreCase("P7-1-5-2-0") || snapCode.equalsIgnoreCase("P7-1-13-2-0"))
																																		    				{
																																    						float temp = Float.parseFloat(snapValue);
																																    						d5_kwh=temp;
																																    						//System.out.println("==inside 2 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																		    				}
																																    				}*/
																																    				/*else if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.contains("LARSEN")|| manufacturerName.contains("L&T"))
																																    				{
																																    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||snapCode.equalsIgnoreCase("P7-1-5-2-0"))
																																		    				{
																																    						//System.out.println("Inside P7-1-5-2-0 kwh value");
																																									
																																    						float temp = Float.parseFloat(snapValue);
																																    						d5_kwh=temp;
																																    						//System.out.println("==inside 3 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																		    				}
																																    				}*/
																																    				
																																    					if(snapCode.equalsIgnoreCase("P7-1-13-2-0")||snapCode.equalsIgnoreCase("P7-1-13-1-0") ||snapCode.equalsIgnoreCase("P7-1-18-2-0")||snapCode.equalsIgnoreCase("P7-1-5-1-0"))
																																		    				{
																																    						float temp = Float.parseFloat(snapValue);
																																    						d5_kwh=temp;
																																    						//System.out.println("==inside dgdf4 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																		    				}
																																    				
																																    					
																																			 }
																																		 }
																																		 
																																	}//End ForLoop
																																	
																																	//System.out.println("==inside ryb phase val ===>"+bPhaseVal1+"==>"+rPhaseVal1+"==>"+yPhaseVal1);
																																	d5.setbPhaseVal(bPhaseVal1);
																																	d5.setrPhaseVal(rPhaseVal1);
																																	d5.setyPhaseVal(yPhaseVal1);
																																	//System.out.println("==inside ryb phase line ===>"+rPhaseLineVal1+"==>"+yPhaseLineVal1+"==>"+bPhaseLineVal1);
																																	d5.setrPhaseLineVal(rPhaseLineVal1);
																																	d5.setyPhaseLineVal(yPhaseLineVal1);
																																	d5.setbPhaseLineVal(bPhaseLineVal1);
																																	//System.out.println("==inside ryb phase active ===>"+rPhaseActiveVal1+"==>"+yPhaseActiveVal1+"==>"+bPhaseActiveVal1);
																																	d5.setrPhaseActiveVal(rPhaseActiveVal1);
																																	d5.setyPhaseActiveVal(yPhaseActiveVal1);
																																	d5.setbPhaseActiveVal(bPhaseActiveVal1);
																																	//System.out.println("==inside ryb phase pf ===>"+rPhasePFVal1+"==>"+yPhasePFVal1+"==>"+bPhasePFVal1);
																																	d5.setrPhasePfVal(rPhasePFVal1);
																																	d5.setyPhasePfVal(yPhasePFVal1);
																																	d5.setbPhasePfVal(bPhasePFVal1);
																																	d5.setAvgPfVal(avgPFVal1);
																																	d5.setActivePowerVal(activePowerVal1);
																																	d5.setPhaseSequence(phaseSequence1);
																																	d5.setMf(mf);
																																	//System.out.println("==inside d5_kwh ===>"+d5_kwh);
																																	d5.setD5_kwh(d5_kwh);
																																	//d5SnashotService.save(d5Snap);
																																}
																																d5DataService.save(d5);
																																//System.out.println("d5 data saved successfully");
																																		
																																//D5 Snapshot variables
																																  rPhaseVal1 = 0; yPhaseVal1 = 0; bPhaseVal1 = 0;
																																  rPhaseLineVal1 = 0; yPhaseLineVal1 = 0; bPhaseLineVal1 = 0;
																																  rPhaseActiveVal1 = 0; yPhaseActiveVal1 = 0; bPhaseActiveVal1 = 0;
																																  rPhasePFVal1 = 0; yPhasePFVal1 = 0; bPhasePFVal1 = 0;
																																  avgPFVal1 = 0; activePowerVal1 = 0;
																																  phaseSequence1 = "";
																																  d5_kwh=0;
																									    					}
																														 
																													 }
																													
																												}// End ForLoop
																												
																											}
																										 System.out.println("Insert into D5 completed");
																									 }// Ends the  D5 Tag
																									
																									 
																									 
																									 // Ends the  D9 Tag
																								 }
																							 }
																						 }
																					 }
																				 }
																			}
																			
																		}//End Child Node
																		
																		if(manufacturerCode.equalsIgnoreCase("1"))
													    				{
																			if(kwhValue.equalsIgnoreCase(""))
																			{
																				kwhValue = kwhValue2;
																			}
																			if(kvahValue.equalsIgnoreCase(""))
																			{
																				kvahValue = kvahValue2;
																				if(kvahValue2.equalsIgnoreCase(""))
																				{
																					kvahValue = kvahValue3;
																				}
																			}
																			if(kvaValue.equalsIgnoreCase(""))
																			{
																				kvaValue = kvaValue2;
																				if(kvaValue2.equalsIgnoreCase(""))
																				{
																					kvaValue = kvaValue3;
																				}
																			}
													    				}
																		
																		if(kwhValue.equalsIgnoreCase("") || kwhValue.equalsIgnoreCase(null))
																		{
																			kwhValue = "0";
																		}
																		if(kvahValue.equalsIgnoreCase("") || kvahValue.equalsIgnoreCase(null))
																		{
																			kvahValue = "0";
																		}
																		if(kvaValue.equalsIgnoreCase("") || kvaValue.equalsIgnoreCase(null))
																		{
																			kvaValue = "0";
																		}
																		if(pfValue.equalsIgnoreCase("") || pfValue.equalsIgnoreCase(null))
																		{
																			pfValue = "0";
																		}
																		//int n = entityManager.createNamedQuery("MeterMaster.updateMeterMasterData").setParameter("currdngkwh", Double.parseDouble(kwhValue)).setParameter("currrdngkvah",Double.parseDouble(kvahValue)).setParameter("currdngkva",Double.parseDouble(kvaValue)).setParameter("mtrclass", meterClass).setParameter("pf", Double.parseDouble(pfValue)).setParameter("accno", accno).setParameter("metrno", meterNumber).setParameter("rdngmonth", billmonth).executeUpdate();
																		/*if(n >0)
																		{*/
																			//System.out.println("MeterMaster updated succussfully");
																			//File Deletion Part
																			mainStatus = "parsed";
																			
																			 String source = unZipFIlePath + "/"+filename;
																			 File sourceFile = new File(source);
																			 //System.out.println("2950 Source file delete : "+sourceFile.delete());
																		/*}
																		else{
																			System.out.println("MeterMaster updation failed");
																			mainStatus = "parsed";
																			 String source = unZipFIlePath + "/"+filename;
																			 File sourceFile = new File(source);
																			 System.out.println("Source file delete : "+sourceFile.delete());
																			// File Deletion Part
																		}*/
																		
																	}
																	
																}
																model.addAttribute("result","File Upload Successfully");
																
																
															  }
																 else{
																	 
																	 System.out
																			.println("MeterDoesnotExist --------");
																	 //Meter Doesnot Exist
																	 mainStatus = "meterDoesNotExist";
																	 System.out.println("metrnooo->"+meterNumber);
																	 String source = unZipFIlePath + "/"+filename;
																	 File sourceFile = new File(source);
																	// System.out.println("2976 Source file delete : "+sourceFile.delete());
																 }
															 }//End Main If
															 
															 else{
																 model.addAttribute("result","Meter Already Exist...");
																 cdfId = countData.get(0).getId();
																 long dataFound = (Long) postgresMdas.createQuery("SELECT COUNT(d.cdfId) FROM D4Data d WHERE d.cdfId = '"+cdfId+"' ").getSingleResult();
																 if(dataFound == 0)
																 {
																	 System.out.println("Import data for d4 tag");
																	int prevdata = cdfDataService.findPrevDataD4(meterNumber);
																	if(prevdata > 0)
																	{
																		 List masterList = masterService.getMeterDataInformation(meterNumber, billmonth+"");
																		 
																		 
																		 String accno="";
																		 for(Iterator<?> iterator1=  masterList.iterator(); iterator1.hasNext();)
									    			    			 		{
																			 System.out.println("insss for");
																			 Object[] obj=(Object[]) iterator1.next();
																			  accno = (String)obj[0];
																			  if(obj[1]!=null )
																			  {
																				ctrn = (Double)obj[1];
																			  }
																			  if(obj[2]!=null )
																			  {
																				  ctrd = (Double)obj[2];
																			  }
																			  if(obj[3]!=null )
																			  {
																				  cd = (Double)obj[3];
																			  }
																			  if(obj[4]!=null )
																			  {
																				  mf = (Double)obj[4];
																			  }
																				
									    			    			 		}
																			/*Object[] obj = (Object[]) masterList.get(0);
																			String accno = (String)obj[0];
																			ctrn = (Double)obj[1];
																			ctrd = (Double)obj[2];
																			cd = (Double)obj[3];
																			mf = (Double)obj[4];*/
																			if(mf > 0)
																			{
																				cd_20 = ((((cd/mf)*20)/100)/2);
																				cd_40 = ((((cd/mf)*40)/100)/2);
																				cd_60 = ((((cd/mf)*60)/100)/2);
																			}
																		 prevMonthFlag = true;
																	}
																	
																	try{
																		
																		NodeList nodeList = doc.getElementsByTagName("CDF");
																		for(int count1 = 0; count1 < nodeList.getLength();count1++)
																		{
																			Node tempNodeFor = nodeList.item(count1);//CDF Node
																			if(tempNodeFor.getNodeType() == Node.ELEMENT_NODE)
																			{
																				if(tempNodeFor.hasChildNodes())
																				{
																					NodeList subnodeList = tempNodeFor.getChildNodes();
																					for(int subCount1 = 0 ; subCount1 < subnodeListForMetrNo.getLength();subCount1++)
																					{
																						Node subChildNodeForData = subnodeList.item(subCount1);//Utility Node
																						 if(subChildNodeForData.getNodeType() == Node.ELEMENT_NODE)
																						 {
																							 if(subChildNodeForData.hasChildNodes())
																							 { 
																								 NodeList dataNodeListFordb = subChildNodeForData.getChildNodes();
																								 for(int dataSubCount1 = 0 ; dataSubCount1 < dataNodeListFordb.getLength();dataSubCount1++)
																								 {
																									 Node d1DataNodeData = dataNodeListFordb.item(dataSubCount1);
																									 if(d1DataNodeData.getNodeType() == Node.ELEMENT_NODE)
																									 {
																										 if(d1DataNodeData.hasChildNodes())
																										 {
																											 if(d1DataNodeData.getNodeName().equalsIgnoreCase("D4"))//Check The D1 Tag
																											 {
																												 String checkDatePattern = "";
																												 NodeList subnodeListD4 = d1DataNodeData.getChildNodes();
																												 if (d1DataNodeData.hasAttributes()) 
																									    			{
																									    	 			NamedNodeMap nodeMap = d1DataNodeData.getAttributes();
																									    				//String code = "",value = "", unit = "";
																									    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																									    				{
																									    					Node node = nodeMap.item(nodeMapIndex);
																									    					intervalPeriod = node.getNodeValue();
																									    					//System.out.println("attr name : " + node.getNodeName()+" attr value : " + intervalPeriod);
																									    				}
																									    			}
																												 
																												 for (int countD4 = 0; countD4 < subnodeListD4.getLength(); countD4++) 
																													{
																													    String dayProfileDate = "";
																														Node tempNodeD4 = subnodeListD4.item(countD4);
																														 if (tempNodeD4.getNodeType() == Node.ELEMENT_NODE) 
																														 {
																															 String nodeName = tempNodeD4.getNodeName();
																															 String nodeValue = tempNodeD4.getTextContent();
																															 if (tempNodeD4.hasAttributes()) 
																												    			{
																												    	 			NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																												    	 			
																												    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																												    				{
																												    					Node node = nodeMap.item(nodeMapIndex);
																												    					dayProfileDate = node.getNodeValue();
																												    					//System.out.println(" profile date attr name : " + node.getNodeName()+" attr value : " + dayProfileDate);
																												    																		    																	    																	    					
																												    				}													    				
																												    			}
																															 
																															 if(dayProfileDate.length() > 2)
																																{
																																	checkDatePattern = dayProfileDate.substring(2);
																																	if(checkDatePattern.startsWith("-"))
																																	{
																																		dayProfileDate = sdf3.format(sdf3.parse(dayProfileDate));
																																	}
																																	else if(checkDatePattern.startsWith("/"))
																																	{
																																		dayProfileDate = sdf4.format(sdf4.parse(dayProfileDate));
																																		
																																	}
																																	else
																																	{
																																		 dayProfileDate = "";
																																	}
																																}
																																else
																																{
																																	 dayProfileDate = "";
																																}
																															 if(prevMonthFlag)
																															 {
																																 Calendar cal1 = Calendar.getInstance();
																																 if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter = dayProfileDate.substring(2);
																																	 if(checkForDatePatter.startsWith("-"))
																																		{
																																		 cal1.setTime(sdf3.parse(dayProfileDate));
																																		}
																																		else if(checkForDatePatter.startsWith("/"))
																																		{
																																			 cal1.setTime(sdf4.parse(dayProfileDate));
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																 cal1.add(Calendar.MONTH, 1);
																																 String profileDateYearMonth = sdfBillDate.format(cal1.getTime());
																																 if(profileDateYearMonth.equalsIgnoreCase(billmonth+""))//Checking Profile Date is Equal to this Month
																																 {
																																	 int kwhFlag = 0;
																																	 NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																	 for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																		{
																																		 	String ipInterval = "0";
																																			int ipIntervalNum = 0;
																																			 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																			 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																			 {
																																				 String subNodeName = subTempNode.getNodeName();//IP ..
																																				 String subNodeValue = subTempNode.getTextContent();
																																				 if(subTempNode.hasAttributes())
																																				 {
																																					 NamedNodeMap nodeMap = subTempNode.getAttributes();
																																	    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																	    				{
																																	    					Node node = nodeMap.item(nodeMapIndex);
																																	    					ipInterval = node.getNodeValue();
																																	    				}
																																				 }
																																				 
																																			 }
																																			 ipIntervalNum = Integer.parseInt(ipInterval);
																																			 boolean sumKwhFlag = true;
																																			 boolean sumKvaFlag = true;
																																			 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																			 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++)
																																			 {
																																				 
																																				 String paramCode = "", paramValue = "";
																																				 Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																				 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																				 {
																																					String subNodeName = subTempNodeIP.getNodeName();//IFLAG, PARAMETER
																																					String subNodeValue = subTempNodeIP.getTextContent();
																																					if(subTempNodeIP.hasAttributes()) 
																																	    			{
																																						NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																	    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																	    				{
																																	    					
																																	    					Node node = nodeMap.item(nodeMapIndex);
																																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																	    					{
																																	    						paramCode = node.getNodeValue();
																																	    					}
																																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																	    					{
																																	    						paramValue = node.getNodeValue();
																																	    					}
																																	    					
																																	    				}
																																	    				
																																	    			}
																																					
																																				 }	
																																					
																																								//FOR HPL
																																									//KVA
																																									 if(paramCode.equalsIgnoreCase("P7-6-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																									 {
																																										 //System.out.println("====== "+Float.parseFloat(paramValue));
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										 
																																										 d4KvaValue = paramValue;
																																										 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																												d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																										 float tempKva = Float.parseFloat(paramValue);
																																										 if(sumKvaFlag)
																																										 {
																																											 sumKva = sumKva + tempKva;
																																											 sumKvaFlag = false;
																																										 }
																																										 
																																										 if(tempKva > maxKva)
																																										 {
																																											 maxKva = tempKva;
																																										 }
																																										 
																																										 if(minKva == 0)
																																										 {
																																											 minKva = tempKva;
																																										 }
																																										 if(tempKva < minKva)
																																										 {
																																											 minKva = tempKva;
																																										 }
																																										 
																																									 }
																																									 
																																									//'P7-1-5-1-0','P7-1-18-0-0'
																																										//KWH
																																										 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																										 {
																																											 //System.out.println("====== "+Float.parseFloat(paramValue));
																																											 if( paramValue == "")
																																											 {
																																												 paramValue = "0";
																																											 }
																																											 d4KwhValue = paramValue;
																																											 float tempKwh = Float.parseFloat(paramValue);
																																											 if(sumKwhFlag)
																																											 {
																																												 sumKwh = sumKwh + tempKwh;
																																												 sumKwhFlag = false;
																																											 }
																																											 kwhFlag = 1;
																																											 /*if(tempKwh > maxKwh)
																																											 {
																																												 maxKwh = tempKwh;
																																											 }*/
																																											 
																																										 }
																																								 
																																							 //End HPL
																																							 
																																							//Load Utilization values
																																							 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																							 //HPL
																																							 if(paramCode.equalsIgnoreCase("P7-6-5-1-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 if(Integer.parseInt(intervalPeriod) > 15)
																																								 {
																																									 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																									 {
																																										 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																										 if(tempKva <= cd_20)
																																										 {
																																											 kva1_lt_cd20++;
																																										 }
																																										 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																										 {
																																											 kva1_lt_cd40++;
																																										 }
																																										 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																										 {
																																											 kva1_lt_cd60++;
																																										 }
																																										 else if(tempKva > cd_60)
																																										 {
																																											 kva1_gt_cd60++;
																																										 }
																																									 }
																																									 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																									 {
																																										 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																										 if(tempKva <= cd_20)
																																										 {
																																											 kva2_lt_cd20++;
																																										 }
																																										 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																										 {
																																											 kva2_lt_cd40++;
																																										 }
																																										 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																										 {
																																											 kva2_lt_cd60++;
																																										 }
																																										 else if(tempKva > cd_60)
																																										 {
																																											 kva2_gt_cd60++;
																																										 }
																																									 }
																																								 }
																																								 else{
																																									 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																									 {

																																										 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																										 if(tempKva <= cd_20)
																																										 {
																																											 kva1_lt_cd20++;
																																										 }
																																										 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																										 {
																																											 kva1_lt_cd40++;
																																										 }
																																										 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																										 {
																																											 kva1_lt_cd60++;
																																										 }
																																										 else if(tempKva > cd_60)
																																										 {
																																											 kva1_gt_cd60++;
																																										 }
																																									 
																																									 }
																																									 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																									 {

																																										 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																										 if(tempKva <= cd_20)
																																										 {
																																											 kva2_lt_cd20++;
																																										 }
																																										 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																										 {
																																											 kva2_lt_cd40++;
																																										 }
																																										 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																										 {
																																											 kva2_lt_cd60++;
																																										 }
																																										 else if(tempKva > cd_60)
																																										 {
																																											 kva2_gt_cd60++;
																																										 }
																																									 
																																									 }
																																								 }
																																							 }
																																							 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																								d4PfValue = paramValue;
																																								 float tempPf = Float.parseFloat(paramValue);
																																									
																																								 sumPf = sumPf + tempPf;
																																								 
																																								 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																								 
																																								 if(tempPf == 0)
																																								 {
																																									pfNoLoad++; 
																																								 }
																																								 if(tempPf == -1)
																																								 {
																																									pfBlackOut++; 
																																								 }
																																								 if((tempPf != 0) && (tempPf != -1))
																																								 {
																																									 if(tempPf <= 0.5F)
																																									 {
																																										 pfLt05++;
																																									 }
																																									 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																									 {
																																										 pf05To07++;
																																										// pfVal1 = pfVal1 + tempPf + ",";
																																									 }
																																									 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																									 {
																																										 pf07To09++;
																																										 //pfVal2 = pfVal2 + tempPf + ",";
																																									 }
																																									 else if(tempPf >0.9F)
																																									 {
																																										 pfGt09++;
																																									 }
																																								 }
																																								 /*if(tempPf > maxPf)
																																								 {
																																									 maxPf = tempPf;
																																								 }*/
																																								
																																							 }
																																				 }
																																			 D4CdfData d4 = new D4CdfData();
																																			 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																			 d4.setIpInterval(Integer.parseInt(ipInterval));
																																			 if(dayProfileDate.length() > 2)
																																				{
																																				 String checkForDatePatter1 = dayProfileDate.substring(2);
																																				 if(checkForDatePatter1.startsWith("-"))
																																					{
																																					 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																					 
																																					}
																																					else if(checkForDatePatter1.startsWith("/"))
																																					{
																																						d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																						 
																																					}
																																					else
																																					{
																																						 dayProfileDate = "";
																																					}
																																				}
																																			 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																			 d4.setKvaValue(d4KvaValue);d4.setKwhValue(d4KwhValue);d4.setPfValue(d4PfValue);
																																			 d4.setIntervalPeriod(intervalPeriod);
																																			 d4LoadDataService.save(d4);
																																			 d4KvaValue = "";
																																			 d4KwhValue = "";
																																			 d4PfValue = "";
																																			 ipInterval = "";
																																			 
																																		}//IP ForLoop
																																	//assign all variables to zero
																																		
																																		//Power Factor Report
																																		int intervalVal = Integer.parseInt(intervalPeriod);
																																		
																																		maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																		minKva = Math.round(minKva * 1000.0)/1000.0;
																																		sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																		sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																		sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																		
																																		D4Data d4 = new D4Data();
																																		d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																		if(dayProfileDate.length() > 2)
																																		{
																																		 String checkForDatePatter1 = dayProfileDate.substring(2);
																																		 if(checkForDatePatter1.startsWith("-"))
																																			{
																																			 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																			 
																																			}
																																			else if(checkForDatePatter1.startsWith("/"))
																																			{
																																				d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																				 
																																			}
																																			else
																																			{
																																				 dayProfileDate = "";
																																			}
																																		}
																																	    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																																        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																																        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																																        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																																        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																																        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																																        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																																        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																																        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																																        d4DtataService.save(d4);
																																        kwhFlag = 0;
																																		maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																		pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																		
																																		kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																		kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																		kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																		kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																																 }
																															 }//If Previous Data Der
																															 
																															 else{
																																 
																																    int kwhFlag = 0;
																																	NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																	for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																	{
																																		String ipInterval = "0";
																																		int ipIntervalNum = 0;
																																		 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																		 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																		 {
																																			 if(subTempNode.hasAttributes()) 
																																    			{
																																    	 			NamedNodeMap nodeMap = subTempNode.getAttributes();
																																    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																    				{
																																    					Node node = nodeMap.item(nodeMapIndex);
																																    					ipInterval = node.getNodeValue();
																																    					//System.out.println(" IP interval attr name : " + node.getNodeName()+" attr value : " + ipInterval);
																																    																				    					
																																    				}
																																    			}
																																		 }
																																		 ipIntervalNum = Integer.parseInt(ipInterval);
																																		 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																		 boolean sumKwhFlag = true;
																																		 boolean sumKvaFlag = true;
																																		 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++) 
																																			{
																																			    String tagAttrId = "";
																																				String paramCode = "", paramValue = "";
																																				Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																				 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																				 {
																																					 if(subTempNodeIP.hasAttributes()) 
																																		    			{
																																						 NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																		    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																		    				{
																																		    					Node node = nodeMap.item(nodeMapIndex);
																																		    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																		    					{
																																		    						paramCode = node.getNodeValue();
																																		    					}
																																		    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																		    					{
																																		    						paramValue = node.getNodeValue();
																																		    					}
																																		    				}
																																						 
																																		    			}
																																					 
																																				 }
																																						//FOR HPL
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-6-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KwhValue = paramValue;
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 
																																					}
																																				//Load Utilization values
																																				 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																						 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																				 //HPL
																																				 if(paramCode.equalsIgnoreCase("P7-6-5-1-0"))
																																				 {
																																					 if( paramValue == "")
																																					 {
																																						 paramValue = "0";
																																					 }
																																					 float tempKva = Float.parseFloat(paramValue);
																																					 if(Integer.parseInt(intervalPeriod) > 15)
																																					 {
																																						 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																						 {
																																							 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																							 if(tempKva <= cd_20)
																																							 {
																																								 kva1_lt_cd20++;
																																							 }
																																							 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																							 {
																																								 kva1_lt_cd40++;
																																							 }
																																							 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																							 {
																																								 kva1_lt_cd60++;
																																							 }
																																							 else if(tempKva > cd_60)
																																							 {
																																								 kva1_gt_cd60++;
																																							 }
																																						 }
																																						 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																						 {
																																							 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																							 if(tempKva <= cd_20)
																																							 {
																																								 kva2_lt_cd20++;
																																							 }
																																							 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																							 {
																																								 kva2_lt_cd40++;
																																							 }
																																							 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																							 {
																																								 kva2_lt_cd60++;
																																							 }
																																							 else if(tempKva > cd_60)
																																							 {
																																								 kva2_gt_cd60++;
																																							 }
																																						 }
																																					 }
																																					 else{
																																						 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																						 {

																																							 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																							 if(tempKva <= cd_20)
																																							 {
																																								 kva1_lt_cd20++;
																																							 }
																																							 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																							 {
																																								 kva1_lt_cd40++;
																																							 }
																																							 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																							 {
																																								 kva1_lt_cd60++;
																																							 }
																																							 else if(tempKva > cd_60)
																																							 {
																																								 kva1_gt_cd60++;
																																							 }
																																						 
																																						 }
																																						 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																						 {

																																							 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																							 if(tempKva <= cd_20)
																																							 {
																																								 kva2_lt_cd20++;
																																							 }
																																							 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																							 {
																																								 kva2_lt_cd40++;
																																							 }
																																							 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																							 {
																																								 kva2_lt_cd60++;
																																							 }
																																							 else if(tempKva > cd_60)
																																							 {
																																								 kva2_gt_cd60++;
																																							 }
																																						 
																																						 }
																																					 }
																																				 }
																																				 
																																				 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																				 {
																																					 //System.out.println("====== "+Float.parseFloat(paramValue));
																																					if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																					
																																					 float tempPf = Float.parseFloat(paramValue);
																																						
																																					 sumPf = sumPf + tempPf;
																																					 d4PfValue = paramValue;
																																					 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																					 
																																					 if(tempPf == 0)
																																					 {
																																						pfNoLoad++; 
																																					 }
																																					 if(tempPf == -1)
																																					 {
																																						pfBlackOut++; 
																																					 }
																																					 if((tempPf != 0) && (tempPf != -1))
																																					 {
																																						 if(tempPf <= 0.5F)
																																						 {
																																							 pfLt05++;
																																						 }
																																						 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																						 {
																																							 pf05To07++;
																																							// pfVal1 = pfVal1 + tempPf + ",";
																																						 }
																																						 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																						 {
																																							 pf07To09++;
																																							 //pfVal2 = pfVal2 + tempPf + ",";
																																						 }
																																						 else if(tempPf >0.9F)
																																						 {
																																							 pfGt09++;
																																						 }
																																					 }
																																					 /*if(tempPf > maxPf)
																																					 {
																																						 maxPf = tempPf;
																																					 }*/
																																					
																																				 }
																																			}
																																		 
																																		 D4CdfData d4 = new D4CdfData();
																																		 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																		 d4.setIpInterval(Integer.parseInt(ipInterval));
																																		 if(dayProfileDate.length() > 2)
																																			{
																																			 String checkForDatePatter1 = dayProfileDate.substring(2);
																																			 if(checkForDatePatter1.startsWith("-"))
																																				{
																																				 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																				 
																																				}
																																				else if(checkForDatePatter1.startsWith("/"))
																																				{
																																					d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																					 
																																				}
																																				else
																																				{
																																					 dayProfileDate = "";
																																				}
																																			}
																																		 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 d4.setKvaValue(d4KvaValue);d4.setKwhValue(d4KwhValue);d4.setPfValue(d4PfValue);
																																		 d4.setIntervalPeriod(intervalPeriod);
																																		 d4LoadDataService.save(d4);
																																		 d4KvaValue = "";
																																		 d4KwhValue = "";
																																		 d4PfValue = "";
																																		 ipInterval = "";
																																	}
																																	//assign all variables to zero
																																	
																																	//Power Factor Report
																																	int intervalVal = Integer.parseInt(intervalPeriod);
																																	
																																	maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																	minKva = Math.round(minKva * 1000.0)/1000.0;
																																	sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																	sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																	sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																	
																																	D4Data d4 = new D4Data();
																																	d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																	if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter1 = dayProfileDate.substring(2);
																																	 if(checkForDatePatter1.startsWith("-"))
																																		{
																																		 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 
																																		}
																																		else if(checkForDatePatter1.startsWith("/"))
																																		{
																																			d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																			 
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																															        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																															        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																															        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																															        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																															        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																															        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																															        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																															        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																															        d4DtataService.save(d4);
																															        
																															        kwhFlag = 0;
																																	maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																	pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																	
																																	kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																	kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																	kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																	kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																																 
																															 }
																														}
																													 
																													 
																													}//End For Loop
																												 
																												 
																												 System.out.println("Insert into D4 completed");
																											 }
																										 }
																									 }
																								 }
																							 }
																						 }
																					}
																				}
																				
																			}
																		}
																	}
																	catch(Exception e)
																	{
																		e.printStackTrace();
																	}
																	 
																 }
																 else
																 {
																	 
																	 System.out.println("------D4 Data Present ---------");
																 }
																 //Duplicate Files Deleted
																 mainStatus = "duplicate";
																 System.out.println("metrnooo->"+meterNumber);
																 System.out.println("-----------Meter Already Exist--------");
																 String source = unZipFIlePath + "/"+filename;
																 File sourceFile = new File(source);
																 //System.out.println("4228 Source file delete : "+sourceFile.delete());
															 }
														}
													}
												 }
											 }//End D1 Tag
										 }
									 }
									 
								 }
							 }
						 }
					}
				}
				
			}
			
		}
		
	}
	
	catch(Exception e)
	{
		mainStatus="corrupted";
		System.out.println("metrnooo->"+meterNumber);
		e.printStackTrace();
		//return mainStatus;
	}
	
	return mainStatus+"/"+meterNumber;
}

}
