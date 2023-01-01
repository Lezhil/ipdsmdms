<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.*,java.util.*" session="false"%>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>

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
		
		$('#MDMSideBarContents,#mpmId,#consumerModificationId').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');
		
		});
	
	
  
	
	
</script>
<div class="page-content" >
<!-- Display Error Message -->
		<c:if test = "${not empty result}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:green;font-size: 16px" >${result}</span>
			</div>
	    </c:if>	
	    <c:if test = "${not empty result1}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${result1}</span>
			</div>
	    </c:if>	
					<span style="color:red;font-size: 16px" id="accountNotExistMsg" ></span>
			
		<!-- End Error Message -->
  <div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Consumer Details Modification</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
									
									<form:form action="./modifyConsumerData"  class="form-inline" role="form" modelAttribute="consumerMasterId" commandName="consumerMasterId" method="post" id="modifyConsumerDataId" >		  
													
													<hr>
													<table>
													<tr><td>
													<label>AccNo<span style="color: red">*</span><form:input path="accno"  id="accno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter ACCNO" maxlength="13" name="accno" onchange="return onEnterAccno(this.value,1)"></form:input></label>
														</td>
													<td>
													<label>MeterNo<span style="color: red">*</span><form:input path="meterno"  id="meterno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Meterno"  maxlength="10" onchange="return onEnterAccno(this.value,2)"></form:input></label>
													</td>
													<td>
													<label>KNO<span style="color: red">*</span><form:input path="kno"  id="kno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="XXXXXXXXXX"  maxlength="13" onchange="return onEnterAccno(this.value,3)"></form:input></label>
													</td>
											</tr>	
													<tr>
													<td>
													<label>Name<span style="color: red">*</span><form:textarea path="name"  id="name" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Consumer Name" maxlength="100"></form:textarea></label>
													</td><td>
													<label>Address<form:textarea path="address1"  id="address1" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Address"  maxlength="900"></form:textarea></label>
													</td>
													<td hidden="true">
													<label><form:textarea path="id"  id="consumerId" class="form-control placeholder-no-fix" type="text" ></form:textarea></label>
													</td>
													
													<%-- <td><label>Category
									   					<form:select path="tadesc"  id="kworhp" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
														<form:option value="0">select</form:option>
														<form:option value="LT">LT</form:option>
														<form:option value="HT">HT</form:option>
														<form:option value="MIP">MIP</form:option>
													</form:select>
									   				 </label></td> --%>
									   				 <td>
													<label>Email Id<form:input path="email"  id="emailId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter EmailId" ></form:input></label>
                                                       </td> 
									   				 
													 <td>
													<label>Mobile No.<form:input path="phoneno"  id="mobile" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Mobile No" ></form:input></label>
													</td> 
													</tr>
													<tr><td>
													
													<label>CD<form:input path="cd"  id="cdId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Contract Demand" ></form:input></label>
														</td><td>												
													<label>Load unit<span style="color: red">*</span><form:select path="kworhp"  id="kworhp" class="form-control placeholder-no-fix" type="text" autocomplete="off" >
													<form:option value="0">select </form:option>
													<form:option value="KW">KW</form:option>
													<form:option value="HP">HP</form:option>
													</form:select>
													</label>
													</td>
													
									       
										   
												    <td>
													<label>SanLoad<span style="color: red">*</span><form:input path="sanload"  id="sanload" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Sanload"  maxlength="8"></form:input></label>
													</td>
													
													</tr>
													<tr><td>
													<label>MF<span style="color: red">*</span><form:input path="mf"  id="mf" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter MF"></form:input></label>
													</td>
													<%-- <td>
													<label>CTRN<form:input path="" name="ctrn" id="ctrn" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""></form:input></label>
													</td><td>
													<label>CTRD<form:input path="" name="ctrd" id="ctrd" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""></form:input></label>
													</td> --%>
													
													<td>
													<label>Supply Voltage
													   <form:select path="supplyvoltage"  id="supplyvoltage" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
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
													<%-- <td>
													<label>Industry Type<form:input path=""  id="industrytype" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" maxlength="50"></form:input></label>
                                                       </td> --%>
                                                     <%--  <td>
													<label>Email Id<form:input path=""  id="industrytype" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" maxlength="50"></form:input></label>
                                                       </td>  --%>
                                                       <td><label>Category
									   					<form:select path="tadesc"  id="categoryId" name="categoryId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
														<form:option value="0">select</form:option>
														<form:option value="LT">LT</form:option>
														<form:option value="HT">HT</form:option>
														<form:option value="MIP">MIP</form:option>
													</form:select>
									   				 </label></td>
									   				 
									   				 <td><label>BillPeriod
									   					<form:input path="billperiod" type="text" class="form-control"  name="billperiodId" id="billperiodId" ></form:input>
									   				 </label></td>
													</tr>
                                                       <tr>
                                                       <td><label>Tariff Code
									   					<form:input path="tariffcode" type="text" class="form-control"  name="tariffCode" id="tariffCode" ></form:input>
									   				 </label></td>
									   				 
									   				 <td><label>C Status<span style="color: red">*</span>
									   					<form:input path="consumerstatus" type="text" class="form-control"  name="consumerstatus" id="consumerstatus" ></form:input>
									   				 </label></td>
									   				 <td><label>Latitude(Y)
									   					<form:input path="latitude" type="text" class="form-control"  name="latitude" id="latitude" ></form:input>
									   				 </label></td>
									   				 <td><label>Longitude(X)
									   					<form:input path="longitude" type="text" class="form-control"  name="longitude" id="longitude" ></form:input>
									   				 </label></td>
                                                       </tr>
                                                       <tr>
													<td>
													<label>Prepaid<span style="color: red">*</span>
													<form:select  path="prepaid" name="prepaid" class="form-control" id="prepaid" >
										     <form:option value="0">No</form:option>
                                             <form:option value="1">Yes</form:option>
                                             
										   </form:select></label>
													</td>
													<td>
													<label>TOD<span style="color: red">*</span><form:select  path="tod" name="tod" class="form-control" id="tod" >
                                              <form:option value="0">No</form:option>
                                             <form:option value="1">Yes</form:option>
										   </form:select></label> </td>
                                                       <td>
													<label>TOU<span style="color: red">*</span>
													<form:select  path="tou" name="tou" class="form-control" id="tou" >
                                             <form:option value="0">No</form:option>
                                             <form:option value="1">Yes</form:option>
										   </form:select></label>
                                                       </td>
                                                       <%--  <td>
													<label>Billing Start Date
													<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
											<form:input type="text" class="form-control" name="bStartDate" id="bStartDate" path=""></form:input>
										<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div></label>
								</td>  --%>
								<td><label>Billing Start Date<div class="input-group input-medium date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
									<form:input path="billperiodstartdate" type="text" class="form-control" name="billperiodstartdateId" id="billperiodstartdateId"  ></form:input>
									<span class="input-group-btn">
									<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div></label></td>
                                   </tr>
											</table>
											
													    <div class="modal-footer"> 								    
													 &nbsp;&nbsp;<button type="button" class="btn blue pull-right" id="updateOption" onclick="validation()" >Update</button>		
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

