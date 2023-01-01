package com.bcits.mdas.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;

import com.bcits.entity.CmriUploadStatusEntity;
import com.bcits.entity.TownEntity;
import com.bcits.entity.XmlUploadSummary;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.SubstationDetailsEntity;
import com.bcits.mdas.service.ChangeModemService;
//import com.bcits.mdas.service.UserService;

import com.bcits.mdas.service.CmriUploadStatusService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.substationdetailsservice;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.TownMasterService;
import com.bcits.service.XmlUploadSummeryService;

import net.lingala.zip4j.core.ZipFile;

@Controller
public class ManualUploadController {

	
	public static String uploadPath = "MIP_uploads";
	public static String unZipPath =  "MIP_unzippedFiles";
	
	//public static String uploadPath = "E:\\hem\\dhbvn_files";
	//public static String unZipPath =  "E:\\\\hem\\\\dhbvn_files_unzipped";
	
	//public static String uploadPath = "E:\\PRADEEPKUMAR C R\\STS-SERVER\\apache-tomcat-7.0.12\\bin\\MIP_uploads";
	//public static String unZipPath = "E:\\PRADEEPKUMAR C R\\STS-SERVER\\apache-tomcat-7.0.12\\bin\\MIP_unzippedFiles";
	
	 // public static String uploadPath = "C:\\tomcat 7\\bin\\MIP_uploads";
	 // public static String unZipPath = "C:\\tomcat 7\\bin\\MIP_unzippedFiles";
	  
	  
	
	public static String parseXml = "";
	public static String duplicate = "";
	public static String meterNotExist = "";
	public static int parsedCount = 0;
	public static int duplicateCount = 0;
	public static int meterNotExistCount = 0;
	public static double mainTime = 0;
	public static String month=null;
	String result = "";
	int uploadFlag = 0;
	
	/*@Autowired
	private UserService userService;*/
	
	@Autowired
	private ChangeModemService changeModemService;
	
	@Autowired
	private CmriUploadStatusService cmriuploadservice;
	
	@Autowired
	private XmlUploadSummeryService xmlUploadSummeryService;
	
	@Autowired
	private TownMasterService townMasterService;
	
	@Autowired
	private FeederMasterService feederMasterService;
	
