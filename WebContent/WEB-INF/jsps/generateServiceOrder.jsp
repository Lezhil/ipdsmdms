<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.ResourceBundle"%>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/print.min.js'/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/assets/css/print.min.css">

<!-- <script src="https://printjs-4de6.kxcdn.com/print.min.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" href=" https://printjs-4de6.kxcdn.com/print.min.css"> -->
<style>
.test {
	background-color: red;
}

.test2 {
	background-color: green;
}

.resizedTextbox {
	width: 500px;
	height: 20px
}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						TableManaged.init();
						loadSearchAndFilter('sample_1');  
						FormComponents.init();
						UIDatepaginator.init();
					
						$("#locType").val("").trigger("change");
						$("#issueType").val("").trigger("change");
						$("#issue").val("").trigger("change");
						/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
						$('#MDMSideBarContents,#serviceOrderManagementID,#generateServiceOrder').addClass(
								'start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

						//alarmData('all');
							 $('#saveAndNotifyModal').click(function() {
									   $('#wide').modal('hide');
									});

							 $('#saveAndPrintModal').click(function() {
								   $('#wide').modal('hide');
								});

						$("#servicecheck").click(
								function() {
									$(".checkboxes").prop('checked',
											$(this).prop('checked'));
								});

												
							

								/*  $('#saveAndNotifyModal').submit(function(e) {
									    e.preventDefault();
									    // Coding
									    //$('#serviceDetailsModalPop').modal('toggle'); //or  
									    $('#serviceDetailsModalPop').modal('hide');
									    return false;
									}); */
						});

	var tableToExcel=(function () {
	    var uri = 'data:application/vnd.ms-excel;base64,'
	        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
	        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
	    return function (table, name) {
	        if (!table.nodeType) table = document.getElementById(table)
	        var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	        document.getElementById("exportExcel").href = uri + base64(format(template, ctx));
	        document.getElementById("exportExcel").download = name+'_'+moment().format("DD-MM-YYYY_hh.mm.ss")+'.xls';
	    }
	})() 

	var zone="%"
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
                        html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
                        html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option>";
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
     // var zone = $('#zone').val();
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
                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option>";
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

 function showTownNameandCode(circle){
		var zonee =  $('#zone').val(); 
		if(zonee == null || zonee == "")
		{
			zone="%";
		}
		//$('#town').find('option').remove();
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
		  		success : function(response) {
		  		   /* $('#town').append($('<option>', {
	 					value : '%',
	 					text : 'ALL',
	 				})); */
	              for (var i = 0; i < response.length; i++) {
	                  $('#town').append($('<option>', {
	  					value : response[i][0],
	  					text : response[i][0] + "-" + response[i][1],
	  				}));
	              }
	            }
		  	});
		  }
 
	function mtrDetails(mtrNo) {
		window.location.href = "./mtrNoDetailsMDAS?mtrno=" + mtrNo;
	}

	/*   function submitData()
	 {	 
	 if($("#fromdate").val()=="")
	 {
	 bootbox.alert('PLease Select From Date');
	 return false;
	 }
	 if($("#todate").val()=="")
	 {
	 bootbox.alert('PLease Select To Date');
	 return false;
	 }
	 var levels = "";
	 var mtrs = "";
	 var flagChecked = 0;
	 var flagChecked1 = 0;
	 checkboxes = document.getElementsByName('alaramtype');
	 for(var i =0;i<checkboxes.length;i++)
	 {
	 if(checkboxes[i].checked)
	 {
	 levels = levels +"'" +checkboxes[i].value + "',";
	 flagChecked1 = 1;
	 }
	 }
	 if(flagChecked1==0)
	 {
	 bootbox.alert('PLease Select Atleast One Alarm Type ');
	 return false;
	 }
	 levels = levels.substring(0,levels.length-1);
	 $("#alaramtypeValue").val(levels);
	
	 checkboxes = document.getElementsByName('metertype');
	 for(var i =0;i<checkboxes.length;i++)
	 {
	 if(checkboxes[i].checked)
	 {
	 mtrs = mtrs +"'" +checkboxes[i].value + "',";
	 flagChecked = 1;
	 }
	 }
	 if(flagChecked==0)
	 {
	 bootbox.alert('PLease Select Atleast One Meter Type ');
	 return false;
	 }
	 mtrs = mtrs.substring(0,mtrs.length-1);
	 $("#metertypeValue").val(mtrs);
	 } */

