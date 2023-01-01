<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <script  type="text/javascript">
  $(".page-content").ready(function()
   {
	  //alert('${selectedMonth}');
	  
			  if('${sectionDash.size()}'>0){
			    		$('#circle').val('${circleList}');
			   			$('#division').val('${divisionByCirclelist}');
			   			$('#subdivision').val('${subdivByCirclelist}');
		      }
	    $("#datedata").val(getPresentMonthDate('${selectedMonth}'));
	  
			
   	    	if('${dashbo.size()}'==0){
		    	$('#dashData').hide();
		    }
   	    	if('${key}'=='Yes'){
		    	$('#sectDashDetails').show();
		    }
   	    	if('${sectionDash.size()}'>0){
		    	$('#sectDashDetails').show();
		    }
   	    	App.init();
   	    	TableManaged.init();
   	    	TableEditable.init();
   	    	FormComponents.init();
   	 	$('#MDMSideBarContents,#MIS-Reports,#administrativeReport').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	   
   	    	   });
  
  
 
 
  
 
   
  </script>
 <style>
.input-medium {
    width: 160px !important;
}
.input-large {
    width: 200px !important;
}
</style>
<div class="page-content" >
	<!-- BEGIN DASHBOARD STATS -->
	
	<div class="row" style="margin-left: 30%;">

		<form action="./administrativeReport" method="post">
		<table>
			<tr>
				<td>SELECT MONTH:</td>
				<td><div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
									<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
								</div></td>
								<td><button type="submit" id="dataview" class="btn green">View Data</button></td>
			</tr>
		</table>
		</form>
	</div>
	<br/>
	<c:if test="${results ne 'notDisplay'}">
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red;font-size:15px;">${results}</span>
					</div>
				</c:if>
	<br/>
	<div class="row" id="dashData">
	<div class="col-md-12">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Circle wise Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
				<div class="portlet-body">
							<div class="table-scrollable">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>SR.NO</th>
											<th>CIRCLE</th>
											<th>HTI_CMRI</th>
											<th>HT_CMRI</th>
											<th>LT_CMRI</th>
											<th>CMRI_Total</th>
											
										</tr>
									</thead>
									
									<tbody>
								
									<c:forEach items="${dashbo}" var="element">
									   
									     <tr>
											<%-- <td><a href="./dashboard?circle=${element[1]}" id="dsasad${element[1]}">${element[1]}</a></td> --%>
											<td>${element[0]}</td>
											<td><a href="./administrativeReport?circle=${element[1]}&month=${selectedMonth}" id="dsasad${element[1]}">${element[1]}</a></td>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td>${element[4]}</td>
											<td>${element[5]}</td>
										</tr>
									</c:forEach>
									    
									</tbody>
								</table>
							</div>
						</div>
			    	</div>
				</div>
				</div>
			
			<div id="sectDashDetails" hidden="true">	
				<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Circle Details : &nbsp;&nbsp;<span style="background-color: #fcb322; color: black"> ${subDivisionName}</span> 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_editable_1', 'Dashboard Sub-Divisionwise Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					<div class="table-scrollable">
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr style="background-color: lightgray; ">
								
								<th>SL NO</th>
								<th>CIRCLE</th>
								<th>DIVISION</th>
								<th>SUBDIVISION</th>
								<th>HTI_CMRI</th>
								<th>HT_CMRI</th>
								<th>LT_CMRI</th>
								<th>CMRI_TOTAL</th>
								<th>HTI_MANUAL</th>
								<th>HT_MANUAL</th>
								<th>LT_MANUAL</th>
								<th>MANUAL_TOTAL</th>
								<th>HTI_RNC</th>
								<th>HT_RNC</th>
								<th>LT_RNC</th>
								<th>DEFECTIVE</th>
								<th>HTI_TOTAL</th>
								<th>HT_TOTAL</th>
								<th>LT_TOTAL</th>
								<th>GRAND_TOTAL</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="1" scope="page" />
							<c:forEach items="${sectionDash}" var="element">
							     <tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									<td>${element[14]}</td>
									<td>${element[15]}</td>
									<td>${element[16]}</td>
									<td>${element[17]}</td>
									<td>${element[18]}</td>
									<td>${element[19]}</td>
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
				
</div>