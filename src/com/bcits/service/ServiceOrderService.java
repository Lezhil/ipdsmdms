package com.bcits.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.ServiceOrder;
import com.bcits.entity.ServiceOrderDetails;

public interface ServiceOrderService extends GenericService<ServiceOrderDetails> {
	public List<ServiceOrder> findALL();

	public List<ServiceOrder> findalldata(String zone, String circle, String division, String sdoname, String fdate,
			String tdate, HttpServletRequest req) throws ParseException;
	public List<?> getSOnofromIssueType(String issueType);
	public List<?> getEventListforMeterList();

	public List<Object[]> getServiceOrderforPowerTheft(String circle, String town, String cancat);

	public List<Object[]> getServiceOrderforMeterEvents(String zone,String circle, String town, String cancat, String issue);

	public List<Object[]> getServiceOrderforCommunicationFail(String zone,String circle,
			String cancat,String town);

	public List<?> getPowerTheftList();

	public List<?> getMeterExceptionAlarmList();

	public List<Object[]> getServiceOrderforMeterException(String circle, 
			String cancat, String issue,String town);

	public Object getSOnumber(String siteMonth);

	public List<?> getDistinctSOnumber();

	public List<?> getFeedbackServiceOrderDetails(String circle,String zone, String locType,
			String issue, String issueType, String sonum, String month, String status, String town);

	public ServiceOrderDetails getEntityById(int parseInt);

	public List<?> getServiceSummaryDetails(String circle, String zone, String locType,
			String locId, String mtrno, String trimStr, String status, String town);
	
	public List<?> generateServiceExcel(HttpServletRequest request,HttpServletResponse response,String locType,String circle,String town,String issueType,String issue,String zone);

}
