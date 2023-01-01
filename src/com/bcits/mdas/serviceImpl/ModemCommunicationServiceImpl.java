package com.bcits.mdas.serviceImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.ModemCommunication;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemCommunicationServiceImpl extends GenericServiceImpl<ModemCommunication> implements ModemCommunicationService {

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getActInactModemCount() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select count(distinct lastmm.zone)as zone,COUNT (DISTINCT lastmm.modem_sl_no),count(case  when sd.times<24 then 1 end) as active "
				+"from ("
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ("
				+"epoch "
				+"FROM "
				+"age("
				+"to_timestamp(:timeStamp,'YYYY-MM-DD HH24:MI:SS.MS'), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times " 
				+"FROM "
				+"(select mc.imei as imei,max(mc.last_communication) as lastComm from meter_data.modem_communication mc,meter_data.master_main mm where 	mc.imei=mm.modem_sl_no GROUP BY imei )lstCom "
				+") sd ,meter_data.master_main lastmm WHERE	lastmm.modem_sl_no = sd.imei";
		
		System.out.println("------------------active count------------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("timeStamp", timeStamp).getResultList();
			return li;
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getActInactModemCountSublevel() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select mm.zone,count(DISTINCT mm.modem_sl_no),count(case  when sd.times<24 then 1 end) as active "
				+"from ("
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ("
				+"epoch "
				+"FROM "
				+"age("
				+"to_timestamp(:timeStamp,'YYYY-MM-DD HH24:MI:SS.MS'), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times " 
				+"FROM "
				+"(select imei,max(last_communication) as lastComm from meter_data.modem_communication GROUP BY imei)lstCom "
				+") sd ,meter_data.master_main mm  WHERE mm.modem_sl_no=sd.imei GROUP BY mm.zone";
		
		System.out.println("============inact modem zone count============="+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("timeStamp", timeStamp).getResultList();
			return li;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getActInactModemCountSublevel(String type1,String sub_type, String value1) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select mm."+sub_type+",count(DISTINCT mm.modem_sl_no),count(case  when sd.times<24 then 1 end) as active "
				+"from ("
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ("
				+"epoch "
				+"FROM "
				+"age("
				+"to_timestamp(:timeStamp1,'YYYY-MM-DD HH24:MI:SS.MS'), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times " 
				+"FROM "
				+"(select imei,max(last_communication) as lastComm from meter_data.modem_communication GROUP BY imei)lstCom "
				+") sd ,meter_data.master_main mm  WHERE mm.modem_sl_no=sd.imei and mm."+type1+"=:value1 and mm.zone=:value1 GROUP BY :type1,mm."+sub_type;
		System.out.println("=timeStamp=============="+timeStamp);
		System.out.println("value1====="+value1);
		System.out.println("type1==========="+type1);
		System.out.println("sub_type==========="+sub_type);
		
		
		
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("timeStamp1", timeStamp).setParameter("value1", value1).setParameter("type1", type1).getResultList();
			return li;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getActInactModemCount(String type1, String value1){
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select :type1,count(DISTINCT mm.modem_sl_no),count(case  when sd.times<24 then 1 end) as active "
				+"from ("
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ("
				+"epoch "
				+"FROM "
				+"age("
				+"to_timestamp(:timeStamp,'YYYY-MM-DD HH24:MI:SS.MS'), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times " 
				+"FROM "
				+"(select imei,max(last_communication) as lastComm from meter_data.modem_communication GROUP BY imei)lstCom "
				+") sd ,meter_data.master_main mm  WHERE mm.modem_sl_no=sd.imei and mm."+type1+"=:value1 GROUP BY mm."+type1;
		System.out.println("*************************************************"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("type1", type1).setParameter("timeStamp", timeStamp).setParameter("value1", value1).getResultList();
			return li;
	}
	
	
	
	
	
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getModemCountZone() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select mm.zone,count(case  when sd.times<24 then 1 end) as active "
				+"from ("
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ("
				+"epoch "
				+"FROM "
				+"age("
				+"to_timestamp('"
				+timeStamp+"', "
				+"'YYYY-MM-DD HH24:MI:SS.MS' "
				+"), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times " 
				+"FROM "
				+"(select imei,max(last_communication) as lastComm from meter_data.modem_communication GROUP BY imei)lstCom "
				+") sd ,meter_data.master_main mm  WHERE mm.modem_sl_no=sd.imei GROUP BY mm.zone";
		
		System.out.println(sql);
		List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			return li.get(0).intValue();
	}
	
	
	
	

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getInactiveIMEI() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String sql="select lst.inact as inactive  from " 
				+"(select "
				+"(case  when sd.times>=24 then  sd.imei end)  as inact " 
				+"from ( "
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm , "
				+"EXTRACT ( "
				+"epoch "
				+"FROM "
				+"age( "
				+"to_timestamp( '"
				+timeStamp+"', "
				+"'YYYY-MM-DD HH24:MI:SS.MS' "
				+"), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") as times "
				+"FROM "
				+"(select imei,max(last_communication) as lastComm from meter_data.modem_communication GROUP BY imei )lstCom "
				+") sd )lst where lst.inact is not NULL"; 

		System.out.println(sql);
		List<String> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			return li;
	}

	@Override
	public List<?> getInactiveModemDetails(List<String> InactiveModems) {
		// TODO Auto-generated method stub
		String modemsfromList="";
		boolean flag=false;
		int i=0;
		 for (String modem : InactiveModems) {
			if(i>0){
				 modemsfromList+=",";
			}
			 modemsfromList+="'"+modem+"'";
			 	i++;
		    }
		String sql="SELECT "
				+"mm.modem_sl_no, "
				+"mm.mtrno, "
				+"mm.fdrcode, "
				+"mm.substation, "
				+"mm.subdivision, "
				+"mm.division, "
				+"mm.district, "
				+"mm.circle, "
				+"sb.lastComm "
				+"FROM "
				+"( "
				+"SELECT "
				+"mc.imei AS mdlNo, "
				+"MAX(mc.last_communication) AS lastComm "
				+"FROM "
				+"meter_data.modem_communication mc "
				+"GROUP BY "
				+"mc.imei "
				+"HAVING "
				+"mc.imei in ("+modemsfromList+")"
				+") sb,meter_data.master_main mm  "
				+"where mm.modem_sl_no=sb.mdlNo"; 

		System.out.println("-----------------insctive modem details------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	}


	@Override
	public List<?> getFailUploadMtrDetails() {
		String sql="select fq.meter_number,mm.fdrcode,mm.substation,fq.fail_reason from "
				+"(SELECT "
				+"DISTINCT meter_number, "
				+"fail_reason "
				+"FROM "
				+"meter_data.xml_upload_status "
				+"WHERE "
				+"file_date=CURRENT_DATE-1 and "
				+"upload_status = 0"
				+ ")fq,meter_data.master_main mm where fq.meter_number=mm.mtrno"; 

		System.out.println("-----------------fail upload mtr details------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	}
	
	
	@Override
	public List<?> getFailUploadMtrDetailsZone(String zone) {
		String sql="select fq.meter_number,mm.fdrcode,mm.substation,fq.fail_reason from "
				+"(SELECT "
				+"DISTINCT meter_number, "
				+"fail_reason "
				+"FROM "
				+"meter_data.xml_upload_status "
				+"WHERE "
				+"file_date=CURRENT_DATE-1 and "
				+"upload_status = 0"
				+ ")fq,meter_data.master_main mm where fq.meter_number=mm.mtrno and mm.zone='"+zone+"'"; 

		System.out.println("----------------zone-fail upload mtr details------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	}
	
	
	@Override
	public List<?> getInactiveModemDetailsZone(String zone) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

		String sql="SELECT "
				+"inactive as modemNo,md.mtrno,md.fdrcode,md.substation,md.subdivision,md.division,md.district,md.circle,timeLast "
				+"FROM "
				+"( "
				+"SELECT "
				+"(CASE "
				+"WHEN sd.times >= 24 THEN "
				+"sd.imei "
				+"END)as inactive,sd.lastComm as timeLast " 
				+"FROM "
				+"( "
				+"SELECT "
				+"lstCom.imei, "
				+"lstCom.lastComm, "
				+"EXTRACT ( "
				+"epoch "
				+"FROM "
				+"age( "
				+"to_timestamp( "
				+"'"+timeStamp+"', "
				+"'YYYY-MM-DD HH24:MI:SS.MS' "
				+"), "
				+"lstCom.lastComm "
				+") / 3600 "
				+") AS times "
				+"FROM "
				+"( "
				+"SELECT "
				+"imei, "
				+"MAX (last_communication) AS lastComm "
				+"FROM "
				+"meter_data.modem_communication "
				+"GROUP BY "
				+"imei "
				+") lstCom "
				+") sd  "
				+") pd,meter_data.master_main md WHERE inactive IS NOT NULL and pd.inactive=md.modem_sl_no and md.zone='"+zone+"'"; 

		System.out.println("-----------------inactive modem details--"+zone+"----------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	}

	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ModelMap getFdrTimeStamps(String METERNO, String MODEM) 
	{
		ModelMap model = new ModelMap();
 		SimpleDateFormat timeStamp2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String query = "SELECT (SELECT max(last_communication)) as last_communication ,(SELECT max(last_sync_inst)) as last_sync_inst ,"
					+" (SELECT max(last_sync_event)) as last_sync_event ,(SELECT max(last_sync_load)) as last_sync_load ,"
					+" (SELECT max(last_sync_bill)) as last_sync_bill  FROM	meter_data.modem_communication mc"
					+" WHERE	mc.meter_number = '"+METERNO+"' ";
		Query q= getCustomEntityManager("postgresMdas").createNativeQuery(query);

		List list=q.getResultList();
		Object[] val=(Object[]) list.get(0);
		if(val.length>0)
		{
			if(val[0]==null) 
			{
				model.put("last_communication","No Data");
				model.put("last_communicationActive",false);

			}
			else
			{
				model.put("last_communication",timeStamp2.format(val[0]));
				model.put("last_communicationActive",isActive(timeStamp2.format(val[0])));
			}		
			if(val[1]==null) 
			{
				model.put("last_sync_inst","No Data");
				model.put("last_sync_instActive",false);
			}
			else
			{
				model.put("last_sync_inst",timeStamp2.format(val[1]));
				model.put("last_sync_instActive",isActive(timeStamp2.format(val[1])));
			}if(val[2]==null) 
			{
				model.put("last_sync_event","No Data");
				model.put("last_sync_eventActive",false);
			}
			else
			{
				model.put("last_sync_event",timeStamp2.format(val[2]));
				model.put("last_sync_eventActive",isActive(timeStamp2.format(val[2])));
			}if(val[3]==null) 
			{
				model.put("last_sync_load","No Data");
				model.put("last_sync_loadActive",false);
			}
			else
			{
				model.put("last_sync_load",timeStamp2.format(val[3]));
				model.put("last_sync_loadActive",isActive(timeStamp2.format(val[3])));
			}if(val[4]==null) 
			{
				model.put("last_sync_bill","No Data");
				model.put("last_sync_billActive",false);
			}
			else
			{
				model.put("last_sync_bill",timeStamp2.format(val[4]));
				model.put("last_sync_billActive",isActive(timeStamp2.format(val[4])));
			}	

		}

		return model;

	}

	private boolean isActive(String dateComm){
		try {
			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
			Date date2 = format.parse(dateComm);
			boolean moreThanDay = Math.abs( new Date().getTime() - date2.getTime()) > MILLIS_PER_DAY;
			if(!moreThanDay){
				return  true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public ModelMap modemInformation(String imei) {
		
		ModelMap model = new ModelMap();
		
		
		String query="SELECT mc.gprs_conn_fail_count,mc.meter_conn_fail_count,mc.temperature,mc.signal,mc.last_communication,mm.simno from meter_data.modem_communication as mc,meter_data.master_main as mm WHERE mc.imei='"+imei+"'and mm.modem_sl_no='"+imei+"' ORDER by last_communication DESC LIMIT 1";
		System.out.println("------------------------------------**********************"+query);
		Query q= getCustomEntityManager("postgresMdas").createNativeQuery(query);
		List list=q.getResultList();
		
		if(list != null && list.size()>0){
		Object[] val=(Object[]) list.get(0);
		if(val.length>0)
		{
			if(val[0]==null) 
			{
				model.put("gprs_conn_fail_count","No Data");
			}
			else
			{
				model.put("gprs_conn_fail_count",val[0]);
			}		
			if(val[1]==null) 
			{
				model.put("meter_conn_fail_count","No Data");
			}
			else
			{
				model.put("meter_conn_fail_count",val[1]);
			}if(val[2]==null) 
			{
				model.put("temperature","No Data");
			}
			else
			{
				model.put("temperature",val[2]);
			}if(val[3]==null) 
			{
				model.put("signal","No Data");
			}
			else
			{
				model.put("signal",val[3]);
			}if(val[4]==null) 
			{
				model.put("last_communication","Not Available");
			}
			else
			{
				try {
					SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					String date=format.format(val[4]) ;
					model.put("last_communication",date);
					long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
					Date date2 = format.parse(date);
					boolean moreThanDay = Math.abs( new Date().getTime() - date2.getTime()) > MILLIS_PER_DAY;
					System.out.println(moreThanDay);
					
					if(moreThanDay){
						model.put("status", "Inactive");
					}else{
						model.put("status", "Active");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					model.put("last_communication","Error");
					model.put("status", "Error");
				}
				
			}	
			if(val[5]==null) 
			{
				model.put("simno","NOData");
			}
			else
			{
				model.put("simno",val[5]);
			}
			
		}
	}
		
		return model;
	}


	@Override
	public List<?> getTotalMeterDetails(String selectedLevel, String selectedLevelName) {
		
		String queryTail="";
		if(!selectedLevel.isEmpty()){
//			queryTail=" AND mm."+selectedLevel+" = '"+selectedLevelName+"'";
			queryTail=" WHERE "+selectedLevel+" = '"+selectedLevelName+"'";

		}
		System.out.println("inside getTotalMeterDetails==>"+queryTail);
		
		
		String sql="SELECT mm.modem_sl_no, mm.mtrno, mm.fdrname, mm.substation, mm.subdivision, mm.division, mm.district, mm.circle, sb.lastComm FROM "
+" (SELECT modem_sl_no, mtrno, fdrname,substation,subdivision,division,district,circle FROM "
+" meter_data.master_main "+queryTail+")mm LEFT JOIN( SELECT mc.imei AS mdlNo, MAX(mc.last_communication) AS lastComm"
+" FROM meter_data.modem_communication mc GROUP BY mc.imei )sb on mm.modem_sl_no=sb.mdlNo";
		
/*		String sql="SELECT "
				+"mm.modem_sl_no, "
				+"mm.mtrno, "
				+"mm.fdrcode, "
				+"mm.substation, "
				+"mm.subdivision, "
				+"mm.division, "
				+"mm.district, "
				+"mm.circle, "
				+"sb.lastComm "
				+"FROM "
				+"( "
				+"SELECT "
				+"mc.imei AS mdlNo, "
				+"MAX(mc.last_communication) AS lastComm "
				+"FROM "
				+"meter_data.modem_communication mc "
				+"GROUP BY "
				+"mc.imei "
				+") sb,meter_data.master_main mm  "
				+"where mm.modem_sl_no=sb.mdlNo "+queryTail; */
		
		System.out.println("-----------------total meter details------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<FeederMasterEntity> getModelMngtDetailsBySS(String zone,String circle, String division, String subdiv, String subStation, ModelMap model, HttpServletRequest request)
	{
		System.out.println("In getModelMngtDetailsBySS==>"+zone+"==>"+circle+"==>"+division+"==>"+subdiv+"==>"+subStation);
		List<?> list=null;
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if(zone.equalsIgnoreCase("All"))
		{
			zone="%";
		}
		else
		{
			zone=zone;
		}
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
		if(subStation.equalsIgnoreCase("All"))
		{
			subStation="%";
		}
		else
		{
			subStation=subStation;
		}
		try
		{
			String sql="SELECT count(status) as total_modems,count(case  WHEN A.Status='Active' THEN 1 END) Active," +
					"count(case  WHEN A.Status='Inactive' THEN 1 END) Inactive from ( " +
					"SELECT 	(CASE WHEN sd.times < 24 THEN 'Active' ELSE	'Inactive' END) AS Status " +
					"FROM (SELECT lstCom.imei,lstCom.lastComm," +
					"EXTRACT (epoch FROM	age(to_timestamp('"+timeStamp+"','YYYY-MM-DD HH24:MI:SS.MS'),lstCom.lastComm) / 3600) AS times " +
					"FROM (SELECT imei,MAX (last_communication) AS lastComm FROM meter_data.modem_communication GROUP BY imei)lstCom) sd, " +
					"meter_data.master_main AS mm WHERE mm.modem_sl_no = sd.imei and "
					+"mm.zone like '"+zone+"' and mm.circle like '"+circle+"' and mm.division like '"+division+"' and mm.subdivision like '"+subdiv+"' "
					+"and mm.substation like '"+subStation+"')A";				
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			Object[] obj=(Object[])list.get(0);
			if(((BigInteger)obj[0]).intValue()==0){
				model.put("results","No Data is Available");
			}
			else{
				model.put("results","notDisplay");
				model.addAttribute("mdmMngtList",list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (List<FeederMasterEntity>) list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<FeederMasterEntity> getSubstationBySubDiv(String zone,String circle, String division, String subdiv, ModelMap model, HttpServletRequest request)
	{
		System.out.println("In getModelMngtDetailsBySS==>"+zone+"==>"+circle+"==>"+division+"==>"+subdiv);
		List<?> list=null;
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if(zone.equalsIgnoreCase("All"))
		{
			zone="%";
		}
		else
		{
			zone=zone;
		}
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
			/*String sql="SELECT DISTINCT substation_name,count(*) as NoF FROM vcloudengine.substation_output " +
					"WHERE substation_district=(SELECT DISTINCT district FROM meter_data.master_main m " +
					"WHERE m.zone like '"+zone+"' and m.circle like '"+circle+"' and m.division like '"+division+"' " +
					"and m.subdivision like '"+subdiv+"') GROUP BY substation_name";*/	
			String sql="SELECT subdivision,count(DISTINCT(substation_name)) as Total_substation FROM " +
					"vcloudengine.substation_output WHERE zone like '"+zone+"' and circle like '"+circle+"' " +
					"and division LIKE '"+division+"' and subdivision LIKE '"+subdiv+"' group by subdivision ";
			System.out.println("Query getSubstationBySubDiv==>"+sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			//Object[] obj=(Object[])list.get(0);
			if(list.size()<1){
				model.put("results","No Data is Available");
			}
			else{
				model.put("results","notDisplay");
				model.addAttribute("mdmMngtList",list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (List<FeederMasterEntity>) list;
	}
	


@Override
    public List<?> getTotalMeterDetailsCalender(String date) {

        String sql="SELECT\n" +
        		"	mm.modem_sl_no,\n" +
        		"	mm.mtrno,\n" +
        		"	mm.accno,\n" +
        		"	mm.customer_name,\n" +
        		"	mm.subdivision,\n" +
        		"	mm.division,\n" +
        		"	mm.district,\n" +
        		"	mm.circle,\n" +
        		"	sb.lastComm\n" +
        		"FROM\n" +
        		"	(\n" +
        		"		SELECT\n" +
        		"			mc.meter_number AS mdlNo,\n" +
        		"			MAX (mc.last_communication) AS lastComm\n" +
        		"		FROM\n" +
        		"			meter_data.modem_communication mc\n" +
        		"		WHERE\n" +
        		"			mc. DATE = to_date('"+date+"', 'DD-MM-YYYY')\n" +
        		"		GROUP BY\n" +
        		"			mc.meter_number\n" +
        		"	) sb,\n" +
        		"	meter_data.master_main mm\n" +
        		"WHERE\n" +
        		"	mm.mtrno = sb.mdlNo";

        System.out.println("-----------------total meter details calender------------------"+sql);
        List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
        return li;
    }

    @Override
    public List<?> getTotalInactiveMeterDetailsCalender(String date) {

        String sql="SELECT\n" +
        		"	A .modem_sl_no,\n" +
        		"	A .mtrno,\n" +
        		"	A .accno,\n" +
        		"	A .customer_name,\n" +
        		"	A .subdivision,\n" +
        		"	A .division,\n" +
        		"	A .district,\n" +
        		"	A .circle,\n" +
        		"	B.last_communication\n" +
        		"FROM\n" +
        		"	(\n" +
        		"		SELECT\n" +
        		"			*\n" +
        		"		FROM\n" +
        		"			meter_data.master_main\n" +
        		"		WHERE\n" +
        		"			mtrno NOT IN (\n" +
        		"				SELECT DISTINCT\n" +
        		"					meter_number\n" +
        		"				FROM\n" +
        		"					meter_data.modem_communication\n" +
        		"				WHERE\n" +
        		"					to_char(DATE, 'DD-MM-YYYY') = '"+date+"'\n" +
        		"			)\n" +
        		"	) A\n" +
        		"LEFT JOIN (\n" +
        		"	SELECT DISTINCT\n" +
        		"		meter_number,\n" +
        		"		MAX (last_communication) last_communication\n" +
        		"	FROM\n" +
        		"		meter_data.modem_communication\n" +
        		"	GROUP BY\n" +
        		"		meter_number\n" +
        		") B ON A .mtrno = B.meter_number;";

        System.out.println("-----------------total meter details inactive calender------------------"+sql);
        List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
        return li;
    }


	@Override
	public Object getAllMetersBasedOn(String zone, String circle, String division, String subdiv) {
		String queryTail="";
		/*if(!selectedLevel.isEmpty()){
//			queryTail=" AND mm."+selectedLevel+" = '"+selectedLevelName+"'";
			queryTail=" WHERE "+selectedLevel+" = '"+selectedLevelName+"'";

		}*/
		if("ALL".equalsIgnoreCase(zone)) {
			queryTail="";
		} else if("ALL".equalsIgnoreCase(circle)) {
			queryTail=" WHERE zone='"+zone+"' ";
		} else if("ALL".equalsIgnoreCase(division)) {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"'";
		} else if("ALL".equalsIgnoreCase(subdiv)) {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"'";
		} else {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdiv+"' ";
		}
		
		System.out.println("inside getTotalMeterDetails==>"+queryTail);
		
		
		String sql="SELECT mm.modem_sl_no, mm.mtrno, mm.fdrname, mm.substation, mm.subdivision, mm.division, mm.district, mm.circle, sb.lastComm,mm.customer_name,mm.customer_mobile,mm.customer_address,mm.accno,mm.consumerstatus,mm.tariffcode,mm.kworhp,mm.sanload,mm.contractdemand,mm.mrname,mm.kno FROM "
+" (SELECT modem_sl_no, mtrno, fdrname,substation,subdivision,division,district,circle,customer_name,customer_mobile,customer_address,accno,consumerstatus,tariffcode,kworhp,sanload,contractdemand,mrname,kno FROM "
+" meter_data.master_main "+queryTail+")mm LEFT JOIN( SELECT mc.meter_number AS mdlNo, MAX(mc.last_communication) AS lastComm"
+" FROM meter_data.modem_communication mc GROUP BY mc.meter_number )sb on mm.mtrno=sb.mdlNo";
		
/*		String sql="SELECT "
				+"mm.modem_sl_no, "
				+"mm.mtrno, "
				+"mm.fdrcode, "
				+"mm.substation, "
				+"mm.subdivision, "
				+"mm.division, "
				+"mm.district, "
				+"mm.circle, "
				+"sb.lastComm "
				+"FROM "
				+"( "
				+"SELECT "
				+"mc.imei AS mdlNo, "
				+"MAX(mc.last_communication) AS lastComm "
				+"FROM "
				+"meter_data.modem_communication mc "
				+"GROUP BY "
				+"mc.imei "
				+") sb,meter_data.master_main mm  "
				+"where mm.modem_sl_no=sb.mdlNo "+queryTail; */
		
		System.out.println("-----------------total meter details------------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return li;
	} 
	
	
	@Override
	public List<?> getAllMetersBasedOnZoneCircle(String zone, String circle,String towncode,String location) {
       String sql="";
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String month=sd.format(d1);
		List<?> li=null;
		
		if(zone.equalsIgnoreCase("All"))
		{
			zone="%";
		}
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		
		if(towncode.equalsIgnoreCase("All"))
		{
			towncode="%";
		}
		
		
		/*String sql="SELECT metrno,uname,accno,industrytype,division,circle,kno FROM meter_data.mm WHERE rdngmonth='"+month+"' \n" +
					"AND circle LIKE '"+circle+"' AND division LIKE '"+division+"'\n" +
					"AND subdiv LIKE '"+subdiv+"' ORDER BY metrno";*/
			
			/*
			 * String
			 * sql="SELECT mtrno,customer_name,accno,village,division,circle,kno FROM meter_data.master_main where\n"
			 * + " circle LIKE '"+circle+"' AND division LIKE '"+division+"'\n" +
			 * "AND subdivision LIKE '"+subdiv+"' ORDER BY mtrno";
			 */
			if(location.equalsIgnoreCase("BOUNDARY METER")) {
				 sql="\r\n" + 
				 		"SELECT m.mtrno,m.customer_name,d.dttpid,m.fdrcategory,m.division,m.circle,m.kno,m.mtrmake,m.zone,am.town_ipds FROM meter_data.master_main m\r\n" + 
				 		"LEFT JOIN meter_data.dtdetails d ON m.mtrno=d.meterno LEFT JOIN meter_data.amilocation am ON m.town_code=am.tp_towncode\r\n" + 
				 		"where\r\n" + 
				 		" m.zone like '"+zone+"' and m.circle LIKE '"+circle+"' AND \r\n" + 
				 		"m.town_code like '"+towncode+"' and m.fdrcategory like 'BOUNDARY METER' ORDER BY m.mtrno";
				System.out.println("BOUNDARY--"+sql);
			} 
			if(location.equalsIgnoreCase("FEEDER METER")) {
				 sql="\r\n" + 
				 		"SELECT m.mtrno,m.customer_name,d.dttpid,m.fdrcategory,m.division,m.circle,m.kno,m.mtrmake,m.zone,am.town_ipds FROM meter_data.master_main m\r\n" + 
				 		"LEFT JOIN meter_data.dtdetails d ON m.mtrno=d.meterno LEFT JOIN meter_data.amilocation am ON m.town_code=am.tp_towncode\r\n" + 
				 		"where\r\n" + 
				 		" m.zone like '"+zone+"' and m.circle LIKE '"+circle+"' AND \r\n" + 
				 		"m.town_code like '"+towncode+"' and m.fdrcategory like 'FEEDER METER' ORDER BY m.mtrno";
				 
					System.out.println("FEEDER sql---->"+sql);
					
			}
			if(location.equalsIgnoreCase("DT")) {
				 sql="\r\n" + 
				 		"SELECT m.mtrno,m.customer_name,d.dttpid,m.fdrcategory,m.division,m.circle,m.kno,m.mtrmake,m.zone,am.town_ipds FROM meter_data.master_main m\r\n" + 
				 		"LEFT JOIN meter_data.dtdetails d ON m.mtrno=d.meterno LEFT JOIN meter_data.amilocation am ON m.town_code=am.tp_towncode\r\n" + 
				 		"where\r\n" + 
				 		" m.zone like '"+zone+"' and m.circle LIKE '"+circle+"' AND \r\n" + 
				 		"m.town_code like '"+towncode+"' and m.fdrcategory like 'DT' ORDER BY m.mtrno";
					System.out.println("DT sql---->"+sql);

			}
			
			if(location.equalsIgnoreCase("ALL")) {
				 sql="\r\n" + 
				 		"SELECT m.mtrno,m.customer_name,d.dttpid,m.fdrcategory,m.division,m.circle,m.kno,m.mtrmake,m.zone,am.town_ipds FROM meter_data.master_main m\r\n" + 
				 		"LEFT JOIN meter_data.dtdetails d ON m.mtrno=d.meterno LEFT JOIN meter_data.amilocation am ON m.town_code=am.tp_towncode\r\n" + 
				 		"where\r\n" + 
				 		" m.zone like '"+zone+"' and m.circle LIKE '"+circle+"' AND \r\n" + 
				 		"m.town_code like '"+towncode+"' and m.fdrcategory like '%' ORDER BY m.mtrno";
					System.out.println("ALL sql---->"+sql);

			}
			
			
			try {
	
		li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	} 
	
	
	
	@Override
    public List<?> getTotalMetersDetailsCalender(String date) {

        String sql="SELECT mm.modem_sl_no,mm.mtrno,mm.accno,mm.customer_name,mm.subdivision,mm.division,mm.district,mm.circle,sb.lastComm FROM meter_data.master_main mm LEFT JOIN \r\n" + 
        		"(	SELECT mc.meter_number AS mdlNo,MAX (mc.last_communication) AS lastComm FROM\r\n" + 
        		"meter_data.modem_communication mc  GROUP BY mc.meter_number\r\n" + 
        		") sb on mm.mtrno = sb.mdlNo";
        List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
        return li;
    }
	
}
