package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.entity.DTHealthEntity;
import com.bcits.service.DTHealthService;
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
public class DTHealthServiceImpl extends GenericServiceImpl<DTHealthEntity> implements DTHealthService {
	@Autowired CalenderClass calenderClass;
	@Override
	public void proceessDTHealthData() {
		String meters="";
		//String rdngmonth=String.valueOf(calenderClass.getPresentMonth());
		String rdngmonth=String.valueOf(calenderClass.getPrevMonth());
		//String rdngmonth="201908";
		//System.err.println(officeCode+rdngmonth);
		
		/*
		 * for(int i=0;i<meterList.size();i++) { if(i<meterList.size()-1) {
		 * meters+="'"+meterList.get(i)+"',"; } else if(i==meterList.size()-1) {
		 * meters+="'"+meterList.get(i)+"'"; } }
		 */
		 //System.out.println(officeCode+"-"+meters+"-"+rdngmonth);	
		 processDTHealthData(rdngmonth);
	}
	
	
	@Override
	public void manualproceessDTHealthData(String rdngmonth) {
		 String meters="";
		 //String rdngmonth=String.valueOf(calenderClass.getPrevMonth());
		 //String rdngmonth=month;
		 processDTHealthData(rdngmonth);

		// processDTHealthData("201909");
		// processDTHealthData("201910");
		// processDTHealthData("201908");
		// processDTHealthData("201907");
			
	}
	

	
	@Override
	public void processDTHealthData(String rdngmonth) {
				List<Object[]> list=new ArrayList<>();
				String year=rdngmonth.substring(0, 4);
				String mnth=rdngmonth.substring(4,6);
				/*String mon = mnth;
				int sum =1;
				String nextmon="";
				sum += Integer.parseInt(mon);
				int length = String.valueOf(sum).length();
				if(sum==13)
				{
				 sum = 01;
				 int y1 = 1;
				 String ey = year.substring(0, 2);
			     String year1= year.substring(2);
				 y1 += Integer.parseInt(year1);
				 year = ey+y1+"0"+sum;
				 nextmon = year ;
				}
				
				else if(length==1){
					nextmon = year +"0"+ sum;
				}
				else {
					nextmon = year + sum;
				}
				*/
				
				
				ObjectMapper mapper=new ObjectMapper();
				
				
				
				String DThealthqry = "Select thirdcalc.* ,(CASE WHEN ovrldinhrs > 8 THEN 't' ELSE 'f' END) as overloaded ,power_on_max,total_hrs_max,power_off_max from (\r\n" + 
						"Select secondcalc.* ,\r\n" + 
						"(ovrldtemp*30)/60  as ovrldinhrs ,\r\n" + 
						"(CASE WHEN (rphaseunbalnce-yphaseunbalance)>50 OR (yphaseunbalance-bphaseunbalnce)>50 OR (bphaseunbalnce-rphaseunbalnce	)>50 then 't' ELSE 'f' END) as unbalance,\r\n" + 
						"((meter_data.num_days("+year+","+mnth+"))*24) as total_hours from\r\n" + 
						"(\r\n" + 
						"Select firstcal.*,\r\n" + 
						"	round((i_r-allAvg)/NULLIF(allAvg,0),4) as rphaseunbalnce,\r\n" + 
						"	round((i_y-allAvg)/NULLIF(allAvg,0),4) as yphaseunbalance, \r\n" + 
						"	round((i_b-allAvg)/NULLIF(allAvg,0),4) as bphaseunbalnce,\r\n" + 
						"	(CASE WHEN maxkva>kva_rating THEN 't' else 'f' END) as overload ,\r\n" + 
						"	COUNT(CASE WHEN maxkva<twe_per THEN 1 END) as val_0_20,\r\n" + 
						"	COUNT(CASE WHEN maxkva>twe_per AND maxkva<seve_per THEN 1 END) as val_0_70,\r\n" + 
						"	COUNT(CASE WHEN (maxkva)>((kva_rating*70)/100) AND (maxkva<kva_rating) THEN 1 END) as ovrldtemp,\r\n" + 
						"	round((avg_kva/nullif (maxkva,0)),4) as  load_factor,\r\n" + 
						"	round((maxkva/nullif (kva_rating,0)),4) as utilization_factor,\r\n" + 
						"	(CASE WHEN maxkva>0 AND maxkva<=twe_per THEN 't'  \r\n" + 
						"	WHEN maxkva>twe_per THEN 'f' END) as underload\r\n" + 
						"from (\r\n" + 
						"	Select  location_id as dttpid,kva_rating,officeid,\r\n" + 
						"	round(avg(NULLIF(ir, 0)),7)as i_r ,round(avg(NULLIF(iy, 0)),7) as i_y ,round(avg(NULLIF(ib, 0)),7) as i_b , sum(kwh) as kwh,sum(kvah) as kvah,\r\n" + 
						"	round((sum(kwh)/nullif (sum(kvah),0)),7) as pf,\r\n" + 
						"	round((avg(ir)+avg(iy)+avg(ib))/3,4) as allAvg, \r\n" + 
						"	round(avg(kva),4) as avg_kva,\r\n" + 
						"	max(kva) as maxkva,\r\n" + 
						"	sum(case WHEN abs(kva/kva_rating)*100 <= 20 THEN 30 END)/60 as range1,\r\n" + 
						"	sum(case WHEN abs((kva/kva_rating)*100) >20 AND abs((kva/kva_rating)*100) <=70 THEN 30 END)/60 as range2,\r\n" + 
						"	sum(case WHEN abs((kva/kva_rating)*100) >70 AND abs(kva/kva_rating)*100 <=100 THEN 30 END)/60 as range3,\r\n" + 
						"	sum(case WHEN abs((kva/kva_rating)*100) >100  THEN 30 END)/60 as range4,\r\n" + 
						"	COUNT(CASE WHEN kva=0 THEN 1 END) as no_load,((kva_rating*20)/100) as twe_per,((kva_rating*70)/100) as seve_per,x1.tp_town_code\r\n" + 
						"	from ( \r\n" + 
						"	Select location_id ,dtd.tp_town_code, max(kva_rating) as kva_rating ,officeid from (\r\n" + 
						"	Select DISTINCT location_id,MAX(dtcapacity)as  kva_rating,officeid,tp_town_code from (\r\n" + 
						"	Select location_id,dtcapacity,mm.mf,dt.officeid,dt.tp_town_code from meter_data.master_main mm\r\n" + 
						"	LEFT JOIN \r\n" + 
						"	meter_data.dtdetails dt on dt.dttpid = mm.location_id where fdrcategory = 'DT' and  town_code like '%')x1 GROUP BY location_id,mf,officeid,tp_town_code\r\n" + 
						"	) dtd\r\n" + 
						"	left join (\r\n" + 
						"	Select  dttpid from meter_data.load_survey_dt where billmonth = '"+rdngmonth+"' GROUP BY dttpid,kva ) ldt \r\n" + 
						"	on ldt.dttpid = dtd.location_id GROUP BY location_id,officeid,tp_town_code )x1 \r\n" + 
						"	left JOIN (\r\n" + 
						"	Select * from meter_data.load_survey_dt where billmonth = '"+rdngmonth+"' )x2 on x1.location_id = x2. dttpid GROUP BY location_id,kva_rating,officeid,x1.tp_town_code order by dttpid)firstcal \r\n" + 
						"GROUP BY dttpid,kva_rating,	i_r,i_y,i_b,kwh,kvah,pf,allAvg,avg_kva,maxkva,range1,range2,range3,range4,no_load,twe_per,seve_per,officeid,tp_town_code)secondcalc )thirdcalc\r\n" + 
						"left join (\r\n" + 
						"Select z.* from \r\n" + 
						"(\r\n" + 
						"Select distinct on (location_id) location_id,max(power_on) as power_on_max,monthyear,max(total_hrs) as total_hrs_max, max(power_off) as power_off_max from meter_data.master_main dt1 \r\n" + 
						"LEFT JOIN (\r\n" + 
						"SELECT a.*,total_hrs-power_off as power_on FROM\r\n" + 
						"(\r\n" + 
						"SELECT meter_sr_number,\r\n" + 
						"to_char(event_restore_date,'YYYYMM') as monthyear,\r\n" + 
						"meter_data.num_days("+year+","+mnth+")*24 as total_hrs,\r\n" + 
						"date_part('hour' ,SUM(event_restore_date-event_occ_date)) as power_off\r\n" + 
						"FROM  meter_data.event_details_mv_from_march WHERE meter_sr_number IN(select distinct mtrno from meter_data.master_main where sdocode like '%' and fdrcategory like 'DT') \r\n" + 
						"and event_name ='Power failure   ' and  to_char(event_restore_date,'YYYYMM')='"+rdngmonth+"' GROUP BY meter_sr_number,monthyear,total_hrs\r\n" + 
						")a)xa on xa.meter_sr_number = dt1.mtrno  where town_code like '%' GROUP BY location_id,monthyear)z\r\n" + 
						")finalpower on thirdcalc.dttpid = finalpower.location_id " ;
				
				
				
				/*String DThealthqry="SELECT f.meter_number,f.avg_ir,f.avg_iy,f.avg_ib,abs(f.rph_unbal) as rph_unbal,abs(f.yph_unbal) as yph_unbal,abs(f.bph_unbal) as bph_unbal,f.lf,f.uf,f.total_hours,f.range_1,f.range_2,f.range_3,f.range_4,f.overload,f.val_0_20,f.val_0_70,  f.officeid,f.dt_id,f.dttpid,\n" +
						"(CASE WHEN (rph_unbal-yph_unbal)>50 OR (yph_unbal-bph_unbal)>50 OR (bph_unbal-rph_unbal)>50 then 't' ELSE 'f' END) as unbalance ,f.kwh,f.kvah,f.pf,f.overld2,f.underload,f.total_hrs,f.power_off,f.power_on,f.load_duration,f.kva_rating,f.i_p\n" +
						"FROM\n" +
						"(SELECT d.meter_number, round(d.avg_ir,4) as avg_ir,round(d.avg_iy,4) as avg_iy,round(d.avg_ib,4) as avg_ib,round(((d.avg_ir-d.all_avg) / d.all_avg),4) as rph_unbal,round(((d.avg_iy-d.all_avg) / d.all_avg),4) as yph_unbal,round(((d.avg_ib-d.all_avg) / d.all_avg),4) as bph_unbal,round(d.load_factor,4) as lf,round(d.uf,4) as uf,d.total_hours,d.range_1,d.range_2,d.range_3,d.range_4,d.overload,d.val_0_20,d.val_0_70, d.officeid,d.dt_id,d.dttpid,d.kwh,d.kvah,d.pf,d.overld2,d.underload,total_hrs,power_on,power_off,d.load_duration,COALESCE(d.kva_rating,'0') as kva_rating,d.i_p\n" +
						"from\n" +
						"(\n" +
						"SELECT b.meter_number,b.avg_ir,b.avg_iy,b.avg_ib, ((b.avg_ir+b.avg_iy+b.avg_ib)/3) as all_avg,b.load_factor,b.uf,b.total_hours,b.range_1,b.range_2,b.range_3,b.range_4 ,b.overload,b.val_0_20,b.val_0_70, b.officeid,b.dt_id,b.dttpid,b.kwh,b.kvah,b.pf,b.overld2,b.underload,b.load_duration,b.kva_rating,b.i_p from\n" +
						"(\n" +
						"SELECT c.meter_number, c.i_r*mf as avg_ir,c.i_y*mf as avg_iy,c.i_b*mf  as avg_ib,(c.avg_kva/c.max_kva) as  load_factor\n" +
						",((c.max_kva)/kva_rating)as uf,c.total_hours,c.i_p,range_1,range_2,range_3,range_4,\n" +
						"--(c.range1*CAST(c.i_p as NUMERIC))/60  as range_1 ,\n" +
						"--(c.range2 *CAST(c.i_p as NUMERIC))/60  as range_2 ,\n" +
						"--(c.range3 *CAST(c.i_p as NUMERIC))/60  as range_3 ,\n" +
						"--(c.range4 *CAST(c.i_p as NUMERIC))/60 as range_4 ,\n" +
						"c.overload,c.val_0_20,c.val_0_70 , c.officeid,c.dt_id,c.dttpid,c.kwh,c.kvah,c.pf,c.overld2,c.underload,c.load_duration,c.kva_rating FROM\n" +
						"(\n" +
						"SELECT meter_number, NULLIF((Y.i_r),0) as i_r,NULLIF((Y.i_y),0) as i_y, NULLIF((Y.i_b),0) as i_b,\n" +
						"((meter_data.num_days("+year+","+mnth+"))*24) as total_hours, max(Y.dtcapacity) as dtcapacity,max(Y.mf) as mf, officeid,dt_id,dttpid,kva_rating,range1 as range_1,range2 as range_2 ,range3 as range_3,range4 as range_4,\n" +
						"--count(CASE WHEN per<20 THEN per END) as range1,\n" +
						"--count(CASE WHEN per>=20 AND per<70 THEN 1 END ) as range2,\n" +
						"--count(CASE WHEN per>=70 AND per<100 THEN 1 end) as range3,\n" +
						"--count(CASE WHEN per>=100 THEN 1 END) as range4,\n" +
						"Y.overload,Y.val_0_20,Y.val_0_70,y.kwh,y.kvah,y.pf,y.overld2,y.underload,y.load_duration,y.i_p,max_kva,avg_kva\n" +
						"from\n" +
						"(\n" +
						"SELECT meter_number,i_r,i_y,i_b,dtcapacity,mf, officeid,dt_id,dttpid,kva_rating,kwh,kvah,pf,load_duration,i_p,\n" +
						"(CASE WHEN max_kva>dtcapacity THEN 't' else 'f' END) as overload ,\n" +
						"COUNT(CASE WHEN max_kva<twe_per THEN 1 END) as val_0_20,\n" +
						"COUNT(CASE WHEN max_kva>twe_per AND max_kva<seve__per THEN 1 END) as val_0_70,\n" +
						"(CASE WHEN ovrld2>8 THEN 't' ELSE 'f' END) as overld2,\n" +
						"(CASE WHEN max_kva>0 AND max_kva<=twe_per THEN 't'  \n" +
						"			WHEN max_kva>twe_per THEN 'f' END) as underload,max_kva,avg_kva,range1,range2,range3,range4\n" +
						"FROM(\n" +
						"SELECT  officeid,dt_id,dttpid,kva_rating,meter_number,i_r,i_y,i_b,dtcapacity,mf,cast(meter_ip_period as NUMERIC) as i_p,\n" +
						"((x.dtcapacity*20)/100) as twe_per, ((x.dtcapacity*70)/100) as seve__per,\n" +
						"(ovrld2*30)/60  as ovrld2,\n" +
						"(no_load*30)/60  as load_duration,max_kva,avg_kva,range1,range2,range3,range4\n" +
						"FROM\n" +
						"(\n" +
						"SELECT officeid,dt_id,dttpid,dtcapacity as kva_rating, meter_number,\n" +
						"avg(i_r) i_r,avg(i_y) i_y,avg(i_b) i_b,maxkva as max_kva,avg(kva) as avg_kva ,max(dtcapacity) dtcapacity, max(mf) mf,\n" +
						"sum(case WHEN abs(((kva)-(dtcapacity))/(dtcapacity))*100 < 20 THEN 30 END)/60 as range1,\n" +
						"sum(case WHEN abs(((kva)-(dtcapacity))/(dtcapacity))*100 >=20 AND abs(((kva)-(dtcapacity))/(dtcapacity))*100 <70 THEN 30 END)/60 as range2,\n" +
						"sum(case WHEN abs(((kva)-(dtcapacity))/(dtcapacity))*100 >=70 AND abs(((kva)-(dtcapacity))/(dtcapacity))*100 <100 THEN 30 END)/60 as range3,\n" +
						"sum(case WHEN abs(((kva)-(dtcapacity))/(dtcapacity))*100 >=100  THEN 30 END)/60 as range4,\n" +
						"COUNT(CASE WHEN (maxkva)>((dtcapacity*70)/100) AND (maxkva<dtcapacity) THEN 1 END) as ovrld2,\n" +
						"COUNT(CASE WHEN kva=0 THEN 1 END) as no_load\n" +
						"FROM\n" +
						"(\n" +
						"SELECT a.*,ss.maxkva FROM \n" +
						"(\n" +
						"SELECT meterno as meter_number, \n" +
						"NULLIF(i_r,0) as i_r,NULLIF(i_y,0) as i_y,NULLIF(i_b,0) as i_b,NULLIF(kva,0) as kva\n" +
						"from(\n" +
						"(select d.*, mn.meterno from (select dates from generate_series(\n" +
						"CAST(to_date('"+rdngmonth+"','YYYYMM')as TIMESTAMP),\n" +
						"CAST(to_date('"+nextmon+"','YYYYMM')as TIMESTAMP),\n" +
						"interval '30 min')  AS dates where to_char(dates,'YYYYMM')!= '"+nextmon+"')d\n" +
						"cross join ( select distinct mtrno as meterno from meter_data.master_main where mtrno is not null and mtrno<>'' and fdrcategory in('DT')) mn)a\n" +
						"LEFT JOIN\n" +
						"(select meter_number,read_time,NULLIF(i_r,0) as i_r,NULLIF(i_y,0) as i_y,NULLIF(i_b,0) as i_b,NULLIF(kva,0) as kva from meter_data.load_survey l left JOIN  meter_data.master_main m\n" +
						"on l.meter_number=m.mtrno   where   m.fdrcategory LIKE 'DT'  and  \n" +
						"to_char(read_time,'YYYYMM')= '"+rdngmonth+"'  order by read_time)b on a.dates=b.read_time and a.meterno=b.meter_number)x\n" +
						") a,\n" +
						"(\n" +
						"select distinct mtrno,max(kva) maxkva from meter_data.master_main m left JOIN  meter_data.load_survey l\n" +
						"on m.mtrno=l.meter_number     AND   i_r>0 and i_y>0 and i_b>0 and\n" +
						" to_char(read_time,'YYYYMM')= '"+rdngmonth+"'  where    m.fdrcategory LIKE 'DT'\n" +
						"GROUP BY m.mtrno, to_char(read_time,'YYYYMM') \n" +
						" )ss WHERE a.meter_number=ss.mtrno\n" +
						" )bb\n" +
						"LEFT JOIN \n" +
						"(\n" +
						"SELECT * from meter_data.dtdetails WHERE meterno in (select distinct mtrno from meter_data.master_main where sdocode like '%' and fdrcategory like 'DT') \n" +
						")d on bb.meter_number=d.meterno GROUP BY officeid,dt_id,dttpid,meter_number,dtcapacity,maxkva\n" +
						")x \n" +
						"LEFT JOIN\n" +
						"(\n" +
						"SELECT (case when COALESCE(meter_ip_period,'')='' THEN '30' ELSE meter_ip_period END)  AS meter_ip_period,meterno FROM meter_data.meter_inventory WHERE meterno in (select distinct mtrno from meter_data.master_main where sdocode like '%' and fdrcategory like 'DT') \n" +
						")aa ON aa.meterno=x.meter_number\n" +
						")Z  \n" +
						"LEFT JOIN\n" +
						"(SELECT kwh,kvah,round((kwh_val/kvah_val),3) as pf,u.mtrno from\n" +
						"(\n" +
						"SELECT t.kwh, t.kvah,t.mtrno,\n" +
						"(case when  kwh ='0' THEN NULL ELSE kwh END) as kwh_val,\n" +
						"(case when cast(kvah as NUMERIC)='0'  THEN NULL ELSE kvah END) as kvah_val\n" +
						"FROM\n" +
						"(\n" +
						"SELECT kwh_imp as kwh,kvah_imp as kvah,mtrno from meter_data.monthly_consumption  WHERE billmonth='"+rdngmonth+"' and mtrno in(select distinct mtrno from meter_data.master_main where sdocode like '%' and fdrcategory like 'DT')   \n" +
						")t GROUP BY t.kwh,t.kvah,t.mtrno\n" +
						")u)s on s.mtrno=z.meter_number GROUP BY z.meter_number,z.i_r,z. i_y,z.i_b,z.dtcapacity,z.mf, z.officeid,z.dt_id,z.dttpid,s.kwh,s,kvah,s.pf,ovrld2,z.twe_per,z.seve__per,z.load_duration,z.kva_rating,z.i_p ,z.max_kva,z.avg_kva,range1,range2,range3,range4\n" +
						")Y GROUP BY meter_number,y.overload,Y.val_0_20,Y.val_0_70, Y.officeid,Y.dt_id,Y.dttpid,y.kwh,y.kvah,y.pf,y.overld2,y.underload,y.load_duration,y.kva_rating,y.i_p,i_r,i_y,i_b,Y.max_kva,Y.avg_kva,range1,range2,range3,range4\n" +
						")c\n" +
						")b\n" +
						")d\n" +
						"LEFT JOIN\n" +
						"(SELECT a.*,total_hrs-power_off as power_on FROM\n" +
						"(\n" +
						"SELECT meter_sr_number,\n" +
						"to_char(event_restore_date,'YYYYMM') as monthyear,\n" +
						"meter_data.num_days("+year+","+mnth+")*24 as total_hrs,\n" +
						"date_part('hour' ,SUM(event_restore_date-event_occ_date)) as power_off\n" +
						"FROM meter_data.event_details WHERE meter_sr_number IN(select distinct mtrno from meter_data.master_main where sdocode like '%' and fdrcategory like 'DT') \n" +
						" and trim(event_name)='Power failure' and  to_char(event_restore_date,'YYYYMM')='"+rdngmonth+"' GROUP BY meter_sr_number,monthyear,total_hrs\n" +
						")a\n" +
						")hrs ON d.meter_number=hrs.meter_sr_number\n" +
						")f";*/
				
				System.err.println(DThealthqry);
				try {
					list=postgresMdas.createNativeQuery(DThealthqry).getResultList();
					
					for (int i = 0; i < list.size(); i++) {
						//System.err.println("in for loop");
						Object[] li=(Object[]) list.get(i);
						//mapper.writeValueAsString(li);
						System.out.println("in for"+li[25]);
						System.err.println("in for===="+list.size()+mapper.writeValueAsString(li));
						boolean overload = false;//='\0';
						String overload1=li[23].toString();
						String overload2=li[33].toString();
						System.err.println("1==="+overload1+"++++++"+overload2);
						if(overload1.charAt(0)=='t' && overload2.charAt(0)=='t')
						{
							overload=true;
						}
						if(overload1.charAt(0)=='t' && overload2.charAt(0)=='f')
						{
							overload=true;
						}
						if(overload1.charAt(0)=='f' && overload2.charAt(0)=='t')
						{
							overload=true;
						}
						if(overload1.charAt(0)=='f' && overload2.charAt(0)=='f')
						{
							overload=false;
						}


							 Double dou=Double.parseDouble(li[16].toString());
							// System.out.println(dou);
							 
							 //Long lon=Long.parseLong(li[10].toString());
						
						DTHealthEntity dt=new DTHealthEntity();
						
						
						dt.setOfficeId((li[2]==null?null:(li[2].toString())));
						dt.setTpDtId(li[0].toString());
						dt.setMonthYear(rdngmonth);
						dt.setAvgCurrRph((li[3]==null?null:Double.parseDouble(li[3].toString())));
						dt.setAvgCurrYph((li[4]==null?null:Double.parseDouble(li[4].toString())));
						dt.setAvgCurrBph((li[5]==null?null:Double.parseDouble(li[5].toString())));
						dt.setUnbalanceRph((li[20]==null?null:Double.parseDouble(li[20].toString())));
						dt.setTp_town_code(li[19]==null?null:(li[19].toString()));
						dt.setUnbalanceYph((li[21]==null?null:Double.parseDouble(li[21].toString())));
						dt.setUnbalanceBph((li[22]==null?null:Double.parseDouble(li[22].toString())));
						dt.setLf((li[27]==null?null:Double.parseDouble(li[27].toString())));
						dt.setUf((li[28]==null?null:Double.parseDouble(li[28].toString())));
						dt.setOverload(overload);
						if(li[31]!=null) {
							if(li[31].toString().equalsIgnoreCase("f")) {
								dt.setUnbalance(false);
							}
							else {
								dt.setUnbalance(true);
							}
							
						}
						//dt.setUnbalance(li[20]==null?null:Boolean.parseBoolean(li[20].toString()) );
						dt.setPowerOfDuration((li[36]==null?"":li[36].toString()));
						dt.setNoLoadDuration(li[16]==null?null:dou);
						dt.setTime_stamp(new Timestamp(System.currentTimeMillis()));
						dt.setPf((li[8]==null?null:Double.parseDouble(li[8].toString())));
						dt.setPowerOnDuration((li[34]==null?"":li[34].toString()));
						dt.setTotalDuration((li[32]==null?"":li[32].toString()));
						dt.setKwh((li[6]==null?null:Double.parseDouble(li[6].toString())));
						dt.setKvah((li[7]==null?null:Double.parseDouble(li[7].toString())));
						dt.setRange1((li[12]==null?null:Double.parseDouble(li[12].toString())));
						dt.setRange2((li[13]==null?null:Double.parseDouble(li[13].toString())));
						dt.setRange3((li[14]==null?null:Double.parseDouble(li[14].toString())));
						dt.setRange4((li[15]==null?null:Double.parseDouble(li[15].toString())));
						dt.setKva_rating((li[1]==null?null:Double.parseDouble(li[1].toString())));
						if(li[29]!=null) {
							if(li[29].toString().equalsIgnoreCase("f")) {
								dt.setUnderload(false);
							}
							else {
								dt.setUnderload(true);
							}
							//dt.setUnderload(Boolean.parseBoolean(li[25].toString()));
						}
						
						
						save(dt);
					//	System.out.println("dt----------------"+dt);
						//customSave(feeder);
						//System.err.println("hjashagdhgh"+feeder.getIb());
						//System.out.println("in savingggg");
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}
	@Override
	public List<?> getDTHealthReport(String zone,String subdiv, String rdngmnth, String circle, String division,String town,String towncode) {
		List<?> list = new ArrayList();
		try {
//			String sql = "Select  dth.*,dtmm.* from meter_data.dt_health_rpt dth \n" +
//						"right join \n" +
//						"(\n" +
//						"Select distinct substation ,string_agg(distinct fdrname,',' ORDER BY fdrname) fdrname ,string_agg(distinct customer_name,',' ORDER BY customer_name)customer_name ,location_id  from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+towncode+"' GROUP BY substation ,fdrname,location_id,town_code )dtmm\n" +
//						"on dth.tp_dt_id = dtmm.location_id where month_year = '"+rdngmnth+"'  ";
			
			String sql="Select distinct dth.*,dtmm.* from meter_data.dt_health_rpt dth \r\n" + 
					"right join \r\n" + 
					"(\r\n" + 
					"Select distinct mm.substation ,string_agg(distinct mm.fdrname,',' ORDER BY mm.fdrname)fdrname ,string_agg(distinct mm.customer_name,',' ORDER BY mm.customer_name)customer_name ,mm.location_id,ami.town_ipds  from meter_data.master_main mm LEFT JOIN meter_data.amilocation ami on mm.town_code=ami.tp_towncode LEFT JOIN meter_data.dtdetails dts on dts.dttpid=mm.location_id where mm.zone like '"+zone+"' and mm.circle like '"+circle+"' and mm.town_code like '"+towncode+"' GROUP BY mm.substation,mm.fdrname,mm.location_id,mm.town_code,ami.town_ipds,dts.dtname)dtmm\r\n" + 
					"on dth.tp_dt_id = dtmm.location_id where month_year ='"+rdngmnth+"' ";
			
			/*String sql = "SELECT distinct A.*, B.parent_substation,B.dtname,B.parent_feeder,B.dtcapacity,B.mf,c.ss_name FROM meter_data.dt_health_rpt A,\n" +
					"meter_data.dtdetails B, meter_data.substation_details c WHERE cast(A.office_id as text)=cast(B.officeid as text) and A.office_id in\n" +
					" ( SELECT cast(sitecode as text) \n" +
					"from meter_data.amilocation WHERE A.tp_dt_id=B.dttpid and circle like '"+circle+"' and division like '"+division+"' and \n" +
					"subdivision like '"+subdiv+"'  and town_ipds like '"+town+"') AND A.month_year='"+rdngmnth+"' and B.parent_substation= c.sstp_id ";*/
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			System.out.println(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public void getDThealthreportPDF(HttpServletRequest request, HttpServletResponse response, String zone1,
			String crcl, String twn, String month, String townname1) {
		
		
	String zone="",circle="",town="" ,townname="";
	try {
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
			town=twn;
		}
		if(townname1=="%")
		{
			townname="ALL";
		}else {
			townname=townname1;
		}
		Rectangle pageSize = new Rectangle(1920,1080);
		Document document = new Document(pageSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
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
	    p1.add(new Phrase("DT Health Report ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p1.setAlignment(Element.ALIGN_CENTER);
	    cell2.addElement(p1);
	    cell2.setBorder(Rectangle.BOTTOM);
	    pdf1.addCell(cell2);
	    document.add(pdf1);
	    
	    PdfPTable header = new PdfPTable(2);
	    header.setWidths(new int[]{1,1});
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
	    
	    
		/*
		 * headerCell = new PdfPCell(new Phrase("Division :"+division,new
		 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
		 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
		 * header.addCell(headerCell);
		 * 
		 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
		 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
		 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
		 * header.addCell(headerCell);
		 */
	    
	    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Month Year :"+month,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

	    document.add(header);
	    
	    String query="";
		List<Object[]> DtloadSummData=null;
		query="Select  dth.*,dtmm.* from meter_data.dt_health_rpt dth \n" +
				"right join \n" +
				"(\n" +
				"Select distinct substation ,string_agg(fdrname,',' ORDER BY fdrname) fdrname ,string_agg(customer_name,',' ORDER BY customer_name)customer_name ,location_id  from meter_data.master_main  where circle like '"+crcl+"' and town_code like '"+twn+"' GROUP BY substation ,fdrname,location_id,town_code )dtmm\n" +
				"on dth.tp_dt_id = dtmm.location_id where month_year = '"+month+"'  ";
		//System.out.println("query--->"+query);
		DtloadSummData=postgresMdas.createNativeQuery(query).getResultList();



		
		PdfPTable parameterTable1 = new PdfPTable(14);
           parameterTable1.setWidths(new int[]{1, 3, 4, 4, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1,});
           parameterTable1.setWidthPercentage(100);
           PdfPCell parameterCell1;
           
           parameterCell1 = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Town code",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("DT Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
	    
           parameterCell1 = new PdfPCell(new Phrase("DT TP Id",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("KVA rating",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Kwh",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Kvah",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Power Factor",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("R-Phase Avg current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Y-Phase Avg current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("B-Phase Avg current",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("R-Phase Unbalance",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
           parameterCell1 = new PdfPCell(new Phrase("Y-Phase Unbalance",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell1.setFixedHeight(25f);
           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable1.addCell(parameterCell1);
           
        // SECOND TABLE
        			PdfPTable parameterTable2 = new PdfPTable(13);
        			parameterTable2.setWidths(new int[] { 2,3 , 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2});
        			parameterTable2.setWidthPercentage(100);
        			PdfPCell parameterCell2;
           
           parameterCell2 = new PdfPCell(new Phrase("B-Phase Unbalance",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Loading_cond_less_20%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Loading_cond_btw_20%_70%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Loading_cond_btw_70%_100%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Loading_cond_greater_100%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           	parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Load Factor",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Utilization Factor",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Overload",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Underload",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Unbalance",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Total Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Power Off Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           parameterCell2 = new PdfPCell(new Phrase("Power On Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell2.setFixedHeight(25f);
           parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable2.addCell(parameterCell2);
           
           
           for (int i = 0; i < DtloadSummData.size(); i++) 
            {
        	   parameterCell1 = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
					 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					 parameterTable1.addCell(parameterCell1);
					 
					 
					 
					
					 
            	Object[] obj=DtloadSummData.get(i);
            	for (int j = 0; j < obj.length; j++) 
            	{
            		if(j==0)
            		{
            			String value1=obj[0]+"";
            			
						 
            			parameterCell1 = new PdfPCell(new Phrase(obj[30]==null?null:obj[30]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
            			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            			 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase(obj[31]==null?null:obj[31]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase(obj[32]==null?null:obj[32]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						
						 
						 parameterCell1 = new PdfPCell(new Phrase(obj[29]==null?null:obj[29]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[23]==null?null:obj[23]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);	
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase(obj[24]==null?null:obj[24]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[22]==null?null:obj[22]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						 parameterCell1 = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell1.setFixedHeight(30f);
						 parameterTable1.addCell(parameterCell1);
						 
						
						 
						 //second
						 parameterCell2 = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[25]==null?null:obj[25]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[26]==null?null:obj[26]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[27]==null?null:obj[27]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[28]==null?null:obj[28]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[15]==null?null:obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[16]==null?null:obj[16]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[19]==null?null:obj[19]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[18]==null?null:obj[18]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 parameterCell2 = new PdfPCell(new Phrase( obj[17]==null?null:obj[17]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell2.setFixedHeight(30f);
						 parameterTable2.addCell(parameterCell2);
						 
						 
						 
						
						 

            		}
            	}
            }
           
                      document.add(parameterTable1);
                      document.add(parameterTable2);

           
                document.add(new Phrase("\n"));
		        LineSeparator separator = new LineSeparator();
		        separator.setPercentage(98);
		        separator.setLineColor(BaseColor.WHITE);
		        Chunk linebreak = new Chunk(separator);
		        document.add(linebreak);
		         
	       
           
           
		        document.close();
		        response.setHeader("Content-disposition", "attachment; filename=DTHealthreport.pdf");
		        response.setContentType("application/pdf");
		        ServletOutputStream outstream = response.getOutputStream();
		        baos.writeTo(outstream);
		        outstream.flush();
		        outstream.close();
           
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	
	@Override
	public void loadSurveyDtDataPush(String rdngmonth) {
		
		System.out.println("rndmonth------>"+rdngmonth);
		String towncode=null;
		
		
		for(int i=5 ; i>=0 ; i-=1) {
		
			 System.out.println("This is I " +i);
	
			
			if(i == 5) {
				towncode = "5%";
			}
			
			if(i == 4) {
				towncode = "4%";
			}
			
			if(i == 3) {
				towncode = "3%";
			}
			if(i == 2) {
				towncode = "2%";
			}
			
			if(i == 1) {
				towncode = "1%";
			}
			
			if(i == 0) {
				towncode = "0%";
			}
		List<Object[]> list=new ArrayList<>();

		String sql = "Insert into meter_data.load_survey_dt (circle,\r\n" + 
				"				tp_town_code,dttpid,yearmonth,\r\n" + 
				"				kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth\r\n" + 
				"				)\r\n" + 
				"			\r\n" + 
				"		\r\n" + 
				"		SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP-INTERVAL '"+rdngmonth+"','yyyyMM') as billmonth\r\n" + 
				"				FROM\r\n" + 
				"				(\r\n" + 
				"				SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
				"				SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
				"				round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
				"				SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
				"				AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
				"				FROM\r\n" + 
				"				( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
				"				dttpid IN    ( SELECT dttpid FROM\r\n" + 
				"				( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
				"				) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '') A,\r\n" + 
				"				 ( \r\n" + 
				"				 \r\n" + 
				"				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
				"				kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
				"				COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
				"				 kva FROM meter_data.load_survey\r\n" + 
				"				WHERE to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '"+rdngmonth+"','yyyyMM') and meter_number in \r\n" + 
				"				(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
				"				GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
				"				kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"				) b\r\n" + 
				"				WHERE     A.meterno = b.meter_number \r\n" + 
				"				GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
				"				( SELECT *  FROM meter_data.master_main am\r\n" + 
				"				) Y WHERE Y.location_id = X.dttpid\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"";
		
		System.out.println("load_survey_dt_push----->"+ sql);
		
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
	}		
	
	@Override
	public void loadSurveyDtDailyPush() {
		
		//System.out.println("rndmonth------>"+rdngmonth);
		String towncode=null;
		
		
		for(int i=5 ; i>=0 ; i-=1) {
		
			 System.out.println("This is I " +i);
	
			
			if(i == 5) {
				towncode = "5%";
			}
			
			if(i == 4) {
				towncode = "4%";
			}
			
			if(i == 3) {
				towncode = "3%";
			}
			if(i == 2) {
				towncode = "2%";
			}
			
			if(i == 1) {
				towncode = "1%";
			}
			
			if(i == 0) {
				towncode = "0%";
			}
		List<Object[]> list=new ArrayList<>();

		String sql = "Insert into meter_data.load_survey_dt (circle,\r\n" + 
				"				tp_town_code,dttpid,yearmonth,\r\n" + 
				"				kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth\r\n" + 
				"				)\r\n" + 
				"			\r\n" + 
				"		\r\n" + 
				"		SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
				"				FROM\r\n" + 
				"				(\r\n" + 
				"				SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
				"				SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
				"				round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
				"				SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
				"				AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
				"				FROM\r\n" + 
				"				( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
				"				dttpid IN    ( SELECT dttpid FROM\r\n" + 
				"				( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
				"				) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '') A,\r\n" + 
				"				 ( \r\n" + 
				"				 \r\n" + 
				"				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
				"				kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
				"				COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
				"				 kva FROM meter_data.load_survey\r\n" + 
				"				WHERE date(read_time)=CURRENT_DATE-2 and meter_number in \r\n" + 
				"				(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
				"				GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
				"				kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"				) b\r\n" + 
				"				WHERE     A.meterno = b.meter_number \r\n" + 
				"				GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
				"				( SELECT *  FROM meter_data.master_main am\r\n" + 
				"				) Y WHERE Y.location_id = X.dttpid\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"";
		
	//	System.out.println("load_survey_dt_push----->"+ sql);
		
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}
		}
		
		
	}		
	
		

	@Override
	public void loadSurveyDtDataPushDayWise() {
		
		//System.out.println("day------>"+day);
		String towncode=null;
		
		
		for(int i=5 ; i>=0 ; i-=1) {
		
			 System.out.println("This is I " +i);
	
			
			if(i == 5) {
				towncode = "5%";
			}
			
			if(i == 4) {
				towncode = "4%";
			}
			
			if(i == 3) {
				towncode = "3%";
			}
			if(i == 2) {
				towncode = "2%";
			}
			
			if(i == 1) {
				towncode = "1%";
			}
			
			if(i == 0) {
				towncode = "0%";
			}
		List<Object[]> list=new ArrayList<>();

		String sql = "Insert into meter_data.load_survey_dt (circle,\r\n" + 
				"				tp_town_code,dttpid,yearmonth,\r\n" + 
				"				kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth\r\n" + 
				"				)\r\n" + 
				"			\r\n" + 
				"		\r\n" + 
				"		SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
				"				FROM\r\n" + 
				"				(\r\n" + 
				"				SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
				"				SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
				"				round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
				"				SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
				"				AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
				"				FROM\r\n" + 
				"				( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
				"				dttpid IN    ( SELECT dttpid FROM\r\n" + 
				"				( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
				"				) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '') A,\r\n" + 
				"				 ( \r\n" + 
				"				 \r\n" + 
				"				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
				"				kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
				"				COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
				"				 kva FROM meter_data.load_survey\r\n" + 
				"				WHERE read_time between '2020-07-20 00:00:00' and '2020-07-26 23:30:00' and meter_number in \r\n" + 
				"				(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
				"				 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
				"				GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
				"				kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"				) b\r\n" + 
				"				WHERE     A.meterno = b.meter_number \r\n" + 
				"				GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
				"				( SELECT *  FROM meter_data.master_main am\r\n" + 
				"				) Y WHERE Y.location_id = X.dttpid\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"";
		
		System.out.println("load_survey_dt_push----->"+ sql);
		
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
	}
public int pushLoadSurveyDt() {
		
		
		String sql = "Insert into load_survey_dt (\n" +
				"circle,division,subdivision,tp_town_code,dttpid,yearmonth,\n" +
				"kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth\n" +
				")\n" +
				"SELECT Y.circle, Y.division, Y.subdivision, X.*,to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM') as billmonth\n" +
				"FROM\n" +
				"(\n" +
				"SELECT A.tp_town_code, A.dttpid, b.yearmonth,\n" +
				"SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\n" +
				"round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\n" +
				"SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\n" +
				"AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\n" +
				"FROM\n" +
				"( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\n" +
				"dttpid IN    ( SELECT dttpid FROM\n" +
				"( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\n" +
				" meterno IS NOT NULL AND meterno != '' ) b\n" +
				")) A,\n" +
				" (  SELECT meter_number, read_time AS yearmonth,\n" +
				" kwh, kvah, kw, i_r,i_y,i_b,\n" +
				"COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\n" +
				" kva FROM meter_data.load_survey\n" +
				"WHERE to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')\n" +
				"GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH24:mi:ss' ), kwh, frequency,read_time,\n" +
				"kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva ) b\n" +
				"WHERE     A.meterno = b.meter_number \n" +
				"GROUP BY tp_town_code, dttpid, yearmonth ) X,\n" +
				"( SELECT *  FROM meter_data.amilocation am\n" +
				") Y WHERE Y.tp_towncode = X.tp_town_code";
		
		System.out.println("load_survey_dt_push----->"+ sql);
		
		int x = 0;
		try {
			x = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	
	}


@Override
public void dtpowerfailure() {
	String sql = "REFRESH MATERIALIZED VIEW meter_data.powerfailure";
	try {
		int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		System.out.println("Data inserted successfully.");

	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Data not inserted successfully.");
	}
	
}

	@Override
	public void lfpfuf() {
		String sql = "REFRESH MATERIALIZED VIEW meter_data.lfpfuf";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}

	@Override
	public void underload_overload() {
		String sql = "REFRESH MATERIALIZED VIEW meter_data.underload_overload";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}

	@Override
	public void underload_overload_seventy() {
		String sql = "REFRESH MATERIALIZED VIEW meter_data.underload_overload_seventy";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}

	@Override
	public void workingstatus() {

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.workingstatus";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			//System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Data not inserted successfully.");
		}
		
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.workingstatusreport";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			//System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Data not inserted successfully.");
		}

	}

	@Override
	public void unbalance() {
		String sql = "REFRESH MATERIALIZED VIEW meter_data.unbalance";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}
	
	@Override
	public void dailyConsumption_tneb() {

		try {
			String sql = "insert into meter_data.daily_consumption (location_code,kno,date,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time)	\r\n"
					+ "select mma.sdocode,mma.kno,(CURRENT_DATE-interval '1 day'),l.meter_number,sum(l.kwh) as kwh_imp,sum(to_number(l.block_energy_kwh_exp,'9999999')) as kwh_exp ,sum(l.kvah) as kvah_imp,sum(to_number(l.block_energy_kvah_exp,'9999999')) as kvah_exp,CURRENT_TIMESTAMP as time_stamp from meter_data.load_survey l left join meter_data.master_main mma on l.meter_number=mma.mtrno\r\n"
					+ "where l.read_time>to_timestamp(to_char(CURRENT_DATE-interval '1 day','yyyy-MM-DD')||' 00:00:00','yyyy-MM-DD HH24:MI:SS') and l.read_time<=to_timestamp(to_char(CURRENT_DATE,'yyyy-MM-DD')||' 00:00:00','yyyy-MM-DD HH24:MI:SS')  GROUP BY l.meter_number,mma.sdocode,mma.kno";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void fdrlist() {
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.fdrlist";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.circle_status";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY  meter_data.comm_active";
//			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}


	@Override
	public void dtDashboardReportQueryReferench() {

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtlastday";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtinstancelastday";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtlastweek";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtinstancelastweek";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtcurrentmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtinstencecurrentmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtlastmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtinstancelastmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtlastday";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtinstancelastday";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtlastweek";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtinstancelastweek";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtinstancecurrentmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtlastmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.underloaddtinstancelastmonth";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.dt_unbalanced";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.dt_power_failure";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.dt_overload_underload";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
		String sql = "REFRESH MATERIALIZED VIEW meter_data.dt_lfpfuf";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW meter_data.overloaddtlastday_cmd";
				int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		

	}
	
	@Override
	public void dtDashboardReportQueryReferench1() {
//		
//		try {
//			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.dtcmd_event_details";
//			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
		  try { 																																																																																																		
			  String sql ="REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.dailydt_poweronff_event1";
			  int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			  }
		  
		  catch (Exception e) { 
			  e.printStackTrace(); 
			  }
		 
		
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.power_onoff_cmd";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void dtDashboardReportQueryReferench2() {
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.total_dt_cmd";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * try { String sql =
		 * "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.dtcmd_event_details"; int
		 * j =
		 * getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate()
		 * ; } catch (Exception e) { e.printStackTrace(); }
		 */
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.total_dt_communicating_cmd";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String sql = "REFRESH MATERIALIZED VIEW  meter_data.power_onoff_cmd";
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


	@Override
	public void dailydtcomm() {
		
		String sql = "REFRESH MATERIALIZED VIEW meter_data.daily_dt_comm_cmd";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}
		
		String sql1 = "REFRESH MATERIALIZED VIEW CONCURRENTLY meter_data.dash_comm";
		try {
			int j = getCustomEntityManager("postgresMdas").createNativeQuery(sql1).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

		
	}
	
	
	


	
}
