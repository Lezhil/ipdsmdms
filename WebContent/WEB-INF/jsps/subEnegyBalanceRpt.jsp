<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
$(".page-content")
.ready(
		function() {
			App.init();
			FormComponents.init();
			//loadSearchAndFilter("npp_sample");
			//loadSearchAndFilter('sample_1');
			//loadSearchAndFilter('sample_2');
			loadSearchAndFilter('sample_1');
			$('#eaId,#substationEnergyBalanceId')
					.addClass('start active ,selected');
			$(
					'#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
					.removeClass('start active ,selected');
			

			$("#govtscheme").val("").trigger("change");
			$("#inputEnrgy").val("").trigger("change");

		});

</script>

<script>


function showCircle(zone) {
	$
			.ajax({
				url : './getCircleByZone',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone
				},
				success : function(response) {
					var html = '';
					html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#LFcircle").html(html);
					$('#LFcircle').select2();
				}
			});
} 


function showTownNameandCode(circle){
	var zone = $('#LFzone').val();
	//var zone =  '${newRegionName}';   
	   $.ajax({
	      	url:'./getTownNameandCodebyCircle',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	  			zone : zone,
	  			circle:circle
	  		},
	  		success : function(response1) {
	  			
                var html = '';
                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
               
                for (var i = 0; i < response1.length; i++) {
                    //var response=response1[i];
                    
                    html += "<option value='"+response1[i][0]+"'>"
                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
                               }
                html += "</select><span></span>";
                $("#LFtownTd").html(html);
                $('#LFtown').select2();
                
            }
	  	});
	  }

function showResultsbasedOntownCode(town) {
	//alert(town);
	//$('#substation').val('').trigger('change');
	var town = $('#LFtown').val();
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	
	$('#substation').empty();
	$('#substation').find('option').remove();
	$('#substation').append($('<option>', {
		value : "",
		text : "Select Sub-Station"
	}));
	$('#substation').append($('<option>', {
		value : "%",
		text : "ALL"
	}));
	
	$.ajax({
		url : './showSubStaTionByTown',
		type : 'GET',
		data : {
			town : town,
			zone:zone,
			circle:circle
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response1) {
		//	alert(response1);
			//$("#substation").val("").trigger("change");
			for (var i = 0; i < response1.length; i++) {
				var resp = response1[i];
				$('#substation').append($('<option>', {
					value : resp[0],
					text : resp[1]
				}));
			}
		}
	});
}

