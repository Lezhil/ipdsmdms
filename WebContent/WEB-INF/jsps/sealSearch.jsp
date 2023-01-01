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
	    			

	    			$('#newSealManager').addClass('Start active ,selected');	
	    			$("#dash-board").removeClass('start active ,selected');
			   	    	  App.init();
			   	    	 TableEditable.init();
			   	    	 FormComponents.init();
			   	    	 UIExtendedModals.init();
			   	    	UIGeneral.init();
			   	    	 handleDynamicPagination.init();
	
			   	    	
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
						<i class="fa fa-edit"></i>Seal Search
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
					<form:form action=""  method="post" id="firstSealForm" modelAttribute="Newseal" commanName="Newseal">
								
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
									<td style="padding-left: 20px;">SEALNO</td>
										<td>
										<input type="text" id="sealNo" name="sealNo" class="form-control input-medium"
										 placeholder="Enter SealNo" maxlength="8" style='text-transform:uppercase'>
                                        </td>



										<td><input type="submit" id="dataview1" class="btn green"
											style="margin-left: 50%; border-right-width: -17px;"
											onclick="return sealSearchMethod();" value="Search"></td>
									</tr>
							</table>
						</div>
					</div>
					
						</form:form>
					
					</div>
					
             	<c:if test = "${firstSealError eq 'Seal Data Not Found'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${firstSealError}</span>
						</div>
			    </c:if>
			    
			        
					<c:if test="${ firstSealShow eq 'firstSealShow'}">
					
					<div class="portlet-body" id="divId" >
					<table class="table table-striped table-hover table-bordered" id="mrTableId" >
									<thead>									
										<tr>								
											    <th>RDNGMONTH</th>
												<th>ACCNO</th>
												<th>METRNO</th>
												<th>NEWSEAL</th>
												<th>MRNAME</th>
												
												
	            						</tr>
									</thead>
									<tbody id="mrTabBodyId">
									        <c:set var="count1" scope="page" value="0"></c:set>
											<c:forEach var="list" items="${firstSealList}">
							
												<tr>
													<td><c:out value="${list[0]}"></c:out></td>
													<td><c:out value="${list[1]}"></c:out></td>
													<td><c:out value="${list[2]}"></c:out></td>
													<td><c:out value="${list[3]}"></c:out></td>
													<td><c:out value="${list[4]}"></c:out></td>
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
						<i class="fa fa-edit"></i>Seal Search
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
					<form:form action=""  method="post" id="secondSealForm" modelAttribute="Newseal" commanName="Newseal">
								
									<div class="form-body">
									
									
										<div class="form-group">

							<table>
								<tbody>
									<tr>
										<td>SEALNO</td>
										<td>
										<input class="form-control input-medium"  type="text" id="sealNoId" name="sealNoId"
										 placeholder="Enter SealNo" style='text-transform:uppercase' maxlength="8" >
                                        </td>

										<td><input type="submit" id="dataview3" class="btn green"
											style="margin-left:10%;	 border-right-width: -17px;"
											onclick="return searchSeal();" value="Search"></td>
									</tr>
							</table>
						</div>
					</div>
					
						</form:form>
					</div>

			<c:if test="${secondSealError eq 'Seal Data Not Found'}">
				<div class="alert alert-danger display-show" id="otherMsg">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${secondSealError}</span>
				</div>
			</c:if>


			<c:if test="${ seconddSealShow eq 'seconddSealShow'}">
					
						<div class="portlet-body">
					<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
									<thead>									
										<tr>								
											    <th>SEALNO</th>
												<th>MRNAME</th>
												<th>IDATE</th>
												<th>BILLMONTH</th>
												<th>ACCNO</th>
												<th>METERNO</th>
												<th>REMARK</th>
												<th>CARDSLNO1</th>
												<th>RMRNAME</th>
												<th>ISSUEDBY</th>
	            						</tr>
																</thead>
										<tbody id="mrWiseBodyId">
											<c:set var="count" scope="page" value="0"></c:set>
											<c:forEach var="element" items="${secondSealList}">
							
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
 function sealSearchMethod()
{
	var newsealNo=$("#sealNo").val();
	if(newsealNo=="" || newsealNo==null )
		{
		 bootbox.alert("Please enter the SealNo");
		 return false;
		}
	else
		{
		$("#firstSealForm").attr("action","./getFirstSealData");
		}
} 
 
 function searchSeal()
 {
	 var sealNo=$("#sealNoId").val();
		if(sealNo=="" || sealNo==null )
			{
			 bootbox.alert("Please enter the SealNo");
			 return false;
			}
		else
		{
		$("#secondSealForm").attr("action","./getSecondSealData");
		}
		
 }
</script>
	