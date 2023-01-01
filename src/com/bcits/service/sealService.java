package com.bcits.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.ui.ModelMap;

import com.bcits.entity.Seal;
import com.bcits.utility.Resultupdated;

public interface sealService extends GenericService<Seal> {
	 public void insertAllSeals(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 public void updateSealOutWard(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 public long getMaxSealCardNum(HttpServletRequest request);
	 
	 
	 public long getMaxSealCardNum1(HttpServletRequest request);
	 
	 public void sealDataMultipleUpdate(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 public void sealDataSingleUpdate(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 void updateSealReIssue(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 void getSealBunches(Seal seal,ModelMap model,HttpServletRequest request);
	 
	 public List<Seal> getSealsForMobileMR(String Mrname,HttpServletRequest request);
	 
	 public int  updateSealTable(String seal, String sealRemark, String meterno, String rmrname, int billmonth, String accno);
	 public ArrayList<Resultupdated> updateSealsPending(HttpServletRequest request, JSONArray array);
	
	 

	 List<Object[]> getMrwiseSealSummary(String billMonth);

	 public List<Object[]> getMrSealNo(String mrName, String billMonth,ModelMap model,String condition); 

	 List<Object[]>  getReturnSeals(ModelMap model);

	 List<Object[]> getMrWiseSealsReturn(String mrName, ModelMap model);

	 int updateCardSlNo1(String[] sealNumbers, long cardSealNo1,String mrName);
	 
	 
	 //Added by Sunil KJ
	 public List<Object[]> getMrWiseSealsReturnPc(String rmrName,ModelMap model,String condition);
	 public int updateMultipleCardSlNo1( long cardSealNo1,String rmrName,String condition);

	public List<Object[]> getpendingdata(String billmonth,String circle);

	public List<?> viewcountwiseDetails(String mrname, String billmonth,ModelMap model, HttpServletRequest request,HttpServletResponse response);

	public List<?> getFirstRecords(String rdngMonth, String sealNo);

	public List<?> getFirstRecords(String sealNo);
}