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
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	  
	   	    	 
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#MDASSideBarContents,#meterOper,#meterAlarm').addClass('start active ,selected');
	    	 $("#MDMSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	;
	    	 
	    	
	    
	    	 //alarmData('all');
   	    	   });
  
/* 
   function officetype(){
	
           $.ajax({
			url : './getofficetype',
		    type:'GET',
			dataType:"text",
			cache:false,
			async:false,
			success:function(response)
			{
				
				 

			 if(officeType == 'circle'){
					alert(officeType);
					$("#zoneTd").hide();
					$("#zonelabel").hide();
					$("#circle").show();
					$("#divisionTd").show();
					$("#divisionlabel").show();
					$("#subdivTd").show();
					$("#sdoCodelabel").show();
					
					
					
				}else if (officeType == 'division'){
					alert(officeType);
					$("#zoneTd").hide();
					$("#zonelabel").hide();
					$("#circleTd").hide();
					$("#circlelabel").hide();
					$("#divisionTd").show();
					$("#divisionlabel").show();
					$("#subdivTd").show();
					$("#sdoCodelabel").show();
					
					
					
					
				}else if(officeType == 'subdivision'){
					alert(officeType);
					$("#zoneTd").hide();
					$("#zonelabel").hide();
					$("#circleTd").hide();
					$("#circlelabel").hide();
					$("#divisionTd").hide();
					$("#divisionlabel").hide();
					$("#subdivTd").show();
					$("#sdoCodelabel").show();
				} 
			 
			}
          
		}); 
         
	}  */
   
  function mtrDetails(mtrNo)
  { 
  	 window.location.href="./mtrNoDetailsMDAS?mtrno=" + mtrNo;
  }
  

  
/* function alarmData(valtype){
	  $("#eventTR tr").remove();
	  //$("#eventTR").empty();
	$.ajax({
		url:"./alarms/"+valtype,
		type:"GET",
		success:function(response){
			var html="";
			$.each(response, function( index, v ) {
				
				if(v[4]==57 || v[4]==58 || v[4]==59 ||  v[4]==60 ||  v[4]==61 ||  v[4]==62 ||  v[4]==251 ||  v[4]==201 ||  v[4]==202   ){
					html+="<tr bgcolor='red'><td>"+moment(v[0]).format('DD-MM-YYYY HH:mm:ss')+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td><a onclick='mtrDetails("+v[3]+");'  style='color:blue;'>"+v[3]+"</a></td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>";
					if(v[7]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[7]+"</td>";
					}
					if(v[8]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[8]+"</td>";
					}
					if(v[9]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[9]+"</td>";
					}
					if(v[10]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[10]+"</td>";
					}
					if(v[11]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[11]+"</td>";
					}
					if(v[12]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[12]+"</td>";
					}
					
					
				}
				else{
					
					html+="<tr bgcolor='yellow'><td>"+moment(v[0]).format('DD-MM-YYYY HH:mm:ss')+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td><a onclick='mtrDetails("+v[3]+");'  style='color:blue;'>"+v[3]+"</a></td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>";
					if(v[7]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[7]+"</td>";
					}
					if(v[8]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[8]+"</td>";
					}
					if(v[9]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[9]+"</td>";
					}
					if(v[10]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[10]+"</td>";
					}
					if(v[11]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[11]+"</td>";
					}
					if(v[12]==null){
						html+="<td></td>";
					}
					else{
						html+="<td>"+v[12]+"</td>";
					}
					
				}
				
				
				});
			
			clearTabledataContent('sample_3');
			$("#eventTR").html(html);
			 //loadSearchAndFilter('sample_3');  
			//eventTR
		},
	 complete: function()
		{  
			loadSearchAndFilter('sample_3');
		}  

	});
	
} */
 
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
	
	function showDivision(circle)
	{
		var zone="All";
		$.ajax({
			url : './showDivisionMDM' + '/' + zone + '/' + circle,
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
					$("#divisionTd").html(html);
					$('#division').select2();
		    	}
			});
	}
	
	
	function showSubdivByDiv(division)
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
	}
	
  </script>
