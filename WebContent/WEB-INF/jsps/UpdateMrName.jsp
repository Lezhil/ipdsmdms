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
			  $("#mrname").val('0');
			  $("#newmrname").val('');
   	    	     App.init();
   	    	  TableEditable.init();
   	    	 FormComponents.init();
   	    	$('#newConnectionId').addClass('start active ,selected');
   	    	$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	 	
   	    	$('#fromDate').val('${currentDate}');
   	   });
  
 
  
</script>
  
<style>
.input-medium {
    width: 160px !important;
}
</style>

<div class="page-content">

	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color: red">${results}</span>
				</div>
				<c:remove var="results" scope="session" />
			</c:if>

			 <div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>MR Name Update
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
				<form  id="updateMrName5" action="./UpdateNewMrName" method="POST">
					
						<table style="width: 53%">
						<tbody>
						<tr>
							<td>
							
							<select class="form-control select2me input-medium" name="circleId" id="circleId" style="margin-top: 10px;"  onchange="return changeFirstmrName();"> 
					   		<option value="">Select Circle</option>
					   		<c:forEach items="${circle}" var="element">
							<option value="${element}">${element}</option>
							</c:forEach></select>
					
							</td>
							
							<td id="firstMrName" ></td>
							
							<td id="sdocodeId">
					   		</td>
					   		
					   		<td id="mrNameId" >
					   		      
						 	  
					   		</td>
					   		
							<td>
							<input type="submit" id="dataview" class="btn green" style="margin-left: 105px;margin-top: 11px;border-right-width: -17px;" onclick="return validation(this.form);" value="Update" >
							</td>
					
					 </tr>
					</tbody></table>
					
						</form>
					</div>
				</div>
				
				
			</div>
			
			
			<!-- new mr name update -->
			
			<div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>MR Name Update For Respective SDO_Code AND TADESC
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
			
				<form method="POST" action="./UpdateNewMrNameSDO">
					<table style="width: 53%">
						<tbody>
						<tr>
						<td>
							
							<select class="form-control select2me input-medium" name="SecondCircleId" id="SecondCircleId" style="margin-top: 10px;"  onchange="return SdoByCircle();"> 
					   		<option value="">Select Circle</option>
					   		<c:forEach items="${circle}" var="element">
							<option value="${element}">${element}</option>
							</c:forEach></select>
					
							</td>
					   		<td id="SecondSdocodeId">
					   		</td>
					   		<td id="secondMrname">
					   		</td>
					   		<td id="tadesc1">
					   		</td>
					   		<td id="MrNameOnTadesc" >
					   		</td>
					   		
						 <td><button type="submit" id="dataview1" class="btn green"   style="margin-left: 105px; margin-top: 12px; display: none" onclick="return validatesecondUpdate(this.form);" ><b>Update</b></button></td>
						
						 </tr>
					</tbody></table>
				</form>
					</div>
				</div>
				</div>
				
				
				<!--Account No wise MRNAME Update  -->
				
				<%-- <div class="portlet box blue" >
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>UPDATE MRNAME BASED ON ACCOUNT NUMBER
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<div class="row" style="margin-left:-1px;">
			
				<form method="POST" action="">
					<table style="width: 53%">
						<tbody>
						<tr>
						<td>
							
							<select class="form-control select2me input-medium" name="AccCircleId" id="AccCircleId" style="margin-top: 10px;"  onchange="return mrnameByCircle(this.value);"> 
					   		<option value="">Select Circle</option>
					   		<c:forEach items="${circle}" var="element">
							<option value="${element}">${element}</option>
							</c:forEach></select>
					
							</td>
					   		<td id="SecondSdocodeId">
					   		</td>
					   		<td id="secondMrname">
					   		</td>
					   		<td id="tadesc1">
					   		</td>
					   		<td id="MrNameOnTadesc" >
					   		</td>
					   		
						 <td><button type="submit" id="dataview1" class="btn green"   style="margin-left: 105px; margin-top: 12px; display: none" onclick="return validatesecondUpdate(this.form);" ><b>Update</b></button></td>
						
						 </tr>
					</tbody></table>
				</form>
					</div>
				</div>
				</div> --%>
				
				


