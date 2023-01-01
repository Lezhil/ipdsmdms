package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.bcits.entity.ServiceStatusEntity;
import com.bcits.service.ServiceStatusService;
@Repository
public class ServiceStatusServiceImpl extends GenericServiceImpl<ServiceStatusEntity> implements ServiceStatusService{

	@Override
	public List<?> getServiceStatus() {
		List<?> list =null;
		String qry=null;
		try {
			qry="select service_name,service_status,service_type,last_ser_sta_chan_date,id from meter_data.service_status;";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getEditServiceStatus(int id) {
		List<?> list =null;
		String qry="";
		try {
			qry="select service_name,service_status,service_type,id from meter_data.service_status where id="+id+"";
			list= getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
          
			return list;
	}

	@Override
	public int getModifyServiceStatus(String id,String servicename, String servicestatus, String servicetype) {
		long currtime = System.currentTimeMillis();
		Timestamp uppdatetime = new Timestamp(currtime);
		int i = 0;
				try {
					String qry="update meter_data.service_status set service_name='"+servicename+"',service_status='"+servicestatus+"',service_type='"+servicetype+"',last_ser_sta_chan_date='"+uppdatetime+"' where id='"+id+"'";
					i = postgresMdas.createNativeQuery(qry).executeUpdate();
					}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				 return i;
	}

}
