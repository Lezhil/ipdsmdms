<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		$("#3gCellData").hide();
		$("#otherHes").hide();
		$("#fromdateTS").hide();
		$("#nameFromDate").hide();
	    $("#toDateTS").hide();
		$("#nameToDate").hide();
		
		
		
		 App.init();
		TableManaged.init();
		FormComponents.init(); 			
		$('#MDASSideBarContents,#ondemand,#meterOper').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
		//loadSearchAndFilter('sample_3');
		//ondemand();
		transdata();
	});
  

</script>

<div class="page-content" >
		<div id="taid">
				</div>
					<div class="row">
							<div class="col-md-12">
				
			
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>On Demand Reading</div>
								<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
							
							<%-- <form > --%>
							<table>
							
							<tr>
							
							<td  style="width: 100px;">Request Type : 
							</td>
							
							<th>
								<div class="radio-inline" id = "reportPeriod">
	                			 <label><input type="radio" name="reportType" value= "singleFeederDT"  checked="checked" onchange="handleClick(this.value);">Meter Reading request</label>
	            	           	</div>
	                       	
		                       	<div class="radio-inline">
		                			 <label><input type="radio" name="reportType" value= "multipleFeederDT"  onchange="handleClick(this.value);" >Request History</label>
		                       	</div>
							</th>	
						
							</tr>
							<tr>
							<td  style="width: 150px;">Location Type: 
							</td>
							<td>
							<input class="form-control input-large" placeholder="Select Location Type" type="text" name="meterNum" autocomplete="off" id="meterNum" onchange="return getHes(this.value);"   ="width: 320px;">
							</td>
							<td>Location Identity: 
							</td>
							<td>
							<input class="form-control input-large"  placeholder="Location Identity" type="text" name="hesType" autocomplete="off" id="hesType" style="width: 320px;">
							</td>
							</tr>
							
							<tr>
							<td  style="width: 150px;">Meter Sr.Number: 
							</td>
							<td>
							<input class="form-control input-large" placeholder="Meter Number" type="text" name="hesType" autocomplete="off" id="hesType" style="width: 320px;">
							</td>
							
							<td>
							<button class="btn blue pull-left" onclick="ondemand()">View</button>  
							</td>
							</tr>
								
							</table>	
							<br>
							
							 <div class="portlet box blue">
							<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Location History 
						 
							</div></div>
							<div class="portlet-body" id="excelUpload">
								<table class="table table-striped table-bordered table-hover" id="sample_3" >
										<thead>									
											<tr>								
												    <th>Sub_division Name</th>
												    <th>Sub_Division Code</th>
												    <th>Location Type </th>
													<th>Location Identity </th>
													<th>Location Name</th>
													<th>Meter Sr.Number</th>
											</tr>
										</thead>
									<!-- 	<tbody id="">
													<td></td>
													<td></td>
												    <td></td>
												    <td></td>
												    <td></td>
												    <td></td>
										</tbody> -->
									</table>	
							</div>
							 
							
							 </div>
							 
							 
							 
							 
							 <div class="portlet box blue">
					<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Meter data read request:
						 
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
						<table>
						<tr>		
						
							
							<th>
								<div class="radio-inline" id = "reportPeriod">
	                			 <label><input type="radio" name="reportType" value= "singleFeederDT"  checked="checked" onchange="handleClick(this.value);">Instantaneous Data</label>
	            	           	</div>
	                       	
		                       	<div class="radio-inline" style="width: 150px;">
		                			 <label><input type="radio" name="reportType" value= "multipleFeederDT"  onchange="handleClick(this.value);" >Complete Data</label>
		                       	</div>
							</th>	
							<th>
							
							<button class="btn blue pull-left" onclick="ondemand()">Read Data</button>  
							
						
							</th>
							</tr>
							<tr>
							<th>
							</th>
						
						</tr>
						
						</table>
							</div>
					</div>	
					
						 <div class="portlet box blue">
					<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Request Status
						 
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
						<table>
							</table>
								<table class="table table-striped table-bordered table-hover" id="sample_3"  border="5">
									<thead>		
										<tr>								
											    <th>Request Id</th>
											    <th>Request Type</th>
											    <th>Request Status</th>
												<th>Exception</th>
												<th>Action</th>
												
										</tr>
									</thead>
									<tbody id="">
									<td></td>
									<td></td>
								    <td></td>
								    <td></td> 
								    <td>	<button class="btn blue pull-left" onclick="ondemand()">View Data</button>  </td>
								   
									</tbody>
								</table>
							</div>
					</div>
					
						
				
							
							<div style="overflow-x:auto;">
					<table class="table table-striped table-bordered table-hover table-condensed" id="sample_4" style="table-layout: auto;" >
									<thead id="thid">									
										
									</thead>
									<tbody id="tbid">
																 									 
												
									</tbody>
								</table>	
								</div>
					</div>
				</div>
			</div>
		</div>
	
	</div>
	
	
	
	<style>
	
	
	
	#sample_4 th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: center;
    background-color: #4b8df8;
    color: white;
     overflow:hidden;
    white-space: nowrap;
}
#sample_4 td {
  text-align: center;
     overflow:hidden;
    white-space: nowrap;
}
	.tdcls1
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
	.tdcls2
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	
		.tdcls3
	{
 padding-bottom:15px;
	width:10%;
	text-align: right;
	font-weight:bold;
	}
	
		.tdcls4
	{
 padding-bottom:15px;
	width:40%;
	/* text-align: left; */
	text-align: justify;
	}
	
	</style>	
			
			
			