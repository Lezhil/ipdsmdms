package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.NamePlate;
import com.bcits.service.GenericService;

public interface MeterInventoryService extends GenericService<MeterInventoryEntity>{
	
	public List<MeterInventoryEntity> getALLMeterDetails();
	public List<MeterInventoryEntity> getALLMeterDetailsById(Long id);
	public List<MeterInventoryEntity> meterNoExistOrNot(String meterno);
	public List<MeterInventoryEntity> saveBatchInventory(List<MeterInventoryEntity> list);
	public List totalInstalledMeters(ModelMap model);
	public List<MeterInventoryEntity> getALLInstockMeters();
	public List<MeterInventoryEntity> getALLInstockMeters(String strLoc,String iortype);
	public List<MeterInventoryEntity> updateMeterNoInstalled(String meterno,String userName);
	public List<MeterInventoryEntity> checkMeterNoIsInSTOCK(String meterno);
	public List<MeterInventoryEntity> checkMeterNoInstalled(String meterno);
	String sequenceMeterResponse( String ssn, String esn);
	public List<Object[]> getMetersBasedOnStatus(String status);
	public List<Object[]> getMetersBasedOnStatusandLoginlvl(String status,String officeType,String region,String circle);

	public Object getissuedAndInstalledMetrdata(String status,String date, String toDate);
	
	
	
	public List<Object[]> getMeterInventoryBasedOnData(String data,String parameter);
	public List<Object[]> getMeterInventoryBasedOnDataByLoginLvl(String data,String parameter,String circle,String region,String officeType);

	public Object getCtRatioPtRatio(String meterno);
	public MeterInventoryEntity getMeterInventoryEntity(String meterno);
	public int updateMeterStatus(String meterno,String meter_status);
	public void getMeterDetailsPdf(HttpServletRequest request, HttpServletResponse response, String param, String data);
	public List<?> getMeterDetailsExcel(String data, String parameter);
	
	public int updateMtrStatus(int deleteId);
	public List totalInstalledMetersForCirclelvl(ModelMap model, String officeType, String circle, String region);
	
	//get UnmappedMetersData
	public List<Object[]> getUnmappedMetersData();
}
