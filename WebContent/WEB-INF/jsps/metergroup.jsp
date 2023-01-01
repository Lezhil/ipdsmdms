<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						App.init();
						TableEditable.init();
						FormComponents.init();
						$('#MDASSideBarContents,#metergroup,#meterOper').addClass('start active ,selected');
						$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');

						
						
						/* App.init();
						TableManaged.init();
						FormComponents.init();
						$('#MDASSideBarContents,#meterOper,#meterGroup').addClass('start active ,selected');
						$(
								"#MDMSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected'); */
						loadSearchAndFilter('sample_3');
						loadSearchAndFilter('sample_4');
					});
/* 
	 $(document).ready(function() {
		$('.js-example-basic-multiple').select2();
	}); 
	 */
</script>
<script>
	function selectingoptions() {
		var api = $("#api").val();
		var bool = $("#boolean").val();

		if (api == "metergroup") {
			$("#tf").hide();
			$("#mgdid").show();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#managejob").hide();
			$("#createMtrjob").hide();
			$("#meterstatus").hide();
			metergrouplist();
		}

		else if (api == "meterjob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").show();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#managejob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			meterjoblist();

		}
		else if (api == "managejob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		}
		else if (api == "Queryjob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
		}
		else if (api == "MeterStatusForJob") {
			$("#tf").show();
			$("#mgdid").hide();
			$("#mjdid").hide();
			$("#mqjid").hide();
			$("#msfjid").hide();
			$("#createMtrjob").hide();
			$("#createMtrgrp").hide();
			$("#queryjob").hide();
			$("#managejob").hide();
		}

		else {
			$("#tf").show();
			$("#mgdid").hide();
		}

	}
	function metergrouplist() {
		$("#mgid tr").remove();
		$.ajax({
			url : "./metergrouplist",
			type : "GET",
			success : function(response) {
				var r = response;
				var html = "";
				for (var i = 0; i < r.length; i++) {
					html += "<tr>";
					html += "<td>" + (i + 1) + "</td>";
					html += "<td>" + r[i][1] + "</td>";
					html += "<td>" + r[i][2] + "</td>";
					html += "<td>"
							+ moment(r[i][4]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
				}
				$("#mgid").html(html);
				 /* $('#sample_3').DataTable( {
			   			destroy: true,
			   			"pageLength": 10,
		                "pagingType": "full_numbers",
			   			
			 	        dom: 'Bfrtip',
			 	        buttons: [
				 	                {
				 	                    extend: 'excelHtml5',
				 	                    title: 'Meter Group'
				 	                },
				 	                {
				 	                    extend: 'pdfHtml5',
				 	                    title: 'Meter Group'
				 	                }
				 	            ]
			 	    } ); */

			}
		});
	}
	function joblist(){
		$.ajax({
			url : "./jobList",
			type : "GET",
			success : function(response) {
				
				var html = "";
				jQuery.each(response ,function(i,val){
					html+='<option value='+val+'>'+val+'</option>';
				});
				$("#jobnamestatus").html(html);
				$("#jobnameQ").html(html);
				$("#managemeterjob").html(html);
				

			}
		});
	}
	function meterjoblist() {
		$("#mjid tr").remove();
		$.ajax({
			url : "./meterjoblist",
			type : "GET",
			success : function(response) {
				var r = response;
				var html = "";
				for (var i = 0; i < r.length; i++) {
					html += "<tr>";
					html += "<td>" + (i + 1) + "</td>";
					html += "<td>" + r[i][1] + "</td>";
					html += "<td>" + r[i][2] + "</td>";
					html += "<td>" + r[i][3] + "</td>";
					html += "<td>"
							+ moment(r[i][5]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
				}
				$("#mjid").html(html);

			}
		});
	}

	function allapis() {
		var api = $("#api").val();
		var bool = $("#boolean").val();
joblist();
		if (api == "metergroup") {
			$("#createMtrgrp").show();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		}

		if (api == "meterjob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").show();
			$("#queryjob").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
		}
		if (api == "managejob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").show();
			$("#queryjob").hide();
			$("#meterstatus").hide();
		}

		if (api == "MeterStatusForJob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#queryjob").hide();
			$("#meterstatus").show();
		}

		if (api == "Queryjob" && (bool == "true" || bool == "False")) {
			$("#createMtrgrp").hide();
			$("#createMtrjob").hide();
			$("#managejob").hide();
			$("#meterstatus").hide();
			$("#queryjob").show();
		}

	}

	function managemeterjob() {
		var jobName = $("#managejobname").val();
		$
				.ajax({
					url : "./manageMeterJob/" + jobName,
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(response) {
						if (response == "200") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = "Successfully Created";
							meterjoblist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = "Job Command Fail or Server Down";
					}
				});

	}

	function querymeterjob() {
		var jobname = $("#jobnameQ").val();
		$.ajax({
			url : "./querymeterjob/" + jobname,
			type : 'GET',

			cache : false,
			success : function(response) {
				var v = response;
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
				for (var i = 0; i < t2.length; i++) {
					html += '<tr><td>' + t2[i].type + '</td><td>'
							+ t2[i].active + '</td></tr>';

				}

				$("#mqjbbid").html(html);
				$("#mqjid").show();

			}
		});

	}

	function meterstatus() {

		var jobname = $("#jobnamestatus").val();
		var count = $("#count").val();
		var devicestatus = $("#device").val();
		var startid = $("#startid").val();
		$.ajax({
			url : "./meterStatusForJob/" + jobname + '/' + count + '/'
					+ devicestatus + '/' + startid,
			type : 'GET',
			cache : false,
			success : function(response) {
				var html='';
				
				var objJSON = typeof jData != 'object' ? JSON.parse(response)
						: jData;
				var ma = objJSON.meters;
				jQuery.each(ma, function(i, val) {
					var mi = val.meterId;
					var su = val.status;
					var fs = val.failureStep;
					var res = val.response;
					jQuery.each(res, function(j, val) {
						if (val.type == "BILLING_PERIOD") {
							
							html+='<tr><td rowspan="2">'+mi+'</td><td rowspan="2">'+su+'</td>';
								html+='	<td rowspan="2">'+fs+'</td><td rowspan="2">Billing Period</td>';
									html+='<td>Active</td><td>'+val.active+'</td>';
									html+='</tr><tr><td>Day Of Month</td><td>'+val.date.dayOfMonth+'</td></tr>';
                          var pt=val.profileType;
						} else if (val.type == "DEMAND_INTEGRATION_PERIOD") {
							html+='<tr><td rowspan="2">'+mi+'</td><td rowspan="2">'+su+'</td>';
							html+='	<td rowspan="2">'+fs+'</td><td rowspan="2">Demand Integration Period</td>';
								html+='<td>Active</td><td>'+val.active+'</td>';
								html+='</tr><tr><td>Demand Period</td><td>'+val.demandPeriod+'</td></tr>';

						} else if (val.type == "PROFILE_CAPTURE_PERIOD") {
							html+='<tr><td rowspan="3">'+mi+'</td><td rowspan="3">'+su+'</td>';
							html+='	<td rowspan="3">'+fs+'</td><td rowspan="3">Profile Capture Period</td>';
								html+='<td>Profile Type</td><td>'+val.profileType+'</td></tr>';
								html+='<tr><td>Capture Period</td><td>'+val.capturePeriod+'</td></tr>';
								html+='<tr><td>Active</td><td>'+val.active+'</td></tr>';
						}

					});
				});
				
$("#msfjbid").html(html);
$('#msfjid').show();
			}
		});

	}
	function ConvertToTable(jData) {
		var arrJSON = typeof jData != 'object' ? JSON.parse(jData) : jData;
		var $table = $('');
		var $headerTr = $('');

		for ( var index in arrJSON[0]) {
			$headerTr.append($('Â ').html(index));
		}
		$table.append($headerTr);
		for (var i = 0; i < arrJSON.length; i++) {
			var $tableTr = $('');
			for ( var index in arrJSON[i]) {
				$tableTr.append($('Â ').html(arrJSON[i][index]));
			}
			$table.append($tableTr);
		}
		$('body').append($table);
	}

	function getLoadCurtlmntParams(val) {
		//alert(val);
		if(val=='LOAD_CURTAILMENT'){
			//$('#loadDetails').toggle();
			$('#loadDetails').modal('show');
		}
		
		
		
	}
	
	function createmeterjob() {
		var type = $("#type").val();

		//alert(type.length);

		var active = $("#active").val();

		//alert(active);

		var jobType = $("#jobtype").val();
		var jobName = $("#jobname").val();
		var meterGroup = $("#mtrgrpname").val();
		/* commandlist=[];
		
		for(var i=0;i<type.length;i++)
		{
			obj=new Object();
			obj.type=type[i];
			obj.active=active[i];
			commandlist[i]=obj;
		}
		

		   obj2=new Object();
		   obj2.commands=commandlist;
		var jsons={"jobConfiguration":obj2,"jobType":jobType,"jobName":jobName,"meterGroup":meterGroup}; */

		/*var commands2 = new Object();
		commands2.jobType=jobType1;
		commands2.jobName=jobName1;
		commands2.meterGroup=meterGroup1;
		alert(JSON.stringify(commands2));
		 var commands={"type":type,"active":active}; */
		var commands = {};
		var types = []
		for (var i = 0; i < type.length; i++) {
			if(type[i]=='LOAD_CURTAILMENT'){
				types.push({
					"type" 		:	type[i],
					"active"	:	active,
					"loadCurtailmentState" 		:	lc_state,
					"powerLimitNormal"	:	lc_p_limit,
					"powerLimitMinOverThresholdDuration" 		:	lc_limit_od,
					"powerLimitMinUnderThresholdDuration"	:	lc_limit_ud,
					"alertPeriod" 		:	alert_period,
					"lockoutPeriod"	:	lockout_prd,
					"lockoutMaxCounter"	:	lockout_m_counter
					
				});
				
			} else{
				types.push({
					"type" 		:	type[i],
					"active"	:	active
					
				});
			}
		}
		commands = {
			"commands" : types
		};
		var jsons = {
			"jobConfiguration" : commands,
			"jobType" : jobType,
			"jobName" : jobName,
			"meterGroup" : meterGroup
		};

		var metersf = JSON.stringify(jsons);
		//alert(metersf);
		$('html, body').css("cursor", "wait");
		$
				.ajax({
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
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = "Successfully Created Job";
							meterjoblist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = "Current Job Exist or Server Down";

					}

				});
		$('html, body').css("cursor", "auto");
		resetLoadCurtParams();
	}

	function ondemand() {
		var grpname = $("#grpname").val();
		var grptype = $("#grptype").val();
		var mtrnos = $("#metrno").val();
		var jsons = {
			"meterGroupName" : grpname,
			"meterGroupType" : grptype,
			"meters" : mtrnos
		};
		var metersf = JSON.stringify(jsons);
		// alert(mtrnos);
		$
				.ajax({
					url : "./createmetergroup",
					type : 'GET',
					dataType : 'json',
					data : {
						"mtrnos" : metersf
					},
					cache : false,
					success : function(response) {

						if (response == "201") {
							$("#alertId").show();
							document.getElementById("alertId").style.display = 'block';
							document.getElementById("spanId").innerHTML = "Successfully Created Group";
							metergrouplist();
							return true;
						}

						$("#alertId").show();
						document.getElementById("alertId").style.display = 'block';
						document.getElementById("spanId").innerHTML = "Current Group Exist or Server Down";

					}

				});
	}
