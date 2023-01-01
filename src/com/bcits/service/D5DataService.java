package com.bcits.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.AlarmHistory;
import com.bcits.entity.Alarms;
import com.bcits.entity.D5Data;

public interface D5DataService extends GenericService<D5Data>
{
	public List<D5Data> findAll(String meterNo,String billMonth,ModelMap model);
	public List getEventDetails(int eventCode,String eventFromDate,String eventToDate,String eventCat);
	public List<Object[]> showEventDetails(String billmonth, ModelMap model);
	public List<Object[]> getalaramData(String fromdate, String todate,String alaramtype, String metertype, HttpServletRequest request,
			ModelMap model);
	public List<Object[]> geteventdata();
	public List<Object[]> eventSummary(String fromDate);
	public List<Object[]> gettotalAlaramData(String valtype);
	
}