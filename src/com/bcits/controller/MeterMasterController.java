package com.bcits.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.ChangesEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.entity.Meterdata;
import com.bcits.entity.ModemConfig;
import com.bcits.entity.ModemMaster;
import com.bcits.entity.Mrname;
import com.bcits.entity.SdoJcc;
import com.bcits.entity.UploadedFile;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.service.ChangesService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterLifeCycleService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MeterdataService;
import com.bcits.service.ModemMasterService;
import com.bcits.service.ModemconfigService;
import com.bcits.service.MrnameService;
import com.bcits.service.SdoJccService;
import com.bcits.service.XmlImportService;
import com.bcits.utility.MDMLogger;
import com.businessobjects.reports.crprompting.CRPromptValue.Str;

/**
 * @author Manjunath Kotagi
 * 
 * Meter Master Controller
 *
 */
@Controller
public class MeterMasterController {
	@Autowired
	private ChangesService changesService;
	@Autowired
	private MasterService masterService;
	@Autowired
	private MeterMasterService meterMasterService;
	@Autowired
	private XmlImportService xmlImportService;	
	@Autowired
	private MrnameService mrnameService;
	
	@Autowired
	private SdoJccService sdoJccService;
	
	@Autowired
	private MeterdataService meterdataservice;
	
	@Autowired
	private ModemMasterService modemmasterservice;
	
	@Autowired
	private ModemconfigService modemconfigservice;
	
	@Autowired
	private MeterLifeCycleService meterlifecycleservice;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	
	
	/*@PersistenceContext(unitName="mdm")
	protected EntityManager entityManager;*/
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager postgresMdas;
	
	@RequestMapping(value="/getmonthdata",method=RequestMethod.POST)	
	public String getmonthdata(ModelMap model,HttpServletRequest request){	
		 model.addAttribute("meterMaterData", meterMasterService.findmonthData(request.getParameter("month")));	
		 model.addAttribute("selectedMonth",request.getParameter("month"));
		 return "metermaster";		 
	}
	
	@RequestMapping(value="/exportManager",method=RequestMethod.GET)
	public String exportManager(HttpServletRequest request)
	{
		return "exportmanager";
	}	
	
	@RequestMapping(value="/metermasterexport",method=RequestMethod.POST)
	public String exportmanager(ModelMap model,HttpServletRequest request){		
		model.addAttribute("exportmeterMaterData", meterMasterService.findmeterandmonthData(request.getParameter("meternumber")));				
		model.addAttribute("selectedMonth",request.getParameter("month"));
		model.addAttribute("meterMasterSelected",true);
		model.addAttribute("selectedmeterNumber",request.getParameter("meternumber"));
		return "exportmanager";		 
	}
	@RequestMapping(value="/billingdataexport",method=RequestMethod.POST)
	public String billingdataexport(ModelMap model,HttpServletRequest request){	
		model.addAttribute("exportmeterBillData", xmlImportService.exportDetailsBasedonMeterNo(request.getParameter("meternumber")));				
		model.addAttribute("selectedMonth",request.getParameter("month"));
		model.addAttribute("billDataSelected",true);
		model.addAttribute("selectedmeterNumber",request.getParameter("meternumber"));
		return "exportmanager";		 
	}
	@RequestMapping(value="/intervaldataexport",method=RequestMethod.POST)
	public String intervaldataexport(ModelMap model,HttpServletRequest request){			
		return "exportmanager";		 
	}
	
	// from here Manjunath D A 
	@RequestMapping(value="/newConnection",method=RequestMethod.GET)
	public String newConnection(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In  new connection " );	
		model.put("mrNames", masterService.findMr());
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		
		newConnectionMeterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		model.addAttribute("category",masterService.getALLCategories());
		/*List<?> circle=masterService.getALLCircle();
		model.addAttribute("circles",circle);*/
		model.addAttribute("circles",sdoJccService.getAllCircle());
		model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		model.addAttribute("MNP",masterService.getAllMNP());
		
		return "newConnection";
	}
	
	//Shrinivas
	@RequestMapping(value="/getCirData",method=RequestMethod.GET)
	public @ResponseBody SdoJcc getCirData(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In get circle data for accno " );
		String accn=request.getParameter("accn");
		System.out.println(accn);
		return sdoJccService.getAllDetailsForAccno(accn);
	}
	
	@RequestMapping(value="/checkMeterAccNoExist",method=RequestMethod.GET)
	public @ResponseBody String checkMeterAccNoExist(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection check  exist" );		
		return meterMasterService.checkMeterAccNoExistOrNot(newConnectionMeterMaster, request);
	}
	
	@RequestMapping(value="/checkMeterExist",method=RequestMethod.GET)
	public @ResponseBody String checkMeterExist(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection check meter exist" );		
		return meterMasterService.checkMeterExist(newConnectionMeterMaster, request);
	}
	
	@RequestMapping(value="/addNewConnection",method={RequestMethod.GET,RequestMethod.POST})
	public String addNewConnection(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In Add new connection Data ");
		meterMasterService.addNewConnectionData(newConnectionMeterMaster, request, model);	
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		model.put("mrNames", masterService.findMr());
		model.addAttribute("category",masterService.getALLCategories());
		model.addAttribute("circles",sdoJccService.getAllCircle());
		model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		model.addAttribute("MNP",masterService.getAllMNP());
		return "newConnection";
	}
	
