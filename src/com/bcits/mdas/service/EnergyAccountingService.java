package com.bcits.mdas.service;

import com.bcits.service.GenericService;

public interface EnergyAccountingService  extends GenericService<Object>{

	//Service Methods To push EA DATA Monthly
	public String scheduleDataPushBoundaryFeeder();
	public String scheduledataPushMainFeeder();
	public String scheduledataPushDT();
	
	//Service emthods to push data for D1-D7
	
	
	
}
