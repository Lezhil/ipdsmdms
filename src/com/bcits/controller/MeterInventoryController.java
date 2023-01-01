package com.bcits.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.DashboardService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.utility.MDMLogger;

@Controller
public class MeterInventoryController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private MeterInventoryService meterInventoryService;

	@Autowired
	private MasterMainService masterMainService;
	@Autowired
	DashboardService ds;

	static String result = "";

@RequestMapping(value="/meterDetails",method={RequestMethod.GET,RequestMethod.POST})	
public String meterDetails(@ModelAttribute("addMeterDetailsFormId") MeterInventoryEntity meterInventory,ModelMap model,HttpSession session){
	String pname=(String) session.getAttribute("projectName");
	 System.out.println("prject name--"+pname);
	 String officeType = (String) session.getAttribute("officeType");
	 String circle =(String) session.getAttribute("officeName");
	 String region = (String) session.getAttribute("newRegionName");
	 	 
	List<MeterInventoryEntity> totalMtrs=meterInventoryService.getALLMeterDetails();
	model.put("curMonth", new SimpleDateFormat("yyyyMM").format(new Date()));
	model.put("curYear", new SimpleDateFormat("yyyy").format(new Date()));
	model.put("meterDetails",totalMtrs );
	//String cuurentDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	//model.put("currentDate", cuurentDate);
	//System.out.println(cuurentDate);
	//model.put("totalMtrs", totalMtrs.size());
	//model.put("totalnoi", meterInventoryService.totalInstalledMeters());
	int count=0;
	int intstalled=0;
	String qry="select count(*)as mtrcnt from meter_data.master_main ";
	Query q = entityManager.createNativeQuery(qry);
	Object list1 = q.getSingleResult();
	model.put("totalMetersForMeterDetails",list1);
	
	if(officeType.equalsIgnoreCase("corporate")) {
	 meterInventoryService.totalInstalledMeters(model);
	 List<Object []> list = ds.unmappedMeters();
		count=list.size();
		model.put("unmapped",count);
	}else{
		 	
		 meterInventoryService.totalInstalledMetersForCirclelvl(model, officeType, circle, region);
		 List<Object []> list = ds.unmappedMeters();
			count=list.size();
			model.put("unmapped",count);
	}
	
	 
	model.put("result", result);
	result="";
	return "meterInventory";
}

@RequestMapping(value="/addMeterInventoryDetails",method={RequestMethod.GET,RequestMethod.POST})	
public String addMeterInventoryDetails(@ModelAttribute("addMeterDetailsFormId") MeterInventoryEntity meterInventory,ModelMap model,HttpServletRequest request){
	long currtime=System.currentTimeMillis();
	try {
			MeterInventoryEntity inventory=new MeterInventoryEntity();
			inventory.setMeterno(meterInventory.getMeterno());
			if(meterInventory.getMeter_connection_type()==1)
			{
				inventory.setMeter_connection_type(meterInventory.getMeter_connection_type());
				inventory.setConnection_type("1");
			}
			if(meterInventory.getMeter_connection_type()==33)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("33");
			}
			if(meterInventory.getMeter_connection_type()==34)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("34");
			}
			
			inventory.setMeter_make(meterInventory.getMeter_make());
			inventory.setMeter_accuracy_class(meterInventory.getMeter_accuracy_class());
			inventory.setMeter_commisioning(meterInventory.getMeter_commisioning());
			inventory.setMeter_constant(meterInventory.getMeter_constant());
			inventory.setMeter_current_rating(meterInventory.getMeter_current_rating());
			inventory.setMeter_ip_period(meterInventory.getMeter_ip_period());
			inventory.setMeter_model(meterInventory.getMeter_model());
			inventory.setMeter_status(meterInventory.getMeter_status());
			inventory.setMeter_voltage_rating(meterInventory.getMeter_voltage_rating());
			inventory.setMonth(meterInventory.getMonth());
			inventory.setCt_ratio(meterInventory.getCt_ratio());
			inventory.setPt_ratio(meterInventory.getPt_ratio());
			inventory.setTender_no(meterInventory.getTender_no());
			inventory.setWarrenty_years(meterInventory.getWarrenty_years());
			inventory.setEntryby(request.getSession().getAttribute("username")+"");
			inventory.setEntrydate(new Timestamp(currtime));
			inventory.setStrLoc(meterInventory.getStrLoc());
			inventory.setStrDesc(meterInventory.getStrDesc());
			inventory.setMeterdigit(meterInventory.getMeterdigit());
			System.out.println(inventory.toString());
			meterInventoryService.save(inventory);
			result="Data Saved Successfully";
	} catch (Exception e) {
	e.printStackTrace();
	result=e.getMessage();
	}
	return "redirect:/meterDetails";
}

