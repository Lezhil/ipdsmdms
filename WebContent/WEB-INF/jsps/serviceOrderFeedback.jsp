\<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "java.util.ResourceBundle" %>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.test {
	background-color: red;
}

.test2 {
	background-color: green;
}
.resizedTextbox {width: 500px; height: 20px}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						TableManaged.init();
						loadSearchAndFilter('sample_1');  
						FormComponents.init();
						UIDatepaginator.init();
						$("#LFcircle").val("").trigger("change");
						$("#locType").val("").trigger("change");
						$("#issueType").val("").trigger("change");
						$("#issue").val("").trigger("change");
						$("#sonum").val("").trigger("change");
						$("#month").val("").trigger("change");
						$("#status").val("").trigger("change");
						/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
						$('#MDMSideBarContents,#serviceOrderManagementID,#serviceOrderFeedback').addClass(
								'start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

						//alarmData('all');

						$('#saveModal').click(function() {
							   $('#serviceFeedbackModal').modal('hide');
							});
					 
					});
	var tableToExcel=(function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("exportExcel").href = uri + base64(format(template, ctx));
	        document.getElementById("exportExcel").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 
	var zone="%"
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
	                          html += "<option value='"+response1[i][1]+"'>"
	                                  +response1[i][0]+"-"+ response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	              });
	  }

	function mtrDetails(mtrNo) {
		window.location.href = "./mtrNoDetailsMDAS?mtrno=" + mtrNo;
	}

	



	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}

	function showResultsbasedOntownCode(){
		}

	function getSOno(issuetype){
	//	alert(issuetype);
		var issueType=$("#issue").val();

		 $.ajax({
             url : './getSOnofromIssueType',
             type : 'GET',
             dataType : 'json',
             asynch : false,
             cache : false,
             data:{
             	   
            	 issueType : issueType
                    },
             
                     success : function(response) {
                       // alert(response);
                         var html = '';
                         html += "<label class='control-label'>SO Number:</label><select id='sonum' name='sonum'  class='form-control select2me' type='text'><option value=''>Select</option>";
                         for (var i = 0; i < response.length; i++) {
                             html += "<option value='"+response[i]+"'>"
                                     + response[i] + "</option>";
                         }
                         html += "</select><span></span>";
                        $("#soNoTd").html(html);
                         $('#sonum').select2();
                     }
                 });
		
		}

	function getIssues(issue)
	 {
		 //alert(issue);
		 var html="";
      if(issue=="Power theft")
          {
   	   html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option>";
   	   html += "<option value='Suspected Power Theft'>Suspected Power Theft</option>";
          }
      
      if(issue=="Meter Events")
      {
   	   $.ajax({
              url : './getEventListfromMeterEvents',
              type : 'GET',
              dataType : 'json',
              asynch : false,
              cache : false,
                      success : function(response) {
                          //alert(response);
                          var html = '';
                          html += "<label class='control-label'>Issue:</label><select id='issue' name='issue' onchange='getSOno(this.value)' class='form-control select2me' type='text'><option value=''>Select</option>";
                          for (var i = 0; i < response.length; i++) {
                              html += "<option value='"+response[i]+"'>"
                                      + response[i] + "</option>";
                          }
                          html += "</select><span></span>";
                         $("#issueId").html(html);
                          $('#issue').select2();
                      }
                  });
      }
      
      if(issue=="Meter Exception/Alarms")
      {
   	   $.ajax({
              url : './getMeterExceptionAlarmList',
              type : 'GET',
              dataType : 'json',
              asynch : false,
              cache : false,
                      success : function(response) {
                          //alert(response);
                          var html = '';
                          html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'onchange='getSOno(this.value)'  class='form-control select2me' type='text'><option value=''>Select</option>";
                          for (var i = 0; i < response.length; i++) {
                              html += "<option value='"+response[i]+"'>"
                                      + response[i] + "</option>";
                          }
                          html += "</select><span></span>";
                         $("#issueId").html(html);
                          $('#issue').select2();
                      }
                  });
      }
      
      if(issue=="Communication Fail")
      {
	   html += "<label class='control-label'>Issue:</label><select id='issue' name='issue' onchange='getSOno(this.value)' class='form-control select2me' type='text'><option value=''>Select</option>";
	   html += "<option value='Consistent Communication Fail'>Consistent Communication Fail</option>";
      }

     		html += "</select><span></span>";
			$("#issueId").html(html);
			$('#issue').select2();
          

	}
