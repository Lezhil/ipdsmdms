<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>"
	type="text/javascript"></script>
<style>
.table-toolbar {
	margin-bottom: 4px
}

.tabbable-custom.tabbable-full-width .nav-tabs>li>a {
	color: #424242;
	font-size: 14px !important;
	padding: 9px 6px;
}
</style>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>"
	type="text/javascript"></script>



<div class="page-content">

	<div class="portlet box blue">
		<c:if test="${not empty msg}">
			<div class="alert alert-danger display-show">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${msg}</span>
			</div>
		</c:if>

		<!-- /.col-md-6 -->

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>360 Degree Meter Data View

			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>


		<div class="portlet-body">
			<jsp:include page="locationFilter.jsp" />
			<div class="row" style="margin-left: -1px;">
				<div class="mt-radio-inline" style="display: none;">
					<label class="mt-radio"> <input type="radio"
						name="optionsRadios" id="meterno_radio" value="meterno" checked
						onchange="setRadioVal(this.value)"> Meter No <span></span>
					</label> <label class="mt-radio"> <input type="radio"
						name="optionsRadios" id="kno_radio" value="kno"
						onchange="setRadioVal(this.value)"> K No <span></span>
					</label>
				</div>
				

				<%--  <form> --%>
				<div class="row" style="margin-left: -1px;">

					<div class="col-md-3">
						<strong>MeterNo:</strong>
						<div id="mtrTd" class="form-group">
							<select class="form-control select2me" id="meterNum"
								name="meterNum">
								<option value='All'>ALL</option>
								<c:forEach items="${meterNum}" var="elements">
									<option value="${elements}">${elements}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							 <strong>From Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									name="fromDate" id="fromDate"
									placeholder="Select Date"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					
					
					
					<div class="col-md-3">
						<div class="form-group">
							 <strong>To Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									placeholder="Select Date"  name="toDate" id="toDate"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>

				</div>

				<button type="button" id="dataview" class="btn yellow"
					style="margin-top: 20px; margin-left: 550px;"
					onclick="checkForm();">
					<b>View</b>
				</button>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>	

				<%-- </form>  --%>
			</div>
			
			<div>
		
					
					
			</table>
													
			</div>

			<div class="modal-body">
			

				<div class="tabbable tabbable-custom tabbable-full-width">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#tab_1_2" data-toggle="tab">Meter
								Details</a></li>
						<c:if test="${projectName eq 'TNEB'}">
							<li style="display: none"><a href="#tab_1_10"
								onclick="return namePlateDetails();" data-toggle="tab">Name
									Plate Details</a></li>
						</c:if>
						<c:if test="${projectName eq 'SPDCL'}">
							<li><a href="#tab_1_10" onclick="return namePlateDetails();"
								data-toggle="tab">Name Plate Details</a></li>
						</c:if>

						<!-- 
							<li><a  href="#tab_1_10" onclick="return namePlateDetails();"
							data-toggle="tab">Name Plate Details</a></li> -->

						<li><a href="#tab_1_4" onclick="return instansDetails();"
							data-toggle="tab">Instantaneous</a></li>
						<li><a href="#tab_1_8" onclick="return billHistoryDetails();"
							data-toggle="tab">Energy History</a></li>
						<li><a href="#tab_1_6" onclick="return loadSurveyDetails();"
							data-toggle="tab">Load Survey</a></li>
						<li><a href="#tab_1_3" onclick="return eventDetails();"
							data-toggle="tab">Events Details</a></li>
						<!-- <li><a href="#tab_1_5" data-toggle="tab">Analysis Details</a></li> -->
						<li><a href="#tab_1_7"
							onclick="return dailyLoadSurveyDetails();" data-toggle="tab">Daily
								Parameters</a></li>
						<li><a href="#"   onclick="return getGraphs();">Graphs</a></li>

						<c:if test="${projectName eq 'TNEB'}">
							<li style="display: none"><a href="#tab_1_11"
								onclick="return transactionData();" data-toggle="tab">Transaction
									Data</a></li>
						</c:if>
						<c:if test="${projectName eq 'SPDCL'}">
							<li><a href="#tab_1_11" onclick="return transactionData();"
								data-toggle="tab">Transaction Data</a></li>
						</c:if>
						<!--<li><a href="#tab_1_12" onclick="return dailyMinMax();" data-toggle="tab">Daily Min Max Avg Data</a></li> 
				    	
						 <li><a href="#tab_1_8" data-toggle="tab">Graph Details</a></li> -->

					</ul>
					<div class="tab-content">

						<!--tab_1_2-->
						<div class="tab-pane active" id="tab_1_2">

							<div class="box">

								<div id="tab_1-1" class="tab-pane active">
									<form role="form" class="form-horizontal" action=" "
										method="post">
										<div class="form-body" >
											<div class="table-responsive" >
												<h4>
													<b>Meter Details</b>
												</h4>
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv2">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																<!-- <li><a href="#" id="print"
																	onclick="exportPDF('consumerDetails','ConsumerDetails')">Export
																		to PDF</a></li>  -->
																<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('consumerDetails', 'MeterDetails')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
												</div>
												<table class="table table-striped table-bordered"
													id="consumerDetails">
													<tbody id="consumdetid">
														<%-- <c:forEach var="element" items="${mtrFdrList}"> --%>
														<tr>
															<th>Circle</th>
															<td id="cirid"></td>

															<%-- <c:when test="${element.fdrcategory eq 'DT'}"> --%>
															<th id="locationName">Feeder Name</th>
															<td id="locationid"></td>
															<%-- </c:when> --%>
															<%-- <c:otherwise> --%>
															<!-- <th id="fdrnameid" hidden="true">FEEDER Name</th>
															<td id="fdrid"></td> -->
															<%-- </c:otherwise> --%>

														</tr>
														<tr>
															<th>Division</th>
															<td id="divid"></td>
															<th>Meter No</th>
															<td id="mtrid"></td>
															<%-- <td><a href="./viewFeederMeterInfoMDAS?mtrno=${element.mtrno}" style="color:blue;">${element.mtrno}</a></td> --%>
														</tr>
														<tr>
															<th>SubDivision</th>
															<%-- <td>${element.subdivision}
																</td> --%>
															<td id="subdivid"></td>
															<th>Meter Type</th>
															<td id="mtrtypeid"></td>
														</tr>

														<tr>
															<th>Town</th>
															<td id="townid"></td>
															<th>Phase</th>
															<td id="phaseid"></td>
														</tr>
														
														<tr>
															<th>DT Name</th>
															<td id="dtid"></td>
															<!-- <th>Section</th>
															<td id="sectionid"></td> -->
															
														</tr>
														
														<%-- </c:forEach> --%>
													</tbody>
												</table>

												<h4>
													<br> <b>Meter Communication Details</b></br>
												</h4>
												<table class="table table-striped table-bordered"
													id="mtrCommDet">
													<tbody id="mtrCommDetails">
														<%-- <c:forEach var="element" items="${comList}"> --%>
														<tr>
															<th>First Communicated on</th>
															<td id="crtym"></td>
															<th>Last Communicated on</th>
															<td id="lastcomm"></td>
															<th>No of Days Communicated till Today</th>
															<td id="commudays"></td>
													
														</tr>

														<%-- </c:forEach> --%>
													</tbody>

												</table>

											</div>
										</div>
									</form>
								</div>

							</div>
						</div>
						<div class="tab-pane" id="tab_1_3">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">

											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 2px;">
														<div id="excelExportDiv3">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																 <!-- <li><a href="#" id=""
																	onclick="exportEvtDetailsPDF('sample_2')">Export to PDF</a></li> --> 
																
																<li><a href="#" id="excelExport3"
																	onclick="tableToExcel3('sample_2', 'Event Details')">Export
																		to Excel</a></li>

															</ul>
														</div>
													</div>
												</div>
												
												<table class="table table-striped table-hover table-bordered" id="sample6">
												
												<thead>
														<tr>
																<!-- <th>Section</th -->
																<th>Feeder name</th>
																<th>Dtname</th>
																<th>DT_Capacity</th>
																<th>MeterNumber</th>
														</tr>
												</thead>
														<tbody id="updtevent">
														
														</tbody>												
												
												
												</table>
												
												
												
												<table
													class="table table-striped table-hover table-bordered"
													id="sample_2">
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th>Event Time</th>
															<th>Event Code</th>
															<th>Event Desc</th>
															<th>Duration</th>
															<th>Vr</th>
															<th>Vy</th>
															<th>Vb</th>
															<th>Ir</th>
															<th>Iy</th>
															<th>Ib</th>
															<th>PFr</th>
															<th>PFy</th>
															<th>PFb</th>
															<th>kWh</th>
														</tr>
													</thead>
													<tbody id="eventTR">
													</tbody>
												</table>
											</div>


										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab_1_4">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="table-toolbar">
														<div class="btn-group pull-right" style="margin-top: 1px;">
															<div id="excelExportDiv1">
																<button class="btn dropdown-toggle"
																	data-toggle="dropdown">
																	Tools <i class="fa fa-angle-down"></i>
																</button>
																<ul class="dropdown-menu pull-right">
																	<!-- <li><a href="#" id="print"
																	onclick="exportPDF('sample_3','Instantaneous Details')">Export
																		to PDF</a></li> -->
																	<li><a href="#" id="excelExport1"
																		onclick="tableToExcel1('sample_3id', 'Instantaneous Details')">Export
																			to Excel</a></li>
																</ul>
															</div>
														</div>
													</div>
													<div id="sample_3id">
											
														
													<table class="table table-striped table-hover table-bordered">
														
													
													
														<tbody>
															<tr>
																<th>Reading Time : </th><span><td id="submittername" style="min-width: 120px;"></td></span>
																<th>Feeder name: </th><span><td id="fdr" style="min-width: 120px;"></td></span>
																<!-- <th>Section: </th><span><td id="section" style="min-width: 120px;"></td></span> -->
																<th>Dtname:</th><span><td id="dtname" style="min-width: 120px;"></td></span>			
																 <th>Dtcapacity:</th><span><td id="dtcapacity" style="min-width: 120px;"></td></span> 
																
															</tr>
														</tbody>
															
															
															
													</table>
													
													<br></br>
													
													<table
														class="table table-striped table-hover table-bordered"
														id="sample_3">
														<tbody>
															<tr>
																<th>kWh</th>
																<td id="kwh_imp" style="min-width: 120px;"></td>
																<th>kVAh</th>
																<td id="kvah" style="min-width: 120px;"></td>
																<th>kVA</th>
																<td id="kva" style="min-width: 120px;"></td>
															</tr>
															<tr>
																<th>kWh (Export)</th>
																<td id="kwh_export"></td>
																<th>kVAh (Export)</th>
																<td id="kvah_export">
																<th>Phase Sequence</th>
																<td id="phase_seq"></td>
															</tr>
															<c:choose>
																<c:when test="${phase eq '1'}">
																	<tr>
																		<th>Phase Current (A)</th>
																		<td id="iph"></td>
																		<th>Neutral Current (A)</th>
																		<td id="inu"></td>
																		<th>Voltage (V)</th>
																		<td id="vavg"></td>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr>
																		<th>Ir Angle</th>
																		<td id="ir_angle"></td>
																		<th>Iy Angle</th>
																		<td id="iy_angle"></td>
																		<th>Ib Angle</th>
																		<td id="ib_angle"></td>
																	</tr>
																	<tr>
																		<th>Ir</th>
																		<td id="ir"></td>
																		<th>Iy</th>
																		<td id="iy"></td>
																		<th>Ib</th>
																		<td id="ib"></td>
																	</tr>
																	<tr>
																		<th>Vr</th>
																		<td id="vr"></td>
																		<th>Vy</th>
																		<td id="vy"></td>
																		<th>Vb</th>
																		<td id="vb"></td>
																	</tr>
																	<tr>
																		<th>Vr Angle</th>
																		<td id="vr_angle"></td>
																		<th>Vy Angle</th>
																		<td id="vy_angle"></td>
																		<th>Vb Angle</th>
																		<td id="vb_angle"></td>
																	</tr>
																	<tr>
																		<th>PFr</th>
																		<td id="pfr"></td>
																		<th>PFy</th>
																		<td id="pfy"></td>
																		<th>PFb</th>
																		<td id="pfb"></td>
																	</tr>
																</c:otherwise>
															</c:choose>

															<tr>
																<th>PF</th>
																<td id="pf_t"></td>
																<th>Frequency (Hz)</th>
																<td id="frequency"></td>
																<th>Power kW</th>
																<td id="p_kw"></td>
															</tr>
															<tr>
																<th>kvar</th>
																<td id="kvar"></td>
																<th>kVArh Lag</th>
																<td id="kvar_lag">
																<th>kVArh Lead</th>
																<td id="kvar_lead"></td>
																</td>
															</tr>
															<tr>
																<th>Power off Count</th>
																<td id="power_off_count"></td>
																<th>Power off Duration (m)</th>
																<td id="power_off_duration"></td>
																<th>Tamper Count</th>
																<td id="tamper_count">
															</tr>
														</tbody>
														<!-- <tbody id="instantsTR">

														</tbody> -->
													</table>
