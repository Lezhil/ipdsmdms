package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.MeterInventoryMobileEntity;
import com.bcits.mdas.entity.SurveyOutputMobileEntity;
import com.bcits.service.GenericService;

public interface MeterInventoryMobileService extends GenericService<MeterInventoryMobileEntity> {

	public List<MeterInventoryMobileEntity> findAll(String sitecode ,String mrcode);
	public List<?> getConsumersForMeterChange(String sitecode , String mrcode, String dtcCode);
	public List<?> getConsumerDetailsMeterChangedAlready(String sitecode ,String mrcode);
	public List<?> getDistinctDTCForMeterChange(String sitecode , String mrcode) ;
	public boolean isConsumerExist(String accno,String newmeterno, String sdocode);
	public boolean isertToSurveyOutput(SurveyOutputMobileEntity downloadEntity);
	boolean updateMeterInstalled(String meterNo, String sdocode);
}
