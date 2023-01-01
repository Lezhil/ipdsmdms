
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>


<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		// $("#rdngMonth").val(getPresentMonthDate('${selectedMonth}'));
		 
		 
    		$('#newConnectionId').addClass('start active ,selected');
    		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#other-Reports,#MIS-Reports").removeClass('start active ,selected');
    		 
		
	});
</script>

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

<div class="page-content">

	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>Tamper Entry
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		</div>
		
		<div class="portlet-body">
		
		<c:if test = "${tampSuccess eq 'Tamper Data Saved Successfully...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:green" ><b>${tampSuccess}</b></span>
						</div>
			        </c:if>
			        
			<div class="row" style="margin-left: 1px;">



				<form action="" id="tamperEntryformId" method="POST">

					<div class="row">
						<div class="form-body">
							<div class="form-group">
								<div class="portlet-body">

									<table>

										<tr>
											<td style="padding-left: 20px;">RDNGMONTH</td>
											<td>
												<div data-date-viewmode="years" data-date-format="yyyymm"
													class="input-group input-medium date date-picker">
													<input type="text" name="rdngMonth" id="rdngMonth" value="${readingMonth}"
														readonly="readonly" class="form-control"
														required="required"> <span class="input-group-btn">
														<button type="button" class="btn default">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</td>


										<td style="padding-left: 20px;">MeterNo</td>
										<td>
											<input type="text" name="meterNo" id="meterNo" maxlength="8" placeholder="Enter MeterNo"
											 class="form-control" required="required" onchange="meterNoCheck();"> 
										</td>
										</tr>
										
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>

                                     <tr>
										<td style="padding-left: 20px;">Tamper Type</td>
											<td><select id="tamperType" name="tamperType" class="form-control input-medium">
													<option value="0">Select Tamper Type</option>
													<c:forEach items="${tamperType}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach>

											</select></td>
											
											<td style="padding-left: 20px;">Occurred Date</td>
											<td>
												<div data-date-viewmode="years" data-date-format="yyyy-mm-dd"
													class="input-group input-medium date date-picker">
													<input type="text" name="occurredDate" id="occurredDate" 
													value="${occurredDate}" class="form-control"
														required="required"> <span class="input-group-btn">
														<button type="button" class="btn default">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</td>

										
										</tr>
										
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										<tr><td>   </td> </tr>
										 <tr>
										 
										 
										<td style="padding-left: 20px;">Current Status</td>
											<td><select id="currentStatus" name="currentStatus" class="form-control input-medium">
													<option value="0">Select Current Status</option>
													<option value="Restored">Restored</option>
													<option value="Not Restored">Not Restored</option>
											</select></td>
											
											 <td style="padding-left: 20px;">Restored Date</td>
											<td>
												<div data-date-viewmode="years" data-date-format="yyyy-mm-dd"
													class="input-group input-medium date date-picker">
													<input type="text" name="restoredDate" id="restoredDate"
													class="form-control"> <span class="input-group-btn">
														<button type="button" class="btn default">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</td>
											
											
											<td><button type="submit" class="btn btn-success" id="htPending" style="margin-left: 50%;"
													onclick="return saveTamperData();">Save
												</button></td>
										</tr>

									</table>
								</div>

							</div>
						</div>
					</div>
				</form>
							</div>
						</div>
						
					<!-- Tamper data -->
					
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> Tamper Data Summary
							
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
								
								<tr>
									    
										<th>CIRCLE</th>
										<th>NOI</th>
										
										
									</tr>
								</thead>
								<tbody>
							
								<c:forEach var="element" items="${tamperData}">
								<tr>
									<td>${element[0]}</td>
									<td><a href="./searchTamperViewData?circle=${element[0]}"><c:out value="${element[1]}"></c:out></a></td>	
									
								</tr>  
									
								</c:forEach>
								
								</tbody>
							</table>
						</div>
					
					
						
	<!-- ---------------------- Tamper Details -------------------------------	 -->
	
							 <c:if test = "${tampDetailsError eq 'Tamper Details Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${tampDetailsError}</span>
						</div>
			        </c:if>
					
					
					</div>
					 <c:if test="${ tampDataShow eq 'tampDataShow'}">
					 
	<div id="sectDashDetails">	
				<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Tamper Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body"  style="overflow: auto; height: 350px;">
					<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_2', 'MRwise RNA Details')">Export to Excel</a></li> -->
							</ul>
						</div>
					<div class="table-scrollable">
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr style="background-color: lightgray;text-align: center;">
								<th>SL NO.</th>
								<th>RDNGMONTH</th>
								<th>CIRCLE</th>
								<th>SDONAME</th> 
								<th>TADESC</th>
								<th>ACCNO</th>
								<th>METERNO</th>
								<th>NAME</th> 
								<th>TAMPERTYPE</th> 
								<th>OCCURRED_DATE</th>
								<th>RESTORED_DATE</th>
								<th>MNP</th> 
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="1" scope="page"/>
							<c:forEach items="${tamperDetails}" var="element">
							     <tr>
									<td>${count}</td>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
					                
								</tr>
							<c:set var="count" value="${count + 1}" scope="page"/>		
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</div>
			</div>
		</c:if> 
		
		</div>
		
	
		
		
<script>
	function saveTamperData()
	{
		var meterNo=$("#meterNo").val();
		var tamperType=$("#tamperType").val();	
		var occurredDate=$("#occurredDate").val();
		var restoredDate=$("#restoredDate").val();
		var currentStatus=$("#currentStatus").val();
		
		
	if (meterNo == "") {
			bootbox.alert("Please Enter Meter No");
			return false;
		}
		if (tamperType == "0") {
			bootbox.alert("Please Select TamperType");
			return false;
		}

		if (occurredDate == "") {
			bootbox.alert("Please Select Occurred Date");
			return false;
		}

		
		if(currentStatus=="Restored")
			{
				if (restoredDate == "") 
				{
					bootbox.alert("Please Select Restored Date");
					return false;
				}
			}

		if (currentStatus == "0") {
			bootbox.alert("Please Select Current Status");
			return false;
		}
		
		$("#tamperEntryformId").attr("action","./saveTamperRecords");
		

	}
	
	
function meterNoCheck()
    {
	var rdngMonth=$("#rdngMonth").val();
	var meterNo=$("#meterNo").val();
	//alert("rdngMonth====="+rdngMonth+"meterNo==="+meterNo);
	$.ajax({
		type : "POST",
		url :"./checkMeternoExistForTamper",
		data :{
			rdngMonth:rdngMonth,meterNo:meterNo
		},
		
		 	success : function(response){
		 		//alert(response);
		 		
		 	if(response!="0")
		 	{
		 		swal({
		            title:  "Entered MeterNo Already Exist",
		            text: "",
		           
		        });
		 		$("#meterNo").val("");
		 	}
		 	}
		
	});
	
	}
</script>