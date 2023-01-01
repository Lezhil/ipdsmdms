package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.AmiLocation;
import com.bcits.service.GenericService;

public interface AmiLocationService extends GenericService<AmiLocation> {
	public String circlecode(String circleName);

	public String divisionCode(String division);

	public String subDivisionCode(String sdoname);

	String circleName(String circleCode);

	String divisionName(String divisionName);

	String subDivisionName(String subdivisionCode);
    public List<Integer[]> getSitcCodeByCircle(String circle,String zone,String town);

	List<Integer[]> getSiteCodeByDivision(String circle, String division, String zone, String town);

	List<String> getAllSiteCodes();

	public AmiLocation getLocationDetails(String zonecode,String circlecode, String division_code, String subdivisioncode,
			 String towncode);

	public AmiLocation getAmiLocationDetails(String circlecode, String division_code, String subdivisioncode, String towncode,String sectionCode);

	//public AmiLocation getAmiLocationByTownCode(String towncode);
	public String getRegionNameByCircle(String circleCode);
	public List<?> getAllRegions();
	public List<?> getCircleListByZoneCode(String zonecode);
	public String getRegionNameByRegionCode(String regionCode);
	public List<?> getTownByCircleCode(String circleCode);
}
