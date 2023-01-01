<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>SAIFI / SAIDI (DT)
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body">
		
			<jsp:include page="locationFilter.jsp"/> 
		
			<div class="modal-body">

				<div class="row">
					<div class="form-group col-md-3">
						<div class="form-group">
						<Strong>Select Report Type</Strong>
							<select class="form-control select2me" 
								name="townfeeder" id="townfeeder" onchange="showFeederDiv(this.value);">
									<option value="">Select Town/Feeder</option>
									<option value="town">Town Wise</option>
									<option value="DT">DT Wise</option>
									
							</select>
						</div>
					</div>
					
					<!-- <div class="col-md-3">
						<div class="form-group">
							<Strong>Select Monthly /Yearly <font color="red">*</font></Strong> <select
								class="form-control select2me" id="reportRange" name="reportRange" onchange="showMonthYear(this.value)">
								<option value="month">Monthly</option>
							
							</select>
						</div>
					</div> -->
				
					<div class="col-md-3" id="monthlyDiv">
						<Strong>Select Month Year  </Strong>
						<div class="input-group ">
							<input type="text" class="form-control from" id="MonthYearId"
								name="MonthYearId" placeholder="Select Month Year"
								style="cursor: pointer"> <span class="input-group-btn">
								<button class="btn default" type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-md-3" id="yearlyDiv" hidden="true">
						<Strong>Select Year  </Strong>
						<div class="input-group ">
							<input type="text" class="form-control yearfrom" id="yearid"
								name="yearid" placeholder="Select Year"
								style="cursor: pointer"> <span class="input-group-btn">
								<button class="btn default" type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-md-3" id="feederDiv" hidden="true">
						<div  class="form-group">
							<Strong>Select DT <font color="red">*</font></Strong> <select
								class="form-control select2me" id="feeder"
								name="feeder">
								<option value="">Select DT</option>
							</select>
						</div>
					</div>
					
					<div class="col-md-3">
						<button type="button" id="viewAreaId" style="margin-top: 25px;"
							name="viewAreaId" class="btn green" onclick="viewArea()">
							<b>View</b>
						</button>
					
					</div>



				</div>
				</div>
				
		<%-- 			<div class="form-group col-md-3">
					<label>Select Region</label>
					<select class="form-control select2me" name="zoneNew" id="zoneNew"
						onchange="showCircleNew(this.value);">
						<option value="">Select Region</option>
						<!-- <option value='%'>ALL</option> -->
						<c:forEach var="elements" items="${zoneList}">
							<option value="${elements}">${elements}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-3">
					<label>Select Circle</label>
					<select class="form-control select2me" id="circleNew"
						name="circleNew" onchange="getTowns(this.value)">
						<option value="">Circle</option>
						<!-- <option value='%'>ALL</option> -->
						<c:forEach items="${circleList}" var="elements">
							<option value="${elements}">${elements}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-3">
					<label>Select Town</label>
					<select class="form-control select2me" id="town" name="town" onchange="getFeederByTown(this.value)">
						<option value="">Town</option>
						<!-- <option value='%'>ALL</option> -->
					</select>
				</div> --%>
				<br>
				
			
				<br>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					<table class="table table-striped table-hover table-bordered"
						id="avg_tab" hidden="true">
						<thead>
							<tr>
								<th>Quality Index</th>
								<th>Year</th>
								<th>Minimum</th>
								<th>Maximim</th>
								<th>Average</th>
							</tr>
						</thead>
						<tbody id="avg_tab_body">
	
						</tbody>
					</table>
				<br>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
				</div>
				<br>
				
				<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
							onclick="exportPDF('sample_3','ReliabilityIndicesDTSummary')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Consumption')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
				<table class="table table-striped table-hover table-bordered"
					id="sample_3">
					<thead>
						<tr>
							
							<th> Meter Number</th>
							<th> DT Name</th>
							<th> DT Code</th>
							
							<th>Start Period </th>
							<th>End Period </th>
						
							<th>Total Interruptions Count</th>
							<th>Total Interruptions Duration(sec)</th>
							
							<th>HT Industrial Consumer Count</th>
							<th>HT Commercial Consumer Count</th>
							<th>LT Industrial Consumer Count</th>
							<th>LT Commericail Consumer Count</th>
							<th>LT Domestic Consumer Count</th>
						
							<th>GOVT Consumer Count</th>
							<th>AGRI Consumer Count</th>
							<th>OTHERS Consumer Count</th>
						
							<th>Total Consumers Count</th>
							<th>SAIFI</th>
							<th>SAIDI</th>
				
						
						</tr>
					</thead>
					<tbody id="AreafeederLocationdata">

					</tbody>
				</table>

			</div>
		</div>
	</div>
