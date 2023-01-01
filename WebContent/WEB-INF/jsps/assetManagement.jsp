<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){			
		App.init();
		TableManaged.init();
		FormComponents.init();
		$('#MDMSideBarContents,#metermang,#assestmanagement').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
 
	 function getMeterLifeCycleData()
	 {
		 alert("HIII");
		 var mtrno=$("#meterNo").val();
	 	 if(mtrno=="")
	 		 {
	 		 	bootbox.alert("Please Enter MeterNo.");
	 		 	return false;
	 		 }
		 $.ajax({
		    	url:"./getMeterData",
		    	type:"GET",
		    	dataType:"JSON",
		    	asynch:false,
		    	cache:false,
		    	data:{
		    		mtrno:mtrno
		    	},
		    	success:function(response)
		    	{
		    		
	 			 var html="";
	 			
		    		for (var i = 0; i < response.length; i++)
		    		{
		    			var data=response[i];
		    			html+="<tr>"
		    			+"<td>"+data.metrno+"</td>"
		    			+"<td>"+data.accno+"</td>"
		    			+"<td>"+data.consumername+"</td>"
		    			+"<td>"+data.address+"</td>"
		    			+"<td>"+data.discom+"</td>"
		    			+"<td>"+data.circle+"</td>"
		    			+"<td>"+data.division+"</td>"
		    			+"<td>"+data.subdiv+"</td>"
		    			+"<td><a href='#' >View History</a></td>"
		    			+"</tr>";
					}
					$("#meterLifeDataBody").html(html);
		    	}
			});
		// onclick='viewMeterwiseData("+${meter.mtrno}+")' id='viewEmp"+${meter.mtrno}+"'
	 }
</script>
<div class="page-content" >
<div class="row">
			 <c:if test="${result ne 'notDisplay'}">
				<div  class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${result}</span>
				</div>
			</c:if>
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Meter Management</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
							</div>
						</div>					
											
						<div class="portlet-body">
						<!-- <table>
										<tr><td>Enter Meter No:<font color="red">*</font></td><td><input type="text" class="form-control input-small" id="meterNo"/></td><td><button type="button" class="btn btn green" onclick="return getMeterLifeCycleData();">Track</button></td>
										</tr>
									     
				   		 </table> </br> </br> -->
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th>METERNO</th>
										<th>ACCOUNT NUMBER</th>
										<th>INSTALLED DATE</th>
										<th>INITIAL READING</th>
										<th>FINAL READING</th>
										<th>METER STATUS</th>
										<th>DISCONNECTION DATE</th>
            					</tr>
								</thead>
								<tbody id="meterLifeDataBody">
									 <c:set var="data" value="${meterdata}"></c:set>
									  <c:forEach var='meter' items='${data}'> 
									  <c:choose>
									  <c:when test="${ not empty meter.meter_no}">
									 	 <tr id="sampel" class="odd gradeX">
										 	<td><c:out value="${meter.meter_no}"/></td>
										<td><c:out value="${meter.consumer_no}"/></td>
										 	<td><c:out value="${meter.installed_date}"/></td>
										 	<td><c:out value="${meter.initial_reading}"/></td>
										 	<td><c:out value="${meter.final_reading}"/></td>
										 	<td><c:out value="${meter.meter_status}"/></td>
										 	<td><c:out value="${meter.disconn_date}"/></td>
									 	</tr> 
									   </c:when>
									   </c:choose>
									 </c:forEach> 									 									 
								</tbody>
							</table>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
			<div class="modal-body" id="closeShow1" style="display: none;">
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
			
			<div id="stack1" class="modal fade" data-width="300" data-backdrop="static">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Update Meter Status</h4>
							</div>

							<div class="modal-body" id='mainDiv'>
								<div class="row">
									<div class="col-md-12">
									<form action="./updatemeterStatus" method="post">

										<table>
											<tr>
												<td>Meter Status</td>
												<td><select  id="mtrstatus" class="form-control placeholder-no-fix" type="text" name="mtrstatus">
												<option value="INACTIVE">INACTIVE</option>
												<option value="ACTIVE">ACTIVE</option>
												</select></td>
											</tr>
											<tr>
												<td>Meter Status</td>
												<td><input  id="mtrno" class="form-control placeholder-no-fix" type="text" name="mtrno"/></td>
											</tr>
											<tr hidden="true">
												<td><input  id="transid" class="form-control placeholder-no-fix" type="text" name="transid"/></td>
											</tr>
										</table>
										<div class="modal-footer">
											<button type="button" data-dismiss="modal" class="btn red" id="closeData">Close</button>
											<button class="btn green pull-right"  id="updateOption">Update Status</button>
										</div>

									</form>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
</div>
<script>
function viewMeterwiseData(meterno)
{
	var arryLength=0;
	$.ajax({
    	url:'./getMeterWiseData/'+meterno,
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
    		
    		var html2 = "<thead><tr style='background-color: lightgray'><th>SLNO.</th><th >METERNO</th><th >METERSTATUS</th><th >TRANSACTION_DATE</th><th >REMARKS</th><th>COMMANDS</th><th>EDIT</th></tr></thead>";
    		for(var j=0;j<response.length;j++)
    		{	
    		     html2 += "<td>"+(j+1)+"</td>";
    		     html2 += "<td>"+response[j][3]+"</td>";
    		     html2 += "<td>"+response[j][0]+"</td>"; 
    		     html2 += "<td>"+response[j][1]+"</td>";
    		     html2 += "<td>"+response[j][2]+"</td>";
    		     html2 += "<td>"+response[j][5]+"</td>";
    		     html2 += "<td><a href='#' onclick='edit(this.id,"+response[j][4]+")' id='edit("+response[j][3]+")' data-toggle='modal' data-target='#stack1'>Edit</a></td></tr>";
    		}
    		$('#sample_4').html(html2);
    		loadSearchAndFilter('sample_4');
    	}
	    
    });
	return false;
}

function edit(param,transid)
{
	$.ajax({
					type : "GET",
					url : "./editMeterStatus/" + transid,
					dataType : "json",
					cache:false,
					async:false,
					success : function(response)
								{ 
									$('#mtrstatus').val(response.meter_status);
						           $('#mtrno').val(response.meter_no); 
						           $('#transid').val(response.trans_id);
							    }							
			});
$('#'+param).attr("data-toggle", "modal");//edit button
$('#'+param).attr("data-target","#stack1"); //edit button
}


</script>
