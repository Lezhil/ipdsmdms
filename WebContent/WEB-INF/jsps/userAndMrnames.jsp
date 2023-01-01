<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script  type="text/javascript">
$(".page-content").ready(function()
    	   {  
		
    	     App.init();
    	  TableEditable.init();
    	 FormComponents.init();
    	 $('#ADMINSideBarContents,#jvvnlUsersId,#userManagement').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
 	loadSearchAndFilter1('sample_2');		
	});
	
	
	function updateMrname(form)
	{
		
	        var result1=myMrnameValidate(form);
	        if(result1)
	           $('#mrnameEdit').attr('action','./updateMrname').submit();
	        else
	        	return false;
	}
	function updateUserData(form)
	{
		   var result=validation(form);
           if(result) 
	         $('#newConnectionMeterMaster').attr('action','./updateUserDetails').submit();
            else
        	   return false; 
	}	

	function addNewUserValidation(form)
	{
		   var result=validation(form);
		   var uname=form.username.value;
		   
           if(result) {

        	   $.ajax(
        	 			{
        	 					type : "GET",
        	 					url :  './isUserNameExists',
        	 					dataType : "text",
        	 					data : {
        	 						userName:uname
        	 						},
        	 					success : function(response){
        	 						
        	 					 		if(response == 'exits'){
        	 								bootbox.alert('userName already exits, please try with some other username');
        	 								$("#username").val("");
        	 					return false;
        	 					 		}
        	 					else{
        	 						$('#newConnectionMeterMaster').attr('action','./addNewUser').submit();
            	 					}
        	 						 		
        	 					}
        	 			});
       	 			

           }
	         
            else
        	   return false; 
	}	

	

	/* function addNewUser(form)
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
						
							
								$("#Editusername").val(response.username);
								$("#Editname").val(response.name);					    	
								$("#EditmobileNo").val(response.mobileNo);
					    		$("#EditemailId").val(response.emailId);

					    		$("#EdituserType").val(response.userType);
					    		
					    		/* $("#Editdiscom").val(response.discom); */
					    		
					    		$("#OldEditofficeType").val(response.officeType);
					    		$("#codeInputEdit").val(response.office);
					    		$("#fixedcodeInputEdit").val(response.office);
					    		$("#EditaccessType").val(response.editAccess);
					    		
					    		$("#id").val(response.id);
					    		
					    		var officeType = response.officeType;
					    		var officeCode =  response.office;	

					    	
					    	$.ajax(
						  			{
						  					type : "GET",
						  					url :  './getLocationNames',
						  					dataType : "text",
						  					data : {
						  						officeCode:officeCode,
						  						officeType:officeType
								            	 
											},
						  					success : function(res){
						  						 
											$("#oldOffice").val(res);
						  					}
												 
																					
						  			});
					    		
	  												}
	  			});
	      $('#'+param).attr("data-toggle", "modal");
		  $('#'+param).attr("data-target","#stack2"); 
		  
	  	    
	  }; 
	
	
	  
	
	
 function validation(form)
     {
	 
	
	    var uname=form.username.value;
	    var name=form.name.value;
    	//var uemail=form.userMailId.value;
    	var upwd=form.userPassword.value;
    	var cupwd=form.cuserPassword.value;
    	//var umobno=form.userMobileNo.value;
    	var ut=form.userType.value;
    	/* var des=form.designation.value; */
    	//var phoneno = /^\d{10}$/;  
       // var atpos = uemail.indexOf("@");
   	   // var dotpos = uemail.lastIndexOf(".");
   	   // var utype=form.userType.value;
   	    var regex = /^[a-zA-Z]*$/;
   	    var pasregex= /^[a-zA-Z0-9]{8}$/;
   	    
   	    var location =   form.officeType.value;
        var office =form.officeId.value;
   	    var editaccess =form.editAccess.value;
   	  
   	   
   	   
   	   
   	   
   	    if(uname=='')
   	    	{
   	    	bootbox.alert("Enter the user login name");
   	        return false;
   		    }
   	    
   	   /*  if(!uname.match(regex))
   	    	{
   	    	bootbox.alert("Enter alphabets only");
   	        return false;
   	    	
   	    	} */
   	     if(!uname.match(regex))
	    	{
	    	bootbox.alert("user name has to contains only alphabets ");
	        return false;
		    }

   	    	if(name == '')
   	    	{
   	    	bootbox.alert("Enter the Name");
   	        return false;
   		    }
   	    	
   	    	
		    
   	  if(upwd=='')
    	{
   		bootbox.alert( "Enter the password it should not be empty");
	    return false;
    	} 
   	 
   	       if(checkStrength(upwd) !=true)
   	    		{
	    			bootbox.alert( "please enter at least one uppercase letter, one lowercase letter, one number and one special character:");
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
 	   if(ut == "")
	    	{
	    	bootbox.alert("Select one userType please");
	        return false;
		    }
        
	 if(location=="")
		{
		bootbox.alert("Select Location");
	 return false;
	 }
	 
	 if(office=="")
		{
		bootbox.alert("Select Office");
	 return false;
	 }
	 
	 if(editaccess== "")
		{
		bootbox.alert("Select Edit access");
	 return false;
	 }

	     
 	   return true;
 	  
     }  
 
 
 
 function checkStrength(password){
    
	  
		var strength = 0;
		
		if (password.length < 5) 
		{  
			return false;	
		}
		
		if (password.length > 5) 
			strength += 1;
		
		 
		if (password.match(/([a-z].*[a-z])|([a-z].*[a-z])/))  strength += 1;
		
		if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) 
			strength += 1 ;
		if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  strength += 1;
		
		
		if (strength>=4 )
		{
			return true;		
		}
		
		else
		{
			return false;
		}
	}
 
 	
  
  function clearMyForm()
	  {
	  $("#userName").hide();
		/* $("#zoneId").hide();
		$("#divisionId").hide();
		$("#circleId").hide();
		$("#updateUserOption").hide();
		$("#addUserOption").show();
		$("#updateMrOption").hide();
		$("#addMrOption").show(); */
		/* $("#passwordTrId").attr("hidden",false);	
		$("#confirmPasswordTrId").attr("hidden",false);
		document.getElementById("newConnectionMeterMaster").reset();
	    document.getElementById("mrnameEdit").reset();   */
	  }  
  
	function myMrnameValidate(form)
	{
		var regex = /^[a-zA-Z ]*$/;
		var mrnam=form.mrname.value;
		if(mrnam.trim()=='')
			{
			 $("#error").hide();
			 bootbox.alert("Enter the Mrname please");
             return false;  
			}
		if(!mrnam.match(regex))
			{
			 bootbox.alert("Enter the alphabets please");
             return false; 
			}
		
		
		  return true;
	}
	
	
	
	
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
					 <span style="color:green" >${msg}</span>
			       </div>
	                </c:if>
	                
	                	
		          <!-- End Success Message -->
		          
		          	<!-- Display Success Message -->
		           
		           
		     
	                
	                	
		          <!-- End Success Message -->
				
    <div class="portlet box blue">
						<div class="portlet-title line">
							<div class="caption"><i class="fa fa-bell-o"></i>User Management
							
                             </div>
							<div class="tools">
								<a href="" class="collapse"></a>
								<a href="" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
						
								<div class="btn-group">
										<button class="btn green" data-target="#stack12" data-toggle="modal"  id="addData" onclick ="clearMyForm()">
									      Add User <i class="fa fa-plus"></i>
									    </button>
									    &nbsp;&nbsp;&nbsp;
								
								         </div><br/><br/>
							<!--BEGIN TABS-->
						
										
								  <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<!-- <li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_2', 'Users List')">Export
										to Excel</a></li> -->
										<li><a href="#" id="excelExport"
									onclick="ExportToExcelUser()">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th hidden="true"> id</th>
										<th>Name</th>
										<th>UserName</th>
										<th>UserType</th>										
										<!-- <th>Discom</th> -->
										<th>Office Type</th>
										<th>Office</th>										
										<!-- <th>Designation</th> -->
									    <th>Edit</th>
										<th>Delete</th>
										
            					</tr>
								</thead>
								<tbody>
									
								<c:forEach var="element" items="${UserList}">
									<tr >
										<!-- <td hidden="true"><input type="checkbox" class="checkboxes" value="1"/></td> -->
										<td hidden="true">${element.id}</td>
										<td>${element.name}</td>
										<td>${element.username}</td>
										<td>${element.userType}</td>
										<%-- <td>${element.designation}</td> --%>
										
										<%-- <td>${element.discom}</td> --%>
										<td>${element.officeType}</td>
										<td>${element.office}</td>
										
										
										
										
									   <%--  <td><address><a href="mailto:webmaster@example.com">${element.userMailId}</a></address></td> --%> 
										<%-- <td>${element.userMobileNo}</td> --%>
										<%-- <td>${element.userLevel}</td> --%>
										<%-- <td><span class="label label-sm label-success">${element.userStatus}</span></td> --%> 
										<td><a href="#" onclick="edit(this.id,${element.id})" id="editData${element.id}">Edit</a></td>
										<td><a href="#" onclick="deleteUser(this.id,${element.id})" id="editData${element.id}">Delete</a></td>
										
									</tr>
									</c:forEach>
									 							
								</tbody>
							</table>
							<form method="post" id="delUser">
							  <input type="hidden" id="delid" name="delUId">
							</form>
							<div id="stack12" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><b>Add New User</b></h4>
										</div>
										<div class="modal-body">
						 	               <form:form action="./addNewUser" modelAttribute="addUserAndMrName" commandName="addUserAndMrName" method="post" id="newConnectionMeterMaster">		  
													<table id="tableData2">
													
													<tr hidden="true">
														<td>Id</td>
														<td><font color="red">*</font></td>
														<td><form:input path="id" id="userid"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="" name="userid"></form:input></td>
													</tr>
														<form:input path="office" id="codeInput" name="codeInput" type="hidden"></form:input>
												
												<tr>
														<td>Name</td>
														
														<td><font color="red">*</font></td>
														<td><form:input path="name" id="name"
																class="form-control placeholder-no-fix" type="text"
																autocomplete="off" placeholder="Enter your first and last name" name="name" ondrop="return false;" maxlength="200"  ></form:input>
											
																</td>
													</tr>
												
														
													<tr>
														<td>User Login Name</td>
														<td><font color="red">*</font></td>
														<td><form:input path="username" id="username"
																class="form-control placeholder-no-fix" type="text"
															autocomplete="off"	placeholder="Enter your name" name="username"  ondrop="return false;" maxlength="200"  ></form:input>
											
																</td>
													</tr>
													
													
													
													<tr id="passwordTrId">
														<td>Password</td>
														<td><font color="red">*</font></td>
														<td><form:input path="userPassword" id="userPassword"
																class="form-control placeholder-no-fix" type="password"
																autocomplete="off" placeholder="Enter password" name="userPassword" maxlength="10" ></form:input>
											
																</td>
													</tr>
													
													<tr id="confirmPasswordTrId">
														
														
														<td>Confirm Password</td>
														<td><font color="red">*</font></td>
														<td><input type="password" id="cuserPassword" name="cuserPassword" class="form-control placeholder-no-fix" placeholder="Enter confirm password" maxlength="10"/>
														
														</td>
																
													</tr>
													  
													  
													  <tr id="mobileNo">
														
														
														<td>Contact No</td>
														<td><font color="red">*</font></td>
														<td><form:input type="number"   path="mobileNo" id="mobileNo" name="mobileNo" class="form-control placeholder-no-fix"  placeholder="Enter Contact No"  maxlength="12"></form:input>
														
														</td>
																
													</tr>
													  
													  <tr id="emailId">
														
														
														<td>Enter Email</td>
														<td><font color="red">*</font></td>
														<td><form:input type="text" path="emailId"  id="emailId" name="emailId" class="form-control placeholder-no-fix" placeholder="Enter Email Id" maxlength="60"></form:input>
														
														</td>
																
													</tr>
													  
													
												   <tr>
														<td>User Type</td>
														<td><font color="red">*</font></td>
														<td><form:select path="userType"
																	id="userType"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select User type" name="userType" required="required">
													   			    <form:option value="">- Please Select -</form:option>
													   			    <c:forEach var="type" items="${userTypeList}">
													   			    <form:option value="${type.userType }">${type.userType }</form:option>
													   			    </c:forEach>
																	
															
													  </form:select>
												     </td>
													</tr>
													<%--   <tr>
														<td>DISCOM</td>
														<td><font color="red">*</font></td>
														<td><form:select path="discom"
																	id="discom"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select User type" name="discom" required="required">
													   			  <form:option value="0">- Please Select -</form:option>
													   			    <c:forEach var="type" items="${discom}">
													   			    <form:option value="${type}">${type}</form:option>
													   			    </c:forEach>
															
													  </form:select>
												     </td>
													</tr> --%>
														
												<%--    <tr>
														<td>Designation</td>
														<td><font color="red">*</font></td>
														<td><form:select path="designation"
																	id="designation"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select User type" name="designation" required="required">
													   			    <form:option value="0">- Please Select -</form:option>
													   			    <c:forEach var="type" items="${designationList}">
													   			    <form:option value="${type}">${type}</form:option>
													   			    </c:forEach>
																	
																	
																	
															
													  </form:select>
													
													  </td>
													</tr> --%>
													  <tr>
														<td>Location</td>
														<td><font color="red">*</font></td>
														<td><form:select path="officeType"
																	id="officeType"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select Office type" name="officeType" onchange="officeTypeSelect()"  required="required">
													   			  <form:option value="">- Please Select -</form:option>
																	<form:option value="corporate">CORPORATE</form:option>
																		<form:option value="region">REGION</form:option>
																	<form:option value="circle">CIRCLE</form:option>
																	<%-- <form:option value="zone">ZONE</form:option>  --%>
																	
																	<%-- <form:option value="division">DIVISION</form:option>
																	<form:option value="subdivision">SUBDIVISION</form:option> --%>
																	
														
													  </form:select>
													
													  </td>
													</tr>
												
													<tr id="officeCode" >
														<td> Select Office</td>
														<td><font color="red">*</font></td>
														<td><select id="officeId"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select User type" name="officeId" onchange= "return getCode(this.value)" required="required">
																	 <option value="default">- Please Select -<option>
													   			   <%--  <form:option value="0">- Please Select -</form:option>
													   			    <c:forEach var="type" items="${circleList}">
													   			    <form:option value="${type}">${type}</form:option>
													   			    </c:forEach> --%>
														 </select>
													
													  </td>
													</tr>
								
											<tr>
														<td>Edit Access</td>
														<td><font color="red">*</font></td>
														<td><form:select path="editAccess"
																	id="editAccess"
																	class="form-control placeholder-no-fix select2me" type="text"
																	autocomplete="off" placeholder="Select Office type" name="editAccess"   required="required">
													   			  <form:option value="">- Please Select -</form:option>
																  <form:option value="yes">Yes</form:option>
																 <form:option value="no">No</form:option>
																
													  </form:select>
													
													  </td>
													</tr>
								
													
													</table>
										                     <div class="modal-footer">
															    
															 <button class="btn blue pull-right" id="addUserOption" type="button" onclick="addNewUserValidation(this.form)" >Add User</button>  
															<!--  <button type="button" id="updateUserOption" class="btn blue pull-right" onclick="updateUserData(this.form)">Update User</button> -->
															 <button type="button" data-dismiss="modal" class="btn red pull-right">Cancel</button>
													</div>	
														
										 </form:form>
														
							</div>
							</div>
							</div>
							</div>
							
							
									<!-- Below code to handle EDIT functionality -->
									<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><b>Edit User Details</b></h4>
											
										</div>
										
										<div class="modal-body">
		    <div id="stack2id" >
		      <div class="row"><br>
		            <div class="col-md-6" >
                    User Name <input type="text" class="form-control" name="Editusername" id="Editusername" readonly="readonly"  >
                    </div>
                    <div class="col-md-6">
                    Name <input type="text" class="form-control" name="Editname" id="Editname" maxlength="20" placeholder="Enter Name" >
                    </div>
                    <input  id="codeInputEdit" name="codeInputEdit" type="hidden">
                     <input  id="fixedcodeInputEdit" name="fixedcodeInputEdit" type="hidden">
                    <input  id="id" name="id" type="hidden">
                   <br>
            </div>
              <div class="row"><br>
                       	 <div class="col-md-6">
		                     Mobile Number <input type="text" class="form-control" name="EditmobileNo" id="EditmobileNo"  placeholder="Enter Mobile No" >
		                     </div>
          				   
                      
                       	<div class="col-md-6">
                       	Email Id <font color="red">*</font> <input type="text" class="form-control" name="EditemailId" id="EditemailId"  placeholder="Enter Email Id " >
                     
                       	</div>
                       	
                 </div>     
           
               <div class="row"><br>
                       
                       	<div class="col-md-6">
                       	UserType <font color="red">*</font><select class="form-control" name="EdituserType" id="EdituserType" >
                     
		   			    <c:forEach var="type" items="${userTypeList}">
		   			    <option value="${type.userType }">${type.userType }</option>
		   			    </c:forEach>
                       	</select>
                       	</div>
                       	
                      <%--  	<div class="col-md-6">
                    	   DISCOM <font color="red">*</font><select class="form-control" name="Editdiscom" id="Editdiscom" >
                       	<option value="0">- Please Select -</option>
                       	     <c:forEach var="type" items="${discom}">
			   			    <option value="${type}">${type}</option>
			   			    </c:forEach>
		   			    
                       	</select>
                       	</div> --%>
                       	 	<div class="col-md-6">
                       Edit Access <select class="form-control" name="EditaccessType" id="EditaccessType" >
                    
                      
						 <option value="yes">Yes</option>
						<option value="no">No</option>
				<!-- 		<option value="division">DIVISION</option>
						<option value="subdivision">SUBDIVISION</option> -->
						 	  
						 
						 	  
						 	  
						 	  
                       	</select>
                       	</div>
              </div><br>
               
               <div class="row"><br>
                       
                       
                   <%--     	<div class="col-md-6">
                       	Designation <font color="red">*</font><select class="form-control" name="Editdesignation" id="Editdesignation" >
                       
                      <option value="0">- Please Select -</option>
	   			    <c:forEach var="type" items="${designationList}">
	   			    <option value="${type}">${type}
	   			    </option></c:forEach>
                       </select>
                       </div> --%>
                       
                       	<div class="col-md-6">
                      Existing 	Office Level <input type="text" class="form-control" name="OldEditofficeType"  id="OldEditofficeType" readonly="readonly" >
                   
                       	</div>
                       	    
                      <div class="col-md-6">
		                Existing Office <input type="text" class="form-control" name="oldOffice" id="oldOffice"  readonly="readonly"  >
                       	</div>
                      
              </div><br>
              
               <div class="row"><br>
               
               	<div class="col-md-6">
                       New Office Level <select class="form-control" name="EditofficeType" onchange="officeTypeSelectEdit()" id="EditofficeType" >
                      <option value="">- Please Select -</option>
                       	<option value="corporate">CORPORATE</option>
						<option value="region">REGION</option> 
						<option value="circle">CIRCLE</option>
				<!-- 		<option value="division">DIVISION</option>
						<option value="subdivision">SUBDIVISION</option> -->
						 	  
						 
						 	  
						 	  
						 	  
                       	</select>
                       	</div>
                 <div class="col-md-6">
                    	    New Office Name <select class="form-control"  name="EditofficeId" id="EditofficeId"  onchange= "return getCodeEdit(this.value)">
                       	<option value="">- Please Select -</option>
                       	
                      
		   			    
                       	</select>
                       	</div>
                </div><br>
           
         
           
           
           
              <div class="modal-footer">
                    <button type="button" data-dismiss="modal"  class="btn red pull-right">Cancel</button>
					<button class="btn blue pull-right" id="modifyId" type="submit"  value="" onclick="return modifyUserDetails();">Modify</button>
			  </div>
				</div>	
		    </div>
		</div>
			</div>							
							</div>
							</div>
							</div>	
							
							
										</div>
								
				<!-- 		<script >
						   var modelAttributeValue = '${userAdded}';
						   alert(modelAttributeValue);
						   if(modelAttributeValue != '' ){
						   alert(modelAttributeValue)
						   ;
						   }
						</script>		 -->
							
