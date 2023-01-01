package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.ui.ModelMap;


import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.service.GenericService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public interface FeederMasterService extends GenericService<FeederMasterEntity> {

	  Object getAllFeedersMobile(String imeiNo);

	List<FeederMasterEntity> getDistinctCircle();

	List <Object[]> getDivisionByCircle(String circle, ModelMap model);

	List <Object[]> getSubdivByDivisionByCircle(String circle, String division,ModelMap model);

	List <Object[]> getFeederBySubByDiv(String circle, String division, String subdiv,ModelMap model);

	List<FeederMasterEntity> findAll(String district);
	List<FeederMasterEntity> findDistricts();

	List<Object[]> getDistinctFeederCode(String sdocode,String feederName, ModelMap model,HttpServletRequest request);

	int updateMrImeiByFeederNo(String sdoNo,String fdrName,String fdrNo, String mrNo, String imeiNo,HttpServletRequest request);

	int updateNullMrImeiByFeederNo(String deviceidPk, HttpServletRequest request);

	List<Object[]> getDistinctFeederName(String sdocode, ModelMap model,HttpServletRequest request);

	List<FeederMasterEntity> getFeederData(String mtrNo);
	
	List<Object[]> getMeterDetailsForXml();
	
	List<?> getCorporateDetailCount();
	
	int getModemCount();

	int getUploadedCount();

	List<?> getFailUploadCount();

	int getTtlMtrCount();

	List<?> getZones();

	List<?> getModemCountZone();

	List<?> getFailUploadCountSublevel();

	public List<?> getFeederInfo(ModelMap model, HttpServletRequest request);

	public  ModelMap getFdrMeterInfo(String FEEDERNAME , String FEEDERCODE) ;

	List<?> getZoneDetailCount(String type);

	List<?> getCircles(String zone);

	List<?> getFailUploadCount(String type, String value);

	List<?> getFailUploadCountSublevel(String type, String sub_type,String value);

	List<?> getCircleDetailCount(String zone);

	List<?> getDivisionDetailCount(String zone);

	List<?> getSubDivisionDetailCount(String zone);

	List<?> getDivisions(String value);

	List<?> getSubDivisions(String value);

	List<?> getSubStations(String value);

	List<?> getSubStationDetailCount(String subdivision);

	List<FeederMasterEntity> getDistinctZone();

	List<FeederMasterEntity> getCircleByZone(String zone, ModelMap model);

	List<FeederMasterEntity> getDivisionByCircle(String zone, String circle, ModelMap model);

	List<FeederMasterEntity> getSubdivByDivisionByCircle(String zone, String circle,String division, ModelMap model);

	List<FeederMasterEntity> getSStationBySubByDiv(String zone, String circle, String division,String subdiv, ModelMap model);

	List<FeederMasterEntity> getDistinctSubDivision();

	List<FeederMasterEntity> getSStationBySub(String subdiv, ModelMap model);

	Object getD1GData(String meterNumber);

	//graph
	List<?> getDistinctSubStation(String queryWhere);
		
	List<?> getFeederBySubstn(String substation);
		
	JSONArray getTimeAndPhases(String feederName,String date, String zone, String circle, String division, String subdiv) throws ParseException;

	List<FeederMasterEntity> getDistinctZoneByLogin(String userName,ModelMap model, HttpServletRequest request);

	List<?> getPowerStatusReportData(String zone, String circle, String division,
			String subdiv, String from, String to);

	List <FeederMasterEntity> showDistrictByCircle(String zone, String circle);

	List<?> getDistinctMtrMake();

	List<?> getDistinctSubstations(String zone, String circle, String division, String subdiv);

	List<?> getDistinctFeeders(String zone, String circle, String division, String subdiv, String substn);

	//distinct Circles
	List<?> getDistinctCircle(HttpServletRequest request, ModelMap model);
	List<?> getDivisionUnderCircle(String request, ModelMap model);
	List<?> getSubdivUnderDivision(String circle, String division,
			ModelMap model);

	List<FeederMasterEntity> getDistinctAmiZone();
	
	List<?> getAtcLosses(String billmonth,String period,String townCode,HttpSession session);
	
	List<?> getTdLosses(String billmonth,String period,String townCode);
	
    List<?> getBillingEffLosses(String billmonth,String period,String townCode);
	
    List<?> getCollectionEffLosses(String billmonth,String period,String townCode);
    
    List<?> getCircleByZone();
    
    List<?> getDivision(String circle);

	List<?> getMeterDetails(String zone, String circle, String division, String subdivision, String meterType,
			String townCode);
	
	List<?> getOldMtrDataforMtrChange(String meterno);

	List getdtTpIDbyTowncode(String townCode);

	List<?> getDTMeterChangeDetails(String townCode);

	List<?> getBoundaryMeterChangeDetails(String townCode);

	List<?> getFeederMeterChangeDetails(String townCode);

	List<FeederMasterEntity> getZoneByLogin(String officeCode);

	void getChangeMeterPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String meterType, String townCode);

	void getFeederMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String towncode, String townname, String metertype);

	void getDTMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl, String dvn,
			String sdiv, String towncode, String townname, String metertype);

	void getBoundaryMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String towncode, String townname, String metertype);

	List<?> getCommFailList(String zone, String circle, String towncode);

	List<?> getTotalConsumersFailData(String id, String towncode);
	
	
	List<?> getWorstATClossesFeeder(String Billmonth);
	List<?> getBestATClossesFeeder(String Billmonth);
	List<?> getWorstATClossesDT(String Billmonth);
	List<?> getBestATClossesDT(String Billmonth);

	
	List<?> getFeederLess15AtcData(String billmonth);
	List<?> getFeederGreater15AtcData(String billmonth);
	List<?> getDTLess15AtcData(String billmonth);
	List<?> getDTGreater15AtcData(String billmonth);

	List<?> getmultipleMonthatclossFeederdata(String feederTpId, String fromMonth, String toMonth,String period);
	List<?> getmultipleMonthatclossDtdata(String dtTpId, String fromMonth, String toMonth,String period);

	void unmappedmeterspdf(HttpServletRequest request, HttpServletResponse response);

	
	
}
