/**
 * 
 */
package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.ValidationProcessReportEntity;
import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface ValidationProcessReportService extends GenericService<ValidationProcessReportEntity>{

	ValidationProcessReportEntity getAssignRuleId(String ruleType, String meter_number, String monthyear);

	List<ValidationProcessReportEntity> getValidationReportData(String zone, String circle, String ruleid, String monthyear, String town);

	List<String> getEstimationRules(String ruleId);

}
