package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.Pfcd5ReportEntity;
import com.bcits.mdas.service.PfcD5ReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class PfcD5ReportServiceImpl  extends GenericServiceImpl<Pfcd5ReportEntity>  implements PfcD5ReportService{

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Override
	public List<?> checkFreezDataD5(String billmonth) {
		String freezeCol = "freeze";
	String qry = "select *from  meter_data.pfc_d5_rpt  where month_year = '"+billmonth+"' and  freezed = '1' ";
		System.out.println(qry);
		List<?> result = entityManager.createNativeQuery(qry).getResultList();	
		return result;
	}

	@Override
	public int freezeDataD5(String billmonth) {
		String qry = "update meter_data.pfc_d5_rpt set freezed = '1' where month_year = '"+billmonth+"'";
		
		int x =0;
		try {
			x = entityManager.createNativeQuery(qry).executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return x;
	}

	
	
}
