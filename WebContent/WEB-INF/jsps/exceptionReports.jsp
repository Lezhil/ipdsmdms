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
		    	
		    	 
		    	
		    		$('#MDMSideBarContents,#exceptionManagementConfig,#emcReports').addClass('start active ,selected');
	   	    	     $("#MDASSideBarContents,#admin-location,#dash-board,#MIS-Reports").removeClass('start active ,selected');
	   	    	  rtcreport();
	   	    	  loadSearchAndFilter1('sample_2');  
	   	    	loadSearchAndFilter1('sample_3');  
	   	    	loadSearchAndFilter1('sample_4');  
	   	    	loadSearchAndFilter1('sample_5'); 
	   	    	loadSearchAndFilter1('sample_6'); 
	   	    	  
   	    	   });
   	   
  

 
  </script>
 <script>
 function rtcreport(){
	 var html="";
	 $.ajax({
		url:'./rtcreport' ,
		type:'GET',
		success:function(res){
			$.each(res,function(i,v){
				html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+moment(v[1]).format('YYYY-MM-DD HH:mm:SS')+"</td><td>"+moment(v[2]).format('YYYY-MM-DD HH:mm:SS')+"</td></tr>";
				
			});
			$("#rtcID").html(html);
		}
	 });
	 
	 loadSearchAndFilter1('sample_7'); 
 }
 </script>
  
  <div class="page-content">
<div class="row">
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user"></i>Load</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_2', 'load intervals missing')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Interval Count</th>
									</tr>
								</thead>
								<tbody>
								<c:set value="0" var="seq" scope="page"/>
								<c:forEach var="retdata" items="${load}">
								<tr class="odd gradeX">
										<td>${seq+1}</td>
										<td>${retdata[0]}</td>
										<td >${retdata[1]}</td>
										
									</tr>
								<c:set var="seq" value="${seq + 1}" scope="page"/>	
								</c:forEach>
									
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Daily Load</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_3', 'daily load records missing')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_3">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Circle</th>
										<th>Division</th>
										<th>Sub Division </th>
										<th>Customer Name</th>
										<th>Customer Address</th>
									</tr>
								</thead>
								<tbody>
								<c:set value="0" var="seq" scope="page"/>
								<c:forEach var="retdata" items="${dload}">
								<tr class="odd gradeX">
										<td>${seq+1}</td>
										<td>${retdata[0]}</td>
										<td >${retdata[1]}</td>
										<td>${retdata[2]}</td>
										<td >${retdata[3]}</td>
										<td>${retdata[4]}</td>
										<td >${retdata[5]}</td>
									</tr>
								<c:set var="seq" value="${seq + 1}" scope="page"/>	
								</c:forEach>
									
									
								</tbody>
								</table>
						</div>
					</div>
					
				</div>
			</div>
  
 <div class="row">
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user"></i>Events</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_4', 'major event list')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_4">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Event Code</th>
										<th>Event</th>
										
									</tr>
								</thead>
								<tbody>
								<c:set value="0" var="seq" scope="page"/>
								<c:forEach var="retdata" items="${eventsl}">
								<tr class="odd gradeX">
										<td>${seq+1}</td>
										<td>${retdata[0]}</td>
										<td >${retdata[1]}</td>
										<td>${retdata[2]}</td>
										
									</tr>
								<c:set var="seq" value="${seq + 1}" scope="page"/>	
								</c:forEach>
									
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Bill History</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_5', 'bill record missing list')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_5">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Circle</th>
										<th>Division</th>
										<th>Sub Division </th>
										<th>Customer Name</th>
										<th>Customer Address</th>
										
									</tr>
								</thead>
								<tbody>
								<c:set value="0" var="seq" scope="page"/>
								<c:forEach var="retdata" items="${bh}">
								<tr class="odd gradeX">
										<td>${seq+1}</td>
										<td>${retdata[0]}</td>
										<td >${retdata[1]}</td>
										<td>${retdata[2]}</td>
										<td>${retdata[3]}</td>
										<td >${retdata[4]}</td>
										<td>${retdata[5]}</td>
										
									</tr>
								<c:set var="seq" value="${seq + 1}" scope="page"/>	
								</c:forEach>
									
									
								</tbody>
							</table>
						</div>
					</div>
					
				</div>
			</div>
  
  <div class="row">
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user"></i>Power Outage</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_6', 'major event list')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_6">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Power On Duration</th>
										<th>Power Off Duration</th>
										
									</tr>
								</thead>
								<tbody>
								<c:set value="0" var="seq" scope="page"/>
								<c:forEach var="retdata" items="${pd}">
								<tr class="odd gradeX">
										<td>${seq+1}</td>
										<td>${retdata[0]}</td>
										<td >${retdata[1]}</td>
										<td>${retdata[2]}</td>
										
									</tr>
								<c:set var="seq" value="${seq + 1}" scope="page"/>	
								</c:forEach>
									
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
				<div class="col-md-6 col-sm-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>RTC Check</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_7', 'bill record missing list')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-bordered table-hover" id="sample_7">
								<thead>
									<tr>
									<th>S.no</th>
										<th >Meter Number</th>
										<th >Read Time</th>
										<th>Server Time</th>
										
										
									</tr>
								</thead>
								<tbody id="rtcID">
								
									
									
								</tbody>
							</table>
						</div>
					</div>
					
				</div> 
			</div>
  
  
  </div>