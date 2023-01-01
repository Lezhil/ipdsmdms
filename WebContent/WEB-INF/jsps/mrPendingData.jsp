<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <script  type="text/javascript">
  $(".page-content").ready(function()
   {
			 
	   /*  $("#datedata").val(getPresentMonthDate('${selectedMonth}')); */
	  
			App.init();
   	    	TableManaged.init();
   	    	TableEditable.init();
   	    	FormComponents.init();

   	    	$('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	   	    if(selectedItem==null)
	   	    {
	   	    	$("#circleId").val("0").trigger("change"); 	 
	   	    }else{
	   	    	$("#circleId").val(selectedItem).trigger("change");
	   	    }
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
	
	<div class="row" id="dashId">
	<div class="col-md-12">
	 <div class="portlet box blue">
	
	<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>MRWise Pending
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
				

			<div class="portlet-body">
							
					<form:form action=""  method="post" id="mrPendingformId">
						<div class="form-body">
						     <div class="form-group">

							<table style="width: 53%">
								<tbody>
									<tr>
										<td>MONTH:</td>
										<td><div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
									<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required" value="${rdngMonthPending}">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
								         </div></td>
								
										<td>CIRCLE</td>
										<td><select class="form-control select2me input-medium"
											name="circleId" id="circleId">
												<option value="0">Select Circle</option>
												<c:forEach items="${circlePending}" var="element">
													<option value="${element}">${element}</option>
												</c:forEach>
										</select></td>



										<td><input type="submit" id="dataview1" class="btn green" style="margin-left: 50px;"
											onclick="return mrWisePendingSearch();" value="View Data"></td>
											
									<td><button type="button" style="margin-left: 80px;" id="datavie" onclick="location.href = './newSealManager';" class="btn default">Previous</button></td>
									
									</tr>
							</table>
						</div>'
					</div>
					
						</form:form>
					
					
					</div>
					</div>
					</div>
				
	
	<br/>
	
	
		<c:if test = "${firstPendingTableError eq 'MR Wise Details Not Found..'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${firstPendingTableError}</span>
						</div>
			    </c:if>
			
<c:if test="${ firstPendingTableShow eq 'firstPendingTableShow' }">
			    
	<div class="row" id="dashData">
	<div class="col-md-12">
	<div class="portlet box blue">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
						
			    
					
				
			     
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>MR wise Details</div>
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
										    <th>Circle</th>
										 	<th>MR Name</th>
											<th>Count</th>
											
											  
										</tr>
									</thead>
									
									<tbody>
								
									<c:forEach items="${dashDetails}" var="element">
									   
									     <tr>
									         <td>${selectedCircle}</td>
											<td>${element[0]}</td>
											<td><a href="./mrpendingSecondTabdata?mrname=${element[0]}&month=${element[1]}&circle=${selectedCircle}" id="dsasad${element[2]}">${element[2]}</a></td>
										   
										</tr>
									</c:forEach>
									    
									</tbody>
								</table>
							</div>
						</div>
						</c:if>
						
			    	</div>
				</div>
				</div>
			
			<c:if test = "${secondPendingTableError eq 'MR Details Not Found'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${secondPendingTableError}</span>
						</div>
			    </c:if>
			    
			    <c:if test="${secondPendingTableShow eq 'secondPendingTableShow'}">
			<div id="sectDashDetails">	
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

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
						<form>
							<table style="width: 35%">
										<tbody>
										<tr>
										</tr>
										</tbody></table></form>
										<br/>
				
				</div>
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
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr style="background-color: lightgray; ">
								
								<th>SL NO</th>
								<th>Seal No</th>
								<th>MR Name</th>
								<th>Accno</th>
								<th>Meter No</th>
								<th>Remarks</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="1" scope="page" />
							<c:forEach items="${sectionDash}" var="element">
							     <tr>
									<td>${count}</td>
									<td>${element[1]}</td>
									<td>${element[4]}</td>
									<td>${element[14]}</td>
									<td>${element[16]}</td>
									<td>${element[17]}</td>
								</tr>
							<c:set var="count" value="${count + 1}" scope="page"/>		
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</div>
			</c:if>
			
			</div>
			</div>
				
			 	 

<script>

	function mrWisePendingSearch() {
		var circle = $("#circleId").val();
		if (circle == '0') {
			bootbox.alert("Please Select circle");
		} else {
			$("#mrPendingformId").attr("action", "./mrpendingdata");
		}
	}
</script>