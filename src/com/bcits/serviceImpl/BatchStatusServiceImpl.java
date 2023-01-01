package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.BatchStatusEntity;
import com.bcits.service.BatchStatusService;


@Repository
public class BatchStatusServiceImpl extends GenericServiceImpl<BatchStatusEntity> implements BatchStatusService{

	@Override
	public List<String> getAllDates() {
		try {
		
		return postgresMdas.createNativeQuery("SELECT DISTINCT(TO_CHAR(RDATESTUMP,'dd-MM-yyyy')) FROM BATCHSTATUS ORDER BY TO_CHAR(RDATESTUMP,'dd-MM-yyyy')").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		}

	@Override
	public List<?> getAllSummaryData(String date1) {
		try {
			return postgresMdas.createNamedQuery("BatchStatusEntity.getAllDetails").setParameter("readingdate", date1).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	
	//Added by vijayalaxmi
	@Override
	public List<?> getCircle() 
	{
		return postgresMdas.createNamedQuery("BatchStatusEntity.getCircle").getResultList();
	}

	@Override
	public List<?> getSdocodeOnCircle(String circle) {
		return postgresMdas.createNamedQuery("BatchStatusEntity.getSdocodeOnCircle").setParameter("circle", circle).getResultList();
	}

	@Override
	public List<?> getReadingDateOnSdocodeAndcircle(String circle,String sdocode) {
		if(!sdocode.equalsIgnoreCase("All"))
		{
			return postgresMdas.createNamedQuery("BatchStatusEntity.getReadingDateOnSdocodeAndcircle").setParameter("circle", circle).setParameter("sdocode", sdocode).getResultList();

		}
		else
		{
			return postgresMdas.createNamedQuery("BatchStatusEntity.getReadingDateOnSdocodeAndcircleSdoAll").setParameter("circle", circle).getResultList();

		}
	}

	@Override
	public List<?> getAllbatchStatusRecords(String circle,String sdocode) {
		
		List<?> batchList=new ArrayList<>();
		String qry="";
		if(sdocode.equalsIgnoreCase("ALL"))
		{
			qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS') as readingDate,c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.circle='"+circle+"' and c.mm_table=1 ";
		}
		else
		{
			 qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS') as readingDate,c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.sdocode='"+sdocode+"' and c.circle='"+circle+"' and c.mm_table=1 ";
		}
		try
		{
			batchList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return batchList;
	}
	
	public List<?> getmmNotUpdatedStatusRecords(String circle,String sdocode) {
		List<?> mmnotUpdatedList=null;
		String qry="";
		if(sdocode.equalsIgnoreCase("ALL"))
		{
			qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.circle='"+circle+"' and c.mm_table=0 ";
		}
		else
		{
		 qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.sdocode='"+sdocode+"' and c.circle='"+circle+"' and c.mm_table=0 ";
		}
		try
		{
			mmnotUpdatedList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mmnotUpdatedList;
	}

	@Override
	public List<?> getxmlUpdatedStatusRecords(String circle, String sdocode) {
		List<?> xmlUpdatedList=null;
		String qry="";
		if(sdocode.equalsIgnoreCase("ALL"))
		{
		 qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.circle='"+circle+"' and c.xmlimport=1 ";
		}
		else
		{
			qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.sdocode='"+sdocode+"' and c.circle='"+circle+"' and c.xmlimport=1 ";
		}
		try
		{
			xmlUpdatedList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return xmlUpdatedList;
	}

	@Override
	public List<?> getxmlNotUpdatedStatusRecords(String circle, String sdocode) {
		List<?> xmlNotUpdatedList=null;
		String qry="";
		if(sdocode.equalsIgnoreCase("ALL"))
		{
			qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where  c.circle='"+circle+"' and c.xmlimport=0 ";
		}
		else{
			qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.sdocode='"+sdocode+"' and c.circle='"+circle+"' and c.xmlimport=0 ";
		}
		
		 
		
		try
		{
			xmlNotUpdatedList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return xmlNotUpdatedList;
	}

	@Override
	public List<?> getxmlAndMMUpdatedStatusRecords(String circle, String sdocode) {
		List<?> xmlNotUpdatedList=null;
		
		String qry="";
		if(sdocode.equalsIgnoreCase("ALL"))
		{
			 qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where  c.circle='"+circle+"' and c.xmlimport=1 and c.mm_table=1 ";
		}
		else
		{
			 qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.sdocode='"+sdocode+"' and c.circle='"+circle+"' and c.xmlimport=1 and c.mm_table=1 ";
		}
		
		
		
		try
		{
			xmlNotUpdatedList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return xmlNotUpdatedList;
	}

	@Override
	public List<?> getAllRecordsWithNoCircles() {
		List<?> allList=null;
		String qry="select c.circle,c.sdocode,c.sdoname,c.meterno,TO_CHAR(c.rdatestump,'YYYY-MM-DD HH:MI:SS'),c.file_name,c.parsestatus,c.amr from BATCHSTATUS c where c.circle is null ";
		
		try
		{
			allList= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return allList;
	}

	@Override
	public List<?> getcirSubDiv(String meterno, String rdngmnth) {
		System.out.println("meterno-->"+meterno);
		System.out.println("rdngmnth-->"+rdngmnth);
		String qry="";
		List<?> result=null;
		try {
			qry="SELECT circle,sdocode,sdoname FROM MM WHERE RDNGMONTH='"+rdngmnth+"' AND metrno='"+meterno+"'";
			System.out.println(qry);
			 result=postgresMdas.createNativeQuery(qry).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int checkData(String billmonth, String meterno) {
		System.out.println("billmonth-->"+billmonth+"--metrno-"+meterno);
		int count=0;
		try {
			String qry="SELECT count(*) FROM BATCHSTATUS WHERE METERNO='"+meterno+"' AND RDNGMONTH='"+billmonth+"' AND XMLIMPORT=0"; 
			count=(int) postgresMdas.createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


}
