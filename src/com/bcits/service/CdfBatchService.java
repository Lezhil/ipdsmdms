package com.bcits.service;

import java.util.List;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.XmlImport;

public interface CdfBatchService extends GenericService<XmlImport>{

	public List<XmlImport> findXmlDataForMonth(String rdngmonth,String meterno);
	public List<MeterMaster> findMeterMasterDataForCURRMonth(String rdngmonth,String meterno);
	int  updateMMkwh(String rdngmonth,String meterno,String kwh,String kvh,String kva,String pf);
	int  updateMMExportReading(String rdngmonth,String meterno,String kwh,String kvh,String kva,String pf,String ekwh,String ekvah, String ekva, String epf,String todayDate);
	long checkMMUpdate(String rdngmonth,String meterno);
	int deleteMtrInXmlImport(String meterno, String yyyyMM1);
	long checkXMLMMUpdate(String rdngmonth, String meterno);
	int deleteMtrInBatchStatus(String meterno, String yyyyMM1);
	long checkMeterNoRdngMnth(String rdngmonth, String meterno);
	
}
