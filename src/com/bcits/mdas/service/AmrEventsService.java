package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.service.GenericService;

public interface AmrEventsService extends GenericService<AmrEventsEntity>
{

	List<AmrEventsEntity> getRecords(String meterno, String fileDate);


	List<AmrEventsEntity> getEventData(String mtrNo, String frmDate,
			String tDate, String radioValue);
	
	List<AmrEventsEntity> getEventDataInfo(String mtrNo);
	
	
}