function getDailysubstation(){
	var circle=$("#circleId").val();
	var division=$("#divisionId").val();
	var sdocode=$("#sdonameId").val();
	var substation=$("#substationDateId").val();
	var fromdate=$("#DailyFDId").val();
	var todate=$("#DailyTDId").val();
	
	$("#sustationEnergyDataId").empty();
	
	var ssid = $('#substation').val();
	var town = $('#LFtown').val();
	var circle =$("#LFcircle").val();
	var zone=$("#LFzone").val();
	var MonthYearId=$('#MonthYearId').val();
	
	if(zone=='' || zone==null){
		bootbox.alert("Please Select Region");
		return false;
	}
	
	if(circle=='' || circle==null){
		bootbox.alert("Please Select circle");
		return false;
	}
	
	if(town=='' || town==null){
		bootbox.alert("Please Select Town");
		return false;
	} 
	if(ssid=='' || ssid==null){
		bootbox.alert("Please Select Sub-Station");
		return false;
	}

	 if(circle=='' || circle==0){
		 bootbox.alert("Please select Circle");
		 return false;
	 } 
	 
	 if(division=='' || division==0){
		 bootbox.alert("Please select Division");
		 return false;
	 }
	 
	 if(sdocode=='' || sdocode==0){
		 bootbox.alert("Please select Subdivision");
		 return false;
	 }
	 
	 if(substation=='' || substation==0){
		 bootbox.alert("Please select Substation");
		 return false;
	 }
	 
	 if(fromdate=='' || fromdate==0){
		 bootbox.alert("Please select From Date");
		 return false;
	 }
	 
	 if(todate=='' || todate==0){
		 bootbox.alert("Please select To Date");
		 return false;
	 } 
	 if(fromdate>todate){
		 bootbox.alert("wrong data inputs");
			return false;
	 }
	 $('#imageee').show();
          $.ajax({
        	  url:"./dailysubenergysample",
        	  type:'POST',
        	  data:{
        			 zone : zone,
        			 circle : circle,
        			 town : town,
        			 ssid : ssid,
        			 MonthYearId : MonthYearId
        	  },
        	  success:function(response){
        		  $('#imageee').hide();
        		  var html="";
        		  if(response.length>0){
        			  $('#sample_1').dataTable().fnClearTable();
        			  for (var i = 0; i < response.length; i++) 
        			  {
        				  var resp=response[i];
        					   html+="<tr>"+
        					  "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
          					  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
          					  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
          					  "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
          					  "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
          					  "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
          					  
        					 /*  "<td><a href='#' onclick=view(this.id,"+resp[0]+") id="+resp[8]+" >view</a></td >"+  */
        					  +"</tr>";	
        					 // $('#sample_1').dataTable().fnClearTable();
        					  		
        			   } 
        			  $("#sustationEnergyDataId").html(html);
					  loadSearchAndFilter('sample_1'); 			
        		   }
        		   else
        			   {
        			   bootbox.alert("Data Not Found For given Input Feilds");
        			   return false;
        			   }
        	  },
        	  complete: function()
      		{  
      			loadSearchAndFilter('sample_1'); 
      		}
        	  
        	  
          });	
	dailyfeedersubenergy();
}
function dailyfeedersubenergy(){
	var circle=$("#circleId").val();
	var division=$("#divisionId").val();
	var sdocode=$("#sdonameId").val();
	var substation=$("#substationDateId").val();
	var fromdate=$("#DailyFDId").val();
	var todate=$("#DailyTDId").val();
	
	
	
          $.ajax({
        	  url:"./dailyfeedersubenergy",
        	  type:'POST',
        	  data:{
        		    
        			 substation:substation,
        			 fromdate:fromdate,
        			 todate:todate
        	  },
        	  success:function(response){
        		  var html="";
    			  for (var i = 0; i < response.length; i++) 
    			  {
    				  var resp=response[i];
    					   html+="<tr>"+
    					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
     					  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
     					  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
     					  "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
     					  "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
     					  "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
     					  "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
     					  "<td>"+(resp[7]==null?"":resp[7])+" </td>"+
     					  "<td>"+(resp[8]==null?"":resp[8])+" </td>"+
    					  "<td>"+(resp[9]==null?"":resp[9])+" </td>"+
    					  "<td>"+(resp[10]==null?"":resp[10])+" </td>"+
    					  +"</tr>";						
    			   }   	
    			  $('#sample_2').dataTable().fnClearTable();
    			  $("#feederEnergyDataId").html(html);
    			  loadSearchAndFilter('sample_2'); 
        	  },
        	  complete: function()
      		{  
      			loadSearchAndFilter('sample_2'); 
      		}
        	  
        	  
          });	
} 

