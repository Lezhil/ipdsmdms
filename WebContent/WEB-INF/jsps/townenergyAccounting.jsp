<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<html>
<body>
	<script type="text/javascript">

  $(".page-content").ready(function()
   	    	   {    
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  loadSearchAndFilter('sample1');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	  $("#tableid").show();
	   	    	 
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   		$('#eaId,#townenergyAccounting').addClass('start active ,selected');
	    	 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	   });

  $("#month").datepicker({
	    format: "mm-yyyy",
	    startView: "months", 
	    minViewMode: "months"
	}); 
  

 
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
      		html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
  			for( var i=0;i<response.length;i++)
  			{
  				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
  			}
  			html+="</select><span></span>";
  			$("#LFcircleTd").html(html);
  			$('#LFcircle').select2();
      	}
  	});
  }
  
	
	
	function showTownNameandCode(circle){
	var zone =  $('#LFzone').val(); 
	
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
              html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium' onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
				
              
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

	
	
	
	
  function showTownBySubdiv(subdiv) {
	     // var zone = $('#zone').val();
	      var circle = $('#circle').val();
	      var division = $('#division').val();
	      $.ajax({
	          url : './getTownNameandCodeBySubDiv',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	        	  sitecode :subdiv
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  + response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	              });
	  }


	function showResultsbasedOntownCode(townCode) {
		//alert(townCode);
		var region = $("#LFzone").val();
		var circle = $("#LFcircle").val();

	$('#feederTpId').val('').trigger('change');
	//var town = $('#townDT').val();
	$('#feederTpId').empty();
	$('#feederTpId').find('option').remove();
	$('#feederTpId').append($('<option>', {
		value : "",
		text : "Select Feeder"
	}));
	$('#feederTpId').append($('<option>', {
		value : "%",
		text : "ALL"
	}));

	$.ajax({
		url : './getFeederBySelection',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		cache : false,
		data : {
			townCode : townCode,
			circle :circle,
			region : region
		},
		success : function(response1) {
			var html = '';
			    html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>ALL</option>";
			   for (var i = 0; i < response1.length; i++) {
			       html += "<option value='"+response1[i][0]+"'>"
			               + response1[i][1] + "</option>";
			   }
			   html += "</select><span></span>";
			   $("#feederDivId").html(html);
			   $('#feederTpId').select2(); 

			/* for (var i = 0; i < response1.length; i++) {
				var resp = response1[i];
				$('#feederTpId').append($('<option>', {
					value : resp[0],
					text : resp[1]
				}));
			}
		   html += "</select><span></span>"; */
		 

		}
	});
}



  function view(){
   var zone=$("#LFzone").val();
  var circle=$("#LFcircle").val(); 
  var town = $("#LFtown").val();
  	var fromdate = $("#selectedFromDateId").val(); 
  	var feederTpId=$("#feederTpId").val();

  	var periodMonth = $("#periodMonth").val();
  	if(zone == ''){
  		bootbox.alert("Please Select  Region");
  		return false;
  	
  	}
  	if(circle == ''){
  		bootbox.alert("Please Select  Circle");
  		return false;
  	
  	}
  	
  	if(town == ''){
  		bootbox.alert("Please Select  Town");
  		return false;
  	
  	}
  	if(feederTpId == ''){
  		bootbox.alert("Please Select  Feeder");
  		return false;
  	
  	}

  	$("#imageee").show();
  		$.ajax({
  			url:"./getFeedertownEAReport",
  			type: 'GET', 
  			data:{
  				
  				monthyear:fromdate,
  				feederTpId:feederTpId,
  				periodMonth:periodMonth,
  				circle:circle,
  				town:town
  				
  				},
  				 success: function(data) {
  			      

  			            $("#imageee").hide();
  			          
  			            if (data != null && data.length > 0) {

  							  var html="";
  					        	 // alert(response);
  					        	   for (var i = 0; i < data.length; i++) {

  								

  					        		   var resp = data[i];
  					        		  
  										var j = i + 1;


  										html += "<tr>" 
  											+ "<td>"+ j + "</td>"
  											+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
  											+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
  											+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"	
  											+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"	
  											+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
  											+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"	
  											+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
  											+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
  											+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
  											+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
  											+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
  											+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
  											+ "<td>"+ ((resp[20] == null) ? "": resp[20]) + " </td>"
  											+ "<td>"+ ((resp[22] == null) ? "": resp[22]) + " </td>";			
  											+"</tr>";
  										
  			        	   }

  					        	   $('#sample1').dataTable().fnClearTable();
  					            	$("#getfdrlossesbody").html(html);
  					            	loadSearchAndFilter('sample1');
  					        	   
  							 
  						 }else {
  								bootbox.alert("No Relative Data Found.");
  							}  
  	 			            
  						
  			            
  		            }
  	});

  	 inputEnergy();
  }


	function inputEnergy(){

		   var zone=$("#LFzone").val();
		   var circle=$("#LFcircle").val(); 
		   var town = $("#LFtown").val();
		   	var fromdate = $("#selectedFromDateId").val(); 
		   	var feederTpId=$("#feederTpId").val();

		   	var periodMonth = $("#periodMonth").val();
		   	if(zone == ''){
		   		bootbox.alert("Please Select  Region");
		   		return false;
		   	
		   	}
		   	if(circle == ''){
		   		bootbox.alert("Please Select  Circle");
		   		return false;
		   	
		   	}
		   	
		   	if(town == ''){
		   		bootbox.alert("Please Select  Town");
		   		return false;
		   	
		   	}
		   	if(feederTpId == ''){
		   		bootbox.alert("Please Select  Feeder");
		   		return false;
		   	
		   	}


		  	$("#imageee").show();
		  		$.ajax({
		  			url:"./getFeederinputEAReport",
		  			type: 'GET', 
		  			data:{
		  				
		  				monthyear:fromdate,
		  				feederTpId:feederTpId,
		  				periodMonth:periodMonth,
		  				circle:circle,
		  				town:town
		  				
		  				},
		  				 success: function(data) {
		  			      

		  			            $("#imageee").hide();
		  			          
		  			            if (data != null && data.length > 0) {

		  							  var html="";
		  					        	 // alert(response);
		  					        	   for (var i = 0; i < data.length; i++) {

		  								

		  					        		   var resp = data[i];
		  					        		  
		  										var j = i + 1;


		  										html += "<tr>" 
		  											+ "<td>"+ j + "</td>"
		  											+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
		  											+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
		  											+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"	
		  											+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
		  											+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
		  											+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"	
		  											+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
		  											+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
		  											+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
		  											+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
		  											+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>"
		  											+ "<td>"+ ((resp[18] == null) ? "": resp[18]) + " </td>";			
		  											+"</tr>";
		  										
		  			        	   }

		  					        	   $('#sample2').dataTable().fnClearTable();
		  					            	$("#getinputenergy").html(html);
		  					            	loadSearchAndFilter('sample2');
		  					        	   
		  							 
		  						 }else {
		  								bootbox.alert("No Relative Data Found.");
		  							}  
		  	 			            
		  						
		  			            
		  		            }
		  	});
					





		}
	
  

	
  </script>
  
  	
	<div class="page-content"   >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Feeder Level- Energy Accounting </div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

						
		<div class="portlet-body ">
					
			<jsp:include page="locationFilter.jsp"/> 
				<div class="row" style="margin-left: -1px;">
				
					<!-- <div class="col-md-3">
								<strong>Town:</strong>
												<div id="feederDivId" class="form-group"><select
													class="form-control select2me input-medium" id="tptown" 
													name="tptown">
													<option value="">Select Town</option>
													<option value="%">All</option>
												</select></div>
						</div> 	 -->	
					
						<div class="col-md-3">
								<strong>Feeder:</strong>
												<div id="feederDivId" class="form-group"><select
													class="form-control select2me input-medium" id="feederTpId" 
													name="feederTpId">
													<option value="">Select Feeder</option>
													<option value="%">All</option>
												</select></div>
						</div> 		
						 		
									<div class="col-md-2">
								<strong>Report Month :</strong>
												<div id="feederDivId" class="form-group"><input type="text" class="form-control from"  name="selectedDateName" id="selectedFromDateId"  
										 placeholder="Select Report Month" style="cursor: pointer"></div>
									</div>
									
									
									
							<div class="col-md-2">
								<strong>Select Period :</strong>
												<div id="fromDateMonthly" class="form-group"><select class="form-control select2me"
												id="periodMonth" name="periodMonth">
												<option value="12">12</option>
												<option value="10">10</option>
												<option value="08">8</option> 
												<option value="06">6</option>
												<option value="04">4</option>
												<option value="02">2</option>
												<option value="00">0</option>
											</select></div>
									</div>
								
									<div class="col-md-2">
									<button type="submit" onclick="view()" style="margin-top: 15px;" class="btn green">Generate</button></div>
						</div>

	
					
					
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
					</div>					
					
				<div class="row" id="tableid">
						
					<div class="col-md-12">
						
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li id="print"><a href="#">Print</a></li> -->
								<!-- <li><a href="#" id=""
									onclick="exportReport('sample1','DT Losses')">Export to PDF</a></li> -->
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample1', 'Feeder Level Energy Losses')">Export to Excel</a>
								</li> 
								
							</ul>
						</div>
						<div class="table-responsive">
								
								
								<table class="table table-striped table-hover table-bordered"
							id="sample1">
							
										<thead>
										
											<tr>
												<th rowspan="2" style="text-align: center;">SL<br> NO</th>
												<th colspan="12" style="text-align: center;" > TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION</th>
											</tr>
											
										<tr>
											<th>Report <br> Month</th>
											<th>Feeder<br> Name</th>
											<th>Feeder <br> Code</th>
											<th>Boundary <br> Feeder</th>
											<th style="text-align:center;">Meter<br>Number</th>
											<th>Toatl <br> Consumers</th>
											<th style="text-align:center;">Units Supplied <br> A</th>
											<th style="text-align:center;">Units Billed <br> B</th>
											<th style="text-align:center;">Amount Billed <br> c</th>
											<th style="text-align:center;">Amount Collected <br> D</th>
											<th style="text-align:center;">Billing Efficiency <br> (B/A)*100</th>
											<th style="text-align:center;">Collection Efficiency <br> (D/C)*100</th>
											<th> Technical Loss</th>
											<th>AT&C Loss</th>
											
											
										</tr>
										
										</thead>
									
									<tbody id="getfdrlossesbody">
									</tbody>
							
							
							</table>
						
						</div>
						
					<div class="col-md-12">
						
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li id="print"><a href="#">Print</a></li> -->
								<!-- <li><a href="#" id=""
									onclick="exportReport('sample1','DT Losses')">Export to PDF</a></li> -->
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample2', 'Input Energy LEVEL')">Export to Excel</a>
								</li> 
								
							</ul>
						</div>
						
						</div>
						
						<div class="table-responsive">
						
									<table class="table table-striped table-hover table-bordered" id="sample2">
									
									
									
										<thead>
										
										
										<tr>
											<th rowspan="2" style="text-align: center;">SL<br> NO</th>
											<th rowspan="2" style="text-align: center;">Substation</th>
											<th colspan="4" style="text-align: center;" > FEEDER METER</th>
											<th colspan="7" style="text-align: center;" >  BOUNDARY METERS</th>
										</tr>
												
													
											<tr>
												<th>Feeder Name</th>
												<th>feeder <br> Code</th>
												<th>Meter Sl <br> No.</th>
												<th>Net Import <br>Energy(Kwh)<br>A</th>
												<th>Boundary <br> Name</th>
												<th>Bounry <br> Code</th>
												<th>Meter Sl <br> No.</th>
												<th>IMPORT(KWH)<br> B</th>
												<th>EXPORT(KWH)<br> C</th>
												<th>NET Energy- <br> Boundary(Kwh)<br>D=B-C</th>
												<th>NET Energy<br> of the feeder <br> (Kwh)<br>E=A-D</th>
												
											</tr>
											
											
											
											<tr>
											
												<th colspan="12" style="text-align: center;">Net input Energy of the feeder in kwh</th>
											
											</tr>
										</thead>
											
									<tbody id="getinputenergy">
									</tbody>
									
									
									
									
									</table>
							
						
						
						
						
						</div>
				
					</div>
							
			 </div>
					
		</div>
	</div>
	
	
	<script type="text/javascript">

	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();
	//var startDate = new Date();
			
			$('.from').datepicker
			({
			    format: "yyyymm",
			    minViewMode: 1,
			    autoclose: true,
			    startDate :new Date(new Date().getFullYear()),
			    endDate: new Date(year, month, '31')
			
			});
	</script>

  
  

</body>
</html>