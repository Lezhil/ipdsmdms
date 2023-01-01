
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>

<script type="text/javascript">
	$(".page-content").ready(function() {
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		$('#MDASSideBarContents,#other-Reports,#circleWiseReport').addClass('start active ,selected');
		 $("#reportsMonth").val(getPresentMonthDate('${selectedMonth}'));
		 
		
		/*  $('#MIS-Reports').addClass('start active ,selected'); */
		 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		 
	   	   
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
							<div class="caption"><i class="fa fa-edit"></i> CircleWise Report
							
					<!-- 	  <a href="#" id="excelExport" class="btn green" style="margin-left: 550px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
						  <button class="btn btn-success" type="button"  onclick="tableToExcel('sample_editable_1', 'circleWiseReport')">Export to Excel</button>
				       <a href="#" id="htPendindPrintData" class="btn green"><font size="2" color="white">PRINT</font></a><img alt="" src="resources/assets/img/print1.jpg" style="height: 35px; width: 35px;"></img> -->
							</div>
							
						</div>
						
							<div class="portlet-body">
			<div class="row" style="margin-left: 1px;">


				<form action="" id="btopFormId">
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


											<td><button type="submit" class="btn btn-success" id="htPending" style="margin-left: 50%;"
													onclick="return showhbtopData();">Show
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
						
						 <c:if test = "${btopError eq 'BTOP MRWise Data Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${btopError}</span>
						</div>
			        </c:if>
					
					 <c:if test="${ btopShow eq 'btopShow'}">
						
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
									
										<th>CIRCLE</th>
										<th>READ_BY_BTOP</th>
										
									</tr>
								</thead>
								<tbody>
					<c:set var="c" scope="page" value="0"></c:set>
					<c:forEach var="element" items="${Circledata}">
							
								<tr>
							<td>${element[0] }</td>
							<%-- <td>${element[1] }</td> --%>
							<c:set var="c" scope="page" value="${c+1}"></c:set>
						<%-- 	<td><a href="#" onclick="editDetails(this.id,'${element[0]}')" id="${c}"><c:out value="${element[1]}"></c:out></a></td> --%>
					<td><a href="./btopSecondTable?circle=${element[0]}"><c:out value="${element[1]}"></c:out></a></td>	
								</tr> 
								</c:forEach>
						
						</tbody>
								
								
							</table>
						</div>
						</c:if>
						
						
								 <c:if test = "${secondBtopError eq 'BTOP MRWise Data Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${secondBtopError}</span>
						</div>
			        </c:if>
					
					
					</div>
					 <c:if test="${ second eq 'second'}">
					 
	<div id="sectDashDetails">	
				<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>BTOP MRWise Details : ${BTOPcircle}
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
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_2', 'MRwise RNA Details')">Export to Excel</a></li>
							</ul>
						</div>
					<div class="table-scrollable">
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr style="background-color: lightgray;text-align: center;">
								<th>SL NO.</th>
								<th>CIRCLE</th>
								<th>MMRNAME</th>
								<th>COUNT</th> 
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="1" scope="page"/>
							<c:forEach items="${secondBtopList}" var="element">
							     <tr>
									<td>${count}</td>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
					                <td><a href="#" onclick="editDetails(this.id,'${element[0]}','${element[1]}')" id="${count}"><c:out value="${element[2]}"></c:out></a></td>
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
		
		<div id="stack1" class="modal fade" tabindex="-1" data-replace="true">
								<div class="modal-dialog modal-full">
									<div class="modal-content">
									   <div class="modal-header">
									        <h4 class="modal-title">MR Wise Total NOI </h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											
										</div> 
										 <div class="btn-group pull-right">
								         <button class="btn dropdown-toggle" data-toggle="dropdown">
									      Tools <i class="fa fa-angle-down"></i>
								         </button>
								  <ul class="dropdown-menu pull-right">
									 <!-- <li><a href="#" id="print">Print</a></li> -->
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_entered_report', 'Holiday Info')">Export
											to Excel</a></li>
								</ul>
							</div> 
						   		        <table class="table table-striped table-hover table-bordered" id="sample_entered_report">
						   		         <thead>
						   		          <tr>
						   		         
						   		          <th>SDONAME</th>
						   		          <th>ACCNO</th>
						   		          <th>TADESC</th>
						   		          <th>METRNO</th>
						   		          <th>NAME</th>
						   		          <th>MMRNAME</th>
						   		          
						   		          </tr>
						   		         </thead>
						   		         <tbody >
						   		         </tbody>
						   		        </table>
									   
										<div class="modal-footer">
											<button type="button" class="btn default" data-dismiss="modal">Close</button>
											
										</div>
										</div>
										
									</div>
								</div>
		
	
		
		
<script>

	function editDetails(param,circle,mmrname)
	{
		
	         var $modal = $('#loading1');
					 $('body').modalmanager('loading');
					  $modal.modal('loading').css({
						  'position': 'fixed',
					  'left': '50%',
					  'top': '50%',
					  'display' : 'block',
					  'overflow': 'hidden'
					 
					  });
		     $.ajax(
					{
							type : "GET",
							url : "./getCircleWiseMeters",
							data:{circle:circle,mmrname:mmrname},
							dataType : "json",
							cache:false,
							async:false,
							success : function(response)
								{	  
								//alert(response);

		  					  var html="";
		  					  for(var i=0;i<response.length;i++)
			  					  {
		  						   html= html+"<tr>";
	  					    		html= html+"<td id='sdoNameId'>"+ response[i][0]+" </td>";
	  					    		html= html+"<td id='accId'>"+ response[i][1]+" </td>";
	  					    		html= html+"<td id='tadescId'>"+ response[i][2]+" </td>";
	  					    		html= html+"<td id='meternoId'>"+ response[i][3]+" </td>";
	  					    		html= html+"<td id='nameId'>"+ response[i][4]+" </td>";
	  					    		html= html+"<td id='mmrnameId'>"+ response[i][5]+" </td>";
	  					    		html= html+"</tr>";
			  					  }
		                        $('#stack1 tbody').html(html);
								 
							 },
		                    error: function(e)
		                    {
								alert('Error: ' + e.responseText);
		                    } 
					
						});

			$('body').modalmanager();
			 $modal.modal();
      		  $('#'+param).attr("data-toggle", "modal");
    		  $('#'+param).attr("data-target","#stack1");
		     }
	
	
	function showhbtopData()
	{
		$("#btopFormId").attr("action","./btopFirstTableData");
	}
</script>
