<%-- <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
  <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	    TableManaged.init();
   	 
   	    	   $('#system-Security').addClass('start active ,selected');
   	    	 $("#dash-board,#360d-view,#cumulative-Analysis,#seal-Manager,#cdf-Import,#cmri-Manager,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	
     	   });
  
  
  
    function check(param)
    {
    	$("#error").show();
    }
   
   
 
    function validation(form)
     {
	    var uname=form.username.value;
    	var uemail=form.userMailId.value;
    	var upwd=form.userPassword.value;
    	var cupwd=form.cuserPassword.value;
    	var umobno=form.userMobileNo.value;
    	var phoneno = /^\d{10}$/;  
        var atpos = uemail.indexOf("@");
   	    var dotpos = uemail.lastIndexOf(".");
   	    var utype=form.userType.value;
   
   	  
   	    if(uname=='')
   	    	{
   	    	//alert("Please Enter user name");
   	    	document.getElementById("usernamecheck").innerHTML = "Enter the user name";
   		    document.getElementById("username").focus();
   		    return false;
   		    }
 		 	  
   	    	document.getElementById("usernamecheck").innerHTML = " ";
   	    
   	  
   	   if(upwd=='')
    	{
    	//alert("Please Enter PWD");
    	document.getElementById("passwordcheck").innerHTML = "Enter the password";
	    document.getElementById("userPassword").focus();
	    return false;
    	}
   	  document.getElementById("passwordcheck").innerHTML = " ";
   	    
   	    if(cupwd=='')
   	    	{
   	    //	alert("Please Enter CPWD");
   	        document.getElementById("cpasswordcheck").innerHTML = "Enter the confirm password";
   		    document.getElementById("cuserPassword").focus();
   		    return false;
   	    	}
   	    document.getElementById("cpasswordcheck").innerHTML = " ";
   	    if(cupwd != upwd)
   	    {
		// alert("Paswords are not matching ");
	    	document.getElementById("cpasswordcheck").innerHTML = "Passwords are not matching";
		    document.getElementById("cuserPassword").focus();
		 return false;
		 }
   	   document.getElementById("cpasswordcheck").innerHTML = " ";
   	 
   	     if(uemail=='')
   	    	 {
   	    	 //alert("Please enter email id");
   	    	    document.getElementById("useremailcheck").innerHTML = "Enter the  email ID";
   	        	document.getElementById("userMailId").focus();
   	    	 return false;
   	    	 }
   	     document.getElementById("useremailcheck").innerHTML = " ";
   	 
 	      if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=uemail.length) 
 	      {
 	    	
 	    	 //alert("Email id is not valid");
 	    	 document.getElementById("useremailcheck").innerHTML = "Enter the valid email ID";
 	    	 document.getElementById("userMailId").focus();
 	         return false;
 	      }
 	     document.getElementById("useremailcheck").innerHTML = " ";
 		 
 	      if(umobno=='')
 	    	  {
 	    	// alert("Please enter mobile no");
 	    	  document.getElementById("umobilenodcheck").innerHTML = "Enter the mobile no";
 	    	  document.getElementById("userMobileNo").focus();
 	    	  return false;
 	    	  }
 	     document.getElementById("umobilenodcheck").innerHTML = " ";
 	    if(umobno.match(phoneno))
         {  
           return true;  
         }  
         else  
         {  
              // alert("Mobile number is not valid ");  
              document.getElementById("umobilenodcheck").innerHTML = "Enter the valid mobile no";
               document.getElementById("userMobileNo").focus();
               return false;  
         }   
 	   
 	    document.getElementById("umobilenodcheck").innerHTML = " ";
    	/*  
 	     if(utype == '-1')
    		{
    	
    		 document.getElementById("usertypecheck").innerHTML = "Please select the user type";
               document.getElementById("userType").focus();
               return false;  
    		}
    	document.getElementById("usertypecheck").innerHTML = " "; */
    } 
 
  
  </script>
<div class="page-content" >


