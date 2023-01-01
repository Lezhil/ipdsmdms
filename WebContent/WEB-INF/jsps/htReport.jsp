
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 //$("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
		 
		  
		 $('#MIS-Reports').addClass('start active ,selected');
		 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
    	 $("#htPendindPrintData").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});
    	 
    	 $('#circle').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	    //alert(selectedItem);
	   	    if(selectedItem==null)
	   	    {
	   	    	//alert('if');
	   	    	$("#circle").val("0").trigger("change"); 	 
	   	    }else{
	   	    	//alert('else');
	   	    	$("#circle").val(selectedItem).trigger("change");
	   	    }
		 
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
				<i class="fa fa-edit"></i>HT Reading Report
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: 1px;">


				<form action="" id="htreadingformId">
					<div class="row">
						<div class="form-body">
							<div class="form-group">
								<div class="portlet-body">

									<table>

										<tr>
											<td>RDNGMONTH</td>
											<td>
												<div data-date-viewmode="years" data-date-format="yyyymm"
													class="input-group input-medium date date-picker">
													<input type="text" name="reportsMonth" id="reportsMonth" value="${readingMonth}"
														readonly="readonly" class="form-control"
														required="required"> <span class="input-group-btn">
														<button type="button" class="btn default">
															<i class="fa fa-calendar"></i>
														</button>
													</span>
												</div>
											</td>

											<td style="padding-left: 20px;">CIRCLE</td>
											<td><select id="circle" name="circle" class="form-control input-medium">
													<option value="0">Select Circle</option>
													<c:forEach items="${circle}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach>

											</select></td>

											<td><button type="submit" class="btn btn-success" id="htPending" style="margin-left: 50%;"
													onclick="return showhthtReadingData();">Show
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
						
						
						
			</div>
			
				<c:if test="${showhtReading eq 'showhtReading'}">
				
	<div class="portlet box blue">
	
	<c:if test = "${htReadingerror eq 'HT Reading Data Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${htReadingerror}</span>
						</div>
			        </c:if>
			        
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> HT Reading Details of ${selectedMonth}
							
						  <!-- <a href="#" id="excelExport" class="btn green" style="margin-left: 550px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a> -->
						  <button class="btn btn-success" type="button" onclick="exportToExcelMethod();">Export to Excel</button>
				       <a href="#" id="htPendindPrintData" class="btn green"><font size="2" color="white">PRINT</font></a><img alt="" src="resources/assets/img/print1.jpg" style="height: 35px; width: 35px;"></img>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									    <th>RDNGMONTH</th>
										<th>CIRCLE</th>
										<th>SDONAME</th>
										<th>TADESC</th>
										<th>ACCNO</th>
										<th>KNO</th>
										<th>METERNO</th>
										<th>KWH</th>
										<th>KVAH</th>
										<th>KVA</th>
										<th>NAME</th>
										
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${htlList}">
								<tr>
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
					</c:if>
					
					
		</div>
		
<script>

	function showhthtReadingData() {
		
		var rdngMonth = $("#reportsMonth").val();
		var circle = $("#circle").val();

		if (circle == "0") {
			bootbox.alert("Please Select Circle");
			return false;
		} else {
			$("#htreadingformId").attr("action", "./htReadingDetails");
			 

		}

	}
	
	function exportToExcelMethod()
	{
		var rdngMonth = $("#reportsMonth").val();
		var circle = $("#circle").val();
	//	alert(rdngMonth);
		//alert(circle);
		if (circle == "0") {
			bootbox.alert("Please Select Circle");
			return false;
		} else {
			 window.open("./htReadingExportToExcel/" +rdngMonth+ "/" + circle);
			

		}
	}
</script>
