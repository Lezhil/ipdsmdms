<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script  type="text/javascript">
	$(".page-content").ready(function(){			
		App.init();
		TableManaged.init();
		FormComponents.init();	
		$('#userAndMRId').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#newConnectionId").removeClass('start active ,selected');
		$("#mrnamecheck").hide();
		
		$("#cuserPassword").val('${myUserProfile.userPassword}');
		/* var myInput = document.getElementById("username").style;
	    myInput.borderStyle="none";
		 */
		
	});
	
	/* 
	function updateUserData(form)
	{
		   var result=validation(form);
           if(result) 
	         $('#newConnectionMeterMaster').attr('action','./updateUserDetails').submit();
            else
        	   return false; 
	}	
		 */
  
	function edit(param,opera)
	  {
		$("#updateMrOption").show();
		$("#addMrOption").hide();
		
		 var operation=parseInt(opera);
	      $.ajax(
	  			{
	  					type : "GET",
	  					url : "./checkMrname/" + operation,
	  					dataType : "json",
	  					success : function(response)
	  												{
												    document.getElementById("id").value=response.id;
	 												document.getElementById("mrname").value=response.mrname;		
	  												}
	  			});
	      $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack2");   
	  	    
	  }; 
	
	  function editUser(param,opera)
	  {
		$('.error').hide(); 
		$("#addUserOption").hide();
		$("#updateUserOption").show();
		
		  var operation=parseInt(opera);
		  $.ajax(
		  			{
		  					type : "GET",
		  					url : "./editUserDetails/" + operation,
		  					dataType : "json",
		  					success : function(response)
		  												{	
		  										    		//alert(response.id);
		  													document.getElementById("userid").value=response.id;
		  													document.getElementById("username").value=response.username;
		  													document.getElementById("userPassword").value=response.userPassword;
		  													/* document.getElementById("userMailId").value=response.userMailId;
		  													document.getElementById("userMobileNo").value=response.userMobileNo;*/
		  													document.getElementById("userType").value=response.userType; 
		  													document.getElementById("designation").value=response.designation;
		  																 
		  												}
		  			});
		  $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack1");
		  }
	
	
	
 function validation(form)
     {
	    var uname=form.username.value;
    	//var uemail=form.userMailId.value;
    	var upwd=form.userPassword.value;
    	var cupwd=form.cuserPassword.value;
    	//var umobno=form.userMobileNo.value;
    	var ut=form.userType.value;
    	var des=form.designation.value;
    	//var phoneno = /^\d{10}$/;  
       // var atpos = uemail.indexOf("@");
   	   // var dotpos = uemail.lastIndexOf(".");
   	   // var utype=form.userType.value;
   	    var regex = /^[a-zA-Z ]*$/;
      
   	   
   	  
   	    if(uname=='')
   	    	{
   	    	bootbox.alert("Enter the user name");
   	        return false;
   		    }
   	    if(!uname.match(regex))
   	    	{
   	    	bootbox.alert("Enter alphabets only");
   	        return false;
   	    	
   	    	}
   	   if(upwd=='')
    	{
   		bootbox.alert( "Enter the password");
	    return false;
    	}
        if(cupwd=='')
   	    	{
   	    	bootbox.alert("Enter the confirm password");
   		    return false;
   	    	}
   	    if(cupwd != upwd)
   	    {
   	    	bootbox.alert( "Passwords are not matching");
		   return false;
		 }
   	 
   	   /* if(uemail=='')
   	    	 {
   	    	bootbox.alert("Enter the  email ID");
   	         return false;
   	    	 }
    if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=uemail.length) 
 	      {
 	   bootbox.alert("Enter the valid email ID");
 	  return false;
 	      }
       if(umobno=='')
 	    	  {
    	      bootbox.alert( "Enter the mobile no");
 		      return false;
 	    	  }
 	    if(!umobno.match(phoneno))
         {  
 	    	bootbox.alert( "Enter the valid mobile no");
            return false;
         }   */
 	   if(ut=="deafult")
	    	{
	    	bootbox.alert("Select one userType please");
	        return false;
		    }
 	   if(des=="deafult")
  	    {
     	 bootbox.alert("Select designation  please");
         return false;
	     }
 	   return true;
 	  
     }   
 	
  
  function clearMyForm()
	  {
	
		$("#updateUserOption").hide();
		$("#addUserOption").show();
		$("#updateMrOption").hide();
		 $("#addMrOption").show();
		document.getElementById("newConnectionMeterMaster").reset();
	    document.getElementById("mrnameEdit").reset();  
	  }  
  
	
	
	
	
	 //var specialKeys = new Array();
	 // specialKeys.push(8); //Backspace|| (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode)
	//  specialKeys.push(9); //Tab
	 // specialKeys.push(46); //Delete
	 // specialKeys.push(36); //Home
	 // specialKeys.push(35); //End
	 // specialKeys.push(37); //Left
	 // specialKeys.push(39); //Right
	 /*  function IsAlphaNumeric(e) {
		  $("#mrnamecheck").hide();
		  var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
		  var ret = ((keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122));
	      document.getElementById("error").style.display = ret ? "none" : "inline";
	      return ret;
	  } */
	 
	function deleteUser(param,opera)
	{
	
		var operation=parseInt(opera);
			
		  bootbox.confirm("Are you sure want to delete this record ?", function(result) {
			  if(result == true)
	             {
		          $("#delid").attr("value",operation);
		          $('form#delUser').attr('action','./detletUserDetails').submit();
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
              <div class="caption"><i class="fa fa-reorder"></i>My Profile</div>
             
              </div>
				<div class="portlet-body">
						  <div class="col-md-12">
   <form:form action="./updateMyProfile" modelAttribute="myUserProfile" commandName="myUserProfile" method="post" id="myUserProfileID">		  
													<table id="tableData2">
													
													<tr hidden="true">
														<td>Id</td>
													<!-- 	<td><font color="red"></font></td> -->
														<td><form:input path="id" id="userid"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="" name="userid"></form:input></td>
													</tr>

													<tr>
														<td>User Name</td>
														
														<!-- <td><font color="red">*</font></td> -->
														<td><form:input path="username" id="username"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="Enter your name" name="username" ondrop="return false;" maxlength="200" disabled="disabled" ></form:input>
											
																</td>
													</tr>
													
													<tr>
														<td>Password</td>
														<!-- <td><font color="red">*</font></td> -->
														<td><form:input path="userPassword" id="userPassword"
																class="form-control placeholder-no-fix" type="password"
																autocomplete="off" placeholder="Enter password" name="userPassword" maxlength="10" ></form:input>
											
																</td>
													</tr>
													
													<tr>
														
														
														<td>Confirm Password</td>
														<!-- <td><font color="red">*</font></td> -->
														<td><input type="password" id="cuserPassword" name="cuserPassword" class="form-control placeholder-no-fix" placeholder="Enter confirm password" maxlength="10"/>
														
														</td>
																
													</tr>
													  
							
													
													
														
												   
													
													
													</table>
										                     <div class="modal-footer">
															    
															<!--  <button class="btn blue pull-right" id="addUserOption" type="submit" onclick="return validation(this.form)" >Add User</button> -->  
															 <button type="submit" id="updateUserOption" class="btn blue pull-right" onclick="return validation(this.form)">Update User</button>
															
													</div>	
														
										 </form:form>
										 </div>
										 </div>
										 </div>


</div>