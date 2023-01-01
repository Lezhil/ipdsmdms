
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
 <script src="jquery-1.3.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						flagView=true;
						App.init();
						TableEditable.init();
						FormComponents.init();
						//loadSearchAndFilter('sample_1');
						
						loadSearchAndFilter('sample_2');
						$('#MDMSideBarContents,#metermang,#mtrDtls').addClass(
								'start active ,selected');
						$(
								"#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

					});
	var flagView=null;
</script>
<div class="page-content">
	<c:if test="${not empty result}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${result}</span>
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<table>
						<tr>
							<td>Total Meters : <b>
									${totalMetersForMeterDetails+unmapped}
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
							<%-- <td> INSTALLED :<b>  ${INSTALLED} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td> --%>
							<td>INSTALLED :<b><a href="#"
									onclick="return getMetersBasedOnStatus('INSTALLED')">${totalMetersForMeterDetails} </a></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<%-- <td>INSTOCK :<b><a href="#"
									onclick="return getMetersBasedOnStatus('INSTOCK')">${INSTOCK}
								</a></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td> --%>
							<td>UNMAPPED :<b><a href="#"
									onclick="return getUnmappedData('UNMAPPED')">${unmapped}
								</a></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						<%-- 	<td>ISSUED :<b><a href="#"
									onclick="return getMetersBasedOnStatus('ISSUED')">${ISSUED} </a></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td>DAMAGED :<b><a href="#"
									onclick="return getMetersBasedOnStatus('DAMAGED')">${DAMAGED}
								</a></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td> --%>
						</tr>
					</table>
					<br> <br>



					<div class="btn-group">
						<button class="btn green" data-target="#stack1"
							data-toggle="modal" id="addData" value="addSingle"
							onclick="clearMyForm(this.value)">
							Add Single Meter <i class="fa fa-plus"></i>
						</button>
						&nbsp;&nbsp;&nbsp;
						<button class="btn blue" data-target="#stack1" data-toggle="modal"
							id="addUserType" value="addBatch"
							onclick="clearMyForm(this.value)">
							Add Meter Batch <i class="fa fa-plus"></i>
						</button>
					</div>
					<br /> <br />
					<table style="width: 25%">
						<tbody>
							<tr>
							<td>
							
							<select  name="parameterId" id="parameterId"
								class="form-control select2me input-medium" onchange="return clearDatafield()" >
								<option value="0">Select Parameter</option>
								<option value="meterslno">Meter Sl No</option>
								<option value="metermake">Meter Make</option>
								<option value="meterstatus">Meter Status</option>
								<option value="manufactureyear">Manufacture YearMonth</option>
							</select>
						</td>
							
								<!-- <td><input class="form-control input-medium"
									placeholder="Enter Meter No" name="meterNumId"
									autocomplete="off" id="meterNumId" /></td>
						 -->
						 
						 <td><input class="form-control input-medium"
									placeholder="XXXXXX" name="dataId"
									autocomplete="off" id="dataId" /></td>
									
									
								<td><button type="button" id="dataview" class="btn yellow"
										onclick="return getMeterData();" formmethod="post">
										<b>View</b>
									</button></td>
							</tr>
						</tbody>
									<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					</table>
					
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
							<li><a href="#" id=""
								onclick="exportPDF('sample_2','Meter_Details')">Export to PDF</a></li>
								<li>
								<a href="#" id="excelExport" onclick="exportToExcelMethod('sample_2', 'Meters Details')">Export to Excel</a>
								</li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
								<th>Edit</th>
								<th>MeterNo</th>
								<th>ConnectionType</th>
								<th>Meter Make</th>
								<th>Commissioning</th>
								<th>Model</th>
								<th>IP Period</th>
								<th>PT Ratio</th>
								<th>Meter Constant</th>
								<th>CT Ratio</th>
								<th>Tender No</th>
								<th>Accuracy Class</th>
								<th>Manufacture<br>YearMonth</th>
								<th>Current Rating</th>
								<th>Warranty Period</th>
								<th>Voltage Rating</th>
								<th>MeterStatus</th>
								<th>Entry By</th>
								<th>Entry Date</th>
								<th>Updated By</th>
								<th>Updated Date</th>
							</tr>
						</thead>
						<tbody id="meterDetailsId">

							<%-- <c:forEach var="element" items="${meterDetails}">
									<tr >
										<td><a href="#" onclick="editMeterDetails(this.id,${element.id})" id="editData${element.id}">Edit</a></td>
										<td>${element.meterno}</td>
										<td>${element.meter_connection_type}</td>
										<td>${element.meter_make}</td>
										<td>${element.meter_commisioning}</td>
										<td>${element.meter_model}</td>
										<td>${element.meter_ip_period}</td>
										<td>${element.pt_ratio}</td>
										<td>${element.meter_constant}</td>
										<td>${element.ct_ratio}</td>
										<td>${element.tender_no}</td>
										<td>${element.meter_accuracy_class}</td>
										<td>${element.month}</td>
										<td>${element.meter_current_rating}</td>
										<td>${element.warrenty_years}</td>
										<td>${element.meter_voltage_rating}</td>
										<td>${element.meter_status}</td>
										
									</tr>
									</c:forEach> --%>

						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
</div>

