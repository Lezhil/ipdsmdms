
<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.application.ReportClientDocument,
	
	com.crystaldecisions.sdk.occa.report.data.Tables,
	com.crystaldecisions.sdk.occa.report.data.ITable,
	com.crystaldecisions.sdk.occa.report.application.ParameterFieldController,
	
	com.crystaldecisions.report.web.viewer.CrPrintMode"%>
	
	<%@page import="com.crystaldecisions.sdk.occa.report.lib.*" %>  
<%@page import="com.crystaldecisions.reports.sdk.*" %>  
<%@page
	import="java.sql.ResultSet,java.text.SimpleDateFormat,java.util.*"%>
<%@page import="com.bcits.controller.MISReports"%>

<script language="javaScript" type="text/javascript" src="./crystalreportviewers/js/crviewer/crv.js"></script>
<% 
try {
	
	
	 String reportName = (String)session.getAttribute("reportName");//"WEB-INF/reports/Energy_wise_report_D4.rpt";
	ReportClientDocument clientDoc = (ReportClientDocument) session
			.getAttribute(reportName);
	ParameterFieldController paramFieldController = null;
	

	if (clientDoc == null) {
		clientDoc = new ReportClientDocument();
		
		//clientDoc.setReportAppServer(ReportClientDocument.inprocConnectionString);

		// Open report
		
		clientDoc.open(reportName, 0);

		
		session.setAttribute(reportName, clientDoc);
	}
	
	
	 SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
	 
	 String fromDate = (String)session.getAttribute("reportFromDate");
	 String toDate = (String)session.getAttribute("reportToDate");
	 
	clientDoc.getDatabaseController().logon("MDAS","BCITS");
	MISReports.addDiscreteParameterValue(clientDoc, "","FROMDATE", format.parse(fromDate));
	

	MISReports.addDiscreteParameterValue(clientDoc, "","TODATE", format.parse(toDate));
	
	if(reportName.equalsIgnoreCase("WEB-INF/reports/EVENTWISE.rpt"))
	{
		
	}
	else if (reportName.equalsIgnoreCase("WEB-INF/reports/Tamper.rpt"))
	{
		
	}
	else{
		MISReports.addDiscreteParameterValue(clientDoc,"", "PrintedBy", "bcits");
	}
	

	
	CrystalReportViewer viewer2 = new CrystalReportViewer();
	viewer2.setOwnPage(true);
	viewer2.setBestFitPage(true);
	viewer2.setOwnForm(true);
	viewer2.setHeight(100);
	viewer2.setWidth(2000);
   viewer2.setDisplayPage(true); 
	
	viewer2.setPrintMode(CrPrintMode.ACTIVEX);

	//	set the reportsource property of the viewer
	viewer2.setReportSource(clientDoc.getReportSource());

	


	viewer2.processHttpRequest(request, response,
			request.getServletContext(),out);
				 
		
}
catch(Exception e)
{
	e.printStackTrace();
	%>
	
	<b>Error accured While Generating Report..</b>
	
<%	
}
%>