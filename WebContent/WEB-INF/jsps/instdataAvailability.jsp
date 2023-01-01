<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>



<script  type="text/javascript">
	$(".page-content").ready(function(){  


		TableManaged.init();
		FormComponents.init();
		loadSearchAndFilter('sample1');
		 App.init();
		  $('#dataAvailReport,#loadataAvailability').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
 	
	
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

</script>
	
 <script>

 
 function getReport() {
	
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
	
	
		$('#imageee').show();
		$.ajax({
			url : './getDTDataAvailabilityinst',
			type : 'GET',
			data : {
				subdiv : subdiv,
				fromDate :fromDate,
				toDate:toDate,
				 zone:zone,
	        	 circle:circle,
	        	 town : town
				
			},
			dataType : 'JSON',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();			

					if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								
								html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
									+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"		
									+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"		
									+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"						
							html +="</tr>";
							}
							$('#sample1').dataTable().fnClearTable();
							$("#updateMasterinst").html(html);
							loadSearchAndFilter('sample1');
						
				}  else {
					bootbox.alert("No Relative Data Found.");
				}
	

			},
			complete: function()
			{  

				 $("#imageee").hide();
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
						<i class="fa fa-edit"></i>DT-Instantaneous DataAvailability Report
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

					
					
				<!-- 	<div class="tabbable tabbable-custom"> -->
					
						 	
					<div class="table-toolbar">
						
							
							
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								
									<!-- <li><a href="#" id="" onclick="exportPDF('sample1','ToBe Approve DTMeter List')">Export to PDF</a></li>	       --> 
									<li><a href="#" id="excelExport" onclick="tableToXlxs('sample1', 'Instantaneous Data Availability Report');">Export to Excel</a></li>
							
								</ul>
							</div>
							
							
						</div>	
						 	
						 	
						 	<div id="imageee" hidden="true" style="text-align: center;">
										<h3 id="loadingText">Loading..... Please wait.</h3>
										<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
							</div>
										
								 	<table class="table table-striped table-bordered table-hover" id="sample1">
											
											<thead>
											
													<tr>
													
														<th colspan='7' style="text-align:center;">Instantaneous Data Availability Report</th>
													
													</tr>
												<tr>
													<th> Sl No</th>
													<th>Region</th>
													<th>Circle</th>
													<th>Town</th>
											        <th>Meter Make</th>
											        <th>Total Mapped Meters</th>
													<th>Constantly Communicated Meters</th>
													<th>load Percentage</th>
												</tr>
											</thead>
											<tbody id="updateMasterinst">
											
											</tbody>
											
									</table>
						 
						 
						 		
							<!-- </div> -->
						</div>			
					</div>
				</div>
			
			
			</div>
	
	
	</div>
	