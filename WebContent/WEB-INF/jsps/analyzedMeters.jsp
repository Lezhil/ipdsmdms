  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	 	 FormComponents.init();
   	    	   $('#MIS-Reports').addClass('start active ,selected');
   	    	   $("#admin-location,#dash-board").removeClass('start active ,selected');
   	    	     
   	    	   });
  function sendToReport() {
		
	   open(
				"./reportGenerate",
				"active",
				'width=440, height=440, toolbar=no, location=no, resizable=yes,scrollbars=yes, directories=no,status=no, titlebar=no');
//window.open("jsps/reportGenerator.jsp");
	}

  function validation()
  {
	  if(document.getElementById("reportFromDate").value == "" || document.getElementById("reportFromDate").value == null )
		  {
		    bootbox.alert('Please select From Date');
     	    return false;
		  }
	  
	  if(document.getElementById("reportToDate").value == "" || document.getElementById("reportToDate").value == null )
	  {
	    bootbox.alert('Please select To Date');
 	    return false;
	  }
	  
	  if(document.getElementById("reportName").value == "0" )
	  {
	    bootbox.alert('Please select Report');
 	    return false;
	  }
	  
	  return true;
  }
  
  </script>
<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>MIS Reports</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="./simpleReport">
							<table>
							<tr>
							<td>
							From Date : 
							</td>
							<td>
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							</tr>
							
							<tr>
							<td>
							To Date :
							</td>
							<td> 
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportToDate" id="reportToDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							</tr>
							
							<tr>
							<td>Select Report : 
							</td>
							<td>
							<select  id="reportName" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="reportName" >
							<option value="0">select</option>
							<option value="WEB-INF/reports/DATEWSIE.rpt">Date Wise Report</option>
							<option value="WEB-INF/reports/ENERGYWSIE.rpt">Energy Wise Report</option>
							<option value="WEB-INF/reports/EVENTWISE.rpt">Event Wise Report</option>
							<option value="WEB-INF/reports/LOADUTILIZATION.rpt">Load Utilization Report</option>
							<option value="WEB-INF/reports/POWERFACTOR.rpt">Power Factor Report</option>
							<option value="WEB-INF/reports/STATICCLASS.rpt">Static Class Report</option>
							<option value="WEB-INF/reports/Tamper.rpt">Tamper Report</option>
							<option value="WEB-INF/reports/TRANSACTION.rpt">Transaction Report</option>
							<option value="WEB-INF/reports/USAGEINDEX.rpt">Usage Index Report</option>
							<option value="WEB-INF/reports/WIRINGVERIFICATION.rpt">Wiring Verification Report</option>
							</select>
							</td>
							</tr>
							</table>
							<div class="modal-footer">
							<button class="btn blue pull-left" onclick="return validation()">Download Report</button>  
							
							</div>
							
							
							</form>								
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>


			
			<%-- <div  class="row">	
			<c:if test = "${result eq 'reportGenerator'}"> 		        
			<%@include file="/reportGenerator.jsp" %>
			</c:if>
			</div> --%>
			<c:if test = "${result eq 'reportGenerator'}"> 
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
			</c:if>

</div>