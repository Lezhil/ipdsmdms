package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.AlarmHistory;
import com.bcits.entity.Alarms;

public interface AlarmHistoryService extends GenericService<AlarmHistory>{ 

	
	public List<?> viewAlarms();
	public List<Alarms> acknowledgeAlarms(int id);
	AlarmHistory saveAlarmInHst(List<Alarms> l, String msg, String ack_by);
	void deleteRecord(int id);
	List<?> getLocationType();
	public List<?> getAlarmHistory(String circle, String zone, String town, String fromDate,String toDate, String loctype);
	public void autoacknowledge(String ack_by);
	List<?> getAllOfficeCodes(String circle, String division, String subdiv);
	public void getViewAlarmDtlspdf(HttpServletRequest request, HttpServletResponse response);
	public void getViewHistoryAlarmDtlspdf(String zone1,String crcl,String twn,String fromdate, String todate, String loctype1, HttpServletRequest request, HttpServletResponse response,String townname);
}
