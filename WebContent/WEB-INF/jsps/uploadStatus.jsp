<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>

<script  type="text/javascript">
$(".page-content").ready(function()
    	   {  
		
	App.init();
	TableEditable.init();
	FormComponents.init();
	loadSearchAndFilter('sample_1');
	$('#ADMINSideBarContents,#uploadStatusId').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
			
	});
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>CMRI Upload Status 
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
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
										onclick="tableToExcel('sample_1', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>File Name</th>
								<th>MeterNo</th>
								<th>status</th>
								<th>DateStump</th>
								
							</tr>
						</thead>
								<tbody>
									<c:set var="count" value="1" scope="page"></c:set>
										<c:forEach var="element" items="${uploadStatus}">
												<tr>
													<td>${count}</td>
													<td>${element[9]}</td>
													<td>${element[2]}</td>
													<td>${element[14]}</td>
													<td>${element[1]}</td>
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
					
					
							
<script>
function validation(btnValue)
{
	var startMeter=$("#meternoStartId").val();
	var endMeter=$("#meternoEndId").val();
	var meterno=$("#meterno").val();
	var connectionType=$("#connectionTypeId").val();
	var meterMake=$("#meterMakeId").val();
	var commision=$("#commisionId").val();
	var model= $("#meterModelId").val();
	var ip=$("#ipPeriodId").val();
	var ptratio=$("#ptratioId").val();
	
	var meterConstant=$("#meterConstatntId").val();
	var ctratio=$("#ctRatioId").val();
	var tenderno=$("#tenderNoId").val();
	var accuracy=$("#mtrAccClassId").val();
	var manfacutre=$("#mYearMnth").val();
	var currntRating=$("#curntRatingId").val();
	var warrenty=$("#warrentyId").val();
	var voltgRating=$("#mtrVltgRatId").val();
	var mtrStatus=$("#mtrStatus").val();


		if(btnValue=="addBatch")
		{
				if(startMeter=='')
				{
					bootbox.alert("Please enter startMeter Sl No");
					return false;
				}
				if(endMeter=='')
				{
					bootbox.alert("Please enter endMeter Sl No");
					return false;
				}
			
				if(isNaN(startMeter))
				{
					bootbox.alert("Please enter only numbers in Start Meter No Field");
					return false;
				}
				if(isNaN(endMeter))
				{
					bootbox.alert("Please enter only numbers in End Meter No Field");
					return false;
				}
				var total=endMeter-startMeter;
				if(endMeter<startMeter)
				{
				bootbox.alert("Please enter End Meter Sl No More than Start Sl No");
				$("#meternoEndId").val("");
				return false;
				}
				if(total>100)
				{
				bootbox.alert("More than 100 meter will not allowed to add");
				$("#meternoEndId").val("");
				return false;
				};
			}
			if(btnValue=="update"||btnValue=="save")
			{
			 if(meterno=="")
				{
					bootbox.alert("Please Enter meterno");
					return false;
				};
	   		 }
		
		if(connectionType==0)
		{
			bootbox.alert("Please Select Connection Type");
			return false;
		}
		
		if(meterMake==0)
		{
			bootbox.alert("Please Select Meter Make");
			return false;
		}
		
		if(commision==0)
		{
			bootbox.alert("Please Select Commisioning");
			return false;
		}
		if(model=="")
		{
			bootbox.alert("Please Enter Meter Model");
			return false;
		}
		if(ip==0)
		{
			bootbox.alert("Please Select Meter IP PERIOD");
			return false;
		}
		 if(ptratio=="" || isNaN(ptratio))
		{
			bootbox.alert("Please Enter PT Ratio  and Enter Only Numbers");
			return false;
		} 
		if(meterConstant=="" || isNaN(meterConstant))
			{
			bootbox.alert("please Enter Meter Constant and Enter Only Numbers");
			return false;
			}
		
		if(ctratio=="" || isNaN(ctratio))
		{
			bootbox.alert("Please Enter CT Ratio and Enter Only Numbers");
			return false;
		}
		if(tenderno=="" )
		{
			bootbox.alert('please Enter Tender');
			return false;
		}
		
		if(accuracy==0 )
		{
			bootbox.alert("Please Select Meter Accuracy Class");
			return false;
		}
		 if(manfacutre=="" )
		{
			bootbox.alert("Please Enter Manufacture Year Month");
			return false;
		}
		
		 if(currntRating=='')
			{
				bootbox.alert('Please Enter Current Rating');
				return false;
			}
		if(warrenty=='')
		{
			bootbox.alert('Please Enter Warrenty Period');
			return false;
		}
		
		if(voltgRating=='' )
		{
			bootbox.alert('Please Enter Voltage Rating ');
			return false;
		}
		if(mtrStatus=='')
		{
			bootbox.alert('Please Select Meter Status');
			return false;
		}
		
	if(btnValue=="update")
	{
		$("#addMeterDetailsFormId").attr('action','./updateMeterInventoryDetails').submit();
	}
	if(btnValue=="save")
	{
		$("#addMeterDetailsFormId").attr('action','./addMeterInventoryDetails').submit();
	}
	if(btnValue=="addbatch")
	{
		$("#addMeterDetailsFormId").attr('action','./addBatchMeterInventoryDetails').submit();
	};
 }
