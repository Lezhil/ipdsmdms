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
						/* loadSearchAndFilter('sample_1'); */
						$("#meterType").val("").trigger("change");
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
						$('#MDASSideBarContents,#meterOper,#ChangeAndInstallation,#meterChangeRpt').addClass('start active ,selected');
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
	function viewChangeMeterData() {
		
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		//var division = $('#division').val();
		//var subdivision = $('#sdoCode').val();
		var town = $('#LFtown').val();
		var meterType = $('#meterType').val();
		//alert(zone);
		
		if (zone == "") {
			bootbox.alert("Please Select zone");
			return false;
		}

		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		/* if (division == "") {
			bootbox.alert("Please Select Division");
			return false;
		}

		if (subdivision == "") {
			bootbox.alert("Please Select Sub-Division");
			return false;
		} */
		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}
		if (meterType == "") {
			bootbox.alert("Please Select MeterType");
			return false;
		}

		var townNameCodeArr =[];
			townNameCodeArr = town.split('-');
			 
		
	//	alert("zone= "+zone+" circle= "+circle+" Division= "+division+" Subdivision= "+sdoCode+" Town= "+town+" Type= "+meterType);
       // $('#imageee').show();
		$
				.ajax({
					url : './viewChangeMeterData',
					type : 'POST',
					data : {
						zone : zone,
						circle : circle,
						division : '%',
						subdivision : '%',
						town : town,
						meterType : meterType
					},
					dataType : 'json',
					success : function(response) {
					//	alert(response);
					showTable(meterType);
						$('#imageee').hide();
						if (meterType == "FEEDER METER") {
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster1").html('');
							/* loadSearchAndFilter('sample_1'); */
	
							if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>" 
										+ "<td>"+ j + "</td>"
										+ "<td>"+ townNameCodeArr[1]+ " </td>"
										+ "<td>"+ resp[0] + " </td>"
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
										+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
										+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
										+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
										+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
										+ "<td>"+ ((resp[12] == null) ? "": moment(resp[12]).format('DD-MM-YYYY')) + " </td>"
										+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
										"</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster1").html(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("No Relative Data Found.");
						}
							
						}
						if (meterType == "DT") {
							$('#sample_2').dataTable().fnClearTable();
							$("#updateMaster2").html('');
							/* loadSearchAndFilter('sample_2'); */

							if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>" 
										+ "<td>"+ j + "</td>"
										+ "<td>"+ townNameCodeArr[1]+ " </td>"
										+ "<td>"+ resp[0] + " </td>"
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
										+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
										+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
										+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
										+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
										+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
										+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
										+ "<td>"+ ((resp[14] == null) ? "": moment(resp[14]).format('DD-MM-YYYY')) + " </td>"
										+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
										"</tr>";
							}
							$('#sample_2').dataTable().fnClearTable();
							$("#updateMaster2").html(html);
							loadSearchAndFilter('sample_2');
						} else {
							bootbox.alert("No Relative Data Found.");
						}

							}
						if (meterType == "BOUNDARY METER") {
							$('#sample_3').dataTable().fnClearTable();
							$("#updateMaster3").html('');
							/* loadSearchAndFilter('sample_3'); */


							if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>" 
										+ "<td>"+ j + "</td>"
										+ "<td>"+ townNameCodeArr[1] + " </td>"
										+ "<td>"+ resp[0] + " </td>"
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
										+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
										+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
										+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
										+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
										+ "<td>"+ ((resp[14] == null) ? "": moment(resp[14]).format('DD-MM-YYYY')) + " </td>"
										+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
										"</tr>";
							}
							$('#sample_3').dataTable().fnClearTable();
							$("#updateMaster3").html(html);
							loadSearchAndFilter('sample_3');
						} else {
							bootbox.alert("No Relative Data Found.");
						}

						}
				
					},
					complete : function() {
						if (meterType == "FEEDER METER") {
							loadSearchAndFilter('sample_1');
						}
						if (meterType == "DT") {
							loadSearchAndFilter('sample_2');
							}
						if (meterType == "BOUNDARY METER") {
							loadSearchAndFilter('sample_3');
						}
						
						//loadSearchAndFilter('sample_1');
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
						<i class="fa fa-edit"></i>Meter Change Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">	
				<jsp:include page="locationFilter.jsp"/> 		

					<%-- <div class="row" style="margin-left: -1px;">
						<div class="col-md-4">
							<div class="form-group">
						   	<label class="control-label">Zone:</label>
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
						<label class="control-label">Circle:</label>
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
							<div id="townId" class="form-group">
							<label class="control-label">Town:</label>
								<select class="form-control select2me" id="town" name="town">
									<option value="">Select Town</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

						</div>
						<div class="col-md-4">
							<div id="divisionTd" class="form-group">
							<label class="control-label">Division:</label>
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
					</div> --%>
					<div class="row" style="margin-left: -1px;">

						<%-- <div class="col-md-4">
							<div id="subdivTd" class="form-group">
							<label class="control-label">Sub-Division:</label>
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
							<div id="townId" class="form-group">
							<label class="control-label">Town:</label>
								<select class="form-control select2me" id="town" name="town">
									<option value="">Select Town</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div> --%>

				
						<div class="col-md-3">
							<div class="form-group">
							<label class="control-label">Meter Type:</label>
								<select class="form-control select2me"   id="meterType" name="meterType">
									<option value="">Select Meter Type</option>
									<%-- <option value="ALL">ALL</option>  --%>
									<option value="FEEDER METER">FEEDER METER</option>
									<option value="DT">DT METER</option>
									<option value="BOUNDARY METER">BOUNDARY METER</option>
								</select>
							</div>

						</div>
						
						
						<div id="show">
							<button type="button" id="viewChangeMeterData" onclick="viewChangeMeterData()" style="margin-top:25px;" name="viewChangeMeterData" class="btn yellow">
								<b>VIEW REPORT</b>
							</button>
						</div>
						</div>
					
						
					<!--BEGIN TABS-->
					
						<!-- <div id="show">
							<button type="button" id="viewChangeMeterData" onclick="viewChangeMeterData()" style="margin-top:25px;" name="viewChangeMeterData" class="btn yellow">
								<b>VIEW REPORT</b>
							</button>
						</div>
						<br /> -->
						
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
						<div class="tabbable tabbable-custom" id="showFeederTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
										    <li><a href="#" id=""
								                onclick="exportPDF1('sample_1','FeederMeterChangeReport')">Export to PDF</a></li>
											<li><a href="#" id="excelExport"
												onclick="tableToExcel('sample_1', 'FeederMeterChangeReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>SL NO</th>
										<th>TOWN NAME</th>
										<th>TOWN CODE</th>
										<th>FEEDER NAME</th>
										<th>FEEDER CODE</th>
										<th>OLD METER NO</th>
										<th>OLD METER MAKE</th>
										<th>OLD MF</th>
										<th>OLD METER LAST READING</th>
										<th>NEW METER NO</th>
										<th>NEW METER MAKE</th>
										<th>NEW MF</th>
										<th>NEW METER FIRST READING</th>
										<th>CHANGED BY</th>
										<th>CHANGED DATE</th>
										<th>REASON</th> 
									</tr>
								</thead>
								<tbody id="updateMaster1">

								</tbody>
							</table>
						</div>
						
						
						<div class="tabbable tabbable-custom" id="showDTTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
										    <li><a href="#" id=""
								                onclick="exportPDF2('sample_2','DTMeterChangeReport')">Export to PDF</a></li>
											<li><a href="#" id="excelExport"
												onclick="tableToXlxs('sample_2', 'DTMeterChangeReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_2">
								<thead>
									<tr>
										<th>SL NO</th>
										<th>TOWN NAME</th>
										<th>TOWN CODE</th>
										<th>FEEDER NAME</th>
										<th>FEEDER CODE</th>
										<th>DT CODE</th>
										<th>DT NAME</th>
										<th>OLD METER NO</th>
										<th>OLD METER MAKE</th>
										<th>OLD MF</th>
										<th>OLD METER LAST READING</th>
										<th>NEW METER NO</th>
										<th>NEW METER MAKE</th>
										<th>NEW MF</th>
										<th>NEW METER FIRST READING</th>
										<th>CHANGED BY</th>
										<th>CHANGED DATE</th>
										<th>REASON</th>
									</tr>
								</thead>
								<tbody id="updateMaster2">

								</tbody>
							</table>
						</div>
						
						
						<div class="tabbable tabbable-custom" id="showBoundaryTableView" hidden="true">
							<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
										    <li><a href="#" id=""
								                onclick="exportPDF3('sample_3','BoundaryMeterChangeReport')">Export to PDF</a></li>
											<li><a href="#" id="excelExport"
												onclick="tableToXlxs('sample_3', 'BoundaryMeterChangeReport')">Export
													to Excel</a></li>
										</ul>
									</div>
								</div>
							<table class="table table-striped table-hover table-bordered"
								id="sample_3">
								<thead>
									<tr>
										<th>SL NO</th>
										<th>TOWN NAME</th>
										<th>TOWN CODE</th>
										<th>FEEDER NAME</th>
										<th>FEEDER CODE</th>
										<th>BOUNDARY CODE</th>
										<th>BOUNDARY NAME</th>
										<th>OLD METER NO</th>
										<th>OLD METER MAKE</th>
										<th>OLD METER LAST READING</th>
										<th>NEW METER NO</th>
										<th>NEW METER MAKE</th>
										<th>NEW METER FIRST READING</th>
										<th>CHANGED BY</th>
										<th>CHANGED DATE</th>
										<th>REASON</th>
										
									</tr>
								</thead>
								<tbody id="updateMaster3">

								</tbody>
							</table>
						</div>
							

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
		    		html+="<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#LFcircleTd").html(html);
					$('#LFcircle').select2();
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
	                html += "<select id='LFtown' name='LFtown' class='form-control  select2me'  type='text'><option value=''>Select Town</option>";
	               
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"-"  +response1[i][1]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#LFtownTd").html(html);
	                $('#LFtown').select2();
	                
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
							html += "<label class='control-label'>Division:</label><select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
							html += "<label class='control-label'>Sub-Division:</label><select id='sdoCode' name='sdoCode'  class='form-control' onchange='showTownBySubDiv(this.value)' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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

	 function showTownBySubDiv(subdivision) {
			
			var zone = $('#zone').val();
			var circle = $('#circle').val();
			var division = $('#division').val();
			$.ajax({
				url : './getTownsBaseOnSubdivision',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone,
					circle : circle,
					division : division,
					subdivision : subdivision
				},
						success : function(response1) {
							var html = '';
							html += "<label class='control-label'>Town:</label><select id='town' name='town'  class='form-control' type='text'><option value=''>Select Town</option>";
							for (var i = 0; i < response1.length; i++) {
								//var response=response1[i];
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#townId").html(html);
							$('#town').select2();
						}
					});
		}
	
