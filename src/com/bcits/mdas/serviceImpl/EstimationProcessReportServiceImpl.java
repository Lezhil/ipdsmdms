/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.controller.DataExchangeController;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.EstimationProcessRptEntity;
import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.EstimationProcessReportService;
import com.bcits.mdas.service.EstimationRuleService;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.itextpdf.text.log.SysoCounter;

/**
 * @author Tarik
 *
 */
@Repository
public class EstimationProcessReportServiceImpl  extends GenericServiceImpl<EstimationProcessRptEntity> implements EstimationProcessReportService{

	@Autowired
	private EstimationRuleService estservice;
	@Autowired
	private AmrLoadService amrloadservice;
	
	@Autowired
	DataExchangeController dec;
	
	@Override
	public List<Object[]> getEstimatedIpValue(String meterNumber,
			String fromDate, String toDate,HttpServletRequest request) {
		String mnthyr = null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
			EstimationRuleEntity v = estservice.getESTRuleById("EST01");
			String ruleId = v.getEruleid();
			String rule_name = v.getErulename();
		//	String condVal  = v.getCondtion();
			String d_Type = v.getData_type();
			String d_Parameter= v.getParameter();
		//  String Cond = v.getCondtion();

//				String sql = "SELECT ml.*,xy.rdate,xy.missing_time,xy.avgkwh,xy.avgkvah,xy.avgkw,xy.avgkva from(SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n" + 
//						"when mm.fdrcategory in('FEEDER METER','BORDER METER') then 'FEEDER'  \r\n" + 
//						"when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n" + 
//						"FROM meter_data.master_main mm )ml INNER JOIN (\r\n" + 
//						"SELECT g.*,h.AVGkwh,h.AVGkvah,h.AVGkw,h.AVGkva FROM\r\n" + 
//						"(SELECT l.meter_number, l.rdate, string_agg(CAST(l.dates as TEXT), ',') as missing_time FROM\r\n" + 
//						"(SELECT m.dates,m.meter_number,m.rdate,m.count FROM\r\n" + 
//						"(SELECT * from(SELECT dates FROM generate_series(CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate+"' as TIMESTAMP),  interval '30 min') AS dates\r\n" + 
//						")a,(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' GROUP BY meter_number, date(read_time) having count(*) <>1440\r\n" + 
//						")b WHERE date(a.dates)=b.rdate\r\n" + 
//						")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
//						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"'  GROUP BY meter_number, date(read_time) having count(*) <>1440\r\n" + 
//						")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
//						")n ON m.meter_number=n.meter_number AND m.dates=n.read_time WHERE n.meter_number is NULL\r\n" + 
//						")l where l.meter_number in('"+meterNumber+"') GROUP BY l.meter_number, l.rdate\r\n" + 
//						")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
//						"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in('"+meterNumber+"')\r\n" + 
//						"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*) <>1440\r\n" + 
//						")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
//						")xy ON ml.mtrno=xy.meter_number";
			
//			String sql = "SELECT ml.*,xy.rdate,xy.missing_time,xy.avgkwh,xy.avgkvah,xy.avgkw,xy.avgkva from(SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n" + 
//					"when mm.fdrcategory in('FEEDER METER','BORDER METER') then 'FEEDER'  \r\n" + 
//					"when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n" + 
//					"FROM meter_data.master_main mm )ml INNER JOIN (\r\n" + 
//					"SELECT g.*,h.AVGkwh,h.AVGkvah,h.AVGkw,h.AVGkva FROM\r\n" + 
//					"(SELECT l.meter_number, l.rdate, string_agg(CAST(l.dates as TEXT), ',') as missing_time FROM\r\n" + 
//					"(SELECT m.dates,m.meter_number,m.rdate,m.count FROM\r\n" + 
//					"(SELECT * from(SELECT TO_CHAR(dates,'YYYY-MM-DD HH12:MI:SS') as dates FROM generate_series('"+fromDate+"', '"+toDate+"', interval '30 min') AS dates WHERE dates  NOT IN (SELECT read_time FROM meter_data.load_survey where  meter_number = '"+meterNumber+"')\r\n" + 
//					"					)a,(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number = '"+meterNumber+"' GROUP BY meter_number, date(read_time) having count(*) <>1440\r\n" + 
//					")b WHERE date(a.dates)=b.rdate\r\n" + 
//					")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
//					"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'  GROUP BY meter_number, date(read_time) having count(*) <>1440\r\n" + 
//					")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
//					")n ON m.meter_number=n.meter_number AND cast (m.dates as timestamp)=n.read_time WHERE n.meter_number is NULL\r\n" + 
//					")l where l.meter_number in('"+meterNumber+"') GROUP BY l.meter_number, l.rdate \r\n" + 
//					")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
//					"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number in('"+meterNumber+"')\r\n" + 
//					"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*) <>1440\r\n" + 
//					")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
//					")xy ON ml.mtrno=xy.meter_number";
			
			// query modified kesav
			
			if (meterNumber.equalsIgnoreCase("03323614") || meterNumber.equalsIgnoreCase("03666605") || meterNumber.equalsIgnoreCase("03323606")
					|| meterNumber.equalsIgnoreCase("03666606") || meterNumber.equalsIgnoreCase("03323615")) {
				
				String sql = "SELECT ml.*,xy.rdate,xy.missing_time,xy.avgkwh,xy.avgkvah,xy.avgkw,xy.avgkva from(Select distinct meterno as mtrno,accno,zone,circle,division,subdivision,town_code,customer_name,location_type from(\r\n" + 
						"SELECT fd.meterno,fd.fdr_id as accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,fd.feedername as customer_name ,\r\n" + 
						"'FEEDER' as location_type FROM meter_data.feederdetails fd , meter_data.master_main mm WHERE mm.mtrno=fd.meterno \r\n" + 
						"UNION ALL\r\n" + 
						"SELECT dt.meterno,dt.dt_id as accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,dt.dtname as customer_name ,\r\n" + 
						"'DT' as location_type FROM meter_data.dtdetails dt , meter_data.master_main mm WHERE mm.mtrno=dt.meterno \r\n" + 
						"UNION ALL\r\n" + 
						"SELECT cm.meterno,cm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,cm.name as customer_name ,\r\n" + 
						"'Consumer' as location_type FROM meter_data.consumermaster cm , meter_data.master_main mm WHERE mm.mtrno=cm.meterno )xxx )ml INNER JOIN (\r\n" + 
						"SELECT g.*,h.AVGkwh,h.AVGkvah,h.AVGkw,h.AVGkva FROM\r\n" + 
						"(SELECT l.meter_number, l.rdate, string_agg(CAST(l.dates as TEXT), ',') as missing_time FROM\r\n" + 
						"(SELECT m.dates,m.meter_number,m.rdate,m.count FROM\r\n" + 
						"(SELECT a.* from\r\n" + 
						"(SELECT dates,'"+meterNumber+"' as meter_number,to_char(dates, 'YYYY-MM-DD') as rdate,count(*)   FROM generate_series(CAST('"+fromDate+"' as TIMESTAMP), CAST('"+toDate+" 23:50:59' as TIMESTAMP),  interval '15 min') AS dates\r\n" + 
						"where dates not in (select read_time from meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number = '"+meterNumber+"')  GROUP BY dates\r\n" + 
						")a \r\n" + 
						"left outer join \r\n" + 
						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number = '"+meterNumber+"' GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")b on date(a.dates)=b.rdate\r\n" + 
						")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'  GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
						")n ON m.meter_number=n.meter_number AND m.dates=n.read_time WHERE n.meter_number is NULL\r\n" + 
						")l where l.meter_number in('"+meterNumber+"') GROUP BY l.meter_number, l.rdate\r\n" + 
						")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
						"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number in('"+meterNumber+"')\r\n" + 
						"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
						")xy ON ml.mtrno=xy.meter_number";
				
				System.out.println(sql);
				List<Object[]> list = postgresMdas.createNativeQuery(sql).getResultList();
				return list;
				
			}
			else {
				String sql = "SELECT ml.*,xy.rdate,xy.missing_time,round(xy.avgkwh,3) as avgkwh,round(xy.avgkvah,3)as avgkvah,round(xy.avgkw,3) as avgkw,round(xy.avgkva,3) as avgkva from(Select distinct meterno as mtrno,accno,zone,circle,division,subdivision,town_code,customer_name,location_type from(\r\n" + 
						"SELECT fd.meterno,fd.fdr_id as accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,fd.feedername as customer_name ,\r\n" + 
						"'FEEDER' as location_type FROM meter_data.feederdetails fd , meter_data.master_main mm WHERE mm.mtrno=fd.meterno \r\n" + 
						"UNION ALL\r\n" + 
						"SELECT dt.meterno,dt.dt_id as accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,dt.dtname as customer_name ,\r\n" + 
						"'DT' as location_type FROM meter_data.dtdetails dt , meter_data.master_main mm WHERE mm.mtrno=dt.meterno \r\n" + 
						"UNION ALL\r\n" + 
						"SELECT cm.meterno,cm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.town_code,cm.name as customer_name ,\r\n" + 
						"'Consumer' as location_type FROM meter_data.consumermaster cm , meter_data.master_main mm WHERE mm.mtrno=cm.meterno )xxx )ml INNER JOIN (\r\n" + 
						"SELECT g.*,h.AVGkwh,h.AVGkvah,h.AVGkw,h.AVGkva FROM\r\n" + 
						"(SELECT l.meter_number, l.rdate, string_agg(CAST(l.dates as TEXT), ',') as missing_time FROM\r\n" + 
						"(SELECT m.dates,m.meter_number,m.rdate,m.count FROM\r\n" + 
						"(SELECT a.* from\r\n" + 
						"(SELECT dates,'"+meterNumber+"' as meter_number,to_char(dates, 'YYYY-MM-DD') as rdate,count(*)   FROM generate_series(CAST('"+fromDate+"' as TIMESTAMP), CAST('"+toDate+" 23:50:59' as TIMESTAMP),  interval '30 min') AS dates\r\n" + 
						"where dates not in (select read_time from meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number = '"+meterNumber+"')  GROUP BY dates\r\n" + 
						")a \r\n" + 
						"left outer join \r\n" + 
						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number = '"+meterNumber+"' GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")b on date(a.dates)=b.rdate\r\n" + 
						")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'  GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
						")n ON m.meter_number=n.meter_number AND m.dates=n.read_time WHERE n.meter_number is NULL\r\n" + 
						")l where l.meter_number in('"+meterNumber+"') GROUP BY l.meter_number, l.rdate\r\n" + 
						")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
						"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '"+fromDate+"' and  '"+toDate+"' and meter_number in('"+meterNumber+"')\r\n" + 
						"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
						")xy ON ml.mtrno=xy.meter_number";
				
				System.out.println(sql);
				List<Object[]> list = postgresMdas.createNativeQuery(sql).getResultList();
				return list;
				
			}
			
				
				//System.out.println("return list===size==-=-="+list.size());
				
			
		} catch (Exception e) {
			e.getMessage();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","ESTIMATE IP DATA","MANUAL ESTIMATE METER DATA",e.toString(),lastmodfydate);

			return null;
		}
				//return list;
	}
	
	@Override
	public List<Object[]> saveEstAvgData(List<Object[]> list,String fromDate,String toDate, HttpServletRequest request,
			String kwhArr[],String kvahArr[],String kwArr[],String kvaArr[]){
		
		System.out.println(list.toString());
		try{
			
			//System.out.println("Inside try block");
		EstimationRuleEntity v = estservice.getESTRuleById("EST01");
		String ruleId = v.getEruleid();
		String rule_name = v.getErulename();
	//	String condVal  = v.getCondtion();
		String d_Type = v.getData_type();
		String d_Parameter= v.getParameter();
		if (!list.isEmpty()) {
			int j =0;
			int count=0;
			HttpSession session = request.getSession(false);
			for (Object[] item : list) {
				String strArray[] = item[10].toString().split(",");						
				for(int i=0; i < strArray.length; i++){
					count++;
				//	System.out.println("++date==+"+strArray[i]);
					AmrLoadEntity amrld=(AmrLoadEntity) amrloadservice.findEntityByUniId(item[0].toString(), Timestamp.valueOf(strArray[i]));
					System.err.println(amrld);
					if(amrld != null) {
						System.out.println("hiii");
					}else {
						AmrLoadEntity amrlds= new AmrLoadEntity();
						amrlds.setMyKey(new KeyLoad((String) item[0], Timestamp.valueOf(strArray[i])));
						amrlds.setKwh(Double.parseDouble(String.valueOf(kwhArr[j])));
						amrlds.setKvah(Double.parseDouble(String.valueOf(kvahArr[j])));
						amrlds.setKw(Double.parseDouble(String.valueOf(kwArr[j])));
						amrlds.setKva(Double.parseDouble(String.valueOf(kvaArr[j])));
						amrlds.setTimeStamp(new Timestamp(System.currentTimeMillis()));
						amrlds.setEstimationflag(new Integer(1));
						amrloadservice.save(amrlds);
						 
						 AmrLoadEntity amrlds1= (AmrLoadEntity) amrloadservice.findEntityByUniId(item[0].toString(), Timestamp.valueOf(strArray[i]));
						 Integer id=amrloadservice.findEntityByLoadId(amrlds1.getMyKey().getMeterNumber(), amrlds1.getMyKey().getReadTime());
						//System.out.println(amrlds1.getMyKey().getMeterNumber()+"read time=="+amrlds1.getMyKey().getReadTime()+"iddd="+id);
						//System.out.println(amrlds.getId()+"before save");
						 //System.err.println(id);
						
						EstimationProcessRptEntity eprt=new EstimationProcessRptEntity();
						eprt.setRule_id(ruleId);
						eprt.setRulename(rule_name);
						eprt.setMeter_number((String) item[0]);
						eprt.setEst_app_date(new Timestamp(System.currentTimeMillis()));
						eprt.setZone((String) item[2]);
						eprt.setCircle((String) item[3]);
						eprt.setDivision((String) item[4]);
						eprt.setSubdivision((String) item[5]);
						eprt.setTown_code((String) item[6]);
						eprt.setLocation_type((String) item[8]);
						eprt.setLocation_id((String) item[1]);
						eprt.setLocation_name((String) item[7]);
						eprt.setEst_from(fromDate);
						eprt.setEst_to(toDate);
						eprt.setEst_date(Timestamp.valueOf(strArray[i]));
						eprt.setData_type(d_Type);
						eprt.setParameter(d_Parameter);
						eprt.setEst_kwh(Double.parseDouble(String.valueOf(kwhArr[j])));
						eprt.setEst_kvah(Double.parseDouble(String.valueOf(kvahArr[j])));
						eprt.setEst_kw(Double.parseDouble(String.valueOf(kwArr[j])));
						eprt.setEst_kva(Double.parseDouble(String.valueOf(kvaArr[j])));
						eprt.setEstimatedBy(session.getAttribute("username").toString());
						eprt.setTableName("load_survey");
						eprt.setTableId(id);
						EstimationProcessRptEntity e1=save(eprt);
						
						e1.getId(); 
						//System.out.println(e1.getId()+"  estimation process id");
					}	
				
				}
				j++;
			}
			//System.err.println(count);
		}
		//System.out.println("return list===size==-=-="+list.size());
		return list;
	
		} catch (Exception e) {
			
			System.out.println("Error");
			
			e.getMessage();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","SAVE EST AVG DATA","MANUAL ESTIMATE METER DATA",e.toString(),lastmodfydate);

			return null;
		}
		
		
	}

	@Override
	public List<Object[]> getEstimatedlastYearValue(String meterNumber,
			String fromDate, String toDate, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
