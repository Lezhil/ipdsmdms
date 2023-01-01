package com.bcits.service;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;

import com.bcits.entity.AlarmDefinition;

public interface AlarmDefinitionService extends GenericService<AlarmDefinition>
{

	List<AlarmDefinition> getAlarmEntityByAccnum(String accountNumber);

	List<AlarmDefinition> getAlarmEntityBylocationCode(String locType);

	List<Object[]> dispatchAlarms(String zone, String circle, String town,
			 String loctype, String fromdate,String toDate);

	List<?> getAlarmDetails(String accno, String mtrno, String radioval);

	int getAlarmRemoveDet(String mtrno, String accno, String radioval);

	List<?> getAlarmPriorityDet(String accno);

	List<?> showAlarmDetailsbyAlarmname(String alarmName, String officeType, String officeName);

	List<?> getAlarmPriorityDetbyAlarmName(String alarmName, String locationId);

	int getalarmRemoveDetailsbyAlarmName(String alarmName, String checkboxes);


}
