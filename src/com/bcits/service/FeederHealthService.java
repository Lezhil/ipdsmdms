package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.FeederHealthEntity;

public interface FeederHealthService extends GenericService<FeederHealthEntity> {

	//void proceessFeederHealthData(String siteCode, List<String> meterList);

	void proceessFeederHealthData(List<String> meterList, String month);

	List<Object[]> getFeederHealthReport(String zone, String circle, String rdngmnth, String town );

	void getFeederHealthReportPdf(HttpServletRequest request, HttpServletResponse response, String zone1, String crcl, String twn, String rdngmnth, String townname1);

	//void proceessFeederHealthData(String string, List<String> meterList);

}
