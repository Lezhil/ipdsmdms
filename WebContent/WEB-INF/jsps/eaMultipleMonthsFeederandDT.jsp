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


<html>
<body>
<script  type="text/javascript">
var jasperUrl ='<%= jasperUrl %>';
	var pageNumber = 1;
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
	   	    	  //loadSearchAndFilter('sample_3');  
	   	    	   FormComponents.init();
	   	    	  UIDatepaginator.init(); 
	   	    	 
	   	    	
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#eaId,#energyAccountingMultiple').addClass('start active ,selected');
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
      		html+="<select id='circle' name='circle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
  			for( var i=0;i<response.length;i++)
  			{
  				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
  			}
  			html+="</select><span></span>";
  			$("#circleTd").html(html);
  			$('#circle').select2();
      	}
  	});
  }
  
	
	function showTownNameandCode(circle){
	var zone =  $('#zone').val(); 
	
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
                html += "<select id='town' name='town'  class='form-control  input-medium' onchange='showfeederCodeandName(this.value)' type='text'><option value=''>Select</option>";
                for (var i = 0; i < response1.length; i++) {
                    //var response=response1[i];
                    
                    html += "<option value='"+response1[i][0]+"'>"
                    +response1[i][0] +"-"  +response1[i][1] + "</option>";
                }
                html += "</select><span></span>";
                $("#townTd").html(html);
                $('#town').select2();
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
	
  function showfeederCodeandName(townCode){
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
	  			 var html = '';
	                html += "<select id='feederTpId' name='feederTpId' onchange='showDTCodeandName(this.value)'  class='form-control input-medium'  type='text'><option value=''>Select</option>";
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
  
  function showDTCodeandName(feederCode){
	   $.ajax({
	      	url:'./getDTtpcodeAndName',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	      		feederCode:feederCode
	  		},
	  		success : function(response1) {
	  			 var html = '';
	                html += "<select id='dtIdName' name='dtIdName'  class='form-control input-medium'  type='text'><option value=''>Select</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            + response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#dtDivId").html(html);
	                $('#dtIdName').select2();
	                 
          }
	  	});
	  }
 
 
 
 
  function hidedtDivId(locType){
	  
	
		if(locType == 'feederLoc'){
			$("#dtDivIdOp").hide();	

			$("#changeName").html("Feeder Name");
			$("#changeTPName").html("Feeder CODE");
		}else{
			$("#dtDivIdOp").show();
			$("#changeName").html("DT Name");
			$("#changeTPName").html("DT TP CODE");
		}
  }
   
  </script>
  
  

	
	
	<div class="page-content"   >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Feeder & DT Multiple Month AT&C Loss </div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

						
				<div class="portlet-body ">
					
			
			<table >
			   <tbody>
					<tr>								
					 		<div class="row" style="margin-left: -1px;">
								<div class="col-md-3">
										<strong>Region:</strong><div id="zoneTd" class="form-group">
											<select class="form-control select2me"
												id="zone" name="zone"
												onchange="showCircle(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option> 
												<c:forEach items="${zoneList}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<strong>Circle:</strong><div id="circleTd" class="form-group">
											 <select class="form-control select2me"
												id="circle" name="circle"
												onchange="showTownNameandCode(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option> 
												
											</select>
										</div>
									</div>
								
								<div class="col-md-3">
								<strong>Town:</strong>
												<div id="townTd" class="form-group"><select
													class="form-control select2me input-medium" id="town"  onchange="showfeederCodeandName(this.value);"
													name="town">
													<option value="">Select</option>
												
												</select></div>
									</div>
									
									
									
									<div class="col-md-3">
								<strong>Location Type:</strong>
												<div id="locationTypeDivId" class="form-group"><select
													class="form-control select2me input-medium" id="locationType" onchange="hidedtDivId(this.value);" 
													name="town">
													<option value="feederLoc">Feeder</option>
													<option value="dtLoc">DT</option>
												
												</select></div>
									</div>
							</div>
						</tr> 
									
							<tr>
								<div class="row" style="margin-left: -1px;">
									<div class="col-md-3">
									<strong>Select Feeder:</strong>
										<div id="feederDivId" class="form-group"><select
													class="form-control select2me input-medium" id="feederTpId" onchange="showDTCodeandName(this.value);"
													name="town">
											</select></div>
									</div>
									
								<div class="col-md-3" hidden="true" id= "dtDivIdOp">
									<strong>Select DT:</strong>
										<div id="dtDivId" class="form-group"><select
													class="form-control select2me input-medium" id="dtIdName" 
													name="dtIdName">
											</select></div>
									</div>
								
								</div>
							</tr>
						
								<tr>
								<div class="row" style="margin-left: -1px;">
								
									<div class="col-md-4"><b>Month Range&nbsp;Selection:</b>

										<div
											class="input-group input-large date-picker input-daterange "
											data-date-format="yyyy-mm-dd">
											<span class="input-group-addon">From</span>
											<input type="text" autocomplete="off" placeholder="From Month"
												class="datepicker form-control"
												<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="myDate"/>
												value="<fmt:formatDate value="${myDate}" pattern="yyyy-mm-dd" />"
												data-date="${myDate}" data-date-format="yyyy-mm-dd"
												data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">To</span> <input type="text"
												autocomplete="off" placeholder="To Month"
												class="datepicker form-control"
												<fmt:parseDate value="${currentDate}" pattern="yyyy-MM-dd" var="mytoDate"/>
												value="<fmt:formatDate value="${mytoDate}" pattern="yyyy-mm-dd" />"
												data-date="${mytoDate}" data-date-format="yyyy-mm-dd"
												data-date-viewmode="years" name="toDate" id="toDate">
										</div>
									</div>
									
									
									<div class="col-md-1"  >
								<strong>Period: </strong>
												<div id="periodMonthDivId" class="form-group">
												<select class="form-control select2me"
												id="periodMonth" name="periodMonth"
												onchange="showDivision(this.value);">
												<option value="12">12</option>
												<option value="10">10</option>
												<option value="08">8</option> 
												<option value="06">6</option>
												<option value="04">4</option>
												<option value="02">2</option>
												<option value="00">0</option>
											</select></div>
									</div>
									
									<div class="col-md-2"  id= "dtDivIdOp">
									<th>
									<button type="submit" onclick="getMultipleAtandCRecords()" class="btn green">Generate</button>
									</th> 
									</div>
									</div>
									</tr>
								
							
							
					</tbody>
				</table>
				
				
				
				<table class="table table-striped table-hover table-bordered"
				id="sample_3" >
				<thead>
					<tr>
						<th>Sl.No</th>
						<th id="changeName">Feeder Name</th>
						<th id="changeTPName">Feeder_ID</th>
						<th>Month Year</th>
						<th>Billing Efficiency (%)</th>
						<th>Collection Efficiency (%)</th>
						<th>AT&C Loss</th>
						
						

					</tr>
				</thead>
				<tbody id="multiplDataRecords">

				</tbody>
			</table>
				</div>
				
				
				
	
	
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
							style="width: 4%; height: 4%;">
					</div>					
							
		</div>
	</div>
 
  
  
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


