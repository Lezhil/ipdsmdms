<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<%@ page import = "java.util.ResourceBundle" %>
<% 
  ResourceBundle jasperResource = ResourceBundle.getBundle("application");
  String jasperUrl=jasperResource.getString("JasperServerUrl");
 %> 
<%


String newRegionName = (String) request.getSession().getAttribute("newRegionName");
String officeName = (String) request.getSession().getAttribute("officeName");
String officeType = (String) request.getSession().getAttribute("officeType");
String editRights = (String) request.getSession().getAttribute("editRights");
String viewRights = (String) request.getSession().getAttribute("viewRights");
System.out.println("Edit rights" +editRights);
System.out.println("View rights" +viewRights);
%>



<html>
<body>
<script  type="text/javascript">
var jasperUrl ='<%= jasperUrl %>'
  $(".page-content").ready(function(){
	   var editRight = '${editRights}';
	   var viewRight = '${viewRights}';
	   
	   var officeName = '${officeName}';
	   var officeType = '${officeType}';
	   var newRegionName = '${newRegionName}';

	  
	  $('.datepicker').datepicker({
          format: 'yyyymm',
          autoclose:true,
          viewMode: 'months',
          minViewMode: "months",
          endDate: '-1m',
          

      }).on('changeDate', function (ev) {
              $(this).datepicker('hide');
          });


      $('.datepicker').keyup(function () {
          if (this.value.match(/[^0-9]/g)) {
              this.value = this.value.replace(/[^0-9^-]/g, '');
          }
      });
	  
	  
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  //loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	 
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#eaId,#energyAccountingTownLevel').addClass('start active ,selected');
	    	 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	
	    
		if(officeType == 'circle'){
		var html='';
  		html+="<select id='CLzone' name='CLzone' class='form-control'  type='text'>";
		html+="<option  value='"+newRegionName+"' selected>"+newRegionName+"</option>";
		html+="</select><span></span>";
		$("#CLzone").html(html);
		
		var html='';
  		html+="<select id='LFcircle' name='LFcircle' class='form-control'  type='text'>";
		html+="<option  value='"+officeName+"' selected>"+officeName+"</option>";
		html+="</select><span></span>";
		$("#LFcircle").html(html);
		
		showTownNameandCodeCircleLogin(officeName);
		}	
		
		
	    	 //alarmData('all');
   	    	   });
 
   
  function mtrDetails(mtrNo)
  { 
  	 window.location.href="./mtrNoDetailsMDAS?mtrno=" + mtrNo;
  }
  

  function handleClick(param){
	 if(param  == 'periodic') {
		$("#toDate").show();
		 $("#todatelabel").show();
		 $("#fromDateMonthly").hide();
		 $("#fromDatePeriodic").show();
	 }else {
		 
		 $("#toDate").hide();
		 $("#todatelabel").hide();
		 $("#fromDateMonthly").show();
		 $("#fromDatePeriodic").hide();
	 }
	  
	 
	  
  }

 
  function clearTabledataContent(tableid)
	{
		 //TO CLEAR THE TABLE DATA
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
	}
