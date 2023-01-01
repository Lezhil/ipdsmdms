package com.bcits.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.lingala.zip4j.core.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import com.bcits.entity.MobileGenStatusEntity;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MobileGenStatusService;
import com.bcits.service.ParseSchedulerService;
import com.bcits.service.SdoJccService;
import com.bcits.service.UserAccessTypeService;
import com.bcits.service.UserService;

@Controller
public class ParseSchedulerController {



	
	@Autowired
	private MasterService masterService;
	@Autowired
	private SdoJccService sdoJccService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private ParseSchedulerService parseSchedulerService;
	
	@Autowired
	private UserAccessTypeService userAccessTypeService;
	
	@Autowired
	private MobileGenStatusService mobileGenStatusService;
	
	@Autowired
	private UserService userService;
	
	
	String uploadPath="";
	String unZipPath="";
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,Model model)
    {
		//HttpSession session=request.getSession(false);
		// userName=(String) session.getAttribute("username");
		//System.out.println("userName-->"+userName);
		System.out.println("enter to upload file controller");
		Document docForMetrNo=null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String billmonth = request.getParameter("billmonth");//sdf.format(d);
			System.out.println("billMonth is : "+billmonth);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");
			
			System.out.println("MultipartFile myFile " +myFile);
			
			String fileName  = myFile.getOriginalFilename();
			
			System.out.println("fileName=="+fileName);
			
			String extension = "";
			//String meterNumber = "";
			int dotIndex = fileName.lastIndexOf(".");
			extension = fileName.substring(dotIndex+1);
			System.out.println("File name : "+fileName + " extension : "+extension);
			
			//create folder for zip
			File folder1 = new File(uploadPath);
			if(!folder1.exists())
			{
				folder1.mkdir();
			}
			//create unzip folder
			
			File folder2 = new File(unZipPath);
			
			System.out.println("folder2==>"+folder2);
			if(!folder2.exists())
			{
				System.out.println("folder not present");
				folder2.mkdir();
			}
			else
			{
				System.out.println("folder-------> exist");
			}
			
			if(!extension.equalsIgnoreCase("zip"))
			{
				model.addAttribute("result","Invalid File Type");
			
			}
			else{
				String filePath = uploadPath;
				if(!fileName.equals(""))
				{  
					System.out.println("Server path:" +filePath+" fileName : "+fileName);
					//Create file
					File fileToCreate = new File(filePath, fileName);
					//If file does not exists create file  
					
						FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	
						fileOutStream.write(myFile.getBytes());
	
						fileOutStream.flush();
	
						fileOutStream.close();
					
				}
				System.out.println("end of upload before try block");
				
				try
			     {
			    	Date dt = new Date();
			 		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd_kk_mm_ss");
			 		String currentTime = sdf1.format(dt);
			    	 
			    	//create output directory is not exists
			    	//File folder = new File(getServlet().getServletContext().getRealPath("/") +"/unzipped");
			    	
			    	// Initiate ZipFile object with the path/name of the zip file.
					ZipFile zipFile = new ZipFile(uploadPath+"/"+fileName);//getServlet().getServletContext().getRealPath("/") +"/uploads/"+fileName);
					
					// Extracts all files to the path specified
					
					zipFile.extractAll(unZipPath + "\\" + currentTime);//getServlet().getServletContext().getRealPath("/") +"/unzipped");
				
					String[] fname = fileName.split("\\.");
					//System.out.println("fname-->"+fname);
			    	System.out.println("Done");
			    	
			    	/*System.out.println("UnZipFolderPath=="+currentTime);
			    	System.out.println("UnZipFilename=="+fname[0]);
			    	System.out.println("filename=="+fileName);
			    	System.out.println("billmonth==="+billmonth);*/
			    	
			    	model.addAttribute("UnZipFolderPath",currentTime);
			    	model.addAttribute("UnZipFilename",fname[0]);
			    	model.addAttribute("filename",fileName);
			    	model.addAttribute("billmonth",billmonth);
			    	
			    	String files = "";
			    	String msg="";
			    	String unZipFolderPath = unZipPath+"/"+currentTime+"/"+fname[0];
			    	String path = unZipFolderPath;
			    	System.out.println("path-->"+path);
					File folder = new File(path);
					File[] listOfFiles = folder.listFiles();
					
					if(listOfFiles != null)
					{
						for(int i =0;i < listOfFiles.length;i++)
						{
							if(listOfFiles[i].isFile())
							{
								files = listOfFiles[i].getName();
								if(!(files.endsWith(".xml") ||files.endsWith(".XML")|| files.endsWith(".xml_mobile")||files.endsWith(".XML_mobile")))
								{
									 msg="error";
								}
							}
						}
						if("error".equalsIgnoreCase(msg))
						{
							model.addAttribute("result","Uploaded File contains other than  XML files.");
						}
						else
						{
							model.addAttribute("parse","parse");
							model.addAttribute("result","File Uploaded Successfully..");
						}
						
					}
					else
					{
						
						model.addAttribute("result","No files..");
					}

			    }
			    catch(Exception ex)
			    {
			       ex.printStackTrace(); 
			    }
			
				System.out.println("end of upload");
				model.addAttribute("selectedMonth",billmonth);
				
			}
		    /*DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    docForMetrNo = dBuilderForMetrNo.parse(myFile.getInputStream());*/
		    //userService.parseTheFile(docForMetrNo, billmonth,model);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "cdfimportmanager";
	}
	
	
	
	//@Scheduled(cron="0 45 22 * * ?")	
	public void  parseFile()
	{
		System.out.println("inside parse mobile scheduler");
	    String unZipFolderPath=AmrController.meterFileFolder;
	    System.out.println("unZipFolderPath-->"+unZipFolderPath);
		Date date1=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		String bilmonth=sdf.format(date1);
		System.out.println(bilmonth);
		//String unZipFolderPath="E:/MOBILE FILES/ALL";
		this.getUnzippedXmlsMobiles(unZipFolderPath, bilmonth);
	// List timeStatus = getUnzippedXmls(unZipFolderPath, billmonth,model);
	}
	
	
	 public  void getUnzippedXmlsMobiles(String unZipFolderPath, String billMonth) 
		{
		 System.out.println("-----inside getUnzippedXmlsMobiles ");
		 
		 ModelMap model=new ModelMap();
			double lIteratorDifference=0;
			String files = "";
			List parseMainStatus=new ArrayList<>();
			try
			{
				System.out.println("-----inside 1 ");
				double lIteratorStartTime = new Date().getTime();
				//System.out.println("lIteratorStartTime--->"+lIteratorStartTime);
				double mainTime = lIteratorStartTime;
				Document docForMetrNo=null;
				String path = unZipFolderPath;
				File folder = new File(path);
				
				File[] listOfFiles = folder.listFiles();
				System.out.println("No. files : "+listOfFiles);
				if(listOfFiles != null)
				{
					
					String parseXml = "";String duplicate = "";String meterNotExist = ""; String corruptedFile="";
					int	parsedCount = 0;int duplicateCount = 0;int meterNotExistCount = 0;int corruptedFileCount=0;
					//System.out.println("-----inside 2 ");
					for(int i =0;i < listOfFiles.length;i++)
					{
						//System.out.println("-----inside 3 ");
						if(listOfFiles[i].isFile())
						{
							
							//System.out.println("-----inside 4 ");
							files = listOfFiles[i].getName();
							
							String extension="";
							int dotIndex = files.lastIndexOf(".");
							extension = files.substring(dotIndex+1);
								
							
								
							//if(files.endsWith(".xml") || files.endsWith(".XML") )
							//if(extension.equalsIgnoreCase("xml_mobile"))
							if(extension.equalsIgnoreCase("xml_mobile"))
							{
								//System.out.println("-----inside 5 ");
								System.out.println("----- file name is : "+files);
								File fileForMetrNo = new File( path+"/"+files);
								DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
								try
								{
									docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
								}
								catch(SAXParseException e)
								{
									docForMetrNo = null;
								}
				    			
				    			System.out.println("file path-->"+path);
				    			String status=parseSchedulerService.parseTheMobileFile(docForMetrNo, billMonth, model, path, files);
				    			String[] st=status.split("/");
				    			System.out.println("statusss=="+status);
				    			System.out.println("st[0]--"+st[0]);
				    			System.out.println("st[1]--"+st[1]);
				    			
				    			MobileGenStatusEntity mobileStatus=new MobileGenStatusEntity();
				    			System.out.println("----- status namestatusstatusstatusstatus is : "+st[0]);
				    			if(st[0].equalsIgnoreCase("parsed"))
				    			{
				    				//System.out.println("meterno==>"+);
				    				parsedCount++;
				    				parseXml = parseXml + files + "<br/>";
				    			
				    				mobileStatus.setStatus("parsed");
				    				
				    			}
				    			else if(st[0].equalsIgnoreCase("meterDoesNotExist"))
					    			{
				    				    meterNotExistCount++;
					    				meterNotExist = meterNotExist + files + "<br/>";
					    				
					    				mobileStatus.setStatus("MtrNotExist");
					    				
					    			}
				    			else if(st[0].equalsIgnoreCase("duplicate"))
				    			{
				    				duplicateCount++;
				    				duplicate = duplicate + files + "<br/>";
				    				
				    				mobileStatus.setStatus("Duplicate");
				    				
				    			}
				    			else if(st[0].equalsIgnoreCase("corrupted"))
				    			{
				    				corruptedFileCount++;
				    				corruptedFile = corruptedFile + files + "<br/>";
				    				
				    				mobileStatus.setStatus("Corrupted");
				    				
				    			}
				    			
				    			mobileStatus.setBillmonth(billMonth);
			    				mobileStatus.setMeterno(st[1]);
			    				mobileStatus.setCreatedate(new Date());
			    				mobileStatus.setFilename(files);
			    				mobileStatus.setFilepath(path+"/"+files);
				    			mobileGenStatusService.save(mobileStatus);
				    			
				    			/*String time1="";
				    			String mainStatus = parseXml + "$" + duplicate + "$" + meterNotExist + "$" + corruptedFile;
				    			String countStatus = parsedCount + "$" +duplicateCount+ "$" +meterNotExistCount+ "$" +corruptedFileCount;
				    			
				    			mainStatus = mainStatus + "@" + countStatus + "@"+time1;
				    			parseMainStatus.add(mainStatus);
				    			parseXml = "";duplicate = "";meterNotExist = ""; corruptedFile="";
				    			parsedCount = 0;duplicateCount = 0;meterNotExistCount = 0;mainTime = 0;corruptedFileCount=0;
				    			//System.out.println(" parsed : "+parseXml);
				    			System.out.println(fileForMetrNo);*/
				    			
				    			File RenameFile = new File( path+"/"+files+"_parsed");
				    			System.out.println("RenameFile--"+RenameFile);
				    			fileForMetrNo.renameTo(RenameFile);
				    			System.out.println("fileForMetrNo--"+fileForMetrNo);
							}
							else
							{
								model.addAttribute("result","No files..");
							}
							
							
						}
					}
				}
				
				/*System.out.println("-------------- end of file list");
				double lIteratorEndTime = new Date().getTime();
				System.out.println("lIteratorEndTime--->"+lIteratorEndTime);
				 lIteratorDifference = lIteratorEndTime - lIteratorStartTime;
				System.out.println("Time taken in milliseconds: " + lIteratorDifference);
				parseMainStatus.add(lIteratorDifference);*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	
	//parsing cmri files
	
	//@Scheduled(cron="0 50 20 * * ?")
	public void  parseCMRIXMLFile()
	{
		System.out.println("inside parse scheduler parseCMRIXMLFile");
		Date date1=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		String bilmonth=sdf.format(date1);
		System.out.println(bilmonth);
		String unZipFolderPath="C:/jboss-as-7.1.1.Final/bin/CDF_unzippedFiles";
		this.getUnzippedXmls(unZipFolderPath, bilmonth);
	}
	
	public  List getUnzippedXmls(String unZipFolderPath, String billMonth) 
	{
		ModelMap model=new ModelMap();
	 System.out.println("-----inside getUnzippedXmls cmri shceduler ");
		double lIteratorDifference=0;
		String files = "";
		List parseMainStatus=new ArrayList<>();
		try
		{
			System.out.println("-----inside 1 ");
			double lIteratorStartTime = new Date().getTime();
			//System.out.println("lIteratorStartTime--->"+lIteratorStartTime);
			double mainTime = lIteratorStartTime;
			Document docForMetrNo=null;
			String path = unZipFolderPath;
			System.out.println("path name--->"+path);
			
			File folder = new File(path);
			
			File[] listOfFiles = folder.listFiles();
			
			
			System.out.println("No. files : "+listOfFiles.length);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd"); // declare as DateFormat
			Calendar today = Calendar.getInstance();
			Calendar yesterday = Calendar.getInstance();
			Calendar yesterday2 = Calendar.getInstance();
			yesterday.add(Calendar.DATE, -1);
			yesterday2.add(Calendar.DATE,-9);
			Date d1 = yesterday.getTime(); // get a Date object
			Date d2 = yesterday2.getTime();
			String yesDate = sdf1.format(d1); // toString for Calendars is mostly not really useful
			String yesDate2 = sdf1.format(d2);
			//System.out.println("yesDate-->"+yesDate);
			System.out.println("yesDate1-->"+yesDate2);
		
			 System.out.println("No Of Folders length->" + listOfFiles.length);
			 for (int i = 0; i < listOfFiles.length; i++) 
			    {
			       if (listOfFiles[i].isDirectory()) 
			      {
			       System.out.println("inside1 Directory ->"+i+ " Name " + listOfFiles[i].getName());
			     // System.out.println("yesDate1-->"+yesDate1+"folder date-->"+listOfFiles[i].getName().substring(0,10));
			       // if(yesDate2.equals(listOfFiles[i].getName().substring(0,10)))
			        //{
			        File[] listOfFiles2 = listOfFiles[i].listFiles();
			      //  System.out.println("inside 1 subdirectory length->"+listOfFiles2.length);
			        for (int j = 0; j < listOfFiles2.length; j++) 
				    {
			        	 if (listOfFiles2[j].isDirectory()) 
			        	 {
			        	  System.out.println("inside2 subdirectory ->"+j+ " Name " +listOfFiles2[j].getName());
			        		  File[] listOfFiles3 = listOfFiles2[j].listFiles();
			        	 System.out.println("inside2 SubDirectory listOfFiles3-->length" + listOfFiles3.length);
			        	 
			        		 for (int k = 0; k < listOfFiles3.length; k++) 
			        		 {
			        			 System.out.println("listOfFiles3[k].getName()-->"+listOfFiles3[k].getName());
			        			 String parseXml = "";String duplicate = "";String meterNotExist = ""; String corruptedFile="";
			     				int	parsedCount = 0;int duplicateCount = 0;int meterNotExistCount = 0;int corruptedFileCount=0;
					        	if (listOfFiles3[k].isFile())
					        	{
					        		try {

						        		//System.out.println("listOfFiles3[k].getName()-->"+listOfFiles3[k].getName());
										//System.out.println("-----inside 4 ");
										
										String extension = "";
										//String meterNumber = "";
										files = listOfFiles3[k].getName();
										int dotIndex = files.lastIndexOf(".");
										extension = files.substring(dotIndex+1);
											
										
											
											//if(files.endsWith(".xml") || files.endsWith(".XML") ||files.endsWith(".xml_cmri" )||files.endsWith(".XML_CMRI" )||files.endsWith(".XML_cmri" ))
											if(extension.equalsIgnoreCase("xml_cmri"))
												
											{
												System.out.println("----- file name is : "+files);
												
												String ExactPath=path+"/"+listOfFiles[i].getName()+"/"+listOfFiles2[j].getName()+"/"+files;
												File fileForMetrNo = new File(ExactPath);
												DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
												try
												{
													docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
												}
												catch(SAXParseException e)
												{
													docForMetrNo = null;
												}
								    			//System.out.println("unzipfolderpath---->"+unZipFolderPath);
												
												String status = userService.parseTheFile(docForMetrNo, billMonth,model, unZipFolderPath,files).trim();
								    			
								    			String[] st=status.split("/");
								    			System.out.println("st length-->"+st.length);
								    			
								    			if(st.length==2)
								    			{
								    			System.out.println("statusss=="+status);
								    			//System.out.println("st[0]--"+st[0]);
								    			//System.out.println("st[1]--"+st[1]);
								    			//userService.importBillingParameters(docForMetrNo);
								    			MobileGenStatusEntity mobileStatus=new MobileGenStatusEntity();
								    			System.out.println("----- status namestatusstatusstatusstatus is : "+st[0]);
								    			if(st[0].equalsIgnoreCase("parsed"))
								    			{
								    				
								    				parsedCount++;
								    				parseXml = parseXml + files + "<br/>";
								    				mobileStatus.setStatus("parsed");
								    			}
								    			else if(st[0].equalsIgnoreCase("meterDoesNotExist"))
									    			{
								    				    meterNotExistCount++;
									    				meterNotExist = meterNotExist + files + "<br/>";
									    				mobileStatus.setStatus("MtrNotExist");
									    				
									    			}
								    			else if(st[0].equalsIgnoreCase("duplicate"))
								    			{
								    				duplicateCount++;
								    				duplicate = duplicate + files + "<br/>";
								    				mobileStatus.setStatus("Duplicate");
								    				
								    			}
								    			else if(st[0].equalsIgnoreCase("corrupted"))
								    			{
								    				corruptedFileCount++;
								    				corruptedFile = corruptedFile + files + "<br/>";
								    				mobileStatus.setStatus("Corrupted");
								    				
								    			}
								    			
								    			mobileStatus.setBillmonth(billMonth);
							    				mobileStatus.setMeterno(st[1]);
							    				mobileStatus.setCreatedate(new Date());
							    				mobileStatus.setFilename(files);
							    				mobileStatus.setFilepath(ExactPath);
								    			mobileGenStatusService.save(mobileStatus);
								    			String time1="";
								    			String mainStatus = parseXml + "$" + duplicate + "$" + meterNotExist + "$" + corruptedFile;
								    			String countStatus = parsedCount + "$" +duplicateCount+ "$" +meterNotExistCount+ "$" +corruptedFileCount;
								    			
								    			mainStatus = mainStatus + "@" + countStatus + "@"+time1;
								    			parseMainStatus.add(mainStatus);
								    			parseXml = "";duplicate = "";meterNotExist = ""; corruptedFile="";
								    			parsedCount = 0;duplicateCount = 0;meterNotExistCount = 0;mainTime = 0;corruptedFileCount=0;
								    			System.out.println("before renaming "+ExactPath);
								    			File RenameFile = new File(ExactPath+"_parsed");
								    			
								    			fileForMetrNo.renameTo(RenameFile);
								    			System.out.println("After renaming "+RenameFile);
								    			/*
											        File newDir = new File(path+"/"+"read"+listOfFiles[i].getName());
											        listOfFiles[i].renameTo(newDir);*/
								    			}
								    			else
								    			{
								    				System.out.println("not contain meterno");
								    			}
								    			}
											else
											{
												model.addAttribute("result","contains other than xml files");
											}
						        	
									} catch (Exception e) {
										e.printStackTrace();
									}
					        	}
			        		 }
			        		 System.out.println("end of for loop for files");
			        	 }
				    }
			        System.out.println("end of for loop first directory");
			        //}
			       }
			      }
			 System.out.println("end of for loop path");
			double lIteratorEndTime = new Date().getTime();
			System.out.println("lIteratorEndTime--->"+lIteratorEndTime);
			 lIteratorDifference = lIteratorEndTime - lIteratorStartTime;
			System.out.println("Time taken in milliseconds: " + lIteratorDifference);
			parseMainStatus.add(lIteratorDifference);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return parseMainStatus;
	}
}
