package com.bcits.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.XmlImport;
import com.bcits.service.CdfBatchService;

@Repository
public class CdfBatchServiceImpl extends GenericServiceImpl<XmlImport> implements CdfBatchService {

	@Override
	public List<XmlImport> findXmlDataForMonth(String rdngmonth, String meterno) {
	System.out.println("rdngmonth==>"+rdngmonth+"---meterno"+meterno);
		String qry1="SELECT KWH,KVH,KVA,PF from XMLIMPORT WHERE month='"+rdngmonth+"' AND METERNO='"+meterno+"'";
		List result=postgresMdas.createNativeQuery(qry1).getResultList();
		System.out.println("result size==>"+result.size());
		return result;
	}

	@Override
	public List<MeterMaster> findMeterMasterDataForCURRMonth(String rdngmonth,
			String meterno) {
		System.out.println("findMeterMasterDataForCURRMonth-->"+rdngmonth+"--"+meterno
				);
		String qry="select * FROM METERMASTER WHERE METRNO='"+meterno+"' AND RDNGMONTH='"+rdngmonth+"' ";
		System.out.println("QRY-->__"+qry);
		List result=postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println("result size==>"+result.size());
		return result;
	}

	@Override
	public int updateMMkwh(String rdngmonth, String meterno, String kwh, String kvh,
			String kva, String pf) {
		System.out.println("inside update method");
		String qry2="UPDATE METERMASTER SET XCURRDNGKWH='"+kwh+"',XCURRRDNGKVAH='"+kvh+"',XCURRDNGKVA='"+kva+"',XPF='"+pf+"',RTC=1 WHERE RDNGMONTH='"+rdngmonth+"' AND  METRNO='"+meterno+"'";
		System.out.println("-update qry-->"+qry2);
		int count=postgresMdas.createNativeQuery(qry2).executeUpdate();
		return count;
	}

	@Override
	public int updateMMExportReading(String rdngmonth, String meterno,
			String kwh, String kvh, String kva, String pf, String ekwh,
			String ekvah, String ekva, String epf,String todayDate) {
		String qry3="UPDATE METERMASTER SET XCURRDNGKWH='"+kwh+"',XCURRRDNGKVAH='"+kvh+"',XCURRDNGKVA='"+kva+"',XPF='"+pf+"',RTC=1,KWHE='"+ekwh+"',KVHE='"+ekvah+"',KVAE='"+ekva+"',PFE='"+epf+"', XMLDATE=TO_DATE('"+todayDate+"', 'yyyy-mm-dd') WHERE RDNGMONTH='"+rdngmonth+"' AND  METRNO='"+meterno+"'";
		System.out.println("update Export reading qry-->"+qry3);
		int count=postgresMdas.createNativeQuery(qry3).executeUpdate();
		return count;
	}

	@Override
	public long checkMMUpdate(String rdngmonth, String meterno) {
		BigDecimal count;
		int count1=0;
		try {
			String qry="SELECT count(*) FROM BATCHSTATUS WHERE RDNGMONTH='"+rdngmonth+"' AND METERNO='"+meterno+"' AND XMLIMPORT=1 AND MM_TABLE=0";
			count= (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			count1=count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count1;
	}

   @Override
	public int deleteMtrInXmlImport(String meterno,String month) {
		int delCount=0;
		try {
			
			String qry="DELETE FROM XMLIMPORT WHERE METERNO='"+meterno+"' ";
			System.out.println(qry);
			delCount=(int) postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return delCount;
	}

   @Override
	public long checkXMLMMUpdate(String rdngmonth, String meterno) {
		BigDecimal count;
		int count1=0;
		try {
			String qry="SELECT count(*) FROM BATCHSTATUS WHERE RDNGMONTH='"+rdngmonth+"' AND METERNO='"+meterno+"' AND XMLIMPORT=1 AND MM_TABLE=1";
			count= (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			count1=count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count1;
	}

@Override
public int deleteMtrInBatchStatus(String meterno, String yyyyMM1) {
	System.out.println("calling delete meterno in batchstatus ");
	int delCount=0;
	try {
		
		String qry="DELETE FROM BATCHSTATUS WHERE METERNO='"+meterno+"' AND RDNGMONTH='"+yyyyMM1+"'";
		System.out.println(qry);
		delCount=(int) postgresMdas.createNativeQuery(qry).executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return delCount;
}

public long checkMeterNoRdngMnth(String rdngmonth, String meterno) {
	BigDecimal count;
	int count1=0;
	try {
		String qry="SELECT count(*) FROM BATCHSTATUS WHERE RDNGMONTH='"+rdngmonth+"' AND METERNO='"+meterno+"'";
		count= (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
		count1=count.intValue();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count1;
}

}
