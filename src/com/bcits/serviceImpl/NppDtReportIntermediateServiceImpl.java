package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.NppDtReportIntermediate;
import com.bcits.entity.NppDtReportIntermediate.KeyNppDtIntermediate;
import com.bcits.entity.NppFeederReportIntermediate;
import com.bcits.entity.NppFeederReportIntermediate.KeyNppIntermediate;
import com.bcits.service.NppDtReportIntermediateService;


@Repository
public class NppDtReportIntermediateServiceImpl extends GenericServiceImpl<NppDtReportIntermediate> implements NppDtReportIntermediateService{

	@Override
	public String insertNppDTData(String data, String service) {
		System.out.println("inside Npp DT Data");
		try {

			JSONArray recs = new JSONArray(data.toString());
			
			//System.out.println("response length--->"+recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				System.out.println("i--"+i);
				int month=0,year=0;
				String tpdtid1="";
				String fdrid=obj.optString("fdrid");
				String input_energy=obj.optString("input_energy");
				String powerfailfreq=obj.optString("powerfailfreq");
				String govt_energy_billed=obj.optString("govt_energy_billed");
				String others_amount_billed=obj.optString("others_amount_billed");
				String ht_commercial_amount_billed=obj.optString("ht_commercial_amount_billed");
				String lt_industrial_amount_billed=obj.optString("lt_industrial_amount_billed");
				String govt_amount_collected=obj.optString("govt_amount_collected");
				String agri_consumer_count=obj.optString("agri_consumer_count");
				String lt_domestic_consumer_count=obj.optString("lt_domestic_consumer_count");
				String ht_industrial_consumer_count=obj.optString("ht_industrial_consumer_count");
				String lt_industrial_consumer_count=obj.optString("lt_industrial_consumer_count");
				String agri_amount_collected=obj.optString("agri_amount_collected");
				String ht_industrial_amount_billed=obj.optString("ht_industrial_amount_billed");
				String minimum_voltage=obj.optString("minimum_voltage");
				String ht_commercial_consumer_count=obj.optString("ht_commercial_consumer_count");
				String lt_commercial_consumer_count=obj.optString("lt_commercial_consumer_count");
				String lt_domestic_energy_billed=obj.optString("lt_domestic_energy_billed");
				String export_energy=obj.optString("export_energy");
				String lt_commercial_energy_billed=obj.optString("lt_commercial_energy_billed");
				String towncode=obj.optString("towncode");
				String open_access_units=obj.optString("open_access_units");
				String max_current=obj.optString("max_current");
				String ht_commercial_energy_billed=obj.optString("ht_commercial_energy_billed");
				String lt_commercial_amount_billed=obj.optString("lt_commercial_amount_billed");
				String lt_domestic_amount_billed=obj.optString("lt_domestic_amount_billed");
				String others_consumer_count=obj.optString("others_consumer_count");
				String time_stamp=obj.optString("time_stamp");
				String govt_consumer_count=obj.optString("govt_consumer_count");
				String ht_industrial_energy_billed=obj.optString("ht_industrial_energy_billed");
				String study_month=obj.optString("study_month");
				String agri_energy_billed=obj.optString("agri_energy_billed");
				String others_energy_billed=obj.optString("others_energy_billed");
				String tpfdrid=obj.optString("tpfdrid");
				String powerfailduration=obj.optString("powerfailduration");
				String ht_industrial_amount_collected=obj.optString("ht_industrial_amount_collected");
				String lt_commercial_amount_collected=obj.optString("lt_commercial_amount_collected");
				String fdrtype=obj.optString("fdrtype");
				String govt_amount_billed=obj.optString("govt_amount_billed");
				String agri_amount_billed=obj.optString("agri_amount_billed");
				String study_year=obj.optString("study_year");
				String officeid=obj.optString("officeid");
				String ht_commercial_amount_collected=obj.optString("ht_commercial_amount_collected");
				String lt_domestic_amount_collected=obj.optString("lt_domestic_amount_collected");
				String lt_industrial_energy_billed=obj.optString("lt_industrial_energy_billed");
				String lt_industrial_amount_collected=obj.optString("lt_industrial_amount_collected");
				String others_amount_collected=obj.optString("others_amount_collected");
				
				//new columns
				String hut_energy_billed=obj.optString("hut_energy_billed");
				String hut_amount_collected=obj.optString("hut_amount_collected");
				String hut_amount_billed=obj.optString("hut_amount_billed");
				String hut_consumer_count=obj.optString("hut_consumer_count");
				String tpdtid=obj.optString("tpdtid");
				
				NppDtReportIntermediate npp=new NppDtReportIntermediate();
				
				if(!(agri_amount_billed.equalsIgnoreCase("null") || agri_amount_billed.equalsIgnoreCase(""))){
						npp.setAgri_amount_billed(Double.parseDouble(agri_amount_billed));
				}
				if(!(agri_amount_collected.equalsIgnoreCase("null")||agri_amount_collected.equalsIgnoreCase(""))){
				npp.setAgri_amount_collected(Double.parseDouble(agri_amount_collected));
				}
				if(!(agri_consumer_count.equalsIgnoreCase("null")||agri_consumer_count.equalsIgnoreCase(""))){
				npp.setAgri_consumer_count(Integer.parseInt(agri_consumer_count));
				}
				if(!(agri_energy_billed.equalsIgnoreCase("null")||agri_energy_billed.equalsIgnoreCase(""))){
				npp.setAgri_energy_billed(Double.parseDouble(agri_energy_billed));
				}
				if(!(export_energy.equalsIgnoreCase("null")||export_energy.equalsIgnoreCase(""))) {
				npp.setExport_energy(Double.parseDouble(export_energy));
				}
				//if(fdr_id.equalsIgnoreCase("null")){
				npp.setFdr_id(fdrid);
				//}
				//System.out.println("ht_commercial_amount_billed--"+ht_commercial_amount_billed);
				//System.out.println("export_energy--"+export_energy);
				//System.out.println("govt_amount_billed--"+govt_amount_billed);
				//System.out.println("TIME_STAMP--"+time_stamp);
				if(!(govt_amount_billed.equalsIgnoreCase("null")||govt_amount_billed.equalsIgnoreCase(""))) {
				npp.setGovt_amount_billed(Double.parseDouble(govt_amount_billed));
				}
				if(!(govt_amount_collected.equalsIgnoreCase("null")||govt_amount_collected.equalsIgnoreCase(""))) {
				npp.setGovt_amount_collected(Double.parseDouble(govt_amount_collected));
				}
				if(!(govt_consumer_count.equalsIgnoreCase("null")||govt_consumer_count.equalsIgnoreCase(""))) {
				npp.setGovt_consumer_count(Integer.parseInt(govt_consumer_count));
				}
				if(!(govt_energy_billed.equalsIgnoreCase("null")||govt_energy_billed.equalsIgnoreCase(""))) {
				npp.setGovt_energy_billed(Double.parseDouble(govt_energy_billed));
				}
				if(!(ht_commercial_amount_billed.equalsIgnoreCase("null")||ht_commercial_amount_billed.equalsIgnoreCase(""))) {
				npp.setHt_commercial_amount_billed(Double.parseDouble(ht_commercial_amount_billed));
				}
				if(!(ht_commercial_energy_billed.equalsIgnoreCase("null")||ht_commercial_energy_billed.equals(""))) {
				npp.setHt_commercial_energy_billed(Double.parseDouble(ht_commercial_energy_billed));
				}
				if(!(ht_commercial_amount_collected.equalsIgnoreCase("null")||ht_commercial_amount_collected.equalsIgnoreCase(""))) {
				npp.setHt_commercial_amount_collected(Double.parseDouble(ht_commercial_amount_collected));
				}
				if(!(ht_commercial_consumer_count.equalsIgnoreCase("null")||ht_commercial_consumer_count.equalsIgnoreCase(""))) {
				npp.setHt_commercial_consumer_count(Integer.parseInt(ht_commercial_consumer_count));
				}
				if(!(ht_industrial_amount_billed.equalsIgnoreCase("null")||ht_industrial_amount_billed.equalsIgnoreCase(""))) {
				npp.setHt_industrial_amount_billed(Double.parseDouble(ht_industrial_amount_billed));
				}
				if(!(ht_industrial_amount_collected.equalsIgnoreCase("null")||ht_industrial_amount_collected.equalsIgnoreCase(""))) {
				npp.setHt_industrial_amount_collected(Double.parseDouble(ht_industrial_amount_collected));
				}
				if(!(ht_industrial_consumer_count.equalsIgnoreCase("null")||ht_industrial_consumer_count.equalsIgnoreCase(""))) {
					npp.setHt_industrial_consumer_count(Integer.parseInt(ht_industrial_consumer_count));
				}
				if(!(ht_industrial_energy_billed.equalsIgnoreCase("null")||ht_industrial_energy_billed.equalsIgnoreCase(""))) {
					npp.setHt_industrial_energy_billed(Double.parseDouble(ht_industrial_energy_billed));
				}
				if(!(input_energy.equalsIgnoreCase("null")||input_energy.equalsIgnoreCase(""))) {
				npp.setInput_energy(Double.parseDouble(input_energy));
				}
				if(!(lt_commercial_amount_billed.equalsIgnoreCase("null")||lt_commercial_amount_billed.equalsIgnoreCase(""))) {
				npp.setLt_commercial_amount_billed(Double.parseDouble(lt_commercial_amount_billed));
				}
				if(!(lt_commercial_amount_collected.equalsIgnoreCase("null")||lt_commercial_amount_collected.equalsIgnoreCase(""))) {
				npp.setLt_commercial_amount_collected(Double.parseDouble(lt_commercial_amount_collected));
				}
				if(!(lt_commercial_consumer_count.equalsIgnoreCase("null")||lt_commercial_consumer_count.equalsIgnoreCase(""))) {
				npp.setLt_commercial_consumer_count(Integer.parseInt(lt_commercial_consumer_count));
				}
				if(!(lt_commercial_energy_billed.equalsIgnoreCase("null")||lt_commercial_energy_billed.equalsIgnoreCase(""))) {
				npp.setLt_commercial_energy_billed(Double.parseDouble(lt_commercial_energy_billed));
				}
				if(!(lt_domestic_amount_billed.equalsIgnoreCase("null")||lt_domestic_amount_billed.equalsIgnoreCase(""))) {
				npp.setLt_domestic_amount_billed(Double.parseDouble(lt_domestic_amount_billed));
				}
				if(!(lt_domestic_amount_collected.equalsIgnoreCase("null")||lt_domestic_amount_collected.equalsIgnoreCase(""))) {
				npp.setLt_domestic_amount_collected(Double.parseDouble(lt_domestic_amount_collected));
				}
				if(!(lt_domestic_consumer_count.equalsIgnoreCase("null")||lt_domestic_consumer_count.equalsIgnoreCase(""))) {
				npp.setLt_domestic_consumer_count(Integer.parseInt(lt_domestic_consumer_count));
				}
				if(!(lt_domestic_energy_billed.equalsIgnoreCase("null")||lt_domestic_energy_billed.equalsIgnoreCase(""))) {
				npp.setLt_domestic_energy_billed(Double.parseDouble(lt_domestic_energy_billed));
				}
				if(!(lt_industrial_amount_billed.equalsIgnoreCase("null")||lt_industrial_amount_billed.equalsIgnoreCase(""))) {
				npp.setLt_industrial_amount_billed(Double.parseDouble(lt_industrial_amount_billed));
				}
				if(!(lt_industrial_amount_collected.equalsIgnoreCase("null")||lt_industrial_amount_collected.equalsIgnoreCase(""))) {
				npp.setLt_industrial_amount_collected(Double.parseDouble(lt_industrial_amount_collected));
				}
				
				if(!(lt_industrial_consumer_count.equalsIgnoreCase("null")||lt_industrial_consumer_count.equalsIgnoreCase(""))) {
					npp.setLt_industrial_consumer_count(Integer.parseInt(lt_industrial_consumer_count));
					}
					if(!(lt_industrial_energy_billed.equalsIgnoreCase("null")||lt_industrial_energy_billed.equalsIgnoreCase(""))) {
					npp.setLt_industrial_energy_billed(Double.parseDouble(lt_industrial_energy_billed));
					}
					if(!(max_current.equalsIgnoreCase("null")||max_current.equalsIgnoreCase(""))) {
					npp.setMax_current(Double.parseDouble(max_current));
					}
					if(!(minimum_voltage.equalsIgnoreCase("null")||minimum_voltage.equalsIgnoreCase(""))) {
					npp.setMinimum_voltage(Double.parseDouble(minimum_voltage));
					}
					if(!(officeid.equalsIgnoreCase(""))) {
					npp.setOfficeid(officeid);
					}
					if(!(open_access_units.equalsIgnoreCase("null")||open_access_units.equalsIgnoreCase(""))) {
					npp.setOpen_access_units(Double.parseDouble(open_access_units));
					}
					if(!(others_amount_billed.equalsIgnoreCase("null")||others_amount_billed.equalsIgnoreCase(""))) {
					npp.setOthers_amount_billed(Double.parseDouble(others_amount_billed));
					}
					if(!(others_amount_collected.equalsIgnoreCase("null")||others_amount_collected.equalsIgnoreCase(""))) {
					npp.setOthers_amount_collected(Double.parseDouble(others_amount_collected));
					}
					if(!(others_consumer_count.equalsIgnoreCase("null")||others_consumer_count.equalsIgnoreCase(""))) {
					npp.setOthers_consumer_count(Integer.parseInt(others_consumer_count));
					}
					if(!(others_energy_billed.equalsIgnoreCase("null")||others_energy_billed.equalsIgnoreCase(""))) {
					npp.setOthers_energy_billed(Double.parseDouble(others_energy_billed));
					}
					if(!(powerfailduration.equalsIgnoreCase("null")||powerfailduration.equalsIgnoreCase(""))) {
					npp.setPower_fail_duration(Integer.parseInt(powerfailduration));
					}
					if(!(powerfailfreq.equalsIgnoreCase("null")||powerfailfreq.equalsIgnoreCase(""))) {
					npp.setPower_fail_freq(Integer.parseInt(powerfailfreq));
					}
					if(!(time_stamp.equalsIgnoreCase("null")||time_stamp.equalsIgnoreCase(""))) {
					npp.setTime_stamp(getTimeStamp(time_stamp));
					}
					if(!(hut_energy_billed.equalsIgnoreCase("null")||hut_energy_billed.equalsIgnoreCase(""))) {
						npp.setHut_energy_billed(Double.parseDouble(hut_energy_billed));
					}
					if(!(hut_consumer_count.equalsIgnoreCase("null")||hut_consumer_count.equalsIgnoreCase(""))) {
						npp.setHut_consumer_count(Long.parseLong(hut_consumer_count));
					}
					if(!(hut_amount_billed.equalsIgnoreCase("null")||hut_amount_billed.equalsIgnoreCase(""))) {
						npp.setHut_amount_billed(Double.parseDouble(hut_amount_billed));
					}
					if(!(hut_amount_collected.equalsIgnoreCase("null")||hut_amount_collected.equalsIgnoreCase(""))) {
						npp.setHut_amount_collected(Double.parseDouble(hut_amount_collected));
					}
					
					npp.setTowncode(towncode);
					npp.setFdrtype(fdrtype);
					npp.setTp_fdr_id(tpfdrid);
					npp.setReadtime(new Timestamp(System.currentTimeMillis()));
					//npp.setFlag(service);
					if(!(study_month.equalsIgnoreCase("null")||study_month.equalsIgnoreCase(""))) 
					{
					month=Integer.parseInt(study_month);
					}
					if(!study_year.equalsIgnoreCase("null")) 
					{
					year=Integer.parseInt(study_year);
					}
					if(!(tpdtid.equalsIgnoreCase("null")||tpdtid.equalsIgnoreCase("")))
					{
						tpdtid1=tpdtid;
					}
					if(tpdtid1!=""&&month!=0&&year!=0&&service!=""){
						//NppFeederReportIntermediate np1=
					npp.setKeyNppDtIntermediate(new KeyNppDtIntermediate(tpdtid, month, year, service));
					try {
						if (update(npp) instanceof NppDtReportIntermediate) {
						}
						
						System.out.println("save npp completed");
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	static Timestamp getTimeStamp(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			if(value.contains("-")){
				return new Timestamp(format.parse(value).getTime());
			}
			if(value.contains("/")){
				return new Timestamp(format1.parse(value).getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return null;
	}
}
