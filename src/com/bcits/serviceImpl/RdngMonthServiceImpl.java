package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.RdngMonth;
import com.bcits.service.RdngMonthService;

@Repository
public class RdngMonthServiceImpl extends GenericServiceImpl<RdngMonth> implements RdngMonthService 
{

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.RdngMonthService#findAll()
	 */
	@Override
	public int findAll()
	{
		List<RdngMonth> list = postgresMdas.createNamedQuery("RdngMonth.findAll").getResultList();
		int month = list.get(0).getRmonth();
		return month;
	}
}
