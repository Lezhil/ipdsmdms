package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.D4CdfData;
import com.bcits.service.D4LoadDataService;
import com.bcits.utility.MDMLogger;

@Repository
public class D4LoadDataServiceImpl extends GenericServiceImpl<D4CdfData> implements D4LoadDataService
{

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D4CdfData> getloadSurveyDetailsBasedOnMeterNo(String meterNo,String loadSurveydate,ModelMap model)
	{
		List<D4CdfData> list= postgresMdas.createNamedQuery("D4CdfData.getDetailsBasedOnId").setParameter("meterNo", meterNo).setParameter("dayOfProfile", loadSurveydate).getResultList();	
		model.put("dailyLoadSurveyData", list);
		model.put("portletTitle", "Daily Load Survey Details of "+loadSurveydate);
		model.put("meterNo", meterNo);
		//model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected date");
		}
		return list;
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Object getIntervalD4LoadData(String billmonth, String meterno,ModelMap model) 
	{
		List<Object[]> dateList=null;
		List list=null;
		String[] l1Arr=new String[48];
		String l1amp="";
		try
		{
		String sql="SELECT  TO_CHAR(cdf.dayProfileDate,'dd-MM-yyyy'),cdf.meterNo,cdf.billmonth FROM D4CdfData cdf  WHERE cdf.meterNo='"+meterno+"' AND cdf.billmonth="+billmonth+"  GROUP BY  cdf.dayProfileDate,cdf.meterNo,cdf.billmonth ORDER BY cdf.dayProfileDate";
		dateList=postgresMdas.createQuery(sql).getResultList();
		model.put("dateList", dateList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dateList;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Object getIntervalD4LoadData1(String billmonth, String meterno,String billdate, ModelMap model)
	{
		String l1sql="";
		String l2sql="";
		String l3sql="";
		List l1ValList=null;
		List l2ValList=null;
		List l3ValList=null;
		String l1amp="";
		String l2amp="";
		String l3amp="";
		Map<String, Object> data=null;
            ArrayList<Double> minMaxVal=new ArrayList<Double>();
		try 
		{
			 l1sql="SELECT L1VALUE FROM D4_LOAD_DATA WHERE METERNO='"+meterno+"' AND BILLMONTH="+billmonth+" AND to_char(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+billdate+"' ORDER BY IP_INTERVAL";
			 l1ValList=postgresMdas.createNativeQuery(l1sql).getResultList();
			 l1sql="SELECT L2VALUE FROM D4_LOAD_DATA WHERE METERNO='"+meterno+"' AND BILLMONTH="+billmonth+" AND to_char(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+billdate+"' ORDER BY IP_INTERVAL";
			 l2ValList=postgresMdas.createNativeQuery(l1sql).getResultList();
			 l1sql="SELECT L3VALUE FROM D4_LOAD_DATA WHERE METERNO='"+meterno+"' AND BILLMONTH="+billmonth+" AND to_char(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+billdate+"' ORDER BY IP_INTERVAL";
			 l3ValList=postgresMdas.createNativeQuery(l1sql).getResultList();
			 MDMLogger.logger.info("====================================>l1sql"+l1sql);
			 /*Double maxL1=Double.parseDouble(Collections.max(l1ValList).toString());
			 Double minL1=Double.parseDouble(Collections.min(l1ValList).toString());
			 Double maxL2=Double.parseDouble(Collections.max(l2ValList).toString());
			 Double minL2=Double.parseDouble(Collections.min(l2ValList).toString());
			 Double maxL3=Double.parseDouble(Collections.max(l3ValList).toString());
			 Double minL3=Double.parseDouble(Collections.min(l3ValList).toString());
			 minMaxVal.add(maxL1);
			 minMaxVal.add(minL1);
			 minMaxVal.add(maxL2);
			 minMaxVal.add(minL2);
			 minMaxVal.add(maxL3);
			 minMaxVal.add(minL3);
			 Double maxVal=Collections.max(minMaxVal);
			 Double minVal=Collections.min(minMaxVal);
			 MDMLogger.logger.info("====================================>maxVal"+maxVal);
			 MDMLogger.logger.info("====================================>minVal"+minVal);*/
			 data=new HashMap<>();
			data.put("l1amp", l1ValList);
			data.put("l2amp", l2ValList);
			data.put("l3amp", l3ValList);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	@Override
	public List<D4CdfData> getLoadSurveyData(String mtrNo, String billMonth) 
	{
		// TODO Auto-generated method stub
		List<D4CdfData> list=null;
		String qry="";
		try {
			list=postgresMdas.createNamedQuery("D4CdfData.getDetails").setParameter("meterNo", mtrNo).setParameter("billMonth", billMonth).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Object[]> getVeeEstimation(String mtrno, String billMonth) {
		List<Object[]> list=null;
		String qry="select sum(AA.kwh) as fkwh,sum(AA.kva) as fkva,sum(AA.kwh_a) as fkwh_a,sum(AA.kwh_b) as fkwh_b,sum(AA.kwh_c) as fkwh_c,"
                  +" sum(AA.kwh_d) as fkwh_d,sum(AA.kwh_e) as fkwh_e,sum(AA.kwh_f) as fkwh_f,sum(AA.kwh_g) as fkwh_g,avg(pf) as fpf "
  +" from ( select DISTINCT day_profile_date,sum(to_number( (CASE WHEN kwhvalue = '' THEN '0' ELSE kwhvalue END), '999D99999')) as kwh, "
 +" sum(to_number((CASE WHEN kvavalue = '' THEN '0' ELSE kvavalue END), '999D99999')) as kva , "
 +" sum(to_number( (CASE WHEN ip_interval =1  THEN kwhvalue WHEN ip_interval =2  THEN kwhvalue WHEN ip_interval =3  THEN kwhvalue WHEN ip_interval =4  THEN kwhvalue "
 +" WHEN ip_interval =5  THEN kwhvalue  WHEN ip_interval =6  THEN kwhvalue  WHEN ip_interval =7  THEN kwhvalue WHEN ip_interval =8  THEN kwhvalue "
 +" WHEN ip_interval =9  THEN kwhvalue WHEN ip_interval =10  THEN kwhvalue  WHEN ip_interval =11  THEN kwhvalue  WHEN ip_interval =17  THEN kwhvalue "
 +" WHEN ip_interval =18  THEN kwhvalue WHEN ip_interval =19  THEN kwhvalue WHEN ip_interval =20  THEN kwhvalue  WHEN ip_interval =21  THEN kwhvalue "
 +"  WHEN ip_interval =22  THEN kwhvalue WHEN ip_interval =23  THEN kwhvalue WHEN ip_interval =24  THEN kwhvalue WHEN ip_interval =25  THEN kwhvalue "
 +"  WHEN ip_interval =26  THEN kwhvalue  WHEN ip_interval =27  THEN kwhvalue WHEN ip_interval =28  THEN kwhvalue WHEN ip_interval =29  THEN kwhvalue "
 +" WHEN ip_interval =30  THEN kwhvalue  WHEN ip_interval =31  THEN kwhvalue  WHEN ip_interval =32  THEN kwhvalue WHEN ip_interval =33  THEN kwhvalue "
 +" WHEN ip_interval =34  THEN kwhvalue WHEN ip_interval =35  THEN kwhvalue  WHEN ip_interval =45  THEN kwhvalue WHEN ip_interval =46  THEN kwhvalue "
 +" WHEN ip_interval =47  THEN kwhvalue WHEN ip_interval =48  THEN kwhvalue  END), '999D99999')) as kwh_a  , "
 +" sum(to_number( (CASE WHEN ip_interval =12  THEN kwhvalue WHEN ip_interval =13  THEN kwhvalue WHEN ip_interval =14  THEN kwhvalue WHEN ip_interval =15  THEN kwhvalue WHEN ip_interval =16  THEN kwhvalue  END ), '999D99999')) as kwh_b  , "
 +" sum(to_number( (CASE  WHEN ip_interval =36  THEN kwhvalue  END ), '999D99999')) as kwh_c  , "
 +" sum(to_number( (CASE WHEN ip_interval =37  THEN kwhvalue END), '999D99999')) as kwh_d   , "
 +" sum(to_number( (CASE WHEN ip_interval =38  THEN kwhvalue END), '999D99999')) as kwh_e , "
 +" sum(to_number( (CASE WHEN ip_interval =39  THEN kwhvalue WHEN ip_interval =40  THEN kwhvalue WHEN ip_interval =41  THEN kwhvalue WHEN ip_interval =42  THEN kwhvalue END), '999D99999')) as kwh_f, "
 +" sum(to_number( (CASE WHEN ip_interval =43  THEN kwhvalue WHEN ip_interval =44  THEN kwhvalue END), '999D99999')) as kwh_g, "
 +" sum(to_number((CASE WHEN pfvalue = '' THEN '0' ELSE pfvalue END), '999D99999')) as pf  "
 +" from mdm_test.d4_load_data where billmonth='"+billMonth+"' and meterno='"+mtrno+"'  GROUP BY day_profile_date) AA";
		try {
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<D4CdfData> getDuplicateData(String mtrNo, String billMonth) 
	{
		// TODO Auto-generated method stub
		List<D4CdfData> list=null;
		String qry="";
		try {
			list=postgresMdas.createNamedQuery("D4CdfData.getDuplicateDetails").setParameter("meterNo", mtrNo).setParameter("dayOfProfile", billMonth).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<?> getconsumptionanalysis(String division, String circle,String subdivision, String month) 
	{
		
		List<?> list=new ArrayList<>();
		/*String qry="SELECT consumption,read_time FROM (SELECT DISTINCT B.metrno,B.circle,B.division,B.subdiv FROM meter_data.metermaster B WHERE B.rdngmonth='"+month+"'\n" +
				"GROUP BY B.metrno,B.circle,B.division,B.subdiv)E\n" +
				"INNER JOIN (SELECT * FROM(SELECT SUM(kwh) AS consumption,\n" +
				"A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.metrno\n" +
				" WHERE E.circle='"+circle+"' AND E.division='"+division+"' AND E.subdiv='"+subdivision+"' AND to_char(D.read_time, 'YYYYMM')='"+month+"'  ORDER BY D.read_time ASC";
		*/
		
		/*String qry=" SELECT consumption,read_time FROM (SELECT DISTINCT B.mtrno,B.circle,B.division,B.subdivision FROM meter_data.master_main B \n" +
				  " GROUP BY B.mtrno,B.circle,B.division,B.subdivision)E \n" +
				  " INNER JOIN (SELECT * FROM(SELECT SUM(kwh) AS consumption, \n" +
				  " A.read_time,A.meter_number FROM meter_data.load_survey A   GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno \n" +
				  " WHERE E.circle like '"+circle+"' AND E.division like'"+division+"' AND E.subdivision LIKE '"+subdivision+"' AND to_char(D.read_time, 'YYYYMM')='"+month+"'  ORDER BY D.read_time ASC";
		*/	
		
		String qry="select round(sum(a.consumption),2) as consumption, a.rt  from (SELECT consumption,to_char(read_time,'YYYY-MM-DD') as rt FROM   \n" +
				"							 (SELECT DISTINCT B.mtrno,B.zone,B.circle,B.division,B.subdivision FROM meter_data.master_main B   \n" +
				"							  GROUP BY B.mtrno,B.circle,B.division,B.subdivision,B.zone)E   \n" +
				"							  INNER JOIN (SELECT * FROM(SELECT SUM(kwh/1000) AS consumption,A.read_time,A .meter_number FROM meter_data.load_survey A GROUP BY A .meter_number,A.read_time ORDER BY A.read_time DESC  ) C) D ON D.meter_number=E.mtrno   \n" +
				"							   WHERE   E.circle like '"+circle+"' AND E.division like '"+division+"' AND E.subdivision LIKE '"+subdivision+"' AND to_char(D.read_time, 'YYYYMM')='"+month+"'  ORDER BY D.read_time ASC) a GROUP BY a.rt";
		//System.out.println("consumption curve analysis qryyyyy----->"+qry);
		
		try {
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
