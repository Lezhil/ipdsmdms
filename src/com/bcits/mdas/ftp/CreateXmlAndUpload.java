package com.bcits.mdas.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity.KeyUplaodStatus;
import com.bcits.mdas.mqtt.Subscriber;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.XmlUploadStatusService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.FilterUnit;



@Controller
public class CreateXmlAndUpload {
	
	@Autowired
	private AmrInstantaneousService amrInstantaneousService;

	@Autowired
	private AmrBillsService amrBillsService;

	@Autowired
	private AmrEventsService amrEventsService;

	@Autowired
	private AmrLoadService amrLoadService;
	
	@Autowired
	private MasterMainService feederMasterService;
	
	@Autowired
	private XmlUploadStatusService xmlUplaodService;

	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager postgresMdas;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	public static final String SUCCESS="SUCCESS";
	
	//FOR SCHEDULER
	public CreateXmlAndUpload(AmrInstantaneousService amrInstantaneousService,AmrBillsService amrBillsService,AmrEventsService amrEventsService,AmrLoadService amrLoadService,MasterMainService feederMasterService,XmlUploadStatusService xmlUplaodService){
		this.amrInstantaneousService = amrInstantaneousService;
		this.amrBillsService = amrBillsService;
		this.amrEventsService = amrEventsService;
		this.amrLoadService = amrLoadService;
		this.feederMasterService = feederMasterService;
		this.xmlUplaodService = xmlUplaodService;
	}
	
