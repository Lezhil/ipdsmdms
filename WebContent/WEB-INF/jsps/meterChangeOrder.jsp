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
		$('#MDMSideBarContents,#newConnectionId,#meterChangeOrder1').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');



		});
	
	function getMeterDetails(){
		
	 var meterNo=document.getElementById('meter_serial_no').value;
		
		
		
		 $.ajax({
				type : 'GET',
				url : "./getMeterDetails/"+meterNo,
				
				success : function(response)
				{
					
						
					for(var j=0;j<response.length;j++){
						
						var expRptData=response[j];
						 $('#id').val(expRptData[0]);				
						 $('#meter_code').val(expRptData[1]);
						 $('#meter_ownership').val(expRptData[5]);
						 $('#meter_ct_ratio').val(expRptData[7]);
						 $('#meter_pt_ratio').val(expRptData[8]);
						 $('#meter_serial_no').val(expRptData[1]);
					 	 $('#meter_make').val(expRptData[4]);
					 	 $('#meter_type').val(expRptData[3]);
						 $('#meter_status').val(expRptData[9]);
					 	$('#no_of_digit_type').val(expRptData[6]);
					}
					
					
				}
			}); 
			
		
		
		
	}

		
	
	/* 	function addConnection(form1)
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
		} */
		/* if(!form1.name.value.match(regex))
		{
		bootbox.alert('Please enter alphabets only in name ');
		return false;
		} */
	/* 	if(form1.address1.value=='')
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
		
	}	 */
	
	/* function checkAccMeterExist(form1)
	{
		var regex = /^[a-zA-Z ]*$/;
		if(form1.accno.value=='' || form1.accno.value.length < 12)
		{
		bootbox.alert('Please enter account number, should contain atleast 12 characters');
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
		
	    if(form1.meterno.value=='')
		{
		bootbox.alert('Please enter meter number');
		return false;
		}
	    if(form1.meterno.value.match(regex))
		 {
		 bootbox.alert('Only alphabets are not allowed in meter number ');
		 return false;
		 }
	    if(/[^a-zA-Z0-9]/.test(form1.meterno.value))
		 {
		 bootbox.alert('Special characters are not allowed');
		 return false;
		 }
	    
	    
	     var str=$('#'+form1.id).serialize();
	  $.ajax({
	   url:'./checkMeterAccNoExist',
	   dataType:'text',
	   type:'GET',
	   data:str,
	   async:false,
	   success:function(response)
	   {
		   if(response=="1")
			   {			   
			    bootbox.alert('AccNo already exist');
			    form1.accno.value='';
			    return false;
			   }
		   if(response=="2")
		   {			   
		    bootbox.alert('meterNo already exist');
		    form1.meterno.value='';
		    return false;
		   }
		   if(response=="3")
		   {			   
		    bootbox.alert('Both accNo and meterNo already exist');
		    $('#accno,#meterno').val('');
		    return false;
		   }
	   }
	 })
	 
	}
	 */
	 
		
	/*  var circle='';
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
	} */
	 
	/*  function checkMeterExist(form)
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
		} */
	 
	 
	
			
	//mrname
	 /* function getmrname(param)
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
	 		 			/*  for( var i=0;i<response.length;i++)
	 		 			 {
	 		 				 $("#mrname").append('<option value="'+response[i]+'">'+response[i]+'</option>'); */
	 		 					
	 		 				/* html+="<option value='"+response[i]+"'>"+response[i]+"</option>";  */
	 		 			/*  }
	 		 	

	 		 	}
	 		 	});
		 }

	
	 }	 */
	 
	 
	 function addConnection(form)
		{
			if(form.meter_ownership.value==0)
			{
				bootbox.alert("Please select Meter Ownership");
				return false;
			}
			if(form.meter_serial_no.value=="")
			{
				bootbox.alert("Please Enter Meter Code");
				return false;
			}
			if(form.meter_ct_ratio.value=="")
			{
				bootbox.alert("Please Enter Meter CT Ratio");
				return false;
			}
			if(form.meter_pt_ratio.value=="")
			{
				bootbox.alert("Please Enter Meter PT Ratio");
				return false;
			}
			if(form.meter_type.value==0)
			{
				bootbox.alert("Please select Meter Type");
				return false;
			}
			if(form.meter_status.value==0)
			{
				bootbox.alert("Please select Meter Status");
				return false;
			}
			if(form.no_of_digit_type.value=="")
			{
				bootbox.alert("Please Enter No of Digit");
				return false;
			}
			if(form.meter_make.value==0)
			{
				bootbox.alert("Please select Meter Make");
				return false;
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
							<div class="caption"><i class="fa fa-globe"></i>EDIT METER MASTER</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
									
									<form:form action="./updateMeterDetails"  class="form-inline" role="form" modelAttribute="meterdata" commandName="meterdata" method="post" id="meterdata">		  
													<table>
													<tr>
													
													<td>
													<label>Meter Code<form:input path="meter_serial_no"  id="meter_serial_no" class="form-control input-medium" type="text"  onchange="return getMeterDetails()"  autocomplete="off" placeholder="Enter Meter Code" maxlength="160" style="margin-left: 40px;"></form:input></label>
													</td>
													
													
													<td><label>Meter Ownership
													<form:select path="meter_ownership" class="form-control input-medium" id="meter_ownership" style="margin-left: 35px;">
													 <form:option value="0">select</form:option>
                                             		<form:option value="uhbvn">UHBVN</form:option>
                                             		<form:option value="consumer">Consumer from combo box</form:option>
                                             		</form:select>
													</label></td> 
										   			
										   			<input type="text" hidden="true" id="id" name="id" />
													
										   			</tr>
													
													<tr>
													<td>
													<label>Meter CT Ratio<form:input path="meter_ct_ratio"  id="meter_ct_ratio" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Meter CT Ratio"  maxlength="900" style="margin-left: 21px;"></form:input></label>
													</td><td>
													<label>Meter PT Ratio<form:input path="meter_pt_ratio"  id="meter_pt_ratio" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Meter PT Ratio"  maxlength="10" style="margin-left: 57px;"></form:input></label>
													</td></tr>
													
													<tr>
									        <td>
									        <label>Meter Type<form:select  path="meter_type" name="meter_type" class="form-control input-medium" id="meter_type" style="margin-left: 43px;" >
									        
										     <form:option value="0">select</form:option>
                                             <form:option value="electromagnetic">Electro Magnetic</form:option>
                                             <form:option value="electronic">Electronic from combo box</form:option>
										   </form:select></label>
										   </td>
										   <td>
									        <label>Meter Status<form:select  path="meter_status" name="meter_status" class="form-control input-medium" id="meter_status" style="margin-left: 70px;" >
									        
										     <form:option value="0">select</form:option>
                                             <form:option value="HT">HT</form:option>
                                             <form:option value="LT">LT</form:option>
										   </form:select></label>
										   </td>
										    </tr>
													
											<tr>
											<td>
											<label>No of Digit<form:input path="no_of_digit_type"  id="no_of_digit_type" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter No of Digit" style="margin-left: 47px;"></form:input></label>
											</td>
											
											 <td>
									        <label>Connection Status<form:select  path="connection_status" name="connection_status" class="form-control input-medium" id="connection_status" style="margin-left: 35px;" >
									        
										     <form:option value="0">select</form:option>
                                             <form:option value="Activate">Activate</form:option>
                                             <form:option value="Deactivate">Deactivate</form:option>
										   </form:select></label>
										   </td>
											
											
											</tr>
											<tr>
													<td>
													<label>Meter Make
													<form:select path="meter_make"  id="meter_make" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter TarrifCode" maxlength="10" style="margin-left: 35px;"></label>
													<form:option value="0">select</form:option>
                                             		<form:option value="lnt">LNT</form:option>
                                             		<form:option value="GENUS">GENUS</form:option>
                                             		<form:option value="DUKE">DUKE</form:option>
                                             		<form:option value="HSPL">HSPL</form:option>
                                             		<form:option value="SECURE">SECURE</form:option>
                                             		<form:option value="LNG">LNG</form:option>
                                             		<form:option value="HPL">HPL</form:option>
                                             		<form:option value="HPLD">HPLD</form:option>
													</form:select>
													</td>
													</tr>
											</table>
													
													   <div class="modal-footer">															    
													 &nbsp;&nbsp;<button class="btn blue pull-right" id="updateOption" onclick="return addConnection(this.form);" >Update Connection</button>		
													 <button type="button" data-dismiss="modal" class="btn white">Clear</button>
													 
													 </div>	
														
										 </form:form>
												 <c:if test = "${not empty success}"> 			        
					        <script>		        
					            var msg = "${success}";
					           alert(msg);
					        </script>	
							</c:if>
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			
</div>