	@RequestMapping(value="/connectionDataModification",method={RequestMethod.POST,RequestMethod.GET})
	public String connectionDataModification(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In data Modification ");
		//model.put("mrNames", mrnameService.findAll());
		model.put("mrNames", masterService.findMr());
		newConnectionMeterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		model.addAttribute("MNP",masterService.getAllMNP());
		model.addAttribute("category",masterService.getALLCategories());
		/*List<?> circle=masterService.getALLCircle();
		model.addAttribute("circles",circle);*/
		model.addAttribute("circles",sdoJccService.getAllCircle());
		model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		model.put("mnpValue", "noVal");
		return "dataModification";
	}
	
	@RequestMapping(value="/getConnectionData",method={RequestMethod.POST,RequestMethod.GET})
	public String getConnectionData(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection data Modification Search by  Account no");
		meterMasterService.searchByAccNo(request, newConnectionMeterMaster, model);
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		
		//model.addAttribute("MNP",masterService.getAllMNP());
		/*List<?> result=masterService.getALLCategories();
		System.out.println("result ="+result.size());*/
		model.addAttribute("category",masterService.getALLCategories());
		/*List<?> circle=masterService.getALLCircle();
		model.addAttribute("circles",circle);*/
		model.addAttribute("circles",sdoJccService.getAllCircle());
		//model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		//model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		
		return "dataModification";
	}
	
	@RequestMapping(value="/getConnectionMtrData",method={RequestMethod.POST,RequestMethod.GET})
	public String getConnectionMtrData(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection mtr data Modification ");
		System.out.println("In connection data Modification Search by  meter  no");
		meterMasterService.searchByMtrNo(request, newConnectionMeterMaster, model);
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		model.addAttribute("MNP",masterService.getAllMNP());
		model.addAttribute("category",masterService.getALLCategories());
		
		model.addAttribute("circles",sdoJccService.getAllCircle());
		model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		return "dataModification";
	}
	
	@RequestMapping(value="/updateConnectionData",method={RequestMethod.POST,RequestMethod.GET})
	public String updateConnectionData(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In connection data updation ");
		model.put("mnpMaster", request.getParameter("mnp"));
		meterMasterService.updateConnectionDataValues(newConnectionMeterMaster, request, model);
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		model.addAttribute("MNP",masterService.getAllMNP());
		model.put("mrNames", masterService.findMr());
		model.addAttribute("category",masterService.getALLCategories());
		model.addAttribute("circles",sdoJccService.getAllCircle());
		model.addAttribute("divisions",sdoJccService.getAllDistDivision());
		model.addAttribute("SDOCODES",sdoJccService.getDistALLSdoCodes());
		model.addAttribute("SDONames",sdoJccService.getDistALLSdoNames());
		
		return "dataModification";
	}
	
	/*Ved Prakash from here*/ 
	@RequestMapping(value="/accountTransfer",method={RequestMethod.POST,RequestMethod.GET})
	public String accountTransfer(@ModelAttribute("meterMaster") MeterMaster meterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In ::::::::::::accountTransfer  ");
		meterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		return "accountTransfer";
	}
	

   
   @RequestMapping(value={"/transferAccount","/transferAccount1"},method={RequestMethod.POST,RequestMethod.GET})
   public String   getMeterAndAccount(@ModelAttribute("meterMaster") MeterMaster meterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In ::::::::::::Get Account Transfer from MeterMaster ");
	    
		    String uri = request.getRequestURI();
		    System.out.println("uuuuuuuuuuuuuu"+uri);
		    System.out.println("Request map"+request.getServletContext().getRealPath(""));
			System.out.println("Context "+request.getServletContext().getContextPath());
		    if(uri.equals("/bsmartmdm/transferAccount"))
		    {
		        System.out.println("OOOOOOOOOOOOLLLLLLLLLLLLLDDDDDDD"+meterMaster.getMaster().getOldaccno());
		    	meterMasterService.getMeterMasterData2(request, meterMaster, model);
		    }
		    else
		    {
		    	 meterMasterService.getMeterMasterData1(request, meterMaster, model);
		    }
		      
	 	  
	 	    return "accountTransfer";
		
	}
   
   
   

