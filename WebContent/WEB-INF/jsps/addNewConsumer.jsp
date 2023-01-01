<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- <style>

label{
	margin-left:50px	
	
}
</style> -->
<script  type="text/javascript">

	$(".page-content").ready(function(){			
		//$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
		
		$('#MDMSideBarContents,#mpmId,#addConsumerId').addClass('start active ,selected');
		$("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports,#meterMaster,#other-Reports").removeClass('start active ,selected');
		
		$('#meterno').attr('readonly', false);
		$('#accno').attr('readonly', false);
		$('#kno').attr('readonly', false);
		});
	
	$( document ).ready(function() {
		getAllLocation('${officeCode}','${officeType}');
	});
</script>
<div class="page-content">
 <c:if test = "${not empty result}">
			         <div class="alert alert-success display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:green" >${result}</span>
			       </div>
	                </c:if>
	                
	<div class="row">                                    
	
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Consumer Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
								<div class="btn-group">
								<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" value="addSingle"  onclick="callAddConsumer();">
									      Add Consumer <i class="fa fa-plus"></i>
								 </button>
									  
								         </div><br/><br/>
							         
                   <div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="print">Print</a></li>
									<li><a href="#" id="excelExport"
										onclick="tableToExcel('sample_1', 'Total Meters')">Export
											to Excel</a></li>
								</ul>
							</div>
					</div>
					<table class="table  table-bordered table-hover" id="sample_1">	
					
						<thead>
							<tr>
								<th>Edit</th>
								<th>Account Number</th>
								<th>K Number</th>
								<th>Name</th>
								<th>Address</th>
								<th>mobile number</th>
								<th>Email ID</th>
								<th>load unit</th>
								<th>Sanction load</th>
								<th>Connected load</th>
								<th>Contract Demand</th>
								<th>Supply Voltage</th>
								 <th>Consumer Category</th>
								<th>Service Status</th>
								<th>DT Code</th>
								<th>Feeder Code</th>
								<th>Prepaid</th>
								<th>TOD</th>
								<th>TOU</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<th>BillPeriod</th>
								<th>Billing Period Start Date</th>
								<th>Meter Serial Number</th>
								<th>MF</th> 
							</tr>
						</thead>
							<tbody id="consumerDetailsId" >
									
								   <c:forEach var="element" items="${consumerDetails}">
									<tr >
										<td><a href="#" onclick="editConsumerDetails(this.id,${element.id})" id="editData${element.id}">Edit</a></td>
										<td>${element.accno}</td>
										<td>${element.kno}</td>
										<td>${element.name}</td>
										<td>${element.address1}</td>
										<td>${element.phoneno}</td>
										<td>${element.email}</td>
										<td>${element.kworhp}</td>
										<td>${element.sanload}</td>
										<td>${element.conload}</td>
										<td>${element.cd}</td>
										<td>${element.supplyvoltage}</td>
										 <td>${element.tadesc}</td>
										<td>${element.consumerstatus}</td>
										<td>${element.dtcode}</td>
										<td>${element.feedercode}</td>
										<td>${element.prepaid}</td>
										<td>${element.tod}</td>
										<td>${element.tou}</td>
										<td>${element.latitude}</td>
										<td>${element.longitude}</td>
										<td>${element.billperiod}</td>
										<td>${element.billperiodstartdate}</td>
										<td>${element.meterno}</td>
										<td>${element.mf}</td> 
										
									</tr>
									</c:forEach>   
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					</div>
<div id="stack1" class="modal fade" role="dialog"  aria-labelledby="myModalLabel10" >
								<div class="modal-dialog" style="width: 60%">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title"><span id="addMeterStackId" style="color: #474627;font-weight: bold;"  >Add Consumer</span></h4>
										</div>
										<span style="color:red;font-size: 16px;"  id="accountNotExistMsg" ></span>
										<div class="modal-body">
						 	               <form:form action=""  method="post" id="addConsumerAMIId" modelAttribute="consumerMasterId" commandName="consumerMasterId"> 		  
													
													<div class="row"  >
													<div class="col-md-4"><label>CIRCLE</label>
							                       	<select   id="circleId"  name="circleId" class="form-control placeholder-no-fix"  onchange="getDivByCircle(this.value);">
													
													
													</select>
													</div>
													
													
														<div class="col-md-4"><label>DIVISION</label>
							                       	<select   id="divisionId" name="divisionId" class="form-control placeholder-no-fix" onchange="getSubDivByDivision(this.value)">
													<option value="0">Select Division</option>
													
													</select>
													</div>
													
													<div class="col-md-4"><label>SUBDIVISION</label>
							                       	<select   id="sdonameId" name="sdonameId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" onchange="getSubStations(this.value)" >
													<option value="0">Select Sub-Division</option>
													
													</select>
													</div>
													</div>
													<div class="row"  >
													<div class="col-md-4"><label>SUBSTATION</label>
							                       	<select   id="substationId" class="form-control placeholder-no-fix"  onchange="getFeeders(this.value);">
													<option value="0">Select SubStation</option>
													
													</select>
													</div>
													
													
														<div class="col-md-4"><label>FEEDER</label>
							                       	<select   id="feederId" name="feederId" class="form-control placeholder-no-fix" onchange="getDTC(this.value)">
													<option value="0">Select Feeder</option>
													
													</select>
													</div>
													
													<div class="col-md-4"><label>DT</label>
							                       	<select   id="dtId" name="dtId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder=""  >
													<option value="0">Select DTC</option>
													
													</select>
													</div>
													</div>
													
													<br>
													<div class="row"  >
													<div class="col-md-4"><label>AccNo</label><span style="color: red">*</span>
													<form:input path="accno"  id="accno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter ACCNO" maxlength="13" name="accno"  onchange="return checkAccnoExist(this.value)"></form:input><!--onchange="return checkAccnoExist(this.value)  -->
													</div>
													
													<div class="col-md-4" id="mtrPrfxDivId" ><label>MeterNo</label><span style="color: red">*</span>
													<form:input path="meterno"  id="meterno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Meterno"  maxlength="10" onchange="return checkMeterno(this.value)" ></form:input>
													<form:input path=""  id="oldmeterno" class="form-control placeholder-no-fix" type="hidden" autocomplete="off" placeholder="Enter Meterno"  maxlength="10" ></form:input>
														<%-- <select
														class="form-control select2me" name="meterno"
														id="meterno" onchange="return checkMeterno(this.value)">
														<option value="0">Select</option>
														<c:forEach var="elements" items="${InstockMeters}">
															<option value="${elements}">${elements}</option>
														</c:forEach>
													</select> --%>
													</div>
												
													<div class="col-md-4"  id="mtrdivId"><label>KNO</label><span style="color: red">*</span>
													<form:input path="kno"  id="kno" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Kno"  maxlength="13"  onchange="return checkForKno(this.value)"></form:input><!-- onchange="return checkForKno(this.value)" -->
													</div>
													</div>
													<div class="row"  >
													<div class="col-md-4"><label>Consumer Name</label><span style="color: red">*</span>
													<form:input path="name" type="text" class="form-control"  name="consumerNameId" id="consumerNameId" ></form:input>
													</div>
													
													
													<div class="col-md-4"><label>Email ID</label><span style="color: red"></span>
													<form:input path="email"  id="emailId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter EmailId" ></form:input>
													</div>
													<div class="col-md-4"><label>Phone No</label><span style="color: red"></span>
													<form:input path="phoneno"  id="mobile" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Mobile No" maxlength="10" ></form:input></label>
													</div>
													</div>
													  
													<br>
													<div class="row">
													<div class="col-md-4" ><label>Address</label><span style="color: red"></span>
													<form:textarea path="address1"  id="address1" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Address"  maxlength="100"></form:textarea>
													</div>
													<div class="col-md-4"><label>CD</label><span style="color: red"></span>
													<form:input path="cd"  id="cdId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Contract Demand" ></form:input></label>
													</div>
													<div class="col-md-4"><label>Load unit</label><span style="color: red">*</span>
													<form:select path="kworhp"  id="kworhp" class="form-control placeholder-no-fix" type="text" autocomplete="off" >
													<form:option value="0">select </form:option>
													<form:option value="KW">KW</form:option>
													<form:option value="HP">HP</form:option>
													</form:select>
													</div>
													</div>
													<br>
													<div class="row">
													<div class="col-md-4"><label>San Load</label><span style="color: red">*</span>
													<form:input path="sanload"  id="sanload" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter Sanload"  maxlength="8"></form:input>
													</div>
													
													<div class="col-md-4"><label>MF</label><span style="color: red">*</span>
													<form:input path="mf"  id="mf" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Enter MF" onchange="checkMF(this.value)"></form:input>
													<form:input path=""  id="oldmf" class="form-control placeholder-no-fix" type="hidden" autocomplete="off" placeholder="Enter MF" onchange="checkMF(this.value)"></form:input>
													</div>
													<div class="col-md-4"><label>Consumer Status</label><span style="color: red">*</span>
													<form:input path="consumerstatus" type="text" class="form-control"  name="consumerstatus" id="consumerstatus" ></form:input>
													</div>
												
													</div>
													
													<br>
													<div class="row">
													<div class="col-md-4"><label>Supply Voltage</label>
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
													</div>
													<div class="col-md-4"><label>Category</label>
													<form:select path="tadesc"  id="categoryId" name="categoryId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="">
														<form:option value="0">select</form:option>
														<form:option value="LT">LT</form:option>
														<form:option value="HT">HT</form:option>
														<form:option value="MIP">MIP</form:option>
													</form:select>
													</div>
													
													<div class="col-md-4" ><label>Bill Period</label>
													<form:input path="billperiod" type="text" class="form-control"  name="billperiodId" id="billperiodId" ></form:input>
													</div>
													</div>
													
													<br>
													<div class="row">
													<div class="col-md-4"><label>Tariff Code</label>
													<form:input path="tariffcode" type="text" class="form-control"  name="tariffCode" id="tariffCode" ></form:input>
													</div>
													<div class="col-md-4"><label>Longitude</label>
													<form:input path="longitude" type="text" class="form-control"  name="longitude" id="longitude" ></form:input>
													</div>
													<div class="col-md-4"><label>Latitude</label>
													<form:input path="latitude" type="text" class="form-control"  name="latitude" id="latitude" ></form:input>
													</div>
													</div>
													<br>
										<div class	="row">
											<div class="col-md-4"><label>TOD</label><span style="color: red">*</span>
											<form:select  path="tod" name="tod" class="form-control" id="tod" >
											<form:option value="0">NO</form:option>
	                                        <form:option value="1">YES</form:option>
	                                       <%--  <form:option value="0">NO</form:option> --%>
										  	</form:select></div>
										   <div class="col-md-4"><label>TOU</label><span style="color: red">*</span>
										   <form:select  path="tou" name="tou" class="form-control" id="tod" >
											     <form:option value="0">NO</form:option>
	                                             <form:option value="1">YES</form:option>
	                                             <%-- <form:option value="0">NO</form:option> --%>
										  			</form:select></div>
										  	<div class="col-md-4">	
										  	<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years" id ="billStartDateId"><label>Bill Start Date</label><span style="color: red">*</span>
											<form:input type="text" class="form-control" name="bStartDate" id="bStartDate" path="billperiodstartdate"></form:input>
											<span class="input-group-btn" >
											<!--  <button class="btn default" type="button"><i class="fa fa-calendar"></i></button> --> 
											</span>
											</div>
											</div>
													</div>
													<br>
													<div class	="row">
											<div class="col-md-4"><label>Prepaid</label><span style="color: red">*</span>
											<form:select  path="prepaid" name="prepaid" class="form-control" id="prepaid" >
										     <form:option value="0">NO</form:option>
                                             <form:option value="1">YES</form:option>
                                            <%--  <form:option value="0">NO</form:option> --%>
                                             
										   </form:select></div>
										   <div class="col-md-4"><label>Connection Load</label><span style="color: red"></span>
														<form:input path="conload"  id="conloadId" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Connection Load"  maxlength="8"></form:input>
														</div>
										   <div class="col-md-4" hidden="true"><label>id</label>
														<form:input path="id" type="text" class="form-control"  name="consumerId" id="consumerId" ></form:input>
											</div>
											<div class="col-md-4"><label>Third Party Feeder Code</label>
											<form:input path="tpfeedercode" type="text" class="form-control"  name="FeederTpId" id="FeederTpId"  placeholder="Enter Feeder sTP code"></form:input>
											</div>
										   
										   </div>
										   
										   <div class="row">
														
											
											<div class="col-md-4"><label>Third Party DT Code</label><span style="color: red"></span>
											<form:input path="tpdtcode" type="text" class="form-control"  name="dtTpCodeId" id="dtTpCodeId" placeholder="Enter DT TP code" ></form:input>
											</div>
											
											<div class="col-md-4" id="mtrdatechngdivId" hidden="true">	
										  	<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years" ><label>Meter Change Date</label><span style="color: red">*</span>
											<form:input type="text" class="form-control" name="mtrdatechngId" id="mtrdatechngId" path=""></form:input>
											<span class="input-group-btn" >
											<!--  <button class="btn default" type="button"><i class="fa fa-calendar"></i></button> --> 
											</span>
											</div>
											</div>
											
											<div class="col-md-4" id="mfdatechngdivId" hidden="true">	
										  	<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years" ><label>MF Change Date</label><span style="color: red">*</span>
											<form:input type="text" class="form-control" name="mfdatechngId" id="mfdatechngId" path=""></form:input>
											<span class="input-group-btn" >
											<!--  <button class="btn default" type="button"><i class="fa fa-calendar"></i></button> --> 
											</span>
											</div>
											</div>
													</div>
													
													
										            <div class="modal-footer">
														<button class="btn blue pull-right" id="addConsumerbtnId" type="button" value="save" onclick="validation()" >ADD</button>  
														<button class="btn blue pull-right" id="updateConsumerbtnId" type="button" value="update" onclick="updateValidation();" hidden="true">UPDATE</button>  
														 <button class="btn blue pull-right" id="addUpdatebtnId" type="button" value=""  style="display: none"></button> 
														<button type="button" data-dismiss="modal" class="btn">Cancel</button>
													</div>
										</form:form>
							</div>
							</div>
							</div>
							</div>

