package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.NppDTrptMonthlyCalculation;
import com.bcits.service.GenericService;

public interface NppDTrptMonthlyCalculationService extends GenericService<NppDTrptMonthlyCalculation>{

	List<Object[]> getNppIntermediateDtData(String monthYear);
	String getMonthlyConsumptionDetails(String fromDate);
	String getDailyConsumptionDetails(String fromDate,String todate,String monthYear);
	String getBillConsumptionDetails(String fromDate,String todate,String monthYear);
	String getMonthinpConsumptionDetails(String monthYear);
	String getinstComsumptionAutoPush(String fromDate,String todate,String monthYear);
	String getmissingvoltageAutoPush(String fromDate,String todate,String monthYear);
	String getmissingvoltageAutoPush_vy(String fromDate,String todate,String monthYear);
	String getmissingvoltageAutoPush_vb(String fromDate,String todate,String monthYear);
	String getAfterMonthlyCOnsumtionDTEADataPush(String monthYear);
	String energyAccountingDTDataPush(String monthYear);
	String getafterMonthlyCOnsumtionFeederTownEADataPush(String monthYear);
	String getAfterMonthlyCOnsumtionFeederTownEADataPushForFeeder(String monthYear);
	String getAfterMonthlyCOnsumtionFeederTownEADataPushForBoundaryr(String monthYear);
	

}
