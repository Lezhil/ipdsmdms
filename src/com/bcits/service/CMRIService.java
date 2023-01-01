package com.bcits.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

/*<<<<<<< .mine*/
import com.bcits.entity.CMRIEntity;
/*=======*/
/*>>>>>>> .r39007*/
/*Author: Ved Prakash Mishra*/
public interface CMRIService extends GenericService<CMRIEntity>{
	
	void commonCMRIProperties(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	void addCMRIData(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	List<CMRIEntity> getCMRIIssueDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	void updateCMRIDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	List<CMRIEntity> getCMRIDataForRecieve(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate,String mriNo);
	
	int getCMRIIssuedOrNot(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	void updateCMRIRecieveDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	List<CMRIEntity> getCMRIIssueDButnotRecievecDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	List<CMRIEntity> getCMRIIssueDAndRecievecDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	void updateCMRIDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri);
	
	List<CMRIEntity> getCMRIRecievecButNotDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	List<CMRIEntity> getCMRIRecievecAndDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	List<CMRIEntity> getCMRIPreparedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);
	
	List<CMRIEntity> getCMRIDifferenceDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate);

	//public List<CMRIEntity> findMrCmrinumber(String name,HttpServletRequest request,ModelMap model);
	public List<CMRIEntity>finCmriNo(ModelMap model);
	//List<CMRIEntity> findAllCmriData(String mrname,ModelMap model);
	//List<CMRIEntity> findAllCmriData1(String rdate,ModelMap model)throws Exception;
	public List findCmriData(String mrname);
	
	
	public List<CMRIEntity> getCMRIBasedOnMrName(String name,Date rdgDate);
	
	public CMRIEntity uploadMakeWise(String readingDate,CMRIEntity cmri);
	
}
