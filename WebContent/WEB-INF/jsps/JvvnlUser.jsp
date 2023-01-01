<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <head>
  <script src=”https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js”></script>
  <script src="<c:url value='/resources/assets/scripts/table-editable.js'/>" type="text/javascript"></script>
  </head>

<script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {  
			
   	    	     App.init();
   	    	  TableEditable.init();
   	    	 FormComponents.init();
   	  	$('#ADMINSideBarContents,#jvvnlUsersIdn').addClass('start active ,selected');
	    	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	 	
	    	
   	    	$('#addData').click(					  
   				  function(){ 
   						  		 
   						 		 document.getElementById("newJvvnlUsers").reset();
   			   	    	    	 document.getElementById('updateOption').style.display='none';
   			 					 document.getElementById('addOption').style.display='block'; 
   			 					 
   	   	    	          }
   				  );
   	   });
  
 
  
</script>
  
<style>
.input-medium {
    width: 160px !important;
}
</style>

<div class="page-content">

	<div class="row">
		<c:if test="${results ne 'notDisplay'}">
				<div  class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
			</c:if>
 
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user fa-lg"></i>AMR USER</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<!-- <a href="javascript:;" class="reload"></a> -->
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<div class="btn-group">
									<button id="addData" class="btn green"  data-toggle="modal" data-target="#stack1">
									Add AMR Users <i class="fa fa-plus" ></i>
									</button>
								</div>
							
							
							<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li id="print"><a href="#">Print</a></li>										
										<li><a href="#" onclick="tableToExcel('sample_3','Export Event Data');">Export to Excel</a></li>
										<!--  -->
									</ul>
								</div>
								<BR><BR>	
								
									<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								    <th>EDIT</th>
								    <th>SI NO.</th>
								    <th hidden="true">userId</th>
								    <th>USER NAME</th>
									<!-- <th>USER_LOGIN_NAME</th> -->
									<th>DESIGNATION</th>
									<!-- <th>PASSWORD</th> -->
									<th>USER TYPE</th>
									<!-- <th>OFFICE_TYPE</th>
									<th>OFFICE_CODE</th>
									<th>CIRCLE</th>
									<th>DIVISION</th>
									<th>Sub-DIVSION</th>
									<th>DELETE</th> -->
								</tr>
						</thead>
						 <tbody>
						<c:set var="count" value="1" scope="page" />
							<c:forEach var="element" items="${jvvnlUserList}">
								<tr>
									<td><a href="#" onclick="edit(this.id,${element.id})"
										id="editData${element.id}">Edit</a></td>
                                     <td>${count}</td>
                                     <td>${element.username}</td>
                                      <td>${element.designation}</td>
                                       <td>${element.userType}</td>
                                     
									<%-- <td hidden="true">${element.userId}</td>
									<td>${element.user_name}</td>
									<td>${element.user_login_name}</td>
									<td>${element.designation}</td>
									
									<td>${element.office_type}</td>
									<td>${element.office_code}</td>
									<td>${element.circle}</td>
                                    <td>${element.division}</td>
									<td>${element.sdoname}</td>
									<td><a href="#" onclick="delUsers(this.id,${element.userId})" id="deleteData${element.userId}">Delete</a></td>
								 --%></tr>
								<c:set var="count" value="${count+1}" scope="page" />
							</c:forEach>

						</tbody> 
					</table>		
						</div>
					</div>
					
					
					
					<!-- END EXAMPLE TABLE PORTLET-->
					
					
					<div id="stack1" class="modal fade"  data-backdrop="static">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title">Add New AMR Users</h4>
								<!-- <p align="right" class="padding"><font color="red">*</font>&nbsp;Indicates mandatory fields.
									</p> -->
							</div>

				
							
							<div class="modal-body" id='mainDiv'>
								<div class="row">
									<div class="col-md-12">


									<form:form action="./addNewJvvnlUsers"
										modelAttribute="newJvvnlUsers" commandName="newJvvnlUsers"
										method="post" id="newJvvnlUsers" enctype="multipart/form-data">

										<table>
											<tr hidden="true">
												<td>User ID</td>

												<td><form:input path="userId" id="userId"
														class="form-control placeholder-no-fix" type="text"
														autocomplete="off" placeholder="" name="userId"></form:input></td>
											</tr>
											<tr>
												<td><font color="red">*</font></td>

												<td><div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-male color-purple"></i>
														</span>
														<form:input path="user_name" id="user_name" 
															class="form-control placeholder-no-fix" type="text"
															autocomplete="off" placeholder="user_name" name="user_name" ></form:input>


													</div></td>
											</tr>


											<tr>
												<td><font color="red">*</font></td>

												<td>
													<div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-user"></i>
														</span>
														<form:input path="user_login_name" id="user_login_name"
															class="form-control placeholder-no-fix" type="text"
															autocomplete="off" placeholder="user_login_name" name="user_login_name" onchange="validateUserName(this.value)"></form:input>
													</div>
												</td>

											</tr>
											

											<tr>
												<td><font color="red">*</font></td>

												<td>
													<div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-lock"></i>
														</span>
														<form:input type="password" path="password" id="password" 
															class="form-control placeholder-no-fix" 
															autocomplete="off" placeholder="password"
															name="password"></form:input>
													</div>
												</td>
											</tr>
											
											<tr>
												<td><font color="red">*</font></td>

												<td>
													<div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-lock"></i>
														</span>
														<form:input type="password" path="" id="repeat_password" 
															class="form-control placeholder-no-fix" 
															autocomplete="off" placeholder="Confirm Password"
															name="repeat_password" onchange="PwdMatch(this.value)"></form:input>
													</div>
												</td>
											</tr>
											
											
											<tr>
												<td><font color="red">*</font></td>
												<td id="masterTd1"><div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-check"></i>
														</span> <select id="designation" path="designation" class="form-control" type="text"
															name="designation"  >
															<option value="0">Select Designation</option>
															<c:forEach items="${designations}" var="element">
																<option value="${element}">${element}</option>
															</c:forEach>
														</select>
													</div></td>
											</tr>

											<tr>
												<td><font color="red">*</font></td>
												<td id="masterTd2"><div class="input-group">
														<span class="input-group-addon"> <i
															class="fa fa-check"></i>
														</span> <select id="office_type" path ="office_type" class="form-control" type="text"
															name="office_type"  onchange="getOfficeCodes(this.value)">
															<option value="0">Select Office_Type</option>
															<c:forEach items="${Office_Types}" var="element">
																<option value="${element}">${element}</option>
															</c:forEach>
														</select>
													</div></td>
											</tr>

											<tr id="divisionTd" >

											</tr>
											
											
											
											
										</table>
										<div class="modal-footer left">
											<button type="button" data-dismiss="modal" class="btn red"
												id="closeData">Close</button>
											<button type="button" class="btn green pull-left" style="display: block;"
												id="addOption" onclick="validation()">Add User </button>
											 <button type="button" class="btn green pull-left" style="display: block;"
												id="updateOption" onclick="validation()">Modify
												User</button>
										</div>

									</form:form>
								</div>
								</div>

							</div>

						</div>
					</div>
					
				</div>
							
			</div>
  
  </div>
  </div>
	
			
			
				
				
				
				
				


