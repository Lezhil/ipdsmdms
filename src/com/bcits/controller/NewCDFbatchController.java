package com.bcits.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bcits.entity.BatchStatusEntity;
import com.bcits.service.BatchStatusService;
import com.bcits.service.CdfBatchService;
import com.bcits.utility.MDMLogger;

@Controller
public class NewCDFbatchController {
	
	public static String uploadPath = "CDF_uploads";
	public static String unZipPath =  "CDF_unzippedFiles";
	public static String Log_File = "Log_File";
	
	private static String schemaName="BSMARTMDM";
//	private static String schemaName="MDM_TEST";
	
	@Autowired
	private  CdfBatchService cdfBatchService;
	
	@Autowired
	private BatchStatusService batchStatusService;
	
	SimpleDateFormat sdfToday=new SimpleDateFormat("YYYY-MM-dd");
	String todayDate=sdfToday.format(new Date());
	@RequestMapping(value="/newcdf",method=RequestMethod.GET)
	public String meterdataCMRI(HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("Inside New cdf batchEntry ");
		System.out.println(todayDate);
		return "NewCDF";
	}
	
	//private static String path ="D:\\vishwanath\\testBatch\\testHPLxml";

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
	
	@RequestMapping(value="/uploadCdfBatchFile1",method=RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,Model model)
    {
		System.out.println("enter to upload batch file controller");
		Document docForMetrNo=null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
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
//				System.out.println("folder not present");
				folder2.mkdir();
			}
			else
			{
				//System.out.println("folder-------> exist");
			}
			if(!extension.equalsIgnoreCase("zip"))
			{
				model.addAttribute("result","Invalid File Type extension should be .zip");
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
					System.out.println(unZipPath + "\\" + currentTime);
					System.out.println("fname-->"+fname[0]);
			    	System.out.println("Done");
			    	//model.addAttribute("UnZipFolderPath",currentTime);
			    	//model.addAttribute("UnZipFilename",fname[0]);
			    	//model.addAttribute("filename",fileName);
			    	String files = "";
			    	String msg="";
			    	String unZipFolderPath = unZipPath+"/"+currentTime+"/"+fname[0];
			    	String path1 = unZipFolderPath;
			    	System.out.println("path-->"+path1);
			    	
					File folder = new File(path1);
					
					File[] listOfFiles = folder.listFiles();
					System.out.println("length-->"+listOfFiles.length);
					System.out.println("list of files--->"+listOfFiles);
					if(listOfFiles != null)
					{
						for(int i =0;i < listOfFiles.length;i++)
						{
							if(listOfFiles[i].isFile())
							{
								files = listOfFiles[i].getName();
								if(!(files.endsWith(".xml") ||files.endsWith(".XML")))
								{
									
									System.out.println("not xml file=="+files);
									 msg="error";
								}
							}
							else
							{
								System.out.println("inside else false");
								 msg="error";
							}
						}
						if("error".equalsIgnoreCase(msg))
						{
							System.out.println("inside error;;;");
							model.addAttribute("result","Uploaded File contains other than XML files AND One ZIP File must contain ONE FOLDER ONLY- CHEK BEFORE UPLOAD");
						}
						/*else if("false".equalsIgnoreCase(msg))
						{
							System.out.println("inside false;");
							model.addAttribute("result","Uploaded File contains other than  XML files.");
						}*/
						else
						{
							model.addAttribute("data","data");
							model.addAttribute("result","File Uploaded Successfully..");
							
							model.addAttribute("UnZipFolderPath",unZipFolderPath);
							System.out.println("unZipFolderPath-->"+unZipFolderPath);
							
						}
					}
					else
					{
						model.addAttribute("result","Zip File Name and Folder Name Not Matched");
					}
			    }
			    catch(Exception ex)
			    {
			       ex.printStackTrace(); 
			    }
				System.out.println("uploaded successfully");
				System.out.println("end of upload");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "NewCDF";
	}
	
	
	String unZipFolderPath=null;

		class SaveData extends Thread{
			
			String path;
		SaveData(String path)
		{
			this.path=path;
			
		}
		
		@Override
		public void run()
		{
			System.out.println("inside run method");
			Date date1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			String bilmonth=sdf.format(date1);
			System.out.println(bilmonth);
			int scount=0;
			int fcount=0;
			int MMFcount=0;
			int MMScount=0;
			int TotalCount=0;
			List resultStatus=new ArrayList<>();
			try
			{
				System.out.println("Connecting to the database...");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.5.204:1521:orcl", schemaName, "bcits");//live
//				connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.5.205:1521:orcl", schemaName, "bcits"); //test
			if(connection==null)
			{
				System.out.println("Connection failed...");
				System.exit(0);
			}
			String files = "", meterNumber = "", billMonth = "";
			int meterNoCount = 0;
			
			//System.out.println("path lnt--->"+path);
			
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
					    			String xmlInserted="";
					    			String xmlNotInserted="";
					    			String MMUpdated="";
					    			String MMNotUpdated="";
					    			System.out.println("length 123-->"+listOfFiles.length);
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
					    			    			//System.out.println("fileForMetrNo-->"+fileForMetrNo);
					    			    			DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					    			    			docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
					    			    				/*new Runnable() {
					    			    			 		public void run() {*/
					    						 		String GenusResult=	cmriXmlReading(docForMetrNo);
					    			    			 	
					    			    			 		BatchStatusEntity bsummary=new BatchStatusEntity();
					    			    			 		System.out.println("result cmri---->"+GenusResult);
					    			    			 		String[] status=GenusResult.split("/");
					    			    			 		
					    			    			 	
																

					    			    			 		if(status[0].equalsIgnoreCase("success"))
					    			    			 		{
					    			    			 			//System.out.println("inside success-->"+scount+"@"+status[1]);
					    			    			 			int bcount=cdfBatchService.deleteMtrInBatchStatus(status[1].toString().trim(), bilmonth);
					    			    			 			scount++;
					    			    			 			TotalCount++;
					    			    			 			xmlInserted=status[1];
					    			    			 			bsummary.setXmlimport(1);
					    			    			 			
					    			    			 		}
					    			    			 		if(status[0].equalsIgnoreCase("failure"))
					    			    			 		{
					    			    			 			fcount++;
					    			    			 			TotalCount++;
					    			    			 			//System.out.println("inside failure-->"+scount+"@"+status[1]);
					    			    			 			xmlNotInserted=status[1];
					    			    			 			bsummary.setXmlimport(0);
					    			    			 			
					    			    			 		}
					    			    			 		if(status[2].equalsIgnoreCase("Msuccess"))
					    			    			 		{
					    			    			 			MMScount++;
					    			    			 			//System.out.println("inside Msuccess-->"+MMScount+"@"+status[1]);
					    			    			 			MMUpdated=status[1];
					    			    			 			bsummary.setMm_table(1);
					    			    			 			
					    			    			 		}
					    			    			 		if(status[2].equalsIgnoreCase("Mfailure"))
					    			    			 		{
					    			    			 			MMFcount++;
					    			    			 			//System.out.println("inside Mfailure-->"+MMFcount+"@"+status[1]);
					    			    			 			MMNotUpdated=status[1].trim();
					    			    			 			bsummary.setMm_table(0);
					    			    			 		}
					    			    			 		if(status[3].equalsIgnoreCase("corrupt"))
					    			    			 		{
					    			    			 			bsummary.setParsestatus("D3Tag-NotPresent");
					    			    			 		}
					    			    			 		if(status[3].contains("D3-01"))
					    			    			 		{
					    			    			 			bsummary.setParsestatus(status[3]);
					    			    			 		}
					    			    			 		
					    			    			 		List<?> data= batchStatusService.getcirSubDiv(status[1].trim(), bilmonth);
					    			    			 		//System.out.println(data.size());
					    			    			 		for(Iterator<?> iterator1=  data.iterator(); iterator1.hasNext();)
					    			    			 		{
					    			    			 			Object[] obj=(Object[]) iterator1.next();
					    			    			 			
					    			    			 			System.out.println("cir-->"+obj[0]);
					    			    			 			System.out.println("sdocode-->"+obj[1]);
					    			    			 			System.out.println("sdoname-->"+obj[2]);
					    			    			 			bsummary.setCircle((String) obj[0]);
					    			    			 			if(obj[1]!=null)
					    			    			 			{
					    			    			 			BigDecimal dd=(java.math.BigDecimal)(obj[1]);
					    			    			 			bsummary.setSdocode(dd.toString());
					    			    			 			//System.out.println("big decimal==="+dd);
					    			    			 			}
					    			    			 			else
					    			    			 			{
					    			    			 				bsummary.setSdocode((String)obj[1]);
					    			    			 			}
					    			    			 			bsummary.setSdoname((String) obj[2]);
					    			    			 		}
					    			    			 		bsummary.setRdngmonth(bilmonth);
					    			    			 		bsummary.setFile_name(files);
					    			    			 		bsummary.setRdatestump(new Date());
					    			    			 		bsummary.setMeterno(status[1].toString().trim());
					    			    			 		bsummary.setAmr(0);
					    			    			 		String drive="C:/jboss-as-7.1.1.Final/bin";
					    			    			 		bsummary.setFile_path(drive+"/"+path+"/"+files);
					    			    			 		//bsummary.setParsestatus("1");
					    			    			 		
					    			    			 		if(!(status[0].equalsIgnoreCase("failure") && status[2].equalsIgnoreCase("Mfailure")))
					    			    			 		{
					    			    			 			batchStatusService.save(bsummary);
					    			    			 		}
					    			    			 		else
					    			    			 		{
					    			    			 			System.out.println("come inside elsss");
					    			    			 			long bcount=cdfBatchService.checkMeterNoRdngMnth(bilmonth, status[1].toString().trim());
					    			    			 			
					    			    			 			if(bcount>0)
					    			    			 			{
					    			    			 			}
					    			    			 			else
					    			    			 			{
					    			    			 				batchStatusService.save(bsummary);
					    			    			 			}
					    			    			 			
					    			    			 		}
					    			    			 		
					    			    			 		File RenameFile = new File( path+"/"+files+"_cmri");
					    					    			//System.out.println("RenameFile--"+RenameFile);
					    					    			fileForMetrNo.renameTo(RenameFile);
					    					    			//System.out.println("fileForMetrNo--"+fileForMetrNo);
					    			    			 		
					    			    		}catch (Exception e) 
					    							{
					    			    			
					    			    			e.printStackTrace();
					    							writeLogFile("Error : "+e);
					    	 					}
					    			    		//end of check meter number
					    			        }//if files.endsWith('xml)
					    			    	else{
					    			    		MDMLogger.logger.warn("File Contains other than XML Files");
					    			    		//System.out.println("File Contains other than XML Files");
					    			    	}
					        	} 
			        		 }
			
			System.out.println("-------------- end of file list");
			//connection.close();
			}//try
			catch(Exception e)
			{
				e.printStackTrace();
				writeLogFile("Error : "+e);
				System.exit(0);
			}
		
		}
			
		}
		//direct method
		@RequestMapping(value="/GenusdCDFImport1",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody String directCall(HttpServletRequest request) 
		{
			System.out.println("");
			String path=request.getParameter("path");
			System.out.println("calling directCall -->"+path);
			try {
			
			new Thread(new SaveData(path)).start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "success";
			
		}
		
  //@Scheduled(cron="0 0/59 * * * ?")
	 
		public  void meterALL() 
		{
			System.out.println("calling ALL CDF mobile import");
			int scount=0;
			int fcount=0;
			int MMFcount=0;
			int MMScount=0;
			int TotalCount=0;
			List resultStatus=new ArrayList<>();
			try
			{
		 
				System.out.println("Connecting to the database...");
				
				
				
				connection = DriverManager.getConnection(
//								"jdbc:oracle:thin:@192.168.5.205:1521:orcl", schemaName, "bcits"); //test
								"jdbc:oracle:thin:@192.168.5.204:1521:orcl", schemaName, "bcits"); //live
						    
			if(connection==null)
			{
				System.out.println("Connection failed...");
				System.exit(0);
			}
			String files = "", meterNumber = "", billMonth = "";
			int meterNoCount = 0;
			
			
			String path=AmrController.meterFileFolder;
			File folder = new File(path);
			//System.out.println("new cdf path --->"+path);
			File[] listOfFiles = folder.listFiles(); 
			
			if(listOfFiles !=null)         // Added By Vijju(25-06-18)
			{
			//System.out.println("No. files : "+listOfFiles.length);
			writeLogFile("No. of files : "+listOfFiles.length);
			writeLogFileNotImported("No. of files : "+listOfFiles.length);
			
			String xmlInserted="";
			String xmlNotInserted="";
			String MMUpdated="";
			String MMNotUpdated="";
			Date date1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			String bilmonth=sdf.format(date1);
			System.out.println(bilmonth);
			
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
			    {
			    	files = listOfFiles[i].getName();
			    	//System.out.println("listOfFiles[i].getName()--"+listOfFiles[i].getName());
			    	if (files.endsWith(".xml") || files.endsWith(".XML"))
			    	{
			    		//System.out.println("fie name contains .xml-"+files);
			    		//fileName = files;
			    		//System.out.println(files);
			    		//check meter number
			    		try{
			    			
			    			File fileForMetrNo = new File( path+"/"+files);
			    			//System.out.println("fileForMetrNo-->"+fileForMetrNo);
			    			DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			    			docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
			    				/*new Runnable() {
			    			 		public void run() {*/
			    			 		
			    			 		String GenusResult=	amrXmlReading(docForMetrNo);
			    			 		
			    					/*}
			    				}.run();*/
			    			 		
			    			 		System.out.println("AMRGenusResult-->"+GenusResult);
			    			 		BatchStatusEntity bsummary=new BatchStatusEntity();
			    			 		String[] status=GenusResult.split("/");
			    			 		
			    			 		if(status[0].equalsIgnoreCase("success"))
			    			 		{
			    			 			int bcount=cdfBatchService.deleteMtrInBatchStatus(status[1].toString().trim(), bilmonth);
			    			 			
			    			 			scount++;
			    			 			TotalCount++;
			    			 			xmlInserted=status[1];
			    			 			bsummary.setXmlimport(1);
			    			 		}
			    			 		if(status[0].equalsIgnoreCase("failure"))
			    			 		{
			    			 			fcount++;
			    			 			TotalCount++;
			    			 			//System.out.println("inside failure-->"+scount+"@"+status[1]);
			    			 			xmlNotInserted=status[1];
			    			 			bsummary.setXmlimport(0);
			    			 			
			    			 		}
			    			 		if(status[2].equalsIgnoreCase("Msuccess"))
			    			 		{
			    			 			MMScount++;
			    			 			//System.out.println("inside Msuccess-->"+MMScount+"@"+status[1]);
			    			 			MMUpdated=status[1];
			    			 			bsummary.setMm_table(1);
			    			 			
			    			 		}
			    			 		if(status[2].equalsIgnoreCase("Mfailure"))
			    			 		{
			    			 			MMFcount++;
			    			 			//System.out.println("inside Mfailure-->"+MMFcount+"@"+status[1]);
			    			 			MMNotUpdated=status[1];
			    			 			bsummary.setMm_table(0);
			    			 			
			    			 		}
			    			 		
			    			 		if(status[3].equalsIgnoreCase("corrupt"))
			    			 		{
			    			 			bsummary.setParsestatus("D3Tag-NotPresent");
			    			 		}
			    			 		if(status[3].contains("D3-01"))
			    			 		{
			    			 			bsummary.setParsestatus(status[3]);
			    			 			
			    			 			
			    			 		}
			    			 		
			    			 		List<?> data= batchStatusService.getcirSubDiv(status[1].trim(), bilmonth);
			    			 		
			    			 		System.out.println(data.size());
			    			 		for(Iterator<?> iterator1=  data.iterator(); iterator1.hasNext();)
			    			 		{
			    			 			Object[] obj=(Object[]) iterator1.next();
			    			 			
			    			 			//System.out.println("cir-->"+obj[0]);
			    			 			//System.out.println("sdocode-->"+obj[1]);
			    			 			//System.out.println("sdoname-->"+obj[2]);
			    			 			bsummary.setCircle((String) obj[0]);
			    			 			
			    			 			if(obj[1]!=null)
			    			 			{
			    			 			BigDecimal dd=(java.math.BigDecimal)(obj[1]);
			    			 			bsummary.setSdocode(dd.toString());
			    			 			//System.out.println("big decimallll==="+dd);
			    			 			}
			    			 			else
			    			 			{
			    			 				bsummary.setSdocode((String)obj[1]);
			    			 			}
			    			 			bsummary.setSdoname((String) obj[2]);
			    			 			
			    			 			
			    			 		}
			    			 		bsummary.setRdngmonth(bilmonth);
			    			 		bsummary.setFile_name(files);
			    			 		bsummary.setRdatestump(new Date());
			    			 		bsummary.setMeterno(status[1].toString());
			    			 		bsummary.setAmr(1);
			    			 	
			    			 		bsummary.setFile_path(path+"/"+files);
			    			 		//bsummary.setParsestatus("1");
			    			 		if(!(status[0].equalsIgnoreCase("failure") && status[2].equalsIgnoreCase("Mfailure")))
			    			 		{
			    			 			batchStatusService.save(bsummary);
			    			 		}
			    			 		else
			    			 		{
			    			 			System.out.println("come inside elsss");
			    			 			long bcount=cdfBatchService.checkMeterNoRdngMnth(bilmonth, status[1].toString().trim());
			    			 			
			    			 			if(bcount>0)
			    			 			{
			    			 			}
			    			 			else
			    			 			{
			    			 				batchStatusService.save(bsummary);
			    			 			}
			    			 			
			    			 		}
			    			 		
			    			 		String mainStatus=xmlInserted+ "$" + xmlNotInserted+ "$" +MMUpdated+ "$" +MMNotUpdated;
			    			 		String mainCount=scount+ "$" +fcount+ "$" +MMScount+ "$" +MMFcount+ "$" +TotalCount;
			    			 		
			    			 		String statusImport=mainStatus+ "@" +mainCount;
			    			 		
			    			 		
			    			 		File RenameFile = new File( path+"/"+files+"_mobile");
					    			//System.out.println("RenameFile--"+RenameFile);
					    			fileForMetrNo.renameTo(RenameFile);
					    			System.out.println("fileForMetrNo--"+fileForMetrNo);
			    		}catch (Exception e) 
							{
			    			e.printStackTrace();
							writeLogFile("Error : "+e);
	 					}
			    		//end of check meter number
			        }//if files.endsWith('xml)
			    }
			}
			System.out.println("-------------- end of file list");
			}
			//connection.close();
			}//try
			
			catch(Exception e)
			{
				e.printStackTrace();
				writeLogFile("Error : "+e);
				System.exit(0);
			}
			
			
		}
		
	
	//Reading CMRI xml files for XmlImport table.
	public  String cmriXmlReading(Document docForMetrNo ) {
		Statement st =null;
		String flagStatus="";
		
		try{
			st= connection.createStatement();
			ResultSet rs = null, rsCount = null, rsForDateCheck = null;

			String meterno="",rdate="",kwh="",kwhunit="",kvh="",kvhunit="",kva="",kvaunit="",arb="",pf="",kwharb="",kvaarb="",kvharb="",pfarb="",flag1="0",flag2="0",flag3="0",flag4="0",flag5="0",flag6 = "0";
			String g2Value = "", g3Value = "";
			int status=0;
			String d1_OccDate = "",d2_OccDate = "",d3_OccDate = "",d4_OccDate = "",d5_OccDate = "",d6_OccDate="";
			
			//export reading
			String ekwh="",ekvah="",ekva="", epf="";
			String ekwh2="",ekvah2="",ekva2="", epf2="";
			String ekwh3="",ekvah3="",ekva3="", epf3="";
			String ekwh4="",ekvah4="",ekva4="", epf4="";
			String ekwh5="",ekvah5="",ekva5="", epf5="";
			String ekwh6="",ekvah6="",ekva6="", epf6="";
			
						
			String rdate_d2="",kwh_d2="",kwhunit_d2="",kvh_d2="",kvhunit_d2="",kva_d2="",kvaunit_d2="",arb_d2="",pf_d2="",kwharb_d2="",kvaarb_d2="",kvharb_d2="",pfarb_d2="",flag1_d2="0",flag2_d2="0",flag3_d2="0",flag4_d2="0",flag5_d2="0",flag6_d2="0";
			String rdate_d3="",kwh_d3="",kwhunit_d3="",kvh_d3="",kvhunit_d3="",kva_d3="",kvaunit_d3="",arb_d3="",pf_d3="",kwharb_d3="",kvaarb_d3="",kvharb_d3="",pfarb_d3="",flag1_d3="0",flag2_d3="0",flag3_d3="0",flag4_d3="0",flag5_d3="0",flag6_d3="0";
			String rdate_d4="",kwh_d4="",kwhunit_d4="",kvh_d4="",kvhunit_d4="",kva_d4="",kvaunit_d4="",arb_d4="",pf_d4="",kwharb_d4="",kvaarb_d4="",kvharb_d4="",pfarb_d4="",flag1_d4="0",flag2_d4="0",flag3_d4="0",flag4_d4="0",flag5_d4="0",flag6_d4="0";
			String rdate_d5="",kwh_d5="",kwhunit_d5="",kvh_d5="",kvhunit_d5="",kva_d5="",kvaunit_d5="",arb_d5="",pf_d5="",kwharb_d5="",kvaarb_d5="",kvharb_d5="",pfarb_d5="",flag1_d5="0",flag2_d5="0",flag3_d5="0",flag4_d5="0",flag5_d5="0",flag6_d5="0";
			String rdate_d6="",kwh_d6="",kwhunit_d6="",kvh_d6="",kvhunit_d6="",kva_d6="",kvaunit_d6="",arb_d6="",pf_d6="",kwharb_d6="",kvaarb_d6="",kvharb_d6="",pfarb_d6="",flag1_d6="0",flag2_d6="0",flag3_d6="0",flag4_d6="0",flag5_d6="0",flag6_d6="0";
			
			boolean d3_01_flag=false, d3_02_flag=false, d3_03_flag=false, d3_04_flag=false, d3_05_flag=false, d3_06_flag=false, d3_exist = false;
			
			String dateCount1 = "0", dateCount2 = "0", dateCount3 = "0", dateCount4 = "0", dateCount5 = "0", dateCount6 = "0";
			String g4Value="";
			
			String MetrFlagStatus="";
			String checkCrpt="D3present";
			
			//for billMonth
			Date billDate = new Date();
			SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
			String billMonth = sdfBillDate.format(billDate);
			//billMonth = "201306";
			int d4DayProfileCount = 0;
			
			//shri
			String manufacturerCode="";
			String manufacturerName="";
			String gvalue="";
			String genusd="3ph-4W";
			String genusNOnDlms1="LT(3ph-4W)";
			String genusNOnDlms2="WC(3ph-4W)";
			String genusNOnDlms3="HT(3ph-4W)";
			String genusNOnDlms4="1ph-2W";
			
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
																		
																		if(nodeName.equalsIgnoreCase("G4"))
																		{
																			g4Value = nodeValue;
																			System.out.println("G4 value ===========================>"+g4Value);
																		}

																		if(nodeName.equalsIgnoreCase("G13"))
																		{
																			meterclass = nodeValue;
																			System.out.println("meter not no===========================>"+meterclass);
																		}
																		
																		
																		if(nodeName.equalsIgnoreCase("G22"))
																		{
																			if (tempNode123.hasAttributes()) 
																			{
																				NamedNodeMap nodeMap = tempNode123.getAttributes();
																				//String manufacturerCode = "",manufacturerName = "";
																				
																				for (int i = 0; i < nodeMap.getLength(); i++) 
																				{
																					String d1AttrId = "", value = "";
																					Node node = nodeMap.item(i);
																					//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());
																					
																					if(node.getNodeName().equalsIgnoreCase("CODE")) 
																					{
																						manufacturerCode = node.getNodeValue();
																						System.out.println("manufacturerCode===>"+manufacturerCode);
																					}
																					else if(node.getNodeName().equalsIgnoreCase("NAME")) 
																					{
																						manufacturerName = node.getNodeValue();
																						System.out.println("manufacturerName===>"+manufacturerName);
																					}															    						
																																																									
																				}
																				
																			}//End has Attribute
																		}
																		if(nodeName.equalsIgnoreCase("G15"))
																		{
																			 gvalue=nodeValue;
																			System.out.println("gvalue-->"+gvalue);
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
														    				try {
																			
														    				/*if(d3TagCount.equalsIgnoreCase("D3-01"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate=dateTime;
														    						System.out.println("d3_01_dateTime--->"+d3_01_dateTime+"rdate=="+rdate);
														    				
														    					if(!rdate.isEmpty()){
														    							System.err.println("before rdate-->"+rdate);
														    				
														    					String checkRdate=rdate.substring(0, 10);
														    					String r1[]=checkRdate.split("-");
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    							System.out.println("s1--->"+s1);
														    						rdate=s1;
														    							System.out.println("after rdate-->"+rdate.substring(0, 10));
														    					}
														    					else
														    					{ 
														    						if(!g2Value.equals("")){
														    						String g1date=g2Value.substring(0, 10);
														    						System.out.println("g1 date=="+g1date);
														    						String g1[]=g1date.split("-");
														    						
														    						String s1=g1[0]+"-"+g1[1]+"-"+g1[2];
														    						rdate=s1;
														    						}
														    					}
														    					
														    					
														    					}
																				d3_01_flag = true;
																				System.out.println("cmri values are dates rdate d3-01->"+rdate);
													    					}
														    				 else if(d3TagCount.equalsIgnoreCase("D3-02"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate_d2=dateTime;	
														    					if(!rdate_d2.isEmpty()){
														    						
														    						System.out.println("before rdate d302-->"+rdate_d2.substring(0, 10));
														    					String checkRdate=rdate_d2.substring(0, 10);
														    					
														    					String r1[]=checkRdate.split("-");
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    						System.out.println("s1--->"+s1);
														    						rdate_d2=s1;
														    						System.out.println("after rdate d302-->"+rdate.substring(0, 10));
														    					}
														    					else
														    					{ 
														    							
														    						System.out.println("g2valueeee d302=="+g4Value);
														    						if(!g4Value.equals("")){
														    						System.out.println("");
														    						String g1date=g4Value.substring(0, 10);
														    						System.out.println("g4Value date=="+g1date);
														    						String g1[]=g1date.split("-");
														    						int m2=Integer.parseInt(g1[1])-1;
														    						System.out.println("month 2--"+m2);
														    						String s1=g1[0]+"-"+m2+"-"+g1[2];
														    						rdate_d2=s1;
														    						}
														    					}
														    					}
																				d3_02_flag = true;
																				System.out.println("cmri values are dates rdate_d2 d3-02->"+rdate_d2);
													    					}
														    				
														    				else if(d3TagCount.equalsIgnoreCase("D3-03"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate_d3=dateTime;	
														    					
														    					if(!rdate_d3.isEmpty()){
														    						//System.out.println("before rdate_d3-->"+rdate_d3.substring(0, 10));
														    					String checkRdate=rdate_d3.substring(0, 10);
														    					
														    					String r1[]=checkRdate.split("-");
														    					
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    						//	System.out.println("s1--->"+s1);
														    						rdate_d3=s1;
														    						//	System.out.println("after rdate-->"+rdate_d3.substring(0, 10));
														    					}
														    					}
																				d3_03_flag = true;
																				//System.out.println("cmri values are dates rdate_d3 d3-03->"+rdate_d3);
													    					}
														    				
														    				else if(d3TagCount.equalsIgnoreCase("D3-04"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate_d4=dateTime;
														    					
														    					if(!rdate_d4.isEmpty()){
														    						//System.out.println("before rdate_d4-->"+rdate_d4.substring(0, 10));
														    					String checkRdate=rdate_d4.substring(0, 10);
														    					
														    					String r1[]=checkRdate.split("-");
														    					
														    					
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    						//	System.out.println("s1--->"+s1);
														    						rdate_d4=s1;
														    						//	System.out.println("after rdate rdate_d4-->"+rdate_d4.substring(0, 10));
														    					}
														    					}
																				d3_04_flag = true;
																				//System.out.println("cmri values are dates rdate_d4 d3-04->"+rdate_d4);
													    					}
														    				else if(d3TagCount.equalsIgnoreCase("D3-05"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate_d5=dateTime;
														    					
														    					if(!rdate_d5.isEmpty()){
														    						//System.out.println("before rdate_d4-->"+rdate_d5.substring(0, 10));
														    					String checkRdate=rdate_d5.substring(0, 10);
														    					
														    					String r1[]=checkRdate.split("-");
														    					
														    					
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    						//	System.out.println("s1--->"+s1);
														    						rdate_d5=s1;
														    						//	System.out.println("after rdate_d5-->"+rdate_d5.substring(0, 10));
														    					}
														    					}
																				d3_05_flag = true;
																				//System.out.println("cmri values are dates rdate_d5  d3-05->"+rdate_d5);
													    					}
														    				else if(d3TagCount.equalsIgnoreCase("D3-06"))
													    					{
														    					d3_01_dateTime = dateTime;	
														    					rdate_d6=dateTime;	
														    					
														    					if(!rdate_d6.isEmpty()){
														    					String checkRdate=rdate_d6.substring(0, 10);
														    					
														    					String r1[]=checkRdate.split("-");
														    					
														    					
														    					
														    					int m1=Integer.parseInt(r1[0]);
														    					if((!(r1[0].equals("01"))&& m1<12)&&r1[1].equals("01"))
														    					{
														    						String s1=r1[1]+"-"+r1[0]+"-"+r1[2];
														    						//System.out.println("s1--->"+s1);
														    						rdate_d6=s1;
														    						//	System.out.println("after rdate_d6-->"+rdate_d6.substring(0, 10));
														    					}
														    					}
																				d3_06_flag = true;
																				//System.out.println("cmri values are dates rdate_d6 d3-06->"+rdate_d6);
													    					}*/
														    				
														    				//old
														    				
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
														    				
														    				} catch (Exception e) {
																				e.printStackTrace();
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
														    					String code = "", value = "0", unit = "",tod = "",occDate = "";
														    					
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
																    				
																    				//for genus Non Dlms
																    			if(manufacturerCode.equalsIgnoreCase("4") && (gvalue.equalsIgnoreCase(genusNOnDlms1)||gvalue.equalsIgnoreCase(genusNOnDlms2)||gvalue.equalsIgnoreCase(genusNOnDlms3)))
																    			{
																    				//System.out.println("inside non dlms");
																    				if(d3TagCount.equalsIgnoreCase("D3-00"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1.equalsIgnoreCase("0") )
															    							{
																								if(value == "")
																								{
																									value = "0";
																								}
																								float temp = Float.parseFloat(value);
																								flag1=temp+"";
																								if(!flag1.equalsIgnoreCase("0.0") )
																								{
																									arb=code;
																									kwhValue = value;
																									kwhunit=unit;
																									kwh= value;
																									flag1=value;
																									kwharb=code;

																								}
															    								
															    								//System.out.println("P7-1-5-1-0 kwhValue value==================>"+kwhValue);
																							System.out.println("flag1 value==================>"+flag1);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && (flag1.equalsIgnoreCase("0") || flag1.equalsIgnoreCase("0.0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println(" P7-1-5-2-0 kwhValue value==================>"+kwhValue);
															    							}
																							System.out.println("D3-00 -->Rajguru-flag1  - "+flag1);
															    							if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								//flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println(" P7-1-18-0-0 kwhValue value==================>"+kwhValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								//flag2=value;
															    								kvharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}

															    							else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								//flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								flag3=value;
															    								pf=value;
															    								pfarb=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
																						if(code.equalsIgnoreCase("P4-4-4-0-0") && flag3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf=value;
															    								pfarb=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}
																    				
																    				if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1.equalsIgnoreCase("0") )
															    							{
																								if(value == "")
																								{
																									value = "0";
																								}
																								float temp = Float.parseFloat(value);
																								flag1=temp+"";
																								if(!flag1.equalsIgnoreCase("0.0") )
																								{
																									arb=code;
																									kwhValue = value;
																									kwhunit=unit;
																									kwh= value;
																									flag1=value;
																									kwharb=code;

																								}
															    								//System.out.println("kwhValue value==================>"+kwhValue);
																							System.out.println("flag1 value==================>"+flag1);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && (flag1.equalsIgnoreCase("0") || flag1.equalsIgnoreCase("0.0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								//flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    						
															    								else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								//flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    						
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								//flag2=value;
															    								kvharb=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								//flag2=value;
															    								kvharb=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								//flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}

															    							else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								//flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								flag3=value;
															    								pf=value;
															    								pfarb=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
																						if(code.equalsIgnoreCase("P4-4-4-0-0") && flag3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf=value;
															    								pfarb=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}
																    				//For d3-02
															    					else
															    					if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1_d2.equalsIgnoreCase("0") )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								//flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
																							}
															    								else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								//flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
																							}
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								//flag2_d2=value;
															    								kvharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
																							}
																							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								//flag2_d2=value;
															    								kvharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
																							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								//flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
																							}
															    								else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								//flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
																							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d2=value;
															    								pfarb_d2=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of d3-02
																    				
																    				//begin of d3-03
															    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1_d3.equalsIgnoreCase("0") )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								//flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
																							}
																							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								//flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
																							}
															    							
															    							
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								flag2_d3=value;
															    								kvharb_d3=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								//flag2_d3=value;
															    								kvharb_d3=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
																							}
															    								else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								//flag2_d3=value;
															    								kvharb_d3=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
																							}														    							
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								flag4_d3=value;
															    								kvaunit_d3=unit;
																								d3_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								//flag4_d3=value;
															    								kvaunit_d3=unit;
																								d3_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
																							}
															    								else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4_d3.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								//flag4_d3=value;
															    								kvaunit_d3=unit;
																								d3_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
																							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d3=value;
															    								pfarb_d3=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}
															    						//begin of d3-04
															    						else if(d3TagCount.equalsIgnoreCase("D3-04"))
															    						{

																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1_d4.equalsIgnoreCase("0") )
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d4=unit;
																    								flag1_d4=value;
																    								kwh_d4= value;
																    								kwharb_d4=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d4=unit;
																    								//flag1_d4=value;
																    								kwh_d4= value;
																    								kwharb_d4=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d4=unit;
																    								//flag1_d4=value;
																    								kwh_d4= value;
																    								kwharb_d4=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    							
																    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvhunit_d4=unit;
																    								flag2_d4=value;
																    								kvharb_d4=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvhunit_d4=unit;
																    								//flag2_d4=value;
																    								kvharb_d4=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvhunit_d4=unit;
																    								//flag2_d4=value;
																    								kvharb_d4=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}
																    						
																    						 	
																    							
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								flag4_d4=value;
																    								kvaunit_d4=unit;
																									d4_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								//flag4_d4=value;
																    								kvaunit_d4=unit;
																									d4_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4_d4.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								//flag4_d4=value;
																    								kvaunit_d4=unit;
																									d4_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								
																    								pf_d4=value;
																    								pfarb_d4=code;
