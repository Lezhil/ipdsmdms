<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script  type="text/javascript">
	$(".page-content").ready(function(){  
		 
		
		TableManaged.init();
		FormComponents.init();
		 $('#dtWiseReport,#showloadphaseID').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#toid").hide();	
		 App.init();
		// $("#action").hide();
	});
	
	
//	var towncode="";
	</script>
	
	
	
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
                    //html+="<select id='circle' name='circle' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                  html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''></option><option value='%'>ALL</option>";
                    
                    for( var i=0;i<response.length;i++)
                    {
                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                    }
					html+="</select><span></span>";
                    $("#LFcircleTd").html(html);
                    $('#LFcircle').select2();
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
                                html += "<option value='"+response[i]+"'>"
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
         //alert("zone---"+zone);
         //alert("circle---"+circle);
         //alert("div---"+division);
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
                         html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
                         for (var i = 0; i < response1.length; i++) {
                             //var response=response1[i];
                             html += "<option value='"+response1[i]+"'>"
                                     + response1[i] + "</option>";
                         }
                         html += "</select><span></span>";
                         $("#subdivTd").html(html);
                         $('#sdoCode').select2();
                     }
                 });
     }

	 function showResultsbasedOntownCode (){
 		
     }

	 function showTownNameandCode(subdiv) {
	      var zone = $('#LFzone').val()
	      var circle = $('#LFcircle').val();
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
	              division : '%',
	              subdivision :'%'
	          },
	                  success : function(response1) {
   	                //  alert(response1);
	                      var html = '';
	                     // html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                   html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''></option><option value='%'>ALL</option>";
	                     
	                      for (var i = 0; i < response1.length; i++) {
   	                  //    towncode=response1[i][0];
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#LFtownTd").html(html);
	                      $('#LFtown').select2();
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
						<i class="fa fa-edit"></i>Voltage Phase Missing
					</div>
				</div>
		
			 
			  
			  
				<div class="portlet-body">
				
				 <jsp:include page="locationFilter.jsp"/>  
					 
				 
				 	<!-- <div class="col-md-3">
						<strong>Feeder:</strong>
								<div id="feederDivId" class="form-group">
												<select	class="form-control select2me input-medium" id="feederTpId" name="feederTpId">
													
													<option value="">Select Feeder</option>	</select>
												
								</div>
						</div>  -->	
					
					<div class="col-md-3">
							<th>Report Type&nbsp;:</th>
							
								<th id="typelist">
								
								<select	class="form-control select2me input-medium"  id="typelist" name="typelist" >
										<option value="">Select</option>
										<option value="vr">R-Phase Voltage Missing</option>
										<option value="vy">Y-Phase Voltage Missing</option>
										<option value="vb">B-Phase Voltage Missing</option>
										
									
									</select>
								
								</th>
								
								
							
					</div>		
					

							
				<!-- <!-- 			
				 <div class="col-md-3">
						<div class="form-group">
							 <strong>From Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control"
									name="fromDate" id="fromDate"
									placeholder="Select Date"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					
					
					
				<div class="col-md-3">
						<div class="form-group">
							 <strong>To Date:</strong>
							<div class="input-group input-medium date date-picker"
								data-date-end-date="-1d" data-date-format="yyyy-mm-dd"
								data-date-viewmode="years">
								<input type="text" autocomplete="off" class="form-control" placeholder="Select Date"  name="toDate" id="toDate"> 
									<span class="input-group-btn">
											<button class="btn default" type="button" id="">
												<i class="fa fa-calendar"></i>
											</button>
									</span>
							</div>
						</div>
					</div>
 --> 
 			
 				<div class="col-md-3">
					<div class="input-group ">
					
						<strong>Report Month:</strong>
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"><span
											class="input-group-btn">
										<button class="btn default" type="button" style="margin-bottom: -17px;">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div>
							
							
				</div>
				
					
					 
			  	<div class="row" style="margin-left: -1px;">
					 	
					 	<div class="col-md-3" style="margin-top:15px;">
							<button type="button" id="showFeederData"  style="margin-top: 13px;"
											onclick="return getloadphaseReport()" name="showFeederData"
											class="btn yellow">
											<b>View</b>
							</button>
						</div> 
			 		</div>
				
			
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
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_2', 'phaseload Report');">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
						
					 		<table class="table table-striped table-hover table-bordered"
									id="sample_2">
									<thead>
										<tr>
											<th>SI.NO</th>
											<th>Region</th>
											<th>Circle</th>
											<th>Town</th>
											<th>Section</th>
											<th>FeeerName</th>	
											<th>Dtname</th>	
											<th>Meter Number</th>					
										</tr>
									
									</thead>
									
									
									<tbody id="getloadphaseReport">
									</tbody>
									
									
								</table>
								
					
					 </div>
					
				</div>
					
			</div>
		
		</div>
		
	</div>

<script>



		function  getloadphaseReport(){

			
			 	var zone = $("#LFzone").val();
				var circle = $("#LFcircle").val();
				
				 var reportType = $('#typelist').val();
				 var rdngmnth=$('#month').val();


			 if(reportType == ''){
				 bootbox.alert('Please select report type');
				 return false;
			 }

			 $("#imageee").show();

			 if(reportType == 'vr') {

				 
			
					/* alert(region);
					alert(circle); */
					if (zone == "") {
						bootbox.alert("Please Select Region");
						return false;
					}

					if (circle == "") {
						bootbox.alert("Please Select circle");
						return false;
					}
					 

					 	$("#sample_2").show();
						 $('#imageee').show();

						 $.ajax({

							 url : './getdtloadphasevr',
								type : 'GET',
								dataType : 'json',
								 data:{
									 
									  	circle:circle,
										rdngmnth : rdngmnth
									
								 },

								 success : function(response) {

									 $('#imageee').hide();
											var html = "";
											for (var i = 0; i < response.length; i++) {

												   var resp=response[i];
												var j = i + 1;
												html += "<tr>" 
													 + "<td>"+ j + "</td>" 
												
													+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
													+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
													+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
													+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
													+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"	
													+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
													+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
													+"</tr>";		
									 }
									 $('#sample_2').dataTable().fnClearTable();
						            	$("#getloadphaseReport").html(html);
						            	loadSearchAndFilter('sample_2');	 
								 },
								 
								    complete: function()
						      		{  
						      			loadSearchAndFilter('sample_2');
						      			$('#imageee').hide();
						      		} 
							 
						 });
				 
			 }

			 if(reportType == 'vy'){


				 
					
					/* alert(region);
					alert(circle); */
					if (zone == "") {
						bootbox.alert("Please Select Region");
						return false;
					}

					if (circle == "") {
						bootbox.alert("Please Select circle");
						return false;
					}
					 

					 	$("#sample_2").show();
						 $('#imageee').show();

						 $.ajax({

							 url : './getdtloadphasevy',
								type : 'GET',
								dataType : 'json',
								 data:{
									 
									  	circle:circle,
										rdngmnth : rdngmnth
									
								 },

								 success : function(response) {

									 $('#imageee').hide();
											var html = "";
											for (var i = 0; i < response.length; i++) {

												   var resp=response[i];
												var j = i + 1;
												html += "<tr>" 
													+ "<td>"+ j + "</td>"
												
													+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
													+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
													+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
													+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
													+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"	
													+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"	
													+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
													+"</tr>";		
									 }
									 $('#sample_2').dataTable().fnClearTable();
						            	$("#getloadphaseReport").html(html);
						            	loadSearchAndFilter('sample_2');	 
								 },
								 
								    complete: function()
						      		{  
						      			loadSearchAndFilter('sample_2');
						      			$('#imageee').hide();
						      		} 
							 
						 });




				 }

			 if(reportType == 'vb'){


				 
					
					/* alert(region);
					alert(circle); */
					if (zone == "") {
						bootbox.alert("Please Select Region");
						return false;
					}

					if (circle == "") {
						bootbox.alert("Please Select circle");
						return false;
					}
					 

					 	$("#sample_2").show();
						 $('#imageee').show();

						 $.ajax({

							 url : './getdtloadphasevb',
								type : 'GET',
								dataType : 'json',
								 data:{
									 
									  	circle:circle,
									  	
										rdngmnth : rdngmnth
									
								 },

								 success : function(response) {

									 $('#imageee').hide();
											var html = "";
											for (var i = 0; i < response.length; i++) {

												   var resp=response[i];
												var j = i + 1;
												html += "<tr>" 
													+ "<td>"+ j + "</td>"
													
													+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
													+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
													+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
													+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
													+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"	
													+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"	
													+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
													+"</tr>";		
									 }
									 $('#sample_2').dataTable().fnClearTable();
						            	$("#getloadphaseReport").html(html);
						            	loadSearchAndFilter('sample_2');	 
								 },
								 
								    complete: function()
						      		{  
						      			loadSearchAndFilter('sample_2');
						      			$('#imageee').hide();
						      		} 
							 
						 });




				 }
			 
			}



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
	
	