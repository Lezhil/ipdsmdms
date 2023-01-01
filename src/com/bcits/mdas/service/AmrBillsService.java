package com.bcits.mdas.service;

import java.util.ArrayList;
import java.util.List;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.service.GenericService;


public interface AmrBillsService extends GenericService<AmrBillsEntity>
{
	public List<AmrBillsEntity> getRecords(String meterNumber, String fileDate);
	//public List<AmrBillsEntity> previous4Month(String meterNumber, String fileDate);

	public List<AmrBillsEntity> getbillHistoryDetails(String mtrNo, String frmDate, String tDate);
	
	public List<AmrBillsEntity> getbillHistoryDetailsInfo(String mtrNo);
	
	//public List<AmrBillsEntity> getBillHistory6months(String mtrNo,ArrayList<String> strlist);

	List<AmrBillsEntity> getBillHistory6months(String mtrNo,
			ArrayList<String> strlist, String radioValue);
	
}
                                