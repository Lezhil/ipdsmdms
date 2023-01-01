package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.Master;
import com.bcits.entity.MeterMaster;

public interface MasterService extends GenericService<Master>
{
	public long FindTotalConsumerCount();
	public List getAllMNP();
	public List getMeterDataInformation(String meterNo,String billmonth);
	public int updateOldAcc(String accno,String oldaccno,HttpServletRequest request,ModelMap model,String circle,String division,String sdoname);
	public String findSDOName(MeterMaster meterMaster,ModelMap model);
	public String findTarrifCode(MeterMaster meterMaster,ModelMap model);
	public long checkAccnoExist(String accno);
	public Boolean addMrname(MeterMaster meterMaster);
	public List FindMakewiseConsumerCount(String billMonth, ModelMap model);
	public long countTotalInst(MeterMaster meterMaster);
	public int updatePhoneno(String accno, String phoneno, String industryType);
	public List<String> getDistinctMrname();
	List<Master> findMr();	
	public List<Master> findSdoCode();
	public List<Master> findTadesc();
	public List<?> FindMrNameOnSdo(String sitecode, String tadesc);
	public List<?> FindTadescCode(String sitecode);
	public List<?> getMrnames();
	public List<?> getALLCategories();
	public Long getCount(String accno);
	
	
	public List<Master> getALLDivisions();
	public List<?> getALLCircle();
	
	public List<?> getALLMrNameByDiv(String division);
	public List<?> getALLMrNameByCIR(String circle);
	public List<?> findSdoCodeBasedonCirle(String circle,String mrname);
	public List<?> findMrNameBasedonCirleSdoCode(String circle);
	public List<?> findFirstMrName(String circle);

	public List<?> findSecondSdoCodesByCircle(String circle);
	public List<?> findSecondMrName(String circle, String sdocode);
	public List<?> FindTadescCodeByMrname(String sdocode,String circle,String mrname);
	public String getcircleByAccno(String accno);
	public List<?> getALLDivisionByCIR(String circle);
	public List<?> getALLMNPByCIR(String circle);
	public Object getTadescforMrWise();
	public List<?> getMrWiseRecordonRdngMonthTadsc(String billMonth,String tadesc);
	public Object getCircleForMrWiseTotal();
	public List<?> getMrwiseDataOnRdngMonthCircle(String billMonth,String circle);
	public Object getAllCircleforMisReport();
	public List getAllMNPOnSdoNames(String sdoName,String circle);
	public List<?> findSdoNames(String circle);
	public List<?> findMrNamesBySdoNames(String sdoname);
	public Object getDistinctCircle(HttpServletRequest request);
	public Object getAllSubDiv(ModelMap model, HttpServletRequest request);
	List<String> getDistinctSubdivision(String circle,String division,HttpServletRequest request);
	public List<Object[]> getGroupValue(String subdiv,String month, ModelMap model);
	String generateAsciiVal(String month,String subdiv,String category,String billingCategory,HttpServletResponse response,HttpServletRequest request);
	public List<?> getALLSubdivByCIR(String circle);
	public  List<?>  getSubDiv();
	public String getindustryType(String accno);
	public double getsanLoad(String valueOf);
	Object getAllCategory();
	Object getAllParts();
	public  List<?> getd4LoadDataMDM(String billMonth,  String meterNo );
	public  List<?> getd4LoadDataMDAS(String profilDate,  String meterNo );
	List<?> getd4LoadDataCurveMDAS(String profilDate, String meterNo);
	List<?> getd4LoadDataCurveMDAM(String billmonth, String meterNo);
	public List FindMakewiseConsumerCount1(String billmonth, ModelMap model);
	public List<Master> getMeterBasedOn(String circle, String division, String subdiv);
	public List<?> geteventdescdata();
	public List<?> getvalfaildata();
	public List<MasterService> getDistinctCircle();
	public List<?> getDivisionByCircle(String circle, ModelMap model);
	public List<?> getSubdivByDivisionByCircle(String circle, String division, ModelMap model);
	public List<?> getDiscomList();
	public List<MasterService> getDistinctZone();
	public List<?> getCircleByZone(String zone, ModelMap model);
	public List<?> getUserRoleData();
	public List<?> getUserRoledata(String userrole);
	public List<?> getAccnoID(Object accNumber);
	List<?> getDiscomDetails(int i);
	List<?> getDtDetails(String dtId);
	List<?> getFdrDetails(String fdrId);
	List<?> getcirclebyzone(String zone, ModelMap model);
	public List<?> getUserRoledata(String dcom, String zonee, String cir, String div, String sdiv, String role);
	//public List<?> getUserRoledataForAlarm( String zonee, String cir, String twn, String role);
	public List<?> getUserRoledataForAlarm(String zonee, String cir, String twn, String role);

	

	
	
	
	
}