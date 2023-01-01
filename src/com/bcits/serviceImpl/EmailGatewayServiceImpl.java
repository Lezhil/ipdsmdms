package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.EmailGateway;
import com.bcits.service.EmailGatewayService;


@Repository
public class EmailGatewayServiceImpl extends GenericServiceImpl<EmailGateway> implements EmailGatewayService
{

	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public EmailGateway findUser() 
	{	
		EmailGateway sms=null;
		List<EmailGateway> list = postgresMdas.createNamedQuery("EmailGateway.findUser").getResultList();
		if(list.size() > 0)
		{
			 sms= list.get(0);
		}
		
		
		return sms;
	}
}
