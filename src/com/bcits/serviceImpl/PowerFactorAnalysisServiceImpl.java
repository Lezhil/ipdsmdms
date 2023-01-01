package com.bcits.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.entity.PowerFactorAnalysisEntity;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.service.PowerFactorAnalysisService;
import com.bcits.utility.CalenderClass;

@Repository
public class PowerFactorAnalysisServiceImpl extends GenericServiceImpl<PowerFactorAnalysisEntity> implements PowerFactorAnalysisService {

	@Autowired
	private AmiLocationService amiLocationService;
	@Autowired 
	CalenderClass calenderClass;
	@Override
	public void savePfAnalysisData() {
		// TODO Auto-generated method stub
		List<String> siteCodes=new ArrayList<>();
		siteCodes=amiLocationService.getAllSiteCodes();
		String rdngmonth=getCurrentYearPreviousMonth();
		//String rdngmonth=String.valueOf(calenderClass.getPresentMonth());
		String year=rdngmonth.substring(0, 4);
		String mnth=rdngmonth.substring(4,6);
		for (Object sdocode : siteCodes) {
			//System.err.println(sdocode.toString());
			List<?> pfData=new ArrayList<>();
			String sql="select q.*,l.event_duration from \n" +
					"(select b.sdocode,b.fdrcategory,b.kno,a.meter_number,a.slab1 as slab1period,\n" +
					"a.slab2 as slab2period,\n" +
					"a.slab3 as slab3period,\n" +
					"a.slab4 as slab4period,a.loadOfDuration FROM\n" +
					"(SELECT meter_number, \n" +
					"COALESCE(sum(case WHEN power_factor >0 AND power_factor<0.5 THEN 30 END),0)/60 as slab1,\n" +
					"COALESCE(sum(case WHEN power_factor >=0.5 AND power_factor<0.7 THEN 30 END),0)/60 as slab2,\n" +
					"COALESCE(sum(case WHEN power_factor >=0.7 AND power_factor<0.9 THEN 30 END),0)/60 as slab3,\n" +
					"COALESCE(sum(case WHEN power_factor >=0.9  THEN 30 END),0)/60 as slab4,\n" +
					"COALESCE(sum(case WHEN kwh=0 THEN 30 END),0)/60 as  loadOfDuration,\n" +
					"(COUNT(*)*30) AS total,\n" +
					"((SELECT meter_data.num_days("+year+", "+mnth+"))*24) as total_month\n" +
					"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))="+year+" \n" +
					"and meter_number in(select mtrno from meter_data.master_main \n" +
					" where sdocode='"+sdocode+"')\n" +
					"GROUP BY meter_number)a,\n" +
					"(select mtrno,subdivision,kno,fdrcategory,sdocode from meter_data.master_main where sdocode='"+sdocode+"')b\n" +
					"where a.meter_number=b.mtrno order by a.meter_number)q LEFT JOIN \n" +
					"(select meter_sr_number,(cast (event_duration as varchar)) as event_duration  from meter_data.event_summary where month_year='"+rdngmonth+"' and  event_name like 'Power failure' and meter_sr_number in (select mtrno from meter_data.master_main \n" +
					" where sdocode='"+sdocode+"')) l on q.meter_number=l.meter_sr_number";
			try {
				pfData=postgresMdas.createNativeQuery(sql).getResultList();
			} catch (Exception e) {

				e.printStackTrace();
			}
		  savePfRptAnalysisData(pfData,rdngmonth);	
		}
	}
	private void savePfRptAnalysisData(List<?> pfData, String rdngmonth) {
		
		for (int i = 0; i < pfData.size(); i++) {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			Object[] pfrptdata=(Object[]) pfData.get(i);
			PowerFactorAnalysisEntity pfRptEntity=new PowerFactorAnalysisEntity();
			pfRptEntity.setOfficeId(pfrptdata[0]==null?"":pfrptdata[0].toString());
			pfRptEntity.setLocationType(pfrptdata[1]==null?"":pfrptdata[1].toString());
			pfRptEntity.setLocationCode(pfrptdata[2]==null?"":pfrptdata[2].toString());
			pfRptEntity.setMonthYear(rdngmonth);
			pfRptEntity.setMeterNumber(pfrptdata[3]==null?"":pfrptdata[3].toString());
			pfRptEntity.setRange1(pfrptdata[4]==null? null :Long.parseLong(pfrptdata[4].toString()));
			pfRptEntity.setRange2(pfrptdata[5]==null? null:Long.parseLong(pfrptdata[5].toString()));
			pfRptEntity.setRange3(pfrptdata[6]==null? null:Long.parseLong(pfrptdata[6].toString()));
			pfRptEntity.setRange4(pfrptdata[7]==null? null:Long.parseLong(pfrptdata[7].toString()));
			pfRptEntity.setNoLoadDuration(pfrptdata[8]==null? null:Long.parseLong(pfrptdata[8].toString()));
			pfRptEntity.setPowerOffDuration(pfrptdata[9]==null?"":pfrptdata[9].toString());
			pfRptEntity.setTimeStamp(timestamp);
			save(pfRptEntity);
			
		}
		
	}

	
}