<script>


function getOfficeCodes(param)
{
	
	var office_type=param;
	  $.ajax({
	    	url:'./getOfficeCodes',
	    	type:'GET',
	    	dataType:'json',
	    	data:{office_type:office_type},
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
				if(response!=null)
					{
		    	
	    		var division='';
	    		 division+="<td><font color='red'>*</font></td><td><div class='input-group'><span class='input-group-addon'><i class='fa fa-check'></i></span><select id='office_code' name='office_code' path='office_code' class='form-control' type='text' autocomplete='off' placeholder='' >  ><option value='0' >OfficeCodes</option>";
				for( var i=0;i<response.length;i++){
					
					
					division+="<option  value='"+response[i][1]+"'>"+response[i][0]+"</option>";
				}
				division+="</td></div></select><span></span>";
				$("#divisionTd").html(division);
				//$('#division').select2();
				$("#divisionTd").show();
	    	};
	    	}
	  }); 
}


function edit(param,opera)
{ 

	 var operation=parseInt(opera); 
	 document.getElementById("newJvvnlUsers").reset();
		  $.ajax(
					{
							type : "GET",
							url : "./editJvvnlUserList/" + operation,
							dataType : "json",
							cache:false,
							async:false,
							success : function(response)
										{ 
								
									
										    $('#userId').val(response.userId);
								            $('#user_name').val(response.user_name);
								            $('#user_login_name').val(response.user_login_name);
								            $('#password').val(response.password);
								            $('#office_type').val(response.office_type);
								            $('#office_code').val(response.office_code);
								            $('#designation').val(response.designation);
								            getOfficeCodes(response.office_type);
								            document.getElementById('updateOption').style.display='block';
										    document.getElementById('addOption').style.display = 'none';  
										  
									    }					
					});
		  
			$('#'+param).attr("data-toggle", "modal");//edit button
			$('#'+param).attr("data-target","#stack1"); //edit button
	
}