</div>


												</div>

												<p>&nbsp;</p>
												<p>&nbsp;</p>
												<p>&nbsp;</p>

												<div class="table-toolbar">
													<div class="table-toolbar">
														<div class="btn-group pull-right" style="margin-top: 1px;">
															<div id="excelExportDiv9">
																<button class="btn dropdown-toggle"
																	data-toggle="dropdown">
																	Tools <i class="fa fa-angle-down"></i>
																</button>
																<ul class="dropdown-menu pull-right">
																	<!-- <li><a href="#" id="print"
																	onclick="exportInstantaneous2PDF()">Export
																		to PDF</a></li> -->
																	<li><a href="#" id="excelExport9" 
																		onclick="tableToXlxs('sample_8', 'Instantaneous Details')">Export
																			to Excel</a></li>
																</ul>
															</div>
														</div>
													</div>

													<table
														class="table table-striped table-hover table-bordered"
														id="sample_8">
														<thead>
															<tr>
																<th>S.No</th>
																<th>Meter No</th>
																<th>Read Time</th>
																<th>kWh</th>
																<th>kVAh</th>
																<th>kVA</th>
																<th>Ir</th>
																<th>Iy</th>
																<th>Ib</th>
																<th>Vr</th>
																<th>Vy</th>
																<th>Vb</th>
																<th>PFr</th>
																<th>PFy</th>
																<th>PFb</th>
																<th>PF</th>
								
																<th>Frequency</th>
																<th>kWh (Export)</th>
																<th>kVAh (Export)</th>
																<th>Power(kW)</th>
																<th>kVArh Lag</th>
																<th>kVArh Lead</th>
																<th>Power off Count</th>
																<th>Power off Duration</th>
																<th>Tamper Count</th>
																<th>Phase Sequence</th>
																<th>Ir Angle</th>
																<th>Iy Angle</th>
																<th>Ib Angle</th>
																<th>Vr Angle</th>
																<th>Vy Angle</th>
																<th>Vb Angle</th>
																<th>Kvar</th>


															</tr>
														</thead>
														<tbody id="instantsTR">

														</tbody>
													</table>


												</div>

											</div>
										</form>
									</div>
								</div>
							</div>
						</div>


						<div class="tab-pane" id="tab_1_6">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv4">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																<!-- <li><a href="#" id=""
																	onclick="exportLoadSurveyPDF()">Export
																		to PDF</a></li> -->
																 <li><a href="#" id="excelExport4"
																	onclick="excelLoadSurvey()">Export
																		to Excel</a></li> 
																<!-- <li><a href="#" id="excelExport4"
																	onclick="tableToExcel4('sample_4', 'Load Survey Details')">Export
																		to Excel</a></li> -->
															</ul>
														</div>
													</div>
												</div>
												
																
													<table class="table table-striped table-hover table-bordered" id="sample5">													
														<thead>
															<tr>
																<!-- <th>Section: </th><span><td id="section" style="min-width: 120px;"></td></span>
																<th>Dtname:</th><span><td id="fdrname" style="min-width: 120px;"></td></span>			
																 <th>Dtcapacity:</th><span><td id="dt_capacity" style="min-width: 120px;"></td></span> 
																  <th>readtime</th><span><td id="read_time" style="min-width: 120px;"></td></span> 
																   <th>MeterNumber:</th><span><td id="meter_number" style="min-width: 120px;"></td></span>  -->
																   
																   
																  <!--  <th>Section</th> -->
																  <th>Feeder Name</th>
																   <th>Dtname</th>
																   <th>DtCapacity</th>  
																   <th>MeterNumber</th>
																
																
															</tr>
														</thead>
														<tbody id="updtload">
													
														</tbody>
													
															
													</table>
													
													

												<table
													class="table table-striped table-hover table-bordered"
													id="sample_4">
												
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th>Read Time</th>
															<c:choose>
																<c:when test="${phase eq '1'}">
																	<th>Average Voltage</th>
																	<th>Average Current</th>
																	<th>Neutral Current</th>
																</c:when>
																<c:otherwise>
																	<th>Vr</th>
																	<th>Vy</th>
																	<th>Vb</th>
																	<th>Ir</th>
																	<th>Iy</th>
																	<th>Ib</th>
																</c:otherwise>
															</c:choose>
															<th>Block kWh</th>
															<th>kVArh Lag</th>
															<th>kVArh Lead</th>
															<th>kVAh</th>
															<th>Power Factor</th>
															<!-- <th>Frequency</th> -->
														</tr>
													</thead>
													<tbody id="loadSurveyTR">

													</tbody>
												</table>
												
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab_1_7">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv5">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																<!-- <li><a href="#" id=""
																	onclick="exportDailyParamPDF()">Export
																		to PDF</a></li> -->
																<li><a href="#" id="excelExport5"
																	onclick="tableToExcel5('sample_5', 'Daily Parameters')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
												</div>
												
											 <table class="table table-striped table-hover table-bordered"
													id="sample8">
													
													<thead>
															<tr>
																<!-- <th>SECTION</th> -->
																<th>Meter Number</th>
																<th>Feeder name</th>
																<th>DTNAME</th>
																<th>DTCAPACITY</th>
																		
															</tr>
													</thead>
															
															<tbody id="updailyload">
															
															</tbody>													
													</table> 
													

												<table class="table table-striped table-hover table-bordered"
													id="sample_5">
													
													<thead>
														<tr>
															<!-- <th>Meter No</th> -->
															<th>Date</th>
															<!-- <th>Vr</th>
															<th>Vy</th>
															<th>Vb</th>
															<th>Ir</th>
															<th>Iy</th>
															<th>Ib</th> -->
															<%-- <c:choose>
																<c:when test="${phase eq '1'}">
																	<th>Average Voltage</th>
																	<th>Average Current</th>
																	<th>Neutral Current</th>
																</c:when>
																<c:otherwise>
																	<th>Vr</th>
																	<th>Vy</th>
																	<th>Vb</th>
																	<th>Ir</th>
																	<th>Iy</th>
																	<th>Ib</th>
																</c:otherwise>
															</c:choose> --%>
															<th>Cum kWh Import</th>
															<th>Cum kWh Export</th>
															<th>Cum kVAh Import </th>
															<th>Cum kVAh Export</th>
															<th>Cum Energy kVArh Q1</th>
															<th>Cum Energy kVArh Q2</th>
															<th>Cum Energy kVArh Q3</th>
															<th>Cum Energy kVArh Q4</th>
															
															<!-- <th>kWh</th>
															<th>kVArh Lag</th>
															<th>kVArh Lead</th>
															<th>kVAh</th> -->
															<!-- <th>kWh Export</th>
															<th>kVAh Export</th> -->
															<!-- <th>Cum kWh</th>
															<th>Cum kVAh</th> -->

														</tr>
													</thead>
													<tbody id="dailyLoadSurveyTR">
													
															

													</tbody>
												</table>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab_1_10">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">


												<table
													class="table table-striped table-hover table-bordered"
													id="sample_10">
													<tbody id="plateDetails">


													</tbody>
												</table>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="tab_1_11">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="btn-group pull-right" style="margin-top: 1px;">
													<div id="excelExportDiv7">
														<button class="btn dropdown-toggle" data-toggle="dropdown">
															Tools <i class="fa fa-angle-down"></i>
														</button>
														<ul class="dropdown-menu pull-right">

															<li><a href="#" id="excelExport7"
																onclick="tableToExcel7('sample_11', 'Transaction Details')">Export
																	to Excel</a></li>
														</ul>
													</div>
												</div>

												<table
													class="table table-striped table-hover table-bordered"
													id="sample_11">
													<thead>
														<tr>
															<th>Event Time</th>
															<th>Event Code</th>
															<th>Event Discription</th>


														</tr>
													</thead>
													<tbody id="TransactionTR">

													</tbody>
												</table>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>



						<div class="tab-pane" id="tab_1_8">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<div class="portlet-body">
											<div class="table-toolbar">
												<div class="btn-group pull-right" style="margin-top: 1px;">
													<div id="excelExportDiv6">
														<button class="btn dropdown-toggle" data-toggle="dropdown">
															Tools <i class="fa fa-angle-down"></i>
														</button>
														<ul class="dropdown-menu pull-right">
															<!-- <li><a href="#" id=""
																	onclick="exportEnergyHistoryPDF()">Export
																		to PDF</a></li> -->
															 <li><a href="#" id="excelExport6"
																onclick="tableToExcel6('sample_6', 'Bill Parameters History')">Export
																	to Excel</a></li> 
														</ul>
													</div>
												</div>
											</div>
											
												<table class="table table-striped table-hover table-bordered"
												id="sample_18">
													 <thead>
															<tr>
																<th>Feedername</th>
																<th>Dtname:</th>
																<th>Dtcapacity:</th>
																<th>Meter_Number:</th>
															</tr>
														</thead>
														
														<tbody id="billHistoryBodyInfo">
										
														</tbody> 
														
																								
														<!-- 	<tbody>
																<th>Dtname:</th><span><td id="fdrname" style="min-width: 120px;"></td></span>	
																<th>Section: </th><span><td id="section" style="min-width: 120px;"></td></span>		
																<th>Dtcapacity:</th><span><td id="dt_capacity" style="min-width: 120px;"></td></span> 
																<th>Meter_Number:</th><span><td id="meter_number" style="min-width: 120px;"></td></span> 	
															</tbody>
														 -->
												
												</table>		
												
							

											<table class="table table-striped table-hover table-bordered"
												id="sample_6">
												
																
											<thead>
									
															
													<tr>
														<!-- <th>Meter No</th> -->
														<th>Date</th>
														<th>kWh</th>
														<th>kVAh</th>
														<th>kVA (MD)</th>
														<th>kW</th>
														<th>MD Date Time</th>
														<th>kW OCC DATE</th>
														<th>kWh Consumption</th>
														<th>kVAh Consumption</th>
														<th>kWh TOD1</th>
														<th>kWh TOD2</th>
														<th>kWh TOD3</th>
														<th>kWh TOD4</th>
														<th>kWh TOD5</th>
														<th>kWh TOD6</th>
														<th>kWh TOD7</th>
														<th>kWh TOD8</th>
														<th>kVAh TOD1</th>
														<th>kVAh TOD2</th>
														<th>kVAh TOD3</th>
														<th>kVAh TOD4</th>
														<th>kVAh TOD5</th>
														<th>kVAh TOD6</th>
														<th>kVAh TOD7</th>
														<th>kVAh TOD8</th>
														<!-- <th>kW</th>  -->
														<!-- <th>kW OCC DATE</th> -->
														<th>kVArh Lag</th>
														<th>kVArh Lead</th>
														<!-- <th>kWh Export</th>
														<th>kVAh Export</th> -->
													</tr>
												</thead>
													<tbody id="billHistoryBody">
	
													</tbody>
													
											</table>


										</div>
										<div id="chartContainer" style="height: 370px; width: 100%;"></div>
										<!-- <div hidden="true" id="graphId"></div>  -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>



				<!--Graph Info  -->
				<!--  <div class="tab-pane" id="tab_1_9">						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						
							<div class="form-body">
				  				<div class="table-responsive">
									<h4><b>Graph Details</b></h4>
							</div>
						</div>
					
				</div>
			</div>
			</div> -->
		</div> 
				<!--Graph Info end -->
				<!--end tab-pane 2-->
				<!-- <button type="button" style="margin-left: 1000px;" data-dismiss="modal" class="btn red">Close</button> -->
			</div>
		</div>
	</div>



