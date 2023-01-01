<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="content-type"
	content="application/vnd.ms-excel; charset=UTF-8">
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
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
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						FormComponents.init();
						//loadSearchAndFilter("npp_sample");
						//loadSearchAndFilter('sample_1');
						//loadSearchAndFilter('sample_2');

						$('#eaId,#eaWiseReport,#NPPReportFeeder')
								.addClass('start active ,selected');
						$(
								'#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
						

						$("#govtscheme").val("").trigger("change");
						$("#inputEnrgy").val("").trigger("change");

					});

	var tableToExcelNew = (function() {

		$('#npp_sample_length').val("-1").trigger("change");
		$('#npp_sample_filter').hide();
		$('#npp_sample_length').hide();
		
		var uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>', base64 = function(
				s) {
			return window.btoa(unescape(encodeURIComponent(s)))
		}, format = function(s, c) {
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
			document.getElementById("exportData").href = uri
					+ base64(format(template, ctx));
			document.getElementById("exportData").download = name + '_'
					+ moment().format("DD-MM-YYYY_hh.mm.ss") + '.xls';

		}
		$('#npp_sample_filter').show();
		$('#npp_sample_length').show();
	})()

	function doExcel1() {
		var blob, wb = {
			SheetNames : [],
			Sheets : {}
		};
		var ws1 = XLSX.read(prepareTable(1), {
			type : "binary"
		}).Sheets.Sheet1;
		wb.SheetNames.push($("#sample_1"));
		wb.Sheets($["#sample_1"]) = ws1;

		var ws2 = XLSX.read(prepareTable(2), {
			type : "binary"
		}).Sheets.Sheet1;
		wb.SheetNames.push($("#sample_2"));
		wb.Sheets($["#sample_2"]) = ws2;
		console.log(ws1);
		console.log(ws2);
		console.log(wb);
		blob = new Blob([ s2ab(XLSX.write(wb, {
			bookType : 'xlsx',
			type : 'binary'
		})) ], {
			type : "application/octet-stream"
		});

		saveAs(blob, "test.xlsx");
	}
</script>
<script type="text/javascript">
	var editFlag = 0;
	function getgovtSchme(scheme) {
		$('#town').find('option').remove();
		$.ajax({
			url : "./showGovtSchemetownValue",
			type : 'GET',
			dataType : 'json',
			data : {
				scheme : scheme
			},
			asynch : false,
			cache : false,
			success : function(response) {
				//alert(response);
				var html = '';
				/* html+="<select id='town' name='town' class='form-control select2me input-medium' multiple='multiple' type='text'><option value=''>Select</option>"; */
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
					//html+="<option  value='"+resp[1]+"'>"+(resp[1]+"-"+resp[0])+"</option>"; 

					$('#town').append($('<option>', {
						value : resp[1],
						text : resp[1] + "-" + resp[0],
					}));

					/*  html+="<option  value='"+resp[0]+"-"+resp[1]+"'>"+resp[0]+"-"+resp[1]+"</option>"*/

				}
				html += "</select><span></span>";
				//$("#townTd").html(html);
				//$('#town').select2();
				if (editFlag == 1) {
					$("#town").val(editFlagName).trigger("change");
					editFlag = 0;
				}
			}
		});

	}

	function viewNPPData() {
		//alert();
		var townScheme = $("#govtscheme").val();
		var town = $("#town").val();
		//alert(townScheme);
		//alert(town);
		var period = $("#inputEnrgy").val();



		if(townScheme==null || townScheme==""){
			bootbox.alert("Please Select Govt.Scheme");
			return;
		}
		if(town==null || town==""){
			bootbox.alert("Please Select Town");
			return;
		}
		if(period==null || period==""){
			bootbox.alert("Please Select Period");
			return;
		}

		$("#sample_1").show();
		$("#sample_2").show();
		$("#npp_sample").show();
		 $('#imageee').show();
		$.ajax({
			url : "./showNPPFeederData",
			type : 'POST',
			data : {
				'town' : town,
				'period' : period
			},
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				var html = "";
				//alert(response);
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];
				/* 	if (townScheme == "RAPDRP") {
						var schemeTown = resp[2];

					} else if (townScheme == "IPDS") {
						var schemeTown = resp[1];
					} */

					html+="<tr>"
						+" <td>"+resp[0]+"</td>"
						+" <td>"+resp[44]+"</td>"
						+" <td>"+resp[42]+"</td>"
						+" <td>"+resp[43]+"</td>"
						+" <td>"+resp[02]+"</td>"
						
						+" <td>"+resp[04]+"</td>"
						+" <td>"+resp[40]+"</td>"
						
						+" <td>"+resp[41]+"</td>"
						+" <td>"+resp[38]+"</td>"
						+" <td>"+resp[39]+"</td>"
						
						
						+" <td>"+resp[6]+"</td>"
						+" <td>"+resp[7]+"</td>"
						+" <td>"+resp[8]+"</td>"
						+" <td>"+resp[9]+"</td>"
						+" <td>"+resp[10]+"</td>"
						+" <td>"+resp[11]+"</td>"
						+" <td>"+resp[12]+"</td>"
						+" <td>"+resp[13]+"</td>"
					
						+" <td>"+resp[14]+"</td>"
						+" <td>"+resp[15]+"</td>"
						+" <td>"+resp[16]+"</td>"
						+" <td>"+resp[17]+"</td>"
						+" <td>"+resp[18]+"</td>"
						+" <td>"+resp[19]+"</td>"
						+" <td>"+resp[20]+"</td>"
						+" <td>"+resp[21]+"</td>"
						
						+" <td>"+resp[22]+"</td>"
						+" <td>"+resp[23]+"</td>"
						+" <td>"+resp[24]+"</td>"
						+" <td>"+resp[25]+"</td>"
						+" <td>"+resp[26]+"</td>"
						+" <td>"+resp[27]+"</td>"
						+" <td>"+resp[28]+"</td>"
						+" <td>"+resp[29]+"</td>"
						
						+" <td>"+resp[30]+"</td>"
						+" <td>"+resp[31]+"</td>"
						+" <td>"+resp[32]+"</td>"
						+" <td>"+resp[33]+"</td>"
						+" <td>"+resp[34]+"</td>"
						+" <td>"+resp[35]+"</td>"
						+" <td>"+resp[36]+"</td>"
						+" <td>"+resp[37]+"</td>"
						;
				}

				$('#npp_sample').dataTable().fnClearTable();
				$("#nppFeederData").html(html);
				loadSearchAndFilter('npp_sample');
			},

			complete : function() {
				loadSearchAndFilter('npp_sample');
			}

		});
	}

	
