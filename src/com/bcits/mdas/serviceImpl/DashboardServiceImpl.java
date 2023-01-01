package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.mdas.service.DashboardService;
import com.bcits.serviceImpl.GenericServiceImpl;
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
@Service
public class DashboardServiceImpl extends GenericServiceImpl<Object> implements DashboardService {

	public List<Object[]> zoneList(){
//		String qry="select   zone, sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main group by zone";
		String qry="select   zone, \n" +
				"sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,\n" +
				"sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,\n" +
				"sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,\n" +
				"sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,\n" +
				"sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,\n" +
				"sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  \n" +
				"from meter_data.master_main group by zone";
		List<Object[]> zl=postgresMdas.createNativeQuery(qry).getResultList();
	//System.out.println(qry);
		return zl;
		
	}
	
	public List<Object[]> zoneList2( String zone){
		String qry="select zone,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total, sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where zone like '"+zone+"' group by zone";
		List<Object[]> zl=postgresMdas.createNativeQuery(qry).getResultList();
		//System.out.println(qry);
		return zl;
		
	}
	
	

	@Override
	public List<Object[]> circleList(String circle) {
		String qry="select   zone,circle,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total, sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where zone='"+circle+"'  group by zone,circle ";
		List<Object[]> cl=postgresMdas.createNativeQuery(qry).getResultList();
//		System.out.println(qry);
		return cl;
	}
	
	
	@Override
	public List<Object[]> circleList2(String circle) {
		String qry="select   zone,circle,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total, sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where zone='"+circle+"'  group by zone,circle ";
		List<Object[]> cl=postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println(qry);
		return cl;
	}

	@Override
	public List<Object[]> divisionList(String string, String string2) {
		String qry="select   zone,circle,division ,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where zone='"+string+"' and circle='"+string2+"'  group by zone,circle,division";
//		System.out.println("qry is---"+qry);
		List<Object[]> dl=postgresMdas.createNativeQuery(qry).getResultList();
		return dl;
	}

