<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<!-- -----Export Grid Data----- -->
<!-- <script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script> -->

<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						App.init();
						TableManaged.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						$('#MDMSideBarContents,#alarmID,#viewalarmshst')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');
					});
</script>
<script>
	var zone = "%"
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
	                     html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircleTd").html(html);
						$('#LFcircle').select2();
					}
				});
	}

	function showDivision(circle) {
		$
				.ajax({
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd").html(html);
						$('#division').select2();
					}
				});
	}
	function showSubdivByDiv(division) {
		// var zone = $('#zone').val();
		var circle = $('#circle').val();
		$
				.ajax({
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	}

	 function showTownNameandCode(circle){
 		var zone =  $('#LFzone').val(); 
		
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
	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
	               
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
	  </script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>View Alarm History
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
		<jsp:include page="locationFilter.jsp"/> 

					<div class="modal-body">

				<div class="row">
					<div class="form-group col-md-3">
						<div class="form-group">
							<label><b>Location</b></label>
							<select
										class="form-control select2me input-medium" id="loctype"
										name="loctype">
											<option value="">Location Type</option>
											<option value='%'>ALL</option>
											<c:forEach var="elements" items="${locationtype}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select>
									</div>
									</div>
						<div class="col-md-3">
						<div class="form-group">
							<label><b>From Date</b></label>
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
								</div>
								</div>	
								
						<div class="col-md-3">
						<div class="form-group">
							<label><b>To Date</b></label>
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
								</div>
								</div>
						<div class="col-md-3">
						<button type="button" id="viewFeeder"
											style="margin-top: 24px;" onclick="return getReport()"
											name="viewconsumers" class="btn yellow">
											<b>View</b>
										</button> 
										</div>
						
				<%-- 				<tr>
									<th id="zone" class="block">Zone&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="circle" onchange="showCircle(this.value);">
										<option value="">Zone</option>
											<option value='All'>ALL</option>
											<c:forEach var="elements" items="${circles}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
								</select></th>
									<th class="block">Circle&nbsp;:</th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showDivision(this.value);">
											<option value="">circles</option>
											<option value='%'>ALL</option>
											<c:forEach var="elements" items="${circles}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></th>
									<th class="block">Division&nbsp;:</th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">

									</select></th>


									<th class="block">Sub&nbsp;Division&nbsp;:</th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode"></select>
								</tr> --%>
								<!-- <tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr> -->
				<%-- 				<tr>
									<th class="block">Location&nbsp;Type&nbsp;:</th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="loctype"
										name="loctype">
											<option value="">Location Type</option>
											<option value='%'>ALL</option>
											<c:forEach var="elements" items="${locationtype}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></th>
									<!-- 	
									<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr> -->
									<th class="block">From&nbsp;Date&nbsp;:</th>
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

									<th class="block">To&nbsp;Date&nbsp;:</th>
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


									<td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value="ALL">ALL</option>
											<c:forEach items="${subdivlist}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>

								</tr> --%>
							<!-- 	<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<td colspan="16"></td>
								</tr>
								<tr>
									<th colspan="64"></th>
								</tr>
								<tr>
 -->
									<!-- <td colspan="3"></td>
									<td>
										<button type="button" id="viewFeeder"
											style="margin-left: 20px;" onclick="return getReport()"
											name="viewconsumers" class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr>
							</tbody>
						</table> -->
						<p>&nbsp;</p>
					</div>

			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print" onclick="exportPDF();">Export
										To Pdf</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Alarm History Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Sl No</th>
								<th>Town</th>
								<th>Location Type</th>
								<th>Location Identity</th>
								<th>Location Name</th>
								<th>Alarm Setting</th>
								<th>Alarm Type</th>
								<th>Alarm Name</th>
								<th>Alarm Priority</th>
								<th>Alarm Date</th>
								<th>Alarm Acknowledge Message</th>
								<th>Alarm Acknowledge Date</th>
								<th>Alarm Acknowledge By</th>
							</tr>
						</thead>

						<tbody id="alarms">

						</tbody>
					</table>


				</div>
			</div>
		</div>
	</div>

</div>



<script>
	function getReport() {
		var loctype = $('#loctype').val();
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var division = $('#division').val();
		var subdiv = $('#sdoCode').val();
		var fromdate = $('#reportFromDate').val();
		var todate = $('#reportToDate').val();

		const date1 = new Date(fromdate);
		const date2 = new Date(todate);
		const diffTime = Math.abs(date2.getTime() - date1.getTime());
		const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
		if (zone == "" || zone == null) {
			bootbox.alert("Please Select Region");
			return false;
		}
		if (circle == "" || circle == null) {
			bootbox.alert("Please Select Circle");
			return false;
		}
		 if (town == "" || town == null) {
			bootbox.alert("Please Select  Town");
			return false;
		}
		/*if (subdiv == "" || subdiv == null) {
			bootbox.alert("Please Select sub division");
			return false;
		} */
		if (loctype == "" || loctype == null) {
			bootbox.alert("Please Select Location");
			return false;
		}
		if (fromdate == "" || fromdate == null) {
			bootbox.alert("Please Select from Date");
			return false;
		}
		if (todate == "" || todate == null) {
			bootbox.alert("Please Select to Date");
			return false;
		}

		if(new Date(fromdate)>new Date(todate))
		{
			bootbox.alert("Please Select Correct Date Range");
			return false;
		}
	
		if(diffDays>30){
			bootbox.alert("Difference Between Date Should be less than 30 days ");
			return false;
		}
		$('#alarms').empty();
		$('#imageee').show();
		$.ajax({
			url : './getalarmshistory',
			type : 'GET',
			data : {
				circle : circle,
				division : division,
				subdiv : subdiv,
				fromdate : fromdate,
				todate : todate,
				loctype : loctype,
				town : town
			},
			dataType : 'JSON',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				if (response.length != 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr><td>"
								+ (i + 1)
								+ "</td>"
								+ "<td>"
								+ (element[15] == null ? "" : (element[15]))
								+ "</td>"
								+ "<td>"
								+ (element[2] == null ? "" : (element[2]))
								+ "</td>"
								+ "<td>"
								+ (element[3] == null ? "" : (element[3]))
								+ "</td>"
								+ "<td>"
								+ (element[4] == null ? "" : (element[4]))
								+ "</td>"
								+ "<td>"
								+ (element[5] == null ? "" : (element[5]))
								+ "</td>"
								+ "<td>"
								+ (element[6] == null ? "" : (element[6]))
								+ "</td>"
								+ "<td>"
								+ (element[7] == null ? "" : (element[7]))
								+ "</td>"
								+ "<td>"
								+ (element[8] == null ? "" : (element[8]))
								+ "</td>"
								+ "<td>"
								+ moment(element[9]).format(
										'DD-MM-YYYY HH:mm:ss')
								+ "</td>"
								+ "<td>"
								+ (element[11] == null ? "" : (element[11]))
								+ "</td>"
								+ "<td>"
								+ moment(element[12]).format(
										'DD-MM-YYYY HH:mm:ss') + "</td>"
								+ "<td>"
								+ (element[13] == null ? "" : (element[13]))
								+ "</td>";
					}
					$('#sample_1').dataTable().fnClearTable();
					$('#alarms').html(html);
					loadSearchAndFilter('sample_1');

				} else {
					$('#sample_1').dataTable().fnClearTable();
					$('#imageee').hide();
					loadSearchAndFilter('sample_1');
					bootbox.alert("No alarms found");
				}

			}
		});

	}
	function exportGridData() {
		html2canvas(document.getElementById('sample_1'), {
			onrendered : function(canvas) {
				var data = canvas.toDataURL();
				var docDefinition = {
					content : [ {
						image : data,
						width : 500
					} ]
				};
				pdfMake.createPdf(docDefinition).download(
						"Alarm_History_Details.pdf");
			}
		});

	}

	function exportPDF()
	 {
		var zone=$('#LFzone').val();
		var loctype = $('#loctype').val();
		var circle = $('#LFcircle').val();
		var town=$('#LFtown').val();
		var fromdate = $('#reportFromDate').val();
		var todate = $('#reportToDate').val();
  		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');

		var zne="",cir="",townn="",loctype1="";
		
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
		if(town=="%"){
			townn="ALL";
		}else{
			townn=town;
		}
		
		if(loctype=="%"){
			loctype1="ALL";
		}else{
			loctype1=loctype;
		}
		
		//window.location.href="./createHclOutputCSV?subdivision="+subdivision+"&billMonth="+billMonth+"&group="+group+"&binder="+binder+"&agency="+agency;
 		window.location.href="./ViewHistoryAlarmsPDF?zne="+zne+"&cir="+cir+"&townn="+townn+"&fromdate="+fromdate+"&todate="+todate+"&loctype1="+loctype1+"&townname="+townname;
	 }
</script>


