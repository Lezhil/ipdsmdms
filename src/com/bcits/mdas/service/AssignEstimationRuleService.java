/**
 * 
 */
package com.bcits.mdas.service;

import com.bcits.mdas.entity.AssignsEstimationRuleEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface AssignEstimationRuleService extends GenericService<AssignsEstimationRuleEntity> {

	AssignsEstimationRuleEntity getAssignRuleId(String ruleType, String locationType, String accno);


}
