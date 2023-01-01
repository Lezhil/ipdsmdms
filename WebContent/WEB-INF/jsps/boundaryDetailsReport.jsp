<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	$(".page-content").ready(function() {
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		loadSearchAndFilter('sample');
		
		 
		 $('#MDMSideBarContents,#reportsId,#boundaryDetailsReport').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	   	    
	});

	$(document).ready(function() {
		generateReport();
		getAllLocation('${officeCode}', '${officeType}');
	});
	
</script>

<script>
	function getAllLocation(officeCode, officeType) {
		$
				.ajax({
					type : 'GET',
					url : "./getAllLocationData",
					data : {
						officeCOde : officeCode,
						officeType : officeType
					},
					async : false,
					cache : false,
					success : function(response) {
					//	alert(response);
						var html = "";
						if (response != null) {
							if (officeType == "discom") {
								html += "<option value=0>Select Circle</option>";
								for (var i = 0; i < response.length; i++) {
									html += "<option value='"+response[i]+"'>"
											+ response[i] + "</option>";
								}
								$("#circleId").empty();
								$("#circleId").append(html);
							}

							else if (officeType == "division") {
								var htmlCircle = "";
								var htmlDivision = "";
								//var htmlSubdivision=""; 
								for (var i = 0; i < response.length; i++) {
									htmlCircle += "<option value='"+response[i][0]+"'>"
											+ response[i][0] + "</option>";
									htmlDivision += "<option value='"+response[i][1]+"'>"
											+ response[i][1] + "</option>";
									//htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
								}
								$("#circleId").empty();
								$("#circleId").append(htmlCircle);
								$("#divisionId").empty();
								$("#divisionId").append(htmlDivision);
								//$("#sdonameId").empty();
								//$("#sdonameId").append(htmlSubdivision);

							} else if (officeType == "circle") {
								$('#circle').find('option').remove();
								html += "";
								for (var i = 0; i < response.length; i++) {
									 $('#circle').append($('<option>', {
										value : response[i],
										text : response[i]
									})); 
									//$("#circle").val(response[i]).trigger("change");
									//html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
								}
								//$("#circle").empty();
								//$("#circle").append(html);
								//	getDivByCircle($("#circleId").val());
								showTown($("#circle").val());
							}

							else if (officeType == "subdivision") {
								var htmlCircle = "";
								var htmlDivision = "";
								var htmlSubdivision = "";
								for (var i = 0; i < response.length; i++) {
									htmlCircle += "<option value='"+response[i][0]+"'>"
											+ response[i][0] + "</option>";
									htmlDivision += "<option value='"+response[i][1]+"'>"
											+ response[i][1] + "</option>";
									htmlSubdivision += "<option value='"+response[i][2]+"'>"
											+ response[i][2] + "</option>";
								}
								$("#circleId").empty();
								$("#circleId").append(htmlCircle);
								$("#divisionId").empty();
								$("#divisionId").append(htmlDivision);
								$("#sdonameId").empty();
								$("#sdonameId").append(htmlSubdivision);
								getSubStations($('#sdonameId').val());
							}

						}

					}
				});
	}

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
						html += "<select id='town' name='town' class='form-control' type='text'><option value=''>Select Town</option>";

						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							html += "<option  value='"+resp[1]+"'>" + resp[0]
									+ "</option>";
						}
						html += "</select><span></span>";
						$("#townId").html(html);
						$('#town').select2();
					}
				});
	}

</script>
<script>
function generateReport(){
 	//var circle= $("#circle").val()
	//var townid=$('#town').val();

	//alert(" circle="+circle+" town ="+town);
 $('#imageee').show();
	$
	.ajax({
		url : './generateReportData',
		type : 'POST',
/* 		data : {
			circle : circle,
			townid : townid
			
		}, */
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			var count = 1;
			$("#updateMaster").html('');
			if (response != null && response.length > 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					html += "<tr>" 
							+ "<td>"+ count++ + "</td>"
							+ "<td>"+ ((resp[0]==null)?"0":resp[0])+ " </td>"
							+ "<td>"+ ((resp[1]==null)?"0":resp[1])+ " </td>"
							+ "<td>"+ ((resp[3]==null)?"0":resp[3])+ " </td>"
							+ "<td>"+ ((resp[4]==null)?"0":resp[4])+ " </td>"
							+ "<td>"+ ((resp[5]==null)?"0":resp[5])+ " </td>"
							+ "</td>" +
							"</tr>";

				}
				$('#sample').dataTable().fnClearTable();
				$("#updateMaster").html(html);
				loadSearchAndFilter('sample');
			} else {
				bootbox.alert("No Data Found for this Criteria.");
				loadSearchAndFilter('sample');
			}
		}
	});
	
}

function exportPDF()
{
	window.location.href=("./RFMReportPDF");
}

</script>

<div class="page-content" >
	<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>RFM Report</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
							</div>
						</div>					
	  					
					<div class="portlet-body">
						<%-- <div class="row" style="margin-left: -1px;">
						<div class="col-md-6">
							<div id="circleTd" class="form-group">
								<select class="form-control select2me" id="circle" name="circle"
									onchange="showTown(this.value);">
									<option value="">Select Circle</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-6">
							<div id="townId" class="form-group">
								<select class="form-control select2me" id="town" name="town">
									<option value=""> Select Town</option>
									<option value="ALL">ALL</option> 
									<c:forEach items="${townList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>

						</div>

					</div> --%>
						
					<!-- <div class="modal-footer">
							<button type="button" class="btn blue pull-left" onclick="generateReport()">Generate RFM Report</button>  
					</div> -->
					
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
								   onclick="exportPDF('sample','RFM_Reports')">Export to PDF</a></li>
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample', 'RFM_Reports')">Export to Excel</a>
								</li>
							</ul>
						</div>
					</div>
                  <div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
                  <table class="table table-striped table-hover table-bordered" id="sample">
						<thead>
							<tr>
								<th>SL No</th>
								<th>Circle</th>
								<th>Town</th>
<!-- 								<th>Feeder Name</th>
								<th>Feeder ID</th> -->
								<th>Total RFM</th>
								<th>Meter Installed</th>
								<th>Meter Not-Installed</th>
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
				