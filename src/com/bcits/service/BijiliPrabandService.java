package com.bcits.service;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;

import com.bcits.entity.MeterMaster;

public interface BijiliPrabandService extends GenericService<MeterMaster>{
	
	public JSONArray getBillDataByMonthly(HttpServletRequest req);

}
