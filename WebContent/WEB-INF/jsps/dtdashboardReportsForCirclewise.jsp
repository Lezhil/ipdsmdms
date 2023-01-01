<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		 
		
		TableManaged.init();
		FormComponents.init();
		App.init();
		 $('#dtWiseReport,#dtdashboardReportsForCirclewise').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#toid").hide();	
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
						<i class="fa fa-edit"></i>DT Dashboard Report
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
							<option value="Overload DT">Overload DT</option>
							<option value="Overload DT Instances">Overload DT Instances</option>
							<option value="Underload DT">Underload DT</option>
							<option value="Underload DT Instances">Underload DT Instances</option>			
							<option value="Unbalance DT">Unbalance DT</option>
							<option value="Unbalance DT Instances">Unbalance DT Instances</option>
							 <option value="PowerFailure DT">PowerFailure DT</option>
							 <option value="PowerFailure DT Instances">PowerFailure DT Instances</option>	
							<option value="Good Power Factor">Good Power Factor</option>
							<option value="Nominal Power Factor">Nominal Power Factor</option>
							<option value="Poor Power Factor">Poor Power Factor</option>
							<option value="Good Utilization Factor">Good Utilization Factor</option>
							<option value="Nominal Utilization Factor">Nominal Utilization Factor</option>
							<option value="Poor Utilization Factor">Poor Utilization Factor</option>
							<option value="Good Load Factor">Good Load Factor</option>
							<option value="Nominal Load Factor">Nominal Load Factor</option>
							<option value="Poor Load Factor">Poor Load Factor</option>
											
						</select>
					</div>
				</div>
				
				<div class="col-md-3">
 					<div id="circleTd" class="form-group">
						<select class="form-control select2me" onchange='showTable(this.value)' id="reportIdPeriod" name="reportIdPeriod">
							<option value="">Select Period</option>
							<option value="Last Day">Last Day</option>
							<option value="Last Week">Last Week</option>
							<option value="Current Month">Current Month</option>
							<option value="Previous month">Previous month</option>			
						</select>
					</div>
				</div>
				
				<div class="col-md-6" id="overload" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Overload</li>  
									  <li>If Max of (IR,IY,IB) is > 70% of max current rating of DT</li>
									  <li>Then the DT is overloaded in that particular IP..</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="underload" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Underload</li>  
									  <li>If Max of (IR,IY,IB) is < 20% of max current rating of DT.</li>
									  <li>Then the DT is underloaded in that particular IP..</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="unbalance" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Unbalance</li>  
									  <li>If (R Phase Unbalance - YPhase Unbalance)>50 or (Y Phase Unbalance - BPhase Unbalance)>50 or (B Phase Unbalance - Rphase  Unbalance)>50.</li>
									</ol> 
								 </div>
							   </div>
							
							<div class="col-md-6" id="powerfailure" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Power Failure</li>  
									  <li>Power failure analysis will be based on power failure even recorded in the report. Already required data is available in tamper/tamper summary reports.</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="goodpf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Good Power Factor</li>  
									  <li>Power Factor > 0.90 is known as Good power factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="nominalpf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Nominal Power Factor</li>  
									  <li> 0.90 > Power factor > 0.5 is known as Nominal power factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="poorpf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Poor Power Factor</li>  
									  <li>Power Factor < 0.50 is known as Poor power factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="gooduf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Good Utilization Factor</li>  
									  <li>Utilization Factor > 0.50 is known as Good Utilization factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="nominaluf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Nominal Utilization Factor</li>  
									  <li> 0.90 > Utilization factor > 0.5 is known as Nominal Utilization factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="pooruf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Poor Utilization Factor</li>  
									  <li>Utilization Factor < 0.50 is known as Poor Utilization factor</li>
									</ol> 
								 </div>
							   </div>
							   
							     <div class="col-md-6" id="goodlf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Good Load Factor</li>  
									  <li>Load Factor > 0.90 is known as Good Load factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="nominallf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Nominal Load Factor</li>  
									  <li> 0.90 > Load factor > 0.5 is known as Nominal Load factor</li>
									</ol> 
								 </div>
							   </div>
							   
							   <div class="col-md-6" id="poorlf" hidden="true">
								 <div  style="margin-top: -50px;">
									 <strong>Note:-</strong> 
									 <ol>
									  <li>Poor Load Factor</li>  
									  <li>Load Factor < 0.50 is known as Poor Load factor</li>
									</ol> 
								 </div>
							   </div>
							
							<div class="col-md-2" >

										<button type="button" id="viewFeeder" style="margin-bottom: -100px; margin-left: -400px; "
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
					
					<div class="tabbable tabbable-custom" id="showDTDetails" hidden="true">
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_1', 'DTDashboard Reports')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Sl No</th>
								<th>Circle</th>
								<th>Town</th>
								<th>Feeder Code</th>
								<th>DT Code</th>
								<th>DT Type</th>
								<th>DT Name</th>
								<th id ="action">Values</th>
							</tr>
						</thead>
						<tbody id="updateMaster1">

						</tbody>
					</table>
				</div>
				
				<div class="tabbable tabbable-custom" id="showDTDetailsData" hidden="true">
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_3', 'DTDashboard Reports')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_3">
						<thead>
							<tr>
								<th>Sl No</th>
								<th>Circle</th>
								<th>Town</th>
								<th>Section</th>
								<th>Feeder Code</th>
								<th>DT Code</th>
								<th>DT Name</th>
								<th>Instance DT Count</th>
							</tr>
						</thead>
						<tbody id="updateMaster3">

						</tbody>
					</table>
				</div>
					
					
			<div class="tabbable tabbable-custom" id="showDTDetailsWithInstances" hidden="true">
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_2', 'ToBeApproveDTMeter');">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
							<th>Sl No</th>
								<th>DT Code</th>
								<th>DT Name</th>
								<th> Meter_number</th>
								<th>DT Capacity (KVA)</th>
								<th>DT Capacity (AMPS)</th>
								<th>TimeStamp</th>
								<th>KWH</th>
								<th>KVAH</th>
								<th>KVA</th>
								<th>IR</th>
								<th>IY</th>
								<th>IB</th>
								<th>VR</th>
								<th>VY</th>
								<th>VB</th>
								<th>PF</th>
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
	
	<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
	<div class="modal-dialog" style="width: 80%; margin-right:30px;">
		<div class="modal-dailog">
		<div class="modal-content">
        <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
			<h4 class="modal-title" id="tabTitle"></h4>  
        </div>
        <div class="modal-body">
        <div class="portlet-body">
         <div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
						  <div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport2" onclick="tableToExcel2('sample_4', 'DTInstances')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div> 
						<table class="table table-striped table-hover table-bordered" id="sample_4">
						<thead>
						<tr>
								<th>Sl No</th>
								<th>DT Code</th>
								<th>DT Name</th>
								<th> Meter_number</th>
								<th>DT Capacity (KVA)</th>
								<th>DT Capacity (AMPS)</th>
								<th>timeStamp</th>
								<th>KWH</th>
								<th>KVAH</th>
								<th>KVA</th>
								<th>IR</th>
								<th>IY</th>
								<th>IB</th>
								<th>VR</th>
								<th>VY</th>
								<th>VB</th>
								<th>PF</th>
							</tr>
						</thead>
						<tbody id="showTotalOnclickInstances">
						</tbody>
						</table>
						</div>
        </div>
		</div>
		</div>
		</div>
		</div>
	
