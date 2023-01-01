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
								<i class="fa fa-reorder"></i> Meter Data Access -FTP Meter Server
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
										<label >User ID</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="MR865SU">
									</div>
									 <div class="form-group">
										<label >User Password</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="********">
									</div>
									<div class="form-group">
										<label >Port Number</label>
										<input type="text" class="form-control input-lg"  placeholder="23">
									</div>
									<div class="form-group">
										<label >FTP Path</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="ftp://182.77.23.23/FTP/METERDATA/METERTYPEI">
									</div>
									<div class="form-group">
										<label >Local Path</label>
										<input type="text" class="form-control input-lg"  placeholder="" value="D://FTP/METERDATA/METERTYPEI">
									</div>
									
									
									<div class="form-actions">
									<button type="submit" class="btn blue">DOWNLOAD DATA FROM FTP SERVER</button>
									                            
									</div>
								
							</form>
						</div>
					</div>
				</div>	
		</div>		
</div>