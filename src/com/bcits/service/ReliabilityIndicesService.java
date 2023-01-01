package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.DtDetailsEntity;


public interface ReliabilityIndicesService  extends GenericService<DtDetailsEntity> {

	public List<?> getReliabalitySingleFeederData(String fromDate, String toDate, String fdrNo,String subStationCode);

	public List<?> getReliabalityMultipleFeederData(String fromDate, String[] feederMultiple,String subStationCode);

	public List<?> getReliabalityMultipleDTData(String fromDate,String toDate, String[] dTNo,String substation);

	public List<?> getReliabalitySingleDTData(String fromDate, String toDate,String dTNo,String substation);

	public List<?> getSaifiSaidiDataDT(String townfeeder, String town,
			String monthyr, String dt);
	
	public void getSaidiSaifiReportPDF(String townfeeder, String town,String zne, String crcl,String monthyr, String dt,String townname1,String feedername ,HttpServletRequest request,HttpServletResponse response);

	

	
}
