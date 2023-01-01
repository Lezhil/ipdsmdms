package com.bcits.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Seal;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.NewSealMangrService;
import com.bcits.service.RdngMonthService;
import com.bcits.service.sealService;
import com.bcits.utility.MDMLogger;

@Controller
public class NewSealManagerController {

	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private MrnameService mrnameService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private NewSealMangrService newSealMangrService;
	
	@Autowired
	private sealService sealService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	
	@RequestMapping(value="/newSealManager",method=RequestMethod.GET)
	public String sealManager(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model)
	{
		//Newseal.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		
		Newseal.setRdngmonth(rdngMonthService.findAll());
		model.put("readingMonthSeal",rdngMonthService.findAll());
		
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		System.out.println(circle);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		model.put("readingDate",sdf.format(date));
		Seal seal=new Seal();
		model.put("sealManager",new Seal());
		//seal.setBillmonth(meterMasterService.getMaxRdgMonthYear(request));
		seal.setBillmonth(rdngMonthService.findAll());
		
		int rdngMonth=rdngMonthService.findAll();
		model.addAttribute("rdngMonthSealissue",rdngMonth);
		
		System.err.println("RDNGMONTH FROM RDNGMONTH==== IN NEWSEALMANAGER=="+rdngMonth);
		model.put("mrNames", mrnameService.findAll());
		
		//MDMLogger.logger.info(arg0);sealService.getCurrentMonthyear());
		return "NewSealManager";
	}
	
