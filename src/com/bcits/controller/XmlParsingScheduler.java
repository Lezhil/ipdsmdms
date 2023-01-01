package com.bcits.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import com.bcits.entity.MobileGenStatusEntity1;
import com.bcits.service.MobileGenStatusService1;
import com.bcits.service.UserService;
import com.bcits.utility.ReportsSMS;

@Controller
public class XmlParsingScheduler
{
	@Autowired
	private UserService userService;
	@Autowired
	private MobileGenStatusService1 mobileGenStatusService;
	
	public static String status="AMR";
	private static String rootFolder ="D:\\MIP_uploads"; //test 
	//private static String rootFolder ="/backupfiles/apache-tomcat/bin/MIP_uploads";//LIVE
	//public static String meterFileFolder=rootFolder+"\\201809"+"\\test";

	//	@Scheduled(cron="0 0 8 * * ?")	
		public void  parseFile()
		{
			System.out.println("inside parse mobile scheduler");
			//getting dynamic folder name
			Date d=new Date();
			Calendar c=Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, -1);
			String billmonth=new SimpleDateFormat("yyyyMM").format(c.getTime());
			ModelMap model=new ModelMap();
			
			 DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				Calendar now = Calendar.getInstance();
				now.add(Calendar.DATE, -1);
			    String presentDate = dateFormat.format(Calendar.getInstance().getTime());
			    String yesterDay = dateFormat.format(now.getTime());
	       
			    Calendar presentDay=Calendar.getInstance();
	       presentDay.setTime(d);
	       String currentDate=new SimpleDateFormat("dd-MM-yyyy").format(presentDay.getTime());
	       System.err.println("currentDate===>"+currentDate);
			
