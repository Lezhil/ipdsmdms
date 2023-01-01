<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<style>
.table-toolbar {
	margin-bottom: 4px
}

.tabbable-custom.tabbable-full-width .nav-tabs>li>a {
	color: #424242;
	font-size: 14px !important;
	padding: 9px 6px;
}
</style>
<script  type="text/javascript">
	$(".page-content").ready(function(){  



	
		$('#fromDate').val('${fromDate}');
		$('#toDate').val('${toDate}');
	
		frmDate = '${fromDate}';
		tDate = '${toDate}';
		
		
		
		$('.datepicker').datepicker({
			format : 'yyyy-mm-dd',
			autoclose : true,
			endDate : "today",

		}).on('changeDate', function(ev) {
			$(this).datepicker('hide');
		});

		$('.datepicker').keyup(
				function() {
					if (this.value.match(/[^0-9]/g)) {
						this.value = this.value.replace(
								/[^0-9^-]/g, '');
					}
				});

		 
		
		TableManaged.init();
		FormComponents.init();
		 $('#dataAvailReport,#dataAvailability').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#toid").hide();	
		 App.init();
		// $("#action").hide();
	});
	
	
	</script>
	
	
	<script  type="text/javascript">

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
	


	 function getReport() {
			//alert(towncode);
			//var subdiv = $('#sdoCode').val();
			//var rdngmnth=$('#reportFromDate').val();
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdiv = $('#sdoCode').val();
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var town = $('#LFtown').val();
		    //alert(town);
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
		
			
			//$('#updateMaster').empty();
			$('#sample_3').dataTable().fnClearTable();
			$('#imageee').show();
			$.ajax({
				url : './getDTDataAvailability',
				type : 'GET',
				data : {
					subdiv : subdiv,
					fromDate :fromDate,
					toDate:toDate,
					circle : circle,
					division : division,
					town : town,
					zone : zone
					//towncode:towncode
				},
				dataType : 'JSON',
				asynch : false,
				cache : false,
				success : function(response) {
					$('#imageee').hide();
					 if (response.length != 0) {
						//$('#sample1').dataTable().fnClearTable();
						$("#updateMaster").html('');
						var html1 = '';
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							//var maxKvar=
						   //var availability=(element[26]/element[22])*100;
							html1 += "<tr>" 
								+"<td>"+(i+1)+"</td>"
						
								+ "<td>"+ (element[0]==null?"":(element[0]))+ "</td>"
								+ "<td>"+ (element[1]==null?"":(element[1]))+ "</td>"
								+ "<td>"+ (element[2]==null?"":(element[2]))+ "</td>"
								+ "<td>"+ (element[3]==null?"":(element[3]))+ "</td>"
								+ "<td>"+ (element[4]==null?"":(element[4]))+ "</td>"
								+ "<td>"+ (element[5]==null?"":(element[5]))+ "</td>"
						html1 +="</tr>";
								
						}
						$('#sample_3').dataTable().fnClearTable();
						$('#updateMaster').html(html1);
						loadSearchAndFilter('sample_3');
						
					} else {
					/* 	$('#sample1').dataTable().fnClearTable();
						$('#updateMaster').html(html1); */
						bootbox.alert("No Data Available");
					}

				},
				complete: function()
				{  
					loadSearchAndFilter('sample_3'); 
				} 
			});


			 getReportbill();
		}

	 function  getReportbill() {
			//alert(towncode);
			//var subdiv = $('#sdoCode').val();
			//var rdngmnth=$('#reportFromDate').val();
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdiv = $('#sdoCode').val();
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var town = $('#LFtown').val();
		    //alert(town);
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
		
			
			//$('#updateMaster').empty();
			//$('#sample2').dataTable().fnClearTable();
			//$('#imageee').show();
			$.ajax({
				url : './getDTDataAvailabilitybill',
				type : 'GET',
				data : {
					subdiv : subdiv,
					fromDate :fromDate,
					toDate:toDate,
					circle : circle,
					division : division,
					town : town,
					zone : zone
					//towncode:towncode
				},
				dataType : 'JSON',
				asynch : false,
				cache : false,
				success : function(response) {
					$('#imageee').hide();
					if (response != null && response.length > 0) {
						//$('#sample1').dataTable().fnClearTable();
						//$("#updateMasterload").html('');
						var html1 = '';
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							//var maxKvar=
						   //var availability=(element[26]/element[22])*100;
							html1 += "<tr>" 
								+"<td>"+(i+1)+"</td>"
						
								+ "<td>"+ (element[0]==null?"":(element[0]))+ "</td>"
								+ "<td>"+ (element[1]==null?"":(element[1]))+ "</td>"
								+ "<td>"+ (element[2]==null?"":(element[2]))+ "</td>"
								+ "<td>"+ (element[3]==null?"":(element[3]))+ "</td>"
								+ "<td>"+ (element[4]==null?"":(element[4]))+ "</td>"
								+ "<td>"+ (element[5]==null?"":(element[5]))+ "</td>"
						html1 +="</tr>";
								
						}
						$('#sample_2').dataTable().fnClearTable();
						$('#updateMasterbill').html(html1);
						loadSearchAndFilter('sample_2');
						
					} else {
						/* $('#sample2').dataTable().fnClearTable();
						$('#updateMasterbill').html(html1); */
						bootbox.alert("No Data Available");
					}

				},
				complete: function()
				{  
					$("#imageee").hide();
					loadSearchAndFilter('sample_2'); 
				} 
			});


			 getReportconsumption();
		}


	 function  getReportconsumption() {
			//alert(towncode);
			//var subdiv = $('#sdoCode').val();
			//var rdngmnth=$('#reportFromDate').val();
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdiv = $('#sdoCode').val();
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var town = $('#LFtown').val();
		    //alert(town);
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
		
			
			//$('#updateMaster').empty();
			//$('#sample_1').dataTable().fnClearTable();
			$('#imageee').show();
			$.ajax({
				url : './getDTDataAvailabilityconsumption',
				type : 'GET',
				data : {
					subdiv : subdiv,
					fromDate :fromDate,
					toDate:toDate,
					circle : circle,
					division : division,
					town : town,
					zone : zone
					//towncode:towncode
				},
				dataType : 'JSON',
				asynch : false,
				cache : false,
				success : function(response) {
					$('#imageee').hide();
					if (response != null && response.length > 0) {
						//$('#sample1').dataTable().fnClearTable();
						//$("#updateMasterload").html('');
						var html1 = '';
						for (var i = 0; i < response.length; i++) {
							var element = response[i];
							//var maxKvar=
						   //var availability=(element[26]/element[22])*100;
							html1 += "<tr>" 
								+"<td>"+(i+1)+"</td>"
						
								+ "<td>"+ (element[0]==null?"":(element[0]))+ "</td>"
								+ "<td>"+ (element[1]==null?"":(element[1]))+ "</td>"
								+ "<td>"+ (element[2]==null?"":(element[2]))+ "</td>"
								+ "<td>"+ (element[3]==null?"":(element[3]))+ "</td>"
								+ "<td>"+ (element[4]==null?"":(element[4]))+ "</td>"
								+ "<td>"+ (element[5]==null?"":(element[5]))+ "</td>";
						html1 +="</tr>";
								
						}
						$('#sample1').dataTable().fnClearTable();
						$('#DTDataAvailabilityconsumption').html(html1);
						loadSearchAndFilter('sample1');
						
					} else {
					/* 	$('#sample3').dataTable().fnClearTable();
						$('#DTDataAvailabilityconsumption').html(html1); */
						bootbox.alert("No Data Available");
					}

				},
				complete: function()
				{  
					loadSearchAndFilter('sample1'); 
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
						<i class="fa fa-edit"></i>DT DataAvailability Report
					</div>
					
					
				</div>
				
				<div class="portlet-body">
				
					<jsp:include page="locationFilter.jsp"/>
					
					 <div class="row" style="margin-left: -1px;"> 
					 
					 		
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
								<input type="text" autocomplete="off" class="form-control"
									placeholder="Select Date"  name="toDate" id="toDate"> <span
									class="input-group-btn">
									<button class="btn default" type="button" id="">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					
						<div class="col-md-2">

										<button type="button" id="viewFeeder" style="margin-top: 19px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> 
							</div>
					</div>
					
					
							<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div> 
							
				
						
					<!-- <div class="tabbable tabbable-custom"> -->
					<div class="table-responsive">
						 <div class="col-md-12">
						 
						 	<div class="table-toolbar">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<!-- <li id="print"><a href="#">Print</a></li> -->	
										<!-- <li><a href="#" id=""onclick="exportPDF('sample11','SubStationData')">Export to PDF</a></li>	 -->
								 		<li>
											<a href="#" id="excelExport" onclick="tableToXlxs('sample_3', 'Data Availability Report')">Export to Excel</a>
										</li> 								
									<!-- <li><a href="#" id="excelExport" onclick="exportToExcelSubMethod('sample1', 'DT Health Report')">Export to Excel</a></li> -->
									</ul>
								</div> 
								
							</div>
							
							<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div>
									
							<table class="table table-striped table-bordered table-hover"
									id="sample_3">
									<thead>
									
											<tr>

												<th colspan='7' style="text-align:center;">Data Availability Report</th>
											</tr>
										<tr>
											 <th> Sl No</th>
											 <th>Region</th>
											 <th>Circle</th>
									        <th>Meter Make</th>
									        <th>Total Mapped Meters</th>
											<th>Constantly Communicated Meters</th>
											<th>Communication Percentage</th>
											
										</tr>
									</thead>
									<tbody id="updateMaster">
									</tbody>
									
								</table>
									 
						 </div>	
					<!-- </div> -->
					</div>
					
					
					
					
					
				<div class="table-responsive">
					
					
								
						<div class="tabbable tabbable-custom">
					
								 <div class="col-md-12">
								 
								 	<div class="table-toolbar">
										<div class="btn-group pull-right">
											<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">
												<!-- <li id="print"><a href="#">Print</a></li> -->	
												<!-- <li><a href="#" id=""onclick="exportPDF('sample11','SubStationData')">Export to PDF</a></li>	 -->
										 												
												<li><a href="#" id="excelExport" onclick="tableToXlxs('sample_2', 'Bill Data Availability Report');">Export to Excel</a>
											
												</li>
											</ul>
										</div>
										
									</div>
							
							<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div>
									
							<table class="table table-striped table-bordered table-hover"
									id="sample_2">
									<thead>
									
											<tr>

												<th colspan='7' style="text-align:center;">Bill Data Availability Report</th>
											</tr>
										<tr>
											 <th> Sl No</th>
											  <th>Region</th>
											 <th>Circle</th>
									        <th>Meter Make</th>
									        <th>Total Mapped Meters</th>
											<th>Constantly Communicated Meters</th>
											<th>Communication Percentage</th>
											
										</tr>
									</thead>
									<tbody id="updateMasterbill">
									</tbody>
									
								</table>
									 
						 </div>	
					</div>
				</div>	
					
						
				<div class="table-responsive">
					
					
								
						<div class="tabbable tabbable-custom">
					
								 <div class="col-md-12">
								 
								 	<div class="table-toolbar">
										<div class="btn-group pull-right">
											<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
											</button>
											<ul class="dropdown-menu pull-right">
													<li>
													<a href="#" id="" onclick="tableToXlxs('sample1', 'DailyConsumption')">Export to Excel</a></li> 
								
											</ul>
										</div>
										
									</div>
							
							<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div>
									
							<table class="table table-striped table-bordered table-hover"
									id="sample1">
									<thead>
									
											<tr>

												<th colspan='7' style="text-align:center;">DailyConsumption Availability Report</th>
											</tr>
										<tr>
											 <th> Sl No</th>
											 <th>Region</th>
											 <th>Circle</th>
									        <th>Meter Make</th>
									        <th>Total Mapped Meters</th>
											<th>Constantly Communicated Meters</th>
											<th>Communication Percentage</th>
											
										</tr>
									</thead>
									<tbody id="DTDataAvailabilityconsumption">
									</tbody>
									
								</table>
									 
						 </div>	
					</div>
				</div>	
					
		
						
						</div>
					</div>
			
			
			</div>
	
	
	</div>
	</div>
	
	
	