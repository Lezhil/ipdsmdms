package com.bcits.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.ConsumerMasterEntity;
import com.bcits.entity.ConsumerReadingEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterMaster;

public interface ConsumerMasterService extends GenericService<ConsumerMasterEntity>{
	
	//public void addNewConsumrData(Master master,HttpServletRequest request,ModelMap model);
	
	List<?> getDistinctCircle();
	List<Master> getZone();
	List<Master> getCircleByZone(String zone);
	List<Master> getCircleByZonebyReg(String zone);
	List<Master> getDivByCircle(String circle);
	List<Master> getSubdivByDivision(String division);
	public void searchByAMRAccNo(HttpServletRequest request,MeterMaster meterMaster,ModelMap model);
	public void  updateAMRConnectionData(MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model);
	public List<ConsumerMasterEntity> checkMtrnoExist(String meterno);
	public List<ConsumerMasterEntity> checkAccnoExist(String accno);
	public List<ConsumerMasterEntity> checkKnoExist(String kno);
	//public List<ConsumerMasterEntity> updateConsumerData(ConsumerMasterEntity consumer,HttpServletRequest request,ModelMap model);
	public List<ConsumerReadingEntity> getReadingData();
	public List<?> getCircle();
	public List<?> getDivisionByCircle(String circle);
	public List<?> getSubdivByDiv(String division);
	
	public List<?> getAllOldDismantle(String circle,String divsion,String subdivision);
	
	public List<ConsumerMasterEntity> getALLConsumerData();
	
	public List getAllcirclesByofficecode(String officecode);
	public List getAlldivisionByofficecode(String circle);
	public List getAllsubdivisionofficecode(String division);
	public List getAllLocationData(String officeType,String officecode);
	
	public List getSubstationsBySubdiv(String sdoname);
	public List getFeederBysubstation(String ssid);
	public List getDtcByFeeders(String fdrid);
	
	public Object getSubFdrDT(String meterno);
	
	public HashMap<String, String> getZoneSitecodeBySubdiv(String circle,String subdiv);
	List<?> getCirleConsumerCount(Object circle);
	List<?> gettotalConsumerCount();
	List<?> getCustomerDetails();
	List<?> getcircleCustomerDetails(List circle,String month);

	List<?> getTotalReadingAvailCons(String month);
	List<?> getTotalConsumerAvail(String month);
	List<?> getTotalNotReadingAvailCons(String month);


	public void insertConsumerreading();
	List<?> getTotalCircleReadingAvailCons(List circle,String month);
	List<?> getTotalCircleConsumerAvail(List circle,String month);
	List<?> getTotalCircleNotReadingAvailCons(List circle,String month);
	List getReadingCustomerDetails(String month);
	List getNotReadingCustomerDetails(String month);
	List<?> getRMSUpload(String month);
	List<?> getRMSpending(String month);
	List getRMSUploadDetails(String monthAlt);
	List getRMSPendingDetails(String monthAlt);
	List<Master> getSubdivByDivision(String zone, String circle, String division);
	List<Master> getDivByCircle(String zone, String circle);
	List<Master> getSubdivandSitecodeByDivision(String zone, String circle,String division);

	public int updateMasterMainMeterNo(String newmeterno,Double mf,Timestamp mtrchangedate);

	
	List<?> getCircleRMSUpload(List circle, String month);
	List<?> getCircleRMSpending(List circle,String month);
	List<?> getcircleReadingDetails(List circle, String month);
	List<?> getCircleNotReadingCustomerDetails(List circle, String month);
	List<?> getCircleRMSUploadDetails(List circle, String month);
	List<?> getCircleRMSPendingDetails(List circle, String month);
	List<?> getTotalDivisionConsumerAvail(String circle, String division, String month);
	List<?> getTotalDivisionReadingAvailCons(String cicle, String divisionlist, String month);
	List<?> getTotalDivisionNotReadingAvailCons(String cicle, String divisionlist, String month);
	List<?> getDivisionRMSUpload(String cicle, String divisionlist, String month);
	List<?> getDivisionRMSpending(String cicle, String divisionlist, String month);
	List<?> getDivisionCustomerDetails(String cicle, String divisionlist, String month);
	List<?> getDivisionReadingDetails(String cicle, String divisionlist, String month);
	List<?> getDivivsionNotReadingCustomerDetails(String cicle, String divisionlist, String month);
	List<?> getDivisionRMSUploadDetails(String cicle, String divisionlist, String month);
	List<?> getDivisionRMSPendingDetails(String cicle, String divisionlist, String month);
	List<?> getTotalSubdivConsumerAvail(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getTotalSubdivReadingAvailCons(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getTotalSubdivNotReadingAvailCons(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivRMSUpload(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivRMSpending(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivCustomerDetails(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivReadingDetails(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivNotReadingCustomerDetails(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivRMSUploadDetails(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getSubdivRMSPendingDetails(String cicle, String divisionlist, String subdivlist, String month);
	List<?> getTownNameandCode(String siteCode);

	public List getTownsBaseOnSubDiv(String subdivname);
	public List getSubstationsByTownCode(String towncode);
	String getSSTPCode(String ssid);
	String getDTTpParentCode(String fdrid);
	List getTownsBaseOnSubdivision( String circle,String zone);
	public List getAllLocationHiarchary(String officeType, String officeCode);
	List<Master> getTownNameandCodebyCircleandzone(String zone,	String circle);
	List<Master> getTownNameandCodebyCircleandzonebyReg(String zone,String circle);

	List<Master> showmeteronBasisofTown(String zone,String circle,String town);
	List<?> getTownNameandCodebySubdiv(String circle,String division,String subdiv);
	List<Master> getFeederTpIdandFeederName(String townCode);
	List getTownsBaseOnCircle(String zone, String circle);
	
	List<?> getdtdata(String towncode);
	List<Master> getFeederByTown(String townCode, String circle);
	List<Master> getDTTpIdandDTName(String feederCode);
	List<?> getFeederBySelection(String townCode, String circle, String zone);
	
	List<Master> getCircleByRegionForFeederOutage(String zone);
	
	
}
