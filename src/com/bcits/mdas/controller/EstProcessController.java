/**
 * 
 */
package com.bcits.mdas.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.controller.DataExchangeController;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.entity.EstimationProcessRptEntity;
import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.LoadSurveyMonthlyConsumptionEntity;
import com.bcits.mdas.entity.LoadSurveyMonthlyConsumptionEntity.KeyLoadServyUniqueId;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.EstimationProcessReportService;
import com.bcits.mdas.service.EstimationRuleService;
import com.bcits.mdas.service.LoadSurveyMonthlyConsumptionService;

/**
 * @author Tarik
 *
 */
@Controller
public class EstProcessController {
	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private EstimationRuleService estservice;

	//@Autowired
	//private AssignValidationRuleService asgveeservice;
	
	//@Autowired
	//private AssignEstimationRuleService asgestservice;

	//@Autowired
	//private ValidationProcessReportService veeprocservice;

	//@Autowired
	//private FeederMasterService feederMasterService;
	
	@Autowired
	private AmrLoadService amrloadservice;
	
	@Autowired
	private EstimationProcessReportService estprocessservice;
	
	@Autowired
	private LoadSurveyMonthlyConsumptionService loadSurveyConservice;
	
	@Autowired
	DataExchangeController dec;
	


	@RequestMapping(value = "/averageofIPProcess", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String averageIPProcess(HttpServletRequest request, Model model) {
		
		String msg = "";
		
		String fromdate = request.getParameter("fromdate");
		String toDate = request.getParameter("todate");

		try {
		averageofIPProcess(fromdate,toDate);
//		lastYearValueProcess("2019-06-01", "2019-06-27");
		msg = "success";
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "failed";
			
		}
		
		return msg;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> averageofIPProcess(String fromDate, String toDate) {
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

			
//			String sql0 = "select mm.mtrno from meter_data.ml_to_estimation_rule_map vrm,\r\n"
//					+ "meter_data.master_main mm where mm.accno=vrm.location_code and status=1 \r\n" + "and e_rule_id='"
//					+ ruleId + "'";
			String sql0="SELECT * FROM(SELECT COALESCE(m1,COALESCE(m2,m3)) as meterno FROM\r\n" + 
					"(SELECT a.*, f.meterno as m1, d.meterno as m2,c.meterno as m3 FROM \r\n" + 
					"(select location_type,location_code from meter_data.ml_to_estimation_rule_map vrm\r\n" + 
					"where  status=1 and e_rule_id='"+ ruleId + "' GROUP BY location_type,location_code\r\n" + 
					")a LEFT JOIN meter_data.feederdetails f ON a.location_code=f.fdr_id LEFT JOIN meter_data.dtdetails d ON a.location_code=d.dt_id\r\n" + 
					"LEFT JOIN meter_data.consumermaster c ON a.location_code=c.accno)f )g WHERE g.meterno is not NULL AND g.meterno<>'';";
		
			System.out.println("IN AVG============================"+sql0);
			List<Object> meterno = entityManager.createNativeQuery(sql0).getResultList();
			String mtrno = "";
			if (meterno.size() > 0) {
				for (Object item : meterno) {
					mtrno += "'" + item + "',";
				}
				if (mtrno.endsWith(",")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}
				if (mtrno.startsWith("'")) {
					mtrno = mtrno.substring(1);
				}
				if (mtrno.endsWith("'")) {
					mtrno = mtrno.substring(0, mtrno.length() - 1);
				}
				
				
//				String sql = "SELECT ml.*,xy.rdate,xy.missing_time,xy.avgkwh,xy.avgkvah,xy.avgkw,xy.avgkva from(SELECT mm.mtrno,mm.accno,mm.zone,mm.circle,mm.division,mm.subdivision,mm.customer_name,(case \r\n" + 
//						"when mm.fdrcategory in('FEEDER METER','BORDER METER','BOUNDARY METER') then 'FEEDER'  \r\n" + 
//						"when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type \r\n" + 
//						"FROM meter_data.master_main mm )ml INNER JOIN (\r\n" + 
//						"SELECT g.*,h.AVGkwh,h.AVGkvah,h.AVGkw,h.AVGkva FROM\r\n" + 
//						"(SELECT l.meter_number, l.rdate, string_agg(CAST(l.dates as TEXT), ',') as missing_time FROM\r\n" + 
//						"(SELECT m.dates,m.meter_number,m.rdate,m.count FROM\r\n" + 
//						"(SELECT * from(SELECT dates FROM generate_series(CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate+"' as TIMESTAMP),  interval '15 min') AS dates\r\n" + 
//						")a,(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
//						")b WHERE date(a.dates)=b.rdate\r\n" + 
//						")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
//						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"'  GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
//						")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
//						")n ON m.meter_number=n.meter_number AND m.dates=n.read_time WHERE n.meter_number is NULL\r\n" + 
//						")l where l.meter_number in('"+mtrno+"') GROUP BY l.meter_number, l.rdate\r\n" + 
//						")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
//						"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in('"+mtrno+"')\r\n" + 
//						"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
//						")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
//						")xy ON ml.mtrno=xy.meter_number";
				
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
						"(SELECT * from(SELECT dates FROM generate_series(CAST('" + fromDate + "' as TIMESTAMP), CAST('" + toDate+"' as TIMESTAMP),  interval '30 min') AS dates\r\n" + 
						")a,(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")b WHERE date(a.dates)=b.rdate\r\n" + 
						")m LEFT JOIN(SELECT ls.meter_number, ls.read_time FROM meter_data.load_survey ls,\r\n" + 
						"(SELECT meter_number, date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"'  GROUP BY meter_number, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")t WHERE ls.meter_number=t.meter_number AND date(ls.read_time)=t.rdate\r\n" + 
						")n ON m.meter_number=n.meter_number AND m.dates=n.read_time WHERE n.meter_number is NULL\r\n" + 
						")l where l.meter_number in('"+mtrno+"') GROUP BY l.meter_number, l.rdate\r\n" + 
						")g LEFT JOIN (SELECT meter_number,avg(kwh) as AVGkwh,avg(kvah) as AVGkvah,avg(kw) as AVGkw,avg(kva) as AVGkva\r\n" + 
						"FROM (select meter_number, kwh,kvah,kw,kva,date(read_time) as rdate, COUNT(*) as count FROM meter_data.load_survey WHERE date(read_time) BETWEEN '" + fromDate + "' and  '" + toDate+"' and meter_number in('"+mtrno+"')\r\n" + 
						"GROUP BY meter_number, kwh,kvah,kw,kva, date(read_time) having count(*)!=96 AND count(*)!= 48\r\n" + 
						")a GROUP BY meter_number)h ON g.meter_number=h.meter_number\r\n" + 
						")xy ON ml.mtrno=xy.meter_number";
				
				System.out.println("final query= "+sql);
				List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
				if (!list.isEmpty()) {
					for (Object[] item : list) {						
						String strArray[] = item[10].toString().split(",");						
						System.out.println("String converted to String array");
						for(int i=0; i < strArray.length; i++){
//							System.out.println(strArray[i]+" "+item[0].toString());
							try {
							AmrLoadEntity amrld=(AmrLoadEntity) amrloadservice.findEntityByUniId(item[0].toString(), Timestamp.valueOf(strArray[i]));
							if(amrld == null) {
								AmrLoadEntity amrlds= new AmrLoadEntity();
								amrlds.setMyKey(new KeyLoad((String) item[0], Timestamp.valueOf(strArray[i])));
								amrlds.setKwh(Double.parseDouble(String.valueOf(item[11])));
								amrlds.setKvah(Double.parseDouble(String.valueOf(item[12])));
								amrlds.setKw(Double.parseDouble(String.valueOf(item[13])));
								amrlds.setKva(Double.parseDouble(String.valueOf(item[14])));
								amrlds.setTimeStamp(new Timestamp(System.currentTimeMillis()));
								amrlds.setEstimationflag(new Integer(1));
								amrloadservice.update(amrlds);
								
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
								eprt.setEst_kwh(Double.parseDouble(String.valueOf(item[11])));
								eprt.setEst_kvah(Double.parseDouble(String.valueOf(item[12])));
								eprt.setEst_kw(Double.parseDouble(String.valueOf(item[13])));
								eprt.setEst_kva(Double.parseDouble(String.valueOf(item[14])));
								estprocessservice.update(eprt);
							}
							}catch(Exception e) {
								//e.getMessage();
								e.printStackTrace();
								
							}
						
						}						
					}
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.getMessage();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","AVERAGE OF IP PROCESS","ESTIMATE METER DATA",e.toString(),lastmodfydate);
			
			return null;
		}
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<?> lastYearValueProcess(String fromDate, String toDate) {
		String mnthyr = null;
		String premnthyr= null;
		try {
			mnthyr = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
				
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(fromDate));
			cal.add(Calendar.YEAR, -1);
			System.out.println("Previous Year month=="+new SimpleDateFormat("yyyyMM").format(cal.getTime()).toString());
			premnthyr=new SimpleDateFormat("yyyyMM").format(cal.getTime()).toString();
			
			EstimationRuleEntity v = estservice.getESTRuleById("EST02");
			String ruleId = v.getEruleid();
			String rule_name = v.getErulename();
			String condVal  = v.getCondtion();
			String d_Type = v.getData_type();
			String d_Parameter= v.getParameter();
			String Cond = v.getCondtion();

			
			String sql = "select mm.mtrno,mm.zone,mm.circle,mm.division,mm.subdivision,vrm.location_code,(case \r\n" + 
					"when mm.fdrcategory in('FEEDER METER','BORDER METER') then 'FEEDER'  \r\n" + 
					"when mm.fdrcategory='DT' then 'DT' else 'Consumer' end) as location_type,\r\n" + 
					"mm.customer_name from meter_data.ml_to_estimation_rule_map vrm,\r\n" + 
					"meter_data.master_main mm where mm.accno=vrm.location_code \r\n" + 
					"and status=1 and e_rule_id='"+ruleId+"'";
			System.out.println(sql);
			List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
			if (!list.isEmpty()) {
				for (Object[] item : list) {	
					System.out.println(item[0].toString());
					LoadSurveyMonthlyConsumptionEntity vpr = loadSurveyConservice.getAssignRuleId(item[0].toString(),mnthyr);
					if (vpr == null) {				
						LoadSurveyMonthlyConsumptionEntity lsmc = loadSurveyConservice.getAssignRuleId(item[0].toString(),premnthyr);
						if (lsmc != null) {
							LoadSurveyMonthlyConsumptionEntity lsmce = new LoadSurveyMonthlyConsumptionEntity();
							lsmce.setMyKey(new KeyLoadServyUniqueId((String) item[0], mnthyr));
							lsmce.setAccno(lsmc.getAccno());;
							lsmce.setKno(lsmc.getKno());
							lsmce.setKva(lsmc.getKva());
							lsmce.setKvah(lsmc.getKvah());
							lsmce.setKwh(lsmc.getKwh());					
							loadSurveyConservice.update(lsmce);
							
							EstimationProcessRptEntity eprt=new EstimationProcessRptEntity();
							eprt.setRule_id(ruleId);
							eprt.setRulename(rule_name);
							eprt.setMeter_number(item[0].toString());
							eprt.setEst_app_date(new Timestamp(System.currentTimeMillis()));
							eprt.setZone((String) item[1]);
							eprt.setCircle((String) item[2]);
							eprt.setDivision((String) item[3]);
							eprt.setSubdivision((String) item[4]);
							eprt.setLocation_type((String) item[6]);
							eprt.setLocation_id((String) item[5]);
							eprt.setLocation_name((String) item[7]);
							eprt.setEst_from(fromDate);
							eprt.setEst_to(toDate);
							eprt.setData_type(d_Type);
							eprt.setParameter(d_Parameter);
							eprt.setEst_kwh(lsmc.getKwh());
							eprt.setEst_kvah(lsmc.getKvah());
							eprt.setEst_kva(lsmc.getKva());
							estprocessservice.save(eprt);								
						}
					}

				}
				return list;	
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","LAST YEAR VALUE PROCESS","ESTIMATE METER DATA",e.toString(),lastmodfydate);

			return null;
		}
	}
	//@Transactional
	@RequestMapping(value="/processAvgIpData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object estimateIpData(HttpServletRequest request){
		String meterNumber=request.getParameter("gMtrNo");
		String fromDate=request.getParameter("gfromDate");
		String toDate=request.getParameter("gToDate");
		System.out.println("in process of ip data===="+meterNumber);
		List<Object[]> list=new ArrayList<>();
		return list=estprocessservice.getEstimatedIpValue(meterNumber, fromDate, toDate,request);
		
	}
	@RequestMapping(value="/processlastYearData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object estimateLastYearValueData(HttpServletRequest request){
		String meterNumber=request.getParameter("gMtrNo");
		String fromDate=request.getParameter("gfromDate");
		String toDate=request.getParameter("gToDate");
		System.out.println("in process of ip data===="+meterNumber);
		List<Object[]> list=new ArrayList<>();
		return list=estprocessservice.getEstimatedlastYearValue(meterNumber, fromDate, toDate,request);
		
	}
	@RequestMapping(value="/saveEstimatedData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object saveIpData(HttpServletRequest request) throws JSONException{
		String est_data=request.getParameter("estimatedavgIpData");
		String fromDate=request.getParameter("gfromDate");
		String toDate=request.getParameter("gToDate");
		//System.out.println(fromDate+"hg"+toDate);
		String kwhData=request.getParameter("kwhData");
		String kvahData=request.getParameter("kvahData");
		String kwData=request.getParameter("kwData");
		String kvaData=request.getParameter("kvaData");
		System.err.println(kwhData+"  "+kvahData+"   "+kwData+"   "+kvaData);
		String kwhArr[]=kwhData.split(",");
		String kvahArr[]=kvahData.split(",");
		String kwArr[]=kwData.split(",");
		String kvaArr[]=kvaData.split(",");
		
		List<Object[]> list=new ArrayList<>();
		JSONArray records = new JSONArray();
		try {
			records = new JSONArray(est_data.toString());
			if (records != null) { 
				   for (int i=0;i<records.length();i++){ 
					   //System.out.println("list===="+records.get(i));
					   
					   JSONArray ary=new JSONArray(records.get(i).toString());
					   
					   Object[] objAry=new Object[ary.length()];
					   
					   for (int j=0;j<ary.length();j++){
						   objAry[j]=ary.get(j);
					   }
					   
					   //System.out.println("list===="+records.get(i+1));
				    list.add(objAry);
				   } 
			}
			//System.out.println(list.size()+"jsonlist");
			String mesg="Estimation Data Save Successfully";
			  List<Object[]> Datalist=estprocessservice.saveEstAvgData(list, fromDate,
			  toDate, request,kwhArr,kvahArr,kwArr,kvaArr); 
			  if(Datalist==null) {
			  mesg="Estimation Data Fail to Save"; 
			  }
			 
		
			return mesg;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());	
			dec.Servicelog("DATA VALIDATION AND ESIMATION","SAVE ESTIMATED DATA","ESTIMATE METER DATA",e.toString(),lastmodfydate);
			
			return "Estimation Data Fail to Save";
		}
		
	}
	 @RequestMapping(value = "/testLoad", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody String testLoad(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
			
			AmrLoadEntity amrld=(AmrLoadEntity) amrloadservice.findEntityByUniId("4613417", Timestamp.valueOf("2019-05-20 16:00:00"));
			ObjectMapper map=new ObjectMapper();
			String obj=map.writeValueAsString(amrld);
			System.out.println(obj);
			return obj;
			
		}
	
	

}
