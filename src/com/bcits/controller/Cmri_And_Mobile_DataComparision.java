package com.bcits.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.CmriAndMobileService;
import com.bcits.service.MobileGenStatusService;

@Controller
public class Cmri_And_Mobile_DataComparision {

	
	@Autowired
	private CmriAndMobileService cmriAndMobileService;
	
	
	@Autowired
	private MobileGenStatusService mobileGenStatusService;
	
	@RequestMapping(value="/mobileDataComparision",method=RequestMethod.GET)
	public String fulDegree(HttpServletRequest request,ModelMap model)
	{
		System.out.println("inside mobileDataComparision controller");
		
		List getMobileStatus= mobileGenStatusService.getALLMobileStatus();
		System.out.println("getMobileStatus-->"+getMobileStatus);
		model.addAttribute("getMobileStatus" ,getMobileStatus);
		model.addAttribute("result" ,"nodata");
		return "mobileDataComparision";
	}
	
	@RequestMapping(value="/getMobileAllData",method={RequestMethod.GET,RequestMethod.POST})
	public String getAllData(HttpServletRequest request,ModelMap model)
	{
		System.out.println("inside getMobileAllData controller");
		
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		
		String billmonthParam = request.getParameter("billmonth");
		int billmonth = Integer.parseInt(billmonthParam);
		System.out.println("billMonth is : "+billmonth);
		String meterno=request.getParameter("meterno");
		System.out.println("meterno-->"+meterno);
		
		List getMobileStatus= mobileGenStatusService.getALLMobileStatus();
		
		model.addAttribute("getMobileStatus" ,getMobileStatus);
		BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
		BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
		/*System.out.println("cdf_idMobile-if-->"+cdf_idMobile);
		System.out.println("cdf_idMobile-if-->"+cdf_idCmri);*/
		
		//model.addAttribute("metrno",meterno);
		
		
		List<?> D1_dataMobileAndCMRI=null;
		List<?> D2_dataMobileAndCMRI=null;
		List<?> D3_dataMobileAndCMRI=null;
		List<?> D4_Load_dataMobileAndCMRI=null;
		
		
		//D1_DATA
		
		/*D1_dataMobileAndCMRI=cmriAndMobileService.findD1_DataMobileCmri(cdf_idMobile, cdf_idCmri);
		System.out.println("D1_dataMobileAndCMRI-->"+D1_dataMobileAndCMRI);
		model.addAttribute("D1_dataMobileAndCMRI",D1_dataMobileAndCMRI);*/
		
		
		//d2-data
		/*D2_dataMobileAndCMRI=cmriAndMobileService.findD2_DataMobileCmri(cdf_idMobile, cdf_idCmri);
		model.addAttribute("D2_dataMobileAndCMRI",D2_dataMobileAndCMRI);
		
		//d3-data
		D3_dataMobileAndCMRI=cmriAndMobileService.findD3_DataMobileCmri(cdf_idMobile, cdf_idCmri);
		model.addAttribute("D3_dataMobileAndCMRI",D3_dataMobileAndCMRI);
		
		model.addAttribute("D4_dataMobileAndCMRI",cmriAndMobileService.findD4_DataMobileCmri(cdf_idMobile, cdf_idCmri, model));*/
		/*model.addAttribute("D5_dataMobileAndCMRI",cmriAndMobileService.findD5_DataMobileCmri(cdf_idMobile, cdf_idCmri, model));
		model.addAttribute("meterno" ,meterno);*/
		
		
		//D4_Load_data
		D4_Load_dataMobileAndCMRI=cmriAndMobileService.findD4_Load_DataMobileCmri(cdf_idMobile, cdf_idCmri, model);
		System.out.println("D4_Load_dataMobileAndCMRI-->"+D4_Load_dataMobileAndCMRI);
		model.addAttribute("D4_Load_dataMobileAndCMRI",D4_Load_dataMobileAndCMRI);
		model.addAttribute("result" ,"data");
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "mobileDataComparision";
	}
	//D1-DATA
	@RequestMapping(value="/getALL_D1data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List  getALLD1data(HttpServletRequest request,ModelMap model)throws NullPointerException
	{
		System.out.println("inside getMobileAll D1 dataata controller");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String billmonthParam = request.getParameter("billmonth");
		int billmonth = Integer.parseInt(billmonthParam);
		System.out.println("billMonth is : "+billmonth);
		String meterno=request.getParameter("meter").trim();
		System.out.println("meterno-->"+meterno);
		List<?> D1_dataMobileAndCMRI=null;
		try {
			BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
			BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
			System.out.println("cdf_id----Mobile-id-->"+cdf_idMobile);
			System.out.println("cdf_id---CMRI-if-->"+cdf_idCmri);
			//D1_DATA
			D1_dataMobileAndCMRI=cmriAndMobileService.findD1_DataMobileCmri(cdf_idMobile, cdf_idCmri);
			System.out.println("D1_dataMobileAndCMRI-->"+D1_dataMobileAndCMRI);
			
			//model.addAttribute("D1_dataMobileAndCMRI",D1_dataMobileAndCMRI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D1_dataMobileAndCMRI;
	}
	
	//D2-DATA
		@RequestMapping(value="/getALL_D2data",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List  getALL_D2data(HttpServletRequest request,ModelMap model)
		{
			System.out.println("inside getMobileAll D2 datacontroller");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String billmonthParam = request.getParameter("billmonth");
			int billmonth = Integer.parseInt(billmonthParam);
			System.out.println("billMonth is : "+billmonth);
			String meterno=request.getParameter("meter");
			System.out.println("meterno-->"+meterno);
			List<?> D2_dataMobileAndCMRI=null;
			try {
				BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
				BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
				System.out.println("cdf_idMobile-id-->"+cdf_idMobile);
				System.out.println("cdf_id CMR-id-->"+cdf_idCmri);
				//D1_DATA
				D2_dataMobileAndCMRI=cmriAndMobileService.findD2_DataMobileCmri(cdf_idMobile, cdf_idCmri);
				System.out.println("D2_dataMobileAndCMRI-->"+D2_dataMobileAndCMRI);
				//model.addAttribute("D1_dataMobileAndCMRI",D1_dataMobileAndCMRI);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return D2_dataMobileAndCMRI;
		}
		
		//D3-DATA
				@RequestMapping(value="/getALL_D3data",method={RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List  getALL_D3data(HttpServletRequest request,ModelMap model)
				{
					System.out.println("inside getMobileAll D3 datacontroller");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					String billmonthParam = request.getParameter("billmonth");
					int billmonth = Integer.parseInt(billmonthParam);
					System.out.println("billMonth is : "+billmonth);
					String meterno=request.getParameter("meter");
					System.out.println("meterno-->"+meterno);
					List<?> D3_dataMobileAndCMRI=null;
					try {
						BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
						BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
						System.out.println("cdf_idMobile-if-->"+cdf_idMobile);
						System.out.println("cdf_idMobile-if-->"+cdf_idCmri);
						D3_dataMobileAndCMRI=cmriAndMobileService.findD3_DataMobileCmri(cdf_idMobile, cdf_idCmri);
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return D3_dataMobileAndCMRI;
				}
				
	//D4-DATA
				@RequestMapping(value="/getALL_D4data",method={RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List  getALLD4data(HttpServletRequest request,ModelMap model)throws NullPointerException
				{
					List D4result1=new ArrayList<>();
					
					System.out.println("inside getMobileAll D4 dataata controller");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					String billmonthParam = request.getParameter("billmonth");
					int billmonth = Integer.parseInt(billmonthParam);
					System.out.println("billMonth is : "+billmonth);
					String meterno=request.getParameter("meter").trim();
					System.out.println("meterno-->"+meterno);
					List<?> D4_dataMobileAndCMRI=null;
					
					List<?> d4_IPIntervalList=null;
					
					List<Object> d4Datalist=new ArrayList<>();
					try {
						BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
						BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
						
						model.addAttribute("cdf_idMobile", cdf_idMobile);
						model.addAttribute("cdf_idCmri", cdf_idCmri);
						
						System.out.println("cdf_id----Mobile-id-->"+cdf_idMobile);
						System.out.println("cdf_id---CMRI-if-->"+cdf_idCmri);
						//D4_DATA
						
				        D4_dataMobileAndCMRI=cmriAndMobileService.findD4_DataMobileCmri(cdf_idMobile, cdf_idCmri, model);
						
						System.out.println("D4_dataMobileAndCMRI------>"+D4_dataMobileAndCMRI.size());
						
						d4_IPIntervalList=cmriAndMobileService.getIpIntervalsD4();
						
						d4Datalist.add(cdf_idMobile);
						d4Datalist.add(cdf_idCmri);
						d4Datalist.add(D4_dataMobileAndCMRI);
						d4Datalist.add(d4_IPIntervalList);
						
						//model.addAttribute("D1_dataMobileAndCMRI",D1_dataMobileAndCMRI);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return d4Datalist;
				}
				
				
				
	// D5 Data			
		
	@RequestMapping(value="/getAlld5Data",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public List d5DataMethod(HttpServletRequest request,ModelMap model)
		{
		String billMonth=request.getParameter("billmonth");
		String meterno=request.getParameter("meter");
		int billmonth = Integer.parseInt(billMonth);
		List<?> D5_dataMobileAndCMRI=null;
		List d5Data=new ArrayList<>();
		try {
			BigDecimal cdf_idMobile=cmriAndMobileService.findCDF_IDMobile(meterno, billmonth);
			BigDecimal cdf_idCmri=cmriAndMobileService.findCDF_IDCmri(meterno, billmonth);
			
			System.out.println("Cdf_mobile_Id in D5 Data------->"+cdf_idMobile);
			System.out.println("Cdf_CMRI in D5 Data------->"+cdf_idCmri);
			
		    D5_dataMobileAndCMRI=cmriAndMobileService.findD5_DataMobileCmri(cdf_idMobile, cdf_idCmri, model);
		    d5Data.add(cdf_idMobile);
		    d5Data.add(cdf_idCmri);
		    d5Data.add(D5_dataMobileAndCMRI);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			return d5Data;
		}
	
	
	@RequestMapping(value="/getD4dataByDayProfile",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List  getD4dataByDayProfile(HttpServletRequest request,ModelMap model)
	{
		
		System.out.println("inside getMobileAllData controller");
		
		List D4_DataOnDayProfileDate=null;
		
		String cmriCdfId=request.getParameter("cmri");
		String mobileCdfId=request.getParameter("mobile");
		String dayProfileDate=request.getParameter("dayProfileDate");
		System.out.println("cmriCdfId-->"+cmriCdfId);
		System.out.println("mobileCdfId-->"+mobileCdfId);
		System.out.println("dayProfileDate-->"+dayProfileDate);
		
		
		try {
			
			//findD4_DataOnDayProfileDate
			
			D4_DataOnDayProfileDate=cmriAndMobileService.findD4_DataOnDayProfileDate(mobileCdfId, cmriCdfId, dayProfileDate.trim());
			System.out.println("D4_DataOnDayProfileDate-->"+D4_DataOnDayProfileDate);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D4_DataOnDayProfileDate;
	}
	
	@RequestMapping(value="/getD4IPIntervalByDayProfile",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List  getD4LoadDataByDayProfile(HttpServletRequest request,ModelMap model)
	{
		
		System.out.println("inside getD4IPIntervalByDayProfile controller");
		
		List D4_LoadInterval=null;
		
		String cmriCdfId=request.getParameter("cmri");
		String mobileCdfId=request.getParameter("mobile");
		String dayProfileDate=request.getParameter("dayProfileDate");
		System.out.println("cmriCdfId-->"+cmriCdfId);
		System.out.println("mobileCdfId-->"+mobileCdfId);
		System.out.println("dayProfileDate-->"+dayProfileDate);
		
		
		try {
			
			//findD4_DataOnDayProfileDate
			
			D4_LoadInterval=cmriAndMobileService.D4_Load_DatagetIntervals(mobileCdfId, cmriCdfId, dayProfileDate.trim());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D4_LoadInterval;
	}
	
	
	//getD4LoadView
	@RequestMapping(value="/getD4LoadView",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List  getD4LoadView(HttpServletRequest request,ModelMap model)
	{
		
		System.out.println("inside /getD4LoadView controller");
		
		List D4_LoadAlldata=null;
		
		
		String pdate=request.getParameter("pdate");
		String interv=request.getParameter("interv");
		String cmriCdfId=request.getParameter("cmri");
		String mobileCdfId=request.getParameter("mobile");
		
		System.out.println("pdate-->"+pdate);
		System.out.println("interv-->"+interv);
		System.out.println("cmriCdfId-->"+cmriCdfId);
		System.out.println("mobileCdfId-->"+mobileCdfId);
		
		
		try {
			
			//D4_load ALL Based on ip and dayprofile date
			System.out.println("interval-->"+interv);
			D4_LoadAlldata=cmriAndMobileService.D4_Load_DataALL(mobileCdfId, cmriCdfId, pdate, interv);
			System.out.println("D4_LoadAlldata--"+D4_LoadAlldata);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D4_LoadAlldata;
	}
	
	@RequestMapping(value="/getD5dataByDayProfile",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List  getD5dataByDayProfile(HttpServletRequest request,ModelMap model)
	{
		
		
		System.out.println("inside getMobileAllData controller");
		
		List D5_DataOnDayProfileDate=null;
		
		String cmriCdfId=request.getParameter("cmri");
		String mobileCdfId=request.getParameter("mobile");
		String dayProfileDate=request.getParameter("dayProfileDate");
		System.out.println("cmriCdfId-->"+cmriCdfId);
		System.out.println("mobileCdfId-->"+mobileCdfId);
		System.out.println("dayProfileDate-->"+dayProfileDate);
		
		try {
			
			//findD4_DataOnDayProfileDate
			
			D5_DataOnDayProfileDate=cmriAndMobileService.findD5_DataOnDayProfileDate(mobileCdfId, cmriCdfId, dayProfileDate.trim());
			System.out.println("D5_DataOnDayProfileDate-->"+D5_DataOnDayProfileDate);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D5_DataOnDayProfileDate;
	}
}
