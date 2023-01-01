package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.Location;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.AmiLocation;
import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.service.HierarchyService;

@Repository
public class HierarchyServiceImpl extends GenericServiceImpl<AmiLocation> implements HierarchyService{

	@Override
	public List<AmiLocation> getALLLocationData()
	{
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllData").getResultList();
		return list;
	}

	@Override
	public List<AmiLocation> getdistinctZones() {
		@SuppressWarnings("unchecked")
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllExistingZones").getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AmiLocation> getAllExistingCircles(String zone)
	{
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllExistingCircles").setParameter("zone", zone).getResultList();
		return list;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<AmiLocation> getAlldivisionsUnderCircle(String zone, String circle) {
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllExistingDivisionsUnderCircle").setParameter("zone", zone).setParameter("circle", circle).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmiLocation> getAllsubDivisionsUnderCircle(String zone,
			String circle, String division) {
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllExistingSubDivisionsUnderDivision").setParameter("zone", zone).setParameter("circle", circle).setParameter("division", division).getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AmiLocation> getTownsUnderSubdivisions(String zone,
			String circle, String division,String subdiv) {
		List<AmiLocation> list=postgresMdas.createNamedQuery("AmiLocation.getAllExistingTownUnderSubDivision").setParameter("zone", zone).setParameter("circle", circle)
				.setParameter("division", division)
				.setParameter("subDivision", subdiv)
				.getResultList();
		return list;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<AmiLocation> getSectionUnderTown(String zone, String circle, String division, String subdiv,
			String town) {
		
		List<AmiLocation> list = postgresMdas.createNamedQuery("AmiLocation.getAllExistingSectionUnderTown").setParameter("zone",zone).setParameter("circle",circle)
				.setParameter("division",division)
				.setParameter("subDivision",subdiv)
				.setParameter("town_ipds",town)
				.getResultList();
		return list;
	}

	

	@Override
	public int checkingZone(String zone)
	{
		List lst=null;
		try
		{
			String sql="SELECT * FROM meter_data.amilocation WHERE tp_zonecode LIKE '"+zone+"'";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			System.out.println(sql);
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		
		
	}
	
	
	@Override
	public int createZoneId()
	{
		int id=0;
		try
		{
			String sql="SELECT CASE WHEN LENGTH ( B.ZONEID ) = 2 THEN B.ZONEID ||'00000' \n" +
					"ELSE B.ZONEID END from(\n" +
					"SELECT CAST(CAST(substr(CAST(A.zone_code AS TEXT),0,3)AS INTEGER )+ 1 AS TEXT) AS ZONEID FROM\n" +
					"( SELECT COALESCE ( MAX(zone_code), '0000000' )  AS zone_code FROM meter_data.amilocation) A  )B";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			return id;
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	
	}

	@Override
	public int checkingCircle(String zoneVal, String circle) 
	{
		List lst=null;
		try
		{
			String sql="SELECT * FROM meter_data.amilocation WHERE upper(tp_zonecode) LIKE upper('"+zoneVal+"') AND upper(tp_circlecode) LIKE upper('"+circle+"')";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getCircleUpdatingId(String zone, String circle) 
	{
		
		int id=0;
		try
		{
			String sql ="SELECT id FROM  meter_data.amilocation WHERE upper(zone) =upper('"+zone+"') AND circle is NULL AND division is NULL AND subdivision is NULL";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			System.out.println(sql);
			System.out.println("======>"+id);
			if(id==0)
			{
				return 0;
			}else
			{
				return id;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int checkingDivision(String zoneVal, String circle, String division) {
		List lst=null;
		try
		{
			String sql="SELECT * FROM meter_data.amilocation WHERE upper(tp_zonecode) LIKE upper('"+zoneVal+"') AND tp_circlecode LIKE '"+circle+"' AND tp_divcode LIKE '"+division+"'";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getdivisionUpdatingId(String zoneVal, String circle,String division) {
		
		int id=0;
		try
		{
			String sql ="SELECT id FROM  meter_data.amilocation WHERE upper(zone) =upper('"+zoneVal+"') AND upper(circle) = upper('"+circle+"') AND division is NULL AND subdivision is NULL";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			System.out.println(sql);
			System.out.println("======>"+id);
			if(id==0)
			{
				return 0;
			}else
			{
				return id;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int checkingSubDivision(String zoneVal, String circle,
			String division, String subdiv) {
		List lst=null;
		try
		{
			String sql="SELECT * FROM meter_data.amilocation WHERE upper(tp_zonecode) LIKE upper('"+zoneVal+"') AND tp_circlecode LIKE '"+circle+"' AND tp_divcode LIKE '"+division+"' AND tp_subdivcode LIKE '"+subdiv+"'";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getSubdivisionUpdatingId(String zoneVal, String circle,String division, String subdiv) {
		
		int id=0;
		try
		{
			String sql ="SELECT id FROM  meter_data.amilocation WHERE upper(zone) =upper('"+zoneVal+"') AND upper(circle) = upper('"+circle+"') AND upper(division)=upper('"+division+"') AND subdivision is NULL";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			System.out.println(sql);
			System.out.println("======>"+id);
			if(id==0)
			{
				return 0;
			}else
			{
				return id;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Override
	public int getTownUpdatingId(String zoneVal, String circle,String division, String subdiv,String town) {
		
		int id=0;
		try
		{
			String sql ="SELECT id FROM  meter_data.amilocation WHERE upper(zone) =upper('"+zoneVal+"') AND upper(circle) = upper('"+circle+"') AND upper(division)=upper('"+division+"') AND upper(subdivision)=upper('"+subdiv+"') AND town_ipds is NULL";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			System.out.println(sql);
			System.out.println("======>"+id);
			if(id==0)
			{
				return 0;
			}else
			{
				return id;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	//section
	@Override
	public int getSectionUpdatingId(String zoneVal, String circle, String division, String subdiv, String town,
			String section) {
		int id=0;
		try
		{
			String sql ="SELECT id FROM  meter_data.amilocation WHERE upper(zone) =upper('"+zoneVal+"') AND upper(circle) = upper('"+circle+"') AND upper(division)=upper('"+division+"') AND upper(subdivision)=upper('"+subdiv+"') AND upper(town_ipds)=upper('"+town+"') AND section is NULL";
			
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			System.out.println(sql);
			System.out.println("======>"+id);
			if(id==0)
			{
				return 0;
			}else
			{
				return id;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
//	@Override
//	public AmiLocation findZoneDetailsByZone(String zoneVal) {
//		
//		try{
//			return postgresMdas.createNamedQuery("AmiLocation.findZoneDetailsByZone", AmiLocation.class).setParameter("zone", zoneVal).getSingleResult();
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	@Override
	public Object findZoneDetailsByZone(String zoneVal) {	
		
		Object result=new ArrayList<AmiLocation>();
		try {

			String qry="select distinct tp_zonecode,ZONE,zone_code from meter_data.amilocation where upper(zone) like upper('"+zoneVal+"')";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
		
	}

	@Override
	public Object findCircleDetailsByZone(String zoneVal, String circle) {
		
		Object result=new ArrayList<AmiLocation>();
		try {

			String qry="select distinct tp_zonecode,ZONE,zone_code,tp_circlecode,circle,circle_code from meter_data.amilocation where upper(zone) like upper('"+zoneVal+"') and upper(circle) like upper('"+circle+"') ";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int createDivId(String circleCode)
	{
		int id=0;
		try
		{
			String sql="SELECT CASE WHEN LENGTH ( B.DIVID ) = 1 THEN B.DIVID ||'00' \n" +
					"ELSE B.DIVID END from(\n" +
					"SELECT CASE WHEN LENGTH ( CAST(A.division_code  AS TEXT)) = 3 THEN \n" +
					"CAST(CAST(substr(CAST(A.division_code AS TEXT),3,4)AS INTEGER )+ 1 AS TEXT)  \n" +
					"ELSE CAST(CAST(substr(CAST(A.division_code AS TEXT),5,1)AS INTEGER )+ 1 AS TEXT)   END AS DIVID \n" +
					"FROM\n" +
					"( SELECT COALESCE ( MAX(division_code), '0000100' )  AS division_code FROM meter_data.amilocation where circle_code='"+circleCode+"') A  )B";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			return id;
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	
	}
	
	@Override
	public Object findDivDetailsByZone(String zoneVal, String circle,String division) {
		
		Object result=new ArrayList<AmiLocation>();
		try {

			String qry="select distinct tp_zonecode,ZONE,zone_code,tp_circlecode,circle,circle_code,tp_divcode,division,division_code from meter_data.amilocation where upper(zone) like upper('"+zoneVal+"') and upper(circle) like upper('"+circle+"') and upper(division) like upper('"+division+"')  ";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int createSubDivId(String divCode)
	{
		int id=0;
		try
		{
			String sql="SELECT CASE WHEN LENGTH ( B.SUBDIVID ) = 1 THEN B.SUBDIVID ||'0' \n" +
					"ELSE B.SUBDIVID END from(\n" +
					"SELECT CASE WHEN LENGTH ( CAST(A.sitecode  AS TEXT)) = 2 THEN \n" +
					"CAST(CAST(substr(CAST(A.sitecode AS TEXT),1,1)AS INTEGER ) AS TEXT)  \n" +
					"ELSE \n" +
					"CAST(CAST(substr(CAST(A.sitecode AS TEXT),6,1)AS INTEGER )+ 1 AS TEXT)   END AS SUBDIVID \n" +
					"FROM( SELECT COALESCE ( MAX(sitecode), '0000010' )  AS sitecode FROM meter_data.amilocation where division_code='"+divCode+"') A  )B";
			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
			return id;
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	
	}
	
//	@Override
//	public int createTownId(String subdivCode) {
//		int id=0;
//		try
//		{
//			String sql="SELECT CASE WHEN LENGTH ( B.SUBDIVID ) = 1 THEN B.SUBDIVID ||'0' \n" +
//					"ELSE B.SUBDIVID END from(\n" +
//					"SELECT CASE WHEN LENGTH ( CAST(A.sitecode  AS TEXT)) = 2 THEN \n" +
//					"CAST(CAST(substr(CAST(A.sitecode AS TEXT),1,1)AS INTEGER ) AS TEXT)  \n" +
//					"ELSE \n" +
//					"CAST(CAST(substr(CAST(A.sitecode AS TEXT),6,1)AS INTEGER )+ 1 AS TEXT)   END AS SUBDIVID \n" +
//					"FROM( SELECT COALESCE ( MAX(sitecode), '0000010' )  AS sitecode FROM meter_data.amilocation where sub='"+subdivCode+"') A  )B";
//			id=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
//			return id;
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			return 0;
//		}
//	}
	
	
	
	@Override
	public Object findSubDivDetailsByZone(String zoneVal, String circle,String division,String subdiv) {
		
		Object result=new ArrayList<AmiLocation>();
		try {

			String qry="select distinct tp_zonecode,ZONE,zone_code,tp_circlecode,circle,circle_code,tp_divcode,division,division_code,tp_subdivcode,subdivision,sitecode from meter_data.amilocation where upper(zone) like upper('"+zoneVal+"') and upper(circle) like upper('"+circle+"') and upper(division) like upper('"+division+"') and upper(subdivision) like upper('"+subdiv+"')";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int checkingTown(String zoneVal, String circle,String division, String subdiv,String townCode) {
		List lst=null;
		try
		{
			String sql="SELECT * FROM meter_data.amilocation WHERE upper(tp_zonecode) LIKE upper('"+zoneVal+"') AND tp_circlecode LIKE '"+circle+"' AND tp_divcode LIKE '"+division+"' AND tp_subdivcode LIKE '"+subdiv+"' and tp_towncode like '"+townCode+"'";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object findTownDetailsByZone(String zoneVal, String circle, String division, String subdiv,
			String town) {
		Object result=new ArrayList<AmiLocation>();
		try {
			String qry="select distinct tp_zonecode,ZONE,zone_code,tp_circlecode,circle,circle_code,tp_divcode,division,division_code,tp_subdivcode,subdivision,sitecode,tp_towncode,town_ipds from meter_data.amilocation where upper(zone) like upper('"+zoneVal+"') and upper(circle) like upper('"+circle+"') and upper(division) like upper('"+division+"') and upper(subdivision) like upper('"+subdiv+"') and upper(town_ipds) like upper('"+town+"')";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int checkingSection(String zoneVal, String circle, String division, String subdiv, String townCode,
			String sectionCode) {
		List lst=null;
		try {
			String sql="SELECT * FROM meter_data.amilocation WHERE upper(tp_zonecode) LIKE upper('"+zoneVal+"') AND tp_circlecode LIKE '"+circle+"' AND tp_divcode LIKE '"+division+"' AND tp_subdivcode LIKE '"+subdiv+"' and tp_towncode like '"+townCode+"' and tp_sectioncode like '"+sectionCode+"' ";
			lst=(ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();
			
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	



}
