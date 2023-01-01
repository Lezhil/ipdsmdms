
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
 $('#MDASSideBarContents,#other-Reports,#htAbtManualReports').addClass('start active ,selected');
		 
    	 $("#htPendindPrintData").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});
    	 $('#other-Reports').addClass('start active ,selected');
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
				<i class="fa fa-edit"></i>HT ABT Manual Report
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>

		<div class="portlet-body">
			<div class="row" style="margin-left: 1px;">


				<form action="" id="htmanualformId">
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
													onclick="return showhtManualRecords();">Show
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
			
	<div class="portlet box blue">
	
	<c:if test = "${manualerror eq 'HT Manual Data Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${manualerror}</span>
						</div>
			        </c:if>
			        
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i> HT ABT Details of ${selectedMonth}
							
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 550px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
				       <a href="#" id="htPendindPrintData" class="btn green"><font size="2" color="white">PRINT</font></a><img alt="" src="resources/assets/img/print1.jpg" style="height: 35px; width: 35px;"></img>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>SDOCODE</th>
										<th>SDONAME</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>READINGREMARK</th>
										<th>KWH</th>
										<th>KVAH</th>
										<th>KVA</th>
										<th>CURRDNGKWH</th>
										<th>CURRRDNGKVAH</th> 
										<th>CURRDNGKVA</th>
										<th>NAME</th>
										<th>ADDRESS</th>
										<th>MRNAME</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${manualList}">
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
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									<td>${element[14]}</td>
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					
		</div>





<script>
function showhtManualRecords()
{
	var circle=$("#circle").val();
	if(circle=="0")
		{
		 bootbox.alert("Please Select Circle");
		 return false;
		}
		else
		{
		 $("#htmanualformId").attr("action","./getHtAbtManualData");
		
		}
			
	
}
</script>