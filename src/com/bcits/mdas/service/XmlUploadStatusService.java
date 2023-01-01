package com.bcits.mdas.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.XmlUploadStatusEntity;
import com.bcits.mdas.ftp.FTPUploadFile;
import com.bcits.service.GenericService;


public interface XmlUploadStatusService extends GenericService<XmlUploadStatusEntity>
{

	List<String> getXMLFilesByDate(String zn, String cir, String div, String subdiv,String fDate, ModelMap model);

	List<Object> getMeterDetailsForFailedXml(String fileDate);
	List<Object> getMeterDetailsForFailedXmlNew(String fileDate);

	List<Object> getLast30DaysDates(String from, String to);

	XmlUploadStatusEntity getRecordByMeterNDate(String meterNumber, String fileDate);

	List<Object> getMeterNumbersForReUpload(String fileDate);

	String generateXmlAndUpload(String meterNumber, String fileDate, FTPUploadFile ftp, String filePath);

	List<String> getMtrNoForDownload(String zn, String cir, String div, String subdiv,String fDate);
	
	
}
