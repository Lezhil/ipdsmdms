package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.MessagingSettings;
import com.bcits.service.MessageSettingsService;

@Repository

public class MessageSettingsServiceImpl extends GenericServiceImpl<MessagingSettings> implements MessageSettingsService
{

	
	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.MessageSettingsService#findAll()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	
	public MessagingSettings findUser() 
	{	
		MessagingSettings sms=null;
		List<MessagingSettings> list = postgresMdas.createNamedQuery("MessagingSettings.findUser").getResultList();
		if(list.size() > 0)
		{
			 sms= list.get(0);
		}
		
		
		return sms;
	}
	
}
