package com.bcits.mdas.service;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;

import com.bcits.mdas.entity.ChangeModemDetailsEntity;
import com.bcits.service.GenericService;

public interface ChangeModemService extends GenericService<ChangeModemDetailsEntity> {
	
	public  HashMap<String,Object> parseTheFile(Document doc,ModelMap map) throws ParseException;
	public  String getLatestMonthMM();
}