	CreateXmlAndUpload(){
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/createAllXmlAndUpload/{fileDate}", method = RequestMethod.GET)
	public String createAllXmlAndUpload(@PathVariable String fileDate) {
		System.out.println("====Inside createAllXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createAllXmlAndUpload****");
		return createAndUploadXML(fileDate);
	}
	@ResponseBody
	@RequestMapping(value = "/createAllXml/{fileDate}", method = RequestMethod.GET)
	public String createAllXml(@PathVariable String fileDate) {
		System.out.println("====Inside createAllXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createAllXmlAndUpload****");
		return createAllXML(fileDate);
	}
	
	
	 String createAndUploadXML(String fileDate){
		JSONArray response=new JSONArray();
		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
				List<Object[]> feederMaster = feederMasterService.getMeterDetailsForXml();
				FTPUploadFile ftp = new FTPUploadFile();
				for (Object[] columns : feederMaster) {
					String meterNumber = columns[0].toString().trim();
					String status="";
					try {
						status= generateXmlAndUpload(meterNumber, fileDate, ftp, filepath);
					}catch (Exception e) {
						e.printStackTrace();
					}
					response.put(new JSONObject().put("meterNo", meterNumber).put("date", fileDate).put("status", status));
				}
				
				ftp.closeFTP();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return response.toString();
	}
	
	
	 String createAllXML(String fileDate){
			JSONArray response=new JSONArray();
			try {
				 String filepath=getFolderPath(fileDate);
				    if(filepath==null){
				    	return "INVALID DATE FORMAT";
				    }
					List<Object[]> feederMaster = feederMasterService.getMeterDetailsForXml();
					//FTPUploadFile ftp = new FTPUploadFile();
					for (Object[] columns : feederMaster) {
						String meterNumber = columns[0].toString().trim();
						String status="";
						try {
							status= generateXml(meterNumber, fileDate, filepath);
						}catch (Exception e) {
							e.printStackTrace();
						}
						response.put(new JSONObject().put("meterNo", meterNumber).put("date", fileDate).put("status", status));
					}
					
					//ftp.closeFTP();
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			return response.toString();
		}
	 
	@ResponseBody
	@RequestMapping(value = "/createSingleXmlAndUpload/{meterNumber}/{fileDate}", method = RequestMethod.GET)
	public String createSingleXmlAndUpload(@PathVariable String meterNumber, @PathVariable String fileDate) {

		String response="";
		try {
			    String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
				FTPUploadFile ftp= new FTPUploadFile();
				response=generateXmlAndUpload(meterNumber,fileDate,ftp, filepath);
				ftp.closeFTP();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return response;
	}
	
	private String getFolderPath(String fileDate){
		try {
			Date date =dateFormat.parse(fileDate);
			String month=FilterUnit.ftpSourceFolder+"/"+new SimpleDateFormat("yyyyMM").format(date);
			String day=month+"/"+new SimpleDateFormat("yyyyMMdd").format(date);
			if(FilterUnit.folderExists(month)){
				if(FilterUnit.folderExists(day)){
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	private String generateXmlAndUpload(String meterNumber, String fileDate, FTPUploadFile ftp, String filePath) throws ParseException{
		String uploadStatus;
		//GETTING DATA FOR XML CREATION
		 Object feederMasterEntity =feederMasterService.getD1GData(meterNumber);//GETTING MASTER DATA IN OBJECT FORMAT
			System.out.println(feederMasterEntity+"   FEEDER MASTER DATA----------------");
			
			if(feederMasterEntity==null) {
				return "NO MASTER DATA FOUND";
			}
		System.out.println("fileDate======="+fileDate);
		AmrInstantaneousEntity amrInstantaneousEntity = amrInstantaneousService.getD1DataForXml(meterNumber,fileDate);//GETTING INSTANTANEOUS DATA
		List<AmrBillsEntity> amrBill = amrBillsService.getRecords(meterNumber,fileDate);//GETTING BILL DATA
		List<AmrEventsEntity> amrEvents = amrEventsService.getRecords(meterNumber,fileDate); //GETTING EVENTS DATA
		List<AmrLoadEntity> amrLoad = amrLoadService.getRecords(meterNumber,fileDate);//GETTING LOAD DATA

		
	
		//INITIATING XML UPLOAD TRACKING SYSTEM
		XmlUploadStatusEntity xmlStatus= new XmlUploadStatusEntity();//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
		xmlStatus.setMyKey(new KeyUplaodStatus(meterNumber, dateFormat.parse(fileDate)));
		xmlStatus.setTimeStamp(new Timestamp(new Date().getTime()));
		if(null!=amrInstantaneousEntity){
			xmlStatus.setInstaStatus(1);
		}
		if(null!=amrBill && amrBill.size()>0){
			xmlStatus.setBillStatus(1);
		}
		if(null!=amrEvents && amrEvents.size()>0){
			xmlStatus.setEventStatus(1);
		}
		if(null!=amrLoad && amrLoad.size()>0){
			xmlStatus.setLoadStatus(1);
		}
		
		String fileName=meterNumber+"_"+fileDate;
		String xmlFileName =filePath+"/"+fileName+".xml";
		
		XmlRECCreator c = new XmlRECCreator();
		
		System.out.println(xmlFileName);
		System.out.println(amrInstantaneousEntity);
		System.out.println(amrBill);
		System.out.println(amrLoad);
		System.out.println(amrEvents);
		
		
		if (amrInstantaneousEntity != null) {//CREATE XML HERE
			// String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			if (xmlCreatedResult.equals(SUCCESS)) {
				String status = ftp.uploadFile(xmlFileName);
				System.out.println(status);
				if (status.equals(SUCCESS)) {
					xmlStatus.setUploadStatus(1);
					xmlStatus.setCreate_status(1);
					xmlStatus.setFilePath(xmlFileName);
					uploadStatus=SUCCESS;
				} else {
					xmlStatus.setUploadStatus(0);
					xmlStatus.setCreate_status(1);
					xmlStatus.setFailReason(status);
					uploadStatus= status;
				}
			} else {
				xmlStatus.setUploadStatus(0);
				xmlStatus.setCreate_status(0);
				xmlStatus.setFailReason(xmlCreatedResult);
				uploadStatus= xmlCreatedResult;
			}
		}else{
			xmlStatus.setUploadStatus(0);
			xmlStatus.setCreate_status(0);
			xmlStatus.setFailReason("XML NOT CREATED. NO DATA AVAILABLE");
			uploadStatus= "XML NOT CREATED. NO DATA AVAILABLE";
		}
		
		System.out.println(xmlStatus.getUploadStatus()+"   ===================" );
		
		if(xmlUplaodService.customupdatemdas(xmlStatus) instanceof XmlUploadStatusEntity){  
			return uploadStatus;
		}else{
			return "FAILED TO UPDATE STATUS";
		}
		
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	private String generateXml(String meterNumber, String fileDate, String filePath) throws ParseException{
		
		
		String uploadStatus;
		//GETTING DATA FOR XML CREATION
		 Object feederMasterEntity =feederMasterService.getD1GData(meterNumber);//GETTING MASTER DATA IN OBJECT FORMAT
			System.out.println(feederMasterEntity+"   FEEDER MASTER DATA----------------");
			
			if(feederMasterEntity==null) {
				return "NO MASTER DATA FOUND";
			}
		System.out.println("fileDate======="+fileDate);
		AmrInstantaneousEntity amrInstantaneousEntity = amrInstantaneousService.getD1DataForXml(meterNumber,fileDate);//GETTING INSTANTANEOUS DATA
		List<AmrBillsEntity> amrBill = amrBillsService.getRecords(meterNumber,fileDate);//GETTING BILL DATA
		List<AmrEventsEntity> amrEvents = amrEventsService.getRecords(meterNumber,fileDate); //GETTING EVENTS DATA
		List<AmrLoadEntity> amrLoad = amrLoadService.getRecords(meterNumber,fileDate);//GETTING LOAD DATA

		
	
		//INITIATING XML UPLOAD TRACKING SYSTEM
		XmlUploadStatusEntity xmlStatus= new XmlUploadStatusEntity();//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
		xmlStatus.setMyKey(new KeyUplaodStatus(meterNumber, dateFormat.parse(fileDate)));
		xmlStatus.setTimeStamp(new Timestamp(new Date().getTime()));
		if(null!=amrInstantaneousEntity){
			xmlStatus.setInstaStatus(1);
		}
		if(null!=amrBill && amrBill.size()>0){
			xmlStatus.setBillStatus(1);
		}
		if(null!=amrEvents && amrEvents.size()>0){
			xmlStatus.setEventStatus(1);
		}
		if(null!=amrLoad && amrLoad.size()>0){
			xmlStatus.setLoadStatus(1);
		}
		
		String fileName=meterNumber+"_"+fileDate;
		String xmlFileName =filePath+"/"+fileName+".xml";
		
		XmlRECCreator c = new XmlRECCreator();
		
		
		if (amrInstantaneousEntity != null) {//CREATE XML HERE
			// String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			if (xmlCreatedResult.equals(SUCCESS)) {
					xmlStatus.setUploadStatus(0);
					xmlStatus.setCreate_status(1);
					uploadStatus="XML CREATED SUCCEFULLY.";
				
			} else {
				xmlStatus.setUploadStatus(0);
				xmlStatus.setCreate_status(0);
				xmlStatus.setFailReason(xmlCreatedResult);
				uploadStatus= xmlCreatedResult;
			}
		}else{
			xmlStatus.setUploadStatus(0);
			xmlStatus.setCreate_status(0);
			xmlStatus.setFailReason("XML NOT CREATED. NO DATA AVAILABLE");
			uploadStatus= "XML NOT CREATED. NO DATA AVAILABLE";
		}
		
		System.out.println(xmlStatus.getUploadStatus()+"   ===================" );
		
		if(xmlUplaodService.customupdatemdas(xmlStatus) instanceof XmlUploadStatusEntity){  
			return uploadStatus;
		}else{
			return "FAILED TO UPDATE STATUS";
		}
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/sendMsgMQTT/{topic}/{payLoad}", method = RequestMethod.GET)
	public String sendMsgMQTT(@PathVariable String topic, @PathVariable String payLoad) {
		return Subscriber.sendMqttMessage(topic, payLoad);
	}
	
	
	
	
	@RequestMapping(value = "/createFailedXmlAndUpload/{fileDate}", method = RequestMethod.GET)
	public @ResponseBody Object createFailedXmlAndUpload(@PathVariable String fileDate) {
		System.out.println("====Inside createFailedXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createFailedXmlAndUpload****");
		return getFailedXmlforUpload(fileDate);
	}
	
	

	
	
	Object getFailedXmlforUpload(String fileDate) {
		List<Map<String, String>> result=new ArrayList<>();
 		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
				List<Object> feederMaster = xmlUplaodService.getMeterDetailsForFailedXml(fileDate);
				FTPUploadFile ftp = new FTPUploadFile();
				for (Object columns : feederMaster) {
					String meterNumber = columns.toString().trim();
					StringBuilder sb=new StringBuilder();
					
					Map<String, String> map=new HashMap<>();
					map.put("mtrno", meterNumber);
					
					try {
						map.put("status", generateXmlAndUpload(meterNumber, fileDate, ftp, filepath));
						/*Future<Boolean> future=updateAsynchronous.generateFailedXml(xmlUplaodService,meterNumber, fileDate, ftp, filepath);
						status+=future;*/
					}catch (Exception e) {
						map.put("status", "failed");
						e.printStackTrace();
					}
					map.put("date", fileDate);
					result.add(map);
				}
				
				ftp.closeFTP();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	@RequestMapping(value = "/createFailedXml/{fileDate}", method = RequestMethod.GET)
	public @ResponseBody Object createFailedXml(@PathVariable String fileDate) {
		System.out.println("====Inside createFailedXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createFailedXmlAndUpload****");
		return getFailedXmlforGenerate(fileDate);
	}
	
	Object getFailedXmlforGenerate(String fileDate) {
		List<Map<String, String>> result=new ArrayList<>();
 		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
				List<Object> feederMaster = xmlUplaodService.getMeterDetailsForFailedXml(fileDate);
				//FTPUploadFile ftp = new FTPUploadFile();
				for (Object columns : feederMaster) {
					String meterNumber = columns.toString().trim();
					StringBuilder sb=new StringBuilder();
					
					Map<String, String> map=new HashMap<>();
					map.put("mtrno", meterNumber);
					
					try {
						map.put("status", generateXml(meterNumber, fileDate,  filepath));
						/*Future<Boolean> future=updateAsynchronous.generateFailedXml(xmlUplaodService,meterNumber, fileDate, ftp, filepath);
						status+=future;*/
					}catch (Exception e) {
						map.put("status", "failed");
						e.printStackTrace();
					}
					map.put("date", fileDate);
					result.add(map);
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	public void generateFailedXmlCron(XmlUploadStatusService statusService) {
		xmlUplaodService=statusService;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -30);
		Date d1=c.getTime();
		String to=sdf.format(d);
		String from=sdf.format(d1);
		System.out.println(" from : "+from+" , to : "+to);
		List<Object> dates = xmlUplaodService.getLast30DaysDates(from, to);
		for (Object columns : dates) {
			String filedate = columns.toString().trim();
			try {
				getFailedXmlforUpload(filedate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping(value = "/uploadFailedXml/{fileDate}", method = RequestMethod.GET)
	public @ResponseBody Object uploadFailedXml(@PathVariable String fileDate) {
		System.out.println("====Inside createFailedXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createFailedXmlAndUpload****");
		return reUploadXml(fileDate);
	}
	
	Object reUploadXml(String fileDate) {
		JSONArray res=new JSONArray();
		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
				List<Object> feederMaster = xmlUplaodService.getMeterNumbersForReUpload(fileDate);
				if(feederMaster!=null) {
					FTPUploadFile ftp = new FTPUploadFile();
					for (Object columns : feederMaster) {
						String meterNumber = columns.toString().trim();
						String status="";
						try {
							status= reUploadGeneratedXml(meterNumber, fileDate, ftp, filepath);
						}catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println(meterNumber);
						
						res.put(new JSONObject().put("meterNo", meterNumber).put("date", fileDate).put("status", status));
					}
					ftp.closeFTP();
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return res;

	}

	
	private String reUploadGeneratedXml(String meterNumber, String fileDate, FTPUploadFile ftp, String filePath) {

		String uploadStatus="failed";
	
		//INITIATING XML UPLOAD TRACKING SYSTEM
		XmlUploadStatusEntity xmlStatus= xmlUplaodService.getRecordByMeterNDate(meterNumber,fileDate);//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
		
		if(xmlStatus!=null) {
			String fileName=meterNumber+"_"+fileDate;
			String xmlFileName =filePath+"/"+fileName+".xml";
			String status = ftp.uploadFile(xmlFileName);
			System.out.println(status);
			if (status.equals(SUCCESS)) {
				xmlStatus.setUploadStatus(1);
				xmlStatus.setFailReason(null);
				xmlStatus.setFilePath(xmlFileName);
				uploadStatus=SUCCESS;
			} else {
				xmlStatus.setUploadStatus(0);
				xmlStatus.setFailReason(status);
				uploadStatus= status;
			}
		}
		
		System.out.println(xmlStatus.getUploadStatus()+"   ===================" );
		
		if(xmlUplaodService.customupdatemdas(xmlStatus) instanceof XmlUploadStatusEntity){  
			return uploadStatus;
		}else{
			return "FAILED TO UPDATE STATUS";
		}
		
	}
	
	public void reUploadFailedxmlCron(String filedate,  XmlUploadStatusService Service) {
		xmlUplaodService=Service;
		this.reUploadXml(filedate);
	}
	
	@RequestMapping(value = "/generateXmlAndDownload/{fileDate}", method = RequestMethod.GET)
	public @ResponseBody Object generateXmlAndDownload(@PathVariable String fileDate, HttpServletRequest request,HttpServletResponse response) {
		System.out.println("====Inside createFailedXmlAndUpload====");
		BCITSLogger.logger.info("*****Inside createFailedXmlAndUpload****");
		String zipfilePath=getGenerateXmlAndDownload(fileDate);
		File file = new File(zipfilePath);
		 InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			try {
				response.setHeader("Content-Disposition", "inline;filename=\""+ fileDate + ".zip\"");
				response.setContentType("application/zip");
				OutputStream out = response.getOutputStream();
				IOUtils.copy(inputStream, out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			System.out.println(file.getName());
			
		
		return null;
	}
	
	String getGenerateXmlAndDownload(String fileDate) {
		//List<Map<String, String>> result=new ArrayList<>();
 		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	return "INVALID DATE FORMAT";
			    }
			   
			    String zipfilePath=filepath+"/"+fileDate+"_"+new SimpleDateFormat("MMddHHmm").format(new Date())+".zip";
				ZipFile zipFile = new ZipFile(zipfilePath);
				
		        ArrayList<File> filesToAdd = new ArrayList<File>();
		        
			    String qry="SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE date='"+fileDate+"';";
				List<Object> feederMaster = postgresMdas.createNativeQuery(qry).getResultList();
				//FTPUploadFile ftp = new FTPUploadFile();
				for (Object columns : feederMaster) {
					String meterNumber = columns.toString().trim();
					StringBuilder sb=new StringBuilder();
					
					//Map<String, String> map=new HashMap<>();
					/*ApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
					UpdateAsynchronous updateAsynchronous=context.getBean(UpdateAsynchronous.class);*/
					//map.put("mtrno", meterNumber);
					
					try {
						//map.put("status", generateXmlNew(meterNumber, fileDate,  filepath));
						/*Future<Boolean> future=updateAsynchronous.generateFailedXml(xmlUplaodService,meterNumber, fileDate, ftp, filepath);
						status+=future;*/
						
						if("XML CREATED SUCCEFULLY.".equalsIgnoreCase(generateXmlNew(meterNumber, fileDate,  filepath))) {
							String path=filepath+"/"+meterNumber+"_"+fileDate+".xml";
							System.out.println(path);
			            	filesToAdd.add(new File(path));
			            	System.out.println("filesToAdd indiv==>"+filesToAdd);
							
						}
						
					}catch (Exception e) {
						//map.put("status", "failed");
						e.printStackTrace();
					}
					
					//map.put("date", fileDate);
					//result.add(map);
				}
				ZipParameters parameters = new ZipParameters();
	            //zipFile.addFiles(filesToAdd, parameters);
	            zipFile.createZipFile(filesToAdd, parameters);
	            return zipfilePath;
	            
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	private String generateXmlNew(String meterNumber, String fileDate, String filePath) throws ParseException{
		
		
		String uploadStatus;
		//GETTING DATA FOR XML CREATION
		 Object feederMasterEntity=null;
		 /*Object feederMasterEntity =feederMasterService.getD1GData(meterNumber);//GETTING MASTER DATA IN OBJECT FORMAT
			System.out.println(feederMasterEntity+"   FEEDER MASTER DATA----------------");
			
			if(feederMasterEntity==null) {
				return "NO MASTER DATA FOUND";
			}*/
		//System.out.println("fileDate======="+fileDate);
		AmrInstantaneousEntity amrInstantaneousEntity = amrInstantaneousService.getD1DataForXml(meterNumber,fileDate);//GETTING INSTANTANEOUS DATA
		List<AmrBillsEntity> amrBill = amrBillsService.getRecords(meterNumber,fileDate);//GETTING BILL DATA
		List<AmrEventsEntity> amrEvents = amrEventsService.getRecords(meterNumber,fileDate); //GETTING EVENTS DATA
		List<AmrLoadEntity> amrLoad = amrLoadService.getRecords(meterNumber,fileDate);//GETTING LOAD DATA

		
	
		//INITIATING XML UPLOAD TRACKING SYSTEM
		XmlUploadStatusEntity xmlStatus= new XmlUploadStatusEntity();//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
		xmlStatus.setMyKey(new KeyUplaodStatus(meterNumber, dateFormat.parse(fileDate)));
		xmlStatus.setTimeStamp(new Timestamp(new Date().getTime()));
		if(null!=amrInstantaneousEntity){
			xmlStatus.setInstaStatus(1);
		}
		if(null!=amrBill && amrBill.size()>0){
			xmlStatus.setBillStatus(1);
		}
		if(null!=amrEvents && amrEvents.size()>0){
			xmlStatus.setEventStatus(1);
		}
		if(null!=amrLoad && amrLoad.size()>0){
			xmlStatus.setLoadStatus(1);
		}
		
		String fileName=meterNumber+"_"+fileDate;
		String xmlFileName =filePath+"/"+fileName+".xml";
		
		XmlRECCreator c = new XmlRECCreator();
		
		String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
		if (xmlCreatedResult.equals(SUCCESS)) {
				/*xmlStatus.setUploadStatus(0);
				xmlStatus.setCreate_status(1);*/
				uploadStatus="XML CREATED SUCCEFULLY.";
			
		} else {
			/*xmlStatus.setUploadStatus(0);
			xmlStatus.setCreate_status(0);
			xmlStatus.setFailReason(xmlCreatedResult);*/
			uploadStatus= xmlCreatedResult;
		}
		
		
		System.out.println(xmlStatus.getUploadStatus()+"   ===================" );
		
		/*if(xmlUplaodService.update(xmlStatus) instanceof XmlUploadStatusEntity){  
			return uploadStatus;
		}else{
			return "FAILED TO UPDATE STATUS";
		}*/
		return uploadStatus;
	}
	
	@RequestMapping(value = "/createFailedXmlNew/{fileDate}", method = RequestMethod.GET)
	public @ResponseBody Object createFailedXmlNew(@PathVariable String fileDate) {
		System.out.println("====Inside createAndUploadFailedXmlNew====");
		getFailedXmlNew(fileDate);
		return "started";
		
	}
	
	void getFailedXmlNew(String fileDate) {
 		try {
			 String filepath=getFolderPath(fileDate);
			    if(filepath==null){
			    	
			    }else {
			    	/*List<Object> feederMaster = xmlUplaodService.getMeterDetailsForFailedXml(fileDate);*/
			    	List<Object> feederMaster = xmlUplaodService.getMeterDetailsForFailedXmlNew(fileDate);
					for (Object columns : feederMaster) {
						String meterNumber = columns.toString().trim();
						
						try {
							//generateXmlAndUpload(meterNumber, fileDate, ftp, filepath);
							//System.out.println("creating new thread");
							new CreateXmlThread(meterNumber, fileDate, filepath,amrInstantaneousService,amrBillsService,
									amrEventsService,amrLoadService,feederMasterService,xmlUplaodService).run();
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
			} 
 		}catch (Exception e) {
				e.printStackTrace();
		}
				
	}
}