</div>






<script>


function getReport() {
	
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
		url : './getdtanditsInstances',
	
		type : 'GET',
		data : {
			zone : zone,
			circle : circle,
			report : report,
			reportIdPeriod : reportIdPeriod
			
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			 $("#imageee").hide();

			 if (report == "Overload DT" || report == "Underload DT" || report == "Unbalance DT") {
					 
				 if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
									+ "<td>"+ ((resp[1]  == null) ? "": resp[1] ) + " </td>"				
									+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"				
									+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
									 + "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalinstanceDetails(this.id)' id='"+resp[5]+"'>"+(resp[6]==null?"":resp[6])+"</a></td>";
								    +"</tr>";
						}
						$('#sample_3').dataTable().fnClearTable();
						$("#updateMaster3").html(html);
						loadSearchAndFilter('sample_3');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
					
			 }

			 
			 if ( report == "PowerFailure DT" ) {
				 
				 if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
									+ "<td>"+ ((resp[1]  == null) ? "": resp[1] ) + " </td>"
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"				
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"	
									+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"			
									 + "<td style='text-align: center; vertical-align: middle;'><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return totalinstanceDetails(this.id)' id='"+resp[4]+"'>"+(resp[6]==null?"":resp[6])+"</a></td>";
								    +"</tr>";
						}
						$('#sample_3').dataTable().fnClearTable();
						$("#updateMaster3").html(html);
						loadSearchAndFilter('sample_3');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
					
			 }
			
			 
			 if (report == "Good Power Factor" || report == "Nominal Power Factor" || report == "Poor Power Factor" ||  report == "Good Utilization Factor" || report == "Nominal Utilization Factor" || report == "Poor Utilization Factor" || report == "Good Load Factor" || report == "Nominal Load Factor" || report == "Poor Load Factor") {
				 
				 if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							var j = i + 1;
							html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
									+ "<td>"+ ((resp[1]  == null) ? "": resp[1] ) + " </td>"				
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
									+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"				
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
									+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>";
								    +"</tr>";
						}
						$('#sample_1').dataTable().fnClearTable();
						$("#updateMaster1").html(html);
						loadSearchAndFilter('sample_1');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
					
			 }

			 if (report == "Overload DT Instances" || report == "Underload DT Instances" || report == "Unbalance DT Instances" || report == "PowerFailure DT Instances") {

				 if (response != null && response.length > 0) {
						var html = "";
						
						for (var i = 0; i < response.length; i++) {
							var resp = response[i];
							if(report == "Overload DT Instances" || report == "Underload DT Instances" || report == "Unbalance DT Instances"){
							var j = i + 1;
							html += "<tr>" 
								+ "<td>"+ j + "</td>"
								+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
								+ "<td>"+ ((resp[1]  == null) ? "": resp[1] ) + " </td>"
								+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
								+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"	
								+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
								+ "<td>"+ ((resp[5] == null) ? "": moment(resp[5]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"		
								+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
								+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
								+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
								+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
								+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
								+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
								+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
								+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
								+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
								+ "<td>"+ ((resp[15]  == null) ? "": resp[15] ) + " </td>";	
								+"</tr>";
							}else{
								var j = i + 1;
								html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
									+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"			
									+ "<td>"+ ((resp[5] == null) ? "": moment(resp[5]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
									+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
									+ "<td>"+ ((resp[7]  == null) ? "": resp[7] ) + " </td>"	
									+ "<td>"+ ((resp[8]  == null) ? "": resp[8] ) + " </td>"				
									+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"				
									+ "<td>"+ ((resp[10] == null) ? "": resp[10]) + " </td>"
									+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"
									+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
									+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
									+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
									+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>";
									
								 
									+"</tr>";
							}
						}
					
						$('#sample_2').dataTable().fnClearTable();
						$("#updateMaster2").html(html);
						loadSearchAndFilter('sample_2');
					} else {
						bootbox.alert("No Relative Data Found.");
					}
			 }

				
		},
		complete: function()
		{  
			loadSearchAndFilter('sample'); 
		} 
	});

}

