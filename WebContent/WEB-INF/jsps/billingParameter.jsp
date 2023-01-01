<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
	   	        
	   		  $("#selectedDateId").val('${month}');
	   		  
	   		    $("#meterVal").val('${meterno}');
	   		   
	    
		App.init();
		TableManaged.init();
		FormComponents.init();	
		loadSearchAndFilter('sample_editable_1');
				
		 $('#MDMSideBarContents,#MIS-Reports,#billingParameter').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
      	});
		
		//	var t = new ScrollableTable(document.getElementById('myScrollTable'), 150,250);
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
				<!-- Display Failure Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Failure Message -->
							
				
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Billing Determinants</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form method="post" id="meterMasterForm" action="./getBillingParameters">
										<table>
										<tr><td>Meter Number</td><td><input type="text" autocomplete="off" class="form-control" id="meterVal" name="meterNo" required="required"></td></tr>
										<tr><td>Select Month</td><td>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedDateId"  readonly >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
																<td>
																<div>
												<button name="search"   type="submit" class="btn green" id="getSearch" >view</button>
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
							<div class="caption"><i class="fa fa-globe"></i>Billing data</div>
							 <a href="#" id="excelExport" class="btn green" style="margin-left: 699px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
					
								<BR><BR>
					
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1" >
									<thead>									
										<tr>								
												<th >slNo</th>
											    <th>READING MONTH</th>
												<th>METERNO</th>
												<th>KWH</th>
												<th>CURRKWH</th>
												<th>PRE-MONTH-KWH</th>
											    <th>CONSUMPTION</th>
	            						</tr>
									</thead>
									<tbody>
						<c:set var="count" value="1" scope="page"> </c:set>
							<c:forEach var="element" items="${billingParameters}">
								<tr>
								   <td >${count}</td>
									 <td>${element[1]}</td> <!--ACC_NO  -->
									 <td>${element[0]}</td>  <!-- METERNO -->
									 <td><fmt:formatNumber type="number" value="${element[3]}"></fmt:formatNumber> </td>  <!-- ZONE -->
									 <td><fmt:formatNumber type="number" value="${element[3]}"></fmt:formatNumber> </td>
									 <td><fmt:formatNumber type="number" value="${element[2]}"></fmt:formatNumber> </td>
									 <td><fmt:formatNumber type="number" value="${element[4]}"></fmt:formatNumber> </td>
								</tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
								</table>
								</div>
								</div>
								</div>
								
								
								
								
								
						
					<!-- END METERMASTER TABLE DATA -->
				</div>
				
				
				<!--  newwwwwwwwwwwwww-->
				
				<%-- <div class="row">
				<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>TOD KWH values</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
					
					
								<BR><BR>
					
					<div class="col-md-12"  style="overflow: scroll;">
					<table class="table table-striped table-bordered table-hover" id="sample_4" >
									<thead>									
										<tr>								
												<th >SlNo</th>
											    <th>READING MONTH</th>
											    <th>T1KWH</th>
												<th>T2KWH</th>
												<th>T3KWH</th>
												<th>T4KWH</th>
											    <th>T5KWH</th>
											    <th>T6KWH</th>
											    <th>T7KWH</th> 
												
												
												
												
												
	            						</tr>
									</thead>
									<tbody>
						<c:set var="count" value="1" scope="page"> </c:set>
							<c:forEach var="element" items="${billingParameters}">
								<tr>
								 
								   <td >${count}</td>
									 <td>${element[0]}</td> <!--ACC_NO  -->
									 <td><fmt:formatNumber type="number" value="${element[11]}"></fmt:formatNumber> </td>
									 <td><fmt:formatNumber type="number" value="${element[12]}"></fmt:formatNumber> </td>
									<td><fmt:formatNumber type="number" value="${element[13]}"></fmt:formatNumber> </td>
									<td><fmt:formatNumber type="number" value="${element[14]}"></fmt:formatNumber> </td>
									<td><fmt:formatNumber type="number" value="${element[15]}"></fmt:formatNumber> </td>
									<td><fmt:formatNumber type="number" value="${element[16]}"></fmt:formatNumber> </td>
									<td><fmt:formatNumber type="number" value="${element[17]}"></fmt:formatNumber> </td>
									
								</tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
								</table>
								</div>
								</div>
								</div>
				</div> --%>
			</div>
			
			
			
			
			
			