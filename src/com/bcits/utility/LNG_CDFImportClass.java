package com.bcits.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LNG_CDFImportClass 
{
	private static String path ="xml_files";
	//private static String path ="E://PRADEEPKUMAR C R//BSMARTMDM_SPACE//IMPORT//xml_files";
	static{
		try{DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());}
		catch (Exception e) {
			e.printStackTrace();
			writeLogFile("Error : "+e);
			System.exit(0);
		}

	}
	static Connection connection;
	static Document docForMetrNo=null;
	public static void main(String[] args) 
	{
				
		try
		{
	 
			System.out.println("Connecting to the database...");
			
			connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@192.168.1.22:1521:ORCL", "JCC", "jvvnlht192168");
					  //"jdbc:oracle:thin:@localhost:1521:orcl", "MDAS", "BCITS");
					//"jdbc:oracle:thin:@182.72.76.244:1521:ORCL", "BSMARTMDM", "bcits");
		
		String files = "", meterNumber = "", billMonth = "";
		int meterNoCount = 0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		System.out.println("No. files : "+listOfFiles.length);
		writeLogFile("No. of files : "+listOfFiles.length);
		writeLogFileNotImported("No. of files : "+listOfFiles.length);

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
		    {
		    	files = listOfFiles[i].getName();
		    	if (files.endsWith(".xml") || files.endsWith(".XML"))
		    	{
		    		//fileName = files;
		    		//System.out.println(files);
		    		//check meter number
		    		try{
		    			
		    			File fileForMetrNo = new File( path+"/"+files);
		    			DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    			docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
		    				new Runnable() {
		    			 		public void run() {
		    			 			runninthread(docForMetrNo);		
		    					}
		    				}.run();
		    		}catch (Exception e) 
						{
						e.printStackTrace();
						writeLogFile("Error : "+e);
						System.exit(0);
 					}
		    		//end of check meter number
		        }//if files.endsWith('xml)
		    }
		}
		
		System.out.println("-------------- end of file list");
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
			writeLogFile("Error : "+e);
			System.exit(0);
		}
		
	}
	
	public static void runninthread(Document docForMetrNo ) {
		
		Statement st =null;
		try{
		st= connection.createStatement();
		ResultSet rs = null, rsCount = null, rsForDateCheck = null;

		String meterno="",rdate="",kwh="",kwhunit="",kvh="",kvhunit="",kva="",kvaunit="",arb="",pf="",kwharb="",kvaarb="",kvharb="",pfarb="",flag1="0",flag2="0",flag3="0",flag4="0",flag5="0",flag6="0";
		String g2Value = "", g3Value = "";
		String d1_OccDate = "",d2_OccDate = "",d3_OccDate = "",d4_OccDate = "",d5_OccDate = "",d6_OccDate="";
		int status=0;
		
		String rdate_d2="",kwh_d2="",kwhunit_d2="",kvh_d2="",kvhunit_d2="",kva_d2="",kvaunit_d2="",arb_d2="",pf_d2="",kwharb_d2="",kvaarb_d2="",kvharb_d2="",pfarb_d2="",flag1_d2="0",flag2_d2="0",flag3_d2="0",flag4_d2="0",flag5_d2="0",flag6_d2="0";
		String rdate_d3="",kwh_d3="",kwhunit_d3="",kvh_d3="",kvhunit_d3="",kva_d3="",kvaunit_d3="",arb_d3="",pf_d3="",kwharb_d3="",kvaarb_d3="",kvharb_d3="",pfarb_d3="",flag1_d3="0",flag2_d3="0",flag3_d3="0",flag4_d3="0",flag5_d3="0",flag6_d3="0";
		String rdate_d4="",kwh_d4="",kwhunit_d4="",kvh_d4="",kvhunit_d4="",kva_d4="",kvaunit_d4="",arb_d4="",pf_d4="",kwharb_d4="",kvaarb_d4="",kvharb_d4="",pfarb_d4="",flag1_d4="0",flag2_d4="0",flag3_d4="0",flag4_d4="0",flag5_d4="0",flag6_d4="0";
		String rdate_d5="",kwh_d5="",kwhunit_d5="",kvh_d5="",kvhunit_d5="",kva_d5="",kvaunit_d5="",arb_d5="",pf_d5="",kwharb_d5="",kvaarb_d5="",kvharb_d5="",pfarb_d5="",flag1_d5="0",flag2_d5="0",flag3_d5="0",flag4_d5="0",flag5_d5="0",flag6_d5="0";
		String rdate_d6="",kwh_d6="",kwhunit_d6="",kvh_d6="",kvhunit_d6="",kva_d6="",kvaunit_d6="",arb_d6="",pf_d6="",kwharb_d6="",kvaarb_d6="",kvharb_d6="",pfarb_d6="",flag1_d6="0",flag2_d6="0",flag3_d6="0",flag4_d6="0",flag5_d6="0",flag6_d6="0";
		boolean d3_01_flag=false, d3_02_flag=false, d3_03_flag=false, d3_04_flag=false, d3_05_flag=false, d3_06_flag=false, d3_exist = false;

		String dateCount1 = "0", dateCount2 = "0", dateCount3 = "0", dateCount4 = "0", dateCount5 = "0", dateCount6 = "0";

		//for billMonth
		Date billDate = new Date();
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		String billMonth = sdfBillDate.format(billDate);
		//billMonth = "201306";
		int d4DayProfileCount = 0;
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(billDate); 
	    cal.add(Calendar.MONTH,-1);
		String dataMonth = sdfBillDate.format(cal.getTime());
		//dataMonth = "201305";
	    System.out.println("Month - 1 date is : " +dataMonth);

		try
		{
		    NodeList nodeList = docForMetrNo.getElementsByTagName("CDF");
			String meterclass="";
			for (int count = 0; count < nodeList.getLength(); count++) 
			{
				 Node tempNode = nodeList.item(count);
				 
					// make sure it's element node.
					if (tempNode.getNodeType() == Node.ELEMENT_NODE) 
					{
						// get node name and value
						//System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
						//System.out.println("Node Value =" + tempNode.getNodeValue());
				 
						if (tempNode.hasAttributes()) 
						{
				 			// get attributes names and values
							NamedNodeMap nodeMap = tempNode.getAttributes();
				 
							for (int i = 0; i < nodeMap.getLength(); i++) 
							{
				 				Node node = nodeMap.item(i);
								//System.out.println("attr name : " + node.getNodeName());
								//System.out.println("attr value : " + node.getNodeValue());
				 
							}
				 
						}
				 
						if (tempNode.hasChildNodes())
						{				 
							//System.out.println(" has child nodes level 1");
							NodeList subnodeList = tempNode.getChildNodes();
							
							for (int count1 = 0; count1 < subnodeList.getLength(); count1++) 
							{
								 Node tempNode1 = subnodeList.item(count1);
							 	if (tempNode1.getNodeType() == Node.ELEMENT_NODE) 
								{
									//System.out.println("\nNode Name =" + tempNode1.getNodeName() + " [OPEN]");
									//System.out.println("Node Value =" + tempNode1.getTextContent());
									if (tempNode1.hasChildNodes())
										{
								 			//System.out.println("has child nodes level 2");
											NodeList subnodeList1 = tempNode1.getChildNodes();//doc.getElementsByTagName("CDF");
											 
											for (int count12 = 0; count12 < subnodeList1.getLength(); count12++) 
											{
												 Node tempNode12 = subnodeList1.item(count12);
											 	if (tempNode12.getNodeType() == Node.ELEMENT_NODE) 
												{
													if (tempNode12.hasChildNodes())
													{
														String dataType = "";
														 
														
														if(tempNode12.getNodeName().equalsIgnoreCase("D1"))
														{
															//System.out.println("Node name : "+tempNode12.getNodeName());
															NodeList subnodeListD1 = tempNode12.getChildNodes();
														
															for (int countD1 = 0; countD1 < subnodeListD1.getLength(); countD1++) 
															{
																 Node tempNode123 = subnodeListD1.item(countD1);
															 	if (tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																{
																	String nodeName = tempNode123.getNodeName();
																	String nodeValue = tempNode123.getTextContent();
																	//System.out.println("\nNode Name =" + nodeName );
																	//System.out.println("Node Value =" + nodeValue);
																	String tagId = "";
																	if(nodeName.equalsIgnoreCase("G1"))
																	{
																		meterno=nodeValue;
																		System.out.println("meter no===========================>"+meterno);
																	}
																	
																	if(nodeName.equalsIgnoreCase("G2"))
																	{
																		g2Value = nodeValue;
																		System.out.println("G2 value ===========================>"+g2Value);
																	}

																	if(nodeName.equalsIgnoreCase("G3"))
																	{
																		g3Value = nodeValue;
																		System.out.println("G3 value ===========================>"+g3Value);
																	}

																	if(nodeName.equalsIgnoreCase("G13"))
																	{
																		meterclass = nodeValue;
																		System.out.println("meter not no===========================>"+meterclass);
																	}
																	
															 		
																}
															}
														}
														
														 
														else if(tempNode12.getNodeName().equalsIgnoreCase("D3"))
														{
															//System.out.println("Node name : D3 --"+tempNode12.getNodeName());
															d3_exist = true;
															NodeList subnodeListD3 = tempNode12.getChildNodes();
															
															Map<String , String> tagSubMaster = new HashMap<String, String>();
															
															 
															for (int countD3 = 0; countD3 < subnodeListD3.getLength(); countD3++) 
															{
																String d3TagCount = "", dateTime = "", mechanism = "", tagId = "", tagAttrId = "";
																String d3Id = "", d3AttrId = "", attrValue = "";
																
																 Node tempNode123 = subnodeListD3.item(countD3);
																 if (tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																 {
																	//String nodeName = tempNode123.getNodeName();
																	d3TagCount = tempNode123.getNodeName();
																	//String nodeValue = tempNode123.getTextContent();
																	//System.out.println("\nNode Name =" + d3TagCount );
																	//System.out.println("Node Value =" + nodeValue);
																	//String tagId = "";
																	
																	if (tempNode123.hasAttributes()) 
													    			{
													    	 			NamedNodeMap nodeMap = tempNode123.getAttributes();
													    				//String code = "",value = "", unit = "";
													    	 			
													    				for (int i = 0; i < nodeMap.getLength(); i++) 
													    				{
													    					Node node = nodeMap.item(i);
													    					//System.out.println("attr name : " + node.getNodeName());
													    					//System.out.println("attr value : " + node.getNodeValue());
													    					if(node.getNodeName().equalsIgnoreCase("DATETIME"))
													    						dateTime = node.getNodeValue();
													    					else if(node.getNodeName().equalsIgnoreCase("MECHANISM"))
													    						mechanism = node.getNodeValue();
													    																	    																	    					
													    				}
													    				String d3_01_dateTime="";
													    				if(d3TagCount.equalsIgnoreCase("D3-01"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate=dateTime;	
																			d3_01_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-02"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d2=dateTime;	
																			d3_02_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				
													    				else if(d3TagCount.equalsIgnoreCase("D3-03"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d3=dateTime;	
																			d3_03_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				
													    				else if(d3TagCount.equalsIgnoreCase("D3-04"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d4=dateTime;	
																			d3_04_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-05"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d5=dateTime;	
																			d3_05_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-06"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d6=dateTime;	
																			d3_06_flag = true;
													    					System.out.println("values are dates"+dateTime);
												    					}
													    				
													    				//System.out.println("code : "+code+" unit : "+unit+" value : "+value);
													    				
													    			}																	

												    				
												    				//////
																	//System.out.println("\n for sub D3 \n");
																	NodeList subTempNodeListD3 = tempNode123.getChildNodes();
																	for (int subCountD3 = 0; subCountD3 < subTempNodeListD3.getLength(); subCountD3++) 
																	{
																		 Node subTempNode123 = subTempNodeListD3.item(subCountD3);
																		 if (subTempNode123.getNodeType() == Node.ELEMENT_NODE) 
																		 {
																			String subNodeName = subTempNode123.getNodeName();//B1, B2 ..
																			String subNodeValue = subTempNode123.getTextContent();
																			//System.out.println("\nNode Name =" + subNodeName );
																			
																			 
																			tagAttrId = tagSubMaster.get(subNodeName);
																		 	
													    					int d3IdForAttribute = 0;
													    					 
													    					int tempindex=0;
													    					String code = "", value = "", unit = "",tod = "",occDate = "";
													    					
																			if(subTempNode123.hasAttributes()) 
															    			{
																				Map<String , String> tagMasterAttribute = new HashMap<String, String>();
																				 
																				
															    	 			NamedNodeMap nodeMap = subTempNode123.getAttributes();
															    				String attributeId = "", attributeValue = "";
															    				for (int i = 0; i < nodeMap.getLength(); i++) 
															    				{
															    					Node node = nodeMap.item(i);
															    					//System.out.println("attr name : " + node.getNodeName());
															    					//System.out.println("attr value : " + node.getNodeValue());
															    					
															    					
															    					if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						
															    						//System.out.println("am coming here"+tempindex++);
															    						
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																	    						code = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																	    						value = node.getNodeValue();
																	    					 
																	    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																	    						unit = node.getNodeValue();	
																	    					   // System.out.println("  code===============>"+code);  
																	    					    //System.out.println("  value===============>"+value);
																	    					   // System.out.println("unit value===============>"+unit);
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
																							else if(node.getNodeName().equalsIgnoreCase("OCCDATE"))
																	    						occDate = node.getNodeValue();	
																	    					
															    						}
															    						if(subNodeName.equalsIgnoreCase("B6"))
															    						{
															    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																	    						code = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																	    						value = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																	    						unit = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																	    						tod = node.getNodeValue();
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
															    			 	
															    					
															    					
															    					//for d3-02
															    					
															    					if(d3TagCount.equalsIgnoreCase("D3-02") || d3TagCount.equalsIgnoreCase("D3-03") || d3TagCount.equalsIgnoreCase("D3-04") || d3TagCount.equalsIgnoreCase("D3-05") || d3TagCount.equalsIgnoreCase("D3-05") || d3TagCount.equalsIgnoreCase("D3-06"))
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
																	    					    //System.out.println("  code===============>"+code);  
																	    					    //System.out.println("  value===============>"+value);
																	    					   // System.out.println("unit value===============>"+unit);
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
																							else if(node.getNodeName().equalsIgnoreCase("OCCDATE"))
																	    						occDate = node.getNodeValue();	
																	    					
															    						}
															    						if(subNodeName.equalsIgnoreCase("B6"))
															    						{
															    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																	    						code = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																	    						value = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																	    						unit = node.getNodeValue();
																	    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																	    						tod = node.getNodeValue();
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
															    					
															    					//End of d3-02
															    					
															    					
															    					
															    					attributeId = tagMasterAttribute.get(node.getNodeName());
															    					attributeValue = node.getNodeValue();
															    					 
															    				}
															    																			    			
														    					//d3TagCount subNodeName
															    				String kwhValue="",kvahValue="",kvaValueB6="",pfValue="",kvaValue="";
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							
														    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    							}
														    							System.out.println("kwhValue=D3-01=================>"+kwhValue);
														    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kwharb=code;
														    							}
														    					        System.out.println("kvahValue=D3-01=================>"+kvahValue);
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    							}
														    							System.out.println("kvaValue=D3-01=================>"+kvaValue);
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf=value;
														    								pfarb=code;
														    							}
														    							System.out.println("pfValue=D3-01=================>"+pfValue);
														    						}
														    						
														    					}
														    					else
														    					if(d3TagCount.equalsIgnoreCase("D3-02"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								System.out.println("kwhValue=D3-02=================>"+kwhValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								flag2_d2=value;
														    								kwharb_d2=code;
														    								System.out.println("kvahValue=D3-02=================>"+kvahValue);
														    							}
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
														    								System.out.println("kvavalue=D3-02=================>"+kvaValue);
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf_d2=value;
														    								pfarb_d2=code;
														    								System.out.println("pfValue=D3-02=================>"+pfValue);
														    							}
														    						}
														    					}
														    					else
														    					if(d3TagCount.equalsIgnoreCase("D3-03"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								System.out.println("kwhValue=D3-03=================>"+kwhValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								flag2_d3=value;
														    								kwharb_d3=code;
														    								System.out.println("kvahValue=D3-03=================>"+kvahValue);
														    							}
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								flag4_d3=value;
														    								kvaunit_d3=unit;
																							d3_OccDate=occDate;
														    								System.out.println("kvavalue=D3-03=================>"+kvaValue);
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf_d3=value;
														    								pfarb_d3=code;
														    								System.out.println("pfValue=D3-03=================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d4=unit;
															    								flag1_d4=value;
															    								kwh_d4= value;
															    								kwharb_d4=code;
															    								System.out.println("kwhValue=D3-04=================>"+kwhValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvhunit_d4=unit;
															    								flag2_d4=value;
															    								kwharb_d4=code;
															    								System.out.println("kvahValue=D3-04=================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								flag4_d4=value;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
															    								System.out.println("kvavalue=D3-04=================>"+kvaValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf_d4=value;
															    								pfarb_d4=code;
															    								System.out.println("pfValue=D3-04=================>"+pfValue);
															    							}
															    						}
															    					}
															    					else
																    					if(d3TagCount.equalsIgnoreCase("D3-05"))
																    					{
																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d5=unit;
																    								flag1_d5=value;
																    								kwh_d5= value;
																    								kwharb_d5=code;
																    								System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																    							
																    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d5= value;
																    								kvhunit_d5=unit;
																    								flag2_d5=value;
																    								kwharb_d5=code;
																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																    						 	
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d5=value;
																    								kvaarb_d5=code;
																    								flag4_d5=value;
																    								kvaunit_d5=unit;
																									d5_OccDate = occDate;
																    								System.out.println("kva value==================>"+kvaValue);
																    							}
																    							
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								pf_d5=value;
																    								pfarb_d5=code;
																    								System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
																    					}
																    					else
																	    					if(d3TagCount.equalsIgnoreCase("D3-06"))
																	    					{
																	    						if(subNodeName.equalsIgnoreCase("B3"))
																	    						{
																	    							if(code.equalsIgnoreCase("P7-1-18-2-0"))
																	    							{
																	    								arb=code;
																	    								kwhValue = value;
																	    								kwhunit_d6=unit;
																	    								flag1_d6=value;
																	    								kwh_d6= value;
																	    								kwharb_d6=code;
																	    								System.out.println("kwhValue=D3-06=================>"+kwhValue);
																	    							}
																	    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d6= value;
																	    								kvhunit_d6=unit;
																	    								flag2_d6=value;
																	    								kwharb_d6=code;
																	    								System.out.println("kvahValue=D3-06=================>"+kvahValue);
																	    							}
																	    							
																	    						}
																	    						if(subNodeName.equalsIgnoreCase("B5"))
																	    						{
																	    							if(code.equalsIgnoreCase("P7-6-13-0-0"))
																	    							{
																	    								arb=code;
																	    								kvaValue = value;
																	    								kva_d6=value;
																	    								kvaarb_d6=code;
																	    								flag4_d6=value;
																	    								kvaunit_d6=unit;
																										d6_OccDate = occDate;
																	    								System.out.println("kvavalue=D3-06=================>"+kvaValue);
																	    							}
																	    						}
																	    						 
																	    						if(subNodeName.equalsIgnoreCase("B9"))
																	    						{
																	    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																	    							{
																	    								arb=code;
																	    								pfValue = value;
																	    								pf_d6=value;
																	    								pfarb_d6=code;
																	    								System.out.println("pfValue=D3-06=================>"+pfValue);
																	    							}
																	    						}
																	    					}
														    																				    				
															    			}
																		 }
																	}
																}
															}
																																												
														}//end of D3
														else if(tempNode12.getNodeName().equalsIgnoreCase("D4"))
														{								
															NodeList subnodeListD4 = tempNode12.getChildNodes();
															
															if (tempNode12.hasAttributes()) 
															{
																NamedNodeMap nodeMap = tempNode12.getAttributes();
																//String code = "",value = "", unit = "";
																for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																{
																	Node node = nodeMap.item(nodeMapIndex);
																	//intervalPeriod = node.getNodeValue();
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
																	//System.out.println("\n Day profile Node Name =" + nodeName +" Node Value =" + nodeValue);
																	if (tempNodeD4.hasAttributes()) 
																	{
																		NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																		
																		for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																		{
																			Node node = nodeMap.item(nodeMapIndex);
																			dayProfileDate = node.getNodeValue();
																			//System.out.println(" profile date attr name : " + node.getNodeName()+" attr value : " + dayProfileDate);
																			
																			String profileDateYearMonth = "";
																			rsForDateCheck = st.executeQuery("SELECT TO_CHAR(ADD_MONTHS(TO_DATE('"+dayProfileDate+"','DD-MM-YYYY'),1),'YYYYMM') FROM DUAL");
																			if(rsForDateCheck.next())
																			{
																				profileDateYearMonth = rsForDateCheck.getString(1);
																			}
																			rsForDateCheck.close();
																			
																			
																			if(billMonth.equalsIgnoreCase(profileDateYearMonth))
																			{
																				d4DayProfileCount++;
																				//System.out.println("Day profile : " + dayProfileDate);
																			}
																						
																				
																																																																										
																		}													    				
																	}
																 }
															}
														}//end of D4
																											 
													}
													
												}
											}
								 
										}
									
								}
							}
				 
						}

						// temporary 
						int month=0;
						String DATE_FORMAT = "dd-MM-yyyy H:mm";
					    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
					    
					    String DATE_FORMAT1 = "dd-MMM-yyyy";
					    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(DATE_FORMAT1);
					    
					  //current Date
						Date currentDate = new Date();
			    		String current_date =  sdf1.format(currentDate);
						
					  //End of Getting current date

					  //D3 tag not present
						if(!d3_exist)
						{
							ResultSet rsForD3 = null;
							System.out.println("D3 tag doesnt exist"+meterno);
							writeLogFile("-------------- D3 tag doesnt exist"+meterno);
							writeLogFileNotImported("-------------- D3 tag doesnt exist"+meterno);
								
							try
							{
								rsForD3=st.executeQuery("SELECT * FROM XMLIMPORT WHERE METERNO='"+meterno+"' AND DATESTAMP='"+current_date+"'");
								if(!rsForD3.next())
								{
									int importStatus=	st.executeUpdate("INSERT INTO XMLIMPORT(METERNO, DATESTAMP, G2VALUE, G3VALUE) values('"+meterno+"','"+current_date+"','"+g2Value+"','"+g3Value+"')");
									if(importStatus > 0 )
									{
										System.out.println("G2 and G3 values inserted successfully");
									}
									else
									{
										System.out.println("G2 and G3 values insertion failed");
									}
								
								}
								else
								{
									System.out.println("G2 and G3 values already exist");
								}
								rsForD3.close();
								
							}
							catch (Exception e)
							{
								e.printStackTrace();
								writeLogFile("Error : "+e);
								System.exit(0);
							}

						}

					    Calendar c1 = Calendar.getInstance();
						String monthyear1="", monthyear2="", monthyear3="", monthyear4="", monthyear5="", monthyear6="";
						String yyyyMM1 = "",yyyyMM2 = "",yyyyMM3 = "",yyyyMM4 = "",yyyyMM5 = "",yyyyMM6 = "";
						//Date for d3-01
						if(d3_01_flag && (rdate != ""))
						{
							Date date = (Date)sdf.parse(rdate);
							//Calendar c1 = Calendar.getInstance();
							c1.setTime(date); 
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
							c1.add(Calendar.MONTH,+1);
							monthyear1=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
							System.out.println("monthyear is : "+monthyear1);
							c1.add(Calendar.MONTH,-1);
							System.out.println("Date + 1 month is : " + sdf.format(c1.getTime()));
							String rdate_substracted=sdf.format(c1.getTime());

							 yyyyMM1 = sdfBillDate.format(date);
							System.out.println("rDate yyyyMM : "+yyyyMM1);
							if(dataMonth.equalsIgnoreCase(yyyyMM1))
							{
								dateCount1 = d4DayProfileCount + "";
							}
					    }
						//end
					    
					    //Date for d3-02
						if(d3_02_flag && (rdate_d2 != ""))
						{
							Date date2 = (Date)sdf.parse(rdate_d2);
							c1.setTime(date2); 
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
							monthyear2=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
							c1.add(Calendar.MONTH,-1);
							String rdate_substracted_d2=sdf.format(c1.getTime());

							 yyyyMM2 = sdfBillDate.format(date2);
							System.out.println("rDate2 yyyyMM : "+yyyyMM2);
							if(dataMonth.equalsIgnoreCase(yyyyMM2))
							{
								dateCount2 = d4DayProfileCount + "";
							}
						}
					    //End
					    
					  //Date for d3-03
						if(d3_03_flag && (rdate_d3 != ""))
						{
							Date date3 = (Date)sdf.parse(rdate_d3);
							c1.setTime(date3); 
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
							monthyear3=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
							c1.add(Calendar.MONTH,-1);
							String rdate_substracted_d3=sdf.format(c1.getTime());

							 yyyyMM3 = sdfBillDate.format(date3);
							System.out.println("rDate3 yyyyMM : "+yyyyMM3);
							if(dataMonth.equalsIgnoreCase(yyyyMM3))
							{
								dateCount3 = d4DayProfileCount + "";
							}
						}
					    //End
					    
					  //Date for d3-04
						if(d3_04_flag && (rdate_d4 != ""))
						{
							Date date4 = (Date)sdf.parse(rdate_d4);
							c1.setTime(date4); 
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
						   
							monthyear4=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
						   
							 yyyyMM4 = sdfBillDate.format(date4);
							System.out.println("rDate4 yyyyMM : "+yyyyMM4);
							if(dataMonth.equalsIgnoreCase(yyyyMM4))
							{
								dateCount4 = d4DayProfileCount + "";
							}
					    }
					    //End
					    
					    //Date for d3-05
						if(d3_05_flag && (rdate_d5 != ""))
						{
							Date date5 = (Date)sdf.parse(rdate_d5);
							c1.setTime(date5);
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
							
							monthyear5=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
							c1.add(Calendar.MONTH,-1);
							String rdate_substracted_d5=sdf.format(c1.getTime());

							yyyyMM5 = sdfBillDate.format(date5);
							System.out.println("rDate5 yyyyMM : "+yyyyMM5);
							if(dataMonth.equalsIgnoreCase(yyyyMM5))
							{
								dateCount5 = d4DayProfileCount + "";
							}
					    }
					    //End
					    
					  //Date for d3-06
						if(d3_06_flag && (rdate_d6 != ""))
						{
							Date date6 = (Date)sdf.parse(rdate_d6);
							c1.setTime(date6); 
							c1.add(Calendar.MONTH,+1);
							if(c1.get(Calendar.MONTH)==0)
							{
								month=12;
								c1.add(Calendar.YEAR,-1);
								
							}
							else
							month=(c1.get(Calendar.MONTH)==0)?12:c1.get(Calendar.MONTH);
						   
							monthyear6=c1.get(Calendar.YEAR)+""+(month<10? "0" : "") + month;
							c1.add(Calendar.MONTH,-1);
							String rdate_substracted_d6=sdf.format(c1.getTime());

							 yyyyMM6 = sdfBillDate.format(date6);
							System.out.println("rDate6 yyyyMM : "+yyyyMM6);
							if(dataMonth.equalsIgnoreCase(yyyyMM6))
							{
								dateCount6 = d4DayProfileCount + "";
							}
						}
					    System.out.println("dateCount : "+dateCount1+" - "+dateCount2+" - "+dateCount3+" - "+dateCount4+" - "+dateCount5+" - "+dateCount6);
					    //End
					    
					    //checking wheather the data already exists or not
					    ResultSet rset=null;
					    try{
							if(d3_01_flag && (rdate != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh+"','"+kwhunit+"','"+kvh+"','"+kvhunit+"','"+kva+"','"+kvaunit+"','"+kwharb+"','"+pf+"','"+kvaarb+"','"+kvharb+"','"+pfarb+"','"+yyyyMM1+"','"+current_date+"','"+dateCount1+"','"+d1_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					    
					    try{
							if(d3_02_flag && (rdate_d2 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d2+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate_d2+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh_d2+"','"+kwhunit_d2+"','"+kvh_d2+"','"+kvhunit_d2+"','"+kva_d2+"','"+kvaunit_d2+"','"+kwharb_d2+"','"+pf_d2+"','"+kvaarb_d2+"','"+kvharb_d2+"','"+pfarb_d2+"','"+yyyyMM2+"', '"+current_date+"','"+dateCount2+"','"+d2_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					    
					    try{
							if(d3_03_flag && (rdate_d3 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d3+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate_d3+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh_d3+"','"+kwhunit_d3+"','"+kvh_d3+"','"+kvhunit_d3+"','"+kva_d3+"','"+kvaunit_d3+"','"+kwharb_d3+"','"+pf_d3+"','"+kvaarb_d3+"','"+kvharb_d3+"','"+pfarb_d3+"','"+yyyyMM3+"', '"+current_date+"','"+dateCount3+"','"+d3_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    	 
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					   
					    try{
							if(d3_04_flag && (rdate_d4 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d4+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	 status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate_d4+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh_d4+"','"+kwhunit_d4+"','"+kvh_d4+"','"+kvhunit_d4+"','"+kva_d4+"','"+kvaunit_d4+"','"+kwharb_d4+"','"+pf_d4+"','"+kvaarb_d4+"','"+kvharb_d4+"','"+pfarb_d4+"','"+yyyyMM4+"', '"+current_date+"','"+dateCount4+"','"+d4_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    	 
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					    
					    try{
							if(d3_05_flag && (rdate_d5 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d5+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate_d5+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh_d5+"','"+kwhunit_d5+"','"+kvh_d5+"','"+kvhunit_d5+"','"+kva_d5+"','"+kvaunit_d5+"','"+kwharb_d5+"','"+pf_d5+"','"+kvaarb_d5+"','"+kvharb_d5+"','"+pfarb_d5+"','"+yyyyMM5+"', '"+current_date+"','"+dateCount5+"','"+d5_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    	 
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					    
					    try{
							if(d3_06_flag && (rdate_d6 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d6+"','DD-MM-YYYY HH24:MI:SS') and METERNO='"+meterno+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate_d6+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh_d6+"','"+kwhunit_d6+"','"+kvh_d6+"','"+kvhunit_d6+"','"+kva_d6+"','"+kvaunit_d6+"','"+kwharb_d6+"','"+pf_d6+"','"+kvaarb_d6+"','"+kvharb_d6+"','"+pfarb_d6+"','"+yyyyMM6+"','"+current_date+"','"+dateCount6+"','"+d6_OccDate+"','"+g2Value+"','"+g3Value+"')");
					    	 
					    }
						rset.close();
						}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}  
					 	
						 System.out.println("updated succesfully=========================>"+status);
					 	//rset.close();
						
					}
				}
			
			 if(status==1)
			 {
				 System.out.println("Data has been uploaded successfully for meter number : "+meterno);
				 writeLogFile("-------------- Data has been uploaded successfully for meter number : "+meterno);
			 }	  
			 else
			 {
				 System.out.println("Data Could not be uploaded for meter number : "+meterno);
				 writeLogFile("-------------- Data Could not be uploaded for meter number : "+meterno);
				 writeLogFileNotImported("-------------- Data Could not be uploaded for meter number : "+meterno);
			 }
				 
		    	
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
			writeLogFile("Error : "+e);
			System.exit(0);
		}
		}catch (Exception e) 
			{
			e.printStackTrace();
			writeLogFile("Error : "+e);
			System.exit(0);
		}finally{
			if(st!=null)
			try{	st.close();}catch (Exception e2) 
				{
				e2.printStackTrace();
			writeLogFile("Error : "+e2);
			System.exit(0);
			}
		}
	
	}
	
	
	public static void writeLogFile(Object str){
		  File f=null;
		  FileOutputStream out=null; 
		  PrintStream p=null; 
		  System.out.println("LOG:::"+str);
		  try{
		   f=new File("Log_file/MIPBillingErrorsLog.txt");
		  // f=new File("E:/PRADEEPKUMAR C R/BSMARTMDM_SPACE/IMPORT/Log_file/MIPBillingErrorsLog.txt");
			
		   out = new FileOutputStream(f,true);
		   p = new PrintStream( out );
		   p.println(str);
		  }catch(Exception e){
		   p.println("Exception in creating file "+e);
		  }
		  p.flush();
		  p.close();
	}

	public static void writeLogFileNotImported(Object str){
		  File f=null;
		  FileOutputStream out=null; 
		  PrintStream p=null; 
		  System.out.println("LOG:::"+str);
		  try{
		   f=new File("Log_file/MIPBillingErrorsLogNotImported.txt");
		   //f=new File("E:/PRADEEPKUMAR C R/BSMARTMDM_SPACE/IMPORT/Log_file/MIPBillingErrorsLogNotImported.txt");
		   out = new FileOutputStream(f,true);
		   p = new PrintStream( out );
		   p.println(str);
		  }catch(Exception e){
		   p.println("Exception in creating file "+e);
		  }
		  p.flush();
		  p.close();
	}
	
}
