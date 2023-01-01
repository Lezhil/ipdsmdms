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
						loadSearchAndFilter('sample_1');
						var zoneVal = "${zoneVal}";
						var circleVal = "${circleVal}";
						$("#month").val('');
						if (zoneVal != '' && circleVal != '') {
							$('#zone').find('option').remove().end().append('<option value="'+ zoneVal +'">' + zoneVal+ '</option>');
							$("#zone").val(zoneVal).trigger("change");
							setTimeout(function() {
							$('#circle').find('option').remove().end().append('<option value="'+ circleVal +'">'+ circleVal+ '</option>');
							$("#circle").val(circleVal).trigger("change");
							}, 200);
						} else {
							$("#zone").val("").trigger("change");
						}
						$('#SurveyAndInstallation,#dtMeterReplacementRpt').addClass('start active ,selected');
						$("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
						.removeClass('start active ,selected');

					});

	$("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	});
</script>
<script type="text/javascript">
  var gMtrNo;
  var gfromDate;
  var gToDate;
  var gEstRuleId;
  var gEstimatedData;
</script>
<script>
	function getdtmeterData() {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = $('#division').val();
		var sectionCode = $('#sectionCode').val();
		var sdoCode = $('#sdoCode').val();
		var town = $('#town').val();

		if (zone == "") {
			bootbox.alert("Please Select zone");
			return false;
		}

		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (sdoCode == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		}
		if (sectionCode == "") {
			bootbox.alert("Please Select Section");
			return false;
		}
		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}

		$
				.ajax({
					url : './getValidationData',
					type : 'POST',
					data : {
						zone : zone,
						circle : circle,
						division : division,
						sdoCode : sdoCode,
						ruleId : ruleId,
						month : month
					},
					dataType : 'json',
					success : function(response) {

						$('#sample_1').dataTable().fnClearTable();
						$("#updateMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								console.log(resp.myKey + "from date=="
										+ resp.fromDate + "toDate===="
										+ resp.toDate);
								var j = i + 1;
								html += "<tr>" + "<td>"
										+ j
										+ "</td>"
										+ "<td>"+ resp.myKey.v_rule_id + " </td>"
										+ "<td>"+ resp.rulename + " </td>"
										+ "<td>"+ ((resp.subdivision == null) ? "": resp.subdivision) + " </td>"
										+ "<td>"+ ((resp.location_type == null) ? "": resp.location_type) + " </td>"
										+ "<td>"+ ((resp.location_id == null) ? "": resp.location_id) + " </td>"
										+ "<td>"+ ((resp.location_name == null) ? "": resp.location_name) + " </td>"
										+ "<td>"+ ((resp.myKey.meter_number == null) ? "": resp.myKey.meter_number)+ " </td>"
										+
										/* "<td>"+((resp.myKey.meter_number==null)?"":resp.myKey.meter_number)+" </td>"+
											 "<td>"+((resp.meter_number==null)?"":resp.meter_number)+" </td>"+
										"<td>"+((resp.meter_number==null)?"":resp.meter_number)+" </td>"+ */
										"<td>"
										+ "<button  onclick='viewData(\""
										+ resp.myKey.meter_number
										+ "\",\""
										+ month
										+ "\",\""
										+ resp.myKey.v_rule_id
										+ "\")' class='btn green' data-toggle='modal'  data-target='#stack2'>View</button>"
										+ "</td>"
										+ "<td>"
										+ "<button  onclick='estimateData(\""
										+ resp.myKey.meter_number
										+ "\",\""
										+ resp.fromDate
										+ "\",\""
										+ resp.toDate
										+ "\",\""
										+ resp.myKey.v_rule_id
										+ "\")' class='btn blue' data-toggle='modal'  data-target='#stack3'>Estimate</button>"
										+ "</td>" +

										"</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("No Relative Data Found.");
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_1');
						// $("#month").val('');
					}
				});

	}

	function viewData(meter_number, month, ruleId) {
		$.ajax({
			url : './viewValidationMeterData',
			type : 'POST',
			data : {
				meter_number : meter_number,
				month : month,
				ruleId : ruleId
			},
			dataType : 'json',
			success : function(response) {
				}
		});

	}
