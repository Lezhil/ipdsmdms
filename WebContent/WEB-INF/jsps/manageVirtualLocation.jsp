<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>

<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>" type="text/javascript"></script>

<script type="text/javascript">
$(".page-content").ready(function()
    	   {  
	
	App.init();
	TableEditable.init();
	FormComponents.init();
    $('#MDMSideBarContents,#mpmId,#manageVirtualLoaction').addClass('start active ,selected');
 	$("#MDASSideBarContents,#ADMINSideBarContents,#DATAEXCHsideBarContents,#metermang,#surveydetails,#360MeterDataViewID,#dataValidId,#DPId,#alarmID,#reportsId,#mdmDashId,#eaId,#todEconomcsId").removeClass('start active ,selected');
 	
 	$("#feedId").hide();
 	$("#dtviewid").hide();
 	$('#optionsRadios1').click();
 	$('#optionsRadios1').click();
 	$('#optionsRadios1').click();
 	$('#optionsRadios1').click();
 	loadSearchAndFilter('sample_1'); 
 	
 			
	});

</script>
<div class="page-content">


	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Manage Virtual Location
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<div class="btn-group">
						<button class="btn green" data-target="#wide" data-toggle="modal"
							id="addData" value="addSingle">
							Add Virtual Location <i class="fa fa-plus"></i>
						</button>
						&nbsp;&nbsp;&nbsp;
					</div>
					<br /> <br />
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Virtual Location')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Delete</th>
								<th>Edit</th>
								<th>View</th>
								<th>Subdivision</th>
								<th>virtual Location</th>
								<th>Location Type</th>
								<th>Entry By</th>
								<th>Entry Date</th>
								<th>Update By</th>
								<th>Update Date</th>

							</tr>
						</thead>
						<tbody id="locId">
							<c:set var="count" value="1" scope="page"></c:set>
							<c:forEach var="element" items="${VLlist}">
								<tr>
									<td><a href="#"
										id="${element.vlID}@${element.locationType}" href="#"
										data-target="#vldelete" data-toggle="modal"
										onclick="deletevirtualloc(this.id)"> Delete </a></td>
									<td><a href="#" id="${element.vlID}" href="#"
										data-target="#vlupdate" data-toggle="modal"
										onclick="updatevirtualloc(this.id)"> Edit </a></td>
									<td><a href="#"
										id="${element.vlID}@${element.locationType}@${element.subdivision}@${element.vlName}"
										data-target="#wide1" data-toggle="modal"
										onclick="vlocationdata(this.id)"> View </a></td>
									<td>${element.subdivision}</td>
									<td>${element.vlName}</td>
									<td>${element.locationType}</td>
									<td>${element.entryBy}</td>
									<td>${element.entryDate}</td>
									<td>${element.updateBy}</td>
									<td>${element.updateDate}</td>
								</tr>
								<c:set var="count" value="{count+1}" scope="page"></c:set>
							</c:forEach>
						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="wide1" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Virtual Location Meters</h4>
			</div>
			<div class="modal-body">
				<div class="row" id="level1">
					<div class="col-md-12">

						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>Consumer Wise Meters
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
								</div>
							</div>

							<div class="portlet-body">

								<br /> <br />
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport1"
												onclick="tableToExcel1('sample_5', 'Meters List')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>

								<table class="table table-striped table-hover table-bordered"
									id="sample_5">
									<thead>
										<tr>


											<th>SubDivision</th>
											<th hidden="true">Location Type</th>
											<th hidden="true">Virtual Location Name</th>
											<th>Account No</th>
											<th>K No</th>
											<th>Consumer Name</th>
											<th>Meter Sr No</th>


										</tr>
									</thead>
									<tbody id="consumerbodyid">



									</tbody>

								</table>

							</div>
						</div>
					</div>
				</div>
				<div class="row" id="level2">
					<div class="col-md-12">

						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>Feeder Wise Meters
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
										
								</div>
							</div>

							<div class="portlet-body">

								<br /> <br />
							   <div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li>
											<a href="#" id="excelExport_fdr"
											   onclick="tableToXlxs('sample_6','Feeder_Details');">Export to Excel</a>
											</li>
										</ul>
									</div>
								</div> 
								

								<table class="table table-striped table-hover table-bordered"
									id="sample_6">
									<thead>
										<tr>
											<th>SubDivision</th>
											<th hidden="true">Location Type</th>
											<th hidden = "true">Virtual Location Name</th>
											<th>Feeder Name</th>
											<th>Third Party Feeder Code</th>
											<th>Meter Sr No</th>
										</tr>
									</thead>
									<tbody id="feederbodyid">



									</tbody>

								</table>

							</div>
						</div>
					</div>
				</div>
				<div class="row" id="level3">
					<div class="col-md-12">

						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>DT Wise Meters
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a> 
								</div>
							</div>

							<div class="portlet-body">

								<br /> <br />
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="#" id="excelExport_dt"
												onclick="tableToXlxs('sample_7', 'DT_Details')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>

								<table class="table table-striped table-hover table-bordered"
									id="sample_7">
									<thead>
										<tr>
											<th>SubDivision</th>
											<th hidden="true">Location Type</th>
											<th hidden = "true">Virtual Location Name</th>
											<th>DT Name</th>
											<th>Third Party DT Code</th>
											<th>Parent Feeder Id</th>
											<th>Meter Sr No</th>


										</tr>
									</thead>
									<tbody id="dtbodyid">



									</tbody>

								</table>

							</div>
						</div>
					</div>
				</div>

				<div class="row" id="level4">
					<div class="col-md-12">

						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>No Meters available in the virtual
									location
								</div>
								<div class="tools">
									<a href="javascript:;" class="remove"></a>
								</div>
							</div>


						</div>
					</div>
				</div>


			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade" id="wide" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Add Virtual Location</h4>
			</div>
			<div class="modal-body">
				<div class="row" id="virtualLocationId">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Virtual Location Creation
							</div>
						</div>
						<div class="portlet-body">
					
					<form action="#" id="vlcreationform" class="horizontal-form">


						<div class="form-body">
							<!-- <h3 class="form-section">Person Info</h3> -->

							<div class="row">
							
							   <div class="col-md-5">
									<div class="form-group">
										<!-- <label class="control-label">Zone</label>  -->
										<select class="form-control select2me" id="zone"
											name="zone" onchange="showCircle(this.value);">
											<option value="">Select Zone</option>
											<c:forEach var="zonename" items="${ZoneList}">
												<option value="${zonename}">${zonename}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<!--/span-->
								<div class="col-md-5">
									<div id="circleTd" class="form-group">
										<!-- <label class="control-label">Circle</label> -->
										<select class="form-control select2me" id="circle"
											name="circle" onchange="showDivision(this.value);">
											<option value="">Select Circle</option>

										</select>
									</div>
								</div> 
								<!--/span-->
							</div>


							<div class="row">

								<div class="col-md-5">
									<div  id="divisionTd" class="form-group">
										<!-- <label class="control-label">Division</label> -->
										 <select id='division' name='division' onchange='showSubdivByDiv(this.value)'
											class='form-control select2me'>
											<option value="">Select Division</option>
											</select>
									</div>
								</div>

								<!--/span-->

								<div class="col-md-5">
									<div id="subdivTd" class="form-group ">
										<!-- <label class="control-label">Sub Division</label> -->
										<select
											class="form-control select2me" id="sdoCode"
											name="sdoCode">
											<option value="">Select Sub-Division</option>

										</select>
									</div>
								</div>
								<!--/span-->
							</div>


							<div class="row">

								<div class="col-md-5">
									<div class="form-group">
										<label>Location Name</label><input type="text" id="locName"
											onkeyup="vlfind()" onkeydown="vlfind()" class="form-control"></input>
										<div id="error"></div>
									</div>
								</div>
								<div class="col-md-5">
									<div class="form-group">
										<label class="control-label">Location Type</label>
										<div class="radio-list">
											<label class="radio-inline"> <input type="radio"
												name="optionsRadios" id="optionsRadios1" value="option1"
												checked onclick="conHide()"> Consumer
											</label> <label class="radio-inline"> <input type="radio"
												name="optionsRadios" id="optionsRadios2" value="option2"
												onclick="feederHide()"> Feeder
											</label> <label class="radio-inline"> <input type="radio"
												name="optionsRadios" id="optionsRadios3" value="option3"
												onclick="DTHide()"> DT
											</label>
										</div>
									</div>
								</div>

								<!--/span-->
							</div>
							<div class="row">
							   <div class="col-md-5">
										<div class="form-group">
											<input type="text" id="mtrno"  
												class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr No." name="mtrno"
												maxlength="12"></input>
										</div>
								</div>
							    <div class="col-md-5">
										<div class="form-group">
											<input type="text" id="acno" style="display: none"
											
													class="form-control placeholder-no-fix"
													placeholder="Enter Account No." name="acno"
													maxlength="12"></input>
										</div>
								</div>
								<!-- <div class="col-md-5">
										<div class="form-group">
												<input type="text" id="kno"  hidden="true"
													class="form-control placeholder-no-fix"
													placeholder="Enter K No." name="kno" maxlength="12"></input>
										</div>
								</div> -->
								<!-- <div class="col-md-5">
										<div class="form-group">
											<input type="text" id="dtmtrno" hidden="true"
												class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr No." name="dtmtrno"
												maxlength="12"></input>
										</div>
								</div> -->
								<div class="col-md-5">
										<div class="form-group">
												<input type="text" id="dtcode" style="display: none;margin-top: -15px;"
														class="form-control placeholder-no-fix"
														placeholder="Enter DT Code." name="dtcode"
														maxlength="12"></input>
										</div>
								</div>
								<!-- <div class="col-md-5">
										<div class="form-group">
											<input type="text" id="feedermtrno" hidden="true"
												class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr No." name="feedermtrno"
												maxlength="12"></input>
										</div>
								</div> -->
								<div class="col-md-5">
										<div class="form-group">
												<input type="text" id="fdcode" style="display: none; margin-top: -30px;"
													class="form-control placeholder-no-fix"
													placeholder="Enter Feeder Code." name="fdcode"
													maxlength="12"></input>
										</div>
								</div>
							</div>

						</div>

						<div class="form-actions right">
							<button type="button" onclick="meterSearch()" class="btn blue">
								<i class="fa fa-check"></i> Search
							</button>
						</div>
					</form>
					</div>
					</div>

					<!-- END FORM-->
				</div>

				<div class="row" id="consumID">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>Consumer List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_2">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_2 .checkboxes" /></th>
											<th>SubDivision</th>
											<th>Account No</th>
											<th>K No</th>
											<th>Consumer Name</th>
											<th>Consumer Category</th>
											<th>Meter Sr No</th>
										</tr>
									</thead>
									<tbody id="consumerbid">



									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="row" id="feedId">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>Feeder List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_3">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_3 .checkboxes" /></th>
											<th>SubDivision</th>
											<th>Feeder Name</th>
											<th>Feeder Code</th>
											<th>Parent Sub-Station</th>
											<th>Third Party Feeder Code</th>
											<!-- <th>Parent Substation Name</th> -->
											<th>Meter Sr No</th>
										</tr>
									</thead>
									<tbody id="feederbid">
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="row" id="dtviewid">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>DT List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_4">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_4 .checkboxes" /></th>
											<th>SubDivision</th>
											<!-- <th >DT Code</th> -->
											<th>DT Name</th>
											<th>Third Party DT Code</th>
											<th>Parent Feeder Id</th>
											<th>Parent Feeder Name</th>
											<th>Meter Sr No</th>
										</tr>
									</thead>
									<tbody id="dtbid">


									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">Close</button>
				<button type="button" class="btn blue" onclick="savecustdata()">Save
					changes</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<div class="modal fade" id="vlupdate" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Update Virtual Location</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Virtual Location Update
							</div>
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="#" method="post" id="vlocformupdate"
							class="horizontal-form" name="vlformupdate">
							<div class="form-body">
								<div class="row">
									<div class="col-md-4">
										<div class="col-md-4" hidden="true">

											<label class="control-label">Location ID</label> <input
												type="text" id="vlocid" name="vlocid"></input>
										</div>
									</div>
								</div>
								<div class="row">

									<div class="col-md-4">
										<div class="form-group ">
											<label class="control-label">Sub Division</label><input
												class="form-control input-medium" id="sdoupdate"
												name="subdivisionname" readonly>
										</div>
									</div>


									<div class="col-md-4">
										<div class="form-group">
											<label>Location Name</label><input type="text"
												id="vlocupdate" name="vlocname" onkeyup="vlfind2()"
												onkeydown="vlfind2()" class="form-control"></input>
											<div id="error1"></div>
										</div>
									</div>
								</div>
								<div class="row" hidden= "true">
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label">Location Type</label> <input
												class="form-control input-medium" id="locationtypeupdate"
												name="locationtypename" readonly>
										</div>
									</div>
								</div>
							</div>
						</form>

						<!-- END FORM-->
					</div>
				</div>
				<div class="row" id="consumIDupdate">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>Consumer List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_8">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_8 .checkboxes" /></th>
											<th>SubDivision</th>
											<th>Account No</th>
											<th>K No</th>
											<th>Consumer Name</th>
											<th>Meter Sr No</th>
											<th hidden="true">VL Id</th>
										</tr>
									</thead>
									<tbody id="consumerbidupdate">



									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="row" id="feedIdupdate">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>Feeder List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_9">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_9 .checkboxes" /></th>
											<th>SubDivision</th>
											<th>Feeder Name</th>
											<th>Third Party Feeder Code</th>
											<th>Meter Sr No</th>
											<th hidden="true">VL Id</th>
										</tr>
									</thead>
									<tbody id="feederbidupdate">
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="row" id="dtviewidupdate">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>DT List
							</div>
							<div class="actions"></div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="sample_10">
									<thead>
										<tr>
											<th style="width1: 8px;"><input type="checkbox"
												class="group-checkable" data-set="#sample_10 .checkboxes" /></th>
											<th>SubDivision</th>
											<!-- <th >DT Code</th> -->
											<th>DT Name</th>
											<th>Third Party DT Code</th>
											<th>Parent Feeder Id</th>
											<th>Meter Sr No</th>
											<th hidden="true">VL Id</th>
										</tr>
									</thead>
									<tbody id="dtbidupdate">


									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>


				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">Close</button>
					<button type="button" class="btn blue" onclick="savevlocdata()">Update
						changes</button>
				</div>
				<!-- END FORM-->
			</div>


		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
