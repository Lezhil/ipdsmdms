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
								<i class="fa fa-reorder"></i> Meter Data Access -CMRI Device
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
										<label >Instance ID</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="1">
									</div>
									
									<div class="form-group">
										<label >Baud Rate</label>
										<select  class="form-control">
											<option>128</option>
											<option>300</option>
											<option>256</option>
											<option>512</option>
											
										</select>
									</div>
									<div class="form-group">
										<label >Meter Communication Port</label>
										<select  class="form-control">
											<option>OPTICAL</option>
											<option>IR</option>
											<option>USB</option>
										</select>
									</div>
									<div class="form-group">
										<label >Meter Make</label>
										<select  class="form-control">
											<option>L&T</option>
											<option>Secure</option>
											<option>Genus</option>
										</select>
									</div>
									<div class="form-group">
										<label >Serial Number</label>
										<select  class="form-control">
											<option>CMR986723</option>
											<option>CMR246723</option>
											<option>CMR94344</option>
										</select>
									</div>
									<div class="form-group">
										<label  class="">What to Read</label>
										<div class="radio-list">
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked> 
											Full Data
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" > 
											Billing Data
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" > 
											Instantaneous Data
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" > 
											Transactions
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" > 
											Billing Data
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" > 
											Load Profile
											</label>
											<label>
											<input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" disabled> 
											Full Data without LS
											</label>
										</div>
									</div>
										<div class="form-group">
										<label >Data Download Path</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="D://CMRI/METERDATA/METERTYPEI">
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