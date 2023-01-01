<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "java.util.ResourceBundle" %>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>"
	type="text/javascript"></script>
<style>
.test {
	background-color: red;
}

.test2 {
	background-color: green;
}
.resizedTextbox {width: 500px; height: 20px}
</style>

<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						TableManaged.init();
						//loadSearchAndFilter('sample_3');  
						FormComponents.init();
						UIDatepaginator.init();
						
						$("#locType").val("").trigger("change");
						$("#mtrno").val("").trigger("change");
						$("#locId").val("").trigger("change");
						/*   $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}')); */
						/* $('#MDMSideBarContents,#serviceOrderManagementID,#serviceOrderSummary').addClass(
								'start active ,selected'); */

								$('#MDMSideBarContents,#serviceOrderManagementID,#serviceOrderSummary').addClass(
								'start active ,selected');
								
						$(
								"#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports")
								.removeClass('start active ,selected');

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
                        html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubdiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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
                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
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

	function mtrDetails(mtrNo) {
		window.location.href = "./mtrNoDetailsMDAS?mtrno=" + mtrNo;
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

	<div class="portlet box blue ">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Service Order Summary
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<!-- <div class="tab-content">
					<div id="tab_1-1" class="tab-pane active"> -->
		<%-- <form role="form" class="form-horizontal" action=" " method="post"> --%>
		
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
				<div id="locIdTd" class="form-group">
					<label class="control-label">Location ID:</label> <input
						class="form-control select2me" id="locId"
						name="locType">
						
					</input>
				</div>
			</div>
			
			<div class="col-md-3">
				<div id="mtrTd" class="form-group">
					<label class="control-label">Meter Number:</label> <input
						class="form-control select2me" id="mtrno"
						name="mtrno">
						
					</input>
				</div>
			</div>
			</div>
			
			
			<div class="row" style="margin-left: -1px;">
			
			<div class="col-md-3">
				<div id="soPendingTd" class="form-group">
					<label class="control-label">SO Pending Days:</label> <input
						class="form-control select2me" id="soPending"
						name="locType">
						
					</input>
				</div>
			</div>
			
			</div>
			
		
		<%-- <div class="portlet-body form">
			<form id="dailyAvgLoadId" >
				<table>
					<tbody>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
										<tr>
							<th class="block">Circle&nbsp;<font color="red">*</font>:</th>
							<th id="circleTd"><select
								class="form-control select2me input-medium" id="circle"
								name="circle" onchange="showDivision(this.value);">
									<option value="">Select</option>
							 		<option value="%">ALL</option>
							<c:forEach items="${circles}" var="elements">
								<option value="${elements}">${elements}</option>
							</c:forEach>
							</select></th>
							
							<th class="block">Division&nbsp;<font color="red">*</font>:</th>
							<th id="divisionTd"><select
								class="form-control select2me input-medium" id="division"
								name="division" onchange="showSubdivByDiv(this.value);">
								<option value="">Select</option>
								<option value="%">ALL</option>
							</select></th>
							
							<th class="block">Sub&nbsp;Division&nbsp;<font color="red">*</font>:</th>
							<th id="subdivTd"><select
								class="form-control select2me input-medium" id="sdoCode"
								name="sdoCode" onchange="showTownBySubdiv(this.value);">
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
						<th class="block">Town&nbsp;<font color="red">*</font>:</th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th>
							<th class="block">Location&nbsp;Type&nbsp;:</th>
							<th id="locTypeTd"><select
								class="form-control select2me input-medium" id="locType"
								name="locType">
								<option value="ALL">Select</option>
											<option value="BOUNDARY METER">BOUNDARY METER</option>
											<option value="DT">DT</option>
											<option value="FEEDER METER">FEEDER METER</option>
							</select></th>
							<th class="block">Location&nbsp;Id&nbsp;:</th>
							<th id="locIdTd"><input type="text"
								class="form-control" id="locId"
								name="locId">
							</th>
							</tr>
						<tr>
							<th colspan="8"></th>
						</tr>
						<tr>
							<th colspan="8"></th>
						</tr>


						<tr>
								<th class="block">Meter&nbsp;Number&nbsp;:</th>
							<th id="mtrTd">
								<input type="text" class="form-control " id="mtrno"
								name="mtrno">
							</th>

						
							<th class="block">SO&nbsp;Pending&nbsp;Duration&nbsp;:</th>
							<th id="soPendingTd">
							<input type="text" 
								class="form-control " id="soPending"
								name="soPending">(Days)
							</th>
							
						</tr>
						
						

					
					</tbody>
				</table> --%>
				<div class="modal-footer">

					<button type="button" onclick="return viewServiceSummary();" class="btn blue pull-left" style="margin-left: 422px;">View
						</button>
				
				</div>


		



		
		
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					</div>
		
		<div class="portlet-body ">

			<div class="table-toolbar">
				<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2">
		
						
					</div>
				</div>
			</div>
			
			<div class="btn-group pull-right">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">

											<li><a href="#" id="exportExcel"
												onclick="tableToExcel('sample_1','Service Order Summary');">Export
													to Excel</a></li>
										</ul>
									</div>
			<table class="table  table-bordered table-hover" id="sample_1">

				<thead>
					<tr>
						<th>SubDivison</th>
						<th>SO Number</th>
						<th>SO Date</th>
						<th>Issue Type</th>
						<th>Issue</th>
						<th>SO Status</th>
						<th>SO Update Date</th>
						<th>Meter Sr Number</th>
					</tr>
				</thead>
				<tbody id="serviceSumm">
				</tbody>
			</table>
		</div>


	
	</div>
<div class="modal fade" id="wide" tabindex="-1" role="basic" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"></h4>
										</div>
										<div class="modal-body">
											<div class="row">
										<label  class="col-md-3 control-label">Status</label>
										<div class="col-md-9">
											<select  class="form-control">
												
											</select>
										</div>
									</div>
									<div class="row">
									
										<label  class="col-md-3 control-label">Reopen Remark</label>
										<div class="col-md-9">
											<input type="text" class="form-control"  placeholder="Enter text">
											<!-- <span class="help-block">A block of help text.</span> -->
										</div>
									</div>
									<div class="row">
									
										<label  class="col-md-3 control-label">Corrective Action</label>
										<div class="col-md-9">
										<textarea class="form-control" rows="3"></textarea></div>
									</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
											<button type="button" class="btn blue">Save changes</button>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>						
	

</div>
<script type="text/javascript">

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


function viewServiceSummary()
{
	//alert("hiii");
	 var zone=$("#LFzone").val();
	 var circle=$("#LFcircle").val();
	 var town=$("#LFtown").val();
     var locType=$("#locType").val();
     var locId=$("#locId").val();
     var mtrno =$("#mtrno").val();
     var soPending=$("#soPending").val();
		var location_code;
		var meterno;
		var location_type;
		if(zone==null || zone=="")
		{
			bootbox.alert("Enter  Region");
			return false;
		}

		if(circle==null || circle=="")
		{
			bootbox.alert("Enter circle.");
			return false;
		}
		if(town==null || town=="")
	       {
	       	bootbox.alert("Enter town.");
	    	return false;
	       }
		
     if(locId==null || locId=="")
     {
    	 location_code='%';
     }else{
    	 location_code=locId;
         }

     if(mtrno==null || mtrno=="")
     {
    	 meterno='%';
     }else{
    	 meterno=mtrno;
         }

     if(locType==null || locType=="")
     {
    	 location_type='%';
     }else{
    	 location_type=locType;
         }

		
		
		 /*  if(soPending==null || soPending=="")
		{
			bootbox.alert("Enter SO Pending Duration.");
			return false;
		}   */
		
	$('#imageee').show();
		
     			$.ajax({
		               url : "./getServiceSummaryDetails",
		               type : 'GET',
		               dataType : 'json',
		               asynch : false,
		               cache : false,
		               data:{
		            	   circle : circle,
		            	    zone:zone,
		            	   town : town,
		            	   locType : location_type,
		            	   locId : location_code,
		            	   mtrno : meterno,
		            	   soPending : soPending
		                   },
		               success : function(response) {
		            	   $('#imageee').hide();
		            	   var html="";
		      				
			               if(response.length>0){
			       			
		      				for(var i=0;i<response.length;i++)
		      				{
			      				var updatedby;
		      	               var resp=response[i];
		      	               if(resp[5] == null)
			      	               {
		      	            	 updatedby = "";
			      	               }
		      	               else
			      	               {
			      	               updatedby = moment(resp[5]).format('DD-MM-YYYY HH:mm:ss');
			      	               }
		      	               html += "<tr>"
		      						+ "<td>"+(resp[7]==null?"":resp[7])+"</td>"
		      						+ "<td>"+(resp[0]==null?"":resp[0])+"</td>"
		      						+ "<td>"+((moment(resp[1]).format('DD-MM-YYYY HH:mm:ss'))==null?"":(moment(resp[1]).format('DD-MM-YYYY HH:mm:ss')))+"</td>"
		      						+ "<td>"+ (resp[3]==null?"":resp[3])+"</td>"
		      						+ "<td>"+ (resp[3]==null?"":resp[2]) +"</td>"
		      						+ "<td>"+ (resp[4]==null?"":resp[4])+"</td>"
		      						+ "<td>"+updatedby+"</td>"
		      						+ "<td>"+ (resp[6]==null?"":resp[6])+"</td>"
		      						+ "</tr>";
		      				}
		      				
				       		}
				       		else{
				       			bootbox.alert("Oops!!! No data available.");
				       			}		
		      				
		      				 $('#sample_1').dataTable().fnClearTable();
		      				$('#serviceSumm').html(html);
		      				loadSearchAndFilter('sample_1'); 
		      			
		      		},
		      		complete: function()
		      		{  
		      			loadSearchAndFilter('sample_1'); 
		      		} 
		
		
		           });
			

}



</script>

