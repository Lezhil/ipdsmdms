package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.service.GenericService;

public interface ModemCommunicationService extends GenericService<ModemCommunication>
{
	List<?> getActInactModemCount();
	List<String> getInactiveIMEI();
	List<?> getInactiveModemDetails(List<String> InactiveModems);
	List<?> getActInactModemCountSublevel();
	List<?> getFailUploadMtrDetails();
	List<?> getInactiveModemDetailsZone(String zone);
	List<?> getFailUploadMtrDetailsZone(String zone);
	public ModelMap getFdrTimeStamps(String METERNO, String MODEM) ;
	public ModelMap modemInformation(String imei);
	List<?> getTotalMeterDetails(String selectedLevel, String selectedLevelName);
	List<?> getActInactModemCount(String type, String value);
	List<?> getActInactModemCountSublevel(String string, String string2,String value);
	List<?> getModelMngtDetailsBySS(String zone,String circle, String division, String subdiv, String subStation, ModelMap model, HttpServletRequest request);
	List<?> getSubstationBySubDiv(String zone,String circle, String division, String subdiv, ModelMap model, HttpServletRequest request);
	Object getTotalMeterDetailsCalender(String selectedDate);
	Object getTotalInactiveMeterDetailsCalender(String selectedDate);
	Object getAllMetersBasedOn(String zone, String circle, String division, String subdiv);
	List<?> getAllMetersBasedOnZoneCircle(String zone, String circle,String towncode,String location);
	Object getTotalMetersDetailsCalender(String date);
}
