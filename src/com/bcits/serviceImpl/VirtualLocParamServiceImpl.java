package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.VirtualLocationParameters;
import com.bcits.service.VirtualLocParam;
@Repository
public class VirtualLocParamServiceImpl  extends GenericServiceImpl<VirtualLocationParameters> implements VirtualLocParam{

	@Override
	public List<?> getVirtualLocationParameters(String circle,String division,String subdiv,String vlType,String vlName,String fromdate,String todate,String projectName) {

		String tableName="";
		String qry="";
		List<?> locParams=new ArrayList();
		if("Consumer".equalsIgnoreCase(vlType)) {
			tableName="consumermaster";
		}
		else if("feeder".equalsIgnoreCase(vlType)) {
			tableName="feederdetails";
		}
		else if("dt".equalsIgnoreCase(vlType)) {
			tableName="dtdetails";
		}
		if("TNEB".equalsIgnoreCase(projectName)) {
		 qry="select b.subdivision,b.vl_id,b.location_type,b.vl_name, a.read_time,a.kwh,a.kvah,a.kw,a.kva,round((case when a.kvah='0' THEN 0 else  (a.kwh/a.kvah) END),3) as pf from \n" +
				"(select read_time, sum(kwh) as kwh,sum(kvah) as kvah,sum(kw) as kw, sum(kva) as kva from meter_data.load_survey where to_char(read_time,'yyyy-MM-dd') BETWEEN '"+fromdate+"' and '"+todate+"' and meter_number IN\n" +
				"(select meterno from meter_data."+tableName+" where vl_id=(select vl_id from meter_data.virtual_location where  UPPER(location_type)=UPPER('"+vlType+"') and vl_name='"+vlName+"' and subdivision like '"+subdiv+"')\n" +
				") GROUP BY read_time ORDER BY read_time)a,\n" +
				"(select vl_id from meter_data."+tableName+" )c,\n" +
				"(select vl_id,vl_name,location_type,subdivision from meter_data.virtual_location where UPPER(location_type)=UPPER('"+vlType+"') and vl_name='"+vlName+"' and subdivision like '"+subdiv+"' and vl_id is not null\n" +
				")b\n" +
				"where c.vl_id=b.vl_id GROUP BY a.read_time,a.kwh,a.kvah,a.kw,a.kva,b.vl_id,b.vl_name,b.location_type,b.subdivision ORDER BY read_time";
		}
		else{
			qry="select b.subdivision,b.vl_id,b.location_type,b.vl_name, a.read_time,a.kwh,a.kvah,a.kw,a.kva,round((case when a.kvah='0' THEN 0 else  (a.kwh/a.kvah) END),3) as pf from \n" +
					"(select read_time, sum(kwh)/1000 as kwh,sum(kvah)/1000 as kvah,sum(kw)/1000 as kw, sum(kva)/1000 as kva from meter_data.load_survey where to_char(read_time,'yyyy-MM-dd') BETWEEN '"+fromdate+"' and '"+todate+"' and meter_number IN\n" +
					"(select meterno from meter_data."+tableName+" where vl_id=(select vl_id from meter_data.virtual_location where  UPPER(location_type)=UPPER('"+vlType+"') and vl_name='"+vlName+"' and subdivision like '"+subdiv+"')\n" +
					") GROUP BY read_time ORDER BY read_time)a,\n" +
					"(select vl_id from meter_data."+tableName+" )c,\n" +
					"(select vl_id,vl_name,location_type,subdivision from meter_data.virtual_location where UPPER(location_type)=UPPER('"+vlType+"') and vl_name='"+vlName+"' and subdivision like '"+subdiv+"' and vl_id is not null\n" +
					")b\n" +
					"where c.vl_id=b.vl_id GROUP BY a.read_time,a.kwh,a.kvah,a.kw,a.kva,b.vl_id,b.vl_name,b.location_type,b.subdivision ORDER BY read_time";
		}
		locParams=postgresMdas.createNativeQuery(qry).getResultList();
		return locParams;
	}
	
	@Override
	public void savelocParams(List<?> list) throws ParseException {
		DateFormat formatter;
	      formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	     List<VirtualLocationParameters> vParameters=new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Object[] parameters=(Object[]) list.get(i);
			 Date date = (Date) formatter.parse(parameters[4].toString());
		      Timestamp readTime = new Timestamp(date.getTime());
			Timestamp timestamp = new Timestamp(new Date().getTime());
		
		VirtualLocationParameters vl=new VirtualLocationParameters();
		//System.out.println(parameters[0].toString());
		vl.setSubdivision(parameters[0]==null?"":parameters[0].toString());
		vl.setVlID(parameters[1]==null?"":parameters[1].toString());
		vl.setVl_name(parameters[3]==null?"":parameters[3].toString());
		vl.setLocation_type(parameters[2]==null?"":parameters[2].toString());
		vl.setReadTime(parameters[4]==null?null:readTime);
		vl.setKwh((parameters[5]==null? null:Double.parseDouble( parameters[5].toString())));
		vl.setKvah((parameters[6]==null? null:Double.parseDouble(parameters[6].toString())));
		vl.setKw((parameters[7]==null? null:Double.parseDouble(parameters[7].toString())));
		vl.setKva((parameters[8]==null? null:Double.parseDouble(parameters[8].toString())));
		vl.setPf((parameters[9]==null? null:Double.parseDouble(parameters[9].toString())));
		vl.setTimeStamp(timestamp);
		vParameters.add(vl);
	    save(vl);
	    
	    /*if(i==0){
	    	break;
	    }*/
		}
		
		
		/*Session session=postgresMdas.unwrap(Session.class);
		Transaction tx=session.beginTransaction();
		for (VirtualLocationParameters virtualLocationParameters : vParameters) {
			try {
				this.postgresMdas.persist(virtualLocationParameters);
				tx.commit();
				System.out.println("---saved-----");
				
			} catch (RuntimeException re) {
				tx.rollback();
				System.out.println("---Error while saved-----");
				throw re;
			}
		}*/
		
		
		
	}
	@Override
	public List<?> getvirtualLocationNames(){
		
		List<?> list=postgresMdas.createNamedQuery("VirtualLocation.getvlnames").getResultList();
		//System.out.println("sd"+list.size());
		return list;

	}


	
}
