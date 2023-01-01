/**
 * 
 */
package com.bcits.mdas.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.controller.DataExchangeController;

import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.ValidationProcessReportEntity;
import com.bcits.mdas.entity.ValidationProcessReportEntity.KeyValidationUniqueId;
import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.mdas.service.AssignValidationRuleService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.ValidationProcessReportService;
import com.bcits.mdas.service.VeeRuleService;
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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

/**
 * @author Tarik
 *
 */
@Controller
public class VEEProcessController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private VeeRuleService addveeservice;

	@Autowired
	private AssignValidationRuleService asgveeservice;

	@Autowired
	private ValidationProcessReportService veeprocservice;

	@Autowired
	private FeederMasterService feederMasterService;
	
	@Autowired
	DataExchangeController dec;
	
//	private Double rtc_lowthrl,missingLoad_lowthrl,incmpltload_lowthrl,missingEvent_lowthrl,invalidPowerfactor_lowthrl,lessKVA_lowthrl,FreqCheck_lowthrl,revenueProt_lowthrl,highCons_lowthrl,lowCons_lowthrl,zeroCons_lowthrl,energySumCheck_lowthrl,negativeConsumption_lowthrl =0.0;
//	private Double rtc_highthrl,missingLoad_highthrl,incmpltload_highthrl,missingEvent_highthrl,invalidPowerfactor_highthrl,lessKVA_highthrl,FreqCheck_highthrl,revenueProt_highthrl,highCons_highthrl,lowCons_highthrl,zeroCons_highthrl,energySumCheck_highthrl,negativeConsumption_highthrl =0.0;
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter OUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@RequestMapping(value = "/RTCFailureProcess", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String aggregation(HttpServletRequest request, Model model) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println("Todays Date: -"+dateFormat.format(date));
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		System.out.println("Yesterday Date: -"+dateFormat.format(yesterday).toString());
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		Date yesterday1 = calendar1.getTime();
		System.out.println("Previous Date: -"+dateFormat.format(yesterday1).toString());
//		rtcFailureProcess("2019-11-01", "2019-11-30"); 			//Done
//		missingLoadDataProcess("2019-12-01", "2019-12-31"); 	//Done
//		incmpltloadDataProcess("2019-11-01", "2019-11-30");	    //Done
//		missingEventDataProcess("2019-11-01", "2019-11-30");    //Done
//		invalidPowerfactorProcess("2019-11-01", "2019-11-30");  //Done
//		lessKVAValueProcess("2019-07-01", "2019-07-30"); 		//Done
//		theresoldFreqCheckProcess("2019-11-01", "2019-11-30");  //Done
//		revenueProtValidProcess("2019-07-01", "2019-08-30"); 	//pass current month and previous month
//		highConsValidProcess("2019-07-01", "2019-08-30"); 		//pass current month and previous month
//		lowConsValidProcess("2019-03-01", "2019-04-01"); 		//pass current month and previous month
//		zeroConsValidProcess("2019-07-01", "2019-08-30");		//Done
//		energySumCheckProcess("2019-07-01", "2019-08-30"); 		//Done
//		negativeConsumptionProcess("2019-07-01", "2019-08-01"); //Done
//	spikeCheckProcess("2019-07-01","2019-08-01");           //Done
//	summationCheckProcess("2019-07-01","2019-08-01");       //Done
	//	repeatedValueCheck("2019-07-01","2019-08-01");
	//	thresholdLimitCheck("2019-07-01","2019-08-01");
		return "sucess";
	}
	
	

	@RequestMapping(value = "/RTCFailureProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String aggregation1(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
        System.out.println(first);
        System.out.println(last);
        rtcFailureProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/missingLoadDataProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String missingLoadDataProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		missingLoadDataProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/missingEventDataProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String missingEventDataProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		missingEventDataProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}

	@RequestMapping(value = "/incmpltloadDataProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String incmpltloadDataProcess1(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		incmpltloadDataProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/invalidPowerfactorProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String invalidPowerfactorProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		invalidPowerfactorProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/theresoldFreqCheckProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String theresoldFreqCheckProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		theresoldFreqCheckProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/lessKVAValueProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String lessKVAValueProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		lessKVAValueProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	/*
	 * @RequestMapping(value = "/revenueProtValidProcess/{month}", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public @ResponseBody String
	 * revenueProtValidProcess(HttpServletRequest request, Model model,@PathVariable
	 * String month) { final LocalDate first = LocalDate.parse(month.trim() + "01",
	 * FORMATTER); final LocalDate last = first.plusMonths(1).minusDays(1);
	 * revenueProtValidProcess(OUT_FORMATTER.format(first),
	 * OUT_FORMATTER.format(last)); // Development Done & Tested(Process & Report)
	 * return "sucess"; }
	 */
	
	/*
	 * @RequestMapping(value = "/highConsValidProcess/{month}", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public @ResponseBody String
	 * highConsValidProcess(HttpServletRequest request, Model model,@PathVariable
	 * String month) { final LocalDate first = LocalDate.parse(month.trim() + "01",
	 * FORMATTER); final LocalDate last = first.plusMonths(1).minusDays(1);
	 * highConsValidProcess(OUT_FORMATTER.format(first),
	 * OUT_FORMATTER.format(last)); // Development Done & Tested(Process & Report)
	 * return "sucess"; }
	 */
	
	/*
	 * @RequestMapping(value = "/lowConsValidProcess/{month}", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public @ResponseBody String
	 * lowConsValidProcess(HttpServletRequest request, Model model,@PathVariable
	 * String month) { final LocalDate first = LocalDate.parse(month.trim() + "01",
	 * FORMATTER); final LocalDate last = first.plusMonths(1).minusDays(1);
	 * lowConsValidProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));
	 * // Development Done & Tested(Process & Report) return "sucess"; }
	 */
	
	@RequestMapping(value = "/zeroConsValidProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String zeroConsValidProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		zeroConsValidProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}

	@RequestMapping(value = "/energySumCheckProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String energySumCheckProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		energySumCheckProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/negativeConsumptionProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String negativeConsumptionProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
		negativeConsumptionProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "sucess";
	}
	
	@RequestMapping(value = "/IncmpltloadDataProcess12", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String incmpltloadDataProcess(HttpServletRequest request, Model model) {	
		incmpltloadDataProcess("2019-12-01", "2019-12-31"); 	//Done
		return "sucess";
	}
	
	
//	@RequestMapping(value = "/summationCheckProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody String summationCheckProcess(HttpServletRequest request, Model model,@PathVariable String month) {
//		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
//        final LocalDate last = first.plusMonths(1).minusDays(1);
//        summationCheckProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done)
//		return "sucess";
//	}
//
//	@RequestMapping(value = "/repeatedValueCheck/{month}", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody String repeatedValueCheck(HttpServletRequest request, Model model,@PathVariable String month) {
//		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
//        final LocalDate last = first.plusMonths(1).minusDays(1);
//        repeatedValueCheck(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done)
//		return "sucess";
//	}
//	@RequestMapping(value = "/thresholdLimitCheck/{month}", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody String thresholdLimitCheck(HttpServletRequest request, Model model,@PathVariable String month) {
//		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
//        final LocalDate last = first.plusMonths(1).minusDays(1);
//        
//        thresholdLimitCheck(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done )
//		return "sucess";
//	}
	
	@RequestMapping(value = "/spikeCheckProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String spikeCheckProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
        System.out.println(first);
        System.out.println(last);
        spikeCheckProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "success";
	}
	
	@RequestMapping(value = "/SummationCheckProcess/{month}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String summationCheckProcess(HttpServletRequest request, Model model,@PathVariable String month) {
		final LocalDate first = LocalDate.parse(month.trim() + "01", FORMATTER);
        final LocalDate last = first.plusMonths(1).minusDays(1);
        summationCheckProcess(OUT_FORMATTER.format(first), OUT_FORMATTER.format(last));   // Development Done & Tested(Process & Report)
		return "success";
	}
	
	@RequestMapping(value = "/RepeatedValueCheck/{date}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String repeatedValueCheck(HttpServletRequest request, Model model,@PathVariable String date) {
        repeatedValueCheck(date, date);   // Development Done & Tested(Process & Report)
		return "success";
	}
	
	@RequestMapping(value = "/ThresholdLimitCheck/{date}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String thresholdLimitCheck(HttpServletRequest request, Model model,@PathVariable String date) {
        thresholdLimitCheck(date, date);   // Development Done & Tested(Process & Report)
		return "success";
	}
	
	
	
	@RequestMapping(value = "/validationReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String validationReport(HttpServletRequest request, Model model) {
		// missingLoadDataProcess("2019-03-01","2019-03-31");
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "validationReport";
	}
	
	@RequestMapping(value = "/downloadValidationExceptionPdf", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadValidationExceptionPdf(HttpServletRequest request, Model model, HttpServletResponse response) 
	{
		String zone = request.getParameter("z");
		String circle = request.getParameter("c");
		
		String town = request.getParameter("t").trim();
		
		String z = "";
		String c = "";
		
		String t = "";
		if(zone.equalsIgnoreCase("All"))
		{
			z = "%";
		}
		if(circle.equalsIgnoreCase("All"))
		{
			c = "%";
		}
		if(town.equalsIgnoreCase("All"))
		{
			t = "%";
		}
		String ruleid = request.getParameter("ruleId").trim();
		String monthyr = request.getParameter("month").trim();
		List<FileInputStream> list=new ArrayList<FileInputStream>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		

		System.out.println(town+ruleid+monthyr);

		

		
		
		
		try 
		{
			


			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			 baos = new ByteArrayOutputStream();
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
		        Chunk glue = new Chunk(new VerticalPositionMark());
		        PdfPCell cell1 = new PdfPCell();
		        Paragraph pstart = new Paragraph();
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("Data Validation Reports : " +ruleid,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		        p1.setAlignment(Element.ALIGN_CENTER);
		        cell2.addElement(p1);
		        cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		        pdf1.addCell(cell2);
		        document.add(pdf1);
		        
		        PdfPTable header = new PdfPTable(4);
	             header.setWidthPercentage(100);
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));

	             
	             
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             document.add(header);

		 			List<ValidationProcessReportEntity> eventDataDateList=veeprocservice.getValidationReportData(z,c,ruleid,monthyr,t);
			        				
			        				PdfPTable parameterTable = new PdfPTable(9);
			   	                 parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1});
			   	                 parameterTable.setWidthPercentage(100);
			   		             PdfPCell parameterCell;
			   		             parameterCell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Month Year",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		          parameterTable.addCell(parameterCell);
			   		           
			   		          parameterCell = new PdfPCell(new Phrase("Rule Id",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Sub Division",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          
			   		          
			   		       parameterCell = new PdfPCell(new Phrase("Rule Name",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);

	 		          
	 		         parameterCell = new PdfPCell(new Phrase("Town Code",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
 		             parameterCell.setFixedHeight(25f);
 		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
 		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
 		          parameterTable.addCell(parameterCell);
 		          
 		          
 		         parameterCell = new PdfPCell(new Phrase("Location Type",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		          parameterTable.addCell(parameterCell);
		          
		          parameterCell = new PdfPCell(new Phrase("Location Name",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		          parameterTable.addCell(parameterCell);
		          
		          parameterCell = new PdfPCell(new Phrase("Meter Number",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		          parameterTable.addCell(parameterCell);
		          
	 		          
	 	
		          
		       
			   		          int i =0 ;
			        				for (ValidationProcessReportEntity obj : eventDataDateList) 
			    	                {
			        					i++;
			        					parameterCell = new PdfPCell(new Phrase((i)+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		   								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   								 parameterTable.addCell(parameterCell);
			    	                	
			    	                		
			    								 parameterCell = new PdfPCell(new Phrase(obj.getMyKey().getMonthyear()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								
			    								 
			    								
			    								 
			    								 parameterCell = new PdfPCell(new Phrase(obj.getMyKey().getV_rule_id()==null?"":obj.getMyKey().getV_rule_id()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    							
			    	                		
			    	                		
		    								 
		    							
		    	                		
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj.getSubdivision()==null?"":obj.getSubdivision()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 

				    	                		parameterCell = new PdfPCell(new Phrase(obj.getRulename()==null?"":obj.getRulename()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj.getTown_code()==null?"":obj.getTown_code()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj.getLocation_type()==null?"":obj.getLocation_type()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								
		    								 parameterCell = new PdfPCell(new Phrase(obj.getLocation_name()==null?"":obj.getLocation_name()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj.getMyKey().getMeter_number()==null?"":obj.getMyKey().getMeter_number()+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		
		    	                		
		    								
		    								 
		    								 
		    	                		
			    	        
		    	                		
		    	                		
		    							
		    						
		    					} 
		        				
		    	                document.add(parameterTable);
		       
			document.close();
			
			
		
			response.setHeader("Content-disposition", "attachment; filename=Data_Validation_Report_"+ruleid+"_"+town+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();
		    
		/*	response.setHeader("Content-disposition", "attachment; filename=InstantaneousParameters_"+meterno+"-"+month+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();*/

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public PdfPCell getCell(String text, int alignment) 
	 {
		    PdfPCell cell = new PdfPCell(new Phrase(text));
		    cell.setPadding(5);
		    cell.setHorizontalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}
	
	@RequestMapping(value = "/getValidationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getValidationData(HttpServletRequest request, Model model) {

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		/*
		 * String division = request.getParameter("division"); String subdivision =
		 * request.getParameter("sdoCode").trim();
		 */
		String town = request.getParameter("town").trim();
		String ruleid = request.getParameter("ruleId").trim();
		String monthyr = request.getParameter("month").trim();
	//	model.addAttribute("results", "notDisplay");
		System.out.println(town+ruleid+monthyr);
//		System.out.println("zone=="+zone+"circle=="+circle+"division=="+division+"subdivision=="+subdivision+"ruleid=="+ruleid+"monthyr=="+monthyr);
		//List<ValidationProcessReportEntity> vrEnt =null;
		List<ValidationProcessReportEntity> vrEnt = veeprocservice.getValidationReportData(zone,circle,ruleid,monthyr,town);
		
		 for (ValidationProcessReportEntity abc : vrEnt) {
			//  System.out.println(abc.getMyKey().getMeter_number());
		  }
		 
	//	model.addAttribute("EstimationRule", vrEnt);
		return vrEnt;

	} 
	
	@RequestMapping(value = "/viewValidationMeterData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> viewValidationMeterData(HttpServletRequest request, Model model) {

		String meter_number = request.getParameter("meter_number");
		String ruleid = request.getParameter("ruleId").trim();
		String monthyr = request.getParameter("month").trim();
		String fromDate,toDate="";
		Double Hgthrlimit=0.0;
		Double Lwthrlimit=0.0;
		System.out.println("ruleid=="+ruleid+"monthyr=="+monthyr);
		
		if(ruleid.equalsIgnoreCase("VEE01")) {
				ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
				if (vpr != null) {
				//	System.out.println("Hi....");
					fromDate=vpr.getFromDate();
					toDate=vpr.getToDate();
					Hgthrlimit=vpr.getHgthrlimit();
					Lwthrlimit=vpr.getLwthrlimit();
					String sql = "select DISTINCT ami.meter_number,read_time,time_stamp,round(extract(epoch from (time_stamp - read_time))/60) as timediff \r\n" + 
							"from meter_data.amiinstantaneous ami \r\n" + 
							"WHERE\r\n" + 
							"round(extract(epoch from (time_stamp - read_time))/60)>="+Hgthrlimit+"\r\n" + 
							"and date(read_time)   BETWEEN '" + fromDate + "' and  '" + toDate+"'" + 
							"and ami.meter_number in ('"+meter_number+"') ";
//					System.out.println(sql);
					List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
					return list;
					
				} else {
					return null;
				}
		}
		else if(ruleid.equalsIgnoreCase("VEE02")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi....");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
//				String sql = "SELECT meter_number,date(read_time),COUNT(*) FROM meter_data.load_survey\r\n" + 
//						"WHERE date(read_time)  BETWEEN '" + fromDate + "' and  '" + toDate+"'" + 
//						"and meter_number in  ('"+meter_number+"')  \r\n" + 
//						"GROUP BY meter_number,date(read_time) having count(*)=0";
				
				String sql= "select m.sdate , COALESCE (m.meterno,'')as meter_number,\r\n" + 
						"CASE  WHEN n.count IS NULL THEN '0' ELSE n.count END count\r\n" + 
						"from \r\n" + 
						"(select date(d) as sdate, mn.meterno \r\n" + 
						"from  generate_series (CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate + "' as TIMESTAMP),  interval '1 day') d\r\n" + 
						" cross join ( select regexp_split_to_table( '"+meter_number+"', '_') as meterno) mn)m \r\n" + 
						" LEFT JOIN\r\n" + 
						"(SELECT meter_number,date(read_time) as rdate,COUNT(*)  FROM meter_data.load_survey\r\n" + 
						"WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ('"+ meter_number + "')  \r\n" + 
						"GROUP BY meter_number,date(read_time) )n\r\n" + 
						"ON m.sdate=n.rdate and m.meterno=n.meter_number\r\n" + 
						"GROUP BY m.sdate,m.meterno,n.count\r\n" + 
						"having count is null or count=0";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE03")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
			//	System.out.println("Hi....");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "select * from (\r\n" + 
						"SELECT meter_number,date(read_time),COUNT(*)  FROM meter_data.load_survey\r\n" + 
						"WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"'" + 
						"and meter_number in ('"+meter_number+"')  \r\n" + 
						"GROUP BY meter_number,date(read_time))b \r\n" + 
						"WHERE b.count is not null AND (count<>48 AND count<>96)";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE04")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi...Event data missing.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
//				String sql = "SELECT meter_number, date(event_time), COUNT(*) FROM meter_data.events\r\n" + 
//						"WHERE date(event_time)  BETWEEN '" + fromDate + "' and  '" + toDate+"'" + 
//						"and meter_number in ('"+meter_number+"') \r\n" + 
//						"GROUP BY meter_number,date(event_time) having count(*)=0 ";
				String sql ="select COALESCE (m.meterno,'')meter_number, m.sdate , \r\n" + 
						"CASE  WHEN n.count IS NULL THEN '0' ELSE n.count END count \r\n" + 
						"from (select date(d) as sdate, mn.meterno \r\n" + 
						"from  generate_series (CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate+"' as TIMESTAMP),  interval '1 day') d \r\n" + 
						" cross join ( select regexp_split_to_table( '"+meter_number+"', '_') as meterno) mn)m \r\n" + 
						" LEFT JOIN \r\n" + 
						"(SELECT meter_number,date(event_time) as edate,COUNT(*)  FROM meter_data.events \r\n" + 
						"WHERE date(event_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in ('" + meter_number + "')  GROUP BY meter_number,date(event_time) )n \r\n" + 
						"ON m.sdate=n.edate and m.meterno=n.meter_number\r\n" + 
						"GROUP BY m.sdate,m.meterno,n.count \r\n" + 
						"having count is null or count=0";
				
//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE05")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi....");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "";
				//System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE06")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi..invalid power factor..");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "SELECT meter_number, date(read_time),pf_threephase FROM meter_data.amiinstantaneous\r\n" + 
						"WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' \r\n" + 
						"and meter_number in ('"+meter_number+"') and pf_threephase not between -1 and 1\r\n" + 
						"GROUP BY meter_number, date(read_time) ,pf_threephase";
				//System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
	
		else if(ruleid.equalsIgnoreCase("VEE07")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi..kva less than kw..");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "SELECT meter_number, date(billing_date),kva,demand_kw FROM meter_data.bill_history\r\n" + 
						"WHERE date(billing_date)  BETWEEN '" + fromDate + "' and  '" + toDate+"'  \r\n" + 
						"and meter_number in ('"+meter_number+"') and kva<=demand_kw\r\n" + 
						"GROUP BY meter_number, date(billing_date),kva,demand_kw";
				//System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE08")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi..fre bynd thrs..");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "SELECT meter_number, date(read_time) , frequency ,concat(" + Lwthrlimit + " ,' - ',  " + Hgthrlimit + ") as range FROM meter_data.amiinstantaneous\r\n" + 
						"WHERE date(read_time)  BETWEEN '" + fromDate + "' and  '" + toDate+"' \r\n" + 
						"and meter_number in ('"+meter_number+"') \r\n" + 
						"and  frequency not between " + Lwthrlimit + " and " + Hgthrlimit + "\r\n" + 
						"GROUP BY meter_number, date(read_time),frequency";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		/*
		 * else if(ruleid.equalsIgnoreCase("VEE09")) { ValidationProcessReportEntity vpr
		 * = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr); if (vpr !=
		 * null) { // System.out.println("Hi...."); fromDate=vpr.getFromDate();
		 * toDate=vpr.getToDate(); Hgthrlimit=vpr.getHgthrlimit();
		 * Lwthrlimit=vpr.getLwthrlimit(); String sql = ""; //System.out.println(sql);
		 * List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
		 * return list;
		 * 
		 * } else { return null; } }
		 */
		/*
		 * else if(ruleid.equalsIgnoreCase("VEE10")) { ValidationProcessReportEntity vpr
		 * = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr); if (vpr !=
		 * null) { //System.out.println("Hi...rev protection.");
		 * fromDate=vpr.getFromDate(); toDate=vpr.getToDate();
		 * Hgthrlimit=vpr.getHgthrlimit(); Lwthrlimit=vpr.getLwthrlimit(); String sql =
		 * "select meterno, billmonth,kwh,pre_month,pre_month_kwh from (\r\n" +
		 * "select * from(with cte as (\r\n" +
		 * "SELECT accno,meterno, billmonth,kwh, lead (billmonth, 1) OVER (\r\n" +
		 * "PARTITION BY meterno ORDER BY billmonth desc) AS pre_month,\r\n" +
		 * "lead (kwh, 1) OVER (\r\n" +
		 * "PARTITION BY meterno ORDER BY kwh desc) AS pre_month_kwh,\r\n" +
		 * "kwh - lead (kwh, 1) OVER ( PARTITION BY meterno ORDER BY billmonth DESC) AS cur_prev_diff,\r\n"
		 * + "rank()OVER (PARTITION BY meterno ORDER BY billmonth desc) as rnk\r\n" +
		 * "FROM meter_data.consumerreadings\r\n" + "where date(rdate)  BETWEEN '" +
		 * fromDate + "' and  '" + toDate+"'  \r\n" +
		 * "and meterno  in ('"+meter_number+"') \r\n" +
		 * "group by accno,meterno, kwh,billmonth\r\n" +
		 * ") select *,((cur_prev_diff * 100)/(pre_month_kwh)) as percentage from cte where  rnk=1) cte1 \r\n"
		 * + "where percentage not BETWEEN  " + Lwthrlimit + " and " + Hgthrlimit +
		 * " )b"; //System.out.println(sql); List<Object[]> list =
		 * entityManager.createNativeQuery(sql).getResultList(); return list;
		 * 
		 * } else { return null; } }
		 */
		/*
		 * else if(ruleid.equalsIgnoreCase("VEE11")) { ValidationProcessReportEntity vpr
		 * = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr); if (vpr !=
		 * null) { //System.out.println("Hi...high consmp.");
		 * fromDate=vpr.getFromDate(); toDate=vpr.getToDate();
		 * Hgthrlimit=vpr.getHgthrlimit(); Lwthrlimit=vpr.getLwthrlimit(); String sql =
		 * "select meterno, billmonth,kwh,pre_month,pre_month_kwh from (\r\n" +
		 * "select * from(with cte as (\r\n" +
		 * "SELECT accno,meterno, billmonth,kwh, lead (billmonth, 1) OVER (\r\n" +
		 * "PARTITION BY meterno ORDER BY billmonth desc) AS pre_month,\r\n" +
		 * "lead (kwh, 1) OVER (\r\n" +
		 * "PARTITION BY meterno ORDER BY kwh desc) AS pre_month_kwh,\r\n" +
		 * "kwh - lead (kwh, 1) OVER ( PARTITION BY meterno ORDER BY billmonth DESC) AS cur_prev_diff,\r\n"
		 * + "rank()OVER (PARTITION BY meterno ORDER BY billmonth desc) as rnk\r\n" +
		 * "FROM meter_data.consumerreadings\r\n" + "where date(rdate)  BETWEEN '" +
		 * fromDate + "' and  '" + toDate+"'  and meterno in ('"+meter_number+"')" +
		 * "group by accno,meterno, kwh,billmonth\r\n" +
		 * ") select *,((cur_prev_diff * 100)/(pre_month_kwh)) as percentage from cte where  rnk=1) cte1 where \r\n"
		 * + " percentage>=50\r\n" + ")b"; // System.out.println(sql); List<Object[]>
		 * list = entityManager.createNativeQuery(sql).getResultList(); return list;
		 * 
		 * } else { return null; } }
		 */
		/*
		 * else if(ruleid.equalsIgnoreCase("VEE12")) { ValidationProcessReportEntity vpr
		 * = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr); if (vpr !=
		 * null) { //System.out.println("Hi...low consmp."); fromDate=vpr.getFromDate();
		 * toDate=vpr.getToDate(); Hgthrlimit=vpr.getHgthrlimit();
		 * Lwthrlimit=vpr.getLwthrlimit(); String sql =
		 * "select meterno, billmonth,kwh,pre_month,pre_month_kwh from (\r\n" +
		 * "select * from(with cte as (\r\n" +
		 * "SELECT accno,meterno, billmonth,kwh, lead (billmonth, 1) OVER (\r\n" +
		 * "PARTITION BY meterno ORDER BY billmonth desc) AS pre_month,\r\n" +
		 * "lead (kwh, 1) OVER (PARTITION BY meterno ORDER BY kwh desc) AS pre_month_kwh,\r\n"
		 * +
		 * "kwh - lead (kwh, 1) OVER ( PARTITION BY meterno ORDER BY billmonth DESC) AS cur_prev_diff,\r\n"
		 * + "rank()OVER (PARTITION BY meterno ORDER BY billmonth desc) as rnk\r\n" +
		 * "FROM meter_data.consumerreadings  where date(rdate)  BETWEEN '" + fromDate +
		 * "' and  '" + toDate+"' and meterno in ('"+meter_number+"') \r\n" +
		 * "group by accno,meterno, kwh,billmonth\r\n" +
		 * ") select *,((cur_prev_diff * 100)/(pre_month_kwh)) as percentage from cte where  rnk=1) cte1 where \r\n"
		 * + "percentage<=-50)b"; // System.out.println(sql); List<Object[]> list =
		 * entityManager.createNativeQuery(sql).getResultList(); return list;
		 * 
		 * } else { return null; } }
		 */
		else if(ruleid.equalsIgnoreCase("VEE13")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi...zero consumpt.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "SELECT meterno, billmonth,kwh,0 as currkwh FROM meter_data.consumerreadings\r\n" + 
						"where date(rdate)   BETWEEN '" + fromDate + "' and  '" + toDate+"'  \r\n" + 
						"and meterno in ('"+meter_number+"') \r\n" + 
						"and kwh=0  group by meterno, kwh,billmonth";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE14")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi..energy sum..");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "with cte1 as(with cte as(\r\n" + 
						"SELECT cm.meterno, cm.billmonth,cm.kwh, lsmc.kwh as load_kwh,\r\n" + 
						"lead (cm.billmonth, 1) OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc ) AS pre_month,\r\n" + 
						"lead (cm.kwh, 1) OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc) AS pre_month_kwh,\r\n" + 
						"cm.kwh - lead (cm.kwh, 1) OVER ( PARTITION BY cm.meterno ORDER BY cm.rdate DESC) AS cons_kwh,\r\n" + 
						"rank()OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc) as rnk\r\n" + 
						"FROM meter_data.consumerreadings cm, meter_data.load_survey_monthly_consumption lsmc\r\n" + 
						"where lsmc.mtrno=cm.meterno and lsmc.billmonth=cm.billmonth and date(cm.rdate)  BETWEEN '" + fromDate + "' and  '" + toDate+"'  \r\n" + 
						"and cm.meterno in('"+meter_number+"')\r\n" + 
						"group by cm.meterno, cm.kwh,cm.billmonth,cm.rdate,lsmc.kwh )\r\n" + 
						"select billmonth,meterno, cons_kwh, load_kwh,cons_kwh- load_kwh as diff from cte where  rnk=1 \r\n" + 
						") select * from cte1  where diff>" + Hgthrlimit + " ";
			 //   System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
		else if(ruleid.equalsIgnoreCase("VEE15")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi...negative consumption.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "select meter_number, billing_date,kwh,pre_month,pre_month_kwh,(kwh-pre_month_kwh) as diff  from(\r\n" + 
						"with cte as (\r\n" + 
						"SELECT meter_number, billing_date,kwh, \r\n" + 
						"lead (billing_date, 1) OVER (PARTITION BY meter_number ORDER BY billing_date desc ) AS pre_month,\r\n" + 
						"lead (kwh, 1) OVER (PARTITION BY meter_number ORDER BY billing_date desc) AS pre_month_kwh,\r\n" + 
						"kwh - lead (kwh, 1) OVER ( PARTITION BY meter_number ORDER BY billing_date DESC) AS cur_prev_diff,\r\n" + 
						"rank()OVER (PARTITION BY meter_number ORDER BY billing_date desc) as rnk\r\n" + 
						"FROM meter_data.bill_history\r\n" + 
						"where date(billing_date)  BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in('"+meter_number+"')\r\n" + 
						"group by meter_number, kwh,billing_date) \r\n" + 
						"select meter_number, billing_date,kwh,pre_month,pre_month_kwh,cur_prev_diff, SIGN(cur_prev_diff) AS diff,rnk\r\n" + 
						"from cte where  rnk=1) cte1 where diff=-1 ";
//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }
	//spike check
		else if(ruleid.equalsIgnoreCase("VEE09")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				System.out.println("Hi...spike check.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = " SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,b.kwh_imp,b.leadkwh,date,mm.town_code,ABS(kwh_imp-leadkwh),(ABS((kwh_imp-leadkwh)/leadkwh)*100) as perc,(case \n" +
						"						 when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"						 when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"						 FROM meter_data.master_main mm RIGHT JOIN (select date,mtrno,kwh_imp,leadkwh,CASE \n" +
						"	WHEN (kwh_imp-leadkwh)<'"+Hgthrlimit+"'*(kwh_imp)  OR (leadkwh-kwh_imp)< '"+Hgthrlimit+"'*(kwh_imp)  THEN\n" +
						"		'1'\n" +
						"	ELSE\n" +
						"		'0'\n" +
						"END  as flag\n" +
						" from\n" +
						"(select  distinct date, mtrno,kwh_imp,lead(kwh_imp) over (ORDER BY kwh_imp) as leadkwh from meter_data.daily_consumption where   mtrno like '"+meter_number+"' \n" +
						"and  date BETWEEN '"+fromDate+"' and '"+toDate+"'\n" +
						")A)b on mm.mtrno=b.mtrno where flag = '1' ";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }	
		
		//repeated check
		else if(ruleid.equalsIgnoreCase("VEE10")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi...repeated  check.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql = "SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,e.flag,e.date,(case \n" +
						"	when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"	when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"	FROM meter_data.master_main mm \n" +
						"	\n" +
						"	 RIGHT JOIN\n" +
						"(select d.*,case when kwh_imp=d.oldkwh and kvah=oldkvah  and  kwh_imp=d.oldkwhimp  and kvah_imp=d.oldkvahimp and kw=d.oldkw  and kva=d.oldkva THEN\n" +
						"														'1'\n" +
						"													ELSE\n" +
						"														'0'\n" +
						"												END  as flag from \n" +
						"(select meter_number, kwh,lead(kwh) over (ORDER BY kwh) as oldkwh,kvah,lead(kvah) over (ORDER BY kvah) as oldkvah,kwh_imp,lead(kwh_imp) over (ORDER BY kwh_imp) as oldkwhimp,kvah_imp,lead(kvah_imp) over (ORDER BY kvah_imp) as oldkvahimp,kw,lead(kw) over (ORDER BY kw) as oldkw,kva,lead(kva) over (ORDER BY kva) as oldkva,date(read_time) from meter_data.load_survey where meter_number like '"+meter_number+"' and date(read_time)='2020-03-05')d limit 1 )e on mm.mtrno=e.meter_number ";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }	
		
		
		else if(ruleid.equalsIgnoreCase("VEE11")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			if (vpr != null) {
				//System.out.println("Hi...summation");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql =" SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,e.oldkwh,e.cum_active_import_energy,e.rtc_date_time,e.consumption,e.kwh_imp,e.flag,(case \n" +
						"	when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"	when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"	FROM meter_data.master_main mm \n" +
						"	\n" +
						"	\n" +
						"	RIGHT JOIN\n" +
						"						(select d.*,case when kwh_imp>d.consumption THEN\n" +
						"														'1'\n" +
						"													ELSE\n" +
						"														'0'\n" +
						"												END  as flag from  \n" +
						"						(select a.*,dc.kwh_imp,ABS((cum_active_import_energy- oldkwh))as consumption from  \n" +
						"						(select distinct  cum_active_import_energy,lead(cum_active_import_energy) over (ORDER BY cum_active_import_energy) as oldkwh,rtc_date_time,mtrno \n" +
						"						from meter_data.daily_load  where date(rtc_date_time) BETWEEN '"+fromDate+"' and '"+toDate+"' )a \n" +
						"						inner  JOIN (select  distinct mtrno,kwh_imp from meter_data.daily_consumption where mtrno like '"+meter_number+"' and date BETWEEN '"+fromDate+"' and '"+toDate+"' )dc on\n" +
						"						a.mtrno=dc.mtrno and a.mtrno like '"+meter_number+"' and \n" +
						"						a.oldkwh is not null ORDER BY dc.mtrno limit 1 )d)e on mm.mtrno=e.mtrno";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }	
		
		
		
		
		else if(ruleid.equalsIgnoreCase("VEE12")) {
			ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleid, meter_number,monthyr);
			System.out.println(meter_number);
			if (vpr != null) {
				//System.out.println("Hi... threshold.");
				fromDate=vpr.getFromDate();
				toDate=vpr.getToDate();
				Hgthrlimit=vpr.getHgthrlimit();
				Lwthrlimit=vpr.getLwthrlimit();
				String sql ="SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,s.date,s.kwh_imp,(case \n" +
						"				when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"				when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"				FROM meter_data.master_main mm \n" +
						"				\n" +
						"				 RIGHT JOIN\n" +
						"(select e.* from \n" +
						"(select d.*,case when kwh_imp>'"+Hgthrlimit+"' then '1' else '0' end as flag  from \n" +
						"(select kwh_imp,meter_number,date(read_time) from meter_data.load_survey where meter_number like '"+meter_number+"' and date(read_time)='"+fromDate+"')d)e where cast(flag as numeric ) =1)s\n" +
						"on mm.mtrno=s.meter_number";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				return list;
				
			} else {
				return null;
			}
	 }	
		
		return null;
		
		
		

}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> rtcFailureProcess(String fromDate, String toDate) {
		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE01");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl  = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();
			Boolean trg=v.getTrigger_v_rule();
		//	System.out.println("trgtrgtrgtrg=="+trg+" erulename== "+erulename);


			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

//		String sql = "select * from meter_data.amiinstantaneous where round(extract(epoch from (time_stamp - read_time))/60)>="+lowthrl+" and round(extract(epoch from (time_stamp - read_time))/60)<="+highthrl+" "
//				+ "and date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'";

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"+
						"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "+
						"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n"  
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code FROM meter_data.master_main mm WHERE mtrno in \r\n"
						+ "(\r\n" + "select DISTINCT ami.meter_number from meter_data.amiinstantaneous ami WHERE\r\n"
						+ "round(extract(epoch from (time_stamp - read_time))/60)>=" + highthrl
						+ " \r\n"
						+ "and date(read_time)  BETWEEN '" + fromDate + "' and  '" + toDate
						+ "' and ami.meter_number in (" + mtrno + ") \r\n" + ")";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							System.out.println("Hi....");
							// veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}
					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","RTC FAILURE PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> missingLoadDataProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE02");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			String meter_no="";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
					meter_no+= "" + item + "_";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}
				if (meter_no.endsWith("_")) {
					meter_no = meter_no.substring(0, meter_no.length() - 1);
				}

//				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
//						+ "when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n"
//						+ "when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n"
//						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
//						+ "SELECT meter_number,date(read_time),COUNT(*)  FROM meter_data.load_survey\r\n"
//						+ "WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ("
//						+ mtrno + ")  \r\n" + "GROUP BY meter_number,date(read_time) having count(*)=0 )b \r\n" + ")";
				
				String sql="SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n" + 
						"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "+
						"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" + 
						"when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n" + 
						"FROM meter_data.master_main mm WHERE mtrno in \r\n" + 
						"(select distinct meter_number from (\r\n" + 
						"select m.sdate , COALESCE (m.meterno,'')meter_number,\r\n" + 
						"CASE  WHEN n.count IS NULL THEN '0' ELSE n.count END count\r\n" + 
						"from \r\n" + 
						"(select date(d) as sdate, mn.meterno \r\n" + 
						"from  generate_series (CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate + "' as TIMESTAMP),  interval '1 day') d\r\n" + 
						" cross join ( select regexp_split_to_table( '"+meter_no+"', '_') as meterno) mn)m \r\n" + 
						" LEFT JOIN\r\n" + 
						"(SELECT meter_number,date(read_time) as rdate,COUNT(*) as count  FROM meter_data.load_survey\r\n" + 
						"WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ("+ mtrno + ")  \r\n" + 
						"GROUP BY meter_number,date(read_time) )n\r\n" + 
						"ON m.sdate=n.rdate and m.meterno=n.meter_number\r\n" + 
						"GROUP BY m.sdate,m.meterno,n.count\r\n" + 
						"having count is null or count=0)b )";
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {

						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","MISSING LOAD DATA PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> incmpltloadDataProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			//System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE03");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

//		String fromDate="";
//		String toDate="";

//		String sql="SELECT * FROM \r\n" + 
//				"(SELECT 'consumer' as cat, meterno FROM consumermaster union ALL\r\n" + 
//				"SELECT 'dt' as cat, meterno FROM dtdetails union ALL\r\n" + 
//				"SELECT 'feeder' as cat, meterno FROM feederdetails \r\n" + 
//				") a LEFT JOIN\r\n" + 
//				"(SELECT meter_number, date(read_time), COUNT(*) FROM meter_data.load_survey\r\n" + 
//				"WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'\r\n" + 
//				"GROUP BY meter_number, date(read_time) \r\n" + 
//				")b ON a.meterno=b.meter_number WHERE b.count is not null AND (count<>48 AND count<>96)";

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"+
						"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "+
						"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" 
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
						+ "SELECT meter_number,date(read_time),COUNT(*)  FROM meter_data.load_survey\r\n"
						+ "WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ("
						+ mtrno + ")   \r\n" + "GROUP BY meter_number,date(read_time) )b \r\n"
						+ "WHERE  (count<>48 AND count<>96))";

				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","INCMPLT LOAD DATA PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> missingEventDataProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE04");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";		
			
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			String meter_no="";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
					meter_no+= "" + item + "_";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}
				if (meter_no.endsWith("_")) {
					meter_no = meter_no.substring(0, meter_no.length() - 1);
				}

//				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
//						+ "when mm.fdrcategory in('FEEDER METER','BORDER METER') then 'FEEDER'  \r\n"
//						+ "when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n"
//						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
//						+ "SELECT meter_number, date(event_time), COUNT(*) FROM meter_data.events\r\n"
//						+ "WHERE date(event_time) BETWEEN '" + fromDate + "' and  '" + toDate
//						+ "' and meter_number in (" + mtrno + ") \r\n"
//						+ "GROUP BY meter_number,date(event_time) )b \r\n" + "WHERE b.count is null) ";
				
				
				String sql= "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n" + 
						"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "+
						"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" + 
						"when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n" + 
						"FROM meter_data.master_main mm WHERE mtrno in \r\n" + 
						"(select distinct meter_number from (\r\n" + 
						"select m.sdate , COALESCE (m.meterno,'')meter_number,\r\n" + 
						"CASE  WHEN n.count IS NULL THEN '0' ELSE n.count END count\r\n" + 
						"from (select date(d) as sdate, mn.meterno \r\n" + 
						"from  generate_series (CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate+"' as TIMESTAMP),  interval '1 day') d\r\n" + 
						" cross join ( select regexp_split_to_table( '"+meter_no+"', '_') as meterno) mn)m \r\n" + 
						" LEFT JOIN\r\n" + 
						"(SELECT meter_number,date(event_time) as edate,COUNT(*)  FROM meter_data.events\r\n" + 
						"WHERE date(event_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in (" + mtrno + ")  GROUP BY meter_number,date(event_time) )n\r\n" + 
						"ON m.sdate=n.edate and m.meterno=n.meter_number\r\n" + 
						"GROUP BY m.sdate,m.meterno,n.count\r\n" + 
						"having count is null or count=0 )b )";
//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","MISSING EVENT DATA PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}

//		String sql="SELECT * FROM \r\n" + 
//				"(SELECT 'CONSUMER' as cat, meterno FROM consumermaster union ALL\r\n" + 
//				"SELECT 'DT' as cat, meterno FROM dtdetails union ALL\r\n" + 
//				"SELECT 'FEEDER' as cat, meterno FROM feederdetails \r\n" + 
//				") a LEFT JOIN\r\n" + 
//				"(SELECT meter_number, date(event_time), COUNT(*) FROM meter_data.events\r\n" + 
//				"WHERE date(event_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'\r\n" + 
//				"GROUP BY meter_number, date(event_time) \r\n" + 
//				")b ON a.meterno=b.meter_number WHERE b.meter_number is NULL";

	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> invalidPowerfactorProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE06");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"+
						"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "+
						"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" 
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
						+ "SELECT meter_number, date(read_time) FROM meter_data.amiinstantaneous\r\n"
						+ "WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ("
						+ mtrno + ")  and pf_threephase not between -1 and 1\r\n"
						+ "GROUP BY meter_number, date(read_time) )b)";

				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;

			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","INVALID POWER FACTOR PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> lessKVAValueProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			//System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE07");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
						+"when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "
						+"when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" 
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
						+ "SELECT meter_number, date(billing_date) FROM meter_data.bill_history\r\n"
						+ "WHERE date(billing_date) BETWEEN '" + fromDate + "' and  '" + toDate
						+ "' and meter_number in (" + mtrno + ")  and kva<=demand_kw\r\n"
						+ "GROUP BY meter_number, date(billing_date)\r\n" + ")b)";

//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
//			dec.Servicelog("DATA VALIDATION AND ESIMATION","LESS KVA VALUE PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}

	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> theresoldFreqCheckProcess(String fromDate, String toDate) {

//	String sql="SELECT a.Location_Type,a.meterno,b.date FROM \r\n" + 
//			"(SELECT 'CONSUMER' as Location_Type, meterno FROM meter_data.consumermaster union ALL\r\n" + 
//			"SELECT 'DT' as Location_Type, meterno FROM meter_data.dtdetails union ALL\r\n" + 
//			"SELECT 'FEEDER' as Location_Type, meterno FROM meter_data.feederdetails \r\n" + 
//			") a LEFT JOIN\r\n" + 
//			"(SELECT meter_number, date(read_time) FROM meter_data.amiinstantaneous\r\n" + 
//			"WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and frequency not between "+lowthrl+" and "+highthrl+"\r\n" + 
//			"GROUP BY meter_number, date(read_time)\r\n" + 
//			")b ON a.meterno=b.meter_number WHERE b.meter_number is not NULL";

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE08");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
						+ "when mm.fdrcategory in('FEEDER METER') then 'FEEDER' "
						+ "when mm.fdrcategory in('BOUNDARY METER') then 'BOUNDARY METER' \r\n" 
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type,mm.town_code \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from (\r\n"
						+ "SELECT meter_number, date(read_time) FROM meter_data.amiinstantaneous\r\n"
						+ "WHERE date(read_time)BETWEEN '" + fromDate + "' and  '" + toDate + "' and meter_number in ("
						+ mtrno + ") and frequency not between " + lowthrl + " and " + highthrl + "\r\n"
						+ "GROUP BY meter_number, date(read_time)\r\n" + ")b)";

//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setTown_code((String) item[8]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","THERESOLD FREQ CHECK PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}

	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> meterDigitComplyingProcess(String fromDate, String toDate) {

//	String fromDate="";
//	String toDate="";

		String sql = "";

		//System.out.println(sql);
		List<?> list = entityManager.createNativeQuery(sql).getResultList();
		//System.err.println(list.size());
		//System.err.println(list.isEmpty());
		if (!list.isEmpty()) {
			int i = 0;
			for (Object item : list) {
				//System.err.println("meterDigitComplyingProcess" + i++);
			}
		}
		return list;
	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> zeroConsValidProcess(String fromDate, String toDate) {

//	String sql="SELECT accno,meterno, billmonth,kwh\r\n" + 
//			"FROM meter_data.consumerreadings\r\n" + 
//			"where date(rdate)  BETWEEN '"+fromDate+"' and  '"+toDate+"'  and kwh=0\r\n" + 
//			"group by accno,meterno, kwh,billmonth";

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE13");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

//				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
//						+ "when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n"
//						+ "when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n"
//						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(\r\n" + "select distinct meterno from (\r\n"
//						+ "SELECT accno,meterno, billmonth,kwh\r\n" + "FROM meter_data.consumerreadings\r\n"
//						+ "where date(rdate) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meterno in ("
//						+ mtrno + ")  and kwh=0\r\n" + "group by accno,meterno, kwh,billmonth\r\n" + ")b)";
				
				String sql="SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \n" +
						"when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"FROM meter_data.master_main mm WHERE mtrno in \n" +
						"(\n" +
						"select distinct meterno from (\n" +
						"select * from(with cte as (\n" +
						"SELECT accno,meterno, billmonth,kwh, lead (billmonth, 1) OVER (\n" +
						"PARTITION BY meterno ORDER BY billmonth desc) AS pre_month,\n" +
						"lead (kwh, 1) OVER (\n" +
						"PARTITION BY meterno ORDER BY kwh desc) AS pre_month_kwh,\n" +
						"kwh - lead (kwh, 1) OVER ( PARTITION BY meterno ORDER BY billmonth DESC) AS cur_prev_diff,\n" +
						"rank()OVER (PARTITION BY meterno ORDER BY billmonth desc) as rnk\n" +
						"FROM meter_data.consumerreadings\n" +
						"where date(rdate) BETWEEN '" + fromDate + "' and  '" + toDate + "' and meterno in ("+ mtrno + ") \n" +
						"group by accno,meterno, kwh,billmonth\n" +
						") select * from cte where rnk=1\n" +
						") cte1 where cur_prev_diff='0'\n" +
						")b)";

//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","ZERO CONS VALID PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> energySumCheckProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//	System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE14");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
						+ "when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n"
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n"
						+ "(with cte1 as(with cte as( SELECT cm.meterno, cm.billmonth,cm.kwh, lsmc.kwh as load_kwh,\r\n"
						+ "lead (cm.billmonth, 1) OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc ) AS pre_month,\r\n"
						+ "lead (cm.kwh, 1) OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc) AS pre_month_kwh,\r\n"
						+ "cm.kwh - lead (cm.kwh, 1) OVER ( PARTITION BY cm.meterno ORDER BY cm.rdate DESC) AS cons_kwh,\r\n"
						+ "rank()OVER (PARTITION BY cm.meterno ORDER BY cm.rdate desc) as rnk\r\n"
						+ "FROM meter_data.consumerreadings cm, meter_data.load_survey_monthly_consumption lsmc\r\n"
						+ "where lsmc.mtrno=cm.meterno and lsmc.billmonth=cm.billmonth and date(cm.rdate) BETWEEN '"
						+ fromDate + "' and  '" + toDate + "' and cm.meterno in (" + mtrno + ")  \r\n"
						+ "group by cm.meterno, cm.kwh,cm.billmonth,cm.rdate,lsmc.kwh )\r\n"
						+ "select billmonth,meterno, cons_kwh, load_kwh,cons_kwh- load_kwh as diff from cte where  rnk=1 \r\n"
						+ ") select distinct meterno from cte1 where diff>" + highthrl + ")";

//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","ENERGY SUM CHECK PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			
			
			return null;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> negativeConsumptionProcess(String fromDate, String toDate) {

//	String sql="select meter_number, billing_date,kwh,pre_month,pre_month_kwh,cur_prev_diff from(with cte as (\r\n" + 
//			"SELECT meter_number, billing_date,kwh, lead (billing_date, 1) OVER (\r\n" + 
//			"PARTITION BY meter_number ORDER BY billing_date desc ) AS pre_month,\r\n" + 
//			"lead (kwh, 1) OVER (\r\n" + 
//			"PARTITION BY meter_number ORDER BY billing_date desc) AS pre_month_kwh,\r\n" + 
//			"kwh - lead (kwh, 1) OVER ( PARTITION BY meter_number ORDER BY billing_date DESC) AS cur_prev_diff,\r\n" + 
//			"rank()OVER (PARTITION BY meter_number ORDER BY billing_date desc) as rnk\r\n" + 
//			"FROM meter_data.bill_history\r\n" + 
//			"where date(billing_date) BETWEEN '"+fromDate+"' and  '"+toDate+"' \r\n" + 
//			"group by meter_number, kwh,billing_date\r\n" + 
//			") select meter_number, billing_date,kwh,pre_month,pre_month_kwh,cur_prev_diff, SIGN(cur_prev_diff) AS diff,rnk\r\n" + 
//			"from cte where  rnk=1) cte1 where diff=-1";

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			//System.out.println("mnthyr==" + mnthyr);

			VeeRuleEntity v = addveeservice.getVeeRuleById("VEE15");
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
//			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n"
						+ "when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n"
						+ "when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \r\n"
						+ "FROM meter_data.master_main mm WHERE mtrno in \r\n" + "(select distinct meter_number from(\r\n"
						+ "with cte as (SELECT meter_number, billing_date,kwh, \r\n"
						+ "lead (billing_date, 1) OVER (PARTITION BY meter_number ORDER BY billing_date desc ) AS pre_month,\r\n"
						+ "lead (kwh, 1) OVER (PARTITION BY meter_number ORDER BY billing_date desc) AS pre_month_kwh,\r\n"
						+ "kwh - lead (kwh, 1) OVER ( PARTITION BY meter_number ORDER BY billing_date DESC) AS cur_prev_diff,\r\n"
						+ "rank()OVER (PARTITION BY meter_number ORDER BY billing_date desc) as rnk\r\n"
						+ "FROM meter_data.bill_history\r\n" + "where date(billing_date) BETWEEN '" + fromDate
						+ "' and  '" + toDate + "' \r\n" + "group by meter_number, kwh,billing_date) \r\n"
						+ "select meter_number, billing_date,kwh,pre_month,pre_month_kwh,cur_prev_diff, SIGN(cur_prev_diff) AS diff,rnk\r\n"
						+ "from cte where  rnk=1) cte1 where diff=-1 and meter_number in (" + mtrno + "))";

//				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[7]);
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
            Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","NEGATIVE CONSUMPTION PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			return null;
		}
	}
	
	//spike check 
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> spikeCheckProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			System.out.println("mnthyr==" + mnthyr);
			VeeRuleEntity v = null;

			String id = "VEE09";
			
			 v = addveeservice.getVeeRuleById(id);
			
			
		//	VeeRuleEntity v = addveeservice.getVeeRule(id);
			
			
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();
			System.out.println(ruleId + rule_name +lowthrl+highthrl+erulename);

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='VEE09' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql = "SELECT  distinct mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,b.kwh_imp,b.leadkwh,b.flag,date, mm.town_code,(case \n" +
						"						 when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"						 when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"						 FROM meter_data.master_main mm RIGHT JOIN (select date,mtrno,kwh_imp,leadkwh,CASE \n" +
						"	WHEN (kwh_imp-leadkwh)<'"+highthrl+"'*(kwh_imp) or  (leadkwh-kwh_imp)<'"+highthrl+"'*(kwh_imp)  THEN\n" +
						"		'1'\n" +
						"	ELSE\n" +
						"		'0'\n" +
						"END  as flag\n" +
						" from\n" +
						"(select  distinct date, mtrno,kwh_imp,lead(kwh_imp) over (ORDER BY kwh_imp) as leadkwh from meter_data.daily_consumption where   mtrno  in (" + mtrno + ") \n" +
						"and  date BETWEEN '"+fromDate+"' and '"+toDate+"'\n" +
						")A)b on mm.mtrno=b.mtrno where flag = '1'";

				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[14]);
							vprpt.setTown_code((String) item[11]); 
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
          //  Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
		//	dec.Servicelog("DATA VALIDATION AND ESIMATION","NEGATIVE CONSUMPTION PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			return null;
		}
	}
	
	//summationCheckProcess
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> summationCheckProcess(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			System.out.println("mnthyr==" + mnthyr);
			VeeRuleEntity v = null;

			String id = "VEE10";
			
			 v = addveeservice.getVeeRuleById(id);
			
			
		//	VeeRuleEntity v = addveeservice.getVeeRule(id);
			
			
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();
			System.out.println(ruleId + rule_name +lowthrl+highthrl+erulename);

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='VEE10' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql =" SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,e.oldkwh,e.cum_active_import_energy,e.rtc_date_time,e.consumption,e.kwh_imp,e.flag,(case \n" +
						"	when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \n" +
						"	when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \n" +
						"	FROM meter_data.master_main mm \n" +
						"	\n" +
						"	\n" +
						"	RIGHT JOIN\n" +
						"						(select d.*,case when kwh_imp>d.consumption THEN\n" +
						"														'1'\n" +
						"													ELSE\n" +
						"														'0'\n" +
						"												END  as flag from  \n" +
						"						(select a.*,dc.kwh_imp,ABS((cum_active_import_energy- oldkwh))as consumption from  \n" +
						"						(select distinct  cum_active_import_energy,lead(cum_active_import_energy) over (ORDER BY cum_active_import_energy) as oldkwh,rtc_date_time,mtrno \n" +
						"						from meter_data.daily_load  where date(rtc_date_time) BETWEEN '"+fromDate+"' and '"+toDate+"' )a \n" +
						"						inner  JOIN (select  distinct mtrno,kwh_imp from meter_data.daily_consumption where mtrno in (" + mtrno + ")and date between '"+fromDate+"' and '"+toDate+"' )dc on\n" +
						"						a.mtrno=dc.mtrno and a.mtrno like in (" + mtrno + ") and \n" +
						"						a.oldkwh is not null ORDER BY dc.mtrno limit 1 )d)e on mm.mtrno=e.mtrno";

				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[13]);
							vprpt.setTown_code((String) item[7]); 
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
          //  Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
		//	dec.Servicelog("DATA VALIDATION AND ESIMATION","NEGATIVE CONSUMPTION PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			return null;
		}
	}
	
	//repeatedvaluecheck
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> repeatedValueCheck(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			System.out.println("mnthyr==" + mnthyr);
			VeeRuleEntity v = null;

			String id = "VEE11";
			
			 v = addveeservice.getVeeRuleById(id);
			
			
		//	VeeRuleEntity v = addveeservice.getVeeRule(id);
			
			
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();
			System.out.println(ruleId + rule_name +lowthrl+highthrl+erulename);

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='VEE11' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql ="SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,e.flag,(case  \r\n" + 
						"													when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'   \r\n" + 
						"													when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type  \r\n" + 
						"													FROM meter_data.master_main mm  \r\n" + 
						"													 RIGHT JOIN \r\n" + 
						"													  \r\n" + 
						"												(select d.*,case when kwh_imp=d.oldkwh  and kwh_imp=d.newkwh then  \r\n" + 
						"																										'1' \r\n" + 
						"																									ELSE \r\n" + 
						"																										'0' \r\n" + 
						"																								END  as flag from  \r\n" + 
						"												(select mtrno, kwh_imp,lead(kwh_imp) over (ORDER BY kwh_imp) as oldkwh,lag(kwh_imp) over (ORDER BY kwh_imp ) as newkwh from meter_data.daily_consumption where mtrno in ("+mtrno+") and  date(create_time)between  '"+fromDate+"' and '"+toDate+"' )d limit 1 )e on mm.mtrno=e.mtrno where flag='1'";					
						
						
						
//						"SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,e.flag,(case \r\n" + 
//						"							when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n" + 
//						"							when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \r\n" + 
//						"							FROM meter_data.master_main mm \r\n" + 
//						"							 RIGHT JOIN\r\n" + 
//						"							 \r\n" + 
//						"						(select d.*,case when kwh=d.oldkwh  and kwh=d.newkwh then \r\n" + 
//						"																				'1'\r\n" + 
//						"																			ELSE\r\n" + 
//						"																				'0'\r\n" + 
//						"																		END  as flag from \r\n" + 
//						"						(select meter_number, kwh,lead(kwh) over (ORDER BY kwh) as oldkwh,lag(kwh) over (ORDER BY kwh ) as newkwh from meter_data.load_survey where meter_number in ("+mtrno+") and  date(read_time) = '"+fromDate+"' )d limit 1 )e on mm.mtrno=e.meter_number";
//				
				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[9]);
							vprpt.setTown_code((String) item[7]); 
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
          //  Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
		//	dec.Servicelog("DATA VALIDATION AND ESIMATION","NEGATIVE CONSUMPTION PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			return null;
		}
	}
	
	
	//Thresholdlimitcheck
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> thresholdLimitCheck(String fromDate, String toDate) {

		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			System.out.println("mnthyr==" + mnthyr);
			VeeRuleEntity v = null;

			String id = "VEE12";
			
			 v = addveeservice.getVeeRuleById(id);
			
			
		//	VeeRuleEntity v = addveeservice.getVeeRule(id);
			
			
			String ruleId = v.getRuleid();
			String rule_name = v.getRulename();
			Double lowthrl = v.getLwthrlimit();
			Double highthrl = v.getHgthrlimit();
			String erulename = v.getErulename();
			System.out.println(ruleId + rule_name +lowthrl+highthrl+erulename);

//			String sql0 = "select mm.mtrno from meter_data.ml_to_validation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and v_rule_id='"
//					+ ruleId + "'";
			
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_validation_rule_map vrm\r\n" + 
					"where  status=1 and v_rule_id='VEE12' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
			System.out.println(sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}

				String sql ="SELECT distinct  mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,mm.town_code,(case \r\n" + 
						"				when mm.fdrcategory in('FEEDER METER','BOUNDARY METER') then 'FEEDER'  \r\n" + 
						"				when mm.fdrcategory='DT' then 'DT' else 'CONSUMER' end) as location_type \r\n" + 
						"				FROM meter_data.master_main mm \r\n" + 
						"				\r\n" + 
						"				 RIGHT JOIN\r\n" + 
						"(select e.* from \r\n" + 
						"(select d.*,case when kwh_imp>("+highthrl+") then '1' else '0' end as flag  from \r\n" + 
						"\r\n" + 
						"(select kwh_imp,meter_number,date(read_time) from meter_data.load_survey where meter_number in (" + mtrno + ") and date(read_time)='"+fromDate+"')d)e where cast(flag as numeric ) =1)s\r\n" + 
						"\r\n" + 
						"on mm.mtrno=s.meter_number";

				System.out.println(sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {
						ValidationProcessReportEntity vpr = veeprocservice.getAssignRuleId(ruleId, (String) item[0],
								mnthyr);
						if (vpr != null) {
							veeprocservice.update(vpr);
						} else {
							ValidationProcessReportEntity vprpt = new ValidationProcessReportEntity();
							vprpt.setMyKey(new KeyValidationUniqueId(ruleId, (String) item[0], mnthyr));
							vprpt.setRulename(rule_name);
							vprpt.setLocation_type((String) item[8]);
							vprpt.setTown_code((String) item[7]); 
							vprpt.setLocation_id((String) item[1]);
							vprpt.setLocation_name((String) item[6]);
							vprpt.setZone((String) item[2]);
							vprpt.setCircle((String) item[3]);
							vprpt.setDivision((String) item[4]);
							vprpt.setSubdivision((String) item[5]);
							vprpt.setFromDate(fromDate);
							vprpt.setToDate(toDate);
							vprpt.setHgthrlimit(highthrl);
							vprpt.setLwthrlimit(lowthrl);
							veeprocservice.update(vprpt);
						}

					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
          //  Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
		//	dec.Servicelog("DATA VALIDATION AND ESIMATION","NEGATIVE CONSUMPTION PROCESS","VALIDATE METER DATA",e.toString(),lastmodfydate);
			return null;
		}
	}
	
	
	@RequestMapping(value="/getEstimationRules/{ruleId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getEstimationRulesByRuleId(@PathVariable String ruleId,HttpServletRequest request){
		//System.out.println("ruleId======="+ruleId);
		List<String> list=new ArrayList<>();
		list=veeprocservice.getEstimationRules(ruleId);
		//System.out.println(list.size());
		return list;
	}
	
	@RequestMapping(value = "/manualEstimationAndEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String manualEstimationAndEdit(HttpServletRequest request, Model model) {
		// missingLoadDataProcess("2019-03-01","2019-03-31");
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "manualEstimationAndEdit";
	}
	
	

}
