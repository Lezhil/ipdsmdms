<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"	type="text/javascript"></script>
<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>	 -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>

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

						$('#MDMSideBarContents,#mdmDashBoardId,#consumptionLoadAnalysisId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_2');
						loadSearchAndFilter('sample_3');
						getConsumercategory();
						$('#Consumer').click();
						$('#Consumer').click();
						tabShow('meterlocation');
						
					});
</script>
<script>

function showCircle(zone)
{
	
	$.ajax({
		//url : './showCircleMDM' + '/' + zone ,
		url : './showCircleMDAS' + '/' + zone,
	    	type:'GET',
	    	
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<option value=''></option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				
				$("#circle").html(html);
				$('#circle').select2();
	    	}
		});
}

function showDivision(circle)
{
	var zone = $('#zone').val();
	$
			.ajax({
				url : './showDivisionMDAS' + '/' + zone + '/' + circle,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#division").html(html);
				$('#division').select2();
	    	}
		});
}

function showSubdivByDiv(division)
{
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	$.ajax({
		url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
		+ division,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
				for( var i=0;i<response1.length;i++)
				{
					//var response=response1[i];
					html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#sdoCode").html(html);
				$('#sdoCode').select2();
	    	}
		});
}
	function getConsumercategory()
	{	
		 $('#consumerCatgryId').find('option').remove();
		$('#consumerCatgryId').append($('<option>', {
			value : "",
			text : "Select Consumer Category"
		})); 
		
		$.ajax({
			url : "./getConsumercategory",
		    	type:'POST',
		    	success:function(response)
		    	{
		    		 for(var i=0;i<=response.length;i++){
						 $('#consumerCatgryId').append($('<option>', {
								value : response[i].trim(),
								text : response[i].trim()
							}));	

				}
		    	}
			});
	}
	
	function getconsumerData(){
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = $('#division').val();
		var sdoCode = $('#sdoCode').val();
		var consumerCatgryId = $('#consumerCatgryId').val();
		var accnoId = $('#accnoId').val();
		var knoId = $('#knoId').val();
		var ConsumermtrnoId = $('#ConsumermtrnoId').val();
 
		  if (zone == "") {
			bootbox.alert("Please Select zone");
			return false;
		}  

		 if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (sdoCode == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		}  

			$
			.ajax({
			    url : './getconsumerData',
		    	type:'POST',
		    	data : {
			    	zone :zone,
			    	circle:circle,
			    	division:division,
			    	sdoCode:sdoCode,
			    	consumerCatgryId:consumerCatgryId,
			    	accnoId:accnoId,
			    	knoId:knoId,
			    	ConsumermtrnoId:ConsumermtrnoId,
			    	
			    },
				dataType : 'json',
		    	success:function(response)
		    	{
				      var html="";
					  for (var i = 0; i < response.length; i++) 
					  {
						  var resp=response[i];
						 // alert(resp[0]);
						   html+="<tr>"+
						   "<td><input id=Consucheck name=Consucheck type=checkbox class=checkboxes value="+resp[5]+" /></td>"+
						  "<td>"+resp[0]+" </td>"+
						  "<td>"+resp[1]+" </td>"+
						  "<td>"+resp[2]+" </td>"+
						  "<td>"+resp[3]+" </td>"+
						  "<td>"+resp[4]+" </td>"+
						  "<td>"+resp[5]+" </td>"+
						  "</tr>";				
					   }                                
					  $('#sample_1').dataTable().fnClearTable();
					  $("#updateConsumerId").html(html);
					  loadSearchAndFilter('sample_1'); 
		    						 
				},
				complete: function()
				{  
					loadSearchAndFilter('sample_1'); 
				}
			});

	}
	function getDTData(){
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		var division=$('#division').val();
		var sdoCode = $('#sdoCode').val();
		var crossdt = $("input[name='asg_dt_radio']").is(":checked");
		var DtcodeId=$('#DtcodeId').val();
		var dtmtrnoId=$('#dtmtrnoId').val();
		
		if(zone==""){
			bootbox.alert("Please Select Zone");
		}
		if(circle==""){
			bootbox.alert("Please Select Circle");
		}
		if(division==""){
			bootbox.alert("Please Select Division")
		}
		if(sdoCode==""){
			bootbox.alert("Please Select SubDivision")
		}  
		$.ajax({
			url:'./getdtdata',
			type:'POST',
			data:{
				zone:zone,
				circle:circle,
		    	division:division,
		    	sdoCode:sdoCode,
		    	crossdt:crossdt,
		    	DtcodeId:DtcodeId,
		    	dtmtrnoId:dtmtrnoId,
			},
			datatype:'Json',
			success:function(response){
				var html="";
				for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
						  alert(resp[0]);
						   html+="<tr>"+
						   "<td><input id=dtcheck name=dtcheck type=checkbox class=checkboxes value="+resp[3]+" /></td>"+
						  "<td>"+resp[3]+" </td>"+
						  "<td>"+resp[4]+" </td>"+
						  "<td>"+resp[5]+" </td>"+
						  "<td>"+resp[6]+" </td>"+
						  "<td>"+resp[7]+" </td>"+
						  "<td>"+resp[8]+" </td>"+
						  "</tr>";	
				   }  
				 $('#sample_2').dataTable().fnClearTable();
				  $("#updateDtId").html(html);
				  loadSearchAndFilter('sample_2'); 
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_2'); 
			}
		});
		
	}
	
	function getFeederdata(){
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		var division =$('#division').val();
		var sdoCode=$('#sdoCode').val();
		var FedrcodeId=$('#FedrcodeId').val();
		var crossfeeder = $("input[name='asg_fedr_radio']").is(":checked");
		var fedrmtrnoId=$('#fedrmtrnoId').val();

		if(zone==""){
			bootbox.alert("Please Select Zone");
		}
		if(circle==""){
			bootbox.alert("Please Select Circle");
		}
		if(division==""){
			bootbox.alert("Please Select Division")
		}
		if(sdoCode==""){
			bootbox.alert("Please Select SubDivision")
		}  
		$.ajax({
			
			url:'./getfeederdata',
			type:'POST',
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdoCode:sdoCode,
				FedrcodeId:FedrcodeId,
				crossfeeder:crossfeeder,
				fedrmtrnoId:fedrmtrnoId,
			},
			datatype:'json',
			success:function(response){
				var html="";
				for(var i=0;i<response.length;i++){
					var resp=response[i];
					html+="<tr>"+
					   "<td><input id=fdrcheck name=fdrcheck type=checkbox class=checkboxes value="+resp[0]+" /></td>"+
					"<td>"+resp[0]+"</td>"+
					"<td>"+resp[1]+"</td>"+
					"<td>"+resp[3]+"</td>"+
					"<td>"+resp[2]+"</td>"+
					"<td>"+resp[4]+"</td>"+
					"</tr>";
				}
				$('#sample_3').dataTable().fnClearTable();
				  $("#updatefedrId").html(html);
				  loadSearchAndFilter('sample_3'); 
				
			},
			complete: function()
			{  
				loadSearchAndFilter('sample_3'); 
			}
		});
	}
	function hidefeeddt(x) {
		if (x.checked) {

			$('#consumerCatgryId').val('');
			$('#accnoId').val('');
			$('#knoId').val();
			$('#ConsumermtrnoId').val('');
			$("#showConsumerId").show();
			$("#showFeeder").hide();
			$("#showDT").hide();
		}

	}

	function hideconfeed(x) {
		if (x.checked) {
			$('#DtcodeId').val('');
			$('#dtmtrnoId').val('');
			$("#showConsumerId").hide();
			$("#showFeeder").hide();
			$("#showDT").show();
		}
	}
	function hidecondt(x) {
		if (x.checked) {
			$('#FedrcodeId').val('');
			$('#fedrmtrnoId').val('');
			$("#showConsumerId").hide();
			$("#showFeeder").show();
			$("#showDT").hide();
		}
	}
	
