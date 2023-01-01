package com.bcits.mdas.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.AmiLocation;
import com.bcits.mdas.service.AndroidService;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.google.gson.Gson;
@Repository
public class AndroidServiceImpl extends GenericServiceImpl<AmiLocation> implements AndroidService   {
	 @Override 
	public  String insert_new( String UserName,  String ConsumerName,  String Password, String Emailid, String MobileNo ,  String otp,  String ebillReg, String que1, String ans1, String que2, String ans2)
	     {
	        System.out.println("registerNewMobUser ===> "+ConsumerName);

	        String fullname=ConsumerName;
	        String username=UserName;
	        String email=Emailid;
	        String phone=MobileNo;

	        int result = 0;

	        String regHashPasswordResult = Password;

	        try {

	            System.out.println("came");

	                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	                Date date = new Date();
	                System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

	                String current_date = dateFormat.format(date);

	                String sql3 = "insert into meter_data.ncpt_customers (customer_name,customer_email_id,customer_login_name,password,customer_contact_no,created_dt,otp,ebill_registration,useractive,status,qestion_id_one,answer_one,qestion_id_two,answer_two,source) values"
	                        + "('" + fullname + "','" + email + "','" + username + "','" + regHashPasswordResult + "','" + phone + "','" +current_date+"','"+otp+"','"+ebillReg+"','active','0','"+que1+"','"+ans1+"','"+que2+"','"+ans1+"','MOB')";
	                System.out.println(sql3);
	                result = postgresMdas.createNativeQuery(sql3).executeUpdate();

	                System.out.println("==========result==========" + result);


	        } catch (Exception e) {
	            System.out.println("In connection class  Exception insert???????????????????????????");
	            e.printStackTrace();
	        }
	        if (result==1) {
	            return "inserted";
	        } else {
	            return "not inserted";
	        }
	    }
	 @Override
	 public int ncpt_rrno_insertion(String s) {
		 return postgresMdas.createNativeQuery(s).executeUpdate();
	 }

	 
	 public List<Object[]> getConsumerConumptionData(String LocationType, String LocationName, String Billmonth){
	
		 
		 
		 
		String qry = null;
		List<Object[]> list = null;
				if(LocationType.equalsIgnoreCase("discom")){
					
					
					qry = "select distinct a.rt,round(sum(a.consumption),2) as consumption  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM "
							+ "(SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B\n" +
							"GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E\n" +
							"INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno\n" +
							" WHERE E.zone='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='"+Billmonth+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
							
				/*	qry = " SELECT  ROUND(SUM(consumption)) as consumption,meter_number FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='201901' 	"
							+ " GROUP BY B.metrno,B.circle,B.division,B.subdiv)E  		"
							+ " INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,  	"
							+ " A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno  "
							+ " WHERE 	E.circle='JCC' AND to_char(D.read_time, 'YYYYMM')='201901' GROUP BY meter_number--ORDER BY D.read_time ASC  "   ;					
					*/
				}else if (LocationType.equalsIgnoreCase("zone")){
					

					qry = "select distinct a.rt,round(sum(a.consumption),2) as consumption  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM "
							+ "(SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B\n" +
							"GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E\n" +
							"INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno\n" +
							" WHERE E.zone='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='"+Billmonth+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
							
					
					/*qry = " SELECT  ROUND(SUM(consumption)) as consumption,meter_number FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='201901' 	"
							+ " GROUP BY B.metrno,B.circle,B.division,B.subdiv)E  		"
							+ " INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,  	"
							+ " A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno  "
							+ " WHERE 	E.circle='JCC' AND to_char(D.read_time, 'YYYYMM')='201901' GROUP BY meter_number--ORDER BY D.read_time ASC  "   ;					
					*/
				}else if(LocationType.equalsIgnoreCase("circle")){
					
					qry = "select distinct a.rt,round(sum(a.consumption),2) as consumption  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM "
							+ "(SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B\n" +
							"GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E\n" +
							"INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno\n" +
							" WHERE E.circle='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='"+Billmonth+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
				
					/*qry = " SELECT  ROUND(SUM(consumption)) as consumption,meter_number FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='201901' 	"
							+ " GROUP BY B.metrno,B.circle,B.division,B.subdiv)E  		"
							+ " INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,  	"
							+ " A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno  "
							+ " WHERE 	E.circle='"+LocationName+"' AND to_char(D.read_time, 'YYYYMM')='201901' GROUP BY meter_number--ORDER BY D.read_time ASC  "   ;					
					*/
					
				}else if (LocationType.equalsIgnoreCase("division")){
					
					qry = "select distinct a.rt,round(sum(a.consumption),2) as consumption  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM "
							+ "(SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B\n" +
							"GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E\n" +
							"INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno\n" +
							" WHERE E.division='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='"+Billmonth+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
				
					
					/*qry = " SELECT  ROUND(SUM(consumption)) as consumption,meter_number FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='201901' 	"
							+ " GROUP BY B.metrno,B.circle,B.division,B.subdiv)E  		"
							+ " INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,  	"
							+ " A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno  "
							+ " WHERE 	 E.division='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='201901' GROUP BY meter_number--ORDER BY D.read_time ASC  "   ;					
					*/
				}else{
					

					qry = "select distinct a.rt,round(sum(a.consumption),2) as consumption  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM "
							+ "(SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B\n" +
							"GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E\n" +
							"INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno\n" +
							" WHERE E.subdivision='"+LocationName+"'  AND to_char(D.read_time, 'YYYYMM')='"+Billmonth+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
				
					
					
					/*qry = " SELECT  ROUND(SUM(consumption)) as consumption,meter_number FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='201901' 	"
							+ " GROUP BY B.metrno,B.circle,B.division,B.subdiv)E  		"
							+ " INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,  	"
							+ " A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno  "
							+ " WHERE  E.subdiv='"+LocationName+"' AND to_char(D.read_time, 'YYYYMM')='201901' GROUP BY meter_number--ORDER BY D.read_time ASC  "   ;					
					*/
				}
		 try {
			  list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	 }
	 
	 
	 public List<Object[]> getDailyConumptionDataServices(String LocationCode ,String kno , String billmonth){
			
		 
			String qry = "select date, round(COALESCE(kwh_imp,0),2) as kwh_imp,kno,mtrno from meter_data.daily_consumption where location_code = '"+LocationCode+"' and"
					+ " kno = '"+kno+"' and TO_CHAR(date ,'YYYYMM') = '"+billmonth+"'  ORDER BY date ";
			List<Object[]> list = null;
					System.out.println(qry);
			 try {
				  list= postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		 }
	 @Override
	// @Transactional(propagation= Propagation.REQUIRED)
	public List<Object[]> getPowerAvailability(String kno) {
		//Gson g=new Gson();
		List<Object[]> list=null;
		 try {
		String qry ="select b.meter_number,b.month,b.total_hours/60 || ' Hrs' as total_hours,trunc((b.total_hours-b.total_power_on)/60)||' Hrs '||to_number((to_char((b.total_hours-b.total_power_on),'9999999')),'999999')%60||' Mins'  as power_off,trunc(b.total_power_on/60) || ' Hrs '||b.total_power_on%60||' Mins' as power_on from (select a.meter_number,a.month, case when a.total_hours is null then  \n" +
				"(SELECT date_part('day', \n" +
				"          to_date((date_part('year', to_date(a.month,'MMYYYY')) || '-' ||\n" +
				"         date_part('month', to_date(a.month,'MMYYYY')) || '-01'),'YYYY-MM-DD')  \n" +
				"                + interval '1 month'\n" +
				"                 -interval '1 day') AS days)*24*60\n" +
				"else a.total_hours end , case when a.total_power_on is null then ((select min(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'MMYYYY')=to_char(to_timestamp(a.month,'MMYYYY')+ interval '1 month','MMYYYY'))-\n" +
				"(select min(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'MMYYYY')=a.month)) else a.total_power_on end  from (select meter_number,to_char(read_time- interval '1 month','MMYYYY') as month,(to_date(to_char(rdate,'YYYY-MM-DD'),'YYYY-MM-DD') - to_date(to_char(lag(rdate) OVER (ORDER BY read_time),'YYYY-MM-DD'),'YYYY-MM-DD'))*24*60 as total_hours,(total_power_on_duration-(lag(total_power_on_duration) OVER (ORDER BY read_time))) as total_power_on from meter_data.amiinstantaneous where to_char(rdate,'DD HH24:MI')='01 00:00' and meter_number in (select mtrno from meter_data.master_main where kno='"+kno+"') ORDER BY rdate desc) a)b";
			 
		
		list= postgresMdas.createNativeQuery(qry).getResultList();
			 //list=g.toJson(l);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	@Transactional
	public List<Object[]> getPowerOffEvent(String billmonth, String kno) {
		//Gson g=new Gson();
		List<Object[]> l=null;
		 try {
		String qry ="select a.meter_number,to_char(occu,'YYYY-MM-DD') as Occ_date,to_char(occu,'YYYY-MM-DD HH24:MI:SS') as power_off_start,to_char(event_time,'YYYY-MM-DD HH24:MI:SS') as power_off_End,"
				+ "to_char(event_time-occu,'DD HH24:MI:SS') as duration FROM (select meter_number,event_time,case when to_number(event_code,'999')-(lead(to_number(event_code,'999'),1) over (ORDER BY event_time||flag desc)) =1 then 1 else 0 end "
				+ "as diff ,lead(event_time,1) over (ORDER BY event_time||flag desc) as occu,event_code from meter_data.events where event_code in ('101','102') and meter_number in ( select mtrno from meter_data.master_main where kno='"+kno+"') "
				+ "and to_char(event_time,'MMYYYY')='"+billmonth+"'" +
				" ORDER BY event_time||flag desc) a where a.diff=1";
		
		 l= postgresMdas.createNativeQuery(qry).getResultList();
		
			 //list=g.toJson(l);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	@Override
	public List<Object[]> voltageRegulation(String billmonth, String kno) {
		List<Object[]> l=null;
		 try {
		String qry ="SELECT * FROM\n" +
				"(select phase,fdrcategory,mtrno,voltage_kv,accno from meter_data.master_main where phase='1' AND phase IS NOT NULL  and fdrcategory='LT' and kno='"+kno+"')A,\n" +
				"(SELECT month_year,meter_number,COALESCE(ver_less_6*0.5,0) as less_6, COALESCE(ver_0_6*0.5,0) as btw_6_0,COALESCE( ver_0_5*0.5,0) as btw_0_5, COALESCE(ver_great_5*0.5,0) as great_5 from\n" +
				"(\n" +
				"SELECT a.month_year, a.meter_number,\n" +
				"sum(CASE WHEN per<0 AND per>=-6 THEN CAST(average_voltage as NUMERIC) END) as ver_0_6,\n" +
				"sum(CASE WHEN per>=0 AND per<=5 THEN CAST(average_voltage as NUMERIC) END) as ver_0_5,\n" +
				"sum(CASE WHEN per<-6 THEN CAST(average_voltage as NUMERIC) END) as ver_less_6,\n" +
				"sum(CASE WHEN per>5 THEN CAST(average_voltage as NUMERIC) END) as ver_great_5\n" +
				"FROM\n" +
				"(\n" +
				"SELECT read_time, average_voltage,meter_number,\n" +
				"round(((CAST(average_voltage as NUMERIC)-CAST(COALESCE(m.voltage_kv,'0') as NUMERIC))/\n" +
				"(CASE WHEN CAST(COALESCE(m.voltage_kv,'0') as NUMERIC)=0 THEN 1 ELSE CAST(COALESCE(m.voltage_kv,'0') as NUMERIC) END))*100, 2) as per,to_char(read_time,'YYYYMM') as month_year\n" +
				"from meter_data.load_survey l,meter_data.master_main m WHERE l.meter_number=m.mtrno and\n" +
				"TO_CHAR(read_time, 'YYYYMM') ='"+billmonth+"' ORDER BY read_time ASC\n" +
				") a GROUP BY a.month_year,a.meter_number\n" +
				")c)b WHERE a.mtrno=b.meter_number\n" +
				"UNION ALL\n" +
				"SELECT * FROM\n" +
				"(select phase,fdrcategory,mtrno,voltage_kv,accno  from meter_data.master_main WHERE fdrcategory='LT' and phase='3' and kno='"+kno+"' )A,\n" +
				"(SELECT month_year,meter_number,COALESCE(ver_less_6*0.5,0) as less_6,COALESCE(ver_0_6*0.5,0) as btw_6_0,COALESCE( ver_0_5*0.5,0) as btw_0_5, COALESCE(ver_great_5*0.5,0) as great_5 from\n" +
				"(\n" +
				"SELECT a.month_year, a.meter_number,\n" +
				"sum(CASE WHEN per<0 AND per>=-6 THEN CAST(average_voltage as NUMERIC) END) as ver_0_6,\n" +
				"sum(CASE WHEN per>=0 AND per<=5 THEN CAST(average_voltage as NUMERIC) END) as ver_0_5,\n" +
				"sum(CASE WHEN per<-6 THEN CAST(average_voltage as NUMERIC) END) as ver_less_6,\n" +
				"sum(CASE WHEN per>5 THEN CAST(average_voltage as NUMERIC) END) as ver_great_5\n" +
				"FROM\n" +
				"(\n" +
				"SELECT read_time,meter_number,(CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC)) as average_voltage,\n" +
				"round((((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))-CAST(COALESCE(m.voltage_kv,'0') as NUMERIC))/\n" +
				"(CASE WHEN CAST(COALESCE(m.voltage_kv,'0') as NUMERIC)=0 THEN 1 ELSE CAST(COALESCE(m.voltage_kv,'0') as NUMERIC) END))*100, 2) as per,to_char(read_time,'YYYYMM') as month_year\n" +
				"from meter_data.load_survey l,meter_data.master_main m WHERE l.meter_number=m.mtrno and \n" +
				"TO_CHAR(read_time, 'YYYYMM') ='"+billmonth+"' ORDER BY read_time ASC\n" +
				") a GROUP BY a.month_year,a.meter_number\n" +
				")c)b\n" +
				"WHERE a.mtrno=b.meter_number";
		
		 l= postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println(qry);
		System.out.println(l);
			 //list=g.toJson(l);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	 
}
