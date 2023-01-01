package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class AmrInstantaneousServiceImpl extends GenericServiceImpl<AmrInstantaneousEntity> implements AmrInstantaneousService
{ 
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getModemsStats(ModelMap model, HttpServletRequest request)
	{
		List<?> list=null;
		String sql="";
		try
		{
			/*sql="SELECT count(*) AS TOTAL, " +
				"COUNT(CASE WHEN TO_CHAR(CURRENT_DATE,'YYYY-MM-DD')=TO_CHAR(TIME_STAMP,'YYYY-MM-DD') THEN 1 END) AS WORKING," +
				"COUNT(CASE WHEN age(CURRENT_TIMESTAMP, TIME_STAMP) > '24 hours' THEN 't' END) AS NOT_WORKING " +
				"FROM meter_data.instantaneous";*/
			//list=entityManager.createNativeQuery(sql).getResultList();
			list=getCustomEntityManager("postgresMdas").createNamedQuery("AmrInstantaneousEntity.getModemsStats").getResultList();
			BCITSLogger.logger.info("getModemsStats==>"+list.size());
			
			int totalModems = 0,workingModems = 0,notWorkingModems = 0;
            if (list.size() > 0) 
            {
                int i = 0;
                while (i < list.size()) 
                {
                    Object[] dat = (Object[])list.get(i);
                    int j = 0;
                    while (j < dat.length) 
                    {
	                        if (j == 0) {
	                        	totalModems += Integer.parseInt(dat[j]+"");
	                        }
	                        if (j == 1) {
	                        	workingModems += Integer.parseInt(dat[j]+"");
	                        }
	                        if (j == 2) {
	                        	notWorkingModems += Integer.parseInt(dat[j]+"");
	                        }
	                        ++j;
                    }
	                    ++i;
                }
            }
            //System.out.println("totalModems ==>>"+totalModems+"==>"+workingModems+"==>"+notWorkingModems);
            model.put("totalModems", totalModems);
            model.put( "workingModems",  workingModems);
            model.put( "notWorkingModems",  notWorkingModems);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getTotalModems(ModelMap model, HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="";
		try
		{
			sql="SELECT zone,circle,district,substation,fdrname,fdrtype,meter_number,mf,read_time,time_stamp,\n" +
				//"to_char(last_communication, 'HH12:MI:SS') as last_comm FROM (\n" +
				"cast(last_communication as VARCHAR) as last_communication FROM (\n" +
				"SELECT zone,circle,division,district,subdivision,mla,substation,fdrname,fdrtype,meter_number,mf,read_time,time_stamp,\n" +
				"age(CURRENT_TIMESTAMP, TIME_STAMP) as last_communication FROM meter_data.instantaneous i,meter_data.master m \n" +
				"WHERE i.meter_number=m.mtrno)A order by district,substation asc,last_communication desc";
			
			BCITSLogger.logger.info("getTotalModems==>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			model.put("results", "notDisplay");
			if (list.size() > 0) 
	        {
	        	model.put("showTotalModems", list);
	        }
	        else
	        {
	        	model.put("results", "Data not available.");
	        }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getWorkingsModems(ModelMap model, HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="";
		try
		{
			sql="SELECT zone,circle,district,substation,fdrname,fdrtype,meter_number,mf,read_time,time_stamp," +
				//"to_char(last_communication, 'HH12:MI:SS') as last_comm FROM (\n" +
				"cast(last_communication as VARCHAR) as last_communication FROM (\n" +
				"SELECT zone,circle,division,district,subdivision,mla,substation,fdrname,fdrtype,meter_number," +
				"mf,read_time,time_stamp,age(CURRENT_TIMESTAMP, TIME_STAMP) as last_communication " +
				"FROM meter_data.instantaneous i,meter_data.master m WHERE i.meter_number=m.mtrno AND " +
				"TO_CHAR(CURRENT_DATE,'YYYY-MM-DD') =TO_CHAR(i.TIME_STAMP,'YYYY-MM-DD'))A order by district,substation asc,last_communication desc";
			
			BCITSLogger.logger.info("getTotalModems==>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			model.put("results", "notDisplay");
			if (list.size() > 0) 
	        {
	        	model.put("showWorkingsModems", list);
	        }
	        else
	        {
	        	model.put("results", "Data not available.");
	        }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getNotWorkingsModems(ModelMap model, HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="";
		try
		{
			sql="SELECT zone,circle,district,substation,fdrname,fdrtype,meter_number,mf,read_time,time_stamp," +
				//"to_char(last_communication, 'HH12:MI:SS') as last_comm FROM (\n" +
				"cast(last_communication as VARCHAR) as last_communication FROM (\n" +
				"SELECT zone,circle,division,district,subdivision,mla,substation,fdrname,fdrtype,meter_number," +
				"mf,read_time,time_stamp,age(CURRENT_TIMESTAMP, TIME_STAMP) as last_communication " +
				"FROM meter_data.instantaneous i,meter_data.master m WHERE i.meter_number=m.mtrno AND " +
				"age(CURRENT_TIMESTAMP, TIME_STAMP) >  '24 hours')A order by district,substation asc,last_communication desc";
			
			BCITSLogger.logger.info("getTotalModems==>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			model.put("results", "notDisplay");
			if (list.size() > 0) 
	        {
	        	model.put("showNotWorkingsModems", list);
	        }
	        else
	        {
	        	model.put("results", "Data not available.");
	        }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<AmrInstantaneousEntity> getInstansData(String mtrNo) 
	{
		String sql = "";
		List<AmrInstantaneousEntity> list=null;
		
		try 
		{
			if(mtrNo.length()<10)
			{
			sql="SELECT * FROM ((SELECT cast('R' as text) as Phase,v_r as Voltage_KV,i_r as Current_Amps,pf_r as Power_Factor,read_time  \n" +
					"FROM meter_data.instantaneous WHERE meter_number='"+mtrNo+"')\n" +
					"UNION\n" +
					"(SELECT cast('Y' as text) as Phase,v_y as Voltage_KV,i_y as Current_Amps,pf_y as Power_Factor,read_time  \n" +
					"FROM meter_data.instantaneous WHERE meter_number='"+mtrNo+"')\n" +
					"UNION\n" +
					"(SELECT cast('B' as text) as Phase,v_b as Voltage_KV,i_b as Current_Amps,pf_b as Power_Factor,read_time  \n" +
					"FROM meter_data.instantaneous WHERE meter_number='"+mtrNo+"'))AA WHERE AA.read_time=(SELECT  max(read_time) FROM meter_data.instantaneous WHERE meter_number='"+mtrNo+"')";
			}
			else
			{
				sql="SELECT * FROM ((SELECT cast('R' as text) as Phase,v_r as Voltage_KV,i_r as Current_Amps,pf_r as Power_Factor,read_time  \n" +
						"FROM meter_data.instantaneous WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"'))\n" +
						"UNION\n" +
						"(SELECT cast('Y' as text) as Phase,v_y as Voltage_KV,i_y as Current_Amps,pf_y as Power_Factor,read_time  \n" +
						"FROM meter_data.instantaneous WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"'))\n" +
						"UNION\n" +
						"(SELECT cast('B' as text) as Phase,v_b as Voltage_KV,i_b as Current_Amps,pf_b as Power_Factor,read_time  \n" +
						"FROM meter_data.instantaneous WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"')))AA WHERE AA.read_time=(SELECT  max(read_time) FROM meter_data.instantaneous WHERE meter_number=(select mtrno from meter_data.master_main where accno='"+mtrNo+"'))";	
			}
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
		
	@Override
	public AmrInstantaneousEntity getD1DataForXml(String meterNumber, String fileDate) {
		AmrInstantaneousEntity abb = null;
		try {
			List<AmrInstantaneousEntity> arr = getCustomEntityManager("postgresMdas").createNamedQuery("AmrInstantaneousEntity.getD1", AmrInstantaneousEntity.class).setParameter("meterNumber", meterNumber).setParameter("fileDate",fileDate).getResultList();

			if(arr.size()>0){
				return arr.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("inside service");
		return abb;

	} 
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ModelMap getFdrInstantaneousData(String imei,String mtrNo) {

		List<?> list=null;
		ModelMap model = new ModelMap();
		
		try 
        {
        	
        	if(imei!=null)
        	{
        		//                          0        1       2      3     4       5              6          
        		String query="SELECT m.ct_ratio,m.pt_ratio,i.kwh,i.kva,i.kvah,i.frequency,i.pf_threephase FROM meter_data.master_main AS m,meter_data.instantaneous AS i WHERE m.modem_sl_no='"+imei+"' AND i.meter_number='"+mtrNo+"'";
        		
        		list=getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
        		
        		Object[] val=(Object[]) list.get(0);
        		if(val.length>0)
        		{
        			if(val[0]==null) 
        			{
        				model.put("ct_ratio","NOData");
        			}
        			else
        			{
        				model.put("ct_ratio",val[0]);
        			}
        			if(val[1]==null) 
        			{
        				model.put("pt_ratio","NOData");
        			}
        			else
        			{
        				model.put("pt_ratio",val[1]);
        			}
        			if(val[2]==null) 
        			{
        				model.put("kwh","NOData");
        			}
        			else
        			{
        				model.put("kwh",val[2]);
        			}
        			if(val[3]==null) 
        			{
        				model.put("kva","NOData");
        			}
        			else
        			{
        				model.put("kva",val[3]);
        			}if(val[4]==null) 
        			{
        				model.put("kvah","NOData");
        			}
        			else
        			{
        				model.put("kvah",val[4]);
        			}
        			if(val[5]==null) 
        			{
        				model.put("frequency","NOData");
        			}
        			else
        			{
        				model.put("frequency",val[5]);
        			}
        			if(val[6]==null) 
        			{
        				model.put("pf_threephase","NOData");
        			}
        			else
        			{
        				model.put("pf_threephase",val[6]);
        			}
        			
        		}	       
        	}
        	
        }
    		
             catch (Exception e) 
    		{
    			e.printStackTrace();
    		}
		return model;
	}

	@Override
	public ModelMap getfdrInsGraphData(String imei) {
		
		List<?> list=null;
		ModelMap model = new ModelMap();
		String query="SELECT i.i_r,i.i_y,i.i_b,i.v_r,i.v_y,i.v_b,i.pf_r,i.pf_y,i.pf_b FROM meter_data.instantaneous as i WHERE i.imei='"+imei+"'";
		
		list=getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		
		Object[] val=(Object[]) list.get(0);
		if(val.length>0)
		{
			if(val[0]==null) 
			{
				model.put("i_r","NOData");
			}
			else
			{
				model.put("i_r",val[0]);
			}
			if(val[1]==null) 
			{
				model.put("i_y","NOData");
			}
			else
			{
				model.put("i_y",val[1]);
			}
			if(val[2]==null) 
			{
				model.put("i_b","NOData");
			}
			else
			{
				model.put("i_b",val[2]);
			}
			if(val[3]==null) 
			{
				model.put("v_r","NOData");
			}
			else
			{
				model.put("v_r",val[3]);
			}
			if(val[4]==null) 
			{
				model.put("v_y","NOData");
			}
			else
			{
				model.put("v_y",val[4]);
			}
			if(val[5]==null) 
			{
				model.put("v_b","NOData");
			}
			else
			{
				model.put("v_b",val[5]);
			}
			if(val[6]==null) 
			{
				model.put("pf_r","NOData");
			}
			else
			{
				model.put("pf_r",val[6]);
			}
			if(val[7]==null) 
			{
				model.put("pf_y","NOData");
			}
			else
			{
				model.put("pf_y",val[7]);
			}
			if(val[8]==null) 
			{
				model.put("pf_b","NOData");
			}
			else
			{
				model.put("pf_b",val[8]);
			}
			
			
		}
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmrInstantaneousEntity> getCompleteInstansData(String mtrNo,String radioValue) {
		List<AmrInstantaneousEntity> list = null;
		try {
			if("meterno".equals(radioValue)) {
			String sql="SELECT * FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"'"
					+ " AND read_time=(SELECT  max(read_time) FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"')";
			 list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			}
			else{
				String sql1="select * from meter_data.amiinstantaneous WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') and read_time=(SELECT  max(read_time) FROM meter_data.amiinstantaneous WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"'))";
				 list = getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();

			}
			// System.out.println("sql instantaneous query---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AmrInstantaneousEntity> getCompleteInstansDataNew(String mtrNo,String frmDate,String tDate,String radioValue) {
		List<AmrInstantaneousEntity> list = null;
		try {
			
			if("meterno".equals(radioValue)) {
				
				
				
				String sql="SELECT am.*,mm.fdrname,ami.section, max(\r\n" + 
						"                CASE\r\n" + 
						"                    WHEN (c.dtcapacity = (100)) THEN 100\r\n" + 
						"                    WHEN (c.dtcapacity = (250)) THEN 250\r\n" + 
						"                    WHEN (c.dtcapacity = (500)) THEN 500\r\n" + 
						"                    WHEN (c.dtcapacity = (25)) THEN 25\r\n" + 
						"                    WHEN (c.dtcapacity = (40)) THEN 40\r\n" + 
						"                    WHEN (c.dtcapacity = (63)) THEN 63\r\n" + 
						"                    WHEN (c.dtcapacity = (315)) THEN 315\r\n" + 
						"                    WHEN (c.dtcapacity = (300)) THEN 300\r\n" + 
						"                    WHEN (c.dtcapacity = (200)) THEN 200\r\n" + 
						"                    WHEN (c.dtcapacity = (175)) THEN 175\r\n" + 
						"                    WHEN (c.dtcapacity = (160)) THEN 160\r\n" + 
						"                    WHEN (c.dtcapacity = (150)) THEN 150\r\n" + 
						"                    WHEN (c.dtcapacity = (75)) THEN 75\r\n" + 
						"                    WHEN (c.dtcapacity = (50)) THEN 50\r\n" + 
						"                    WHEN (c.dtcapacity = (16)) THEN 16\r\n" + 
						"                    WHEN (c.dtcapacity IS NULL) THEN 100\r\n" + 
						"                    ELSE NULL \r\n" + 
						"                END) AS dt_capacity,c.dtname FROM meter_data.amiinstantaneous am LEFT JOIN meter_data.master_main mm ON (am.meter_number=mm.mtrno)LEFT JOIN meter_data.dtdetails c ON (am.meter_number=c.meterno)\r\n" + 
						" LEFT JOIN meter_data.amilocation ami ON(mm.town_code=ami.tp_towncode) WHERE meter_number='"+mtrNo+"' AND read_time=(SELECT  max(read_time) FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"') GROUP BY am.id,mm.fdrname,ami.section,c.dtname";
				
			 list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			 System.out.println(sql);
			}
			
			else{
				String sql="select am.*,mm.fdrname,ami.section from meter_data.amiinstantaneous am LEFT JOIN meter_data.master_main mm ON (am.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(mm.town_code=ami.tp_towncode) WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') and read_time=(SELECT  max(read_time) FROM meter_data.amiinstantaneous WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"')";
				System.out.println(sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AmrInstantaneousEntity> getAllInstansData(String mtrNo,String frmDate,String tDate, String radioValue) {
		List<AmrInstantaneousEntity> list = null;
		String sql=null;
		try {
			if("meterno".equals(radioValue)) {
			 sql="SELECT * FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' AND read_time<(SELECT  max(read_time)"
					+ " FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"')"
					+ "AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY read_time DESC";
			}
			else{

				sql="SELECT * FROM meter_data.amiinstantaneous WHERE meter_number in"
						+ " (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') "
						+ "AND read_time<(SELECT  max(read_time) FROM meter_data.amiinstantaneous "
						+ "WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"') "
						+ " AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY read_time DESC;";
			}
			 list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			 System.out.println("sql instantaneous query---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AMIInstantaneousEntity> meterByDate(String meterNo,String billMonth, ModelMap model)
	{
		return getCustomEntityManager("postgresMdas").createNamedQuery("AMIInstantaneousEntity.getDataByreadDate").setParameter("meterNo", meterNo).setParameter("readdate", billMonth).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmrInstantaneousEntity> getMeterData(String mtrNo) {
		List<AmrInstantaneousEntity> list = null;
		try {
			String sql="select i_r,i_y,i_b,v_r,v_y,v_b,pf_r,pf_y,pf_b,frequency,kwh,kvah,kvarh_lag,kwh_exp,kvah_exp,kvarh_lead,kva,kvar,power_off_count,power_off_duration,tamper_count,i_r_angle,i_y_angle,i_b_angle,v_ry_angle,v_rb_angle,v_yb_angle,read_time,meter_number from meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' ORDER BY read_time ASC LIMIT 1  ";
			System.out.println(sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
