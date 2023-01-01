package com.bcits.service;

import java.util.List;

import com.bcits.entity.EventMaster;

public interface EventmasterService extends GenericService<EventMaster>{
	
	public List<EventMaster> findAll();

	public List<EventMaster> getEventDescription(String code);

		public String gettampertypeDetails(String valueOf);
		public List<EventMaster> getEventCategory();

}