package com.bcits.mdas.serviceImpl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bcits.mdas.entity.PrepaidMaster;
import com.bcits.mdas.service.PrepaidMasterService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class PrepaidMasterServiceImpl extends GenericServiceImpl<PrepaidMaster> implements PrepaidMasterService {

	@Override
	public PrepaidMaster getDataByMtrno(String mtrno) {
		
		return postgresMdas.createNamedQuery("PrepaidMaster.getDataByMtrno", PrepaidMaster.class).setParameter("mtrno", mtrno).getSingleResult();
		
	}

}
