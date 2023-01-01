
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 //$("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
		 
		  
			 $('#MDMSideBarContents,#MIS-Reports,#asciiReportHT').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
    	 $("#htPendindPrintData").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});
    	 
    	/*  $('#circle').change(function() { 
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
	   	    } */

	   	   
		 
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


				<form action="" id="htreadingformId" method="POST">
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
													<option value="%">ALL</option>
													<c:forEach items="${circle}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach>

											</select></td>

											<!-- <td><button type="submit" class="btn btn-success" id="htPending" value="12" data-toggle="tooltip" title="ASCII REPORT"
													onclick="return showhthtReadingData(this.value);">HT Reading
												</button></td>
												
												<td><button type="submit" class="btn btn-success" id="htPending2" value="13" data-toggle="tooltip" title="ASCII REPORT"
													onclick="return showhthtReadingData(this.value);">Second Meters
												</button></td> -->
												
												<td><button type="button" class="btn btn-success" id="htPending3"  data-toggle="tooltip" title="EXCEL REPORT"
													onclick="return exportToExcelMethod();">Export To Excel
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
 		<c:if test = "${htReadingerror eq 'HT Reading Data Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${htReadingerror}</span>
						</div>
			        </c:if>
		
		
		
		
		<%-- <c:if test="${showhtReading eq 'showhtReading'}">
				
	<div class="portlet box blue">
	
	<c:if test = "${htReadingerror eq 'HT Reading Data Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${htReadingerror}</span>
						</div>
			        </c:if>
			        
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>HT Reading Summary
							
						  <!-- <a href="#" id="excelExport" class="btn green" style="margin-left: 550px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a> 
						  <button class="btn btn-success" type="button" onclick="exportToExcelMethod();">Export to Excel</button>
				       <a href="#" id="htPendindPrintData" class="btn green"><font size="2" color="white">PRINT</font></a><img alt="" src="resources/assets/img/print1.jpg" style="height: 35px; width: 35px;"></img>
						 -->	
						 
						 </div>
							
						</div>
						<c:set var="mainmeter" value="0"  scope="page"/>
						<c:set var="secondmeter" value="0" scope="page" />
						<c:set var="totalcompleted" value="0" scope="page" />
						
						<div class="portlet-body" id="excelUpload">
							<table class="table  table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>MAIN METERS</th>
										<th>SECOND METERS</th>
										<th>TOTAL COMPLETED</th>
										
									</tr>
									</thead>
								
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${htlList}">
								<tr>
									<td><b><font color=" #900C3F ">${element[0]}</font></b></td>
									<td><font color="#006a5f">${element[1]}</font></td>
									<td><font color="#006a5f">${element[2]}</font></td>
									<td><font color="#006a5f">${element[3]}</font></td>
									<c:set var="mainmeter" value="${mainmeter+ element[1]}"  scope="page"/>
						           <c:set var="secondmeter" value="${secondmeter+ element[2]}" scope="page" />
						           <c:set var="totalcompleted" value="${totalcompleted+ element[3]}" scope="page" />
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								
								<tr>
								<td><b>TOTAL</b></td>
								<td><b><font color=" #900C3F ">${mainmeter}</font><b></td>
								<td><b><font color=" #900C3F ">${secondmeter}</font><b></td>
								<td><b><font color=" #900C3F ">${totalcompleted}</font><b></td>
								</tr>
							</table >
						</div>
					</div>
					</c:if> --%>
		
		
<script>

	function showhthtReadingData(param) {
// 		alert(param);
		
		var rdngMonth = $("#reportsMonth").val();
		var circle = $("#circle").val();

		if (circle == "0") {
			bootbox.alert("Please Select Circle");
			return false;
		} else {
			$("#htreadingformId").attr("action", "./generateAsciiReport/"+param);

		}

	}
	
	function exportToExcelMethod()
	{
		var rdngMonth = $("#reportsMonth").val();
		var circle = $("#circle").val();
	//	alert(rdngMonth);
		if(circle=="%")
			{
			circle="ALL";
			}
		if (circle == "0") {
			bootbox.alert("Please Select Circle");
			return false;
		} else {

			window.location.href="./htReadingExportToExcel?rdngMonth="+rdngMonth+"&circle="+circle;
			

		}
	}

	$(document).ready(function(){
	    $("#htPending2").tooltip({
	        placement : 'top'
	    });
	    $("#htPending").tooltip({
	        placement : 'top'
	    });
	    $("#htPending3").tooltip({
	        placement : 'top'
	    });
	});
</script>