function delUsers(param,opera)
{ 
	
	 var operation=parseInt(opera); 
	
	 bootbox.confirm("Are you sure to delete selected records from JVVNL User details.", function(confirmed) 
	 {
        if(confirmed==true)
        {
        	 $.ajax(
			 {
				 	type : "GET",
					url : "./delJvvnlUserList/" + operation,
					dataType : "text",
					cache:false,
					async:false,
					success : function(response)
					{ 
						
						 if(response=='deleted')
				    	{
				    		window.location.href="./jvvnlUsers?flag=0";
				    	}
				    	else if(response=='notDeleted')
				    	{
				    		window.location.href="./jvvnlUsers?flag=1";
				    	}
				    	else
				    	{
				    		window.location.href="./jvvnlUsers";
				    	}   
						           
					}
			 
			 });
		 }
	});
	
}


function validateUserName(nm)
{	
	
	  $.ajax(
				{
					type : "GET",
					url : "./getDupUserName/", 
					dataType : "json",
					data:{name:nm},
					cache:false,
					success : function(response)
								{	      
										
										if(response!="")
										{
										  for (var i = 0; i < response.length; i++) 
										  {
											  
											bootbox.alert("<b>"+response[i].user_login_name+"</b> already exists.");
											document.getElementById("user_login_name").value="";
											 document.getElementById("password").value="";
											document.getElementById("repeat_password").value="";
											
										  	return false;
										   }
										}
										
								}	
			
				});
	 }

function validation()
{
	var pwd=$('#password').val();
	var pwdLen=pwd.length;
if( $('#user_name').val().trim()=='')
{
		bootbox.alert("Please enter the user_name...");
		return false;
}

if( $('#user_login_name').val().trim()=='')
{
		bootbox.alert("Please enter the user_login_name...");
		return false;
}
if( $('#password').val().trim()=='')
{
		bootbox.alert("Please Enter  Password");
		return false;
}
if(pwdLen<5)
	{
	bootbox.alert("Password atleast Contain Minimum 5 characters");
	 document.getElementById("password").value="";
	document.getElementById("repeat_password").value="";
	return false;
	}
if( $('#repeat_password').val().trim()=='')
{
		bootbox.alert("Please Enter Confirm Password");
		return false;
}

if( $('#designation').val().trim()==0)
{
		bootbox.alert("Please Select Designation");
		return false;
}


if( $('#office_type').val().trim()==0)
{
		bootbox.alert("Please Select office_type");
		return false;
}

if( $('#office_code').val().trim()==0)
{
		bootbox.alert("Please Select office_code");
		return false;
}
$("#newJvvnlUsers").submit();

}

function PwdMatch(Rpwd)
{
// alert("Rpwd-->"+Rpwd);
var pwd=$('#password').val();
// alert("pwd--"+pwd);

if(pwd!=Rpwd)
	{
	 bootbox.alert("Password Not Matched");
	 document.getElementById("password").value="";
	 document.getElementById("repeat_password").value="";
	 return false;
	}


}
 
</script>

  
<style>
.input-medium {
    width: 160px !important;
}
</style>




