<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  FormComponents.init();
   	    	  TableManaged.init();
   	          
   	    	   $('#seal-Manager').addClass('start active ,selected');
   	    	 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	     
   	    	   });
  
  </script>
<div class="page-content" >
<div class="row">
		
		
		
				
				<div class="col-md-12">
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-comments"></i>Location Hierarchy</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="dd" id="nestable_list_2">
								<ol class="dd-list">
									<li class="dd-item" data-id="13">
										<div class="dd-handle">ALWAR</div>
									</li>
									<li class="dd-item" data-id="14">
										<div class="dd-handle">JCC</div>
									</li>
									<li class="dd-item" data-id="15">
										<div class="dd-handle">JPDC</div>
										<ol class="dd-list">
											<li class="dd-item" data-id="16">
												<div class="dd-handle">DD-I.Jaipur</div>
											</li>
											<li class="dd-item" data-id="17">
												<div class="dd-handle">DD-II.Jaipur</div>
											</li>
											<li class="dd-item" data-id="18">
												<div class="dd-handle">SAMBHAR</div>
											</li>
											<li class="dd-item" data-id="19">
												<div class="dd-handle">CHOMU</div>
											</li>
										</ol>
									</li>
								</ol>
							</div>
						</div>
					</div>
				</div>
				
			 
</div>