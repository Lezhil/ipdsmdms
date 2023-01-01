package com.bcits.mdas.mqtt;

import java.util.Date;

import javax.servlet.FilterConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.MasterMainService;

class UpdateMasterMain extends Thread{

	String imei;String meterNumber;/*String mtrMake;*/
	
	@Autowired
	private MasterMainService mainService;

	public UpdateMasterMain(FilterConfig filterConfig,String imei, String meterNumber) {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		this.imei = imei;
		this.meterNumber = meterNumber;
		/*this.mtrMake = mtrMake;*/
	}
	
	@Override
	public void run() {
		try {
//			MasterMainEntity mainEntity=mainService.getEntityByImeiNoAndMtrNO(imei, meterNumber);
			MasterMainEntity mainEntity=mainService.getEntityByMtrNO(meterNumber);
			if(mainEntity!=null) {
				/*mainEntity.setMtrmake(mtrMake);*/
				mainEntity.setLast_communicated_date(new Date());
				mainService.customupdateBySchema(mainEntity,"postgresMdas");
			}
			
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		super.run();
	}
	
}
