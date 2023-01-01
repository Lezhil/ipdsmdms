package com.bcits.service;

import com.bcits.entity.EmailGateway;

public interface EmailGatewayService extends GenericService<EmailGateway>
{

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.SmsGatewayService#findUser(java.lang.String)
	 */
	public abstract EmailGateway findUser();

}