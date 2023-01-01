package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterReaderDetailsEntity;
import com.bcits.service.MRDetailsService;
/*Author: Ved Prakash Mishra*/
@Repository
public class MRDetailsServiceImpl extends GenericServiceImpl<MeterReaderDetailsEntity> implements MRDetailsService {

	@Override
	public List<MeterReaderDetailsEntity> getAllMrDetails() {
		List list=null;
		list=postgresMdas.createNamedQuery("MeterReaderDetailsEntity.getAllData").getResultList();
		return list;
	}

	@Override
	public Boolean findDupliMRname(String mrname) 
	{   boolean flag=false;;
		if(mrname!=null)
		{
			List list=postgresMdas.createNamedQuery("MeterReaderDetailsEntity.getDupliMrname").setParameter("mrname", mrname).getResultList();
		   if(list.size()>0)
		   {   
		       System.out.println("TTTTTTTT"+list.size());
			   return flag=true;
		   }
		}
		return flag;
		
	  
	}

	
	@Override
	public Boolean findDevice1(ModelMap model,String sbmno)
	{
	
		boolean flag=false;

		if(sbmno!= null)
		{
		   List l= postgresMdas.createNamedQuery("MeterReaderDetailsEntity.getDevice").setParameter("device",sbmno).getResultList();
		   if(l.size()>0)
		      return flag=true;
		}
		
		return flag;
	}
	
	 public String findDevice2(ModelMap model,String sbmno)
	 {
		 String flag=null;
		 try{
		   flag=(String) postgresMdas.createNamedQuery("MeterReaderDetailsEntity.getDevice").setParameter("device",sbmno).getSingleResult();
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
	
	
	
	

	// Added by shivanand
	
	@Override
	public String findMRname(String sbmno)
	{
		List<MeterReaderDetailsEntity> a =null;

		if(sbmno!= null)
		{
		a =   postgresMdas.createNamedQuery("MeterReaderDetailsEntity.getMRname").setParameter("device",sbmno).getResultList();
		  
		}
		
		String mrName =null;
		
		for (int i = 0; i < a.size(); i++)
		{
			mrName =a.get(i).getMrname();
		}
		
		return mrName;
	}
	
	

}
