<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/highcharts/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/exporting.js'/>" type="text/javascript"></script>

<!-- <link href="resources/assets/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" /> -->

<script src="<c:url value='/resources/bsmart.lib.js/shim.min.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/xlsx.full.min.js'/>"
	type="text/javascript"></script>
<style>
.table-toolbar {
	margin-bottom: 4px;
}

.col-md-2 {
	width: 24.500%;
}

.col-md-1 {
	width: 24.333%;
}
/* .paginate_button {
width: 10px;
} */
.col-xs-5 {
	width: auto;
}

.dropdown-menu>li>a>.badge {
	position: absolute;
	margin-top: 1px;
	right: 10px;
	display: inline;
	font-size: 14px ! important;
	height: 18px ! important;
	font-weight: bold;
	padding: 2px 5px 0px 5px ! important;
}

.paginate_button {
	padding: 15px 25px;
	font-size: 15px;
	text-align: center;
	cursor: pointer;
	outline: none;
	color: #fff;
	background-color: #717887;
	border: none;
	border-radius: 15px;
	box-shadow: 0 9px #999;
}

.paginate_button:hover {
	background-color: #597bc7
}

.paginate_button:active {
	background-color: #3e8e41;
	box-shadow: 0 5px #666;
	transform: translateY(4px);
}
</style>
<style>
a {
	text-decoration: none;
	display: inline-block;
	padding: 8px 16px;
}

a:hover {
	background-color: #ddd;
	color: black;
}

.previous {
	background-color: #f1f1f1;
	color: black;
}

.next {
	background-color: #4CAF50;
	color: white;
}

.round {
	border-radius: 50%;
}
</style>
<style>
.portlet.box.blue>.portlet-title {
	background-color: #4b8cf8;
}

.alert {
	border: 1px solid transparent;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
}

.modal-dialog {
	padding: 40px 100px 30px 220px;
	width: 100%;
}

.table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td,
	.table tbody>tr>td, .table tfoot>tr>td {
	padding: 8px;
	line-height: 1.428571429;
	vertical-align: top;
}

