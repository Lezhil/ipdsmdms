package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.CmriNumber;
import com.bcits.service.cmriDeviceService;
/*Author: Ved Prakash Mishra*/
@Repository

public class cmriDeviceServiceImpl  extends GenericServiceImpl<CmriNumber> implements cmriDeviceService{

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.cmriDeviceService#newCmriNumber(org.springframework.ui.ModelMap)
	 */
	
/*	@Override
	public void newCmriNumber(ModelMap model) 
	{
		
			  List<CmriNumber> cmriNumber=findAllCmriNumber();	
			  model.addAttribute("cmriNoList",cmriNumber);
		
	}
	*/
	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.cmriDeviceService#findAllCmriNumber()
	 */
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CmriNumber> findAllCmriNumber() {
		return postgresMdas.createNamedQuery("CmriNumber.FindAllCrmi").getResultList();	
	}

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.cmriDeviceService#findDuplicate(java.lang.String)
	 */
	
	@Override
	public String findDuplicate(String cmrinumber,ModelMap model) {
		 String flag=null;
		 try{
		
			 flag=(String) postgresMdas.createNamedQuery("CmriNumber.FindDuplicate").setParameter("cmri_no", cmrinumber).getSingleResult();
		 }catch(javax.persistence.NoResultException nre){
			 flag=null;
		 }
		 
		   return flag;
		 
	}

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.cmriDeviceService#updateCrmri(java.lang.String, java.lang.Integer, org.springframework.ui.ModelMap)
	 */
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateCrmri(String cmrinumber,String oldcmri,ModelMap model) 
	{
		System.out.println("NNNNNNEEEEEEEEEWWWWWWWWWCMRI"+cmrinumber);
		System.out.println("OOOOOOLLLLLLLLLDDDDDDDDDCMRI"+oldcmri);
		 int flag=0;
		 flag=postgresMdas.createNamedQuery("CmriNumber.UpdateCmriNo").setParameter("cmri_no", cmrinumber).setParameter("old_cmri_no",oldcmri).executeUpdate();
		 if(flag>0)  
		 {
		   model.put("msg", "CMRI Details Updated  Successfully");
		   return flag=1;
		 }
		   else
		   {
			   model.put("msg", "CMRI Details cannot be edited ");
			   return flag;
		   }
			 
		
	}

	@Override
	public String checkCmriAvail(String cmrinumber, ModelMap model)
	{
		String flag=null;
		 try{
		   flag=(String) postgresMdas.createNamedQuery("CmriNumber.CheckAvil").setParameter("cmri_no", cmrinumber).getSingleResult();
		   if(flag==null)
		   {
			   return null;
		   }
		 }
		 
		 catch(javax.persistence.NoResultException nre){
			 flag=null;
		 }
		 
		   return flag;
		 
	}



}