/*   var zone="%"
  function showCircle(zone)
	{
		var zone="All";
		$.ajax({
			url : './showCircleMDM' + '/' + zone ,
		    	type:'GET',
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<option value=''></option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					
					$("#circle").html(html);
					$('#circle').select2();
		    	}
			});
	} */
	
  
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
      		html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
  			for( var i=0;i<response.length;i++)
  			{
  				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
  			}
  			html+="</select><span></span>";
  			$("#LFcircleTd").html(html);
  			$('#LFcircle').select2();
      	}
  	});
  }
  
	
	function showTownNameandCode(circle){
	var zone =  $('#LFzone').val(); 
	
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
                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
               
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

	
	
	
	function showTownNameandCodeCircleLogin(circle){
		var zone = '${newRegionName}';  
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
	                
	                html += "<select id='Circletown' name='Circletown'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                 html += "<option value='"+response1[i][0]+"'>"
	                                    +response1[i][0] +"-"  +response1[i][1] + "</option>";        
	                            
	                }
	                html += "</select><span></span>";
	                $("#CircletownTd").html(html);
	                $('#Circletown').select2();
	                 
	                
	                
	            }
		  	});
		  }

  function showTownBySubdiv(subdiv) {
	     // var zone = $('#zone').val();
	      var circle = $('#circle').val();
	      var division = $('#division').val();
	      $.ajax({
	          url : './getTownNameandCodeBySubDiv',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	        	  sitecode :subdiv
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='town' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  + response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	              });
	  }
	
	
  </script>
  
	<div class="page-content" >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Town Level- Energy Accounting </div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

						
				<div class="portlet-body ">
					
			
		<jsp:include page="locationFilter.jsp"/> 
				<div class="row" style="margin-left: -1px;">
				
							
						
						 		
						 		
							<div class="col-md-2">
								<strong>Report Month :</strong>
												<div id="feederDivId" class="form-group"><input type="text" class="form-control from"  name="selectedDateName" id="selectedFromDateId"  
										 placeholder="Select Report Month" style="cursor: pointer"></div>
									</div>
									
									
									
									<div class="col-md-2">
								<strong>Select Period :</strong>
												<div id="fromDateMonthly" class="form-group"><select class="form-control select2me"
												id="periodMonth" name="periodMonth">
												
												<option value="12">12</option>
												<option value="10">10</option>
												<option value="08">8</option> 
												<option value="06">6</option>
												<option value="04">4</option>
												<option value="02">2</option>
												<option value="00">0</option>
												
											</select></div>
									</div>
									
									<div class="col-md-2">
									<button type="submit" onclick="view()" style="margin-top: 15px;" class="btn green">Generate</button></div>
						</div>
							
				<!-- 		<div id="loading" style="text-align: center;">
		<h3 id="loadingText">Loading..... Please wait</h3>
		<img alt="image" src="./resources/images/loading.gif"
			style="width: 3%; height: 3%;">
	</div> -->
	
	<div class="contain" id="box" style="display: none;">
		<div class="row well">
			<button id="previousPage" style="margin-left: 18%;"
				onclick="privious()" >prev</button>
			<input id="currentPageNumber" value="1"
				style="width: 30px; text-align: center;   readonly="readonly" />
			<button id="nextPage" onclick="next()">next</button>
			<select id="exporttype" style="margin-left: 48%;">
				<option value="PDF">pdf</option>
				<!-- <option value="XLSX">xlsx</option>
				<option value="XLS">xls</option> -->
				<!-- <option value="RTF">rtf</option>
				<option value="CSV">csv</option>
				<option value="XML">xml</option>
				<option value="DOCX">docx</option>
				<option value="PPTX">pptx</option> -->
			</select> <a id="download" onclick="exportReport()">Export</a>

			<div id="container" style="margin-top: 15px;"></div>

		</div>
	</div>
	<div id="sld_Image" style="margin-top: 15px;" hidden="true">
	<img id="sld_Image_path" src="http://1.23.144.187:8102/downloads/sldreport/014.jpg" alt="SLD_IMAGE Not available" style="width:750px">
	</div>
		<div id="imageee" hidden="true" style="text-align: center;">
			<h3 id="loadingText">Loading..... Please wait.</h3>
			<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
			style="width: 4%; height: 4%;">
	</div>				
							
		</div>
	</div>
 </div>
	
