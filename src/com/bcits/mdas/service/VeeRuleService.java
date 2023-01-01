/**
 * 
 */
package com.bcits.mdas.service;


import java.util.List;

import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik Afwas
 *
 */
public interface VeeRuleService extends GenericService<VeeRuleEntity> {
	
	public List<VeeRuleEntity> getVeeRule();
	public VeeRuleEntity getVeeRuleById(String id);
	public VeeRuleEntity getVeeRule(String id);
	public String getlatestRuleId();
	public List<VeeRuleEntity> getActiveVeeRule();
}