<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<span id="addMeterStackId"
						style="color: #474627; font-weight: bold;">Add Meter</span>
				</h4>
			</div>
			<span style="color: red; font-size: 16px;" id="accountNotExistMsg"></span>
			<div class="modal-body">
				<form:form action="" method="post" id="addMeterDetailsFormId"
					modelAttribute="addMeterDetailsFormId"
					commandName="addMeterDetailsFormId">


					<div class="row" hidden="true" id="mtrSlnoDivId">
						<div class="col-md-6">
							<label>Start Meter Sl No</label><span style="color: red">*</span>
							<form:input style="width:100%; float: left;" path="" type="text" 
								name="meternoStartId" id="meternoStartId" class="form-control"
								placeholder="XXXXXX"></form:input>
						</div>
						<div class="col-md-6">
							<label>End Meter Sl No</label><span style="color: red">*</span>
							<form:input style="width:100%; float: left;" path="" type="text"
								name="meternoEndId" id="meternoEndId" class="form-control"
								placeholder="XXXXXX"></form:input>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6" id="mtrPrfxDivId" hidden="true">
							<label>Meter Sl No Prefix</label><!-- <span style="color: red">*</span> -->
							<form:input style="width:100%; float: left;" path="" type="text"
								name="mtrPrefix" id="mtrPrefix" class="form-control"
								placeholder="XXXXX" ></form:input>
						</div>
						<div class="col-md-6" hidden="true" id="mtrdivId">
							<label>Meter Sl No</label><span style="color: red">*</span>
							<form:input style="width:100%; float: left;" path="meterno"
								type="text" name="meterno" id="meterno" class="form-control"
								placeholder="XXXXXXX" onchange="checkMeterNo(this.value)"></form:input>
						</div>
						<div class="col-md-6">
							<label>Meter Connection Type</label><span style="color: red">*</span>
							<form:select path="meter_connection_type" name="connectionTypeId"
								id="connectionTypeId" class="form-control form-group  placeholder-no-fix">
								<form:option value="0">Select </form:option>
								<form:option value="1">1-Phase</form:option>
								<form:option value="33">3-Phase 3-Wire</form:option>
								<form:option value="34">3-Phase 4-Wire</form:option>

							</form:select>
						</div>
					</div>
					<div hidden="true">
						<form:input style="width:100%; float: left;" path="id" type="text"
							name="meterId" id="meterId" class="form-control"></form:input>
					</div>
					<!--  form-control placeholder-no-fix-->
					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Meter Make</label><span style="color: red">*</span>
							<form:select path="meter_make" name="meterMakeId"
								id="meterMakeId" class="form-control form-group  ">
								<form:option value="0">Select MeterMake</form:option>
								<form:option value="GENUS">GENUS</form:option>
								<form:option value="HPL">HPL</form:option>
								<form:option value="SECURE">SECURE</form:option>
								<form:option value="L&T">L&T</form:option>
								<form:option value="L&G">L&G</form:option>
							</form:select>
						</div>
						<div class="col-md-6">
							<label>Meter Commissioning</label><span style="color: red">*</span>
							<form:select path="meter_commisioning" type="text"
								name="commisionId" id="commisionId" class="form-control form-group  ">
								<form:option value="0">Select </form:option>
								<form:option value="PRIMARY">PRIMARY</form:option>
								<form:option value="SECONDARY">SECONDARY</form:option>
							</form:select>
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Meter Model</label>
							<form:input type="text" path="meter_model" name="meterModelId"
								id="meterModelId" class="form-control" placeholder="Enter Meter Model"></form:input>
						</div>
						<div class="col-md-6">
							<label>Meter IP Period</label><span style="color: red">*</span>
							<form:select path="meter_ip_period" name="ipPeriodId"
								id="ipPeriodId" class="form-control form-group  ">
								<form:option value="0">Select IP Period</form:option>
								<form:option value="15">15 min</form:option>
								<form:option value="30">30 min</form:option>
								<form:option value="60">60 min</form:option>
							</form:select>
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>PT Ratio(KV)</label><span style="color: red">*</span>
							<form:input type="text" path="pt_ratio" name="ptratioId"
								id="ptratioId" class="form-control" placeholder="Enter PT_Ratio"></form:input>
						</div>
						<div class="col-md-6">
							<label>Meter Constant</label><span style="color: red">*</span>
							<form:input type="text" path="meter_constant"
								name="meterConstatntId" id="meterConstatntId"
								class="form-control" placeholder="Enter Meter Constant"
								autocomplete="off"></form:input>
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>CT Ratio(A)</label><span style="color: red">*</span>
							<form:input type="text" path="ct_ratio" name="ctRatioId"
								id="ctRatioId" class="form-control" placeholder="Enter CT_Ratio"></form:input>
						</div>
						<div class="col-md-6">
							<label>Tender NO</label>
							<form:input type="text" path="tender_no" id="tenderNoId"
								class="form-control" placeholder="Enter Tender No"></form:input>
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Meter Accuracy Class</label>
							<form:select path="meter_accuracy_class" name="mtrAccClassId"
								id="mtrAccClassId" class="form-control form-group  ">
								<form:option value="0">Select </form:option>
								<form:option value="1">1sec</form:option>
								<form:option value="0.5">0.5sec</form:option>
								<form:option value="0.2">0.2sec</form:option>
							</form:select>
						</div>
						<div class="col-md-6">
							<label>Manufacture Year-month</label>
							<div class="input-group">
								<form:input path="month" type="text" class="form-control from"
									name="mYearMnth" id="mYearMnth"></form:input>
								<span class="input-group-btn"><button class="btn default"
										type="button">
										<i class="fa fa-calendar"></i>
									</button></span>
							</div>
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Meter Current Rating</label>
							<form:input type="text" path="meter_current_rating"
								name="curntRatingId" id="curntRatingId" class="form-control"
								placeholder="Enter Meter Current Rating"></form:input>
						</div>
						<div class="col-md-6">
							<label>Warranty period</label><span style="color: red"></span>
							<%-- <div class="input-group input-medium date date-picker"
								data-date-format="yyyy" data-date-viewmode="years">
								<form:input path="warrenty_years" type="text"
									class="form-control" name="warrentyId" id="warrentyId"></form:input>
								<span class="input-group-btn"><button class="btn default"
										type="button">
										<i class="fa fa-calendar"></i>
									</button></span>
							</div> --%>
						 <div class="input-group ">
								
									<form:input path="warrenty_years" type="text"
									class="form-control from2" name="warrentyId" id="warrentyId" ></form:input> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								  </div>  
						</div>
					</div>

					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Meter Voltage Rating</label>
							<form:input type="text" path="meter_voltage_rating"
								name="mtrVltgRatId" id="mtrVltgRatId" class="form-control"
								placeholder="Enter Meter Voltage Rating"></form:input>
						</div>
						<div class="col-md-6" id="mtrStatusId">
							<label>Meter Status</label><span style="color: red">*</span>
							<form:select path="meter_status" name="mtrStatus" id="mtrStatus"
								class="form-control form-group  ">
								<form:option value="0">Select </form:option>
								<form:option value="INSTOCK">IN STOCK</form:option>
								<%-- <form:option value="INSTALLED">INSTALLED</form:option>
								<form:option value="DAMAGED">DAMAGED</form:option> --%>
							</form:select>
						</div>

					</div>
					<br>
					<c:if test="${projectName eq 'SPDCL'}">
					<div class="row">

						<div class="col-md-6">
							<label>Store</label><span style="color: red">*</span>
							<form:select path="strLoc" name="strLoc" id="strLoc"
								class="form-control form-group  ">
								<form:option value="0">Select Store</form:option>
								<form:option value="BCITS">BCITS</form:option>
								<form:option value="APSPDCL">APSPDCL</form:option>

							</form:select>
						</div>
						<div class="col-md-6">
							<label>Store Description</label>
							<form:input type="text" path="strDesc" name="strDesc"
								id="strDesc" class="form-control"
								placeholder="Enter Meter Voltage Rating"></form:input>
						</div>

					</div>
					</c:if>
					<div class="row">
					<div class="col-md-6">
							<label>Meter Digits</label><span style="color: red">*</span>
							<form:input type="text" path="meterdigit" name="meterdigit"
								id="meterdigitId" class="form-control"
								placeholder="Enter Meter Digits"></form:input>
						</div>

					</div>
					<div class="modal-footer">
						<button class="btn blue pull-right" id="addMeterbtnId"
							type="button" value="save" onclick="validation(this.value);"
							hidden="true">ADD</button>
						<button class="btn blue pull-right" id="updateMeterbtnId"
							type="button" value="update" onclick="validation(this.value);"
							hidden="true">UPDATE</button>
						<button class="btn blue pull-right" id="addMeterBatchbtnId"
							type="button" value="addBatch" onclick="validation(this.value);"
							hidden="true">ADD</button>
						<button type="button" data-dismiss="modal" class="btn">Cancel</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>


