package com.bcits.serviceImpl;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MobileBillingDataEntity;
import com.bcits.service.MeterMasterService;
import com.bcits.service.SBMService;
import com.bcits.utility.MDMLogger;

/*Author: Ved Prakash Mishra*/
@Repository
public class SBMServiceImpl extends GenericServiceImpl<MobileBillingDataEntity> implements SBMService 
{

	
	@Autowired
	private MeterMasterService metermasterService;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void getSbmData(ModelMap model,Integer readingMonth) 
	{
		
	
		List<Object[]> list=null;
		list=postgresMdas.createNamedQuery("MeterMaster.getdata1").setParameter("readingMonth", readingMonth).getResultList();
		if(list.size()>0)
		{
		for (Object[] object : list)  
		{
			MobileBillingDataEntity mobileBillingDataEntity=new MobileBillingDataEntity();
			
			        
					 if(object[0]!=null)
					     mobileBillingDataEntity.setMetrno(object[0].toString());
					 if(object[1]!=null)
					     mobileBillingDataEntity.setConsumerid(object[1].toString());
					 if(object[2]!=null)
					     mobileBillingDataEntity.setConsumername(object[2].toString());
					 if(object[3]!=null)
						 mobileBillingDataEntity.setAddress(object[3].toString());
					 if(object[4]!=null)
					     mobileBillingDataEntity.setBillmonth(Integer.parseInt(object[4].toString()));
					 if(object[5]!=null)
					     mobileBillingDataEntity.setPkwh(Double.parseDouble(object[5].toString()));
					 if(object[6]!=null)
					     mobileBillingDataEntity.setPkvah(Double.parseDouble(object[6].toString()));
					 if(object[7]!=null)
					     mobileBillingDataEntity.setPkva(Double.parseDouble(object[7].toString()));
					 if(object[8]!=null)
					     mobileBillingDataEntity.setOldseal(object[8].toString());
					 if(object[9]!=null)
					     mobileBillingDataEntity.setMrname(object[9].toString());
					 if(object[10]!=null)
						 mobileBillingDataEntity.setTadesc(object[10].toString());
					 if(object[11]!=null)
						 mobileBillingDataEntity.setPhoneno(object[11].toString());
					 if(object[12]!=null)
						 mobileBillingDataEntity.setMtrmake(object[12].toString());
					 
					 if(object[13] != null)
						 mobileBillingDataEntity.setIndustryType(object[13]+"");
					
					 try {
						 mobileBillingDataEntity.setUploadstatus(0);
						 save(mobileBillingDataEntity);
						 model.put("msg","SBM Data Added succssfully");
					     } 
					 catch (Exception e)
					     {
						e.printStackTrace();
					    }
					 }
		}
		else
		{
			model.put("msg","No Record Found for this month");
		}
		
	    	
	}

	@Override
	public int getMaxRdgMonthYear(HttpServletRequest request) 
	{
		int monthYear = 0;
		try {
			monthYear = (int) postgresMdas.createNamedQuery(
					"MeterMaster.getMaxMonthYear").getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return monthYear;
		
	}
	
	public List<MobileBillingDataEntity> getMrnameData() 
	{
		List list=null;
		list=postgresMdas.createNamedQuery("MobileBillingDataEntity.GetMrname").getResultList();
		return list;
	}


	
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MobileBillingDataEntity> getMobileBillingData(String mrname, HttpServletRequest request) 
	{
		
		
		
		/*Date d = new Date();
		int month =d.getMonth()+1;
		int year = d.getYear()+1900;
		String billmonth =""+year+""+month;*/
		
		
		
		int billmonthint = metermasterService.getMaxRdgMonthYear(request);
		
		/*int billmonthint = Integer.parseInt(billmonth);*/
		
		int uploadstatus = 0;
		
		System.out.println("----------------- :MR NAME  "+mrname+ "       BILL MONTH "+billmonthint );
	    List<MobileBillingDataEntity> data = postgresMdas.createNamedQuery("MobileBillingDataEntity.getConsumerDataForMobile").setParameter("mrname",mrname).setParameter("billmonth",billmonthint).setParameter("uploadstatus",uploadstatus).getResultList();
		

	    System.out.println("SSSSSSSSSSSSSSSSSS "+data.size());
	 
	 
	 if(data.size()>0){
		 
		 
		 System.out.println(">>>>>>>>>>>>>>"+data);
		 return data;
		 
	 }
	 else{
		 
		 
		 
		 return null;
		 
	 }
	 
	 
		
	}


	@Override
	public void countBillmonth(ModelMap model,Integer billmonth) 
	{
		MDMLogger.logger.info("======>"+billmonth);
			long l=(long)postgresMdas.createNamedQuery("MobileBillingDataEntity.GetCurrBillMonth").setParameter("billmonth", billmonth).getSingleResult();
		    if(l>0)
		    	model.put("msg","This month's record is already added ");
		    else
		    	getSbmData(model,billmonth);
		
		
	}


}

