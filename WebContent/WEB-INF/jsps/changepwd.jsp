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
		
		//$("#cuserPassword").val('${myUserProfile.userPassword}');
	});
	
 function validation()
     {
	   
    	var upwd=$("#userPassword").val();
    	var cupwd=$("#cuserPassword").val();
   	    
   	   
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
   	 if(cupwd == upwd)
	    {
   		 $.ajax({
   			type:'post',
   			url:'./passwordUpdation',
   			data: {
   				pwd:upwd
   			},
   		success : function(response){
   			if(response='succ'){
   				bootbox.alert( "Your password has been changed successfully! Thank you. ");
   			}
   			
   		}
   		
   			 
   		 });
   		 
	    	
		   
		 }
 	   
 	  
     }   
  
</script>

<div class="page-content" >
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
				
<div class="portlet box blue">
            
       <div class="portlet-title">
              <div class="caption"><i class="fa fa-reorder"></i>Change Password</div>
             
              </div>
				<div class="portlet-body">
						  <div class="col-md-12">
   <%-- <form:form action="./updateMyProfile" modelAttribute="myUserProfile" commandName="myUserProfile" method="post" id="myUserProfileID">		 --%>  
													<table id="tableData2">
													
													<tr hidden="true">
														<td>Id</td>
													<!-- 	<td><font color="red"></font></td> -->
														<td><input  id="userid"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="" name="userid"></input></td>
													</tr>

													<!-- <tr>
														<td>User Name</td>
														
														<td><font color="red">*</font></td>
														<td><input  id="username"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="Enter your name" name="username" ondrop="return false;" maxlength="200" disabled="disabled" ></input>
											
																</td>
													</tr> -->
													
													<tr>
														<td>New Password</td>
														<!-- <td><font color="red">*</font></td> -->
														<td><input  id="userPassword"
																class="form-control placeholder-no-fix" type="password"
																autocomplete="off" placeholder="Enter password" name="userPassword" maxlength="10" ></input>
											
																</td>
													</tr>
													
													<tr>
														<td>Confirm New Password</td>
														<!-- <td><font color="red">*</font></td> -->
														<td><input type="password" autocomplete="off" id="cuserPassword" name="cuserPassword" class="form-control placeholder-no-fix" placeholder="Enter confirm password" maxlength="10"/>
														</td>
																
													</tr>
													
													</table>
										                     <div class="modal-footer">
															 <button type="submit" id="updateUserOption" class="btn blue pull-right" onclick="return validation()">Change Password</button>
															
													</div>	
														
										<%--  </form:form> --%>
										 </div>
										 </div>
										 </div>


</div>