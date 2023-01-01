<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						$("#month").val('');
						
						loadSearchAndFilter('sample_1');
						$('#reportsId,#feederOutage').addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});

	$("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	});
</script>


<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Feeder Outage Report
			</div>
		</div>


		<div class="portlet-body">
		
			<%-- <div class="row" style="margin-left: -1px;">
				<div class="col-md-3">
					<div id="circleTd" class="form-group">
						<label class="control-label">Circle:</label> <select
							class="form-control select2me" id="circle" name="circle"
							onchange="showTown(this.value);">
							<option value="">Select Circle</option>
							<option value="%">ALL</option>
							<c:forEach items="${circleList}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<div id="townId" class="form-group">
						<label class="control-label">Town:</label> <select
							class="form-control select2me" id="town" name="town">
							<option value="">Select Town</option>
							<option value="%">ALL</option>
							<c:forEach items="${townList}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
						</select>
					</div>

				</div>
				</div> --%>
	       <div class="row" style="margin-left: -1px;">
	       	<jsp:include page="locationFilter.jsp"/> 
				<div class="col-md-3">
					<div id="feederDivId" class="form-group">
						<label class="control-label"><b>Feeder:</b></label> <select
							class="form-control select2me input-medium" id="feederTpId"
							name="feederTpId">
							<option value="">Select Feeder</option>
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<div class="input-group ">
						<label class="control-label"><b>Month:</b></label><input
							type="text" class="form-control from" id="month" name="month"
							required="required" placeholder="Select Month"> <span
							class="input-group-btn">
							<button class="btn default" type="button"
								style="margin-bottom: -23px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div>
				</div>
				<div class="row" style="margin-left: -1px;">
				<button type="button" id="feederOutageRrt" style="margin-left: 50px;border-top-width: 2px;margin-bottom: -63px;"onclick="return getfeederOutageReport()" name="feederOutageRrt" class="btn green">
					<b>View</b>
				</button>
			</div>
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
						 <li><a href="#" id="" onclick="exportPDF('sample_1','FeederOutageRpt')">Export to PDF</a></li>
						<li><a href="#" id="excelExport" onclick="tableToXlxs('sample_1', 'FeederOutageRpt')">Export to Excel</a></li>
					</ul>
				</div>
			</div>
			<table class="table table-striped table-hover table-bordered" id="sample_1">
				<thead>
					<tr>
						<th>Sl.No</th>
						<th>Month Year</th>
						<th>Circle</th>
						<th>Town</th>
						<th>Feeder Code</th>
						<th>Feeder Name</th>
						<th>Meter No</th>
						<th style='word-wrap: break-word;'>Total No Of Occurrence</th>
						<th style='word-wrap: break-word;'>Total Power Failure Duration</th>
					</tr>
				</thead>
				<tbody id="sample1">

				</tbody>
			</table>
		</div>
				</div>
		<div id="stack2" class="modal fade" role="dialog"
					aria-labelledby="myModalLabel10" aria-hidden="true">
					<div class="modal-dialog" style="width: 85%;">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h4 class="modal-title" style="color: white;">Feeder Outage Details </h4>
							</div>
						
							<div class="modal-body">
							
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<!-- <li><a href="#" id="" onclick="exportPDF('sample_2','FeederOutageMnthRpt')">Export to PDF</a></li> -->
											<li><a href="#" id="excelExport" onclick="tableToXlxs('sample_2', 'FeederOutageMnthRpt')">Export to Excel</a></li>
										</ul>
									</div>
								</div>
								<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
											<th>Sl.No</th>
											<th>Circle</th>
											<th>Town</th>
											<th>Feeder Code</th>
											<th>Feeder Name</th>
											<th>Meter No</th>
											<th>Date</th>
											<th style='word-wrap: break-word;'>Total No Of Occurrence</th>
											<th style='word-wrap: break-word;'>Total Power Failure Duration</th>
											
											
										</tr>
									</thead>
									<tbody id="sample2">
									</tbody>
								</table>	
							</div>	
							
							<div class="modal-footer">
                          		<button class="btn red pull-right" data-dismiss="modal" class="btn">Close</button>
                            </div>
								
						</div>
					</div>
				</div>			
<!-- new implementation  -->
<div id="stack3" class="modal fade" role="dialog"
					aria-labelledby="myModalLabel10" aria-hidden="true">
					<div class="modal-dialog" style="width: 85%;">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h4 class="modal-title" style="color: white;">Feeder Outage Duration  Details </h4>
							</div>
						
							<div class="modal-body">
							
								<div class="table-toolbar">
									<div class="btn-group pull-right" style="margin-top: 15px;">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<!-- <li><a href="#" id="" onclick="exportPDF('sample_3','FeederOutageMnthRpt')">Export to PDF</a></li> -->
											<li><a href="#" id="excelExport" onclick="tableToXlxs('sample_3', 'FeederOutageMnthRpt')">Export to Excel</a></li>
										</ul>
									</div>
								</div>
								<table class="table table-striped table-hover table-bordered"
									id="sample_3">
									<thead>
										<tr>
											<th>Sl.No</th>
											
											<th>Feeder Code</th>
											<th>Feeder Name</th>
											<th>Meter No</th>
											<th style='word-wrap: break-word;'> Occurrence Time </th>
											<th style='word-wrap: break-word;'>Restoration Time </th>
											<th>Duration (in min.)</th>
										</tr>
									</thead>
									<tbody id="sample3">
									</tbody>
								</table>	
							</div>	
							
							<div class="modal-footer">
                          		<button class="btn red pull-right" data-dismiss="modal" class="btn">Close</button>
                            </div>
								
						</div>
					</div>
				</div>			
		
		
	</div>
<script>

function showTown(circle) {
	$
			.ajax({
				url : './showTownByCircle',
				type : 'GET',
				data : {
					circle : circle
				},
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<label class='control-label'>Town:</label><select id='town' name='town' onchange='showResultsbasedOntownCode(this.value)' class='form-control' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";

					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html += "<option  value='"+resp[1]+"'>" + resp[1]+ "-" + resp[0] + "</option>";
					}
					html += "</select><span></span>";
					$("#townId").html(html);
					$('#town').select2();
				}
			});
}

