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
						formreset();
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
						$('#MDASSideBarContents,#meterOper,#ChangeAndInstallation,#changeMeter').addClass('start active ,selected');
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
  
  
  var tableToExcel=(function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport12").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport12").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()
	
</script>
<script>
	function getmeterData() {
		
		
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		/* var division = $('#division').val();
		var subdivision = $('#sdoCode').val(); */
		var town = $('#LFtown').val();
		var meterType = $('#meterType').val();
	//	alert(zone);
	//	alert(circle);
	//alert(town);

		if (zone == "") {
			bootbox.alert("Please Select zone");
			return false;
		}

		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		/* if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (subdivision == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		} */
		if (town == "") {
			bootbox.alert("Please Select Town");
			return false;
		}
		if (meterType == "") {
			bootbox.alert("Please Select Meter Type");
			return false;
		}
          $('#imageee').show();
		
		$
				.ajax({
					url : './viewMeterDetails',
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
						 $('#imageee').hide();
						$('#sample_1').dataTable().fnClearTable();
						$("#updateMaster1").html('');
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>"
									    + "<td>"+ j + "</td>"
									    <c:if test = "${editRights eq 'yes'}">
										+ "<td>"+ "<button  onclick='mapOldMtrData(\""+ resp[2]+ "\",\""+ resp[3]+ "\",\""+ resp[4]+ "\")' class='btn yellow' data-toggle='modal'  data-target='#stack2'>Change Meter</button>"+ "</td>"
									    </c:if>		    
										+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ meterType + " </td>"
										+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"	
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
										+"</tr>";
									
					

										
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster1").html(html);
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
						<i class="fa fa-edit"></i>Change Meter
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
						</div> --%>

						<%-- <div class="col-md-4">
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

						</div> --%>
						<div class="col-md-3">
							<div class="form-group">
							<label class="control-label">Meter Type:</label>
								<select class="form-control select2me" id="meterType" name="meterType">
									<option value="">Select Meter Type</option>
									<%-- <option value="ALL">ALL</option>  --%>
									<option value="FEEDER METER">FEEDER METER</option>
									<option value="DT">DT METER</option>
									<option value="BOUNDARY METER">BOUNDARY METER</option>
								</select>
							</div>
							</div>
							
							<div type="button" id="viewmeterData" onclick="getmeterData()" style="margin-top:25px;" name="viewmeterData" class="btn green">
								<b>VIEW</b>
							</div>
						<div id="imageee" hidden="true" style="text-align: center;">
							<h3 id="loadingText">Loading..... Please wait.</h3>
							<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
								style="width: 4%; height: 4%;">
						</div>



					</div>
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom" >
						<div id="showConsumer">
							<!-- <div type="button" id="viewmeterData" onclick="getmeterData()" style="margin-left: 500px;" name="viewmeterData" class="btn green">
								<b>VIEW</b>
							</div> -->
							
							
			<div id="excel1" >
			<div class="btn-group pull-right">
				<button class="btn dropdown-toggle" data-toggle="dropdown">
					Tools <i class="fa fa-angle-down"></i>
				</button>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" id=""
						onclick="exportPDF('sample_1','Change Meter')">Export to PDF</a></li>
					<li><a href="#" id="excelExport12"
						onclick="tableToExcel('sample_1',' Change Meter');">Export to
							Excel</a></li>
				</ul>
			</div></div>

							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>SL NO.</th>
										<c:if test = "${editRights eq 'yes'}">
										<th>CHANGE</th>
										 </c:if>
										<th>SUB-DIV NAME</th>
										<th>TOWN NAME</th>
										<th>METER TYPE</th>
										<th>METER NO</th>
										<th>NAME</th>
										<th>CODE</th>
										<th>PHASE</th>	
										<th>METER MAKE</th>
										<th>MF</th>
									</tr>
								</thead>
								<tbody id="updateMaster1">

								</tbody>
							</table>
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
							<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;"  >Change Meter Details</span></h4>
							</div>
								<div class="modal-body">
									<form action="#" id="editMeterReplacement" method="post">
										
									 <div class="row"> 
									 
									 		<div class="col-md-6">
												<div id="addsectionId" class="form-group">
												 <label class="control-label">Select Reason</label>
													 <span style="color: red" class="required">*</span>
													<select class="form-control select2me" id="reasonid" onchange="reason(this.value)" name="reasonid">
														<option value="">Select Changed Reason</option>
															<option value="Meter burnt with Final Reading">Meter burnt with Final Reading</option> 
															<option value="Defective">Defective</option> 
															<option value="Normal">Normal</option> 
															<option value="Meter burnt With Out Final Reading">Meter burnt With Out Final Reading</option> 
															<option value="Other reasons">Other reasons</option>
														
													</select>
												</div>
											</div>
											
											<div class="col-md-6">
												<div id="otrrsn" class="form-group" style="display:none">
													<label class="control-label">Other Reasons</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="othrreason"
														class="form-control placeholder-no-fix"
														placeholder="Enter Reason" name="OTHRREASONN"
														maxlength="50"></input>
												</div>
											</div>
								          
								          <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter No" name="uoldmtrno" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Make</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="umeterMakeId"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter Make" name="umeterMakeId" readonly="readonly"
														maxlength="50"></input>
													 
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter Old Meter MF" name="uoldmf" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div>
								          
										
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Location Code</label>
													 <span style="color: red" class="required">*</span>
													<input type="text" id="uloccode"
														class="form-control placeholder-no-fix" placeholder="Enter Location Code" 
														readonly="readonly" name="uloccode"
														maxlength="50"></input>
												</div>
											 </div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Location Name</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="ulocname"
														class="form-control placeholder-no-fix"
														placeholder="Enter Location Name" name="ulocname" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div>

											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Phase</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldphase"
														class="form-control placeholder-no-fix"
														placeholder="Enter Phase" name="uoldphase" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div>
											
											<!-- <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Old Meter Last Reading</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="uoldmtrrdg"
														class="form-control placeholder-no-fix"
														placeholder="Old Meter Last Reading" name="UOLDMTRRDG" readonly="readonly"
														maxlength="50"></input>
												</div>
											</div> -->
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">OLD Meter Final KWH</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="oldmtrkwh"
														class="form-control placeholder-no-fix"
														placeholder="OLD Meter Final KWH" name="oldmtrkwh"
														maxlength="50" readonly="readonly"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">OLD Meter Final KVAH</label>
													 <span style="color: red" class="required" >*</span> 
													 <input
														type="text" id="oldmtrkvh"
														class="form-control placeholder-no-fix"
														placeholder="Old Meter Final KVAH" name="oldmtrkvh"
														maxlength="50" readonly="readonly"></input>
														
														<input type="text" id="mdkw" name="mdkw" hidden="true"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">OldMeter Released Date</label>
													 <span style="color: red" class="required">*</span> 
															<div class="input-group date date-picker" data-date-format="dd-mm-yyyy" data-date-end-date="0d" data-date-viewmode="years">
															<input type="text" class="form-control" placeholder="Select Date" name="releasedDate" id="releasedDate"  style="cursor: pointer">
															<span class="input-group-btn">
															<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
															</span>
															</div>
												</div>
											</div>
											</div>
											 <div class="row"> 
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter No</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmtrno"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter No" name="unewmtrno"
														maxlength="50"  onchange="return checkMeterNo(this.value)"></input>
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
										    	   <label class="control-label">New Meter Type</label>
												   <span style="color: red" class="required">*</span>
														<select name="meterConnType"
															id="meterConnType" class="form-control select2me">
															<option value="">Select Meter Type </option>
															<option value="1 Phase">1-Phase</option>
															<option value="3 Phase">3-Phase</option>
							
														</select>
											 	 </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter Capacity</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewcapacity"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter Capacity" name="unewcapacity"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter MF</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmf"
														class="form-control placeholder-no-fix"
														placeholder="Enter New Meter MF" name="unewmf"
														maxlength="50"></input>
												</div>
											</div>

											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter Initial KWH</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmtrkwh"
														class="form-control placeholder-no-fix"
														placeholder="New Meter Initial KWH" name="unewmtrkwhs"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">New Meter Initial KVAH</label>
													 <span style="color: red" class="required">*</span> 
													 <input
														type="text" id="unewmtrkvh"
														class="form-control placeholder-no-fix"
														placeholder="New Meter Initial KVAH" name="unewmtrkvh"
														maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Installed Date</label>
													 <span style="color: red" class="required">*</span> 
															<div class="input-group date date-picker" data-date-format="dd-mm-yyyy" data-date-end-date="0d" data-date-viewmode="years">
															<input type="text" class="form-control" placeholder="Select Date" name="installedDate" id="installedDate"  style="cursor: pointer">
															<span class="input-group-btn">
															<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
															</span>
															</div>
												</div>
											</div>
									
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Meter Under IPDS Scheme</label>
													 <span style="color: red" class="required">*</span> 
													 <select class="form-control select2me" id="schemeId" name="schemeId">
														<option value="">Select Meter Under IPDS Scheme</option>
															<option value="Yes">YES</option>
															<option value="No">NO</option>
													</select>
												</div>
											</div>
											
											
												  
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="editMeterReplacement"
												type="button" onclick="return editMeterReplacement3()">Change</button>
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
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
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


	function mapOldMtrData(meterno){
		$.ajax({
			url : './getOldMtrDataforMtrChange',
			type : 'POST',
			data : {
				meterno : meterno,
			},
			dataType : 'json',
			success : function(response) {
				if(response.length>0)
					{
					for(var i=0;i<response.length;i++){
						
						var data=response[0];
						
						$("#uoldmtrno").val(data[0]);
						$("#umeterMakeId").val(data[1]);
						$("#uoldmf").val(data[2]);
						$("#uoldphase").val(data[3]);
						$("#ulocname").val(data[4]);
						$("#uloccode").val(data[5]);
						$("#oldmtrkwh").val(data[6]);
						$("#oldmtrkvh").val(data[7]);
						$("#mdkw").val(data[8]);
						
						/* $("#uoldmtrrdg").val(data[0]);
						$("#uoldmtrno").val(data[0]); */
					}
					}
				
			}
		});
		}


	function editMeterReplacement3(){
		 var reasonid=$("#reasonid").val();
		var uoldmtrno=$("#uoldmtrno").val();
		var umeterMakeId=$("#umeterMakeId").val();
		var uoldmf=$("#uoldmf").val();
		var uloccode=$("#uloccode").val();
		var ulocname=$("#ulocname").val();
		var uoldphase=$("#uoldphase").val();
		var oldmtrkwh=$("#oldmtrkwh").val();
		var oldmtrkvh=$("#oldmtrkvh").val();
		var releasedDate=$("#releasedDate").val();
		var unewmtrno =$("#unewmtrno").val();
		var unewmeterMakeId =$("#unewmeterMakeId").val();
		var meterConnType =$("#meterConnType").val();
		var unewcapacity =$("#unewcapacity").val();
		var unewmf =$("#unewmf").val();
		var unewmtrkwh =$("#unewmtrkwh").val();
		var unewmtrkvh =$("#unewmtrkvh").val();
		var schemeId =$("#schemeId").val();
		var installedDate =$("#installedDate").val();
		var mdkw=$("#mdkw").val();
		var othrrsn="" ; 


		 if (reasonid == "") {
			bootbox.alert("Please Select Meter Change Reason.");
			return false;
		}
		if(reasonid=="Other reasons"){
			othrrsn =$("#othrreason").val();
			if (othrrsn == "") {
				bootbox.alert("Please Enter Other Reason.");
				return false;
			}
			reasonid="Other- "+othrrsn;
		}
		//new column
		if (releasedDate == "") {
			bootbox.alert("Please Select Old Meter Release Date");
			return false;
		}

		if (unewmtrno == "") {
			bootbox.alert("Please Enter New Meter No");
			return false;
		}

		if (unewmeterMakeId == "") {
			bootbox.alert("Please Select New Meter Make");
			return false;
		}
		//new column
		if (meterConnType == "") {
			bootbox.alert("Please Select New Meter Type");
			return false;
		}
		
		if (unewcapacity == "" || isNaN(unewcapacity)) {
			bootbox.alert("Please Enter New Meter Capacity");
			return false;
		}

		if (unewmf == "" || isNaN(unewmf)) {
			bootbox.alert("Please Enter New Meter MF");
			return false;
		}
		if (unewmtrkwh =="" || isNaN(unewmtrkwh)) {
			bootbox.alert("Please Enter New Meter first KWH");
			return false;
		}
		if (unewmtrkvh =="" || isNaN(unewmtrkvh)) {
			bootbox.alert("Please Enter New Meter first KVAH");
			return false;
		}	
		if (installedDate == "") {
			bootbox.alert("Please Select Meter Installed Date");
			return false;
		} 		

		$.ajax(
				{
						type : "GET",
						url : "./meterChangeProcess",
						data:{reasonid:reasonid,
							uoldmtrno:uoldmtrno,
							umeterMakeId:umeterMakeId,
							uoldmf:uoldmf,
							uloccode:uloccode,
							ulocname:ulocname,
							uoldphase:uoldphase,
							oldmtrkwh:oldmtrkwh,
							oldmtrkvh:oldmtrkvh,
							releasedDate:releasedDate,
							unewmtrno:unewmtrno,
							unewmeterMakeId:unewmeterMakeId,
							meterConnType:meterConnType,
							unewcapacity:unewcapacity,
							unewmf:unewmf,
							unewmtrkwh:unewmtrkwh,
							unewmtrkvh:unewmtrkvh,
							schemeId:schemeId,
							installedDate:installedDate,
							mdkw:mdkw
							},
						dataType : 'txt',
						success : function(response)
						{
							if(response=="NoVal"){
								bootbox.alert("Meter Change is Unsuccessful");
								}
							else
								{
								bootbox.alert(response);
								}
							/* bootbox.alert(msg); */
							formreset();
							//getMtrChangeData();
							//$("#meterDetailsId").empty();
							$('#stack2').modal('hide');
						}
				}); 
		
	}
	
	
function editMeterReplacement1(){

 
	var reasonid=$("#reasonid").val();
	var uoldmtrno=$("#uoldmtrno").val();
	var umeterMakeId=$("#umeterMakeId").val();
	var uoldmf=$("#uoldmf").val();
	var uloccode=$("#uloccode").val();
	var ulocname=$("#ulocname").val();
	var uoldphase=$("#uoldphase").val();
	var oldmtrkwh=$("#oldmtrkwh").val();
	var oldmtrkvh=$("#oldmtrkvh").val();
	var releasedDate=$("releasedDate").val();
	
	var unewmtrno =$("#unewmtrno").val();
	var unewmeterMakeId =$("#unewmeterMakeId").val();
	var meterConnType =$("#meterConnType").val();
	var unewcapacity =$("#unewcapacity").val();
	var unewmf =$("#unewmf").val();
	var unewmtrkwh =$("#unewmtrkwh").val();
	var unewmtrkvh =$("#unewmtrkvh").val();
	var schemeId =$("#schemeId").val();
	var installedDate =$("#installedDate").val();
	
	var othrrsn="" ; 


	 if (reasonid == "") {
		bootbox.alert("Please Select Meter Change Reason.");
		return false;
	}
	if(reasonid=="Other reasons"){
		othrrsn =$("#othrreason").val();
		if (othrrsn == "") {
			bootbox.alert("Please Enter Other Reason.");
			return false;
		}
		reasonid="Other- "+othrrsn;
	}

	if (unewmtrno == "") {
		bootbox.alert("Please Enter New Meter No");
		return false;
	}

	if (unewmeterMakeId == "") {
		bootbox.alert("Please Select New Meter Make");
		return false;
	}

	if (unewcapacity == "" || isNaN(unewcapacity)) {
		bootbox.alert("Please Enter New Meter Capacity");
		return false;
	}

	if (unewmf == "" || isNaN(unewmf)) {
		bootbox.alert("Please Enter New Meter MF");
		return false;
	}
	if (unewmtrkwh =="" || isNaN(unewmtrkwh)) {
		bootbox.alert("Please Enter New Meter first KWH");
		return false;
	}
	if (unewmtrkvh =="" || isNaN(unewmtrkvh)) {
		bootbox.alert("Please Enter New Meter first KVAH");
		return false;
	}	
	if (installedDate == "") {
		bootbox.alert("Please Select Meter Installed Date");
		return false;
	} 
	if (schemeId == "") {
		bootbox.alert("Please Select IPDS Schema");
		return false;
	} 

				

 $.ajax(
			{
					type : "GET",
					url : "./meterChangeProcess",
					"array":{

						reasonid:reasonid,
						uoldmtrno:uoldmtrno,
						umeterMakeId:umeterMakeId,
						uoldmf:uoldmf,
						uloccode:uloccode,
						ulocname:ulocname,
						uoldphase:uoldphase,
						oldmtrkwh:oldmtrkwh,
						oldmtrkvh:oldmtrkvh,
						releasedDate:releasedDate,
						unewmtrno:unewmtrno,
						unewmeterMakeId:unewmeterMakeId,
						meterConnType:meterConnType,
						unewcapacity:unewcapacity,
						unewmf:unewmf,
						unewmtrkwh:unewmtrkwh,
						unewmtrkvh:unewmtrkvh,
						schemeId:schemeId,
						installedDate:installedDate

						},
					dataType : 'txt',
					success : function(response)
					{
						
						bootbox.alert(response);
						formreset();
						//getMtrChangeData();
						//$("#meterDetailsId").empty();
						$('#stack2').modal('hide');
						
					}
			
			}); 
	
}

function checkMeterNo(meterno)
{
	
	  if(meterno==""){
	  	bootbox.alert("Please enter Meterno");
	  	$("#meterno").val("");
	  	return false;
	  }
	 
	$.ajax(
			{
					type : "GET",
					url : "./checkMeterNoInInventory/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response.length==0)
						{
							bootbox.alert("Meter Number " +meterno+ " Not In Stock");
							$("#unewmtrno").val("");
							
							return false;
						}
						else
							{
							for(var i=0;i<response.length;i++)
								{
								if(response[0].meter_status=="INSTOCK")
	  								{
									//checkMeterNoInMasterMain(meterno,response[0].meter_make);
								}
								else{
									bootbox.alert("Meter Number " +meterno+ " Already Installed");
		  							$("#unewmtrno").val("");
		  							return false;
	  	  	  					 }
							}
					}
					}
			});
	  }
function reason(other){

	if(other=="Other reasons"){
		$("#otrrsn").show();
		}else{
	    $("#otrrsn").hide();
	}
	
}

function formreset(){
	
    $('#othrreason').val('');
    $('#uoldmtrno').val('');
    $('#uoldmf').val('');
    $('#uloccode').val('');
    
    $('#ulocname').val('');
    $('#uoldphase').val('');
    $('#oldmtrkwh').val('');
    $('#oldmtrkvh').val('');
    $('#unewmtrno').val('');
    
    $('#unewcapacity').val('');
    $('#unewmf').val('');
    $('#unewmtrkwh').val('');
    $('#unewmtrkvh').val('');
    $('#installedDate').val('');

    
    $("#reasonid").val("").trigger("change");
    $("#meterConnType").val("").trigger("change");
    $("#schemeId").val("").trigger("change");
	$("#unewmeterMakeId").val("").trigger("change");
	$("#umeterMakeId").val("").trigger("change");
}

function exportPDF()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var division = '%';
	var subdivision ='%';
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
	
	window.location.href=("./ChangeMeterPDF?zne="+zne+"&cir="+cir+"&div="+div+"&subdiv="+subdiv+"&town="+town+"&meterType="+meterType);

}
	
</script>
