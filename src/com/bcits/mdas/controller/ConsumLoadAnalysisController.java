/**
 * 
 */
package com.bcits.mdas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.mdas.service.BoundaryDetailsService;
import com.bcits.mdas.service.FeederBoundaryDetailsService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;

/**
 * @author Tarik
 *
 */

@Controller
public class ConsumLoadAnalysisController {
	
	@Autowired 
	private FeederDetailsService feederdetailsservice;
	
	@Autowired 
	private FeederBoundaryDetailsService feederboundarydetailsservice;

	@Autowired 
	private MasterMainService masterMainService;
	
	@Autowired 
	private BoundaryDetailsService boundaryDetailsService;
	
	String msg = "";
	
	@RequestMapping(value="/consumLoadAnalys",method={RequestMethod.GET,RequestMethod.POST})
	public String consumLoadAnalys(HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		/*List<?> circleList=feederdetailsservice.getcircle();
		model.put("circleList", circleList);*/
		try {
		
			HttpSession session = request.getSession(false);
			if (session == null) {
				//return "redirect:/login";
				return "redirect:./?sessionVal=expired";
			}
		
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String userType = (String) session.getAttribute("userType");
		String qry = null;

		if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '"
					+ officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("division")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '"
					+ officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '"
					+ officeCode + "' ";
		}
		
		String SubdivName = null;
		
			if (officeType.equalsIgnoreCase("subdivision")) 
			SubdivName =(java.lang.String)feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();

			
		
		 model.put("msg", msg);
		 model.put("officeType",officeType);
         model.addAttribute("userType",userType);
		 
		 msg = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "consumLoadAnalys";
	}

}