<div class="page-content" >
			
			
		
	    
	    <div class="portlet box blue "  id="boxid">
	    <div class="portlet-title">
										<div class="caption"><i class="fa fa-reorder"></i>Meter Exceptions/Alarms</div>
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
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport2" 
									onclick="tableToExcel2('sample_3', 'AlarmDetails')">Export
										to Excel</a></li>
							</ul>
										</div>
										</div></div>
										<%-- <c:if test="${officeType}">	
										officetype='zone'								
										$("#zone").show();		
											</c:if> --%>
											
										
										
										
										
										
										
										
								<table><tbody>
								<!-- 
								<tr>
								  <th class="block">Office Type&nbsp;:</th>
								<th id=""><select class="form-control select2me input-medium"
									id="" name="" onchange="officeTypeSelect();">
									<option value=""></option>
									    <option value="zone">ZONE</option>
										<option value="circle">CIRCLE</option>
										<option value="division">DIVISION</option>
										<option value="subdivision">SUBDIVISION</option>
								</select></th>
								
							</tr> -->
					<!-- 		<tr>
								
										<th class="block" id='zonelabel'>Zone&nbsp;:</th>
								<th id="zoneTd"><select class="form-control select2me input-medium"
									id="zone" name="circle" onchange="showCircle(this.value);">
									<option value=""></option>
										<option value="JVVNL">JVVNL</option>
								</select></th>
								
										<th class="block" id='circlelabel'>Circle&nbsp;:</th>
						<th id="circleTd"><select
							class="form-control select2me input-medium" id="circle"
							name="circle" onchange="showDivision(this.value);">

						</select></th>
						<th class="block" id=divisionlabel >Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
										
								</select></th></tr> -->
							
								<tr>
							<!-- 	<th class="block" id=sdoCodelabel>Subdivision&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
										
								</select></th> -->
								
								<!-- <th colspan="4" ></th> -->
                                <td style="width:150px;"><b>Meter Sr.Number :</b></td>
                                 <td><input class="form-control input-medium" name="mtrno" id="mtrno" >
                                 </td>
                                 
							</tr>
								
								<tr><th colspan="8"></th></tr><tr><th colspan="8"></th></tr>
										 <tr><th class="block">From&nbsp;Date&nbsp;:</th>
										 <th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years"  data-date-end-date="0d" data-date-viewmode="years">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th class="block">To&nbsp;Date&nbsp;:</th><th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" data-date-end-date="0d" data-date-viewmode="years">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th colspan="4" >
																<div >
																
									<div class="col-md-offset-4 col-md-8">                        
										<!-- <button type="button" class="btn yellow">Reset</button>   -->
										<button type="submit" onclick="validation()" class="btn green">View</button> 
									</div>
								</div>
																</th>
																</tr>
							</tbody>
								</table><br><br>
								
						<div class="text-center">
							<button type="button" value="fdrCategory" onclick="return validationMtrno()" class="btn green">Location Details</button>
						</div>  	
										
												
																
					<table class="table table-striped table-hover table-bordered"  id="sample_3">
						
						<thead>
						
						
							<tr>
								<!-- <th>S.no</th>
								<th>Meter Number</th>
								<th>Subdivision Name</th>
								<th>Customer Name</th>
								<th>Customer Address</th>
								<th>Alarm Time </th>
								<th>Alarm Active Information</th> -->
								<th>S.no</th>
								<th>Meter Sr.Number</th>
								<th>Alarm ID</th>
								<th>Alarm Description</th>
								<th>Alarm Date</th>
		
							</tr>
						</thead>
						<tbody id="eventTRr">
						
		</tbody>
					</table>
					</div>
			</div>
			
	
</div>

<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<!-- <h5 class="modal-title" id="">Consumption</h5> -->
			</div>
			<div class="modal-body">	
			<table class="table table-striped table-hover table-bordered"
									id="sample_5">
									<thead>
										<tr>
                                            <th>Subdivision</th>
											<th>Category</th>
											<th>Consumer Name</th>
											<th>Account No</th>
											<th>K No</th>
											<th hidden="true">Feeder Category</th>
										</tr>
									</thead>
									<tbody id="getConsumerwiseData">
									</tbody>
								</table>
								
								<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn red">Close</button>
												</div>
		</div>
		</div>
</div>
</div>

<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<!-- <h5 class="modal-title" id="">Consumption</h5> -->
			</div>
			<div class="modal-body">	
			<table class="table table-striped table-hover table-bordered"
									id="sample_6">
									<thead>
										<tr>
                                            <th>Subdivision</th>
											<th>Feeder Name</th>
											<th>Cross Feeder</th>
											<th>Feeder Code</th>
											<th hidden="true">Feeder Category</th>
										</tr>
									</thead>
									<tbody id="getFeederwiseData">
									</tbody>
								</table>
								
								<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn red">Close</button>
												</div>
		</div>
		</div>
</div>
</div>

<div id="stack3" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<!-- <h5 class="modal-title" id="">Consumption</h5> -->
			</div>
			<div class="modal-body">	
			<table class="table table-striped table-hover table-bordered"
									id="sample_7">
									<thead>
										<tr>
                                            <th>Subdivision</th>
											<th>DT Name</th>
											<th>DT Capacity</th>
											<th>Cross DT</th>
											<th>DT Code</th>
											<th hidden="true">Feeder Category</th>
										</tr>
									</thead>
									<tbody id="getDTwiseData">
									</tbody>
								</table>
								
								<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn red">Close</button>
												</div>
		</div>
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
	//var zone=$("#zone").val();
	
	var fdate=$("#selectedFromDateId").val();
	var tdate=$("#selectedToDateId").val();
	var mtrno=$("#mtrno").val();

 /* if(zone=="")
	{
	bootbox.alert("Please Select Zone");
	return false;
	}   */
 if(mtrno=="")
	 {
	 bootbox.alert("Please Enter Meter Number")
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
		bootbox.alert("From date cannot be greater than To date.");
		return false;
	}
