package com.bcits.mdas.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.bcits.entity.*;
import com.bcits.mdas.entity.SubstationDetailsEntity;
import com.bcits.service.GenericService;

public interface substationdetailsservice extends GenericService<SubstationDetailsEntity> {

	public List<?> getSubstationDetails();
	public List<?> getSubstationDetailsNew();
	public List<?> getmasterSubstationDetailsNew(String zone,String circle,String town_code);
	
	public List<?> getChangedDetails(int id);
	
    int getModifyDetails(String substaidd,String substanamee, String substacapp, String substacodee, String tpparcodee,String userName,String latitude,String longitude,String dcuno, String editSubstationCapacityinMVA);
    
	public List<?> getSubdivVolBySubdiv();
	
	public List<?> getParentfeeder(Double voltagelevel,String sitecode);
	
	public List<?> getDistinctZone();
	
	public List<?> getCircleByZone(String zone, ModelMap model);
	
    public List<?> getDivisionByCircle(String circle,ModelMap model);
    
    public List<?> getSubdivisionByDivision(String division,ModelMap model);
    
    public String getSsid();

	int subStationDel(String str);

	public List<?> getTownList(String zone, String circle);
	
	void getTownDetailsPdf(HttpServletRequest request,HttpServletResponse response,String region,String circle);
	public void getSubstationDtlspdf(HttpServletRequest request, HttpServletResponse response,String zone,String circle,String town);

	public List<?> getExcelTownList(String zone, String circle);
	public List<SubstationDetailsEntity> getSubstationBySSTpCode(String sscode);
	public SubstationDetailsEntity getSubstationByCode(String sscode);
	
}
