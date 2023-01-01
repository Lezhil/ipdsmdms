package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.InitialMeterInfo;
import com.bcits.mdas.entity.ValidationProcessReportEntity;
import com.bcits.mdas.service.InitialMeterInfoService;
import com.bcits.serviceImpl.GenericServiceImpl;
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

@Service

public class InitialMeterInfoServiceImpl extends GenericServiceImpl<InitialMeterInfo> implements InitialMeterInfoService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InitialMeterInfo> getInitialMeterInfo()
	{
		try {
		return getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.getInitialMeterInfo").getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<InitialMeterInfo> initialMeterFeederDataSync()
	{
		try {
		return getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.initialMeterFeederDataSync").getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<InitialMeterInfo> initialMeterBoundaryDataSync()
	{
		try {
		return getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.initialMeterBoundaryDataSync").getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getDTAppDetails(String region, String circle,String towncode) {
//		try {
//			return getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.getDTAppDetails").getResultList();
//			}catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
		
		
		try {
			
			String sql="select * from (\r\n" + 
					"select id,regioncode,region,circlecode,circle,towncode,townname,feedercode,dtcode,\r\n" + 
					"meterid,time_stamp,updatedby,updateddate,metertype,wire,currentrating,voltagerating,mf,\r\n" + 
					"lattitude,longitude,ipperiod,meterdigit,\r\n" + 
					"feedername,dtname\r\n" + 
					"from meter_data.initial_meter_info  where sync_status NOT IN('0')  and data_type='MasterInfo' and circle='"+circle+"' and  towncode LIKE '"+towncode+"' and type='DT'\r\n" + 
					"and clientid='2'\r\n" + 
					"GROUP BY id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,\r\n" + 
					"substation,substationcode,townname,towncode,\r\n" + 
					"meterid,metertype,wire,currentrating,voltagerating,mf,\r\n" + 
					"lattitude,longitude,ipperiod,meterdigit,\r\n" + 
					"feedercode,feedername,dtcode,dtname,time_stamp)x ";
			
			System.out.println("Query="+sql);
			return getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getDTNotAppDetails(String region, String circle,String towncode) {
		try {
			String sql="select * from (\n" +
					"select id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,substation,substationcode,townname,towncode,\n" +
					"meterid,metertype,wire,currentrating,voltagerating,mf,\n" +
					"lattitude,longitude,ipperiod,meterdigit,\n" +
					"feedercode,feedername,dtcode,dtname,time_stamp,\n" +
					"ROW_NUMBER () OVER (\n" +
					"      PARTITION BY meterid\n" +
					"      ORDER BY\n" +
					"         time_stamp desc\n" +
					"   ) as rnk\n" +
					"from meter_data.initial_meter_info  where sync_status='0' and data_type='MasterInfo' and circle='"+circle+"' and  towncode LIKE '"+towncode+"' and type='DT'\n" +
					"and clientid='2'\n" +
					"GROUP BY id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,\n" +
					"substation,substationcode,townname,towncode,\n" +
					"meterid,metertype,wire,currentrating,voltagerating,mf,\n" +
					"lattitude,longitude,ipperiod,meterdigit,\n" +
					"feedercode,feedername,dtcode,dtname,time_stamp)x where rnk=1";
			System.out.println("Query="+sql);
			return getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public InitialMeterInfo getDTNotAppDetailByMeter(int id, String meterNo) {  
		try {
//			String sql="select * from (\n" +
//					"select id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,substation,substationcode,townname,towncode,\n" +
//					"meterid,metertype,wire,currentrating,voltagerating,mf,\n" +
//					"lattitude,longitude,ipperiod,meterdigit,\n" +
//					"feedercode,feedername,dtcode,dtname,time_stamp,\n" +
//					"ROW_NUMBER () OVER (\n" +
//					"      PARTITION BY meterid\n" +
//					"      ORDER BY\n" +
//					"         time_stamp desc\n" +
//					"   ) as rnk\n" +
//					"from meter_data.initial_meter_info  where sync_status='0' and data_type='MasterInfo' and type='DT'\n" +
//					"and clientid='2' and \n" +
//					"GROUP BY id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,\n" +
//					"substation,substationcode,townname,towncode,\n" +
//					"meterid,metertype,wire,currentrating,voltagerating,mf,\n" +
//					"lattitude,longitude,ipperiod,meterdigit,\n" +
//					"feedercode,feedername,dtcode,dtname,time_stamp)x where rnk=1";	
			
			return getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.getDTNotAppDetailByMeter", InitialMeterInfo.class)
					.setParameter("id", id)
					.setParameter("meterid",meterNo)
					.getSingleResult();
			
			
//			return (List<InitialMeterInfo>) getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.getDTNotAppDetailByMeter")
//					.setParameter("id", id)
//					.setParameter("meterid",meterNo)
//					.getResultList();
//			return getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int rejectDTMeters(String meterNo, String userName){
		long currtime = System.currentTimeMillis();
		Timestamp updateDate =  new Timestamp(currtime);
		
		String qry = "UPDATE meter_data.initial_meter_info SET sync_status=4,updatedby='"+userName+"',updateddate= '"+updateDate+"' WHERE meterid='"+meterNo+"' and sync_status=0 and type='DT' and data_type='MasterInfo'";
	
		int result = 0 ;
		try {
			result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
			
	}
	
	@Override
	public String checkDTMeterExistOrNot(String meterNo) {

		String result = null;
		try {
			/*String qry = "Select distinct dttpid from meter_data.dtdetails d, meter_data.meter_inventory m \r\n" + 
					"where d.meterno=m.meterno and d.meterno='"+meterNo+"' and m.meter_status='INSTALLED' ";*/
			
			String qry = "select distinct location_id from meter_data.master_main where mtrno='"+meterNo+"'";
			result= (String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	
	}
	
	@Override
	public int duplicateDTMeters(String meterNo, String userName){
		long currtime = System.currentTimeMillis();
		Timestamp updateDate =  new Timestamp(currtime);
		
		//String qry = "UPDATE meter_data.initial_meter_info SET sync_status=2,updatedby='"+userName+"',updateddate= '"+updateDate+"' WHERE meterid='"+meterNo+"' and sync_status=0 and data_type='MasterInfo'";
	
	
		
		int result = 0 ;
		try {
//			result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			
			result= getCustomEntityManager("postgresMdas").createNamedQuery("InitialMeterInfo.duplicateDTMeters")
					.setParameter("updatedby", userName)
					.setParameter("updateddate", updateDate)
					.setParameter("meterid",meterNo)
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
			
	}

	
	

	@Override
	public void getDtMtrAppList(HttpServletRequest request, HttpServletResponse response, String reporttype) {
		try {
			
			Rectangle pageSize = new Rectangle(1750, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("DT Meter List",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(1);
		    header.setWidths(new int[]{1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Select Type :"+reporttype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> Dtappdata=null;
			query="select regioncode,region,circlecode,circle,towncode,townname,feedercode,dtcode,meterid,to_char(time_stamp,'YYYY-MM-DD HH24:MI:ss'),updatedby,to_char(updateddate,'YYYY-MM-DD HH24:MI:ss')as receiveddate from meter_data.initial_meter_info  where sync_status=1 and data_type='MasterInfo' and type='DT'";
					Dtappdata=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(13);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("REGION CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("REGION NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CIRCLE CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CIRCLE NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FEEDER CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("RECEIVED DATE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("APPROVED BY",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("APPROVED DATE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
			
			
	           for (int i = 0; i < Dtappdata.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=Dtappdata.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=DTMeterApproval.pdf");
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
	public void getDtMtrNotappList(HttpServletRequest request, HttpServletResponse response, String reporttype) {
		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("DT Meter List",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(1);
		    header.setWidths(new int[]{1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Select Type :"+reporttype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> Dtnotappdata=null;
			query="select * from (\n" +
					"select regioncode,region,circlecode,circle,towncode,townname,meterid,time_stamp,division,division_code,subdivision,subdivisioncode,section,sectioncode,substation,substationcode,metertype,wire,currentrating,voltagerating,mf,lattitude,longitude,ipperiod,meterdigit,feedercode,feedername,dtcode,dtname,ROW_NUMBER () OVER (PARTITION BY meterid ORDER BY\n" +
					"time_stamp desc) as rnk from meter_data.initial_meter_info  where sync_status='0' and data_type='MasterInfo' and type='DT' and clientid='2'\n" +
					"GROUP BY id,region,regioncode,circle,circlecode,division,division_code,subdivision,subdivisioncode,section,sectioncode,substation,substationcode,townname,towncode,meterid,metertype,wire,currentrating,voltagerating,mf,lattitude,longitude,ipperiod,meterdigit,feedercode,feedername,dtcode,dtname,time_stamp)x where rnk=1";
					Dtnotappdata=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(9);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("REGION CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("REGION NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CIRCLE CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CIRCLE NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("RECEIVED DATE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
			
			
	           for (int i = 0; i < Dtnotappdata.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=Dtnotappdata.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=ToBeApprovedDt.pdf");
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
	public void getPreiodCommSumm(HttpServletRequest request, HttpServletResponse response, String zone, String circle, String town, String fromdate, String todate, String townnames) {
		
		String znee="",crcll="",divv="",subdivi="",townn="";

		try {
		if(zone.equalsIgnoreCase("%"))
		{
			znee="ALL";
		}else {
			znee=zone;
		}
		if(circle.equalsIgnoreCase("%"))
		{
			crcll="ALL";
		}else {
			crcll=circle;
		}
		
		if(town.equalsIgnoreCase("%"))
		{
			townn="ALL";
		}else {
			townn=town;
		}
		//System.out.println(townn);
		Rectangle pageSize = new Rectangle(1050, 720);
		Document document = new Document(pageSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
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
	    p1.add(new Phrase("Meter Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p1.setAlignment(Element.ALIGN_CENTER);
	    cell2.addElement(p1);
	    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
	    pdf1.addCell(cell2);
	    document.add(pdf1);
	    
	    PdfPTable header = new PdfPTable(5);
	    header.setWidths(new int[]{1,1,1,1,1});
	    header.setWidthPercentage(100);
	    
	    PdfPCell headerCell=null;
	    headerCell = new PdfPCell(new Phrase("Region :"+znee,new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);		    
	    
	    headerCell = new PdfPCell(new Phrase("Circle :"+crcll,new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);		    		    
	    
	    headerCell = new PdfPCell(new Phrase("Town Name :"+townnames,new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("FromDate :"+fromdate,new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("ToDate :"+todate,new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    
	    document.add(header);
		
	    String query="";
		List<Object[]> PeriodCommSummData1=null;
		query="select date,total_meter,communicating,non_communicating,total_dt_meter,dt_communicating,dt_non_communicating,total_feeder_meter,fm_commuicating,fm_non_communicating,\r\n" + 
				"total_boundary_meter,bm_communicating,bm_non_communicating,circle,town from meter_data.period_wise_data  where zone like '"+zone+"' and circle like '"+circle+"' and town_code like'"+town+"' and date >='"+fromdate+"' and date <= '"+todate+"' " ;
				PeriodCommSummData1=postgresMdas.createNativeQuery(query).getResultList();
		//System.out.println("1111==="+query);
				
		   PdfPTable parameterTable = new PdfPTable(16);
           parameterTable.setWidths(new int[]{1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1});
           parameterTable.setWidthPercentage(100);
           PdfPCell parameterCell;
           
           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
	    
           parameterCell = new PdfPCell(new Phrase("DATE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("CIRCLE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("TOWN",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("TOTAL METERS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("COMM METERS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("NON COMM MTRS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("DT METERS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("DT COMM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("DT NON-COMM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("FEEDER MTRS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("FM COMM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("FM NON-COMM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("BOUNDARY MTRS",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("BOUNDARY COMM ",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("BOUNDARY NON-COMM ",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(25f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           
           
           for (int i = 0; i < PeriodCommSummData1.size(); i++) 
            {
        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
					 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					 parameterTable.addCell(parameterCell);
					
					 
            	Object[] obj=PeriodCommSummData1.get(i);
            	for (int j = 0; j < obj.length; j++) 
            	{
            		if(j==0)
            		{
            			String value1=obj[0]+"";
            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
						 
						 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
						 
						 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
    response.setHeader("Content-disposition", "attachment; filename=PeriodwiseCommunicationSummary.pdf");
	response.setContentType("application/pdf");
	ServletOutputStream outstream = response.getOutputStream();
	baos.writeTo(outstream);
	outstream.flush();
	outstream.close();
		
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
