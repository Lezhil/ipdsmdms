<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
	$("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				$('#DATAEXCHsideBarContents,#ServiceMonitoringId,#ServiceExceptionsReportId').addClass('start active ,selected');

			    $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
     	                });
	</script>
	
<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Service Exception Notification Report
			</div>
		</div>
		
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;"></div>
			</div>

			<form class="horizontal-form">
				<div class="form-body">
					<div class="row">

						<div class="col-md-3">
							<label class="control-label ">Select Date</label>
							<font color="red">*</font>
							<div class="input-group input-large date-picker input-daterange "
								data-date-format="yyyy-mm-dd">

								<input type="text" autocomplete="off" placeholder="From Date"
									class="form-control" data-date-format="yyyy-mm-dd"
									data-date-viewmode="years" name="fromDate" id="fromDate">
									
								<span class="input-group-addon">To</span> <input type="text"
									autocomplete="off" placeholder="To Date" class="form-control"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years"
									name="toDate" id="toDate">
							</div>
						</div>

						<div class="col-md-3" style="margin-left: 7%;">
							<div class="form-group" id="ServiceReport1">
								<label class="control-label ">Service Name</label>
								<font color="red">*</font> <select
									class="form-control select2me " name="ServiceReport"
									id="ServiceReport">
									<option value="">Select</option>
									<c:forEach items="${service}" var="element">
										<option value="${element}">${element}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col-md-1">
							<div class="form-group">
							<label class="control-label "></label>
								<div>
									<button type="button" 
										onclick="return getReport()" class="btn green" style="margin-top:13%;">View</button>
								</div>
							</div>
						</div>
						
						
						<div class="col-md-1">
							<div class="form-group">
							<label class="control-label "></label>
								<div>
									<button type="button" name="excelExport" class="btn green" style="margin-top:13%;">
				                     <a href="#" id="excelExport" onclick="return tableToExcelNew('sample1','Service Exception Notification Report');"><font color="white">Export to Excel</font></a>
			                        </button>
								</div>
							</div>
						</div>
					
			
			</div>
				</div>
			</form>
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
			<table class="table table-striped table-hover table-bordered"
				id="sample1">
				<thead>
					<tr>
						<th>Service Name</th>
						<th>Requester</th>
						<th>Provider</th>
						<th>Exception</th>
						<th>Exception time</th>
						<th>Notified</th>
						<th>Email Sent to</th>
						<th>SMS Sent To</th>
					</tr>
				</thead>
				<tbody id="updateMaster">
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>

	     function getReport() {
		var fdate = $("#fromDate").val();
		var tdate = $("#toDate").val();
		var service = $("#ServiceReport").val();
		$('#imageee').show();
	      if(fdate=='' || fdate==null){
			bootbox.alert('Please Select from date');
		} 
		else if(tdate=='' || tdate==null){
			bootbox.alert('Please Select todate.');
		}
		else if(service=='' || service==null)
			{
			bootbox.alert('Please Select Service name.');
			}
		else
	     {
			
		    $.ajax({
			url : './getReport',
			type : 'POST',
			data : {
				fdate : fdate,
				tdate : tdate,
				service : service
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				if (response.length != 0) {
				var html = "";
				var select = new Array();
				for (var i = 0; i < response.length; i++) {
				var resp = response[i];
				
				if(resp[5]=="true")
				{
					var value ="Yes";
				}
				else
					{
					var value ="No";
					}
				
				html += "<tr><td>" + resp[0] + "</td>" + "<td>"
				+ resp[1] + "</td>" + "<td>" + resp[2]
				+ "</td>" + "<td>" + resp[3] + "</td>" + "<td>"
				+ moment(resp[4]).format("YYYY-MM-DD HH:mm:ss")
				+ "</td>" + "<td>" + value + "</td>" + "<td>"
				+ resp[6] + "</td>" + "<td>" + resp[7]
				+ "</td>" + " </tr>";

					}
					 $('#sample1').dataTable().fnClearTable(); 
					$('#updateMaster').html(html);
					 loadSearchAndFilter('sample1');

				} else {
					alert("No data");
					$('#sample1').dataTable().fnClearTable();
				}

			}
			  
	        });
		 
	     }
	        }

	var tableToExcelNew = (function() {
	var uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>', base64 = function(s) 
	{
			return window.btoa(unescape(encodeURIComponent(s)))
		         }, 
		    format = function(s, c) {
			return s.replace(/{(\w+)}/g, function(m, p) {
			return c[p];
			})
		    }
		         
		return function(table, name) {
		if (!table.nodeType)
		table = document.getElementById(table)
		        var ctx = {
				worksheet : name || 'Worksheet',
				table : table.innerHTML
			}
			document.getElementById("excelExport").href = uri + base64(format(template, ctx));
			document.getElementById("excelExport").download = name + '_' + moment().format("DD-MM-YYYY_hh.mm.ss") + '.xls';
		 }
	     })
	    ();

	$('.input-daterange').datepicker({
		autoclose : true,
		endDate : '+0d'
	      });
</script>								
										
										
						
				
			
												