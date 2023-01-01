  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
	  $("#datedata").val(getPresentMonthDate('${selectedMonth}'));
	 // $("#todayId").val('${todayDate}');
	 
   	       App.init();
   	    	TableManaged.init();
   	    	FormComponents.init();
   	    	
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
		   Charts.initPieCharts() */;
		   $('#MDMSideBarContents,#dashboard2').addClass('start active ,selected');
   	    	 $("#MDASSideBarContents,#360d-view,cumulative-Analysis,cmri-Manager,seal-Manager,cdf-Import,system-Security,meter-Observations,interval-DataAnalyzer,events-Analyzer,exceptions-Analyzer,Load-SurveyAnalyzer,instantaneous-Parameters,VEE-RulesEngine,Assessment-Reports,MIS-Reports").removeClass('start active ,selected');
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
  
  </script>
  
    <script type="text/javascript">
  $(window).load(function() {
		dashboardLazy();
	});
  function dashboardLazy(){
	  var dateVal=$("#datedata").val();
	  $.ajax({
		 url:"./dashboardLazy",
	     type:"GET",
	     data:{
	    	 "dateVal":dateVal
	     },
	     dataType:"json",
			timeout : 50000,
			cache: true,
			success : function(response) {
				var res=response[0];
				
				var html='';
				for(var i=0;i<res.length;i++){
					html+='<li class="list-group-item">'+res[i][0]+'<span class="badge badge-warning"><font size="2" color="black"><b>'+res[i][1]+'</b></font></span></li>';
				}
				$("#stId").html(html);
				$("#loaderId").hide();
			}
		  
	  });
	  
  }
  
  function viewCategorywiseData(sdoname)
  {
	 // alert("inside viewCategorywiseData");
		var month=$('#datedata').val();
	//alert(sdoname+"--"+month);
  
  	var arryLength=0;
  	
  	$.ajax({
	    	url:'./getAciveMeterData',
	    	type:'GET',
	    	dataType:'json',
	    	data:{sdoname:sdoname,month:month},
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
	    		var html2 = "<thead><tr style='background-color: lightgray'><th>SLNO.</th><th >METERNO</th><th >DIVISION</th><th >SUBDIVISION</th><th >ACCNO</th><th >CONS.Name</th><th >Status</th></tr></thead>";
	    		var html1="";
	    		for (var i = 0; i < response.length; i++) 
	        	{
	    			if(i==0)
	    				{
	    				arryLength=response[i].length;
	    				}
	    		}
	    		for (var i = 0; i < response.length; i++) 
	        	{
	    			html1="<tr>";
	    			html1 += "<td>"+(i+1)+"</td>";
	    			for (var j = 0; j < arryLength; j++) 
		        	{
		    			html1 += "<td>"+response[i][j]+"</td>";
		    		}
	    			html2+=html1+"</tr>";
	    		}
	    		$('#sample_4').html(html2);
	    		loadSearchAndFilter('sample_4');
	    	}
		    
	    });
  	return false;
  }
</script>
<div class="page-content" >
	<!-- BEGIN DASHBOARD STATS -->
	
	<div class="row">
	
	<form action="./getMonthDashboardData" method="post">
	<div class="col-lg-3 col-md-2 col-sm-6 col-xs-12">
	    <span class="help-block">Select Month</span>	
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
			<div class="row">
			 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="fa fa-group"></i>
						</div>
						<div class="details">
							<div class="number">
								${total}  
							</div>
							<div class="desc">                           
								Total Consumers
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
							<i class="fa fa-bolt"></i>
						</div>
						<div class="details">
							<div class="number">
								 ${totalActive}  
							</div>
							<div class="desc">                           
								Active Consumers
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
							<i class="fa fa-bolt"></i>
						</div>
						<div class="details">
							<div class="number">
								${total-totalActive}
							</div>
							<div class="desc">                           
								InActive Consumers
							</div>
						</div>
						<!-- <a class="more" href="#">
						Details <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                 
					</div>
				</div> 
				<div class="col-lg-3 col-md-3 col-sm-6col-xs-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="fa fa-bolt"></i>
						</div>
						<div class="details">
