package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.entity.VoltageRegulationEntity;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.service.VoltageRegulationService;
import com.bcits.utility.CalenderClass;

@Repository
public class VoltageRegulationServiceImpl extends GenericServiceImpl<VoltageRegulationEntity> implements VoltageRegulationService {

	@Autowired
	private AmiLocationService amiLocationService;
	@Autowired 
	CalenderClass calenderClass;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	public void saveVoltageVariationData() {
	List<String> siteCodes=new ArrayList<>();
	siteCodes=amiLocationService.getAllSiteCodes();
	String rdngmonth=String.valueOf(calenderClass.getPresentMonth());
	String year=rdngmonth.substring(0, 4);
	String mnth=rdngmonth.substring(4,6);
	for (Object sdocode : siteCodes) {
		
		List<?> voltageVarData=new ArrayList<>();
		String sql="SELECT * FROM\n" +
				"				(select sdocode,fdrcategory,mtrno,accno from meter_data.master_main where   phase IS NOT NULL  and sdocode like '"+sdocode+"' )A,\n" +
				"				(SELECT month_year,meter_number,(ver_less_6/60) as less_6, (ver_0_6/60) as btw_6_0,( ver_0_5/60) as btw_0_5, (ver_great_5/60) as great_5,round(((no_load*CAST((SELECT meter_ip_period FROM meter_data.meter_inventory WHERE meterno in (meter_number)) as NUMERIC))/60) ,2) as no_load_duration from\n" +
				"				(\n" +
				"				SELECT a.month_year, a.meter_number,COUNT(CASE WHEN kva=0 THEN 1 END) as no_load,\n" +
				"				COALESCE(sum(CASE WHEN per<0 AND per>=-6 THEN 30 END),0) as ver_0_6,\n" +
				"				COALESCE(sum(CASE WHEN per>=0 AND per<=5 THEN 30 END),0) as ver_0_5,\n" +
				"				COALESCE(sum(CASE WHEN per<-6 THEN 30 END),0) as ver_less_6,\n" +
				"				COALESCE(sum(CASE WHEN per>5 THEN 30 END),0) as ver_great_5\n" +
				"				FROM\n" +
				"				(\n" +
				"				SELECT read_time, average_voltage,meter_number,kva,\n" +
				"				round(((CAST(average_voltage as NUMERIC)-CAST(COALESCE(m.voltage_kv,'0') as NUMERIC))/\n" +
				"				(CASE WHEN CAST(COALESCE(m.voltage_kv,'0') as NUMERIC)=0 THEN 1 ELSE CAST(COALESCE(m.voltage_kv,'0') as NUMERIC) END))*100, 2) as per,to_char(read_time,'YYYYMM') as month_year\n" +
				"				from meter_data.load_survey l,meter_data.master_main m WHERE    l.meter_number=m.mtrno and\n" +
				"				TO_CHAR(read_time, 'YYYYMM') ='"+rdngmonth+"' ORDER BY read_time ASC\n" +
				"				) a GROUP BY a.month_year,a.meter_number\n" +
				"				)c)b,(select meter_sr_number,(cast (event_duration as varchar)) as event_duration from meter_data.event_summary where event_name='Power failure' and month_year='"+rdngmonth+"'\n" +
				")p	WHERE a.mtrno=b.meter_number and a.mtrno=p.meter_sr_number and b.meter_number=p.meter_sr_number";
		try {
			voltageVarData=postgresMdas.createNativeQuery(sql).getResultList();
			System.out.println(voltageVarData.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveVolageVariationEntity(voltageVarData,rdngmonth);
		
		
	}
	}
	private void saveVolageVariationEntity(List<?> voltageVarData, String rdngmonth) {
		// TODO Auto-generated method stub
		for (int i = 0; i < voltageVarData.size(); i++) {
			Double hours = null;
			
			Timestamp timestamp = new Timestamp(new Date().getTime());
			Object[] volrptdata=(Object[]) voltageVarData.get(i);
		VoltageRegulationEntity vre=new VoltageRegulationEntity();
		//convert to hours
		if(volrptdata[12]!=null) {
		if(volrptdata[12].toString().contains("days")){
			String a=volrptdata[12].toString();
			String days=a.substring(0, 2);
			String replacesStr=a.replaceAll("days", "");
			String dat[]=(replacesStr.toString()).split(":");
			System.out.println(replacesStr);
			String b=dat[0].substring(dat[0].length()-2, dat[0].length());
			System.out.println(b+"   "+days+" ss "+dat[0].substring(dat[0].length()-2, dat[0].length())+"dc "+dat[1]+" "+dat[2]);
			hours=Double.parseDouble(days)*24+ Double.parseDouble(dat[0].substring(dat[0].length()-2, dat[0].length()))+(Double.parseDouble(dat[1]))/60+(Double.parseDouble(dat[2])/3600);

			
			/*String a=volrptdata[12].toString();
			int days=a.ind
			String dat[]=(a.toString()).split(":");*/
			System.out.println(days+"  "+dat[0]+dat[1]+dat[2]+hours+"  d=="+df2.format(hours));
		}
		else {
		String dat[]=(volrptdata[12].toString()).split(":");
		//System.out.println(dat[0]+dat[1]+dat[2]);
		 hours=Double.parseDouble(dat[0])+(Double.parseDouble(dat[0]))/60+(Double.parseDouble(dat[1])/3600);
		//System.out.println(hours+"  "+Double.parseDouble(volrptdata[10].toString()));
		}
		}
		else {
			volrptdata[12]=null;
		}
		
		vre.setOfficeId(volrptdata[0]==null?"":volrptdata[0].toString());
		vre.setLocationType(volrptdata[1]==null?"":volrptdata[1].toString());
		vre.setLocationCode(volrptdata[3]==null?"":volrptdata[3].toString());
		vre.setMonthYear(volrptdata[4]==null?"":volrptdata[4].toString());
		vre.setMeterSerialNum(volrptdata[2]==null?"":volrptdata[2].toString());
		vre.setRange1(volrptdata[6]==null? null:Long.parseLong(volrptdata[6].toString()));
		vre.setRange2(volrptdata[7]==null? null:Long.parseLong(volrptdata[7].toString()));
		vre.setRange3(volrptdata[8]==null? null:Long.parseLong(volrptdata[8].toString()));
		vre.setRange4(volrptdata[9]==null? null:Long.parseLong(volrptdata[9].toString()));
		vre.setNoLoadDuration((volrptdata[10] ==null? null:Double.parseDouble(volrptdata[10].toString())));
		vre.setPowerOffDuration((double) Math.round(hours));
		vre.setTimeStamp(timestamp);
		save(vre);
		//System.out.println(Double.parseDouble(volrptdata[10].toString()));
		
		
		}
		
		
	}
}