function Monthlysubenrgy(){

	var circle=$("#circleId").val();
	var division=$("#divisionId").val();
	var sdocode=$("#sdonameId").val();
	var substation=$("#substationMonthId").val();
	var fromdate=$("#FromMntId").val();
	var todate=$("#toMntId").val();
	$("#sustationEnergyDataId").empty();
	
	 if(circle=='' || circle==0){
		 bootbox.alert("Please select Circle");
		 return false;
	 } 
	 
	 if(division=='' || division==0){
		 bootbox.alert("Please select Division");
		 return false;
	 }
	 
	 if(sdocode=='' || sdocode==0){
		 bootbox.alert("Please select Subdivision");
		 return false;
	 }
	 
	 if(substation=='' || substation==0){
		 bootbox.alert("Please select Substation");
	 }
	 
	 if(fromdate=='' || fromdate==0){
		 bootbox.alert("Please select From Date");
		 return false;
	 }
	 
	 if(todate=='' || todate==0){
		 bootbox.alert("Please select To Date");
		 return false;
	 } 
	 
	 if(fromdate>todate){
		 bootbox.alert("wrong data inputs");
			return false;
	 }
	 $('#imageee').show();
   $.ajax({
	   url:"./monthlysubenergy",
	   type:'POST',
	   data:{
		  
			 substation:substation,
			 fromdate:fromdate,
			 todate:todate
	   },
	   success:function(response){
		   $('#imageee').hide();
		   var html="";
		   if(response.length>0){
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
					   html+="<tr>"+
					  "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
  					  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
  					  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
  					  "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
  					  "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
  					  "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
  					  "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
  					  "<td>"+(resp[7]==null?"":resp[7])+" </td>"+
  					  "<td>"+(resp[8]==null?"":resp[8])+" </td>"+
					 /*  "<td><a href='#' onclick=view(this.id,"+resp[0]+") id="+resp[8]+" >view</a></td >"+  */
					  +"</tr>";	
					  $('#sample_1').dataTable().fnClearTable();
					  
					  				
			   }
			  $("#sustationEnergyDataId").empty();
			  $("#sustationEnergyDataId").html(html);
			  loadSearchAndFilter('sample_1'); 	 
		   }
		   else
			   {
			   bootbox.alert("Data Not Found For given Input Feilds");
			   return false;
			   }
			 
	   },
	   complete: function()
 		{  
 			loadSearchAndFilter('sample_1'); 
 		}
   });
   mntlyfeedersubenergy(); 
}
function mntlyfeedersubenergy(){
	var substation=$("#substationMonthId").val();
	var fromdate=$("#FromMntId").val();
	var todate=$("#toMntId").val();
	 $.ajax({
		 url:"./mntlyfeedersubenergy",
		 type:'POST',
		 data:{
			 substation:substation,
			 fromdate:fromdate,
			 todate:todate
		 },
		  success:function(response){
			  var html="";
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
					   html+="<tr>"+
						  "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
	  					  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
	  					  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
	  					  "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
	  					  "<td>"+(resp[4]==null?"":resp[4])+" </td>"+
	  					  "<td>"+(resp[5]==null?"":resp[5])+" </td>"+
	  					  "<td>"+(resp[6]==null?"":resp[6])+" </td>"+
	  					  "<td>"+(resp[7]==null?"":resp[7])+" </td>"+
	  					  "<td>"+(resp[8]==null?"":resp[8])+" </td>"+
	  					  "<td>"+(resp[9]==null?"":resp[9])+" </td>"+
	  					  "<td>"+(resp[9]==null?"":resp[10])+" </td>"+
					 
					  +"</tr>";						
			   }  
			  
			  $('#sample_2').dataTable().fnClearTable();
			  $("#feederEnergyDataId").html(html);
			  loadSearchAndFilter('sample_2'); 
			
  	  },
  	complete: function()
		{  
			loadSearchAndFilter('sample_2'); 
		}
	 });
}
function view(id,param){
	var mtrno=id;
	var billmth=param;
	
	
	$("#stack1").modal('show');
	$.ajax({
		url:"./viewconsumption",
		type:'POST',
		data:{
			mtrno:mtrno,
			billmth:billmth
		},
		success:function(response){

			if(response.length>0){
			var html="";
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
					   html+="<tr>"+
					   "<td>"+(resp[0]==null?"":resp[0])+" </td>"+
	  					  "<td>"+(resp[1]==null?"":resp[1])+" </td>"+
	  					  "<td>"+(resp[2]==null?"":resp[2])+" </td>"+
	  					  "<td>"+(resp[3]==null?"":resp[3])+" </td>"+
					 
					  +"</tr>";						
			   } 
			  $('#sample_5').dataTable().fnClearTable();
			  $("#viewconsumption").html(html);
			  loadSearchAndFilter('sample_5'); 
			}
			else {
				bootbox.alert("Data NOt Found");
				return false;
				}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_5'); 
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
						<i class="fa fa-edit"></i>Bus-Bar Loss
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
							<li class="active"><a href="#tab_1_1" data-toggle="tab"
								onclick="callDailyTab('daily');">Monthly</a></li>
							<!-- <li><a href="#tab_1_1" data-toggle="tab" hidden="true"  onclick="callDailyTab('monthly');">Monthly</a></li> -->
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
								<div class="row" style="margin-left: -1px;">
									<div class="row">
										<jsp:include page="locationFilter.jsp" />
										<!-- <div class="col-md-3"><label>CIRCLE</label>
							                       	<select   id="circleId"  name="circleId" class="form-control select2me"  onchange="getDivByCircle(this.value);">
							                       	<option value="">Select Circle</option>
							                       	<option value="%">ALL</option>
													
													
													</select>
													</div>
													
													<div class="col-md-3"><label>DIVISION</label>
							                       	<select   id="divisionId" name="divisionId" class="form-control select2me" onchange="getSubDivByDivision(this.value)">
													<option value="0">Select Division</option>
													<option value="%">ALL</option>
													
													</select> -->
									</div>

									<!-- <div class="col-md-3"><label>SUBDIVISION</label>
							                       	<select   id="sdonameId" name="sdonameId" class="form-control select2me" type="text" autocomplete="off" placeholder="" onchange="showTownBySubdiv(this.value)" >
													<option value="0">Select Sub-Division</option>
													<option value="%">ALL</option>
													
													</select>
													</div>
													<div class="col-md-3"><label>TOWN</label>
							                       	<select   id="townId" name="townId" class="form-control select2me" type="text" autocomplete="off" onchange="getSubStations(this.value)">
													<option value="0">Select TOWNS</option>
													<option value="%">ALL</option> -->

									</select>
								</div>
								<!-- <div class="col-md-3">
													<strong>Town:</strong>
																	<div id="townTd" class="form-group"><select
																		class="form-control select2me input-medium" id="town"
																		name="town">
																		<option value="">Select</option>
																	<option value="%">ALL</option>
																	</select></div>
														</div> -->
							</div>
							<div class="row" id="dailySubs">
								<!-- <div class="col-md-3">
									<label><Strong>SUBSTATION</Strong></label> <select
										id="substationDateId" class="form-control select2me">
										<option value="0">Select SubStation</option>
										<option value="%">ALL</option>

									</select>
								</div> -->
								
								<div class="col-md-3">
							<div id="substaTd" class="form-group">
							<label class="control-label"><b>Sub-Station:</b></label>
								<select class="form-control select2me" id="substation"
									name="substation">
									<option value="">Select Sub-Station</option>
									<option value="%">ALL</option> 
									<c:forEach items="${substationList}" var="substation">
										<option value="${substation}">${substation}</option>
									</c:forEach>
								</select>
							</div>

						</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><Strong>From
												Date</Strong></label>
										<div class="input-group input-medium date date-picker"
											data-date-format="yyyy-mm-dd" data-date-end-date="0d"
											data-date-viewmode="years" id="fromDate">

											<input type="text" class="form-control" name="DailyFDId"
												id="DailyFDId"> <span class="input-group-btn">
												<button class="btn default" type="button" id="">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div> -->

								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label"><Strong>To Date</Strong></label>
										<div class="input-group input-medium date date-picker"
											data-date-format="yyyy-mm-dd" data-date-end-date="0d"
											data-date-viewmode="years" id="toDate">
											<input type="text" autocomplete="off" class="form-control"
												name="DailyTDId" id="DailyTDId"> <span
												class="input-group-btn">
												<button class="btn default" type="button" id="">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div> -->
								
								<div class="col-md-3" id="monthlyDiv" style="margin-top: 10px;">
						<Strong>Select Month Year  </Strong>
						<div class="input-group ">
							<input type="text" class="form-control from" id="MonthYearId"
								name="MonthYearId" placeholder="Select Month Year"
								style="cursor: pointer"> <span class="input-group-btn">
								<button class="btn default" type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
					</div>
								
							</div>

							<div class="row" id="Monthlysub" hidden="true">
								<div class="col-md-3">
									<label>SUBSTATION</label> <select id="substationMonthId"
										class="form-control select2me ">
										<option value="0">Select SubStation</option>

									</select>
								</div>


								<!-- <div class="col-md-3" id="fromMonthId" ><label>From Month</label><span style="color: red"></span>
													<div class="input-group input-medium date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
													<input  type="text" class="form-control " name="FromMntId" id="FromMntId"  ></input>
													<span class="input-group-btn"><button class="btn default" type="button"><i class="fa fa-calendar"></i></button></span></div>
													</div> -->
								<div class="col-md-3" id="fromMonthId">
									<label>From Month</label><span style="color: red"></span>
									<div class="input-group ">
										<input type="text" class="form-control from" name="FromMntId"
											id="FromMntId"></input> <span class="input-group-btn"><button
												class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button></span>
									</div>
								</div>

								<div class="col-md-3" id="toMonthId">
									<label>To Month</label><span style="color: red"></span>
									<div class="input-group ">
										<input type="text" class="form-control from" name="toMntId"
											id="toMntId"></input> <span class="input-group-btn"><button
												class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button></span>
									</div>
								</div>
							</div>


							<br>

							<button type="button" id="viewDaiy" style="margin-left: 480px;"
								onclick=" return getDailysubstation();" name="view"
								class="btn yellow">
								<b>View</b>

							</button>
							<!-- <button type="button" id="viewMonthlysub" onclick="return Monthlysubenrgy()"
										style="margin-left: 480px;" 
										name="viewMonthlysub" class="btn yellow">
										<b>View</b>
										
									</button> -->
						</div>
					</div>
				</div>
				<br>
				<div id="imageee" hidden="true" style="text-align: center;">
					<h3 id="loadingText">Loading..... Please wait.</h3>
					<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
						style="width: 4%; height: 4%;">
				</div>
				<div class="portlet box grey">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>Bus-Bar Loss
						</div>
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<!-- <li><a href="#" id="print">Print</a></li> -->
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_1', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					</div>

					<div class="portlet-body">
						<!-- <table class="table table-striped table-hover table-bordered"
										id="sample_1"> -->
						<table class="table  table-bordered table-hover" id="sample_1">
							<thead>
								<tr>
									
									<th>Substation Name</th>
									<th>Substation_ID</th>
									<th>Cumulative Energy<br> I/C Feeders
									</th>
									<th>Cumulative Energy<br> O/G Feeders
									</th>
									<th>Total Substation<br> Loss(kWh)
									</th>
									<th>Loss %</th>
									<!-- <th>View <br>Details</th> -->
								</tr>
							</thead>
							<tbody id="sustationEnergyDataId">
							</tbody>
						</table>
					</div>
				</div>



				<!-- <div class="portlet box grey">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Feeder Wise Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="print">Print</a></li>
									<li><a href="#" id="excelExport1"
										onclick="tableToExcel1('sample_2', 'Feederwise Details')">Export
											to Excel</a></li>
								</ul>
							</div>
					</div>
				</div>

				<div class="portlet-body">
									<table class="table table-striped table-hover table-bordered"
										id="sample_2" >
										<thead>
											<tr >
												<th ><span id="thDateFiledFeederId" hidden="true">Date</span><span id="thMonthFiledFeederId" hidden="true" >MonthYear</span></th>
												<th>sdocode</th>
												<th>Sdoname</th>
												<th>Substation<br> Name</th>
												<th>Substation ID</th>
												<th>Feeder <br>Name</th>
												<th>Feeder ID</th>
												<th>Feeder Type</th>
												<th>Feeder <br>Voltage Level</th>
												<th>Feeder MF</th>
												<th>Consumption<br>(kWh)</th>
											</tr>
										</thead>
										<tbody id="feederEnergyDataId">
										
										</tbody>
									</table>
										</div>
										</div> -->

			</div>
		</div>
	</div>
</div>
</div>
</div>




<script>

function getAllLocation(officeCOde,officeType)
{
	$.ajax({
		type : 'GET',
		url : "./getAllLocationData",
		data:{officeCOde:officeCOde,officeType:officeType},
		async : false,
		cache : false,
		success : function(response)
		{
			var html="";
			if(response!=null)
				{
				if(officeType=="discom"){
				html+="<option value=0>Select Circle</option><option value=%>ALL</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#circleId").empty();
				$("#circleId").append(html);
				}

				else if(officeType=="division"){
					var htmlCircle=""; 
					var htmlDivision=""; 
					//var htmlSubdivision=""; 
					for(var i=0;i<response.length;i++)
					{
						htmlCircle+="<option value='"+response[i][0]+"'>"+response[i][0]+"</option>"; 
						htmlDivision+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
						//htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
					}
					$("#circleId").empty();
					$("#circleId").append(htmlCircle);
					$("#divisionId").empty();
					$("#divisionId").append(htmlDivision);
					//$("#sdonameId").empty();
					//$("#sdonameId").append(htmlSubdivision);
					
					}
				else if(officeType=="circle"){
					html+=""; 
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
						}
					$("#circleId").empty();
					$("#circleId").append(html);
					getDivByCircle($("#circleId").val());
					}
				
				else if(officeType=="subdivision"){
					var htmlCircle=""; 
					var htmlDivision=""; 
					var htmlSubdivision=""; 
					for(var i=0;i<response.length;i++)
					{
						htmlCircle+="<option value='"+response[i][0]+"'>"+response[i][0]+"</option>"; 
						htmlDivision+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
						htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
					}
					$("#circleId").empty();
					$("#circleId").append(htmlCircle);
					$("#divisionId").empty();
					$("#divisionId").append(htmlDivision);
					$("#sdonameId").empty();
					$("#sdonameId").append(htmlSubdivision);
					getSubStations($('#sdonameId').val());
					}
				
				}
			
		}
	});
}
function getDivByCircle(circle)
{
	var zone="%";
	$.ajax({
		type : 'GET',
		url : "./getDivByCircle",
		data:{circle:circle,zone:zone},
		async : false,
		cache : false,
		success : function(response)
		{
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Division</option><option value=%>ALL</option>"; 
				for(var i=0;i<response.length;i++)
					{
						html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#divisionId").empty();
				$("#divisionId").append(html);
				$("#divisionId").select2();
				$("#sdonameId").select2();
				}
		}
	});
}
function getSubDivByDivision(division)
{
	var zone = "%";
	var circle = $('#circleId').val();
	$.ajax({
	type : 'GET',
	url : "./getSubdivByDiv",
	data:{
		 zone : zone,
	  	 circle : circle,
	 	 division : division
	 	 },
		 async   : false,
		 cache   : false,
		 success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Sub-Division</option><option value=%>ALL</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#sdonameId").empty();
				$("#sdonameId").append(html);
				$("#sdonameId").select2();
				}
		}
	});
}

