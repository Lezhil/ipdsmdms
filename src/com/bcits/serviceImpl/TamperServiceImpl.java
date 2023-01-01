package com.bcits.serviceImpl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.TamperEntity;
import com.bcits.service.TamperService;

@Repository
public class TamperServiceImpl extends GenericServiceImpl<TamperEntity> implements TamperService {

	@Override
	public List<?> getTamperType() {
		List<?> tampType=null;
		try {
			tampType=postgresMdas.createNamedQuery("TamperEntity.getTamperType").getResultList();
		} catch (Exception e) {
		}
		return tampType;
	}
	
	
	
	@Override
	public List<?> getTamperDATA(int rdngmonth) {
		List<?> tampDATA=null;
		try {
			String qry="";
			qry="select circle,count(*) as noi from  mdm_test.tmp where rdngmonth ='"+rdngmonth+"' group by circle order by circle";
			tampDATA=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tampDATA;
	}



	@Override
	public List<?> getTmpViewData(String circle, int rdngmonth) {
		List<?> tmp=null;
		try {
			String qry="SELECT rdngmonth,circle,sdoname,tadesc,accno,meterno,name,tampertype,occurred_date,restored_date,mnp from mdm_test.TMP "
					   + " where rdngmonth='"+rdngmonth+"' and circle='"+circle+"'  order by accno";
			System.out.println(qry);
			tmp=postgresMdas.createNativeQuery(qry).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}



	@Override
	public Long checkMeterNo(String rdngMonth, String meterNo) {
		 long cnt= 0;
		try {
			cnt= (Long) postgresMdas.createNamedQuery("TamperEntity.checkMeterNo").setParameter("rdngMonth", rdngMonth).setParameter("meterNo",meterNo).getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
