package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.D9Data;

public interface D9DataService extends GenericService<D9Data>
{
	public List<D9Data> findAll(String meterNo,String billMonth,ModelMap model);
	public List<D9Data> tamperEventData(String billMonth,ModelMap model);
}