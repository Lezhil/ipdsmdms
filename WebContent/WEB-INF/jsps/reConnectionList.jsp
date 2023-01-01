<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html><%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();			
		$('#MDMSideBarContents,#meterOper,#ondempro').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  

	
</script>
 <div class="page-content">
	<div class="row">
		<div class="col-md-12">
		
						 <div class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Reconnection Eligible List :
							<div class="radio-inline">
											<label>
											<input type="radio" name="optionsRadios" id="authorised" value="authorised" checked> 
											Authorised
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="aotu" value="auto" > 
											Auto
											</label>
										
										</div>
						 
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
						<form action="" id="actionId" method="POST">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
								
									<tr>
										<th>METER NO</th>
										<th>ACC NO</th>
										
										<th>NAME</th>
										<th>CONSUMER STATUS</th>
										<th>FEEDER CODE</th>
										<th>STATUS</th>
										<th>ACTION</th>
										
									</tr>
								</thead>
								
								<tbody>
								<c:forEach var="app" items="${result}">
								<tr>
								<td>${app.meterno}</td>
								<td>${app.accno}</td>
								<td>${app.name}</td>
								<td>${app.consumerStatus}</td>
								<td>${app.feedercode}</td>
								<td>Scheduled for Reconnection</td>
								
								<td> <button  class="btn blue pull-left" onclick="return reconnection('${app.meterno}')">Reconnect</button> </td>
								</tr>
								</c:forEach>
								</tbody>
							</table>
							</form>
						</div>
						
					</div>
						
		</div>
		</div>
		</div>
		 <c:if test = "${Success eq 'Reconnected Successfully'}">
		<script>
		
         alert("Meter Reconnected succesfully");
      
		</script>
		</c:if>
		
		<script>
function getStatus()
{
	 $("#statusId").attr("action","./getStatus");
		
} 
   
function reconnection(meterno)
{
	
	 $("#actionId").attr("action","./manualReconnection?meterno="+meterno);
		
}
</script>