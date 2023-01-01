<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  TableManaged.init();
   	        
   	    	   $('#meterData-Acquisition').addClass('start active ,selected');
   	    	 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	     
   	    	   });
  
  </script>
<div class="page-content" >

	<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12 ">
					<!-- BEGIN SAMPLE FORM PORTLET-->   
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i> Meter Data Access -DLMS Meter
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="" class="reload"></a>
								<a href="" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form role="form">
								<div class="form-body">
								 
									
									<div class="form-group">
										<label >Meter Make</label>
										<select  class="form-control">
											<option>L&T</option>
											<option>Secure</option>
											<option>Genus</option>
										</select>
									</div>
									<div class="form-group">
										<label >API Type</label>
										<select  class="form-control">
											<option>Read API I</option>
											<option>Read API II</option>
											<option>MRI PREPARE</option>
											<option>Convert API</option>
										</select>
									</div>
									<div class="form-group">
										<label for="exampleInputFile" class="col-md-3 control-label">Select Config File</label>
										<div class="col-md-9">
											<input type="file" id="exampleInputFile">
											<p class="help-block">Select DLMS Configuration File</p>
										</div>
									</div>
									<div class="form-group">
										<label >API Commands</label>
										<select  class="form-control">
											<option>Get Status</option>
											<option>Silent</option>
											<option>API Identification</option>
											<option>Abort</option>
										</select>
									</div>
									
									
									<div class="form-actions">
									<button type="submit" class="btn blue">RUN DLMS COMMAND</button>
									                            
									</div>
								
							</form>
						</div>
					</div>
				</div>	
		</div>		
</div>