	@RequestMapping(value="/getSealByAccno",method={RequestMethod.POST,RequestMethod.GET})
	public String getConnectionData(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection new Seal manager Search by  Account no");
		System.out.println("In connection new Seal manager Search by  Account no");
		newSealMangrService.searchSealByAccNo(request, Newseal, model);
		
		
		//
		//System.out.println("result ="+result.size());*/
		
		return "NewSealManager";
	}
	
	
	@RequestMapping(value="/getSealByAcc",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object[] getSealByAcc(HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection new Seal manager Search by  Account no");
		
		String accno=request.getParameter("accno");
		String rdngmnth=request.getParameter("rdngmnth");
		Object[] result=newSealMangrService.searchSealByAcc(accno, rdngmnth, model);
		
		return result;
	}
	
	@RequestMapping(value="/getSealByMtr",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object[] getSealByMtr(HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection new Seal manager Search by  Mtr no");
		
		String meterno=request.getParameter("meterno");
		String rdngmnth=request.getParameter("rdngmnth");
		Object[] result=newSealMangrService.searchSealByMtr(meterno, rdngmnth);
		
		return result;
	}
	
	@RequestMapping(value="/updateNewSeal",method={RequestMethod.POST,RequestMethod.GET})
	public String updateNewSeal(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection new Seal manager Search by  Mtr no");
		
		model.put("readingMonthSeal",rdngMonthService.findAll());
		
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		
		String meterNo=request.getParameter("meterno");

		String rdngmnth=request.getParameter("rdngmonth1");

		System.out.println("rdngmonth1-->"+rdngmnth);

		String newsealNum=Newseal.getNewseal();
		System.out.println("newsealNum-->"+newsealNum);
		String accNo=Newseal.getAccno();
	
		System.out.println("meterNo======="+meterNo);
		String mrname1=request.getParameter("mrnameId");
		 System.out.println("mrname1======="+mrname1);
	/*	int readingMonth=Newseal.getRdngmonth();*/
	
		int readingMonth=Integer.parseInt(rdngmnth);
	    System.out.println("readingMonth From AccNo and MeterNo Updated To Seal ======="+readingMonth);
		String result=newSealMangrService.getSealData(newsealNum,model,accNo,meterNo,mrname1,readingMonth);
		System.err.println("result-->"+result);
		if(result.equalsIgnoreCase("Updated"))
		{
			model.put("Updated", "Updated Successfully");
		}
		else
		{
			model.put("NotUpdated", "nnnnn");
		}
		if(result.equalsIgnoreCase("sealUpdated"))
		{
			model.put("Updated", "Updated Successfully");
		}
		else
		{
			model.put("Notupdated", "Not Updated");
		}
		
		model.put("sealManager",new Seal());
		
		return "NewSealManager";
	}
	
	//MR wise seal to next month
	
	/*@RequestMapping(value="/getMrNameBasedOndiv",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getMrNameBasedOndiv(HttpServletRequest request,ModelMap model)
	{
		System.out.println("mrnames for div");
		
		String division=request.getParameter("param");
		
		return masterService.getALLMrNameByDiv(division);
		
		//return result;
	}
	
	@RequestMapping(value="/getMrNameBasedOncir",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getMrNameBasedOncir(HttpServletRequest request,ModelMap model)
	{
		System.out.println("mrnames for circle");
		
		String circle=request.getParameter("param");
		
		return masterService.getALLMrNameByCIR(circle);
		
		
	}*/
	
	//get Total No of Seal 
	
	@RequestMapping(value="/getTotalNoSeal",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Long getTotalNoSeal(HttpServletRequest request,ModelMap model)
	{
		System.out.println("total no seal");
		
		int Frommnt=Integer.parseInt(request.getParameter("Frommnt"));
		String MrNameDiv=request.getParameter("MrNameDiv");
		System.out.println("Frommnt-->"+Frommnt);
		System.out.println("MrNameDiv-->"+MrNameDiv);
		Long count= newSealMangrService.getTotalNoSeal(Frommnt, MrNameDiv);
		System.out.println(count);
		
		return count;
	}
	
	
	@RequestMapping(value="/transferSealForNxtMnth",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody int transferSealForNxtMnth(HttpServletRequest request,ModelMap model)
	{
		System.out.println("TransferSealForNxtMnth");
		int result=0;
		int Frommnt=Integer.parseInt(request.getParameter("Frommnt"));
		int ToMnth=Integer.parseInt(request.getParameter("tomnt"));
		String mrname=request.getParameter("mrname");
		System.out.println("Frommnt-->"+Frommnt);
		System.out.println("ToMnth-->"+ToMnth);
		System.out.println("mrname-->"+mrname);
		
		 result= newSealMangrService.sealDataTransferForNxtMnth(Frommnt, ToMnth,mrname);
		return result;
	}

	
	
	// Addedc by vijju
	
	@RequestMapping(value="/mrWisePending",method={RequestMethod.GET,RequestMethod.POST})
	public String mrwisependingMethod(ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session)
	{
		int rdngMonth=rdngMonthService.findAll();
		model.addAttribute("rdngMonthPending",rdngMonth);
		model.put("circlePending",masterService.getCircleForMrWiseTotal());
		return "mrPendingData";
	}
	
	@RequestMapping(value="/mrpendingdata",method={RequestMethod.GET,RequestMethod.POST})
	public String mrpendingdata(ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session)
	{
		System.out.println("========INSIDE MRWISE PENDING DATA=======");
		model.put("circlePending",masterService.getCircleForMrWiseTotal());
		
		int rdngMonth=rdngMonthService.findAll();
		model.addAttribute("rdngMonthPending",rdngMonth);
		
		String circle=request.getParameter("circleId");
		String billmonth=request.getParameter("month");
		System.out.println("billmonth--- "+billmonth);
		 model.put("selectedCircle",circle);
		List<Object[]> dashDetails=sealService.getpendingdata(billmonth,circle);
		System.out.println(dashDetails);
		
		if(dashDetails.size() > 0)
		{
		    model.put("dashDetails",dashDetails);
			model.put("firstPendingTableShow","firstPendingTableShow");
		}
		else
		{
			model.put("firstPendingTableError","MR Wise Details Not Found..");
		}
		
		return "mrPendingData";
	}
	
	@RequestMapping(value="/mrpendingSecondTabdata",method={RequestMethod.GET,RequestMethod.POST})
	public String mrpendingdataFirst(ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session)
	{
		System.out.println("========INSIDE FIRST MRWISE PENDING DATA=======");
		
		model.put("circlePending",masterService.getCircleForMrWiseTotal());
		String circle=request.getParameter("circle");
		String mrname=request.getParameter("mrname");
		String billmonth=request.getParameter("month");
		System.out.println("billmonth--- "+billmonth);
		
		 model.put("selectedCircle",circle);
		 
		
		List<Object[]> dashDetails=sealService.getpendingdata(billmonth,circle);
	    model.put("dashDetails",dashDetails);
		model.put("firstPendingTableShow","firstPendingTableShow");
	
		List<?> sectionDash=sealService.viewcountwiseDetails(mrname,billmonth,model,request,response);
		
		if(sectionDash.size() > 0)
		{
			model.put("sectionDash",sectionDash);
			model.put("secondPendingTableShow","secondPendingTableShow");
		}
		else
		{
			model.put("secondPendingTableError","MR Details Not Found");
		}
		
		model.addAttribute("mrname",mrname);
		
		int rdngMonth=rdngMonthService.findAll();
		System.out.println("RDNGMONTH FROM RDNGMONTH==== IN mrpendingdata=="+rdngMonth);
		model.addAttribute("rdngMonthPending",rdngMonth);
		return "mrPendingData";
	}
	
	
	
	@RequestMapping(value="/updateSealIssueToMR",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public BigDecimal SealIssueToMR(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model,HttpSession session)
	{
		String sealFrom=request.getParameter("sealNoFrom");
		String sealTo=request.getParameter("sealNoTo");
		int sealLen=Integer.parseInt(request.getParameter("seallen"));
		
		String mrName=request.getParameter("mrName");
		String date =request.getParameter("date");
		String rdMonth=request.getParameter("rdMonth");
		
		System.out.println("sealFrom====="+sealFrom+"sealTo======"+sealTo+"sealLen======"+sealLen+"mrName======"+mrName+"date==="+date+"rdMonth====="+rdMonth);
		
		BigDecimal result= newSealMangrService.sealCountForSealIssue(sealFrom, sealTo,sealLen);
		System.out.println("Count From Seal===="+result);
		
		BigDecimal cardslNo= newSealMangrService.getCardslNoForSealIssue();
		System.out.println("cardslNo From Seal===="+cardslNo);
		Long cardslNo1=Long.parseLong(String.valueOf(cardslNo))+1;
		
		System.out.println("cardslNo1 By adding 1===="+cardslNo1);
		
	    String userName=(String) session.getAttribute("username");
	    System.out.println("userName===="+userName);
		int updateRes=newSealMangrService.updateSealIssue(mrName,date,rdMonth,cardslNo1,sealFrom,sealTo,sealLen,userName);
		return result;
	}

}
