package com.bcits.mdas.ftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemLifeCycleEntity;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemLifeCycleService;
import com.bcits.mdas.service.XmlUploadStatusService;
import com.bcits.mdas.utility.SendModemAlertViaMail;

public class Scheduler {

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
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private ModemLifeCycleService lifeCycleService;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	public void createAndUploadXMLScheduler() {
		System.out.println("SCHEDULER STARTED ===============================");
		CreateXmlAndUpload xmlUpload = new CreateXmlAndUpload(amrInstantaneousService, amrBillsService, amrEventsService, amrLoadService, feederMasterService, xmlUplaodService);
		xmlUpload.createAndUploadXML(dateFormat.format(yesterday()));
	}
	
	public void createAllXMLScheduler() {
		System.out.println("SCHEDULER STARTED ===============================");
		CreateXmlAndUpload xmlUpload = new CreateXmlAndUpload(amrInstantaneousService, amrBillsService, amrEventsService, amrLoadService, feederMasterService, xmlUplaodService);
		xmlUpload.createAllXML(dateFormat.format(yesterday()));
	}

	public static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	public void updateMasterForAllChangesMeterNo() {
		System.out.println("SCHEDULER STARTED ===============================");
		//int count=masterMainService.updateMasterForAllChangesMeterNo();
		String meterChangeQry="SELECT CC.fdrname,CC.mtrno,CC.circle,CC.division,CC.subdivision,CC.substation, DD.*,case when CC.mtrno=DD.meter_number then 'SAME' else 'METER CHANGED' END as status,CC.mtrmake,CC.dlms FROM (SELECT * from meter_data.master_main)CC JOIN(SELECT AA.imei,AA.maxDate,BB.meter_number  from (select imei,max(date) as maxDate from meter_data.modem_communication GROUP BY imei) AA LEFT JOIN meter_data.modem_communication BB ON AA.maxDate= BB.date AND AA.imei=BB.imei)DD ON CC.modem_sl_no=DD.imei AND CC.mtrno != DD.meter_number ORDER BY CC.circle";
		List<?> lis=null;
		try {
			lis=masterMainService.executeSelectQueryRrnList(meterChangeQry);
		}catch (Exception e) {
			e.getMessage();
		}
		int i=0;
		if(lis!=null) {
			for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();)
			{
				Object[] values = (Object[]) iterator.next();
				MasterMainEntity mainEntity=masterMainService.getEntityByImeiNoAndMtrNO(((String)values[6]).trim(),((String)values[1]).trim());
				
				mainEntity.setMtr_change_date(new SimpleDateFormat("yyyy-MM-DD").format((Date)values[7]));
				mainEntity.setOld_mtr_no((String)values[1]);
				mainEntity.setMtrno((String)values[8]);
				try {
					masterMainService.update(mainEntity);
					i++;
				}catch (Exception e) {
					e.getMessage();
				}
				
				try {
					ModemLifeCycleEntity cycleEntity=new ModemLifeCycleEntity();
					cycleEntity.setImei(mainEntity.getModem_sl_no());
					cycleEntity.setLocation(mainEntity.getZone()+">"+mainEntity.getCircle()+">"+mainEntity.getDivision()+">"+mainEntity.getSubdivision()+">"+mainEntity.getFdrname());
					cycleEntity.setEvents("Meter : "+(String)values[1]+" changed to : "+(String)values[8]);
					cycleEntity.setEdate(new Date());
					lifeCycleService.save(cycleEntity);
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		}
		
		
		System.out.println("Total Master Meter No updated : "+i);
	}
	
	public void generateFailedXml() {
		CreateXmlAndUpload xml=new CreateXmlAndUpload();
		xml.generateFailedXmlCron(xmlUplaodService);
	}
	
	
	public void reUploadFailedXml() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date d1=c.getTime();
		String filedate=new SimpleDateFormat("yyyy-MM-dd").format(d1);
		
		CreateXmlAndUpload xml=new CreateXmlAndUpload();
		xml.reUploadFailedxmlCron(filedate, xmlUplaodService);
	}
	
	//@Transactional
	public void updateImeiLastComm() {
		try {
			masterMainService.updateImeiLastComm();
		} catch (Exception e) { e.printStackTrace();}
		try {
			masterMainService.updateInstallationDate();
		} catch (Exception e) { e.printStackTrace();}
		
	}
	
