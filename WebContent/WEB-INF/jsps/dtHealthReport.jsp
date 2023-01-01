<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">


$(".page-content").ready(function(){  
	 
	
	TableManaged.init();
	FormComponents.init();
	loadSearchAndFilter('sample');
	 $('#dtWiseReport,#dtHealthReport').addClass('start active ,selected');
	 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
		"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');

	 App.init();	
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

//var towncode="";
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
						<i class="fa fa-edit"></i>DT Health Report
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
					<div class="input-group ">
					
						<strong>Report Month:</strong>
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"><span
											class="input-group-btn">
										<button class="btn default" type="button" style="margin-bottom: -17px;">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div>
							
							
							</div>
							
							<div class="col-md-2">

										<button type="button" id="viewFeeder" style="margin-top: 19px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> 
									</div>
									<div class="col-md-6">
								 <div  style="margin-left: 0px;">
									 <label><b>Note:-</b></label> 
						
									 <ol>
									  <li>Overload calculation greater than 100%.</li>  
									  <li>Underload calculation lesser than 20%.</li>
									  <li>Unbalance greater than 50%.</li>
									</ol> 
								 </div>
							   </div>
						</div>

		<%-- 				<table style="width: 38%">
							<tbody>
								<tr>
								<th id="zone1" class="block">Region&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="circle" onchange="showCircle(this.value);">
										<option value=""></option>
										<option value="%">ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showDivision(this.value);">
									<option id="getCircles" value=""></option>
										<option value=""></option>
										
								</select></th>
								
									<th class="block">Town&nbsp;:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th> --%>
							
								<!-- <th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">
									

								</select></th></tr> -->
							<!-- <tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr> -->
								<!-- <th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode"  onchange="showTownBySubdiv(this.value);"></select> -->
									
								<!-- 	<th class="block">Town&nbsp;:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th> -->
					<!-- 				<th class="block">Report&nbsp;Month&nbsp;:</th>
							<td>
							<div class="input-group">
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"> <span
											class="input-group-btn">
										<button class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div>
							</td> -->
							
									<!-- <td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr>
							</tbody>
						</table> -->
						<p>&nbsp;</p>
					
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 6px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="pdfExport"
									onclick="exportPDF()">Export
										to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcelNew('sample', 'DT Health Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-bordered table-hover" id="sample">
						<thead>
							<tr>
							        <th> Sl No</th>
							       
							        <th>Town Name</th>
							         <th>Substation Name</th>
							          <th>Feeder Name</th>
							         <!--   <th>DT Id</th> -->
							            <th>DT TP Id</th>
							            <th>DT Name</th>
							           <!--  <th>MF</th> -->
									<!-- <th>Meter number</th> -->
									<th>KVA Rating</th>
									<th>kWh</th>
									<th>kVah</th>
									<th>Power Factor</th>
									<th>R-Phase Avg Current</th>
									<th>Y-Phase Avg Current</th>
									<th>B-Phase Avg Current</th>
									<th>R-Phase Unbalance</th>
									<th>Y-Phase Unbalance</th>
									<th>B-Phase Unbalance</th>
									<th>Loading_Cond
									_less_20%</th>
									<th>Loading_Cond
									_btw_20%_70%</th>
									<th>Loading_Cond
									_btw_70%_100%</th>
									<th>Loading_Cond
									_greater_100%</th>
									<th>Load Factor</th>
									<th>Utilization Factor</th>
									<th>Overload</th>
									<th>Underload</th>
									<th>Unbalance</th>
									<th>Total Hours</th>
									<th>Power Off Hours</th>
									<th>Power On Hours</th>
									
							</tr>
						</thead>
						<tbody id="updateMaster">
						 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>



<script>


function getReport() {
	//alert(towncode);
	//var subdiv = $('#sdoCode').val();
	//var rdngmnth=$('#reportFromDate').val();
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var division = $('#division').val();
	var subdiv = $('#sdoCode').val();
	var rdngmnth=$('#month').val();
	var town =$("#LFtown").val();
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
	if(division=="")
	{
	bootbox.alert("Please Select division");
	return false;
	}
	if(subdiv=="")
	{
	bootbox.alert("Please Select sub division");
	return false;
	}
	if(town=="")
	{
	bootbox.alert("Please Select Town");
	return false;
	}
	if(rdngmnth=="")
	{
	bootbox.alert("Please Select Report Month");
	return false;
	}
	
	//$('#updateMaster').empty();
	$('#sample').dataTable().fnClearTable();
	$('#imageee').show();
	$.ajax({
		url : './getDTHealthData',
		type : 'GET',
		data : {
			subdiv : subdiv,
			rdngmnth : rdngmnth,
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
				//$('#sample').dataTable().fnClearTable();
				$("#updateMaster").html('');
				var html1 = '';
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					//var maxKvar=
				   //var availability=(element[26]/element[22])*100;
					html1 += "<tr>" 
						+"<td>"+(i+1)+"</td>"
						/*  + "<td>"+ (element[30]==null?"":(element[30]))+ "</td>"  */
						+ "<td>"+ (element[35]==null?"":(element[35]))+ "</td>"
						+ "<td>"+ (element[31]==null?"":(element[31]))+ "</td>"
						+ "<td>"+ (element[32]==null?"":(element[32]))+ "</td>"
						+ "<td>"+ (element[3]==null?"":(element[3]))+ "</td>"
						+ "<td>"+ (element[33]==null?"":(element[33]))+ "</td>"

						/* + "<td>"+ (element[2]==null?"":(element[2]))+ "</td>" */		
/* 						+ "<td>"+ (element[30]==null?"":(element[30]))+ "</td>"
 */					+ "<td>"+ (element[29]==null?"":(element[29]))+ "</td>"
					+ "<td>"+ (element[23]==null?"":(element[23]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[24]==null?"":(element[24]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[22]==null?"":(element[22]).toFixed(3))+ "</td>"
					+"<td>"+ (element[6]==null?"":(element[6]).toFixed(3)) +"</td>"
					+"<td>"+ (element[7]==null?"":(element[7]).toFixed(3))+"</td>"
					+ "<td>"+(element[8]==null?"":(element[8]).toFixed(3))+ "</td>"
					+ "<td>"+(element[9]==null?"":(element[9]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[10]==null?"":(element[10]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[11]==null?"":(element[11]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[25]==null?"":(element[25]))+ "</td>"
					+ "<td>"+ (element[26]==null?"":(element[26]))+ "</td>"
					+ "<td>"+ (element[27]==null?"":(element[27])) +"</td>"
		    		+ "<td>"+ (element[28]==null?"":(element[28]))+ "</td>"
					+ "<td>"+ (element[12]==null?"":(element[12]))+ "</td>"
					+ "<td>"+ (element[13]==null?"":(element[13]))+ "</td>"
					+ "<td>"+ (element[14]==null?"":(element[14]))+ "</td>"
					+ "<td>"+ (element[15]==null?"":(element[15]))+ "</td>"
					+ "<td>"+ (element[16]==null?"":(element[16]))+ "</td>"
					+ "<td>"+ (element[19]==null?"":(element[19]))+ "</td>"
					+ "<td>"+ (element[18]==null?"":(element[18]))+ "</td>"
					+ "<td>"+ (element[17]==null?"":(element[17]))+ "</td>"
					
				
				}
				$('#sample').dataTable().fnClearTable();
				$('#updateMaster').html(html1);
				loadSearchAndFilter('sample');
				
			} else {
				$('#sample').dataTable().fnClearTable();
				$('#updateMaster').html(html1);
				bootbox.alert("No Data Available");
			}

		},
		complete: function()
		{  
			loadSearchAndFilter('sample'); 
		} 
	});

}
</script>
<script>
function exportPDF()
{
	var zone=$('#LFzone').val();
	var circle=$('#LFcircle').val();
	var town=$('#LFtown').val();
	var month=$('#month').val();
	var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
	
	var zne="",cir="",townn="",townname1="";
	//alert(zone)
	if(zone=="%"){
		zne="ALL";
	}else{
	    zne=zone;
	}
	if(circle=="%"){
		cir="ALL";
	}else{
	    cir=circle;
	}
	/* if(division=="%"){
		div="ALL";
	}else{
		div=division;
	}
	if(subdiv=="%"){
		subdvn="ALL";
	}else{
		subdvn=subdiv;
	} */
	if(town=="%"){
		townn="ALL";
	}else{
		townn=town;
	}

	/* if(townname=="%"){
		townname1="ALL";
	}else{
		townname1=townname;
	} */
	//alert(zne+cir+townn)
	window.location.href=("./DThealthreportPDF?zne="+zne+"&cir="+cir+"&townn="+townn+"&month="+month+"&townname="+townname);
	
	//window.location.href=("./DTLoadingSummPDF?cir="+cir+"&townn="+townn+"&zone1="+zone1+"&month="+month);
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
</script>


