package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.BusinessRoleEntity;
import com.bcits.mdas.service.BusinessRoleService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class BusinessRoleServiceImpl extends GenericServiceImpl<BusinessRoleEntity> implements BusinessRoleService {

	@Override
	public List<?> getRoleStatus(String designation, String username) {
		List<?> resultList = null;

		try {

			String qry = "select role_name FROM meter_data.business_roles where role_name not in(SELECT designation FROM meter_data.users)";
			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;

	}

	@Override
	public Object gettownByScheme(String scheme) {
		List<?> resultList = null;
		String qry;

		try {
			if (scheme.equalsIgnoreCase("RAPDRP")) {
				qry = "select town_rapdrp from meter_data.amilocation where town_rapdrp is not null ORDER BY town_rapdrp";

			} else {
				qry = "select distinct (town_ipds),tp_towncode from meter_data.amilocation where town_ipds is not null ORDER BY town_ipds";
			}

			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	@Override
	public List<?> getDistinctPeriod() {
		List<?> resultList = null;
		try {
			String qry = "select distinct month_year from meter_data.pfc_d5_rpt ORDER BY month_year";
			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<?> getDistinctPeriodD7() {
		List<?> resultList = null;
		try {
			String qry = "select distinct month_year from meter_data.pfc_d7_rpt ORDER BY month_year";
			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public Object getfdrcountData(String period, String TownId[]) {
		List<?> resultList = null;
		String qry = null;
		String isAllSeelected = null;

		for (int i = 0; i <= TownId.length - 1; i++) {
			if (TownId[i].equalsIgnoreCase("All")) {
				isAllSeelected = "%";
				break;
			} else {
				isAllSeelected = TownId[i];
			}
		}
		if (isAllSeelected.equalsIgnoreCase("yes")) {

//			qry="SELECT DISTINCT	* FROM	(\n" +
//					"SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
//					"from meter_data.pfc_d5_rpt where month_year='"+period+"' 	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
//					"WHERE Y.tp_towncode = X.town ) AA";	

			qry = "select \r\n" + "town,\r\n" + "feeder_name,\r\n" + "mtrno,\r\n"
					+ "flag from meter_data.pfc_d6_rpt where month_year = '" + period + "' and town_codes like '"+isAllSeelected+"'";
		//	System.out.println(sql);
		} else {
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2 = null;
			int size = TownId.length;
			String test = null;
			String[] splittest = null;
			String firsttest = null;
			String finalString = null;

			for (int i = 0; i <= size - 1; i++) {
				int x = size - 1;
				String[] splittest1 = TownId[i].split("-");
				if (i == 0) {
					firsttest = "('";
					test = splittest1[0];
					finalString = firsttest + test + "')";
				} else if (i != x) {
					test = test + "','" + splittest1[0];
				} else {
					test = test + "','" + splittest1[0] + "')";
					finalString = firsttest + test;
				}

			}

//			qry="SELECT DISTINCT	* FROM	(\n" +
//					"SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
//					"from meter_data.pfc_d5_rpt where month_year='"+period+"' 	AND town in "+finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
//					"WHERE Y.tp_towncode = X.town ) AA";

			qry = "select \r\n" + "town,\r\n" + "feeder_name,\r\n" + "mtrno,\r\n"
					+ "flag from meter_data.pfc_d6_rpt where town_codes like '" + isAllSeelected + "' and month_year = '"
					+ period + "'";
		}

		try {
			// String qry= "select rec_id,town,fdr_id,meter_sr_number,meter_communicating
			// from meter_data.pfc_d5_rpt where month_year='"+period+"' and town =
			// '"+town+"'";

			 System.out.println(qry);
			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public String getStateName() {
		String resultList = null;
		try {
			String qry = "select distinct state_name from meter_data.state_discom_master";
			resultList = (String) postgresMdas.createNativeQuery(qry).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public String getDiscomName() {

		String resultList = null;
		try {
			String qry = "select distinct discom_name from meter_data.state_discom_master";
			resultList = (String) postgresMdas.createNativeQuery(qry).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<?> getDistinctPeriodD1() {
		List<?> resultList = null;
		try {
			String qry = "select distinct month_year from meter_data.pfc_d1_rpt";
			resultList = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public String getCompanyName() {
		String resultList = null;
		try {
			String qry = "select DISTINCT(company) from meter_data.amilocation";
			resultList = (String) postgresMdas.createNativeQuery(qry).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}