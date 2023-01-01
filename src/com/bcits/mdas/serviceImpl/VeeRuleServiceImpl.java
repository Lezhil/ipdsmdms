package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.mdas.service.VeeRuleService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class VeeRuleServiceImpl extends GenericServiceImpl<VeeRuleEntity>  implements VeeRuleService {
//	public List<VeeRuleEntity> getVeeRule(){
//		
//		return null;
//	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VeeRuleEntity> getVeeRule()
	{
		
		return  postgresMdas.createNamedQuery("VeeRuleEntity.findAllVeeRules").getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VeeRuleEntity> getActiveVeeRule()
	{
		
		return  postgresMdas.createNamedQuery("VeeRuleEntity.findAllActiveVeeRules").getResultList();
	}

	@Override
	public VeeRuleEntity getVeeRuleById(String id) {
		
		return postgresMdas.createNamedQuery("VeeRuleEntity.getVeeRuleById",VeeRuleEntity.class).setParameter("ruleid", id).getSingleResult();
	
		//return  postgresMdas.createNamedQuery("VeeRuleEntity.getVeeRuleById").setParameter("ID", id).getResultList();
	}
	
	
	
	@Override
	public VeeRuleEntity getVeeRule(String id) 
	{
		System.out.println("Inside VEE MEthod");
		String sql ="SELECT * FROM meter_data.validation_rule_mst  where v_rule_id='VEE16'";
		VeeRuleEntity id1=(VeeRuleEntity) postgresMdas.createNativeQuery(sql).getSingleResult();
		System.out.println("ID  :"+id1);
		return id1;
	}
	
	@Override
	public String getlatestRuleId() {
		String sql="SELECT B.\"rule\"||(case WHEN \"length\"(B.id)=1 then '0'||B.id else B.id  END) from\r\n" + 
				"(\r\n" + 
				"SELECT substr(A.rule_id, 0,4) as rule,CAST(CAST(substr(A.rule_id, 4,\"length\"(A.rule_id)) as INTEGER)+1 as TEXT) as id FROM\r\n" + 
				"(\r\n" + 
				"SELECT COALESCE(\"max\"(v_rule_id),'VEE00') as rule_id FROM meter_data.validation_rule_mst\r\n" + 
				")A\r\n" + 
				")B";
		
		String id=(String) postgresMdas.createNativeQuery(sql).getSingleResult();
		System.out.println("ID  :"+id);
		return id;
		//return  postgresMdas.createNamedQuery("VeeRuleEntity.getVeeRuleById").setParameter("ID", id).getResultList();
	}
}