</script>
<div class="page-content">

	<div id="alertId" class="alert alert-danger display-show"
		style="display: none">
		<button class="close" data-close="alert"></button>
		<span style="color: red" id="spanId"></span>
	</div>
	<div class="row">
		<div class="col-md-12">

			<div class="portlet-body">

				<table>
					<tr>
						<td>SELECT :</td>
						<td><select id="api" class="form-control placeholder-no-fix"
							autocomplete="off" placeholder="" onchange="selectingoptions()">
								<option value="0"></option>
								<option value="metergroup">Create Meter Group</option>
								<option value="meterjob">Create Meter Job</option>
								<option value="managejob">Manage Meter Job</option>
								<option value="Queryjob">Query Meter Job</option>
								<option value="MeterStatusForJob">Meter Status for Job</option>
						</select></td>
					</tr>
					<tr id="tf">
						<td>SELECT :</td>
						<td><select id="boolean"
							class="form-control placeholder-no-fix" autocomplete="off"
							placeholder="">
								<option value="0"></option>
								<option value="true">True</option>
								<option value="False">False</option>
						</select></td>
					</tr>
				</table>
				<div class="modal-footer">
					<button class="btn blue pull-left" onclick="allapis()">Submit</button>

				</div>

			</div>



			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue" hidden="true" id="createMtrgrp">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Create Meter Group
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>SELECT METER GROUP NAME :</td>
							<td><input type="text" name="grpname" id="grpname"
								class="form-control"></td>
						</tr>
						<tr>
							<td>SELECT METER GROUP TYPE :</td>
							<td><select id="grptype"
								class="form-control placeholder-no-fix" type="text"
								autocomplete="off" placeholder="" name="event">
									<option value="0"></option>
									<option value="STATIC">STATIC</option>
							</select></td>
						<tr>
							<td>Select METER NUMBERS :</td>
							<td>
								<!-- <input type="text" name="metrno" id="metrno"  class="form-control"> -->
								<select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="metrno" multiple="multiple" autofocus="autofocus">
									<!-- <option value="8000003">8000003</option>
												<option value="8000004">8000004</option> -->
									<c:forEach items="${meters}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>

							</select>
							</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="ondemand()">Submit</button>

					</div>
				</div>
			</div>



			<div class="portlet box blue" hidden="true" id="createMtrjob">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Create Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form> --%>
					<table>
						<tr>
							<td>SELECT TYPE:</td>
							<td><select id="type"
								class="js-example-basic-multiple select2me placeholder-no-fix input-large" onchange="getLoadCurtlmntParams(this.value)"
								multiple="multiple" type="text" autocomplete="off"
								placeholder="">
									<option value="0"></option>
									<option value="BILLING_PERIOD">BILLING PERIOD</option>
									<option value="DEMAND_INTEGRATION_PERIOD">DEMAND
										INTEGRATION PERIOD</option>
									<option value="PROFILE_CAPTURE_PERIOD">PROFILE CAPTURE
										PERIOD</option>
									<option value="TARIFF_CALENDAR">TARIFF CALENDAR</option>
									<option value="LOAD_CURTAILMENT">LOAD CURTAILMENT</option>
									<option value="EVENT_THRESHOLD">EVENT THRESHOLD</option>
							</select></td>
						</tr>
						<tr>
							<td>ACTIVE :</td>
							<td><select id="active"
								class="js-example-basic-multiple placeholder-no-fix input-large select2me"
								type="text" autocomplete="off" placeholder="" name="event">
									<option value="0"></option>
									<option value="True">TRUE</option>
									<option value="false">FALSE</option>
							</select></td>
						<tr>
							<td>Select Job Type :</td>
							<td><select
								class="form-control placeholder-no-fix select2me" id="jobtype"
								autofocus="autofocus">
									<option value="0">Select job</option>
									<option value="METER_COMMAND_GET">METER_COMMAND_GET</option>
									<option value="METER_COMMAND_SET">METER_COMMAND_SET</option>
							</select></td>
						</tr>
						<tr>
							<td>Enter Job Name :</td>
							<td><input type="text" name="jobname" id="jobname"
								class="form-control"> <!-- <select class="form-control placeholder-no-fix select2me" id="jobname"  autofocus="autofocus" >
									<option value="0">Select job</option>
									<option value="UGVCLGET1211">UGVCLGET1211</option> --> </select></td>
						</tr>
						<tr>
							<td>SELECT METER GROUP NAME :</td>
							<td>
								<!-- <input type="text" name="mtrgrpname" id="mtrgrpname"  class="form-control"> -->
								<select id="mtrgrpname"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="">
									<option value="" disabled="disabled" selected="selected">Select</option>
									<c:forEach var="elements" items="${groupList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
							</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="createmeterjob()">Submit</button>

					</div>
				</div>
			</div>





			<div class="portlet box blue" hidden="true" id="managejob">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Manage Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td><!-- <input type="text" name="managejobname"
								id="managejobname" class="form-control"> -->
								<select
									id="managemeterjob" name="jobname"
									class="form-control placeholder-no-fix select2me" type="text"
									autocomplete="off" placeholder="" style="width: 300px;">


								</select></td>
						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="managemeterjob()">Submit</button>

					</div>
				</div>
			</div>


			<div class="portlet box blue" hidden="true" id="queryjob">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>To Query Meter Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td><select id="jobnameQ" name="jobname"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" style="width: 300px;">
									
									
							</select>
							
							<!-- <input type="text" name="jobname" id="jobnameQ"
								class="form-control"> --></td>
						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="querymeterjob()">Submit</button>

					</div>
				</div>
			</div>


			<div class="portlet box blue" hidden="true" id="meterstatus">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter Status For Job
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">

					<%-- <form > --%>
					<table>
						<tr>
							<td>JOB NAME:</td>
							<td>
							<select id="jobnamestatus" name="jobnamestatus"
								class="form-control placeholder-no-fix select2me" type="text"
								autocomplete="off" placeholder="" style="width: 300px;">
									<option value="" disabled="disabled" selected="selected">Select</option>
									
							</select>
							<!-- <select  name="jobnamestatus" id="jobnamestatus" class="form-control" onclick="joblist()">
								
								</select> --></td>


							<td>COUNT:</td>
							<td><input type="text" name="count" id="count"
								class="form-control" placeholder="111"></td>

							<td>DEVICE STATUS:</td>
							<td><input type="text" name="device" id="device"
								class="form-control" placeholder="SUCCESS"></td>


							<td>START ID:</td>
							<td><input type="text" name="startid" id="startid"
								class="form-control" placeholder="1"></td>

						</tr>

					</table>
					<div class="modal-footer">
						<button class="btn blue pull-left" onclick="meterstatus()">Submit</button>

					</div>
				</div>
			</div>


















		</div>
	</div>
	<div class="row" style="display: none" id="mgdid">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter Groups
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">


					<!-- <div class="table-toolbar">
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
 -->

					<BR>
					<BR>
					<!-- <div class="table-scrollable">	 -->
					<table class="table table-striped table-bordered table-hover"
						id="sample_3">
						<thead>
							<tr>
								<th>S.no</th>
								<th>Meter Group Name</th>
								<th>Meter Group Type</th>
								<th>Created Time</th>

							</tr>
						</thead>
						<tbody id="mgid">


						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" style="display: none" id="mjdid">
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


					<BR>
					<BR>
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

							</tr>
						</thead>
						<tbody id="mjid">


						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" id="mqjid" style="display: none">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Query Data
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">


					<div class="table-toolbar"></div>


					<BR>
					<BR>
					<!-- <div class="table-scrollable">	 -->
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

							<table class="table table-striped table-bordered table-hover "
								id="sample_6">
								<thead>
									<tr>
										<th colspan="2" style="text-align: center;">Config
											Commands</th>
									</tr>
									<tr>
										<th>Type</th>
										<th>Active</th>
									</tr>
								</thead>
								<tbody id="mqjbbid">




								</tbody>
							</table>
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" id="msfjid" style="display: none">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter Status Response Data
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">


					<div class="table-toolbar"></div>


					<BR>
					<BR>
					<!-- <div class="table-scrollable">	 -->
					<table class="table table-striped table-bordered table-hover" style="overflow-y: auto;">
						<thead>
							<tr>
								<th>Meter Number</th>
								<th>Status</th>
								<th>Failure Step</th>
								<th>Type</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="msfjbid">
							
							
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="loadDetails" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;">LOAD CURTAILMENT PARAMETERS</font></a>
					</h4>
				</div>

				<div class="modal-body">
				<div class="row">
					<div class="form-group col-md-6">
						<label>Load Curtailment State</label><font color="red">*</font>
						<div class="input-group" id="circleTd">
							<select class="form-control select2me input-medium" id="lc_state" name="lc_state">
								<option value="ENABLE">ENABLE</option>
								<option value="DISABLE">DISABLE</option>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-6">
						<label>Power Limit Normal</label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="lc_p_limit" name="lc_p_limit"  placeholder="Power Limit Normal"></input>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label>Limit Over Duration </label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="lc_limit_od" name="lc_limit_od"  placeholder="Limit Over Duration"></input>
						</div>
					</div>
					
					<div class="form-group col-md-6">
						<label>Limit Under Duration</label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="lc_limit_ud" name="lc_limit_ud"  placeholder="Limit Under Duration"></input>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-4">
						<label>Alert Period </label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="alert_period" name="alert_period"  placeholder="Alert Period"></input>
						</div>
					</div>
					
					<div class="form-group col-md-4">
						<label>Lockout Period</label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="lockout_prd" name="lockout_prd"  placeholder="Lockout Period" ></input>
						</div>
					</div>
					<div class="form-group col-md-4">
						<label>Lockout Max Counter </label><font color="red">*</font>
						<div>
							<input type="text" class="form-control " id="lockout_m_counter" name="lockout_m_counter"  placeholder="Lockout Max Counter" ></input>
						</div>
					</div>
				</div>
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn blue btn-outline" onclick="setLoadCurtlParameters()">Submit</button>
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	

