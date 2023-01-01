<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		    
		App.init();
		TableManaged.init();
		FormComponents.init();
		loadSearchAndFilter('sample_1');
		 $('#reportsId,#feederHealthRpt').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
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
                     html+="<select id='LFcircle' name='LFcircle' onchange= 'showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
                             html += "<select id='division' name='division' onchange= showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
      /* function showSubdivByDiv(division) {
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
         } */


      function showTownNameandCode(circle) {
    	      var zone = $('#LFzone').val();
    	     // var circle = $('#circle').val();
    	     //var division = $('#division').val();
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
						<i class="fa fa-edit"></i>Feeder Health Report
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
									name="circle" onchange="showCircle(this.value);">
										<option value=""></option>
										<option value="%">All</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange= "showTownBySubdiv(this.value);">
									<option id="getCircles" value=""></option>
										<option value=""></option>
								</select></th>
								<!-- <th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">

								</select></th></tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode" onchange="showTownBySubdiv(this.value);"></select> -->
									
												<th class="block">Town&nbsp;:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th>
									</tr>
									<tr>
									<th class="block">Report Month </th>
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
							</td>
							
									
									<td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value="ALL">ALL</option>
											<c:forEach items="${subdivlist}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>
									
							
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table> --%>
						
						<div class="col-md-3">
								<strong>Report Month :</strong>
												<div id="monthId" class="form-group"><input type="text" class="form-control from"  name="month" id="month"  
										 placeholder="Select Report Month" style="cursor: pointer"></div>
									</div>
									
						<div class="col-md-2">
						<button type="submit" onclick="return getReport();" style="margin-top: 15px;" class="btn green">View</button></div>
									
						<p>&nbsp;</p>
					</div>
				
							<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Health Report')">Export
										to Excel</a></li>
										<li><a href="#" id="pdfExport"
									onclick="exportPDF()">Export
										to PDF</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-bordered table-hover" id="sample_1">
						<thead>
							<tr>
							        <th>Sl No</th>
									<th>SubStation</th>
									<th>Feeder Name</th>
									<!-- <th>Feeder Id</th> -->
									<th>Feeder TpId </th>
									<th>Meter number</th>
									<th>Mf</th>
									<th>kWh</th>
									<th>kVah</th>
									<th>Power Factor</th>
									<th>kVA</th>
									<th>kW</th>
									<th>kVAr</th>
									<th>R-Phase Current</th>
									<th>Y-Phase Current</th>
									<th>B-Phase Current</th>
									<th>Date & Time</th>
									<th>Max kVAr</th>
									<th>Min kVAr</th>
									<th>Load Factor</th>
									<th>Interruptions</th>
									<th>Total Hours</th>
									<th>Power Off Hours</th>
									<th>Power On Hours</th>
									<th>Availability</th>
									<th>Max kVA</th>
									<th>Min kVA</th>
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
var days = function(month,year) {
    return new Date(year, month, 0).getDate();
 };
function check(){
	var a='24 Day 14 Hour 58 Min 0 Sec';
}
function getReport() {
	//var subdiv = $('#sdoCode').val();
	//var rdngmnth=$('#reportFromDate').val();
     var tot='';
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	//var division = $('#division').val();
	//var subdiv = $('#sdoCode').val();
	var town =$("#LFtown").val();
	var rdngmnth=$('#month').val();
	var month=rdngmnth.substring(4,6);
	var year=rdngmnth.substring(0,4);
	var tdays=days(month,year);
	var totalhours=tdays*24
	
	if(zone=="")
	{
	bootbox.alert("Please Enter Region");
	return false;
	}
	if(circle=="" || circle== null)
	{
	bootbox.alert("Please Enter circle");
	return false;
	}
	/* if(division=="")
	{
	bootbox.alert("Please Enter division");
	return false;
	}
	if(subdiv=="")
	{
	bootbox.alert("Please Enter sub division");
	return false;
	} */
	if(town=="")
	{
	bootbox.alert("Please Enter town");
	return false;
	}
	if(rdngmnth=="")
	{
	bootbox.alert("Please Enter Report Month");
	return false;
	}
	
	
	$('#updateMaster').empty();
	$('#sample_1').dataTable().fnClearTable();
	$('#imageee').show();
	$.ajax({
		url : './getfeederHealthData',
		type : 'GET',
		data : {
			zone : zone,
			circle : circle,
			rdngmnth : rdngmnth,
			town : town
			
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			if (response.length != 0) {
				//$('#sample').dataTable().fnClearTable();
				$("#updateMaster").html('');
				var html = '';
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
                     if(element[20]!=null){
					var powerOffdur=element[20];
					//var a=powerOffdur.getHours();
					//var a=powerOffdur.split(":");


					var time =powerOffdur;
					const [day, hour, min, sec] = time.match(/\d+/g);
					var dh=day*24;
					var hh=hour;
					var miinh=min/60;
					tot=Number(dh)+Number(hh)+Number(miinh);				
					var powerOnDuration=(totalhours-tot).toFixed(2);
                     }
				    var availability=(powerOnDuration/totalhours)*100;
					html += "<tr>" 
						+"<td>"+(i+1)+"</td>"
					+"<td>"+ (element[0]==null?"":(element[0]))+"</td>"
					+ "<td>"+ (element[1]==null?"":(element[1]))+ "</td>"
					/* + "<td>"+ (element[5]==null?"":(element[5]))+ "</td>" */
					+ "<td>"+ (element[6]==null?"":(element[6]))+ "</td>"
					+ "<td>"+ (element[8]==null?"":(element[8]))+ "</td>"
					+ "<td>"+ (element[2]==null?"":(element[2]))+ "</td>"
					+ "<td>"+ (element[9]==null?"":(element[9]))+ "</td>"
					+ "<td>"+ (element[10]==null?"":(element[10]))+ "</td>"
					+ "<td>"+ (element[11]==null?"":(element[11]))+ "</td>"
					+"<td>"+ (element[12]==null?"":(element[12]).toFixed(3)) +"</td>"
					+"<td>"+ (element[14]==null?"":(element[14]).toFixed(3)) +"</td>"
					+"<td>"+ (element[15]==null?"":(element[15]).toFixed(3))+"</td>"
					+ "<td>"+(element[16]==null?"":(element[16]).toFixed(3))+ "</td>"
					+ "<td>"+(element[17]==null?"":(element[17]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[18]==null?"":(element[18]).toFixed(3))+ "</td>"
/* 					+ "<td>"+(element[13]!=null?"":moment(element[13]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"
 */					+ "<td>"+(element[13]==null?"":moment(element[13]).format('DD-MM-YYYY HH:mm:ss'))+ "</td>"
					+ "<td>"+ (element[23]==null?"":(element[23]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[24]==null?"":(element[24]).toFixed(3))+ "</td>"
					+ "<td>"+ (element[19]==null?"":(element[19]))+ "</td>"
					+ "<td>"+ (element[21]==null?"":(element[21])) +"</td>"
		    		+ "<td>"+ totalhours+ "</td>"
					+ "<td>"+ (element[20]==null?"":tot.toFixed(2))+ "</td>"
					+ "<td>"+  (element[20]==null?"":powerOnDuration)+ "</td>"
					+ "<td>"+ (element[20]==null?"":parseFloat(availability).toFixed(2))+ "</td>"
					+"<td>"+ (element[25]==null?"":(element[25]).toFixed(3)) +"</td>"
					+"<td>"+ (element[26]==null?"":(element[26]).toFixed(3)) +"</td>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				bootbox.alert("No Data Available");
			}

		},
		complete: function()
		{  
			loadSearchAndFilter('sample'); 
		}
	});

}
function exportPDF()
{
	var zone = $('#LFzone').val();
	var circle = $('#LFcircle').val();
	var town =$("#LFtown").val();
	var rdngmnth=$('#month').val();
	var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
	
var zne="",cir="",townn="";
	
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
	window.location.href=("./viewfeederhealthpdf?zne="+zne+"&cir="+cir+"&townn="+townn+"&rdngmnth="+rdngmnth+"&townname="+townname);
}
</script>
<script>


var date=new Date();
var year=date.getFullYear();
var month=date.getMonth();

$('.from').datepicker
({
    format: "yyyymm",
    minViewMode: 1,
    autoclose: true,
    startDate :new Date(new Date().getFullYear()),
    endDate: new Date(year, month-1, '30')
});
</script>


