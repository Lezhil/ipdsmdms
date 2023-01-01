package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.IndstrChangeEntity;
import com.bcits.entity.IndstrDeleteEntity;
import com.bcits.entity.NppFeederReportIntermediate;
import com.bcits.service.IndstrDeleteService;

@Repository
public class IndstrDeleteServiceImpl extends GenericServiceImpl<IndstrDeleteEntity> implements IndstrDeleteService{

	@Override
	public String insertIndstrChangeServiceData(String data) {
		System.out.println("inside insert delete");
		try {

			//data="[{\"fdrid\":null,\"input_energy\":null,\"powerfailfreq\":null,\"govt_energy_billed\":\"2277.9\",\"others_amount_billed\":\"0\",\"ht_commercial_amount_billed\":null,\"lt_industrial_amount_billed\":\"21481\",\"govt_amount_collected\":\"16265\",\"agri_consumer_count\":\"9\",\"lt_domestic_consumer_count\":\"77\",\"ht_industrial_consumer_count\":null,\"lt_industrial_consumer_count\":\"2\",\"agri_amount_collected\":\"0\",\"ht_industrial_amount_billed\":null,\"minimum_voltage\":\"22\",\"ht_commercial_consumer_count\":null,\"lt_commercial_consumer_count\":\"3\",\"lt_domestic_energy_billed\":\"9951\",\"export_energy\":null,\"lt_commercial_energy_billed\":\"940\",\"towncode\":\"074\",\"open_access_units\":null,\"max_current\":null,\"ht_commercial_energy_billed\":null,\"lt_commercial_amount_billed\":\"9394\",\"lt_domestic_amount_billed\":\"8310\",\"others_consumer_count\":\"0\",\"time_stamp\":\"2019-06-10 12:52:16\",\"govt_consumer_count\":\"4\",\"ht_industrial_energy_billed\":null,\"study_month\":\"4\",\"agri_energy_billed\":\"21855.55\",\"others_energy_billed\":\"0\",\"tpfdrid\":\"431601\",\"powerfailduration\":null,\"ht_industrial_amount_collected\":null,\"lt_commercial_amount_collected\":\"9394\",\"fdrtype\":\"INDUSTRIAL\",\"govt_amount_billed\":\"16265\",\"agri_amount_billed\":\"0\",\"study_year\":\"2019\",\"officeid\":null,\"ht_commercial_amount_collected\":null,\"lt_domestic_amount_collected\":\"8310\",\"lt_industrial_energy_billed\":\"3650\",\"lt_industrial_amount_collected\":\"21481\",\"others_amount_collected\":\"0\"}]]";
			//data="[{\"entby\":\"DEV434\",\"reason\":\"Load Transfer to new feeder\",\"flag\":\"Y\",\"ssfdrstrcode_old\":\"152608105\",\"ip_id\":\"172.20.65.13\",\"enton\":\"2019-06-21 14:09:16\",\"updt_dt\":\"2019-06-22 04:00:01\",\"type\":\"DTR\",\"change_st\":\"2019-01-03 00:00:00\",\"ssfdrstrcode_new\":\"153601054\"}]";
			//data="[{\"entby\":\"SS5306\",\"del_dt\":\"2019-04-01 16:51:49\",\"flag\":\"Y\",\"ip_id\":\"172.64.159.105\",\"enton\":\"2019-04-01 16:51:49\",\"delete_dt\":\"2019-04-02 04:00:00\",\"ssfdrstrcode\":\"530608074\",\"type\":\"DTR\"}]";
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->"+recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				
				
				
				String entby =	obj.optString("entby");
				String del_dt =	obj.optString("del_dt");
				String flag =	obj.optString("flag");
				String ip_id =	obj.optString("ip_id");
				String delete_dt =	obj.optString("delete_dt");
				String ssfdrstrcode =	obj.optString("ssfdrstrcode");
				String type =	obj.optString("type");
				String enton =	obj.optString("enton");

				IndstrDeleteEntity indChange=new IndstrDeleteEntity();
				
				if(!entby.equalsIgnoreCase("null")){
					indChange.setEntby(entby);
					}
								
				if(!flag.equalsIgnoreCase("null")){
					indChange.setFlag(flag);
				}
							
				if(!ssfdrstrcode.equalsIgnoreCase("null")){
					indChange.setSsfdrstrcode(Integer.parseInt(ssfdrstrcode));
				}
				if(!ip_id.equalsIgnoreCase("null")){
					indChange.setIp_id(ip_id);
				}				
				if(!type.equalsIgnoreCase("null")){
					indChange.setType(type);
				}
				
				if(!delete_dt.equalsIgnoreCase("null")){
					indChange.setDelete_dt(getTimeStamp(delete_dt));
				}
				if(!del_dt.equalsIgnoreCase("null")){
					indChange.setDel_dt(getTimeStamp(del_dt));
				}
							
				if(!enton.equalsIgnoreCase("null")){
					indChange.setEnton(getTimeStamp(enton));
				}
				
				
				
				
				save(indChange);

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
