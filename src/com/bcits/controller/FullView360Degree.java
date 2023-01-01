package com.bcits.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.D2Data;
import com.bcits.entity.D3Data;
import com.bcits.entity.D4CdfData;
import com.bcits.entity.D4Data;
import com.bcits.entity.D5Data;
import com.bcits.entity.D9Data;
import com.bcits.service.D2DataService;
import com.bcits.service.D3DataService;
import com.bcits.service.D4DtataService;
import com.bcits.service.D4LoadDataService;
import com.bcits.service.D5DataService;
import com.bcits.service.D9DataService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.XmlImportService;
import com.bcits.utility.MDMLogger;

@Controller
public class FullView360Degree 
{
	@Autowired 
	private D2DataService d2DataService;
	
	@Autowired
	private D5DataService d5DataService;
	
	@Autowired
	private D9DataService d9DataService;
	
	@Autowired
	private XmlImportService xmlImportService;
	
	@Autowired
	private D3DataService d3DataService;
	
	@Autowired
	private D4DtataService d4DtataService;
	
	
	@Autowired
	private D4LoadDataService d4LoadDataService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@RequestMapping(value="/getInstantanousData",method={RequestMethod.GET,RequestMethod.POST})
	public String getInstantanousData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In intantanous ");
		List<D2Data> list=d2DataService.findAll(meterNo,selectedDateName,model);		
		return "360degreeview";
	}
	
	@RequestMapping(value="/getEventData/{meterNo}/{selectedDateName}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getEventData(@PathVariable String meterNo,@PathVariable String selectedDateName,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info("In EventData ");
		System.err.println("event");
		List<D5Data> list=d5DataService.findAll(meterNo,selectedDateName,model);
		return "360degreeview";
	}
	@RequestMapping(value="/getEventData",method={RequestMethod.GET,RequestMethod.POST})
	public String getEventDatanew(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In EventData ");
		List<D5Data> list=d5DataService.findAll(meterNo,selectedDateName,model);
		return "360degreeview";
	}
	@RequestMapping(value="/getTransactions",method={RequestMethod.GET,RequestMethod.POST})
	public String getTransactionsData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In EventData ");
		List<D9Data> list=d9DataService.findAll(meterNo,selectedDateName,model);
		return "360degreeview";
	}
	
	@RequestMapping(value="/getBillingDatas",method={RequestMethod.GET,RequestMethod.POST})
	public String getBillingData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName,HttpSession session)
	{
		String officeType = (String) session.getAttribute("officeType");
		System.out.println("This is the session value for Offive type #######" +officeType );
		
		
		MDMLogger.logger.info("In BillingData ");
		//List<XmlImport> list=xmlImportService.getDetailsBasedonMeterNo(meterNo,selectedDateName,model);		
		meterMasterService.getBillingDataMM(selectedDateName, meterNo,request, model);
		return "360degreeview";
	}
	
	@RequestMapping(value="/getReadingsData",method={RequestMethod.GET,RequestMethod.POST})
	public String getReadingsData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In ReadingsData ");
		List<D3Data> list=d3DataService.getDetailsBasedOnMeterNo(meterNo,selectedDateName,model);			
		return "360degreeview";
	}
	
	//Old Code for All days but not intervals
	/*@RequestMapping(value="/getLoadSurvayData",method={RequestMethod.GET,RequestMethod.POST})
	public String getLoadSurvayData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo)
	{
		MDMLogger.logger.info("In LoadSurvayData ");
		List<D4Data> list=d4DtataService.getDetailsBasedOnMeterNo(meterNo,d4DtataService.getCurrentMonthyear(),d4DtataService.getCurrentYearPreviousMonth());	
		model.put("loadSurveyData", list);	
		model.put("portletTitle", "Load Survey Details");
		model.put("meterNo", meterNo);
		return "360degreeview";
	}*/
	
	
	@RequestMapping(value="/getLoadSurvayData",method={RequestMethod.GET,RequestMethod.POST})
	public String getLoadSurvayData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In LoadSurvayData ");		
		//List<D4CdfData> list=d4LoadDataService.getloadSurveyDetailsBasedOnMeterNo(meterNo,loadSurveydate,model);
		List<D4Data> list=d4DtataService.getDetailsBasedOnMeterNo(meterNo,selectedDateName,d4DtataService.getCurrentYearPreviousMonth(),model);
		return "360degreeview";
	}
	
	//new Code for Every interval in one day
	@RequestMapping(value="/getDailyLoadSurvayData",method={RequestMethod.GET,RequestMethod.POST})
	public String getDailyLoadSurvayData(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String loadSurveydate,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In LoadSurvayData ");		
		List<D4CdfData> list=d4LoadDataService.getloadSurveyDetailsBasedOnMeterNo(meterNo,loadSurveydate,model);
		//List<D4Data> list=d4DtataService.getDetailsBasedOnMeterNo(meterNo,selectedDateName,d4DtataService.getCurrentYearPreviousMonth(),model);
		
		//String billMonth = request.getParameter("selectedDateId");
		
		String kva = "",kwh = "",pf="";
		
		for(int i=0; i < list.size();i++)
		{
			kva = kva+"["+(i+1)+","+list.get(i).getKvaValue()+"],";
			pf = pf+"["+(i+1)+","+list.get(i).getPfValue()+"],";
			kwh = kwh+ "["+(i+1)+","+list.get(i).getKwhValue()+"],";
		}
		if(!kwh.equalsIgnoreCase(""))
			kwh = kwh.substring(0,kwh.length()-1);
		
		if(!kva.equalsIgnoreCase(""))
			kva = kva.substring(0,kva.length()-1);
		
		if(!pf.equalsIgnoreCase(""))
			pf = pf.substring(0,pf.length()-1);
		
		request.setAttribute("kwhValue", kwh);
		request.setAttribute("kvaValue", kva);
		request.setAttribute("pfValue",  pf);
		model.put("selectedMonth", selectedDateName);
		return "360degreeview";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/download360ViewPdf/{meterno}/{month}/{viewCategory}", method ={ RequestMethod.POST,RequestMethod.GET})
	public  String download360ViewPdf(@PathVariable String meterno,@PathVariable String month,@PathVariable String viewCategory,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		MDMLogger.logger.info("In meterno "+meterno+"=-=-=-=-=-"+month+"-=-=-"+viewCategory);	
		if(viewCategory.equalsIgnoreCase("BilledData"))
		{
			meterMasterService.downloadBilledDataPdf(meterno, month, model,response);
		}
		/*else if(viewCategory.equalsIgnoreCase("LoadSurveyDatewise"))
		{
			d4DtataService.downloadLoadSurveyDataPdf(meterno,month,model,response);
		}*/
		else if(viewCategory.equalsIgnoreCase("LoadSurveyIpwise"))
		{
			d4DtataService.downloadLoadSurveyIpPdf(meterno,month,model,response);
		}
		else
		{
			d2DataService.download360ViewPdf(meterno, month, model,response);
		}
		return null;
	}
	
	@RequestMapping(value="/getLoadSurveyDatewise",method={RequestMethod.GET,RequestMethod.POST})
	public String getLoadSurveyDatewise(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In LoadSurvayData ");		
		List<Object[]> list1=d4DtataService.getLoadSurveyDatewise(meterNo,selectedDateName,model);
		model.put("loadSurveyIpWise", list1);
		return "360degreeview";
	}
}
