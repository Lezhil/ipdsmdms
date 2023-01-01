/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.AssignValidationRuleEntity;
import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.service.AssignValidationRuleService;
import com.bcits.serviceImpl.GenericServiceImpl;

/**
 * @author Tarik
 *
 */
@Repository
public class AssignValidationRuleServiceImpl extends GenericServiceImpl<AssignValidationRuleEntity> implements AssignValidationRuleService{

	@Override
	public AssignValidationRuleEntity getAssignRuleId(String ruleType, String locationType, String accno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("AssignValidationRuleEntity.getAssignRuleId", AssignValidationRuleEntity.class)
					.setParameter("v_rule_id", ruleType)
					.setParameter("location_type", locationType)
					.setParameter("location_code", accno)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<AssignValidationRuleEntity> getAllDetailsbyId(String ruleId) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("AssignValidationRuleEntity.getAllDetailsbyId", AssignValidationRuleEntity.class)
					.setParameter("v_rule_id", ruleId)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