<script>


function getCircleByZone(zone)
{
	//alert(zone);
	$.ajax({
		type : 'GET',
		url : "./getCircleByZone",
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
	var zone="%";
	$.ajax({
		type : 'GET',
		url : "./getDivByCircle",
		data:{circle:circle,zone:zone},
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

function getBtnValue(){
$("#addUpdatebtnId").val("1");
var btnVal=$("#addUpdatebtnId").val();
alert(btnVal);
	
}

function getSubDivByDivision(division)
{
	//alert(division);
	var zone = "%";
			var circle = $('#circleId').val();
	$.ajax({
		type : 'GET',
		url : "./getSubdivByDiv",
		data:{zone : zone,
			  circle : circle,
			  division : division},
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


function myClearFunction() {
    $("#").reset();
}

function checkAccnoExist(accno)
{
	 $.ajax({
		type : 'GET',
		url : "./checkAccnoExistInConsuMaster/"+accno,
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			if(response != "" )
				{
				$("#accno").val("");
			    
				//$("#accountNotExistMsg").html(response);
				bootbox.alert(accno+ " Already Present" );
				
				}else{
					$("#accountNotExistMsg").html(response);
				}
		}
	}); 
	

	return true;
}

function validation()
{
	
	var regex = /^[a-zA-Z ]*$/;
	var floatNo=/^[0-9]*\.?[0-9]*$/;
	var regexOnlyCharandNo = /^[a-zA-Z0-9 ]*$/;
	var regexMobile=/^[0-9]{10}$/;
	var accno=$("#accno").val();
	var meterno=$("#meterno").val();
	var kno=$("#kno").val();
	var name=$("#consumerNameId").val();
	var address1=$("#address1").val();
	var emailId=$("#emailId").val();
	var mobileNo=$("#mobile").val();
	var cd=$("#cdId").val();
	var sanload=$("#sanload").val();
	var mf=$("#mf").val();
	var supplyvoltage=$("#supplyvoltage").val();
	var loadunit=$("#kworhp").val();
	var category=$("#categoryId").val();
	var billperiod=$("#billperiodId").val();
	var tariffCode=$("#tariffCode").val();
	var consumerstatus=$("#consumerstatus").val();
	var latitude=$("#latitude").val();
	var longitude=$("#longitude").val();
	var prepaid=$("#prepaid").val();
	var tod=$("#tod").val();
	var tou=$("#tou").val();
	var bStartDate=$("#bStartDate").val();
	var connLoad=$("#conloadId").val();
	var dtcode=$("#dtId").val();
	var fdrcode=$("#feederId").val();
	var substation=$("#substationId").val();
	var circle=$("#circleId").val();
	var division=$("#divisionId").val();
	var subdiv=$("#feederId").val();
	var curDate=new Date().getDate();
	var bdate=bStartDate.split("-")[2];
	var bMonth=bStartDate.split("-")[1];
	var curMonth=new Date().getMonth()+1;
	
	
		if(circle==0){
		bootbox.alert("Please Select Circle");
		return false;
		}
		if(division==0){
		bootbox.alert("Please Select Division");
		return false;
		}
		if(subdiv==0){
		bootbox.alert("Please Select SubDivision");
		return false;
		}
		if(substation==0){
		bootbox.alert("Please Select Substation");
		return false;
		}
		if(fdrcode==0){
		bootbox.alert("Please Select Feeder");
		return false;
		}
		if(dtcode==0){
		bootbox.alert("Please Select DTC");
		return false;
		}
	
	    if(accno=="")
		{
		bootbox.alert("Please Enter Accno");
		$("#accno").val("");
		return false;
		}
	  if(!accno.match(regexOnlyCharandNo))
		{
		bootbox.alert("Special Charcters Not allowed For Accno");
		$("#accno").val("");
		return false;
		}
	  if(accno.length<8 && accno.length>8)
		{
		bootbox.alert("Accno must be more than 8 letters");
		$("#accno").val("");
		return false;
		}

	if(meterno=="")
	{
		bootbox.alert("Please Enter meterno");
		$("#meterno").val("");
		return false;
	}
	if(!meterno.match(regexOnlyCharandNo))
	{
		bootbox.alert("Special Charcters Not allowed For Accno");
		$("#meterno").val("");
		return false;
	}
	if(meterno.length<6)
	{
		bootbox.alert("MeterNo must be minimumum 7 letters");
		$("#meterno").val("");
		return false;
	}
	
	if(kno=="")
	{
		bootbox.alert("Please Enter KNO");
		$("#kno").val("");
		return false;
	}
		
  	 if(isNaN(kno)|| kno.length<9)
	{
		bootbox.alert("Enter Only Numbers minimum 10 digits for Kno");
		$("#kno").val("");
		return false;
	} 
	
	if(name=="")
	{
		bootbox.alert("Please Enter Name");
		return false;
	}
	
	 if(mobileNo.length>0)
	{
			if(!mobileNo.match(regexMobile))
		{
			bootbox.alert('Please Enter 10 digit mobile numbers');
			$("#mobile").val("");
			return false;
		}
	}

	if(cd.length>0)
	{
		 if(isNaN(cd))
		{
			bootbox.alert("Contractdemand,Should contain only digits");
			return false;
		}  

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
	
	if(consumerstatus=="" || !consumerstatus.match(regex))
	{
		bootbox.alert('please enter ConsumerStatus, should contains only Alphebets');
		return false;
	}
	if(billperiod.length>0){
		if(isNaN(billperiod)||(billperiod>0 && billperiod>30))
			{
			bootbox.alert('BillPeriod should be a no between 1 to 30');
			return false;
			}
		}
	
	if(longitude.length> 0)
	{
		 if(!longitude.match(floatNo))
			{
				bootbox.alert('Only Numbers allowed in longitude');
				$("#longitude").val("");
				return false;
			} 
	} 
	if(latitude.length > 0)
	{
		if(!latitude.match(floatNo)){
			bootbox.alert('Only Numbers allowed in latitude');
			$("#latitude").val("");
		return false;
		}
	}

	if(connLoad.length>0)
		{
			if(!connLoad.match(floatNo)){
				bootbox.alert('Only Numbers allowed in connectionLoad');
				$("#conloadId").val("");
				return false;
			}
		}
	if(bStartDate=="")
	{
		bootbox.alert('please Select BillStartDate');
		return false;
	}
	if(curMonth!=bMonth)
	{
	bootbox.alert("Please Select BillStartDate in Current Month Only");
	$("#bStartDate").val("");
	return false;
	}
	if(bdate<curDate)
		{
		bootbox.alert("Bill Period must Not be past Date");
		$("#bStartDate").val("");
		return false;

		} 
	

	$("#addConsumerAMIId").attr('action','./addNewConsumerData').submit();
	
}


function updateValidation()
{
	
	var regex = /^[a-zA-Z ]*$/;
	var floatNo=/^[0-9]*\.?[0-9]*$/;
	var regexOnlyCharandNo = /^[a-zA-Z0-9 ]*$/;
	var regexMobile=/^[0-9]{10}$/;
	var accno=$("#accno").val();
	var meterno=$("#meterno").val();
	var kno=$("#kno").val();
	var name=$("#consumerNameId").val();
	var address1=$("#address1").val();
	var emailId=$("#emailId").val();
	var mobileNo=$("#mobile").val();
	var cd=$("#cdId").val();
	var sanload=$("#sanload").val();
	var mf=$("#mf").val();
	var supplyvoltage=$("#supplyvoltage").val();
	var loadunit=$("#kworhp").val();
	var category=$("#categoryId").val();
	var billperiod=$("#billperiodId").val();
	var tariffCode=$("#tariffCode").val();
	var consumerstatus=$("#consumerstatus").val();
	var latitude=$("#latitude").val();
	var longitude=$("#longitude").val();
	var prepaid=$("#prepaid").val();
	var tod=$("#tod").val();
	var tou=$("#tou").val();
	var bStartDate=$("#bStartDate").val();
	var connLoad=$("#conloadId").val();
	var dtcode=$("#dtId").val();
	var fdrcode=$("#feederId").val();

	var btnvalue=$("#addUpdatebtnId").val();
	var oldmeter=$("#oldmeterno").val();
	var mtrchngedate=$("#mtrdatechngId").val();
	var mfchngedate=$("#mfdatechngId").val();
	var oldmf=$("#oldmf").val();

	
	   if(accno=="")
		{
		bootbox.alert("Please Enter Accno");
		$("#accno").val("");
		return false;
		}
	  if(!accno.match(regexOnlyCharandNo))
		{
		bootbox.alert("Special Charcters Not allowed For Accno");
		$("#accno").val("");
		return false;
		}
	  if(accno.length<8 && accno.length>8)
		{
		bootbox.alert("Accno must be more than 8 letters");
		$("#accno").val("");
		return false;
		}

	if(meterno=="")
	{
		bootbox.alert("Please Enter meterno");
		$("#meterno").val("");
		return false;
	}
	if(!meterno.match(regexOnlyCharandNo))
	{
		bootbox.alert("Special Charcters Not allowed For Accno");
		$("#meterno").val("");
		return false;
	}
	if(meterno.length<6)
	{
		bootbox.alert("MeterNo must be minimumum 7 letters");
		$("#meterno").val("");
		return false;
	}
	
	if(kno=="")
	{
		bootbox.alert("Please Enter KNO");
		$("#kno").val("");
		return false;
	}
		
  	 if(isNaN(kno)|| kno.length<9)
	{
		bootbox.alert("Enter Only Numbers minimum 10 digits for Kno");
		$("#kno").val("");
		return false;
	} 
	
	if(name=="")
	{
		bootbox.alert("Please Enter Name");
		return false;
	}
	
	 if(mobileNo.length>0)
	{
			if(!mobileNo.match(regexMobile))
		{
			bootbox.alert('Please Enter 10 digit mobile numbers');
			$("#mobile").val("");
			return false;
		}
	}

	if(cd.length>0)
	{
		 if(isNaN(cd))
		{
			bootbox.alert("Contractdemand,Should contain only digits");
			return false;
		}  

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
	
	if(consumerstatus=="" || !consumerstatus.match(regex))
	{
		bootbox.alert('please enter ConsumerStatus, should contains only Alphebets');
		return false;
	}
	if(billperiod.length>0){
		if(isNaN(billperiod)||(billperiod>0 && billperiod>30))
			{
			bootbox.alert('BillPeriod should be a no between 1 to 30');
			return false;
			}
		}
	
	if(longitude.length> 0)
	{
		 if(!longitude.match(floatNo))
			{
				bootbox.alert('Only Numbers allowed in longitude');
				$("#longitude").val("");
				return false;
			} 
	} 
	if(latitude.length > 0)
	{
		if(!latitude.match(floatNo)){
			bootbox.alert('Only Numbers allowed in latitude');
			$("#latitude").val("");
		return false;
		}
	}

	if(connLoad.length>0)
		{
			if(!connLoad.match(floatNo)){
				bootbox.alert('Only Numbers allowed in connectionLoad');
				$("#conloadId").val("");
				return false;
			}
		}
	if(bStartDate=="")
	{
		bootbox.alert('please Select BillStartDate');
		return false;
	}
	if(btnvalue=="2"){
		
		if(meterno!=oldmeter)
		{
		if(mtrchngedate==""){
			bootbox.alert("Please Select Meter Change Date");
			$("#mtrdatechngId").val("");
			return false;
			}
		if(oldmf!=mf){
			if(mfchngedate==""){
				bootbox.alert("Please Select MF Change Date");
				$("#mfrdatechngId").val("");
				return false;
				}
		}
		
		}
	}
	

	$("#addConsumerAMIId").attr('action','./modifyConsumerData').submit();
	
}


function checkMeterno(meterno){
var btnvalue=$("#addUpdatebtnId").val();
var oldmeter=$("#oldmeterno").val();
if(meterno==""){
	bootbox.alert("Please enter Meterno");
	$("#meterno").val("");
	return false;
}


if(btnvalue=="2"){
	if(meterno!=oldmeter){
		//$("#mfdatechngdivId").show();
		$("#mtrdatechngdivId").show();
		
	}
	if(meterno==oldmeter){
		//$("#mfdatechngdivId").show();
		$("#mtrdatechngdivId").hide();
		
	}
	//$("#mfdatechngdivId").show();
	//$("#mtrdatechngdivId").show();
}


	//onEnterAccno(meterno,2);
if(meterno!=oldmeter){
$.ajax(
			{
					type : "GET",
					url : "./checkMeterNoInInventory/" + meterno,
					dataType : "json",
					success : function(response)
					{
						if(response.length==0)
						{
							bootbox.alert("Meter Number " +meterno+ " Not In Stock");
							$("#meterno").val("");
							if(btnvalue=="2"){
							$("#mtrdatechngdivId").hide();
							}
							return false;
						}
						else
							{
							for(var i=0;i<response.length;i++)
								{
								if(response[0].meter_status=="INSTOCK")
  								{
									checkMeterNoInMasterMain(meterno);
									/* 
									if(data==null){
  								$('#metermanufacturer').val(response[0].meter_make);
  								//$('#MeterMFDivId').val(response[0].meter_make);
									}
								else{
									bootbox.alert("Meter Number " +meterno+ " Already Installed");
		  							$("#meterno").val("");
		  							return false;
  								} */
								}
								else{
									bootbox.alert("Meter Number " +meterno+ " Already Installed");
		  							$("#meterno").val("");
		  							if(btnvalue=="2"){
		  							$("#mtrdatechngdivId").hide();
		  							}
		  							return false;
	  	  	  					 
	  	  	  					 }
							}
					}
					}
			});
	}
}

function checkMF(mf)
{
	var btnvalue=$("#addUpdatebtnId").val();
	var oldmf=$("#oldmf").val();
	//alert("oldmf--"+oldmf+"--newmf-"+mf);
	if(btnvalue=="2"){
			if(oldmf!=mf){
				$("#mfdatechngdivId").show();
			}
			if(oldmf==mf){
				$("#mfdatechngdivId").hide();
			}
	
		}
}


function checkForKno(kno)
{
	$.ajax({
		type:'GET',
		url:"./checkFOrKnoExist/"+kno,
		async:false,
		cache:false,
		success:function(response)
		{
			if(response != "" )
			{
			$("#kno").val("");
		    
			//$("#accountNotExistMsg").html(response);
			bootbox.alert("KNO "+kno+ " Already Present" );
			}else{
				$("#accountNotExistMsg").html(response);
			}
		}
		
		});

}

function editConsumerDetails(param,opera)
{
	$("#addUpdatebtnId").val("2");
	var btnVal=$("#addUpdatebtnId").val();
	
	//$('#meterno').attr('readonly', true);
	$('#accno').attr('readonly', true);
	$('#kno').attr('readonly', true);
	$("#addConsumerbtnId").hide();
	$("#updateConsumerbtnId").show();
	document.getElementById('addConsumerAMIId').reset();
	document.getElementById('addMeterStackId').innerHTML='';
	document.getElementById('addMeterStackId').innerHTML='Modify Consumer';
	 var operation=parseInt(opera);
     $.ajax(
			{
					type : "GET",
					url : "./getConsumerDataById/" + operation,
					dataType : "json",
					success : function(response)
					{
						$("#accno").val(response.accno);
						$("#meterno").val(response.meterno);
						$("#oldmeterno").val(response.meterno);
						$("#kno").val(response.kno);
						$("#consumerNameId").val(response.name);
						$("#address1").val(response.address1);
						$("#emailId").val(response.email);
						$("#mobile").val(response.phoneno);
						$("#cdId").val(response.cd);
						$("#sanload").val(response.sanload);
						$("#mf").val(response.mf);
						$("#oldmf").val(response.mf);
						if(response.kworhp!=null){
							$("#kworhp").val(response.kworhp);
							}
						else{
							$("#kworhp").val(0);
							}
						
						if(response.supplyvoltage!=null)
							{
							$("#supplyvoltage").val(response.supplyvoltage);
							}
						else
							{
							$("#supplyvoltage").val(0);
							}
						
						if(response.tadesc!=null){
							$("#categoryId").val(response.tadesc);
							}
						else
							{
							$("#categoryId").val(0);
							}
						
						$("#billperiodId").val(response.billperiod);
						$("#tariffCode").val(response.tariffcode);
						$("#consumerstatus").val(response.consumerstatus);
						$("#latitude").val(response.latitude);
						$("#longitude").val(response.longitude);
						$("#prepaid").val(response.prepaid);
						$("#tod").val(response.tod);
						$("#tou").val(response.tou);
						$("#bStartDate").val(response.billperiodstartdate);
						$("#conloadId").val(response.conload);
						$("#consumerId").val(response.id);
						$("#dtTpCodeId").val(response.tpdtcode);
						$("#FeederTpId").val(response.tpfeedercode);

						getAlldtLocation(response.meterno);

						
					}
			}); 
		
    $('#'+param).attr("data-toggle", "modal");
	$('#'+param).attr("data-target","#stack1");   
	    
}; 

function callAddConsumer(){
	$("#addUpdatebtnId").val("1");
	var btnVal=$("#addUpdatebtnId").val();
	
		
	getAllLocation('${officeCode}','${officeType}');
	$('#meterno').attr('readonly', false);
	$('#accno').attr('readonly', false);
	$('#kno').attr('readonly', false);
	
	$("#addConsumerbtnId").show();
	$("#updateConsumerbtnId").hide();
	document.getElementById('addConsumerAMIId').reset();
	document.getElementById('addMeterStackId').innerHTML='';
	document.getElementById('addMeterStackId').innerHTML='Add Consumer';
}


function getAllLocation(officeCOde,officeType)
{
	$.ajax({
		type : 'GET',
		url : "./getAllLocationData",
		data:{officeCOde:officeCOde,officeType:officeType},
		async : false,
		cache : false,
		success : function(response)
		{
			var html="";
			if(response!=null)
				{
				if(officeType=="discom"){
				html+="<option value=0>Select Circle</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
					}
				$("#circleId").empty();
				$("#circleId").append(html);
				}

				else if(officeType=="division"){
					var htmlCircle=""; 
					var htmlDivision=""; 
					//var htmlSubdivision=""; 
					for(var i=0;i<response.length;i++)
					{
						htmlCircle+="<option value='"+response[i][0]+"'>"+response[i][0]+"</option>"; 
						htmlDivision+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
						//htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
					}
					$("#circleId").empty();
					$("#circleId").append(htmlCircle);
					$("#divisionId").empty();
					$("#divisionId").append(htmlDivision);
					//$("#sdonameId").empty();
					//$("#sdonameId").append(htmlSubdivision);
					
					}
				else if(officeType=="circle"){
					html+=""; 
					for(var i=0;i<response.length;i++)
						{
						html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; 
						}
					$("#circleId").empty();
					$("#circleId").append(html);
					getDivByCircle($("#circleId").val());
					}
				
				else if(officeType=="subdivision"){
					var htmlCircle=""; 
					var htmlDivision=""; 
					var htmlSubdivision=""; 
					for(var i=0;i<response.length;i++)
					{
						htmlCircle+="<option value='"+response[i][0]+"'>"+response[i][0]+"</option>"; 
						htmlDivision+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"; 
						htmlSubdivision+="<option value='"+response[i][2]+"'>"+response[i][2]+"</option>"; 
					}
					$("#circleId").empty();
					$("#circleId").append(htmlCircle);
					$("#divisionId").empty();
					$("#divisionId").append(htmlDivision);
					$("#sdonameId").empty();
					$("#sdonameId").append(htmlSubdivision);
					getSubStations($('#sdonameId').val());
					}
				
				}
			
		}
	});
}

function getSubStations(subdivision)
{
	$.ajax({
		type : 'GET',
		url : "./getSubStations",
		data:{subdivision:subdivision},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select SubStation</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i][0]+"'>"+response[i][0]+' -- '+response[i][1]+"</option>"; 
					}
				$("#substationId").empty();
				$("#substationId").append(html);
				}
			
		}
	});
}

function getFeeders(ssid)
{
	$.ajax({
		type : 'GET',
		url : "./getFeeders",
		data:{ssid:ssid},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select Feeder</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i][0]+"'>"+response[i][0]+' -- '+response[i][1]+"</option>"; 
					}
				$("#feederId").empty();
				$("#feederId").append(html);
				}
			
		}
	});
}