.badge-success {
	background-color: #3cc051;
}
</style>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						Index.initMiniCharts();
						$("#month").val(getPresentMonthDate('${billmonth}'));
						//loadSearchAndFilter('sample_1');
						//loadSearchAndFilter('sample_2');
						$('#MDMSideBarContents,#reportsId,#consumerReadingSummRepId')
								.addClass('start active ,selected');
						$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
	 $("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	}); 
	var tableToExcelNew1= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport1").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport1").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()
	
	var tableToExcelNew2= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport2").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport2").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()  
		var tableToExcelNew3= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport3").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport3").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()  
		var tableToExcelNew4= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport4").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport4").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()  
		var tableToExcelNew5= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport5").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport5").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()  
	function getConsumerStatus()
	{
	$.ajax({

	url:"./getTotalConsumerDetail",
	 type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
		var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Customer Name</th><th>Customer Address</th><th>Account No.</th> <th>Phone No.</th> </tr></thead>";
		var html="";
		for(var i=0;i<response.length;i++)
		{
           var resp=response[i];
           html += "<tr>"
				+ "<td>"+(i+1)+"</td>"
				+ "<td>"+resp[4]+"</td>"
				+ "<td>"+resp[5]+"</td>"
				+ "<td>"+resp[2] +"</td>"
				+ "<td>"+(resp[6]==null?"":resp[6]) +"</td>"
				
				+ "</tr>";
		}
	$('#sample_1').dataTable().fnClearTable();
		$("#totalConsData").html(html);
		loadSearchAndFilter1('sample_1');
	},

complete : function() {
	loadSearchAndFilter('sample_1');
}
});
		}

	function getReadingStatus()
	{
		var month=$("#month").val();
		//alert(month);
		$.ajax({

			url:"./getReadingConsumerDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data:{
					month : month
					},
				success : function(response) {
				var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Account No.</th><th>Kno</th><th>Meter Number</th> <th>Billmonth</th> </tr></thead>";
				var html="";
				for(var i=0;i<response.length;i++)
				{
		           var resp=response[i];
		           html += "<tr>"
						+ "<td>"+(i+1)+"</td>"
						+ "<td>"+resp[2]+"</td>"
						+ "<td>"+resp[3]+"</td>"
						+ "<td>"+resp[21] +"</td>"
						+ "<td>"+(resp[41]==null?"":resp[41]) +"</td>"
						
						+ "</tr>";
				}
			$('#sample_2').dataTable().fnClearTable();
				$("#readingAvailData").html(html);
				loadSearchAndFilter1('sample_2');
				//$('#month').reset();
			},

		complete : function() {
			loadSearchAndFilter('sample_2');
		}



		});
		 
		
	}

	function getNotReadingAvail()
	{
		var month=$("#month").val();
		$.ajax({

			url:"./getnotReadingConsumerDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data:{
					month : month
					},
				success : function(response) {
				var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Account No.</th><th>Kno</th><th>Meter Number</th>  </tr></thead>";
				var html="";
				for(var i=0;i<response.length;i++)
				{
		           var resp=response[i];
		           html += "<tr>"
						+ "<td>"+(i+1)+"</td>"
						+ "<td>"+resp[2]+"</td>"
						+ "<td>"+resp[1]+"</td>"
						+ "<td>"+resp[0] +"</td>"
						+ "</tr>";
				}
			$('#sample_3').dataTable().fnClearTable();
				$("#readingnotAvailData").html(html);
				loadSearchAndFilter1('sample_3');
			},

		complete : function() {
			loadSearchAndFilter('sample_3');
		}



		});
		 
		
		}

	function getRMSPendingCount()
	{
		var month=$("#month").val();
		$.ajax({

			url:"./getRMSPendingDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data:{
					month : month
					},
				success : function(response) {
				var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Account No.</th><th>Kno</th><th>Meter Number</th>  </tr></thead>";
				var html="";
				for(var i=0;i<response.length;i++)
				{
		           var resp=response[i];
		           html += "<tr>"
						+ "<td>"+(i+1)+"</td>"
						+ "<td>"+resp[3]+"</td>"
						+ "<td>"+resp[2]+"</td>"
						+ "<td>"+resp[4] +"</td>"
						+ "</tr>";
				}
			$('#sample_5').dataTable().fnClearTable();
				$("#rmsPending1").html(html);
				loadSearchAndFilter1('sample_5');
			},

		complete : function() {
			loadSearchAndFilter('sample_5');
		}



		});


	}
	

	function getRMSCount()
	{
		var month=$("#month").val();
		$.ajax({

			url:"./getRMSUploadDetail",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data:{
					month : month
					},
				success : function(response) {
				var htmll = "<thead><tr style='background-color: lightgray'><th>Sl NO</th><th>Account No.</th><th>Kno</th><th>Meter Number</th>  </tr></thead>";
				var html="";
				for(var i=0;i<response.length;i++)
				{
		           var resp=response[i];
		           html += "<tr>"
						+ "<td>"+(i+1)+"</td>"
						+ "<td>"+resp[3]+"</td>"
						+ "<td>"+resp[2]+"</td>"
						+ "<td>"+resp[4] +"</td>"
						+ "</tr>";
				}
			$('#sample_4').dataTable().fnClearTable();
				$("#rmsUpload").html(html);
				loadSearchAndFilter1('sample_4');
			},

		complete : function() {
			loadSearchAndFilter('sample_4');
		}



		});


	}

	
	function getConsumerReport()
	{
		
		$("#getdata").attr("action","./consumerReadingSummRep").submit();
		
		
		
		
       /* var month=$("#month").val();
       if(month==null || month=="")
           {
           bootbox.alert("Please Enter Month-Year.");
           }
       $.ajax({

			url:"./getALLDetailsbasedonMonth",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data:{
					month : month
					},
				success : function(response) {

					 $("#total_span_percent").text(response.totalCons);
					$("#total_span").text(response.total);

					$("#reading_span_percent").text(response.totalReading);
					$("#reading_span").text(response.readingAvail);

					$("#not_reading_span_percent").text(response.totalNotReading);
					$("#not_raeding_span").text(response.readingNotAvail);

					$("#rms_upload_span_percent").text(response.rmsCountper);
					$("#rms_upload_span").text(response.rmsCount);

					$("#rms_pending_span_percent").text(response.rmspendingper);
					$("#rms_pending_span").text(response.rmspending);

					
					
				}	
					
		}); */
		 
       
		}
	</script>

