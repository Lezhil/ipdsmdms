package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.PfcD2IntermediateEntity;
import com.bcits.entity.PfcD2IntermediateEntity.PfcD2IntermediateKey;
import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.service.PfcD2InterrmediateService;

@Repository
public class PfcD2InterrmediateServiceImpl extends GenericServiceImpl<PfcD2IntermediateEntity> implements PfcD2InterrmediateService {

	@Override
	public String insertD2Data(String data) {
		try {

		
		//data="[{\"ncreqpending\":\"40\",\"ncreqclosed\":\"1\",\"town\":\"067\",\"closedbeyondserc\":\"0\",\"releasedbyitsystm\":\"6\",\"monthyear\":\"042019\",\"rec_id\":null,\"percent_withinserc\":\"100\",\"timeStamp\":\"2019-06-10 12:53:36\",\"closedwithinserc\":\"6\",\"ncreqreceived\":\"11\",\"totncreq\":\"30\",\"ncreqopeningcnt\":\"190\"}]";
		
		JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->"+recs.length());
			//List<String> townlist=new ArrayList<>();
			//String qry="select DISTINCT town from meter_data.pfc_d2_rpt_intermediate where month_year='042019'";
			//townlist=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				
				
				String	monthyear=obj.optString("monthyear");
				String	town=obj.optString("town");
				/*if(townlist.contains(town)){
					System.out.println("before..check town");
					continue;
					
				}*/
				
				String	nc_req_opening_cnt=obj.optString("ncreqopeningcnt");
				String	nc_req_received=obj.optString("ncreqreceived");
				String	tot_nc_req=obj.optString("totncreq");
				String	nc_req_closed=obj.optString("ncreqclosed");
				String	nc_req_pending=obj.optString("ncreqpending");
				String	closed_with_in_serc=obj.optString("closedwithinserc");
				String	closed_beyond_serc=obj.optString("closedbeyondserc");
				String	percent_within_serc=obj.optString("percent_withinserc");
				String	released_by_it_system=obj.optString("releasedbyitsystm");
				String	timestamp=obj.optString("timeStamp");

				PfcD2IntermediateEntity d2=new PfcD2IntermediateEntity();
				d2.setMyKey(new PfcD2IntermediateKey(monthyear, town));
				if(!nc_req_opening_cnt.equalsIgnoreCase("null")) {
				d2.setNc_req_opening_cnt(Integer.parseInt(nc_req_opening_cnt));
				}
				if(!nc_req_received.equalsIgnoreCase("null")) {
					
				d2.setNc_req_received(Integer.parseInt(nc_req_received));
				}
				if(!tot_nc_req.equalsIgnoreCase("null")) {
				d2.setTot_nc_req(Integer.parseInt(tot_nc_req));
				}
				if(!nc_req_closed.equalsIgnoreCase("null")) {
				d2.setNc_req_closed(Integer.parseInt(nc_req_closed));
				}
				if(!nc_req_pending.equalsIgnoreCase("null")) {
					d2.setNc_req_pending(Integer.parseInt(nc_req_pending));
				}
				if(!closed_with_in_serc.equalsIgnoreCase("null")) {
					d2.setClosed_with_in_serc(Integer.parseInt(closed_with_in_serc));
				}
				if(!closed_beyond_serc.equalsIgnoreCase("null")) {
					d2.setClosed_beyond_serc(Integer.parseInt(closed_beyond_serc));
				}
				if(!released_by_it_system.equalsIgnoreCase("null")) {
					d2.setReleased_by_it_system(Integer.parseInt(released_by_it_system));
					
				}
				if(!percent_within_serc.equalsIgnoreCase("null")) {
					d2.setPercent_within_serc(Double.parseDouble(percent_within_serc));
					
				}
				if(!timestamp.equalsIgnoreCase("null")) {
				d2.setTimestamp(getTimeStamp(timestamp));
				}
				d2.setReadtime(new Timestamp(System.currentTimeMillis()));
				
				try {
					//save(d2);
					if (update(d2) instanceof PfcD2IntermediateEntity) {
						 
					}
					System.out.println("save d2 completed");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	static Timestamp getTimeStamp(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return new Timestamp(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