function getDTC(fdrid)
{
	$.ajax({
		type : 'GET',
		url : "./getDtcByFeeders",
		data:{fdrid:fdrid},
		async : false,
		cache : false,
		success : function(response)
		{
			//alert(response);
			var html="";
			if(response!=null)
				{
				html+="<option value=0>Select DTC</option>"; 
				for(var i=0;i<response.length;i++)
					{
					html+="<option value='"+response[i][0]+"'>"+response[i][0]+' -- '+response[i][1]+"</option>"; 
					}
				$("#dtId").empty();
				$("#dtId").append(html);
				}
			
		}
	});
}

function getAlldtLocation(meterno)
{
	$.ajax({
		type : 'GET',
		url : "./getAlldtLocation",
		data:{meterno:meterno},
		async : false,
		cache : false,
		success : function(response)
		{
			var htmlCircle="";
			var htmlDivision="";
			var htmlSubdivision="";
			var htmlSubstation="";
			var htmlfeeder="";
			var htmldt="";
			if(response!=null)
				{
				htmlCircle+="<option value='"+response[0]+"'>"+response[0]+"</option>"; 
				htmlDivision+="<option value='"+response[1]+"'>"+response[1]+"</option>"; 
				htmlSubdivision+="<option value='"+response[2]+"'>"+response[2]+"</option>";
				htmlSubstation+="<option value='"+response[3]+"'>"+response[3]+' -- '+response[4]+"</option>"; 
				htmlfeeder+="<option value='"+response[5]+"'>"+response[5]+' -- '+response[6]+"</option>"; 
				htmldt+="<option value='"+response[7]+"'>"+response[7]+' -- '+response[8]+"</option>"; 
				$("#circleId").empty();
				$("#circleId").append(htmlCircle);
				$("#divisionId").empty();
				$("#divisionId").append(htmlDivision);
				$("#sdonameId").empty();
				$("#sdonameId").append(htmlSubdivision);
				$("#substationId").empty();
				$("#substationId").append(htmlSubstation);
				$("#feederId").empty();
				$("#feederId").append(htmlfeeder);
				$("#dtId").empty();
				$("#dtId").append(htmldt);
				}
			
		}
	});
}

function checkMeterNoInMasterMain(meterno)
{
	$.ajax(
  			{
  					type : "GET",
  					url : "./checkMeterNoInMasterMain/" + meterno,
  					dataType : "json",
  					success : function(response)
  					{
  	  					 if(response==null){
  	  						
  	  	  					}
  	  					 else{
								bootbox.alert("Meter Number " +meterno+ " Already Installed");
	  							$("#meterno").val("");
	  							return false;
  	  	  					 
  	  	  					 }
  	  				}
				
  			});

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
				if(response.length>0){
					bootbox.alert("Meter Number " +meterno+ " Already Assigned to Consumer");
						$("#meterno").val("");
						return false;
					}
			}
		
		});
}

/* $(function () {
    $('#bStartDate').datetimepicker({  
        minDate:new Date()
     });
}); */
</script>
