package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.D3Data;
import com.bcits.service.D3DataService;
import com.bcits.utility.MDMLogger;

@Repository
public class D3DataServiceImpl extends GenericServiceImpl<D3Data> implements D3DataService
{
	public List<D3Data> getDetailsBasedOnMeterNo(String meterNo,String billMonth,ModelMap model)
	{
		List<D3Data> list= postgresMdas.createNamedQuery("D3Data.getDetailsBasedOnMeterNo").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		MDMLogger.logger.info(" before previous month "+getBeforePreviousMonthYear(billMonth)+" "+billMonth);
		//List<XmlImport> list= entityManager.createNamedQuery("XmlImport.getThreeMonthsReadings").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).setParameter("beforePreviousMonth", Integer.parseInt(getBeforePreviousMonthYear(billMonth))).getResultList();
		model.put("readingData", list);	
		model.put("portletTitle", "Readings");
		model.put("meterNo", meterNo);
		model.put("selectedMonth", billMonth);
		MDMLogger.logger.info("list size "+list.size());
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}
		return list;
	}
}