	public void sendMails() {
		try {
			sendMailforInstallationsStat();
		}catch (Exception e) { e.printStackTrace();}
		try {
			sendMailforInstalledNotInMaster();
		}catch (Exception e) { e.printStackTrace();}
		try {
			sendMailInstalledNotCommToday();
		}catch (Exception e) { e.printStackTrace();}
	}
	
	
	public void sendMailforInstalledDetails() {
		
		String newbody="";
		String sendermailid = null;
		String sendermailpassword = null;
		
		String sql="SELECT mtrno, ZONE, circle, division, subdivision, substation, fdrname, modem_sl_no, last_communicated_date , accno, customer_name\n" +
				"FROM meter_data.master_main WHERE to_char(installation_date,'YYYY-MM-DD')=to_char((CURRENT_DATE-1),'YYYY-MM-DD');";
		
		List<?> li=masterMainService.executeSelectQueryRrnList(sql);
		 String body=" <span>Dear Sir,</span><br><br>\r\n" ; 
		    
		 	body+=newbody;	
		 	
	     	body+=	"<span>Below is the list of modems which Installed yesterday ("+new SimpleDateFormat("yyyy-MM-dd").format(yesterday())+").</span><br><br>\r\n" ;
	     
		 if(li.size()!=0) {
			 body+=	"<div style='overflow: scroll;'> <table style='font-size: 9pt;'>\r\n" + 
			     		" <tr>\r\n" + 
			     		" <th>Sl No</th>\r\n" + 
			     		" <th>Meter No</th>\r\n" + 
			     		"  <th>Zone</th>\r\n" + 
			     		"  <th>Circle</th>\r\n" + 
			     		"  <th>Division</th>\r\n" + 
			     		"  <th>Subdivision</th>\r\n" + 
			     		"  <th>Substation</th>\r\n" + 
			     		"  <th>Acc No</th>\r\n" + 
			     		"  <th>Consumer Name</th>\r\n" + 
			     		"  <th>Modem Sl.No </th>\r\n" +
			     		"  <th>Last Communicated Date</th>\r\n" +
			     		" </tr>\r\n" ; 
			     		
			     	int i=1;
			     	for(Object list:li){
			     		Object[] obj=(Object[]) list;
			     		body+=" <tr>\r\n" + 
							" <td>"+i+"</td>\r\n" + 
				     		" <td>"+obj[0]+"</td>\r\n" + 
				     		"  <td>"+obj[1]+"</td>\r\n" + 
				     		"  <td>"+obj[2]+"</td>\r\n" + 
				     		"  <td>"+obj[3]+"</td>\r\n" + 
				     		"  <td>"+obj[4]+"</td>\r\n" + 
				     		"  <td>"+obj[5]+"</td>\r\n" + 
				     		"  <td>"+obj[9]+"</td>\r\n" + 
				     		"  <td>"+obj[10]+"</td>\r\n" + 
				     		"  <td>"+obj[7]+"</td>\r\n" + 
				     		"  <td>"+(obj[8]==null?"":obj[8])+"</td>\r\n" +
				     		" </tr>\r\n";
						i++;
			     	}
			     	
			     	body+=" </table></div>\r\n";
			     	
			     	body+="<style> table { border-collapse: collapse; } table, th, td { border: 1px solid black;padding: 0px; } </style>";
			     	
		 } else {
			 body+="No Modem Installed Yesterday.";
		 }
	     
		// body+=newbody;
		 
		 body+="<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
	     			+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";
		
		// String to="sowjanya.medisetti@bcits.in";
		//	String cc="sowjanya.medisetti@bcits.in";
		String to="salehin.anjum@bcits.in";
		String cc="pmblr-01.tech@bcits.co.in";
		String subject="AMI-Notification: Meter Installed Yesterday";
		
		
		
		
		//System.out.println(mail_to+"  <<<<<<<>>>>>>>  "+mail_cc);
		new Thread(new SendModemAlertViaMail(subject,body,to,cc,sendermailid,sendermailpassword)).run();
		
	}
	
