package com.bcits.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
/*<<<<<<< .mine*/
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
/*>>>>>>> .r39007*/
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

/*<<<<<<< .mine*/
import com.bcits.entity.CMRIEntity;
import com.bcits.service.CMRIService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.SdoJccService;
import com.bcits.service.cmriDeviceService;
import com.bcits.utility.MDMLogger;
/*=======*/
/*=======*/
/*>>>>>>> .r39007*/

@Repository
public class CMRIServiceImpl extends GenericServiceImpl<CMRIEntity> implements CMRIService  {

	@Autowired
	private MrnameService mrnameService;
	
	@Autowired
	private SdoJccService sdoJccService;
	
	@Autowired
	private cmriDeviceService cmriDeviceService;
    
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void commonCMRIProperties(HttpServletRequest request,ModelMap model,CMRIEntity cmri) {
	
		
		model.put("mrNames", mrnameService.findAll());
		model.put("subDivision", sdoJccService.findAll());
		model.put("cmriNo", cmriDeviceService.findAllCmriNumber());
		cmri.setBillMonth(meterMasterService.getMaxRdgMonthYear(request));		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIIssueDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIIssueDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIBasedOnMrName(String name,Date rdgDate)
	{		
		//System.out.println("-name : "+name + " : "+getDate3(rdgDate) + " : "+rdgDate);
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIBasedOnMrName").setParameter("name",name).setParameter("rdgDate",getDate3(rdgDate)).getResultList();	    
		return list;
	}
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public int getCMRIIssuedOrNot(HttpServletRequest request,ModelMap model,CMRIEntity cmri)
	{	
		int res=0;		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.FindCMRIIssues").setParameter("rdgDate",getDate3(cmri.getRdgDate())).setParameter("billMonth",cmri.getBillMonth()).setParameter("mriNo", cmri.getMriNo()).getResultList();
	    if(list.size()>0)
	    {
	    	res=1;
	    }
		return res;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIIssueDButnotRecievecDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIIssueDButnotRecievecDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIIssueDAndRecievecDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		long secureCount = 0,lntCount = 0,genusC = 0,genusD = 0,hplM = 0,hplD = 0,lngCount =0,mipC=0,mipM=0,sipC=0,sipM = 0;
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIIssueDAndRecievecDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		for(int i=0;i < list.size();i++)
		{
			
			secureCount = secureCount + list.get(i).getMrSecure();
			
			lntCount = lntCount + list.get(i).getMrLnt();
			genusC = genusC + list.get(i).getMrGenusc();
			genusD = genusD + list.get(i).getMrGenusd();
			hplM = hplM + list.get(i).getMrHplmacs();
			hplD = hplD + list.get(i).getMrHpld();
			lngCount = lngCount + list.get(i).getMrlng();
			mipC = mipC + list.get(i).getMipCmri();
			mipM = mipM + list.get(i).getMipMannual();
			sipC = sipC + list.get(i).getSipCmri();
			sipM = sipM + list.get(i).getSipMannual();
		}
		model.put("secureCount", secureCount);model.put("lntCount", lntCount);model.put("genusC", genusC);
		model.put("genusD", genusD);
		model.put("hplM", hplM);
		model.put("hplD", hplD);
		model.put("lngCount", lngCount);
		model.put("mipCCount", mipC);
		model.put("mipMCount", mipM);
		model.put("sipCCount", sipC);
		model.put("sipMCount", sipM);
		
		
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIRecievecButNotDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{	
		MDMLogger.logger.info("all val "+cmri.getBillMonth()+" "+ rdgDate);
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIRecievecButNotDumpedDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	 
		MDMLogger.logger.info("size val "+list.size());
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIRecievecAndDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIRecievecAndDumpedDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIPreparedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIPreparedDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIDifferenceDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate)
	{		 
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIDifferenceDetails").setParameter("rdgDate",getDate3(rdgDate)).setParameter("billMonth",cmri.getBillMonth()).getResultList();	    
		return list;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addCMRIData(HttpServletRequest request,ModelMap model,CMRIEntity cmri)
	{
		try {			
			save(cmri);	
			model.put("result", "CMRI Issued successfully");
			model.put("cmriData",getCMRIIssueDetails(request, model, cmri, new Date()));
		} catch (Exception e) {
			model.put("result", "error while Issuing CMRI");
			e.printStackTrace();
		}	
		cmri=new CMRIEntity();
		commonCMRIProperties(request, model, cmri);	
		model.put("cmriManager", cmri);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCMRIDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri)
	{	
		try {
			//update(cmri);	
			int res=postgresMdas.createNamedQuery("CMRIEntity.updateIssueDetails").setParameter("rdgDate", getDate3(cmri.getRdgDate())).setParameter("mriNo", cmri.getMriNo()).setParameter("billMonth", cmri.getBillMonth()).setParameter("subDiv", cmri.getSubDiv()).setParameter("accessories", cmri.getAccessories()).setParameter("name", cmri.getName()).setParameter("iDate", cmri.getiDate()).executeUpdate();
			if(res>0)
			{
				model.put("result", "CMRI updated successfully");
			}
			
		} catch (Exception e) {
			model.put("result", "Error while updating");
			e.printStackTrace();			
		}		
		model.put("cmriData",getCMRIIssueDetails(request, model, cmri, cmri.getRdgDate()));
		cmri=new CMRIEntity();
		commonCMRIProperties(request, model, cmri);
		model.put("cmriManager", cmri);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCMRIRecieveDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri)
	{	
		try {
			System.out.println("the billmonth Is : "+cmri.getBillMonth());
			cmri.setTotalMrd(cmri.getMrSecure()+cmri.getMrLnt()+cmri.getMrGenusc()+cmri.getMrGenusd()+cmri.getMrHpld()+cmri.getMrHplmacs()+cmri.getMrlng());
			//update(cmri);
			int res=postgresMdas.createNamedQuery("CMRIEntity.updateRecieveDetails").setParameter("rdgDate", getDate3(cmri.getRdgDate())).setParameter("rDate", cmri.getrDate()).setParameter("mriNo", cmri.getMriNo()).setParameter("billMonth", cmri.getBillMonth()).setParameter("mrSecure", cmri.getMrSecure()).setParameter("mrGenusc", cmri.getMrGenusc()).setParameter("mrGenusd", cmri.getMrGenusd()).setParameter("mrHpld", cmri.getMrHpld()).setParameter("mrHplmacs", cmri.getMrHplmacs()).setParameter("mrlng",  cmri.getMrlng()).setParameter("mrLnt",  cmri.getMrLnt()).setParameter("mrRemark",  cmri.getMrRemark()).setParameter("mipCmri",  cmri.getMipCmri()).setParameter("mipMannual",  cmri.getMipMannual()).setParameter("sipCmri",  cmri.getSipCmri()).setParameter("sipMannual",  cmri.getSipMannual()).setParameter("totalMrd",  cmri.getTotalMrd()).setParameter("gxtFiles",  cmri.getGxtFiles()).executeUpdate();
			model.put("result", "CMRI details updated successfully");			
		} catch (Exception e) {
			model.put("result", "Error while updating");
			e.printStackTrace();			
		}
		cmri=new CMRIEntity();
		commonCMRIProperties(request, model, cmri);
		model.put("cmriManager", cmri);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCMRIDumpedDetails(HttpServletRequest request,ModelMap model,CMRIEntity cmri)
	{	
		try {
			cmri.setMrdDumped(cmri.getSecure()+cmri.getLng()+cmri.getLnt()+cmri.getgCommon()+cmri.getgDlms()+cmri.getHpld()+cmri.getHplm());
			update(cmri);	
			model.put("result", "CMRI details updated successfully");			
		} catch (Exception e) {
			model.put("result", "Error while updating");
			e.printStackTrace();			
		}
		cmri=new CMRIEntity();
		commonCMRIProperties(request, model, cmri);
		model.put("cmriManager", cmri);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CMRIEntity> getCMRIDataForRecieve(HttpServletRequest request,ModelMap model,CMRIEntity cmri,Date rdgDate,String mriNo)
	{	
		List<CMRIEntity> list= postgresMdas.createNamedQuery("CMRIEntity.getCMRIDataForRecieve").setParameter("rdgDate",getDate3(rdgDate)).setParameter("mriNo",mriNo).getResultList();
		MDMLogger.logger.info("size and vals "+list.size()+ " " +list.get(0).getName());
		return list;
	}
	
	@Override
	public List<CMRIEntity> finCmriNo(ModelMap model) 
	{
		List<CMRIEntity> data=null;
		data= postgresMdas.createNamedQuery("CMRIEntity.FindCmri").getResultList();
		if(data.size()>0)
		{
			System.out.println("Whu data is not comming"+data);
			model.put("cmrino",data);
			return data;
		}
		else
		{
			return null;
		}
	
		
	}

/*	@Override
	public List<CMRIEntity> findAllCmriData(String mrname,ModelMap model)
	{
		  List<CMRIEntity> data=null;
		 data=postgresMdas.createNamedQuery("CMRIEntity.findAllData").setParameter("name",mrname).getResultList();
	      
		  if (data.size() > 0)
		  {
				
				for (Iterator iterator1 = data.iterator(); iterator1.hasNext();) 
				{
					CMRIEntity cmrim = (CMRIEntity) iterator1.next();
					//model.put("data",cmrim.getmrn);
					model.put("metrno",cmrim.getMriNo());
					System.out.println("Result"+cmrim.getMriNo());
					
				}
			
		  }
			return data;
	}
*/
	/*@Override
	
	public List<CMRIEntity> findAllCmriData1(String rdate,ModelMap model)throws Exception
	{
		 List<CMRIEntity> data=null;
		 DateFormat redadingdate=new SimpleDateFormat("yyyy-MM-dd");
		 Date startdate=redadingdate.parse(rdate);
	     String newdatestring=redadingdate.format(startdate);
	     
	     System.out.println("RRRRRRRRRRRRDDDDDDDDDDDD"+startdate);
		 data=postgresMdas.createNamedQuery("CMRIEntity.findAllData1").setParameter("rDate",startdate).getResultList();
	      
		  if (data.size() > 0)
		  {
				System.out.println("WWWWWWWWWWWWWWWWWWWWWWW");
				for (Iterator iterator1 = data.iterator(); iterator1.hasNext();) 
				{
					   CMRIEntity cmrim = (CMRIEntity) iterator1.next();
				       model.put("data",cmrim.getName());
				       model.put("cmrino",cmrim.getMriNo());
					   System.out.println("RDDDDDDDDDDDDDDDDDDDDD"+cmrim.getMriNo()+" "+cmrim.getName());
					
				}
			
		  }
			return data;
	}

	*/
	
	@Override
	public List findCmriData(String mrname) {
		
		return postgresMdas.createNamedQuery("CMRIEntity.findAllData").setParameter("name",mrname).getResultList();
	}


	@Transactional(propagation=Propagation.REQUIRED)
	public CMRIEntity uploadMakeWise(String readingDate,CMRIEntity cmri)
	{
		String query = "SELECT DISTINCT READINGDATE,MTRMAKE,COUNT(*)   FROM   METERMASTER   WHERE "+
						"TO_CHAR(READINGDATE,'DD-MM-YYYY') ='"+readingDate+"' AND MRNAME LIKE '"+cmri.getName()+"%'  AND  MRINO  IS NOT NULL "  +
						"GROUP BY READINGDATE,MTRMAKE "+
						"ORDER BY READINGDATE ,MTRMAKE";
		Query q = postgresMdas.createNativeQuery(query);
		List<?> list = q.getResultList();
		for(int i=0;i<list.size();i++)
		{
			Object[] obj = (Object[])list.get(i);
			String mtrMake = obj[1]+"";
			long count = Long.parseLong(obj[2]+"");
			if(mtrMake.equalsIgnoreCase("SECURE"))
			{
				cmri.setMrSecure(count);
			}
			else if(mtrMake.equalsIgnoreCase("LnT"))
			{
				cmri.setMrLnt(count);
			} 
			else if(mtrMake.equalsIgnoreCase("GENUSC"))
			{
				cmri.setMrGenusc(count);
			} 
			else if(mtrMake.equalsIgnoreCase("GENUSD"))
			{
				cmri.setMrGenusd(count);
			} 
			else if(mtrMake.equalsIgnoreCase("HPLM"))
			{
				cmri.setMrHplmacs(count);;
			} 
			else if(mtrMake.equalsIgnoreCase("HPLD"))
			{
				cmri.setMrHpld(count);
			} 
			else if(mtrMake.equalsIgnoreCase("LNG"))
			{
				cmri.setMrlng(count);
			} 
		}
		
		
		String query1 = "SELECT DISTINCT A.READINGDATE,B.TADESC,COUNT(*)   FROM   METERMASTER A ,MASTER B  "+
						"WHERE TO_CHAR(A.READINGDATE,'DD-MM-YYYY') ='"+readingDate+"' AND A.MRNAME "
								+ "LIKE '"+cmri.getName()+"%' AND A.ACCNO=B.ACCNO  AND A.MRINO  IS NOT NULL "+
						"GROUP BY A.READINGDATE,B.TADESC ORDER BY A.READINGDATE ,B.TADESC";
		Query q1 = postgresMdas.createNativeQuery(query1);
		List<?> list1 = q1.getResultList();
		long ht = 0;
		long mipc = 0;
		for(int j=0 ;j<list1.size();j++)
		{
			Object[] obj = (Object[])list1.get(j);
			String type = obj[1]+"";
			
			long count = Long.parseLong(obj[2]+"");
			
			if(type.equalsIgnoreCase("HT"))
			{
				ht = count;
			}
			
			if(type.equalsIgnoreCase("MIP"))
			{
				mipc = count;
				
			}
			if(type.equalsIgnoreCase("SIP"))
			{
				cmri.setSipCmri(count);
			}
		}
		cmri.setMipCmri(mipc+ht);
		String query2 = "SELECT DISTINCT A.READINGDATE,B.TADESC,COUNT(*)   FROM   METERMASTER A ,MASTER B "+
						"WHERE TO_CHAR(A.READINGDATE,'DD-MM-YYYY') ='"+readingDate+"' AND "
								+ "A.MRNAME LIKE '"+cmri.getName()+"%' AND A.ACCNO=B.ACCNO  AND A.MRINO  IS  NULL "
								+"GROUP BY A.READINGDATE,B.TADESC ORDER BY A.READINGDATE ,B.TADESC";
		Query q2 = postgresMdas.createNativeQuery(query2);
		List<?> list2 = q2.getResultList();

		
		long ht1 = 0;
		long mipm = 0;
		for(int j=0 ;j<list2.size();j++)
		{
			Object[] obj = (Object[])list2.get(j);
			String type = obj[1]+"";
			long count = Long.parseLong(obj[2]+"");
			if(type.equalsIgnoreCase("HT"))
			{
				ht1 = count;
			}
			if(type.equalsIgnoreCase("MIP"))
			{
				mipm = count;
			}
			if(type.equalsIgnoreCase("SIP"))
			{
				cmri.setSipMannual(count);
			}
		}
		cmri.setMipMannual(ht1+mipm);
		
		
		update(cmri);
		
		return cmri;
	}
	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CMRIEntity> findMrCmrinumber(String name,HttpServletRequest request,ModelMap model) {
	    return entityManager.createNamedQuery("CMRIEntity.FindMrCmrino").setParameter("name",name).getResultList();
		
	}*/
	
	

	
}