</script>

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>NPP Report Feeder</strong>
			</div>
		</div>


		<div class="portlet-body">
			<div id="selectionDetails" class="row" style="margin-left: -1px;">
				<div class="col-md-3">
					<strong>Select Govt. Scheme</strong>
					<div id="govtSch" class="form-group">
						<select class="form-control select2me" id="govtscheme"
							name="govtscheme" onchange="getgovtSchme(this.value);">
							<option value="0">Select</option>
							<!-- <option value="RAPDRP">RAPDRP</option> -->
							<option value="IPDS">IPDS</option>

						</select>
					</div>
				</div>
				<div class="col-md-4">
					<strong>Town</strong>
					<div id="townTd" class="form-group">
						<select class="form-control select2me"
							id="town" name="town">
							<option value="">Select</option>

						</select>
					</div>
				</div>
				<div class="col-md-3">
					<strong>Input Energy Period</strong>
					<div id="energyTd" class="form-group">
					
					<input type="text" class="form-control from"  id="inputEnrgy" style="width:200px;" 
							onchange="dateformat(this.value);"	name="inputEnrgy" placeholder="Select From Month" style="cursor: pointer">
					<%-- 	<select class="form-control select2me" id="inputEnrgy"
							name="inputEnrgy" onchange="dateformat(this.value);">
							<option value="">Select</option>
							<!-- <option value="ALL">ALL</option> -->
							<c:forEach items="${monthyearList}" var="mnthyrVal">
								<option value="${mnthyrVal}">${mnthyrVal}</option>
							</c:forEach>
						</select> --%>
					</div>
				</div>
			</div>
			<div class="row" style="margin-left: 391px;">
				<button type="button" id="viewData" style="margin-left: -30px;"
					onclick="return viewNPPData();" name="viewData" class="btn blue">
					<b>View Report</b>
				</button>

				<!--  <button type="button" id="exportData"
											style="margin-left: 20px;" onclick="tableToExcelNew('npp_sample','NPP Feeder Report')"
											name="exportData" class="btn blue">
											<b>Export to Excel</b>
										</button>  -->
				<!-- <button type="button" id="exportData"
											style="margin-left: 20px;" class="btn blue"  name="exportData"
									onclick="tableToExcelNew1('sample_1', 'NPP Feeder Report')">Export
										to Excel</button> -->

				<a href="#" id="exportData" name="exportData" class="btn blue"
					onclick="exportToExcelMethod('nppFeederReportTable', 'NPP Feeder Report')">Export
					to Excel</a>




				<button type="button" id="jsonData" onclick="downloadReport()"
					style="margin-left: 0px;" name="jsonData" class="btn blue">
					<b>Export to JSON</b>
				</button>
				
				<a href="#" id="" name="" class="btn blue"
					onclick="exportPDF('nppFeederReportTable', 'NPP Feeder Report')">Export
					to PDF</a>
					
			</div>
			<br> <br> <br>

			<div class="portlet-body" id="nppFeederReportTable">

				<table class="table table-striped" id="sample_1" >
					<thead>
						<tr style="background-color: lightgray;">
							<th class="text-center">NPP REPORT</th>
						</tr>
						<tr style="background-color: white;">
							<th></th>
						</tr>
						<tr style="background-color: lightgray;">
							<th class="text-center">Level of Monitoring : NPP</th>
						</tr>
						<tr style="background-color: white;">
							<th></th>
						</tr>
						<tr style="background-color: lightgray;">
							<th class="text-center">Format : Feeder</th>
						</tr>
						<tr style="background-color: white;">
							<th></th>
						</tr>
					</thead>
				</table>

				<table class="table table-striped" id="sample_2" >
					<thead>
						<tr>
							<td
								style="color: #000000; width: 150px; background-color: #DCDCDC;">Name
								of State :</td>
							<td style="background-color: #DCDCDC;"><input
								class="form-control input-large" name="state" id="state"
								readonly="readonly" value="${stateName}"
								style="width: 100%; padding-bottom: 10px;"></td>
						</tr>
						<tr>
							<td
								style="color: #000000; width: 150px; background-color: #DCDCDC;">Name
								of Discom :</td>
							<td style="background-color: #DCDCDC;"><input
								class="form-control input-large" name="discom" id="discom"
								readonly="readonly" value="${discomName}"
								style="width: 100%; padding-bottom: 10px;"></td>
						</tr>
						<tr>
							<td
								style="color: #000000; width: 150px; background-color: #DCDCDC;">Report
								Month :</td>
							<td style="background-color: #DCDCDC;"><input
								class="form-control input-large" name="repmonth" id="repmonth"
								readonly="readonly" style="width: 100%; padding-bottom: 10px;">
							</td>
						</tr>
						<tr>
							<td
								style="color: #000000; width: 150px; background-color: #DCDCDC;">Input
								Energy Period :</td>
							<td style="background-color: #DCDCDC;"><input
								class="form-control input-large" name="periodmonth"
								id="periodmonth" readonly="readonly"
								style="width: 100%; padding-bottom: 10px;"></td>
						</tr>


					</thead>
				</table>
				<br>
				<br>
				<br>