/* function getSubStations(subdivision)
{
	$.ajax({
		type : 'GET',
		url : "./getSubStations",
		data:{subdivision:subdivision},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select SubStation</option><option value=%>ALL</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
					}
				$("#substationDateId").empty();
				$("#substationDateId").append(html);
				$("#substationMonthId").empty();
				$("#substationMonthId").append(html);
				}
			
		}
	});
} */

function getSubStations(towncode)
{
	$.ajax({
		type : 'GET',
		url : "./getSubStationsByTownCode",
		data:{towncode:towncode},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select SubStation</option><option value=%>ALL</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
					}
				$("#substationDateId").empty();
				$("#substationDateId").append(html);
				$("#substationMonthId").empty();
				$("#substationMonthId").append(html);
				}
			
		}
	});
} 
function showTownBySubdiv(subdiv) {
      var zone = "%";
      var circle = $('#circleId').val();
      var division = $('#divisionId').val();
     // alert("z-"+zone+" circle-"+circle);
      //alert("d-"+division+" sub-"+subdiv);
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
              subdivision :subdiv
          },
                  success : function(response) {
                      /* 
                      var html = '';
                      html += "<select id='townId' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                      for (var i = 0; i < response1.length; i++) {
                          //var response=response1[i];
                          html += "<option value='"+response1[i][1]+"'>"
                                  + response1[i][1] + "</option>";
                      }
                      html += "</select><span></span>";
                      $("#townTd").html(html);
                      $('#town').select2(); */
                	//alert(response);
          			var html="";
          			if(response!=null)
          				{
          				html+="<option value=0>Select Town</option><option value=%>ALL</option>"; 
          				for(var i=0;i<response.length;i++)
          					{
          					html+="<option value='"+response[i][0]+"'>"+response[i][0]+"-"+response[i][1]+"</option>"; 
          					}
          				$("#townId").empty();
          				$("#townId").append(html);
          				$("#townId").select2();
          				
          				}
          			
          		
                  }
              });
  }

