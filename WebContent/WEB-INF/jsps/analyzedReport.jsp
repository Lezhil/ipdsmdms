
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 
		 $('#MDMSideBarContents,#reportsId,#analyzedReports').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		 
    	 $("#analysedPrintId").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});

    
		 $('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	   
	   	    if(selectedItem==null)
	   	    {
	   	    	$("#circleId").val("0").trigger("change"); 	 
	   	    }else{
	   	    	$("#circleId").val(selectedItem).trigger("change");
	   	    }
      
	   	    if('${result}' !='')
	   	    {
	   	    	alert('${result}');
	   	    }
	   	    
	});
</script>

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>
<script type="text/javascript">

function showCircle(zone) {
	//alert("ib circle"+zone);
	var zone = "All";
	$.ajax({
		url : './showCircleMDM' + '/' + zone,
		type : 'GET',

		success : function(response) {
			var html = '';
			html += "<option value=''></option>";
			for (var i = 0; i < response.length; i++) {
				html += "<option  value='"+response[i]+"'>" + response[i]
						+ "</option>";
			}

			$("#circleId").html(html);
			//$('#circleId').select2();
		}
	});
}

function showDivision(circle) {
	var zone = "All";
	$.ajax({
				url : './showDivisionMDM' + '/' + zone + '/' + circle,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='ALL'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#divisionId").html(html);
					$('#division').select2();
				}
			});
}
//impl....
function showDivisionss(circle_code) {
	var zone = "All";
	$.ajax({
				url : './showDivisionsByCircleCode' + '/' + circle_code,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value=''>ALL</option>";
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
	var circle = $('#circleId').val();
	//var divarray=new Array();
	var x=document.getElementById("divisionId");
	//alert(x.value);
	options = x.getElementsByTagName('option'),
	   values  = [];

	    for (var i=options.length; i--;) {
	        if (options[i].selected) values.push(options[i].value)
	    }

	    console.log("div values---"+values)
	 /* for (var i = 0; i < x.options.length; i++) {
	     if(x.options[i].selected ==true){
	          alert(x.options[i].selected);
	      }
	  } */
	var zone = "All";
	$.ajax({
				url : './showSubdivByDivMDM' + '/' + zone + '/' + circle
						+ '/' + values,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response1) {

					//alert("subdiv  "+response1);
					var html = '';
					html += "<select id='sdoCode' name='sdoCode' onchange='return showmeterListBySubdiv(this.value);' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value=''>ALL</option>";
					for (var i = 0; i < response1.length; i++) {
						//var response=response1[i];
						html += "<option value='"+response1[i]+"'>"
								+ response1[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#subdivTd").html(html);
					$('#subdiv').select2();
				}
			});
}
</script>
<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Analyzed Reports</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="" id="cmriId" method="POST">
							
							<table>
							<tbody>
							<tr>
							<th class="block">Zone :</th>
								<td><select id="zoneId"
									class="form-control select2me input-medium" name="zoneId" onchange="return showCircle(this.value);">
										<option value="0">Select</option>
										<option value="JVVNL">JVVNL</option>
								</select></td>
								<th class="block">Circle :</th>
								<td><select id="circleId"
									class="form-control select2me input-medium" name="circleId" onchange="return showDivision(this.value);">
										
								</select></td>
								<th class="block">Division :</th>
								<td><select id="divisionId"
									class="form-control select2me input-medium" name="divisionId"  onchange="return showSubdivByDiv(this.value);">
										<option value="0">Select</option>
										<option value="ALL">ALL</option>
											<option value=""></option>
								</select></td>
								<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr><tr>
								<th class="block">Sub Division</th>
								<td><select id="sdoId"
									class="form-control select2me input-medium" name="sdoId" >
										<option value="0">Select</option>
										<option value="ALL">ALL</option>
											<option value=""></option>
								</select></td>
                          
							
							<th class="block">Rdngmonth :</th>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							
							<!--<td>CIRCLE :</td>
								<td><select id="circleId"
									class="form-control placeholder-no-fix" name="circleId" onchange="return getSdoName(this.value);">
										<option value="0">Select</option>
										<c:forEach var="element" items="${circle}">
											<option value="${element}">${element}</option>
										</c:forEach>
								</select></td>
								
								 <td>SUBDIVISION :</td>
								<td ><select id="sdoId"
									class="form-control input-medium" name="sdoId"  onchange="return getMnpOnSdoname();">
									<option value="0">select</option>
								</select></td> -->
							
							
							<%-- <tr>
							<td>Select MNP : </td>
							<td>
							<select  id="mnp" class="form-control placeholder-no-fix" name="mnp" >
							<option value="0">select</option>
							<!-- <option value="%">All</option> -->
							<c:forEach var="element" items="${MNP}">
							<option value="${element}">${element}</option>
							</c:forEach>
							</select>
							</td>
							</tr> --%>
							
							
							
							<td>Select Report : </td>
							<td>
							<select  id="reportName" class="form-control placeholder-no-fix" name="reportName" >
							<option value="0">select</option>
							<!-- <option value="CNP">CNP Report</option> -->
							<option value="DATEWISEREPORT">Date Wise Report</option>
							<option value="ENERGYWISEREPORT">Energy Wise Report</option>
							<option value="TAMPEREVENTREPORT">Tamper Report</option>
							<option value="TRANSACTIONREPORT">Instantaneous Report</option>
						 	<option value="POWERFACTORREPORT">Power Factor Report</option>
							 <option value="USAGEINDEXING">Usage Indexing Report</option>
							 <option value="TAMPEREVENTSUMMARY">Tamper Event Summary Report</option>
							

							<!--<option value="METERCHANGE">MeterChange Report</option> 
						<option value="CMRILIST">CMRI list Report</option> -->
							<!-- <option value="EVENTWISEREPORT">Event Wise Report</option> -->
							<!-- <option value="FAULTY">FAULTY Report</option> -->
							<!-- <option value="MANUAL">MANUAL Reading Report</option> -->
							<!-- <option value="LOADUTILIZATIONREPORT">Load Utilization Report</option>
							<option value="STATICMETERCLASSREPORT">Static Class Report</option> -->
							
							
							<!-- <option value="USAGEINDEXREPORT">Usage Index Report</option> -->
							<!-- <option value="WIRINGVERIFICATIONREPORT">Wiring Verification Report</option> -->
							<!-- <option value="OTHERMAKE">OtherMake Report</option> -->
							</select>
							</td>
							</tr>
							</tbody>
							</table>
							<div class="modal-footer">
							<button type="submit" class="btn blue pull-left" onclick="return validation()">Generate Report</button>  
							
							</div>
							
							</form>								
						</div>
						
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
					 <c:if test = "${cmrierror eq 'CMRI List Report Data Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${cmrierror}</span>
						</div>
			        </c:if>
					
					 <c:if test="${ cmriDataShow eq 'cmriDataShow'}">
					<div class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> CMRI List Report
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 150px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDOCODE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>RDNGMONTH</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${cmriList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
					
					
	<!-- ------------------- CNP REPORT -------------------------- -->

			<c:if test="${cnperror eq 'CNP Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${cnperror}</span>
				</div>
			</c:if>

			<c:if test="${ cnpListDataShow eq 'cnpListDataShow'}">
					<div class="portlet box blue">
	
					<div class="portlet-title">
						<div class="caption"><i class="fa fa-edit"></i> CNP Report
				            <a href="#" id="excelExport" class="btn green"  style="margin-left: 50px;" onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
						</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>MNP</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>READING REMARK</th>
										<th>NAME</th>
										<th>ADDRESS</th>
										<th>MRNAME</th>
										
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${cnpList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
								
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
						
					</div>
					</c:if>
					
					
		<!-- -----------------------DateWise D4 Data Report---------------------------- -->
					
					<c:if test="${dateWiseError eq 'DateWise Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${dateWiseError}</span>
				</div>
			</c:if>

			<c:if test="${ dateWiseDataShow eq 'dateWiseDataShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title" >
							<div class="caption"><i class="fa fa-edit"></i> DateWise Report
				               <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
				             <!--   <button class="btn" id="export">Save</button> -->
							</div>
							
						</div>
						<div class="portlet-body" id="excelUploadPDF">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>BILLMONTH</th>
										<th>ACCNO</th>
										<th>NAME</th>
										<th>METERNO</th>
										<th>CD</th>
										<th>DAY_PROFILE_DATE </th>
										<th>kWh</th>
										<th>kVAh</th>
										<th>Ir</th>
										<th>Iy</th>
										<th>Ib</th>
										<th>ADDRESS</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${dateWiseList}">
								<tr>
									<%-- <td>${element[0]}</td>
									<td>${element[1]}</td>
									 <td>${element[2]}</td> 
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td> --%>
									
									
									<td>${element.CIRCLE}</td>
									<td>${element.SDONAME}</td>
									 <td>${element.BILLMONTH}</td> 
									<td>${element.ACCNO}</td>
									<td>${element.NAME}</td>
									<td>${element.METERNO}</td>
									<td>${element.CD}</td>
									<td>${element.DAY_PROFILE_DATE}</td>
									<td>${element.WH}</td>
									<td>${element.VAH}</td>
									<td>${element.IR}</td>
									<td>${element.IY}</td>
									<td>${element.IB}</td>
									<td>${element.ADDRESS}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
					
					
  <!-- 	-----------------------Deffective Report--------------------------- -->
					
					<c:if test="${defectiveReportError eq 'Defective Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${defectiveReportError}</span>
				</div>
			</c:if>

			<c:if test="${ defectiveReportShow eq 'defectiveReportShow'}">
					<div class="portlet box blue">
	
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Defective Report
					          <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									    <th>IMAGE</th>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>MNP</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>READINGREMARK </th>
										<th>NAME</th>
										<th>ADDRESS1</th>
										<th>MRNAME</th>
										
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${defectiveList}">
								<tr>
								<td><input type="button" class="btn btn-info" value="View Image" id="def${count}" 
					                 onclick="showImageForDeffective(this.id,'${element[4]}');" ></td>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
					
	<!-- -----------------------EnergyWise Report--------------------------- -->
					
					<c:if test="${energyReportError eq 'EnergyWise Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${energyReportError}</span>
				</div>
			</c:if>

			<c:if test="${ energyReportShow eq 'energyReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> EnergyWise Report
						       <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
									<!-- <th>CDFID</th> -->
										<th>ACCNO</th>
										<th>NAME</th>
										
										<th>METRNO</th>
										<!-- <th>MNP</th> -->
										<th>BILLING DATE</th>
										<th>ENERGY(kWh)</th>
										<th>kVAh</th>
										<th>kVA</th>
										<!-- <th>SUM_KWH</th>
										<th>SUPPLYVOLTAGE</th>
										<th>INDUSTRYTYPE</th>
										<th>D3_01_ENERGY </th>
										<th>D3_02_ENERGY</th>
										<th>D3_03_ENERGY</th>
										<th>CURRDNGKWH</th>
										<th>PRKWH</th>
										<th>MFYEAR</th> -->
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${energyListList}">
								<tr>
									
									<td>${element.CIRCLE}</td>
									<td>${element.SDONAME}</td>
									<td>${element.ACCNO}</td>
									<td>${element.NAME}</td>
									<td>${element.METERNO}</td>
									<td>${element.DAY_PROFILE_DATE}</td>
									<td>${element.KWH}</td>
									<td>${element.KVAH}</td>
									<td>${element.KVA}</td>
									
									<%-- <td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									<td>${element[14]}</td>
									<td>${element[15]}</td>
									<td>${element[16]}</td>
									<td>${element[17]}</td>
									<td>${element[18]}</td>
									<td>${element[19]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
					
					<!-- -----------------------EventWise Report--------------------------- -->
					
					<c:if test="${eventReportError eq 'EventWise Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${eventReportError}</span>
				</div>
			</c:if>

			<c:if test="${ eventReportShow eq 'eventReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> EventWise Report
							   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									    <th>ACCNO</th>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>METRNO</th>
										<th>TRANSACTION_TIME</th>
										<th>D9_ID</th>
										<th>CDFID</th>
										<th>TRANSACTION_CODE</th>
										<th>TRANSACTION</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${eventList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
						<!-- -----------------------Faulty Report--------------------------- -->
					
					<c:if test="${faultyReportError eq 'Faulty Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${faultyReportError}</span>
				</div>
			</c:if>

			<c:if test="${ faultyReportShow eq 'faultyReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Faulty Report
						  		 <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									   
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>ACCNO</th>
										<th>METERNO</th>
										<th>TAMPERTYPE</th>
										<th>OCCURRED_DATE</th>
										<th>RESTORED_DATE</th>
										<th>NAME</th>
										<th>MNP</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${faultyList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
							<!-- -----------------------IndexUsage Report--------------------------- -->
					
					<c:if test="${indexUsageReportError eq 'Index Usage Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${indexUsageReportError}</span>
				</div>
			</c:if>

			<c:if test="${ indexUsageReportShow eq 'indexUsageReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Index Usage Report
						 	   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
                            </div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									   
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<!-- <th>CDFID</th> -->
										<th>ACCNO</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>METRNO</th>
										<th>XCURRDNGKVA</th>
										<th>CD</th>
										<th>DAY_PROFILE_DATE</th>
										<th>INTERVAL_PERIOD</th>
										<th>MAX_KVA</th>
										<th>MF</th>
										<th>MIN_kVA</th>
										<th>SUM_kWh</th>
										<th>SUM_kVH</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${indexList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									<td>${element[14]}</td>
									<%-- <td>${element[15]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
	<!-- -----------------------LoadUtilization Report--------------------------- -->
					
					<c:if test="${loadUtilizeReportError eq 'Load Utilization Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${loadUtilizeReportError}</span>
				</div>
			</c:if>

			<c:if test="${ loadUtilizeReportShow eq 'loadUtilizeReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Load Utilization Report
							    <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<!-- <th>CDFID</th> -->
										<th>ACCNO</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>METRNO</th>
										<th>INTERVAL_PERIOD</th>
										<th>DAY_PROFILE_DATE</th>
										<th>MF</th>
										<th>CD</th>
										<th>IP_GS_20</th>
										<th>IP_GS_20_40</th>
										<th>IP_GS_40_60</th>
										<th>IP_GS_60</th>
										<th>IP_OUT_GS_20</th>
										<th>IP_OUT_GS_20_40</th>
										<th>IP_OUT_GS_40_60</th>
										<th>IP_OUT_GS_60</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${loadList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									<td>${element[14]}</td>
									<td>${element[15]}</td>
									<td>${element[16]}</td>
									<td>${element[17]}</td>
									<%-- <td>${element[18]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
					<!-- -----------------------Manual Report--------------------------- -->
					
					<c:if test="${manualReportError eq 'Manual Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${manualReportError}</span>
				</div>
			</c:if>

			<c:if test="${ manualReportShow eq 'manualReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>  Manual Reading Report
					             <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									    <th>IMAGE</th>
										<th>CIRCLE</th>
										<th>SDOCODE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>NAME</th>
										<th>ADDRESS</th>
										<th>READINGREMARK</th>
										<th>MRNAME</th>
										<th>kWH</th>
										<th>kVAh</th>
										<th>kVA</th>
										
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
						 	 <c:set var="count" scope="page" value="${count+1}"></c:set>

								<c:forEach var="element" items="${manualList}">
								<tr>
                                    <td><input type="button" class="btn btn-info" value="View Image" id="btn${count}" onclick="showImageMethod(this.id,'${element[4]}','${element[6]}','${element[12]}');" ></td>
                                    <td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[12]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
	<!-- -----------------------Other Make Report--------------------------- -->
					
					<c:if test="${otherMakeReportError eq 'OtherMake Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${otherMakeReportError}</span>
				</div>
			</c:if>

			<c:if test="${ otherMakeReportShow eq 'otherMakeReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>  OtherMake Report
								  <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>RDNGMONTH</th>
										<th>SDOCODE</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>NAME</th>
										<th>MTRMAKE</th>
										<th>MNP</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${otherList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					</c:if>
					
						<!-- -----------------------Power Factor Report--------------------------- -->
					
					<c:if test="${pfReportError eq 'OtherMake Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${pfReportError}</span>
				</div>
			</c:if>

			<c:if test="${ pfReportShow eq 'pfReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>  PowerFactor Report
							   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									   
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>NAME</th>
										<th>METERNO</th>
										<th>READTIME</th>
										<th>PFr</th>
										<th>PFy</th>
										<th>PFb</th>
										<th>PF3PHASE</th>
										<th>FREQUENCY</th>
									
										
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${pfList}">
								<tr>
									<td>${element.CIRCLE}</td>
									<td>${element.SDONAME}</td>
									<td>${element.ACCNO}</td>
									<td>${element.NAME}</td>
									<td>${element.METERNO}</td>
									<td>${element.READ_TIME}</td>
									<td>${element.PFR}</td>
									<td>${element.PFY}</td>
									<td>${element.PFB}</td>
									<td>${element.PF_3PHASE}</td>
									<td>${element.FREQUENCY}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
					
	<!-- -----------------------Meter Change Report--------------------------- -->
					
					<c:if test="${meterChangeReportError eq 'MeterChange Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${meterChangeReportError}</span>
				</div>
			</c:if>

			<c:if test="${ meterChangeReportShow eq 'meterChangeReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Meter Change Report
						  		<a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>ACCNO</th>
										<th>METERNO</th>
										<th>XMLDATE</th>
										<th>READINGREMARK</th>
										<th>NEWMETER</th>
										<th>REMARK</th>
										<th>MRNAME</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${meterList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
					
				<!-- -----------------------Static Class Report--------------------------- -->
					
					<c:if test="${staticReportError eq 'Static Class Report Data Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${staticReportError}</span>
				</div>
			</c:if>

			<c:if test="${ staticClassReportShow eq 'staticClassReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Static Class Report
						  		  <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<!-- <th>CDF_ID</th> -->
										<th>ACCNO</th>
										<th>METERNO</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>METER_CLASS</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${staticList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<%-- <td>${element[7]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
						
						
	<!-- -----------------------Tamper Report--------------------------- -->
					
		<c:if test="${tamperReportError eq 'Tamper Report Data Not Found...'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${tamperReportError}</span>
			</div>
		</c:if>

			<c:if test="${ tamperReportShow eq 'tamperReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Tamper Report
							   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
			
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									<th>Month-Year</th>
									<th>Subdivision</th>
									<th>KNo</th>
									<th>Account Number</th>
									<th>Consumer Name</th>
									<th>Meter Serial number</th>
									<th>Tamper Event Name</th>
									<th>Tamper Event  date</th>
									<th>Tamper Event Duration</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${tamperList}">
								<tr>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[0]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<%-- <td>${element[8]}</td>
									<td>${element[9]}</td> --%>
									<%-- <td>${element[10]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
		<!-- Tamper Event Summary Report -->
		
		<c:if test="${tamperHistoryReportError eq 'Tamper Report Data Not Found...'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${tamperReportError}</span>
			</div>
		</c:if>

			<c:if test="${ tamperHistoryReportShow eq 'tamperReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Tamper History  Report
							   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
			
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									<th>Month-Year</th>
									<th>Subdivision</th>
									<th>KNo</th>
									<th>Account Number</th>
									<th>Consumer Name</th>
									<th>Meter Serial number</th>
									<th>Tamper Event Name</th>
									<th>Tamper count </th>
									<th>Tamper Event Duration</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${tamperHistoryList}">
								<tr>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[0]}</td>
									<td>${element[2]}</td>
									<td>${element[10]}</td>
									<td>${element[4]}</td>
									<%-- <td>${element[8]}</td>
									<td>${element[9]}</td> --%>
									<%-- <td>${element[10]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
					
					
		<!-- -----------------------Transaction Report--------------------------- -->
					
		<c:if test="${transactionReportError eq 'Tansaction Report Data Not Found...'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${transactionReportError}</span>
			</div>
		</c:if>

			<c:if test="${ transactionReportShow eq 'transactionReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Transaction Report
								  <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									   
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>NAME</th>
										<th>METERNO</th>
										<th>READTIME</th>
										<th>Vr</th>
										<th>Vy</th>
										<th>Vb</th>
										<th>Ir</th>
										<th>Iy</th>
										<th>Ib</th>
										<th>kVAh</th>
										<th>kVA</th>
										
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${transList}">
								<tr>
									<td>${element.CIRCLE}</td>
									<td>${element.SDONAME}</td>
									<td>${element.ACCNO}</td>
									<td>${element.NAME}</td>
									<td>${element.METERNO}</td>
									<td>${element.READ_TIME}</td>
									<td>${element.VR}</td>
									<td>${element.VY}</td>
									<td>${element.VB}</td>
									<td>${element.IR}</td>
									<td>${element.IY}</td>
									<td>${element.IB}</td>
									<td>${element.KVAH}</td>
									<td>${element.KVA}</td>
									<%-- <td>${element[15]}</td>
									<td>${element[16]}</td>
									<td>${element[17]}</td>
									<td>${element[18]}</td>
									<td>${element[19]}</td>
									<td>${element[20]}</td>
									
									<td>${element[21]}</td>
									<td>${element[22]}</td>
									<td>${element[23]}</td>
									<td>${element[24]}</td>
									<td>${element[25]}</td>
									<td>${element[26]}</td>
									<td>${element[27]}</td>
									<td>${element[28]}</td>
									<td>${element[29]}</td> --%>
									<%-- <td>${element[30]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
					
	<!-- -----------------------Wiring Verification Report--------------------------- -->
					
		<c:if test="${wiringReportError eq 'Wiring Verification Report Data Not Found...'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${wiringReportError}</span>
			</div>
		</c:if>

			<c:if test="${ wiringReportShow eq 'wiringReportShow'}">
					<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Wiring Verification Report
						 		   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									   <!--  <th>CDF_ID</th> -->
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>NAME</th>
										<th>MNP</th>
										<th>METERNO</th>
										<th>MTRMAKE</th>
										<th>RTCYN</th>
										<th>PHASE_SEQUENCE</th>
										<th>INTERVAL_PERIOD</th>
										<th>METER_TYPE</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${wireList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<%-- <td>${element[11]}</td> --%>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</c:if>
					
					<c:if test="${ usageIndex eq 'usageIndex'}">
					<div class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Usage Index Report today
						 <!--  <a href="#" id="excelExport" class="btn green" style="margin-left: 150px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a> -->
							</div>
							
						</div>
						
						
						 <div class="portlet-body" id="excelUpload">
							 <table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>DIVISION</th>
										<th>SUBDIVISION</th>
										<th>NAME</th>
										<th>ADDRESS</th>
										<th>ACCNO</th>
										<th>METERNO</th>
										<th>RDNGMONTH</th>
										<th>TOTAL HRS</th>
										<th>POWER ON(HRS)</th>
										<th>POWER OFF(HRS)</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${usageIndexedData1}">
								<tr>
									<td>${element.circle}</td>
									<td>${element.division}</td>
									<td>${element.subdivision}</td>
									<td>${element.name}</td>
									<td>${element.address}</td>
									<td>${element.accno}</td>
									<td>${element.meterno}</td>
									<td>${element.rdngMonth}</td>
									<td>${element.totalhrs}</td>
									<td>${element.powerOn}</td>
									<td>${element.powerOff}</td>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table> 
						</div> 
						
					</div>
					</c:if>
					
					
				<!-- --------------- Manual Report Image Popup------------------- -->
				
				     <div id="stack3" class="modal fade" tabindex="1" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" ></button>
								<h4 class="modal-title"> Manual Reading Report Meter Image</h4>
										</div>
										<left><font color="green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AccNo:&nbsp;&nbsp;<span id="accc" style="color:blue;font-size: 14;" ></span></left>
										<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ConsumerName:&nbsp;&nbsp;<span id="cn" style="color:blue;font-size: 14;" > </span>
										<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mrname:&nbsp;&nbsp;<span id="mrname" style="color:blue;font-size: 14;" > </span>
					         <!-- 
					          <div id='loadingmessage' >
  									<img src="/resources/assets/img/loading.gif" />
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
  									</div> -->
					         <div class="modal-body">
					        
					            <div class="row">
					             
					            <!--   <div class="col-md-12">
					            	
					             
								</div> -->

											<table id="sealIssueTabId">

                                               <tr>
													<td></td>
													<td> </td>
											   </tr>
							
											</table>
											</div>
											</div>
										
					              </div>
					           </div>
					       </div>
					   
					
					
						<!-- ---------------  Deffective Report Image Popup------------------- -->
				
				     <div id="stack4" class="modal fade" tabindex="1" data-backdrop="static" data-keyboard="false">
					   <div class="modal-dialog">
					      <div class="modal-content">
					      <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" ></button>
								<h4 class="modal-title"> Defective Report Meter Image</h4>
										</div>
					         <div class="modal-body">
					        
					            <div class="row">
					              
					              <div class="col-md-12">
									   <table id="deffectiveTabId">
									           <tr>
													<td>AccNo :</td>
													<td> </td>
											   </tr>

										</table>
										
					              </div>
					           </div>
					       </div>
					   </div>
					  </div>
					</div>
					
					
			 
					
					
				</div>
			</div>
</div>

<script>
function validation()
{
	//alert("in validation()")
	var circle=$("#circleId").val();
	var rdngMonth=$("#reportFromDate").val();
	var mnp=$("#mnp").val();
	var reportName=$("#reportName").val();
	var sdoname=$("#sdoId").val();
  	alert(circle+rdngMonth+sdoname);
	
	if(circle=="0")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}

		/* if(sdoname=="0" || sdoname==null)
		{
		bootbox.alert("Please Select SdoName");
		return false;
		} */
		/* 
	    if (mnp == "0" || mnp == null) 
		{
			bootbox.alert("Please Select MNP");
			return false;
		} */
		if (reportName == "0" || reportName == null) 
		{
			bootbox.alert("Please Select Report");
			return false;
		}
		
		if(reportName=="CMRILIST")
			{
		      $("#cmriId").attr("action","./analysedReportData");
			}
		
		if(reportName=="CNP")
		  {
		       $("#cmriId").attr("action","./analysedCNPReport");
		  }
		
		if(reportName=="DATEWISEREPORT")
		{
	 	    $("#cmriId").attr("action","./analysedDateWiseReport");
		}
		
		if(reportName=="DEFECTIVE")
		{
	  	 $("#cmriId").attr("action","./analysedDefectiveReport");
		}

		if(reportName=="ENERGYWISEREPORT")
		{
	  	 $("#cmriId").attr("action","./analysedEnergyWiseReport");
		}
		
		if(reportName=="EVENTWISEREPORT")
		{
	   $("#cmriId").attr("action","./analysedEventWiseReport");
		}
		
		if(reportName=="FAULTY")
		{
	  	 $("#cmriId").attr("action","./analysedFaultyReport");
		}
		
		if(reportName=="USAGEINDEXREPORT")
		{
	  	 $("#cmriId").attr("action","./analysedIndexUsageReport");
		}
		
		if(reportName=="LOADUTILIZATIONREPORT")
		{
	  	 $("#cmriId").attr("action","./analysedLoadUtilizationReport");
		}
		
		if(reportName=="MANUAL")
		{
	   $("#cmriId").attr("action","./analysedManualReport");
		}
		
		if(reportName=="OTHERMAKE")
		{
	 	  $("#cmriId").attr("action","./analysedOtherMakeReport");
		}
		
		if(reportName=="POWERFACTORREPORT")
		{
	  	 $("#cmriId").attr("action","./analysedPowerFactorReport");
		}
		
		if(reportName=="METERCHANGE")
		{
	  	 $("#cmriId").attr("action","./analysedMeterChangeReport");
		}
		
		if(reportName=="STATICMETERCLASSREPORT")
		{
	     $("#cmriId").attr("action","./analysedStaticClassReport");
		}
		
		if(reportName=="TAMPEREVENTREPORT")
		{
	     $("#cmriId").attr("action","./analysedTamperReport");
		}
		
		if(reportName=="TRANSACTIONREPORT")
		{
	     $("#cmriId").attr("action","./analysedTransactionReport");
		}
		
		if(reportName=="WIRINGVERIFICATIONREPORT")
		{
	     $("#cmriId").attr("action","./analysedWiringReport");
		}
		if(reportName=="USAGEINDEXING")
		{
	     $("#cmriId").attr("action","./usageIndexingReport");
		}

		if(reportName=="ENERGYWISEREPORT")
		{
	  	 $("#cmriId").attr("action","./analysedEnergyWiseReportAMI");
		}
		if(reportName=="TAMPEREVENTSUMMARY")
		{
	  	 $("#cmriId").attr("action","./analysedTamperHistoryReport");
		}

		/* $("#reportName").val('${report_name}'); */
	}

function getSdoName(param)
{

var circle=param;
	 $.ajax({
		 type : "POST",
		 url:"./analysedReportgetSdoName",
		 data:{circle:circle},
		 success:function(response)
		 {
			 if(response != null)
	    		{
	    			var html='<option value="%">ALL</option>';
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 $("#sdoId").empty();
	    			 $("#sdoId").append(html);
	    			 $("#sdoId").val('${sdoNAME}');
	    		}
			/*  getMnpOnSdoname(); */
		 }
		 
	 });
	 
}


function getMnpOnSdoname()
{
	var sdoId=$("#sdoId").val();
	
	var circle=$("#circleId").val();

	//alert(sdoId+"=="+circle);
	
	 $.ajax({
		 type : "POST",
		 url:"./analysedReportgetMnp",
		 data:{sdoId:sdoId, circle:circle},
		 success:function(response)
		 {
			 if(response != null)
	    		{
	    			var html='<option value="%">ALL</option>';
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 $("#mnp").empty();
	    			 $("#mnp").append(html);
	    			 $("#mnp").val('${mnpVal}');
	    		}
			 $("#reportName").val('${report_name}');
			
		 }
		
	 });
	// $("#reportFromDate").val('${rdngmonth_1}');
	}


/* --------------  Show Image Method----------------- */
 
 
 
 function showImageMethod(param1,param,cname,mrname)
 {
	 $('#accc').html("");		    
		$('#cn').html("");	
		$('#mrname').html("");
	 var rdngMonth="${rdngmonth_1}";
	
     var accNo=param;
	 var $modal = $('#loading1');
	 $('body').modalmanager('loading');
	  $modal.modal('loading').css({
		  'position': 'fixed',
	  'left': '50%',
	  'top': '50%',
	  'display' : 'block',
	  'overflow': 'hidden'
	 
	  });
    var html="";

    html= html+"<div><tr>";
	html= html+"<td>${accNo}</td>";  
	/* html= html+"<td><img src='resources/assets/img/rose.jpeg' alt=''></td>"; */
	
	html= html+"<td><img id=imgId src=./imageDisplay/getImageForManualReading/"+rdngMonth+"/"+accNo+"></img></td>";
    html= html+"</tr></div>";
    $('#accc').html(accNo);		    
	$('#cn').html(cname);	
	$('#mrname').html(mrname);	
	
    $('#stack3 tbody').html(html);
   
    
	$('body').modalmanager();
	$modal.modal();
	$('#'+param1).attr("data-toggle", "modal");
	$('#'+param1).attr("data-target","#stack3");
	
	
	
 } 



   function showImageForDeffective(param,accNo)
   {

	var rdngMonth="${rdngmonth_1}";

    var htmlDef="";
    htmlDef=htmlDef+"<div><tr>";
   /*  htmlDef=htmlDef+"<td>Deffective Report</td>"; */
   /*  html= html+"<td><img src='resources/assets/img/rose.jpeg' alt=''></td>";  */
   
     htmlDef=htmlDef+"<td><img style=align:center src=./imageDisplay/getImageForDeffective/"+rdngMonth+"/"+accNo+ "></img></td>";  
     htmlDef=htmlDef+"</tr></div>";

    $("#stack4 tbody").html(htmlDef);

    $('body').modalmanager();
	//$modal.modal(); 
	$('#'+param).attr("data-toggle", "modal");
	$('#'+param).attr("data-target","#stack4");
    
   }
   
</script>


<script>


/* document.getElementById('export').addEventListener('click',
		  exportPDF);
 */
		var specialElementHandlers = {
		  // element with id of "bypass" - jQuery style selector
		  '.no-export': function(element, renderer) {
		    // true = "handled elsewhere, bypass text extraction"
		    return true;
		  }
		};

	/* 	function exportPDF() {

		  var doc = new jsPDF('p', 'pt', 'a4');
		  //A4 - 595x842 pts
		  //https://www.gnu.org/software/gv/manual/html_node/Paper-Keywords-and-paper-size-in-points.html


		  //Html source 
		  var source = document.getElementById('sample_editable_1').innerHTML;

		  var margins = {
		    top: 10,
		    bottom: 10,
		    left: 10,
		    width: 1024
		  };

		  doc.fromHTML(
		    source, // HTML string or DOM elem ref.
		    margins.left,
		    margins.top, {
		      'width': margins.width,
		      'elementHandlers': specialElementHandlers
		    },

		    function(dispose) {
		      // dispose: object with X, Y of the last line add to the PDF 
		      //          this allow the insertion of new lines after html
		      doc.save('Test.pdf');
		    }, margins);
		} */


</script>



<style>
imgId
{
margin-left: 50%;
}
</style>
