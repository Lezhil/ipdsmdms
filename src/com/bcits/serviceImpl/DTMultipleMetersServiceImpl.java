package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.dialect.PostgresPlusDialect;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.DTMultipleMetersEntity;
import com.bcits.service.DTMultipleMetersService;
@Repository
public class DTMultipleMetersServiceImpl extends GenericServiceImpl<DTMultipleMetersEntity> implements DTMultipleMetersService{

	
	@Override
	public List getDtMultipleMtrsData() {
		System.out.println("inside getDtMultipleMtrsData method");
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String to_date=sdf.format(cal.getTime());
		System.out.println(to_date);
		cal.add(Calendar.DATE, -5);
		String from_date=sdf.format(cal.getTime());
		System.out.println(from_date);
		
		
		List list=new ArrayList<>();
		String qry="SELECT officeid,tp_town_code,dtname,dttpid,iptime,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\n" +
				   "round((sum(b.kwh)/(CASE WHEN sum(b.kvah)=0 THEN 1 ELSE sum(b.kvah) END )),4) as pf FROM \n" +
				   "(SELECT dttpid,meterno,officeid,tp_town_code,dtname FROM meter_data.dtdetails WHERE \n" +
				   " dttpid in (SELECT dttpid FROM (\n" +
				   "SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n" +
				   " GROUP BY dttpid HAVING count(*)>1)b))a,\n" +
				   "(select meter_number,to_char(read_time,'yyyy-MM-dd HH24:mi:ss') as iptime,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,sum(COALESCE(kw,'0') )as kw,sum(COALESCE(kva,'0')) as kva \n" +
				   "from meter_data.load_survey WHERE to_char(read_time,'yyyy-MM-dd')>='"+from_date+"' AND to_char(read_time,'yyyy-MM-dd')<='"+to_date+"' \n" +
				   "GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd HH24:mi:ss'))b\n" +
				   "WHERE a.meterno=b.meter_number GROUP BY officeid,tp_town_code,dtname,dttpid,iptime ORDER BY  iptime ASC ";
		
		try {
			System.out.println("dt multiple meters qry--"+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	/*	if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				
				try {
				Object[] obj=(Object[]) list.get(i);
				DTMultipleMetersEntity dt=new DTMultipleMetersEntity();
				dt.setTown(String.valueOf(obj[1]));
				dt.setDtname(String.valueOf(obj[2]));
				dt.setDttpid(String.valueOf(obj[3]));
				if(obj[4]!=null){
					
				dt.setIptime(Timestamp.valueOf(obj[4]+""));
				}
				if(obj[5]!=null){
				dt.setKwh( Double.parseDouble(obj[5]+""));
				}
				if(obj[6]!=null){
				dt.setKvah(Double.parseDouble(obj[6]+""));
				}
				if(obj[7]!=null){
				dt.setKw( Double.parseDouble(obj[7]+""));
				}
				if(obj[8]!=null){
				dt.setKva( Double.parseDouble(obj[8]+""));
				}
				if(obj[9]!=null){
				dt.setPf(Double.parseDouble(obj[9]+""));
				}
				dt.setTimestamp(new Timestamp(System.currentTimeMillis()));
				System.out.println("count--"+i);
				//save(dt);
				try {
					save(dt);
				} catch (Exception e) {
					continue;
				}
				
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		
		}
		*/
		System.out.println(list.size());
		
		return list;
	}

}