<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
				<table class="table table-striped table-hover table-bordered"
					id="npp_sample" hidden="true">
					<thead>
						<tr>
							<th>Feeder Code</th>
							<th>Feeder_Type(U/R/M)</th>
							
							<th>Start Billing Period</th>
							<th>End Billing Period</th>
							<th>No_of_Power_Failure</th>
							<th>Duration_of_Power_Failure(Second)</th>
							<th>Minimum_voltage(V)</th>
							<th>Maximum_Current(A)</th>
							<th>Input Energy(kwh)</th>
							<th>Export Energy(kwh)</th>
							
							<th>HT_Industrial_Consumer_Count</th>
							<th>HT_Commercial_Consumer_Count</th>
							<th>LT_Industrial_Consumer_Count</th>
							<th>LT_Commercial_Consumer_Count</th>
							<th>LT_Domestic_Consumer_Count</th>
							<th>Govt_Consumer_Count</th>
							<th>Agri_Consumer_Count</th>
							<th>Others_Consumer_Count</th>
							
							<th>HT_Industrial_Energy_Billed(kwh)</th>
							<th>HT_Commercial</th>
							<th>LT_Industrial_Energy_Billed(kwh)</th>
							<th>LT_Commercial_Energy_Billed(kwh)</th>
							<th>LT_Domestic_Energy_Billed(kwh)</th>
							<th>Govt_Energy_Billed(kwh)</th>
							<th>Agri_Energy_Billed(kwh)</th>
							<th>Others_Energy_Billed(kwh)</th>
							
							<th>HT_Industrial_Amount_Billed</th>
							<th>HT_Commercial</th>
							<th>LT_Industrial_Amount_Billed</th>
							<th>LT_Commercial_Amount_Billed</th>
							<th>LT_Domestic_Amount_Billed</th>
							<th>Govt_Amount_Billed</th>
							<th>Agri_Amount_Billed</th>
							<th>Others_Amount_Billed</th>
							
							<th>HT_Industrial_Amount_Collected</th>
							<th>HT_Commercial_Amount_Collected</th>
							<th>LT_Industrial_Amount_Collected</th>
							<th>LT_Commercial_Amount_Collected</th>
							<th>LT_Domestic_Amount_Collected</th>
							<th>Govt_Amount_Collected</th>
							<th>Agri_Amount_Collected</th>
							<th>Others_Amount_Collected</th>
							
							<!-- <th>Billing Efficiency</th>
							<th>Collection Efficiency</th>
							<th>AT&C Loss</th>
 -->

						</tr>
					</thead>
					<tbody id="nppFeederData"></tbody>
				</table>

			</div>




		</div>
	</div>