//																    								System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
															    						}//end of d3-04
															    						
															    						//Begin of D3-05
															    						else if(d3TagCount.equalsIgnoreCase("D3-05"))
															    						{

																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1_d5.equalsIgnoreCase("0") )
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d5=unit;
																    								flag1_d5=value;
																    								kwh_d5= value;
																    								kwharb_d5=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d5=unit;
																    								//flag1_d5=value;
																    								kwh_d5= value;
																    								kwharb_d5=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d5=unit;
																    								//flag1_d5=value;
																    								kwh_d5= value;
																    								kwharb_d5=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d5= value;
																    								kvhunit_d5=unit;
																    								flag2_d5=value;
																    								kvharb_d5=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d5= value;
																    								kvhunit_d5=unit;
																    								//flag2_d5=value;
																    								kvharb_d5=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}
																	    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d5= value;
																    								kvhunit_d5=unit;
																    								//flag2_d5=value;
																    								kvharb_d5=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}
																    							
																    							
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d5=value;
																    								kvaarb_d5=code;
																    								flag4_d5=value;
																    								kvaunit_d5=unit;
																									d5_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d5=value;
																    								kvaarb_d5=code;
																    								//flag4_d5=value;
																    								kvaunit_d5=unit;
																									d5_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4_d5.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d5=value;
																    								kvaarb_d5=code;
																    								//flag4_d5=value;
																    								kvaunit_d5=unit;
																									d5_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								
																    								pf_d5=value;
																    								pfarb_d5=code;
