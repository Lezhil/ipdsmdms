<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			
			var zoneVal="${zoneVal}";
			var circleVal="${circleVal}";
			
			//alert(zoneVal);
	       if(zoneVal!='' && circleVal!=''){
	    	   
				$('#zone').find('option').remove().end().append('<option value="'+ zoneVal +'">'+ zoneVal +'</option>');
				$("#zone").val(zoneVal).trigger("change");
				
				setTimeout(function(){ 
					$('#circle').find('option').remove().end().append('<option value="'+ circleVal +'">'+ circleVal +'</option>');
					$("#circle").val(circleVal).trigger("change");
				}, 200);
			} else{
				$("#zone").val("").trigger("change");
			}
			
	       $('#MDMSideBarContents,#ruleConfig,#dataValidId').
			addClass('start active ,selected');
		   $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
			.removeClass('start active ,selected');
			
		});
</script>
<script type="text/javascript">
var mtrNum;
function mtrDetails(mtrNo)
{
	mtrNum=mtrNo;
	window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	 /* window.open("./viewFeederMeterInfo?mtrno="+ mtrNo,"_blank"); */
}


function eventDetails()
{
	alert(mtrNum);
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getEventData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_2').dataTable().fnClearTable();
   		 	$('#eventTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_2');
		}  
	  }); 
}


function instansDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getInstansData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_3').dataTable().fnClearTable();
   		 	$('#instantsTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_3');
		}  
	  }); 
}

function loadSurveyDetails()
{
	var mtrNo=mtrNum;
	$.ajax({
    	url : './getLoadSurveyData/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		var html="";
   		 	for(var i=0;i<data.length; i++)
			{
				var resp=data[i];
				 html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" </tr>";
	   		}
   		 $('#sample_4').dataTable().fnClearTable();
   		 	$('#loadSurveyTR').html(html);
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_4');
		}  
	  }); 
}

</script>



<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Estimates & Edits
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
								
									<td>
									<select class="form-control select2me" id="type" name="type" >
										<option value="" disabled="disabled"> Select Estimation Type</option>
										<option value='i'>Current</option>
										<option value='v'>Voltage</option>
									</select>
									
									</td>
									
									<td>
									<div class="input-group input-large date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" placeholder="From Date"
												class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span> <input type="text"
												placeholder="To Date" class="form-control"
												value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />"
												data-date="${currentDate}" data-date-format="yyyy-mm-dd" autocomplete="off"
												data-date-viewmode="years" name="toDate" id="toDate">
										</div>
									</td>
									
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getFeeder()" name="viewFeeder"
											class="btn yellow">
											<b>Check</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
						
					<p>&nbsp;</p>	
					</div>
				
					
					<br>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<br>
				
					
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
								<th>Meter No</th>
								<th>Meter Type</th>
								<th>Read Time</th>
								<th>Ir</th>
								<th>Iy</th>
								<th>Ib</th>
								<th>Avg Current</th>
								<th>Vr</th>
								<th>Vy</th>
								<th>Vb</th>
								<th>Avg Voltage</th>
								<th>KWh</th>
								<th>KVAh</th>
								<th>Action</th>
						   </tr>
						</thead>
						<tbody id="updateMaster">
							 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>

