<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script>
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_5');
						//loadSearchAndFilter('sample_1');
						$('#nppData').addClass('start active ,selected');
						$(
								"#dash-board2,#installation,#liveDashboard,#mtrDtls,#360d-view,#meterData-Acquisition,#userAccMang,#onAirVerUpd,#modemMang,#MIS-Reports-photoBilling,#fdrOnMap,#calenderjsp,#installation,#meterChange,#totalModemComm,#updateMasterFdrStatus,#updateFeederType,#notCommDetails,#reasonforUnavailability,#updateMeterTypeTab,#cdfImport,#updateMeterTypeTab,#reports")
								.removeClass('start active ,selected');
					});

	$("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	});
</script>

<script>

	function generateReport() {
		var month = $('#month').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		
		if(month==null || month==''){
			bootbox.alert("Please select Month");
		} else if(fromDate==null || fromDate==''){
			bootbox.alert("Please select Start Billing Period.");
		} else if(toDate==null || toDate==''){
			bootbox.alert("Please select End Billing Period.");
		} else {
			$('#imageee').show();
			$.ajax({
				url : "./triggerNPPDataGeneration",
				type : "GET",
				data : {
					monthyear : month,
					fromDate : fromDate,
					toDate : toDate
				},
				dataType : 'TEXT',
				asynch : false,
				cache : false,
				success : function(data) {
					$('#imageee').hide();
					if(data=="Success"){
						bootbox.alert("Data Generated Successfully.")
					} else {
						bootbox.alert("OOPS! Something Went Wrong. Please Try again Later.")
					}
					
				}
			});

		}
	}

	function downloadReport() {

		var zone = $('#zone').val();
		var month = $('#month').val();
		$('#energyBody').empty();
		//alert(month);
		//$('#imageee').show();
		/* $.ajax({
			url : "./downloadNPPDataGeneration",
			type : "GET",
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				alert(data);
				
			}
		}); */

		window.open("./downloadNPPDataGeneration?monthyear="+month);

	}
</script>



<div class="page-content">
	<div class="row">
		<div class="col-md-12">


			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>NPP Data Generation
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">
						<div class="col-md-3">
							<h4>Select Month :</h4>
							<div class="input-group input-medium">
								<input type="text" class="form-control from" readonly id="month"
									name="month" placeholder="Select Month"> <span
									class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>

						<div class="col-md-3">
							<h4 >Start Billing period :</h4>
								<div class="input-group input-medium date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" style="text-align: initial;" placeholder="Select From Date" class="form-control"
										value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
										data-date="${currentDate}" data-date-format="yyyy-mm-dd"
										data-date-viewmode="years" name="fromDate" id="fromDate">
									
								</div>
						</div>

						<div class="col-md-3">
							<h4>End Billing period :</h4>
								<div class="input-group input-medium date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" style="text-align: initial;"
										placeholder="Select To Date" class="form-control"
										value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
										data-date="${currentDate}" data-date-format="yyyy-mm-dd"
										data-date-viewmode="years" name="toDate" id="toDate"/>
								
								</div>
						</div>
						<div class="col-md-3" style="margin-top: 38px">
							<!-- 	<td style="padding-left: 15px;"> -->
							<button onclick="return generateReport();"  class="btn yellow">
								<b>Generate</b>
							</button>
							
							<button onclick="return downloadReport();"  class="btn green">
								<b>Download</b>
							</button>
						</div>
					</div>

					<br>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<!-- <br>

					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport2"
									onclick="tableToExcel2('sample_5', 'Outage & Reliability Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_5">
						<thead>

							<tr>
								<th>Sl No</th>
								<th>State</th>
								<th>DISCOM</th>
								<th>Circle</th>
								<th>Division</th>
								<th>Substation</th>
								<th>Feeder Name</th>
								<th>Meter No</th>
								<th>Billing Date (H1)</th>
								<th>KWH</th>
								<th>KVAH</th>
								<th>KVA</th>
								<th>KW</th>
								<th>Billing Date (H2)</th>
								<th>KWH</th>
								<th>KVAH</th>
								<th>KVA</th>
								<th>KW</th>
								<th>Billing Date (H3)</th>
								<th>KWH</th>
								<th>KVAH</th>
								<th>KVA</th>
								<th>KW</th>

							</tr>
						</thead>
						<tbody id="energyBody">

						</tbody>
					</table> -->

				</div>
			</div>
		</div>



		<div class="modal fade" id="basic" tabindex="-1" role="basic"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1000px;">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #4b8cf8;">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">
							<font style="font-weight: bold; color: white;">DETAILS</font></a>
						</h4>
					</div>
					<div class="modal-body">
						<table class="table table-striped table-hover table-bordered"
							id="sample_1">
							<thead>
								<!-- <tr>
											<th>SL No</th>
											<th>Zone</th>
											<th>Circle</th>
											<th>Division</th>
											<th>Sub-Division</th>
											<th>Sub-Station</th>
											<th>Feeder Name</th>
											<th>Feeder Code</th>
											<th>Meter No</th>
											<th>IMEI No</th>
										</tr> -->
							</thead>
							<tbody id="modemMasterTbody">
								<tr>
									<td>Zone</td>
									<td id="mzone"></td>
								</tr>
								<tr>
									<td>Circle</td>
									<td id="mcircle"></td>
								</tr>
								<tr>
									<td>Division</td>
									<td id="mdiv"></td>
								</tr>
								<tr>
									<td>Sub-Division</td>
									<td id="msubdiv"></td>
								</tr>
								<tr>
									<td>Sub-Station</td>
									<td id="msubstn"></td>
								</tr>
								<tr>
									<td>Feeder Name</td>
									<td id="mfdrname"></td>
								</tr>
								<tr>
									<td>Feeder Code</td>
									<td id="mfdrcode"></td>
								</tr>
								<tr>
									<td>Village</td>
									<td id="mvill"></td>
								</tr>
								<tr>
									<td>Meter No.</td>
									<td id="mmtrno"></td>
								</tr>
							</tbody>
						</table>




					</div>
					<div class="modal-footer">
						<button type="button" class="btn dark btn-outline"
							data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>



	</div>

</div>

<style>
.table-scrollable {
	width: 100%;
	overflow-x: auto;
	overflow-y: hidden;
	border: 1px solid #dddddd;
	margin: 10px 0 !important;
}
</style>



<script>
	var startDate = new Date();
	var fechaFin = new Date();
	var FromEndDate = new Date();
	var ToEndDate = new Date();
	$('.from').datepicker({
		autoclose : true,
		minViewMode : 1,
		format : 'yyyymm'
	}).on(
			'changeDate',
			function(selected) {
				startDate = new Date(selected.date.valueOf());
				startDate.setDate(startDate.getDate(new Date(selected.date
						.valueOf())));

			});
</script>