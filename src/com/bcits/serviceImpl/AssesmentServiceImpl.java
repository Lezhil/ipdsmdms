package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.Assesment;
import com.bcits.service.AssesmentService;

@Repository
public class AssesmentServiceImpl extends GenericServiceImpl<Assesment> implements AssesmentService
{

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Assesment> findAll()
	{
		return postgresMdas.createNamedQuery("Assesment.findAll").getResultList();
	}
}
