package com.bcits.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Seal;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.RdngMonthService;
import com.bcits.service.sealService;
import com.bcits.utility.MDMLogger;

@Controller
public class sealManagementController {
	
	@Autowired
	private sealService sealService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private MrnameService mrnameService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@RequestMapping(value="/sealManager",method=RequestMethod.GET)
	public String sealManager(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{
		seal.setBillmonth(meterMasterService.getMaxRdgMonthYear(request));
		model.put("mrNames", mrnameService.findAll());
		//model.put("currentmonth", value)
		//MDMLogger.logger.info(arg0);sealService.getCurrentMonthyear());
		return "sealmanager";
	}
	
	@RequestMapping(value="/pushAllseal",method={RequestMethod.GET,RequestMethod.POST})
	public String sealInward(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{
		
		MDMLogger.logger.info("In seal InWard");
		
		sealService.insertAllSeals(seal, model, request);		
		
		model.put("Newseal",new MeterMaster());
		
		return "NewSealManager";
	}
	
	@RequestMapping(value="/sealOutWard",method={RequestMethod.GET,RequestMethod.POST})
	public String sealOutWard(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In seal outWard");
		//seal.setIssuedBy((String)request.getSession().getAttribute("username"));
		sealService.updateSealOutWard(seal, model, request);		
		return "sealmanager";
	}
	
	@RequestMapping(value="/sealReIssue",method={RequestMethod.GET,RequestMethod.POST})
	public String sealReIssue(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		MDMLogger.logger.info("In seal ReIssue "+seal.getCardSealNo());
		//long sealCardNo1=seal.getCardSealNo();
		sealService.updateSealReIssue(seal, model, request);
		//seal.setCardSealNo(sealCardNo1);
		return "sealmanager";
	}
	
	@RequestMapping(value="/getSealCardNum",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getSealCardNum(HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal card number");
		return sealService.getMaxSealCardNum(request);
	}
	
	
	@RequestMapping(value="/getSealCardNum1",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getSealCardNum1(HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal card number");
		return sealService.getMaxSealCardNum1(request);
	}
	
	
	/*@RequestMapping(value="getSealCardNum1",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getSealCardNum1(HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal card number");
		return sealService.getMaxSealCardNum(request);
	}
	*/
	@RequestMapping(value="/singleSealUpdate",method={RequestMethod.GET,RequestMethod.POST})
	public String singleSealUpdate(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		MDMLogger.logger.info("In seal singleSealUpdate");
	
		sealService.sealDataSingleUpdate(seal, model, request);	
		
		model.put("Newseal",new MeterMaster());
		
		return "NewSealManager";
	}
	
	@RequestMapping(value="/sealMultipleUpdate",method={RequestMethod.GET,RequestMethod.POST})
	public String sealMultipleUpdate(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		MDMLogger.logger.info("In seal MultipleUpdate");
	
		sealService.sealDataMultipleUpdate(seal, model, request);	
		model.put("Newseal",new MeterMaster());
		return "NewSealManager";
	}
	
	@RequestMapping(value="/showBunches",method={RequestMethod.GET,RequestMethod.POST})
	public String showBunches(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		MDMLogger.logger.info("In show bunches "+seal.getBillmonth());
		model.put("value", "bunches");
		//model.put("allBunches", sealService.getSealBunches(request));
        sealService.getSealBunches(seal,model,request);        
        seal.setBillmonth(meterMasterService.getMaxRdgMonthYear(request));        
        model.put("sealManager", seal);
		return "sealmanager";
	}
	
	@RequestMapping(value="/showMrwiseSealSummary",method={RequestMethod.GET,RequestMethod.POST})
	public String showMrwiseSealSummary(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		
		int rdngMonth=rdngMonthService.findAll();
		model.addAttribute("rdngMonthSealissue",rdngMonth);
		
		MDMLogger.logger.info("In seal Summary Mrwise----------->"+request.getParameter("billMonth"));
		String billMonth=request.getParameter("billMonth");
		model.put("mrwiseSealList", sealService.getMrwiseSealSummary(billMonth));
		// model.put("sealManager", seal);
		
	     model.put("Newseal",new MeterMaster());
		 
		 return "NewSealManager";
	}
	
	@RequestMapping(value="/displayMrWiseSeals/{mrName}/{billMonth}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object displayMrWiseSeals(@PathVariable String mrName, @PathVariable String billMonth,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal Summary Mrwise----------->"+mrName+"---"+billMonth);
		return sealService.getMrSealNo(mrName,billMonth,model,"%");
	}
	
	
	// Added by Sunil KJ
	@RequestMapping(value="/displayMrWiseSealsUsed/{mrName}/{billMonth}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object displayMrWiseSealsUsed(@PathVariable String mrName, @PathVariable String billMonth,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal Summary Mrwise----------->"+mrName+"---"+billMonth);
		return sealService.getMrSealNo(mrName,billMonth,model,"SEALUSED");
	}
	
	
	@RequestMapping(value="/displayMrWiseSealsDamaged/{mrName}/{billMonth}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object displayMrWiseSealsDamaged(@PathVariable String mrName, @PathVariable String billMonth,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal Summary Mrwise----------->"+mrName+"---"+billMonth);
		return sealService.getMrSealNo(mrName,billMonth,model,"DAMAGE");
	}
	
	
	//End
	
	@RequestMapping(value="/showReturnSeals",method={RequestMethod.GET,RequestMethod.POST})
	public String showReturnSeals(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		int rdngMonth=rdngMonthService.findAll();
		model.addAttribute("rdngMonthSealissue",rdngMonth);
		
		MDMLogger.logger.info("In show Return seals=====>");
        model.addAttribute("mrwiseSealReturnList", sealService.getReturnSeals(model));
        //model.addAttribute("sealManager", seal);
        model.put("Newseal",new MeterMaster());
		//return "sealmanager";
        return "NewSealManager";
	}
	
	
	@RequestMapping(value="/showReturnSeals1",method={RequestMethod.GET,RequestMethod.POST})
	public String showReturnSeals1(@ModelAttribute("sealManager") Seal seal,HttpServletRequest request,ModelMap model)
	{	
		MDMLogger.logger.info("In show Return seals=====>");
        model.addAttribute("mrwiseSealReturnList", sealService.getReturnSeals(model));
        model.addAttribute("sealManager", seal);
        long n = sealService.getMaxSealCardNum1(request);
        model.put("results1", "Card SlNo1 updated Successfully ... CardSlNo1 is : "+n);
        model.put("Newseal",new MeterMaster());
        //return "sealmanager";
        
        return "NewSealManager";
	}
	
	@RequestMapping(value="/displayMrWiseSealsReturn/{rmrName}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object displayMrWiseSealsReturn(@PathVariable String rmrName,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal displayMrWiseSeals----------->"+rmrName);
		return sealService.getMrWiseSealsReturn(rmrName,model);
	}
	
	
	//Added by Sunil KJ 
	
