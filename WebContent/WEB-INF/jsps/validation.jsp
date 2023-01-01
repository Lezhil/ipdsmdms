<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						//high('${instantanousData[0].rPhaseVal}');
						App.init();
						TableEditable.init();
						TableManaged.init();
						FormComponents.init();
						UIDatepaginator.init();
						$('#MDMSideBarContents,#vee').addClass('start active ,selected');

						/*  if('${billedDataList.size()}'>0)
						{
							  
						displayConsuptionChart();
						} */

						/* $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}'));
						$("#loadSurveydateId").val(moment(new Date).format('DD-MM-YYYY')); */
						$('#MDMSideBarContents,#vee,#Validation').
						addClass('start active ,selected');
				$(
						"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
						.removeClass('start active ,selected');
				$('#meterVal').val('${meterNo}');

			});
</script>
<div class="page-content">

	<div class="portlet box blue">


		<!-- /.col-md-6 -->

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Validation
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>


		<div class="portlet-body">

			<div class="col-md-12">

				<form method="post" id="meterMasterForm" action="">
					<table>
						<tr>
							<td>Meter Number</td>
							<td><input type="text" class="form-control" id="meterVal"
								name="meterNo" required="required"></td>
						</tr>
						<tr>
							<td>Select YearMonth</td>
							<td>
								<div class="input-group input-medium date date-picker"
									data-date-format="yyyymm" data-date-viewmode="years" id="five">
									<input type="text" class="form-control" name="selectedDateName"
										id="selectedDateId" readonly> <span
										class="input-group-btn">
										<button class="btn default" type="button" id="six">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
						</tr>

						<tr>
							<td></td>
							<td><input style="background-color: green;" type="submit" class="form-control" id="submit"
								value="Submit" required="required"></td>
						</tr>
					</table>





				</form>



			</div>
			<br /> <br />
			<div class="clearfix"></div>
			<div class="clearfix"></div>
           <div>
           <table border="1" style="width:1296px;">
           
           <thead>
           <tr><th>Meter No</th><th>High/Low Check</th><th>Missing Read Check</th><th>Consecutive Missing Reading Check</th><th>Load Factor Check</th><th>Sum Check</th><th>Last Month Reading</th><th>Maximum Reading Check</th></tr>
           
           </thead>
           <tbody></tbody>
					<tr><td>HRT29278</td><td>Yes</td><td>Yes</td><td>Consecutive missing check not required</td><td>Yes</td><td>Yes</td><td>All last month data as been processed</td><td>No</td></tr>	

						<tr>
							
							<td colspan="8"><input style="color: green;" type="submit" class="form-control" id="submit"
								value="Submit" required="required"></td>
						</tr>
					</table>
           </div>

		</div>
	</div>

	<c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>
	<!-- End Error Message -->

	<!-- Start Intantanous -->

</div>