function clearMyForm(param)
{
	document.getElementById('addMeterDetailsFormId').reset();
	document.getElementById('addMeterStackId').innerHTML='';
	document.getElementById('addMeterStackId').innerHTML='Add Meter';
	$("#addMeterbtnId").hide();
	$("#updateMeterbtnId").hide();
	$("#updateMeterbtnId").hide();
	if(param=="addSingle"){
		$("#addMeterbtnId").show();
		$("#updateMeterbtnId").hide();
		$("#addMeterBatchbtnId").hide();
		$("#mtrdivId").show();
		$("#mtrPrfxDivId").hide();
		$("#mtrSlnoDivId").hide();
		}
	if(param=="addBatch"){
		$("#addMeterbtnId").hide();
		$("#updateMeterbtnId").hide();
		$("#addMeterBatchbtnId").show();
		$("#mtrdivId").hide();
		$("#mtrPrfxDivId").show();
		$("#mtrSlnoDivId").show();
		document.getElementById('addMeterStackId').innerHTML ="";
		document.getElementById('addMeterStackId').innerHTML='Add Meter Batch';
		}
}

function editMeterDetails(param,opera)
{
	
	$("#mtrdivId").show();
	$("#addMeterbtnId").hide();
	$("#updateMeterbtnId").show();
	$("#addMeterBatchbtnId").hide();
	 document.getElementById('addMeterDetailsFormId').reset();
	 document.getElementById('addMeterStackId').innerHTML ="";
	 document.getElementById('addMeterStackId').innerHTML='Modify Meter';
	 var operation=parseInt(opera);
     $.ajax(
			{
					type : "GET",
					url : "./getMeterInventoryData/" + operation,
					dataType : "json",
					success : function(response)
								{
									$("#meterId").val(response.id);
									$("#meterno").val(response.meterno);
									$("#connectionTypeId").val(response.meter_connection_type);
									$("#meterMakeId").val(response.meter_make);
									$("#commisionId").val(response.meter_commisioning);
									$("#meterModelId").val(response.meter_model);
									$("#ipPeriodId").val(response.meter_ip_period);
									$("#ptratioId").val(response.pt_ratio);
									$("#meterConstatntId").val(response.meter_constant);
									$("#ctRatioId").val(response.ct_ratio);
									$("#tenderNoId").val(response.tender_no);
									$("#mtrAccClassId").val(response.meter_accuracy_class);
									$("#mYearMnth").val(response.month);
									$("#curntRatingId").val(response.meter_current_rating);
									$("#warrentyId").val(response.warrenty_years);
									$("#mtrVltgRatId").val(response.meter_voltage_rating);
									$("#mtrStatus").val(response.meter_status);
								}
			}); 
		
    $('#'+param).attr("data-toggle", "modal");
	  $('#'+param).attr("data-target","#stack1");   
	    
}; 


function checkMeterNo(meterno)
{
	$("#accountNotExistMsg").html(""); 
	$.ajax(
			{
					type : "GET",
					url : "./checkMeterNoInInventory/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response.length>0)
						{
							 $("#accountNotExistMsg").html("Entered Meter No Aleready Present"); 
						}
					}
			});
}

function getMeterData()
{
	var meterno= $("#meterNumId").val();
	$.ajax(
			{
					type : "GET",
					url : "./checkMeterNoInInventory/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response.length>0)
						{
							
							var html="";
							for(var i=0;i<response.length;i++){
								var data=response[i];
								html+="<tr>";          
								html+="<td><a href='#' onclick='editMeterDetails(this.id,\""+data.id+"\")' id='editData'>Edit</a></td>";
								html+="<td>"+data.meterno+"</td>";
								html+="<td>"+data.meter_connection_type+"</td>";
								html+="<td>"+data.meter_make+"</td>";
								html+="<td>"+data.meter_commisioning+"</td>";
								html+="<td>"+data.meter_model+"</td>";
								html+="<td>"+data.meter_ip_period+"</td>";
								html+="<td>"+data.pt_ratio+"</td>";
								html+="<td>"+data.meter_constant+"</td>";
								html+="<td>"+data.ct_ratio+"</td>";
								html+="<td>"+data.tender_no+"</td>";
								html+="<td>"+data.meter_accuracy_class+"</td>";
								html+="<td>"+data.month+"</td>";
								html+="<td>"+data.meter_current_rating+"</td>";
								html+="<td>"+data.warrenty_years+"</td>";
								html+="<td>"+data.meter_voltage_rating+"</td>";
								html+="<td>"+data.meter_status+"</td>";
								
								html+="</tr>";
								}
							$("#meterDetailsId").html(html);
						}
						
						
					}
			});
}


</script>			

