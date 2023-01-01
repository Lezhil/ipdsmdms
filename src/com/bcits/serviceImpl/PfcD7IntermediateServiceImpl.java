package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.PfcD7IntermediateEntity;
import com.bcits.entity.PfcD7IntermediateEntity.pfcD7IntermediateKey;
import com.bcits.service.PfcD7IntermediateService;

@Repository
public class PfcD7IntermediateServiceImpl extends GenericServiceImpl<PfcD7IntermediateEntity> implements PfcD7IntermediateService {

	@Override
	public String insertD7Data(String data) {
		System.out.println("inside-- d7");
		//{"town":"065","time_stamp":"2019-06-10 12:41:22","consumercnt":"1120","onlinepayment":"360","monthyear":"042019","totamtcollected":"1116581","rec_id":null,"onlineamt":"268625"}
		try {
			//data="[{\"town\":\"067\",\"time_stamp\":\"2019-06-10 12:41:22\",\"consumercnt\":\"1125\",\"onlinepayment\":\"23.22\",\"monthyear\":\"052019\",\"totamtcollected\":\"1116581\",\"rec_id\":null,\"onlineamt\":\"332.22\"}]";
			
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->"+recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				
				String town=obj.optString("town");
				String time_stamp=obj.optString("time_stamp");
				String onlinepayment=obj.optString("onlinepayment");
				String monthyear=obj.optString("monthyear");
				String totamtcollected=obj.optString("totamtcollected");
				String rec_id=obj.optString("rec_id");
				String onlineamt=obj.optString("onlineamt");
				String consumercnt=obj.optString("consumercnt");
				
				
				PfcD7IntermediateEntity d7=new PfcD7IntermediateEntity();
				
				//d7.setMonthYear(monthyear);
				//d7.setTown(town);
				
				d7.setMyKey(new pfcD7IntermediateKey(monthyear,town));
				if(!consumercnt.equalsIgnoreCase("null")) {
				d7.setConsumerCnt(Double.parseDouble(consumercnt));
				}
				if(!onlinepayment.equalsIgnoreCase("null")) {
				d7.setOnlinePayment(Double.parseDouble(onlinepayment));
				}
				if(!totamtcollected.equalsIgnoreCase("null")) {
				d7.setTotalAmtCollected(Double.parseDouble(totamtcollected));
				}
				if(!onlineamt.equalsIgnoreCase("null")) {
				d7.setOnlineAmt(Double.parseDouble(onlineamt));
				}
				if(!time_stamp.equalsIgnoreCase("null")) {
				d7.setTimestamp(getTimeStamp(time_stamp));
				}
				d7.setReadtime(new Timestamp(System.currentTimeMillis()));
				
				try {
					
					if (update(d7) instanceof PfcD7IntermediateEntity) {
					}
					System.out.println("save d7 completed");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