function getIssues(issue)
	 {
		 //alert(issue);
		 var html="";
       if(issue=="Power theft")
           {
    	   html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option>";
    	   html += "<option value='Suspected Power Theft'>Suspected Power Theft</option>";
           }
       
       if(issue=="Meter Events")
       {
    	   $.ajax({
               url : './getEventListfromMeterEvents',
               type : 'GET',
               dataType : 'json',
               asynch : false,
               cache : false,
                       success : function(response) {
                           //alert(response);
                           var html = '';
                           html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option>";
                          /*  for (var i = 0; i < response.length; i++) {
                               html += "<option value='"+response[i]+"'>"
                                       + response[i] + "</option>";
                           }
                           html += "</select><span></span>"; */
                          $("#issueId").html(html);
                           $('#issue').select2();
                       }
                   });
       }
       
       if(issue=="Meter Exception/Alarms")

           {  html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option>";
    	   html += "<option value='Missing Load Survey Data'>Missing Load Survey Data</option>";
    	   html += "<option value='Incomplete Load Survey Data'>Incomplete Load Survey Data</option>";

           }
     /*   {
    	   $.ajax({
               url : './getMeterExceptionAlarmList',
               type : 'GET',
               dataType : 'json',
               asynch : false,
               cache : false,
                       success : function(response) {
                           //alert(response);
                           var html = '';
                           html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option> <option value=''>Missing Load survey data</option><option value=''>Incomplete Load survey data</option>   ";
                           for (var i = 0; i < response.length; i++) {
                               html += "<option value='"+response[i]+"'>"
                                       + response[i] + "</option>";
                           }
                           html += "</select><span></span>";
                          $("#issueId").html(html);
                           $('#issue').select2();
                       }
                   });
       } */
       
       if(issue=="Communication Fail")
       {
	   html += "<label class='control-label'>Issue:</label><select id='issue' name='issue'  class='form-control select2me' type='text'><option value=''>Select</option>";
	   html += "<option value='Consistent Communication Fail'>Consistent Communication Fail</option>";
       }

      		html += "</select><span></span>";
			$("#issueId").html(html);
			$('#issue').select2();
           

	}
	var model_global_serial;
	var system_date;
	var userName;
	var issueTimestamp;
	function viewServiceOrderDetails()
	{
		//alert("================");
		 var zone=$("#LFzone").val();
       var circle=$("#LFcircle").val();
       /* var division=$("#division").val();
       var sdoCode=$("#sdoCode").val(); */
       var locType=$("#locType").val();
       var issue=$("#issue").val();
       var issueType =$("#issueType").val();
       var town =$("#LFtown").val();
		//alert(issue);
		 if(zone==null || zone=="")
       {
       	bootbox.alert(" Please Enter Region.");
       	return false;
       }
		 if(circle==null || circle=="")
	       {
	       	bootbox.alert(" Please Enter  circle.");
	       	return false;
	       }
  
       /* if(division==null || division=="")
       {
       	bootbox.alert("Enter division.");
    	return false;
       }

       if(sdoCode==null || sdoCode=="")
       {
       	bootbox.alert("Enter subdivision.");
    	return false;
       } */
       if(town==null || town=="")
       {
       	bootbox.alert("Enter town.");
    	return false;
       }
       if(locType==null || locType=="")
       {
       	bootbox.alert("Enter location type.");
    	return false;
       }
       if(issueType==null || issueType=="")
       {
       	bootbox.alert("Enter issue type.");
    	return false;
       }
       if(issue==null || issue=="")
       {
       	bootbox.alert("Enter issue.");
    	return false;
       }
       $('#imageee').show();

       			$.ajax({
		               url : "./getConsServiceOrderDetails",
		               type : 'GET',
		               dataType : 'json',
		               asynch : false,
		               cache : false,
		               data:{
			               zone:zone,
		            	   circle : circle,
		            	   //division : division,
		            	   //sdoCode : sdoCode,
		            	   town : town,
		            	   locType : locType,
		            	   issue : issue,
		            	   issueType : issueType
		                   },
		               success : function(data) {
		            	   $('#imageee').hide();
		            	   var html="";
		      				//alert(data.resultSO);
		      				
		      				var resultSO=data.resultSO;
		      				model_global_serial = resultSO;
		      				
		      				var sysdate=data.date;
		      				system_date=sysdate;
		      				var username=data.userName;
		      				userName=username;
		      				var timestamp=data.timestamp;
		      				issueTimestamp=timestamp;
		      				  
		      				var response=data.array;
		      				
		      			  if(response.length>0){
			      			  for(var i=0;i<response.length;i++)
		      				{
		      	               var resp=response[i];
		      	           //  alert(resp);
		      	               html += "<tr>"
		      						+ "<td><input id='servicecheck' name='servicecheck' type='checkbox' class='checkboxes' value='"+resp[2]+","+locType+","+resp[6]+","+resp[7] +","+resp[5]+"'></td>"
		      						+ "<td>"+(resp[2]==null?"":resp[2])+"</td>"
		      						+ "<td>"+locType+"</td>"
		      						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
		      						+ "<td>"+ (resp[7]==null?"":resp[7]) +"</td>"
		      						+ "<td>"+ (resp[5]==null?"":resp[5])+"</td>"
		      						+ "</tr>";
		      				}	
		      			  }
		      				  
		      			  
		      			 else{
			       			bootbox.alert("Oops!!! No data available.");
			       			}	 
		      				
		      				 $('#sample_1').dataTable().fnClearTable();
		      				$('#serviceOrder').html(html);
		      				loadSearchAndFilter('sample_1'); 
		      			
		      		},
		      		complete: function()
		      		{  
		      			loadSearchAndFilter('sample_1'); 
		      		} 
		
		
		           });
			
	}

	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
