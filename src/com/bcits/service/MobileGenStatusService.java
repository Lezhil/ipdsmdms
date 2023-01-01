package com.bcits.service;

import java.util.List;

import com.bcits.entity.MobileGenStatusEntity;

public interface MobileGenStatusService extends GenericService<MobileGenStatusEntity> {

	
	List<?> getALLMobileStatus();
}
