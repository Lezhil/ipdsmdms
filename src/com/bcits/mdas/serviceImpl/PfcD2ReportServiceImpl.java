package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.PfcD2reportEntity;
import com.bcits.mdas.service.PfcD2ReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class PfcD2ReportServiceImpl extends GenericServiceImpl<PfcD2reportEntity>  implements PfcD2ReportService{

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Override
	public List<Object[]> getPFCD2ReportData(String billmonthNew) {
		
		List<Object[]> result  = null;
		String msg = null;
		
		String qry  = "Select TOWN,month_year,timestamp ,Sum(nc_req_opening_cnt) as nc_req_opening_cnt ,\n" +
				"Sum(nc_req_received) as nc_req_received,\n" +
				"Sum(tot_nc_req)as tot_nc_req,\n" +
				"Sum(nc_req_closed)as nc_req_closed,\n" +
				"Sum(nc_req_pending)as nc_req_pending,\n" +
				"Sum(closed_with_in_serc)as closed_with_in_serc,\n" +
				"Sum(closed_beyond_serc)as closed_beyond_serc,\n" +
				"Sum(percent_within_serc)as percent_within_serc,\n" +
				"Sum(released_by_it_system)as released_by_it_system  from meter_data.pfc_d2_rpt_intermediate where month_year = '"+billmonthNew+"' GROUP BY town,month_year,timestamp" ;
		//System.out.println(qry);
		
	try {
		result = entityManager.createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}

	
	
	@Override
	public List<?> checkFreezDataD2(String billmonth) {
		String freezeCol = "freeze";
	String qry = "select *from  meter_data.pfc_d2_rpt  where month_year = '"+billmonth+"' and  freezed = '1' ";
		System.out.println(qry);
		List<?> result = entityManager.createNativeQuery(qry).getResultList();	
		return result;
	}

	@Override
	public int freezeDataD2(String billmonth) {
		String qry = "update meter_data.pfc_d2_rpt set freezed = '1' where month_year = '"+billmonth+"'";
		
		int x =0;
		try {
			x = entityManager.createNativeQuery(qry).executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return x;
	}
	
}