</script>
<div class="page-content">

	<div class="portlet box blue" hidden="true">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Generate Service Orders
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal">
				<div class="form-body">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">From Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="fromdate"
											id="fromdate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">To Date</label>
								<div class="col-md-9">
									<div class="input-group input-medium date date-picker"
										data-date-format="yyyy-mm-dd" data-date-viewmode="years"
										id="five">
										<input type="text" class="form-control" name="todate"
											id="todate" readonly> <span class="input-group-btn"><button
												class="btn default" type="button" id="six">
												<i class="fa fa-calendar"></i>
											</button> </span>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="row">
									<label class="control-label col-md-3">Alarm Types</label>
									<div class="col-xs-7 col-sm-3">
										<div class="checkbox-list">
											<label> <input type="checkbox" id="Tamper"
												name="alaramtype" value="Tamper"> Tamper Data
											</label> <label> <input type="checkbox" id="Outage"
												name="alaramtype" value="13"> Outage Data
											</label> <label> <input type="checkbox" id="Others"
												name="alaramtype" value="1178"> Others
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
						<div class="col-md-6">
							<div class="form-group">

								<label class="control-label col-md-3">Meter Types</label>
								<div class="col-md-9">
									<div class="checkbox-list">
										<label> <input type="checkbox" id="HT"
											name="metertype" value="LT"> LT
										</label>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="alaramtypeValue" name="alaramtypeValue">
						<input type="hidden" id="metertypeValue" name="metertypeValue">
						<!--/span-->
					</div>
					<!--/row-->
				</div>
				<div class="form-actions fluid">
					<div class="row">
						<div class="col-md-6"></div>
						<div class="col-md-6">
							<div class="col-md-offset-8 col-md-9">
								<button type="submit" class="btn green"
									onclick="return submitData();">View</button>
								<button type="button" class="btn default">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- Display Error Message -->
	<c:if test="${not empty msg}">
		<div class="alert alert-danger display-show">
			<button class="close" data-close="alert"></button>
			<span style="color: red">${msg}</span>
		</div>
	</c:if>

	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Generate Service Orders
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
				<div id="locTypeTd" class="form-group">
					<label class="control-label">Location Type:</label> <select
						class="form-control select2me" id="locType"
						name="locType">
						<option value="">Select</option>
						<option value="BOUNDARY METER">BOUNDARY METER</option>
						<option value="DT">DT</option>
						<option value="FEEDER METER">FEEDER METER</option>

					</select>
				</div>
			</div>


			<div class="col-md-3">
				<div id="issueTypeTd" class="form-group">
					<label class="control-label">Issue Type:</label> <select
						class="form-control select2me" id="issueType"
						name="issueType" onchange="getIssues(this.value)">
						<option value="">Select</option>
						<!-- <option value="Power theft">Power Theft</option> -->
						<option value="Meter Events">Meter Events</option>
						<option value="Meter Exception/Alarms">Meter Exception/Alarms</option>
						<option value="Communication Fail">Communication Fail</option>
					</select>
				</div>
			</div>
			
			<div class="col-md-3" >
				<div id="issueId" class="form-group" >
					<label class="control-label">Issue:</label> <select
						class="form-control select2me" id="issue"
						name="issue">
						<option value="">Select</option>
					</select>
				</div>
			</div>


			
		</div>

  

		<!-- 
							<th class="block">Location&nbsp;Type&nbsp;<font color="red">*</font>:</th>
							<th id="locTypeTd"><select
								class="form-control select2me input-medium" id="locType"
								name="locType">
								<option value="">Select</option>
											<option value="BOUNDARY METER">BOUNDARY METER</option>
											<option value="DT">DT</option>
											<option value="FEEDER METER">FEEDER METER</option>
							</select></th>

					
							<th class="block">Issue&nbsp;Type&nbsp;<font color="red">*</font>:</th>
							<th id="issueTypeTd"><select
								class="form-control select2me input-medium" id="issueType"
								name="issueType" onchange="getIssues(this.value)">
								<option value="">Select</option>
											<option value="Power theft">Power theft</option>
											<option value="Meter Events">Meter Events</option>
											<option value="Meter Exception/Alarms">Meter Exception/Alarms</option>
											<option value="Communication Fail">Communication Fail</option>
							</select></th>
							</tr>
							 <tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr> 


						<tr>
							<th class="block">Issue&nbsp;<font color="red">*</font>:</th>
							<th id="issueTd"><select
								class="form-control select2me input-medium" id="issue"
								name="issue">
								<option value="">Select</option>
							</select></th>
							
						</tr>
						
						
					</tbody>
				</table> --%> -->

		<div class="modal-footer">

			<button type="button" onclick="return viewServiceOrderDetails();"
				style="margin-left: 422px;" class="btn blue pull-left">View
			</button>
		</div>


		
		<div id="imageee" hidden="true" style="text-align: center;">
			<h3 id="loadingText">Loading..... Please wait.</h3>
			<img alt="image" src="./resources/bsmart.lib.js/loading.gif"
				style="width: 4%; height: 4%;">
		</div>



		
			<div class='row'>
				<div class='col-sm-9'>
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" data-target="#wide" data-toggle="modal"
						onclick="return generateServiceOrderDetails()"
						class="btn blue pull-left">Generate To Service Order</button>
				</div>
			</div>
			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2"></div>
				</div>
			</div>
			<div class="btn-group pull-right">
				<button class="btn dropdown-toggle" data-toggle="dropdown">
					Tools <i class="fa fa-angle-down"></i>
				</button>
				<ul class="dropdown-menu pull-right">

					<li><a href="#" id="exportExcel"
						onclick="generateServiceExcel('sample_1','Generate Service Order');">Export
							to Excel</a></li>
				</ul>
			</div>
			<table class="table  table-bordered table-hover" id="sample_1">

				<thead>
					<tr>

						<th><input id='servicecheck' name='servicecheck'
							type='checkbox' class='checkboxes'>Select</th>
						<th>Town</th>
						<th>LocType</th>
						<th>LocIdentity</th>
						<th>LocName</th>
						<th>MeterSrNumber</th>

					</tr>
				</thead>
				<tbody id="serviceOrder">

				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="wide" tabindex="-1" role="basic"
	aria-hidden="true">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #4b8df8">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Service Order</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box yellow">
						<div class="portlet-title">
							<%
								ResourceBundle resource = ResourceBundle.getBundle("messages");
								String header = resource.getString("pageheader");
								String logopath = resource.getString("pageheaderlogo");
							%>
							<div class="caption">
								<a href="#" class="companyName"><img src="<%=logopath%>"
									alt="" /><span class="title" style="color: white;"><%=header%></span></a>
							</div>
							<div class="tools">
								<button type="button" id="saveAndPrintModal" target="_blank"
									href="www.google.com" onclick="return saveAndPrintDeatils();"
									class="btn blue pull-left">Save And Print</button>



								<button type="button" id="saveAndNotifyModal"
									onclick="return saveAndNotifyDeatils();"
									class="btn blue pull-left">Save And Notify</button>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-hover">

									<tbody>
										<tr>
											<td colspan="3">Region:- <span id="zoneModal"></span></td>
										</tr>
										<tr>
											<td colspan="3">Circle :- <span id="circleModal"></span></td>
										</tr>
										<tr>
											<td colspan="3">Town :- <span id="townModal"></span></td>
										</tr>

										<tr>
											<td colspan="2" id="soNoModal">SO Number :- <span
												id="soNoModal_val"> </span>
											</td>
											<td id="dateModal">Date:- <span id="date_val"> </span></td>
										</tr>

										<tr>
											<td colspan="3" id="fromModal">From:- <span
												id="user_val"> </span></td>
										</tr>

										<tr>
											<td colspan="3" id="toModal">To:-<font color="red">*</font><input
												type="text" id="toSectionModal" class="form-control " /></td>
										</tr>

										<tr>
											<td colspan="3">Issue Type :- <span id="issueTypeModal">
											</span></td>
										</tr>

										<tr>
											<td colspan="3">Issue :-<span id="issueModal">
											</span></td>
										</tr>

										<tr>
											<td colspan="3">Please Verify the issue as mentioned
												above , and report corrective action for below mentioned
												metering locations.</td>
										</tr>

										<tr>
											<td colspan="3">
												<table id="serviceupdateTable" style="width: 100%"
													border="1">
													</tr>
													</thead>
													<tr>
													</tr>
												</table>
											</td>
										</tr>

										<tr>
											<td rowspan="2" id="emailModal">EMail</td>
											<td id="toMailModal">To:-<font color="red">*</font><input
												type="text" id="toMailVal" class="form-control" required
												onblur="checkToMail();" /> <span id="toMsg">Enter
													only one valid email address.</span></td>
										</tr>
										<tr>
											<td id="ccMailModal">Cc:-<input type="text"
												id="ccMailVal" class="form-control" required
												onblur="checkCCMail();" /> <span id="ccMsg">Enter
													only four valid email address. </span></td>
										</tr>
										<tr>
											<td colspan="3" id="msgModal">SMS:-<font color="red"></font><input
												type="text" id="msgVal" class="form-control" maxlength="54"
												required onblur="check();" /> <span id="message">Enter
													only five valid mobile numbers.</span></td>

										</tr>
									</tbody>
								</table>
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
function generateServiceOrderDetails()
{

	var zone=$("#LFzone").val();
var circle=$("#LFcircle").val();	
  var town=$("#LFtown").val();
  var locType=$("#locType").val();
  var issueType=$("#issueType").val();
  var issue=$("#issue").val();


  $("#soNoModal_val").text(model_global_serial);
  $("#date_val").text(system_date); 
  $("#user_val").text(userName); 

  var toMailModal=$("#toMailVal").val('');
  var ccMailModal=$("#ccMailVal").val('');
  var msgModal=$("#msgVal").val('');
  var tosection=$("#toSectionModal").val('');
  $("#message").html("Enter only five valid mobile numbers.");
  $("#ccMsg").html("Enter only four valid email address.");
  $("#toMsg").html("Enter only one valid email address.");
  
  var checkedValue = null; 
	var mtrno=[];
	var sdoname=[];
	var loctype=[];
	var locidentity=[];
	var locname=[];
	var singlevalues=[];
	var singlearray=[];
	var finalarray=[];
	var optarray=[];
	var splitValue=[];
	var arrayVal=[];
	var html2="";
  var inputElements = document.getElementsByName('servicecheck');
  for(var i=0; i < inputElements.length; i++){
	  
	  var htmll = "<thead><tr style='background-color: lightgray'><th>LocType</th><th>LocIdentity</th><th>LocName</th> <th>MeterSrNumber</th></tr></thead>";
        if(inputElements[i].checked)
          {
          
	       checkedValue = inputElements[i].value;

	        if(checkedValue!='on')
		    {
	    	   singlevalues = checkedValue.split(",");
		   		singlearray.push(singlevalues);
		   		finalarray.push(singlearray);
		     }
        	
        } 
  }

 
  for(var j=0; j<finalarray.length;j++)
  {
			
			arrayVal = finalarray[j][j];
			//alert(arrayVal);
			htmll += "<tr>"
					+ "<td>"+arrayVal[1]+"</td>"
					+ "<td>"+arrayVal[2]+"</td>"
					+ "<td>"+arrayVal[3]+"</td>"
					+ "<td>"+arrayVal[4]+"</td>"
					+ "</tr>";
}

  
$('#serviceupdateTable').html(htmll);

/* if(Region=="%")
{
var zone ="ALL";
}

if(circle=="%")
	{
      var circle="ALL";
	}

  if(town=="%")
	{
    var town="ALL";
	} */

	
          $("#zoneModal").html(zone);	
          $("#circleModal").html(circle);	
		  $("#townModal").html(town);
		  $("#issueTypeModal").html(issueType);
		  $("#issueModal").html(issue);
		  $("#issueModal").html(issue);
}