<div class="page-content">
<c:if test="${not empty updateFlag}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: green">${updateFlag}</span>
		</div>
	</c:if>
	<span style="color: red" id="noDataExistMsg"></span>
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>Consumer Reading Summary
					Report</strong>
			</div>
		</div>

		<div class="portlet-body">
		  <form action="#" id="getdata">
			<div class="row" style="margin-left: -1px;">
				<div class=" col-sm-1">
					<div class="input-group input-medium">
						<strong>Month Year :</strong><font color="red">*</font><input
							type="month" class="form-control from"  id="month"
							name="month" required="required" placeholder="Enter billmonth">  <span
							class="input-group-btn">
							<button class="btn default" type="button"
								style="margin-bottom: -17px;">
								<i class="fa fa-calendar"></i>
							</button>
						</span>
					</div>
				</div>
				
			
				<div class=" col-sm-1">
					<button type="button" id="consSummRep"
						style="margin-left: 168px; margin-top: 19px;"
						onclick="return getConsumerReport()" name="consSummRep"
						class="btn green">
						<b>View Data</b>
					</button>
				</div>
			</div>
            </form>
			<br>
			<br>


			<!-- ----------------Pie Chart------------------- -->

			<div class="row ">
				<div class="col-md-6">
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
							<i class="fa fa"></i>Consumer Reading Summary :
							<span style="color: white; font-size: 18px; font-weight: bold;">${billmonth}</span></div>
							<!-- totalMeters -->
						</div>

						<div  style="margin-left: 5px;">
							<div class="col-xs-4">
								<div class="easy-pie-chart">
									<div class="number transactions" id="total_span_percent"
										data-percent="${totalCons}">

										<span>${totalCons}</span>%
									</div>
								</div>


								<label style="color: #8d8888;">
									Total&nbsp;Consumer:&nbsp;&nbsp;&nbsp;&nbsp; <u
									onclick="getConsumerStatus(); return false;"
									data-toggle="modal" data-target="#totalConsModal"
									data-dismiss="modal"
									style="font-weight: bold; color: green; cursor: pointer;">
										<span style="color: orange; font-weight: bold;"
										id="total_span">${total}</span>
								</u> <br />

								</label>
							</div>

							<div class="col-xs-4">
								<div class="easy-pie-chart">
									<div class="number transactions" id="reading_span_percent"
										data-percent="${totalReading}">

										<span>${totalReading}</span>%
									</div>
								</div>


								<label style="color: #8d8888;"> Reading&nbsp;Available&nbsp;&nbsp;:
									<u onclick="getReadingStatus(); return false;"
									data-toggle="modal" data-target="#readingAvailModal"
									data-dismiss="modal"
									style="font-weight: bold; color: green; cursor: pointer;">
										<span style="color: green; font-weight: bold;"
										id="reading_span">${readingAvail}</span>
								</u> <br />
								</label>
							</div>

							<div class="col-xs-4">
								<div class="easy-pie-chart">
									<div class="number transactions" id="not_reading_span_percent"
										data-percent="${totalNotReading}">

										<span>${totalNotReading}</span>%
									</div>
								</div>


								<label style="color: #8d8888;">
									Reading&nbsp;Not&nbsp;Available: <u
									onclick="getNotReadingAvail(); return false;"
									data-toggle="modal" data-target="#readingNotAvailModal"
									data-dismiss="modal"
									style="font-weight: bold; color: green; cursor: pointer;">
										<span style="color: red; font-weight: bold;"
										id="not_raeding_span">${readingNotAvail}</span>
								</u> <br />
								</label>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="portlet-body">
							<div class="row">
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number total" data-percent="${totalCons}"><span>${totalCons}</span>%</div>
										<a class="title" >Total Meters &nbsp;&nbsp;&nbsp;<span  style="color: orange;font-weight: bold;">${total}</span> </a>
									</div>
								</div>
								<div class="margin-bottom-10 visible-sm"></div>
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number upload" data-percent=""><span></span>%</div>
										<a class="title" >Uploaded to RMS &nbsp;&nbsp;&nbsp;<span id="syncId"  style="color: green;font-weight: bold;" ></span></a>
									</div>
								</div>
								<div class="margin-bottom-10 visible-sm"></div>
								<div class="col-md-4">
									<div class="easy-pie-chart">
										<div class="number pending" data-percent=""><span></span>%</div>
										<a class="title" >Uploaded pending to RMS  &nbsp;&nbsp;&nbsp;<span id="unsychid" style="color: red;font-weight: bold;"></span></a>
									</div>
								</div>
							</div>
						</div>
