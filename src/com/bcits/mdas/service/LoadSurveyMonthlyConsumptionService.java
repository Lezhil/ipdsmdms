/**
 * 
 */
package com.bcits.mdas.service;

import com.bcits.mdas.entity.LoadSurveyMonthlyConsumptionEntity;
import com.bcits.service.GenericService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tarik
 *
 */
public interface LoadSurveyMonthlyConsumptionService extends GenericService<LoadSurveyMonthlyConsumptionEntity>{

	LoadSurveyMonthlyConsumptionEntity getAssignRuleId(String meter_number, String billmonth);
	
	List<?> getlocationwisedata(String locIdentity, String meterno,String LocType);
	
	List<?> getdtLocationdata(String locIdentity, String meterno);

	//List<?> getAreawisefeederdata(String circle,String division,String subdiv, String monthyr, String consumavail,String loc);
	
	List<?> getAreawisefeederdata(String circle,String townCode , String monthyr, String consumavail,String loc);
	
	
//	List<?> getAreawisefeederdataTest(String circle,String townCode , String monthyr,String loc);
	List<?> getFeedertownEAReport(String monthyear,String feederTpId , String periodMonth,String circle,String town);
	List<?> getFeederinputEAReport(String monthyear,String feederTpId , String periodMonth,String circle,String town);
	
	List<?> getMonthlyConsumptionReport(String circle,String townCode , String monthyr,String loc);

	List<?> getAreawisedtdata(String circle, String division,String subdiv,String monthyr, String consumavail);

	BigDecimal togetConsumption(String id);

	BigDecimal togetConsumpt(String id);
	int updateConsumption(String consumption,String consumpt,String oldconsumption,String oldconsumpt,String remark,String id,String mtrno,String loccode,String month);
	
	int updateEnergy(String consumption,String oldconsumption,String id,String mtrno,String month);
	
	int updateEnergyExp(String consumpt,String oldconsumpt,String id,String mtrno,String month);
	
	List<?> dailysubenergy(String substation, String fromdate, String todate);
	
	List<?> dailysubenergysample(String zone, String circle, String town, String monthid);

	List<?> dailyfeedersubenergy( String substation, String fromdate, String todate);

	List<?> monthlysubenergy(String substation, String fromdate, String todate);

	List<?> mntlyfeedersubenergy( String substation, String fromdate, String todate);

	List<?> viewconsumption(String mtrno, String billmonth);
	
	List<?> gethtloss(String region, String circle,String fdrid);
	
	
	 List<?> getfdrlossdetailsInfo(String month, String towncode, String fdrid);
	 
	 List<?> getfdrEnergyUpdate(String month, String towncode, String circle,String mtrno);
	 int getenergyDetails(String imp, String exp,String mtrno,String month);
	 List<?> getdtlossdetailsInfo(String month,String towncode, String fdrid);
	
	List<?> getdtloss(String zone, String circle, String town, String monthyear, String feederTpId, String periodMonth);

	/*
	 * List<?> getfdrlossdetails(String month, String period, String towncode,
	 * String fdrid);
	 */
		
	List<?> getfdrlossdetails(String month, String towncode,String fdrid);
	List<?> getfdrcount(String month, String towncode);
	
	List<?> getfdrcountinfo(String month, String towncode);

}
