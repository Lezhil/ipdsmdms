<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {  
	  				$('#accno').val('');
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
	   	        	$('#newConnectionId').addClass('start active ,selected');
	   	     	   $("#dash-board,#360d-view,#cumulative-Analysis,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	   	    	 
	   	     	 
	   	   
   	    	   
   	    	   	   
   	    	   
   	    	   });

  	    function getMeterMasterData()
  	      {   
  	    	var accno=document.getElementById('oldaccno').value;
  	    	reWhiteSpace = new RegExp(/^\s+$/);
  	    	var accnocheck = /^[a-zA-Z0-9]{12,13}$/; 
  	    	alert(accno);
  	        if(accno=='')
      		{
      		  document.getElementById("oldacccheck").innerHTML = "Enter the accno no please";
   	    	  document.getElementById("oldaccno").focus();
   	    	  return false;
      		}
      		
  	        else if(accno.match(accnocheck))
  	       		{ 
  	       		
  	       	    $('#accountTransfer').attr('action','./transferAccount').submit();	
  	    		return true;
  	        	}
  	        else
  	        	{
  	        	
  	        	 document.getElementById("oldacccheck").innerHTML = "Enter atlest 12 digits only";
  	        	 return false;
  	        	} 
  	        }
  	    
    	  
    	  
  	    function getMeterMasterData1()
  	      {   
  	    	  var meterno=document.getElementById('metrno').value;
  	    	  var letters = /^[A-Za-z]+$/;
  	          var code = /^[@#!$%&*/]+$/;
  	          
  	          if(meterno=='')
  	    		{
  	    		  document.getElementById("meternocheck").innerHTML = "Enter the meter no please";
  	    		  document.getElementById("metrno").focus();
  	 	    	  return false;
  	    		}
  	          
  	          else if(meterno.match(letters) || meterno.match(code))
  	     		{
  	     	      document.getElementById("meternocheck").innerHTML = "<b style='color:red'>Please provide alphanumeric value only</b>";
  	     	      $("#accountTransfer input").val("");
  	     		  $("#metrno").focus();
  	     		} 
  	          else
  	    		{
  	    		$('#accountTransfer').attr('action','./transferAccount1').submit();
  	    		return true;
  	    		}
  	    			
  	      } 	
 
   function myValidate(form)
    {
	   var meterno=form.metrno.value;
    	var accno=form.accno.value;
    	reWhiteSpace = new RegExp(/^\s+$/);
    	
    	var accnocheck = /^[a-zA-Z0-9]{12,13}$/; 
    
    	if(meterno=='')
    		{
    		 document.getElementById("meternocheck").innerHTML = "Enter the meter no please";
    		 $("#metrno").focus();
    		 return false; 
    		}
    	
    	/* else if(reWhiteSpace.test(accno))
      	{
    		 document.getElementById("meternocheck").innerHTML = "Check accno for whitespace";
             return false;
      	} */
    	 if(!reWhiteSpace.test(accno) && accno.match(accnocheck))
    		{
    		 return true;
    		 
    		}
    	 else  
         {  
           
               document.getElementById("accnocheck").innerHTML = "Enter the valid account number and must be 12 or 13 digit only";
               document.getElementById("accno").focus();
               return false;  
         }   
 	   
 	    document.getElementById("accnocheck").innerHTML = " ";
    	
    }
   
   
   
   
   function myClearFunction()
   {
   	$("#oldaccno").val('');
	$("#metrno").val('');
	$("#accno").val('');
   	$("#metrno").focus();
    }
  /*  function validateBackspace(e)
   {
	   var accval=document.getElementById("accno").value;
	   var key = e.which? e.which : e.keyCode;
	   alert(key+"  "+accval.length);
	  /*   if(!key ===8 && accval.length===4)
	       return false;
	    else
	    	return true; 
	   
   }*/
    
   function validateBackspace(e)
   {
   
   var keyPressed = e.keyCode;
  
    var accval=document.getElementById("accno").value;
  
    if(accval.length == 4 )
     {
     if(keyPressed == 8)
      {
      return false;
      }else{
       return true;
      }
      
     }
   }
  
  </script>
<div class="page-content" >
    
    
    	<c:if test = "${AccnoNotThere eq 'SDOCODE Wrong, Please check again'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${AccnoNotThere}</span>
						</div>
			    </c:if>
			    
			    
	<div class="portlet box blue">
	 
                        
			<c:if test = "${AccnoNotThere eq 'SDOCODE Wrong, Please check again'}"> 			        
			        <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${AccnoNotThere}</span>
						</div>
			 </c:if>
          
						<div class="portlet-title">
							    <div class="caption"><i class="fa fa-reorder"></i>Account Transfer</div>
							    <div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						 <div class="col-md-12">
										
										<form:form action="./transferAccountUpdate" name="form" method="post" id="accountTransfer" modelAttribute="meterMaster" commandName="meterMaster" >
										<table>
									
									
									
										<tr><td>Select YearMonth</td><td>
										
																<form:input path="rdngmonth" type="text" class="form-control" name="rdngmonth" id="rdngmonth"  readonly="true" ></form:input>
																</td></tr>
																			
									
										
										
		
										<tr>
										<td>Meter Number</td>
										<td><form:input path="metrno" id="metrno"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="Enter meter no" name= "metrno" required="required" onblur="getMeterMasterData1()" maxlength="12"></form:input>
																
															 <span id="meternocheck" style="color:red;font-weight:bold;" ></span> 
																</td></tr>						
																	
										
								 		
										
										<tr>
										<td>Old_Acc_No</td>
										<td><form:input path="master.oldaccno" id="oldaccno"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="on"  name= "master.oldaccno" onblur="getMeterMasterData()" value="${meterMaster.accno}"></form:input>
											 <span id="oldacccheck" style="color:red;" ></span> 
																</td></tr>
										 
									
									  <%-- <tr>
										<td>New_Acc_No</td>
										<td><form:input path="accno" id="accno"
																class="form-control placeholder-no-fix" type="text"
															autocomplete="off" name= "accno" oncut="return false;" onpaste="return false;" 
															minlength="4" onkeypress="return validateBackspace(event)" value="${subaccno}">
											</form:input>
																
											<span id="accnocheck" style="color:red;font-weight:bold;" ></span>					
										</td>
									  </tr> --%>
										
										<tr>
											<td>New_Acc_No</td>
											<td><form:input path="accno" id="accno" class="form-control placeholder-no-fix" 
															type="text"	autocomplete="on" name="accno" ></form:input>
												<span id="accnocheck" style="color:red;" ></span>					
											</td>
										</tr>
									</table>
									<br><br>
										<div class="form-actions top fluid ">
											<div class="col-md-offset-10 col-md-5">
												<button name="transfer" type="submit" class="btn green" onclick="return myValidate(this.form)">Transfer</button>
												<button name="clear" onclick="myClearFunction()" type="button" class="btn green">Clear</button>
												</div>
												</div>
												
												
								
										
										
										
										
											
						
										</form:form>
											
									
					
					<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
							
						</div>
		</div>
		
			
</div></div>