<script>
<%--  var des= {desc1:"<%= message %>"};
 var sentences=des['desc1'].match(/[^\.\!\?]+[\.\!\?]+/g)
 .map(function(s){
   return s.replace(/^\s+|\s+$/g/'.');
 }); --%>

	<%-- var role = '${schemaName1}';
	var schemaname = '${schemaName}';
	var pageNumber = 1;
	var jasperUrl ='<%= jasperUrl %>'  
	var newurl = "";
	
 --%>

	

	

	

	function checkReportList() {
		resetPageNumber();
		if (role == 'CORPORATE' || role == 'CIRCLE') {
				 if ($("#jrReport").val() == '/JVVNL/MIS_Reports/Monthly_Reports/Defective_meters_other_than_AG'){
					 $("#circle").hide();
						$("#division").hide();
						$("#subdiv").hide();
						$("#month").show();
						$("#year").show();
						$("#feeder").hide();
					 }else{
						  $("#circle").show();
						  $("#division").show();
						  $("#subdiv").show();
						  $("#month").show();
						  $("#year").show();
						   $("#feeder").show();
						 }
			
		}
		else if (role == 'DIVISION') {
			$("#circle").show();
			$("#division").show();
			$("#subdiv").show();
			$("#month").show();
			$("#year").show();
			$("#feeder").show();
		}
		else {
			$("#month").show();
			$("#year").show();
			$("#feeder").show();
		}
	}

	function getJasperReport () {
		if ($('#jrReport').val() == "" || $('#jrReport').val() == "select") {
			bootbox.alert("Please select Report");
			return false;
		}
		$('#loading').show();
		if (role == 'CORPORATE' || role == 'CIRCLE') {
			var reportname = $('#jrReport').val();
			var subdivision =$('#jrSubDiv :selected').text();
			var month = $('#jrMonth').val();
			var year = +$('#jrYear').val();
			var circle =$('#jrCircle').val();
			var division = $('#jrDiv').val();
			var feedername = $('#jrFeeder :selected').text();
			generateMonthlyReport (reportname,year,month,circle,division,subdivision,feedername,pageNumber);
			 newurl = jasperUrl+'downloadmonthlyreport?year='+year+'&month='+month+'&circle='+circle+'&division='+division+'&subdivision='+subdivision+'&reportname='+reportname+'&feedername='+feedername+'&exporttype=';
		} else if (role == 'DIVISION') {
 
			 var reportname = $('#jrReport').val();
				var subdivision =$('#jrSubDiv :selected').text();
				var month = $('#jrMonth').val();
				var year = +$('#jrYear').val();
				var circle =$('#jrCircle').val();
				var division = $('#jrDiv').val();
				var feedername = $('#jrFeeder :selected').text();
				generateMonthlyReport(reportname,year,month,circle,division,subdivision,feedername,pageNumber);
				newurl = jasperUrl+'downloadmonthlyreport?year='+year+'&month='+month+'&circle='+circle+'&division='+division+'&subdivision='+subdivision+'&reportname='+reportname+'&feedername='+feedername+'&exporttype=';
		} else {

			var subdivision = '${subdivision}';
			var division = '${division}';
			var circle = '${circle}';
			var reportname = $('#jrReport').val();
			var subdiv = '${schemaName}';
			var month = $('#jrMonth').val();
			var year = +$('#jrYear').val();
			var feedername = $('#jrFeeder :selected').text();//$('#jrFeeder').val();
			var tariff = $('#jrTariff').val();
			var newString  =circle[0];
			for(var i =1; i< circle.length; i++){
			         newString+= circle[i].toLowerCase();
			}
			generateMonthlyReport(reportname, year, month, newString, division,subdivision, feedername, pageNumber);
			newurl = jasperUrl+'downloadmonthlyreport?year='+year+'&month='+month+'&circle='+newString+'&division='+division+'&subdivision='+subdivision+'&reportname='+reportname+'&feedername='+feedername+'&exporttype=';
		}
	}


	function generateMonthlyReport(reportname, year, month, circle, division,subdivision, feedername, pageNumber){
		$('#currentPageNumber').val(pageNumber);
		 $('#loading').show();
		 $.ajax({
		        url: jasperUrl+"getJasperMonthlyReport",
		        type: 'GET', 
		        data:{reportname:reportname,year:year,month:month,circle:circle,division:division,subdivision:subdivision,feedername:feedername,pageNumber:pageNumber},  
		        success: function(data) {
			            if(data==='EoR'){
			            	// $('#currentPageNumber').val(--pageNumber)
			            	$('#currentPageNumber').val(pageNumber)
			            	swal({
								title : "No data for selected criteria",
								text : "",
								confirmButtonColor : "#2196F3",
							});
			            }else{
				            	$('#box').css('display','');            
				           	 	$('#container').empty();
				           	  	$('#container').append(data);   
				           	  	
			                }
			            $('#loading').hide();
		            },
		    		error : function(xhr, textStatus, errorThrown) {
		    			swal({
							title : "Oops! An error has occurred in fetching report Kindly contact administrator or try after some time",
							text : "",
							confirmButtonColor : "#2196F3",
						});
					}
		    }); 
		}

	
	
	
	
	
	function exportReport() {
	/* 	var jasperUrl = "http://localhost:7272/JasperFilter/" */
		var reportName = "TNEB/EA_TOWN_WISE_REPORT_DOWNLOAD";
		var town=$("#LFtown").val();
		var monthyear = $("#selectedFromDateId").val(); 
		var periodMonth=$("#periodMonth").val();
		var exporttype=$("#exporttype").val();
		/* var newurl = jasperUrl+'downloadjasperreportEATownWise?monthyear='+monthyear+'&reportName='+reportName+'&periodMonth='+periodMonth+'&town='+town+'&exporttype='; */
		/* location.href=newurl+$('#exporttype').val(); */
		window.open("./downLoadEaReportsTownWise?reportName="+reportName+"&town="+town+"&monthyear="+monthyear+"&periodMonth="+periodMonth+"&exporttype="+exporttype);
		/* window.location.href=("./tryDownloadNew"); */
		/* window.open("./exportjsonSaifiSaidiFeeder?monthyr=" + monthyr + "&townfeeder=" + townfeeder + "&town=" + town + "&feeder=" + feeder); */

	}
	
	
	function next() {
		$('#currentPageNumber')
				.val(parseInt($('#currentPageNumber').val()) + 1);
		pageNumber = $('#currentPageNumber').val();
		getJasperReport();

	}

	function privious() {
		if ($('#currentPageNumber').val() > 1) {
			$('#currentPageNumber').val(
					parseInt($('#currentPageNumber').val()) - 1)
			pageNumber = $('#currentPageNumber').val();
			getJasperReport();
		}

	}

	function resetPageNumber(){
		pageNumber = 1;
		// $('#currentPageNumber').val(pageNumber);
		}
