package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.mdas.service.EstimationRuleService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class EstimationRuleServiceImpl extends GenericServiceImpl<EstimationRuleEntity>  implements EstimationRuleService  {

	@Override
	public List<EstimationRuleEntity> getEstimationActiveRule() {
		return  postgresMdas.createNamedQuery("EstimationRuleEntity.findActiveRules").getResultList();
	}
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<EstimationRuleEntity> getEstimationRule()
	{
		
		return  postgresMdas.createNamedQuery("EstimationRuleEntity.findAllESTRules").getResultList();
	}
	
	@Override
	public EstimationRuleEntity getESTRuleById(String id) {
		
		return postgresMdas.createNamedQuery("EstimationRuleEntity.getESTRuleById",EstimationRuleEntity.class).setParameter("ID", id).getSingleResult();
	}
	
	@Override
	public String getlatestRuleId() {
		String sql="SELECT B.\"rule\"||(case WHEN \"length\"(B.id)=1 then '0'||B.id else B.id  END) from\r\n" + 
				"(\r\n" + 
				"SELECT substr(A.rule_id, 0,4) as rule,CAST(CAST(substr(A.rule_id, 4,\"length\"(A.rule_id)) as INTEGER)+1 as TEXT) as id FROM\r\n" + 
				"(\r\n" + 
				"SELECT COALESCE(\"max\"(e_rule_id),'EST00') as rule_id FROM meter_data.estimation_rule_mst\r\n" + 
				")A\r\n" + 
				")B";
		
		String id=String.valueOf( postgresMdas.createNativeQuery(sql).getSingleResult());
//		System.err.println("ID  :"+ id);
		return id;
		//return  postgresMdas.createNamedQuery("VeeRuleEntity.getVeeRuleById").setParameter("ID", id).getResultList();
	}

}
