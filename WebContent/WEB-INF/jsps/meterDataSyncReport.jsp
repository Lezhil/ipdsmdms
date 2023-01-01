<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>


<%
	}
%>

<!-- <style>
.spantext{
margin:5px;
font-weight:600;

}
</style> -->
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						//Tasks.initDashboardWidget();
						FormComponents.init();
						loadSearchAndFilter('sample1');
						$("#reportId").val("").trigger("change");
						$('#ADMINSideBarContents,#meterSyncReport').addClass('start active ,selected');
						$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
					//	.removeClass('start active ,selected');				

					
					});
	
	

</script>
<div class="page-content">
 
	<div class="portlet box blue">

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Meter Data Sync Report
			</div>
		</div>

		<div class="portlet-body">
			
		<div>
		
		<table class="table table-striped table-hover table-bordered" id="table">
			<div class="col-md-9">
<!-- 					<label class="control-label">Report:</label>
 -->					<div id="circleTd" class="form-group">
						<select class="form-control select2me" id="reportId" name="reportId">
							<option value="">Select Report</option>
 							<!-- <option value="MAPPED METERS INTEGRATION STATUS FOR GENUS">TOTAL METERS INTEGRATION STATUS FOR GENUS WITH READ TIME</option> -->
							
							<option value="TOTAL METERS INTEGRATION STATUS FOR GENUS WITH TODAY COMMUNICATION TIME">TOTAL METERS INTEGRATION STATUS FOR GENUS WITH TODAY COMMUNICATION TIME</option>	
<!-- 							<option value="MAPPED METERS INTEGRATION STATUS FOR ANALOGICS">MAPPED METERS INTEGRATION STATUS FOR ANALOGICS</option>
 -->							<option value="TOTAL METERS INTEGRATION STATUS FOR ANALOGICS">TOTAL METERS INTEGRATION STATUS FOR ANALOGICS</option>	
											
						</select>
					</div>
				</div>
				
		<button type="button" id="buttonId" class="btn yellow"  onclick="reports()"><b>VIEW</b></button>
				

		</table>
		
		<div>		
		
		
		</div>
		
		
		</div>
		
		<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
		<br>
			<div class="row" id="meterSyncId">
				<div class="col-md-12">
					<div class="btn-group pull-right">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							Tools <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right">
							<!-- <li id="print"><a href="#">Print</a></li> -->
							<li><a href="#" id=""
								onclick="exportPDF('sample1','MeterSyncStatus Report')">Export to PDF</a></li>
								</ul>
							
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample1">

						<thead>
							<tr>
								<th>Sl No</th>	
								<th>Last Updated Instant</th>
								<th>Last Updated Load</th>
								<th>Last Updated Event</th>
								<th>Last Updated Bill</th>
								<th>Last Updated DailyLoad</th>
								<th>Last Initial Info</th>
								<th>Last NamePlate Info</th>
								<th>TOTAL METER COUNT</th>
								
								
							</tr>
						</thead>
						<tbody id="getReportValues">
						</tbody>
					</table>
					

				</div>
			</div>
			
		</div>
	</div>

</div>


<script>

function reports(){
	var reportId =$("#reportId").val();
   // alert(reportId);
	if (reportId == "") {
		bootbox.alert("Please Select Report Type");
		return false;
	}
	$('#meterSyncId').hide();
	$("#imageee").show();
	
	$
	.ajax({
		url : './getMeterSyncStatusReport',
		type : 'POST',
		
		
		data : {
			reportId : reportId,
				
		},
		dataType : 'json',
		
		
		success : function(response) {
			 $("#imageee").hide();
			 $('#meterSyncId').show();

						 
			  if (reportId == "TOTAL METERS INTEGRATION STATUS FOR GENUS WITH TODAY COMMUNICATION TIME") {

				 if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
				
							//alert(resp);
							var j = i + 1;
							html += "<tr>" 
								+ "<td>"+ j + "</td>"
								+ "<td>"+ ((resp.last_sync_inst == null) ? "Not-Communicating Today": moment(resp.last_sync_inst).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_load == null) ? "Not-Communicating Today": moment(resp.last_sync_load).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_event == null ) ? "Not-Communicating Today": moment(resp.last_sync_event).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_bill == null) ? "Not-Communicating Today": moment(resp.last_sync_bill).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								 + "<td>"+ ((resp.last_sync_dailyload == null) ? "Not-Communicating Today": moment(resp.last_sync_dailyload).format('YYYY-MM-DD HH:mm:ss')) + " </td>" 
								+ "<td>"+ ((resp.last_sync_meterinfo == null) ? "Not-Communicating Today": moment(resp.last_sync_meterinfo).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_nameplate == null) ? "Not-Communicating Today": moment(resp.last_sync_nameplate).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.meter_count == null) ? "0": resp.meter_count) + " </td>"; 
						}
						$('#sample1').dataTable().fnClearTable();
						$("#getReportValues").html(html);
						loadSearchAndFilter('sample1');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
				 
			 }	

			  else if (reportId == "TOTAL METERS INTEGRATION STATUS FOR ANALOGICS") {

				 if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
				
							//alert(resp);
							var j = i + 1;
							html += "<tr>" 
								+ "<td>"+ j + "</td>"		
								+ "<td>"+ ((resp.last_sync_inst == null) ? "Not-Communicating Today": moment(resp.last_sync_inst).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_load == null) ? "Not-Communicating Today": moment(resp.last_sync_load).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_event == null ) ? "Not-Communicating Today": moment(resp.last_sync_event).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_bill == null) ? "Not-Communicating Today": moment(resp.last_sync_bill).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								 + "<td>"+ ((resp.last_sync_dailyload == null) ? "Not-Communicating Today": moment(resp.last_sync_dailyload).format('YYYY-MM-DD HH:mm:ss')) + " </td>" 
								+ "<td>"+ ((resp.last_sync_meterinfo == null) ? "Not-Communicating Today": moment(resp.last_sync_meterinfo).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp.last_sync_nameplate == null) ? "Not-Communicating Today": moment(resp.last_sync_nameplate).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								 + "<td>"+ ((resp.meter_count == null) ? "0": resp.meter_count) + " </td>"; 
						
						}
						$('#sample1').dataTable().fnClearTable();
						$("#getReportValues").html(html);
						loadSearchAndFilter('sample1');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
					
			 }
		},
		
	});

}



</script>