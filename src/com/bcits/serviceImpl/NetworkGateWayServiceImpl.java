package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.EmailGateway;
import com.bcits.entity.NetworkPathGateWay;
import com.bcits.service.EmailGatewayService;
import com.bcits.service.NetworkGateWayService;


@Repository
public class NetworkGateWayServiceImpl extends GenericServiceImpl<NetworkPathGateWay> implements NetworkGateWayService
{

	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public NetworkPathGateWay findUser() 
	{	
		NetworkPathGateWay sms=null;
		List<NetworkPathGateWay> list = postgresMdas.createNamedQuery("NetworkPathGateWay.findUser").getResultList();
		if(list.size() > 0)
		{
			 sms= list.get(0);
		}
		
		
		return sms;
	}
}
