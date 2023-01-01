<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="<c:url value='/highcharts/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<link href="resources/assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>" type="text/javascript"></script>


<script>
	var urlLavel = "";

	$(".page-content")
			.ready(
					function() {
						urlLavel = '${urlLavel}';
						
						Index.initMiniCharts();
						TableEditable.init();
						FormComponents.init();
						/* $("#MDASSideBarContents,#mdmDashId, #healthDashboard").addClass(
								'start active ,selected');
						$('#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected'); */

						$("#DashboardContent, #healthDashboard").addClass(
						'start active ,selected');
						$('#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
						.removeClass('start active ,selected');

						App.init();
						
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

<div class="page-content">


<div class="portlet box blue"
		style="margin-right: 0px; margin-left: 0px;">
		<div class="portlet-title blue">
			<div class="caption" style="font-size: 16px;">
				<i class="fa fa-bar-chart-o"></i>Communication Details
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>

			</div>
		</div>
		<div class="portlet-body">

			<div class="table-scrollable">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th
								style="text-align: center; max-width: 150px; min-width: 65px; white-space: normal; vertical-align: middle;"
								class="info">Circle</th>
							<!-- <th
								style="text-align: center; max-width: 150px; min-width: 65px; white-space: normal; vertical-align: middle;"
								class="info">Division</th>
							<th
								style="text-align: center; max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;"
								class="info">SubDivision</th>
							<th
								style="text-align: center; max-width: 150px; min-width: 120px; white-space: normal; vertical-align: middle;"
								class="info">Town</th> -->
							<th
								style="text-align: center; max-width: 150px; min-width: 65px; white-space: normal;"
								class="info">Total Meters</th>
							<!-- <th
								style="text-align: center; max-width: 150px; min-width: 65px; white-space: normal; vertical-align: middle;"
								class="success">Mapped Meters</th> -->
							<th
								style="text-align: center; max-width: 150px; min-width: 107px; white-space: normal;"
								class="warning">Total NonComm Meters</th>
							<th
								style="text-align: center; max-width: 150px; min-width: 107px; white-space: normal;"
								class="warning">NonComm from Last 24 Hrs</th>
							<th
								style="text-align: center; max-width: 150px; min-width: 107px; white-space: normal;"
								class="warning">NonComm from Last 5 Days</th>
							<!-- <th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal;" class="warning">Inactive from Last 10 Days</th>
							<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal;" class="danger">Inactive from Last 20 Days</th>
							<th style="text-align: center;max-width: 150px; min-width: 107px; white-space: normal;" class="danger">Inactive from Last 30 Days</th> -->
						</tr>
					</thead>
					<c:forEach var="element" items="${commReportList}">
						<tr>
							<td style="text-align: center;">
							<u onclick="getMeterDetailsStatus('${element[0]}'); return false;" data-toggle="modal" data-target="#commDetails" data-dismiss="modal" style="font-weight: bold; color: green; cursor: pointer;">${element[0]}</u></td>
							<%-- <td style="text-align: center;">${element[1]}</td>
							<td style="text-align: center;">${element[2]}</td>
							<td style="text-align: center;">${element[12]}</td> --%>
							<td style="text-align: center;">${element[1]}</td>
							<%-- <td style="text-align: center;">${element[5]}</td> --%>
							<td style="text-align: center;">${element[3]}</td>
							<td style="text-align: center;">${element[4]}</td>
							<td style="text-align: center;">${element[5]}</td>
							<%-- <td style="text-align: center;">${element[8]}</td>
								<td style="text-align: center;">${element[9]}</td>
								<td style="text-align: center;">${element[10]}</td> --%>

						</tr>
					</c:forEach>
					<tbody>


					</tbody>
				</table>
			</div>
		</div>
	</div>


	<c:if test="${sub_level eq 'circle'}">

		<div class="portlet box blue"
			style="margin-bottom: 0px; margin-top: 5px;">
			<div class="portlet-title blue">
				<div class="caption" style="font-size: 16px;">
					<i class="fa fa-bar-chart-o"></i>Circle Wise Statictics
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>

				</div>
			</div>
			<div class="portlet-body">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th rowspan="2" style="text-align: center;">S.No</th>
							<th rowspan="2" style="text-align: center;">Region</th>
							<th rowspan="2" style="text-align: center;">Circle</th>
							<!-- <th rowspan="2" style="text-align: center;">Town</th> -->
							<!-- <th style="text-align: center;">RAPDRP</th> -->
							<!-- <th rowspan="2" style="text-align: center;">IPDS</th> -->
							<th rowspan="2" style="text-align: center;">Total Meter</th>
							<!-- <th rowspan="2" style="text-align: center;">Mapped Meters</th> -->
							<!-- <th rowspan="2" style="text-align: center;">Inactive Meter</th> -->
							<!-- <th style="text-align: center;">DLMS</th> -->
							<!-- <th style="text-align: center;">NON-DLMS</th> -->
							<th rowspan="2" style="text-align: center;">Total Feeder Meter</th>
							<th colspan="2" style="text-align: center;">Feeder Meter</th>
							<th rowspan="2" style="text-align: center;">Boundary Meter</th>
							<th rowspan="2" style="text-align: center;">DT Meter</th>
							<!-- <th style="text-align: center;">HT</th>
										<th style="text-align: center;">LT</th> -->
						</tr>
						<tr>
							<th style="text-align: center;">IPDS</th>
							<th style="text-align: center;">NON-IPDS</th>
						</tr>

					</thead>
					<tbody>
						<c:set var="count" value="1" scope="page" />
						<c:forEach var="rnrr" items="${rapNonrap}">

							<tr>
								<td style="font-style: normal; text-align: center;">${count}</td>
								<td style="text-align: center;">${ rnrr[0]}</td>
								<%-- <td style="text-align: center;"><a  href="#" onclick="return displaysubdivwisecounts('${rnrr[0]}','${rnrr[1]}');" data-toggle="modal" data-target="#subdivvaluePopUp"  >${ rnrr[1]}</a></td> --%>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#circlewise"
									onclick="return getCircleWiseDetailsStatus('${ rnrr[0]}','${ rnrr[1]}');">${ rnrr[1]}</a></td>
								<%-- <td style="text-align: center;">${ rnrr[15]}</td> --%>
								<%-- <td style="text-align: center;"><a href="#"  data-toggle="modal"  data-target="#showStatData"  onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','RAPDRP');" > ${ rnrr[2]}</a></td> --%>
								<%-- <td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','IPDS');">
										${ rnrr[4]}</a></td> --%>
								<td style="text-align: center;">${ rnrr[5]}</td>
								<%-- <td style="text-align: center;">${ rnrr[6]}</td>
								<td style="text-align: center;">${ rnrr[7]}</td> --%>
								<%-- <td style="text-align: center;"><a  href="#" data-toggle="modal"  data-target="#showStatData"  onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','DLMS');"> ${ rnrr[8]}</a></td> --%>
								<%-- <td style="text-align: center;"><a  href="#" data-toggle="modal"  data-target="#showStatData"  onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','Non-DLMS');"> ${ rnrr[8]}</a></td> --%>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','FEEDER METER');">
										${ rnrr[11]}</a></td>
								<%--<td style="text-align: center;">${ rnrr[4]}</td> --%>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','IPDS');">
										${ rnrr[3]}</a></td>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','NON_IPDS');">
										${ rnrr[4]}</a></td>
								<%-- <td style="text-align: center;">${ rnrr[16]}</td> --%>
								<%-- <td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#nonipds"
									onclick="return shownonIpds('${ rnrr[0]}','${ rnrr[1]}','NON_IPDS');">
										${ rnrr[16]}</a></td> --%>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','BOUNDARY METER');">
										${ rnrr[12]}</a></td>
								<td style="text-align: center;"><a href="#"
									data-toggle="modal" data-target="#showStatData"
									onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','DT');">
										${ rnrr[13]}</a></td>
								<%-- <td  style="text-align: center;"><a  href="#" data-toggle="modal"  data-target="#showStatData"  onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','HT');"> ${ rnrr[12]}</a></td>
							<td  style="text-align: center;"><a  href="#" data-toggle="modal"  data-target="#showStatData"  onclick="return showStatDataFn('${ rnrr[0]}','${ rnrr[1]}','LT');"> ${ rnrr[13]}</a></td>
							 --%>

							</tr>

							<c:set var="count" value="${count + 1}" scope="page" />

						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>

		<br>

	</c:if>





	<c:if test="${sub_level eq 'circle'}">
	<c:if test="${officeType eq 'corporate'}">
		<!-- onclick="getFeederLess15Graph('FeederMore15'); return false;" -->
		<div class="portlet box blue"
			style="margin-bottom: 0px; margin-top: 5px;">
			<div class="portlet-title blue">
				<div class="caption" style="font-size: 16px;">
					<i class="fa fa-list-ol"></i>Health wizard AT&C losses
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="row">
					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">Feeder
										AT&C Losses</span>
								</div>

							</div>
							<div class="portlet-body">
								<div class="row">
									<div class="col-md-4" style="margin-top: -6px;">
										<a href="javascript:;" class="btn default red-stripe"
											onclick="getLosses('FEEDER METER','FDRLSR15'); return false;"
											data-toggle="modal" data-target="#stack5"> Total No of
											Feeder less than 15% AT&C loss: <b>${feederLess15}</b>
										</a>
									</div>


								</div>
							</div>
							<div class="portlet-body">
								<div class="row">
									<div class="col-md-4" style="margin-top: -6px;">
										<a href="javascript:;" class="btn default red-stripe"
											onclick="getLosses('FEEDER METER','FDRGTR15'); return false;"
											data-toggle="modal" data-target="#stack5"> Total No of
											Feeder greater than 15% AT&C loss : <b>${feederGrt15}</b>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">DT
										AT&C Losses</span>
								</div>

							</div>
							<div class="portlet-body">
								<div class="row">
									<div class="col-md-4" style="margin-top: -6px;">
										<a href="javascript:;" class="btn default red-stripe"
											onclick="getDTLosses('DT','DTLSR15'); return false;"
											data-toggle="modal" data-target="#stack6"> Total No of DT
											less than 15% AT&C loss: <b>${DtLessGrt15}</b>
										</a>
									</div>
								</div>
							</div>
							<div class="portlet-body">
								<div class="row">
									<div class="col-md-4" style="margin-top: -6px;">
										<a href="javascript:;" class="btn default red-stripe"
											onclick="getDTLosses('DT','DTGTR15'); return false;"
											data-toggle="modal" data-target="#stack6"> Total No of DT
											greater than 15% AT&C loss: <b>${DtGrt15}</b>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	</c:if>

	<c:if test="${sub_level eq 'circle'}">
		<c:if test="${officeType eq 'corporate'}">
		<!-- onclick="getFeederLess15Graph('FeederMore15'); return false;" -->
		<div class="portlet box blue"
			style="margin-bottom: 0px; margin-top: 5px;">
			<div class="portlet-title blue">
				<div class="caption" style="font-size: 16px;">
					<i class="fa fa-list-ol"></i>Best and Worst Performing Feeder List
					(AT&C)
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>

			<div class="portlet-body">
				<div class="row">
					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">Best
										Performing Feeders</span>
								</div>
							</div>
							<div class="portlet-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th style="text-align: center;">S.No</th>
											<th style="text-align: center;">Feeder Name</th>
											<th style="text-align: center;">AT&C Loss (%)</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="worstDt" items="${bestFeederATC}">
											<tr>
												<td style="font-style: normal; text-align: center;">${count}</td>
												<td style="text-align: center;">${worstDt[0]}</td>
												<td style="text-align: center;">${worstDt[1]}</td>
											</tr>
											<c:set var="count" value="${count + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">Worst
										Performing Feeders</span>
								</div>

							</div>
							<div class="portlet-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th style="text-align: center;">S.No</th>
											<th style="text-align: center;">Feeder Name</th>
											<th style="text-align: center;">AT&C Loss (%)</th>


										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="worstDt" items="${worstFeederATC}">

											<tr>
												<td style="font-style: normal; text-align: center;">${count}</td>
												<td style="text-align: center;">${worstDt[0]}</td>
												<td style="text-align: center;">${worstDt[1]}</td>

											</tr>
											<c:set var="count" value="${count + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
		</c:if>
	


	<c:if test="${sub_level eq 'circle'}">
	<c:if test="${officeType eq 'corporate'}">
		<!-- onclick="getFeederLess15Graph('FeederMore15'); return false;" -->
		<div class="portlet box blue"
			style="margin-bottom: 0px; margin-top: 5px;">
			<div class="portlet-title blue">
				<div class="caption" style="font-size: 16px;">
					<i class="fa fa-list-ol"></i>Best and Worst Performing DT List
					(AT&C)
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>

			<div class="portlet-body">
				<div class="row">
					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">Best
										Performing DT</span>
								</div>
							</div>
							<div class="portlet-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th style="text-align: center;">S.No</th>
											<th style="text-align: center;">DT Name</th>
											<th style="text-align: center;">AT&C Loss (%)</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="worstDt" items="${bestDTatc}">
											<tr>
												<td style="font-style: normal; text-align: center;">${count}</td>
												<td style="text-align: center;">${worstDt[0]}</td>
												<td style="text-align: center;">${worstDt[1]}</td>
											</tr>
											<c:set var="count" value="${count + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cursor font-dark hide"></i> <span
										class="caption-subject font-dark bold uppercase">Worst
										Performing DT</span>
								</div>

							</div>
							<div class="portlet-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th style="text-align: center;">S.No</th>
											<th style="text-align: center;">DT Name</th>
											<th style="text-align: center;">AT&C Loss (%)</th>


										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="worstDt" items="${worstDTatc}">

											<tr>
												<td style="font-style: normal; text-align: center;">${count}</td>
												<td style="text-align: center;">${worstDt[0]}</td>
												<td style="text-align: center;">${worstDt[1]}</td>

											</tr>
											<c:set var="count" value="${count + 1}" scope="page" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	</c:if>

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

	<div class="modal fade" id="showStatData" tabindex="-1" role="basic"
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
							id="showStatDataTitle"></font>
					</h4>
				</div>

				<div class="modal-body">

					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport6"
									onclick="tableToXlxs('sample_editable_1','Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
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
						<tbody id="showStatDataBody">
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
	
	<div class="modal fade" id="showStatData" tabindex="-1" role="basic"
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
							id="showStatDataTitle"></font>
					</h4>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
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
						<tbody id="showStatDataBody">
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
<!-- Kesav -->
	<div class="modal fade" id="showtownStatData" tabindex="-1" role="basic"
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
							id="showtownStatDataTitle"></font>
					</h4>
				</div>
				<div class="modal-body">
				
					 <div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport6"
									onclick="tableToXlxs('sample_towneditable_1','Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> 
					
					
					<table class="table table-striped table-hover table-bordered"
						id="sample_towneditable_1">
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
						<tbody id="showtownStatDataBody">
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
		
	
	<div class="modal fade" id="commDetails" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;" id="tabCommTitle"></font>
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport6"
									onclick="tableToXlxs('meterDetailsTbl','Comm Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="meterDetailsTbl"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>Town</th>
								<th>Total Meters</th>
								<th>Total NonComm Meters</th>
								<th>NonComm from Last 24 Hrs</th>
								<th>NonComm from Last 5 Days</th>
							</tr>
						</thead>
						<tbody id="getMeterDetailsTblBody">
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
	
	<div class="modal fade" id="circlewise" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #4b8cf8;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title">
						<font style="font-weight: bold; color: white;" id="tabcirclewiseTitle"></font>
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport6"
									onclick="tableToXlxs('circlewiseDetailsTbl','Circle Wise Details');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="circlewiseDetailsTbl"
						class="table table-striped table-hover table-bordered">
						<thead>
						<tr>
							<th rowspan="2" style="text-align: center;">S.No</th>
							<th rowspan="2" style="text-align: center;">Region</th>
							<th rowspan="2" style="text-align: center;">Circle</th>
							<th rowspan="2" style="text-align: center;">Town</th>
							<th rowspan="2" style="text-align: center;">Total Meter</th>
							<th rowspan="2" style="text-align: center;">Total Feeder Meter</th>
							<th colspan="2" style="text-align: center;">Feeder Meter</th>
							<th rowspan="2" style="text-align: center;">Boundary Meter</th>
							<th rowspan="2" style="text-align: center;">DT Meter</th>	
						</tr>
						<tr>
							<th style="text-align: center;">IPDS</th>
							<th style="text-align: center;">NON-IPDS</th>
						</tr>

					</thead>
						<tbody id="getCirWiseTblBody">
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
	
	

</div>

<script type="text/javascript">
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

					tableData += "<tr> " 
							+ "<td>" + counter + "</td> " 
							+ "<td>" + res.zone + "</td> " 
							+ "<td>" + res.circle+ "</td> " 
							+ "<td>" + res.division + "</td> "
							+ "<td>" + res.subdivision + "</td> " 
							+ "<td>" + res.substation + "</td> " 
							+ "<td>" + res.town + "</td> " 
							+ "<td>" + res.mtrno + "</td> " 
							+ "<td>" + res.atc_loss_percent + "</td>" 
							+ "</tr>";
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
					tableData += "<tr> " 
							+ "<td>" + counter + "</td> " 
							+ "<td>" + res.tpdt_id + "</td> " 
							+ "<td>" + res.zone + "</td> " 
							+ "<td>" + res.circle + "</td> "
							+ "<td>" + res.division + "</td> " 
							+ "<td>" + res.subdivision + "</td> " 
							+ "<td>" + res.substation + "</td> "
							+ "<td>" + res.mtrno + "</td> " 
							+ "<td>" + res.atc_loss_percent + "</td>" 
							+ "</tr>";
					counter++;

				}
				$('#getDtAtandcLoss').dataTable().fnClearTable();
				$("#getDtAtandcLossBody").append(tableData);
				loadSearchAndFilter1('getDtAtandcLoss');

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

					html += "<tr>" 
							+ "<td >" + (i + 1) + "</td>" 
							+ "<td >" + resp[0] + "</td>" 
							+ "<td >" + resp[1] + "</td>"
							+ "<td >" + resp[2] + "</td>" 
							+ "<td >" + resp[3] + "</td>" 
							+ "<td >" + resp[4] + "</td>" 
							+ "<td >" + resp[5] + "</td>" 
							+ "<td >" + resp[6] + "</td>"
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
<script>

function showStatDataFn(zone, circle, type) {

	$('#showStatDataTitle').text(type);
	//alert(zone+circle+type);
	$.ajax({
		url : './getDashBoardStatData',
		data : {
			zone : zone,
			circle : circle,		
			type : type

		},
		type : 'GET',
		dataType : 'json',
		async : true,
		success : function(response) {
			var html = "";

			for (var i = 0; i < response.length; i++) {
				//alert(response[i]);
				var resp = response[i];

				html += "<tr>" 
						+ "<td >" + (i + 1) + "</td>" 
						+ "<td >" + resp[0] + "</td>" 
						+ "<td >" + resp[1] + "</td>" 
						+ "<td >" + resp[2] + "</td>"
						+ "<td >" + resp[3] + "</td>" 
						+ "<td >" + resp[4] + "</td>" 
						+ "<td >" + resp[6] + "</td>" 
						+ "<td >" + resp[5] + "</td>";
				html += " </tr>";
			}
			$('#sample_editable_1').dataTable().fnClearTable();
			$("#showStatDataBody").append(html);
			loadSearchAndFilter1('sample_editable_1');
		}

	});

}

function showStatDataTownFn(zone, circle, town, type) {
	//$('#circlewise').hide();
	$('#circlewise').modal('hide');
	$('#showtownStatDataTitle').text(type);
	
	 $.ajax({
		url : './getDashBoardTownbasedStatData',
		data : {
			zone : zone,
			circle : circle,
			town : town,
			type : type

		},
		type : 'GET',
		dataType : 'json',
		async : true,
		success : function(response) {
			var html = "";
			loadSearchAndFilter1('sample_towneditable_1');
			for (var i = 0; i < response.length; i++) {
				//alert(response[i]);
				var resp = response[i];

				html += "<tr>" 
					+ "<td >" + (i + 1) + "</td>" 
					+ "<td >" + resp[0] + "</td>" 
					+ "<td >" + resp[1] + "</td>" 
					+ "<td >" + resp[2] + "</td>"
					+ "<td >" + resp[3] + "</td>" 
					+ "<td >" + resp[4] + "</td>" 
					+ "<td >" + resp[6] + "</td>" 
					+ "<td >" + resp[5] + "</td>";
			html += " </tr>";
			}
			$('#sample_towneditable_1').dataTable().fnClearTable();
			$("#showtownStatDataBody").append(html);
			loadSearchAndFilter1('sample_towneditable_1');
		}

	}); 

}


	function getMeterDetailsStatus(circle) {

			$('#tabCommTitle').html("Communication Details : " + circle);

			$('#imageee').show();
			$.ajax({
				url : "./getMtrDetailsStatusBasedCir",
				data : {
					circle : circle
				},
				type : "POST",
				dataType : "json",
				async : false,
				success : function(response) {
					loadSearchAndFilter1('meterDetailsTbl');
					var html = "";
					$('#imageee').hide();
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html += "<tr>" 
								+ "<td >" + (i + 1) + "</td>" 
								+ "<td >" + resp[0] + "</td>" 
								+ "<td >" + resp[1] + "</td>" 
								+ "<td >" + resp[2] + "</td>"
								+ "<td >" + resp[3]+"-"+resp[12] + "</td>" 
								+ "<td >" + resp[5] + "</td>" 
								+ "<td >" + resp[6] + "</td>" 
								+ "<td >" + resp[7] + "</td>"
								+ "<td >" + resp[8] + "</td>"
						html += " </tr>";
					}
					$('#meterDetailsTbl').dataTable().fnClearTable();
					$("#getMeterDetailsTblBody").append(html);
					loadSearchAndFilter1('meterDetailsTbl');
				},
				error : function(xhr) {
					$('#imageee').hide();
					bootbox.alert("Some error occured!!");
					
					$('#meterDetailsTbl').dataTable().fnClearTable();
					loadSearchAndFilter1('meterDetailsTbl');
				}

			}); 

		}

	function getCircleWiseDetailsStatus(zone, circle) {

		$('#tabcirclewiseTitle').html("Circle Wise Statictics Details : " + circle);

		$('#imageee').show();
		$.ajax({
			url : "./getCircleWiseDetailsStatus",
			timeout:1000,
			data : {
				zone : zone,
				circle : circle
			},
			type : "POST",
			dataType : "json",
			async : false,
			success : function(response) {
				loadSearchAndFilter1('circlewiseDetailsTbl');
				var html = "";
				$('#imageee').hide();
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					html += "<tr>" 
							+ "<td style='text-align: center;'>" + (i + 1) + "</td>" 
							+ "<td style='text-align: center;'>" + resp[0] + "</td>" 
							+ "<td style='text-align: center;'>" + resp[1] + "</td>" 
							+ "<td style='text-align: center;'>" + resp[2] +"-"+resp[15] + "</td>"
							+ "<td style='text-align: center;'>" + resp[5] + "</td>" 
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' data-target='#showtownStatData' onclick='return showStatDataTownFn(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ resp[15]+ "\",\""+ 'FEEDER METER'+ "\");'>" + resp[10] + "</a></td>"
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' data-target='#showtownStatData' onclick='return showStatDataTownFn(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ resp[15]+ "\",\""+'IPDS'+ "\");'>" + resp[4] + "</a></td>" 
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' data-target='#showtownStatData' onclick='return showStatDataTownFn(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ resp[15]+ "\",\""+'NON_IPDS'+ "\");'>" + resp[16] + "</a></td>" 
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' data-target='#showtownStatData' onclick='return showStatDataTownFn(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ resp[15]+ "\",\""+'BOUNDARY METER'+ "\");'>" + resp[11] + "</a></td>"
							+ "<td style='text-align: center;'><a href='#' data-toggle='modal' data-target='#showtownStatData' onclick='return showStatDataTownFn(\""+ resp[0]+ "\",\""+ resp[1]+ "\",\""+ resp[15]+ "\",\""+'DT'+ "\");'>" + resp[12] + "</a></td>";
					html += " </tr>";
				}
				$('#circlewiseDetailsTbl').dataTable().fnClearTable();
				$("#getCirWiseTblBody").append(html);
				loadSearchAndFilter1('circlewiseDetailsTbl');
			},
			error : function(xhr) {
				$('#imageee').hide();
				bootbox.alert("Some error occured!!");
				
				$('#circlewiseDetailsTbl').dataTable().fnClearTable();
				loadSearchAndFilter1('circlewiseDetailsTbl');
			}

		}); 

	}

</script>