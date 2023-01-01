package com.bcits.service;

import com.bcits.entity.SmsGateway;

public interface SmsGatewayService extends GenericService<SmsGateway>
{

	public abstract SmsGateway findUser();

}