</br>							<div class="desc">
								Total meters : 4 &nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="desc">                           
								Connected : 3  &nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="desc">                           
								Disconnected : 1  &nbsp;&nbsp;&nbsp;&nbsp;
							</div>
  
						</div>
						<!-- <a class="more" href="#">
						Details <i class="m-icon-swapright m-icon-white"></i>
						</a> -->                 
					</div>
				</div> 
				
				
			</div>


		
		
	   
	    



	<div class="row">
				<div class="col-md-6">
					<div class="portlet box grey" style="width: 100%;">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-bell-o"></i>Installation Details &nbsp;
							<span class="label label-warning" id="SpanHeader72"><!-- <a onclick="return viewCategorywiseData();" data-target="#stack6" data-toggle="modal" id="deviceNotLiveId" href="#">
							<font size="2" color="#222222"><b>CATEGORYWSIE</b></font></a> --></span> </div>
						</div>
				<div class="portlet-body">
					<div class="scroller" style="height: 200px;" data-always-visible="1" data-rail-visible="0">
					<ul class="feeds">
							<li>
								<div class="col1">
									<div class="cont">
													 
						<div class="cont-col1">
						<div class="desc">	
						<div class="table-scrollable">					
						<table class="responsive table table-bordered" id="displayData">
									<thead>
										<tr style='background-color: lightgray'>
										  <th>SINo.</th> 
											<th>SubDivision</th>
											<th>TOTAL</th>
											<th>ACTIVE</th>
											<th>PENDING</th>
											
										</tr>
									</thead>
									<tbody id="displayValues">
									 <c:set var="count" value="0" scope="page" />
									 <c:forEach var="element" items="${installDetails}">
									 <tr>
								 	 <td>${count+1}</td>  
										 <c:forEach var="i" begin="0" end="${instArrayLength-1}">
										 <c:if test="${i>0}"><td>${element[i]}</td></c:if>
										 </c:forEach>
										</tr>
									 <c:set var="count" value="${count+1}" scope="page"/>
									 </c:forEach>
									</tbody>
									
								</table></div>
														</div>
													</div>
												</div>
											</div>
										
									</li>
									
									
								</ul>
						</div>
		            </div>
					</div>
				</div>	
				
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								
								<div style="background-color:#e2ab6c;" >
									<div class="panel-heading">
										Sensitive Tampers
									</div>
									
									<%-- <ul class="list-group">
									<c:forEach var="data" items="${d5DataList}">
										<li class="list-group-item">${data[0]}<span class="badge badge-success">${data[1]}</span></li>
										</c:forEach>
										<c:forEach var="data1" items="${defectiveList}">
										<li class="list-group-item">Meter Defective<span class="badge badge-success">${data1[1]}</span></li>
										</c:forEach> 
									</ul> --%>
									<div class="lds-hourglass" id="loaderId"><div>Loading....</div></div>
									<ul class="list-group" id="stId">
									</ul>
								</div>
						</div>
			</div>
			<!-- ************************************************ -->
			
			<div id="stack6" class="modal fade" tabindex="-1" data-width="400" data-backdrop="static" data-keyboard="false">
							<div class="modal-dialog" style="width: 90%;" >
							
								<div class="modal-content">
								
									<div class="modal-header">
									<div id="image" hidden="true" style="text-align: center;height: 100%;width: 100%;" >
									<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
                         <h3 id="loadingText"><font id="masterTd">Loading..... Please wait.</font> 
						 </h3> 