</div>


<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<h5 class="modal-title" id="">
					<b>Enter/Update Consumption</b>
				</h5>
			</div>
			<div class="modal-body">
					
					<input type="hidden" id="oldConsumptionId" name="oldConsumptionId" /> 
					<input type="hidden" id="monthlyConsId" name="monthlyConsId" /> 
					<input type="hidden" id="mtrnoId" name="meternoId" /> 
					<input type="hidden" id="locodeId" name="meternoId" /> 

					<div class="inline-labels">
						<div class="form-group">
							<label>Consumption(kWh) With MF: 
								<input type="text" class="form-control placeholder-no-fix"
								id="ConsumptionId" name="ConsumptionId"
								placeholder="Enter Consumption"></input></label>
						</div>
						<div class="form-group">
							<label for="comment">Edit Remark:-</label>
							<textarea class="form-control" rows="5" id="EdittextId"
								name="EdittextId"></textarea>
							<input type="text" id="EditRemarkId" hidden="true">
						</div>
						<div class="form-group" hidden="true">
							<label for="comment"></label>
							<textarea class="form-control" rows="5" id="" name=""></textarea>
							<input type="text" id="MeterNo" hidden="true">
						</div>




					</div>



					<div class="modal-footer">
						<button class="btn blue pull-right" id="Updatedtbt" type="button"
							value="update" onclick="return update()">UPDATE</button>
						<button class="btn red pull-left" type="button"
							data-dismiss="modal">Cancel</button>
					</div>

				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();

						$('#dtWiseReport,#reliabilityIndicesDT')
						.addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
						.removeClass('start active ,selected');
						
						
						loadSearchAndFilter('sample_3');
						App.init();

					});
</script>
<script>
function showCircle(zone) {
	//alert("hiiiii...")
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
						html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircle").html(html);
						$('#LFcircle').select2();
					}
				});
	} 
/* function showCircle(zone) {
	$('#LFcircle').find('option').remove();
	$
			.ajax({
				url : './showCircle' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {

					$('#LFcircle').append($('<option>', {
						value : "",
						text : "Circle"
					}));
				 $('#LFcircle').append($('<option>', {
						value : "%",
						text : "ALL"
					})); 
					
					for (var i = 0; i < response.length; i++) {
						$('#LFcircle').append($('<option>', {
							value : response[i],
							text : response[i]
						}));		
					}
					
				}
			});
} */