function showResultsbasedOntownCode(townCode) {
	var circle=$('#LFcircle').val();
	var circleNameCodeArr =[];
	if(circle !="%"){
		circleNameCodeArr = circle.split('-');
		circle=circleNameCodeArr[0];
		}
	//alert(circle);
	$('#feederTpId').val('').trigger('change');
	$('#feederTpId').empty();
	$('#feederTpId').find('option').remove();
	$('#feederTpId').append($('<option>', {
		value : "",
		text : "Select Feeder"
	}));
	

	$.ajax({
		url : './getFeederByTown',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			townCode : townCode,
			circle:circle
		},
		success : function(response1) {
			var html = '';
			if(response1.length>1){
				$('#feederTpId').append($('<option>', {
					value : "%",
					text : "ALL"
				}));
			}
			for (var i = 0; i < response1.length; i++) {
				var resp = response1[i];
				$('#feederTpId').append($('<option>', {
					value : resp[0],
					text : resp[1]
				}));
			}
			$('#feederTpId').select2();

		}
	});
}

</script>

<script>

	function getfeederOutageReport(){
		var zone=$('#LFzone').val();
		var circle =$("#LFcircle").val();
		var feeder = $('#feederTpId').val();
		var town = $('#LFtown').val();
		var month = $('#month').val();
		//alert(circle);
		//alert(town);
		if(zone=='' || zone==null){
			bootbox.alert("Please Select Region  ");
			return false;
		}		
		if(circle=='' || circle==null){
			bootbox.alert("Please Select Circle");
			return false;
		}		
		if(town=='' || town==null){
			bootbox.alert("Please Select Town");
			return false;
		} 
		if(feeder=='' || feeder==null){
			bootbox.alert("Please Select Feeder");
			return false;
		}
		if(month=='' || month==null){
			bootbox.alert("Please Select Month");
			return false;
		}
		var circleNameCodeArr =[];
		if(circle !="%"){
		circleNameCodeArr = circle.split('-');
		circle=circleNameCodeArr[1];
		}
        $('#imageee').show();
        $.ajax({
			url : './getfeederOutageReport',
			type : 'POST',
			data : {
				circle : circle,
				town : town,
				feeder : feeder,
				month : month
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				$("#sample1").html('');
				$('#sample_1').dataTable().fnClearTable();
				if (response != null && response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html += "<tr>" 
							+ "<td style='text-align: center;'>"+ (i+1) + "</td>"
							+ "<td style='text-align: center;'>"+ ((resp[0]==null)?"":resp[0]) + " </td>"
							+ "<td style='text-align: center;'>"+ ((resp[1]==null)?"":resp[1]) + " </td>"
							+ "<td style='text-align: center;'>"+ ((resp[2]==null)?"":resp[2]) + " </td>"
							+ "<td style='text-align: center;'>"+ ((resp[3]==null)?"":resp[3]) + " </td>"
							+ "<td style='text-align: center;'>"+ ((resp[4]==null)?"":resp[4]) + " </td>"
							+ "<td style='text-align: center;'>"+ ((resp[5]==null)?"":resp[5]) + " </td>"
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' style='padding: 4px;' data-target='#stack2' id='viewMonthDetails' onclick='return viewMonthWiseFeederDetails(\""+resp[0]+"\",\""+resp[3]+"\",\""+resp[5]+"\" )'>"+ ((resp[6]==null)?"":resp[6]) + "</a></td>"
							+ "<td style='text-align: center;'>"+ ((resp[7]==null)?"":resp[7]) + " </td>"
							"</tr>"; 
					}				
					$("#sample1").html(html);
					loadSearchAndFilter('sample_1');
				} else {
					$('#imageee').hide();
					bootbox.alert("No Data Found for this Criteria.");
					loadSearchAndFilter('sample_1');
				}
			},
			error: function (error) {
				$('#imageee').hide();
				$("#sample1").html('');
				$('#sample_1').dataTable().fnClearTable();
				bootbox.alert("Some Error Occured.");
				loadSearchAndFilter('sample_1');
			}
		});

	}

	function viewMonthWiseFeederDetails(month,feedercode,meterno){
     
     // alert(meterno);
		  $('#imageee').show();
	        $.ajax({
				url : './getMonthWisefeederOutageReport',
				type : 'POST',
				data : {
					feeder : feedercode,
					month : month,
					meterno:meterno
				},
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					$('#imageee').hide();
					$("#sample2").html('');
					$('#sample_2').dataTable().fnClearTable();
					if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							html += "<tr>" 
								+ "<td style='text-align: center;'>"+ (i+1) + "</td>"
								+ "<td style='text-align: center;'>"+ ((resp[0]==null)?"":resp[0]) + " </td>"
								+ "<td style='text-align: center;'>"+ ((resp[1]==null)?"":resp[1]) + " </td>"
								+ "<td style='text-align: center;'>"+ ((resp[2]==null)?"":resp[2]) + " </td>"
								+ "<td style='text-align: center;'>"+ ((resp[3]==null)?"":resp[3]) + " </td>"
								+ "<td style='text-align: center;'>"+ ((resp[4]==null)?"":resp[4]) + " </td>"
								+ "<td style='text-align: center;'>"+ ((resp[5]==null)?"":resp[5]) + " </td>"
								+ "<td style='text-align: center;'><a href='#' data-toggle='modal' style='padding: 4px;' data-target='#stack3' id='viewMonthDurationDetails' onclick='return viewMonthDurationWiseFeederDetails(\""+resp[2]+"\",\""+resp[4]+"\",\""+resp[5]+"\" )'>"+ ((resp[6]==null)?"":resp[6]) + "</a></td>"
								+ "<td style='text-align: center;'>"+ ((resp[7]==null)?"":resp[7]) + " </td>"
								"</tr>"; 
						}		
						$("#sample2").html(html);
						loadSearchAndFilter('sample_2');
					} else {
						$('#imageee').hide();
						bootbox.alert("No Data Found for this Criteria.");
						loadSearchAndFilter('sample_2');
					}
				},
				error: function (error) {
					$('#imageee').hide();
					bootbox.alert("Some Error Occured.");
					loadSearchAndFilter('sample_2');
				}
			});


	}



	function viewMonthDurationWiseFeederDetails(feedercode,meterno,month){
	     
	     // alert(meterno);
			  $('#imageee').show();
		        $.ajax({
					url : './getMonthDurationWisefeederOutageReport',
					type : 'POST',
					data : {
						feeder : feedercode,
						month : month,
						meterno:meterno
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						$('#imageee').hide();
						$("#sample3").html('');
						$('#sample_3').dataTable().fnClearTable();
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>" 
									+ "<td style='text-align: center;'>"+ (i+1) + "</td>"
									+ "<td style='text-align: center;'>"+ ((resp[0]==null)?"":resp[0]) + " </td>"
									+ "<td style='text-align: center;'>"+ ((resp[1]==null)?"":resp[1]) + " </td>"
									+ "<td style='text-align: center;'>"+ ((resp[2]==null)?"":resp[2]) + " </td>"
									+ "<td style='text-align: center;'>"+ (resp[3] == null ? "" : moment(resp[3]).format('DD-MM-YYYY hh:mm:ss')) + " </td>"
									+ "<td style='text-align: center;'>"+ (resp[4] == null ? "" : moment(resp[4]).format('DD-MM-YYYY hh:mm:ss')) + " </td>"
									+ "<td style='text-align: center;'>"+ ((resp[5]==null)?"":resp[5]) + " </td>"
									
									"</tr>"; 

									
							}		
							$("#sample3").html(html);
							loadSearchAndFilter('sample_3');
						} else {
							$('#imageee').hide();
							bootbox.alert("No Data Found for this Criteria.");
							loadSearchAndFilter('sample_3');
						}
					},
					error: function (error) {
						$('#imageee').hide();
						bootbox.alert("Some Error Occured.");
						loadSearchAndFilter('sample_3');
					}
				});


		}
		

	function showCircle(zone) {
		$
				.ajax({
					url : './getCircleByRegionForFeederOutage',
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
							html += "<option value='"+response[i][0]+"-"+response[i][1]+"'>"
									+ response[i][0] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircle").html(html);
						$('#LFcircle').select2();
					}
				});
	} 


	function showTownNameandCode(circle){
		var zone = $('#LFzone').val();
		var circleNameCodeArr =[];
		if(circle !="%"){
			circleNameCodeArr = circle.split('-');
			circle=circleNameCodeArr[0];
			}
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

	                html += "<select id='LFtown' name='LFtown'  onchange='showResultsbasedOntownCode(this.value)'class='form-control  input-medium'  type='text'><option value=''>Select town </option><option value='%'>ALL</option>";

	               
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

<script>
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	
	$('.from').datepicker({
		format : "yyyymm",
		minViewMode : 1,
		autoclose : true,
		startDate : new Date(new Date().getFullYear(), '-55'),
		endDate : new Date(year, month - 1, '31')
	});



	function exportPDF()
	{ 

		var zone=$('#LFzone').val();
		var circle =$("#LFcircle").val();
		var circleNameCodeArr =[];
		if(circle !="%"){
			circleNameCodeArr = circle.split('-');
			circle=circleNameCodeArr[1];
			}
		//var circle=circleNameCodeArr[1];
		var feeder = $('#feederTpId').val();
		var town = $('#LFtown').val();
		var month = $('#month').val();
		 /* alert(circle);
		alert(town); */
			
		window.location.href = ("./FeederOutagePDF?month=" + month + "&feeder=" + feeder + "&town=" + town + "&circle=" + circle);				
	}

</script>
