<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	<script src="crystalreportviewers/crystalreportviewers/js/log4javascript/log4javascript_uncompressed.js"></script>
	<script src="resources/assets/scripts/ui-general.js"></script>
	

  <script  type="text/javascript">
  $(".page-content").ready
	    (function(){   
	    		
	    			$('#rdngmonth').val("${rdngmonth}");
	    			  App.init();
			   	    	 TableEditable.init();
			   	    	 FormComponents.init();
			   	    	 UIExtendedModals.init();
			   	  	$('#MDASSideBarContents,#other-Reports,#mrTotal').addClass('start active ,selected');
			   	      $('#other-Reports').addClass('start active ,selected');
	});
		</script>

<style>
.input-medium {
    width: 160px !important;
}
</style>

<div class="page-content" >
 <div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>MR Wise Total
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
					<form:form action="./getAllMrWiseTotalData"  method="post" id="mrformId" modelAttribute="Newseal" commanName="Newseal">
								
									<div class="form-body">
									
									
										<div class="form-group">

							<table style="width: 53%">
								<tbody>
									<tr>
										<td>RDNGMONTH</td>
										<td>
											<div data-date-viewmode="years" data-date-format="yyyymm"
												class="input-group input-medium date date-picker">
												<form:input type="text" path="" name="readingmonth" value="${readingMonth}" 
													id="readingmonth" class="form-control" required="required"
													readonly="readonly"></form:input>
												<span class="input-group-btn">
													<button type="button" class="btn default">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div>
										</td>
										<td hidden>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CATEGORY</td>
										<td hidden><select class="form-control select2me input-medium" style="margin-left: 13px;"
											name="tadescId" id="tadescId">
												<option value="0">Select Category</option>
												<c:forEach items="${category}" var="element">
													<option value="${element}">${element}</option>
												</c:forEach>
										</select></td>



										<td><input type="submit" id="dataview1" class="btn green"
											style="margin-left: 30px;	 border-right-width: -17px;"
											onclick="return mrSearchMethod();" value="Search"></td>
									</tr>
							</table>
						</div>
					</div>
					
						</form:form>
					
					</div>
					
             	<c:if test = "${mrTotalError eq 'MR Wise Total Records Not Found...'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${mrTotalError}</span>
						</div>
			    </c:if>
			    
			        
					<c:if test="${ mrTotalShow eq 'mrTotalShow'}">
					
					<div class="portlet-body" id="divId" >
					<table class="table table-striped table-hover table-bordered" id="mrTableId" >
									<thead>									
										<tr>								
											    <th>CIRCLE</th>
												<th>HT_TOTAL</th>
												<th>HT<br>COMPLETED</th>
												<th>HT_PENDING</th>
												<th>ABT_TOTAL</th>
												<th>ABT<br>COMPLETED</th>
												<th>ABT_PENDING</th>
												<th>MIP_TOTAL</th>
												<th>MIP<br>COMPLETED</th>
												<th>MIP_PENDING</th>
												
												
	            						</tr>
									</thead>
									<tbody id="mrTabBodyId">
									        <c:set var="count1" scope="page" value="0"></c:set>
											<c:forEach var="list" items="${mrTotalList}">
							
												<tr>
													<td><c:out value="${list[0]}"></c:out></td>
													<td><c:out value="${list[1]}"></c:out></td>
													
													<td><c:out value="${list[2]}"></c:out></td>
													<td><c:out value="${list[3]}"></c:out></td>
													
													<td><c:out value="${list[4]}"></c:out></td>
													<td><c:out value="${list[5]}"></c:out></td>
													
													<td><c:out value="${list[6]}"></c:out></td>
													<td><c:out value="${list[7]}"></c:out></td>
													
													<td><c:out value="${list[8]}"></c:out></td>
													<td><c:out value="${list[9]}"></c:out></td>
													
											
												</tr>
											</c:forEach>
									</tbody>
								</table>
				</div>
				</c:if>
				
				</div>
				</div>
				
			
			
			 <div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>MR Total
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
					<form:form action="./getMrWiseTotalDataOnCircle"  method="post" id="mrformId" modelAttribute="Newseal" commanName="Newseal">
								
									<div class="form-body">
									
									
										<div class="form-group">

							<table style="width: 53%">
								<tbody>
									<tr>
										<td>RDNGMONTH</td>
										<td>
											<div data-date-viewmode="years" data-date-format="yyyymm"
												class="input-group input-medium date date-picker">
												<form:input type="text" path="" name="rdMonth" value="${readingMonth}" style="margin-left: 13px;"
													id="rdMonth" class="form-control" required="required"
													readonly="readonly"></form:input>
												<span class="input-group-btn">
													<button type="button" class="btn default">
														<i class="fa fa-calendar"></i>
													</button>
												</span>
											</div>
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CIRCLE</td>
										<td><select class="form-control select2me input-medium" style="margin-left: 13px;"
											name="circleId" id="circleId">
												<option value="0">Select Circle</option>
												<c:forEach items="${circle}" var="element">
													<option value="${element}">${element}</option>
												</c:forEach>
										</select></td>



										<td><input type="submit" id="dataview2" class="btn green"
											style="margin-left: 30px;	 border-right-width: -17px;"
											onclick="return mrWiseSearchMethod();" value="Search"></td>
									</tr>
							</table>
						</div>
					</div>
					
						</form:form>
					</div>

			<c:if test="${mrWiseListError eq 'MR Wise Total Records Not Found...'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${mrWiseListError}</span>
				</div>
			</c:if>


			<c:if test="${ mrWiseListShow eq 'mrWiseListShow'}">
					
						<div class="portlet-body">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
									<thead>									
										<tr>								
											    <th>CIRCLE</th>
												<th>SDOCODE</th>
												<th>SDONAME</th>
												<th>MMRNAME</th>
												<th>ABT_TOTAL</th>
												<th>ABT_COMPLETED</th>
												<th>ABT_PENDING</th>
												<th>HT_TOTAL</th>
												<th>HT_COMPLETED</th>
												<th>HT_PENDING</th>
												<th>MIP_TOTAL</th>
												<th>MIP_COMPLETED</th>
												<th>MIP_PENDING</th>
												<th>GTOTAL</th>
												<th>TOTAL_COMPLETED</th>
												<th>TOTAL_PENDING</th>
												
	            						</tr>
																</thead>
										<tbody id="mrWiseBodyId">
											<c:set var="count" scope="page" value="0"></c:set>
											<c:forEach var="element" items="${mrWiseList}">
							
												<tr>
													<td><c:out value="${element[0]}"></c:out></td>
													<td><c:out value="${element[1]}"></c:out></td>
													
													<td><c:out value="${element[2]}"></c:out></td>
													<td><c:out value="${element[3]}"></c:out></td>
													
													<td><c:out value="${element[4]}"></c:out></td>
													<td><c:out value="${element[5]}"></c:out></td>
													
													<td><c:out value="${element[6]}"></c:out></td>
													<td><c:out value="${element[7]}"></c:out></td>
													
													<td><c:out value="${element[8]}"></c:out></td>
													<td><c:out value="${element[9]}"></c:out></td>
													
													<td><c:out value="${element[10]}"></c:out></td>
													<td><c:out value="${element[11]}"></c:out></td>
													
													<td><c:out value="${element[12]}"></c:out></td>
													<td><c:out value="${element[13]}"></c:out></td>
													
													<td><c:out value="${element[14]}"></c:out></td>
													<td><c:out value="${element[15]}"></c:out></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
				</div>
				</c:if>
				</div>

				
			</div>

			</div>
			
<script>
 function mrSearchMethod()
{
	$("#sample_editable_1").hide();
} 
 
 function mrWiseSearchMethod()
 {
	 $("#mrTableId").hide();
	 var circle=$("#circleId").val();
	 if(circle=="0")
		 {
		 bootbox.alert("Please Select Circle");
		 return false;
		 }
		
 }
</script>
	