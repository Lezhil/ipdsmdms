package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.AuditTrailEntity;
import com.bcits.service.AuditTrailEntityService;

@Repository
 public class AuditTrailEntityServiceImpl extends GenericServiceImpl<AuditTrailEntity> implements AuditTrailEntityService  {

	
	   @Override
	   public List<?> getAuditDetails(String mtrNum, String fromDate, String toDate){
		   List<?> list=new ArrayList<>();
		   String sql="select mg.*,mj.job_name,mj.job_type,case when mj.response_code='201' then 'Success' else 'Failed' end as jobcreStatus,case when (select m.status_code from meter_data.meter_job_management m where m.job_name=mj.job_name)='200' then 'Success' else 'not Started' end as jobStatus from (select meter_group_name,case when response_code='201' then 'Success' else 'Failure' end as groupCrestatus,timestamp from meter_data.meter_groups where meter_group_name in\n" +
				   "(select meter_group_name from meter_data.meter_group_meters where meter_number='"+mtrNum+"') and timestamp BETWEEN '"+fromDate+"' and '"+toDate+"') mg LEFT JOIN meter_data.meter_jobs mj on meter_group_name=meter_group";
		   list=postgresMdas.createNativeQuery(sql).getResultList();
		   System.out.println(list.size());
		return list;
		   
	   }

	@Override
	public List<?> getDataAquisition(String mtrNo, String fromDate,
			String toDate ) {
		// TODO Auto-generated method stub
		List<?> list=new ArrayList<>();
		String sql="SELECT cast(a.day as timestamp ),b.meter_number,b.last_communication,( CASE WHEN to_char( b.DATE, 'yyyy-mm-dd' ) = to_char( last_communication, 'yyyy-mm-dd' ) THEN 0 ELSE 1 END ) AS status  FROM\n" +
				"(\n" +
				"SELECT date(generate_series(timestamp '"+fromDate+"', '"+toDate+"', '1 day')) AS day\n" +
				")a LEFT JOIN \n" +
				"( SELECT meter_number, DATE, last_communication FROM meter_data.modem_communication WHERE meter_number = '"+mtrNo+"' AND DATE BETWEEN '"+fromDate+"' AND '"+toDate+"' ORDER BY DATE )b ON a.day=b.date;";
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getMtrData(String mtrNum, String date) {

		List<?> list=new ArrayList<>();
		String sql="SELECT meter_number,imei,date,last_communication,last_sync_inst,last_sync_event from meter_data.modem_communication where meter_number='"+mtrNum+"' and date='"+date+"'";
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getValidationTransactions(String mtrNum, String fromDate,
			String toDate) {
		List<?> list=new ArrayList<>();
		String sql="SELECT cast(a.day as timestamp ),b.v_rule_id,b.v_rulename,b.meter_number,b.location_type,b.location_id,b.location_name,b.zone,b.circle,b.division,b.subdivision,\n" +
				"(CASE WHEN b.meter_number is null then 'SUCCESS' else 'FAILURE' END) as status,b.createddate\n" +
				"FROM\n" +
				"(\n" +
				"SELECT date(generate_series(timestamp '"+fromDate+"', '"+toDate+"', '1 day')) AS day\n" +
				")a LEFT JOIN \n" +
				"(\n" +
				"select * from meter_data.validation_process_rpt where \n" +
				"meter_number='"+mtrNum+"' and \"date\"(createddate) BETWEEN '"+fromDate+"' and '"+toDate+"'\n" +
				")b ON a.day=date(b.createddate);";

		try {
			System.out.println("YHFGHKJHK"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getSingleValidationData(String mtrNo, String date) {
		List<?> list=new ArrayList<>();
		String sql="select v_rule_id,v_rulename,meter_number,location_type,location_id,location_name,zone,circle,division,subdivision from meter_data.validation_process_rpt where meter_number='"+mtrNo+"' and  to_char(createddate,'dd-MM-YYYY HH24:MI:SS')='"+date+"'";
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getEstimationTransactions(String mtrNum, String fromDate,String toDate,String locationType) {

		String loc_type=locationType.toUpperCase();
		
		List<?> list=new ArrayList<>();
		String sql="SELECT cast(a.day as timestamp ),b.meter_number,b.est_date,\n" +
				"(CASE WHEN b.meter_number is null then 'SUCCESS' else 'FAILURE' END) as status\n" +
				"FROM\n" +
				"(\n" +
				"SELECT date(generate_series(timestamp '"+fromDate+"', '"+toDate+"', '1 day')) AS day\n" +
				")a LEFT JOIN \n" +
				"(\n" +
				"select * from meter_data.estimation_process_rpt where \n" +
				"meter_number='"+mtrNum+"' and  location_type='"+loc_type+"' and \"date\"(est_date) BETWEEN '"+fromDate+"' and '"+toDate+"'\n" +
				")b ON a.day=date(b.est_date);";
		try {
		list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getSingleEstimationData(String mtrNo, String date) {
		List<?> list=new ArrayList<>();

		String sql="select * from meter_data.estimation_process_rpt where  meter_number='"+mtrNo+"' and est_date='"+date+"'";
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	   
	
}
