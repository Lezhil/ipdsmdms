package com.bcits.mdas.service;
import java.util.List;

import com.bcits.mdas.entity.BusinessRoleEntity;
import com.bcits.service.GenericService;

public interface BusinessRoleService extends GenericService<BusinessRoleEntity>{

	public List<?> getRoleStatus(String designation,String username);

	public Object gettownByScheme(String scheme);

	public List<?> getDistinctPeriod();

	public Object getfdrcountData(String period,String TownId[]);

	public String getStateName();

	public String getDiscomName();

	List<?> getDistinctPeriodD1();

	String getCompanyName();

	List<?> getDistinctPeriodD7();

	
}