//get MeterDetails Data For Edit
@RequestMapping(value="/getMeterInventoryData/{operation}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object retriveMrName(@PathVariable Long operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit MeterDetails   ");
		MeterInventoryEntity result=new MeterInventoryEntity();
		try {
			result = meterInventoryService.find(operation);
			
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	//check Meterno Exist or not
	@RequestMapping(value="/checkMeterNoInInventory/{meterno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object retriveMrName(@PathVariable String meterno,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		return meterInventoryService.meterNoExistOrNot(meterno.trim());
	}
	
	//update Meter Inventory Details
	@RequestMapping(value="/updateMeterInventoryDetails",method={RequestMethod.GET,RequestMethod.POST})	
	public String updateMeterInventoryDetails(@ModelAttribute("addMeterDetailsFormId") MeterInventoryEntity meterInventory,ModelMap model,HttpServletRequest request){
		long currtime=System.currentTimeMillis();
	try {
			MeterInventoryEntity inventory=meterInventoryService.find(meterInventory.getId());
			if(meterInventory.getMeterno()!=null){
			inventory.setMeterno(meterInventory.getMeterno());
			if(meterInventory.getMeter_connection_type()==1)
			{
				inventory.setMeter_connection_type(meterInventory.getMeter_connection_type());
				inventory.setConnection_type("1");
			}
			if(meterInventory.getMeter_connection_type()==33)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("33");
			}
			if(meterInventory.getMeter_connection_type()==34)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("34");
			}
			inventory.setMeter_connection_type(meterInventory.getMeter_connection_type());
			inventory.setMeter_make(meterInventory.getMeter_make());
			inventory.setMeter_accuracy_class(meterInventory.getMeter_accuracy_class());
			inventory.setMeter_commisioning(meterInventory.getMeter_commisioning());
			inventory.setMeter_constant(meterInventory.getMeter_constant());
			inventory.setMeter_current_rating(meterInventory.getMeter_current_rating());
			inventory.setMeter_ip_period(meterInventory.getMeter_ip_period());
			inventory.setMeter_model(meterInventory.getMeter_model());
			inventory.setMeter_status(meterInventory.getMeter_status());
			inventory.setMeter_voltage_rating(meterInventory.getMeter_voltage_rating());
			inventory.setMonth(meterInventory.getMonth());
			inventory.setCt_ratio(meterInventory.getCt_ratio());
			inventory.setPt_ratio(meterInventory.getPt_ratio());
			inventory.setTender_no(meterInventory.getTender_no());
			inventory.setWarrenty_years(meterInventory.getWarrenty_years());
			inventory.setMeterdigit(meterInventory.getMeterdigit());
			inventory.setStrLoc(meterInventory.getStrLoc());
			inventory.setStrDesc(meterInventory.getStrDesc());
			inventory.setUpdateby(request.getSession().getAttribute("username")+"");
			inventory.setUpdatedate(new Timestamp(currtime));
			meterInventoryService.update(inventory);
			result="Data Updated Successfully";
			}
	} catch (Exception e) {
		
	e.printStackTrace();
	result=e.getMessage();
	}
	return "redirect:/meterDetails";
}
@RequestMapping(value="/addBatchMeterInventoryDetails",method={RequestMethod.GET,RequestMethod.POST})	
public String addBatchMeterInventoryDetails(@ModelAttribute("addMeterDetailsFormId") MeterInventoryEntity meterInventory,ModelMap model,HttpServletRequest request){
	long currtime=System.currentTimeMillis();
	try {
			List<String> mtrList=new ArrayList<String>();
			List<MeterInventoryEntity> list=new ArrayList<MeterInventoryEntity>();
			int start=Integer.parseInt(request.getParameter("meternoStartId"));
			int end=Integer.parseInt(request.getParameter("meternoEndId"));
			String prefix=request.getParameter("mtrPrefix");
			for (int i = start; i <=end; i++) {
				
				String meterno="";
				meterno=prefix+String.valueOf(i);
				List<MeterInventoryEntity> result=meterInventoryService.meterNoExistOrNot(meterno);
				if(result.size()>0)
				{
					mtrList.add(meterno);
				}
			}
			for (int i = start; i <=end; i++) {
			String meterno="";
			meterno=prefix+String.valueOf(i);
			MeterInventoryEntity inventory=new MeterInventoryEntity();
			if(mtrList.contains(meterno)){
			continue;
			}
			inventory.setMeterno(meterno);
			//inventory.setMeter_connection_type(meterInventory.getMeter_connection_type());
			if(meterInventory.getMeter_connection_type()==1)
			{
				inventory.setMeter_connection_type(meterInventory.getMeter_connection_type());
				inventory.setConnection_type("1");
			}
			if(meterInventory.getMeter_connection_type()==33)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("33");
			}
			if(meterInventory.getMeter_connection_type()==34)
			{
				inventory.setMeter_connection_type((short) 3);
				inventory.setConnection_type("34");
			}
			inventory.setMeter_make(meterInventory.getMeter_make());
			inventory.setMeter_accuracy_class(meterInventory.getMeter_accuracy_class());
			inventory.setMeter_commisioning(meterInventory.getMeter_commisioning());
			inventory.setMeter_constant(meterInventory.getMeter_constant());
			inventory.setMeter_current_rating(meterInventory.getMeter_current_rating());
			inventory.setMeter_ip_period(meterInventory.getMeter_ip_period());
			inventory.setMeter_model(meterInventory.getMeter_model());
			inventory.setMeter_status(meterInventory.getMeter_status());
			inventory.setMeter_voltage_rating(meterInventory.getMeter_voltage_rating());
			inventory.setMonth(meterInventory.getMonth());
			inventory.setCt_ratio(meterInventory.getCt_ratio());
			inventory.setPt_ratio(meterInventory.getPt_ratio());
			inventory.setTender_no(meterInventory.getTender_no());
			inventory.setWarrenty_years(meterInventory.getWarrenty_years());
			inventory.setEntryby(request.getSession().getAttribute("username")+"");
			inventory.setEntrydate(new Timestamp(currtime));
			inventory.setStrLoc(meterInventory.getStrLoc());
			inventory.setStrDesc(meterInventory.getStrDesc());
			inventory.setMeterdigit(meterInventory.getMeterdigit());
			list.add(inventory);
			}
			meterInventoryService.saveBatchInventory(list);
			result="Data Saved Successfully";
	} catch (Exception e) {
	e.printStackTrace();
	result=e.getMessage();
	
	}
	return "redirect:/meterDetails";
}