<div class="modal fade" id="basic" tabindex="-1" role="basic"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" ><font style="font-weight: bold; color: white;">Estimation Details</font></a></h4>
							</div>
							<div class="modal-body">

								<form action="#" id="changeEstimation">
									<input type="text" id="column" name="column" hidden="true">
									<input type="text" id="colid" name="colid" hidden="true">
									<div class="row">
										<div class="form-group col-md-6">
											<label>Meter No</label>
											<div class="input-group">
												<input type="text" class="form-control" placeholder="Meter No" id="mtrno" name="mtrno" readonly="readonly">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>Read Time</label>
											<div class="input-group">
												<input type="text" class="form-control" id="read_time"  name="read_time" readonly="readonly"
													placeholder="">
											</div>
										</div>
										
									</div>
									
									<div class="row">
										<div class="form-group col-md-3">
											<label>Ir</label>
											<div class="input-group">
												<input type="text" class="form-control" id="i_r"  name="i_r" readonly="readonly"
													placeholder="">
											</div>
										</div>
									
										<div class="form-group col-md-3">
											<label>Iy</label>
											<div class="input-group">
												<input type="text" class="form-control" id="i_y" name="i_y" readonly="readonly"
													placeholder="">
											</div>
										</div>
										<div class="form-group col-md-3">
											<label>Ib<font color="red">*</font></label>
											<div class="input-group">
												<input type="text" class="form-control" id="i_b" name="i_b" readonly="readonly" 
													placeholder="">
											</div>
										</div>
										
										<div class="form-group col-md-3">
											<label>Agv Current<font color="red">*</font></label>
											<div class="input-group">
												<input type="text" class="form-control" id="avg_current" name="avg_current" readonly="readonly" 
													placeholder="">
											</div>
										</div>
										
									</div>
									
									<div class="row">
										<div class="form-group col-md-3">
											<label>Vr</label>
											<div class="input-group">
												<input type="text" class="form-control" id="v_r"  name="v_r" readonly="readonly"
													placeholder="">
											</div>
										</div>
									
										<div class="form-group col-md-3">
											<label>Vy</label>
											<div class="input-group">
												<input type="text" class="form-control" id="v_y" name="v_y" readonly="readonly"
													placeholder="">
											</div>
										</div>
										<div class="form-group col-md-3">
											<label>Vb<font color="red">*</font></label>
											<div class="input-group">
												<input type="text" class="form-control" id="v_b" name="v_b" readonly="readonly" 
													placeholder="">
											</div>
										</div>
										
										<div class="form-group col-md-3">
											<label>Avg Voltage<font color="red">*</font></label>
											<div class="input-group">
												<input type="text" class="form-control" id="avg_voltage" name="avg_voltage" readonly="readonly" 
													placeholder="">
											</div>
										</div>
										
									</div>
									

								</form>
							<span id="info_load"></span>


							</div>
							<div class="modal-footer">
								<button type="button" class="btn dark btn-outline"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn green" onclick="saveEstimationChanges();">Save changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>

<script>

function getFeeder() {
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var type= $('#type').val();
	$('#updateMaster').empty();
	$('#imageee').show();
	$.ajax({
		url : './getCurrentEstimationData',
		type : 'POST',
		data : {
			fromDate : fromDate,
			toDate : toDate,
			type : type
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				var html = "";
				
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					//alert(element);
					
					/* for(var j=0;j<element.length;j++){
						if(element[j]==null){
							element[j]="";
						}
					} */
					
					
					
					html += "<tr>" 
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[36]+ "</td>";
					
					if(element[3]!=null){
						html +="<td>"
							+ moment(element[3]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					}
					
					html+= "<td>"+ element[6]+ "</td>"
					+ "<td>"+ element[7]+ "</td>"
					+ "<td>"+ element[8]+ "</td>"
					+ "<td>"+ element[32]+ "</td>"
					
					+ "<td>"+ element[9]+ "</td>"
					+ "<td>"+ element[10]+ "</td>"
					+ "<td>"+ element[11]+ "</td>"
					+ "<td>"+ element[31]+ "</td>"
					
					+ "<td>"+ element[12]+ "</td>"
					+ "<td>"+ element[13]+ "</td>";
					
					if(type=='i'){
						if(element[6]==null || parseFloat(element[6])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[6]+"\",\"i_r\");'>Estimate</a>"
								+ "</td>";
						} else if(element[7]==null || parseFloat(element[7])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[7]+"\",\"i_y\");'>Estimate</a>"
								+ "</td>";
						}else if (element[8]==null || parseFloat(element[8])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[8]+"\",\"i_b\");'>Estimate</a>"
								+ "</td>";
						}
					} else if(type=='v'){
						if(element[9]==null || parseFloat(element[9])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[9]+"\",\"v_r\");'>Estimate</a>"
								+ "</td>";
						} else if(element[10]==null || parseFloat(element[10])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[10]+"\",\"v_y\");'>Estimate</a>"
								+ "</td>";
						}else if (element[11]==null || parseFloat(element[11])<0){
							html +="<td>"
								+ "<a href='#' onclick='getEstimatePop(\""+element[0]+"\",\""+element[11]+"\",\"v_b\");'>Estimate</a>"
								+ "</td>";
						}
					
					}
					
					
					html+= " </tr>";
				}
				$('#imageee').hide();
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').append(html);
				loadSearchAndFilter('sample_1');
			} else {
				$('#imageee').hide();
				bootbox.alert("No Data Found for this Criteria.");
			}

		}
	});
	
}


