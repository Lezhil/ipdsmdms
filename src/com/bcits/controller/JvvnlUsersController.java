package com.bcits.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.JvvnlUsersEntity;
import com.bcits.service.JvvnlUsersService;
import com.bcits.utility.MDMLogger;

@Controller
public class JvvnlUsersController {
	
	@Autowired
	private JvvnlUsersService jvvnlUsersService;
	
@RequestMapping(value="/jvvnlUsers",method={RequestMethod.GET,RequestMethod.POST})
public String JvvnlUsers(@ModelAttribute("newJvvnlUsers") JvvnlUsersEntity jvvnlusers,BindingResult bindingResult,ModelMap model, HttpServletRequest req,HttpServletResponse resp)
{
	MDMLogger.logger.info("--Inside Jvvnl Users--");
	
	jvvnlUsersService.findAllUsers(model);
	
	/*model.addAttribute("Office_Types", jvvnlUsersService.findAllOfficeTypes());
	model.addAttribute("designations", jvvnlUsersService.getDesignations());*/
	
	int value=0;
	if(req.getParameter("flag")!=null)
	  {
		
		   value=Integer.parseInt(req.getParameter("flag"));
		   if(value==0)
			  {
				  model.addAttribute("results", "JVVNL User Deleted Successfully.");
			  }
			  else if(value==1)
			  {
				  model.addAttribute("results", "Error While Deleting ....!!!");
			  }
	  }
	  else
	  {
		  model.addAttribute("results", "notDisplay");
	  }
	 
	return "JvvnlUser";
	
}


@RequestMapping(value="/getOfficeCodes",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getOfficeCodes(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
	{
	
	System.out.println("come inside get office codes");
//	System.out.println("office type=="+request.getParameter("office_type"));
	String Office_Type=request.getParameter("office_type");
	List Office_codes=jvvnlUsersService.getAllOfficeCodes(Office_Type.trim());
	jvvnlUsersService.findAllUsers(model);
	return Office_codes;
}




@RequestMapping(value="/addNewJvvnlUsers",method={RequestMethod.POST,RequestMethod.GET})
public String addNewDHBVNUSers(@ModelAttribute("newJvvnlUsers") JvvnlUsersEntity jvvnlusers,BindingResult bindingResult,ModelMap model,HttpServletRequest request)
{
	MDMLogger.logger.info("In ::::::::::: Add And Update JVVNL USERS ::::::::: ");
	

	System.out.println("userId----"+jvvnlusers.getUserId());
	int uid=jvvnlusers.getUserId();
	
	if(uid==0)
	{
		jvvnlUsersService.findUsersData(jvvnlusers, model);
		
	}
	else
	{
		jvvnlUsersService.update(jvvnlusers);
		model.addAttribute("results","Jvvnl User updated Successfully");
	}
	model.addAttribute("Office_Types", jvvnlUsersService.findAllOfficeTypes());
	model.addAttribute("designations", jvvnlUsersService.getDesignations());
	model.addAttribute("newJvvnlUsers", new JvvnlUsersEntity());
	jvvnlUsersService.findAllUsers(model);
	
	return "JvvnlUser";
}


@RequestMapping(value="/editJvvnlUserList/{opera}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object editDhbvnUserList(@PathVariable int opera,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		MDMLogger.logger.info("In Edit Jvvnl User Details ----"+opera);
		model.addAttribute("newJvvnlUsers", new JvvnlUsersEntity());
		JvvnlUsersEntity jvvnlUsers = jvvnlUsersService.find(opera);
		String pwd=jvvnlUsers.getPassword();
		System.out.println("password-->"+pwd);
		jvvnlUsers.setPassword(pwd);
		jvvnlUsersService.findAllUsers(model);
		model.addAttribute("Office_Types", jvvnlUsersService.findAllOfficeTypes());
		model.addAttribute("designations", jvvnlUsersService.getDesignations());
		return jvvnlUsers;
	}
	


@RequestMapping(value="/delJvvnlUserList/{opera}",method={RequestMethod.POST,RequestMethod.GET})
public @ResponseBody Object delJvvnlUserList(@PathVariable int opera,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	
	MDMLogger.logger.info("In Delte Jvvnl User Details ----"+opera);
	
	
	String val1= "deleted";
    String val2= "notDeleted";
	int UsrId=opera;
    jvvnlUsersService.delUsers(UsrId);
	JvvnlUsersEntity entity=jvvnlUsersService.find(UsrId);
	
	if(entity==null)
	{
	   
	    return val1;
	}
	else
	{
		return val2;
	}
	
}

@RequestMapping(value="/getDupUserName",method={RequestMethod.POST,RequestMethod.GET})
public @ResponseBody Object getDupUserName(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{    
	MDMLogger.logger.info("In Duplicate User Details ----");
	String uname=request.getParameter("name");
	System.out.println("uname-->"+uname);
	List<JvvnlUsersEntity> userName=jvvnlUsersService.findUserName(uname);
	MDMLogger.logger.info("In  ----"+userName.size());
	return userName;
}
}