function saveAndNotifyDeatils()
{
var so_number=model_global_serial;
var locType=$("#locType").val();
var issueType=$("#issueType").val();
var issue=$("#issue").val();
var toMailModal=$("#toMailVal").val();
var ccMailModal=$("#ccMailVal").val();
var msgModal=$("#msgVal").val();
var tosection=$("#toSectionModal").val();
var zone=$("#LFzone").val();
var circle=$("#LFcircle").val();	
  var town=$("#LFtown").val();
if(tosection==null || tosection=="")
{
	bootbox.alert("Enter To whom to be notified.");
	return false;
}	
if(toMailModal==null || toMailModal=="")
{
	bootbox.alert("Enter whom to send mail notification .");
	return false;
}
if(msgModal==null || msgModal=="")
{
	bootbox.alert("Enter  whom to send SMS notification.");
	return false;
}

var date=system_date;
var checkedValue = null; 
var singlevalues=[];
var singlearray=[];
var finalarray=[];
var arrayVal=[];
var locID=null;
var mtr=null;
var locIdAll=[];
var optarr=[];
var mtrarr=[];
var all_types = [];
var all_validations = [];
var inputElements = document.getElementsByName('servicecheck');
for(var i=0; i < inputElements.length; i++){
	  
      if(inputElements[i].checked)
        {
        
	       checkedValue = inputElements[i].value;

	        if(checkedValue!='on')
		    {
	    	   singlevalues = checkedValue.split(",");
		   		singlearray.push(singlevalues);
		   		finalarray.push(singlearray);
		     }
      	
      } 
}

for(var j=0; j<finalarray.length;j++)
{
			
			arrayVal = finalarray[j][j];
			locID=arrayVal[2];
			mtr=arrayVal[4];
			optarr.push(locID);
			mtrarr.push(mtr);

all_validations.push({
	"loc_type" : arrayVal[1],
	"loc_ID" : arrayVal[2],
	"loc_name" : arrayVal[3],
	"mtr_sr" : arrayVal[4]

});
all_types = all_validations;
}

$.ajax({

	url : './savingAllServiceData',
	type : 'GET',
	data : {
		so_number : so_number,
		locType : locType,
		issue : issue,
		issueType : issueType,
		issueTimestamp : issueTimestamp,
		toMailModal : toMailModal.toString(),
		ccMailModal : ccMailModal.toString(),
		msgModal : msgModal.toString(),
		optarr : optarr.toString(),
		mtrarr : mtrarr.toString(),
		zone:zone,
		circle : circle,
		town : town,
		arrayVal : JSON.stringify(all_types),
		tosection : tosection,
		userName : userName
		
	},
	dataType : 'text',
	success : function(response) {
		//alert(response);
		
		if(response!=null){
			bootbox.alert("Data Saved and Notified  Successfully!!!");
		}
		else{
			bootbox.alert("Oops!!! Something went wrong.");
			}
	}

});
//exportGridData();
}

