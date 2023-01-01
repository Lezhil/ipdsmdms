package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.service.GenericService;

public interface EstimationRuleService extends GenericService<EstimationRuleEntity> {
	
	public List<EstimationRuleEntity> getEstimationActiveRule();
	public String getlatestRuleId();
	public List<EstimationRuleEntity> getEstimationRule();
	public EstimationRuleEntity getESTRuleById(String id);
	
}