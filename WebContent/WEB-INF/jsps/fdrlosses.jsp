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
	   	    	  $("#tableid").hide();
	   	    	 
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   		$('#eaId,#fdrlosses').addClass('start active ,selected');
	    	 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	   });

  $("#month").datepicker( {
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
                html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' onchange='showResultsbasedOntownCode(this.value)'  type='text'><option value=''>Select</option>";
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

  function showResultsbasedOntownCode(townCode){
	   $.ajax({
	      	url:'./getFeederTpandName',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	      		townCode:townCode
	  		},
	  		success : function(response1) {
	  			 var html = '';
	                html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select Feeder</option><option value='%'>All</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            + response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#feederDivId").html(html);
	                $('#feederTpId').select2();
              
           }
	  	});
	  }

  function view()
  {
	 	var zone=$("#LFzone").val();
	 	var circle=$("#LFcircle").val();

	var town=$("#LFtown").val();
	var fromdate = $("#month").val(); 


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
	if(fromdate == ''){
		bootbox.alert("Please Select  Bill Month");
		return false;

	}




	if(periodMonth == ''){
		bootbox.alert("Please Select  Period ");
		return false;

	}

	

	
 	 var html1 = "";
 	 
 	 $("#imageee").show();
 	$("#tableid").show();
 		$.ajax({
 			url:"./getfdrlossdetails",
 			type: 'GET', 
 			data : {
 				fromdate : fromdate,
				town : town,
				feederTpId : feederTpId,
				periodMonth:periodMonth
				
				},

 				 success: function(response) {
 					 $("#imageee").hide();

 					

 					var map = new Map();
 					var arr = new Set();
 					for (var i = 0; i < response.fdrcount.length; i++) {
 	 					var key_val = response.fdrcount[i];
 	 					// alert(key_val);
 					    var key = key_val[1];
 					    var value = key_val[0];
 					   
 					    map.set(key,value);

 					   
 					}

 					
 						 for(var i=0; i < response.getfdrlossdetails.length; i++){
 	 						 var html2 = "";
 	 						 var html3 = "";
 							 var resp = response.getfdrlossdetails[i];
 							 var rs = map.get(resp[0]);
 						//	 alert(resp[0])
 							 if(arr.has(resp[0]))
	 						{
 								 html2 = "<td hidden='true'  >" + ((resp[1] == null) ? "" : resp[1]) + "</td>"
 								 + "<td hidden='true' >" + ((resp[0] == null) ? "" : resp[0]) + "</td>"
 								 + "<td hidden='true' >" + ((resp[3] == null) ? "" : resp[3]) + "</td>"
 								 + "<td hidden='true' >" + ((resp[4] == null) ? "" : resp[4]) + "</td>" 
 								 + "<td hidden='true' >" + ((resp[5] == null) ? "" : resp[5]) + "</td>"
 								
	 						}
 							 else
 	 						 {
 								 html2 = "<td rowspan='"+rs+"'>" + ((resp[1] == null) ? "" : resp[1]) + "</td>"
 								 + "<td rowspan='"+rs+"' >" + ((resp[0] == null) ? "" : resp[0]) + "</td>"
 								 + "<td rowspan='"+rs+"' >" + ((resp[3] == null) ? "" : resp[3]) + "</td>"
 								 + "<td rowspan='"+rs+"' >" + ((resp[4] == null) ? "" : resp[4]) + "</td>" 
 								 + "<td rowspan='"+rs+"' >" + ((resp[5] == null) ? "" : resp[5]) + "</td>" 
 	 							 }
 							if(arr.has(resp[0]))
 								
 							{
 								html3 = "<td hidden='true'>" + ((resp[18] == null) ? "" : resp[18]) + "</td>"
								 + "<td hidden='true' >" + ((resp[13] == null) ? "" : resp[13]) + "</td>"
								 + "<td hidden='true' >" + ((resp[14] == null) ? "" : resp[14]) + "</td>"
								 + "<td hidden='true' >" + ((resp[15] == null) ? "" : resp[15]) + "</td>" 
								 + "<td hidden='true' >" + ((resp[19] == null) ? "" : resp[19]) + "</td>"  
								 + "<td hidden='true' >" + ((resp[17] == null) ? "" : resp[17]) + "</td>"  
								 + "<td hidden='true' >" + ((resp[22] == null) ? "" : resp[22]) + "</td>"
								 + "<td hidden='true' >" + ((resp[21] == null) ? "" : resp[21]) + "</td>"  
								 + "<td hidden='true' >" + ((resp[24] == null) ? "" : resp[24]) + "</td>"  
								 + "<td hidden='true' >" + ((resp[21] == null) ? "" : resp[21]) + "</td>"
								 + "<td hidden='true' >" + ((resp[23] == null) ? "" : resp[23]) + "</td>"  

	 						}
							 else
	 						 {
								 html3 = "<td rowspan='"+rs+"' >" + ((resp[18] == null) ? "" : resp[18]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[13] == null) ? "" : resp[13]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[14] == null) ? "" : resp[14]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[15] == null) ? "" : resp[15]) + "</td>" 
								 + "<td rowspan='"+rs+"' >" + ((resp[19] == null) ? "" : resp[19]) + "</td>"  
								 + "<td rowspan='"+rs+"' >" + ((resp[17] == null) ? "" : resp[17]) + "</td>" 
								 + "<td rowspan='"+rs+"' >" + ((resp[22] == null) ? "" : resp[22]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[21] == null) ? "" : resp[21]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[24] == null) ? "" : resp[24]) + "</td>" 
								 + "<td rowspan='"+rs+"' >" + ((resp[21] == null) ? "" : resp[21]) + "</td>"
								 + "<td rowspan='"+rs+"' >" + ((resp[23] == null) ? "" : resp[23]) + "</td>"
	 							 }
 							 arr.add(resp[0]);

 							
 								
 	 							 
 							// var l = ((resp[22] == null) ? "" : resp[22]);
 							var j = i + 1;
 							 html1 += "<tr id='idRow' >"
 							 + "<td>"+ j + "</td>"
 							 + html2
 							 + "<td>" + ((resp[8] == null) ? "" : resp[8]) + "</td>" 
 							 + "<td>" + ((resp[7] == null) ? "" : resp[7]) + "</td>" 
 							 + "<td>" + ((resp[9] == null) ? "" : resp[9]) + "</td>" 
 							 + "<td>" + ((resp[10] == null) ? "" : resp[10]) + "</td>" 
 							 + "<td>" + ((resp[11] == null) ? "" : resp[11]) + "</td>" 
 							 + html3  + "</tr>"; 
 						}
 						$('#sample1').dataTable().fnClearTable();
 							$("#getfdrlossesbody").html(html1);
 							loadSearchAndFilter('sample1');
 					 
 			            
 		            }
 	});
 		
 	 }

  
  </script>

	<div class="page-content">
		<div class="portlet box blue " id="boxid">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-reorder"></i>Feeder Level Transmission Losses
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a> <a
						href="#portlet-config" data-toggle="modal" class="config"></a> <a
						href="javascript:;" class="reload"></a> <a href="javascript:;"
						class="remove"></a>
				</div>
			</div>
			<div class="portlet-body ">

				<jsp:include page="locationFilter.jsp" />

				<div class="row" style="margin-left: -1px;">

					<div class="col-md-3
					">
						<strong>Feeder:</strong>
						<div id="feederDivId" class="form-group">
							<select class="form-control select2me input-medium"
								id="feederTpId" name="feederTpId">
								<option value="">Select Feeder</option>
								<option value="%">All</option>
							</select>
						</div>
					</div>
					
					<div class="col-md-3">
			<div class="input-group ">
						<strong>Month Year :</strong><font color="red">*</font><input
							type="text" class="form-control from"  id="month"
							name="month" required="required" placeholder="Select MonthYear">  <span
							class="input-group-btn">
							<button class="btn default" type="button"
								style="margin-bottom: -17px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div></div>
					
					<div class="col-md-2">
						<strong>Select Period :</strong>
						<div id="fromDateMonthly" class="form-group">
							<select class="form-control select2me" id="periodMonth"
								name="periodMonth">
								<option value="12">12</option>
								<option value="10">10</option>
								<option value="08">8</option>
								<option value="06">6</option>
								<option value="04">4</option>
								<option value="02">2</option>
								<option value="00">0</option>
							</select>
						</div>
					</div>

					

					<div class="col-md-2">
						<button type="submit" onclick="view()" style="margin-top: 15px;"
							class="btn green">View</button>
					</div>
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
								<a href="#" id="excelExport" onclick="tableToExcel('sample1', 'Feeder Losses')">Export to Excel</a>
								</li> 
							</ul>
						</div>

						<table class="table table-striped table-hover table-bordered"
							id="sample1">

							<thead>
								<tr>
									<th rowspan="3"  style="text-align: center;" >SL<br> NO</th>
									<th colspan="5" style="text-align: center;" >Feeder</th>
									<th colspan="5" style="text-align: center;" >Boundary</th>
									<th rowspan="2">Total Available<br> Input Energy</th>
									<th colspan="3" style="text-align: center;" >Energy Consumed at HT Consumers</th>
									<th  rowspan="3"  style="text-align: center;" >No Of DT Consumers</th>
									<th colspan="4"  style="text-align: center;" >Aggregated Energy at DT Level</th>
									<th rowspan="2"  style="text-align: center;" >Loss During Transmission at Feeder Level</th>
									<th rowspan="2"  style="text-align: center;" >HT Loss (%)</th>
								</tr>
								
								<tr>
								<th rowspan="2">Feeder Name</th>
								<th rowspan="2">Feeder Code</th>
								<th>Import</th>
								<th>Export</th>
								<th>Net Energy</th>
								<th rowspan="2">Boundary Name</th>
								<th rowspan="2">Boundary Code</th>
								<th>Import</th>
								<th>Export</th>
								<th>Net Energy</th>
								<th>HT Commercial</th>
								<th>HT Industrial</th>
								<th>Total Energy at HT Consumer</th>
								<th>Unit Supplied</th>
								<th>Unit Billed</th>
								<th>Loss</th>
								<th>DT Loss(%)</th>
								
								</tr>
								
							<tr>
							<th style="text-align: center;">A</th>
							<th style="text-align: center;">B</th>
							<th style="text-align: center;">C = (A-B)</th>
							<th style="text-align: center;">D</th>
							<th style="text-align: center;">E</th>
							<th style="text-align: center;">F = (D-E)</th>
							<th style="text-align: center;">G = (C-F)</th>
							<th style="text-align: center;">I</th>
							<th style="text-align: center;">J</th>
							<th style="text-align: center;">K = (I+J)</th>
							<th style="text-align: center;">L</th>
							<th style="text-align: center;">O</th>
							<th style="text-align: center;">P = L-O</th>
							<th style="text-align: center;">Q= P/L</th>
							<th style="text-align: center;">M= G -(K+L)</th>
							<th style="text-align: center;">N= M/G</th>
							
							
							
							</tr>
							</thead>
							<tbody id="getfdrlossesbody">
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
	    startDate :new Date(new Date().getFullYear(),'-55'),
	    endDate: new Date(year, month-1, '31')
	});
	</script>


	
</body>

</html>

