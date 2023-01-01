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
		$('#MDMSideBarContents,#metermang,#newconnection').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');

		});
	
  
	
	
</script>
<div class="page-content" >
<!-- Display Error Message -->
		<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:green" >${result}</span>
			</div>
	    </c:if>	
					<span style="color:red" id="accountNotExistMsg"></span>
			
		<!-- End Error Message -->
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>New Connection</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
									
									<form:form action="./addNewConnectionAMR"  class="form-inline" role="form" modelAttribute="
" commandName="newConnectionMeterMaster" method="post" id="newConnectionMeterMasterId" >		  
													<table>
													
													<tr><td>
													<label>RdngMonth<form:input path="rdngmonth"  id="rdngmonth" class="form-control placeholder-no-fix" type="text" autocomplete="off" value="201806" ></form:input></label>
													</td><td>
													<label>AccNo<form:input path="accno"  id="accno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter ACCNO" maxlength="13" name="accno" onchange="checkAccnoExist(this.value)"></form:input></label>
														</td>
													<td>
													<label>MeterNo<form:input path="metrno"  id="meterno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="XXXXXXXXXX"  maxlength="10"></form:input></label>
													</td>
													<%-- <td>
													<label>ModemNo<form:input path=""  id="modemno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="XXXXXXXXXX"  maxlength="10"></form:input></label>
													</td> --%>
													
											</tr>	
											
											<tr>
													<td><label>Zone 123
													<form:select path="master.zone"  id="zoneId" class="form-control placeholder-no-fix"  onchange="getCircleByZone(this.value);">
													<option value="0">Select Zone</option>
													<c:forEach items="${zones}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach>
													</form:select>
													</label>
													</td>
												
													<td><label>Circle<form:select path="master.circle"  id="circleId" class="form-control placeholder-no-fix"  onchange="getDivByCircle(this.value);">
													<option value="0">Select Circle</option>
													<%-- <c:forEach items="${circle}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach> --%>
													</form:select>
													</label>
													</td>
													<td><label>Division<form:select path="master.division"  id="divisionId" class="form-control placeholder-no-fix" onchange="getSubDivByDivision(this.value)">
													<option value="0">Select Division</option>
													
													<%-- <c:forEach items="${circle}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach> --%>
													</form:select>
													</label>
													</td>
													
													<td><label>Subdivision<form:select path="master.sdoname"  id="sdonameId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" >
													<option value="0">Select Sub-Division</option>
													
													<%-- <c:forEach items="${circle}" var="element">
														<option value="${element}">${element}</option>
													</c:forEach> --%>
													</form:select>
													</label>
												</tr>	
													<tr>
													<td>
													<label>Name<form:textarea path="master.name"  id="name" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" maxlength="100"></form:textarea></label>
													</td><td>
													<label>Address<form:textarea path="master.address1"  id="address1" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""  maxlength="900"></form:textarea></label>
													</td> <td>
													<label>Mobile No.<form:input path="master.phoneno2"  id="mobile" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Mobile No" maxlength="10"></form:input></label>
													</td> 
													 <td><label>Status<form:select  path="master.status" name="status" class="form-control" id="status" >
										     <form:option value="0">select</form:option>
										     <form:option value="INACTIVE">INACTIVE</form:option>
                                             <form:option value="ACTIVE">ACTIVE</form:option>
                                             <form:option value="TDC">TDC</form:option>
                                              <form:option value="PDC">PDC</form:option>
										   </form:select>
										   </label>
										   </td>
													</tr>
													<tr><td>
													
													<label>CD<form:input path="master.contractdemand"  id="contractdemand" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""  maxlength="10"></form:input></label>
														</td><td>												
													<label>KW/HP<form:select path="master.kworhp"  id="kworhp" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
													<form:option value="0">select</form:option>
													<form:option value="KW">KW</form:option>
													<form:option value="HP">HP</form:option>
													</form:select>
													</label>
													</td>
													
									        <td>
									        <label>Supply Type<form:select  path="master.supplytype" name="supplyType" class="form-control" id="supplyType" >
										     <form:option value="0">select</form:option>
                                             <form:option value="HT">HT</form:option>
                                             <form:option value="LT">LT</form:option>
										   </form:select></label>
										   </td> 
										   
										  			
										  			
													<%-- <td>
													
													<label>C Status<form:input path="master.consumerstatus" type="text" class="form-control"  name="consumerstatus" id="consumerstatus" maxlength="1"></form:input></label>
													</td --%>
												    
												    <td>
													<label>SanLoad<form:input path="master.sanload"  id="sanload" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""  maxlength="8"></form:input></label>
													</td>
													<%-- <td>
													<label>ConnDate<div class="input-group input-medium date date-picker" >			
																<form:input path="master.conndate" class="form-control" id="conndate" type="text" autocomplete="off" placeholder="" ></form:input>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="seven"><i class="fa fa-calendar"></i></button>																
																</span>																
															</div>
													</label>
													</td> --%>
													</tr>
													<tr><td>
													<label>MF<form:input path="mf"  id="mf" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" ></form:input></label>
													</td><td>
													<label>CTRN<form:input path="ctrn" name="ctrn" id="ctrn" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""></form:input></label>
													</td><td>
													<label>CTRD<form:input path="ctrd" name="ctrd" id="ctrd" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""></form:input></label>
													</td>
													
													<td>
													<label>Supply Voltage
													   <form:select path="master.supplyvoltage"  id="supplyvoltage" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
													  <form:option value="0">select</form:option>
													   <form:option value="400V">400V</form:option>
                                            		   <form:option value="3.3KV">3.3KV</form:option>
                                           			   <form:option value="11KV">11KV</form:option>
                                             		   <form:option value="33KV">33KV</form:option>
                                             		  <form:option value="66KV">66KV</form:option>
                                            		  <form:option value="132KV">132KV</form:option>
                                            	      <form:option value="220KV">220KV</form:option>
                                             	     <form:option value="400KV">400KV</form:option>												
													</form:select>													
													</label>
													</td>
													</tr>
													<tr><td>
													 <label>Meter Make<form:select path="mtrmake"  id="mtrmake" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
													
													<form:option value="0">select</form:option>
												    <c:forEach items="${mtrmakeList}" var="value">
													<form:option value="${value}">${value}</form:option>
													</c:forEach>
													
													</form:select>
													</label>
													</td>
													<td>
													
													<label>Meter Type
													<form:select path="mtrtype" name="mtrtype" id="mtrtype" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
													<form:option value="0">select</form:option>
													<form:option value="OPTICAL">OPTICAL</form:option>
													<form:option value="IR">IR</form:option>
													<form:option value="CM">CM</form:option>
																									
													</form:select>	
													</label>
													</td>
													<td>
													<label>Initial Reading<form:input path="prkwh"  id="prkwh" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""></form:input></label>
													</td>
													<td>
													<label>Industry Type<form:input path="master.industrytype"  id="industrytype" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" maxlength="50"></form:input></label>
                                                       </td></tr>													

													</table>
													   <div class="modal-footer">															    
													 &nbsp;&nbsp;<button type="button" class="btn blue pull-right" id="updateOption" onclick="validation()">Add Connection</button>		
													 <button type="button" data-dismiss="modal" class="btn white" onclick="myClearFunction();">Clear</button>
													 </div>	
														
										 </form:form>
													
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
</div>

