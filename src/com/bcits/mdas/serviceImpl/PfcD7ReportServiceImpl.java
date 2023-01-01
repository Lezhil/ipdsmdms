package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.PfcD7ReportEntity;
import com.bcits.mdas.service.PfcD7ReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class PfcD7ReportServiceImpl extends GenericServiceImpl<PfcD7ReportEntity>  implements PfcD7ReportService{

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Override
	public int freezeData(String billmonth) {
		
		String qry = "update meter_data.pfc_d7_rpt set freezed = '1' where month_year = '"+billmonth+"'";
		
		int x =0;
		try {
			x = entityManager.createNativeQuery(qry).executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}

	@Override
	public List<?> checkFreezDataD7(String billmonth) {
		String qry = "select *from  meter_data.pfc_d7_rpt  where month_year = '"+billmonth+"' and freezed = '1'";
		
		List<?> result = entityManager.createNativeQuery(qry).getResultList();	
		
		
		return result;
	}

}
