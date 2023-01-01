package com.bcits.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.entity.User;
import com.bcits.service.UserService;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private DataSource dataSource;

	@RequestMapping(value = "/", method = { RequestMethod.POST, RequestMethod.GET })
	public String index(ModelMap model, HttpServletRequest request) {

		String sessionValue = request.getParameter("sessionVal");

		model.put("msg", "notDisplay");
		if (sessionValue != null) {
			model.addAttribute("msg", "Your session has been Expired.<br> Please login again!");
		}
		model.put("user", new User());
		return "login";
	}

	private static String logType = "Office_Staff";

	int invSessionFlag=0;
	
	@RequestMapping(value="/homeNew",method = { RequestMethod.POST, RequestMethod.GET })
	public String homePageNew(HttpServletRequest request)
	{
		return "homePageNew";
	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public String login(@ModelAttribute User user, ModelMap model, HttpServletRequest request, HttpSession session) {
		String afterLoginPage = "";

		System.out.println("loginType-->" + request.getParameter("logintype"));
		String loginType = request.getParameter("logintype");

		if (loginType == null) {
			loginType = logType;
		}
		
		String username=user.getUsername();
		
		if(username!=null && !"".equals(username)) {
			System.out.println("usermname : " + username);
			if (loginType.equalsIgnoreCase("Office_Staff")) {
				// System.out.println("come inside office staff");
				afterLoginPage = userService.afterLogin(user.getUsername(), user.getUserPassword(), session, model);
			} else if (loginType.equalsIgnoreCase("Jvvnl_Staff")) {
				// System.out.println("come inside Jvvnl staff");
				System.out.println(user.getUsername() + "--" + user.getUserPassword());

				afterLoginPage = userService.jvvnlUserLogin(user.getUsername(), user.getUserPassword(), session, model);
			} else {
				System.out.println("Invalid LoginType");
			}
			model.put("user", new User());
			SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
			String billmonth = sdfBillDate.format(new Date());
			session.setAttribute("selectedMonth", billmonth);
			session.setAttribute("username", username);
			
			return afterLoginPage;
		} else {
			return "redirect:/";
		}
		
		
	}

	/*
	 * @RequestMapping(value="/logout",method=RequestMethod.GET) public String
	 * logout(ModelMap model,HttpServletRequest request) {
	 * 
	 * model.put("user", new User());
	 * 
	 * HttpSession session=request.getSession(); String
	 * usertype=(String)session.getAttribute("userType");
	 * 
	 * 
	 * session.invalidate();
	 * 
	 * 
	 * session.removeAttribute("userName"); session.removeAttribute("userType");
	 * session.removeAttribute("path"); session.removeAttribute("userId");
	 * session.removeAttribute("userNumber"); session.removeAttribute("userLevel");
	 * session.removeAttribute("userMailId"); //session.invalidate();
	 * 
	 * model.put("msg", "notDisplay"); return "login"; }
	 */

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest request) {

		model.put("user", new User());

		HttpSession session = request.getSession(false);
		session.removeAttribute("SelectedItem");
		session.removeAttribute("SelectedItem1");
		session.removeAttribute("SelectedItem3");
		session.removeAttribute("SelectedItem4");
		if (request.isRequestedSessionIdValid() && session != null) {
			session.invalidate();

		}

		/*
		 * session.removeAttribute("userName"); session.removeAttribute("userType");
		 * session.removeAttribute("path"); session.removeAttribute("userId");
		 * session.removeAttribute("userNumber"); session.removeAttribute("userLevel");
		 * session.removeAttribute("userMailId"); //session.invalidate();
		 */
		model.put("msg", "notDisplay");
		return "login";
	}

	@RequestMapping(value = "/loginBsmatMDM")
	public String loginMDM(ModelMap model, HttpServletRequest request) {
		return "loginMDM";
	}

}
