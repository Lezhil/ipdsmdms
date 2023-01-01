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
<style>
.test {
	background-color: red;
}

.test2 {
	background-color: green;
}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						TableManaged.init();
						//loadSearchAndFilter('sample_3');  
						FormComponents.init();
						UIDatepaginator.init();

						/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
						$('#MDMSideBarContents,#alarm,#alarmID').addClass(
								'start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

						alarmData('all');
					});
	function showCircle(zone) {
		var zone = "All";
		$.ajax({
			url : './showCircleMDM' + '/' + zone,
			type : 'GET',

			success : function(response) {
				var html = '';
				html += "<option value=''></option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>" + response[i]
							+ "</option>";
				}

				$("#circle").html(html);
				$('#circle').select2();
			}
		});
	}

	function showDivision(circle) {
		var zone = "All";
		$
				.ajax({
					url : './showDivisionMDM' + '/' + zone + '/' + circle,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option>";
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
		var circle = $('#circle').val();
		var zone = "All";
		$
				.ajax({
					url : './showSubdivByDivMDM' + '/' + zone + '/' + circle
							+ '/' + division,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response1) {
						var html = '';
						html += "<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#subdiv').select2();
					}
				});
	}

	function mtrDetails(mtrNo) {
		window.location.href = "./mtrNoDetailsMDAS?mtrno=" + mtrNo;
	}

	/*   function submitData()
	 {	 
	 if($("#fromdate").val()=="")
	 {
	 bootbox.alert('PLease Select From Date');
	 return false;
	 }
	 if($("#todate").val()=="")
	 {
	 bootbox.alert('PLease Select To Date');
	 return false;
	 }
	 var levels = "";
	 var mtrs = "";
	 var flagChecked = 0;
	 var flagChecked1 = 0;
	 checkboxes = document.getElementsByName('alaramtype');
	 for(var i =0;i<checkboxes.length;i++)
	 {
	 if(checkboxes[i].checked)
	 {
	 levels = levels +"'" +checkboxes[i].value + "',";
	 flagChecked1 = 1;
	 }
	 }
	 if(flagChecked1==0)
	 {
	 bootbox.alert('PLease Select Atleast One Alarm Type ');
	 return false;
	 }
	 levels = levels.substring(0,levels.length-1);
	 $("#alaramtypeValue").val(levels);
	
	 checkboxes = document.getElementsByName('metertype');
	 for(var i =0;i<checkboxes.length;i++)
	 {
	 if(checkboxes[i].checked)
	 {
	 mtrs = mtrs +"'" +checkboxes[i].value + "',";
	 flagChecked = 1;
	 }
	 }
	 if(flagChecked==0)
	 {
	 bootbox.alert('PLease Select Atleast One Meter Type ');
	 return false;
	 }
	 mtrs = mtrs.substring(0,mtrs.length-1);
	 $("#metertypeValue").val(mtrs);
	 } */

	function alarmData(valtype) {
		$("#eventTR tr").remove();
		 $.ajax({
			url:"./alarms/"+valtype,
			type:"GET",
			success:function(response){
				var html="";
				$.each(response, function( index, v ) {
					
					if(v[4]==57 || v[4]==58 || v[4]==59 ||  v[4]==60 ||  v[4]==61 ||  v[4]==62 ||  v[4]==251 ||  v[4]==201 ||  v[4]==202   ){
						html+="<tr bgcolor='red'><td>"+moment(v[0]).format('DD-MM-YYYY HH:mm:ss')+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td><a onclick='mtrDetails("+v[3]+");'  style='color:blue;'>"+v[3]+"</a></td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>";
						if(v[7]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[7]+"</td>";
						}
						if(v[8]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[8]+"</td>";
						}
						if(v[9]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[9]+"</td>";
						}
						if(v[10]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[10]+"</td>";
						}
						if(v[11]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[11]+"</td>";
						}
						if(v[12]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[12]+"</td>";
						}
						
						
					}
					else{
						html+="<tr bgcolor='yellow'><td>"+moment(v[0]).format('DD-MM-YYYY HH:mm:ss')+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td><a onclick='mtrDetails("+v[3]+");'  style='color:blue;'>"+v[3]+"</a></td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>";
						if(v[7]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[7]+"</td>";
						}
						if(v[8]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[8]+"</td>";
						}
						if(v[9]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[9]+"</td>";
						}
						if(v[10]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[10]+"</td>";
						}
						if(v[11]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[11]+"</td>";
						}
						if(v[12]==null){
							html+="<td></td>";
						}
						else{
							html+="<td>"+v[12]+"</td>";
						}
						
					}
					
					
					});
				clearTabledataContent('sample_3');
				$("#eventTR").html(html);
				 //loadSearchAndFilter('sample_3');  
				//eventTR
			},
		 complete: function()
			{  
				loadSearchAndFilter('sample_3');
			}  

		}); 

	}

	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
</script>
<div class="page-content">

	<div class="portlet box blue" hidden="true">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Alarms
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal">
				<div class="form-body">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">From Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="fromdate"
											id="fromdate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">To Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="todate"
											id="todate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="row">
									<label class="control-label col-md-3">Alarm Types</label>
									<div class="col-xs-7 col-sm-3">
										<div class="checkbox-list">
											<label> <input type="checkbox" id="Tamper"
												name="alaramtype" value="Tamper"> Tamper Data
											</label> <label> <input type="checkbox" id="Outage"
												name="alaramtype" value="13"> Outage Data
											</label> <label> <input type="checkbox" id="Others"
												name="alaramtype" value="1178"> Others
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">

								<label class="control-label col-md-3">Meter Types</label>
								<div class="col-md-9">
									<div class="checkbox-list">
										<label> <input type="checkbox" id="HT"
											name="metertype" value="LT"> LT
										</label>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="alaramtypeValue" name="alaramtypeValue">
						<input type="hidden" id="metertypeValue" name="metertypeValue">
						<!--/span-->
					</div>
					<!--/row-->
				</div>
				<div class="form-actions fluid">
					<div class="row">
						<div class="col-md-6"></div>
						<div class="col-md-6">
							<div class="col-md-offset-8 col-md-9">
								<button type="submit" class="btn green"
									onclick="return submitData();">View</button>
								<button type="button" class="btn default">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- Display Error Message -->
	<c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>

	<div class="portlet box blue ">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Alarms
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<!-- <div class="tab-content">
					<div id="tab_1-1" class="tab-pane active"> -->
		<%-- <form role="form" class="form-horizontal" action=" " method="post"> --%>
		<div class="portlet-body form">
			<form id="dailyAvgLoadId" action="">
				<table>
					<tbody>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th class="block">Zone&nbsp;:</th>
							<th id=""><select
								class="form-control select2me input-medium" id="zone"
								name="circle" onchange="showCircle(this.value);">
									<option value=""></option>
									<option value="JVVNL">JVVNL</option>
							</select></th>
							<th class="block">Circle&nbsp;:</th>
							<th id="circleTd"><select
								class="form-control select2me input-medium" id="circle"
								name="circle" onchange="showDivision(this.value);">

							</select></th>
							<th class="block">Division&nbsp;:</th>
							<th id="divisionTd"><select
								class="form-control select2me input-medium" id="division"
								name="division">

							</select></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>


						<tr>

							<th class="block">Sub&nbsp;Division&nbsp;:</th>
							<th id="subdivTd"><select
								class="form-control select2me input-medium" id="sdoCode"
								name="sdoCode">

							</select></th>

							<!-- <tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr> -->
							<th class="block">From&nbsp;Date&nbsp;:</th>
							<th>
								<div class="input-group input-medium date date-picker"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years"
									id="five">
									<input type="text" autocomplete="off" class="form-control"
										name="selectedDateName" id="selectedFromDateId"> <span
										class="input-group-btn">
										<button class="btn default" type="button" id="six">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</th>
							<th class="block">To&nbsp;Date&nbsp;:</th>
							<th>
								<div class="input-group input-medium date date-picker"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years"
									id="five">
									<input type="text" autocomplete="off" class="form-control"
										name="selectedDateName" id="selectedToDateId"> <span
										class="input-group-btn">
										<button class="btn default" type="button" id="six">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</th>
							<!--<tr>
							<th colspan="8"></th>
						</tr>
							<th>
								<div>
								<div class="modal-footer">
 									<div class="col-md-offset-4 col-md-8">
 										<!-- <button type="button" class="btn yellow">Reset</button>   
										<button type="button" onclick="validation()" class="btn green">View All</button>
										<button type="submit" class="btn red" onclick="return alarmData('EON');">Event On</button>
										
									</div>
									
								</div>
							</th>-->
						</tr>
					</tbody>
				</table>
				<div class="modal-footer">

					<button type="button" onclick="validation()" class="btn blue pull-left">View
						</button>
					<button type="submit" class="btn red pull-left"
						onclick="return alarmData('EON');">Event On</button>
						<button type="button" class="btn green pull-left"
								onclick="return alarmData('EOFF');">Event Off</button>

				</div>


			</form>



		</div>
		<div class="portlet-body ">
			<!-- <div class="form-actions fluid">
				<div class="row">
					<div class="col-md-6"></div>
					<div class="col-md-6">
						<div class="col-md-offset-8 col-md-9">
							<button type="submit" class="btn red"
								onclick="return alarmData('EON');">Event On</button>
							<button type="button" class="btn green default"
								onclick="return alarmData('EOFF');">Event Off</button>
						</div>
					</div>
				</div>
			</div> -->
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							Tools <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right">
							<li><a href="#" id="print">Print</a></li>
							<li><a href="#" id="excelExport2"
								onclick="tableToExcel2('sample_3', 'AlarmDetails')">Export
									to Excel</a></li>
						</ul>
					</div>
				</div>
			</div>
			<table class="table  table-bordered table-hover" id="sample_3">

				<thead>
					<tr>

						<th>Alarm Date Time</th>
						<th>SubDivison Name</th>
						<th>Category</th>
						<th>Meter Serial Number</th>
						<th>Event Code</th>
						<th>Event Status</th>
						<th>Alarm Description</th>
						<th>Voltage R(V)</th>
						<th>Voltage Y(V)</th>
						<th>Voltage B(V)</th>
						<th>Line Current(R)</th>
						<th>Line Current(Y)</th>
						<th>Line Current(B)</th>
					</tr>
				</thead>
				<tbody id="eventTR">

					<%-- 	<c:forEach var="element" items="${alaramlist}">
						
						 <c:choose>
						 <c:when test="${(element[4] eq '57') or (element[4] eq '58') or (element[4] eq '59') or (element[4] eq '60') or (element[4] eq '61') or (element[4] eq '62') or (element[4] eq '251') or (element[4] eq '201') or (element[4] eq '202')}">
						  <tr bgcolor="red">
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4]}</td>
						<td>${element[6]}</td>
						<td>${element[7]}</td>
						<td>${element[8]}</td>
						<td>${element[9]}</td>
						<td>${element[10]}</td>
						<td>${element[11]}</td>
						<td>${element[12]}</td>
						</tr>
						 
						 
						 </c:when>
						 <c:otherwise>
						 <tr>
						<td>${element[0]}</td>
						<td>${element[1]}</td>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						<td>${element[4]}</td>
						<td>${element[6]}</td>
						<td>${element[7]}</td>
						<td>${element[8]}</td>
						<td>${element[9]}</td>
						<td>${element[10]}</td>
						<td>${element[11]}</td>
						<td>${element[12]}</td>
						</tr>
						 
						 
						 </c:otherwise>
						 
						 </c:choose>
						
						
						</c:forEach> --%>
				</tbody>
			</table>
		</div>


		<%-- </form> --%>
		<!-- </div>
			</div> -->
	</div>


</div>


<script>
	function submitDataLoadExcel() {
		var metrno = $("#meterVal").val();

		if (metrno.trim() == '') {
			bootbox.alert('Please enter Meter number');
			return false;
		}

		var Billmonth = $("#selectedDateId").val();

		//alert("metrno--"+metrno+"--Billmonth-"+Billmonth);

		window.location.href = "./downloadLOadSurveyExcel?metrno=" + metrno
				+ "&Billmonth=" + Billmonth;

		/*  /downloadLOadSurveyExcel*/
	}
</script>