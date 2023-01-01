package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import com.bcits.entity.ServiceExceptionEntity;
import com.bcits.service.ServiceexceptionsService;
@Repository
public class ServiceexceptionsServiceimpl extends GenericServiceImpl<ServiceExceptionEntity> implements ServiceexceptionsService  {
	
	//Method to pull Service type
	@Override
	public List<?> getExceptionreports() {
		List<?> reports=new ArrayList<>();
		String qry="select service_name from meter_data.service_exception_log";
		try{
			reports=postgresMdas.createNativeQuery(qry).getResultList();
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return reports;
	}

	@Override
	public List<?> getExceptionreport(String fdate,String tdate,String service) {
		
		List<?> reports1=new ArrayList<>();
		
		try 
		{
			String qyr="select distinct meterdata.service_name,meterdata.requester,meterdata.provider,meterdata.exception,meterdata.time_stamp,meterdata.notified,masterData.emails,masterData.smss from\n" +
					"(select service_name,requester,provider,exception,time_stamp,notified from meter_data.service_exception_log where to_char(time_stamp,'yyyy-MM-dd')>='"+fdate+"' and to_char(time_stamp,'yyyy-MM-dd')<='"+tdate+"' and service_name='"+service+"')meterdata,\n" +
					"(select emails,smss,service_name from meter_data.service_excp_notify_setting)masterdata WHERE meterdata.service_name=masterdata.service_name";
			System.out.println("qry---------------->"+qyr);
			reports1=postgresMdas.createNativeQuery(qyr).getResultList();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return reports1;
			
	}

	@Override
	public List<?> getmobileno() {
		List<?> list=null;
		String qry="";
		
		try {
			qry="select distinct smss from meter_data.service_excp_notify_setting;";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

}
