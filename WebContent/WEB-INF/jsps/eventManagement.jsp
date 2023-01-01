 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	      App.init();
   	    	   //  TableEditable.init();
		    	 Tasks.initDashboardWidget();
		    	 FormComponents.init();
		    //	 TableManaged.init();
		    	
		    	 
		    	 $("#eventFromDate").val('${eventFromDate}');
		    	 $("#eventToDate").val('${eventToDate}');
		    		$('#MDMSideBarContents,#event-Management').addClass('start active ,selected');
	   	    	     $("#MDASSideBarContents,#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
	   	    	  summary();
	   	    	  loadSearchAndFilter('sample_3');
   	    	   });
   	    	   
  
  function validation()
  {
	  if(document.getElementById("eventFromDate").value == "" || document.getElementById("eventFromDate").value == null)
		  {
		  	bootbox.alert("Please Select From Date...");
		  	return false;
		  }
	  
	  if(document.getElementById("eventToDate").value == "" || document.getElementById("eventToDate").value == null)
	  {
	  	bootbox.alert("Please Select To Date...");
	  	return false;
	  }
	  
	  if(document.getElementById("event").value == "0" && document.getElementById("eventcat").value=="0")
	  {
	  	bootbox.alert("Please Select Event or Please Select Category ");
	  	return false;
	  }
	 
	  return true;
  }
  function catfun(){
	  
	  $("#eventrid").hide();
	  $("#orid").hide();
  }
  function eventfun(){
	  $("#orid").hide();
	  $("#cat").hide();
	 
  }
  function refresh(){
	  $("#eventrid").show();
	  $("#orid").show();
	  $("#cat").show();
  }
  
  function summary(){
	  
	  var eventDate= $("#eventDate").val();
	  if(eventDate==''){
		  
		  eventDate=moment(new Date()).format("DD-MM-YYYY");
	  }
	 
	  $.ajax({
		  url:'./eventSummary/'+eventDate,
		  type:'GET',
		  success:function(response){
			 var  hhtml="<th>Date</th>";
			 var  bhtml='<td style="text-align: left;">'+eventDate+'</td>';
			 $.each(response, function( index, value ) {
				 $.each(value, function( inde, valu ) {
					 if(inde==0){
						hhtml+='<th >'+valu+'</th>'; 
					 }
					 else{
						 bhtml+='<td style="text-align: left;">'+valu+'</td>'; 
					 }
					});
				});
			 $("#shid").html(hhtml);
			 $("#sbid").html(bhtml);
		  }
	  });
	 }
  
  </script>
  
  <div class="page-content">
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Event Summary</div>
							
						</div>					
											
						<div class="portlet-body">
							
						
						
							<table>
							<tr>
							<td>
							Select Date : 
							</td>
							<td>
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="eventDate" id="eventDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							
						<td>
							<div >
							 <button class="btn blue pull-left" onclick="summary()">Submit</button>  
							
							</div>
							</td>
							</tr>
							
							
							</table>
							
							<div class="modal-footer">
							<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead >
										<tr id="shid" >
											
										</tr>
									</thead>
									<tbody>
										<tr id="sbid">
											</tr>
										
									</tbody>
								</table>
							</div>
						</div>
							
							</div>
							
				
							
											
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
  
 
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Event Management</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" onclick="refresh()" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="./eventManagementDetails">
						
							<table>
							<tr>
							<td>
							From Date : 
							</td>
							<td>
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="eventFromDate" id="eventFromDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							</tr>
							<tr>
							<td>
							To Date :
							</td>
							<td> 
							<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control" name="eventToDate" id="eventToDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							</tr>
							<tr id="eventrid">
							
							
							<td>Select Event : 
							</td>
							<td>
							<select  id="event" onclick="eventfun()" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="event" >
							<option value="0">select</option>
							
							<c:forEach var="element" items="${events}">
							<option value="${element.eventCode}">${element.eventDescription}</option>
							</c:forEach>
							</select>
							</td>
							</tr>
							<tr>
							<tr id="orid"><td colspan="2">(<span style="font-weight: bold;">OR</span>)</td></tr>
							<tr id="cat">
							<td>  Select Category : 
							</td>
							<td>
							<select  id="eventcat" onclick="catfun()" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="category" >
							<option value="0">select</option>
							<%-- <c:set var="count" count="0" scope="page" /> --%>
							<c:forEach var="elementcat" items="${catlist}">
							<option value="${elementcat}">${elementcat}</option>
							<%-- <c:set var="count" value="${count + 1}" scope="page"/> --%>
							</c:forEach>
							</select>
							</td>
							</tr>
							
							
							
							</table>
							<div class="modal-footer">
							 <button class="btn blue pull-left" onclick="return validation()">Submit</button>  
							
							</div>
							</form>	
							
				<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_3', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
							<c:if test="${not empty eventLength }">
							
								<BR><BR>
								<!-- <div class="table-scrollable">	 -->
								<table class="table table-striped table-bordered table-hover" id="sample_3" >
									<thead>		
									<tr>								
											    <th colspan="19" style="text-align: center;background-color: graytext;">${event_name}</th>
											    
												
										</tr>							
										<tr>								
											    <th>Meter No</th>
											    <th>Account No</th>
											    <th>Name</th>
												<th>Event Code</th>
												<th>Division</th>
												<th>Circle</th>
												<th>Event Desc</th>
												<th>Event Time</th>
												<th>RPhase </th>
												<th>YPhase </th>
												<th>BPhase </th>
												<th>RPhase Line</th>
												<th>YPhase Line</th>
												<th>BPhase Line</th>
												<th>RPhase PF</th>
												<th>YPhase PF</th>
												<th>BPhase PF</th>
												<th>CD</th>
												<th>KWH</th>
												
										</tr>
									</thead>
									<tbody>
																		
									<c:forEach var="event" items='${eventDetails}'>
										 	<tr id="sampel" class="odd gradeX">	
										 		<td>${event[0]}</td>
											    <td>${event[1]}</td>
											    <td>${event[2]}</td>
											    <td>${event[3]}</td>
											    <td>${event[4]}</td>
											    <td>${event[5]}</td>							 		
											    <td>${event[6]}</td>
											    <td>${event[7]}</td>
											    <td>${event[8]}</td>
											    <td>${event[9]}</td>
											    <td>${event[10]}</td>
											    <td>${event[11]}</td>
											    <td>${event[12]}</td>
											    <td>${event[13]}</td>
											    <td>${event[14]}</td>
											    <td>${event[15]}</td>
											    <td>${event[16]}</td>
											    <td>${event[17]}</td>
											    <td>${event[18]}</td>
											   </tr>
										 </c:forEach>								 									 
												
									</tbody>
								</table>	
							<!-- 	</div> -->
								</c:if>	
								<c:if test="${not empty eventLength1 }">
								<font color="red">Data Not Found</font>
								</c:if>				
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
  
  </div>