</script>



  
  
  <script>
function submitDataLoadExcel()
{
var metrno=$("#meterVal").val();

if(metrno.trim()=='')
{
bootbox.alert('Please enter Meter number');
return false;
}

var Billmonth =$("#selectedDateId").val();

//alert("metrno--"+metrno+"--Billmonth-"+Billmonth);

window.location.href="./downloadLOadSurveyExcel?metrno="+metrno+"&Billmonth="+Billmonth;

/*  /downloadLOadSurveyExcel*/
}
function validation()
{
	var zone=$("#zone").val();
	
	var fdate=$("#selectedFromDateId").val();
	var tdate=$("#selectedToDateId").val();
 if(zone=="")
	{
	bootbox.alert("Please Select Zone");
	return false;
	}  
	
	if(fdate=="")
		{
			bootbox.alert("Please Select  From Date");
			return false;
		}
	if(tdate=="")
	{
		bootbox.alert("Please Select  To Date");
		return false;
	}
	if(new Date(fdate)>new Date(tdate))
	{
		bootbox.alert("Wrong Date Inputs");
		return false;
	}

}
/* function view(){
	var town=$("#town").val();
	var fromdate = $("#selectedFromDateId").val(); 
	var subdivision  = $("#sdoCode").val(); 
		
		
	if(town == ''){
		bootbox.alert("Please Select  Town");
		return false;
	
	}
		$.ajax({
			url:"./getEneryAccoutingDataTownWise",
			type:"get",
			data:{
				fromdate:fromdate,
				town:town,
				subdivision:subdivision
				},
		success:function(res){
			if(res.length == '0'){
				bootbox.alert('No Data found for selected inputs');
			}else{
			html="";
			$.each(res,function(i,v){
			
				 $("#atAndCloss").val(v[15]);
				 $("#billingEffec").val(v[8]);
				 $("#technicalLoss").val(v[13]);
				 $("#technicalLossPercent").val(v[14]);
				 $("#collectEffec").val(v[9]);
				 $("#townNameMain").val(v[2]);
				
			});
			townWiseFeederData(fromdate, town);
			//$("#listID").empty().
			
			}
		}
	});
	
} */


