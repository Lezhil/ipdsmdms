<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	   	    	$('#MDASSideBarContents,#mobile-billing-data,#sbmDetails').addClass('start active ,selected');
	   	     	   $("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	 
	   	    });
  
  function validateMyForm(form)
  {
	  var sbmno=form.sbmnumber.value;
	 // var manfacture=form.manufacture.value;
	  var sbmlen=sbmno.toString().length;
	  if((sbmno.trim()=='' || sbmno.trim()==null))
	   {
		bootbox.alert("Please Eneter IMEI Number Must be numeric");
	    return false;
	   }
	  if(sbmno < 1 || sbmno < 0)
	   {
		bootbox.alert("Please Eneter valid IMEI Number Must be numeric");
	    return false;
	   }
	  
	  
	  if(isNaN(sbmno))
	     {
		    bootbox.alert("IMEI no Must be numeric only");
		    return false;
		} 
	  
	  if(sbmno!='' && sbmno.length <15)
	   {
		    bootbox.alert("IMEI no Must be 15 digits long");
		    return false;
		}
	   
	  /*  if(manfacture=="default")
	   {
		 bootbox.alert("Please Select Manufacturer");
		 return false;
	   } */
}
  
  
	function deleteSBMDetails(opera)
	{
		//var operation=parseInt(opera);
			
		  bootbox.confirm("Are you sure want to delete this record ?", function(result) {
			  if(result == true)
	             {
				  $("#delid").attr("value",opera);
		          $('form#delSbmData').attr('action','./detletSbmDetails').submit();
	             }
	
		  });
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
							    <div class="caption"><i class="fa fa-reorder"></i>ADD IMEI Details</div>
							    <div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						  <div class="col-md-12">
										
								<form:form action="./addSbmDetails" name="form" method="post" id="" modelAttribute="sbmDetailsEntity" commandName="sbmDetailsEntity" >
								    <table>
									
									<tr>
									<td>IMEI Number</td>
									<td><form:input path="sbmnumber" type="text" class="form-control" name="sbmnumber" id="sbmnumber" maxlength="15"></form:input></td>
									</tr>
									
									<tr>
									<td>Type</td>
									<td><form:input path="type" type="text" class="form-control" name="type" id="type" value=" Mobile" readonly="true"></form:input></td>
									</tr>
									
									<%--  <tr>
									 <td>Manufacture</td>
									 <td><form:select path="manufacture" name="manufacture" id="manufacture" class="form-control">
									   <form:option value="default">Select</form:option>
									   <form:option value="VISIONTAKE">VISIONTAKE</form:option>
									   <form:option value="SAMSUNG">SAMSUNG</form:option>
									   <form:option value="NOKIA">NOKIA</form:option>
									   <form:option value="KARBON">KARBON</form:option>
									   <form:option value="LAVA">LAVA</form:option>
									 </form:select></td>
									 </tr> --%>
									
										
									 </table>
									 <br><br>
									 <div class="modal-footer">	
                                      <button type="submit" class="btn green" onclick="return validateMyForm(this.form)" >Submit</button>
												</div>
												
					                </form:form>
											
									
					
						</div>
						
					<form method="post" id="delSbmData">
				      <input type="hidden" id="delid" name="delSbmId">
				    </form>	
					<div class="portlet-body">
							
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_2">
                              <thead>
								<tr>
								<!--  <th hidden="true">SBMID</th> -->
						         <th>IMEI Number</th>
						         <th>IMEI Type</th>
						         <!-- <th>Manufacture</th> -->
						         <th>Date</th>
						         <th>Delete</th>
						        </tr>
								</thead>
								
								<tbody>
								<c:forEach items="${sbmdata}" var="elements">
						        <tr>
						         <%-- <td hidden="true">${elements.sbmdid}</td> --%>
						         <td>${elements.sbmnumber}</td>
						         <td>${elements.type}</td>
						      <%--    <td>${elements.manufacture}</td> --%>
						         <td>${elements.datestamp}</td>
						         <td><a href="#" onclick="deleteSBMDetails(${elements.sbmnumber})">Delete</a></td>
						         </tr>
						        </c:forEach>
								</tbody>
							</table>
						</div>
						
					 
						
						
						
		</div>
		
						
						
						
			
</div></div>