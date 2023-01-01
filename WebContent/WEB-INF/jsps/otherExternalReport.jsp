<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  TableEditable.init();
		    	Tasks.initDashboardWidget();
		    	 FormComponents.init();
		    	 TableManaged.init();
   	    	
		    	 $('#MDASSideBarContents,#other-Reports,#otherExternalReports').addClass('start active ,selected');
	   	    	  $("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
	   	    	   $('#other-Reports').addClass('start active ,selected');
	   	    	   $("#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
	   	    	     
	   	    	   });
	   	    	   
   	    	   
  
  function validation()
  {
	  if(document.getElementById("reportName").value == 0)
		  {
		  	bootbox.alert("Select the Report...");
		  	return false;
		  }
  }
   	    	   </script>

<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Other Reports</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="./simpleExternaleReport" method="post">
							<table>
							<tr>
							<td>
							Select Month : 
							</td>
							<td>
							<div class="input-group input-medium date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportsMonth" id="reportsMonth"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							</tr>
							
							<tr>
							<td>Select Report : 
							</td>
							<td>
							<select  id="reportName" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="reportName" >
							<option value="0">select</option>
							<option value="1">Defective Meter List</option>
							<option value="2">CD is Zero & KVA above 50</option>
							<option value="3">Zero Consumption List</option>
							<option value="4">Consumption Fall Down(less than 30%)</option>
							<option value="5">Consumption Fall Down(above 30%)</option>
							
							</select>
							</td>
							</tr>
							</table>
							<div class="modal-footer">
							<button class="btn blue pull-left" onclick="return validation()">Generate Report</button>  
							
							</div>
							
							
							</form>								
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
	<c:if test="${defectiveList.size() > 0 || cdList.size() > 0 || consumptioList.size() > 0 || consumptionfalldownlessList.size() > 0 || consumptionfalldowaboveList.size() > 0}">
	
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Reports</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
						<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
							<c:if test="${defectiveList.size() > 0 }">
							<thead>
							<tr>
							<th>Account No</th>
							<th>MeterNo</th>
							<th>Name</th>
							<th>Address</th>
							<th>MF</th>
							<th>KWORHP</th>
							<th>Sanload</th>
							<th>CD</th>
							<th>Reading Remark</th>
							<th>RTC</th>
							<th>MRName</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="element" items="${defectiveList }">
							
							<tr>
							<td>${element[0] }</td>
							<td>${element[1] }</td>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							<td>${element[10] }</td>
							</tr>
							
							</c:forEach>
							</tbody>
							</table>
							</c:if>
							<c:if test="${cdList.size() > 0 }">
							<thead>
							<tr>
							<th>Account No</th>
							<th>MeterNo</th>
							<th>Name</th>
							<th>Address</th>
							<th>MF</th>
							<th>KWORHP</th>
							<th>Sanload</th>
							<th>CD</th>
							<th>CURRDNGKVA</th>
							<th>XCURRDNGKVA</th>
							<th>Reading Remark</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="element" items="${cdList }">
							<c:if test="${element[11] > element[12]}">
							<c:if test="${element[11] < element[13]}">
							<tr>
							<td>${element[0] }</td>
							<td>${element[1] }</td>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							<td>${element[10] }</td>
							</tr>
							</c:if>
							
							</c:if>
							
							
							</c:forEach>
							</tbody>
							</c:if>
							<c:if test="${consumptioList.size() > 0 }">
							<thead>
							<tr>
							<th>Account No</th>
							<th>MeterNo</th>
							<th>Name</th>
							<th>Address</th>
							<th>MF</th>
							<th>KWORHP</th>
							<th>Sanload</th>
							<th>CD</th>
							<th>Reading Remark</th>
							<th>KWH Units</th>
							<th>MRName</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="element" items="${consumptioList }">
							<tr>
							<td>${element[0] }</td>
							<td>${element[1] }</td>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							<td>${element[10] }</td>
							</tr>
							
							</c:forEach>
							</tbody>
							</c:if>
							<c:if test="${consumptionfalldownlessList.size() > 0 }">
							<thead>
							<tr>
							<th>Account No</th>
							<th>MeterNo</th>
							<th>Name</th>
							<th>Address</th>
							<th>Tariff Code</th>
							<th>CD</th>
							<th>Cuurent Reading</th>
							<th>Average Units</th>
							<th>Previous Units</th>
							<th>Current Units</th>
							
							</tr>
							</thead>
							<c:forEach var="element" items="${consumptionfalldownlessList }">
							
							
							<tr>
							<td>${element[0] }</td>
							<td>${element[1] }</td>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							
							</tr>
							
							</c:forEach>
							
							</c:if>
							
							<c:if test="${consumptionfalldowaboveList.size() > 0 }">
							<thead>
							<tr>
							<th>Account No</th>
							<th>MeterNo</th>
							<th>Name</th>
							<th>Address</th>
							<th>Tariff Code</th>
							<th>CD</th>
							<th>Cuurent Reading</th>
							<th>Average Units</th>
							<th>Previous Units</th>
							<th>Current Units</th>
							
							</tr>
							</thead>
							<c:forEach var="element" items="${consumptionfalldowaboveList }">
							
							
							<tr>
							<td>${element[0] }</td>
							<td>${element[1] }</td>
							<td>${element[2] }</td>
							<td>${element[3] }</td>
							<td>${element[4] }</td>
							<td>${element[5] }</td>
							<td>${element[6] }</td>
							<td>${element[7] }</td>
							<td>${element[8] }</td>
							<td>${element[9] }</td>
							
							</tr>
							
							</c:forEach>
							
							</c:if>
							
							
							
							</table>
							
												
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			</c:if>
</div>   	    	   