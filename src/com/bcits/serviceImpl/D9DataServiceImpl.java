package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.D9Data;
import com.bcits.service.D9DataService;

@Repository
public class D9DataServiceImpl extends GenericServiceImpl<D9Data> implements D9DataService
{

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D9Data> findAll(String meterNo,String billMonth,ModelMap model)
	{	
		List<D9Data> list= postgresMdas.createNamedQuery("D9Data.Transacions").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		model.put("transactionData", list);	
		model.put("meterNo", meterNo);
		model.put("portletTitle", "Transaction Details");
		model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}
		 
		String transactionCode="";
		/*if(list.size()>0)
		{
			for (int i = 0; i < list.size(); i++) 
			{
				transactionCode=transactionCode+list.get(i).getTransactionCode()+",";
			}
			MDMLogger.logger.info("coming transactionDesc "+transactionCode);
			try {			
				final String queryString = "SELECT t FROM TransactionMaster t,D9Data d9 WHERE d9.transactionCode=t.transactionCode AND t.transactionCode IN("+transactionCode.substring(0,transactionCode.length()-1)+")";
				Query query = postgresMdas.createQuery(queryString);
				List<EventMaster> list2=query.getResultList();						
				model.put("transactionDesc", list2);			
			} 
			catch (RuntimeException re) 
			{
				re.printStackTrace();
				throw re;
			}
		}*/
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D9Data> tamperEventData(String billMonth,ModelMap model)
	{
		List<D9Data> list= postgresMdas.createNamedQuery("D9Data.TamperEventsData").setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		System.err.println("tamper-------"+list.size());
		model.put("transactionData", list);	
		return list;
	}
	
}
