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
			
	       $('#MDMSideBarContents,#dataValidId,#veeValidationCheck').addClass('start active ,selected');
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
	//alert(mtrNum);
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
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>VEE Validation Checkes
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
									<td><select class="form-control select2me"
										name="zone" id="zone" onchange="showCircle(this.value);">
											<option value="">Zone</option>
											<option value="">ALL</option>
											<c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="circleTd"><select
										class="form-control select2me" id="circle"
										name="circle">
											<option value="">Circle</option>
											<option value="">ALL</option>
											<c:forEach items="${circleList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="divisionTd"><select
										class="form-control select2me" id="division"
										name="division">
											<option value="">Division</option>
											<option value="">ALL</option>
											<c:forEach items="${divisionList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="subdivTd"><select
										class="form-control select2me" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value="">ALL</option>
											<c:forEach items="${subdivList}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>
										
									<td>
									<select class="form-control select2me" id="type" name="type" >
											<option value="" disabled="disabled"> Select Validation Type</option>
											<option value='revenue_protection'>Revenue Protection</option>
											<option value='high_consumption'>High Consumption</option>
											<option value='low_consumption'>Low Consumption</option>
											<option value='zero_consumption'>Zero Consumption</option>
											<option value='negative_consumption'>Negative Consumption</option>
											<option value='maximum_demand'>Maximum Demand</option>
											<option value='cum_decrements'>CUM Decrements</option>
											<option value='cum_increments'>CUM Read Increments</option>
											<option value='f_o_rdate'>Future or Old read Dates</option>
											<option value='m_d_d_exceeds'>Meter Dials Digit Exceeds</option>
									</select>
								
									
									</td>
									
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getFeeder()" name="viewFeeder"
											class="btn yellow">
											<b>Run Validation</b>
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
								<th>Account No.</th>
								<th>K No</th>
								<th>Meter No</th>
								<th>Consumer Name</th>
								<th>Sanction Load</th>
								<th>Contract Demand</th>
								<th>Billing Month</th>
								<th>Current KWH</th>
								<th>Previous KWH</th>
								<th>Current Consumption</th>
								<th>Previous Consumption</th>
								<th>Percentage</th>
								<th id="kvamf">KVA*MF</th>
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



<script>

function showCircle(zone) {
	$
			.ajax({
				url : './showCircleMDAS' + '/' + zone,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control' type='text'><option value=''>Circle</option><option value=''>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option  value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
				}
			});
}

function showDivision(circle) {
	var zone = $('#zone').val();
	$
			.ajax({
				url : './showDivisionMDAS' + '/' + zone + '/' + circle,
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(response) {
					var html = '';
					html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control' type='text'><option value=''>Division</option><option value=''>ALL</option>";
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
	//document.getElementById("sdoCode").reset();
	//$('#sdoCode').val('');
	$('#sdoCode').empty();
	//$('#sdoCode').html('');
	$('#sdoCode').find('option').remove();
	$('#sdoCode').append($('<option>', {
		value : "",
		text : "Sub-Division"
	}));
	$('#sdoCode').append($('<option>', {
		value : "",
		text : "All"
	}));
	$.ajax({
		url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
				+ division,
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response1) {
			

			/* var html='';
			html+="<select id='sdoCode' name='sdoCode' class='form-control select2me input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>"; */
			for (var i = 0; i < response1.length; i++) {
				//var response=response1[i];
				/* html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>"; */

				$('#sdoCode').append($('<option>', {
					value : response1[i],
					text : response1[i]
				}));
			}
			/* html+="</select><span></span>";
			$("#subdivTd").html(html); */
			//$('#subdiv').select2();
		}
	});
}


function getFeeder() {
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var subdiv = $('#sdoCode').val();
	var type = $('#type').val();
	
	if(type=='cum_decrements' || type=='f_o_rdate' || type=='m_d_d_exceeds' || type=='cum_increments'){
		bootbox.alert("No Data Found for this Criteria.");
		return;
	}
	
	$('#updateMaster').empty();
	$('#imageee').show();
	$.ajax({
		url : './getVeeValidationChecked',
		type : 'POST',
		data : {
			zone : zone,
			circle : circle,
			division : division,
			subdiv : subdiv,
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
					
					html += "<tr>" 
					+ "<td>"+ element[1]+ "</td>"
					+ "<td>"+ element[2]+ "</td>"
					+ "<td>"+ element[0]+ "</td>"
					+ "<td>"+ element[3]+ "</td>"
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[5]+ "</td>"
					
					+ "<td>"+ element[7]+ "</td>"
					+ "<td>"+ element[8]+ "</td>"
					+ "<td>"+ element[9]+ "</td>"
					+ "<td>"+ element[10]+ "</td>"
					+ "<td>"+ element[11]+ "</td>"
					+ "<td>"+ element[12]+ "</td>";
					
					
					var kva=parseFloat(element[13]);
					var mf=parseFloat(element[18]);
					html +="<td>"+ parseFloat(kva*mf).toFixed(2)+ "</td>";
					
					
					/* if(element[8]!=null){
						html +="<td>"
							+ moment(element[8]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					} */
					
					
					
					html+= " </tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				$('#imageee').hide();
			} else {
				$('#imageee').hide();
				bootbox.alert("No Data Found for this Criteria.");
			}

		}
	});
	
}


function updateFdrTable() {

	var imeiVal = $('#IMEI').val();
	var aurl="";
	//alert(imeiVal.length);
	if(imeiVal.length<10){
		aurl="./getFeedersBasedOnMeterNoMDAS/" + imeiVal;
	} else {
		aurl="./getFeedersBasedOnaccno/" + imeiVal;
	}
	
	/* alert('IMEI====='+imeiVal); */
	$
			.ajax({
				url : aurl,
				type : "GET",
				dataType : "json",
				async : false,
				success : function(response) {
					var x = JSON.stringify(response);
					/* alert('result====='+x); */

					if (response.length == 0 || response.length == null) {
						bootbox
								.alert("Data Not Available for this IMEI No. / Meter No : "
										+ imeiVal);
						$('#updateMaster').empty();
					} else {
						var html = "";
						var select=new Array();
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];

							

								html += "<tr>" + "<td>"
										+ "<a href='./modemDetailsInactiveMDAS?modem_sl_no="+resp.modem_sl_no+"&mtrno="+resp.mtrno+"&substation="+resp.substation+"' style='color:blue;'>"+resp.modem_sl_no+"</a>"
										+ "</td>"
										+ "<td>"
										+ "<a style='color:blue;' onclick='return mtrDetails(\""+resp.mtrno+"\");'>"+resp.mtrno+"</a>"
										+ "</td>"
										+ "<td>"+ resp.customer_name+ "</td>"
										+ "<td>"+ resp.accno+ "</td>"
										+ "<td>"+ resp.substation+ "</td>"
										+ "<td>"+ resp.subdivision+ "</td>"
										+ "<td>"+ resp.division+ "</td>"
										+ "<td>"+ resp.circle+ "</td>";
										
									if(resp.last_communicated_date!=null){
										html +="<td>"
												+ moment(resp.last_communicated_date).format("YYYY-MM-DD HH:mm:ss")
												+ "</td>";
									} else{
										html +="<td></td>";
									}
										
										html+= " </tr>";
							
						}

						/* $('#sample_editable_1').dataTable().fnClearTable(); */
							$('#sample_1').dataTable().fnClearTable();
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_1');
						
						
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