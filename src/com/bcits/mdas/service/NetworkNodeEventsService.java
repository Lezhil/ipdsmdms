package com.bcits.mdas.service;

import com.bcits.mdas.entity.NetworkNodeEvents;
import com.bcits.service.GenericService;

public interface NetworkNodeEventsService  extends GenericService<NetworkNodeEvents>{

	long highcount();

}
