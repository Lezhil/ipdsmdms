package com.bcits.mdas.service;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.FeederEntity;
import com.bcits.service.GenericService;

public interface FeederDetailsService extends GenericService<FeederEntity> {

	public List<?> getDistinctsubdivision();
	
	public List<?> getcircle();

	public List<?> getfeederdetails(String Region,String Circle,String town,String feeder);
	
	public List<?> getFeederMaster(String zone,String subdiv, String circle,String town,String towncode);

	public List<?> getEditDetailsById(String id);

	public Object getSSnameBySubdiv(String town);

	public String getfdrid();

	//public List<?> getFeederidForDD();

	public Object getFdrIdNamenameBySubdiv(String parentid, String sdoCode);

	public Object getsubdivByCircle(String circle);

	Object isfeederAttached(int id);

	Object isDtAttached(int id);

	Object isConsumerAttached(int id);

	Object deletemethod(int deleteId);

	Object getFeederDetails(String feederId);

	Object isMeterNoAvailable(String meterNo);

	Object validateMeterNO(String meterNo);

	int setMeterInventoflag(String meterNo, String userName);

	public HashMap<String, String> getlocationHireachy(String subdiv);

	public List<?> getDistinctZone();

	Object getSSnameAndFeederNameBySubdiv(String subdivision);

	List<?> getMeterDetailsByFdrcode(String fdrcode);

	FeederEntity getDetailsById(String bdrid);

	public Object isFeederCodeAvailable(String fdrCode);

	public List<?> getDistinctCategory();


	Object getSSnameByofficeID(String officeId);

	Object getFdrIdNamenameBySubdivandSubstat(String parentid, String officeId);

	List<String> getFeederAllMeters();

	public Object getfdrIdByTpfeederId(String feedercode,String tp_towncode);

	public List<FeederEntity> getFeederDetailsByFeederId(String feedercode, String towncode);
	
	public String getSSIdByTpsubstationId(String tp_ssid,String towncode);

	public FeederEntity getBoundaryDetailsByFeederId(String boundaryid, String feedercode, String towncode);

	public String getFeederId(String feedercode, String sstp_id, String towncode);
	
	public List<FeederEntity> getFeederByMeterno(String meterno);

	public FeederEntity getMtrExistOnFeeder(String feedercode);

	public List<FeederEntity> checkMeterAttachedonFeeder(String feedercode, String meterid);

	public List<?> getBoundaryMeterDetailsByTown(String townCode, String ssid,String circle,String zone);

	public String getFeederIdbyTpfdrId(String feedercode);

	public void getFdrDtlspdf(HttpServletRequest request, HttpServletResponse response, String region, String circle, String town, String feederType,String townname);
	
	public FeederEntity getBoundaryByFeederMrtId(String boundaryid, String feedercode, String meterid);

	public FeederEntity getFeederBySubstationMrtId(String towncode,String feedercode, String substationcode, String meterid);

	public List<?> getAllipdsnonipdsFeederList(String region, String circle, String town, String category);

	public Object isFdrcodeAvail(String fdrcode);

	public List<?> getfeederOutageReport(String month, String feeder, String town,String circle);
	public void getfeederOutagePdf(String month, String feeder, String town,String circle,HttpServletResponse response, HttpServletRequest request);

	public List<?> getMonthWisefeederOutageReport(String month, String feeder,String meterno);
	
	public List<?> getMonthDurationWisefeederOutageReport(String month, String feeder,String meterno);


	
}
