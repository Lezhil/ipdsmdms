<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style>
.dropdown-menu li>a {
	padding: 6px 0 6px 13px;
	line-height: 23px;
}

.dropdown-menu>li>a>.badge {
	position: absolute;
	margin-top: 1px;
	right: 20px;
	display: inline;
	font-size: 14px ! important;
	height: 18px ! important;
	font-weight: bold;
	padding: 2px 5px 0px 5px ! important;
}
/* .col-xs-1, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9, .col-xs-10, .col-xs-11, .col-xs-12, .col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12, .col-md-1, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-10, .col-md-11, .col-md-12, .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12 {
    position: relative;
    min-height: 1px;
    padding-right: 3px;
    padding-left: 3px;
} */
.portlet.box>.portlet-body {
	padding: 5px;
}

.portlet {
	margin-bottom: 10px;
}
</style>

<script>

/* $('#ADMINSideBarContents')
.addClass('start active ,selected');

		 */			
</script>

<script>
	$(".page-content")
			.ready(
					function() {
						
						
						App.init();
						TableEditable.init();
						FormComponents.init();
						 $('#MDMSideBarContents,#surveydetails,#installation').addClass('start active ,selected'); 
						$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');

						
					/* 	App.init(); */
						/* loadSearchAndFilter2('sample_editable_2'); */
					/* 	TableEditable.init();
						FormComponents.init(); */
						
						/* $('#sample_editable_2').dataTable({
			                "aLengthMenu": [
			                    [20, 50, 100, -1],
			                    [20, 50, 100, "All"] // change per page values here
			                ], */
			                // set the initial value
			              /*   "iDisplayLength": 20,
			                 
			                "sPaginationType": "bootstrap",
			                "oLanguage": {
			                    "sLengthMenu": "_MENU_ records",
			                    "oPaginate": {
			                        "sPrevious": "Prev",
			                        "sNext": "Next"
			                    }
			                },
			                "aoColumnDefs": [{
			                        'bSortable': false,
			                        'aTargets': [0]
			                    }
			                ]
			            }); */
						
						/* $('#MDMSideBarContents,#surveydetails,#installation').addClass('start active ,selected'); */
					/* 	$('#ADMINSideBarContents,#installation').addClass('start active ,selected'); */
						/* $(	"#MDASSideBarContents,#MDMSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');
						 */
						runpiecharts();
						
						//PIECHART====================================================

						 /* $.ajax({
									url : "./getSurveyAndInstallStatus",
									data : "",
									type : "GET",
									dataType : "text",
									async : false,
									success : function(response) {
										var object = JSON.parse(response);

 										alert(object.graphData);
										
										
										
										Highcharts
												.chart(
														'containerPieChart',
														{
															chart : {
																plotBackgroundColor : null,
																plotBorderWidth : null,
																plotShadow : false,
																type : 'pie'
															},
															title : {
																text : ''
															},
															tooltip : {
																pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
															},
															legend : {
																align : 'right',
																layout : 'vertical',
																verticalAlign : 'top',
															},
															exporting : {
																enabled : false
															},
															plotOptions : {
																pie : {
																	allowPointSelect : true,
																	cursor : 'pointer',
																	dataLabels : {
																		enabled : false
																	},
																	showInLegend : true
																}
															},
															series : [ {
																name : '',
																colorByPoint : true,
																data : object.graphData
															} ]
														});

										//BAR CHART==================================================== 
										 Highcharts
												.chart(
														'containerBarChart',
														{
															chart : {
																type : 'column'
															},

															title : {
																text : 'Last 30 Days Survey & Installation Status'
															},

															legend : {
																align : 'center',
																verticalAlign : 'bottom',
																layout : 'horizontal'
															},
															colors : [
																	'#57b5e3',
																	'#3cc051',
																	'#FF0000' ],
															xAxis : {
																categories : object.dates_graph,
																labels : {
																	x : -10
																}
															},

															yAxis : {
																allowDecimals : false,
																title : {
																	text : 'Count'
																},
																max : object.graph_max,
																endOnTick : false
															},

															series : [
																	{
																		name : 'Survey',
																		data : object.survey_graph
																	},
																	{
																		name : 'Installation',
																		data : object.install_graph
																	},
																	{
																		name : 'communicated',
																		data : object.communi_graph
																	} ],

															responsive : {
																rules : [ {
																	condition : {
																		maxWidth : 500
																	},
																	chartOptions : {
																		legend : {
																			align : 'center',
																			verticalAlign : 'bottom',
																			layout : 'horizontal'
																		},
																		yAxis : {
																			labels : {
																				align : 'left',
																				x : 0,
																				y : -5
																			},
																			title : {
																				text : null
																			}
																		},
																		subtitle : {
																			text : null
																		},
																		credits : {
																			enabled : false
																		}
																	}
																} ]
															}
														}); 

									}
								});   */

					});