//function 

//pdf convertor
/* function exportGridData()
{
	//alert("pdffff");
	html2canvas(document.getElementById('serviceOrderDeatils'), {
        onrendered: function (canvas) {
            var data = canvas.toDataURL();
            var docDefinition = {
                content: [{
                    image: data,
                    width: 500
                }]
            };
            pdfMake.createPdf(docDefinition).download("serviceOrderDeatils.pdf");
         }
       });

} */

 function printPdf(){
	var doc = new jsPDF('p', 'pt');
	var elem = document.getElementById("serviceOrderDeatils");
	var res = doc.autoTableHtmlToJson(elem);
	doc.autoTable(res.columns, res.data);
	doc.save("serviceOrderDeatils.pdf");
}

function check()
{

	var mob = $("#msgVal").val();
var reg=/^\+[0-9]{2,3}-[0-9]\d{10}/;
	 if(reg.test(mob) == false)
		{
	$("#message").html("Enter valid mobile numbers.");	
		}
    }


function checkToMail()
{

	var tomail=$("#toMailVal").val();
	//alert(tomail);
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

    //alert(reg.test(tomail.value))
    if (reg.test(tomail) == false) 
    {
    	$("#toMsg").html("Enter valid email address");
        return false;
    }
    else
    {
    	$("#toMsg").html("Its a valid one ! ");
    	 return true;
     }
}

	function checkCCMail()
	{

		var ccmail=$("#ccMailVal").val();
		//alert(tomail);
	    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

	    //alert(reg.test(tomail.value))
	    if (reg.test(ccmail) == false) 
	    {
	    	$("#ccMsg").html("Enter valid email address");
	        return false;
	    }

	    else
	    {
	    	$("#ccMsg").html("Its a valid one ! ");
	    	 return true;
	     }

	}

	function generateServiceExcel() {
		  var zone=$("#LFzone").val();
		  var circle=$("#LFcircle").val();
		  var town=$("#LFtown").val();
		  var locType=$("#locType").val();
		  var issueType=$("#issueType").val();
		  var issue=$("#issue").val();

	 	  
		if(zone =='%'){
			zone='ALL'
		}
		if (circle =='%'){
			circle='ALL'
		}
		if (town =='%'){
			town='ALL'
		}
		 
		//window.location.href = ("./DtDetailsExcel?circle=" + circle + "&town="+ town + "&ssid=" + ssid);
		window.location.href = ("./generateServiceExcel?circle=" + circle + "&locType="+ locType + "&town=" + town +"&issueType="+issueType+"&issue="+issue+"&zone="+zone);
	} 
	
	
