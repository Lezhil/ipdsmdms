  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="resources/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.js" type="text/javascript"></script>
<link href="resources/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>


<style>
<!--

-->
</style>


<style>
/* #mrDiv {
    overflow-y: hidden;
     overflow-x: show;
} */
.lds-hourglass {
  display: inline-block;
  position: relative;
  width: 64px;
  height: 64px;
}
.lds-hourglass:after {
  content: " ";
  display: block;
  border-radius: 50%;
  width: 0;
  height: 0;
  margin: 6px;
  box-sizing: border-box;
  border: 26px solid #fff;
  border-color: #1a2994  transparent #2dd632  transparent;
  animation: lds-hourglass 1.2s infinite;
}
@keyframes lds-hourglass {
  0% {
    transform: rotate(0);
    animation-timing-function: cubic-bezier(0.55, 0.055, 0.675, 0.19);
  }
  50% {
    transform: rotate(900deg);
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }
  100% {
    transform: rotate(1800deg);
  }
}

</style>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {
   //onload=noBack();
	  $("#datedata").val('${month}');
	 // $("#todayId").val('${todayDate}');
	 
   	       App.init();
   	    	TableManaged.init();
   	    	FormComponents.init();
 		   Index.initMiniCharts();

   	    	  /* Index.init();
   		   Index.initJQVMAP(); // init index page's custom scripts
   		   Index.initCalendar(); // init index page's custom scripts
   		   Index.initCharts(); // init index page's custom scripts
   		   Index.initChat();
   		   Index.initMiniCharts();
   		   Index.initDashboardDaterange();
   		   Tasks.initDashboardWidget();
   		   FormComponents.init();
   		   Charts.init();
		   Charts.initCharts();
		   Charts.initPieCharts() */
		   $('#MDMSideBarContents,#reportsId,#getBillingDataSyncDetails').addClass('start active ,selected');
   	    	 $("#MDASSideBarContents,#360d-view,cumulative-Analysis,cmri-Manager,seal-Manager,cdf-Import,system-Security,meter-Observations,interval-DataAnalyzer,events-Analyzer,exceptions-Analyzer,Load-SurveyAnalyzer,instantaneous-Parameters,VEE-RulesEngine,Assessment-Reports,MIS-Reports,BillSyncId").removeClass('start active ,selected');
   	    	   });
  
 
  /*  
  function disableBackButton()
  {
  window.history.forward()
  } 
  disableBackButton()
  window.onload=disableBackButton(); 
  window.onpageshow=function(evt) 
  { if(evt.persisted) 
	  disableBackButton() 
  } 
  window.onunload=function() { void(0) }  
    */
   /*  var rescount=1;

       $( document ).ready(function() {
    	var totalmtrs='${total}';
  	  $.ajax({
  		 url:"./getSyncDataFromRMS",
  	     type:"GET",
  	     dataType:"text",
  			cache: true,
  			success : function(response) {
  				
				if(response=='null')
					{
					response=0;
					rescount=1;
					}
				else
					{
					rescount=1;
					}
  				
  				$("#syncId").html(response);
  				var unsych=parseInt(totalmtrs)-parseInt(response);
  				$("#unsychid").html(unsych);
  	  			}
  		  
  	  });
    });   */ 
  </script>
  
    
<div class="page-content" >
	<!-- BEGIN DASHBOARD STATS -->
