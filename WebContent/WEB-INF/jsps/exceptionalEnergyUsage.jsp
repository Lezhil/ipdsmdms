<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();
		loadSearchAndFilter('sample');
		$("#reportFromDate").val('');
		$("#zone").val("").trigger("change");
		 $('#MDMSideBarContents,#reportsId,#exceptionalEnergyUsage').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		});
	</script>
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
						<i class="fa fa-edit"></i>Exceptional Energy Usage Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
								<th  class="block">Zone&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
										<option value="">Select Zone</option>
										<option value="%">ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showDivision(this.value);">
									<option id="getCircles" value="">Select Circle</option>
								</select></th>
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">
									<option id="division" value="">Select Division</option>

								</select></th></tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd">
								<select class="form-control select2me input-medium" id="sdoCode" name="sdoCode">
									<option value="">Select Sub-Division</option>
								
								</select>
									
									<th class="block">RdngMonth </th>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" placeholder="Select Month" name="reportFromDate" id="reportFromDate" value="${readingMonth}" style="cursor: pointer">
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							
							
									
							
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
					</div>
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample">
						<thead>
							<tr>
							<th>Sl No</th>
							<th>Meter No</th>
							<th>KNo</th>
							<th>Account Number</th>
							<th>Subdivision</th>
							<th>Consumer Name</th>
							<th>Consumer Status</th>
							<th>Maximum Possible Consumption</th>
							<th>Actual Consumption</th>
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
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var subdiv = $('#sdoCode').val();
	var rdngmnth=$('#reportFromDate').val();

	if (zone == "") {
		bootbox.alert("Please Select zone");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (division == "") {
		bootbox.alert("Please Select division");
		return false;
	}

	if (sdoCode == "") {
		bootbox.alert("Please Select Subdivision");
		return false;
	}

	if (rdngmnth == "") {
		bootbox.alert("Please Select Month");
		return false;
	}
	
	
	$('#updateMaster').empty();
	$.ajax({
		url : './exceptionalenergyusage',
		type : 'GET',
		data : {
			zone : zone,
			circle: circle,
			division : division,
			subdiv : subdiv,
			rdngmnth : rdngmnth,
			
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr><td>"+(i+1)+"</td>" 
					+"<td>"+ element[0]+"</td>"
					+ "<td>"+ element[1]+ "</td>"
					+ "<td>"+ element[2]+ "</td>"
					+ "<td>"+ element[3]+ "</td>"
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[5]+ "</td>"
					+ "<td>"+ element[6]+ "</td>"
					+ "<td>"+ element[7]+ "</td>";
					
				}
				$('#sample').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample');
				
			} else {
				bootbox.alert("No meters found");
			}

		}
	});

}
</script>
