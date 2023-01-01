<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	$(".page-content")
			.ready(
					function() {
						
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						$('#MDASSideBarContents').addClass(
								'start active ,selected');
						$(
								'#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

						App.init();

					});
</script>
<script type="text/javascript">
	var mtrNum;
	function mtrDetails(mtrNo) {
		mtrNum = mtrNo;
		window.location.href = "./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
		/* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
	}

	function eventDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getEventData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" + " <td>" + resp[0] + "</td>" + " <td>"
							+ resp[1] + "</td>" + " <td>" + resp[2] + "</td>"
							+ " <td>" + resp[3] + "</td>" + " <td>" + resp[4]
							+ "</td>" + " <td>" + resp[5] + "</td>" + " <td>"
							+ resp[6] + "</td>" + " <td>" + resp[7] + "</td>"
							+ " <td>" + resp[8] + "</td>" + " <td>" + resp[9]
							+ "</td>" + " <td>" + resp[10] + "</td>" + " <td>"
							+ resp[11] + "</td>" + " <td>" + resp[12] + "</td>"
							+ " </tr>";
				}
				$('#sample_2').dataTable().fnClearTable();
				$('#eventTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_2');
			}
		});
	}

	function instansDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getInstansData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" + " <td>" + resp[0] + "</td>" + " <td>"
							+ resp[1] + "</td>" + " <td>" + resp[2] + "</td>"
							+ " <td>" + resp[3] + "</td>" + " </tr>";
				}
				$('#sample_3').dataTable().fnClearTable();
				$('#instantsTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_3');
			}
		});
	}

	function loadSurveyDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getLoadSurveyData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" + 
					" <td>" + resp[0] + "</td>" + 
					" <td>"	+ resp[1] + "</td>" + 
					" <td>" + resp[2] + "</td>" + 
					" <td>" + resp[3] + "</td>" + 
					" <td>" + resp[4] + "</td>" + 
					" <td>" + resp[5] + "</td>" + 
					" <td>" + resp[6] + "</td>" + 
					" <td>" + resp[7] + "</td>" + 
					" <td>" + resp[8] + "</td>" + 
					" <td>" + resp[9] + "</td>" + 
					" <td>" + resp[10] + "</td>" + 
					" <td>" + resp[11] + "</td>" + 
					" <td>" + resp[12] + "</td>" + 
					" </tr>";
				}
				$('#sample_4').dataTable().fnClearTable();
				$('#loadSurveyTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_4');
			}
		});
	}
</script>



<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">

				<c:if test="${type eq 'notcom'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Not Communicating Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>

				<c:if test="${type eq 'active'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Communicating Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>
				
				<c:if test="${type eq 'dtdetails'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total DTs
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>
				
				<c:if test="${type eq 'feeder'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total Feeder Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>
				
				<c:if test="${type eq 'boundary'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total Boundary Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>
				
				<c:if test="${type eq 'inactive'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total Inactive Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>

				<%-- <c:if test="${type ne 'notcom' && type ne 'active' }">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if> --%>
				
				<c:if test="${type eq 'totalmtr'}">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Total Meters
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
				</c:if>

				<div class="portlet-body">
					<c:if test="${hideFlag ne 'true'}">
						<div class="row" style="margin-left: -1px;">

							<table style="width: 38%">
								<tbody>
									<tr>
										<td><select class="form-control select2me input-medium"
											name="zone" id="zone" onchange="showCircle(this.value);">
												<option value="">Zone</option>
												<option value='All'>ALL</option>
												<c:forEach var="elements" items="${zoneList}">
													<option value="${elements}">${elements}</option>
												</c:forEach>
										</select></td>

										<td id="circleTd"><select
											class="form-control select2me input-medium" id="circle"
											name="circle">
												<option value="">Circle</option>
												<option value='All'>ALL</option>
												<c:forEach items="${circleList}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
										</select></td>

										<td id="divisionTd"><select
											class="form-control select2me input-medium" id="division"
											name="division">
												<option value="">Division</option>
												<option value='All'>ALL</option>
												<c:forEach items="${divisionList}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
										</select></td>

										<td id="subdivTd"><select
											class="form-control select2me input-medium" id="sdoCode"
											name="sdoCode">
												<option value="">Sub-Division</option>
												<option value='All'>ALL</option>
												<c:forEach items="${subdivList}" var="sdoVal">
													<option value="${sdoVal}">${sdoVal}</option>
												</c:forEach>
										</select></td>

										<td>
											<button type="button" id="viewFeeder"
												style="margin-left: 20px;" onclick="return getFeeder()"
												name="viewFeeder" class="btn yellow">
												<b>View</b>
											</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
										</td>
									</tr>
								</tbody>
							</table>
							<p>&nbsp;</p>



							<div class="row" align="left">
								<label class="mt-radio" style="margin-left: 14px;"> <input
									type="radio" name="optionsRadios" id="meterno_radio"
									value="meterno" checked onchange="setRadioVal(this.value)">
									Meter No <span></span>
								</label> <label class="mt-radio"> <input type="radio"
									name="optionsRadios" id="accno_radio" value="accno"
									onchange="setRadioVal(this.value)"> Account No <span></span>
								</label>
							</div>


							<div class="row">
								<div hidden="true">
									<input class="form-control input-medium" name="radioVal"
										id="radioVal" value="meterno" />
								</div>
								<input class="form-control input-medium" autocomplete="off"
									placeholder="Enter Meter No." name="meterNum" id="meterNum"
									style="padding-left: 14px; margin-left: 14px;" />
								<button type="button"
									style="margin-top: 3px; margin-left: 20px;"
									data-dismiss="modal" class="btn blue"
									onclick="updateFdrTable()">SUBMIT</button>
							</div>




							<p>&nbsp;</p>
						</div>
					</c:if>
				    <c:if test="${type eq 'notcom'}">
						<div class="col-md-6" id="link">
						<a href="#" id="openLink" onclick="viewPage()" style="margin-bottom: -10px;margin-left: -14px;"><b>Detailed Report-Non Communication</b></a>
								<!-- <button type="button" id="openLink" onclick="viewPage()" style="margin-bottom: -10px;margin-left: -14px;" name="openLink" class="btn green">
									<b>Detailed Report-Non Communication</b>	
								</button> -->
					    </div>
				    </c:if>
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Meter No.</th>
								<th>Location Name</th>
								<th>Location Id</th>

								<th>SubStation</th>
								<th>SubDivision</th>
								<th>Division</th>
								<th>Circle</th>
								<th>Last Sync Time</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
							<c:forEach var="element" items="${mDetailList}">
								<tr>

									<td><a
										href='./viewFeederMeterInfoMDAS?mtrno=${element[1]}'
										style='color: blue;'
										onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td>
									<td>${element[9]}</td>
									<td>${element[20]}</td>

									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[7]}</td>
									<td><fmt:formatDate value="${element[8]}"
											pattern="dd-MM-yyyy HH:mm:ss" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form id="DeleteForm" method="POST">
						<input type="hidden" name="deviceid" value="" id="deleteID" /> <input
							type="hidden" name="editVal" value="" id="editVal" />
					</form>

				</div>
			</div>
		</div>
	</div>

</div>



<script>
	function setRadioVal(val) {
		//radioVal=val;
		$("#radioVal").val(val);

		var radioValue = $("input[name='optionsRadios']:checked").val();
		//console.log(radioValue);
		//alert(radioVal+" "+radioValue);
		if (val == 'meterno') {
			$("#meterNum").attr("placeholder", "Enter Meter No");
		} else {
			$("#meterNum").attr("placeholder", "Enter Account No");
		}
	}

	function showCircle(zone) {
		$
				.ajax({
					url : './showCircleMDAS' + '/' + zone,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#circleTd").html(html);
						$('#circle').select2();
					}
				});
	}

	function showDivision(circle) {
		var zone = $('#zone').val();
		$
				.ajax({
					url : './showDivisionMDAS' + '/' + zone + '/' + circle,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
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
		$('#sdoCode').find('option').remove();
		$.ajax({
			url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
					+ division,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				$('#sdoCode').append($('<option>', {
					value : "",
					text : "Sub-Division"
				}));
				$('#sdoCode').append($('<option>', {
					value : "All",
					text : "All"
				}));

				/* var html='';
				html+="<select id='sdoCode' name='sdoCode' class='form-control select2me input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>"; */
				for (var i = 0; i < response1.length; i++) {
					//var response=response1[i];
					/* html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>"; */

					$('#sdoCode').append($('<option>', {
						value : response1[i],
						text : response1[i]
					}));
				}
				/* html+="</select><span></span>";
				$("#subdivTd").html(html); */
				//$('#subdiv').select2();
			}
		});
	}

	function getFeeder() {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = $('#division').val();
		var subdiv = $('#sdoCode').val();

		if (zone == '' || zone == null) {
			bootbox.alert("Please select zone!");
			return false;
		}
		if (circle == '' || circle == null) {
			bootbox.alert("Please select circle!");
			return false;
		}
		if (division == '' || division == null) {
			bootbox.alert("Please select division!");
			return false;
		}
		if (subdiv == '' || subdiv == null) {
			bootbox.alert("Please select subdivision!");
			return false;
		}

		$('#updateMaster').empty();
		$
				.ajax({
					url : './getAllMetersBasedOnMDAS' + '/' + zone + '/'
							+ circle + '/' + division + '/' + subdiv,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						if (response.length != 0) {
							var html = "";

							for (var i = 0; i < response.length; i++) {
								var element = response[i];

								html += "<tr>"
										+ "<td>"
										+ "<a style='color:blue;' onclick='return mtrDetails(\""
										+ element[1]
										+ "\");'>"
										+ element[1]
										+ "</a>"
										/*  href='./mtrNoDetails?mtrno="+element[1]+"' */
										+ "</td>"
										+ "<td>"
										+ (element[9] == null ? "" : element[9])
										+ "</td>"
										+ "<td>"
										+ (element[1] == null ? "" : element[1])
										+ "</td>"
										+ "<td>"
										+ (element[19] == null ? ""
												: element[19])
										+ "</td>"
										+ "<td>"
										+ (element[3] == null ? "" : element[3])
										+ "</td>"
										+ "<td>"
										+ (element[4] == null ? "" : element[4])
										+ "</td>"
										+ "<td>"
										+ (element[5] == null ? "" : element[5])
										+ "</td>"
										+ "<td>"
										+ (element[7] == null ? "" : element[7])
										+ "</td>";

								if (element[8] != null) {
									html += "<td>"
											+ moment(element[8]).format(
													"YYYY-MM-DD HH:mm:ss")
											+ "</td>";
								} else {
									html += "<td>" + "</td>";
								}

								+" </tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_1');

						} else {
							bootbox
									.alert("Meter not exist in the master main table ");
						}

					}
				});

	}

	function updateFdrTable() {

		var imeiVal = $('#meterNum').val();
		var radioValue = $("input[name='optionsRadios']:checked").val();

		if (imeiVal == '' || imeiVal == null) {
			if (radioValue == 'meterno') {
				bootbox.alert("Please Enter Meter No.");
			} else {
				bootbox.alert("Please Enter Account No.");
			}
			return false;
		}

		var aurl = "";

		//alert(imeiVal.length);
		if (radioValue == 'meterno') {
			aurl = "./getFeedersBasedOnMeterNoMDAS/" + imeiVal;
		} else {
			aurl = "./getFeedersBasedOnaccno/" + imeiVal;
		}

		/* alert('IMEI====='+imeiVal); */
		$
				.ajax({
					url : aurl,
					type : "GET",
					dataType : "json",
					async : false,
					success : function(response) {
						var x = JSON.stringify(response);
						//alert('result====='+x); 

						if (response.length == 0 || response.length == null) {

							var _alert_text = "Data Not Available for this ";
							if (radioValue == 'meterno') {
								_alert_text += "Meter No : ";
							} else {
								_alert_text += "Account No : ";
							}
							_alert_text += imeiVal;

							bootbox.alert(_alert_text);
							$('#updateMaster').empty();
						} else {
							var html = "";
							var select = new Array();
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								console.log(resp);

								html += "<tr>"
										+ "<td>"
										+ "<a style='color:blue;' onclick='return mtrDetails(\""
										+ resp.mtrno
										+ "\");'>"
										+ resp.mtrno
										+ "</a>"
										+ "</td>"
										+ "<td>"
										+ (resp.customer_name == null ? ""
												: resp.customer_name)
										+ "</td>"
										+ "<td>"
										+ (resp.accno == null ? "" : resp.accno)
										+ "</td>"
										+ "<td>"
										+ (resp.kno == null ? "" : resp.kno)
										+ "</td>"
										+ "<td>"
										+ (resp.substation == null ? ""
												: resp.substation)
										+ "</td>"
										+ "<td>"
										+ resp.subdivision
										+ "</td>"
										+ "<td>"
										+ resp.division
										+ "</td>"
										+ "<td>" + resp.circle + "</td>";

								if (resp.last_communicated_date != null) {
									html += "<td>"
											+ moment(
													resp.last_communicated_date)
													.format(
															"DD-MM-YYYY HH:mm:ss")
											+ "</td>";
								} else {
									html += "<td></td>";
								}

								html += " </tr>";

							}

							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_1');

						}

					}

				});

	}

	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable({
			"aLengthMenu" : [ [ 20, 50, 100, -1 ], [ 20, 50, 100, "All" ] // change per page values here
			],
			"iDisplayLength" : 100
		});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}

	function viewPage(){
		window.location.href=("./consisCommFailRep");
		}
</script>