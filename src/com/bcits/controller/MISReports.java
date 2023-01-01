package com.bcits.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.service.MasterService;
import com.crystaldecisions.sdk.occa.report.application.DataDefController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.FieldDisplayNameType;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

@Controller
public class MISReports
{
	
	@Autowired
	private MasterService masterService;
	
	@RequestMapping(value="/analyzedMeters",method={RequestMethod.GET,RequestMethod.POST})
	public String analyzedMeters(HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("MNP",masterService.getAllMNP());
		model.addAttribute("result", "abc");
		return "analyzedMeters";
	}
	
	
	@RequestMapping(value="/simpleReport",method={RequestMethod.GET,RequestMethod.POST})
	 public String simpleReport(HttpServletRequest request,ModelMap model)
	 {
		 System.out.println("+-----------simpleReport-----------------------");
		 HttpSession session = request.getSession();
		 SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd  hh:mm:ss");
		 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		 String reportFromDate = request.getParameter("reportFromDate");
		 String reportToDate = request.getParameter("reportToDate");
		 
		 
		 String reportName = request.getParameter("reportName");
		 String mnp = request.getParameter("mnp");
		 if(mnp.equalsIgnoreCase("all"))
		 {
			 mnp = "%";
		 }
		 String reportFromDate1 = "", reportToDate1 = "";
		 try{
		 Date d = sdf1.parse(reportFromDate);
		 reportFromDate1 = sdf.format(d);
		 Date d1 = sdf1.parse(reportToDate);
		 reportToDate1 = sdf.format(d1);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 System.out.println("-----mnp--------- : "+mnp + " : "+reportToDate1);
		 System.out.println("reportToDate--> "+reportToDate);
		 System.out.println("reportFromDate--> "+reportFromDate);
		 System.out.println("reportName--> "+reportName);
		 
		 session.setAttribute("reportFromDate", reportFromDate1);
		 session.setAttribute("reportToDate", reportToDate1);
		 session.setAttribute("reportName", reportName);
		 session.setAttribute("mnp1", mnp);
		 model.put("result", "reportGenerator");
		 model.addAttribute("MNP",masterService.getAllMNP());
		 return "analyzedMeters";
	 }
	
	@RequestMapping(value="/reportGenerate",method={RequestMethod.GET,RequestMethod.POST})
	 public String reportGenerate(HttpServletRequest request,ModelMap model,HttpServletResponse response)throws Exception
	 {
		 System.out.println("-------------------reportGenerate--------------");
		
			
		 return "reportGenerator";
	 }
	 
	 public static void addDiscreteParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object newValue) throws ReportSDKException{
	        DataDefController dataDefController = null;
	        if(reportName.equals(""))
	            dataDefController = clientDoc.getDataDefController();
	        else
	            dataDefController = clientDoc.getSubreportController().getSubreport(reportName).getDataDefController();

	        ParameterFieldDiscreteValue newDiscValue = new ParameterFieldDiscreteValue();
	        newDiscValue.setValue(newValue);

	        ParameterField paramField = (ParameterField)dataDefController.getDataDefinition().getParameterFields().findField(parameterName, FieldDisplayNameType.fieldName, Locale.getDefault());
	        boolean multiValue = paramField.getAllowMultiValue();

      if(multiValue) {
	            Values newVals = (Values)paramField.getCurrentValues().clone(true);
	            newVals.add(newDiscValue);
	            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName ,newVals);
	        } else {
	            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName , newValue);
	        }
	    }
}
