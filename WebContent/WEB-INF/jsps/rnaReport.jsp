<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <script  type="text/javascript">
  $(".page-content").ready(function()
   {
	  $('#MDASSideBarContents,#other-Reports,#rnaReport').addClass('start active ,selected');
	  $('#other-Reports').addClass('start active ,selected');		  
	    $("#datedata").val(getPresentMonthDate('${selectedMonth}'));
	    
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
	
	<div class="row">
		<form action="./generaternaReport" method="post">
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
				<td><select class="form-control" name="circle" id="circle">
						<option value="0">Select Circle</option> 
						<c:forEach items="${circle}" var="element">
						<option value="${element}">${element}</option>
						</c:forEach>	
				</select></td> 
				
				<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" id="dataview" class="btn green">View Data</button></td>
								
			</tr>
		</table>
		</form>
	</div>
	<br/>
	
	<div class="row" id="dashData">
	<div class="col-md-12">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>RNA Report</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
				<div class="portlet-body">
				 <div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="cmd" onclick="$('#sample1').tableExport({fileName:'RNA Report',type:'pdf',tableId:'sample1'})">Export to pdf</a></li>
								<li><a href="#" id="excelExport1" onclick="tableToExcel('sample1', 'RNA Report')">Export to Excel</a></li>
							</ul>
						</div>
							<div class="table-scrollable">
								<table class="table table-striped table-hover table-bordered" id="sample1">
									<thead>
										<tr>
											<th>Circle</th>
											<th>SdoCode</th>
											<th>SdoName</th>
											<th>MRName</th>
											<th>Count</th>
										</tr>
									</thead>
									
									<tbody>
								
									<c:forEach items="${dashDetails}" var="element">
									   
									     <tr>
											<td>${element[0]}</td>
											<td>${element[1]}</td>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td><a href="./generaternaReport?circle=${element[0]}&sdocode=${element[1]}&sdoname=${element[2]}&mrname=${element[3]}&month=${element[5]}" id="dsasad${element[2]}">${element[4]}</a></td>
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
						<i class="fa fa-edit"></i>MR Details : &nbsp;&nbsp;<span style="background-color: #fcb322; color: black"> ${mrname}</span> 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body"  style="overflow: auto; height: 350px;">
					<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_2', 'MRwise RNA Details')">Export to Excel</a></li>
							</ul>
						</div>
					<div class="table-scrollable">
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr style="background-color: lightgray;text-align: center;">
								<th>Sl No.</th>
								<th>CIRCLE</th>
								<th>SDOCODE</th>
								<th>SDONAME</th>
								<th>Tariff Desc</th>
								<th>ACCNO</th>
								<th>METRNO</th>
								<th>READINGREMARK</th>
								<th>MRNAME</th>
								<th>UNITSKWH</th>
								<th>UNITSKVAH</th>
								<th>READINGDATE</th>
								<th>NAME</th>
								<th>ADDRESS</th> 
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="1" scope="page"/>
							<c:forEach items="${sectionDash}" var="element">
							     <tr>
									<td>${count}</td>
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
				
