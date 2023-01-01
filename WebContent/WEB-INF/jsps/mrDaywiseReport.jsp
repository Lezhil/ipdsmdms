<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Date"%>
<style>
#masterTd
{
   color: #004466;
   font-weight:500 ;
}
</style>

<script type="text/javascript">
var sectionName;
  	$(".page-content").ready(function()
  		   {    	
  		$('#fromDate').val('${cuurentDate}');
  		if('${circle}'!='')
  			{
  			$('#circle').val('${circle}')
  			}
  		
  		if('${mrDaywiseList.size()}'>0)
  			{
  			 $('#mrDayDiv').show();
  			}
  		 sectionName='${sectionname}';
  		    	App.init();
  		    	TableEditable.init(); 
  		      FormComponents.init();
  		    //UIExtendedModals.init();
  		   loadSearchAndFilter('table_3'); 
  		  $('#other-Reports').addClass('start active ,selected');
  		$("#feeder-reports,#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
  		   $('#circle').select2();
  		$("#print").click
		  (
	    	    	function()
	    	    	{ 
	    	    		printcontent($("#mrDiv").html());
				    }
	    	    	);
  		$("#print1").click
  	  (
    	    	function()
    	    	{ 
    	    		printcontent($("#printPending").html());
  			    }
    	    	);
  		   }); 	
  	
	   
	  
  	
  	function loadImage()
  	{
  		$('#mrDayDiv').hide();
  	   $("#imageee").show();
  	}
  	
  	
  	function showPendingList(mrname)
  	{
  		var date=$('#fromDate').val();
  		$.ajax({
	    	url:'./showPendingList'+'/'+mrname+'/'+date,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	beforeSend: function(){
		        $('#image').show();
		    },
		    complete: function(){
		        $('#image').hide();
		        $('#closeShow').show();
		        $('#closeShow1').show();
		        $('#excelExportDiv').show();
		    },
	    	success:function(response)
	    	{
	    		$('#mrnameTr').show();
	    		var html2 = "<thead><tr style='background-color: lightgray'><th>SLNO.</th><th>MRNAME</th><th >SDOCODE</th><th >ACCNO.</th><th >METERNO.</th></tr>";
	    		for(var j=0;j<response.length;j++)
	    		{		
	    		     html2 += "<tr><td>"+(j+1)+"</td>";
	    		     html2 += "<td>"+response[j][0]+"</td>";
	    		     html2 += "<td>"+response[j][1]+"</td>"; 
	    		     html2 += "<td>"+response[j][2]+"</td>";
	    		     html2 += "<td>"+response[j][3]+"</td></tr>";
	    		}
	    		$('#sample_4').html(html2);
	    	}
  		});
  	}
  	
  	function showAllPendingList()
  	{
  		var mrNameValue="";
  		var date=$('#fromDate').val();
  		$.ajax({
	    	url:'./showAllPendingList'+'/'+date,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	beforeSend: function(){
		        $('#image').show();
		        
		    },
		    complete: function(){
		        $('#image').hide();
		        $('#closeShow').show();
		        $('#closeShow1').show();
		        $('#excelExportDiv').show();
		    },
	    	success:function(response)
	    	{
	    		$('#mrnameTr').hide();
	    		var html2 = "<thead><tr style='background-color: lightgray'><th>SLNO.</th><th >SDOCODE</th><th >ACCNO.</th><th >METERNO.</th>";
	    		for(var j=0;j<response.length;j++)
	    		{		
	    			if(j==0)
	    				{
	    				html2+="<tr style='background-color: lightgray;' ><td><b>MR NAME</b></td><td><span ><b>"+response[j][0]+"</b></span></td></tr>";
	    				}
	    			if(j>0)
    				{
	    			if(response[j][0]!=mrNameValue)
	    				{
	    				html2+="<tr style='background-color:lightgray;' ><td><b>MR NAME</b></td><td><span ><b>"+response[j][0]+"</b></span></td></tr>";
	    				}
    				}
	    		     html2 += "<td>"+(j+1)+"</td>";
	    		     html2 += "<td>"+response[j][1]+"</td>"; 
	    		     html2 += "<td>"+response[j][2]+"</td>";
	    		     html2 += "<td>"+response[j][3]+"</td></tr>"; 
	    		     mrNameValue=response[j][0];
	    		}
	    		$('#sample_4').html(html2);
	    	}
  		});
  	}
  	
 	</script>
 	

<div class="page-content">



	<div class="portlet box blue">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>MR Daywise view</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
					
						 <div class="col-md-12">
										<form role="form" id="hhbmInfo" >
										<table >
										<tr >
														  <td>
										      <div class="input-icon">
															<i class="fa fa-calendar"></i> <input
																class="form-control date-picker input-medium"  type="text"
																value="" 
																data-date-format="yyyymm" data-date-viewmode="years" name="fromDate" id="fromDate" readonly="true" />
																
														</div>
													</td>
													
													
														  <td><div><button class="btn green pull-left" id="gpsViewButton" onclick="return loadImage();"  formaction="./showMrDaywiseReport" formmethod="post">view</button></div></td>
										</tr>
								
										</table>
										</form>
									
						</div>
						</div>
		</div>
		<c:if test="${results ne 'notDisplay'}">
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red;font-size:15px;">${results}</span>
					</div>
				</c:if>
				<div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
		<div id="mrDayDiv" class="portlet box green" style="display: none;">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>MR Daywise view</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
					<!-- <div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('table_3', 'MRDAYWISE_REPORT')">Export
										to Excel</a></li>
							</ul>
						</div> -->
						 <div class="col-md-16">
		 <table class="table table-striped table-hover table-bordered" id="table_3" border="1">
							<thead >
								<tr style="background-color: lightgray; ">
								<th>MRName</th>
								<th>TotalNOI</th>
								<c:forEach var="value"  items="${daysOfMonth}" >
   												<th style="background-color: lightgray; ">DAY-${value}</th>
									</c:forEach>
									<th>TotalEntered</th>
									<th>TotalPending</th>
								</tr>
								
							</thead>
							<tbody>
							<c:set var="count" value="0" scope="page" />
							<c:forEach var="element" items="${mrDaywiseList}">
							
							<tr > 
							<c:forEach var="i" begin="0" end="${arrayLength-1}">
							<c:choose>
								   <c:when test="${i<1}">
								          <td><span class="label label-warning" id='SpanHeader'><b ><font color="black">${element[i]}</font></b></span></td>
								   </c:when>
								   <c:when test="${element[i] ne '0' && i>=2 && i!=(arrayLength-2)&& i!=(arrayLength-1)}">
								          <td><span class="label label-primary" id='SpanHeader'><b >${element[i]}</b></span></td>
								   </c:when>
								    <c:when test="${element[i] ne '0' && i==1 && i!=(arrayLength-2)&& i!=(arrayLength-1)}">
								          <td><span class="label label-success" id='SpanHeader'><font color="white"><b >${element[i]}</b></font></span></td>
								   </c:when>
								   <c:when test="${i==(arrayLength-2)}">
								   <c:choose>
								   <c:when test="${element[i]ne '0'}">
								   <td><span class="label label-success" id='SpanHeader'><b ><font color="white">${element[i]}</font></b></span></td>
								   </c:when>
								   <c:otherwise>
								   <td>${element[i]}</td>
								   </c:otherwise>
								   </c:choose>
								   </c:when>
								   <c:when test="${i==(arrayLength-1)}">
								   <c:choose>
								   <c:when test="${element[i]ne '0'}">
								   <td><span class="label label-danger" id='SpanHeader'><b ><a href="#" style="color: white;" data-toggle='modal' data-target='#stack6' onclick="return showPendingList('${element[0]}');">${element[i]}</a></b></span></td>
								   </c:when>
								   <c:otherwise>
								   <td>${element[i]}</td>
								   </c:otherwise>
								   </c:choose>
								   </c:when>
								   <c:otherwise>
								   <td>${element[i]}</td>
								   </c:otherwise>
								</c:choose>
							
							
							</c:forEach>
							</tr>
								
								 <c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
							</tbody>
							<tr>
								<td><span class="label label-warning"><font color="black" size="2"><b>Total</b></font></span></td>
								<td><span class="label label-success" id='SpanHeader'><b ><font color="white" size="2">${totaNoi}</font></b></span></td>
								<c:forEach var="element" items="${totalDaySumList}">
								<td><span class="label label-success" id='SpanHeader'><b ><font color="white" size="2">${element[1]}</font></b></span></td>
								</c:forEach>
								<td><span class="label label-success" id='SpanHeader'><b ><font color="white" size="2">${totalEntered}</font></b></span></td>
								<c:choose>
								  <c:when test="${totalPending eq '0'}">
								  <td>${totalPending}</td>
								  </c:when>
								  <c:otherwise>
								  <td><span class="label label-danger" id='SpanHeader'><b ><a href="#" style="color: white" data-toggle='modal' data-target='#stack6' onclick="return showAllPendingList();">${totalPending}</a></b></span></td>
								  </c:otherwise>
								</c:choose>
								
								</tr>
		</table>
		</div>
		</div>
		</div>
	<!-- <div id="printPending" >
	<table class="table table-striped table-bordered table-hover" id="sample_4" border="1">
								<thead>
									<tr>
										<th>SlNo.</th>
										<th >ACCNO.</th>
										<th >SUBDIVISION</th>
										<th >SUBSTATION</th>
										<th >FEEDERNAME</th>
										<th >METERNO.</th>
									</tr>
								</thead>
								<tbody id="mobileTb">
									
									
								</tbody>
							</table>
							</div> -->
							<!-- <div class="portlet box grey" >
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Meter Reader Pending List</div>   
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
								<li><a href="#" id="print1">Print</a></li>
							</ul>
						</div>
						 <div class="col-md-16">
						 <div id="printPending">
		 <table class="table table-striped table-bordered table-hover" id="sample_4" border="1">
								<thead>
									<tr>
										<th>SlNo.</th>
										<th >ACCNO.</th>
										<th >SUBDIVISION</th>
										<th >SUBSTATION</th>
										<th >FEEDERNAME</th>
										<th >METERNO.</th>
									</tr>
								</thead>
								<tbody id="mobileTb">
									
									
								</tbody>
							</table>
		</div>
		</div>
		</div>
		</div> -->
		<div class="row">
			
			<div id="stack6" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 50%;" >
							
								<div class="modal-content">
								
									<div class="modal-header">
									<div id="image" hidden="true" style="text-align: center" >
									<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
                         <h3 id="loadingText"><font id="masterTd">Loading..... Please wait.</font> 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:4%;height: 4%;margin-right: 10px;">
						</div>
									<div id="closeShow" hidden="true">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title">PENDING INSTALLATIONS
										
										</h4>
										</div>
									</div>

									<div class="modal-body" id="closeShow1" style="display: none;">
									<div id="excelExportDiv"   hidden="true">
									<span id="SpanHeader72" class="label label-primary">
									<a href="#" id="excelExport1" 
									onclick="tableToExcel1('sample_4', 'PENDING INSTALLATIONS')"><font size="2" color="white"><b>EXPORT
										TO EXCEL</b></font></a></span>
										<span id="SpanHeader72" class="label label-primary">
									<a href="#" id="print"><font size="2" color="white"><b>PRINT
										</b></font></a></span>
										</div>
										<br>
										<div class="row">
											<div class="col-md-12">
											<div id="mrDiv">
											<table class="table table-striped table-hover table-bordered" id="sample_4" border="1" >
								<tbody >
									
									
								</tbody>
													</table>
													</div>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
						</div>
						<div class="row">
			
						</div>
	</div>
	




