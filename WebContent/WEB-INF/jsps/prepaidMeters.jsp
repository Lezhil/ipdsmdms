 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <style>
.table-toolbar {
    margin-bottom: 4px;
}

</style>


<!--  <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
  <script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
 <script  type="text/javascript">
 var mtrNum=null;
 var frmDate=null;
 var tDate=null;
  $(".page-content").ready(function()
   {     
	  //alert('${mtrno}');
	  $('#fromDate').val('${fromDate}');
	  $('#toDate').val('${toDate}');
	  
	  
	  var radio="${radioVal}";
	  if(radio=='kno'){
		  $('#meterNum').val('${accno}');
		  $("#kno_radio").click();
		
	  } else{
		  $('#meterNum').val('${mtrno}');
		  $("#meterno_radio").click();
	  }
	  
	  
	  	frmDate='${fromDate}';
		tDate='${toDate}';
	 	mtrNum='${mtrno}';
	 	
	 	var msg="${msg}";
	 	if(msg!=null && msg !=''){
	 		$("#errId").show();
			setTimeout(function() {
		        $("#errId").fadeOut(1500);
		    },5000);
	 	}
	 	
		App.init();
		TableEditable.init();
		FormComponents.init();
		
		$('#MDMSideBarContents,#prepaidMeters').addClass('start active ,selected');
   		 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#alarm").removeClass('start active ,selected');
   	
		
   });	 
  
  </script>
  
  <script type="text/javascript">
 
function isFloat(x) { return !!(x % 1); }
function getTodaysConsumption()
{
	var mtrNo=mtrNum;
	var tariff_code=$("#tariff_code").text();
	//alert(tariff_code);
	
	$('#imageee').show();
	//getOnDemandPooldata(mtrNo);
	$('#imageee').hide();
	
	$.ajax({
    	url : './getTodaysConsumption/'+mtrNo,
    	type:'GET',
    	data : {
    		tariff_code : tariff_code,
    	},
    	dataType:'json',
    	async: false,
    	cache:false,
    	success:function(data)
    	{
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	
	   		 	var resp=data[0];
				
				    $("#meterno").text(resp.mtrno);
				    $("#t_kwh_con").text(parseFloat(resp.t_kwh_con).toFixed(0)+" kWh");
				    $("#c_kwh").text(parseFloat(resp.c_kwh).toFixed(0)+" kWh");
				    $("#c_kwh_con").text(parseFloat(resp.c_kwh_con).toFixed(0)+" kWh");
				    $("#submittername").text(resp.read_time);
				    
				var resp1=data[1];    
				$("#amount").text(parseFloat(resp1.amount).toFixed(0) +" Rs");
			    $("#balance").text(parseFloat(resp1.balance).toFixed(0) + " Rs");
			    $("#unit_balance").text(parseFloat(resp1.unitbalance).toFixed(0) +" kWh");
				
		   		
	   		 	//$('#sample_3').dataTable().fnClearTable();
	   		 	//$('#instantsTR').html(html);
	    	}
    	},
		complete: function()
		{  
			//loadSearchAndFilter('sample_3');
		}  
	  });
		
}

function dtconvert(val){
	var datesp=moment(val).format('HH:mm:ss');
	//alert(datesp);
	var d = new Date();
	var str = $.datepicker.formatDate('yy-mm-dd', d);
	var fstr=str+"T "+datesp+"+0530";
	return fstr;
}

function getConsumptionHistory() {
	var mtrNo = mtrNum;
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	//alert(fromDate+" --- "+toDate);
	$.ajax({
    	url : './getConsumptionHistory/'+mtrNo,
    	type:'GET',
    	dataType:'json',
    	data : {
    		fromDate : fromDate,
    		toDate : toDate,
    	},
    	asynch:false,
    	cache:false,
    	success:function(data)
    	{
    		if(data.length==0 || data.length==null)
    		{
    			bootbox.alert("Data Not Available for this Meter No. : "+mtrNo);
    		}
    		else 
    		{
	    		var html="";
	   		 	for(var i=0;i<data.length; i++)
				{
					var resp=data[i];
					 
					
					 
					html+="<tr>"
						+" <td>"+(resp.keyReadings.date==null?"":moment(resp.keyReadings.date).format("YYYY-MM-DD"))+"</td>"
						+" <td>"+(resp.consumption==null?"":(parseFloat(resp.consumption).toFixed(0) ))+" kWh </td>"
						+" <td>"+parseFloat(resp.amount).toFixed(0)+" Rs</td>"
						+" <td>"+parseFloat(resp.comsumption_remaining).toFixed(0) +" kWh</td>"
						+" <td>"+parseFloat(resp.balance).toFixed(0)+" Rs</td>"
						+" </tr>";
		   		}
	   		 	$('#sample_6').dataTable().fnClearTable();
	   		 	$('#billHistoryBody').html(html);
	    	}
    	},
		complete: function()
		{  
			loadSearchAndFilter('sample_6');
		}  
	  }); 
	
}

