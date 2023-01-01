<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
.headid{
font-size: 27px;
font-style: bold;
}
.selid{
    width: 118px;
    height: 32px;
}

</style>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						//high('${instantanousData[0].rPhaseVal}');
						App.init();
						TableEditable.init();
						TableManaged.init();
						FormComponents.init();
						UIDatepaginator.init();
						$('#MDMSideBarContents,#vee,#Aggregation').addClass('start active ,selected');
						/*  if('${billedDataList.size()}'>0)
						{
							  
						displayConsuptionChart();
						} */

						/* $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}'));
						$("#loadSurveydateId").val(moment(new Date).format('DD-MM-YYYY')); */
					
						$(
								"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');
						$('#meterVal').val('${meterNo}');

			});
</script>
<div class="page-content">

	<div class="portlet box blue">


		<!-- /.col-md-6 -->

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Aggregation
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>


		<div class="portlet-body">

			<div class="col-md-12">

				<form method="post" id="meterMasterForm" action="">
					<table>
						<tr>
							<td class="headid">Zone :</td>
							<td >
							<select class="selid" id="zoneId" onchange="getCircleByZone(this.value)">
							<option value="0">Select Zone</option>
							<c:forEach var="zone" items="${zone}">
							
							<option>${zone}</option>
							</c:forEach>
							</select>
							</td>
							<td class="headid">Circle :</td>
							<td >
							<select class="selid" id="circleId" onchange="getDivByCircle(this.value)"></select>
							</td>
							<td class="headid" > Division :</td>
							<td >
							<select class="selid" id="divisionId"></select>
							</td>
							<!-- <td class="headid">Sub-Division :</td>
							<td >
							<select class="selid"><option>UHBVN</option></select>
							</td>
							<td class="headid">Sub-Station :</td>
							<td >
							<select class="selid"><option>UHBVN</option></select>
							</td> -->
						</tr>
						
						<tr>
						 <td colspan="5"></td>
							<td><input style="background-color: green;" type="button" class="form-control" id="submit"
								value="Submit" required="required" onclick="getConsumptionData()"></td> 
						</tr>
					</table>





				</form>



			</div>
			<br /> <br />
			<div class="clearfix"></div>
			<div class="clearfix"></div>
           <div>
           <table border="1" style="margin-left: 195px; width: 881px;">
           
           <thead>
           <tr><th>Sub Division Name</th><th>Energy Consumed(KWH)</th></tr>
           
           </thead>
           <tbody id="aggdata">
           
           </tbody>
					

						
					</table>
           </div>

		</div>
	</div>

	<c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>
	<!-- End Error Message -->

	<!-- Start Intantanous -->

</div>

<script type="text/javascript">
function getCircleByZone(zone)
{
	
	//alert(zone);
	$.ajax({
		type : 'GET',
		url : "./getCircleByZone",
		data:{zone:zone},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Circle</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#circleId").empty();
				$("#circleId").append(html);
				}
			
		}
	});

	
}

function getDivByCircle(circle)
{
	//alert(circle);
	$.ajax({
		type : 'GET',
		url : "./getDivByCircle",
		data:{circle:circle},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Division</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				
				$("#divisionId").empty();
				$("#divisionId").append(html);
				}
			
		}
	});
}

function getSubDivByDivision(division)
{
	//alert(division);
	$.ajax({
		type : 'GET',
		url : "./getSubdivByDiv",
		data:{division:division},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Sub-Division</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#sdonameId").empty();
				$("#sdonameId").append(html);
				}
			
		}
	});
}

function getConsumptionData()
{   var zoneid=$('#zoneId').val();
	var cirid=$('#circleId').val();
	var divid=$('#divisionId').val();
	
	//alert(division);
	$.ajax({
		type : 'GET',
		url : "./veeAggregationDATA",
		data:{"zoneid":zoneid,
			"cirid":cirid,
			"divid":divid
		
		},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				 
				for(var i=0;i<response.length;i++)
					{
					
					
					html+="<tr><td>"+response[i][0]+"</td>";	
					 if(response[i][1]==null){
						 html+=" <td>0</td></tr>";
				} 
					 if(response[i][1]!=null){
						 html+="<td>"+response[i][1]+"</td></tr>";
				} 
					}
						 
					
				$("#aggdata").empty();
				$("#aggdata").append(html);
				}
			
		}
	});
}
</script>