</script>

<script>

function showTable(value){

	if (value == "FEEDER METER") {
		$('#sample_1').dataTable().fnClearTable();
		loadSearchAndFilter('sample_1');
		$('#showDTTableView').hide();
		$('#showBoundaryTableView').hide();
		$('#showFeederTableView').show();
	}
	if (value == "DT") {
		$('#sample_2').dataTable().fnClearTable();
		loadSearchAndFilter('sample_2');
		$('#showBoundaryTableView').hide();	
		$('#showFeederTableView').hide();
		$('#showDTTableView').show();
		}
	if (value == "BOUNDARY METER") {
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_3');
		$('#showDTTableView').hide();
		$('#showFeederTableView').hide();
		$('#showBoundaryTableView').show();
	}
	
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
	
	
	function exportPDF1()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var division = $('#LFdivision').val();
		var subdivision = $('#LFsdoCode').val();
		//var town = $('#town').val();
		var town = $('#LFtown').val();
		var meterType = $('#meterType').val();
		
		 var zne="",cir="",div="",subdiv="";
			
			if(zone=="%"){
				zne="ALL";
			}else{
				zne=zone;
			}
			if(circle=="%"){
				cir="ALL";
			}else{
			    cir=circle;
			}
			if(division=="%"){
				div="ALL";
			}else{
				div=division;
			}
			if(subdivision=="%"){
				subdiv="ALL";
			}else{
				subdiv=subdivision;
			}
		
		window.location.href=("./FeederMeterPDF?zne="+zne+"&cir="+cir+"&div="+div+"&subdiv="+subdiv+"&town="+town+"&meterType="+meterType);
	}
	
	function exportPDF2()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var division = $('#LFdivision').val();
		var subdivision = $('#sdoCode').val();
		var town = $('#LFtown').val();
		var meterType = $('#meterType').val();
		
		var zne="",cir="",div="",subdiv="";
		
		if(zone=="%"){
			zne="ALL";
		}else{
			zne=zone;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
		if(division=="%"){
			div="ALL";
		}else{
			div=division;
		}
		if(subdivision=="%"){
			subdiv="ALL";
		}else{
			subdiv=subdivision;
		}
		
		window.location.href=("./DTMeterPDF?zne="+zne+"&cir="+cir+"&div="+div+"&subdiv="+subdiv+"&town="+town+"&meterType="+meterType);
	}
	
	function exportPDF3()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var division = $('#LFdivision').val();
		var subdivision = $('#sdoCode').val();
		var town = $('#LFtown').val();
		var meterType = $('#meterType').val();
		
		var zne="",cir="",div="",subdiv="";
		
		if(zone=="%"){
			zne="ALL";
		}else{
			zne=zone;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
		if(division=="%"){
			div="ALL";
		}else{
			div=division;
		}
		if(subdivision=="%"){
			subdiv="ALL";
		}else{
			subdiv=subdivision;
		}
		
		window.location.href=("./BoundaryMeterPDF?zne="+zne+"&cir="+cir+"&div="+div+"&subdiv="+subdiv+"&town="+town+"&meterType="+meterType);
	}
	
</script>
