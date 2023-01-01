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
	   	   	$('#MDMSideBarContents,#eaId,#energyAccountingDTLevel').addClass('start active ,selected');
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

  function showFeeder(parentid)
	{
	 var sitecode = $("#subdiv").val(); 
		
		$.ajax({
				url : './showfdrIdNameBySubdivAndSSnameNew',
			    	type:'GET',
			    	dataType:'json',
			    	data : {
			    		 parentid:parentid,
			    		 sitecode : sitecode
			          },
			    	success:function(response)
			    	{
			    	
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						$("#feeder").html(html);
						
						
			    	}
				});
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
	
  
  
  function showSubstation(officeId)
	{
		
		$.ajax({
				url : './showSSnameByofficeID',
			    	type:'GET',
			    	dataType:'json',
			    	data : {
			              
			        	  officeId : officeId
			          },
			    	success:function(response)
			    	{
			    		
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i][0]+"'>"+response[i][1]+"-"+response[i][0]+"</option>";
						}
						$("#subStationList").html(html);
						$("#subStationList").select2();
						
						
			    	}
				});
		}

	/* function showSubstation(subdivision)
	{
		
		$.ajax({
			url : './getSubstationEA' + '/' + subdivision,
		    	type:'GET',
		    	dataType:'json',
		    	
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<option value=''></option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					
					$("#subStationList").html(html);
					$("#subStationList").select2();
		    	}
			});
	} */
	
	
	
	
	
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
  <script>
  
  
  </script>
  
		<div class="page-content" >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Energy Accounting DT Level</div>
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
												<li><a href="#" id="print" onclick="printData()">Print</a></li>
												<li><a href="#" id="excelExport2" 
													onclick="tableToExcel2('sample_3', 'Energy Accounting DT Report')">Export
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
								<div class="radio-inline" id = "reportPeriod">
	                			 <label><input type="radio" name="reportType" value= "monthly"  onchange="handleClick(this.value);">Monthly Report</label>
	            	           	</div>
	                       	
		                       	<div class="radio-inline">
		                			 <label><input type="radio" name="reportType" value= "periodic"  onchange="handleClick(this.value);" checked>Periodic Report</label>
		                       	</div>
							</th>	
							
					</tr>
							
					<tr>
						
							 
						 
						 <th class="block">BillMonth :</th>
						 				
						 				
						 				<th id="fromDateMonthly">
											<div class="input-group input-medium date date-picker" data-date-end-date="-1m" data-date-format="yyyymm" data-date-viewmode="years" >
												<input type="text" autocomplete="off" class="form-control"   name="selectedDateName" id="selectedFromDateId"   >
												<span class="input-group-btn">
												<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
												</span>
											</div>
										</th>
										<!-- <th id="fromDatePeriodic">
											<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" >
												<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
												<span class="input-group-btn">
												<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
												</span>
											</div>
										</th>
										</div>
										<th class="block" id= "todatelabel">To&nbsp;BillMonth&nbsp;:</th>
										<th>
													<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="toDate">
														<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
														<span class="input-group-btn">
														<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
														</span>
														</div>
									   </th> -->
									   
									    <th>
						 <div class="input-group input-large date-picker input-daterange" id= "todatelabel" data-date-end-date="-1m" data-date-format="yyyy-mm-dd">
											<input type="text" autocomplete="off" placeholder="From Billmonth" class="datepicker" id="selectedFromDateIdPeriodic" 
											<fmt:parseDate value="${currentDate}" pattern="yyyyMM" var="myDate"/>
											 value="<fmt:formatDate value="${myDate}" pattern="yyyymm" />" data-date="${myDate}"
										    data-date-format="yyyymm" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">To</span>
											<input type="text" autocomplete="off" placeholder="To Billmonth" data-date-end-date="-1m" class="datepicker" id="selectedToDateId"
											<fmt:parseDate value="${currentDate}" pattern="yyyyMM" var="mytoDate"/>
											value="<fmt:formatDate value="${mytoDate}" pattern="yyyymm" />" data-date="${mytoDate}"
											data-date-format="yyyymm" data-date-viewmode="years" name="toDate" id="toDate"> 
								</div>
						 
						 </th>
							
					</tr>
						
					<tr>
					
						<th class="block" id='subDiv'>Sub-Division&nbsp;:</th>
								<th id="circleTd">
								<select
									class="form-control select2me input-medium" id="subdiv"
									name="subdiv" onchange="showSubstation(this.value);">
									
									<option value="">Select</option>
								  <c:forEach var="elements" items="${resultList}">
								    	
									    	<option value="${elements[1]}">${elements[0]}</option>
									    </c:forEach>
								</select>
						</th>
					
						<th class="block" id=subStation >Sub-Station&nbsp;:</th>
							<th id="subStationData">
							<select
								class="form-control select2me input-medium" id="subStationList" onchange="showFeeder(this.value);"
								name="subStation">
								
								
						     	</select>
							</th>
							
							
					 </tr>
								
							<tr><th colspan="8"></th></tr><tr><th colspan="8"></th></tr>
							<tr>
								<th class="block" id=sdoCodelabel>Feeder&nbsp;:</th>
								<th id="subdivTd">
									<select
										class="form-control select2me input-medium" id="feeder" multiple="multiple"
										name="feeder">
									</select>
							   </th>
								<th class="block" id=sdoCodelabel>Boundry Feeder&nbsp;:</th>
								<th id="subdivTd">
									<select
										class="form-control select2me input-medium" id="boundaryFeeder"
										name="boundaryFeeder">
											    <option value="">Select</option>
											    <option value="t">Yes</option>
											    <option value="f">No</option>
											    
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
												<th>Feeder Name</th>
												<th>Feeder Code</th>
												<th>TP Feeder Code</th>
												<th>Boundary Feeder</th>
												<th>Meter Sr. Number</th>											
													
													
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
view();
}
function view(){
	
	
	var fromdate= null ;
	
	var todate=$("#selectedToDateId").val();
	var subdiv=$("#subdiv").val();
	var subStation=$("#subStationList").val();
	var feederCode=$("#feeder").val();
	var boundaryFeeder=$("#boundaryFeeder").val();
	var reportPeriod=$('input[name = "reportType"]:checked').val();
	
if(subdiv == ''){
	bootbox.alert("Please select subdivision ");
	return false;
}	
if(subStation == ''){
	bootbox.alert("Please select Substation ");
	return false;
}	

if(boundaryFeeder == ''){
	bootbox.alert("Please Select Boundary Feeder ");
	return false;
}	


	
if(reportPeriod == 'monthly'){	
	
	fromdate= $("#selectedFromDateId").val();
	
}
if(reportPeriod == 'periodic'){
	
	fromdate= $("#selectedFromDateIdPeriodic").val();
	
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
		bootbox.alert("From Month and To Month cannot be same ");
		return false;
	}
	
}
if (fromdate == ''){
	bootbox.alert("Please Select from month");
	return false;
}
	
	
	var boundryResult = null;
	$.ajax({
		url:"./getEneryAccoutingDTLevelData",
		type:"get",
		data:{
			fromdate:fromdate,
			todate:todate,
			subdiv:subdiv,
			subStation:subStation,
			feederCode:feederCode,
			boundaryFeeder:boundaryFeeder,
			reportPeriod :reportPeriod
			
			
		},
		success:function(res){
			if(res.length == '0' ){
				bootbox.alert('No data found')
			}else{
			html="";
			$.each(res,function(i,v){
				
				if(v[17] == true){
					boundryResult = 'Yes';
				}else{
					boundryResult = 'No';
				}
				
				html+="<tr><td>"+(i+1)+"</td><td>"+v[6]+"</td><td>"+v[21]+"</td><td>"+v[20]+"</td><td>"+v[19]+"</td><td>"+v[22]+"</td><td>"+v[23]+"</td>"
				+"<td>"+v[24]+"</td>"
				 
				+"<td>"+boundryResult+"</td>"
				
				+"<td>"+v[16]+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td><td>"+v[13]+"</td><td>"+v[14]+"</td><td>"+v[18]+"</td></tr>";
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
 /* if(officeType == 'zone'){
	
	$("#zoneTd").show();
	$("#zonelabel").show();
	$("#circle").show();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	
	
	
	
}


else if(officeType == 'circle'){
	
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circle").show();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	
	
	
	
	
}else if (officeType == 'division'){
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circleTd").hide();
	$("#circlelabel").hide();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	
	
	
	
}else if(officeType == 'subdivision'){
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circleTd").hide();
	$("#circlelabel").hide();
	$("#divisionTd").hide();
	$("#divisionlabel").hide();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
}  */


</script>
 <script>
  
  </script>