</script>


<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-chain"></i>Installation Progress
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-bottom: 0px;">

				<div class="col-sm-3" >
					<div class="dropdown inline clearfix">
						<ul class="dropdown-menu" role="menu"
							style="width: 100%; height: 230px; pointer-events:; padding: 5px ! important;">
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#"> <b>Overall Status</b>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Total Consumers<span
									class="badge badge-info " id="totalMeters">${totalmaster}</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Total Installed<span
									class="badge badge-info " id="totalInstalled">${total_installed}</span>
							</a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Installed Today  <span class="badge badge-success "
									id="installed">${installedtoday}</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Communicating Today<span
									class="badge badge-primary " id="installPending">${commtoday}</span>
							</a></li>
						</ul>
					</div>

				</div>
				<div class="col-sm-3" style="display: none;">
					<div class="dropdown inline clearfix">
						<ul class="dropdown-menu" role="menu"
							style="width: 100%; height: 230px; pointer-events:; padding: 5px ! important;">
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#"> <b>Others</b>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Overall Installed<span
									class="badge badge-success " id="totalInastalled">${totalinstalled }</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Not in Master<span
									class="badge badge-danger " id="insNotInMaster">${notinmaster}</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Active Today<span
									class="badge badge-primary " id="insNotInMaster">${activetoday}</span>
							</a></li>
							<!-- <li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Survey Status <span class="badge badge-success "
									id="surveyStatusHaryana">Completed</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Feeder after survey <span class="badge badge-info "
									id="surveyFinishedHaryana">0</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Modem Installed  <span class="badge badge-success "
									id="installedHaryana">0</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Modem Yet to Install<span
									class="badge badge-danger " id="installPendingHaryana">0</span>
							</a></li> -->
						</ul>
					</div>

				</div>
					<!-- <div class="col-sm-3">
					<div class="dropdown inline clearfix">
						<ul class="dropdown-menu" role="menu"
							style="width: 100%; height: 230px; pointer-events:; padding: 5px ! important;">
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#"> <b>Himachal Pradesh</b>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Total Feeders (RFP)<span
									class="badge badge-info " id="totalMetersHp">1063</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Survey Status <span class="badge badge-danger "
									id="surveyStatusHp">Not Started</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Feeder after survey <span class="badge badge-info "
									id="surveyFinishedHp">0</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Modem Installed  <span class="badge badge-success "
									id="installedHp">0</span>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Modem Yet to Install<span
									class="badge badge-danger " id="installPendingHp">1063</span>
							</a></li>
						</ul>
					</div>

				</div> -->
				<div class="col-md-3">
					<div id="containerPieChart" style="width: 100%; height: 230px;"></div>
				</div>
 			<!-- 	<div class="col-md-3">

					<div class="dropdown inline clearfix">
						<ul class="dropdown-menu" role="menu"
							style="width: 100%; height: 230px; pointer-events:; padding: 5px ! important;">
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#"> <b>This Month</b>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Surveyed <span class="badge badge-info "
									id="surveyedThisMonth">0</span></a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Installed <span class="badge badge-success "
									id="installedThisMonth">0</span></a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#"> <b>Last Month</b>
							</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Surveyed <span class="badge badge-info "
									id="surveyedLastMonth">0</span></a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1"
								href="#">Installed <span class="badge badge-success "
									id="installedLastMonth">0</span></a></li>
						</ul>
					</div>

				</div> -->

			</div>
		</div>
	</div>

