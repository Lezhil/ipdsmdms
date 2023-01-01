package com.bcits.service;


import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.Alarms;

public interface AlarmService extends GenericService<Alarms> {

	//Optional<Alarms> findById(int id);
	void deleteRecord(int id);

	void processEventAlarmData();
	void processValidationsAlarmData();
	

	List<?> getAmiCircleByZone(String zone);


	Object getAmiDivisionByCircle(String zone, String circle);

	List<?> getSubdivByDivisionByCircleAmi(String zone, String circle,
			String values);

	boolean findMeterLocation(String mtrNum, String locationType);

	String sendEmailNotification(String to, String sdocode, String loctype, String locname, String alarmname,
			String priority, String alarm_type, String alarmSetting, String locCode);

	List<String> getAccountDetails(String officeType, String officeName);

	List<String> getMtrnos(String officeType, String officeName);

	List<String> getAlarmName(String officeType, String officeName);
}
