package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.UserAccessType;
import com.bcits.service.UserAccessTypeService;


@Repository
public class UserAccessTypeServiceImpl extends GenericServiceImpl<UserAccessType> implements UserAccessTypeService{

	@Transactional(propagation=Propagation.SUPPORTS)
	public String getSideMenu(String userType)
	{
		System.out.println("userType-->"+userType);
		return (String)postgresMdas.createNamedQuery("UserAccessType.getSideMenu").setParameter("userType", userType).getSingleResult();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public long checkUserType(String userType)
	{
		return (long)postgresMdas.createNamedQuery("UserAccessType.checkUserType").setParameter("userType", userType).getSingleResult();
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UserAccessType> findAll()
	{
		return postgresMdas.createNamedQuery("UserAccessType.findAll").getResultList();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean isUserTypeAssigned(String userType)
	{
		List<?> result ;
		
		String qry = "Select *from meter_data.users where usertype= '"+userType+"' ";
		
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		if(result.size()> 0 ){
			return true;
		}else{
			return false;	
		}
			
		
	}
	
	
	@Transactional
	public int deleteUserType(String deleteId)
	{
		int result ;
		
		String qry = "delete from meter_data.user_access_type where id = '"+deleteId+"' ";
		
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		
		return result;
			
		
	}

	@Override
	public String getEditRights(String userType) {
		String  result ;
		
		String qry = "SELECT EDIT_ACCESS  FROM METER_DATA.USER_ACCESS_TYPE WHERE USERTYPE = '"+userType+"'  ";
	
		
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult().toString();
		
		return result;
	}

	@Override
	public String getViewRights(String userType) {
		String result ;
		
		String qry = "SELECT VIEW_ACCESS FROM METER_DATA.USER_ACCESS_TYPE WHERE USERTYPE = '"+userType+"'  ";
		
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult().toString();
		
		return result;
	}
}
