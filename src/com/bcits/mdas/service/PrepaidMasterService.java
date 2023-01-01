package com.bcits.mdas.service;

import com.bcits.mdas.entity.PrepaidMaster;
import com.bcits.service.GenericService;

public interface PrepaidMasterService extends GenericService<PrepaidMaster> {

	PrepaidMaster getDataByMtrno(String mtrno);

}