</script>


<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Consumption/Load Analysis
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
						<li class="active"><a href="#tab_1_1" data-toggle="tab" onclick="tabShow('meterlocation')">Metering
								Locations </a></li>
						<li><a href="#tab_1_2"  data-toggle="tab" onclick="tabShow('time')">Time
								Period</a></li>
						<li><a href="#tab_1_3"  data-toggle="tab" onclick="tabShow('graph')">Consumption/Load
								Analysis</a></li>

					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab_1_1" >
							<hr />
							<div class="form-group">
								<div class="mt-radio-inline">
									<label class="mt-radio"> <input type="radio"
										name="optionsRadios" id="Consumer" value="Consumer"
										onchange="hidefeeddt(this)" checked> Consumer <span></span></label>
									<label class="mt-radio"> <input type="radio"
										name="optionsRadios" id="DT" value="DT"
										onchange="hideconfeed(this)"> DT <span></span></label> <label
										class="mt-radio"> <input type="radio"
										name="optionsRadios" id="feeder" value="Feeder"
										onchange="hidecondt(this)"> Feeder <span></span></label>
								</div>
							</div>
						<div class="row" style="margin-left: -1px;">
							<div class="col-md-3">
									<div id="zoneTd" class="form-group">
										<label>Zone:</label>
										<select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
									</div>
								</div>
								<div class="col-md-3">
									<div id="circleTd" class="form-group">
										<label>Circle:</label> <select class="form-control select2me"
											id="circle" name="circle" onchange="showDivision(this.value);">
											
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div id="divisionTd" class="form-group">
										<label>Division:</label> <select
											class="form-control select2me" id="division" name="division" onchange="showSubdivByDiv(this.value)">
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div id="subdivTd" class="form-group">
										<label>Sub-Division:</label> <select
											class="form-control select2me" id="sdoCode" name="sdoCode">
										</select>
									</div>
								</div>
								
							</div> 
							<div id="showConsumerId">
								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3" >
										<div class="form-group">
											<label>Consumer Category:</label> <select
												class="form-control select2me" id="consumerCatgryId"
												name="consumerCatgryId" >
												<option value="">Select Consumer Category</option>
											</select>
										</div>
									</div>

									<div class="col-md-3">
										<div class="form-group">
											<label>Account Number:</label> <input type="text"
												id="accnoId" class="form-control placeholder-no-fix"
												placeholder="Enter Account No." name="accnoId"
												maxlength="12"></input>
										</div>
									</div>
								<div class="col-md-3">
										<div class="form-group">
											<label>K No:</label> <input type="text" id="knoId"
												class="form-control placeholder-no-fix"
												placeholder="Enter K No." name="knoId" maxlength="12"></input>
										</div>
									</div>
								
								
									<div class="col-md-3">
										<div class="form-group">
											<label>Meter Sr.No.</label> <input type="text"
												id="ConsumermtrnoId" class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr.No." name="ConsumermtrnoId"
												maxlength="12"></input>
										</div>
									</div>
									</div>
									</br>
									<div class="row" style="margin-left: -1px;">
									<div class="col-md-4">
										<button type="button" id="viewId" style="margin-left: 10px;"
											 name="viewId" class="btn yellow" onclick="getconsumerData()">
											<b>View</b>
										</button>
									</div>
									</div>
								<hr />
								<table class="table table-striped table-hover table-bordered"
									id="sample_1">
									<thead>
										<tr>
										   <th>Select</th>
											<th>Subdivision</th>
											<th>Consumer category</th>
											<th>Consumer Name</th>
											<th>Consumer Account No</th>
											<th>Consumer K number</th>
											<th>Meter Serial Number</th>
										</tr>
									</thead>
									<tbody id="updateConsumerId">

									</tbody>
								</table>
							</div>
							<div id="showDT" style="display: none">

								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3">
										<div class="form-group">
									
											<label class="control-label">DT Code</label> <input
												type="text" id="DtcodeId"
												class="form-control placeholder-no-fix"
												placeholder="Enter DT Code." name="DtcodeId" maxlength="12"></input>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cross
												DT : </label><br>
											<div class="make-switch" data-on="success" data-off="warning"
												style="position: relative; left: 11%;">
												<input type="checkbox" id="asg_dt_radio" name="asg_dt_radio"
													checked="checked" class="toggle" />
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Meter Sr No</label> <input
												type="text" id="dtmtrnoId"
												class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr No." name="dtmtrnoId"
												maxlength="12"></input>
										</div>
									</div>
				<button type="button" id="viewDTData" style="margin-left: 480px;" onclick="getDTData()" name="viewDTData" class="btn yellow"> <b>View</b></button>
 								</div>
								<hr />
								<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
										     <th>Select</th>
											<th>Subdivision</th>
											<th>DT Name</th>
											<th>DT Capacity</th>
											<th>Cross DT [Yes/No]</th>
											<th>DT Code</th>
											<th>Meter Serial Number</th>
										</tr>
									</thead>
									<tbody id="updateDtId">

									</tbody>
								</table>
							</div>

							<div id="showFeeder" style="display: none">

								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Feeder Code</label> <input
												type="text" id="FedrcodeId"
												class="form-control placeholder-no-fix"
												placeholder="Enter Feeder Code." name="FedrcodeId"
												maxlength="12"></input>
												
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cross
												FEEDER : </label><br>
											<div class="make-switch" data-on="success" data-off="warning"
												style="position: relative; left: 11%;">
												<input type="checkbox" id="asg_fedr_radio"
													name="asg_fedr_radio" checked="checked" class="toggle" />
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Meter Sr No</label> <input
												type="text" id="fedrmtrnoId"
												class="form-control placeholder-no-fix"
												placeholder="Enter Meter Sr No." name="fedrmtrnoId"
												maxlength="12"></input>
										</div>
									</div>
						<button type="button" id="viewFeederData" style="margin-left: 480px;" onclick="getFeederdata()" name="viewFeederData" class="btn yellow"> <b>View</b></button>
								</div>

								<hr />
								<table class="table table-striped table-hover table-bordered"
									id="sample_3">
									<thead>
										<tr>
										    <th>Select</th>
											<th>Subdivision</th>
											<th>Feeder Name</th>
											<th>Cross DT [Yes/No]</th>
											<th>Feeder Code</th>
											<th>Meter Serial Number</th>
										</tr>
									</thead>
									<tbody id="updatefedrId">

									</tbody>
								</table>
							</div>
						</div>


						<div class="tab-pane active" id="tab_1_2"  >
							<div class="row">
								<div class="col-md-6" >
							 <div class="well well-lg" style="width: 100%; height: 200px;">
										<div class="portlet-body">
											<label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="optDateId" value="optDateId"
												onchange="" checked> Date <span></span></label>
											 <!-- <div class="form-group">
												<label class="control-label">Date</label> <input type="text"
													id="DateId" class="form-control input-large"
													placeholder="" name="DateId"></input>
											</div>  -->
											
											<div class="input-group input-large date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="DateId" id="DailyTDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
											
										</div>
									 </div> 

								</div>


								<div class="col-md-6">
									<div class="well well-sm" style=" width: 100%; height: 200px;">
										<div class="portlet-body">
											<label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="optDailyId" value="optDailyId"
												onchange=""> Daily <span></span></label>

											<!-- <div class="form-group">
												<label class="control-label"> From Date</label> <input
													type="text" id="DailyFDId"
													class="form-control input-large" placeholder=""
													name="DailyFDId" maxlength="12"></input>
											</div>
 -->
 <br>
 <div class="form-group">
 <label class="control-label">From Date</label>
 <div class="input-group input-large date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="fromDate">
 
																<input type="text" autocomplete="off" class="form-control" name="DailyFDId" id="DailyFDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>
										<!-- 	<div class="form-group">
												<label class="control-label">To Date</label> <input
													type="text" id="DailyTDId"
													class="form-control input-large" placeholder=""
													name="DailyTDId" maxlength="12"></input>
											</div> -->
