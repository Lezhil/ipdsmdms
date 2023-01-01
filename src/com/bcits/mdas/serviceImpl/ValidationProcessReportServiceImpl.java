/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ValidationProcessReportEntity;
import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.mdas.service.ValidationProcessReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

/**
 * @author Tarik
 *
 */
@Repository
public class ValidationProcessReportServiceImpl extends GenericServiceImpl<ValidationProcessReportEntity> implements ValidationProcessReportService{

	@Override
	public ValidationProcessReportEntity getAssignRuleId(String ruleType, String meter_number, String monthyear) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("ValidationProcessReportEntity.getAssignRuleId", ValidationProcessReportEntity.class)
					.setParameter("v_rule_id", ruleType)
					.setParameter("meter_number", meter_number)
					.setParameter("monthyear", monthyear)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<ValidationProcessReportEntity>  getValidationReportData(String zone,String circle,String ruleid, String monthyear, String town) {
		try {
			
			if("%".equalsIgnoreCase(zone)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("ValidationProcessReportEntity.getallValidationReportData", ValidationProcessReportEntity.class).setParameter("v_rule_id", ruleid).setParameter("monthyear", monthyear).getResultList();
			} else if("%".equalsIgnoreCase(circle)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("ValidationProcessReportEntity.getValidationReportDatabyzone", ValidationProcessReportEntity.class).setParameter("zone", zone).setParameter("v_rule_id", ruleid).setParameter("monthyear", monthyear).getResultList();
				/*
				 * } else if("%".equalsIgnoreCase(division)) { return
				 * getCustomEntityManager("postgresMdas").createNamedQuery(
				 * "ValidationProcessReportEntity.getValidationReportDatabyCircle",
				 * ValidationProcessReportEntity.class).setParameter("zone",
				 * zone).setParameter("circle", circle).setParameter("v_rule_id",
				 * ruleid).setParameter("monthyear", monthyear).getResultList(); } else
				 * if("%".equalsIgnoreCase(subdivision)) { return
				 * getCustomEntityManager("postgresMdas").createNamedQuery(
				 * "ValidationProcessReportEntity.getValidationReportDatabyDivision",
				 * ValidationProcessReportEntity.class).setParameter("zone",
				 * zone).setParameter("circle", circle).setParameter("division",
				 * division).setParameter("v_rule_id", ruleid).setParameter("monthyear",
				 * monthyear).getResultList();
				 */
			}else if("%".equalsIgnoreCase(town)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("ValidationProcessReportEntity.getValidationReportDatabySubDiv", ValidationProcessReportEntity.class).setParameter("zone", zone).setParameter("circle", circle).setParameter("v_rule_id", ruleid).setParameter("monthyear", monthyear).getResultList();
			}else {
				return getCustomEntityManager("postgresMdas").createNamedQuery("ValidationProcessReportEntity.getValidationReportData", ValidationProcessReportEntity.class).setParameter("zone", zone).setParameter("circle", circle).setParameter("town_code", town).setParameter("v_rule_id", ruleid).setParameter("monthyear", monthyear).getResultList();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<String> getEstimationRules(String ruleId){
		List<String> list=new ArrayList<>();
		final String auto_trigger="t";
//		System.out.println("in service=============="+ruleId);
		String sqlqy="select e_rule_id,e_rule_name from meter_data.estimation_rule_mst where e_rule_id = (SELECT estimation_rule FROM meter_data.validation_rule_mst WHERE v_rule_id='"+ruleId+"' and trig_auto_estimate='t')";
		try {
			list=postgresMdas.createNativeQuery(sqlqy).getResultList();
			//list=postgresMdas.createNamedQuery("VeeRuleEntity.getEstimationRulesByVeeRuleId").setParameter("ruleid", ruleId).setParameter("trigger_v_rule", auto_trigger).getResultList();
//			System.out.println("+++in service======="+list.size());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	
}