	@Override
	public List<Object[]> subdivList(String string, String string2,
			String string3) {
		String qry="select   zone,circle,division,subdivision,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where zone='"+string+"' and circle='"+string2+"' and division='"+string3+"'    group by zone,circle,division,subdivision";
//      System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}
	
	@Override
	public List<Object[]> getregionWiseMeterList(String region) {
		String qry="	select distinct on (mtrno) zone,circle,division,subdivision,twn.town_name,\r\n" + 
				"				case when fdrcategory ='DT' then 'DT METER' else fdrcategory end as loctype,\r\n" + 
				"				location_id,mtrno,cc.last_communication\r\n" + 
				"				from meter_data.master_main mm,meter_data.town_master twn,meter_data.modem_communication cc where mm.town_code=twn.towncode\r\n" + 
				"				and zone='"+region+"' and mm.mtrno=cc.meter_number order by mtrno,last_communication desc";
     System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}
	
	@Override
	public List<Object[]> getCircleWiseMeterList(String region,String circle) {
		String qry="select distinct on (mtrno) zone,circle,division,subdivision,twn.town_name,\n" +
				"case when fdrcategory ='DT' then 'DT METER' else fdrcategory end as loctype,\n" +
				"location_id,mtrno,cc.last_communication\n" +
				"from meter_data.master_main mm,meter_data.town_master twn,meter_data.modem_communication cc where mm.town_code=twn.towncode and mm.mtrno=cc.meter_number\n" +
				"and zone='"+region+"' and circle='"+circle+"'  order by mtrno,last_communication desc";
      System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}
	
	@Override
	public List<Object[]> getDivisionWiseMeterList(String region,String circle,String div) {
		String qry="select distinct on (mtrno) zone,circle,division,subdivision,twn.town_name,\n" +
				"case when fdrcategory ='DT' then 'DT METER' else fdrcategory end as loctype,\n" +
				"location_id,mtrno,cc.last_communication\n" +
				"from meter_data.master_main mm,meter_data.town_master twn,meter_data.modem_communication cc where mm.town_code=twn.towncode and mm.mtrno=cc.meter_number\n" +
				"and zone='"+region+"' and circle='"+circle+"' and division='"+div+"' order by mtrno,last_communication desc ";
      System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}
	
	@Override
	public List<Object[]> getSubDivisionWiseMeterList(String region,String circle,String div,String subdiv) {
		String qry="select distinct on (mtrno) zone,circle,division,subdivision,twn.town_name,\n" +
				"case when fdrcategory ='DT' then 'DT METER' else fdrcategory end as loctype,\n" +
				"location_id,mtrno,cc.last_communication\n" +
				"from meter_data.master_main mm,meter_data.town_master twn,meter_data.modem_communication cc where mm.town_code=twn.towncode and mm.mtrno=cc.meter_number\n" +
				"and zone='"+region+"' and circle='"+circle+"' and division='"+div+"' and subdivision='"+subdiv+"' order by mtrno,last_communication desc ";
      System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}
	
	@Override
	public List<Object[]> getTownWiseMeterList(String region,String circle,String div,String subdiv,String town) {
		String qry="select distinct on (mtrno) zone,circle,division,subdivision,twn.town_name,\n" +
				"case when fdrcategory ='DT' then 'DT METER' else fdrcategory end as loctype,\n" +
				"location_id,mtrno,cc.last_communication\n" +
				"from meter_data.master_main mm,meter_data.town_master twn,meter_data.modem_communication cc where mm.town_code=twn.towncode and mm.mtrno=cc.meter_number\n" +
				"and zone='"+region+"' and circle='"+circle+"' and division='"+div+"' and subdivision='"+subdiv+"' and twn.town_name='"+town+"'order by mtrno,last_communication desc";
      System.out.println("qry is---"+qry);
		List<Object[]> sdl=postgresMdas.createNativeQuery(qry).getResultList();
		return sdl;
	}

	@Override
	public List<?> pCommSummary(String zone, String circle, String town, String fromdate, String todate){
		List<?> pcs=new ArrayList<>();
		if("ALL".equalsIgnoreCase(zone) || "".equalsIgnoreCase(zone)){
			//System.out.println("in zone"+zone);
			zone="%";
			
		}
		if("ALL".equalsIgnoreCase(circle) || "".equalsIgnoreCase(circle)){
			//System.out.println("in circle"+circle);
			circle="%";
			
		}
		/*
		 * if("ALL".equalsIgnoreCase(division) || "".equalsIgnoreCase(division)){
		 * //System.out.println("in div"+division); division="%";
		 * 
		 * }
		 */
		/*
		 * if("ALL".equalsIgnoreCase(subdivision) || "".equalsIgnoreCase(subdivision)){
		 * //System.out.println("in circle"+subdivision); subdivision="%";
		 * 
		 * }
		 */
		
		String qry="select date,total_meter,communicating,non_communicating,total_dt_meter,dt_communicating,dt_non_communicating,total_feeder_meter,fm_commuicating,fm_non_communicating,\r\n" + 
				"total_boundary_meter,bm_communicating,bm_non_communicating,circle,town_code from meter_data.period_wise_data  where zone like '"+zone+"' and circle like '"+circle+"' and town_code like'"+town+"' and date >='"+fromdate+"' and date <= '"+todate+"' " ;
		
//		String qry="SELECT  dd,case when mc.total is null then (select count(mtrno)  from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else mc.total end,case when mc.comm is null then 0 else mc.comm end ,case when mc.noncom is null then (select count(mtrno)  from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else mc.noncom end as noncommtotal ,\n" +
//				"case when ltcj.lttotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else ltcj.lttotal end as lttotal ,\n" +
//				"case when ltcj.ltcomm is null then 0 else ltcj.ltcomm end as ltctotal,case when ltcj.ltc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  else ltcj.ltc end as ltnctotal,\n" +
//				"case when htcj.httotal is null then(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else htcj.httotal end  as httotal,case when htcj.htcomm is null then 0 else htcj.htcomm end as htctotal,case when htcj.htc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else htcj.htc end as htnctotal,\n" +
//				"case when dtcj.dttotal='0' or dtcj.dttotal is null then(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else dtcj.dttotal end  as dttotal,case when dtcj.dtcomm is null then 0 else dtcj.dtcomm end  as dtctotal    ,case when dtcj.dtc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else dtcj.dtc\n" +
//				" end as dtnctotal,case when fmcj.fmtotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else fmcj.fmtotal end  as fmtotal,\n" +
//				"case when fmcj.fmcomm is null then 0 else fmcj.fmcomm  end as fmctotal,case when fmcj.fmc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') \n" +
//				"else fmcj.fmc end as fmnctotal,case when bmcj.bmtotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='BOUNDARY METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else bmcj.bmtotal end  as bmtotal,case when bmcj.bmcomm is null then 0 else bmcj.bmcomm end  as bmctotal,case when bmcj.bmc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='BOUNDARY METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') else bmcj.bmc end as bmnctotal\n" +
//				"FROM generate_series\n" +
//				"        (  to_timestamp('"+fromdate+"', 'yyyy-MM-dd') \n" +
//				"        , to_timestamp('"+todate+"', 'yyyy-MM-dd')\n" +
//				"        , interval '1 day') dd left join (select date,(select count(mtrno)  from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as total,count(*) as comm,(select count(mtrno)  from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"')-count(*) as noncom from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')\n" +
//				" group by date order by date) mc on to_char(dd,'yyyy-MM-dd')=to_char(mc.date,'yyyy-MM-dd') \n" +
//				" left join (\n" +
//				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as lttotal,count(*) as ltcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '"+zone+"' and circle like '"+circle+"')-count(*) as ltc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='LT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  \n" +
//				" group by date order by date) ltcj on  to_char(dd,'yyyy-MM-dd')=to_char(ltcj.date,'yyyy-MM-dd') \n" +
//				" left join (\n" +
//				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as httotal,count(*) as htcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '"+zone+"' and circle like '"+circle+"')-count(*) as htc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='HT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  \n" +
//				" group by date order by date) htcj on  to_char(dd,'yyyy-MM-dd')=to_char(htcj.date ,'yyyy-MM-dd')\n" +
//				" left join (\n" +
//				" select  date,(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as dttotal,count(*) as dtcomm ,(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '"+zone+"' and circle like '"+circle+"')-count(*) as dtc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='DT' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  \n" +
//				" group by date order by date) dtcj on  to_char(dd,'yyyy-MM-dd')=to_char(dtcj.date,'yyyy-MM-dd') \n" +
//				" left join (\n" +
//				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as fmtotal,count(*) as fmcomm ,(select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '"+zone+"' and circle like '"+circle+"')-count(*) as fmc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  \n" +
//				" group by date order by date) fmcj on  to_char(dd,'yyyy-MM-dd')=to_char(fmcj.date ,'yyyy-MM-dd')\n" +
//				" left join (\n" +
//				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='BOUNDARY METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"') as bmtotal,count(*) as bmcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='BOUNDARY METER' and zone like '"+zone+"' and circle like '"+circle+"')-count(*) as bmc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='BOUNDARY METER' and zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"')  \n" +
//			" group by date order by date) bmcj on  to_char(dd,'yyyy-MM-dd')=to_char(bmcj.date,'yyyy-MM-dd')";
		System.out.println(qry);
		/*String qry="SELECT  dd,case when mc.total is null then (select count(mtrno)  from meter_data.master_main where zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else mc.total end,case when mc.comm is null then 0 else mc.comm end ,case when mc.noncom is null then (select count(mtrno)  from meter_data.master_main where zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else mc.noncom end as noncommtotal ,\n" +
				"case when ltcj.lttotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else ltcj.lttotal end as lttotal ,\n" +
				"case when ltcj.ltcomm is null then 0 else ltcj.ltcomm end as ltctotal,case when ltcj.ltc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')  else ltcj.ltc end as ltnctotal,\n" +
				"case when htcj.httotal is null then(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else htcj.httotal end  as httotal,case when htcj.htcomm is null then 0 else htcj.htcomm end as htctotal,case when htcj.htc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else htcj.htc end as htnctotal,\n" +
				"case when dtcj.dttotal is null then(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '%') else dtcj.dttotal end  as dttotal,case when dtcj.dtcomm is null then 0 else dtcj.dtcomm end  as dtctotal    ,case when dtcj.dtc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else dtcj.dtc\n" +
				" end as dtnctotal,case when fmcj.fmtotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else fmcj.fmtotal end  as fmtotal,\n" +
				"case when fmcj.fmcomm is null then 0 else fmcj.fmcomm  end as fmctotal,case when fmcj.fmc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') \n" +
				"else fmcj.fmc end as fmnctotal,case when bmcj.bmtotal is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='BORDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else bmcj.bmtotal end  as bmtotal,case when bmcj.bmcomm is null then 0 else bmcj.bmcomm end  as bmctotal,case when bmcj.bmc is null then (select count(mtrno)  from meter_data.master_main where fdrcategory='BORDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') else bmcj.bmc end as bmnctotal\n" +
				"FROM generate_series\n" +
				"        (  to_timestamp('"+fromdate+"', 'yyyy-MM-dd') \n" +
				"        , to_timestamp('"+todate+"', 'yyyy-MM-dd')\n" +
				"        , interval '1 day') dd left join (select date,(select count(mtrno)  from meter_data.master_main where zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as total,count(*) as comm,(select count(mtrno)  from meter_data.master_main where zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')-count(*) as noncom from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')\n" +
				" group by date order by date) mc on to_char(dd,'yyyy-MM-dd')=to_char(mc.date,'yyyy-MM-dd') \n" +
				" left join (\n" +
				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as lttotal,count(*) as ltcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='LT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')-count(*) as ltc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='LT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')  \n" +
				" group by date order by date) ltcj on  to_char(dd,'yyyy-MM-dd')=to_char(ltcj.date,'yyyy-MM-dd') \n" +
				" left join (\n" +
				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as httotal,count(*) as htcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='HT' and zone like '%' and circle like '"+circle+"' and division like "+division+"' and subdivision like '"+subdivision+"')-count(*) as htc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='HT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')  \n" +
				" group by date order by date) htcj on  to_char(dd,'yyyy-MM-dd')=to_char(htcj.date ,'yyyy-MM-dd')\n" +
				" left join (\n" +
				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as dttotal,count(*) as dtcomm ,(select count(mtrno)  from meter_data.master_main where fdrcategory='DT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')-count(*) as dtc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='DT' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"' )  \n" +
				" group by date order by date) dtcj on  to_char(dd,'yyyy-MM-dd')=to_char(dtcj.date,'yyyy-MM-dd') \n" +
				" left join (\n" +
				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as fmtotal,count(*) as fmcomm ,(select count(mtrno)  from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"')-count(*) as fmc from meter_data.modem_communication where date between '"+fromdate+"' and '"+todate+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='FEEDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"' )  \n" +
				" group by date order by date) fmcj on  to_char(dd,'yyyy-MM-dd')=to_char(fmcj.date ,'yyyy-MM-dd')\n" +
				" left join (\n" +
				" select date,(select count(mtrno)  from meter_data.master_main where fdrcategory='BORDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"') as bmtotal,count(*) as bmcomm,(select count(mtrno)  from meter_data.master_main where fdrcategory='BORDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"%' and subdivision like '"+subdivision+"')-count(*) as bmc from meter_data.modem_communication where date between '"+subdivision+"' and '"+subdivision+"' and meter_number  in (select mtrno from meter_data.master_main where fdrcategory='BORDER METER' and zone like '%' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"' )  \n" +
				" group by date order by date) bmcj on  to_char(dd,'yyyy-MM-dd')=to_char(bmcj.date,'yyyy-MM-dd')";*/
		try {
			 pcs=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("qry------"+qry+"\n"+pcs.size());
		
		return pcs;
	}
	

	
	@Override
	public List<Object[]> unmappedMeters() 
	{
	//String qry="select A.meter_number,case when np.manufacturer_name like 'null' then '' else np.manufacturer_name end ,A.first_comm,A.LAST_comm from (SELECT meter_number,min(last_communication) AS first_comm ,max(last_communication) as LAST_comm  FROM meter_data.modem_communication WHERE DATE(last_communication)>=CURRENT_DATE-30 and meter_number NOT IN (SELECT mtrno FROM meter_data.master_main) GROUP BY meter_number) A LEFT JOIN meter_data.name_plate np on np.meter_serial_number=a.meter_number";
	String qry = "select distinct * from meter_data.unmapped";
			
	List<Object[]> unm=postgresMdas.createNativeQuery(qry).getResultList();
		return unm;
	}

	@Override
	public Object gettotalunm() 
	{
		//String qry="select count(A.meter_number) as total from (SELECT meter_number,min(last_communication) AS first_comm ,max(last_communication) as LAST_comm  FROM meter_data.modem_communication WHERE DATE(last_communication)>=CURRENT_DATE-30 and meter_number  NOT IN (SELECT mtrno FROM meter_data.master_main) GROUP BY meter_number) A LEFT JOIN meter_data.name_plate np on np.meter_serial_number=a.meter_number";
		String qry = "select count(*) from meter_data.unmapped";
				Object unm=postgresMdas.createNativeQuery(qry).getSingleResult();
					return unm;
	}

	@Override
	public void getMtrtypewiseSummpdf(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//	PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Meter Type Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			query="select   zone, sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,\r\n" + 
					"					sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,\r\n" + 
					"					sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,\r\n" + 
					"					sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm,\r\n" + 
					"					sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,\r\n" + 
					"					sum(case when fdrcategory='HT' then 1 else 0  end) as HTC\r\n" + 
					"					from meter_data.master_main  group by zone";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(6);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=MeterTypeWiseSummary.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	//for regionlevelpdf
	@Override
	public void getMtrtypewiseSummpdfforregion(HttpServletRequest request, HttpServletResponse response,String zone ) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//	PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Meter Type Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			query="select   zone, sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,\r\n" + 
					"					sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,\r\n" + 
					"					sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,\r\n" + 
					"					sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm,\r\n" + 
					"					sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,\r\n" + 
					"					sum(case when fdrcategory='HT' then 1 else 0  end) as HTC\r\n" + 
					"					from meter_data.master_main  where zone like '"+zone+"' group by zone";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(6);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=MeterTypeWiseSummary.pdf");
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
	public void getCirclewiseSummpdf(HttpServletRequest request, HttpServletResponse response, String circle) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//	PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Circle Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			query="select   zone,circle,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total, \n" +
					"sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,\n" +
					"sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,\n" +
					"sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm, sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,\n" +
					"sum(case when fdrcategory='HT' then 1 else 0  end) as HTC\n" +
					"from meter_data.master_main where zone='"+circle+"' group by zone,circle";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(7);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[5]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=CircleWiseSummary.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	//disionwisepdf
	
	@Override
	public void getDivisionWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String division) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Division Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			
			query="select  zone,circle,division,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where  circle= '"+division+"'  group by zone,circle,division";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(8);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	          
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=DivisionWiseSummary.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//subdivpdf
	@Override
	public void getSubdivisionWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String subdivision) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("SubDivision Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			
			query="select  zone,circle,division ,subdivision,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main where  division= '"+subdivision+"'  group by zone,circle,division,subdivision";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(9);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("SubDivision",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
						/*
						 * parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						 */
							 
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
			        response.setHeader("Content-disposition", "attachment; filename=SubDivisionWiseSummary.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//townwisepdf
	
	@Override
	public void getTownWiseSummPDF(HttpServletRequest request, HttpServletResponse response,String town) {

		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//PdfWriter.getInstance(document, baos);
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
		    p1.add(new Phrase("Town Wise Summary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			
			query="		select  m.zone,m.circle,m.division , m.subdivision, a.town_name,sum(case when fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,sum(case when fdrcategory='LT' then 1 else 0 end) as LTC,sum(case when fdrcategory='HT' then 1 else 0  end) as HTC,sum(case when fdrcategory='DT' then 1 else 0  end) as DTC,sum(case when fdrcategory='FEEDER METER' then 1 else 0  end) as fm,sum(case when fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm  from meter_data.master_main m, meter_data.town_master a where m.town_code=a.towncode  and  m.subdivision= '"+town+"'  group by m.zone,m.circle,m.division,a.town_name,m.subdivision";
			System.out.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(10);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Region",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("SubDivision",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           parameterCell = new PdfPCell(new Phrase("Total Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Boundary Meters",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
						/*
						 * parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						 */
							 
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
			        response.setHeader("Content-disposition", "attachment; filename=TownWiseSummary.pdf");
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
	public Object townList(String string, String string2, String string3, String string4) {
		
		String qry="select  mm.zone,mm.circle,mm.division,mm.subdivision,al.town_name as townname,sum(case when mm.fdrcategory in ('DT','FEEDER METER','BOUNDARY METER') then 1 else 0 end) as Total,\n" +
				" sum(case when mm.fdrcategory='DT' then 1 else 0  end) as DTC,\n" +
				" sum(case when mm.fdrcategory='FEEDER METER' then 1 else 0  end) as fm,\n" +
				" sum(case when mm.fdrcategory='BOUNDARY METER' then 1 else 0  end) as bm ,\n" +
				" sum(case when mm.fdrcategory='LT' then 1 else 0 end) as LTC,\n" +
				" sum(case when mm.fdrcategory='HT' then 1 else 0  end) as HTC \n" +
				" from meter_data.master_main mm,meter_data.town_master al  where mm.town_code=al.towncode and mm.zone='"+string+"' and mm.circle='"+string2+"' and mm.division='"+string3+"' and mm.subdivision='"+string4+"'  \n" +
				" group by mm.zone,mm.circle,mm.division,mm.subdivision,al.town_name";
		System.out.println(qry);
		List<Object[]> tdl=postgresMdas.createNativeQuery(qry).getResultList();
		return tdl;
		
		
		
	}


	
}
