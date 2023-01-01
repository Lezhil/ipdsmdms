package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.PfcD3ReportEntity;
import com.bcits.mdas.entity.PfcD4reportEntity;
import com.bcits.mdas.service.PfcD3ReportService;
import com.bcits.mdas.service.PfcD4ReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class PfcD4ReportServiceImpl extends GenericServiceImpl<PfcD4reportEntity>  implements PfcD4ReportService{

	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Override
	public List<?> checkFreezDataD4(String billmonth) {
		String freezeCol = "freeze";
	String qry = "select *from  meter_data.pfc_d4_rpt  where month_year = '"+billmonth+"' and  freezed = '1' ";
		System.out.println(qry);
		List<?> result = entityManager.createNativeQuery(qry).getResultList();	
		return result;
	}

	@Override
	public int freezeDataD4(String billmonth) {
		String qry = "update meter_data.pfc_d4_rpt set freezed = '1' where month_year = '"+billmonth+"'";
		
		int x =0;
		try {
			x = entityManager.createNativeQuery(qry).executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return x;
	}
}