</div></div></div> --%>
				<div class=" col-sm-6">
					<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption">
							<i class="fa fa"></i>Reading Upload Summary :
							<span
					style="color: white; font-size: 18px; font-weight: bold;">${billmonth}</span></div>
							<!-- totalMeters -->
						</div>

						<div  style="margin-left: 5px;">
							<div class="col-xs-4">
								<div class="easy-pie-chart ">
									<div class="number transactions" id="rms_upload_span_percent"
										data-percent="${rmsCountper}">

										<span>${rmsCountper}</span>%
									</div>
								</div>


								<label class="col-xs-7" style="color: #8d8888;">
									Upload&nbsp;to&nbsp;RMS: <u
									onclick="getRMSCount(); return false;" data-toggle="modal"
									data-target="#rmsUplaod" data-dismiss="modal"
									style="font-weight: bold; color: green; cursor: pointer;">
										<span style="color: orange; font-weight: bold;"
										id="rms_upload_span">${rmsCount}</span>
								</u> <br />
								</label>
							</div>

							<div class="col-xs-4">
								<div class="easy-pie-chart ">
									<div class="number transactions" id="rms_pending_span_percent"
										data-percent="${rmspendingper}">

										<span>${rmspendingper}</span>%
									</div>
								</div>


								<label class="col-xs-7" style="color: #8d8888;">
									RMS&nbsp;Reading&nbsp;Upload&nbsp;Pending: <u
									onclick="getRMSPendingCount(); return false;"
									data-toggle="modal" data-target="#rmsPending"
									data-dismiss="modal"
									style="font-weight: bold; color: green; cursor: pointer;">
										<span style="color: orange; font-weight: bold;"
										id="rms_pending_span">${rmspending}</span>
								</u> <br />
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<br>
			<br>





			<!-- ------------Tables----------------- -->

			<div class="portlet box blue" hidden="true">

				<div class="portlet-title" hidden="true">
					<div class="caption">
						<i class="fa fa"></i>Reading Not Available Details <a href="#"
							id="excelExport" class="btn green" style="margin-left: 150px;"
							onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export
							to Excel</a>
					</div>

				</div>
				<div class="portlet-body" id="excelUpload" hidden="true">
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								<th>Subdivision</th>
								<th>Account No.</th>
								<th>K Number</th>
								<th>Consumer Name</th>
								<th>Meter Sr. Number</th>
							</tr>
						</thead>
						<tbody id="ConsumerReadingID">

						</tbody>
					</table>
					<br>
					<br>
					<br>
				</div>
			</div>




			<div class="portlet box blue" hidden="true">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa"></i>RMS Reading Upload Pending Details <a
							href="#" id="excelExport" class="btn green"
							style="margin-left: 150px;"
							onclick="tableToExcel('sample_editable_2', 'DATA STATUS')">Export
							to Excel</a>
					</div>

				</div>
				<div class="portlet-body" id="excelUpload" hidden="true">
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_2">
						<thead>
							<tr>
								<th>Subdidvision</th>
								<th>Account No.</th>
								<th>K Number</th>
								<th>Consumer Name</th>
								<th>Meter Sr. Number</th>
							</tr>
						</thead>
						<tbody id="rmsReadingID">

						</tbody>
					</table>
					<br>
					<br>
				</div>
			</div>











		</div>
	</div>
</div>