	/*@RequestMapping(value="/transferAccountUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String accountTransferUpdate(@ModelAttribute("meterMaster") MeterMaster meterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In ::::::::::::accountTransfer"+meterMaster.getAccno().substring(0, 4));		
		String oldaccno=request.getParameter("master.oldaccno");
		String accno=meterMaster.getAccno();
		meterMaster.setSdocode(Short.parseShort(meterMaster.getAccno().substring(0, 4)));
	   // List<MeterMaster> mts=meterMasterService.searchByAccNoSec(request, meterMaster, model);
	   // changesService.changesUpdate(mts.get(0), accno, model);
	    List<MeterMaster> mts=meterMasterService.searchByAccNoSec(request,meterMaster, oldaccno, model);
	    ChangesEntity obj=changesService.InsertTransaction1(mts.get(0),"OLD", request);
	    changesService.update(obj);
        model.put("sdocodeValue", meterMaster.getSdocode());
        System.out.println("old------SDO Code from meterMaster...."+meterMaster.getSdocode());
        String sdocode=accno.substring(0, 4);
        System.out.println("new ----SDO Code from meterMaster...."+sdocode);
        List<?> sdojccList=sdoJccService.getDataFromsdoJcc(sdocode);
        
        
        String circle="";
        String division="";
        String sdoname="";

        Short sdoCode=0;

        String sdoCode="";

        

        for(Iterator<?> iterator=sdojccList.iterator();iterator.hasNext();)
    	{
			Object[] obj1=(Object[]) iterator.next();
			System.out.println("sdoCode From SDOJCC Table====="+obj1[3]);
			
			sdoCode=(Short) obj1[3];
		
		}
        System.out.println("sdoCode From SDOJCC Table After List Iteration=====>"+sdoCode);
        

    	for(Iterator<?> iterator1=sdojccList.iterator();iterator1.hasNext();)
    	{
    		System.out.println("Inside 1St iterator========>"+sdoCode);
			Object[] obj1=(Object[]) iterator1.next();
			sdoCode=String.valueOf(obj1[3]);
			
			System.out.println("sdoCode From SDOJcc Table========>"+sdoCode);
		}
    	
    	System.out.println("sdoCode From SDOJcc Table OutSide of the Iterator Loop========>"+sdoCode);
    	
    	System.out.println("sdoCode From SDOJcc Table OutSide of the Iterator Loop========>"+sdoCode);
       
    	 if(sdoCode!=null && !(sdoCode.equalsIgnoreCase("")) )
         {

        try {
        	
        	for(Iterator<?> iterator=sdojccList.iterator();iterator.hasNext();)
        	{
    			Object[] obj1=(Object[]) iterator.next();
    			System.out.println("circle=="+obj1[0]);
    			System.out.println("division=="+obj1[1]);
    			System.out.println("sdoname=="+obj1[2]);
    			
    			 circle=(String) obj1[0];
    			 division=(String) obj1[1];
    			 sdoname=(String) obj1[2];
    		
    		}
            
        
    	    int success= masterService.updateOldAcc(accno,oldaccno,request,model,circle,division,sdoname);
    	    if(success>0)
    	    {
    		  meterMasterService.updateNewAccount(meterMaster, request,model);
    		  List<MeterMaster> mts1=meterMasterService.searchByAccNoSec(request,meterMaster, accno, model);
    		  ChangesEntity obj1=changesService.InsertTransaction1(mts1.get(0),"NEW", request);
    		  changesService.update(obj1);
    	      model.put("msg","Meter account trasfered successfully");
    	    }
    	    else 
    	    {
    	      model.put("msg","Account not transferred,new account no. may be existed ");
    	    }
    	     MeterMaster newMeterMaster=new MeterMaster();
    	     newMeterMaster.setRdngmonth(meterMaster.getRdngmonth());
    	     model.put("meterMaster", newMeterMaster);
    	}
     
        catch (Exception e) {
    		e.printStackTrace();
    	}
        
        }
        else
        {
        	model.put("AccnoNotThere", "SDOCODE Wrong, Please check again");
        }
        
    	
		 return "accountTransfer";
	}*/
   
   
   @RequestMapping(value="/transferAccountUpdate",method={RequestMethod.POST,RequestMethod.GET})
  	public String accountTransferUpdate(@ModelAttribute("meterMaster") MeterMaster meterMaster,HttpServletRequest request,ModelMap model)
  	{
  		MDMLogger.logger.info("In ::::::::::::accountTransfer"+meterMaster.getAccno().substring(0, 4));		
  		String oldaccno=request.getParameter("master.oldaccno");
  		String accno=meterMaster.getAccno();
  		meterMaster.setSdocode(Integer.parseInt(meterMaster.getAccno().substring(0, 4)));
  	   // List<MeterMaster> mts=meterMasterService.searchByAccNoSec(request, meterMaster, model);
  	   // changesService.changesUpdate(mts.get(0), accno, model);
  	    List<MeterMaster> mts=meterMasterService.searchByAccNoSec(request,meterMaster, oldaccno, model);
  	    ChangesEntity obj=changesService.InsertTransaction1(mts.get(0),"OLD", request);
  	    changesService.update(obj);
          model.put("sdocodeValue", meterMaster.getSdocode());
          System.out.println("old------SDO Code from meterMaster...."+meterMaster.getSdocode());
          String sdocode=accno.substring(0, 4);
          System.out.println("new ----SDO Code from meterMaster...."+sdocode);
          List<?> sdojccList=sdoJccService.getDataFromsdoJcc(sdocode);
          
          
          String circle="";
          String division="";
          String sdoname="";
          String sdoCode="";
          
      	for(Iterator<?> iterator1=sdojccList.iterator();iterator1.hasNext();)
      	{
      		System.out.println("Inside 1St iterator========>"+sdoCode);
  			Object[] obj1=(Object[]) iterator1.next();
  			sdoCode=String.valueOf(obj1[3]);
  			
  			System.out.println("sdoCode From SDOJcc Table========>"+sdoCode);
  		}
      	
      	System.out.println("sdoCode From SDOJcc Table OutSide of the Iterator Loop========>"+sdoCode);
         
          if(sdoCode!=null && !(sdoCode.equalsIgnoreCase("")) )
          {
          try {
          	
          	for(Iterator<?> iterator=sdojccList.iterator();iterator.hasNext();)
          	{
      			Object[] obj1=(Object[]) iterator.next();
      			System.out.println("circle=="+obj1[0]);
      			System.out.println("division=="+obj1[1]);
      			System.out.println("sdoname=="+obj1[2]);
      			
      			 circle=(String) obj1[0];
      			 division=(String) obj1[1];
      			 sdoname=(String) obj1[2];
      		
      		}
              
          System.err.println("accno=="+accno+"  oldaccno=="+oldaccno+" " );
      	    int success= masterService.updateOldAcc(accno,oldaccno,request,model,circle,division,sdoname);
      	    if(success>0)
      	    {
      		  meterMasterService.updateNewAccount(meterMaster, request,model);
      		  List<MeterMaster> mts1=meterMasterService.searchByAccNoSec(request,meterMaster, accno, model);
      		  ChangesEntity obj1=changesService.InsertTransaction1(mts1.get(0),"NEW", request);
      		  changesService.update(obj1);
      	      model.put("msg","Meter account trasfered successfully");
      	    }
      	    else 
      	    {
      	      model.put("msg","Account not transferred,new account no. may be existed ");
      	    }
      	     MeterMaster newMeterMaster=new MeterMaster();
      	     newMeterMaster.setRdngmonth(meterMaster.getRdngmonth());
      	     model.put("meterMaster", newMeterMaster);
      	}
       
          catch (Exception e) {
      		e.printStackTrace();
      	}
          
          }
          else
          {
          	model.put("AccnoNotThere", "SDOCODE Wrong, Please check again");
          }
          
      	
  		 return "accountTransfer";
  	}

	
	@RequestMapping(value="/checkAccnoExist/{accno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkAccnoExist(@PathVariable String accno,HttpServletRequest request,ModelMap model)
	{
		System.out.println("------checkAccnoExist----- : "+accno);
		String res = "";
		long n = masterService.checkAccnoExist(accno);
		System.out.println("=-=-=-=-=-=- : "+n);
		if(n > 0)
		{
			res = "Account Number Already Exist ...";
		}
		return res;
	}
	
	
	@RequestMapping(value="/checkMeterExist/{metrno}/{rdngmonth}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkMeterExist(@PathVariable String metrno, @PathVariable Integer rdngmonth,HttpServletRequest request,ModelMap model)
	{
		System.out.println("------checkAccnoExist----- : "+metrno);
		String res = "";
		long n = meterMasterService.checkMeterExist(rdngmonth, metrno);
		System.out.println("=-=-=-=-=-=- : "+n);
		if(n > 0)
		{
			res = "Meter Number Already Exist ...";
		}
		return res;
	}
	
	@RequestMapping(value="/checkMeterExists/{metrno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkMeterExists(@PathVariable String metrno,HttpServletRequest request,ModelMap model)
	{
		System.out.println("------checkAccnoExist----- : "+metrno);
		String res = "";
		long n = meterdataservice.checkMeterExists(metrno);
		System.out.println("=-=-=-=-=-=- : "+n);
		if(n > 0)
		{
			res = "Meter Number Already Exist ...";
		}
		return res;
	}
	
	
	@RequestMapping(value="/showVariationOfEnergy",method={RequestMethod.GET,RequestMethod.POST})
	public String showVariationOfEnergy(@ModelAttribute("metermasterSearch")MeterMaster meterMaster,HttpServletRequest request,ModelMap model)
	{
		String yearMonth=meterMasterService.getCurrentMonthyear();
		List<?> circles=consumerMasterService.getDistinctCircle();
		//String circles=meterMasterService.getCurrentMonthyear();
		String category=request.getParameter("category");
		if(request.getParameter("month")!=null)
		{
			yearMonth=request.getParameter("month");
		}
		else
		{
			yearMonth=meterMasterService.getCurrentMonthyear();
		}
		if(category!=null)
		{
			String sdocode=request.getParameter("subDivision");
			String circle=request.getParameter("circle");
			String division=request.getParameter("division");
			model.put("circle",circle);
			model.put("division",division);
			
			model.put("sdoCode","AEN_F-III_SANGANER");
			//model.put("sdoCode",sdoJccService.getsubDivisionName(sdocode));
			//model.put("VariationOfEnergyList", meterMasterService.showVariationOfEnergy(yearMonth,model,category,sdocode));
			model.put("size" , "10");
		}
		model.put("selectedMonth",yearMonth);
		model.put("circles",circles);
		return "variationOfEnergy";
	}
	
	@RequestMapping(value="/getAllDivisonBasedOnCircle/{circle}",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody Object getSubstationList(@PathVariable String circle,HttpServletResponse response,HttpServletRequest request,ModelMap model)
   	{
		return masterMainService.getDivisions("JCC");
    }
	
	@RequestMapping(value="/getAllSubDivisonBasedOnDivision/{division}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getAllSubDivisonBasedOnDivision(@PathVariable String division,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		return masterMainService.getSubDivisions(division);
	}
	
	@RequestMapping(value = "/importMeterNoExcel", method = {RequestMethod.GET,RequestMethod.POST})
	public String otherExternalReports(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		model.put("results", "notDisplay");
		return "meterNoExcelReport";
	}
	
	@RequestMapping(value="/uploadMeterNoExcel",method={RequestMethod.POST})
	public String holidayUpload(@ModelAttribute("meterNoUpload") UploadedFile meterNoUpload, ModelMap model, HttpServletRequest request)
	{
		String billmonth=request.getParameter("billmonth");
		MDMLogger.logger.info("In Holiday details Details upload"+billmonth);
		System.out.println("meterNoUpload-->"+meterNoUpload);
		String errorVal=meterMasterService.meterNoUpload(meterNoUpload,billmonth, model, request);
		return "meterNoExcelReport";
   }
	String res="";
	@RequestMapping(value="/updateMrNames",method={RequestMethod.GET,RequestMethod.POST})
	public String updateMrName(@ModelAttribute("mrnames")Master mrname,HttpServletRequest request,ModelMap model,HttpSession session)
	{
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		
		
		model.put("mrNames", masterService.findMr());
		model.put("sdoCodes", masterService.findSdoCode());
		model.put("MrnamesFromMrTable",masterService.getMrnames());
		//model.put("tadesc",masterService.findTadesc());
		String oldMrName=(String) session.getAttribute("oldMrName");
		String newMrName=(String) session.getAttribute("newMrName");
		model.addAttribute("results","notDisplay");
		if(res.equalsIgnoreCase("success"))
		{
			model.addAttribute("results","MRName " +oldMrName+ " updated to " +newMrName+ " Successfully");
		}
		 if(res.equalsIgnoreCase("failure"))
		{
			model.addAttribute("results","MRName Not Updated.Please Try Again!.");
		}
		if(res.equalsIgnoreCase("sdosuccess"))
		{
			model.addAttribute("results","MRName Updated  Successfully");
		}
		if(res.equalsIgnoreCase("sdofailure"))
		{
			model.addAttribute("results","MRName Not Updated.Please Try Again!.");
		}
		
		res="";
		return "UpdateMrName";
	}
	
	@RequestMapping(value="/UpdateNewMrName",method={RequestMethod.POST})
	public String UpdateNewMrName(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		String oldMrName=request.getParameter("firstmrId");
		String newMrName=request.getParameter("newmrname2");
		String sdocode =request.getParameter("sdocodename");
		String circle=request.getParameter("circleId");
		System.out.println("1 St Update oldMrName----"+oldMrName+ "newMrName----"+newMrName+"Circle===="+circle+"sdocode===="+sdocode);
		session.setAttribute("oldMrName", oldMrName);
		session.setAttribute("newMrName", newMrName);
		int count=mrnameService.updateMRName(oldMrName,newMrName,circle,sdocode);
		if(count>0)
		{
			res="success";
		}
		else
		{
			res="failure";
		}
		return "redirect:updateMrNames";
	}
	
	
	/*update mrnames in mrname table */
	
	@RequestMapping(value="/UpdateMrNameTable",method={RequestMethod.GET,RequestMethod.POST})
	public String UpdateMrNameTable(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		System.out.println("come to Mrname table update");
		String oldMrName=request.getParameter("mrname2");
		String newMrName=request.getParameter("newmrname2");
		System.out.println("old mrname==="+oldMrName);
		System.out.println("newMrName==="+newMrName);
		
		int count=mrnameService.updateMrnameTable(oldMrName, newMrName);
		System.out.println("count=="+count);
		if(count>0)
		{
			res="success";
		}
		else{
			res="failure";
		}
		model.put("MrnamesFromMrTable",masterService.getMrnames());
		System.out.println("end of updation");
		return "redirect:updateMrNames";
	}
	
	
	/*end of update mrnames in mrname table*/
	
	//add new mrname
	  
   @RequestMapping(value="/addNewMrnameToMrTable",method={RequestMethod.POST,RequestMethod.GET})
	public String newMrName(@ModelAttribute("mrnames")Mrname mrname,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In ::::::::::::::::::::::::: MrNames ");
		
		System.out.println("enter to mr names /addNewMrnameToMrTable ");
		
			System.out.println("MMMMMMMMRR===="+mrname.getMrname());
			mrnameService.save(mrname);
			
			model.put("mrNames", masterService.findMr());
			model.put("sdoCodes", masterService.findSdoCode());
			model.put("MrnamesFromMrTable",masterService.getMrnames());
		return "redirect:updateMrNames";
	}
	
	@RequestMapping(value="/exportAccNo",method=RequestMethod.GET)
	public String exportAccno(HttpServletRequest request,ModelMap model)
	{
		return "ExportAccno";
		
	}
	
	
	@RequestMapping(value="/getMRName",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getMRname(HttpServletRequest request,ModelMap model)
	{
		System.out.println("calling getMRName after selecting tadesc");// list.get(0).setMrname(mrname);
             return    masterService.FindMrNameOnSdo(request.getParameter("sitecode"),request.getParameter("tades"));
		//return masterService.findMr();
		
	}
	
	@RequestMapping(value="/getMRNameDataExport",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getMRNameDataExport(HttpServletRequest request,ModelMap model)
	{
		System.out.println("calling getMRName after selecting tadesc");
             return    masterService.FindMrNameOnSdo(request.getParameter("sitecode"),request.getParameter("tades"));
		
	}
	
	
	@RequestMapping(value="/getTadescCode",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getTadescCode(HttpServletRequest request,ModelMap model)
	{
		System.out.println("calling getTadescCode after selecting sdocode ");
             return    masterService.FindTadescCode(request.getParameter("sitecode"));
		
	}
	
	
	@RequestMapping(value="/UpdateNewMrNameSDO",method={RequestMethod.GET,RequestMethod.POST})
	public String UpdateNewMrNameSDO(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		String oldMrName=request.getParameter("secondmrId");
		String newMrName=request.getParameter("newmrname3");
		String sdoCode=request.getParameter("SecondSdocode");
		String tadesc=request.getParameter("tadesc");

		String circle=request.getParameter("SecondCircleId");

		System.out.println(" in 2nd UPDATE oldMrName----newMrName "+oldMrName+"----"+newMrName);
		session.setAttribute("oldMrName", oldMrName);
		session.setAttribute("newMrName", newMrName);
		int count=mrnameService.updateMRNameSDO(oldMrName,newMrName,sdoCode,tadesc, circle);
		if(count>0)
		{
			res="sdosuccess";
		}
		else
		{
			res="sdofailure";
		}
		return "redirect:updateMrNames";
	}
	
	
	
	//Delete records in MeterMaster By Account No
		//@Transactional(propagation=Propagation.REQUIRED)
		@RequestMapping(value="/deleteSelectedAccNo",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody String deleteSelectedAccNo(HttpServletRequest request,ModelMap model)
		{
			
			System.out.println("come to delete method  controller");
			String accno=request.getParameter("accno");
			String rdngmonth=request.getParameter("month");
			
			System.out.println("accno==="+accno+" rdngmonth-->"+rdngmonth);
			
			meterMasterService.findAccDATA(accno, rdngmonth, request);
			
			String flag="";
			int count=0;
			count= meterMasterService.delByAccNo(accno);
			if(count>0)
			{
				flag= "deleted";
			}
			else
			{
				flag= "notDeleted";
				
			}
			return flag;
		
		}
		
		
		//MR wise seal to next month
		
		@RequestMapping(value="/getMrNameBasedOndiv",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getMrNameBasedOndiv(HttpServletRequest request,ModelMap model)
		{
			System.out.println("mrnames for div");
			
			String division=request.getParameter("param");
			
			return masterService.getALLMrNameByDiv(division);
			
		}
		@RequestMapping(value="/getSubDivisionBasedOncirle",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getSubDivisionBasedOncirle(HttpServletRequest request,ModelMap model)
		{
			System.out.println("SubDivision for circle");
			
			String division=request.getParameter("param");
			
			return sdoJccService.getSubDivisionList(division);
			
		}
		
		@RequestMapping(value="/getMrNameBasedOncir",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getMrNameBasedOncir(HttpServletRequest request,ModelMap model)
		{
			System.out.println("mrnames for circle");
			
			String circle=request.getParameter("param");
			
			return masterService.getALLMrNameByCIR(circle);
			
		}
		@RequestMapping(value="/getDivisionBasedOncirle",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getDivisionBasedOncirle(HttpServletRequest request,ModelMap model)
		{
			System.out.println("Divisions for circle");
			
			String circle=request.getParameter("param");
			
			return sdoJccService.getALLDivisionByCIR(circle);
			
		}
		@RequestMapping(value="/getMNPBasedOncirle",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getMNPBasedOncirle(HttpServletRequest request,ModelMap model)
		{
			System.out.println("mrnames for circle");
			
			String circle=request.getParameter("param");
			
			return masterService.getALLMNPByCIR(circle);
			
		}
		
		

		@RequestMapping(value="/getSecondSdoCodesByCircle",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getSecondSdoCodesByCircle(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			  
			
			return masterService.findSecondSdoCodesByCircle(circle);
		}
		

		@RequestMapping(value="/getSdoCodesCirclewise",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getsdocode(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			  
			 String mrname=request.getParameter("mrName");
			return masterService.findSdoCodeBasedonCirle(circle,mrname);
		}
		
		
		@RequestMapping(value="/getFirstMrNameCirclewise",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getmrnameonCircleMethod(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			  
			  
			return masterService.findFirstMrName(circle);
		}
		
		@RequestMapping(value="/getSecondMrNameCirclewise",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getSecondMrNameCirclewise(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			String sdocode=request.getParameter("sdocode");
			  
			  
			return masterService.findSecondMrName(circle,sdocode);
		}
		
		
		
		@RequestMapping(value="/getMrNameSdoCodesCirclewise",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getMRNamesOnsdocodeCircle(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			
			return masterService.findMrNameBasedonCirleSdoCode(circle);
		}
		
		@RequestMapping(value="/getTadescCodeByMrname",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List<?> getTadescCodeByMrname(HttpServletRequest request,ModelMap model)
		{
			System.out.println("calling getTadescCode by mrnames ");
			String sdocode=request.getParameter("sdocode");
			String circle=request.getParameter("circle");
			String mrname=request.getParameter("mrname");
			
	             return    masterService.FindTadescCodeByMrname(sdocode,circle,mrname);
			
		}
		
		
		@RequestMapping(value="/getSdoNamesCirclewise",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getSdoNamesCirclewise(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circle");
			return masterService.findSdoNames(circle);
		}
		
		@RequestMapping(value="/getMrnamesBySDO",method={RequestMethod.GET,RequestMethod.POST})
		@ResponseBody public List<?> getMrnamesBySDO(HttpServletRequest request,ModelMap model)
		{
			String sdoname=request.getParameter("sdoname");
			return masterService.findMrNamesBySdoNames(sdoname);
		}
		
		@RequestMapping(value="/meterMaster",method=RequestMethod.GET)
		public String meterMaster(ModelMap model,HttpServletRequest request){	
			//model.addAttribute("meterMaterData", meterMasterService.findAll());
			return "metermaster1";
		}
		
		@RequestMapping(value="/getmonthdata1",method=RequestMethod.POST)
		public String getmonthdata1(ModelMap model,HttpServletRequest request){	
			 model.addAttribute("meterMaterData", meterMasterService.findmonthData2(request.getParameter("month"),request,model));	
			 model.addAttribute("selectedMonth",request.getParameter("month"));
			 return "metermaster1";		 
		}
		
		@RequestMapping(value="/addmeter",method=RequestMethod.GET)
		public String addmeter(@ModelAttribute("addmetermaster") Meterdata addmetermaster,HttpServletRequest request,ModelMap model)
		{
			return "addmetermaster";
		}
		@RequestMapping(value="/modemmaster",method=RequestMethod.GET)
		public String modemmaster(@ModelAttribute("addmodemmaster") ModemMaster addmodemmaster,HttpServletRequest request,ModelMap model)
		{
			return "modemmaster";
		}
		
		
		@RequestMapping(value="/disconnection",method=RequestMethod.GET)
		public String disconnection(HttpServletRequest request,ModelMap model)
		{
			model.put("results", "notDisplay");
			model.put("circleVal",masterService.getDistinctCircle(request));
			model.put("subdivlist",masterService.getSubDiv());
			model.put("divlist",masterService.getALLDivisions());
			return "disconnection";
		}
		
		@RequestMapping(value="/addNewMeter",method={RequestMethod.GET,RequestMethod.POST})
		public String addNewMeter(@ModelAttribute("addmetermaster") Meterdata addmetermaster,HttpServletRequest request,ModelMap model,BindingResult result)
		{
			addmetermaster.setDate(new Date());
			//meterdataservice.customSave(addmetermaster);
			
			String meterno=request.getParameter("meter_serial_no");
			System.out.println("mtr--->"+meterno);
			
			MeterLifeCycleEntity lifecycle=new MeterLifeCycleEntity();
			lifecycle.setMeter_no(meterno);
			meterlifecycleservice.customSave(lifecycle);
			model.addAttribute("result","meter Added Successfully");
			model.put("addmetermaster", new Meterdata());
			return "addmetermaster";
		}
		
		@RequestMapping(value="/addNewModem",method={RequestMethod.GET,RequestMethod.POST})
		public String addNewModem(@ModelAttribute("addmodemmaster") ModemMaster addmodemmaster,HttpServletRequest request,ModelMap model,BindingResult result)
		{
			modemmasterservice.save(addmodemmaster);
			return "modemmaster";
		}

		@RequestMapping(value="/modemconfig",method=RequestMethod.GET)
		public String modemconfig(@ModelAttribute("addmodemconfig") ModemConfig addmodemconfig,HttpServletRequest request,ModelMap model)
		{
			model.put("results", "notDisplay");
			model.put("circleVal",masterService.getDistinctCircle(request));
			model.put("subdivlist",masterService.getSubDiv());
			model.put("divlist",masterService.getALLDivisions());
			
			return "modemconfiguration";
		}
		
		@RequestMapping(value="/addmodemconfig",method={RequestMethod.GET,RequestMethod.POST})
		public String addmodemconfig(@ModelAttribute("addmodemconfig") ModemConfig addmodemconfig,HttpServletRequest request,ModelMap model,BindingResult result)
		{
			try {
				modemconfigservice.customsavemdas(addmodemconfig);
				model.put("results", "saved successfull");
			} catch (Exception e) {
				model.put("results", "please try again");
			}
			return "modemconfiguration";
		}
		
		
		@RequestMapping(value="/meterChangeOrder",method=RequestMethod.GET)
		public String meterChangeOrder(@ModelAttribute("meterdata") Meterdata meterdata,ModelMap model,HttpServletRequest request){	
			//model.addAttribute("meterMaterData", meterMasterService.findAll());
			return "meterChangeOrder";
		}
		
		
		@RequestMapping(value="/getMeterDetails/{metrno}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<Object> getMeterDetails(@PathVariable String metrno, HttpServletRequest request,ModelMap model)
		{
			System.out.println("------Get Meter Details----- : "+metrno);
			String res = "";
			List<Object> result =null;
			String qry = "SELECT * FROM meter_data.meter_list  WHERE meter_serial_no ='"+metrno+"'";
			result= postgresMdas.createNativeQuery(qry).getResultList();
			int count = result.size();		
			return result;
		}
		
		
		@RequestMapping(value="/updateMeterDetails",method={RequestMethod.POST,RequestMethod.GET})
		public  String updateMeterDetails(@ModelAttribute ("meterdata")Meterdata meterdata , HttpServletRequest request,ModelMap model)
		{
				try {
					meterdataservice.update(meterdata);
					model.addAttribute("success", "Updated Successfully");
				} catch (Exception e) {
						e.printStackTrace();
				}
			
			return  "meterChangeOrder";
		}
		
		@RequestMapping(value="/updateStatus/{accno}/{status}/{date}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object updateStatus(@PathVariable String accno,@PathVariable String status,@PathVariable String date,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
			{
		 		int count=0;
				MDMLogger.logger.info("In Edit UHBVN User Details ----"+accno+" "+status);
				count=meterMasterService.updatestatus(accno,status,date);
			return count;
				
			}
		
		/*update to meter master billing parameters*/
		//@Scheduled(cron="0 0/1 * * * ?")
		@RequestMapping(value="/callUpdateMeterMaster",method={RequestMethod.POST,RequestMethod.GET})
		public  void callUpdateMeterMaster()
		{
			System.out.println("calling reading update from billhistory to metermaster");
			meterMasterService.updateDataToMetrMaster();		
		}
		
		
		@RequestMapping(value="/getMeterDetailsAcc",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody MeterMaster getMeterDataByAccno(HttpServletRequest request)
		{
			System.out.println("CALAe");
			return  meterMasterService.getMeterDataByAccno(request);
			
		}
		
		
		@RequestMapping(value="/addRemarks",method={RequestMethod.GET})
		public @ResponseBody String addRemarks(HttpServletRequest request,ModelMap model)
		{
			System.out.println("calling addRemarks");
			
			String meterno=request.getParameter("meterno");
			String remark=request.getParameter("remark");
			int rcount=meterMasterService.updateLogDamageIssue(meterno, remark);
			String result="notupdated";
				if(rcount>0)
				{
					result="updated";
				}
			return result;
			
		}
		
		@RequestMapping(value="/getMetrConfigData",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List getEventData(HttpServletRequest request,ModelMap model)
		{
			System.out.println("inside Event Data--"+request.getParameter("meterno"));
			String meterno=request.getParameter("meterno");
			List<MeterLifeCycleEntity> eventData=meterMasterService.getMetrConfigData(meterno);
			return eventData;
		}
			
		
		@RequestMapping(value="/getMetrDetailsAstMngt",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List getMetrDetailsAstMngt(HttpServletRequest request,ModelMap model)
		{
			
			String meterno=request.getParameter("mtrno");
			//System.out.println("inside getMetrDetailsAstMngt--"+meterno);
			List<?> eventData=meterMasterService.getMetrDetails(meterno);
			return eventData;
		}
		
		//zone circle
		@RequestMapping(value="/showCircleMDM/{zone}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showCircle(@PathVariable String zone,HttpServletRequest request,ModelMap model)
		{
			
			System.out.println("divisonn==");
			System.out.println("==--showCircle--==List=="+zone);
			return meterMasterService.getCirclesByZone(zone);
		}
		
		//circle ,division
		@RequestMapping(value="/showDivisionMDM/{zone}/{circle}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showDivision(@PathVariable String zone,@PathVariable String circle,HttpServletRequest request,ModelMap model)
		{
			//System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
			return meterMasterService.getDivisionByCircleMDM(zone, circle);
		}
		//div,subdiv
		@RequestMapping(value="/showSubdivByDivMDM/{zone}/{circle}/{values}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showSubdivByDiv(@PathVariable String zone,@PathVariable String circle,@PathVariable String values,HttpServletRequest request,ModelMap model)
		{
			System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+values);
			return meterMasterService.getSubdivByDivisionByCircleMDM(zone, circle, values);
		}
		/**
		 * purpose: To get circle based on circle code
		 * @param circleCode
		 * @param request
		 * @param model
		 * @return circles in the code
		 */
		@RequestMapping(value="/getCircle/{circleCode}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getCircles(@PathVariable String circleCode,HttpServletRequest request,ModelMap model)
		{
			System.out.println("==--showSubdivByDiv--==List==");
			return meterMasterService.getCirclesByCircleCode(circleCode);
		}
		

		//subdiv,meterlist
		@RequestMapping(value="/showMeterListBySubDiv/{zone}/{circle}/{divison}/{subdivison}",method={RequestMethod.GET,RequestMethod.POST})
		public  @ResponseBody Object consumptionCurveMDAS(@PathVariable String zone, @PathVariable String circle,@PathVariable String divison,@PathVariable String subdivison,HttpServletRequest request,ModelMap model)
		{
		  System.out.println("hitting controller "+circle +"n"+subdivison+"ss"+divison);
		 //model.addAttribute("meterNoList" , metrList);
		 return  meterMasterService.getMeterListBySubDiv(zone,circle,divison,subdivison);
		}
		

		@RequestMapping(value="/getConsumptionData/{mtrno}/{billmonth}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getConsumptionData(@PathVariable String billmonth ,@PathVariable String mtrno , HttpServletRequest request,ModelMap model)
		{
		    System.out.println("billmonth"+billmonth);
			System.out.println("mtrno"+mtrno);
	        model.addAttribute("d4LoadDataMDM",masterService.getd4LoadDataMDM(billmonth,mtrno));
			model.addAttribute("meterNo",mtrno);
			model.addAttribute("formatDate",billmonth);
			return masterService.getd4LoadDataMDM(billmonth,mtrno);
		}
		
		@RequestMapping(value="/showmeternoBySubDivMDM",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showmeternoBySubDiv(HttpServletRequest request,ModelMap model)
		{String division=request.getParameter("division");
		String circle=request.getParameter("circle");
		String zone=request.getParameter("zone");
		String subdivision=request.getParameter("subdivision");
			return meterMasterService.getmeternoBySubDivByDivisionMDM(zone, circle, division, subdivision);
		}
		

		//new module for demo05-12-2018
		@RequestMapping(value="/billdeterminant",method={RequestMethod.GET,RequestMethod.POST})
	    public String billdeterminant(HttpServletRequest request,ModelMap model)
	    
		{
		
			return "billdeterminant";
		}
		
}
