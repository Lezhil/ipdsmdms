package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.D2Data;

public interface D2DataService extends GenericService<D2Data>
{
	public List<D2Data> findAll(String meterNo,String billMonth,ModelMap model);
	public List<D2Data> download360ViewPdf(String meterno, String month,ModelMap model,HttpServletResponse response);
	public List<D2Data> getInstansData(String mtrNo);
}