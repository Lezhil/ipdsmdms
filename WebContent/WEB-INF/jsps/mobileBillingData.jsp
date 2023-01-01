<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
	<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	
	 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {  
	  				
	  
	  
      
				  var elements = document.getElementsByName("metrno");
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
	   	    	   UIDatepaginator.init();
	   	    		UIExtendedModals.init();
	   	        	$('#mobile-billing-data').addClass('start active ,selected');
	   	     	   $("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	 
	   	    
	   	     	$('#myModal').on('shown.bs.modal', function () {
	   	     	 
	   	         var progress = setInterval(function() {
	   	         var $bar = $('.bar');

	   	         if ($bar.width()==500) {
	   	           
	   	             // complete
	   	           
	   	             clearInterval(progress);
	   	             $('.progress').removeClass('active');
	   	             $('#myModal').modal('hide');
	   	             $bar.width(0);
	   	             
	   	         } else {
	   	           
	   	             // perform processing logic here
	   	           
	   	             $bar.width($bar.width()+50);
	   	         }
	   	         
	   	         $bar.text($bar.width()/5 + "%");
	   	     	}, 800);
	   	       
	   	       
	   	     })
   	    	   
   	    	   });

  function pleaseWait()
  {
	  $('#newConnectionMeterMaster').attr('action','./updateUserDetails').submit();
  }
  

  
  function abc()
  {
	
	  var $modal = $('#loading1');
	 /*  document.getElementById("loadingForm").submit(); */
	  document.getElementById("pagesubmit").submit();
	  
	  //$("#loadingForm").attr("action","./addDataToSbm");
	  $('body').modalmanager('loading');
	  $modal.modal('loading').css({
		  'position': 'absolute',
	  'left': '50%',
	  'top': '50%',
	  });
	  
	return true;
  }
  

  </script>
<div class="page-content" >

<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
    
	<div class="portlet box blue">
	 
                        
			
          
						<div class="portlet-title">
							    <div class="caption"><i class="fa fa-reorder"></i>Mobile Billing Data </div>
							    <div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						 <div class="col-md-12">
										
							<form:form action="./addDataToSbm" name="form" method="post" id="pagesubmit" modelAttribute="transferData" commandName="transferData" >
										
										
										<table>	
										<tr><td>Bill Month</td><td>
										
																<form:input path="billmonth" type="text" class="form-control" name="billmonth" id="billmonth"  readonly="true" ></form:input>
																</td></tr>
																			
									 </table>
									 <br><br>
									 <div class="modal-footer">	

									 <!--  <button name="" type="submit" class="btn green">Submit</button> -->  
									<!--   <div class="container">
                                   <a href="#myModal" role="button" class="btn btn-primary" data-toggle="modal" onclick="document.getElementById('pagesubmit').submit();">Submit</a>
									</div> -->  
                                      
									  <a href="#" role="button" class="btn green" id="loading" onclick="return abc()">Submit</a> 
									  <a href="#"  id="loading1" ></a> 
									  
									<!--  <div class="container">
                                   <a href="#myModal" type="submit" role="button" class="btn btn-primary" data-toggle="modal">Submit</a>
									</div>  -->

  
									 
												</div>
												
												
								</form:form>				
								
								 
								
								
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <!-- <button type="button" class="close" data-dismiss="modal" id="pw" aria-hidden="true">×</button> -->
        <h4 class="modal-title" id="myModalLabel">Please Wait</h4>
      </div>
      <div class="modal-body center-block">
        <div class="progress">
          <div class="progress-bar bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="1000">
            
          </div>
        </div>
      </div>
      <!-- <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div> -->
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
								
												
							</div>
					
						</div>
		</div>		
</div>