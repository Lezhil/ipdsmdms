package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.SiteLocationEntity;
import com.bcits.service.GenericService;

public interface SiteLocationService extends GenericService<SiteLocationEntity> {


	public List<SiteLocationEntity> getAllSiteLocations(ModelMap model, HttpServletRequest request);

	public List<Object> getAllSiteCodes();
	
	List<String> getAllSdoCodes();

	List getSectionCodes(String type);

	List getOnlySectionCodes();

	void getTreeSiteCodes(String code, HttpServletResponse response);

	public String getDbuser(String sdocode);

	List<SiteLocationEntity> findBySdocode(String sdocode);

	public List<Object[]> findDistinctDivision(ModelMap model, HttpServletRequest request);

	public List<Object[]> findDistinctSubDivision(String divSdo, ModelMap model,
			HttpServletRequest request);

	public List<Object[]> findByLocType(String locType, ModelMap model,
			HttpServletRequest request);

	public List<SiteLocationEntity> getLocationByMail(String string);
	
	void getTreeSiteCodesForAsset(String code, HttpServletResponse response);

}
