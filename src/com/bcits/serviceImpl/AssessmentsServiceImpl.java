package com.bcits.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.bcits.entity.AssessmentEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.service.AssessmentsService;
import com.crystaldecisions12.reports.queryengine.Session;

@Repository
public class AssessmentsServiceImpl extends GenericServiceImpl<AssessmentEntity> implements AssessmentsService {

	@Override
	public Object getDistinctCircle() {
		String qry = "SELECT DISTINCT CIRCLE FROM meter_data.ASSESSMENT";
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@Override
	public Object getDistinctBillmonth() {
		String qry = "SELECT DISTINCT BILLMONTH FROM meter_data.ASSESSMENT";
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@Override
	public Object getDistinctCategory() {
		String qry = "SELECT DISTINCT CATEGORY FROM meter_data.ASSESSMENT";
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@Override
	public Object getDistinctTamperType() {
		String qry = "SELECT DISTINCT TAMPER_TYPE FROM meter_data.ASSESSMENT";
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssessmentEntity> getAssesmentDetails(String billmonth, String circle, String category,
			String tamperType, String discom) {
//	return entityManager.createNamedQuery("AssessmentEntity.getReport").setParameter("billmonth",Integer.parseInt(billmonth)).setParameter("circle",circle).setParameter("category",category).setParameter("tamperType",tamperType).getResultList();
		String qry = "SELECT METRNO,CIRCLE,DIVISION,SUBDIV,CATEGORY,TAMPER_TYPE,COUNT(*),BILLMONTH,NAME,ADDRESS,MTRMAKE FROM meter_data.ASSESSMENT\n"
				+ " WHERE BILLMONTH='" + billmonth + "' AND CIRCLE='" + circle + "' AND CATEGORY='" + category + "' "
				+ "AND TAMPER_TYPE='" + tamperType + "' AND DISCOM='" + discom
				+ "' GROUP BY METRNO,CIRCLE,DIVISION,SUBDIV,CATEGORY,TAMPER_TYPE,BILLMONTH,NAME,ADDRESS,MTRMAKE";
		System.out.println("qry-------- " + qry);
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssessmentEntity> getAssesmentMoreDetails(String billmonth, String circle, String category,
			String tamperType, String meterNo) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("AssessmentEntity.getReport")
				.setParameter("billmonth", Integer.parseInt(billmonth)).setParameter("circle", circle)
				.setParameter("category", category).setParameter("tamperType", tamperType)
				.setParameter("meterNo", meterNo).getResultList();
	}

	@Override
	public List<?> getMeterLifeCycleData(String mtrNo, String flag, HttpSession session, String Fdrcategory) {
		// HttpSession session=(HttpSession) new Session();
		String pname = (String) session.getAttribute("projectName");
		System.out.println("prject name--" + pname);
		List<?> data = null;
		Date d1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String rdngmonth = sdf.format(d1);
		String sql = "";

		try {
			if (flag.equalsIgnoreCase("mtrno")) {

				/*
				 * sql="SELECT m.kno,m.circle,m.division,m.subdiv,\n" +
				 * "m.mtrmake,m.name,m.address1,m.metrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,\n"
				 * +
				 * "MIN(last_communication) as first_communicated,MAX(last_communication)as last_communicated,l.disconn_date,l.conn_date,final_reading FROM meter_data.modem_communication mc, \n"
				 * + "meter_data.mm m, meter_data.meter_lifecycle l\n" + "WHERE meter_number='"
				 * +mtrNo+"' AND m.metrno=mc.meter_number AND mc.meter_number=l.meter_no and rdngmonth='"
				 * +rdngmonth+"'\n" + "GROUP BY m.kno,m.circle,m.division,m.subdiv,\n" +
				 * "m.mtrmake,m.name,m.address1,m.metrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,l.disconn_date,l.conn_date,final_reading";
				 */
				/*
				 * sql="SELECT m.kno,m.circle,m.division,m.subdivision,\n" +
				 * "m.mtrmake,m.customer_name,m.customer_address,m.mtrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,\n"
				 * +
				 * "MIN(last_communication) as first_communicated,MAX(last_communication)as last_communicated,l.disconn_date,l.conn_date,final_reading FROM meter_data.modem_communication mc, \n"
				 * + "meter_data.master_main m, meter_data.meter_lifecycle l\n" +
				 * "WHERE mtrno='"
				 * +mtrNo+"' AND m.mtrno=mc.meter_number AND mc.meter_number=l.meter_no\n" +
				 * "GROUP BY m.kno,m.circle,m.division,m.subdivision,\n" +
				 * "m.mtrmake,m.customer_name,m.customer_address,m.mtrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,l.disconn_date,l.conn_date,final_reading";
				 */

				/*
				 * if(pname.equalsIgnoreCase("TNEB")){
				 * 
				 * sql="SELECT * FROM\n" +
				 * "(SELECT ZONE,circle,division,subdivision,fdrcategory AS LOC_TYPE,mtrno as MeterSrNo,\n"
				 * +
				 * "accno as LOC_IDENTITY , customer_name AS LOC_NAME ,mtrmake AS MeterMake,installation_date,mtrno\n"
				 * + " FROM meter_data.master_main m WHERE mtrno='"+mtrNo+"')MA,\n" +
				 * "(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n"
				 * +
				 * "(SELECT max(read_time)AS lastcommunication,meter_number,kwh as lkwh FROM meter_data.amiinstantaneous WHERE meter_number='"
				 * +mtrNo+"' \n" +
				 * " GROUP BY read_time,meter_number,kwh ORDER BY read_time desc LIMIT 1)A\n" +
				 * "LEFT JOIN \n" +
				 * "(SELECT read_time AS firstrdate,meter_number,kwh AS fkwh FROM meter_data.meter_firsttime_read_data WHERE meter_number='"
				 * +mtrNo+"')B\n" + "ON A.meter_number=B.meter_number)C\n" +
				 * "where MA.MeterSrNo=C.meter_number";
				 * 
				 * sql="SELECT * FROM\n" +
				 * "(SELECT ZONE,circle,division,subdivision,fdrcategory AS LOC_TYPE,mtrno as MeterSrNo,\n"
				 * +
				 * "accno as LOC_IDENTITY , customer_name AS LOC_NAME ,mtrmake AS MeterMake,installation_date,mtrno,create_time,remarks\n"
				 * + " FROM meter_data.master_main m WHERE mtrno='"+mtrNo+"')MA,\n" +
				 * "(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n"
				 * +
				 * "(SELECT max(read_time)AS lastcommunication,meter_number,kwh as lkwh FROM meter_data.amiinstantaneous "
				 * + "WHERE meter_number='"+mtrNo+"'  \n" +
				 * " GROUP BY read_time,meter_number,kwh ORDER BY read_time desc LIMIT 1)A\n" +
				 * "LEFT JOIN \n" +
				 * "(SELECT read_time AS firstrdate,meter_number,kwh AS fkwh FROM meter_data.amiinstantaneous WHERE meter_number='"
				 * +mtrNo+"' ORDER BY read_time ASC limit 1)B\n" +
				 * "ON A.meter_number=B.meter_number)C\n" + "where MA.MeterSrNo=C.meter_number";
				 * } else { sql="SELECT * FROM \n" +
				 * "(SELECT ZONE,circle,division,subdivision,fdrcategory AS LOC_TYPE,mtrno as MeterSrNo,\n"
				 * +
				 * "accno as LOC_IDENTITY , customer_name AS LOC_NAME ,mtrmake AS MeterMake,create_time,\n"
				 * + " FROM meter_data.master_main m WHERE mtrno='"+mtrNo+"')MA,\n" +
				 * "(SELECT survey_timings,newmeterno,firstrdate,fkwh,lastcommunication,lkwh FROM\n"
				 * +
				 * "(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n"
				 * +
				 * "(SELECT max(read_time)AS lastcommunication,meter_number,kwh/1000 as lkwh FROM meter_data.amiinstantaneous WHERE meter_number='"
				 * +mtrNo+"' \n" +
				 * " GROUP BY read_time,meter_number,kwh ORDER BY read_time desc LIMIT 1)A\n" +
				 * "LEFT JOIN \n" +
				 * "(SELECT read_time AS firstrdate,meter_number,kwh/1000 AS fkwh FROM meter_data.meter_firsttime_read_data WHERE meter_number='"
				 * +mtrNo+"')B\n" + "ON A.meter_number=B.meter_number)C \n" + "LEFT JOIN\n" +
				 * "(SELECT survey_timings,newmeterno FROM meter_data.survey_output WHERE newmeterno='"
				 * +mtrNo+"')D\n" + "ON D.newmeterno=C.meter_number)E\n" +
				 * "where MA.MeterSrNo=E.newmeterno"; }
				 */
				
				if(Fdrcategory.equalsIgnoreCase("DT")) {
					sql=" SELECT * FROM\n" +
							"(\n" +
							"SELECT m.ZONE,m.circle,m.division,m.subdivision,m.fdrcategory AS LOC_TYPE,m.mtrno as MeterSrNo,\n" +
							"d.dttpid as LOC_IDENTITY , m.customer_name AS LOC_NAME ,m.mtrmake AS MeterMake,installation_date,mtrno,create_time,latitude,longitude,remarks_date\n" +
							"FROM meter_data.master_main m \n" +
							"LEFT JOIN\n" +
							"meter_data.dtdetails d ON  m.mtrno=d.meterno WHERE  m.mtrno='"+mtrNo+"')MA,\n" +
							"(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n" +
							"(SELECT max(read_time)AS lastcommunication,meter_number,kwh_imp as lkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time desc LIMIT 1)A\n" +
							"LEFT JOIN \n" +
							"(SELECT  min(read_time) AS firstrdate,meter_number,kwh_imp AS fkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time ASC limit 1)B\n" +
							"ON A.meter_number=B.meter_number)C\n" +
							"where MA.MeterSrNo=C.meter_number";
					System.out.println( sql);
				}
				else if(Fdrcategory.equalsIgnoreCase("FEEDER METER")) {
					sql=" SELECT * FROM\n" +
							"(\n" +
							"SELECT m.ZONE,m.circle,m.division,m.subdivision,m.fdrcategory AS LOC_TYPE,m.mtrno as MeterSrNo,\n" +
							"f.tp_fdr_id as LOC_IDENTITY , m.customer_name AS LOC_NAME ,m.mtrmake AS MeterMake,installation_date,mtrno,create_time,remarks,latitude,longitude,remarks_date\n" +
							"FROM meter_data.master_main m \n" +
							"LEFT JOIN\n" +
							"meter_data.feederdetails f ON  m.mtrno=f.meterno WHERE  m.mtrno='"+mtrNo+"')MA,\n" +
							"(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n" +
							"(SELECT max(read_time)AS lastcommunication,meter_number,kwh_imp as lkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time desc LIMIT 1)A\n" +
							"LEFT JOIN \n" +
							"(SELECT  min(read_time) AS firstrdate,meter_number,kwh_imp AS fkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time ASC limit 1)B\n" +
							"ON A.meter_number=B.meter_number)C\n" +
							"where MA.MeterSrNo=C.meter_number";
					System.out.println( sql);
				}
				
				else if(Fdrcategory.equalsIgnoreCase("BOUNDARY METER" )) {
					sql=" SELECT * FROM\n" +
							"(\n" +
							"SELECT m.ZONE,m.circle,m.division,m.subdivision,m.fdrcategory AS LOC_TYPE,m.mtrno as MeterSrNo,\n" +
							"f.boundary_id as LOC_IDENTITY , m.customer_name AS LOC_NAME ,m.mtrmake AS MeterMake,installation_date,mtrno,create_time,remarks,latitude,longitude,remarks_date\n" +
							"FROM meter_data.master_main m \n" +
							"LEFT JOIN\n" +
							"meter_data.feederdetails f ON  m.mtrno=f.meterno WHERE  m.mtrno='"+mtrNo+"')MA,\n" +
							"(SELECT B.firstrdate,B.fkwh,A.lastcommunication,A.lkwh,B.meter_number FROM\n" +
							"(SELECT max(read_time)AS lastcommunication,meter_number,kwh_imp as lkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time desc LIMIT 1)A\n" +
							"LEFT JOIN \n" +
							"(SELECT  min(read_time) AS firstrdate,meter_number,kwh_imp AS fkwh FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' GROUP BY read_time,meter_number,kwh_imp ORDER BY read_time ASC limit 1)B\n" +
							"ON A.meter_number=B.meter_number)C\n" +
							"where MA.MeterSrNo=C.meter_number";
					System.out.println(sql);
				}
				
				

			} else if (flag.equalsIgnoreCase("kno")) {

				/*
				 * sql="SELECT m.kno,m.circle,m.division,m.subdiv,\n" +
				 * "m.mtrmake,m.name,m.address1,m.metrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,\n"
				 * +
				 * "MIN(last_communication) as first_communicated,MAX(last_communication)as last_communicated,l.disconn_date,l.conn_date,final_reading FROM meter_data.modem_communication mc, \n"
				 * + "meter_data.mm m, meter_data.meter_lifecycle l\n" + "WHERE kno='"
				 * +mtrNo+"' AND m.metrno=mc.meter_number AND mc.meter_number=l.meter_no\n" +
				 * "GROUP BY m.kno,m.circle,m.division,m.subdiv,\n" +
				 * "m.mtrmake,m.name,m.address1,m.metrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,l.disconn_date,l.conn_date,final_reading";
				 */
				sql = "SELECT m.kno,m.circle,m.division,m.subdivision,\n"
						+ "m.mtrmake,m.customer_name,m.customer_address,m.mtrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,\n"
						+ "MIN(last_communication) as first_communicated,MAX(last_communication)as last_communicated,l.disconn_date,l.conn_date,final_reading FROM meter_data.modem_communication mc, \n"
						+ "meter_data.master_main m, meter_data.meter_lifecycle l\n" + "WHERE kno='" + mtrNo
						+ "' AND m.mtrno=mc.meter_number AND mc.meter_number=l.meter_no\n"
						+ "GROUP BY m.kno,m.circle,m.division,m.subdivision,\n"
						+ "m.mtrmake,m.customer_name,m.customer_address,m.mtrno,m.accno,l.installed_date,l.devicer_registration,l.provision_date,l.disconn_date,l.conn_date,final_reading";

			}
			System.out.println("life cycle qry--" + sql);
			data = postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}
