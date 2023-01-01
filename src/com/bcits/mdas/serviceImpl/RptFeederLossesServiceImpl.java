package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.RptFeederLossesEntity;
import com.bcits.mdas.service.RptFeederLossesService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class RptFeederLossesServiceImpl  extends GenericServiceImpl<RptFeederLossesEntity> implements RptFeederLossesService{

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Override
	public List<Object[]> getrptFeederLosses(String billmonthNew) {
					
			List<Object[]> result  = null;
			String msg = null;
			
			String qry  = "Select  *,round((1-(Actualbilling_efficiency*Actualcollection_efficiency))*100,2) as atc_loss_percent from(\n" +
					"Select *,round((total_unit_billed/total_unit_supply),2) AS Actualbilling_efficiency,round((total_amount_collected/total_amount_billed),2) AS Actualcollection_efficiency,round((total_unit_billed/total_unit_supply)*100,2) AS billing_efficiency,round((total_amount_collected/total_amount_billed)*100,2) AS collection_efficiency from (Select * from\n" +
					"(select SUM(COALESCE(ht_ind_energy_bill ,0)+\n" +
					"COALESCE(ht_com_energy_bill ,0)+\n" +
					"COALESCE(lt_ind_energy_bill ,0)+\n" +
					"COALESCE(lt_com_energy_bill ,0)+\n" +
					"COALESCE(lt_dom_energy_bill ,0)+\n" +
					"COALESCE(govt_energy_bill ,0)+\n" +
					"COALESCE(agri_energy_bill ,0)+\n" +
					"COALESCE(other_energy_bill ,0) )As total_unit_billed,\n" +
					"SUM(COALESCE(ht_ind_amount_bill ,0)+\n" +
					"COALESCE(ht_com_amount_bill ,0)+\n" +
					"COALESCE(lt_ind_amount_bill ,0)+\n" +
					"COALESCE(lt_com_amount_bill ,0)+\n" +
					"COALESCE(lt_dom_amount_bill ,0)+\n" +
					"COALESCE(govt_amount_bill ,0)+\n" +
					"COALESCE(agri_amount_bill ,0)+\n" +
					"COALESCE(other_amount_bill ,0) )As total_amount_billed,\n" +
					"SUM(COALESCE(ht_ind_amount_collected	,0)+\n" +
					"COALESCE(ht_com_amount_collected	,0)+\n" +
					"COALESCE(lt_ind_amount_collected ,0)+\n" +
					"COALESCE(lt_com_amount_collected ,0)+\n" +
					"COALESCE(lt_dom_amount_collected ,0)+\n" +
					"COALESCE(govt_amount_collected ,0)+\n" +
					"COALESCE(agri_amount_collected ,0)+\n" +
					"COALESCE(other_amount_collected ,0) )as total_amount_collected,\n" +
					"SUM(COALESCE(ht_ind_con_count	,0)+\n" +
					"COALESCE(ht_com_con_count	,0)+\n" +
					"COALESCE(lt_ind_con_count ,0)+\n" +
					"COALESCE(lt_com_con_count ,0)+\n" +
					"COALESCE(lt_dom_con_count ,0)+\n" +
					"COALESCE(govt_con_count ,0)+\n" +
					"COALESCE(agri_con_count ,0)+\n" +
					"COALESCE(other_con_count ,0) )as total_consumer,\n" +
					"feeder_code,towncode,monthyear,time_stamp  from meter_data.npp_data group by feeder_code,towncode,monthyear,time_stamp\n" +
					") A ,\n" +
					"(select meterno, fdr_id,officeid,tp_fdr_id,feedername from meter_data.feederdetails)B where A.feeder_code = B.fdr_id )F,\n" +
					"(Select kwh_imp as total_unit_supply,mtrno,billmonth from meter_data.monthly_consumption )D where D.mtrno =F.meterno and billmonth = '201904')BB" ;
		try {
			result = entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return result;
		
	}

	 
	
}