	public void sendMailforInstallationsStat() {
		String newbody="";

		String qry="SELECT surveydate,count(a.newmeterno) , count(b.meter_number) as notcom FROM	\n" +
				"(select newmeterno,date(survey_timings) as surveydate from meter_data.survey_output)A\n" +
				"LEFT JOIN(\n" +
				"SELECT mtrno as meter_number FROM meter_data.master_main WHERE mtrno not in \n" +
				"(SELECT distinct meter_number FROM meter_data.modem_communication WHERE date=(CURRENT_DATE-1))\n" +
				")B ON A.newmeterno=B.meter_number GROUP BY a.surveydate ORDER BY surveydate desc;";
		List<?> li=masterMainService.executeSelectQueryRrnList(qry);
		String body=" <span>Dear Sir,</span><br><br>\r\n" ; 
		body+=newbody;
		List<Object[]> listForxl=new ArrayList<>();
		body+=	"<span>Below is the Meter Installation Report </span><br><br>\r\n" ;
		
		if(li.size()!=0) {
			 body+=	"<div> <table style='font-size: 9pt;'>\r\n" + 
			     		" <tr>\r\n" + 
			     		" <th style='text-align: center;'>Sl No</th>\r\n" + 
			     		" <th style='min-width: 100px;text-align: center;'>Date</th>\r\n" + 
			     		"  <th style='min-width: 100px;text-align: center;'>Installed</th>\r\n" + 
			     		"  <th style='min-width: 100px;text-align: center;'>Communicated Today</th>\r\n" + 
			     		"  <th style='min-width: 100px;text-align: center;'>Not Communicated Today</th>\r\n" + 
			     		" </tr>\r\n" ; 
			     		
			     	int i=1;
			     	int total=0;
			     	int totalcomm=0;
			     	int totalnoncom=0;
			     	
			     	for(Object list:li){
			     		Object[] obj=(Object[]) list;
			     		Integer comm=Integer.parseInt(String.valueOf(obj[1]))-Integer.parseInt(String.valueOf(obj[2]));
			     		total+=Integer.parseInt(String.valueOf(obj[1]));
			     		totalnoncom+=Integer.parseInt(String.valueOf(obj[2]));
			     		totalcomm+=comm;
			     		Object[] ojb1= {String.valueOf(obj[0]),total,totalcomm,totalnoncom};
			     		listForxl.add(ojb1);
			     		body+=" <tr>\r\n" + 
							" <td style='text-align: center;'>"+i+"</td>\r\n" + 
				     		" <td style='text-align: center;'>"+obj[0]+"</td>\r\n" + 
				     		"  <td style='text-align: center;'>"+obj[1]+"</td>\r\n" + 
				     		"  <td style='text-align: center;'>"+comm+"</td>\r\n" + 
				     		"  <td style='text-align: center;'>"+obj[2]+"</td>\r\n" + 
				     		" </tr>\r\n";
						i++;
			     	}
			     	body+="<tr>"
			     		+ "<td colspan=2 style='font-weight: bold;'>Total</td>"
			     		+ "<td>"+total+"</td>"
			     		+ "<td>"+totalcomm+"</td>"
			     		+ "<td>"+totalnoncom+"</td>"
			     		+ "</tr>";
			    
			     	
			  body+=" </table></div>\r\n";
			     	
			  body+="<style> table { border-collapse: collapse; } table, th, td { border: 1px solid black;padding: 4px; } </style>";
			     	
		 } else {
			 body+="No Meter Installed.";
		 }
		
		body+="<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
     			+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";
	
		String to="ceo@bcits.co.in,md@bcits.co.in,cto@bcits.co.in,rajesh@bcits.co.in";
		//String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in";
		
		//String to="sowjanya.medisetti@bcits.in";
		String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in,sowjanya.medisetti@bcits.in";
		
		/*String to="salehin.anjum@bcits.in";*/
		String subject="AMI-Notifcation: Modem Installation Report";
		
		String[] heading= {"SL No","Date","Installed","Communicated Today","Not Communicated Today"};
		String file=createExcelFile(listForxl,heading,"INSTALLATION_REPORT");
		new Thread(new SendModemAlertViaMail(subject,body,to,cc,file)).run();
	}
	
	
	public void sendMailforInstalledNotInMaster() {
		
		String newbody="";
		
		String sql="select distinct COALESCE(meter_number,'0') as mtrno,(SELECT MC.last_communication FROM meter_data.modem_communication MC WHERE COALESCE(MC.meter_number,'0')= COALESCE(meter_number,'0') ORDER BY MC.last_communication limit 1 ) from meter_data.modem_communication\n" +
				"where meter_number not in (select COALESCE(mtrno,'0') from\n" +
				"meter_data.master_main) GROUP BY COALESCE(meter_number,'0')";;
		
		List<?> li=masterMainService.executeSelectQueryRrnList(sql);
		 String body=" <span>Dear Sir,</span><br><br>\r\n" ; 
		    
		 	body+=newbody;	
		 	
	     	body+=	"<span>Below is the list of meters which Installed but Not in Master.</span><br><br>\r\n" ;
	     
		 if(li.size()!=0) {
			 body+=	"<div> <table style='font-size: 9pt;'>\r\n" + 
			     		" <tr>\r\n" + 
			     		" <th>Sl No</th>\r\n" + 
			     		" <th>Meter No</th>\r\n" + 
			     		"  <th>Last Communicated Date</th>\r\n" +
			     		" </tr>\r\n" ; 
			     		
			     	int i=1;
			     	for(Object list:li){
			     		Object[] obj=(Object[]) list;
			     		body+=" <tr>\r\n" + 
							" <td>"+i+"</td>\r\n" + 
				     		" <td>"+obj[0]+"</td>\r\n" + 
				     		"  <td>"+(obj[1]==null?"":obj[1])+"</td>\r\n" +
				     		" </tr>\r\n";
						i++;
			     	}
			     	
			     	body+=" </table></div>\r\n";
			     	
			     	body+="<style> table { border-collapse: collapse; } table, th, td { border: 1px solid black;padding: 4px; } </style>";
			     	
		 } else {
			 body+="";
		 }
	     
		// body+=newbody;
		 
		 body+="<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
	     			+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";
		
		//String to="sowjanya.medisetti@bcits.in";
		String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in,sowjanya.medisetti@bcits.in";
		String to="ceo@bcits.co.in,md@bcits.co.in,cto@bcits.co.in,rajesh@bcits.co.in";
		//String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in";
		
		/*String to="salehin.anjum@bcits.in";*/
		
		String subject="AMI-NOTIFICATION: Meter Installed But Not In Master";
		
		String[] heading= {"SL No","Meter No","Last Communicated Date"};
		
		String file=createExcelFile(li,heading,"NOT_IN_MASTER_REPORT");
		//System.out.println(mail_to+"  <<<<<<<>>>>>>>  "+mail_cc);
		new Thread(new SendModemAlertViaMail(subject,body,to,cc,file)).run();
		
	}
	
