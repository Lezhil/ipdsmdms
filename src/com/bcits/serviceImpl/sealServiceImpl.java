package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.Seal;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.sealService;
import com.bcits.utility.MDMLogger;
import com.bcits.utility.Resultupdated;

@Repository
public class sealServiceImpl extends GenericServiceImpl<Seal> implements sealService  {

	@Autowired
	private MrnameService mrnameService;



	@Autowired
	private MeterMasterService meterMasterService;


	@Transactional(propagation=Propagation.REQUIRED)
	public void insertAllSeals(Seal seal,ModelMap model,HttpServletRequest request)
	{
		MDMLogger.logger.info(" val "+seal.getSealNo().replaceAll("[^0-9]", "")+"  "+Double.parseDouble(seal.getSealNo().replaceAll("[^0-9]", "")));
		Seal oldSeal=seal;
		String initialVal=seal.getSealNo();		    
		int startSealVal=Integer.parseInt(seal.getSealNo().replaceAll("[^0-9]", ""));
		int endSealVal=Integer.parseInt(seal.getMrname().replaceAll("[^0-9]", ""));//here mrName contains seal To, just taken in object form			
		
		MDMLogger.logger.info(startSealVal+"-=-=-="+endSealVal);
		int p=0;
		int q=0;
		for (int i = startSealVal; i <=endSealVal; i++) 
		{
			String startSealValLen=i+"";
			String sealNum=initialVal.substring(0,initialVal.length()-startSealValLen.length())+i;
			MDMLogger.logger.info("sealNum-=-=-="+sealNum);
			long res=(long)postgresMdas.createNamedQuery("Seal.checkSealExistOrNot").setParameter("sealNo", sealNum).getSingleResult();

			if(res>0)
			{
				q++;
			}
			else 
			{
				Seal newSeal=new Seal();
				newSeal.setSealNo(sealNum);
				newSeal.setrDate(oldSeal.getrDate());
				try 
				{
					save(newSeal);
					p++;	
					MDMLogger.logger.info("p value-=-=-="+p);
				} catch (Exception e) {
					model.put("result", "Error while Adding new seals");
					e.printStackTrace();
				}

			}
			model.put("result", p+ " seals Added successfully and seals already existed are "+q);				
		}			
		setSealEmpty(oldSeal,model);
		model.put("mrNames", mrnameService.findAll());
	}

