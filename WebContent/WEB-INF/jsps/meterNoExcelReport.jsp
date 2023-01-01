<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
$(".page-content").ready(function()
    	   { 
FormComponents.init();
$("#billmonth").val(getPresentMonthDate('${selectedMonth}'));
    $('#other-Reports').addClass('start active ,selected');
	$("#feeder-reports,#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
    	   });
	function validateFile()
	{
		if($('#uploadFile').val()=='')
			{
			  bootbox.alert('Please select file to upload.');
			  return false;
			}
	}
    	   
</script>
<div class="page-content">
<c:if test="${results ne 'notDisplay'}">
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red;font-size:15px;">${results}</span>
					</div>
				</c:if>
		<%-- <div class="portlet box blue">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Update RTC</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
					
						 <div class="col-md-12">
		<div class="row">
			<div class="col-md-12">
										
			<form:form action="./uploadMeterNoExcel"  enctype="multipart/form-data" method="post" id="meterNoUpload">
	 			<div class="form-group">
						<div >
						<div data-date-viewmode="years" data-date-format="yyyymm" class="input-group input-medium date date-picker">
									<input name="billmonth" id="billmonth" readonly="readonly" class="form-control" required="required" type="text">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
					</div>
							<label for="exampleInputFile" class="control-label">Choose File</label>							
						<input type="file" name="file" id="uploadFile" />	
											
												<div class="modal-footer">
													<!-- <button type="button" data-dismiss="modal" class="btn default pull-left">Close</button> -->
													<button class="btn green pull-left" style="display: block;" id="uploadOption"  onclick="return validateFile();">Upload</button>
												</div>
				                        </div>
	               </div>
	
			</form:form> 
																	
											  

			</div>
</div>
</div>
</div>
</div> --%>

<!-- Update RTC new -->
  
		<div class="row">
			<div class="col-md-12">
										
			<form:form action="./downloadExcelCMRI"  enctype="multipart/form-data" method="post" id="meterNoUpload">
	 			<div class="form-group">
						<div >
							<div class="modal-footer">
									<!-- <button type="button" data-dismiss="modal" class="btn default pull-left">Close</button> -->
											<button class="btn green pull-left" style="display: block;" id="downloadOption" >DownloadTemplate</button>
												</div>
				             </div>
	               </div>
	
			</form:form> 

			</div>
</div>



<div class="portlet box blue">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Update CMRIMASTER</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
					
						 <div class="col-md-12">
		<div class="row">
			<div class="col-md-12">
										
			<form:form action="./uploadMeterNoExcel"  enctype="multipart/form-data" method="post" id="meterNoUpload">
	 			<div class="form-group">
						<div >
						<div data-date-viewmode="years" data-date-format="yyyymm" class="input-group input-medium date date-picker">
									<input name="billmonth" id="billmonth" readonly="readonly" class="form-control" required="required" type="text">
										<span class="input-group-btn">
											<button type="button" class="btn default"><i class="fa fa-calendar"></i></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>	
					</div>
							<label for="exampleInputFile" class="control-label">Choose File</label>							
						<input type="file" name="file" id="uploadFile" />	
											
												<div class="modal-footer">
													<!-- <button type="button" data-dismiss="modal" class="btn default pull-left">Close</button> -->
													<button class="btn green pull-left" style="display: block;" id="uploadOption"  onclick="return validateFile();">Upload</button>
												</div>
				                        </div>
	               </div>
	
			</form:form> 
																	
											  

			</div>
</div>
</div>
</div>
</div>




</div>
