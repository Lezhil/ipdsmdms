<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>



	<div class="page-content" >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Data Sync</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

						
		 	<div class="portlet-body ">
			<table >
			   <tbody>
					<tr>
						<th>Report Type&nbsp;:</th>
							<th id="reportListTH">
								<select	class="form-control select2me input-medium" onchange='showTable(this.value)' id="reportList" name="reports" >
									<option value="">Select</option>
									<option value="monthlyconsump">Monthly Consumption Data Push</option>
									<option value="dailyconsump">Daily Consumption Data Push Month wise</option>
									<option value="billconsump">Bill Consumption Data Push Month wise</option>
									<option value="moninpconsump">Monthy to Input Consumption Data Push Month wise</option>
									<option value="instconsump">Instantaneous Consumption Data Push Month wise</option>
									<option value="voltmiss">Missing Volt_r Data Push Month wise</option>
									<option value="voltmiss_vy">Missing Volt_y Data Push Month wise</option>
									<option value="voltmiss_vb">Missing Volt_b Data Push Month wise</option>
									<option value="veepush">VEE DataPush Before Monthly Consumption</option>
									<option value="dthealthdatapush">DT Health Report Data Push</option>
									<option value="monthlyafterpushEADT">DT EA After Monthly Consumption Need to Push</option>
									<option value="eadtpush">Energy Accounting DT Push</option>
									<option value="monthlyafterpushEAFeederAndTown">Town & Feeder EA After Monthly Consumption Need to Push</option>
									<option value="eafeedertownpushfeeder">Energy Accounting Feeder & Town Push --- FEEDER</option>
									<option value="eafeedertownpushboundary">Energy Accounting Feeder & Town Push --- BOUNDARY</option>
									<option value="pfcd2report">D2 Intermediate to main push</option>
									<option value="pfcd7report">D7 Intermediate to main push</option>
									<option value="D2report">Legacy Data Integration - D2 main push</option>
									<option value="D7report">Legacy Data Integration - D7 main push</option>
									<option value="NppfeederrptLT">Legacy Data Integration - NppfeederrptLT main push</option>
									<option value="NppfeederrptHT">Legacy Data Integration - NppfeederrptHT main push</option>
									<option value="Nppfeederrptltdt">Legacy Data Integration - Nppfeederrptltdt main push</option>
									<option value="IndstrChange">Legacy Data Integration - IndstrChange main push</option>
									<option value="IndstrDelete">Legacy Data Integration - IndstrDelete main push</option>
									<option value="Indfeedermas">Legacy Data Integration - Indfeedermas main push</option>
									<option value="Indstructuremas">Legacy Data Integration - Indstructuremas main push</option>
									<option value="Indssmas">Legacy Data Integration - Indssmas main push</option>
									<option value="ComwebMobile">Legacy Data Integration - ComwebMobile main push</option>						
						     	</select>
							</th>
							<th>Bill Month&nbsp;:</th>
							<th id="monthlypushdate">
											<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyymm" data-date-viewmode="years" >
												<input type="text" autocomplete="off" class="form-control"   name="selectedDateName" id="selectedFromDateId"   >
												<span class="input-group-btn">
												<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
												</span>
											</div>
										</th>
										
										<th id="fromDateMonthly">
											<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyy-mm-dd" data-date-viewmode="years" >
												<input type="text" autocomplete="off" class="form-control"   name="selectedFromDate" id="selectedFromDate" >
												<span class="input-group-btn">
												<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
												</span>
											</div>
										</th>
										
										<th id="toDateMonthly">
											<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyy-mm-dd" data-date-viewmode="years" >
												<input type="text" autocomplete="off" class="form-control" name="selectedToDate" id="selectedToDate">
												<span class="input-group-btn">
												<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
												</span>
											</div>
										</th>
							<th>
								<div >
									<div class="">                        
										<!-- <button type="button" class="btn yellow">Reset</button>   -->
										<button type="submit" onclick="syncReport()" class="btn green">Sync Data</button> 
										
									</div>
								</div>
							</th>	
						 </tr>
				</tbody>
			</table><br><br><br>
								
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
			</div>					
					<table class="table table-striped table-hover table-bordered" border="1" id="sample_3">
						<thead>
								<tr>
									<th>S.no</th>
									<th>Report Type</th>
										
							</tr>
						</thead>
					<tbody id="eventTR">
				</tbody>
			</table>
		</div>
	</div>
 </div>

 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {    
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  //loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	$('#fromDateMonthly').hide();
	   			$('#toDateMonthly').hide();
	   	  
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#ADMINSideBarContents,#adminArea,#IntermediateDataSyn').addClass('start active ,selected');
	    	 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	 //alarmData('all');
	    	
   	    	   });


  
	
