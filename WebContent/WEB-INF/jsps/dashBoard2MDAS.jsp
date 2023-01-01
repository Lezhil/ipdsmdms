<!-- BEGIN PAGE -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/highcharts/highcharts.js'/>"type="text/javascript"></script>
<script src="<c:url value='/highcharts/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/export-data.js'/>"type="text/javascript"></script>
<script src="<c:url value='/highcharts/accessibility.js'/>" type="text/javascript"></script>

<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	
<!-- <script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>  -->

<!-- <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script> -->

<link href="resources/assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>" type="text/javascript"></script>

<style>
#chart_2 {
	height: 700px;
}

.highcharts-figure, .highcharts-data-table table {
	min-width: 560px;
	max-width: 1020px;
	margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 1000px;
}

.highcharts-data-table caption {
	padding: 1em 0;
	font-size: 1.2em;
	color: #555;
}

.highcharts-data-table th {
	font-weight: 600;
	padding: 0.5em;
}

.highcharts-data-table td, .highcharts-data-table th,
	.highcharts-data-table caption {
	padding: 0.5em;
}

.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even)
	{
	background: #f8f8f8;
}

.highcharts-data-table tr:hover {
	background: #f1f7ff;
}
</style>

<style>
.col-xs-5 {
	width: auto;
}

.dropdown-menu>li>a>.badge {
	position: absolute;
	margin-top: 1px;
	right: 10px;
	display: inline;
	font-size: 14px ! important;
	height: 18px ! important;
	font-weight: bold;
	padding: 2px 5px 0px 5px ! important;
}
.pie {
  width: 100px;
  height: 100px;
  background-image: conic-gradient(orange 64%, blue 17%, black);
  border-radius: 50%
}
</style>

 <style> 
        .vertical { 
            border-left: 3px solid green; 
            height: 200px; 
            position:absolute; 
            left: 58%; 
        } 
  </style> 
