package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.SmsGateway;
import com.bcits.service.SmsGatewayService;


@Repository
public class SmsGatewayServiceImpl extends GenericServiceImpl<SmsGateway> implements SmsGatewayService
{

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.SmsGatewayService#findUser(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.EmailGatewayService#findUser(java.lang.String)
	 */
 
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public SmsGateway findUser() 
	{	
		SmsGateway sms=null;
		List<SmsGateway> list = postgresMdas.createNamedQuery("SmsGateway.findUser").getResultList();
		if(list.size() > 0)
		{
			 sms= list.get(0);
		}
		
		
		return sms;
	}
}
