<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();
		loadSearchAndFilter('sample_1');
		 $('#reportsId,#pfanalysis').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		});

	 $("#month").datepicker({
			format : "mm-yyyy",
			startView : "months",
			minViewMode : "months"
		}); 
		
	var tableToExcelNew= (function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("excelExport").href = uri + base64(format(template, ctx));
	        document.getElementById("excelExport").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})()
	
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
                    html+="<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                    for( var i=0;i<response.length;i++)
                    {
                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                    }
					html+="</select><span></span>";
                    $("#LFcircle").html(html);
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
                            html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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

     function showTownNameandCode(subdivIdName)
 	{
    	 
	      var zone = $('#LFzone').val();
	      var circle = $('#LFcircle').val();
	     
	      $.ajax({
	          url : './getTownsBaseOnCircle',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	              zone : zone,
	              circle : circle,
	              
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#LFtown").html(html);
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
						<i class="fa fa-edit"></i>Revenue Protection Reports
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
				<jsp:include page="locationFilter.jsp"/> 
					<div class="row" style="margin-left: -1px;">

						<%-- <table style="width: 38%">
							<tbody>
								<tr>
								<th id="zone1" class="block">Region&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
										<option value=""></option>
										<option value="%">All</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showTownsBycircle(this.value);">
									<option id="getCircles" value=""></option>
										<option value=""></option>
								</select></th>
								
								<th class="block">Town&nbsp;:</th>
								<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
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
								
									<th class="block">Location&nbsp;Type&nbsp;:</th>
								 <th id="meter"><select
									class="form-control select2me input-medium" id="metertype"
									name="metertype">
									<option value=""></option>
										<c:forEach var="elements" items="${locationList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
										</select></th> 
									
									
									<th class="block">Rdngmonth </th>
							<td>
							<div class="input-group input-medium" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="month" class="form-control from" name="reportFromDate" id="reportFromDate"  required="required"  value="${readingMonth}" >
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							
							<th class="block">Report&nbsp;Month</th>
									<td>
										<div class="input-group">
											<input type="text" class="form-control from" id="reportFromDate"
												name="reportFromDate" placeholder="Select Month"
												style="cursor: pointer" required="required"  value="${readingMonth}"> <span
												class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td>

							
							<th class="block">Report&nbsp; Type:</th>
								 <th id="reportname"><select
									class="form-control select2me input-medium" id="reporttype"
									name="reporttype">
									<option value=""></option>
									<option value="">Select Report</option>
										<option value="pfr">Power Factor Analysis Report</option>
										<option value="vvr">Voltage Variation Report</option>
										</select></th>
										
										 </tr>
										 
										 <tr> 
										
										<th></th>	<th></th>
							
									<td style="text-align: center;">
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table> --%>
						<div class="row" style="margin-left: -1px;">
						
						<div class="col-md-3">
							<strong>Location&nbsp;Type :</strong><div id="meter" class="form-group">
								<select class="form-control select2me"
									id="metertype" name="metertype">
									<option value=""></option>
									<!-- <option value="%">ALL</option> --> 
									<c:forEach var="elements" items="${locationList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
								</select>
							</div>
						</div>
							
						<div class="col-md-3">
						<strong>Report Month :</strong>
						<div id="monthId" class="form-group"><input type="text" class="form-control from"  name="reportFromDate" id="reportFromDate"  required="required"
								placeholder="Select Report Month" style="cursor: pointer"></div>
						</div>
						
						<div class="col-md-3">
							<strong>Report&nbsp;Type :</strong><div id="reportname" class="form-group">
								<select class="form-control select2me"
									id="reporttype" name="reporttype">
									<option value=""></option>
									<option value="">Select Report</option>
										<option value="pfr">Power Factor Analysis Report</option>
										<option value="vvr">Voltage Variation Report</option>
								</select>
							</div>
						</div>
						
						<button type="button" id="viewFeeder" style="margin-bottom: -48px;"
											onclick="return getReport()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button>			
							
					  </div>
					<p>&nbsp;</p>
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div id="powerfactor" style="display: none">
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 1px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
							<li><a href="#" id=""
								onclick="exportPDF2('sample_1', 'Power Factor Analysis Report')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcelNew('sample_1', 'Power Factor Analysis Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table  class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
							<th>Sl No</th>
							<th>Location name</th>
							<!-- <th>K Number</th> -->
							<th>Meter Number</th>
							<th>Monthly Pf</th>
							<th>pf(0.0-0.50)%</th>
							<th>Hours</th>
							<th>pf(0.50-0.70)%</th>
							<th>Hours</th>
							<th>pf(0.70-0.90)%</th>
							<th>Hours</th>
							<th>pf(>0.90)%</th>
							<th>Hours</th>
							<!-- <th>Hours</th> -->
							<th>Total Hours(month)</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						 
						</tbody>
					</table>
					</div>
					
					<div id="voltagevariation" style="display: none">
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 1px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
							<li><a href="#" id=""
								onclick="exportPDF1('sample_2', 'Voltage Variation Analysis Report')">Export to PDF</a></li>
								<li><a href="#" id="excelExport1"
									onclick="tableToExcelNew1('sample_2', 'Voltage Variation Analysis Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<table  class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr>
							<th>Sl No</th>
							<!-- <th>Location name</th>
							<th>Customer Name</th> -->
						<!-- 	<th>K Number</th> -->
						    <th>Town Name</th>
							<th>Location Name</th>
							<th>Meter Number</th>
							<th>Rated Voltage</th>
							<th>-6%_to_0%</th>
							<th>Hours</th>
							<th>0%_to_5%</th>
							<th>Hours</th>
							<th>less_-6%</th>
							<th>Hours</th>
							<th>great_5%</th>
							<th>Hours</th>
							<!-- <th>Hours</th> -->
							<th>Total Hours(month)</th>
							</tr>
						</thead>
						<tbody id="voltagevariationreport">
						 
						</tbody>
					</table>
					</div>
					
				
			</div>
		</div>
	</div>
	
</div>



<script>


function getReport() {
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	//var div = $('#division').val();
	//var subdiv = $('#sdoCode').val();
	var rdngmnth=$('#reportFromDate').val();
	var metertype=$('#metertype').val();
	var reporttype=$('#reporttype').val();
	var town=$('#LFtown').val();

	if(zone==""){
		   bootbox.alert("Please select Region");
		   return false;

		  }

	if(circle==""){
		   bootbox.alert("Please select circle");
		   return false;

		  }

	if(town==""){
		   bootbox.alert("Please select Town");
		   return false;

		  }
	  
	  if(metertype==""){
		   bootbox.alert("Please select Meter Type");
		   return false;

		  }
	  if(rdngmnth==""){
		   bootbox.alert("Please select Reading Month");
		   return false;

		  }
	  if(reporttype==""){
		   bootbox.alert("Please select Report Type");
		   return false;

		  }
	  
	
	if(reporttype=="pfr"){

		getpfanalysisReport();
		
		}
	else{

		getvoltageanalysisReport();
		}
	
}
function getvoltageanalysisReport(){
	//alert("dsf");
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	//var div = $('#division').val();
	//var subdiv = $('#sdoCode').val();
	var rdngmnth=$('#reportFromDate').val();
	var metertype=$('#metertype').val();
	var town=$('#LFtown').val();
	//alert(metertype);
		$("#voltagevariation").show();
		$('#voltagevariationreport').empty();
		 $("#powerfactor").hide();
       $('#imageee').show();
		$.ajax({
			url : './voltageVariationanalysisreport',
			type : 'GET',
			data : {
				zone : zone,
				circle : circle,
				//div : div,
				//subdiv : subdiv,
				rdngmnth : rdngmnth,
				metertype : metertype,
				town : town,
			},
			dataType : 'JSON',
			asynch : false,
			cache : false,
			success : function(response) {
				$('#imageee').hide();
				//alert(response);
				 if (response.length != 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var element = response[i];
						html += "<tr><td>"+(i+1)+"</td>" 
						+"<td>"+ ((element[6]==null) ? "" : element[6])+"</td>"
						+"<td>"+ ((element[10]==null) ? "" : element[10])+"</td>"
						/* +"<td>"+ ((element[4]==null) ? "" : element[4])+"</td>" */
						+"<td>"+ ((element[0]==null) ? "" : element[0])+"</td>"
						+"<td>"+ ((element[1]==null) ? "" : element[1])+"</td>"
						+"<td>"+ ((element[17]==null) ? "" : element[17])+"</td>"
						+"<td>"+ ((element[18]==null) ? "" : element[18])+"</td>"
						+"<td>"+ ((element[19]==null) ? "" : element[19])+"</td>"
						+"<td>"+ ((element[20]==null) ? "" : element[20])+"</td>"
						+"<td>"+ ((element[15]==null) ? "" : element[15])+"</td>"
						+"<td>"+ ((element[16]==null) ? "" : element[16])+"</td>"
						+"<td>"+ ((element[21]==null) ? "" : element[21])+"</td>"
						+"<td>"+ ((element[22]==null) ? "" : element[22])+"</td>"
						+"<td>"+ ((element[14]==null) ? "" : element[14])+"</td>"
					/* 	+"<td>"+ parseFloat(Math.round(element[14])).toFixed(2) +"</td>" */
						+"</tr>" 
						
						
					}
					$('#sample_2').dataTable().fnClearTable();
					$('#voltagevariationreport').html(html);
					loadSearchAndFilter('sample_2');
					
				} else {
					$('#sample_2').dataTable().fnClearTable();
					$('#voltagevariationreport').html(html);
					bootbox.alert("No meters found");
				} 

			} 
		});
		

		
}
	function getpfanalysisReport(){
	  //var subdiv = $('#sdoCode').val();
	  var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	  var rdngmnth=$('#reportFromDate').val();
	  var metertype=$('#metertype').val();
	  $("#powerfactor").show();
	  $("#voltagevariation").hide();
	  var town=$('#LFtown').val();
	$('#updateMaster').empty();
	$('#imageee').show();


	
	$.ajax({
		url : './pfanalysisreport',
		type : 'GET',
		data : {
			//subdiv : subdiv,
			zone : zone,
			circle : circle,
			rdngmnth : rdngmnth,
			metertype : metertype,
			town : town,
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr><td>"+(i+1)+"</td>" 
					+"<td>"+ ((element[1]==null) ? "" : element[1])+"</td>"
					/* +"<td>"+ ((element[2]==null) ? "" : element[2])+"</td>" */
					+"<td>"+ ((element[0]==null) ? "" : element[0])+"</td>"
					+"<td>"+ ((element[3]==null) ? "" : element[3])+"</td>"
					+"<td>"+ ((element[5]==null) ? "" : element[5])+"</td>"
					+"<td>"+ ((element[4]==null) ? "" : element[4])+"</td>"
					+"<td>"+ ((element[7]==null) ? "" : element[7])+"</td>"
					+"<td>"+ ((element[6]==null) ? "" : element[6])+"</td>"
					+"<td>"+ ((element[9]==null) ? "" : element[9])+"</td>"
					+"<td>"+ ((element[8]==null) ? "" : element[8])+"</td>"
					+"<td>"+ ((element[11]==null) ? "" : element[11])+"</td>"
					+"<td>"+ ((element[10]==null) ? "" : element[10])+"</td>"
					+"<td>"+ ((element[13]==null) ? "" : element[13])+"</td>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				bootbox.alert("No meters found");
			}

		}
	});

}

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
	    endDate: new Date(year, month-1, '30')
	});
	
	function exportPDF1()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town=$('#LFtown').val();
		var rdngmnth=$('#reportFromDate').val();
		var metertype=$('#metertype').val();
		var reporttype=$('#reporttype').val();
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		var zne="",crcl="",twn="",townname1="";
//alert(town)
		if(zone=="%"){
			zne="ALL";
		}else{
			zne=zone;
		}
		if(circle=="%"){
			crcl="ALL";
		}else{
			crcl=circle;
		}
		if(town=="%"){
			twn="ALL";
		}else{
			twn=town;
		}
		if(townname=="%"){
			townname1="ALL";
		}else{
			townname1=townname;
		}
		
		window.location.href=("./VoltageVarRepPDF?zne="+zne+"&crcl="+crcl+"&twn="+twn+"&rdngmnth="+rdngmnth+"&metertype="+metertype+"&reporttype="+reporttype+"&townname1="+townname1);
	}
	
	function exportPDF2()
	{
		var zone = $('#LFzone').val();
		var circle = $('#LFcircle').val();
		var town=$('#LFtown').val();
		var rdngmnth=$('#reportFromDate').val();
		var metertype=$('#metertype').val();
		var reporttype=$('#reporttype').val();
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		var zne="",crcl="",twn="",townname1="";
		//alert(townname)
		if(zone=="%"){
			zne="ALL";
		}else{
			zne=zone;
		}
		if(circle=="%"){
			crcl="ALL";
		}else{
			crcl=circle;
		}
		if(town=="%"){
			twn="ALL";
		}else{
			twn=town;
		}
		if(townname=="%"){
			townname1="ALL";
		}else{
			townname1=townname;
		}
		//alert(townname1)

		window.location.href=("./PowerFactorRepPDF?zne="+zne+"&crcl="+crcl+"&twn="+twn+"&rdngmnth="+rdngmnth+"&metertype="+metertype+"&reporttype="+reporttype+"&townname1="+townname1);
	}
</script>


