package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.ui.ModelMap;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.MeterChangeTransHistory;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.service.GenericService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public interface MasterMainService extends GenericService<MasterMainEntity> {

	Object getAllFeedersMobile(String imeiNo);

	List<MasterMainEntity> getDistinctCircle();

	List<Object[]> getDivisionByCircle(String circle, ModelMap model);

	List<Object[]> getSubdivByDivisionByCircle(String circle, String division, ModelMap model);

	List<Object[]> getFeederBySubByDiv(String circle, String division, String subdiv, ModelMap model);

	List<MasterMainEntity> findAll(String district);

	List<MasterMainEntity> findDistricts();

	List<Object[]> getDistinctFeederCode(String sdocode, String feederName, ModelMap model, HttpServletRequest request);

	int updateMrImeiByFeederNo(String sdoNo, String fdrName, String fdrNo, String mrNo, String imeiNo,
			HttpServletRequest request);

	int updateNullMrImeiByFeederNo(String deviceidPk, HttpServletRequest request);

	List<Object[]> getDistinctFeederName(String sdocode, ModelMap model, HttpServletRequest request);

	List<MasterMainEntity> getFeederData(String mtrNo);
	
	List<MasterMainEntity> getFeederDataInfo(String mtrNo,String zone,String circle);
	
	List<MasterMainEntity> getMeterDataByKno(String kno);
	
	List<?> getMeterData(String mtrNo);

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

	public ModelMap getFdrMeterInfo(String FEEDERNAME, String FEEDERCODE);

	List<?> getZoneDetailCount(String type);

	List<?> getCircles(String zone);

	List<?> getFailUploadCount(String type, String value);

	List<?> getFailUploadCountSublevel(String type, String sub_type, String value);

	List<?> getCircleDetailCount(String zone);

	List<?> getDivisionDetailCount(String zone);

	List<?> getSubDivisionDetailCount(String zone);

	List<?> getDivisions(String value);

	List<?> getSubDivisions(String value);

	List<?> getSubStations(String value);

	List<?> getSubStationDetailCount(String subdivision);

	List<MasterMainEntity> getDistinctZone();

	List<MasterMainEntity> getCircleByZone(String zone, ModelMap model);

	List<MasterMainEntity> getDivisionByCircle(String zone, String circle, ModelMap model);

	List<MasterMainEntity> getSubdivByDivisionByCircle(String zone, String circle, String division, ModelMap model);

	List<MasterMainEntity> getSStationBySubByDiv(String zone, String circle, String division, String subdiv,
			ModelMap model);

	List<MasterMainEntity> getDistinctSubDivision();

	List<MasterMainEntity> getSStationBySub(String subdiv, ModelMap model);

	Object getD1GData(String meterNumber);

	// graph
	List<?> getDistinctSubStation(String queryWhere);

	List<?> getFeederBySubstn(String substation);

	JSONArray getTimeAndPhases(String feederName, String date) throws ParseException;

	List<MasterMainEntity> getDistinctZoneByLogin(String userName, ModelMap model, HttpServletRequest request);
	
	List<MasterMainEntity> getFeedersBasedOn(String zone, String circle, String division, String subdiv);
	List<String> getSubStationsBasedOn(String zone, String circle, String division, String subdiv);
	List<MasterMainEntity> getFeedersBasedOnImei(String imei);
	List<MasterMainEntity> getFeedersBasedOnUniqueImei(String imei);
	List<MasterMainEntity> getFeedersBasedOnMeterNo(String mtrno);
	MasterMainEntity getEntityByImeiNoAndMtrNO(String imei, String mtrno);

	int updateMasterForAllChangesMeterNo();

	List<?> executeSelectQueryRrnList(String sql);
	
	Object[] getAllModemDetails();
	
	// YOGESH METHODS
		List<MasterMainEntity> findDistinctZones();
		List<MasterMainEntity> findDistinctCircle(String zone);
		List<MasterMainEntity> findDistinctDivision(String zone,String circle);
		List<MasterMainEntity> findDistinctSubDivision(String zone,String circle,String division);
		List<MasterMainEntity> findDistinctSubstation(String zone,String circle,String division,String subdivision);
		List<MasterMainEntity> findAllFeedersforModem(String zone,String circle,String division,String subdivision,String substation);

		void updateImeiLastComm();

		void updateInstallationDate();

		MasterMainEntity getEntityByAccnoSubstation(String accno, String subdivision);

		List<MasterMainEntity> getFeederDataAccno(String parameter);

		List<MasterMainEntity> getFeedersBasedOnAccno(String accno);
		
	//TO CHECK METER NO EXISTANCE IN MASTER_MAIN
		public int checkMeterExist(String oldmeterNo);
		
		public List<MasterMainEntity> getOldMeterData(String oldMeterNo);

		List<MasterMainEntity> getmeters();

		MasterMainEntity getEntityByMtrNO(String mtrno);

		List<?> getAllSubdivisions();

		List<MasterMainEntity> getDataByMeterType(String string);

		List<MasterMainEntity> getConsumptionRecords(String zone);
		
		List<?> getViewDataBySubdivision(HttpServletRequest req);

		MasterMainEntity getFeederDetailsByID(String mla);

		List<String> getAllMeterNumByCategoryAndSubDIV(String subDiv,String fdrCategory);

		List<?> getallcirclelist();

		Object getSubdivisionbycircle(String circle);

		List<Object[]> getALLcriticaldata();
		

		Object getSurveyDataImages(String kno);

		byte[] findOnlyImage(ModelMap model, HttpServletRequest request,
				HttpServletResponse response, String mtrNumber);

		List<MasterMainEntity> getDistinctMeterNos();
		
		List<MasterMainEntity> getDistinctKNos();

		
		public List getMeterDataForReplacement(String oldMeterNo);
		
		public int  mtrReplaceUpdateNewMtrno(String oldMeterNo,String newmeterno,String mtrchangeDate,String mf);

		
		List<?> getmeterDataByNOdeID(HttpServletRequest request);
		
		List<MeterChangeTransHistory> getAllMeterChangeData();

		List<MasterMainEntity> getConsumerLocData(String mtrNum, String accNum);

		List<Object[]> getFeederLocData(String mtrNum);

//		List<DtDetailsEntity> getDtLocData(String mtrNum, String dtId);
		
		List<Object[]> getDtLocData(String mtrNum, String dtId);
		
		void getMeterDetailsPdf(HttpServletRequest request,HttpServletResponse response,String circle,String division,String subdiv,String towncode,String townname,String location);

		MasterMainEntity getEntityByMtrNOandTcode(String towncode,String mtrno);
		
		boolean getmetrno(String mtrno);
}
