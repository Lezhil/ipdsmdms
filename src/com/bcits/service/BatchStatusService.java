package com.bcits.service;

import java.util.List;

import com.bcits.entity.BatchStatusEntity;

public interface BatchStatusService extends GenericService<BatchStatusEntity>{

	List<String> getAllDates();

	List<?> getAllSummaryData(String date1);
	List<?> getCircle();

	List<?> getSdocodeOnCircle(String circle);

	List<?> getReadingDateOnSdocodeAndcircle(String circle, String sdocode);

	List<?> getAllbatchStatusRecords(String circle,String sdocode);
	

    List<?> getmmNotUpdatedStatusRecords(String circle,String sdocode);

	List<?> getcirSubDiv(String meterno,String rdngmnth);
	


	List<?> getxmlUpdatedStatusRecords(String circle, String sdocode);

	List<?> getxmlNotUpdatedStatusRecords(String circle, String sdocode);

	List<?> getxmlAndMMUpdatedStatusRecords(String circle, String sdocode);

	List<?> getAllRecordsWithNoCircles();
	
	int checkData(String billmonth,String meterno);
	

	
}
