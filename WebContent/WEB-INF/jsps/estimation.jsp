<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<<style>
#valuenid th{
width: 10px;
}
</style>
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

						/*  if('${billedDataList.size()}'>0)
						{
							  
						displayConsuptionChart();
						} */

						/* $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}'));
						$("#loadSurveydateId").val(moment(new Date).format('DD-MM-YYYY')); */
						$('#MDMSideBarContents,#vee,#Estimation').addClass('start active ,selected');
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
				<i class="fa fa-reorder"></i>Estimation
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

				<form id="meterMasterForm" >
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
							<td><input style="background-color: green;" type="button" class="form-control" id="submit"
								onclick=getVeeActData()    value="Submit" required="required"></td>
						</tr>
					</table>





				</form>



			</div>
			<br /> <br />
			<div class="clearfix"></div>
			<div class="clearfix"></div>
           <div>
          <!--  <span style="background-color: yellow">R--Reading | G.H.R--General Hours Reading | A--Actual |  E--Estimated | RKWH--Reading KWH</span> -->
          <!--  <table border="1" id="valid" style="height: 20px;">
           
           <thead>
           <tr><th>Meter No</th><th colspan="2">Reading Date</th><th colspan="2">RKWH  </th><th colspan="2">RKVAH   </th><th colspan="2">GHR KWH</th><th colspan="2">RKWH 0530-0800Hrs</th><th colspan="2">RKWH 1730-1800Hrs</th><th colspan="2">RKWH 1800-1830Hrs</th><th colspan="2">RKWH 1830-1900Hrs</th><th colspan="2">RKWH 1900-2100Hrs</th><th colspan="2">RKWH 2100-2200Hrs</th><th colspan="2">MDI Reading</th><th colspan="2">Power Factor</th></tr>
           <tr id="valuenid"><th></th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th><th>A</th><th>E</th></tr>
           </thead>
           <tbody></tbody>
					 <tr><td>123</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>	 

						<tr>
							
							<td colspan="25"><input style="color: green;" type="submit" class="form-control" id="submit"
								value="Submit" required="required"></td>
						</tr>
					</table> -->
					
					<table border="1" style="left:120px; width:1285px;">
           
           <thead>
           <tr><th colspan="2">Meter No</th><th colspan="2" id="mtrnoId"></th></tr>
           <tr><th>Parameters</th><th>Actual</th><th>Estimnated</th></tr>
           
           </thead>
           <tbody></tbody>
					<tr><td>Reading Date</td><td>05/09/2018</td><td>05/09/2018</td></tr>	
                   <tr><td>Reading KWH</td><td id="kwh"></td><td>2500</td></tr>	
                   <tr><td>Reading KVAH</td><td id="kva"></td><td>2100</td></tr>	
                   <tr><td>General Hours Reading KWH</td><td id="kwh_a"></td><td>150</td></tr>	
                   <tr><td>Reading KWH 0530-0800 Hrs</td><td id="kwh_b"></td><td>1000</td></tr>	
                   <tr><td>Reading KWH 1730-1800 Hrs</td><td id="kwh_c"></td><td>500</td></tr>	
                   <tr><td>Reading KWH 1800-1830 Hrs</td><td id="kwh_d"></td><td>100</td></tr>	
                   <tr><td>Reading KWH 1830-1900 Hrs</td><td id="kwh_e"></td><td>100</td></tr>	
                   <tr><td>Reading KWH 1900-2100 Hrs</td><td id="kwh_f"></td><td>500</td></tr>	
                   <tr><td>Reading KWH 2100-2200 Hrs</td><td id="kwh_g"></td><td>150</td></tr>	
                    <tr><td>MDI Reading</td><td id=""></td><td>2000</td></tr>
                     <tr><td>Power Factor</td><td id="pf"></td><td>2200</td></tr>
						<tr>
							
							<td colspan="3"><input style="color: green;" type="submit" class="form-control" id="submit"
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
<script>
function getVeeActData()
{
	    var meterno=$("#meterVal").val();
		var billmonth=$("#selectedDateId").val();
	//alert(zone);
	$.ajax({
		type : 'GET',
		url : "./veeEstimationActual",
		data:{mtrno:meterno,
			bm:billmonth
			
		},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				var r=response[0];
				$("#mtrnoId").html(meterno);
                $("#kwh").html(r[0]);
                $("#kva").html(r[1]);
                $("#kwh_a").html(r[2]);
                $("#kwh_b").html(r[3]);
                $("#kwh_c").html(r[4]);
                $("#kwh_d").html(r[5]);
                $("#kwh_e").html(r[6]);
                $("#kwh_f").html(r[7]);
                $("#kwh_g").html(r[8]);
                $("#pf").html(r[9]);

				}
			
		}
	});

	
}

</script>

