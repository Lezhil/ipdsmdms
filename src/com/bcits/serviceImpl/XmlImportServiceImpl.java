package com.bcits.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.XmlImport;
import com.bcits.service.XmlImportService;
import com.bcits.utility.CalenderClass;

@Repository
public class XmlImportServiceImpl extends GenericServiceImpl<XmlImport> implements XmlImportService 
{
	@Autowired
	private CalenderClass calenderClass;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<XmlImport> getDetailsBasedonMeterNo(String meterNo,String billMonth,ModelMap model)
	{	
		List<XmlImport> list= postgresMdas.createNamedQuery("XmlImport.getDetailsBasedonMeterNo").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		model.put("billingData", list);	
		model.put("portletTitle", "Billing Details");
		model.put("meterNo", meterNo);
		model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}
		return list;
	}

	@Override
	public List<XmlImport> exportDetailsBasedonMeterNo(String meterNo) {
		return postgresMdas.createNamedQuery("XmlImport.exportDetailsBasedonMeterNo").setParameter("meterNo", meterNo).setParameter("billMonth", 202112).getResultList();
	}
	
	public String ChechForBillingParameters(String meterNo,String currentDate) 
	{
		String num ="0";
		List list =  postgresMdas.createNamedQuery("XmlImport.ChechForBillingParameters").setParameter("meterNo", meterNo).setParameter("datestamp", currentDate).getResultList();
		if(list.size() > 0)
		{
			num = "1";
		}
		return num;
	}
	
	public String ChechRdateData(String meterNo,Date rDate) 
	{
		String num ="0";
		List list =  postgresMdas.createNamedQuery("XmlImport.ChechRdateData").setParameter("meterNo", meterNo).setParameter("rdate", rDate).getResultList();
		System.out.println("***************************** : "+list.size());
		if(list.size() > 0)
		{
			num = "1";
		}
		return num;
	}
	
}
