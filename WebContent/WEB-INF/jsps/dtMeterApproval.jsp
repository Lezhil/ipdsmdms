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

<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init(); 
						App.init();
						 UIDatepaginator.init(); 
						$("#reportType").val("").trigger("change");
						TableManaged.init();
						formreset();
						
						$('#MDMSideBarContents,#staDtlsId,#dtMeterApproval,#mpmId').addClass('start active ,selected');
						$("#MDASSideBarContents,#dash-board,#360d-view,#buisnessRoleDetails,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');
						 //$("#toid").hide();	
						
						
						var result_flag="${results}";
						var a_flag="${alert_type}";
						if (result_flag !="notDisplay"){
							if (a_flag =="success"){
								bootbox.alert(result_flag);
							}

							if (a_flag =="error"){
								bootbox.alert(result_flag);
							}
							
						}

					
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

	<div class="portlet box blue">

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>DT Meter Approval
			</div>
		</div>

		<div class="portlet-body">
		
				<jsp:include page="locationFilter.jsp"/> 

			<div class="row" style="margin-left: -1px;">
				
						<div class="col-md-4">
							<div class="form-group">
							<label class="control-label"><b>Select Type:</b></label>
								<select class="form-control select2me input-medium"  onchange='showTable(this.value)' id="reportType" name="reportType">
									<option value="">Select Approve Type</option>
									<option value="APPROVED">APPROVED</option>
									<option value="TO BE APPROVE">TO BE APPROVE</option>
								</select>
							</div>

						</div>

				<div class="col-md-6" id="show">
							<button type="button" id="viewDTAppDetails" onclick="viewDTAppDetails()" style="margin-bottom: -65px;" name="viewChangeMeterData" class="btn yellow">
								<b>GET DATA</b>
							</button>
				</div>
			</div>
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
			<br />

			
				<div class="tabbable tabbable-custom" id="showAppDTMtrTableView" hidden="true">
					<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								<li><a href="#" id=""
								         onclick="exportPDF('sample_1','Approved DTMeter List')">Export to PDF</a></li>
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_1', 'ApprovedDTMeterList')">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>SL NO</th>
								<th>REGION CODE</th>
								<th>REGION NAME</th>
								<th>CIRCLE CODE</th>
								<th>CIRCLE NAME</th>
								<th>TOWN CODE</th>
								<th>TOWN NAME</th>
								<th>FEEDER CODE</th>
								<th>DT CODE</th>
								<th>METER NO</th>
								<th>RECEIVED DATE</th>
								<th>APPROVED BY</th>
								<th>APPROVED DATE</th>
							</tr>
						</thead>
						<tbody id="updateMaster1">

						</tbody>
					</table>
				</div>
				
				
				<div class="tabbable tabbable-custom" id="showDTTableView" hidden="true">
					<div class="table-toolbar">
							<!-- <div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								<li><a href="#" id=""
								         onclick="exportPDF('sample_2','ToBe Approve DTMeter List')">Export to PDF</a></li>
									<li><a href="#" id="excelExport"
										onclick="tableToXlxs('sample_2', 'ToBeApproveDTMeter');">Export
											to Excel</a></li>
								</ul>
							</div> -->
							
							
							<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								
									<li><a href="#" id="" onclick="exportPDF('sample_2','ToBe Approve DTMeter List')">Export to PDF</a></li>	       
									<li><a href="#" id="excelExport" onclick="tableToXlxs('sample_2', 'ToBeApproveDTMeter');">Export to Excel</a></li>
							
								</ul>
							</div>
							
							
						</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
								<th>SL NO</th>
								<th>REGION CODE</th>
								<th>REGION NAME</th>
								<th>CIRCLE CODE</th>
								<th>CIRCLE NAME</th>
								<th>TOWN CODE</th>
								<th>TOWN NAME</th>
								<th>FEEDER CODE</th>
								<th>DT CODE</th>
								<th>METER NO</th>
								<th>RECEIVED DATE</th>
							</tr>
						</thead>
						<tbody id="updateMaster2">

						</tbody>
					</table>
				</div>
			
			
		</div>
	</div>

</div>



<div id="stack1" hidden="false" class="modal fade" role="dialog"
	id="popUp" aria-labelledby="myModalLabel10" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">


		<div class="modal-content">
			<div class="modal-header">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-cogs"></i>DT Meter Details
						</div>

					</div>


					<div class="portlet-body">
						<div class="modal-body">

								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Region :</b></label> <input
												type="text" id="region"
												class="form-control placeholder-no-fix" name="region"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Region Code :</b></label> <input
												type="text" id="region_code"
												class="form-control placeholder-no-fix" name="region_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									
									
								</div>
								<div class="row">
								    <div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Circle :</b></label> <input
												type="text" id="circle"
												class="form-control placeholder-no-fix" name="circle"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Circle Code :</b></label> <input
												type="text" id="circle_code"
												class="form-control placeholder-no-fix" name="circle_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
		
								</div>
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Division :</b></label> <input
												type="text" id="division"
												class="form-control placeholder-no-fix" name="division"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Division Code :</b></label> <input
												type="text" id="division_code"
												class="form-control placeholder-no-fix" name="division_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
								</div>
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Sub-Division :</b></label> <input
												type="text" id="subdiv"
												class="form-control placeholder-no-fix" name="subdiv"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Sub-Division Code :</b></label> <input
												type="text" id="subdiv_code"
												class="form-control placeholder-no-fix" name="subdiv_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
	
								</div>
								
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Section :</b></label> <input
												type="text" id="section"
												class="form-control placeholder-no-fix" name="section"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Section Code :</b></label> <input
												type="text" id="sec_code"
												class="form-control placeholder-no-fix" name="sec_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									
								
								</div>
								
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Town :</b></label> <input
												type="text" id="townname"
												class="form-control placeholder-no-fix" name="townname"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Town Code :</b></label> <input
												type="text" id="town_code"
												class="form-control placeholder-no-fix" name="town_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
								
								</div>
								
								<div class="row">
									
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Sub-Station :</b></label> <input
												type="text" id="substation"
												class="form-control placeholder-no-fix" name="substation"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Sub-Station Code :</b></label> <input
												type="text" id="substation_code"
												class="form-control placeholder-no-fix" name="substation_code"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									
								</div>
								
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Feeder Name :</b></label> <input
												type="text" id="feedername"
												class="form-control placeholder-no-fix" name="feedername"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>Feeder Code :</b></label> <input
												type="text" id="feedercode"
												class="form-control placeholder-no-fix" name="feedercode"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									
								</div>
								
								<div class="row">
								
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>DT Name :</b></label> <input
												type="text" id="dtname"
												class="form-control placeholder-no-fix" name="dtname"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label"><b>DT Code :</b></label> <input
												type="text" id="dtcode"
												class="form-control placeholder-no-fix" name="dtcode"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
								</div>
								<hr>
								
								<div class="row">
								
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Meter No :</b></label> <input
												type="text" id="meterno"
												class="form-control placeholder-no-fix" name="meterno"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Meter Type :</b></label> <input
												type="text" id="meter_type"
												class="form-control placeholder-no-fix" name="meter_type"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Current Rating :</b></label> <input
												type="text" id="cur_rating"
												class="form-control placeholder-no-fix" name="cur_rating"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								

								</div>
								
							
								
								<div class="row">
								
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Voltage Rating :</b></label> <input
												type="text" id="voltrating"
												class="form-control placeholder-no-fix" name="voltrating"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
								
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Wire :</b></label> <input
												type="text" id="wire"
												class="form-control placeholder-no-fix" name="wire"
												maxlength="12" readonly="readonly"></input>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>MF :</b></label> <input
												type="text" id="mf"
												class="form-control placeholder-no-fix" name="mf"
												readonly="readonly"  data-toggle='tooltip' title='Show Details' maxlength="50"></input>
										</div>
									</div>
								
								</div>
								
								<div class="row">
								
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b> Lattitude :</b></label> <input
												type="text" id="lattitude"
												class="form-control placeholder-no-fix" name="lattitude"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Longitude :</b></label> <input
												type="text" id="longitude"
												class="form-control placeholder-no-fix" name="longitude"
												readonly="readonly" maxlength="50"></input>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>IP Period :</b></label> <input
												type="text" id="ipPeriod"
												class="form-control placeholder-no-fix" name="ipPeriod"
												readonly="readonly"></input>
										</div>
									</div>
									

								</div>
								
								<!-- <div class="row">
								
									<div class="col-md-4">
										<div class="form-group">
											<label class="control-label"><b>Meter Digit</b></label> <input
												type="text" id="meterDigit"
												class="form-control placeholder-no-fix" name="meterDigit"
												readonly="readonly"></input>
										</div>
									</div>
								
									

								</div> -->


								<div class="modal-footer">
									<button type="button" data-dismiss="modal" class="btn blue pull-left"
										onclick="return formreset()">Close</button>
									<button type="button" data-dismiss="modal" id="reject" class="btn red pull-right"
										onclick="return reject(this.value)">Reject</button>
									<button class="btn green pull-right" id="approve"
										type="submit" onclick="return approve(this.value)">Approve</button>
								</div>
							

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>





<script>


	function viewDTAppDetails(){
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town=$("#LFtown").val();
		var reportType =$("#reportType").val();

		if (reportType == "") {
			bootbox.alert("Please Select Approve Type");
			return false;
		}
		
		$("#imageee").show();
		$
		.ajax({
			url : './viewDTAppDetails',
			type : 'POST',
			data : {
				 zone:zone,
	        	 circle:circle,
	        	 town : town,
				reportType : reportType
			},
			dataType : 'json',
			success : function(response) {
				 $("#imageee").hide();

				 if (reportType == "APPROVED") {

					 if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								/* html += "<tr>" 
										+ "<td>"+ j + "</td>"
										+ "<td>"+ ((resp.regioncode == null) ? "": resp.regioncode) + " </td>"
										+ "<td>"+ ((resp.region == null) ? "": resp.region) + " </td>"				
										+ "<td>"+ ((resp.circlecode == null) ? "": resp.circlecode) + " </td>"
										+ "<td>"+ ((resp.circle == null) ? "": resp.circle) + " </td>"				
										+ "<td>"+ ((resp.towncode == null) ? "": resp.towncode) + " </td>"
										+ "<td>"+ ((resp.townname == null) ? "": resp.townname) + " </td>"
										+ "<td>"+ ((resp.feedercode == null) ? "": resp.feedercode) + " </td>"
										+ "<td>"+ ((resp.dtcode == null) ? "": resp.dtcode) + " </td>"
										+ "<td>"+ ((resp.meterid == null) ? "": resp.meterid) + " </td>"
										+ "<td>"+ ((resp.time_stamp == null) ? "": moment(resp.time_stamp).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
										+ "<td>"+ ((resp.updatedby == null) ? "": resp.updatedby) + " </td>"
										+ "<td>"+ ((resp.updateddate == null) ? "": moment(resp.updateddate).format('YYYY-MM-DD HH:mm:ss')) + " </td>"
										
									html +="</tr>"; */



								html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
									+ "<td>"+ ((resp[2]  == null) ? "": resp[2] ) + " </td>"				
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"				
									+ "<td>"+ ((resp[5] == null) ? "": resp[5]) + " </td>"
									+ "<td>"+ ((resp[6] == null) ? "": resp[6]) + " </td>"
									+ "<td>"+ ((resp[7] == null) ? "": resp[7]) + " </td>"
									+ "<td>"+ ((resp[8] == null) ? "": resp[8]) + " </td>"
									+ "<td>"+ ((resp[9] == null) ? "": resp[9]) + " </td>"
									+ "<td>"+ ((resp[10] == null) ? "": moment(resp[10]).format('YYYY-MM-DD HH:mm:ss.SSS')) + " </td>"	
									+ "<td>"+ ((resp[11] == null) ? "": resp[11]) + " </td>"	
									+ "<td>"+ ((resp[12] == null) ? "": moment(resp[12]).format('YYYY-MM-DD HH:mm:ss.SSS')) + " </td>"								
									html +="</tr>";
							}
							$('#sample_1').dataTable().fnClearTable();
							$("#updateMaster1").html(html);
							loadSearchAndFilter('sample_1');
						} else {
							bootbox.alert("No Relative Data Found.");
						}
						
				 }

				  if (reportType == "TO BE APPROVE") {

					 if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var j = i + 1;
								html += "<tr>" 
									+ "<td>"+ j + "</td>"
									+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
									+ "<td>"+ ((resp[1]  == null) ? "": resp[1] ) + " </td>"				
									+ "<td>"+ ((resp[4] == null) ? "": resp[4]) + " </td>"
									+ "<td>"+ ((resp[3] == null) ? "": resp[3]) + " </td>"				
									+ "<td>"+ ((resp[14] == null) ? "": resp[14]) + " </td>"
									+ "<td>"+ ((resp[13] == null) ? "": resp[13]) + " </td>"
									+ "<td>"+ ((resp[25] == null) ? "": resp[25]) + " </td>"
									+ "<td>"+ ((resp[27] == null) ? "": resp[27]) + " </td>"
									+ "<td><a href='#' style='color:blue;' onclick='showData(\""+ resp[0]+ "\",\""+ resp[15]+ "\",\""+ resp[29]+ "\",\""+ reportType+ "\")' data-toggle='tooltip' title='Show Details'  aria-label='View'>"+ ((resp[15] == null) ? "": resp[15]) + " </a></td>"
									+ "<td>"+ ((resp[29] == null) ? "": moment(resp[29]).format('YYYY-MM-DD HH:mm:ss.SSS')) + " </td>"									
									html +="</tr>";
							}
							$('#sample_2').dataTable().fnClearTable();
							$("#updateMaster2").html(html);
							loadSearchAndFilter('sample_2');
						} else {
							bootbox.alert("No Relative Data Found.");
						}
				 }

					
			},
			complete : function() {
				 $("#imageee").hide();
				 if (reportType == "APPROVED") {
					loadSearchAndFilter('sample_1');
				 }
				  if (reportType == "TO BE APPROVE") {
					 loadSearchAndFilter('sample_2');
				 }
				
			}
		});

	}






  function formreset(){
	    $('#townid').val('');
	    $('#townname').val('');
	    $('#technicalLoss').val('');
	    $('#goLiveDate').val('');
	    $("#fileUpload").val('');   
	}

	function showTable(value){

		if (value == "APPROVED") {
			$('#sample_1').dataTable().fnClearTable();
			loadSearchAndFilter('sample_1');
			$('#showDTTableView').hide();
			$('#showAppDTMtrTableView').show();
		}
		if (value == "TO BE APPROVE") {
			$('#sample_2').dataTable().fnClearTable();
			loadSearchAndFilter('sample_2');
			$('#showAppDTMtrTableView').hide();
			$('#showDTTableView').show();
			}

		
	 }


	function showData(id,meterNo,time,reportType){

		$("#imageee").show();
		$
		.ajax({
			url : './viewDTMeterWiseDetails',
			type : 'POST',
			data : {
				id : id,
				meterNo : meterNo,
				time : time,
				reportType : reportType
			},
			dataType : 'json',
			success : function(response) {
				 $("#imageee").hide();
				 $('#region').val(response.region);
				 $('#region_code').val(response.regioncode);
				 $('#circle').val(response.circle);
				 $('#circle_code').val(response.circlecode);
				 $('#division').val(response.division);
				 $('#division_code').val(response.division_code);
				 $('#subdiv').val(response.subdivision);
				 $('#subdiv_code').val(response.subdivisioncode);
				 $('#section').val(response.section);
				 $('#sec_code').val(response.sectioncode);

				 $('#townname').val(response.townname);
				 $('#town_code').val(response.towncode);
				 $('#substation').val(response.substation);
				 $('#substation_code').val(response.substationcode);
				 $('#feedername').val(response.feedername);
				 $('#feedercode').val(response.feedercode);


				 $('#dtname').val(response.dtname);
				 $('#dtcode').val(response.dtcode);
				 $('#meterno').val(response.meterid);
				 $('#meter_type').val(response.metertype);
				 $('#cur_rating').val(response.currentrating);
				 $('#voltrating').val(response.voltagerating);


				 $('#wire').val(response.wire);
				 $('#mf').val(response.mf);
				 $('#lattitude').val(response.lattitude);
				 $('#longitude').val(response.longitude);
				 $('#ipPeriod').val(response.ipperiod);
				 
				 $('#reject').val(response.meterid);
				 $('#approve').val(response.meterid+","+id);
				 
				 
				 $('#stack1').modal('show');

				// $('#reject').val(response.meterid);


			}
		});

		
	 }

   function approve(meterNoId){

	   var mtrNo = meterNoId.split(",")[0];
	   var id = meterNoId.split(",")[1];
		 
	   $("#imageee").show();
		$.ajax({
			url : './checkDTMeterExistOrNot',
			type : 'POST',
			data : {
				meterNo : mtrNo
			},
			dataType : 'text',
			success : function(response) {	
				 $("#imageee").hide();
				if(response !=""){
					$('#stack1').modal('hide');
					bootbox.confirm(mtrNo+ " is already attached for "+ response +" DT,Do you want to Proceed?", function(result) {
						  if(result == true)
				            {
							  //Do  Operation....When meter is attached in somewhere.
							   $("#imageee").show();
							   $.ajax({
									url : './replaceAppDTMeters',
									type : 'POST',
									data : {
										meterNo : mtrNo,
										id : id
									},
									dataType : 'text',
									success : function(response) {
										
									    $("#imageee").hide();
										if(response == "sucess"){
											 bootbox.alert("Meter Mapped Sucessfully!!");
											 $('#sample_2').dataTable().fnClearTable();
											 loadSearchAndFilter('sample_2');
											 $('#viewDTAppDetails').click();
											 $('#viewDTAppDetails').click();
											 
										 }else{
											 bootbox.alert("Some error occured!!");
											 $('#sample_2').dataTable().fnClearTable();
											 loadSearchAndFilter('sample_2');
											 $('#viewDTAppDetails').click();
											 $('#viewDTAppDetails').click();
										}
										
	
									},error : function() {
										 $("#imageee").hide();
										 bootbox.alert("Some error occured!!");
										 $('#sample_2').dataTable().fnClearTable();
										 loadSearchAndFilter('sample_2');
										 $('#viewDTAppDetails').click();
										 $('#viewDTAppDetails').click();
									}
								});
								 
							  
				            }

				       /*      else{
				            	//Do Reject Operation....When meter is attached in somewhere.
				            	//bootbox.alert(mtrNo+ " will reject from Initial sync Table");
				            	$
				    			.ajax({
				    				url : './rejectDTMeters',
				    				type : 'POST',
				    				data : {
				    					meterNo : mtrNo
				    				},
				    				dataType : 'text',
				    				success : function(response) {
				    					 $('#viewDTAppDetails').click();
										 $('#viewDTAppDetails').click();

				    				}
				    			});
				            	
						    } */
					 });
					
				}else{
					//Do all Operation....When meter is not attached any where.
					$('#stack1').modal('hide');
					$("#imageee").show();
					$.ajax({
						url : './approveDTMeters',
						type : 'POST',
						data : {
							meterNo : mtrNo,
							id : id
						},
						dataType : 'text',
						success : function(response) {
							
						    $("#imageee").hide();
							if(response == "sucess"){
								 bootbox.alert("Meter Mapped Sucessfully!!");
								 $('#sample_2').dataTable().fnClearTable();
								 loadSearchAndFilter('sample_2');
								 $('#viewDTAppDetails').click();
								// $('#viewDTAppDetails').click();
								 
							 }else if(response == "failure"){
								 bootbox.alert("Some error occured!!");
								 $('#sample_2').dataTable().fnClearTable();
								 loadSearchAndFilter('sample_2');
								 $('#viewDTAppDetails').click();
								 $('#viewDTAppDetails').click();
								 
							 }
							 else{
								 bootbox.alert("Some error occured!!");
								 $('#sample_2').dataTable().fnClearTable();
								 loadSearchAndFilter('sample_2');
								 $('#viewDTAppDetails').click();
								 $('#viewDTAppDetails').click();
							}
							
	
						},error : function() {
							$("#imageee").hide();
							bootbox.alert("Some error occured!!");
							$('#sample_2').dataTable().fnClearTable();
							loadSearchAndFilter('sample_2');
							 $('#viewDTAppDetails').click();
							 $('#viewDTAppDetails').click();
						}
					}); 


				}
				 
				
				 

			}
		});

	  }
	 

	 function reject(meterNo){
		 
		 $("#imageee").show();
			$
			.ajax({
				url : './rejectDTMeters',
				type : 'POST',
				data : {
					meterNo : meterNo
				},
				dataType : 'text',
				success : function(response) {
					
					 $("#imageee").hide();
					 if(response == "sucess"){
						 bootbox.alert("Meter Rejected Sucessfully!!");
						 $('#sample_2').dataTable().fnClearTable();
						loadSearchAndFilter('sample_2');
						 $('#viewDTAppDetails').click();
						 $('#viewDTAppDetails').click();
						 
					 }else{
						 bootbox.alert("Some error occured!!");
						 $('#sample_2').dataTable().fnClearTable();
						loadSearchAndFilter('sample_2');
						 $('#viewDTAppDetails').click();
						 $('#viewDTAppDetails').click();
					}
				}
			});

	  }

	
	function exportPDF()
    {
		var reportType =$("#reportType").val();
		
		window.open("./appdtmtrlistpdf/"+reportType)

	}
	
	
</script>