function myClearFunction() {
    $("#addConsumerAMIId").reset();
}

function onEnterAccno(accno,flag)
{
var data="";

	$.ajax({
		type:'GET',
		url:"./getConsumerDatetoModify",
		data:{accno:accno,flag:flag},
		async:false,
		cache:false,
		success:function(response)
		{
			$("#accountNotExistMsg").html("");
			if(response.length>0)
				{
				for(var i=0;i<response.length;i++)
				data=response[i];
				$("#consumerId").val(data.id);
				$("#accno").val(data.accno);
				$("#meterno").val(data.meterno);
				$("#kno").val(data.kno);
				$("#name").val(data.name);
				$("#address1").val(data.address1);
				$("#emailId").val(data.email);
				$("#mobile").val(data.phoneno);
				$("#cdId").val(data.cd);
				$("#sanload").val(data.sanload);
				$("#mf").val(data.mf);
				$("#supplyvoltage").val(data.supplyvoltage);
				$("#kworhp").val(data.kworhp);
				$("#categoryId").val(data.tadesc);
				$("#billperiodId").val(data.billperiod);
				$("#tariffCode").val(data.tariffcode);
				$("#consumerstatus").val(data.consumerstatus);
				$("#latitude").val(data.latitude);
				$("#longitude").val(data.longitude);
				$("#prepaid").val(data.prepaid);
				$("#tod").val(data.tod);
				$("#tou").val(data.tou);
				//$("#billperiodstartdateId").val(data.billperiodstartdate);
				if(data.billperiodstartdate!=null||data.billperiodstartdate!=""){
				$("#billperiodstartdateId").val(moment(data.billperiodstartdate).format('YYYY-MM-DD'));
				}
				}
			else
				{
				$("#accountNotExistMsg").html("Data Not Found");
				}
		}
		
		});
}
function validation()
{
	
	var regex = /^[a-zA-Z ]*$/;
	var regexMobile=/^[0-9]{10}$/;
	var accno=$("#accno").val();
	var meterno=$("#meterno").val();
	var kno=$("#kno").val();
	var name=$("#name").val();
	var address1=$("#address1").val();
	var emailId=$("#emailId").val();
	var mobileNo=$("#mobile").val();
	var cd=$("#cdId").val();
	var sanload=$("#sanload").val();
	var mf=$("#mf").val();
	var supplyvoltage=$("#supplyvoltage").val();
	var loadunit=$("#kworhp").val();
	var category=$("#categoryId").val();
	var billperiod=$("#billperiod").val();
	var tariffCode=$("#tariffCode").val();
	var consumerstatus=$("#consumerstatus").val();
	var latitude=$("#latitude").val();
	var longitude=$("#longitude").val();
	var prepaid=$("#prepaid").val();
	var tod=$("#tod").val();
	var tou=$("#tou").val();
	var bStartDate=$("#bStartDate").val();
	
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

	if(kno=="" )
	{
		bootbox.alert("Please Enter KNO");
		return false;
	}
	
	if(name=="")
	{
		bootbox.alert("Please enter Name");
		return false;
	}
	
	if(address1=="")
	{
		bootbox.alert("Please Enter Address");
		return false;
	}
	if(emailId=="")
	{
		bootbox.alert("Please Eneter EmailId");
		return false;
		
	}
	if(mobileNo=="")
	{
		bootbox.alert("Please Enter MobileNo");
		return false;
	}
	 if(!mobileNo.match(regexMobile))
	 {
	 bootbox.alert('Please Enter 10 digit mobile numbers');
	 return false;
	 }
	
	 if(cd=="" || isNaN(cd))
	{
		bootbox.alert("Please enter contractdemand,Should contain only digits");
		return false;
	} 
	if(loadunit=="" || loadunit==0)
		{
		bootbox.alert("please select load unit");
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
	
	if(supplyvoltage=="" || supplyvoltage==0)
	{
		bootbox.alert("Please Select supplyvoltage");
		return false;
	}
	 if(category=="" || category==0)
	{
		bootbox.alert("Please Select category");
		return false;
	}
	 if(billperiod=='')
		{
			bootbox.alert('Please enter billperiod');
			return false;
		}
	if(tariffCode=='')
	{
		bootbox.alert('Please enter tariffCode');
		return false;
	}
	
	if(consumerstatus=='' || !consumerstatus.match(regex))
	{
		bootbox.alert('Please enter  consumerstatus , and only alpahabets ');
		return false;
	}
	if(latitude=='')
	{
		bootbox.alert('Please enter latitude');
		return false;
	}
	if(longitude=='')
	{
		bootbox.alert('Please enter longitude');
		return false;
	} 
		
	 $("#modifyConsumerDataId").submit();
	 
}
</script>
