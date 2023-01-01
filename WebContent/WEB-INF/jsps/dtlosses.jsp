<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>

<%@ page import="java.util.ResourceBundle"%>
<% 
  ResourceBundle jasperResource = ResourceBundle.getBundle("application");
  String jasperUrl=jasperResource.getString("JasperServerUrl");
 %>


<html>
<body>
	<script type="text/javascript">
var jasperUrl ='<%= jasperUrl %>'
  $(".page-content").ready(function()
   	    	   {    
	  
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
	   	    	  loadSearchAndFilter('sample1');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	 
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   		$('#eaId,#dtlosses').addClass('start active ,selected');
	    	 $("#MDMSideBarContents,#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	
	    	 
	    	
	    
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
                html += "<select id='LFtown' name='LFtown'  class='form-control input-medium' onchange='showResultsbasedOntownCode(this.value)'  type='text'><option value=''>Select</option>";
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

	
	function showResultsbasedOntownCode(townCode){
	   $.ajax({
	      	url:'./getFeederTpandName',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	      		townCode:townCode
	  		},
	  		success : function(response1) {
		  		var responseValueAll ="%";
		  		var responseShowAll ="ALL";
	  			 var html = '';
	                html += "<select id='feederTpId' name='feederTpId'  class='form-control input-medium'  type='text'><option value=''>Select</option>";

	                html += "<option value='"+responseValueAll+"'>"
                    + responseShowAll + "</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            + response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#feederDivId").html(html);
	                $('#feederTpId').select2();
               
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

	<div class="page-content">
		<div class="portlet box blue " id="boxid">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-reorder"></i>DT Losses
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
						<strong>Feeder:</strong>
						<div id="feederDivId" class="form-group">
							<select class="form-control select2me input-medium"
								id="feederTpId" name="feederTpId">
								<option value="">Select Feeder</option>

							</select>
						</div>
					</div>


					<div class="col-md-2">
						<strong>Report Month :</strong>
						<div id="feederDivId" class="form-group">
							<input type="text" class="form-control from"
								name="selectedDateName" id="selectedFromDateId"
								placeholder="Select Report Month" style="cursor: pointer">
						</div>
					</div>



					<div class="col-md-2">
						<strong>Select Period :</strong>
						<div id="fromDateMonthly" class="form-group">
							<select class="form-control select2me" id="periodMonth"
								name="periodMonth">
								<option value="12">12</option>
								<option value="10">10</option>
								<option value="08">8</option>
								<option value="06">6</option>
								<option value="04">4</option>
								<option value="02">2</option>
								<option value="00">0</option>
							</select>
						</div>
					</div>

					<div class="col-md-2">
						<button type="submit" onclick="view()" style="margin-top: 15px;"
							class="btn green">Generate</button>
					</div>
				</div>
				<div id="imageee" hidden="true" style="text-align: center;">
					<h3 id="loadingText">Loading..... Please wait.</h3>
					<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
						style="width: 4%; height: 4%;">
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li id="print"><a href="#">Print</a></li> -->
								<li><a href="#" id=""
									onclick="exportReport('sample1','DT Losses')">Export to PDF</a></li>
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample1', 'DT Losses')">Export to Excel</a>
								</li> 
							</ul>
						</div>

						<table class="table table-striped table-hover table-bordered"
							id="sample1">

							<thead>
								<tr>
									<th>SL NO</th>
									<th>DT NAME</th>
									<th>DT CODE</th>
									<th>METER NUMBER</th>
									<th>TOTAL CONSUMER</th>
									<th>DT INPUT ENERGY - A (kwh)</th>
									<th>UNIT BILLED - B (kwh)</th>
									<th>LOSS - C=(A-B)</th>
									<th>LOSS PERCENTAGE (%)</th>
								</tr>
							</thead>
							<tbody id="getdtlossesbody">
							</tbody>
						</table>


					</div>
				</div>

			</div>

		</div>


	</div>


	<script>
 
 function exportReport() {
		/* 	var jasperUrl = "http://localhost:7272/JasperFilter/" */
		//	var reportName = "TNEB/EA_DT_Report_download";
			var town=$("#LFtown").val();
			var monthyear = $("#selectedFromDateId").val(); 
			var periodMonth=$("#periodMonth").val();
			var zone=$("#LFzone").val();
		 	var circle=$("#LFcircle").val();
		 	var feederTpId=$("#feederTpId").val();
		 	//alert(feederTpId);
		 	if(feederTpId=="%"){
		 		feederTpId="All";
			 	}
			
			window.open("./getdtlossPDF?town="+town+"&monthyear="+monthyear+"&periodMonth="+periodMonth+"&feederTpId="+feederTpId+"&zone="+zone+"&circle="+circle);
			
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



function view(){
 	$('#container').empty();
 	var zone=$("#LFzone").val();
 	var circle=$("#LFcircle").val();

var town=$("#LFtown").val();
var fromdate = $("#selectedFromDateId").val(); 


var feederTpId=$("#feederTpId").val();
var periodMonth = $("#periodMonth").val(); 

var reportName = "/TNEB/EA_DT_Report";

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

if(feederTpId == ''){
	bootbox.alert("Please Select  Feeder");
	return false;

}
if(fromdate == ''){
	bootbox.alert("Please Select  Bill Month");
	return false;

}




if(periodMonth == ''){
	bootbox.alert("Please Select  Period ");
	return false;

}
 $("#imageee").show();
	$.ajax({
		url:"./getdtloss",
		type: 'GET', 
		data:{
			monthyear:fromdate,
			town:town,
			feederTpId:feederTpId,
			periodMonth:periodMonth,
			reportName:reportName,
			zone : zone,
			circle : circle
			
			},
			 success: function(response) {
				// alert(response);
				 $("#imageee").hide();
				 if(response != null && response.length > 0 ){
					 var html = "";
					 for(var i=0; i < response.length; i++){
						 var resp = response[i];
						// alert(resp);
						 var citrus = resp[0].substr(0);
						 var j = i + 1;
						 html += "<tr>"
						 + "<td>"+ j + "</td>"
						 + "<td>" + citrus + "</td>"
						 + "<td>" + ((resp[1] == null) ? "" : resp[1]) + "</td>"
						 + "<td>" + ((resp[2] == null) ? "" : resp[2]) + "</td>"
						 + "<td>" + ((resp[3] == null) ? "" : resp[3]) + "</td>" 
						 + "<td>" + ((resp[4] == null) ? "" : resp[4]) + "</td>" 
						 + "<td>" + ((resp[5] == null) ? "" : resp[5]) + "</td>" 
						 + "<td>" + ((resp[6] == null) ? "" : resp[6]) + "</td>" 
						 + "<td>" + ((resp[7] == null) ? "" : (resp[7]).toFixed(2)) + "</td>" 
						 html +="</tr>";
					}
					 $('#sample1').dataTable().fnClearTable();
						$("#getdtlossesbody").html(html);
						loadSearchAndFilter('sample1');
				 }else{
		            	
            	    	$("#imageee").hide();
            	    	$('#sample1').dataTable().fnClearTable();
						$("#getdtlossesbody").html(html);
            	    	bootbox.alert("No Relative Data Found.");
			           	  	
		                }
		            
	            }
});

}


function printData()
{
   var divToPrint=document.getElementById("sample1");
  
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
    endDate: new Date(year, month, '31')

});
</script>
</body>

</html>

