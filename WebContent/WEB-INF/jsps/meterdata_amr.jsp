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
								<i class="fa fa-reorder"></i> Meter Data Access - AMR
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
										<label >Modem Name</label>
										<select  class="form-control">
											<option>L&T Modem Type 1 GSM</option>
											<option>L&T Modem Type 2 GSM</option>
											<option>Secure Modem Type 1 GSM</option>
											<option>Secure Modem Type 2 GSM</option>
											
										</select>
									</div>
									<div class="form-group">
										<label >Modem Connection Type</label>
										<select  class="form-control">
											<option>GSM</option>
											<option>GPRS</option>
										
										</select>
									</div>
										<div class="form-group">
										<label >Initial Commands</label>
										<textarea class="form-control" rows="5">
										GSM*DATA||||| PKT++++TYPE||||||||
										</textarea>
									</div>
									<div class="form-group">
										<label >Port Number(1-256)</label>
										<input type="text" class="form-control input-lg"  placeholder="1-256">
									</div>
									
								<div class="form-actions">
									<button type="submit" class="btn blue">Access Meter Data</button>
									                            
								</div>
							</form>
						</div>
					</div>
				</div>	
		</div>		
</div>