	void setSealEmpty(Seal oldSeal,ModelMap model)
	{
		Seal  seal=new Seal();
		seal.setBillmonth(oldSeal.getBillmonth());
		model.put("sealManager", seal);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateSealOutWard(Seal seal,ModelMap model,HttpServletRequest request)
	{    	
		Seal oldSeal=seal;

		String initialVal=seal.getSealNo();	
		System.out.println("sssss"+seal.getSealNo());
		int startSealVal=Integer.parseInt(seal.getSealNo().replaceAll("[^0-9]", ""));
		int endSealVal=Integer.parseInt(seal.getCmri().replaceAll("[^0-9]", ""));//here cmri contains seal To, just taken in object form			
		String sealNum="";
		for (int i = startSealVal; i <=endSealVal; i++) 
		{
			String startSealValLen=i+"";
			sealNum=sealNum+",'"+initialVal.substring(0,initialVal.length()-startSealValLen.length())+i+"'";							
		}

		String sql="UPDATE Seal s SET s.mrname=:mrname,s.iDate=:iDate,s.billmonth=:billmonth,s.dvision=:division,s.subDiv=:subDiv,s.issuedBy=:issuedBy,s.cardSealNo=:cardSealNo WHERE (s.sealNo IN ("+sealNum.substring(1, sealNum.length())+")) AND s.iDate IS NULL";
		try {
			int res=postgresMdas.createQuery(sql).setParameter("iDate",seal.getiDate()).setParameter("billmonth",seal.getBillmonth()).setParameter("division",seal.getDvision()).setParameter("subDiv",seal.getSubDiv()).setParameter("cardSealNo",seal.getCardSealNo()).setParameter("mrname",seal.getMrname()).setParameter("issuedBy",request.getSession().getAttribute("username")).executeUpdate();

			if(res>0)
			{
				String sql2="SELECT s FROM Seal s WHERE (s.sealNo IN ("+sealNum.substring(1, sealNum.length())+")) AND s.iDate=:iDate AND s.mrname=:mrname AND s.cardSealNo=:cardSealNo AND s.billmonth=:billmonth ORDER BY s.sealNo";
				List<Seal> list=postgresMdas.createQuery(sql2).setParameter("iDate",seal.getiDate()).setParameter("mrname",seal.getMrname()).setParameter("billmonth",seal.getBillmonth()).setParameter("cardSealNo",seal.getCardSealNo()).getResultList();
				//model.put("result", "updated succesfully from sealNo "+seal.getSealNo() +" to "+seal.getCmri());
				model.put("result", "updated succesfully from sealNo "+list.get(0).getSealNo() +" to "+list.get(list.size()-1).getSealNo());
				model.put("sealPdf", "yes");
				model.put("docName", "Seal-"+seal.getMrname()+"--"+list.get(0).getSealNo()+"--"+list.get(list.size()-1).getSealNo()+"-"+getDate3(seal.getiDate()));
				model.put("pdfData",list);
			}
			else {
				model.put("result", "seals not updated please enter correct seals or seals may be already issued");
			}
		} catch (Exception e) {
			model.put("result", "Error while updation");
			e.printStackTrace();
		}
		setSealEmpty(oldSeal,model);	
		model.put("mrNames", mrnameService.findAll());
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateSealReIssue(Seal seal,ModelMap model,HttpServletRequest request)
	{    	
		System.out.println("Datsss ::::::::::::");
		System.out.println("hoi  "+seal.getCardSealNo1());
		try {
			int res=postgresMdas.createNamedQuery("Seal.SealReIssue").setParameter("iDate",seal.getiDate()).setParameter("billmonth",seal.getBillmonth()).setParameter("division",seal.getDvision()).setParameter("subDiv",seal.getSubDiv()).setParameter("cardSealNo1",seal.getCardSealNo1()).setParameter("mrname",seal.getMrname()).setParameter("issuedBy",seal.getIssuedBy()).executeUpdate();

			if(res>0)
			{
				System.out.println("Data is coming :::"+res);
				String sql2="SELECT s FROM Seal s WHERE  s.iDate=:iDate AND s.mrname=:mrname AND s.cardSealNo1=:cardSealNo1 AND s.billmonth=:billmonth ORDER BY s.sealNo";
				List<Seal> list=postgresMdas.createQuery(sql2).setParameter("iDate",seal.getiDate()).setParameter("mrname",seal.getMrname()).setParameter("billmonth",seal.getBillmonth()).setParameter("cardSealNo1",seal.getCardSealNo1()).getResultList();
				//model.put("result", "updated succesfully from sealNo "+seal.getSealNo() +" to "+seal.getCmri());
				model.put("result", "updated succesfully from sealNo "+list.get(0).getSealNo() +" to "+list.get(list.size()-1).getSealNo());
				model.put("sealPdf", "yes");
				model.put("docName", "Seal-"+seal.getMrname()+"--"+list.get(0).getSealNo()+"--"+list.get(list.size()-1).getSealNo()+"-"+getDate3(seal.getiDate()));
				model.put("pdfData",list);
				//model.put("result", "seals updated/Issued succesfully ");
			}

			else {
				model.put("result", "seals not updated, No seals with entered sealCardNo");
			}
		} catch (Exception e) {
			model.put("result", "Error while updation");
			e.printStackTrace();
		}
		setSealEmpty(seal,model);	
		model.put("mrNames", mrnameService.findAll());
	}

	 
		 @Transactional(propagation=Propagation.REQUIRED)
		 public void sealDataMultipleUpdate(Seal seal,ModelMap model,HttpServletRequest request)
		 {    	
			    Seal oldSeal=seal;
			    String initialVal=seal.getSealNo();		    
			    int startSealVal=Integer.parseInt(seal.getSealNo().replaceAll("[^0-9]", ""));
				int endSealVal=Integer.parseInt(seal.getCmri().replaceAll("[^0-9]", ""));//here cmri contains seal To, just taken in object form			
				String sealNum="";
				MDMLogger.logger.info("-=-=-=-=-seal.getCardSealNo1()>>>>>>>>>>>>>>>>"+seal.getCardSealNo1());
				for (int i = startSealVal; i <=endSealVal; i++) 
				{
					String startSealValLen=i+"";
					sealNum=sealNum+",'"+initialVal.substring(0,initialVal.length()-startSealValLen.length())+i+"'";							
				}
				
				
				System.out.println(" In Return Entry Multiple Seal Update --- iDate====="+seal.getiDate()+"division==="+seal.getDvision()+"subDiv==="+seal.getSubDiv());
				System.out.println("CardSealNo1==="+seal.getCardSealNo1()+"mrname====="+seal.getMrname()+"issuedBy==="+seal.getIssuedBy()+"revDate==="+seal.getRevDate());
				
				
				String sql="UPDATE Seal s SET s.mrname=:mrname,s.iDate=:iDate,s.billmonth=:billmonth,s.dvision=:division,s.subDiv=:subDiv,s.issuedBy=:issuedBy,s.cardSealNo1=:cardSealNo1,s.revDate=:recievedDate WHERE (s.sealNo IN ("+sealNum.substring(1, sealNum.length())+")) AND s.iDate IS NOT NULL";
				try {
					int res=postgresMdas.createQuery(sql).setParameter("iDate",seal.getiDate()).setParameter("billmonth",0).setParameter("division",seal.getDvision()).setParameter("subDiv",seal.getSubDiv()).setParameter("cardSealNo1",seal.getCardSealNo1()).setParameter("mrname",seal.getMrname()).setParameter("issuedBy",seal.getIssuedBy()).setParameter("recievedDate",seal.getRevDate()).executeUpdate();
					
					if(res>0)
						{
						model.put("result", "updated succesfully from sealNo "+seal.getSealNo() +" to "+seal.getCmri());
						}
					else
					{
						model.put("result", "seals not update please check whether entered seals are issued or not");
					}
				} catch (Exception e) {
					model.put("result", "Error while updation");
					e.printStackTrace();
				}
				setSealEmpty(oldSeal,model);
				model.put("mrNames", mrnameService.findAll());
		 }
		 

	@Transactional(propagation=Propagation.REQUIRED)
	public void sealDataSingleUpdate(Seal seal,ModelMap model,HttpServletRequest request)
	{    	
		try {

			String sealNo=seal.getSealNo();
			Date iDate=seal.getiDate();
			String division=seal.getDvision();
			String subDiv=seal.getSubDiv();
			long CardSealNo1=seal.getCardSealNo1();
			String mrname=seal.getMrname();
			String issuedBy=seal.getIssuedBy();
			Date revDate=seal.getRevDate();
			
			System.out.println(" In Return Entry Seal ---  sealNo==="+sealNo+"iDate====="+iDate+"division==="+division+"subDiv==="+subDiv);
			System.out.println("CardSealNo1==="+CardSealNo1+"mrname====="+mrname+"issuedBy==="+issuedBy+"revDate==="+revDate);
			
			//String cardSealNo=
			int res= postgresMdas.createNamedQuery("Seal.singleupdate").setParameter("sealNo", seal.getSealNo()).setParameter("iDate",seal.getiDate()).setParameter("billmonth",0).setParameter("division",seal.getDvision()).setParameter("subDiv",seal.getSubDiv()).setParameter("cardSealNo1",seal.getCardSealNo1()).setParameter("mrname",seal.getMrname()).setParameter("issuedBy",seal.getIssuedBy()).setParameter("recievedDate",seal.getRevDate()).executeUpdate();
			if(res>0)
			{
				model.put("result", "Seal updated succefully");	    	 
			}
			else
			{
				model.put("result", "seals not update please check whether entered seal is issued or not");
			}
		} catch (Exception e) {
			model.put("result", "Error while updation");
			e.printStackTrace();
		}
		Seal oldSeal=seal;
		setSealEmpty(oldSeal,model);
		model.put("mrNames", mrnameService.findAll());
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public long getMaxSealCardNum(HttpServletRequest request)
	{    	
		return (long)postgresMdas.createNamedQuery("Seal.getMaxSealCardNum").getSingleResult();
	}

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public long getMaxSealCardNum1(HttpServletRequest request)
	{    	
		return (long)postgresMdas.createNamedQuery("Seal.getMaxSealCardNum1").getSingleResult();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void getSealBunches(Seal seal,ModelMap model,HttpServletRequest request)
	{    	
		//final String q = "SELECT s.cardSealNo1,COUNT(*) FROM Seal s WHERE s.cardSealNo1 IS NOT NULL AND s.revDate IS NOT NULL AND s.iDate IS NULL GROUP BY s.cardSealNo1 ORDER BY s.cardSealNo1";
		final String q = "SELECT s.cardSealNo1,COUNT(*) FROM Seal s WHERE s.cardSealNo1 IS NOT NULL AND  s.iDate IS NULL GROUP BY s.cardSealNo1 ORDER BY s.cardSealNo1";
		Query query = postgresMdas.createQuery(q);
		List list= query.getResultList();//postgresMdas.createNamedQuery("Seal.GetSealBunches").getResultList();
		model.put("allBunches", list);
		Seal oldSeal=seal;
		setSealEmpty(oldSeal,model);
		model.put("mrNames", mrnameService.findAll());
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Seal> getSealsForMobileMR(String mrname, HttpServletRequest request) 
	{
		int billmonthint = meterMasterService.getMaxRdgMonthYear(request);
		int uploadstatus = 0;
		System.out.println("----------------- : "+mrname);
		List<Seal> data = postgresMdas.createNamedQuery("Seal.getSealsForMobileMR").setParameter("mrname",mrname).setParameter("billmonth",billmonthint).getResultList();

		if(data.size()>0){
			System.out.println(">>>>>>>>>>>>>>"+data);
			return data;

		}
		else{
			return null;
		}
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int updateSealTable(String seal, String sealRemark, String meterno, String rmrname, int billmonth, String accno){
		int updated = 0;


		updated = postgresMdas.createNamedQuery("Seal.updateSealsStatusMobile").setParameter("sealNo","%"+seal).setParameter("accNo", accno).setParameter("meterno", meterno).setParameter("remark", sealRemark).setParameter("rmrname", rmrname).setParameter("billmonth", billmonth).executeUpdate();

		return updated;

	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ArrayList<Resultupdated> updateSealsPending(HttpServletRequest request, JSONArray array) {


		Resultupdated res;
		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();


		try {
			String seal= null,sealRemark= null,idseal = null,rmrname = null, accNo = null, meterNo = null;
            int idsealInt= 0;
			
			for (int i = 0; i < array.length(); i++)
			{

				res = new Resultupdated();
				int pendingSealUpdateresult = 0;

				try{

					JSONObject json =array.getJSONObject(i);

					seal = json.getString("SEALNO");
					sealRemark = json.getString("SEALUSED");
					idseal = json.getString("SERVERID");
					idsealInt = Integer.parseInt(idseal);
					
					rmrname = json.getString("MRNAME");
					accNo = json.getString("ACCNO");
					meterNo = json.getString("EXTRA2");

					
					
					if(sealRemark.equals("DAMAGE") || sealRemark.equalsIgnoreCase("DAMAGE")){
						
						pendingSealUpdateresult = postgresMdas.createNamedQuery("Seal.updatePendingSealFromMobile")
								.setParameter("sealNo",seal)
								.setParameter("remark", sealRemark)
								.setParameter("rmrname", rmrname)
								.setParameter("id", idsealInt)
								.executeUpdate();
						
					}
					else if(sealRemark.equals("RETURN") || sealRemark.equalsIgnoreCase("RETURN")){
						
						
						pendingSealUpdateresult = postgresMdas.createNamedQuery("Seal.returnPendingSealFromMobile")
								.setParameter("sealNo",seal)
								.setParameter("remark", sealRemark)
								.setParameter("rmrname", rmrname)
								.setParameter("id", idsealInt)
								.setParameter("mrname", null)
								.setParameter("iDate", null)
								.setParameter("dvision", null)
								.setParameter("billmonth", null)
								.setParameter("subDiv", null)
								.setParameter("issuedBy", null)
								.executeUpdate();
						
					}
					
					
                       else if(sealRemark.equals("USE") || sealRemark.equalsIgnoreCase("USE")){
                    	   
                    	   
                    	   sealRemark = "SEALUSED";
						
						   pendingSealUpdateresult = postgresMdas.createNamedQuery("Seal.updateSealsUSEDFromMobile")
								                                  .setParameter("id", idsealInt)
								                                  .setParameter("accNo", accNo)
								                                  .setParameter("meterno", meterNo)
								                                  .setParameter("remark", sealRemark)
								                                  .setParameter("rmrname", rmrname)
								                                  .executeUpdate();
						
					}
					
					
					

					if(pendingSealUpdateresult > 0){


						res.status="UPDATED";
						res.connectionNo = idseal;


					}


				}					
				catch(Exception e){
					e.printStackTrace();

					res.status="NOTUPDATED";
					res.connectionNo = idseal;

				}


				list.add(res);

			} 

		}catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}




	@Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getMrwiseSealSummary(String billMonth) 
	{
		List<Object[]> summaryList=null;
		try 
		{
			String sql="SELECT s.mrname,count(*),'"+billMonth+"',(SELECT count(*) FROM Seal s1 WHERE cast(s1.billmonth as string)like :billMonth AND s1.mrname = s.mrname AND s1.remark ='SEALUSED'),(SELECT count(*) FROM Seal s1 WHERE cast(s1.billmonth as string)like :billMonth AND s1.mrname = s.mrname AND s1.remark ='DAMAGE') FROM Seal s where cast(s.billmonth as string)like :billMonth group by s.mrname order by s.mrname ";
			summaryList=postgresMdas.createQuery(sql).setParameter("billMonth", billMonth).getResultList();
			
			

			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return summaryList;
		
	}

	@Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getMrSealNo(String mrName, String billMonth,ModelMap model,String condition) 
	{
		List<Object[]> sealList=null;
		try 
		{
			String sql = "";
			if(condition.equalsIgnoreCase("%"))
			{
				 sql="SELECT s.sealNo,s.accNo,s.meterno,s.billmonth,s.mrname,s.issuedBy FROM Seal s where cast(s.billmonth as string)LIKE :billMonth and UPPER(s.mrname)LIKE :mrName " ;
			}
			else{
				 sql="SELECT s.sealNo,s.accNo,s.meterno,s.billmonth,s.mrname,s.issuedBy FROM Seal s where cast(s.billmonth as string)LIKE :billMonth and UPPER(s.mrname)LIKE :mrName AND s.remark LIKE '"+condition+"'";
			}
			
			sealList=postgresMdas.createQuery(sql).setParameter("billMonth", billMonth).setParameter("mrName", mrName).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sealList;
	}
	
	@Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getReturnSeals(ModelMap model) 
	{
		List<Object[]> returnSealList=null;
		try 
		{
			String sql="SELECT s.rmrname,count(*),'RETURN',(SELECT count(*)  FROM Seal s1 WHERE UPPER(s1.remark) LIKE 'RETURN' AND s1.iDate IS NULL AND s1.rmrname = s.rmrname AND s1.sealNo LIKE 'R%' ),"
					+ "(SELECT count(*)  FROM Seal s2 WHERE UPPER(s2.remark) LIKE 'RETURN' AND s2.iDate IS NULL AND s2.rmrname = s.rmrname AND s2.sealNo LIKE 'MR%' ) FROM Seal s where UPPER(s.remark) LIKE 'RETURN' AND s.iDate IS NULL group by s.rmrname order by s.rmrname ";
			returnSealList=postgresMdas.createQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnSealList;
	}
	
	@Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getMrWiseSealsReturn(String rmrName,ModelMap model) 
	{
		List<Object[]> returnSealList=null;
		try 
		{
			String sql="SELECT s.sealNo FROM Seal s where UPPER(s.remark) LIKE 'RETURN' AND s.iDate IS  NULL AND UPPER(s.rmrname)LIKE :rmrName ";
			returnSealList=postgresMdas.createQuery(sql).setParameter("rmrName", rmrName).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnSealList;
	}
	
	//Added by Sunil KJ
	
	 @Transactional(propagation=Propagation.SUPPORTS)
		public List<Object[]> getMrWiseSealsReturnPc(String rmrName,ModelMap model,String condition) 
		{
			List<Object[]> returnSealList=null;
			try 
			{
				String sql="SELECT s.sealNo FROM Seal s where UPPER(s.remark) LIKE 'RETURN' AND s.iDate IS  NULL AND UPPER(s.rmrname)LIKE :rmrName AND s.sealNo LIKE '"+condition+"%' ";
				returnSealList=postgresMdas.createQuery(sql).setParameter("rmrName", rmrName).getResultList();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return returnSealList;
		}
	//End
	
	

	@Override
	 @Transactional(propagation=Propagation.REQUIRED)
	public int updateCardSlNo1(String[] sealNumbers, long cardSealNo1,String rmrName)
	{
		int updateCount=0;
		int updateCount1=0;
		try
		{
			for (int i = 0; i < sealNumbers.length; i++)
			{
				String sql="UPDATE Seal s SET s.cardSealNo1=:cardSealNo1 , s.remark = 'CARDSLASSIGNED' WHERE UPPER(s.rmrname) LIKE :rmrName AND UPPER(s.remark) LIKE 'RETURN' AND s.iDate IS  NULL AND s.sealNo='"+sealNumbers[i].trim()+"'";
				MDMLogger.logger.info("-=-=-=-=->query>>>>>>>>"+sql);
				Query query=postgresMdas.createQuery(sql);
				updateCount=query.setParameter("cardSealNo1", cardSealNo1).setParameter("rmrName", rmrName).executeUpdate();
				if(updateCount==1)
				 {
					updateCount1++;
				 }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return updateCount1;
	}
	
	
	 @Transactional(propagation=Propagation.REQUIRED)
		public int updateMultipleCardSlNo1( long cardSealNo1,String rmrName,String condition)
		{
		 int updateCount = 0;
			try
			{
					String sql="UPDATE Seal s SET s.cardSealNo1="+cardSealNo1+" , s.remark = 'CARDSLASSIGNED' WHERE UPPER(s.rmrname) LIKE '"+rmrName+"' AND UPPER(s.remark) LIKE 'RETURN' AND s.iDate IS  NULL AND s.sealNo LIKE '"+condition+"%' ";
					MDMLogger.logger.info("-=-=-=-=->query>>>>>>>>"+sql);
					Query query=postgresMdas.createQuery(sql);
					 updateCount=query.executeUpdate();
					
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return updateCount;
		}
	 
	 @SuppressWarnings("unchecked")
	@Override
		public List<Object[]> getpendingdata(String month,String circle)
		{
			// old  qry
			/*String qry="select mrname,BILLMONTH,count(*) as noi from seal  where billmonth='"+month+"' and (remark like '0' or remark  is null) and accno is null group by mrname,BILLMONTH order by mrname";*/
			
			String qry=" SELECT MRNAME,BILLMONTH,COUNT(*) as NOI, '"+circle+"' as CIRCLE from SEAL where MRNAME in "
					+ " (select distinct mrname FROM seal where billmonth='"+month+"' and "
					+ " mrname in(select mrname from master where circle like '"+circle+"')) and   billmonth='"+month+"' and "
					+ " (remark like '0' or remark  is null) "
					+ " and accno is null GROUP BY MRNAME,BILLMONTH ORDER BY MRNAME ";
			return postgresMdas.createNativeQuery(qry).getResultList();
		}

		@Override
		public List<?> viewcountwiseDetails(String mrname,String month, ModelMap model,HttpServletRequest request, HttpServletResponse response)
		{
			List<?> secondPending=null;
			try {
				String qry="SELECT * FROM SEAL WHERE MRNAME like '"+mrname+"' AND BILLMONTH='"+month+"' and (remark like '0' or remark  is null) and accno is null order by SEALNO";
				secondPending= postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
		        	 e.printStackTrace();
			}
		     return secondPending;
		}

// Vijayalaxmi
		@Override
		public List<?> getFirstRecords(String rdngMonth, String sealNo)
		{ 
			List<?> firstList=null;
			try {
				String qry="SELECT RDNGMONTH,ACCNO,METRNO,NEWSEAL,MRNAME from METERMASTER where newseal like '"+sealNo+"' "
						+ "and rdngmonth='"+rdngMonth+"' order by rdngmonth desc";
				firstList=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
						
			return firstList;
		}

		@Override
		public List<?> getFirstRecords(String sealNo) 
		{
			List<?> secondList=null;
			try {
				String qry="SELECT SEALNO,MRNAME,IDATE,BILLMONTH,ACCNO,METERNO,REMARK,CARDSLNO1,RMRNAME,ISSUEDBY from "
						+ "SEAL where SEALNO like '"+sealNo+"' ";
				secondList=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
						
			return secondList;
        }
}