<div id="stack6" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 73%;" >
							
								<div class="modal-content">
								
									<div class="modal-header">
									<div id="image" hidden="true" style="text-align: center;height: 100%;width: 100%;" >
									<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
                         <h3 id="loadingText"><font id="masterTd">Loading..... Please wait.</font> 
						 </h3> 
<!-- 						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:4%;height: 4%;margin-right: 10px;"> -->
						</div>
									<div id="closeShow">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title green" ><b><span id="heading" ></span>  METER DETAILS</b>
										
										</h4>
										
										</div>
									</div>

									<div class="modal-body">
										<br>
										<div class="row">
											<div class="col-md-12" id="meterdataId">
											 
													
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>




<div id="stack2" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h5 class="modal-title" id="myDiv">Please Select Date</h5>
			</div>

			<div class="modal-body">
				<div class="row">

					<div class="col-md-6">
						<div class="form-group">
							<label>From Date : </label>

							<div class="input-group date date-picker"
								data-date-format="yyyy-mm-dd" data-date-end-date="0d"
								data-date-viewmode="years">
								<input type="text" placeholder="Select From Date"
									class="form-control" name="fromDate" id="fromDate"
									style="cursor: pointer"> <span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label>To Date : </label>

							<div class="input-group date date-picker"
								data-date-format="yyyy-mm-dd" data-date-end-date="0d"
								data-date-viewmode="years">
								<input type="text" class="form-control"
									placeholder="Select To Date" name="toDate" id="toDate"
									style="cursor: pointer"> <span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>

					<div class="col-md-6" style="margin-left: 51px;">
						<button class="btn blue pull-right" id="btnId"
							onclick="return issuedMeters(this.value);">View</button>
						<!--  <button type="button" data-dismiss="modal" class="btn">Cancel</button></div> -->
					</div>


				</div>

			</div>
		</div>
	</div>
