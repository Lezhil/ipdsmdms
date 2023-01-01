package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.ExceptionManagementEntity;
import com.bcits.service.ExceptionMangService;

@Repository
public class ExceptionMangServiceImpl extends GenericServiceImpl<ExceptionManagementEntity> implements ExceptionMangService{

	@Override
	public List findALL() {
		List list=null;
		try {
			String qry="SELECT e.id,e.ticketno,accno,meterno,subdiv,e.exceptions,feedback,status,flag\n" +
						"FROM meter_data.mm m ,meter_data.exception_management e WHERE m.metrno=e.meterno\n" +
						"AND rdngmonth='201812'";
		System.out.println("exception query--"+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
	}

	@Override
	public int UpdateFlag(int id) {
		System.out.println("inside update method--"+id);
		int count=0;
		try {
			String qry="Update meter_data.exception_management set FLAG=1 WHERE id='"+id+"'";
			System.out.println("updateqry--"+qry);
			 count=postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	

}
