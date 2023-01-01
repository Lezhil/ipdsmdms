package com.bcits.mdas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.UserAccessType;
import com.bcits.mdas.service.BusinessRoleService;
import com.bcits.service.UserAccessTypeService;


@Controller
public class UserAccessTypeController {
	@Autowired
	BusinessRoleService businessRoleService;

	@Autowired
	private UserAccessTypeService userAccessTypeService;
	
	/*@RequestMapping(value="/accessTypes",method={RequestMethod.POST,RequestMethod.GET})
	public  String buisnessRoleDetails(ModelMap model,HttpServletRequest request)
	{	
				
		List<?> resultList =null;
		List<?> moduleList = null;
		try {
			resultList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.user_access_type").getResultList();
			moduleList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.roleprevilage").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("moduleList" ,moduleList);
		model.addAttribute("resultList" ,resultList);
		return "userAccess";
	}*/
	
	@RequestMapping(value="/accessTypes",method={RequestMethod.POST,RequestMethod.GET})
	public  String buisnessRoleDetails(ModelMap model,HttpServletRequest request)
	{	
				
		List<?> resultList =null;
		List<?> moduleList = null;
		try {
			
			String sql="SELECT * FROM meter_data.roleprevilage  where active_status=true order by submodule asc";
			String sql2 = "SELECT * FROM meter_data.user_access_type order by id";
					
			moduleList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			resultList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql2).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("moduleList" ,moduleList);
		model.addAttribute("resultList" ,resultList);
		return "userAccess";
	}
	
	@RequestMapping(value="/accessTypeDemo",method={RequestMethod.POST,RequestMethod.GET})
	public  String accessTypeDemo(ModelMap model,HttpServletRequest request)
	{	
				
		List<?> resultList =null;
		List<?> moduleList = null;
		try {
			
			String sql="SELECT * FROM meter_data.roleprevilage where active_status=true order by id";
			String sql2 = "SELECT * FROM meter_data.user_access_type order by id";
					
			moduleList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			resultList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql2).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("moduleList" ,moduleList);
		model.addAttribute("resultList" ,resultList);
		return "TestT";
	}
	
	
	@RequestMapping(value="/ModulesNames",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getModuleName(ModelMap model,HttpServletRequest request)
	{	
				
		
		List<?> moduleList = null;
		try {
			
			moduleList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.roleprevilage where active_status=true order by submodule asc").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return moduleList;
	}
	
	
	@RequestMapping(value="/assignedModulesNames",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object assignedModuleName(ModelMap model,HttpServletRequest request)
	{	
				
		
		List<?> moduleList = null;
		try { 
			
			moduleList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.user_access_type").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return moduleList;
	}
	
	@RequestMapping(value="/addUserTypeModuleData",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody String addUserTypeData(@RequestParam("moduleList[]") String[] moduleList, HttpServletRequest request,ModelMap model)
	    {
	    //	String accessRoles = request.getParameter("moduleName");
	    	String userAccessType = request.getParameter("uType");
//	    	System.err.println(moduleList);
	    	String success = null;
	    	String result = ""; 
	    	if (moduleList.length > 0)
	    	{ 
	    	StringBuilder sb = new StringBuilder();
	    	for (String s : moduleList) {
	    		sb.append(s).append(",");
	    	} 
	    	result = sb.deleteCharAt(sb.length() - 1).toString();
	    	}
	   
	    	try {
	    		UserAccessType uAT = new UserAccessType();
	    		uAT.setUserType(userAccessType);
	    		uAT.setAccessTypeId(result);
	    		userAccessTypeService.customSave(uAT);
	    		success = "Success";
			} catch (Exception e) {
				e.printStackTrace();
				success = "NotSuccess";
			}
    	
    		/*model.put("msg", "UserType Created  Successfully...");
    		model.put("userTypeList", userAccessTypeService.findAll());*/
			return success;
	    }
	
	
	@RequestMapping(value="/checkExcistingUserType",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody String  checkExcistingUserType( HttpServletRequest request,ModelMap model){
		String result = null;
		String userAccessType = request.getParameter("uType");
		
		long n = userAccessTypeService.checkUserType(userAccessType);
		if(n > 0)
    	{
			result = "availabale";
    	}else{
    		result = "Notavailabale";
    	}
		return result;
	    }
	
	
	@RequestMapping(value="/assignedtoUser",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody String  assignedtoUser( HttpServletRequest request,ModelMap model){
		String result = null;
		String userAccessType = request.getParameter("userType");
		
		boolean userResult  = userAccessTypeService.isUserTypeAssigned(userAccessType);
		if(userResult)
   	{
			result = "availabale";
   	}else{
   		result = "Notavailabale";
   	}
		return result;
	    }
	
	@RequestMapping(value="/deleteUsertype",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody String  deleteUsertype( HttpServletRequest request,ModelMap model){
		String result = null;
		String deleteId = request.getParameter("id");
		
		int userResult  = userAccessTypeService.deleteUserType(deleteId);
	if(userResult > 0){
		result = "Success";
	}else{
		result = "Unsuccess";
	}
		
		return result;
	    }
	
	@Transactional
	@RequestMapping(value="/updateTypeModuleData",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody String updateTypeModuleData(@RequestParam("moduleList[]") String[] moduleList,@RequestParam("mainUserType") String mainUserType,
			 @RequestParam("uType") String userAccessType,
			/* @RequestParam("viewAccess") String viewAccessData,
			 @RequestParam("editAccess") String editAccessData, */
			 HttpServletRequest request,ModelMap model)
	    {
	    //	String accessRoles = request.getParameter("moduleName");
		/*
		 * String userAccessType = request.getParameter("uType"); String viewAccessData
		 * = request.getParameter("viewAccess"); String editAccessData =
		 * request.getParameter("editAccess");
		 */
	    	
	    	
	    	
	    	String success = null;
	    	String result = ""; 
	    	if (moduleList.length > 0)
	    	{ 
	    	StringBuilder sb = new StringBuilder();
	    	for (String s : moduleList) {
	    		sb.append(s).append(",");
	    	} 
	    	result = sb.deleteCharAt(sb.length() - 1).toString();
	    	}
	   
	    	
	    	String qry = "Update meter_data.user_access_type set usertype = '"+userAccessType+"' , accesstypeid ='"+result+"' where usertype = '"+userAccessType+"' ";
	    	
	    	int x = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
   	
   		/*model.put("msg", "UserType Created  Successfully...");
   		model.put("userTypeList", userAccessTypeService.findAll());*/
	    	if (x > 0) {
	    		return success;	
	    	}else {
	    		return "failed";
	    	}
	    	
			
	    }
}


