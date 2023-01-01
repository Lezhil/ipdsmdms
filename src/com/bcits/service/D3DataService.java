package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.D3Data;

public interface D3DataService extends GenericService<D3Data>
{
	public List<D3Data> getDetailsBasedOnMeterNo(String meterNo,String billMonth,ModelMap model);
}