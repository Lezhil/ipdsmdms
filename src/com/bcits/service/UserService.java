package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;

import com.bcits.entity.JvvnlUsersEntity;
import com.bcits.entity.User;

public interface UserService extends GenericService<User>
{

	public abstract List<User> findAll(String userMailId, String userPassword);
	public String afterLogin(String userMailId,String userPassword,HttpSession session,ModelMap model);
	public String parseTheFile(Document doc,String billmonthParam,ModelMap model,String unZipFIlePath,String filename);
	public List<User> findAllUser();
	public abstract List<JvvnlUsersEntity> findAllJvvnlUsers(String userMailId, String userPassword);
	public Boolean findDuplicate(User user,ModelMap modelss);
	//public void importBillingParameters(Document docForMetrNo);
	public Integer getUserID(String username);
	
	public String jvvnlUserLogin(String userMailId,String userPassword,HttpSession session,ModelMap model);
	public List<String[]> getReport();
	//public  String getofficetype(String username);
	
	/*public Boolean findDuplicate1(String mailid,ModelMap modelss);
	public Boolean findConstraint(User user);
*/
	public List getSubdivList(String officeCode,String officeType);
	public  User getDataById(String userid);
	User getDataByEmailId(String emailId);

}