function showTownNameandCode(circle){
	var zone=$("#LFzone").val();
	$('#LFtown').find('option').remove();
	$.ajax({
		url : './showTownByCircle',
		type : 'GET',
		data : {
			zone : zone,
			circle : circle
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {

			$('#LFtown').append($('<option>', {
				value : "",
				text : "Town"
			}));
			 $('#LFtown').append($('<option>', {
				value : "%",
				text : "ALL"
			})); 
			
			for (var i = 0; i < response.length; i++) {
				$('#LFtown').append($('<option>', {
					value : response[i][1],
					text : response[i][1]+" - "+response[i][0]
				}));		
			}
			
		}
	});
	
	
}


function showResultsbasedOntownCode(town){
	
	var townfeeder=$("#townfeeder").val();
	$('#feeder').find('option').remove();
	if(townfeeder=='DT'){
		$.ajax({
			url : './getDTMetersByTown',
			type : 'POST',
			data : {
				town : town,
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#feeder').append($('<option>', {
					value : "",
					text : "Select Feeder"
				}));
				 $('#town').append($('<option>', {
					value : "%",
					text : "ALL"
				})); 
				
				for (var i = 0; i < response.length; i++) {
					$('#feeder').append($('<option>', {
						value : response[i][0],
						text : response[i][0]+" - "+response[i][1]
					}));		
				}
				
			}
		});


	}
	
}

	function viewArea() {
		$('#sample_3').dataTable().fnClearTable();
			
		var townfeeder = $("#townfeeder").val();
		var reportRange = $("#reportRange").val();
		var yearid = $("#yearid").val();
		
		var zoneNew = $("#LFzone").val();
		var circleNew = $("#LFcircle").val();
		var town = $("#LFtown").val();
		var feeder = $("#feeder").val();
		var monthyr = $("#MonthYearId").val();
		var locationtype = $("#locationtypeId").val();
		var Consumavail = $("#ConsumavailId").val();

		
		if(zoneNew==null || zoneNew==""){
			bootbox.alert("Please Select Region");
			return;
		}

		if(circleNew==null || circleNew==""){
			bootbox.alert("Please Select Circle");
			return;
		}


		if(town==null || town==""){
			bootbox.alert("Please Select Town");
			return;
		}

		if(townfeeder==null || townfeeder==""){
			bootbox.alert("Please Select the Report Type (Town/Feeder Wise).");
			return;
		}
		
	
		
		
		if(townfeeder=="DT"){
			if(feeder==null || feeder==""){
				bootbox.alert("Please Select DT.");
				return;
			}
		}
		
		if(reportRange=="month"){
			if(monthyr==null || monthyr==""){
				bootbox.alert("Please Select Month Year");
				return;
			}
		} else if(reportRange=="year"){
			if(yearid==null || yearid==""){
				bootbox.alert("Please Select Year");
				return;
			}
		}
		var all_saifi=[];
		var all_saidi=[];
		$('#imageee').show();
		//$('#imageee').show();
		//debugger;
		$.ajax({
	    	url : './getSaidiSaifiReportDT',
	    	type:'POST',
	    	dataType:'json',
	    	data : {
	    		townfeeder : townfeeder,
	    		reportRange : reportRange,
	    		zone : zoneNew,
				circle : circleNew,
				town : town,
	    		monthyr : monthyr,
	    		dt : feeder,
	    		yearid : yearid,
		    },
	    	asynch : false,
	    	cache : false,
	    	success : function(data)
	    	{
	    		if (data == null){
	    			bootbox.alert("Something went wrong.");
					return;
	    		}
	    		
	    		if (data.length == 0){
	    			bootbox.alert("No results found for the selected inputs.");
					return;
	    		}
	    		$('#imageee').hide();
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					var name="";
					var myVal="";
					if(townfeeder=="DT"){
						name=$("#feeder option:selected").text();
					} else {
						name=resp[0];
					}

					all_saifi.push(parseFloat(resp[4]));
					all_saidi.push(parseFloat(resp[5]));
					
				
					html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[17]+"</td>"
						+" <td>"+resp[18]+"</td>"
						
						+" <td>"+resp[03]+"</td>"
						+" <td>"+resp[04]+"</td>"
						
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" <td>"+resp[13]+"</td>"
						+" <td>"+resp[14]+"</td>"
						+" <td>"+resp[16]+"</td>"
						+" <td>"+resp[19]+"</td>"
						+" <td>"+resp[20]+"</td>";
					
					
					
					
					/* html+="<tr>"
							+" <td>"+name+"</td>"
							+" <td>"+resp[6]+"</td>"
							+" <td>"+resp[1]+"</td>"
							+" <td>"+resp[2]+"</td>"
							+" <td>"+resp[3]+"</td>"
							+" <td>"+(parseFloat(resp[4]).toFixed(3))+"</td>"
							+" <td>"+(parseFloat(resp[5]).toFixed(3))+"</td>"; */
							
							html+=" </tr>";
		   		}
	   		 
	   			$('#sample_3').dataTable().fnClearTable();
	   			$('#AreafeederLocationdata').html(html);
	   		 
	    	},
			complete: function()
			{  
				loadSearchAndFilter1('sample_3');

				if(reportRange=="year"){
					var max_saifi=Math.max.apply(Math,all_saifi); // 3
					var min_saifi=Math.min.apply(Math,all_saifi); // 1
	
					var avg_saifi=arrSum(all_saifi)/all_saifi.length;
					
					//alert(avg_saifi);
					var max_saidi=Math.max.apply(Math,all_saidi); // 3
					var min_saidi=Math.min.apply(Math,all_saidi); // 1
	
					var avg_saidi=arrSum(all_saidi)/all_saidi.length;
					//alert(avg_saifi +" --- "+avg_saidi);

					var html="<tr>"
							+"<td>SAIFI</td>"	
							+"<td>"+yearid+"</td>"	
							+"<td>"+(min_saifi).toFixed(3)+"</td>"	
							+"<td>"+max_saifi.toFixed(3)+"</td>"	
							+"<td>"+avg_saifi.toFixed(3)+"</td>"	
							+"</tr>"
							+"<td>SAIDI</td>"	
							+"<td>"+yearid+"</td>"	
							+"<td>"+min_saidi.toFixed(3)+"</td>"	
							+"<td>"+max_saidi.toFixed(3)+"</td>"	
							+"<td>"+avg_saidi.toFixed(3)+"</td>"	
							+"<tr>"
							+"</tr>";

					$('#avg_tab').show();
					$('#avg_tab').dataTable().fnClearTable();
		   			$('#avg_tab_body').html(html);
		   			loadSearchAndFilter1('avg_tab');
		   			$('#imageee').hide();
				} else{
					$('#avg_tab').hide();
					$('#imageee').hide();
				}
				$('#imageee').hide();
					
				
				
				
			}  
		  }); 
		
		
		
	}

	const arrSum = function(arr){
		  return arr.reduce(function(a,b){
			    return a + b
			  }, 0);
			};
	
