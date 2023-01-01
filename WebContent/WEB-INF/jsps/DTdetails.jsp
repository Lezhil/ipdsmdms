<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"	type="text/javascript"></script>
<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"	type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>"	type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						TableManaged.init();
						loadSearchAndFilter('dt_report');
						//$("#circle").val("").trigger("change");
						/* $( "#uniform-onyes" ).removeClass( "span checked" ) */
						$('#MDMSideBarContents,#mpmId,#dtDetailsId').addClass(
								'start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');
						//loadSearchAndFilter("sample_1");
						//getAlldetailsData();

					});
</script>
<div class="page-content">
	<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
<c:if test = "${editRights eq 'yes'}">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Bulk Edit(DT Details)
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">

					<form action="./uploadDtFile" id="uploadFile"
						enctype="multipart/form-data" method="post">

						<div class="form-group">
							<div class="row" style="margin-left: -1px;">
								
								<div class="col-md-6">
									<div>
										<label for="exampleInputFile1">Only Upload xlsx File(<a
											href='#' id='dtSample' onclick='downLoadSampleExcel()'>Download
												Sample-File</a>)
										</label>
										
										 <input type="file" id="fileUpload"
											onchange="ValidateSingleInput(this);" name="fileUpload"><br />
											
										
										<button class="btn blue pull-left" style="display: block;"
											id="uploadButton" onclick="return finalSubmit();">Upload
											File</button>
										&nbsp;
									</div>
								</div>
								<div class="col-md-6">
									<div style="margin-left: 0px;">
										<label><b>Note:-</b></label>
										<ol>
											<li>User can able to do bulk Update Only.</li>
											<li>User can able to update only
												DT Type,DT Name,Capacity,Phase,Latitude and Longitude.</li>
											<li>Capacity,Phase,Latitude and Longitude should be Number.</li>

										</ol>
									</div>
								</div>
							</div>
						</div>
					</form>

					<div align="center">
						<div class="col-md-6">
							<div id='loadingmessage' style='display: none'>
								<img alt="image" src="./resources/assets/img/Preloader_3.gif"
									class="ajax-loader" width=100px; height=100px;> <span
									id="load" class="wait" style="color: blue;"><b>Uploading
										Data Please Wait</b>!!!!</span>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
</c:if>

	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>DT Details
			</div>
		</div>

		<div class="portlet-body">
			<%-- <c:if test="${officeType eq 'subdivision'}">  --%>
			<div class="row" style="margin-left: -3px;">
				<div class="col-md-4">
				<c:if test = "${editRights eq 'yes'}">
					<div class="btn-group">
						<button class="btn green" data-toggle="modal"
							data-target="#stack1" id="" onclick=" adddt()">
							Add DT <i class="fa fa-plus"></i>
						</button>
					</div>
					</c:if>
				</div>
			</div>
			<br>
			<jsp:include page="locationFilter.jsp"/> 
			<div class="row" style="margin-left: -1px;">
				<div class="col-md-3">
						<div id="feederDivId" class="form-group">
							<label class="control-label"><b>Feeder:</b></label> <select
								class="form-control select2me input-medium" id="feederTpId"
								name="feederTpId">
								<option value="">Select Feeder</option>
							</select>
						</div>
					</div>

			<div class="col-md-2">
				<div id="showFeederData" class="form-group">
					<button type="button" id="viewFeederData"
						onclick="getAlldetailsData();" style="margin-top: 25px;"
						name="viewFeederData" class="btn yellow">
						<b>View</b>
					</button>
					
				</div>
			</div>
			</div>
		<%-- 	<div class="row" style="margin-left: -1px;">
				<div class="col-md-4">
					<div id="circleTd" class="form-group">
						<label class="control-label">Circle:</label> <select
							class="form-control select2me" id="circle" name="circle"
							onchange="showTown(this.value);">
							<option value="">Select Circle</option>
							<option value="%">ALL</option>
							<c:forEach items="${circleList}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div id="townDTId" class="form-group">
						<label class="control-label">Town:</label> <select
							class="form-control select2me" id="townDT" name="townDT">
							<option value="">Select Town</option>
							<option value="%">ALL</option>
							<c:forEach items="${townList}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>

				</div>
				<div class="col-md-4">
							<div id="substaTd" class="form-group">
							<label class="control-label">Sub-Station:</label>
								<select class="form-control select2me" id="substationDT"
									name="substationDT">
									<option value="">Select Sub-Station</option>
									<option value="%">ALL</option> 
									<c:forEach items="${substationList}" var="substation">
										<option value="${substation}">${substation}</option>
									</c:forEach>
								</select>
							</div>

						</div>

				<div class="col-md-4">
					<div id="feederDivId" class="form-group">
						<label class="control-label">Feeder:</label> <select
							class="form-control select2me input-medium" id="feederTpId"
							name="feederTpId">
							<option value="">Select Feeder</option>
						</select>
					</div>
				</div>
			</div> --%>
			
			<!-- <div class="portlet box blue">
					<div class="portlet-title line">
			<div class="portlet-title">
				<div class="caption">DT Details</div>
			</div>
		</div>
			 <div class="portlet-body"> -->
			<br>
			<br>
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 15px;">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
						Tools <i class="fa fa-angle-down"></i>
					</button>
					<ul class="dropdown-menu pull-right">
						<li><a href="#" id=""
							onclick="exportPDF('dt_report','DtDetails')">Export to PDF</a></li>
						<li><a href="#" id="" onclick="DTtableToExcel()">Export
								to Excel</a></li>
					</ul>
				</div>
			</div>

			<table class="table table-striped table-hover table-bordered"
				id="dt_report">
				<thead>
					<tr>
						<!--   <th>EDIT</th>
								 <th>DT Id</th>
								<th>DT Type</th>
								<th>DT NAME</th>
								<th>DT Capacity</th>
								<th>Phase</th>
								<th>DT Code</th>
								<th>Feeder Code</th>
								<th>Meter Sr Number</th>
								<th>Meter Manufacturer</th>
								<th>Consumption Percentage</th> 
								<th>Delete</th> -->
						<c:if test = "${editRights eq 'yes'}">
						<th>Edit</th></c:if>
						<th>Region</th>
						<th>Circle</th>
						<th>Town</th>
						<th>Feeder Name</th>
						<th>Feeder Code</th>
						<th>DT Code</th>
						<th>DT Type</th>
						<th>DT Name</th>
						<th>DT Capacity</th>
						<th>DT HV Voltage</th>
						<th>DT HV max Current (Cal)</th>
						<th>DT LV Voltage</th>
						<th>DT LV Max Current (Cal)</th>
						<th>Phase</th>
						<th>Latitude</th>
						<th>Longitude</th>
						<th>Meter Sr Number_1</th>	
						<th>Meter Sr Number_2</th>		
						<th>Meter Manufacturer</th>
					
						<!-- <th>Consumption Percentage</th> -->
						<!-- <th>MF</th> -->
						<c:if test = "${editRights eq 'yes'}">
						<th>Delete</th></c:if>
					</tr>
				</thead>
				<tbody id="Dtdetails">
					<%-- <c:forEach var="element" items="${dtDetails}">
								<tr>
									
								   <td><a href="#" onclick=" editDTdetails(this.id)"id="${element[0]}">Edit</a></td>
								   <td>${element[11]}</td> 
									 <td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td> 
									<td><a href="#" onclick="DeleteDt(this.id)"id="${element[11]}">Delete</a></td>

								</tr>
							</c:forEach> --%>
				</tbody>
				<div id="imageee" hidden="true" style="text-align: center;">
					<h3 id="loadingText">Loading..... Please wait.</h3>
					<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
						style="width: 4%; height: 4%;">
				</div>
			</table>

			<%-- <div class="row">
				<div class="col-md-12">
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 1px;">
								<div id="excelExportDiv2">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print">Print</a></li>
										<li><a href="#" id="excelExport2"
											onclick="tableToExcel2('sample_1', 'Dt Details')">Export
												to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>EDIT</th>
								 <th>DT Id</th>
								<th>DT Type</th>
								<th>DT NAME</th>
								<th>DT Capacity</th>
								<th>Phase</th>
								<th>DT TP Code</th>
								<th>TP Parent Code</th>
								<th>Meter Sr Number</th>
								<th>Meter Manufacturer</th>
								<th>Consumption Percentage</th>
								<th>Delete</th>  </c:if>
							</tr>
						</thead>
						<tbody id="Dtdetails">
							<c:set var="count" value="1" scope="page"></c:set>
							<c:forEach var="element" items="${dtDetails}">
								<tr>
									
								   <td><a href="#" onclick=" editDTdetails(this.id)"id="${element[0]}">Edit</a></td>
								   <td>${element[11]}</td> 
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td><a href="#" onclick="DeleteDt(this.id)"id="${element[11]}">Delete</a></td>

								</tr>
								<c:set var="count" value="{count+1}" scope="page"></c:set>
							</c:forEach>
						</tbody>
					</table>
					<div id="imageee" hidden="true" style="text-align: center;">
								<h3 id="loadingText">Loading..... Please wait.</h3>
								<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
									style="width: 4%; height: 4%;">
							</div>

	</div>
	</div> --%>
		</div>
	</div>