<div class="row">
	
	<form action="./getMonthDashboardDataBillData" method="post">
	 <div class="col-lg-3 col-md-2 col-sm-6 col-xs-12">
	    <span class="help-block" hidden="true"></span>	
	</div> 
	
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
	<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
									<input type="text" name="month" id="datedata" readonly="readonly" class="form-control" required="required">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
								</div>
							</div>
							<div>	<input type="text" hidden="true"  name="value" id="value"  value = "zone"></div>
							<div>	<input type="text" hidden="true" name="type" id="type"  value="dashboard"></div>
							
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<span>
		<button type="submit" id="dataview" class="btn green">View Data</button>	</span>																					
								<br>
		</div>
		</form>
	</div>
	<br/>
			<%-- <div class="row">
			
			 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="fa fa-upload"></i>
						</div>
						<div class="details">
							<div class="number">
								<span >${total}</span>
							</div>
							<div class="desc">                           
								Total Meters 
							</div>
						</div>
						<!-- <a class="more" href="#">
						Details <i class="m-icon-swapright m-icon-white"></i>
						</a>    -->              
					</div>
				</div>
				
				 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat purple">
						<div class="visual">
							<i class="fa fa-refresh"></i>
						</div>
						<div class="details">
							<div class="number" id="totalActiveId">
								<span id="syncId"> ${resultSyncCount}</span>
							</div>
							<div class="desc" >                           
								uploaded to RMS
							</div>
						</div>
						<!-- <a class="more" href="#">
						Details <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                 
					</div>
				</div>
			 	
				<div class="col-lg-3 col-md-3 col-sm-6col-xs-12">
					<div class="dashboard-stat yellow">
						<div class="visual">
							<i class="fa fa-ban"></i>
						</div>
						<div class="details">
							<div class="number">
					         <span id="unsychid">${total-resultSyncCount}</span>
							</div>
							<div class="desc">                           
								Uploaded pending to RMS
							</div>
						</div>
						<!-- <a class="more" href="#">
						Details <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                 
					</div>
				</div>
				</div> --%>
				
				
				<!-- new dashboard -->
				<div class="row ">
				<div class="col-md-12">
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-calendar"></i>RMS DASHBOARD</div>
							<!-- <div class="actions">
								<a href="javascript:;" class="btn btn-sm yellow easy-pie-chart-reload"><i class="fa fa-repeat"></i> Reload</a>
							</div> -->
						</div> 
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number total" data-percent="${totalMtrs}"><span>${totalMtrs}</span>%</div>
										<a class="title" >Total Meters &nbsp;&nbsp;&nbsp;<span  style="color: orange;font-weight: bold;">${total}</span> </a>
									</div>
								</div>
								<div class="margin-bottom-10 visible-sm"></div>
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number upload" data-percent="${totalRMSCountPers}"><span>${totalRMSCountPers}</span>%</div>
										<a class="title" >Uploaded to RMS &nbsp;&nbsp;&nbsp;<span id="syncId"  style="color: green;font-weight: bold;" > ${resultSyncCount}</span></a>
									</div>
								</div>
								<div class="margin-bottom-10 visible-sm"></div>
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number pending" data-percent="${PendingRMSPers}"><span>${PendingRMSPers}</span>%</div>
										<a class="title" >Uploaded pending to RMS  &nbsp;&nbsp;&nbsp;<span id="unsychid" style="color: red;font-weight: bold;">${total-resultSyncCount}</span></a>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
				</div>
				
				
				<!--  -->
				
				 <div class="row"> 
				<div class="portlet box blue">
			       
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Data Synchronization Report 
						 	   <a href="#" id="excelExport" class="btn green" style="margin-left: 50px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
                            </div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>SLNO</th>
										<th>RDNGMONTH</th>
										<th>METRNO</th>
										<th>ACCNO</th>
										<th>NAME</th>
										<th>CIRCLE</th>
										<th>DIVISION</th>
										<th>SUBDIV</th>
										<th>KWH VALUE</th>
										<!-- <th>REMARK</th> -->
									</tr>
								</thead>
								
								<tbody>
								
							 <c:if test="${resultSyncCount ne '0'}"> 
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${SycData}">
								<tr>
									<td>${count}</td>
									<td>${element.BILLMONTH}</td>
									<td>${element.METERNO}</td>
									<td>${element.ACCNO}</td>
									<td>${element.NAME}</td>
									<td>${element.CIRCLE}</td>
									<td>${element.DIVISION}</td>
									<td>${element.SDONAME}</td>
									<td>${element.KWH}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								 </c:if> 
								</tbody>
								
							</table>
						</div>
						
					</div>
			</div> 
</div>

<script>

$(function () {

    $('.total').easyPieChart({
        barColor: '#e8b514',
        scaleColor: false,
        lineWidth: 10,
        trackWidth: 8,
        animate: false,
        lineCap: 'square',
        size: 110,
         trackColor: '#f2f2f2',
        // The color of the scale lines, false to disable rendering.
        scaleColor: '#6d6a23',
        // Defines how the ending of the bar line looks like. Possible values are: butt, round and square.
    });
    
    $('.upload').easyPieChart({
        barColor: '#2ed33b',
        scaleColor: false,
        lineWidth: 10,
        trackWidth: 8,
        animate: false,
        lineCap: 'square',
        size: 110,
         trackColor: '#f2f2f2',
        // The color of the scale lines, false to disable rendering.
        scaleColor: '#6d6a23',
        // Defines how the ending of the bar line looks like. Possible values are: butt, round and square.
    });
    
    
    $('.pending').easyPieChart({
        barColor: '#f6190b',
        scaleColor: false,
        lineWidth: 10,
        trackWidth: 6,
        animate: false,
        lineCap: 'square',
        size: 110,
         trackColor: '#f2f2f2',
        // The color of the scale lines, false to disable rendering.
        scaleColor: '#6d6a23',
        // Defines how the ending of the bar line looks like. Possible values are: butt, round and square.
    });
    
});

</script>