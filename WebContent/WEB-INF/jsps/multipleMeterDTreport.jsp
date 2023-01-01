<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script>
$(".page-content").ready(	 	
		function() {
			App.init();
			TableEditable.init();
			
		    $('#MDMSideBarContents,#reportsId,#multipleMtrDtRpt').addClass('start active ,selected');
			   $("#MDASSideBarContents,#dash-board2,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
				.removeClass('start active ,selected');
			FormComponents.init();
			loadSearchAndFilter('sample_1');	
			loadSearchAndFilter('sample_2');
			loadSearchAndFilter('sample_3');
			loadSearchAndFilter('sample_4');
			loadSearchAndFilter('sample_5');
			loadSearchAndFilter('sample_6');		
			var zoneVal="${zoneVal}";
			var circleVal="${circleVal}";
		
		   
			
			
		//	$('input[id="uConsumer"]').prop('checked', true);
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
			
	       $('.datepicker').datepicker({
	           format: 'yyyy-mm-dd',
	           autoclose:true,
	           endDate: '-1d',
	           

	       }).on('changeDate', function (ev) {
	               $(this).datepicker('hide');
	           });


	       $('.datepicker').keyup(function () {
	           if (this.value.match(/[^0-9]/g)) {
	               this.value = this.value.replace(/[^0-9^-]/g, '');
	           }
	       });
	       
	   
	   
		});

</script>
<script type="text/javascript">

function getMonthWise(){

	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var sdoCode = $('#sdoCode').val();
	var townCode =	$('#townId').val();
	var dtTpCode = $('#dtTpId').val();
	var billmonth = $('#selectedFromDateId').val();
	
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

	$('#imageee').show();
		$
		.ajax({
		    url : './getMultipleMeterDTMonthWise',
	    	type:'POST',
	    	data : {
		    	zone :zone,
		    	circle:circle,
		    	division:division,
		    	sdoCode:sdoCode,
		    	townCode:townCode,
		    	dtTpCode:dtTpCode,
		       	billmonth:billmonth
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee').hide();
	    		if(response.length == 0 ){
	    			bootbox.alert('No Data found for selected paramters.');
	    			return false;
	    		}else{
	    		
			      var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					 
					  "<td>"+resp[4]+" </td>"+
					  "<td>"+resp[6]+" </td>"+
					  "<td>"+resp[10]+" </td>"+
					  "<td>"+resp[11]+" </td>"+
					  "<td>"+resp[12]+" </td>"+
					  
					  "<td>"+resp[13]+" </td>"+
					  "<td>"+resp[14]+" </td>"+
					  "<td>"+resp[15]+" </td>"+
					  "<td>"+resp[16]+" </td>"+
					  "<td>"+resp[17]+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_1').dataTable().fnClearTable();
				  $("#updateMaster").html(html);
				  loadSearchAndFilter('sample_1');  
	    						 
	    		}},
			complete: function()
			{  
				loadSearchAndFilter('sample_1'); 
			}
		});

}

function getMultipleMeterDTDateWiseData(){

	 
	
	var selectedFromDateIdPeriodic = $('#selectedFromDateIdPeriodic').val();
	var selectedToDateId = $('#selectedToDateId').val();
	var dtTpCodeDateWise = $('#dtTpCodeDateWise').val().trim();
	
	if (dtTpCodeDateWise == "") {
		bootbox.alert("Please enter DT CODE");
		return false;
	}
	
	if (selectedFromDateIdPeriodic == "") {
		bootbox.alert("Please select from date");
		return false;
	}
	if (selectedToDateId == "") {
		bootbox.alert("Please select to date");
		return false;
	}
	$('#imageee1').show();
	
		$.ajax({
		    url : './getMultipleMeterDTDateWise',
	    	type:'POST',
	    	data : {
	    		fromDate :selectedFromDateIdPeriodic,
	    		toDate:selectedToDateId,
		    	dtTpCode:dtTpCodeDateWise
		    	
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee1').hide();
	    		if(response.length == 0 ){
	    			bootbox.alert('No Data found for selected paramters.');
	    			return false;
	    		}else{
	    			var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					 
					   "<td>"+resp[4]+" </td>"+
						  "<td>"+resp[6]+" </td>"+
						  "<td>"+resp[10]+" </td>"+
						  "<td>"+resp[11]+" </td>"+
						  "<td>"+resp[12]+" </td>"+
						  
						  "<td>"+resp[13]+" </td>"+
						  "<td>"+resp[14]+" </td>"+
						  "<td>"+resp[15]+" </td>"+
						  "<td>"+resp[16]+" </td>"+
						  "<td>"+resp[17]+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_3').dataTable().fnClearTable();
				  $("#updateMasterDateWise").html(html);
				  loadSearchAndFilter('sample_3');  
	    						 
	    						 
	    		}},
			complete: function()
			{  
				loadSearchAndFilter('sample_3'); 
			}
		});
}

	function getMultipleMeterDTMETERWiseData(){
		var selectedFromDateIdPeriodic = $('#selectedFromDateIdMeterWise').val();
		var dtTpCodeDateWise = $('#DTtpCodeMeterWise').val().trim();
	
	if (dtTpCodeDateWise == "") {
		bootbox.alert("Please enter DT TP CODE");
		return false;
	}
	
	if (selectedFromDateIdPeriodic == "") {
		bootbox.alert("Please select from date");
		return false;
	}
	$('#imageee2').show();
	
	
		$.ajax({
		    url : './getMultipleMeterDTMeterWise',
	    	type:'POST',
	    	data : {
	    		billDate :selectedFromDateIdPeriodic,
	    		
		    	dtTpCode:dtTpCodeDateWise
		    	
		    	
		    },
			dataType : 'json',
	    	success:function(response)
	    	{
	    		$('#imageee2').hide();
	    		if(response.length == 0 ){
	    			bootbox.alert('No Data found for selected paramters.');
	    			return false;
	    		}else{
	    		
	    		
	    		
	    		for (var i = 0; i < response.length; i++) 
				  {
	    			var resp2=response[i];
	    			
	    	 	$("#subDivisionmainId").val(resp2[4]);
	    		$("#townMain").val(resp2[6]);
	    		$("#dtTpcodeMainId").val(resp2[10]);
	    		$("#DtNameMainId").val(resp2[11]);
	    		$("#dateMainId").val(selectedFromDateIdPeriodic);
	    		//$("#dateMainId").val(resp2[13]);
				  } 
			      var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					//  alert(resp[0]);
					   html+="<tr>"+
					 
					  
					  "<td>"+resp[13]+" </td>"+
					  "<td>"+resp[12]+" </td>"+
					  
					 
					  "<td>"+resp[14]+" </td>"+
					  "<td>"+resp[15]+" </td>"+
					  "<td>"+resp[16]+" </td>"+
					  "<td>"+resp[17]+" </td>"+
					  "<td>"+resp[18]+" </td>"+
					  "</tr>";				
				   }                                
				  $('#sample_4').dataTable().fnClearTable();
				  $("#updateMasterMeterWise").html(html);
				  loadSearchAndFilter('sample_4');  
	    						 
			}},
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
						<i class="fa fa-edit"></i>Multiple Meter DT Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
				
					<!--BEGIN TABS-->
					<div class="tabbable tabbable-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1_1"   data-toggle="tab">Month Wise</a></li>
							<li><a href="#tab_1_2" data-toggle="tab" hidden="true">Date Wise</a></li>
							<li><a href="#tab_1_3" data-toggle="tab" hidden="true">DT Meter Wise</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
									
								
								<div class="row" style="margin-left: -1px;">
					<div class="col-md-3">
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
					<div class="col-md-3">
						<div id="circleTd" class="form-group">
							<select class="form-control select2me" id="circle" name="circle">
									<option value="">Select Circle</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${circleList}" var="elements">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div id="divisionTd" class="form-group">
							<select class="form-control select2me" id="division" name="division">
									<option value="">Select Division</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${divisionList}" var="elements">
									<option value="${elements}">${elements}</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div id="subdivTd" class="form-group">
							<select class="form-control select2me" id="sdoCode" name="sdoCode">
									<option value="">Select Sub-Division</option>
									<option value="ALL">ALL</option>
									<c:forEach items="${subdivList}" var="sdoVal">
									<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
							</select>
						</div>
					</div>
				</div>


								<div id="showConsumer">
									<div class="row" style="margin-left: -1px;">
											<div class="col-md-3">
													<div id="townDivId" class="form-group">
														<!-- <label class="control-label">Consumer Category:</label> -->
															<select class="form-control select2me" id="townId" name="townId">
															<option value="">Select Town</option>
															<option value="ALL">ALL</option>	
															</select>
													</div>
											 </div>
											 	<div class="col-md-3" >
													<div id= "dtDivID"  class="form-group">
														<!-- <label class="control-label">Consumer Category:</label> -->
															<select class="form-control select2me" 
																id="dtTpId" name="dtTpId">
																<option value="">Select DT Code</option>
															</select>
															
															
														<!-- 	 <input type="text" id="dtTpCodeId"
																class="form-control placeholder-no-fix"
																placeholder="Enter DT TP Code." name="asgdtcode"
																maxlength="12"></input> -->
													</div>
											 </div>
											 	<div class="col-md-3">
													<div class="form-group">
														<!-- <label class="control-label">Consumer Category:</label> -->
																<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyymm" data-date-viewmode="years" >
										<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId" placeholder="Select Date"  >
										<span class="input-group-btn">
										<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
										</span>
									</div>
													</div>
											 </div>
											 
										
									</div>
									<button type="button" id="viewConsumerData"
										style="margin-left: 480px;" onclick="getMonthWise()"
										name="viewConsumerData" class="btn yellow">
										<b>Generate Report</b>
									</button>
									<br />
									<hr />
												<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
									
									<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_1', 'MultipleMeterDTReport')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_1" >
										<thead>
											<tr>
																							
												<th>Sub Division</th>
												<th>Town</th>
												<th>DT TP code</th>
												<th>DT Name</th>
												<th>Month Year</th>
												<th>kWh</th>
												<th>kVah</th>
												<th>kW</th>
												<th>kVa</th>
												<th>PF</th>
												
											</tr>
										</thead>
										<tbody id="updateMaster">

										</tbody>
									</table>
								</div>
							</div>
							
							
							<!-- New Tab DATE WISE -->
							
							<div class="tab-pane" id="tab_1_2">
									
								


								<div id="ushowConsumer" >
									<div class="row" style="margin-left: -1px;">
										
										<div class="col-md-2">
														<div class="form-group">
															<!-- <label class="control-label">Account No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="dtTpCodeDateWise"
																class="form-control placeholder-no-fix"
																placeholder="Enter DT  CODE" name="dtTpCodeDateWise"
																maxlength="12"></input>
														</div>
										</div>
									
										<div class="col-md-3">
														 <div class="input-group input-large date-picker input-daterange" id= "todatelabel"  data-date-format="yyyy-mm-dd">
											
											<input type="text" autocomplete="off" placeholder="From Date" class="datepicker form-control" id="selectedFromDateIdPeriodic" 
											<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="myDate"/>
											 value="<fmt:formatDate value="${myDate}" pattern="yyyy-mm-dd" />" data-date="${myDate}"
										    data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">To</span>
										
											<input type="text" autocomplete="off" placeholder="To Date" class="datepicker form-control" id="selectedToDateId"
											<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="mytoDate"/>
											value="<fmt:formatDate value="${mytoDate}" pattern="yyyy-mm-dd" />" data-date="${mytoDate}"
											data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="toDate" id="toDate"> 
								</div>
										</div>
									</div>
									<button type="button" id="viewasgConsumerData"
										style="margin-left: 480px;" onclick="getMultipleMeterDTDateWiseData()"
										name="viewasgConsumerData" class="btn yellow">
										<b>Generate Report</b>
									</button>
									<br />
									<hr />
						<div id="imageee1" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
							
									<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_3', 'MultipleMeterDTReport')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
						
									<table class="table table-striped table-hover table-bordered"
										id="sample_3" >
										<thead>
											<tr>
												<th>Sub Division</th>
												<th>Town</th>
												<th>DT TP code</th>
												<th>DT Name</th>
												<th>Date</th>
												<th>kWh</th>
												<th>kVah</th>
												<th>kW</th>
												<th>kVa</th>
												<th>PF</th>
											</tr>
										</thead>
										<tbody id="updateMasterDateWise">

										</tbody>
									</table>
								</div>
								

								
							</div>
							
							
							<!--  end new tab-->	
							
							
							<!--  Start of 3 tab-->	
							<div class="tab-pane" id="tab_1_3">
									

								<div id="ushowConsumer" >
									<div class="row" style="margin-left: -1px;">
										
										<div class="col-md-3">
														<div class="form-group">
															<!-- <label class="control-label">Account No</label> <span
																style="color: red" class="required">*</span>  -->
																<input
																type="text" id="DTtpCodeMeterWise"
																class="form-control placeholder-no-fix"
																placeholder="Enter DT Code" name="DTtpCodeMeterWise"
																maxlength="12"></input>
														</div>
										</div>
										<div class="col-md-3">
														 	<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyy-mm-dd" data-date-viewmode="years" >
										<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateIdMeterWise"  placeholder="Select Date" >
										<span class="input-group-btn">
										<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
										</span>
									</div>
										</div>
									
									</div>
									<button type="button" id="viewasgConsumerData"
										style="margin-left: 480px;" onclick="getMultipleMeterDTMETERWiseData()"
										name="viewasgConsumerData" class="btn yellow">
										<b>Generate Report</b>
									</button>
									<br />
											<div id="imageee2" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
									   <table style="color:#000000;  padding-bottom: 25px;  width:100%; " class="table table-bordered">
               
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC; "><b>SubDivision :</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large"  name="" autocomplete="off" id="subDivisionmainId" readonly type="text" style="width: 100%;" >
               
               </td>
               </tr>
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>Town</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large"  name="" autocomplete="off" id="townMain" readonly type="text" style="width: 100%;" >
               </td>
               </tr>
               
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>DT TP CODE</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name=""   autocomplete="off" id="dtTpcodeMainId" readonly type="text" style="width: 100%;" >
               </td>
               </tr>
                
               <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>DT NAME</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="DtNameMainId" readonly="readonly"  type="text" style="width: 100%;" >
               </td>
               </tr>   
               
                <tr>
               <td style="color: #000000; width:150px; background-color:#DCDCDC;"><b>DATE</b>
               </td>
               <td style="background-color:#DCDCDC;">
               <input class="form-control input-large" name="" autocomplete="off" id="dateMainId" readonly="readonly"  type="text" style="width: 100%;" >
               </td>
               </tr>
              </table>
									<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_4', 'MultipleMeterDTReport')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
						
									
									<table class="table table-striped table-hover table-bordered"
										id="sample_4" >
										<thead>
											<tr>
											
												<th>IP</th>
												<th>Meter No</th>
												<th>kWh</th>
												<th>kVah</th>
												<th>kW</th>
												<th>kVa</th>
												<th>PF</th>
											</tr>
										</thead>
										<tbody id="updateMasterMeterWise">

										</tbody>
									</table>
								</div>
								

								
							</div>
							
							<!--  end 3rd tab-->	
							
							
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>



<script>


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
	    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
						html += "<select id='sdoCode' name='sdoCode' onchange='getTownsBaseOnSubdivision(this.value)'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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


 function getTownsBaseOnSubdivision(subdivision) {
		
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = $('#division').val();
		
		$.ajax({
			url : './getTownsBaseOnSubdivision',
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			data : {
				zone : zone,
				circle : circle,
				division : division,
				subdivision:subdivision
			},
					success : function(response1) {
						var html = '';
						html += "<select id='townId' name='townId' onchange ='getDTtPCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i][0]+"'>"
									+ response1[i][1] + "</option>";
						}
						html += "</select><span></span>";
						$("#townDivId").html(html);
						$('#townId').select2();
					}
				});
	}

 
 
 function getDTtPCode(townCode) {
	
	$.ajax({
		url : './getDtTpIDbyTown',
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			townCode : townCode,
			
		},
				success : function(response1) {
					var html = '';
					html += "<select id='dtTpId' name='dtTpId' class='form-control input-medium' type='text'><option value=''>Select DT Code</option><option value='%'>ALL</option>";
					for (var i = 0; i < response1.length; i++) {
						//var response=response1[i];
						html += "<option  value='"+response1[i]+"'>"
								+ response1[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#dtDivID").html(html);
					$('#dtTpId').select2();
				}
			});
	
 }
</script>
<style>
#s2id_estimationrule, #s2id_unestimationrule  {
width: 236px;
}

</style>