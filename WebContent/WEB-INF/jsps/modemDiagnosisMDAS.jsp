<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.input-medium {
    width: 180px !important;
}
a {
    color: blue;
    text-shadow: none !important;
}
</style>
		
<script>
$(".page-content").ready(function() 
{
	//alert('${inActiveModems}');
	if ('${mdmMngtList.size()}' > 0) 
	{
		$('#zone').val('${zone}');
		$('#circle').val('${circle}');
		$('#division').val('${division}');
		$('#sdoCode').val('${subdiv}');
		$('#viewDiagnosisStats').show();
	}
	if ('${diagnosCatList.size()}' > 0) 
	{
		$('#zone').val('${zone}');
		$('#circle').val('${circle}');
		$('#division').val('${division}');
		$('#sdoCode').val('${subdiv}');
		$('#DiagnosisStatsDetails').show();
	}
	App.init();
	TableEditable.init();
	 FormComponents.init();
	 loadSearchAndFilter('sample_1');
	 $('#MDASSideBarContents,#modemMang,#modemDiagnosis').addClass('start active ,selected');
		$('dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');

});
</script>

<script>
function showCircle(zone)
{
	$.ajax({
	    	url:'./showCircleMDAS'+'/'+zone,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Circle</option><option value='All'>ALL</option>";
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

function showDivision(circle)
{
	var zone=$('#zone').val();
	$.ajax({
	    	url:'./showDivisionMDAS'+'/'+zone+'/'+circle,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Division</option><option value='All'>ALL</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#divisionTd").html(html);
				$('#division').select2();
	    	}
		});
}

function showSubdivByDiv(division)
{
	var zone=$('#zone').val();
	var circle=$('#circle').val();
	$.ajax({
	    	url:'./showSubdivByDivMDAS'+'/'+zone+'/'+circle+'/'+division,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>";
				for( var i=0;i<response1.length;i++)
				{
					//var response=response1[i];
					html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#subdivTd").html(html);
				$('#subdiv').select2();
	    	}
		});
}

function modemDiagnosVal(form1)
{
	if(form1.zone.value=='noVal')
  	{
  	   bootbox.alert('Please select Zone');
	   return false;
  	}
	if(form1.circle.value=='noVal')
  	{
  	   bootbox.alert('Please select Circle');
	   return false;
  	}
	if(form1.division.value=='noVal')
  	{
  	   bootbox.alert('Please select Division');
	   return false;
  	}
	if(form1.sdoCode.value=='noVal')
  	{
  	   bootbox.alert('Please select Sub-Division');
	   return false;
  	}
} 
var zone='${zone}';
var circle='${circle}';
var division='${division}';
var subdiv='${subdiv}';
function getValOnDiagStats(category)
{
	$('#category').val(category);
	window.location.href="./modemDiagnosisCatMDAS?zone="+zone+"&circle="+circle+"&division="+division+"&sdoCode="+subdiv+"&category=" + category;
	return false;
	
}
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	 window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
}
</script>

<div  class="page-content" >
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>
			
	<div class="portlet box green">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Meter Diagnosis Details
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="reload"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
				<form action="./modemDiagnosisStatsMDAS" method="post">
					<table style="width: 38%">
						<tbody>
							<tr>
								<td><select class="form-control select2me input-medium" name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Zone</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>
								
								<td id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle">
										<option value='noVal'>Circle</option>
										<option value='All'>ALL</option>
										<c:forEach items="${circleList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
										<option value='noVal'>Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${divisionList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
										<option value='noVal'>Sub-Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select></td>
								<td>
								<button id="viewFdrOnMap" name="viewFdrOnMap" onclick="return modemDiagnosVal(this.form);" class="btn yellow"><b>View</b></button>
								<!-- <button type="submit" id="viewFdrOnMap" onclick="return modemDiagnosVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<input type="text" id="category" hidden="true">
	<div class="portlet box blue" id="viewDiagnosisStats" style="display: none;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-globe"></i>Meter Diagnosis Stats
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body" id="viewFOM">
			<!-- <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Modem Diagnosis Stats')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div> -->
			<table class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th style="text-align: center;">Today</th>
						<th style="text-align: center;">Previous Day</th>
						<th style="text-align: center;">Current Month</th>
						<th style="text-align: center;">Previous Month</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="element" items="${mdmMngtList}">
						<tr style="text-align: center;">
							<td><a href='#' onclick="return getValOnDiagStats('Today');" >${element[1]}</a></td>
							<td><a href='#' onclick="return getValOnDiagStats('Yesterday');" >${element[2]}</a></td>
							<td><a href='#' onclick="return getValOnDiagStats('CurrMonth');" >${element[3]}</a></td>
							<td><a href='#' onclick="return getValOnDiagStats('PrevMonth');" >${element[4]}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
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
									<div id="closeShow">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h4 class="modal-title"><b>Consumer Variation</b>
										
										</h4>
										</div>
									</div>

									<div class="modal-body">
										<br>
										<div class="row">
											<div class="col-md-12">
											 <table class="table table-striped table-hover table-bordered" id="sample_4"  >
											 <thead>
											 <tr>
											 <th>Meter No</th>
											 <th>Ir</th>
											 <th>Iy</th>
											 <th>Ib</th>
											 <th>Vr</th>
											 <th>Vy</th>
											 <th>Vb</th>
											 <th>V_b1</th>
											 <th>kWh</th>
											<!--  <th>METERTYPE</th>
											 <th>DEVICEID</th>
											 <th>CURRENTRATING</th>
											 <th>YOM</th>
											 <th>VIEW</th> -->
											 </tr>
											 <tr></tr>
											 </thead>
								                 <tbody id="meterBody" >
								                </tbody>
										    </table> 
													
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
	</div>
	
		<div class="portlet box purple" id="DiagnosisStatsDetails" style="display: none;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-globe"></i>Meter Diagnosis Stats Details
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Modem Diagnosis Stats Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
			<table class="table table-striped table-hover table-bordered" id="sample_1">
				<thead>
					<tr>
						<th style="text-align: center;">Acc No</th>
						<th style="text-align: center;">Consumer Name</th>
						<th style="text-align: center;">Meter No</th>
						<th style="text-align: center;">Event Code</th>
						<th style="text-align: center;">Tracked Time</th>
						<th style="text-align: center;">Event Desp</th>
						<th style="text-align: center;">Event Param</th>
						
					</tr>
				</thead>
				<tbody>

					<c:forEach var="element" items="${diagnosCatList}">
						<tr style="text-align: center;">
							<td>${element[4]}</td>
							<td>${element[5]}</td>
							<td><a href='./viewFeederMeterInfoMDAS?mtrno=${element[0]}'>${element[0]}</a></td>
							<td>${element[1]}</td>
							<td><fmt:formatDate value="${element[2]}"  pattern="dd-MM-yyyy HH:mm:ss"/></td>
							<td>${element[3]}</td>
								<td><a href="#" onclick="getAddress('${element[0]}','${element[2]}')" data-target='#stack6' data-toggle='modal'>View</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	</div>
	</div>
</div>
				
				
<<script>
function getAddress(meterno, viewTime)
{
	
	$.ajax({
		   type: "POST",
	        url: "./getDataCompare",
	        data:{meterno:meterno,
	        	viewTime:viewTime
	        			
	        },
	        
	        dataType: "json",
	        cache:false,
			async:false,
	        success: function(response)
	     
	        {
	        	
	        	var result=response[0];
	        	
	        	if(result!=null)
		        	{
		        	 var html ="<tr>"
							+" <td>"+result[1]+"</td>" //meterno
							+" <td>"+result[3]+"</td>" //phase
							+" <td>"+result[4]+"</td>" //phase
							+" <td>"+result[5]+"</td>" //mtrtype
							+" <td>"+result[6]+"</td>" ///mtrmke
							+" <td>"+result[7]+"</td>" //mtrtype
							+" <td>"+result[8]+"</td>" ///mtrmke
							+" <td>"+result[9]+"</td>" //mtrtype
							+" <td>"+result[10]+"</td>" ///mtrmke
							
							+" </tr>"; 
						$("#meterBody").empty();
						$("#meterBody").append(html); 
		        	}
	        }
		});
	}
 

</script>				