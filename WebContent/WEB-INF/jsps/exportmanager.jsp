<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		var elements = document.getElementsByName("meternumber");
	    for (var i = 0; i < elements.length; i++) {
	        elements[i].oninvalid = function(e) {
	            e.target.setCustomValidity("");
	            if (!e.target.validity.valid) {
	                e.target.setCustomValidity("Please Enter Meter Number");
	            }
	        };
	        elements[i].oninput = function(e) {
	            e.target.setCustomValidity("");
	        };
	    }
	    
		App.init();
		TableManaged.init();
		FormComponents.init();			
		$('#export-Manager').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Export Manager</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form action="./getmonthdata" class="form-horizontal" method="post">
									<div class="form-body">
										<div class="form-group">
													<label  class="col-md-3 control-label">Search By</label>
													<div class="col-md-4">
														<input type="text" class="form-control" placeholder="Enter text" required="required" name="meternumber" value="${selectedmeterNumber}" >
														<span class="help-block">Enter Meter Number</span>
													</div>
										</div>																																	
													
										<div class="form-actions top fluid ">
											<div class="col-md-offset-3 col-md-9">
												<button name="metermaster" onclick="form.action='metermasterexport';" type="submit" class="btn green">View Meter Data</button>
												<!-- <button name="billingdata" onclick="form.action='billingdataexport';" type="submit" class="btn green">View Billing Data</button> --> 
											</div>
										</div>
									</div>
								</form>
								<br>							
								
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('allValuetable','Export Meter Data');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<br>
								<br>
								<table class="table table-striped table-bordered table-hover" id="sample_2">
									<thead>									
										<tr>	
											<c:if test="${ not empty meterMasterSelected}">									
												<th>METERNO</th>
												<th>READING MONTH</th>
												<th>METERSTATUS</th>
												<th>PHASE</th>
												<th>CTRN</th>
												<th>CTRD</th>
												<th>AMPRATING</th>
												<th>CURRENT KWH</th>
												<th>CURRENTKVAH</th>
												<th>CURRENT KVA</th>
											    <th>METER MAKE</th>
											</c:if>
											
											<c:if test="${ not empty billDataSelected}">
												<th>METERNO</th>
												<th>READING MONTH</th>
												<th>RDATE</th>
												<th>KWH</th>
												<th>KWHUNIT</th>
												<th>KVH</th>
												<th>KVHUNIT</th>
												<th>KVA</th>
												<th>KVAUNIT</th>
												<th>KWHARB</th>
											    <th>PFARB</th>
											</c:if>
	            						</tr>
									</thead>
									<tbody>
																		
										 <c:forEach var='meter' items='${exportmeterMaterData}'>
										 	<tr id="sampel" class="odd gradeX">									 		
											 	<td><c:out value="${meter.metrno}"/></td>
											 	<td><c:out value="${meter.rdngmonth}"/></td>
											 	<td><c:out value="${meter.meterstatus}"/></td>
											 	<td><c:out value="${meter.phase}"/></td>
											 	<td><c:out value="${meter.ctrn}"/></td>
											 	<td><c:out value="${meter.ctrd}"/></td>
											 	<td><c:out value="${meter.amprating}"/></td>
											 	<td><c:out value="${meter.currdngkwh}"/></td>
											 	<td><c:out value="${meter.currrdngkvah}"/></td>
											 	<td><c:out value="${meter.currdngkva}"/></td>
											 	<td><c:out value="${meter.mtrmake}"/></td> 
										 	</tr>
										 </c:forEach>									 									 
										 
										 <c:forEach var='meter' items='${exportmeterBillData}'>
											 	<tr id="sampel" class="odd gradeX">									 		
												 	<td><c:out value="${meter.meterno}"/></td>
												 	<td><c:out value="${meter.month}"/></td>
												 	<td><c:out value="${meter.rdate}"/></td>
												 	<td><c:out value="${meter.kwh}"/></td>
												 	<td><c:out value="${meter.kwhunit}"/></td>
												 	<td><c:out value="${meter.kvh}"/></td>
												 	<td><c:out value="${meter.kvhunit}"/></td>
												 	<td><c:out value="${meter.kva}"/></td>
												 	<td><c:out value="${meter.kvaunit}"/></td>
												 	<td><c:out value="${meter.kwharb}"/></td>
												 	<td><c:out value="${meter.pfarb}"/></td> 
											 	</tr>
										 </c:forEach>
										 
									</tbody>
								</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
			<div id="allValue" style="display:none">
				<table id="allValuetable">
					<thead>									
						<tr>	
							<c:if test="${ not empty exportmeterMaterData}">									
								<th>METERNO</th>
								<th>READING MONTH</th>
								<th>METERSTATUS</th>
								<th>PHASE</th>
								<th>CTRN</th>
								<th>CTRD</th>
								<th>AMPRATING</th>
								<th>CURRENT KWH</th>
								<th>CURRENTKVAH</th>
								<th>CURRENT KVA</th>
							    <th>METER MAKE</th>
							</c:if>
							
							<c:if test="${ not empty exportmeterBillData}">
								<th>METERNO</th>
								<th>READING MONTH</th>
								<th>RDATE</th>
								<th>KWH</th>
								<th>KWHUNIT</th>
								<th>KVH</th>
								<th>KVHUNIT</th>
								<th>KVA</th>
								<th>KVAUNIT</th>
								<th>KWHARB</th>
							    <th>PFARB</th>
							</c:if>
         						</tr>
					</thead>
					<tbody>
														
						 <c:forEach var='meter' items='${exportmeterMaterData}'>
						 	<tr>									 		
							 	<td><c:out value="${meter.metrno}"/></td>
							 	<td><c:out value="${meter.rdngmonth}"/></td>
							 	<td><c:out value="${meter.meterstatus}"/></td>
							 	<td><c:out value="${meter.phase}"/></td>
							 	<td><c:out value="${meter.ctrn}"/></td>
							 	<td><c:out value="${meter.ctrd}"/></td>
							 	<td><c:out value="${meter.amprating}"/></td>
							 	<td><c:out value="${meter.currdngkwh}"/></td>
							 	<td><c:out value="${meter.currrdngkvah}"/></td>
							 	<td><c:out value="${meter.currdngkva}"/></td>
							 	<td><c:out value="${meter.mtrmake}"/></td> 
						 	</tr>
						 </c:forEach>									 									 
						 
						 <c:forEach var='meter' items='${exportmeterBillData}'>
							 	<tr>									 		
								 	<td><c:out value="${meter.meterno}"/></td>
								 	<td><c:out value="${meter.month}"/></td>
								 	<td><c:out value="${meter.rdate}"/></td>
								 	<td><c:out value="${meter.kwh}"/></td>
								 	<td><c:out value="${meter.kwhunit}"/></td>
								 	<td><c:out value="${meter.kvh}"/></td>
								 	<td><c:out value="${meter.kvhunit}"/></td>
								 	<td><c:out value="${meter.kva}"/></td>
								 	<td><c:out value="${meter.kvaunit}"/></td>
								 	<td><c:out value="${meter.kwharb}"/></td>
								 	<td><c:out value="${meter.pfarb}"/></td> 
							 	</tr>
						 </c:forEach>
						 
					</tbody>
				</table>
			</div>
</div>