</script>
<div class="page-content">

	<div class="portlet box blue" hidden="true">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Generate Service Orders
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal">
				<div class="form-body">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">From Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="fromdate"
											id="fromdate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">To Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="todate"
											id="todate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="row">
									<label class="control-label col-md-3">Alarm Types</label>
									<div class="col-xs-7 col-sm-3">
										<div class="checkbox-list">
											<label> <input type="checkbox" id="Tamper"
												name="alaramtype" value="Tamper"> Tamper Data
											</label> <label> <input type="checkbox" id="Outage"
												name="alaramtype" value="13"> Outage Data
											</label> <label> <input type="checkbox" id="Others"
												name="alaramtype" value="1178"> Others
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">

								<label class="control-label col-md-3">Meter Types</label>
								<div class="col-md-9">
									<div class="checkbox-list">
										<label> <input type="checkbox" id="HT"
											name="metertype" value="LT"> LT
										</label>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="alaramtypeValue" name="alaramtypeValue">
						<input type="hidden" id="metertypeValue" name="metertypeValue">
						<!--/span-->
					</div>
					<!--/row-->
				</div>
				<div class="form-actions fluid">
					<div class="row">
						<div class="col-md-6"></div>
						<div class="col-md-6">
							<div class="col-md-offset-8 col-md-9">
								<button type="submit" class="btn green"
									onclick="return submitData();">View</button>
								<button type="button" class="btn default">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- Display Error Message -->
	<c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>

	<div class="portlet box blue ">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Service Order Feedback
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
		
		
		
		<div class="col-md-3">
				<div id="locTypeTd" class="form-group">
					<label class="control-label">Location Type:</label> <select
						class="form-control select2me" id="locType"
						name="locType">
						<option value="">Select</option>
						<option value="BOUNDARY METER">BOUNDARY METER</option>
						<option value="DT">DT</option>
						<option value="FEEDER METER">FEEDER METER</option>

					</select>
				</div>
			</div>
			
			
			<div class="col-md-3">
				<div id="issueTypeTd" class="form-group">
					<label class="control-label">Issue Type:</label> <select
						class="form-control select2me" id="issueType"
						name="issueType" onchange="getIssues(this.value)">
						<option value="">Select</option>
						<<!-- option value="Power theft">Power Theft</option> -->
						<option value="Meter Events">Meter Events</option>
						<option value="Meter Exception/Alarms">Meter Exception/Alarms</option>
						<option value="Communication Fail">Communication Fail</option>
					</select>
				</div>
			</div>
			
			
			<div class="col-md-3" >
				<div id="issueId" class="form-group" >
					<label class="control-label">Issue:</label> <select
						class="form-control select2me" id="issue"
						name="issue" onchange="getSOno(this.value)">
						<option value="">Select</option>
					</select>
				</div>
			</div>
			</div>
			
			<div class="row" style="margin-left: -1px;">
			
			<div class="col-md-3">
				<div id="soNoTd" class="form-group">
					<label class="control-label" style="margin-top: -10px;">SO Number:</label> <select
								class="form-control select2me" id="sonum"
								name="sonum">
									<option value="">Select</option>
							
							</select>
			</div>
			</div>
			
			
			<div class="col-md-3">SO&nbsp;Date&nbsp;<font color="red">*</font>
					<div class="form-group">
							<div class="input-group date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="0d" data-date-viewmode="years" id="monthyear">
 							<input type="text"  class="form-control" name="month" id="month"   >
							<span class="input-group-btn">
							<button class="btn default" type="button" id="month" > <i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</div>
							</div>
						
						
						
						<div class="col-md-3">Status&nbsp;:
							<div id="statusTd"><select
								class="form-control select2me" id="status"
								name="status">
								<option value="">Select</option>
											<option value="Open">Open</option>
											<option value="Closed">Closed</option>
											<option value="Re-Open">Re-Open</option>
							</select></div>
							</div>
							</div>
			
		
		
		
		
		<!-- <div class="tab-content">
					<div id="tab_1-1" class="tab-pane active"> -->
		<%-- <form role="form" class="form-horizontal" action=" " method="post"> --%>
		<%-- <div class="portlet-body form">
			<form id="dailyAvgLoadId" action="">
				<table>
					<tbody>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th class="block">Circle&nbsp;<font color="red">*</font>:</th>
							<th id=""><select
								class="form-control select2me input-medium" id="circle"
								name="circle" onchange="showDivision(this.value);">
									<option value="">Select</option>
							 		<option value="%">ALL</option>
							<c:forEach items="${circles}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
							</select></th>
							
							<th class="block">Division&nbsp;<font color="red">*</font>:</th>
							<th id="divisionTd"><select
								class="form-control select2me input-medium" id="division"
								name="division" onchange="showSubdivByDiv(this.value);">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th>
							
							<th class="block">Sub&nbsp;Division&nbsp;<font color="red">*</font>:</th>
							<th id="subdivTd"><select
								class="form-control select2me input-medium" id="sdoCode"
								name="sdoCode" onchange="showTownBySubdiv(this.value);">
								<option value="">Select</option>
								<option value="%">ALL</option>
							</select></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
						<th class="block">Town&nbsp;<font color="red">*</font>:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
								<option value="%">ALL</option>
							</select></th>
		<th class="block">Location&nbsp;Type&nbsp;<font color="red">*</font>:</th>
							<th id="locTypeTd"><select
								class="form-control select2me input-medium" id="locType"
								name="locType">
								<option value="">Select</option>
											<option value="BOUNDARY METER">BOUNDARY METER</option>
											<option value="DT">DT</option>
											<option value="FEEDER METER">FEEDER METER</option>
							</select></th>

					
							<th class="block">Issue&nbsp;Type&nbsp;<font color="red">*</font>:</th>
							<th id="issueTypeTd"><select
								class="form-control select2me input-medium" id="issueType"
								name="issueType" onchange="getIssues(this.value)">
								<option value="">Select</option>
											<option value="Power theft">Power theft</option>
											<option value="Meter Events">Meter Events</option>
											<option value="Meter Exception/Alarms">Meter Exception/Alarms</option>
											<option value="Communication Fail">Communication Fail</option>
							</select></th>
							</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						
						<tr>
							<th class="block">Issue&nbsp;<font color="red">*</font>:</th>
							<th id="issueTd"><select
								class="form-control select2me input-medium" id="issue"
								name="issue">
								<option value="">Select</option>
							</select></th>

							<th class="block">SO&nbsp;NUmber&nbsp;:</th>
							<th id="soNoTd"><select
								class="form-control select2me input-medium" id="sonum"
								name="sonum">
									<option value="">Select</option>
							<c:forEach items="${so_number}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
							</select></th>

							
								<th class="block">Report&nbsp;Month&nbsp;<font color="red">*</font>:</th>
							<td><div class="form-group">
							<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd"  data-date-end-date="0d" data-date-viewmode="years" id="monthyear">
 							<input type="text"  class="form-control" name="month" id="month"   >
							<span class="input-group-btn">
							<button class="btn default" type="button" id="month" > <i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</div>
							</td>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th class="block">Status&nbsp;:</th>
							<th id="statusTd"><select
								class="form-control select2me input-medium" id="status"
								name="status">
								<option value="">Select</option>
											<option value="Open">Open</option>
											<option value="Closed">Closed</option>
											<option value="Re-Open">Re-Open</option>
							</select></th>
							
						</tr>
					</tbody>
				</table> --%>
				<div class="modal-footer">

					<button type="button" onclick="return viewServiceOrderDetails();" class="btn blue pull-left"  style="margin-left: 422px;">View
						</button>
					

				</div>


			<%-- </form> --%>

			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>

		</div>
		
		<div class="portlet-body ">
	
			<div class='row'>
		</div>
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2">
					
						
					</div>
				</div>
			</div>
			
				<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="exportExcel"
												onclick="tableToExcel('sample_1','Service Order Feedback');">Export
													to Excel</a></li>
										</ul>
									</div>
			<table class="table  table-bordered table-hover" id="sample_1">

				<thead>
					<tr>

						<th>Edit</th>
						<th>SubDivison</th>
						<th>SO Number</th>
						<th>SO Date</th>
						<th>From</th>
						<th>To</th>
						<th>Issue Type</th>
						<th>Issue</th>
						<th>Notified</th>
						<th>Email at</th>
						<th>SMS at</th>
						<th>Status</th>
						<th>Corrective Action</th>
						<th>Remark</th>
						
					</tr>
				</thead>
				<tbody id="servicefeedback">
				
				</tbody>
			</table>
		</div>
	



	</div>