</div>
</div>
</div>

<script>


function getTadescCodeByMrname()
{
	 var circle=$("#SecondCircleId").val();
	 var sdocode=$("#SecondSdocode").val();
	 var mrname=$("#secondmrId").val();

	 if(mrname=="")
		{
		bootbox.alert("Please select MR Name");
		}
	
	$.ajax({
    	url:'./getTadescCodeByMrname',
    	type:'GET',
    	dataType:'json',
    	data:{circle:circle,sdocode:sdocode,mrname:mrname},
    	asynch:false,
    	cache:false,
    	success:function(response)
    	{
    		
    		if(response != null)
    		{
    			
    			var html='<select class="form-control select2me input-medium" name="tadesc" id="tadesc2"  onchange="getMrnameByTadesc();" style="margin-top: 10px;">'
    			+'<option value=0>Select Tadesc</option>'
    			
    			 html+=	'<option value="ALL">All</option>';
    			 for( var i=0;i<response.length;i++)
    			 {
    				
    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
    			 }
    			 html+='</select>';
    			
    			 $("#tadesc1").empty();
    			 $("#tadesc1").append(html);
    			// $("#name1").select2();
    			 
    		}
    	}
    	
    });
}


//validation

function validation()
{
		  
	  if($("#circleId").val()=="" || $("#circleId").val()==null)
	  {
	    bootbox.alert('Please Select Circle.');
	    return false;
	  }
	 
} 
//to upper new mrname
function makeUppercase1(){
	  
$('#newmrname').keyup(function(){
	    $(this).val($(this).val().toUpperCase());
	});
}



 function validationSDO()
{
	 
	 var name1=$("#name1").val();
	 var mm= $('#newmrname1').val();
	 
		
	  if($("#sdocode1").val()==0)
	  {
	    bootbox.alert('Please Select SDOCODE');
	    return false;
	  }
	  
	  if($("#tadesc2").val()==0)
		  {
		    bootbox.alert('Please Select TADESC');
		    return false;
		  }
	
	  if($("#name1").val()==0 && $("#newmrname1").val()=="" || $("#newmrname1").val()==null)
	  {
	    bootbox.alert('Please Select MRNAME');
	    return false;
	  }
	  
	  if($("#newmrname1").val()=="" || $("#newmrname1").val()==null)
	  {
	    bootbox.alert('Please Enter MR Name text box.');
	    return false;
	  }
}
//to upper new mrname
function makeUppercase(){
	  
$('#newmrname1').keyup(function(){
	    $(this).val($(this).val().toUpperCase());
	});
}

function makeUppercase3(){
	  
	$('#newmrname3').keyup(function(){
		    $(this).val($(this).val().toUpperCase());
		});
	}


//validation for mrtable

function validationMrTable()
{
	 
	  if($("#mrname2").val()==0)
		  {
		    bootbox.alert('Please Select MR. Name');
		    return false;
		  }
	
	  if($("#newmrname2").val()=="" || $("#newmrname2").val()==null)
	  {
	    bootbox.alert('Please Enter NEW MR Name.');
	    return false;
	  }
} 
//to upper new mrname
 function makeUppercase2(){
	
	  
$('#newmrname5').keyup(function(){
	    $(this).val($(this).val().toUpperCase());
	});
} 

 
 
 
 