</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<c:choose>
					<c:when test="${alert_type eq 'success'}">
						<div class="alert alert-success display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: green">${results}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: red">${results}</span>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>DT Meter Replacement Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">			

					<div class="row" style="margin-left: -1px;">
						<div class="col-md-4">
							<div class="form-group">
								<select class="form-control select2me" name="zone" id="zone"
									onchange="showCircle(this.value);">
									<option value="">Select Zone</option>
									 <option value="%">ALL</option> 
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="circleTd" class="form-group">
								<select class="form-control select2me" id="circle" name="circle">
									<option value="">Select Circle</option>
									<!-- <option value="ALL">ALL</option> -->
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="divisionTd" class="form-group">
								<select class="form-control select2me" id="division"
									name="division">
									<option value="">Select Division</option>
									<!-- <option value="ALL">ALL</option> -->
									<c:forEach items="${divisionList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="row" style="margin-left: -1px;">

						<div class="col-md-4">
							<div id="subdivTd" class="form-group">
								<select class="form-control select2me" id="sdoCode"
									name="sdoCode">
									<option value="">Select Sub-Division</option>
									<!-- 	<option value="ALL">ALL</option> -->
									<c:forEach items="${subdivList}" var="sdoVal">
										<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="sectionId" class="form-group">
								<select class="form-control select2me" id="sectionCode"
									name="sectionCode">
									<option value="">Select Section</option>
									<!-- 	<option value="ALL">ALL</option> -->
									<c:forEach items="${sectionList}" var="sectionVal">
										<option value="${sectionVal}">${sectionVal}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col-md-4">
							<div id="townId" class="form-group">
								<select class="form-control select2me" id="town" name="town">
									<option value="">Select Town</option>
									<%-- <option value="ALL">ALL</option>  --%>
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

						</div>
					</div>
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom">
						<div id="showConsumer">
						   <button type="button" id="addnewdtmeter" data-toggle='modal'  data-target='#stack1' style="margin-left: 400px;" name="addnewdtmeter" class="btn green">
								<b>CHANGE METER</b>
							</button>
							<button type="button" id="viewdtmeterData" onclick="getdtmeterData()" name="viewdtmeterData" class="btn yellow">
								<b>VIEW REPORT</b>
							</button>
							<br />
							
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_1', 'DTMeterReplacementReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>SL NO.</th>
										<th>EDIT</th>
										<th>SS NAME</th>
										<th>FEEDER NAME</th>
										<th>DT CODE</th>
										<th>DT NAME</th>
										<th>CROSS DT</th>
										<th>DT TYPE</th>
										<th>DT CAPACITY</th>
										<th>DT PHASE</th>
										<th>OLD METER SERIAL NO</th>
										<th>OLD METER MAKE</th>
										<th>OLD MF</th>
										<th>OLD METER LAST READING</th>
										<th>NEW METER SERIAL NO</th>
										<th>NEW METER MAKE</th>
										<th>NEW MF</th>
										<th>NEW METER FIRST READING</th>
										<th>GEO COORDINATE X</th>
										<th>GEO COORDINATE Y</th>
										<th>NEW VOLTAGE MF</th>
										<th>NEW CURRENT MF</th> 
									</tr>
								</thead>
								<tbody id="updateMaster">

								</tbody>
							</table>
						</div>

					</div>
				</div>
				
				
				<div id="stack1" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;"  >Add DT Meter Replacement Details</span></h4>
								</div>
							
								<div class="modal-body">
									<form action="#" id="addDTMeterReplacement" method="post">
										
									 <div class="row"> 
									 
									 		<div class="col-md-6">
												<div id="addsectionId" class="form-group">
												 <label class="control-label">Select Section</label>
													 <span style="color: red" class="required">*</span>
													<select class="form-control select2me" id="addsectionCode" name="addsectionCode">
														<option value="">Select Section</option>
														<!-- 	<option value="ALL">ALL</option> -->
														<c:forEach items="${sectionList}" var="sectionVal">
															<option value="${sectionVal}">${sectionVal}</option>
														</c:forEach>
													</select>
												</div>
											</div>
							
											<div class="col-md-6">
												<div class="form-group">
												   <label class="control-label">Select Feeder Name</label>
													 <span style="color: red" class="required">*</span>
													<select class="form-control select2me" id="feedername" name="feedername">
														<option value="">Select Feeder Name</option>
														<!-- 	<option value="ALL">ALL</option> -->
														<c:forEach items="${sectionList}" var="sectionVal">
															<option value="${sectionVal}">${sectionVal}</option>
														</c:forEach>
													</select>

												</div>
											</div>	
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Code</label>
													 <span style="color: red" class="required">*</span>
													<input type="text" id="dtcode"
														class="form-control placeholder-no-fix" placeholder="Enter DT Code"
														readonly="readonly" name="DTCODE"
														maxlength="50"></input>
												</div>
											 </div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Name</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="dtname"
														class="form-control placeholder-no-fix"
														placeholder="Enter DT Name" name="DTNAME"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Cross DT</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="crossdt" name="crossdt">
														<option value="">Select Cross DT</option>
															<option value="Yes">YES</option>
															<option value="No">NO</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Type</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="dttype" name="dttype">
														<option value="">Select DT Type</option>
															<option value="HT">HT</option>
															<option value="LT">LT</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Capacity</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="dtcapacity"
														class="form-control placeholder-no-fix"
														placeholder="Enter DT Capacity" name="DTCAPACITY"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Phase</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="dtphase" name="dtphase">
														<option value="">Select DT Phase</option>
															<option value="1">1-Phase</option>
															<option value="3">3-Phase</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="oldmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter No" name="OLDMTRNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Make</label>
													 <span style="color: red" class="required">*</span> 
													 <select  name="oldmeterMakeId"
															id="oldmeterMakeId" class="form-control select2me">
															<option value="0">Select Old Meter Make</option>
															<option value="GENUS">GENUS</option>
															<option value="HPL">HPL</option>
															<option value="SECURE">SECURE</option>
															<option value="L&T">L&T</option>
															<option value="L&G">L&G</option>
													  </select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="oldmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter MF" name="OLDMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Last Reading</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="oldmtrrdg"
														class="form-control placeholder-no-fix"
														placeholder="Old Meter Last Reading" name="OLDMTRRDG"
														maxlength="50"></input>
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="newmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter No" name="NEWMTRNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter Make</label>
													 <span style="color: red" class="required">*</span> 
													  <select  name="meterMakeId"
															id="meterMakeId" class="form-control select2me">
															<option value="0">Select New Meter Make</option>
															<option value="GENUS">GENUS</option>
															<option value="HPL">HPL</option>
															<option value="SECURE">SECURE</option>
															<option value="L&T">L&T</option>
															<option value="L&G">L&G</option>
													  </select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="newmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter MF" name="NEWMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Voltage MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="newvltmf"
														class="form-control placeholder-no-fix"
														placeholder="New Voltage MF" name="NEWVLTMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Current MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="newcurmf"
														class="form-control placeholder-no-fix"
														placeholder="New Current MF" name="NEWCURMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter First Reading</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="newmtrrdg"
														class="form-control placeholder-no-fix"
														placeholder="New Meter First Reading" name="NEWMTRRDG"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">GEO Coordinate X</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="bdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter GEO Coordinate X" name="BDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">GEO Coordinate Y</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="bdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter GEO Coordinate Y" name="BDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											
												  
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="addBoundaryMeter"
												type="button" onclick="addBoundary()">ADD</button>
									  </div>
									</form>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>

	</div>

	<div id="stack2" class="modal fade" role="dialog"
		aria-labelledby="myModalLabel10" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;"  >Modify DT Meter Replacement Details</span></h4>
							</div>
								<div class="modal-body">
									<form action="#" id="editDTMeterReplacement" method="post">
										
									 <div class="row"> 
									 
									 		<div class="col-md-6">
												<div id="addsectionId" class="form-group">
												 <label class="control-label">Select Section</label>
													 <span style="color: red" class="required">*</span>
													<select class="form-control select2me" id="usectionCode" name="usectionCode">
														<option value="">Select Section</option>
														<!-- 	<option value="ALL">ALL</option> -->
														<c:forEach items="${sectionList}" var="sectionVal">
															<option value="${sectionVal}">${sectionVal}</option>
														</c:forEach>
													</select>
												</div>
											</div>
							
											<div class="col-md-6">
												<div class="form-group">
												   <label class="control-label">Select Feeder Name</label>
													 <span style="color: red" class="required">*</span>
													<select class="form-control select2me" id="ufeedername" name="ufeedername">
														<option value="">Select Feeder Name</option>
														<!-- 	<option value="ALL">ALL</option> -->
														<c:forEach items="${sectionList}" var="sectionVal">
															<option value="${sectionVal}">${sectionVal}</option>
														</c:forEach>
													</select>

												</div>
											</div>	
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Code</label>
													 <span style="color: red" class="required">*</span>
													<input type="text" id="udtcode"
														class="form-control placeholder-no-fix" placeholder="Enter DT Code"
														readonly="readonly" name="UDTCODE"
														maxlength="50"></input>
												</div>
											 </div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Name</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="udtname"
														class="form-control placeholder-no-fix"
														placeholder="Enter DT Name" name="UDTNAME"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Cross DT</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="ucrossdt" name="ucrossdt">
														<option value="">Select Cross DT</option>
															<option value="Yes">YES</option>
															<option value="No">NO</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Type</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="udttype" name="udttype">
														<option value="">Select DT Type</option>
															<option value="HT">HT</option>
															<option value="LT">LT</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Capacity</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="udtcapacity"
														class="form-control placeholder-no-fix"
														placeholder="Enter DT Capacity" name="UDTCAPACITY"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">DT Phase</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="udtphase" name="udtphase">
														<option value="">Select DT Phase</option>
															<option value="1">1-Phase</option>
															<option value="3">3-Phase</option>
													</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter No" name="UOLDMTRNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Make</label>
													 <span style="color: red" class="required">*</span> 
													 <select  name="umeterMakeId"
															id="umeterMakeId" class="form-control select2me">
															<option value="0">Select Old Meter Make</option>
															<option value="GENUS">GENUS</option>
															<option value="HPL">HPL</option>
															<option value="SECURE">SECURE</option>
															<option value="L&T">L&T</option>
															<option value="L&G">L&G</option>
														</select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter MF" name="UOLDMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Last Reading</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmtrrdg"
														class="form-control placeholder-no-fix"
														placeholder="Old Meter Last Reading" name="UOLDMTRRDG"
														maxlength="50"></input>
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter No" name="UNEWMTRNO"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter Make</label>
													 <span style="color: red" class="required">*</span> 
													 <select  name="unewmeterMakeId"
															id="unewmeterMakeId" class="form-control select2me">
															<option value="0">Select New Meter Make</option>
															<option value="GENUS">GENUS</option>
															<option value="HPL">HPL</option>
															<option value="SECURE">SECURE</option>
															<option value="L&T">L&T</option>
															<option value="L&G">L&G</option>
													  </select>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter MF" name="UNEWMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Voltage MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewvltmf"
														class="form-control placeholder-no-fix"
														placeholder="New Voltage MF" name="UNEWVLTMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Current MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewcurmf"
														class="form-control placeholder-no-fix"
														placeholder="New Current MF" name="UNEWCURMF"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter First Reading</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmtrrdg"
														class="form-control placeholder-no-fix"
														placeholder="New Meter First Reading" name="UNEWMTRRDG"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">GEO Coordinate X</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ubdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter GEO Coordinate X" name="UBDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">GEO Coordinate Y</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ubdrloc"
														class="form-control placeholder-no-fix"
														placeholder="Enter GEO Coordinate Y" name="UBDRLOC"
														maxlength="50"></input>
												</div>
											</div>
											
												  
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="editMeterReplacement"
												type="button" onclick="editDTMeterReplacement()">Update</button>
									  </div>
									</form>
								</div>
			</div>
		</div>
	</div>


</div>



<script>

	function showCircle(zone)
	{
		 $.ajax({
		    	url:'./getCircleByZone',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					zone : zone
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
		    	}
			});
	}

	 function showDivision(circle) {
		 var zone = $('#zone').val();
		
			$.ajax({
				url : './getDivByCircle',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone,
					circle : circle
				},
						success : function(response) {
							var html = '';
							html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option  value='"+response[i]+"'>"
										+ response[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#divisionTd").html(html);
							$('#division').select2();
						}
					});
		}
	 function showSubdivByDiv(division) {
			
			var zone = $('#zone').val();
			var circle = $('#circle').val();
			$.ajax({
				url : './getSubdivByDiv',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone,
					circle : circle,
					division : division
				},
						success : function(response1) {
							var html = '';
							html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								//var response=response1[i];
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTd").html(html);
							$('#sdoCode').select2();
						}
					});
		}


	
</script>

<script>

	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();

	$('.from').datepicker({
		format : "yyyymm",
		minViewMode : 1,
		autoclose : true,
		startDate : new Date(new Date().getFullYear()),
		endDate : new Date(year, month - 1, '31')
	});
	
</script>