</div>
<style>
.select2-container-multi .select2-search-choice-close {
	left: -15px;
}
</style>

	<script type="text/javascript">
		function downloadReport() {

			var govtscheme = $('#govtscheme').val();
			var town = $('#town').val();
			twon = JSON.stringify(town);
			var month = $('#inputEnrgy').val();
			$('#energyBody').empty();
			//alert(month);
			//$('#imageee').show();
			/* $.ajax({
				url : "./downloadNPPDataGeneration",
				type : "GET",
				dataType : 'json',
				asynch : false,
				cache : false,
				success : function(data) {
					alert(data);
					
				}
			}); */

			window.open("./downloadNPPDataGenerationFeeder?monthyear=" + month
					+ "&govtscheme=" + govtscheme + "&town=" + town);

		}
		
		function exportPDF()
		{
			var scheme=$("#govtscheme").val();
	  		var town=$("#town").val();
	  		var period=$("#inputEnrgy").val();
	  		var state=$('#state').val();
	  		var discom=$('#discom').val();
	  		var month=$('#repmonth').val();
	  		var ieperiod=$('#periodmonth').val();
	  		var townname = $("#town option:selected").map(function(){return this.text}).get().join(',');
	  		
	  		window.location.href=("./NPPReportFeederPDF?scheme="+scheme+"&town="+town+"&period="+period+"&state="+state+"&discom="+discom+"&month="+month+"&ieperiod="+ieperiod+"&townname="+townname);
		}

		function exportToExcelMethod()
		{
			var scheme=$("#govtscheme").val();
	  		var town=$("#town").val();
	  		var period=$("#inputEnrgy").val();
	  		var ieperiod=$('#periodmonth').val();

	  		var per=period;
	  		   var year = per.substring(0, 4);
			   var monthNo = per.substring(4, 6);
			   if(monthNo>=12)
				   {
				   year++;
				   }
			   var monthVal=moment().month(monthNo).format('MMM');
			   var endMonth=monthVal +"-"+year;
	  		
	  		window.open("./exportToExcelNPPRepFeederData?scheme="+scheme+"&town="+town+"&period="+period+"&ieperiod="+ieperiod+"&endMonth="+endMonth);
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
		
		
		 function dateformat(param)
		 {
			   var year = param.substring(0, 4);
			   var monthNo = param.substring(4, 6);
			  
			   
			   var monthName =  moment().month(monthNo-1).format('MMM');
			   var firstDate= 01;
			   var lastDate =  new Date(year, monthNo , 0).getDate(); 
			   var fromDate =   monthName+" " +firstDate+ " "+ year;
			   var toDate =  monthName+" " +lastDate+ " "+ year; 
			   var finalDate = fromDate +" "+ "to" +" " + toDate;
			   
			   $("#periodmonth").val(finalDate);
			   
			   var year = param.substring(0, 4);
			   var monthNo = param.substring(4, 6);
			   if(monthNo>=12)
			   {
			   year++;
			   }
			   var monthVal=moment().month(monthNo).format('MMM');
			   var endMonth=monthVal +"-"+year;
			  
		       $("#repmonth").val(endMonth);
		 }	
		
		
		
	</script>