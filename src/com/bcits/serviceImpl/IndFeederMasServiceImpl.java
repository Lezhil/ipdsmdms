package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.IndFeederMasEntity;
import com.bcits.entity.PfcD2IntermediateEntity;
import com.bcits.service.IndFeederMasService;

@Repository
public class IndFeederMasServiceImpl extends GenericServiceImpl<IndFeederMasEntity> implements IndFeederMasService{

	@Override
	public String insertIndssFeederMasData(String data) {
		System.out.println("inside insertIndssFeederMasData");
		try {

			//res="[{\"timeStamp\":\"2019-06-10 12:53:36\",\"ncreqpending\":\"24\",\"ncreqclosed\":\"6\",\"closedwithinserc\":\"6\",\"closedbeyondserc\":\"0\",\"releasedbyitsystm\":\"6\",\"ncreqreceived\":\"11\",\"totncreq\":\"30\",\"rec_id\":null,\"ncreqopeningcnt\":\"19\",\"percent_withinserc\":\"100\"}]";
			//data="[{\"htloadkva\":null,\"ssfcode\":null,\"mfcode\":null,\"occode\":\"462\",\"fvkv\":null,\"peakkva\":null,\"sscode\":\"5260\",\"peakkvadt\":null,\"inOut\":null,\"lengthmf\":null,\"size3\":null,\"size4\":null,\"size1\":null,\"size2\":null,\"fmaecode\":null,\"fcode\":null,\"noSubFeeder\":null,\"energyacTag\":null,\"fname\":null,\"nodt\":null,\"moditime\":null,\"detime\":null,\"editenergyacTag\":null,\"ip\":null,\"crby\":null,\"cptag\":null,\"ccode\":\"52\",\"length1\":null,\"ftype\":null,\"pkaddlloadamps\":null,\"dtloadkva\":null,\"length3\":null,\"length2\":null,\"peakampsdt\":null,\"fmae2\":null,\"length4\":null,\"fmae3\":null,\"maxct\":null,\"fmae4\":null,\"nospur\":null,\"pkaddlloadkva\":null,\"peakamps\":null}]";
			//data="[{\"htloadkva\":null,\"ssfcode\":\"645002\",\"mfcode\":null,\"occode\":\"444\",\"fvkv\":\"11\",\"peakkva\":null,\"sscode\":\"6450\",\"peakkvadt\":null,\"inOut\":null,\"lengthmf\":\"7000\",\"size3\":null,\"size4\":null,\"size1\":null,\"size2\":null,\"fmaecode\":null,\"fcode\":\"2\",\"noSubFeeder\":null,\"energyacTag\":null,\"fname\":\"645002-PATHIRANKOTTAI\",\"nodt\":\"69\",\"moditime\":null,\"detime\":\"2019-06-12 13:37:02\",\"editenergyacTag\":null,\"ip\":\"223.233.69.178\",\"crby\":\"SS6450\",\"cptag\":\"U\",\"ccode\":null,\"length1\":null,\"ftype\":\"RURAL\",\"pkaddlloadamps\":null,\"dtloadkva\":null,\"length3\":null,\"length2\":null,\"peakampsdt\":null,\"fmae2\":null,\"length4\":null,\"fmae3\":null,\"maxct\":null,\"fmae4\":null,\"nospur\":null,\"pkaddlloadkva\":null,\"peakamps\":null}]";
			//data="[{\"htloadkva\":null,\"ssfcode\":null,\"mfcode\":null,\"occode\":\"462\",\"fvkv\":null,\"peakkva\":null,\"sscode\":\"5260\",\"peakkvadt\":null,\"inOut\":null,\"lengthmf\":null,\"size3\":null,\"size4\":null,\"size1\":null,\"size2\":null,\"fmaecode\":null,\"fcode\":null,\"noSubFeeder\":null,\"energyacTag\":null,\"fname\":null,\"nodt\":null,\"moditime\":null,\"detime\":null,\"editenergyacTag\":null,\"ip\":null,\"crby\":null,\"cptag\":null,\"ccode\":\"52\",\"length1\":null,\"ftype\":null,\"pkaddlloadamps\":null,\"dtloadkva\":null,\"length3\":null,\"length2\":null,\"peakampsdt\":null,\"fmae2\":null,\"length4\":null,\"fmae3\":null,\"maxct\":null,\"fmae4\":null,\"nospur\":null,\"pkaddlloadkva\":null,\"peakamps\":null}]";
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->"+recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				
				
				String	htloadkva=obj.optString("htloadkva");
				String	ssfcode=obj.optString("ssfcode");
				String	mfcode=obj.optString("mfcode");
				String	occode=obj.optString("occode");
				String	fvkv=obj.optString("fvkv");
				String	peakkva=obj.optString("peakkva");
				String	sscode=obj.optString("sscode");
				String	peakkvadt=obj.optString("peakkvadt");
				String	inOut=obj.optString("inOut");
				String	lengthmf=obj.optString("lengthmf");
				String	size3=obj.optString("size3");
				String	size4=obj.optString("size4");
				String	size1=obj.optString("size1");
				String	size2=obj.optString("size2");
				String	fmaecode=obj.optString("fmaecode");
				String	fcode=obj.optString("fcode");
				String	noSubFeeder=obj.optString("noSubFeeder");
				String	energyacTag=obj.optString("energyacTag");
				String	fname=obj.optString("fname");
				String	nodt=obj.optString("nodt");
				String	moditime=obj.optString("moditime");
				String	detime=obj.optString("detime");
				String	editenergyacTag=obj.optString("editenergyacTag");
				String	ip=obj.optString("ip");
				String	crby=obj.optString("crby");
				String	cptag=obj.optString("cptag");
				String	ccode=obj.optString("ccode");
				String	length1=obj.optString("length1");
				String	ftype=obj.optString("ftype");
				String	pkaddlloadamps=obj.optString("pkaddlloadamps");
				String	dtloadkva=obj.optString("dtloadkva");
				String	length3=obj.optString("length3");
				String	length2=obj.optString("length2");
				String	peakampsdt=obj.optString("peakampsdt");
				String	fmae2=obj.optString("fmae2");
				String	length4=obj.optString("length4");
				String	fmae3=obj.optString("fmae3");
				String	maxct=obj.optString("maxct");
				String	fmae4=obj.optString("fmae4");
				String	nospur=obj.optString("nospur");
				String	pkaddlloadkva=obj.optString("pkaddlloadkva");
				String	peakamps=obj.optString("peakamps");


				IndFeederMasEntity fdrmas=new IndFeederMasEntity();
				
				/*
				 * fdrmas.setHtloadkva(Double.parseDouble(htloadkva));
				 * fdrmas.setSsfcode(Integer.parseInt(ssfcode));
				 * fdrmas.setMfcode(Integer.parseInt(mfcode));
				 * fdrmas.setOccode(Integer.parseInt(occode));
				 * fdrmas.setFvkv(Integer.parseInt(fvkv));
				 * fdrmas.setPeakkva(Double.parseDouble(peakkva));
				 * fdrmas.setSscode(Integer.parseInt(sscode));
				 * fdrmas.setPeakkvadt(getTimeStamp(peakkvadt)); fdrmas.setIn_out(inOut);
				 * fdrmas.setLengthmf(Integer.parseInt(lengthmf)); fdrmas.setSize3(size3);
				 * fdrmas.setSize4(size4); fdrmas.setSize1(size1); fdrmas.setSize2(size2);
				 * fdrmas.setFmaecode(fmaecode); fdrmas.setFcode(Integer.parseInt(fcode));
				 * fdrmas.setNo_sub_feeder(Integer.parseInt(noSubFeeder));
				 * fdrmas.setEditenergyac_tag(editenergyacTag); fdrmas.setFname(fname);
				 * fdrmas.setNodt(Integer.parseInt(nodt));
				 * fdrmas.setModitime(getTimeStamp(moditime));
				 * fdrmas.setDetime(getTimeStamp(detime));
				 * fdrmas.setEditenergyac_tag(editenergyacTag); fdrmas.setIp(ip);
				 * fdrmas.setCrby(crby); fdrmas.setCptag(cptag);
				 * fdrmas.setCcode(Integer.parseInt(ccode));
				 * fdrmas.setLength1(Double.parseDouble(length1)); fdrmas.setFtype(ftype);
				 * fdrmas.setPkaddlloadamps(Double.parseDouble(pkaddlloadamps));
				 * fdrmas.setDtloadkva(Double.parseDouble(dtloadkva));
				 * fdrmas.setLength3(Double.parseDouble(length3));
				 * fdrmas.setLength2(Double.parseDouble(length2));
				 * fdrmas.setPeakamps(Double.parseDouble(peakamps)); fdrmas.setFmae2(fmae2);
				 * fdrmas.setLength4(Double.parseDouble(length4)); fdrmas.setFmae3(fmae3);
				 * fdrmas.setMaxct(Integer.parseInt(maxct)); fdrmas.setFmae4(fmae4);
				 * fdrmas.setNospur(Integer.parseInt(nospur));
				 * fdrmas.setPkaddlloadkva(Double.parseDouble(pkaddlloadkva));
				 * fdrmas.setPeakamps(Double.parseDouble(peakamps));
				 */

					
					if(!htloadkva.equalsIgnoreCase("null"))
					{
					fdrmas.setHtloadkva(Double.parseDouble(htloadkva));
					}
					if(!ssfcode.equalsIgnoreCase("null"))
					{
					fdrmas.setSsfcode(Integer.parseInt(ssfcode));
					}
					if(!mfcode.equalsIgnoreCase("null")){
					fdrmas.setMfcode(Integer.parseInt(mfcode));
					}
					if(!occode.equalsIgnoreCase("null")){
					fdrmas.setOccode(Integer.parseInt(occode));
					}
					if(!fvkv.equalsIgnoreCase("null")){
					fdrmas.setFvkv(Integer.parseInt(fvkv));
					}
					if(!peakkva.equalsIgnoreCase("null"))
					{
					fdrmas.setPeakkva(Double.parseDouble(peakkva));
					}
					if(!sscode.equalsIgnoreCase("null")){
					fdrmas.setSscode(Integer.parseInt(sscode));
					}
					if(!peakkvadt.equalsIgnoreCase("null")){
					fdrmas.setPeakkvadt(getTimeStamp(peakkvadt));
					}
					if(!inOut.equalsIgnoreCase("null")){
					fdrmas.setIn_out(inOut);
					}
					if(!lengthmf.equalsIgnoreCase("null")){
					fdrmas.setLengthmf(Integer.parseInt(lengthmf));
					}
					if(!size3.equalsIgnoreCase("null")){
					fdrmas.setSize3(size3);
					}
					if(!size4.equalsIgnoreCase("null")){
					fdrmas.setSize4(size4);
					}
					if(!size1.equalsIgnoreCase("null")){
					fdrmas.setSize1(size1);
					}
					if(!size2.equalsIgnoreCase("null")){
					fdrmas.setSize2(size2);
					}
					if(!fmaecode.equalsIgnoreCase("null")){
					fdrmas.setFmaecode(fmaecode);
					}
					if(!fcode.equalsIgnoreCase("null")){
					fdrmas.setFcode(Integer.parseInt(fcode));
					}
					if(!noSubFeeder.equalsIgnoreCase("null")){
					fdrmas.setNo_sub_feeder(Integer.parseInt(noSubFeeder));
					}
					if(!editenergyacTag.equalsIgnoreCase("null")){
					fdrmas.setEditenergyac_tag(editenergyacTag);
					}
					if(!fname.equalsIgnoreCase("null")){
					fdrmas.setFname(fname);
					}
					if(!nodt.equalsIgnoreCase("null")){
					fdrmas.setNodt(Integer.parseInt(nodt));
					}
					if(!moditime.equalsIgnoreCase("null")){
					fdrmas.setModitime(getTimeStamp(moditime));
					}
					if(!detime.equalsIgnoreCase("null")){
					fdrmas.setDetime(getTimeStamp(detime));
					}
					if(!editenergyacTag.equalsIgnoreCase("null")){
					fdrmas.setEditenergyac_tag(editenergyacTag);
					}
					if(!ip.equalsIgnoreCase("null")){
					fdrmas.setIp(ip);
					}
					if(!crby.equalsIgnoreCase("null")){
					fdrmas.setCrby(crby);
					}
					if(!cptag.equalsIgnoreCase("null")){
					fdrmas.setCptag(cptag);
					}
					if(!ccode.equalsIgnoreCase("null")){
					fdrmas.setCcode(Integer.parseInt(ccode));}
					if(!length1.equalsIgnoreCase("null")){
					fdrmas.setLength1(Double.parseDouble(length1));}
					if(!ftype.equalsIgnoreCase("null")){
					fdrmas.setFtype(ftype);
					}
					if(!pkaddlloadamps.equalsIgnoreCase("null")){
					fdrmas.setPkaddlloadamps(Double.parseDouble(pkaddlloadamps));}
					if(!dtloadkva.equalsIgnoreCase("null")){
					fdrmas.setDtloadkva(Double.parseDouble(dtloadkva));}
					if(!length3.equalsIgnoreCase("null")){
					fdrmas.setLength3(Double.parseDouble(length3));}
					if(!length2.equalsIgnoreCase("null")){
					fdrmas.setLength2(Double.parseDouble(length2));}
					if(!peakamps.equalsIgnoreCase("null")){
					fdrmas.setPeakamps(Double.parseDouble(peakamps));}
					if(!fmae2.equalsIgnoreCase("null")){
					fdrmas.setFmae2(fmae2);
					}
					if(!length4.equalsIgnoreCase("null")){
					fdrmas.setLength4(Double.parseDouble(length4));}
					if(!fmae3.equalsIgnoreCase("null")){
					fdrmas.setFmae3(fmae3);
					}
					if(!maxct.equalsIgnoreCase("null")){
					fdrmas.setMaxct(Integer.parseInt(maxct));
					}
					if(!fmae4.equalsIgnoreCase("null")){
					fdrmas.setFmae4(fmae4);
					}
					if(!nospur.equalsIgnoreCase("null")){
					fdrmas.setNospur(Integer.parseInt(nospur));
					}
					if(!pkaddlloadkva.equalsIgnoreCase("null")){
					fdrmas.setPkaddlloadkva(Double.parseDouble(pkaddlloadkva));
					}
					if(!peakamps.equalsIgnoreCase("null")){
					fdrmas.setPeakamps(Double.parseDouble(peakamps));
					}
					save(fdrmas);
				
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