function view(){
 	$('#container').empty();
 	
var zone=$("#LFzone").val();
var circle=$("#LFcircle").val();
var town=$("#LFtown").val();
var fromdate = $("#selectedFromDateId").val(); 
var reportName = "/TNEB/EA_TOWN_WISE_REPORT";
var periodMonth = $("#periodMonth").val(); 


if(zone == ''){
	bootbox.alert("Please Select  Region");
	return false;

}

if(circle == ''){
	bootbox.alert("Please Select  Circle");
	return false;

}

if(town == ''){
	bootbox.alert("Please Select  Town");
	return false;

}
if(fromdate == ''){
	bootbox.alert("Please Select  Bill Month");
	return false;

}
 $("#imageee").show();
	$.ajax({
		url:"./getTownWiseEAjasperReport",
			type: 'GET', 
		data:{
			monthyear:fromdate,
			town:town,
			reportName:reportName,
			periodMonth:periodMonth
			},
			 success: function(data) {
		            if(data==='EoR'){
		            	$("#imageee").hide();
		            	// $('#currentPageNumber').val(--pageNumber)
		            	$('#currentPageNumber').val(pageNumber)
		            	bootbox.alert("No Data found for selected inputs")
		            }else{
		            	$.ajax({
		            		url:"./getTownSLDimagePath",
		            		type:"get",
		            		data:{
		            			town:town
		            			
		            		},
		            		success:function(res){
		            			if(res == "noImage"){
		            				bootbox.alert("No SLD Image available for this town");
		            			}
		            	document.getElementById("sld_Image_path").src = res;
		            		}
		            	});
            	    	$("#imageee").hide();
		            	    	$("#sld_Image").show();
			            	$('#box').css('display','');            
			           	 	$('#container').empty();
			           	  	$('#container').append(data);   
			           	  	
		                }
		            
	            }
});

}

/* function view(){
	 	$('#container').empty();

	var town=$("#town").val();
	var fromdate = $("#selectedFromDateId").val(); 
	var reportName = "/TNEB/EA_TOWN_WISE_REPORT";
	if(town == ''){
		bootbox.alert("Please Select  Town");
		return false;
	
	}
	if(fromdate == ''){
		bootbox.alert("Please Select  Bill Month");
		return false;
	
	}
	 $("#imageee").show();
		$.ajax({
			url:jasperUrl + "getJasperReportTNEBEA",
			type: 'GET', 
			data:{
				monthyear:fromdate,
				town:town,
				reportName:reportName
				},
				 success: function(data) {
			            if(data==='EoR'){
			            	$("#imageee").hide();
			            	// $('#currentPageNumber').val(--pageNumber)
			            	$('#currentPageNumber').val(pageNumber)
			            	bootbox.alert("No Data found for selected inputs")
			            }else{
			            	$.ajax({
			            		url:"./getTownSLDimagePath",
			            		type:"get",
			            		data:{
			            			town:town
			            			
			            		},
			            		success:function(res){
			            			if(res == "noImage"){
			            				bootbox.alert("No SLD Image available for this town");
			            			}
			            	document.getElementById("sld_Image_path").src = res;
			            		}
			            	});
	            	    	$("#imageee").hide();
			            	    	$("#sld_Image").show();
				            	$('#box').css('display','');            
				           	 	$('#container').empty();
				           	  	$('#container').append(data);   
				           	  	
			                }
			            
		            }
	});
	
} */



function townWiseFeederData(fromdate, townCode){
	
	$.ajax({
		url:"./getEneryAccoutingDataTownWiseFeeder",
		type:"get",
		data:{
			fromdate:fromdate,
			town:townCode
			
		},
		success:function(res){
			if(res.length == '0'){
				bootbox.alert('No data found for selected inputs');
			}else{
				
				
			html="";
			$.each(res,function(i,v){
				if(v[11] == true){
					boundryResult = "Yes";
				}else{
					boundryResult = "No";
				}
				
				html+="<tr><td>"+(i+1)+"</td><td>"+v[7]+"</td><td>"+v[23]+"</td><td>"+v[22]+"</td><td>"+v[21]+"</td><td>"+v[10]+"</td>"+
				"<td>"+v[8]+"</td><td>"+boundryResult+"</td><td>"+v[9]+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[12]+"</td><td>"+v[13]+"</td><td>"+v[14]+"</td><td>"+v[17]+"</td><td>"+v[18]+"</td><td>"+v[20]+"</td></tr>";
			});
			//$("#listID").empty().
			clearTabledataContent('sample_3');
			$("#eventTR").html(html);
			loadSearchAndFilter('sample_3');
			$("#boxid").show();
			}
		}
	});
	
	
	
}



function printData()
{
   var divToPrint=document.getElementById("sample_3");
  
   newWin= window.open("");
   newWin.document.write(divToPrint.outerHTML);
   newWin.print();
   newWin.close();

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
</body>

</html>
 
