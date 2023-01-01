package com.bcits.mdas.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.SimDetailsEntity;
import com.bcits.mdas.service.SimDetailsService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Component
@Repository
public class SimDetailsServiceImpl extends GenericServiceImpl<SimDetailsEntity> implements SimDetailsService{

	@Override
	public List<?> getSimDetails() {
		return postgresMdas.createNamedQuery("SimDetailsEntity.getsimdetails").getResultList();
	}

	@Override
	public List<?> addSimDetails() {
		return postgresMdas.createNamedQuery("SimDetailsEntity.getsimdetails").getResultList();
	}

	@Override
	public List<?> getEditSimDetails(int id) {
        String qry="select id,nwk_ser_provider,mob_dir_number,sim_status,sim_static_ip from meter_data.sim_details where id="+id+"";
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public int getModifySimDetails(String simid,String mdnumberr, String simstaticipp, String nsproviderr, String simstatuss,String userName) {
		long currtime = System.currentTimeMillis();
	    Timestamp updatetime = new Timestamp(currtime);
        int i=0;
        String qry="update meter_data.sim_details set nwk_ser_provider='"+nsproviderr+"',mob_dir_number='"+mdnumberr+"',sim_status='"+simstatuss+"',sim_static_ip='"+simstaticipp+"',update_by='"+userName+"',update_date='"+updatetime+"' where id="+simid+"";
        try {
			i=postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return i;
	}

	@Override
	public List<?> getConsumerData(String mtrno) {
		
		String qry="";
		List<?> list=null;
		try {
			 qry="select fdrcategory from meter_data.master_main where mtrno='"+mtrno+"'";
				 list = postgresMdas.createNativeQuery(qry).getResultList();
				 System.out.println("feeder cat..."+list);
		} catch (Exception e) {
              e.printStackTrace();		
              }
		
		return list;
	}

	@Override
	public List<?> getMeterData(String mtrno,String fdate,String tdate) {
		String qry="";
		List<?> list=null;
		try {
			qry="select ma.meter_number,ma.event_code,em.event,ma.event_time FROM meter_data.events ma left join "
					+ "meter_data.event_master em on to_number(ma.event_code,'9999')=em.event_code where ma.meter_number='"+mtrno+"' "
					+ "and to_char(ma.event_time,'yyyy-MM-dd')>='"+fdate+"' and to_char(ma.event_time,'yyyy-MM-dd')<='"+tdate+"'"
					+ "ORDER BY ma.event_time desc";
			list = postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
              e.printStackTrace();
               }
		return list;
	}
}
