package com.bcits.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.entity.DTMultipleMetersEntity;
import com.bcits.service.DTMultipleMetersService;

@Controller
public class DTMultipleMetersConroller {
	
	@Autowired
	private DTMultipleMetersService dTMultipleMetersService;
	
	@RequestMapping(value = "/insertDtMulipleMtrs", method = { RequestMethod.GET, RequestMethod.POST })
	public void  addBoundaryDetailsData(HttpServletRequest request, Model model) {
		
	System.out.println("inside insertDtMulipleMtrs contrller");
	List list= dTMultipleMetersService.getDtMultipleMtrsData();
	
	if(list.size()>0){
		for (int i = 0; i < list.size(); i++) {
			
			try {
			Object[] obj=(Object[]) list.get(i);
			DTMultipleMetersEntity dt=new DTMultipleMetersEntity();
			dt.setTown(String.valueOf(obj[1]));
			dt.setDtname(String.valueOf(obj[2]));
			dt.setDttpid(String.valueOf(obj[3]));
			if(obj[4]!=null){
				
			dt.setIptime(Timestamp.valueOf(obj[4]+""));
			}
			if(obj[5]!=null){
			dt.setKwh( Double.parseDouble(obj[5]+""));
			}
			if(obj[6]!=null){
			dt.setKvah(Double.parseDouble(obj[6]+""));
			}
			if(obj[7]!=null){
			dt.setKw( Double.parseDouble(obj[7]+""));
			}
			if(obj[8]!=null){
			dt.setKva( Double.parseDouble(obj[8]+""));
			}
			if(obj[9]!=null){
			dt.setPf(Double.parseDouble(obj[9]+""));
			}
			dt.setTimestamp(new Timestamp(System.currentTimeMillis()));
			System.out.println("count--"+i);
			try {
				dTMultipleMetersService.customUpdate(dt);
			} catch (Exception e) {
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	
	}
	
	
	}

}
