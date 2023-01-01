package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class AmrEventsServiceImpl extends GenericServiceImpl<AmrEventsEntity> implements AmrEventsService
{

	@Override
	public List<AmrEventsEntity> getRecords(String meterno, String fileDate) {
		List<AmrEventsEntity> abb=null;
    try{
		abb= getCustomEntityManager("postgresMdas").createNamedQuery("AmrEventsEntity.getRecords",AmrEventsEntity.class).setParameter("meterno",meterno).setParameter("fileDate",fileDate).getResultList();
		 
       }
       catch (Exception e) 
       {
	e.printStackTrace();
         }	
        return abb;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmrEventsEntity> getEventData(String mtrNo,String frmDate,String tDate,String radioValue) {
		List<AmrEventsEntity> abb=null;
		String sql="";
		
		 try{
				if("meterno".equals(radioValue)) {
		    	/*sql="SELECT meter_number,event_time,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    		+ "meter_data.events WHERE meter_number='"+mtrNo+"' and to_char(event_time,'yyyy-MM-dd') " 
		    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    	sql="SELECT	meter_number,	event_time,event_code, ( case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then \n" +
		    			"concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,	v_r,	v_y,	v_b,	i_r,	i_y,	i_b,	pf_r,	pf_y,	pf_b,	kwh \n" +
		    			"FROM	meter_data.events WHERE	meter_number = '"+mtrNo+"' AND to_char(event_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY 	event_time DESC";
		    	
		    	}
		    	else{
				     sql="SELECT	meter_number,	event_time,event_code, ( case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then \n" +
				    		 "concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,	v_r,	v_y,	v_b,	i_r,	i_y,	i_b,	pf_r,	pf_y,	pf_b,	kwh \n" +
				    		 "FROM	meter_data.events WHERE	meter_number in (SELECT distinct metrno from meter_data.metermaster where kno='"+mtrNo+"') AND to_char(event_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY 	event_time DESC";

		    	}
		    		/*sql="SELECT meter_number,event_time,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    	    		+ "meter_data.events WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"') and to_char(event_time,'yyyy-MM-dd') " 
		    	    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    		/*sql="SELECT meter_number,event_time, (case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then " +
		    			"concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    	    		+ "meter_data.events WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"') and to_char(event_time,'yyyy-MM-dd') " 
		    	    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    	    	//System.out.println("qryyyyy--->"+sql);
		    	    	/*}*/
		    	
		    	
				abb= getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				 
		       }
		       catch (Exception e) 
		       {
			e.printStackTrace();
		         }	
	        return abb;
	}

	@Override
	public List<AmrEventsEntity> getEventDataInfo(String mtrNo) {
		List<AmrEventsEntity> abc=null;
		String sql="";
		
		 try{
				if("meterno" !=null || "meterno"==null) {
		    	/*sql="SELECT meter_number,event_time,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    		+ "meter_data.events WHERE meter_number='"+mtrNo+"' and to_char(event_time,'yyyy-MM-dd') " 
		    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    	sql="SELECT	distinct on(ev.meter_number)ev.meter_number,mm.fdrname,ami.section,max( \r\n" + 
		    			"						                CASE \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (100)) THEN 100 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (250)) THEN 250 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (500)) THEN 500 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (25)) THEN 25 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (40)) THEN 40 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (63)) THEN 63 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (315)) THEN 315 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (300)) THEN 300 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (200)) THEN 200 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (175)) THEN 175 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (160)) THEN 160 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (150)) THEN 150 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (75)) THEN 75 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (50)) THEN 50 \r\n" + 
		    			"						                    WHEN (c.dtcapacity = (16)) THEN 16 \r\n" + 
		    			"						                    WHEN (c.dtcapacity IS NULL) THEN 100 \r\n" + 
		    			"						                    ELSE NULL  \r\n" + 
		    			"						                END) AS dt_capacity,c.dtname \r\n" + 
		    			"		    			FROM	meter_data.events ev  LEFT JOIN meter_data.dtdetails c ON(ev.meter_number=c.meterno) LEFT JOIN meter_data.master_main mm  ON(ev.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code) WHERE	meter_number = '"+mtrNo+"'  GROUP BY ev.meter_number,mm.fdrname,ami.section,c.dtname \r\n" + 
		    			" ";
		    	
		    	}
		    	
		    		/*sql="SELECT meter_number,event_time,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    	    		+ "meter_data.events WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"') and to_char(event_time,'yyyy-MM-dd') " 
		    	    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    		/*sql="SELECT meter_number,event_time, (case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then " +
		    			"concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,event_code,v_r,v_y,v_b,i_r,i_y,i_b,pf_r,pf_y,pf_b,kwh FROM "
		    	    		+ "meter_data.events WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"') and to_char(event_time,'yyyy-MM-dd') " 
		    	    		+ "BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY event_time DESC";*/
		    	    	//System.out.println("qryyyyy--->"+sql);
		    	    	/*}*/
		    	
		    	System.out.println(sql);
				abc= getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				 
		       }
		       catch (Exception e) 
		       {
			e.printStackTrace();
		         }	
	        return abc;
	} 
} 

