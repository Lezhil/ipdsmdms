
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		$('#MDASSideBarContents,#other-Reports,#accNoNotAvailableReport').addClass('start active ,selected');
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
				<i class="fa fa-edit"></i>Account No Not Available Report
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


											<td><button type="submit" class="btn btn-success" id="accnoId" style="margin-left: 50%;"
													onclick="return accnodata();">Search
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
			
			<c:if test = "${accntListError eq 'Account Details Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${accntListError}</span>
						</div>
			        </c:if>
			        
	<c:if test="${ accntListShow eq 'accntListShow'}">
		<div class="portlet box blue">

			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-edit"></i> Account No Details
					<a href="#" id="excelExport" class="btn green"
						style="margin-left: 550px;"
						onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export
						to Excel</a>
				</div>

			</div>
			<div class="portlet-body" id="excelUpload">
				<table class="table table-striped table-hover table-bordered"
					id="sample_editable_1">
					<thead>
						<tr>
							<th>CIRCLE</th>
							<th>SDONAME</th>
							<th>ACCNO</th>
							<th>METERNO</th>
							<th>NAME</th>
							<th>ADDRESS</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="count" value="1" scope="page">
						</c:set>
						<c:forEach var="element" items="${accntList}">
							<tr>
								<td>${element[0]}</td>
								<td>${element[1]}</td>
								<td>${element[3]}</td>
								<td>${element[4]}</td>
								<td>${element[5]}</td>
								<td>${element[6]}</td>
							</tr>
							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</c:if>
					
					
</div>





<script>
	function accnodata() {

		$("#htmanualformId").attr("action", "./getaccountdetailsUsersRpt");

	}
</script>