package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcits.mdas.service.EnergyAccountingService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Service
public class EnergyAccountingServiceImpl extends GenericServiceImpl<Object> implements EnergyAccountingService {

	
	@Override
	public String scheduledataPushMainFeeder() {
		 String msg = null;
		  
		  String reportMonth = "202001";
		  String town_code = "013";
		  String Monthsinterval = null;
		  String TableName = null;
		  String energySuppliedInterval= null;
		  
		  for (int i = 12; i >= 0 ; i -= 2) {
				
				 System.out.println("This is I " +i);
				 
				 
				 if(i == 12)
				 {
					 Monthsinterval = "11 MONTH";
					  TableName = "rpt_eamainfeeder_losses_12months";
					  energySuppliedInterval= "14 MONTH";
					  		 
				 }
				 
				 if(i == 10)
				 {
					 Monthsinterval = "09 MONTH";
					  TableName = "rpt_eamainfeeder_losses_10months";
					  energySuppliedInterval= "12 MONTH";
					  		 
				 }
				 
				 if(i == 8)
				 {
					 Monthsinterval = "07 MONTH";
					  TableName = "rpt_eamainfeeder_losses_08months";
					  energySuppliedInterval= "10 MONTH";
					  		 
				 }
				 
				 if(i == 6)
				 {
					 Monthsinterval = "05 MONTH";
					  TableName = "rpt_eamainfeeder_losses_06months";
					  energySuppliedInterval= "08 MONTH";
					  		 
				 }
				 
				 if(i == 4)
				 {
					 Monthsinterval = "03 MONTH";
					  TableName = "rpt_eamainfeeder_losses_04months";
					  energySuppliedInterval= "06 MONTH";
					  		 
				 }
				 
				 
				 if(i == 2)
				 {
					 Monthsinterval = "01 MONTH";
					  TableName = "rpt_eamainfeeder_losses_02months";
					  energySuppliedInterval= "04 MONTH";
					  		 
				 }
			
				 if(i == 0)
				 {
					 Monthsinterval = "00 MONTH";
					  TableName = "rpt_eamainfeeder_losses_00months";
					  energySuppliedInterval= "03 MONTH";
					  		 
				 }
				 
				 String qry = "insert into meter_data."+TableName+" (\n" +
				  "month_year,office_id,fdr_id,tp_fdr_id,fdr_name,meter_sr_number,tot_consumers,\n" +
				  "unit_billed,amt_billed,amt_collected,time_stamp,\n" +
				  "town_code,boundary_feeder,feeder_import_energy,unit_supply)\n" +
				  "(\n" +
				  "Select maind.*, main_unit_supply\n" +
				  "from \n" +
				  "(\n" +
				  "Select CAST ('"+reportMonth+"' as NUMERIC) report_month,  CAST (officeid as NUMERIC) officeid,\n" +
				  "feeder_code,tp_feeder_code,feedername,meterno,\n" +
				  "SUM(total_lt_consumer_count + total_ht_consumer_cocount )as total_consumer_count,\n" +
				  "SUM(total_ht_unit_billed + total_lt_unit_billed )as total_units_billed,\n" +
				  "SUM(total_ht_amount_billed + total_lt_amount_billed )as total_amount_billed,\n" +
				  "SUM (total_ht_amount_collected + total_lt_amount_collected) as total_amount_collected,now()\n" +
				  ",towncode,boundry_feeder,\n" +
				  "total_import_energy_supplied\n" +
				  "from (Select feeder_code,fd.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,feedername,\n" +
				
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(ht_ind_energy_bill ,0)+\n" +
				  " COALESCE(ht_com_energy_bill ,0))    END)  AS total_ht_unit_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(lt_ind_energy_bill ,0)+\n" +
				  " COALESCE(lt_com_energy_bill ,0)+\n" +
				  " COALESCE(lt_dom_energy_bill ,0)+\n" +
				  " COALESCE(govt_energy_bill ,0)+\n" +
				  " COALESCE(agri_energy_bill ,0)+\n" +
				  " COALESCE(hut_energy_billed ,0)+\n" +
				  " COALESCE(other_energy_bill ,0))   END)  AS total_lt_unit_billed,\n" +
				  "  \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_ind_amount_bill	,0)+\n" +
				  " COALESCE(ht_com_amount_bill	,0))   END)  AS total_ht_amount_billed ,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_ind_amount_bill ,0)+\n" +
				  " COALESCE(lt_com_amount_bill ,0)+\n" +
				  " COALESCE(lt_dom_amount_bill ,0)+\n" +
				  " COALESCE(govt_amount_bill ,0)+\n" +
				  " COALESCE(agri_amount_bill ,0)+\n" +
				  " COALESCE(hut_amount_billed ,0)+\n" +
				  " COALESCE(other_amount_bill ,0))   END)  AS total_lt_amount_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_ind_amount_collected	,0)+\n" +
				  " COALESCE(ht_com_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_ind_amount_collected ,0)+\n" +
				  " COALESCE(lt_com_amount_collected ,0)+\n" +
				  " COALESCE(lt_dom_amount_collected ,0)+\n" +
				  " COALESCE(govt_amount_collected ,0)+\n" +
				  " COALESCE(agri_amount_collected ,0)+\n" +
				  " COALESCE(hut_amount_collected ,0)+\n" +
				  " COALESCE(other_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" +
				  " \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_ind_con_count ,0)+\n" +
				  "COALESCE(lt_com_con_count ,0)+\n" +
				  "COALESCE(lt_dom_con_count ,0)+\n" +
				  "COALESCE(govt_con_count ,0)+\n" +
				  "COALESCE(agri_con_count ,0)+\n" +
				  "COALESCE(hut_consumer_count ,0)+\n" +
				  "COALESCE(other_con_count ,0))   END)  AS total_lt_consumer_count,\n" +
				  "  \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_ind_con_count	,0)+\n" +
				  "COALESCE(ht_com_con_count	,0))   END)  AS total_ht_consumer_cocount\n" +
				  "from meter_data.npp_data_monthly_calculation dc\n" +
				  " LEFT JOIN meter_data.feederdetails fd on fd.fdr_id =  dc.feeder_code WHERE tp_town_code= '"+town_code+"'  and boundry_feeder = 'false' and fd.feeder_type = 'IPDS'  and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric) GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,fd.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername)Z\n" +
				  " LEFT JOIN(\n" +
				  "Select mtrno,SUM(energy_supplied)as total_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n" +
				  "SUM(kwh_exp)as total_export_energy_supplied\n" +
				  "from (\n" +
				  "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\n" +
				  "--(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as energy_supplied  from (\n" +
				  "(kwh_imp) as energy_supplied  from (\n" +
				  " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  meter_data.monthly_consumption mc  \n" +
				  " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and mc.billmonth >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno GROUP BY feeder_code, feeder_type,\n" +
				  "tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied,boundary_name,total_export_energy_supplied,feedername,total_import_energy_supplied ORDER BY feeder_code)maind\n" +
				  "LEFT JOIN \n" +
				  "(Select (coalesce(mainfeeder_unit_supply,0) - coalesce(mainboundary_unit_supply,0)) as main_unit_supply,unit_tp_fdr_id from (\n" +
				  " Select Max(CASE WHEN boundry_feeder  is false  THEN main_unit_supply END )as  mainfeeder_unit_supply,\n" +
				  " max(CASE WHEN boundry_feeder  is true  THEN main_unit_supply END )as  mainboundary_unit_supply,unit_tp_fdr_id from(\n" +
				  " Select SUM(Dtotal_energy_supplied) as main_unit_supply ,tp_fdr_id as unit_tp_fdr_id,boundry_feeder\n" +
				  "from (\n" +
				  "Select mtrno,SUM(Denergy_supplied)as Dtotal_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n" +
				  "SUM(kwh_exp)as total_export_energy_supplied,tp_fdr_id,boundry_feeder\n" +
				  "from (\n" +
				  "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,tp_fdr_id,\n" +
				  "(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as Denergy_supplied  from (\n" +
				  "--(kwh_imp) as energy_supplied  from (\n" +
				  " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder,tp_fdr_id  from  meter_data.monthly_consumption  mc  \n" +
				  " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and mc.billmonth >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric)"
				  + " AND mc.billmonth <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno,tp_fdr_id,boundry_feeder ORDER BY tp_fdr_id)za GROUP BY tp_fdr_id,za.boundry_feeder )xxx  "
				  + " GROUP BY unit_tp_fdr_id ORDER BY unit_tp_fdr_id  )xxxx)units  on units.unit_tp_fdr_id= maind.tp_feeder_code)" ;
				  
		  
		  int  x ;
		  
		  try {
		x =   postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		  
		  
		  }
		  return msg;
	}
	
	
	@Override
	public String scheduleDataPushBoundaryFeeder() {
		  String msg = null;
		  
		  String reportMonth = "202001";
		  String town_code = "013";
		  String Monthsinterval = null;
		  String TableName = null;
		  String energySuppliedInterval= null;
		  
		  for (int i = 12; i >= 0 ; i -= 2) {
				
				 System.out.println("This is I " +i);
				 
				 
				 if(i == 12)
				 {
					 Monthsinterval = "11 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_12months";
					  energySuppliedInterval= "14 MONTH";
					  		 
				 }
				 
				 if(i == 10)
				 {
					 Monthsinterval = "09 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_10months";
					  energySuppliedInterval= "12 MONTH";
					  		 
				 }
				 
				 if(i == 8)
				 {
					 Monthsinterval = "07 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_08months";
					  energySuppliedInterval= "10 MONTH";
					  		 
				 }
				 
				 if(i == 6)
				 {
					 Monthsinterval = "05 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_06months";
					  energySuppliedInterval= "08 MONTH";
					  		 
				 }
				 
				 if(i == 4)
				 {
					 Monthsinterval = "03 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_04months";
					  energySuppliedInterval= "06 MONTH";
					  		 
				 }
				 
				 
				 if(i == 2)
				 {
					 Monthsinterval = "01 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_02months";
					  energySuppliedInterval= "04 MONTH";
					  		 
				 }
			
				 if(i == 0)
				 {
					 Monthsinterval = "00 MONTH";
					  TableName = "rpt_eaboundaryfeeder_losses_00months";
					  energySuppliedInterval= "03 MONTH";
					  		 
				 }
				
				 String qry =  "insert into meter_data."+TableName+" (\n" +
						 "feeder_code,feeder_name,feeder_tp_id,meterno,boundary_feeder_id,\n" +
						 "boundary_feeder_name,boundary_feeder_meterno,billmonth,feeder_import_energy,\n" +
						 "boundary_import_energy,boundary_export_energy)\n" +
						 "(Select  feeder_code,feedername,tp_feeder_code,meterno,boundary_id,boundary_name,meterno,\n" +
						 "  CAST ('"+reportMonth+"' as NUMERIC) report_month,  \n" +
						 "total_energy_supplied,\n" +
						 "total_import_energy_supplied,total_export_energy_supplied\n" +
						 "from (Select feeder_code,dc.feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,boundary_name,feedername,\n" +
						 "boundary_id\n" +
						 "from meter_data.npp_data_monthly_calculation dc\n" +
						 " LEFT JOIN meter_data.feederdetails fd on fd.tp_fdr_id =  dc.tp_feeder_code WHERE tp_town_code= '013' and boundry_feeder = 'true' "
						 + "and CAST (monthyear as NUMERIC) >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric) "
						 + "GROUP BY feeder_code,tp_feeder_code,towncode,dc.officeid,dc.feeder_type,fd.meterno,fd.boundry_feeder ,boundary_name,feedername,boundary_id)Z\n" +
						 " LEFT JOIN(\n" +
						 "Select mtrno,SUM(energy_supplied)as total_energy_supplied,SUM(kwh_imp)as total_import_energy_supplied,\n" +
						 "SUM(kwh_exp)as total_export_energy_supplied\n" +
						 "from (\n" +
						 "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\n" +
						 "(kwh_imp) as energy_supplied  from (\n" +
						 " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  meter_data.monthly_consumption mc  \n" +
						 " left join meter_data.feederdetails fd on fd.meterno = mc.mtrno  where fd.feeder_type = 'IPDS' and boundry_feeder = 'true'"
						 + " and mc.billmonth >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric)"
						 + " AND mc.billmonth <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno "
						 + "GROUP BY feeder_code, feeder_type,\n" +
						 "tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied,boundary_name,total_export_energy_supplied,boundary_id,feedername,"
						 + "total_import_energy_supplied ORDER BY feeder_code)";
		  
		  System.out.println("Boundary Feeder Push Query ---> " +qry) ;
		  
		  
		  int  x;
		  
		  try {
		x =  postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		  }
		  return msg;
		}
	

	

	@Override
	public String scheduledataPushDT() {
		 String msg = null;
		  
		  String reportMonth = "202001";
		  String town_code = "013";
		  String Monthsinterval = null;
		  String TableName = null;
		  String energySuppliedInterval= null;
		  
		  for (int i = 12; i >= 0 ; i -= 2) {
				
				 System.out.println("This is I " +i);
				 
				 
				 if(i == 12)
				 {
					 Monthsinterval = "11 MONTH";
					  TableName = "rpt_eadt_losses_12months";
					  energySuppliedInterval= "14 MONTH";
					  		 
				 }
				 
				 if(i == 10)
				 {
					 Monthsinterval = "09 MONTH";
					  TableName = "rpt_eadt_losses_10months";
					  energySuppliedInterval= "12 MONTH";
					  		 
				 }
				 
				 if(i == 8)
				 {
					 Monthsinterval = "07 MONTH";
					  TableName = "rpt_eadt_losses_08months";
					  energySuppliedInterval= "10 MONTH";
					  		 
				 }
				 
				 if(i == 6)
				 {
					 Monthsinterval = "05 MONTH";
					  TableName = "rpt_eadt_losses_06months";
					  energySuppliedInterval= "08 MONTH";
					  		 
				 }
				 
				 if(i == 4)
				 {
					 Monthsinterval = "03 MONTH";
					  TableName = "rpt_eadt_losses_04months";
					  energySuppliedInterval= "06 MONTH";
					  		 
				 }
				 
				 
				 if(i == 2)
				 {
					 Monthsinterval = "01 MONTH";
					  TableName = "rpt_eadt_losses_02months";
					  energySuppliedInterval= "04 MONTH";
					  		 
				 }
			
				 if(i == 0)
				 {
					 Monthsinterval = "00 MONTH";
					  TableName = "rpt_eadt_losses_00months";
					  energySuppliedInterval= "03 MONTH";
					  		 
				 }
				
		  
		  
		  String qry = "insert into meter_data."+TableName+"(\n" +
				  "month_year,\n" +
				  "tpdt_id,\n" +
				  "meterno,\n" +
				  "feeder_tp_id,\n" +
				  "town_code,\n" +
				  "dt_name,\n" +
				  "feedername,\n" +
				  "office_id,\n" +
				  "time_stamp,\n" +
				  "total_unit_supply,\n" +
				  "total_unit_billed,\n" +
				  "total_amount_billed,\n" +
				  "total_amount_collected,\n" +
				  "total_consumer_count,\n" +
				  "total_ht_unit_billed,\n" +
				  "total_lt_unit_billed,\n" +
				  "total_ht_amount_billed,\n" +
				  "total_lt_amount_billed,\n" +
				  "total_ht_amount_collected,\n" +
				  "total_lt_amount_collected,\n" +
				  "total_lt_consumer_count,\n" +
				  "total_ht_consumer_cocount\n" +
				  ")\n" +
				  "(\n" +
				  "Select distinct *from \n" +
				  "(SELECT \n" +
				  "'"+reportMonth+"' as monthyear, p.tpdtid, p.meterno,p.tp_fdr_id,p.towncode, p.dtname,p.feedername,p.officeid,now(),\n" +
				  "COALESCE(p.total_energy_supplied,0)+COALESCE(q.total_energy_supplied,0) AS gtotal_energy_supplied,\n" +
				  "COALESCE(p.total_ht_unit_billed,0)+COALESCE(p.total_lt_unit_billed,0) AS total_unit_billed,\n" +
				  "COALESCE(p.total_ht_amount_billed,0)+COALESCE(p.total_lt_amount_billed,0) AS total_amount_billed,\n" +
				  "COALESCE(p.total_ht_amount_collected,0)+COALESCE(p.total_lt_amount_collected,0) AS total_amount_collected,\n" +
				  "COALESCE(p.total_lt_consumer_count,0)+COALESCE(p.total_ht_consumer_cocount,0) AS total_consumer_cocount,\n" +
				  "P.total_ht_unit_billed,\n" +
				  "P.total_lt_unit_billed,\n" +
				  "P.total_ht_amount_billed,\n" +
				  "P.total_lt_amount_billed,\n" +
				  "P.total_ht_amount_collected,\n" +
				  "P.total_lt_amount_collected,\n" +
				  "P.total_lt_consumer_count,\n" +
				  "P.total_ht_consumer_cocount\n" +
				  "FROM \n" +
				  "(select tpdtid,tp_fdr_id,towncode, meterno, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') AS METERNO2, \n" +
				  "sum(total_ht_unit_billed)total_ht_unit_billed,\n" +
				  "sum(total_lt_unit_billed)total_lt_unit_billed,\n" +
				  "sum(total_ht_amount_billed)total_ht_amount_billed,\n" +
				  "sum(total_lt_amount_billed)total_lt_amount_billed,\n" +
				  "sum(total_ht_amount_collected)total_ht_amount_collected,\n" +
				  "sum(total_lt_amount_collected)total_lt_amount_collected,\n" +
				  "sum(total_lt_consumer_count)total_lt_consumer_count,\n" +
				  "sum(total_ht_consumer_cocount)total_ht_consumer_cocount,\n" +
				  "sum(total_energy_supplied )total_energy_supplied  from \n" +
				  "(Select tp_fdr_id,tpdtid, meterno,towncode,dtname,feedername,officeid,\n" +
				  "split_part(meterno, ',', 1) as meterno1,\n" +
				  "split_part(meterno, ',', 2) as meterno2,\n" +
				  "split_part(meterno, ',', 3) as meterno3,\n" +
				  "--regexp_split_to_table(meterno,','),\n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(ht_industrial_energy_billed ,0)+\n" +
				  " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(lt_industrial_energy_billed ,0)+\n" +
				  " COALESCE(lt_commercial_energy_billed ,0)+\n" +
				  " COALESCE(lt_domestic_energy_billed ,0)+\n" +
				  " COALESCE(govt_energy_billed ,0)+\n" +
				  " COALESCE(agri_energy_billed ,0)+\n" +
				  " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\n" +
				  "  \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_amount_billed	,0)+\n" +
				  " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_amount_billed ,0)+\n" +
				  " COALESCE(lt_commercial_amount_billed ,0)+\n" +
				  " COALESCE(lt_domestic_amount_billed ,0)+\n" +
				  " COALESCE(govt_amount_billed ,0)+\n" +
				  " COALESCE(agri_amount_billed ,0)+\n" +
				  " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_amount_collected	,0)+\n" +
				  " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_amount_collected ,0)+\n" +
				  " COALESCE(lt_commercial_amount_collected ,0)+\n" +
				  " COALESCE(lt_domestic_amount_collected ,0)+\n" +
				  " COALESCE(govt_amount_collected ,0)+\n" +
				  " COALESCE(agri_amount_collected ,0)+\n" +
				  " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" +
				  " \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_consumer_count ,0)+\n" +
				  "COALESCE(lt_commercial_consumer_count ,0)+\n" +
				  "COALESCE(lt_domestic_consumer_count ,0)+\n" +
				  "COALESCE(govt_consumer_count ,0)+\n" +
				  "COALESCE(agri_consumer_count ,0)+\n" +
				  "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\n" +
				  "  \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_consumer_count	,0)+\n" +
				  "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\n" +
				  " \n" +
				  " \n" +
				  " \n" +
				  "from meter_data.npp_dt_rpt_monthly_calculation npDt    GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\n" +
				  "left join \n" +
				  "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \n" +
				  "where  mc.billmonth >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '14 MONTH','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) GROUP BY monthMeterno )y\n" +
				  "on x.meterno1=y.monthMeterno\n" +
				  "GROUP BY  tpdtid, tp_fdr_id,meterno,towncode, meterno1,dtname,feedername,officeid, COALESCE(meterno2,'0') \n" +
				  " )P\n" +
				  "LEFT JOIN \n" +
				  "(select  tpdtid, meterno,tp_fdr_id,towncode,  COALESCE(meterno2,'0') AS METERNO2,  total_ht_unit_billed, \n" +
				  "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount,\n" +
				  "sum(total_energy_supplied)total_energy_supplied   from \n" +
				  "(Select tp_fdr_id,tpdtid, meterno,towncode,\n" +
				  "split_part(meterno, ',', 2) as meterno2,\n" +
				  "--regexp_split_to_table(meterno,','),\n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC) >=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(ht_industrial_energy_billed ,0)+\n" +
				  " COALESCE(ht_commercial_energy_billed ,0))    END)  AS total_ht_unit_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  " COALESCE(lt_industrial_energy_billed ,0)+\n" +
				  " COALESCE(lt_commercial_energy_billed ,0)+\n" +
				  " COALESCE(lt_domestic_energy_billed ,0)+\n" +
				  " COALESCE(govt_energy_billed ,0)+\n" +
				  " COALESCE(agri_energy_billed ,0)+\n" +
				  " COALESCE(others_energy_billed ,0))   END)  AS total_lt_unit_billed,\n" +
				  "  \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_amount_billed	,0)+\n" +
				  " COALESCE(ht_commercial_amount_billed	,0))   END)  AS total_ht_amount_billed ,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_amount_billed ,0)+\n" +
				  " COALESCE(lt_commercial_amount_billed ,0)+\n" +
				  " COALESCE(lt_domestic_amount_billed ,0)+\n" +
				  " COALESCE(govt_amount_billed ,0)+\n" +
				  " COALESCE(agri_amount_billed ,0)+\n" +
				  " COALESCE(others_amount_billed ,0))   END)  AS total_lt_amount_billed,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_amount_collected	,0)+\n" +
				  " COALESCE(ht_commercial_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" +
				  " \n" +
				  " SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_amount_collected ,0)+\n" +
				  " COALESCE(lt_commercial_amount_collected ,0)+\n" +
				  " COALESCE(lt_domestic_amount_collected ,0)+\n" +
				  " COALESCE(govt_amount_collected ,0)+\n" +
				  " COALESCE(agri_amount_collected ,0)+\n" +
				  " COALESCE(others_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" +
				  " \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '"+Monthsinterval+"'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(lt_industrial_consumer_count ,0)+\n" +
				  "COALESCE(lt_commercial_consumer_count ,0)+\n" +
				  "COALESCE(lt_domestic_consumer_count ,0)+\n" +
				  "COALESCE(govt_consumer_count ,0)+\n" +
				  "COALESCE(agri_consumer_count ,0)+\n" +
				  "COALESCE(others_consumer_count ,0))   END)  AS total_lt_consumer_count,\n" +
				  "  \n" +
				  "SUM(CASE WHEN (CAST (monthyear as NUMERIC)>=\n" +
				  "cast(to_char((TO_DATE(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n" +
				  "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (CAST (monthyear as NUMERIC)<=CAST( to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n" +
				  "COALESCE(ht_industrial_consumer_count	,0)+\n" +
				  "COALESCE(ht_commercial_consumer_count	,0))   END)  AS total_ht_consumer_cocount\n" +
				  " \n" +
				  " \n" +
				  " \n" +
				  "from meter_data.npp_dt_rpt_monthly_calculation npDt   GROUP BY tp_fdr_id,tpdtid,towncode,dtname,feedername,officeid, METERNO)x\n" +
				  "join \n" +
				  "(Select SUM(kwh_imp)as total_energy_supplied,  mtrno as monthMeterno  from  meter_data.monthly_consumption mc  \n" +
				  "where  mc.billmonth >=cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '"+energySuppliedInterval+"','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('"+reportMonth+"', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric) GROUP BY monthMeterno )y\n" +
				  "on x.meterno2=y.monthMeterno \n" +
				  "group by  tpdtid, meterno,  COALESCE(meterno2,'0') ,  total_ht_unit_billed,tp_fdr_id,towncode,\n" +
				  "total_lt_unit_billed,total_ht_amount_billed,total_lt_amount_billed,total_ht_amount_collected,total_lt_amount_collected,total_lt_consumer_count,total_ht_consumer_cocount\n" +
				  ")Q\n" +
				  "ON P.METERNO=Q.METERNO   ) q\n" +
				  ")" ;
		  
		  int  x;
		  
		  try {
		x =   postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  }
		  return msg;
		  
	
	}

}
