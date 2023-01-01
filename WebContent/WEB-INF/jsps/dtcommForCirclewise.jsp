<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<script  type="text/javascript">

	$(".page-content").ready(function(){  
		
		App.init();
		TableManaged.init();
		FormComponents.init();
		 $('#dtWiseReport,#dtcommForCirclewise').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#toid").show();	
	
	});
	


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
               // html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
                    html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
    // var zone = $('#zone').val();
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
                     html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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

function showTownBySubdiv(subdiv) {
     // var zone = $('#zone').val();
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
             subdivision :subdiv
          },
                  success : function(response1) {
                      var html = '';
                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
                      for (var i = 0; i < response1.length; i++) {
                          //var response=response1[i];
                          html += "<option value='"+response1[i][0]+"'>"
                                  + response1[i][0]+"-"+response1[i][1] + "</option>";
                      }
                      html += "</select><span></span>";
                      $("#townTd").html(html);
                      $('#town').select2();
                  }
              });
  }


function showResultsbasedOntownCode (){
		
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
	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium' onchange='showResultsbasedOntownCode(this.value)'  type='text'><option value=''></option><option value='%'>ALL</option>";
	               
	                for (var i = 0; i < response1.length; i++) {
	                    var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                               }
	                html += "</select><span></span>";
	                $("#LFtownTd").html(html);
	                $('#LFtown').select2();
	                
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
	                html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select</option>";
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

 

</script>



<script>

		/*  function getdtcommReport(){

			var zone=$('#LFzone').val();
			var circle=$('#LFcircle').val();
			var town=$("#LFtown").val();

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
		 	$("#sample_2").show();
			 $('#imageee').show();


			 $.ajax({

				 url : './getdtcommReport',
					type : 'GET',
					dataType : 'json',
					 data:{
						   zone:zone,
						   circle:circle,
						  	town:town
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
										+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
										+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
										+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
										+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
										+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
										+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
										+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
										+"</tr>";		
						 }
						 $('#sample_2').dataTable().fnClearTable();
			            	$("#getdtcommReport").html(html);
			            	loadSearchAndFilter('sample_2');	 
					 },
					 
					    complete: function()
			      		{  
			      			loadSearchAndFilter('sample_2');
			      			$('#imageee').hide();
			      		} 


				 });		
			} 
			
		 */


			function  getdtcommReport(){

				 var reportType = $('#typelist').val();
			
			

				 if(reportType == ''){
					 bootbox.alert('Please select report type');
					 return false;
				 }


				 $("#imageee").show();
				 if(reportType == 'Dtday'){


						 var zone=$('#LFzone').val();
						var circle=$('#LFcircle').val();
						var town=$("#LFtown").val();

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
					 	$("#sample_2").show();
						 $('#imageee').show();


						 $.ajax({

							 url : './getdtcommReport',
								type : 'GET',
								dataType : 'json',
								 data:{
									   zone:zone,
									   circle:circle,
									  	town:town
									
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
													+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
													+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
													+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
													+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
													+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
													+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
													+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
													+"</tr>";		
									 }
									 $('#sample_2').dataTable().fnClearTable();
						            	$("#getdtcommReport").html(html);
						            	loadSearchAndFilter('sample_2');	 
								 },
								 
								    complete: function()
						      		{  
						      			loadSearchAndFilter('sample_2');
						      			$('#imageee').hide();
						      		} 


							 });		

					 }

				 else if(reportType=="DTmonth"){


					 var zone=$('#LFzone').val();
						var circle=$('#LFcircle').val();
						var town=$("#LFtown").val();

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
					 	$("#sample_2").show();
						 $('#imageee').show();


						 $.ajax({

							 url : './getdtcommReportmonth',
								type : 'GET',
								dataType : 'json',
								 data:{
									   zone:zone,
									   circle:circle,
									  	town:town,
									  	month:month
									
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
													+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
													+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
													+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
													+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
													+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
													+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
													+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
													+"</tr>";		
									 }
									 $('#sample_2').dataTable().fnClearTable();
						            	$("#getdtcommReport").html(html);
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
	
</script>




<div class="page-content">

	

	<c:if test="${not empty msg}">
		<div class="alert alert-success display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${msg}</span>
		</div>
	</c:if>
	
	<div class="row">
		<div class="col-md-12">
		
		<div class="portlet box blue">
			
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>DT-Communication Report
					</div>
				</div>
		
			
				<div class="portlet-body">
				
					 <jsp:include page="locationFilter.jsp"/> 
					 
				 
				 	<div class="col-md-3">
						<strong>Feeder:</strong>
								<div id="feederDivId" class="form-group">
												<select	class="form-control select2me input-medium" id="feederTpId" name="feederTpId">
													
													<option value="">Select Feeder</option>	</select>
												
								</div>
						</div> 	
					
					<div class="col-md-3">
							<th>Report Type&nbsp;:</th>
							
								<th id="typelist">
								
									<select	class="form-control select2me input-medium" id="typelist" name="reports" >
										<option value="">Select</option>
										<option value="Dtday">DT Communication for last day</option>
										<option value="DTmonth">DT Communication for last Month</option>
									
									</select>
								
								</th>
							
					</div>		
					

							
							
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

				
					
					 
			  <div class="row" style="margin-left: -1px;">
					 	
					 	<div class="col-md-3" style="margin-top:15px;">
							<button type="button" id="showFeederData"  style="margin-top: 13px;"
											onclick="return getdtcommReport()" name="showFeederData"
											class="btn yellow">
											<b>View</b>
							</button>
						</div>
			 </div>
				
			
				<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
				</div>
					  
				<div class="tabbable tabbable-custom">
					 
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_2', 'DT-Communication Report');">Export
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
											<th>Town Code</th>
											<th>Town</th>
											<th>Section Code</th>
											<th>Scetion</th>
											<th>Total DT</th>
											<th>Communicating DT</th>
											<th>Non-Communicating DT</th>
											
									
										</tr>
									
									</thead>
									
									
									<tbody id="getdtcommReport">
									</tbody>
									
									
								</table>
								
					 </div>
					 </div>
					
				</div>
					
			</div>
		
		</div>
		
	</div>

