package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.mdas.entity.InitialMeterInfo;
import com.bcits.service.GenericService;

public interface InitialMeterInfoService extends GenericService<InitialMeterInfo>{

	List<InitialMeterInfo> getInitialMeterInfo();

	List<?> getDTAppDetails(String region, String circle,String towncode);

	List<?> getDTNotAppDetails(String region, String circle,String towncode);

	InitialMeterInfo getDTNotAppDetailByMeter(int id, String meterNo);

	void getDtMtrAppList(HttpServletRequest request,HttpServletResponse response,String reporttype);
	
	void getDtMtrNotappList(HttpServletRequest request,HttpServletResponse response,String reporttype);
	
	void getPreiodCommSumm(HttpServletRequest request,HttpServletResponse response,String zone,String circle,String town,String fromdate,String todate,String townnames);

	int rejectDTMeters(String meterNo, String userName);

	public String checkDTMeterExistOrNot(String meterNo);

	int duplicateDTMeters(String meterNo, String string);

	List<InitialMeterInfo> initialMeterFeederDataSync();
	
	List<InitialMeterInfo> initialMeterBoundaryDataSync();


}
   