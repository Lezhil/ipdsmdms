<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- <link href="./resources/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" /> -->

<<script>
<!--

//-->
$( document ).ready(function() {
	App.init();
	TableManaged.init();
	FormComponents.init();	
	$('#MDASSideBarContents,#meterOper,#ondempro,#mdreset').addClass('start active ,selected');
	$("#MDMSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	resetFields();
	meterjoblist();
});

</script>


<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		bootbox.alert(msg);
	</script>
</c:if>

<div class="page-content">
	<div class="alert alert-success" id="succId" style="display: none;">
		<button class="close" data-close="alert"></button>
		<strong>Success!</strong> The job has been Created.
	</div>
	<div class="alert alert-danger" id="errId" style="display: none;">
		<button class="close" data-close="alert"></button>
		<strong>Error!</strong> Oops! Something went wrong!
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Reset Maximum Demand
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="form-group col-md-3">
							<label>Meter Group</label><font color="red">*</font>
							<div class="input-group">
								<span class="input-group-addon"> <i class="fa fa-group"></i>
								</span> <select id="mtrgrpname"
									class="form-control placeholder-no-fix select2me">
									<option value="" disabled="disabled" selected="selected">Select
										Group</option>
									<c:forEach var="elements" items="${groupList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group col-md-3">
							<label>Job Type</label><font color="red">*</font>
							<div class="input-group">
								<span class="input-group-addon"> <i class="fa fa-gears"></i>
								</span> <select class="form-control placeholder-no-fix select2me"
									id="jobtype">
									<option value="" disabled="disabled" selected="selected">Select
										Job Type</option>
									<!-- <option value="METER_COMMAND_GET">METER_COMMAND_GET</option> -->
									<option value="METER_COMMAND_SET">METER_COMMAND_SET</option>
								</select>
							</div>
						</div>
						
						<div class="form-group col-md-3">
							<label>Job Name</label><font color="red">*</font>
							<div class="input-group">
								<span class="input-group-addon"> <i class="fa fa-tag"></i>
								</span> <input type="text" class="form-control" id="job_name"
									name="job_name" placeholder="Enter Job Name"></input>
							</div>
						</div>
						
						
						<div class="form-group col-md-3">
						
						<button type="button" id="savebtn" class="btn green" style="margin-top: 25px;"
							onclick="return validateFields();">Create</button>
						
						<button type="button" class="btn btn-warning" style="margin-top: 25px; margin-left: 25px;"
							onclick="resetFields();">Reset</button>
						

					</div>
						
						
					</div>





					


					




					



				</div>
			</div>
		</div>
	</div>


	<div class="row" id="mjdid">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter Jobs
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">


					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>


					<BR> <BR>
					<!-- <div class="table-scrollable">	 -->
					<table class="table table-striped table-bordered table-hover"
						id="sample_4">
						<thead>
							<tr>
								<th>S.no</th>
								<th>Job Type</th>
								<th>Job Name</th>
								<th>Meter Group</th>
								<th>Created Time</th>
								<th></th>
								<th></th>
								<th></th>
								<!-- <th></th> -->
							</tr>
						</thead>
						<tbody id="mjid">


						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="qryDetails" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #4b8cf8;">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<font style="font-weight: bold; color: white;">Job Details</font></a>
				</h4>
			</div>

			<div class="modal-body">
				<div class="table-toolbar">
					<table class="table table-striped table-bordered table-hover"
						id="sample_5">

						<tbody id="mqjbid">
							<tr>
								<td class="hid">Job Name</td>
								<td id="jn"></td>
							</tr>
							<tr>
								<td class="hid">Meter Group</td>
								<td id="mg"></td>
							</tr>
							<tr>
								<td class="hid">Scheduled Time</td>
								<td id="st"></td>
							</tr>
							<tr>
								<td class="hid">Timeout Time</td>
								<td id="tt"></td>
							</tr>
							<tr>
								<td class="hid">Create Time</td>
								<td id="ct"></td>
							</tr>
							<tr>
								<td class="hid">Update Time</td>
								<td id="ut"></td>
							</tr>
							<tr>
								<td class="hid">Status</td>
								<td id="sta"></td>
							</tr>
							<tr>
								<td class="hid">Job Type</td>
								<td id="jt"></td>
							</tr>
							<tr>
								<td class="hid">Total Processed Devices</td>
								<td id="tpd"></td>
							</tr>
							<tr>
								<td class="hid">Total Successful Devices</td>
								<td id="tsd"></td>
							</tr>
							<tr>
								<td class="hid">Total Failed Devices</td>
								<td id="tfd"></td>
							</tr>

							
						</tbody>
					</table>


				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn dark btn-outline"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>



<div class="modal fade" id="mtrStatusJob" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #4b8cf8;">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<font style="font-weight: bold; color: white;">Meter Status For Job</font></a>
				</h4>
			</div>

			<div class="modal-body">
				<div class="table-toolbar">
					<table class="table table-striped table-bordered table-hover" id="sample_6">
						<thead>
							<tr>
								<th>S.no</th>
								<th>Meter No</th>
								<th>Status</th>
								<th>Failure Step</th>
								
							</tr>
						</thead>
						<tbody id="mtrStatusJobtbody">

						</tbody>
							
						
					</table>


				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn dark btn-outline"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>




<script>
	function reloadPage() {
		location.reload(true);
	}

	function validateFields() {

		var mtrgrpname = $("#mtrgrpname").val();
		var jobtype = $("#jobtype").val();
		var job_name = $("#job_name").val();
		

		if (mtrgrpname == null || mtrgrpname == "") {
			bootbox.alert("Please Select Meter Group.");
			return false;
		}
		if (jobtype == null || jobtype == "") {
			bootbox.alert("Please Select Job Type.");
			return false;
		}
		if (job_name == null || job_name == "") {
			bootbox.alert("Please Enter Job Name.");
			return false;
		}


		var commands = {};
		var types = [];
		types.push({
			"type" : "MAX_DEMAND_RESET",
			

		});

		commands = {
			"commands" : types
		};
		var jsons = {
			"jobConfiguration" : commands,
			"jobType" : jobtype,
			"jobName" : job_name,
			"meterGroup" : mtrgrpname
		};

		var metersf = JSON.stringify(jsons);
		//alert(metersf);
		
		
		$.ajax({
			url : "./createmeterjob",
			type : 'GET',
			dataType : 'text',
			data : {
				"mtrnos" : metersf
			},
			cache : false,
			success : function(response) {
				//alert(response);
				if (response == "201") {
					$("#succId").show();
					setTimeout(function() {
				        $("#succId").fadeOut(1500);
				    },5000);
					meterjoblist();
					return true;
				} else {
					$("#errId").show();
					setTimeout(function() {
				        $("#errId").fadeOut(1500);
				    },5000);
				}
			},
			error : function(xhr) {
				
				alert(error);
				$("#errId").show();
				setTimeout(function() {
			        $("#errId").fadeOut(1500);
			    },5000);
			}

		});
		resetFields();
		meterjoblist();
	}

	function resetFields() {

		$("#mtrgrpname").val("").trigger("change");
		$("#jobtype").val("").trigger("change");
		$("#job_name").val("");
		

	}

	function meterjoblist() {
		$("#mjid tr").remove();
		$
				.ajax({
					url : "./meterjoblist/MAX_DEMAND_RESET",
					type : "GET",
					success : function(response) {

						//alert(r);
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var r = response[i];

							html += "<tr>";
							html += "<td>" + (i + 1) + "</td>";
							html += "<td>" + r[1] + "</td>";
							html += "<td>" + r[2] + "</td>";
							html += "<td>" + r[3] + "</td>";
							html += "<td>"
									+ moment(r[5])
											.format("YYYY-MM-DD HH:mm:ss")
									+ "</td>";
							html += "<td> <input type='button' class='btn green' id='qryJobid'  value='Query Job' onclick='return querymeterjob(\"" + r[2] + "\")'/></td>";
							html += "<td> <input type='button' class='btn blue' id='qryJobid'  value='Start Job' onclick='return managemeterjob(\"" + r[2] + "\")'/></td>";
							html += "<td> <input type='button' class='btn btn-primary' id='meterStatus'  value='Meter Status' onclick='return meterstatusforjob(\"" + r[2] + "\")'/></td>";

							html += "</tr>";
						}
						$('#sample_4').dataTable().fnClearTable();
						$("#mjid").html(html);
						loadSearchAndFilter1('sample_4');
					}
				});
	}

	function querymeterjob(jobname) {

		$.ajax({
			url : "./querymeterjobNew/" + jobname,
			type : 'GET',
			dataType: "JSON",
			cache : false,
			success : function(response) {
				var v = response;
				//alert(v);
				$("#jn").html(v.jobName);
				$("#mg").html(v.meterGroup);
				$("#st").html(v.scheduledTime);
				$("#tt").html(v.timeoutTime);
				$("#ct").html(v.createTime);
				$("#ut").html(v.updateTime);
				$("#sta").html(v.status);
				$("#jt").html(v.jobType);
				$("#tpd").html(v.totalProcessedDevices);
				$("#tsd").html(v.totalSuccessfulDevices);
				$("#tfd").html(v.totalFailedDevices);
				var jc = v.jobConfiguration;
				var t1 = jc.name;
				var t2 = jc.commands;
				var html = '';
				

				$("#mqjbbid").html(html);
				$('#qryDetails').modal('show');
			}
		});

	}
	
	function managemeterjob(jobName) {
		$
				.ajax({
					url : "./manageMeterJob/" + jobName,
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(response) {
						if (response == "200") {
							bootbox.alert("Job Started.")
						} else{
							bootbox.alert("Opps! Something went wrong!")
						}

						
					}
				});

	}
	
	
	function meterstatusforjob(jobName) {
		
		$.ajax({
			url : "./meterStatusForJobNew/" + jobName,
			type : 'GET',
			dataType: "JSON",
			cache : false,
			success : function(response) {
				var meters=response.meters;
				//alert(JSON.stringify(meters));
				var html="";
				for (var i = 0; i < meters.length; i++) {
					var r = meters[i];
					
					//alert(r.meterId+"----"+r.status+"----"+r.failureStep);
					
					html += "<tr>";
					html += "<td>" + (i + 1) + "</td>";
					html += "<td>" + r.meterId + "</td>";
					html += "<td>" + r.status + "</td>";
					html += "<td>" + r.failureStep + "</td>";

					html += "</tr>";
				}
				
				$('#sample_6').dataTable().fnClearTable();
				$("#mtrStatusJobtbody").html(html);
				loadSearchAndFilter1('sample_6');
				$('#mtrStatusJob').modal('show');
				
			}
		});
		
	}
	
	
</script>