	@RequestMapping(value="/displayMrWiseSealsReturnPc/{rmrName}/{condition}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object displayMrWiseSealsReturnPc(@PathVariable String rmrName,@PathVariable String condition,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In seal displayMrWiseSeals----------->"+rmrName);
		return sealService.getMrWiseSealsReturnPc(rmrName,model,condition);
	}
	
	
	@RequestMapping(value="/updateMultipleCardSealNo1/{rmrName}/{condition}/{cardSealNo1}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object updateMultipleCardSealNo1(@PathVariable String rmrName,@PathVariable String condition,@PathVariable long cardSealNo1, ModelMap model,HttpServletRequest request )
	{
		System.out.println("----"+rmrName+ " : "+condition+ " : "+cardSealNo1);
		sealService.updateMultipleCardSlNo1(cardSealNo1, rmrName, condition);
		return ""; 
	}
	
	
	//End
	
	@RequestMapping(value="/updateCardSlNo1/{cardSealNo1}/{sealNumbers}/{rmrName}",method=RequestMethod.GET)
	public @ResponseBody String updateCardSlNo1(@PathVariable String cardSealNo1,@PathVariable String[] sealNumbers,@PathVariable String rmrName, ModelMap model, HttpServletRequest request) 
	{
		int count=0;
		String val1="";
			MDMLogger.logger.info("-=-=-=-=->sealNumbers>>>>>>>>"+cardSealNo1+"--"+rmrName+"--"+sealNumbers.length);
		 count=sealService.updateCardSlNo1(sealNumbers,Long.parseLong(cardSealNo1),rmrName);
		 MDMLogger.logger.info("-=-=-=-=->sealNumbers>>>>>>>>"+count);
		if(count==sealNumbers.length)
		{
			 val1= "updated";
		}
		else
		{
			 val1= "notUpdated";
		}
		return val1;
		
	}
}
