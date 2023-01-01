package com.bcits.serviceImpl;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Seal;
import com.bcits.service.NewSealMangrService;
import com.bcits.service.sealService;



@Repository
public class NewSealMangrServiceImpl extends GenericServiceImpl<Seal> implements NewSealMangrService{
	
	@Autowired
	sealService sealService;

	@Override
	public Long getTotalNoSeal(int FromMonth, String mrname) {
		System.out.println(FromMonth+"---==---"+mrname);
		return  (Long) postgresMdas.createNamedQuery("Seal.GetTotalNoSeal").setParameter("MRNAME", mrname).setParameter("FromMonth", FromMonth).getSingleResult();
	}

	@Override
	public int sealDataTransferForNxtMnth(int FromMonth, int ToMonth,
			String mrname) {
		int count=0;
		try {		
		count=	 postgresMdas.createNamedQuery("Seal.UpdateSealDataForNxtMnth").setParameter("ToMonth",ToMonth).setParameter("FromMonth", FromMonth).setParameter("mrname", mrname).executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return count;
	}
	
	
	//searchSealByAccNo
	
		@Override
		public void searchSealByAccNo(HttpServletRequest request,
				MeterMaster meterMaster, ModelMap model) {
			System.out.println("--inside searchSEALByMtrNo-----");
			List<MeterMaster> list = null;
			String newSeal = "";
			String sql = "";
			try {
				String accno=meterMaster.getAccno().trim();
				Integer rdngmnth=meterMaster.getRdngmonth();
				System.out.println("Accno--" +accno+"rdgMonth--" +rdngmnth);
				list = postgresMdas.createNamedQuery("MeterMaster.searchByAccNo").setParameter("accNo", accno).setParameter("rdgMonth",rdngmnth).getResultList();
				//mrname = (String)postgresMdas.createNamedQuery("Master.FindMrNameByMtrNo").setParameter("accno", meterMaster.getAccno()).getSingleResult();
				
				
				
				System.out.println(list.size());
				System.out.println(list.toString());
		
			
			if (list.size() == 0) 
			{
				model.put("result", "Data not found for entered METER number");
				System.out.println("inside accnoService Impl list==0");
				int rdgMonth = meterMaster.getRdngmonth();
				meterMaster = new MeterMaster();
				meterMaster.setRdngmonth(rdgMonth);
				
			}
			
			if (list.size() > 0) {
			
				System.out.println("insiddddd seal"+list.get(0).getNewseal());
				model.put("Newseal", list.get(0));
				
			}
			
		} catch (Exception e) 
		{

			e.printStackTrace();
		}
			
		}
	//ByAccno
		@Override
		public Object[] searchSealByAcc(String accno, String rdngmnth,ModelMap model) {
			System.out.println("accno-->"+accno+"=--rdngmnth=="+rdngmnth);
			Object[] newseal=null;
			try {
			String qry="SELECT NEWSEAL,METRNO,MRNAME FROM METERMASTER WHERE ACCNO LIKE '"+accno+"' AND RDNGMONTH='"+rdngmnth+"' AND NEWSEAL IS NOT NULL AND METRNO IS NOT NULL";
			System.out.println(qry);
			newseal =  (Object[]) postgresMdas.createNativeQuery(qry).getSingleResult();
			System.out.println("newseal--"+newseal);
			System.out.println(newseal[2]);
			model.addAttribute("mrname",newseal[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newseal;
		}

		@Override
		public Object[] searchSealByMtr(String mtrno, String rdngmnth) {
			System.out.println("accno-->"+mtrno+"=--rdngmnth=="+rdngmnth);
			Object[] newseal=null;
			try {
			String qry="SELECT NEWSEAL,ACCNO,MRNAME FROM METERMASTER WHERE METRNO LIKE '"+mtrno+"' AND RDNGMONTH='"+rdngmnth+"'";
			System.out.println(qry);
			newseal = (Object[]) postgresMdas.createNativeQuery(qry).getSingleResult();
			System.out.println("newseal--"+newseal);
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return newseal;
		}

		@SuppressWarnings({ "unchecked", "null" })
		@Override
		public String getSealData(String newsealNum,ModelMap model,String accNo,String meterNo,String mrname1,int readingMonth) 
		{
		    System.out.println("newsealNum-----"+newsealNum);
			System.out.println("accNo------"+accNo);
			System.out.println("meterNo------"+meterNo);
			System.out.println("mrname1-------"+mrname1);
			System.out.println("readingMonth----"+readingMonth);
			
			String updated="";
			
			List<Seal> result=null;
			
			
			Seal rs=new Seal();
			try {
				
				rs=(Seal) postgresMdas.createNamedQuery("Seal.GetSealBySealNo").setParameter("sealNo",newsealNum).getSingleResult();
				

				Seal seal=sealService.find(rs.getId());
		     	Date idate1=rs.getiDate();
		     	
		     	System.out.println("idate-------"+idate1);
		     	String idate="";
				String rmrName=rs.getRmrname();
				String mrname=rs.getMrname();
				System.out.println("R-MR Name from query-----"+rmrName);
				System.out.println("MR Name from query-----"+mrname);
				
				
				seal.setAccNo(accNo);
				seal.setMeterno(meterNo);
				if(idate1==null)
				{
					 Date date = Calendar.getInstance().getTime();
					 DateFormat formatter = new SimpleDateFormat("MM-yyyy");
					 DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
					 String today = formatter.format(date);
				
					 idate="01-"+today;
					
					seal.setiDate(formatter1.parse(idate));
					seal.setBillmonth(readingMonth);
					if(mrname==null)
					{
					if(mrname1=="")
					{
						if(rmrName==null)
						{
							
						}
						else
						{
							seal.setMrname(rmrName);
						}
					}
					else
					{
						seal.setMrname(mrname1);
					}
					
				}
					seal.setRemark("USED");
					
					updated="sealUpdated";
				}
				
				 sealService.update(seal);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			return updated;
		}

		
//Added By Vijayalaxmi
		@Override
		public BigDecimal sealCountForSealIssue(String sealFrom, String sealTo,int sealLen) 
		{
			String qry="SELECT COUNT (*) AS nos FROM seal WHERE sealno >= '"+sealFrom+"' AND sealno <= '"+sealTo+"' AND idate IS NULL AND LENGTH (sealno) >= '"+sealLen+"' ";
			return (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
		}

		@Override
		public BigDecimal getCardslNoForSealIssue() {
			String qry="select max(CARDSLNO) as crdslno from seal";
			return (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
		}

		@Override
		public int updateSealIssue(String mrName, String date, String rdMonth, Long cardslNo1, String sealFrom, String sealTo, int sealLen,String userName) {
			int count=0;
			try {		
				
				int startSealVal=Integer.parseInt(sealFrom.replaceAll("[^0-9]", ""));
				int endSealVal=Integer.parseInt(sealTo.replaceAll("[^0-9]", ""));
				System.out.println("startSealVal----"+startSealVal+"endSealVal------"+endSealVal);
				
				
				String	sealNum1=sealFrom.substring(0);	
				String sealNum2=sealTo.substring(1,sealTo.length());	
					System.out.println("sealNum1----"+sealNum1+"sealNum2------"+sealNum2);
				String qry="update  SEAL  set MRNAME='"+mrName+"' , IDATE=TO_DATE('"+date+"','yyyy-mm-dd'),BILLMONTH='"+rdMonth+"', ISSUEDBY='"+userName+"',CARDSLNO='"+cardslNo1+"'  where SEALNO BETWEEN '"+sealFrom+"' and '"+sealTo+"' and IDATE is null and length(SEALNO)>='"+sealLen+"'";
				System.out.println("Update qry===="+qry);
				count=postgresMdas.createNativeQuery(qry).executeUpdate();
				
				/*count=	 entityManager.createNamedQuery("Seal.UpdateSealIssueData").setParameter("mrname",mrName).setParameter("iDate", date).setParameter("billmonth", rdMonth).setParameter("cardSealNo", cardslNo1).setParameter("sealFrom", sealFrom).setParameter("sealTo", sealTo).setParameter("sealLen", sealLen).setParameter("issuedBy", userName).executeUpdate();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
			return count;
		}

	
}