function totalinstanceDetails(dttpid){
	
	var dttpid = dttpid;
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var report =$("#reportId").val();
	var reportIdPeriod =$("#reportIdPeriod").val();
	var meterno = $("#meterno").val();
	
	 $.ajax({
    	 url:"./totalinstanceDetails",
           type:'GET',
           data:{
        	   dttpid:dttpid,	   
        	   zone:zone,
        	   circle:circle,
        	   report:report,
        	   reportIdPeriod:reportIdPeriod,
        	   meterno:meterno
               },
           success:function(response)
           {
        	   var html="";
        	 //  alert(response);
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
						+ "<td>"+ ((resp[5] == null) ? "": moment(resp[5]).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
						+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
						+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
						+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
						+ "<td>"+ ((resp[9]  == null) ? "": resp[9] ) + " </td>"	
						+ "<td>"+ ((resp[10]  == null) ? "": resp[10] ) + "</td>"				
						+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"				
						+ "<td>"+ ((resp[12] == null) ? "": resp[12]) + " </td>"
						+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
						+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
						+ "<td>"+ ((resp[15] == null) ? "": resp[15]) + " </td>";
				
		
					+"</tr>";
				}
                $('#sample_4').dataTable().fnClearTable();
            	$("#showTotalOnclickInstances").html(html);
           },
           complete: function()
      		{  
      			loadSearchAndFilter('sample_4');
      			$('#imageee').hide();
      		} 
    });
	
}