</div>


<div id="stack1" class="modal fade" role="dialog">

	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					onclick="return optionCheck();"></button>
				<h5 class="modal-title" id="addData">Add DT Details</h5>
			</div>
			<br>
			<div class="modal-body">
				<form action="" method="post" id="addDTdetailsId">
					<div class="row" id="crossdt">
						<div class="col-md-12 ">

							<div class="form-group">
								<!-- <label  class="">Cross DT</label> -->
								<div class="radio-list">

									<label class="radio-inline"> <input type="radio"
										name="optionsRadios" id="onno" onclick="return changeNO()"
										value="option2" checked>Add DT
									</label>
									<!-- <label class="radio-inline">
											<input type="radio" name="optionsRadios" id="onyes" onclick="return changeYes()" value="option1" >Yes
											</label> -->
								</div>
							</div>
						</div>
					</div>
					<div class="row">

						<div class="col-md-4" id="DttypeDivId">
							<div class="form-group">
								<label class="control-label">DT Type</label> <select
									class="form-control  select2me" name="dttype" id="dttype"
									data-placeholder="Select DT Type" tabindex="1">
									<option value="LT">LT</option>
									<option value="HT">HT</option>
								</select>
							</div>
						</div>
						<div class="col-md-4" id="dtid" hidden="true">
							<div class="form-group">
								<label class="control-label">DT ID</label> <input type="text"
									id="dtId" class="form-control placeholder-no-fix"
									placeholder="Enter DT ID" name="dtId" maxlength="12"></input>
							</div>
						</div>

						<div class="col-md-4" id="DtnameDivId">
							<div class="form-group">
								<label class="control-label">DT Name</label><font color="red">*</font><input
									type="text" id="dtname" class="form-control placeholder-no-fix"
									placeholder="Enter DT Name" name="dtname"></input>

							</div>
						</div>

						<!-- 	<div class="col-md-4" id="TpParentDivId">
							<div class="form-group">
								<label class="control-label">TP Parent Code</label> <input
									type="text" id="tpparentcode"
									class="form-control placeholder-no-fix"
									placeholder="Enter TP Parent Code" name="tpparentcode"
									maxlength="12"></input>
							</div>
						</div> -->
					</div>
					<div class="row">
						<div class="col-md-4" id="DtcapDivId">
							<div class="form-group">
								<label class="control-label">DT Capacity(KVa)</label><font
									color="red">*</font><input type="text" id="dtcapacity"
									class="form-control placeholder-no-fix"
									placeholder="Enter DT Capacity" name="dtcapacity"
									maxlength="12"></input>
							</div>
						</div>

						<div class="col-md-4" id="DtPhaseDivId">
							<label>DT Phase Type</label> <select id="dtphase" name="dtphase"
								class="form-control  select2me">
								<!-- <option value="0">Select Phase</option> -->
								<option value="1">1 Phase</option>

								<option value="3">3 Phase</option>
							</select>
						</div>
						<div class="col-md-4" id="TpDtDivId">
							<div class="form-group">
								<label class="control-label"> TP DT Code<font
									color="red">*</font></label> <input
									type="text" id="tpdtcode"
									class="form-control placeholder-no-fix"
									onchange="checkDuplicate()" placeholder="Enter TP DT Code"
									name="tpdtcode"></input>
							</div>
						</div>
					</div>
					<!-- yes div -->

					<div class="row">

						<div class="col-md-4" id="SubDivId">
							<div class="form-group">
								<label class="control-label">Subdivision<font
									color="red">*</font></label> <select class="form-control select2me "
									name="subdiv" id="subdiv"
									onchange="return getTowns(this.value);">
									<option value="0">Select Subdivision</option>
									<c:forEach var="elements" items="${SubdivName}">
										<option value="${elements[0]}@${elements[1]}">${elements[1]}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4" id="towndivId">
							<label>Town<font color="red">*</font></label>
							<div id="townId" class="form-group">
								<select class="form-control select2me" id="town" name="town"
									onchange="return getSubStations(this.value);">
									<option value="">Select Town</option>

								</select>
							</div>
						</div>
					</div>
					<div class="row">

						<div class="col-md-4" id="ParentfsubDivId">
							<label>Substation<font color="red">*</font></label> <select id="parentfeedersub"
								name="parentfeedersub" class="form-control select2me "
								onchange="getFeeders(this.value);">
								<option value="0">Select SubStation</option>
							</select>
						</div>
						<div class="col-md-4" id="ParentfDivId">
							<label>Feeder<font color="red">*</font></label> <select class="form-control select2me"
								name="parentfeeder" id="parentfeeder"
								data-placeholder="Select Feeder" tabindex="1"
								onchange="getDTC(this.value)">
								<option value="0">Select Feeder</option>
							</select>
						</div>
						<div class="col-md-4" id="TpParentDivId">
							<div class="form-group">
								<label class="control-label">TP Parent Code</label> <input
									type="text" id="tpparentcode"
									class="form-control placeholder-no-fix"
									placeholder="Enter TP Parent Code" name="tpparentcode"
									maxlength="12"></input>
							</div>
						</div>
					</div>
					<div class="row">

						<div class="col-md-4" id="DTnameListId">
							<label>DT</label> <select id="dtNamesList" name="dtNamesList"
								class="form-control  select2me placeholder-no-fix">
								<option value="0">Select DTC</option>

							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" id="CrossMeterDivId">
							<label>Cross Point Metered</label><br>
							<label><input type="radio" name="optradio1"
								id="cpmradio1" onclick="return changeSubYes()" checked="checked" />Yes&nbsp;</label>
							<label><input type="radio" name="optradio1"
								id="cpmradio2" onclick="return changeSubNo()" />NO</label>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" id="MeterSrDivId">
							<div class="form-group">

								<label>Meter Serial No<font color="red">*</font></label><br>
								<input type="text" id="meterno"
									class="form-control placeholder-no-fix"
									placeholder="Enter Meter Serial No" name="meterno"
									maxlength="12" onchange="return checkMeterNo(this.value)"></input>
								<input type="hidden" id="oldmeterno"
									class="form-control placeholder-no-fix"
									placeholder="Enter Meter Serial No" name="oldmeterno"></input>


							</div>
						</div>
						<div class="col-md-4" id="MeterMFDivId">
							<div class="form-group">
								<label class="control-label">Meter Manufacturer<font
									color="red">*</font></label> <input type="text" id="metermanufacturer"
									class="form-control placeholder-no-fix"
									placeholder="Enter Metermanufacturer" name="metermanufacturer"></input>

							</div>
						</div>
						<!-- <div class="col-md-4" id="MFDivId">
							<div class="form-group">
								<label class="control-label">MF<font color="red">*</font></label>
								<input type="text" id="mf"
									class="form-control placeholder-no-fix" placeholder="Enter MF"
									name="mf" maxlength="12" onchange="checkMF(this.value)"></input>
								<input type="hidden" id="oldmf"
									class="form-control placeholder-no-fix" 
									name="oldmf" ></input>
							</div>
						</div> -->
						<div class="col-md-4" id="ConsumptionperDivId">
							<div class="form-group">
								<label class="control-label">Consumption Percentage<font
									color="red">*</font></label> <input type="text" id="Consumptionper"
									class="form-control placeholder-no-fix"
									placeholder="Enter Consumption Percentage"
									name="Consumptionper" maxlength="12"></input>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" id="mtrdatechngdivId" hidden="true">
							<div class="input-group date date-picker"
								data-date-format="yyyy-mm-dd" data-date-viewmode="years">
								<label>Meter Change Date</label><span style="color: red">*</span>
								<input type="text" class="form-control" name="mtrdatechngId"
									id="mtrdatechngId"></input> <span class="input-group-btn">
									<!--  <button class="btn default" type="button"><i class="fa fa-calendar"></i></button> -->
								</span>
							</div>
						</div>

						<div class="col-md-4" id="mfdatechngdivId" hidden="true">
							<div class="input-group date date-picker"
								data-date-format="yyyy-mm-dd" data-date-viewmode="years">
								<label>MF Change Date</label><span style="color: red">*</span> <input
									type="text" class="form-control" name="mfdatechngId"
									id="mfdatechngId"></input> <span class="input-group-btn">
									<!--  <button class="btn default" type="button"><i class="fa fa-calendar"></i></button> -->
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" id="latitudeID" hidden="true">
							<div class="form-group">
								<label class="control-label">Latitude</label> 
									<input type="text" id="latitude"
									class="form-control placeholder-no-fix"
									placeholder="Enter latitude" name="latitude"></input>

							</div>
							
						</div>

						<div class="col-md-4" id="longitudeID" hidden="true">
							<div class="form-group">
								<label class="control-label">Longitude</label> 
									<input type="text" id="longitude"
									class="form-control placeholder-no-fix"
									placeholder="Enter Longitude" name="longitude"></input>

							</div>
						</div>
					</div>


					<div class="modal-footer">
						<button class="btn green pull-right" id="addDTbtn" type="button"
							value="crossDTNo" onclick="validation(this.value);" hidden="true">Add</button>
						<!-- <button class="btn green pull-right" id="adddtbt2" type="button"
							value="save" onclick="add2validation(this.value);" hidden="true">Add2</button> -->
						<button class="btn blue pull-right" id="Updatedtbt" type="button"
							value="update" onclick="validationUpdate(this.value);"
							hidden="true">UPDATE</button>
						<button class="btn red pull-left" type="button"
							onclick="optionCheck()" data-dismiss="modal">Cancel</button>
						<button class="btn blue pull-right" id="addUpdatebtnId"
							type="button" value="" style="display: none"></button>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>


