package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.FeederHealthEntity;
import com.bcits.service.FeederHealthService;
import com.bcits.utility.CalenderClass;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
@Repository
public class FeederHealthServiceImpl extends GenericServiceImpl<FeederHealthEntity> implements FeederHealthService {

	@Autowired CalenderClass calenderClass;
	/*@Override
	public void proceessFeederHealthData(String officeCode, List<String> meterList) {
		

		String meters="";
		String rdngmonth=String.valueOf(calenderClass.getPresentMonth());
		//System.err.println(officeCode+rdngmonth);
		
	
		
		 for(int i=0;i<meterList.size();i++)
		 {
			 if(i<meterList.size()-1)
			 {
				 meters+="'"+meterList.get(i)+"',";
			 }
			 else if(i==meterList.size()-1) 
			 {
				 meters+="'"+meterList.get(i)+"'";
			 }
		 }
			processFeederHealthData(officeCode,meters,rdngmonth);
		
		 //System.out.println(meters);
			
	}*/
/*	private void processFeederHealthData(String officeCode, String meters, String rdngmonth)  {

		//System.out.println("in the processs feeder HEALTH");
		List<Object[]> list=new ArrayList<>();
		String year=rdngmonth.substring(0, 4);
		String mnth=rdngmonth.substring(4,6);
		//ObjectMapper mapper=new ObjectMapper();
		//System.out.println(mapper.writeValueAsString(meters));
		String feederHealthQry="SELECT  *,(totalhours-powerontime) as poweroffduration FROM\n" +
				"(\n" +
				"SELECT  f.officeid,f.feedername,f.fdr_id,f.tp_fdr_id,f.meterno,l.monthlykwh,l.monthlykvah,round(l.monthlykwh/l.monthlykvah ,3) as pf,l.kva,l.read_time,l.kw,l.kvar_lag,l.i_r,l.i_y,l.i_b,round(l.avgkva/l.kva,3) as lf, l.days*24 as totalhours,((select max(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'YYYYMM')='"+rdngmonth+"' and to_char(rdate,'DD')=cast(l.days as TEXT)  and to_char(rdate,'HH24')='23'  \n" +
				"and meter_number in ("+meters+")\n" +
				")-(select min(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'YYYYMM')='"+rdngmonth+"' and to_char(rdate,'DD HH24')='01 00' \n" +
				"and meter_number in ("+meters+")\n" +
				"))/60 as powerontime,l.maxkvarLag,l.minkvarlag FROM\n" +
				"(\n" +
				"select feedername,fdr_id,tp_fdr_id,meterno,mf,parentid, officeid from meter_data.feederdetails \n" +
				"where officeid='"+officeCode+"' AND crossfdr='0'\n" +
				") f LEFT JOIN\n" +
				"(\n" +
				"SELECT a.*,b.monthlykwh,b.monthlykvah,b.avgkva,b.days,b.maxkvarLag,b.minkvarlag  FROM\n" +
				"(\n" +
				"SELECT meter_number,read_time,i_r,i_y,i_b,kva,kw,kvar_lag FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"'\n" +
				"AND  (meter_number,kva) iN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,sum(kwh) as monthlykwh,sum(kvah) as monthlykvah,avg(kva) as avgkva,((SELECT num_days("+year+", "+mnth+"))) as days,\"max\"(kvar_lag) as maxkvarLag , min(kvar_lag) as minkvarlag  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' AND EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))="+year+" GROUP BY  meter_number\n" +
				")b WHERE a.meter_number=b.meter_number\n" +
				") l ON f.meterno=l.meter_number\n" +
				") q LEFT JOIN\n" +
				"(\n" +
				"select meter_number, count(meter_number) as mtrcount from meter_data.events  where event_code='102' \n" +
				"and to_char(event_time,'MMYYYY')='"+rdngmonth+"' and meter_number in  ("+meters+")\n" +
				"GROUP BY meter_number\n" +
				")d ON q.meterno=d.meter_number;";
		String feederHealthQry="SELECT  *,(totalhours-powerontime) as poweroffduration FROM\n" +
				"(\n" +
				"SELECT  f.officeid,f.feedername,f.fdr_id,f.tp_fdr_id,f.meterno,l.monthlykwh,l.monthlykvah,round(l.monthlykwh/l.monthlykvah ,3) as pf,l.kva,l.read_time,l.kw,l.kvar_lag,l.i_r,l.i_y,l.i_b,round(l.avgkva/l.kva,3) as lf, l.days*24 as totalhours,((select max(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'YYYYMM')='201905' and to_char(rdate,'DD')=cast(l.days as TEXT)  and to_char(rdate,'HH24')='23'  \n" +
				"and meter_number in ('4613441','4613442','4613444','4613409')\n" +
				")-(select min(total_power_on_duration) from meter_data.amiinstantaneous  where to_char(read_time,'YYYYMM')='201905' and to_char(rdate,'DD HH24')='01 00' \n" +
				"and meter_number in ('4613441','4613442','4613444','4613409')\n" +
				"))/60 as powerontime,l.maxkvarLag,l.minkvarlag FROM\n" +
				"(\n" +
				"select feedername,fdr_id,tp_fdr_id,meterno,mf,parentid, officeid from meter_data.feederdetails \n" +
				"where officeid='2104630' AND crossfdr='0'\n" +
				") f LEFT JOIN\n" +
				"(\n" +
				"SELECT a.*,b.monthlykwh,b.monthlykvah,b.avgkva,b.days,b.maxkvarLag,b.minkvarlag  FROM\n" +
				"(\n" +
				"SELECT meter_number,read_time,i_r,i_y,i_b,kva,kw,kvar_lag FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"('4613441','4613442','4613444','4613409')\n" +
				"and to_char(read_time,'YYYYMM') ='201905'\n" +
				"AND  (meter_number,kva) iN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"('4613441','4613442','4613444','4613409')\n" +
				"and to_char(read_time,'YYYYMM') ='201905' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,sum(kwh) as monthlykwh,sum(kvah) as monthlykvah,avg(kva) as avgkva,((SELECT num_days(2019, 05))) as days,\"max\"(kvar_lag) as maxkvarLag , min(kvar_lag) as minkvarlag  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"('4613441','4613442','4613444','4613409')\n" +
				"and to_char(read_time,'YYYYMM') ='201905' AND EXTRACT('month' FROM date(read_time))=05 AND EXTRACT('year' FROM date(read_time))=2019 GROUP BY  meter_number\n" +
				")b WHERE a.meter_number=b.meter_number\n" +
				") l ON f.meterno=l.meter_number\n" +
				") q LEFT JOIN\n" +
				"(\n" +
				"select meter_number, count(meter_number) as mtrcount from meter_data.events  where event_code='102' \n" +
				"and to_char(event_time,'MMYYYY')='201905' and meter_number in  ('4613441','4613442','4613444','4613409')\n" +
				"GROUP BY meter_number\n" +
				")d ON q.meterno=d.meter_number;";
		try {
			list=postgresMdas.createNativeQuery(feederHealthQry).getResultList();
			//System.err.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				//System.err.println("in for loop");
				Object[] li=(Object[]) list.get(i);
				//mapper.writeValueAsString(li);
				//System.err.println(li[0].toString());
				FeederHealthEntity feeder=new FeederHealthEntity();
				feeder.setOfficeId(li[0].toString());
				feeder.setFeeder_id(li[2].toString());
				feeder.setTpFeederId(li[3].toString());
				feeder.setMonthyear(rdngmonth);
				feeder.setMeterNumber(li[4].toString());
				feeder.setKwh(Double.parseDouble(li[5].toString()));
				feeder.setKvah(Double.parseDouble(li[6].toString()));
				feeder.setPf(Double.parseDouble(li[7].toString()));
				feeder.setPeakKva(Double.parseDouble(li[8].toString()));
				feeder.setPeakKvaDate(calenderClass.convetStringToTimeStamp(li[9].toString()));
				feeder.setKw(Double.parseDouble(li[10].toString()));
				feeder.setKvar(Double.parseDouble(li[11].toString()));
				feeder.setIr((li[12] ==null? "" :li[12].toString()));
				feeder.setIy((li[13] ==null? "" :li[13].toString()));
				feeder.setIb((li[14] ==null? "" :li[14].toString()));
				feeder.setLf((li[15] ==null? Double.parseDouble("0"): Double.parseDouble(li[15].toString())));
				feeder.setMaxKvar((li[18] ==null? Double.parseDouble("0"):Double.parseDouble(li[18].toString())));
				feeder.setMinKvar((li[19] ==null? Double.parseDouble("0"):Double.parseDouble(li[19].toString())));
	     		feeder.setPower_off_duration((li[20] ==null? null:li[20].toString()));
				feeder.setPower_off_count((li[21] ==null?null:Long.parseLong(li[21].toString())));
				feeder.setTime_stamp(new Timestamp(System.currentTimeMillis()));
				save(feeder);
				//customSave(feeder);
				//System.err.println("hjashagdhgh"+feeder.getIb());
				//System.out.println("in savingggg");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}*/
	@Override
	public void proceessFeederHealthData(List<String> meterList,String rdngmonth) {
		// TODO Auto-generated method stub
		String meters="";
		Timestamp timestamp = new Timestamp(new Date().getTime());
		//String rdngmonth="201910";//String.valueOf(calenderClass.getPresentMonth());
		List<Object[]> list=new ArrayList<>();
		String year=rdngmonth.substring(0, 4);
		String mnth=rdngmonth.substring(4,6);
		for(Object item : meterList){
			if(item !=null){
			meters+="'"+item+"',";	
		}
		}
		if(meters.endsWith(","))
		{
			meters = meters.substring(0,meters.length() - 1);
		}
		String feederHealthQry="select * from \n" +
				"(SELECT f.officeid,f.fdr_id,f.tp_fdr_id,c.billmonth,c.kwh_imp,c.kvah_imp,(case when kvah_imp=0 then 0 else round((c.kwh_imp/c.kvah_imp),3) end ) as pf, a.*,b.maxkvar,b.minkvar,round((b.avgkva/(CASE WHEN a.kva=0  THEN 1 ELSE a.kva END)),3) as lf,f.tp_town_code,b.minkva   FROM\n" +
				"(\n" +
				"SELECT kva,read_time,kw,kvar_lag,i_r,i_y,i_b, meter_number FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"'\n" +
				"AND  (meter_number,kva) IN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,avg(kva) as avgkva,((SELECT meter_data.num_days("+year+", "+mnth+"))) as days,\"max\"(kvar_lag) as maxkvar , min(kvar_lag) as minkvar,min(kva) as minkva  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' and  kvar_lag>0 AND EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))="+year+" GROUP BY  meter_number\n" +
				")b,\n" +
				"(select mtrno,kwh_imp,kvah_imp,billmonth from meter_data.monthly_consumption where mtrno in ("+meters+") and billmonth='"+rdngmonth+"')c,\n" +
				"(select officeid,fdr_id,tp_fdr_id,feedername,meterno,mf,parentid,tp_town_code from meter_data.feederdetails)f\n" +
				"WHERE a.meter_number=b.meter_number and a.meter_number=c.mtrno and a.meter_number=f.meterno and b.meter_number=f.meterno and f.meterno=c.mtrno ) q LEFT JOIN \n" +
				"(select meter_sr_number,event_freq,(cast (event_duration as varchar)) as event_duration  from meter_data.event_summary where month_year='"+rdngmonth+"' and  event_name like 'Power failure' and meter_sr_number in ("+meters+" )) l on q.meter_number=l.meter_sr_number ORDER BY q.meter_number";			
	
		/*	String feederHealthQry="select * from \r\n" + 
				"(SELECT f.officeid,f.fdr_id,f.tp_fdr_id,c.billmonth,c.kwh_imp,c.kvah_imp,(case when kvah_imp=0 then 0 else round((c.kwh_imp/c.kvah_imp),3) end ) as pf, a.*,b.maxkvar,b.minkvar,round((b.avgkva/(CASE WHEN a.kva=0  THEN 1 ELSE a.kva END)),3) as lf,f.tp_town_code,b.minkva   FROM\r\n" + 
				"(\r\n" + 
				"SELECT kva,read_time,kw,kvar_lag,i_r,i_y,i_b, meter_number FROM meter_data.load_survey WHERE\r\n" + 
				"(meter_number, read_time) IN\r\n" + 
				"(\r\n" + 
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \r\n" + 
				"("+meters+")\r\n" + 
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"'\r\n" + 
				" GROUP BY  meter_number\r\n" + 
				")\r\n" + 
				")a, \r\n" + 
				"(\r\n" + 
				"select meter_number,avg(kva) as avgkva,((SELECT meter_data.num_days('"+year+"', '"+mnth+"'))) as days,\"max\"(kvar_lag) as maxkvar , min(kvar_lag) as minkvar,min(kva) as minkva  from meter_data.load_survey \r\n" + 
				"where  meter_number in \r\n" + 
				"("+meters+")\r\n" + 
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"'  AND EXTRACT('month' FROM date(read_time))='"+mnth+"' AND EXTRACT('year' FROM date(read_time))='"+year+"' GROUP BY  meter_number\r\n" + 
				")b,\r\n" + 
				"(select mtrno,kwh_imp,kvah_imp,billmonth from meter_data.monthly_consumption where mtrno in ("+meters+") and billmonth='"+rdngmonth+"')c,\r\n" + 
				"(select officeid,fdr_id,tp_fdr_id,feedername,meterno,mf,parentid,tp_town_code from meter_data.feederdetails)f\r\n" + 
				"WHERE a.meter_number=b.meter_number and a.meter_number=c.mtrno and a.meter_number=f.meterno and b.meter_number=f.meterno and f.meterno=c.mtrno ) q LEFT JOIN \r\n" + 
				"(select meter_sr_number,event_freq,(cast (event_duration as varchar)) as event_duration  from meter_data.event_summary where month_year='"+rdngmonth+"' and  event_name like 'Power failure' and meter_sr_number in ("+meters+")) l on q.meter_number=l.meter_sr_number ORDER BY q.meter_number";*/
		
		System.err.println(feederHealthQry);
				
		/*"select * from \n" +
				"(SELECT f.officeid,f.fdr_id,f.tp_fdr_id,c.billmonth,c.kwh_imp,c.kvah_imp,round((c.kwh_imp/c.kvah_imp),3) as pf, a.*,b.maxkvar,b.minkvar,round((b.avgkva/a.kva),3) as lf  FROM\n" +
				"(\n" +
				"SELECT kva,read_time,kw,kvar_lag,i_r,i_y,i_b, meter_number FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"(select meterno from meter_data.feederdetails)\n" +
				"and to_char(read_time,'YYYYMM') ='201901'\n" +
				"AND  (meter_number,kva) iN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"(select meterno from meter_data.feederdetails)\n" +
				"and to_char(read_time,'YYYYMM') ='201901' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,avg(kva) as avgkva,((SELECT num_days(2019, 01))) as days,\"max\"(kvar_lag) as maxkvar , min(kvar_lag) as minkvar  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"(select meterno from meter_data.feederdetails)\n" +
				"and to_char(read_time,'YYYYMM') ='201901' AND EXTRACT('month' FROM date(read_time))=01 AND EXTRACT('year' FROM date(read_time))=2019 GROUP BY  meter_number\n" +
				")b,\n" +
				"(select mtrno,kwh_imp,kvah_imp,billmonth from meter_data.monthly_consumption where mtrno in (select meterno from meter_data.feederdetails) and billmonth='201901')c,\n" +
				"(select officeid,fdr_id,tp_fdr_id,feedername,meterno,mf,parentid from meter_data.feederdetails)f\n" +
				"WHERE a.meter_number=b.meter_number and a.meter_number=c.mtrno and a.meter_number=f.meterno and b.meter_number=f.meterno and f.meterno=c.mtrno ) q LEFT JOIN \n" +
				"(select meter_sr_number,event_freq,(cast (event_duration as varchar)) as event_duration  from meter_data.event_summary where month_year='201901' and  event_name like 'Power failure' and meter_sr_number in (select meterno from meter_data.feederdetails )) l on q.meter_number=l.meter_sr_number ORDER BY q.meter_number";
				
				
			
		"SELECT f.officeid,f.fdr_id,f.tp_fdr_id,c.billmonth,c.kwh_imp,c.kvah_imp,round((c.kwh_imp/c.kvah_imp),3) as pf, a.*,b.maxkvar,b.minkvar,round((b.avgkva/a.kva),3) as lf,mc.mtrcount, b.days  FROM\n" +
				"(\n" +
				"SELECT kva,read_time,kw,kvar_lag,i_r,i_y,i_b, meter_number FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='201901'\n" +
				"AND  (meter_number,kva) iN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='201901' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,avg(kva) as avgkva,((SELECT num_days(2019, 01))) as days,\"max\"(kvar_lag) as maxkvar , min(kvar_lag) as minkvar  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='201901' AND EXTRACT('month' FROM date(read_time))=01 AND EXTRACT('year' FROM date(read_time))=2019 GROUP BY  meter_number\n" +
				")b,(select mtrno,kwh_imp,kvah_imp,billmonth from meter_data.monthly_consumption where mtrno in ("+meters+") and billmonth='201901')c,\n" +
				"(select officeid,fdr_id,tp_fdr_id,feedername,meterno,mf,parentid from meter_data.feederdetails)f,\n" +
				"(select meter_number, count(meter_number) as mtrcount from meter_data.events  where event_code='101' \n" +
				"and to_char(event_time,'MMYYYY')='012019' and meter_number in  ("+meters+")\n" +
				"GROUP BY meter_number)mc\n" +
				"WHERE a.meter_number=b.meter_number and a.meter_number=c.mtrno and a.meter_number=f.meterno and b.meter_number=f.meterno and f.meterno=c.mtrno and c.mtrno=mc.meter_number";
				*/
				/*"SELECT f.officeid,f.fdr_id,f.tp_fdr_id,c.billmonth,c.kwh_imp,c.kvah_imp,round((c.kwh_imp/c.kvah_imp),3) as pf, a.*,b.maxkvar,b.minkvar,round((b.avgkva/a.kva),3) as lf, b.days  FROM\n" +
				"(\n" +
				"SELECT kva,read_time,kw,kvar_lag,i_r,i_y,i_b, meter_number FROM meter_data.load_survey WHERE\n" +
				"(meter_number, read_time) IN\n" +
				"(\n" +
				"select meter_number, \"min\"(read_time) rdate  from meter_data.load_survey where meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"'\n" +
				"AND  (meter_number,kva) iN (select meter_number,\"max\"(kva) from meter_data.load_survey where meter_number  IN\n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' GROUP BY meter_number) GROUP BY  meter_number\n" +
				")\n" +
				")a, \n" +
				"(\n" +
				"select meter_number,avg(kva) as avgkva,((SELECT num_days("+year+", "+mnth+"))) as days,\"max\"(kvar_lag) as maxkvar , min(kvar_lag) as minkvar  from meter_data.load_survey \n" +
				"where  meter_number in \n" +
				"("+meters+")\n" +
				"and to_char(read_time,'YYYYMM') ='"+rdngmonth+"' AND EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))="+year+" GROUP BY  meter_number\n" +
				")b,(select mtrno,kwh_imp,kvah_imp,billmonth from meter_data.monthly_consumption where mtrno in ("+meters+") and billmonth='"+rdngmonth+"')c,\n" +
				"(select officeid,fdr_id,tp_fdr_id,feedername,meterno,mf,parentid from meter_data.feederdetails)f\n" +
				"WHERE a.meter_number=b.meter_number and a.meter_number=c.mtrno and a.meter_number=f.meterno and b.meter_number=f.meterno and f.meterno=c.mtrno";
		*/
	
		try {
			list=postgresMdas.createNativeQuery(feederHealthQry).getResultList();
			System.out.println(list.size());
			for(int i=0; i<list.size();i++) {
				Object[] li=(Object[]) list.get(i);
				FeederHealthEntity feeder=new FeederHealthEntity();
				feeder.setOfficeId(li[0].toString());
				feeder.setFeeder_id(li[1].toString());
				feeder.setTpFeederId(li[2].toString());
				feeder.setMonthyear(li[3].toString());
				feeder.setMeterNumber(li[14].toString());
				feeder.setKwh(Double.parseDouble(li[4].toString()));
				feeder.setKvah(Double.parseDouble(li[5].toString()));
				feeder.setPf(Double.parseDouble(li[6].toString()));
				feeder.setPeakKva(Double.parseDouble(li[7].toString()));
				feeder.setPeakKvaDate(calenderClass.convetStringToTimeStamp(li[8].toString()));
				feeder.setKw(Double.parseDouble(li[9].toString()));
				feeder.setKvar(Double.parseDouble(li[10].toString()));
				feeder.setIr((li[11] ==null? "" :li[11].toString()));
				feeder.setIy((li[12] ==null? "" :li[12].toString()));
				feeder.setIb((li[13] ==null? "" :li[13].toString()));
				feeder.setLf((li[17] ==null? null: Double.parseDouble(li[17].toString())));
				feeder.setMaxKvar((li[15] ==null? null:Double.parseDouble(li[15].toString())));
				feeder.setMinKvar((li[16] ==null? null:Double.parseDouble(li[16].toString())));
				System.out.println("in duration-----");
				feeder.setTownCode((li[18].toString()));
				feeder.setMinkva(Double.parseDouble(li[19].toString()));
	     		feeder.setPower_off_duration((li[22] ==null? null:li[22].toString()));
				feeder.setPower_off_count((li[21] ==null? null:Long.parseLong(li[21].toString())));
				feeder.setTime_stamp(timestamp);
				save(feeder);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	@Override 
	public List<Object[]> getFeederHealthReport(String zone, String circle,  String rdngmnth, String town){
		List<Object[]> list=new ArrayList<>();
		String year=rdngmnth.substring(0, 4);
		String mnth=rdngmnth.substring(4,6);
		
//		String sql="select (select ss_name from meter_data.substation_details where ss_id=b.parentid\n" +
//				"),b.feedername,b.mf,a.id,a.office_id,a.fdr_id,a.tp_fdr_id,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva*b.mf as kva,a.peak_kva_date,a.kw*b.mf as kw,a.kvar*b.mf as kvar,cast(a.i_r as NUMERIC)*b.ct_ratio as i_r,cast(a.i_y as NUMERIC)*b.ct_ratio as i_y,cast(a.i_b as NUMERIC)*b.ct_ratio as i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max*b.mf as kvar_max,a.kvar_min*b.mf as kvar_min from \n" +
//				"(select * from meter_data.feeder_health_rpt WHERE month_year='"+rdngmnth+"' and cast(office_id as NUMERIC) in (select distinct sitecode from meter_data.amilocation where  circle like '"+circle+"' and zone like '"+zone+"' and tp_towncode like '"+town+"'))a LEFT JOIN\n" +
//				"((select feedername,officeid,cast(m.mf as numeric),parentid,fdr_id,meterno,m,cast(m.ct_ratio as numeric) from meter_data.feederdetails f,meter_data.master_main m where f.meterno=m.mtrno ))b on a.fdr_id=b.fdr_id and a.meter_sr_number=b.meterno  order by a.meter_sr_number;";
				
		String sql = "select (select ss_name from meter_data.substation_details where ss_id=b.parentid\r\n" + 
				"),b.feedername,b.mf,a.id,a.office_id,a.fdr_id,a.tp_fdr_id,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva*b.mf as kva,a.peak_kva_date,a.kw*b.mf as kw,a.kvar*b.mf as kvar,cast(a.i_r as NUMERIC)*b.ct_ratio as i_r,cast(a.i_y as NUMERIC)*b.ct_ratio as i_y,cast(a.i_b as NUMERIC)*b.ct_ratio as i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max*b.mf as kvar_max,a.kvar_min*b.mf as kvar_min,max(a.peak_kva*b.mf) as max_kva,min(a.minkva*b.mf) as min_kva from \r\n" + 
				"(select * from meter_data.feeder_health_rpt WHERE month_year='"+rdngmnth+"' and cast(office_id as NUMERIC) in (select distinct sitecode from meter_data.amilocation where  circle like '"+circle+"' and zone like '"+zone+"' and tp_towncode like '"+town+"'))a LEFT JOIN\r\n" + 
				"((select feedername,officeid,cast(m.mf as numeric),parentid,fdr_id,meterno,m,cast(m.ct_ratio as numeric) from meter_data.feederdetails f,meter_data.master_main m where f.meterno=m.mtrno ))b on a.fdr_id=b.fdr_id and a.meter_sr_number=b.meterno  group by parentid,feedername,office_id,id,a.fdr_id,a.tp_fdr_id,mf,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva,a.peak_kva_date,a.kw,a.kvar,a.i_r,b.ct_ratio,a.i_y,a.i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max,a.kvar_min order by a.meter_sr_number";
		
		System.err.println("query---"+sql);
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
			return list;
		} catch (Exception e) {
			// TODO: handle exceptio
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void getFeederHealthReportPdf(HttpServletRequest request, HttpServletResponse response, String zone1, String crcl, String twn, String rdngmnth,String townname) {
		try {
			/*
			 * String zone = request.getParameter("zone"); String circle =
			 * request.getParameter("circle"); String town = request.getParameter("town");
			 * String rdngmnth = request.getParameter("rdngmnth");
			 */
			String zone="",circle="",town="";
			if(zone1=="%")
			{
				zone="ALL";
			}else {
				zone=zone1;
			}
			if(crcl=="%")
			{
				circle="ALL";
			}else {
				circle=crcl;
			}
			if(twn=="%")
			{
				town="ALL";
			}else {
				town=townname;
			}
			/*if(townname1.equalsIgnoreCase("ALL"))
			{
				townname="%";
			}else {
				townname=townname1;
			}*/
			
			String year=rdngmnth.substring(0, 4);
			String mnth=rdngmnth.substring(4,6);
			int yr = Integer.parseInt(year);
			int mon = Integer.parseInt(mnth);

			YearMonth yearMonthObjects = YearMonth.of(yr, mon);
			int daysInMonths = yearMonthObjects.lengthOfMonth();		
			int totalhourss=daysInMonths*24;

			
			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Feeder Health Report ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(4);
		    header.setWidths(new int[]{1,1,1,1});
		    header.setWidthPercentage(100);
		    PdfPCell headerCell=null;

		    headerCell = new PdfPCell(new Phrase("Region :"+zone,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+town,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+rdngmnth,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);	
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    document.add(header);
		    
		    
			List<Object[]> ViewAlarmsData=null;
			/*String sql="select (select ss_name from meter_data.substation_details where ss_id=b.parentid\n" +
					"),b.feedername,b.mf,a.id,a.office_id,a.fdr_id,a.tp_fdr_id,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva*b.mf as kva,a.peak_kva_date,a.kw*b.mf as kw,a.kvar*b.mf as kvar,cast(a.i_r as NUMERIC)*b.ct_ratio as i_r,cast(a.i_y as NUMERIC)*b.ct_ratio as i_y,cast(a.i_b as NUMERIC)*b.ct_ratio as i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max*b.mf as kvar_max,a.kvar_min*b.mf as kvar_min from \n" +
					"(select * from meter_data.feeder_health_rpt WHERE month_year='"+rdngmnth+"' and cast(office_id as NUMERIC) in (select distinct sitecode from meter_data.amilocation where  circle like '"+crcl+"' and zone like '"+zone1+"' and tp_towncode like '"+twn+"'))a LEFT JOIN\n" +
					"((select feedername,officeid,cast(m.mf as numeric),parentid,fdr_id,meterno,m,cast(m.ct_ratio as numeric) from meter_data.feederdetails f,meter_data.master_main m where f.meterno=m.mtrno ))b on a.fdr_id=b.fdr_id and a.meter_sr_number=b.meterno  order by a.meter_sr_number;";
					System.out.println("sql--->"+sql);*/
			
			String sql="select (select ss_name from meter_data.substation_details where ss_id=b.parentid\r\n" + 
					"),b.feedername,b.mf,a.id,a.office_id,a.fdr_id,a.tp_fdr_id,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva*b.mf as kva,a.peak_kva_date,a.kw*b.mf as kw,a.kvar*b.mf as kvar,cast(a.i_r as NUMERIC)*b.ct_ratio as i_r,cast(a.i_y as NUMERIC)*b.ct_ratio as i_y,cast(a.i_b as NUMERIC)*b.ct_ratio as i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max*b.mf as kvar_max,a.kvar_min*b.mf as kvar_min,max(a.peak_kva*b.mf) as max_kva,min(a.minkva*b.mf) as min_kva from \r\n" + 
					"(select * from meter_data.feeder_health_rpt WHERE month_year='"+rdngmnth+"' and cast(office_id as NUMERIC) in (select distinct sitecode from meter_data.amilocation where  circle like '"+crcl+"' and zone like '"+zone1+"' and tp_towncode like '"+twn+"'))a LEFT JOIN\r\n" + 
					"((select feedername,officeid,cast(m.mf as numeric),parentid,fdr_id,meterno,m,cast(m.ct_ratio as numeric) from meter_data.feederdetails f,meter_data.master_main m where f.meterno=m.mtrno ))b on a.fdr_id=b.fdr_id and a.meter_sr_number=b.meterno  group by parentid,feedername,office_id,id,a.fdr_id,a.tp_fdr_id,mf,a.month_year,a.meter_sr_number,a.kwh,a.kvah,a.pf,a.peak_kva,a.peak_kva_date,a.kw,a.kvar,a.i_r,b.ct_ratio,a.i_y,a.i_b,a.lf,a.power_off_duration,a.power_off_count,a.time_stamp,a.kvar_max,a.kvar_min order by a.meter_sr_number;\r\n" + 
					"";
			
			ViewAlarmsData=postgresMdas.createNativeQuery(sql).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(12);
	           parameterTable.setWidths(new int[]{1, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1,2});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("SubStation",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder TpId",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter number",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Mf",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("kWh",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("kVah",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Power Factor",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("kVA",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("kW",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("kVAr",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	        // SECOND TABLE
   			PdfPTable parameterTable1 = new PdfPTable(14);
   			parameterTable1.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});	
   			parameterTable1.setWidthPercentage(100);
   			PdfPCell parameterCell1;
	           
	           parameterCell1 = new PdfPCell(new Phrase("R-Phase Current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);

	           parameterCell1 = new PdfPCell(new Phrase("Y-Phase Current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("B-Phase Current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Date & Time",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Max kVAr",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Min kVAr",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Load Factor",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Interruptions",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Total Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Power Off Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Power On Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Availability",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Max KVA",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Min KVA",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	   		Date date1 = null;
	           for (int i = 0; i < ViewAlarmsData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						 Object[] obj=ViewAlarmsData.get(i);
						 String alarmdate = "";
		            		SimpleDateFormat parseFormat = new SimpleDateFormat(
		       		                 "yyyy-MM-dd HH:mm:ss");
		            		DecimalFormat decimalFormat = new DecimalFormat("#0.000");
		            		DecimalFormat decimalFormatter = new DecimalFormat("##.##");

		            			
		            			if(obj[13] != null)
		            			{
		       		             date1 = parseFormat.parse(obj[13].toString());
		       		             alarmdate = parseFormat.format(date1); 
		            			}
		            			String availability="";
		            			String powerOnDuration="";
		            			String poweroffes="";
		            			double poweroff=0;
		            			String a=(String) obj[20];
		            			if(a!=null) {
		            			String years=rdngmnth.substring(0, 4);
		            			String mnths=rdngmnth.substring(4,6);
		            			int yrs = Integer.parseInt(years);
		            			int mons = Integer.parseInt(mnths);

		            			YearMonth yearMonthObject = YearMonth.of(yrs, mons);
		            			double daysInMonth = yearMonthObject.lengthOfMonth();
		            			//System.err.println(daysInMonth);
		            			double totalhours=daysInMonth*24;
		            			//System.err.println("+total hours+"+totalhours);
		            			Pattern p = Pattern.compile("\\d+");
		            			Matcher m = p.matcher(a);
		            			List<Double> ab=new ArrayList<Double>();
		            			while(m.find()) {
		            			String s="";
		            			// System.out.println(m.group());
		            			s=m.group();
		            			ab.add(Double.parseDouble(s));
		            			}
		            			double poweroffs=0;
		            			//String poweroffes="";
		            			 poweroff=0;
		            			double powerOnDurations=0;
		            			//String powerOnDuration="";
		            			double availabilitys=0;
					/*
					 * System.out.println("days==== "+ab.get(0));
					 * System.out.println("hours==== "+ab.get(1));
					 * System.out.println("minutes==== "+ab.get(2));
					 * System.out.println("seconds==== "+ab.get(3));
					 */
		            			 poweroffs=(ab.get(0)*24)+ab.get(1)+(ab.get(2)/60);
		            			 poweroffes = decimalFormatter.format(poweroffs);
		            			poweroff = Double.parseDouble(poweroffes);
		            			//System.out.println("powerffDuration "+poweroff);
		            			 powerOnDurations=(totalhours-poweroff);
		            			powerOnDuration=decimalFormatter.format(powerOnDurations);
		            			//System.out.println("powerOnDuration="+powerOnDuration);
		            			availabilitys=(powerOnDurations/totalhours)*100;
		            			availability = decimalFormatter.format(availabilitys);
		            			//System.err.println("availability====="+(powerOnDurations/totalhours));
		            			//System.err.println("availability====="+availabilitys);
		            			
		            			}
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 ///parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							
							 parameterCell = new PdfPCell(new Phrase(obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( decimalFormat.format((obj[12]==null?null:obj[12]))+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( decimalFormat.format((obj[14]==null?null:obj[14]))+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(decimalFormat.format((obj[15]==null?null:obj[15]))+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable.addCell(parameterCell);
							 
							 //second
							 parameterCell1 = new PdfPCell(new Phrase( obj[16]==null?null:obj[16]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[17]==null?null:obj[17]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
						
							 parameterCell1 = new PdfPCell(new Phrase( obj[18]==null?null:obj[18]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[13]==null?null:alarmdate+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(decimalFormat.format((obj[23]==null?null:obj[23]))+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(decimalFormat.format( obj[24]==null?null:obj[24])+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[19]==null?null:obj[19]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[21]==null?null:obj[21]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							// parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 //h
							 parameterCell1 = new PdfPCell(new Phrase( obj[14]==null?null:totalhourss+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[20]==null?null:poweroff+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							// parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase((obj[20]==null?null:powerOnDuration+""),new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase( obj[20]==null?null:availability+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(decimalFormat.format((obj[25]==null?null:obj[25]))+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(decimalFormat.format( obj[26]==null?null:obj[26])+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 //parameterCell1.setFixedHeight(25f);
							 parameterTable1.addCell(parameterCell1);
							 
						/*
						 * parameterCell1 = new PdfPCell(new Phrase( obj[20]==null?null:obj[14]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * //parameterCell1.setFixedHeight(25f);
						 * parameterTable1.addCell(parameterCell1);
						 * 
						 * parameterCell1 = new PdfPCell(new Phrase( obj[20]==null?null:obj[14]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * //parameterCell1.setFixedHeight(25f);
						 * parameterTable1.addCell(parameterCell1);
						 * 
						 * parameterCell1 = new PdfPCell(new Phrase( obj[20]==null?null:obj[14]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * //parameterCell1.setFixedHeight(25f);
						 * parameterTable1.addCell(parameterCell1);
						 */
							  
	            		//}
	            	}
	            }
	            }
                          document.add(parameterTable);
                          document.add(parameterTable1);

	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=FeederHealthReport.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	            
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
	
