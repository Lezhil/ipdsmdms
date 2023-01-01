package com.bcits.service;

import com.bcits.entity.EmailGateway;
import com.bcits.entity.NetworkPathGateWay;

public interface NetworkGateWayService extends GenericService<NetworkPathGateWay>
{

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.SmsGatewayService#findUser(java.lang.String)
	 */
	public abstract NetworkPathGateWay findUser();

}