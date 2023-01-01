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
		  $('#MDMSideBarContents,#dataEstimation,#dataValidId').
			addClass('start active ,selected');
		   $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
			.removeClass('start active ,selected');

			$("#zone").val("").trigger("change");
			$("#rule").val("").trigger("change");
			$("#reportFromDate").val('');
			$("#reportToDate").val('');
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
			    		html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
								html += "<select id='sdoCode' name='sdoCode'  onchange='showTownsBySubDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
								for (var i = 0; i < response1.length; i++) {
									html += "<option  value='"+response1[i]+"'>"
											+ response1[i] + "</option>";
								}
								html += "</select><span></span>";
								$("#subdivTd").html(html);
								$('#sdoCode').select2();
							}
						});
			}

		 function showTownsBySubDiv(subdivIdName)
			{
			    var zone = $('#zone').val();
				var circle = $('#circle').val();
			    var division = $('#division').val();
				var subdivision=subdivIdName;
				
				$.ajax({
					type : 'GET',
					
					url : "./getTownsBaseOnSubdivision",
					data:{
						zone : zone,
						circle : circle,
						division : division,
						subdivision : subdivision
						},
					async : false,
					cache : false,
					success : function(response)
					{
						var html="";
						if(response!=null)
							{
							html+="<option value=''>Select Town</option><option value='%'>ALL</option>"; 
							for(var i=0;i<response.length;i++)
								{
								html+="<option value='"+response[i][0]+"'>"+ response[i][0]+"-" +response[i][1]+"</option>"; 
							
							$("#town").empty();
							$("#town").append(html);
							$("#town").select2();
							
							}
							}
						
					}
				});
			}

		 function showTownNameandCode(circle){
				var zone =  $('#zone').val(); 
				//$('#town').find('option').remove();
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
				  		success : function(response) {
				  		   $('#town').append($('<option>', {
			 					value : '%',
			 					text : 'ALL',
			 				}));
			              for (var i = 0; i < response.length; i++) {
			                 
			                  $('#town').append($('<option>', {
			  					value : response[i][0],
			  					text : response[i][0] + "-" + response[i][1],
			  				}));
			              }
			            }
				  	});
				  }
		  	
	 
	 function getestimatedrule(){
		 
		// alert("in rules");
		 $.ajax({
				url : './getestimatedrules',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response1) {
					//alert("subdiv  "+response1);
					var html = '';
					html += "<select id='rule' name='rule'  class='form-control input-medium' type='text'><option value=''>Select Rule</option>";
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
	
</script> 
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Data Estimation Report
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
								<th class="block">Region&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
									<option value="">Select Region</option>
										<option value="%">ALL</option>
											<c:forEach items="${zoneList}" var="zonename">
												<option value="${zonename}">${zonename}</option>
											</c:forEach>
								</select></th>
								<th class="block">Select Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showTownNameandCode(this.value);">
									
									<!-- <option id="getCircles" value=""></option>
										<option value=""></option> -->
								</select></th>
								
								<th class="block">Select Town&nbsp;:</th>
								<th id="townId"><select
									class="form-control select2me input-medium" id="town"
									name="town" >
									<option value="">Select Town</option>
									</select></th>
									
								<!-- <th class="block">Select Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value);">
									<option value="">Select Division</option>

								</select></th> --></tr>
							<tr>
								<th colspan="8"></th>
							</tr> 
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<!-- <th class="block">Select  Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode" >
									<option value="">Select Sub-Division</option>
									</select></th> -->
								
								
									
								<th class="block">Estimation&nbsp;Rule&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="rule"
									name="rule"> <option value="">Select Rule</option>
											 <c:forEach items="${rules}" var="estrules">
												<option value="${estrules.eruleid}">${estrules.erulename}</option>
											</c:forEach> 
									</select></th>
									
									<th class="block">From Date:</th>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" style="cursor:pointer" >
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							
							<th class="block">To Date:</th>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportToDate" id="reportToDate"  style="cursor: pointer">
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
									
								
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
							
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
<!-- 								<li><a href="#" id="print" onclick="exportPDF('sample','Data Estimation Report')">Print</a></li>
 -->								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample', 'DataEstimationReport')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
	
					<table class="table table-striped table-hover table-bordered" id="sample">
						<thead>
							<tr>
							<th>Sl No</th>
							<th>Rule Id</th>
							<th>Rule name</th>
							<th>Estimation rule  date</th>
							<th>Subdivision</th>
							<th>Town Code</th>
							<th>Location Type </th>
							<!-- <th>Location Identity</th> -->
							<th>Location Name</th>
							<th>Meter No</th>
							<th>Estimated Period</th>
							<th>Estimated Date</th>
							<th>Data Type of Parameter </th>
							<th>Parameter Name</th>
							<th>Kwh Value</th>
							<th>Estimated Kwh </th>
							<th>kvah Value </th>
							<th>Estimated kvah</th>
							<th>kw Value </th>
							<th>Estimated kw </th>
							<th>kva Value</th>
							<th>Estimated kva </th>
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
	var town = $('#town').val();
	var ruleId = $('#rule').val();
	var fromDate = $('#reportFromDate').val();
	var toDate = $('#reportToDate').val();
	//$('#updateMaster').empty();
	//$('#sample').dataTable().fnClearTable();
	
	var date1 = new Date(fromDate);
    var date2 = new Date(toDate);
    var timeDiff = Math.abs(date2.getTime() - date1.getTime());
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	
	if(zone=="")
	{
	bootbox.alert("Please Select Region");
	return false;
	}
	if(circle=="")
	{
	bootbox.alert("Please Select Circle");
	return false;
	}
	
	if(town=="")
	{
	bootbox.alert("Please Select Town");
	return false;
	}
	if(ruleId=="")
	{
	bootbox.alert("Please Select Estimation Rule");
	return false;
	}
	if(fromDate=="")
	{
	bootbox.alert("Please Select From Date");
	return false;
	}
	if(toDate=="")
	{
	bootbox.alert("Please Select To Date");
	return false;
	}

	if(new Date(fromDate)>new Date(toDate))
	{
		bootbox.alert("Please Select Correct Date Range");
		return false;
	}

	if(diffDays>30)
    {
    	bootbox.alert('Date Difference should not be more than 30');
    	return false;
    }
    
	$('#imageee').show();
	$.ajax({
		url : './getestimatedReport',
		type : 'POST',
		data : {
			zone:zone,
			circle: circle,
			ruleId : ruleId,
			fromDate : fromDate,
			toDate : toDate,
			town : town
			
			
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			$("#updateMaster").html('');
			$('#sample').dataTable().fnClearTable();
			
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr><td>"+(i+1)+"</td>" 
					+"<td>"+ element[1]+"</td>"
					+ "<td>"+ element[2]+ "</td>"
					+ "<td>"+  moment(element[4]).format('DD-MM-YYYY hh:mm:ss')+ "</td>"
					+ "<td>"+ element[8]+ "</td>"
					+ "<td>"+ element[9]+ "</td>"
					+ "<td>"+ element[10]+ "</td>"
					 + "<td>"+ element[12]+ "</td>" 
					+ "<td>"+ element[3]+ "</td>"
					+"<td>"+ element[13]+" To "+element[14] +"</td>"
					+ "<td>"+ moment(element[15]).format('DD-MM-YYYY hh:mm:ss')+ "</td>"
					+"<td>"+ (element[16]== null ? "": (element[16])) +"</td>"
					+"<td>"+ (element[17]== null ? "": (element[17])) +"</td>"
					+"<td>"+ (element[18]== null ? "": (element[18])) +"</td>"
					+"<td>"+ (element[19]== null ? "": (element[19])) +"</td>"
					+"<td>"+ (element[20]== null ? "": (element[20]))+"</td>"
					+"<td>"+ (element[21]== null ? "": (element[21])) +"</td>"
					+"<td>"+ (element[22]== null ? "": (element[22])) +"</td>"
					+"<td>"+ (element[23]== null ? "": (element[23])) +"</td>"
					+"<td>"+  (element[24]== null ? "": (element[24])) +"</td>"
					+"<td>"+ (element[25]== null ? "": (element[25]))+"</td>";
				}
				$('#sample').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample');
				
			} else {
				bootbox.alert("No relative data found");
			}

		}
	});

}
</script>




