/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.AssignsEstimationRuleEntity;
import com.bcits.mdas.service.AssignEstimationRuleService;
import com.bcits.serviceImpl.GenericServiceImpl;

/**
 * @author Tarik
 *
 */
@Repository
public class AssignEstimationRuleServiceImpl extends GenericServiceImpl<AssignsEstimationRuleEntity> implements AssignEstimationRuleService{

	@Override
	public AssignsEstimationRuleEntity getAssignRuleId(String ruleType, String locationType, String accno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("AssignsEstimationRuleEntity.getAssignRuleId", AssignsEstimationRuleEntity.class)
					.setParameter("e_rule_id", ruleType)
					.setParameter("location_type", locationType)
					.setParameter("location_code", accno)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
