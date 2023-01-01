<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){			
		$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
		App.init();
		TableManaged.init();
		FormComponents.init();
		$('#MDMSideBarContents,#metermang,#meterMaster').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
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
								<form action="./getmonthdata1" method="post">
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
										<!-- <th>METERSTATUS</th> -->
										<th>PHASE</th>
										<th>CTRN</th>
										<th>CTRD</th>
										<th>AMPRATING</th>
										<th>CURRENT KWH</th>
										 <th>CURRENT KVAH</th>
										<th>CURRENT KVA</th> 
										
										
										
									   
            					</tr>
								</thead>
								 <tbody>
									
							<c:set var="count" value="1" scope="page"></c:set>
									 <c:forEach var="element" items="${meterMaterData}">
								<tr>
									 
									
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[6]}</td>

								</tr>
								<c:set var="count" value="{count+1}" scope="page"></c:set>
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