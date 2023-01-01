<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>

<style>
.select2-choice {

	margin-right: -1px;
}
</style>

<script  type="text/javascript">
var circle = '${officeName}';
var officeTyperesult = '${officeType}';
var zone = '${newRegionName}';

	$(".page-content")
			.ready(
					function() {

						App.init();
						TableEditable.init();
						FormComponents.init();

						$('#MDASSideBarContents,#mdmDashId,#mtws').addClass(
								'start active ,selected');
						$(
								"#MDMSideBarContents,#ADMINSideBarContents,#DATAEXCHsideBarContents,#metermang,#surveydetails,#360MeterDataViewID,#dataValidId,#DPId,#alarmID,#reportsId,#eaId,#todEconomcsId")
								.removeClass('start active ,selected');
						$("#level5").hide();
						$("#level4").hide();
						$("#level3").hide();
						$("#level2").hide();
						loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_2');
						loadSearchAndFilter('sample_3');
						loadSearchAndFilter('sample_4');
						loadSearchAndFilter('sample_5');

						if (officeTyperesult == 'region') {
							locData2();
						} else if (officeTyperesult == 'circle') {
							locData2();
						} else {
							locData();
						}

						var regioPdf;
						var circlepdf;
						var divisionpdf;
						var subdivisionpdf;
						var townpdf;

					});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Type Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<br />
					<br />
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportPDF1('sample_1','MeterTypeWiseSummary')">Export
										to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'MeterTypeWiseSummary')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Region</th>
								<th>Total Meters</th>
								<!-- <th>HT Meters</th> -->
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>


							</tr>
						</thead>
						<tbody id="locId">
						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" id="level2">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Circle Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<br />
					<br />
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportPDF2('sample_2','Circle Wise Summary')">Export
										to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('sample_2', 'Circle Wise Summary')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
								<th>Region</th>
								<th>Circle</th>
								<th>Total Meters</th>
								<!-- <th>HT Meters</th> -->
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>


							</tr>
						</thead>
						<tbody id="cirId">



						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" id="level3">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Division Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<br />
					<br />
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportPDF3('sample_3','Division Wise Summary')">Export
										to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('sample_3', 'Division Wise Summary')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_3">
						<thead>
							<tr>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>Total Meters</th>
								<!--<th>HT Meters</th> -->
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>
							</tr>
						</thead>
						<tbody id="divId">

						</tbody>

					</table>

				</div>
			</div>
		</div>
	</div>
	<div class="row" id="level4">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>SubDivision Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<br />
					<br />
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportPDF4('sample_4','SubDivision Wise Summary')">Export
										to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToXlxs('sample_4', 'SubDivision Wise Summary')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_4">
						<thead>
							<tr>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>Total Meters</th>
								<!--<th>HT Meters</th> -->
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>


							</tr>
						</thead>
						<tbody id="subdivId">

						</tbody>

					</table>

				</div>
			</div>
		</div>
		</div>

		<!--   town wise data  -->

		<div class="row" id="level5">
			<div class="col-md-12">

				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Town Wise Summary
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="javascript:;" class="remove"></a>
						</div>
					</div>

					<div class="portlet-body">

						<br />
						<br />
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<!-- <li><a href="#" id="print">Print</a></li> -->
									<li><a href="#" id=""
										onclick="exportPDF5('sample_5','Town Wise Summary')">Export
											to PDF</a></li>
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_5', 'Town Wise Summary')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>

						<table class="table table-striped table-hover table-bordered"
							id="sample_5">
							<thead>
								<tr>
									<th>Region</th>
									<th>Circle</th>
									<th>Division</th>
									<th>SubDivision</th>
									<!-- <th>LT Meters</th>
								<th>HT Meters</th> -->
									<th>Town</th>
									<th>Total Meters</th>
									<th>DT Meters</th>
									<th>Feeder Meters</th>
									<th>Boundary Meters</th>
								</tr>
							</thead>
							<tbody id="townId">
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
</div>

	<div class="modal fade" id="stack1" role="basic"
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
								<li><a href="#" id="excelExport6"
									onclick="tableToExcel6('sample_Meter','LocationWiseSummaryDetails');">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table id="sample_Meter"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Region</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>Town</th>
								<th>Location Type</th>
								<th>Location Id</th>
								<th>Meter No.</th>
								<th>Last Comm Time</th>
							</tr>
						</thead>
						<tbody id="getMeterDetails">
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
	<script>
		var circ = null;
		function locData() {
			$('#imageee').show();
			$
					.ajax({
						url : './zoneList',
						type : 'GET',
						success : function(res) {
							$('#imageee').hide();
							var html = '';
							$.each(res,function(i, v) {
									circ = v[0];
									
									html += '<tr>'
											+ '<td><a  href="#" onclick="circleData(this.id)" id="'+ v[0] + '">' + v[0]+ '</a></td>' 
											+ '<td><a  href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" onclick="getregionData(this.id); return false;" id="'+ v[0] + '">'+ v[1] + '</a></td>'
											/* +'<td>'+v[2]+'</td>' */
											+ '<td>' + v[4] + '</td>' 
											+ '<td>' + v[5] + '</td>'
											+ '<td>' + v[6] + '</td>' 
											+ '</tr>';

											});
							//regioPdf =v[0];
							$('#sample_1').dataTable().fnClearTable();
							$('#locId').html(html);
							loadSearchAndFilter('sample_1');

						}

					});
		}

		function locData2() {
			var ciId;
			$('#imageee').show();
			$
					.ajax({
						url : './zoneList2',
						type : 'GET',
						success : function(res) {
							$('#imageee').hide();
							var html = '';
							$.each(res,function(i, v) {
									circ = v[0];
									html += '<tr>'
										+ '<td><a  href="#" onclick="circleData(this.id)" id="'+ v[0] + '">' + v[0]+ '</a></td>' 
										+ '<td><a  href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getregionData(this.id)" id="'+ v[0] + '">'+ v[1] + '</a></td>'
										/* +'<td>'+v[2]+'</td>' */
										+ '<td>' + v[4] + '</td>' 
										+ '<td>' + v[5] + '</td>'
										+ '<td>' + v[6] + '</td>' 
										+ '</tr>';

									});
							$('#sample_1').dataTable().fnClearTable();
							$('#locId').html(html);
							loadSearchAndFilter('sample_1');
							//alert(officeTyperesult);
							if (officeTyperesult == 'circle') {
								circleData(circ);
								$('#sample_2').dataTable().fnClearTable();
								loadSearchAndFilter('sample_2');
							}

						}

					});
		}

		function locData3() {
			//alert("inside locdata3 method");
			$('#imageee').show();
			$
					.ajax({
						url : './circleList2',
						type : 'GET',
						data : {
							circle : circle
						},
						success : function(res) {
							$('#imageee').hide();
							var html = '';
							$.each(res,function(i, v) {
												circ = v[0];
												html += '<tr>'
														+ '<td><a href="#" onclick="circleData(this.id)" id="'+ v[0] + '">' + v[0]+ '</a></td>'
														+ '<td><a  href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getregionData(this.id)" id="'+ v[0] + '">'+ v[1] + '</a></td>'
														/* +'<td>'+v[1]+'</td>'
														+'<td>'+v[2]+'</td>' */
														+ '<td>' + v[4]+ '</td>' 
														+ '<td>' + v[5] + '</td>'
														+ '<td>' + v[6]+ '</td>' 
														+ '</tr>';

											});
							$('#locId').html(html);
						}

					});
		}

		var selectedcirclepdf;
		function circleData(cir) {
			circlePdf = cir;
			$
					.ajax({
						url : './circleList',
						type : 'GET',
						data : {
							circle : cir
						},
						success : function(res) {
							var html = '';
							$.each(res,function(i, v) {

												html += '<tr><td>'+ v[0]+ '</td><td><a  href="#"onclick="divisionData(this.id)" id="'+ v[0] + '@' + v[1]+ '">' + v[1]+ '</a></td>'
														/* +'<td>'+v[2]+'</td><td>'+v[3]+'</td>' */
														+ '<td><a href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getcircleData(this.id)" id="'+ v[0] + '@' + v[1]+ '">' + v[2]+ '</td>' 
														+ '<td>' + v[5]+ '</td>'
														+ '<td>' + v[6]+ '</td>'
														+ '<td>' + v[7]+ '</td></tr>';

											});
							$('#sample_2').dataTable().fnClearTable();
							$('#cirId').html(html);

							loadSearchAndFilter('sample_2');

							$("#level5").hide();
							$("#level4").hide();
							$("#level3").hide();
							$("#level2").show();
						}

					});
		}
		function divisionData(div) {
			//alert(div);

			divisionpdf = div;
			$.ajax({url : './divisionList',
						type : 'GET',
						data : {
							div : div
						},
						success : function(res) {
							var html = '';
							$.each(res,function(i, v) {
								html += '<tr><td>'+ v[0] + '</td>'+
										'<td>'+ v[1] + '</td>'+
										'<td><a href="#" onclick="subdivisionData(this.id)" id="'+ v[0] + '@' + v[1] + '@' + v[2] + '">' + v[2] + '</a></td>'
										/* +'<td>'+v[3]+'</td><td>'+v[4]+'</td>' */
										+ '<td><a href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getDivisionData(this.id)" id="'+ v[0] + '@' + v[1] + '@' + v[2] + '">' + v[3] + '</a></td>'
										+ '<td>' + v[6] + '</td>'
										+ '<td>' + v[7] + '</td>'
										+ '<td>' + v[8] + '</td></tr>';
								});
							$('#sample_3').dataTable().fnClearTable();
							$('#divId').html(html);
							loadSearchAndFilter('sample_3');

							$("#level5").hide();
							$("#level4").hide();
							$("#level3").show();
							$("#level2").show();
						}

					});
		}
		function subdivisionData(subdiv) {
			//	alert(subdiv);
			subdivisionpdf = subdiv;
			$
					.ajax({
						url : './subdivList',
						type : 'GET',
						data : {
							subdiv : subdiv

						},
						success : function(res) {
							//	alert(res);
							var html = '';
							$.each(res,function(i, v) {
									html += '<tr><td>' + v[0] + '</td>'
											+'<td>' + v[1] + '</td>'
											+'<td>' + v[2] + '</td>'
											+'<td><a href="#" onclick="townWiseData(this.id)" id="'+ v[0] + '@' + v[1] + '@' + v[2] + '@'+ v[3] + '">' + v[3] + '</a></td>'
											/* +'<td>'+v[3]+'</td><td>'+v[4]+'</td>' */
											+'<td><a href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getSubDivisionData(this.id)" id="'+ v[0] + '@' + v[1] + '@' + v[2] + '@'+ v[3] + '">' + v[4] + '</a></td>'
											+'<td>' + v[7] + '</td>'
											+'<td>' + v[8] + '</td>'
											+'<td>' + v[9] + '</td></tr>';

									});
							$('#sample_4').dataTable().fnClearTable();
							$('#subdivId').html(html);
							loadSearchAndFilter('sample_4');
							$("#level5").hide();
							$("#level4").show();
							$("#level3").show();
							$("#level2").show();
						}

					});
		}

		function townWiseData(town) {
			//alert(town)
			townpdf = town;
			$.ajax({
				url : './townList',
				type : 'GET',
				data : {
					town : town

				},
				success : function(res) {
					var html = '';
					$.each(res, function(i, v) {
						html += '<tr>'
							+'<td>' + v[0] + '</td>'
							+'<td>' + v[1] + '</td>'
							+'<td>' + v[2] + '</td>'
							+'<td>' + v[3] + '</td>'
								/* +'<td>'+v[4]+'</td><td>'+v[5]+'</td>' */
							+ '<td>' + v[4] + '</td>'
							+ '<td><a href="javascript:;" data-toggle="modal" data-target="#stack1" data-dismiss="modal" class="green" onclick="getTownData(this.id);" id="'+ v[0] + '@' + v[1] + '@' + v[2] + '@'+ v[3] + '@'+ v[4] +'">' + v[5] + '</a></td>'
							+ '<td>' + v[6] + '</td>'
							+ '<td>' + v[7] + '</td>'
							+ '<td>' + v[8] + '</td></tr>';

					});
					$('#sample_5').dataTable().fnClearTable();
					$('#townId').html(html);
					loadSearchAndFilter('sample_5');
					$('#level5').show();
					$("#level4").show();
					$("#level3").show();
					$("#level2").show();
				}

			});
		}

		function exportPDF1() {
	  		var zone = '${newRegionName}';
	  		
	  		//alert(zone);
	  		if (officeTyperesult=='corporate')
		  		{

			window.location.href = ("./MtrTypeCirWiseSummPDF");
		  		}
	  		else
		  		{
	  			window.location.href = ("./MtrTypeCirWiseSummPDF?zone="+zone);
		  		} 
		}

		function exportPDF2() {
			var circle = circlePdf;
			//alert(circle);
			window.location.href = ("./CircleWiseSummPDF?circle=" + circle);
		}

		function exportPDF3() {
			var division = divisionpdf;
			//alert(division);
			window.location.href = ("./DivisionWiseSummPDF?division=" + division);
		}

		function exportPDF4() {
			var subdivision = subdivisionpdf;
			//alert(division);
			window.location.href = ("./SubdivisionWiseSummPDF?subdivision=" + subdivision);
		}

		function exportPDF5() {
			var town = townpdf;
			//	alert(town);
			window.location.href = ("./TownWiseSummPDF?town=" + town);
		}
	</script>
	<script>
	function getregionData(reg) {
		$('#tabTitle').html("Region Wise Summary Details");
		loadSearchAndFilter1('sample_Meter');
		$.ajax({
				url : './getregionWiseMeterList',
				type : 'GET',
				data : {
					region : reg
					},
				success : function(res) {
				var html = '';
				var counter = 1;
					$.each(res,function(i, v) {
						html +=  "<tr> " 
							+ "<td>" + counter + "</td> "
							+ "<td>" +  v[0] + "</td> " 
							+ "<td>" +  v[1] + "</td> " 
							+ "<td>" +  v[2] + "</td> " 
							+ "<td>" +  v[3] + "</td> " 
							+ "<td>" +  v[4] + "</td> "
							+ "<td>" +  v[5] + "</td> " 
							+ "<td>" +  v[6] + "</td> " 
							+ "<td>" +  v[7] + "</td> " 
							+ "<td>" + ((v[8] == null) ? "": moment(new Date(v[8]).toUTCString()).format('DD-MM-YYYY HH:mm:ss'))  + "</td> "
							+ "</tr>";
						counter++;
						
							});
					$('#sample_Meter').dataTable().fnClearTable();
					//$("#getMeterDetails").append(html);
					$('#getMeterDetails').html(html);
					loadSearchAndFilter1('sample_Meter');

				}

		});
	}

	function getcircleData(cir) {
		$('#tabTitle').html("Circle Wise Summary Details");
		loadSearchAndFilter1('sample_Meter');
		$.ajax({
				url : './getcircleWiseMeterList',
				type : 'GET',
				data : {
					circle : cir
					},
				success : function(res) {
				var html = '';
				var counter = 1;
					$.each(res,function(i, v) {
						html +=  "<tr> " 
							+ "<td>" + counter + "</td> "
							+ "<td>" +  v[0] + "</td> " 
							+ "<td>" +  v[1] + "</td> " 
							+ "<td>" +  v[2] + "</td> " 
							+ "<td>" +  v[3] + "</td> " 
							+ "<td>" +  v[4] + "</td> "
							+ "<td>" +  v[5] + "</td> " 
							+ "<td>" +  v[6] + "</td> " 
							+ "<td>" +  v[7] + "</td> " 
							+ "<td>" + ((v[8] == null) ? "": moment(new Date(v[8]).toUTCString()).format('DD-MM-YYYY HH:mm:ss'))  + "</td> "
							+ "</tr>";
						counter++;
							});
					$('#sample_Meter').dataTable().fnClearTable();
					$('#getMeterDetails').html(html);
					loadSearchAndFilter1('sample_Meter');

				}

		});
	}

	function getDivisionData(div) {
		$('#tabTitle').html("Division Wise Summary Details");
		loadSearchAndFilter1('sample_Meter');
		$.ajax({
				url : './getDivisionWiseMeterList',
				type : 'GET',
				data : {
					division : div
					},
				success : function(res) {
				var html = '';
				var counter = 1;
				$.each(res,function(i, v) {
					html +=  "<tr> " 
						+ "<td>" + counter + "</td> "
						+ "<td>" +  v[0] + "</td> " 
						+ "<td>" +  v[1] + "</td> " 
						+ "<td>" +  v[2] + "</td> " 
						+ "<td>" +  v[3] + "</td> " 
						+ "<td>" +  v[4] + "</td> "
						+ "<td>" +  v[5] + "</td> " 
						+ "<td>" +  v[6] + "</td> " 
						+ "<td>" +  v[7] + "</td> " 
						+ "<td>" + ((v[8] == null) ? "": moment(new Date(v[8]).toUTCString()).format('DD-MM-YYYY HH:mm:ss'))  + "</td> "
						+ "</tr>";
					counter++;
						});
				$('#sample_Meter').dataTable().fnClearTable();
				$('#getMeterDetails').html(html);
				loadSearchAndFilter1('sample_Meter');

				}

		});
	}

	function getSubDivisionData(subdiv) {
		$('#tabTitle').html("Sub Division Wise Summary Details");
		loadSearchAndFilter1('sample_Meter');
		$.ajax({
				url : './getSubDivisionWiseMeterList',
				type : 'GET',
				data : {
					subdivision : subdiv
					},
				success : function(res) {
					var html = '';
					var counter = 1;
					$.each(res,function(i, v) {
						html +=  "<tr> " 
							+ "<td>" + counter + "</td> "
							+ "<td>" +  v[0] + "</td> " 
							+ "<td>" +  v[1] + "</td> " 
							+ "<td>" +  v[2] + "</td> " 
							+ "<td>" +  v[3] + "</td> " 
							+ "<td>" +  v[4] + "</td> "
							+ "<td>" +  v[5] + "</td> " 
							+ "<td>" +  v[6] + "</td> " 
							+ "<td>" +  v[7] + "</td> " 
							+ "<td>" + ((v[8] == null) ? "": moment(new Date(v[8]).toUTCString()).format('DD-MM-YYYY HH:mm:ss'))  + "</td> "
							+ "</tr>";
						counter++;
							});
					$('#sample_Meter').dataTable().fnClearTable();
					$('#getMeterDetails').html(html);
					loadSearchAndFilter1('sample_Meter');

				}

		});
	}

	function getTownData(town) {
		$('#tabTitle').html("Town Wise Summary Details");
		loadSearchAndFilter1('sample_Meter');
		$.ajax({
				url : './getTownWiseMeterList',
				type : 'GET',
				data : {
					town : town
					},
				success : function(res) {
					var html = '';
					var counter = 1;
					$.each(res,function(i, v) {
						html +=  "<tr> " 
							+ "<td>" + counter + "</td> "
							+ "<td>" +  v[0] + "</td> " 
							+ "<td>" +  v[1] + "</td> " 
							+ "<td>" +  v[2] + "</td> " 
							+ "<td>" +  v[3] + "</td> " 
							+ "<td>" +  v[4] + "</td> "
							+ "<td>" +  v[5] + "</td> " 
							+ "<td>" +  v[6] + "</td> " 
							+ "<td>" +  v[7] + "</td> " 
							+ "<td>" + ((v[8] == null) ? "": moment(new Date(v[8]).toUTCString()).format('DD-MM-YYYY HH:mm:ss'))  + "</td> "
							+ "</tr>";
						counter++;
							});
					$('#sample_Meter').dataTable().fnClearTable();
					$('#getMeterDetails').html(html);
					loadSearchAndFilter1('sample_Meter');

				}

		});
	}
	
	</script>

