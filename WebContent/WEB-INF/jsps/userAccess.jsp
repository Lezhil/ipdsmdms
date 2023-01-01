<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>



<%
String editRight = (String) request.getSession().getAttribute("editRights");
String viewRight = (String) request.getSession().getAttribute("viewRights");

System.out.println("Edit rights" +editRight);
System.out.println(viewRight);
%>

	<script type="text/javascript" src="./resources/assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="./resources/assets/plugins/select2/validate.min.js"></script>
<script type="text/javascript" src="./resources/assets/plugins/select2/bootstrap_multiselect.js"></script>
<script type="text/javascript" src="./resources/assets/plugins/bootstrap/js/bootstrap_multiselect.js"></script>





<c:set var="editRight" value="<%=editRight%>"></c:set>
<c:set var="viewRight" value="<%=viewRight%>"></c:set>
<script  type="text/javascript">

$(".page-content").ready(function()
    	   {  
	
    	     App.init();
    	  TableEditable.init();
    	 FormComponents.init();
    	
    	 $('#ADMINSideBarContents,#accessTypes,#userManagement').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
 	
    $('#multi-select-demo').multiselect({
        
        enableFiltering: true
    });
    loadSearchAndFilter('sample_2');
   
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
	
	  function editUser(param,opera){
		  
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
   	    var regex = /^[a-zA-Z]*$/;
   	    var pasregex= /^[a-zA-Z0-9]{8}$/;
   	    
   	    
      
   	   
   	  
   	    if(uname=='')
   	    	{
   	    	bootbox.alert("Enter the user name");
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
 

 
 	
 function getModuleNames()
	{
		 $.ajax({
				url : './ModulesNames',
			    	type:'GET',
			    	dataType:'json',
			    	success:function(response)
			    	{
			    	
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							html+="<option  value='"+response[i][4]+"'>"+response[i][2]+"</option>";
						}
						
						$("#moduleListMultiple").html(html);
						
			    	}
				}); 
		}
	
 function assignedRoles(id,userType,userRoles,viewexcess,editexcess)
	{

	   $('#EditnewUserType').val(userType);
		//$('#MainnewUserType').val(response[i][1]);


		var dataarray=userRoles.split(",");
		
		$("#modules_list_edit").val(dataarray);

		
		$("#EditviewAccessID").val(editexcess);
		$('#ChangeEditAccessID').val(viewexcess);
		
		 /* $.ajax({
				url : './assignedModulesNames',
			    	type:'GET',
			    	dataType:'json',
			    	data:{
						userType:userType

				    	},
			    	success:function(response)
			    	{
			    	
			    		var html='';
			    		html+="<option value=''></option>";
						for( var i=0;i<response.length;i++)
						{
							
							$('#EditnewUserType').val(response[i][1]);
							$('#MainnewUserType').val(response[i][1]);


							var dataarray=response[i][2].split(",");
							
							$("#modules_list_edit").val(dataarray);

							
							$("#EditviewAccessID").val(response[i][3]);
							$('#ChangeEditAccessID').val(response[i][4]);
							
							
						}
						
						
						 
			    	}
				}); */ 
		}

  function clearMyForm()
	  {
	  $("#subdivisionId").hide();
		$("#zoneId").hide();
		$("#divisionId").hide();
		$("#circleId").hide();
		$("#updateUserOption").hide();
		$("#addUserOption").show();
		$("#updateMrOption").hide();
		$("#addMrOption").show();
		$("#passwordTrId").attr("hidden",false);	
		$("#confirmPasswordTrId").attr("hidden",false);
		document.getElementById("newConnectionMeterMaster").reset();
	    document.getElementById("mrnameEdit").reset();  
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

	  function userTypeValidation()
	  {
			
		
		  uType=document.getElementById("newUserType").value;
		//  moduleList=document.getElementById("moduleListMultiple").value;
		  
		 var  moduleList	= $("#addMudules").val();	
		/*   var   viewAccess	= $("#viewAccessID").val();	 
		  var   editAccess	= $("#editAccessID").val(); */	 

		  
		  var regex = /^[a-zA-Z ]*$/;
		  if(document.getElementById("newUserType").value == "" || document.getElementById("newUserType").value == null)
			  {
			  	bootbox.alert("please enter UserType ...");
			  	return false;
			  }
		
		  if(!uType.match(regex))
			  {
			  
			  bootbox.alert("enter only alphabets");
			  return false;
			  }

			if(moduleList == ''){
				 bootbox.alert("Select Modules");
				  return false;
				}
        
	        $.ajax({
		   		url:"./checkExcistingUserType",
		   		type:"get",
		   		data:{
		   			uType:uType,
		   			   			
		   		},
		   		success:function(res){
		   		
		   		if(res == 'availabale'){
		   		bootbox.alert('User type already exist');	
		   		
		   		return false;
		   		}else {
		   			$.ajax({
		   		   		url:"./addUserTypeModuleData",
		   		   		type:"get",
		   		   		data:{
		   		   			uType:uType,
		   		   			moduleList:moduleList
		   				
		   				 
		   		   		},
		   		   		success:function(res){
		   		   		alert("User Type has been added successful");
		   		   	location.reload(true); 
		   		   	$('#stack2').hide();
		   		
		   		   		}
		   		   	});
		   		}
		   		}
		   		});
	        
	        
	      
	       
	       
	    //   $("#addUserTypeDataFormId").attr("action","./addUserTypeData").submit();
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
						<div class="portlet-title line">
							<div class="caption"><i class="fa fa-bell-o"></i>User Access Type
							
                             </div>
							<div class="tools">
								<a href="" class="collapse"></a>
								<a href="" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
						
								<div class="btn-group">
									<!-- 	<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" onclick="clearMyForm()">
									      Add User <i class="fa fa-plus"></i>
									    </button> -->
									    &nbsp;&nbsp;&nbsp;
									    <button class="btn blue" data-target="#stack2" data-toggle="modal" onclick="getModuleNames()" id="addUserType" >
									      Add UserType <i class="fa fa-plus"></i>
									    </button>
								         </div><br/><br/>
							<!--BEGIN TABS-->
						
										
								
							<table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th hidden="true"> id</th>
										<th>ID</th>
										<th>User Type</th>
										<th>Sub-Module Name</th>
										<!-- <th>Designation</th> -->
										
										
									    <th>Edit</th>
										<th>Delete</th>
									
            					</tr>
								</thead>
								<tbody>
									
							<c:forEach var="app" items="${resultList}" >
							<tr>
								<td>${app[0]}</td>
								<td>${app[1]}</td>
								<td><input type="text" value="${app[2]}"  id="newUserTypeInput" readonly="readonly" class="form-control"></td>
								
								
								<td><c:if test = "${editRight eq 'yes'}"><button type="button" id= "editRoleID" class="btn blue pull-left" data-toggle="modal" data-target="#stack3" onclick="return assignedRoles('${app[0]}','${app[1]}','${app[2]}','${app[3]}','${app[4]}')" >Edit</button></c:if></td>
								<td><c:if test = "${editRight eq 'yes'}"><button type="button" id= "deleteRole" class="btn blue pull-left" onclick="deleteRole(${app[0]},'${app[1]}')">Delete</button></c:if></td>
								
								<%--- <td>${app.userName}</td>
								<td>${app.dateStamp}</td>--%>
							</tr>
						</c:forEach>
									 							
								</tbody>
							</table>
							<form method="post" id="delUser">
							  <input type="hidden" id="delid" name="delUId">
							</form>
							<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">User Management</h4>
										</div>
										<div class="modal-body">
						 	               <form:form action="./addNewUser" commandName="addUserAndMrName" method="post" id="newConnectionMeterMaster">		  
													<table id="tableData2">
													
													
								
													
													</table>
										                     <div class="modal-footer">
															    
															 <button class="btn blue pull-right" id="addUserOption" type="submit" onclick="return validation(this.form)" >Add User</button>  
															 <button type="button" id="updateUserOption" class="btn blue pull-right" onclick="updateUserData(this.form)">Update User</button>
															 <button type="button" data-dismiss="modal" class="btn">Cancel</button>
													</div>	
														
										 </form:form>
							
							</div>
							</div>
							</div>
							</div>
							  <div id="stack3" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
				  <div class="modal-dialog">
				  <div class="modal-content">
				  <div class="modal-header">
				  <button type="button" class="close" data-dismiss="modal" ></button>
				  <h4><b>Edit User Type Details</b></h4>
				  </div>
				  <div class="modal-body">
				  
				
				 
				  
				
				  
											  <table >
													<input type="hidden" name="newUserType" multiple="multiple" id="MainnewUserType" class="form-control">
													<tr>
													<td>UserType</td>
													
													<th><font color="red">*</font></th>
													<th><input type="text" name="newUserType" multiple="multiple" id="EditnewUserType"  readonly="readonly" class="form-control"></th>
													</tr>
													
													
													
													<tr>	
													<td >Select Modules</td>
													<th><font color="red">*</font></th>
														<th id="feederDivMultiple">
															<select	class="multi-select-demo" id="modules_list_edit" multiple="multiple" name="modules_list_edit"  placeholder= "Select Module";  > 
																	<option value="">Select Modules</option>
																	<c:forEach items="${moduleList}" var="elements">
																						<option value="${elements[4]}">${elements[2]}</option>
																					</c:forEach>
															</select>
													</th>
													
													</tr>
													
												<!-- 	<tr>
													<td >View Access</td>
													<th><font color="red">*</font></th>
														<th id="viewAccess">
															<select	class="form-control select2 " id="EditviewAccessID"   name="modules" >
																	
																<option value="yes">Yes</option>
																<option value="no">No</option>	
															</select>
													</th>
													
													</tr>
													<tr>
													<td >Edit Access</td>
													<th><font color="red">*</font></th>
														<th id="editAccess">
															<select	class="form-control select2 " id="ChangeEditAccessID"  name="modules" >
																	<option value="yes">Yes</option>
																<option value="no">No</option>
															</select>
													</th>
													</tr> -->
														<%-- <td >Select Modules</td>
														<th><font color="red">*</font></th>
													<th id="roles">
													
													<select	class="form-control select2 " id="select2_sample2" multiple="multiple"  name="zone" onchange="showCircle(this.value);">
																	<option value="">Select Modules</option>
																	<c:forEach items="${moduleList}" var="elements">
																						<option value="${elements[3]}">${elements[2]}</option>
																					</c:forEach>
															</select>
														</th> --%>
													
																			
													  
													</table>
				  
				<!-- 
                             <table class="table table-striped table-bordered table-hover" id="sample_2">
								<thead>									
									<tr>										
										<th hidden="true"> id</th>
										<th>ID</th>
										<th>Access Types</th>
										<th>Sub-Module Name</th>
										<th>Designation</th>
									  
            					</tr>
								</thead>
								<tbody id="newUserTypeInputEdit">
												
								</tbody>
							</table>
	          		 -->
		
	       
               
               <div class="modal-footer">
                           <button class="btn red pull-left" type="button" data-dismiss="modal" >Cancel</button>
                           <button class="btn blue pull-right"   id="modify" type="button" onclick="modifyNew()">Update</button>
                </div>
              		  
					  </div>	
					</div>  
				</div>	  
				</div>	
							<div id="stack2" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">Add UserType</h4>
										</div>
										<div class="modal-body">
						 	               <%-- <form action="./addUserTypeData"  method="post" id="addUserTypeDataFormId"> 		 --%>  
												<input type="hidden" name="userAccessTypeRoles" id="userAccessTypeRoles" >
												<table >
													
													<tr>
													<td>UserType</td>
													<th><font color="red">*</font></th>
													<th><input type="text" name="newUserType" multiple="multiple" id="newUserType" class="form-control"></th>
													</tr>
													
													
													
													<tr>	
													<td >Select Modules</td>
													<th><font color="red">*</font></th>
														<th id="addMudulesTh">
															<select	class="multi-select-demo" id="addMudules" multiple="multiple" name="modules"  placeholder= "Select Module";  > 
																	<option value="">Select Modules</option>
																	<c:forEach items="${moduleList}" var="elements">
																						<option value="${elements[4]}">${elements[2]}</option>
																					</c:forEach>
															</select>
													</th>
													
													</tr>
													
												<!-- 	<tr>
													<td >View Access</td>
													<th><font color="red">*</font></th>
														<th id="viewAccess">
															<select	class="form-control select2 " id="viewAccessID"   name="modules" >
																	
																<option value="yes">Yes</option>
																<option value="no">No</option>	
															</select>
													</th>
													
													</tr>
													<tr>
													<td >Edit Access</td>
													<th><font color="red">*</font></th>
														<th id="editAccess">
															<select	class="form-control select2 " id="editAccessID"  name="modules" >
																	<option value="yes">Yes</option>
																<option value="no">No</option>
															</select>
													</th>
													</tr> -->
														<%-- <td >Select Modules</td>
														<th><font color="red">*</font></th>
													<th id="roles">
													
													<select	class="form-control select2 " id="select2_sample2" multiple="multiple"  name="zone" onchange="showCircle(this.value);">
																	<option value="">Select Modules</option>
																	<c:forEach items="${moduleList}" var="elements">
																						<option value="${elements[3]}">${elements[2]}</option>
																					</c:forEach>
															</select>
														</th> --%>
													
														</tr>					
													  
													</table>
										            <div class="modal-footer">
														<button class="btn blue pull-right" id="addUserOption" type="submit" onclick="return userTypeValidation()" >Add UserType</button>  
														<button type="button" data-dismiss="modal" class="btn">Cancel</button>
													</div>	
														
														
														                     
                
														
														
														
									<%-- 	 </form> --%>
							
							</div>
							</div>
							</div>
							</div>
							
							
							
										</div>
									</div>
									

									
								</div>
								
								
							
<script>
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
	  						for (var i=0; i<response.length; i++) {
	  							
	  							
	  							 var name = response[i];
	  		                    
	  		                    $("#officeId").append("<option >"+name+"</option>");
	  							
	  							/* $('#officeId').val(response[i]); */
	  							}
											
									 
	  												}
	  			});
	
};

function modifyNew(){

	
	var   mainUserType=document.getElementById("MainnewUserType").value;
	var   uTypeEdit=document.getElementById("EditnewUserType").value;
	var   moduleListEdit	=$("#modules_list_edit").val();	 
/* 	var   viewAccessEdit	= $("#EditviewAccessID").val();	 
	var   editAccessEdit	= $("#ChangeEditAccessID").val();	 */ 



	
    
   			$.ajax({
   		   		url:"./updateTypeModuleData",
   		   		type:"get",
   		   		data:{
   	   		   		mainUserType:mainUserType,
   		   			uType:uTypeEdit,
   		   			moduleList:moduleListEdit
   				  
   				 
   		   		},
   		   		success:function(res){
   		   		alert("User Type has been updated successful");
   		   	$('#stack3').hide();
   		 window.location.reload();
   		   		}
   		   	});
   		
   		
	
}


function deleteRole(idValue,accType)
{
	  
	 
	var id = idValue;
	var userType = accType;
	bootbox.confirm("Are you sure want to delete UserType ?", function(result) {


		  if(result == true){
	 $.ajax(
	  			{
	  					type : "GET",
	  					url :  './assignedtoUser',
	  					dataType : "text",
	  					data : {
	  						userType:userType
						},
	  					success : function(response){
	  						
	  						
						if(response == 'availabale'){
							bootbox.alert("UserType is assigned to user, So unable to delete" );
							$('#stack2').hide();
						}	else{
							 $.ajax(
					 	  			{
					 	  					type : "GET",
					 	  					url :  './deleteUsertype',
					 	  					dataType : "text",
					 	  					data : {
					 	  						id:id
					 	  						
					 						},
					 	  					success : function(response){
					 	  						
					 	  						
					 	  						if(response == 'Success'){
					 								alert("UserType is deleted successfully" );
					 								location.reload(true); 
					 							}else{
					 								bootbox.alert("Unable to delete userType, Please try again" );
					 							}	
					 	  						
					 	  						
					 	  					}							
					 	  		});	 			
						}		 
	  											
	  					}
	  			});	
		  }
	 }); 

};
	
	

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

</script>