<script>
function ExportToExcelUser()
{
	

	window.location.href=("./userdetailsexcel");
}



function officeTypeSelect()
{
	 $("#officeId").empty();
	var officeType = document.getElementById("officeType").value;
	
	 $.ajax(
	  			{
	  					type : "GET",
	  					url :  './getlocationTypes',
	  					dataType : "json",
	  					data : {
	  						officeType:officeType
	  						
						},
	  					success : function(response){

	  						var html = '';
				    		html+="<option value=''>-Please Select-</option>";
	  						for (var i=0; i<response.length; i++) {
	  							 var name = response[i][0];
	  							 var value = response[i][1]; 
	  							html+="<option  value='"+name+"'>"+name+"</option>";
	  							}
	  						$("#officeId").html(html);	
	  						$('#officeId').select2();	 
											
						}
	  			});
}

function officeTypeSelectEdit()
{
	
	 $("#EditofficeId").empty();
	var officeType = document.getElementById("EditofficeType").value;


	
	 $.ajax(
	  			{
	  					type : "GET",
	  					url :  './getlocationTypes',
	  					dataType : "json",
	  					data : {
	  						officeType:officeType
	  						
						},

						
	  					success : function(response){
		  					
	  						var html = '';
				    		html+="<option value=''>-Please Select-</option>";
	  						for (var i=0; i<response.length; i++) {
	  						
	  							 var name = response[i][0];
	  							 var value = response[i][1]; 

	  							html+="<option  value='"+name+"'>"+name+"</option>";
	  							
	  							/* $('#officeId').val(response[i]); */
	  							}
											
	  						$("#EditofficeId").html(html);	
	  						$('#EditofficeId').select2();	 
	  												}
	  			});


}


