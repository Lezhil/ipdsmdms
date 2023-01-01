package com.bcits.mdas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.User;
import com.bcits.mdas.entity.BusinessRoleEntity;
import com.bcits.mdas.service.BusinessRoleService;

@Controller
public class UserManagementController {

	private static final String String = null;
	@Autowired
	BusinessRoleService businessRoleService;
	int addNewBusiness=0;
	private Object role_name;
	
	@RequestMapping(value="/buisnessRoleDetails",method={RequestMethod.POST,RequestMethod.GET})
	public  String buisnessRoleDetails(ModelMap model,HttpServletRequest request)
	{	
			
		
		List<?> resultList =null;
		
		try {
			resultList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.business_roles").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
			if (addNewBusiness == 1) {
				model.addAttribute("successAdded","Successfully add new Buisness role");
			}
		model.addAttribute("resultList" ,resultList);
		return "businessRoleManagement";
	}
	
	// Role Previliges
	@RequestMapping(value="/roleprivileges",method={RequestMethod.POST,RequestMethod.GET})
	public  String roleprivileges(ModelMap model,HttpServletRequest request)
	{
		List<?> rolesList =null;
		
		try {
			rolesList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT role_name FROM meter_data.business_roles").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("rolesList" ,rolesList);
		return "roleprivileges";
	}
	
	
	
	
	
	
	@RequestMapping(value="/addBuisnessRoleDetails",method={RequestMethod.POST,RequestMethod.GET})
	public  String addBuisnessRoleDetails(ModelMap model,@ModelAttribute("addBusinessRoles")BusinessRoleEntity businessRoleEntity, HttpServletRequest request)
	{	
		HttpSession session=request.getSession();
		
	
		String roleName = request.getParameter("roleName");
		String userName =(String) session.getAttribute("username"); 
		
		BusinessRoleEntity bse = new BusinessRoleEntity();
		
		bse.setRoleName(roleName);
		bse.setEntryBy(userName);
		bse.setEntrydate(new Date());
		
		try {
			businessRoleService.save(bse);	
			addNewBusiness= 1;
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
		return "redirect:/buisnessRoleDetails";
	}
	
	
	@RequestMapping(value = "/validateBuisnessRoles", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String validateBuisnessRoles(ModelMap model, HttpServletRequest request){
		
		
		String roleName = request.getParameter("roleName");
		List<?> resultList =null;
		
		try {
			resultList = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT role_name FROM meter_data.business_roles where role_name = '"+roleName+"' ").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if (resultList.size() != 0) {
			return "rollExits";
		}else{
			return "rollNotExits";
		}
		

	}
	
	
	
	@RequestMapping(value = "/getLocationCodes", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String getLocationCodes(ModelMap model, HttpServletRequest request){
		
		
		String locationName = request.getParameter("name");
		String locationType = request.getParameter("type");
		String resultList =null;
		String qry = null ;
		
		if(locationType.equalsIgnoreCase("corporate")){
			qry = "SELECT DISTINCT discom_code FROM meter_data.amilocation where DISCOM = '"+locationName+"' ";	
		}else if(locationType.equalsIgnoreCase("region")){
				qry = "SELECT DISTINCT zone_code FROM meter_data.amilocation where zone = '"+locationName+"' ";	
		}else if(locationType.equalsIgnoreCase("circle")){
			qry = "SELECT DISTINCT circle_code FROM meter_data.amilocation where circle = '"+locationName+"' ";	
		}else if(locationType.equalsIgnoreCase("division")){
			qry = "SELECT DISTINCT division_code FROM meter_data.amilocation where division = '"+locationName+"' ";
		}else if (locationType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT DISTINCT sitecode FROM meter_data.amilocation where subdivision = '"+locationName+"' ";
		}
		
		
		try {
			resultList = (String) businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult().toString();
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return resultList;

	}
	
	
	@RequestMapping(value = "/getlocationTypes", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody List<?> getlocationTypes(ModelMap model, HttpServletRequest request){
		
		
		
		String locationType = request.getParameter("officeType");
		List<?> resultList =null;
		String qry = null ;
		ArrayList<String> al = new ArrayList<>();
		al.add("TNEB");
		if(locationType.equalsIgnoreCase("corporate")){
			qry = "SELECT DISTINCT DISCOM,DISCOM_CODE FROM meter_data.amilocation where discom is not null ";
		}if(locationType.equalsIgnoreCase("region")){
			qry = "SELECT DISTINCT zone, ZONE_CODE FROM meter_data.amilocation where zone is not null ";
		}else if(locationType.equalsIgnoreCase("circle")){
			qry = "SELECT DISTINCT circle, Circle_CODE FROM meter_data.amilocation where circle is not null ";	
		}else if(locationType.equalsIgnoreCase("division")){
			qry = "SELECT DISTINCT division, DIVISION_CODE FROM meter_data.amilocation where division is not null";
		}else if (locationType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT DISTINCT subdivision, SITECODE FROM meter_data.amilocation where subdivision is not null ";
		}
		
		try {
			resultList = (List<?>) businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return resultList;

	}
	
	@RequestMapping(value = "/isAnyUserAssigned", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	@Transactional
	public  int isAnyUserAssigned(ModelMap model, HttpServletRequest request){
		
		
		String roleName = request.getParameter("roleName");
		System.out.println("role name is--->"+roleName);
		String designation = request.getParameter("designation");
		System.out.println("designation is--->"+designation);
		
		List<?> resultList=businessRoleService.getRoleStatus(designation,roleName);
		
		System.out.println("list sizeeee is----->"+resultList.get(0));
	
	for(int i=0;i<resultList.size();i++) {
		
			if(resultList.get(i).equals(roleName)) 
	     {
			String qry = "Delete from meter_data.business_roles where role_name='"+roleName+"'";
			int n=  businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			return n;
         }
			else {
				System.out.println("Delete Operation No Need---->");
				return 0;
			}
			
	}
	
	return 0;
 	
	}

	@RequestMapping(value="/underDevelopment",method={RequestMethod.POST,RequestMethod.GET})
	public  String underDevelopment(ModelMap model,HttpServletRequest request)
	{
		return "underDevelopment";
	}


}

