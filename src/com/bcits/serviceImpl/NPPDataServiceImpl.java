package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bcits.entity.NPPDataEntity;
import com.bcits.service.NPPDataService;

@Repository
public class NPPDataServiceImpl extends GenericServiceImpl<NPPDataEntity> implements NPPDataService {

	@Override
	public List<NPPDataEntity> getDataByMonthYear(String my) {
		return postgresMdas.createNamedQuery("NPPDataEntity.getDataByMonthYear", NPPDataEntity.class)
				.setParameter("monthYear", Integer.parseInt(my))
				.getResultList();
	}
	@Override
	public List<NPPDataEntity> getDataByTownMonthYear(String town, String my) {
		
		String[] towns=town.split(",");
		
		System.out.println(towns);
		List<String> list=Arrays.asList(towns);
		
		/*
		 * Query q=postgresMdas.unwrap(Session.class).
		 * createQuery("select n from NPPDataEntity n where monthYear=:monthYear and n.keyNPPData.feedercode in "
		 * +
		 * "(select f.fdrid from FeederEntity f where f.tpparentid in (select s.sstpid from SubstationDetailsEntity s where s.parent_town in (:towns)))"
		 * ); q.setParameterList("towns", list); q.setParameter("monthYear",
		 * Integer.parseInt(my));
		 */
		
		
		  return postgresMdas.createNamedQuery("NPPDataEntity.getDataByTownMonthYear", NPPDataEntity.class) 
				  .setParameter("towns", list) 
				  .setParameter("monthYear", Integer.parseInt(my)) 
				  .getResultList();
		 
		
		//return q.list();
	}

	@Override
	public List<Object[]> getRAPDRPTown() {
		List list=null;
		try {
			String sql="select DISTINCT town_rapdrp,tp_towncode from meter_data.amilocation ";
			System.err.println("rapdrp are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<Object[]> getIPDSTown() {
		List list=null;
		try {
			String sql="select DISTINCT town_ipds,tp_towncode from meter_data.amilocation ";
			System.err.println("ipds are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDistinctMonthYear() {
		List list=null;
		try {
			String sql="select DISTINCT monthyear from meter_data.npp_data ";
			System.err.println("ipds are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	

	@Override
	public List<Object[]> getNPPReportRapdrpDetails(String town, String period) {
		List list=null;
		System.err.println("innnnn");
		try {
			/*String sql="SELECT DISTINCT l.discom,l.town_ipds,l.town_rapdrp,b.* FROM meter_data.amilocation l,\n" +
					"(\n" +
					"SELECT s.parent_town,a.* FROM meter_data.substation_details s,\n" +
					"(\n" +
					"SELECT f.feedername,f.fdr_id,f.parentid,f.tpparentid,n.* FROM meter_data.npp_data_monthly_calculation n, meter_data.feederdetails f WHERE n.feeder_code=f.fdr_id and n.monthyear='"+period+"'\n" +
					")a WHERE s.sstp_id=a.tpparentid AND s.parent_town in ("+town+")\n" +
					")b WHERE l.tp_towncode=b.parent_town ";
*/
			
			
			
			String sql = "Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
					"	to_char(to_date('"+period+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
					" 	to_char(to_date('"+period+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period,\n" +
					" 	'U' as feeder_type from  (\n" +
					"	Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
					"	Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
					"	Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin,\n" +
					"	ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,ht_ind_energy_bill,\n" +
					"	ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,\n" +
					"	ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,\n" +
					"	other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected		\n" +
					"	from meter_data.power_onoff po 				LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear			where to_char(date, 'yyyymm') = '"+period+"' and po.towncode = '"+town+"' \n" +
					"	GROUP BY feedercode ,meterid,monthyear,		ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_dom_con_count,govt_con_count,\n" +
					"	agri_con_count,other_con_count,ht_ind_energy_bill,ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,\n" +
					"	lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_com_con_count,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected) xa"
					+ "	LEFT JOIN meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth	";
			
			
			//System.err.println("npp rapdrp reports are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<Object[]> getNPPReportipdsDetails(String town, String period) {
		List list=null;
		System.err.println("innnnn");
		try {
			String sql="SELECT DISTINCT l.discom,l.town_ipds,l.town_rapdrp,b.* FROM meter_data.amilocation l,\n" +
					"(\n" +
					"SELECT s.parent_town,a.* FROM meter_data.substation_details s,\n" +
					"(\n" +
					"SELECT f.feedername,f.fdr_id,f.parentid,n.* FROM meter_data.npp_data n, meter_data.feederdetails f WHERE n.feeder_code=f.fdr_id and n.monthyear='"+period+"'\n" +
					")a WHERE s.ss_id=a.parentid AND s.parent_town in ('"+town+"')\n" +
					")b WHERE l.tp_towncode=b.parent_town ";
			System.err.println("npp ipds reports are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

}
