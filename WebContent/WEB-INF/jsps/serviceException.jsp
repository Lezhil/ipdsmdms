<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#MIS-Reports").addClass('start active , selected');

						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						TableManaged.init();
						Existingexception();

						$('#DATAEXCHsideBarContents,#ServiceMonitoringId,#serviceExceptionId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_2');
						 
						
					});
</script>
<script>
function Existingexception(){
	document.getElementById('UpdateAckMsg').reset();
	$('#imageee').show();
	$.ajax({
		url:"./existingexception",
		type:'POST',
		datatype:'json',
		success:function(response)
    	{
			$('#imageee').hide();
			//alert(response)
		      var html="";
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
					var response6="";
					 if(resp[6]==true)
						{
						 response6='yes';
						 
						}
					  else if(resp[6]==false)
						{
						response6='No';
						
						}  
					
					   html+="<tr>"+
					   //"<td hidden="true" id="ackId"+id>"+resp[0]+" </td>"+
					  "<td>"+resp[1]+" </td>"+
					  "<td>"+resp[2]+" </td>"+
					  "<td>"+resp[3]+" </td>"+
					  "<td>"+resp[4]+" </td>"+
					  //"<td>"+resp[5]+" </td>"+
					  "<td>"+ (resp[5] == null ? "" : moment(resp[5]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"+
					  "<td>"+response6+" </td>"+
					  "<td><a href=# onclick=OnAcknowledge("+resp[0]+") id=ackId("+resp[0]+")>Acknowledged</a></td>"
				      /* "<td><a href='#'  data-toggle='modal' data-target='#stack1'>Acknowledged</a></td >"+ */
					  +"</tr>";						
			   }   
			  
			  $('#sample_1').dataTable().fnClearTable();
			  $("#updateExistExcpId").html(html);
			  loadSearchAndFilter('sample_1'); 
    						 
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_1'); 
		}
		
	});
	
}
function view(){
	var DailyFDId = $('#DailyFDId').val();
	var DailyTDId = $('#DailyTDId').val();
	var serviceId = $('#serviceId').val();
	
	if(DailyFDId==""){
		bootbox.alert("Please Select From Date");
		return false;
	}
	if(DailyTDId==""){
		bootbox.alert("Please Select To Date");
		return false;
	}
	if(DailyFDId>DailyTDId){
		bootbox.alert("wrong data inputs");
		return false;
	}
	if(serviceId==""){
		bootbox.alert("Please Select Service Name");
		return false;
	}
	$('#imageee').show();
	$.ajax({
		url:"./getAckData",
		type:'POST',
		data:{
			DailyFDId:DailyFDId,
			DailyTDId:DailyTDId,
			serviceId:serviceId,
		},
		datatype:'json',
		success:function(res){
			$('#imageee').hide();
			if (res.length != 0) {
			html="";
			$.each(res,function(i,v){
			   var varible5="";
				 if(v[5]==true)
					{
					  varible5='yes';
					 
					}
				  else if(v[5]==false)
					{
					   varible5='No';
					
					} 
				html +="<tr>"
				+"<td>"+(i+1)+"</td>"
				+"<td>"+v[0]+"</td>"
				+"<td>"+v[1]+"</td>"
				+"<td>"+v[2]+"</td>"
				+"<td>"+v[3]+"</td>"
				 +"<td>"+ (v[4] == null ? "" : moment(v[4]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"
				+"<td>"+varible5+"</td>"
				 +"<td>"+ (v[6] == null ? "" : moment(v[6]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"
				+"<td>"+v[7]+"</td>"
				+"<td>"+v[8]+"</td>"
				"</tr>";
			});
			
			clearTabledataContent('sample_2');
			
			$("#updateAckExcpId").html(html);
			}
			else{
				bootbox.alert("No Data");
			}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_2'); 
		}
	});
	
}

function OnAcknowledge(param) {
	 var Acknowledged = $("#AckMsgId").val();
	//$("#AckMsgId").clear();
	//document.getElementById('UpdateAckMsg').reset();
	
	var id = param;
	//alert(id);
	if (id == "" || id == null) {
		bootbox.alert("Nodata ");
		return false;
	}
	$("#serExcId").val(id);
	 $("#stack1").modal('show');
	 $.ajax({

		type : "GET",
		url : "./onAcknowledge/" + id,
		dataType : "json",
		cache : false,
		async : false,
		success : function(response) {
			//alert(response);
			 var html='';
			 $("#AckMsgId").val(response); 
		
		}
	}); 
   
} 

function save(){
	var Acknowledged = $("#AckMsgId").val();
	var ackId = $("#serExcId").val();
	if(Acknowledged == ''){
		bootbox.alert("Please enter the Acknowledge messege");
		return false;
	}
	
	
	$.ajax({
		url:"./tosaveAck",
		type:'GET',
		data:{
			Acknowledged:Acknowledged,
			ackId:ackId,
		},
		datatype:'json',
		success:function(response){
			//alert(response);
			if(response=="exist"){
				bootbox.alert("Acknowledged Message Saved Successfully");
				return false;
			}
			else if (response=="not exist"){
				bootbox.alert("Acknowledged NotSaved ");
				return false;
			}
			
		},
		
	});

	
} 

</script>
<div class="page-content">
<div class="portlet box blue">
<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Service Exception
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>

<div class="portlet-body">
<div class="modal-body">
<div class="tabbable tabbable-custom tabbable-full-width">
<ul class="nav nav-tabs">
						<li class="active"><a onclick="Existingexception()" href="#tab_1_1" data-toggle="tab">Existing Exception
								 </a></li>
						<li><a href="#tab_1_2" onclick=";" data-toggle="tab" >Acknowledged Exception
								</a></li>
					</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab_1_1" >
<table class="table table-striped table-hover table-bordered"
									id="sample_1" >
									<thead>
										<tr>
										   <th>Service Name</th>
											<th>Requester</th>
											<th>Provider</th>
											<th>Exception</th>
											<th>Exception capture time</th>
											<th >Notified</th>
											<th>Acknowledged</th>
											<!-- <th hidden="true" id="id">id</th> -->
										</tr>
									</thead>
									<tbody id="updateExistExcpId">
                                       
									</tbody>
								</table>
</div>

<div class="tab-pane fade" id="tab_1_2" >
<div class="row">
<div class="col-md-4">
 <div class="form-group">
 <label class="control-label">From Date<font color="red">*</font></label>
 <div class="input-group input-large date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="0d" data-date-viewmode="years" id="fromDate">
 
																<input type="text"  class="form-control" name="DailyFDId" id="DailyFDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div> 
																</div>
<div class="col-md-4">																
<div class="form-group">
 <label class="control-label">To Date<font color="red">*</font></label>
 <div class="input-group input-large date date-picker"  data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="DailyTDId" id="DailyTDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>	
																</div>															
<div class="col-md-4">
<div id="serviceNameId" class="form-group">
										<label>Service Name:</label> <select
											class="form-control select2me" id="serviceId" name="serviceId" >
											<option value="">Select</option>
									<c:forEach items="${service}" var="element">
										<option value="${element}">${element}</option>
									</c:forEach>
										</select>
									</div>
</div>

</div>
<div class="row" style="margin-left: 400px;">
									<div class="col-md-4">
										<button type="button" id="viewId" style="margin-left: 10px;"
											 name="viewId" class="btn yellow" onclick="view()">
											<b>View</b>
										</button>
									</div>
									</div>
<br>
<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
<table class="table table-striped table-hover table-bordered"
									id="sample_2"  >
									<thead>
										<tr>
										    <th>S.No</th>
										    <th>Service Name</th>
											<th>Requester</th>
											<th>Provider</th>
											<th>Exception</th>
											<th>Exception time</th>
											<th>Notified</th>
											<th>Acknowledged Date</th>
											<th>Acknowledged By</th>
											<th>Acknowledged Message</th>
										</tr>
									</thead>
									<tbody id="updateAckExcpId" >

									</tbody>
								</table>
</div>

</div><!-- tab-content -->
</div>
</div><!-- modal-body -->
</div><!-- portlet-body -->
</div>
</div>

<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<h5 class="modal-title" id="">Acknowledged Message</h5>
			</div>
			<div class="modal-body">	
			<form action="" method="post" id="UpdateAckMsg">
				<div class="inline-labels">
   
    <div class="form-group">
  <label for="comment">Acknowledged Message:-</label>
  <textarea class="form-control" rows="5" id="AckMsgId" name="AckMsgId"></textarea>
  <input  type="text" id="serExcId" hidden="true">
</div>
    </div>
		
		<div class="modal-footer">
						<button class="btn blue pull-left" id="Savebt" type="button" data-dismiss="modal"
							value="save"  hidden="true" onclick="return save(this.value)">Save</button>
						<button class="btn red pull-right" type="button" 
							data-dismiss="modal">Cancel</button>
					</div>
		</form>
		</div>
		</div>
</div>
</div>





