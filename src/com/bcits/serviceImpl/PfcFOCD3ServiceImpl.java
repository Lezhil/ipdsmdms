package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.PfcFOCD3IntermediateEntity;
import com.bcits.entity.PfcFOCD3IntermediateEntity.pfcfocd3IntermediateKey;
import com.bcits.service.PfcFOCD3Service;

@Repository
public class PfcFOCD3ServiceImpl extends GenericServiceImpl<PfcFOCD3IntermediateEntity> implements PfcFOCD3Service{

	@Override
	public String insertFocD3Report(String data, String servicename) {
		System.out.println("inside pfcOnlinePmtComp");
		try {
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->" + recs.length());

			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);

				//String rec_id = obj.optString("rec_id");
				String month_year = obj.optString("monthyear");
				String town_code = obj.optString("towncode");
				String comp_opening_cnt = obj.optString("compopeningcnt");
				String comp_received = obj.optString("compreceived");
				String tot_complains = obj.optString("totcomplaints");
				String compclosed = obj.optString("compclosed");
				String comp_pending = obj.optString("comppending");
				String closed_with_inserc = obj.optString("closedwithinserc");
				String closed_beyond_serc = obj.optString("closedbeyondserc");
				String percent_with_inserc = obj.optString("percent_withinserc");
				String time_stamp = obj.optString("timestamp");

				
				PfcFOCD3IntermediateEntity onlinePmtComp = new PfcFOCD3IntermediateEntity();
				
				onlinePmtComp.setMyKey(new pfcfocd3IntermediateKey(month_year,town_code));
					if (!comp_opening_cnt.equalsIgnoreCase("null")) {
					onlinePmtComp.setComp_opening_cnt(Integer.parseInt(comp_opening_cnt));
				}
				if (!comp_received.equalsIgnoreCase("null")) {
					onlinePmtComp.setComp_received(Integer.parseInt(comp_received));
				}
				if (!tot_complains.equalsIgnoreCase("null")) {
					onlinePmtComp.setTot_complains(Integer.parseInt(tot_complains));
				}
				if (!compclosed.equalsIgnoreCase("null")) {
					onlinePmtComp.setCompclosed(Integer.parseInt(compclosed));
				}
				if (!comp_pending.equalsIgnoreCase("null")) {
					onlinePmtComp.setComp_pending(Integer.parseInt(comp_pending));
				}
				if (!closed_with_inserc.equalsIgnoreCase("null")) {
					onlinePmtComp.setClosed_with_inserc(Integer.parseInt(closed_with_inserc));
				}
				if (!closed_beyond_serc.equalsIgnoreCase("null")) {
					onlinePmtComp.setClosed_beyond_serc(Integer.parseInt(closed_beyond_serc));
				}
				if (!percent_with_inserc.equalsIgnoreCase("null")) {
					onlinePmtComp.setPercent_within_serc(Double.parseDouble(percent_with_inserc));
				}
				if (!time_stamp.equalsIgnoreCase("null")) {
					onlinePmtComp.setTime_stamp(getTimeStamp(time_stamp));
				}
				onlinePmtComp.setFlag(servicename);
				onlinePmtComp.setReadtime(new Timestamp(System.currentTimeMillis()));

				//save(onlinePmtComp);
				try {
					if(update(onlinePmtComp)instanceof PfcFOCD3IntermediateEntity);
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
