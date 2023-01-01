<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Date"%>
<style>
#masterTd
{
   color: #004466;
   font-weight:500 ;
}
.input-small {
    width: 180px !important;
}
</style>

<script type="text/javascript">
var sectionName;
  	$(".page-content").ready(function()
  		   {    	
  		$('#fromDate').val('${cuurentDate}');
  		 
  		    	App.init();
  		    	TableEditable.init(); 
  		      FormComponents.init();
  		    $('#MDMSideBarContents,#MIS-Reports,#showMdiExceedRpt').addClass('start active ,selected');
 			 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
 				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
 		
  		 $('#subdivision').select2();
  		 
  		$("#print").click
		  (
	    	    	function()
	    	    	{ 
	    	    		printcontent($("#mrDiv").html());
				    }
	    	    	);
  		$("#print1").click
  	  (
    	    	function()
    	    	{ 
    	    		printcontent($("#printPending").html());
  			    }
    	    	);
  		   }); 	
	  
  	
  	
 	</script>
 	

<div class="page-content">



	<div class="portlet box purple">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>MDI Exceed Report</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
					
						 <div class="col-md-12">
										<form role="form" id="hhbmInfo" >
										<table >
										<tr >
										<td id="masterTd1"><select  id="subdivision" class="form-control input-small"  type="text" name="subdivision" >
										    <option value="noVal">SUBDIVISION</option>
													<c:forEach items="${subdivisionVal}" var="element">
													<option value="${element}">${element}</option>
													</c:forEach>	
										   </select></td>
											
										<td><select class="form-control select2me input-small" name="category" id="category" >
									    <option value="">Category</option>
									    <c:forEach var="elements" items="${category}">
									    <option value="${elements}">${elements}</option>
									    </c:forEach></select>
									   </td>
											
											<td>
										      <div class="input-icon">
															<i class="fa fa-calendar"></i> <input
																class="form-control date-picker input-medium"  type="text"
																value="" 
																data-date-format="yyyymm" data-date-viewmode="years" name="fromDate" id="fromDate" readonly="true" />
																
														</div>
													</td>
													
													
														  <td><div><button class="btn green pull-left" id="gpsViewButton"  formaction="./generateMdiExceedRpt" formmethod="post">view</button></div></td>
										</tr>
								
										</table>
										</form>
									
						</div>
						</div>
		</div>
		<c:if test="${results ne 'notDisplay'}">
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red;font-size:15px;">${results}</span>
					</div>
				</c:if>
				
		
	
		
	</div>
	




