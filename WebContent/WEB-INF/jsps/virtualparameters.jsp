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
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$('.datepicker').datepicker({
							format : 'dd-mm-yyyy',
							autoclose : true,
							endDate : "today",

						}).on('changeDate', function(ev) {
							$(this).datepicker('hide');
						});

						$('.datepicker').keyup(
								function() {
									if (this.value.match(/[^0-9]/g)) {
										this.value = this.value.replace(
												/[^0-9^-]/g, '');
									}
								});
						var zone = '%';
						showCircle(zone);
						App.init();
						TableManaged.init();
						loadSearchAndFilter('sample_1');	
						FormComponents.init();
						$('#MDMSideBarContents,#DPId,#vlparameters').addClass(
								'start active ,selected');
						$(
								"#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

					});
</script>
<script>
var zone="%";
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
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
						html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Virtual Location Parameters
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<div class="row" style="margin-left: -1px;">

						<!-- <table style="width: 38%">
							<tbody>
								<tr> -->
								<div class="col-md-3">
						         <div id="circleTd" class="form-group">
									<select
										class="form-control select2me input-medium" name="circle"
										id="circle" onchange="showDivision(this.value);">
											<option value="">Circle</option>
									</select>
									</div>
					             </div>
								<div class="col-md-3">
						        <div id="divisionTd" class="form-group">
									<select
										class="form-control select2me input-medium" name="division"
										id="division" onchange="showSubdivByDiv(this.value)">
											<option value="">Division</option>
									</select>
								</div>
					            </div>
					            <div class="col-md-3">
					         	<div id="subdivTd" class="form-group">
									<select
										class="form-control select2me input-medium" name="sdocode"
										id="sdocode">
											<option value="">Sub Division</option>
									</select>
									</div>
					            </div>
					            <div class="col-md-3">
					         	<div id="vltypeTd" class="form-group">
									<select
										class="form-control select2me input-medium" name="vltype"
										id="vltype">
											<option value="">Please Select VL Type</option>
											<option value="Consumer">Consumer</option>
											<option value="Feeder">Feeder</option>
											<option value="Dt">DT</option>
									</select>
								</div>
					            </div>
					            </div>
									
						<!-- 		</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr> -->
								
								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3">
						         	  <div id="vlnameTd" class="form-group">
									    <select
											class="form-control select2me input-medium" name="vlname"
											id="vlname">
												<option value="">Please Select VL Name</option>
												<c:forEach var="elements" items="${locList}">
													<option value="${elements}">${elements}</option>
												</c:forEach>
										</select>
									 </div>
									</div>
									
							
									
								   <div class="col-md-3">
										<div class="form-group">
												<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
												<input type="text" placeholder="Select From Date" class="form-control" name="fromDate" id="fromDate"  style="cursor: pointer">
												<span class="input-group-btn">
												<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												</div>
										</div>	
									</div>	
									<div class="col-md-3">
										<div class="form-group">
												<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
												<input type="text" class="form-control" placeholder="Select To Date" name="toDate" id="toDate"  style="cursor: pointer">
												<span class="input-group-btn">
												<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												</div>
										</div>
									</div>
									
																
												
								
										<%-- <div
											class="input-group input-large date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" autocomplete="off" placeholder="From Date"
												class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd"
												data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span> <input type="text"
												autocomplete="off" placeholder="To Date"
												class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd"
												data-date-viewmode="years" name="toDate" id="toDate">
										</div> --%>
									
									<!-- <th class="block">From&nbsp;Date </th>
							<td>
							
							<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
							</td> -->

									<button type="button" id="viewFeeder" style="margin-left: 480px;" onclick="return getReport()" name="viewconsumers" class="btn yellow"> <b>View</b></button>
									<br /><hr />

					</div>


					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Virtual Location parameters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Subdivision</th>
								<th>Virtual Location Type</th>
								<th>Virtual Location Name</th>
								<th>IP</th>
								<th>Kwh</th>
								<th>Kvah</th>
								<th>Kw</th>
								<th>Kva</th>
								<th>PF</th>
							</tr>
						</thead>
						<tbody id="updateMaster">

						</tbody>
					</table>


				</div>
			</div>
		</div>
	</div>

</div>



<script>
	function getReport() {
		var circle = $('#circle').val();
		var division = $('#division').val();
		var subdiv = $('#sdoCode').val();
		var vlType = $('#vltype').val();
		var vlName = $('#vlname').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		//vat fd=document.getElementById("FromDate");
		//var td=document.getElementById("#")
		

		$('#updateMaster').empty();
		$.ajax({
			url : './getvlParameters',
			type : 'GET',
			dataType : 'JSON',
			data : {
				circle : circle,
				division : division,
				subdiv : subdiv,
				vlType : vlType,
				vlName : vlName,
				fromDate : fromDate,
				toDate : toDate
			},
			asynch : false,
			cache : false,
			success : function(response) {
				if (response.length != 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr>" + "<td>"
								+(element[0]==null?"": element[0])
								+ "</td>"
								+ "<td>"
								+ (element[2]==null?"":element[2])
								+ "</td>"
								+ "<td>"
								+ (element[3]==null?"":element[3])
								+ "</td>"
								+ "<td>"
								+ (element[4]==null?"":moment(element[4]).format(
										'DD-MM-YYYY hh:mm:ss')) + "</td>"
								+ "<td>" + (element[5]==null?"":element[5]) + "</td>" + "<td>"
								+(element[6]==null?"": element[6] )
								+ "</td>" + "<td>" + (element[7]==null?"":element[7])
								+ "</td>" + "<td>" + (element[8]==null?"":element[8]) + "</td>"
								+ "<td>" + (element[9]==null?"":element[9]) + "</td>";
					}
					$('#sample_1').dataTable().fnClearTable();
					$('#updateMaster').html(html);
					loadSearchAndFilter('sample_1');

				} else {
					bootbox.alert("No meters found");
				}

			}
		});

	}
</script>