	public void sendMailInstalledNotCommToday() {
		
		String newbody="";
		
		//String sql="SELECT ZONE, circle, division, subdivision, substation, accno, customer_name, mtrno, modem_sl_no, last_communicated_date FROM meter_data.master_main WHERE mtrno in(SELECT mtrno FROM meter_data.master_main a,(SELECT DISTINCT meter_number, imei from meter_data.modem_communication GROUP BY meter_number, imei)b WHERE a.mtrno=b.meter_number AND a.modem_sl_no=b.imei ) AND mtrno not in ( SELECT mtrno FROM meter_data.master_main a, ( SELECT DISTINCT meter_number, imei from meter_data.modem_communication WHERE date=CURRENT_DATE GROUP BY meter_number, imei )b WHERE a.mtrno=b.meter_number AND a.modem_sl_no=b.imei )";
		
		String sql="SELECT ZONE, circle, division, subdivision, accno, customer_name, mtrno,  last_communicated_date,  \n" +
				"				 (SELECT   \n" +
				"				 (CASE WHEN event_code='101'  THEN 'POWER OUTAGE'  \n" +
				"				 WHEN event_code='102' THEN 'POWER RESTORE'   \n" +
				"				  \n" +
				"				 END) as diagdetails  \n" +
				"				 FROM meter_data.events WHERE meter_number=mtrno   \n" +
				"				 AND CAST(event_time as VARCHAR)||id=(SELECT CAST(max(event_time) as VARCHAR)||max(id) FROM meter_data.events WHERE meter_number=mtrno))  \n" +
				"				 FROM meter_data.master_main WHERE   mtrno NOT IN(  \n" +
				"				  SELECT M .meter_number FROM meter_data.modem_communication M   \n" +
				"				 WHERE DATE >= (CURRENT_DATE - 1) AND M .meter_number IS NOT NULL  \n" +
				"				 ) AND last_communicated_date is not null  ORDER BY ZONE, circle, division, subdivision, fdrname;";
		
		List<?> li=masterMainService.executeSelectQueryRrnList(sql);
		 String body=" <span>Dear Sir,</span><br><br>\r\n" ; 
		    
		 	body+=newbody;	
		 	
	     	body+=	"<span>Below is the list of meters which is not Communicating Yesterday.</span><br><br>\r\n" ;
	     
		 if(li.size()!=0) {
			 body+=	"<div style='overflow: scroll;'> <table style='font-size: 9pt;'>\r\n" + 
			     		" <tr>\r\n" + 
			     		" <th>Sl No</th>\r\n" + 
			     		"  <th>Zone</th>\r\n" + 
			     		"  <th>Circle</th>\r\n" + 
			     		"  <th>Division</th>\r\n" + 
			     		"  <th>Subdivision</th>\r\n" + 
			     		"  <th>Acc No</th>\r\n" + 
			     		"  <th>Consumer Name</th>\r\n" + 
			     		" <th>Meter No</th>\r\n" + 
			     		"  <th>Last Communicated Date</th>\r\n" +
			     		"  <th>Diagnostic Details</th>\r\n" +
			     		" </tr>\r\n" ; 
			     		
			     	int i=1;
			     	for(Object list:li){
			     		Object[] obj=(Object[]) list;
			     		
			     		body+=" <tr>\r\n" + 
							" <td>"+i+"</td>\r\n" + 
				     		" <td>"+obj[0]+"</td>\r\n" + 
				     		"  <td>"+obj[1]+"</td>\r\n" + 
				     		"  <td>"+obj[2]+"</td>\r\n" + 
				     		"  <td>"+obj[3]+"</td>\r\n" + 
				     		"  <td>"+obj[4]+"</td>\r\n" + 
				     		"  <td>"+obj[5]+"</td>\r\n" + 
				     		"  <td>"+(obj[6]==null?"":obj[6])+"</td>\r\n" + 
				     		"  <td>"+(obj[7]==null?"":obj[7])+"</td>\r\n" +
				     		"  <td>"+(obj[8]==null?"":obj[8])+"</td>\r\n" +
				     		" </tr>\r\n";
						i++;
			     	}
			     	
			     	body+=" </table></div>\r\n";
			     	
			     	body+="<style> table { border-collapse: collapse; } table, th, td { border: 1px solid black;padding: 0px; } </style>";
			     	
		 } else {
			 body+="All Meters Communicating Yesterday.";
		 }
	     
		// body+=newbody;
		 
		 body+="<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
	     			+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";
		
		//String to="sowjanya.medisetti@bcits.in";
		String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in,sowjanya.medisetti@bcits.in";
		String to="ceo@bcits.co.in,md@bcits.co.in,cto@bcits.co.in,rajesh@bcits.co.in";
		// String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in";
		
		 //String to="salehin.anjum@bcits.in";
		 
		 String subject="AMI-Notification: Meter Not Communicating Yesterday";
		
		
		String[] heading= {"SL No","Zone","Circle","Division","Subdivision","Acc No","Consumer Name","Meter No","Last Communicated Date","Diagnostic Details"};
		
		String file=createExcelFile(li,heading,"NOT_COMM_REPORT");
		
		//System.out.println(mail_to+"  <<<<<<<>>>>>>>  "+mail_cc);
		new Thread(new SendModemAlertViaMail(subject,body,to,cc,file)).run();
		
		
		
	}
	
