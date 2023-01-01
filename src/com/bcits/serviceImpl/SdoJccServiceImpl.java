package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.SdoJcc;
import com.bcits.service.SdoJccService;

@Repository
public class SdoJccServiceImpl extends GenericServiceImpl<SdoJcc> implements SdoJccService
{
	
	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.SdoJccService#findAll()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public  List<SdoJcc> findAll()
	{
		return postgresMdas.createNamedQuery("SdoJcc.findAll").getResultList();
	}

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public  List getLevelDetails(String sdoCode,String level,String readingMonth)
	{
		List list = null;
		if(level.equalsIgnoreCase("1"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.sdocode IN ("+sdoCode+") AND m.consumerstatus LIKE 'R' AND substr(m.accno,5,1) not like '9' AND mm.readingdate is null ORDER BY m.sdocode";
			System.out.println("1=="+queryString);
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		
		else if (level.equalsIgnoreCase("2"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.sdocode IN ("+sdoCode+") AND m.consumerstatus LIKE 'R' AND  mm.rtc = '1' ORDER BY m.accno,m.sdocode";
			System.out.println("2=="+queryString);
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		
		else if (level.equalsIgnoreCase("3"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.sdocode IN ("+sdoCode+") AND m.consumerstatus LIKE 'R' AND mm.rtc= '0' ORDER BY m.accno ORDER BY m.sdocode";
			System.out.println("3=="+queryString);
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		else if (level.equalsIgnoreCase("4"))
		{
			final String queryString = "select b.rdngmonth,a.tadesc,a.sdocode,a.accno,b.metrno,b.mtrmake,b.mtrtype,a.contractdemand,b.mf,a.kworhp, "
										+"a.sanload,a.name,a.address1,b.CURRDNGKWH,b.CURRRDNGKVAH,b.CURRDNGKVA,b.PF,b.READINGREMARK,b.remark,b.XMLDATE, "
										+"b.XCURRDNGKWH,b.XCURRRDNGKVAH,b.XCURRDNGKVA ,b.XPF,b.readingdate,b.RTC,b.mrname,a.mnp,a.phoneno from master "
										+"a,metermaster b where a.accno=b.accno and b.rdngmonth='"+readingMonth+"' and a.consumerstatus like 'R' and "
										+"a.sdocode like "+sdoCode+" ";
			System.out.println("4=="+queryString);
			Query query = postgresMdas.createNativeQuery(queryString);
			 list=query.getResultList();
		}
		else if (level.equalsIgnoreCase("5"))
		{
			final String queryString = "select accno,metrno,rtc,readingremark,remark,mrname from metermaster where rdngmonth="+readingMonth+" "
										+ "and mcst like 'R' and (readingremark like '%CHANGE%'  OR readingremark like '%MIS%'  OR "
										+ "remark like '%CHANGE%' OR remark like '%change%'  OR remark like '%mc%'  OR remark like '%meter%' " 
										+ "OR remark like '%MC%') and rtc='0' and metrno<>remark AND sdocode like "+sdoCode+" ORDER BY accno"; 
			System.out.println("5=="+queryString);
			Query query = postgresMdas.createNativeQuery(queryString);
			 list=query.getResultList();
		}
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public  List getMRLevelDetails(String sdoCode,String level,String readingMonth,String mrName)
	{
		List list = null;
		try{
		if(level.equalsIgnoreCase("1"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From Master m,MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.subdiv LIKE '"+sdoCode+"' AND UPPER(m.mrname) LIKE UPPER('"+mrName+"') AND m.consumerstatus LIKE 'R' AND mm.readingdate is null ORDER BY m.subdiv";
			System.out.println("--- : "+queryString);
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		
		else if (level.equalsIgnoreCase("2"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From Master m,MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.subdiv LIKE '"+sdoCode+"' AND UPPER(m.mrname) LIKE UPPER('"+mrName+"') AND m.consumerstatus LIKE 'R' AND mm.rtc = '1' ORDER BY m.accno, m.subdiv";
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		
		else if (level.equalsIgnoreCase("3"))
		{
			final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From Master m,MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.subdiv LIKE '"+sdoCode+"' AND UPPER(m.mrname) LIKE UPPER('"+mrName+"') AND m.consumerstatus LIKE 'R' AND mm.rtc= '0' ORDER BY m.accno, m.subdiv";
			Query query = postgresMdas.createQuery(queryString);
			 list=query.getResultList();
		}
		else if (level.equalsIgnoreCase("4"))
		{
			final String queryString = "select b.rdngmonth,a.tadesc,a.subdiv,a.accno,b.metrno,b.mtrmake,b.mtrtype,a.contractdemand,b.mf,a.kworhp, "
										+"a.sanload,a.name,a.address1,b.CURRDNGKWH,b.CURRRDNGKVAH,b.CURRDNGKVA,b.PF,b.READINGREMARK,b.remark,b.XMLDATE, "
										+"b.XCURRDNGKWH,b.XCURRRDNGKVAH,b.XCURRDNGKVA ,b.XPF,b.readingdate,b.RTC,b.mrname,a.mnp,a.phoneno from meter_data.master "
										+"a,meter_data.metermaster b where a.accno=b.accno and b.rdngmonth='"+readingMonth+"' and a.consumerstatus like 'R' and "
										+"a.subdiv like '"+sdoCode+"' AND UPPER(a.mrname) LIKE UPPER('"+mrName+"')";
			Query query = postgresMdas.createNativeQuery(queryString);
			 list=query.getResultList();
		}
		else if (level.equalsIgnoreCase("5"))
		{
			final String queryString = "select accno,metrno,rtc,readingremark,remark,mrname from meter_data.metermaster where rdngmonth="+readingMonth+" "
					+ "and mcst like 'R' and (readingremark like '%CHANGE%'  OR readingremark like '%MIS%'  OR "
					+ "remark like '%CHANGE%' OR remark like '%change%'  OR remark like '%mc%'  OR remark like '%meter%' " 
					+ "OR remark like '%MC%') and rtc='0' and metrno<>remark AND subdiv like '"+sdoCode+"' AND UPPER(mrname) LIKE UPPER('"+mrName+"') ORDER BY accno"; 
			
			Query query = postgresMdas.createNativeQuery(queryString);
			 list=query.getResultList();
			 
		}}
		catch(Exception e){e.printStackTrace();}
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getPendingSummaryDetails(String readingMonth,String condition)
	{
		 String queryString = "";
		if(condition.equalsIgnoreCase("normal"))
		{
			  queryString = "SELECT m.subdiv,count(*) From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.consumerstatus LIKE 'R' AND substr(m.accno,5,1) not like '9' AND mm.readingdate is null GROUP BY m.subdiv ORDER BY m.subdiv";
		}
		else if(condition.equalsIgnoreCase("ht"))
		{
			queryString = "SELECT m.subdiv,count(*) From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.consumerstatus LIKE 'R' AND substr(m.accno,5,1) Like '9' AND mm.readingdate is null GROUP BY m.subdiv ORDER BY m.subdiv";
		}
		
		Query query = postgresMdas.createNativeQuery(queryString);
		List list=query.getResultList();
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getNewConnectionDetails(String readingMonth)
	{
		String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From meter_data.Master m,meter_data.MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND mm.prevmeterstatus LIKE 'MI' ORDER BY m.sdocode";
		Query query = postgresMdas.createNativeQuery(queryString);
		List list=query.getResultList();
		return list;
	}
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getHtPendingDetails(String readingMonth,String circle)
	{
		//final String queryString = "SELECT m.accno,mm.metrno,m.name,m.address1,m.mrname From Master m,MeterMaster mm WHERE m.accno = mm.accno AND mm.rdngmonth = '"+readingMonth+"' AND m.consumerstatus LIKE 'R' AND substr(m.accno,5,1) LIKE '9' AND mm.readingdate is null  ORDER BY m.sdocode";
		/*String queryString = "select m.circle,m.tadesc,m.sdoname,m.accno,MM.metrno,m.name,m.address1,m.mrname, readingremark,mm.remark,mm.readingdate,mm.XMLDATE,mm.XCURRDNGKWH\n" +
				" from METERMASTER mm ,MASTER m where m.ACCNO=MM.ACCNO AND mm.rdngmonth = '"+readingMonth+"' AND mm.rdngmonth=201805 and  MM.xmldate is null and substr(m.accno,5,1)='9' and m.CONSUMERSTATUS like 'R'  \n" +
				"and m.circle like '"+circle+"' order by m.mrname,m.accno";*/
		
		String queryString ="select circle,tadesc,subdiv,accno,metrno,name,address1,mmrname, readingremark,remark,readingdate,XMLDATE,XCURRDNGKWH,CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA from meter_data.mm where rdngmonth='"+readingMonth+"'\n" +
								"and xmldate is null and substr(accno,5,1)='9' and cst like 'R' and htmanual is null and circle like '"+circle+"' order by mmrname,accno";
		
		System.out.println(queryString);
		//Query query = entityManager.createQuery(queryString);
		List list=postgresMdas.createNativeQuery(queryString).getResultList();
		
		//List list=query.getResultList();
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCircle() {
		return postgresMdas.createNamedQuery("SdoJcc.getAllCircle").getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllDivisonBasedOnCircle(String circle) {
		List<String> list=null;
		try 
		{
			list=postgresMdas.createNamedQuery("SdoJcc.getAllDivisonBasedOnCircle").setParameter("circle", circle).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<?> getAllSubDivisonBasedOnDivision(String division) {
		List<?> list=postgresMdas.createNamedQuery("SdoJcc.getAllSubDivisonBasedOnDivision").setParameter("division",division).getResultList();
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();
			data.put("subDivision", obj[0]);
			data.put("sdocode", obj[1]);
			result.add(data);
		}
		return result;
	}


	@Override
	public String getsubDivisionName(String sdocode) {
		return (String) postgresMdas.createNamedQuery("SdoJcc.getsubDivisionName").setParameter("sdoCode", sdocode).getSingleResult();
	}


	/*@Override
	public List<String> getAllDistDivision() {
		
		return (String) entityManager.createNamedQuery("SdoJcc.getDistALLDivision").setParameter("sdoCode", sdocode).getSingleResult();
	}*/
	
	
	@Override
	public List<?> getAllDistDivision() {
		List<?> list=postgresMdas.createNamedQuery("SdoJcc.getDistALLDivision").getResultList();
		return list;
	}
	
	@Override
	public List<?> getDistALLSdoCodes() {
		List<?> list=postgresMdas.createNamedQuery("SdoJcc.getDistALLSdoCodes").getResultList();
		return list;
	}


	@Override
	public List<?> getDistALLSdoNames() {
		List<?> list=postgresMdas.createNamedQuery("SdoJcc.getDistALLSdoNames").getResultList();
		return list;
	}


	@Override
	public SdoJcc getAllDetailsForAccno(String accn) {
		System.out.println(accn);
		SdoJcc list=null;
		try {
			
		list=(SdoJcc) postgresMdas.createNamedQuery("SdoJcc.getAllDetailsForAccno").setParameter("sdoCode", accn).getSingleResult();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
	
	@Override
	public List<?> getDistALLMNP() {
		List<?> list=postgresMdas.createNamedQuery("Master.getDistALLMNP").getResultList();
		return list;
	}


	@Override
	public List<?> getDistALLMNPSDOJCC() {
		List<?> list=postgresMdas.createNamedQuery("SdoJcc.getDistMNPSDOJCC").getResultList();
		return list;
	}


	@Override
	public List<?> getALLDivisionByCIR(String circle) {
		//System.out.println("calling Mrname Based on  Circle .. query "+circle);
		return postgresMdas.createNamedQuery("SdoJcc.getDivisionByCIR").setParameter("circle", circle).getResultList();
	}


	@Override
	public List<?> getSubDivisionList(String division) {
		//System.out.println("calling Mrname Based on  division .. query "+division);
		return postgresMdas.createNamedQuery("SdoJcc.getSUBDivisionByCIR").setParameter("division", division).getResultList();
	}

	
    // Added By Vijayalaxmi
	@Override
	public List<?> getHtManualDetails(String rdngMonth, String circle1) 
	{
		List<?> list=null;
		try {
			String qry ="select circle,sdocode,subdiv,accno,metrno,readingremark,"
					+ "(case when readingremark like 'COMMUNICATION FAILED' then CURRDNGKWH end) as kwh,"
					+ "(case when readingremark like 'COMMUNICATION FAILED' then CURRRDNGKVAH end) as kvah,"
					+ "(case when readingremark like 'COMMUNICATION FAILED' then CURRDNGKVA   end) as kva, "
					+ "CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA,name,address1,mrname from meter_data.mm where rdngmonth='"+rdngMonth+"' and circle like '"+circle1+"'"
					+ "and xmldate is null and substr(accno,5,1)='9' and cst like 'R' and htmanual ='1'";
				 System.out.println(qry);
				 list=postgresMdas.createNativeQuery(qry).getResultList();
				 
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		 return list;
	}



	@Override
	public List<?> getSdoNameForAnaysedReport(String circle) {
		//System.out.println("calling circle .. query "+circle);
		List sdonames=null;
		try {
		String qry="select DISTINCT(sdoname) FROM meter_data.MASTER WHERE CIRCLE='"+circle+"' ORDER BY sdoname ASC ";
		sdonames=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdonames;
	}




	
//	Added By Vijayalaxmi

	@Override
	public List<?> getDataFromsdoJcc(String sdocode) 
	{
		List<?> sdoList=null;
		try {
			sdoList=postgresMdas.createNamedQuery("SdoJcc.getDataFromsdoJcc").setParameter("sdoCode", sdocode).getResultList();
			System.out.println("---------sdoList.size based on SDOCODE---------"+sdoList.size());
	} catch (Exception e) {
		e.printStackTrace();
	}
		return sdoList;
	}


	@Override
	public List<?> getAccnoNotAvailableData(String rdngMonth) {
		List<?> accNoData=null;
		try {
			String qry="select circle,subdiv,accno,metrno,name,address1 from meter_data.mm where rdngmonth='"+rdngMonth+"' and   "
				    + "substr(accno,1,12) ~ '[a-zA-Z]' and cst like 'R' order by accno";
			accNoData=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return accNoData;
	}
	
	
	

}
