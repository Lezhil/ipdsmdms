package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.IndFeederMasEntity;
import com.bcits.entity.IndssMassEntity;
import com.bcits.service.IndssMassService;

@Repository
public class IndssMassServiceImpl extends GenericServiceImpl<IndssMassEntity> implements IndssMassService{

	@Override
	public String insertIndssMass(String data) {
		System.out.println("inside insertIndssMass");
		try {
				//data="[{\"fdrid\":null,\"input_energy\":null,\"powerfailfreq\":null,\"govt_energy_billed\":\"2277.9\",\"others_amount_billed\":\"0\",\"ht_commercial_amount_billed\":null,\"lt_industrial_amount_billed\":\"21481\",\"govt_amount_collected\":\"16265\",\"agri_consumer_count\":\"9\",\"lt_domestic_consumer_count\":\"77\",\"ht_industrial_consumer_count\":null,\"lt_industrial_consumer_count\":\"2\",\"agri_amount_collected\":\"0\",\"ht_industrial_amount_billed\":null,\"minimum_voltage\":\"22\",\"ht_commercial_consumer_count\":null,\"lt_commercial_consumer_count\":\"3\",\"lt_domestic_energy_billed\":\"9951\",\"export_energy\":null,\"lt_commercial_energy_billed\":\"940\",\"towncode\":\"074\",\"open_access_units\":null,\"max_current\":null,\"ht_commercial_energy_billed\":null,\"lt_commercial_amount_billed\":\"9394\",\"lt_domestic_amount_billed\":\"8310\",\"others_consumer_count\":\"0\",\"time_stamp\":\"2019-06-10 12:52:16\",\"govt_consumer_count\":\"4\",\"ht_industrial_energy_billed\":null,\"study_month\":\"4\",\"agri_energy_billed\":\"21855.55\",\"others_energy_billed\":\"0\",\"tpfdrid\":\"431601\",\"powerfailduration\":null,\"ht_industrial_amount_collected\":null,\"lt_commercial_amount_collected\":\"9394\",\"fdrtype\":\"INDUSTRIAL\",\"govt_amount_billed\":\"16265\",\"agri_amount_billed\":\"0\",\"study_year\":\"2019\",\"officeid\":null,\"ht_commercial_amount_collected\":null,\"lt_domestic_amount_collected\":\"8310\",\"lt_industrial_energy_billed\":\"3650\",\"lt_industrial_amount_collected\":\"21481\",\"others_amount_collected\":\"0\"}]]";
				//data="[{\"entby\":\"SSADMIN\",\"peaksofar_dt\":null,\"hvkv\":\"33\",\"enton\":\"2019-04-01 11:12:34\",\"peak_sofar\":\"0\",\"occode\":\"462\",\"villcode_no\":null,\"sscode\":\"5260\",\"vcode\":null,\"hvfss1fedcode\":null,\"hvfss2fedcode\":null,\"hvfss3fedcode\":null,\"hvfss4fedcode\":null,\"sstype\":\"R\",\"ccodef1\":null,\"hvfss4\":null,\"nolvfed\":\"1\",\"hvfss5\":null,\"hvfss2\":null,\"hvfss3\":null,\"ccodef4\":null,\"ccodef3\":null,\"hvfss1\":null,\"ccodef2\":null,\"pincode\":\"0\",\"rcode\":\"5\",\"blockcode_no\":null,\"cptag\":null,\"ssadd3\":null,\"ssname\":\"Mallankinar 33/11KV \",\"ssadd2\":null,\"ssph\":\"0\",\"oldsscode\":null,\"ssadd1\":null,\"sstag\":\"R\",\"opnccode\":\"462\",\"ccode\":\"52\",\"ssip\":\"192.168.1.250\",\"fromdt\":null,\"lvkv2\":null,\"lvkv1\":\"11\",\"lvkv4\":null,\"lvkv3\":null,\"todt\":null}]";
				
				JSONArray recs = new JSONArray(data.toString());
				System.out.println("response length--->"+recs.length());
				
				for (int i = 0; i < recs.length(); i++) {
					JSONObject obj = recs.getJSONObject(i);
					
					
					
					String	entby=obj.optString("entby");
					String	peaksofar_dt=obj.optString("peaksofar_dt");
					String	hvkv=obj.optString("hvkv");
					String	enton=obj.optString("enton");
					String	peak_sofar=obj.optString("peak_sofar");
					String	occode=obj.optString("occode");
					String	villcode_no=obj.optString("villcode_no");
					String	sscode=obj.optString("sscode");
					String	vcode=obj.optString("vcode");
					String	hvfss1fedcode=obj.optString("hvfss1fedcode");
					String	hvfss2fedcode=obj.optString("hvfss2fedcode");
					String	hvfss3fedcode=obj.optString("hvfss3fedcode");
					String	hvfss4fedcode=obj.optString("hvfss4fedcode");
					String	sstype=obj.optString("sstype");
					String	ccodef1=obj.optString("ccodef1");
					String	hvfss4=obj.optString("hvfss4");
					String	nolvfed=obj.optString("nolvfed");
					String	hvfss5=obj.optString("hvfss5");
					String	hvfss2=obj.optString("hvfss2");
					String	hvfss3=obj.optString("hvfss3");
					String	ccodef4=obj.optString("ccodef4");
					String	ccodef3=obj.optString("ccodef3");
					String	hvfss1=obj.optString("hvfss1");
					String	ccodef2=obj.optString("ccodef2");
					String	pincode=obj.optString("pincode");
					String	rcode=obj.optString("rcode");
					String	blockcode_no=obj.optString("blockcode_no");
					String	cptag=obj.optString("cptag");
					String	ssadd3=obj.optString("ssadd3");
					String	ssname=obj.optString("ssname");
					String	ssadd2=obj.optString("ssadd2");
					String	ssph=obj.optString("ssph");
					String	oldsscode=obj.optString("oldsscode");
					String	ssadd1=obj.optString("ssadd1");
					String	sstag=obj.optString("sstag");
					String	opnccode=obj.optString("opnccode");
					String	ccode=obj.optString("ccode");
					String	ssip=obj.optString("ssip");
					String	fromdt=obj.optString("fromdt");
					String	lvkv2=obj.optString("lvkv2");
					String	lvkv1=obj.optString("lvkv1");
					String	lvkv4=obj.optString("lvkv4");
					String	lvkv3=obj.optString("lvkv3");
					String	todt=obj.optString("todt");

					IndssMassEntity indChange=new IndssMassEntity();
					
					if(!entby.equalsIgnoreCase("null")){
						indChange.setEntby(entby);
						}
					if(!peaksofar_dt.equalsIgnoreCase("null")){
						indChange.setPeak_sofar(Integer.parseInt(peak_sofar));
						}				
					if(!hvkv.equalsIgnoreCase("null")){
						indChange.setHvkv(Integer.parseInt(hvkv));
					}
					if(!enton.equalsIgnoreCase("null")){
						indChange.setEnton(getTimeStamp(enton));
					}				
					if(!peak_sofar.equalsIgnoreCase("null")){
						indChange.setPeak_sofar(Integer.parseInt(peak_sofar));
					}
					if(!occode.equalsIgnoreCase("null")){
						indChange.setOccode(Integer.parseInt(occode));
					}				
					if(!villcode_no.equalsIgnoreCase("null")){
						indChange.setVillcode_no(Integer.parseInt(villcode_no));
					}
					if(!sscode.equalsIgnoreCase("null")){
						indChange.setSscode(Integer.parseInt(sscode));
					}
					if(!vcode.equalsIgnoreCase("null")){
						indChange.setVcode(Integer.parseInt(vcode));
					}
					if(!hvfss1fedcode.equalsIgnoreCase("null")){
						indChange.setHvfss1fedcode(Integer.parseInt(hvfss1fedcode));
					}		
					if(!hvfss2fedcode.equalsIgnoreCase("null")){
						indChange.setHvfss2fedcode(Integer.parseInt(hvfss2fedcode));
						}
					if(!hvfss3fedcode.equalsIgnoreCase("null")){
						indChange.setHvfss3fedcode(Integer.parseInt(hvfss3fedcode));
						}				
					if(!hvfss4fedcode.equalsIgnoreCase("null")){
						indChange.setHvfss4fedcode(Integer.parseInt(hvfss4fedcode));
					}
					if(!sstype.equalsIgnoreCase("null")){
						indChange.setSstype(sstype);
					}				
					if(!ccodef1.equalsIgnoreCase("null")){
						indChange.setCcodef1(Integer.parseInt(ccodef1));
					}
					if(!hvfss4.equalsIgnoreCase("null")){
						indChange.setHvfss4(Integer.parseInt(hvfss4));
					}				
					if(!nolvfed.equalsIgnoreCase("null")){
						indChange.setNolvfed(Integer.parseInt(nolvfed));
					}
					if(!hvfss5.equalsIgnoreCase("null")){
						indChange.setHvfss5(Integer.parseInt(hvfss5));
						}
					if(!hvfss2.equalsIgnoreCase("null")){
						indChange.setHvfss2(Integer.parseInt(hvfss2));
						}				
					if(!hvfss3.equalsIgnoreCase("null")){
						indChange.setHvfss3(Integer.parseInt(hvfss3));
					}
					if(!ccodef4.equalsIgnoreCase("null")){
						indChange.setCcodef4(Integer.parseInt(ccodef4));
					}				
					if(!ccodef3.equalsIgnoreCase("null")){
						indChange.setCcodef3(Integer.parseInt(ccodef3));
					}
					if(!hvfss1.equalsIgnoreCase("null")){
						indChange.setHvfss1(Integer.parseInt(hvfss1));
					}				
					if(!ccodef2.equalsIgnoreCase("null")){
						indChange.setCcodef2(Integer.parseInt(ccodef2));
					}
					if(!pincode.equalsIgnoreCase("null")){
						indChange.setPincode(Integer.parseInt(pincode));
					}
					if(!rcode.equalsIgnoreCase("null")){
						indChange.setRcode(Integer.parseInt(rcode));
						}
					if(!blockcode_no.equalsIgnoreCase("null")){
						indChange.setBlockcode_no(Integer.parseInt(blockcode_no));
						}				
					if(!cptag.equalsIgnoreCase("null")){
						indChange.setCptag(cptag);
					}
					if(!ssadd3.equalsIgnoreCase("null")){
						indChange.setSsadd3(ssadd3);
					}				
					if(!ssname.equalsIgnoreCase("null")){
						indChange.setSsname(ssname);
					}
					if(!ssadd2.equalsIgnoreCase("null")){
						indChange.setSsadd2(ssadd2);
					}				
					if(!ssph.equalsIgnoreCase("null")){
						indChange.setSsph(ssph);
					}
					if(!oldsscode.equalsIgnoreCase("null")){
						indChange.setOldsscode(Integer.parseInt(oldsscode));
					}
					if(!ssadd1.equalsIgnoreCase("null")){
						indChange.setSsadd1(ssadd1);
					}
					if(!sstag.equalsIgnoreCase("null")){
						indChange.setSstag(sstag);
					}		
					if(!opnccode.equalsIgnoreCase("null")){
						indChange.setEntby(opnccode);
						}
					if(!ccode.equalsIgnoreCase("null")){
						indChange.setCcode(Integer.parseInt(ccode));
						}				
					if(!ssip.equalsIgnoreCase("null")){
						indChange.setSsip(ssip);
					}
					if(!fromdt.equalsIgnoreCase("null")){
						indChange.setFromdt(getTimeStamp(fromdt));
					}				
					if(!lvkv2.equalsIgnoreCase("null")){
						indChange.setLvkv2(Integer.parseInt(lvkv2));
					}
					if(!lvkv1.equalsIgnoreCase("null")){
						indChange.setLvkv1(Integer.parseInt(lvkv1));
					}				
					if(!lvkv4.equalsIgnoreCase("null")){
						indChange.setLvkv4(Integer.parseInt(lvkv4));
					}
					if(!lvkv3.equalsIgnoreCase("null")){
						indChange.setLvkv3(Integer.parseInt(lvkv3));
					}				
					if(!todt.equalsIgnoreCase("null")){
						indChange.setTodt(getTimeStamp(todt));
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
