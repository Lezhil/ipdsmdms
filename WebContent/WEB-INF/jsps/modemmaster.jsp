<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.*,java.util.*" session="false"%>

<style>

label{
	margin-left:50px	
	
}
</style>
<script  type="text/javascript">

	$(".page-content").ready(function(){			
		//$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
		App.init();
		TableManaged.init();
		FormComponents.init();
		$('#conndate').val(moment(new Date()).format('MM/DD/YYYY'));
		$('#newConnectionId').addClass('start active ,selected');
		$("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');


		});
	
  
	
		function addConnection(form1)
	{	
	
		  var regex = /^[a-zA-Z ]*$/;
		 
		  var regexMobile=/^[0-9]{10}$/;
		  
		  var regexMtrno=/^[A-Za-z\d]{3,}$/;
		
		  if(form1.accno.value=='' || form1.accno.value.length < 12)
			{
			bootbox.alert('Please enter account number, atleast contain 12 characters');
			return false;
			}
		   if(form1.accno.value.match(regex))
			 {
			 bootbox.alert('Only alphabets are not allowed in account number ');
			 return false;
			 }
		  
		    if(/[^a-zA-Z0-9]/.test(form1.accno.value))
			 {
			 bootbox.alert('Special characters are not allowed in accno');
			 return false;
			 }
		  
		 
		 if(form1.meterno.value.match(regex))
		 {
		 bootbox.alert('Only alphabets are not allowed in meter number');
		 return false;
		 }
		 if(/[^a-zA-Z0-9]/.test(form1.meterno.value))
		 {
		 bootbox.alert('Special characters are not allowed in meterno');
		 return false;
		 }
		 if(!form1.meterno.value.match(regexMtrno))
			{
			bootbox.alert('Please enter meter number maximum lenght 3 ');
			return false;
			}
		 
		if(form1.name.value=='')
		{
			bootbox.alert('Please enter name');
			return false;
		}
		/* if(!form1.name.value.match(regex))
		{
		bootbox.alert('Please enter alphabets only in name ');
		return false;
		} */
		if(form1.address1.value=='')
		{
			bootbox.alert('Please enter address');
			return false;
		}
		//mobile
		 if(form1.mobile.value=='')
			{
				bootbox.alert('Please enter mobile no');
				return false;
			}
		  if(!form1.mobile.value.match(regexMobile))
			 {
			 bootbox.alert('Please enter 10 digit mobile numbers');
			 return false;
			 }
		  
		 if(form1.tariffcode.value=='')
		{
			bootbox.alert('Please enter tariffCode');
			return false;
		}
		 if(form1.tariffcode.value.match(regex))
		 {
		 bootbox.alert('only alphabets are not allowed in tarrifcode');
		 return false;
		 }
		 if(/[^a-zA-Z0-9]/.test(form1.tariffcode.value))
		 {
		 bootbox.alert('Special characters are not allowed in tarrifcode');
		 return false;
		 }
		
		if(form1.contractdemand.value=='' || isNaN(form1.contractdemand.value))
		{
			bootbox.alert('please enter cd, should contain only digits');
			return false;
		}
		if(form1.kworhp.value=='0')
		{
			bootbox.alert('Please select kw/hp');
			return false;
		}
		if(form1.sanload.value=='' || isNaN(form1.sanload.value))
		{
			bootbox.alert('please enter sanload, should contain only digits');
			return false;
		}
		if(form1.mf.value=='' || isNaN(form1.mf.value))
		{
			bootbox.alert('please enter MF, should contain only digits');
			return false;
		}
		
		if(form1.ctrn.value=='')//ctrn
			{
			bootbox.alert('please enter CTRN value');
			return false;
			}
		
		
		if(isNaN(form1.ctrn.value))
		{
			bootbox.alert('CTRN should contain only digits');
			return false;
		}
		
		
		if(form1.ctrd.value=='')//ctrd
		{
		bootbox.alert('please enter CTRD value');
		return false;
		}
		
		
		if(isNaN(form1.ctrd.value))
		{
			bootbox.alert('CTRD should contain only digits');
			return false;
		}
		if(form1.supplyvoltage.value=='0')
		{
			bootbox.alert('Please select supply voltage');
			return false;
		}
		if(form1.mtrmake.value=='0')
		{
			bootbox.alert('Please select meter make ');
			return false;
		}
		if(form1.mtrtype.value=='0')
		{
			bootbox.alert('Please select meter Type ');
			return false;
		}
		if(form1.mrname.value=='0' || form1.mrname.value=='')
		{
			bootbox.alert('Please select mrname ');
			return false;
		}
		if(form1.prkwh.value=='' || isNaN(form1.prkwh.value))
		{
			bootbox.alert('Please enter initial reading, should contain only digits ');
			return false;
		}
		if(form1.tadesc.value=='0')
		{
			bootbox.alert('Please select category ');
			return false;
		}
		if(form1.industrytype.value=='')
		{
			bootbox.alert('Please enter Industry Type');
			return false;
		}
		 if(!form1.industrytype.value.match(regex))
		 {
		 bootbox.alert('Only alphabets are  allowed in industrytype  ');
		 return false;
		 }
		 
		 if(form1.supplyType.value=='0')
			{
				bootbox.alert('Please select Supply Type');
				return false;
			}
		 
		
		if(!form1.mobile.value.match(regexMobile1))
			{
			bootbox.alert("Enter Only Numbers");
			return false;
			}
		
	}	
		 function myClearFunction1()
		    {
		     	$("#modem_serial_no").val("");
		    	$("#modem_code").val("");
		    	$("#modem_host_name").val("");
		    	$("#sim_serial_no").val("");
		    	$("#mobile_no").val("");
		    	$("#apn").val("");
		    	$("#sim_service_provider").val("");
		   
		    	
		    }
	 

	
	 var circle='';
	function checkAccnoExist(accno)
	{
		
		 $.ajax({
			type : 'GET',
			url : "./checkAccnoExist/"+accno,
			async : false,
			cache : false,
			success : function(response)
			{
				var accn=accno.substring(0,4);
				if(response != "" )
					{
					 
					$("#accno").val("");
					$("#accountNotExistMsg").html(response);
				
					}else{
						$.ajax({
							type : 'GET',
							url : "./getCirData",
							data:{accn:accn},
							async : false,
							cache : false,
							success : function(response)
							{
								//alert(response);
								if(response!=null && response!="")
									{
								    circle=response.circle;
									$("#circle").val(circle);
									$("#division").val(response.division);
									$("#sdocode").val(response.sdoCode);
									$("#sdoname").val(response.sdoName);
									//$("#mnp").val(response.sdoName);
									}
								
								else{
									bootbox.alert("No circle found for given Accno");
									return false;
									}
							}
						});
						
						$("#accountNotExistMsg").html(response);
						 getmrname(circle);
						  circle='';		  
					}
			}
		}); 
		return true;
	}
	 
	 function checkMeterExist(form)
		{
			var accno = form.accno.value;
			var metrno=form.metrno.value;
			var rdngmonth=form.rdngmonth.value;
			if(accno == "" || accno == null)
				{
				bootbox.alert("please Enter Accno");
				return false;
				}
			else{
			 $.ajax({
				type : 'GET',
				url : "./checkMeterExist/"+metrno+"/"+rdngmonth,
				async : false,
				cache : false,
				success : function(response)
				{
					if(response != "" )
						{
						$("#meterno").val("");
					    
						$("#accountNotExistMsg").html(response);
						
						}else{
							$("#accountNotExistMsg").html(response);
						}
				}
			}); 
			}
			
		
			return true;
		}
	 
	 
	
			
	//mrname
	 function getmrname(param)
	 {
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

	 		 		var newOption = $('<option>');
	 		         newOption.attr('value',0).text(" "); 
	 		         $('#mrname').empty(newOption);
	 		         var defaultOption = $('<option>');
	 		         defaultOption.attr('value',"").text("Select Mrname");
	 		         $('#mrname').append(defaultOption);
	 		         
	 		 			/*  var html='<select class="form-control select2me input-medium" name="mrnamediv" id="mrnamediv" >Select MRname</option>'; */
	 		 			 for( var i=0;i<response.length;i++)
	 		 			 {
	 		 				 $("#mrname").append('<option value="'+response[i]+'">'+response[i]+'</option>');
	 		 					
	 		 				/* html+="<option value='"+response[i]+"'>"+response[i]+"</option>";  */
	 		 			 }
	 		 	

	 		 	}
	 		 	});
		 }

	
	 }	
	
