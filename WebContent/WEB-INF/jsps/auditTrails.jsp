<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();			
		$('#MDMSideBarContents,#vee,#ondempro').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  

	
</script>
 <div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body">
							
							<form action="" id="statusId" method="POST">
							<div class="portlet box blue">
								<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Audit Trails:
						 
							</div>
							
						</div>
							</div>
							<table>

							<tr>
							
							<th>Date Selections: </th>
								<td><div class="col-md-3">
										<div
											class="input-group input-large date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" placeholder="From Date"
												class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span> <input type="text"
												placeholder="To Date" class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="toDate" id="toDate">
										</div>
									</div></td>

								<td><button type="submit" id="dataview" class="btn yellow"
										formaction="./getAuditTrailData" formmethod="post">
										<b>View</b>
									</button></td>
							
							</tr>
							
							
							</table>
							
							</form>
									<div class="tabbable tabbable-custom">
								
								
								
							  <table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th>Sl.No</th>
										<th>MeterNo</th>
										<th >Transcation Type</th>
										<th >Date & Time</th>
										
										<!-- <th rowspan="2">SixMonthAvg</th>
										<th rowspan="2">TwelveMonthAvg</th> -->
							            
            					</tr>
            					
								</thead>
								<tbody>
									<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${list}">
									<tr >
										
										
										<td>${count}</td>
										<td>${element[2]}</td>
										<td>${element[1]}</td>
										<td>${element[3]}</td>
									 <c:set var="count" value="${count+1}" scope="page"/>	
										
								</tr>
									</c:forEach>
									
								
									 							
								</tbody>
							</table>
			
										</div>				
						</div>
						
						
		</div>
		</div>
		</div>
		 
		
		
		<script>

   
</script>