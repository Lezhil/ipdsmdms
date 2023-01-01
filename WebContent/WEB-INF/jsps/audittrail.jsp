<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableManaged.init();
						FormComponents.init();

						$('#MDMSideBarContents,#auditTrailsAMR,#dataValidId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');
					});
</script>
<script>
   
	function disableinput(locationType) {
		if (!("Consumer").localeCompare(locationType)) {
			//document.getElementById("accno").disabled = false;
			document.getElementById("assetId").disabled = true;
		} else {
			//document.getElementById("accno").disabled = true;
			document.getElementById("assetId").disabled = false;
		}
	}
	function  checkMtrLocType(mtrNum){
		var locationType = $('#vllocType').val();
		
		$.ajax({
			type:'get',
			url:'./findmeterlocation',
			data:{
				mtrNum:mtrNum,
				locationType : locationType
			},
			success:function(res){
				if(res==false){
					$("#mtrno").val("");
					//$('#vllocType').clear();
					//bootbox.alert("Invalid Meter Location Type Of This MeterNumber");
					 $('#error').show();
					document.getElementById('error').innerHTML="Invalid&nbsp;Location&nbsp;Type&nbsp;Of&nbsp;this&nbsp;Meter&nbsp;Number";
					document.getElementById('error').style.color="red"; 
				//	mtrLocRes=false;
				}	
				 else
					{
					// $("#mtrno").val("");	
					$('#error').hide();
				//	mtrLocRes=true;
					} 
			}
		});
		//return result;
	}

function  checkMtrLocType1(mtrNum){
	    var mtrLocRes=true;
		var locationType = $('#vllocType').val();
		
		$.ajax({
			type:'get',
			url:'./findmeterlocation',
			data:{
				mtrNum:mtrNum,
				locationType : locationType
			},
			success:function(res){
				if(res==false){
					 $("#mtrno").val("");
					 $('#error').show();
					document.getElementById('error').innerHTML="Invalid&nbsp;Location&nbsp;Type&nbsp;Of&nbsp;this&nbsp;Meter&nbsp;Number";
					document.getElementById('error').style.color="red"; 
					mtrLocRes=false;
				}	
				 else
					{
					//$("#mtrno").val("");
					$('#error').hide();
					mtrLocRes=true;
					} 
			}
		});
	  return mtrLocRes;
	}
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body">
				<div class="portlet box blue">

					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Audit Trails:

						</div>

					</div>
				<div class="portlet-body">
				<form action="#">
					<table>
						<tr>
							<th id="vlloc" class="block">Location&nbsp;Type:<span style="color: red">*</span></th>
							<th id="loctype"><select
								class="form-control select2me input-medium" id="vllocType"
								name="vllocType">
<!-- 								name="vllocType" onchange="disableinput(this.value)"> -->
									<option value=""></option>
									<!-- <option value="Consumer">Consumer</option> -->
									<option value="feeder">FEEDER</option>
									<option value="dt">DT</option>
									<option value="boundary">BOUNDARY</option>
									
							</select><!-- <div id="error"></div> --></th>
							<!-- <th class="block">Account&nbsp;Number:</th>
							<th id="accnumTd"><input class="form-control input-medium"
								name="accno" id="accno" /></th> -->
							<c:set var="loc" scope="session" value="'${vllocType}'" />

							<%-- <th class="block">Asset Id&nbsp;:</th>
							<c:if test="${loc not eq 'Consumer' }"></c:if>
							<th id="assetTd"><input class="form-control input-medium"
								name="assetId" id="assetId" /></th> --%>
								<th class="block">MeterSr&nbsp;Number:<span style="color: red">*</span></th>
							<th id="mtrnoTd"><input class="form-control input-medium"
								name="mtrno" id="mtrno" onchange="checkMtrLocType(this.value);" />
								<span class="error" id="error"></span></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							
								

							<th class="block">From Date<span style="color: red">*</span></th>
							<td>
								<div class="input-group date date-picker"
									data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
									<input type="text" class="form-control" name="reportFromDate" style="cursor: pointer"
										id="reportFromDate"> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
							<th class="block">To Date<span style="color: red">*</span></th>
							<td>
								<div class="input-group date date-picker"
									data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
									<input type="text" class="form-control" name="reportToDate" style="cursor: pointer"
										id="reportToDate"> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
							<th id="audit" class="block">Transaction&nbsp;Type:</th>
							<th id="auditTd"><select
								class="form-control select2me input-medium" id="auditType"name="auditType">
									<option value=""></option>
