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
		 $('#dtWiseReport,#dtpowerfailureForCirclewise').addClass('start active ,selected');
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
								<i class="fa fa-edit"></i>DT-PowerONOFF Report
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a> <a
									href="javascript:;" class="remove"></a>
							</div>
					</div>
				
					<div class="portlet-body">
					
						<jsp:include page="locationFilter.jsp"/> 
						
							<div class="row" style="margin-left: -1px;">
							
							<div class="col-md-3">
							
									<div id="circleTd" class="form-group">
									
										<select class="form-control select2me" onchange='showTable(this.value)' id="reportId" name="reportId">
										
											<option value="">Select Report</option>
											<option value="Total powerfailure Dt"> Power_ONOFF DT</option>
									
										</select>		
									</div>
							</div>
							
							<div class="col-md-3">
								<div id="circleTd" class="form-group">
								
									<select class="form-control select2me" onchange='showTable(this.value)' id="reportIdPeriod" name="reportIdPeriod">
									
										<option value="">Select Period</option>
										<option value="Current Day"><h2>Current Day</h2></option>
									
									
									</select>
								
								</div>
							</div>
							
								<div class="col-md-2" >

										<button type="button" id="viewFeeder" style="margin-bottom: -100px; margin-left: -400px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> 
									</div>
									
							</div>
							
								<p>&nbsp;</p>
								
								
							<div id="imageee" hidden="true" style="text-align: center;">
								<h3 id="loadingText">Loading..... Please wait.</h3>
								<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div>
							
					<div class="tabbable tabbable-custom" id="showDTDetailsWithInstances" hidden="true">
							<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_2', 'Dt-PowerfailureReport');">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
							<th>Sl No</th>	
							
								<th>Region</th>
								<th>Circle</th>
								<th>Town Code</th>
								<th>Town Name</th>
								<th>Section_code</th>
								<th>Section</th>
								<th>Dttpid</th>
								<th>Dtname</th>
								<th>Meter Number</th>
								<th>Dtcapacity_AMPS</th>
								<th>Dt_capacity_KVA</th>
								<th>Event Occurance Time</th>
								<th>Event Restoration Date</th>
								<th>Event Duration</th>
							</tr>
						</thead>
						<tbody id="updateMaster2">

						</tbody>
					</table>
				</div>
					
				</div>
				</div>
			
			
			</div>
		
		
		</div>
	
	</div>
	
	
	<script>

		function getReport(){

			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var report =$("#reportId").val();
			var reportIdPeriod =$("#reportIdPeriod").val();

			if(zone=="")
			{
			bootbox.alert("Please Select Region");
			return false;
			}
			if(circle=="" || circle== null)
			{
			bootbox.alert("Please Select circle");
			return false;
			}
			if(report=="")
			{
			bootbox.alert("Please Select Report");
			return false;
			}
			if(reportIdPeriod=="")
			{
			bootbox.alert("Please Select Report Period");
			return false;
			}
			
			$("#imageee").show();

			 $.ajax({
		    	 url:"./getdtpowerfailureReport",
		           type:'GET',
		           data:{
		        	   zone:zone,
		        	   circle:circle,
		        	   report:report,
		        	   reportIdPeriod:reportIdPeriod
		               },
		           success:function(response)
		           {
		        	   if (response != null && response.length > 0) {
		        	   var html="";
		        	 //  alert(response);
		        	   for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
								+ "<td>"+ j + "</td>"
								+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
								+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
								+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"	
								+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
								+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
								+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
								+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
								+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"	
								+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
								+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
								+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"	
								+ "<td>"+ ((resp[12] == null) ? "": moment(resp[12]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp[13] == null) ? "": moment(resp[13]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
								+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>";
							
								
								+"</tr>";
						
				
							
						}
		        	   
		                $('#sample_2').dataTable().fnClearTable();
		            	$("#updateMaster2").html(html);
		            	loadSearchAndFilter('sample_2');
		        	   }else {
							bootbox.alert("No Relative Data Found.");
						}
		        	   
		           },
		           complete: function()
		      		{  
		      			loadSearchAndFilter('sample_2');
		      			$('#imageee').hide();
		      		} 
		    });			
		}



	</script>
	
	<script>

		function showTable(value){

				if(value=="Total powerfailure Dt"){

					$('#sample_2').dataTable().fnClearTable();
					loadSearchAndFilter('sample_2');
					$('#showDTDetailsWithInstances').show();

					}

			}
		
	</script>