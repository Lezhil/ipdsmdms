/**
 * 
 */
package com.bcits.mdas.service;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.LoadSurveyMonthlyConsumptionEntity;
import com.bcits.serviceImpl.GenericServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tarik
 *
 */
@Repository
public  class LoadSurveyMonthlyConsumptionServiceImpl extends GenericServiceImpl<LoadSurveyMonthlyConsumptionEntity> implements LoadSurveyMonthlyConsumptionService{

	@Override
	public LoadSurveyMonthlyConsumptionEntity getAssignRuleId(String meter_number, String billmonth) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("LoadSurveyMonthlyConsumptionEntity.getAssignRuleId", LoadSurveyMonthlyConsumptionEntity.class)
					.setParameter("mtrno", meter_number)
					.setParameter("billmonth", billmonth)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<?> getlocationwisedata(String locIdentity,String meterno,String loctype) {
		List<?> list=null;
		String qry="";
		try {
			//System.out.println("loctt--"+loctype+"  meterno=="+meterno);
			if(loctype.equalsIgnoreCase("FEEDER")){
			if (!meterno.isEmpty()) { 
				// System.out.println("metrrnooo fdr");
				qry="select  lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
						"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid\n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
						"where m.fdrcategory like '%FEEDER%' and lm.mtrno=m.mtrno and f.meterno=m.mtrno  and m.mtrno='"+meterno+"'ORDER BY lm.billmonth";
				
				  }
			else {
				 System.out.println("fdrid fdr");
				qry="select  lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
						"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid\n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
						"where  lm.mtrno=m.mtrno and f.meterno=m.mtrno  and  f.tp_fdr_id='"+locIdentity+"'ORDER BY lm.billmonth";

				//m.fdrcategory like '%FEEDER%' and
				}
			
			}
			
			else if(loctype.equalsIgnoreCase("DT")){
				if (!meterno.isEmpty()) { 
					// System.out.println("metrrnooo dt");
				qry="select  lm.billmonth,m.subdivision,d.parent_substation as substattion_code,d.dttpid,d.dtname,\n" +
						"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp as monthlyconcwith_mf,lm.kwh_exp,lm.id,d.officeid\n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails d \n" +
						"where m.fdrcategory like '%DT%' and lm.mtrno=m.mtrno and d.meterno=m.mtrno  and m.mtrno='"+meterno+"'ORDER BY lm.billmonth";
				
				}
				else {
					// System.out.println("dtid dt");
					qry="select lm.billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\n" +
							"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails f\n" +
							"where m.fdrcategory like '%DT%' and \n" +
							"lm.mtrno=m.mtrno and f.meterno=m.mtrno  and f.dttpid='"+locIdentity+"'ORDER BY lm.billmonth";
					
					}
				}
			
			if(loctype.equalsIgnoreCase("BOUNDARY")){
				if (!meterno.isEmpty()) { 
					// System.out.println("metrrnooo fdr");
					qry="select  lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
							"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
							"where m.fdrcategory like '%BOUNDARY%' and lm.mtrno=m.mtrno and f.meterno=m.mtrno  and m.mtrno='"+meterno+"'ORDER BY lm.billmonth";
					
					  }
				else {
					 System.out.println("fdrid fdr");
					qry="select  lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
							"m.mtrno,mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
							"where  lm.mtrno=m.mtrno and f.meterno=m.mtrno  and  f.tp_fdr_id='"+locIdentity+"'ORDER BY lm.billmonth";

					//m.fdrcategory like '%FEEDER%' and
					}
				
				}
			 System.out.println("location wise monthly consumption qry--"+qry);
			try {
				list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();	
			} catch (Exception e) {
			e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getdtLocationdata(String locIdentity, String meterno) {
		List<?> list=null;
		String qry="";
		try {
			if (!locIdentity.isEmpty()) { 
				 qry="select parent_substation as substation_name,(select s.ss_id from meter_data.substation_details s where s.ss_name=parent_substation) as SubsatationCode,\n" +
						 "(select subdivision from meter_data.master_main m where m.mtrno=meterno) as subdivision_name,\n" +
						 "officeid,\n" +
						 "dtname, billmonth, monthlyConsumption,\n" +
						 "dt_id,meterno,metermanufacture,(CASE  WHEN (mf is null )  THEN	''  else mf end) as mf from  (\n" +
						 "SELECT  dt.parentid , \n" +
						 "dt.officeid,dt.parent_substation,\n" +
						 "dt.dtname, mc.billmonth,mc.kwh as monthlyConsumption, \n" +
						 "dt.dt_id,dt.meterno,dt.metermanufacture,dt.mf\n" +
						 "from meter_data.dtdetails dt  \n" +
						 "INNER JOIN\n" +
						 "meter_data.load_survey_monthly_consumption mc on  mc.mtrno = dt.meterno and dt.dt_id = '"+locIdentity+"'\n" +
						 ")Z";
				  }
			else if (!meterno.isEmpty()) { 
				 
			        qry="select parent_substation as substation_name,(select s.ss_id from meter_data.substation_details s where s.ss_name=parent_substation) as SubsatationCode,\n" +
			        		"(select subdivision from meter_data.master_main m where m.mtrno=meterno) as subdivision_name,\n" +
			        		"officeid,\n" +
			        		"dtname, billmonth, monthlyConsumption,\n" +
			        		"dt_id,meterno,metermanufacture,(CASE  WHEN (mf is null )  THEN	''  else mf end) as mf from  (\n" +
			        		"SELECT  dt.parentid , \n" +
			        		"dt.officeid,dt.parent_substation,\n" +
			        		"dt.dtname, mc.billmonth,mc.kwh as monthlyConsumption, \n" +
			        		"dt.dt_id,dt.meterno,dt.metermanufacture,dt.mf\n" +
			        		"from meter_data.dtdetails dt  \n" +
			        		"INNER JOIN\n" +
			        		"meter_data.load_survey_monthly_consumption mc on  mc.mtrno = dt.meterno and dt.meterno = '"+meterno+"'\n" +
			        		")Z";
			       
				  }
			// System.out.println(qry);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

/*	@Override
	public List<?> getAreawisefeederdata(String circle,String division,String subdiv, String monthyr, String consumavail,String loc) {
		String qry=null;
		List<?> list=null;

		if(division.equals(0) || division==null){
			 division="%";
		
		}
		if(subdiv.equals(0) || subdiv==null  ){
			subdiv="%";
		}
		
		try {
			if(loc.equalsIgnoreCase("FEEDER")){
			
			 if(consumavail.equalsIgnoreCase("Available")){
				qry="select lm.billmonth,subdivision,f.parentid as substattion_code,f.fdr_id,f.feedername,\n" +
						"m.mtrno,mtrmake,m.mf,lm.kwh_imp*(cast(COALESCE( m.mf,'1') as numeric))  as monthlyconcwith_mf,lm.id,f.officeid\n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
						"where m.fdrcategory like '%FEEDER%' and m.circle like '"+circle+"' and\n" +
						"m.division like '"+division+"' and m.subdivision like '"+subdiv+"' and\n" +
						"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno;";
			}
			else if(consumavail.equalsIgnoreCase("NotAvailable")){
				qry="select TO_CHAR("+monthyr+",'999999') as  billmonth,subdivision,f.parentid as substattion_code,f.fdr_id,f.feedername,\n" +
						"m.mtrno,mtrmake,m.mf, mtrmake as kwh,mtrmake as id,f.officeid\n" +
						"from meter_data.master_main m,meter_data.feederdetails f\n" +
						"where m.fdrcategory like '%FEEDER%' and m.circle like '"+circle+"' and\n" +
						"m.division like '"+division+"' and m.subdivision like '"+subdiv+"' and f.meterno=m.mtrno \n" +
						"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma"
					    + " WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
			}
			
		}
			if(loc.equalsIgnoreCase("DT")){
				
				 if(consumavail.equalsIgnoreCase("Available")){
					qry="select lm.billmonth,m.subdivision,f.parentid as substattion_code,f.dt_id,f.dtname,\n" +
							"m.mtrno,mtrmake,m.mf,lm.kwh_imp*(cast(COALESCE( m.mf,'1') as numeric))  as monthlyconcwith_mf,lm.id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails f\n" +
							"where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
							"m.division like '"+division+"' and m.subdivision like '"+subdiv+"' and\n" +
							"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno;";
				}
				else if(consumavail.equalsIgnoreCase("NotAvailable")){
					qry="select TO_CHAR("+monthyr+",'999999') as  billmonth,m.subdivision,f.parentid as substattion_code,f.dt_id,f.dtname,\n" +
							"m.mtrno,mtrmake,m.mf,mtrmake as kwh,mtrmake as id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.dtdetails f\n" +
							"where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
							"m.division like '"+division+"' and m.subdivision like '"+subdiv+"'\n" +
							"and f.meterno=m.mtrno \n" +
							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \n" +
							"WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
				}
				
			}
			System.out.println("getAreawisefeederdata---"+qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/

/*	@Override
	public List<?> getAreawisefeederdata(String circle,String townCode, String monthyr, String consumavail,String loc) {
		String qry=null;
		List<?> list=null;

		if(division.equals(0) || division==null){
			 division="%";
		
		}
		if(subdiv.equals(0) || subdiv==null  ){
			subdiv="%";
		}
		
		try {
			if(loc.equalsIgnoreCase("FEEDER")){
			
			 if(consumavail.equalsIgnoreCase("Available")){
				qry="select distinct on(m.mtrno)m.mtrno, lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
						"(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf, (CASE WHEN (lm.kwh_exp IS NULL) THEN '0' ELSE lm.kwh_exp  end)\r\n" + 
						",lm.id,f.officeid,flag\n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
						"where  m.circle like '"+circle+"' and\n" +
						"m.town_code like '"+townCode+"'  and\n" +
						"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno and m.fdrcategory like 'FEEDER METER';";
			//	System.out.println(qry);
			}
			else if(consumavail.equalsIgnoreCase("NotAvailable")){
				
				qry="select distinct on(m.mtrno)m.mtrno,TO_CHAR("+monthyr+",'999999') as  billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,vl_id as kwh, (CASE WHEN (mc.kwh_exp IS NULL) THEN '0' ELSE mc.kwh_exp  end)\r\n" + 
						",mc.id,f.officeid\r\n" + 
						"from meter_data.master_main m,meter_data.dtdetails f LEFT JOIN meter_data.monthly_consumption mc ON mc.mtrno=f.meterno where m.fdrcategory like '%DT%' and m.circle like  '"+circle+"'  and m.town_code like '"+townCode+"' and f.meterno=m.mtrno \r\n" + 
						"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \r\n" + 
						"WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') \r\n" + 
						"";
			}
			
		}
			if(loc.equalsIgnoreCase("DT")){
				
				 if(consumavail.equalsIgnoreCase("Available")){
					qry="select distinct on(m.mtrno)m.mtrno,lm.billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\n" +
							"(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf, (CASE WHEN (lm.kwh_exp IS NULL) THEN '0' ELSE lm.kwh_exp  end)\r\n" + 
							",lm.id,f.officeid,lm.flag\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails f\n" +
							"where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
							"m.town_code like  '"+townCode+"' and\n" +
							"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno;";
				}
				else if(consumavail.equalsIgnoreCase("NotAvailable")){
					qry="select distinct on(m.mtrno)m.mtrno,TO_CHAR("+monthyr+",'999999') as  billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\n" +
							"(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,(CASE WHEN(mc.imp_kwh IS NULL) THEN '0' ELSE mc.imp_kwh end), (CASE WHEN (mc.kwh_exp IS NULL) THEN '0' ELSE mc.kwh_exp  end)\r\n" + 
							",mc.id,f.officeid\n" +
							"from meter_data.master_main m,meter_data.dtdetails f LEFT JOIN meter_data.monthly_consumption mc ON mc.mtrno=f.meterno \n" +
							"where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
							"m.town_code like '"+townCode+"'\n" +
							"and f.meterno=m.mtrno \n" +
							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \n" +
							"WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
				}
				
			}
			
			if(loc.equalsIgnoreCase("BOUNDARY")){
				
				 if(consumavail.equalsIgnoreCase("Available")){
					qry="select distinct on(m.mtrno)m.mtrno, lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
							"(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf, (CASE WHEN (lm.kwh_exp IS NULL) THEN '0' ELSE lm.kwh_exp  end)\r\n" + 
							",lm.id,f.officeid,flag\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
							"where  m.circle like '"+circle+"' and\n" +
							"m.town_code like '"+townCode+"'  and\n" +
							"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno and m.fdrcategory like 'BOUNDARY METER'; ";
				//	System.out.println(qry);
				}
				else if(consumavail.equalsIgnoreCase("NotAvailable")){
					qry="select distinct on(m.mtrno)m.mtrno ,TO_CHAR("+monthyr+",'999999') as  billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
							"(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,district,mc.id, (CASE WHEN (mc.kwh_exp IS NULL) THEN '0' ELSE mc.kwh_exp  end)\r\n" + 
							",f.officeid\n" +
							"from meter_data.master_main m,meter_data.feederdetails f LEFT JOIN meter_data.monthly_consumption mc ON mc.mtrno=f.meterno \n" +
							"where  m.circle like '"+circle+"' and\n" +
							"m.town_code like '"+townCode+"' and f.meterno=m.mtrno and fdrcategory='BOUNDARY METER' \n" +
							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma"
						    + " WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
				}
				
			}
			System.out.println("getAreawisefeederdata---"+qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	*/
	
	
	
	
	@Override
	public List<?> getAreawisefeederdata(String circle,String townCode, String monthyr, String consumavail,String loc) {
		String qry=null;
		List<?> list=null;

		/*if(division.equals(0) || division==null){
			 division="%";
		
		}
		if(subdiv.equals(0) || subdiv==null  ){
			subdiv="%";
		}*/
		
		try {
			if(loc.equalsIgnoreCase("FEEDER")){
			
			 if(consumavail.equalsIgnoreCase("Available")){
				qry="select lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
						"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,(CASE  WHEN (lm.kwh_imp is  NULL)  THEN	0  else lm.kwh_imp end) as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid,lm.flag  \n" +
						"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
						"where  m.circle like '"+circle+"' and\n" +
						"m.town_code like '"+townCode+"'  and\n" +
						"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno and m.fdrcategory like 'FEEDER METER';";
				
			}
			else if(consumavail.equalsIgnoreCase("NotAvailable")){
				qry="select TO_CHAR("+monthyr+",'999999') as  billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
						"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'0'  else m.mf end) as mf,(CASE  WHEN (vl_id is null )  THEN	'0'  else m.mf end) as kwh,f.kwh_exp,district,mtrmake as id,f.officeid\n" +
						"from meter_data.master_main m,meter_data.feederdetails f\n" +
						"where  m.circle like '"+circle+"' and\n" +
						"m.town_code like '"+townCode+"' and f.meterno=m.mtrno and fdrcategory='FEEDER METER'\n" +
						"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma"
					    + " WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
			}
			
		}
			if(loc.equalsIgnoreCase("DT")){
				
				 if(consumavail.equalsIgnoreCase("Available")){
					qry="select lm.billmonth,m.subdivision,f.tpparentid as substattion_code,f.dttpid,f.dtname,\n" +
							"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid,lm.flag\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails f\n" +
							"where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
							"m.town_code like  '"+townCode+"' and\n" +
							"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno;";
				}
				else if(consumavail.equalsIgnoreCase("NotAvailable")){
					/*
					 * qry="select distinct TO_CHAR("
					 * +monthyr+",'999999') as  billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\n"
					 * +
					 * "m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,vl_id as kwh,kwh_exp,mtrmake as id,f.officeid\n"
					 * + "from meter_data.master_main m,meter_data.dtdetails f\n" +
					 * "where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\n" +
					 * "m.town_code like '"+townCode+"'\n" + "and f.meterno=m.mtrno \n" +
					 * "and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \n"
					 * + "WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
					 */
					
					
					/*qry ="\r\n" + 
							"select a.*,COALESCE(b.kwh_exp,0) as kwh_exp from \r\n" + 
							"(select TO_CHAR("+monthyr+",'999999') as  billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\r\n" + 
							"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,vl_id as kwh,f.id,f.officeid\r\n" + 
							"from meter_data.master_main m,meter_data.dtdetails f where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and\r\n" + 
							"m.town_code like '"+townCode+"'\r\n" + 
							"and f.meterno=m.mtrno \r\n" + 
							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \r\n" + 
							"WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"'))a LEFT JOIN\r\n" + 
							"\r\n" + 
							"(select kwh_exp,mtrno from meter_data.monthly_consumption where billmonth='"+monthyr+"')b ON(a.mtrno=b.mtrno) ";*/
					
					
					qry="select distinct TO_CHAR(\r\n" + 
							"					 "+monthyr+",'999999') as  billmonth,m.subdivision,f.tpparentid as substattion_code,f.dttpid,f.dtname,\r\n" + 
							"					 \r\n" + 
							"					 m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,COALESCE(CAST(f.vl_id as numeric),0)as kwh,COALESCE(CAST(f.kwh_exp as numeric),0) as kwh_exp,mtrmake as id,f.officeid\r\n" + 
							"					  from meter_data.master_main m,meter_data.dtdetails f \r\n" + 
							"					 where m.fdrcategory like '%DT%' and m.circle like '"+circle+"' and \r\n" + 
							"					 m.town_code like '"+townCode+"'  and f.meterno=m.mtrno  \r\n" + 
							"					 and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \r\n" + 
							"					  WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ; ";
					
					
				}
				
			}
			
			if(loc.equalsIgnoreCase("BOUNDARY")){
				
				 if(consumavail.equalsIgnoreCase("Available")){
					qry="select lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
							"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,(CASE  WHEN (lm.kwh_imp is  NULL)  THEN	0  else lm.kwh_imp end)  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid,lm.flag\n" +
							"from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\n" +
							"where  m.circle like '"+circle+"' and\n" +
							"m.town_code like '"+townCode+"'  and\n" +
							"lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and f.meterno=m.mtrno and m.fdrcategory like 'BOUNDARY METER'; ";
				//	System.out.println(qry);
				}
				else if(consumavail.equalsIgnoreCase("NotAvailable")){
//					qry="select distinct TO_CHAR("+monthyr+",'999999') as  billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\n" +
//							"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,f.imp_exp,district,mtrmake as id,f.officeid\n" +
//							"from meter_data.master_main m,meter_data.feederdetails f\n" +
//							"where  m.circle like '"+circle+"' and\n" +
//							"m.town_code like '"+townCode+"' and f.meterno=m.mtrno and fdrcategory='BOUNDARY METER' \n" +
//							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma"
//						    + " WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"') ";
					
					
					qry="\r\n" + 
							"select a.*,COALESCE(b.kwh_exp,0) as kwh_exp from \r\n" + 
							"(select TO_CHAR("+monthyr+",'999999') as  billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\r\n" + 
							"m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,vl_id as kwh,mtrmake as id,f.officeid\r\n" + 
							"from meter_data.master_main m,meter_data.dtdetails f where m.fdrcategory like 'BOUNDARY METER' and m.circle like '"+circle+"' and\r\n" + 
							"m.town_code like '"+townCode+"'\r\n" + 
							"and f.meterno=m.mtrno \r\n" + 
							"and m.mtrno NOT IN(select distinct ma.mtrno from meter_data.monthly_consumption mc,meter_data.master_main ma \r\n" + 
							"WHERE mc.mtrno=ma.mtrno and mc.billmonth='"+monthyr+"'))a LEFT JOIN\r\n" + 
							"\r\n" + 
							"(select kwh_exp,mtrno from meter_data.monthly_consumption where billmonth='"+monthyr+"')b ON(a.mtrno=b.mtrno) "; 
				}
				
			}
			System.out.println("getAreawisefeederdata---"+qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/*
	 * @Override public List<?> getAreawisefeederdataTest(String circle, String
	 * townCode, String monthyr, String loc) { String qry=null; List<?> list=null;
	 * 
	 * try {
	 * 
	 * if(loc.equalsIgnoreCase("FEEDER")){
	 * 
	 * qry="select DISTINCT m.mtrno,lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\r\n"
	 * +
	 * "					(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,lm.adjacent_value_imp,lm.adjacent_value_exp\r\n"
	 * +
	 * "						from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\r\n"
	 * + "						where  m.circle like '"+circle+"' and\r\n" +
	 * "						m.town_code like '"+townCode+"'  and\r\n" +
	 * "						lm.mtrno=m.mtrno and lm.billmonth='"
	 * +monthyr+"' and f.meterno=m.mtrno and m.fdrcategory like 'FEEDER METER'; ";
	 * 
	 * } System.out.println("getAreawisefeederdataTest---"+qry); list=
	 * postgresMdas.createNativeQuery(qry).getResultList();
	 * 
	 * }catch(Exception e) { e.printStackTrace(); } return list; }
	 * 
	 */
	

	@Override
	public List<?> getAreawisedtdata(String circle,String division,String subdiv, String monthyr,String consumavail) {
		String qry=null;
		List<?> list=null;
		try {
			if(consumavail.equalsIgnoreCase("ALL")){
				qry="SELECT * from\n" +
						"(SELECT distinct parent_substation as substation_name, (select s.ss_id from meter_data.substation_details s where s.ss_name=parent_substation) as SubsatationCode,\n" +
						"(select subdivision from meter_data.master_main m where m.mtrno=meterno) as subdivision_name,\n" +
						"dt.officeid,\n" +
						"dt.dtname, mc.billmonth,mc.kwh as monthlyConsumption, \n" +
						"dt.dt_id,dt.meterno,dt.metermanufacture,dt.mf,\n" +
						"(select circle from meter_data.master_main m where m.mtrno=meterno) as circle,\n" +
						"(select division from meter_data.master_main m where m.mtrno=meterno) as division\n" +
						"from meter_data.dtdetails dt  \n" +
						"INNER JOIN\n" +
						"meter_data.load_survey_monthly_consumption mc on  mc.mtrno = dt.meterno \n" +
						")A  WHERE  A.circle like '"+circle+"' \n" +
						"and division like '"+division+"'\n" +
						"and subdivision_name like '"+subdiv+"'\n" +
						"and A.billmonth='"+monthyr+"'";
			}
			else if(consumavail.equalsIgnoreCase("Available")){
				qry="SELECT * from\n" +
						"(SELECT distinct parent_substation as substation_name, (select s.ss_id from meter_data.substation_details s where s.ss_name=parent_substation) as SubsatationCode,\n" +
						"(select subdivision from meter_data.master_main m where m.mtrno=meterno) as subdivision_name,officeid,\n" +
						"dt.dtname, mc.billmonth,mc.kwh as monthlyConsumption, \n" +
						"dt.dt_id,dt.meterno,dt.metermanufacture,dt.mf,(select circle from meter_data.master_main m where m.mtrno=meterno) as circle,\n" +
						"(select division from meter_data.master_main m where m.mtrno=meterno) as division\n" +
						"from meter_data.dtdetails dt  \n" +
						"INNER JOIN\n" +
						"meter_data.load_survey_monthly_consumption mc on  mc.mtrno = dt.meterno \n" +
						")A WHERE  A.monthlyConsumption is  not  null  \n" +
						"and A.circle like '"+circle+"' \n" +
						"and division like '"+division+"'\n" +
						"and subdivision_name like '"+subdiv+"'\n" +
						"and A.billmonth='"+monthyr+"'";
			}
			else{
				qry="SELECT * from\n" +
						"(SELECT distinct parent_substation as substation_name, (select s.ss_id from meter_data.substation_details s where s.ss_name=parent_substation) as SubsatationCode,\n" +
						"(select subdivision from meter_data.master_main m where m.mtrno=meterno) as subdivision_name,officeid,\n" +
						"dt.dtname, mc.billmonth,mc.kwh as monthlyConsumption, \n" +
						"dt.dt_id,dt.meterno,dt.metermanufacture,dt.mf,(select circle from meter_data.master_main m where m.mtrno=meterno) as circle,\n" +
						"(select division from meter_data.master_main m where m.mtrno=meterno) as division\n" +
						"from meter_data.dtdetails dt  \n" +
						"INNER JOIN\n" +
						"meter_data.load_survey_monthly_consumption mc on  mc.mtrno = dt.meterno \n" +
						")A WHERE  A.monthlyConsumption is null  \n" +
						"and A.circle like '"+circle+"' \n" +
						"and division like '"+division+"'\n" +
						"and subdivision_name like '"+subdiv+"'\n" +
						"and A.billmonth='"+monthyr+"'";
			}
			//System.out.println(qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public BigDecimal togetConsumption(String id) {
		String qry=null;
		
		BigDecimal kwh=null;
	
		try {
			qry="(select distinct (CASE  WHEN (kwh_imp is  NULL)  THEN	0  else kwh_imp end) as kwh_imp from meter_data.monthly_consumption where id='"+id+"');";
	
			System.out.println("monthly consumprtion get kwh--"+qry);
		
			kwh= (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kwh;
	}	
	
	@Override
	public BigDecimal togetConsumpt(String id) {
		String qry=null;
		
		BigDecimal kwh=null;
	
		try {
			//qry="select kwh from meter_data.load_survey_monthly_consumption where mtrno = '"+meterno+"' and billmonth='"+month+"'";
			qry="(select adjacent_value_imp from meter_data.monthly_consumption where id='"+id+"');";
	
			System.out.println("monthly consumprtion get kwh--"+qry);
		
			kwh= (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kwh;
	}	
	
	
	
	

	
	@Override
	public int updateConsumption(String consumption,String consumpt,String oldconsumption,String oldconsumpt,String remark,String id,String mtrno,String loccode,String month){
		int i=0;
		String qry="";
		
		try {
			if(oldconsumption.equalsIgnoreCase("null") && oldconsumption.equalsIgnoreCase("")){
				oldconsumption="0.0";
			}
			if(id!=""){
			//qry="update meter_data.load_survey_monthly_consumption set kwh='"+Consumption+"',remarks='"+edittext+"' where mtrno = '"+meterno+"' and billmonth = '"+month+"'";
			qry="UPDATE meter_data.monthly_consumption SET old_kwh='"+oldconsumption+"',kwh_imp='"+consumption+"',adjacent_value_imp='"+consumption+"',remarks='"+remark+"',flag='Manually Edited',updatedby='bcitsAMR',updatedtime=now() WHERE id='"+id+"'";
			}
			else
			{
			qry="INSERT INTO meter_data.monthly_consumption(id,location_code,billmonth,mtrno,kwh_imp,old_kwh,flag,updatedby,updatedtime,remarks) \n" +
				"VALUES (nextval('meter_data.monthly_consumption_seq'),'"+loccode+"','"+month+"','"+mtrno+"','"+consumption+"','0','Manually Edited','bcitsAMR','now()','"+remark+"')";
			}
			
			System.out.println("qry--"+qry);
			i=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	

	@Override
	public int updateEnergy(String oldconsumption,String consumption,String id,String mtrno,String month){
		
		int i=0;
		String qry="";
		try {
			qry="UPDATE meter_data.input_energy SET old_kwh='"+oldconsumption+"',adjacent_value_imp='"+consumption+"',kwh_imp = kwh_imp+'"+consumption+"' WHERE id='"+id+"'";
			
			System.out.println("qry--"+qry);
			i=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		return i;
	}

	@Override
	public int updateEnergyExp(String oldconsumpt,String consumpt,String id,String mtrno,String month){
		
		int i=0;
		String qry="";
		try {
			qry="UPDATE meter_data.input_energy SET old_kwh_exp='"+oldconsumpt+"',adjacent_value_exp='"+consumpt+"',kwh_imp = kwh_imp-'"+consumpt+"' WHERE id='"+id+"'";
			
			System.out.println("qry--"+qry);
			i=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		return i;
	}




	@Override
	public List<?> dailysubenergy(String substation, String fromdate, String todate) {
		List<?> list=null;
		String qry="";
		try {
			/*qry="select C.date,parent_subdivision,\n" +
					"office_id,ss_name,CumulativeEnergyIC,CumulativeEnergyOG,\n" +
					"cast((CumulativeEnergyIC-CumulativeEnergyOG)as VARCHAR) as Totalsubstation,\n" +
					"((CumulativeEnergyIC-CumulativeEnergyOG)/CumulativeEnergyIC)*100 lossperc\n" +
					"from \n" +
					"(select sum(kwh) as CumulativeEnergyOG,s.parent_subdivision,s.office_id as office_id ,s.ss_name\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  s.ss_id=fd.parentid and  mc.mtrno=fd.meterno  GROUP BY \n" +
					"s.parent_subdivision,office_id ,s.ss_name)A,\n" +
					"(select sum(kwh) as CumulativeEnergyIC\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  CAST(s.parent_id as VARCHAR)=fd.fdr_id  and mc.mtrno=fd.meterno   \n" +
					")B,\n" +
					"(select distinct date from meter_data.daily_consumption dc,meter_data.feederdetails fd WHERE dc.mtrno=fd.meterno)C\n" +
					"where ss_name='"+substation+"'\n" +
					"and  to_char(date,'yyyy-MM-dd')>='"+fromdate+"' and to_char(date,'yyyy-MM-dd')<='"+todate+"'";*/
			
			qry=	"select C.*,\n" +
					"(COALESCE(C.total_sub_loss,0)/(case WHEN COALESCE(C.incomingfdrkwh,0)=0 THEN (case WHEN C.outgoingfdrkwh=0 THEN 1 \n"+
					"ELSE C.outgoingfdrkwh END) ELSE C.incomingfdrkwh END))*100 as loss_per from\n" +
					"(select A.*,B.incomingfdrkwh,(COALESCE(B.incomingfdrkwh,0)-A.outgoingfdrkwh) AS total_sub_loss from\n" +
					"(select TO_CHAR(l.read_time,'dd-mm-yyyy') as ldate,s.parent_subdivision,s.office_id as subdivcode,s.ss_name,s.ss_id, COALESCE(sum(l.kwh_imp),0) as outgoingfdrkwh\n" +
					"FROM meter_data.feederdetails f,meter_data.substation_details s,meter_data.load_survey l\n" +
					"where f.parentid=s.ss_id and  l.meter_number=f.meterno  \n" +
					"and to_char(l.read_time,'yyyy-MM-dd')>='"+fromdate+"' and to_char(l.read_time,'yyyy-MM-dd')<='"+todate+"'\n" +
					"and s.ss_name like '"+substation+"'\n" +
					"GROUP BY ldate,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id)A\n" +
					"LEFT JOIN \n" +
					"(select s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,COALESCE(sum(l.kwh_imp),0) as incomingfdrkwh\n" +
					"from meter_data.substation_details s,meter_data.feederdetails f,meter_data.load_survey l \n" +
					"where s.parent_feeder=f.feedername and s.ss_name like '"+substation+"' \n" +
					"and l.meter_number=f.meterno and to_char(l.read_time,'yyyy-MM-dd')>='"+fromdate+"' and to_char(l.read_time,'yyyy-MM-dd')<='"+todate+"'\n" +
					"GROUP BY subdivcode,s.parent_subdivision,s.ss_name,s.ss_id)B\n" +
					"ON A.ss_name=B.ss_name\n" +
					" )C;";
			
			System.out.println("substation enery loss Daily--"+qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> dailyfeedersubenergy( String substation, String fromdate, String todate) {
		List<?> list=null;
		String qry="";
		try {
			/*qry="select date,parent_subdivision,office_id,ss_name,ss_id,feedername,fdr_id,voltagelevel,mf,consumption\n" +
					"from\n" +
					"(select sum(kwh) consumption,s.parent_subdivision,s.office_id as office_id ,s.ss_name,fd.feedername,fd.fdr_id,fd.voltagelevel,fd.mf,s.ss_id\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  s.ss_id=fd.parentid   and mc.mtrno=fd.meterno GROUP BY \n" +
					"s.parent_subdivision,office_id ,s.ss_name,ss_id,fd.feedername,fd.fdr_id,fd.voltagelevel,fd.mf )A,\n" +
					"(select distinct date from meter_data.daily_consumption dc,meter_data.feederdetails fd WHERE dc.mtrno=fd.meterno)C\n" +
					" WHERE  ss_name='"+substation+"'\n" +
					"and  to_char(date,'yyyy-MM-dd')>='"+fromdate+"' and to_char(date,'yyyy-MM-dd')<='"+todate+"'";
			*/
			qry="(select to_char(l.read_time,'dd-mm-yyyy') as frdate,s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,'incoming' as fdrtype,f.voltagelevel,f.mf,COALESCE(sum(l.kwh_imp),0) as incomingfdrkwh\n" +
					"from meter_data.substation_details s,meter_data.feederdetails f,meter_data.load_survey l \n" +
					"where s.parent_feeder=f.feedername and s.ss_name like '"+substation+"' \n" +
					"and l.meter_number=f.meterno and to_char(l.read_time,'yyyy-MM-dd')>='"+fromdate+"' and to_char(l.read_time,'yyyy-MM-dd')<='"+todate+"'\n" +
					"GROUP BY frdate,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,fdr_id,f.voltagelevel,f.mf)\n" +
					"UNION ALL\n" +
					"--daily feeder outgoing \n" +
					"(select to_char(l.read_time,'dd-mm-yyyy') as frdate,s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,'outgoing' as fdrtype,f.voltagelevel,f.mf,COALESCE(sum(l.kwh_imp),0) as incomingfdrkwh\n" +
					"FROM meter_data.feederdetails f,meter_data.substation_details s,meter_data.load_survey l\n" +
					"where f.parentid=s.ss_id and  l.meter_number=f.meterno  \n" +
					"and to_char(l.read_time,'yyyy-MM-dd')>='"+fromdate+"' and to_char(l.read_time,'yyyy-MM-dd')<='"+todate+"'\n" +
					"and s.ss_name like '"+substation+"'\n" +
					"GROUP BY frdate,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,fdrtype,f.voltagelevel,f.mf)";
			System.out.println("dailyfeedersubenergy- qry:"+qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> monthlysubenergy( String substation, String fromdate, String todate) {
		List<?> list=null;
		String qry="";
		try {
			/*qry="select billmonth,parent_subdivision,office_id,ss_name,CumulativeEnergyIC,CumulativeEnergyOG,\n" +
					"cast((CumulativeEnergyIC-CumulativeEnergyOG)as VARCHAR) as Totalsubstation,\n" +
					"((CumulativeEnergyIC-CumulativeEnergyOG)/CumulativeEnergyIC)*100 lossperc,mtrno\n" +
					"from \n" +
					"(select sum(kwh) as CumulativeEnergyOG,s.parent_subdivision,s.office_id as office_id ,s.ss_name,mc.billmonth,mc.mtrno\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  s.ss_id=fd.parentid and  mc.mtrno=fd.meterno  GROUP BY \n" +
					"s.parent_subdivision,office_id ,s.ss_name,billmonth,mc.mtrno)A,\n" +
					"(select sum(kwh) as CumulativeEnergyIC\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  CAST(s.parent_id as VARCHAR)=fd.fdr_id  and mc.mtrno=fd.meterno   \n" +
					")B WHERE ss_name='"+substation+"'\n" +
					"and billmonth BETWEEN '"+fromdate+"'  and '"+todate+"'";*/
			
			qry="select C.*,\n" +
					"(COALESCE(C.total_sub_loss,0)/(case WHEN COALESCE(C.incomingfdrkwh,0)=0 THEN C.outgoingfdrkwh ELSE C.incomingfdrkwh END))*100 as loss_per from\n" +
					"(select A.*,B.incomingfdrkwh,(COALESCE(B.incomingfdrkwh,0)-A.outgoingfdrkwh) AS total_sub_loss from\n" +
					"(select l.billmonth,s.parent_subdivision,s.office_id as subdivcode,s.ss_name,s.ss_id, COALESCE(sum(l.kwh),0) as outgoingfdrkwh\n" +
					"FROM meter_data.feederdetails f,meter_data.substation_details s,meter_data.load_survey_monthly_consumption l\n" +
					"where f.parentid=s.ss_id and  l.mtrno=f.meterno and l.billmonth BETWEEN '"+fromdate+"'  and '"+todate+"'\n" +
					"and s.ss_name like '"+substation+"'\n" +
					"GROUP BY l.billmonth,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id)A\n" +
					"LEFT JOIN \n" +
					"(select s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,COALESCE(sum(l.kwh),0) as incomingfdrkwh\n" +
					"from meter_data.substation_details s,meter_data.feederdetails f,meter_data.load_survey_monthly_consumption l \n" +
					"where s.parent_feeder=f.feedername and s.ss_name like '"+substation+"' \n" +
					"and l.mtrno=f.meterno and l.billmonth BETWEEN '"+fromdate+"'  and '"+todate+"'\n" +
					"GROUP BY subdivcode,s.parent_subdivision,s.ss_name,s.ss_id)B\n" +
					"ON A.ss_name=B.ss_name\n" +
					" )C";
			System.out.println("monthly substation energy queryyy--"+qry);
			
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	@Override
	public List<?> mntlyfeedersubenergy( String substation, String fromdate, String todate) {
		List<?> list=null;
		String qry="";
		try {
			/*qry="select mc.billmonth,s.parent_subdivision,s.office_id as office_id ,s.ss_name,s.ss_id,fd.feedername,fd.fdr_id,fd.voltagelevel,fd.mf,sum(kwh) consumption\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  s.ss_id=fd.parentid   and mc.mtrno=fd.meterno and ss_name='"+substation+"' and billmonth BETWEEN '"+fromdate+"'  and '"+todate+"' GROUP BY \n" +
					"s.parent_subdivision,office_id ,s.ss_name,fd.feedername,fd.fdr_id,fd.voltagelevel,fd.mf,mc.billmonth,s.ss_id ";
			*/
			qry="(select to_char(l.read_time,'yyyyMM') as frdate,s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,'incoming' as fdrtype,f.voltagelevel,f.mf,COALESCE(sum(l.kwh_imp),0) as incomingfdrkwh\n" +
					"from meter_data.substation_details s,meter_data.feederdetails f,meter_data.load_survey l \n" +
					"where s.parent_feeder=f.feedername and s.ss_name like '"+substation+"' \n" +
					"and l.meter_number=f.meterno and to_char(l.read_time,'yyyyMM')>='"+fromdate+"' and to_char(l.read_time,'yyyyMM')<='"+todate+"'\n" +
					"GROUP BY frdate,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,fdr_id,f.voltagelevel,f.mf)\n" +
					"UNION ALL\n" +
					"--daily feeder outgoing \n" +
					"(select to_char(l.read_time,'yyyyMM') as frdate,s.office_id as subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,'outgoing' as fdrtype,f.voltagelevel,f.mf,COALESCE(sum(l.kwh_imp),0) as incomingfdrkwh\n" +
					"FROM meter_data.feederdetails f,meter_data.substation_details s,meter_data.load_survey l\n" +
					"where f.parentid=s.ss_id and  l.meter_number=f.meterno  \n" +
					"and to_char(l.read_time,'yyyyMM')>='"+fromdate+"' and to_char(l.read_time,'yyyyMM')<='"+todate+"'\n" +
					"and s.ss_name like '"+substation+"'\n" +
					"GROUP BY frdate,subdivcode,s.parent_subdivision,s.ss_name,s.ss_id,f.feedername,f.fdr_id,fdrtype,f.voltagelevel,f.mf)";
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> viewconsumption(String mtrno, String billmonth) {
		List<?> list=null;
		String qry="";
		try {
			qry="select CumulativeEnergyIC,CumulativeEnergyOG,\n" +
					"cast((CumulativeEnergyIC-CumulativeEnergyOG)as VARCHAR) as Totalsubstation,\n" +
					"((CumulativeEnergyIC-CumulativeEnergyOG)/CumulativeEnergyIC)*100 lossperc,mtrno,billmonth\n" +
					"from \n" +
					"(select sum(kwh) as CumulativeEnergyOG,mtrno,billmonth\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  s.ss_id=fd.parentid and  mc.mtrno=fd.meterno group by mtrno,billmonth )A,\n" +
					"(select sum(kwh) as CumulativeEnergyIC\n" +
					"from meter_data.substation_details s,meter_data.feederdetails fd,meter_data.load_survey_monthly_consumption mc\n" +
					"where  CAST(s.parent_id as VARCHAR)=fd.fdr_id  and mc.mtrno=fd.meterno   \n" +
					")B where mtrno='"+mtrno+"' and billmonth='"+billmonth+"'";
			list= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
           e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> gethtloss(String region, String circle,String fdrid) {
		List<?> list=null;
		String qry="";
		try {
			qry="select * from meter_data.ht_losses where region like '"+region+"' and circle like '"+circle+"' and feeder_code='"+fdrid+"'";
			list= postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println(qry);
		} catch (Exception e) {
           e.printStackTrace();
		}
		return list;
	}
	
	

	@Override
	public List<?> dailysubenergysample(String zone, String circle, String town, String monthid) {
		
		List<?> list=null;
		String qry="";
		try {
			qry="select substation_name,substation_id,energy_incomming_feeder,energy_outgoing_feeder,loss,loss_per from meter_data.bus_bar_loss where region like '"+zone+"' and circle like '"+circle+"' and town like '"+town+"' and month_year='"+monthid+"'";
			list= postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println(qry);
		} catch (Exception e) {
           e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<?> getdtloss(String zone, String circle, String town, String monthyear, String feederTpId,
			String periodMonth) {
		List<?> list=null;
		String qry="";
		try {
		 qry="select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,	\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"				CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'14 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR)\r\n" + 
				" as input_eng_per,\r\n" + 
				" CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'13 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_lt,\r\n" + 
				" \r\n" + 
				" CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'12 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"						\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_12months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							 )A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='12' and total_unit_supply > 0\r\n" + 
				"							\r\n" + 
				"							\r\n" + 
				"						UNION ALL \r\n" + 
				"							\r\n" + 
				"		select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'12 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'11 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'10 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"\r\n" + 
				"		FROM meter_data.rpt_eadt_losses_10months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							 )A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='10'	and total_unit_supply > 0\r\n" + 
				"							\r\n" + 
				"							\r\n" + 
				"													\r\n" + 
				"	UNION ALL\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				"	select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'10 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'9 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'8 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_08months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							 )A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='08' and total_unit_supply > 0\r\n" + 
				"					\r\n" + 
				"					UNION ALL\r\n" + 
				"			\r\n" + 
				"			\r\n" + 
				"			select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'8 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'7 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'6 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_06months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							 )A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='06' and total_unit_supply > 0\r\n" + 
				"							\r\n" + 
				"						\r\n" + 
				"							UNION ALL\r\n" + 
				"							\r\n" + 
				"							\r\n" + 
				"							select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'6 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'5 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'4 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_04months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							 )A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='04' and total_unit_supply > 0\r\n" + 
				"							\r\n" + 
				"							\r\n" + 
				"							\r\n" + 
				"								\r\n" + 
				"							UNION ALL\r\n" + 
				"							\r\n" + 
				"							select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'4 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_02months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							)A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like'"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='02' and total_unit_supply > 0\r\n" + 
				"					\r\n" + 
				"						UNION ALL		\r\n" + 
				"							\r\n" + 
				"					select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,(loss/nullif(total_unit_supply,0))* 100 as loss_per from\r\n" + 
				"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
				"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
				"							total_consumer_count,feedername,\r\n" + 
				"round(total_unit_supply,2)total_unit_supply,\r\n" + 
				"round(total_unit_billed,2)total_unit_billed,\r\n" + 
				"round(total_amount_billed,2)total_amount_billed,\r\n" + 
				"round(total_amount_collected,2)total_amount_collected,\r\n" + 
				"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"							tech_loss,\r\n" + 
				"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
				"							time_stamp,meterno,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
				"\r\n" + 
				"							FROM meter_data.rpt_eadt_losses_00months where town_code  like'"+town+"'\r\n" + 
				"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
				"							)A )X ,\r\n" + 
				"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode like '"+town+"')Y\r\n" + 
				"							WHERE Y.sitecode=X.office_id\r\n" + 
				"							AND '"+periodMonth+"'='00' and total_unit_supply > 0";
		 
		 
		System.out.println(qry);

		 list= postgresMdas.createNativeQuery(qry).getResultList();
		
		}catch (Exception e) {
	           e.printStackTrace();
		}
		return list;
	}

	

	@Override
	public List<?> getMonthlyConsumptionReport(String circle, String townCode, String monthyr,
			String loc) {
		String qry=null;
		List<?> list=null;
		//System.out.println(loc);
		try {
			if(loc.equalsIgnoreCase("FEEDER METER") || loc.equalsIgnoreCase("BOUNDARY METER")) {
			qry="select lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\r\n" + 
					"						m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	''  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid,flag,lm.old_kwh,lm.updatedby,lm.updatedtime\r\n" + 
					"						from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.feederdetails f\r\n" + 
					"						where  m.circle like '"+circle+"' and\r\n" + 
					"						m.town_code like '"+townCode+"'  and\r\n" + 
					"						lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and flag = 'Manually Edited' and f.meterno=m.mtrno and m.fdrcategory like '"+loc+"';";
			}else {
				
				qry="select lm.billmonth,m.subdivision,f.parent_substation as substattion_code,f.dttpid,f.dtname,\r\n" + 
						"							m.mtrno,(CASE  WHEN (mtrmake is null )  THEN	''  else mtrmake end) as mtrmake,(CASE  WHEN (m.mf is null )  THEN	'1'  else m.mf end) as mf,lm.kwh_imp  as monthlyconcwith_mf,lm.kwh_exp,lm.id,f.officeid,flag,lm.updatedby,lm.updatedtime\r\n" + 
						"							from meter_data.master_main m,meter_data.monthly_consumption lm,meter_data.dtdetails f\r\n" + 
						"							where m.fdrcategory like '"+loc+"' and m.circle like '"+circle+"' and\r\n" + 
						"							m.town_code like  '"+townCode+"' and\r\n" + 
						"							lm.mtrno=m.mtrno and lm.billmonth='"+monthyr+"' and flag = 'Manually Edited' and f.meterno=m.mtrno;";
				
			}
			list= postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println(qry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getfdrlossdetails(String month, String towncode, String fdrid)
	{
//		String sql = "SELECT last.* FROM\n" +
//				"(SELECT f.*,g.* FROM\n" +
//				"(SELECT sum(total_unit_supply),feeder_tp_id FROM meter_data.rpt_eadt_losses_"+period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"+fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\n" +
//				"(SELECT bb.*,d.* FROM\n" +
//				"(SELECT count(dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"+fdrid+"' GROUP BY tpparentid )d RIGHT JOIN\n" +
//				"(SELECT aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.meterno as mtrno,aa.boundary_id as bid,aa.boundary_name as bname,aa.bimp as b_imp,bexp as b_exp ,aa.netdt  as netdt,aa.netfdr,tot_energy as total_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind),(tot_energy)-COALESCE((htlt.htcom+htlt.htind),0)  as trans_loss,round((((tot_energy)-COALESCE((htlt.htcom+htlt.htind),0))/tot_energy),2) as per FROM\n" +
//				"(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"+month+"' AND tp_fdr_id like '"+fdrid+"' GROUP BY tp_fdr_id) htlt right JOIN\n" +
//				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\n" +
//				"(SELECT fbd.*, sum(kwh_imp) as imp,sum(kwh_exp) as exp,meter_number FROM meter_data.load_survey ls,\n" +
//				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\n" +
//				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\n" +
//				" \n" +
//				"(SELECT fbd.*, COALESCE(sum(kwh_imp),0) as bimp,COALESCE(sum(kwh_exp),0) as bexp,meter_number as bmn FROM meter_data.load_survey ls,\n" +
//				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\n" +
//				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\n" +
//				"a.tp_fdr_id = b.tp_fdr_id\n" +
//				")aa\n" +
//				"on aa.tp_fdr_id = htlt.tp_fdr_id\n" +
//				") bb\n" +
//				"on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null) f\n" +
//				"on f.tp_id = g.feeder_tp_id)last";
		
		
//		String sql = "SELECT last.* FROM\r\n" + 
//				"(SELECT f.*,g.* FROM\r\n" + 
//				"(SELECT sum(total_unit_supply),feeder_tp_id FROM meter_data.rpt_eadt_losses_"+period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"+fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\r\n" + 
//				"(SELECT bb.*,d.* FROM\r\n" + 
//				"(SELECT count(dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"+fdrid+"' GROUP BY tpparentid )d RIGHT JOIN\r\n" + 
//				"(SELECT aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.meterno as mtrno,aa.boundary_id as bid,aa.boundary_name as bname,aa.bimp as b_imp,bexp as b_exp ,aa.netdt  as netdt,aa.netfdr,tot_energy as total_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind),(tot_energy)-COALESCE((htlt.htcom+htlt.htind),0)  as trans_loss,round((((tot_energy)-COALESCE((htlt.htcom+htlt.htind),0))/tot_energy),2) as per FROM\r\n" + 
//				"(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"+month+"' AND tp_fdr_id like '"+fdrid+"' GROUP BY tp_fdr_id) htlt right JOIN\r\n" + 
//				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\r\n" + 
//				"(SELECT fbd.*,sum(kwh_imp) as imp,sum(kwh_exp) as exp,mtrno from meter_data.monthly_consumption ls,\r\n" + 
//				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd \r\n" + 
//				"WHERE fbd.meterno = ls.mtrno and billmonth='"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY mtrno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\r\n" + 
//				" \r\n" + 
//				"(SELECT fbd.*, COALESCE(sum(kwh_imp),0) as bimp,COALESCE(sum(kwh_exp),0) as bexp,mtrno as bmn FROM meter_data.monthly_consumption ls,\r\n" + 
//				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\r\n" + 
//				"WHERE fbd.meterno = ls.mtrno AND billmonth = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY mtrno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\r\n" + 
//				"a.tp_fdr_id = b.tp_fdr_id\r\n" + 
//				")aa\r\n" + 
//				"on aa.tp_fdr_id = htlt.tp_fdr_id\r\n" + 
//				") bb\r\n" + 
//				"on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null) f\r\n" + 
//				"on f.tp_id = g.feeder_tp_id)last\r\n" + 
//				"";
		
//		String sql = "SELECT last.* FROM\r\n" + 
//				"(SELECT f.*,g.*,ABS((f.total_energy)-COALESCE((f.htcom+g.unit_supply),0))  as trans_loss,ABS(round((((f.total_energy)-COALESCE((f.htcom+g.unit_supply),0))/f.total_energy),2)) as per FROM\r\n" + 
//				"(SELECT sum(total_unit_supply) as unit_supply,feeder_tp_id FROM meter_data.rpt_eadt_losses_"+period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"+fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\r\n" + 
//				"(SELECT bb.*,d.* FROM\r\n" + 
//				"(SELECT count(dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"+fdrid+"' GROUP BY tpparentid )d RIGHT JOIN\r\n" + 
//				"(SELECT aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.meterno as mtrno,aa.boundary_id as bid,aa.boundary_name as bname,aa.bimp as b_imp,bexp as b_exp ,aa.netdt  as netdt,aa.netfdr,tot_energy as total_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind) FROM\r\n" + 
//				"(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"+month+"' AND tp_fdr_id like '"+fdrid+"' GROUP BY tp_fdr_id) htlt right JOIN\r\n" + 
//				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\r\n" + 
//				"(SELECT fbd.*,sum(kwh_imp) as imp,sum(kwh_exp) as exp,mtrno from meter_data.monthly_consumption ls,\r\n" + 
//				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd \r\n" + 
//				"WHERE fbd.meterno = ls.mtrno and billmonth='"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY mtrno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\r\n" + 
//				" \r\n" + 
//				"(SELECT fbd.*, COALESCE(sum(kwh_imp),0) as bimp,COALESCE(sum(kwh_exp),0) as bexp,mtrno as bmn FROM meter_data.monthly_consumption ls,\r\n" + 
//				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\r\n" + 
//				"WHERE fbd.meterno = ls.mtrno AND billmonth = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY mtrno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\r\n" + 
//				"a.tp_fdr_id = b.tp_fdr_id\r\n" + 
//				")aa\r\n" + 
//				"on aa.tp_fdr_id = htlt.tp_fdr_id\r\n" + 
//				") bb\r\n" + 
//				"on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null) f\r\n" + 
//				"on f.tp_id = g.feeder_tp_id)last\r\n" + 
//				"";
		
//		String sql = "SELECT last.* FROM\r\n" + 
//				"(SELECT f.*,g.*,ABS((f.total_energy)-COALESCE((f.htcom+g.unit_supply),0))  as trans_loss,ABS(round((((f.total_energy)-COALESCE((f.htcom+g.unit_supply),0))/f.total_energy),2)) as per FROM\r\n" + 
//				"(SELECT sum(total_unit_supply) as unit_supply,feeder_tp_id FROM meter_data.rpt_eadt_losses_"+period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"+fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\r\n" + 
//				"(SELECT bb.*,d.* FROM\r\n" + 
//				"(SELECT count(dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"+fdrid+"' GROUP BY tpparentid )d RIGHT JOIN\r\n" + 
//				"(SELECT aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.meterno as mtrno,aa.boundary_id as bid,aa.boundary_name as bname,aa.bimp as b_imp,bexp as b_exp ,aa.netdt  as netdt,aa.netfdr,tot_energy as total_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind) FROM\r\n" + 
//				"(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"+month+"' AND tp_fdr_id like '"+fdrid+"' GROUP BY tp_fdr_id) htlt right JOIN\r\n" + 
//				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\r\n" + 
//				"(SELECT fbd.*,cast(sum(feeder_import_energy )as numeric) as imp,cast('0' as numeric) as exp,meter_sr_number from meter_data.rpt_eamainfeeder_losses_"+period+"months ls,\r\n" + 
//				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd \r\n" + 
//				"WHERE fbd.meterno = ls.meter_sr_number and month_year='"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY meter_sr_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\r\n" + 
//				" \r\n" + 
//				"(SELECT fbd.*, COALESCE(sum(ls.boundary_import_energy),0) as bimp,COALESCE(sum(ls.boundary_export_energy),0) as bexp,ls.meterno as bmn FROM meter_data.rpt_eaboundaryfeeder_losses_"+period+"months ls ,\r\n" + 
//				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\r\n" + 
//				"WHERE fbd.meterno = ls.meterno AND billmonth = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY ls.meterno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\r\n" + 
//				"a.tp_fdr_id = b.tp_fdr_id\r\n" + 
//				")aa\r\n" + 
//				"on aa.tp_fdr_id = htlt.tp_fdr_id\r\n" + 
//				") bb\r\n" + 
//				"on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null) f\r\n" + 
//				"on f.tp_id = g.feeder_tp_id)last";
		
//		String sql = "SELECT last.tp_id,last.feedername,last.amn,last.imp,last.exp,last.netfdr,last.meterno,last.boundary_id,last.boundary_name,last.bimp,\r\n" + 
//				"last.bexp,last.netdt,last.tot_energy,last.htcom,last.htind,last.ddt,last.netenergy_of_feeder ,last.unit_supply ,last.total_energy,\r\n" + 
//				" count , unit_supply ,  trans_loss ,(case when slno=1 then per END) as per\r\n" + 
//				"FROM\r\n" + 
//				"(SELECT f.*,g.*,round((COALESCE(f.netfdr,0)-COALESCE(f.newse,0)),2) as total_energy,ABS((f.tot_energy)-COALESCE((f.htcom+g.unit_supply),0))  as trans_loss,ABS(round((((f.tot_energy)-COALESCE((f.htcom+g.unit_supply),0))/f.tot_energy),2)) as per,ROW_NUMBER() over ( PARTITION BY f.tp_id ORDER BY f.tp_id ) as slno1 FROM\r\n" + 
//				"(SELECT sum(total_unit_supply) as unit_supply,feeder_tp_id FROM meter_data.rpt_eadt_losses_"+period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"+fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\r\n" + 
//				"\r\n" + 
//				"(SELECT bb.*,d.* FROM\r\n" + 
//				"(SELECT count(dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"+fdrid+"' GROUP BY tpparentid )d RIGHT JOIN\r\n" + 
//				"(select aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.netfdr,aa.meterno,aa.boundary_id,aa.boundary_name,aa.bimp,aa.bexp,aa.netdt,aa.tot_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind)as ddt,aa.netenergy_of_feeder,aa.NEWSE,ROW_NUMBER() over ( PARTITION BY aa.tp_fdr_id ORDER BY aa.tp_fdr_id ) as slno\r\n" + 
//				" FROM\r\n" + 
//				"(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"+month+"' AND tp_fdr_id like '"+fdrid+"' GROUP BY tp_fdr_id)htlt right JOIN\r\n" + 
//				"((SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\r\n" + 
//				"(SELECT fbd.*,cast(sum(feeder_import_energy )as numeric) as imp,cast('0' as numeric) as exp,meter_sr_number from meter_data.rpt_eamainfeeder_losses_"+period+"months ls,\r\n" + 
//				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd \r\n" + 
//				"WHERE fbd.meterno = ls.meter_sr_number and month_year='"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY meter_sr_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\r\n" + 
//				"\r\n" + 
//				"(SELECT fbd.*, COALESCE(sum(ls.boundary_import_energy),0) as bimp,COALESCE(sum(ls.boundary_export_energy),0) as bexp,ls.meterno as bmn\r\n" + 
//				"FROM meter_data.rpt_eaboundaryfeeder_losses_"+period+"months ls ,\r\n" + 
//				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\r\n" + 
//				"WHERE fbd.meterno = ls.meterno AND billmonth = '"+month+"' AND fbd.tp_fdr_id like '"+fdrid+"' GROUP BY ls.meterno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\r\n" + 
//				"a.tp_fdr_id = b.tp_fdr_id\r\n" + 
//				")z left JOIN\r\n" + 
//				"\r\n" + 
//				"(select feeder_tp_id,sum((boundary_import_energy-boundary_export_energy)+unit_supply) as netenergy_of_feeder,\r\n" + 
//				"SUM(round(COALESCE(boundary_import_energy,0)-COALESCE(boundary_export_energy,0),2)) AS NEWSE\r\n" + 
//				"from meter_data.rpt_eamainfeeder_losses_"+period+"months fl\r\n" + 
//				"left outer join meter_data. rpt_eaboundaryfeeder_losses_"+period+"months fds   on   fl.fdr_id  = fds.feeder_code   where fl.town_code  like'"+towncode+"'\r\n" + 
//				"and   month_year ='"+month+"'\r\n" + 
//				"and  billmonth ='"+month+"'or billmonth is null\r\n" + 
//				"\r\n" + 
//				"GROUP BY feeder_tp_id\r\n" + 
//				")b on(b.feeder_tp_id=z.tp_fdr_id))aa on aa.tp_fdr_id = htlt.tp_fdr_id)bb  on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null)f\r\n" + 
//				"on f.tp_id = g.feeder_tp_id)last\r\n" + 
//				"";
		
		/*
		 * String sql =
		 * "select vvv.*,round(((vvv.trans_loss/vvv.total_energy)*100),2)as per,round(((vvv.dtloss/unit_supply)*100),2) as dtlossper from\r\n"
		 * +
		 * "(SELECT last.tp_id,last.feedername,last.amn,last.imp,last.exp,last.netfdr,last.meterno,last.boundary_id,last.boundary_name,last.bimp,\r\n"
		 * +
		 * "last.bexp,last.netdt,last.tot_energy,last.htcom,last.htind,last.ddt,last.netenergy_of_feeder ,last.unit_supply ,last.total_energy,\r\n"
		 * +
		 * " count  ,  ABS((last.total_energy)-COALESCE((last.htcom+last.unit_supply),0))  as trans_loss, (last.unit_supply - last.total_lt_unit_billed )as dtloss,total_lt_unit_billed \r\n"
		 * + "FROM\r\n" +
		 * "(SELECT f.*,g.*,round((COALESCE(f.netfdr,0)-COALESCE(f.newse,0)),2) as total_energy,ROW_NUMBER() over ( PARTITION BY f.tp_id ORDER BY f.tp_id ) as slno1 FROM\r\n"
		 * +
		 * "(SELECT sum(total_unit_supply) as unit_supply,feeder_tp_id ,sum(total_lt_unit_billed) as total_lt_unit_billed FROM meter_data.rpt_eadt_losses_"
		 * +period+"months WHERE month_year = '"+month+"' AND feeder_tp_id like '"
		 * +fdrid+"' GROUP BY feeder_tp_id) g RIGHT JOIN\r\n" + "\r\n" +
		 * "(SELECT bb.*,d.* FROM\r\n" +
		 * "(SELECT count(distinct dttpid),tpparentid FROM meter_data.dtdetails WHERE tpparentid like '"
		 * +fdrid+"' and dttpid is not null and dttpid !='' and meterno is not null and meterno != '' GROUP BY tpparentid )d RIGHT JOIN\r\n"
		 * +
		 * "(select aa.tp_fdr_id as tp_id,aa.feedername,aa.amn,aa.imp,aa.exp,aa.netfdr,aa.meterno,aa.boundary_id,aa.boundary_name,aa.bimp,aa.bexp,aa.netdt,aa.tot_energy,htlt.htcom,htlt.htind,htlt.tp_fdr_id,(htlt.htcom+htlt.htind)as ddt,aa.netenergy_of_feeder,aa.NEWSE,ROW_NUMBER() over ( PARTITION BY aa.tp_fdr_id ORDER BY aa.tp_fdr_id ) as slno\r\n"
		 * + " FROM\r\n" +
		 * "(SELECT COALESCE(sum(ht_commercial_energy_billed),0) as htcom,COALESCE(sum(ht_industrial_energy_billed),0) as htind, tp_fdr_id FROM meter_data.npp_dt_rpt_monthly_calculation WHERE monthyear = '"
		 * +month+"' AND tp_fdr_id like '"
		 * +fdrid+"' GROUP BY tp_fdr_id)htlt right JOIN\r\n" +
		 * "((SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\r\n"
		 * +
		 * "(SELECT fbd.*,cast(sum(feeder_import_energy )as numeric) as imp,cast('0' as numeric) as exp,meter_sr_number from meter_data.rpt_eamainfeeder_losses_"
		 * +period+"months ls,\r\n" +
		 * "(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"
		 * +towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd \r\n" +
		 * "WHERE fbd.meterno = ls.meter_sr_number and month_year='"
		 * +month+"' AND fbd.tp_fdr_id like '"
		 * +fdrid+"' GROUP BY meter_sr_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\r\n"
		 * + "\r\n" +
		 * "(SELECT fbd.*, COALESCE(sum(ls.boundary_import_energy),0) as bimp,COALESCE(sum(ls.boundary_export_energy),0) as bexp,ls.meterno as bmn\r\n"
		 * + "FROM meter_data.rpt_eaboundaryfeeder_losses_"+period+"months ls ,\r\n" +
		 * "(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"
		 * +towncode+"' AND tp_fdr_id like '"+fdrid+"')fbd\r\n" +
		 * "WHERE fbd.meterno = ls.meterno AND billmonth = '"
		 * +month+"' AND fbd.tp_fdr_id like '"
		 * +fdrid+"' GROUP BY ls.meterno,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\r\n"
		 * + "a.tp_fdr_id = b.tp_fdr_id\r\n" + ")z left JOIN\r\n" + "\r\n" +
		 * "(select feeder_tp_id,sum((boundary_import_energy-boundary_export_energy)+unit_supply) as netenergy_of_feeder,\r\n"
		 * +
		 * "SUM(round(COALESCE(boundary_import_energy,0)-COALESCE(boundary_export_energy,0),2)) AS NEWSE\r\n"
		 * + "from meter_data.rpt_eamainfeeder_losses_"+period+"months fl\r\n" +
		 * "left outer join meter_data. rpt_eaboundaryfeeder_losses_"
		 * +period+"months fds   on   fl.fdr_id  = fds.feeder_code   where fl.town_code  like'"
		 * +towncode+"'\r\n" + "and   month_year ='"+month+"'\r\n" +
		 * "and  billmonth ='"+month+"'or billmonth is null\r\n" + "\r\n" +
		 * "GROUP BY feeder_tp_id\r\n" +
		 * ")b on(b.feeder_tp_id=z.tp_fdr_id))aa on aa.tp_fdr_id = htlt.tp_fdr_id)bb  on d.tpparentid = bb.tp_id WHERE bb.tp_id is not null)f\r\n"
		 * + "on f.tp_id = g.feeder_tp_id)last)vvv\r\n" + "\r\n" + "";
		 */
		
		
		/*
		 * String sql
		 * ="select distinct p.*,ABS(round(((p.diff/p.fdr)*100),2))as per from \r\n" +
		 * "(select z.*,ABS((z.fdr)-COALESCE((z.dtr+z.ht_consumption),2))  as diff from \r\n"
		 * +
		 * "(select distinct a.feedername,a.tp_fdr_id,a.billmonth,a.meterno,CASE WHEN(a.fdr IS NULL) THEN '0' ELSE a.fdr end,	CASE WHEN(w.tp_fdr_id IS NULL) THEN 'Nil' ELSE w.tp_fdr_id end as boundayid,(CASE WHEN(w.feedername IS NULL) THEN 'Nil' ELSE w.feedername end) as bname,CASE WHEN(w.boundary IS NULL) THEN '0' ELSE w.boundary end,count(distinct s.dttpid),COALESCE(round(sum(distinct x.kwh_imp),0),0) as dtr,\r\n"
		 * + "\r\n" +
		 * "COALESCE(SUM((CAST(y.ht_com_con_count as numeric))+(CAST(y.ht_ind_con_count as numeric))),0) as ht_consumers,COALESCE(SUM(y.ht_ind_energy_bill+ y.ht_com_energy_bill),0) as ht_consumption \r\n"
		 * + "\r\n" + "from \r\n" +
		 * "(select distinct fd.feedername,fd.tp_fdr_id,mc.billmonth,COALESCE(round((CASE WHEN (mc.adjacent_value_imp)>0 THEN (sum(mc.kwh_imp+mc.adjacent_value_imp)) ELSE (sum(mc.kwh_imp-mc.adjacent_value_Exp)) END),2),0) as fdr,fd.meterno from meter_data.feederdetails fd LEFT JOIN  meter_data.monthly_consumption mc ON (fd.meterno=mc.mtrno) where  mc.billmonth>=cast(to_char(to_date('"
		 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
		 * "mc.billmonth <cast(to_char(to_date('"
		 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code='"
		 * +towncode+"' and fd.tp_fdr_id ='"
		 * +fdrid+"'  and fd.meterno IN (select mtrno from meter_data.master_main where fdrcategory='FEEDER METER')GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id,mc.adjacent_value_imp,mc.adjacent_value_Exp)a LEFT JOIN \r\n"
		 * + "\r\n" + "\r\n" +
		 * "(select distinct fd.feedername,fd.tp_fdr_id,mc.billmonth,COALESCE(round(sum(mc.kwh_imp),0),0)  as boundary from meter_data.feederdetails fd LEFT JOIN  meter_data.monthly_consumption mc ON (fd.meterno=mc.mtrno) where  mc.billmonth>=cast(to_char(to_date('"
		 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
		 * "mc.billmonth <cast(to_char(to_date('"
		 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code='"
		 * +towncode+"' and fd.tp_fdr_id ='"
		 * +fdrid+"'  and fd.meterno IN (select mtrno from meter_data.master_main where fdrcategory='BOUNDARY METER')GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id)w ON(a.tp_fdr_id=w.tp_fdr_id) LEFT JOIN\r\n"
		 * + "\r\n" + "\r\n" +
		 * " (SELECT DISTINCT on (dt.dttpid)dt.dttpid as dttpid,string_agg(dt.meterno, ',') as meterno,dt.tpparentid from meter_data.dtdetails dt where  dt.tpparentid LIKE '"
		 * +fdrid+"' and dt.tp_town_code LIKE '"
		 * +towncode+"'  and dt.meterno is not NULL and dt.meterno <> '' GROUP BY dt.dttpid,dt.tpparentid)s ON(a.tp_fdr_id=s.tpparentid) LEFT JOIN\r\n"
		 * + "\r\n" +
		 * "(select distinct on(cm.mtrno)cm.mtrno,dt.tpparentid,mm.fdrname,dt.dtname,cm.billmonth,cm.kwh_imp from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.dttpid=mm.location_id)  LEFT JOIN meter_data.monthly_consumption cm ON(dt.meterno=cm.mtrno) \r\n"
		 * + "where  cm.billmonth>=cast(to_char(to_date('"
		 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
		 * "cm.billmonth <cast(to_char(to_date('"
		 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and dt.tp_town_code='"
		 * +towncode+"' and dt.tpparentid='"
		 * +fdrid+"'  GROUP BY dt.tpparentid,mm.fdrname,dt.dtname,cm.billmonth,cm.kwh_imp,cm.mtrno,dt.dttpid)x ON(a.tp_fdr_id=x.tpparentid) LEFT JOIN\r\n"
		 * + "\r\n" + "\r\n" + "\r\n" +
		 * "(select distinct lm.ht_com_con_count,lm.ht_ind_con_count,lm.tp_feeder_code,lm.ht_ind_energy_bill,lm.ht_com_energy_bill from meter_data.master_main mm LEFT JOIN meter_data.npp_data_monthly_calculation lm ON(mm.mtrno=lm.mtrno) where (CAST(lm.monthyear as NUMERIC))>=cast(to_char(to_date('"
		 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
		 * "(CAST(lm.monthyear as NUMERIC)) <cast(to_char(to_date('"
		 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and lm.tp_feeder_code='"
		 * +fdrid+"')y ON (a.tp_fdr_id=y.tp_feeder_code)\r\n" + "\r\n" +
		 * "GROUP BY a.feedername,a.tp_fdr_id,a.billmonth,a.meterno,a.fdr,w.boundary,w.tp_fdr_id,w.feedername)z)p\r\n"
		 * + "";
		 */
		
		String sql="select distinct p.*,ABS(round(((p.diff/p.fdr)*100),2))as per from \r\n" + 
				"(select z.*,ABS((z.fdr)-COALESCE((z.dtr+z.ht_consumption),2))  as diff from \r\n" + 
				"(select  distinct a.feedername,a.tp_fdr_id,a.billmonth,a.meterno,CASE WHEN(a.fdr IS NULL) THEN '0' ELSE a.fdr end,	CASE WHEN(w.tp_fdr_id IS NULL) THEN 'Nil' ELSE w.tp_fdr_id end as boundayid,(CASE WHEN(w.feedername IS NULL) THEN 'Nil' ELSE w.feedername end) as bname,CASE WHEN(w.boundary IS NULL) THEN '0' ELSE w.boundary end,count(distinct s.dttpid),x.dtr,COALESCE(SUM((CAST(y.ht_com_con_count as numeric))+(CAST(y.ht_ind_con_count as numeric))),0) as ht_consumers,COALESCE(SUM(y.ht_ind_energy_bill+ y.ht_com_energy_bill),0) as ht_consumption  from \r\n" + 
				"(select distinct fd.feedername,fd.tp_fdr_id,mc.billmonth,mc.kwh_imp as fdr,fd.meterno from meter_data.feederdetails fd LEFT JOIN  meter_data.input_energy mc ON (fd.meterno=mc.mtrno) where cast(mc.billmonth as numeric)>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
				"cast(mc.billmonth as numeric) <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code LIKE '"+towncode+"' and fd.tp_fdr_id LIKE '"+fdrid+"'  and fd.meterno IN (select mtrno from meter_data.master_main where fdrcategory='FEEDER METER')GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id)a LEFT JOIN \r\n" + 
				"\r\n" + 
				"\r\n" + 
				"(select distinct fd.meterno, fd.feedername,fd.tp_fdr_id,mc.billmonth,COALESCE(round(sum(mc.kwh_imp),0),0)  as boundary from meter_data.feederdetails fd LEFT JOIN  meter_data.input_energy mc ON (fd.meterno=mc.mtrno) where cast(mc.billmonth as numeric)>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
				"cast(mc.billmonth as numeric) <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code LIKE '"+towncode+"' and fd.tp_fdr_id LIKE'"+fdrid+"'  and fd.meterno IN (select mtrno from meter_data.master_main where fdrcategory='BOUNDARY METER')GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id)w ON(a.meterno=w.meterno) LEFT JOIN\r\n" + 
				"\r\n" + 
				" (SELECT DISTINCT on (dt.dttpid)dt.dttpid as dttpid,string_agg(dt.meterno, ',') as meterno,dt.tpparentid from meter_data.dtdetails dt where  dt.tpparentid LIKE '"+fdrid+"' and dt.tp_town_code LIKE '"+towncode+"'  and dt.meterno is not NULL and dt.meterno <> '' GROUP BY dt.dttpid,dt.tpparentid)s ON(a.meterno=s.meterno) LEFT JOIN\r\n" + 
				"\r\n" + 
				"(select fd.meterno,COALESCE(round(sum(mc.kwh_imp),2),0) as dtr from meter_data.dtdetails dt LEFT JOIN meter_data.feederdetails fd ON(dt.tpparentid=fd.tp_fdr_id) LEFT JOIN meter_data.input_energy mc ON(dt.meterno=mc.mtrno) where mc.fdrcategory='DT' AND fd.tp_fdr_id LIKE '"+fdrid+"' and dt.meterno is not null and  cast(mc.billmonth as numeric)>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
				"cast(mc.billmonth as numeric) <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric)  GROUP BY fd.meterno ORDER BY fd.meterno ASC)x		ON(a.meterno=x.meterno)\r\n" + 
				"\r\n" + 
				"LEFT JOIN\r\n" + 
				"\r\n" + 
				"(select distinct lm.ht_com_con_count,lm.ht_ind_con_count,lm.tp_feeder_code,lm.ht_ind_energy_bill,lm.ht_com_energy_bill from meter_data.master_main mm LEFT JOIN meter_data.npp_data_monthly_calculation lm ON(mm.mtrno=lm.mtrno) where (CAST(lm.monthyear as NUMERIC))>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
				"(CAST(lm.monthyear as NUMERIC)) <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and lm.tp_feeder_code LIKE '"+fdrid+"')y ON (a.tp_fdr_id=y.tp_feeder_code)GROUP BY a.feedername,a.tp_fdr_id,a.billmonth,a.meterno,a.fdr,w.tp_fdr_id,w.feedername,w.boundary,x.dtr)z)p";
		System.out.println(sql);
		
		return postgresMdas.createNativeQuery(sql).getResultList();
	}
	
	
	@Override
	public List<?> getfdrlossdetailsInfo(String month,String towncode, String fdrid) {
		List<?> list=null;
		String qry="";
	
		
		try {

			
			
//			qry ="(select distinct fd.tp_fdr_id,fd.feedername,fd.meterno,COALESCE(mc.kwh_imp,0) as fdr from meter_data.feederdetails fd LEFT JOIN  meter_data.monthly_consumption mc ON (fd.meterno=mc.mtrno) where  mc.billmonth>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
//					"mc.billmonth <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code='"+towncode+"' and fd.tp_fdr_id ='"+fdrid+"' GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id)";
//			
			
			
			
			/*
			 * qry="\r\n" +
			 * "(select distinct fd.tp_fdr_id,fd.feedername,fd.meterno,COALESCE(mc.kwh_imp,0) as fdr,mc.adjacent_value_imp,mc.adjacent_value_exp, (CASE WHEN (mc.adjacent_value_imp)>0 THEN (sum(mc.kwh_imp+mc.adjacent_value_imp)) ELSE (sum(mc.kwh_imp-mc.adjacent_value_Exp)) END) as net from meter_data.feederdetails fd"
			 * + " LEFT JOIN  meter_data.monthly_consumption mc ON (fd.meterno=mc.mtrno)" +
			 * " where  mc.billmonth>=cast(to_char(to_date('"
			 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
			 * "mc.billmonth <cast(to_char(to_date('"
			 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code Like '"
			 * +towncode+"' " + "and fd.tp_fdr_id LIKE '"
			 * +fdrid+"' GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id,mc.adjacent_value_imp,mc.adjacent_value_exp)\r\n"
			 * + "";
			 */
			
			
			qry="select distinct y.zone,y.circle,y.town_ipds,x.* from \r\n" + 
					"(select distinct fd.tp_town_code,fd.tp_fdr_id,fd.feedername,fd.meterno,COALESCE(mc.kwh_imp,0) as fdr,mc.adjacent_value_imp,mc.adjacent_value_exp, (CASE WHEN (mc.adjacent_value_imp)>0 THEN (sum(mc.kwh_imp+mc.adjacent_value_imp)) ELSE (sum(mc.kwh_imp-mc.adjacent_value_Exp)) END) as net from meter_data.feederdetails fd LEFT JOIN  meter_data.input_energy mc ON (fd.meterno=mc.mtrno) where cast(mc.billmonth as numeric)>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
					"cast(mc.billmonth as numeric)<cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and fd.tp_town_code Like '"+towncode+"' and fd.tp_fdr_id LIKE '"+fdrid+"' GROUP BY fd.feedername,mc.billmonth,mc.kwh_imp,fd.meterno,fd.tp_fdr_id,mc.adjacent_value_imp,mc.adjacent_value_exp,fd.tp_town_code)x LEFT JOIN (SELECT zone,circle,town_ipds,tp_towncode from meter_data.amilocation)y ON(x.tp_town_code=y.tp_towncode)\r\n" + 
					" ";
			System.out.println(qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
			
			
		}catch(Exception e) {
			
			
			 e.printStackTrace();
			
		}
		return list;
	}
	
	@Override
	public List<?> getdtlossdetailsInfo(String month, String towncode, String fdrid) {
		
		List<?> list=null;
		String qry="";
		
		try {
		
			
//			qry="\r\n" + 
//					"select distinct on(dt.meterno)dt.meterno,dt.dtname,string_agg(distinct CAST(round(dt.dtcapacity,0) as varchar), ',') as dtcapacity,round(rpt.total_unit_supply,2)total_unit_supply,(CASE WHEN (mc.flag is NULL) THEN 'ACTUAL' else mc.flag end) from meter_data.dtdetails dt LEFT JOIN meter_data.rpt_eadt_losses_"+period+"months rpt ON (dt.dttpid=rpt.tpdt_id) LEFT JOIN meter_data.monthly_consumption mc ON(mc.mtrno=rpt.meterno) where rpt.month_year='"+month+"' and dt.tpparentid LIKE '"+fdrid+"' and dt.tp_town_code LIKE '"+towncode+"' and dt.meterno is NOT NULL GROUP BY dt.dtname,dt.meterno,rpt.total_unit_supply,mc.flag";
			
			/*
			 * qry="(select distinct on(cm.mtrno)cm.mtrno, mm.fdrname,dt.tpparentid,dt.dttpid,dt.dtname,cast(cm.billmonth as numeric) as numeric ,round(cm.kwh_imp,0) from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.dttpid=mm.location_id)  LEFT JOIN meter_data.input_energy cm ON(dt.meterno=cm.mtrno) \r\n"
			 * + "where  cast(cm.billmonth as numeric)>=cast(to_char(to_date('"
			 * +month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" +
			 * "cast(cm.billmonth as numeric) <cast(to_char(to_date('"
			 * +month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and dt.tp_town_code LIKE '"
			 * +towncode+"' and dt.tpparentid LIKE '"
			 * +fdrid+"' and cm.mtrno is not null and mm.fdrcategory LIKE 'DT' GROUP BY dt.tpparentid,mm.fdrname,dt.dtname,cm.billmonth,cm.kwh_imp,dt.meterno,dt.dttpid,cm.mtrno)"
			 * ;
			 */
			
			qry="select distinct y.zone,y.circle,y.town_ipds,x.* from \r\n" + 
					"(select distinct on(cm.mtrno)cm.mtrno,dt.tp_town_code,mm.fdrname,dt.tpparentid,dt.dttpid,dt.dtname,cast(cm.billmonth as numeric) as numeric ,round(cm.kwh_imp,0) from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.dttpid=mm.location_id)  LEFT JOIN meter_data.input_energy cm ON(dt.meterno=cm.mtrno) \r\n" + 
					"where  cast(cm.billmonth as numeric)>=cast(to_char(to_date('"+month+"','YYYYMM')-INTERVAL '01 MONTH', 'YYYYMM')AS numeric) AND \r\n" + 
					"cast(cm.billmonth as numeric) <cast(to_char(to_date('"+month+"', 'YYYYMM') - INTERVAL '00 MONTH','YYYYMM') AS numeric) and dt.tp_town_code LIKE '"+towncode+"' and dt.tpparentid LIKE '"+fdrid+"' and cm.mtrno is not null and mm.fdrcategory LIKE 'DT' GROUP BY dt.tpparentid,mm.fdrname,dt.dtname,cm.billmonth,cm.kwh_imp,dt.meterno,dt.dttpid,cm.mtrno,dt.tp_town_code)x LEFT JOIN (SELECT zone,circle,town_ipds,tp_towncode from meter_data.amilocation)y ON(x.tp_town_code=y.tp_towncode)";
			
			
		
			System.out.println(qry);
			list= postgresMdas.createNativeQuery(qry).getResultList();
			
		}catch(Exception e) {
			
			
			 e.printStackTrace();
			
		}
		return list;
	}

	@Override
	public List<?> getfdrcount(String month, String towncode) 
	{
		
		
		String sql = "SELECT count(*),aa.tp_fdr_id FROM\n" +
				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\n" +
				"(SELECT fbd.*, sum(kwh_imp) as imp,sum(kwh_exp) as exp,meter_number FROM meter_data.load_survey ls,\n" +
				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' )fbd\n" +
				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"'  GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\n" +
				" \n" +
				"(SELECT fbd.*, COALESCE(sum(kwh_imp),0) as bimp,COALESCE(sum(kwh_exp),0) as bexp,meter_number as bmn FROM meter_data.load_survey ls,\n" +
				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' )fbd\n" +
				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"'  GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\n" +
				"a.tp_fdr_id = b.tp_fdr_id\n" +
				")aa\n" +
				"GROUP BY aa.tp_fdr_id";

		
		return postgresMdas.createNativeQuery(sql).getResultList();
	}

	@Override
	public List<?> getfdrcountinfo(String month, String towncode) {
		
		
		String sql = "SELECT count(*),aa.tp_fdr_id FROM\n" +
				"(SELECT a.tp_fdr_id,a.feedername,a.meterno as amn,a.imp,a.exp,COALESCE((a.imp-a.exp),0) as netfdr,b.meterno,b.boundary_id,b.boundary_name,cast(b.bimp as varchar) as bimp,cast(b.bexp as varchar) as bexp, COALESCE((b.bimp-b.bexp),0) as netdt,COALESCE((a.imp-a.exp),0)-COALESCE((b.bimp-b.bexp),0) as tot_energy FROM\n" +
				"(SELECT fbd.*, sum(kwh_imp) as imp,sum(kwh_exp) as exp,meter_number FROM meter_data.load_survey ls,\n" +
				"(SELECT tp_fdr_id,feedername ,meterno,boundary_id FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '0' AND boundry_feeder = 'f' AND tp_town_code like '"+towncode+"' )fbd\n" +
				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"'  GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.feedername ORDER BY fbd.tp_fdr_id)a left  JOIN\n" +
				" \n" +
				"(SELECT fbd.*, COALESCE(sum(kwh_imp),0) as bimp,COALESCE(sum(kwh_exp),0) as bexp,meter_number as bmn FROM meter_data.load_survey ls,\n" +
				"(SELECT tp_fdr_id ,meterno,boundary_id,boundary_name FROM meter_data.feederdetails WHERE meterno is not null and crossfdr = '1' AND boundry_feeder = 't' AND tp_town_code like '"+towncode+"' )fbd\n" +
				"WHERE fbd.meterno = ls.meter_number AND to_char(ls.read_time,'yyyymm') = '"+month+"'  GROUP BY meter_number,fbd.tp_fdr_id,fbd.meterno,fbd.boundary_id,fbd.boundary_name ORDER BY fbd.tp_fdr_id)b on\n" +
				"a.tp_fdr_id = b.tp_fdr_id\n" +
				")aa\n" +
				"GROUP BY aa.tp_fdr_id";
		
		return postgresMdas.createNativeQuery(sql).getResultList();
	}

	@Override
	public List<?> getfdrEnergyUpdate(String circle,String towncode,String month,String mtrno) {
		
		String sql="\r\n" + 
				"select lm.billmonth,subdivision,f.tpparentid as substattion_code,f.tp_fdr_id,f.feedername,\r\n" + 
				"m.mtrno,lm.adjacent_value_imp,lm.adjacent_value_exp,lm.id \r\n" + 
				"from meter_data.master_main m,meter_data.input_energy lm,meter_data.feederdetails f\r\n" + 
				"where  m.circle like '"+circle+"' and\r\n" + 
				"m.town_code like '"+towncode+"'  and\r\n" + 
				"lm.mtrno=m.mtrno and cast(lm.billmonth as numeric)='"+month+"' and f.meterno=m.mtrno and m.fdrcategory like 'FEEDER METER';";
		
		
		System.out.println(sql);
		return postgresMdas.createNativeQuery(sql).getResultList();
	}

	@Override
	public int getenergyDetails(String imp, String exp, String mtrno, String month) {
		int i = 0;
		String sql = "update meter_data.input_energy set adjacent_value_imp='" + imp + "', adjacent_value_exp='"+ exp+ "' where mtrno ='" +mtrno+ "' and billmonth='"+month+"' ";
		 System.out.println(sql);
		try {
			i = postgresMdas.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<?> getFeedertownEAReport(String monthyear, String feederTpId, String periodMonth, String circle,
			String town) {
		List<?> list=null;
		
		try {
		
		String qry="		select distinct * from\r\n" + 
				"					  (SELECT A.*,round((1-((unit_billed/nullif(unit_supply,0))*(amt_collected/nullif(amt_billed,0))))*100,2) as atc_loss_percent  FROM(\r\n" + 
				"					 select meter_sr_number,tot_consumers,round(unit_supply,2) as unit_supply, round(unit_billed,2) as unit_billed, round(amt_billed,2) as amt_billed,round(amt_collected,2) as amt_collected,office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,fdr_id,tp_fdr_id,fdr_name,REPLACE(boundary_feeder,'false','NIL') as boundary_feeder,\r\n" + 
				"					 round((unit_billed/nullif(unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
				"					 round((amt_collected/nullif(amt_billed,0))*100,2) AS collection_efficiency,\r\n" + 
				"			 round(((unit_supply-unit_billed)/nullif(unit_supply,0))*100,2) as t_d_loss,\r\n" + 
				" 					 round((unit_billed/nullif(unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
				"					 round((amt_collected/nullif(amt_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
				"					 tech_loss,\r\n" + 
				"					CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'4 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
				"					CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"				 round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\r\n" + 
				"					 time_stamp\r\n" + 
				"					 FROM meter_data.rpt_eamainfeeder_losses_02months WHERE month_year ='"+monthyear+"' AND  town_code LIKE '"+town+"' and tp_fdr_id LIKE '"+feederTpId+"')A)X ,\r\n" + 
				"					 (select zone,circle,town_ipds,tp_subdivcode,sitecode,subdivision from meter_data.amilocation where circle LIKE '"+circle+"')Y\r\n" + 
				"					WHERE Y.sitecode=X.office_id and '"+periodMonth+"' = '02' ";
		
		
		
		
		
		System.out.println(qry);
		list= postgresMdas.createNativeQuery(qry).getResultList();
		}catch(Exception e) {
			 e.printStackTrace();	
		}
		return list;
	}

	

	@Override
	public List<?> getFeederinputEAReport(String monthyear, String feederTpId, String periodMonth, String circle,
			String town) {
		List<?> list=null;
		
		try {
		
		String qry="\r\n" + 
				"SELECT BD.*, COALESCE(round((CASE WHEN SLNO=1 THEN (COALESCE(netenergy_of_feeder_revised,0)-COALESCE(netenergyboundry,0))-COALESCE(newse,0) end),2),0) as netenergy_of_feder_1  \r\n" + 
				"FROM\r\n" + 
				"(\r\n" + 
				"SELECT ad.*, (case when slno=1 then ((COALESCE(boundary_import_energy,0)-COALESCE(boundary_export_energy,0))+COALESCE(feeder_import_energy,0)) else\r\n" + 
				"(COALESCE(boundary_import_energy,0)-COALESCE(boundary_export_energy,0)) end) as netenergy_of_feeder_revised\r\n" + 
				"fROM\r\n" + 
				"(Select a.*,\r\n" + 
				"(case when a.boundary_feeder_meterno is null and a.boundary_import_energy is null and a.boundary_export_energy is null\r\n" + 
				"then a.feeder_import_energy else round(b.netenergy_of_feeder,2) end) as netenergy_of_feeder_1,b.newse,\r\n" + 
				"ROW_NUMBER() over ( PARTITION BY feeder_code   ORDER BY     feeder_code ) as slno\r\n" + 
				" from\r\n" + 
				"(select fdr_name,\r\n" + 
				"fdr_id,\r\n" + 
				"a.feeder_code,\r\n" + 
				"meter_sr_number,\r\n" + 
				"round(unit_supply,3) as unit_supply ,\r\n" + 
				"a.feeder_import_energy as feeder_import_energy ,\r\n" + 
				"b.boundary_feeder_name,\r\n" + 
				"boundary_feeder_id,\r\n" + 
				"boundary_feeder_meterno,\r\n" + 
				"boundary_import_energy,\r\n" + 
				"boundary_export_energy,\r\n" + 
				"round(boundary_import_energy-boundary_export_energy,2) AS netEnergyBoundry,\r\n" + 
				"month_year AS billmonth,\r\n" + 
				"ss_parentid,a.ss_name from \r\n" + 
				"(SELECT rpt.fdr_name, rpt.fdr_id, rpt.tp_fdr_id as feeder_code,\r\n" + 
				"rpt.meter_sr_number, round(rpt.unit_supply,3) as unit_supply ,rpt.feeder_import_energy,rpt.ss_parentid,sd.substation as ss_name,rpt.month_year,rpt.town_code,sd.circle\r\n" + 
				"FROM meter_data.rpt_eamainfeeder_losses_02months rpt LEFT JOIN meter_data.master_main sd ON(rpt.meter_sr_number=sd.mtrno))a\r\n" + 
				"left join\r\n" + 
				"--JOIN\r\n" + 
				"(SELECT rpt.boundary_feeder_name,\r\n" + 
				"rpt.boundary_feeder_id,rpt.boundary_feeder_meterno,\r\n" + 
				"rpt.boundary_import_energy,rpt.boundary_export_energy,round(rpt.boundary_import_energy-rpt.boundary_export_energy,2) AS netEnergyBoundry,\r\n" + 
				"rpt.billmonth,rpt.feeder_code FROM meter_data. rpt_eaboundaryfeeder_losses_02months rpt \r\n" + 
				"-- WHERE FEEDER_CODE='FDR001952' AND BILLMONTH='201910'\r\n" + 
				")b \r\n" + 
				"on a.month_year=b.billmonth and a.fdr_id=b.feeder_code  \r\n" + 
				"where  month_year='"+monthyear+"' and a.town_code like '"+town+"'  and a.feeder_code LIKE '"+feederTpId+"' and a.circle like '"+circle+"'\r\n" + 
				")a\r\n" + 
				"left JOIN\r\n" + 
				"\r\n" + 
				"(select fdr_id,sum((boundary_import_energy-boundary_export_energy)+unit_supply) as netenergy_of_feeder,\r\n" + 
				"SUM(round(COALESCE(boundary_import_energy,0)-COALESCE(boundary_export_energy,0),2)) AS NEWSE\r\n" + 
				"from meter_data.rpt_eamainfeeder_losses_02months fl\r\n" + 
				"left outer join meter_data. rpt_eaboundaryfeeder_losses_02months fds   on   fl.fdr_id  = fds.feeder_code   where fl.town_code Like '"+town+"' \r\n" + 
				"and   month_year ='"+monthyear+"'\r\n" + 
				"and  billmonth ='"+monthyear+"' or billmonth is null\r\n" + 
				"\r\n" + 
				"GROUP BY fdr_id \r\n" + 
				")b\r\n" + 
				"on(a.fdr_id=b.fdr_id) ORDER BY a.feeder_code ASC)AD\r\n" + 
				"where  '"+periodMonth+"'='02'\r\n" + 
				")BD";
		
		
		
		
		
		System.out.println(qry);
		list= postgresMdas.createNativeQuery(qry).getResultList();
		}catch(Exception e) {
			 e.printStackTrace();	
		}
		return list;
	}


	
	
	

	
}
