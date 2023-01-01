package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.IndssMassEntity;
import com.bcits.entity.PfcD2IntermediateEntity;
import com.bcits.entity.PfcD3ReportIntermediateEntity;
import com.bcits.entity.PfcD3ReportIntermediateEntity.pfcd3IntermediateKey;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;
import com.bcits.service.PfcOnlineD3PmtCompService;

@Repository
public class PfcOnlineD3PmtCompServiceImpl extends GenericServiceImpl<PfcD3ReportIntermediateEntity> implements PfcOnlineD3PmtCompService {
	@Override
	public String insertPfcOnline(String data,String servicename) {
		System.out.println("inside pfcOnlinePmtComp");
		try {
			//data="[{\"towncode\":\"318\",\"comppending\":\"65\",\"closedwithinserc\":\"90\",\"compclosed\":\"110\",\"closedbeyondserc\":\"20\",\"monthyear\":\"062019\",\"rec_id\":\"1\",\"compopeningcnt\":\"100\",\"compreceived\":\"75\",\"totcomplains\":\"175\",\"percent_withinserc\":\".5\",\"timestamp\":\"2019-06-30 14:22:35\"},{\"towncode\":\"319\",\"comppending\":\"5\",\"closedwithinserc\":\"50\",\"compclosed\":\"70\",\"closedbeyondserc\":\"20\",\"monthyear\":\"062019\",\"rec_id\":\"2\",\"compopeningcnt\":\"50\",\"compreceived\":\"25\",\"totcomplains\":\"75\",\"percent_withinserc\":\".66\",\"timestamp\":\"2019-06-30 14:27:50\"},{\"towncode\":\"320\",\"comppending\":\"100\",\"closedwithinserc\":\"300\",\"compclosed\":\"400\",\"closedbeyondserc\":\"100\",\"monthyear\":\"062019\",\"rec_id\":\"3\",\"compopeningcnt\":\"200\",\"compreceived\":\"300\",\"totcomplains\":\"500\",\"percent_withinserc\":\".6\",\"timestamp\":\"2019-06-30 14:29:09\"},{\"towncode\":\"321\",\"comppending\":\"0\",\"closedwithinserc\":\"65\",\"compclosed\":\"70\",\"closedbeyondserc\":\"5\",\"monthyear\":\"062019\",\"rec_id\":\"4\",\"compopeningcnt\":\"10\",\"compreceived\":\"60\",\"totcomplains\":\"70\",\"percent_withinserc\":\".92\",\"timestamp\":\"2019-06-30 14:32:29\"},{\"towncode\":\"322\",\"comppending\":\"50\",\"closedwithinserc\":\"150\",\"compclosed\":\"150\",\"closedbeyondserc\":\"0\",\"monthyear\":\"062019\",\"rec_id\":\"5\",\"compopeningcnt\":\"150\",\"compreceived\":\"50\",\"totcomplains\":\"200\",\"percent_withinserc\":\".75\",\"timestamp\":\"2019-06-30 14:34:25\"}]";
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->" + recs.length());

			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);

				//String rec_id = obj.optString("rec_id");
				String month_year = obj.optString("monthyear");
				String town_code = obj.optString("towncode");
				String comp_opening_cnt = obj.optString("compopeningcnt");
				String comp_received = obj.optString("compreceived");
				String tot_complains = obj.optString("totcomplains");
				String compclosed = obj.optString("compclosed");
				String comp_pending = obj.optString("comppending");
				String closed_with_inserc = obj.optString("closedwithinserc");
				String closed_beyond_serc = obj.optString("closedbeyondserc");
				String percent_with_inserc = obj.optString("percent_withinserc");
				String time_stamp = obj.optString("timestamp");

				
				PfcD3ReportIntermediateEntity onlinePmtComp = new PfcD3ReportIntermediateEntity();
				
				onlinePmtComp.setMyKey(new pfcd3IntermediateKey(month_year,town_code));
				
				/*if (!month_year.equalsIgnoreCase("null")) {
					onlinePmtComp.setTown_code(town_code);
				}
				if (!town_code.equalsIgnoreCase("null")) {
					onlinePmtComp.setMonth_year(month_year);
				}*/
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
					if(update(onlinePmtComp)instanceof PfcD3ReportIntermediateEntity);
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
