package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.AlarmHistory;
import com.bcits.entity.Alarms;
import com.bcits.service.AlarmHistoryService;
import com.bcits.service.AlarmService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Repository
public class AlarmHistoryServiceImpl extends GenericServiceImpl<AlarmHistory>
		implements AlarmHistoryService {
	@Autowired
	private AlarmService alarmservice;

	@Override
	public List<?> viewAlarms() {
		List<?> list = new ArrayList<>();
		String qry ="select a.*,b.subdivision,b.town_ipds from \n" +
				"(select * from meter_data.alarms ORDER BY alarm_date DESC)a,\n" +
				"(select subdivision,sitecode,tp_towncode,town_ipds from meter_data.amilocation )b where a.town_id =b.tp_towncode";
		try {
			list = postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	// @Transactional(propagation=Propagation.SUPPORTS)
	public List<Alarms> acknowledgeAlarms(int id) {

		List<Alarms> l = new ArrayList<>();
		String sql = "select * from meter_data.alarms  where id='" + id + "'";
		final String qry = "select a.office_id,a.location_type, a.location_code,a.location_name from meter_data.alarms a where a.id='"
				+ id + "'";
		try {

			// Query query = postgresMdas.createQuery(qry);
			// list=query.getResultList();

			l = postgresMdas.createNativeQuery(sql, Alarms.class).getResultList();
			// l =(List) find(id);
			// list=(List<?>) customfind(id);
			// for(int i=0;i<l.size();i++){
			// Object[] obj=(Object[]) list.get(i);

			// }
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return l;
	}

	@Override
	public AlarmHistory saveAlarmInHst(List<Alarms> l, String msg, String ack_by) {
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		AlarmHistory al = new AlarmHistory();
		al.setOfficeId(l.get(0).getOfficeId());
		al.setLocatioTtype(l.get(0).getLocatioTtype());
		al.setLocationCode(l.get(0).getLocationCode());
		al.setLocationName(l.get(0).getLocationName());
		al.setAlarmSetting(l.get(0).getAlarmSetting());
		al.setAlarmType(l.get(0).getAlarmType());
		al.setAlarmName(l.get(0).getAlarmName());
		al.setAlarm_priority(l.get(0).getAlarm_priority());
		al.setAlarm_date(l.get(0).getAlarm_date());
		al.setAck_msg(msg);
		al.setAckDate(timestamp);
		al.setAckBy(ack_by);
		al.setTownId(l.get(0).getTownId());
		al.setTime_stamp(timestamp);
		return al;

	}

	@Override
	public void deleteRecord(int id) {
		alarmservice.delete(id);
	}

	@Override
	public List<?> getLocationType() {
		List<?> locationType = new ArrayList<>();
		try {

			locationType = postgresMdas.createNamedQuery("MasterMainEntity.getFdrCategory").getResultList();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return locationType;

	}

	@Override
	public List<?> getAlarmHistory(String circle, String zone, String town, String fromDate,String toDate, String loctype) {

		List<?> alarmHst = new ArrayList<>();
		List<?> sitecode = getAllOfficeCodes(circle,zone,town);
		String officeCodes="";
		for (Object item : sitecode) {
			officeCodes += "'" + item + "',";
		}
		if (officeCodes.endsWith(",")) {
			officeCodes = officeCodes.substring(0, officeCodes.length() - 1);
		}
	/*	 for(int i=0;i<sitecode.size();i++)
		 {
			 if(i<sitecode.size()-1)
			 {
				 officeCodes+="'"+sitecode.get(i)+"',";
			 }
			 else if(i==sitecode.size()-1) 
			 {
				 officeCodes+="'"+sitecode.get(i)+"'";
			 }
		 }*/
		if("LT".equalsIgnoreCase(loctype) || "HT".equalsIgnoreCase(loctype)) {
			loctype="Consumer";
		}
		/*
		 * if(zone.equalsIgnoreCase("ALL")) { zone = "%"; }
		 * if(town.equalsIgnoreCase("ALL")) { town = "%"; }
		 */
		
		/*
		 * String sql = "select a.*,b.subdivision from \n" +
		 * "(select * from meter_data.alarms_hst where office_id in ("+officeCodes+")\n"
		 * + " and to_char(alarm_date,'yyyy-MM-dd') BETWEEN '"+fromDate+"' and '"
		 * +toDate+"' and upper(location_type) like upper('"+loctype+"'))a,\n" + " \n" +
		 * "( select DISTINCT subdivision,sitecode from meter_data.amilocation where sitecode in ("
		 * +officeCodes+"))b\n" + "where cast(a.office_id as NUMERIC)=b.sitecode";
		 */
		
		String sql="select a.*,b.town_ipds from\n" +
		"(select * from meter_data.alarms_hst where\n" +
		"to_char(alarm_date,'yyyy-MM-dd') BETWEEN '"+fromDate+"' and '"+toDate+"' and upper(location_type) like upper('"+loctype+"') and town_id like '"+town+"')a,\n" +
		"( select DISTINCT town_ipds,sitecode from meter_data.amilocation where circle like '"+circle+"' and tp_towncode like '"+town+"' )b\n" +
		"where cast(a.office_id as NUMERIC)=b.sitecode";
	System.out.println("sql--->"+sql);
		
		try {

			alarmHst = postgresMdas.createNativeQuery(sql).getResultList();
			// System.err.println(sql);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return alarmHst;
	}

	@Override
	public List<?> getAllOfficeCodes(String circle, String division,String subdiv) {

		List<?> officeId = new ArrayList<>();
		System.out.println(subdiv);
		String sql = " select sitecode from meter_data.amilocation where subdivision like '"+subdiv+"' and division like '"+division+"' AND circle LIKE '"+circle+"'";
		try {

			officeId = postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return officeId;

	}

	@Override
	public void autoacknowledge(String ack_by) {
		final String message = "Auto Acknowledge";
		Alarms currentList=new Alarms();
		 AlarmHistory alarm_hst=new AlarmHistory();
		//String ack_by=null;
		
		List<Alarms> list=new ArrayList<>();
		String sql = "SELECT * from meter_data.alarms WHERE time_stamp < NOW() - INTERVAL '7 day' ";

		
		try {

			list = postgresMdas.createNativeQuery(sql,Alarms.class).getResultList();
			if(list.size()>0) {
			 Alarms obj=list.get(0);
			
			int[] toBeDeleted = new int[list.size()];
			int[] todel=new int[list.size()];
			//todel=list.parallelStream().
			for(int i=0;i<list.size();i++){
				currentList=list.get(i);
				toBeDeleted[i]=currentList.getId();
			}
			for (int i = 0; i < toBeDeleted.length; i++) {
				//toBeDeleted[i]=Integer.parseInt(toBeDelete[i]);
				list=acknowledgeAlarms(toBeDeleted[i]);
				alarm_hst=saveAlarmInHst(list, message,ack_by);
				save(alarm_hst);
				deleteRecord(toBeDeleted[i]);
				//d5DataService.customSave(alarm_hst);
			}
		} }catch (Exception e) {

			e.printStackTrace();
		}
		


	}

	@Override
	public void getViewAlarmDtlspdf(HttpServletRequest request, HttpServletResponse response) {


		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("View Alarms",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> ViewAlarmsData=null;
			query="select b.subdivision,a.location_type,a.location_code,a.location_name,a.alarm_setting,a.alarm_type,a.alarm_name,a.alarm_priority,a.alarm_date,b.town_ipds from \n" +
					"(select * from meter_data.alarms ORDER BY alarm_date DESC)a,\n" +
					"(select subdivision,sitecode,tp_towncode,town_ipds from meter_data.amilocation )b where a.town_id =b.tp_towncode";
			System.out.println(query);
			ViewAlarmsData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(11);
	           parameterTable.setWidths(new int[]{2,2,2,2,2,2,2,2,2,2,2});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Subdivision",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Type",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Identity",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Setting",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Type",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Priority",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Date",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < ViewAlarmsData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 Date date1=null;
	            	Object[] obj=ViewAlarmsData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		String alarmdate ="" ;
	            		SimpleDateFormat parseFormat = new SimpleDateFormat(
	       		                 "yyyy-MM-dd HH:mm:ss");
	            			
	            			if(obj[8] != null)
	            			{
	       		             date1 = parseFormat.parse(obj[8].toString());
	       		             alarmdate = parseFormat.format(date1); 
	            			}
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:alarmdate+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 

	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=ViewAlarms.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	
		
	
	}
	
	@Override
	public void getViewHistoryAlarmDtlspdf(String zone1,String crcl,String twn,String fromdate, String todate, String loctype1,HttpServletRequest request, HttpServletResponse response,String townname) {

		
		String zone="",circle="",town="",loctype;
		if(zone1=="%")
		{
			zone="ALL";
		}else {
			zone=zone1;
		}
		if(crcl=="%")
		{
			circle="ALL";
		}else {
			circle=crcl;
		}
		if(twn=="%")
		{
			town="ALL";
		}else {
			town=townname;
		}
		if(loctype1=="%"){
			loctype="ALL";
		}else{
			loctype=loctype1;
		}
		Date date2 = null;
		Date date1 = null;
		

		try {
			
			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);		
			document.open();
			
			
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("View Alarms History",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
			/*
			 * PdfPTable header = new PdfPTable(2); header.setWidths(new int[]{1,1});
			 * header.setWidthPercentage(100);
			 * 
			 * header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			 * header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			 * header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			 * 
			 * document.add(header);
			 */
		    
		    PdfPTable header = new PdfPTable(6);
			header.setWidthPercentage(100);
			PdfPCell headerCell = null;
			
			headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(zone, PdfPCell.ALIGN_LEFT));
			
			headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));
			
			headerCell = new PdfPCell(new Phrase("Town :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(town, PdfPCell.ALIGN_LEFT));

			/*
			 * headerCell = new PdfPCell(new Phrase("Division :", new
			 * Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell); // header.addCell(getCell("Division :",
			 * PdfPCell.ALIGN_RIGHT)); header.addCell(getCell(div, PdfPCell.ALIGN_LEFT));
			 * 
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :", new
			 * Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell); // header.addCell(getCell("Division :",
			 * PdfPCell.ALIGN_RIGHT)); header.addCell(getCell(sdcd, PdfPCell.ALIGN_LEFT));
			 */
			
			headerCell = new PdfPCell(new Phrase("Location Type :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			// header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(loctype, PdfPCell.ALIGN_LEFT));
			
			
				headerCell = new PdfPCell(new Phrase("From Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(fromdate, PdfPCell.ALIGN_LEFT));
				
				headerCell = new PdfPCell(new Phrase("To Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(todate, PdfPCell.ALIGN_LEFT));
			

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			// header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			document.add(header);
		    
		    
		    
		    
		    
		    List<?> alarmHst = new ArrayList<>();
		    List<Object []> ViewAlarmsData = null;
			List<?> sitecode = getAllOfficeCodes(circle,zone,town);
			String officeCodes="";
			for (Object item : sitecode) {
				officeCodes += "'" + item + "',";
			}
			if (officeCodes.endsWith(",")) {
				officeCodes = officeCodes.substring(0, officeCodes.length() - 1);
			}
		
			if("LT".equalsIgnoreCase(loctype) || "HT".equalsIgnoreCase(loctype)) {
				loctype="Consumer";
			}
			
			/*String sql = "select a.*,b.subdivision from \n" +
					"(select * from meter_data.alarms_hst where office_id in ("+officeCodes+")\n" +
					" and to_char(alarm_date,'yyyy-MM-dd') BETWEEN '"+fromDate+"' and '"+toDate+"' and upper(location_type) like upper('"+loctype+"'))a,\n" +
					" \n" +
					"( select DISTINCT subdivision,sitecode from meter_data.amilocation where sitecode in ("+officeCodes+"))b\n" +
					"where cast(a.office_id as NUMERIC)=b.sitecode";
			*/
			String sql="select a.*,b.town_ipds from\n" +
			"(select * from meter_data.alarms_hst where\n" +
			"to_char(alarm_date,'yyyy-MM-dd') BETWEEN '"+fromdate+"' and '"+todate+"' and upper(location_type) like upper('"+loctype1+"') and town_id like '"+twn+"')a,\n" +
			"( select DISTINCT town_ipds,sitecode from meter_data.amilocation where circle like '"+crcl+"' and tp_towncode like '"+twn+"' )b\n" +
			"where cast(a.office_id as NUMERIC)=b.sitecode";
			
		    
			ViewAlarmsData=postgresMdas.createNativeQuery(sql).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(13);
	           parameterTable.setWidths(new float[]{(float) 0.5,(float) 1.3,1,1,(float) 4.3,1,(float) 0.7,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Location Type",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Identity",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Setting",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Type",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Priority",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Date",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Ack Msg",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Ack Date",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Alarm Ack By",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < ViewAlarmsData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=ViewAlarmsData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		String alarmdate = "", ackdate = "";
	            		SimpleDateFormat parseFormat = new SimpleDateFormat(
	       		                 "yyyy-MM-dd HH:mm:ss");
	            			
	            			if(obj[9] != null)
	            			{
	       		             date1 = parseFormat.parse(obj[9].toString());
	       		             alarmdate = parseFormat.format(date1); 
	            			}
	            			if(obj[12] != null)
	            			{
	       		             date2 = parseFormat.parse(obj[12].toString());
	       		             ackdate = parseFormat.format(date2);
	            			}
	            			
	            		if(j==0)
	            		{
	            			
	            			
	       		        
	       		        
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[15]==null?null:obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 //obj[9]==null?null:obj[9]+"",+
							 parameterCell = new PdfPCell(new Phrase( alarmdate,new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null: obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( ackdate,new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 

	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=ViewAlarms.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	
		
	
	}

}