function saveAndPrintDeatils()
{
  var so_number=model_global_serial;
  var locType=$("#locType").val();
  var issueType=$("#issueType").val();
  var issue=$("#issue").val();
  var toMailModal=$("#toMailVal").val();
  var ccMailModal=$("#ccMailVal").val();
  var msgModal=$("#msgVal").val();
  var tosection=$("#toSectionModal").val();
  var circle=$("#LFcircle").val();
  var zone=$("#LFzone").val();
  var town=$("#LFtown").val();

  if(tosection==null || tosection=="")
  {
  	bootbox.alert("Enter To whom to be notified.");
  	return false;
  }	
  if(toMailModal==null || toMailModal=="")
  {
  	bootbox.alert("Enter whom to send mail notification .");
  	return false;
  }
/*   if(msgModal==null || msgModal=="")
  {
  	bootbox.alert("Enter  whom to send SMS notification.");
  	return false;
  } */

  var date=system_date;
  var checkedValue = null; 
  var singlevalues=[];
  var singlearray=[];
  var finalarray=[];
  var arrayVal=[];
  var locID=null;
  var mtr=null;
  var locIdAll=[];
  var optarr=[];
  var mtrarr=[];
  var all_types = [];
  var all_validations = [];
  var inputElements = document.getElementsByName('servicecheck');
  for(var i=0; i < inputElements.length; i++){
  	  
        if(inputElements[i].checked)
          {
          
  	       checkedValue = inputElements[i].value;

  	        if(checkedValue!='on')
  		    {
  	    	   singlevalues = checkedValue.split(",");
  		   		singlearray.push(singlevalues);
  		   		finalarray.push(singlearray);
  		     }
        	
        } 
  }

  for(var j=0; j<finalarray.length;j++)
  {
  			
  			arrayVal = finalarray[j][j];
  			locID=arrayVal[2];
  			mtr=arrayVal[4];
  			optarr.push(locID);
  			mtrarr.push(mtr);

  all_validations.push({
  	"loc_type" : arrayVal[1],
  	"loc_ID" : arrayVal[2],
  	"loc_name" : arrayVal[3],
  	"mtr_sr" : arrayVal[4]

  });
  all_types = all_validations;
  }
  arrayVal = JSON.stringify(all_types);
  $.ajax({

  	url : './savingAllServiceDataPrint',
  	type : 'POST',
  	data : {
  		so_number : so_number,
  		locType : locType,
  		issue : issue,
  		issueType : issueType,
  		issueTimestamp : issueTimestamp,
  		toMailModal : toMailModal.toString(),
  		ccMailModal : ccMailModal.toString(),
  		msgModal : msgModal.toString(),
  		optarr : optarr.toString(),
  		mtrarr : mtrarr.toString(),
  		circle : circle,
  		zone : zone,
  		town : town,
  		arrayVal : JSON.stringify(all_types),
  		tosection : tosection,
  		userName : userName
  		
  	},
  
  	success : function(response) {
		//var bin = atob(response);
		window.open("data:application/pdf;base64," + response);


		
  		
  	}

  });

  //printJS({printable: base64, type: 'pdf', base64: true});

  
  
  	//window.location.href="./viewPrintPdfPage?so_number="+so_number+"&locType="+locType+"&issueType="+issueType+"&issue="+issue+"&circle="+circle+"&division="+division+"&sdoCode="+sdoCode+"&arrayVal="+arrayVal+"&tosection="+tosection+"&userName="+userName;

}
</script>