</script>

<script>

function downloadReport() {

	var townfeeder = $("#townfeeder").val();
	var town = $("#LFtown").val();
	var feeder = $("#feeder").val();
	var monthyr = $("#MonthYearId").val();
	
	


	if(townfeeder==null || townfeeder==""){
		bootbox.alert("Please Select the Report Type (Town/Feeder Wise).");
		return;
	}
	if(town==null || town==""){
		bootbox.alert("Please Select Town");
		return;
	}
	
	if(townfeeder=="feeder"){
		if(feeder==null || feeder==""){
			bootbox.alert("Please Select Feeder.");
			return;
		}
	}
	
	if(reportRange=="month"){
		if(monthyr==null || monthyr==""){
			bootbox.alert("Please Select Month Year");
			return;
		}
	}
	window.open("./exportjsonSaifiSaidiFeeder?monthyr=" + monthyr + "&townfeeder=" + townfeeder + "&town=" + town + "&feeder=" + feeder);

}


	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}

	function exportPDF()
	{
		// alert("inside pdf function");
		var townfeeder = $("#townfeeder").val();
		var reportRange = $("#reportRange").val();
		var yearid = $("#yearid").val();
		
		var zoneNew = $("#LFzone").val();
		var circleNew = $("#LFcircle").val();
		var town = $("#LFtown").val();
		var feeder = $("#feeder").val();
		
		var monthyr = $("#MonthYearId").val();
		var locationtype = $("#locationtypeId").val();
		var Consumavail = $("#ConsumavailId").val();
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		var feedername = $("#feeder option:selected").map(function(){return this.text}).get().join(',');
		var zne="",cir="",twn="",townname1="";
		if(zoneNew == "%")
	{
		zne = "ALL";
	}else{
		zne = zoneNew;
	}
		
	if(circleNew == "%")
	{
		cir = "ALL";
	}else{
		cir = circleNew;
	}
	//alert("hii cir"+cir)
	if(town == "%")
	{
		twn = "ALL";
	}else{
		twn  = town;
	}
	if(townname=="%"){
		townname1="ALL";
	}else{
		townname1=townname;
	}

		
	 
		window.location.href= ("./ReliabilityIndicesDTSummary?townfeeder="+ townfeeder +"&twn="+twn+"&zne="+zne+"&cir="+cir+"&monthyr="+ monthyr + "&feeder="+feeder+"&townname1="+townname1+"&feedername="+feedername);					
	}




	
	/*  function locationWise(param)
	{
	  
	  if(param=='location')
		  {
		  $('#tab_1_1').show();
		  $('#tab_1_2').hide();
		  
		  }
	  if(param=='area')
	  {
	  $('#tab_1_1').hide();
	  $('#tab_1_2').show();
	  
	  } 
	  
	}   */
</script>

<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});
	
	$('.yearfrom').datepicker
	({
	    format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});


	
	function showMonthYear(val){
		//alert(val);
		if(val=='month'){
			$("#monthlyDiv").show();
			$("#yearlyDiv").hide();
			$("#monthYearLevel").text("Month");
		} else if(val=='year'){
			$("#monthlyDiv").hide();
			$("#yearlyDiv").show();
			$("#monthYearLevel").text("Year");
		}
		
	}
	function showFeederDiv(val){
		if(val=='town'){
			$("#feederDiv").hide();
			$("#townFeederLevel").text("Town");
		} else if(val=='DT'){
			$("#feederDiv").show();
			$("#townFeederLevel").text("DT");
			var town = $("#LFtown").val();
			showResultsbasedOntownCode(town);
			
			
		}
		
	}

	
</script>