function getEstimatePop(id,val,type) {
	$('#imageee').show()
	resetModel();
	
	$.ajax({
		url : "./getLoadIntervaldataById",
		type : "GET",
		data : {
			id: id,
			type : type
		},
		dataType : "json",
		async : false,
		success : function(response) {
			var element1 = response[0];
			var element2 = response[1];
			//alert(element1);
			$('#column').val(type);
			$('#colid').val(id);
			$('#mtrno').val(element1[1]);
			$('#i_r').val(element1[3]);
			$('#i_y').val(element1[4]);
			$('#i_b').val(element1[5]);
			$('#v_r').val(element1[7]);
			$('#v_y').val(element1[8]);
			$('#v_b').val(element1[9]);
			$('#read_time').val(moment(element1[2]).format("YYYY-MM-DD HH:mm:ss"));
			$("#"+type).val(element1[6]);
			$("#"+type).attr("readonly", false);
			$("#"+type).css("color", "red"); 
			var html="Value Estimated Based on Last Interval Values.<br> Date : "+moment(element2[2]).format("YYYY-MM-DD HH:mm:ss")
			+" | Ir : "+element2[3]+", Iy : "+element2[4]+", Ib : "+element2[5]+" | Vr : "+element2[7]+", Vy : "+element2[8]+", Vb : "+element2[8]+""
			
			$("#info_load").html(html);
			$('#basic').modal('show');
			$('#imageee').hide();
		}

	
	});
	
	
}

function resetModel() {
	$('#column').val("");
	$('#colid').val("");
	$('#read_time').val("");
	$('#mtrno').val("");
	$('#i_r').val("");
	$('#i_y').val("");
	$('#i_b').val("");
	$('#v_r').val("");
	$('#v_y').val("");
	$('#v_b').val("");
	$("#info_load").html("");
	$("#i_r").attr("readonly", true);
	$("#i_r").css("color", "black"); 
	$("#i_y").attr("readonly", true);
	$("#i_y").css("color", "black"); 
	$("#i_b").attr("readonly", true);
	$("#i_b").css("color", "black"); 
	$("#i_b").attr("readonly", true);
	$("#i_b").css("color", "black"); 
	
	$("#v_r").attr("readonly", true);
	$("#v_r").css("color", "black"); 
	$("#v_y").attr("readonly", true);
	$("#v_y").css("color", "black"); 
	$("#v_b").attr("readonly", true);
	$("#v_b").css("color", "black"); 
	$("#v_b").attr("readonly", true);
	$("#v_b").css("color", "black"); 
}

function saveEstimationChanges() {
	
	var col=$('#column').val();
	var colid=$('#colid').val();
	var val=$("#"+col).val();
	//alert(col+"---"+colid+"---"+val);
	
	$.ajax({
				url : "./saveEstimationChanges",
				type : "GET",
				dataType : "text",
				data : {
					col : col,
					colid : colid,
					val : val
				},
				async : false,
				success : function(response) {
					if(response=='success'){
						$('#basic').modal('hide');
						bootbox.alert("Estimation Saved Successfully");
						getFeeder();
					} else{
						bootbox.alert("Oops!! Something went wrong!");
					}

				}

			}); 

}
</script>
<style>
.select2-choice{
	width: 180px;
}


</style>