package com.bcits.mdas.serviceImpl;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.NetworkNodeEvents;
import com.bcits.mdas.service.NetworkNodeEventsService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class NetworkNodeEventsServiceImpl extends GenericServiceImpl<NetworkNodeEvents> implements NetworkNodeEventsService {

	@Override
	public long highcount() {
		BigDecimal hn;
		int i=0;
		
		try
		{
		 hn =(BigDecimal) postgresMdas.createNativeQuery("select max(seq_id) from meter_data.network_node_events")
				.getSingleResult();
		 i=hn.intValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return i;
		
	}

}