function getCode(name)
{
	
		var type = document.getElementById("officeType").value;
			 $.ajax(
		  			{
		  					type : "GET",
		  					url :  './getLocationCodes',
		  					dataType : "json",
		  					data : {
		  						name:name,
		  						type:type
				            	 
							},
		  					success : function(response){
											
										  $('#codeInput').val(response);
		  												}
		  			});

};

function getCodeEdit(name)
{
	var type = document.getElementById("EditofficeType").value;
		 $.ajax(
  			{
			type : "GET",
			url :  './getLocationCodes',
			dataType : "json",
			data : {
				name:name,
				type:type
            	 
				},
				success : function(response){
						
					  $('#codeInputEdit').val(response);
											}
	  			});

	};

function modifyUserDetails(){

var userName = $("#Editusername").val();
var name = $("#Editname").val();
var userType =$("#EdituserType").val();

var mobileNo =$("#EditmobileNo").val();
var emailId =$("#EditemailId").val();

/* var discom =$("#Editdiscom").val(); */

var officeType =$("#OldEditofficeType").val();
var officeCode = $("#fixedcodeInputEdit").val();

var newofficeName = $("#EditofficeId").val(); 
var newOfficeType =  $("#EditofficeType").val();

var EditaccessType =  $("#EditaccessType").val();

if(newOfficeType !== '0'){
		if(newofficeName == ''){
			
			bootbox.alert('please select new office location');
			return false;
			}
		officeType = $("#EditofficeType").val();
		officeCode = $("#codeInputEdit").val();

		
		}


var id = $("#id").val();

if(name == ''){
	bootbox.alert('please enter name');
	return false;
}
if(userType == ''){
	bootbox.alert('please Select  usertype');
	return false;
}

if(mobileNo == ''){
	bootbox.alert('please enter Mobile no');
	return false;
}
if(emailId == ''){
	bootbox.alert('please enter email');
	return false;
}
if(officeType == ''){
	bootbox.alert('please Select office type');
	return false;
}
if(officeCode == ''){
	bootbox.alert('please Select office code');
	return false;
}
$.ajax(
			{
			type : "GET",
			url :  './updateUserDetailsNew',
			dataType : "text",
			data : {
				userName:userName,
				name:name,
				userType:userType,
				mobileNo:mobileNo,
				emailId:emailId,
				officeType:officeType,
				officeCode:officeCode,
				EditaccessType:EditaccessType,
				id:id
	            	 
				},
				success : function(response){
					
					if(response == "success"){
					alert("UserDetails Updated Successfully");
					$('#stack2').hide();
					location.reload(true); 
						}else{
						bootbox.alert("Failed to update, please try after sometimes");
						$('#stack2').hide();
						
					}
		}
			});

}

</script>
