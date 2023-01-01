package com.bcits.service;

import java.util.List;

import com.bcits.entity.ServiceExceptionEntity;
import com.bcits.entity.ServiceExcpNotificationSetting;

public interface ServiceExceptionService extends GenericService<ServiceExcpNotificationSetting>{

	public List<?> getexistingexception();
	public List<?> getAckData(String fdate,String tdate,String serviceId);
	int tosaveack(String AckMsg,int ackId,String username);
	public int setackstatus(String str);
	public List<?> getServiceName();
	public List<?> getAllServiceExceNotifData();
	public List<?> getEditDetils(int parseInt);
	public int UpdateEditDetils(String mobileNo, String emailId, String id, String serviceName);
	
	
	
}
