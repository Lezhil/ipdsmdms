<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){			
		$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
		App.init();
		TableManaged.init();
		FormComponents.init();
		$('#meterMaster').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Meter Master</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form action="./getmonthdata" method="post">
								<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
										<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
										</span>			
								</div>				
										<span class="help-block">Select Month</span>																							
								<br>				
								<div>
									<button type="submit" id="dataview" class="btn green">View Data</button>
								</div>
								</form>
								<br>							
							
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th>METERNO</th>
										<th>ACCOUNT NUMBER</th>
										<th>METERSTATUS</th>
										<th>PHASE</th>
										<th>CTRN</th>
										<th>CTRD</th>
										<th>AMPRATING</th>
										<th>CURRENT WH</th>
										<th>CURRENT VAH</th>
										<th>CURRENT VA</th>
										
										<th>TOD1 Wh</th>
										<th>TOD2 Wh</th>
										<th>TOD3 Wh</th>
										<th>TOD4 Wh</th>
										<th>TOD5 Wh</th>
										<th>TOD6 Wh</th>
										<th>TOD7 Wh</th>
										
										<th>TOD1 Vah</th>
										<th>TOD2 Vah</th>
										<th>TOD3 Vah</th>
										<th>TOD4 Vah</th>
										<th>TOD5 Vah</th>
										<th>TOD6 Vah</th>
										<th>TOD7 Vah</th>
										
										
									    <th>METER MAKE</th>
            					</tr>
								</thead>
								 <tbody>
									<c:set var="data" value="${meterMaterData}"></c:set>
									 <c:forEach var='meter' items='${data}'>
									 	<tr id="sampel" class="odd gradeX">									 		
										 	<td><c:out value="${meter.metrno}"/></td>
										 	<td><c:out value="${meter.accno}"/></td>
										 	<td><c:out value="${meter.meterstatus}"/></td>
										 	<td><c:out value="${meter.phase}"/></td>
										 	<td><c:out value="${meter.ctrn}"/></td>
										 	<td><c:out value="${meter.ctrd}"/></td>
										 	<td><c:out value="${meter.amprating}"/></td>
										 	<td><c:out value="${meter.currdngkwh}"/></td>
										 	<td><c:out value="${meter.currrdngkvah}"/></td>
										 	<td><c:out value="${meter.currdngkva}"/></td>
										 	
										 	<td><c:out value="${meter.t1kwh}"/></td>
										 	<td><c:out value="${meter.t2kwh}"/></td>
										 	<td><c:out value="${meter.t3kwh}"/></td>
										 	<td><c:out value="${meter.t4kwh}"/></td>
										 	<td><c:out value="${meter.t5kwh}"/></td>
										 	<td><c:out value="${meter.t6kwh}"/></td>
										 	<td><c:out value="${meter.t7kwh}"/></td>

										 	<td><c:out value="${meter.t1kvah}"/></td>
										 	<td><c:out value="${meter.t2kvah}"/></td>
										 	<td><c:out value="${meter.t3kvah}"/></td>
										 	<td><c:out value="${meter.t4kvah}"/></td>
										 	<td><c:out value="${meter.t5kvah}"/></td>
										 	<td><c:out value="${meter.t6kvah}"/></td>
										 	<td><c:out value="${meter.t7kvah}"/></td>
										 	
										 	<td><c:out value="${meter.mtrmake}"/></td> 
									 	</tr>
									 </c:forEach>									 									 
									 
								</tbody> 
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>

<script>
	/* $('#dataview').click(function () { 		
		var month="month="+$("#datedata").val();
		$.ajax({
			type : "POST",
			url : "./getmonthdata",
			dataType:"json",
			data:month,
			asynch:false,
			
			success : function(response) {
				
				alert(response[0].metrno);
				$("#sampel").append("<td>"+response.metrno+"/td>");
			}
		});
	});		
	 */
</script>