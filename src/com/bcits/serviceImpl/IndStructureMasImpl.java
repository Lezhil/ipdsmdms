package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.bcits.entity.IndStructureMasEntity;
import com.bcits.entity.IndssMassEntity;
import com.bcits.service.IndStructureMasService;

@Repository
public class IndStructureMasImpl extends GenericServiceImpl<IndStructureMasEntity> implements IndStructureMasService{

	@Override
	public String insertIndsStructureMas(String data)  {
		System.out.println("inside insertIndsStructureMas");
		try {

			//res="[{\"timeStamp\":\"2019-06-10 12:53:36\",\"ncreqpending\":\"24\",\"ncreqclosed\":\"6\",\"closedwithinserc\":\"6\",\"closedbeyondserc\":\"0\",\"releasedbyitsystm\":\"6\",\"ncreqreceived\":\"11\",\"totncreq\":\"30\",\"rec_id\":null,\"ncreqopeningcnt\":\"19\",\"percent_withinserc\":\"100\"}]";
			//data="[{\"entrytime\":null,\"afss4\":null,\"aeno\":null,\"ccoden\":null,\"afss3\":null,\"afss2\":null,\"villcode_no\":null,\"afss1\":null,\"aecodedummy\":null,\"strcode\":\"220\",\"sscode\":\"4412\",\"vcode\":null,\"inchargepostcode\":null,\"load\":null,\"aecode\":\"AE424553\",\"dt_load_no\":\"47\",\"spurcode\":null,\"place\":null,\"add2\":null,\"add1\":null,\"fcode\":\"5\",\"entrydt\":\"2019-06-20 13:05:43\",\"affcode1\":null,\"aestrcode\":\"236\",\"strname\":\"BHARATHY NAGAR SS VI ADDL\",\"locno\":\"6\",\"affcode4\":null,\"affcode2\":null,\"sub_fcode\":null,\"affcode3\":null,\"ip\":\"172.61.145.228\",\"crby\":\"SS4412\",\"cptag\":\"N\",\"ssfdrstrcode\":\"441205220\",\"dtkva\":null,\"ccode\":\"424\",\"dt_cap_no\":\"100\"}]]";
			JSONArray recs = new JSONArray(data.toString());
			System.out.println("response length--->"+recs.length());
			
			for (int i = 0; i < recs.length(); i++) {
				JSONObject obj = recs.getJSONObject(i);
				
				String	entrytime=obj.optString("entrytime");
				String	afss4=obj.optString("afss4");
				String	aeno=obj.optString("aeno");
				String	ccoden=obj.optString("ccoden");
				String	afss3=obj.optString("afss3");
				String	afss2=obj.optString("afss2");
				String	villcode_no=obj.optString("villcode_no");
				String	afss1=obj.optString("afss1");
				String	aecodedummy=obj.optString("aecodedummy");
				String	strcode=obj.optString("strcode");
				String	sscode=obj.optString("sscode");
				String	vcode=obj.optString("vcode");
				String	inchargepostcode=obj.optString("inchargepostcode");
				String	load=obj.optString("load");
				String	aecode=obj.optString("aecode");
				String	dt_load_no=obj.optString("dt_load_no");
				String	spurcode=obj.optString("spurcode");
				String	place=obj.optString("place");
				String	add2=obj.optString("add2");
				String	add1=obj.optString("add1");
				String	fcode=obj.optString("fcode");
				String	entrydt=obj.optString("entrydt");
				String	affcode1=obj.optString("affcode1");
				String	aestrcode=obj.optString("aestrcode");
				String	strname=obj.optString("strname");
				String	locno=obj.optString("locno");
				String	affcode4=obj.optString("affcode4");
				String	affcode2=obj.optString("affcode2");
				String	sub_fcode=obj.optString("sub_fcode");
				String	affcode3=obj.optString("affcode3");
				String	ip=obj.optString("ip");
				String	crby=obj.optString("crby");
				String	cptag=obj.optString("cptag");
				String	ssfdrstrcode=obj.optString("ssfdrstrcode");
				String	dtkva=obj.optString("dtkva");
				String	ccode=obj.optString("ccode");
				String	dt_cap_no=obj.optString("dt_cap_no");

				IndStructureMasEntity indstr=new IndStructureMasEntity();
				if(!entrytime.equalsIgnoreCase("null"))
				{
				indstr.setEntrytime(entrytime);
				}
				if(!afss4.equalsIgnoreCase("null"))
				{
				indstr.setAfss4(Integer.parseInt(afss4));
				}
				if(!aeno.equalsIgnoreCase("null"))
				{
				indstr.setAeno(Integer.parseInt(aeno));
				}
				if(!ccoden.equalsIgnoreCase("null"))
				{
				indstr.setCcoden(Integer.parseInt(ccoden));
				}
				if(!afss3.equalsIgnoreCase("null"))
				{
				indstr.setAfss3(Integer.parseInt(afss3));
				}
				if(!afss2.equalsIgnoreCase("null"))
				{
				indstr.setAfss2(Integer.parseInt(afss2));
				}
				if(!villcode_no.equalsIgnoreCase("null"))
				{
				indstr.setVillcode_no(Integer.parseInt(villcode_no));
				}
				if(!afss1.equalsIgnoreCase("null"))
				{
				indstr.setAfss1(Integer.parseInt(afss1));
				}
				if(!aecodedummy.equalsIgnoreCase("null"))
				{
				indstr.setAecodedummy(aecodedummy);
				}
				if(!strcode.equalsIgnoreCase("null"))
				{
				indstr.setStrcode(Integer.parseInt(strcode));
				}
				if(!sscode.equalsIgnoreCase("null"))
				{
				indstr.setSscode(Integer.parseInt(sscode));
				}
				if(!vcode.equalsIgnoreCase("null"))
				{
				indstr.setVcode(Integer.parseInt(vcode));
				}
				if(!inchargepostcode.equalsIgnoreCase("null"))
				{
				indstr.setInchargepostcode(inchargepostcode);
				}
				if(!load.equalsIgnoreCase("null"))
				{
				indstr.setLoad(Double.parseDouble(load));
				}
				if(!aecode.equalsIgnoreCase("null"))
				{
				indstr.setAecode(aecode);
				}
				if(!dt_load_no.equalsIgnoreCase("null"))
				{
				indstr.setDt_load_no(Integer.parseInt(dt_load_no));
				}
				if(!spurcode.equalsIgnoreCase("null"))
				{
				indstr.setSpurcode(Integer.parseInt(spurcode));
				}
				if(!place.equalsIgnoreCase("null"))
				{
				indstr.setPlace(place);
				}
				if(!add2.equalsIgnoreCase("null"))
				{
				indstr.setAdd2(add2);
				}
				if(!add1.equalsIgnoreCase("null"))
				{
				indstr.setAdd1(add1);
				}
				if(!fcode.equalsIgnoreCase("null"))
				{
				indstr.setFcode(Integer.parseInt(fcode));
				}
				if(!entrydt.equalsIgnoreCase("null"))
				{
				indstr.setEntrydt(getTimeStamp(entrydt));
				}
				if(!affcode1.equalsIgnoreCase("null"))
				{
				indstr.setAffcode1(Integer.parseInt(affcode1));
				}
				if(!aestrcode.equalsIgnoreCase("null"))
				{
				indstr.setAestrcode(Integer.parseInt(aestrcode));
				}
				if(!strname.equalsIgnoreCase("null"))
				{
				indstr.setStrname(strname);
				}
				if(!locno.equalsIgnoreCase("null"))
				{
				indstr.setLocno(Integer.parseInt(locno));
				}
				if(!affcode4.equalsIgnoreCase("null"))
				{
				indstr.setAffcode4(Integer.parseInt(affcode4));
				}
				if(!affcode2.equalsIgnoreCase("null"))
				{
				indstr.setAffcode2(Integer.parseInt(affcode2));
				}
				if(!sub_fcode.equalsIgnoreCase("null"))
				{
				indstr.setSub_fcode(Integer.parseInt(sub_fcode));
				}
				if(!affcode3.equalsIgnoreCase("null"))
				{
				indstr.setAffcode3(Integer.parseInt(affcode3));
				}
				if(!ip.equalsIgnoreCase("null"))
				{
				indstr.setIp(ip);
				}
				if(!crby.equalsIgnoreCase("null"))
				{
				indstr.setCrby(crby);
				}
				if(!cptag.equalsIgnoreCase("null"))
				{
				indstr.setCptag(cptag);
				}
				if(!ssfdrstrcode.equalsIgnoreCase("null"))
				{
				indstr.setSsfdrstrcode(Integer.parseInt(ssfdrstrcode));
				}
				if(!dtkva.equalsIgnoreCase("null"))
				{
				indstr.setDtkva(Double.parseDouble(dtkva));
				}
				if(!ccode.equalsIgnoreCase("null"))
				{
				indstr.setCcode(Integer.parseInt(ccode));
				}
				if(!dt_cap_no.equalsIgnoreCase("null"))
				{
				indstr.setDt_cap_no(Integer.parseInt(dt_cap_no));
				}
				
				save(indstr);
				
				
				
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