<form method="post" id="delVL">
	<input type="hidden" id="delvlid" name="delVirtualLocation">
</form>
<script>
	
	
	function updatevirtualloc(data)
		{
			$("#vlocid").val(data);
			$.ajax({
				type: "GET",
				url: "./getVirtualLocation/"+data,
				data: {
					vlid:data
				},
				success : function(response)
							{
								$("#sdoupdate").val(response.subdivision);
								$("#vlocupdate").val(response.vlName);
								$("#locationtypeupdate").val(response.locationType);
								if(response.locationType=="Consumer")
									{
									conHide1();
									var ltype='cons';
									meterSearch2(ltype);
									}
								if(response.locationType=="Feeder")
									{
									feederHide1();
									var ltype='feeder';
									meterSearch2(ltype);
									}
								if(response.locationType=="DT")
									{
									DTHide1();
									var ltype='dt';
									meterSearch2(ltype);
									}
							}
			});
		}
		
	function deletevirtualloc(data)
	{
		bootbox.confirm("Are you sure want to delete this record ?", function(result) 
				{
					if(result == true)
		            {
			          $("#delvlid").attr("value",data);
			          $('form#delVL').attr('action','./deleteVL').submit();
		            }
				});
	}
		function vlocationdata(data){
			$.ajax({
				url:'./vlConsumData',
				type:'GET',
				data:{
					ldata:data
					
				},
				success:function(res){
					var html='';
					var fhtml='';
					var dhtml='';
					$.each(res,function(i,v)
							{
						if(v[1]=="Consumer")		
							{
						html+='<tr><td>'+v[0]+'</td><td hidden="true">'+v[1]+'</td><td hidden = "true">'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td></tr>';
							}
						else if(v[1]=="Feeder")		
						{
						fhtml+='<tr><td>'+v[0]+'</td><td hidden="true">'+v[1]+'</td><td hidden = "true">'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td></tr>';
						}
						else if(v[1]=="DT")		
						{
						dhtml+='<tr><td>'+v[0]+'</td><td hidden="true">'+v[1]+'</td><td hidden = "true">'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td></tr>';
						}
						
					});
					if(fhtml==''&&dhtml==''&&html=='')
						{
						$("#level1").hide();
						$("#level2").hide();
						$("#level3").hide();
						$("#level4").show();
						}
					else if(fhtml==''&&dhtml=='')
					{
					clearTabledataContent('sample_5');
					$('#consumerbodyid').html(html);
					$("#level1").show();
				 	$("#level2").hide();
				 	$("#level3").hide();
				 	$("#level4").hide();
				 	loadSearchAndFilter('sample_5'); 
					}
					else if(html==''&&dhtml=='')
					{
					clearTabledataContent('sample_6');
					$('#feederbodyid').html(fhtml);
					$("#level1").hide();
				 	$("#level2").show();
				 	$("#level3").hide();
				 	$("#level4").hide();
				 	loadSearchAndFilter('sample_6'); 
					}
					else if(html==''&&fhtml=='')
					{
					clearTabledataContent('sample_7');
					$('#dtbodyid').html(dhtml);
					$("#level1").hide();
				 	$("#level2").hide();
				 	$("#level3").show();
				 	$("#level4").hide();
				 	loadSearchAndFilter('sample_7'); 
					}
				}
			
			});
			
		}
		function conHide(){
			$("#consumID").show();
			$("#acno").show();
			//$("#kno").show();
			//$("#mtrno").show();
			$("#feedId").hide();
		 	$("#dtviewid").hide();
		 	$("#fdcode").hide();
			$("#feedermtrno").hide();
			$("#dtcode").hide();
			$("#dtmtrno").hide();
		}
		function conHide1(){
		 	$("#consumIDupdate").show();
			$("#feedIdupdate").hide();
		 	$("#dtviewidupdate").hide();
		}
		function feederHide(){
			$("#consumID").hide();
			$("#feedId").show();
		 	$("#dtviewid").hide();
		 	$("#acno").hide();
		 	//$("#mtrno").hide();
			//$("#kno").hide();
			$("#fdcode").show();
			$("#feedermtrno").show();
			$("#dtcode").hide();
			$("#dtmtrno").hide();
		}
		function feederHide1()
		{
		 	$("#consumIDupdate").hide();
			$("#feedIdupdate").show();
		 	$("#dtviewidupdate").hide();
		}
		function DTHide(){
			$("#consumID").hide();
			$("#feedId").hide();
		 	$("#dtviewid").show();
		 	$("#acno").hide();
			//$("#kno").hide();
			//$("#mtrno").hide();
		 	$("#fdcode").hide();
			$("#feedermtrno").hide();
			$("#dtcode").show();
			$("#dtmtrno").show();
		}
		function DTHide1(){
		 	$("#consumIDupdate").hide();
			$("#feedIdupdate").hide();
		 	$("#dtviewidupdate").show();
		}
		/* function showCircle(zone)
		{
			$.ajax({
				url : './showCircleAmi' + '/' + zone ,
			    	type:'GET',
			    	dataType:'json',
			    	asynch:false,
			    	cache:false,
			    	success:function(response)
			    	{
		  			var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						
						$("#circle").html(html);
						$('#circle').select2();
			    	}
				});
		}
		
		function showDivision(circle)
		{
			var zone=$('#zone').val();
			$.ajax({
				url : './showDivisionAmi' + '/' + zone + '/' + circle,
			    	type:'GET',
			    	dataType:'json',
			    	asynch:false,
			    	cache:false,
			    	success:function(response)
			    	{
		  			var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						html+="<span></span>";
						$("#division").html(html);
						$('#division').select2();
			    	}
				});
		}
		
		
		function showSubdivByDiv(division)
		{
			var circle=$('#circle').val();
			var zone=$('#zone').val();
			$.ajax({
				url : './showSubdivByDivAmi' + '/' + zone + '/' + circle + '/'
				+ division,
			    	type:'GET',
			    	dataType:'json',
			    	asynch:false,
			    	cache:false,
			    	success:function(response1)
			    	{
		  			var html='';
			    		html+="<option value=''>Sub-Division</option>";
						for( var i=0;i<response1.length;i++)
						{
							//var response=response1[i];
							html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
						}
						html+="<span></span>";
						$("#sdoCode").html(html);
						$('#sdoCode').select2();
			    	}
				});
		} */

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
			    		html+="<select id='circle' name='circle' class='form-control' onchange='showDivision(this.value)'  type='text'><option value=''>Select Circle</option>";
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
								html += "<select id='division' name='division' class='form-control '  onchange='showSubdivByDiv(this.value)' type='text'><option value=''>Select Division</option>";
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
								html += "<select id='sdoCode' name='sdoCode' class='form-control'  type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
		function meterSearch2(ltype){
			var subdiv = $('#sdoupdate').val();
			var vlid = $('#vlocid').val();
			$.ajax({
				type: 'GET',
				url :'./VirtualLocationMetersUpdate',
				data:{
					subdivision: subdiv,
						locationType:ltype,
						vlid:vlid
				},
				success:function(res){
					var repc=res.key;
					html='';
					$.each(res,function(i,v){
						if(i=='con'){
						$.each(v,function(ic,vc){
						html+='<tr><td><input type="checkbox" class="checkboxes"  name="consumupdate" id="'+vc[5]+'" value="'+vc[2]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td><td>'+vc[4]+'</td><td hidden="true">'+vc[5]+'</td></tr>';
						});
						clearTabledataContent('sample_8');
						$("#consumerbidupdate").html(html);
						var items = document.getElementsByName('consumupdate');
						selectAll(vlid, items);
						loadSearchAndFilter('sample_8'); 
						}
						if(i=='feeder'){
							$.each(v,function(ic,vc){
							html+='<tr><td><input type="checkbox" class="checkboxes" name="feedupdate"  id="'+vc[4]+'" value="'+vc[1]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td><td hidden="true">'+vc[4]+'</td></tr>';
							});
							clearTabledataContent('sample_9');
							$("#feederbidupdate").html(html);
							var items = document.getElementsByName('feedupdate');
							selectAll(vlid, items);
							loadSearchAndFilter('sample_9'); 
							}
						if(i=='dt'){
							$.each(v,function(ic,vc){
							html+='<tr><td><input type="checkbox" class="checkboxes" name="dtdidupdate" id="'+vc[5]+'" value="'+vc[1]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td><td>'+vc[4]+'</td><td hidden="true">'+vc[5]+'</td></tr>';
							});
							clearTabledataContent('sample_10');
							$("#dtbidupdate").html(html);
							var items = document.getElementsByName('dtdidupdate');
							selectAll(vlid, items);
							loadSearchAndFilter('sample_10'); 
							}
						
					});
					
				}
			});
		}
		function selectAll(vlid, items) {
	        for (var i = 0; i < items.length; i++) {
	            if (items[i].id == vlid)
	                items[i].checked = true;
	        }
	    }
		function meterSearch(){
			var zone=$("#zone").val();
			var circle=$("#circle").val();
			var division=$("#division").val();
			var subdiv=$("#sdoCode").val();
			
			var mtrno=$("#mtrno").val();
			var acno=$("#acno").val();
			var dtcode=$("#dtcode").val();
			var fdcode=$("#fdcode").val();
			if(subdiv==null || subdiv==''){
				bootbox.alert("Please select sub division");
				return false;
			}
			if(mtrno==null || mtrno==''){
				mtrno="%";

			}
			if(acno==null || acno==''){
				acno="%";
			}
			if(dtcode==null || dtcode==''){
				dtcode="%";
			}
			if(fdcode==null || fdcode==''){
				fdcode="%";
			}
			var ltype;
			if($('#optionsRadios1').prop('checked')) {
				ltype="cons";
			}
			if($('#optionsRadios2').prop('checked')) {
				ltype="feeder";
			}
			if($('#optionsRadios3').prop('checked')) {
				ltype="dt";
			}
			/* if($("#isAgeSelected").is(':checked')){
				ltype="cons";
			} */
			
			
			$.ajax({
				type: 'GET',
				url :'./VirtualLocationMeters',
				data:{
					subdivision: subdiv,
					locationType:ltype,
					mtrno:mtrno,
					acno:acno,
					dtcode:dtcode,
					fdcode:fdcode
				},
				success:function(res){
					var repc=res.key;
					html='';
					$.each(res,function(i,v){
						if(i=='con'){
						$.each(v,function(ic,vc){
						html+='<tr><td><input type="checkbox" class="checkboxes" name="consum" value="'+vc[2]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td><td>'+vc[5]+'</td><td>'+vc[4]+'</td></tr>';
						});
						clearTabledataContent('sample_2');
						$("#consumerbid").html(html);
						loadSearchAndFilter('sample_2'); 
						}
						if(i=='feeder'){
							$.each(v,function(ic,vc){
							html+='<tr><td><input type="checkbox" class="checkboxes" name="feed" value="'+vc[1]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[4]+'</td><td>'+vc[6]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td></tr>';
							});
							clearTabledataContent('sample_3');
							$("#feederbid").html(html);
							loadSearchAndFilter('sample_3'); 
							}
						if(i=='dt'){
							$.each(v,function(ic,vc){
							html+='<tr><td><input type="checkbox" class="checkboxes" name="dtdid" value="'+vc[1]+'" /></td><td>'+vc[0]+'</td><td>'+vc[1]+'</td><td>'+vc[2]+'</td><td>'+vc[3]+'</td><td>'+vc[5]+'</td><td>'+vc[4]+'</td></tr>';
							});
							clearTabledataContent('sample_4');
							$("#dtbid").html(html);
							loadSearchAndFilter('sample_4'); 
							}
						
					});
					
				}
			});
		}
		function vlfind(){
			var vlname=$("#locName").val();
			$.ajax({
				type:'get',
				url:'./vlfind',
				data:{
					vlname:vlname
				},
				success:function(res){
					if(res==true){
						$('#error').show();
						document.getElementById('error').innerHTML="Location name must be unique";
						document.getElementById('error').style.color="red";
					}	
					else
						{
						$('#error').hide();
						}
				}
			});
		}
		function vlfind2(){
			var vlname=$("#vlocupdate").val();
			$.ajax({
				type:'get',
				url:'./vlfind',
				data:{
					vlname:vlname
				},
				success:function(res){
					if(res==true){
						$('#error1').show();
						document.getElementById('error1').innerHTML="Location name must be unique";
						document.getElementById('error1').style.color="red";
					}	
					else
						{
						$('#error1').hide();
						}
				}
			});
		}
		
		function savecustdata(){
			
			var consumli = [];
            var ltype;
			if($('#optionsRadios1').prop('checked')) {
				ltype="cons";
				 $.each($("input[name='consum']:checked"), function(){            
		            	consumli.push($(this).val());
		            });
			}
			if($('#optionsRadios2').prop('checked')) {
				ltype="feeder";
				$.each($("input[name='feed']:checked"), function(){            
	            	consumli.push($(this).val());
	            });
			}
			if($('#optionsRadios3').prop('checked')) {
				ltype="dt";
				$.each($("input[name='dtdid']:checked"), function(){            
	            	consumli.push($(this).val());
	            });
			}
			var vlname=$("#locName").val();
			if(vlname==''){
              bootbox.alert("Please insert any location name")
              return false;
				}
			var subdiv=$("#sdoCode").val();
			$.ajax({
				type:'GET',
				url:'./locationDataSaved',
				data:{
					conlist:consumli,
					locflag:ltype,
					vlname:vlname,
					subdivision:subdiv
				},
				success:function(res){
					
					bootbox.alert(res+" meters are saved in this location", function gotovl(){location.reload(true);});
						
				}	
			});
		}
		function savevlocdata()
		{
		var list = [];
		var vlname=$("#vlocupdate").val();
		var subdiv=$("#sdoupdate").val();
		var location = $("#locationtypeupdate").val();
		vlid = $("#vlocid").val();
        var ltype;
		if(location.localeCompare("Consumer")==0) {
			ltype="cons";
			 $.each($("input[name='consumupdate']:checked"), function(){            
	            	list.push($(this).val());
	            });
		}
		if(location.localeCompare("Feeder")==0) {
			ltype="feeder";
			$.each($("input[name='feedupdate']:checked"), function(){            
            	list.push($(this).val());
            });
		}
		if(location.localeCompare("DT")==0) {
			ltype="dt";
			$.each($("input[name='dtdidupdate']:checked"), function(){            
            	list.push($(this).val());
            });
		}

	
		$.ajax({
			type:'GET',
			url:'./locationDataUpdate',
			data:{
				conlist:list,
				locflag:ltype,
				vlname:vlname,
				subdivision:subdiv,
				lvid:vlid
			},
			success:function(res){
				
				bootbox.alert(res+" meters are updated in this location", function gotovl(){window.location.reload(true);});
				
			}	
		});
			
		}
		
		</script>