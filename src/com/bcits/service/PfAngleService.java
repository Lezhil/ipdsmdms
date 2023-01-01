package com.bcits.service;

import java.util.List;

import com.bcits.entity.PfAngle;

public interface PfAngleService extends GenericService<PfAngle>

{
	public List<PfAngle> getLeadAngle(float lead);

}