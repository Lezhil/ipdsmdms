package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.FeederHealthEntity;
import com.bcits.entity.Master;
import com.bcits.service.ReportService;
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
public class ReportServiceImplementation extends GenericServiceImpl<Master> implements ReportService{

	@Override
	public List<?> pfanalysisreport(String region,String circle,String town,String rdngmnth,String metertype) {
		List<?> pflist=new ArrayList<>();
		String year=rdngmnth.substring(0, 4);
		String mnth=rdngmnth.substring(4,6);
		String sql="";
		//System.out.println(year+" =-=-=-=-=-=---"+mnth);
		/*
		 * String
		 * sql="select c.*,a.*,b.*,(b.range1/total_month)*100 as range1per,(b.range2/total_month)*100 as range2per,(b.range3/total_month)*100 as range3per,(b.range4/total_month)*100 as range4per from \n"
		 * +
		 * "(SELECT meter_number, round((case WHEN \"sum\"(kvah)<>0 THEN \"sum\"(kwh)/\"sum\"(kvah) else 0 END), 2) as pf,\n"
		 * + "((SELECT meter_data.num_days("+year+", "+mnth+"))*24) as total_month\n" +
		 * "from meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="
		 * +mnth+" AND EXTRACT('year' FROM date(read_time))="
		 * +year+" GROUP BY meter_number)a,\n" +
		 * "(select range1,range2,range3,range4,meter_sr_number,month_year from meter_data.pf_analysis_rpt where location_type='"
		 * +metertype+"' and month_year='"
		 * +rdngmnth+"' and cast(office_id as NUMERIC) in (select distinct sitecode from meter_data.amilocation where subdivision like '"
		 * +subdiv+"') )b,\n" +
		 * "(select mtrno,subdivision,kno from meter_data.master_main)c\n" +
		 * "where a.meter_number=b.meter_sr_number and c.mtrno=b.meter_sr_number and a.meter_number=c.mtrno"
		 * ;
		 */
		//System.out.println(sql);
			/*	"select a.meter_number,b.subdivision,b.kno,a.pf,a.slab1 as slab1period,(a.slab1/a.total_month)*100 as slab1p,\n" +
				"a.slab2 as slab2period,(a.slab2/a.total_month)*100 as slab2per,\n" +
				"a.slab3 as slab3period,(a.slab3/a.total_month)*100 as slab3per,\n" +
				"a.slab4 as slab4period,(a.slab4/a.total_month)*100 as slab4per,a.total,a.total_month FROM\n" +
				"(SELECT meter_number, round((case WHEN \"sum\"(kvah)<>0 THEN \"sum\"(kwh)/\"sum\"(kvah) else 0 END), 2) as pf,\n" +
				"COALESCE(sum(case WHEN power_factor >0 AND power_factor<0.5 THEN 30 END),0)/60 as slab1,\n" +
				"COALESCE(sum(case WHEN power_factor >=0.5 AND power_factor<0.7 THEN 30 END),0)/60 as slab2,\n" +
				"COALESCE(sum(case WHEN power_factor >=0.7 AND power_factor<0.9 THEN 30 END),0)/60 as slab3,\n" +
				"COALESCE(sum(case WHEN power_factor >=0.9  THEN 30 END),0)/60 as slab4,\n" +
				"(COUNT(*)*30) AS total,\n" +
				"((SELECT meter_data.num_days("+year+", "+mnth+"))*24) as total_month\n" +
				"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))=2019 \n" +
				"and meter_number in(select mtrno from meter_data.master_main \n" +
				" where subdivision LIKE '"+subdiv+"' and fdrcategory like '"+metertype+"')\n" +
				"GROUP BY meter_number)a,\n" +
				"(select mtrno,subdivision,kno from meter_data.master_main where subdivision like '"+subdiv+"' and fdrcategory='"+metertype+"')b\n" +
				"where a.meter_number=b.mtrno";*/
		
		
		if(metertype.equalsIgnoreCase("BOUNDARY METER")) {
	
			 
			 
				sql = "select mm.mtrno,mm.fdrname,mm.kno,a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
						"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
						"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
						"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total,a.total_month FROM\n" +
						"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
						"(COUNT(*)*30) AS total,\n" +
						"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
						"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+"  AND EXTRACT('year' FROM date(read_time))= "+year+" \n" +
						"and meter_number in(select mtrno from meter_data.master_main  \n" +
						"where town_code like '"+town+"'  and fdrcategory like 'BOUNDARY METER') \n" +
						"GROUP BY meter_number)a\n" +
						"right join \n" +
						"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+town+"' and zone like '"+region+"' and circle like '"+circle+"' and mm.fdrcategory='BOUNDARY METER' order by slab1period" ;
		}
		else if(metertype.equalsIgnoreCase("FEEDER METER")){
			
			
			sql = "select mm.mtrno,mm.fdrname,mm.kno,a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
					"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
					"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
					"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total,a.total_month FROM\n" +
					"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
					"(COUNT(*)*30) AS total,\n" +
					"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
					"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+"  AND EXTRACT('year' FROM date(read_time))= "+year+" \n" +
					"and meter_number in(select mtrno from meter_data.master_main  \n" +
					"where town_code like '"+town+"'  and fdrcategory like 'FEEDER METER') \n" +
					"GROUP BY meter_number)a\n" +
					"right join \n" +
					"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+town+"' and zone like '"+region+"' and circle like '"+circle+"' and mm.fdrcategory='FEEDER METER' order by slab1period" ;
			
			
		}
		else if(metertype.equalsIgnoreCase("DT")){
			
			sql= "select mm.mtrno,(select string_agg(distinct dtname,',' ORDER BY dtname) from meter_data.dtdetails where dttpid = mm.location_id)as dtname,mm.kno, a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
					"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
					"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
					"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total,a.total_month FROM\n" +
					"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
					"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
					"(COUNT(*)*30) AS total,\n" +
					"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
					"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))= "+year+"\n" +
					"and meter_number in(select mtrno from meter_data.master_main  \n" +
					"where town_code like '"+town+"'  and fdrcategory like 'DT') \n" +
					"GROUP BY meter_number)a\n" +
					"right join \n" +
					"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+town+"' and zone like '"+region+"' and circle like '"+circle+"'  and mm.fdrcategory like 'DT' order by slab1period" ; 
		}
		try {
			System.out.println(pflist.size()+ sql);
			pflist=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {

			e.printStackTrace();
		}
		//System.out.println(pflist.size()+sql);
		return pflist;
	}
	
	@Override
	public List<?> voltageVariationAnalysisRep(String town, String rdngmnth, String metertype, String zone, String circle) {
		String sql="";
		List<?> list=new ArrayList<>();
		String year=rdngmnth.substring(0, 4);
		String mnth=rdngmnth.substring(4,6);
		String mon = mnth;
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
		//System.out.println(year+" =-=-=-=-=-=---"+mnth);
		
			/*
			 * String
			 * sql="select distinct mtrno,customer_name,accno,kno,voltage_kv,range1,range2,range3,range4,((meter_data.num_days("
			 * +year+","+mnth+"))*24)  as total_hours from \n" +
			 * "(SELECT C.*,D.range1,D.range2,D.range3,D.range4 FROM \n" +
			 * "(SELECT range1,range2,range3,range4,meter_sr_number from meter_data.voltage_regulation_rpt WHERE"
			 * +
			 * " office_id in(SELECT cast(sitecode as text) from meter_data.amilocation WHERE zone like '"
			 * +zone+"' " + "and circle like '"+circle+"' and division like '"
			 * +div+"' and subdivision like '"+subdiv+"') " +
			 * "and location_type='"+metertype+"' and month_year='"+rdngmnth+"')D\n" +
			 * "LEFT JOIN\n" + "(\n" +
			 * "SELECT A.customer_name,A.accno,A.kno,A.voltage_kv,A.mtrno,\n" +
			 * "(CASE WHEN cast(connection_type as NUMERIC)=1 THEN voltage_kv END) as ratedVoltage_1,\n"
			 * +
			 * "(CASE WHEN cast(connection_type as NUMERIC)=33 THEN voltage_kv END) as ratedVoltage_33,\n"
			 * +
			 * "(CASE WHEN cast(connection_type as NUMERIC)=34 THEN cast(voltage_kv as numeric)/1.732 END) as ratedVoltage_34\n"
			 * + "from \n" + "(\n" +
			 * "SELECT customer_name,accno,kno,mtrno,voltage_kv from meter_data.master_main\n"
			 * + ") A LEFT JOIN\n" + "(\n" +
			 * "SELECT connection_type,meterno from meter_data.meter_inventory\n" +
			 * ") B on A.mtrno=B.meterno)C on C.mtrno=D.meter_sr_number)x";
			 */
			if(metertype.equalsIgnoreCase("BOUNDARY METER")) {
			/*
			 * sql="SELECT * FROM\n" +
			 * "(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '110' ELSE  '110' END) as numeric)),3) as rated_voltage,accno,customer_name,kno,fdrcategory,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code) as towncodename  from meter_data.master_main WHERE fdrcategory='BOUNDARY METER' and town_code like '"
			 * +town+"')A,\n" +
			 * "(SELECT meterno, meter_connection_type,connection_type from meter_data.meter_inventory WHERE connection_type='34')C,\n"
			 * + "(select boundary_name,meterno from meter_data.feederdetails)d,\n"+
			 * "(SELECT month_year,meter_number,total_hours,\n" +
			 * "round(cast(COALESCE((((COALESCE(ver_less_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n"
			 * + "(COALESCE(ver_less_6*15,0) )/60  as less_6_hrs, \n" +
			 * "round(cast(COALESCE((((COALESCE(ver_0_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n"
			 * + "(COALESCE(ver_0_6*15,0))/60 as btw_6_0_hrs,\n" +
			 * "round(cast(COALESCE( (((COALESCE(ver_0_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n"
			 * + "(COALESCE( ver_0_5*15,0))/60  as btw_0_5_hrs,\n" +
			 * "round(cast(COALESCE( (((COALESCE(ver_great_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n"
			 * + "(COALESCE(ver_great_5*15,0))/60  as great_5_hrs from\n" + "(\n" +
			 * "SELECT a.month_year, a.meter_number,a.total_hours , \n" +
			 * "sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
			 * "sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
			 * "sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
			 * "sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" + "FROM\n" + "(\n"
			 * + "SELECT read_time, meter_number,((meter_data.num_days("+year+","+
			 * mnth+"))*24)  as total_hours,\n" +
			 * " cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric)/'1.732' as average_voltage,kva,\n"
			 * + "round(\n" + "(round(\n" +
			 * "((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3)-110/1.732,3)/(110/1.732))*100,2) as per,\n"
			 * + "to_char(read_time,'YYYYMM') as month_year \n" +
			 * "from meter_data.load_survey l,meter_data.master_main m \n" +
			 * "WHERE l.meter_number=m.mtrno and \n" +
			 * "READ_TIME>=TO_DATE('"+rdngmnth+"','yyyymm') \n" + "AND\n" +
			 * "READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" + "ORDER BY  read_time\n" +
			 * ") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
			 * ")c)b WHERE a.mtrno=b.meter_number  and b.meter_number=c.meterno and a.mtrno=c.meterno and c.meterno=d.meterno"
			 * ;
			 */
			 
				sql="SELECT mtrno,rated_voltage,accno,fdrname,kno,fdrcategory,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code),'','','',fdrname,'',b.* FROM\n" +
						"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '110' ELSE '110' END) as numeric)),3) as rated_voltage,accno,fdrname,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='BOUNDARY METER' and town_code like '"+town+"' and zone like '"+zone+"' and circle like '"+circle+"')A\n" +
						"LEFT JOIN\n" +
						"(SELECT month_year,meter_number,total_hours,\n" +
						"round(cast(COALESCE((((COALESCE(ver_less_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
						"(COALESCE(ver_less_6*15,0) )/60  as less_6_hrs, \n" +
						"round(cast(COALESCE((((COALESCE(ver_0_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
						"(COALESCE(ver_0_6*15,0))/60 as btw_6_0_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_0_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
						"(COALESCE( ver_0_5*15,0))/60  as btw_0_5_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_great_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
						"(COALESCE(ver_great_5*15,0))/60  as great_5_hrs from\n" +
						"(\n" +
						"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
						"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
						"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
						"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
						"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
						"FROM\n" +
						"(\n" +
						"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
						" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
						"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 63.51039261, 8)/63.51039261 , 8)* 100 as per,\n" +
						"to_char(read_time,'YYYYMM') as month_year \n" +
						"from meter_data.load_survey l LEFT JOIN meter_data.master_main m \r\n" + 
						"ON(l.meter_number=m.mtrno) where m.town_code like '"+town+"' \n" +
						"and \n" +
						"READ_TIME>=TO_DATE('"+rdngmnth+"','yyyymm') \n" +
						"AND\n" +
						"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
						"ORDER BY  read_time\n" +
						") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
						")c)b ON a.mtrno=b.meter_number ";
					
			}
			else if(metertype.equalsIgnoreCase("FEEDER METER")){
			/*
			 * sql="SELECT * FROM\n" +
			 * "(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '11000' ELSE  '11000' END) as numeric)),3) as rated_voltage,accno,customer_name,kno,fdrcategory,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code) as towncodename  from meter_data.master_main WHERE fdrcategory='FEEDER METER' and town_code like '"
			 * +town+"')A,\n" +
			 * "(SELECT meterno, meter_connection_type,connection_type from meter_data.meter_inventory WHERE connection_type='34')C,\n"
			 * + "(select feedername,meterno from meter_data.feederdetails)d,\n"+
			 * "(SELECT month_year,meter_number,total_hours,\n" +
			 * "round(cast(COALESCE((((COALESCE(ver_less_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n"
			 * + "(COALESCE(ver_less_6*30,0) )/60  as less_6_hrs, \n" +
			 * "round(cast(COALESCE((((COALESCE(ver_0_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n"
			 * + "(COALESCE(ver_0_6*30,0))/60 as btw_6_0_hrs,\n" +
			 * "round(cast(COALESCE( (((COALESCE(ver_0_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n"
			 * + "(COALESCE( ver_0_5*30,0))/60  as btw_0_5_hrs,\n" +
			 * "round(cast(COALESCE( (((COALESCE(ver_great_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n"
			 * + "(COALESCE(ver_great_5*30,0))/60  as great_5_hrs from\n" + "(\n" +
			 * "SELECT a.month_year, a.meter_number,a.total_hours , \n" +
			 * "sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
			 * "sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
			 * "sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
			 * "sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" + "FROM\n" + "(\n"
			 * + "SELECT read_time, meter_number,((meter_data.num_days("+year+","+
			 * mnth+"))*24)  as total_hours,\n" +
			 * " cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric)/'1.732' as average_voltage,kva,\n"
			 * + "round(\n" + "(round(\n" +
			 * "((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3)-11000/1.732,3)/(11000/1.732))*100,2) as per,\n"
			 * + "to_char(read_time,'YYYYMM') as month_year \n" +
			 * "from meter_data.load_survey l,meter_data.master_main m \n" +
			 * "WHERE l.meter_number=m.mtrno and \n" +
			 * "READ_TIME>=TO_DATE('"+rdngmnth+"','yyyymm') \n" + "AND\n" +
			 * "READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" + "ORDER BY  read_time\n" +
			 * ") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
			 * ")c)b WHERE a.mtrno=b.meter_number  and b.meter_number=c.meterno and a.mtrno=c.meterno and c.meterno=d.meterno"
			 * ;
			 */
				
				
				
				sql="SELECT mtrno,rated_voltage,accno,fdrname,kno,fdrcategory,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code),'','','',fdrname,'',b.* FROM\n" +
						"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '11000' ELSE '11000' END) as numeric)),3) as rated_voltage,accno,fdrname,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='FEEDER METER' and town_code like '"+town+"' and zone like '"+zone+"' and circle like '"+circle+"')A\n" +
						"LEFT JOIN\n" +
						"(SELECT month_year,meter_number,total_hours,\n" +
						"round(cast(COALESCE((((COALESCE(ver_less_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
						"(COALESCE(ver_less_6*30,0) )/60  as less_6_hrs, \n" +
						"round(cast(COALESCE((((COALESCE(ver_0_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
						"(COALESCE(ver_0_6*30,0))/60 as btw_6_0_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_0_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
						"(COALESCE( ver_0_5*30,0))/60  as btw_0_5_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_great_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
						"(COALESCE(ver_great_5*30,0))/60  as great_5_hrs from\n" +
						"(\n" +
						"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
						"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
						"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
						"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
						"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
						"FROM\n" +
						"(\n" +
						"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
						" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
						"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 6351.0392609, 8)/6351.0392609 , 8)* 100 as per,\n" +
						"to_char(read_time,'YYYYMM') as month_year \n" +
						"from meter_data.load_survey l,meter_data.master_main m LEFT JOIN meter_data.master_main m \r\n" + 
						"ON(l.meter_number=m.mtrno) where m.town_code like '"+town+"' \n" +
						" and \n" +
						"READ_TIME>=TO_DATE('"+rdngmnth+"','yyyymm') \n" +
						"AND\n" +
						"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
						"ORDER BY  read_time\n" +
						") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
						")c)b ON a.mtrno=b.meter_number ";
			}
			else if(metertype.equalsIgnoreCase("DT")){
				sql="SELECT mtrno,rated_voltage,accno,customer_name,kno,fdrcategory,(Select string_agg( tp_towncode ||'-'||  town_ipds,',' ORDER BY tp_towncode) as towncodename from  meter_data.amilocation where tp_towncode = town_code),'','','',customer_name,'',b.* FROM\n" +
						"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '440' ELSE '440' END) as numeric)),3) as rated_voltage,accno,customer_name,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='DT' and town_code like '"+town+"' and zone like '"+zone+"' and circle like '"+circle+"')A\n" +
						"LEFT JOIN\n" +
						"(SELECT month_year,meter_number,total_hours,\n" +
						"round(cast(COALESCE((((COALESCE(ver_less_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
						"(COALESCE(ver_less_6*30,0) )/60  as less_6_hrs, \n" +
						"round(cast(COALESCE((((COALESCE(ver_0_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
						"(COALESCE(ver_0_6*30,0))/60 as btw_6_0_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_0_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
						"(COALESCE( ver_0_5*30,0))/60  as btw_0_5_hrs,\n" +
						"round(cast(COALESCE( (((COALESCE(ver_great_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
						"(COALESCE(ver_great_5*30,0))/60  as great_5_hrs from\n" +
						"(\n" +
						"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
						"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
						"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
						"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
						"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
						"FROM\n" +
						"(\n" +
						"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
						" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
						"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 254.041570, 8)/254.041570 , 8)* 100 as per,\n" +
						"to_char(read_time,'YYYYMM') as month_year \n" +
						"from meter_data.load_survey l LEFT JOIN meter_data.master_main m \n" +
						"ON(l.meter_number=m.mtrno)   where m.town_code like '"+town+"' and \n" +
						"READ_TIME>=TO_DATE('"+rdngmnth+"','yyyymm') \n" +
						"AND\n" +
						"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
						"ORDER BY  read_time\n" +
						") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
						")c)b ON a.mtrno=b.meter_number ";
			}
			try {
			System.out.println("pflist.size()" +sql);
		
			list=postgresMdas.createNativeQuery(sql).getResultList();

			//System.err.println("pflist---------"+list.size());
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	@Override
	public List<?> getconsumerData(String zone,String circle,String division,String subdivision,String kno,String acno,String mtrno,String consumerCatgry) {
		List<?> list = null;
		String qry = "";
		try {
			qry="select mm.subdivision,COALESCE(cm.tadesc,''),cm.name,cm.accno,cm.kno,cm.meterno\n" +
					"from meter_data.consumermaster cm,meter_data.master_main mm \n" +
					"WHERE  mm.accno=cm.accno  and mm.zone like '"+zone+"' and\n" +
					"mm.circle like '"+circle+"' and division like '"+division+"'\n" +
					"and mm.subdivision like '"+subdivision+"'";
			if (!consumerCatgry.isEmpty()) {
				qry+= " and cm.tadesc='"+consumerCatgry+"'";
	         }
			if (!acno.isEmpty()) {
				qry+= " and cm.accno='"+acno+"'";
	        }
			if ( !kno.isEmpty()) {
				qry+= " and cm.kno='"+kno+"'";
	        }
			if (!mtrno.isEmpty()) {
				qry+=  " and cm.meterno='"+mtrno+"'";
	        }
			System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getdtdata(String zone,String circle,String division,String subdivision,String crossdt, String dtcodeId, String dtmtrnoId) {
		List<?> list=null;
		String qry=null;
		String crdt=null;
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		try {
			qry="select mm.zone,mm.circle,mm.division,mm.subdivision,dt.dtname,dt.dtcapacity,\n" +
					"CAST(dt.crossdt AS varchar),COALESCE(dt.dt_id,''),dt.meterno\n" +
					"from meter_data.dtdetails dt,meter_data.master_main mm where mm.mtrno=dt.meterno\n" +
					"and mm.zone like '"+zone+"' and mm.circle like '"+circle+"' \n" +
					"and mm.division like '"+division+"' and mm.subdivision like '"+subdivision+"'";
			 if (!dtcodeId.isEmpty()) { 
				  qry+= " and dt.dt_id='"+dtcodeId+"'";
				  } if (!crdt.isEmpty()) {
					  qry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
				  } if (!dtmtrnoId.isEmpty()) { 
					  qry+= " and dt.meterno='"+dtmtrnoId+"'";  
				  }
			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			//System.out.println(qry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getfeederdata(String zone,String circle,String division,String subdivision,String fedrcodeId, String crossfeeder,String fedrmtrnoId) {
	
		List<?> list=null;
		String qry=null;
		String crfeeder=null;
		if(crossfeeder.equals("true")){
			crfeeder="1";
		}
		else{
			crfeeder="0";
		}
		try {
			qry="select mm.subdivision,fd.feedername,COALESCE(fd.fdr_id,''),CAST(fd.crossfdr AS VARCHAR),fd.meterno\n" +
					"from meter_data.master_main mm,meter_data.feederdetails fd WHERE mm.mtrno=fd.meterno\n" +
					"and mm.zone like '"+zone+"' and mm.circle like '"+circle+"' and mm.division like '"+division+"'\n" +
					"and subdivision like '"+subdivision+"'";
			
			 if (!fedrcodeId.isEmpty()) { 
				  qry+= " and fd.fdr_id='"+fedrcodeId+"'";
				  }
			if(!crfeeder.isEmpty()){
				qry+=" and CAST(fd.crossfdr AS VARCHAR)='"+crfeeder+"'";
				
			}
			if(!fedrmtrnoId.isEmpty()){
				qry+="and fd.meterno='"+fedrmtrnoId+"'";
			}
			//System.out.println(qry);
 			list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<?> getDtHealthRptData(String zone, String circle,String rdngMonth,String officeId,String town){
		List<?> list=new ArrayList<>();

/*		String sql="select distinct a.month_year,(select subdivision from meter_data.amilocation where sitecode=cast(a.office_id as NUMERIC) and tp_towncode=b.tp_town_code),(select ss_name from meter_data.substation_details where sstp_id=b.parent_substation and parent_town=b.tp_town_code  \n" +
				"),(select DISTINCT feedername from meter_data.feederdetails where fdr_id=b.parentid  ),b.dtname,b.dttpid,b.dtcapacity,a.meter_sr_number,b.crossdt from \n" +
				"(select month_year,meter_sr_number,office_id from meter_data.dt_health_rpt WHERE month_year='"+rdngMonth+"' and office_id in ("+officeId+"))a,\n" +
				"(SELECT crossdt,meterno,dttpid,dttype,dtname,dtcapacity,parent_feeder,parent_substation,parentid,tp_town_code from meter_data.dtdetails where meterno in (select meter_sr_number from meter_data.dt_health_rpt WHERE month_year='"+rdngMonth+"' and office_id in ("+officeId+"))and tp_town_code like '"+town+"')b\n" +
				"where a.meter_sr_number=b.meterno ORDER BY a.meter_sr_number";
*/
		
/*
 * String sql=
 * "Select subdivision, substation,string_agg(fdrname,',' ORDER BY fdrname)fdrname,string_agg(mtrno,',' ORDER BY mtrno)mtrno,\n"
 * +
 * "string_agg(customer_name,',' ORDER BY customer_name)customer_name,tp_dt_id,kva_rating,month_year,office_id from meter_data.dt_health_rpt dth \n"
 * +
 * "left join meter_data.master_main mm on mm.location_id = dth.tp_dt_id where month_year = '"
 * +rdngMonth+"' and office_id in ("+officeId+") and town_code like '"
 * +town+"' and zone like '"+zone+"' and circle like '"
 * +circle+"'  GROUP BY subdivision, substation,tp_dt_id,kva_rating,month_year,office_id"
 * ;
 */
		
	String sql="Select distinct mm.zone,mm.circle,mm.subdivision, mm.substation,string_agg(distinct mm.fdrname,',' ORDER BY mm.fdrname)fdrname,string_agg(distinct mm.mtrno,',' ORDER BY mm.mtrno)mtrno,\r\n" + 
			"string_agg(distinct mm.customer_name,',' ORDER BY mm.customer_name)customer_name,dth.tp_dt_id,dth.kva_rating,dth.month_year,dth.office_id,dth.lf,dth.pf,ami.town_ipds from meter_data.dt_health_rpt dth \r\n" + 
			"left join meter_data.master_main mm on mm.location_id = dth.tp_dt_id LEFT JOIN meter_data.amilocation ami on mm.town_code=ami.tp_towncode where dth.month_year = '"+rdngMonth+"' and dth.office_id in ("+officeId+") and mm.town_code like '"+town+"' and mm.zone like '"+zone+"' and mm.circle like '"+circle+"' and dth.lf is NOT NULL and dth.pf is NOT NULL  GROUP BY mm.subdivision,mm.substation,dth.tp_dt_id,dth.kva_rating,dth.month_year,dth.office_id,mm.zone,mm.circle,dth.lf,dth.pf,ami.town_ipds";	
		
		System.out.println(sql);
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	@Override
	public List<?> getNetworkGraphicalData(String rdngMonth,String officeId,String locType){
		List<List<?>> list=new ArrayList<>();
		List<?> powerFactor=new ArrayList<>();
		List<?> utilizationFactor=new ArrayList<>();
		List<?> loadFactor=new ArrayList<>();
		String tableName="";
		if("dt".equalsIgnoreCase(locType)) {
			tableName="dt_health_rpt";
		}
		else {
			tableName="feeder_health_rpt";
		}
		String sql="select sum(a.dt1) as pf1 ,sum(a.dt2) as pf2,sum(a.dt3) as pf3 ,sum(a.dt4) as pf4 ,sum(a.dt5)  as pf5,sum(a.dt6) as pf6,sum(a.dt7) as pf7,sum(a.dt8) as pf8,sum(a.dt9) as pf9,sum(a.dt10) as pf10 from  (SELECT\n" +
				"	( CASE WHEN pf >= 0 AND pf < 0.1 THEN 1 ELSE 0 END ) AS dt1,\n" +
				"	( CASE WHEN pf >= 0.1 AND pf < 0.2 THEN 1 ELSE 0 END ) AS dt2,\n" +
				"	( CASE WHEN pf >= 0.2 AND pf < 0.3 THEN 1 ELSE 0 END ) AS dt3,\n" +
				"	( CASE WHEN pf >= 0.3 AND pf < 0.4 THEN 1 ELSE 0 END ) AS dt4,\n" +
				"	( CASE WHEN pf >= 0.4 AND pf < 0.5 THEN 1 ELSE 0 END ) AS dt5,\n" +
				"	( CASE WHEN pf >= 0.5 AND pf < 0.6 THEN 1 ELSE 0 END ) AS dt6,\n" +
				"	( CASE WHEN pf >= 0.6 AND pf < 0.7 THEN 1 ELSE 0 END ) AS dt7,\n" +
				"	( CASE WHEN pf >= 0.7 AND pf < 0.8 THEN 1 ELSE 0 END ) AS dt8,\n" +
				"	( CASE WHEN pf >= 0.8 AND pf < 0.9 THEN 1 ELSE 0 END ) AS dt9,\n" +
				"	( CASE WHEN pf >= 0.9 AND pf < 1.0 THEN 1 ELSE 0 END ) AS dt10 \n" +
				"FROM\n" +
				"	meter_data."+tableName+" \n" +
				"WHERE\n" +
				"	month_year = '"+rdngMonth+"' and office_id in("+officeId+"))a";
				/*"GROUP BY\n" +
				"	meter_sr_number,pf) a";*/
		System.out.println(sql);
		String ufsql="select sum(a.dt1) as uf1 ,sum(a.dt2) as uf2,sum(a.dt3) as uf3 ,sum(a.dt4) as uf4 ,sum(a.dt5)  as uf5,sum(a.dt6) as uf6,sum(a.dt7) as uf7,sum(a.dt8) as uf8,sum(a.dt9) as uf9,sum(a.dt10) as uf10 from  (SELECT\n" +
				"	( CASE WHEN uf >= 0 AND uf < 0.1 THEN 1 ELSE 0 END ) AS dt1,\n" +
				"	( CASE WHEN uf >= 0.1 AND uf < 0.2 THEN 1 ELSE 0 END ) AS dt2,\n" +
				"	( CASE WHEN uf >= 0.2 AND uf < 0.3 THEN 1 ELSE 0 END ) AS dt3,\n" +
				"	( CASE WHEN uf >= 0.3 AND uf < 0.4 THEN 1 ELSE 0 END ) AS dt4,\n" +
				"	( CASE WHEN uf >= 0.4 AND uf < 0.5 THEN 1 ELSE 0 END ) AS dt5,\n" +
				"	( CASE WHEN uf >= 0.5 AND uf < 0.6 THEN 1 ELSE 0 END ) AS dt6,\n" +
				"	( CASE WHEN uf >= 0.6 AND uf < 0.7 THEN 1 ELSE 0 END ) AS dt7,\n" +
				"	( CASE WHEN uf >= 0.7 AND uf < 0.8 THEN 1 ELSE 0 END ) AS dt8,\n" +
				"	( CASE WHEN uf >= 0.8 AND uf < 0.9 THEN 1 ELSE 0 END ) AS dt9,\n" +
				"	( CASE WHEN uf >= 0.9 AND uf < 1.0 THEN 1 ELSE 0 END ) AS dt10 \n" +
				"FROM\n" +
				"	meter_data.dt_health_rpt \n" +
				"WHERE\n" +
				"	month_year = '"+rdngMonth+"' and office_id in ("+officeId+") \n" +
				"GROUP BY\n" +
				"	meter_sr_number,uf) a";
		String loadFactorSql="select sum(a.dt1) as lf1 ,sum(a.dt2) as lf2,sum(a.dt3) as lf3 ,sum(a.dt4) as lf4 ,sum(a.dt5)  as lf5,sum(a.dt6) as lf6,sum(a.dt7) as lf7,sum(a.dt8) as lf8,sum(a.dt9) as lf9,sum(a.dt10) as lf10 from  (SELECT\n" +
				"	( CASE WHEN lf >= 0 AND lf < 0.1 THEN 1 ELSE 0 END ) AS dt1,\n" +
				"	( CASE WHEN lf >= 0.1 AND lf < 0.2 THEN 1 ELSE 0 END ) AS dt2,\n" +
				"	( CASE WHEN lf >= 0.2 AND lf < 0.3 THEN 1 ELSE 0 END ) AS dt3,\n" +
				"	( CASE WHEN lf >= 0.3 AND lf < 0.4 THEN 1 ELSE 0 END ) AS dt4,\n" +
				"	( CASE WHEN lf >= 0.4 AND lf < 0.5 THEN 1 ELSE 0 END ) AS dt5,\n" +
				"	( CASE WHEN lf >= 0.5 AND lf < 0.6 THEN 1 ELSE 0 END ) AS dt6,\n" +
				"	( CASE WHEN lf >= 0.6 AND lf < 0.7 THEN 1 ELSE 0 END ) AS dt7,\n" +
				"	( CASE WHEN lf >= 0.7 AND lf < 0.8 THEN 1 ELSE 0 END ) AS dt8,\n" +
				"	( CASE WHEN lf >= 0.8 AND lf < 0.9 THEN 1 ELSE 0 END ) AS dt9,\n" +
				"	( CASE WHEN lf >= 0.9 AND lf < 1.0 THEN 1 ELSE 0 END ) AS dt10 \n" +
				"FROM\n" +
				"	meter_data."+tableName+" \n" +
				"WHERE\n" +
				"	month_year = '"+rdngMonth+"' and office_id in ("+officeId+") \n" +
				"GROUP BY\n" +
				"	meter_sr_number,lf) a";
		try {
			powerFactor=postgresMdas.createNativeQuery(sql).getResultList();
			utilizationFactor=postgresMdas.createNativeQuery(ufsql).getResultList();
			loadFactor=postgresMdas.createNativeQuery(loadFactorSql).getResultList();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		list.add(powerFactor);
		list.add(utilizationFactor);
		list.add(loadFactor);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFeederHealthRptData(String zone,String circle,String rdngmonth, String officeCode,String town,String crossfdr) {
		// TODO Auto-generated method stub
		
		String sql="select distinct a.*,b.*,(select DISTINCT subdivision from meter_data.amilocation where sitecode=b.officeid),(select ss_name from meter_data.substation_details where ss_id=b.parentid) from \n" +
				"(select month_year,meter_sr_number from meter_data.feeder_health_rpt where month_year='"+rdngmonth+"' and office_id in ("+officeCode+"))a,\n" +
				"(select feedername,tp_fdr_id,meterno,crossfdr,officeid,parentid from meter_data.feederdetails where  officeid in ("+officeCode+") and CAST(crossfdr AS varchar)='"+crossfdr+"')b\n" +
				"where a.meter_sr_number=b.meterno";
		System.out.println(sql);
		List<Object[]> list=new ArrayList<>();
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List getMultipleMeterDTMonthWiseResult(String zone, String circle,
			String division, String sdocode, String townCode, String dtTpCode,String billmonth) {
		
		
		String sql="SELECT * FROM \n" +
				"(SELECT * FROM \n" +
				"(SELECT circle,circle_code,division,division_code,subdivision,sitecode,town_ipds,tp_towncode FROM meter_data.amilocation )aa,\n" +
				"(SELECT officeid,tp_town_code,dttpid,dtname,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,round((sum(b.kwh)/sum(b.kvah)),2)as pf FROM \n" +
				"(SELECT dttpid,dtname,meterno,officeid,tp_town_code FROM meter_data.dtdetails WHERE \n" +
				" dttpid in (SELECT dttpid FROM (\n" +
				"SELECT count(*),dttpid,dtname FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n" +
				" GROUP BY dttpid,dtname HAVING count(*)>1)b))a,\n" +
				"(select meter_number,to_char(read_time,'yyyyMM') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,sum(COALESCE(kw,'0') )as kw,sum(COALESCE(kva,'0')) as kva \n" +
				"from meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+billmonth+"'  GROUP BY meter_number,to_char(read_time,'yyyyMM'))b\n" +
				"WHERE a.meterno=b.meter_number GROUP BY officeid,tp_town_code,dttpid,dtname,yearmonth ORDER BY dttpid)bb\n" +
				"WHERE aa.sitecode=bb.officeid AND aa.tp_towncode=bb.tp_town_code)b WHERE circle = '"+circle+"' AND division = '"+division+"' AND subdivision = '"+sdocode+"' AND tp_towncode = '"+townCode+"' AND dttpid='"+dtTpCode+"' ";
		List<Object[]> list=new ArrayList<>();
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List getMultipleMeterDTDateWiseResult(String fromDate,
			String toDate, String dtTpCode) {
		String sql="SELECT * FROM \n" +
				"(SELECT * FROM \n" +
				"(SELECT circle,circle_code,division,division_code,subdivision,sitecode,town_ipds,tp_towncode FROM meter_data.amilocation )aa,\n" +
				"(SELECT officeid,tp_town_code,dttpid,dtname,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,round((sum(b.kwh)/sum(b.kvah)),2)as pf FROM \n" +
				"(SELECT dttpid,dtname,meterno,officeid,tp_town_code FROM meter_data.dtdetails WHERE \n" +
				" dttpid in (SELECT dttpid FROM (\n" +
				"SELECT count(*),dttpid,dtname FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n" +
				" GROUP BY dttpid,dtname HAVING count(*)>1)b))a,\n" +
				"(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,sum(COALESCE(kw,'0') )as kw,sum(COALESCE(kva,'0')) as kva \n" +
				"from meter_data.load_survey WHERE  to_char(read_time,'yyyy-MM-dd') between '"+fromDate+"' and '"+toDate+"'   GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd'))b\n" +
				"WHERE a.meterno=b.meter_number GROUP BY officeid,tp_town_code,dttpid,dtname,yearmonth ORDER BY dttpid)bb\n" +
				"WHERE aa.sitecode=bb.officeid AND aa.tp_towncode=bb.tp_town_code)b WHERE dttpid='"+dtTpCode+"' ";
		List<Object[]> list=new ArrayList<>();
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List getMultipleMeterDTMeterWiseResult(String billDate,
			String dtTpCode) {
		String sql="SELECT * FROM\n" +
				"(SELECT circle,circle_code,division,division_code,subdivision,sitecode,town_ipds,tp_towncode FROM meter_data.amilocation)aa,\n" +
				"(select a.tp_town_code,a.officeid,a.dttpid,a.dtname,a.meterno,b.yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,round((sum(b.kwh)/sum(b.kvah)),2)as pf FROM\n" +
				"(SELECT dttpid,dtname,meterno,officeid,tp_town_code FROM meter_data.dtdetails WHERE \n" +
				" dttpid in (SELECT dttpid FROM (\n" +
				"SELECT count(*),dttpid, dtname FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n" +
				" GROUP BY dttpid,dtname HAVING count(*)>1)b))a,\n" +
				"(select meter_number,to_char(read_time,'yyyy-MM-dd HH24:mi:ss') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,sum(COALESCE(kw,'0') )as kw,sum(COALESCE(kva,'0')) as kva \n" +
				"from meter_data.load_survey WHERE to_char(read_time,'yyyy-MM-dd')='"+billDate+"' \n" +
				"GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd HH24:mi:ss'))b\n" +
				"WHERE a.meterno=b.meter_number AND dttpid='"+dtTpCode+"'\n" +
				"GROUP BY yearmonth ,tp_town_code,officeid,dttpid,dtname,meterno ORDER BY yearmonth )bb\n" +
				"WHERE aa.sitecode=bb.officeid AND aa.tp_towncode=bb.tp_town_code";
		List<Object[]> list=new ArrayList<>();
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return list;
	}

	@Override
	public Object getDtHealthRptPdf(HttpServletRequest request, HttpServletResponse response, String month,
			String officeCode,String zone,String circle, String loctype, String town) {
System.out.println("hi");
		try {	
			String zone1="",circle1="",division="",subdivision="",town1 = "";
			
			if(zone=="ALL")
			{
				zone1="%";
			}else {
				zone1=zone;
			}
			if(circle=="ALL")
			{
				circle1="%";
			}else {
				circle1=circle;
			}
		
			if(town=="ALL")
			{
				town1="%";
			}else {
				town1=town;
			}
			Rectangle pageSize = new Rectangle(1350, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Network Asset Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    
		   

		    
		    headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(zone, PdfPCell.ALIGN_LEFT));
		    
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+dvn,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 * 
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+sdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
			/*
			 * headerCell = new PdfPCell(new Phrase("Town :"+townname,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+town,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		
		    
		    
		    headerCell = new PdfPCell(new Phrase("Location Type :"+loctype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
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
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> DtnwperformData=null;
			/*query="select distinct a.month_year,(select subdivision from meter_data.amilocation where sitecode=cast(a.office_id as NUMERIC) and tp_towncode=b.tp_town_code),(select ss_name from meter_data.substation_details where sstp_id=b.parent_substation and parent_town=b.tp_town_code  \n" +
					"),(select DISTINCT feedername from meter_data.feederdetails where fdr_id=b.parentid  ),b.dtname,b.dttpid,b.dtcapacity,a.meter_sr_number,b.crossdt from \n" +
					"(select month_year,meter_sr_number,office_id from meter_data.dt_health_rpt WHERE month_year='"+month+"' and office_id in ("+officeCode+"))a,\n" +
					"(SELECT crossdt,meterno,dttpid,dttype,dtname,dtcapacity,parent_feeder,parent_substation,parentid,tp_town_code from meter_data.dtdetails where meterno in (select meter_sr_number from meter_data.dt_health_rpt WHERE month_year='"+month+"' and office_id in ("+officeCode+"))and tp_town_code like '"+town+"')b\n" +
					"where a.meter_sr_number=b.meterno ORDER BY a.meter_sr_number";*/
			
		query=	"Select subdivision, substation,string_agg(fdrname,',' ORDER BY fdrname)fdrname,string_agg(mtrno,',' ORDER BY mtrno)mtrno,\n" +
			"string_agg(customer_name,',' ORDER BY customer_name)customer_name,tp_dt_id,kva_rating,month_year,office_id from meter_data.dt_health_rpt dth \n" +
			"left join meter_data.master_main mm on mm.location_id = dth.tp_dt_id where month_year = '"+month+"' and office_id in ("+officeCode+") and town_code like '"+town+"'  GROUP BY subdivision, substation,tp_dt_id,kva_rating,month_year,office_id";
			
			System.out.println("query----->"+query);
			DtnwperformData=postgresMdas.createNativeQuery(query).getResultList();
		
			
			PdfPTable parameterTable = new PdfPTable(9);
	           parameterTable.setWidths(new int[]{1,1,2,4,4,4,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("SL.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
			
			  parameterCell = new PdfPCell(new Phrase("Subdivision",new
			  Font(Font.FontFamily.HELVETICA ,11, Font.BOLD)));
			  parameterCell.setFixedHeight(25f);
			  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			  parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			  parameterTable.addCell(parameterCell);
			 
	           
	           parameterCell = new PdfPCell(new Phrase("Substation Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Number",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Id",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Capacity",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Year Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < DtnwperformData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=DtnwperformData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 

	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=NetworkAssetDetails.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	
	}

	@Override
	public Object getFeederHealthRptPdf(HttpServletRequest request, HttpServletResponse response, String month,
			String officeCode,String zone,String circle, String loctype,String town,String townname) {
	
		String zone1="",circle1="",town1="",crossfdr="1";
        
		if("FEEDER METER".equalsIgnoreCase(loctype)) {
			crossfdr="0";
		} 
		if(zone=="ALL")
		{
			zone1="%";
		}else {
			zone1=zone;
		}
		if(circle=="ALL")
		{
			circle1="%";
		}else {
			circle1=circle;
		}
	
		if(town=="ALL")
		{
			town1="%";
		}else {
			town1=town;
		}
		
		


		try {
			
			Rectangle pageSize = new Rectangle(1350, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Network Asset Performance ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
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
		    
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+division,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 * 
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdivision,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    
			
			  headerCell = new PdfPCell(new Phrase("Town :"+townname,new
			  Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			  headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			  headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			  header.addCell(headerCell);
			 
		    
		    headerCell = new PdfPCell(new Phrase("Location Type :"+loctype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
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
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> FeedernwperformData=null;
			query="select distinct a.*,b.*,(select DISTINCT subdivision from meter_data.amilocation where sitecode=b.officeid),(select ss_name from meter_data.substation_details where ss_id=b.parentid) from \n" +
					"(select month_year,meter_sr_number from meter_data.feeder_health_rpt where month_year='"+month+"' and office_id in ("+officeCode+"))a,\n" +
					"(select feedername,tp_fdr_id,meterno,crossfdr,officeid,parentid from meter_data.feederdetails where  officeid in ("+officeCode+") and CAST(crossfdr AS varchar)='"+crossfdr+"')b\n" +
					"where a.meter_sr_number=b.meterno";
			System.out.println("query----->"+query);
			FeedernwperformData=postgresMdas.createNativeQuery(query).getResultList();
		
			
			PdfPTable parameterTable = new PdfPTable(8);
	           parameterTable.setWidths(new int[]{1,1,2,2,4,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Month Year",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Sub Division",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Substation Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder ID",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Office ID",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < FeedernwperformData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=FeedernwperformData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
				
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 

	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=NetworkAssetDetails.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	
	}

	@Override
	public void getVolVarReppdf(HttpServletRequest request, HttpServletResponse response, String zne, String crcl, String twn,
			String loctype, String rdngmonth,String townname1) {
		
		List<?> list=new ArrayList<>();
		String year=rdngmonth.substring(0, 4);
		String mnth=rdngmonth.substring(4,6);
		String mon = mnth;
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
		
		String zone="",circle="",town="",townname="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
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
			
			Rectangle pageSize = new Rectangle(1850, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Voltage Variation Report ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
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
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Location Type :"+loctype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+rdngmonth,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> volvarrepData=null;
			
			
				 
			
			if(loctype.equalsIgnoreCase("BOUNDARY METER")) {
				
				 
					query="SELECT mtrno,rated_voltage,fdrname,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code),b.* FROM\n" +
							"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '110' ELSE '110' END) as numeric)),3) as rated_voltage,accno,fdrname,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='BOUNDARY METER' and town_code like '"+twn+"')A\n" +
							"LEFT JOIN\n" +
							"(SELECT meter_number,total_hours,\n" +
							"round(cast(COALESCE((((COALESCE(ver_less_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
							"(COALESCE(ver_less_6*15,0) )/60  as less_6_hrs, \n" +
							"round(cast(COALESCE((((COALESCE(ver_0_6*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
							"(COALESCE(ver_0_6*15,0))/60 as btw_6_0_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_0_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
							"(COALESCE( ver_0_5*15,0))/60  as btw_0_5_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_great_5*15,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
							"(COALESCE(ver_great_5*15,0))/60  as great_5_hrs from\n" +
							"(\n" +
							"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
							"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
							"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
							"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
							"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
							"FROM\n" +
							"(\n" +
							"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
							" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
							"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 63.51039261, 8)/63.51039261 , 8)* 100 as per,\n" +
							"to_char(read_time,'YYYYMM') as month_year \n" +
							"from meter_data.load_survey l,meter_data.master_main m \n" +
							"WHERE l.meter_number=m.mtrno and \n" +
							"READ_TIME>=TO_DATE('"+rdngmonth+"','yyyymm') \n" +
							"AND\n" +
							"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
							"ORDER BY  read_time\n" +
							") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
							")c)b ON a.mtrno=b.meter_number ";
						
				}
				else if(loctype.equalsIgnoreCase("FEEDER METER")){
				
					
					
					
					query="SELECT mtrno,rated_voltage,fdrname,(Select  tp_towncode ||'-'||  town_ipds from meter_data.amilocation where tp_towncode = town_code),b.* FROM\n" +
							"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '11000' ELSE '11000' END) as numeric)),3) as rated_voltage,accno,fdrname,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='FEEDER METER' and town_code like '"+twn+"')A\n" +
							"LEFT JOIN\n" +
							"(SELECT meter_number,total_hours,\n" +
							"round(cast(COALESCE((((COALESCE(ver_less_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
							"(COALESCE(ver_less_6*30,0) )/60  as less_6_hrs, \n" +
							"round(cast(COALESCE((((COALESCE(ver_0_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
							"(COALESCE(ver_0_6*30,0))/60 as btw_6_0_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_0_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
							"(COALESCE( ver_0_5*30,0))/60  as btw_0_5_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_great_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
							"(COALESCE(ver_great_5*30,0))/60  as great_5_hrs from\n" +
							"(\n" +
							"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
							"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
							"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
							"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
							"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
							"FROM\n" +
							"(\n" +
							"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
							" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
							"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 6351.0392609, 8)/6351.0392609 , 8)* 100 as per,\n" +
							"to_char(read_time,'YYYYMM') as month_year \n" +
							"from meter_data.load_survey l,meter_data.master_main m \n" +
							"WHERE l.meter_number=m.mtrno and \n" +
							"READ_TIME>=TO_DATE('"+rdngmonth+"','yyyymm') \n" +
							"AND\n" +
							"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
							"ORDER BY  read_time\n" +
							") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
							")c)b ON a.mtrno=b.meter_number ";
				}
				else if(loctype.equalsIgnoreCase("DT")){
					query="SELECT mtrno,rated_voltage,customer_name,(Select string_agg( tp_towncode ||'-'||  town_ipds,',' ORDER BY tp_towncode) as towncodename from  meter_data.amilocation where tp_towncode = town_code),b.* FROM\n" +
							"(select mtrno,ROUND((cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '440' ELSE '440' END) as numeric)),3) as rated_voltage,accno,customer_name,kno,fdrcategory,town_code from meter_data.master_main WHERE fdrcategory='DT' and town_code like '"+twn+"')A\n" +
							"LEFT JOIN\n" +
							"(SELECT meter_number,total_hours,\n" +
							"round(cast(COALESCE((((COALESCE(ver_less_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as less_6,\n" +
							"(COALESCE(ver_less_6*30,0) )/60  as less_6_hrs, \n" +
							"round(cast(COALESCE((((COALESCE(ver_0_6*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_6_0,\n" +
							"(COALESCE(ver_0_6*30,0))/60 as btw_6_0_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_0_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as btw_0_5,\n" +
							"(COALESCE( ver_0_5*30,0))/60  as btw_0_5_hrs,\n" +
							"round(cast(COALESCE( (((COALESCE(ver_great_5*30,0))/60)/c.total_hours)*100,0) as numeric),2) as great_5,\n" +
							"(COALESCE(ver_great_5*30,0))/60  as great_5_hrs from\n" +
							"(\n" +
							"SELECT a.month_year, a.meter_number,a.total_hours , \n" +
							"sum(CASE WHEN per<0 AND per>=-6 THEN 1 else 0 END) as ver_0_6,\n" +
							"sum(CASE WHEN per>=0 AND per<=5 THEN 1 else 0 END) as ver_0_5,\n" +
							"sum(CASE WHEN per<-6 THEN 1 else 0 END) as ver_less_6,\n" +
							"sum(CASE WHEN per>5 THEN 1 else 0 END) as ver_great_5\n" +
							"FROM\n" +
							"(\n" +
							"SELECT read_time, meter_number,((meter_data.num_days("+year+","+mnth+"))*24)  as total_hours,\n" +
							" cast((case WHEN COALESCE(TRIM(voltage_kv),'') ='' THEN '0' ELSE  voltage_kv END) as numeric) as average_voltage,kva,\n" +
							"round(round(round(((CAST(COALESCE(v_r,0) as NUMERIC)+CAST(COALESCE(v_y,0) as NUMERIC)+CAST(COALESCE(v_b,0) as NUMERIC))/3),6) - 254.041570, 8)/254.041570 , 8)* 100 as per,\n" +
							"to_char(read_time,'YYYYMM') as month_year \n" +
							"from meter_data.load_survey l,meter_data.master_main m \n" +
							"WHERE l.meter_number=m.mtrno and \n" +
							"READ_TIME>=TO_DATE('"+rdngmonth+"','yyyymm') \n" +
							"AND\n" +
							"READ_TIME<TO_DATE('"+nextmon+"','yyyymm') \n" +
							"ORDER BY  read_time\n" +
							") a GROUP BY a.month_year,a.meter_number,a.total_hours\n" +
							")c)b ON a.mtrno=b.meter_number ";
				}
			volvarrepData=postgresMdas.createNativeQuery(query).getResultList();
			//System.out.println("query is---"+query);
			
			PdfPTable parameterTable = new PdfPTable(14);
	           parameterTable.setWidths(new int[]{1,1,2,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Town Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Rated Voltage",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("-6%_to_0%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("0%_to_5%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("less_-6%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("great_5%",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Hours(month)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < volvarrepData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=volvarrepData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			
	            			 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							
							 
							 
							 
	            			 
							 

	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=VoltageVariationReport.pdf");
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
	public void getPowerFactorReppdf(HttpServletRequest request, HttpServletResponse response, String zne, String crcl, String twn,
			 String loctype, String rdngmonth,String townname1) {

		List<?> list=new ArrayList<>();
		String year=rdngmonth.substring(0, 4);
		String mnth=rdngmonth.substring(4,6);
		
		String zone="",circle="",town="",townname="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
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
			
			
			Rectangle pageSize = new Rectangle(1850, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Power Factor Analysis Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
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
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Location Type :"+loctype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+rdngmonth,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> powfacrepData=null;
			
			
			
			if(loctype.equalsIgnoreCase("BOUNDARY METER")) {
				
				 
				 
					query = "select mm.mtrno,mm.fdrname,a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
							"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
							"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
							"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total_month FROM\n" +
							"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
							"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
							"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
							"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
							"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
						
							"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
							"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+"  AND EXTRACT('year' FROM date(read_time))= "+year+" \n" +
							"and meter_number in(select mtrno from meter_data.master_main  \n" +
							"where town_code like '"+twn+"'  and fdrcategory like 'BOUNDARY METER') \n" +
							"GROUP BY meter_number)a\n" +
							"right join \n" +
							"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+twn+"'  and mm.fdrcategory='BOUNDARY METER' order by slab1period" ;
			}
			else if(loctype.equalsIgnoreCase("FEEDER METER")){
				
				
				query = "select mm.mtrno,mm.fdrname,a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
						"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
						"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
						"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total_month FROM\n" +
						"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
						
						"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
						"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+"  AND EXTRACT('year' FROM date(read_time))= "+year+" \n" +
						"and meter_number in(select mtrno from meter_data.master_main  \n" +
						"where town_code like '"+twn+"'  and fdrcategory like 'FEEDER METER') \n" +
						"GROUP BY meter_number)a\n" +
						"right join \n" +
						"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+twn+"'  and mm.fdrcategory='FEEDER METER' order by slab1period" ;
				
				
			}
			else if(loctype.equalsIgnoreCase("DT")){
				
				
				
				query= "select mm.mtrno,(select string_agg(distinct dtname,',' ORDER BY dtname) from meter_data.dtdetails where dttpid = mm.location_id)as dtname, a.pf,a.slab1 as slab1period,round(cast((a.slab1/a.total_month)*100 as numeric),2) as slab1p,\n" +
						"a.slab2 as slab2period,round(cast((a.slab2/a.total_month)*100 as numeric),2) as slab2per,\n" +
						"a.slab3 as slab3period,round(cast((a.slab3/a.total_month)*100 as numeric),2) as slab3per,\n" +
						"a.slab4 as slab4period,round(cast((a.slab4/a.total_month)*100 as numeric),2) as slab4per,a.total_month FROM\n" +
						"(SELECT meter_number, round((case WHEN sum(kvah)<>0 THEN sum(kwh)/sum(kvah) else 0 END), 2) as pf,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >0 AND kwh/NULLIF(kvah,0)<0.5 THEN 30 END),0)/60 as slab1,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.5 AND kwh/NULLIF(kvah,0)<0.7 THEN 30 END),0)/60 as slab2,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.7 AND kwh/NULLIF(kvah,0)<0.9 THEN 30 END),0)/60 as slab3,\n" +
						"COALESCE(sum(case WHEN kwh/NULLIF(kvah,0) >=0.9  THEN 30 END),0)/60 as slab4,\n" +
						
						"((SELECT meter_data.num_days("+year+","+mnth+"))*24) as total_month \n" +
						"FROM meter_data.load_survey  WHERE EXTRACT('month' FROM date(read_time))="+mnth+" AND EXTRACT('year' FROM date(read_time))= "+year+"\n" +
						"and meter_number in(select mtrno from meter_data.master_main  \n" +
						"where town_code like '"+twn+"'  and fdrcategory like 'DT') \n" +
						"GROUP BY meter_number)a\n" +
						"right join \n" +
						"meter_data.master_main mm on mm.mtrno = a.meter_number where mm.town_code like '"+twn+"'  and mm.fdrcategory like 'DT' order by slab1period" ; 
			}
			powfacrepData=postgresMdas.createNativeQuery(query).getResultList();
			//System.out.println("query is---"+query);
			
			PdfPTable parameterTable = new PdfPTable(13);
	           parameterTable.setWidths(new int[]{1,2,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Location Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MeterNumber",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Monthly PF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("pf(0.0-0.50%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("pf(0.50-0.70%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("pf(0.70-0.90%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("pf(>0.90%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Hours",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Hours(month)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < powfacrepData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=powfacrepData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
	            		}
	            	}
	            }
	           
                          document.add(parameterTable);
	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=PowerFactorAnalysisReport.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	


	public List<?> getSaidiDataFromTableUpdate(String townfeeder, String town, String monthyr,String feeder) {
	
	String sql="";
	String myCondition="";
	

	
	if("town".equals(townfeeder)) {
		
		sql="Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
				"to_char(to_date('"+monthyr+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
				"to_char(to_date('"+monthyr+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period,"
				+ "round((totalpowerfailureduration/total_consumers),2) as SAIFI,"
				+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI,'U' as feeder_type from  (\n" +
				"Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
				"Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
				"ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n" +
				"lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n" +
				"(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers,"
				+ " Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n" +
				"LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n" +
				"where to_char(date, 'yyyymm') = '"+monthyr+"' and po.towncode like '"+town+"' \n" +
				"GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n" +
				"agri_con_count,other_con_count ,meterid,monthyear) xa \n" +
				"LEFT JOIN \n" +
				"meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n" +
				" ";
		
	} else if("feeder".equals(townfeeder)) {
		sql="Select xa.*, kwh_imp, kwh_exp, min_voltage,max_current , \n" +
				"to_char(to_date('"+monthyr+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
				"to_char(to_date('"+monthyr+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period, "
				+ "round((totalpowerfailureduration/total_consumers),2) as SAIFI,"
				+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI ,'U' as feeder_type from  (\n" +
				"Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
				"Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
				"ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n" +
				"lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n" +
				"(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers, "
				+ "Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n" +
				"LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n" +
				"where to_char(date, 'yyyymm') = '"+monthyr+"' and feedercode =  '"+feeder+"'\n" +
				"GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n" +
				"agri_con_count,other_con_count ,meterid,monthyear) xa \n" +
				"LEFT JOIN \n" +
				"meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n" +
				" ";
		
	}
	
	System.out.println("SAIFI/SAIDI query" +sql );
	List<?> res=null;
	if(!"".equals(sql)) {
		res=postgresMdas.createNativeQuery(sql).getResultList();
	}
	return res;
}
	
	
	// pdf

		@Override
		public void getSaidiSaifiReportpdf(String townfeeder, String town, String zne, String crcl, String frommonth,
				String feeder,String townname1,String feedername, HttpServletRequest request, HttpServletResponse response,String tomonth) {

			//System.out.println("inside pdf ---------"+townfeeder+"feeder"+feeder+"townname1"+feedername);
			
			String zone = "", circle = "", town1 = "",townname="";
			try {
				if (zne == "%") {
					zone = "ALL";
				} else {
					zone = zne;
				}
				if (crcl == "%") {
					circle = "ALL";
				} else {
					circle = crcl;
				}
				if (town == "%") {
					town1 = "ALL";
				} else {
					town1 = town;
				}
				if(townname1=="%")
				{
					townname="ALL";
				}else {
					townname=townname1;
				}

				//System.err.println(feeder);
				Rectangle pageSize = new Rectangle(2050, 720);
				Document document = new Document(pageSize);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				// PdfWriter.getInstance(document, baos);
				PdfWriter writer = PdfWriter.getInstance(document, baos);
				HeaderFooterPageEvent event = new HeaderFooterPageEvent();
				writer.setPageEvent(event);

				document.open();

				Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
				Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
				pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.addElement(pstart);

				document.add(pdf2);
				PdfPCell cell2 = new PdfPCell();
				Paragraph p1 = new Paragraph();
				p1.add(new Phrase("ReliabilityIndicesFeederSummary", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
				p1.setAlignment(Element.ALIGN_CENTER);
				cell2.addElement(p1);
				cell2.setBorder(Rectangle.BOTTOM);
				pdf1.addCell(cell2);
				document.add(pdf1);

				/*
				 * PdfPTable header = new PdfPTable(6); header.setWidths(new int[]{1,1});
				 * header.setWidthPercentage(100);
				 * 
				 * header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				 * header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				 * header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				 * 
				 * document.add(header);
				 */

				PdfPTable header = new PdfPTable(6);
				header.setWidthPercentage(100);
				PdfPCell headerCell = null;

				headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(zone, PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Town:", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(townname, PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Month Year :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(frommonth, PdfPCell.ALIGN_LEFT));
				
				headerCell = new PdfPCell(new Phrase(" To Month Year :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(tomonth, PdfPCell.ALIGN_LEFT));
				
				

				headerCell = new PdfPCell(new Phrase("Report Type :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);

				header.addCell(getCell(townfeeder + "wise", PdfPCell.ALIGN_LEFT));

				if ("feeder".equals(townfeeder)) {

					headerCell = new PdfPCell(
					new Phrase("Feeder :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
					headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					headerCell.setFixedHeight(20f);
					headerCell.setBorder(PdfPCell.NO_BORDER);
					header.addCell(headerCell);
					header.addCell(getCell(feedername, PdfPCell.ALIGN_LEFT));
				}
				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

				document.add(header);

				String query = "";
				List<Object[]> CirwiseMtrData = null;
				if ("town".equals(townfeeder)) {
					query = "Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
							"to_char(to_date('"+frommonth+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
							"to_char(to_date('"+tomonth+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period,"
							+ "round((totalnooccurence/total_consumers),2) as SAIFI,"
							+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI,'U' as feeder_type from  (\n" +
							"Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
							"Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
							"ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n" +
							"lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n" +
							"(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers,"
							+ " Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n" +
							"LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n" +
							"where to_char(date, 'yyyymm') >= '"+frommonth+"'and to_char(date,'yyyymm')<='"+tomonth+"' and po.towncode like '"+town+"' \n" +
							"GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n" +
							"agri_con_count,other_con_count ,meterid,monthyear) xa \n" +
							"LEFT JOIN \n" +
							"meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n" +
							" ";
				} else if ("feeder".equals(townfeeder)) {
					query = "Select xa.*, round(kwh_imp,2) as kwh_imp, round(kwh_exp,2)as kwh_exp, min_voltage,max_current , \n" + "to_char(to_date('" + frommonth
							+ "', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" + "to_char(to_date('"
							+ frommonth + "', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period, "
							+ "round((totalnooccurence/total_consumers),2) as SAIFI,"
							+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI ,'U' as feeder_type from  (\n"
							+ "Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n"
							+ "Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n"
							+ "ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n"
							+ "lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n"
							+ "(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers, "
							+ "Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n"
							+ "LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n"
							+ "where to_char(date, 'yyyymm')> = '" + frommonth + "' and to_char(date, 'yyyymm')< = '" + tomonth + "' and feedercode =  '" + feeder + "'\n"
							+ "GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n"
							+ "agri_con_count,other_con_count ,meterid,monthyear) xa \n" + "LEFT JOIN \n"
							+ "meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n"
							+ " ";

				}

				 System.out.println(query);
				CirwiseMtrData = postgresMdas.createNativeQuery(query).getResultList();
				System.err.println(CirwiseMtrData.size() + query);

				PdfPTable parameterTable = new PdfPTable(21);
				parameterTable.setWidths(new int[] { 2, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 2, 2 });
				parameterTable.setWidthPercentage(100);
				PdfPCell parameterCell;

				parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase("Feedercode", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Start Period", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase("End Period", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Minimum Voltage", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Maximum Voltage", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Input Energy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Export Energy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Total Interruptions Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase("Total Interruptions Duration(sec)",
						new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("HT Industrial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("HT Commercial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("LT Industrial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("LT Commercial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("LT Domestic Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);
				parameterCell = new PdfPCell(
						new Phrase("GOVT Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("AGRI Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("OTHERS Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(
						new Phrase("Total Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase("SAIFI", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase("SAIDI", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
				parameterCell.setFixedHeight(30f);
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				parameterTable.addCell(parameterCell);

				for (int i = 0; i < CirwiseMtrData.size(); i++) {
					parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
					parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					parameterTable.addCell(parameterCell);

					Object[] obj = CirwiseMtrData.get(i);
					for (int j = 0; j < obj.length; j++) {
						if (j == 0) {
							String value1 = obj[0] + "";
							parameterCell = new PdfPCell(new Phrase(obj[0] == null ? null : obj[0] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[19] == null ? null : obj[19] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[20] == null ? null : obj[20] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[17] == null ? null : obj[17] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[18] == null ? null : obj[18] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[15] == null ? null : obj[15] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[16] == null ? null : obj[16] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[2] == null ? null : obj[2] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[4] == null ? null : obj[4] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[5] == null ? null : obj[5] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[6] == null ? null : obj[6] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[7] == null ? null : obj[7] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[8] == null ? null : obj[8] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[9] == null ? null : obj[9] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[10] == null ? null : obj[10] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[11] == null ? null : obj[11] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[12] == null ? null : obj[12] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[13] == null ? null : obj[13] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[21] == null ? null : obj[21] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

							parameterCell = new PdfPCell(new Phrase(obj[22] == null ? null : obj[22] + "",
									new Font(Font.FontFamily.HELVETICA, 11)));
							parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							parameterCell.setFixedHeight(30f);
							parameterTable.addCell(parameterCell);

						}
					}
				}

				document.add(parameterTable);

				document.add(new Phrase("\n"));
				LineSeparator separator = new LineSeparator();
				separator.setPercentage(98);
				separator.setLineColor(BaseColor.WHITE);
				Chunk linebreak = new Chunk(separator);
				document.add(linebreak);

				document.close();
				response.setHeader("Content-disposition", "attachment; filename=ReliabilityIndicesFeederSummary.pdf");
				response.setContentType("application/pdf");
				ServletOutputStream outstream = response.getOutputStream();
				baos.writeTo(outstream);
				outstream.flush();
				outstream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		
		
		@SuppressWarnings("unchecked")
		@Override
		public List<Object[]> getfrequencyobliviondata(String zone,String circle,String town,String date,String locType) {
		
			
			String sql="select distinct on (a.meter_number)a.meter_number as meter_number,a.fdrname,a.fdrcategory,read_time,frequency, (CASE\r\n" + 
					"								 WHEN frequency > 49.5 and frequency < 50.5 THEN 'NORMAL FREQUENCY RANGE' \r\n" + 
					"								 WHEN frequency > 50.5 then 'HIGH FREQUENCY RANGE'\r\n" + 
					"								 WHEN frequency < 49.5 then 'LOW FREQUENCY RANGE'\r\n" + 
					"					END\r\n" + 
					"		) AS Economy  from\r\n" + 
					"	(select distinct on (s.meter_number)s.meter_number as meter_number,m.fdrname,m.fdrcategory,avg(frequency) as frequency,date(s.read_time)as read_time from meter_data.amiinstantaneous s  LEFT JOIN meter_data.master_main m on(s.meter_number=m.mtrno)  where s.read_time BETWEEN '"+date+" 00:00:00' and '"+date+" 23:59:59' and zone like '"+zone+"' and circle like '"+circle+"' and  m.town_code LIKE '"+town+"'  and fdrcategory like '"+locType+"' and s.meter_number=m.mtrno GROUP BY  meter_number,read_time,fdrname,fdrcategory)a group by meter_number,frequency,read_time,fdrname,fdrcategory";
			System.out.println(sql);
			List<Object[]> list=new ArrayList<>();
			try {
				list=postgresMdas.createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return list;
		}

		@Override
		public List<?> getSaidiData(String town_code, String loc_type, String month_year)
		{
			String sql = "";
			if(loc_type.equalsIgnoreCase("FEEDER METER"))
			{
			 sql = "SELECT * FROM meter_data.caidi_caifi_calculation np ,\n" +
					 "(SELECT * FROM meter_data.feederdetails WHERE tp_town_code = '"+town_code+"' and CAST(crossfdr AS varchar)='0')a\n" +
					 "WHERE np.month_year = '"+month_year+"' AND a.fdr_id  = np.location_code and np.type = '"+loc_type+"'";
			}
			else if(loc_type.equalsIgnoreCase("BOUNDARY METER"))
			{
			 sql = "SELECT * FROM meter_data.caidi_caifi_calculation np ,\n" +
					 "(SELECT * FROM meter_data.feederdetails WHERE tp_town_code = '"+town_code+"' and CAST(crossfdr AS varchar)='1')a\n" +
					 "WHERE np.month_year = '"+month_year+"' AND a.fdr_id  = np.location_code and np.type = '"+loc_type+"'";
			}
			else
			{
				 sql = "SELECT * FROM meter_data.caidi_caifi_calculation np ,\n" +
						 "(SELECT * FROM meter_data.dtdetails WHERE tp_town_code = '"+town_code+"' )a\n" +
						 "WHERE np.month_year = '"+month_year+"' AND a.dt_id  = np.location_code and np.type = '"+loc_type+"'";
			}
			// TODO Auto-generated method stub
			List<?> list=new ArrayList<>();
			System.err.println(sql);
			try {
				list=postgresMdas.createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return list;
		}

		

	}