package com.bcits.mdas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.MasterMainService;

@Controller
public class SpecialMeters {
	@Autowired
	MasterMainService mms;
	@RequestMapping(value="/netMeter",method={RequestMethod.GET,RequestMethod.POST})
	public String netMeter(Model model){
		return "netMeter";
	}
	@Transactional
	@RequestMapping(value="/preMeter/{mtrno}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object preMeterUpdate(@PathVariable String mtrno ){
		String query = "update meter_data.master_main set meter_type='prepaid' where mtrno='"+mtrno+"'";
		int i = 0;
		try 
		{
			i = mms.getCustomEntityManager("postgresMdas").createNativeQuery(query).executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		 /*int value = mms.
				 getCustomEntityManager("postgresMdas").createNativeQuery(query).executeUpdate();*/
		return String.valueOf(i);
	}
	@RequestMapping(value="/preMeterList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object preMeterList( ){
		String query = "select * from  meter_data.master_main where meter_type='prepaid' ";
		 List<Object[]> l= mms.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		return l;
	}
	@RequestMapping(value="/allMeterList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object allMeterList( ){
		String query = "select mtrno from  meter_data.master_main where meter_type IS NULL ";
		 List<Object[]> l= mms.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		return l;
	}


}