<!-- 						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:4%;height: 4%;margin-right: 10px;"> -->
						</div>
									<div id="closeShow" hidden="true">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title"><b>ACTIVE METER DETAILS</b>
										
										</h4>
										</div>
									</div>

									<div class="modal-body" id="closeShow1" style="display: none;">
										<br>
										<div class="row">
											<div class="col-md-12">
											<div id="mrDiv">
											<table class="table table-striped table-hover table-bordered" id="sample_4"  >
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
						
			<div class="clearfix"></div>
			<div class="row ">
				<div class="col-md-6">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Meter Make Provider</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>
									<tr>
										
										<th>Meter Make</th>
										<th>Total Consumer</th>
										<th >Percentage</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${not empty SECURECONSUMERCOUNT}">
									<tr >
										<td >SECURE</td>
										<td >${SECURECONSUMERCOUNT}</td>
										<td >${SECURE}</td>
									</tr></c:if>
									<c:if test="${not empty LNTCONSUMERCOUNT}">
									<tr >
										<td >LNT</td>
										<td >${LNTCONSUMERCOUNT}</td>
										<td >${LNT}</td>
									</tr></c:if>
									<c:if test="${not empty LNGCONSUMERCOUNT}">
									<tr >
										<td >LNG</td>
										<td >${LNGCONSUMERCOUNT}</td>
										<td >${LNG}</td>
									</tr></c:if>
									<c:if test="${not empty GENUSCONSUMERCOUNT}">
									<tr >
										<td >GENUS POWER INFRASTRUCTURES LTD</td>
										<td >${GENUSCONSUMERCOUNT}</td>
										<td >${GENUSC}</td>
									</tr></c:if>
									<c:if test="${not empty GENUSDCONSUMERCOUNT}">
									<tr >
										<td >GENUS DLMS</td>
										<td >${GENUSDCONSUMERCOUNT}</td>
										<td >${GENUSD}</td>
									</tr></c:if>
									<c:if test="${not empty HPLMCONSUMERCOUNT}">
									<tr >
										<td >HPL COMMON</td>
										<td >${HPLMCONSUMERCOUNT}</td>
										<td >${HPLM}</td>
									</tr></c:if>
									<c:if test="${not empty HPLDCONSUMERCOUNT}">
									<tr >
										
										<td >HPL DLMS</td>
										<td >${HPLDCONSUMERCOUNT}</td>
										<td >${HPLD}</td>
									</tr></c:if>
									<c:if test="${not empty OTHERSCONSUMERCOUNT}">
									<tr >
										<td >OTHERS</td>
										<td >${OTHERSCONSUMERCOUNT}</td>
										<td >${OTHERS}</td>
									</tr></c:if>
									
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
					
					
				
			
				<%-- <div class="col-md-6 col-sm-6">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-bell-o"></i>Tamper Events</div>
							<div class="actions">
								
							</div>
						</div>
						<div class="portlet-body">
							<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible="0">
								<ul class="feeds">
								<c:forEach var="tamper" items="${transactionData }">
								<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														${tamper[1]}
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												 ${tamper[2]}
											</div>
										</div>
									</li>
								
								</c:forEach>
								
									
									<!-- <li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														R-Ph current missing
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												18
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														Load imbalance. 
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												30
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														B phase potential missing. 
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												20
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														Power failed. 
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												15
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														CT short. 
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												5
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														CT Open. 
														            
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date" style="color: #000000">
												12
											</div>
										</div>
									</li> -->
								</ul>
							</div>
							<div class="scroller-footer">
								<div class="pull-right">
									<!-- <a href="#">View Events Report <i class="m-icon-swapright m-icon-gray"></i></a> &nbsp; -->
								</div>
							</div>
						</div>
					</div>
				</div> --%>
			</div>
		
		<div class="row" id="subleveles">
		  <c:forEach var="app" items="${finaldata}">
		
		<%-- <c:if test = "${divFinal == 'divFinal'}">
		
			<a class=" col-sm-4" href="./dashboard?type=${app.zone}&value= ${value}">
	</c:if> --%>
	
					<c:choose>
										<c:when test="${label == 'Division'}">
											<a class=" col-sm-4" href="#">  
										</c:when>
										<c:otherwise>
										<a class=" col-sm-4" href="./dashboard?type=${app.zone}&value= ${value}"> 
										</c:otherwise>
									</c:choose>
			<div class="portlet box blue ">
					<div class="portlet-title">
						<div class="caption">${label}  :  ${app.zone}</div><span id="todayId" style="color:yellow;font-weight: 15" > : ${todayDate}</span> 
						<!-- totalMeters -->
					</div>
					<div class="portlet-body">
						<label style="color: #8d8888; padding-top: 5px; float: left;">Total Consumers :
							<span id="totalFeeder"
							style="color: #FFFFFF; font-size: large; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${app.total_meters}</span>
						</label>
						<div class="clearfix"></div>
				</div>
				<div class="portlet-body">
				
				<c:choose>
										<c:when test="${label == 'Division'}">
											<label style="color: #319e2d; padding-top: 5px; float: left;" onclick="return viewCategorywiseData('${app.zone}');" data-target="#stack6" data-toggle="modal" >Aquired :
							<span id="totalFeeder"
							style="color: #FFFFFF; font-size: large; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${app.uploadCount}</span>
							
						</label>
						
						
										</c:when>
										<c:otherwise>
										<label style="color: #319e2d; padding-top: 5px; float: left;"  >Aquired :
							<span id="totalFeeder"
							style="color: #FFFFFF; font-size: large; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${app.uploadCount}</span>
							
						</label>
										</c:otherwise>
									</c:choose>
						
						<label style="color: #8d8888; padding-top: 5px; float: left;">Pending :
							<span id="totalFeeder"
							style="color: #FFFFFF; font-size: large; background-color: #4b8cf8; padding: 0px 10px; text-align: center;">${app.total_meters-app.uploadCount}</span>
						</label>
						<div class="clearfix"></div>
				</div>
				
				</div>
			</a>
			
		</c:forEach> 
	</div>
		
		
			
</div>