function syncReport(){
	 var reportType = $('#reportList').val();
	 var billmonth = $('#selectedFromDateId').val();
	 var fromdate = $('#selectedFromDate').val();
	 var todate = $('#selectedToDate').val();
	 
	 if(reportType == ''){
		 bootbox.alert('Please select report type');
		 return false;
	 }
	 /* if(billmonth == ''){
		 bootbox.alert('Please select billmonth');
		 return false;
	 } */
	 
	    $("#imageee").show();
	 	if(reportType == 'veepush') {
			  $.ajax({
					url:"./averageofIPProcess",
					type:"get",
					data:{
						fromdate : fromdate,
						todate : todate
						
					},
					success:function(res){
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'monthlyconsump') {
			  $.ajax({
 					url:"./monthluComsumptionAutoPush",
					type:"get",
					data:{
						
						billmonth :billmonth
											
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		
		}else if(reportType =='dailyconsump'){


			$.ajax({
		
					url: "./dailyComsumptionAutoPush",	
					type:"get",
					data:{
						billmonth :billmonth,
						fromdate : fromdate,
						todate : todate
						
					
						},	

					success:function(res){
								//alert(res);
								if(res == 'success'){
									 $("#imageee").hide();
									bootbox.alert('Data is synched successfully');
								}
								if(res == 'failed'){
									 $("#imageee").hide();
									bootbox.alert('Failed to sync data');
								}
								if(res == 'synched'){
									 $("#imageee").hide();
									bootbox.alert('Data is already synched for selected billmonth');
								}
							}	
				});
			
	}  else if(reportType =='billconsump'){


		$.ajax({
	
				url: "./billComsumptionAutoPush",	
				type:"get",
				data:{
					billmonth :billmonth,
					fromdate : fromdate,
					todate : todate
					
				
					},	

				success:function(res){
							//alert(res);
							if(res == 'success'){
								 $("#imageee").hide();
								bootbox.alert('Data is synched successfully');
							}
							if(res == 'failed'){
								 $("#imageee").hide();
								bootbox.alert('Failed to sync data');
							}
							if(res == 'synched'){
								 $("#imageee").hide();
								bootbox.alert('Data is already synched for selected billmonth');
							}
						}	
			});
		
}else if(reportType =='moninpconsump'){


	$.ajax({

			url: "./moninpComsumptionAutoPush",	
			type:"get",
			data:{
				billmonth :billmonth
			
				},	

			success:function(res){
						//alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}	
		});
	
}
	else if(reportType =='instconsump'){


		$.ajax({
	
				url: "./instComsumptionAutoPush",	
				type:"get",
				data:{
					billmonth :billmonth,
					fromdate : fromdate,
					todate : todate
					
				
					},	

				success:function(res){
							//alert(res);
							if(res == 'success'){
								 $("#imageee").hide();
								bootbox.alert('Data is synched successfully');
							}
							if(res == 'failed'){
								 $("#imageee").hide();
								bootbox.alert('Failed to sync data');
							}
							if(res == 'synched'){
								 $("#imageee").hide();
								bootbox.alert('Data is already synched for selected billmonth');
							}
						}	
			});
	}
		else if(reportType =='voltmiss'){


			$.ajax({
		
					url: "./missingvoltageAutoPush",	
					type:"get",
					data:{
						billmonth :billmonth,
						fromdate : fromdate,
						todate : todate
						
					
						},	

					success:function(res){
								//alert(res);
								if(res == 'success'){
									 $("#imageee").hide();
									bootbox.alert('Data is synched successfully');
								}
								if(res == 'failed'){
									 $("#imageee").hide();
									bootbox.alert('Failed to sync data');
								}
								if(res == 'synched'){
									 $("#imageee").hide();
									bootbox.alert('Data is already synched for selected billmonth');
								}
							}	
				});
}else if(reportType =='voltmiss_vy'){


	$.ajax({

			url: "./missingvoltageAutoPush_vy",	
			type:"get",
			data:{
				billmonth :billmonth,
				fromdate : fromdate,
				todate : todate
				
			
				},	

			success:function(res){
						//alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}	
		});
}else if(reportType =='voltmiss_vy'){


	$.ajax({

			url: "./missingvoltageAutoPush_vy",	
			type:"get",
			data:{
				billmonth :billmonth,
				fromdate : fromdate,
				todate : todate
				
			
				},	

			success:function(res){
						//alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}	
		});
}else if(reportType =='voltmiss_vb'){


	$.ajax({

			url: "./missingvoltageAutoPush_vb",	
			type:"get",
			data:{
				billmonth :billmonth,
				fromdate : fromdate,
				todate : todate
				
			
				},	

			success:function(res){
						//alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}	
		});
}else if(reportType == 'dthealthdatapush') {
			  $.ajax({
					url:"./dtHealthDataProcess",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'monthlyafterpushEADT') {
			  $.ajax({
					url:"./afterMonthlyCOnsumtionDTEADataPush",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}
	 	
		else if(reportType == 'eadtpush') {
			  $.ajax({
					url:"./energyAccountingDTDataPush",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'monthlyafterpushEAFeederAndTown') {
			  $.ajax({
					url:"./afterMonthlyCOnsumtionFeederTownEADataPush",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'eafeedertownpushfeeder') {
			  $.ajax({
					url:"./afterMonthlyCOnsumtionFeederTownEADataPushForFeeder",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'eafeedertownpushboundary') {
			  $.ajax({
					url:"./afterMonthlyCOnsumtionFeederTownEADataPushForBoundaryr",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
					//	alert(res);
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}else if(reportType == 'D2report' || reportType == 'D7report' || reportType == "NppfeederrptLT" || reportType == "NppfeederrptHT" || reportType == "Nppfeederrptltdt"
			|| reportType == "IndstrChange" || reportType == "IndstrDelete" || reportType == "Indfeedermas" || reportType == "Indstructuremas" || reportType == "Indssmas"
				|| reportType == "ComwebMobile"){
			
			//alert(billmonth);
			
			$.ajax({
				url:"./IPDSManual",
				type:"get",
				data:{
					billmonth :billmonth,
					reportType :reportType
					
				},
				success:function(res){
					if(res == 'success'){
						 $("#imageee").hide();
						bootbox.alert('Data is synched successfully');
					}
					if(res == 'failed'){
						 $("#imageee").hide();
						bootbox.alert('Failed to sync data');
					}
					if(res == 'synched'){
						 $("#imageee").hide();
						bootbox.alert('Data is already synched for selected billmonth');
					}
				}
			});
		}else if(reportType == 'pfcd2report'){
			$.ajax({
				url:"./dataSyncforD2report",
				type:"get",
				data:{
					billmonth :billmonth
					
				},
				success:function(res){
					if(res == 'success'){
						 $("#imageee").hide();
						bootbox.alert('Data is synched successfully');
					}
					if(res == 'failed'){
						 $("#imageee").hide();
						bootbox.alert('Failed to sync data');
					}
					if(res == 'synched'){
						 $("#imageee").hide();
						bootbox.alert('Data is already synched for selected billmonth');
					}
				}
			});
		}else if(reportType == 'pfcd7report') {
			  $.ajax({
					url:"./dataSyncforD7report",
					type:"get",
					data:{
						billmonth :billmonth
						
					},
					success:function(res){
						if(res == 'success'){
							 $("#imageee").hide();
							bootbox.alert('Data is synched successfully');
						}
						if(res == 'failed'){
							 $("#imageee").hide();
							bootbox.alert('Failed to sync data');
						}
						if(res == 'synched'){
							 $("#imageee").hide();
							bootbox.alert('Data is already synched for selected billmonth');
						}
					}
				}); 
		}

	 	
}
		 
	
  </script>
  
  
	

<script>
function submitDataLoadExcel()
{
var metrno=$("#meterVal").val();

if(metrno.trim()=='')
{
bootbox.alert('Please enter Meter number');
return false;
}
var Billmonth =$("#selectedDateId").val();
window.location.href="./downloadLOadSurveyExcel?metrno="+metrno+"&Billmonth="+Billmonth;
}

</script>

<!-- <script>
$(function(){
    $('.datepicker').datepicker({
        format: 'yyyy-mm',
        endDate: '+0d',
        autoclose: true
    });
});
</script> -->



<style>
table, th, td {
  padding: 5px;
}

</style>

<script>

function showTable(value){
	
	if(value == "monthlyconsump"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}

	if(value == "dailyconsump"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "billconsump"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "moninpconsump"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	if(value == "instconsump"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "voltmiss"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "voltmiss_vy"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "voltmiss_vb"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	if(value == "veepush"){
		$('#monthlypushdate').hide();
		$('#fromDateMonthly').show();
		$('#toDateMonthly').show();
	}
	
	if(value == "dthealthdatapush"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	if(value == "monthlyafterpushEADT"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	
	
	if(value == "eadtpush"){
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	

	if (value == "monthlyafterpushEAFeederAndTown") {
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	if (value == "eafeedertownpushfeeder") {
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	if (value == "eafeedertownpushboundary") {
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	if (value == "pfcd2report") {
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();4
	}
	
	if (value == "pfcd7report") {
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
	if (value == "D2report" || value == "D7report" || value == "NppfeederrptLT" || value == "NppfeederrptHT" || value == "Nppfeederrptltdt"
			|| value == "IndstrChange" || value == "IndstrDelete" || value == "Indfeedermas" || value == "Indstructuremas" || value == "Indssmas"
			|| value == "ComwebMobile") {
	
		
		$('#monthlypushdate').show();
		$('#fromDateMonthly').hide();
		$('#toDateMonthly').hide();
	}
	
}
</script>