<script>


function getCircleByZone(zone)
{
	//alert(zone);
	$.ajax({
		type : 'GET',
		//url : "./getCircleByZone",
		url : './showCircleMDM' + '/' +zone,
		data:{zone:zone},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Circle</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#circleId").empty();
				$("#circleId").append(html);
				}
			
		}
	});

	
}

function getDivByCircle(circle)
{
	//alert(circle);
var zone = $('#zoneId').val();
	$.ajax({
		type : 'GET',
		//url : "./getDivByCircle",
		url : './showDivisionMDM' +'/' + zone + '/' +circle,
		data:{circle:circle},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Division</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				
				$("#divisionId").empty();
				$("#divisionId").append(html);
				}
			
		}
	});
}

function getSubDivByDivision(division)
{
	//alert(division);
	var zone = $('#zoneId').val();
	var circle=$('#circleId').val();
	$.ajax({
		type : 'GET',
		//url : "./getSubdivByDiv",
		url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/' +division,
		data:{division:division},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Sub-Division</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#sdonameId").empty();
				$("#sdonameId").append(html);
				}
			
		}
	});
}

function validation()
{
	//alert("hi");
	  var regex = /^[a-zA-Z ]*$/;
	var regexMobile=/^[0-9]{10}$/;
	var accno=$("#accno").val();
	var meterno=$("#meterno").val();
	var modemno=$("#modemno").val();
	var zoneId=$("#zoneId").val();
	var circleId=$("#circleId").val();
	var divisionId=$("#divisionId").val();
	var sdonameId=$("#sdonameId").val();
	var name=$("#name").val();
	var address1=$("#address1").val();
	var mobile=$("#mobile").val();
	var status=$("#status").val();
	var contractdemand=$("#contractdemand").val();
	var kworhp=$("#kworhp").val();
	var supplyType=$("#supplyType").val();
	var sanload=$("#sanload").val();
	var mf=$("#mf").val();
	var ctrn=$("#ctrn").val();
	var ctrd=$("#ctrd").val();
	var supplyvoltage=$("#supplyvoltage").val();
	var mtrmake=$("#mtrmake").val();
	var mtrtype=$("#mtrtype").val();
	var prkwh=$("#prkwh").val();
	var industrytype=$("#industrytype").val();

	if(accno=="")
		{
		bootbox.alert("Please Enter Accno");
		return false;
		}

	if(meterno=="")
	{
		bootbox.alert("Please Enter meterno");
		return false;
	}

	if(modemno=="")
	{
		bootbox.alert("Please Enter modemno");
		return false;
	}
	if(zoneId=="" || zoneId==0)
	{
		bootbox.alert("Please Select Zone");
		return false;
	}
	if(circleId=="" || circleId==0)
	{
		bootbox.alert("Please Select Circle");
		return false;
	}
	if(divisionId=="" || divisionId==0)
	{
		bootbox.alert("Please Select Division");
		return false;
	}
	
	if(sdonameId=="" || sdonameId==0)
	{
		bootbox.alert("Please Select Subdivision");
		return false;
	}
	
	if(name=="" || name==0)
	{
		bootbox.alert("Please Enter Name");
		return false;
	}
	if(address1=="" || address1==0)
	{
		bootbox.alert("Please Eneter Address");
		return false;
	}
	if(mobile=="")
	{
		bootbox.alert("Please Enter MobileNo");
		return false;
	}
	 if(!mobile.match(regexMobile))
	 {
	 bootbox.alert('Please Enter 10 digit mobile numbers');
	 return false;
	 }
	
	 if(status=="" || status==0)
	{
		bootbox.alert("Please Select Subdivision");
		return false;
	} 
	if(contractdemand=="" || isNaN(contractdemand))
	{
		bootbox.alert("Please enter contractdemand,Should contain only digits");
		return false;
	}
	
	
	if(kworhp=="" || kworhp==0)
	{
		bootbox.alert("Please Select kworhp");
		return false;
	}
	
	if(supplyType=="" || supplyType==0)
	{
		bootbox.alert("Please Select supplyType");
		return false;
	}
	
	if(sanload=="" || isNaN(sanload))
	{
		bootbox.alert("Please enter sanload,Should contain only digits");
		return false;
	}
	if(mf=="" || isNaN(mf))
	{
		bootbox.alert('please enter MF, should contain only digits');
		return false;
	}
	if(ctrn=="" ||isNaN(ctrn))
	{
		bootbox.alert('please enter CTRN, should contain only digits');
		return false;
	}
	if(ctrd=="" || isNaN(ctrd))
	{
		bootbox.alert('please enter CTRD, should contain only digits');
		return false;
	}
	if(supplyvoltage=="" || supplyvoltage==0)
	{
		bootbox.alert("Please Select supplyvoltage");
		return false;
	}
	if(mtrmake=="" || mtrmake==0)
	{
		bootbox.alert("Please Select mtrmake");
		return false;
	}
	
	if(mtrtype=="" || mtrtype==0)
	{
		bootbox.alert("Please Select mtrtype");
		return false;
	}
	if(prkwh=="" || isNaN(prkwh))
	{
		bootbox.alert('Please enter initial reading, should contain only digits ');
		return false;
	}
	if(industrytype=="" )
	{
		bootbox.alert("Please Select industrytype");
		return false;
	} 
	if(!industrytype.match(regex))
	{
		bootbox.alert('Only alphabets are  allowed in industrytype ');
		return false;
	} 

	$("#newConnectionMeterMasterId").submit();
}

function myClearFunction() {
    document.getElementById("newConnectionMeterMasterId").reset();
}

function checkAccnoExist(accno)
{
	
	 $.ajax({
		 type : 'GET',
		url : "./checkAccnoExist/"+accno,
		async : false,
		cache : false,
		success : function(response)
		{
			if(response != "" )
				{
				$("#accno").val("");
			    
				$("#accountNotExistMsg").html(response);
				
				}else{
					$("#accountNotExistMsg").html(response);
				}
		}
	}); 
	return true;
} 


</script>
