package com.bcits.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.CMRIEntity;
import com.bcits.entity.CmriNumber;
import com.bcits.service.CMRIService;
import com.bcits.service.cmriDeviceService;
import com.bcits.utility.MDMLogger;

@Controller
public class CMRIController {
	@Autowired
	private cmriDeviceService cmriDeviceService;
	
	@Autowired
	private CMRIService cmriService;
	
	@RequestMapping(value="/cmriManager",method=RequestMethod.GET)
	public String cmriManager(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CmriNumber cmriNumber)
	{
		List findCmriNumber=cmriDeviceService.findAllCmriNumber();
		model.put("CmriList",findCmriNumber);
		return "cmrimanager";
	}
	
	@RequestMapping(value="/cmriIssue",method={RequestMethod.POST,RequestMethod.GET})
	public String cmriIssue(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In cmriIssue  ");	
		cmriService.commonCMRIProperties(request, model, cmri);		
		model.put("cmriData", cmriService.getCMRIIssueDetails(request, model, cmri, new Date()));
		return "cmriIssue";
	}

	
	@RequestMapping(value="/addCmriIssue",method={RequestMethod.POST,RequestMethod.GET})
	public String addCmriIssue(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In addCmriIssue");
		cmriService.addCMRIData(request, model, cmri);
		return "cmriIssue";
	}
	
	
	@RequestMapping(value="/getCMRIDataForEdit/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getCMRIDataForEdit(@PathVariable long id,HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In CmriIssue Edit");
		return cmriService.find(id);
		 
	}
	
	
	@RequestMapping(value="/issueDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String issueDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In update CmriIssue");		
		cmriService.updateCMRIDetails(request, model, cmri);
		return "cmriIssue";
	}
	
	@RequestMapping(value="/getIssueDataForRefresh",method={RequestMethod.POST,RequestMethod.GET})
	public String getIssueDataForRefresh(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In cmriIssue  ");	
		cmriService.commonCMRIProperties(request, model, cmri);		
		model.put("cmriData", cmriService.getCMRIIssueDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriIssue";
	}
	
	@RequestMapping(value="/cmriReceive",method={RequestMethod.POST,RequestMethod.GET})
	public String cmriReceive(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In cmriReceive ");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("cmriRecieveData", cmriService.getCMRIIssueDButnotRecievecDetails(request, model, cmri, new Date()));
		return "cmriReceive";
	}
	
	@RequestMapping(value="/cmriReceiveRefresh",method={RequestMethod.POST,RequestMethod.GET})
	public String cmriReceiveRefresh(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In cmriReceive ");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("cmriRecieveData", cmriService.getCMRIIssueDButnotRecievecDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriReceive";
	}
	
	//Added by Sunil KJ -  upload automatically 
	@RequestMapping(value="/uploadToCmriFormId",method={RequestMethod.GET,RequestMethod.POST})
	public String uploadToCmriFormId(HttpServletRequest request,ModelMap model)
	{
		long id = Integer.parseInt(request.getParameter("hiddenCmriId"));
		CMRIEntity cmri = cmriService.find(id);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		String readingDate = sdf.format(cmri.getRdgDate());
		System.out.println("the Reading Date is : "+readingDate);
		cmri = cmriService.uploadMakeWise(readingDate, cmri);
		System.out.println("---------------the cmri : "+cmri.getBillMonth() );
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("cmriRecieveData", cmriService.getCMRIIssueDButnotRecievecDetails(request, model, cmri,cmri.getRdgDate()));
		model.put("cmriManager", cmri);
		
		return "cmriReceive";
	}
	
	
	@RequestMapping(value="/cmriAlreadyReceivedData",method={RequestMethod.POST,RequestMethod.GET})
	public String cmriAlreadyReceivedData(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In already cmriReceive ");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("alreadyRecievedData", "YES");
		model.put("totalshow", "totalshow");
		model.put("cmriRecieveData", cmriService.getCMRIIssueDAndRecievecDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriReceive";
	}
	
	@RequestMapping(value="/getCMRIDataForRecieve/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getCMRIDataForRecieve(@PathVariable long id,HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In CmriIssue Recieve details");
		return  cmriService.find(id);
		
		 
	}
	
	@RequestMapping(value="/recieveDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String recieveDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In update CmriRecieve");
		cmriService.updateCMRIRecieveDetails(request, model, cmri);
		model.put("cmriRecieveData", cmriService.getCMRIIssueDButnotRecievecDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriReceive";
	}
	
	@RequestMapping(value="/alreadyRecieveDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String alreadyRecieveDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In update CmriRecieve");
		cmriService.updateCMRIRecieveDetails(request, model, cmri);
		model.put("alreadyRecievedData", "YES");
		model.put("cmriRecieveData", cmriService.getCMRIIssueDAndRecievecDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriReceive";
	}
	
	@RequestMapping(value="/getCMRIIssued",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getCMRIIssued(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In CmriIssue deevice issued or not");
		return  cmriService.getCMRIIssuedOrNot(request, model, cmri);
		
		 
	}
	
	@RequestMapping(value="/cmriDumping",method={RequestMethod.POST,RequestMethod.GET})
	public String cmriDumping(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In ::::::::::::cmriDumping  ");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("cmriDumpedData", cmriService.getCMRIRecievecButNotDumpedDetails(request, model, cmri, new Date()));		
		return "cmriDumping";
	}
	
	@RequestMapping(value="/dumpedDataRefresh",method={RequestMethod.POST,RequestMethod.GET})
	public String dumpedDataRefresh(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In dumped data refresh");	
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("cmriDumpedData", cmriService.getCMRIRecievecButNotDumpedDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	@RequestMapping(value="/dumpedDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String dumpedDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In dumped update");
		cmriService.updateCMRIDumpedDetails(request, model, cmri);
		model.put("cmriDumpedData", cmriService.getCMRIRecievecButNotDumpedDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	@RequestMapping(value="/alreadyDumped",method={RequestMethod.POST,RequestMethod.GET})
	public String alreadyDumped(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In already dumped update");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("check", "tally");
		model.put("cmriDumpedData", cmriService.getCMRIRecievecAndDumpedDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	@RequestMapping(value="/tallyDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String tallyDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In tally update");
		cmriService.updateCMRIDumpedDetails(request, model, cmri);
		model.put("check", "tally");
		model.put("cmriDumpedData", cmriService.getCMRIRecievecAndDumpedDetails(request, model, cmri,cmri.getRdgDate()));		
		return "cmriDumping";
	}
	
	@RequestMapping(value="/alreadyPrepared",method={RequestMethod.POST,RequestMethod.GET})
	public String alreadyPrepared(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In already dumped update");
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("check", "prepared");
		model.put("cmriDumpedData", cmriService.getCMRIPreparedDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	@RequestMapping(value="/preparedDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String preparedDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In prepared update");
		cmriService.updateCMRIDumpedDetails(request, model, cmri);
		model.put("check", "prepared");
		model.put("cmriDumpedData", cmriService.getCMRIPreparedDetails(request, model, cmri,cmri.getRdgDate()));
			return "cmriDumping";
	}
	@RequestMapping(value="/dumpingDifference",method={RequestMethod.POST,RequestMethod.GET})
	public String dumpingDifference(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In already dumped update");	
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("check", "difference");
		model.put("cmriDumpedData", cmriService.getCMRIDifferenceDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriDumping";
	}

	@RequestMapping(value="/differenceDataUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String differenceDataUpdate(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("In difference update");
		cmriService.updateCMRIDumpedDetails(request, model, cmri);
		model.put("check", "difference");
		model.put("cmriDumpedData", cmriService.getCMRIDifferenceDetails(request, model, cmri,cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	@RequestMapping(value="/dumpedStatusData",method={RequestMethod.POST,RequestMethod.GET})
	public String dumpedStatusData(HttpServletRequest request,ModelMap model,@ModelAttribute("cmriManager") CMRIEntity cmri)
	{
		MDMLogger.logger.info("dumped status data");	
		cmriService.commonCMRIProperties(request, model, cmri);
		model.put("check", "dumpedStatus");
		model.put("cmriDumpedData", cmriService.getCMRIRecievecAndDumpedDetails(request, model, cmri, cmri.getRdgDate()));
		return "cmriDumping";
	}
	
	
	//Ved Prakash Mishra
	
	@RequestMapping(value="/addNewCmriNo",method={RequestMethod.POST,RequestMethod.GET})
	public String addNewCmriNumber(@ModelAttribute("cmriManager") CmriNumber cmriNumber,HttpServletRequest request,ModelMap model)
	{
		String flag=cmriDeviceService.findDuplicate(cmriNumber.getCmri_no(),model);
		if(flag!=null)
		{
			 model.put("msg","CMRI number is already exist ");
		}
		else
		{
		   cmriDeviceService.save(cmriNumber);
		   model.put("msg","CMRI number addedd successfully");
		}
	
		List findCmriNumber=cmriDeviceService.findAllCmriNumber();
		model.put("CmriList",findCmriNumber);
		model.put("cmrinumber", new CmriNumber());
		return "cmrimanager";
	}
	
	
	  
    @RequestMapping(value="/editCmriNumber/{operation}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object editMessage(@PathVariable("operation") String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
        return cmriDeviceService.findDuplicate(operation,model);
    }
    
    
    
    @RequestMapping(value="/updateCmriNumber",method=RequestMethod.POST)
	public String updateCmriNumber(@ModelAttribute("cmriManager") CmriNumber cmriNumber,BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws IOException
	{	
			   String flag=null;
			   String cmrinum=cmriNumber.getCmri_no();
			   String oldcmri=request.getParameter("cmri_numb");
			     if(cmriDeviceService.findDuplicate(cmrinum,model)!=null)
			     {
			    	 model.put("msg","This CMRINo is already exist");
			     }
			     else
			     {
			       cmriDeviceService.updateCrmri(cmrinum,oldcmri,model);
			     }
			   List findCmriNumber=cmriDeviceService.findAllCmriNumber();
			   model.put("CmriList",findCmriNumber);
			
		       return "cmrimanager";
	}
	
	
    @RequestMapping(value="/checkDuplicate/{operation}",method={RequestMethod.POST,RequestMethod.GET})
  	public @ResponseBody Object checkDuplicateCmri(@PathVariable("operation") String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
  	 {
           return cmriDeviceService.findDuplicate(operation,model);
     }
    
    
    @RequestMapping(value="/checkAvail/{operation}",method={RequestMethod.POST,RequestMethod.GET})
  	public @ResponseBody Object checkCmriAvail(@PathVariable("operation") String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
  	 {
           return cmriDeviceService.checkCmriAvail(operation, model);
  	 }
	
	
    @RequestMapping(value="/downloadExcelCMRI",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String downloadCMRI(HttpServletRequest request,HttpServletResponse response)
    {
    	System.out.println("calling download excel");
		
		try{
			
		     String sheetName = "CMRIUpdate";
		     XSSFWorkbook wb = new XSSFWorkbook();
	         XSSFSheet sheet = wb.createSheet(sheetName) ;
	         XSSFRow header  = sheet.createRow(0);        
	         CellStyle lockedCellStyle = wb.createCellStyle();
	         lockedCellStyle.setLocked(true);
	         CellStyle unlockedCellStyle = wb.createCellStyle();
	         unlockedCellStyle.setLocked(false);
	         XSSFCellStyle style = wb.createCellStyle();
	         style.setWrapText(true);
	         XSSFFont font = wb.createFont();
	         font.setFontName(HSSFFont.FONT_ARIAL);
	         font.setFontHeightInPoints((short) 10);
	         font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	         style.setFont(font);
	         header.createCell(0).setCellValue("METERNO");
	         header.createCell(1).setCellValue("MTRMAKE");
	         String fileName="CMRIMaster";
	         FileOutputStream fileOut = new FileOutputStream(fileName); 
	         wb.write(fileOut);
	         fileOut.flush();
	         fileOut.close();
	         ServletOutputStream servletOutputStream;
	         servletOutputStream = response.getOutputStream();
	         response.setContentType("application/vnd.ms-excel");
	         response.setHeader("Content-Disposition", "inline;filename=\"CMRI.xlsx"+"\"");
	         FileInputStream input = new FileInputStream(fileName);
	         IOUtils.copy(input, servletOutputStream);
	         //servletOutputStream.w
	         servletOutputStream.flush();
	         servletOutputStream.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
    	
    }
   
    
}
