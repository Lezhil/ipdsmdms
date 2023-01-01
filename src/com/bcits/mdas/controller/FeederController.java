package com.bcits.mdas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemLifeCycleEntity;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemLifeCycleService;
import com.bcits.mdas.service.ModemMasterServiceMDAS;

@Controller
public class FeederController {

	@Autowired
	private ModemMasterServiceMDAS modemMasterService;
	
	@Autowired
	private FeederMasterService feederService;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private ModemLifeCycleService lifeCycleService;
	
		
	int addMasterFlag=0;
	
	@RequestMapping(value="/feederMaster",method={RequestMethod.GET,RequestMethod.POST})
	public String totalMeterDetails(@ModelAttribute("mainEntity") MasterMainEntity mainEntity,ModelMap model, HttpServletRequest request)//get active and inactive count
	{
		List<FeederMasterEntity> zoneList=feederService.getDistinctZone();
		List<?> mtrMakeList=feederService.getDistinctMtrMake();
		List<?> allImei=modemMasterService.findNotInstalledIMEI();
		
		model.put("zoneList", zoneList);
		model.put("mtrmakeList", mtrMakeList);
		model.put("allImei", allImei);
		
		if(addMasterFlag==1) {
			model.put("msg", "New Feeder Details Added Successfully!");
			addMasterFlag=0;
		} else if(addMasterFlag==2) {
			model.put("msg", "OOPs! Something went wrong.");
			addMasterFlag=0;
		}
		
		return "feederMaster";
	}
	
	@RequestMapping(value = "/addNewFeederMaster", method = {RequestMethod.GET, RequestMethod.POST})
	public String addNewFeederMaster(@ModelAttribute("mainEntity") MasterMainEntity mainEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  {
		
		try {
			String insdate=request.getParameter("installation_date");
			mainEntity.setInstallation_date(new SimpleDateFormat("yyyy-MM-dd").parse(insdate));
			/*ObjectMapper om=new ObjectMapper();
			System.out.println(om.writeValueAsString(mainEntity));*/
			masterMainService.save(mainEntity);
			addMasterFlag=1;
		} catch (Exception e) {
			e.printStackTrace();
			addMasterFlag=2;
		}
		
		try {
			ModemLifeCycleEntity cycleEntity=new ModemLifeCycleEntity();
			cycleEntity.setImei(mainEntity.getModem_sl_no());
			cycleEntity.setLocation(mainEntity.getZone()+">"+mainEntity.getCircle()+">"+mainEntity.getDivision()+">"+mainEntity.getSubdivision()+">"+mainEntity.getSubstation());
			cycleEntity.setEvents("Attached with Meter No : "+mainEntity.getMtrno());
			cycleEntity.setEdate(new Date());
			lifeCycleService.save(cycleEntity);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/feederMaster";
		
	}
	
}
