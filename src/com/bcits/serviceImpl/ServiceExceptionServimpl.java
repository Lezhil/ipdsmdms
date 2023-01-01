package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.ServiceExceptionEntity;
import com.bcits.entity.ServiceExcpNotificationSetting;
import com.bcits.service.ServiceExceptionService;
@Repository
public class ServiceExceptionServimpl extends GenericServiceImpl<ServiceExcpNotificationSetting> implements ServiceExceptionService {

	@Override
	public List<?> getexistingexception() {
		List<?> list=null;
		String qry="";
		try {
			qry="select id,service_name,requester,provider,exception,exceptiontime,notified from meter_data.service_exception_log where ackmessage = '' or  ackmessage IS NULL";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return list;
	}

	@Override
	public List<?> getAckData(String fdate,String tdate,String serviceId) {
		List<?> list=null;
		String qry="";
		try {
			qry="select service_name,requester,provider,exception,exceptiontime,\n" +
					"notified,ackdate,ack_by,ackmessage\n" +
					"from meter_data.service_exception_log  where to_char(time_stamp,'yyyy-MM-dd')>='"+fdate+"' and to_char(time_stamp,'yyyy-MM-dd')<='"+tdate+"' and service_name='"+serviceId+"'  AND ackmessage IS NOT NULL AND ackmessage != ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			//System.out.println(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int tosaveack(String AckMsg,int ackId,String username) {
		long currtime = System.currentTimeMillis();
	    Timestamp updatetime = new Timestamp(currtime);
		int i=0;
		String qry="";
		try {
			qry="update meter_data.service_exception_log set ackmessage='"+AckMsg+"',ack_by='"+username+"',ackdate='"+updatetime+"' WHERE id='"+ackId+"';";
			i=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	
	@Override
	public int setackstatus(String str) {
		return postgresMdas.createNativeQuery(str).executeUpdate();
		
	}
	
	@Override
	public List<?> getServiceName() {
		List<?> list=null;
		String qry="";
		try {
			qry="SELECT DISTINCT service_name from meter_data.service_status";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getAllServiceExceNotifData() {
		List<?> list=null;
		String qry="";
		try {
			qry="SELECT * from  meter_data.service_excp_notify_setting";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getEditDetils(int id) {
		
		
			String qry="select id, service_name, emails, smss from meter_data.service_excp_notify_setting where id ="+id+""; 
			
			
			return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
		
		
	}

	@Override
	public int UpdateEditDetils(String mobileNo, String emailId, String id, String serviceName) {
		// TODO Auto-generated method stub
		String qry="update meter_data.service_excp_notify_setting  set  emails = '"+emailId+"', smss= '"+mobileNo+"'  where id ="+id+""; 
		
		
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		
	}

	
	
	
	
}