<div class="form-group">
 <label class="control-label">To Date</label>
 <div class="input-group input-large date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="DailyTDId" id="DailyTDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>

										</div>
									</div>

								</div>

							</div>

							<div class="row">
								 <div class="col-md-6">
									<div class="well well-sm" style=" width: 100%; height: 200px;">
										<div class="portlet-body">
											<label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="optWeklyId" value="optWeklyId"
												onchange="" checked> Weekly <span></span></label><br>
											<!-- <div class="col-md-6">
												<label  class="control-label">From Month</label> <select
													class="form-control select2me input-small" id="WeklyFMId"
													name="WeklyFMId">
													<option value=""></option>
												</select>
													
											</div> -->
											 <div class="col-md-6">
 <label class="control-label">From Month</label>
 <div class="input-group input- date date-picker"  data-date-format="MM-yyyy" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="WeklyFMId" id="WeklyFMId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>
                                            <div class="col-md-6">
											<label class="control-label">From Week</label>  <select
													class="form-control select2me input-small" id="WeklyFWId"
													name="WeklyFWId">
													<option value="">Week 1</option>
													<option value="">Week 2</option>
													<option value="">Week 3</option>
													<option value="">Week 4</option>
												</select>
                                               </div>
                                             
                                         <br>
                                          <br>
                                           <br>
                                            <br>
                                            <div class="row">
                                       <!-- <div class="col-md-6" >&nbsp;&nbsp;&nbsp;&nbsp;
											<label class="control-label">To Month</label><select
													class="form-control select2me input-small" id="MontlyTMId"
													name="MontlyTMId">
													<option value=""></option>
												</select>
                                               </div>   -->
                                               <div class="col-md-6">
 <label class="control-label">To Month</label>
 <div class="input-group input- date date-picker" data-date-format="MM-yyyy" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="MontlyTMId" id="MontlyTMId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>     
                                             
                                              <div class="col-md-6">
											<label class="control-label">To Week</label>  <select
													class="form-control select2me input-small" id="MontlyTWId"
													name="MontlyTWId">
													<option value="">Week 1</option>
													<option value="">Week 2</option>
													<option value="">Week 3</option>
													<option value="">Week 4</option>
												</select>
                                               </div>
                                               </div>
                                             </div>   
										</div>
									</div> 
								<div class="col-md-6">
									<div class="well well-sm" style=" width: 100%; height: 200px;">
										<div class="portlet-body">
											<label class="mt-radio"> <input type="radio"
												name="optionsRadios" id="optMthilyId" value="optMthilyId"
												onchange=""> Monthly <span></span></label>

											<!-- <div class="form-group">
												<label class="control-label"> From Month</label> <select
													class="form-control select2me input-large" id="MonthlyFDId"
													name="MonthlyFDId">
													<option value=""></option>
												</select>
											</div> -->
											</div>
											<div class="col-md-6">
 <label class="control-label">From Month</label>
 <div class="input-group input- date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" >
																<input type="text" autocomplete="off" class="form-control" name="MonthlyFDId" id="MonthlyFDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>
											<!-- <div class="form-group">
												<label class="control-label">To Month</label> <select
													class="form-control select2me input-large" id="MonthlyTDId"
													name="MonthlyTDId">
													<option value=""></option>
												</select>
											</div> -->
											<div class="col-md-6">
 <label class="control-label">To Month</label>
 <div class="input-group input- date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" >
																<input type="text" autocomplete="off" class="form-control" name="MonthlyTDId" id="MonthlyTDId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																</div>
										</div>
									</div>
								</div>
								</div><!--End tab2  -->
								
								<div class="tab-pane" id="tab_1_3">

							<div class="box">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
														<div align="right"><button  type="button" id="printChart" 
														class="btn green"  >
									
									               <b>Print</b>
								                       </button></div> 
									
											</div>
											 <!-- <label class="checkbox-inline">
											<input type="checkbox" name="checkboxValues" id="mf1" 
											 onclick="return graphicalView();"> Apply MF
											</label> -->
											</div>
										</form>
									</div>
						  
								 <div id="chartContainer1" style="height: 370px; width: 100%;"></div>
							</div>
						</div>
								
							</div> 
						</div>
					</div>
					<!-- tab-content -->
				</div>
			</div>
			<!-- modal-body -->
		</div>
		<!-- portlet-body -->
	
	<!-- portlet box blue -->