</div>


<div id="stack3" class="modal fade" tabindex="-1" data-width="400">
	<div class="modal-dialog" style="width: 73%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>

				<!-- <h4 class="modal-title" id="myDiv"><span id="spanissuedId" ></span> Meter Details</h3>	 -->
				<h4 class="modal-title green">
					<b><span id="spanissuedId"></span> METER DETAILS</b>

				</h4>
			</div>
              <div class="row">
               <button class="btn blue pull-right" id="btnId"
							onclick="tableToExcel4('sample_1', 'Meter Details')" style="margin-right: 25px;border-bottom-width: -7px;margin-bottom: -14px;border-top-width: -16px;margin-top: 10px;" >Export To Excel</button>
        
             <!-- <div class="col-md-4">
                <a href="#" id="excelExport" class="btn blue pull-right"
						type="button"
						onclick="exportGridData();"
						style="position: relative; left: 210%;top: 25px;margin-right: 163px;margin-bottom: -41px;margin-left: 0px;border-left-width: -1px;">Export to Excel</a>
               </div> -->
               </div>
			<div class="modal-body">
			
				<div class="row">
				
					<div class="col-md-12" id="issedMetersId"></div>
               
				</div>
			</div>
		</div>
	</div>
</div>



<script>
	function validation(btnValue) {
		
	
		var regex = /^[a-zA-Z ]*$/;
		var curmonth=${curMonth};	
		var curyear=${curYear};
		var startMeter = $("#meternoStartId").val();
		var endMeter = $("#meternoEndId").val();
		var meterno = $("#meterno").val();
		var connectionType = $("#connectionTypeId").val();
		var meterMake = $("#meterMakeId").val();
		var commision = $("#commisionId").val();
		var model = $("#meterModelId").val();
		var ip = $("#ipPeriodId").val();
		var ptratio = $("#ptratioId").val();
		var meterConstant = $("#meterConstatntId").val();
		var ctratio = $("#ctRatioId").val();
		var tenderno = $("#tenderNoId").val();
		var accuracy = $("#mtrAccClassId").val();
		var manfacture = $("#mYearMnth").val();
		var currntRating = $("#curntRatingId").val();
		var warrenty = $("#warrentyId").val();
		var voltgRating = $("#mtrVltgRatId").val();
		var mtrStatus = $("#mtrStatus").val();
		var mtrPrefix = $("#mtrPrefix").val();
		var strLoc = $("#strLoc").val();
		var meterdigit=$("#meterdigitId").val();
		var projectName=${'projectName'};

		//old
		
	
		if (btnValue == "addBatch") {
			if (startMeter == '') {
				bootbox.alert("Please enter startMeter Sl No");
				return false;
			}
			if (endMeter == '') {
				bootbox.alert("Please enter endMeter Sl No");
				return false;
			}

			if (isNaN(startMeter)) {
				bootbox.alert("Please enter only numbers in Start Meter No Field");
				$("#meternoStarId").val("");
				return false;
			}
			if (isNaN(endMeter)) {
				bootbox.alert("Please enter only numbers in End Meter No Field");
				$("#meternoEndId").val("");
				return false;
			}
			if(startMeter.length<5){
				bootbox.alert("Start Meter No should be minimum 6 digits");
				$("#meternoStartId").val("");
			return false;
				}
			if(endMeter.length<5){
				bootbox.alert("End Meter No should be minimum 6 digits");
				$("#meternoEndId").val("");
			return false;
				}
			var total = endMeter - startMeter;
			
			if (endMeter < startMeter) {
				bootbox.alert("End Meter Sl No should be greater than  Start Sl No");
				$("#meternoEndId").val("");
				return false;
			}
			if (total > 100) {
				bootbox.alert("More than 100 meter will not allowed to add");
				$("#meternoEndId").val("");
				return false;
			};
			if (mtrPrefix != "") {
				if (mtrPrefix.length > 4) {
					bootbox.alert("Prefix should be less than 4 character");
					return false;
				} else if (!mtrPrefix.match(regex)) {
					bootbox.alert("Meter Sl No Prefix should contain only alphabets");
					return false;
				}

			}
		}
		if (btnValue == "update" || btnValue == "save") {
			if (meterno == "") {
				bootbox.alert("Please Enter meterno");
				return false;
			};
		}
		if (connectionType == 0) {
			bootbox.alert("Please Select Connection Type");
			return false;
		}
		if (meterMake == 0) {
			bootbox.alert("Please Select Meter Make");
			return false;
		}

		if (commision == 0) {
			bootbox.alert("Please Select Commisioning");
			return false;
		}
		/* 
		 if(model=="")

		 {
		 bootbox.alert("Please Enter Meter Model");
		 return false;
		 } */
		if (ip == 0) {
			bootbox.alert("Please Select Meter IP PERIOD");
			return false;
		}
		if (ptratio == "" || isNaN(ptratio)) {
			bootbox.alert("PT Ratio  and Enter Only Numbers");
			return false;
		}
		if (meterConstant == "" || isNaN(meterConstant)) {
			bootbox.alert("Meter Constant and Enter Only Numbers");
			return false;
		}

		if (ctratio == "" || isNaN(ctratio)) {
			bootbox.alert("CT Ratio and Enter Only Numbers");
			return false;
		}

		if (btnValue == "save") {
		if (mtrStatus == "0") {
			bootbox.alert('Please Select Meter Status');
			return false;
		};
		}
		

		/* if(tenderno=="" )
		{
			bootbox.alert('please Enter Tender');
			return false;
		}
		
		if(accuracy==0 )
		{
			bootbox.alert("Please Select Meter Accuracy Class");
			return false;
		}*/
		if (manfacture.size != "") {
			if (manfacture > curmonth) {
				bootbox.alert("Manfacture Year Month Should not be Future Month");
				return false;
			}
		}

		/*  if(currntRating=='')
			{
				bootbox.alert('Please Enter Current Rating');
				return false;
			} */
		if (warrenty != "") {
			if (warrenty <= curyear){
				bootbox.alert('Warrenty Period must be future year');
			return false;
			}
		}

		/*
		if(voltgRating=='' )
		{
			bootbox.alert('Please Enter Voltage Rating ');
			return false;
		} */
		
	/* 	if (mtrStatus == "0") {
			bootbox.alert('Please Select Meter Status');
			return false;
		}
		if (strLoc == "0") {
			bootbox.alert('Please Select Store ');
			return false;
		} */
		if(projectName=="SPDCL"){
		if (mtrStatus == "0") {
			bootbox.alert('Please Select Meter Status');
			return false;
		}
		}

		if (meterdigit=="") {
			bootbox.alert("Please enter  Meter Digit ");
			return false;
		}
		
		if (isNaN(meterdigit)) {
			bootbox.alert("Please enter only numbers in Meter Digit Field");
			return false;
		}
		
		if (meterdigit>10 ) {
			bootbox.alert("meterdigit should be less than 10");
			return false;
		}
		
		 if (btnValue == "update") {
			$("#addMeterDetailsFormId").attr('action',
					'./updateMeterInventoryDetails').submit();
		}
		if (btnValue == "save") {
			$("#addMeterDetailsFormId").attr('action',
					'./addMeterInventoryDetails').submit();
		}
		if (btnValue == "addBatch") {
			$("#addMeterDetailsFormId").attr('action',
					'./addBatchMeterInventoryDetails').submit();
		}; 
	}
	function clearMyForm(param) {
		document.getElementById('addMeterDetailsFormId').reset();
		document.getElementById('addMeterStackId').innerHTML = '';
		document.getElementById('addMeterStackId').innerHTML = 'Add Meter';
		$("#addMeterbtnId").hide();
		$("#updateMeterbtnId").hide();
		$("#updateMeterbtnId").hide();
		if (param == "addSingle") {
			$("#addMeterbtnId").show();
			$("#updateMeterbtnId").hide();
			$("#addMeterBatchbtnId").hide();
			$("#mtrdivId").show();
			$("#mtrPrfxDivId").hide();
			$("#mtrSlnoDivId").hide();
			$("#mtrStatusId").show();
			$("#meterno").attr("readonly", false);
		}
		if (param == "addBatch") {
			$("#addMeterbtnId").hide();
			$("#updateMeterbtnId").hide();
			$("#addMeterBatchbtnId").show();
			$("#mtrdivId").hide();
			$("#mtrPrfxDivId").show();
			$("#mtrSlnoDivId").show();
			$("#mtrStatusId").show();
			document.getElementById('addMeterStackId').innerHTML = "";
			document.getElementById('addMeterStackId').innerHTML = 'Add Meter Batch';
		}
	}

	function editMeterDetails(param, opera) {
		
 		$("#mtrdivId").show();
		$("#mtrPrfxDivId").hide();
		$("#mtrSlnoDivId").hide();
		$("#addMeterbtnId").hide();
		$("#updateMeterbtnId").show();
		$("#addMeterBatchbtnId").hide();
		$("#mtrStatusId").hide();
		$("#meterno").attr("readonly", true);
		document.getElementById('addMeterDetailsFormId').reset();
		document.getElementById('addMeterStackId').innerHTML = "";
		document.getElementById('addMeterStackId').innerHTML = 'Modify Meter';
		var operation = parseInt(opera);
		$.ajax({
			type : "GET",
			url : "./getMeterInventoryData/" + operation,
			dataType : "json",
			success : function(response) {
				$("#meterId").val(response.id);
				$("#meterno").val(response.meterno);
				$("#connectionTypeId").val(response.connection_type);
				$("#meterMakeId").val(response.meter_make);
				$("#commisionId").val(response.meter_commisioning);
				$("#meterModelId").val(response.meter_model);
				$("#ipPeriodId").val(response.meter_ip_period);
				$("#ptratioId").val(response.pt_ratio);
				$("#meterConstatntId").val(response.meter_constant);
				$("#ctRatioId").val(response.ct_ratio);
				$("#tenderNoId").val(response.tender_no);
				$("#mtrAccClassId").val(response.meter_accuracy_class);
				$("#mYearMnth").val(response.month);
				$("#curntRatingId").val(response.meter_current_rating);
				$("#warrentyId").val(response.warrenty_years);
				$("#mtrVltgRatId").val(response.meter_voltage_rating);
				$("#mtrStatus").val(response.meter_status);
				$("#strLoc").val(response.strLoc);
				$("#strDesc").val(response.strDesc);
				$("#meterdigitId").val(response.meterdigit);
			}
		});

		$('#' + param).attr("data-toggle", "modal");
		$('#' + param).attr("data-target", "#stack1");

	};

	function checkMeterNo(meterno) {
		var regex = /^[a-zA-Z0-9]*$/;
		if (meterno.length < 5) {

			bootbox.alert("Meter Sl No Length must be minimum 6 charecter");
			$("#meterno").val("");
			return false;
		}
		if (!meterno.match(regex)) {
			bootbox.alert("Special charecter Not Allowed For Meter Sl No");
			$("#meterno").val("");
			return false;
		}
		$.ajax({
			type : "GET",
			url : "./checkMeterNoInInventory/" + meterno,
			dataType : "json",
			success : function(response) {
				if (response.length > 0) {
					// $("#accountNotExistMsg").html("'"+meterno+"'  Meter No Aleready Present"); 
					bootbox.alert("'" + meterno
							+ "'  Meter No Already Present");
					$("#meterno").val("");
				}
			}
		});
	}

	function getMeterData() {
		var parameter=$("#parameterId").val();
		var data=$("#dataId").val().trim();
	
		if(parameter=='0'){
				bootbox.alert("Please Select Any one parameter");
				return false;
			}
		if(data==''){
			 $(function() {
		            $("#dataId").focus();
		        });
				bootbox.alert("Please Enter Apropriate Data to Search");
				return false;
			}
		
	 	/* alert("p--"+parameter);
		alert("data--"+data); */
		 
		if(parameter=="manufactureyear")
		{
			if(isNaN(data)){
				bootbox.alert("Manufacture Year should be like this 'yyyyMM' format");
				return false;
				}
		}

		 $("#meterDetailsId").empty();
		$('#imageee').show();
		$.ajax({
					type : "GET",
					//url : "./checkMeterNoInInventory/" + meterno,
					url : "./getMeterInventoryBasedOnData",
					dataType : "json",
					data:{data:data,parameter:parameter},
					success : function(response) {
						flagView=false;
						$('#imageee').hide();
						if (response.length > 0) {
							var html = "";
						//	var conType = "";
							for (var i = 0; i < response.length; i++) {
								var data = response[i];
								if (data[1] == "33") {
									conType = "3-Phase 3-Wire";
								}
								if (data[1] == "34") {
									conType = "3-Phase 4-Wire";
								}
								if (data[1] == "1") {
									conType = "1-Phase";
								}

								html += "<tr>";
								html += "<td><a href='#' data-toggle='modal' data-target='#stack1' onclick='editMeterDetails(this.id,\""
										+ data[20]
										+ "\")' id='editData'>Edit</a></td>";
								html += "<td>"
										+ (data[0] == null ? ""
												: data[0]) + "</td>";
								html += "<td>" + conType + "</td>";
								html += "<td>"
										+ (data[2] == null ? ""
												: data[2]) + "</td>";
								html += "<td>"
										+ (data[3] == null ? ""
												: data[3])
										+ "</td>";
								html += "<td>"
										+ (data[4] == null ? ""
												: data[4]) + "</td>";
								html += "<td>"
										+ (data[5] == null ? ""
												: +data[5])
										+ "</td>";
								html += "<td>"
										+ (data[6] == null ? ""
												: +data[6]) + "</td>";
								html += "<td>"
										+ (data[7] == null ? ""
												: data[7])
										+ "</td>";
								html += "<td>"
										+ (data[8] == null ? ""
												: data[8]) + "</td>";
								html += "<td>"
										+ (data[9] == null ? ""
												: data[9]) + "</td>";
								html += "<td>"
										+ (data[10] == null ? ""
												: data[10])
										+ "</td>";
								html += "<td>"
										+ (data[11] == null ? "" : data[11])
										+ "</td>";
								html += "<td>"
										+ (data[12] == null ? ""
												: data[12])
										+ "</td>";
								html += "<td>"
										+ (data[13] == null ? ""
												: data[13])
										+ "</td>";
								html += "<td>"
										+ (data[14] == null ? ""
												: data[14])
										+ "</td>";
								html += "<td>"
										+ (data[15] == null ? ""
												: data[15]) + "</td>";
								html += "<td>"
										+ (data[16] == null ? ""
												: data[16]) + "</td>";
								html += "<td>"
										+ (data[17] == null ? ""
												: moment(data[17])
														.format('YYYY-MM-DD HH:mm:ss'))
										+ "</td>";
								html += "<td>"
										+ (data[18] == null ? ""
												: data[18]) + "</td>";
								html += "<td>"
										+ (data[19] == null ? ""
												: moment(data[19])
														.format('YYYY-MM-DD HH:mm:ss'))
										+ "</td>";
								html += "</tr>";
							}
							clearTabledataContent('sample_2');
							$("#meterDetailsId").html(html);
							loadSearchAndFilter('sample_2');
						} else {
							if(parameter=="meterslno")
							{
							bootbox.alert("Data Not Found for Entered Meterno");
							return false;
							}
							if(parameter=="metermake")
							{
							bootbox.alert("Data Not Found for Entered Meter Make");
							return false;
							}
							if(parameter=="meterstatus")
							{
							bootbox.alert("Data Not Found for Entered Meter Status");
							return false;
							}
							if(parameter=="manufactureyear")
							{
							bootbox.alert("Data Not Found for Entered Manufacture Year Month");
							return false;
							}
						}
					}
				});
	}
	function getMetersBasedOnStatus(status) {
		$('#stack6').modal('hide');
		$("#meterBody").empty();
		$("#heading").text("");
		if (status == "INSTALLED") {
			$("#heading").text("INSTALLED ");
		}
		if (status == "INSTOCK") {
			$("#heading").text("INSTOCK ");
		}
		if (status == "ISSUED") {
			$("#heading").text("ISSUED ");
		}
		if (status == "DAMAGED") {
			$("#heading").text("DAMAGED ");
		}
		$("#meterdataId").empty();
		$('#imageee').show();
		var date = "";
		$
				.ajax({
					type : "GET",
					url : "./getMetersBasedOnStatus",
					data : {
						status : status,
						date : date
					},
					dataType : "json",
					success : function(response) {
						//alert(response);
						$('#imageee').hide();
						var html = "<table class=table table-striped table-hover table-bordered id=sample_1><thead><tr>"
								+ "<th>METERNO</th><th>LOCATION NAME</th> <th>LOCATION ID</th><th>SUBSTATION</th><th>SUBDIVISION</th>"
								+ "<th>DIVISION</th><th>CIRCLE</th> <th>LAST COMMUNICATION</th></tr></thead><tbody>";
						if (response.length > 0) {
							for (var i = 0; i < response.length; i++) {
								data = response[i];
								/* if (data[6] == "33") {
									conType = "3-Phase 3-Wire";
								}
								if (data[6] == "34") {
									conType = "3-Phase 4-Wire";
								}
								if (data[6] == "1") {
									conType = "1-Phase";
								} */
								html += "<tr>" + " <td>"
										+ (data[0]== null ? ""
												: data[0])
										+ "</td>"
										+ " <td>"
										+ (data[1] == null ? ""
												: data[1])
										+ "</td>" //phase
										+ " <td>"
										+ (data[2] == null ? ""
												: data[2])
										+ "</td>" //mtrtype
										+ " <td>"
										+ (data[3] == null ? ""
												: data[3])
										+ "</td>" //mtrmke
										+ " <td>"
										+ (data[4] == null ? ""
												: data[4])
										+ "</td>" //category
										+ " <td>"
										+ (data[5] == null ? ""
												: data[5])
										+ "</td>" // Location
										+ " <td>"
										+ (data[6] == null ? ""
												: data[6])
										+ "</td>" // Location
										/* + " <td>"
										+ conType
										+ "</td>" // subdiv */
										+ " <td>"
										+ (data[7] == null ? ""
												: moment(data[7])
														.format('YYYY-MM-DD HH:mm:ss'))
										+ "</td>" // subdiv
										+ " </tr>";

							}
							html += "</tbody></table>";
							$('#stack6').modal('show');
							$("#meterdataId").empty();
							$("#meterdataId").append(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("DATA NOT FOUND");
							return false;
						}
					}
				});
	}


	function getUnmappedData(status) {
		$('#stack6').modal('hide');
		$("#meterBody").empty();
		$("#heading").text("");
		if (status == "UNMAPPED") {
			$("#heading").text("UNMAPPED ");
		}
		
		$("#meterdataId").empty();
		$('#imageee').show();
		//var date = "";
		$
				.ajax({
					type : "GET",
					url : "./getUnmappedMetersData",
					dataType : "json",
					success : function(response) {
						
						$('#imageee').hide();
						var html = "<table class=table table-striped table-hover table-bordered id=sample_1><thead><tr>"
								+ "<th>METERNO</th><th>MANUFACTURER NAME</th> <th>FIRST COMMUNICATION</th><th>LAST COMMUNICATION</th>"
								+ "</tr></thead><tbody>";
						if (response.length > 0) {
							for (var i = 0; i < response.length; i++) {
								data = response[i];
								/* if (data[6] == "33") {
									conType = "3-Phase 3-Wire";
								}
								if (data[6] == "34") {
									conType = "3-Phase 4-Wire";
								}
								if (data[6] == "1") {
									conType = "1-Phase";
								} */
								html += "<tr>" + " <td>"
										+ (data[0]== null ? ""
												: data[0])
										+ "</td>"
										+ " <td>"
										+ (data[1] == null ? ""
												: data[1])
										+ "</td>" //phase
										+ " <td>"
										+ (data[2] == null ? ""
												: moment(data[2])
												.format('YYYY-MM-DD HH:mm:ss'))
										+ "</td>" //mtrtype
										+ " <td>"
										+ (data[3] == null ? ""
												: moment(data[3])
												.format('YYYY-MM-DD HH:mm:ss'))
										+ "</td>" //mtrmke
                                    	+ " </tr>";

							}
							html += "</tbody></table>";
							$('#stack6').modal('show');
							$("#meterdataId").empty();
							$("#meterdataId").append(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("DATA NOT FOUND");
							return false;
						}
					}
				});
	}
	
	function issuedMeters(status) {
		/* $("#meterBody").empty();
		$("#heading").text("");
		if(status=="INSTALLED"){
			$("#heading").text("INSTALLED ");
			}
		if(status=="INSTOCK"){
			$("#heading").text("INSTOCK ");
			}
		if(status=="ISSUED"){
			$("#heading").text("ISSUED ");
			}
		if(status=="DAMAGED"){
			$("#heading").text("DAMAGED ");
			} */
		
		$("#issedMetersId").empty();
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		if (fromDate == "") {
			bootbox.alert("Please Select From Date");
			return false;
		}
		if (toDate == "") {
			bootbox.alert("Please Select To Date");
			return false;
		}
		$.ajax({
					type : "GET",
					url : "./getMetersBasedOnStatus",
					data : {
						status : status,
						fromDate : fromDate,
						toDate : toDate
					},
					dataType : "json",
					success : function(response) {
						var html = "<table class=table table-striped table-hover table-bordered id=sample_3><thead><tr>"
								+ "<th>METERNO</th><th>METER_STATUS</th> <th>METER MAKE</th>"
								+ "<th>CONNECTION TYPE</th><th>SURVEYOR NAME</th><th>I&C Code</th> <th>ENTRY DATE</th></tr></thead><tbody>";
						if (response.length > 0) {

							for (var i = 0; i < response.length; i++) {
								data = response[i];
								if (data[3] == "33") {
									conType = "3-Phase 3-Wire";
								}
								if (data[3] == "34") {
									conType = "3-Phase 4-Wire";
								}
								if (data[3] == "1") {
									conType = "1-Phase";
								}
								html += "<tr>"
										+ " <td>"
										+ (data[0] == null ? "" : data[0])
										+ "</td>"
										+ " <td>"
										+ (data[1] == null ? "" : data[1])
										+ "</td>" //phase
										+ " <td>"
										+ (data[2] == null ? "" : data[2])
										+ "</td>" //mtrtype
										+ " <td>"
										+ conType
										+ "</td>" // subdiv
										+ " <td>"
										+ (data[4] == null ? "" : data[4])
										+ "</td>" //mtrtype
										+ " <td>"
										+ (data[6] == null ? "" : data[6])
										+ "</td>" 
										+ " <td>"
										+ (data[5] == null ? "" : moment(
												data[5]).format('YYYY-MM-DD'))
										+ "</td>" // subdiv
										+ " </tr>";

							}
							html += "</tbody></table>";
							$('#stack3').modal('show');
							$("#issedMetersId").empty();
							$("#issedMetersId").append(html);
							loadSearchAndFilter('sample_3');
						} else {
							bootbox.alert("DATA NOT FOUND");
							return false;
						}
						$('#stack2').modal('hide');

					}
				});
		 //$("#toDate").clear();
	}

	function spanChange(status) {
		$("#spanissuedId").text("");
		$("#btnId").val("ISSUED");
		if (status == "INSTALLED") {
			$("#spanissuedId").text("INSTALLED ");
			$("#btnId").val("INSTALLED");
		}

		if (status == "ISSUED") {
			$("#spanissuedId").text("ISSUED ");
			$("#btnId").val("ISSUED");
		}

	}
	function exportGridData()
	{
		 $("#tab_content").table2excel({
             filename: "ExcelTable.xls"
         });
	  
    
    }
	
	/* 	var exportGridFormat=$("#ExportGridFormat").val();
		if(exportGridFormat !=='select')
		{
		if(!"pdfFormat".localeCompare(exportGridFormat)){
		html2canvas(document.getElementById('tab_content'), {
	        onrendered: function (canvas) {
	            var data = canvas.toDataURL();
	            var docDefinition = {
	                content: [{
	                    image: data,
	                    width: 500
	                }]
	            };
	            pdfMake.createPdf(docDefinition).download("PdfTable.pdf");
	         }
	       });
	     } 
		else
		  {*/



			function clearDatafield(){
					$("#dataId").val("");
				}

		 
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

			$('.from2').datepicker
			({
			    format: "yyyymm",
			    minViewMode: "years",
			    autoclose: true,
			    startDate :new Date(year, month+1, '31'),
			     //endDate: new Date(year, month-1, '31') 
			});
	
			function exportPDF()
			{
				var parameter=$("#parameterId").val();
				var data=$("#dataId").val().trim();

				if(parameter=='0'){
					flagView=true;
					bootbox.alert("Please Select Any one parameter");
					return false;
				}
			if(data==''){
				 $(function() {
			            $("#dataId").focus();
			        });
				    flagView=true;
					bootbox.alert("Please Enter Apropriate Data to Search");
					return false;
				}

			if (flagView) {
				bootbox.alert("Please click on view button");
				return false; 
			}

				window.location.href=("./MeterDetailsPDF?parameter="+parameter+"&data="+data);
			}


			function exportToExcelMethod(){
				var parameter=$("#parameterId").val();
				var data=$("#dataId").val().trim();
				if(parameter=='0'){
					flagView=true;
					bootbox.alert("Please Select Any one parameter");
					return false;
				}
			if(data==''){
				 $(function() {
			            $("#dataId").focus();
			        });
				    flagView=true;
					bootbox.alert("Please Enter Apropriate Data to Search");
					return false;
				}
			if (flagView) {
				bootbox.alert("Please click on view button");
				return false; 
			}
				
				window.location.href=("./MeterDetailsExcel?parameter="+parameter+"&data="+data);

				}
			
</script>

