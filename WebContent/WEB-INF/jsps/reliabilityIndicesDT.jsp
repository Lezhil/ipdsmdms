<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {    
	  
	  $('.datepicker').datepicker({
          format: 'yyyy-mm',
          autoclose:true,
          endDate: '-1d',
          

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
	   	  	 $("#fromDateMonthly").hide();
	   	    	$("#feederDivMultiple").hide();  
	   	    	
	   	    	/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
	   	   	$('#MDMSideBarContents,#reportsId,#reliabilityIndicesDT').addClass('start active ,selected');
	    	 $("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	    	 //alarmData('all');
	    	 showCircle();
   	    	   });
 
	  
  

  
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
  
  var zone="%";
  function showCircle(){
 	 
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
                 html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                 for( var i=0;i<response.length;i++)
                 {
                     html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                 }
					html+="</select><span></span>";
                 $("#circleListTH").html(html);
                 $('#circle').select2();
             }
         });
 }
  
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
                      html += "<select id='divisionId' name='divisionId' onchange='showSubdivByDiv(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
                      for (var i = 0; i < response.length; i++) {
                          html += "<option value='"+response[i]+"'>"
                                  + response[i] + "</option>";
                      }
                      html += "</select><span></span>";
                      $("#divisionTd").html(html);
                      $('#divisionId').select2();
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
                       html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
	      var circle = $('#circle').val();
	      var division = $("#divisionId").val();
	  // var subdiv = $("#subDivisionId").val();
	      //alert(subdiv);
	      //alert(division);
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
	   	                  //alert(response1)
	                      var html = '';
	                      html += "<select id='town' name='town'  onchange='showSSnameBySubdiv(this.value)'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][1]+"'>"
	                                  + response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	              });
	  }

  
  function showSSnameBySubdiv(town)
	{
	
	
	  $.ajax({
        url : './showSSnameBySubdiv',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
            
        	town : town
        },
                success : function(response) {
                    var html = '';
                    html += "<select id='substationId' name='substationId' onchange='showfdrIdNameBySubdivAndSSname(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Select Sub-Station</option><option value='%'>ALL</option>";
                    for (var i = 0; i < response.length; i++) {
                        //var response=response1[i];
                       
                        
                        html += "<option value='"+response[i][0]+"'>"+response[i][1]+"</option>";
                    }
                    html += "</select><span></span>";
                   /* 
                    $("#subdivTd").html(html);
                    $('#sdoCode').select2(); */
                    
                    $("#subStationList").html(html);
					$("#subStationList").html(html);
                   
						
						
                }
            });
	  
	}
		
  
  function showfdrIdNameBySubdivAndSSname(parentid)
	{

		var sdoCode=$('#sdoCode').val();
	  $.ajax({
          url : './showDTNameBySubdivAndSSname',
          type : 'GET',
          dataType : 'json',
          asynch : false,
          cache : false,
          data : {
              
        	  parentid : parentid,
        	  sdoCode : sdoCode,
          },
                  success : function(response) {
                	  if(response == ''){
                		  bootbox.alert('No DT attached to the selected substation');
                		  $('#feederList').val("").trigger("change");
                		  $('#feederList').find('option').remove();
                	  }else{
                      var html = '';
                      html += "<select id='feederList' name='feederList'  class='form-control select2me input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
                      for (var i = 0; i < response.length; i++) {
                          //var response=response1[i];
                          
                          html += "<option value='"+response[i]+"'>"+response[i]+"</option>";
                      }
                      html += "</select><span></span>";
                     /* 
                      $("#subdivTd").html(html);
                      $('#sdoCode').select2(); */
                      
                      $("#feederList").html(html);
					  $("#feederListMultiple").html(html);
                	  }
                  }
              });
	 
	}
		/* $.ajax({
				url : './showDTNameBySubdivAndSSname' + '/' +parentid,
			    	type:'GET',
			    	dataType:'json',
			    	success:function(response)
			    	{
			    	
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
						}
						$("#feederList").html(html);
						$("#feederListMultiple").html(html);
						
			    	}
				}); */
		


  
	
  </script>
  <script>
  
  
  </script>
  
		<div class="page-content" >
		    <div class="portlet box blue "  id="boxid">
			    <div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>Reliability Indices (DT)</div>
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
				
					<tr >
						<th  >Circle&nbsp;:</th>
							<th id="circleListTH">
								<select	class="form-control select2me input-medium" id="circleList"
								name="circle" >
									<option value="">Select</option>
									  
						     	</select>
							</th>
						
						<th class="block" id='division'>Division&nbsp;:</th>
								<th id="divisionTd">
								<select
									class="form-control select2me input-medium" id="divisionlists"
									name="division" >
								</select>
						</th>
					
						<th class="block" id=sdoCodelabel>Sub-Division&nbsp;:</th>
								<th id="subdivTd">
									<select
										class="form-control select2me input-medium" id="subDivisionId" 
										name="subDivisionId"  onchange="showTownBySubdiv(this.value);" >
									</select>
							   </th>
							
							
							
					 </tr>
						</tbody>
				</table>
						<table >
			   <tbody>
						<tr>
						
						<th class="block" id='reportType'>Report Type &nbsp;:</th>
							<th>
								<div class="radio-inline" id = "reportPeriod">
	                			 <label><input type="radio" name="reportType" value= "singleFeederDT"  checked="checked" onchange="handleClick(this.value);">Single DT</label>
	            	           	</div>
	                       	
		                       	<div class="radio-inline">
		                			 <label><input type="radio" name="reportType" value= "multipleFeederDT"  onchange="handleClick(this.value);" >Multiple DT</label>
		                       	</div>
							</th>	
							
					</tr>		
					<tr>
						
						<th class="block">Town&nbsp;:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town" onchange='showSSnameBySubdiv(this.value)'>
								<option value="">Select Town</option>
							<option value="%">ALL</option>
							</select></th>
							
					<th  id=subStationName >Sub-Station&nbsp;:</th>
							<th id="subStationDIV">
								<select	class="form-control select2me input-medium" id="subStationList" onclick="showfdrIdNameBySubdivAndSSname(this.value)"
								name="subStation" >
									
									
						     	</select>
							</th>
							<th  id=feederName >DT&nbsp;:</th>
							
							
							<th id="feederDiv" >
								<select	class="form-control select2me input-medium" id="feederList"
								name="feederName"  >
									<option value="">Select DT</option>
									   
						     	</select>
							</th>
							
							
							<th id="feederDivMultiple">
								<select	class="form-control select2me input-medium" id="feederListMultiple"
								name="feederName" multiple="multiple" >
									<option value="">Select DT</option>
									   
						     	</select>
							</th>
							
					</tr>		
					<tr>
						 <th class="block">From BillMonth :</th>
				 				<th id="fromDateMonthly">
									<div class="input-group input-medium date date-picker" data-date-end-date="-1d" data-date-format="yyyy-mm" data-date-viewmode="years" >
										<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
										<span class="input-group-btn">
										<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
										</span>
									</div>
								</th>
							<!-- 	<th id="fromDatePeriodic">
									<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm" data-date-viewmode="years" >
										<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
										<span class="input-group-btn">
										<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
										</span>
									</div>
								</th> -->
									
								<!-- <th class="block" id= "todatelabel">To&nbsp;BillMonth&nbsp;:</th>
										<th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm" data-date-viewmode="years" id="toDate">
											<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
											<span class="input-group-btn">
											<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
											</span>
											</div>
									   </th> -->
									   
									   
									    <th>
						 <div class="input-group input-large date-picker input-daterange" id= "todatelabel"  data-date-format="yyyy-mm">
											<input type="text" autocomplete="off" placeholder="From Date" class="datepicker form-control" id="selectedFromDateIdPeriodic" 
											<fmt:parseDate value="${currentDate}" pattern="yyyy-MM" var="myDate"/>
											 value="<fmt:formatDate value="${myDate}" pattern="yyyy-mm" />" data-date="${myDate}"
										    data-date-format="yyyy-mm" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">To</span>
											<input type="text" autocomplete="off" placeholder="To Date" class="datepicker form-control" id="selectedToDateId"
											<fmt:parseDate value="${currentDate}" pattern="yyyy-MM" var="mytoDate"/>
											value="<fmt:formatDate value="${mytoDate}" pattern="yyyy-mm" />" data-date="${mytoDate}"
											data-date-format="yyyy-mm" data-date-viewmode="years" name="toDate" id="toDate"> 
								</div></th>
						</tr>			
					
					<tr>
						<th>
								<div >
									<div class="">                        
										<!-- <button type="button" class="btn yellow">Reset</button>   -->
										<button type="submit" onclick="validation()" class="btn green">Generate</button> 
									</div>
								</div>
								</th>	
					</tr>
							
					</tbody>
				</table>
							</br></br></br>
											<div class="table-toolbar" style="margin-top: 17px;margin-left: 17px;">
								<div class="btn-group pull-right" >
									<div id="excelExportDiv2">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
													Tools <i class="fa fa-angle-down"></i>
										</button>
										
										<ul class="dropdown-menu pull-right">
												<li><a href="#" onclick="printData();" id="print">Print</a></li>
												<li><a href="#" id="excelExport2" 
													onclick="tableToExcel2('sample_3', 'Reliability Indices (DT)')">Export
									 				to Excel</a></li>
										</ul>
										
									</div>
								</div>
						</div>			
						<div id="imageee" hidden="true" style="text-align: center;">
								<h3 id="loadingText">Loading..... Please wait.</h3>
								<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
									style="width: 4%; height: 4%;">
							</div>					
							<table class="table table-striped table-hover table-bordered" border="1" id="sample_3">
								<thead>
									<tr>
												<th>S.no</th>
												<th>BillMonth</th>
												<th>Subdivision</th>
												<th>Substation</th>
												<th>DT Code</th>
												<th>DT Name</th>
												<th>Feeder Code</th>
												<th>Feeder Name</th>
												<th>Meter Serial number</th>
												<th>SAIFI</th>
												<th>SAIDI</th>
												<th>CAIDI</th>
												<th>MAIFI</th>	
												
									</tr>
								</thead>
								<tbody id="eventTR">
										
								</tbody>
							</table>
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
/* function validation()
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
view();
} */
function validation(){
	
	var fromdate = null ;
	
	
	
	var todate=$("#selectedToDateId").val();
	
	var division=$("#divisionlists").val();
	var circle=$("#circleList").val();
	
	var subdiv=$("#subDivisionId").val();
	var subStation=$("#subStationList").val();
//	var feeder=$("#feederList").val();
	var reportPeriod=$('input[name = "reportType"]:checked').val();
	
	
	var feederMultiple;
	var feederCode;
	
	if(reportPeriod == 'singleFeederDT'){
		fromdate=$("#selectedFromDateIdPeriodic").val();
		if($("#feederList").val() == ''){
			bootbox.alert("Please Select DT");
			return false;	
		}
		
		
		feederMultiple	= $("#feederList").val();	
		 var res = feederMultiple.split("-");
		    feederCode = res[0];
	}else{
		fromdate=$("#selectedFromDateId").val();
		if($("#feederListMultiple").val() == ''){
			bootbox.alert("Please Select DT");
			return false;	
		}
		
		feederCode	= $("#feederListMultiple").val();	
		
	}

	 if(circle=="")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}  
	 if(division=="")
		{
		bootbox.alert("Please Select Division");
		return false;
		}  
	 if(subdiv=="")
		{
		bootbox.alert("Please Select Sub-Division");
		return false;
		}  
	 if(subStation=="")
		{
		bootbox.alert("Please Select Substation");
		return false;
		}  
	/*  if(feeder=="")
		{
		bootbox.alert("Please Select Feeder");
		return false;
		}   */
	/*  if(fromdate=="")
		{
		bootbox.alert("Please Select From");
		return false;
		}   */
		
		
		
		if(reportPeriod == 'singleFeederDT'){
		
			if(fromdate == ''){
				bootbox.alert('Please enter from billmonth.');
				return false;
			}
			if(todate == ''){
				bootbox.alert('Please enter to billmonth.');
				return false;
			}
				
			if(fromdate == todate){
				bootbox.alert('From billmonth and To billmonth cannot be same');
				return false;
			}else{
				$("#imageee").show();
			
			
	$.ajax({
		url:"./getDTWiseData",
		type:"get",
		data:{
			fromdate:fromdate,
			todate:todate,
			subdiv:subdiv,
			subStation:subStation,
			todate:todate,
			reportPeriod:reportPeriod,
			feederCode:feederCode
			
		},
		success:function(res){
			$("#imageee").hide();
			if(res == ''){
				bootbox.alert('No Data');
			}else{
			/* <td>"+moment(v[11]).format('YYYY-MM-DD HH:mm:ss')+"</td> */
			html="";
			$.each(res,function(i,v){
				html+="<tr><td>"+(i+1)+"</td><td>"+fromdate+ "</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>"+
				"<td>"+v[7]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td><td>"+v[11]+"</td><td>"+v[12]+"</td></tr>";
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
	 }	else{
		if(fromdate == ''){
			bootbox.alert('Please enter billmonth');
			return false;
		}else{
		 
		 $("#imageee").show();
	$.ajax({
		url:"./getMultipleDTwiseData",
		type:"get",
		data:{
			fromdate:fromdate,
			todate:todate,
			subdiv:subdiv,
			subStation:subStation,
			todate:todate,
			reportPeriod:reportPeriod,
			feederCode:feederCode
			
		},
		success:function(res){
			$("#imageee").hide();
			if(res == ''){
				bootbox.alert('No Data');
			}else{
			/* <td>"+moment(v[11]).format('YYYY-MM-DD HH:mm:ss')+"</td> */
			html="";
			$.each(res,function(i,v){
				html+="<tr><td>"+(i+1)+"</td><td>"+fromdate+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td>"+
				"<td>"+v[7]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td><td>"+v[11]+"</td><td>"+v[12]+"</td></tr>";
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
		}
}
 

function printData()
{
   var divToPrint=document.getElementById("sample_3");
  
   newWin= window.open("");
   newWin.document.write(divToPrint.outerHTML);
   newWin.print();
   newWin.close();

}
function handleClick(param){
	 if(param  == 'singleFeederDT') {
		 
		 
		$("#toDate").show();
		 $("#todatelabel").show();
		 $("#selectedToDateId").show();
		 $("#feederDivMultiple").hide();
		 $("#feederDiv").show();
		 $("#fromDateMonthly").hide();
		 
				 
	 }else {
		 
		 $("#toDate").hide();
		 $("#todatelabel").hide();
		 $("#selectedToDateId").hide();
		 $("#feederDivMultiple").show();
		 $("#feederListMultiple").show();
		 $("#feederDiv").hide();
		 $("#fromDateMonthly").show();
	 }
	  
 }
 

</script>

<!-- <script>
$(function(){
    $('.datepicker').datepicker({
        format: 'yyyy-mm',
        endDate: '+0d',
        autoclose: true
    });
});
</script> -->
<style>
table, th, td {
  padding: 5px;
}

</style>