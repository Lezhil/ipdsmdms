package com.bcits.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.ProcessSchedular;
import com.bcits.service.ProcessSchedularService;

@Controller
public class ProcessSchedularController {
	


	@Autowired
	private ProcessSchedularService processSchedularService;

	@RequestMapping(value = "/processScheduling", method = { RequestMethod.GET, RequestMethod.POST })
	public String processScheduling(HttpServletRequest request, ModelMap model) {
		// System.err.println("hello");
		List<?> list = processSchedularService.getSavedDataList();
		model.addAttribute("savedList", list);
		// System.out.println(list.size());
		return "processSchedular";

	}

	@RequestMapping(value = "/showProcessNameandId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showProcessNameandId(HttpServletRequest request, ModelMap model) {
		List<Object[]> list1 = null;
		List<Object[]> list2 = null;
		System.err.println("inside-");
		String process = request.getParameter("process");
		System.err.println(process);
		List<Object[]> array = new ArrayList<Object[]>();

		if (process.equals("Validation Type")) {
			// System.err.println("valiadtion types are------");
			list1 = (List<Object[]>) processSchedularService.getValidationData();
			array.addAll(list1);
		} else if (process.equals("Estimation Type")) {
			list2 = (List<Object[]>) processSchedularService.getEstimationData();
			array.addAll(list2);
		}

		return array;
	}

	@RequestMapping(value = "/savingProcessdata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> savingProcessdata(HttpServletRequest request, ModelMap model, HttpSession session) {
		//System.out.println("savingg!!!");
		String process = request.getParameter("process");
		String occtypeTime = request.getParameter("occtypeTime");
		String dateandTimevalue = request.getParameter("dateandTimevalue");
		String occuranceval = request.getParameter("occuranceval");
		String processName = request.getParameter("processName");
		// System.out.println(processName);
		String[] processNameandId = processName.split("-");
		String splitValueId = processNameandId[0];
		// System.err.println("splited
		// val---"+splitValueId+"st"+splitValueId.toString());
		String splitValueName = processNameandId[1];
		// System.err.println("splited
		// val---"+splitValueName+"st"+splitValueName.toString());
		// System.out.println(splitValueId+"---"+splitValueName);
		String userName = session.getAttribute("username").toString();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// System.out.println("time n name is:"+userName +timestamp);

		// System.err.println(process);
		ProcessSchedular processsch = new ProcessSchedular();
		processsch.setProcesstype(process);
		processsch.setOccurancetype(occtypeTime);
		processsch.setOccurancetime(dateandTimevalue);
		processsch.setRepeatedocctime(occuranceval);
		processsch.setProcessname(splitValueName);
		processsch.setProcessid(splitValueId);
		processsch.setCreatedby(userName);
		processsch.setCreateddate(timestamp);

		/*
		 * ObjectMapper o=new ObjectMapper(); try {
		 * System.out.println(o.writeValueAsString(processsch)); } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */
		processSchedularService.save(processsch);

		List<?> list = processSchedularService.getSavedDataList();
		// System.out.println(list.size());
		// model.addAttribute("savedList",list);
		return list;
	}

	@RequestMapping(value = "/editSavedData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> editSavedData(HttpServletRequest request, ModelMap model) {
		List<ProcessSchedular> res = new ArrayList<>();
		String id = request.getParameter("id");
		// System.err.println(id);
		// res=processSchedularService.getEditedDataList(id);
		ProcessSchedular ent = processSchedularService.getEntityById(Integer.parseInt(id));
		res.add(ent);
		return res;
	}

	// @Transactional
	@RequestMapping(value = "/updateProcessTable", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> updateProcessTable(HttpServletRequest request, ModelMap model, HttpSession session) {
		// System.out.println("shruthiiiiiii");
		// List<ProcessSchedular> res=new ArrayList<>();
		String id = request.getParameter("id");
		String process = request.getParameter("process");
		String occtypeTime = request.getParameter("occtypeTime");
		String dateandTimevalue = request.getParameter("dateandTimevalue");
		String occuranceval = request.getParameter("occuranceval");
		String processName = request.getParameter("processName");
		// System.err.println(id);

		// List li=processSchedularService.getDataList(id);
		String userName = session.getAttribute("username").toString();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ProcessSchedular schedular = processSchedularService.getEntityById(Integer.parseInt(id));

		if (schedular != null) {
			String[] processNameandId = processName.split("-");
			String splitValueId = processNameandId[0];
			String splitValueName = processNameandId[1];
			// res=processSchedularService.getEditedDataList(id);
			// ProcessSchedular processsch = schedular;
			schedular.setProcesstype(process);
			schedular.setOccurancetype(occtypeTime);
			schedular.setOccurancetime(dateandTimevalue);
			schedular.setRepeatedocctime(occuranceval);
			schedular.setProcessname(splitValueName);
			schedular.setProcessid(splitValueId);
			//schedular.setCreatedby(userName);
			//schedular.setCreateddate(timestamp);
			schedular.setUpdatedby(userName);
			schedular.setUpdateddate(timestamp);
			if("Once".equalsIgnoreCase(occtypeTime)) {
				schedular.setRepeatedocctime(null);
			}
			
			
			
			//ObjectMapper o = new ObjectMapper();
			try {
				// System.out.println(o.writeValueAsString(schedular));
				processSchedularService.customUpdate(schedular);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		List list = processSchedularService.getSavedDataList();

		return list;

	}

}
