package com.bcits.serviceImpl;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.Master;
import com.bcits.entity.MeterMaster;
import com.bcits.service.MasterService;


@Repository
public class MasterServiceImpl extends GenericServiceImpl<Master> implements MasterService
{
 
	@Transactional(propagation=Propagation.SUPPORTS)
	public long FindTotalConsumerCount()
	{
		return (long) postgresMdas.createNamedQuery("Master.FindTotalConsumerCount").getSingleResult();
	}
	@Transactional(propagation=Propagation.SUPPORTS)
	public List FindMakewiseConsumerCount(String billMonth, ModelMap model)
	{
		String query="";
		String query1="";
		long totalCount=0;
		List list=null;
		List list1=null;
	     try 
	     {
	    	 
	    	 query="SELECT COUNT(*) AS CN FROM meter_data.MASTER WHERE CONSUMERSTATUS IN ('R')";
	    	// query1="SELECT count(DISTINCT meter_number) FROM meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+billMonth+"'";
	    	  query1="SELECT count(distinct metrno) FROM meter_data.metermaster WHERE rdngmonth='"+billMonth+"' and metrno in(SELECT DISTINCT meter_number \n" +
	    			  "FROM meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+billMonth+"')";
	    	int inactiveconsumers= Integer.parseInt(postgresMdas.createNativeQuery(query).getSingleResult().toString());
	    	int total=Integer.parseInt(postgresMdas.createNativeQuery(query1).getSingleResult().toString());
	    	//model.put("inactiveconsumers", inactiveconsumers);
	    	model.addAttribute("totalActive", total);
	    	//AND ACCNO IN (SELECT ACCNO FROM mdm_test.METERMASTER WHERE  RDNGMONTH="+billMonth+" AND DISCOM='UHBVN')
	    	
	    	 /*query="SELECT TADESC,COUNT(*) AS CN FROM meter_data.MASTER WHERE CONSUMERSTATUS LIKE 'R' GROUP BY TADESC";
	    	 
	    	 list=postgresMdas.createNativeQuery(query).getResultList();
	    	 //  AND ACCNO IN (SELECT ACCNO FROM mdm_test.METERMASTER WHERE  RDNGMONTH="+billMonth+" AND DISCOM='UHBVN')
	    	 //System.out.println(query);
	    	// System.out.println("list size-->"+list.size());
	    	 for (int i = 0; i < list.size(); i++) 
	    	 {
	    		Object[] str=(Object[]) list.get(i);
	    		for (int j = 0; j < str.length; j++) 
	    		{
	    			if(i==0)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("HT", str[j]);
	    				}
	    			}
	    			if(i==1)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("ABTHT", str[j]);
	    				}
	    				
	    			}
	    			if(i==2)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("NDS", str[j]);
	    				}
	    				
	    			}
	    			if(i==3)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("SIP", str[j]);
	    				}
	    				
	    			}
	    			if(i==4)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("MIP", str[j]);
	    				}
	    				
	    			}
	    			if(i==5)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("ABTMIP", str[j]);
	    				}
	    				
	    			}
				}
	    		
			}*/
	    	
	    	String qryMtrMke="SELECT count(*) from meter_data.metermaster where rdngmonth='"+billMonth+"'";
	    	totalCount=Integer.parseInt(postgresMdas.createNativeQuery(qryMtrMke).getSingleResult().toString());
	    	
	    	 query="SELECT ROUND(SUM(CASE WHEN AA.MTRMAKE='LNT' OR AA.MTRMAKE='LNTOLD' THEN CN  END)/"+totalCount+"*100,2) AS LNTCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='LNG' THEN AA.CN END)/"+totalCount+"*100,2)AS LNGCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='GENUS POWER INFRASTRUCTURES LTD' THEN AA.CN END)/"+totalCount+"*100,2)AS GENUSCCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='GENUSD' THEN AA.CN END)/"+totalCount+"*100,2)AS GENUSDCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='HPLM' THEN AA.CN END)/"+totalCount+"*100,2)AS HPLMCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='HPLD' THEN AA.CN END)/"+totalCount+"*100,2)AS HPLDCOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE='SECURE' THEN AA.CN END)/"+totalCount+"*100,2)AS SECURECOUNT,\n" +
	    			 "ROUND(SUM(CASE WHEN AA.MTRMAKE NOT IN('LNT','LNG','GENUSD','GENUS POWER INFRASTRUCTURES LTD','HPLM','HPLD','LNTOLD','SECURE') THEN AA.CN END)/"+totalCount+"*100,2)AS OTHERS ,\n" +
	    			 "SUM(CASE WHEN AA.MTRMAKE='LNT' OR AA.MTRMAKE='LNTOLD' THEN CN  END)as LNT,SUM(CASE WHEN AA.MTRMAKE='LNG' THEN AA.CN END) as LNG,SUM(CASE WHEN AA.MTRMAKE='GENUS POWER INFRASTRUCTURES LTD' THEN AA.CN END) as GENUS,\n" +
	    			 "SUM(CASE WHEN AA.MTRMAKE='GENUSD' THEN AA.CN END) as GENUSD,SUM(CASE WHEN AA.MTRMAKE='HPLM' THEN AA.CN END) HPLM,SUM(CASE WHEN AA.MTRMAKE='HPLD' THEN AA.CN END) as HPLD,\n" +
	    			 "SUM(CASE WHEN AA.MTRMAKE='SECURE' THEN AA.CN END) as SECUR,SUM(CASE WHEN AA.MTRMAKE NOT IN('LNT','LNG','GENUSD','GENUS POWER INFRASTRUCTURES LTD','HPLM','HPLD','LNTOLD','SECURE') THEN AA.CN END) as othe  \n" +
	    			 "FROM (SELECT  UPPER(MM.MTRMAKE)AS MTRMAKE ,COUNT(*) AS CN FROM  meter_data.METERMASTER MM,meter_data.MASTER MS  WHERE MS.ACCNO=MM.ACCNO AND MM.RDNGMONTH="+billMonth+"  \n" +
	    			 "GROUP BY MM.MTRMAKE ORDER BY MM.MTRMAKE)AA";
	    	 System.out.println("find make wise--"+query);
	    	 list1=postgresMdas.createNativeQuery(query).getResultList();
	    	 for (int i = 0; i < list1.size(); i++) 
	    	 {
	    		Object[] str=(Object[]) list1.get(i);
	    		for (int j = 0; j < str.length; j++) 
	    		{
	    			if(j==0)
	    			{
	    				model.put("LNT", str[j]);
	    				//model.put("LNTCONSUMERCOUNT",(((BigDecimal)str[j]).longValue()*totalCount/100));
	    			}
	    			if(j==1)
	    			{
	    				model.put("LNG", str[j]);
	    			}
	    			if(j==2)
	    			{
	    				model.put("GENUSC", str[j]);
	    			}
	    			if(j==3)
	    			{
	    				model.put("GENUSD", str[j]);
	    			}
	    			if(j==4)
	    			{
	    				model.put("HPLM", str[j]);
	    			}
	    			if(j==5)
	    			{
	    				model.put("HPLD", str[j]);
	    			}
	    			if(j==6)
	    			{
	    				model.put("SECURE", str[j]);
	    			}
	    			if(j==7)
	    			{
	    				model.put("OTHERS", str[j]);
	    			}
	    			if(j==8)
	    			{
	    				model.put("LNTCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==9)
	    			{
	    				model.put("LNGCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==10)
	    			{
	    				model.put("GENUSCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==11)
	    			{
	    				model.put("GENUSDCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==12)
	    			{
	    				model.put("HPLMCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==13)
	    			{
	    				model.put("HPLDCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==14)
	    			{
	    				model.put("SECURECONSUMERCOUNT", str[j]);
	    			}
	    			if(j==15)
	    			{
	    				model.put("OTHERSCONSUMERCOUNT", str[j]);
	    			}
	    		}
	    	 }
	     } 
	     catch (Exception e) 
	     {
			e.printStackTrace();
		}
		return list;
	
	}
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List FindMakewiseConsumerCount1(String billMonth, ModelMap model)
	{
		String query="";
		String query1="";
		long totalCount=0;
		List list=null;
		List list1=null;
	     try 
	     {
	    	 
	    	 query="SELECT COUNT(*) AS CN FROM meter_data.MASTER WHERE CONSUMERSTATUS NOT IN ('R')";
	    	int inactiveconsumers= Integer.parseInt(postgresMdas.createNativeQuery(query).getSingleResult().toString());
	    	model.put("inactiveconsumers", inactiveconsumers);
	    	
	    	 /*query="SELECT TADESC,COUNT(*) AS CN FROM meter_data.MASTER WHERE CONSUMERSTATUS LIKE 'R'   AND ACCNO IN (SELECT ACCNO FROM mdm_test.METERMASTER WHERE  RDNGMONTH="+billMonth+" AND DISCOM='UHBVN') GROUP BY TADESC";*/
	    	
	    	list=postgresMdas.createNativeQuery(query).getResultList();
	    	 
	    	// System.out.println(query);
	    	// System.out.println("list size-->"+list.size());
	    	 for (int i = 0; i < list.size(); i++) 
	    	 {
	    		Object[] str=(Object[]) list.get(i);
	    		for (int j = 0; j < str.length; j++) 
	    		{
	    			if(i==0)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("HT", str[j]);
	    				}
	    			}
	    			if(i==1)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("ABTHT", str[j]);
	    				}
	    				
	    			}
	    			if(i==2)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("NDS", str[j]);
	    				}
	    				
	    			}
	    			if(i==3)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("SIP", str[j]);
	    				}
	    				
	    			}
	    			if(i==4)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("MIP", str[j]);
	    				}
	    				
	    			}
	    			if(i==5)
	    			{
	    				if(j==1)
	    				{
	    					totalCount=totalCount+Long.parseLong(str[j].toString());
	    					model.put("ABTMIP", str[j]);
	    				}
	    				
	    			}
				}
	    		
			}
	    	 query="SELECT ROUND(SUM(CASE WHEN AA.MTRMAKE='LNT' OR AA.MTRMAKE='LNTOLD' THEN CN  END)/"+totalCount+"*100,2) AS LNTCOUNT,ROUND(SUM(CASE WHEN AA.MTRMAKE='LNG' THEN AA.CN END)/"+totalCount+"*100,2)AS LNGCOUNT,ROUND(SUM(CASE WHEN AA.MTRMAKE='GENUSC' THEN AA.CN END)/"+totalCount+"*100,2)AS GENUSCCOUNT,ROUND(SUM(CASE WHEN AA.MTRMAKE='GENUSD' THEN AA.CN END)/"+totalCount+"*100,2)AS GENUSDCOUNT,"
	    	 		+ "ROUND(SUM(CASE WHEN AA.MTRMAKE='HPLM' THEN AA.CN END)/"+totalCount+"*100,2)AS HPLMCOUNT,"
	    	 				+ "ROUND(SUM(CASE WHEN AA.MTRMAKE='HPLD' THEN AA.CN END)/"+totalCount+"*100,2)AS HPLDCOUNT,ROUND(SUM(CASE WHEN AA.MTRMAKE='SECURE' THEN AA.CN END)/"+totalCount+"*100,2)AS SECURECOUNT,"
	    	 						+ "ROUND(SUM(CASE WHEN AA.MTRMAKE NOT IN('LNT','LNG','GENUSD','GENUSC','HPLM','HPLD','LNTOLD','SECURE') THEN AA.CN END)/"+totalCount+"*100,2)AS OTHERS "
	    	 								+ ",SUM(CASE WHEN AA.MTRMAKE='LNT' OR AA.MTRMAKE='LNTOLD' THEN CN  END)as LNT,SUM(CASE WHEN AA.MTRMAKE='LNG' THEN AA.CN END) as LNG,SUM(CASE WHEN AA.MTRMAKE='GENUSC' THEN AA.CN END) as GENUS,SUM(CASE WHEN AA.MTRMAKE='GENUSD' THEN AA.CN END) as GENUSD,SUM(CASE WHEN AA.MTRMAKE='HPLM' THEN AA.CN END) HPLM,SUM(CASE WHEN AA.MTRMAKE='HPLD' THEN AA.CN END) as HPLD,SUM(CASE WHEN AA.MTRMAKE='SECURE' THEN AA.CN END) as SECUR,SUM(CASE WHEN AA.MTRMAKE NOT IN('LNT','LNG','GENUSD','GENUSC','HPLM','HPLD','LNTOLD','SECURE') THEN AA.CN END) as othe "
	    	 								+ " FROM "
	    	 								+ "(SELECT  UPPER(MM.MTRMAKE)AS MTRMAKE ,COUNT(*) AS CN FROM  mdm_test.METERMASTER MM,mdm_test.MASTER MS  WHERE MS.ACCNO=MM.ACCNO AND MS.CONSUMERSTATUS LIKE 'R' AND MM.RDNGMONTH="+billMonth+"  GROUP BY MM.MTRMAKE ORDER BY MM.MTRMAKE)AA";
	    	 System.out.println("Genus comman--"+query);
	    	 list1=postgresMdas.createNativeQuery(query).getResultList();
	    	 for (int i = 0; i < list1.size(); i++) 
	    	 {
	    		Object[] str=(Object[]) list1.get(i);
	    		for (int j = 0; j < str.length; j++) 
	    		{
	    			if(j==0)
	    			{
	    				model.put("LNT", str[j]);
	    				//model.put("LNTCONSUMERCOUNT",(((BigDecimal)str[j]).longValue()*totalCount/100));
	    			}
	    			if(j==1)
	    			{
	    				model.put("LNG", str[j]);
	    			}
	    			if(j==2)
	    			{
	    				model.put("GENUSC", str[j]);
	    			}
	    			if(j==3)
	    			{
	    				model.put("GENUSD", str[j]);
	    			}
	    			if(j==4)
	    			{
	    				model.put("HPLM", str[j]);
	    			}
	    			if(j==5)
	    			{
	    				model.put("HPLD", str[j]);
	    			}
	    			if(j==6)
	    			{
	    				model.put("SECURE", str[j]);
	    			}
	    			if(j==7)
	    			{
	    				model.put("OTHERS", str[j]);
	    			}
	    			if(j==8)
	    			{
	    				model.put("LNTCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==9)
	    			{
	    				model.put("LNGCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==10)
	    			{
	    				model.put("GENUSCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==11)
	    			{
	    				model.put("GENUSDCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==12)
	    			{
	    				model.put("HPLMCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==13)
	    			{
	    				model.put("HPLDCONSUMERCOUNT", str[j]);
	    			}
	    			if(j==14)
	    			{
	    				model.put("SECURECONSUMERCOUNT", str[j]);
	    			}
	    			if(j==15)
	    			{
	    				model.put("OTHERSCONSUMERCOUNT", str[j]);
	    			}
	    		}
	    	 }
	     } 
	     catch (Exception e) 
	     {
			e.printStackTrace();
		}
		return list;
	
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getAllMNP()
	{
	
		return postgresMdas.createNamedQuery("Master.getAllMnp").getResultList();
	}

	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateOldAcc(String accno,String oldaccno,HttpServletRequest request, ModelMap model,String circle,String division,String sdoname) 
	{
		System.out.println("come inside upload acc");
		int res=0;
		Master exist=find(accno);
		if(exist==null)
		{
			short sdocode=(short) model.get("sdocodeValue");
			
			//System.out.println("circle  in Master Update..."+circle+"division---"+division+"sdoname----"+sdoname);
			res= postgresMdas.createNamedQuery("Master.UpdateOldAcc").setParameter("accno",accno).setParameter("oldaccno", oldaccno).setParameter("sdocode", sdocode).
					setParameter("circle", circle).setParameter("division", division).setParameter("sdoname", sdoname).executeUpdate();
		}
		return res;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getMeterDataInformation(String meterNo,String billmonth)
	{
		List list = null;
		try {			
			final String queryString = "SELECT mm.accno,mm.ctrn,mm.ctrd,m.contractdemand,mm.mf FROM Master m,MeterMaster mm WHERE m.accno=mm.accno AND mm.metrno = '"+meterNo+"' AND mm.rdngmonth = '"+billmonth+"'";
			System.out.println(queryString);
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();						
			return list;				
		} 
		catch (RuntimeException re) 
		{
			re.printStackTrace();
			throw re;
		}	
	}

	@Override
	public String findSDOName(MeterMaster meterMaster, ModelMap model) {
		String res=null;
	try {
		res=(String)postgresMdas.createNamedQuery("Master.FindSDOName").setParameter("accno",meterMaster.getAccno()).getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
		
		return res;
	}

	@Override
	public String findTarrifCode(MeterMaster meterMaster, ModelMap model) {
		String tarifcode=null;
		try{
			
			tarifcode=(String)postgresMdas.createNamedQuery("Master.FindTrridcode").setParameter("accno",meterMaster.getAccno()).getSingleResult();		
		}
		
	 catch(Exception e)
	 {
		 e.printStackTrace();
		 return null;
	 }
		return tarifcode;
			}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public long checkAccnoExist(String accno)
	{
		return (long)postgresMdas.createNamedQuery("Master.checkAccnExists").setParameter("accno", accno).getSingleResult();
	}
	
	
	@Override
	public long countTotalInst(MeterMaster meterMaster)
	{
		Long countTotal=null;
		try
		{

			countTotal=(long)getCustomEntityManager("postgresMdas").createNamedQuery("Master.findTotalInst").setParameter("rdngmonth",meterMaster.getRdngmonth()).getSingleResult();

			if(countTotal>0)
				return countTotal;
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		return countTotal;
		
	}


	@Override
	public Boolean addMrname(MeterMaster meterMaster)
	{
		boolean flag=false;
		try
		{
			List l=postgresMdas.createNamedQuery("Master.addMrname").setParameter("mrname",meterMaster.getAccno()).setParameter("accno",meterMaster.getAccno()).getResultList();
			if(l.size()>0)
				return flag=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return flag;
	}

	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updatePhoneno(String accno,String phoneno, String industrytype) 
	{
		int res=0;
		res= postgresMdas.createNamedQuery("Master.updatePhoneno").setParameter("accno",accno).setParameter("phoneno", phoneno).setParameter("industrytype", industrytype).executeUpdate();
		
		return res;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getDistinctMrname()
	{
		String sql="SELECT DISTINCT mrname FROM mdm_test.MASTER where mrname is not null";
		return postgresMdas.createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<Master> findMr()
	{
	//Object array;
	return getCustomEntityManager("postgresMdas").createNamedQuery("Master.FindAll").getResultList();
	/*String array="SELECT DISTINCT m.mrname FROM MASTER m WHERE MRNAME NOT IN ('0','NA','na') and m.mrname is NOT NULL ORDER BY m.mrname";
	
	for (int i = 0; i < array.size(); i++) {
		Master data=new Master();
		String mrname;
		data.setMrname(mrname);
	}
		String sql="SELECT DISTINCT MRNAME FROM MASTER WHERE MRNAME is NOT NULL";
		return postgresMdas.createNativeQuery(sql).getResultList();*/
	}
	
	public List<Master> findSdoCode()
	{
		return getCustomEntityManager("postgresMdas").createNamedQuery("Master.FindSdoCode").getResultList();
	}
	@Override
	public List<Master> findTadesc() {
		
		return postgresMdas.createNamedQuery("Master.FindTadesc").getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<?> FindMrNameOnSdo(String sitecode, String tadesc) {
		
		System.out.println("sitecode-->"+sitecode+"tadesc-->"+tadesc);
		List<String>  mrName=null;
		try
		{
			if(sitecode.equals("%")&&tadesc.equals("%"))
			{
				System.out.println("both ALL");
				mrName=postgresMdas.createNamedQuery("Master.FindMrNameForAllST").getResultList();
								
			}
			else if(sitecode.equals("%") && tadesc!="%")
			{
				System.out.println("only sitecode ALL");
				mrName=postgresMdas.createNamedQuery("Master.FindMrNameForAllSDO").getResultList();
			}
			else if(sitecode!="ALL" && tadesc.equals("ALL"))
			{
			//	short sitecode1=(short)Integer.parseInt(sitecode);
			//	System.out.println("sitecode1==="+sitecode1);
				System.out.println("only tadesc ALL");
				mrName=postgresMdas.createNamedQuery("Master.FindMrNameForAllTadesc").setParameter("sdocode",sitecode).getResultList();
			}
			else{
				System.out.println("not all");
			//	short sitecode1=(short)Integer.parseInt(sitecode);
			//	System.out.println("sitecode1==="+sitecode1);
				System.out.println("tadesc==="+tadesc);
			mrName=getCustomEntityManager("postgresMdas").createNamedQuery("Master.FindMrNameOnSdoTde").setParameter("sdocode",sitecode).getResultList();		
			}
			}
		
		
	 catch(Exception e)
	 {
		 e.printStackTrace();
		 return null;
	 }
		return mrName;
		}
	
	@SuppressWarnings("unchecked")
	public List<?> FindTadescCode(String sitecode) {
		
		List<String>  tadesc=null;
		
		
		try
		{
		if(sitecode.equals("%"))
		{
			System.out.println("sitecode1==="+sitecode);
			tadesc=postgresMdas.createNamedQuery("Master.FindAllTadescCode").getResultList();
			
			
		}
		else{
			//short sitecode1=(short)Integer.parseInt(sitecode);
		//	System.out.println("sitecode1==="+sitecode1);
			tadesc=postgresMdas.createNamedQuery("Master.FindTadescCode").setParameter("sdocode",sitecode).getResultList();	
			
		}
		}
		
	 catch(Exception e)
	 {
		 e.printStackTrace();
		 return null;
	 }
		return tadesc;
	}
	@Override
	public List<?> getMrnames() {
		
		 return postgresMdas.createNamedQuery("Mrname.FindAllMrnames").getResultList();
	}
	
	
	@Override
	public List<?> getALLCategories() {
		System.out.println("calling category.. query");
		return postgresMdas.createNamedQuery("Master.FindAllCategory").getResultList();
		
	}
	public Long getCount(String accno) {
		
		System.out.println("callingggggggg");
		
		return (Long) getCustomEntityManager("postgresMdas").createNamedQuery("Master.getCount").setParameter("accno", accno).getSingleResult();
		
		// TODO Auto-generated method stub
	}

	
	@Override
	public List<Master> getALLDivisions() {
		List division=null;
		System.out.println("calling division.. query");
		try {
			
		

		String qry="SELECT DISTINCT(division) FROM meter_data.MASTER WHERE division is not NULL ORDER BY DIVISION";
		division=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return division;
				
	}

	
	
	
	@Override
	public List<?> getALLCircle() {
		List<?> circle=null;
		//System.out.println("calling circle.. query");
		try {
			
		
		String qry="SELECT DISTINCT(circle) FROM meter_data.MASTER WHERE division is not NULL ORDER BY CIRCLE ASC";
		circle=postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return circle;
				
	}
	@Override
	public List<?> getALLMrNameByDiv(String division) {
		//List<String> mrName=null;
		System.out.println("calling Mrname Based on  Division .. query "+division);
	
			
		/*
		String MRqry="SELECT DISTINCT(MRNAME) FROM MASTER where DIVISION='"+division+"' AND CONSUMERSTATUS LIKE 'R' AND MRNAME is NOT NULL ORDER BY MRNAME";
		mrName=postgresMdas.createNativeQuery(MRqry).getResultList();*/
		
		return postgresMdas.createNamedQuery("Master.getMrnameByDIV").setParameter("division", division).getResultList();
		
		/*}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mrName;*/
		
	}
	
	@Override
	public List<?> getALLMrNameByCIR(String circle) {
	
		//System.out.println("calling Mrname Based on  Circle .. query "+circle);
		return postgresMdas.createNamedQuery("Master.getMrnameByCIR").setParameter("circle", circle).getResultList();
		
	}
	
	//Added by vijayalaxmi
	@Override
	public List<?> findSdoCodeBasedonCirle(String circle,String mrname) {
		return postgresMdas.createNamedQuery("Master.findSdoCodeBasedonCirle").setParameter("circle", circle).setParameter("mrname", mrname).getResultList();
	}
	
	@Override
	public List<?> findSecondSdoCodesByCircle(String circle) {
		System.out.println("come to findSecondSdoCodesByCircle--"+circle);
		return postgresMdas.createNamedQuery("Master.findSecondSdoCodesByCircle").setParameter("circle", circle).getResultList();
	}
	
	@Override
	public List<?> findMrNameBasedonCirleSdoCode(String circle) {
		
		return postgresMdas.createNamedQuery("Master.findMrNameBasedonCirleSdoCode").setParameter("circle", circle).getResultList();
	}
	@Override
	public String getcircleByAccno(String accno) 
	{
		String qry="SELECT CIRCLE FROM MASTER WHERE ACCNO='"+accno+"'";
		System.out.println(qry);
		return (String) postgresMdas.createNativeQuery(qry).getSingleResult();
	}
	
	
	@Override
	public List<?> findFirstMrName(String circle) {
		return postgresMdas.createNamedQuery("Master.findFirstMrName").setParameter("circle", circle).getResultList();
	}
	@Override
	public List<?> getALLDivisionByCIR(String circle) {
		//System.out.println("calling Mrname Based on  Circle .. query "+circle);
		return postgresMdas.createNamedQuery("Master.getDivisionByCIR").setParameter("circle", circle).getResultList();
	}
	@Override
	public List<?> getALLMNPByCIR(String circle) {
		//System.out.println("calling Mrname Based on  Circle .. query "+circle);
		return postgresMdas.createNamedQuery("Master.getmnpByCIR").setParameter("circle", circle).getResultList();
	}
	
	@Override
	public List<?> findSecondMrName(String circle,String sdocode) {
		
		return postgresMdas.createNamedQuery("Master.findSecondMrName").setParameter("circle", circle).setParameter("sdocode", Short.parseShort(sdocode)).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<?> FindTadescCodeByMrname(String sdocode,String circle,String mrname) {
		
		List<String>  tadesc=null;
		
		/*	System.out.println("circle==="+circle);
			System.out.println("sdocode==="+sdocode);
			System.out.println("mrname==="+mrname);*/
			
			tadesc=postgresMdas.createNamedQuery("Master.FindAllTadescByMrname").setParameter("circle",circle).setParameter("sdocode",Short.parseShort(sdocode)).setParameter("mrname", mrname).getResultList();
			
			
		
		return tadesc;
	}
	
	@Override
	public Object getTadescforMrWise() 
	{
		return postgresMdas.createNamedQuery("Master.getTadescforMrWise").getResultList();
	}
	
	@Override
	public List<?> getMrWiseRecordonRdngMonthTadsc(String billMonth,String tadesc) 
	{
		String qry="select CIRCLE,count(case when TADESC  like 'HT' then 1 end) as HT_total,"
				+ " count(case when readingdate is not null and TADESC like 'HT' then 1 end) as HT_Completed,"
				+ " count(case when readingdate is null and TADESC  like 'HT' then 1 end) as HT_pending,"
				+ " count(case when TADESC  like 'ABT%' then 1 end) as ABT_total,"
				+ " count(case when readingdate is not null and TADESC like 'ABT%' then 1 end) as ABT_Completed,"
				+ " count(case when readingdate is null and TADESC  like 'ABT%' then 1 end) as ABT_pending,"
				+ " count(case when TADESC  not like 'ABT%' and TADESC not like 'HT' then 1 end) as MIP_total,count(case when TADESC  not like 'ABT%' and TADESC not like 'HT' and "
				+ " readingdate is not null then 1 end) as MIP_Completed,"
				+ " count(case when readingdate is null and TADESC  not like 'ABT%' and TADESC not like 'HT' then 1 end) as MIP_pending"
				+ " FROM meter_data.MM where RDNGMONTH='"+billMonth+"' and CST like 'R' group by CIRCLE order by CIRCLE";
		System.out.println("sry---->"+qry);
		    List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		    return list;
	}
	
	
	@Override
	public Object getCircleForMrWiseTotal() 
	{
		return postgresMdas.createNamedQuery("Master.getCircleForMrWiseTotal").getResultList();
	}
	
	
	@Override
	public List<?> getMrwiseDataOnRdngMonthCircle(String billMonth,String circle)
	{
		String qry="select circle,sdocode,subdiv,mmrname,count(case when tadesc  like 'ABT%' then 1 end) as ABT_total,"
		+" count(case when readingdate is not null and tadesc like 'ABT%' then 1 end) as ABT_Completed,"
		+" count(case when readingdate is null and tadesc  like 'ABT%' then 1 end) as ABT_pending,"
		+" count(case when tadesc  like 'HT' then 1 end) as HT_total,count(case when readingdate is not null and tadesc like 'HT' then 1 end) as HT_Completed,"
		+" count(case when readingdate is null and tadesc  like 'HT' then 1 end) as HT_pending,"
		+" count(case when tadesc  not like 'ABT%' and tadesc not like 'HT' then 1 end) as MIP_total,"
		+" count(case when tadesc  not like 'ABT%' and tadesc not like 'HT' and readingdate is not null then 1 end) as MIP_Completed,"
		+" count(case when readingdate is null and tadesc  not like 'ABT%' and tadesc not like 'HT' then 1 end) as MIP_pending,"
		+" count(*) as Gtotal,count(case when  readingdate is not null then 1 end) as Total_Completed,"
		+" count(case when readingdate is null   then 1 end) as Total_pending"
		+" FROM meter_data.MM WHERE rdngmonth='"+billMonth+"' and cst like 'R' and circle like '"+circle+"'  group by circle,sdocode,subdiv,mmrname order by mmrname";
	
		System.out.println("List size---->"+postgresMdas.createNativeQuery(qry).getResultList().size());
		return postgresMdas.createNativeQuery(qry).getResultList();
	}
	
	
	@Override
	public Object getAllCircleforMisReport()
	{
		List<?> circle=null;
		try {
			String qry="select DISTINCT(m.circle) from meter_data.MASTER m WHERE m.circle is not null ORDER BY CIRCLE ASC";
			circle=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return circle;
	}

	@Override
	public Object getAllCategory()
	{
		List<?> circle=null;
		try {
			String qry="select DISTINCT category from meter_data.master";
			circle=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return circle;
	}

	@Override
	public Object getAllParts()
	{
		List<?> circle=null;
		try {
			String qry="select DISTINCT part from meter_data.master";
			circle=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return circle;
	}

	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getAllMNPOnSdoNames(String sdoName,String circle) {
		List<?> mnp=null;
		try {
			
			mnp= postgresMdas.createNamedQuery("Master.getAllMnpOnSdoName").setParameter("sdoname", sdoName).setParameter("circle", circle).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mnp;	
		
	}
	@Override
	public List<?> findSdoNames(String circle) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("Master.getSDONameByCir").setParameter("circle", circle).getResultList();
		
	}
	
	@Override
	public List<?> findMrNamesBySdoNames(String sdoname) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("Master.findMrNamesBySdoNames").setParameter("sdoname", sdoname).getResultList();
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getDistinctCircle(HttpServletRequest request)
	{

		/*return postgresMdas.createNamedQuery("Master.getDistinctCircle").setParameter("discom", "UHBVN").getResultList();*/
		//newly changed
		return getCustomEntityManager("postgresMdas").createNamedQuery("Master.getDistinctCircle").getResultList();

	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getAllSubDiv(ModelMap model,HttpServletRequest request) 
	{
		System.out.println("subdivision");
		List<Object[]> list=null;
		List<Object[]> list1=null;
		List<Object[]> list2=null;
		List<Object[]> list3=null;
		List<Object[]> list4=null;

		String sql="SELECT DISTINCT SUBDIV FROM meter_data.MASTER WHERE  SUBDIV IS NOT NULL"; //DISCOM LIKE 'UHBVN' AND

		list=postgresMdas.createNativeQuery(sql).getResultList();
		model.put("subdivlist",list);

		String sql1="SELECT DISTINCT TRIM(CATEGORY) FROM meter_data.METERMASTER WHERE  CATEGORY IS NOT NULL"; //DISCOM LIKE 'UHBVN' AND

		list1=postgresMdas.createNativeQuery(sql1).getResultList();
		model.put("category",list1);

		String sql2="SELECT DISTINCT GROUP_VALUE FROM meter_data.METERMASTER WHERE  RDNGMONTH=(SELECT MAX(MM.RDNGMONTH) FROM meter_data.METERMASTER MM) AND GROUP_VALUE IS NOT NULL ORDER BY GROUP_VALUE";  //DISCOM LIKE 'UHBVN' AND

		list2=postgresMdas.createNativeQuery(sql2).getResultList();
		model.put("groupList",list2);

		String sql3="SELECT DISTINCT BILLING_CATEGORY FROM meter_data.METERMASTER WHERE  RDNGMONTH=(SELECT MAX(MM.RDNGMONTH) FROM meter_data.METERMASTER MM) ORDER BY BILLING_CATEGORY";//DISCOM LIKE 'UHBVN' AND

		list3=postgresMdas.createNativeQuery(sql3).getResultList();
		model.put("billingCategory",list3);

		String sql4="SELECT DISTINCT PART FROM meter_data.MASTER WHERE  PART IS NOT NULL ORDER BY PART";//DISCOM LIKE 'UHBVN' AND

		list4=postgresMdas.createNativeQuery(sql4).getResultList();
		model.put("partValue",list4);
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getDistinctSubdivision(String circle,String division,HttpServletRequest request)
	{
	//	String discom=(String) request.getSession(false).getAttribute("loginType").toString().toUpperCase();
		List<String> list=null;
		try 
		{
			list=postgresMdas.createNamedQuery("Master.findSubdByCircle").setParameter("circle", circle.toUpperCase()).setParameter("division", division.toUpperCase()).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getGroupValue(String subdiv,String month, ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT DISTINCT GROUP_VALUE FROM meter_data.METERMASTER WHERE RDNGMONTH='"+month+"' AND UPPER(SUBDIV)LIKE'"+subdiv.toUpperCase()+"' AND GROUP_VALUE IS NOT NULL";
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String generateAsciiVal(String month,String subdiv,String category,String billCategory,HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			if(billCategory.trim().equalsIgnoreCase(""))
			{
				billCategory="%";
			}
			String rootFolder="D:\\BackUp";
			//String rootFolder="D:\\METER_JSON";
			if(!folderExists(rootFolder));
			PrintWriter writer = new PrintWriter(rootFolder+"\\"+"SAMPLE.txt", "UTF-8");
			/*writer.flush();
			writer.close();*/
			List<Object[]> list3=null;
			String sql="";
			String asciiValue="";
			String finalRes="";
			String meterNo="",rdate="--------",kwh="--------",kvah="--------",t1kwh="--------",t2kwh="--------",t3kwh="--------",t4kwh="--------",t5kwh="--------",t6kwh="--------",t7kwh="--------",t8kwh="--------",t1kvah="--------",t2kvah="--------",t3kvah="--------",t4kvah="--------",t5kvah="--------",t6kvah="--------",t7kvah="--------",t8kvah="--------",kva="--------",kwhe="--------",kvhe="--------",kvae="--------";
			//sql="SELECT METERNO,replace(to_char(RDATE,'dd-MM-yyyy'),'-',''),replace(KWH,'.',''),replace(KVH,'.',''),replace(T1KWH,'.',''),replace(T2KWH,'.',''),replace(T3KWH,'.',''),replace(T4KWH,'.',''),replace(T5KWH,'.',''),replace(T6KWH,'.',''),replace(T7KWH,'.',''),replace(T8KWH,'.',''),replace(T1KVAH,'.',''),replace(T2KVAH,'.',''),replace(T3KVAH,'.',''),replace(T4KVAH,'.',''),replace(T5KVAH,'.',''),replace(T6KVAH,'.',''),replace(T7KVAH,'.',''),replace(T8KVAH,'.',''),replace(KVA,'.','') FROM DHBVN.XMLIMPORT WHERE MONTH="+month+" and FLAG='1'";
			//sql="SELECT  X.METERNO,replace(to_char(x.RDATE,'dd-MM-yyyy'),'-',''),ROUND(x.KWH,1),ROUND(x.KVH,1),ROUND(x.T1KWH,1),ROUND(x.T2KWH,1),ROUND(x.T3KWH,1),ROUND(x.T4KWH,1),ROUND(x.T5KWH,1),ROUND(x.T6KWH,1),ROUND(x.T7KWH,1),ROUND(x.T8KWH,1),ROUND(x.T1KVAH,1),ROUND(x.T2KVAH,1),ROUND(x.T3KVAH,1),ROUND(x.T4KVAH,1),ROUND(x.T5KVAH,1),ROUND(x.T6KVAH,1),ROUND(x.T7KVAH,1),ROUND(x.T8KVAH,1),ROUND(x.KVA,2) FROM METERMASTER MM, XMLIMPORT X WHERE MM.METRNO=X.METERNO   AND X.FLAG=1 AND MM.SUBDIV='"+subdiv+"'";
			/*sql="SELECT BB.* FROM"+" "
+"("+" "
+"SELECT DISTINCT X.METERNO AS AMTRNO,replace(to_char(x.RDATE,'dd-MM-yyyy'),'-',''),ROUND(x.KWH,1),ROUND(x.KVH,1),ROUND(x.T1KWH,1),"+" "
+"ROUND(x.T2KWH,1),ROUND(x.T3KWH,1),ROUND(x.T4KWH,1),ROUND(x.T5KWH,1),ROUND(x.T6KWH,1),ROUND(x.T7KWH,1),ROUND(x.T8KWH,1),ROUND(x.T1KVAH,1),ROUND(x.T2KVAH,1),ROUND(x.T3KVAH,1),ROUND(x.T4KVAH,1),ROUND(x.T5KVAH,1),ROUND(x.T6KVAH,1),"+" "
+"ROUND(x.T7KVAH,1),ROUND(x.T8KVAH,1),ROUND(x.KVA,2),X.KWH AS AKWH FROM METERMASTER MM, XMLIMPORT X "+" "
+"WHERE MM.METRNO=X.METERNO   AND X.FLAG=1 AND MM.SUBDIV='"+subdiv+"' )AA"+" "
+" JOIN"+" "
+"("+" "
+"SELECT DISTINCT X.METERNO AS BMTRNO,replace(to_char(x.RDATE,'dd-MM-yyyy'),'-',''),ROUND(x.KWH,1),ROUND(x.KVH,1),ROUND(x.T1KWH,1),"+" "
+"ROUND(x.T2KWH,1),ROUND(x.T3KWH,1),ROUND(x.T4KWH,1),ROUND(x.T5KWH,1),ROUND(x.T6KWH,1),ROUND(x.T7KWH,1),ROUND(x.T8KWH,1),ROUND(x.T1KVAH,1),ROUND(x.T2KVAH,1),ROUND(x.T3KVAH,1),ROUND(x.T4KVAH,1),ROUND(x.T5KVAH,1),ROUND(x.T6KVAH,1),"+" "
+"ROUND(x.T7KVAH,1),ROUND(x.T8KVAH,1),ROUND(x.KVA,2),X.KWH AS BKWH, (COALESCE(T1KWH,0)+COALESCE(T2KWH,0)+COALESCE(T3KWH,0)+COALESCE(T4KWH,0)+COALESCE(T5KWH,0)+COALESCE(T6KWH,0)+COALESCE(T7KWH,0)+COALESCE(T8KWH,0)) AS SUMDATA FROM METERMASTER MM, XMLIMPORT X "+" "
+"WHERE MM.METRNO=X.METERNO   AND X.FLAG=1 AND MM.SUBDIV='"+subdiv+"' )BB"+" "
+"ON AA.AMTRNO=BB.BMTRNO AND AA.AKWH=BB.SUMDATA";*/
			
			/*sql="SELECT * FROM(SELECT AA.*,ROW_NUMBER() OVER(PARTITION BY AA.BMTRNO ORDER BY AA.BMTRNO)AS ROW_NUM FROM("+" "
+"SELECT DISTINCT X.METERNO AS BMTRNO,replace(to_char(x.RDATE,'dd-MM-yyyy'),'-','') AS BRDATE,ROUND(x.KWH,1),ROUND(x.KVH,1),ROUND(x.T1KWH,1),"+" "
+"ROUND(x.T2KWH,1),ROUND(x.T3KWH,1),ROUND(x.T4KWH,1),ROUND(x.T5KWH,1),ROUND(x.T6KWH,1),ROUND(x.T7KWH,1),ROUND(x.T8KWH,1),ROUND(x.T1KVAH,1),ROUND(x.T2KVAH,1),ROUND(x.T3KVAH,1),ROUND(x.T4KVAH,1),ROUND(x.T5KVAH,1),ROUND(x.T6KVAH,1),"+" "
+"ROUND(x.T7KVAH,1),ROUND(x.T8KVAH,1),ROUND(x.KVA,2),X.KWH AS BKWH, (COALESCE(x.T1KWH,0)+COALESCE(x.T2KWH,0)+COALESCE(x.T3KWH,0)+COALESCE(x.T4KWH,0)+COALESCE(x.T5KWH,0)+COALESCE(x.T6KWH,0)+COALESCE(x.T7KWH,0)+COALESCE(x.T8KWH,0)) AS SUMDATA"+" "
+"FROM METERMASTER MM, XMLIMPORT X "+" "
+"WHERE MM.METRNO=X.METERNO   AND X.FLAG=1 AND MM.SUBDIV like '"+subdiv+"'"+" "
+") AA WHERE AA.BKWH=AA.SUMDATA "+" "
+")BB WHERE BB.ROW_NUM=1";*///WITH BKWH=SUMTKWH
			/*sql="SELECT * FROM(SELECT AA.*,ROW_NUMBER() OVER(PARTITION BY AA.BMTRNO ORDER BY AA.BMTRNO)AS ROW_NUM FROM("+" "
					+"SELECT DISTINCT X.METERNO AS BMTRNO,replace(to_char(x.RDATE,'dd-MM-yyyy'),'-','') AS BRDATE,ROUND(x.KWH,1),ROUND(x.KVH,1),ROUND(x.T1KWH,1),"+" "
					+"ROUND(x.T2KWH,1),ROUND(x.T3KWH,1),ROUND(x.T4KWH,1),ROUND(x.T5KWH,1),ROUND(x.T6KWH,1),ROUND(x.T7KWH,1),ROUND(x.T8KWH,1),ROUND(x.T1KVAH,1),ROUND(x.T2KVAH,1),ROUND(x.T3KVAH,1),ROUND(x.T4KVAH,1),ROUND(x.T5KVAH,1),ROUND(x.T6KVAH,1),"+" "
					+"ROUND(x.T7KVAH,1),ROUND(x.T8KVAH,1),ROUND(x.KVA,2),X.KWH AS BKWH, (COALESCE(x.T1KWH,0)+COALESCE(x.T2KWH,0)+COALESCE(x.T3KWH,0)+COALESCE(x.T4KWH,0)+COALESCE(x.T5KWH,0)+COALESCE(x.T6KWH,0)+COALESCE(x.T7KWH,0)+COALESCE(x.T8KWH,0)) AS SUMDATA"+" "
					+"FROM METERMASTER MM, XMLIMPORT X "+" "
					+"WHERE MM.METRNO=X.METERNO   AND X.FLAG=1 AND UPPER(MM.CATEGORY) LIKE '"+category.toUpperCase().trim()+"' AND MM.GROUP_VALUE LIKE '"+group.trim()+"' AND MM.BILLING_CATEGORY LIKE '"+billCategory.trim()+"' AND MM.SUBDIV like '"+subdiv.trim()+"'"+" "
					+") AA "+" "
					+")BB WHERE BB.ROW_NUM=1";*/
			if("SOLAR".equalsIgnoreCase(billCategory))
			{
				sql="SELECT METRNO,replace(to_char(RDATE,'dd-MM-yyyy'),'-','') AS BRDATE,XCURRDNGKWH,XCURRRDNGKVAH,"+" "
				+"ROUND(T1KWH,1), ROUND(T2KWH,1),ROUND(T3KWH,1),ROUND(T4KWH,1),ROUND(T5KWH,1),ROUND(T6KWH,1),ROUND(T7KWH,1),ROUND(T8KWH,1),"+" "
				+"ROUND(T1KVAH,1),ROUND(T2KVAH,1),ROUND(T3KVAH,1),ROUND(T4KVAH,1),ROUND(T5KVAH,1),ROUND(T6KVAH,1), ROUND(T7KVAH,1),ROUND(T8KVAH,1),ROUND(XCURRDNGKVA,2),KWHE,KVHE,KVAE"+" "
				+"FROM meter_data.METERMASTER WHERE CAST(RDNGMONTH as text) LIKE '"+month+"'  AND "
				+"TRIM(BILLING_CATEGORY) LIKE '"+billCategory.toUpperCase().trim()+"' AND TRIM(SUBDIV) LIKE '"+subdiv.trim()+"' AND DISCOM LIKE 'UHBVN' AND "
				+"TRIM(CATEGORY) LIKE '"+category.toUpperCase().trim()+"' AND XCURRDNGKWH>0 and xcurrdngkwh is not null and xmldate is not null AND KWHE is not NULL";
			}
			else
			{
				sql="SELECT METRNO,replace(to_char(RDATE,'dd-MM-yyyy'),'-','') AS BRDATE,XCURRDNGKWH,XCURRRDNGKVAH,"+" "
						+"ROUND(T1KWH,1), ROUND(T2KWH,1),ROUND(T3KWH,1),ROUND(T4KWH,1),ROUND(T5KWH,1),ROUND(T6KWH,1),ROUND(T7KWH,1),ROUND(T8KWH,1),"+" "
						+"ROUND(T1KVAH,1),ROUND(T2KVAH,1),ROUND(T3KVAH,1),ROUND(T4KVAH,1),ROUND(T5KVAH,1),ROUND(T6KVAH,1), ROUND(T7KVAH,1),ROUND(T8KVAH,1),ROUND(XCURRDNGKVA,2)"+" "
						+"FROM meter_data.METERMASTER WHERE CAST(RDNGMONTH as text) LIKE '"+month+"' "
						+" AND TRIM(SUBDIV) LIKE '"+subdiv.trim()+"' AND "
						+"TRIM(CATEGORY) LIKE '"+category.toUpperCase().trim()+"' AND XCURRDNGKWH>0 and xcurrdngkwh is not null and xmldate is not null";
			}
			
			/*		sql="SELECT METRNO,replace(to_char(RDATE,'dd-MM-yyyy'),'-','') AS BRDATE,XCURRDNGKWH,XCURRRDNGKVAH,"+" "
				+"ROUND(T1KWH,1), ROUND(T2KWH,1),ROUND(T3KWH,1),ROUND(T4KWH,1),ROUND(T5KWH,1),ROUND(T6KWH,1),ROUND(T7KWH,1),ROUND(T8KWH,1),"+" "
					+"ROUND(T1KVAH,1),ROUND(T2KVAH,1),ROUND(T3KVAH,1),ROUND(T4KVAH,1),ROUND(T5KVAH,1),ROUND(T6KVAH,1), ROUND(T7KVAH,1),ROUND(T8KVAH,1),ROUND(XCURRDNGKVA,2)"+" "
					+"FROM meter_data.METERMASTER WHERE CAST(RDNGMONTH as text) LIKE '"+month+"' AND "
					+"TRIM(BILLING_CATEGORY) LIKE '"+billCategory.toUpperCase().trim()+"' AND TRIM(SUBDIV) LIKE '"+subdiv.trim()+"' AND DISCOM LIKE 'UHBVN' AND "
					+"TRIM(CATEGORY) LIKE '"+category.toUpperCase().trim()+"' AND XCURRDNGKWH>0 and xcurrdngkwh is not null and xmldate is not null";
	*/
			System.err.println(sql);
			list3=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			if(list3.size()==0)
			{
				return "noData";
			}
			for (int i = 0; i <list3.size(); i++) 
			{
				Object[] str=list3.get(i);
				for (int j = 0; j < str.length; j++)
				{
					if(j==0)
					{
						meterNo=str[j].toString();
						
						if(meterNo.length()<8)
						{
							meterNo="0"+meterNo;
							for (int k = 0; k<8; k++) 
							{
								if(meterNo.length()!=8)
								{
									meterNo="0"+meterNo;
								}
							}
							asciiValue=asciiValue+meterNo;
						}
						else
						{
							asciiValue=asciiValue+meterNo;
						}
					}
					if(j==1)
					{
						if(str[j]!=null)
						{
							rdate=str[j].toString();
							asciiValue=asciiValue+rdate;
						}
						else
						{
							asciiValue=asciiValue+rdate;
						}
						
					}
					if(j==2)
					{
						if(str[j]!=null)
					  {
						kwh=str[j].toString();
						if(asciiValue.length()==16)
						{
							for (int k = 1; k<=46; k++) 
							{
								asciiValue=asciiValue+"-";
							}
							if(kwh.length()>8)
							{
								kwh=kwh.substring(0, 8);
								asciiValue=asciiValue+kwh;
							}
							else if(kwh.length()<8)
							{
								kwh="0"+kwh;
								for (int k = 0; k<8; k++) 
								{
									if(kwh.length()!=8)
									{
										kwh="0"+kwh;
									}
								}
								asciiValue=asciiValue+kwh;
							}
							else
							{
								asciiValue=asciiValue+kwh;
							}
						}
					}
					else
					{
						for (int k = 1; k<=46; k++) 
						{
							asciiValue=asciiValue+"-";
						}
						asciiValue=asciiValue+kwh;
					}
					}
					
					
					if(j==3)
					{
						if(str[j]!=null)
							{
							
							kvah=str[j].toString();
							if(asciiValue.length()==70)
							{
								for (int k = 1; k<=32; k++) 
								{
									asciiValue=asciiValue+"-";
								}
								if(kvah.length()>8)
								{
									kvah=kvah.substring(0, 8);
									asciiValue=asciiValue+kvah;
									
								}
								else if(kvah.length()<8)
								{
									kvah="0"+kvah;
									for (int k = 0; k<8; k++) 
									{
										if(kvah.length()!=8)
										{
											kvah="0"+kvah;
										}
									}
									asciiValue=asciiValue+kvah;
								}
								else
								{
									asciiValue=asciiValue+kvah;
								}
							}
							
						}
							else
							{
								for (int k = 1; k<=32; k++) 
								{
									asciiValue=asciiValue+"-";
								}
								asciiValue=asciiValue+kvah;
							}
					}
					/*if(i==0)
					{
						MDMLogger.logger.info("=========>"+asciiValue.length()+"==-=-=-="+asciiValue);
					}*/
					if(j==4)
					{
						if(str[j]!=null)
						{
						t1kwh=str[j].toString();
						if(asciiValue.length()==110)
						{
							for (int k = 1; k<=20; k++) 
							{
								asciiValue=asciiValue+"-";
							}
							if(t1kwh.length()>8)
							{
								t1kwh=t1kwh.substring(0, 8);
								asciiValue=asciiValue+t1kwh;
							}
							else if(t1kwh.length()<8)
							{
								t1kwh="0"+t1kwh;
								for (int k = 0; k<8; k++) 
								{
									if(t1kwh.length()!=8)
									{
										t1kwh="0"+t1kwh;
									}
								}
								asciiValue=asciiValue+t1kwh;
							}
							else
							{
								asciiValue=asciiValue+t1kwh;
							}
						}
						}
						else
						{
							for (int k = 1; k<=20; k++) 
							{
								asciiValue=asciiValue+"-";
							}
							asciiValue=asciiValue+t1kwh;
						}
					}
					
					if(j==5)
					{
						if(str[j]!=null)
						{
						t2kwh=str[j].toString();
						
							if(t2kwh.length()>8)
							{
								t2kwh=t2kwh.substring(0, 8);
								asciiValue=asciiValue+t2kwh;
							}
							else if(t2kwh.length()<8)
							{
								t2kwh="0"+t2kwh;
								for (int k = 0; k<8; k++) 
								{
									if(t2kwh.length()!=8)
									{
										t2kwh="0"+t2kwh;
									}
								}
								asciiValue=asciiValue+t2kwh;
							}
							else
							{
								asciiValue=asciiValue+t2kwh;
							}
						}
						else
						{
							asciiValue=asciiValue+t2kwh;
						}
					}
					
					if(j==6)
					{
						if(str[j]!=null)
						{
						t3kwh=str[j].toString();
						
						if(t3kwh.length()>8)
						{
							t3kwh=t3kwh.substring(0, 8);
							asciiValue=asciiValue+t3kwh;
						}
						else if(t3kwh.length()<8)
						{
							t3kwh="0"+t3kwh;
							for (int k = 0; k<8; k++) 
							{
								if(t3kwh.length()!=8)
								{
									t3kwh="0"+t3kwh;
								}
							}
							asciiValue=asciiValue+t3kwh;
						}
						else
						{
							asciiValue=asciiValue+t3kwh;
						}
						}
						else
						{
							asciiValue=asciiValue+t3kwh;
						}
					
					}
					
					if(j==7)
					{
						if(str[j]!=null)
						{
						t4kwh=str[j].toString();
						
							if(t4kwh.length()>8)
							{
								t4kwh=t4kwh.substring(0, 8);
								asciiValue=asciiValue+t4kwh;
							}
							else if(t4kwh.length()<8)
							{
								t4kwh="0"+t4kwh;
								for (int k = 0; k<8; k++) 
								{
									if(t4kwh.length()!=8)
									{
										t4kwh="0"+t4kwh;
									}
								}
								asciiValue=asciiValue+t4kwh;
								
								
							}
							else
							{
								asciiValue=asciiValue+t4kwh;
							}
						}
						else
						{
							asciiValue=asciiValue+t4kwh;
						}
					}
					if(j==8)
					{
						if(str[j]!=null)
						{
						t5kwh=str[j].toString();
						
						if(t5kwh.length()>8)
						{
							t5kwh=t5kwh.substring(0, 8);
							asciiValue=asciiValue+t5kwh;
						}
						else if(t5kwh.length()<8)
						{
							t5kwh="0"+t5kwh;
							for (int k = 0; k<8; k++) 
							{
								if(t5kwh.length()!=8)
								{
									t5kwh="0"+t5kwh;
								}
							}
							asciiValue=asciiValue+t5kwh;
						}
						else
						{
							asciiValue=asciiValue+t5kwh;
						}
						}
						else
						{
							asciiValue=asciiValue+t5kwh;
						}
					}
					
					if(j==9)
					{
						if(str[j]!=null)
						{
						t6kwh=str[j].toString();
						
						if(t6kwh.length()>8)
						{
							t6kwh=t6kwh.substring(0, 8);
							asciiValue=asciiValue+t6kwh;
						}
						else if(t6kwh.length()<8)
						{
							t6kwh="0"+t6kwh;
							for (int k = 0; k<8; k++) 
							{
								if(t6kwh.length()!=8)
								{
									t6kwh="0"+t6kwh;
								}
							}
							asciiValue=asciiValue+t6kwh;
						}
						else
						{
							asciiValue=asciiValue+t6kwh;
						}
						}
						else
						{
							asciiValue=asciiValue+t6kwh;
						}
					}
					if(j==10)
					{
						if(str[j]!=null)
						{
						t7kwh=str[j].toString();
						
						if(t7kwh.length()>8)
						{
							t7kwh=t7kwh.substring(0, 8);
							asciiValue=asciiValue+t7kwh;
						}
						else if(t7kwh.length()<8)
						{
							t7kwh="0"+t7kwh;
							for (int k = 0; k<8; k++) 
							{
								if(t7kwh.length()!=8)
								{
									t7kwh="0"+t7kwh;
								}
							}
							asciiValue=asciiValue+t7kwh;
							
						}
						else
						{
							asciiValue=asciiValue+t7kwh;
						}
						}
						else
						{
							asciiValue=asciiValue+t7kwh;
						}
					}
					
					if(j==12)
					{
						if(str[j]!=null)
						{
							t1kvah=str[j].toString();
							if(asciiValue.length()==186)
							{
								for (int k = 1; k<=72; k++) 
								{
									asciiValue=asciiValue+"-";
								}
								if(t1kvah.length()>8)
								{
									t1kvah=t1kvah.substring(0, 8);
									asciiValue=asciiValue+t1kvah;
								}
								else if(t1kvah.length()<8)
								{
									t1kvah="0"+t1kvah;
									for (int k = 0; k<8; k++) 
									{
										if(t1kvah.length()!=8)
										{
											t1kvah="0"+t1kvah;
										}
									}
									asciiValue=asciiValue+t1kvah;
								}
								else
								{
									asciiValue=asciiValue+t1kvah;
								}
							}
						}
						else
						{
							for (int k = 1; k<=72; k++) 
							{
								asciiValue=asciiValue+"-";
							}
							asciiValue=asciiValue+t1kvah;
						}
						
						
					}
					
					if(j==13)
					{
						if(str[j]!=null)
						{
						t2kvah=str[j].toString();
						
							if(t2kvah.length()>8)
							{
								t2kvah=t2kvah.substring(0, 8);
								asciiValue=asciiValue+t2kvah;
							}
							else if(t2kvah.length()<8)
							{
								t2kvah="0"+t2kvah;
								for (int k = 0; k<8; k++) 
								{
									if(t2kvah.length()!=8)
									{
										t2kvah="0"+t2kvah;
									}
								}
								asciiValue=asciiValue+t2kvah;
							}
							else
							{
								asciiValue=asciiValue+t2kvah;
							}
						}
						else
						{
							asciiValue=asciiValue+t2kvah;
						}
					}
					
					if(j==14)
					{
						if(str[j]!=null)
						{
						t3kvah=str[j].toString();
						
						if(t3kvah.length()>8)
						{
							t3kvah=t3kvah.substring(0, 8);
							asciiValue=asciiValue+t3kvah;
						}
						else if(t3kvah.length()<8)
						{
							t3kvah="0"+t3kvah;
							for (int k = 0; k<8; k++) 
							{
								if(t3kvah.length()!=8)
								{
									t3kvah="0"+t3kvah;
								}
							}
							asciiValue=asciiValue+t3kvah;
						}
						else
						{
							asciiValue=asciiValue+t3kvah;
						}
						}
						else
						{
							asciiValue=asciiValue+t3kvah;
						}
					
					}
					
					if(j==15)
					{
						if(str[j]!=null)
						{
						t4kvah=str[j].toString();
						
							if(t4kvah.length()>8)
							{
								t4kvah=t4kvah.substring(0, 8);
								asciiValue=asciiValue+t4kvah;
							}
							else if(t4kvah.length()<8)
							{
								t4kvah="0"+t4kvah;
								for (int k = 0; k<8; k++) 
								{
									if(t4kvah.length()!=8)
									{
										t4kvah="0"+t4kvah;
									}
								}
								asciiValue=asciiValue+t4kvah;
							}
							else
							{
								asciiValue=asciiValue+t4kvah;
							}
						}
						else
						{
							asciiValue=asciiValue+t4kvah;
						}
					}
					if(j==16)
					{
						if(str[j]!=null)
						{
						t5kvah=str[j].toString();
						
						if(t5kvah.length()>8)
						{
							t5kvah=t5kvah.substring(0, 8);
							asciiValue=asciiValue+t5kvah;
						}
						else if(t5kvah.length()<8)
						{
							t5kvah="0"+t5kvah;
							for (int k = 0; k<8; k++) 
							{
								if(t5kvah.length()!=8)
								{
									t5kvah="0"+t5kvah;
								}
							}
							asciiValue=asciiValue+t5kvah;
						}
						else
						{
							asciiValue=asciiValue+t5kvah;
						}
						}
						else
						{
							asciiValue=asciiValue+t5kvah;
						}
					}
					
					if(j==17)
					{
						if(str[j]!=null)
						{
						t6kvah=str[j].toString();
						
						if(t6kvah.length()>8)
						{
							t6kvah=t6kvah.substring(0, 8);
							asciiValue=asciiValue+t6kvah;
						}
						else if(t6kvah.length()<8)
						{
							t6kvah="0"+t6kvah;
							for (int k = 0; k<8; k++) 
							{
								if(t6kvah.length()!=8)
								{
									t6kvah="0"+t6kvah;
								}
							}
							asciiValue=asciiValue+t6kvah;
						}
						else
						{
							asciiValue=asciiValue+t6kvah;
						}
						}
						else
						{
							asciiValue=asciiValue+t6kvah;
						}
					}
					if(j==18)
					{
						if(str[j]!=null)
						{
						t7kvah=str[j].toString();
						
						if(t7kvah.length()>8)
						{
							t7kvah=t7kvah.substring(0, 8);
							asciiValue=asciiValue+t7kvah;
						}
						else if(t7kvah.length()<8)
						{
							t7kvah="0"+t7kvah;
							for (int k = 0; k<8; k++) 
							{
								if(t7kvah.length()!=8)
								{
									t7kvah="0"+t7kvah;
								}
							}
							asciiValue=asciiValue+t7kvah;
						}
						else
						{
							asciiValue=asciiValue+t7kvah;
						}
						}
						else
						{
							asciiValue=asciiValue+t7kvah;
						}
					}
					if(j==19)
					{
						if(str[j]!=null)
						{
						t8kvah=str[j].toString();
						
						if(t8kvah.length()>8)
						{
							t8kvah=t8kvah.substring(0, 8);
							asciiValue=asciiValue+t8kvah;
						}
						else if(t8kvah.length()<8)
						{
							t8kvah="0"+t8kvah;
							for (int k = 0; k<8; k++) 
							{
								if(t8kvah.length()!=8)
								{
									t8kvah="0"+t8kvah;
								}
							}
							asciiValue=asciiValue+t8kvah;
						}
						else
						{
							asciiValue=asciiValue+t8kvah;
						}
						}
						else
						{
							asciiValue=asciiValue+t8kvah;
						}
					}
					
					if("SOLAR".equalsIgnoreCase(billCategory))
					{
									if(j==20)
									{
										if(str[j]!=null)
										{
										kva=str[j].toString();
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											if(kva.length()>8)
											{
												kva=kva.substring(0, 8);
												asciiValue=asciiValue+kva;
											}
											else if(kva.length()<8)
											{
												kva="0"+kva;
												for (int k = 0; k<8; k++) 
												{
													if(kva.length()!=8)
													{
														kva="0"+kva;
													}
												}
												asciiValue=asciiValue+kva;
												
											}
											else
											{
												asciiValue=asciiValue+kva;
											}
										}
										else
										{
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											asciiValue=asciiValue+kva;
											
										}
									}
									
									if(j==21)
									{
										if(str[j]!=null)
										{
										kwhe=str[j].toString();
										
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											if(kwhe.length()>8)
											{
												kwhe=kwhe.substring(0, 8);
												asciiValue=asciiValue+kwhe;
											}
											else if(kwhe.length()<8)
											{
												kwhe="0"+kwhe;
												for (int k = 0; k<8; k++) 
												{
													if(kwhe.length()!=8)
													{
														kwhe="0"+kwhe;
													}
												}
												asciiValue=asciiValue+kwhe;
												
											}
											else
											{
												asciiValue=asciiValue+kwhe;
											}
										}
										else
										{
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											asciiValue=asciiValue+kva;
											
										}
									}
									if(j==22)
									{
										if(str[j]!=null)
										{
											kvhe=str[j].toString();
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											if(kvhe.length()>8)
											{
												kvhe=kvhe.substring(0, 8);
												asciiValue=asciiValue+kvhe;
											}
											else if(kvhe.length()<8)
											{
												kvhe="0"+kvhe;
												for (int k = 0; k<8; k++) 
												{
													if(kvhe.length()!=8)
													{
														kvhe="0"+kvhe;
													}
												}
												asciiValue=asciiValue+kvhe;
												
											}
											else
											{
												asciiValue=asciiValue+kvhe;
											}
											
										
										}
										else
										{
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											asciiValue=asciiValue+kva;
											
										}
									}
									if(j==23)
									{
										if(str[j]!=null)
										{
											kvae=str[j].toString();
										
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											if(kvae.length()>8)
											{
												kvae=kvae.substring(0, 8);
												asciiValue=asciiValue+kva;
											}
											else if(kvae.length()<8)
											{
												kvae="0"+kvae;
												for (int k = 0; k<8; k++) 
												{
													if(kvae.length()!=8)
													{
														kvae="0"+kvae;
													}
												}
												asciiValue=asciiValue+kvae;
												
											}
											else
											{
												asciiValue=asciiValue+kvae;
											}
										}
										else
										{
											for (int k = 1; k<=64; k++) 
											{
												asciiValue=asciiValue+"-";
											}
											asciiValue=asciiValue+kvae;
											
										}
										/*if(meterNo.equalsIgnoreCase("HRT72966")||meterNo.equalsIgnoreCase("HRT72965"))
										{
											MDMLogger.logger.info("===============>asc"+asciiValue);
										}
										*/
										if(asciiValue.length()==610)
										{
											finalRes=finalRes+asciiValue+"@";
										}
										
									}
						
					}
					else
					{
										if(j==20)
										{
											if(str[j]!=null)
											{
											kva=str[j].toString();
											if(asciiValue.length()==322)
											{
												for (int k = 1; k<=64; k++) 
												{
													asciiValue=asciiValue+"-";
												}
												if(kva.length()>8)
												{
													kva=kva.substring(0, 8);
													asciiValue=asciiValue+kva;
												}
												else if(kva.length()<8)
												{
													kva="0"+kva;
													for (int k = 0; k<8; k++) 
													{
														if(kva.length()!=8)
														{
															kva="0"+kva;
														}
													}
													asciiValue=asciiValue+kva;
													
												}
												else
												{
													asciiValue=asciiValue+kva;
												}
												
											}
											}
											else
											{
												for (int k = 1; k<=64; k++) 
												{
													asciiValue=asciiValue+"-";
												}
												asciiValue=asciiValue+kva;
												
											}
											/*if(meterNo.equalsIgnoreCase("HRT72966")||meterNo.equalsIgnoreCase("HRT72965"))
											{
												MDMLogger.logger.info("===============>asc"+asciiValue);
											}
											*/
											if(asciiValue.length()==394)
											{
												finalRes=finalRes+asciiValue+"@";
											}
											
										}
										
					}
					
				}
				asciiValue="";
				meterNo="";rdate="--------";kwh="--------";kvah="--------";t1kwh="--------";t2kwh="--------";t3kwh="--------";t4kwh="--------";t5kwh="--------";t6kwh="--------";t7kwh="--------";t8kwh="--------";t1kvah="--------";t2kvah="--------";t3kvah="--------";t4kvah="--------";t5kvah="--------";t6kvah="--------";t7kvah="--------";t8kvah="--------";kva="--------";
			}
			String[] arr=finalRes.split("@");
			
			for (int i = 0; i < arr.length; i++)
			{
					//MDMLogger.logger.info("===============>arr"+arr[i]);
				writer.print(arr[i]);
				writer.print("\r\n");
				
			}
			writer.flush();
			writer.close();
			String currentDate=new SimpleDateFormat("MMM-yy").format(new SimpleDateFormat("yyyyMM").parse(month));
		//String path="E:\\PRADEEPKUMAR C R\\STS-SERVER\\apache-tomcat-7.0.12\\bin\\METER_JSON\\SAMPLE.txt";
		//G31_101_HT_May-16 
	  //String path="F:\\BSMARTMDM_LIVE\\jvvnlFeederLocator\\jboss-as-7.1.1.Final\\bin\\METER_JSON\\SAMPLE.txt";
		//	String path="D:\\jboss-as-7.1.1.Final\\bin\\METER_JSON\\SAMPLE.txt"; //for 10.250
			String path="D:\\BackUp\\sample.txt";
			File downloadFile = new File(path);
			try {
				FileInputStream inputStream = new FileInputStream(downloadFile);
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
			                     "attachment;filename="+subdiv.toUpperCase().replace(" ", "")+"_"+category+"_"+currentDate);

				 // get output stream of the response
		        OutputStream outStream = response.getOutputStream();
		 
		        byte[] buffer = new byte[1024];
		        int bytesRead = -1;
		 
		             // write bytes read from the input stream into the output stream
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        outStream.flush();
		        outStream.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private boolean folderExists(String rtfolder) {
		File rootFolder= new File(rtfolder);
		if(!rootFolder.exists())
		{
			try {
				rootFolder.mkdir();
				System.out.println("Created new folder");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Folder creation failed");
				return false;
			}
		}else
		{
			System.out.println("Folder exists");
			return true;
		}
	}
	@Override
	public List<?> getSubDiv() {
		List subdiv=null;
		System.out.println("calling subdivision.. query");
		try {
			
		

		String qry="SELECT DISTINCT(subdiv) FROM meter_data.MASTER WHERE subdiv is not NULL ORDER BY subdiv";
		subdiv=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return subdiv;
				
	}
	
	//Loading data MONTH wise for MDM
	@Override
	public List<?>  getd4LoadDataMDM(String billMonth , String meterNo ) {
		List<?> result=null;
		String query = "select meter_number, to_char(read_time, 'yyyyMM') as billmonth,SUM(COALESCE(kwh,0))/1000,to_char(read_time, 'yyyy-MM-dd') from meter_data.load_survey where meter_number ='"+meterNo+"' and to_char(read_time, 'yyyyMM')='"+billMonth+"'  group by to_char(read_time,'yyyy-MM-dd'),meter_number, billmonth";
		System.out.println("qry is------>"+query);
		result = postgresMdas.createNativeQuery(query).getResultList();
		return result;
	}
	
	// Loading data DAY wise for MDAS
	@Override
	public List<?>  getd4LoadDataMDAS(String profilDate,  String meterNo ){
		List result=null;
		
		String query = "select * from meter_data.load_survey where meter_number = '"+meterNo+"'  AND to_char(read_time, 'yyyy-MM-dd') = '"+profilDate+"' ORDER BY read_time ASC";
		System.out.println("Consumption curve ---> " +query);
		result = postgresMdas.createNativeQuery(query).getResultList();
		return result;
	}
	@Override
	public List<?>  getd4LoadDataCurveMDAS(String profilDate,  String meterNo ){
		List result=null;
		
		String query = "select read_time,kwh from meter_data.load_survey where meter_number = '"+meterNo+"'  AND to_char(read_time, 'yyyy-MM-dd') = '"+profilDate+"' ORDER BY read_time ASC";
		System.out.println("Consumption curve ---> " +query);
		result = postgresMdas.createNativeQuery(query).getResultList();
		return result;
	}
	
	@Override
	public List<?>  getd4LoadDataCurveMDAM(String billmonth,  String meterNo ){
		List result=null;
		
		String query ="SELECT SUM (COALESCE(kwh,0)/1000),to_char(read_time,'yyyy-MM-dd') FROM meter_data.load_survey WHERE meter_number = '"+meterNo+"' AND to_char(read_time, 'yyyyMM') = '"+billmonth+"' GROUP BY to_char(read_time,'yyyy-MM-dd')";
		System.out.println("Consumption curve MDM (Graph)---> " +query);
		result = postgresMdas.createNativeQuery(query).getResultList();
		return result;
	}
	@Override
	public List<?> getALLSubdivByCIR(String circle) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("Master.getDivisionBycircle").setParameter("circle",circle).getResultList();
	}
	
	@Override
	public String getindustryType(String accno)
	{
		return  (String) getCustomEntityManager("postgresMdas").createNamedQuery("Master.Findindustrytype").setParameter("accno",accno).getSingleResult();
	}
	@Override
	public double getsanLoad(String accno) {
		return  (double) getCustomEntityManager("postgresMdas").createNamedQuery("Master.Findsanload").setParameter("accno",accno).getSingleResult();
	}
	@Override
	public List<Master> getMeterBasedOn(String circle, String division,String subdiv) 
	{
		return  getCustomEntityManager("postgresMdas").createNamedQuery("Master.getAllDataMeters").setParameter("circle",circle).setParameter("division",division).setParameter("sdoname",subdiv).getResultList();
	}
	@Override
	public List<?> geteventdescdata() {
		List result=null;
		//System.out.println("events----------");
		String sql="SELECT event_code,event FROM meter_data.event_master WHERE event_type='C'";
		System.out.println("event code-"+sql);
		result=postgresMdas.createNativeQuery(sql).getResultList();
		return result;
	}
	
	@Override
	public List<?> getvalfaildata() {
		List value=null;
		//System.out.println("validation failure---------");
		String qry="SELECT v_rule_id,v_rule_name FROM meter_data.validation_rule_mst where  v_rule_id NOT IN('VEE05','VEE09','VEE07','VEE10','VEE11','VEE12','VEE13','VEE14','VEE15') order by v_rule_id";
		value=postgresMdas.createNativeQuery(qry).getResultList();
		return value;
	}
	@Override
	public List<MasterService> getDistinctCircle() {
		List<MasterService> list =null;
		try {
			String sql="SELECT DISTINCT circle from meter_data.master_main";
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<?> getcirclebyzone(String zone, ModelMap model) {
		
		
		List<?> list=null;
		try {
			if("ALL".equalsIgnoreCase(zone)) {
				String sql="SELECT DISTINCT circle from meter_data.amilocation order by circle";
				//System.err.println(sql);
				list=postgresMdas.createNativeQuery(sql).getResultList();
				}else {
					String sql="SELECT DISTINCT circle from meter_data.amilocation WHERE zone LIKE '"+zone+"' order by circle";
					//System.err.println(sql);
					list=postgresMdas.createNativeQuery(sql).getResultList();
				}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<?> getDivisionByCircle(String circle, ModelMap model) {
		
		
		List<?> list=null;
		try {
			if("ALL".equalsIgnoreCase(circle)) {
				String sql="SELECT DISTINCT division from meter_data.amilocation order by division";
				//System.err.println(sql);
				list=postgresMdas.createNativeQuery(sql).getResultList();
			}else {
				String sql="SELECT DISTINCT division from meter_data.amilocation WHERE circle LIKE '"+circle+"' order by division";
				//System.err.println(sql);
				list=postgresMdas.createNativeQuery(sql).getResultList();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<?> getSubdivByDivisionByCircle(String circle, String division, ModelMap model) {
		List<?> list=null;
		try {
		String sql="SELECT DISTINCT subdivision from meter_data.amilocation WHERE circle LIKE '"+circle+"' and division like '"+division+"'";
		//System.err.println(sql);
		list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<?> getDiscomList() {
		List result=null;
		String sql="SELECT DISTINCT discom FROM meter_data.amilocation";
		//System.out.println("qry is----"+sql);
		result=postgresMdas.createNativeQuery(sql).getResultList();
		return result;
	}
	@Override
	public List<MasterService> getDistinctZone() {
		List res=null;
		String sql="SELECT DISTINCT ZONE from meter_data.master_main";
		//System.out.println("qry is----"+sql);
		res=postgresMdas.createNativeQuery(sql).getResultList();
		return res;
	}
	@Override
	public List<?> getCircleByZone(String zone, ModelMap model) {
		List res=null;
		String sql="SELECT DISTINCT circle from meter_data.master_main where zone LIKE '"+zone+"'";
		//System.out.println("qry is----"+sql);
		res=postgresMdas.createNativeQuery(sql).getResultList();
		return res;
	}
	@Override
	public List<?> getUserRoleData() {
		List li=null;
		String sql="SELECT DISTINCT usertype from meter_data.users";
		//String sql="SELECT DISTINCT discom FROM meter_data.amilocation";
		//System.out.println("qry is----"+sql);
		li=postgresMdas.createNativeQuery(sql).getResultList();
		return li;
	}
	@Override
	public List<?> getUserRoledata(String userrole) {
		List user=null;
		String sql="select id, office,username,designation,mobile_no,email_id FROM meter_data.users WHERE designation like '"+userrole+"'" ;
		//System.out.println("qry user role is----"+sql);
		user=postgresMdas.createNativeQuery(sql).getResultList();
		return user;
	}
	
	 @Override public List<?> getAccnoID(Object i) { 
		 List<?> userDetails=null; 
		 String qry="SELECT accno,sdocode,phoneno,email FROM meter_data.consumermaster WHERE accno='"+i+"'";
	  userDetails=postgresMdas.createNativeQuery(qry).getResultList(); 
	  return userDetails; 
	  }
	 @Override public List<?> getDiscomDetails(int i) { 
		 List<?> userDetails=null; 
			String qry="select id, office,mobile_no,email_id FROM meter_data.users WHERE id='"+i+"'" ;
	  //System.out.println("accno id is----"+qry);
	  userDetails=postgresMdas.createNativeQuery(qry).getResultList(); 
	  return userDetails; 
	  }

	 @Override 
	 public List<?> getDtDetails(String dtId) { 
		 List<?> userDetails=null; 
		 String qry="select dttpid,officeid,tp_town_code from meter_data.dtdetails  where dttpid='"+dtId+"'";
	  userDetails=postgresMdas.createNativeQuery(qry).getResultList(); 
	  return userDetails; 
	 }

	 @Override 
	 public List<?> getFdrDetails(String fdrId) { 
		 List<?> userDetails=null; 
		 String qry="select tp_fdr_id,officeid,tp_town_code from meter_data.feederdetails  where tp_fdr_id='"+fdrId+"'";
	  userDetails=postgresMdas.createNativeQuery(qry).getResultList(); 
	  return userDetails; 
	
	
	 }
	 @Override
		public List<?> getUserRoledataForAlarm(String Zone,String circle,String twn,String userrole) {
			System.out.println("in serviceimpl-------");
			List user=null;
			String zne="",cir="",div="",sub="",urole="",dicomm="";
			if(Zone=="%"){
				zne="ALL";
			}else{
				zne=Zone;
			}
			if(circle=="%"){
				cir="ALL";
			}else{
				cir=circle;
			}
			/*if(division=="%"){
				div="ALL";
			}else{
				div=division;
			}
			if(sdoCode=="%"){
				sub="ALL";
			}else{
				sub=sdoCode;
			}*/
			if(userrole=="%"){
				urole="ALL";
			}else{
				urole=userrole;
			}
			/*if(discom=="%"){
				dicomm="ALL";
			}else{
				dicomm=discom;
			}*/
			/*String sql="select id, office,username,designation,mobile_no,email_id FROM meter_data.users WHERE designation='"+userrole+"'" ;*/
			String sql="select distinct u.id,u.office,u.username,u.designation,u.mobile_no,u.email_id FROM meter_data.users u,meter_data.amilocation m "
					+ "WHERE m.DISCOM_CODE=(cast(u.office as INT))   and zone like '"+Zone+"' and circle like '"+circle+"'  and u.usertype like '"+userrole+"' and u.discom is not null  and u.office !=''";
			System.out.println("qry user role is----"+sql);
			user=postgresMdas.createNativeQuery(sql).getResultList();
			return user;
		}
	@Override
	public List<?> getUserRoledata(String dcom, String zonee, String cir, String div, String sdiv, String role) {
		// TODO Auto-generated method stub
		return null;
	}
}
