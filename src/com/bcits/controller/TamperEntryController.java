package com.bcits.controller;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.TamperEntity;
import com.bcits.service.RdngMonthService;
import com.bcits.service.TamperService;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class TamperEntryController {
	
	@Autowired
	private TamperService tamperService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	int status=0;
	
	@RequestMapping(value="/tamperEntry",method={RequestMethod.GET,RequestMethod.POST})
	public String tamperMethod(ModelMap model)
	{
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("occurredDate",sdf1.format(date));
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("restoredDate",sdf2.format(date));
		int readingMonth=rdngMonthService.findAll();
		
		model.put("readingMonth",readingMonth);
		
		List<?> tamperType=tamperService.getTamperType();
		model.put("tamperType",tamperType);
		
		List tamperData=tamperService.getTamperDATA(readingMonth);
		System.out.println("tamperData  size-->"+tamperData.size());
		model.addAttribute("tamperData",tamperData);
		if(status==1){
			model.put("tampSuccess","Tamper Data Saved Successfully...");
			status=0;
		}
		return "tamperEntry";
	}
	
	@Transactional
	@RequestMapping(value="/saveTamperRecords",method={RequestMethod.GET,RequestMethod.POST})
	public String tamperSaveMethod(HttpServletRequest request,HttpSession session,ModelMap model) throws ParseException
	{
		List<?> tamperType=tamperService.getTamperType();
		model.put("tamperType",tamperType);
		
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("occurredDate",sdf1.format(date));
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("restoredDate",sdf2.format(date));
		
		int readingMonth=rdngMonthService.findAll();
		
		model.put("readingMonth",readingMonth);
		
		String rdngMonth =request.getParameter("rdngMonth");
		String meterNo =request.getParameter("meterNo");
		String tampertype= request.getParameter("tamperType");
		String occurredDate =request.getParameter("occurredDate");
		String restoredDate =request.getParameter("restoredDate");
		String currentStatus =request.getParameter("currentStatus");
		String userName=(String) session.getAttribute("username");
		try {
			Date currentDate=new Date();
			
			
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String currDate=format.format(currentDate);
			
			//System.out.println("rdngMonth====>"+rdngMonth+"meterNo=====>"+meterNo+"tampertype===>"+tampertype+"occurredDate====>"+occurredDate);
			//System.out.println("restoredDate====>"+restoredDate+"currentStatus=====>"+currentStatus+"userName===>"+userName+"currentDate====>"+currDate);
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			TamperEntity tamp=new TamperEntity();
			
			tamp.setRdngMonth(Integer.parseInt(rdngMonth));
			tamp.setMeterNo(meterNo);
			
			tamp.setTamperType(tampertype);
			tamp.setOccurredDate(sdf.parse(occurredDate));
			if( !(restoredDate.equalsIgnoreCase("")) && (restoredDate!=null) && restoredDate!="")
			{
				tamp.setRestoredDate(sdf.parse(restoredDate));
			}
			tamp.setCurrentStatus(currentStatus);
			tamp.setUserName(userName);
			tamp.setTimeStamp(currDate);
			tamperService.save(tamp);
			List tamperData=tamperService.getTamperDATA(readingMonth);
			System.out.println("tamperData  size-->"+tamperData.size());
			model.addAttribute("tamperData",tamperData);
			status=1;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:tamperEntry";
	}
	
	@RequestMapping(value="/searchTamperViewData",method={RequestMethod.GET,RequestMethod.POST})
	public String searchTamper(HttpServletRequest request,ModelMap model)
	{
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("occurredDate",sdf1.format(date));
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		model.put("restoredDate",sdf2.format(date));
		int readingMonth=rdngMonthService.findAll();
		model.put("readingMonth",readingMonth);
		
		
		String circle=request.getParameter("circle");
		int rdngmonth= rdngMonthService.findAll();
		
		List tamperData=tamperService.getTamperDATA(readingMonth);
		System.out.println("1st tamperData  size-->"+tamperData.size());
		model.addAttribute("tamperData",tamperData);
		
		List<?> tmpList=tamperService.getTmpViewData(circle,rdngmonth);
		if(tmpList.size() > 0)
		{
			model.put("tamperDetails", tmpList);
			model.put("tampDataShow", "tampDataShow");
		}
		else
		{
			model.put("tampDetailsError", "Tamper Details Not Found...");
		}
		List<?> tamperType=tamperService.getTamperType();
		model.put("tamperType",tamperType);
		return "tamperEntry";
	}
	
	@RequestMapping(value="/checkMeternoExistForTamper",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public Long MeternoExist(HttpServletRequest req,ModelMap model)
	{
		String rdngMonth=req.getParameter("rdngMonth");
		String meterNo=req.getParameter("meterNo");
		
		Long meternoCount=tamperService.checkMeterNo(rdngMonth,meterNo);
		System.out.println("count of meterno from table---"+meternoCount);
		
		
		return meternoCount;
	}
	
}