function setRadioVal(val) {
	$("#radioVal").val(val);
	if(val=='meterno'){
		$("#meterNum").attr("placeholder", "Enter Meter No");
	} else{
		$("#meterNum").attr("placeholder", "Enter K No");
	}
}
</script>
  
  
  
<div class="page-content" >
    
	<div class="portlet box blue">
	 
		<div class="alert alert-danger display-show" id="errId" style="display: none;">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	
                        
									<!-- /.col-md-6 -->
          
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Prepaid Meter Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
							<div class="row" style="margin-left:-1px;">
								<div class="mt-radio-inline">
                                     <label class="mt-radio">
                                         <input type="radio" name="optionsRadios" id="meterno_radio" value="meterno" checked onchange="setRadioVal(this.value)"> Meter No
                                         <span></span>
                                     </label>
                                     <label class="mt-radio">
                                         <input type="radio" name="optionsRadios" id="kno_radio"  value="kno" onchange="setRadioVal(this.value)"> K No
                                         <span></span>
                                     </label>
                                 </div>
							
								<form>
									<table style="width: 25%">
									<tbody>
										<tr>
											<td hidden="true"><input class="form-control input-medium" name="radioVal" id="radioVal" value="meterno" /></td>
											<td><input class="form-control input-medium" placeholder="Enter Meter No." name="meterNum" id="meterNum" /></td>
										    <td><div class="col-md-3">
											<div class="input-group input-large date-picker input-daterange"  data-date-format="yyyy-mm-dd">
											<input type="text" placeholder="From Date" class="form-control" value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />" data-date="${currentDate}"
										    data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span>
											<input type="text" placeholder="To Date" class="form-control" value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />" data-date="${currentDate}"
											data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="toDate" id="toDate">
											</div>
										</div></td>
										    
										    
										    <td><button  type="submit" id="dataview" class="btn yellow" formaction="./prepaidMeters" formmethod="post"><b>View</b></button></td>
										</tr>
									</tbody>
									</table>
								</form>
				
							</div>
							
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<div class="modal-body">
					
						<div class="tabbable tabbable-custom tabbable-full-width">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_1_2" data-toggle="tab">Consumer Details</a></li>
				<li><a href="#tab_1_4" onclick="return getTodaysConsumption();" data-toggle="tab">Todays Consumption</a></li>
				<li><a href="#tab_1_8" onclick="return getConsumptionHistory();" data-toggle="tab">Consumption History</a></li>
				
			</ul>
					<div class="tab-content">

						<!--tab_1_2-->
						<div class="tab-pane active" id="tab_1_2">

							<div class="box">

								<div id="tab_1-1" class="tab-pane active">
									<form role="form" class="form-horizontal" action=" "
										method="post">
										<div class="form-body">
											<div class="table-responsive">
												<h4><b>Consumer Details</b></h4>
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv2">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																<li><a href="#" id="print" onclick="exportPDF('consumerDetails','ConsumerDetails')">Export to PDF</a></li>
																<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('consumerDetails', 'Consumer Details')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
												</div>
												<table class="table table-striped table-bordered" id="consumerDetails">
													<tbody>
														<c:forEach var="element" items="${mtrFdrList}">
															<tr><th>Circle</th><td>${element.circle}</td><th>Consumer Name</th><td>${element.customer_name}</td></tr>
															<tr><th>Division</th><td>${element.division}</td><th>Consumer Mobile</th><td>${element.customer_mobile}</td></tr>
															<tr><th>SubDivision</th><td>${element.subdivision}</th><th>Consumer Address </td> <td>${element.customer_address}</td></tr>
															<tr><th>Account No</th><td>${element.accno}</td><th>Meter No</th><td>${element.mtrno}</td></tr>
															<tr><th>K No</th><td>${element.kno}</td><th>Tariff</th><td id="tariff_code">${element.tariffcode}</td> </tr>
															<tr><th>Supply Type</th><td>LT</td><th>Phase</th><td>${meterMaster.phase}</td></tr>
															<tr><th>Balance</th><td><fmt:formatNumber type = "number" pattern = "###" value = "${balance}" /> Rs</td>
															<th>Unit Balance</th><td><fmt:formatNumber type = "number" pattern = "###" value = "${unitbalance}" /> kWh</td></tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</form>
								</div>

							</div>
						</div>
						
						<div class="tab-pane" id="tab_1_4">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<form role="form" class="form-horizontal" action=" "
											method="post">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 27px;">
														<div id="excelExportDiv3">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																
																<li><a href="#" id="excelExport3"
																	onclick="tableToExcel3('sample_3', 'Instants Details')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
													<h5>
														<b>Reading Time : <span id="submittername"></span></b>
													</h5>
													<table
														class="table table-striped table-hover table-bordered"
														id="sample_3">
														<tbody>
															<tr><th>Meter No </th><td id="meterno"></td><th>Total kWh Consumption (Current Month)</th><td id="t_kwh_con"></td></tr>
															<tr><th>Current kWh Reading </th><td id="c_kwh"></td><th>Todays Consumption</th><td id="c_kwh_con"></td></tr>
															<tr><th>Amount Today </th><td id="amount"></td><th>Balance</th><td id="balance"></td></tr>
															<tr><th>unit Balance </th><td id="unit_balance"></td></tr>
															<!-- <tr><th>kWh (Export)</th><td id="kwh_export"></td><th>kVAh (Export)</th><td id="kvah_export"><th></th><td id=""></td></td></tr>
															<tr><th>PF</th><td id="pf_t"></td><th>Frequency (Hz)</th><td id="frequency"></td><th>Power kW</th><td id="p_kw"></td></tr>
															<tr><th>kvar</th><td id="kvar"></td><th>kVArh Lag</th><td id="kvar_lag"><th>kVArh Lead</th><td id="kvar_lead"></td></td></tr>
															<tr><th>Power off Count</th><td id="power_off_count"></td><th>Power off Duration (m)</th><td id="power_off_duration"></td><th>Tamper Count</th><td id="tamper_count"></tr> -->
														</tbody>
														<!-- <tbody id="instantsTR">

														</tbody> -->
													</table>
													
													
													
												</div>

											</div>
										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab_1_8">

							<div class="box">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
											<div class="portlet-body">
												<div class="table-toolbar">
													<div class="btn-group pull-right" style="margin-top: 1px;">
														<div id="excelExportDiv5">
															<button class="btn dropdown-toggle"
																data-toggle="dropdown">
																Tools <i class="fa fa-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																
																<li><a href="#" id="excelExport5"
																	onclick="tableToExcel5('sample_5', 'Bill History')">Export
																		to Excel</a></li>
															</ul>
														</div>
													</div>
													<table
														class="table table-striped table-hover table-bordered"
														id="sample_6">
														<thead>
															<tr>
																<th>Date</th>
																<th>kWh Consumption</th>
																<th>Amount</th>
																<th>Unit Balance</th>
																<th>Balance</th>
															</tr>
														</thead>
														<tbody id="billHistoryBody">

														</tbody>
													</table>


												</div>
												<div id="chartContainer" style="height: 370px; width: 100%;"></div>
												<!-- <div hidden="true" id="graphId"></div> -->
											</div>
									</div>
								</div>
							</div>
						</div>
					</div>



					<!--Graph Info  -->
		<%-- <div class="tab-pane" id="tab_1_8">						
			<div class="box">
				<div class="tab-content">
					<div id="tab_1-1" class="tab-pane active">
						<form role="form" class="form-horizontal" action=" " method="post">
							<div class="form-body">
				  				<div class="table-responsive">
									<h4><b>Graph Details</b></h4>
							</div>
						</div>
					</form>
				</div>
			</div>
			</div>
		</div> --%>
		<!--Graph Info end -->
		<!--end tab-pane 2-->
		<!-- <button type="button" style="margin-left: 1000px;" data-dismiss="modal" class="btn red">Close</button> -->
		</div>
		</div>
					</div>
					
						
							
						</div>
		</div>
	
</div>



<script>
function loadSearchAndFilter(param) {
	$('#' + param).dataTable().fnDestroy();
	$('#' + param).dataTable(
			{
				"aLengthMenu" : [ [ 10, 20, 50, 100, -1 ],
						[ 10, 20, 50, 100, "All" ] // change per page values here
				],
				"iDisplayLength" : 10
			});
	jQuery('#' + param + '_wrapper .dataTables_filter input')
			.addClass("form-control input-small"); // modify table search input 
	jQuery('#' + param + '_wrapper .dataTables_length select')
			.addClass("form-control input-xsmall"); // modify table per page dropdown 
	jQuery('#' + param + '_wrapper .dataTables_length select')
			.select2(); // initialize select2 dropdown 
}

</script>

