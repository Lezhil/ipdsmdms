<%@page import="com.crystaldecisions.sdk.occa.report.data.Alert"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script src="<c:url value='resources/assets/scripts/form-wizard.js'/>" type="text/javascript"></script> 
 <script  type="text/javascript">
  
  $(".page-content").ready(function()
   	    	   {     
	  $("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
	  $("#meterno").val('${meterno}');
	 var data="${result}";
	 if(data=="nodata")
		 {
		//$("#ALLtabs").hide();
		 }
	 else if(data=="data")
	 {
	
	$("#loadingmessage-tabs").hide();
	 }
		//$("#loadingmessage-tabs").hide();
	  
	
	   App.init();
   	    	   	 FormDropzone.init();
   	    		 FormComponents.init();
   	    	   $('#mobileDataComparision').addClass('start active ,selected');
   	    	   $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#seal-Manager,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	
   	    	   });
  
  //window.onload = setInterval("getParsedXml()", 5000);
 
  
  
  
  
  </script>
  
<div class="page-content" >
	<!-- BEGIN PAGE CONTENT-->
	<c:if test = "${not empty meterno}"> 	
	<!-- <script>
	alert(${meterno});
	var mtr=${meterno};
	$("#meterno").val(mtr);
	document.getElementById("tabs").style.display = "block"; 
	</script> -->
			        
			      <%--   <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
							</div> --%>
			        </c:if>
			        <%-- 	<c:if test = "${dataPresent ne 'notDisplay'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${result}</span>
							</div>
			        </c:if> --%>
			 <div class="alert alert-success display-show" id="alertMsg" style="display: none;">
							<button class="close" data-close="alert"></button>
							 <span style="color:green" id="parseCompleteMsg"></span> 
				</div>
			<!-- END PAGE CONTENT-->
<div class="clearfix"></div>
<div class="row">
<div class="col-md-6">
					<!-- BEGIN PROGRESS BARS PORTLET-->
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>MOBILE and CMRI XML Comparison 
						
					</div>
					
				</div>
				<div class="portlet-body" style="overflow: auto; height: 150px;">

					<form action="./getMobileAllData" id="uploadFile"
						enctype="multipart/form-data" method="post">
						Select BillMonth :
						<div data-date-viewmode="years" data-date-format="yyyymm"
							class="input-group input-medium date date-picker">
							<input type="text" name="billmonth" id="billmonth"
								class="form-control" required="required" readonly="readonly">
							<span class="input-group-btn">
								<button type="button" class="btn default">
									<i class="fa fa-calendar"></i>
								</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</span>
						</div>
						<br />
						<div class="form-group">
							<label for="meternoLa">MeterNo:</label> 
							<input type="text" id="meterno" name="meterno"><br />
							
							<!-- <div class="col-md-3">
					<span>
					<button type="submit" id="dataview" class="btn green" style="margin-left: 223px;margin-bottom: -1px;margin-top: -57px;" onclick="return validation()">View Data</button>	</span>																					
											<br>
					</div> -->
					
					
						</div>
					</form>

				</div>

			</div>
			 	
					</div>
					
					<!--data alerady parsed status -->
					<div class="col-md-6">
					
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs"></i>Meter Parsed Status 
						
					</div>
					
				</div>
				<div class="portlet-body" style="overflow: auto; height: 150px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>BILLMONTH</th>
											<th>METERNO</th>
											<th>STATUS</th>
											<th>CREATED DATE</th>
											
											
											</tr>
											</thead>
											<tbody id="mtrStatus">
												<c:forEach var='meter' items='${getMobileStatus}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>								 		
											 	
											 	<td><c:out value="${meter[0]}"/></td>
											 	<td><c:out value="${meter[1]}"/></td>
											 	<td><c:out value="${meter[2]}"/></td>
											 	<td><c:out value="${meter[3]}"/></td>
											 	</tr>
											 	
											 	</c:forEach>
											 	</tbody>			
												</table>
				</div>

			</div>
			 	
					</div>
					<!-- END PROGRESS BARS PORTLET-->
			</div>					

         
				<div class="row"  >
				<div class="form-body" id="ALLtabs" style="margin-left: 7px;">
				
									
												<button class="btn blue" onclick="return d1datafun();">D1-DATA View</button> 
												<button class="btn blue" onclick="return d2datafun();">D2-DATA View</button>
												<button class="btn blue" onclick="return d3datafun();">D3-DATA View</button>
												<button class="btn blue" onclick="return d4datafun();">D4-DATA View</button>  
												<button class="btn blue" onclick="return d4Loaddatafun();">D4-LOAD-DATA View</button>
												<button class="btn blue" onclick="return d5datafun();">D5_DATA View</button> 
										
											
											
							</div>
							<!-- <div id='loadingmessage-tabs' style='display:none'>
  									
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=250px;  height=130px;>
								</div> -->
											
				
				
				<div class="col-md-6" id="d1tab" style="display: none">
				
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D1-DATA
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto; height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI</th>
											<th>MOBILE</th>
											
											
											</tr>
											</thead>
											<tbody id="D1_DATA">
												<%-- <c:forEach var='meter' items='${D1_dataMobileAndCMRI}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>								 		
											 	<td>METER_CLASS</td>
											 	<td><c:out value="${meter.METER_CLASS_CMRI}"/></td>
											 	<td><c:out value="${meter.METER_CLASS_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>METER_TYPE</td>
											 	<td><c:out value="${meter.METER_TYPE_CMRI}"/></td>
											 	<td><c:out value="${meter.METER_TYPE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>MANUFACTURER_CODE</td>
											 	<td><c:out value="${meter.MANUFACTURER_CODE_CMRI}"/></td>
											 	<td><c:out value="${meter.MANUFACTURER_CODE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>MANUFACTURER_NAME</td>
											 	<td><c:out value="${meter.MANUFACTURER_NAME_CMRI}"/></td>
											 	<td><c:out value="${meter.MANUFACTURER_NAME_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>METERDATE</td>
											 	<td><c:out value="${meter.METERDATE_CMRI}"/></td>
											 	<td><c:out value="${meter.METERDATE_MOBILE}"/></td>
											 	</tr>
											 	</c:forEach> --%>
											 	</tbody>			
												</table>
												
												<div id='loadingmessageD1table' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader"  width=150px;  height=130px;>
  									
								</div>
								<div id='d1mesg' >
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<span id="d1span" style="display: none; font-size: 20px; color: red; text-align: center; margin-left: 40%;">Data Not Found</span>
  									
								</div>
								
				</div>
				</div>
				
				
				
				
				
				</div>
				
				
				
				<div class="col-md-6" id="d2tab" style="display: none">
				
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
				<i class="fa fa-cogs"></i>D2-DATA
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI</th>
											<th>MOBILE</th>
											
											</tr>
											</thead>
												<tbody id="D2_DATA">
												<%-- <c:forEach var='meter' items='${D2_dataMobileAndCMRI}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>								 		
											 	<td>R_PHASE_VAL</td>
											 	<td><c:out value="${meter.R_PHASE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.R_PHASE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>Y_PHASE_VAL</td>
											 	<td><c:out value="${meter.Y_PHASE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.Y_PHASE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>B_PHASE_VAL</td>
											 	<td><c:out value="${meter.B_PHASE_VAL}"/></td>
											 	<td><c:out value="${meter.B_PHASE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>R_PHASE_LINE_VAL</td>
											 	<td><c:out value="${meter.R_PHASE_LINE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.R_PHASE_LINE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>Y_PHASE_LINE_VAL</td>
											 	<td><c:out value="${meter.Y_PHASE_LINE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.Y_PHASE_LINE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>B_PHASE_LINE_VAL</td>
											 	<td><c:out value="${meter.B_PHASE_LINE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.B_PHASE_LINE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>R_PHASE_ACTIVE_VAL</td>
											 	<td><c:out value="${meter.R_PHASE_ACTIVE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.R_PHASE_ACTIVE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>Y_PHASE_ACTIVE_VAL</td>
											 	<td><c:out value="${meter.Y_PHASE_ACTIVE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.Y_PHASE_ACTIVE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>B_PHASE_ACTIVE_VAL</td>
											 	<td><c:out value="${meter.B_PHASE_ACTIVE_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.B_PHASE_ACTIVE_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>R_PHASE_PF_VAL</td>
											 	<td><c:out value="${meter.R_PHASE_PF_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.R_PHASE_PF_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>Y_PHASE_PF_VAL</td>
											 	<td><c:out value="${meter.Y_PHASE_PF_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.Y_PHASE_PF_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>B_PHASE_PF_VAL</td>
											 	<td><c:out value="${meter.B_PHASE_PF_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.B_PHASE_PF_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>AVG_PF_VAL</td>
											 	<td><c:out value="${meter.AVG_PF_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.AVG_PF_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>ACTIVE_POWER_VAL</td>
											 	<td><c:out value="${meter.ACTIVE_POWER_VAL_CMRI}"/></td>
											 	<td><c:out value="${meter.ACTIVE_POWER_VAL_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>PHASE_SEQUENCE</td>
											 	<td><c:out value="${meter.PHASE_SEQUENCE_CMRI}"/></td>
											 	<td><c:out value="${meter.PHASE_SEQUENCE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>R_PHASE_CURRENT_ANGLE</td>
											 	<td><c:out value="${meter.R_PHASE_CURRENT_ANGLE_CMRI}"/></td>
											 	<td><c:out value="${meter.R_PHASE_CURRENT_ANGLE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>Y_PHASE_CURRENT_ANGLE</td>
											 	<td><c:out value="${meter.Y_PHASE_CURRENT_ANGLE_CMRI}"/></td>
											 	<td><c:out value="${meter.Y_PHASE_CURRENT_ANGLE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>B_PHASE_CURRENT_ANGLE</td>
											 	<td><c:out value="${meter.B_PHASE_CURRENT_ANGLE_CMRI}"/></td>
											 	<td><c:out value="${meter.B_PHASE_CURRENT_ANGLE_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>D2_KWH</td>
											 	<td><c:out value="${meter.D2_KWH_CMRI}"/></td>
											 	<td><c:out value="${meter.D2_KWH_MOBILE}"/></td>
											 	</tr>
										 	
										 </c:forEach>	 --%>
												</tbody>			
												</table>
												
												<div id='loadingmessageD2table' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader"  width=150px;  height=130px;>
  									
								</div>
								<div>
  									<span  id="d2span" style="color: red; font-size: 20px; text-align: center; margin-left: 40%; display: none;">Data Not Found</span>
  									</div>
				
				</div>
				</div>
				</div>
				<div class="col-md-6" id="d3tab" style="display: none">
				
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D3-DATA
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI</th>
											<th>MOBILE</th>
											
											</tr>
											</thead>
											<tbody id="D3_DATA">
												<%-- <c:forEach var='meter' items='${D3_dataMobileAndCMRI}'>
										 	<tr id="sampel" class="odd gradeX">	
										 	<td hidden="true"></td>								 		
											 	<td>D3_01_ENERGY</td>
											 	<td><c:out value="${meter.D3_01_ENERGY_CMRI}"/></td>
											 	<td><c:out value="${meter.D3_01_ENERGY_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>D3_02_ENERGY</td>
											 	<td><c:out value="${meter.D3_02_ENERGY_CMRI}"/></td>
											 	<td><c:out value="${meter.D3_02_ENERGY_MOBILE}"/></td>
											 	</tr>
											 	<tr>
											 	<td>D3_03_ENERGY</td>
											 	<td><c:out value="${meter.D3_03_ENERGY_CMRI}"/></td>
											 	<td><c:out value="${meter.D3_03_ENERGY_MOBILE}"/></td>
											 	</tr>
											 	</c:forEach> --%>
											 	</tbody>			
												</table>
												<div id='loadingmessageD3table' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
								</div>
								
								<div>
								<span id="d3span" style="display: none; margin-left:  40%; color: red; text-align: center; font-size: 20px;">Data Not Found</span>
								</div>
				</div>
				</div>
				</div>
				
				<div class="col-md-4" id="d4tab" style="display: none">
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D4-DATA
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>Day PRofile Date</th>
											
										<!-- 	<th>CMRI Count</th>
											<th>MOBILE Count</th> -->
											
											</tr>
											</thead>
											<tbody id="D4_DATA">
												<%-- <c:forEach var='meter' items='${D4_dataMobileAndCMRI}'>
										 	<tr>	
										 	<td hidden="true"></td>								 		
											 	<td id="d4date"> <a class='btn active'onclick="getDatad4('${cdf_idMobile}','${cdf_idCmri}','${meter[0]}')"><u style="color:blue">${meter[0]}</u></a></td>
											 	<td>${meter[2]}</td>
											 	<td>${meter[1]}</td>
											</tr>
											 	</c:forEach> --%>
											 	</tbody>			
												</table>
												<div id='loadingmessaged4Data' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
								</div>
								
								<div>
								<span id="spand4" style="display: none; margin-left: 40%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
								</div>
				</div>
				</div>
				</div>
				
				<!-- D4 day profile data -->
				<div class="col-md-6" id="d4tabDAY" style="display: none;"  >
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D4-DATA Based ON Day Profile Date :<span id="d4dateFieldtobeAppend"></span>
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI Count</th>
											<th>MOBILE Count</th>
											
											</tr>
											</thead>
											<tbody id="D4_DATA_ProfileDate">
												
											 	</tbody>			
												</table>
												<div id='loadingmessage-D4-LoadData' style='display:none'>
  									<!-- <img src="/resources/assets/img/loading.gif" /> -->
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
								</div>
								
								<div>
								<span id="d4span" style="display: none; margin-left: 40%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
								</div>
				</div>
				</div>
				</div>
				
				<!--d4-lOAD DATA  -->
				<div class="col-md-6" id="d4Loadtab" style="display:none;">
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D4-LOAD-DATA 
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 115px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>Day Profile Date</th>
											<th>IP Interval</th>
											<th>View</th>
											
											
											</tr>
											</thead>
											<tbody id="D4_LOAD-DATA">
										<%-- 	<td><select class="form-control select2me input-small" name="d4LoadDate" id="d4LoadDate" onchange="return getInterval('${cdf_idMobile}','${cdf_idCmri}',this.value) "  >
						 	   <option value="0">Select Day-ProfileDate</option>
								<c:forEach items="${D4_Load_dataMobileAndCMRI}" var="element">
								<option value="${element}">${element}</option>
								</c:forEach></select>
					   						</td>
											 	<td id="ipintervals">
					   							</td>
					   							<td id="view">
					   							<button class="btn green" type="button" id="viewbtn"   value="view" onclick="return getALLD4LoadData('${cdf_idMobile}','${cdf_idCmri}');">View</button>
					   							</td> --%>
											 	</tbody>			
												</table>
												
								<div id='loadd4dataImage' style='display:none'>
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=80px;  height=80px;>
								</div>
								
								<div>
									<span id="d4loadSpan" style="display: none; margin-left: 40%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
								</div>
				</div>
				</div>
				</div>
				
				
				
				<!-- D4 Load data day profile data -->
				<div class="col-md-6" id="d4LoadtabDAY" style="display:none;" >
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D4-LOAD - DATA Based IP Interval :<span id="d4IpIntervalAppend"></span>
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 200px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI Count</th>
											<th>MOBILE Count</th>
											
											</tr>
											</thead>
											<tbody id="D4_Load-DATA_ProfileDate">
												
											 	</tbody>			
												</table>
												<div id='loadD4ViewImg' style='display:none'>
  									<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
								</div>
								<div>
									<span id="loadviewSpan" style="display: none; margin-left: 40%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
								</div>
				</div>
				</div>
				</div>
				
				<div class="col-md-4" id="d5tab" style="display: none">
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D5-DATA
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
										<thead>
											<tr>
										
											<th>Event Date</th>
											<!-- <th>CMRI Count</th>
											<th>MOBILE Count</th> -->
											
											</tr>
											</thead>
										 	<tbody id="D5_DATA">
												<%-- <c:forEach var='meter' items='${D5_dataMobileAndCMRI}'>
										 	<tr>	
										 	<td hidden="true"></td>								 		
											 	<td id="d4date"> <a class='btn active'onclick="getData5('${cdf_idMobile}','${cdf_idCmri}','${meter[0]}')"><u style="color:blue">${meter[0]}</u></a></td>
											 	<td>${meter[2]}</td>
											 	<td>${meter[1]}</td>
											</tr>
											 	</c:forEach> --%>
											 	</tbody>	 	
												</table>
												
						<div id='loadingmessaged5_data' style='display:none'>
  						   		<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
						</div>
						<div>
								<span id="d5spanId" style="display: none; margin-left: 20%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
						</div>
												
				</div>
				</div>
				</div>
				
				<!-- D5 day profile data -->
				<div class="col-md-6" id="d5tabDAY" style="display: none">
				<div class="portlet box blue">
				<div class="portlet-title">
				<div class="caption">
						<i class="fa fa-cogs"></i>D5-DATA Based ON Day Event Date :<font color="yellow"><span id="dateFieldtobeAppend"></span></font>
						
					</div>
					</div>
					<div class="portlet-body" style="overflow: auto;height: 400px;">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_5">
										<thead>
											<tr>
										
											<th>ColName</th>
											<th>CMRI Count</th>
											<th>MOBILE Count</th>
											
											</tr>
											</thead>
											<tbody id="D5_DATA_ProfileDate">
												
											 	</tbody>			
												</table>
												<div id='loadingmessage-D5LoadData' style='display:none'>
  						   		<img src="resources/assets/img/home/ajax-loader(1).gif" class="ajax-loader" width=150px;  height=130px;>
						</div>
						<div>
								<span id="d5span" style="display: none; margin-left: 20%; font-size: 20px; color: red;text-align: center;">Data Not Found</span>
						</div>
				</div>
				</div>
				</div>
				</div>
				</div>
				
							
							
<style>						
#loadingmessage {
  height: 400px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.ajax-loader {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -32px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
#load {
  height: 500px;
  position: relative;
//  background-color: gray; /* for demonstration */
}
.wait{
  position: absolute;
  left: 50%;
  top: 60%;
  margin-left: -2px; /* -1 * image width / 2 */
  margin-top: -32px; /* -1 * image height / 2 */
}
							
</style>

<script type="text/javascript">



function validation()
{
var meterno=$("#meterno").val();

if(meterno=="")
{
bootbox.alert("please enter MeterNo");
return false;
	
}
$("#loadingmessage-tabs").show();
}

function d1datafun()
{
	//alert("calling d1");
$("#d1tab").show();
$("#d2tab").hide();
$("#d3tab").hide();
$("#d4tab").hide();
$("#d4tabDAY").hide();
$("#d4Loadtab").hide();
$("#d4LoadtabDAY").hide();
$("#d5tabDAY").hide();
$("#d5tab").hide();



var billmonth=$("#billmonth").val();
//alert(billmonth);
var meter=$("#meterno").val();
//alert(meter);
if(meter=="")
{
bootbox.alert("please enter MeterNo");
return false;
}
$("#D1_DATA").empty();
$("#loadingmessageD1table").show();
$.ajax({
	type : "GET",
	url : "./getALL_D1data",
	data:{billmonth:billmonth,meter:meter},
	
	cache:false,
	success : function(response)
	{
		if(response=="")
			{
			$("#loadingmessageD1table").hide();
			$("#d1span").show();
			}
		var dataNew=response;
		var htmlTable="";
		 $.each(dataNew, function(index, data)
				    { 
			 $("#d1span").hide();
			 $("#loadingmessageD1table").hide();
                       htmlTable+= "<tr>"
                    	+"<td>METER_CLASS</td>"
                    	+"<td>"+data.METER_CLASS_CMRI+"</td>"
						+"<td>"+data.METER_CLASS_MOBILE+"</td>"
						+"</tr>"
						+"<tr>"
						
						+"<td>METER_TYPE</td>"
                    	+"<td>"+data.METER_TYPE_CMRI+"</td>"
						+"<td>"+data.METER_TYPE_MOBILE+"</td>"
						+"</tr>"
						
						+"<td>MANUFACTURER_CODE</td>"
                    	+"<td>"+data.MANUFACTURER_CODE_CMRI+"</td>"
						+"<td>"+data.MANUFACTURER_CODE_MOBILE+"</td>"
						+"</tr>"
						
						

						+"<tr>"
						+"<td>MANUFACTURER_NAME</td>"
						+"<td>"+data.MANUFACTURER_NAME_CMRI+"</td>"
						+"<td>"+data.MANUFACTURER_NAME_MOBILE+"</td>"
						+"</tr>"
						
						
						+"<tr>"
						+"<td>METERDATE</td>"
						+"<td>"+data.METERDATE_CMRI+"</td>"
						+"<td>"+data.METERDATE_MOBILE+"</td>"
						+"</tr>"
			});
		 $("#D1_DATA").append(htmlTable); 
		}
	});
}

function d2datafun()
{
	//alert("calling d1");
$("#d1tab").hide();
$("#d2tab").show();
$("#d3tab").hide();
$("#d4tab").hide();
$("#d4tabDAY").hide();
$("#d4Loadtab").hide();
$("#d4LoadtabDAY").hide();
$("#d5tabDAY").hide();
$("#d5tab").hide();



var billmonth=$("#billmonth").val();
//alert(billmonth);
var meter=$("#meterno").val();
//alert(meter);
if(meter=="")
{
bootbox.alert("please enter MeterNo");
return false;
}
$("#D2_DATA").empty();
$("#loadingmessageD2table").show();

$.ajax({
	type : "GET",
	url : "./getALL_D2data",
	data:{billmonth:billmonth,meter:meter},
	cache:false,
	success : function(response)
	{
		
		//alert("response---"+response);
		if(response=="")
			{
			$("#loadingmessageD2table").hide();
			$("#d2span").show();
			}
		var dataNew=response;
		var htmlTable="";
		 $.each(dataNew, function(index, data)
				    { 
			 $("#d2span").hide();
			 $("#loadingmessageD2table").hide();
                       htmlTable+= "<tr>"
                    	   +"<td>R_PHASE_VAL</td>"
                       	+"<td>"+data.R_PHASE_VAL_CMRI+"</td>"
   						+"<td>"+data.R_PHASE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						+"<tr>"
   						
   						+"<td>Y_PHASE_VAL</td>"
                       	+"<td>"+data.Y_PHASE_VAL_CMRI+"</td>"
   						+"<td>"+data.Y_PHASE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<td>B_PHASE_VAL</td>"
                       	+"<td>"+data.B_PHASE_VAL_CMRI+"</td>"
   						+"<td>"+data.B_PHASE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						

   						+"<tr>"
   						+"<td>R_PHASE_LINE_VAL</td>"
   						+"<td>"+data.R_PHASE_LINE_VAL_CMRI+"</td>"
   						+"<td>"+data.R_PHASE_LINE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<tr>"
   						+"<td>Y_PHASE_LINE_VAL</td>"
   						+"<td>"+data.Y_PHASE_LINE_VAL_CMRI+"</td>"
   						+"<td>"+data.Y_PHASE_LINE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						
   						+"<td>B_PHASE_LINE_VAL</td>"
                       	+"<td>"+data.B_PHASE_LINE_VAL_CMRI+"</td>"
   						+"<td>"+data.B_PHASE_LINE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<td>R_PHASE_ACTIVE_VAL</td>"
                       	+"<td>"+data.R_PHASE_ACTIVE_VAL_CMRI+"</td>"
   						+"<td>"+data.R_PHASE_ACTIVE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						

   						+"<tr>"
   						+"<td>Y_PHASE_ACTIVE_VAL</td>"
   						+"<td>"+data.Y_PHASE_ACTIVE_VAL_CMRI+"</td>"
   						+"<td>"+data.Y_PHASE_ACTIVE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<tr>"
   						+"<td>B_PHASE_ACTIVE_VAL</td>"
   						+"<td>"+data.B_PHASE_ACTIVE_VAL_CMRI+"</td>"
   						+"<td>"+data.B_PHASE_ACTIVE_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<td>R_PHASE_PF_VAL</td>"
                       	+"<td>"+data.R_PHASE_PF_VAL_CMRI+"</td>"
   						+"<td>"+data.R_PHASE_PF_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<td>Y_PHASE_PF_VAL</td>"
                       	+"<td>"+data.Y_PHASE_PF_VAL_CMRI+"</td>"
   						+"<td>"+data.Y_PHASE_PF_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<tr>"
   						+"<td>B_PHASE_PF_VAL</td>"
   						+"<td>"+data.B_PHASE_PF_VAL_CMRI+"</td>"
   						+"<td>"+data.B_PHASE_PF_VAL_MOBILE+"</td>"
   						+"</tr>"
   						

   						+"<tr>"
   						+"<td>AVG_PF_VAL</td>"
   						+"<td>"+data.AVG_PF_VAL_CMRI+"</td>"
   						+"<td>"+data.AVG_PF_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<tr>"
   						+"<td>ACTIVE_POWER_VAL</td>"
   						+"<td>"+data.ACTIVE_POWER_VAL_CMRI+"</td>"
   						+"<td>"+data.ACTIVE_POWER_VAL_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<td>PHASE_SEQUENCE</td>"
                       	+"<td>"+data.PHASE_SEQUENCE_CMRI+"</td>"
   						+"<td>"+data.PHASE_SEQUENCE_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<td>R_PHASE_CURRENT_ANGLE</td>"
                       	+"<td>"+data.R_PHASE_CURRENT_ANGLE_CMRI+"</td>"
   						+"<td>"+data.R_PHASE_CURRENT_ANGLE_MOBILE+"</td>"
   						+"</tr>"
   						
   						

   						+"<tr>"
   						+"<td>Y_PHASE_CURRENT_ANGLE</td>"
   						+"<td>"+data.Y_PHASE_CURRENT_ANGLE_CMRI+"</td>"
   						+"<td>"+data.Y_PHASE_CURRENT_ANGLE_MOBILE+"</td>"
   						+"</tr>"
   						
   						
   						+"<tr>"
   						+"<td>B_PHASE_CURRENT_ANGLE</td>"
   						+"<td>"+data.B_PHASE_CURRENT_ANGLE_CMRI+"</td>"
   						+"<td>"+data.B_PHASE_CURRENT_ANGLE_MOBILE+"</td>"
   						+"</tr>"
   						
   						+"<tr>"
   						+"<td>D2_KWH</td>"
   						+"<td>"+data.D2_KWH_CMRI+"</td>"
   						+"<td>"+data.D2_KWH_MOBILE+"</td>"
   						+"</tr>"
   						
   						
			});
		 $("#D2_DATA").append(htmlTable); 
		}
	});
}

//D3-DATA
function d3datafun()
{
	//alert("calling d1");
$("#d1tab").hide();
$("#d2tab").hide();
$("#d3tab").show();
$("#d4tab").hide();
$("#d4tabDAY").hide();
$("#d4Loadtab").hide();
$("#d4LoadtabDAY").hide();
$("#d5tabDAY").hide();
$("#d5tab").hide();



var billmonth=$("#billmonth").val();
//alert(billmonth);
var meter=$("#meterno").val();
//alert(meter);
if(meter=="")
{
bootbox.alert("please enter MeterNo");
return false;
}
$("#D3_DATA").empty();
$("#loadingmessageD3table").show();
$.ajax({
	type : "GET",
	url : "./getALL_D3data",
	data:{billmonth:billmonth,meter:meter},
	cache:false,
	success : function(response)
	{
		//alert(response);
		
		if(response=="")
			{
			$("#loadingmessageD3table").hide();
			$("#d3span").show();
			}
		var dataNew=response;
		var htmlTable="";
		 $.each(dataNew, function(index, data)
				    { 
			 $("#d3span").hide();
			 $("#loadingmessageD3table").hide();
                       htmlTable+= "<tr>"
                    	+"<td>D3_01_ENERGY</td>"
                    	+"<td>"+data.D3_01_ENERGY_CMRI+"</td>"
						+"<td>"+data.D3_01_ENERGY_MOBILE+"</td>"
						+"</tr>"
						+"<tr>"
						
						+"<td>D3_02_ENERGY</td>"
                    	+"<td>"+data.D3_02_ENERGY_CMRI+"</td>"
						+"<td>"+data.D3_02_ENERGY_MOBILE+"</td>"
						+"</tr>"
						
						+"<td>D3_03_ENERGY</td>"
                    	+"<td>"+data.D3_03_ENERGY_CMRI+"</td>"
						+"<td>"+data.D3_03_ENERGY_MOBILE+"</td>"
						+"</tr>"
						
						

						
			});
		 $("#D3_DATA").append(htmlTable); 
		}
	});


}
function d4datafun()
{
	//alert("calling d1");
$("#d1tab").hide();
$("#d2tab").hide();
$("#d3tab").hide();
$("#d4tab").show();
$("#d4tabDAY").hide();
$("#d4Loadtab").hide();
$("#d4LoadtabDAY").hide();
$("#d5tabDAY").hide();
$("#d5tab").hide();

var billmonth=$("#billmonth").val();
//alert(billmonth);
var meter=$("#meterno").val();
//alert(meter);
if(meter=="")
{
bootbox.alert("please enter MeterNo");
return false;
}
$("#D4_DATA").empty();
$("#loadingmessaged4Data").show();
$.ajax({
	type : "GET",
	url : "./getALL_D4data",
	data:{billmonth:billmonth,meter:meter},
	cache:false,
	success : function(response)
	{
			
		if(response[2]=="")
			{
			$("#loadingmessaged4Data").hide();
			$("#spand4").show();
			}
		
		 var dataNew=response[2];
		var htmlTable="";
		 $.each(dataNew, function(index, data)
				    { 
			 $("#spand4").hide();
			 $("#loadingmessaged4Data").hide();
			    var cdfMobile=response[0];
			    var cdfCMRI=response[1];
			    var date=(data.Date_MOBILE)+'';
	
				   htmlTable+= "<tr >"
                 	  +"<td onclick='getDatad4("+cdfMobile+","+cdfCMRI+",\""+data.Date_MOBILE+"\");'><u>"+data.Date_MOBILE+" </u></td>" 
                 	/*  +"<td onclick=getDatad4("+cdfMobile+","+cdfCMRI+",\""+data.Date_MOBILE+"\");>"+data.Date_MOBILE+" </td>" */
                 //	+"<td onclick='getDatad4();'> "+date+" </td>"
                    	
						+"</tr>";
					
			});
		// alert(htmlTable);
		 $("#D4_DATA").append(htmlTable);  
		}
	});
	
}


function d4Loaddatafun()
{
	//alert("calling d1");
	$("#d1tab").hide();
	$("#d2tab").hide();
	$("#d3tab").hide();
	$("#d4tab").hide();
	$("#d4tabDAY").hide();
	$("#d4Loadtab").show();
	$("#d4LoadtabDAY").hide();
	$("#d5tabDAY").hide();
	$("#d5tab").hide();
	
	var billmonth=$("#billmonth").val();
	 //alert(billmonth);
	var meter=$("#meterno").val();
     //alert(meter);
	if(meter=="")
	{
	bootbox.alert("Please Enter MeterNo");
	return false;
    }
	
	
	 $("#D4_LOAD-DATA").empty(); 
	$("#loadd4dataImage").show();
	$.ajax({
		type : "GET",
		url : "./getALL_D4data",
		data:{billmonth:billmonth,meter:meter},
		cache:false,
		success : function(response)
		{
			/* alert("Response 3"+response[3]);
			alert("Response 3 Length---"+response[3].length); */
			
			$("#loadd4dataImage").hide();	
			var htmlTable="";
			var date="";
			
			
			var cdfMobile=response[0];
	    	var cdfCMRI=response[1];
	    	
	    	
			/* if(response[2]=="")
				{
				$("#loadingmessaged4Data").hide();
				$("#spand4").show();
				} */
			
			 var dataNew=response[2];
				
			//	alert("response[2].length----"+response[2].length);
				
				 
				/*  var propDate="";	
				var ipDate=""; */
				
				htmlTable+= "<tr >"
          		    +"<td><select class='form-control' name='d4LoadDate' id='d4LoadDate' style='width:180px;'>"
          		  +"<option value='0'>Select Day-ProfileDate</option>";
          		    
          		    for(var i=0;i<response[2].length;i++)
          		    	{
          		    		 propDate=response[2][i].Date_MOBILE;
          		    		 
          		    	htmlTable+="<option value="+response[2][i].Date_MOBILE+">"+response[2][i].Date_MOBILE+"</option>";
			                
          		    	}
          		  htmlTable+="</select></td>";
  			        
          		 htmlTable+= "<td><select class='form-control' name='d4IpDate' id='d4IpDate' style='width:150px;'>"
            		    +"<option value='0'>Select IP Interval</option>";
            		  
            		  
          		  for(var j=0;j<response[3].length;j++)
    		    	{
          			    ipDate=response[3][j].IP_Interval;
          			    
    		    	htmlTable+="<option value="+response[3][j].IP_Interval+">"+response[3][j].IP_Interval+"</option>";
		                
    		    	}
          		    
          		  
							
          		htmlTable+= "</select></td>" 
          		            +"<td id='view'><button class='btn green' type='button' id='viewbtn' value='view' onclick='return getALLD4LoadData("+cdfMobile+","+cdfCMRI+");'>View</button></td>"
			                +"</tr>"; 
          		    
			// alert(htmlTable);
			 $("#D4_LOAD-DATA").append(htmlTable);  
			}
		});
	
}


function d5datafun()
{
	$("#d1tab").hide();
	$("#d2tab").hide();
	$("#d3tab").hide();
	$("#d4tab").hide();
	$("#d4tabDAY").hide();
	$("#d4Loadtab").hide();
	$("#d4LoadtabDAY").hide();
	$("#d5tabDAY").hide();
	$("#d5tab").show();

	var billmonth=$("#billmonth").val();
		//alert(billmonth);
	var meter=$("#meterno").val();
		//alert(meter);
	if(meter=="")
	{
	bootbox.alert("Please Enter MeterNo");
	return false;
	}
	$("#D5_DATA").empty();
	$("#loadingmessaged5_data").show();

	
	$.ajax({
		type : "POST",
		url  : "./getAlld5Data",
	    data : {billmonth:billmonth, meter:meter},
	    success :function(response)
	    {
	    	//alert(response);
	    	//alert("response[0].length---"+response[0].length);
	    	//alert("response[0]------------"+response[0]);
	    	//alert("response[1]------------"+response[1]);
	    	//ert("response[2]------------"+response[2]);
	    	
	    	var cdfMobile=response[0];
	    	var cdfCMRI=response[1];
	    	if(response[2]=="")
	    		{
	    		 $("#d5spanId").show();
				 $("#loadingmessaged5_data").hide();
	    		}
	    	
			var htmlTable="";
			for(var i=0 ;i<response[2].length;i++)
			{ 
				 
				 $("#d5spanId").hide();
				 $("#loadingmessaged5_data").hide();
				 
				  var event_date=response[2][i].Event_Date;
				  
					    htmlTable+= "<tr >"
	                 	  +"<td onclick='getData5("+cdfMobile+","+cdfCMRI+",\""+event_date+"\");'><font style=color:blue><u>"+response[2][i].Event_Date+" </u></font></td>" 
	                 
							+"</tr>";
						
			}
			
			// alert(htmlTable);
			 $("#D5_DATA").append(htmlTable);  
			}
		
		  });

}





$("#D5_DATA_ProfileDate").html("");


//D4DATA
function getDatad4(mobile,cmri,dayProfileDate)
{
	
 //alert("cdf cmri==="+cmri);
//alert("dayProfileDate=="+dayProfileDate); 
 $("#dateFieldtobeAppend").val(dayProfileDate); 
	document.getElementById("d4dateFieldtobeAppend").innerHTML = dayProfileDate;
	// clearTabledataContent('sample_editable_5');
 	$("#d4tabDAY").show(); 
	$("#D4_DATA_ProfileDate").empty();
	$("#loadingmessage-D4-LoadData").show();
	$.ajax({
		type : "GET",
		url : "./getD4dataByDayProfile",
		data:{mobile:mobile,cmri:cmri,dayProfileDate:dayProfileDate},
		cache:false,
		success : function(response)
		
		{
			if(response==""){
				$("#loadingmessage-D4-LoadData").hide();
				$("#d4span").show();
			}
			
			
		var dataNew=response;
		var htmlTable="";
		
		 $.each(dataNew, function(index, data)
				    { 
			 $("#d4span").hide();
			 $("#loadingmessage-D4-LoadData").hide();
				   
                       htmlTable+= "<tr>"
                    	+"<td>MIN_KVA</td>"
                    	+"<td>"+data.MIN_KVA_CMRI+"</td>"
						+"<td>"+data.MIN_KVA_MOBILE+"</td>"
						+"</tr>"
						+"<tr>"
						
						+"<td>MAX_KVA</td>"
                    	+"<td>"+data.MAX_KVA_CMRI+"</td>"
						+"<td>"+data.MAX_KVA_MOBILE+"</td>"
						+"</tr>"
						
						+"<td>SUM_KWH</td>"
                    	+"<td>"+data.SUM_KWH_CMRI+"</td>"
						+"<td>"+data.SUM_KWH_MOBILE+"</td>"
						+"</tr>"
						
						

						+"<tr>"
						+"<td>SUM_PF</td>"
						+"<td>"+data.SUM_PF_CMRI+"</td>"
						+"<td>"+data.SUM_PF_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>SUM_KVA</td>"
						+"<td>"+data.SUM_KVA_CMRI+"</td>"
						+"<td>"+data.SUM_KVA_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>INTERVAL_PERIOD</td>"
						+"<td>"+data.INTERVAL_PERIOD_CMRI+"</td>"
						+"<td>"+data.INTERVAL_PERIOD_MOBILE+"</td>"
						+"</tr>"
						
						
						
						+"<tr>"
						+"<td>KWH_FLAG</td>"
						+"<td>"+data.KWH_FLAG_CMRI+"</td>"
						+"<td>"+data.KWH_FLAG_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_05</td>"
						+"<td>"+data.PF_05_CMRI+"</td>"
						+"<td>"+data.PF_05_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_05_07</td>"
						+"<td>"+data.PF_05_07_CMRI+"</td>"
						+"<td>"+data.PF_05_07_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_07_09</td>"
						+"<td>"+data.PF_07_09_CMRI+"</td>"
						+"<td>"+data.PF_07_09_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_09</td>"
						+"<td>"+data.PF_09_CMRI+"</td>"
						+"<td>"+data.PF_09_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_NOLOAD</td>"
						+"<td>"+data.PF_NOLOAD_CMRI+"</td>"
						+"<td>"+data.PF_NOLOAD_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PF_BLACKOUT</td>"
						+"<td>"+data.PF_BLACKOUT_CMRI+"</td>"
						+"<td>"+data.PF_BLACKOUT_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_GS_20</td>"
						+"<td>"+data.IP_GS_20_CMRI+"</td>"
						+"<td>"+data.IP_GS_20_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_GS_20_40</td>"
						+"<td>"+data.IP_GS_20_40_CMRI+"</td>"
						+"<td>"+data.IP_GS_20_40_MOBILE+"</td>"
						+"</tr>"

						+"<tr>"
						+"<td>IP_GS_40_60</td>"
						+"<td>"+data.IP_GS_40_60_CMRI+"</td>"
						+"<td>"+data.IP_GS_40_60_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_GS_60</td>"
						+"<td>"+data.IP_GS_60_CMRI+"</td>"
						+"<td>"+data.IP_GS_60_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_OUT_GS_20</td>"
						+"<td>"+data.IP_OUT_GS_20_CMRI+"</td>"
						+"<td>"+data.IP_OUT_GS_20_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_OUT_GS_20_40</td>"
						+"<td>"+data.IP_OUT_GS_20_40_CMRI+"</td>"
						+"<td>"+data.IP_OUT_GS_20_40_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_OUT_GS_40_60</td>"
						+"<td>"+data.IP_OUT_GS_40_60_CMRI+"</td>"
						+"<td>"+data.IP_OUT_GS_40_60_MOBILE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>IP_OUT_GS_60</td>"
						+"<td>"+data.IP_OUT_GS_60_CMRI+"</td>"
						+"<td>"+data.IP_OUT_GS_60_MOBILE+"</td>"
                    	+"</tr>"

						
				    });
		 //$("#d4tabDAY").empty();
		 $("#D4_DATA_ProfileDate").append(htmlTable); 
		}
	
 			
	
});
 
	}
	
	

function getInterval(mobile,cmri,dayProfileDate)
{
	//alert(mobile);
	//alert(cmri);
	//alert(dayProfileDate);

	$.ajax({
		type : "GET",
		url : "./getD4IPIntervalByDayProfile",
		data:{mobile:mobile,cmri:cmri,dayProfileDate:dayProfileDate},
		cache:false,
		success : function(response)
		{
			//alert(response);
			if(response != null)
    		{
    			
    			var html='<select class="form-control select2me input-small" name="interval" id="interval"  ><option value=0>Select IpInterval</option>';
    			 for( var i=0;i<response.length;i++)
    			 {
    				
    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
    			 }
    			 html+='</select>';
    			 $("#ipintervals").empty();
    			 $("#ipintervals").append(html);
			
		}
		}
	});
}


function getALLD4LoadData(mobile,cmri)
{
	//alert("mobile---"+mobile+"cmri---"+cmri);
	
	var pdate=$('#d4LoadDate').val();
	var interv=$('#d4IpDate').val();
	/* alert(maga1);
	alert(maga2); */
	
	/* var propDate=$('#d4LoadDate').val();
		var ipDate=$('#d4LoadDate').val(); */
		
	/* var pdate=$("#d4LoadDate").val();
	var interv=$("#interval").val(); */
	document.getElementById("d4IpIntervalAppend").innerHTML = interv;
	$("#d4LoadtabDAY").show();
	
	$("#loadD4ViewImg").show();
	$("#loadviewSpan").hide();
	$("#D4_Load-DATA_ProfileDate").empty();
 $.ajax({
			type : "GET",
			url : "./getD4LoadView",
			data:{pdate:pdate,interv:interv,mobile:mobile,cmri:cmri},
			cache:false,
			success : function(response)
			{
				
				//alert(response);
				if(response=="")
					{
					$("#loadD4ViewImg").hide();
					$("#loadviewSpan").show();
					}
				var dataNew=response;
				
				//$("#viewbtn").show();
				var htmlTable="";
				 $.each(dataNew, function(index, data)
						    { 
						  
					 $("#loadD4ViewImg").hide();
					 
		                       htmlTable+= "<tr>"
		                    	+"<td>KVAVALUE</td>"
		                    	+"<td>"+data.KVAVALUE_CMRI+"</td>"
								+"<td>"+data.KVAVALUE_MOBILE+"</td>"
								+"</tr>"
								+"<tr>"
								
								+"<td>KWHVALUE</td>"
		                    	+"<td>"+data.KWHVALUE_CMRI+"</td>"
								+"<td>"+data.KWHVALUE_MOBILE+"</td>"
								+"</tr>"
								
								+"<td>PFVALUE</td>"
		                    	+"<td>"+data.PFVALUE_CMRI+"</td>"
								+"<td>"+data.PFVALUE_MOBILE+"</td>"
								+"</tr>"
								
								
						    });
				// $("#D4_Load-DATA_ProfileDate").empty();
				 $("#D4_Load-DATA_ProfileDate").append(htmlTable); 
				}
			
		 			
			
		});

			}


/* function clearTabledataContent(tableid)
{
	 //TO CLEAR THE TABLE DATA
	var oSettings = $('#'+tableid).dataTable().fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	for (i = 0; i <= iTotalRecords; i++) 
	{
		$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
	} 
	
} */


function getData5(mobile,cmri,dayProfileDate)
{
	
	/* alert("mobile==="+mobile);
	alert("cmri==="+cmri);
	alert("dayProfileDate==="+dayProfileDate); */
	
	 $("#dateFieldtobeAppend").val(dayProfileDate); 
	$("#D5_DATA_ProfileDate").html("");
	document.getElementById("dateFieldtobeAppend").innerHTML = dayProfileDate;
	//clearTabledataContent('sample_editable_5');
	$("#d5tabDAY").show();
	 $("#loadingmessage-D5LoadData").show();
	 
	 $("#D5_DATA_ProfileDate").empty();
	$.ajax({
		type : "GET",
		url : "./getD5dataByDayProfile",
		data:{mobile:mobile,cmri:cmri,dayProfileDate:dayProfileDate},
		cache:false,
		success : function(response)
		{
			
			if(response==""){
				$("#loadingmessage-D5LoadData").hide();
				$("#d5span").show();
			}
			
		var dataNew=response;
		
		var htmlTable="";
		 $.each(dataNew, function(index, data)
				    { 
					 $("#loadingmessage").hide();
					 $("#loadingmessage-D5LoadData").hide();
					 $("#d5span").hide();
						
                       htmlTable+= "<tr>"
                    	+"<td>EVENT_CODE_OLD1</td>"
                    	+"<td>"+data.EVENT_CODE_OLD1+"</td>"
						+"<td>"+data.EVENT_CODE_OLD+"</td>"
						+"</tr>"
						+"<tr>"
						
						+"<td>EVENT_STATUS1</td>"
                    	+"<td>"+data.EVENT_STATUS1+"</td>"
						+"<td>"+data.EVENT_STATUS+"</td>"
						+"</tr>"
						
						+"<td>EVENT_CODE</td>"
                    	+"<td>"+data.EVENT_CODE1+"</td>"
						+"<td>"+data.EVENT_CODE+"</td>"
						+"</tr>"
						
						

						+"<tr>"
						+"<td>R_PHASE_VAL</td>"
						+"<td>"+data.R_PHASE_VAL1+"</td>"
						+"<td>"+data.R_PHASE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>Y_PHASE_VAL</td>"
						+"<td>"+data.Y_PHASE_VAL1+"</td>"
						+"<td>"+data.Y_PHASE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>B_PHASE_VAL</td>"
						+"<td>"+data.B_PHASE_VAL1+"</td>"
						+"<td>"+data.B_PHASE_VAL+"</td>"
						+"</tr>"
						
						
						
						+"<tr>"
						+"<td>R_PHASE_LINE_VAL</td>"
						+"<td>"+data.R_PHASE_LINE_VAL1+"</td>"
						+"<td>"+data.R_PHASE_LINE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>Y_PHASE_LINE_VAL</td>"
						+"<td>"+data.Y_PHASE_LINE_VAL1+"</td>"
						+"<td>"+data.Y_PHASE_LINE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>B_PHASE_LINE_VAL</td>"
						+"<td>"+data.B_PHASE_LINE_VAL1+"</td>"
						+"<td>"+data.B_PHASE_LINE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>R_PHASE_ACTIVE_VAL</td>"
						+"<td>"+data.R_PHASE_ACTIVE_VAL1+"</td>"
						+"<td>"+data.R_PHASE_ACTIVE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>Y_PHASE_ACTIVE_VAL</td>"
						+"<td>"+data.Y_PHASE_ACTIVE_VAL1+"</td>"
						+"<td>"+data.Y_PHASE_ACTIVE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>B_PHASE_ACTIVE_VAL</td>"
						+"<td>"+data.B_PHASE_ACTIVE_VAL1+"</td>"
						+"<td>"+data.B_PHASE_ACTIVE_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>R_PHASE_PF_VAL</td>"
						+"<td>"+data.R_PHASE_PF_VAL1+"</td>"
						+"<td>"+data.R_PHASE_PF_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>Y_PHASE_PF_VAL</td>"
						+"<td>"+data.Y_PHASE_PF_VAL1+"</td>"
						+"<td>"+data.Y_PHASE_PF_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>B_PHASE_PF_VAL</td>"
						+"<td>"+data.B_PHASE_PF_VAL1+"</td>"
						+"<td>"+data.B_PHASE_PF_VAL+"</td>"
						+"</tr>"

						+"<tr>"
						+"<td>AVG_PF_VAL</td>"
						+"<td>"+data.AVG_PF_VAL1+"</td>"
						+"<td>"+data.AVG_PF_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>ACTIVE_POWER_VAL</td>"
						+"<td>"+data.ACTIVE_POWER_VAL1+"</td>"
						+"<td>"+data.ACTIVE_POWER_VAL+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>PHASE_SEQUENCE</td>"
						+"<td>"+data.PHASE_SEQUENCE1+"</td>"
						+"<td>"+data.PHASE_SEQUENCE+"</td>"
						+"</tr>"
						
						+"<tr>"
						+"<td>D5_KWH</td>"
						+"<td>"+data.D5_KWH1+"</td>"
						+"<td>"+data.D5_KWH+"</td>"
						+"</tr>"
						
				    });
		
		 $("#D5_DATA_ProfileDate").append(htmlTable); 
		 
		}
 	});

}

</script>
<style>						
#loadingmessage {
  height: 400px;
  position: relative;
 /*  background-color: gray; /* for demonstration */ */
}
</style>
