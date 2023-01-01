<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#selectedDateId").val('${month}');

						$("#meterVal").val('${meterno}');

						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');

						$('#MDMSideBarContents,#MIS-Reports,#zeroConsumption')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

					});

	function showCircle(zone) {
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

				$("#circle").html(html);
				$('#circle').select2();
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
</script>
<div class="page-content">

	<div class="row">
		<div class="col-md-12">

			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Zero Consumption Report
					</div>
					<div class="tools">


						<a href="javascript:;" class="collapse"></a> <a
							href="#portlet-config" data-toggle="modal" class="config"></a> <a
							href="javascript:;" class="reload"></a> <a href="javascript:;"
							class="remove"></a>

					</div>
				</div>

				<div class="portlet-body">
					<form method="post" id="meterMasterForm" action="./zeroConsumptiongetDATA">
						<table>
							<tbody>
								<tr>
									<th id="zone" class="block">Zone&nbsp;:</th>
									<th id="zones"><select
										class="form-control select2me input-medium" id="zone"
										name="circle" onchange="showCircle(this.value);">
											<option value=""></option>
											<option value="JVVNL">JVVNL</option>
									</select></th>
									<th class="block">Circle&nbsp;:</th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showDivision(this.value);">
											<option id="getCircles" value=""></option>
											<option value=""></option>
									</select></th>
									<th class="block">Division&nbsp;:</th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">

									</select></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th class="block">Sub&nbsp;Division&nbsp;:</th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode"></select>
								<tr>
									<td>Select Month</td>
									<td>
										<div class="input-group input-medium date date-picker"
											data-date-format="yyyymm" data-date-viewmode="years"
											id="five">
											<input type="text" class="form-control"
												name="selectedDateName" id="selectedDateId"
												autocomplete="off"> <span class="input-group-btn">
												<button class="btn default" type="button" id="six">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td>
									<td>
										<div>
											<button name="search" type="submit" class="btn green"
												id="getSearch">view</button>
										</div>
									</td>

								</tr>
						</table>




						<!-- PopUp for DatePicker only for Load Survey -->


						<!-- End Popup -->

					</form>



				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
			<!-- BEGUN METERMASTER TABLE DATA -->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>Meter List
					</div>

				</div>

				<div class="portlet-body">

					<BR>
					<BR>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">

						<thead>
							<tr>
								<th>CIRCLE</th>
								<th>SUBDIVISION</th>
								<th>NAME</th>
								<th>ACCNO</th>
								<th>METERNO</th>
								<th>READING MONTH</th>
								<th>CURR_READING</th>
								<th>PRE_MONTH_READING</th>
								<th>CONSUMPTION</th>
							</tr>
						</thead>


						<tbody>

							<c:forEach var="element" items="${zeroConcmp}">
								<tr>
									<td>${element[9]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${element[3]}"></fmt:formatNumber></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${element[4]}"></fmt:formatNumber></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${element[5]}"></fmt:formatNumber></td>
								</tr>

							</c:forEach>
						</tbody>

					</table>
				</div>
			</div>
		</div>


	</div>

</div>





