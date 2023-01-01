package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.service.GenericService;

public interface DashboardService extends GenericService<Object>{

	List<Object[]> zoneList();
	List<Object[]> zoneList2( String object);

	List<Object[]> circleList(String circle);
	List<Object[]> circleList2(String circle);

	List<Object[]> divisionList(String string, String string2);

	List<Object[]> subdivList(String string, String string2, String string3);

	List<?> pCommSummary(String zone, String circle, String town, String fromdate, String todate);

	List<Object[]> unmappedMeters();
	

	Object gettotalunm();

	void getMtrtypewiseSummpdf(HttpServletRequest request, HttpServletResponse response);
	void getMtrtypewiseSummpdfforregion(HttpServletRequest request, HttpServletResponse response,String zone);

	void getCirclewiseSummpdf(HttpServletRequest request, HttpServletResponse response, String circle);
	void getDivisionWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String division);
	void getSubdivisionWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String subdivision);
	void getTownWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String town);

	Object townList(String string, String string2, String string3, String string4);
	List<Object[]> getregionWiseMeterList(String string);
	List<Object[]> getCircleWiseMeterList(String region, String circle);
	List<Object[]> getDivisionWiseMeterList(String region, String circle, String div);
	List<Object[]> getSubDivisionWiseMeterList(String region, String circle, String div, String subdiv);
	List<Object[]> getTownWiseMeterList(String region, String circle, String div, String subdiv, String town);


	

}
