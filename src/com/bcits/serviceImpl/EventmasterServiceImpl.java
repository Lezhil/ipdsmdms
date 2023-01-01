package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.EventMaster;
import com.bcits.service.EventmasterService;

@Repository
public class EventmasterServiceImpl extends GenericServiceImpl<EventMaster> implements EventmasterService{

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<EventMaster> findAll()
	{
		return postgresMdas.createNamedQuery("EventMaster.findAll").getResultList();
	}

	@Override
	public List<EventMaster> getEventDescription(String code) {
		return postgresMdas.createNamedQuery("EventMaster.findDesc").setParameter("code",code).getResultList();
	}
	
	@Override
	public String gettampertypeDetails(String eventcode)
	{
		
		return  (String) getCustomEntityManager("postgresMdas").createNamedQuery("EventMaster.findtampertype").setParameter("eventcode",Integer.parseInt(eventcode)).getSingleResult();
	}
	public List<EventMaster> getEventCategory() {
		return postgresMdas.createNamedQuery("EventMaster.catgoryList").getResultList();
	}
	
}
