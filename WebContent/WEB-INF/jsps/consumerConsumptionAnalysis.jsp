<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>

<style>

label{
	margin-left:50px	
	
}
</style>
<script  type="text/javascript">

	$(".page-content").ready(function(){			
		//$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
		 $('#MDMSideBarContents,#reportsId,#consumerConsumptionanalysis').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		App.init();
		TableManaged.init();
		FormComponents.init();
		$('#conndate').val(moment(new Date()).format('MM/DD/YYYY'));
		
		});
	
	
</script>


<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<c:choose>
					<c:when test="${alert_type eq 'success'}">
						<div class="alert alert-success display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: green">${results}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger display-show">
							<button class="close" data-close="alert"></button>
							<span style="color: red">${results}</span>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Consumer Consumption Analysis
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

					<div class="row" style="margin-left: -1px;">
						<div class="col-md-4">
							<div class="form-group">
								<select class="form-control select2me" name="zone" id="zone"
									onchange="showCircle(this.value);">
									<option value="">Select Zone</option>
									 <option value="%">ALL</option> 
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="circleTd" class="form-group">
								<select class="form-control select2me" id="circle" name="circle">
									<option value="">Select Circle</option>
									 <option value="%">ALL</option> 
									<c:forEach items="${circleList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div id="divisionTd" class="form-group">
								<select class="form-control select2me" id="division"
									name="division">
									<option value="">Select Division</option>
									 <option value="%">ALL</option> 
									<c:forEach items="${divisionList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="row" style="margin-left: -1px;">

						<div class="col-md-4">
							<div id="subdivTd" class="form-group">
								<select class="form-control select2me" id="sdoCode"
									name="sdoCode">
									<option value="">Select Sub-Division</option>
										<option value="%">ALL</option> 
									<c:forEach items="${subdivList}" var="sdoVal">
										<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="col-md-3">
							<div class="input-group ">
								<input type="text" class="form-control from" id="month"
									name="month" placeholder="Select Month" style="cursor: pointer">
								<span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>

						</div>
						
						<div class="col-md-3">
						<button class="btn green pull-right" type="button"
							data-dismiss="modal" onclick="dayConsum()">view</button>
						
					</div>
					</div>
					</div>
					</div>
					</div>
					</div>
					

<%-- <div class="page-content" >
<!-- Display Error Message -->
		<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
	    </c:if>	
	    
	    
					<span style="color:red" id="accountNotExistMsg"></span>
			
		<!-- End Error Message -->
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Consumer Consumption Analysis</div></div></div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
										
											
						
				
					<div class="col-md-12">
							
							<table >
								<tr >
									<td>
										<div data-date-viewmode="years" data-date-format="yyyymm"  class="input-group input-medium date date-picker">
											<input type="text" name="month" id="month" readonly="readonly" class="form-control" required="required">
											<span class="input-group-btn">
												<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</span>	
										</div>	
									</td>
								
									<td id="masterTd1">
									<select  id="circle" class="form-control select2me input-medium"  type="text" name="circle" onchange="return getdivVal(this.value);">
									    <option value="0">SELECT CIRCLE</option>
										<c:forEach items="${circleVal}" var="element">
											<option value="${element}">${element}</option>
										</c:forEach>	
									</select></td>
										   
								    <td id="masterTd2">
									<select class="form-control select2me input-medium" name="subdivision" id="division" onchange="return getsubdivValue(this.value);">
									    <option value="0">Select division</option>
									    <c:forEach var="elements" items="${divlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>
									
										   
										   
									<td id="masterTd3">
									<select class="form-control select2me input-medium" name="subdivision" id="subdivision">
									    <option value="0">Select Subdiv</option>
									    <c:forEach var="elements" items="${subdivlist}">
									    	<option value="${elements}">${elements}</option>
									    </c:forEach>
									</select></td>
									
									<td>
										<button type ="submit" class="btn btn-primary" onclick="dayConsum();">
											<b>View</b>
										</button>
									</td>
													 
								</tr>
								
						</table>
						<div id="imageee" hidden="true" style="text-align: center;">
		   				 <h3 id="loadingText">Loading.... Please wait. </h3> 
						</div>
									
					</div>
					<!-- ED EXAMPLE TABLE PORTLET-->
					
				</div>
			</div> --%>
			
			

<br><br>

<div id="chartContainer" style="height: 370px; width: 100%;"></div></div>
	<script type="text/javascript">


	function showCircle(zone)
	{
		 $.ajax({
		    	url:'./getCircleByZone',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					zone : zone
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
		    	}
			});
	}

	 function showDivision(circle) {
		 var zone = $('#zone').val();
		
			$.ajax({
				url : './getDivByCircle',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone,
					circle : circle
				},
						success : function(response) {
							var html = '';
							html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response.length; i++) {
								html += "<option  value='"+response[i]+"'>"
										+ response[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#divisionTd").html(html);
							$('#division').select2();
						}
					});
		}
	 function showSubdivByDiv(division) {
			
			var zone = $('#zone').val();
			var circle = $('#circle').val();
			$.ajax({
				url : './getSubdivByDiv',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone,
					circle : circle,
					division : division
				},
						success : function(response1) {
							var html = '';
							html += "<select id='sdoCode' name='sdoCode'  class='form-control' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								//var response=response1[i];
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTd").html(html);
							$('#sdoCode').select2();
						}
					});
		}


	
function dayConsum() {
	var dataPoints = [];
 	 var circle=$("#circle").val();
 	 if(circle=="0"){
 		 bootbox.alert("Please select Circle.")
 	 }
	 var division=$("#division").val();
	 if(division=="0")
		 {
		 division="all";
		 }
	 var subdivision=$("#sdoCode").val();
	 if(subdivision=="0")
	 {
		 subdivision="all";
	 }
	 var month=$("#month").val();
	 $('#imageee').show();
	$.ajax({       
    	url : './getconsumptionanalysyis',
    	type:'GET',
    	dataType:'json',
    	data:{division:division,circle:circle,subdivision:subdivision,month:month},
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		$('#imageee').hide();
   		 	for(var i=0;i<data.length; i++)
			{
   				
   		 		dataPoints.push({
   		 			
//		        x: new Date(moment(data[i][1]).format("YYYY"), moment(data[i][1]).format("MM"), moment(data[i][1]).format("DD")),
				x:	new Date(data[i][1]),
			     y:parseFloat(data[i][0])
		        
		      });
	   		}
   		 	
   		 var chart = new CanvasJS.Chart("chartContainer", {
		      title: {
		        text: "Consumption Curve"
		      },
		      axisX: {
		        title: "Ip-Interval",
		        
		      },
		      axisY: {
		        includeZero: false,
		        title: "kwh-Value",
		        /* interval:1,
	             intervalType: "day" */
		        
		      },
		      data: [{
		        type: "line",
		        dataPoints: dataPoints
		      }]
		    });

		    chart.render();	
	},
	  
 });

}


var date = new Date();
var year = date.getFullYear();
var month = date.getMonth();

$('.from').datepicker({
	format : "yyyymm",
	minViewMode : 1,
	autoclose : true,
	startDate : new Date(new Date().getFullYear()),
	endDate : new Date(year, month - 1, '31')
});
</script>
	