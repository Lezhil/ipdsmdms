package com.bcits.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Seal;

public interface NewSealMangrService extends GenericService<Seal>{

	public Long getTotalNoSeal(int FromMonth,String mrname);
	
	
	 public int sealDataTransferForNxtMnth(int FromMonth,int ToMonth,String mrname); 
	
	//New Seal Manager
		public void searchSealByAccNo(HttpServletRequest request,MeterMaster meterMaster,ModelMap model);
		
		public Object[] searchSealByAcc(String accno,String rdngmnth,ModelMap model);
		public Object[] searchSealByMtr(String mtrno,String rdngmnth);
		public String getSealData(String newsealNum,ModelMap model,String accNo,String meterNo,String mrname1,int readingMonth);


		public BigDecimal sealCountForSealIssue(String sealFrom, String sealTo,int sealLen);


		public BigDecimal getCardslNoForSealIssue();


		public int updateSealIssue(String mrName, String date, String rdMonth, Long cardslNo1, String sealFrom, String sealTo, int sealLen,String userName);

}

