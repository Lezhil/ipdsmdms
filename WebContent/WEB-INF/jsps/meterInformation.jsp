<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		$('#MDASSideBarContents,#meterOper,#statusUpdate').addClass('start active ,selected');
		$("#MDMSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  

	
</script>
 <div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body">
							
							<form action="" id="statusId" method="POST">
							<div class="portlet box blue">
								<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Meter Information:
						 
							</div>
							
						</div>
							</div>
							<table>

							<tr>
							
							<th>METER NO: </th>
								<td>
							
							<input type="text" class="form-control" name="meterNo" id="meterNo" autocomplete="off" >
							<input name = "action" type = "hidden" value ="connect"/>
							</td>
							
							
							</tr>
							
							
							</table>
							<div class="modal-footer">
							<button type="submit" class="btn blue pull-left" onclick="return getStatus()">Connection Status</button>  
							
							</div>
							</form>
													
						</div>
						 <div class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Meter Status for ${deviceId}:
						 
							</div>
							
						</div>
						
						
						<div class="portlet-body" id="excelUpload">
						<form action="" id="actionId" method="POST">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>	
									<tr>
										<th>METER NO</th>
										<th>CURRENT STATUS</th>
										
										<th>360D VIEW</th>
										<th>METER DETAILS</th>
										<th>ACTION</th>
										
									</tr>
								</thead>
								<tbody>
								<td>${deviceId} </td>
								<td>${status}</td>
								
							<%-- 	<td>${LastDisconn}</td>
								<td>${LastReconn}</td>
							 --%>
								
								
								
								<td><a  href="./viewFeederMeterInfoMDAS?mtrno=${deviceId}" style="color: blue; text-decoration: underline;">${deviceId}</a></td>
								<td><a  href="./mtrNoDetailsMDAS?mtrno=${deviceId}" style="color: blue; text-decoration: underline;">${deviceId}</a></td>
								
								
								
								<input name = "action" type = "hidden" value = "${action}"/>
								<input name = "meterNo" type = "hidden" value = "${deviceId}"/>
								<td> <button class="btn blue pull-left" onclick="return validation()">${action}</button> </td>
								
								</tbody>
							</table>
							</form>		
						</div>
						
					</div>
					 <div class="portlet box blue">
					<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Meter operations history ${deviceId}:
						 
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>	
									<tr>
										<th>METER NO</th>
										<th>ACCNO</th>
										<th>KNO</th>
										<th>FINAL READING</th>
										
										<th>DISCONNECTED DATE</th>
										<th>RECONNECTED DATE</th>
										
									</tr>
								</thead>
								<c:forEach var="tl" items='${meterData}'> 
								<tbody>
								
								<td>${tl[0]} </td>
								<td>${tl[1]}</td>
								<td>${tl[2]} </td>
								<td>${tl[4]} </td>
								
								<td>${tl[3]}</td>
							
							<td>${tl[5]} </td>
								
							
								</tbody>
									</c:forEach>
							</table>
							</div>
					</div>
					
				 
					
						 <c:if test = "${success eq 'disconnected'}">
		<script>
		
         alert("Meter disconnected succesfully");
      
		</script>
		</c:if>
		 <c:if test = "${success eq 'connected'}">
		<script>
		
         alert("Meter Reconnected succesfully");
      
		</script>
		</c:if>
		</div>
		</div>
		</div>
		 
		
		
		<script>
function getStatus()
{
	 $("#statusId").attr("action","./getStatus");
		
} 
function validation()
{
	 $("#actionId").attr("action","./changeStatus");
		
}
   
</script>