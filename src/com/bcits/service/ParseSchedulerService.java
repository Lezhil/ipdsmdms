package com.bcits.service;

import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;

import com.bcits.entity.BatchStatusEntity;


public interface ParseSchedulerService  extends GenericService<BatchStatusEntity>
{
	public String parseTheMobileFile(Document doc,String billmonthParam,ModelMap model,String unZipFIlePath,String filename);
}
