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
			
	       $('#MDMSideBarContents,#vee,#ruleConfig').
			addClass('start active ,selected');
		   $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
			.removeClass('start active ,selected');
			
		});
$(document).ready(function() {
	$('.js-example-basic-multiple').select2();

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
						<i class="fa fa-edit"></i>VEE Rules Configuration
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
											<option value="" disabled="disabled" selected="selected"> Select Validation Type</option>
											<option value='revenue_protection'>Revenue Protection</option>
											<option value='high_consumption'>High Consumption</option>
											<option value='low_consumption'>Low Consumption</option>
											
									</select>
									</td>
									
									<td id="subdivTd"><select
										class="form-control select2me" id="parameters"
										name="parameters">
											<option value="" disabled="disabled" selected="selected">Parameters</option>
											<option value="percentage">Consumption Percentage</option>
									</select></td>
									
									<td id="subdivTd"><select
										class="form-control select2me" id="condition"
										name="condition">
											<option value="" disabled="disabled" selected="selected">Condition</option>
											<option value=">">Greater Than</option>
											<option value=">=">Greater Than Equal to</option>
											<option value="&lt;">Less Than</option>
											<option value="&lt;=">Less Than Equal to</option>
											<option value="=">Equal to</option>
											<option value="±">±</option>
									</select></td>
									
									<td>
									<input type="text" class="form-control" id="param_val" placeholder="Enter Value" style="width: 200px;">
									
									</td>
									
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return saveConfig()" name="viewFeeder"
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
				</div>
			</div>
</div></div>
<div class="row">
		<div class="col-md-12">
<div class="portlet box blue">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-edit"></i>VEE Rules Configuration
		</div>
		<div class="tools">
			<a href="javascript:;" class="collapse"></a> <a
				href="javascript:;" class="remove"></a>
		</div>
	</div>

	<div class="portlet-body">
		<div class="row" style="margin-left: -1px;margin-right: -1px;">
			
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
								<th>VEE Rule</th>
								<th>Parameters</th>
								<th>Condition1</th>
								<th>Condition2</th>
								<th>Group Selection</th>
								<th>Real Time Enable/Disable</th>
							</tr>
						</thead>
						<tbody id="ruleListBody">
							 <c:forEach var="element" items="${ruleList}">
								 <tr>
								 	<td>${element.name}</td> 
									<td>${element.parameters}</td>
									<td>${element.con1}</td>
									<td>${element.con2}</td>
									<td>
									<select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="groupno" multiple="multiple" autofocus="autofocus">
									
									
										<option value="group1">group1</option>
										<option value="group2">group2</option>
										<option value="group3">group3</option>
										<option value="group4">group4</option>
									

							</select>
									</td>
									<td>
									<div class="make-switch" data-on-label="&nbsp;Enable&nbsp;" data-off-label="&nbsp;Disable&nbsp;">
												<input type="checkbox" checked class="toggle"/>
											</div>
									</td>
								</tr>
							 </c:forEach>
						</tbody>
					</table>
		
		</div>
	</div>
</div>
			
			
		</div>
	</div>
	
</div>



<script>

function saveConfig() {
	var type = $('#type').val();
	var parameters = $('#parameters').val();
	var condition = $('#condition').val();
	var param_val = $('#param_val').val();
	
	//alert(type+"  ---  "+parameters+"  ---  "+condition+"  ---  "+param_val);
	
	if(type==null || type==""){
		bootbox.alert("Please Select Rules Type.");
		return false;
	}
	if(parameters==null || parameters==""){
		bootbox.alert("Please Select Parameters.");
		return false;
	}
	if(condition==null || condition==""){
		bootbox.alert("Please Select Condition.");
	
		return false;
	}
	if(param_val==null || param_val==""){
		bootbox.alert("Please Enter Value of the condition.");
		return false;
	}
	
	$('#imageee').show();
	
	$.ajax({
		url : './updateVeeRulesConfigs',
		type : 'POST',
		data : {
			type : type,
			parameters : parameters,
			condition : condition,
			param_val : param_val,
		},
		dataType : 'TEXT',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				
				//alert(response);
				if(response=='success'){
					bootbox.alert("Rules Updated Successfully.");
				} else{
					bootbox.alert("Rules Updated Failed.");
				}
				
				$('#imageee').hide();
			} else {
				$('#imageee').hide();
				bootbox.alert("Oops Something went wrong!!");
			}

		},
		complete : function(){
			getAllRules();
		}
	}); 
	
}

function getAllRules() {
	$.ajax({
		url : './getAllVeeRules',
		type : 'POST',
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				//alert(response);
				var html="";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>" 
						 + "<td>"+ element.name + "</td>"
						 + "<td>"+ element.parameters + "</td>"
						 + "<td>"+ element.con1+"</td>"
						 + "<td>"+ element.con2+"</td>"
						 +"</tr>";
				}
				//alert(html);
				$('#sample_1').dataTable().fnClearTable();
				$("#ruleListBody").append(html);
				loadSearchAndFilter('sample_1');
			} 

		}
	}); 
}
</script>
<style>
.select2-choice{
	width: 200px;
}


</style>