<div class="modal fade"  tabindex="-1" role="basic" aria-hidden="true" id="serviceFeedbackModal">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"></h4>
										</div>
										<div class="modal-body">
											<div class="row">
										<label  class="col-md-3 control-label">Status</label>
										<div class="col-md-9" >
										<select
								class="form-control select2me input-medium" id="statusModal"
								name="statusModal" onchange="changeReOpen(this.value)">
											<option value="">Select</option>
											<option value="Open">Open</option>
											<option value="Closed">Closed</option>
											<option value="Re-Open">Re-Open</option>	
											</select>
										</div>
									</div>
									<div class="row">
									
										<div id="reopenblock" hidden="true">
										<label  class="col-md-3 control-label">Reopen Remark</label>
										<div class="col-md-9">
											<input type="text" class="form-control"  placeholder="Enter text" id="reopenRemark">
											<!-- <span class="help-block">A block of help text.</span> -->
										</div></div>
									</div>
									<div class="row">
									
										<label  class="col-md-3 control-label">Corrective Action</label>
										<div class="col-md-9">
										<textarea class="form-control" rows="3" id="correctiveAction"></textarea></div>
									</div>
									<div class="row">
									<label  class="col-md-3 control-label" hidden="true";>ID</label>
										<div class="col-md-9" hidden="true";>
										<textarea class="form-control" rows="1" id="srvcId"></textarea></div>
										</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
											<button type="button" class="btn blue" id="saveModal" onclick="return updateServiceTable();">Save changes</button>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>						
	