//																    								System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
																    					
															    						}//end of D3-05
															    						
															    						//Begin of D3-06
															    						else if(d3TagCount.equalsIgnoreCase("D3-06")){

																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1_d6.equalsIgnoreCase("0") )
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d6=unit;
																    								flag1_d6=value;
																    								kwh_d6= value;
																    								kwharb_d6=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d6=unit;
																    								//flag1_d6=value;
																    								kwh_d6= value;
																    								kwharb_d6=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    							
																    								else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d6=unit;
																    								//flag1_d6=value;
																    								kwh_d6= value;
																    								kwharb_d6=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d6= value;
																    								kvhunit_d6=unit;
																    								flag2_d6=value;
																    								kvharb_d6=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d6= value;
																    								kvhunit_d6=unit;
																    								//flag2_d6=value;
																    								kvharb_d6=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}															    							
																    								else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d6= value;
																    								kvhunit_d6=unit;
																    								//flag2_d6=value;
																    								kvharb_d6=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}	
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d6=value;
																    								kvaarb_d6=code;
																    								flag4_d6=value;
																    								kvaunit_d6=unit;
																									d6_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d6=value;
																    								kvaarb_d6=code;
																    								//flag4_d6=value;
																    								kvaunit_d6=unit;
																									d6_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    								else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4_d6.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d6=value;
																    								kvaarb_d6=code;
																    								//flag4_d6=value;
																    								kvaunit_d6=unit;
																									d6_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								
																    								pf_d6=value;
																    								pfarb_d6=code;
//																    								System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
															    						}
															    					
															    					}
																    			
																    			//genusdlms
																    			if(manufacturerCode.equalsIgnoreCase("4") && (gvalue.equalsIgnoreCase(genusd)||gvalue.equalsIgnoreCase(genusNOnDlms4)))
																    			{
																    				//System.out.println("inside genusd");
															    					if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("P7-1-13-2-0 kwhValue value==================>"+kwhValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-1-5-2-0")) &&  flag1.equalsIgnoreCase("0") )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("P7-1-5-2-0 kwhValue value==================>"+kwhValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh= value;
															    								kvhunit=unit;
															    								kvharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								flag2=value;
															    								kvharb=code;
															    								
															    								kvhunit=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								flag2=value;
															    								kvharb=code;
															    								kvhunit=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						}
															    						
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
															    								d1_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-5-2-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																							flag4=value;
																							d1_OccDate = occDate;
															    							}
																							/*else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								//flag5=value;
															    							}*/
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf=value;
															    								pfarb=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//end of d3-01 
															    					
															    					//For d3-02
															    					else
															    					if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0"))   )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2 = value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-1-5-2-0")) &&  flag1_d2.equalsIgnoreCase("0") )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2 = value;
															    								kwharb_d2=code;
															    								
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kwharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kwharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								flag3_d2=value;
															    								kvharb_d2=code;
															    								
															    								kvhunit_d2=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
															    								d2_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							 else if(code.equalsIgnoreCase("P7-6-5-2-0")&& flag4_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								flag5_d2=value;
																								d2_OccDate = occDate;
															    							} 
																						/*	else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								//flag5_d2=value;
															    							}*/
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d2=value;
															    								pfarb_d2=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}
															    					
															    					//End of d3-02
															    					//for d3 - 03
															    					else
															    					if(d3TagCount.equalsIgnoreCase("D3-03"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0"))   )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-1-5-2-0")) &&  flag1_d3.equalsIgnoreCase("0") )
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
															    								
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d3= value;
															    								kwhunit_d3=unit;
															    								kwharb_d3=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								flag2_d3=value;
															    								kwharb_d3=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								flag3_d3=value;
															    								kvharb_d3=code;
															    								
															    								kvhunit_d3=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								flag4_d3=value;
															    								kvaunit_d3=unit;
															    								d3_OccDate=occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-5-2-0")&& flag4_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								flag5_d3=value;
																								d3_OccDate=occDate;
															    							}
																							/*else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								//flag5_d3=value;
															    							}*/
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d3=value;
															    								pfarb_d3=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}
															    				 	//End of d3-03
															    					//Begining of d3-04
															    					
															    					else
																    					if(d3TagCount.equalsIgnoreCase("D3-04"))
																    					{
																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																    							if((code.equalsIgnoreCase("P7-1-13-2-0"))   )
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d4=unit;
																    								flag1_d4=value;
																    								kwh_d4= value;
																    								kwharb_d4=code;
																    								//System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																    							
																    							/*else if((code.equalsIgnoreCase("P7-1-13-1-0")) &&  flag1.equalsIgnoreCase("0") )
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit=unit;
																    								flag6_d4=value;
																    								kwh= value;
																    								kwharb=code;
																    								
																    							}*/
																    							
																    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1.equalsIgnoreCase("0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kwh_d4= value;
																    								kwhunit_d4=unit;
																    								kwharb_d4=code;
																    								//System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																    							
																    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvhunit_d4=unit;
																    								flag2_d4=value;
																    								kwharb_d4=code;
																    								//System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																    							
																    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d4.equalsIgnoreCase("0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								flag3_d4=value;
																    								kvharb_d4=code;
																    								
																    								kvhunit_d4=unit;
																    								//System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																    							/*else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvharb_d4=code;
																    								kvhunit_d4=unit;
																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}*/
																    						 	
																    							
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								flag4_d4=value;
																    								kvaunit_d4=unit;
																    								d4_OccDate = occDate;
																    								//System.out.println("kva value==================>"+kvaValue);
																    							}
																    							/*else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d4.equalsIgnoreCase("0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								kvaunit_d4=unit;
																									flag5_d4=value;
																									d4_OccDate = occDate;
																    							}*/
																								/*else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d4=value;
																    								kvaarb_d4=code;
																    								kvaunit_d4=unit;
																									//flag5_d4=value;
																    							}*/
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								
																    								pf_d4=value;
																    								pfarb_d4=code;
																    								//System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
																    					}
															    					
															    					//end of d3-04
															    					
															    					//Begining of d3-05
															    					
																    					else
																	    					if(d3TagCount.equalsIgnoreCase("D3-05"))
																	    					{
																	    						if(subNodeName.equalsIgnoreCase("B3"))
																	    						{
																	    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																	    							if((code.equalsIgnoreCase("P7-1-13-2-0"))   )
																	    							{
																	    								arb=code;
																	    								kwhValue = value;
																	    								kwhunit_d5=unit;
																	    								flag1_d5=value;
																	    								kwh_d5= value;
																	    								kwharb_d5=code;
																	    								//System.out.println("kwhValue value==================>"+kwhValue);
																	    							}
																	    							/*
																	    							else if((code.equalsIgnoreCase("P7-1-13-1-0")) &&  flag1.equalsIgnoreCase("0") )
																	    							{
																	    								arb=code;
																	    								kwhValue = value;
																	    								kwhunit=unit;
																	    								flag6_d5=value;
																	    								kwh= value;
																	    								kwharb=code;
																	    								
																	    							}
																	    							*/
																	    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1.equalsIgnoreCase("0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kwh_d5= value;
																	    								kwhunit_d5=unit;
																	    								kwharb_d5=code;
																	    								System.out.println("kvahValue value==================>"+kvahValue);
																	    							}
																	    							
																	    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d5= value;
																	    								kvhunit_d5=unit;
																	    								flag2_d5=value;
																	    								kwharb_d5=code;
																	    								System.out.println("kvahValue value==================>"+kvahValue);
																	    							}
																	    							
																	    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d5.equalsIgnoreCase("0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d5= value;
																	    								flag3_d5=value;
																	    								kvharb_d5=code;
																	    								
																	    								kvhunit_d5=unit;
																	    								System.out.println("kvahValue value==================>"+kvahValue);
																	    							}
																	    							/*else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d5= value;
																	    								kvharb_d5=code;
																	    								kvhunit_d5=unit;
																	    								System.out.println("kvahValue value==================>"+kvahValue);
																	    							}
																	    						 	*/
																	    							
																	    						}
																	    						if(subNodeName.equalsIgnoreCase("B5"))
																	    						{
																	    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																	    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
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
																	    							/*else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d5.equalsIgnoreCase("0"))
																	    							{
																	    								arb=code;
																	    								kvaValue = value;
																	    								kva_d5=value;
																	    								kvaarb_d5=code;
																	    								kvaunit_d5=unit;
																										flag5_d5=value;
																										d5_OccDate = occDate;
																	    							}*/
																									/*else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
																	    							{
																	    								arb=code;
																	    								kvaValue = value;
																	    								kva_d5=value;
																	    								kvaarb_d5=code;
																	    								kvaunit_d5=unit;
																										//flag5_d5=value;
																	    							}*/
																	    						}
																	    						 
																	    						if(subNodeName.equalsIgnoreCase("B9"))
																	    						{
																	    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																	    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																	    							{
																	    								arb=code;
																	    								pfValue = value;
																	    								
																	    								pf_d5=value;
																	    								pfarb_d5=code;
//																	    								System.out.println("pfValue value==================>"+pfValue);
																	    							}
																	    						}
																	    					}
															    					
															    					//end of d3-05
															    					
															    					
															    					//Begining of d3-06
																	    					else
																		    					if(d3TagCount.equalsIgnoreCase("D3-06"))
																		    					{
																		    						if(subNodeName.equalsIgnoreCase("B3"))
																		    						{
																		    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																		    							if((code.equalsIgnoreCase("P7-1-13-2-0"))   )
																		    							{
																		    								arb=code;
																		    								kwhValue = value;
																		    								kwhunit_d6=unit;
																		    								flag1_d6=value;
																		    								kwh_d6= value;
																		    								kwharb_d6=code;
																		    								//System.out.println("kwhValue value==================>"+kwhValue);
																		    							}
																		    							
																		    						/*	else if((code.equalsIgnoreCase("P7-1-13-1-0")) &&  flag1.equalsIgnoreCase("0") )
																		    							{
																		    								arb=code;
																		    								kwhValue = value;
																		    								kwhunit=unit;
																		    								flag6_d6=value;
																		    								kwh= value;
																		    								kwharb=code;
																		    								
																		    							}*/
																		    							
																		    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1.equalsIgnoreCase("0"))
																		    							{
																		    								arb=code;
																		    								kvahValue = value;
																		    								kwh_d6= value;
																		    								kwhunit_d6=unit;
																		    								kwharb_d6=code;
																		    								System.out.println("kvahValue value==================>"+kvahValue);
																		    							}
																		    							
																		    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
																		    							{
																		    								arb=code;
																		    								kvahValue = value;
																		    								kvh_d6= value;
																		    								kvhunit_d6=unit;
																		    								flag2_d6=value;
																		    								kwharb_d6=code;
//																		    								System.out.println("kvahValue value==================>"+kvahValue);
																		    							}
																		    							
																		    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d6.equalsIgnoreCase("0"))
																		    							{
																		    								arb=code;
																		    								kvahValue = value;
																		    								kvh_d6= value;
																		    								flag3_d6=value;
																		    								kvharb_d6=code;
																		    								
																		    								kvhunit_d6=unit;
//																		    								System.out.println("kvahValue value==================>"+kvahValue);
																		    							}
																		    							/*else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0"))
																		    							{
																		    								arb=code;
																		    								kvahValue = value;
																		    								kvh_d6= value;
																		    								kvharb_d6=code;
																		    								kvhunit_d6=unit;
																		    								System.out.println("kvahValue value==================>"+kvahValue);
																		    							}*/
																		    						 	
																		    							
																		    						}
																		    						if(subNodeName.equalsIgnoreCase("B5"))
																		    						{
																		    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																		    							if(code.equalsIgnoreCase("P7-6-5-2-4"))
																		    							{
																		    								arb=code;
																		    								kvaValue = value;
																		    								kva_d6=value;
																		    								kvaarb_d6=code;
																		    								flag4_d6=value;
																		    								kvaunit_d6=unit;
																		    								d6_OccDate = occDate;
//																		    								System.out.println("kva value==================>"+kvaValue);
																		    							}
																		    							/*else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d6.equalsIgnoreCase("0"))
																		    							{
																		    								arb=code;
																		    								kvaValue = value;
																		    								kva_d6=value;
																		    								kvaarb_d6=code;
																		    								kvaunit_d6=unit;
																											flag5_d6=value;
																		    							}*/
																										/*else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
																		    							{
																		    								arb=code;
																		    								kvaValue = value;
																		    								kva_d6=value;
																		    								kvaarb_d6=code;
																		    								kvaunit_d6=unit;
																											//flag5_d6=value;
																		    							}*/
																		    						}
																		    						 
																		    						if(subNodeName.equalsIgnoreCase("B9"))
																		    						{
																		    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																		    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																		    							{
																		    								arb=code;
																		    								pfValue = value;
																		    								
																		    								pf_d6=value;
																		    								pfarb_d6=code;
//																		    								System.out.println("pfValue value==================>"+pfValue);
																		    							}
																		    						}
																		    						
																		    					}//End of d3-06
																		    					}
																    									
																    			//HPL																		  
																    			if(manufacturerCode.equalsIgnoreCase("6")||manufacturerName.equalsIgnoreCase("HPL Electric & Power Pvt. Ltd.")||manufacturerName.equalsIgnoreCase("AVON METERS PVT LTD")||manufacturerName.equalsIgnoreCase("HPL Electric & Power Ltd."))
																    			  {
																    				//System.out.println("Inside HPL data");
																    				if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-13-2-0") || code.equalsIgnoreCase("P7-1-13-1-0") || code.equalsIgnoreCase("P7-1-5-1-0") || code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue+"kwharb-->"+kwharb);
															    							}
															    							else if((code.equalsIgnoreCase("P7-1-18-0-0"))  && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue+"kwharb-->"+kwharb);
															    							}
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh= value;
															    								kwhunit=unit;
															    								kwharb=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue+"kwharb-->"+kwharb);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-1-0")|| code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								System.out.println("kvahValue value==================>"+kvahValue+"kvharb-->"+kvharb);
															    							}
															    							else if((code.equalsIgnoreCase("P7-3-18-0-0"))  && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue+"kvharb-->"+kvharb);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								flag3=value;
															    								kvharb=code;
															    								
															    								kvhunit=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue+"kvharb_d2-->"+kvharb_d2);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
															    								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue+"kvaarb-->"+kvaarb);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								flag5=value;
																								d1_OccDate = occDate;
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								flag5=value;
																								d1_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								//flag5=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")|| (code.equalsIgnoreCase("P4-4-4-0-0")))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf=value;
															    								pfarb=code;
