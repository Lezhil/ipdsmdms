package com.bcits.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.AndroidService;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@SuppressWarnings("unchecked")
public class MobController {

	@Autowired
	AndroidService androidService;


	@RequestMapping(value = "/mobGetPowerAvilabilityForMap", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object mobGetPowerAvilabilityForMap(HttpServletRequest request) {

		JSONArray array = new JSONArray();
		String sql = "select M.zone, M.circle, M.division, M.subdivision, M.sdocode, M.mtrno, M.mtrmake, M.longitude, M.latitude, M.customer_name, M.customer_address, M.accno, M.kno,"
				+" (CASE WHEN event_code is null THEN 'INACTIVE' WHEN event_code ='101' THEN 'OFF' ELSE 'ON'END) as status,E.event_time   "
				+" from (select distinct on(mtrno) zone,circle,division,subdivision,sdocode,mtrno,mtrmake,longitude,latitude,customer_name,customer_address,accno,kno from meter_data.master_main where mtrno is not null ORDER BY mtrno)M"
				+" LEFT JOIN"
				+" (SELECT distinct on (meter_number)  meter_number, event_time,event_code FROM  meter_data.events where event_code in('101','102') and event_time > NOW() - INTERVAL '5 days' order by meter_number, event_time desc)E"
				+" ON M.mtrno=E.meter_number";

		System.out.println(sql);

		try {

			List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			for (int i = 0 ; i<list.size();i++) {
				Object[] columns=list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("zone", String.valueOf(columns[0]));
				obj.put("circle", String.valueOf(columns[1]));
				obj.put("division", String.valueOf(columns[2]));
				obj.put("subdivision", String.valueOf(columns[3]));
				obj.put("sdocode", String.valueOf(columns[4]));
				obj.put("mtrno", String.valueOf(columns[5]));
				obj.put("mtrmake", String.valueOf(columns[6]));
				obj.put("longitude", String.valueOf(columns[7]));
				obj.put("latitude", String.valueOf(columns[8]));
				obj.put("customer_name", String.valueOf(columns[9]));
				obj.put("customer_address", String.valueOf(columns[10]));
				obj.put("accno", String.valueOf(columns[11]));
				obj.put("kno", String.valueOf(columns[12]));
				String status=String.valueOf(columns[13]);
				String evtTime=String.valueOf(columns[14]);
				obj.put("status", status);
				obj.put("event_time", evtTime);

				obj.put("offDuration", "NA");//DEFAULT
				if(status.equals("OFF") && evtTime.length()>5){//!=null
					Date eventTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(evtTime); 
					Date now= new Date();
					long diff=now.getTime()-eventTime.getTime();
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000);// % 24;
					String diffInHourMin=diffHours+" Hours "+diffMinutes+" Minutes";
					System.out.print(diffInHourMin);
					obj.put("offDuration", diffInHourMin);
				}


				array.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array.toString();
	}


	@RequestMapping(value = "/mobMeshNetworkForMap", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object mobMeshNetworkForMap(HttpServletRequest request) {

		JSONArray array = new JSONArray();

	/*	String sql = "SELECT AA.meter_serial_number, AA.manufacturer_name,AA.firmware_version,BB.gateway_node_id, BB.node_id,BB.node_type,BB.latitude,BB.longitude,BB.parent_node_id,BB.update_time,BB.status,BB.parentLat,BB.parentLon"
				+" FROM "
				+" (select meter_serial_number,manufacturer_name,firmware_version,node_id from meter_data.name_plate where node_id is not null and meter_serial_number is not null )AA"
				+" RIGHT JOIN "
				+" (SELECT XX.gateway_node_id, XX.node_id, XX.node_type, XX.latitude, XX.longitude, XX.parent_node_id, XX.update_time,XX.status,YY.latitude as parentLat,YY.longitude as parentLon FROM "
				+" (SELECT gateway_node_id, node_id,node_type,latitude,longitude,parent_node_id,update_time,status from meter_data.search_nodes where to_char(update_time,'YYYY-MM-dd')=to_char(now(),'YYYY-MM-dd') ORDER BY gateway_node_id,node_type)XX"
				+" LEFT JOIN "
				+" (SELECT gateway_node_id, node_id,node_type,latitude,longitude,parent_node_id,update_time,status from meter_data.search_nodes where to_char(update_time,'YYYY-MM-dd')=to_char(now(),'YYYY-MM-dd') ORDER BY gateway_node_id,node_type)YY"
				+" ON XX.parent_node_id=YY.node_id)BB ON  "
				+" BB.node_id=AA.node_id order by BB.node_type desc";*/
		
		String sql = "SELECT XXX.meter_serial_number, XXX.manufacturer_name,XXX.firmware_version,XXX.gateway_node_id, XXX.node_id,XXX.node_type,XXX.latitude,XXX.longitude,XXX.parent_node_id,XXX.update_time,XXX.status,XXX.parentLat,XXX.parentLon ,YYY.circle, YYY.division, YYY.subdivision, YYY.sdocode, YYY.customer_name, YYY.customer_address, YYY.accno, YYY.kno, YYY.customer_mobile,XXX.parentNode_type, count(*) over ( partition by XXX.gateway_node_id ) as gateWayTotal   FROM  (SELECT AA.meter_serial_number, AA.manufacturer_name,AA.firmware_version,BB.gateway_node_id, BB.node_id,BB.node_type,BB.latitude,BB.longitude,BB.parent_node_id,BB.update_time,BB.status,BB.parentLat,BB.parentLon,BB.parentNode_type FROM  ( select meter_serial_number,manufacturer_name,firmware_version,node_id from meter_data.name_plate where node_id is not null and meter_serial_number is not null  )AA RIGHT JOIN  ( SELECT XX.gateway_node_id, XX.node_id, XX.node_type, XX.latitude, XX.longitude, XX.parent_node_id, XX.update_time,XX.status,YY.latitude as parentLat,YY.longitude as parentLon,YY.node_type as parentNode_type FROM  ( SELECT DISTINCT ON(node_id) gateway_node_id, node_id,node_type,latitude,longitude,parent_node_id,update_time,status from meter_data.search_nodes where  update_time>(now() - INTERVAL '3 days') ORDER BY node_id,update_time desc,node_type )XX LEFT JOIN  ( SELECT DISTINCT ON(node_id) gateway_node_id, node_id,node_type,latitude,longitude,parent_node_id,update_time,status from meter_data.search_nodes where  update_time>(now() - INTERVAL '3 days') ORDER BY node_id,update_time desc,node_type )YY ON XX.parent_node_id=YY.node_id )BB ON   BB.node_id=AA.node_id order by BB.node_type desc) XXX LEFT JOIN  meter_data.master_main YYY ON XXX.meter_serial_number =YYY.mtrno";

		System.out.println(sql);

		try {

			List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			for (int i = 0 ; i<list.size();i++) {
				Object[] columns=list.get(i);
				String meterNo=String.valueOf(columns[0]);
				String make=String.valueOf(columns[1]);
				String firmVersion=String.valueOf(columns[2]);
				String gatewayNodeId=String.valueOf(columns[3]);
				String nodeId=String.valueOf(columns[4]);
				String nodeType=String.valueOf(columns[5]);
				String latitude=String.valueOf(columns[6]);
				String longitude=String.valueOf(columns[7]);
				String parentNodeId=String.valueOf(columns[8]);
				String updateTime=String.valueOf(columns[9]);
				String status=String.valueOf(columns[10]);
				String parentLat=String.valueOf(columns[11]);
				String parentLon=String.valueOf(columns[12]);
				JSONObject obj = new JSONObject()
						.put("meterNo", meterNo)
						.put("make", make)
						.put("firmVersion", firmVersion)
						.put("gatewayNodeId", gatewayNodeId)
						.put("nodeId", nodeId)
						.put("nodeType", nodeType)
						.put("latitude", latitude)
						.put("longitude", longitude)
						.put("parentNodeId", parentNodeId)
						.put("updateTime", updateTime)
						.put("status", status) 
						.put("parentLat", parentLat) 
						.put("parentLon", parentLon) 
						.put("circle", String.valueOf(columns[13])) 
						.put("division", String.valueOf(columns[14])) 
						.put("subdivision", String.valueOf(columns[15])) 
						.put("sdocode", String.valueOf(columns[16])) 
						.put("customer_name", String.valueOf(columns[17])) 
						.put("customer_address", String.valueOf(columns[18])) 
						.put("accno", String.valueOf(columns[19])) 
						.put("kno", String.valueOf(columns[20])) 
						.put("customer_mobile", String.valueOf(columns[21])) 
						.put("parentNodeType", String.valueOf(columns[22]))
						.put("gateWayTotal", String.valueOf(columns[23]));;

				array.put( obj);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array.toString();
	}

	@RequestMapping(value = "/getInstallationProgressMob", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getInstallationProgressMob(HttpServletRequest request) {
		JSONObject obj = new JSONObject();

		String sql = "SELECT 'total' as heading, count(DISTINCT(kno)) as meters FROM meter_data.master_main"
				+" union all "
				+" SELECT 'installed' as heading, count(DISTINCT(kno)) as meters FROM meter_data.survey_output  "
				+" union all "
				+" select 'communicating'as heading, count(DISTINCT(kno)) as meters  from meter_data.survey_output where newmeterno in "
				+" (select distinct meter_number from meter_data.modem_communication where last_communication >(now() - INTERVAL '2 days'))";

		System.out.println(sql);

		try {
			List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			for (int i = 0 ; i<list.size();i++) {
				Object[] columns=list.get(i);
				String heading=String.valueOf(columns[0]);
				String count=String.valueOf(columns[1]);

				if(heading.equals("total")){
					obj.put("total", count);
				}else if(heading.equals("installed")){
					obj.put("installed", count);
				}else{
					obj.put("communicating", count);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(obj);
		return obj.toString();
	}

	@RequestMapping(value = "/getDayWiseInstallationProgressMob", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getDayWiseInstallationProgressMob(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		String sql = "SELECT to_date(survey_timings,'YYYY-MM-DD HH24-MI-SS') as dateInsalled, count(DISTINCT(newmeterno)) as meters FROM meter_data.survey_output  GROUP BY dateInsalled order by dateInsalled desc";

		System.out.println(sql);

		try {
			List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			for (int i = 0 ; i<list.size();i++) {
				Object[] columns=list.get(i);
				String dateInsalled=String.valueOf(columns[0]);
				String count=String.valueOf(columns[1]);
				JSONObject obj = new JSONObject();
				obj.put("dateInsalled",  new SimpleDateFormat("dd-MMM-yyyy").format((new SimpleDateFormat("yyyy-MM-dd")).parse(dateInsalled)) );
				obj.put("installedCount", count);
				array.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(array);
		return array.toString();
	}

	@RequestMapping(value = "/getMeterCommunicationStatusMobile", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getMeterCommunicationStatusMobile(HttpServletRequest request) {
		JSONArray array = new JSONArray();

		try {
			String qrycomm="SELECT \"count\"(*) total,\n" +
					"\"count\"(CASE WHEN c.meter_number is not null then 1 END) as active,\n" +
					"\"count\"(CASE WHEN c.meter_number is null then 1 END) as inactive,\n" +
					"\"count\"(CASE WHEN c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 THEN 1 END) as inc24h,\n" +
					"\"count\"(CASE WHEN c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 THEN 1 END) as inc5d,\n" +
					"\"count\"(CASE WHEN c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 THEN 1 END) as inc10d,\n" +
					"\"count\"(CASE WHEN c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 THEN 1 END) as inc20d,\n" +
					"\"count\"(CASE WHEN c.difhr>720  THEN 1 END) as inc30d \n" +
					"from meter_data.master_main m LEFT JOIN\n" +
					"(\n" +
					"SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number\n" +
					") c on m.mtrno=c.meter_number";
			System.out.println(qrycomm);
				List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(qrycomm).getResultList();
				for (int i = 0 ; i<list.size();i++) {
					Object[] columns=list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("total", String.valueOf(columns[0]));
					obj.put("active", String.valueOf(columns[1]));
					obj.put("inactive", String.valueOf(columns[2]));
					obj.put("inc24h", String.valueOf(columns[3]));
					obj.put("inc5d", String.valueOf(columns[4]));
					obj.put("inc10d", String.valueOf(columns[5]));
					obj.put("inc20d", String.valueOf(columns[6]));
					obj.put("inc30d", String.valueOf(columns[7]));
					array.put(obj);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(array);
		return array.toString();
	}
	
	@RequestMapping(value = "/getMeterTampersMobile", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getMeterTampersMobile(HttpServletRequest request) {
		JSONArray array = new JSONArray();

		try {
			String qrycomm="SELECT DD.meter_number, DD.event_code, DD.event_time,DD.event, BB.circle,BB.division,BB.subdivision,"
+" BB.mtrmake,BB.longitude,BB.latitude,BB.customer_name,BB.customer_mobile,BB.customer_address,BB.accno,BB.kno,BB.sdocode FROM"
+" (SELECT  AA.meter_number, AA.event_code, AA.event_time,CC.event FROM "
+" (SELECT distinct on(meter_number) meter_number,event_code,event_time FROM meter_data.events where event_time >= (now() - INTERVAL '2 days')"  
+" and event_code in(select to_char(event_code, 'FM99999') from meter_data.event_master where category='Tamper' and event_code not in (101,102))" 
+" order by  meter_number, event_time desc) AA LEFT JOIN meter_data.event_master CC ON AA.event_code=to_char(CC.event_code, 'FM99999'))DD"
+" INNER JOIN meter_data.master_main BB ON DD.meter_number=BB.mtrno";
			System.out.println(qrycomm);
				List<Object[]> list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(qrycomm).getResultList();
				for (int i = 0 ; i<list.size();i++) {
					Object[] columns=list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("meter_number", String.valueOf(columns[0]));
					obj.put("event_code", String.valueOf(columns[1]));
					obj.put("event_time", String.valueOf(columns[2]));
					obj.put("event_desc", String.valueOf(columns[3]));
					obj.put("circle", String.valueOf(columns[4]));
					obj.put("division", String.valueOf(columns[5]));
					obj.put("subdivision", String.valueOf(columns[6]));
					obj.put("mtrmake", String.valueOf(columns[7]));
					obj.put("longitude", String.valueOf(columns[8]));
					obj.put("latitude", String.valueOf(columns[9]));
					obj.put("customer_name", String.valueOf(columns[10]));
					obj.put("customer_mobile", String.valueOf(columns[11]));
					obj.put("customer_address", String.valueOf(columns[12]));
					obj.put("accno", String.valueOf(columns[13]));
					obj.put("kno", String.valueOf(columns[14]));
					obj.put("sdocode", String.valueOf(columns[15]));
					array.put(obj);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(array);
		return array.toString();
	}
}