</div>

<script>
function viewServiceOrderDetails()
{
  var zone=$("#LFzone").val();
  var circle=$("#LFcircle").val();
  var town=$("#LFtown").val();
  var locType=$("#locType").val();
  var issueType=$("#issueType").val();
  var issue=$("#issue").val();
  var sonum=$("#sonum").val();
  var month=$("#month").val();
  var status=$("#status").val();
 /*  var town=$("#town").val(); */
 //alert(town);
var status_new;
var soNumber;
if(zone==null || zone=="")
{
	bootbox.alert("Enter Region");
	return false;
}
if(circle==null || circle=="")
{
	bootbox.alert("Enter circle");
	return false;
}
if(town==null || town=="")
{
	bootbox.alert("Enter town");
	return false;
}
if(locType==null || locType=="")
{
	bootbox.alert("Enter Location type");
	return false;
}
if(issueType==null || issueType=="")
{
	bootbox.alert("Enter issue type");
	return false;
}
if(issue==null || issue=="")
{
	bootbox.alert("Enter issue");
	return false;
}
if(month==null || month=="")
{
	bootbox.alert("Enter Report Date");
	return false;
}
/* if(sonum==null || sonum=="")
{
	bootbox.alert("Enter SO Number");
	return false;
}
if(status==null || status=="")
{
	bootbox.alert("Enter status");
} */
if(sonum==null || sonum=="")
{
	soNumber='%'
}else
{
	soNumber=sonum
}
if(status==null || status=="")
{
	status_new='%'
}else
{
	status_new=status
}
 $('#imageee').show();
  $.ajax({
      url : "./getServiceOrderDetailsFeedback",
      type : 'GET',
      dataType : 'json',
      asynch : false,
      cache : false,
      data:{
   	   circle : circle,
   	   zone:zone,
   		town : town,
   	   locType : locType,
   	   issue : issue,
   	   issueType : issueType,
   	   sonum : soNumber,
   	   month : month,
   	   status : status_new
          },
      success : function(response) {
          $('#imageee').hide();
    	  var html="";
				for(var i=0;i<response.length;i++)
				{
	               var resp=response[i];
	               //alert(resp[16]);
	               html += "<tr>"
						+ "<td><a href='#' data-toggle='modal' data-target='#serviceFeedbackModal' onclick='editUser(this.id)' id='"
						+ resp[0] + "' style='color:blue'>EDIT</a></td>"
						+ "<td>"+(resp[22]==null?"":resp[22])+"</td>"
						+ "<td>"+(resp[2]==null?"":resp[2])+"</td>"
						+ "<td>"+ ((moment(resp[7]).format('DD-MM-YYYY HH:mm:ss'))==null?"":(moment(resp[7]).format('DD-MM-YYYY HH:mm:ss'))) +"</td>"
						+ "<td>"+ (resp[14]==null?"":resp[14])+"</td>"
						+ "<td>"+ (resp[17]==null?"":resp[17])+"</td>"
						+ "<td>"+ (resp[21]==null?"":resp[21])+"</td>"
						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
						+ "<td>"+ (resp[19]==null?"":resp[19])+"</td>"
						+ "<td>"+ (resp[9]==null?"":resp[9])+"</td>"
						+ "<td>"+ (resp[16]==null?"":resp[16])+"</td>"
						+ "<td>"+ (resp[10]==null?"":resp[10])+"</td>"
						+ "<td>"+ (resp[18]==null?"":resp[18])+"</td>"
						+ "<td>"+ (resp[11]==null?"":resp[11])+"</td>"
						+ "</tr>";
				}		
				
				 $('#sample_1').dataTable().fnClearTable();
				$('#servicefeedback').html(html);
				loadSearchAndFilter('sample_1'); 
			
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_1'); 
		} 


  });
  

}