function callDailyTab(param){

	if(param=="daily")
	{
		
		$("#viewMonthlysub").hide();
		$("#viewDaiy").show();
		$("#dailySubs").show();
		$("#Monthlysub").hide();
		$("#thDateFiledSubId").show();
		$("#thMonthFiledSubId").hide();
		$("#thDateFiledFeederId").show();
		$("#thMonthFiledFeederId").hide();
	/* document.getElementById('fromDateId').style.display='block';
	document.getElementById('todateId').style.display='block'; 
	document.getElementById('fromMonthId').style.display='none'; 
	document.getElementById('toMonthId').style.display='none';  */
	/* $("#fromDateId").show();
	$("#todateId").show();
	$("#fromMonthId").hide();
	$("#toMonthId").hide(); */
	
		$('#DailyFDId').val('').trigger('change');
		$('#DailyTDId').val('').trigger('change');
		
		$('#sustationEnergyDataId').empty();
		$('#feederEnergyDataId').empty();
	}
	if(param=="monthly")
	{
		$("#viewMonthlysub").show();
	     $("#viewDaiy").hide();
		$("#Monthlysub").show();
		$("#dailySubs").hide();
		$("#thDateFiledSubId").hide();
		$("#thMonthFiledSubId").show();
		$("#thDateFiledFeederId").hide();
		$("#thMonthFiledFeederId").show();
		/* document.getElementById('fromDateId').style.display='none';
		document.getElementById('todateId').style.display='none'; 
		document.getElementById('fromMonthId').style.display='block'; 
		document.getElementById('toMonthId').style.display='block'; */
		
			$('#FromMntId').val('').trigger('change'); 
			$('#toMntId').val('').trigger('change');
			$('#sustationEnergyDataId').empty();
			$('#feederEnergyDataId').empty();
			
	}
	
}

