package com.bcits.service;

import java.text.ParseException;
import java.util.List;

import com.bcits.entity.VirtualLocationParameters;

public interface VirtualLocParam extends GenericService<VirtualLocationParameters>{

	public List<?> getVirtualLocationParameters(String circle, String division, String subdiv, String vlType, String vlName, String fromdate, String todate, String projectName);

	void savelocParams(List<?> list) throws ParseException;

	List<?> getvirtualLocationNames();
}