</div>


<script type="text/javascript">
	var mtrNum = null;
	var frmDate = null;
	var tDate = null;
	var radioVal = null;
	var meterNumber = null;
	var projectName = '';
	$(".page-content")
			.ready(
					function() {
						console.log('${officeCode}' + " " + '${officeType}');
						// alert('${officeCode}');
						/* 	var session='';
							
							//alert(session);
							if(session=="circle"){
								$("#zone").hide();
								$("#zones").hide();
								//write javascript function 
								getCircle('${officeCode}');
								//$("#circleTd").hide();
							}
							else if(session=="divison"){

								//$("#")
							}  
						 */
						$('#tab_1-1').hide();
						projectName = "${projectName}";
						//$("#LFzone").val('${newRegionName}').trigger("change");
						//$('#LFzone').val('${newRegionName}');
						//$("#LFcircle").val('${officeName}').trigger("change");
						//$('#LFcircle').val('${officeName}');
						//$("#LFtown").val('${elements[0]}').trigger("change");
					 	$('#meterno').val('').trigger('change');  
			   	    	$('#LFtown').val('').trigger('change');   
						//$('#LFtown').val('${elements[0]}');
						$('#fromDate').val('${fromDate}');
						$('#toDate').val('${toDate}');
						//meterNumber = "${accno}";
						//console.log("acc number----" + meterNumber);
						//console.log("meter number----" + '${mtrno}');
						//var radio = "${radioVal}";
						//console.log("radio : "+radio);
						/* if (radio == 'kno') { */
						$('#meterNum').val('${mtrno}');
						//$("#kno_radio").click();

						/* } else { */
						//$('#meterNum').val('${mtrno}');
						//$("#meterno_radio").click();
						/* } */
						frmDate = '${fromDate}';
						tDate = '${toDate}';
						mtrNum = '${mtrno}';
						radioVal = $("input[name='optionsRadios']:checked")
								.val();
						//radioVal = '${radioVal}';
						//alert('${phase}'+"--currDate");
						$('.datepicker').datepicker({
							format : 'yyyy-mm-dd',
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

						App.init();
						//meterList();
						TableEditable.init();
						FormComponents.init();
						/* 	$('#360d-view').addClass('start active ,selected'); */
						$('#MDMSideBarContents,#360MeterDataViewID,#360d-view')
								.addClass('start active ,selected');
						$(
								"#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');
					});

	
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
	function showTownNameandCode(circle) {
		var zone = $('#LFzone').val();

		$
				.ajax({
					url : './getTownsBaseOnSubdivision',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle,
						division : '%',
						subdivision : '%'
					},
					success : function(response1) {
						var html = '';
						html += "<select id='LFtown' name='LFtown' onchange= 'showResultsbasedOntownCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i][0]+"'>"
									+ response1[i][0] + "-" + response1[i][1]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#LFtown").html(html);
						$('#LFtown').select2();
					}
				});
	}
	
	function showResultsbasedOntownCode(param) {

		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var twncode = $('#LFtown').val();

		$
				.ajax({
					url : './getMtrnobyTowncode',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle,
						twncode : twncode,
					},
					success : function(response) {
						/* if(response == null || response == ""){
							bootbox.alert("Meter Number Not Exist For the selected town");
							return false;
							} */
						var html = '';
						html += "<select id='meterNum' name='meterNum'  class='form-control input-medium' type='text'><option value=''>Select MeterNo</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#meterNum").html(html);
						$('#meterNum').select2();
					},
				});
	}
	//impl....
	
	
	
	function showDivisionss(circle_code) {
		var zone = "All";
		$
				.ajax({
					url : './showDivisionsByCircleCode' + '/' + circle_code,
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

						//alert("subdiv  "+response1);
						var html = '';
						html += "<select id='sdoCode' name='sdoCode' onchange='return showmeterListBySubdiv(this.value);' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
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

	function showmeterListBySubdiv(subdivision) {
		//var zone = "All";
		var circle = $('#circle').val();

		var divison = $('#division').val();
		//alert(circle+"----"+divison+"----"+subdivision);

		var zone = "All";

		$
				.ajax({
					url : './showMeterListBySubDiv/' + zone + '/' + circle
							+ '/' + divison + '/' + subdivision,
					type : 'GET',
					dataType : 'text',
					asynch : false,
					cache : false,
					success : function(response1) {
						console.log("response" + response1);
						var html = '';
						html += "<select id='meterNo' name='meterNo' class='form-control input-medium' type='text'><option value=''>Meter-List</option>";
						var elements = jQuery.parseJSON(response1);
						for (var i = 0; i < elements.length; i++) {
							console.log("ele--" + elements[i]);
							html += "<option  value='"+elements[i]+"'>"
									+ elements[i] + "</option>";
						}
						/* jQuery.each(elements, function(i, val) {
							html += '<option value='+val.jobName+'>' + val.jobName
									+ '</option>';
						
						}); */

						html += "</select><span></span>";
						$("#meterNo").html(html);
						//$('#meterNo').select2();
					}
				});
	}
	function checkForm() {
		$('#tab_1-1').show();
		zone = $('#LFzone').val();
		circle = $('#LFcircle').val();
		town = $('#LFtown').val();
		frmDate = $('#fromDate').val();
		tDate = $("#toDate").val();
		meterNum = $("#meterNum").val();

		if (zone == "" || zone == null) {
			bootbox.alert("Please Select Region");
			return false;
		}
		if (circle == "" || circle == null) {
			bootbox.alert("Please Select Circle");
			return false;
		}
		if (town == "" || town == null) {
			bootbox.alert("Please Select Town");
			return false;
		}
		if (meterNum == "") {
			bootbox.alert("Please Select Meter Number ");
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			return false;
		}
		$.ajax({
			url : './fullView360MDAS',
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			data : {
				zone : zone,
				circle : circle,
				town : town,
				meterNum : meterNum,
				frmDate : frmDate,
				tDate : tDate,
			},
			success : function(response) {
				if (response.length == 0) {
					bootbox.alert("No results found for the selected inputs.");
					$('#mtrCommDet').dataTable().fnClearTable();
					return;
				}

				$("#lastcomm").text(
						moment(response[0][2]).format('DD-MM-YYYY HH:mm:ss'));
				$("#commudays").text(response[0][3]);

				$("#crtym").text(
						moment(response[0][1]).format('DD-MM-YYYY HH:mm:ss'));

				$('#mtrCommDet').dataTable().fnClearTable();
				$('#mtrCommDetails').html(html);
			},
			complete : function() {
				loadSearchAndFilter('mtrCommDetails');
				$('#mtrCommDet').dataTable().fnClearTable();
			}

		});
		getConsumerDetails();
	}

	function getConsumerDetails() {
		meterNum = $("#meterNum").val();
		$.ajax({
			url : './getConsumerdetails1',
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			data : {
				meterNum : meterNum,
			},
			success : function(response) {
				if (response.length == 0) {
					bootbox.alert("No results found for the selected inputs.");
					$('#consumerDetails').dataTable().fnClearTable();
					return;
				}
				$("#cirid").text(response[0][2]);
				$("#divid").text(response[0][3]);
				$("#subdivid").text(response[0][5]);
				$("#townid").text(response[0][78]);
				$("#sectionid").text(response[0][79]);
				$("#dtid").text(response[0][80]);
				if (response[0][13] == "DT") {
					//$("#dtnameid").show();
					//$("#dtid").show();
					//$("#fdrnameid").hide();
					//$("#fdrid").hide();
					$("#locationid").text(response[0][51]);
				} else {
					/* $("#fdrnameid").show();
					$("#fdrid").show();
					$("#dtnameid").hide();
					$("#dtid").hide(); */
					$("#locationid").text(response[0][10]);
				}
				$("#mtrid").html("<a target='_blank' href='./viewFeederMeterInfoMDAS?mtrno="+response[0][29]+"'>"+response[0][29]+"</a>");
				$("#mtrtypeid").text(response[0][13]);
				$("#phaseid").text(
						response[0][66] == null ? "" : response[0][66]);

				$('#consumerDetails').dataTable().fnClearTable();
				$('#consumdetid').html(html);
			}
		});
	}
</script>

<script type="text/javascript">
	function eventDetails() {
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}                                                                                                                                                          
		var mtrNo = meterNum;
		var radioValue = radioVal;
		$('#imageee').show();
		$.ajax({
			url : './getEventData/' + mtrNo + '/' + frmDate + '/' + tDate + '/'
					+ radioValue,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				$('#imageee').hide();
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
					$('#sample_2').dataTable().fnClearTable();
				} else {
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var resp = data[i];
						var evtdate = moment(resp[1]).format(
								'YYYY-MM-DD HH:mm:ss');
						html += "<tr>"
						/* +" <td>"+resp[0]+"</td>" */
						+ " <td>" + evtdate + "</td>" + " <td>" + resp[2]
								+ "</td>";
						var edesc = getEventDesc(resp[2]);

						var resp3 = resp[3];
						if (resp3 == null) {
							resp3 = '';
						} else {
							var v = resp[3];
							var v1 = v.split(',');
							var starts = moment(v1[0]);
							var ends = moment(v1[1]);
							var ms = starts.diff(ends, 'days');
							var ms1 = (starts.diff(ends, 'hours')) % 24;
							var ms2 = (starts.diff(ends, 'minutes')) % 60;
							var ms3 = (starts.diff(ends, 'seconds')) % 60;
							if (ms1.toString().length == 1) {
								ms1 = '0' + ms1;
							}
							if (ms2.toString().length == 1) {
								ms2 = '0' + ms2;
							}
							if (ms3.toString().length == 1) {
								ms3 = '0' + ms3;
							}

							if (ms == '1') {
								resp3 = ms + 'day ' + ms1 + ':' + ms2 + ':'
										+ ms3;
							}

							resp3 = ms + 'days ' + ms1 + ':' + ms2 + ':' + ms3;

						}

						var resp4 = resp[4];
						if (resp4 == null) {
							resp4 = 0;
						} else {
							resp4 = resp[4];
						}

						var resp5 = resp[5];
						if (resp5 == null) {
							resp5 = 0;
						} else {
							resp5 = resp[5];
						}

						var resp6 = resp[6];
						if (resp6 == null) {
							resp6 = 0;
						} else {
							resp6 = resp[6];
						}

						var resp7 = resp[7];
						if (resp7 == null) {
							resp7 = 0;
						} else {
							resp7 = resp[7];
						}

						var resp8 = resp[8];
						if (resp8 == null) {
							resp8 = 0;
						} else {
							resp8 = resp[8];
						}

						var resp9 = resp[9];
						if (resp9 == null) {
							resp9 = 0;
						} else {
							resp9 = resp[9];
						}

						var resp10 = resp[10];
						if (resp10 == null) {
							resp10 = 0;
						} else {
							resp10 = resp[10];
						}

						var resp11 = resp[11];
						if (resp11 == null) {
							resp11 = 0;
						} else {
							resp11 = resp[11];
						}
						var resp12 = resp[12];
						if (resp12 == null) {
							resp12 = 0;
						} else {
							resp12 = resp[12];
						}
						var resp13 = resp[13];
						if (resp13 == null) {
							resp13 = 0;
						} else {
							resp13 = resp[13];
						}

						resp13 = parseFloat(resp13);

						html += " <td>" + edesc + "</td>";
						html += " <td>" + resp3 + "</td>" + " <td>" + resp4
								+ "</td>" + " <td>" + resp5 + "</td>" + " <td>"
								+ resp6 + "</td>" + " <td>" + resp7 + "</td>"
								+ " <td>" + resp8 + "</td>" + " <td>" + resp9
								+ "</td>" + " <td>" + resp10 + "</td>"
								+ " <td>" + resp11 + "</td>" + " <td>" + resp12
								+ "</td>" + " <td>" + resp13.toFixed(3)
								+ "</td>" + " </tr>";
					}
					$('#meterNoHeading').html(mtrNo);
					$('#sample_2').dataTable().fnClearTable();
					$('#eventTR').html(html);
				}
			},
			complete : function() {
				loadSearchAndFilter('sample_2');
				$('#imageee').hide();
			}
		});

		eventDetailsInfo();
	}

	function eventDetailsInfo(){

		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}       

		var mtrNo = meterNum;
	
		$('#imageee').show();

		$.ajax({


			url : './getEventDataInfo/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,

			success : function(data) {
				
						
				  if (data!= null && data.length > 0 || data==null && data.length>0) {
				   var html = "";
				
	        	   for (var i=0; i < data.length;i++) {
							var resp = data[i];
					
						html += "<tr>" 
							
							/* + "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>" */
							+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
							+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
							+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
								
					
							+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>";
						
							+"</tr>";
	        	   
	                $('#sample6').dataTable().fnClearTable();
	            	$("#updtevent").html(html);
	            	loadSearchAndFilter('sample6');
	        		}

				  }else {
						bootbox.alert("No Relative Data Found.");
					}
	        	  
				
			},

			complete : function() {
				loadSearchAndFilter('sample6');
				$('#imageee').hide();
			}

		});

}


		
	function getEventDesc(eCode) {
		var eDesc = '';
		if (eCode == '101') {
			eDesc = 'Power faliure - Occurrence';
		} else if (eCode == '102') {
			eDesc = 'Power faliure - Restoration';
		} else if (eCode == '1') {
			eDesc = 'R-Phase PT link Missing (Missing Potential) - Occurrence';
		} else if (eCode == '2') {
			eDesc = 'R-Phase PT link Missing (Missing Potential) - Restoration';
		} else if (eCode == '3') {
			eDesc = 'Y-Phase PT link Missing (Missing Potential) - Occurrence';
		} else if (eCode == '5') {
			eDesc = 'B-Phase PT link Missing (Missing Potential) - Occurrence';
		} else if (eCode == '4') {
			eDesc = 'Y-Phase PT link Missing (Missing Potential) - Restoration';
		} else if (eCode == '8') {
			eDesc = 'Over Voltage in any Phase - Restoration';
		} else if (eCode == '9') {
			eDesc = 'Low Voltage in any Phase - Occurrence';
		} else if (eCode == '6') {
			eDesc = 'B-Phase PT link Missing (Missing Potential) - Restoration';
		} else if (eCode == '7') {
			eDesc = 'Over Voltage in any Phase - Occurrence';
		} else if (eCode == '10') {
			eDesc = 'Low Voltage in any Phase - Restoration';
		} else if (eCode == '11') {
			eDesc = 'Voltage Unbalance - Occurrence';
		} else if (eCode == '12') {
			eDesc = 'Voltage Unbalance - Restoration';
		} else if (eCode == '51') {
			eDesc = 'Phase  R CT reverse - Occurrence';
		} else if (eCode == '52') {
			eDesc = 'Phase  R CT reverse - Restoration';
		} else if (eCode == '53') {
			eDesc = 'Phase  Y CT reverse - Occurrence';
		} else if (eCode == '54') {
			eDesc = 'Phase  Y CT reverse - Restoration';
		} else if (eCode == '55') {
			eDesc = 'Phase  B CT reverse - Occurrence';
		} else if (eCode == '56') {
			eDesc = 'Phase  B CT reverse - Restoration';
		} else if (eCode == '57') {
			eDesc = 'Phase  R CT Open - Occurrence';
		} else if (eCode == '58') {
			eDesc = 'Phase  R CT Open - Restoration';
		} else if (eCode == '59') {
			eDesc = 'Phase  Y CT Open - Occurrence';
		} else if (eCode == '60') {
			eDesc = 'Phase  Y CT Open - Restoration';
		} else if (eCode == '61') {
			eDesc = 'Phase  B CT Open - Occurrence';
		} else if (eCode == '62') {
			eDesc = 'Phase  B CT Open - Restoration';
		} else if (eCode == '63') {
			eDesc = 'Current Unbalance - Occurrence';
		} else if (eCode == '64') {
			eDesc = 'Current Unbalance - Restoration';
		} else if (eCode == '65') {
			eDesc = 'CT Bypass - Occurrence';
		} else if (eCode == '66') {
			eDesc = 'CT Bypass - Restoration';
		} else if (eCode == '67') {
			eDesc = 'Over Current in any Phase - Occurrence';
		} else if (eCode == '68') {
			eDesc = 'Over Current in any Phase - Restoration';
		} else if (eCode == '151') {
			eDesc = 'Real Time Clock  Date and Time';
		} else if (eCode == '152') {
			eDesc = 'Demand Integration Period';
		} else if (eCode == '153') {
			eDesc = 'Profile Capture Period';
		} else if (eCode == '154') {
			eDesc = 'Single-action Schedule for Billing Dates';
		} else if (eCode == '155') {
			eDesc = 'Activity Calendar for Time Zones etc.';
		} else if (eCode == '201') {
			eDesc = 'Influence of Permanent Magnet or AC/ DC Electromagnet - Occurrence';
		} else if (eCode == '202') {
			eDesc = 'Influence of Permanent Magnet or AC/ DC Electromagnet - Restoration';
		} else if (eCode == '203') {
			eDesc = 'Neutral Disturbance - HF AND DC - Occurence';
		} else if (eCode == '204') {
			eDesc = 'Neutral Disturbance - HF AND DC - Restoration';
		} else if (eCode == '205') {
			eDesc = 'Very Low PF - Occurrence';
		} else if (eCode == '206') {
			eDesc = 'Very Low PF - Restoration';
		} else if (eCode == '251') {
			eDesc = 'Meter Cover Opening - Occurrence';
		} else if (eCode == '301') {
			eDesc = 'Meter Disconnected';
		} else if (eCode == '302') {
			eDesc = 'Meter Connected';
		}

		return eDesc;
	}
	function isFloat(x) {
		return !!(x % 1);
	}




	
	function instansDetails() {
		//var mtrNo = meterNum;
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}                                                                                                                                          

		var mtrNo = meterNum;		
		//getOnDemandPooldata(meterNumber);
		$('#imageee').hide();
		var radioValue = radioVal;
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		$('#imageee').show();
		//alert(projectName);
		$.ajax({
			url : './getInstansDetails/' + mtrNo + '/' + radioValue,
			type : 'GET',
			asynch : false,
			cache : false,
			data : {
				frmDate : frmDate,
				tDate : tDate
			},
			dataType : 'json',
			async : false,
			cache : false,
			success : function(data) {
				$('#imageee').hide();
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
					$('#sample_8').dataTable().fnClearTable();
				} else {
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var resp = data[i];
						for (var j = 0; j < resp.length; j++) {
							if (resp[j] == null) {
								resp[j] = "";
							} else {
								if ($.isNumeric(resp[j])) {
									if (isFloat(resp[j])) {
										resp[j] = parseFloat(resp[j])
												.toFixed(3);
									}

								}
							}
						}
						console.log("in other than tneb projjj");
						$("#kwh_imp").text(
								(resp[25] == "" ? resp[25]
										: (parseFloat(resp[25]) / divider)));
						$("#kvah").text(
								(resp[27] == "" ? resp[27]
										: (parseFloat(resp[27]) / divider)));
						$("#kva").text(
								(resp[9] == "" ? resp[9]
										: (parseFloat(resp[9]) / divider)));
						$("#kvar").text(
								(resp[29] == "" ? resp[29]
										: (parseFloat(resp[29]) / divider)));
						$("#kvar_lag").text(
								(resp[30] == "" ? resp[30]
										: (parseFloat(resp[30]) / divider)));
						$("#kvar_lead").text(
								(resp[31] == "" ? resp[31]
										: (parseFloat(resp[31]) / divider)));

						$("#kwh_export").text(
								(resp[24] == "" ? resp[24]
										: (parseFloat(resp[24]) / divider)));
						$("#kvah_export").text(
								(resp[26] == "" ? resp[26]
										: (parseFloat(resp[26]) / divider)));

						$("#pfr").text(resp[19] == null ? "" : resp[19]);
						$("#pfy").text(resp[20] == null ? "" : resp[20]);
						$("#pfb").text(resp[21] == null ? "" : resp[21]);

						var pfr = parseFloat(resp[19]);
						var pfy = parseFloat(resp[20]);
						var pfb = parseFloat(resp[21]);

						var ir_angle = Math.acos(pfr) * (180 / Math.PI);
						var iy_angle = Math.acos(pfy) * (180 / Math.PI);
						var ib_angle = Math.acos(pfb) * (180 / Math.PI);

						$("#ir_angle").text(parseFloat(ir_angle).toFixed(3));
						$("#iy_angle").text(parseFloat(iy_angle).toFixed(3));
						$("#ib_angle").text(parseFloat(ib_angle).toFixed(3));

						$("#vr_angle").text(120);
						$("#vy_angle").text(120);
						$("#vb_angle").text(120);

						$("#ir").text(resp[13] == null ? "" : resp[13]);
						$("#iy").text(resp[14] == null ? "" : resp[14]);
						$("#ib").text(resp[15] == null ? "" : resp[15]);

						$("#iph").text(resp[51] == null ? "" : resp[51]);
						$("#inu").text(resp[52] == null ? "" : resp[52]);
						$("#vavg").text(resp[50] == null ? "" : resp[50]);

						$("#vr").text(resp[16] == null ? "" : resp[16]);
						$("#vy").text(resp[17] == null ? "" : resp[17]);
						$("#vb").text(resp[18] == null ? "" : resp[18]);

						$("#pf_t").text(resp[22] == null ? "" : resp[22]);
						$("#frequency").text(resp[23] == null ? "" : resp[23]);
						$("#p_kw").text(resp[28] == null ? "" : resp[28]);

						$("#power_off_count").text(
								resp[32] == null ? "" : resp[32]);
						$("#power_off_duration").text(
								resp[33] == null ? "" : resp[33]);
						$("#tamper_count").text(
								resp[36] == null ? "" : resp[36]);

						$("#meterNumberIns").text(mtrNo);

						$("#submittername").text(moment(resp[3]).format('YYYY-MM-DD HH:mm:ss'));

						
						$("#dtname").text(resp[64] == null ? "" : resp[64]);
						$("#fdr").text(resp[61] == null ? "" : resp[61]);
						$("#section").text(resp[62] == null ? "" : resp[62]);
						$("#dtcapacity").text(resp[63] == null ? "" : resp[63]);
						

						$("#phase_seq")
								.text(
										resp[54] == null ? "FORWARD"
												: resp[54] == "" ? "FORWARD"
														: resp[54]);

					}
					$('#sample_8').dataTable().fnClearTable();
					$('#instantsTR').html(html);
					//sloadSearchAndFilter('sample_8');
				}
			},
			complete : function() {
				loadSearchAndFilter('sample_8');
				$('#imageee').hide();
			} 

	
		});
		getAllInstanteniousDetails(mtrNo);
	}


	function getAllInstanteniousDetails(mtrNo) {
		$('#imageee').show();
		if ($('#meterNum').val() == ""){
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}                                                                                                                                                        
		

		var phase = "${phase}";
		var divider = '';
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		var radioValue = radioVal;
		$
				.ajax({
					url : './getALLInstansDetails/' + mtrNo + '/' + radioValue,
					timeout:3000,
					asynch : false,
					cache : false,
					
					type : 'GET',
					data : {
						frmDate : frmDate,
						tDate : tDate
					},
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(data) {
						if (data.length == 0 || data.length == null) {
							bootbox
									.alert("Data Not Available for this Meter No. : "
											+ mtrNo);
							$('#sample_8').dataTable().fnClearTable();
						} else {
							var html = "";
							for (var i = 0; i < data.length; i++) {
								var resp = data[i];

								if (phase == 1) {
									resp[13] = resp[51];
									resp[16] = resp[50];
								}

								for (var j = 0; j < resp.length; j++) {
									if (resp[j] == null) {
										resp[j] = "";
									} else {
										if ($.isNumeric(resp[j])) {
											if (isFloat(resp[j])) {
												resp[j] = parseFloat(resp[j])
														.toFixed(3);
											}

										}
									}
								}

								var pfr = parseFloat(resp[19]);
								var pfy = parseFloat(resp[20]);
								var pfb = parseFloat(resp[21]);

								var ir_angle = Math.acos(pfr) * (180 / Math.PI);
								var iy_angle = Math.acos(pfy) * (180 / Math.PI);
								var ib_angle = Math.acos(pfb) * (180 / Math.PI);

								html += "<tr>" + " <td>"
										+ (i + 1)
										+ "</td>"
										+ " <td>"
										+ resp[4]
										+ "</td>"
										+ " <td>"
										+ (resp[3] == null ? "" : moment(
												resp[3]).format(
												"YYYY-MM-DD HH:mm:ss"))
										+ "</td>"
										+ " <td>"
										+ (resp[25] == null ? ""
												: (parseFloat(resp[25]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[27] == null ? ""
												: (parseFloat(resp[27]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[9] == null ? ""
												: (parseFloat(resp[9]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ resp[13]
										+ "</td>"
										+ " <td>"
										+ resp[14]
										+ "</td>"
										+ " <td>"
										+ resp[15]
										+ "</td>"
										+ " <td>"
										+ resp[16]
										+ "</td>"
										+ " <td>"
										+ resp[17]
										+ "</td>"
										+ " <td>"
										+ resp[18]
										+ "</td>"
										+ " <td>"
										+ resp[19]
										+ " </td>"
										
										+ " <td>"
										+ resp[20]
										+ "</td>"
										+ " <td>"
										+ resp[21]
										+ "</td>"
										+ " <td>"
										+ resp[22]
										+ "</td>"
										+ " <td>"
										+ resp[23]
										+ "</td>"
										+ " <td>"
										+ (resp[24] == "" ? resp[24]
												: (parseFloat(resp[24]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[26] == "" ? resp[26]
												: (parseFloat(resp[26]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[28] == "" ? resp[28]
												: (parseFloat(resp[28]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[30] == "" ? resp[30]
												: (parseFloat(resp[30]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ (resp[31] == "" ? resp[31]
												: (parseFloat(resp[31]) / divider)
														.toFixed(3))
										+ "</td>"
										+ " <td>"
										+ resp[32]
										+ "</td>"
										+ " <td>"
										+ resp[33]
										+ "</td>"
										+ " <td>"
										+ resp[36]
										+ "</td>"
										+ "<td>"
										+ (resp[54] == null ? "FORWARD"
												: resp[54] == "" ? "FORWARD"
														: resp[54]) + "</td>"
										+ "<td>"
										+ (parseFloat(ir_angle).toFixed(3))
										+ "</td>" + "<td>"
										+ (parseFloat(iy_angle).toFixed(3))
										+ "</td>" + "<td>"
										+ (parseFloat(ib_angle).toFixed(3))
										+ "</td>" + "<td>120</td>"
										+ "<td>120</td>" + "<td>120</td>"
							          	+ " <td>"
							        	+ resp[29]
								        + "</td>"
								        + " </tr>";

							}
							$('#sample_8').dataTable().fnClearTable();
							$('#instantsTR').html(html);
							loadSearchAndFilter('sample_8');
							
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_8');
						$('#imageee').hide();
					}
				});
	}
	

	function getOnDemandPooldata(meterNumber) {
		var d1 = new Date();
		var d2 = new Date(d1);
		d2.setMinutes(d1.getMinutes() - 60);

		var date1 = dtconvert(d1);
		var date2 = dtconvert(d2);
		//alert(date1+" || "+date2);
		$.ajax({
			type : "GET",
			url : "./onDemandProfile/INSTANTANEOUS/INSTANTANEOUS/"
					+ meterNumber + "/" + date2 + "/" + date1,
			async : false,
			success : function(response) {
				//alert("response-----in------on demand"+response);
				if (response == "NoData") {

				} else if (response == "Succ") {

				}
			}

		});
	}
	function dtconvert(val) {
		var datesp = moment(val).format('HH:mm:ss');
		//alert(datesp);
		var d = new Date();
		var str = $.datepicker.formatDate('yy-mm-dd', d);
		var fstr = str + "T " + datesp + "+0530";
		return fstr;
	}

	function namePlateDetails() {
		var mtrNo = document.getElementById("meterNum").value;
		var radioValue = $("input[name='optionsRadios']:checked").val();
		$('#imageee').show();

		$.ajax({
			url : './getNamePlateDetails/' + mtrNo + '/' + radioValue,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				var html = '';
				for (var i = 0; i < response.length; i++) {
					console.log("date....." + response[i][0]);

					html += '<tr><th>Meter No</th><td>' + response[i][0]
							+ '<td><tr>'
							+ '<tr><th>Manufacturer 	Name</th><td>'
							+ response[i][1] + '<td><tr>'
							+ '<tr><th>Firmware Version</th><td>'
							+ response[i][2] + '<td><tr>'
							+ '<tr><th>Meter Type</th><td>'
							+ (response[i][3] == null ? "" : (response[i][3]))
							+ '<td><tr>' + '<tr><th>Internal CT Ratio</th><td>'
							+ (response[i][8] == null ? "" : (response[i][8]))
							+ '<td><tr>' + '<tr><th>Internal PT Ratio</th><td>'
							+ (response[i][9] == null ? "" : (response[i][9]))
							+ '<td><tr>'
							+ '<tr><th>Year Of Manufacture</th><td>'
							+ (response[i][4] == null ? "" : (response[i][4]))
							+ '<td><tr>' + '<tr><th>Hardware Version</th><td>'
							+ response[i][5] + '<td><tr>'
							+ '<tr><th>First Ping Date</th><td>'
							+ moment(response[i][6]).format('DD-MM-YYYY')
							+ '<td><tr>' + '<tr><th>Last Ping Date</th><td>'
							+ moment(response[i][7]).format('DD-MM-YYYY')
							+ '<td><tr>';
				}
				//$("#sample_editable_1").html(html1);
				$("#plateDetails").html(html);

			}
		});

	}

	function loadSurveyDetails() {
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}                                                                                                                                                          
		
		var phase = "${phase}";
		var mtrNo = meterNum;
		var radioValue = radioVal;
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		$('#imageee').show();
		$.ajax({
			url : './getLoadSurveyData/' + mtrNo + '/' + frmDate + '/' + tDate
					+ '/' + radioValue,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				$('#imageee').hide();
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
					$('#sample_4').dataTable().fnClearTable();
				} else {
					var html2 = "";
					for (var i = 0; i < data.length; i++) {

						var resp = data[i];

						for (var j = 0; j < resp.length; j++) {
							if (resp[j] == null) {
								resp[j] = "";
							} else {
								if ($.isNumeric(resp[j])) {
									if (isFloat(resp[j])) {
										resp[j] = parseFloat(resp[j])
												.toFixed(3);
									}

								}
							}
						}

						var mtrdate = moment(resp[1]).format(
								'YYYY-MM-DD HH:mm:ss');
						var mtrTime = moment(resp[1]).format('HH:mm:ss');
						if (mtrTime == '00:00:00') {
							var newdate = new Date(mtrdate);
							newdate.setDate(newdate.getDate() - 1);
							var dd = newdate.getDate();
							var mm = newdate.getMonth() + 1;
							var y = newdate.getFullYear();
							var someFormattedDate = y + '-' + mm + '-' + dd
									+ ' 24:00:00';
							mtrdate = someFormattedDate;
						}
						//alert(resp0);
						var resp2 = resp[2];
						if (resp2 == null) {
							resp2 = 0;
						} else {
							resp2 = resp[2];
						}

						var resp3 = resp[3];
						if (resp3 == null) {
							resp3 = 0;
						} else {
							resp3 = resp[3];
						}

						var resp4 = resp[4];
						if (resp4 == null) {
							resp4 = 0;
						} else {
							resp4 = resp[4];
						}

						var resp5 = resp[5];
						if (resp5 == null) {
							resp5 = 0;
						} else {
							resp5 = resp[5];
						}

						var resp6 = resp[6];
						if (resp6 == null) {
							resp6 = 0;
						} else {
							resp6 = resp[6];
						}

						var resp7 = resp[7];
						if (resp7 == null) {
							resp7 = 0;
						} else {
							resp7 = resp[7];
						}

						var resp8 = resp[8];
						if (resp8 == null) {
							resp8 = 0;
						} else {
							resp8 = resp[8];
						}

						var resp9 = resp[9];
						if (resp9 == null) {
							resp9 = 0;
						} else {
							resp9 = resp[9];
						}

						var resp10 = resp[10];
						if (resp10 == null) {
							resp10 = 0;
						} else {
							resp10 = resp[10];
						}

						var resp11 = resp[11];
						if (resp11 == null) {
							resp11 = 0;
						} else {
							resp11 = resp[11];
						}
						var resp12 = resp[12];
						if (resp12 == null) {
							resp12 = 0;
						} else {
							resp12 = resp[12];
						}

						html2 += "<tr>" + " <td>" + mtrdate + "</td>";

						if (phase == 1) {
							html2 += " <td>" + resp[13] + "</td>" + " <td>"
									+ resp[14] + "</td>" + " <td>" + resp[15]
									+ "</td>";
						} else {
							html2 += " <td>" + resp[2] + "</td>" + " <td>"
									+ resp[3] + "</td>" + " <td>" + resp[4]
									+ "</td>" + " <td>" + resp[5] + "</td>"
									+ " <td>" + resp[6] + "</td>" + " <td>"
									+ resp[7] + "</td>";
						}

						html2 += " <td>"
								+ (resp[8] == "" ? resp[8]
										: (parseFloat(resp[8]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[9] == "" ? resp[9]
										: (parseFloat(resp[9]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[10] == "" ? resp[10]
										: (parseFloat(resp[10]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[11] == "" ? resp[11]
										: (parseFloat(resp[11]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[16] == "" ? resp[16]
										: (parseFloat(resp[16]))) + "</td>"
								+ " </tr>";

						
					}
					
				
					$('#sample_4').dataTable().fnClearTable();
					$('#loadSurveyTR').html(html2);

				}
			},
			complete : function() {
				loadSearchAndFilter('sample_4');
				$('#sample_4').dataTable().fnSort([ [ 0, 'desc' ] ]);
				$('#imageee').hide();

			}
		});
		loadSurveyDetailsInfo();
	}

	
	function loadSurveyDetailsInfo() {
		
	var mtrNo = meterNum;
		
		
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}                                                                                                                                                   
		

	
		$('#imageee').show();
		$.ajax({
			url : './getLoadSurveyDataInfo/'+ mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success:function(data)
	        {
	        	   
				var html = "";

				if (data!= null && data.length > 0 || data==null && data.length>0) {
	        	   for (var i=0; i < data.length;i++) {
							var resp = data[i];
					
						html += "<tr>" 
							+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
							+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
							/* + "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>" */
							
							+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"	
							+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>";
						
							+"</tr>";
	        	   
	                $('#sample5').dataTable().fnClearTable();
	            	$("#updtload").html(html);
	            	loadSearchAndFilter('sample5');
	        		}
				}else {
					bootbox.alert("No Relative Data Found.");
				}
	        	   
	           },
			complete : function() {
				loadSearchAndFilter('sample5');
				
				$('#imageee').hide();

			}
		});
		
	}

	
	
	function getGraphs() 
	{
		
		var zone1 =$("#LFzone").val();
		var circle1=$("#LFcircle").val();
		var town1=$("#LFtown").val();
		var mtrno = $("#meterNum").val();
		var from = $("#fromDate").val();
		var to = $("#toDate").val();
		//alert(zone);
		
		 var zone ="";
		var cicle="";
		var town="";
		
		if(zone1 == '%'){
			zone='ALL';
		} else{
			zone=zone1;
		}if(circle1 =='%'){
			circle='ALL';
		}else{
			circle=circle1;
		}if(town1 == '%'){
			town = 'ALL';
		}else{
			town = town1;
		}
		window.location.href = ('./getMinMaxData?mtrno=' + mtrno + '&from='
				+ from + '&to=' + to +'&zone='+ zone+ '&circle='+circle+ '&town='+town + '');
		// window.location.href('./getMinMaxData?mtrno='+mtrno+'&from='+from+'&to='+to+'');	

	}

	function dailyLoadSurveyDetails() {
		//alert("fev");

		$('#imageee').show(); 
		frmDate = $('#fromDate').val();
		tDate = $("#toDate").val();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}   
		var phase = "${phase}";
		var mtrNo = meterNum;
		var radioValue = radioVal;
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		$('#imageee').show();
		//alert(mtrNo);
		$.ajax({
			url : './getDailyLoadSurveyData/' + mtrNo + '/' + frmDate + '/'
					+ tDate + '/' + radioValue,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				$('#imageee').hide();
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
					$('#sample_5').dataTable().fnClearTable();
				} else {
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var resp = data[i];
						for (var j = 0; j < resp.length; j++) {
							if (resp[j] == null) {
								resp[j] = "";
							} else {
								if ($.isNumeric(resp[j])) {
									if (isFloat(resp[j])) {
										resp[j] = parseFloat(resp[j])
												.toFixed(3);
									}

								}
							}
						}

						html += "<tr>" + " <td>" + moment(resp[3]).format("YYYY-MM-DD") + "</td>";
						

					/* 	if (phase == 1) {
							html += " <td>" + resp[11] + "</td>" + " <td>"
									+ resp[12] + "</td>" + " <td>" + resp[13]
									+ "</td>";
						} else {
							html += " <td>" + resp[1] + "</td>" + " <td>"
									+ resp[2] + "</td>" + " <td>" + resp[3]
									+ "</td>" + " <td>" + resp[4] + "</td>"
									+ " <td>" + resp[5] + "</td>" + " <td>"
									+ resp[6] + "</td>";
						} */

						/* html += " <td>"
								+ (resp[7] == "" ? resp[7]
										: (parseFloat(resp[7]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[8] == "" ? resp[8]
										: (parseFloat(resp[8]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[9] == "" ? resp[9]
										: (parseFloat(resp[9]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[10] == "" ? resp[10]
										: (parseFloat(resp[10]) / divider))
								+ "</td>" */
								/* + " <td>"
								+ (resp[14] == "" ? resp[14]
										: (parseFloat(resp[14]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[15] == "" ? resp[15]
										: (parseFloat(resp[15]) / divider))
								+ "</td>" */
								/* + " <td>"
								+ (resp[16] == "" ? resp[16]
										: (parseFloat(resp[16]) / divider))
								+ "</td>"
								+ " <td>"
								+ (resp[17] == "" ? resp[17]
										: (parseFloat(resp[17]) / divider))
								+ "</td>" */

								html += " <td>"
									+ (resp[4] == "" ? resp[4]
											: (parseFloat(resp[4]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[5] == "" ? resp[5]
											: (parseFloat(resp[5]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[6] == "" ? resp[6]
											: (parseFloat(resp[6]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[7] == "" ? resp[7]
											: (parseFloat(resp[7]).toFixed(3)))
									+ "</td>"
									+" <td>"
									+ (resp[8] == "" ? resp[8]
											: (parseFloat(resp[8]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[9] == "" ? resp[9]
											: (parseFloat(resp[9]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[10] == "" ? resp[10]
											: (parseFloat(resp[10]).toFixed(3)))
									+ "</td>"
									+ " <td>"
									+ (resp[11] == "" ? resp[11]
											: (parseFloat(resp[11]).toFixed(3)))
									+ "</td>"
								+ " </tr>";

								$("#fdrname").text(resp[15] == null ? "" : resp[15]);
								$("#section").text(resp[16] == null ? "" : resp[16]); 
								$("#dt_capacity").text(resp[17] == null ? "" : resp[17]);


								
					}
					$('#sample_5').dataTable().fnClearTable();
					$('#dailyLoadSurveyTR').html(html);
				}
			},
			complete : function() {
				loadSearchAndFilter('sample_5');
				$('#imageee').hide();
			}
		});

		 dailyLoadSurveyDetailsInfo(); 
	}


	function dailyLoadSurveyDetailsInfo(){

		$('#imageee').show();
	
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
	   
		 var phase = "${phase}";
		var mtrNo = meterNum;
	
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		} 
		$('#imageee').show();

		$.ajax({

			url : './getDailyLoadSurveyDataInfo/'+ mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,

			success : function(data) {

				$('#imageee').hide();
				
				if (data!= null && data.length > 0 || data==null && data.length>0) {
					
					for (var i = 0; i < data.length; i++) { 
					var html = "";
					var resp = data[i];
				
					html += "<tr>" 
						+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
						+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"	
						+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
						 + "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>";
							
						/* + "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>" */
						+"</tr>";
					}	
						$('#sample8').dataTable().fnClearTable();
						$('#updailyload').html(html);
				}

			/* 	else {
					var html = "";
					for (var i = 0; i < data.length; i++) { 
						var resp = data[i];
						

							
									html += "<tr>" 
										+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
										+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
										 + "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"; 
											;
										+"</tr>";
							
					 } 
					
					$('#sample8').dataTable().fnClearTable();
					$('#updailyload').html(html);
				} */
			},

			complete : function() {
				loadSearchAndFilter('sample8');
				$('#imageee').hide();
			}

			
		});

}


	
	

	function billHistoryDetails() {
		var mtrNo = meterNum;
		var radioValue = radioVal;
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			$('#imageee').hide();
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			$('#imageee').hide();
			return false;  
		}                                                                                                                                                                 


		
		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		$('#imageee').show();
		$.ajax({
			url : './getbillHistoryDetails/' + mtrNo + '/' + frmDate + '/'
					+ tDate + '/' + radioValue,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				$('#imageee').hide();
				/* 
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
				} else {
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var resp = data[i];

						for (var j = 0; j < resp.length; j++) {
							if (resp[j] == null) {
								resp[j] = "";
							} else {
								if ($.isNumeric(resp[j])) {
									if (isFloat(resp[j])) {
										resp[j] = parseFloat(resp[j])
												.toFixed(3);
									}

								}
							}
						}
						console.log("response---" + resp[76]);

						//alert(resp[15]);
						html += "<tr>" 
								+ "<td>" + moment(resp[5]).format("YYYY-MM-DD HH:mm:ss") + "</td>"
								+ "<td>" + (resp[15] == "" ? resp[15] : (parseFloat(resp[15]) / divider)) + "</td>"
								+ "<td>" + (resp[18] == "" ? resp[18] : (parseFloat(resp[18]) / divider)) + "</td>"
								+ "<td>" + (resp[45] == "" ? resp[45] : (parseFloat(resp[45]) / divider)) + "</td>"
								+ "<td>" + (resp[46]) + "</td>"
								+ "<td>" + (resp[76] == "" ? resp[77] : (parseFloat(resp[77]) / divider)) + "</td>"
								+ "<td>" + (resp[77] == "" ? resp[78] : (parseFloat(resp[78]) / divider)) + "</td>"
								+ "<td>"
								+ (resp[7] == "" ? resp[7]
										: (parseFloat(resp[7]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[8] == "" ? resp[8]
										: (parseFloat(resp[8]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[9] == "" ? resp[9]
										: (parseFloat(resp[9]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[10] == "" ? resp[10]
										: (parseFloat(resp[10]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[11] == "" ? resp[11]
										: (parseFloat(resp[11]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[12] == "" ? resp[12]
										: (parseFloat(resp[12]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[13] == "" ? resp[13]
										: (parseFloat(resp[13]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[14] == "" ? resp[14]
										: (parseFloat(resp[14]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[19] == "" ? resp[19]
										: (parseFloat(resp[19]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[20] == "" ? resp[20]
										: (parseFloat(resp[20]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[21] == "" ? resp[21]
										: (parseFloat(resp[21]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[22] == "" ? resp[22]
										: (parseFloat(resp[22]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[23] == "" ? resp[23]
										: (parseFloat(resp[23]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[24] == "" ? resp[24]
										: (parseFloat(resp[24]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[25] == "" ? resp[25]
										: (parseFloat(resp[25]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[26] == "" ? resp[26]
										: (parseFloat(resp[26]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[27] == "" ? resp[27]
										: (parseFloat(resp[27]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[28] == "" ? resp[28] : resp[28])
								+ "</td>"
								+ "<td>"
								+ (resp[16] == "" ? resp[16]
										: (parseFloat(resp[16]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[17] == "" ? resp[17]
										: (parseFloat(resp[17]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[66] == "" ? resp[66]
										: (parseFloat(resp[66]) / divider))
								+ "</td>"
								+ "<td>"
								+ (resp[67] == "" ? resp[67]
										: (parseFloat(resp[67]) / divider))
								+ "</td>" + " </tr>";
				 */

				/* new  */
				
				if (data.length == 0 || data.length == null) {
					bootbox.alert("Data Not Available for this Meter No. : "
							+ mtrNo);
					$('#sample_6').dataTable().fnClearTable();
				} else {
					
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var resp = data[i];

						for (var j = 0; j < resp.length; j++) {
							if (resp[j] == null) {
								resp[j] = "";
							} else {
								if ($.isNumeric(resp[j])) {
									if (isFloat(resp[j])) {
										resp[j] = parseFloat(resp[j])
												.toFixed(3);
									}

								}
							}
						}
						//console.log("response---"+resp[0]);

						//alert(resp[16]);
						html += "<tr>" + "<td>"
								+ moment(resp[0]).format("YYYY-MM-DD HH:mm:ss")
								+ "</td>" + "<td>"
								+ (resp[1] == null ? "" : (resp[1])) + "</td>"
								+ "<td>" + (resp[2] == null ? "" : (resp[2]))
								+ "</td>" + "<td>"
								+ (resp[3] == null ? "" : (resp[3])) + "</td>"
								+ "<td>" + (resp[23] == null ? "" : (resp[23]))
								+ "</td>" + "<td>" + (resp[4]) + "</td>"
								+ "<td>" + (resp[24] == null ? "" : (resp[24]))
								+ "</td>" + "<td>"
								+ (resp[5] == null ? "" : (resp[5])) + "</td>"
								+ "<td>" + (resp[6] == null ? "" : (resp[6]))
								+ "</td>" + "<td>"
								+ (resp[7] == null ? "" : (resp[7])) + "</td>"
								+ "<td>" + (resp[8] == null ? "" : (resp[8]))
								+ "</td>" + "<td>"
								+ (resp[9] == null ? "" : (resp[9])) + "</td>"
								+ "<td>" + (resp[10] == null ? "" : (resp[10]))
								+ "</td>" + "<td>"
								+ (resp[11] == null ? "" : (resp[11]))
								+ "</td>" + "<td>"
								+ (resp[12] == null ? "" : (resp[12]))
								+ "</td>" + "<td>"
								+ (resp[13] == null ? "" : (resp[13]))
								+ "</td>" + "<td>"
								+ (resp[14] == null ? "" : (resp[14]))
								+ "</td>" + "<td>"
								+ (resp[15] == null ? "" : (resp[15]))
								+ "</td>" + "<td>"
								+ (resp[16] == null ? "" : (resp[16]))
								+ "</td>" + "<td>"
								+ (resp[17] == null ? "" : (resp[17]))
								+ "</td>" + "<td>"
								+ (resp[18] == null ? "" : (resp[18]))
								+ "</td>" + "<td>"
								+ (resp[19] == null ? "" : (resp[19]))
								+ "</td>" + "<td>"
								+ (resp[20] == null ? "" : (resp[20]))
								+ "</td>" + "<td>"
								+ (resp[21] == null ? "" : (resp[21]))
								+ "</td>" + "<td>"
								+ (resp[22] == null ? "" : (resp[22]))
								+ "</td>"
								/*  +"<td>" +(resp[23] ==null ? "": (resp[23]))+ "</td>" */
								/*  +"<td>"+(resp[24] ==null ? "": (resp[24]))+ "</td>"  */
								+ "<td>" + (resp[25] == null ? "" : (resp[25]))
								+ "</td>" + "<td>"
								+ (resp[26] == null ? "" : (resp[26]))
								
								+ "</td>";

								

							 /*	$("#fdrname").text(resp[33] == null ? "" : resp[33]);
								$("#section").text(resp[35] == null ? "" : resp[35]); 
								$("#dt_capacity").text(resp[32] == null ? "" : resp[32]);
								$("#meter_number").text(resp[34] == null ? "" : resp[34]); */

								 
						/*  +"<td>" +(resp[27] ==null ? "": (resp[27]))+ "</td>" 
						 +"<td>" +(resp[28] ==null ? "": (resp[28]))+ "</td>"; */

					}
					$('#sample_6').dataTable().fnClearTable();
					$('#billHistoryBody').html(html);
				}
			},
			complete : function() {
				loadSearchAndFilter('sample_6');
				$('#imageee').hide();
			//	location.reload();
			}
		});
		 billHistoryDetailsInfo(); 
		billHistoryDetailsCurve();
		
	}

	function billHistoryDetailsInfo(){
		var mtrNo = meterNum;
		
		
		$('#imageee').show();
		if ($('#meterNum').val() == "") {
			bootbox.alert("Please Select Meter Number ");
			$('#imageee').hide();
			return false;
		}

				
		$('#imageee').show();

		 $.ajax({


					url : './getbillHistoryDetailsInfo/'+ mtrNo,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,

					success : function(data) {
						$('#imageee').hide();


						if (data!= null && data.length > 0 || data==null && data.length>0) {
							   var html = "";
							//   alert(data);
				        	   for (var i=0; i < data.length;i++) {
										var resp = data[i];
								
									html += "<tr>" + "<td>"
										+ (resp[1] == null ? "" : (resp[1])) + "</td>"
										+ "<td>"
										+ (resp[2] == null ? "" : (resp[2])) + "</td>"
										
										 + "<td>"
										+ (resp[4] == null ? "" : (resp[4])) + "</td>" 
										+ "<td>" +(resp[0] == null ? "" : (resp[0]))
										+ "</td>";
				        	   
				                $('#sample_18').dataTable().fnClearTable();
				            	$("#billHistoryBodyInfo").html(html);
				            	loadSearchAndFilter('sample_18');
				        		}

							  }else {
									bootbox.alert("No Relative Data Found.");
								}
					},
					complete : function() {
						loadSearchAndFilter('sample_18');
						$('#imageee').hide();
					//	location.reload();
					}
				});
			}
								
					
		

	function billHistoryDetailsCurve() {

		var dataPoints = [];
		var consumption = "";
		
		var mtrNo = meterNum;
		var radioValue = radioVal;

		if (!"TNEB".localeCompare(projectName)) {
			divider = 1;
		} else {
			divider = 1000;
		}
		$.ajax({
			url : './getbillHistoryDetails/' + mtrNo + '/' + frmDate + '/'
					+ tDate + '/' + radioValue,
			type : 'GET',
			dataType : 'json',

			success : function(data) {/* 
													for (var i = 0; i < data.length; i++) {
														var resp = data[i];
														 	if(i==0)
														{
														 consumption=resp[15]-0;
														}else{
															  var result=data[i-1];
															  consumption=resp[15]-result[15];
															}	 

															for (var j = 0; j < resp.length; j++) {
																if (resp[j] == null) {
																	resp[j] = "";
																} else {
																	if ($.isNumeric(resp[j])) {
																		if (isFloat(resp[j])) {
																			resp[j] = parseFloat(resp[j])
																					.toFixed(3);
																		}

																	}
																}
															}
															//alert(resp[]);77
														dataPoints.push({
															x : new Date(resp[5]),
															y : ((parseFloat(resp[77])) / divider)
														});
													}
													var chart = new CanvasJS.Chart("chartContainer", {
														title : {
															text : "Bill History Details Curve (Month Wise)"
														},
														axisX : {
															title : "Month",
															interval : 1,
															intervalType : "month"

														},
														axisY : {
															includeZero : true,
															interval : 20,
															title : "Consumption",

														},
														data : [ {
															type : "column",
															dataPoints : dataPoints
														} ]
													});

													chart.render();
			 */

				/* new  */

				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					/* 	if(i==0)
					{
					 consumption=resp[15]-0;
					}else{
						  var result=data[i-1];
						  consumption=resp[15]-result[15];
						}	 */

					dataPoints.push({
						x : new Date(resp[0]),
						y : ((resp[5]))
					});
				}
				var chart = new CanvasJS.Chart("chartContainer", {
					title : {
						text : "Energy History Details Curve (Month Wise)"
					},
					axisX : {
						title : "Month",
						interval : 1,
						intervalType : "month"

					},
					axisY : {
						includeZero : false,
						title : "Consumption",

					},
					data : [ {
						type : "column",
						dataPoints : dataPoints,		
						indexLabel: "{y}",
				        indexLabelPlacement: "inside",  
				        indexLabelOrientation: "horizontal"
					} ]
				});

				chart.render();

			},
		});
	}
	/* function dailyMinMax(){
		var mtrNo = mtrNum;
		var radioValue = $("input[name='optionsRadios']:checked").val();
		console.log("radioVal"+radioValue);
		var tourl="";
		
		if(!"meterno".localeCompare(radioValue)){
			tourl='./getMinMaxAvgDataByMtrNo/'+ mtrNo + '/' + frmDate + '/'+ tDate;
			
		}
		else{
			//alert("inthe kno else con");
			tourl='./getMinMaxAvgDataBykNo/'+ mtrNo + '/' + frmDate + '/'+ tDate;
		}
		console.log(tourl);
		$.ajax({
			url:tourl,
			type:'GET',
			success:function(response){
				var html = "";
				if (response.length == 0 || response.length == null) {
					bootbox.alert("Data Not Available for this  Meter No /K number: "
									+ mtrNo);
					//$('#dailyMinMaxAvg').empty();
				} else {
					//alert("response-"+response.length);
				for(var i=0; i<response.length; i++)
				{
					
					console.log("date....."+response[i][0]);
				html +="<tr>"
					+ "<td>"+ response[i][0]+ "</td>"
					+ "<td>"+ response[i][1]+ "</td>"
					+ "<td>"+ response[i][2]+ "</td>"
					+ "<td>"+ response[i][3]+ "</td>"
					+ "<td>"+ response[i][4]+ "</td>"
					+ "<td>"+ response[i][5]+ "</td>"
					+ "<td>"+ response[i][6]+ "</td>"
					+ "<td>"+ response[i][7]+ "</td>"
				+"</tr>";
				
				
				
				//loadSearchAndFilter('sample_1');
			}
				$('#sample_12').dataTable().fnClearTable();
				loadSearchAndFilter('sample_12');
				$('#dailyMinMaxAvg').html(html);
				
			}
			}
			 complete : function() {
				loadSearchAndFilter('sample_12');
			} 
		//dailyMinMaxAvg
		
		});
	} */
	function transactionData() {
		var mtrNo = meterNum;
		var radioValue = radioVal;
		if ($('#meterNum').val() == ""){
			bootbox.alert("Please Enter Meter Number / K number");
			return false;
		}
		if (frmDate == "") {
			bootbox.alert("Please Select from Date");
			return false;
		}
		if (tDate == "") {
			bootbox.alert("Please Select To Date");
			return false;
		}
		var tourl = "";
		$('#TransactionTR').empty();
		if (!"meterno".localeCompare(radioValue)) {
			tourl = './transactiondata/' + mtrNo + '/' + frmDate + '/' + tDate;
			$('#imageee').show();
			$
					.ajax({
						url : tourl,
						type : 'GET',
						//dataType : 'JSON',
						asynch : false,
						cache : false,
						success : function(response) {
							$('#imageee').hide();
							//alert(response);
							if (response.length == 0 || response.length == null) {
								bootbox.alert("No data for this meter number "
										+ mtrNo);
							}
							var htmlq = '';
							for (var i = 0; i < response.length; i++) {
								var element = response[i];

								htmlq += "<tr>"
										+ "<td>"
										+ moment(element[0]).format(
												'DD-MM-YYYY HH:mm:ss')
										+ "</td>" + "<td>" + element[1]
										+ "</td>" + "<td>" + element[2]
										+ "</td>";
								//+"</tr>";
							}
							$('#sample_11').dataTable().fnClearTable();
							$('#TransactionTR').html(htmlq);
							loadSearchAndFilter('sample_11');
						}

					});
		} else {
			bootbox.alert("Please enter only meter number instead of kno : ");
		}
	}
	function setRadioVal(val) {
		$("#radioVal").val(val);
		if (val == 'meterno') {
			$("#meterNum").attr("placeholder", "Enter Meter No");
			// meterList();

		} else {
			$("#meterNum").attr("placeholder", "Enter K No");
			//kNoList();
		}
	}
</script>







<script>
	function loadSearchAndFilter(param) {
		$('#' + param).dataTable().fnDestroy();
		$('#' + param).dataTable(
				{
					"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
							[ 10, 20, 50, 100, "All" ] // change per page values here
					],
					"iDisplayLength" : 10
				});
		jQuery('#' + param + '_wrapper .dataTables_filter input').addClass(
				"form-control input-small"); // modify table search input 
		jQuery('#' + param + '_wrapper .dataTables_length select').addClass(
				"form-control input-xsmall"); // modify table per page dropdown 
		jQuery('#' + param + '_wrapper .dataTables_length select').select2(); // initialize select2 dropdown 
	}

	

	function showTownNameandCode(circle) {
		var zone = $('#zone').val();
		// var circle = $('#circle').val();
		//var division = $('#division').val();
		$
				.ajax({
					url : './getTownsBaseOnSubdivision',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : '%',
						circle : circle,
						division : '%',
						subdivision : '%'
					},
					success : function(response1) {
						var html = '';
						html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option value='"+response1[i][0]+"'>"
									+ response1[i][0] + "-" + response1[i][1]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#LFtown").html(html);
						$('#LFtown').select2();
					}
				});
	}

	function showResultsbasedOntownCode() {
		var zone=$('#LFzone').val();
		var circle=$('#LFcircle').val();
		var twncode = $('#LFtown').val();
		
	//	String zne="",town="",cir="";
	
		/* if (twncode == "%") {
			town = "ALL";
		} else {
			town = twncode;
		} */
		
				$.ajax({
					url : './getMtrnobyTowncode',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone:zone,
						circle:circle,
						town : twncode
					},
					success : function(response) {
						var html = '';
						html += "<select id='meterNum' name='meterNum' onchange= 'showResultsbasedOntownCode(this.value)'  class='form-control input-medium' type='text'><option value=''>Select MeterNo</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#meterNum").html(html);
						$('#meterNum').select2();
					},
				});

				
	}

	

	</script>
	
	<script>

	function exportEnergyHistoryPDF()
	{		
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var mtrno = $('#meterNum').val();
		var fdate = $('#fromDate').val();
		var tdate = $('#toDate').val();

		if(zone == "%")
		{
			zne = "ALL";
		}else{
			zne = zone;
		}
		if(circle == "%")
		{
			cir = "ALL";
		}else{
			cir = circle;
		}
		if(town == "%")
		{
			twn = "ALL";
		}else{
			twn  = town;
		}
		
		window.location.href="./ViewEnergyHistoryPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
						
	}

	function exportLoadSurveyPDF()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town = $('#LFtown').val();
		var mtrno = $('#meterNum').val();
		var fdate = $('#fromDate').val();
		var tdate = $('#toDate').val();

		if(zone == "%")
		{
			zne = "ALL";
		}else{
			zne = zone;
		}
		if(circle == "%")
		{
			cir = "ALL";
		}else{
			cir = circle;
		}
		if(town == "%")
		{
			twn = "ALL";
		}else{
			twn  = town;
		}
		
		window.location.href="./ViewLoadSurveyPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
		
		}

	 function exportEvtDetailsPDF()
	 {
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var town = $('#LFtown').val();
			var mtrno = $('#meterNum').val();
			var fdate = $('#fromDate').val();
			var tdate = $('#toDate').val();

			if(zone == "%")
			{
				zne = "ALL";
			}else{
				zne = zone;
			}
			if(circle == "%")
			{
				cir = "ALL";
			}else{
				cir = circle;
			}
			if(town == "%")
			{
				twn = "ALL";
			}else{
				twn  = town;
			}
			
			window.location.href="./ViewEventDetailsPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
			
	 }
 

		function exportDailyParamPDF()
		{
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var town = $('#LFtown').val();
			var mtrno = $('#meterNum').val();
			var fdate = $('#fromDate').val();
			var tdate = $('#toDate').val();

			if(zone == "%")
			{
				zne = "ALL";
			}else{
				zne = zone;
			}
			if(circle == "%")
			{
				cir = "ALL";
			}else{
				cir = circle;
			}
			if(town == "%")
			{
				twn = "ALL";
			}else{
				twn  = town;
			}
			
			window.location.href="./ViewDailyParamPDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
			
		}

		function excelLoadSurvey()
		{
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var town = $('#LFtown').val();
			var mtrno = $('#meterNum').val();
			var fdate = $('#fromDate').val();
			var tdate = $('#toDate').val();

			if(zone == "%")
			{
				zne = "ALL";
			}else{
				zne = zone;
			}
			if(circle == "%")
			{
				cir = "ALL";
			}else{
				cir = circle;
			}
			if(town == "%")
			{
				twn = "ALL";
			}else{
				twn  = town;
			}
			
			window.location.href="./ViewLoadSurveyExcel?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
								
		}

		function excelMeterDetails()
		{
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var town = $('#LFtown').val();
			var mtrno = $('#meterNum').val();
			var fdate = $('#fromDate').val();
			var tdate = $('#toDate').val();

			if(zone == "%")
			{
				zne = "ALL";
			}else{
				zne = zone;
			}
			if(circle == "%")
			{
				cir = "ALL";
			}else{
				cir = circle;
			}
			if(town == "%")
			{
				twn = "ALL";
			}else{
				twn  = town;
			}
			
			window.location.href="./ViewMtrDetailsExcel?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
			
		}

		function exportInstantaneous2PDF()
		{
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var town = $('#LFtown').val();
			var mtrno = $('#meterNum').val();
			var fdate = $('#fromDate').val();
			var tdate = $('#toDate').val();

			if(zone == "%")
			{
				zne = "ALL";
			}else{
				zne = zone;
			}
			if(circle == "%")
			{
				cir = "ALL";
			}else{
				cir = circle;
			}
			if(town == "%")
			{
				twn = "ALL";
			}else{
				twn  = town;
			}
			
			window.location.href="./ViewInstantaneous2PDF?zne="+zne+"&cir="+cir+"&twn="+twn+"&mtrno="+mtrno+"&fdate="+fdate+"&tdate="+tdate;
			
		}
		
	</script>