/*//meter Allocation to MR
@RequestMapping(value="/mrAllocation",method={RequestMethod.GET,RequestMethod.POST})	
public String mrAllocation(HttpServletRequest request,ModelMap model){
	
	System.out.println("inside mrrrrrrrr");
	//List<MeterInventoryEntity> totalMtrs=meterInventoryService.getALLInstockMeters();
	List<MeterInventoryEntity> totalMtrs=meterInventoryService.getALLMeterDetails();
	List<?> mrcodes=meterInventoryService.getAllMrcodes();
	model.put("meterDetails",totalMtrs );
	model.put("mrcodes",mrcodes );
	return "mrAllocation";
}

@RequestMapping(value="/getMRnameBymrcode",method={RequestMethod.POST,RequestMethod.GET})
public @ResponseBody String getMRnameBymrcode(HttpServletResponse response,HttpServletRequest request) 
{
	System.out.println("inside get Mrname");
	String mrcode=request.getParameter("mrcode");
	return meterInventoryService.getMRnameByMrcode(mrcode);
}*/
	
	@RequestMapping(value="/checkMeterNoInMasterMain/{meterno}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody MasterMainEntity getMRnameBymrcode(@PathVariable String meterno,HttpServletResponse response,HttpServletRequest request) 
	{
		
		System.out.println("inside  checkMeterNoInMasterMain");
		/*try {*/
			return masterMainService.getEntityByMtrNO(meterno);
		/*} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		*/
	}
	//check Meterno Exist or not
		@RequestMapping(value="/getMetersBasedOnStatus",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getMetersBasedOnStatus(HttpServletResponse response,HttpServletRequest request,ModelMap model,HttpSession session) 
		{
			Object obj=new Object();
			Object li =new ArrayList<>();
			String status=request.getParameter("status");
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			String officeType = (String) session.getAttribute("officeType");
			 String circle =(String) session.getAttribute("officeName");
			 String region = (String) session.getAttribute("newRegionName");		
			 
			 //if(status.equalsIgnoreCase("INSTOCK")||status.equalsIgnoreCase("DAMAGED")){
				if(officeType.equalsIgnoreCase("corporate")) {

					li =  meterInventoryService.getMetersBasedOnStatus(status);
				}else {
					li = meterInventoryService.getMetersBasedOnStatusandLoginlvl(status, officeType, region, circle);

				}
				//System.out.println(li);
				return li;
			//}
			//if(status.equalsIgnoreCase("ISSUED")||status.equalsIgnoreCase("INSTALLED")){
			//	return meterInventoryService.getissuedAndInstalledMetrdata(status,fromDate,toDate);
			//}
			//return obj;
			
		}
		
		//check Meterno Exist or not
		@RequestMapping(value="/getUnmappedMetersData",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getUnmappedMetersData(HttpServletResponse response,HttpServletRequest request,ModelMap model,HttpSession session) 
		{
			Object obj=new Object();
			Object li =new ArrayList<>();
					li =  meterInventoryService.getUnmappedMetersData();
				
				//System.out.println(li);
				return li;
			
			
		}
		
		//check Meterno Exist or not
		@RequestMapping(value="/getMeterInventoryBasedOnData",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getMeterInventoryBasedOnData(@RequestParam String data,@RequestParam String parameter,HttpServletResponse response,HttpServletRequest request,ModelMap model,HttpSession session) throws JsonParseException, JsonMappingException, IOException
		{
			
			List<?> li =new ArrayList<>();
			
		String officeType = (String) session.getAttribute("officeType");
		String circle = (String) session.getAttribute("officeName");
		String region = (String) session.getAttribute("newRegionName");
			
		if(officeType.equalsIgnoreCase("corporate")) {
			li= meterInventoryService.getMeterInventoryBasedOnData(data,parameter);

		}else {
			
			li= meterInventoryService.getMeterInventoryBasedOnDataByLoginLvl(data,parameter,circle,region,officeType);

		}
		
		return li;
		}
		
		@RequestMapping(value="/MeterDetailsPDF",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody void MeterDetailsPDF(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
		{
			String param=request.getParameter("parameter");
			String data=request.getParameter("data");
			
			meterInventoryService.getMeterDetailsPdf(request, response, param, data);
		}
		
		@RequestMapping(value="/MeterDetailsExcel",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object MeterDetailsExcel(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
		{
			try {
			String parameter=request.getParameter("parameter");
			String data=request.getParameter("data");
			
			 String fileName = "MeterDetails";
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
	             
	             header.createCell(0).setCellValue("MeterNo");
					header.createCell(1).setCellValue("ConnectionType");
					header.createCell(2).setCellValue("Meter Make");
					header.createCell(3).setCellValue("Commissioning");
					header.createCell(4).setCellValue("Model");
					header.createCell(5).setCellValue("IP Period");
					header.createCell(6).setCellValue("PT Ratio");
					header.createCell(7).setCellValue("Meter Constant");
					header.createCell(8).setCellValue("CT Ratio");
					header.createCell(9).setCellValue("Tender No");
					header.createCell(10).setCellValue("Accuracy Class");
					header.createCell(11).setCellValue("Manufacture YearMonth");
					header.createCell(12).setCellValue("Current Rating");
					header.createCell(13).setCellValue("Warranty Period");
					header.createCell(14).setCellValue("Voltage Rating");
					header.createCell(15).setCellValue("MeterStatus");
					header.createCell(16).setCellValue("Entry By");
					header.createCell(17).setCellValue("Entry Date");
					header.createCell(18).setCellValue("Updated By");
					header.createCell(19).setCellValue("Updated Date");
					
					List<?> meterdetails=null;
					meterdetails=meterInventoryService.getMeterDetailsExcel(data,parameter);
					
					 int count =1;
						//int cellNO=0;
			            for(Iterator<?> iterator=meterdetails.iterator();iterator.hasNext();){
			      	    final Object[] values=(Object[]) iterator.next();
			      		
			      		XSSFRow row = sheet.createRow(count);
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
			      		if(values[2]==null)
			      		{
			      			row.createCell(2).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(2).setCellValue(String.valueOf(values[2]));
			      		}
			      		if(values[3]==null)
			      		{
			      			row.createCell(3).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(3).setCellValue(String.valueOf(values[3]));
			      		}
			      		if(values[4]==null)
			      		{
			      			row.createCell(4).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(4).setCellValue(String.valueOf(values[4]));
			      		}
			      		if(values[5]==null)
			      		{
			      			row.createCell(5).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(5).setCellValue(String.valueOf(values[5]));
			      		}
			      		if(values[6]==null)
			      		{
			      			row.createCell(6).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(6).setCellValue(String.valueOf(values[6]));
			      		}
			      		if(values[7]==null)
			      		{
			      			row.createCell(7).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(7).setCellValue(String.valueOf(values[7]));
			      		}
			      		if(values[8]==null)
			      		{
			      			row.createCell(8).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(8).setCellValue(String.valueOf(values[8]));
			      		}
			      		if(values[9]==null)
			      		{
			      			row.createCell(9).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(9).setCellValue(String.valueOf(values[9]));
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
			      		if(values[12]==null)
			      		{
			      			row.createCell(12).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(12).setCellValue(String.valueOf(values[12]));
			      		}
			      		if(values[13]==null)
			      		{
			      			row.createCell(13).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(13).setCellValue(String.valueOf(values[13]));
			      		}
			      		if(values[14]==null)
			      		{
			      			row.createCell(14).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(14).setCellValue(String.valueOf(values[14]));
			      		}
			      		if(values[15]==null)
			      		{
			      			row.createCell(15).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(15).setCellValue(String.valueOf(values[15]));
			      		}
			      		if(values[16]==null)
			      		{
			      			row.createCell(16).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(16).setCellValue(String.valueOf(values[16]));
			      		}
			      		if(values[17]==null)
			      		{
			      			row.createCell(17).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(17).setCellValue(String.valueOf(values[17]));
			      		}
			      		if(values[18]==null)
			      		{
			      			row.createCell(18).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(18).setCellValue(String.valueOf(values[18]));
			      		}
			      		if(values[19]==null)
			      		{
			      			row.createCell(19).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      		row.createCell(19).setCellValue(String.valueOf(values[19]));
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
						response.setHeader("Content-Disposition", "inline;filename=\"ManageDetailsReport.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						servletOutputStream.flush();
						servletOutputStream.close();
			
			return null;
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		}
		
		
		
		
		
		
		
		
		
}
