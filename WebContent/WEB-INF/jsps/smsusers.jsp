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
   	  	$('#MDMSideBarContents,#newConnectionId,#sms').addClass('start active ,selected');
			$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');

	    	
   	    	
   	    	$('#addData').click(					  
   				  function(){ 
   						  		 
   						 		 document.getElementById("smstorecipients").reset();
   			   	    	    	 document.getElementById('updateOption').style.display='none';
   			 					 document.getElementById('addOption').style.display='block'; 
   			 					 
   	   	    	          }
   				  );
   	   });
  
  function getdivVal(param)
  {
  	$.ajax({
  		url : "./getdivBasedOncir",
			data:{param:param},
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
    			var html='';
	    		html+="<select id='division' name='division' class='form-control' onchange='return getsubdivValue(this.value);' type='text'><option value='deafult' >Division</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				
				$("#masterTd2").html(html);
				$('#subdiv').select2();
	    	}
		});
  }
  
  function getsubdivValue(param)
  {
	  var circle=$("#circle").val();
  	$.ajax({
  		url : "./getsubdivBasedOncir",
			data:{param:param,circle:circle},
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
    			var html='';
	    		html+="<select id='subdivision' name='subdivision' class='form-control' type='text'><option value='deafult' >Subdivision</option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				html+="</select><span></span>";
				$("#masterTd3").html(html);
				$('#subdiv').select2();
	    	}
		});
  }
  
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
							<div class="caption"><i class="fa fa-user fa-lg"></i>SMS/E-mail Recipient Configuration</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<!-- <a href="javascript:;" class="reload"></a> -->
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<div class="btn-group">
									<button id="addData" class="btn green"  data-toggle="modal" data-target="#stack1">
									Add Recipient <i class="fa fa-plus" ></i>
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
								    <th>RECIPIENT_NAME</th>
									<th>RECIPIENT_DESIGNATION</th>
									<th>RECIPIENT_EMAIL</th>
									<th>RECIPIENT_MOBILENO</th> 
									<th>MESSAGE_TYPE</th>
									<th>METER_TYPE</th>
									<th>CIRCLE</th>
									<th>DIVISION</th>
									<th>SUBDIVSION</th>
									<th>DELETE</th>
								</tr>
						</thead>
						 <tbody>
						<c:set var="count" value="1" scope="page" />
							<c:forEach var="element" items="${smsUserList}">
								<tr>
									<td><a href="#" onclick="edit(this.id,${element.id})"
										id="editData${element.id}">Edit</a></td>
                                     <td>${count}</td>
									<td hidden="true">${element.id}</td>
									<td>${element.r_name}</td>
									<td>${element.r_designation}</td>
									<td>${element.r_mail}</td>
									<td>${element.r_mobile_num}</td>
									<td>${element.message_type}</td>
									<td>${element.meter_type}</td>
									<td>${element.circle}</td>
                                    <td>${element.division}</td>
									<td>${element.subdivision}</td>
									<td><a href="#" onclick="delUsers(this.id,${element.id})" id="deleteData${element.id}">Delete</a></td>
								</tr>
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
								<h4 class="modal-title">Add New Recipient</h4>
								<!-- <p align="right" class="padding"><font color="red">*</font>&nbsp;Indicates mandatory fields.
									</p> -->
							</div>

				
							
							<div class="modal-body" id='mainDiv'>
								<div class="row">
									<div class="col-md-12">


									 <form:form action="./addNewRecipients"
										modelAttribute="smstorecipients" commandName="smstorecipients"
										method="post" id="smstorecipients" enctype="multipart/form-data">
										
										<table>
											<tr hidden="true">
												<td>User ID</td>
												<td><form:input path="id" id="id" class="form-control placeholder-no-fix" type="text"
														autocomplete="off" placeholder="" name="id"/></td>
											</tr>
											<tr>
												<td>Recipient_name</td>
												<td>
												<form:input path="r_name" id="r_name" class="form-control placeholder-no-fix" type="text"
												autocomplete="off"  name="r_name"/>
												</td>
											</tr>
											<tr>
												<td>Recipient_Designation</td>
												<td><form:input type="text" path="r_designation" id="r_designation" class="form-control placeholder-no-fix" 
													autocomplete="off"  name="r_designation"></form:input>
												</td>
											</tr>
											<tr>
												<td>Recipient_Email_Id</td>
												<td><form:input path="r_mail" id="r_mail" class="form-control placeholder-no-fix" type="text"
													autocomplete="off"  name="r_mail" onchange="validateUserName(this.value)"></form:input>
												</td>
											</tr>
											<tr>
												<td>Recipient_Mobile_No</td>
												<td><form:input path="r_mobile_num" id="r_mobile_num" class="form-control placeholder-no-fix" type="text"
													autocomplete="off" name="r_mobile_num" onchange="validateUserName(this.value)"></form:input>
												</td>
											</tr>
											<tr>
												<td>Circle</td>
												<td id="masterTd1">
														 <form:select id="circle" path="circle" name="circle" class="form-control" type="text" onchange="return getdivVal(this.value);">
															<form:option value="0">Select</form:option>
															<c:forEach items="${circle}" var="element">
																<form:option value="${element}">${element}</form:option>
															</c:forEach>
														</form:select>
													</td>
											</tr>

											<tr>
												<td>Division</td>
												<td id="masterTd2"> <form:select id="division" class="form-control" path="division" name="division" type="text" onchange="return getsubdivValue(this.value);">
															<form:option value="0">Select</form:option>
														</form:select>
													</td>
											</tr>
											<tr>
												<td>Subdivision</td>
												<td id="masterTd3"><form:select id="subdivision" path ="subdivision" class="form-control" type="text" name="subdivision" >
															<form:option value="0">Select</form:option>
														</form:select>
													</td>
											</tr>
											<tr>
											<td>Message Type</td>
											<td><div class="checkbox-list">
													<label > 
													<input type="checkbox" id="Tamper" path="message_type" name="message_type" value="Tamper data">
													Tamper Data
													<input type="checkbox" id="Outage" path="message_type" name="message_type" value="Outage data">
													Outage Data
													</label>
												</div></td>
											</tr>
											<tr>
											<td>Meter Type</td>
											<td><div class="checkbox-list">
													<label > 
													<input type="checkbox" id="Feeder" path="meter_type" name="meter_type" value="Feeder">
													Feeder 
													<input type="checkbox" id="DT" path="meter_type" name="meter_type" value="DT">
													 DT
													<input type="checkbox" id="HT" path="meter_type" name="meter_type" value="HT">
													 HT
													<input type="checkbox" id="BM" path="meter_type" name="meter_type" value="BM">
													 BM
													<input type="checkbox" id="LT" path="meter_type" name="meter_type" value="LT">
													 LT
													</label>
												</div></td>
											</tr>
										</table>
										<div class="modal-footer left">
											<button type="button" data-dismiss="modal" class="btn red"
												id="closeData">Close</button>
											<button type="button" class="btn green pull-left" style="display: block;"
												id="addOption" onclick="validation()" >Save</button>
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
	 document.getElementById("smstorecipients").reset();
		  $.ajax(
					{
							type : "GET",
							url : "./editRecipientsList/" + operation,
							dataType : "json",
							cache:false,
							async:false,
							success : function(response)
							
										{ 
								alert(response);
										    $('#id').val(response.id);
								            $('#r_name').val(response.r_name);
								            $('#r_designation').val(response.r_designation);
								            $('#r_mail').val(response.r_mail);
								            $('#r_mobile_num').val(response.r_mobile_num);
								            $('#circle').val(response.circle);
								            
								            $('#division')
								            .append($("<option></option>")
								                       .attr("value",response.division)
								                       .text(response.division)); 
								             $('#subdivision')
								            .append($("<option></option>")
								                       .attr("value",response.subdivision)
								                       .text(response.subdivision)); 
								            
								            $('#division').val(response.division); 
								            $('#subdivision').val(response.subdivision);
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
	
	 bootbox.confirm("Are you sure to delete selected records from Recipient details.", function(confirmed) 
	 {
        if(confirmed==true)
        {
        	 $.ajax(
			 {
				 	type : "GET",
					url : "./smsUserList/" + operation,
					dataType : "text",
					cache:false,
					async:false,
					success : function(response)
					{ 
						
						 if(response=='deleted')
				    	{
				    		window.location.href="./smsconfig?flag=0";
				    	}
				    	else if(response=='notDeleted')
				    	{
				    		window.location.href="./smsconfig?flag=1";
				    	}
				    	else
				    	{
				    		window.location.href="./smsconfig";
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
	var flagChecked = 0;
	 var flagChecked1 = 0;
	 var flagChecked2 = 0;
	 var levels = "";
if( $('#r_name').val().trim()=='')
{
		bootbox.alert("Please enter the Recipient_name.");
		return false;
}

if( $('#r_mail').val().trim()=='' && $('#r_mobile_num').val().trim()=='')
{
		bootbox.alert("Please enter Recipient_Email or Mobile Number");
		return false;
}
if( $('#circle').val().trim()=='' || $('#circle').val().trim()=='0')
{
		bootbox.alert("Please select Circle");
		return false;
}
checkboxes = document.getElementsByName('message_type');
	for(var i =0;i<checkboxes.length;i++)
		{
		
		if(checkboxes[i].checked)
		 {
			levels = levels + checkboxes[i].value + ",";
			flagChecked1 = 1;
		}
		}
	if(flagChecked1==0)
	{
		  bootbox.alert('PLease Select Atleast One message_type ');
		   return false;
	}
	
	checkboxes = document.getElementsByName('meter_type');
	for(var i =0;i<checkboxes.length;i++)
		{
		
		if(checkboxes[i].checked)
		 {
			levels = levels + checkboxes[i].value + ",";
			flagChecked2 = 1;
		}
		}
	if(flagChecked2==0)
	{
		  bootbox.alert('PLease Select Atleast One Meter Type ');
		   return false;
	}

$("#smstorecipients").submit();

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




