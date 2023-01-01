package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.AmiLocation;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class AmiLocationServiceImpl  extends GenericServiceImpl<AmiLocation>  implements AmiLocationService{

	@Override
	public String circlecode(String circleName) {
		Integer circlecode= (Integer) postgresMdas.createNamedQuery("AmiLocation.circlecode").setParameter("circle", circleName).getSingleResult();
		return circlecode.toString();
	}

	@Override
	public String divisionCode(String division) {
		Integer divisioncode= (Integer) postgresMdas.createNamedQuery("AmiLocation.divisioncode").setParameter("division", division).getSingleResult();
		return divisioncode.toString();
	}

	@Override
	public String subDivisionCode(String sdoname) {
		Integer subDivisioncode= (Integer) postgresMdas.createNamedQuery("AmiLocation.subdivisioncode").setParameter("sdoname", sdoname).getSingleResult();
		return subDivisioncode.toString();
	}
	
	@Override
	public String circleName(String circleCode) {
		String circleName= (String) postgresMdas.createNamedQuery("AmiLocation.circleName").setParameter("circleCode", Integer.parseInt(circleCode)).getSingleResult();
		return circleName;
	}

	@Override
	public String divisionName(String divisionCode) {
		String divisionName= (String) postgresMdas.createNamedQuery("AmiLocation.divisionName").setParameter("divisionCode", Integer.parseInt(divisionCode)).getSingleResult();
		return divisionName;
	}
	@Override
	public String subDivisionName(String subdivisionCode) {
		String subdivisionName= (String) postgresMdas.createNamedQuery("AmiLocation.subdivisionName").setParameter("sitecode", Integer.parseInt(subdivisionCode)).getSingleResult();
		return subdivisionName;
	}

	@Override
	public List<Integer[]> getSitcCodeByCircle(String circle,String zone,String town) {
		List<Integer[]> siteCodes=new ArrayList<>();
		String sql="select distinct sitecode from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like '"+town+"'" ;
		siteCodes=postgresMdas.createNativeQuery(sql).getResultList();
		return siteCodes;
				//=postgresMdas.createNamedQuery("AmiLocation.getSdocodeByCircle").setParameter("circle", circle).getResultList();
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer[]> getSiteCodeByDivision(String circle,String division,String zone, String town) {

		List<Integer[]> siteCodes=new ArrayList<>();
		String sql="select distinct sitecode from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"' and division like '"+division+"'";
		//return siteCodes=postgresMdas.createNamedQuery("AmiLocation.getSdocodeByDivision").setParameter("circle", circle).setParameter("division", division).getResultList();
		siteCodes=postgresMdas.createNativeQuery(sql).getResultList();
		System.out.println("div-----"+sql);
		return siteCodes;
	}
	@Override
	public List<String> getAllSiteCodes() {

		List<String> sitecodes=postgresMdas.createNamedQuery("AmiLocation.getAllsubdivisioncode").getResultList();
		return sitecodes;
	}

	@Override
	public AmiLocation getLocationDetails(String zonecode, String circlecode, String division_code, String subdivisioncode,
			 String towncode) {
		//List<AmiLocation> result=new ArrayList<AmiLocation>();
		try {
			 return getCustomEntityManager("postgresMdas").createNamedQuery("AmiLocation.getLocationDetails",AmiLocation.class)
					 .setParameter("tp_zonecode", zonecode)
					 .setParameter("tp_circlecode", circlecode)
					 .setParameter("tp_divcode", division_code)
					 .setParameter("tp_subdivcode", subdivisioncode)
					 .setParameter("tp_towncode", towncode)
					 .getSingleResult();
	//	result= getCustomEntityManager("postgresMdas").createNamedQuery("getLocationDetails").getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public AmiLocation getAmiLocationDetails(String circlecode, String division_code, String subdivisioncode,
			 String towncode,String sectionCode) {
		//List<AmiLocation> result=new ArrayList<AmiLocation>();
		try {
			 return getCustomEntityManager("postgresMdas").createNamedQuery("AmiLocation.getAmiLocationDetails",AmiLocation.class)
					 .setParameter("tp_circlecode", circlecode)
					 .setParameter("tp_divcode", division_code)
					 .setParameter("tp_subdivcode", subdivisioncode)
					 .setParameter("tp_towncode", towncode).setParameter("tp_sectioncode", sectionCode)
					 .getSingleResult();
	//	result= getCustomEntityManager("postgresMdas").createNamedQuery("getLocationDetails").getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public String getRegionNameByCircle(String circleCode){
		
		String Regionname = ""; 
		String qry = "Select distinct zone from meter_data.amilocation where circle_code = '"+circleCode+"'  ";
		try {
			Regionname=postgresMdas.createNativeQuery(qry).getSingleResult().toString();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Regionname;
	}
	
	public List<?> getAllRegions(){
		
		List<?> AllRegionname = null  ; 
		String qry = "Select distinct zone from meter_data.amilocation  order by zone";
		try {
			AllRegionname=postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllRegionname;
	}
	
	public List<?> getCircleListByZoneCode(String zonecode){
		
		List<?> AllRegionname = null  ; 
		String qry = "Select distinct circle from meter_data.amilocation where zone_code = '"+zonecode+"' order by circle ";
		try {
			AllRegionname=postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllRegionname;
	}
	
	
	public String getRegionNameByRegionCode(String regionCode){
		
		String Regionname = ""; 
		String qry = "Select distinct zone from meter_data.amilocation where zone_code = '"+regionCode+"'  ";
		
		System.out.println(qry);
		try {
			Regionname=postgresMdas.createNativeQuery(qry).getSingleResult().toString();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Regionname;
	}
	
	public List<?> getTownByCircleCode(String circleCode){
		
		List<?> AllRegionname = null  ; 
		String qry = "Select distinct tp_towncode , town_ipds from meter_data.amilocation where circle_code = '"+circleCode+"' order by tp_towncode  ";
		try {
			AllRegionname=postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllRegionname;
	}
	
	
}