	@Autowired
	substationdetailsservice substationDetails;
	
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,Model model,HttpSession session)
    {
		Document docForMetrNo=null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");
			
			String fileName  = myFile.getOriginalFilename();
			
			String extension = "";
			//String meterNumber = "";
			int dotIndex = fileName.lastIndexOf(".");
			extension = fileName.substring(dotIndex+1);
			System.out.println("File name : "+fileName + " extension : "+extension);
			
			File folder1 = new File(uploadPath);
			if(!folder1.exists())
			{
				folder1.mkdir();
			}
			//create unzip folder
			File folder2 = new File(unZipPath);
			if(!folder2.exists())
			{
				folder2.mkdir();
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
						System.out.println("file uploaded successfully");
					
	
				}
				System.out.println("end of upload");
				
				try
			     {
			    	Date dt = new Date();
			 		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd_kk_mm_ss");
			 		String currentTime = sdf1.format(dt);
			    	 
			    	//create output directory is not exists
			    	//File folder = new File(getServlet().getServletContext().getRealPath("/") +"/unzipped");
			    	
			    	// Initiate ZipFile object with the path/name of the zip file.
					ZipFile zipFile = new ZipFile(uploadPath+"/"+fileName);//getServlet().getServletContext().getRealPath("/") +"/uploads/"+fileName);
					File unZipNew = new File(unZipPath, currentTime);
					unZipNew.mkdir();
					
					// Extracts all files to the path specified
					zipFile.extractAll(unZipPath + "/" + currentTime);//getServlet().getServletContext().getRealPath("/") +"/unzipped");
				
					String[] fname = fileName.split("\\.");
					
			    	System.out.println("Done");
			    	
			    	
			    	model.addAttribute("UnZipFolderPath",currentTime);
			    	model.addAttribute("UnZipFilename",fname[0]);
			    	model.addAttribute("filename",fileName);
			    	
			    	String files = "";
			    	String msg="";
			    	String unZipFolderPath = unZipPath+"/"+currentTime;
			    	String path = unZipFolderPath;
					File folder = new File(path);
				
					File[] listOfFiles = folder.listFiles();
					if(listOfFiles != null)
					{
						for(int i =0;i < listOfFiles.length;i++)
						{
							if(listOfFiles[i].isFile())
							{
								files = listOfFiles[i].getName();
								if(!(files.endsWith(".xml") ||files.endsWith(".XML")))
								{
									msg="wrongdata";
								}
							}
						}
						if("wrongdata".equalsIgnoreCase(msg))
						{
							model.addAttribute("result","Uploaded File doesn't contain any XML files.");
						}
						else
						{
							model.addAttribute("parse","parse");
							model.addAttribute("result","File Uploaded Successfully..");
						}
					}
			    	
			    }
			    catch(Exception ex)
			    {
			       ex.printStackTrace(); 
			    }
			
				System.out.println("end of upload");
						
				
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
	
	
	
	@RequestMapping(value="/parseFileMDAS/{path}/{filename}/{filee}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object parseFile(@PathVariable String path,@PathVariable String filename,@PathVariable String filee, HttpServletRequest request,ModelMap model,HttpSession session) throws JSONException
	{
		System.out.println("----------parseFile---------- : "+path + " : "+filee);
		//String billmonth = request.getParameter("billmonth");
		String unZipFolderPath = unZipPath+"/"+path;
		parseXml = "";duplicate = "";meterNotExist = "";
		parsedCount = 0;duplicateCount = 0;meterNotExistCount = 0;mainTime = 0;
		JSONObject resJsonObject  = getUnzippedXmls(unZipFolderPath,model,session); 
		double time=((resJsonObject.getDouble("lIteratorDifference")/1000)/60);
		time = Math.round(time*100.0)/100.0;
	    
		HashMap<String,Object> hashObj=new HashMap<>();
		hashObj.put("parsed",parseXml);
		hashObj.put("parsedCount",parsedCount);
		hashObj.put("meterNotExist",meterNotExist);
		hashObj.put("meterNotExistCount",meterNotExistCount);
		hashObj.put("duplicate",duplicate);
		hashObj.put("duplicateCount",duplicateCount);
		hashObj.put("time", time);
		
		
		System.err.println("the time taken is : "+time);
		System.err.println(" parsed : "+parseXml);
		System.err.println(" parsedCount : "+ parsedCount);
		System.err.println(" meterNotExistCount : "+meterNotExistCount);
		System.err.println(" duplicateCount : "+duplicateCount);
		String time1 = time+"";
		model.addAttribute("result","Parsing Completed.. Time Taken is : "+time1);
		//NEED TO ITERATE THE JSON AND NEED TO SAVE INTO UPLOAD STATUS TABLE
		System.err.println("Json Response -======>"+resJsonObject.toString());
		
		
		return hashObj;
	}
	
	public  JSONObject getUnzippedXmls(String unZipFolderPath,ModelMap model,HttpSession session) 
	{

		 System.out.println(" inside getUnzippedXmls ");
			double lIteratorDifference=0;
			JSONObject resJson=new JSONObject();
			JSONArray resJsonArray=new JSONArray();
			Date d1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			month=sdf.format(d1);
			try
			{
				double lIteratorStartTime = new Date().getTime();
				mainTime = lIteratorStartTime;
				Document docForMetrNo=null;
				String files = "";
				String path = unZipFolderPath;
				System.out.println("PATH : "+path);
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				System.out.println("No. files : "+listOfFiles.length);
				if(listOfFiles != null)
				{
					ArrayList<HashMap<String,Object>> listOfFileResultantFiles=new ArrayList<>();
					
					System.out.println("---0-- file name is : "+files);
					for(int i =0;i < listOfFiles.length;i++)
					{
						System.out.println("---1-- file name is : "+files);
						if(listOfFiles[i].isFile())
						{
							System.out.println("---2-- file name is : "+files);
							files = listOfFiles[i].getName();
							if(files.endsWith(".xml") || files.endsWith(".XML"))
							{
								System.out.println("---3-- file name is : "+files);
								File fileForMetrNo = new File( path+"/"+files);
								DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				    			docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
				    			String mmStatus = "";
				    			/*String status = changeModemService.parseTheFile(docForMetrNo,model);*/
				    			HashMap<String,Object> resMap= changeModemService.parseTheFile(docForMetrNo,model); 
				    			System.out.println("shri----"+resMap.toString());
				    			if("parsed".equalsIgnoreCase(String.valueOf(resMap.get("status"))))
				    			{
				    				/*mmStatus="";*/
				    				parsedCount++;
				    				parseXml = parseXml + files + "<br/>";
				    			}
				    			else if("meterDoesNotExist".equalsIgnoreCase(String.valueOf(resMap.get("status"))))
					    		{
				    				meterNotExistCount++;
					    			meterNotExist = meterNotExist + files + "<br/>";
					    		}
				    			else if("Duplicate".equalsIgnoreCase(String.valueOf(resMap.get("status"))))
				    			{
				    				duplicateCount++;
				    				duplicate = duplicate + files + "<br/>";
				    			}
				    			resMap.put("fileName",files);
				    			resMap.put("", resMap.get("g2date"));
				    		
				    			System.out.println("result--"+resMap.toString());
				    			
				    			XmlUploadSummary xmlUpload=new XmlUploadSummary();
				    			xmlUpload.setMeterno(String.valueOf(resMap.get("meterno")));
				    			xmlUpload.setFilename(String.valueOf(resMap.get("fileName")));
				    			xmlUpload.setStatus(String.valueOf(resMap.get("status")));
				    			if(resMap.get("g2date")!=null)
				    			{
				    			xmlUpload.setG2date((Timestamp) resMap.get("g2date"));
				    			}
				    			
				    			if(resMap.get("g3date")!=null)
				    			{
				    			xmlUpload.setG3date((Timestamp) resMap.get("g3date"));
				    			}
				    			xmlUpload.setMonth(Integer.parseInt(new SimpleDateFormat("yyyyMM").format(new Date())));
				    			xmlUpload.setUploaddate(new Timestamp(System.currentTimeMillis()));
				    			xmlUploadSummeryService.save(xmlUpload);
				    			
				    			/*CmriUploadStatusEntity cmrUpEntity=new CmriUploadStatusEntity();
				    			cmrUpEntity.setTimeStamp(new Timestamp(new Date().getTime()));
				    			cmrUpEntity.setBillStatus(Integer.parseInt(String.valueOf(resMap.get("billdataUpdation"))));
				    			cmrUpEntity.setEventStatus(Integer.parseInt(String.valueOf(resMap.get("eventsUpdation"))));
				    			cmrUpEntity.setInstaStatus(Integer.parseInt(String.valueOf(resMap.get("instanteneousUpdation"))));
				    			cmrUpEntity.setLoadStatus(Integer.parseInt(String.valueOf(resMap.get("LoadSurveyUpdation"))));
				    			cmrUpEntity.setFiledate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(resMap.get("filedate"))).getTime()));
				    			cmrUpEntity.setFailReason(String.valueOf(resMap.get("failReason")));
				    			cmrUpEntity.setMeternumber(String.valueOf(resMap.get("meternumber")));
				    			cmrUpEntity.setFailReason(String.valueOf(resMap.get("failReason")));
				    			cmrUpEntity.setUploadStatus(String.valueOf(resMap.get("uploadStatus")));
				    			cmrUpEntity.setMonth(month);
				    			cmrUpEntity.setParsed(Integer.parseInt(String.valueOf(resMap.get("parsed"))));
				    			cmrUpEntity.setReadfrom("CMRI");
				    			cmrUpEntity.setFilePath(files);*/
				    			
				    			
				    			/*if("1".equalsIgnoreCase(String.valueOf(resMap.get("parsed"))))
				    			{
				    				try
					    			{
					    				//cmriuploadservice.customupdatemdas(cmrUpEntity);
				    					cmriuploadservice.customupdateBySchema(cmrUpEntity,"postgresMdas");
					    			}catch(Exception e)
					    			{
					    				System.err.println("PROBLEM WHILE INSERTING INTO CMRI STATUS TABLE!!!!");
					    				e.printStackTrace();
					    			}
				    			}*/
				    			resJsonArray.put(resMap);
				    			System.out.println(" resMap=====>"+resMap);
				    			System.out.println(" parsed : "+parseXml);
				    			System.out.println(" parsedCount : "+ parsedCount);
				    			System.out.println(" meterNotExistCount : "+meterNotExistCount);
				    			System.out.println(" duplicateCount : "+duplicateCount);
				    			//session.setAttribute("listOfMaps", listOfFileResultantFiles);
				    			//model.addAttribute("listOfMaps",listOfFileResultantFiles);
							}
						}
					}
				}
				
				System.out.println("-------------- end of file list");
				double lIteratorEndTime = new Date().getTime();
				
				 lIteratorDifference = lIteratorEndTime - lIteratorStartTime;
				System.out.println("Time taken in milliseconds: " + lIteratorDifference);
			}
			
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				resJson.put("lIteratorDifference", lIteratorDifference);
				resJson.put("resFiles", resJsonArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return lIteratorDifference;
			return resJson;
		
	}
	
	@RequestMapping(value="/uploadStatusTrack",method={RequestMethod.GET,RequestMethod.POST})
	public String uploasStatusTrack(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
	System.out.println("inside upload status tracker");
	Date d1=new Date();
	String rdngmonth=new SimpleDateFormat("yyyyMM").format(d1);
	model.put("uploadStatus",cmriuploadservice.getAllUploadStatusData(rdngmonth));	
	return "uploadStatus";
	}
	
	@RequestMapping(value="/getXmlUploadSummery",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List getXmlUploadSummery(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
		System.out.println("inside getXmlUploadSummery");
		String rdngmonth=new SimpleDateFormat("yyyyMM").format(new Date());
		return cmriuploadservice.getAllUploadStatusData(rdngmonth);
	}
	
	
	@RequestMapping(value="/uploadSLDFile",method=RequestMethod.POST)
	public String uploadSLDFile(HttpServletRequest request,Model model,HttpSession session)
    {

		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dt = dateFormat.format(date);
            
			String townname=request.getParameter("townname").trim();
			String towncode=request.getParameter("townid").trim();
			String goLiveDate=request.getParameter("goLiveDate").trim();
			String technicalLoss=request.getParameter("technicalLoss").trim();
			String baselineLoss=request.getParameter("baseLineLoss").trim();
			//System.out.println("Baseline loss"+baselineLoss);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");
		
			String fileName  = myFile.getOriginalFilename();
					
			String extension = "";
			Date goLiveDateNew = null;
			int dotIndex = fileName.lastIndexOf(".");
			extension = fileName.substring(dotIndex+1);
			
			try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    goLiveDateNew = format.parse(goLiveDate);
			
		//	System.out.println("extension name : "+extension + " Towncode : "+towncode  + " Townname : "+townname  + " goLiveDate : "+goLiveDate+" goLiveDateNew : "+goLiveDateNew+" TechnicalLoss : "+technicalLoss);

			}catch(Exception e){
				e.printStackTrace();
			}
			TownEntity town= townMasterService.getTownEntity(towncode);
			if (town != null) {
				if(extension.equalsIgnoreCase("pdf")) {
					String orgfileName  = myFile.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
					town.setSld_file(myFile.getBytes());
					town.setFilename(fileName);
					
					town.setServer_filepath(FilterUnit.sldImageServerPath+ "/" + towncode+".jpg");
//					town.setServer_filepath(FilterUnit.sldUploadFolder + "/" + towncode+"/"+orgfileName+"_"+dt+".pdf");
					String uploadpath=uploadPdf_Server(orgfileName+dt,towncode, myFile);
					convertPdfToimage(fileName,towncode, myFile,uploadpath);
				}
				if(technicalLoss != "" ) {
					town.setTechnical_loss(technicalLoss);
				}
				if(baselineLoss != "" ) {
					town.setBaseline_loss(baselineLoss);
				}
				
				if(goLiveDate != "") {
					//System.out.println("Golive Date="+goLiveDateNew);
					town.setGolivedate(goLiveDateNew);
				}	
				
				
				town.setUpdatedby(session.getAttribute("username").toString());
				town.setUpdateddate(new Timestamp(System.currentTimeMillis()));
			}
			townMasterService.update(town);
			uploadFlag = 1;
			result = "Data Saved Successfully";
			model.addAttribute("results", result);
			model.addAttribute("alert_type", "success");
			
		}
		catch(Exception e)
		{
			uploadFlag = 3;
			result = "OOPS! Something went wrong!!";
			model.addAttribute("results", result);
			model.addAttribute("alert_type", "error");
			e.printStackTrace();
		}
				

		return "redirect:/townDetails";
		
//		return "redirect:/viewTownDetails";
		
	}
	
	
	private void convertPdfToimage(String fileName,String towncode, MultipartFile myFile,String uploadpath) {

		try {
	        //String sourceDir = uploadpath; // Pdf files are read from this folder
	        String destinationDir = FilterUnit.sldUploadFolder+"/"; // converted images from pdf document are saved here

//	        System.out.println("fileName==="+fileName);
//	        System.out.println("uploadpath==="+uploadpath);
	        File sourceFile = new File(uploadpath+".pdf");
	        File destinationFile = new File(destinationDir);
	        if (!destinationFile.exists()) {
	            destinationFile.mkdir();
//	            System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
	        }
	        if (sourceFile.exists()) {
//	            System.out.println("Images copied to Folder: "+ destinationFile.getName());             
	            PDDocument document = PDDocument.load(uploadpath+".pdf");
	            List<PDPage> list = document.getDocumentCatalog().getAllPages();
//	            System.out.println("Total files to be converted -> "+ list.size());

	            String fileName1 = myFile.getName().replace(".pdf", "");             
	            int pageNumber = 1;
	            for (PDPage page : list) {
	                BufferedImage image = page.convertToImage();
//	                File outputfile = new File(destinationDir + towncode +"_"+ pageNumber +".jpg");
	                File outputfile = new File(destinationDir + towncode  +".jpg");
//	                System.out.println("Image Created -> "+ outputfile.getName());
	                ImageIO.write(image, "jpg", outputfile);
	                pageNumber++;
	            }
	            document.close();
//	            System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
	        } else {
	            System.err.println(destinationFile.getName() +" File not exists");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
}
	
	
	
	
	private String uploadPdf_Server(String fileName,String towncode, MultipartFile myFile) {
		
		String filePath=null;
		
		if (!myFile.isEmpty()) {
			try {
				byte[] bytes = myFile.getBytes();

				// Creating the directory to store file
				String path = FilterUnit.sldUploadFolder + "/" + towncode ;

				File dir = new File(path);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				filePath=dir.getAbsolutePath()+ File.separator + fileName;
				File serverFile = new File(filePath+".pdf");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

//				System.out.println("You successfully uploaded file=" + fileName);
			} catch (Exception e) {
				e.getMessage();
//				System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
			}
		} 		
		
		return filePath;


	}

	private String getFolderPath(String towncode) {
		try {
			String path = FilterUnit.sldUploadFolder + "/" + towncode ;
			if (FilterUnit.folderExists(path)) {
				return path;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/uploadSLDSubstnFile",method=RequestMethod.POST)
	public String uploadSLDSubstnFile(HttpServletRequest request,Model model,HttpSession session)
    {

		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dt = dateFormat.format(date);
            
			String ssname=request.getParameter("ssname").trim();
			String sscode=request.getParameter("ssid").trim();
			
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");
		
			String fileName  = myFile.getOriginalFilename();
					
			String extension = "";
			Date goLiveDateNew = null;
			int dotIndex = fileName.lastIndexOf(".");
			extension = fileName.substring(dotIndex+1);
			
			try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   // goLiveDateNew = format.parse(goLiveDate);
			
		//	System.out.println("extension name : "+extension + " Towncode : "+towncode  + " Townname : "+townname  + " goLiveDate : "+goLiveDate+" goLiveDateNew : "+goLiveDateNew+" TechnicalLoss : "+technicalLoss);

			}catch(Exception e){
				e.printStackTrace();
			}
			SubstationDetailsEntity substan= substationDetails.getSubstationByCode(sscode); 
			if (substan != null) {
				if(extension.equalsIgnoreCase("pdf")) {
					String orgfileName  = myFile.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
					substan.setFilename(fileName);
					substan.setSld_file(myFile.getBytes());
					
					substan.setServer_filepath(FilterUnit.sldImageServerPath+ "/" + sscode+".jpg");
//					town.setServer_filepath(FilterUnit.sldUploadFolder + "/" + towncode+"/"+orgfileName+"_"+dt+".pdf");
					String uploadpath=uploadPdf_Server(orgfileName+dt,sscode, myFile);
					convertPdfToimage(fileName,sscode, myFile,uploadpath);
				}
				
				
				
				substan.setUpdate_by(session.getAttribute("username").toString());
				substan.setUpdate_date(new Timestamp(System.currentTimeMillis()));
			}
			substationDetails.update(substan);
			uploadFlag = 1;
			result = "Data Saved Successfully";
			model.addAttribute("results", result);
			model.addAttribute("alert_type", "success");
			
		}
		catch(Exception e)
		{
			uploadFlag = 3;
			result = "OOPS! Something went wrong!!";
			model.addAttribute("results", result);
			model.addAttribute("alert_type", "error");
			e.printStackTrace();
		}
				

		return "redirect:/substationdetails";
		
//		return "redirect:/viewTownDetails";
		
	}
	
	
	@RequestMapping(value="/viweSLDData/{townCode}",method={RequestMethod.GET,RequestMethod.POST})
	public String viweSLDData(@PathVariable("townCode") String towncode,HttpServletRequest request, HttpServletResponse response,Model model,HttpSession session)
    {
		FileInputStream fileStream;
		
		try{

			TownEntity town= townMasterService.getTownEntity(towncode);
			if (town != null) {
						
				try {
					if (town.getSld_file() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + town.getFilename() + "");
						OutputStream out = response.getOutputStream();
						InputStream is = new ByteArrayInputStream(town.getSld_file());
						IOUtils.copy(is, out);
						out.flush();
						out.close();
					} else {
						OutputStream out = response.getOutputStream();
						IOUtils.write("File Not Found", out);
						out.flush();
						out.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 return null;
		}
		return null;
	}
	
	@RequestMapping(value="/viweSLDSubstanData/{ssCode}",method={RequestMethod.GET,RequestMethod.POST})
	public String viweSLDSubstanData(@PathVariable("ssCode") String ssCode,HttpServletRequest request, HttpServletResponse response,Model model,HttpSession session)
    {
		FileInputStream fileStream;
		
		try{

			SubstationDetailsEntity substan= substationDetails.getSubstationByCode(ssCode); 
			if (substan != null) {
						
				try {
					if (substan.getSld_file() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + substan.getFilename() + "");
						OutputStream out = response.getOutputStream();
						InputStream is = new ByteArrayInputStream(substan.getSld_file());
						IOUtils.copy(is, out);
						out.flush();
						out.close();
					} else {
						OutputStream out = response.getOutputStream();
						IOUtils.write("File Not Found", out);
						out.flush();
						out.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 return null;
		}
		return null;
	}
	
	
	@RequestMapping(value="/townDetails",method={RequestMethod.POST,RequestMethod.GET})
	public  String townDetails(ModelMap model,HttpServletRequest request,HttpSession session)
	{
		//System.out.println("Town-details..");
		long currtime = System.currentTimeMillis();
		System.out.println("currtime..."+currtime);
		String userName = (String) session.getAttribute("username");
		
		String officeType = (String)session.getAttribute("officeType");
    	String officeCode=(String) session.getAttribute("officeCode");
    	String userType = (String)session.getAttribute("userType");
    	System.out.println("officeType="+officeType+" officeCode= "+officeCode+" userType= "+userType);
    	List<FeederMasterEntity> zoneList;
		if(officeType.equalsIgnoreCase("circle")) {
			zoneList = feederMasterService.getZoneByLogin(officeCode);
		}else {
			zoneList = feederMasterService.getDistinctZone();
		}
		
		
		if (uploadFlag == 1) {
			model.addAttribute("results", "Data Saved Successfully");
			model.addAttribute("alert_type", "success");
			uploadFlag = 0;
		} else if (uploadFlag == 2) {
			model.addAttribute("results", "Data Update Successfully");
			model.addAttribute("alert_type", "success");
			uploadFlag = 0;
		} else if (uploadFlag == 3) {
			model.addAttribute("results", "OOPS! Something went wrong!!");
			model.addAttribute("alert_type", "error");
			uploadFlag = 0;
		} else {
			model.addAttribute("results", "notDisplay");
		}
		 
		model.addAttribute("zoneList", zoneList);

		return "towndetails";
	}
	
	
	@Transactional
	@RequestMapping(value="/viewTownDetails",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody Object viewTownDetails(ModelMap model,HttpServletRequest request,HttpSession session)
	{
		//System.out.println("in controllee");
		List<?> townList=new ArrayList<>();
		String zone=request.getParameter("zone").trim();
		String circle=request.getParameter("circle").trim();  	
    	
        try {		
        	townList=substationDetails.getTownList(zone,circle);
        } catch (Exception e) {
				e.printStackTrace();
			}
    	
    	return townList;
	}
	
	
	@Transactional
	@RequestMapping(value = "/deleteSLDData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object deleteSLDData(HttpServletRequest request, Model model) {

		String townCode = request.getParameter("townCode").trim();
		String townName = request.getParameter("townName").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));
		
		
		TownEntity town= townMasterService.getTownEntity(townCode);
		if (town != null) {
			
		 try {
				if (town.getSld_file() != null) {	
					town.setSld_file(null);
					town.setFilename(null);
					town.setServer_filepath(null);
					town.setUpdatedby(session.getAttribute("username").toString());
					town.setUpdateddate(new Timestamp(System.currentTimeMillis()));	
				}
				
				townMasterService.update(town);
				return "success";
			}  catch (Exception e) {
				e.printStackTrace();
				return "failed";
				
			}
			

		}else {
			System.out.println("No Entity Found ");
			return "failed";
			
		}

	}
	
	@Transactional
	@RequestMapping(value = "/deleteSLDSubstanData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object deleteSLDSubstanData(HttpServletRequest request, Model model) {

		String ssCode = request.getParameter("ssCode").trim();
		String ssName = request.getParameter("ssName").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));
		
		
		SubstationDetailsEntity substan= substationDetails.getSubstationByCode(ssCode); 
		if (substan != null) {
			
		 try {
				if (substan.getSld_file() != null) {	
					substan.setSld_file(null);
					substan.setFilename(null);
					substan.setServer_filepath(null);
					substan.setUpdate_by(session.getAttribute("username").toString());
					substan.setUpdate_date(new Timestamp(System.currentTimeMillis()));	
				}
				
				substationDetails.update(substan);
				return "success";
			}  catch (Exception e) {
				e.printStackTrace();
				return "failed";
				
			}
			

		}else {
			System.out.println("No Entity Found ");
			return "failed";
			
		}

	}

	@RequestMapping(value="/towndetailspdf",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void towndetailspdf(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String reg=request.getParameter("reg");
		String cir=request.getParameter("cir");

		String region="",circle="";
		
		if(reg.equalsIgnoreCase("ALL")) 
		{
			region="%";
		}else {
			region=reg;
		}
		if(cir.equalsIgnoreCase("ALL"))
		{
			circle="%";
		}else {
			circle=cir;
		}
		
		substationDetails.getTownDetailsPdf(request, response, region, circle);
	}
	
	@RequestMapping(value="/exportToExcelTownDetailsData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelTownDetailsData(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {
			String region=request.getParameter("reg");
			String circle= request.getParameter("cir");
			
			String reg="",cir;
			if(region.equalsIgnoreCase("ALL"))
			{
				reg="%";
			}else {
				reg=region;
			}
			if(circle.equalsIgnoreCase("ALL"))
			{
				cir="%";
			}else {
				cir=circle;
			}
	    
		    String fileName = "TownDetails";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header  = sheet.createRow(0);
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
				header.createCell(0).setCellValue("S.NO");		
				header.createCell(1).setCellValue("CIRCLE CODE");
				header.createCell(2).setCellValue("CIRCLE NAME");
				header.createCell(3).setCellValue("TOWN CODE");
				header.createCell(4).setCellValue("TOWN NAME");
				header.createCell(5).setCellValue("TECHNICAL LOSS(%)");
				header.createCell(6).setCellValue("BASELINE LOSS(%)");
				header.createCell(7).setCellValue("GOLIVE LOSS(%)");
				header.createCell(8).setCellValue("ENTRY BY");
				header.createCell(9).setCellValue("ENTRY DATE");
				header.createCell(10).setCellValue("UPDATED BY");
				header.createCell(11).setCellValue("UPDATED DATE");
				header.createCell(12).setCellValue("REGION CODE");
				header.createCell(13).setCellValue("REGION");
				
				List<?> townData=null;
				townData =substationDetails.getExcelTownList(reg,cir);
				
				int count =1;
				int cellNO=0;
	            for(Iterator<?> iterator=townData.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		if(values[3]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[3]));
	      		}
	      		
	      		if(values[2]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[2]));
	      		}
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[0]));
	      		}
	      		
	      		if(values[1]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[1]));
	      		}
	      		
	      		if(values[7]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[7]));
	      		}
	      		
	      		if(values[12]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[12]));
	      		}
	      		
	      		if(values[8]==null)
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(values[8]));
	      		}
	      		
	      		if(values[4]==null)
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(values[4]));
	      		}
	      		
	      		if(values[5]==null)
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(values[5]));
	      		}
	      		
	      		if(values[10]==null)
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(values[10]));
	      		}
	      		
	      		if(values[11]==null)
	      		{
	      			row.createCell(11).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(11).setCellValue(String.valueOf(values[11]));
	      		}
	      		if(values[13]==null)
	      		{
	      			row.createCell(12).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(12).setCellValue(String.valueOf(values[13]));
	      		}
	      		
	      		if(values[14]==null)
	      		{
	      			row.createCell(13).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(13).setCellValue(String.valueOf(values[14]));
	      		}
	      		
	      		count ++;
	             }
				
	            FileOutputStream fileOut = new FileOutputStream(fileName);    	
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
			    
			    ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"TownDetails.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelTownDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelTownDetails(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {

			String region=request.getParameter("reg");
			String circle= request.getParameter("cir");
			
			String reg="",cir;
			if(region.equalsIgnoreCase("ALL"))
			{
				reg="%";
			}else {
				reg=region;
			}
			if(circle.equalsIgnoreCase("ALL"))
			{
				cir="%";
			}else {
				cir=circle;
			}
	    
		    String fileName = "TownSampleDetails";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header  = sheet.createRow(0);
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
				header.createCell(0).setCellValue("TOWN CODE");
				header.createCell(1).setCellValue("TOWN NAME");
				header.createCell(2).setCellValue("TECHNICAL LOSS(%)");
				header.createCell(3).setCellValue("BASELINE LOSS(%)");
				header.createCell(4).setCellValue("GOLIVE DATE");
				
				List<?> townData=null;
				townData =substationDetails.getExcelTownList(reg,cir);
				
				int count =1;
				//int cellNO=0;
	            for(Iterator<?> iterator=townData.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		//cellNO++;
	      		//row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		if(values[0]==null)
	      		{
	      			row.createCell(0).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(0).setCellValue(String.valueOf(values[0]));
	      		}
	      		
	      		if(values[1]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(values[1]));
	      		}
	      		
	      		if(values[7]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[7]));
	      		}
	      		
	      		if(values[12]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[12]));
	      		}
	      		
	      		if(values[8]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[8]));
	      		}
	      		
	      		count ++;
	             }
				
	            FileOutputStream fileOut = new FileOutputStream(fileName);    	
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
			    
			    ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"SampleTownDetails.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
			
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