	public String createExcelFile(List<?> list, String[] headings,String filename) {
		
		XSSFWorkbook xlsFile = new XSSFWorkbook();
		CreationHelper helper = xlsFile.getCreationHelper();
		Sheet sheet1 = xlsFile.createSheet("Sheet 1");
		
		XSSFCellStyle style = xlsFile.createCellStyle();
		style.setBorderTop((short) 6); // double lines border
		style.setBorderBottom((short) 1); // single line border
		style.setBorderLeft((short)1);
		style.setBorderRight((short)1);
		
		XSSFFont font = xlsFile.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);     
		
		XSSFCellStyle defaultstyle = xlsFile.createCellStyle();
		defaultstyle.setBorderTop((short) 1); // double lines border
		defaultstyle.setBorderBottom((short) 1); // single line border
		defaultstyle.setBorderLeft((short)1);
		defaultstyle.setBorderRight((short)1);
		
		
		Row headerRow  = sheet1.createRow(0);
		for (int i = 0; i < headings.length; i++) {
			 headerRow .createCell(i).setCellValue( String.valueOf(headings[i]));
			 headerRow.getCell(i).setCellStyle(style);
			 sheet1.autoSizeColumn(i);
		}   
		
		int r=1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object [] obj = (Object[]) iterator.next();
			Row row = sheet1.createRow(r++);
			for (int i = 0; i < obj.length; i++) {
				
				row.createCell(0).setCellValue(r-1);
				row.getCell(0).setCellStyle(defaultstyle);
				row.createCell(i+1).setCellValue(
					     helper.createRichTextString(String.valueOf(obj[i]))
					);
				row.getCell(i+1).setCellStyle(defaultstyle);
				sheet1.autoSizeColumn(i+1);
			}
			
			
			
		}
		String file=filename+".xlsx";
		try {
			FileOutputStream fos = new FileOutputStream(file);
			xlsFile.write(fos);
			fos.close();
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