</script>


<script>

function showTable(value){
	
	if (value == "Overload DT" || value == "Underload DT" || value == "Unbalance DT" || value == "PowerFailure DT") {
		$('#sample_2').dataTable().fnClearTable();
		$('#sample_1').dataTable().fnClearTable();
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_3');
		$('#showDTDetailsWithInstances').hide();
		$('#showDTDetails').hide();
		$('#showDTDetailsData').show();
		$('#action').hide();
	}
	if (value == "Overload DT Instances" || value == "Underload DT Instances" || value == "Unbalance DT Instances" || value == "PowerFailure DT Instances" ) {
		$('#sample_2').dataTable().fnClearTable();
		$('#sample_1').dataTable().fnClearTable();
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_2');
		$('#showDTDetailsData').hide();
		$('#showDTDetails').hide();
		$('#showDTDetailsWithInstances').show();
		}
	if(value =="Good Power Factor" || value == "Nominal Power Factor" || value == "Poor Power Factor" || value =="Good Utilization Factor" || value == "Nominal Utilization Factor" || value == "Poor Utilization Factor" || value =="Good Load Factor" || value == "Nominal Load Factor" || value == "Poor Load Factor"){
		$('#sample_2').dataTable().fnClearTable();
		$('#sample_1').dataTable().fnClearTable();
		$('#sample_3').dataTable().fnClearTable();
		loadSearchAndFilter('sample_1');
		$('#showDTDetailsWithInstances').hide();
		$('#showDTDetailsData').hide();
		$('#showDTDetails').show();
		$('#action').show();	
	}
	
	if (value == "Overload DT" || value == "Overload DT Instances") {
		$('#underload').hide();
		$('#unbalance').hide();
		$('#overload').show();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#powerfailure').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Underload DT" || value == "Underload DT Instances") {
		$('#underload').show();
		$('#overload').hide();
		$('#unbalance').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#powerfailure').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Unbalance DT" || value == "Unbalance DT Instances") {
		$('#unbalance').show();
		$('#overload').hide();
		$('#underload').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#powerfailure').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "PowerFailure DT" || value == "PowerFailure DT Instances") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').show();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Good Power Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').show();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#powerfailure').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
		
	}
	
	if (value == "Nominal Power Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').show();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Poor Power Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#nominalpf').hide();
		$('#poorpf').show();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#powerfailure').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	
	if (value == "Good Utilization Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').show();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
		
	}
	
	if (value == "Nominal Utilization Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').show();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Poor Utilization Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').show();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').hide();
	}
	
	if (value == "Good Load Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').show();
		$('#nominallf').hide();
		$('#poorlf').hide();
		
	}
	
	if (value == "Nominal Load Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').show();
		$('#poorlf').hide();
	}
	
	if (value == "Poor Load Factor") {
		$('#unbalance').hide();
		$('#overload').hide();
		$('#underload').hide();
		$('#powerfailure').hide();
		$('#nominalpf').hide();
		$('#poorpf').hide();
		$('#goodpf').hide();
		$('#nominaluf').hide();
		$('#pooruf').hide();
		$('#gooduf').hide();
		$('#goodlf').hide();
		$('#nominallf').hide();
		$('#poorlf').show();
	}
	
 }

</script>