<!-- 									<option value="config">Meter Configuration</option>
 -->									<option value="acquisition">Data Acquisition</option>
									<option value="validation">Data Validation</option>
									<option value="estimation">Data Estimation</option>
							</select></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>

						<tr>

							<!-- <th id="error"></th> -->
							
						<td colspan="2"></td>
							<td>
								<button type="button" id="locDetails" style="margin-left: 20px;"
									onclick="return getLocDetails()" name="locDetails"
									class="btn yellow">
									<b>View</b>
								</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
							</td>
						</tr>
					</table>
				</form>
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>

				<div class="modal-body">

					<div class="tabbable tabbable-custom tabbable-full-width">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1_2" data-toggle="tab">Location
									Details</a></li>

							<li><a href="#tab_1_8" onclick="return getauditTrails();"
								data-toggle="tab">Audit Trail Details</a></li>


						</ul>
						<div class="tab-content">

							<!--tab_1_2-->
							<div class="tab-pane active" id="tab_1_2">



								<div class="box">
									<div class="tab-content">
										<div id="tab_1-1" class="tab-pane active">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv6">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">

																<li><a href="#" id="excelExport6"
																	onclick="tableToExcel6('sample_6', 'Location Details')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
												</div>
												<table
													class="table table-striped table-hover table-bordered"
													id="sample_6">

													<tbody id=consumerDetails>

													</tbody>
												</table>

											</div>
											<!-- 										<div id="chartContainer" style="height: 300px; width: 100%;"></div>
 -->
											<!-- 										<div hidden="true" id="graphId"></div>
 -->
										</div>
									</div>
									<!--  <div id="chartContainer" style="height: 370px; width: 100%;"></div> -->
								</div>
							</div>

							<div class="tab-pane" id="tab_1_3">

								<div class="box">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div align="right">
														<button type="button" id="printChart" class="btn green">

															<b>Print</b>
														</button>
													</div>

												</div>

											</div>
										</form>
									</div>

								</div>
							</div>




							<div class="tab-pane" id="tab_1_8">

								<div class="box">
									<div class="tab-content">
										<div id="tab_1-1" class="tab-pane active">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv6">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">

																<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_8', 'Audit Trails')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
												</div>
												<table
													class="table table-striped table-hover table-bordered"
													id="sample_8">
													<thead>
														<tr>
															<th>Meter Serial No</th>
															<th>Transaction Type</th>
															<th>Transaction Date</th>
															<th>Transaction Status</th>
															<th>Status</th>
														</tr>
													</thead>
													<tbody id=auditDetails>

													</tbody>
												</table>

											</div>
											<!-- 										<div id="chartContainer" style="height: 300px; width: 100%;"></div>
 -->
											<!-- 										<div hidden="true" id="graphId"></div>
 -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>




				</div>
				</div>
				</div>


				<!-- endddd -->
			</div>


		</div>
	</div>
</div>
<div id="stack6" class="modal fade" tabindex="-1" data-width="400"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog" style="width: 90%;">

		<div class="modal-content">

			<div class="modal-header">
				<div id="image" hidden="true"
					style="text-align: center; height: 100%; width: 100%;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h3 id="loadingText">
						<font id="masterTd">Loading..... Please wait.</font>
					</h3>
				</div>
				<div id="closeShow" hidden="true">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="-title">
						<b>Audit Details</b>

					</h4>
				</div>
			</div>

			<div class="modal-body" id="closeShow1" style="display: none;">

				<br />
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a id="print">Print</a></li> -->
								<li><a id="excelExport1"
									onclick="tableToExcel1('sample_4', 'Audit Details')">Export
										to Excel</a></li>
							</ul>
						</div>
						<div id="mrDiv">
							<table class="table table-striped table-hover table-bordered"
								id="sample_4">

								<thead id="sample_4_head">
								</thead>

								<tbody id="sample_4_body">
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>
</div>