</script>
<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<h5 class="modal-title" id="">Consumption</h5>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-hover table-bordered"
					id="sample_5">
					<thead>
						<tr>
							<th>Cumulative Energy<br>I/C Feeders
							</th>
							<th>Cumulative Energy<br> O/G Feeders
							</th>
							<th>Total Substation<br> Loss(kWh)
							</th>
							<th>Loss %</th>
						</tr>
					</thead>
					<tbody id="viewconsumption">
					</tbody>

				</table>
			</div>
		</div>
	</div>
</div>

<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});

</script>

<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});
	
	$('.yearfrom').datepicker
	({
	    format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});


	
	function showMonthYear(val){
		//alert(val);
		if(val=='month'){
			$("#monthlyDiv").show();
			$("#yearlyDiv").hide();
			$("#monthYearLevel").text("Month");
		} else if(val=='year'){
			$("#monthlyDiv").hide();
			$("#yearlyDiv").show();
			$("#monthYearLevel").text("Year");
		}
		
	}
	function showFeederDiv(val){
		if(val=='town'){
			$("#feederDiv").hide();
			$("#townFeederLevel").text("Town");
		} else if(val=='DT'){
			$("#feederDiv").show();
			$("#townFeederLevel").text("DT");
			var town = $("#LFtown").val();
			showResultsbasedOntownCode(town);
			
			
		}
		
	}

	
</script>
