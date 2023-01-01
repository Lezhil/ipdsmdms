package com.bcits.service;

import com.bcits.entity.PfcFOCD3IntermediateEntity;

public interface PfcFOCD3Service  extends GenericService<PfcFOCD3IntermediateEntity>{
	public String insertFocD3Report(String data,String servicename);
}
