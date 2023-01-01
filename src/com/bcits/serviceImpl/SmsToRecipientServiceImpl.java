package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.SmsToRecipient;
import com.bcits.service.SmsToRecipientService;

@Repository
public class SmsToRecipientServiceImpl extends GenericServiceImpl<SmsToRecipient> implements SmsToRecipientService 
{
	public List<?> findAllRecipients(ModelMap model) {
		List<SmsToRecipient> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("SmsToRecipient.findAllUsers").getResultList();
			model.addAttribute("smsUserList",list);
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@Override
	public List<SmsToRecipient> getDataBySubdiv(String division,String subdivision) 
	{
		List<SmsToRecipient> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("SmsToRecipient.findsubdiv").setParameter("subdivision", subdivision).getResultList();
			if(list.size()==0)
			{
				list=postgresMdas.createNamedQuery("SmsToRecipient.finddiv").setParameter("division", division).getResultList();
			}
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}
}
