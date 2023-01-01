package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.service.TodService;

@Repository
public class TodServiceimpl extends GenericServiceImpl<Object> implements TodService  {
	

@Override
public List<?> gettodreport(String fdate, String tdate, String mtrno) {
	List<?> reports1=new ArrayList<>();
	
	try 
	{
		String qry="select id,tod_gdate,meter_number,tod1s,tod1e,kwh1,kvah1,tod2s,tod2e,kwh2,kvah2,tod3s,tod3e,kwh3,kvah3,tod4s,tod4e,kwh4,kvah4,tod5s,tod5e,kwh5,kvah5,tod6s,tod6e,kwh6,kvah6,tod7s,tod7e,kwh7,kvah7,tod8s,tod8e,kwh8,kvah8 from meter_data.tod_wise_daily_data_aggregation where meter_number='"+mtrno+"' and tod_gdate BETWEEN '"+fdate+"' AND '"+tdate+"'";
		System.out.println("qry------------>"+qry);
		reports1=postgresMdas.createNativeQuery(qry).getResultList();
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	return reports1;
		
}
	
}