<script>
	function optionCheck() {
		var chyes = $('#uniform-onyes span').attr('class');
		var chno = $('#uniform-cpmradio1 span').attr('class');

		/* $("#onno").prop("checked", true);
		 
		 $("#onyes").prop("checked", false);
		 $( "#uniform-onyes" ).removeClass( "span checked" );
		 var ic = $('#onyes').prop('checked'); */
		if (chyes == 'checked') {
			$("#uniform-onyes span").removeClass("checked");
			$("#uniform-onno span").addClass("checked");
			$("#uniform-cpmradio2 span").removeClass("checked");
			$("#uniform-cpmradio1 span").addClass("checked");

		}

		$("#subdiv").val("").trigger("change");
		$("#town").val("").trigger("change");
		$("#parentfeedersub").val("").trigger("change");
		$("#parentfeeder").val("").trigger("change");

	}

	function adddt() {
		document.getElementById('addDTdetailsId').reset();
		$("#mtrdatechngdivId").hide();
		$("#mfdatechngdivId").hide();
		$("#addUpdatebtnId").val("1");
		$("#DTnameListId").hide();
		$("#DtnameDivId").show();
		$("#meterno").attr('readonly', false);
		document.getElementById('addDTdetailsId').reset();
		document.getElementById('addData').innerHTML = "";
		document.getElementById('addData').innerHTML = 'Add DT Details';
		$("#CircleDivId").hide();
		$("#SubstationDivId").hide();
		$("#CrossMeterDivId").hide();
		$("#FeederDivId").hide();
		$("#ConsumptionperDivId").hide();
		$("#DttypeDivId").show();
		$("#ParentfsubDivId").show();
		$("#parentfeedersub").show();
		$("#parentfeeder").show();
		$("#ParentfDivId").show();
		$("#parentfeeder").show();
		$("#DtcapDivId").show();
		$("#DtPhaseDivId").show();
		$("#TpDtDivId").show();
		$("#TpParentDivId").show();
		$("#MeterSrDivId").show();
		$("#MFDivId").show();
		$("#crossdt").show();
		$("#Updatedtbt").hide();
		// $("#adddtbt2").hide();
		$("#dtid").hide();
		$("#addDTbtn").show();
		$("#DivId").hide();
		$("#SubDivId").show();
		$("#MeterMFDivId").show();
		$("#metermanufacturer").show();
		$("#ParentfsubDivId").show();
		$("#parentfeedersub").show();
		$("#DTnameListId").hide();
		$("#latitudeID").hide();
		$("#longitudeID").hide();
		$("#towndivId").show();
	}

	//add validation
	function validation(btnValue) {
		var metersr = $("#meterno").val();
		var metermanufacturer = $('#metermanufacturer').val();
		var mf = $('#mf').val();
		var dtcapacity = $("#dtcapacity").val();
		var rx = /^\d+(?:\.\d{1,1})?$/;
		//var Consumptionper = $("#Consumptionper").val();
		var regex = /^[a-zA-Z]*$/;
		var dtname = $("#dtname").val();
		var dtNamesList = $("#dtNamesList").val();
		var subdiv = $("#subdiv").val();
		var parentfeedersub = $("#parentfeedersub").val();
		var parentfeeder = $("#parentfeeder").val();
		var btnvalueUpdt = $("#addUpdatebtnId").val();
		var oldmeter = $("#oldmeterno").val();
		var mtrchngedate = $("#mtrdatechngId").val();
		var mfchngedate = $("#mfdatechngId").val();
		var oldmf = $("#oldmf").val();
		var dttpcode = $("#tpdtcode").val();

		if (btnValue == "crossDTNo") {

			if (dtname == '') {
				bootbox.alert("Enter DT Name");
				return false;
			}

			if (dtcapacity == '') {

				bootbox.alert("Please Enter Dtcapacity Value ");
				return false;
			}

			if (isNaN(dtcapacity)) {

				bootbox.alert("Dtcapacity Enter Only Numbers");
				return false;
			}
			if (dttpcode == '' || dttpcode == null) {
				bootbox.alert("Enter TPDTCode Value");
				return false;
			}
			if (subdiv == 0) {
				bootbox.alert("Please Select subdivision");
				return false;
			}
			if (parentfeedersub == 0) {
				bootbox.alert("Please Select substation");
				return false;
			}
			if (parentfeeder == 0) {
				bootbox.alert("Please Select Feeder");
				return false;
			}

			if (metersr == "" || metersr == null) {

				bootbox.alert("Please Enter MeterSr.no ");
				return false;
			}

			if (metermanufacturer == '') {

				bootbox.alert("Please Enter Meter Manufacturer ");
				return false;
			}

			if (!metermanufacturer.match(regex)) {
				bootbox.alert("Please Enter valid Meter Manufacturer ");
				return false;
			}

			/* 	if (mf == '') {
					bootbox.alert("Please Enter MF No. ");
					return false;
				} 
				
				if (isNaN(mf)) {
					bootbox.alert("MF Enter Only Numbers");
					return false;
				}; */
		}
		if (btnValue == "crossDTYes") {
			if (dttpcode == '' || dttpcode == null) {
				bootbox.alert("Enter TPDTCode Value");
				return false;
			}
			if (subdiv == 0) {
				bootbox.alert("Please  Select subdivision");
				return false;
			}
			if (parentfeedersub == 0) {
				bootbox.alert("Please  Select substation");
				return false;
			}
			if (parentfeeder == 0) {
				bootbox.alert("Please  Select Feeder");
				return false;
			}
			if (dtNamesList == 0) {
				bootbox.alert("Enter DT Name");
				return false;
			}

			if (metersr == "") {

				bootbox.alert("Please Enter MeterNo ");
				return false;
			}
			if (metermanufacturer == '') {

				bootbox.alert("Please Enter Meter Manufacturer ");
				return false;
			}
			/* if (mf == '') {
				bootbox.alert("Please Enter MF No. ");
				return false;
			}
			if (isNaN(mf)) {
				bootbox.alert("MF Enter Only Numbers");
				return false;
			}; */
		}
		if (btnValue == "crossDTSubNo") {

			if (subdiv == 0) {
				bootbox.alert("Please  Select subdivision");
				return false;
			}
			if (parentfeedersub == 0) {
				bootbox.alert("Please  Select substation");
				return false;
			}
			if (parentfeeder == 0) {
				bootbox.alert("Please  Select Feeder");
				return false;
			}
			if (dtNamesList == 0) {
				bootbox.alert("Enter DT Name");
				return false;
			}
			/* if (Consumptionper == '') {
				bootbox.alert("Please Enter Consumptionpercentage No. ");
				return false;
			} */
			/* if (isNaN(Consumptionper)) {

				bootbox.alert("invalid Consumptionpercentage");
				return false;
			} */
			/* if (!Consumptionper.match(rx)) {
				bootbox.alert("Please Enter Only one decimal value ");
				return false;
			}; */
		}
		if (btnvalueUpdt == "2") {

			if (metersr != oldmeter) {
				if (mtrchngedate == "") {
					bootbox.alert("Please Select Meter Change Date");
					$("#mtrdatechngId").val("");
					return false;
				}
				/* 	if(oldmf!=mf){
						if(mfchngedate==""){
							bootbox.alert("Please Select MF Change Date");
							$("#mfrdatechngId").val("");
							return false;
							}
					} */
			}
		}

		if (btnValue == "crossDTYes") {

			$("#addDTdetailsId").attr('action', './addCrossDTdetails').submit();
		}

		if (btnValue == "crossDTNo") {
			$("#addDTdetailsId").attr('action', './addNoCrossDTdetails')
					.submit();
		}

		if (btnValue == "crossDTSubNo") {

			$("#addDTdetailsId").attr('action', './addCrossDTSubNo').submit();
		}

		/* if (btnValue == "update") {
			alert("inside update submit");
			
			$("#addDTdetailsId").attr('action', './Modifydtdetails').submit();
		}; */
	}

	//update validation
	function validationUpdate(btnValue) {
		var metersr = $("#meterno").val();
		var metermanufacturer = $('#metermanufacturer').val();
		var mf = $('#mf').val();
		var dtcapacity = $("#dtcapacity").val();
		var rx = /^\d+(?:\.\d{1,1})?$/;
		var Consumptionper = $("#Consumptionper").val();
		var regex = /^[a-zA-Z]*$/;
		var dtname = $("#dtname").val();
		var btnvalueUpdt = $("#addUpdatebtnId").val();
		var oldmeter = $("#oldmeterno").val();
		var mtrchngedate = $("#mtrdatechngId").val();
		var mfchngedate = $("#mfdatechngId").val();
		var oldmf = $("#oldmf").val();
		var latitude = $("#latitudeID").val();
		var longitude = $("#longitudeID").val();
		//alert(latitude);

		//alert("btnvalueUpdt--"+btnvalueUpdt);
		if (dtname == '') {
			bootbox.alert("Enter DT Name");
			return false;
		}

		if (dtcapacity == '') {

			bootbox.alert("Please Enter Dtcapacity Value ");
			return false;
		}

		if (isNaN(dtcapacity)) {

			bootbox.alert("Dtcapacity Enter Only Numbers");
			return false;
		}

		if (metersr == "") {

			bootbox.alert("Please Enter MeterSr.no ");
			return false;
		}

		/* if (metermanufacturer == '') {

			bootbox.alert("Please Enter Meter Manufacturer ");
			return false;
		}

		if (!metermanufacturer.match(regex)) {
			bootbox.alert("Please Enter valid Meter Manufacturer ");
			return false;
		} */

		/* 	if (mf == '') {
				bootbox.alert("Please Enter MF No. ");
				return false;
			} 
			
			if (isNaN(mf)) {
				bootbox.alert("MF Enter Only Numbers");
				return false;
			}; */
		if (btnvalueUpdt == "2") {

			if (metersr != oldmeter) {
				if (mtrchngedate == "") {
					bootbox.alert("Please Select Meter Change Date");
					$("#mtrdatechngId").val("");
					return false;
				}
			}
			/* if(oldmf!=mf){
				if(mfchngedate==""){
					bootbox.alert("Please Select MF Change Date");
					$("#mfrdatechngId").val("");
					return false;
					}
			} */
		}

	/* 	if (Consumptionper == '') {
			bootbox.alert("Please Enter Consumptionpercentage No. ");
			return false;
		}
		if (isNaN(Consumptionper)) {

			bootbox.alert("invalid Consumptionpercentage");
			return false;
		}
		if (!Consumptionper.match(rx)) {
			bootbox.alert("Please Enter Only one decimal value ");
			return false;
		}
		; */

		//alert("btnvalueUpdt--"+btnvalueUpdt);
		//alert("oldmeter--"+oldmeter);

		if (btnValue == "update") {

			$("#addDTdetailsId").attr('action', './Modifydtdetails').submit();
		}
		;
	}
	//yes
	function changeYes() {

		$("#addDTbtn").prop('value', 'crossDTSubYes');
		$("#addDTbtn").prop('value', 'crossDTYes');
		var btnValue = $('#addDTbtn').val();
		$("#DttypeDivId").hide();
		$("#DtcapDivId").hide();
		$("#DtPhaseDivId").hide();
		$("#TpDtDivId").hide();
		$("#TpParentDivId").hide();
		$("#CircleDivId").hide();
		$("#SubstationDivId").show();
		$("#CrossMeterDivId").show();
		$("#FeederDivId").show();
		$("#ConsumptionperDivId").show();
		$("#ConsumptionperDivId").hide();
		$("#DivId").hide();
		$("#DTnameListId").show();
		$("#DtnameDivId").hide();
		$("#meterno").attr('readonly', false);

	}
	//no
	function changeNO(param) {
		$("#addDTbtn").prop('value', 'crossDTNo');
		var btnValue = $('#addDTbtn').val();
		//document.getElementById('addDTdetailsId').reset();
		$("#CircleDivId").hide();
		$("#DivId").hide();
		$("#SubstationDivId").hide();
		$("#SubDivId").show();
		//$("#towndivId").show();
		$("#CrossMeterDivId").hide();
		$("#FeederDivId").hide();
		$("#ConsumptionperDivId").hide();
		$("#DttypeDivId").show();
		$("#ParentfsubDivId").show();
		$("#ParentfDivId").show();
		$("#DtcapDivId").show();
		$("#DtPhaseDivId").show();
		$("#TpDtDivId").show();
		$("#TpParentDivId").show();
		$("#MeterSrDivId").show();
		$("#MeterMFDivId").show();
		$("#MFDivId").show();
		$("#crossdt").show();
		$("#DTnameListId").hide();
		$("#DtnameDivId").show();
		$("#meterno").attr('readonly', false);

	}
	function editDTdetails(param) {
		$("#mtrdatechngdivId").hide();
		$("#mfdatechngdivId").hide();
		$("#addUpdatebtnId").val("2");
		document.getElementById('addDTdetailsId').reset();
		document.getElementById('addData').innerHTML = "";
		document.getElementById('addData').innerHTML = 'Edit DT Details';
		$("#MeterMFDivId").hide();
		$("#metermanufacturer").hide();
		$("#CircleDivId").hide();
		$("#SubDivId").hide();
		$("#DivId").hide();
		$("#towndivId").hide();
		$("#SubstationDivId").hide();
		$("#CrossMeterDivId").hide();
		$("#FeederDivId").hide();
		$("#ConsumptionperDivId").hide();
		$("#DttypeDivId").show();
		$("#ParentfsubDivId").hide();
		$("#parentfeedersub").hide();
		$("#ParentfDivId").hide();
		$("#parentfeeder").hide();
		$("#DtcapDivId").show();
		$("#DtPhaseDivId").show();
		$("#TpDtDivId").show();
		$("#TpParentDivId").show();
		$("#MeterSrDivId").show();
		$("#meterno").attr('readonly', true);
		$("#tpparentcode").attr('readonly', true);
		$("#tpdtcode").attr('readonly', true);
		//$("#MeterMFDivId").show();
		//$("#MFDivId").show();
		$("#MFDivId").hide();
		$("#crossdt").hide();
		$("#Updatedtbt").show();
		$("#addDTbtn").hide();
		$("#dtid").hide();
		$("#DTnameListId").hide();
		$("#DtnameDivId").show();
		$("#latitudeID").show();
		$("#longitudeID").show();
		
		

		var id = param;
		if (id == "" || id == null) {
			bootbox.alert("Nodata ");
			return false;
		}

		$.ajax({
			type : "GET",
			url : "./editdtdetails/" + id,
			dataType : "json",
			cache : false,
			async : false,
			success : function(response) {
				var data = response[0];
				$("#dtname").val(data[2]);
				$("#dttype").val(data[1]);
				$("#dttype").val(data[1]).trigger("change");
				$("#dtcapacity").val(data[3]);
				$("#tpdtcode").val(data[5]);
				$("#tpparentcode").val(data[6]);
				$("#meterno").val(data[7]);
				$("#oldmeterno").val(data[7]);
				$("#metermanufacturer").val(data[8]);
				$("#Consumptionper").val(data[9]);
				$("#dtId").val(data[0]);
				$("#mf").val(data[10]);
				$("#oldmf").val(data[10]);
				//$("#meterno").attr('readonly',true);
				//$("#oldmeterno").attr('readonly',true);
				$("#dtphase").val(data[4]);
				$("#latitude").val(data[11]);
				$("#longitude").val(data[12]);	
				setSelectValue('dtphase', data[4]);
			}
		});

		$('#' + param).attr("data-toggle", "modal");//edit button
		$('#' + param).attr("data-target", "#stack1"); //edit button 

	}

	function setSelectValue(id, val) {
		 $("#dtphase").val(val).trigger("change");
		// document.getElementById(id).value = val;
	}
	function changeSubYes() {
		//document.getElementById('addDTdetailsId').reset();
		$("#MeterSrDivId").show();
		$("#MeterMFDivId").show();
		$("#MFDivId").show();
		$("#ConsumptionperDivId").hide();
		$("#addDTbtn").prop('value', 'crossDTYes');

	}
	function changeSubNo(param) {
		$("#addDTbtn").prop('value', 'crossDTSubNo');
		var btnValue = $('#addDTbtn').val();
		//document.getElementById('addDTdetailsId').reset();

		$("#MeterSrDivId").hide();
		$("#MeterMFDivId").hide();
		$("#MFDivId").hide();
		$("#ConsumptionperDivId").show();
	}
	function checkMeterNo(meterno) {
		var btnvalue = $("#addUpdatebtnId").val();
		var oldmeter = $("#oldmeterno").val();
		if (meterno == "") {
			bootbox.alert("Please enter Meterno");
			$("#meterno").val("");
			return false;
		}
		if (btnvalue == "2") {
			if (meterno != oldmeter) {
				//$("#mfdatechngdivId").show();
				$("#mtrdatechngdivId").show();

			}
			if (meterno == oldmeter) {
				//$("#mfdatechngdivId").show();
				$("#mtrdatechngdivId").hide();

			}
			//$("#mfdatechngdivId").show();
			//$("#mtrdatechngdivId").show();
		}
		if (meterno != oldmeter) {
			$.ajax({
				type : "GET",
				url : "./checkMeterNoInInventory/" + meterno,
				dataType : "json",
				success : function(response) {
					if (response.length == 0) {
						bootbox.alert("Meter Number " + meterno
								+ " Not In Stock");
						$("#meterno").val("");
						if (btnvalue == "2") {
							$("#mtrdatechngdivId").hide();
						}
						return false;
					} else {
						for (var i = 0; i < response.length; i++) {
							if (response[0].meter_status == "INSTOCK") {
								checkMeterNoInMasterMain(meterno,
										response[0].meter_make);
							} else {
								bootbox.alert("Meter Number " + meterno
										+ " Already Installed");
								$("#meterno").val("");
								if (btnvalue == "2") {
									$("#mtrdatechngdivId").hide();
								}
								return false;

							}
						}
					}
				}
			});
		}
	}
	function getDTDeTailsByMeterNo() {
		var meterno = $("#meterNumId").val();
		$.ajax({
			type : "GET",
			url : "./getDTDeTailsByMeterNo/" + meterno,
			dataType : "json",
			success : function(response) {
				if (response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var data = response[i];
						/*  html+="<tr>";          
						html+="<td><a href='#' onclick='editDTdetails(this.id,\""+data.id+"\")' id='editData'>Edit</a></td>";
						html+="<td>"+data.meterno+"</td>";
						html+="<td>"+data.meter_connection_type+"</td>";
						html+="<td>"+data.meter_make+"</td>";
						html+="<td>"+data.meter_commisioning+"</td>";
						html+="<td>"+data.meter_model+"</td>";
						html+="<td>"+data.meter_ip_period+"</td>";
						html+="<td>"+data.pt_ratio+"</td>";
						html+="<td>"+data.meter_constant+"</td>";
						html+="<td>"+data.ct_ratio+"</td>";
						html+="<td>"+data.tender_no+"</td>";
						html+="<td>"+data.meter_accuracy_class+"</td>";
						html+="<td>"+data.month+"</td>";
						html+="<td>"+data.meter_current_rating+"</td>";
						html+="<td>"+data.warrenty_years+"</td>";
						html+="<td>"+data.meter_voltage_rating+"</td>";
						html+="<td>"+data.meter_status+"</td>";
						html+="</tr>";  */
					}
					$("#meterDetailsId").html(html);
				}

			}
		});
	}
	function DeleteDt(param) {
		$("#dtId").val(param);
		var id = $("#dtId").val();
		if (id != "") {
			bootbox.confirm("Are you sure want to delete this record ?",
					function(result) {
						if (result == true) {
							$("#addDTdetailsId").attr('action',
									'./deleteDtdetails').submit();

						}

					});
		} else {
			bootbox.alert("Deleting Unsuccessfully");
		}
	}

	function checkDuplicate() {
		var tpdtcode = $("#tpdtcode").val();
		$.ajax({
			url : "./checkDuplicatedtParent/" + tpdtcode,
			type : "GET",
			dataType : "text",
			async : false,
			cache : false,
			success : function(response) {

				if (response == 'Code Exist') {
					bootbox.alert("DT TP Code is already Exist");
					$("#tpdtcode").val("");
					return false;
				}
			}
		});
	}

	/* function getSubStations(subdivIdName)
	 {

	 var sub=subdivIdName.split('@');
	 var subdivision=sub[1];
	
	 $.ajax({
	 type : 'GET',
	
	 url : "./getSubStations",
	 data:{subdivision:subdivision},
	 async : false,
	 cache : false,
	 success : function(response)
	 {
	 //alert(response);
	 var html="";
	 var htmFeeder="";
	 var htmlDTC="";
	 if(response!=null)
	 {
	 htmlDTC+="<option value=0>Select DTC</option>"; 
	 html+="<option value=0>Select SubStation</option>"; 
	 htmFeeder+="<option value=0>Select Feeder</option>"; 
	 for(var i=0;i<response.length;i++)
	 {
	 html+="<option value='"+response[i][0]+"@"+response[i][1]+"'>"+response[i][0]+' -- '+response[i][1]+"</option>"; 
	 }
	 $("#dtNamesList").empty();
	 $("#dtNamesList").append(htmlDTC);
	 $("#parentfeeder").empty();
	
	 $("#parentfeeder").append(htmFeeder);
	 $("#parentfeedersub").empty();
	 $("#parentfeedersub").append(html);
	 $("#parentfeedersub").select2();
	
	 }
	
	
	 }
	 });
	 } */

	//get Towns 
	function getTowns(subdivIdName) {

		var sub = subdivIdName.split('@');
		var subdivision = sub[1];

		$
				.ajax({
					type : 'GET',

					url : "./getTownsBaseOnSubDiv",
					data : {
						subdivision : subdivision
					},
					async : false,
					cache : false,
					success : function(response) {
						//alert(response);
						var html = "";
						var htmFeeder = "";
						var htmlDTC = "";
						if (response != null) {
							htmlDTC += "<option value=0>Select DTC</option>";
							/* html+="<option value=0>Select Towns</option>"; */
							html += "	<label class='control-label'>Town:</label><option value=0>Select Towns</option>";
							htmFeeder += "<option value=0>Select Feeder</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option value='"+response[i][0]+"@"+response[i][1]+"'>"
										+ response[i][0]
										+ '-'
										+ response[i][1] + "</option>";

								$("#town").empty();
								$("#town").append(html);
								$("#town").select2();

							}
						}

					}
				});
	}
	//based on towns
	function getSubStations(subdivIdName) {
		var sub = subdivIdName.split('@');
		var towncode = sub[0];

		$
				.ajax({
					type : 'GET',

					url : "./getSubStationsByTownCode",
					data : {
						towncode : towncode
					},
					async : false,
					cache : false,
					success : function(response) {
						//alert(response);
						var html = "";
						var htmFeeder = "";
						var htmlDTC = "";
						if (response != null) {
							htmlDTC += "<option value=0>Select DTC</option>";
							html += "	<label class='control-label'>Sub-Station:</label><option value=0>Select SubStation</option>";
							htmFeeder += "<option value=0>Select Feeder</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option value='"+response[i][0]+"@"+response[i][1]+"'>"
										+ response[i][0]
										+ '-'
										+ response[i][1] + "</option>";
							}
							$("#dtNamesList").empty();
							$("#dtNamesList").append(htmlDTC);
							$("#parentfeeder").empty();

							$("#parentfeeder").append(htmFeeder);
							$("#parentfeedersub").empty();
							$("#parentfeedersub").append(html);
							$("#parentfeedersub").select2();

						}

					}
				});
	}

	function getFeeders(ssidName) {
		var sub = ssidName.split('@');
		var ssid = sub[0];
		$
				.ajax({
					type : 'GET',
					url : "./getFeeders",
					data : {
						ssid : ssid
					},
					async : false,
					cache : false,
					success : function(response) {
						//alert(response);
						var html = "";
						var htmlDTC = "";
						if (response != null) {
							htmlDTC += "<option value=0>Select DTC</option>";
							html += "<option value=0>Select Feeder</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option value='"+response[i][0]+"@"+response[i][1]+"'>"
										+ response[i][1] + "</option>";
							}
							$("#parentfeeder").empty();
							$("#dtNamesList").empty();
							$("#dtNamesList").append(htmlDTC);
							$("#parentfeeder").append(html);
							$("#parentfeeder").select2();
						}

					}
				});
	}

	function getDTC(fdrid) {
		var fid = fdrid.split("@");
		getDTTPCode(fid[0]);

		$
				.ajax({
					type : 'GET',
					url : "./getDtcByFeeders",
					data : {
						fdrid : fid[0]
					},
					async : false,
					cache : false,
					success : function(response) {
						var html = "";
						if (response != null) {
							html += "<option value=0>Select DTC</option>";
							for (var i = 0; i < response.length; i++) {

								html += "<option value='"+response[i][0]+"@"+response[i][1]+"'>"
										+ response[i][0]
										+ ' -- '
										+ response[i][1] + "</option>";
							}
							$("#dtNamesList").empty();
							$("#dtNamesList").append(html);
							$("#dtNamesList").select2();
						}

					}
				});
	}

	function checkMeterNoInMasterMain(meterno, mtrmke) {
		$.ajax({
			type : "GET",
			url : "./checkMeterNoInMasterMain/" + meterno,
			dataType : "json",
			success : function(response) {
				if (response == null) {
					$('#metermanufacturer').val(mtrmke);
				} else {
					bootbox.alert("Meter Number " + meterno
							+ " Already Installed");
					$("#meterno").val("");
					return false;

				}
			}
		});
	}
	function checkMF(mf) {
		var btnvalue = $("#addUpdatebtnId").val();
		var oldmf = $("#oldmf").val();
		//alert("oldmf--"+oldmf+"--newmf-"+mf);
		if (btnvalue == "2") {
			if (oldmf != mf) {
				$("#mfdatechngdivId").show();
			}
			if (oldmf == mf) {
				$("#mfdatechngdivId").hide();
			}

		}
	}

	function getAlldetailsData() {

		//var ssid = $('#substationDT').val();
		var region= $('#LFzone').val();
		var town = $('#LFtown').val();
		var circle = $("#LFcircle").val();
		var feeder = $('#feederTpId').val();

		/* alert("ssid--"+ssid);
		alert("town--"+town);
		alert("circle--"+circle); */
		if(region == '' || circle == null){
			 bootbox.alert("Please Select  Region");
			  return false;
			 }
		if (circle == '' || circle == null) {
			bootbox.alert("Please Select circle");
			return false;
		}

		if (town == '' || town == null) {
			bootbox.alert("Please Select Town");
			return false;
		}
		/* if(ssid=='' || ssid==null){
			bootbox.alert("Please Select Sub-Station");
			return false;
		} */
		if (feeder == '' || feeder == null) {
			bootbox.alert("Please Select Feeder");
			return false;
		}
		$("#imageee").show();
		// $("#Dtdetails").empty();
		$('#dt_report').dataTable().fnClearTable();
		$
				.ajax({
					type : "POST",
					url : "./getAlldetailsData",
					dataType : "json",
					data : {
						region : region,
						town : town,
						//ssid : ssid
						frdId : feeder,
						circle : circle
					},
					async : false,
					cache : false,
					success : function(response) {
						$("#imageee").hide();
						if (response.length > 0) {

							var html = "";
							for (var i = 0; i < response.length; i++) {
								var data = response[i];
								//	html += "<tr>"+
								//	 "<td><a href='#' onclick='editDTdetails(this.id)' id='"+data[0]+"'>Edit</a></td>"+
								/* "<td>"+(data[11] == null?"": data[11])+ "</td>"+ */
								//	 "<td>"+(data[1] == null?"": data[1])+ "</td>"+
								//	 "<td>"+(data[2] == null?"": data[2])+ "</td>"+
								//	 "<td>"+(data[3] == null?"": data[3])+ "</td>"+
								//	 "<td>"+(data[4] == null?"": data[4])+ "</td>"+
								//	 "<td>"+(data[6] == null?"": data[6])+ "</td>"+
								//	 "<td>"+(data[7] == null?"": data[7])+ "</td>"+			
								//	 "<td>"+(data[8] == null?"": data[8])+ "</td>"+
								//	 "<td>"+(data[9] == null?"": data[9])+ "</td>"+
								//	 "<td>"+(data[10] == null?"":data[10])+ "</td>"+
								//	 "<td><a href='#' onclick='DeleteDt(this.id)'id='"+data[11]+"'>Delete</a></td>"+
								//	"</tr>";
								html += "<tr>"
								<c:if test = "${editRights eq 'yes'}">
								+ "<td><a href='#' onclick='editDTdetails(this.id)' id='"+data[0]+"'>Edit</a></td>" </c:if>+

								 "<td>"+(data[20] == null?"": data[20])+ "</td>"+
								 "<td>"+(data[11] == null?"": data[11])+ "</td>"+
								 "<td>"+(data[12] == null?"": data[10]+"-"+data[12])+ "</td>"+
								 "<td>"+(data[15] == null?"": data[15])+ "</td>"+
								 "<td>"+(data[5] == null?"": data[5])+ "</td>"+	
								 "<td>"+(data[0] == null?"": data[0])+ "</td>"+
								 "<td>"+(data[1] == null?"": data[1])+ "</td>"+
								 "<td>"+(data[2] == null?"": data[2])+ "</td>"+
								 "<td>"+(data[3] == null?"": data[3])+ "</td>"+
								 "<td>"+(data[16] == null?"": data[16])+ "</td>"+
								 "<td>"+(data[17] == null?"": data[17])+ "</td>"+
								 "<td>"+(data[18] == null?"": data[18])+ "</td>"+
								 "<td>"+(data[19] == null?"": data[19])+ "</td>"+
								 "<td>"+(data[4] == null?"": data[4])+ "</td>"+
								 "<td>"+(data[13] == null?"": data[13])+ "</td>"+
								 "<td>"+(data[14] == null?"": data[14])+ "</td>"+
								 "<td><a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+data[6]+"' style='color:blue;'>"+data[6]+"</a></td>"+
								 "<td><a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+data[7]+"' style='color:blue;'>"+data[7]+"</a></td>"+
								 "<td>"+(data[8] == null?"": data[8])+ "</td>"
								 <c:if test = "${editRights eq 'yes'}">
								 /* +"<td><a href='#' onclick='DeleteDt(this.id)'id='"+data[0]+"'>Delete</a></td>" */
								 +"<td><a>Delete</a></td>"
								 </c:if> 
								 +"</tr>";
							  }

							/* var html="";
							 $.each(response,function(i,data){
								 html += "<tr>"+
								 "<td><a href='#' onclick='editDTdetails(this.id)' id='"+data[0]+"'>Edit</a></td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								 "<td>"+data[11]+ "</td>"+
								
								 "<td><a href='#' onclick='DeleteDt(this.id)'id='${data[11]}'>Delete</a></td>"+

								"</tr>"; 

							 });*/

							$("#imageee").hide();
							// $("#Dtdetails").empty();
							$('#dt_report').dataTable().fnClearTable();
							$("#Dtdetails").html(html);
							loadSearchAndFilter('dt_report');
						} else {
							$('#dt_report').dataTable().fnClearTable();
							$("#Dtdetails").html(html);
							bootbox	.alert("Data Not Found For given Input Fields");
							return false;
						}
					}
				});

	}
	function showTown(circle) {
		$
				.ajax({
					url : './showTownByCircle',
					type : 'GET',
					data : {
						circle : circle
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<label class='control-label'>Town:</label><select id='townDT' name='townDT' onchange='getFeederByTown(this.value)' class='form-control' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";

						for (var i = 0; i < response.length; i++) {
							//		alert("response[i]== "+response[i]);
							var resp = response[i];
							html += "<option  value='"+resp[1]+"'>" + resp[1]
									+ "-" + resp[0] + "</option>";
						}
						html += "</select><span></span>";
						$("#townDTId").html(html);
						$('#townDT').select2();
						//below Code for based on town Substation drop down
						/* $("#substationDT").val("").trigger("change");
						$('#substationDT').empty();
						$('#substationDT').find('option').remove();
						$('#substationDT').append($('<option>', {
							value : "",
							text : "Select Sub-Station"
						})); */
					}
				});
	}

	function showSubStaTionByTown(town) {
		$('#substation').val('').trigger('change');
		var town = $('#townDT').val();
		$('#substationDT').empty();
		$('#substationDT').find('option').remove();
		$('#substationDT').append($('<option>', {
			value : "",
			text : "Select Sub-Station"
		}));
		$('#substationDT').append($('<option>', {
			value : "%",
			text : "ALL"
		}));

		$.ajax({
			url : './showSubStaTionByTown',
			type : 'GET',
			data : {
				town : town
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				//$("#substation").val("").trigger("change");
				for (var i = 0; i < response1.length; i++) {
					var resp = response1[i];
					$('#substationDT').append($('<option>', {
						value : resp[0],
						text : resp[1]
					}));
				}
			}
		});
	}

	function getDTTPCode(val) {
		$.ajax({
			url : './getDTTPcodeByFdrid',
			type : 'GET',
			dataType : 'TEXT',
			data : {
				fdrid : val
			},
			success : function(response) {
				//alert("tpdt--"+response);

				//tpprntcode
				$("#tpparentcode").val(response);
				$("#tpparentcode").prop("readonly", true);
			}
		});
	}

	function exportPDF() {
		var circle = $("#LFcircle").val();
		var town = $('#LFtown').val();
		var ssid = $('#substationDT').val();
		var fdrID = $('#feederTpId').val();
		
		if (circle =='%'){
			circle='ALL'
		}
		if (town =='%'){
			town='ALL'
		}
		if (fdrID =='%'){
			fdrID='ALL'
		}

		//window.open("./dtdetailspdf/"+circle+"/"+town+"/"+ssid)
	//	window.location.href = ("./dtdetailspdf?circle=" + circle + "&town="+ town + "&ssid=" + ssid);
		window.location.href = ("./dtdetailspdf?circle=" + circle + "&town="+ town + "&fdrID=" + fdrID);
		

	}

	function downLoadSample(type) {

		//window.location.href="http://1.23.144.187:8102/downloads/sldreport/"+\type+".xlsx";
		window.location.href = "http://1.23.144.187:8102/downloads/sldreport/DtMaster.xlsx";
	}

	function finalSubmit() {
		if (document.getElementById("fileUpload").value == ""
				|| document.getElementById("fileUpload").value == null) {
			bootbox.alert(' Please Select xlsx file to upload');
			return false;
		}
	}

	var _validFileExtensions = [ ".xlsx" ]; //,".jpg","jpeg",".png",".gif"
	function ValidateSingleInput(oInput) {
		if (oInput.type == "file") {
			var sFileName = oInput.value;
			if (sFileName.length > 0) {
				var blnValid = false;
				for (var j = 0; j < _validFileExtensions.length; j++) {
					var sCurExtension = _validFileExtensions[j];
					if (sFileName.substr(
							sFileName.length - sCurExtension.length,
							sCurExtension.length).toLowerCase() == sCurExtension
							.toLowerCase()) {
						blnValid = true;
						break;
					}
				}

				if (!blnValid) {
					//bootbox.alert("Sorry,  " + sFileName + " is invalid, allowed extensions is: " + _validFileExtensions.join(", "));
					bootbox.alert("Only xlsx file is allowed to Upload");
					oInput.value = "";
					return false;
				}
			}
		}
		return true;
	}

	function downLoadSampleExcel() {
		var region = $("#LFzone").val();
		var circle = $("#LFcircle").val();
		var town = $('#LFtown').val();
		//var ssid = $('#substationDT').val();
		var fdrID = $('#feederTpId').val();
		/*/alert(circle);
		  alert(town);
		alert(fdrID);  */
		if (region =='%'){
			region='ALL'
		}
		if (circle =='%'){
			circle='ALL'
		}
		if (town =='%'){
			town='ALL'
		}
		if (fdrID =='%'){
			fdrID='ALL'
		}

		//window.location.href = ("./dtdetailssampleExcel?circle=" + circle+ "&town=" + town + "&ssid=" + ssid);
		window.open("./dtdetailssampleExcel?circle="+circle+"&town="+town+"&fdrID="+fdrID+"&region="+region);
	}

	function DTtableToExcel() {
		var region = $("#LFzone").val();
		var circle = $("#LFcircle").val();
		var town = $('#LFtown').val();
		//var ssid = $('#substationDT').val();
		var fdrID = $('#feederTpId').val();

		if(region =='%'){
			region='ALL'
		}
		if (circle =='%'){
			circle='ALL'
		}
		if (town =='%'){
			town='ALL'
		}
		if (fdrID =='%'){
			fdrID='ALL'
		}
		//window.location.href = ("./DtDetailsExcel?circle=" + circle + "&town="+ town + "&ssid=" + ssid);
		window.location.href = ("./DtDetailsExcel?circle="+circle+"&town="+town+"&fdrID="+fdrID+"&region="+region);
	}

	function clearfieldsNew() {

		$("#subdiv").val("").trigger("change");
		$("#town").val("").trigger("change");
		$("#parentfeedersub").val("").trigger("change");
		$("#parentfeeder").val("").trigger("change");

	}

	function showResultsbasedOntownCode(townCode) {
			//alert(townCode);
			var region = $("#LFzone").val();
			var circle = $("#LFcircle").val();

		$('#feederTpId').val('').trigger('change');
		//var town = $('#townDT').val();
		$('#feederTpId').empty();
		$('#feederTpId').find('option').remove();
		$('#feederTpId').append($('<option>', {
			value : "",
			text : "Select Feeder"
		}));
		$('#feederTpId').append($('<option>', {
			value : "%",
			text : "ALL"
		}));

		$.ajax({
			url : './getFeederBySelection',
			type : 'POST',
			dataType : 'json',
			asynch : false,
			cache : false,
			data : {
				townCode : townCode,
				circle :circle,
				region : region
			},
			success : function(response1) {
				var html = '';
				/*    html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>ALL</option>";
				   for (var i = 0; i < response1.length; i++) {
				       html += "<option value='"+response1[i][0]+"'>"
				               + response1[i][1] + "</option>";
				   }
				   html += "</select><span></span>";
				   $("#feederDivId").html(html);
				   $('#feederTpId').select2(); */

				for (var i = 0; i < response1.length; i++) {
					var resp = response1[i];
					$('#feederTpId').append($('<option>', {
						value : resp[0],
						text : resp[1]
					}));
				}
				$('#feederTpId').select2();

			}
		});
	}
	
	
	
	
	
	function showTownNameandCode(circle){
		var zone = $('#LFzone').val();
		//var zone =  '${newRegionName}';   
		   $.ajax({
		      	url:'./getTownNameandCodebyCircle',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
		  			zone : zone,
		  			circle:circle
		  		},
		  		success : function(response1) {
		  			
	                var html = '';
	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
	               
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#LFtownTd").html(html);
	                $('#LFtown').select2();
	                
	            }
		  	});
		  }

	
	
	
	
	function showCircle(zone) {
		$
				.ajax({
					url : './getCircleByZone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone
					},
					success : function(response) {
						var html = '';
						html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircle").html(html);
						$('#LFcircle').select2();
					}
				});
	} 
	
	
	
	
	
	
	
	
	
	
	
	
</script>