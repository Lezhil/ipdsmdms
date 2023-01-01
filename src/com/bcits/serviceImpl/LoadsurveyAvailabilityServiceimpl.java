package com.bcits.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.LoadAvailabilityReportEntity;
import com.bcits.entity.LoadAvailabilityReportEntity.KeyLoadAvl;
import com.bcits.service.LoadAvailabilityRptService;
@Repository
public class LoadsurveyAvailabilityServiceimpl extends GenericServiceImpl<LoadAvailabilityReportEntity> implements LoadAvailabilityRptService {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LoadAvailabilityReportEntity> getDistinctZone() {
	
			List<LoadAvailabilityReportEntity> list = null;
			try {
				String sql = "SELECT DISTINCT zone from meter_data.load_availability";
				// String sql="SELECT DISTINCT zone from meter_data.master where zone not like
				// ''";
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getCircleByZone(String zone, ModelMap model) {
		List<LoadAvailabilityReportEntity> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.load_availability WHERE zone like '" + zone + "'";
			// String sql="SELECT DISTINCT circle from meter_data.master WHERE zone like
			// '"+zone+"' and circle not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getDivisionByCircle(String zone, String circle, ModelMap model) {
		List<LoadAvailabilityReportEntity> list = null;
		try {
			
			String sql = "SELECT DISTINCT division from meter_data.load_availability WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "'";
			// String sql="SELECT DISTINCT division from meter_data.master WHERE zone like
			// '"+zone+"' and "
			// + "circle LIKE '"+circle+"' and division not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getSubdivByDivisionByCircle(String zone, String circle, String division, ModelMap model) {
		List<LoadAvailabilityReportEntity> list = null;
		try {
			String sql = "SELECT DISTINCT subdivision from meter_data.load_availability WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "' and division like '" + division + "'";
			// String sql="SELECT DISTINCT subdivision from meter_data.master WHERE zone
			// like '"+zone+"' and circle LIKE '"+circle+"' "
			// + "and division like '"+division+"' and subdivision not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getloadavailabilityreport(String zone,String circle,String division,String subdiv, String fDate, String tDate) {
		List<?> reports1=new ArrayList<>();
		try {
			
			if(zone.equalsIgnoreCase("All"))
			{
				zone="%";
			}
			if(circle.equalsIgnoreCase("All"))
			{
				circle="%";
			}
			if(division.equalsIgnoreCase("All"))
			{
				division="%";
			}
			if(subdiv.equalsIgnoreCase("All"))
			{
				subdiv="%";
			}
			
			String qry="SELECT * FROM meter_data.load_availability WHERE date>='"+fDate+"' AND date<='"+tDate+"' AND zone like '"+zone+"' AND division like '"+division+"' AND subdivision like'"+subdiv+"' AND circle like '"+circle+"'" + 
					"ORDER BY zone,circle,division,subdivision, substation,mtrno,date";
			
			System.out.println(qry);
			
			reports1 = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reports1;
	}

	@Override
	public List<?> getLoadSummaryReport(String fromDate, String toDate) {
		List<?> reports2=new ArrayList<>();
		try {
			String sql="SELECT to_char(A.ldate, 'DD-MM-YYYY'), count(CASE WHEN zone='DHBVN' THEN 1 END) as dh_total, \n" +
					"count(CASE WHEN zone='UHBVN' THEN 1 END) as uh_total,\n" +
					"count(CASE WHEN zone='UHBVN' OR zone='DHBVN' THEN 1 END) as h_total,\n" +
					"count(CASE WHEN zone='HP' THEN 1 END) as hp_total, count(CASE WHEN zone is NULL THEN 1 END) as oth_total,\n" +
					"count(CASE WHEN (zone='DHBVN' AND (lcount=48 OR lcount=96)) THEN 1 END) as dh_full, \n" +
					"count(CASE WHEN (zone='UHBVN' AND (lcount=48 OR lcount=96)) THEN 1 END) as uh_full,\n" +
					"count(CASE WHEN ((zone='DHBVN' OR zone='UHBVN') AND (lcount=48 OR lcount=96)) THEN 1 END) as h_full,\n" +
					"count(CASE WHEN (zone='HP' AND (lcount=48 OR lcount=96)) THEN 1 END) as hp_full, \n" +
					"count(CASE WHEN (zone is NULL AND (lcount=48 OR lcount=96)) THEN 1 END) as oth_full\n" +
					"FROM ( SELECT m.zone,x.* FROM meter_data.master_main m RIGHT JOIN ( SELECT meter_number,date(read_time) as ldate, count(*) as lcount\n" +
					"FROM meter_data.load_survey WHERE date(read_time)>='"+fromDate+"' AND date(read_time)<='"+toDate+"' AND read_time is not NULL\n" +
					"GROUP BY meter_number, date(read_time) ORDER BY date(read_time))X on m.mtrno=X.meter_number )A GROUP BY A.ldate ORDER BY A.ldate";
			
			
			System.out.println(sql);
			reports2=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
		} catch (Exception e) {
			
		}
		
		return reports2;
	}


		@Override
		public List<?> executeSelectQueryRrnList(String qry) {
	
				try {
					return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
		
		}

}
