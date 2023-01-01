package com.bcits.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.EventMaster;
import com.bcits.service.D5DataService;
import com.bcits.service.EventmasterService;
import com.bcits.utility.MDMLogger;
import com.ibm.icu.text.SimpleDateFormat;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class EventMgmntController
{
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Autowired
	private EventmasterService eventmasterService;
	
	@Autowired
	private D5DataService d5DataService;
	
	
	@RequestMapping(value="/eventManagement",method={RequestMethod.POST,RequestMethod.GET})
	public String eventManagement(HttpServletRequest request,ModelMap model)
	{
		List<EventMaster> list = eventmasterService.findAll();
		List<EventMaster> catlist = eventmasterService.getEventCategory();
		model.put("events", list);
		model.put("catlist", catlist);
		return "eventManagement";
	}
	@RequestMapping(value="/eventsList",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object eventList(HttpServletRequest request,ModelMap model)
	{
		List<EventMaster> list = eventmasterService.findAll();
		return list;
	}
	
	
	@RequestMapping(value="/geteventDesc/{code}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String geteventDesc(@PathVariable String code, HttpServletRequest request,ModelMap model)
	{
		List<EventMaster> list = eventmasterService.getEventDescription(code);
		model.put("events", list);
		return "eventManagement";
	}
	
	@RequestMapping(value="/eventManagementDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String eventManagementDetails(@RequestParam("eventFromDate") String eventFromDate,@RequestParam("eventToDate") String eventToDate,@RequestParam("event") int eventCode,@RequestParam("category") String  eventcat,HttpServletRequest request,ModelMap model)
	{
		//System.out.println("///////////////////////// : "+eventFromDate+ " : "+eventToDate+ " : "+eventCode);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
		List d5data = d5DataService.getEventDetails(eventCode, eventFromDate, eventToDate,eventcat);
		model.put("eventDetails", d5data);
		if(d5data.size() > 0)
		{
			model.put("eventLength", "eventLength");
		}
		else{
			model.put("eventLength1", "eventLength1");
		}
		List<EventMaster> list = eventmasterService.findAll();
		List<EventMaster> catlist = eventmasterService.getEventCategory();
		model.put("events", list);
		model.put("eventFromDate", eventFromDate);
		model.put("eventToDate", eventToDate);
		model.put("catlist", catlist);
		if(eventCode==0){
			model.put("event_name", "EVENT CATEGORY :"+ eventcat.toUpperCase());
			
		}
		else{
			String s=entityManager.createNativeQuery("select event from meter_data.event_master where event_code='"+eventCode+"'").getSingleResult().toString();
			model.put("event_name", "EVENT NAME :"+s.toUpperCase());
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return "eventManagement";
	}
	@RequestMapping(value="/eventSummary/{eventDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object eventSummary(@PathVariable String eventDate){
		List d5data = d5DataService.eventSummary(eventDate);
		return d5data;
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tamperReportPdf/{meterno}/{month}", method ={ RequestMethod.POST,RequestMethod.GET})
	public String tamperReportPdf(@PathVariable String meterno,@PathVariable String month,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
				List<Object[]> data=null;
				List<Object[]> dataHeader=null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
			try
			{
				
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
                Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
                Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
                PdfPTable pdf1 = new PdfPTable(1);
                pdf1.setWidthPercentage(100); // percentage
                pdf1.getDefaultCell().setPadding(3);
                pdf1.getDefaultCell().setBorderWidth(0);
                pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
              
                PdfPTable pdf2 = new PdfPTable(1);
                pdf2.setWidthPercentage(100); // percentage
                pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                
                PdfPCell cell1 = new PdfPCell();
                Paragraph pstart = new Paragraph();
                pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.addElement(pstart);
                pdf2.addCell(cell1);
                document.add(pdf2);
                
                PdfPCell cell2 = new PdfPCell();
                Paragraph p1 = new Paragraph();
                p1.add(new Phrase("Tamper Report For Meter : "+meterno,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
                p1.setAlignment(Element.ALIGN_CENTER);
                cell2.addElement(p1);
                cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
                pdf1.addCell(cell2);
                document.add(pdf1);
              
                String queryHead="select c.billmonth,to_char(MM.READINGDATE,'dd-MM-yyyy HH:MI:SS'),m.name,m.address1,D1.METER_TYPE,"
                				+ "D1.MANUFACTURER_NAME,MM.MF from mdm_test.d5_data d5,mdm_test.master m, mdm_test.metermaster mm, "
                				+ "mdm_test.cdf_data c,mdm_test.event_master e,mdm_test.D1_DATA d1 where c.cdf_id= d5.cdf_id and "
                				+ "mm.accno=m.accno and c.accno=m.accno and e.event_code=d5.event_code and "
                				+ "D5.CDF_ID=D1.CDF_ID and c.accno=mm.accno and MM.METRNO='"+meterno+"' and "
        						+ "m.consumerstatus like 'R' and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and c.BILLMONTH='"+month+"' and mm.rdngmonth='"+month+"'  "
        						+ "order by MM.METRNO,D5.EVENT_CODE ASC";
                
                MDMLogger.logger.info("Tamper queryHead====>>"+queryHead);
                dataHeader=entityManager.createNativeQuery(queryHead).getResultList();
                MDMLogger.logger.info("Tamper Query queryHead SIZE====>>"+dataHeader.size());
                if(dataHeader.size()==0)
                { 
                	model.put("results", "No data available.");
                	return "360degreeview";
                }
                String mtrDate="",conName="",conAddress="",mtrType="",mfrName="";
                BigDecimal rdMonth=null,mf=null;
                
                for (int x = 0; x < dataHeader.size(); x++) 
	  	    	 {
	  	    		Object[] str1=(Object[]) dataHeader.get(x);
	  	    		for (int y = 0; y < str1.length; y++) 
	  	    		{
	  	    				if(y==0)
	  	    				{
	  	    					rdMonth=(BigDecimal) str1[y];
	  	    				}
	  	    				if(y==1)
	  	    				{
	  	    					mtrDate= (String)str1[y];
	  	    				}
	  	    				if(y==2)
	  	    				{
	  	    					conName=(String) str1[y];
	  	    				}
	  	    				if(y==3)
	  	    				{
	  	    					conAddress=(String) str1[y];
	  	    				}
	  	    				if(y==4)
	  	    				{
	  	    					mtrType=(String) str1[y];
	  	    				}
	  	    				if(y==5)
	  	    				{
	  	    					mfrName=(String) str1[y];
	  	    				}
	  	    				if(y==6)
	  	    				{
	  	    					mf=(BigDecimal) str1[y];
	  	    				}
	  	    		}
	  	    		break;
	  	    	 }
	  	    		
                PdfPTable header = new PdfPTable(4);
	                 header.setWidthPercentage(100);
	                 header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell("", PdfPCell.ALIGN_LEFT));

	                 header.addCell(getCell("Reading Month : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(rdMonth.toString(), PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("METER RTC : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(mtrDate, PdfPCell.ALIGN_LEFT));
	                 	                 
	                 header.addCell(getCell("Consumer Name : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(conName, PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("Meter Serail Number : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(meterno, PdfPCell.ALIGN_LEFT));
	                
	                 header.addCell(getCell("Address : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(conAddress, PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("MF : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(mf.toString(), PdfPCell.ALIGN_LEFT));
	                 	                 
	                 header.addCell(getCell("Meter Type : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(mtrType, PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("Demand Integration Period : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell("30", PdfPCell.ALIGN_LEFT));
	                 
	                 header.addCell(getCell("Meter Manufacture : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(mfrName, PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell("Profile Capture Period : ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell("30", PdfPCell.ALIGN_LEFT));
	                 
	                 header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	                 header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	                 header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	                 document.add(header);
	                 
	                 
	                 PdfPTable table = new PdfPTable(13);
	                 table.setWidths(new int[]{ 2 , 2, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
	                 table.setWidthPercentage(100);
	                 PdfPCell cell;
	                 cell = new PdfPCell(new Phrase("Date Time",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setRowspan(2);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("Duration /n Days HH:MM:SS",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setRowspan(2);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("Event Names",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setRowspan(2);
	                 table.addCell(cell);
	                
	                 
	                 cell = new PdfPCell(new Phrase("Voltage(V)",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setColspan(3);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("Current (A)",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setColspan(3);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("PF ",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setColspan(3);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("KWH",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setRowspan(2);
	                 table.addCell(cell);
	          
	                 
	                 cell = new PdfPCell(new Phrase("R",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("Y",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("B",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("R",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("Y",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("B",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(45);
	                 table.addCell(cell);
	                 
	                 cell = new PdfPCell(new Phrase("R",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("Y",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 cell = new PdfPCell(new Phrase("B",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 table.addCell(cell);
	                 
	                 
	                 //query
	                 
	              /* String query="SELECT time_1,evt3,rpv3,ypv3,bpv3,rplv3,yplv3,bplv3,"
	               				+ "rppv3,yppv3,bppv3,dkwh3,evtsts3,SUBSTR(TO_CHAR(difference),8,11) FROM (select time_1,time_2, "
	               				+ "TO_TIMESTAMP(time_1,'dd-MM-yyyy HH24:MI:SS')- TO_TIMESTAMP(time_2, 'dd-MM-yyyy HH24:MI:SS') as difference,"
	               				+ "evt2 as evt3,rpv2 as rpv3,ypv2 as ypv3,bpv2 as bpv3,rplv2 as rplv3,yplv2 as yplv3,bplv2 as bplv3,rppv2 as rppv3,"
	               				+ "yppv2 as yppv3,bppv2 as bppv3,dkwh2 as dkwh3,evtsts2 as evtsts3 from "
	               				+ "(select to_char(event_time,'dd-MM-yyyy HH24:MI:SS') as time_1, LAG(to_char(event_time,'dd-MM-yyyy HH24:MI:SS')) "
	               				+ "over(order by event_time) as time_2,evt1 as evt2,rpv1 as rpv2,ypv1 as ypv2,bpv1 as bpv2,rplv1 as rplv2,yplv1 "
	               				+ "as yplv2,bplv1 as bplv2,rppv1 as rppv2,yppv1 as yppv2,bppv1 as bppv2,dkwh1 as dkwh2,evtsts1 as evtsts2 from "
	               				+ "(select d5.event_time as event_time,e.event as evt1,d5.r_phase_val as rpv1,d5.y_phase_val as ypv1,d5.b_phase_val "
	               				+ "as bpv1,D5.R_PHASE_LINE_VAL as rplv1,D5.Y_PHASE_LINE_VAL yplv1,D5.B_PHASE_LINE_VAL bplv1,D5.R_PHASE_PF_VAL rppv1,"
	               				+ "D5.Y_PHASE_PF_VAL as yppv1,D5.B_PHASE_PF_VAL as bppv1,D5.D5_KWH as dkwh1,d5.event_status as evtsts1 from "
	               				+ "d5_data d5,master m, metermaster mm, cdf_data c,event_master e where c.cdf_id= d5.cdf_id "
	               				+ "and mm.accno=m.accno and c.accno=m.accno and e.event_code=d5.event_code and  c.accno=mm.accno and "
	               				+ "MM.METRNO='"+meterno+"' and m.consumerstatus like 'R' and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and c.BILLMONTH='"+month+"' and mm.rdngmonth='"+month+"'"
	               				+ "order by MM.METRNO,D5.EVENT_CODE ASC)))";*/
	                 
	                 String query="SELECT	time_1,	evt3,	rpv3,	ypv3,	bpv3,	rplv3,	yplv3,	bplv3,	rppv3,	yppv3,	bppv3,	dkwh3,	evtsts3,	to_char(difference, 'dd-MM-yyyy HH24:MI:SS') as difference FROM	(		SELECT			time_1,			time_2,			cast(time_1 as TIMESTAMP) - cast(time_2 as TIMESTAMP) AS difference, "
		            			+" evt2 AS evt3,			rpv2 AS rpv3,			ypv2 AS ypv3,			bpv2 AS bpv3,			rplv2 AS rplv3,			yplv2 AS yplv3, "
		            			+" bplv2 AS bplv3,			rppv2 AS rppv3,			yppv2 AS yppv3,			bppv2 AS bppv3,			dkwh2 AS dkwh3,			evtsts2 AS evtsts3		FROM "
		            			+" (				SELECT					cast(event_time as TIMESTAMP) AS time_1,					LAG (						cast(event_time as TIMESTAMP)					) OVER (ORDER BY event_time) AS time_2, "
		            			+"		evt1 AS evt2,					rpv1 AS rpv2,					ypv1 AS ypv2,					bpv1 AS bpv2,					rplv1 AS rplv2,					yplv1 AS yplv2,					bplv1 AS bplv2,					rppv1 AS rppv2,					yppv1 AS yppv2,					bppv1 AS bppv2,					dkwh1 AS dkwh2,					evtsts1 AS evtsts2 "
		            			+"	FROM					(						SELECT							d5.event_time AS event_time,							e.event AS evt1,							d5.r_phase_val AS rpv1,							d5.y_phase_val AS ypv1,							d5.b_phase_val AS bpv1,						D5.R_PHASE_LINE_VAL AS rplv1,							D5.Y_PHASE_LINE_VAL yplv1,							D5.B_PHASE_LINE_VAL bplv1,							D5.R_PHASE_PF_VAL rppv1,							D5.Y_PHASE_PF_VAL AS yppv1,							D5.B_PHASE_PF_VAL AS bppv1,							D5.D5_KWH AS dkwh1,							d5.event_status AS evtsts1 "
		            			+"			FROM							mdm_test.d5_data d5,							mdm_test.master M,							mdm_test.metermaster mm,							mdm_test.cdf_data C,							mdm_test.event_master e						WHERE							C .cdf_id = d5.cdf_id						AND mm.accno = M .accno						AND C .accno = M .accno						AND e.event_code = d5.event_code						AND C .accno = mm.accno						AND MM.METRNO = 'HRH58812'						AND M .consumerstatus LIKE 'R'						AND C .meterno = mm.metrno						AND mm.rdngmonth = C .billmonth						AND C .BILLMONTH = '201808'						AND mm.rdngmonth = '201808'						ORDER BY							MM.METRNO,							D5.EVENT_CODE ASC					) AA			) BB	) CC";
		                 
	               MDMLogger.logger.info("Tamper DATA Quuiouoery==uiououo==>>"+query);
	               data=entityManager.createNativeQuery(query).getResultList();
	               MDMLogger.logger.info("Tamper Query DATA LIST SIZE==ppppppppp==>>"+data.size());
	               
	               String evtDate="",evt="",dur="";
	               BigDecimal rPH_Val=null,yPH_Val=null,bPH_Val=null;
	               BigDecimal rPH_Line_Val=null,yPH_Line_Val=null,bPH_Line_Val=null;
	               BigDecimal rPH_PF_Val=null,yPH_PF_Val=null,bPH_PF_Val=null,d5_KWH=null;
	               String evtStatus="";
	               String prev="";
	               for (int n = 0; n < data.size(); n++) 
		  	    	 {
		  	    		Object[] str=(Object[]) data.get(n);
		  	    		evt=(String) str[1];
		  	    		if(!prev.equalsIgnoreCase(evt)){
		  	    			 cell = new PdfPCell(new Phrase(evt,new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			                 table.addCell(cell);
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			table.addCell("");
				  	    			prev=evt;
				               
		  	    		}
		  	    		evtDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(str[0]);
		  	    					rPH_Val=(BigDecimal) str[2];
		  	    					yPH_Val=(BigDecimal) str[3];
		  	    					bPH_Val=(BigDecimal) str[4];
		  	    					rPH_Line_Val=(BigDecimal) str[5];
		  	    					yPH_Line_Val=(BigDecimal) str[6];
		  	    					bPH_Line_Val=(BigDecimal) str[7];
		  	    					rPH_PF_Val=(BigDecimal) str[8];
		  	    					yPH_PF_Val=(BigDecimal) str[9];
		  	    					bPH_PF_Val=(BigDecimal) str[10];
		  	    					d5_KWH=(BigDecimal) str[11];
		  	    					if(Integer.parseInt(String.valueOf(str[12])) == 0)
		  	    					{
		  	    						evtStatus=(String) "Occurrence";
		  	    					}
		  	    					else
		  	    					{
		  	    						evtStatus=(String)"Restoration";
		  	    					}
		  	    					dur=(String) str[13];
		  	    		
		  	    		
		  	    		 table.addCell(evtDate);
		  	    		if(Integer.parseInt(String.valueOf(str[12])) == 0)
	    					{
		  	    			table.addCell(" - ");
	    					}
	    					else
	    					{
	    						table.addCell(dur);
	    					}
		                 table.addCell(evt+"-"+ evtStatus);
		                 table.addCell(rPH_Val.toString());
		                 table.addCell(yPH_Val.toString());
		                 table.addCell(bPH_Val.toString());
		                 table.addCell(rPH_Line_Val.toString());
		                 table.addCell(yPH_Line_Val.toString());
		                 table.addCell(bPH_Line_Val.toString());
		                 table.addCell(rPH_PF_Val.toString());
		                 table.addCell(yPH_PF_Val.toString());
		                 table.addCell(bPH_PF_Val.toString());
		                 table.addCell(d5_KWH==null?"":d5_KWH.toString());
		  			}
	                 document.add(table);
	                 document.close();

			response.setHeader("Content-disposition", "attachment; filename=Tamper_Event_Report_"+meterno+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

			} 
            catch (Exception de) 
            {
            	de.printStackTrace();
			}

			return null;
	}
	
	public static PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(5);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
	@RequestMapping(value="/exceptionManagementCon",method={RequestMethod.POST,RequestMethod.GET})
	public String exceptionManagementCon(HttpServletRequest request,ModelMap model)
	{
		/*List<EventMaster> list = eventmasterService.findAll();
		model.addAttribute("events", list);*/
		return "exceptionManagementConfiguration";
	}
	@RequestMapping(value="/exceptionMangementList",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object exceptionMangementList(){
		List<EventMaster> list = eventmasterService.findAll();
		return list;
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(value="/configModification/{v}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object configModification(@PathVariable String v){
		
		String[] code=v.split("_");
		String sql=null;
		if(code[0].equalsIgnoreCase("inactive")){
			 sql="update meter_data.event_master set event_config='A' where event_code="+code[1];
		}
		else{
             sql="update meter_data.event_master set event_config='I' where event_code="+code[1];
		}
		
		int i=entityManager.createNativeQuery(sql).executeUpdate();
		return Integer.toString(i);
	}
	@RequestMapping(value="/exceptionReports",method={RequestMethod.POST,RequestMethod.GET})
	public String exceptionReports(Model m){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfm = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String s=df.format(date);
		String sm=dfm.format(date);
		String lsql="select meter_number,count(meter_number) from meter_data.load_survey where TO_CHAR(read_time, 'yyyy-MM-dd')='"+s+"' GROUP BY meter_number HAVING COUNT(meter_number)<48";
		List<Object[]> l=entityManager.createNativeQuery(lsql).getResultList();
		m.addAttribute("load", l);
		String dlsql="select mtrno,circle,division,subdivision,customer_name,customer_address from meter_data.master_main where mtrno  not in (select mtrno from meter_data.daily_load where TO_CHAR(rtc_date_time, 'yyyy-MM-dd')='"+s+"')";
		List<Object[]> dl=entityManager.createNativeQuery(dlsql).getResultList();
		m.addAttribute("dload", dl);
		String eventsl="select meter_number,event_code,(select event from meter_data.event_master where event_code=TO_NUMBER(e.event_code, '9999')) from meter_data.events e where to_number(event_code,'9999') in (select event_code from meter_data.event_master where event_config='A'\n" +
				") and TO_CHAR(event_time, 'yyyy-MM-dd')='"+s+"'";
		List<Object[]> eventslist=entityManager.createNativeQuery(eventsl).getResultList();
		m.addAttribute("events", eventslist);
		String billhist="select mtrno,circle,division,subdivision,customer_name,customer_address from meter_data.master_main where mtrno  not in (select mtrno from meter_data.bill_history where TO_CHAR(billing_date, 'yyyyMM')='"+sm+"')";
		List<Object[]> billhistlist=entityManager.createNativeQuery(billhist).getResultList();
		m.addAttribute("bh", billhistlist);
		String powerDuration="select mn.meter_number,\n" +
				"(trunc((case \n" +
				"when mn.power_on_time>1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then 1440\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then mn.power_on_time\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')='"+s+"' then mn.power_on_time\n" +
				"end)/60)||' Hours ' )||\n" +
				"(((case \n" +
				"when mn.power_on_time>1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then 1440\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then mn.power_on_time\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')='"+s+"' then mn.power_on_time\n" +
				"end)%60)||' Minutes') as power_on_currdate\n" +
				",\n" +
				"(trunc((case\n" +
				"when mn.power_on_time>1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"'  then 0\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then 1440-(mn.power_on_time)\n" +
				"when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')='"+s+"' then ((EXTRACT(HOUR FROM  CURRENT_TIMESTAMP)*60)+EXTRACT(MINUTE FROM  CURRENT_TIMESTAMP))-(mn.power_on_time)\n" +
				"end)/60)||' Hours ' )\n" +
				" ||\n" +
				" (((case \n" +
				" when mn.power_on_time>1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then 0\n" +
				" when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')!='"+s+"' then 1440-(mn.power_on_time)\n" +
				" when mn.power_on_time<=1440 and to_char(current_timestamp, 'YYYY-MM-DD')='"+s+"' then ((CAST((COALESCE(((EXTRACT(HOUR FROM  CURRENT_TIMESTAMP)*60)+EXTRACT(MINUTE FROM  CURRENT_TIMESTAMP)),'0')) AS INTEGER))-CAST((COALESCE(mn.power_on_time,'0')) AS INTEGER))\n" +
				" end)%60)||' Minutes') \n" +
				"as power_off_currdate\n" +
				" from (\n" +
				"select meter_number,max(total_power_on_duration)-(select max(total_power_on_duration) from meter_data.amiinstantaneous where   meter_number=a.meter_number and  to_char(rdate,'YYYY-MM-DD')=to_char(to_timestamp('"+s+"','YYYY-MM-DD')- interval '1 day', 'YYYY-MM-DD')) as  power_on_time from meter_data.amiinstantaneous a where  to_char(rdate,'YYYY-MM-DD')='"+s+"' and total_power_on_duration is not null  GROUP BY meter_number) mn";
		List<Object[]> powerDurationlist=entityManager.createNativeQuery(powerDuration).getResultList();
		m.addAttribute("pd", powerDurationlist);
		
		return "exceptionReports";
	}
	@RequestMapping(value="/rtcreport",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody Object rtcreport(){
		 String s="select meter_number,read_time,sample_or_server_time+INTERVAL '5 hour 30 minute' as servertime from meter_data.load_survey where to_char(read_time,'yyyy-MM-dd HH24:MI')  not in (to_char(sample_or_server_time+INTERVAL '5 hour 30 minute','yyyy-MM-dd HH24:MI')) ";
	 List<Object[]> l=entityManager.createNativeQuery(s).getResultList();
	 return l;
     	 }
	
}
