package com.bcits.service;

import org.codehaus.jettison.json.JSONObject;

import com.bcits.entity.MeterChangeTransHistory;

public interface MeterChangeTransHistoryService extends GenericService<MeterChangeTransHistory> {
	
	public String callService(JSONObject obj,String meteMake);

}
