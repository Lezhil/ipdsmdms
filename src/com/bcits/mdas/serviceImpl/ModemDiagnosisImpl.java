package com.bcits.mdas.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.ModemDiagnosisEntity;
import com.bcits.mdas.service.ModemDiagnosisService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemDiagnosisImpl extends GenericServiceImpl<ModemDiagnosisEntity> implements ModemDiagnosisService {
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ModemDiagnosisEntity> getModemDiagnosisStats(String zone, String circle, String division,String subdiv, ModelMap model, HttpServletRequest request)
	{
		List<ModemDiagnosisEntity> list=null;
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		else
		{
			circle=circle;
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		else
		{
			division=division;
		}
		if(subdiv.equalsIgnoreCase("All"))
		{
			subdiv="%";
		}
		else
		{
			subdiv=subdiv;
		}
		try
		{
			/*String sql="SELECT COUNT(*) as Total,\n" +
					"COUNT(case when CURRENT_DATE=d.\"date\" then 1 end) as Today,\n" +
					"COUNT(case when (CURRENT_DATE-1)=d.\"date\" then 1 end) as Yesterday,\n" +
					"COUNT(case when to_char(current_date,'yyyyMM')=to_char(\"date\",'yyyyMM') then 1 end) as Current_Month,\n" +
					"COUNT(case when to_char(current_date - interval '1' month,'yyyyMM')=to_char(d.\"date\",'yyyyMM') then 1 end) as Prev_Month \n" +
					"FROM meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
					"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"'";*/
			String sql="SELECT COUNT(*) as Total,\n" +
					"COUNT(case when CURRENT_DATE=date(d.event_time) then 1 end) as Today,\n" +
					"COUNT(case when (CURRENT_DATE-1)=date(d.event_time) then 1 end) as Yesterday,\n" +
					"COUNT(case when to_char(current_date,'yyyyMM')=to_char(date(d.event_time),'yyyyMM') then 1 end) as Current_Month,\n" +
					"COUNT(case when to_char(current_date - interval '1' month,'yyyyMM')=to_char(date(d.event_time),'yyyyMM') then 1 end) as Prev_Month \n" +
					"FROM meter_data.events d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
					"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"';";
			System.out.println("Query getModemDiagnosisStats===>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			if(list.size()<1){
				model.put("results","No Data is Available");
			}
			else{
				model.addAttribute("mdmMngtList",list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ModemDiagnosisEntity> getModemDiagnosisCat(String zone, String circle, String division, String subdiv, String category,ModelMap model,HttpServletRequest request)
	//public List<ModemDiagnosisEntity> getModemDiagnosisCat(String category,ModelMap model,HttpServletRequest request)
	{
		List<ModemDiagnosisEntity> list=null;
		String sql=null;
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		else
		{
			circle=circle;
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		else
		{
			division=division;
		}
		if(subdiv.equalsIgnoreCase("All"))
		{
			subdiv="%";
		}
		else
		{
			subdiv=subdiv;
		}
		try
		{
			if(category.equalsIgnoreCase("Today"))
			{
				sql="SELECT d.meter_number,d.event_code,d.event_time,\n" +
						"(SELECT event from meter_data.event_master WHERE event_code=CAST(d.event_code as INTEGER)) as ecent_desp,\n" +
						"m.accno,m.customer_name\n" +
						"FROM meter_data.events d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
						"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"' AND date(d.event_time)=CURRENT_DATE ";
				
			}
			else if(category.equalsIgnoreCase("Yesterday"))
			{
				sql="SELECT d.meter_number,d.event_code,d.event_time,\n" +
						"(SELECT event from meter_data.event_master WHERE event_code=CAST(d.event_code as INTEGER)) as ecent_desp,\n" +
						"m.accno,m.customer_name\n" +
						"FROM meter_data.events d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
						"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"' AND date(d.event_time)=CURRENT_DATE-1 ";
				
			}
			else if(category.equalsIgnoreCase("CurrMonth"))
			{
				sql="SELECT d.meter_number,d.event_code,d.event_time,\n" +
						"(SELECT event from meter_data.event_master WHERE event_code=CAST(d.event_code as INTEGER)) as ecent_desp,\n" +
						"m.accno,m.customer_name\n" +
						"FROM meter_data.events d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
						"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"' "+
						" AND to_char(current_date,'yyyyMM')=to_char(date(d.event_time),'yyyyMM') ";
			}
			else if(category.equalsIgnoreCase("PrevMonth"))
			{
				sql="SELECT d.meter_number,d.event_code,d.event_time,\n" +
						"(SELECT event from meter_data.event_master WHERE event_code=CAST(d.event_code as INTEGER)) as ecent_desp,\n" +
						"m.accno,m.customer_name\n" +
						"FROM meter_data.events d,meter_data.master_main m WHERE d.meter_number=m.mtrno and m.zone like '"+zone+"' \n" +
						"and m.circle like '"+circle+"' and m.division like '"+division+"' and m.subdivision like '"+subdiv+"' "+
						" AND to_char(current_date - interval '1' month,'yyyyMM')=to_char(date(d.event_time),'yyyyMM') ";
			}
			//System.out.println("Query getModemDiagnosisCat===>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			if(list.size()<1){
				model.put("results","No Data is Available");
			}
			else{
				model.addAttribute("diagnosCatList",list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getmodemcount(String zone, String circle, String division,String subdiv,ModelMap model) {
		List<?> list=null;
		String qry="";
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		else
		{
			circle=circle;
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		else
		{
			division=division;
		}
		if(subdiv.equalsIgnoreCase("All"))
		{
			subdiv="%";
		}
		else
		{
			subdiv=subdiv;
		}
		try {
			qry="SELECT COUNT(*) as Total,\n" +
					"					COUNT(case when CURRENT_DATE=d.date then 1 end) as Today,\n" +
					"					COUNT(case when (CURRENT_DATE-1)=d.date then 1 end) as Yesterday,\n" +
					"					COUNT(case when to_char(current_date,'yyyyMM')=to_char(date,'yyyyMM') then 1 end) as Current_Month,\n" +
					"					COUNT(case when to_char(current_date - interval '1' month,'yyyyMM')=to_char(d.date,'yyyyMM') then 1 end) as Prev_Month\n" +
					"					FROM meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno and \n" +
					"					m.zone like '"+zone+"' \n" +
					"					and m.circle like '"+circle+"' \n" +
					"					and m.division like '"+division+"' \n" +
					"					and m.subdivision like '"+subdiv+"';";
			System.out.println(qry);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			if(list.size()<1){
				model.put("results","No Data is Available");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getValDiagStat(String zone, String circle, String division,String subdiv, String category, ModelMap model) {
		List<?> list=null;
		String qry="";
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		else
		{
			circle=circle;
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		else
		{
			division=division;
		}
		if(subdiv.equalsIgnoreCase("All"))
		{
			subdiv="%";
		}
		else
		{
			subdiv=subdiv;
		}
		try {
			if(category.equalsIgnoreCase("Today")){
				qry="SELECT d.meter_number,d.imei,d.tracked_time,d.diag_type,\n" +
						"(case d.status \n" +
						"WHEN 'NLEN' THEN 'Modem not able to communicate with meter. Problem is their with meter cable connection or meter'\n" +
						"WHEN 'CRCF' THEN 'Error in dlms CRC(cyclic redundancy check) format'\n" +
						"WHEN 'AARJ' THEN 'Meter is rejecting. Need to give correct password before communication with meter' \n" +
						"WHEN 'FRER' THEN 'Framing error in dlms format'\n" +
						"WHEN 'FAIL' THEN 'Default error' END) as Status,m.accno,m.customer_name FROM \n" +
						"meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno \n" +
						"and m.zone like '"+zone+"'  and m.circle like '"+circle+"'  and m.division like '"+division+"'  \n" +
						"and m.subdivision like '"+subdiv+"' and current_date=d.date;";
			}
			else if(category.equalsIgnoreCase("Yesterday")){
				
			
				qry="SELECT d.meter_number,d.imei,d.tracked_time,d.diag_type,\n" +
				"(case d.status \n" +
				"WHEN 'NLEN' THEN 'Modem not able to communicate with meter. Problem is their with meter cable connection or meter'\n" +
				"WHEN 'CRCF' THEN 'Error in dlms CRC(cyclic redundancy check) format'\n" +
				"WHEN 'AARJ' THEN 'Meter is rejecting. Need to give correct password before communication with meter' \n" +
				"WHEN 'FRER' THEN 'Framing error in dlms format'\n" +
				"WHEN 'FAIL' THEN 'Default error' END) as Status,m.accno,m.customer_name FROM \n" +
				"meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno \n" +
				"and m.zone like '"+zone+"'  and m.circle like '"+circle+"'  and m.division like '"+division+"'  \n" +
				"and m.subdivision like '"+subdiv+"' and current_date-1=d.date;";
			
			}
			else if(category.equalsIgnoreCase("CurrMonth")){
				qry="SELECT d.meter_number,d.imei,d.tracked_time,d.diag_type,\n" +
						"(case d.status \n" +
						"WHEN 'NLEN' THEN 'Modem not able to communicate with meter. Problem is their with meter cable connection or meter'\n" +
						"WHEN 'CRCF' THEN 'Error in dlms CRC(cyclic redundancy check) format'\n" +
						"WHEN 'AARJ' THEN 'Meter is rejecting. Need to give correct password before communication with meter' \n" +
						"WHEN 'FRER' THEN 'Framing error in dlms format'\n" +
						"WHEN 'FAIL' THEN 'Default error' END) as Status,m.accno,m.customer_name FROM \n" +
						"meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno \n" +
						"and m.zone like '"+zone+"'  and m.circle like '"+circle+"'  and m.division like '"+division+"'  \n" +
						"and m.subdivision like '"+subdiv+"' and to_char(current_date,'yyyyMM')=to_char(date,'yyyyMM');";
				
			}
			else if(category.equalsIgnoreCase("PrevMonth")){
				qry="SELECT d.meter_number,d.imei,d.tracked_time,d.diag_type,\n" +
						"(case d.status \n" +
						"WHEN 'NLEN' THEN 'Modem not able to communicate with meter. Problem is their with meter cable connection or meter'\n" +
						"WHEN 'CRCF' THEN 'Error in dlms CRC(cyclic redundancy check) format'\n" +
						"WHEN 'AARJ' THEN 'Meter is rejecting. Need to give correct password before communication with meter' \n" +
						"WHEN 'FRER' THEN 'Framing error in dlms format'\n" +
						"WHEN 'FAIL' THEN 'Default error' END) as Status,m.accno,m.customer_name FROM \n" +
						"meter_data.modem_diagnosis d,meter_data.master_main m WHERE d.meter_number=m.mtrno \n" +
						"and m.zone like '"+zone+"'  and m.circle like '"+circle+"'  and m.division like '"+division+"'  \n" +
						"and m.subdivision like '"+subdiv+"' and  to_char(current_date - interval '1' month,'yyyyMM')=to_char(d.date,'yyyyMM');";
				
			}
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();	
			System.out.println(qry);
			if(list.size()<1){
				model.put("results","No Data is Available");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
