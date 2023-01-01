package com.bcits.mdas.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.SubstationOutputEntity;
import com.bcits.service.GenericService;

public interface SubstationOutputService extends GenericService<SubstationOutputEntity> 
{

	 List<?> getSubStationBYdistrict(String district);
	 byte[] getImage(HttpServletRequest request,HttpServletResponse response, int id,String imagetype);
	 List<Map<String, Object>> getSubstationAllDAta(String fromdate,String district,ModelMap model);
	
}
