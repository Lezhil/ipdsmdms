package com.bcits.service;

import com.bcits.entity.PfcD3ReportIntermediateEntity;

public interface PfcOnlineD3PmtCompService extends GenericService<PfcD3ReportIntermediateEntity>{

	public String insertPfcOnline(String data,String servicename);

}
