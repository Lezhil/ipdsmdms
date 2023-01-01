<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		var elements = document.getElementsByName("meternumber");
	    for (var i = 0; i < elements.length; i++) {
	        elements[i].oninvalid = function(e) {
	            e.target.setCustomValidity("");
	            if (!e.target.validity.valid) {
	                e.target.setCustomValidity("Please Enter Meter Number");
	            }
	        };
	        elements[i].oninput = function(e) {
	            e.target.setCustomValidity("");
	        };
	    }
	    
		App.init();
		TableManaged.init();
		FormComponents.init();			
		$('#export-Manager').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	});
  
</script>
<div class="page-content" >

<div class="row">
				<div class="col-mdd-12">
					<div class="portlet box light-grey">
						<div class="portlet-title">
							
							
							<div class="caption"><i class="fa fa-globe"></i>Export Accno manager</div>
							<div class="tools">
							
								
							<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								 
							
							</div>
						
							<div class="portlet-body">
								<form action="./getmonthdata" class="form-horizontal" method="post">
									<div class="form-body">
										<div class="form-group">
													<label  class="col-md-3 control-label">Search By</label>
													<div class="col-md-4">
														<input type="text" class="form-control" placeholder="Enter text" required="required" name="meternumber" value="${selectedmeterNumber}" >
														<span class="help-block">Enter Meter Number</span>
													</div>
										</div>																																	
													
										<div class="form-actions top fluid ">
											<div class="col-md-offset-3 col-md-9">
												<button name="metermaster" onclick="form.action='metermasterexport';" type="submit" class="btn green">View Meter Data</button>
												<!-- <button name="billingdata" onclick="form.action='billingdataexport';" type="submit" class="btn green">View Billing Data</button> --> 
											</div>
										</div>
									</div>
								</form>
							
							
							
							</div>
						
						</div>
					
					</div>
				
				</div>
</div>
</div>