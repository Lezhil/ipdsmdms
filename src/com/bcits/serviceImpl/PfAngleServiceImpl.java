package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.PfAngle;
import com.bcits.service.PfAngleService;

@Repository
public class PfAngleServiceImpl extends GenericServiceImpl<PfAngle> implements PfAngleService 
{

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<PfAngle> getLeadAngle(float lead)
	{
		
		return postgresMdas.createNamedQuery("PfAngle.getLeadAngle").setParameter("pfLead", lead).getResultList();
	}
}
