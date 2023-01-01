package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.AmiLocation;
import com.bcits.service.GenericService;

public interface AndroidService extends GenericService<AmiLocation>{

	String insert_new(String UserName, String ConsumerName, String Password, String Emailid, String MobileNo,
			String otp, String ebillReg, String que1, String ans1, String que2, String ans2);

	int ncpt_rrno_insertion(String s);
	
	 public List<Object[]> getConsumerConumptionData(String LocationType, String LocationName, String Billmonth);

	List<Object[]> getDailyConumptionDataServices(String locationCode, String kno,
			String billmonth);

	List<Object[]> getPowerAvailability(String kno);

	List<Object[]>  getPowerOffEvent(String billmonth, String kno);

	List<Object[]> voltageRegulation(String billmonth, String kno);

}