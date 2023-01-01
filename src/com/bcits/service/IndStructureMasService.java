package com.bcits.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bcits.entity.IndStructureMasEntity;
import com.bcits.service.GenericService;

public interface IndStructureMasService extends GenericService<IndStructureMasEntity> {
	
	public String insertIndsStructureMas(String data);
	
}
