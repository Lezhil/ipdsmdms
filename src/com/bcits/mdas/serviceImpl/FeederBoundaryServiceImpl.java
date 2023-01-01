/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.FeederEntity;
import com.bcits.mdas.service.FeederBoundaryDetailsService;
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

/**
 * @author Tarik
 *
 */
@Service
public class FeederBoundaryServiceImpl extends GenericServiceImpl<FeederEntity> implements FeederBoundaryDetailsService {
	@Override
	public List<?> getfeederdetails(String ssid,String tp_towncode) {
		List<?> list=null;
		try {
			/*
			 * String
			 * sql="select ami.town_rapdrp,ssd.ss_name,fdr.feedername,fdr.tp_fdr_id,fdr.meterno,fdr.manufacturer,fdr.mf,fdr.voltagelevel, from meter_data.feederdetails fdr,meter_data.amilocation ami ,\r\n"
			 * +
			 * "meter_data.substation_details ssd where ssd.parent_town=ami.tp_towncode and\r\n"
			 * + "fdr.officeid=ami.sitecode and fdr.tpparentid='"
			 * +ssid+"' and fdr.deleted is null order by fdr.meterno";ssd.parent_town=ami.tp_towncode
			 */
//			String sql="select ami.town_ipds,ssd.ss_name,fdr.feedername,fdr.tp_fdr_id,fdr.meterno,fdr.manufacturer,fdr.mf,fdr.voltagelevel, "
//					+ "fdr_id,parentid as sscode,tpparentid as tp_sscode,officeid as sdocode,ssd.tp_parent_id as tp_sdocode\n" +
//					"from meter_data.feederdetails fdr,meter_data.amilocation ami , meter_data.substation_details ssd where "
//					+ "ssd.office_id = ami.sitecode and crossfdr='0' and fdr.officeid=ami.sitecode and fdr.tpparentid='"+ssid+"' "
//					+ "AND (fdr.deleted IS NULL OR fdr.deleted =0) order by fdr.meterno";
			

//			String sql="select ami.town_ipds,ssd.ss_name,fdr.feedername,fdr.tp_fdr_id,fdr.meterno,fdr.manufacturer,fdr.mf,fdr.voltagelevel, fdr_id,parentid as sscode,tpparentid as tp_sscode,officeid as sdocode,ssd.tp_parent_id as tp_sdocode from "
//					+ "(select * from meter_data.feederdetails f where f.crossfdr='0' and f.tpparentid='"+ssid+"' AND (f.deleted IS NULL OR f.deleted =0) order by f.meterno) fdr,meter_data.amilocation ami , "
//					+ "meter_data.substation_details ssd where ssd.parent_town=ami.tp_towncode and fdr.tpparentid=ssd.sstp_id and ami.tp_towncode='"+tp_towncode+"'; ";
			
			String sql="SELECT ami.town_ipds, ssd.ss_name, fdr.feedername, fdr.tp_fdr_id, fdr.meterno, fdr.manufacturer, fdr.mf, fdr.voltagelevel, "
					+ "fdr_id, parentid AS sscode, tpparentid AS tp_sscode, officeid AS sdocode, ssd.tp_parent_id AS tp_sdocode "
					+ "FROM( SELECT * FROM meter_data.feederdetails f WHERE f.crossfdr = '0' AND f.tpparentid = '"+ssid+"' AND ( f.deleted IS NULL OR f.deleted = 0) AND f.tp_town_code = '"+tp_towncode+"') fdr, "
					+ "(SELECT tp_towncode, town_ipds FROM meter_data.amilocation GROUP BY tp_towncode, town_ipds) ami, meter_data.substation_details ssd "
					+ "WHERE ssd.parent_town = ami.tp_towncode AND fdr.tpparentid = ssd.sstp_id AND ami.tp_towncode = '"+tp_towncode+"' AND ssd.office_id = fdr.officeid ORDER BY fdr.tp_fdr_id;";

			System.out.println("hi="+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getTownByCircle(String circle,String zone) {
		List<?> list = new ArrayList();
		try {
			String sql ="select distinct town_ipds,tp_towncode from meter_data.amilocation where circle like '"+circle+"' and zone like '"+zone+"'  and town_ipds is not null order by town_ipds";
			//System.out.println(sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<?> getSubStaTionByTown(String towncode,String zone ,String circle) {
		List<?> list = new ArrayList();
		String sql="";
				
		try {
		//	String sql ="select distinct sstp_id,ss_name from meter_data.substation_details where parent_town='"+towncode+"' and ss_name is not null order by ss_name ";
			if (towncode.equalsIgnoreCase("%"))
			{ sql ="select distinct sstp_id,ss_name from meter_data.substation_details where \r\n" + 
					"tp_parent_id in (SELECT DISTINCT tp_subdivcode FROM meter_data.amilocation WHERE tp_towncode like '"+towncode+"' and circle like '"+circle+"' and zone like '"+zone+"')\r\n" + 
					"and  parent_town like '"+towncode+"' and ss_name is not null order by ss_name;";
			}
			else {
			 sql="select distinct sstp_id,ss_name from meter_data.substation_details where \r\n" + 
						"tp_parent_id in (SELECT DISTINCT tp_subdivcode FROM meter_data.amilocation WHERE tp_towncode like '"+towncode+"')\r\n" + 
						"and  parent_town like '"+towncode+"' and ss_name is not null order by ss_name;";
			}
			
		System.out.println(sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//	@Override
//	public List<FeederEntity> getAllDetailsbyId(String ruleId) {
//
//			try {
//				return getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getAllDetailsbyId", FeederEntity.class)
//						.setParameter("v_rule_id", ruleId)
//						.getResultList();
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//	}
	
	@Override
	public List<?> generateBoundaryReportData() {
		List<?> list=null;
		try {
//			String sql="select ami.circle ,ami.town_rapdrp as town, COUNT(boundary_id),\r\n" + 
//					"count(CASE WHEN meter_installed=1 THEN 1 END) as meter_installed,\r\n" + 
//					"count(CASE WHEN meter_installed=0 THEN 1 END) as meter_not_installed\r\n" + 
//					"from meter_data.feederdetails fdr,meter_data.amilocation ami , meter_data.substation_details ssd \r\n" + 
//					"where ssd.parent_town=ami.tp_towncode \r\n" + 
//					"and fdr.officeid=ami.sitecode and fdr.crossfdr='1' and fdr.boundry_feeder=TRUE and fdr.deleted='0'\r\n" + 
//					"GROUP BY circle,town;";
			
//			String sql="SELECT a.circle,a.town,b.count,b.meter_installed,b.meter_not_installed FROM \r\n" + 
//					"(select ami.circle ,ami.town_rapdrp as town,ami.sitecode from meter_data.amilocation ami , \r\n" + 
//					"meter_data.substation_details ssd \r\n" + 
//					"where ssd.parent_town=ami.tp_towncode) a LEFT JOIN \r\n" + 
//					"(select fdr.officeid,COUNT(boundary_id),\r\n" + 
//					"count(CASE WHEN meter_installed=1 THEN 1 END) as meter_installed,\r\n" + 
//					"count(CASE WHEN meter_installed=0 THEN 1 END) as meter_not_installed\r\n" + 
//					"from meter_data.feederdetails fdr \r\n" + 
//					"where  fdr.crossfdr='1' and fdr.boundry_feeder=TRUE and fdr.deleted='0'\r\n" + 
//					"GROUP BY fdr.officeid \r\n" + 
//					") b ON b.officeid=a.sitecode order by a.circle;";
			
			
			String sql="SELECT DISTINCT circle, town_ipds,b.parent_town,\r\n" + 
					"COALESCE(b.total_inst,0) as total_inst ,\r\n" + 
					"COALESCE(b.meter_installed,0) as meter_installed,\r\n" + 
					"COALESCE(b.meter_not_installed,0) as meter_not_installed\r\n" + 
					"FROM meter_data.amilocation l LEFT JOIN\r\n" + 
					"(\r\n" + 
					"SELECT s.parent_town,a.* FROM meter_data.substation_details s,\r\n" + 
					"(\r\n" + 
					"select tp_town_code, COUNT(boundary_id) as total_inst,\r\n" + 
					"count(CASE WHEN meter_installed=1 THEN 1 END) as meter_installed,\r\n" + 
					"count(CASE WHEN meter_installed=0 THEN 1 END) as meter_not_installed\r\n" + 
					"from meter_data.feederdetails fdr\r\n" + 
					"where fdr.crossfdr='1' and fdr.boundry_feeder=TRUE and fdr.deleted='0' \r\n" + 
					"GROUP BY tp_town_code\r\n" + 
					")a WHERE "+ 
					//"s.sstp_id=a.tpparentid\r\n" + 
					"a.tp_town_code=s.parent_town\r\n"+
					")b ON l.tp_towncode=b.parent_town\r\n" + 
					"ORDER BY circle, town_ipds;";
			//System.out.println("hi="+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void getBoundaryDetailsPdf(HttpServletRequest request, HttpServletResponse response, String circle, String town,
			String substation) {
		List<Object[]> BDRData=null;
		String cirname="",townname="",substa="";
		try {
			String qry="select distinct ami.circle,ami.town_ipds,ssd.ss_name from meter_data.feederdetails fdr,meter_data.amilocation ami,meter_data.substation_details ssd \n" +
					"where ssd.parent_town=ami.tp_towncode and fdr.tpparentid = ssd.sstp_id and ami.circle ='"+circle+"' and fdr.tp_town_code='"+town+"' and fdr.tpparentid='"+substation+"'";
			BDRData=postgresMdas.createNativeQuery(qry).getResultList();
			
			if(BDRData.size()>0){
				cirname =(String)BDRData.get(0)[0];
				townname=(String)BDRData.get(0)[1];
				substa=(String)BDRData.get(0)[2];		
			}
			else{
				cirname="";
				townname="";
				substa="";
			}
			
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
		    p1.add(new Phrase("Boundary Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Circle :"+cirname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Sub-Station :"+substa,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
			
		    
		    String query="";
			List<Object[]> BoundaryData1=null;
			query="SELECT ssd.ss_name, fdr.feedername, fdr.tp_fdr_id, fdr.meterno, fdr.manufacturer, fdr.mf,ami.town_ipds, fdr.voltagelevel, fdr_id, parentid AS sscode, tpparentid AS tp_sscode, officeid AS sdocode, ssd.tp_parent_id AS tp_sdocode "
					+ "FROM( SELECT * FROM meter_data.feederdetails f WHERE f.crossfdr = '0' AND f.tpparentid = '"+substation+"' AND ( f.deleted IS NULL OR f.deleted = 0) AND f.tp_town_code = '"+town+"') fdr, (SELECT tp_towncode, town_ipds FROM meter_data.amilocation "
					+ "GROUP BY tp_towncode, town_ipds) ami, meter_data.substation_details ssd WHERE ssd.parent_town = ami.tp_towncode AND fdr.tpparentid = ssd.sstp_id AND ami.tp_towncode = '"+town+"' AND ssd.office_id = fdr.officeid ORDER BY fdr.tp_fdr_id";
			BoundaryData1=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(7);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("SUBSTATION",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FEEDER NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TP FEEDER CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER SL NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER MANUFACTURER",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
			
			
	           for (int i = 0; i < BoundaryData1.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=BoundaryData1.get(i);
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
			        response.setHeader("Content-disposition", "attachment; filename=BoundaryDetails.pdf");
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
	public void getBoundaryPdf(HttpServletRequest request, HttpServletResponse response,String zone, String circle, String town,
			String substation,String townnames,String substationname) {
		
		List<Object[]> BounData=null;
		String zonename="", cirname="",townname="",substa="";
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
			LocalDateTime sysdatetime = LocalDateTime.now();
//			String qry="select distinct ami.circle,ami.town_ipds,ssd.ss_name from meter_data.feederdetails fdr,meter_data.amilocation ami,meter_data.substation_details ssd \n" +
//					"where ssd.parent_town=ami.tp_towncode and fdr.tpparentid = ssd.sstp_id and ami.circle like '"+circle+"' and fdr.tp_town_code like '"+town+"' and fdr.tpparentid like '"+substation+"'";
//			BounData=postgresMdas.createNativeQuery(qry).getResultList();
//			
//			if(BounData.size()>0){
//				cirname =(String)BounData.get(0)[0];
//				townname=(String)BounData.get(0)[1];
//				substa=(String)BounData.get(0)[2];		
//			}
//			else{
//				cirname="";
//				townname="";
//				substa="";
//			}
			
			if (zone.equalsIgnoreCase("%") && circle.equalsIgnoreCase("%") && town.equalsIgnoreCase("%") && substation.equalsIgnoreCase("%")){
				zonename="ALL";
				substa="ALL";
				townname="ALL";
				cirname="ALL";
			}else {
				String qry="select distinct ami.zone, ami.circle,ami.town_ipds,ssd.ss_name from meter_data.feederdetails fdr,meter_data.amilocation ami,meter_data.substation_details ssd \n" +
						"where ssd.parent_town=ami.tp_towncode and fdr.tpparentid = ssd.sstp_id and ami.zone like '"+zone+"' and ami.circle like '"+circle+"' and fdr.tp_town_code like '"+town+"' and fdr.tpparentid like '"+substation+"'";
				BounData=postgresMdas.createNativeQuery(qry).getResultList();
				System.out.println("qry1------+" + qry);
				if(BounData.size()>0){
					cirname =(String)BounData.get(0)[0];
					townname=(String)BounData.get(0)[1];
					substa=(String)BounData.get(0)[2];	
					zonename =(String)BounData.get(0)[3];

				}
			}
			
			if (zone.equalsIgnoreCase("%")){
				zonename="ALL";
			}else {
				zonename=zone;
			}
			
			if (circle.equalsIgnoreCase("%")){
				cirname="ALL";
			}else {
				cirname=circle;
			}
			if (town.equalsIgnoreCase("%")){
				townname="ALL";
			}else {
				townname=townnames;
			}
			if (substation.equalsIgnoreCase("%")){
				substa="ALL";
			}else {
				substa=substationname;
			}

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
		    p1.add(new Phrase("Boundary Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(4);
		    header.setWidths(new int[]{1,1,1,1});
		    header.setWidthPercentage(100);
		    
		    
		    PdfPCell headerCell=null;
		    
		    headerCell = new PdfPCell(new Phrase("Region :"+zonename,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+cirname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Sub-Station :"+substa,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Current Date&Time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
			
		    
		    String query="";
			List<Object[]> Boundarydata1=null;
			query="SELECT f.tp_fdr_id,f.boundary_id,f.boundary_name,f.boundary_location,a.town_ipds,m.fdrname ,m.latitude,m.longitude,f.meterno,manufacturer,f.ct_ratio,f.pt_ratio,f.mf,f.imp_exp from meter_data.feederdetails f, meter_data.master_main m,meter_data.amilocation a where m.location_id=f.tp_fdr_id  and m.town_code=a.tp_towncode  and  tp_town_code like '"+town+"' and tpparentid like '"+substation+"' and crossfdr='1' and boundry_feeder=true and deleted=0 and meterno is not null";
					
			Boundarydata1=postgresMdas.createNativeQuery(query).getResultList();
			
			System.out.println(query);
			
			   PdfPTable parameterTable = new PdfPTable(15);
	           parameterTable.setWidths(new int[]{1,1,1,1,2,2,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("FEEDER ID",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("BOUNDARY ID",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("BOUNDARYNAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("BOUNDARYLOCATION",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Latitude",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Longitude",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           parameterCell = new PdfPCell(new Phrase("METER MAKE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CT",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("PT",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("EXPORT/IMPORT",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
			
			
	           for (int i = 0; i < Boundarydata1.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=Boundarydata1.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=BoundaryDetails.pdf");
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
	public void getRFMReportPDF(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			Rectangle pageSize = new Rectangle(1250, 720);
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
		    p1.add(new Phrase("RFM Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
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
			List<Object[]> RFMReportData=null;
			query="SELECT DISTINCT circle, town_ipds,COALESCE(b.total_inst,0) as total_inst ,COALESCE(b.meter_installed,0) as meter_installed,\n" +
					"COALESCE(b.meter_not_installed,0) as meter_not_installed,b.parent_town FROM meter_data.amilocation l LEFT JOIN\n" +
					"(\n" +
					"SELECT s.parent_town,a.* FROM meter_data.substation_details s,\n" +
					"(\n" +
					"select tp_town_code, COUNT(boundary_id) as total_inst,count(CASE WHEN meter_installed=1 THEN 1 END) as meter_installed,\n" +
					"count(CASE WHEN meter_installed=0 THEN 1 END) as meter_not_installed from meter_data.feederdetails fdr\n" +
					"where fdr.crossfdr='1' and fdr.boundry_feeder=TRUE and fdr.deleted='0' GROUP BY tp_town_code\n" +
					")a WHERE a.tp_town_code=s.parent_town\n" +
					")b ON l.tp_towncode=b.parent_town ORDER BY circle, town_ipds";
			RFMReportData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(6);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           parameterCell = new PdfPCell(new Phrase("Total RFM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Installed",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Not-Installed",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < RFMReportData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=RFMReportData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=RFMReport.pdf");
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