<!-- -------------modal---------------- -->
<div id="popUpGrid2">
	<div id="totalConsModal" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						id="closePopUp"></button>
					<h4 class="modal-title">
						<span id="popUpHeading"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box blue">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa"></i>Total Customer<font color="yellow"><b><span
												id="subCode"></span></b></font>
									</div>
								</div>

								<div class="portlet-body">
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="excelExport1"
												onclick="tableToExcelNew1('sample_1','Customer Details');">Export
													to Excel</a></li>
										</ul>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_1">
										<thead>
											<tr>
												<th>Sl.No</th>
												<th>Customer Name</th>
												<th>Customer Address</th>
												<th>Account No.</th>
												<th>Phone No.</th>


											</tr>
										</thead>
										<tbody id="totalConsData">

										</tbody>
									</table>

									<div class="modal-footer">
										<button type="button" class="btn btn-danger"
											data-dismiss="modal">Close</button>


									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="popUpGrid2">
	<div id="readingAvailModal" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						id="closePopUp"></button>
					<h4 class="modal-title">
						<span id="popUpHeading"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box blue">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa"></i>Reading Available<font color="yellow"><b><span
												id="subCode"></span></b></font>
									</div>
								</div>

								<div class="portlet-body">
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="excelExport2"
												onclick="tableToExcelNew2('sample_2','Reading Details');">Export
													to Excel</a></li>
										</ul>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_2">
										<thead>
											<tr>
												<th>Sl.No</th>
												<th>Account No.</th>
												<th>Kno</th>
												<th>Meter Number</th>
												<th>Billmonth</th>


											</tr>
										</thead>
										<tbody id="readingAvailData">

										</tbody>
									</table>

									<div class="modal-footer">
										<button type="button" class="btn btn-danger"
											data-dismiss="modal">Close</button>


									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="popUpGrid2">
	<div id="readingNotAvailModal" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						id="closePopUp"></button>
					<h4 class="modal-title">
						<span id="popUpHeading"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box blue">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa"></i>Reading Not Available<font color="yellow"><b><span
												id="subCode"></span></b></font>
									</div>
								</div>

								<div class="portlet-body">
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="excelExport3"
												onclick="tableToExcelNew3('sample_3','Not Reading Details');">Export
													to Excel</a></li>
										</ul>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_3">
										<thead>
											<tr>
												<th>Sl.No</th>
												<th>Account No.</th>
												<th>Kno</th>
												<th>Meter Number</th>



											</tr>
										</thead>
										<tbody id="readingnotAvailData">

										</tbody>
									</table>

									<div class="modal-footer">
										<button type="button" class="btn btn-danger"
											data-dismiss="modal">Close</button>


									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="popUpGrid2">
	<div id="rmsUplaod" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						id="closePopUp"></button>
					<h4 class="modal-title">
						<span id="popUpHeading"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box blue">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa"></i>Upload to RMS <font color="yellow"><b><span
												id="subCode"></span></b></font>
									</div>
								</div>

								<div class="portlet-body">
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="excelExport4"
												onclick="tableToExcelNew4('sample_4','RMS Upload Details');">Export
													to Excel</a></li>
										</ul>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_4">
										<thead>
											<tr>
												<th>Sl.No</th>
												<th>Account No.</th>
												<th>Kno</th>
												<th>Meter Number</th>



											</tr>
										</thead>
										<tbody id="rmsUpload">

										</tbody>
									</table>

									<div class="modal-footer">
										<button type="button" class="btn btn-danger"
											data-dismiss="modal">Close</button>


									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="popUpGrid2">
	<div id="rmsPending" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						id="closePopUp"></button>
					<h4 class="modal-title">
						<span id="popUpHeading"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box blue">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa"></i>Pending RMS <font color="yellow"><b><span
												id="subCode"></span></b></font>
									</div>
								</div>

								<div class="portlet-body">
									<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="excelExport5"
												onclick="tableToExcelNew5('sample_5','RMS Pending Details');">Export
													to Excel</a></li>
										</ul>
									</div>
									<table class="table table-striped table-hover table-bordered"
										id="sample_5">
										<thead>
											<tr>
												<th>Sl.No</th>
												<th>Account No.</th>
												<th>Kno</th>
												<th>Meter Number</th>



											</tr>
										</thead>
										<tbody id="rmsPending1">

										</tbody>
									</table>

									<div class="modal-footer">
										<button type="button" class="btn btn-danger"
											data-dismiss="modal">Close</button>


									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
/* 	var startDate = new Date();
	var fechaFin = new Date();
	var FromEndDate = new Date();
	var ToEndDate = new Date();
	$('.from').datepicker({
		autoclose : true,
		minViewMode : 1,
		format : 'yyyy-mm'
	}).on(
			'changeDate',
			function(selected) {
				startDate = new Date(selected.date.valueOf());
				startDate.setDate(startDate.getDate(new Date(selected.date
						.valueOf())));

			}); */

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



