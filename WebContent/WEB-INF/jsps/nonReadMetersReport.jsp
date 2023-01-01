<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<!-- -----Export Grid Data----- -->
<!-- 	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
 -->
<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>
 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		App.init();
		TableManaged.init();
		FormComponents.init();
		//loadSearchAndFilter('sample_2');
		 $('#MDMSideBarContents,#reportsId,#nonReadMetersReport').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#zone").val('noVal').trigger("change");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		});
	function showCircle(zone)
	{
		$.ajax({
		    	url:'./getcirclebyzone',
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
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Circle</option><option value='%'>ALL</option>";
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
	
	function showDivision(circle)
	{
		var zone=$('#zone').val();
		$.ajax({
		    	url:'./getDivByCircle',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
		    		circle:circle,zone:zone
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Division</option><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
		    	}
			});
	}
	function showSubdivByDiv(division)
	{
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		$.ajax({
			url : "./getSubdivByDiv",
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	
				data:{zone : zone,
					  circle : circle,
					  division : division},
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium'  type='text'><option value='noVal'>Sub-Division</option><option value='%'>ALL</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#sdoCode').select2();
		    	}
			});
	}

 	 function showTownBySubdiv(subdiv) {
	      var zone = $('#zone').val();
	      var circle = $('#circle').val();
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
	              division : division,
	              subdivision :subdiv
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"'>"
	                                  + response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	             });
	  } 
	  

    function getnonreadmeterreport() {
    	
        var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var subdiv=$("#sdoCode").val();
		var town =$("#town").val();
		var fdate=$("#fromDate").val();
		var tdate=$("#toDate").val();
		
		if(zone=='noVal' || zone==null){
			bootbox.alert('Please Select zone');
			return false;
		} 
		else if(circle=='noVal'||circle==null){
			bootbox.alert('Please Select circle');
			return false;
		}
		else if(division=='noVal'||division==null){
			bootbox.alert('Please Select division');
			return false;
		}
		else if(subdiv==''||subdiv==null){
			bootbox.alert('Please Select subdivision');
			return false;
		}
		else if(town==''||town==null){
			bootbox.alert('Please Select town');
			return false;
		}
		else if(fdate==''||fdate==null){
			bootbox.alert('Please Select fromdate');
			return false;
		}
		else if(tdate==''||tdate==null){
			bootbox.alert('Please Select todate'); 
			return false;
		}
		if(fdate>tdate)
    	{
    	    bootbox.alert("From Date Should be Less than To Date");
    	    return false;
    	}
		
		    var date1 = new Date(fdate);
	        var date2 = new Date(tdate);
	        var timeDiff = Math.abs(date2.getTime() - date1.getTime());
	        var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	       
	        var fromYear=date1.getFullYear()
	        var toYear=date2.getFullYear()
	        var diffyear =toYear-fromYear;
	        
	       
	        if(diffDays>30)
	        {
	        	bootbox.alert('Date Difference should not be more than 30'); 
				return false;
	        }
		
		$('#imageee').show();
	    $.ajax({
		url : './nonReadMetersReportAnalyticsData',
		type : 'POST',
		data : {
			zone:zone,
			circle:circle,
			division:division,
			sdoCode:subdiv,
			fromDate:fdate,
			toDate:tdate,
			town : town
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();		
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) 
				{
					var resp = response[i];
					var srno=i+1;
					html+="<tr>"+
							  "<td>"+srno+"</td>"+
							  "<td>"+((resp[0]==null)?"":resp[0])+" </td>"+
							  "<td>"+((resp[1]==null)?"":resp[1])+" </td>"+
							  "<td>"+((resp[3]==null)?"":resp[3])+" </td>"+
							 /*  "<td>"+((resp[3]==null)?"":resp[3])+" </td>"+ */
							  "<td>"+((resp[4]==null)?"":resp[4])+" </td>"+
							  /* "<td>"+((resp[5]==null)?"":resp[5])+" </td>"+ */
							  "<td>"+((resp[7]==null)?"":moment(resp[7]).format("YYYY-MM-DD HH:mm:ss"))+" </td>"+
							  "<td>"+((resp[8]==null)?"":resp[8])+" </td>"+
							  "</tr>";	
				 }
				$('#sample_2').dataTable().fnClearTable(); 
				$('#TbodyID').html(html);
				loadSearchAndFilter('sample_2');

			} 	
			else {
				 bootbox.alert("No Data Found");
				 $('#sample_2').dataTable().fnClearTable(); 
			} 

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
						<i class="fa fa-edit"></i>Consistent Communication Fail Meters Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> 
					</div>
				</div>
				<div class="portlet-body">
						<div class="row" style="margin-left: -1px;">
						   <div class="col-md-3"><b>Region:</b>
							<div class="form-group">
								<select class="form-control select2me input-medium"
									name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Region</option>
										<option value='%'>ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
							</div>
						</div>
                          <div class="col-md-3"><b>Circle:</b>
							<div id="circleTd" class="form-group">
								<select
									class="form-control select2me input-medium" id="circle"
									name="circle">
										<option value='noVal'>Circle</option>
										<option value='%'>ALL</option>
										<c:forEach items="${circleList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
								</div>
						</div>
						<div class="col-md-3"><b>Division:</b>
							<div id="divisionTd" class="form-group">
								<select class="form-control select2me input-medium" id="division" name="division">
										<option value='noVal'>Division</option>
										<option value='%'>ALL</option>
										<c:forEach items="${divisionList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-3"><b>Sub-Division:</b>
							<div id="subdivTd" class="form-group">
                               <select class="form-control select2me input-medium" id="sdoCode"  name="sdoCode" id="sdoCode">
										<option value='noVal'>Sub-Division</option>
										<option value='%'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select>
							</div>
						</div>
					</div> 
					
					
                     <div class="row" style="margin-left: -1px;">
                     
                     <div class="col-md-3"><b>Town:</b>
							<div id="townTd" class="form-group">
                               <select class="form-control select2me input-medium" id="town"  name="town" >
										<option value=''>Town</option>
										<option value='%'>ALL</option>
										<c:forEach items="${townList}" var="townVal">
											<option value="${townVal}">${townVal}</option>
										</c:forEach>
								</select>
							</div>
						</div>
						        <div class="col-md-3"><b>From Date:</b>
									<div class="form-group">
										
											<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
											<input type="text" placeholder="Select From Date" class="form-control" name="fromDate" id="fromDate"  style="cursor: pointer">
											<span class="input-group-btn">
											<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
											</span>
											</div>
									</div>	
								</div>	
								<div class="col-md-3"><b>To Date:</b>
									<div class="form-group">
										
											<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-end-date="0d" data-date-viewmode="years">
											<input type="text" class="form-control" placeholder="Select To Date" name="toDate" id="toDate"  style="cursor: pointer">
											<span class="input-group-btn">
											<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
											</span>
											</div>
									</div>
								</div>
																
						</div>	
						
								
               <div class="tabbable tabbable-custom">
					<div id="show">
							<button type="button" id="dataview"
								style="margin-left: 480px;" onclick="return  getnonreadmeterreport()"
								name="dataview" class="btn yellow">
								<b>View</b>
							</button>
							<br />
							
					<div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
		     	   </div>
		     	   <div class="table-toolbar">
								<div class="btn-group pull-right" style="margin-top: 15px;">
									<div id="excelExportDiv2">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools 
									<i class="fa fa-angle-down"></i></button>
									<ul class="dropdown-menu pull-right">
									<li><a href="#" id="print" onclick="exportGridData()">Export to PDF</a></li>
							        <li><a href="#" id="excelExport2" onclick="tableToExcel2('sample_2', 'Consistent Communication Fail Meters Report')">Export to Excel</a></li>
								</ul>
								</div>
						   </div>
				  </div>
				<table class="table table-striped table-bordered table-hover"
					id="sample_2">
					<thead>
						<tr>
						<th>Sl No</th>
						<th>Meter No</th> 
						<th>Subdivision</th>
						<!-- <th>KNo</th> -->
						<th>Location ID</th>
						<th>Location Name</th>
						<!-- <th>Status</th> -->
						<th>Last Meter Read Date</th>
						<th>Last Meter Diagnostic signal</th>
							<!-- <th rowspan="2">SixMonthAvg</th>
										<th rowspan="2">TwelveMonthAvg</th> -->

						</tr>

						</thead>
						<tbody id="TbodyID">
								<%-- <c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${reslutList}">
									<tr>
		
		
										<td>${count}</td>
										<td>${element[0]}</td>
										<td>${element[1]}</td>
										<td>${element[2]}</td>
										<td>${element[3]}</td>
										<td>${element[4]}</td>
										<td>${element[5]}</td>
										<td>${element[0]}</td>
										<td>${element[7]}</td>
										<td>${element[8]}</td>
										<td>${element[7]}</td>
										
										<c:set var="count" value="${count+1}" scope="page" />
		
									</tr>
								</c:forEach> --%>
		
						</tbody> 
					</table>
 				</div> 
			  </div>
		   </div>	
	    </div>
	</div>
  </div>
</div>
<script>
	 $('.input-daterange').datepicker({
			autoclose : true,
			endDate : '+0d'
		      });	
	 function exportGridData()
	 {
	 	
	 	html2canvas(document.getElementById('sample_2'), {
	         onrendered: function (canvas) {
	             var data = canvas.toDataURL();
	             var docDefinition = {
	                 content: [{
	                     image: data,
	                     width: 500
	                 }]
	             };
	             pdfMake.createPdf(docDefinition).download("ConsistentCommunicationFailMetersReport.pdf");
	          }
	        });

	 }
	 
</script>


<script>

function createPDF() {
    var sTable = document.getElementById('sample_2').innerHTML;

    var style = "<style>";
    style = style + "table {width: 100%;font: 17px Calibri;}";
    style = style + "table, th, td {border: solid 1px #DDD; border-collapse: collapse;";
    style = style + "padding: 2px 3px;text-align: center;}";
    style = style + "</style>";

    // CREATE A WINDOW OBJECT.
    var win = window.open('', '', 'height=700,width=700');

    win.document.write('<html><head>');
    win.document.write('<title>Profile</title>');   // <title> FOR PDF HEADER.
    win.document.write(style);          // ADD STYLE INSIDE THE HEAD TAG.
    win.document.write('</head>');
    win.document.write('<body>');
    win.document.write(sTable);         // THE TABLE CONTENTS INSIDE THE BODY TAG.
    win.document.write('</body></html>');

    win.document.close(); 	// CLOSE THE CURRENT WINDOW.

    win.print();    // PRINT THE CONTENTS.
}


</script>




