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
		$('#MDMSideBarContents,#metermang,#addmeter').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');


		TableManaged.init();
		FormComponents.init();
		$('#conndate').val(moment(new Date()).format('MM/DD/YYYY'));
		
		});
	
  
	
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
	 
	 function checkMeterExist()
		{
			var metrno=$("#meter_code").val();
			 $.ajax({
				type : 'GET',
				url : "./checkMeterExists/"+metrno,
				async : false,
				cache : false,
				success : function(response)
				{
					if(response != "" )
						{
						$("#meter_code").val("");
					    
						$("#accountNotExistMsg").html(response);
						
						}else{
							$("#accountNotExistMsg").html(response);
						}
				}
			}); 
			
			
		
			return true;
		}
	 
	 
	 function myClearFunction1()
	    {
	     	$("#meter_ownership").val("");
	    	$("#meter_code").val("");
	    	$("#meter_ct_ratio").val("");
	    	$("#meter_pt_ratio").val("");
	    	$("#meter_serial_no").val("");
	    	$("#meter_make").val("");
	    	$("#meter_type").val("");
	    	$("#meter_status").val("");
	    	$("#no_of_digit_type").val("");
	   
	    	
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
							<div class="caption"><i class="fa fa-globe"></i>ADD NEW METER </div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
									
									<form:form action="./addNewMeter"  class="form-inline" role="form" modelAttribute="addmetermaster" commandName="addmetermaster" method="post" id="addmetermaster">		  
													<table>
													<tr>
													<%-- <td><label>Meter Ownership
													<form:select path="meter_ownership" class="form-control input-medium" id="meter_ownership">
													 <form:option value="0">select</form:option>
                                             		<form:option value="CUSTOMER">CUSTOMER</form:option>
                                             		<form:option value="consumer">Consumer from combo box</form:option>
                                             		</form:select>
													</label></td>  --%>
										   			
										   			<td>
													<label>Meter Code<form:input path="meter_serial_no"  id="meter_serial_no" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Meter Code" maxlength="100" style="margin-left: 18px;" onchange="return checkMeterExist()"></form:input></label>
													</td>
													
										   			<!-- </tr>
													
													<tr> -->
													<td>
													<label>Meter CT Ratio<form:input path="meter_ct_ratio"  id="meter_ct_ratio" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Meter CT Ratio"  maxlength="900" style="margin-left: 21px;"></form:input></label>
													</td><td>
													<label>Meter PT Ratio<form:input path="meter_pt_ratio"  id="meter_pt_ratio" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter Meter PT Ratio"  maxlength="10" ></form:input></label>
													</td></tr>
													
													<tr>
									        <%-- <td>
									        <label>Meter Type<form:select  path="meter_type" name="meter_type" class="form-control input-medium" id="meter_type" style="margin-left: 43px;" >
									        
										     <form:option value="0">select</form:option>
                                             <form:option value="electromagnetic">Electro Magnetic</form:option>
                                             <form:option value="electronic">Electronic from combo box</form:option>
										   </form:select></label>
										   </td> --%>
										   <td>
									        <label>Meter Status<form:select  path="meter_status" name="meter_status" class="form-control input-medium" id="meter_status" style="margin-left: 15px;" >
									        
										     <form:option value="0">select</form:option>
                                             <form:option value="HT">HT</form:option>
                                             <form:option value="LT">LT</form:option>
										   </form:select></label>
										   </td>
										    <%-- </tr>
													
											<tr>
											<td>
											<label>No of Digit<form:input path="no_of_digit_type"  id="no_of_digit_type" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter No of Digit" style="margin-left: 47px;"></form:input></label>
											</td> --%>
											
													<td>
													<label>Meter Make
													<form:select path="meter_make"  id="meter_make" class="form-control input-medium" type="text" autocomplete="off" placeholder="Enter TarrifCode" maxlength="10" style="margin-left: 35px;"></label>
													<form:option value="0">select</form:option>
                                             		<%-- <form:option value="lnt">LNT</form:option> --%>
                                             		<form:option value="GENUS">GENUS</form:option>
                                             		<%-- <form:option value="DUKE">DUKE</form:option>
                                             		<form:option value="HSPL">HSPL</form:option>
                                             		<form:option value="SECURE">SECURE</form:option>
                                             		<form:option value="LNG">LNG</form:option>
                                             		<form:option value="HPL">HPL</form:option>
                                             		<form:option value="HPLD">HPLD</form:option> --%>
													</form:select>
													</td>
													</tr>
											
											</table>
													
													   <div class="modal-footer">															    
													 &nbsp;&nbsp;<button class="btn blue pull-right" id="updateOption" onclick="return addConnection(this.form);" >Add Meter</button>		
													 <button type="button" data-dismiss="modal" onclick="return myClearFunction1()" class="btn white">Clear</button>
													 
													 </div>	
														
										 </form:form>
													
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>