<script>
	function getLocDetails() {
		$('#consumerDetails').empty();
		var locurl='';
		$('[href="#tab_1_2"]').tab('show');
		meterNum = $("#mtrno").val();
		var accountNumber=$('#accno').val();
		var fromDate = $('#reportFromDate').val();
		var toDate = $('#reportToDate').val();
		var locationType = $('#vllocType').val();
		var assetId = $('#assetId').val();
		var crossDtrFdr='';
		var status=true;
		
		
		const date1 = new Date(fromDate);
		const date2 = new Date(toDate);
		const diffTime = Math.abs(date2.getTime() - date1.getTime());
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		//alert(assetId);
		if(locationType==""){
			bootbox.alert("Please Enter Location Type");
			return false;
		}
		/* if(meterNum=="" && accountNumber =="" && assetId==""){
			
			if(!("Consumer").localeCompare(locationType)){
			bootbox.alert("Please Enter Meter Number or Account Number");
			}
			else{ 
			bootbox.alert("Please Enter Meter Number or Asset Id");
			}
			return false;
		} */
		 if(meterNum==""){
				bootbox.alert("Please Enter Meter Number");
				return false;
			} 
		/* if(mtrLocRes==false){
			bootbox.alert("Invalid Location Type Of this Meter Number");
			return false;
		} */
		
		if(fromDate==""){
			bootbox.alert("Please Enter from Date");
			return false;
		}
		if(toDate==""){
			bootbox.alert("Please Enter to Date");
			return false;
		}
		if(new Date(fromDate)>new Date(toDate))
		{
			bootbox.alert("Please Select Correct Date Range");
			return false;
		}
		if(diffDays>30){
			bootbox.alert("Difference Between Date Should be less than 30 days ");
			return false;
		}
		if (assetId==""){
			status=checkMtrLocType1(meterNum);
		}
		
		if(status==false){
			bootbox.alert("Invalid Location Type Of this Meter Number");
			return false;
		}else{ 
		$('#error').hide();
		if(!("Consumer").localeCompare(locationType)){
			locurl='./getConsumerLocationData';
			
		}
		else if((!("feeder").localeCompare(locationType))||("boundary"==locationType)){
			locurl='./getFeederLocationData'
		}
		else if(!("dt").localeCompare(locationType)){
			locurl='./getDtLocationData';
		}
		$('#imageee').show();
		$.ajax({
			url : locurl,
			type : 'GET',
			dataType:'json',
            asynch:false,
            cache:false,
            data : {
            	meterNum : meterNum,
            	accountNumber : accountNumber,
            	assetId : assetId
            },
			success : function(response) {
				//alert(response);
				$('#imageee').hide();
				if(response.length !=0){
				var html = '';
				for (var i = 0; i < response.length; i++) {
					var data = response[i];
					//console.log("date....."+data+"   " + response[i][0]);

					if(!("Consumer").localeCompare(locationType)){
						$("#mtrno").val(data[5]);
					html += '<tr>'
							+ '<tr><th>subdivision</th><td>'
							+ (data[0]== null ? "" : data[0])
							+ '</td></tr>'
							+ '<tr><th>Consumer Category</th><td>'+ (data[1] == null ? "" : data[1]) + '</td></tr>'
							+ '<tr><th>Consumer Name</th><td>'+( data[2]== null ? "" : data[2]) + '</td></tr>'
							+ '<tr><th>Consumer Account Number</th><td>' +( data[3]== null ? "" : data[3])
							+ '</td></tr>'
							+ '<tr><th>Consumer k Number</th><td>'
							+ (data[4]== null ? "" : data[4]) + '</td></tr>'
							+ '<tr><th>Meter Serial Number</th><td>' + (data[5]== null ? "" : data[5])
							+ '</td></tr>'
					/* +'<tr><th>Phase</th><td>'+data.phase+'<td><tr>' */;
				}
					else if((!("feeder").localeCompare(locationType))){
					//	alert("data"+data[4]);
						
						if (data[2] == 1) {
							crossDtrFdr = "Yes";
						} else {
							crossDtrFdr = "No";
						}
						$("#mtrno").val(data[4]);
						html += '<tr>'
							+ '<tr><th>subdivision</th><td>'
							+ (data[0]== null ? "" : data[0])
							+ '</td></tr>'
							+ '<tr><th>Feeder Name </th><td>'+ (data[1] == null ? "" : data[1]) + '</td></tr>'
							/* + '<tr><th>Cross Feeder</th><td>'+ (data[2]== null ? "" : crossDtrFdr) + '</td></tr>' */
							+ '<tr><th>Feeder Code</th><td>' + (data[3]== null ? "" : data[3])
							+ '</td></tr>'
							+ '<tr><th>Meter Serial Number</th><td>' + (data[4]== null ? "" : data[4])
							+ '</td></tr>'
							break;
						
					}

					else if(locationType=="boundary"){
						//	alert("data"+data[4]);
							
							if (data[2] == 1) {
								crossDtrFdr = "Yes";
							} else {
								crossDtrFdr = "No";
							}
							$("#mtrno").val(data[4]);
							html += '<tr>'
								+ '<tr><th>subdivision</th><td>'
								+ (data[0]== null ? "" : data[0])
								+ '</td></tr>'
								+ '<tr><th>Boundary Name </th><td>'+ (data[1] == null ? "" : data[1]) + '</td></tr>'
								/* + '<tr><th>Cross Feeder</th><td>'+ (data[2]== null ? "" : crossDtrFdr) + '</td></tr>' */
								+ '<tr><th>Boundary Code</th><td>' + (data[3]== null ? "" : data[3])
								+ '</td></tr>'
								+ '<tr><th>Meter Serial Number</th><td>' + (data[4]== null ? "" : data[4])
								+ '</td></tr>'
								break;
							
						}
					else if(!("dt").localeCompare(locationType)){
						if (data[2] == 1) {
							crossDtrFdr = "Yes";
						} else {
							crossDtrFdr = "No";
						}
						$("#mtrno").val(data[4]);
						html += '<tr>'
							+ '<tr><th>subdivision</th><td>'+ (data[0]==null? "":data[0])+ '</td></tr>'+ 
						      '<tr><th>Dt Name</th><td>'+ (data[1] == null ? "" : data[1]) + '</td></tr>'
							+ '<tr><th>Dt Capacity</th><td>'+ (data[6]==null? "":data[6]) + '</td></tr>'
							+ '<tr><th>Cross Dt</th><td>' + (data[2]==null? "":crossDtrFdr)
							+ '</td></tr>'
							+ '<tr><th>Dt Code</th><td>'
							+ (data[3]==null? "":data[3]) + '</td></tr>'
							+ '<tr><th>Meter Serial Number</th><td>' + (data[4]==null? "":data[4])
							+ '</td></tr>'
							break;
							
					}
				}
				}else{
					bootbox.alert("No Data Found Or Meter is of not of specified location type ");
				}
				$("#consumerDetails").html(html);
			}

		});}
	}
	function getauditTrails() {
		var locationType = $('#vllocType').val();
		var accNum = $('#accno').val();
		var assetId = $('#assetId').val();
		var mtrNum = $('#mtrno').val();
		var fromDate = $('#reportFromDate').val();
		var toDate = $('#reportToDate').val();
		var auditType = $('#auditType').val();
		var accountNumber=$('#accno').val();
		//to calcluate number of days
		const date1 = new Date(fromDate);
		const date2 = new Date(toDate);
		const diffTime = Math.abs(date2.getTime() - date1.getTime());
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		
		if(locationType==""){
			bootbox.alert("Please Enter Location Type");
			return false;
		}
		/* if(meterNum=="" && accountNumber =="" && assetId==""){
			if(!("Consumer").localeCompare(locationType)){
			bootbox.alert("Please Enter Meter Number or Account Number");
			}
			else{ 
			bootbox.alert("Please Enter Meter Number or Asset Id");
			}
			return false;
		} */
		 if(meterNum==""){
			bootbox.alert("Please Enter Meter Number");
			return false;
		} 
		if(fromDate==""){
			bootbox.alert("Please Enter from Date");
			return false;
		}
		if(toDate==""){
			bootbox.alert("Please Enter to Date");
			return false;
		}

		if(new Date(fromDate)>new Date(toDate))
		{
			bootbox.alert("Please Select Correct Date Range");
			return false;
		}
		if(diffDays>30){
			bootbox.alert("Difference Between Date Should be less than 30 days ");
			return false;
		}
		
		
		//$(".table table-striped table-hover table-bordered").html("");
		/*  $('#stack6').remove();
		 var myClone = originalModal.clone();
		    $('body').append(myClone); */

		$('#stack6').on('hidden.bs.modal', function() {

			$(this).removeData('bs.modal');

			$(this).find('form').trigger('reset');
		});
		console.log(auditType + "ss");
		if (!("config").localeCompare(auditType)) {
			getMtrConfigurations(locationType, mtrNum, fromDate, toDate);
		} else if (!("acquisition").localeCompare(auditType)) {
			getDataAquisituionDetails(locationType, mtrNum, fromDate, toDate);
		} else if (!("validation").localeCompare(auditType)) {
			getValidationAuditTrails(locationType, mtrNum, fromDate, toDate,
					accNum);

		}
		//else{
		else if (!("estimation").localeCompare(auditType)) {
			getEstimationAuditTrails(locationType, mtrNum, fromDate, toDate);
		}

	}

	function getMtrConfigurations(locationType, mtrNum, fromDate, toDate) {

		//$('#sample_4 tr').empty();

		var trType = "Meter Configuration";
		var html = '';
		var status = '';
		$
				.ajax({
					url : './getAuditDetails',
					type : 'GET',
					dataType : 'json',
					data : {
						locationType : locationType,
						mtrNum : mtrNum,
						fromDate : fromDate,
						toDate : toDate
					},
					asynch : false,
					cache : false,
					success : function(response) {
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							/* if (element[3] == 201) {
								status = "Success";
							}

							else {
								status = "Fail";
							} */
							var sdate=moment(element[2]).format('DD-MM-YYYY HH:mm:ss');
							html += "<tr>" + "<td>"
									+ mtrNum
									+ "</td>"
									+ "<td>"
									+ trType
									+ "</td>"
									+ "<td>"
									+ (element[2] == null ? "" : new Date(Number(element[2])).toLocaleString())
									+ "</td>"
									+ "<td>"
									+ element[1]
									+ "</td>"
									+ '<td><a href="#" onclick="viewAuditdetails(this.id,\''
									+ element[0]
									+ '\',\''
									+ element[1]
									+ '\',\''
									+ sdate
									+ '\',\''
									+ element[3]
									+ '\',\''
									+ element[4]
									+ '\',\''
									+ element[5]
									+ '\',\''
									+ element[6]
									+ '\');" data-toggle="modal" data-target="#stack6" id="'
									+ mtrNum + '">' + "view";

						}
						$('#sample_8').dataTable().fnClearTable();
						$("#auditDetails").html(html);
						loadSearchAndFilter('sample_8');
					}

				});
	}

	function getDataAquisituionDetails(locationType, mtrNum, fromDate, toDate) {

		//$('#sample_4 tr').empty();
		var trType = "Data Acquisition";
		var html = '';
		$
				.ajax({
					url : './getDataAquistion',
					type : 'GET',
					dataType : 'json',
					data : {
						locationType : locationType,
						mtrNum : mtrNum,
						fromDate : fromDate,
						toDate : toDate
					},
					asynch : false,
					cache : false,
					success : function(response) {
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							if (element[3] == 0) {
								status = "SUCCESS";
							}

							else {
								status = "FAIL";
							}
							html += "<tr>"
									+ "<td>"
									+ (element[1] == null ? mtrNum : element[1])
									+ "</td>"
									+ "<td>"
									+ trType
									+ "</td>"
									+ "<td>"
									+ (element[0] == null ? "" : moment(element[0]).format('DD-MM-YYYY'))
									+ "</td>"
									+ "<td>"
									+ status
									+ "</td>";
									if(!'SUCCESS'.localeCompare(status)){
									html +=  '<td><a href="#" onclick="viewDataAcqetails(this.id,\''
									+ element[1]
									+ '\',\''
									+ element[0]
									+ '\');" data-toggle="modal" data-target="#stack6" id="'
									+ mtrNum + '">' + "view";
									}
									else{
										html += '<td>Meter has not communicated </td>';
										}

						}
						$('#sample_8').dataTable().fnClearTable();
						$("#auditDetails").html(html);
						loadSearchAndFilter('sample_8');
					}

				});
	}

	function getValidationAuditTrails(locationType, mtrNum, fromDate, toDate,
			accNum) {
		//$('#sample_4 tr').empty();
		var createdDate='';
		var trType = "Validation";
		var html = '';
		$
				.ajax({
					url : './getValidationAuditData/' + locationType + '/'
							+ mtrNum + '/' + fromDate + '/' + toDate,
					type : 'GET',
					dataType : 'json',
					/* data : {
						locationType : locationType,
						mtrNum : mtrNum,
						fromDate : fromDate,
						toDate : toDate
					}, */
					asynch : false,
					cache : false,
					success : function(response) {
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							if(element[12] == null){
								//alert("Data0=="+element[0]);
								createdDate=moment(element[0]).format('DD-MM-YYYY HH:mm:ss');
								//createdDate=element[0];
							}
							else{
								//alert("Data12=="+element[12]);
								createdDate=moment(element[12]).format('DD-MM-YYYY HH:mm:ss');
								//alert("createdDate=="+createdDate);
									//element[12];
							}

							html += "<tr>"
									+ "<td>"
									+ (element[3] == null ? mtrNum : element[3])
									+ "</td>"
									+ "<td>"
									+ trType
									+ "</td>"
									+ "<td>"
									+ (element[12] == null ? new Date(Number(element[0])).toLocaleString() : new Date(Number(element[12])).toLocaleString())
									+ "</td>"
									+ "<td>"
									+ element[11]
									+ "</td>"
									if(!'SUCCESS'.localeCompare(element[11])){
										html +=  '<td>Validation has run successfully</td>';
										}
									else{
										html += '<td><a href="#" onclick="viewValidationData(this.id,\''
										+ element[1]
										+ '\',\''
										+ createdDate
										+ '\');" data-toggle="modal" data-target="#stack6" id="'
										+ mtrNum + '">' + "view";
										}
								

						}
						$('#sample_8').dataTable().fnClearTable();
						$("#auditDetails").html(html);
						loadSearchAndFilter('sample_8');
					}

				});

	}
	function getEstimationAuditTrails(locationType, mtrNum, fromDate, toDate) {

		//$('#sample_4 tr').empty();
		var trType = "Estimation";
		var html = '';
		$
				.ajax({
					url : './getEstimationAuditData',
					type : 'GET',
					dataType : 'json',
					data : {
						locationType : locationType,
						mtrNum : mtrNum,
						fromDate : fromDate,
						toDate : toDate
					},
					asynch : false,
					cache : false,
					success : function(response) {
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							var formatedDate='';
							if(element[2]==null){
								formatedDate=moment(element[0]).format('DD-MM-YYYY hh:mm:ss');
							}
							else{
							 formatedDate= moment(element[2]).format('YYYY-MM-DD hh:mm:ss');
							}
							html += "<tr>"
									+ "<td>"
									+ (element[1] == null ? mtrNum : element[1])
									+ "</td>"
									+ "<td>"
									+ trType
									+ "</td>"
									+ "<td>"
									+ (element[2] == null ? new Date(Number(element[0])).toLocaleString() : new Date(Number(element[2])).toLocaleString())
									+ "</td>"
									+ "<td>"
									+ element[3]
									+ "</td>";
									if(!'SUCCESS'.localeCompare(element[3])){
										html +=  '<td>Estimation has run successfully</td>';
									}
									else{
										html +=  '<td><a href="#" onclick="viewEstimationData(this.id,\''
										+ element[1]
										+ '\',\''
										+ formatedDate
										+ '\');" data-toggle="modal" data-target="#stack6" id="'
										+ mtrNum + '">' + "view";
										}

						}
						$('#sample_8').dataTable().fnClearTable();
						$("#auditDetails").html(html);
						loadSearchAndFilter('sample_8');
					}

				});

	}
	function viewAuditdetails(id, groupName, groupStatus, date, jobName,
			jobType, jobStatus, jobCreaStatus) {
		$('#closeShow').show();
		$('#closeShow1').show();
		$('#excelExport').show();
		var html2 = '';
		html2 = "<tr style='background-color: lightgray'><th>Meter Number</th><th>Group Name</th><th>Group Status</th><th>date</th> <th>Job Name</th><th>Job Type</th><th>Job Status </th><th> job Created Status</th></tr>";
		var html1 = "";
		//html1 = "<tbody>";
		//for (var i = 0; i < response.length; i++) {
		html1 += "<tr>";
		html1 += "<td>" + id + "</th>" + "<td>" + groupName + "</td>" + "<td>"
				+ groupStatus + "</td>" + "<td>" +date + "</td>" + "<td>"
				+ jobName + "</td>" + "<td>" + jobType + "</td>" + "<td>"
				+ jobStatus + "</td>" + "<td>" + jobCreaStatus + "</td>";

		html1 += "</tr>";
		$('#sample_4_head').empty();
		$('#sample_4').dataTable().fnClearTable();
		$('#sample_4_head').html(html2);
		$('#sample_4_body').html(html1);
		loadSearchAndFilter('sample_4');
		$('#closeShow').show();
		$('#closeShow1').show();
		$('#excelExport').show();
		
	}

	function viewDataAcqetails(id, mtrNum, date) {
		//$("#sample_4").html("");
		//$('#sample_4 th').empty();new Date(Number(resp.entry_date)).toLocaleString()

      // alert(moment(new Date(Number(date)).toLocaleString().format('DD-MM-YYYY')));
     // date.parse('DD-MM-YYYY');
    //  var d = new Date(date.parse('DD-MM-YYYY'));
     //  DateTime date = new DateTime(long.Parse(date));
     // moment(new Date(Number(date)).format('DD-MM-YYYY')1559327400000
  //    alert(moment(Number(date)).format('YYYY-MM-DD'));
      

		$.ajax({
					url : './getMtrCommData/' + mtrNum + '/' + moment(Number(date)).format('YYYY-MM-DD'),
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					complete : function() {
						//$('#image').hide();
						$('#closeShow').show();
						$('#closeShow1').show();
						$('#excelExport').show();
					},
					success : function(response) {

						var html2 = '';
						html2 = "<tr style='background-color: lightgray'><th>Sl NO</th><th>Meter Number</th><th>Date</th> <th>last_communication</th></tr>";
						var html1 = "";
						//html1 = "<tbody>";

						for (var i = 0; i < response.length; i++) {

							html1 = "<tr>";
							html1 += "<td>"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ response[i][0]
									+ "</td>"
									/* + "<td>"
									+ ((response[i][1]) == null ? ""
											: response[i][1])
									+ "</td>" */
									+ "<td>"
									+ response[i][2]
									+ "</td>"
									+ "<td>"
									+ (response[i][3] == null ? "" : new Date(Number(response[i][3])).toLocaleString()) + "</td>";
							html1 += "</tr>";
						}
						$('#sample_4_head').empty();
						//$('#sample_4_body').html('');
						$('#sample_4').dataTable().fnClearTable();
						//$('#sample_4 th').empty();
						//$('#sample_4').empty();
						$('#sample_4_head').html(html2);
						$('#sample_4_body').html(html1);
						loadSearchAndFilter('sample_4');
					}
				});

		$('#closeShow').show();
		$('#closeShow1').show();
		$('#excelExport').show();
	}
	function viewValidationData(id, mtrNum, date) {
		//$('#sample_4 th').empty();
		//$("#sample_4").html("");
		// $('#sample_4').dataTable().fnClearTable();
		$.ajax({

					url : './getSingleValidationData/' + id + '/' + date,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					complete : function() {
						//$('#image').hide();
						$('#closeShow').show();
						$('#closeShow1').show();
						$('#excelExport').show();
					},
					success : function(response) {

						var html2 = '';
						html2 = "<tr style='background-color: lightgray'><th>Sl NO</th><th>V Rule Id</th><th>V Rule Name</th><th>meter_number</th> <th>location Type</th><th>Location Id</th><th>location Name</th><th>zone</th><th>circle</th><th>division</th><th>subdivision</th></tr>";
						var html1 = "";
						//html1 = "<tbody>";
						for (var i = 0; i < response.length; i++) {
							html1 += "<tr>";
							html1 += "<td>"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ ((response[i][0]) == null ? "":response[i][0])
									+ "</td>"
									+ "<td>"
									+ ((response[i][1]) == null ? ""
											: response[i][1])
									+ "</td>"
									+ "<td>"
									+ ((response[i][2]) == null ? "":response[i][2])
									+ "</td>"
									+ "<td>"
									+ (response[i][3] == null ? ""
											: response[i][3]) + "</td>"
									+ "<td>" +((response[i][4]) == null ? "": response[i][4]) + "</td>"
									+ "<td>" + ((response[i][5]) == null ? "":response[i][5] )+ "</td>"
									+ "<td>" + ((response[i][6]) == null ? "":response[i][6] )+ "</td>"
									+ "<td>" +((response[i][7]) == null ? "": response[i][7] )+ "</td>"
									+ "<td>" + ((response[i][8]) == null ? "":response[i][8]) + "</td>"
									+ "<td>" + ((response[i][9]) == null ? "":response[i][9]) + "</td>";
							html1 += "</tr>";
						}
						$('#sample_4_head').empty();
						//$('#sample_4_body').html('');
						$('#sample_4').dataTable().fnClearTable();
						//$('#sample_4 th').empty();
						//$('#sample_4').empty();
						$('#sample_4_head').html(html2);
						$('#sample_4_body').html(html1);
						loadSearchAndFilter('sample_4');
					}
				});

		$('#closeShow').show();
		$('#closeShow1').show();
		$('#excelExport').show();
	}
	function viewEstimationData(id, mtrNum, date) {
		//$("#sample_4").html("");
		var html2 = '';
		$
				.ajax({

					url : './getSingleEstimationData/' + id + '/' + date,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					complete : function() {
						//$('#image').hide();
						$('#closeShow').show();
						$('#closeShow1').show();
						$('#excelExport').show();
					},
					success : function(response) {

						 html2 = '';
						html2 = "<tr style='background-color: lightgray'><th>Sl NO</th><th>Rule Id</th><th>Rule Name</th><th>meter_number</th><th>Estimated Date</th> <th>location Type</th><th>Location Id</th><th>location Name</th><th>zone</th><th>circle</th><th>division</th><th>subdivision</th></tr>";
						var html1 = "";

						
						for (var i = 0; i < response.length; i++) {
							html1 += "<tr>";
							html1 += "<td>"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ response[i][1]
									+ "</td>"
									+ "<td>"
									+ ((response[i][2]) == null ? "": response[i][2])
									+ "</td>"
									+ "<td>"
									+ ((response[i][3]) == null ? "":response[i][3])
									+ "</td>"
									+ "<td>"
									+ (response[i][14] == null ? "" : moment(
											response[i][14]).format(
											'DD-MM-YYYY hh:mm:ss')) + "</td>"
									+ "<td>" + ((response[i][9]) == null ? "":response[i][9]) + "</td>"
									+ "<td>" + ((response[i][10]) == null ? "":response[i][10]) + "</td>"
									+ "<td>" +((response[i][11]) == null ? "": response[i][11]) + "</td>"
									+ "<td>" + ((response[i][5]) == null ? "":response[i][5] )+ "</td>"
									+ "<td>" + ((response[i][6]) == null ? "":response[i][6]) + "</td>"
									+ "<td>" + ((response[i][7]) == null ? "":response[i][7] )+ "</td>"
									+ "<td>" + ((response[i][8]) == null ? "":response[i][8]) + "</td>";
							html1 += "</tr>";
						}
						$('#sample_4_head').empty();
						//$('#sample_4_body').html('');
						$('#sample_4').dataTable().fnClearTable();
						//$('#sample_4 th').empty();
						//$('#sample_4').empty();
						$('#sample_4_head').html(html2);
						$('#sample_4_body').html(html1);
						loadSearchAndFilter('sample_4');
					}
				});

		$('#closeShow').show();
		$('#closeShow1').show();
		$('#excelExport').show();

	}

	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable(
				{
					"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
							[ 10, 20, 50, 100, "All" ] // change per page values here
					],
					"iDisplayLength" : 10
				});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}
</script>