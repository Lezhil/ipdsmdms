<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  
  $(".page-content").ready(function()
   	    	   {     
	     $("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
	
	     $('#MDASSideBarContents,#assesmentsNew').addClass('start active ,selected');
	    	   $("#cdf-Import,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	
   	    	   });
  
  window.onload = setInterval("getParsedXml()", 5000);
  
  
  
  
  function Validation()
  {
	  if($("#billmonthID").val()==0)
	  {
		  bootbox.alert("Please select Reading Month");
		  return false;
	  }
	  if($("#circle").val()==0)
	  {
		 bootbox.alert("Please select Circle");
		 return false;
	  }
	  if($("#category").val()==0)
	  {
		  bootbox.alert("Please select Category");
		  return false;
	  }
	  if($("#tamperType").val()==0)
	  {
		  bootbox.alert("Please select Tamper Type");
		  return false;
	  }
  }
  </script>
  
	<div class="page-content" >
		 <c:if test = "${result ne 'notDisplay'}"> 			        
			    <div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color:red" >${result}</span>
				</div>
		</c:if> 
		<c:if test = "${not empty eventLength1 }">			        
			    <div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color:red" >Data Not Found</span>
				</div>
		</c:if>
		
		<!-- <div class="alert alert-danger display-show" id="alertMsg" style="display: none;">
				<button class="close" data-close="alert"></button>
				<span style="color:red" id="parseCompleteMsg"></span>
		</div> -->

		<div class="row">
			<div class="col-md-12">
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Assessment</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body">
							<!-- 	<div class="btn-group">	
									<button id="sample_editable" style="margin-left: 890px;" class="btn purple"  data-toggle="modal" data-target="#stack1">
									View Report
									</button>
								</div>
						 -->
							<form action="./parseassesments" id="uploadFile" enctype="multipart/form-data" method="post">
			        		<div class="form-body">
							   <div class="row">
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label">Select Month</label>
													<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
									 					<input type="text" name="billmonth" id="billmonth" class="form-control" required="required">
							 		 					<span class="input-group-btn"><button type="button" class="btn default"><i class="fa fa-calendar"></i></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
													</div>
											</div>
										</div>
										<div class="col-md-2" style="margin-top: 26px;">
											<div class="form-group">
												<button class="btn blue pull-left" id="uploadButton" onclick="return finalSubmit();">Generate Assessment</button>
											</div>
										</div>
										<div class="col-md-3" style="margin-top: 26px;">
											<div class="btn-group">
												<button id="sample_editable" class="btn purple"  data-toggle="modal" data-target="#stack1">
													View Report</button>
											</div>
										</div>
								</div>
							   </div>
					       </form>
					       
					        
					 	 <c:if test="${not empty eventLength }">
							<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id=excelExport onclick="tableToExcel('table_4','Export_Event_Data');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<BR><BR>
								
								<table class="table table-striped table-bordered table-hover" id="table_4" >
									<thead>									
										<tr>								
												<th>SLNO.</th>
											   <!--  <th>METER NO</th>
											    <th>CIRCLE</th>
											    <th>DIVISION</th>
												<th>SUBDIVISION</th>
												<th>CATEGORY</th>
												<th>MF</th>
												<th>TAMPER_TYPE </th>
												<th>OCCDATE </th>
												<th>RESTDATE </th>
												<th>UNITS</th>
												<th>ACTUAL UNITS</th>
												<th>UNITS_TOBECHARGE</th>
												<th>RATE</th>
												<th>AMOUNT</th>
												 -->
												<th>METER NO</th>
												 <th>CIRCLE</th>
											    <th>DIVISION</th>
												<th>SUBDIVISION</th>
												<th>CATEGORY</th>
												<th>TAMPER_TYPE </th>
												<th>RDNG MONTH </th>
												<th>NAME</th>
												<th>ADDRESS</th>
												<th>MTRMAKE</th>
												<th>COUNT </th>
										</tr>
									</thead>
									<tbody>
										 <c:set var="count" value="1"/>								
											<c:forEach var="event" items='${eventDetails}'>
										 	<tr id="sampel" class="odd gradeX">	
										 		<td>${count}</td>								 		
											    <%-- <td>${event.metrno}</td>
											    <td>${event.circle}</td>
											    <td>${event.division}</td>
											    <td>${event.subdiv}</td>
											    <td>${event.category}</td>
											    <td>${event.mtrmake}</td>
											    <td>${event.mf}</td>
											    <td>${event.tamper_type}</td>
											    <td>${event.occdate}</td>
											    <td>${event.restdate}</td>
											    <td>${event.units}</td>
											    <td>${event.actual_units}</td>
											    <td>${event.units_tobecharge}</td>
											    <td>${event.rate}</td>
											    <td>${event.amount}</td>
											    <td>${event.name}</td>
											    <td>${event.address}</td>  --%>
											    <td><a href="./assesmentReportDetails?billmonth=${event[7]}&circle=${event[1]}&category=${event[4]}&tamperType=${event[5]}&meterNo=${event[0]}" id="meterNo${event[0]}">${event[0]}</a></td>
											    <td>${event[1]}</td>
											    <td>${event[2]}</td>
											    <td>${event[3]}</td>
											    <td>${event[4]}</td>
											    <td>${event[5]}</td>
											    <td>${event[7]}</td>
											     <td>${event[8]}</td>
											    <td>${event[9]}</td>
											    <td>${event[10]}</td>
											    <td>${event[6]}</td>
											 </tr>
											   <c:set var="count" value="${count+1}"/>	
										 </c:forEach>								 									 
									</tbody>
								</table>
								 </c:if>	 
								
				       </div>
					</div>
					</div>
 			 </div>
							
							<c:if test="${not empty eventLength2 }">
							<div class="portlet box purple"> 
							 <div class="portlet-title" style="margin-top: 68px;"> 
								<div class="caption">
									<i class="fa fa-edit"></i>Meter Details :  &nbsp;&nbsp;<span style="background-color: #fcb322; color: black"> ${meterNo}</span>  
								</div>
							</div> </div>
								 <div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i></button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id=excelExport onclick="tableToExcel('sample_3','Export_Event_Data');">Export to Excel</a></li>
									</ul>
									</div>
								<BR><BR>
								
								
								<table class="table table-striped table-bordered table-hover" id="table_4" >
									<thead>									
										<tr>								
												<th>SLNO.</th>
											    <!--  <th>METER NO</th> 
											    <th>CIRCLE</th>
											    <th>DIVISION</th>
												<th>SUBDIVISION</th>
												<th>CATEGORY</th>-->
											<!-- 	<th>MTRMAKE</th> -->
												<th>MF</th>
												<!-- <th>TAMPER_TYPE </th> -->
												<th>OCCDATE </th>
												<th>RESTDATE </th>
												<th>UNITS</th>
												<th>ACTUAL UNITS</th>
												<th>UNITS_TOBECHARGE</th>
												<th>RATE</th>
												<th>AMOUNT</th>
											<!-- 	<th>NAME</th>
												<th>ADDRESS</th> -->
										</tr>
									</thead>
									<tbody>
										 <c:set var="count" value="1"/>	
										  <c:set var="totalunits" value="0"/>
										  <c:set var="totalactualunits" value="0"/>
										  <c:set var="totalcharge" value="0"/>	
											<c:forEach var="event" items='${assesmentdat}'>
										 	<tr id="sampel" class="odd gradeX">	
										 		<td>${count}</td>								 		
											   <%--  <td>${event.metrno}</td> 
											    <td>${event.circle}</td>
											    <td>${event.division}</td>
											    <td>${event.subdiv}</td>
											    <td>${event.category}</td>
											    <td>${event.mtrmake}</td>--%>
											    <td>${event.mf}</td>
											  <%--   <td>${event.tamper_type}</td> --%>
											    <td>${event.occdate}</td>
											    <td>${event.restdate}</td>
											    <td>${event.units}</td>
											    <td>${event.actual_units}</td>
											    <td>${event.units_tobecharge}</td>
											    <td>${event.rate}</td>
											    <td>${event.amount}</td>
											 <%--    <td>${event.name}</td>
											    <td>${event.address}</td>   --%>
											 </tr>
											   <c:set var="count" value="${count+1}"/>	
											   <c:set var="totalunits" value="${totalunits + event.units}"/>
											   <c:set var="totalactualunits" value="${totalactualunits + event.actual_units}"/>
											   <c:set var="totalcharge" value="${totalcharge + event.units_tobecharge}"/>	
										 </c:forEach>		
										 <tr>
										 <th colspan="4" style="text-align: center;">Total</th>
										 <td><b>${totalunits}</b></td>
										 <td><b>${totalactualunits}</b></td>
										 <td><b>${totalcharge}</b></td>
										 </tr>						 									 
									</tbody>
									</table>
							</c:if>
				       </div>  
    	<div id="stack1" class="modal fade" data-width="500" data-backdrop="static" data-keyboard="false">
					<div class="modal-dialog">
					    <div class="modal-content">
					       <div class="modal-header">
							 <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							 <h4 class="modal-title">Assessment Report</h4>
						  </div>
					      <div class="modal-body">
					          <div class="row">
					              <div class="col-md-12">
					           		 <form action="./assesmentReportDetails">
								 <table>
										<tr><td>Reading Month<font color="red">*</font></td>
										 <td><select name="billmonth" class="form-control" id="billmonthID">
										     <option value="0">select</option> 
										      <c:forEach items="${billmonthVal}" var="element">
											  <option value="${element}">${element}</option>
											  </c:forEach>	
										     </select></td>
										</tr>
										
									     <tr>
										   <td>Circle<font color="red">*</font></td>
						                    <td><select  name="circle" class="form-control" id="circle">
										     <option value="0">select</option> 
										      <c:forEach items="${circleVal}" var="element">
											  <option value="${element}">${element}</option>
											  </c:forEach>	
										     </select></td> 
										</tr>
										
										<tr>
										   <td>Category<font color="red">*</font></td>
						                    <td><select name="category" class="form-control" id="category">
										     <option value="0">select</option> 
										      <c:forEach items="${categoryval}" var="element">
											  <option value="${element}">${element}</option>
											  </c:forEach>	
										    </select></td> 
										</tr>
										
										<tr>
										   <td>Tamper Type<font color="red">*</font></td>
						                    <td><select  name="tamperType" class="form-control" id="tamperType">
										     <option value="0">select</option> 
										      <c:forEach items="${tamperTypeVal}" var="element">
											  <option value="${element}">${element}</option>
											  </c:forEach>	
										   </select></td> 
										</tr>
									</table> 					
												
							             <div align="right">	
							                 <button  class="btn blue "  onclick="return Validation();">View</button>												 
											 <button type="button" data-dismiss="modal" class="btn ">Close</button>
										</div>
									</form> 
					              </div>
					           </div>
					       </div>
					   </div>
					 </div>
				</div>
					  
					