package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.JvvnlUsersEntity;
import com.bcits.mdas.entity.UserMDAS;
import com.bcits.service.JvvnlUsersService;

@Repository
public class JvvnlUsersServicImpl extends GenericServiceImpl<JvvnlUsersEntity> implements JvvnlUsersService{

	@Override
	public List<?> findAllOfficeTypes() {
		
		List result=null;
		try {
		result=postgresMdas.createNamedQuery("JvvnlUsersEntity.findAllOfficeTypes").getResultList();
		//System.out.println("size office type->"+result.size());
		return result;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getAllOfficeCodes(String Office_Type) {

		//System.out.println("inside getAllOfficeCodes service Impl=="+Office_Type);
		List OfficeCodeData=null;
		String oc=Office_Type.trim();
		
		
		String qry="";
		
		try {
		
		if(oc.equalsIgnoreCase("CIRCLE"))
		{
			//System.out.println("inside circle qry office codes");
		qry="select DISTINCT circle,OFFICE_CODE from mdm_test.jvvnl_users where OFFICE_TYPE LIKE '%CIRCLE'\n" +
				   " AND OFFICE_CODE is not NULL GROUP BY CIRCLE,OFFICE_CODE ORDER BY CIRCLE ASC";
		}
		else if(Office_Type.equalsIgnoreCase("division"))
		{
			//System.out.println("inside division qry office codes");
		qry="select DISTINCT DIVISION,OFFICE_CODE from mdm_test.jvvnl_users where OFFICE_TYPE LIKE 'DIVISION'\n" +
				" AND OFFICE_CODE is not NULL GROUP BY DIVISION,OFFICE_CODE ORDER BY DIVISION ASC";
		}
		
		else if(Office_Type.equalsIgnoreCase("SUBDIVISION"))
		{
			//System.out.println("inside division qry office codes");
		qry="select DISTINCT SDONAME,OFFICE_CODE from mdm_test.jvvnl_users where OFFICE_TYPE LIKE 'SUBDIVISION'\n" +
				" AND OFFICE_CODE is not NULL GROUP BY SDONAME,OFFICE_CODE ORDER BY SDONAME ASC";
		}
		if(qry!="")
		{
		OfficeCodeData=postgresMdas.createNativeQuery(qry).getResultList();
//		System.out.println("qry-->"+qry);
//		System.out.println("OfficeCodeData size-->"+OfficeCodeData.size());
		}
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		return OfficeCodeData;
	}

	@Override
	public List<?> getDesignations() {
		List result=null;
		try {
		result=postgresMdas.createNamedQuery("JvvnlUsersEntity.getDesignations").getResultList();
//		System.out.println("size getDesignations ->"+result.size());
		return result;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}
	
 @Override
	public void findUsersData(JvvnlUsersEntity jvvnlusers,ModelMap model) {
		List<JvvnlUsersEntity> list=null;
		String Office_Code=jvvnlusers.getOffice_code();
//		System.out.println("Office_Code service Impl==>"+Office_Code);
		try {
		list=postgresMdas.createNamedQuery("JvvnlUsersEntity.findUsersData").setParameter("office_code",Office_Code).getResultList();
//		System.out.println("jvvnl users size->"+list.size());
		
		if(list.size()>0)
		{
			
			
			JvvnlUsersEntity addUser=new JvvnlUsersEntity();
			addUser.setCircle(list.get(0).getCircle());
			addUser.setDivision(list.get(0).getDivision());
			addUser.setSdoname(list.get(0).getSdoname());
			addUser.setOffice_type(jvvnlusers.getOffice_type());
			addUser.setDesignation(jvvnlusers.getDesignation());
			addUser.setOffice_code(jvvnlusers.getOffice_code());
			addUser.setUser_name(jvvnlusers.getUser_name());
			addUser.setUser_login_name(jvvnlusers.getUser_login_name().toUpperCase());
			addUser.setPassword(jvvnlusers.getPassword());
			save(addUser);
			model.addAttribute("results","Jvvnl User Added Successfully");
			
		}
		else
		{
			model.addAttribute("results","Error while Adding  Jvvnl User");
			
		}
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
	}

@Override
//@Transactional(propagation=Propagation.SUPPORTS)
public List<?> findAllUsers(ModelMap model) {
//	System.out.println("come inside all usersssss");
	List<UserMDAS> list=null;
	try
	{
		list=postgresMdas.createNamedQuery("UserMDAS.findAll").getResultList();
//		System.out.println("users size=="+list.size());
		model.addAttribute("jvvnlUserList",list);
	}
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	return list;
}

@Override
@Transactional(propagation = Propagation.REQUIRED)
public int delUsers(int userId) 
{
//	System.out.println("come to delete users-- impl");
	try {
		
	return postgresMdas.createNamedQuery("JvvnlUserEntity.delUser").setParameter("userId", userId).executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
		return 0;
	}
}

@Override
public List<JvvnlUsersEntity> findUserName(String userName) {
	try {
		return postgresMdas.createNamedQuery("JvvnlUserEntity.findDupUserName").setParameter("user_login_name", userName).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}



	
	
}