function updateServiceTable()
{
 var statusModal=$("#statusModal").val();
 var reopenRemark=$("#reopenRemark").val();
 var correctiveAction=$("#correctiveAction").val();
 var serviceid=$("#srvcId").val();
 $.ajax({
     url : "./getUpadtedServiceFeedback",
     type : 'GET',
     dataType : 'json',
     asynch : false,
     cache : false,
     data:{
    	 statusModal : statusModal,
    	 reopenRemark : reopenRemark,
    	 correctiveAction : correctiveAction,
    	 serviceid : serviceid
         },
     success : function(response) {
         var html="";
				if (response == 0){

					bootbox.alert("DATA UPDATED SUCCESFULLY!!!!");
					}else {
						bootbox.alert("ERROR IN UPDATING.....");
					}
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_1'); 
		} 


 });
 

}

function editUser(serviceid) {
//	alert(serviceid);
	var statusModal=$("#statusModal").val();
	 var reopenRemark=$("#reopenRemark").val();
	 var correctiveAction=$("#correctiveAction").val();

	$.ajax({
		url : "./sendidValModal",
		data : {
			id : serviceid
		},
		dataType : 'json',
		success : function(response) {
		//	alert(response);
			for (var i = 0; i < response.length; i++) {
				var resp = response[i];
				
				$("#statusModal").val(resp.sos_status).trigger("change");
				$("#srvcId").val(resp.id);
			
			}

		}
	});

}


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

function changeReOpen(val)
{
	//alert(val);
	var reopenRemark = $('#reopenRemark').val();
	if (val == "Re-Open") {
		$("#reopenblock").show();
	} else {
		$("#reopenblock").hide();
	}
}
</script>