function myMrnameValidate(form)
{
	var regex = /^[a-zA-Z ]*$/;
	var mrnam=form.newmrname5.value;
	if(mrnam.trim()=='')
		{
		 //$("#error").hide();
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
 
 

function editMrName(param,opera)
{
	  var operation=parseInt(opera);
	  $.ajax(
	  			{
	  					type : "GET",
	  					url : "./editMrname/" + operation,
	  					dataType : "json",
	  					success : function(response)
 {
	  						}
 });
}


function deleteUser(param,opera)
{
		
	var operation=parseInt(opera);
		
	  bootbox.confirm("Are you sure want to delete this record ?", function(result) {
		  if(result == true)
             {
	          $("#delid").attr("value",operation);
	          $('form#delUser').attr('action','./detleMrName').submit();
             }

	  });
}
 
 /* ---------------------------------------MR Wise Update------------------------------------ */
 
 
 function changeFirstmrName()
 {
	 var circle=$("#circleId").val();
	 $.ajax({
		 type : "POST",
		 url:"./getFirstMrNameCirclewise",
		 data:{circle:circle},
		 success:function(response)
		 {
			 
			 
			 if(response != null)
	    		{
	    			
	    			var html='<select class="form-control input-medium" name="firstmrId" id="firstmrId"   style="margin-top: 10px;" style="margin-top: 10px;" onchange="return changeSdoCode();">'
	    			+'<option value=0>Select MR Name</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			
	    			 $("#firstMrName").empty();
	    			 $("#firstMrName").append(html);
	    			 
	    		}
		 }
		 
	 });
	 
 }
 function changeSdoCode()
 {
	 var circle=$("#circleId").val();
	 var mrName=$("#firstmrId").val();
	 $.ajax({
		 type : "POST",
		 url:"./getSdoCodesCirclewise",
		 data:{circle:circle,mrName:mrName},
		 success:function(response)
		 {
			 
			 
			 if(response != null)
	    		{
	    			
	    			var html='<select class="form-control input-medium" name="sdocodename" id="sdocode"   style="margin-top: 10px;" style="margin-top: 10px;" onchange="getmrNameBasedOnCircle();">'
	    			+'<option value=0>Select SDO Code</option>'
	    			+'<option value="All">All</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			
	    			 $("#sdocodeId").empty();
	    			 $("#sdocodeId").append(html);
	    			 
	    		}
		 }
		 
	 });
	 
 }
 
 function getmrNameBasedOnCircle()
 {
	 var sdocode=$("#sdocode").val();
	 var circle=$("#circleId").val();
	 
	 $.ajax({
		 type : "POST",
		 url:"./getMrNameSdoCodesCirclewise",
		 data:{sdocode:sdocode,circle:circle},
		 success:function(response)
		 {
			 
			 if(response != null)
	    		{
					var html='<div class="select-editable" ><select class="form-control select4me input-medium" name="oldmrname2" id="name2" path="mrname" onchange="this.nextElementSibling.value=this.value"> <option value=0>Select MrName</option>';
					 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
			   		html+='</select>';
					 html+='<input type="text" id="newmrname2"  name="newmrname2" placeholder="Select MR Name" onkeyup="makeUppercase();" style="padding-bottom: 3px;padding-left: 7px;padding-top: 7px;">';
			   		 html+='</div>';
	    			 $("#mrNameId").empty();
	    			 $("#mrNameId").append(html);
	    			 $("#name2").select2();
	    			 $("#newmrname2").show();
	    		}
		 }
		 
	 });
 }

 function SdoByCircle()
 {
	 var circle=$("#SecondCircleId").val();
	 $.ajax({
		 type : "POST",
		 url:"./getSecondSdoCodesByCircle",
		 data:{circle:circle},
		 success:function(response)
		 {
			 if(response != null)
	    		{
	    			var html='<select class="form-control input-medium" name="SecondSdocode" id="SecondSdocode"   style="margin-top: 10px;" style="margin-top: 10px;" onchange="changeEditSecondmrName();">'
	    			+'<option value=0>Select SDO Code</option>';
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 $("#SecondSdocodeId").empty();
	    			 $("#SecondSdocodeId").append(html);
	    		}
		 }
		 
	 });
	 
 }

 function changeEditSecondmrName()
 {
	 var circle=$("#SecondCircleId").val();
	 var sdocode=$("#SecondSdocode").val();
	 if(sdocode=="")
		{
		bootbox.alert("Please select sdocode");
		}
	 $.ajax({
		 type : "POST",
		 url:"./getSecondMrNameCirclewise",
		 data:{circle:circle,sdocode:sdocode},
		 success:function(response)
		 {
			 if(response != null)
	    		{
	    			var html='<select class="form-control input-medium" name="secondmrId" id="secondmrId"   style="margin-top: 10px;" style="margin-top: 10px;"  onchange="return getTadescCodeByMrname();">'
	    			+'<option value=0>Select MR Name</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 $("#secondMrname").empty();
	    			 $("#secondMrname").append(html);
	    		}
		 }
		 
	 });
	 
 }

 function getMrnameByTadesc()
 {
	 var circle=$("#SecondCircleId").val();
	 $.ajax({
		 type : "POST",
		 url:"./getFirstMrNameCirclewise",
		 data:{circle:circle},
		 success:function(response)
		 {
			 
			 if(response != null)
	    		{
	    			var html='<div class="select-editable" ><select class="form-control input-medium" name="editSecondmrId" id="editSecondmrId"  onchange="this.nextElementSibling.value=this.value">'
	    			+'<option value=0>Select MR Name</option>';
	    			
	    			 for( var i=0;i<response.length;i++)
	    			 {
	    				
	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
	    			 }
	    			 html+='</select>';
	    			 html+='<input type="text" id="newmrname3"  name="newmrname3" placeholder="Select MR Name" onkeyup="makeUppercase3();" style="padding-bottom: 3px;padding-left: 7px;padding-top: 7px;">';
 			   		 html+='</div>';
 			   		 
	    			 $("#MrNameOnTadesc").empty();
	    			 $("#MrNameOnTadesc").append(html);
	    			 $("#editSecondmrId").select2();
 	    			 $("#newmrname2").show();
 	    			 $("#dataview1").show();
	    		}
		 }
		 
	 });
	 
 }


 function validatesecondUpdate()
 {
	    var circle=$("#SecondCircleId").val();
		
		if(circle=="")
			{
			bootbox.alert("Please select circle");
			return false;
			}
		  if($("#newmrname3").val()=="" || $("#newmrname3").val()==null)
		  {
		    bootbox.alert('Please Enter NEW MR Name.');
		    return false;
		  }
 }


 function mrnameByCircle(param)
 {
	 alert("param->"+param);
 	if(param !='')
	 {
 		  $.ajax({
 		 	type : "GET",
 		 	url : "./getMrNameBasedOncir",
 		 	data:{param:param},
 		 	cache : false,
 		 	async : false,
 		 	dataType:'json',
 		 	success : function(response){

 		 		 if(response != null)
 	    		{
 	    			var html='<div class="select-editable" ><select class="form-control input-medium" name="editACCmrId" id="editAccmrId"  onchange="this.nextElementSibling.value=this.value">'
 	    			+'<option value=0>Select MR Name</option>';
 	    			
 	    			 for( var i=0;i<response.length;i++)
 	    			 {
 	    				
 	    				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
 	    			 }
 	    			 html+='</select>';
 	    			 html+='<input type="text" id="ACCmrname4"  name="newmrname3" placeholder="Select MR Name" onkeyup="makeUppercase3();" style="padding-bottom: 3px;padding-left: 7px;padding-top: 7px;">';
  			   		 html+='</div>';
  			   		 
 	    			 $("#MrNameOnTadesc").empty();
 	    			 $("#MrNameOnTadesc").append(html);
 	    			 $("#editSecondmrId").select2();
  	    			 $("#newmrname2").show();
  	    			 $("#dataview1").show();
 	    		}
 	 		 	
 		 	}
 		 	}); 
	 }


 }	

 
</script>
<style>



.select-editable {
     position: relative;
     background-color:white;
     border:solid grey 1px;
     width:160px;
     height:24px;
 }
 .select-editable select {
     position: relative;
     top:0px;
     left:0px;
     font-size:14px;
     border:none;
     width:160px;
     margin:0;
 }
 .select-editable input {
     position:absolute;
     top:0px;
     left:0px;
     width:140px;
     padding:1px;
     font-size:12px;
     border:none;
 }
 .select-editable select:focus, .select-editable input:focus {
     outline:none;
 }



</style>
