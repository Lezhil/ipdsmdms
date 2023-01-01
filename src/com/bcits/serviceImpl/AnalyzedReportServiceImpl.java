package com.bcits.serviceImpl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.Master;
import com.bcits.entity.MeterMasterExtra;
import com.bcits.service.AnalyzedReportService;




@Repository
public class AnalyzedReportServiceImpl extends GenericServiceImpl<Master> implements AnalyzedReportService {

	@Override
	public List<?> getCmriListData(String circle, String rdngMonth, String sdoname)
	{ 
		List<?> cmriList=null;
		try {
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);

		String qry="select circle,sdocode,subdiv,tadesc,ACCNO,METRNO ,name,MNP,to_char(to_date('"+rdngMonth+"', 'yyyyMM')+ interval '0 month','yyyyMM') "
				+ " as RDNGMOTNH   from meter_data.mm WHERE rdngmonth='"+rdngMonth+"' and rtc='1' and CIRCLE like '"+circle+"' and subdiv like '"+sdoname+"' "
				+ " and cst like 'R'  order by accno ";
			
			System.out.println("cmriList  qry====="+qry);
			cmriList=postgresMdas.createNativeQuery(qry).getResultList();
			
			
			System.out.println("cmriList  size====="+cmriList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmriList;
	}


	@Override
	public List<?> getCNPListData(String circle, String rdngMonth, String sdoname)
	{
		List<?> cnpList=null;
		
		try {
			String qry=" select circle,subdiv,tadesc,mnp,accno,metrno,readingremark,name,address1,mrname from meter_data.mm where rdngmonth='"+rdngMonth+"' "
					+ " and ( READINGREMARK  like '%NOT%' or   READINGREMARK  like '%HEIGHT%' )and  READINGREMARK not like '%METER%CHANG%'"
					+ " and READINGREMARK not like '%SUPPLY NOT AVAILABLE%' and readingremark not like 'DISPLAY NOT VISIBLE'  and rtc='0' and "
					+ " CIRCLE like '"+circle+"' and subdiv like '"+sdoname+"'   order by circle,accno";
			System.out.println("cnpList  qry====="+qry);
			cnpList=postgresMdas.createNativeQuery(qry).getResultList();
			
			System.out.println("cnpList  size====="+cnpList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnpList;
	}

	
	@Override
	public List<?> getDateWiseListData(String circle, String rdngMonth, String sdoname) {
		List<?> datewiseList=null;
		 ArrayList<Map<String, Object>> list1=new ArrayList<>();
		try {
			
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
			 
			// int rdngMonth1=Integer.parseInt(rdngMonth)-1;
			

			/* String qry = "SELECT	M .circle,	M .subdiv, mm.rdngmonth	,M .accno,	M . NAME,	M .mnp,	mm.metrno,	M.contractdemand,LS.read_time,30 as INTERVAL_PERIOD,0 as max_kva, mm.mf, LS.kvah, 0 as SUM_PF, 0 as SUM_KWH,	M .address1 AS address \n" +
			 "FROM	meter_data.master M,	meter_data.metermaster mm, meter_data.load_survey LS WHERE mm.accno = M .accno AND   mm.metrno =LS.meter_number AND M .circle LIKE '"+circle+"'  AND M .subdiv LIKE '"+sdoname+"' \n" +
			 "AND to_char(LS.read_time, 'YYYYMM')='"+rdngMonth+"' AND to_char(LS.read_time,'yyyyMM') <= to_char(to_date('"+rdngMonth+"', 'yyyyMM') + INTERVAL '0 month','yyyyMM')\n" +
			 "AND to_char(LS.read_time,	'yyyyMM') >= to_char(to_date('"+rdngMonth+"', 'yyyyMM') - INTERVAL '1 month','yyyyMM') ORDER BY mm.metrno,LS.read_time";
			*/ 
			 String qry=" SELECT	M.circle,M .subdiv, mm.rdngmonth,M.accno,M.NAME,	mm.metrno,	M.contractdemand,LS.read_time, LS.kwh, LS.kvah,LS.i_r,LS.i_y,LS.i_b, \n" +
					 "					 		 	M .address1 AS address  \n" +
					 "					 		 FROM	meter_data.master M,	meter_data.metermaster mm, meter_data.load_survey LS WHERE mm.accno = M .accno AND \n" +
					 "					 		 mm.metrno =LS.meter_number AND M .circle LIKE '"+circle+"'  AND M .subdiv LIKE '"+sdoname+"'  \n" +
					 "					 		 AND to_char(LS.read_time, 'YYYYMM')='"+rdngMonth+"' AND mm.rdngmonth='"+rdngMonth+"' ORDER BY mm.metrno,LS.read_time; ;\n" +
					 "			 ";
			 
			 System.out.println("datewiseList  qry====="+qry);
			 
			 
			 
			datewiseList=postgresMdas.createNativeQuery(qry).getResultList();
			
			System.out.println("datewiseList  size====="+datewiseList.size());
			
			DecimalFormat df=new DecimalFormat(".##");
			
		    for (Iterator iterator1 = datewiseList.iterator(); iterator1.hasNext();)
		    {
		    	Map<String, Object> data=new HashMap<>();
		    	Object[] obj = (Object[]) iterator1.next();
				 if (datewiseList.size() > 0) 
				 {
					 //Double kwh=null;
					 String kwh="";
					 String kvah="";
					 String ir="";
					 String iy="";
					 String ib="";
					 if(obj[8]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[8]));
						double dkwh= wh/1000;
						kwh=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[9]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[9]));
						double dkwh= wh/1000;
						kvah=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[10]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[10]));
						double dkwh= wh/1000;
						ir=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[11]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[11]));
						double dkwh= wh/1000;
						iy=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[12]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[12]));
						double dkwh= wh/1000;
						ib=new DecimalFormat("#.##").format(dkwh);
					 } 
					 
					 
					 data.put("CIRCLE", obj[0]);
					 data.put("SDONAME", obj[1]);
					 data.put("BILLMONTH", obj[2]);
					 data.put("ACCNO", obj[3]);
					 data.put("NAME", obj[4]);
					 data.put("METERNO", obj[5]);
					 data.put("CD", obj[6]);
					 data.put("DAY_PROFILE_DATE", obj[7]);
					 data.put("WH", kwh);
					 data.put("VAH", kvah);
					 data.put("IR", ir);
					 data.put("IY", iy);
					 data.put("IB", ib);
					 data.put("ADDRESS", obj[13]);
					 list1.add(data);
				 }
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	
	@Override
	public List<?> getDefectiveListData(String circle, String rdngMonth, String sdoname) {
		List<?> defectiveList=null;
	//	String mnp1="";
		
		try {
		String qry=" select circle,subdiv,tadesc,mnp,accno,metrno,readingremark,name,address1,mrname from meter_data.mm where "
					+ " rdngmonth= '"+rdngMonth+"' and (readingremark like '%DEF%'  or readingremark like '%STOP%' or readingremark like"
					+ " '%DISPLAY%'  or readingremark like '%BURNT%' or readingremark like '%HANG%' or READINGREMARK  "
					+ " like '%NOT%' or READINGREMARK  like '%LINE%' or READINGREMARK  like '%HEIGHT%' )and  READINGREMARK not like '%METER%CHANG%'"
					+ " and READINGREMARK not like '%SUPPLY NOT AVAILABLE%'  and rtc='0' and CIRCLE like '"+circle+"' and subdiv like '"+sdoname+"' "
					+ " order by circle,accno";

			defectiveList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("defectiveList  qry====="+qry);
			System.out.println("defectiveList  size====="+defectiveList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defectiveList;
	}

	
	@Override
	public List<?> getEnergyWiseListData(String circle, String rdngMonth, String sdoname) {
		List<?> energywiseList=null;
		ArrayList<Map<String, Object>> list1=new ArrayList<>();
		try {
			
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			
			DateFormat format = new SimpleDateFormat("yyyyMM");
			Date date = format.parse(rdngMonth);
			 
		
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String  last=format1.format(date);
			
			
		 String qry="select mm.circle,m.subdiv,m.accno,m.name,bh.meter_number,\n" +
				 "bh.billing_date,bh.kwh,bh.kvah,bh.kva from  meter_data.bill_history bh, meter_data.metermaster mm ,meter_data.master m \n" +
				 "where bh.billing_date ='"+last+"' and  mm.metrno=bh.meter_number and m.accno=mm.accno\n" +
				 "and m.circle like '"+circle+"' and m.subdiv like '"+sdoname+"' and mm.metrno=bh.meter_number ORDER BY bh.billing_date";
			
			
		System.out.println("energywiseList  qry====="+qry);
			energywiseList=postgresMdas.createNativeQuery(qry).getResultList();
			
			for (Iterator iterator1 = energywiseList.iterator(); iterator1.hasNext();)
		    {
		    	Map<String, Object> data=new HashMap<>();
		    	Object[] obj = (Object[]) iterator1.next();
				 if (energywiseList.size() > 0) 
				 {
					 
				 }
		    }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public List<?> getEventWiseListData(String circle, String rdngMonth, String sdoname) {
		List<?> eventwiseList=null;
		
		try {
			
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
			
			
		String qry="SELECT distinct m.accno,m.CIRCLE,m.subdiv,m.name,m.mnp,mm.metrno, d9.datetime as transaction_time,d9.d9_id,"
				+ " d9.cdf_id,d9.transaction_code, t.transaction from mdm_test.d9_data d9,mdm_test.master m, mdm_test.metermaster mm, mdm_test.cdf_data c ,"
				+ " mdm_test.transaction_master t where c.cdf_id= d9.cdf_id and mm.accno=m.accno and c.accno=m.accno and "
				+ " cast(T .transaction_code as VARCHAR)=d9.transaction_code and  c.accno=mm.accno and c.meterno=mm.metrno and"
				+ " mm.rdngmonth=c.billmonth and c.billmonth='"+rdngMonth+"' "
				+ " AND to_number(to_char(d9.datetime, 'yyyyMM'),'999999') <= to_number(to_char(to_date('"+rdngMonth+"', 'yyyyMM') + INTERVAL '0 month','yyyyMM'),	'999999') and "
				+ " m.CIRCLE like '"+circle+"' and m.subdiv like '"+sdoname+"' ";
			 			 
			 
			/* String qry="SELECT  distinct m.accno,m.CIRCLE,m.sdoname,m.name,m.mnp,mm.metrno, d9.datetime as transaction_time,d9.d9_id, d9.cdf_id,d9.transaction_code,\n" +
					 " t.transaction from d9_data d9,master m, metermaster mm, cdf_data c , transaction_master t \n" +
					 "where c.cdf_id= d9.cdf_id and mm.accno=m.accno and c.accno=m.accno and  t.transaction_code=d9.transaction_code \n" +
					 "and  c.accno=mm.accno and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and \n" +
					 "c.billmonth="+rdngMonth+"\n" +
					 " and  to_char(d9.datetime,'yyyyMM') <= "+rdngMonth+"\n" +
					 " and  m.CIRCLE LIKE '"+circle+"' and m.SDONAME LIKE '"+sdoname+"' AND m.mnp like '"+mnp+"' ";*/


		eventwiseList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("eventwiseList  qry====="+qry);
			System.out.println("eventwiseList  size====="+eventwiseList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventwiseList;
	}

	@Override
	public List<?> getFaultyListData(String circle, String rdngMonth, String sdoname) {
		List<?> faultyList=null;
		
		try {
			
		/*String qry="SELECT a.CIRCLE,a.SDONAME, b.RDNGMONTH,a.SDOCODE,a.ACCNO,METRNO,NAME ,MTRMAKE,MNP,TAMPERTYPE,TDATE  from "
				+ " MASTER a,METERMASTER b, TAMPER c where a.accno=b.accno and c.meterno=b.metrno and consumerstatus like 'R' "
				+ " and b.RDNGMONTH='"+rdngMonth+"' and "
				+ " c.RDNGMONTH='"+rdngMonth+"' AND CIRCLE like '"+circle+"' "
				+ " and SDONAME like '"+sdoname+"' and mnp like '"+mnp+"' ";*/
		
		String qry="SELECT CIRCLE,subdiv,tadesc,accno,meterno,TAMPERTYPE,OCCURRED_DATE,RESTORED_DATE,name,mnp from mdm_test.TMP "
				+ " where rdngmonth='"+rdngMonth+"' and circle like '"+circle+"' and subdiv like '"+sdoname+"'  order by accno";
			
		    faultyList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("faultyList  qry====="+qry);
			System.out.println("faultyList  size====="+faultyList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return faultyList;
	}

	@Override
	public List<?> getIndexUsageListData(String circle, String rdngMonth, String sdoname) {
		List<?> usageList=null;
		
		try {
			
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
			
			
			 String qry= "SELECT 	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,	M .mnp,	mm.metrno,	mm.XCURRDNGKVA,	M.contractdemand,	ls.read_time,	30 as interval_period,	0 as max_kva,	mm.mf,	0 as min_kva,	0 as sum_kwh,	0 as SUM_KVA FROM 	meter_data.master M,	meter_data.metermaster mm,	meter_data.load_survey ls \n" +
			 "WHERE mm.accno = M .accno AND mm.metrno = ls.meter_number AND mm.rdngmonth = '"+rdngMonth+"' AND to_char(ls.read_time,	'yyyyMM') <= to_char(to_date('"+rdngMonth+"', 'yyyyMM') + INTERVAL '0 month','yyyyMM')\n" +
			 " AND to_char(	ls.read_time,'yyyyMM') >= to_char(to_date('"+rdngMonth+"', 'yyyyMM') - INTERVAL '1 month',	'yyyyMM') AND M .CIRCLE LIKE '"+circle+"' AND M .subdiv LIKE '"+sdoname+"' " ;
			 
			 
		/*String qry="SELECT M.CIRCLE,M.subdiv,c.cdf_id,m.accno,m.name,m.mnp,mm.metrno,mm.XCURRDNGKVA,d4.cd,d4.day_profile_date,"
				+ " d4.interval_period, d4.max_kva, d4.mf,d4.min_kva,d4.sum_kwh,d4.SUM_KVA from mdm_test.d4_data d4,mdm_test.master m, mdm_test.metermaster"
				+ " mm, mdm_test.cdf_data c where c.cdf_id= d4.cdf_id and mm.accno=m.accno and c.accno=m.accno and c.accno=mm.accno and"
				+ " c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and c.billmonth= '"+rdngMonth+"' and  "
				+ " to_char(d4.DAY_PROFILE_DATE,'yyyyMM') <= to_char(to_date('"+rdngMonth+"', 'yyyyMM')+ interval '0 month','yyyyMM') "
				+ " and to_char(d4.DAY_PROFILE_DATE,'yyyyMM') >= to_char(to_date('"+rdngMonth+"', 'yyyyMM')- interval '1 month','yyyyMM')"
				+ " and  M.CIRCLE like '"+circle+"' AND M.subdiv like '"+sdoname+"' " ;
*/
		    usageList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("usageList  qry====="+qry);
			System.out.println("usageList  size====="+usageList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usageList;
	}

	@Override
	public List<?> getLoadUtilizationData(String circle, String rdngMonth, String sdoname) {
		List<?> loadList=null;
	
		
		try {
			
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
		//	int rdngMonth1=Integer.parseInt(rdngMonth)-1;
			
			 
		 String qry = "SELECT	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,	M .mnp,	mm.metrno,	30 as interval_period,	LS.read_time,	mm.mf,	m.contractdemand,	0 as ip_gs_20,	0 as ip_gs_20_40,	0 as ip_gs_40_60,	0 as ip_gs_60,	0 as ip_out_gs_20,	0 as ip_out_gs_20_40,	0 as ip_out_gs_40_60,	0 as ip_out_gs_60 \n" +
				 "FROM	meter_data.master M,	meter_data.metermaster mm,	meter_data.load_survey LS	WHERE mm.accno = M .accno AND  mm.metrno = LS.meter_number AND mm.rdngmonth = '"+rdngMonth+"'\n" +
				 "AND to_char(LS.read_time,	'yyyyMM') <= to_char(	to_date('"+rdngMonth+"', 'yyyyMM') + INTERVAL '0 month',	'yyyyMM') AND to_char(LS.read_time,	'yyyyMM') >= to_char(	to_date('"+rdngMonth+"', 'yyyyMM') - INTERVAL '1 month','yyyyMM') AND M .CIRCLE LIKE '"+circle+"' AND M .subdiv LIKE '"+sdoname+"' ";
			 
		/*String qry="select m.CIRCLE,m.subdiv,c.cdf_id,m.accno,m.name,m.mnp,mm.metrno,d4.interval_period,d4.day_profile_date,"
			+ " d4.mf,d4.cd,d4.ip_gs_20,d4.ip_gs_20_40,d4.ip_gs_40_60,d4.ip_gs_60,d4.ip_out_gs_20,"
			+ " d4.ip_out_gs_20_40,d4.ip_out_gs_40_60,d4.ip_out_gs_60 from mdm_test.d4_data d4,mdm_test.master m, mdm_test.metermaster mm, mdm_test.cdf_data c where"
			+ " c.cdf_id= d4.cdf_id and mm.accno=m.accno and c.accno=m.accno and c.accno=mm.accno and c.meterno=mm.metrno"
			+ " and mm.rdngmonth=c.billmonth and c.billmonth='"+rdngMonth+"' and "
			+ " to_char(d4.DAY_PROFILE_DATE,'yyyyMM')  <= to_char(to_date('"+rdngMonth+"', 'yyyyMM')+ interval '0 month','yyyyMM') "
			+ " and to_char(d4.DAY_PROFILE_DATE,'yyyyMM') >= to_char(to_date('"+rdngMonth+"', 'yyyyMM')- interval '1 month','yyyyMM')and "
			+ " M.CIRCLE like '"+circle+"' AND M.subdiv like '"+sdoname+"' ";*/


			/* String qry="select m.CIRCLE,m.SDONAME,c.cdf_id,m.accno,m.name,m.mnp,mm.metrno,d4.interval_period,d4.day_profile_date, \n" +
					 "d4.mf,d4.cd,d4.ip_gs_20,d4.ip_gs_20_40,d4.ip_gs_40_60,d4.ip_gs_60,d4.ip_out_gs_20,\n" +
					 " d4.ip_out_gs_20_40,d4.ip_out_gs_40_60,d4.ip_out_gs_60 from d4_data d4,master m, \n" +
					 "metermaster mm, cdf_data c where c.cdf_id= d4.cdf_id and mm.accno=m.accno and \n" +
					 "c.accno=m.accno and c.accno=mm.accno and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth \n" +
					 "and c.billmonth="+rdngMonth+" \n" +
					 "  and to_char(d4.day_profile_date,'yyyyMM')  >= "+rdngMonth1+"\n" +
					 "  and to_char(d4.day_profile_date,'yyyyMM')  <= "+rdngMonth+"\n" +
					 " and  M.CIRCLE LIKE '"+circle+"' AND M.SDONAME LIKE '"+sdoname+"' AND m.mnp like '"+mnp+"' ";*/
					
			 
		loadList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("loadList  qry====="+qry);
			System.out.println("loadList  size====="+loadList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loadList;
	}

	@Override
	public List<?> getManualReportData(String circle, String rdngMonth, String sdoname) {
		
		List<?> manualList=null;
	
		try {

		String qry=" select circle,sdocode,subdiv,tadesc,accno,metrno,name,address1,readingremark,case when "
				+ " CURRDNGKWH<>'99999999' then CURRDNGKWH end as kwh,case when CURRRDNGKVAH<>'99999999' then CURRRDNGKVAH "
				+ " end as kvah,case when CURRDNGKVA<>'99999999' then CURRDNGKVA  end as kva,MRNAME from meter_data.mm where rdngmonth='"+rdngMonth+"' "
				+ " and cst like 'R' and rtc='0'  "
				+ " and circle like '"+circle+"' and subdiv like '"+sdoname+"' order by accno ";

		    manualList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("manualList  qry====="+qry);
			System.out.println("manualList  size====="+manualList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manualList;
	}

	@Override
	public List<?> getOtherMakeReportData(String circle, String rdngMonth, String sdoname) {
		List<?> otherList=null;
		
		try {
			
		String qry="SELECT A.CIRCLE, b.rdngmonth,a.sdocode,a.subdiv,a.ACCNO,METRNO,name ,mtrmake,MNP from  meter_data.MASTER a,meter_data.METERMASTER b  "
				+ " where a.accno=b.accno and rtc='0' and consumerstatus like 'R' and (mtrmake like 'AVON' and length(metrno)<7  "
				+ " or mtrmake in ('ABB','DATAPRO','ELYMER','DUKE','BHEL','JME','LnTOLD','SYNERGY','TTL') )"
				+ " and RDNGMONTH='"+rdngMonth+"' AND "
				+ " A.CIRCLE like '"+circle+"' AND A.subdiv like '"+sdoname+"'  ";

		    otherList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("otherList  qry====="+qry);
			System.out.println("otherList  size====="+otherList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return otherList;
	}

	@Override
	public List<?> getPowerFactorData(String circle, String rdngMonth, String sdoname) {
		List<?> pfList=null;
		ArrayList<Map<String, Object>> list1=new ArrayList<>();
		try {
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println("rdngMonth-->"+rdngMonth);
			 
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
			String qry="";
			
		//	int rdngMonth1=Integer.parseInt(rdngMonth)-1;
			
			/* qry=	"SELECT 	M .CIRCLE, 	M .subdiv, 	M .accno,  	M . NAME,  	M .mnp, 	mm.metrno,	mm.xpf,	0 as sum_pf,	0 as pf_05,	0 as pf_05_07,	0 as pf_07_09,	0 as pf_09,	0 as pf_noload,	0 as pf_blackout,	30 as interval_period,	ls.read_time FROM meter_data.master M, 	meter_data.metermaster mm, 	meter_data.load_survey ls \n" +
			" WHERE mm.accno = M .accno AND mm.metrno = ls.meter_number  AND to_char(LS.read_time, 'YYYYMM')='"+rdngMonth+"' AND to_char(ls.read_time,'yyyyMM') <= to_char(	to_date('"+rdngMonth+"', 'yyyyMM') + INTERVAL '0 month','yyyyMM') AND to_char(	ls.read_time, 	'yyyyMM' ) >= to_char(to_date('"+rdngMonth+"', 'yyyyMM') - INTERVAL '1 month',	'yyyyMM')\n" +
			"AND M .CIRCLE LIKE  '"+circle+"' AND M .subdiv LIKE '"+sdoname+"' " ;
			*/
			
			qry="SELECT	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,		mm.metrno,I.read_time,I.pf_r,	I.pf_y,	I.pf_b,	I.pf_threephase ,I.frequency,	I.kvah,	I.kva\n" +
						"FROM 	meter_data.master M,	meter_data.metermaster mm,	meter_data.amiinstantaneous I \n" +
						"WHERE mm.accno = M .accno AND I.meter_number = mm.metrno AND M .CIRCLE LIKE '"+circle+"'  AND M .subdiv LIKE  '"+sdoname+"'\n" +
						"AND mm.rdngmonth = '"+rdngMonth+"' ORDER BY I.read_time";
			System.out.println("pfList  qry====="+qry);

		    pfList=postgresMdas.createNativeQuery(qry).getResultList();
			
			for (Iterator iterator1 = pfList.iterator(); iterator1.hasNext();)
		    {
		    	Map<String, Object> data=new HashMap<>();
		    	Object[] obj = (Object[]) iterator1.next();
				 if (pfList.size() > 0) 
				 {
					 //Double kwh=null;
					 String kva="";
					 String kvah="";
					 String pfr="";
					 String pfy="";
					 String pfb="";
					 String pfthreephase="";
					 String frequency="";
					
					 if(obj[6]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[6]));
						//double dkwh= wh/1000;
						pfr=new DecimalFormat("#.##").format(wh);
					 } 
					 if(obj[7]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[7]));
						//double dkwh= wh/1000;
						pfy=new DecimalFormat("#.##").format(wh);
					 } 
					 if(obj[8]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[8]));
						//double dkwh= wh/1000;
						pfb=new DecimalFormat("#.##").format(wh);
					 } 
					 if(obj[9]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[9]));
						//double dkwh= wh/1000;
						pfthreephase=new DecimalFormat("#.##").format(wh);
					 } 
					 if(obj[10]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[10]));
						frequency=new DecimalFormat("#.##").format(wh);
					 } 
					 
					 
					 data.put("CIRCLE", obj[0]);
					 data.put("SDONAME", obj[1]);
					 data.put("ACCNO", obj[2]);
					 data.put("NAME", obj[3]);
					 data.put("METERNO", obj[4]);
					 data.put("READ_TIME", obj[5]);
					 data.put("PFR", pfr);
					 data.put("PFY", pfy);
					 data.put("PFB", pfb);
					 data.put("PF_3PHASE", pfthreephase);
					 data.put("FREQUENCY", frequency);
					
					 list1.add(data);
				 }
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public List<?> getMeterChangeData(String circle, String rdngMonth, String sdoname) {
		List<?> mtrChangeList=null;
		//String mnp1="";
		
		try {
			SimpleDateFormat  s=new SimpleDateFormat("yyyyMM");
			Date d=null;
			try {
				d=s.parse(rdngMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
			
			 String fmtMonth=dateFormat.format(d);
			 System.out.println(d);
			 System.out.println("fmtMonth====>"+fmtMonth);
			 
			 String qry="SELECT circle,subdiv,tadesc,accno,MNP,metrno,xmldate,readingremark,substr(remark,14,8) as newmeter,remark,mrname  from meter_data.mm "
			 		+ "where rdngmonth ='"+rdngMonth+"' and remark like 'METER CHANGE%' and substr(remark,14,8)<>metrno AND CIRCLE like '"+circle+"' "
			 		+ "and subdiv  like '"+sdoname+"'  ORDER BY circle,mrname,newmeter "; 


		    mtrChangeList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("mtrChangeList  qry====="+qry);
			System.out.println("mtrChangeList  size====="+mtrChangeList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtrChangeList;
	}

	@Override
	public List<?> getStaticClassData(String circle, String rdngMonth, String sdoname) {
		List<?> classList=null;
	
		try {
			
			String qry="SELECT M.CIRCLE,	M.subdiv,	MM.accno,	MM .metrno,	M . NAME,	M .mnp,	MM.mtrclass FROM meter_data.master M,meter_data.metermaster MM \n" +
					"WHERE  M.accno = MM.accno AND M .CIRCLE LIKE '"+circle+"' AND M .subdiv LIKE '"+sdoname+"' AND MM .rdngmonth = '"+rdngMonth+"' ";
					
		/*String qry=" SELECT m.CIRCLE,m.subdiv,c.cdf_id,c.accno,c.meterno,m.name , m.mnp,d.meter_class"
				+ " from mdm_test.cdf_data c , mdm_test.d1_data d, mdm_test.master m where c.cdf_id=d.cdf_id and c.accno= m.accno and "
				+ " m.CIRCLE like '"+circle+"' and m.subdiv like '"+sdoname+"'  and "
				+ " c.billmonth='"+rdngMonth+"' order by c.cdf_id ";*/

		    classList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("classList  qry====="+qry);
			System.out.println("classList  size====="+classList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classList;
	}

	@Override
	public List<?> getTamperReportData(String circle, String rdngMonth, String sdoname,String division,String TownCode) {
		List<?> tamperList=null;
		String qry1="";

		try{
			qry1="select evf.*,mdm.kno,mdm.accno,mdm.customer_name,mdm.subdivision\n" +
					" from (select em.meter_number,em.event_code,(select ema.event from meter_data.event_master ema \n" +
					"where ema.event_code=to_number(em.event_code,'9999')),event_time, ( case when (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=1 or (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=0  then to_char(em.event_time-lag(em.event_time, 1) OVER (ORDER BY  em.meter_number,em.event_time,em.id desc),'DDD HH24:MI:SS')  END) as duration from meter_data.events em\n" +
					"where to_char(em.event_time,'yyyyMM')='"+rdngMonth+"' and to_number(em.event_code,'999') in (select ems.event_code from meter_data.event_master ems where ems.category='Tamper') and em.meter_number in (select mdmi.mtrno from meter_data.master_main mdmi where  mdmi.circle like '"+circle+"' and mdmi.division like '"+ division+"' and mdmi.subdivision like '"+sdoname+"' and mdmi.town_code like  '"+TownCode+"')\n" +
					"GROUP BY em.meter_number,em.event_time,em.id,em.event_code\n" +
					"ORDER BY em.meter_number,em.event_time,em.id desc ) evf left join meter_data.master_main mdm on mdm.mtrno=evf.meter_number";
			/*qry1=" select evf.*,mdm.kno,mdm.accno,mdm.consumername,mdm.rdngmonth,mdm.subdiv from (select em.meter_number,em.event_code,(select ema.event from meter_data.event_master ema where ema.event_code=to_number(em.event_code,'9999')),event_time, ( case when"
					+ " (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=1 or (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=0  then to_char(em.event_time-lag(em.event_time, 1) OVER (ORDER BY  em.meter_number,em.event_time,em.id desc),'DDD HH24:MI:SS')  END) as duration from meter_data.events em where to_char(em.event_time,'yyyyMM')='"+rdngMonth+"' and to_number(em.event_code,'999') in (select ems.event_code from meter_data.event_master ems where ems.category='Tamper') and em.meter_number in (select mdmi.metrno from meter_data.metermaster mdmi where mdmi.rdngmonth='"+rdngMonth+"' and  mdmi.circle like '%' and mdmi.division like '%' and mdmi.subdiv like '"+sdoname+"' )\n" +
					"ORDER BY em.meter_number,em.event_time,em.id desc ) evf left join meter_data.metermaster mdm on mdm.metrno=evf.meter_number  and mdm.rdngmonth='"+rdngMonth+"'";
		*/
			/*qry1="select\n" +
					"(select accno from meter_data.metermaster where rdngmonth=201902 and metrno=meter_number ), \n" +
					"(select kno from meter_data.metermaster where rdngmonth=201902 and metrno=meter_number ), \n" +
					"(select subdiv from meter_data.metermaster where rdngmonth=201902 and metrno=meter_number ), \n" +
					"(select consumername from meter_data.metermaster where rdngmonth=201902 and metrno=meter_number ), \n" +
					"meter_number,event_code,event_time,\n" +
					"( case when(to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER\n" +
					"(ORDER BY event_time))=1 then (event_time-lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration from meter_data.events\n" +
					"where to_number(event_code,'999') in\n" +
					"(select event_code from meter_data.event_master where category='Tamper') and to_char(event_time,'yyyyMM')='201902'\n" +
					"and meter_number in (select metrno from meter_data.metermaster where rdngmonth=201902)\n" +
					"ORDER BY meter_number,event_time desc";*/
					/*String qry1="SELECT M.accno,M.CIRCLE,	M.subdiv,	M.NAME,mm.metrno,ev.event_time,ev.event_code,em.event FROM\n" +
					"	(SELECT accno,circle,subdiv,NAME FROM meter_data.master)M,\n" +
					"	(select accno,metrno,rdngmonth from meter_data.metermaster)mm,\n" +
					"	(SELECT event,event_code from meter_data.event_master)em,\n" +
					"	(select event_time,meter_number, event_code FROM meter_data.events where event_code IN('"+251+"','"+101+"','"+202+"','"+203+"','"+204+"','"+207+"','"+208+"','"+69+"','"+70+"'))ev\n" +
					"	WHERE  mm.accno = M .accno AND ev.meter_number = mm.metrno  AND CAST(em.event_code AS VARCHAR)=ev.event_code\n" +
					" and  M.CIRCLE LIKE '"+circle+"' AND M .subdiv LIKE '%' AND mm.rdngmonth = '"+rdngMonth+"'  AND to_char(ev.event_time,'yyyyMM')='"+rdngMonth+"'\n" +
					"GROUP BY M .accno,	M.CIRCLE,	M.subdiv,	M.NAME,mm.metrno,ev.event_time,ev.event_code,em.event  ORDER BY mm.metrno,event_time,event_code";
					
			/*String qry  = "SELECT M .accno,	M.CIRCLE,	M.subdiv,	M.NAME,	mm.metrno,ev.event_time AS event_time,ev.event_code,em.event \n" +
						"FROM 	meter_data.master M,	meter_data.metermaster mm,	meter_data.event_master em,	meter_data.events ev\n" +
						"WHERE  mm.accno = M .accno AND ev.meter_number = mm.metrno  AND CAST(em.event_code AS VARCHAR)=ev.event_code\n" +
						" and  M.CIRCLE LIKE '"+circle+"' AND M .subdiv LIKE '%' AND mm .rdngmonth = '"+rdngMonth+"'  AND to_char(ev.event_time,'yyyyMM')='"+rdngMonth+"'\n" +
						" GROUP BY M .accno,	M.CIRCLE,	M.subdiv,	M.NAME,mm.metrno,ev.event_time,ev.event_code,em.event  ORDER BY mm.metrno,event_time,event_code";
					*/
/*	String qry="SELECT distinct (m.accno),m.CIRCLE,m.subdiv,m.name,m.mnp,mm.metrno,d5.event_time as event_time,d5.d5_id,d5.cdf_id,"
		+ " d5.event_code,e.event from mdm_test.d5_data d5,mdm_test.master m, mdm_test.metermaster mm, mdm_test.cdf_data c ,mdm_test.event_master e where"
		+ " c.cdf_id= d5.cdf_id and mm.accno=m.accno and c.accno=m.accno and e.event_code=d5.event_code and  c.accno=mm.accno"
		+ " and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and m.CIRCLE like '"+circle+"' and m.subdiv like '"+sdoname+"' "
		+ " and c.billmonth='"+rdngMonth+"' and d5.event_time is not null order by m.accno ";
*/	
		    tamperList=postgresMdas.createNativeQuery(qry1).getResultList();
		    System.out.println(qry1);
		//	System.out.println("tamperList  size*****S====="+tamperList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tamperList;
	}

	@Override
	public List<?> getTamperHistoryReport(String circle, String rdngMonth, String sdoname ){
		List<?> tamperHistoryList=null;
		String qry="";
		try{
			qry="SELECT A.*,am.tamper_count FROM(select evf.*,mdm.kno,mdm.accno,mdm.consumername,mdm.rdngmonth,mdm.subdiv from (select em.meter_number,em.event_code,(select ema.event from meter_data.event_master ema where ema.event_code=to_number(em.event_code,'9999')),event_time, ( case when (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=1 or (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=0  then to_char(em.event_time-lag(em.event_time, 1) OVER (ORDER BY  em.meter_number,em.event_time,em.id desc),'DDD HH24:MI:SS')  END) as duration from meter_data.events em where to_char(em.event_time,'yyyyMM')='201901' and to_number(em.event_code,'999') in (select ems.event_code from meter_data.event_master ems where ems.category='Tamper') and em.meter_number in (select mdmi.metrno from meter_data.metermaster mdmi where mdmi.rdngmonth='201901' and  mdmi.circle like '%' and mdmi.division like '%' and mdmi.subdiv like '"+sdoname+"' )\n" +
					"ORDER BY em.meter_number,em.event_time,em.id desc ) evf left join meter_data.metermaster mdm on mdm.metrno=evf.meter_number  and mdm.rdngmonth='"+rdngMonth+"')A\n" +
					"INNER JOIN meter_data.amiinstantaneous am ON A.meter_number=am.meter_number and to_char(am.read_time,'YYYYMM')='"+rdngMonth+"'\n" +
					"LIMIT 20000";
		tamperHistoryList=postgresMdas.createNativeQuery(qry).getResultList();
	
		//System.out.println("tamperList  size*****S====="+tamperHistoryList.size());
		System.out.println(qry);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return tamperHistoryList;
	}
	@Override
	public List<?> getTransactionReportData(String circle, String rdngMonth, String sdoname) {
		List<?> transList=null;
		 ArrayList<Map<String, Object>> list1=new ArrayList<>();
		try {
			
			/*String qry= "SELECT	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,	M .mnp,	mm.metrno,	0 as D2_ID_OLD,	0 as CDF_ID_OLD,	I.v_r,	I.v_y,	I.v_b,	I.i_r,	I.i_y,	I.i_b,	I.kvah,	I.kva,	I.i_r_angle,\n" +
					"I.i_y_angle,	I.i_b_angle,	I.i_r,	I.pf_threephase,	I.i_b,	I.i_r,	mm.MF, 0 as  D2_ID,	0 as CDF_ID,	I.pf_y,	I.pf_b,	I.pf_threephase,\n" +
					"0 as D2_KWH FROM 	meter_data.master M,	meter_data.metermaster mm,	meter_data.instantaneous I WHERE mm.accno = M .accno AND I.meter_number = mm.metrno AND M .CIRCLE LIKE '"+circle+"'  AND M .subdiv LIKE  '"+sdoname+"' \n" +
					"AND mm.rdngmonth = '"+rdngMonth+"' ORDER BY M .accno " ;*/
			
		String qry=	"SELECT	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,		mm.metrno,I.read_time,I.v_r,	I.v_y,	I.v_b,	I.i_r,	I.i_y,	I.i_b,	I.kvah,	I.kva\n" +
					"FROM 	meter_data.master M,	meter_data.metermaster mm,	meter_data.amiinstantaneous I \n" +
					"WHERE mm.accno = M .accno AND I.meter_number = mm.metrno AND M .CIRCLE LIKE '"+circle+"'  AND M .subdiv LIKE  '"+sdoname+"'\n" +
					" AND mm.rdngmonth = '"+rdngMonth+"' ORDER BY I.read_time";
			
			
	/*String qry=" SELECT m.CIRCLE,m.subdiv,c.cdf_id,m.accno,m.name,m.mnp,mm.metrno,d2.D2_ID_OLD,d2. CDF_ID_OLD, d2.R_PHASE_VAL,"
			+ " d2.Y_PHASE_VAL, d2.B_PHASE_VAL,d2. R_PHASE_LINE_VAL,d2.Y_PHASE_LINE_VAL,d2.B_PHASE_LINE_VAL,d2.R_PHASE_ACTIVE_VAL, "
			+ " d2.Y_PHASE_ACTIVE_VAL,d2.B_PHASE_ACTIVE_VAL,d2.R_PHASE_PF_VAL,d2.Y_PHASE_PF_VAL,d2.B_PHASE_PF_VAL, "
			+ " d2.AVG_PF_VAL,d2.ACTIVE_POWER_VAL,d2.PHASE_SEQUENCE,d2.MF,d2.D2_ID,d2.CDF_ID, d2.R_PHASE_CURRENT_ANGLE, "
			+ " d2.Y_PHASE_CURRENT_ANGLE, d2.B_PHASE_CURRENT_ANGLE,d2.D2_KWH from mdm_test.d2_data d2,mdm_test.master m, mdm_test.metermaster mm, "
			+ " mdm_test.cdf_data c where c.cdf_id= d2.cdf_id and mm.accno=m.accno and c.accno=m.accno and c.accno=mm.accno "
			+ " and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and m.CIRCLE like '"+circle+"' and "
			+ " m.subdiv like '"+sdoname+"' and c.billmonth='"+rdngMonth+"' "
			+ " order by m.accno ";*/

	        transList=postgresMdas.createNativeQuery(qry).getResultList();
			//System.out.println("transList  qry====="+qry);
			//System.out.println("transList  size====="+transList.size());
			
			
			for (Iterator iterator1 = transList.iterator(); iterator1.hasNext();)
		    {
		    	Map<String, Object> data=new HashMap<>();
		    	Object[] obj = (Object[]) iterator1.next();
				 if (transList.size() > 0) 
				 {
					 //Double kwh=null;
					 String kva="";
					 String kvah="";
					 String ir="";
					 String iy="";
					 String ib="";
					 String vr="";
					 String vy="";
					 String vb="";
					 if(obj[6]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[6]));
						double dkwh= wh/1000;
						//System.out.println("dkwh---"+dkwh);
						vr=new DecimalFormat("#.##").format(dkwh);
						//System.out.println("vr after---"+vr);
					 } 
					 if(obj[7]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[7]));
						double dkwh= wh/1000;
						vy=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[8]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[8]));
						double dkwh= wh/1000;
						vb=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[9]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[9]));
						double dkwh= wh/1000;
						ir=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[10]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[10]));
						double dkwh= wh/1000;
						iy=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[11]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[11]));
						double dkwh= wh/1000;
						ib=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[12]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[12]));
						double dkwh= wh/1000;
						kvah=new DecimalFormat("#.##").format(dkwh);
					 } 
					 if(obj[13]!=null )
					 {
						Double wh=Double.parseDouble(String.valueOf(obj[13]));
						double dkwh= wh/1000;
						kva=new DecimalFormat("#.##").format(dkwh);
					 } 
					 
					 
					 data.put("CIRCLE", obj[0]);
					 data.put("SDONAME", obj[1]);
					 data.put("ACCNO", obj[2]);
					 data.put("NAME", obj[3]);
					 data.put("METERNO", obj[4]);
					 data.put("READ_TIME", obj[5]);
					 data.put("VR", vr);
					 data.put("VY", vy);
					 data.put("VB", vb);
					 data.put("IR", ir);
					 data.put("IY", iy);
					 data.put("IB", ib);
					 data.put("KVAH", kvah);
					 data.put("KVA", kva);
					 list1.add(data);
				 }
		    }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public List<?> getWiringReportData(String circle, String rdngMonth, String sdoname) {
		List<?> wireList=null;
		
		try {
			
			String qry = "SELECT DISTINCT 	M .CIRCLE,	M .subdiv,	M .accno,	M . NAME,	M .mnp,	mm.metrno,	mm.mtrmake,	mm.rtcyn,	0 as phase_sequence,	30 as interval_period,	mm.dlms FROM		meter_data.master M,	meter_data.metermaster mm,\n" +
					"meter_data.instantaneous I WHERE mm.accno = M .accno AND I.meter_number = mm.metrno AND M .CIRCLE like '"+circle+"' and m.subdiv like '"+sdoname+"' AND mm .rdngmonth = '"+rdngMonth+"' \n" +
					"ORDER BY	M .accno";
			
			
			
/*	  String qry="select distinct c.cdf_id,m.CIRCLE,m.subdiv,m.accno,m.name,m.mnp,mm.metrno,mm.mtrmake,mm.rtcyn,d2.phase_sequence,d4d.interval_period,"
		+ " d1.meter_type from mdm_test.d2_data d2,mdm_test.master m, mdm_test.metermaster mm, mdm_test.cdf_data c,mdm_test.d4_data d4d,mdm_test.d1_data d1 where c.cdf_id= d2.cdf_id"
		+ " and mm.accno=m.accno and c.accno=m.accno and d1.cdf_id=c.cdf_id and d4d.cdf_id=c.cdf_id and "
		+ " c.accno=mm.accno and c.meterno=mm.metrno and mm.rdngmonth=c.billmonth and m.CIRCLE like '"+circle+"' and m.subdiv like '"+sdoname+"' "
		+ " and c.billmonth='"+rdngMonth+"' order by m.accno ";
*/
	        wireList=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("wireList  qry====="+qry);
			System.out.println("wireList  size====="+wireList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wireList;
	}


	@Override
	public List<?> getMISXMLReport(String circle, String rdngMonth,
			String sdoname) {
		String sdoname1="";
		if(sdoname.equalsIgnoreCase("all"))
		{
			sdoname1="%";
		}
		else
		{
			sdoname1=sdoname;
		}
		
		List MISXml=null;
		try {
			/*String qry="select CIRCLE,sdocode,SDONAME,tadesc as Category,ACCNO,METRNO,case when upper(remark) like 'METER%CHA%' \n" +
				" then readingremark||'--'||remark else readingremark end as remark,(CASE WHEN XCURRDNGKWH IS NOT NULL  and READINGREMARK ='OK' "
				+ " THEN READINGREMARK END) as READINGREMARK, XCURRDNGKWH as CMRI_KWH,XCURRRDNGKVAH as CMRI_KVAH,\n" +
				" XCURRDNGKVA as CMRI_KVA,XPF AS CMRI_PF,CURRDNGKWH as Manual_KWH,(case when CURRRDNGKVAH not like '99999%' then CURRRDNGKVAH end) as Manual_KVAH,\n" +
				" (case when CURRDNGKVA not like '99999%' then CURRDNGKVA end) as Manual_KVA,(case when PF not like '99999%' then PF end) as Manual_PF,\n" +
				" Name||'--'||address1 as \"Name and Address\",CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA,PF from mm \n" +
				" where cst like 'R' and substr(accno,5,1)<>'9' and circle like '"+circle+"' and sdoname like '"+sdoname1+"' and RDNGMONTH='"+rdngMonth+"'order by accno";*/
			
			
			String qry="select CIRCLE,sdocode,subdiv,tadesc as Category,ACCNO,METRNO,case when upper(remark) like 'METER%CHA%' \n" +
					" then readingremark||'--'||remark else readingremark end as remark,XCURRDNGKWH as CMRI_KWH,XCURRRDNGKVAH as CMRI_KVAH,\n" +
					" XCURRDNGKVA as CMRI_KVA,XPF AS CMRI_PF,CURRDNGKWH as Manual_KWH,(case when CURRRDNGKVAH not like '99999%' then CURRRDNGKVAH end) as Manual_KVAH,\n" +
					" (case when CURRDNGKVA not like '99999%' then CURRDNGKVA end) as Manual_KVA,(case when PF not like '99999%' then PF end) as Manual_PF,\n" +
					" Name||'--'||address1 as \"Name and Address\",CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA,PF from mdm_test.mm \n" +
					" where MCST like 'R' and substr(accno,5,1)<>'9' and circle like '"+circle+"' and subdiv like '"+sdoname1+"' and RDNGMONTH='"+rdngMonth+"'order by accno";
			MISXml=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("MISXml  qry====="+qry);
			System.out.println("MISXml  size====="+MISXml.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MISXml;
	}

	
	@Override
	public List<?> ExcelForLoadSurvey(String meterno,String billMonth) {
		
		
		List list=null;
		String qry="";
		try {
			 qry="select meterno,day_profile_date as ls_date,IP_Interval,kvavalue as kva,kwhvalue as kwh,pfvalue as pf\n" +
					"from mdm_test.d4_load_data where meterno like '"+meterno+"' and billmonth='"+billMonth+"' and IP_INTERVAL!=0 order by ls_date,ip_interval";
			
			/* qry="select meterno,day_profile_date as ls_date,IP_Interval,kvavalue as kva,kwhvalue as kwh,pfvalue as pf\n" +
					"from D4_LOAD_DATA_3MONTHS where meterno = '"+meterno+"' and billmonth="+billMonth+" and IP_INTERVAL!=0 order by ls_date,ip_interval";
		*/
			 list =postgresMdas.createNativeQuery(qry).getResultList();
			
			System.out.println("list Lexcel qry====="+qry);
			System.out.println("list  Lexcel size====="+list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//Added By Vijayalaxmi
	
	/*---------  Getting Image for manual Reading Report------------*/


	@Transactional(propagation=Propagation.SUPPORTS)
	public byte[] findOnlyImage4(ModelMap model,HttpServletRequest request,HttpServletResponse response,String rdngMonth,String accNo) 
	{
		System.out.println("In findOnlyImage4 Impl===="+rdngMonth+" "+accNo);
		
		
		byte image[]=null;
		try
		{
		/*List<MeterMasterExtra> hh=  entityManager.createNamedQuery("MeterMasterExtra.findOnlyImage").setParameter("readingMonth",rdngMonth).setParameter("accno",accNo).getResultList();
		*/
			
			MeterMasterExtra hh= null;
			String qry="select * from METERMASTER_EXTRAS m where m.RDNGMONTH='"+rdngMonth+"' and m.ACCNO like '"+accNo+"' ";
			System.out.println("image qry-->"+qry);
			hh=(MeterMasterExtra) postgresMdas.createNativeQuery(qry,MeterMasterExtra.class).getSingleResult();
			
		response.setContentType("image/jpeg/png");
    	byte bt[] = null;
    	
    	OutputStream ot = null;
    	ot = response.getOutputStream();	
    	
    	
    	if(hh != null)
    	{
    		
    	 bt=hh.getImageOne();
    	
    		
    		/*bt=hh.get(0).getImageOne();*/
    	 
    	 System.out.println(" BT Image from table==>"+ bt);
    		
    	    if(bt==null || bt.toString().isEmpty() || bt.toString().equalsIgnoreCase("null") )
    		{
    	    
    	    	String pathname = "E:\\MDM10-04_Schedulr\\bsmartmdm\\WebContent\\resources\\assets\\img\\notFoundImage.png";
    	    	
/*    			String pathname = "E:\\Vcloudengine-JVVNL\\bsmartJVVNL\\WebContent\\resources\\bsmart.lib.js\\gmapimages\\noImage.jpg";
*/    		
    	    	BufferedImage originalImage = ImageIO.read(new File(pathname));
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ImageIO.write( originalImage, "png", baos );
    			baos.flush();
    			byte[] imageInByte = baos.toByteArray();
    			ot.write(imageInByte);
    			baos.close();
    		}
    	    else if(bt.length>1)
    		{
    			ot.write(bt);
            	ot.close();
    		}
    		
    	}
    	else
    	{
    		
    	}
       Base64 b=new Base64();
       //BCITSLogger.logger.info("================>BT"+bt.length);
       image=b.encodeBase64(bt);
		/*return b.encodeBase64(bt);*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return image;    	
    }

	
// Vijayalaxmi
	@Override
	public byte[] findOnlyImageDefective(ModelMap model,HttpServletRequest request, HttpServletResponse response,String rdngMonth, String accNo) {
		System.out.println("In findOnlyImageDefectiveService Impl===="+rdngMonth+" "+accNo);
		
		byte image[]=null;
		try
		{
		/*List<MeterMasterExtra> hh=  postgresMdas.createNamedQuery("MeterMasterExtra.findOnlyImage").setParameter("readingMonth",rdngMonth).setParameter("accno",accNo).getResultList();
		*/
			
			MeterMasterExtra hh= null;
			String qry="select * from METERMASTER_EXTRAS m where m.RDNGMONTH='"+rdngMonth+"' and m.ACCNO like '"+accNo+"' ";
			hh=(MeterMasterExtra) postgresMdas.createNativeQuery(qry,MeterMasterExtra.class).getSingleResult();
			
		response.setContentType("image/jpeg/png");
        byte bt[] = null;
   	
   	    OutputStream ot = null;
   	    ot = response.getOutputStream();	
   
      	if(hh != null)
     	{
   	    bt=hh.getImageOne();
   	
   	    System.out.println(" Defective Image from METERMASTER_EXTRAS table==>"+ bt);
   		
   	    if(bt==null || bt.toString().isEmpty() || bt.toString().equalsIgnoreCase("null") )
   		{
   	    	String pathname = "E:\\MDM10-04_Schedulr\\bsmartmdm\\WebContent\\resources\\assets\\img\\notFoundImage.png";
   	    	  		
   	    	BufferedImage originalImage = ImageIO.read(new File(pathname));
   			ByteArrayOutputStream baos = new ByteArrayOutputStream();
   			ImageIO.write( originalImage, "png", baos );
   			baos.flush();
   			byte[] imageInByte = baos.toByteArray();
   			ot.write(imageInByte);
   			baos.close();
   		}
   	    else if(bt.length>1)
   		{
   			ot.write(bt);
           	ot.close();
   		}
   		
   	}
   	else
   	{
   		
   	}
      Base64 b=new Base64();
      //BCITSLogger.logger.info("================>BT"+bt.length);
      image=b.encodeBase64(bt);
		/*return b.encodeBase64(bt);*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return image;  
	}
	
	
	@Override
	public List<?> getconsumerConsumptionData(String circle, String rdngMonth, String category, String part) {
		List<?> consumerList=null;
		try {
			
	String qry=" SELECT 	aa.circle,	aa.part,	aa.division,	aa.subdiv,	aa.accno,	aa.metrno,	aa.CONSUMERNAME,	aa.CATEGORY,	COALESCE (aa.supplyvoltage, '0')"
			+ " AS svoltage,	aa.contractdemand AS  cd,	aa.sanload AS sload,	aa.mf,	(CASE WHEN aa.category LIKE 'LT' THEN (aa.ckwh - aa.pkwh) * aa.mf	"
			+ "	WHEN aa.category LIKE 'HT' THEN			(aa.ckvah -aa.pkvah) * aa.mf END	) AS consumption,	ROUND(	(CASE	WHEN aa.category LIKE 'LT' 	AND aa.xkva > 0 THEN		((aa.XKWH - aa.pkwh) *aa.mf) / aa.xkva	"
			+ "	WHEN aa.category LIKE 'HT'	AND aa.xkva > 0 THEN	((aa.XKWH - aa.pkwh) * aa.mf) / aa.xkva	 END),	2	) AS util FROM	(SELECT	ms.circle,	ms.part,ms.division,ms.subdiv,mm.accno,	mm.CONSUMERNAME,mm.metrno,ms.industrytype,"
			+ "	ms.supplyvoltage,ms.contractdemand,		ms.sanload,	mm.mf,	mm.category,COALESCE (mm.currdngkwh, 0) AS ckwh,COALESCE (mm.prkwh, 0) pkwh,COALESCE (mm.currrdngkvah, 0) AS ckvah,"
			+ "	COALESCE (mm.prkvah, 0) AS pkvah, COALESCE (mm.currdngkva, 0) AS ckva,	mm.XCURRDNGKWH AS xkwh,			(mm.XCURRDNGKVA * mm.mf) AS xkva FROM	meter_data.master ms,	meter_data.metermaster mm		WHERE	ms.accno = mm.accno	AND mm.prkwh > 0	"
			+ "	AND ms.consumerstatus LIKE 'R'		AND ms.circle LIKE  '"+circle+"' AND mm.category LIKE  '"+category+"' "
			+ "	AND CAST(mm.rdngmonth as TEXT) = to_char(to_date( '"+rdngMonth+"', 'yyyyMM')+ interval '1 month','yyyyMM') 	) aa";
			
	/*AND  cast(ms.part as text)   LIKE  '"+part+"'*/
	System.out.println("transList  qry====="+qry);
		consumerList=postgresMdas.createNativeQuery(qry).getResultList();
			
			System.out.println("transList  size====="+consumerList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumerList;
	}






	@Override
	public List<?> getUsageIndexingReport(HttpServletRequest request)
	{
		List<?> meterData=new ArrayList<>();
		try 
		{
			String rdngmonth=request.getParameter("reportFromDate");
			String subdivision=request.getParameter("sdoId");
			if(subdivision.equalsIgnoreCase("ALL"))
			{
				subdivision="%";
			}
			
			String qry="SELECT meterDetails.circle,meterDetails.division,meterDetails.subdiv,meterDetails.name,meterDetails.address1,meterDetails.accno,Evt.meter_number,cast(Evt.mints as VARCHAR) from \n" +
					" (SELECT consumer.accno,consumer.circle,consumer.division,consumer.subdiv,consumer.name,consumer.address1,metrno  from \n" +
					" (SELECT accno,metrno from meter_data.metermaster where rdngmonth='"+rdngmonth+"' and subdiv like '"+subdivision+"')MM\n" +
					" LEFT JOIN\n" +
					"(SELECT accno,circle,division,subdiv,name,address1 from meter_data.master where subdiv like '"+subdivision+"')consumer \n" +
					" on MM.accno=consumer.accno)meterDetails\n" +
					" left JOIN\n" +
					"(SELECT C.meter_number,(((sum(C.hr))*60)+( sum(C.mints)))as mints,sum(C.sec) from \n" +
					"(SELECT B.meter_number,B.event_time,B.event_code,B.OFFTIME,\n" +
					"DATE_PART('hour',B.event_time -B.OFFTIME)as hr,\n" +
					"DATE_PART('minute',B.event_time -B.OFFTIME)mints,\n" +
					"DATE_PART('second',B.event_time -B.OFFTIME) sec\n" +
					"FROM \n" +
					"(select A.meter_number,A.event_time,A.event_code,(case when A.event_code='102' then  (LAG(A.event_time,1) OVER ( PARTITION BY meter_number ORDER BY event_time)) end)AS OFFTIME\n" +
					"from \n" +
					"(SELECT meter_number,event_time,event_code FROM meter_data.events \n" +
					"where event_code in ('101','102') and to_char(event_time,'yyyyMM')='"+rdngmonth+"' ORDER BY meter_number,event_time)A)B)C\n" +
					"GROUP BY c.meter_number)Evt\n" +
					"on meterDetails.metrno=Evt.meter_number WHERE Evt.mints IS NOT NULL ";
			
			        System.out.println("+++++++++++++++"+qry);
			        
			meterData= postgresMdas.createNativeQuery(qry).getResultList();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return meterData;
	}

	


	@Override
	public List findALLDateWiseAvgLOad(String selectedDateName, String circle,
			String division, String subdivision) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String rdngmonth=sdf.format(d1);
		List list=null;
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		if(subdivision.equalsIgnoreCase("All"))
		{
			subdivision="%";
		}
		try {
			
			String qry="SELECT mm.metrno,m.circle,m.subdiv, mm.rdngmonth,m.accno,m.NAME,m.contractdemand,to_char(l.read_time,'dd-MM-yyyy'),\n" +
						"SUM(kwh) AS total_consumption,round(SUM(kwh)/24,2) AS avg \n" +
						"FROM meter_data.load_survey l,meter_data.master m,meter_data.metermaster mm\n" +
						"WHERE to_char(read_time,'yyyy-MM-dd')='"+selectedDateName+"' AND mm.accno = M .accno AND  mm.rdngmonth='"+rdngmonth+"' AND\n" +
						"mm.metrno =l.meter_number AND M .circle LIKE '"+circle+"'  AND M.subdiv LIKE '"+subdivision+"' \n" +
						"GROUP BY mm.metrno,m.circle,m.subdiv, mm.rdngmonth,m.accno,m.NAME,m.contractdemand,to_char(l.read_time,'dd-MM-yyyy');";
				
			System.out.println("dateWise Load query=="+qry);
		list=postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println("size--"+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
	}


	@Override
	public List findAllMissingMtrsInBillData(String selectedDateName,
			String circle, String division, String subdivision) {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String rdngmonth=sdf.format(d1);
		List list=null;
		if(circle.equalsIgnoreCase("All"))
		{
			circle="%";
		}
		if(division.equalsIgnoreCase("All"))
		{
			division="%";
		}
		if(subdivision.equalsIgnoreCase("All"))
		{
			subdivision="%";
		}
		try {
			
			String qry="SELECT mtrno,accno,customer_name,circle,subdivision FROM meter_data.master_main WHERE circle LIKE '"+circle+"' AND \n" +
					"subdivision LIKE '"+subdivision+"' and mtrno NOT IN\n" +
					"(SELECT DISTINCT(meter_number) FROM   meter_data.bill_history WHERE  to_char(billing_date,'yyyy-MM-dd')='"+selectedDateName+"')";
				
			System.out.println("dateWise Load query=="+qry);
		list=postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println("size--"+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
	
	}


	@Override
	public List<?> getTamperSummaryData(String circle, String division,String rdngmnth,String subdiv, String TownCode) {
		List<?> tamperList=null;
		String qry1="";

		try {
			qry1="select to_char(event_time,'yyyyMM') as rdngmonth,subdivision,kno,accno,customer_name,meter_number,event,count(event)\n" +
					"from (select evf.*,mdm.kno,mdm.accno,mdm.customer_name,mdm.subdivision\n" +
					" from (select em.meter_number,em.event_code,(select ema.event from meter_data.event_master ema \n" +
					"where ema.event_code=to_number(em.event_code,'9999')),event_time, ( case when (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=1 or (to_number(em.event_code, '999')-lag(to_number(em.event_code, '999'), 1)  OVER (ORDER BY  em.meter_number,em.event_time,em.id desc))=0  then to_char(em.event_time-lag(em.event_time, 1) OVER (ORDER BY  em.meter_number,em.event_time,em.id desc),'DDD HH24:MI:SS')  END) as duration from meter_data.events em\n" +
					"where to_char(em.event_time,'yyyyMM')='"+rdngmnth+"' and to_number(em.event_code,'999') in (select ems.event_code from meter_data.event_master ems where ems.category='Tamper') and em.meter_number in (select mdmi.mtrno from meter_data.master_main mdmi where  mdmi.circle like '"+circle+"' and mdmi.division like '"+division+"' and mdmi.subdivision like '"+subdiv+"' and mdmi.town_code like  '"+TownCode+"')\n" +
					"GROUP BY em.meter_number,em.event_time,em.id,em.event_code\n" +
					"ORDER BY em.meter_number,em.event_time,em.id desc ) evf left join meter_data.master_main mdm on mdm.mtrno=evf.meter_number  )X group by kno,event,meter_number,accno,customer_name,subdivision,to_char(event_time,'yyyyMM')";
			//System.out.println(qry1);
		    tamperList=postgresMdas.createNativeQuery(qry1).getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tamperList;
		
		
	}
	
}
