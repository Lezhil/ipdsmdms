<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
 <!-- <style>
 .test{
      background-color: red;
      }
      .test2{
      background-color: green;
      }
 
 </style> -->
 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {    
	  
	  $('.datepicker').datepicker({
          format: 'yyyymm',
          autoclose:true,
          endDate: '-1m',
          

      }).on('changeDate', function (ev) {
              $(this).datepicker('hide');
          });


      $('.datepicker').keyup(function () {
          if (this.value.match(/[^0-9]/g)) {
              this.value = this.value.replace(/[^0-9^-]/g, '');
          }
      });
	  
	  
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  //loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	 $("#fromDateMonthly").hide();
	   	    	
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#MDMSideBarContents,#eaId,#energyAccountingFeederLevel,#subdivisionEAreport').addClass('start active ,selected');
	    	 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	
	    	 
	    	
	    
	    	 //alarmData('all');
   	    	   });
 
   
  function mtrDetails(mtrNo)
  { 
  	 window.location.href="./mtrNoDetailsMDAS?mtrno=" + mtrNo;
  }
  

  function handleClick(param){
	 if(param  == 'periodic') {
		$("#toDate").show();
		 $("#todatelabel").show();
		 $("#fromDateMonthly").hide();
		 $("#fromDatePeriodic").show();
	 }else {
		 
		 $("#toDate").hide();
		 $("#todatelabel").hide();
		 $("#fromDateMonthly").show();
		 $("#fromDatePeriodic").hide();
	 }
	  
	 
	  
  }

 
  function clearTabledataContent(tableid)
	{
		 //TO CLEAR THE TABLE DATA
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
	}
  function showCircle(zone)
	{
		var zone="All";
		$.ajax({
			url : './showCircleMDM' + '/' + zone ,
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
	
	function showSubstation(subdivision)
	{
		
		$.ajax({
			url : './getSubstation' + '/' + subdivision,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
		    		
	  				for( var i=0;i<response.length;i++)
					{
	  					 var name = response[i];
		                    
		                    $("#subStationList").append("<option >"+name+"</option>");
							
					}
					
		    	}
			});
	}
	
	
	
	
	
	/* function showSubdivByDiv(division)
	{
		var circle=$('#circle').val();
		var zone="All";
		$.ajax({
			url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/'
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
					$("#subdivTd").html(html);
					$('#subdiv').select2();
		    	}
			});
	} */
	
  </script>
 
  
		<div class="page-content" >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Sub Division EA report </div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

						
				<div class="portlet-body ">
						<div class="table-toolbar">
								<div class="btn-group pull-right" style="margin-top: 1px;">
									<div id="excelExportDiv2">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
													Tools <i class="fa fa-angle-down"></i>
										</button>
										
										<ul class="dropdown-menu pull-right">
												<li><a href="#" onclick="printData();" id="print">Print</a></li>
												<li><a href="#" id="excelExport2" 
													onclick="tableToExcel2('sample_3', 'Energy Accounting Feeder Subdivision wise Report')">Export
									 				to Excel</a></li>
										</ul>
										
									</div>
								</div>
						</div>
			
			<table>
			   <tbody>
					<tr>
						<th class="block" id='reportType'>Report Type &nbsp;:</th>
							<th>
								<div class="radio-inline">
	                			 <label><input type="radio" name="reportType" value= "monthly"  onchange="handleClick(this.value);">Monthly Report</label>
	            	           	</div>
	                       	
		                       	<div class="radio-inline">
		                			 <label><input type="radio" name="reportType" value= "periodic"  onchange="handleClick(this.value);" checked>Periodic Report</label>
		                       	</div>
							</th>	
							
					</tr>
							
					<tr>
						
							 
						 
						 <th class="block">From BillMonth :</th>
						 <th id="fromDateMonthly">
										<div class="input-group input-medium date date-picker" data-date-end-date="-1m"  data-date-format="yyyymm" data-date-viewmode="years" id="fromDate">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th></br></br>
											<!--  <th id="fromDatePeriodic">
										 <div class="input-group input-medium date date-picker" data-date-end-date="-1m" data-date-format="yyyymm" data-date-viewmode="years" id="fromDate">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateIdPeriodic"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>						
																
																
																<th id= "todatelabel">To&nbsp;BillMonth&nbsp;:</th><th>
										<div class="input-group input-medium date date-picker" data-date-end-date="-1m" data-date-format="yyyymm" data-date-viewmode="years" id="toDate">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th> -->
								 <th>
						 <div class="input-group input-large date-picker input-daterange" data-date-end-date="-1m" id= "todatelabel"  data-date-format="yyyymm">
											<input type="text" autocomplete="off" placeholder="From BillMonth" class="datepicker" id="selectedFromDateIdPeriodic" 
											<fmt:parseDate value="${currentDate}" pattern="yyyymm" var="myDate"/>
											 value="<fmt:formatDate value="${myDate}" pattern="yyyymm" />" data-date="${myDate}"
										    data-date-format="yyyymm" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">To</span>
											<input type="text" autocomplete="off" placeholder="To BillMonth" class="datepicker" id="selectedToDateId"
											<fmt:parseDate value="${currentDate}" pattern="yyyymm" var="mytoDate"/>
											value="<fmt:formatDate value="${mytoDate}" pattern="yyyymm" />" data-date="${mytoDate}"
											data-date-format="yyyymm" data-date-viewmode="years" name="toDate" id="toDate"> 
								</div>
						 
						 </th>
		
								</tr></br></br>
								<tr>								
							 		<th class="block" id='circlelabel'>Sub-Division&nbsp;:</th>
										<th id="circleTd">
										<select
											class="form-control select2me input-medium" id="subdiv"
											name="subdiv" >
											
											<option value="">Select</option>
										  <c:forEach var="elements" items="${resultList}">
										    	
											    	<option value="${elements}">${elements}</option>
											    </c:forEach>
										</select>
								</th>
								</tr> 
						
								<tr>
								
										<th>                       
												<!-- <button type="button" class="btn yellow">Reset</button>   -->
												<button type="submit" onclick="view()" class="btn green">Generate</button> 
											
										</th>	
								
							</tr>
							
							
					</tbody>
				</table></br></br></br>
						
							<table class="table table-striped table-hover table-bordered" border="1" id="sample_3">
								<thead>
									<tr>
												<th>S.no</th>
												<th>Report Month</th>
												
												<th>Subdivision</th>
												<th>Subdivision Code</th>
												<th>TP Subdivision Code</th>
												
												
												
												<th>Meter No</th>
												<th>Total Consumers</th>
												<th>Units Supplied</th>
												<th>Units Billed</th>
												<th>Amount Billed</th>
												<th>Amount Collected</th>
												
												<th>Billing Efficiency</th>
												<th>Collected Efficiency</th>
												<th>T & D Loss</th>
												<th>Technical Loss</th>
												<th>% Technical Loss</th>
												
												<th>AT & C Loss</th>
											
												
												
											<!-- 	<th>Subdivision Name</th>
												<th>Subdivision Code</th>
												<th>TP Subdivision Code</th>
												<th>Feeder Name</th>
												<th>TP Feeder Code</th>
												<th>Boundry Feeder </th>
												<th>DT Name </th>
												<th>DT Code </th>
												<th>TP DT Code </th>
												
												<th>Boundry DT </th> -->
												
											
												
												</th>
												
									</tr>
								</thead>
								<tbody id="eventTR">
										<%-- <c:forEach var="v" items="${ma}">
										
										<tr>
										<td>${v[0]}</td>
										<td>${v[1]}</td>
										<td>${v[2]}</td>
										<td>${v[3]}</td>
										<td>${v[4]}</td>
										<td>${v[5]}</td>
										</tr>
										
										</c:forEach> --%>
								</tbody>
							</table>
							
							
							
		</div>
	</div>
 </div>


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

//alert("metrno--"+metrno+"--Billmonth-"+Billmonth);

window.location.href="./downloadLOadSurveyExcel?metrno="+metrno+"&Billmonth="+Billmonth;

/*  /downloadLOadSurveyExcel*/
}
function validation()
{
	var zone=$("#zone").val();
	
	var fdate=$("#selectedFromDateId").val();
	var tdate=$("#selectedToDateId").val();
 if(zone=="")
	{
	bootbox.alert("Please Select Zone");
	return false;
	}  
	
	if(fdate=="")
		{
			bootbox.alert("Please Select  From Date");
			return false;
		}
	if(tdate=="")
	{
		bootbox.alert("Please Select  To Date");
		return false;
	}
	if(new Date(fdate)>new Date(tdate))
	{
		bootbox.alert("Wrong Date Inputs");
		return false;
	}

}
function view(){
	var todate=$("#selectedToDateId").val();
	var subdiv=$("#subdiv").val();
	var reportPeriod=$('input[name = "reportType"]:checked').val();
	var fromdate = null; 
	
	if(reportPeriod == 'periodic'){
		fromdate =$("#selectedFromDateIdPeriodic").val();
	
		
		if(fromdate=="")
		{
			bootbox.alert("Please Select  From  Month");
			return false;
		}
		if(todate=="")
		{
			bootbox.alert("Please Select  To Month");
			return false;
		}
		if(fromdate == todate){
			bootbox.alert("From billmonth month and to billmonth cannot be same ");
			return false;
		}
		
		
	}else {
		fromdate = $("#selectedFromDateId").val();
		
		}
		
	if(subdiv == ''){
		bootbox.alert("Please Select  Sub_division");
		return false;
	
	}
	
	
	
	/* var subStation=$("#subStationList").val();
	var feeder=$("#feeder").val();
	var boundaryFeeder=$("#boundaryFeeder").val();
	
	 */
	
	 
	/* $.ajax({
		url:"./getEneryAccoutingDataSubdivisionWise",
		type:"get",
		
		
		
		data:{
			fromdate:fromdate,
			todate:todate,
			subdiv:subdiv,
			reportPeriod:reportPeriod
		
		}, */
	
		
		
		$.ajax({
			url:"./getEneryAccoutingDataSubdivisionWise",
			type:"get",
			data:{
				fromdate:fromdate,
				todate:todate,
				reportPeriod:reportPeriod,
				subdiv:subdiv
				},
		success:function(res){
			if(res.length == '0'){
				bootbox.alert('No Data found for selected inputs');
			}else{
			html="";
			$.each(res,function(i,v){
				html+="<tr><td>"+(i+1)+"</td><td>"+v[7]+"</td><td>"+v[19]+"</td><td>"+v[18]+"</td><td>"+v[17]+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td>"+
				"<td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td><td>"+v[13]+"</td><td>"+v[14]+"</td><td>"+v[16]+"</td></tr>";
			});
			//$("#listID").empty().
			clearTabledataContent('sample_3');
			$("#eventTR").html(html);
			loadSearchAndFilter('sample_3');
			$("#boxid").show();
			}
		}
	});
	
}
function printData()
{
   var divToPrint=document.getElementById("sample_3");
  
   newWin= window.open("");
   newWin.document.write(divToPrint.outerHTML);
   newWin.print();
   newWin.close();

}

</script>