			System.err.println("presentDate===>"+presentDate);
			System.err.println("yesterDay===>"+yesterDay);
			System.err.println("bill month===>"+billmonth);
			String unZipFolderPath=rootFolder+"/"+billmonth+"/"+yesterDay;
			System.err.println("dynamic--"+rootFolder+"\\"+billmonth+"\\"+yesterDay);
		    System.out.println("unZipFolderPath-->"+unZipFolderPath);
			Date date1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			String bilmonth=sdf.format(date1);
			System.out.println(bilmonth);
			this.getUnzippedXmlsMobiles(unZipFolderPath, bilmonth);
		}
		
		@Async
		 public  void getUnzippedXmlsMobiles(String unZipFolderPath, String billMonth) 
			{
			 ModelMap model=new ModelMap();
				double lIteratorDifference=0;
				String files = "";
				Date d=new Date();
				//extracting date from path
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Calendar now = Calendar.getInstance();
				now.add(Calendar.DATE, -1);
				try {
					d=dateFormat.parse(dateFormat.format(now.getTime()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				List parseMainStatus=new ArrayList<>();
				try
				{
					double lIteratorStartTime = new Date().getTime();
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
						System.out.println("-----inside 2 ");
						for(int i =0;i < listOfFiles.length;i++)
						{
							System.out.println("-----inside 3 ");
							if(listOfFiles[i].isFile())
							{
								System.out.println("-----inside 4 ");
								files = listOfFiles[i].getName();
								
								String extension="";
								int dotIndex = files.lastIndexOf(".");
								extension = files.substring(dotIndex+1);

								if(extension.equalsIgnoreCase("xml") || files.endsWith("XML"))
								{
									System.out.println("-----inside 5 ");
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
					    			String status = userService.parseTheFile(docForMetrNo, billMonth,model, unZipFolderPath,files).trim();
					    			String[] st=status.split("/");
					    			System.out.println("statusss=="+status);
					    			System.out.println("st[0]--"+st[0]);
					    			System.out.println("st[1]--"+st[1]);
					    			
					    			MobileGenStatusEntity1 mobileStatus=new MobileGenStatusEntity1();
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
					    			
					    			//mobileStatus.setDates_of_files(d);
					    			mobileStatus.setBillmonth(billMonth);
				    				mobileStatus.setMeterno(st[1]);
				    				mobileStatus.setDb_date(new Date());
				    				mobileStatus.setFilename(files);
				    				mobileStatus.setFilepath(path+"/"+files);
					    			mobileGenStatusService.save(mobileStatus);
					    			System.err.println("SAVED INTO PARSE SCHEDULAR TABLE=======>"+files);
								}
								else
								{
									model.addAttribute("result","No files..");
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
		

		//@Scheduled(cron="0 0/1 * * * ?")
		//@Scheduled(cron="0 0 9 * * ?")
		public void ReportsEmail()
		{
			System.err.println("in schedular");
			List<String[]> list=userService.getReport();
			System.err.println(list.size());
		        System.err.println("listsixze----- "+list.size());
		       File file123 = new File("/backupfiles/apache-tomcat/bin/AMR_FILES/amrreport.xls");
		     //   File file123 = new File("D:/AMR_FILES/amrreport.xls");
		       
		        try {
					file123.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("MDI Exceed Report") ;
			
				XSSFCellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
				font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
				font.setFontHeightInPoints((short) 12);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				font.setBold(true);
				style.setFont(font);
 				style.setAlignment(CellStyle.ALIGN_CENTER);
 				style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
 		        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
 			
 				
 				XSSFCellStyle style1 = workbook.createCellStyle();
				XSSFFont font1 = workbook.createFont();
				font1.setFontName(XSSFFont.DEFAULT_FONT_NAME);
				style1.setFont(font1);
 				style1.setAlignment(CellStyle.ALIGN_CENTER);
 			
				
				XSSFRow row;
				 Map < String, Object[] > sealInfo = new TreeMap < String, Object[] >();
				 row = sheet.createRow(0);
				         
				  		
	 				    Cell cell1 = row.createCell(0);
			            cell1.setCellValue("SL NO");
			            Cell cell2 = row.createCell(1);
			            cell2.setCellValue("CIRCLE");
			            Cell cell3 = row.createCell(2);
			            cell3.setCellValue("SUBDIVISION");
			            Cell cell4 = row.createCell(3);
			            cell4.setCellValue("TOTAL METERS");
			            Cell cell5 = row.createCell(4);
			            cell5.setCellValue("DLMS METERS");
			            Cell cell6 = row.createCell(5);
			            cell6.setCellValue("MODEM INSTALLED");
			            Cell cell7 = row.createCell(6);
			            cell7.setCellValue("COMMUNICATING TODAY");
			            Cell cell8 = row.createCell(7);
			            cell8.setCellValue("NON DLMS METERS");
			            Cell cell9 = row.createCell(8);
				  		cell9.setCellValue("UNKNOWN METER TYPE");
				  		
				  		for(int j = 0; j<=8; j++){
				  			row.getCell(j).setCellStyle(style);
				  	        sheet.setColumnWidth(j, 7000); 
				  	        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
				  	    }
				  		
		    	   for (int i = 0; i < list.size(); i++) 
					 {
						 Object[] object=list.get(i);
						 row = sheet.createRow(i+1);
						 for (int j = 0; j < object.length; j++) 
						 {
							 if(j==0)
							 {
								 Cell cell = row.createCell(j);
								 cell.setCellValue(i+1);
								 row.getCell(j).setCellStyle(style1);
							 }else
							 {
								 Cell cell = row.createCell(j);
								 cell.setCellValue(object[j]+"");
								 row.getCell(j).setCellStyle(style1);
							 }
						 }
					 }
		    	   try {
			            FileOutputStream outs = new FileOutputStream(file123);
			            workbook.write(outs);
			            outs.close();
			        } catch (Exception e) {
			          e.printStackTrace();
			        }
			
		    	  
			String to= "chaitra.hk@bcits.in";
			String cc="iswarya.kasukurthy@bcits.in";
			String header="UHBVN AMR Report";
			 String body="<html>"
						+ "<body>"
						+ "<h4>Hi,<br/>PFA </h4>"
						+ "<br/><br/>"
						+ "</body></html>"
						+ "Thanks,<br/>"
						+ "AMR Team <br/> <br/>";
			(new ReportsSMS(to,cc,header,body)).run();
			 
			 file123.delete();
		}
	
}