</div>


<style>
.hid {
	color: #333;
	font-size: medium;
	font-weight: bold;
}

.tdcls1 {
	padding-bottom: 15px;
	width: 10%;
	text-align: right;
	font-weight: bold;
}

.tdcls2 {
	padding-bottom: 15px;
	width: 40%;
	/* text-align: left; */
	text-align: justify;
}

.tdcls3 {
	padding-bottom: 15px;
	width: 10%;
	text-align: right;
	font-weight: bold;
}

.tdcls4 {
	padding-bottom: 15px;
	width: 40%;
	/* text-align: left; */
	text-align: justify;
}

.col-container {
	display: flex;
	width: 100%;
}

.col {
	flex: 1;
	padding: 16px;
}
</style>

<script>
var lc_state,lc_p_limit,lc_limit_od,lc_limit_ud;
var alert_period,lockout_prd,lockout_m_counter;
function setLoadCurtlParameters() {
	lc_state=$("#lc_state").val();
	lc_p_limit=$("#lc_p_limit").val();
	lc_limit_od=$("#lc_limit_od").val();
	lc_limit_ud=$("#lc_limit_ud").val();
	alert_period=$("#alert_period").val();
	lockout_prd=$("#lockout_prd").val();
	lockout_m_counter=$("#lockout_m_counter").val();
	
	if(lc_p_limit==null || lc_p_limit==""){
		bootbox.alert("Please Enter Power Limit.");
		return false;
	} 
	if(lc_limit_od==null || lc_limit_od==""){
		bootbox.alert("Please Enter Limit Over Duration.");
		return false;
	} 
	if(lc_limit_ud==null || lc_limit_ud==""){
		bootbox.alert("Please Enter Limit Under Duration.");
		return false;
	} 
	if(alert_period==null || alert_period==""){
		bootbox.alert("Please Enter Alert Period.");
		return false;
	} 
	if(lockout_prd==null || lockout_prd==""){
		bootbox.alert("Please Enter Lockout Period.");
		return false;
	}
	if(lockout_m_counter==null || lockout_m_counter==""){
		bootbox.alert("Please Enter Loackout Max Counter .");
		return false;
	}
	$('#loadDetails').modal('hide');
	
	lc_p_limit=parseInt(lc_p_limit);
	lc_limit_od=parseInt(lc_limit_od);
	lc_limit_ud=parseInt(lc_limit_ud);
	alert_period=parseInt(alert_period);
	lockout_prd=parseInt(lockout_prd);
	lockout_m_counter=parseInt(lockout_m_counter);

	bootbox.alert(" LOAD CURTAILMENT PARAMETERS saved Successfully");
	return true;
	
}

function resetLoadCurtParams() {
	
	$("#lc_state").val("ENABLE").trigger("change");
	$("#lc_p_limit").val("");
	$("#lc_limit_od").val("");
	$("#lc_limit_ud").val("");
	$("#alert_period").val("");
	$("#lockout_prd").val("");
	$("#lockout_m_counter").val("");
	
}


</script>
