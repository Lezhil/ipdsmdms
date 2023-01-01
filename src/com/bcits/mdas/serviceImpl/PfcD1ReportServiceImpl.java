package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.pfcd1rptentity;
import com.bcits.mdas.service.PfcD1ReportService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class PfcD1ReportServiceImpl extends GenericServiceImpl<pfcd1rptentity>  implements PfcD1ReportService{ 

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	public List<Object[]> getPFCD1ReportData(String billmonth ){
		
		List<Object[]> result  = null;
		String msg = null;
		
		String qry  = "SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,2) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
				"(select town_code,town_ipds,		round((unit_billed/unit_supply),2) AS 		     actualbillng_efficiency,\n" +
				"round((amt_collected/amt_billed),2) AS actualcollection_efficiency,\n" +
				"round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
				"round((amt_collected/amt_billed)*100,2) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
				"SUM (unit_billed) as unit_billed,\n" +
				"SUM (amt_billed) as amt_billed,\n" +
				"SUM (amt_collected) as amt_collected\n" +
				"from  meter_data.rpt_eamainfeeder_losses_12months mfl \n" +
				"left join amilocation al on al.tp_towncode = mfl.town_code where month_year = '"+billmonth+"'  GROUP BY town_code,town_ipds)firstqry )a";
	
		
	/*	String qry  = "Select  *,round((1-(Actualbilling_efficiency*Actualcollection_efficiency))*100,2) as atc_loss_percent from(\n" +
				"Select *,"
				+ "round((total_unit_billed/total_unit_supply),2) AS Actualbilling_efficiency,"
				+ "round((total_amount_collected/total_amount_billed),2) AS Actualcollection_efficiency,"
				+ "round((total_unit_billed/total_unit_supply)*100,2) AS billing_efficiency,"
				+ "round((total_amount_collected/total_amount_billed)*100,2) AS collection_efficiency from (Select * from\n" +
				"(select SUM(COALESCE(ht_industrial_energy_billed	,0)+\n" +
				"COALESCE(ht_commercial_energy_billed	,0)+\n" +
				"COALESCE(lt_industrial_energy_billed/2	,0)+\n" +
				"COALESCE(lt_commercial_energy_billed/2	,0)+\n" +
				"COALESCE(lt_domestic_energy_billed/2	,0)+\n" +
				"COALESCE(govt_energy_billed	,0)+\n" +
				"COALESCE(agri_energy_billed	,0)+\n" +
				"COALESCE(others_energy_billed	,0) )As total_unit_billed,\n" +
				"SUM(\n" +
				"COALESCE(ht_industrial_amount_billed	,0)+\n" +
				"COALESCE(ht_commercial_amount_billed	,0)+\n" +
				"COALESCE(lt_industrial_amount_billed/2	,0)+\n" +
				"COALESCE(lt_commercial_amount_billed/2	,0)+\n" +
				"COALESCE(lt_domestic_amount_billed/2	,0)+\n" +
				"COALESCE(govt_amount_billed	,0)+\n" +
				"COALESCE(agri_amount_billed	,0)+\n" +
				"COALESCE(others_amount_billed	,0) )As total_amount_billed,\n" +
				
				  "SUM(COALESCE(ht_industrial_amount_collected	,0)+\n" +
				  "COALESCE(ht_commercial_amount_collected	,0)+\n" +
				  "COALESCE(lt_industrial_amount_collected/2	,0)+\n" +
				  "COALESCE(lt_commercial_amount_collected/2	,0)+\n" +
				  "COALESCE(lt_domestic_amount_collected/2	,0)+\n" +
				  "COALESCE(govt_amount_collected	,0)+\n" +
				  "COALESCE(agri_amount_collected	,0)+\n" +
				  "COALESCE(others_amount_collected	,0) )as total_amount_collected,\n" +
				  "fdr_id,towncode,study_month,study_year,time_stamp  from meter_data.npp_feeder_rpt group by fdr_id,towncode,study_month,study_year,time_stamp\n" +
				  ") A ,\n" +
				  "(select meterno, fdr_id from meter_data.feederdetails)B where A.fdr_id = B.fdr_id )F,\n" +
				  "(Select kwh_imp as total_unit_supply,mtrno,billmonth from meter_data.monthly_consumption )D where D.mtrno =F.meterno and billmonth = '201904')BB" ;
		
		*/
		
		
		
		
	try {
		result = entityManager.createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
		return result;
		
	}

	@Override
	public List<?> checkFreezDataD1(String billmonth) {
		String freezeCol = "freeze";
	String qry = "select *from  meter_data.pfc_d1_rpt  where month_year = '"+billmonth+"' and  freezed = '1' ";
		System.out.println(qry);
		List<?> result = entityManager.createNativeQuery(qry).getResultList();	
		return result;
	}

	@Override
	public int freezeDataD1(String billmonth) {
		String qry = "update meter_data.pfc_d1_rpt set freezed = '1' where month_year = '"+billmonth+"'";
		
		int x =0;
		try {
			x = entityManager.createNativeQuery(qry).executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return x;
	}
	
	
	
	
}