</script>
<div class="page-content" >
<!-- Display Error Message -->
		<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result}</span>
			</div>
	    </c:if>	
	    
	    
					<span style="color:red" id="accountNotExistMsg"></span>
			
		<!-- End Error Message -->
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>MODEM MASTER</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
									
									<form:form action="./addNewModem"  class="form-inline" role="form" modelAttribute="addmodemmaster" commandName="addmodemmaster" method="post" id="addmodemmaster">		  
													<table>
													<tr>
													<td><label>Modem Serial No.<form:input path="modem_serial_no"  id="modem_serial_no" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter modem no" maxlength="100" style="margin-left: 9px;" aria-describedby="emailHelp"></form:input></label></td> 
										   			
										   			<td>
													<label>Modem Code<form:input path="modem_code"  id="modem_code" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Modem Code" maxlength="100"></form:input></label>
													</td>
													
										   			</tr>
													
													<tr>
													<td>
													<label>Modem Host Name<form:input path="modem_host_name"  id="modem_host_name" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Modem Host Name"  maxlength="900"></form:input></label>
													</td><td>
													<label>Sim Serial No<form:input path="sim_serial_no"  id="sim_serial_no" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Sim Serial No" maxlength="10"></form:input></label>
													</td></tr>
													<tr>
													<td>
													<label>Mobile  No<form:input path="mobile_no"  id="mobile_no" class="form-control input-medium" type="text" placeholder="Enter Mobile No" style="margin-left: 61px;"></form:input></label>
													</td><td>
												<label>APN<form:input path="apn"  id="apn" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter APN" maxlength="100" style="margin-left: 59px;"></form:input></label>
													</td>
													</tr>
													<tr>
									        <td>
									        <label>Sim Service Provider<form:input path="sim_service_provider"  id="sim_service_provider" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Sim Service Provider" maxlength="100"></form:input></label>
										   </td>
										    </tr>
											</table>
													
													   <div class="modal-footer">															    
													 &nbsp;&nbsp;<button class="btn blue pull-right" id="updateOption" onclick="return addConnection(this.form);" >Add Modem</button>		
													 <button type="button" data-dismiss="modal" onclick="return myClearFunction1()" class="btn white">Clear</button>
													 
													 </div>	
														
										 </form:form>
													
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>