<script>
function showCircle(zone) {
	$
			.ajax({
				url : './getCircleByZone',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zone : zone
				},
				success : function(response) {
					var html = '';
					html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#LFcircle").html(html);
					$('#LFcircle').select2();
				}
			});
} 


function showTownNameandCode(circle){
	var zone = $('#LFzone').val();
	//var zone =  '${newRegionName}';   
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
                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  onchange='showResultsbasedOntownCode(this.value)' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
               
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





var Base64 = {

		// private property
		_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

		// public method for encoding
		encode : function (input) {
		    var output = "";
		    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		    var i = 0;

		    input = Base64._utf8_encode(input);

		    while (i < input.length) {

		        chr1 = input.charCodeAt(i++);
		        chr2 = input.charCodeAt(i++);
		        chr3 = input.charCodeAt(i++);

		        enc1 = chr1 >> 2;
		        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		        enc4 = chr3 & 63;

		        if (isNaN(chr2)) {
		            enc3 = enc4 = 64;
		        } else if (isNaN(chr3)) {
		            enc4 = 64;
		        }

		        output = output +
		        this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
		        this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

		    }

		    return output;
		},

		// public method for decoding
		decode : function (input) {
		    var output = "";
		    var chr1, chr2, chr3;
		    var enc1, enc2, enc3, enc4;
		    var i = 0;

		    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		    while (i < input.length) {

		        enc1 = this._keyStr.indexOf(input.charAt(i++));
		        enc2 = this._keyStr.indexOf(input.charAt(i++));
		        enc3 = this._keyStr.indexOf(input.charAt(i++));
		        enc4 = this._keyStr.indexOf(input.charAt(i++));

		        chr1 = (enc1 << 2) | (enc2 >> 4);
		        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
		        chr3 = ((enc3 & 3) << 6) | enc4;

		        output = output + String.fromCharCode(chr1);

		        if (enc3 != 64) {
		            output = output + String.fromCharCode(chr2);
		        }
		        if (enc4 != 64) {
		            output = output + String.fromCharCode(chr3);
		        }

		    }

		    output = Base64._utf8_decode(output);

		    return output;

		},

		// private method for UTF-8 encoding
		_utf8_encode : function (string) {
		    string = string.replace(/\r\n/g,"\n");
		    var utftext = "";

		    for (var n = 0; n < string.length; n++) {

		        var c = string.charCodeAt(n);

		        if (c < 128) {
		            utftext += String.fromCharCode(c);
		        }
		        else if((c > 127) && (c < 2048)) {
		            utftext += String.fromCharCode((c >> 6) | 192);
		            utftext += String.fromCharCode((c & 63) | 128);
		        }
		        else {
		            utftext += String.fromCharCode((c >> 12) | 224);
		            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
		            utftext += String.fromCharCode((c & 63) | 128);
		        }

		    }

		    return utftext;
		},

		// private method for UTF-8 decoding
		_utf8_decode : function (utftext) {
		    var string = "";
		    var i = 0;
		    var c = c1 = c2 = 0;

		    while ( i < utftext.length ) {

		        c = utftext.charCodeAt(i);

		        if (c < 128) {
		            string += String.fromCharCode(c);
		            i++;
		        }
		        else if((c > 191) && (c < 224)) {
		            c2 = utftext.charCodeAt(i+1);
		            string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
		            i += 2;
		        }
		        else {
		            c2 = utftext.charCodeAt(i+1);
		            c3 = utftext.charCodeAt(i+2);
		            string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
		            i += 3;
		        }

		    }

		    return string;
		}

		}


</script>