function getMultipleAtandCRecords()
{ 
	 var FromMonth=$('#fromDate').val();
	 var ToMonth=$('#toDate').val();
	 var feederTpId=$("#feederTpId").val();
	 
	 var dtIdName=$("#dtIdName").val();
	 var locationType=$("#locationType").val();
	 var periodMonth=$("#periodMonth").val();
	   
	 var region = $("#zone").val();
	 var circle = $("#circle").val();
	 var town = $("#town").val(); 
	 
	 if(region.trim()==''){
	 bootbox.alert('Please select Region');
	 return false;
	 }
	 
	 if(circle.trim()=='')
	 {
	 bootbox.alert('Please select Circle');
	 return false;
	 }
	 
	 if(town.trim()=='')
	 {
	 bootbox.alert('Please select Town');
	 return false;
	 }
	 
	 
	 if(FromMonth.trim()=='')
	 {
	 bootbox.alert('Please select From Month');
	 return false;
	 }
	 
	 if(ToMonth.trim()=='')
	 {
	 bootbox.alert('Please select To month');
	 return false;
	 }
	 
	 if(locationType.trim()=='')
	 {
	 bootbox.alert('Please select location type');
	 return false;
	 }
	 
	 if(feederTpId.trim()=='')
	 {
	 bootbox.alert('Please select Feeder');
	 return false;
	 }
	
	 
	 if(locationType.trim() == 'dtLoc'){
		 if(dtIdName.trim()=='')
		 {
		 bootbox.alert('Please Select DT');
		 return false;
		 }	 
	 }
		
	 if(periodMonth.trim()=='')
	 {
	 bootbox.alert('Please Select Period');
	 return false;
	 }
	  $.ajax({

			url:"./getMultipleMonthatandcLossData",
			 type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					FromMonth  : FromMonth,
					ToMonth : ToMonth,
					feederTpId : feederTpId,
					dtIdName : dtIdName,
					locationType : locationType,
					periodMonth : periodMonth
					},
				success : function(response) {
					if(response.length == 0){
						bootbox.alert('No data found for the selected inputs.');
					}  
				var html1="";
				for(var i=0;i<response.length;i++)
				{
	               var resp=response[i];
	               html1 += "<tr>"
	            	   + "<td>"+(i+1)+"</td>"
						+ "<td>"+(resp[0]==null?"":resp[1]) +"</td>"
						+ "<td>"+(resp[1]==null?"":resp[2]) +"</td>"
						+ "<td>"+ (resp[2]==null?"":resp[0]) +"</td>"
						+ "<td>"+ (resp[3]==null?"":resp[3])+"</td>"
						+ "<td>"+ (resp[4]==null?"":resp[4])+"</td>"
						+ "<td>"+ (resp[7]==null?"":resp[7])+"</td>"
						+ "</tr>";
				}
			$('#sample_3').dataTable().fnClearTable();
				$("#multiplDataRecords").html(html1);
				loadSearchAndFilter1('sample_3');
			},
		
		complete : function() {
			loadSearchAndFilter('sample_3');
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
    endDate: new Date(year, month, '31')

});
</script>
</body>

</html>
 