<script>
	var urlLavel = "";

	$(".page-content")
			.ready(
					function() {
						urlLavel = '${urlLavel}';
						
					
						//alert('${currentDate}');
						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						Index.initMiniCharts();
						TableEditable.init();
						FormComponents.init();
						/*  loadSearchAndFilter('table_1'); 
						 loadSearchAndFilter('table_2'); 
						 loadSearchAndFilter('table_3');  */
						//alert('${sub_level}');
						$("#DashboardContent,#livedashboard").addClass(
								'start active ,selected');
						$(
								'#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
</script>


<style>
.portlet.box.blue>.portlet-title {
	background-color: #4b8cf8;
}

.alert {
	border: 1px solid transparent;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
}

.modal-dialog {
	padding: 40px 100px 30px 220px;
	width: 100%;
}

.table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	padding: 8px;
	line-height: 1.428571429;
	vertical-align: top;
}

.badge-success {
	background-color: #3cc051;
}


</style>

<script type="text/javascript">
	/* function mtrDetails(mtrId) {
		alert(mtrId);
	} */
</script>

<div class="page-content">
	<%-- <div class="row">
		
		<div class="col-xs-12"
			style="padding: 0px 10px; margin: 0px 25px 0px 0px; padding-left: 15px; margin-top: -15px;">
				<div style="float: right; font-size: 24px;" id="locationHeader">
				<label style="float: left;"> Zones : <span id="ZonesInTitle"
					style="color: #000000;">${ZONECOUNT}</span> &nbsp;&nbsp;
				</label> <label style="float: left;"> Circle : <span
					id="CircleInTitle" style="color: #000000;">${CIRCLECOUNT}</span> &nbsp;&nbsp;
				</label> <label style="float: left;"> Division : <span
					id="DivisionInTitle" style="color: #000000;">${DIVISIONCOUNT }</span> &nbsp;&nbsp;
				</label> <label style="float: left;"> SubDivision : <span id="SubDivisionInTitle" style="color: #000000;">${SUBDIVISIONCOUNT}</span> &nbsp;
					&nbsp;
				</label>
			</div>
		</div>
	</div> --%>




	<div class="portlet box blue" style="margin-bottom: 0px;">
		<div class="portlet-title blue">
			<label
				style='color: #000000; font-size: 16px; float: left; padding-top: 9px;'><span
				style="color: #c6dbff;"><i class="fa fa-building"></i>
					${level}</span>&nbsp;<span style="color: #FFF;">${value}</span></label>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>

			</div>
		</div>
		<div class="portlet-body">

			<div class="row" style="margin-bottom: 10px;">

				<div class=" col-sm-2 " style="width: 260px;">
					<div class="dashboard-stat2 "
						style="padding-top: 33px; padding-bottom: 0px; padding-left: 58px;">
						<div class="display">
							<div class="number" style="text-align: center;">
								<h3 class="font-green-sharp" style="font-size: 40px;">
									<b> <span class="popovers" data-container="body"
										data-trigger="hover" data-placement="top"
										data-content="Total Number of Meter Installed">${totalMeters}</span>
									</b>
								</h3>
								<small style="font-size: 15px;">TOTAL METERS MAPPED</small>
							</div>
						</div>

					</div>
				</div>
				<!-- <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <a class="dashboard-stat dashboard-stat-v2 green" href="#">
                                    <div class="visual">
                                        <i class="fa fa-shopping-cart"></i>
                                    </div>
                                    <div class="details">
                                        <div class="number">
                                            <span data-counter="counterup" data-value="549">0</span>
                                        </div>
                                        <div class="desc"> New Orders </div>
                                    </div>
                                </a>
                            </div> -->



				<div class="col-sm-4">
					<div
						style="background: aliceblue; padding-left: 20px; padding-bottom: 15px; width: 360px;">
						<label style="font-size: medium; padding-top: 5px;"><i
							class="fa fa-tablet"></i> Meter Communication Status</label>
						<!-- <span style="font-size: x-small; margin-left: 5px;">For last 24 hrs
					</span> -->
						<span style="font-size: x-small; margin-left: 5px;">For <c:set
								var="yesterday"
								value="<%=new java.util.Date(new java.util.Date().getTime())%>" />
							<fmt:formatDate type="date" value="${yesterday}"
								pattern="dd-MM-yyyy" />
						</span>
						<div class="row">
							<div class="col-xs-2">
								<div class="easy-pie-chart " style="padding-top: 11px;">
									<div id="activeper" class="number visits activeper popovers"
										data-percent="10" data-container="body" data-trigger="hover"
										data-placement="top"
										data-content="Percentage of Active Meters over Installed Meters.">
										<span id="activeperspan">${com_per}</span>%
									</div>
								</div>
							</div>

							<div class="col-xs-5 dropdown inline clearfix"
								style="width: 245px;">
								<ul class="dropdown-menu" role="menu"
									style="width: 234px; margin-left: 35px;">
									<%-- <li><a href="./totalMeterDetails">Active <span
										class="badge badge-success">${activeCount}</span></a></li>
								<li class="divider" style="margin: 3px 0;"></li>
								<li><a
									onclick="getInactiveModemsSubLevel('HEADER'); return false;"
									data-toggle="modal" data-target="#stack2" data-dismiss="modal">Inactive
										<span class="badge badge-danger">${inActiveCount}</span>
								</a></li> --%>
									<%-- <li><a href="" class="popovers" data-container="body"
                                            data-trigger="hover" data-placement="top" data-content="Meters which are Installed today till now. Click to get the details.">
                                            <font style="font-size: 14px;">Installed Meters </font><span
										class="badge badge-success">${installed}</span></a></li>
										<li class="divider" style="margin: 3px 0;"></li> --%>

									<%--  <li><a href="./unmappedMeters" class="popovers" data-container="body"
                                            data-trigger="hover" data-placement="top" data-content="Meters which are UnMapped till now. Click to get the details.">
                                            <font style="font-size: 14px;">UnMapped Meters </font><span
										class="badge badge-success">${unmappedMeter}</span></a></li>  --%>
									<li><a
										href="./totalMeterDetailsMDAS?type=active${urlLavel}"
										class="popovers" data-container="body" data-trigger="hover"
										data-placement="top"
										data-content="Meters which are communicating today till now. Click to get the details.">
											<font style="font-size: 14px;">Communicating Meters </font><span
											class="badge badge-success">${activeCount}</span>
									</a></li>
									<li class="divider" style="margin: 3px 0;"></li>
									<li><a
										href="./totalMeterDetailsMDAS?type=notcom${urlLavel}"
										class="popovers" data-container="body" data-trigger="hover"
										data-placement="top"
										data-content="Meters which didn't communicated today till now. Click to get the details. ">
											<font style="font-size: 14px;">Not Communicating </font><span
											class="badge badge-warning">${inActiveCount}</span>
									</a></li>
									<%-- <li class="divider" style="margin: 3px 0;"></li>
								<li><a href="./totalMeterDetailsMDAS?type=inactive${urlLavel}"> <font style="font-size: 14px;">Modems Not Installed </font><span
										class="badge badge-danger">${mtrNvrComm}</span></a></li> --%>
								</ul>
							</div>

						</div>
					</div>
				</div>
				<%-- <div class="col-sm-4" style="margin-left: 49px; ">
				<div style="background: aliceblue; padding-left: 10px; padding-bottom: 15px; width: 360px;">
					<label style="font-size: medium; padding-top: 5px;"><i
						class="fa fa-upload"></i> RMS Sync Status</label> <span
						style="font-size: x-small; margin-left: 5px;">On <c:set
							var="yesterday"
							value="<%=new java.util.Date(new java.util.Date().getTime())%>" />
						<fmt:formatDate type="date" value="${yesterday}"
							pattern="yyyy-MM" /> 
					</span>
					<div class="row">
						
						<div class="col-xs-2">
							<div class="easy-pie-chart " style="padding-top: 11px;">
								<div class="number visits uploadper popovers" id="uploadper"
									data-percent=20 data-container="body"  data-trigger="hover" data-placement="top" data-content="Percentage of Synced Meter Data over Total Meters.">
									<span id="uploadperspan">0</span>%
								</div>
							</div>
						</div>
					
					
						<div class="col-xs-5 dropdown inline clearfix">
							<ul class="dropdown-menu" role="menu" style="width: 234px; margin-left: 35px;">
								<li><a href="#" style="cursor: default;" class="popovers" data-container="body"
                                            data-trigger="hover" data-placement="top" data-content="No of Consumer data synced with RMS system for billing in current month.">RMS Data Synced <span
										class="badge badge-success" id="uploadedCount"></span></a></li>
								<li class="divider" style="margin: 3px 0;"></li>
								<li><a  href="#" style="cursor: default;" class="popovers" data-container="body"
                                            data-trigger="hover" data-placement="top" data-content="No of Consumer data yet to sync with RMS system for billing in current month.">Yet to Sync

										<span class="badge badge-warning" id="failedUpldCount">
									</span>
								</a></li>
								<li class="divider" style="margin: 3px 0;"></li>
								<li><a href="#">Meter Changed <span
										class="badge badge-info" id="uploadedCount">${mtrChnaged } </span></a></li>
							</ul>
						</div>
						
					</div>
				</div>
				</div> --%>
				<div class="col-sm-4" style="margin-left: 49px;">
					<div
						style="background: aliceblue; padding-left: 10px; padding-bottom: 15px; width: 360px;">
						<label style="font-size: medium; padding-top: 5px;"><i
							class="fa fa-refresh"></i>Meters Status(Last 30Days)</label>
						<div class="row">
							<div class="col-xs-5 dropdown inline clearfix">
								<ul class="dropdown-menu" role="menu"
									style="width: 234px; margin-left: 35px;">
									<li><a href="./unmappedMeters" class="popovers"
										data-container="body" data-trigger="hover"
										data-placement="top" data-content="No of Meters Not Mapped">Un
											Mapped Meters <span class="badge badge-success"
											id="uploadedCount"></span>
									</a></li>
									<!-- <li class="divider" style="margin: 3px 0;"></li>
									<li><a  href="#" style="cursor: default;" class="popovers" data-container="body"
                                            data-trigger="hover" data-placement="top" data-content="No of Consumer data yet to sync with RMS system for billing in current month.">Yet to Sync

										<span class="badge badge-warning" id="failedUpldCount">
									</span>
								</a></li> -->
								
								</ul>
							</div>

						</div>
					</div>
				</div>
				<!-- <div class="col-sm-2" style="height: 100% !important; text-align: center;padding-top: 13px;">
				<a style="width: 100%; margin-top: 10px; font-size: small;" onclick="viewOnMapSubstation(); return false;" class="btn default red-stripe"><i class="fa fa-map-marker"></i>&nbsp; View On Map&nbsp;</a>
				<br/><br/>
				<a style="width: 100%; font-size: small;"  onclick="powerSupplyGraphp(); return false;" class="btn default green-stripe"><i class="fa fa-bolt"></i> &nbsp; Power Supply</a>
				</div> -->

			</div>
		</div>

	</div>

	<div class="row" style="margin-right: 0px; margin-left: 0px;">
		<div class="progress progress-striped active"
			style="margin-bottom: 5px;">
			<div class="progress-bar progress-bar-primary" role="progressbar"
				aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
				style="width: 100%">
				<marquee id="marqueeInfo"
					style="font-weight: bold; font-size: small; text-shadow: 1px 1px #4b8cf8;">
				</marquee>
			</div>
		</div>
	</div>



	<br>

	 <div class="portlet box blue"
		style="margin-bottom: 0px; margin-top: 5px;">
		<div class="portlet-title blue">
			<div class="caption" style="font-size: 16px;">
				<i class="fa fa-bar-chart-o"></i>Last 30 days Communication status
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div id="chart_2" style="height: 500px;" class="chart"></div>
		</div>
	</div> 


	<br>

	<!-- <div class="portlet box blue"
		style="margin-bottom: 0px; margin-top: 5px;">
		<div class="portlet-title blue">
			<div class="caption" style="font-size: 16px;">
				<i class="fa fa-bar-chart-o"></i>Last 30 Days Mapped Meters
			</div>
			<div class="actions">
				<a href="javascript:;" data-toggle="modal" data-target="#formId">

				</a>
			</div>
		</div>
		<div class="portlet-body">
			<div id="chart_3" style="height: 300px;" class="chart"></div>
		</div>

	</div> -->


	<!-- Modal -->
	<div class="modal fade" id="formId" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-scrollable" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;"
							id="MappedMetersTitle"></font>
					</h4>

				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('formIdTable','MappedMeters Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="formIdTable"
						class="table table-striped table-bordered table-advance table-hover">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Meter-No</th>
								<th>Meter Manufacturer</th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody id="formIdTableBody">
						</tbody>
					</table>
                   </div>
					<div class="modal-footer">
						<button type="button" class="btn dark btn-outline""
							data-dismiss="modal">Close</button>
					</div>
				</div>


			</div>
		</div>
	


	<br>

	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs"></i>MDAS wise/Meter wise Summary
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
				<!-- <a href="#portlet-config" data-toggle="modal" class="config"></a> -->
				<a href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
				<c:forEach items="${HESDETAILS}" var="Element">
					<c:choose>
						<c:when test="${fn:contains(Element[0], 'ANALOGICS')}">
							<div class="col-md-3">

								<div class="portlet box blue ">
									<div class="portlet-title">
										<div class="caption">${Element[0]=="GENUS"?"GENUS-DT Meters":Element[0]=="ANALOGICS"?"Feeder/Boundary MDAS":Element[0]}</div>
										<!-- totalMeters -->
									</div>
									<div class="portlet-body">
										<label style="color: #8d8888; padding-top: 5px; float: left;">Total
											Count : <span id="totalFeeder"
											style="color: #FFFFFF; font-size: medium; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${Element[1]}
										</span>
										</label>
										<div class="clearfix"></div>
										<div class="row">
											<label
												style="color: #4b8df8; font-size: medium; padding-top: 5px; margin-left: 15px; margin-top: -15px;">Count
												Status</label>

											<div class="row" style="margin-left: 5px;">
												<div class="col-xs-5">
													<div class="easy-pie-chart ">
														<div class="number transactions"
															data-percent="${Element[6]}">
															<!--issue  -->
															<span>${Element[6]}</span>%
														</div>
													</div>
												</div>
												<label class="col-xs-7" style="color: #8d8888;">
													Mapped Meters : <u
													onclick="getHESMeterStatus('${Element[0]}','active','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: green; cursor: pointer;">${Element[2]}</u>
													<br /> <%-- Inactive : <u
											onclick="getHESMeterStatus('${Element[0]}','inactive','${Element[7]}'); return false;"
											data-toggle="modal" data-target="#stack4"
											data-dismiss="modal"
											style="font-weight: bold; color: red; cursor: pointer;">${Element[3]}</u><br /> --%>
													Comm Today : <u
													onclick="getHESMeterStatus('${Element[0]}','commTdy','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: orange; cursor: pointer;">${Element[4]}</u>
													<br /> NotComm Today : <u
													onclick="getHESMeterStatus('${Element[0]}','notCommTdy','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: red; cursor: pointer;">${Element[5]}</u>
												</label>
											</div>
										</div>

									</div>
								</div>

							</div>

							<c:forEach items="${fdrcatSummaryList}" var="element">

								<c:choose>
									<c:when test="${fn:contains(element[0], 'DT')}">
									</c:when>
									<c:otherwise>
										<div class="col-md-3">

											<div class="portlet box green ">
												<div class="portlet-title">
													<div class="caption">
														<%-- 	
								 <c:choose>
											<c:when test="${fn:contains(element[0], 'DT')}">
												<span class="caption-subject font-dark bold uppercase">${element[0]}&nbsp;METER</span>
											</c:when>
											<c:otherwise> --%>
														<span class="caption-subject font-dark ">${element[0]}</span>
														<%-- </c:otherwise>
										</c:choose>   --%>
													</div>
												</div>
												<div class="portlet-body">
													<label
														style="color: #8d8888; padding-top: 5px; float: left;">Total
														Count : <span id="totalFeeder"
														style="color: #FFFFFF; font-size: medium; background-color: #3c9e00; padding: 0px 10px; text-align: center;">${element[1]}
													</span>
													</label>
													<div class="clearfix"></div>
													<div class="row">
														<label
															style="color: #4b8df8; font-size: medium; padding-top: 5px; margin-left: 15px; margin-top: -15px;">Count
															Status</label>

														<div class="row" style="margin-left: 5px;">
															<div class="col-xs-5">
																<div class="easy-pie-chart ">
																	<div class="number transactions"
																		data-percent="${element[8]}">
																		<!--issue  -->
																		<span>${element[8]}</span>%
																	</div>
																</div>
															</div>
															<label class="col-xs-7" style="color: #8d8888;">
																Mapped Meters : <u
																onclick="getMeterTypeSummaryDetails('${element[0]}','active}'); return false;"
																data-toggle="modal" data-target="#stack4"
																data-dismiss="modal"
																style="font-weight: bold; color: green; cursor: pointer;">${element[2]}</u>
																<br /> <%-- Inactive : <u
											onclick="getMeterTypeSummaryDetails('${Element[0]}','inactive'); return false;"
											data-toggle="modal" data-target="#stack4"
											data-dismiss="modal"
											style="font-weight: bold; color: red; cursor: pointer;">${Element[3]}</u><br /> --%>
																Comm Today : <u
																onclick="getMeterTypeSummaryDetails('${element[0]}','commTdy'); return false;"
																data-toggle="modal" data-target="#stack4"
																data-dismiss="modal"
																style="font-weight: bold; color: orange; cursor: pointer;">${element[4]}</u>
																<br /> NotComm Today : <u
																onclick="getMeterTypeSummaryDetails('${element[0]}','notCommTdy'); return false;"
																data-toggle="modal" data-target="#stack4"
																data-dismiss="modal"
																style="font-weight: bold; color: red; cursor: pointer;">${element[5]}</u>

															</label>
														</div>
													</div>

												</div>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>

						</c:when>
						<c:otherwise>
							
							<div class="col-md-3">
								<div class="portlet box blue ">
									<div class="portlet-title">
										<div class="caption">${Element[0]=="GENUS"?"Dist Transformer MDAS":Element[0]=="ANALOGICS"?"ANALOGICS-Meters":Element[0]}</div>
										<!-- totalMeters -->
									</div>
									<div class="portlet-body">
										<label style="color: #8d8888; padding-top: 5px; float: left;">Total
											Count : <span id="totalFeeder"
											style="color: #FFFFFF; font-size: medium; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${Element[1]}
										</span>
										</label>
										<div class="clearfix"></div>
										<div class="row">
											<label
												style="color: #4b8df8; font-size: medium; padding-top: 5px; margin-left: 15px; margin-top: -15px;">Count
												Status</label>

											<div class="row" style="margin-left: 5px;">
												<div class="col-xs-5">
													<div class="easy-pie-chart ">
														<div class="number transactions"
															data-percent="${Element[6]}">
															<!--issue  -->
															<span>${Element[6]}</span>%
														</div>
													</div>
												</div>
												<label class="col-xs-7" style="color: #8d8888;">
													Mapped Meters : <u
													onclick="getHESMeterStatus('${Element[0]}','active','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: green; cursor: pointer;">${Element[2]}</u>
													<br /> <%-- Inactive : <u
											onclick="getHESMeterStatus('${Element[0]}','inactive','${Element[7]}'); return false;"
											data-toggle="modal" data-target="#stack4"
											data-dismiss="modal"
											style="font-weight: bold; color: red; cursor: pointer;">${Element[3]}</u><br /> --%>
													Comm Today : <u
													onclick="getHESMeterStatus('${Element[0]}','commTdy','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: orange; cursor: pointer;">${Element[4]}</u>
													<br /> NotComm Today : <u
													onclick="getHESMeterStatus('${Element[0]}','notCommTdy','${Element[7]}'); return false;"
													data-toggle="modal" data-target="#stack4"
													data-dismiss="modal"
													style="font-weight: bold; color: red; cursor: pointer;">${Element[5]}</u>
												</label>
											</div>
										</div>

									</div>
								</div>

							</div>
							
						</c:otherwise>
					</c:choose>

				</c:forEach>

				<!--  <div class = "vertical"></div>  -->

			</div>


		</div>
	</div>

	<!-- </div> -->


	<%-- <c:if test="${sub_level eq 'circle'}"> --%>

		<!-- <div class="portlet box blue" -->
		<!-- style="margin-bottom: 0px; margin-top: 5px;"> -->
			<!-- <div class="portlet-title blue" style="background-color: cadetblue"  >
				<div class="caption" style="font-size: 16px;">
					<i class="fa fa-bar-chart-o"></i>Meter Type Wise Summary
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>

				</div>
			</div> -->
			

		<%-- <div class="portlet-body">
			
		</div>

		

	</c:if>

 --%>

	<div class="row" style="margin-right: 1px; margin-top: 21px;"></div>

	


	


	





	<c:choose>
		<c:when
			test="${sub_level == 'corporate' || sub_level == 'circle' }">

			<div class="row"
				style="margin-right: 15px; margin-top: 21px; width: 77%;">
				<!-- header corporate level-->

				<div class="col-xs-12"
					style="padding: 0px 0px; margin: 0px 0px 0px 0px; padding-left: 15px;">
					<label style="color: #000000; font-size: 16px; padding-top: 5px;">${sub_level}</label>
				</div>
			</div>

		</c:when>
	</c:choose>
	<div class="row" id="subleveles">

		<c:forEach items="${sublevel}" var="Element">


			<c:choose>
				<c:when
					test="${sub_level == 'corporate' || sub_level == 'circle' }">



					<a class=" col-sm-2"
						href="./dashBoard2MDAS?type=${sub_level}&value=${Element[0]}">



						<div class="portlet box blue ">
							<div class="portlet-title">
								<div class="caption" style="color: ;font-size: 10px; padding-top: 5px; float: left;">${Element[0]}</div>
								<!-- totalMeters -->
							</div>
							<div class="portlet-body">
								<label style="color: #8d8888; font-size: small; padding-top: 5px; float: left;">Total
									Meters : <span id="totalFeeder"
									style="color: #FFFFFF; font-size: small; background-color: #4b8cf8; padding: 0px 6px; text-align: center;">${Element[1]}
								</span>
								</label>
								<div class="clearfix"></div>
								<div class="row">
									<label
										style="color: #4b8df8; font-size: medium; padding-top: 5px; margin-left: 15px; margin-top: -15px;">Meter
										Status</label>

									<div class="row" style="margin-left: 5px;">

										<div class="col-xs-5">
										<!-- <div class="pie"></div> -->
											<div class="easy-pie-chart ">
												<div class="number transactions"
													data-percent="${Element[6]}">
													<!--issue  -->
													<span>${Element[6]}</span>%
												</div>
											</div> 
										</div>
										<%-- <label class="col-xs-7" style="color: #8d8888;">Mapped
											Meters: <span style="font-weight: bold; color: green;">${Element[2]}</span>
											<br />Inactive: <u onclick="getInactiveModemsSubLevel('${Element[0]}'); return false;" data-toggle="modal" data-target="#stack2" data-dismiss="modal" style="font-weight: bold; color: red">${Element[3]}</u>
										</label> --%>
									</div>
								</div>
								<%-- <label style="color: #4b8df8; font-size :medium;   padding-top: 5px;">RMS Export Status </label>
						<span style="font-size: x-small; margin-left: 5px;">For <c:set
							var="yesterday"
							value="<%=new java.util.Date(new java.util.Date().getTime() - 60*60*24*1000)%>" />
						<fmt:formatDate type="date" value="${yesterday}"
							pattern="dd-MM-yyyy" />
						</span>
						
						
						<br> <label style="color: #8d8888; ">Acquired:&nbsp;<span style="color: blue; font-weight: bold;" >${Element[8]}</span>
							
							&nbsp;&nbsp;&nbsp;Synced 
							: <span style="color: green; font-weight: bold;" >${Element[4]}</span> &nbsp;&nbsp;&nbsp;
							Failed : <span style="font-weight: bold; color: red;"> ${Element[5]} </span>
							<u onclick="getNotUploadedModemsSubLevel('${Element[0]}'); return false;" 
							data-toggle="modal" data-target="#stack3" data-dismiss="modal" style="font-weight: bold; color: red";" >${Element[5]}</u>
							
							
						</label> --%>

							</div>
						</div>
					</a>


				</c:when>
			</c:choose>
		</c:forEach>



	</div>










	<div class="portlet box blue" id="feedersUnderSubstation">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>All Consumer Details
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>

		<div class="portlet-body">

			<table class="table table-striped table-hover table-bordered"
				id="table_1">
				<thead>
					<tr>

						<th>Consumer Acc No</th>
						<th>Consumer Name</th>
						<th>METER NO</th>
						<th>STATUS</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="element" items="${feederInfoList}">
						<tr>
							<%-- <td><a onclick="return mtrDetails('${element[2]}')" style="color: blue; text-decoration: underline;"> ${ element[0]}</a></td> --%>
							<td>${ element[0]}</td>
							<td>${element[1]}</td>
							<td><a href="./viewFeederMeterInfoMDAS?mtrno=${element[2]}"
								style="color: blue; text-decoration: underline;">${ element[2]}</a></td>
							<td><c:choose>
									<c:when test="${fn:contains(element[3], 'Active')}">
										<span class="label label-sm label-success">${element[3]}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-sm label-danger">${element[3]}</span>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div id="stack2" class="modal fade" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;">Inactive
							Meters</font>
					</h4>
				</div>

				<div class="modal-body">
					<table
						class="table table-striped table-bordered table-advance table-hover"
						id="inactiveTable">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Modem No.</th>
								<th>Meter No.</th>
								<th>Feeder Name</th>
								<th>SubStation</th>
								<th>SubDivision</th>
								<th>Division</th>
								<th>District</th>
								<th>Circle</th>
								<th>Last Sync Time</th>
							</tr>
						</thead>
						<tbody id="inactiveModemDetailsTbl">
							<!-- //ashwini -->

						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div id="stack3" class="modal fade"
		style="display: none; overflow-y: scroll; overflow-x: hidden;"
		tabindex="-1" data-width="400" data-backdrop="static"
		data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button class="bootbox-close-button close" type="button"
						style="margin-top: 1px;" data-dismiss="modal">X</button> -->
					<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>  -->
					<h4 class="modal-title">Meters Failed to Upload</h4>
					<span
						style="margin-left: 930px; position: absolute; margin-top: -29px;">
						<button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
					</span>

				</div>

				<div class="modal-body">
					<div class="portlet">

						<div class="portlet-body">
							<div class="table-responsive">
								<table
									class="table table-striped table-bordered table-advance table-hover">
									<thead>
										<tr>
											<th>Meter No.</th>
											<th>Feeder Name</th>
											<th>SubStation</th>
											<th>Failure Reason</th>
											<th>
												<button id="btnXmlUplad"
													style="font-size: small; background: #f84700;"
													type="button" class="btn btn-primary"
													onclick="forceUploadXMLAll(); return false;">
													&nbsp;<i class="fa fa-upload"></i>&nbsp;Upload All
												</button>
											</th>
										</tr>
									</thead>
									<tbody id="failedUploadMtrDetailsTbl">


									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="stack4" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;" id="tabTitle"></font>
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="excelExport"
												onclick="tableToExcel('getHESDetailsTbl', 'Meter_Type_Summary')">Export
													to Excel</a></li> -->

								<li><a href="#" id="excelExport6"
									onclick="tableToExcel6('sample_1','Meter Type Wise Summary Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="sample_1"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>SubStation</th>
								<th>Feeder Name</th>
								<th>Feeder/DT Code</th>
								<th>Town</th>
								<th>Meter No.</th>
								<th>Last Comm Time</th>
							</tr>
						</thead>
						<tbody id="getHESDetailsTblBody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>



	<div class="modal fade" id="stack5" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;" id="tabTitleLoss"></font>
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('getAtandcLoss','Health wizard AT&C losses');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="getAtandcLoss"
						class="table table-striped table-bordered table-advance table-hover">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>SubStation</th>
								<th>Town</th>
								<th>Meter No.</th>
								<th>AT&C Loss %</th>
							</tr>
						</thead>
						<tbody id="getAtandcLossBody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<div class="modal fade" id="stack6" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;" id="tabTitleDTLoss"></font>
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('getDtAtandcLoss','Health wizard AT&C losses');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="getDtAtandcLoss"
						class="table table-striped table-bordered table-advance table-hover">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>TpDTId</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>SubStation</th>
								<th>Meter No.</th>
								<th>AT&C Loss %</th>
							</tr>
						</thead>
						<tbody id="getDtAtandcLossBody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>


	

	<div class="modal fade" id="chartModal" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Feeder AT&C losses updated</h4>
				</div>
				<div id="chartContainer1"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn dark btn-outline"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>


	<div class="modal fade" id="nonipds" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<font style="font-size: 10pt; color: black;">X</font>
					</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;"
							id="showStatDataTitle">NON-IPDS</font>
					</h4>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_9">
						<thead>
							<tr>
								<th>SL No</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>SubStation</th>
								<th>Town</th>
								<th>Meter NO</th>

							</tr>
						</thead>
						<tbody id="nonipdsDataBody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn dark btn-outline"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>



	<script type="text/javascript">
		var totalMeters = '${totalMeters}';
		var failedUpload = '${failedUpldCount}';
		var totalMappedMeters = '${totalMappedMeters}';

		$(document)
				.ready(
						function() {
							if ('${level}' == "Sub Division") {//FOR SUBSTATION CHANGING THE GRID TO LIST
								document
										.getElementById('feedersUnderSubstation').style.display = 'show';
								document.getElementById('subleveles').style.display = 'none';
							} else {
								document
										.getElementById('feedersUnderSubstation').style.display = 'none';
								document.getElementById('subleveles').style.display = 'show';
							}
							//alert('${level}');
							var activeper = "${com_per}";
							activeper = parseInt(activeper);
							var col = "";
							if (activeper <= 30) {
								col = '#cb3935';
							} else if (activeper > 30 && activeper < 90) {
								col = '#f0ad4e';
							} else {
								col = '#5cb85c';
							}

							/* $('.easy-pie-chart').easyPieChart({
							    barColor: function (percent) {
							       return (activeper < 30 ? '#cb3935' : activeper < 90 ? '#f0ad4e' : '#5cb85c');
							    }
							}); */

							$('.activeper').data('easyPieChart').update(
									'${com_per}'); //MAIN HEAD ACTIVE COUNT PIECHART
							//$('.easyPieChart').barColor('#cb3935');
							$(".activeper").data('easyPieChart').options['barColor'] = col;

							//$("#uploadedCount").text(0);
							//$("#failedUpldCount").text(0); 
							$("#manualMtrs").text("0");
							//$("#uploadperspan").text(0);
							//$('.uploadper').data('easyPieChart').update(0);

							 getLast30DaysActiveStatus(); //Dashboard issue
							//getMappedMetersDetails();
							/* getRmsSyncData(); */
							 getUnmappedMeters(); //Dashboard issue
							 setScrollAlert(); //Dashboard issue

							 

						});

		function getRmsSyncData() {
			var total = "${totalMeters}";
			total = parseInt(total);
			//alert();

			$.ajax({
				//url : "http://1.23.144.187:8081/bsmartjvvnl/sendingMeteSynchDataToAMI",
				url : "./getSyncDataFromRMS",
				type : "GET",
				dataType : "TEXT",
				async : true,
				success : function(response) {
					//alert(response);

					var obj = parseInt(response);
					$("#uploadedCount").text(obj);

					var per = (parseFloat(obj) / total) * 100;

					var failed = (total - obj);

					$("#failedUpldCount").text(failed);
					//alert(per);
					$("#uploadperspan").text(parseFloat(per).toFixed());
					$('.uploadper').data('easyPieChart').update(per);

				},
				error : function(xhr) {
					//alert("error");
					$("#uploadedCount").text(0);
					$("#failedUpldCount").text(total);
					$("#uploadperspan").text(0);
					$('.uploadper').data('easyPieChart').update(0);
				}
			});

		}

		function setScrollAlert() {
			//alert('${inActiveCount}');
			if ('${inActiveCount}' > 0) {
				var countInactive = '${inActiveCount}';
				var meter = "meters are";
				if (countInactive == 1) {
					meter = "meter is";
				}
				$("#marqueeInfo").append(
						"&emsp;&emsp;&emsp;&emsp;" + countInactive + " "
								+ meter + " not communicating today.");
			}

			/* if('${level}'=="Corporate"){
				document.getElementById('locationHeader').style.display = 'show';
			}else{
				document.getElementById('locationHeader').style.display = 'none';
			} */

			if (failedUpload > 0) {
				document.getElementById('btnXmlUplad').style.display = 'show';
				$("#marqueeInfo").append(
						"&emsp;&emsp;&emsp;&emsp;Yesterday " + failedUpload
								+ " meter data uploading failed.");
			} else {
				document.getElementById('btnXmlUplad').style.display = 'none';
			}
			//GET DIAGNOSIS ALERTS
			/*$.ajax({
				url : "./getDiagnosisAlertsMDAS",
				data : "",
				type : "GET",
				dataType : "JSON",
				async : false,
				success : function(response) {
					//var array=JSON.parse(response);
					for (var i = 0; i < response.length; i++) {
						var obj = response[i];
						//var errorDetails=getErrorDetails(obj.diag_type,obj.status,obj);
						// if(errorDetails!=""){
						//	var matter="Modem has thrown "+errorDetails+" in Substation-"+obj.substation+" > Feeder-"+obj.fdrname+" ("+obj.tracked_time+") for the meter "+obj.meter_number;
						//	$("#marqueeInfo").append("&emsp;&emsp;&emsp;&emsp;"+matter);	
						//} 
						var etime = moment(obj[3])
								.format('DD-MM-YYYY HH:mm:ss');
						var matter = "For Meter No: " + obj[0]
								+ ", Account No : " + obj[4] + ", Event '"
								+ obj[2] + "' occurred in SubDivsion>" + obj[6]
								+ " at " + etime;
						$("#marqueeInfo").append(
								"&emsp;&emsp;&emsp;&emsp;" + matter);
					}
				}
			});*/

		}

		function getErrorDetails(diag, status, obj) {

			if (diag == "POWERSTS") {
				if (status == "FAIL") {
					return "'Power Outage'";
				} else if (status == "SUCC") {
					return "'Power Restore'";
				}
			} else if (diag == "METERSTS") {
				if (status == "NLEN") {
					return "'Not able to communicate with the meter because of cable problem'";
				} else if (status == "CRCF") {
					return "";
				} else if (status == "AARJ") {
					return "'Meter Rejected Communication'";
				} else if (status == "FRER") {
					return "";
				} else if (status == "FAIL") {
					return "";
				}
			} else if (diag == "METEWSTS") {
				return "'No Power Availble for the Modem'";
			}

			return diag;
		}

		function getLast30DaysActiveStatus() {
			var level = "${level}";
			var value = "${value}";
			$.ajax({
				url : "./getLastDaysActiveStatusMDAS?type="+level+"&value="+value,
				data : "",
				type : "GET",
				dataType : "json",
				async : false,
				success : function(response) {
					var activeArray = [];
					var nonactiveArray=[];
					var totalArray=[];
					var uploadArray = [];
					var dates = [];
					for (var i = 0; i < response.length; i++) {
						var columns = response[i];
						//alert(response);

						var dummyActive = []
						var dummyActive2=[]
						
						
						dummyActive.push(i);
						dummyActive.push(columns[1]);//commm.
						
						dummyActive2.push(columns[2]);//nonactive
						activeArray.push(dummyActive);
						nonactiveArray.push(dummyActive2);
				
						

						/* if (i % 2 != 0) {*/
						var dummyDate = [];
						//	dummyDate.push(i);
						dummyDate.push(columns[0]);
						dates.push(dummyDate);
						/* } */

					}
					//alert("------------------------------------------------------------\n"+uploadArray+"\n------------------------------------------------------------");

					var maxCount = 0;

					if (activeArray.length > maxCount) {
						maxCount = activeArray.length;
					}

					
				Highcharts.chart('chart_2', {
						
						chart : {
							type : 'column',
							width:'1150',
						},

						title : {
							text : 'Last 30 days Communication status'
						},
						

						legend : {
							align : 'center',
							verticalAlign : 'bottom',
							layout : 'horizontal'
						},
						colors : [ '#117d03', '#57b5e3' ],
						xAxis : {
							categories : dates,
							 
							
							labels : {
								x : -10,
								tickPixelInterval : 30,
								
							}
						},

						yAxis : {
							allowDecimals : false,
							title : { text : 'Count' },
							//categories: activeArray ,
							labels : { y : -0 },

							stackLabels: {
					        	enabled: true,
								rotation: -45,
					            style: {
					                /* fontWeight: 'bold',
					                color: ( // theme
					                    Highcharts.defaultOptions.title.style &&
					                    Highcharts.defaultOptions.title.style.color
					                ) || 'black' */
					            }
					        }
							
						}, tooltip: {
					        headerFormat: '<b>{point.x}</b><br/>',
					        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
						        
					    },

						plotOptions : {
							series : {
								dataLabels: {	
					                enabled: true,
					                rotation : -90
					            },
					            
								cursor : 'pointer',
								point : {
									events : {
										click : function() {
											//alert('Category: ' + this.category + ', value: ' + this.y+', name: '+this.series.name);
											//window.location.href = "./totalMeterDetailsMDAS?type=bardata&date="+this.category+"&name="+this.series.name+urlLavel;

										}
									}
								}
							},
							column: {
					            stacking: 'normal',
					            dataLabels: {
					                enabled: true,
					                rotation : -90
					            }
					        }

						
						},

						series : [ 
						
						{
							name : 'Communication Status',
							data :  activeArray,
						},{
							name : 'Non-Communication Status',
							data :  nonactiveArray,
						}

						],

						responsive : {
							rules : [ {
								condition : {
									maxWidth : 800
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
											y : -5,
											tickPixelInterval : 30,
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
			});

		}

		/* function getMappedMetersDetails() {
			$
					.ajax({
						url : "./getMappedMetersDetails",
						data : "",
						type : "GET",
						dataType : "json",
						async : false,
						success : function(response) {
							var activeArray = [];
							var dates = [];
							for (var i = 0; i < response.length; i++) {
								var columns = response[i];

								var dummyActive = []
								dummyActive.push(i);
								dummyActive.push(columns[1]);
								activeArray.push(dummyActive);
								var dummyDate = [];
								dummyDate.push(columns[0]);
								dates.push(dummyDate);

							}
							
							var maxCount = 0;

							if (activeArray.length > maxCount) {
								maxCount = activeArray.length;
							}

							Highcharts
									.chart(
											'chart_3',
											{
												chart : {
													type : 'column'
												},

												title : {
													text : 'Last 30 Days Mapped Meters'
												},

												legend : {
													align : 'center',
													verticalAlign : 'bottom',
													layout : 'horizontal'
												},
												colors : [ '#57b5e3', '#3cc051' ],
												xAxis : {
													categories : dates,
												},

												yAxis : {
													allowDecimals : false,
													title : {
														text : 'Count'
													},
													max : totalMappedMeters,
													ticks : 5,
													tickDecimals : 0,
													endOnTick : false
												},

												plotOptions : {
													series : {
														cursor : 'pointer',
														point : {
															events : {
																click : function() {
																	// alert('MapDate: ' + this.category + ', value: ' + this.y+', name: '+this.series.name);
																	getMeterDetailsforPopup(this.category);

																}
															}
														}
													}
												},

												series : [
														{
															name : 'Meters Mapped Status',
															data : activeArray,
														},

												],

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
					});

		} */

		function getInactiveModemsSubLevel(levelName) {
			var columnName = '${columnName}';
			levelName = levelName.replace(/\//g, '@@@');
			$
					.ajax({
						url : "./getInactiveModemsSubLevelMDAS/" + levelName
								+ "/" + columnName,
						data : "",
						type : "GET",
						dataType : "json",
						async : false,
						success : function(response) {
							var tableData = "";
							var j = 1;
							for (var i = 0; i < response.length; i++) {

								var res = response[i];
								var date = res.lastComm;
								if (date != "--") {
									date = new Date(res.lastComm);
									date = date.toUTCString();
									date = moment(date).format(
											'DD-MM-YYYY HH:mm:ss');
								} else {
									date = "Not Communicated";
								}
								tableData += "<tr> " + "<td>"
										+ j
										+ "</td> "
										+ "<td> "
										+ "<div class='important'></div> "
										+ "<a href='./modemDetailsInactiveMDAS?modem_sl_no="
										+ res.modem_sl_no
										+ "&mtrno="
										+ res.mtrno
										+ "&substation="
										+ res.substation
										+ "' style='color:blue;'>"
										+ res.modem_sl_no
										+ "</a> "
										+ "</td> "
										+ "<td><a onclick='return mtrDetails(\""
										+ (res.mtrno).trim()
										+ "\")' style='color:blue;'>"
										+ res.mtrno + "</a></td> " + "<td>"
										+ res.fdrname + "</td> " + "<td>"
										+ res.substation + "</td> " + "<td>"
										+ res.subdivision + "</td> " + "<td>"
										+ res.division + "</td> " + "<td>"
										+ res.district + "</td> " + "<td>"
										+ res.circle + "</td> " + "<td>" + date
										+ "</td> " + "</tr>";
								j++;
							}
							$('#inactiveModemDetailsTbl').html(tableData);
							loadSearchAndFilter1('inactiveTable');
						}
					});
		}

		function getMeterDetailsforPopup(date) {

			$('#formId').modal('show');
			$('#MappedMetersTitle').html("Mapped Meters Details : " + date);
			$.ajax({
				url : './mappedMetersDetailsPopUp',
				type : 'POST',
				data : {
					mappeddate : date
				},
				dataType : 'json',
				success : function(response) {
					//alert(response)
					var tableData = "";
					var counter = 1;
					for (var i = 0; i < response.length; i++) {

						var res = response[i];
						tableData += "<tr> " + "<td>" + counter + "</td> "
								+ "<td>" + res.mtrno + "</td> " + "<td>"
								+ res.mtrmake + "</td> " + "<td>"
								+ res.fdrcategory + "</td>" + "</tr>";
						counter++;
					}
					$('#formIdTable').dataTable().fnClearTable();
					$("#formIdTableBody").append(tableData);
					loadSearchAndFilter1('formIdTable');
				}

			});

		}

		function getNotUploadedModemsSubLevel(levelName) {
			var columnName = '${columnName}';
			levelName = levelName.replace(/\//g, '@@@');
			$
					.ajax({
						url : "./getNotUploadedModemsSubLevelMDAS/" + levelName
								+ "/" + columnName,
						data : "",
						type : "POST",
						dataType : "json",
						async : false,
						success : function(response) {

							var tableData = "";
							for (var i = 0; i < response.length; i++) {

								var res = response[i];

								tableData += "<tr> "
										+ "<td class='highlight'> "
										+ "<a onclick='return mtrDetails(\""
										+ (res.meter_number).trim()
										+ "\")' style='color:blue;'>"
										+ res.meter_number
										+ "</a> "
										+ "</td> "
										+ "<td>"
										+ res.fdrname
										+ "</td> "
										+ "<td>"
										+ res.substation
										+ "</td> "
										+ "<td>"
										+ res.fail_reason
										+ "</td> "
										+ "<td><button type='button' class='btn btn-primary' onclick='forceUploadXML(\""
										+ (res.meter_number).trim()
										+ "\"); return false;' >Force Upload</button></td> "
										+ "</tr>";
							}
							$('#failedUploadMtrDetailsTbl').html(tableData);

						}
					});
		}

		function forceUploadXML(meterNumber) {
			var d = new Date();
			d.setDate(d.getDate() - 1);
			var fileDate = moment(d).format('YYYY-MM-DD');
			var uploadUrl = "./createSingleXmlAndUpload/" + meterNumber + "/"
					+ fileDate; //UPLOAD SINGLE METER XML 
			$.ajax({
				url : uploadUrl,
				data : "",
				type : "GET",
				dataType : "text",
				async : false,
				success : function(response) {
					bootbox.alert(response);
				}
			});
		}

		function forceUploadXMLAll() {
			var d = new Date();
			d.setDate(d.getDate() - 1);
			var fileDate = moment(d).format('YYYY-MM-DD');

			var uploadUrl = "./createAllXmlAndUpload/" + fileDate;//UPLOAD ALL XML FILES

			$
					.ajax({
						url : uploadUrl,
						data : "",
						type : "GET",
						dataType : "text",
						async : true,
						beforeSend : function() {
							bootbox
									.alert("XML upload request has been sent.\nPlease refresh after a few seconds.");
						}

					});
		}

		/* function mtrDetails(mtrNo)
		 { 
		 window.location.href="./mtrNoDetails?mtrno=" + mtrNo;
		 } */
		function mtrDetails(mtrNo) {
			window.location.href = "./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
		}
		function powerSupplyGraphp() {
			window.location.href = "./powSupplyuStatsMDAS";
		}
		function viewOnMapSubstation() {
			window.location.href = "./mapSubstationMDAS";
		}

		function getHESMeterStatus(type, status, circle) {
			var hes_type = "";
			if(type == "GENUS")
				{
				hes_type = "GENUS";
				}
			else
				{
				hes_type = type;
				}
			
			$('#tabTitle').html("MDAS wise Summary Details for " + hes_type);
			$.ajax({
				url : "./getHESMeterStatus/" + hes_type + "/" + status + "/"
						+ circle,
				data : "",
				type : "GET",
				dataType : "json",
				async : false,
				success : function(response) {
					var tableData = "";
					var counter = 1;
					for (var i = 0; i < response.length; i++) {

						var res = response[i];
						var date = res.lastComm;
						if (date != "--") {
							date = new Date(res.lastComm);
							date = date.toUTCString();
							date = moment(date).format('DD-MM-YYYY HH:mm:ss');
						} else {
							date = "Not Communicated";
						}
						tableData += "<tr> " + "<td>" + counter + "</td> "
								+ "<td>" + res.zone + "</td> " + "<td>"
								+ res.circle + "</td> " + "<td>" + res.division
								+ "</td> " + "<td>" + res.subdivision
								+ "</td> " + "<td>" + res.substation + "</td> "
								+ "<td>" + res.fdrname
								+ "</td> " + "<td>" + res.fdrcode
								+ "</td> "
								
								+ "<td>" + res.town + "</td> " + "<td>"
								+ res.mtrno + "</td> " + "<td>" + date
								+ "</td>" + "</tr>";
						counter++;
					}
					$('#sample_1').dataTable().fnClearTable();
					$("#getHESDetailsTblBody").append(tableData);
					loadSearchAndFilter1('sample_1');

				}
			});
		}

		function getMeterTypeSummaryDetails(type, status) {
			$('#tabTitle').html("Meter Type Wise Summary Details for " + type);
			$.ajax({
				url : "./getMeterTypeSummaryDetails/" + type + "/" + status,
				data : "",
				type : "GET",
				dataType : "json",
				async : false,
				success : function(response) {
					var tableData = "";
					var counter = 1;
					for (var i = 0; i < response.length; i++) {

						var res = response[i];
						var date = res.lastComm;
						if (date != "--") {
							date = new Date(res.lastComm);
							date = date.toUTCString();
							date = moment(date).format('DD-MM-YYYY HH:mm:ss');
						} else {
							date = "Not Communicated";
						}
						tableData += "<tr> " + "<td>" + counter + "</td> "
								+ "<td>" + res.zone + "</td> " + "<td>"
								+ res.circle + "</td> " + "<td>" + res.division
								+ "</td> " + "<td>" + res.subdivision
								+ "</td> " + "<td>" + res.substation + "</td> "
								+ "<td>" + res.fdrname
								+ "</td> " + "<td>" + res.fdrcode
								+ "</td> "
								+ "<td>" + res.town + "</td> " + "<td>"
								+ res.mtrno + "</td> " + "<td>" + date
								+ "</td>"  + "</tr>";
						counter++;
					}
					$('#sample_1').dataTable().fnClearTable();
					$("#getHESDetailsTblBody").append(tableData);
					loadSearchAndFilter1('sample_1');

				}
			});
		}
		/* data :{
			type:type,
			status:status
			
		} */

		function getLosses(type, status) {

			$('#tabTitleLoss').html("Health wizard AT&C losses " + type);
			$.ajax({
				url : "./getLosses/" + type + "/" + status,
				data : "",
				type : "GET",
				dataType : "json",

				async : false,
				success : function(response) {

					var tableData = "";
					var counter = 1;
					for (var i = 0; i < response.length; i++) {
						var res = response[i];

						tableData += "<tr> " + "<td>" + counter + "</td> "
								+ "<td>" + res.zone + "</td> " + "<td>"
								+ res.circle + "</td> " + "<td>" + res.division
								+ "</td> " + "<td>" + res.subdivision
								+ "</td> " + "<td>" + res.substation + "</td> "
								+ "<td>" + res.town + "</td> " + "<td>"
								+ res.mtrno + "</td> " + "<td>"
								+ res.atc_loss_percent + "</td>" + "</tr>";
						counter++;

					}
					$('#getAtandcLoss').dataTable().fnClearTable();
					$("#getAtandcLossBody").append(tableData);
					loadSearchAndFilter1('getAtandcLoss');

				}
			});
		}

		function getDTLosses(type, status) {

			$('#tabTitleDTLoss').html("Health wizard AT&C losses " + type);
			$.ajax({
				url : "./getDTLosses/" + type + "/" + status,
				data : "",
				type : "GET",
				dataType : "json",

				async : false,
				success : function(response) {

					var tableData = "";
					var counter = 1;
					for (var i = 0; i < response.length; i++) {
						var res = response[i];
						tableData += "<tr> " + "<td>" + counter + "</td> "
								+ "<td>" + res.tpdt_id + "</td> " + "<td>"
								+ res.zone + "</td> " + "<td>" + res.circle
								+ "</td> " + "<td>" + res.division + "</td> "
								+ "<td>" + res.subdivision + "</td> " + "<td>"
								+ res.substation + "</td> "

								+ "<td>" + res.mtrno + "</td> " + "<td>"
								+ res.atc_loss_percent + "</td>" + "</tr>";
						counter++;

					}
					$('#getDtAtandcLoss').dataTable().fnClearTable();
					$("#getDtAtandcLossBody").append(tableData);
					loadSearchAndFilter1('getDtAtandcLoss');

				}
			});
		}
		function getUnmappedMeters() {
			var total = "${totalMeters}";
			total = parseInt(total);
			//alert();

			$.ajax({
				//url : "http://1.23.144.187:8081/bsmartjvvnl/sendingMeteSynchDataToAMI",
				url : "./getUnMappedMetersDashboard",
				type : "GET",
				dataType : "TEXT",
				async : true,
				success : function(response) {
					//alert(response);

					var obj = parseInt(response);
					$("#uploadedCount").text(obj);

					var per = (parseFloat(obj) / total) * 100;

					var failed = (total - obj);

					$("#failedUpldCount").text(failed);
					//alert(per);
					$("#uploadperspan").text(parseFloat(per).toFixed());
					$('.uploadper').data('easyPieChart').update(per);

				},
				error : function(xhr) {
					//alert("error");
					$("#uploadedCount").text(0);
					$("#failedUpldCount").text(total);
					$("#uploadperspan").text(0);
					$('.uploadper').data('easyPieChart').update(0);
				}
			});

		}

		function shownonIpds() {

			$.ajax({
				url : './getNonIpdsData',
				data : {

				},
				type : 'GET',
				dataType : 'json',
				async : true,
				success : function(response) {

					var html = "";

					for (var i = 0; i < response.length; i++) {

						var resp = response[i];

						html += "<tr>" + "<td >" + (i + 1) + "</td>" + "<td >"
								+ resp[0] + "</td>" + "<td >" + resp[1]
								+ "</td>" + "<td >" + resp[2] + "</td>"
								+ "<td >" + resp[3] + "</td>" + "<td >"
								+ resp[4] + "</td>" + "<td >" + resp[5]
								+ "</td>" + "<td >" + resp[6] + "</td>"
						html += " </tr>";
					}
					$('#sample_editable_9').dataTable().fnClearTable();
					$("#nonipdsDataBody").append(html);
					loadSearchAndFilter1('sample_editable_9');
				}

			});

		}

		//AT&C losses Feeder and DT

		function getFeederLess15Graph(reultType) {
			var billmonth = '201911'
			var period = '12';
			var townCode = '013';

			/* 	var fmonth ='201910'; 
				//alert("fmonth..."+fmonth);
				var tmonth ='201910';
				//alert("tmonth..."+tmonth);
				var circle = '201910';
				//alert("circles..."+circle) */

			if (billmonth == "") {
				bootbox.alert("Please Select Report Month");
				return false;
			}

			if (period == "") {
				bootbox.alert("Please Select Period");
				return false;
			}

			if (townCode == "") {
				bootbox.alert("Please Select Town");
				return false;
			}
			$('#imageee').show();
			var data2 = [];
			$.ajax({
				url : './getAtcLosses',
				type : 'POST',
				data : {
					billmonth : billmonth,
					period : period,
					townCode : townCode
				},
				dataType : 'json',
				success : function(response) {
					$('#imageee').hide();
					for (var i = 0; i < response.length; i++) {
						data2.push(response[i])
					}

					var chart = new CanvasJS.Chart("chartContainer1", {
						animationEnabled : true,

						title : {
							text : "AT&C"
						},
						dataPointWidth : 10,
						axisY : {
							gridColor : "rgba(1,77,101,.1)",
							includeZero : true,

							suffix : "%",

						},
						axisX : {
							interval : 1,
							title : "Feeder Name"

						},

						data : [ {
							type : "column",
							color : "#014D65",
							dataPoints : data2
						} ]
					});
					$('#chartModal').on('shown.bs.modal', function() {
						chart.render();
					});
				}
			});

		}
	</script>