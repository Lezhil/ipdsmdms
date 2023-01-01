package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Mrname;
import com.bcits.service.MrnameService;

@Repository
public class MrnameServiceImpl extends GenericServiceImpl<Mrname> implements MrnameService {

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Mrname> findAll() {
		
		return postgresMdas.createNamedQuery("Mrname.FindAll").getResultList();
	}

	@Override
	public String findDuplicateMrname(String mrname, ModelMap model) 
	{
		String flag=null;
		  try
		  {
	      flag=(String)postgresMdas.createNamedQuery("Mrname.FindDuplicate").setParameter("mrname",mrname).getSingleResult();
		  }
	      catch(javax.persistence.NoResultException nre)
	      {
	    	  flag=null;
	      }
	    	return flag;
	}
	
	@Override
	public int updateMRName(String oldMrName, String newMrName,String circle,String sdocode) 
	{
		int count=0;
		if(!sdocode.equalsIgnoreCase("All"))
		{
		String qry="UPDATE mdm_test.Master SET mrname='"+newMrName+"' where circle='"+circle+"' and sdocode='"+sdocode+"' and  mrname='"+oldMrName+"'";
		 count=postgresMdas.createNativeQuery(qry).executeUpdate();
		}
		else 
		{
			String qry="UPDATE mdm_test.Master SET mrname='"+newMrName+"' where circle='"+circle+"' and sdocode like '%' and mrname='"+oldMrName+"'";
			 count=postgresMdas.createNativeQuery(qry).executeUpdate();
		}
		return count;
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterMaster> AccountNos() {
		
    return postgresMdas.createNamedQuery("MeterMaster.FindAllAccounts").getResultList();
	}

	@Override
	public int updateAccountNO(String oldAccNo, String newAccNo) {
		
		String qry="UPDATE MeterMaster SET accno='"+newAccNo+"' where accno='"+oldAccNo+"'";
		int count=postgresMdas.createNativeQuery(qry).executeUpdate();
		return count;
	}

	
	public int updateMRNameSDO(String oldMrName, String newMrName,String sdoCode, String tadesc,String circle) {
		System.out.println("come to query");
		int count=0;
		String qry="";
		
	
		try {
			
		
		if(tadesc.equalsIgnoreCase("ALL"))
		{
			System.out.println("come to all tadesc");
			qry="UPDATE Master SET mrname='"+newMrName+"' where circle='"+circle+"' and mrname='"+oldMrName+"' and sdocode='"+sdoCode+"' and tadesc LIKE '%'";
	
		}
		else
		{
			System.out.println("come to selected tadesc");
		qry="UPDATE mdm_test.Master SET mrname='"+newMrName+"' where circle='"+circle+"' and mrname='"+oldMrName+"' and sdocode='"+sdoCode+"' and tadesc='"+tadesc+"'";
		
		} 
		count=postgresMdas.createNativeQuery(qry).executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateMrnameTable(String oldMrName, String newMrName) {
		System.out.println("come to query updateMrnameTable");
		String qry="UPDATE MRNAME SET MRNAME='"+newMrName+"' WHERE MRNAME='"+oldMrName+"'";
		System.out.println("qry=="+qry);
		int count=postgresMdas.createNativeQuery(qry).executeUpdate();
		return count;
	}
	
	
	
	

}

