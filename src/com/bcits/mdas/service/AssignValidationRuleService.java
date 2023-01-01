/**
 * 
 */
package com.bcits.mdas.service;


import java.util.List;

import com.bcits.mdas.entity.AssignValidationRuleEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface AssignValidationRuleService extends GenericService<AssignValidationRuleEntity> {

	AssignValidationRuleEntity getAssignRuleId(String ruleType, String locationType, String accno);
	
	List<AssignValidationRuleEntity> getAllDetailsbyId(String ruleId);

	

}
