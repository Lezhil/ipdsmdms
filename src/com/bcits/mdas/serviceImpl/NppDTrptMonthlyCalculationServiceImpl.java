package com.bcits.mdas.serviceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.NppDTrptMonthlyCalculation;
import com.bcits.mdas.service.NppDTrptMonthlyCalculationService;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.ibm.icu.math.BigDecimal;

@Repository
public class NppDTrptMonthlyCalculationServiceImpl extends GenericServiceImpl<NppDTrptMonthlyCalculation>
		implements NppDTrptMonthlyCalculationService {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Override
	public List<Object[]> getNppIntermediateDtData(String monthYear) {

		List<Object[]> result = null;
		String msg = null;

		String qry = "Select X.*,now(),string_agg(d.meterno,',' ORDER BY meterno) as meterno ,string_agg(d.dt_id,',' ORDER BY dt_id) as dt_id ,string_agg(d.dtname,',' ORDER BY dtname) as dtname,d.officeid    from meter_data.dtdetails d,\n"
				+ "(Select * from\n" + "(SELECT f.fdr_id,feedername, a.* FROM meter_data.feederdetails f, \n" + "(\n"
				+ "SELECT\n" + "study_year||\n"
				+ "(CASE WHEN \"length\"(CAST(study_month as TEXT))=1 THEN '0'||study_month else ''||study_month END) as monthyear,\n"
				+ "tp_fdr_id,\n" + "towncode,\n" + "tpdtid,\n"
				+ "sum(COALESCE(power_fail_freq,0)) as power_fail_freq,\n"
				+ "sum(COALESCE(power_fail_duration,0)) as power_fail_duration,\n"
				+ "sum(COALESCE(minimum_voltage,0)) as minimum_voltage,\n"
				+ "sum(COALESCE(max_current,0)) as max_current,\n" + "sum(COALESCE(input_energy,0)) as input_energy,\n"
				+ "sum(COALESCE(export_energy,0)) as export_energy,\n"
				+ "sum(COALESCE(ht_industrial_consumer_count,0)) as ht_industrial_consumer_count,\n"
				+ "sum(COALESCE(ht_commercial_consumer_count,0)) as ht_commercial_consumer_count,\n"
				+ "sum(COALESCE(lt_industrial_consumer_count,0)) as lt_industrial_consumer_count,\n"
				+ "sum(COALESCE(lt_commercial_consumer_count,0)) as lt_commercial_consumer_count,\n"
				+ "sum(COALESCE(lt_domestic_consumer_count,0)) as lt_domestic_consumer_count,\n"
				+ "sum(COALESCE(govt_consumer_count,0)) as govt_consumer_count,\n"
				+ "sum(COALESCE(agri_consumer_count,0)) as agri_consumer_count,\n"
				+ "sum(COALESCE(others_consumer_count,0)) as others_consumer_count,\n"
				+ "sum(COALESCE(ht_industrial_energy_billed,0)) as ht_industrial_energy_billed,\n"
				+ "sum(COALESCE(ht_commercial_energy_billed,0)) as ht_commercial_energy_billed,\n"
				+ "sum(COALESCE(lt_industrial_energy_billed,0)) as lt_industrial_energy_billed,\n"
				+ "sum(COALESCE(lt_commercial_energy_billed,0)) as lt_commercial_energy_billed,\n"
				+ "sum(COALESCE(lt_domestic_energy_billed,0)) as lt_domestic_energy_billed,\n"
				+ "sum(COALESCE(govt_energy_billed,0)) as govt_energy_billed,\n"
				+ "sum(COALESCE(agri_energy_billed,0)) as agri_energy_billed,\n"
				+ "sum(COALESCE(others_energy_billed,0)) as others_energy_billed,\n"
				+ "sum(COALESCE(ht_industrial_amount_billed,0)) as ht_industrial_amount_billed,\n"
				+ "sum(COALESCE(ht_commercial_amount_billed,0)) as ht_commercial_amount_billed,\n"
				+ "sum(COALESCE(lt_industrial_amount_billed,0)) as lt_industrial_amount_billed,\n"
				+ "sum(COALESCE(lt_commercial_amount_billed,0)) as lt_commercial_amount_billed,\n"
				+ "sum(COALESCE(lt_domestic_amount_billed,0)) as lt_domestic_amount_billed,\n"
				+ "sum(COALESCE(govt_amount_billed,0)) as govt_amount_billed,\n"
				+ "sum(COALESCE(agri_amount_billed,0)) as agri_amount_billed,\n"
				+ "sum(COALESCE(others_amount_billed,0)) as others_amount_billed,\n"
				+ "sum(COALESCE(ht_industrial_amount_collected,0)) as ht_industrial_amount_collected,\n"
				+ "sum(COALESCE(ht_commercial_amount_collected,0)) as ht_commercial_amount_collected,\n"
				+ "sum(COALESCE(lt_industrial_amount_collected,0)) as lt_industrial_amount_collected,\n"
				+ "sum(COALESCE(lt_commercial_amount_collected,0)) as lt_commercial_amount_collected,\n"
				+ "sum(COALESCE(lt_domestic_amount_collected,0)) as lt_domestic_amount_collected,\n"
				+ "sum(COALESCE(govt_amount_collected,0)) as govt_amount_collected,\n"
				+ "sum(COALESCE(agri_amount_collected,0)) as agri_amount_collected,\n"
				+ "sum(COALESCE(others_amount_collected,0)) as others_amount_collected,\n"
				+ "sum(COALESCE(open_access_units,0)) as open_access_units,\n"
				+ "sum(COALESCE(hut_consumer_count,0)) as hut_consumer_count,\n"
				+ "sum(COALESCE(hut_energy_billed,0)) as hut_energy_billed,\n"
				+ "sum(COALESCE(hut_amount_billed,0)) as hut_amount_billed,\n"
				+ "sum(COALESCE(hut_amount_collected,0)) as hut_amount_collected\n"
				+ "FROM meter_data.npp_dt_rpt_intermediate  GROUP BY tp_fdr_id,study_month,study_year,towncode,tpdtid	\n"
				+ ")a WHERE f.tp_fdr_id=a.tp_fdr_id AND f.tp_town_code=a.towncode  and f.boundry_feeder=FALSE)C)X\n"
				+ "where X.tpdtid=d.dttpid GROUP BY dttpid,fdr_id,monthyear,\n"
				+ "tp_fdr_id,towncode,tpdtid,power_fail_freq,power_fail_duration,minimum_voltage,max_current,input_energy,export_energy,ht_industrial_consumer_count,ht_commercial_consumer_count,lt_industrial_consumer_count,lt_commercial_consumer_count,\n"
				+ "lt_domestic_consumer_count,govt_consumer_count,agri_consumer_count,others_consumer_count,ht_industrial_energy_billed,\n"
				+ "ht_commercial_energy_billed,lt_industrial_energy_billed,lt_commercial_energy_billed,lt_domestic_energy_billed,govt_energy_billed,\n"
				+ "agri_energy_billed,others_energy_billed,ht_industrial_amount_billed,ht_commercial_amount_billed,lt_industrial_amount_billed,\n"
				+ "lt_commercial_amount_billed,lt_domestic_amount_billed,govt_amount_billed,agri_amount_billed,others_amount_billed,\n"
				+ "ht_industrial_amount_collected,ht_commercial_amount_collected,lt_industrial_amount_collected,lt_commercial_amount_collected,\n"
				+ "lt_domestic_amount_collected,govt_amount_collected,agri_amount_collected,others_amount_collected,hut_consumer_count,hut_energy_billed, hut_amount_billed, hut_amount_collected,open_access_units,officeid,feedername order by tp_fdr_id";

		try {
			result = entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getMonthlyConsumptionDetails(String monthYear) {

		BigDecimal check = null;
		String msg = null;

		try {

			String query = "select distinct billmonth from meter_data.monthly_consumption where billmonth = '"
					+ monthYear + "'";

			System.out.println(query);

			check = (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
			System.out.println(check);

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

		if (check == null) {

			
			  String query =
			  "insert into meter_data.monthly_consumption (billmonth,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time,min_current,\r\n"
			  + "max_current,\r\n" + "min_voltage,\r\n" + "max_voltage\r\n" + ")\r\n" +
			  "\r\n" + "\r\n" +
			  "select distinct on (B.meter_number) B.month, B.meter_number,(B.kwhimp*B.mf) AS kwh_imp,(B.kwhexp*B.mf) AS kwh_exp,\r\n"
			  +
			  "				(B.kvahimp*B.mf) AS kvah_imp,(B.kvahexp*B.mf) AS kvah_exp,B.time_stamp,round(B.mincurrent,2)mincurrent,round(B.maxcurrent*B.mf,2)as maxcurrent,round(B.minvolt,2)as minvolt,round(B.maxvolt,2)as maxvolt\r\n"
			  + "				FROM\r\n" +
			  "			(select A.month,A.meter_number,A.kwhimp,A.kwhexp,A.kvahimp,A.kvahexp,\r\n"
			  +
			  "			m.mtrno,COALESCE(cast(m.mf AS NUMERIC),1) AS mf,A.time_stamp,A.mincurrent,A.maxcurrent,A.minvolt,A.maxvolt from\r\n"
			  + "			(\r\n" +
			  "			select to_number((to_char((read_time-INTERVAL '0 MONTH'),'yyyyMM')),'999999') as month,\r\n"
			  + "			meter_number,\r\n" +
			  "			sum(kwh) AS kwhimp ,sum(kwh_exp) AS kwhexp,sum(kvah_exp) AS kvahexp,sum(kvah) AS kvahimp,CURRENT_TIMESTAMP as time_stamp,min(i_r+i_y+i_b)/3 as mincurrent,\r\n"
			  +
			  "	max (i_r+i_y+i_b)/3 as maxcurrent,min (v_r+v_y+v_b)/3 as minvolt,max(v_r+v_y+v_b)/3 as maxvolt \r\n"
			  +
			  "			from meter_data.load_survey l WHERE  to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')\r\n"
			  + "			GROUP BY month,meter_number)A,\r\n" +
			  "			meter_data.master_main m\r\n" +
			  "			WHERE m.mtrno=A.meter_number AND\r\n" +
			  "			m.mtrno NOT IN(select distinct mtrno from meter_data.monthly_consumption WHERE to_char(billmonth,'yyyyMM') = to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')))B;\r\n"
			  + "			";
			 
			
			System.out.println(query);
			int j = entityManager.createNativeQuery(query).executeUpdate(); 
			System.out.println("Data inserted successfully.");
		} else {

			System.out.println("inside else");
			msg = "synched";

		}
		return msg;

	}
	
	@Override
	public String getDailyConsumptionDetails(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
	/*	try {
			
			
			//mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
//			String query = "select distinct rtc_date_time from meter_data.daily_load where date(rtc_date_time)=(date_trunc('month', '"+fromDate+"'::date))::date '";
					
			String query="select distinct billmonth,mtrno,kwh_imp from  meter_data.input_energy where  billmonth ='"+monthYear+"' and kwh_imp is not null  GROUP BY  mtrno,billmonth,kwh_imp";
			
			
			
			System.out.println(query);
				
			
			check = (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
			System.out.println(check);

		} catch (NoResultException nre) {
			//nre.printStackTrace();
		}*/
		
		try {
		
		
			
				String query ="\r\n" + 
						"						INSERT INTO meter_data.input_energy (billmonth,mtrno,time_stamp,kwh_imp,fdrcategory)  						   \r\n" + 
						"						   \r\n" + 
						"						(select distinct z.billmonth,z.mtrno,now() as time_stamp,round((z.kwh_imp*COALESCE(p.mf,1)),1) as kwh_imp,z.fdrcategory from  \r\n" + 
						"						(select DISTINCT  x.billmonth,x.mtrno,sum(y.cum_active_import_energy-x.cum_active_import_energy) as kwh_imp,x.fdrcategory,x.town_code  from   \r\n" + 
						"						(select DISTINCT dl.mtrno,dl.cum_active_import_energy,cast(to_char(dl.rtc_date_time,'YYYYMM') as VARCHAR)as billmonth,mm.fdrcategory,mm.town_code from meter_data.daily_load dl LEFT JOIN meter_data.master_main mm ON(dl.mtrno=mm.mtrno)  where  to_char(dl.rtc_date_time,'YYYY-MM-DD')='"+fromDate+"')x LEFT JOIN  \r\n" + 
						"						  \r\n" + 
						"						(select DISTINCT  dl.mtrno,dl.cum_active_import_energy,cast(to_char(dl.rtc_date_time,'YYYYMM') as VARCHAR) as billmonth,mm.town_code from meter_data.daily_load dl LEFT JOIN meter_data.master_main mm ON(dl.mtrno=mm.mtrno) where to_char(dl.rtc_date_time,'YYYY-MM-DD')='"+todate+"')y ON(x.mtrno=y.mtrno)  where  x.fdrcategory  IN('FEEDER METER','DT','BOUNDARY METER') GROUP BY x.mtrno,x.billmonth,x.cum_active_import_energy,y.cum_active_import_energy,x.fdrcategory,x.town_code)z LEFT JOIN  \r\n" + 
						"						  \r\n" + 
						"						(select meterno,mf from meter_data.feederdetails)p  ON(z.mtrno=p.meterno) where z.kwh_imp is not null  and z.mtrno NOT IN(select mtrno from meter_data.input_energy where billmonth='"+monthYear+"') GROUP BY z.mtrno,z.billmonth,z.fdrcategory,z.fdrcategory,p.mf,z.kwh_imp);\r\n" + 
						"						\r\n" + 
						"						";
				System.out.println(query);
				
				int j = entityManager.createNativeQuery(query).executeUpdate();
				System.out.println("Data daily inserted successfully.");
		
		
		
		} 
		
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		
		
		
		  return msg;
		  
		  }
		 
		
	@Override
	public String getBillConsumptionDetails(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			
			String query="\r\n" + 
					" INSERT INTO meter_data.input_energy(billmonth,mtrno,time_stamp,kwh_imp,fdrcategory)\r\n" + 
					" \r\n" + 
					" \r\n" + 
					" \r\n" + 
					"(select m.billmonth,m.meter_number,now() as time_stamp,round((m.kwh_imp*COALESCE(p.mf,1)),1) as kwh_imp,m.fdrcategory from \r\n" + 
					"(select distinct x.meter_number,x.billing_date as billmonth,round(sum(y.kwh-x.kwh),1) as kwh_imp,CURRENT_TIMESTAMP as time_stamp,x.fdrcategory  from \r\n" + 
					"\r\n" + 
					"(select bh.meter_number,bh.kwh,cast(to_char(bh.billing_date,'YYYYMM') as VARCHAR) as billing_date,mm.town_code,mm.fdrcategory from meter_data.bill_history bh LEFT JOIN meter_data.master_main mm ON(bh.meter_number=mm.mtrno)  where to_char(bh.billing_date,'YYYY-MM-DD')= '"+fromDate+"' and bh.kwh is not null)x LEFT JOIN\r\n" + 
					"\r\n" + 
					"(select bh.meter_number,bh.kwh,cast(to_char(bh.billing_date,'YYYYMM') as VARCHAR) as billing_date,mm.town_code,mm.fdrcategory from meter_data.bill_history bh LEFT JOIN meter_data.master_main mm ON(bh.meter_number=mm.mtrno)   where to_char(bh.billing_date,'YYYY-MM-DD')= '"+todate+"' and bh.kwh is not null)y ON(x.meter_number=y.meter_number)  and x.fdrcategory IN('FEEDER METER','DT','BOUNDARY METER') \r\n" + 
					" GROUP BY x.meter_number,x.billing_date,x.fdrcategory)m  LEFT JOIN\r\n" + 
					"\r\n" + 
					"(select meterno,mf from meter_data.feederdetails)p ON(m.meter_number=p.meterno)\r\n" + 
					"where m.kwh_imp is not null and m.meter_number NOT IN (select mtrno from meter_data.input_energy where billmonth='"+monthYear+"') GROUP BY m.meter_number,m.billmonth,m.time_stamp,m.fdrcategory,p.mf,m.kwh_imp);";
			
		
			
			System.out.println("Bill"+query);

			int j = entityManager.createNativeQuery(query).executeUpdate();
			
			System.out.println("Data Bill inserted successfully.");
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
		 
	@Override
	public String getMonthinpConsumptionDetails(String monthYear) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			
			
			
			String qry="INSERT INTO meter_data.input_energy(billmonth,mtrno,kwh_imp,time_stamp,fdrcategory)\r\n" + 
					"select mc.billmonth,mc.mtrno,round(mc.kwh_imp,1) as kwh_imp,now() as time_stamp,mm.fdrcategory from meter_data.monthly_consumption mc LEFT JOIN meter_data.master_main mm ON(mc.mtrno=mm.mtrno) where mc.kwh_imp is not null and mc.billmonth='"+monthYear+"' and   mm.fdrcategory  IN('FEEDER METER','DT','BOUNDARY METER') and mc.mtrno NOT IN (select mtrno from meter_data.input_energy WHERE billmonth='"+monthYear+"')";
			
		
			System.out.println("Monthly"+qry);

			int j = entityManager.createNativeQuery(qry).executeUpdate();
			System.out.println("Data Monthy Input inserted successfully.");
		
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
	
	
	@Override
	public String getinstComsumptionAutoPush(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			
			String query="INSERT INTO meter_data.input_energy(billmonth,mtrno,time_stamp,kwh_imp,fdrcategory)\r\n" + 
					"			(select  DISTINCT m.billmonth,m.meter_number,now() as time_stamp,round((m.kwh_imp*COALESCE(p.mf,1)),1) as kwh_imp,m.fdrcategory from \r\n" + 
					"			(select x.billmonth,x.meter_number,sum(y.kwh_imp-x.kwh_imp) as kwh_imp,x.fdrcategory from\r\n" + 
					"			(select CAST(to_char(l.read_time,'yyyyMM') as VARCHAR) as billmonth,\r\n" + 
					"			l.meter_number,\r\n" + 
					"		 l.kwh_imp ,mm.town_code,mm.fdrcategory\r\n" + 
					"			from meter_data.amiinstantaneous l  JOIN meter_data.master_main mm ON(l.meter_number=mm.mtrno) WHERE  read_time BETWEEN '"+fromDate+" 00:00:00'  and '"+fromDate+" 00:59:59'\r\n" + 
					"		GROUP BY billmonth,meter_number,kwh_imp,mm.town_code,mm.fdrcategory)x  LEFT JOIN\r\n" + 
					"		\r\n" + 
					"				(select CAST(to_char(l.read_time,'yyyyMM') as VARCHAR)  as billmonth,\r\n" + 
					"			l.meter_number,\r\n" + 
					"			 l.kwh_imp,mm.town_code,mm.fdrcategory\r\n" + 
					"			from meter_data.amiinstantaneous l  JOIN meter_data.master_main mm ON(l.meter_number=mm.mtrno) WHERE  read_time BETWEEN '"+todate+" 00:00:00'  and '"+todate+" 00:59:59' \r\n" + 
					"		GROUP BY kwh_imp,billmonth,meter_number,mm.town_code,mm.fdrcategory)y ON(x.meter_number=y.meter_number) where  x.fdrcategory  IN('FEEDER METER','DT','BOUNDARY METER') GROUP BY x.billmonth,x.meter_number,x.fdrcategory)m   LEFT JOIN 	(select meterno,mf from meter_data.feederdetails)p ON(m.meter_number=p.meterno) where m.kwh_imp is not null and  m.kwh_imp>0 and m.meter_number NOT IN(select mtrno from meter_data.input_energy where billmonth='"+monthYear+"')  GROUP BY m.billmonth,m.meter_number,m.fdrcategory,p.mf,m.kwh_imp) \r\n" + 
					"		\r\n" + 
					"		\r\n" + 
					"		\r\n" + 
					"		";
			
		
			
			System.out.println("Instant"+query);

			int j = entityManager.createNativeQuery(query).executeUpdate();
			
			System.out.println("Data Instant inserted successfully.");
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
	
	
	@Override
	public String getmissingvoltageAutoPush(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			
			String query="\r\n" + 
					"INSERT INTO meter_data.vr_phase(region,circle,town,section,feedername,dtname,meter_number,month_year)\r\n" + 
					" (select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name,x.meter_number, '"+monthYear+"' as month_year from \r\n" + 
					"				(select distinct on(ls.meter_number)ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_r) as v_r,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls \r\n" + 
					"				 LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) \r\n" + 
					"				  where ls.read_time >= to_date('"+fromDate+"', 'YYYY-MM-DD') +INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') and am.circle LIKE '%' \r\n" + 
					"				 GROUP BY ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name)x where x.v_r<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0);";
			
		
		
			
			
			
			System.out.println("Voltage missing"+query);

			int j = entityManager.createNativeQuery(query).executeUpdate();
			
			System.out.println(" Voltage missing Data inserted successfully.");
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
	
	@Override
	public String getmissingvoltageAutoPush_vy(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			String query="INSERT INTO meter_data.vy_phase(region,circle,town,section,feedername,dtname,meter_number,month_year)\r\n" + 
					"\r\n" + 
					"\r\n" + 
					" (select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name,x.meter_number , '"+monthYear+"' as month_year from \r\n" + 
					"				(select distinct on(ls.meter_number)ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_y) as v_y,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls \r\n" + 
					"				 LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) \r\n" + 
					"				  where ls.read_time >= to_date('"+fromDate+"', 'YYYY-MM-DD') +INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') \r\n" + 
					"				 GROUP BY ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name)x where  x.v_y<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0);\r\n" + 
					" ";
			
			
			System.out.println("Voltage Vy missing"+query);

			int j = entityManager.createNativeQuery(query).executeUpdate();
			
			System.out.println(" Voltage missing Data inserted successfully.");
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
	
	
	@Override
	public String getmissingvoltageAutoPush_vb(String monthYear,String fromDate,String todate) {
		BigDecimal check = null;
		//String mnthyr=null;
		String msg = null;
		
		try {
			
			

			
		
			
			String query="INSERT INTO meter_data.vb_phase(region,circle,town,section,feedername,dtname,meter_number,month_year)\r\n" + 
					"\r\n" + 
					"\r\n" + 
					" (select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name, x.meter_number, '"+monthYear+"' as month_year from \r\n" + 
					"				(select distinct on(ls.meter_number)ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_b) as v_b,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls \r\n" + 
					"				 LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) \r\n" + 
					"				  where ls.read_time >= to_date('"+fromDate+"', 'YYYY-MM-DD') +INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') \r\n" + 
					"				 GROUP BY ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name)x where x.v_b<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0);\r\n" + 
					" ";
			
			
			
			System.out.println("Voltage Vy missing"+query);

			int j = entityManager.createNativeQuery(query).executeUpdate();
			
			System.out.println(" Voltage missing Data inserted successfully.");
		
		
	
		}
		catch (NoResultException nre) {
			//nre.printStackTrace();
		}
		
		  return msg;
		  
	 }
	
	@Override
	public String getAfterMonthlyCOnsumtionDTEADataPush(String monthYear) {

		BigDecimal check = null;
		String msg = null;

		String year = monthYear.substring(0, 4);
		String month = monthYear.substring(4, 6);

//		System.out.println(year);
//		System.out.println(month);

		try {

			String query = "select distinct monthyear from meter_data.npp_dt_rpt_monthly_calculation where monthyear = '"
					+ monthYear + "'";

			System.out.println(query);

			check = (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
			System.out.println(check);

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

		if (check == null) {

			String query = "\r\n" + "\r\n" + "Insert into meter_data.npp_dt_rpt_monthly_calculation(\r\n"
					+ "fdr_id,feedername,monthyear, tp_fdr_id,towncode, tpdtid,\r\n" + "\r\n" + "power_fail_freq,\r\n"
					+ "power_fail_duration,\r\n" + "minimum_voltage,\r\n" + "max_current,\r\n" + "input_energy,\r\n"
					+ "export_energy,\r\n" + "ht_industrial_consumer_count,\r\n" + "ht_commercial_consumer_count,\r\n"
					+ "lt_industrial_consumer_count,\r\n" + "lt_commercial_consumer_count,\r\n"
					+ "lt_domestic_consumer_count,\r\n" + "govt_consumer_count,\r\n" + "agri_consumer_count,\r\n"
					+ "others_consumer_count,\r\n" + "ht_industrial_energy_billed,\r\n"
					+ "ht_commercial_energy_billed,\r\n" + "lt_industrial_energy_billed,\r\n"
					+ "lt_commercial_energy_billed,\r\n" + "lt_domestic_energy_billed,\r\n" + "govt_energy_billed,\r\n"
					+ "agri_energy_billed,\r\n" + "others_energy_billed,\r\n" + "ht_industrial_amount_billed,\r\n"
					+ "ht_commercial_amount_billed,\r\n" + "lt_industrial_amount_billed,\r\n"
					+ "lt_commercial_amount_billed,\r\n" + "lt_domestic_amount_billed,\r\n" + "govt_amount_billed,\r\n"
					+ "agri_amount_billed,\r\n" + "others_amount_billed,\r\n" + "ht_industrial_amount_collected,\r\n"
					+ "ht_commercial_amount_collected,\r\n" + "lt_industrial_amount_collected,\r\n"
					+ "lt_commercial_amount_collected,\r\n" + "lt_domestic_amount_collected,\r\n"
					+ "govt_amount_collected,\r\n" + "agri_amount_collected,\r\n" + "others_amount_collected,\r\n"
					+ "open_access_units,\r\n" + "hut_consumer_count,\r\n" + "hut_energy_billed,\r\n"
					+ "hut_amount_billed,\r\n" + "hut_amount_collected,\r\n" + "time_stamp,\r\n" + "meterno,\r\n"
					+ "dt_id,\r\n" + "dtname,officeid\r\n" + ")(\r\n"
					+ "Select X.*,now(),string_agg(d.meterno,',' ORDER BY meterno) as meterno ,string_agg(d.dt_id,',' ORDER BY dt_id) as dt_id ,string_agg(d.dtname,',' ORDER BY dtname) as dtname,d.officeid    from meter_data.dtdetails d,\r\n"
					+ "(Select * from\r\n" + "(SELECT f.fdr_id,feedername, a.* FROM meter_data.feederdetails f, \r\n"
					+ "(\r\n" + "SELECT\r\n" + "study_year||\r\n"
					+ "(CASE WHEN \"length\"(CAST(study_month as TEXT))=1 THEN '0'||study_month else ''||study_month END) as monthyear,\r\n"
					+ "tp_fdr_id,\r\n" + "towncode,\r\n" + "tpdtid,\r\n" + "\r\n"
					+ "sum(COALESCE(power_fail_freq,0)) as power_fail_freq,\r\n"
					+ "sum(COALESCE(power_fail_duration,0)) as power_fail_duration,\r\n"
					+ "sum(COALESCE(minimum_voltage,0)) as minimum_voltage,\r\n"
					+ "sum(COALESCE(max_current,0)) as max_current,\r\n"
					+ "sum(COALESCE(input_energy,0)) as input_energy,\r\n"
					+ "sum(COALESCE(export_energy,0)) as export_energy,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_consumer_count,0)) as ht_industrial_consumer_count,\r\n"
					+ "sum(COALESCE(ht_commercial_consumer_count,0)) as ht_commercial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_industrial_consumer_count,0)) as lt_industrial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_commercial_consumer_count,0)) as lt_commercial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_domestic_consumer_count,0)) as lt_domestic_consumer_count,\r\n"
					+ "sum(COALESCE(govt_consumer_count,0)) as govt_consumer_count,\r\n"
					+ "sum(COALESCE(agri_consumer_count,0)) as agri_consumer_count,\r\n"
					+ "sum(COALESCE(others_consumer_count,0)) as others_consumer_count,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_energy_billed,0)) as ht_industrial_energy_billed,\r\n"
					+ "sum(COALESCE(ht_commercial_energy_billed,0)) as ht_commercial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_industrial_energy_billed,0)) as lt_industrial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_commercial_energy_billed,0)) as lt_commercial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_domestic_energy_billed,0)) as lt_domestic_energy_billed,\r\n"
					+ "sum(COALESCE(govt_energy_billed,0)) as govt_energy_billed,\r\n"
					+ "sum(COALESCE(agri_energy_billed,0)) as agri_energy_billed,\r\n"
					+ "sum(COALESCE(others_energy_billed,0)) as others_energy_billed,\r\n" + "\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_amount_billed,0)) as ht_industrial_amount_billed,\r\n"
					+ "sum(COALESCE(ht_commercial_amount_billed,0)) as ht_commercial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_industrial_amount_billed,0)) as lt_industrial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_commercial_amount_billed,0)) as lt_commercial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_domestic_amount_billed,0)) as lt_domestic_amount_billed,\r\n"
					+ "sum(COALESCE(govt_amount_billed,0)) as govt_amount_billed,\r\n"
					+ "sum(COALESCE(agri_amount_billed,0)) as agri_amount_billed,\r\n"
					+ "sum(COALESCE(others_amount_billed,0)) as others_amount_billed,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_amount_collected,0)) as ht_industrial_amount_collected,\r\n"
					+ "sum(COALESCE(ht_commercial_amount_collected,0)) as ht_commercial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_industrial_amount_collected,0)) as lt_industrial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_commercial_amount_collected,0)) as lt_commercial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_domestic_amount_collected,0)) as lt_domestic_amount_collected,\r\n"
					+ "sum(COALESCE(govt_amount_collected,0)) as govt_amount_collected,\r\n"
					+ "sum(COALESCE(agri_amount_collected,0)) as agri_amount_collected,\r\n"
					+ "sum(COALESCE(others_amount_collected,0)) as others_amount_collected,\r\n"
					+ "sum(COALESCE(open_access_units,0)) as open_access_units,\r\n"
					+ "sum(COALESCE(hut_consumer_count,0)) as hut_consumer_count,\r\n"
					+ "sum(COALESCE(hut_energy_billed,0)) as hut_energy_billed,\r\n"
					+ "sum(COALESCE(hut_amount_billed,0)) as hut_amount_billed,\r\n"
					+ "sum(COALESCE(hut_amount_collected,0)) as hut_amount_collected\r\n" + "\r\n"
					+ "FROM meter_data.npp_dt_rpt_intermediate where study_month = '" + month + "' and study_year = '"
					+ year + "' GROUP BY tp_fdr_id,study_month,study_year,towncode,tpdtid	\r\n"
					+ ")a WHERE f.tp_fdr_id=a.tp_fdr_id AND f.tp_town_code=a.towncode  and f.boundry_feeder=FALSE)C)X\r\n"
					+ "where X.tpdtid=d.dttpid and d.meterno is not null GROUP BY dttpid,fdr_id,monthyear,\r\n" + "tp_fdr_id,towncode,tpdtid,\r\n"
					+ "power_fail_freq,\r\n" + "power_fail_duration,\r\n" + "minimum_voltage,\r\n" + "max_current,\r\n"
					+ "input_energy,\r\n" + "export_energy,\r\n" + "ht_industrial_consumer_count,\r\n"
					+ "ht_commercial_consumer_count,\r\n" + "lt_industrial_consumer_count,\r\n"
					+ "lt_commercial_consumer_count,\r\n" + "lt_domestic_consumer_count,\r\n"
					+ "govt_consumer_count,\r\n" + "agri_consumer_count,\r\n" + "others_consumer_count,\r\n"
					+ "ht_industrial_energy_billed,\r\n" + "ht_commercial_energy_billed,\r\n"
					+ "lt_industrial_energy_billed,\r\n" + "lt_commercial_energy_billed,\r\n"
					+ "lt_domestic_energy_billed,\r\n" + "govt_energy_billed,\r\n" + "agri_energy_billed,\r\n"
					+ "others_energy_billed,\r\n" + "ht_industrial_amount_billed,\r\n"
					+ "ht_commercial_amount_billed,\r\n" + "lt_industrial_amount_billed,\r\n"
					+ "lt_commercial_amount_billed,\r\n" + "lt_domestic_amount_billed,\r\n" + "govt_amount_billed,\r\n"
					+ "agri_amount_billed,\r\n" + "others_amount_billed,\r\n" + "ht_industrial_amount_collected,\r\n"
					+ "ht_commercial_amount_collected,\r\n" + "lt_industrial_amount_collected,\r\n"
					+ "lt_commercial_amount_collected,\r\n" + "lt_domestic_amount_collected,\r\n"
					+ "govt_amount_collected,\r\n" + "agri_amount_collected,\r\n" + "others_amount_collected,\r\n"
					+ "hut_consumer_count,\r\n" + "hut_energy_billed, \r\n" + "hut_amount_billed, \r\n"
					+ "hut_amount_collected,\r\n" + "open_access_units,officeid,feedername\r\n"
					+ "order by tp_fdr_id\r\n" + ")";
			

			int j = entityManager.createNativeQuery(query).executeUpdate();
			System.out.println("Data inserted successfully.");
		} else {

			System.out.println("inside else");
			msg = "synched";

		}
		return msg;
	}

	@Override
	public String energyAccountingDTDataPush(String monthYear) {
		String msg = null;

		String reportMonth = monthYear;
		// String town_code = towncode;
		String Monthsinterval = null;
		String m1=null;
		String m2=null;
		String TableName = null;
		String energySuppliedInterval = null;

		for (int i = 12; i >= 0; i -= 2) {

			System.out.println("This is I " + i);

			if (i == 12) {
				Monthsinterval = "11 MONTH";
				m1="13 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_12months";
				energySuppliedInterval = "14 MONTH";

			}

			if (i == 10) {
				Monthsinterval = "09 MONTH";
				m1="11 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_10months";
				energySuppliedInterval = "12 MONTH";

			}

			if (i == 8) {
				Monthsinterval = "07 MONTH";
				m1="09 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_08months";
				energySuppliedInterval = "10 MONTH";

			}

			if (i == 6) {
				Monthsinterval = "05 MONTH";
				m1="07 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_06months";
				energySuppliedInterval = "08 MONTH";

			}

			if (i == 4) {
				Monthsinterval = "03 MONTH";
				m1="05 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_04months";
				energySuppliedInterval = "06 MONTH";

			}

			if (i == 2) {
				Monthsinterval = "01 MONTH";
				m1="03 MONTH";
				m2="2 MONTH";
				TableName = "rpt_eadt_losses_02months";
				energySuppliedInterval = "04 MONTH";

			}

			if (i == 0) {
				Monthsinterval = "00 MONTH";
				m1="00 MONTH";
				m2="02 MONTH";
				TableName = "rpt_eadt_losses_00months";
				energySuppliedInterval = "03 MONTH";

			}

			
			
			
			
		/*	String qry = "insert into meter_data." + TableName + " (\n" + "month_year,\n" + "tpdt_id,\n" + "meterno,\n"
			+ "feeder_tp_id,\n" + "town_code,\n" + "dt_name,\n" + "feedername,\n" + "office_id,\n"
			+ "time_stamp,\n" + "total_unit_supply,\n" + "total_unit_billed,\n" + "total_amount_billed,\n"
			+ "total_amount_collected,\n" + "total_consumer_count,\n" + "total_ht_unit_billed,\n"
			+ "total_lt_unit_billed,\n" + "total_ht_amount_billed,\n" + "total_lt_amount_billed,\n"
			+ "total_ht_amount_collected,\n" + "total_lt_amount_collected,\n" + "total_lt_consumer_count,\n"
			+ "total_ht_consumer_cocount\n" + ")\n" + "(\n" + "Select distinct *from \n" + "(SELECT \n" + "'"
			+ reportMonth
			+ "' as monthyear, p.tpdtid, p.meterno,p.tp_fdr_id,p.towncode, p.dtname,p.feedername,p.officeid,now(),\n"
			+ "COALESCE(p.total_energy_supplied,0)+COALESCE(q.total_energy_supplied,0) AS gtotal_energy_supplied,\n"
			+ "COALESCE(p.total_ht_unit_billed,0)+COALESCE(p.total_lt_unit_billed,0) AS total_unit_billed,\n"
			+ "COALESCE(p.total_ht_amount_billed,0)+COALESCE(p.total_lt_amount_billed,0) AS total_amount_billed,\n"
			+ "COALESCE(p.total_ht_amount_collected,0)+COALESCE(p.total_lt_amount_collected,0) AS total_amount_collected,\n"
			+ "COALESCE(p.total_lt_consumer_count,0)+COALESCE(p.total_ht_consumer_cocount,0) AS total_consumer_cocount,\n"
			+ "P.total_ht_unit_billed,\n" + "P.total_lt_unit_billed,\n" + "P.total_ht_amount_billed,\n"
			+ "P.total_lt_amount_billed,\n" + "P.total_ht_amount_collected,\n"
			+ "P.total_lt_amount_collected,\n" + "P.total_lt_consumer_count,\n"
			+ "P.total_ht_consumer_cocount\n" + "FROM \n"
			+ "(select distinct tpdtid,tp_fdr_id,towncode, meterno, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') AS METERNO2, \n"
			+ "sum(total_ht_unit_billed)total_ht_unit_billed,\n"
			+ "sum(total_lt_unit_billed)total_lt_unit_billed,\n"
			+ "sum(total_ht_amount_billed)total_ht_amount_billed,\n"
			+ "sum(total_lt_amount_billed)total_lt_amount_billed,\n"
			+ "sum(total_ht_amount_collected)total_ht_amount_collected,\n"
			+ "sum(total_lt_amount_collected)total_lt_amount_collected,\n"
			+ "sum(total_lt_consumer_count)total_lt_consumer_count,\n"
			+ "sum(total_ht_consumer_cocount)total_ht_consumer_cocount,\n"
			+ "sum(total_energy_supplied )total_energy_supplied  from \n"
			+ "(Select tp_fdr_id,tpdtid, meterno,towncode,dtname,feedername,officeid,\n"
			+ "split_part(meterno, ',', 1) as meterno1,\n" + "split_part(meterno, ',', 2) as meterno2,\n"
			+ "split_part(meterno, ',', 3) as meterno3,\n" + "--regexp_split_to_table(meterno,','),\n"
			+ "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ " COALESCE(ht_industrial_energy_billed ,0)+\n"
			+ " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ " COALESCE(lt_industrial_energy_billed ,0)+\n" + " COALESCE(lt_commercial_energy_billed ,0)+\n"
			+ " COALESCE(lt_domestic_energy_billed ,0)+\n" + " COALESCE(govt_energy_billed ,0)+\n"
			+ " COALESCE(agri_energy_billed ,0)+\n"
			+ " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\n" + "  \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_amount_billed	,0)+\n"
			+ " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_amount_billed ,0)+\n" + " COALESCE(lt_commercial_amount_billed ,0)+\n"
			+ " COALESCE(lt_domestic_amount_billed ,0)+\n" + " COALESCE(govt_amount_billed ,0)+\n"
			+ " COALESCE(agri_amount_billed ,0)+\n"
			+ " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_amount_collected	,0)+\n"
			+ " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_amount_collected ,0)+\n"
			+ " COALESCE(lt_commercial_amount_collected ,0)+\n"
			+ " COALESCE(lt_domestic_amount_collected ,0)+\n" + " COALESCE(govt_amount_collected ,0)+\n"
			+ " COALESCE(agri_amount_collected ,0)+\n"
			+ " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" 
			+ " \n"
			+ "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL'3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_consumer_count ,0)+\n" + "COALESCE(lt_commercial_consumer_count ,0)+\n"
			+ "COALESCE(lt_domestic_consumer_count ,0)+\n" + "COALESCE(govt_consumer_count ,0)+\n"
			+ "COALESCE(agri_consumer_count ,0)+\n"
			+ "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\n" + "  \n"
			
			+ "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n"
			+ "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_consumer_count	,0)+\n"
			+ "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\n" + " \n"
			+ " \n" + " \n"
			+ "from meter_data.npp_dt_rpt_monthly_calculation npDt where meterno is not null and meterno <>'' and and (CAST (monthyear as NUMERIC)>=\r\n"  
			+ "cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+m1+"','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'"+m2+"','YYYYMM') AS numeric))  GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\n"
			+ "left join \n"
			+ "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \n"
			+ "where  mc.billmonth >=cast(to_char(to_date('" + reportMonth + "', 'YYYYMM') - INTERVAL '"
			+ energySuppliedInterval + "','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and mtrno NOT IN (select oldmeterno from meter_data.meterchange_transhistory) GROUP BY monthMeterno )y\n"
			+ "on x.meterno1=y.monthMeterno\n"
			+ "GROUP BY  tpdtid, tp_fdr_id,meterno,towncode, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') \n"
			+ " )P\n" + "LEFT JOIN \n"
			+ "(select  tpdtid, meterno,tp_fdr_id,towncode,  COALESCE(meterno2,'0') AS METERNO2,  total_ht_unit_billed, \n"
			+ "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount,\n"
			+ "sum(total_energy_supplied)total_energy_supplied   from \n"
			+ "(Select tp_fdr_id,tpdtid, meterno,towncode,\n" + "split_part(meterno, ',', 2) as meterno2,\n"
			+ "--regexp_split_to_table(meterno,','),\n" + "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n"
			+ "cast(to_char((TO_DATE(to_char(to_date('" + reportMonth
			+ "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '" + Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ " COALESCE(ht_industrial_energy_billed ,0)+\n"
			+ " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ " COALESCE(lt_industrial_energy_billed ,0)+\n" + " COALESCE(lt_commercial_energy_billed ,0)+\n"
			+ " COALESCE(lt_domestic_energy_billed ,0)+\n" + " COALESCE(govt_energy_billed ,0)+\n"
			+ " COALESCE(agri_energy_billed ,0)+\n"
			+ " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\n" + "  \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_amount_billed	,0)+\n"
			+ " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_amount_billed ,0)+\n" + " COALESCE(lt_commercial_amount_billed ,0)+\n"
			+ " COALESCE(lt_domestic_amount_billed ,0)+\n" + " COALESCE(govt_amount_billed ,0)+\n"
			+ " COALESCE(agri_amount_billed ,0)+\n"
			+ " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_amount_collected	,0)+\n"
			+ " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" + " \n"
			+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_amount_collected ,0)+\n"
			+ " COALESCE(lt_commercial_amount_collected ,0)+\n"
			+ " COALESCE(lt_domestic_amount_collected ,0)+\n" + " COALESCE(govt_amount_collected ,0)+\n"
			+ " COALESCE(agri_amount_collected ,0)+\n"
			+ " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" + " \n"
			+ "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
			+ Monthsinterval
			+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(lt_industrial_consumer_count ,0)+\n" + "COALESCE(lt_commercial_consumer_count ,0)+\n"
			+ "COALESCE(lt_domestic_consumer_count ,0)+\n" + "COALESCE(govt_consumer_count ,0)+\n"
			+ "COALESCE(agri_consumer_count ,0)+\n"
			+ "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\n" + "  \n"
			+ "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n"
			+ "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
			+ "COALESCE(ht_industrial_consumer_count	,0)+\n"
			+ "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\n" + " \n"
			+ " \n" + " \n"
			+ "from meter_data.npp_dt_rpt_monthly_calculation npDt where (CAST (monthyear as NUMERIC)>=\r\n"
			+ "cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+m1+"','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'"+m1+"','YYYYMM') AS numeric))   GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\n"
			+ "join \n"
			+ "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \n"
			+ "where  mc.billmonth >=cast(to_char(to_date('" + reportMonth + "', 'YYYYMM') - INTERVAL '"
			+ energySuppliedInterval + "','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"
			+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and mtrno NOT IN (select oldmeterno from meter_data.meterchange_transhistory) GROUP BY monthMeterno)y\n"
			+ "on x.meterno2=y.monthMeterno \n"
			+ "group by  tpdtid, meterno,  COALESCE(meterno2,'0') ,  total_ht_unit_billed,tp_fdr_id,towncode,\n"
			+ "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount\n"
			+ ")Q\n" + "ON P.METERNO=Q.METERNO   ) q\n" + ")"; */
	
			
			String qry="insert into meter_data." + TableName + " (\r\n" + 
					"	tpdt_id,\r\n" + 
					"  month_year,\r\n" + 
					" 	meterno,\r\n" + 
					"  feeder_tp_id,\r\n" + 
					"  town_code,\r\n" + 
					"  dt_name,\r\n" + 
					"  feedername,\r\n" + 
					"  office_id,\r\n" + 
					"  time_stamp,\r\n" + 
					"  total_unit_supply,\r\n" + 
					"  total_unit_billed,\r\n" + 
					"  total_amount_billed,\r\n" + 
					"  total_amount_collected,\r\n" + 
					"  total_consumer_count,\r\n" + 
					"  total_ht_unit_billed,\r\n" + 
					"  total_lt_unit_billed,\r\n" + 
					"  total_ht_amount_billed,\r\n" + 
					"  total_lt_amount_billed,\r\n" + 
					"  total_ht_amount_collected,\r\n" + 
					"  total_lt_amount_collected,\r\n" + 
					"  total_lt_consumer_count,\r\n" + 
					"  total_ht_consumer_cocount\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"(\r\n" + 
					"Select distinct * from \r\n" + 
					"(SELECT distinct p.tpdtid,\r\n" + 
					"'"+reportMonth+"' as monthyear,  p.meterno,p.tp_fdr_id,p.towncode, split_part(p.dtname, ',', 1) as dtname,p.feedername,p.officeid,now(),\r\n" + 
					"COALESCE(p.total_energy_supplied,0) + COALESCE(q.total_energy_supplied,0) AS gtotal_energy_supplied,\r\n" + 
					"COALESCE(p.total_ht_unit_billed,0)+COALESCE(p.total_lt_unit_billed,0) AS total_unit_billed,\r\n" + 
					"COALESCE(p.total_ht_amount_billed,0)+COALESCE(p.total_lt_amount_billed,0) AS total_amount_billed,\r\n" + 
					"COALESCE(p.total_ht_amount_collected,0)+COALESCE(p.total_lt_amount_collected,0) AS total_amount_collected,\r\n" + 
					"COALESCE(p.total_lt_consumer_count,0)+COALESCE(p.total_ht_consumer_cocount,0) AS total_consumer_cocount,\r\n" + 
					"P.total_ht_unit_billed,\r\n" + 
					"P.total_lt_unit_billed,\r\n" + 
					"P.total_ht_amount_billed,\r\n" + 
					"P.total_lt_amount_billed,\r\n" + 
					"P.total_ht_amount_collected,\r\n" + 
					"P.total_lt_amount_collected,\r\n" + 
					"P.total_lt_consumer_count,\r\n" + 
					"P.total_ht_consumer_cocount\r\n" + 
					"FROM \r\n" + 
					"(select distinct ON(tpdtid)tpdtid,tp_fdr_id,towncode, meterno, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') AS METERNO2, \r\n" + 
					"sum(total_ht_unit_billed)total_ht_unit_billed,\r\n" + 
					"sum(total_lt_unit_billed)total_lt_unit_billed,\r\n" + 
					"sum(total_ht_amount_billed)total_ht_amount_billed,\r\n" + 
					"sum(total_lt_amount_billed)total_lt_amount_billed,\r\n" + 
					"sum(total_ht_amount_collected)total_ht_amount_collected,\r\n" + 
					"sum(total_lt_amount_collected)total_lt_amount_collected,\r\n" + 
					"COALESCE(sum(distinct total_lt_consumer_count+total_agri_consumer_count),0) AS total_lt_consumer_count,\r\n" + 
					"COALESCE(sum(total_ht_consumer_cocount),0) AS total_ht_consumer_cocount,\r\n" + 
					"sum(total_energy_supplied )total_energy_supplied \r\n" + 
					"\r\n" + 
					"from\r\n" + 
					"(select DISTINCT tp_fdr_id,tpdtid, meterno,towncode,dtname,feedername,officeid,\r\n" + 
					"split_part(meterno, ',', 1) as meterno1,\r\n" + 
					"split_part(meterno, ',', 2) as meterno2,\r\n" + 
					"split_part(meterno, ',', 3) as meterno3,\r\n" + 
					"--regexp_split_to_table(meterno,','),\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(ht_industrial_energy_billed ,0)+\r\n" + 
					" COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(lt_industrial_energy_billed ,0)+\r\n" + 
					" COALESCE(lt_commercial_energy_billed ,0)+\r\n" + 
					" COALESCE(lt_domestic_energy_billed ,0)+\r\n" + 
					" COALESCE(govt_energy_billed ,0)+\r\n" + 
					" COALESCE(agri_energy_billed ,0)+\r\n" + 
					" COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\r\n" + 
					"  \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_amount_billed	,0)+\r\n" + 
					" COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_amount_billed ,0)+\r\n" + 
					" COALESCE(lt_commercial_amount_billed ,0)+\r\n" + 
					" COALESCE(lt_domestic_amount_billed ,0)+\r\n" + 
					" COALESCE(govt_amount_billed ,0)+\r\n" + 
					" COALESCE(agri_amount_billed ,0)+\r\n" + 
					" COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_amount_collected	,0)+\r\n" + 
					" COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_commercial_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_domestic_amount_collected ,0)+\r\n" + 
					" COALESCE(govt_amount_collected ,0)+\r\n" + 
					" COALESCE(agri_amount_collected ,0)+\r\n" + 
					" COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n" + 
					" \r\n" + 
					"\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_consumer_count ,0)+\r\n" + 
					"COALESCE(lt_commercial_consumer_count ,0)+\r\n" + 
					"COALESCE(lt_domestic_consumer_count ,0)+ \r\n" + 
					"COALESCE(govt_consumer_count ,0)+ \r\n" + 
					"COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\r\n" + 
					"\r\n" + 
					"SUM(DISTINCT CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric))   THEN  (\r\n" + 
					"COALESCE(agri_consumer_count ,0))  END)  AS total_agri_consumer_count,\r\n" + 
					"				\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'02 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_consumer_count	,0)+\r\n" + 
					"COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount from meter_data.npp_dt_rpt_monthly_calculation where meterno is not null and meterno <>'' and meterno <>''   and (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+m1+"','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'"+m2+"','YYYYMM') AS numeric)) GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x left join \r\n" + 
					"\r\n" + 
					"\r\n" + 
					"(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.input_energy mc  \r\n" + 
					"where  cast(mc.billmonth as numeric) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND cast(mc.billmonth as numeric) <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and fdrcategory NOT IN('FEEDER METER')  GROUP BY monthMeterno)y\r\n" + 
					"on x.meterno1=y.monthMeterno\r\n" + 
					"GROUP BY  tpdtid, tp_fdr_id,meterno,towncode, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0')\r\n" + 
					" )P\r\n" + 
					"LEFT JOIN \r\n" + 
					"(select  tpdtid, meterno,tp_fdr_id,towncode,  COALESCE(meterno2,'0') AS METERNO2,  total_ht_unit_billed, \r\n" + 
					"total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount,\r\n" + 
					"sum(total_energy_supplied)total_energy_supplied   from \r\n" + 
					"(Select tp_fdr_id,tpdtid, meterno,towncode,\r\n" + 
					"split_part(meterno, ',', 2) as meterno2,monthyear,\r\n" + 
					"--regexp_split_to_table(meterno,','),\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(ht_industrial_energy_billed ,0)+\r\n" + 
					" COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(lt_industrial_energy_billed ,0)+\r\n" + 
					" COALESCE(lt_commercial_energy_billed ,0)+\r\n" + 
					" COALESCE(lt_domestic_energy_billed ,0)+\r\n" + 
					" COALESCE(govt_energy_billed ,0)+\r\n" + 
					" COALESCE(agri_energy_billed ,0)+\r\n" + 
					" COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\r\n" + 
					"  \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_amount_billed	,0)+\r\n" + 
					" COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_amount_billed ,0)+\r\n" + 
					" COALESCE(lt_commercial_amount_billed ,0)+\r\n" + 
					" COALESCE(lt_domestic_amount_billed ,0)+\r\n" + 
					" COALESCE(govt_amount_billed ,0)+\r\n" + 
					" COALESCE(agri_amount_billed ,0)+\r\n" + 
					" COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_amount_collected	,0)+\r\n" + 
					" COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_commercial_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_domestic_amount_collected ,0)+\r\n" + 
					" COALESCE(govt_amount_collected ,0)+\r\n" + 
					" COALESCE(agri_amount_collected ,0)+\r\n" + 
					" COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n" + 
					"\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_industrial_consumer_count ,0)+\r\n" + 
					"COALESCE(lt_commercial_consumer_count ,0)+\r\n" + 
					"COALESCE(lt_domestic_consumer_count ,0)+ \r\n" + 
					"COALESCE(govt_consumer_count ,0)+ \r\n" + 
					"COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\r\n" + 
					"\r\n" + 
					"SUM(DISTINCT CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric))   THEN  (\r\n" + 
					"COALESCE(agri_consumer_count ,0))   END)  AS total_agri_consumer_count,\r\n" + 
					"				\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '03 MONTH','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'02 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_industrial_consumer_count	,0)+\r\n" + 
					"COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\r\n" + 
					" \r\n" + 
					"from meter_data.npp_dt_rpt_monthly_calculation npDt where (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+m1+"','YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'"+m2+"','YYYYMM') AS numeric))   GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO,monthyear)x\r\n" + 
					"join \r\n" + 
					"(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.input_energy mc  \r\n" + 
					"where  cast(mc.billmonth as numeric) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND cast(mc.billmonth as numeric) <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and fdrcategory NOT IN('FEEDER METER')  GROUP BY monthMeterno)y\r\n" + 
					"on x.meterno2=y.monthMeterno  \r\n" + 
					"group by  tpdtid, meterno,  COALESCE(meterno2,'0') ,  total_ht_unit_billed,tp_fdr_id,towncode,\r\n" + 
					"total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount\r\n" + 
					")Q\r\n" + 
					"ON P.METERNO=Q.METERNO) q \r\n" + 
					")\r\n" + 
					" ";
			
			
			
			/*
			 * String qry="  insert into meter_data. "+ TableName +"  (\r\n" +
			 * "	tpdt_id,\r\n" + "  month_year,\r\n" + " 	meterno,\r\n" +
			 * "  feeder_tp_id,\r\n" + "  town_code,\r\n" + "  dt_name,\r\n" +
			 * "  feedername,\r\n" + "  office_id,\r\n" + "  time_stamp,\r\n" +
			 * "  total_unit_supply,\r\n" + "  total_unit_billed,\r\n" +
			 * "  total_amount_billed,\r\n" + "  total_amount_collected,\r\n" +
			 * "  total_consumer_count,\r\n" + "  total_ht_unit_billed,\r\n" +
			 * "  total_lt_unit_billed,\r\n" + "  total_ht_amount_billed,\r\n" +
			 * "  total_lt_amount_billed,\r\n" + "  total_ht_amount_collected,\r\n" +
			 * "  total_lt_amount_collected,\r\n" + "  total_lt_consumer_count,\r\n" +
			 * "  total_ht_consumer_cocount\r\n" + ")\r\n" + "(\r\n" +
			 * "Select distinct * from \r\n" + "(SELECT distinct p.tpdtid,\r\n" + "'"+
			 * reportMonth+"' as monthyear,  p.meterno,p.tp_fdr_id,p.towncode, split_part(p.dtname, ',', 1) as dtname,p.feedername,p.officeid,now(),\r\n"
			 * +
			 * "COALESCE(p.total_energy_supplied,0)+COALESCE(q.total_energy_supplied,0) AS gtotal_energy_supplied,\r\n"
			 * +
			 * "COALESCE(p.total_ht_unit_billed,0)+COALESCE(p.total_lt_unit_billed,0) AS total_unit_billed,\r\n"
			 * +
			 * "COALESCE(p.total_ht_amount_billed,0)+COALESCE(p.total_lt_amount_billed,0) AS total_amount_billed,\r\n"
			 * +
			 * "COALESCE(p.total_ht_amount_collected,0)+COALESCE(p.total_lt_amount_collected,0) AS total_amount_collected,\r\n"
			 * +
			 * "COALESCE(p.total_lt_consumer_count,0)+COALESCE(p.total_ht_consumer_cocount,0) AS total_consumer_cocount,\r\n"
			 * + "P.total_ht_unit_billed,\r\n" + "P.total_lt_unit_billed,\r\n" +
			 * "P.total_ht_amount_billed,\r\n" + "P.total_lt_amount_billed,\r\n" +
			 * "P.total_ht_amount_collected,\r\n" + "P.total_lt_amount_collected,\r\n" +
			 * "P.total_lt_consumer_count,\r\n" + "P.total_ht_consumer_cocount\r\n" +
			 * "FROM \r\n" +
			 * "(select distinct ON(tpdtid)tpdtid,tp_fdr_id,towncode, meterno, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') AS METERNO2, \r\n"
			 * + "sum(total_ht_unit_billed)total_ht_unit_billed,\r\n" +
			 * "sum(total_lt_unit_billed)total_lt_unit_billed,\r\n" +
			 * "sum(total_ht_amount_billed)total_ht_amount_billed,\r\n" +
			 * "sum(total_lt_amount_billed)total_lt_amount_billed,\r\n" +
			 * "sum(total_ht_amount_collected)total_ht_amount_collected,\r\n" +
			 * "sum(total_lt_amount_collected)total_lt_amount_collected,\r\n" +
			 * "sum(total_lt_consumer_count)total_lt_consumer_count,\r\n" +
			 * "sum(total_ht_consumer_cocount)total_ht_consumer_cocount,\r\n" +
			 * "sum(total_energy_supplied )total_energy_supplied  from \r\n" +
			 * "(Select tp_fdr_id,tpdtid, meterno,towncode,dtname,feedername,officeid,\r\n"
			 * + "split_part(meterno, ',', 1) as meterno1,\r\n" +
			 * "split_part(meterno, ',', 2) as meterno2,\r\n" +
			 * "split_part(meterno, ',', 3) as meterno3,\r\n" +
			 * "--regexp_split_to_table(meterno,','),\r\n" +
			 * "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + " COALESCE(ht_industrial_energy_billed ,0)+\r\n" +
			 * " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + " COALESCE(lt_industrial_energy_billed ,0)+\r\n" +
			 * " COALESCE(lt_commercial_energy_billed ,0)+\r\n" +
			 * " COALESCE(lt_domestic_energy_billed ,0)+\r\n" +
			 * " COALESCE(govt_energy_billed ,0)+\r\n" +
			 * " COALESCE(agri_energy_billed ,0)+\r\n" +
			 * " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\r\n" +
			 * "  \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(ht_industrial_amount_billed	,0)+\r\n" +
			 * " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(lt_industrial_amount_billed ,0)+\r\n" +
			 * " COALESCE(lt_commercial_amount_billed ,0)+\r\n" +
			 * " COALESCE(lt_domestic_amount_billed ,0)+\r\n" +
			 * " COALESCE(govt_amount_billed ,0)+\r\n" +
			 * " COALESCE(agri_amount_billed ,0)+\r\n" +
			 * " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(ht_industrial_amount_collected	,0)+\r\n" +
			 * " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('202202', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n"
			 * + "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(lt_industrial_amount_collected ,0)+\r\n" +
			 * " COALESCE(lt_commercial_amount_collected ,0)+\r\n" +
			 * " COALESCE(lt_domestic_amount_collected ,0)+\r\n" +
			 * " COALESCE(govt_amount_collected ,0)+\r\n" +
			 * " COALESCE(agri_amount_collected ,0)+\r\n" +
			 * " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n"
			 * 
			 * + " \r\n" + "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"+
			 * Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL'3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(lt_industrial_consumer_count ,0)+\r\n" +
			 * "COALESCE(lt_commercial_consumer_count ,0)+\r\n" +
			 * "COALESCE(lt_domestic_consumer_count ,0)+\r\n" +
			 * "COALESCE(govt_consumer_count ,0)+\r\n" +
			 * "COALESCE(agri_consumer_count ,0)+\r\n" +
			 * "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\r\n"
			 * + "  \r\n" + "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * + reportMonth
			 * +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" +
			 * "COALESCE(ht_industrial_consumer_count	,0)+\r\n" +
			 * "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\r\n"
			 * + " \r\n" + " \r\n" + " \r\n" +
			 * "from meter_data.npp_dt_rpt_monthly_calculation npDt where meterno is not null and meterno <>'' and meterno <>'' or meterno in (select meterno from meter_data.meterchange_transhistory mt \r\n"
			 * + "join meter_data.npp_dt_rpt_monthly_calculation mc\r\n" +
			 * "on (mt.oldmeterno=mc.meterno or mt.accno= mc.tpdtid) \r\n" +
			 * "where accno=CASE WHEN reason='Normal' THEN newmeterno ELSE oldmeterno end)  GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\r\n"
			 * + "left join \r\n" +
			 * "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \r\n"
			 * + "where  mc.billmonth >=cast(to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '"+ energySuppliedInterval +
			 * "','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and mtrno NOT IN (select oldmeterno from meter_data.meterchange_transhistory where reason  NOT IN('Normal')) GROUP BY monthMeterno )y\r\n"
			 * +
			 * "on x.meterno1=y.monthMeterno where meterno2 NOT IN(select oldmeterno from meter_data.meterchange_transhistory where reason  NOT IN('Normal')) and meterno1 NOT IN(select oldmeterno from meter_data.meterchange_transhistory where reason  NOT IN('Normal')) \r\n"
			 * +
			 * "GROUP BY  tpdtid, tp_fdr_id,meterno,towncode, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') \r\n"
			 * + " )P\r\n" + "LEFT JOIN \r\n" +
			 * "(select  tpdtid, meterno,tp_fdr_id,towncode,  COALESCE(meterno2,'0') AS METERNO2,  total_ht_unit_billed, \r\n"
			 * +
			 * "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount,\r\n"
			 * + "sum(total_energy_supplied)total_energy_supplied   from \r\n" +
			 * "(Select tp_fdr_id,tpdtid, meterno,towncode,\r\n" +
			 * "split_part(meterno, ',', 2) as meterno2,\r\n" +
			 * "--regexp_split_to_table(meterno,','),\r\n" +
			 * "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + " COALESCE(ht_industrial_energy_billed ,0)+\r\n" +
			 * " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + " COALESCE(lt_industrial_energy_billed ,0)+\r\n" +
			 * " COALESCE(lt_commercial_energy_billed ,0)+\r\n" +
			 * " COALESCE(lt_domestic_energy_billed ,0)+\r\n" +
			 * " COALESCE(govt_energy_billed ,0)+\r\n" +
			 * " COALESCE(agri_energy_billed ,0)+\r\n" +
			 * " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\r\n" +
			 * "  \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(ht_industrial_amount_billed	,0)+\r\n" +
			 * " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +
			 * "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * + reportMonth +
			 * "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" +
			 * "COALESCE(lt_industrial_amount_billed ,0)+\r\n" +
			 * " COALESCE(lt_commercial_amount_billed ,0)+\r\n" +
			 * " COALESCE(lt_domestic_amount_billed ,0)+\r\n" +
			 * " COALESCE(govt_amount_billed ,0)+\r\n" +
			 * " COALESCE(agri_amount_billed ,0)+\r\n" +
			 * " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+
			 * reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n"
			 * + "COALESCE(ht_industrial_amount_collected	,0)+\r\n" +
			 * " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n"
			 * + " \r\n" + " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +
			 * "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + "- INTERVAL '"
			 * +Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * + reportMonth +
			 * "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" +
			 * "COALESCE(lt_industrial_amount_collected ,0)+\r\n" +
			 * " COALESCE(lt_commercial_amount_collected ,0)+\r\n" +
			 * " COALESCE(lt_domestic_amount_collected ,0)+\r\n" +
			 * " COALESCE(govt_amount_collected ,0)+\r\n" +
			 * " COALESCE(agri_amount_collected ,0)+\r\n" +
			 * " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n"
			 * + " \r\n" + "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +
			 * "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + "- INTERVAL '"+
			 * Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * + reportMonth
			 * +"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" +
			 * "COALESCE(lt_industrial_consumer_count ,0)+\r\n" +
			 * "COALESCE(lt_commercial_consumer_count ,0)+\r\n" +
			 * "COALESCE(lt_domestic_consumer_count ,0)+\r\n" +
			 * "COALESCE(govt_consumer_count ,0)+\r\n" +
			 * "COALESCE(agri_consumer_count ,0)+\r\n" +
			 * "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\r\n"
			 * + "  \r\n" + "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" +
			 * "cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +
			 * "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" +
			 * "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
			 * + reportMonth +
			 * "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" +
			 * "COALESCE(ht_industrial_consumer_count	,0)+\r\n" +
			 * "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\r\n"
			 * + " \r\n" + " \r\n" + " \r\n" +
			 * "from meter_data.npp_dt_rpt_monthly_calculation npDt where meterno NOT IN (select distinct oldmeterno from meter_data.meterchange_transhistory where reason  NOT IN('Normal'))  GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\r\n"
			 * + "join \r\n" +
			 * "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \r\n"
			 * + "where  mc.billmonth >=cast(to_char(to_date('"+ reportMonth
			 * +"', 'YYYYMM') - INTERVAL '"+
			 * energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) and mtrno NOT IN (select oldmeterno from meter_data.meterchange_transhistory) GROUP BY monthMeterno)y\r\n"
			 * +
			 * "on x.meterno2=y.monthMeterno  where x.meterno2 NOT IN(select oldmeterno from meter_data.meterchange_transhistory where reason  NOT IN('Normal'))\r\n"
			 * +
			 * "group by  tpdtid, meterno,  COALESCE(meterno2,'0') ,  total_ht_unit_billed,tp_fdr_id,towncode,\r\n"
			 * +
			 * "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount\r\n"
			 * + ")Q\r\n" + "ON P.METERNO=Q.METERNO) q\r\n" + ")";*/
			 
			
			
			System.out.println("DT Push Query ---> " + qry);

			int x = 0;

			try {
				x = entityManager.createNativeQuery(qry).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg;

	}

	@Override
	public String getafterMonthlyCOnsumtionFeederTownEADataPush(String monthYear) {
		BigDecimal check = null;
		String msg = null;

		String year = monthYear.substring(0, 4);
		String month = monthYear.substring(4, 6);

		try {

			String query = "select * from meter_data.npp_data_monthly_calculation where monthyear = '" + monthYear
					+ "'";

			System.out.println(query);

			check = (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
			System.out.println(check);

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

		if (check == null) {

			String query = "insert into meter_data.npp_data_monthly_calculation(\r\n"
					+ "  officeid,feeder_code,tp_feeder_code,monthyear,\r\n" + "feeder_type,towncode,\r\n" + "\r\n"
					+ "no_of_power_faliure, \r\n" + "duration_of_power_faliure,\r\n" + "min_voltage,\r\n"
					+ "max_current,\r\n" + "input_energy,\r\n" + "export_energy,\r\n" + "\r\n" + "ht_ind_con_count,\r\n"
					+ "ht_com_con_count,\r\n" + "lt_ind_con_count,\r\n" + "lt_com_con_count,\r\n"
					+ "lt_dom_con_count,\r\n" + "govt_con_count,\r\n" + "agri_con_count,\r\n" + "other_con_count,\r\n"
					+ "\r\n" + "ht_ind_energy_bill,\r\n" + "ht_com_energy_bill,\r\n" + "lt_ind_energy_bill,\r\n"
					+ "lt_com_energy_bill,\r\n" + "lt_dom_energy_bill,\r\n" + "govt_energy_bill,\r\n"
					+ "agri_energy_bill,\r\n" + "other_energy_bill,\r\n" + "\r\n" + "ht_ind_amount_bill,\r\n"
					+ "ht_com_amount_bill,\r\n" + "lt_ind_amount_bill,\r\n" + "lt_com_amount_bill,\r\n"
					+ "lt_dom_amount_bill,\r\n" + "govt_amount_bill,\r\n" + "agri_amount_bill,\r\n"
					+ "other_amount_bill,\r\n" + "\r\n" + "ht_ind_amount_collected,\r\n"
					+ "ht_com_amount_collected,\r\n" + "lt_ind_amount_collected,\r\n" + "lt_com_amount_collected,\r\n"
					+ "lt_dom_amount_collected,\r\n" + "govt_amount_collected,\r\n" + "agri_amount_collected,\r\n"
					+ "other_amount_collected,\r\n" + "\r\n" + "\r\n" + "hut_consumer_count,\r\n"
					+ "hut_energy_billed,\r\n" + "hut_amount_billed,\r\n" + "hut_amount_collected,\r\n"
					+ "time_stamp\r\n" + ")\r\n" + "(\r\n"
					+ "SELECT distinct officeid,fdr_id, a.* FROM meter_data.feederdetails f, \r\n" + "(\r\n"
					+ "SELECT\r\n" + "tp_fdr_id,study_year||\r\n"
					+ "(CASE WHEN \"length\"(CAST(study_month as TEXT))=1 THEN '0'||study_month else ''||study_month END) as\r\n"
					+ "monthyear,\r\n" + "fdrtype,\r\n" + "towncode,\r\n" + "\r\n"
					+ "sum(COALESCE(power_fail_freq,0)) as power_fail_freq,\r\n"
					+ "sum(COALESCE(power_fail_duration,0)) as power_fail_duration,\r\n"
					+ "sum(COALESCE(minimum_voltage,0)) as minimum_voltage,\r\n"
					+ "sum(COALESCE(max_current,0)) as max_current,\r\n"
					+ "sum(COALESCE(input_energy,0)) as input_energy,\r\n"
					+ "sum(COALESCE(export_energy,0)) as export_energy,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_consumer_count,0)) as ht_industrial_consumer_count,\r\n"
					+ "sum(COALESCE(ht_commercial_consumer_count,0)) as ht_commercial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_industrial_consumer_count,0)) as lt_industrial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_commercial_consumer_count,0)) as lt_commercial_consumer_count,\r\n"
					+ "sum(COALESCE(lt_domestic_consumer_count,0)) as lt_domestic_consumer_count,\r\n"
					+ "sum(COALESCE(govt_consumer_count,0)) as govt_consumer_count,\r\n"
					+ "sum(COALESCE(agri_consumer_count,0)) as agri_consumer_count,\r\n"
					+ "sum(COALESCE(others_consumer_count,0)) as others_consumer_count,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_energy_billed,0)) as ht_industrial_energy_billed,\r\n"
					+ "sum(COALESCE(ht_commercial_energy_billed,0)) as ht_commercial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_industrial_energy_billed,0)) as lt_industrial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_commercial_energy_billed,0)) as lt_commercial_energy_billed,\r\n"
					+ "sum(COALESCE(lt_domestic_energy_billed,0)) as lt_domestic_energy_billed,\r\n"
					+ "sum(COALESCE(govt_energy_billed,0)) as govt_energy_billed,\r\n"
					+ "sum(COALESCE(agri_energy_billed,0)) as agri_energy_billed,\r\n"
					+ "sum(COALESCE(others_energy_billed,0)) as others_energy_billed,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_amount_billed,0)) as ht_industrial_amount_billed,\r\n"
					+ "sum(COALESCE(ht_commercial_amount_billed,0)) as ht_commercial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_industrial_amount_billed,0)) as lt_industrial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_commercial_amount_billed,0)) as lt_commercial_amount_billed,\r\n"
					+ "sum(COALESCE(lt_domestic_amount_billed,0)) as lt_domestic_amount_billed,\r\n"
					+ "sum(COALESCE(govt_amount_billed,0)) as govt_amount_billed,\r\n"
					+ "sum(COALESCE(agri_amount_billed,0)) as agri_amount_billed,\r\n"
					+ "sum(COALESCE(others_amount_billed,0)) as others_amount_billed,\r\n" + "\r\n"
					+ "sum(COALESCE(ht_industrial_amount_collected,0)) as ht_industrial_amount_collected,\r\n"
					+ "sum(COALESCE(ht_commercial_amount_collected,0)) as ht_commercial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_industrial_amount_collected,0)) as lt_industrial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_commercial_amount_collected,0)) as lt_commercial_amount_collected,\r\n"
					+ "sum(COALESCE(lt_domestic_amount_collected,0)) as lt_domestic_amount_collected,\r\n"
					+ "sum(COALESCE(govt_amount_collected,0)) as govt_amount_collected,\r\n"
					+ "sum(COALESCE(agri_amount_collected,0)) as agri_amount_collected,\r\n"
					+ "sum(COALESCE(others_amount_collected,0)) as others_amount_collected,\r\n" + "\r\n" + "\r\n"
					+ "sum(COALESCE(hut_consumer_count,0)) as hut_consumer_count,\r\n"
					+ "sum(COALESCE(hut_energy_billed,0)) as hut_energy_billed,\r\n"
					+ "sum(COALESCE(hut_amount_billed,0)) as hut_amount_billed,\r\n"
					+ "sum(COALESCE(hut_amount_collected,0)) as hut_amount_collected,\r\n" + "\r\n" + "\r\n" + "\r\n"
					+ "now()\r\n" + "\r\n" + "FROM meter_data.npp_feeder_rpt_intermediate where study_year = '" + year
					+ "' and study_month = '" + month
					+ "' GROUP BY tp_fdr_id,study_month,study_year,fdrtype,towncode,officeid,fdr_id\r\n" + "\r\n"
					+ ")a WHERE f.tp_fdr_id=a.tp_fdr_id AND f.tp_town_code=a.towncode and  boundry_feeder = 'false' and feeder_type = 'IPDS'\r\n"
					+ ")";

			int j = entityManager.createNativeQuery(query).executeUpdate();
			System.out.println("Data inserted successfully.");
		} else {

			System.out.println("inside else");
			msg = "synched";

		}
		return msg;
	}

	@Override
	public String getAfterMonthlyCOnsumtionFeederTownEADataPushForFeeder(String monthYear) {

		String msg = null;

		String reportMonth = monthYear;
		// String town_code = towncode;
		String Monthsinterval = null;
		String TableName = null;
		String energySuppliedInterval = null;

		for (int i = 12; i >= 0; i -= 2) {

			System.out.println("This is I " + i);

			if (i == 12) {
				Monthsinterval = "11 MONTH";
				TableName = "rpt_eamainfeeder_losses_12months";
				energySuppliedInterval = "14 MONTH";

			}

			if (i == 10) {
				Monthsinterval = "09 MONTH";
				TableName = "rpt_eamainfeeder_losses_10months";
				energySuppliedInterval = "12 MONTH";

			}

			if (i == 8) {
				Monthsinterval = "07 MONTH";
				TableName = "rpt_eamainfeeder_losses_08months";
				energySuppliedInterval = "10 MONTH";

			}

			if (i == 6) {
				Monthsinterval = "05 MONTH";
				TableName = "rpt_eamainfeeder_losses_06months";
				energySuppliedInterval = "08 MONTH";

			}

			if (i == 4) {
				Monthsinterval = "03 MONTH";
				TableName = "rpt_eamainfeeder_losses_04months";
				energySuppliedInterval = "06 MONTH";

			}

			if (i == 2) {
				Monthsinterval = "01 MONTH";
				TableName = "rpt_eamainfeeder_losses_02months";
				energySuppliedInterval = "04 MONTH";

			}

			if (i == 0) {
				Monthsinterval = "00 MONTH";
				TableName = "rpt_eamainfeeder_losses_00months";
				energySuppliedInterval = "03 MONTH";

			}

			/*String qry = "insert into meter_data." + TableName + "  (\n"
					+ "month_year,office_id,fdr_id,tp_fdr_id,fdr_name,meter_sr_number,tot_consumers,\n"
					+ "unit_billed,amt_billed,amt_collected,time_stamp,\n"
					+ "town_code,boundary_feeder,feeder_import_energy,unit_supply)\n" + "(\n"
					+ "Select maind.*, main_unit_supply\n" + "from \n" + "(\n" + "Select CAST ('" + reportMonth
					+ "' as NUMERIC) report_month,  CAST (officeid as NUMERIC) officeid,\n"
					+ "feeder_code,tp_feeder_code,feedername,meterno,\n"
					+ "SUM(total_lt_consumer_count + total_ht_consumer_cocount )as total_consumer_count,\n"
					+ "SUM(total_ht_unit_billed + total_lt_unit_billed )as total_units_billed,\n"
					+ "SUM(total_ht_amount_billed + total_lt_amount_billed )as total_amount_billed,\n"
					+ "SUM (total_ht_amount_collected + total_lt_amount_collected) as total_amount_collected,now()\n"
					+ ",towncode,boundry_feeder,\n" + "total_import_energy_supplied\n"
					+ "from (Select feeder_code,fd.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,feedername,\n"
					+

					"SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ " COALESCE(ht_ind_energy_bill ,0)+\n"
					+ " COALESCE(ht_com_energy_bill ,0))    END)  AS total_ht_unit_billed,\n" + " \n"
					+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ " COALESCE(lt_ind_energy_bill ,0)+\n" + " COALESCE(lt_com_energy_bill ,0)+\n"
					+ " COALESCE(lt_dom_energy_bill ,0)+\n" + " COALESCE(govt_energy_bill ,0)+\n"
					+ " COALESCE(agri_energy_bill ,0)+\n" + " COALESCE(hut_energy_billed ,0)+\n"
					+ " COALESCE(other_energy_bill ,0))   END)  AS total_lt_unit_billed,\n" + "  \n"
					+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ "COALESCE(ht_ind_amount_bill	,0)+\n"
					+ " COALESCE(ht_com_amount_bill	,0))   END)  AS total_ht_amount_billed ,\n" + " \n"
					+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ "COALESCE(lt_ind_amount_bill ,0)+\n" + " COALESCE(lt_com_amount_bill ,0)+\n"
					+ " COALESCE(lt_dom_amount_bill ,0)+\n" + " COALESCE(govt_amount_bill ,0)+\n"
					+ " COALESCE(agri_amount_bill ,0)+\n" + " COALESCE(hut_amount_billed ,0)+\n"
					+ " COALESCE(other_amount_bill ,0))   END)  AS total_lt_amount_billed,\n" + " \n"
					+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ "COALESCE(ht_ind_amount_collected	,0)+\n"
					+ " COALESCE(ht_com_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" + " \n"
					+ " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ "COALESCE(lt_ind_amount_collected ,0)+\n" + " COALESCE(lt_com_amount_collected ,0)+\n"
					+ " COALESCE(lt_dom_amount_collected ,0)+\n" + " COALESCE(govt_amount_collected ,0)+\n"
					+ " COALESCE(agri_amount_collected ,0)+\n" + " COALESCE(hut_amount_collected ,0)+\n"
					+ " COALESCE(other_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" + " \n"
					+ "SUM(distinct CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" 
					+ "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" 
					+ "- INTERVAL '01 MONTH'),'YYYYMM') AS numeric))   THEN  (\n"
					+ "COALESCE(lt_ind_con_count ,0)+\n" + "COALESCE(lt_com_con_count ,0)+\n"
					+ "COALESCE(lt_dom_con_count ,0)+\n" + "COALESCE(govt_con_count ,0)+\n"
					+ "COALESCE(agri_con_count ,0)+\n" + "COALESCE(hut_consumer_count ,0)+\n"
					+ "COALESCE(other_con_count ,0))   END)  AS total_lt_consumer_count,\n" + "  \n"
					+ "SUM(distinct CASE WHEN (CAST (monthyear as NUMERIC)>=\n" + "cast(to_char((TO_DATE(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" + "- INTERVAL '"
					+ Monthsinterval
					+ "'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
					+ "COALESCE(ht_ind_con_count	,0)+\n"
					+ "COALESCE(ht_com_con_count	,0))   END)  AS total_ht_consumer_cocount\n"
					+ "from meter_data.npp_data_monthly_calculation dc\n"
					+ " LEFT JOIN meter_data.feederdetails fd on fd.fdr_id =  dc.feeder_code WHERE tp_town_code like '%'  and boundry_feeder = 'false' and fd.feeder_type = 'IPDS'  and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('"
					+ reportMonth
					+ "', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric) GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,fd.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername)Z\n"
					+ " LEFT JOIN(\n"
					+ "Select mtrno,SUM(energy_supplied)as total_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n"
					+ "SUM(kwh_exp)as total_export_energy_supplied\n" + "from (\n"
					+ "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\n"
					+ "--(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as energy_supplied  from (\n"
					+ "(kwh_imp) as energy_supplied  from (\n"
					+ " Select distinct kwh_imp ,mc.kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  meter_data.monthly_consumption mc  \n"
					+ " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and mc.billmonth >=cast(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '" + energySuppliedInterval
					+ "','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('" + reportMonth
					+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno GROUP BY feeder_code, feeder_type,\n"
					+ "tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied,boundary_name,total_export_energy_supplied,feedername,total_import_energy_supplied ORDER BY feeder_code)maind\n"
					+ "LEFT JOIN \n"
					+ "(Select (coalesce(mainfeeder_unit_supply,0) - coalesce(mainboundary_unit_supply,0)) as main_unit_supply,unit_tp_fdr_id from (\n"
					+ " Select Max(CASE WHEN boundry_feeder  is false  THEN main_unit_supply END )as  mainfeeder_unit_supply,\n"
					+ " max(CASE WHEN boundry_feeder  is true  THEN main_unit_supply END )as  mainboundary_unit_supply,unit_tp_fdr_id from(\n"
					+ " Select SUM(Dtotal_energy_supplied) as main_unit_supply ,tp_fdr_id as unit_tp_fdr_id,boundry_feeder\n"
					+ "from (\n"
					+ "Select distinct mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n"
					+ "SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\n" + "from (\n"
					+ "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\n"
					+ "(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from (\n"
					+ "--(kwh_imp) as energy_supplied  from (\n"
					+ " Select kwh_imp ,mc.kwh_exp, mtrno,billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.monthly_consumption  mc  \n"
					+ " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and mc.billmonth >=cast(to_char(to_date('"
					+ reportMonth + "', 'YYYYMM') - INTERVAL '" + energySuppliedInterval + "','YYYYMM') AS numeric)"
					+ " AND mc.billmonth <cast(to_char(to_date('" + reportMonth
					+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id)za GROUP BY tp_fdr_id,za.boundry_feeder )xxx  "
					+ " GROUP BY unit_tp_fdr_id ORDER BY unit_tp_fdr_id  )xxxx)units  on units.unit_tp_fdr_id= maind.tp_feeder_code)";*/
					
					
					
		/*	String qry="insert into meter_data." + TableName + "  (\r\n" + 
					"month_year,office_id,fdr_id,tp_fdr_id,fdr_name,meter_sr_number,\r\n" + 
					"unit_billed,amt_billed,amt_collected ,time_stamp,\r\n" + 
					"town_code,boundary_feeder,feeder_import_energy,unit_supply,tot_consumers)\r\n" + 
					"\r\n" + 
					"(select distinct pm.*,sum(distinct cm.ht_ind_con_count+cm.ht_com_con_count+cm.lt_ind_con_count+cm.lt_com_con_count+cm.agri_con_count+cm.govt_con_count+cm.lt_dom_con_count+cm.other_con_count+cm.hut_consumer_count) as total_consumer_count from \r\n" + 
					"(\r\n" + 
					"Select distinct  maind.*, main_unit_supply\r\n" + 
					"from \r\n" + 
					"(\r\n" + 
					"Select CAST ('"+ reportMonth +"' as NUMERIC) report_month,  CAST (officeid as NUMERIC) officeid,\r\n" + 
					"feeder_code,tp_feeder_code,feedername,meterno,\r\n" + 
					"SUM(total_ht_unit_billed + total_lt_unit_billed )as total_units_billed,\r\n" + 
					"SUM(total_ht_amount_billed + total_lt_amount_billed )as total_amount_billed,\r\n" + 
					"SUM (total_ht_amount_collected + total_lt_amount_collected) as total_amount_collected,now()\r\n" + 
					",towncode,boundry_feeder,total_import_energy_supplied\r\n" + 
					"from (Select feeder_code,fd.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,feedername,\r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(ht_ind_energy_bill ,0)+\r\n" + 
					" COALESCE(ht_com_energy_bill ,0))    END)  AS total_ht_unit_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					" COALESCE(lt_ind_energy_bill ,0)+\r\n" + 
					" COALESCE(lt_com_energy_bill ,0)+\r\n" + 
					" COALESCE(lt_dom_energy_bill ,0)+\r\n" + 
					" COALESCE(govt_energy_bill ,0)+\r\n" + 
					" COALESCE(agri_energy_bill ,0)+\r\n" + 
					" COALESCE(hut_energy_billed ,0)+\r\n" + 
					" COALESCE(other_energy_bill ,0))   END)  AS total_lt_unit_billed,\r\n" + 
					"  \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_ind_amount_bill	,0)+\r\n" + 
					" COALESCE(ht_com_amount_bill	,0))   END)  AS total_ht_amount_billed ,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_ind_amount_bill ,0)+\r\n" + 
					" COALESCE(lt_com_amount_bill ,0)+\r\n" + 
					" COALESCE(lt_dom_amount_bill ,0)+\r\n" + 
					" COALESCE(govt_amount_bill ,0)+\r\n" + 
					" COALESCE(agri_amount_bill ,0)+\r\n" + 
					" COALESCE(hut_amount_billed ,0)+\r\n" + 
					" COALESCE(other_amount_bill ,0))   END)  AS total_lt_amount_billed,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_ind_amount_collected	,0)+\r\n" + 
					" COALESCE(ht_com_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n" + 
					" \r\n" + 
					" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_ind_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_com_amount_collected ,0)+\r\n" + 
					" COALESCE(lt_dom_amount_collected ,0)+\r\n" + 
					" COALESCE(govt_amount_collected ,0)+\r\n" + 
					" COALESCE(agri_amount_collected ,0)+\r\n" + 
					" COALESCE(hut_amount_collected ,0)+\r\n" + 
					" COALESCE(other_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n" + 
					" \r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(lt_ind_con_count ,0)+\r\n" + 
					"COALESCE(lt_com_con_count ,0)+\r\n" + 
					"COALESCE(lt_dom_con_count ,0)+\r\n" + 
					"COALESCE(govt_con_count ,0)+\r\n" + 
					"COALESCE(agri_con_count ,0)+\r\n" + 
					"COALESCE(hut_consumer_count ,0)+\r\n" + 
					"COALESCE(other_con_count ,0))   END)  AS total_lt_consumer_count,\r\n" + 
					"  \r\n" + 
					"SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
					"cast(to_char((TO_DATE(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
					"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
					"COALESCE(ht_ind_con_count	,0)+\r\n" + 
					"COALESCE(ht_com_con_count	,0))   END)  AS total_ht_consumer_cocount\r\n" + 
					"from meter_data.npp_data_monthly_calculation dc\r\n" + 
					" LEFT JOIN meter_data.feederdetails fd on fd.fdr_id =  dc.feeder_code WHERE tp_town_code like '%'  and boundry_feeder = 'false' and fd.feeder_type = 'IPDS'  and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+Monthsinterval+"','YYYYMM') AS numeric) GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,fd.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername)Z\r\n" + 
					" LEFT JOIN(\r\n" + 
					"Select mtrno,SUM(energy_supplied)as total_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\r\n" + 
					"SUM(kwh_exp)as total_export_energy_supplied\r\n" + 
					"from (\r\n" + 
					"Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\r\n" + 
					"--(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as energy_supplied  from (\r\n" + 
					"(kwh_imp) as energy_supplied  from (\r\n" + 
					" Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  meter_data.monthly_consumption mc  \r\n" + 
					" left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS'  and mc.billmonth >=cast(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '"+ energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno GROUP BY feeder_code, feeder_type,\r\n" + 
					"tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied,boundary_name,total_export_energy_supplied,feedername,total_import_energy_supplied ORDER BY feeder_code)maind\r\n" + 
					"LEFT JOIN \r\n" + 
					"(Select (coalesce(mainfeeder_unit_supply,0) - coalesce(mainboundary_unit_supply,0)) as main_unit_supply,unit_tp_fdr_id from (\r\n" + 
					" Select Max(CASE WHEN boundry_feeder  is false  THEN main_unit_supply END )as  mainfeeder_unit_supply,\r\n" + 
					" max(CASE WHEN boundry_feeder  is true  THEN main_unit_supply END )as  mainboundary_unit_supply,unit_tp_fdr_id from(\r\n" + 
					" Select SUM(Dtotal_energy_supplied) as main_unit_supply ,tp_fdr_id as unit_tp_fdr_id,boundry_feeder\r\n" + 
					"from (\r\n" + 
					"Select mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\r\n" + 
					"SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\r\n" + 
					"from (\r\n" + 
					"Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\r\n" + 
					"(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from (\r\n" + 
					"--(kwh_imp) as energy_supplied  from (\r\n" + 
					" Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.monthly_consumption  mc  \r\n" + 
					" left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and fd.meterno='' and mc.billmonth >=cast(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"+ reportMonth +"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id)za GROUP BY tp_fdr_id,za.boundry_feeder )xxx   GROUP BY unit_tp_fdr_id ORDER BY unit_tp_fdr_id  )xxxx)units  on units.unit_tp_fdr_id= maind.tp_feeder_code LEFT JOIN meter_data.npp_data_monthly_calculation mcm ON (maind.meterno=mcm.mtrno) where maind.meterno='X0857643')pm LEFT JOIN \r\n" + 
					" \r\n" + 
					" (select DISTINCT fd.meterno,ea.ht_ind_con_count,ea.ht_com_con_count,ea.lt_ind_con_count,ea.lt_com_con_count,ea.agri_con_count,ea.govt_con_count,ea.lt_dom_con_count,ea.other_con_count,ea.hut_consumer_count  from meter_data.feederdetails fd left JOIN meter_data.npp_data_monthly_calculation ea ON (fd.tp_fdr_id=ea.tp_feeder_code) where  monthyear='"+ reportMonth +"' GROUP BY  fd.meterno,ea.ht_ind_con_count,ea.ht_com_con_count,ea.lt_ind_con_count,ea.lt_com_con_count,ea.agri_con_count,ea.govt_con_count,ea.lt_dom_con_count,ea.other_con_count,ea.hut_consumer_count)cm ON (pm.meterno=cm.meterno) GROUP BY pm.report_month,pm.officeid,pm.feeder_code,pm.tp_feeder_code,pm.feedername,pm.meterno,pm.total_units_billed,pm.total_amount_billed,pm.total_amount_collected,pm.now,pm.towncode,pm.boundry_feeder,pm.total_import_energy_supplied,pm.main_unit_supply)";*/
			/*
			 * "(Select SUM(Dtotal_energy_supplied) as main_unit_supply ,tp_fdr_id as unit_tp_fdr_id\n"
			 * + "from (\n" +
			 * "Select mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n"
			 * + "SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\n" +
			 * "from (\n" +
			 * "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\n" +
			 * "(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from (\n"
			 * + "--(kwh_imp) as energy_supplied  from (\n" +
			 * " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.monthly_consumption  mc  \n"
			 * +
			 * " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and mc.billmonth >=cast(to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '"
			 * +energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"
			 * +reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id)za GROUP BY tp_fdr_id) units \n"
			 * + "  on units.unit_tp_fdr_id= maind.tp_feeder_code\n" + ")" ;
			 * 
			 */
					
					
					String qry=" insert into meter_data."+TableName+"  (\r\n" + 
							"month_year,office_id,fdr_id,tp_fdr_id,fdr_name,meter_sr_number,tot_consumers,\r\n" + 
							"unit_billed,amt_billed,amt_collected,time_stamp,\r\n" + 
							"town_code,boundary_feeder,feeder_import_energy,unit_supply)\r\n" + 
							"(\r\n" + 
							"Select maind.*, main_unit_supply\r\n" + 
							"from \r\n" + 
							"(\r\n" + 
							"Select DISTINCT CAST ('"+reportMonth+"' as NUMERIC) report_month,  CAST (officeid as NUMERIC) officeid,\r\n" + 
							"feeder_code,tp_feeder_code,feedername,meterno,\r\n" + 
							"SUM(total_lt_consumer_count + total_ht_consumer_cocount )as total_consumer_count,\r\n" + 
							"SUM(total_ht_unit_billed + total_lt_unit_billed )as total_units_billed,\r\n" + 
							"SUM(total_ht_amount_billed + total_lt_amount_billed )as total_amount_billed,\r\n" + 
							"SUM (total_ht_amount_collected + total_lt_amount_collected) as total_amount_collected,now()\r\n" + 
							",towncode,xv.boundry_feeder,\r\n" + 
							"total_import_energy_supplied\r\n" + 
							"from (Select DISTINCT feeder_code,fd.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,fd.feedername,\r\n" + 
							"SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							" COALESCE(ht_ind_energy_bill ,0)+\r\n" + 
							" COALESCE(ht_com_energy_bill ,0))    END)  AS total_ht_unit_billed,\r\n" + 
							" \r\n" + 
							" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							" COALESCE(lt_ind_energy_bill ,0)+\r\n" + 
							" COALESCE(lt_com_energy_bill ,0)+\r\n" + 
							" COALESCE(lt_dom_energy_bill ,0)+\r\n" + 
							" COALESCE(govt_energy_bill ,0)+\r\n" + 
							" COALESCE(agri_energy_bill ,0)+\r\n" + 
							" COALESCE(hut_energy_billed ,0)+\r\n" + 
							" COALESCE(other_energy_bill ,0))   END)  AS total_lt_unit_billed,\r\n" + 
							"  \r\n" + 
							" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							"COALESCE(ht_ind_amount_bill	,0)+\r\n" + 
							" COALESCE(ht_com_amount_bill	,0))   END)  AS total_ht_amount_billed,\r\n" + 
							" \r\n" + 
							" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							"COALESCE(lt_ind_amount_bill ,0)+\r\n" + 
							" COALESCE(lt_com_amount_bill ,0)+\r\n" + 
							" COALESCE(lt_dom_amount_bill ,0)+\r\n" + 
							" COALESCE(govt_amount_bill ,0)+\r\n" + 
							" COALESCE(agri_amount_bill ,0)+\r\n" + 
							" COALESCE(hut_amount_billed ,0)+\r\n" + 
							" COALESCE(other_amount_bill ,0))   END)  AS total_lt_amount_billed,\r\n" + 
							" \r\n" + 
							" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							"COALESCE(ht_ind_amount_collected	,0)+\r\n" + 
							" COALESCE(ht_com_amount_collected	,0))   END)  AS total_ht_amount_collected,\r\n" + 
							" \r\n" + 
							" SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							"COALESCE(lt_ind_amount_collected ,0)+\r\n" + 
							" COALESCE(lt_com_amount_collected ,0)+\r\n" + 
							" COALESCE(lt_dom_amount_collected ,0)+\r\n" + 
							" COALESCE(govt_amount_collected ,0)+\r\n" + 
							" COALESCE(agri_amount_collected ,0)+\r\n" + 
							" COALESCE(hut_amount_collected ,0)+\r\n" + 
							" COALESCE(other_amount_collected ,0))   END)  AS total_lt_amount_collected,\r\n" + 
							" \r\n" + 
							"SUM(distinct CASE WHEN (CAST (monthyear as NUMERIC)=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '02 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric))   THEN  (\r\n" + 
							"COALESCE(lt_ind_con_count ,0)+\r\n" + 
							"COALESCE(lt_com_con_count ,0)+\r\n" + 
							"COALESCE(lt_dom_con_count ,0)+\r\n" + 
							"COALESCE(govt_con_count ,0)+\r\n" + 
							"COALESCE(agri_con_count ,0)+\r\n" + 
							"COALESCE(hut_consumer_count ,0)+\r\n" + 
							"COALESCE(other_con_count ,0))   END)  AS total_lt_consumer_count,\r\n" + 
							"  \r\n" + 
							"SUM(distinct CASE WHEN (CAST (monthyear as NUMERIC)>=\r\n" + 
							"cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\r\n" + 
							"- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\r\n" + 
							"COALESCE(ht_ind_con_count	,0)+\r\n" + 
							"COALESCE(ht_com_con_count	,0))   END)  AS total_ht_consumer_cocount\r\n" + 
							"from meter_data.npp_data_monthly_calculation dc\r\n" + 
							" LEFT JOIN meter_data.feederdetails fd on (fd.tp_fdr_id =  dc.tp_feeder_code) WHERE tp_town_code like '%'   and boundry_feeder = 'false' and fd.feeder_type = 'IPDS'  and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric)  GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,fd.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername)Z\r\n" + 
							" LEFT JOIN (\r\n" + 
							"Select distinct mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\r\n" + 
							"SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\r\n" + 
							"from (\r\n" + 
							"Select distinct kwh_imp,kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\r\n" + 
							"(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from  (select distinct ak.kwh_imp,mp.kwh_exp,ak.mtrno,ak.billmonth,ak.boundry_feeder,ak.tp_fdr_id from \r\n" + 
							"(\r\n" + 
							" Select distinct mc.kwh_imp,mc.mtrno,mc.billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.input_energy mc  \r\n" + 
							" left join meter_data.feederdetails fd on fd.meterno = mc.mtrno where fd.feeder_type = 'IPDS' and cast(mc.billmonth as numeric) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND cast(mc.billmonth as numeric)  <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric))ak,\r\n" + 
							" (select kwh_exp,mtrno from meter_data.monthly_consumption)mp where  (ak.mtrno=mp.mtrno) and mp.kwh_exp is not null) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id) xv on Z.meterno = xv.mtrno GROUP BY feeder_code, feeder_type,\r\n" + 
							"tp_feeder_code,towncode,officeid,meterno,xv.boundry_feeder,xv.dtotal_energy_supplied,boundary_name,total_export_energy_supplied,feedername,total_import_energy_supplied ORDER BY feeder_code)maind\r\n" + 
							"LEFT JOIN \r\n" + 
							"(Select (coalesce(mainfeeder_unit_supply,0) - coalesce(mainboundary_unit_supply,0)) as main_unit_supply,unit_tp_fdr_id from (\r\n" + 
							" Select Max(CASE WHEN boundry_feeder  is false  THEN main_unit_supply END )as  mainfeeder_unit_supply,\r\n" + 
							" max(CASE WHEN boundry_feeder  is true  THEN main_unit_supply END )as  mainboundary_unit_supply,unit_tp_fdr_id from(\r\n" + 
							" Select SUM(Dtotal_energy_supplied) as main_unit_supply ,tp_fdr_id as unit_tp_fdr_id,boundry_feeder\r\n" + 
							"from  (\r\n" + 
							"Select distinct mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\r\n" + 
							"SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\r\n" + 
							"from (\r\n" + 
							"Select distinct kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\r\n" + 
							"(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from  (select distinct ak.kwh_imp,mp.kwh_exp,ak.mtrno,ak.billmonth,ak.boundry_feeder,ak.tp_fdr_id from \r\n" + 
							"(\r\n" + 
							" Select distinct mc.kwh_imp,mc.mtrno,mc.billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.input_energy mc  \r\n" + 
							" left join meter_data.feederdetails fd on fd.meterno = mc.mtrno where fd.feeder_type = 'IPDS' and cast(mc.billmonth as numeric) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND cast(mc.billmonth as numeric)  <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric))ak,\r\n" + 
							" (select kwh_exp,mtrno from meter_data.monthly_consumption)mp where  (ak.mtrno=mp.mtrno)  and mp.kwh_exp is not null) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id)za GROUP BY tp_fdr_id,za.boundry_feeder )xxx   GROUP BY unit_tp_fdr_id ORDER BY unit_tp_fdr_id  )xxxx)units  on units.unit_tp_fdr_id= maind.tp_feeder_code)";
			
		
			System.out.println("Main Feeder Push Query ---> " + qry);

			int x = 0;

			try {
				x = entityManager.createNativeQuery(qry).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return msg;

	}

	@Override
	public String getAfterMonthlyCOnsumtionFeederTownEADataPushForBoundaryr(String monthYear) {

		String msg = null;

		String reportMonth = monthYear;
		// String town_code = towncode;
		String Monthsinterval = null;
		String TableName = null;
		String energySuppliedInterval = null;

		for (int i = 12; i >= 0; i -= 2) {

			System.out.println("This is I " + i);

			if (i == 12) {
				Monthsinterval = "11 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_12months";
				energySuppliedInterval = "14 MONTH";

			}

			if (i == 10) {
				Monthsinterval = "09 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_10months";
				energySuppliedInterval = "12 MONTH";

			}

			if (i == 8) {
				Monthsinterval = "07 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_08months";
				energySuppliedInterval = "10 MONTH";

			}

			if (i == 6) {
				Monthsinterval = "05 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_06months";
				energySuppliedInterval = "08 MONTH";

			}

			if (i == 4) {
				Monthsinterval = "03 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_04months";
				energySuppliedInterval = "06 MONTH";

			}

			if (i == 2) {
				Monthsinterval = "01 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_02months";
				energySuppliedInterval = "04 MONTH";

			}

			if (i == 0) {
				Monthsinterval = "00 MONTH";
				TableName = "rpt_eaboundaryfeeder_losses_00months";
				energySuppliedInterval = "03 MONTH";

			}

			String qry = "insert into meter_data." + TableName + " (\n"
					+ "feeder_code,feeder_name,feeder_tp_id,meterno,boundary_feeder_id,\n"
					+ "boundary_feeder_name,boundary_feeder_meterno,billmonth,feeder_import_energy,\n"
					+ "boundary_import_energy,boundary_export_energy)\n"
					+ "(Select distinct on (meterno)  feeder_code,feedername,tp_feeder_code,meterno,boundary_id,boundary_name,meterno,\n"
					+ "  CAST ('" + reportMonth + "' as NUMERIC) report_month,  \n" + "total_energy_supplied,\n"
					+ "total_import_energy_supplied,total_export_energy_supplied\n"
					+ "from (Select feeder_code,dc.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,feedername,\n"
					+ "boundary_id\n" + "from meter_data.npp_data_monthly_calculation dc\n"
					+ " LEFT JOIN meter_data.feederdetails fd on fd.tp_fdr_id =  dc.tp_feeder_code WHERE tp_town_code like '%' and boundry_feeder = 'true' "
					+ "and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('" + reportMonth
					+ "', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric) "
					+ "GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,dc.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername,boundary_id)Z\n"
					+ " LEFT JOIN(\n"
					+ "Select mtrno,SUM(energy_supplied)as total_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n"
					+ "SUM(kwh_exp)as total_export_energy_supplied\n" + "from (\n"
					+ "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\n"
					+ "(kwh_imp) as energy_supplied  from (\n"
					+ " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  meter_data.monthly_consumption mc  \n"
					+ " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and boundry_feeder = 'true'"
					+ " and mc.billmonth >=cast(to_char(to_date('" + reportMonth + "', 'YYYYMM') - INTERVAL '"
					+ energySuppliedInterval + "','YYYYMM') AS numeric)" + " AND mc.billmonth <cast(to_char(to_date('"
					+ reportMonth
					+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno "
					+ "GROUP BY feeder_code, feeder_type,\n"
					+ "tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied,boundary_name,total_export_energy_supplied,boundary_id,feedername,"
					+ "total_import_energy_supplied ORDER BY meterno,feeder_code)";

			System.out.println("Boundary Feeder Push Query ---> " + qry);

			int x = 0;

			try {
				x = entityManager.createNativeQuery(qry).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return msg;

	}

	
}
