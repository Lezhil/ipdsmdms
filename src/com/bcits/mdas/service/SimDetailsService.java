package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.SimDetailsEntity;
import com.bcits.service.GenericService;

public interface SimDetailsService extends GenericService<SimDetailsEntity>{
	
	public List<?> getSimDetails();

	public List<?> addSimDetails();
	
	public List<?> getEditSimDetails(int id);
	
	public int getModifySimDetails(String id,String mdnumberr,String simstaticipp,String nsproviderr,String simstatuss,String userName);
	
	public List<?> getConsumerData(String mtrno);
	
	public List<?> getMeterData(String mtrno,String fdate,String tdate);
}
