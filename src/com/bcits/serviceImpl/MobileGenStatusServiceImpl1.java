package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.MobileGenStatusEntity1;
import com.bcits.service.MobileGenStatusService1;

@Repository
public class MobileGenStatusServiceImpl1 extends GenericServiceImpl<MobileGenStatusEntity1> implements MobileGenStatusService1{

	@Override
	public List<?> getALLMobileStatus() {
		
		List result=null;
		String qry="SELECT BILLMONTH,METERNO,STATUS,to_char(CREATEDATE,'YYYY-MM-DD hh:mi:ss') AS CREATEDATE FROM BSMARTMDM_TEST.MOBILE_GEN_MTR_STATUS  ORDER BY BILLMONTH";
		result=postgresMdas.createNativeQuery(qry).getResultList();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