<!-- page-content -->


<script>
function tabShow(param){

if(param=='meterlocation'){

$('#tab_1_1').show();
$('#tab_1_2').hide();
$('#tab_1_3').hide();
	
}
if(param=='time'){
	

	$('#tab_1_1').hide();
	$('#tab_1_2').show();
	$('#tab_1_3').hide();
		
	}
if(param=='graph'){
	

	$('#tab_1_1').hide();
	$('#tab_1_2').hide();
	$('#tab_1_3').show();
	graphicalView();
	}
}

function graphicalView(){

	
var dataPoints = [];
var maxData = [];
var avgData = [];
var minCurrentIr = [];
var maxCurrentIr = [];
var avgCurrentIr =[];
var minVoltVr = [];
var maxVoltVr = [];
var avgVoltVr = [];
var minBlockKwh = [];
var maxBlockKwh = [];
var avgBlockKwh = [];


 frmDate='2019-02-01';
 tDate = '2019-04-30'; 
 meterNum='4613456';
 var tourl="";
// alert(checkBox.checked);

	 
		 tourl='./getMinMaxAvgDataByMtrNoandMf/'+meterNum+'/'+frmDate+'/'+tDate;
		
	
 
 $.ajax({
		url:tourl,
		type:'GET',
		success:function(response){
			if(response.length == 0 || response.length == null ){
				bootbox.alert("No data for this meter number "+meterNum);
			}
			else{
			var minimumParameters=response[0];
			var maximumParameters=response[1];
			var averageParameters=response[2];
			//alert(minimumParameters+"sdd"+maximumParameters +maximumParameters.length);
			
			
			for(var i=0;i<maximumParameters.length;i++){
				var respo = maximumParameters[i];
				
				maxData.push({
					x : new Date(respo[1]),
					y : (respo[10])
				});
			}
			
			
				var chart = new CanvasJS.Chart("chartContainer1", {
					animationEnabled: true,
					title:{
						text: "Energy Consumption"
					},
					axisX: {
						
						 valueFormatString: 'hh:mm' 
					},
					axisY: {
						title: "kWh",
						includeZero: false,
						suffix: " "
					},
					legend:{
						cursor: "pointer",
						fontSize: 16,
						itemclick: toggleDataSeries
					},
					toolTip:{
						shared: true
					},
					data: [{
						name: "Time",
						type: "spline",
						yValueFormatString: "#0.## ",
						showInLegend: true,
						dataPoints: maxData
					},
					/* {
						type: "line",
						name: "Max Kvah",
						color: "#C24642",
						axisYIndex: 0,
						showInLegend: true,
						dataPoints: maxData
					 },
					{
						type: "line",
						name: "Avg Kvah",
						color: "#7F6084",
						axisYIndex: 0,
						//axisYType: "secondary",
						showInLegend: true,
						dataPoints: avgData 
					} */
					] 
				});
						
					
			chart.render();
					document.getElementById("printChart").addEventListener("click",function(){
						//var a=document.getElementById("tab_1_3");
						//a.print()
				    	chart.print();
				    	/* chartIr.print();
				    	chartVr.print();
				    	chartBKwh.print(); */
				    }); 
				function toggleDataSeries(e) {
					if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
						e.dataSeries.visible = false;
					} else {
						e.dataSeries.visible = true;
					}
					e.chart.render();
				}
			}
		
		}
		

	});
	

}


</script>