<!-- BEGIN PAGE CONTENT-->
			
			<div class="row">
				<div class="col-md-12">
				
					
					
					<!-- Display Success Message -->
		           
		           
		           <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
				
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>User Access Management</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="btn-group">
									<button id="sample_editable_1_new" class="btn green"  data-toggle="modal" data-target="#stack1">
									Add New User<i class="fa fa-plus" ></i>
									</button>
								</div>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#">Print</a></li>
										<li><a href="#">Save as PDF</a></li>
										<li><a href="#">Export to Excel</a></li>
									</ul>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr>
										<th class="table-checkbox" hidden="true"><input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
										<th>Username</th>
										<th >Email</th>
										<th >Mobile No</th>
										<th >Access Level</th>
										<th >Status</th>
									
									</tr>
								</thead>
								<tbody>
								<c:forEach var="element" items="${UserList}">
									<tr class="odd gradeX">
										<td hidden="true"><input type="checkbox" class="checkboxes" value="1" /></td>
										<td>${element.username}</td>
										<td ><address><a href="mailto:webmaster@example.com">${element.userMailId}</a></address></td>
										<td>${element.userMobileNo}</td>
										<td>${element.userLevel}</td>
										<td ><span class="label label-sm label-success">${element.userStatus}</span></td> 
									</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
					</div>
					
					
		       
	<!-- END EXAMPLE TABLE PORTLET-->
					
					
					
					<!-- ADDING NEW USER POPUP FORM START -->
					
					<div id="stack1" class="modal fade" tabindex="-1" data-width="400">
						<div class="modal-dialog">
							<div class="modal-content">
								
								<div class="modal-body">
									<div class="row">
										<div class="col-md-12">
										
										
								
							
											
										 
											 <form:form action=""  name="form" commandName="" modelAttribute="" method="post" id="" enctype="multipart/form-data">
											
											<table>
											
									
			     							      
			
											       
												
													<tr hidden="true">
														<td>Id</td>
														<td><font color="red">*</font></td>
														<td><form:input path="id" id="userid"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="" name="userid"></form:input></td>
													</tr>

													<tr>
														<td>User Name</td>
														
														<td><font color="red">*</font></td>
														<td><form:input path="username" id="username"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="Enter your name" name="username"  ></form:input>
																<span id="usernamecheck" style="color:red;font-weight:bold;" ></span>
																</td>
													</tr>
													
													<tr>
														<td>Password</td>
														<td><font color="red">*</font></td>
														<td><form:input path="userPassword" id="userPassword"
																class="form-control placeholder-no-fix" type="password"
																autocomplete="off" placeholder="Enter password" name="userPassword" ></form:input>
																<span id="passwordcheck" style="color:red;font-weight:bold;" ></span>
																</td>
													</tr>
													
													<tr>
														
														
														<td>Confirm Password</td>
														<td><font color="red">*</font></td>
														<td><input type="password" id="cuserPassword" name="cuserPassword" class="form-control placeholder-no-fix" placeholder="Enter confirm password" />
														<span id="cpasswordcheck" style="color:red;font-weight:bold;" ></span>
														</td>
																
													</tr>
													  
													
													
													<tr>
														<td>Email</td>
														<td><font color="red">*</font></td>
														<td><form:input path="userMailId" id="userMailId"
														class="form-control placeholder-no-fix" type="text" 
														autocomplete="off" placeholder="Enter E-mail ID" name="userMailId"/>
													   <span id="useremailcheck" style="color:red;font-weight:bold;" ></span>
													   </td>
													  
													</tr>
												
												   <tr>
														<td>Mobile Number</td>
														<td><font color="red">*</font></td>
														<td><form:input path="userMobileNo" id="userMobileNo"
														class="form-control placeholder-no-fix"  type="text" 
								                        autocomplete="off" placeholder="Enter mobile number" name="userMobileNo" />
														<span id="umobilenodcheck" style="color:red;font-weight:bold;" ></span>
													   </td>
													</tr>
													
													
												   <tr>
														<td>User Type</td>
														<td><font color="red">*</font></td>
														<td><form:select path="userType"
																	id="userType"
																	class="form-control placeholder-no-fix" type="text"
																	autocomplete="off" placeholder="Select User type" name="userType" required="required">
													   			   <form:option value="-1" selected="true">Choose yours</form:option>
																	<form:option value="USER">USER</form:option>
																	<form:option value="ADMIN">ADMIN</form:option>
																	
															
													  </form:select><!-- <span id="usertypecheck" style="color:red;font-weight:bold;" ></span> --></td>
													</tr>
													
													
													
													
													 <tr hidden="true">
														<td>User Level</td>
														<td><font color="red">*</font></td>
														<td>
														
														<form:input path="userLevel" id="userLevel" 
														 class="form-control placeholder-no-fix" type="text" 
														autocomplete="off" placeholder="" name="userLevel" value="NONE"/>
														
													   </td>
													</tr>
													
													 <tr hidden="true">
														<td>User Status</td>
														<td><font color="red">*</font></td>
														<td>
														
														<form:input path="userStatus" id="userStatus"
														class="form-control placeholder-no-fix" type="text" 
														autocomplete="off" placeholder="" name="userStatus" value="NONE"/>
														
													   </td>
													</tr>
													
													
												<!-- 
													<tr>
													 <td>User Image</td>
              											<td><font color="red">*</font></td>
             											 <td><input path="userImage"  id="userImage"
                											class="form-control placeholder-no-fix" type="file"
               												autocomplete="off" placeholder="" name="userImage"/></td>
												     </tr> -->
									         	</table>
																	
												
												
												
												
												
														
												<div class="modal-footer">
													 <button type="button" data-dismiss="modal" class="btn">Close</button>
													 <button type ="submit" class="btn blue pull-right" style="display: block;" id="addOption" onclick="return validation(this.form)">Add User</button>
									
												</div>
											</form:form>
									 
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- END ADDING NEW USER POPUP FORM START -->
					
				</div>
			</div>
</div> --%>