<!-- 	<div class="row" style="margin-bottom: 10px;">
		<div class="col-md-12">
			<div id="containerBarChart" style="width: 100%; height: 300px;"></div>
		</div>
	</div> -->
	<!--  from here-->

	<div class="row">

		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-chain"></i>Active Meters on ${dateHead}
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<form action="./installationDetailsMDAS" method="post">
							<table style="width: 30%">
								<tbody>
									<tr>
										<td>Select Date</td>
										<td>
											<div class="input-icon">
												<i class="fa fa-calendar"></i> <input
													class="form-control date-picker input-medium"
													style="width: 150px ! important;" type="text"
													value="${date}" data-date-format="yyyy-mm-dd"
													data-date-viewmode="years" name="date" id="date" />
											</div>
										</td>
										<td>
											<button id="viewFdrOnMap"
												onclick="return fdrOnMapVal(this.form);" name="viewFdrOnMap"
												class="btn yellow">
												<b>View</b>
											</button>
										</td>
									</tr>
								</tbody>
							</table>
						</form>

					</div>

					<div class="table-toolbar">

						<div class="btn-group pull-right" style="margin-top: 15px;">

						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								<th>No.</th>
								<th>Meter No.</th>
								<th>Consumer Name</th>
								<th>Account No.</th>
								<th>Last Sync Time</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="element" items="${mDetailList}">
								<tr>
									<td>${element.rowNo}</td>
									<td>${element.meter_number}</td>
									
									<td>${element.fdrname}</td>
									<td>${element.substation}</td>
									<td><fmt:formatDate value="${element.last_communication}"
											pattern="dd-MM-yyyy HH:mm:ss" /></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
	<!-- to here -->
	<div class="portlet box red" style=" display: none;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs"></i>Diagnosis Info on ${dateHead}
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse" data-original-title=""
					title=""> </a>
			</div>
		</div>
		<div class="portlet-body">
		<div class="table-toolbar">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#">Print</a></li>
										<li><a href="#">Save as PDF</a></li>
										<li><a href="#">Export to Excel</a></li>
									</ul>
								</div>
							</div>
				<table class="table table-striped table-bordered table-hover"
						id="sample_editable_2">
					<thead>
						<tr>
							<th>No.</th>
							<th>Meter No.</th>
							
							<th>Consumer Name</th>
							<th>Account No.</th>
							<th>Diag. Type</th>
							<th>Diag Status</th>
							<th>Diag Time</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="element" items="${diagInfo}">
							<tr>
								<td>${element.count}</td>
								<td>${element.meter_number}</td>
								
								<td>${element.fdrname}</td>
								<td>${element.substation}</td>
								<td>${element.diag_type}</td>
								<td>${element.status}</td>
								<td><fmt:formatDate value="${element.tracked_time}"
										pattern="dd-MM-yyyy HH:mm:ss" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
	</div> 
	
	

	<%-- <div class="row">

		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-chain"></i>Communicated Info
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<table style="width: 30%">
							<tbody>
								<tr>
									<td>Select Date</td>
									<td>
										<div class="input-icon">
											<i class="fa fa-calendar"></i> <input
												class="form-control date-picker input-medium"
												style="width: 150px ! important;" type="text"
												value="${datecom}" data-date-format="yyyy-mm-dd"
												data-date-viewmode="years" name="datecom" id="datecom" />
										</div>
									</td>
									<td>
										<button id="" onclick="communicationDetails()"
											class="btn yellow">
											<b>View</b>
										</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="table-toolbar">

						<div class="btn-group pull-right" style="margin-top: 15px;">

						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_3">
						<thead>
							<tr>
								<th>No.</th>
								<th>Circle.</th>
								<th>Division</th>
								<th>Sub-Division</th>
								<th>Substation</th>
								<th>Feeder Name</th>
								<th>Meter No</th>
								<th>ModemIMEI</th>
								<th>Communication Status</th>
								<th>Last Communication</th>
							</tr>
						</thead>
						<tbody id="commData">

						</tbody>
						
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div> --%>
</div>

<script type="text/javascript">
	function communicationDetails(dateComm) {
		var datecom1 = $('#datecom').val();

		$.ajax({
			url : "./communicationDetails/" + datecom1,
			type : "GET",
			dataType : "json",
			async : false,
			success : function(response) {

				if (response.length == 0 || response.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : ");
				} else {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];

						html += "<tr>" + "<td>" + (i + 1) + "</td>" + "<td>"
								+ resp.circle + "</td>" + "<td>"
								+ resp.division + "</td>" + "<td>"
								+ resp.subDivision + "</td>" + "<td>"
								+ resp.subStation + "</td>" + "<td>"
								+ resp.feederNmae + "</td>" + "<td>"
								+ resp.meterNo + "</td>" + "<td>" + resp.imei
								+ "</td>" + "<td>" + resp.commun + "</td>"
								+ "<td>" + moment(resp.instDate).format('YYYY-MM-DD') + "</td>"

								+ " </tr>";
					}

					$('#sample_3').dataTable().fnClearTable();
					$('#commData').html(html);
					loadSearchAndFilter1('sample_3');
				}

			}

		});

	}
</script>


<script>
function runpiecharts(){
	
	var master="${totalmaster}";
	master=parseFloat(master);
	
	var masterinstalled="${installedmaster}";
	masterinstalled=parseFloat(masterinstalled);
	
	var masterpending="${pendingmaster}";
	masterpending=parseFloat(masterpending);
	
	var insper=parseFloat((masterinstalled/master)*100).toFixed(2);
	var penper=parseFloat((masterpending/master)*100).toFixed(2);
	
	//alert(insper+"--"+penper)
	Highcharts
	.chart(
			'containerPieChart',
			{
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					type : 'pie'
				},
				title : {
					text : ''
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				legend : {
					align : 'right',
					layout : 'vertical',
					verticalAlign : 'top',
				},
				exporting : {
					enabled : false
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : false
						},
						showInLegend : true
					}
				},
				series : [ {
					name : '',
					colorByPoint : true,
					data : [{
						name : 'Installed',
						y : parseFloat(insper)
					}, {
						name : 'Pending',
						y : parseFloat(penper)
					}]
				}]
			}); 
			

	
			
			
			
	
	
}


</script>

