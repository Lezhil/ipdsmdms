<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
	   	        
	   		  $("#selectedDateId").val('${month}');
	   		  
	   		    $("#meterVal").val('${meterno}');
	   		   
	    
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				loadSearchAndFilter('sample_1');
				

				$('#MDMSideBarContents,#MIS-Reports,#demandExceed').addClass('start active ,selected');
		  			 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
		  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		  		
      	});
  

	
	  
		
		
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Demand Exceed Report</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form method="post" id="meterMasterForm" action="./demandExceedList">
										<table>
										<tr><td>Select Month</td><td>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<input type="text" class="form-control" autocomplete="off" name="selectedDateName" id="selectedDateId"   >
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
							<div class="caption"><i class="fa fa-globe"></i>Meter List</div>
							
						</div>					
											
						<div class="portlet-body">
					
						<div class="table-toolbar">
								 <div class="btn-group" >
								
									<br/>
									<br/>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="print">Print</a></li>
										<li><a href="#" id="cmd" onclick="$('#sample_1').tableExport({fileName:'Demand Exceed Report',type:'pdf',tableId:'sample_1'})">Export to pdf</a></li>
										<li><a href="#" id="excelExport" onclick="tableToExcel('sample_1', 'Demand Exceed Report')">Export to Excel</a></li>
									</ul>
								</div>
							</div> 
					
					<table class="table table-striped table-hover table-bordered" id="sample_1" >
									
									<thead>
							<tr>
												<th>SlNo</th>
												<th>CIRCLE</th>
												<th>SUBDIVISION</th>
											    <th>NAME</th>
												<th>ACCNO</th>
												<th>METERNO</th>
											    <th>READING MONTH</th>
												<th>KVA</th>
												<th>MF</th>
												<th>KVA*MF</th>
												<th>CD</th>
												<th>EXCEEDED</th>
							</tr>
						</thead>
									
									<tbody>
						<c:set var="count" value="1" scope="page"> </c:set>
							<c:forEach var="element" items="${zeroConcmp}">
								<tr>
								   <td >${count}</td>
								    <td>${element[0]}</td>
								     <td>${element[1]}</td>
									 <td>${element[3]}</td> <!--ACC_NO  -->
									 <td>${element[4]}</td>  <!-- METERNO -->
									 <td>${element[5]}</td>  <!-- ZONE -->
									 <td>${element[6]}</td> <!--ACC_NO  -->
									 <td>${element[7]}</td>  <!-- METERNO -->
									 <td>${element[8]}</td>  <!-- ZONE -->
									 <td>${element[9]}</td>  <!-- METERNO -->
									 <td>${element[10]}</td>  <!-- ZONE -->
									  <td>${element[11]}</td>  
									 </tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
								</table>
								</div>
								</div>
								</div>
								
					
				</div>
				
			</div>
			
			
			
			
			
			