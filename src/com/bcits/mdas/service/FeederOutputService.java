package com.bcits.mdas.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.FeederOutputEntity;
import com.bcits.service.GenericService;

public interface FeederOutputService extends GenericService<FeederOutputEntity> 
{
	List<?> getCircle();
	List<?> getDistrictByCircle(String circle);
	List<?> getSubStationByDistrict(String circle,String district);
	List<?> getFeederByStation(String circle,String district,String feeder);
	Object getFeederAllData(String id);
	byte[] getImage(HttpServletRequest request, HttpServletResponse response, int id,String imagetype) ;
	List<Map<String, Object>> getSurveyData(String fromdate,ModelMap model);
	List<Map<String, Object>> getFeederAllDAta(String fromdate, ModelMap model);
	List<Map<String, Object>> getSurveyAllData(ModelMap model);
	List<Map<String, Object>> getSurveyDataByZone(ModelMap model,String zone);
	List<Map<String, Object>> getSurveyDataByCircle(ModelMap model,String zone,String state);
	List<Map<String, Object>> getFeederData(ModelMap model,String state,String zone,String circle);
	List<Map<String, Object>> getSubDivisionData(ModelMap model, String state, String zone,String circle, String division);
	List<FeederOutputEntity> findDistinctZones();
	List<FeederOutputEntity> findDistinctCircle(String zone);
	List<FeederOutputEntity> findDistinctDivision(String zone,String circle);
	List<FeederOutputEntity> findDistinctSubDivision(String zone,String circle,String division);
	List<FeederOutputEntity> findDistinctSubstation(String zone,String circle,String division,String subdivision);
	List<FeederOutputEntity> findAllFeedersforModem(String zone,String circle,String division,String subdivision,String substation);
	Object getViewOnImageMtrData(HttpServletRequest request, String mtrNumber,ModelMap model);
	byte[] findOnlyImage(ModelMap model, HttpServletRequest request,HttpServletResponse response, String mtrNumber);
	byte[] getConsumerImage(HttpServletRequest request, HttpServletResponse response, int id, String imagetype);
		

	
	
}