view();
}
function view(){
	/* var zone=$("#zone").val();
	var circle=$("#circle").val();
	var division=$("#division").val();
	var sdoname=$("#sdoCode").val(); */
	var fdate=$("#selectedFromDateId").val();
	var tdate=$("#selectedToDateId").val(); 
	var mtrno=$("#mtrno").val();
	/* if(circle=="")
		{
		circle="%";
		}
	if(division=="" || division==null)
	{
		division="%";
	}
	if(sdoname=="" || sdoname==null)
	{
		sdoname="%";
	} */

	$.ajax({
		url:"./getMeterByFdrcat/"+mtrno,
		type:"get",
		data:{
			fdate:fdate,
			tdate:tdate,
			mtrno:mtrno
		},
		success:function(res){
		          if(res.length !=0){
				  html="";
				  for (var i = 0; i < res.length; i++){
					var v=res[i];
						 html +="<tr>"
								+"<td>"+(i+1)+"</td>"
								+"<td>"+v[0]+"</td>"
								+"<td>"+v[1]+"</td>"
								+"<td>"+(v[2]== null ? "":v[2])+"</td>"
								+"<td>"+ (v[3] == null ? "" : moment(v[3]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"
								"</tr>";
				} 

				$('#sample_3').dataTable().fnClearTable();
					$('#eventTRr').html(html);
					loadSearchAndFilter('sample_3');
			}else{
				bootbox.alert("No Data Found");
			} 
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_3');
		} 
	});
	
}
 /* if(officeType == 'zone'){
	
	$("#zoneTd").show();
	$("#zonelabel").show();
	$("#circle").show();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	$("#mtrno").show();
	
	
	
} */


/* else if(officeType == 'circle'){
	
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circle").show();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	$("#mtrno").show();
	
	
	
	
} *//* else if (officeType == 'division'){
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circleTd").hide();
	$("#circlelabel").hide();
	$("#divisionTd").show();
	$("#divisionlabel").show();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	$("#mtrno").show();
	
	
	
} *//* else if(officeType == 'subdivision'){
	
	$("#zoneTd").hide();
	$("#zonelabel").hide();
	$("#circleTd").hide();
	$("#circlelabel").hide();
	$("#divisionTd").hide();
	$("#divisionlabel").hide();
	$("#subdivTd").show();
	$("#sdoCodelabel").show();
	$("#mtrno").show();
} */ 

function validationMtrno()
{
	//alert("hiii");
	var meterno=$("#mtrno").val();
	$.ajax({
		url:"./getMeterFdrwiseData",
		type:'GET',
		data:{
			meterno:meterno
		},
		 success:function(response){
		   //alert(response);
		    	if(response.length !=0){ 
		    		var html = "";
		    		for (var i = 0; i < response.length; i++){
		    			var element = response[i];
		    			
		    			if(element[5]=="HT" || element[5]=="LT"){
		    				html += "<tr>" +
							"<td>"+(element[0]==null?"":element[0])+"</td>"
							+"<td>"+(element[1]==null?"":element[1])+"</td>"
							+"<td>"+(element[2]==null?"":element[2])+"</td>"
							+"<td>"+(element[3]==null?"":element[3])+"</td>"
							+"<td>"+(element[4]==null?"":element[4])+"</td>"
							+"<td hidden='true' id='id'>"+(element[5]==null?"":element[5])+"</td>";
							
							$('#sample_5').dataTable().fnClearTable();
							$("#stack1").modal('show');
		 					$('#getConsumerwiseData').html(html);
		 					loadSearchAndFilter('sample_5');
							
		    			} else if(element[4]=="FEEDER METER" || element[4]=="BORDER METER"){
		    				html += "<tr>" +
							"<td>"+(element[0]==null?"":element[0])+"</td>"
							+"<td>"+(element[1]==null?"":element[1])+"</td>"
							+"<td>"+(element[2]==1?"Yes":"No")+"</td>"
							+"<td>"+(element[3]==null?"":element[3])+"</td>"
							+"<td hidden='true' id='id'>"+(element[4]==null?"":element[4])+"</td>";
							
		    				$('#sample_6').dataTable().fnClearTable();
		    				$("#stack2").modal('show');
		 					$('#getFeederwiseData').html(html);
		 					loadSearchAndFilter('sample_6');
		 					
		    			} else if(element[5]=="DT"){
		    				html += "<tr>" +
							"<td>"+(element[0]==null?"":element[0])+"</td>"
							+"<td>"+(element[1]==null?"":element[1])+"</td>"
							+"<td>"+(element[2]==null?"":element[2])+"</td>"
							+"<td>"+(element[3]==1?"Yes":"No")+"</td>"
							+"<td>"+(element[4]==null?"":element[4])+"</td>"
							+"<td hidden='true' id='id'>"+(element[5]==null?"":element[5])+"</td>";
							
		    				$('#sample_7').dataTable().fnClearTable();
		    				$("#stack3").modal('show');
		 					$('#getDTwiseData').html(html);
		 					loadSearchAndFilter('sample_7');
		    			}
		    		}
 				
		    	}else{
		    		bootbox.alert("No Data Found");
		    	}
		    },
		
	});
	}
	

</script>