//															    								System.out.println("pfValue value==================>"+pfValue+"pfarb-->"+pfarb);
															    							}
															    						}
															    						
															    					}
															    					
															    					//For d3-02
															    					else
															    					if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0"))|| (code.equalsIgnoreCase("P7-1-5-1-0"))|| (code.equalsIgnoreCase("P7-1-5-2-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-1-18-0-0"))  && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kvharb_d2=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								flag3_d2=value;
															    								kvharb_d2=code;
															    								
															    								kvhunit_d2=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-18-0-0"))  && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
															    								d2_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								flag5_d2=value;
																								d2_OccDate = occDate;
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								flag5_d2=value;
																								d2_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								//flag5_d2=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||(code.equalsIgnoreCase("P4-4-4-0-0")))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d2=value;
															    								pfarb_d2=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of d3-02
																    				
																    				//Begin of D3-03
															    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || code.equalsIgnoreCase("P7-1-13-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||
															    									code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d3= value;
															    								kwhunit_d3=unit;
															    								kwharb_d3=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-18-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								flag2_d3=value;
															    								kvharb_d3=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								flag3_d3=value;
															    								kvharb_d3=code;
															    								
															    								kvhunit_d3=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-3-5-0-0")||code.equalsIgnoreCase("P7-3-5-2-0")) && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-2-0")||code.equalsIgnoreCase("P7-6-18-0-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								flag4_d3=value;
															    								kvaunit_d3=unit;
															    								d3_OccDate=occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								flag5_d3=value;
																								d3_OccDate=occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								//flag5_d3=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d3=value;
															    								pfarb_d3=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					
															    					}//end of D3-03
															    					
																    				//Begin of D3-04
															    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||
															    									code.equalsIgnoreCase("P7-1-5-2-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d4=unit;
															    								flag1_d4=value;
															    								kwh_d4= value;
															    								kwharb_d4=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d4= value;
															    								kwhunit_d4=unit;
															    								kwharb_d4=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-18-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvhunit_d4=unit;
															    								flag2_d4=value;
															    								kvharb_d4=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								flag3_d4=value;
															    								kvharb_d4=code;
															    								
															    								kvhunit_d4=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-2-0")||code.equalsIgnoreCase("P7-6-18-0-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								flag4_d4=value;
															    								kvaunit_d4=unit;
															    								d4_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								flag5_d4=value;
																								d4_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								//flag5_d4=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d4=value;
															    								pfarb_d4=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of D3-04
																    				
																    				//Begin of D3-05
															    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")||
															    									code.equalsIgnoreCase("P7-1-5-2-0")||code.equalsIgnoreCase("P7-1-18-0-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d5=unit;
															    								flag1_d5=value;
															    								kwh_d5= value;
															    								kwharb_d5=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d5= value;
															    								kwhunit_d5=unit;
															    								kwharb_d5=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-18-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvhunit_d5=unit;
															    								flag2_d5=value;
															    								kvharb_d5=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								flag3_d5=value;
															    								kvharb_d5=code;
															    								kvhunit_d5=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvharb_d5=code;
															    								kvhunit_d5=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-2-0")||code.equalsIgnoreCase("P7-6-18-0-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								flag4_d5=value;
															    								kvaunit_d5=unit;
															    								d5_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								flag5_d5=value;
																								d5_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								//flag5_d5=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf_d5=value;
															    								pfarb_d5=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of D3-05
																    				
																    				//Begin of D3-06
															    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || code.equalsIgnoreCase("P7-1-18-0-0")||
															    									(code.equalsIgnoreCase("P7-1-13-1-0")||code.equalsIgnoreCase("P7-1-5-2-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d6=unit;
															    								flag1_d6=value;
															    								kwh_d6= value;
															    								kwharb_d6=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d6= value;
															    								kwhunit_d6=unit;
															    								kwharb_d6=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-18-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvhunit_d6=unit;
															    								flag2_d6=value;
															    								kvharb_d6=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								flag3_d6=value;
															    								kvharb_d6=code;
															    								kvhunit_d6=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvharb_d6=code;
															    								kvhunit_d6=unit;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-2-0")||code.equalsIgnoreCase("P7-6-18-0-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								flag4_d6=value;
															    								kvaunit_d6=unit;
															    								d6_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								flag5_d6=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								//flag5_d6=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d6=value;
															    								pfarb_d6=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of D3-06
																    				
																    			}//end of hpl
																    			
																    			if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																    			{
																    				//System.out.println("Inside LNT");
																    				if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								//flag1=value;
															    								kwh= value;
															    								kwharb=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    						
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								//flag2=value;
															    								kvharb=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								//flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf=value;
															    								pfarb=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
																    				//For d3-02
															    					else if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								//flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
//															    								System.out.println("kwhValue value==================>"+kwhValue);
																							}
															    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								//flag2_d2=value;
															    								kvharb_d2=code;
//															    								System.out.println("kvahValue value==================>"+kvahValue);
																							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
															    							}
																							else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d2.equalsIgnoreCase("0"))
																							{
																								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								//flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
//															    								System.out.println("kva value==================>"+kvaValue);
																							}
															    						
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf_d2=value;
															    								pfarb_d2=code;
//															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    					}//End of d3-02

																    				//Begin of d3-03
																	    			else if(d3TagCount.equalsIgnoreCase("D3-03"))
																		    			{
																    						if(subNodeName.equalsIgnoreCase("B3"))
																    						{
																    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
																    							{
																    								arb=code;
																    								kwhValue = value;
																    								kwhunit_d3=unit;
																    								flag1_d3=value;
																    								kwh_d3= value;
																    								kwharb_d3=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d3.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kwhValue = value;
																    								kwhunit_d3=unit;
																    								//flag1_d3=value;
																    								kwh_d3= value;
																    								kwharb_d3=code;
//																    								System.out.println("kwhValue value==================>"+kwhValue);
																								}
																    							
																    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d3= value;
																    								kvhunit_d3=unit;
																    								flag2_d3=value;
																    								kvharb_d3=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d3.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvahValue = value;
																    								kvh_d3= value;
																    								kvhunit_d3=unit;
																    								//flag2_d3=value;
																    								kvharb_d3=code;
//																    								System.out.println("kvahValue value==================>"+kvahValue);
																								}
																    							
																    						}
																    						if(subNodeName.equalsIgnoreCase("B5"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																    							{
																    								arb=code;
																    								kvaValue = value;
																    								kva_d3=value;
																    								kvaarb_d3=code;
																    								flag4_d3=value;
																    								kvaunit_d3=unit;
																									d3_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																    							}
																								else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d3.equalsIgnoreCase("0"))
																								{
																									arb=code;
																    								kvaValue = value;
																    								kva_d3=value;
																    								kvaarb_d3=code;
																    								//flag4_d3=value;
																    								kvaunit_d3=unit;
																									d3_OccDate = occDate;
//																    								System.out.println("kva value==================>"+kvaValue);
																								}
																    						
																    						}
																    						 
																    						if(subNodeName.equalsIgnoreCase("B9"))
																    						{
																    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																    							{
																    								arb=code;
																    								pfValue = value;
																    								
																    								pf_d3=value;
																    								pfarb_d3=code;
//																    								System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
																    					}
																    				 	//End of d3-03
																    					
																    				//Begining of d3-04
																    					else
																	    					if(d3TagCount.equalsIgnoreCase("D3-04"))
																	    					{
																	    						if(subNodeName.equalsIgnoreCase("B3"))
																	    						{
																	    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																	    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
																	    							{
																	    								arb=code;
																	    								kwhValue = value;
																	    								kwhunit_d4=unit;
																	    								flag1_d4=value;
																	    								kwh_d4= value;
																	    								kwharb_d4=code;
																	    							//System.out.println("kwhValue value==================>"+kwhValue);
																	    							}
																									else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d4.equalsIgnoreCase("0"))
																									{
																										arb=code;
																	    								kwhValue = value;
																	    								kwhunit_d4=unit;
																	    								//flag1_d4=value;
																	    								kwh_d4= value;
																	    								kwharb_d4=code;
																	    								//System.out.println("kwhValue value==================>"+kwhValue);
																									}
																	    							
																	    							
																	    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d4= value;
																	    								kvhunit_d4=unit;
																	    								flag2_d4=value;
																	    								kvharb_d4=code;
																	    								//System.out.println("kvahValue value==================>"+kvahValue);
																	    							}
																									else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d4.equalsIgnoreCase("0"))
																									{
																										arb=code;
																	    								kvahValue = value;
																	    								kvh_d4= value;
																	    								kvhunit_d4=unit;
																	    								//flag2_d4=value;
																	    								kvharb_d4=code;
																	    								//System.out.println("kvahValue value==================>"+kvahValue);
																									}
																	    							
																	    						}
																	    						if(subNodeName.equalsIgnoreCase("B5"))
																	    						{
																	    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																	    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																	    							{
																	    								arb=code;
																	    								kvaValue = value;
																	    								kva_d4=value;
																	    								kvaarb_d4=code;
																	    								flag4_d4=value;
																	    								kvaunit_d4=unit;
																										d4_OccDate = occDate;
																										//System.out.println("kva value==================>"+kvaValue);
																	    							}
																									else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d4.equalsIgnoreCase("0"))
																									{
																										arb=code;
																	    								kvaValue = value;
																	    								kva_d4=value;
																	    								kvaarb_d4=code;
																	    								//flag4_d4=value;
																	    								kvaunit_d4=unit;
																										d4_OccDate = occDate;
																										//System.out.println("kva value==================>"+kvaValue);
																									}
																	    							
																	    						}
																	    						 
																	    						if(subNodeName.equalsIgnoreCase("B9"))
																	    						{
																	    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																	    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																	    							{
																	    								arb=code;
																	    								pfValue = value;
																	    								
																	    								pf_d4=value;
																	    								pfarb_d4=code;
																	    								//System.out.println("pfValue value==================>"+pfValue);
																	    							}
																	    						}
																	    					}
																    					
																    					//end of d3-04
																    					
																    					//Begining of d3-05
																    					
																	    					else
																		    					if(d3TagCount.equalsIgnoreCase("D3-05"))
																		    					{
																		    						if(subNodeName.equalsIgnoreCase("B3"))
																		    						{
																		    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																		    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
																		    							{
																		    								arb=code;
																		    								kwhValue = value;
																		    								kwhunit_d5=unit;
																		    								flag1_d5=value;
																		    								kwh_d5= value;
																		    								kwharb_d5=code;
																		    								//System.out.println("kwhValue value==================>"+kwhValue);
																		    							}
																										else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d5.equalsIgnoreCase("0"))
																										{
																											arb=code;
																		    								kwhValue = value;
																		    								kwhunit_d5=unit;
																		    								//flag1_d5=value;
																		    								kwh_d5= value;
																		    								kwharb_d5=code;
																		    								//System.out.println("kwhValue value==================>"+kwhValue);
																										}
																		    							
																		    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																		    							{
																		    								arb=code;
																		    								kvahValue = value;
																		    								kvh_d5= value;
																		    								kvhunit_d5=unit;
																		    								flag2_d5=value;
																		    								kvharb_d5=code;
																		    								//System.out.println("kvahValue value==================>"+kvahValue);
																		    							}
																										else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d5.equalsIgnoreCase("0"))
																										{
																											arb=code;
																		    								kvahValue = value;
																		    								kvh_d5= value;
																		    								kvhunit_d5=unit;
																		    								//flag2_d5=value;
																		    								kvharb_d5=code;
																		    								//System.out.println("kvahValue value==================>"+kvahValue);
																										}
																		    							
																		    						}
																		    						if(subNodeName.equalsIgnoreCase("B5"))
																		    						{
																		    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																		    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																		    							{
																		    								arb=code;
																		    								kvaValue = value;
																		    								kva_d5=value;
																		    								kvaarb_d5=code;
																		    								flag4_d5=value;
																		    								kvaunit_d5=unit;
																											d5_OccDate = occDate;
																											//System.out.println("kva value==================>"+kvaValue);
																		    							}
																										else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d5.equalsIgnoreCase("0"))
																										{
																											arb=code;
																		    								kvaValue = value;
																		    								kva_d5=value;
																		    								kvaarb_d5=code;
																		    								//flag4_d5=value;
																		    								kvaunit_d5=unit;
																											d5_OccDate = occDate;
																											//System.out.println("kva value==================>"+kvaValue);
																										}
																		    							
																		    						}
																		    						 
																		    						if(subNodeName.equalsIgnoreCase("B9"))
																		    						{
																		    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																		    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																		    							{
																		    								arb=code;
																		    								pfValue = value;
																		    								
																		    								pf_d5=value;
																		    								pfarb_d5=code;
																		    								//System.out.println("pfValue value==================>"+pfValue);
																		    							}
																		    						}
																		    					}	//end of d3-05
																    					
																    				
																    					
																    					//Begining of d3-06
																		    					else
																			    					if(d3TagCount.equalsIgnoreCase("D3-06"))
																			    					{
																			    						if(subNodeName.equalsIgnoreCase("B3"))
																			    						{
																			    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																			    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
																			    							{
																			    								arb=code;
																			    								kwhValue = value;
																			    								kwhunit_d6=unit;
																			    								flag1_d6=value;
																			    								kwh_d6= value;
																			    								kwharb_d6=code;
																			    								//System.out.println("kwhValue value==================>"+kwhValue);
																			    							}
																											else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d6.equalsIgnoreCase("0"))
																											{
																												arb=code;
																			    								kwhValue = value;
																			    								kwhunit_d6=unit;
																			    								//flag1_d6=value;
																			    								kwh_d6= value;
																			    								kwharb_d6=code;
																			    								//System.out.println("kwhValue value==================>"+kwhValue);
																											}
																			    							
																			    							
																			    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
																			    							{
																			    								arb=code;
																			    								kvahValue = value;
																			    								kvh_d6= value;
																			    								kvhunit_d6=unit;
																			    								flag2_d6=value;
																			    								kvharb_d6=code;
																			    								//System.out.println("kvahValue value==================>"+kvahValue);
																			    							}
																											else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d6.equalsIgnoreCase("0"))
																											{
																												arb=code;
																			    								kvahValue = value;
																			    								kvh_d6= value;
																			    								kvhunit_d6=unit;
																			    								//flag2_d6=value;
																			    								kvharb_d6=code;
																			    								//System.out.println("kvahValue value==================>"+kvahValue);
																											}															    							
																			    						}
																			    						if(subNodeName.equalsIgnoreCase("B5"))
																			    						{
																			    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																			    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
																			    							{
																			    								arb=code;
																			    								kvaValue = value;
																			    								kva_d6=value;
																			    								kvaarb_d6=code;
																			    								flag4_d6=value;
																			    								kvaunit_d6=unit;
																												d6_OccDate = occDate;
																												//System.out.println("kva value==================>"+kvaValue);
																			    							}
																											else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d6.equalsIgnoreCase("0"))
																											{
																												arb=code;
																			    								kvaValue = value;
																			    								kva_d6=value;
																			    								kvaarb_d6=code;
																			    								//flag4_d6=value;
																			    								kvaunit_d6=unit;
																												d6_OccDate = occDate;
																												//System.out.println("kva value==================>"+kvaValue);
																											}
																			    						}
																			    						 
																			    						if(subNodeName.equalsIgnoreCase("B9"))
																			    						{
																			    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																			    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
																			    							{
																			    								arb=code;
																			    								pfValue = value;
																			    								
																			    								pf_d6=value;
																			    								pfarb_d6=code;
																			    								//System.out.println("pfValue value==================>"+pfValue);
																			    							}
																			    						}
																			    					}//End of d3-06
																    				}// End Of LNT
																    			if(manufacturerCode.equalsIgnoreCase("101"))
																    			{
																    				//System.out.println("inside LNG");
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
															    							//System.out.println("kwhValue=D3-01=================>"+kwhValue);
															    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    							}
															    					        // System.out.println("kvahValue=D3-01=================>"+kvahValue);
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
															    							//System.out.println("kvaValue=D3-01=================>"+kvaValue);
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
															    							//System.out.println("pfValue=D3-01=================>"+pfValue);
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
															    								//System.out.println("kwhValue=D3-02=================>"+kwhValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
															    								//System.out.println("kvahValue=D3-02=================>"+kvahValue);
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
																								//System.out.println("kvavalue=D3-02=================>"+kvaValue);
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
															    								//System.out.println("pfValue=D3-02=================>"+pfValue);
															    							}
															    						}
															    					}//end of D3-02
															    						// begin of D3-03
															    						else if(d3TagCount.equalsIgnoreCase("D3-03"))
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
																    								//	System.out.println("kwhValue=D3-03=================>"+kwhValue);
																    							}
																    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d3= value;
																    								kvhunit_d3=unit;
																    								flag2_d3=value;
																    								kvharb_d3=code;
																    								//System.out.println("kvahValue=D3-03=================>"+kvahValue);
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
																									//System.out.println("kvavalue=D3-03=================>"+kvaValue);
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
																    								//System.out.println("pfValue=D3-03=================>"+pfValue);
																    							}
																    						}
															    						}// end of D3-03
															    						
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
																    								//System.out.println("kwhValue=D3-04=================>"+kwhValue);
																    							}
																    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d4= value;
																    								kvhunit_d4=unit;
																    								flag2_d4=value;
																    								kvharb_d4=code;
																    								//System.out.println("kvahValue=D3-04=================>"+kvahValue);
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
																									//System.out.println("kvavalue=D3-04=================>"+kvaValue);
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
																    								//System.out.println("pfValue=D3-04=================>"+pfValue);
																    							}
																    						}
															    						}//end of d3-04
																    				
																    				//Begin of D3-05
															    						else if(d3TagCount.equalsIgnoreCase("D3-05"))
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
																    								//System.out.println("kwhValue value==================>"+kwhValue);
																    							}
																    							
																    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																    							{
																    								arb=code;
																    								kvahValue = value;
																    								kvh_d5= value;
																    								kvhunit_d5=unit;
																    								flag2_d5=value;
																    								kvharb_d5=code;
																    								//System.out.println("kvahValue value==================>"+kvahValue);
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
																									//System.out.println("kva value==================>"+kvaValue);
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
																    								//System.out.println("pfValue value==================>"+pfValue);
																    							}
																    						}
																    					}// end of D3-05
																    				//Begin Of D3-06
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
																	    								//	System.out.println("kwhValue=D3-06=================>"+kwhValue);
																	    							}
																	    					        if(code.equalsIgnoreCase("P7-3-13-0-0"))
																	    							{
																	    								arb=code;
																	    								kvahValue = value;
																	    								kvh_d6= value;
																	    								kvhunit_d6=unit;
																	    								flag2_d6=value;
																	    								kvharb_d6=code;
																	    								//	System.out.println("kvahValue=D3-06=================>"+kvahValue);
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
																										//	System.out.println("kvavalue=D3-06=================>"+kvaValue);
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
																	    								//System.out.println("pfValue=D3-06=================>"+pfValue);
																	    							}
																	    						}
																	    					}// end of d3-06
																    				
															    					}//end of LNG
																    			
																    			//Begin of Secure
																    			if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																    			{
																    				//System.out.println("inside secure");
																    				if(d3TagCount.equalsIgnoreCase("D3-01"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")||(code.equalsIgnoreCase("P7-1-18-2-0")))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-1-18-0-0")) && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh= value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwharb=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit=unit;
															    								kwh= value;
															    								kwharb=code;
															    								//System.out.println("kwhValue P7-1-5-2-0 value==================>"+flag1);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh= value;
															    								kwhunit=unit;
															    								flag1=value;
															    								kwharb=code;
															    								System.out.println("kwhValue P7-1-18-1-0 value==================>"+flag1);
															    							}
																						//Export reading KWH by Rajguru
																						else if(code.equalsIgnoreCase("P7-1-6-1-0"))
															    							{
															    								ekwh = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah = value;
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								kvharb=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")|| code.equalsIgnoreCase("P7-3-5-1-0") || code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvhunit=unit;
															    								flag2=value;
															    								kvharb=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								flag3=value;
															    								kvharb=code;
															    								
															    								kvhunit=unit;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-3-18-2-0")||code.equalsIgnoreCase("P7-6-5-1-0")) && flag2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								flag3=value;
															    								kvharb=code;
															    								kvhunit=unit;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2.equalsIgnoreCase("0") &&  flag3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh= value;
															    								kvharb=code;
															    								kvhunit=unit;
																								flag6=value;
																								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
																								//	System.out.println("kva value==================>"+kvaValue);
															    							}
																						//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva = value;
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								flag5=value;
																								d1_OccDate = occDate;
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-2-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								flag5=value;
																								d1_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
																								//flag5=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
																								//flag5=value;
															    							}
															    						}
															    						 if(subNodeName.equalsIgnoreCase("B6"))
															    						{
															    						if(code.equalsIgnoreCase("P7-6-5-0-0")&& flag4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva=value;
															    								kvaarb=code;
															    								flag4=value;
															    								kvaunit=unit;
																								d1_OccDate = occDate;
															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-6-0-0")&& ekva.equalsIgnoreCase("0"))
															    							{
															    								ekva = value;
															    							}
																					}
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf=value;
															    								pfarb=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
																						else if(code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf=value;
															    								pfarb=code;
															    							}
																						//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf = value;
															    							}
															    						}
															    						
															    					}
															    					
															    					//For d3-02
															    					else
															    					if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kwharb_d2=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kwharb_d2=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kwharb_d2=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kvharb_d2=code;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								flag3_d2=value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-2-0") && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								flag3_d2=value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
															    								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
																								flag6_d2=value;
																								//	System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					      //Export reading KWH by Rajguru
																							else if(code.equalsIgnoreCase("P7-1-6-1-0"))
																    							{
																    								ekwh2 = value;
																    							}
																							//Export reading KVAH  by Rajguru
																							else if(code.equalsIgnoreCase("P7-3-6-0-0"))
																    							{
																    								ekvah2 = value;
																    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
																								//	System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-6-18-0-0")||code.equalsIgnoreCase("P7-6-18-2-0")) && flag4_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								flag5_d2=value;
																								d2_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
																								//flag5_d2=value;
															    							}								
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
																								//flag5=value;
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P7-4-6-1-0"))
																    							{
																    								ekva2 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-4-6-2-0"))
																    							{
																    								ekva2 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-6-6-0-0"))
															    							{
															    								ekva2 = value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf_d2=value;
															    								pfarb_d2=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P4-4-4-2-0"))
																    							{
																    								epf2 = value;
																    							}
															    						}
															    						
															    					}
															    					//End of d3-02
															    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d3= value;
															    								kwhunit_d3=unit;
															    								kwharb_d3=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d3= value;
															    								kwhunit_d3=unit;
															    								kwharb_d3=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								flag2_d3=value;
															    								kvharb_d3=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								flag3_d3=value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-18-2-0") && flag2_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								flag3_d3=value;
															    								kvharb_d3=code;
															    								
															    								kvhunit_d3=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
																								flag6_d3=value;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					      //Export reading KWH by Rajguru
																							else if(code.equalsIgnoreCase("P7-1-6-1-0"))
																    							{
																    								ekwh3 = value;
																    							}
																							//Export reading KVAH  by Rajguru
																							else if(code.equalsIgnoreCase("P7-3-6-0-0"))
																    							{
																    								ekvah3 = value;
																    							}
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								flag4_d3=value;
															    								kvaunit_d3=unit;
																								d3_OccDate=occDate;
															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-6-18-0-0")|| code.equalsIgnoreCase("P7-6-18-2-0"))&& flag4_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								flag5_d3=value;
																								d3_OccDate=occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								d3_OccDate=occDate;
																								//flag5_d3=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								d3_OccDate=occDate;
																								//flag5_d3=value;
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P7-4-6-1-0"))
																    							{
																    								ekva3 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-4-6-2-0"))
																    							{
																    								ekva3 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-6-6-0-0"))
															    							{
															    								ekva3 = value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								pf_d3=value;
															    								pfarb_d3=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P4-4-4-2-0"))
																    							{
																    								epf3 = value;
																    							}
															    						}
															    					
															    					}//End of d3-03
															    					//Begining of d3-04
															    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d4=unit;
															    								flag1_d4=value;
															    								kwh_d4= value;
															    								kwharb_d4=code;
															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d4= value;
															    								kwhunit_d4=unit;
															    								kwharb_d4=code;
																							flag1_d4=value;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d4= value;
															    								kwhunit_d4=unit;
															    								kwharb_d4=code;
																							    flag1_d4=value;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d4.equalsIgnoreCase("0"))
														    								{
														    									arb=code;
															    								kwhValue = value;
															    								kwhunit_d4=unit;
															    								flag1_d4=value;
															    								kwh_d4= value;
															    								kwharb_d4=code;
														    									System.out.println("kvahValue value==================>"+kvahValue);
														    								}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvhunit_d4=unit;
															    								flag2_d4=value;
															    								kvharb_d4=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								flag3_d4=value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-2-0") && flag2_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								flag3_d4=value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
																								flag3_d4=value;
																								flag6_d4=value;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					      //Export reading KWH by Rajguru
																							else if(code.equalsIgnoreCase("P7-1-6-1-0"))
																    							{
																    								ekwh4 = value;
																    							}
																							//Export reading KVAH  by Rajguru
																							else if(code.equalsIgnoreCase("P7-3-6-0-0"))
																    							{
																    								ekvah4 = value;
																    							}
															    						 	
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								flag4_d4=value;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-6-18-0-0")||code.equalsIgnoreCase("P7-6-18-2-0"))&& flag4_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								flag5_d4=value;
																								d4_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
																								//flag5_d4=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
																								//flag5_d4=value;
															    							}
															    							
															    							//Export kva by Rajguru
																							else if(code.equalsIgnoreCase("P7-4-6-1-0"))
																    							{
																    								ekva4 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-4-6-2-0"))
																    							{
																    								ekva4 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-6-6-0-0"))
															    							{
															    								ekva4 = value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d4=value;
															    								pfarb_d4=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P4-4-4-2-0"))
																    							{
																    								epf4 = value;
																    							}
															    						}
															    					
															    					}//end of d3-04
																    				//begin of D3-05
															    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
															    					{

															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d5=unit;
															    								flag1_d5=value;
															    								kwh_d5= value;
															    								kwharb_d5=code;
															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d5= value;
															    								kwhunit_d5=unit;
															    								kwharb_d5=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d5= value;
															    								kwhunit_d5=unit;
															    								kwharb_d5=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d5.equalsIgnoreCase("0"))
														    								{
													    										arb=code;
															    								kwhValue = value;
															    								kwhunit_d5=unit;
															    								flag1_d5=value;
															    								kwh_d5= value;
															    								kwharb_d5=code;
													    										System.out.println("kvahValue value==================>"+kvahValue);
													    									}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvhunit_d5=unit;
															    								flag2_d5=value;
															    								kvharb_d5=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								flag3_d5=value;
															    								kvharb_d5=code;
															    								kvhunit_d5=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-2-0") && flag2_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								flag3_d5=value;
															    								kvharb_d5=code;
															    								kvhunit_d5=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvharb_d5=code;
																								flag6_d5=value;
															    								kvhunit_d5=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					        
															    					      //Export reading KWH by Rajguru
																							else if(code.equalsIgnoreCase("P7-1-6-1-0"))
																    							{
																    								ekwh5 = value;
																    							}
																							//Export reading KVAH  by Rajguru
																							else if(code.equalsIgnoreCase("P7-3-6-0-0"))
																    							{
																    								ekvah5 = value;
																    							}
															    						 	
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
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
															    							else if((code.equalsIgnoreCase("P7-6-18-0-0")||code.equalsIgnoreCase("P7-6-18-2-0"))&& flag4_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								flag5_d5=value;
																								d5_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								d5_OccDate = occDate;
																								//flag5_d5=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								d5_OccDate = occDate;
																								//flag5_d5=value;
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P7-4-6-1-0"))
																    							{
																    								ekva5 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-4-6-2-0"))
																    							{
																    								ekva5 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-6-6-0-0"))
															    							{
															    								ekva5 = value;
															    							}
															    							
															    							
															    							
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d5=value;
															    								pfarb_d5=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P4-4-4-2-0"))
																    							{
																    								epf5 = value;
																    							}
															    						}
															    					
															    					}//End of D3-05 
																    				
																    				//Begin of D3=06
															    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d6=unit;
															    								flag1_d6=value;
															    								kwh_d6= value;
															    								kwharb_d6=code;
															    								System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d6= value;
															    								kwhunit_d6=unit;
															    								kwharb_d6=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d6= value;
															    								kwhunit_d6=unit;
															    								kwharb_d6=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d6.equalsIgnoreCase("0"))
												    										{
												    											arb=code;
															    								kwhValue = value;
															    								kwhunit_d6=unit;
															    								flag1_d6=value;
															    								kwh_d6= value;
															    								kwharb_d6=code;
												    											System.out.println("kvahValue value==================>"+kvahValue);
												    										}
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvhunit_d6=unit;
															    								flag2_d6=value;
															    								kvharb_d6=code;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								flag3_d6=value;
															    								kvharb_d6=code;
															    								
															    								kvhunit_d6=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if((code.equalsIgnoreCase("P7-3-18-2-0")||code.equalsIgnoreCase("P7-6-18-2-0")) && flag2_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								flag3_d6=value;
															    								kvharb_d6=code;
															    								
															    								kvhunit_d6=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvharb_d6=code;
																								flag6_d6=value;
															    								kvhunit_d6=unit;
															    								System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    					      //Export reading KWH by Rajguru
																							else if(code.equalsIgnoreCase("P7-1-6-1-0"))
																    							{
																    								ekwh6 = value;
																    							}
																							//Export reading KVAH  by Rajguru
																							else if(code.equalsIgnoreCase("P7-3-6-0-0"))
																    							{
																    								ekvah6 = value;
																    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								flag4_d6=value;
															    								kvaunit_d6=unit;
																								d6_OccDate = occDate;
															    								System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if((code.equalsIgnoreCase("P7-6-18-0-0")||code.equalsIgnoreCase("P7-6-18-2-0"))&& flag4_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								flag5_d6=value;
																								d6_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								d6_OccDate = occDate;
																								//flag5_d6=value;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								d6_OccDate = occDate;
																								//flag5_d6=value;
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P7-4-6-1-0"))
																    							{
																    								ekva6 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-4-6-2-0"))
																    							{
																    								ekva6 = value;
																    							}
																							else if(code.equalsIgnoreCase("P7-6-6-0-0"))
															    							{
															    								ekva6 = value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d6=value;
															    								pfarb_d6=code;
															    								System.out.println("pfValue value==================>"+pfValue);
															    							}
															    							//Export PF by Rajguru
																							else if(code.equalsIgnoreCase("P4-4-4-2-0"))
																    							{
																    								epf6 = value;
																    							}
															    						}
															    					
															    					}
																    			} // end of secure
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
																System.out.println(subnodeListD4.getLength());
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
																				//System.out.println("dayProfileDate-->"+nodeMapIndex+"------"+dayProfileDate);
																				try {
																					
																				/*if(Integer.parseInt(dayProfileDate))*/
																					
																				rsForDateCheck = st.executeQuery("SELECT TO_CHAR(ADD_MONTHS(TO_DATE('"+dayProfileDate+"','DD/MM/YYYY'),1),'YYYYMM') FROM DUAL");
																				if(rsForDateCheck.next())
																				{
																					profileDateYearMonth = rsForDateCheck.getString(1);
																				}
																				rsForDateCheck.close();
																				} catch (Exception e) {
																					System.out.println("");
																				}
																				
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
						    
						    String DATE_FORMAT2  = "dd/MM/yyyy";
						    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(DATE_FORMAT2);
						    String DATE_FORMAT3  = "yyyymm";
						    java.text.SimpleDateFormat sdf3 = new java.text.SimpleDateFormat(DATE_FORMAT3);
						    
						    String DATE_FORMAT4  = "dd-MM-yyyy";
						    java.text.SimpleDateFormat sdf4 = new java.text.SimpleDateFormat(DATE_FORMAT4);
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
									rsForD3=st.executeQuery("SELECT * FROM XMLIMPORT WHERE METERNO='"+meterno.trim()+"' AND DATESTAMP='"+current_date+"'");
									if(!rsForD3.next())
									{
										int importStatus=	st.executeUpdate("INSERT INTO XMLIMPORT(METERNO, DATESTAMP, G2VALUE, G3VALUE) values('"+meterno.trim()+"','"+current_date+"','"+g2Value+"','"+g3Value+"')");
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
		//date cmri				
							String yyyyMM1 = "",yyyyMM2 = "",yyyyMM3 = "",yyyyMM4 = "",yyyyMM5 = "",yyyyMM6 = "";
							//Date for d3-01
							if(d3_01_flag && (rdate != ""))
							{
								Date date = (Date)sdf4.parse(rdate);
								//System.err.println("date-->"+date);
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
								c1.add(Calendar.MONTH,-1);
								System.out.println("Date + 1 month is : " + sdf.format(c1.getTime()));
								String rdate_substracted=sdf.format(c1.getTime());
								System.out.println("rdate==------"+rdate);
								 yyyyMM1 = sdfBillDate.format(date);
								System.out.println("rDate yyyyMM : "+yyyyMM1);
								if(yyyyMM1.startsWith("0"))
								{
									yyyyMM1 = yyyyMM1.substring(2);
									yyyyMM1 = "20"+yyyyMM1;
									System.out.println("rDate1 yyyyMM after modify : "+yyyyMM1);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM1))
								{
									dateCount1 = d4DayProfileCount + "";
								}
						    }
							//end
						    
						    //Date for d3-02
							if(d3_02_flag && (rdate_d2 != ""))
							{
								Date date2 = (Date)sdf4.parse(rdate_d2);
								c1.setTime(date2); 
								//System.err.println("date2-->"+date2);
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
								if(yyyyMM2.startsWith("0"))
								{
									yyyyMM2 = yyyyMM2.substring(2);
									yyyyMM2 = "20"+yyyyMM2;
									System.out.println("rDate2 yyyyMM after modify : "+yyyyMM2);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM2))
								{
									dateCount2 = d4DayProfileCount + "";
								}
							}
						    //End
						    
						  //Date for d3-03
							if(d3_03_flag && (rdate_d3 != ""))
							{
								Date date3 = (Date)sdf4.parse(rdate_d3);
								//System.err.println("date3-->"+date3);
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
								if(yyyyMM3.startsWith("0"))
								{
									yyyyMM3 = yyyyMM3.substring(2);
									yyyyMM3 = "20"+yyyyMM3;
									System.out.println("rDate3 yyyyMM after modify : "+yyyyMM3);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM3))
								{
									dateCount3 = d4DayProfileCount + "";
								}
							}
						    //End
						    
						  //Date for d3-04
							if(d3_04_flag && (rdate_d4 != ""))
							{
								Date date4 = (Date)sdf4.parse(rdate_d4);
								//System.err.println("date4-->"+date4);
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
								if(yyyyMM4.startsWith("0"))
								{
									yyyyMM4 = yyyyMM4.substring(2);
									yyyyMM4 = "20"+yyyyMM4;
									System.out.println("rDate4 yyyyMM after modify : "+yyyyMM4);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM4))
								{
									dateCount4 = d4DayProfileCount + "";
								}
						    }
						    //End
						    
						    //Date for d3-05
							if(d3_05_flag && (rdate_d5 != ""))
							{
								Date date5 = (Date)sdf4.parse(rdate_d5);
								//System.err.println("date5-->"+date5);
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
								if(yyyyMM5.startsWith("0"))
								{
									yyyyMM5 = yyyyMM5.substring(2);
									yyyyMM5 = "20"+yyyyMM5;
									System.out.println("rDate5 yyyyMM after modify : "+yyyyMM5);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM5))
								{
									dateCount5 = d4DayProfileCount + "";
								}
						    }
						    //End
						    
						  //Date for d3-06
							if(d3_06_flag && (rdate_d6 != ""))
							{
								Date date6 = (Date)sdf4.parse(rdate_d6);
								//System.err.println("date6-->"+date6);
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
								if(yyyyMM6.startsWith("0"))
								{
									yyyyMM6 = yyyyMM6.substring(2);
									yyyyMM6 = "20"+yyyyMM6;
									System.out.println("rDate6 yyyyMM after modify : "+yyyyMM6);
									
								}
								if(dataMonth.equalsIgnoreCase(yyyyMM6))
								{
									dateCount6 = d4DayProfileCount + "";
								}
							}
							//System.out.println("dateCount : "+dateCount1+" - "+dateCount2+" - "+dateCount3+" - "+dateCount4+" - "+dateCount5+" - "+dateCount6);
							// System.out.println("rdate1 : "+rdate+ " rdate2 : "+rdate_d2+" rdate3 : " +rdate_d3+"rdate4 : "+rdate_d4+ "rdate5 : "+rdate_d5+ "rdate6 : "+rdate_d6);
						    //checking batchstatus for MM_TABLE column 0 for particular meterno
							// System.out.println("billmonth cmri-->"+billMonth);
						    long MMcount=cdfBatchService.checkMMUpdate(billMonth, meterno.trim());
						    System.out.println("MMcount cmri-->"+MMcount);
						    if(MMcount>0)
						    {
						    int delCount=cdfBatchService.deleteMtrInXmlImport(meterno.trim(),yyyyMM1);
						    System.out.println("delCount-->"+delCount);
						    }
						    else{
						    }
						    //End
						    
						    //checking wheather the data already exists or not
						    
						    ResultSet rset=null;
						    int y1,y2,y3,y4,y5,y6;
						    
						    if(g2Value!="" && !d3_exist){
								    if(rdate.isEmpty())
								    {
								    	System.out.println("rdate d3-01 is empty and G2value->"+g2Value);
								    	Date g2d1=sdf.parse(g2Value);
								    	
								    	yyyyMM1=sdfBillDate.format(g2d1);
								    	
								    	 y1=Integer.parseInt(yyyyMM1);
								    	 y2=Integer.parseInt(yyyyMM1)-1;
								    	 y3=Integer.parseInt(yyyyMM1)-2;
								    	 y4=Integer.parseInt(yyyyMM1)-3;
								    	 y5=Integer.parseInt(yyyyMM1)-4;
								    	 y6=Integer.parseInt(yyyyMM1)-5;
								    	 
								    	 	yyyyMM1=String.valueOf(y1);
									    	yyyyMM2=String.valueOf(y2);
									    	yyyyMM3=String.valueOf(y3);
									    	yyyyMM4=String.valueOf(y4);
									    	yyyyMM5=String.valueOf(y5);
									    	yyyyMM6=String.valueOf(y6);
								    	
								    }
						    }
						    try{
								if(d3_01_flag )
							{
							System.out.println("check inside  d3-01-->yyyyMM1"+yyyyMM1+"   rdate1-->"+rdate);
							
						    rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM1+"' and METERNO='"+meterno.trim()+"'");
						   
						    if(!rset.next())
						    {
						    	System.out.println("Inside d3-01 genus non dlms rdate1--"+rdate+"  meterno--"+meterno);
						    	//System.out.println(kwh+"/"+kvh+"/"+kva+"/"+pf+"/export Reading-->"+ekwh+"/"+ekvah+"/"+ekva+"/"+epf+"/aa"+kwharb+"/"+kvaarb+"/"+kvharb+"/"+pfarb);
						    	//status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno+"',TO_DATE('"+rdate+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh+"','"+kwhunit+"','"+kvh+"','"+kvhunit+"','"+kva+"','"+kvaunit+"','"+kwharb+"','"+pf+"','"+kvaarb+"','"+kvharb+"','"+pfarb+"','"+yyyyMM1+"','"+current_date+"','"+dateCount1+"','"+d1_OccDate+"','"+g2Value+"','"+g3Value+"')");
						    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno.trim()+"',TO_DATE('"+rdate+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh+"','"+kwhunit+"','"+kvh+"','"+kvhunit+"','"+kva+"','"+kvaunit+"','"+kwharb+"','"+pf+"','"+kvaarb+"','"+kvharb+"','"+pfarb+"','"+yyyyMM1+"','"+current_date+"','"+dateCount1+"','"+d1_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh+"','"+ekvah+"','"+ekva+"','"+epf+"')");
						    
						    }
							rset.close();
							}
								if(status==1){
									
									List dataMM=cdfBatchService.findMeterMasterDataForCURRMonth(yyyyMM1, meterno.trim());
									
									if(dataMM.size()>0){
										System.out.println("inside  d3-01-->yyyyMM1"+yyyyMM1);
										
										int dataUpdate=cdfBatchService.updateMMExportReading(yyyyMM1, meterno.trim(),  kwh, kvh, kva, pf, ekwh, ekvah, ekva, epf,todayDate);
										if(dataUpdate>0)
										{
											MetrFlagStatus="Msuccess";
											System.out.println("GENUS data updated to Meter Master Table successfully");
										}
										else
										{
											MetrFlagStatus="Mfailure";
										}
									}
									else
									{
										MetrFlagStatus="Mfailure";
									}
								}
								else
								{
									MetrFlagStatus="Mfailure";
								}
								if(kwh.isEmpty() || kva.isEmpty() || kvh.isEmpty()){
									MetrFlagStatus="Mfailure";
									
								}
								if(!billMonth.equalsIgnoreCase(yyyyMM1))
								{
									MetrFlagStatus="Mfailure";
									checkCrpt="D3-01 (month="+yyyyMM1+")";
								}
						    }catch (Exception e) {
								e.printStackTrace();
								writeLogFile("Error : "+e);
								System.exit(0);
							}
						    
						    try{
								if(d3_02_flag )
							{
							rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM2+"' and METERNO='"+meterno.trim()+"'");
						    //rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d2+"','DD-MM-YYYY hh24:mi:ss') and METERNO='"+meterno.trim()+"'");
						    if(!rset.next())
						    {
						    	//System.out.println("INSIDE D3-02");
						    	//System.out.println("status-->>>>"+status);
						    	int temp=status;
						    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno.trim()+"',TO_DATE('"+rdate_d2+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d2+"','"+kwhunit_d2+"','"+kvh_d2+"','"+kvhunit_d2+"','"+kva_d2+"','"+kvaunit_d2+"','"+kwharb_d2+"','"+pf_d2+"','"+kvaarb_d2+"','"+kvharb_d2+"','"+pfarb_d2+"','"+yyyyMM2+"', '"+current_date+"','"+dateCount2+"','"+d2_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh2+"','"+ekvah2+"','"+ekva2+"','"+epf2+"')");
						    	if(temp==0)
						    	{
						    		status=temp;
						    	}
						    }
							rset.close();
							}
						    }catch (Exception e) {
								e.printStackTrace();
								writeLogFile("Error : "+e);
								System.exit(0);
							}
						    try{
						    	if(!rdate_d3.startsWith("00"))
						    	{
								if(d3_03_flag )
									{
									rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM3+"' and METERNO='"+meterno.trim()+"'");
								    //rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d3+"','DD-MM-YYYY hh24:mi:ss') and METERNO='"+meterno+"'");
								    if(!rset.next())
								    {
								    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d3+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d3+"','"+kwhunit_d3+"','"+kvh_d3+"','"+kvhunit_d3+"','"+kva_d3+"','"+kvaunit_d3+"','"+kwharb_d3+"','"+pf_d3+"','"+kvaarb_d3+"','"+kvharb_d3+"','"+pfarb_d3+"','"+yyyyMM3+"', '"+current_date+"','"+dateCount3+"','"+d3_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh3+"','"+ekvah3+"','"+ekva3+"','"+epf3+"')");
								    	 
								    }
									rset.close();
									}
						    	}
						    }
						    catch (Exception e) {
								e.printStackTrace();
						    	writeLogFile("Error : "+e);
								System.exit(0);
							}
						   
						    try{
						    	if(!rdate_d4.startsWith("00"))
						    	{
								if(d3_04_flag )
									{
									rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM4+"' and METERNO='"+meterno.trim()+"'");
								  //  rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d4+"','DD-MM-YYYY hh24:mi:ss') and METERNO='"+meterno+"'");
								    if(!rset.next())
								    {
								    	System.out.println("rdate_d4-->"+rdate_d4+"   monthyyyyMM4-->"+yyyyMM4);
								    	 status=st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d4+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d4+"','"+kwhunit_d4+"','"+kvh_d4+"','"+kvhunit_d4+"','"+kva_d4+"','"+kvaunit_d4+"','"+kwharb_d4+"','"+pf_d4+"','"+kvaarb_d4+"','"+kvharb_d4+"','"+pfarb_d4+"','"+yyyyMM4+"', '"+current_date+"','"+dateCount4+"','"+d4_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh4+"','"+ekvah4+"','"+ekva4+"','"+epf4+"')");
								    	 
								    }
									rset.close();
									}
						    	}
						     }
						    catch (Exception e) {
								e.printStackTrace();
						    	writeLogFile("Error : "+e);
								System.exit(0);
							}
						    
						    try{
						    	if(!rdate_d5.startsWith("00"))
						    	{
										if(d3_05_flag )
									{
									rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM5+"' and METERNO='"+meterno.trim()+"'");
								    rset=st.executeQuery("select * from XMLIMPORT where RDATE=TO_DATE('"+rdate_d5+"','DD-MM-YYYY hh24:mi:ss') and METERNO='"+meterno+"'");
								    if(!rset.next())
								    {
								    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d5+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d5+"','"+kwhunit_d5+"','"+kvh_d5+"','"+kvhunit_d5+"','"+kva_d5+"','"+kvaunit_d5+"','"+kwharb_d5+"','"+pf_d5+"','"+kvaarb_d5+"','"+kvharb_d5+"','"+pfarb_d5+"','"+yyyyMM5+"', '"+current_date+"','"+dateCount5+"','"+d5_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh5+"','"+ekvah5+"','"+ekva5+"','"+epf5+"')");
								    }
									rset.close();
									}
						       }
						    }
						    catch (Exception e) {
								e.printStackTrace();
						    	writeLogFile("Error : "+e);
								System.exit(0);
							}
						    
						    try{
						    	if(!(rdate_d6.startsWith("00")))
						    	{
										if(d3_06_flag )
									{
								    rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM6+"' and METERNO='"+meterno+"'");
								    if(!rset.next())
								    {
								    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d6+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d6+"','"+kwhunit_d6+"','"+kvh_d6+"','"+kvhunit_d6+"','"+kva_d6+"','"+kvaunit_d6+"','"+kwharb_d6+"','"+pf_d6+"','"+kvaarb_d6+"','"+kvharb_d6+"','"+pfarb_d6+"','"+yyyyMM6+"','"+current_date+"','"+dateCount6+"','"+d6_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh6+"','"+ekvah6+"','"+ekva6+"','"+epf6+"')");
								    	 
								    }
									rset.close();
									}
						        }
						    }
						    catch (Exception e) {
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
					 System.out.println("MetrFlagStatus-->"+MetrFlagStatus);
					 flagStatus="success/"+meterno+"/"+MetrFlagStatus+"/";
					 System.out.println("Data has been uploaded successfully for meter number : "+meterno);
					 writeLogFile("-------------- Data has been uploaded successfully for meter number : "+meterno);
				 }	  
				 else
				 {
					 MetrFlagStatus="Mfailure";
					 flagStatus="Failure/"+meterno+"/"+MetrFlagStatus+"/";
					 System.out.println("Data Could not be uploaded for meter number : "+meterno);
					 System.out.println("Data Could not be uploaded for meter number : "+meterno);
					 writeLogFile("-------------- Data Could not be uploaded for meter number : "+meterno);
					 writeLogFileNotImported("-------------- Data Could not be uploaded for meter number : "+meterno);
				 }
			
				
				 
			    if(!d3_exist)	
			    {
			    	checkCrpt="corrupt";
			    }
			    flagStatus=flagStatus+checkCrpt;
			   
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
		return flagStatus;
	}
	
	
	//Reading AMR xml files for XmlImport table.
	public  String amrXmlReading(Document docForMetrNo ) {
	System.out.println("inside amrXmlReading");

	Statement st =null;
	String flagStatus="";
	
	try{
		st= connection.createStatement();
		ResultSet rs = null, rsCount = null, rsForDateCheck = null;

		String meterno="",rdate="",kwh="",kwhunit="",kvh="",kvhunit="",kva="",kvaunit="",arb="",pf="",kwharb="",kvaarb="",kvharb="",pfarb="",flag1="0",flag2="0",flag3="0",flag4="0",flag5="0",flag6 = "0";
		String g2Value = "", g3Value = "";
		int status=0;
		String d1_OccDate = "",d2_OccDate = "",d3_OccDate = "",d4_OccDate = "",d5_OccDate = "",d6_OccDate="";
		
		String ekwh="",ekvah="",ekva="", epf="";
		String ekwh2="",ekvah2="",ekva2="", epf2="";
		String ekwh3="",ekvah3="",ekva3="", epf3="";
		String ekwh4="",ekvah4="",ekva4="", epf4="";
		String ekwh5="",ekvah5="",ekva5="", epf5="";
		String ekwh6="",ekvah6="",ekva6="", epf6="";
					
		String rdate_d2="",kwh_d2="",kwhunit_d2="",kvh_d2="",kvhunit_d2="",kva_d2="",kvaunit_d2="",arb_d2="",pf_d2="",kwharb_d2="",kvaarb_d2="",kvharb_d2="",pfarb_d2="",flag1_d2="0",flag2_d2="0",flag3_d2="0",flag4_d2="0",flag5_d2="0",flag6_d2="0";
		String rdate_d3="",kwh_d3="",kwhunit_d3="",kvh_d3="",kvhunit_d3="",kva_d3="",kvaunit_d3="",arb_d3="",pf_d3="",kwharb_d3="",kvaarb_d3="",kvharb_d3="",pfarb_d3="",flag1_d3="0",flag2_d3="0",flag3_d3="0",flag4_d3="0",flag5_d3="0",flag6_d3="0";
		String rdate_d4="",kwh_d4="",kwhunit_d4="",kvh_d4="",kvhunit_d4="",kva_d4="",kvaunit_d4="",arb_d4="",pf_d4="",kwharb_d4="",kvaarb_d4="",kvharb_d4="",pfarb_d4="",flag1_d4="0",flag2_d4="0",flag3_d4="0",flag4_d4="0",flag5_d4="0",flag6_d4="0";
		String rdate_d5="",kwh_d5="",kwhunit_d5="",kvh_d5="",kvhunit_d5="",kva_d5="",kvaunit_d5="",arb_d5="",pf_d5="",kwharb_d5="",kvaarb_d5="",kvharb_d5="",pfarb_d5="",flag1_d5="0",flag2_d5="0",flag3_d5="0",flag4_d5="0",flag5_d5="0",flag6_d5="0";
		String rdate_d6="",kwh_d6="",kwhunit_d6="",kvh_d6="",kvhunit_d6="",kva_d6="",kvaunit_d6="",arb_d6="",pf_d6="",kwharb_d6="",kvaarb_d6="",kvharb_d6="",pfarb_d6="",flag1_d6="0",flag2_d6="0",flag3_d6="0",flag4_d6="0",flag5_d6="0",flag6_d6="0";
		
		boolean d3_01_flag=false, d3_02_flag=false, d3_03_flag=false, d3_04_flag=false, d3_05_flag=false, d3_06_flag=false, d3_exist = false;
		
		String dateCount1 = "0", dateCount2 = "0", dateCount3 = "0", dateCount4 = "0", dateCount5 = "0", dateCount6 = "0";
		
		String MetrFlagStatus="";
		String checkCrpt="D3present";
		//for billMonth
		Date billDate = new Date();
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		String billMonth = sdfBillDate.format(billDate);
		//billMonth = "201306";
		int d4DayProfileCount = 0;
		
		//shri
		String manufacturerCode="";
		String manufacturerName="";
		String gvalue="";
		String genusd="3ph-4W";
		
		
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
																	
																	if(nodeName.equalsIgnoreCase("G22"))
																	{
																		if (tempNode123.hasAttributes()) 
																		{
																			NamedNodeMap nodeMap = tempNode123.getAttributes();
																			//String manufacturerCode = "",manufacturerName = "";
																			
																			for (int i = 0; i < nodeMap.getLength(); i++) 
																			{
																				String d1AttrId = "", value = "";
																				Node node = nodeMap.item(i);
																				//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());
																				
																				if(node.getNodeName().equalsIgnoreCase("CODE")) 
																				{
																					manufacturerCode = node.getNodeValue();
																					System.out.println("manufacturerCode===>"+manufacturerCode);
																				}
																				else if(node.getNodeName().equalsIgnoreCase("NAME")) 
																				{
																					manufacturerName = node.getNodeValue();
																					System.out.println("manufacturerName===>"+manufacturerName);
																				}															    						
																																																								
																			}
																			
																		}//End has Attribute
																	}
																	if(nodeName.equalsIgnoreCase("G15"))
																	{
																		 gvalue=nodeValue;
																		System.out.println("gvalue-->"+gvalue);
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
													    					//System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-02"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d2=dateTime;	
																			d3_02_flag = true;
													    					//System.out.println("values are dates"+dateTime);
												    					}
													    				
													    				else if(d3TagCount.equalsIgnoreCase("D3-03"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d3=dateTime;	
																			d3_03_flag = true;
													    					//System.out.println("values are dates"+dateTime);
												    					}
													    				
													    				else if(d3TagCount.equalsIgnoreCase("D3-04"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d4=dateTime;	
																			d3_04_flag = true;
													    					//System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-05"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d5=dateTime;	
																			d3_05_flag = true;
													    					//System.out.println("values are dates"+dateTime);
												    					}
													    				else if(d3TagCount.equalsIgnoreCase("D3-06"))
												    					{
													    					d3_01_dateTime = dateTime;	
													    					rdate_d6=dateTime;	
																			d3_06_flag = true;
													    					//System.out.println("values are dates"+dateTime);
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
													    					String code = "", value = "0", unit = "",tod = "",occDate = "";
													    					
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
															    				//System.out.println("secure");
															    				if(d3TagCount.equalsIgnoreCase("D3-00"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							
														    							if(code.equalsIgnoreCase("P7-1-5-1-0")) //&& flag1.equalsIgnoreCase("0") )
														    							{
																							if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1=temp+"";
																							if(!flag1.equalsIgnoreCase("0.0") )
																							{
																								arb=code;
																								kwhValue = value;
																								kwhunit=unit;
																								kwh= value;
																								flag1=value;
																								kwharb=code;

																							}
														    								
														    								//System.out.println("P7-1-5-1-0 kwhValue value==================>"+kwhValue);
																						//System.out.println("flag1 value==================>"+flag1);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && (flag1.equalsIgnoreCase("0") || flag1.equalsIgnoreCase("0.0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//System.out.println(" P7-1-5-2-0 kwhValue value==================>"+kwhValue);
														    							}
																					//	System.out.println("D3-00 -->Rajguru-flag1  - "+flag1);
														    							if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								//flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//System.out.println(" P7-1-18-0-0 kwhValue value==================>"+kwhValue);
														    							}
														    						
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kvharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kvharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								//flag2=value;
														    								kvharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-5-2-0") && flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
																							//System.out.println("kva value==================>"+kvaValue);
														    							}

														    							else if(code.equalsIgnoreCase("P7-6-18-0-0") && flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								//flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								flag3=value;
														    								pf=value;
														    								pfarb=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
																					if(code.equalsIgnoreCase("P4-4-4-0-0") && flag3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}//end of d3-00
															    				else if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if((code.equalsIgnoreCase("P7-1-18-0-0")) && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh= value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								kwh= value;
														    								kwharb=code;
														    								//System.out.println("kwhValue P7-1-5-2-0 value==================>"+flag1);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh= value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwharb=code;
														    								//System.out.println("kwhValue P7-1-18-1-0 value==================>"+flag1);
														    							}
																					//Export reading KWH by Rajguru
																					else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
														    							{
														    								ekwh = value;
														    							}
																					//Export reading KVAH  by Rajguru
																					else if(code.equalsIgnoreCase("P7-3-6-0-0"))
														    							{
														    								ekvah = value;
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh= value;
														    								kwhunit=unit;
														    								kwharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0")|| code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kvharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								flag3=value;
														    								kvharb=code;
														    								
														    								kvhunit=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if((code.equalsIgnoreCase("P7-3-18-2-0")||code.equalsIgnoreCase("P7-6-5-1-0")) && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								flag3=value;
														    								kvharb=code;
														    								
														    								kvhunit=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2.equalsIgnoreCase("0") &&  flag3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvharb=code;
														    								kvhunit=unit;
																							flag6=value;

														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
																					//Export PF by Rajguru
																					else if(code.equalsIgnoreCase("P7-4-6-1-0"))
														    							{
														    								ekva = value;
														    							}
																					else if(code.equalsIgnoreCase("P7-4-6-2-0"))
														    							{
														    								ekva = value;
														    							}
																					else if(code.equalsIgnoreCase("P7-6-6-0-0"))
													    							{
													    								ekva = value;
													    							}
														    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							flag5=value;
																							d1_OccDate = occDate;
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-18-2-0")&& flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							flag5=value;
																							d1_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
																							//flag5=value;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
																							//flag5=value;
														    							}
														    						}
														    						 if(subNodeName.equalsIgnoreCase("B6"))
														    						{
																				
														    						if(code.equalsIgnoreCase("P7-6-5-0-0")&& flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-6-0-0")&& ekva.equalsIgnoreCase("0"))
														    							{
														    								ekva = value;
														    							}
																				}
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf=value;
														    								pfarb=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
																					else if(code.equalsIgnoreCase("P4-4-4-0-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    							}
																					//Export PF by Rajguru
																					else if(code.equalsIgnoreCase("P4-4-4-2-0"))
														    							{
														    								epf = value;
														    							}
														    						}
														    						
														    					}
														    					
														    					//For d3-02
														    					else
														    					if(d3TagCount.equalsIgnoreCase("D3-02"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d2= value;
														    								kwhunit_d2=unit;
														    								kwharb_d2=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-1-0") && flag1_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d2= value;
														    								kwhunit_d2=unit;
														    								kwharb_d2=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-2-0") && flag1_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d2= value;
														    								kwhunit_d2=unit;
														    								kwharb_d2=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0")||code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								kvharb_d2=code;
														    								flag2_d2=value;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								flag3_d2=value;
														    								kvharb_d2=code;
														    								
														    								kvhunit_d2=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-18-2-0") && flag2_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								flag3_d2=value;
														    								kvharb_d2=code;
														    								
														    								kvhunit_d2=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvharb_d2=code;
														    								kvhunit_d2=unit;
																							flag6_d2=value;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					        
														    					      //Export reading KWH by Shrinivas
																						else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
															    							{
															    								ekwh2 = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah2 = value;
															    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-5-0-0")||code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
																							//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								kvaunit_d2=unit;
																							flag5_d2=value;
																							d2_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
																							//flag5_d2=value;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-1-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
																							//flag5=value;
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva2 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva2 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-6-6-0-0"))
														    							{
														    								ekva2 = value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0")||(code.equalsIgnoreCase("P4-4-4-0-0")))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d2=value;
														    								pfarb_d2=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    							
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf2 = value;
															    							}
														    							
														    						}
														    					}//End of d3-02
															    				
															    				//Begin of D3-03
														    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-5-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								flag2_d3=value;
														    								kvharb_d3=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					      //Export reading KWH by Rajguru
																						else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
															    							{
															    								ekwh3 = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah3 = value;
															    							}
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								flag4_d3=value;
														    								kvaunit_d3=unit;
														    								d3_OccDate=occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva3 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva3 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-6-6-0-0"))
														    							{
														    								ekva3 = value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf_d3=value;
														    								pfarb_d3=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf3 = value;
															    							}
														    							
														    						}
														    					}// end of D3-03
															    				//Begin of D3-01
														    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
														    					{

														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-5-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								flag2_d4=value;
														    								kvharb_d4=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					        
														    					      //Export reading KWH by Rajguru
																						else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
															    							{
															    								ekwh4 = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah4 = value;
															    							}
														    							
														    							
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								flag4_d4=value;
														    								kvaunit_d4=unit;
														    								d4_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva4 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva4 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-6-6-0-0"))
														    							{
														    								ekva4 = value;
														    							}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d4=value;
														    								pfarb_d4=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf4 = value;
															    							}
														    						}
														    					}//End of D3-04
															    				
															    				//Begin D3-05
														    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
														    					{

														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-5-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								flag2_d5=value;
														    								kvharb_d5=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					        
														    					      //Export reading KWH by Rajguru
																						else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
															    							{
															    								ekwh5 = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah5 = value;
															    							}
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								flag4_d5=value;
														    								kvaunit_d5=unit;
														    								d5_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva5 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva5 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-6-6-0-0"))
														    							{
														    								ekva5 = value;
														    							}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								pf_d5=value;
														    								pfarb_d5=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf5 = value;
															    							}
														    						}
														    					}// End of D3-05
															    				
															    				//Begin Of D3-06
															    				
														    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
														    					{

														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0")||code.equalsIgnoreCase("P7-3-5-0-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								flag2_d6=value;
														    								kvharb_d6=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    					      //Export reading KWH by Rajguru
																						else if(code.equalsIgnoreCase("P7-1-6-1-0")||code.equalsIgnoreCase("P7-1-6-2-0"))
															    							{
															    								ekwh6 = value;
															    							}
																						//Export reading KVAH  by Rajguru
																						else if(code.equalsIgnoreCase("P7-3-6-0-0"))
															    							{
															    								ekvah6 = value;
															    							}
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-0-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								flag4_d6=value;
														    								kvaunit_d6=unit;
														    								d6_OccDate = occDate;
														    								//System.out.println("kva value==================>"+kvaValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P7-4-6-1-0"))
															    							{
															    								ekva6 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-4-6-2-0"))
															    							{
															    								ekva6 = value;
															    							}
																						else if(code.equalsIgnoreCase("P7-6-6-0-0"))
														    							{
														    								ekva6 = value;
														    							}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d6=value;
														    								pfarb_d6=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    							//Export PF by Rajguru
																						else if(code.equalsIgnoreCase("P4-4-4-2-0"))
															    							{
															    								epf6 = value;
															    							}
														    						}
														    					}//end of D3-06
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
																			try {
																				
																			
																			String profileDateYearMonth = "";
																			rsForDateCheck = st.executeQuery("SELECT TO_CHAR(ADD_MONTHS(TO_DATE('"+dayProfileDate+"','DD/MM/YYYY'),1),'YYYYMM') FROM DUAL");
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
																			} catch (Exception e) {
																				
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
					    
					    String DATE_FORMAT2  = "dd/MM/yyyy";
					    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(DATE_FORMAT2);
					    String DATE_FORMAT3  = "yyyymm";
					    java.text.SimpleDateFormat sdf3 = new java.text.SimpleDateFormat(DATE_FORMAT3);
					    
					    String DATE_FORMAT4  = "dd-MM-yyyy";
					    java.text.SimpleDateFormat sdf4 = new java.text.SimpleDateFormat(DATE_FORMAT4);
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
								rsForD3=st.executeQuery("SELECT * FROM XMLIMPORT WHERE METERNO='"+meterno.trim()+"' AND DATESTAMP='"+current_date+"'");
								if(!rsForD3.next())
								{
									int importStatus=	st.executeUpdate("INSERT INTO XMLIMPORT(METERNO, DATESTAMP, G2VALUE, G3VALUE) values('"+meterno.trim()+"','"+current_date+"','"+g2Value+"','"+g3Value+"')");
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
							Date date = (Date)sdf4.parse(rdate);
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
							c1.add(Calendar.MONTH,-1);
							System.out.println("Date + 1 month is : " + sdf.format(c1.getTime()));
							String rdate_substracted=sdf.format(c1.getTime());

							 yyyyMM1 = sdfBillDate.format(date);
							System.out.println("rDate yyyyMM : "+yyyyMM1);
							if(yyyyMM1.startsWith("0"))
							{
								yyyyMM1 = yyyyMM1.substring(2);
								yyyyMM1 = "20"+yyyyMM1;
								//System.out.println("rDate1 yyyyMM after modify : "+yyyyMM1);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM1))
							{
								dateCount1 = d4DayProfileCount + "";
							}
					    }
						//end
					    
					    //Date for d3-02
						if(d3_02_flag && (rdate_d2 != ""))
						{
							Date date2 = (Date)sdf4.parse(rdate_d2);
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
							if(yyyyMM2.startsWith("0"))
							{
								yyyyMM2 = yyyyMM2.substring(2);
								yyyyMM2 = "20"+yyyyMM2;
							//	System.out.println("rDate2 yyyyMM after modify : "+yyyyMM2);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM2))
							{
								dateCount2 = d4DayProfileCount + "";
							}
						}
					    //End
					    
					  //Date for d3-03
						if(d3_03_flag && (rdate_d3 != ""))
						{
							Date date3 = (Date)sdf4.parse(rdate_d3);
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
							if(yyyyMM3.startsWith("0"))
							{
								yyyyMM3 = yyyyMM3.substring(2);
								yyyyMM3 = "20"+yyyyMM3;
							//	System.out.println("rDate3 yyyyMM after modify : "+yyyyMM3);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM3))
							{
								dateCount3 = d4DayProfileCount + "";
							}
						}
					    //End
					    
					  //Date for d3-04
						if(d3_04_flag && (rdate_d4 != ""))
						{
							Date date4 = (Date)sdf4.parse(rdate_d4);
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
							if(yyyyMM4.startsWith("0"))
							{
								yyyyMM4 = yyyyMM4.substring(2);
								yyyyMM4 = "20"+yyyyMM4;
								//System.out.println("rDate4 yyyyMM after modify : "+yyyyMM4);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM4))
							{
								dateCount4 = d4DayProfileCount + "";
							}
					    }
					    //End
					    
					    //Date for d3-05
						if(d3_05_flag && (rdate_d5 != ""))
						{
							Date date5 = (Date)sdf4.parse(rdate_d5);
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
							//System.out.println("rDate5 yyyyMM : "+yyyyMM5);
							if(yyyyMM5.startsWith("0"))
							{
								yyyyMM5 = yyyyMM5.substring(2);
								yyyyMM5 = "20"+yyyyMM5;
								//System.out.println("rDate5 yyyyMM after modify : "+yyyyMM5);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM5))
							{
								dateCount5 = d4DayProfileCount + "";
							}
					    }
					    //End
					    
					  //Date for d3-06
						if(d3_06_flag && (rdate_d6 != ""))
						{
							Date date6 = (Date)sdf4.parse(rdate_d6);
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
							//System.out.println("rDate6 yyyyMM : "+yyyyMM6);
							if(yyyyMM6.startsWith("0"))
							{
								yyyyMM6 = yyyyMM6.substring(2);
								yyyyMM6 = "20"+yyyyMM6;
							//	System.out.println("rDate6 yyyyMM after modify : "+yyyyMM6);
								
							}
							if(dataMonth.equalsIgnoreCase(yyyyMM6))
							{
								dateCount6 = d4DayProfileCount + "";
							}
						}
					   // System.out.println("dateCount : "+dateCount1+" - "+dateCount2+" - "+dateCount3+" - "+dateCount4+" - "+dateCount5+" - "+dateCount6);
					    //End
					    
					    
					    //checking batchstatus for MM_TABLE column 0 for particular meterno
						//System.out.println("billmonth cmri-->"+billMonth);
					    long MMcount=cdfBatchService.checkMMUpdate(billMonth, meterno.trim());
					    // System.out.println("MMcount cmri-->"+MMcount);
					    if(MMcount>0)
					    {
					    int delCount=cdfBatchService.deleteMtrInXmlImport(meterno.trim(),yyyyMM1);
					    // System.out.println("delCount-->"+delCount);
					    }
					    else{
					    }
					    
					    //checking wheather the data already exists or not
					    
					    ResultSet rset=null;
					    try{
					    	System.out.println(d3_01_flag+"---"+rdate);
							if(d3_01_flag && (rdate != ""))
						{
								
					    rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM1+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	//System.out.println("Inside d3-01 genus non dlms rdate1--"+rdate+"  meterno--"+meterno);
					    	//System.out.println(kwh+"/"+kvh+"/"+kva+"/"+pf+"/"+dateCount1+" export Reading-->"+ekwh+"/"+ekvah+"/"+ekva+"/"+epf+"/aarb"+kwharb+"/"+kvaarb+"/"+kvharb+"/"+pfarb);
					    	//status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE) values('"+meterno.trim()+"',TO_DATE('"+rdate+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh+"','"+kwhunit+"','"+kvh+"','"+kvhunit+"','"+kva+"','"+kvaunit+"','"+kwharb+"','"+pf+"','"+kvaarb+"','"+kvharb+"','"+pfarb+"','"+yyyyMM1+"','"+current_date+"','"+dateCount1+"','"+d1_OccDate+"','"+g2Value+"','"+g3Value+"')");
					     status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno.trim()+"',TO_DATE('"+rdate+"','DD-MM-YYYY HH24:MI:SS'),'"+kwh+"','"+kwhunit+"','"+kvh+"','"+kvhunit+"','"+kva+"','"+kvaunit+"','"+kwharb+"','"+pf+"','"+kvaarb+"','"+kvharb+"','"+pfarb+"','"+yyyyMM1+"','"+current_date+"','"+dateCount1+"','"+d1_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh+"','"+ekvah+"','"+ekva+"','"+epf+"')");
					    
					    }
						rset.close();
						}
							if(status==1){
								List dataMM=cdfBatchService.findMeterMasterDataForCURRMonth(yyyyMM1, meterno.trim());
								//System.out.println("data...>>"+dataMM.size());
								if(dataMM.size()>0){
									
									
									//int dataUpdate=cdfBatchService.updateMMkwh(yyyyMM1, meterno, kwh, kvh, kva, pf);
									int dataUpdate=cdfBatchService.updateMMExportReading(yyyyMM1, meterno.trim(),  kwh, kvh, kva, pf, ekwh, ekvah, ekva, epf,todayDate);
									
									if(dataUpdate>0)
									{
										MetrFlagStatus="Msuccess";
										System.out.println(" data updated to Meter Master Table successfully");
									}
									else
									{
										MetrFlagStatus="Mfailure";
									}
								}
								else
								{
									MetrFlagStatus="Mfailure";
								}
							}
							else
							{
								MetrFlagStatus="Mfailure";
							}
							if(!billMonth.equalsIgnoreCase(yyyyMM1))
							{
								MetrFlagStatus="Mfailure";
								checkCrpt="D3-01 (month="+yyyyMM1+")";
							}
					    }catch (Exception e) {
							e.printStackTrace();
							writeLogFile("Error : "+e);
							System.exit(0);
						}
					    
					    try{
							if(d3_02_flag && (rdate_d2 != ""))
						{
					    rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM2+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	//System.out.println("INSIDE D3-02");
					    	//System.out.println("status-->>>>"+status);
					    	int temp=status;
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno.trim()+"',TO_DATE('"+rdate_d2+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d2+"','"+kwhunit_d2+"','"+kvh_d2+"','"+kvhunit_d2+"','"+kva_d2+"','"+kvaunit_d2+"','"+kwharb_d2+"','"+pf_d2+"','"+kvaarb_d2+"','"+kvharb_d2+"','"+pfarb_d2+"','"+yyyyMM2+"', '"+current_date+"','"+dateCount2+"','"+d2_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh2+"','"+ekvah2+"','"+ekva2+"','"+epf2+"')");
					    	if(temp==0)
					    	{
					    		status=temp;
					    	}
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
						rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM3+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d3+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d3+"','"+kwhunit_d3+"','"+kvh_d3+"','"+kvhunit_d3+"','"+kva_d3+"','"+kvaunit_d3+"','"+kwharb_d3+"','"+pf_d3+"','"+kvaarb_d3+"','"+kvharb_d3+"','"+pfarb_d3+"','"+yyyyMM3+"', '"+current_date+"','"+dateCount3+"','"+d3_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh3+"','"+ekvah3+"','"+ekva3+"','"+epf3+"')");
					    	 
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
						rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM4+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	 status=st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d4+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d4+"','"+kwhunit_d4+"','"+kvh_d4+"','"+kvhunit_d4+"','"+kva_d4+"','"+kvaunit_d4+"','"+kwharb_d4+"','"+pf_d4+"','"+kvaarb_d4+"','"+kvharb_d4+"','"+pfarb_d4+"','"+yyyyMM4+"', '"+current_date+"','"+dateCount4+"','"+d4_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh4+"','"+ekvah4+"','"+ekva4+"','"+epf4+"')");
					    	 
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
						rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM5+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d5+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d5+"','"+kwhunit_d5+"','"+kvh_d5+"','"+kvhunit_d5+"','"+kva_d5+"','"+kvaunit_d5+"','"+kwharb_d5+"','"+pf_d5+"','"+kvaarb_d5+"','"+kvharb_d5+"','"+pfarb_d5+"','"+yyyyMM5+"', '"+current_date+"','"+dateCount5+"','"+d5_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh5+"','"+ekvah5+"','"+ekva5+"','"+epf5+"')");
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
						rset=st.executeQuery("select * from XMLIMPORT where month='"+yyyyMM6+"' and METERNO='"+meterno.trim()+"'");
					    if(!rset.next())
					    {
					    	 status=	st.executeUpdate("insert into XMLIMPORT(METERNO,RDATE,KWH,KWHUNIT,KVH,KVHUNIT,KVA,KVAUNIT,KWHARB,PF,KVAARB,KVHARB,PFARB,MONTH,DATESTAMP,LOAD_SURVEY_COUNT,KVAOCCDATE,G2VALUE, G3VALUE,KWHE,KVHE,KVAE,PFE) values('"+meterno+"',TO_DATE('"+rdate_d6+"','DD-MM-YYYY hh24:mi:ss'),'"+kwh_d6+"','"+kwhunit_d6+"','"+kvh_d6+"','"+kvhunit_d6+"','"+kva_d6+"','"+kvaunit_d6+"','"+kwharb_d6+"','"+pf_d6+"','"+kvaarb_d6+"','"+kvharb_d6+"','"+pfarb_d6+"','"+yyyyMM6+"','"+current_date+"','"+dateCount6+"','"+d6_OccDate+"','"+g2Value+"','"+g3Value+"','"+ekwh6+"','"+ekvah6+"','"+ekva6+"','"+epf6+"')");
					    	 
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
				 System.out.println("MetrFlagStatus-->"+MetrFlagStatus);
				 flagStatus="success/"+meterno+"/"+MetrFlagStatus+"/";
				 System.out.println("Data has been uploaded successfully for meter number : "+meterno);
				 // writeLogFile("-------------- Data has been uploaded successfully for meter number : "+meterno);
			 }	  
			 else
			 {
				 MetrFlagStatus="Mfailure";
				 flagStatus="Failure/"+meterno+"/"+MetrFlagStatus+"/";
				 System.out.println("Data Could not be uploaded for meter master : "+meterno);
				 // System.out.println("Data Could not be uploaded for meter number : "+meterno);
				 // writeLogFile("-------------- Data Could not be uploaded for meter number : "+meterno);
				 //writeLogFileNotImported("-------------- Data Could not be uploaded for meter number : "+meterno);
			 }
		
			
			 
		    if(!d3_exist)	
		    {
		    	checkCrpt="corrupt";
		    }
		    flagStatus=flagStatus+checkCrpt;
				 
		   
		}
		//try
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
	
	return flagStatus;
}
	public static void writeLogFile(Object str){
		  File f=null;
		  FileOutputStream out=null; 
		  PrintStream p=null; 
		  System.out.println("LOG:::"+str);
		  try{
		  // f=new File("D:\\vishwanath\\testBatch\\Log_file\\MIPBillingErrorsLog.txt");
			  f=new File(Log_File);
			  if(!f.exists())
				{
					f.mkdir();
				}
			  String merror="MIPBillingErrorsLog.txt";
			File  f1=new File(f+"/"+merror);
			/*if(f1.exists())
			{
				f1.delete();
			}*/
		   out = new FileOutputStream(f1,true);
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
		   //f=new File("D:\\vishwanath\\testBatch\\Log_file\\MIPBillingErrorsLogNotImported.txt");
		   f=new File(Log_File);
			  if(!f.exists())
				{
					f.mkdir();
				}
			  String merror="MIPBillingErrorsLogNotImported.txt";
			File  f1=new File(f+"/"+merror);
		   out = new FileOutputStream(f1,true);
		  
		   p = new PrintStream( out );
		   p.println(str);
		  }catch(Exception e){
		   p.println("Exception in creating file "+e);
		  }
		  p.flush();
		  p.close();
	}
	
	
	//getSummary
	@RequestMapping(value="/getSummaryBatch",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getSummaryBatch(HttpServletRequest request,ModelMap model)
	{
		System.out.println("calling getSummaryBatch based on date ");
		String date1=request.getParameter("d1");
		System.out.println("date1==="+date1);
		List<?> data=batchStatusService.getAllSummaryData(date1);
		return data;
	}

}
