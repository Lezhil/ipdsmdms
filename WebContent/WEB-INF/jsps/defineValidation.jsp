<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="<c:url value='/resources/assets/global/css/bootstrap-switch.min.css'/>"  rel="stylesheet" type="text/css"/>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%if(session.getAttribute("username")==null || session.getAttribute("username")==null){ %>
		<script>
			window.location.href="./?sessionVal=expired";
		</script>
	<%} 
	
%> 
<script>
	$(".page-content").ready(function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						formreset();
				   		$(".make-switch").change(function() {
						    if ($("input[name='v_trigger_radio']").is(":checked")) {
						    	$("#activeRule").show();
						    } else {
						    	$("#activeRule").hide();
						    }
						    if ($("input[name='uv_trigger_radio']").is(":checked")) {
						    	$("#uactiveRule").show();
						    } else {
						    	$("#uactiveRule").hide();
						    }
						});
						getVeeRuleDetails();	
						$('#MDMSideBarContents,#dataValidId,#defineValidation').addClass('start active ,selected');
						$("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
					});

</script>
<script type="text/javascript">
var thresoldregex= /^[0-9]*$/;

function getEstimationActiveRule(){
	$('#getActiverule').find('option').remove();
	$('#getActiverule').append($('<option>', {
		value : "",
		text : "Please Select Estimation Rule"
	}));
	$('#EstActiveRule').find('option').remove();
	$('#EstActiveRule').append($('<option>', {
		value : "",
		text : "Please Select Estimation Rule"
	}));
	$
	.ajax({
		url : './getEstimationActiveRule',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		success : function(response) {
		 for(var i=0;i<=response.length;i++){
				 $('#getActiverule').append($('<option>', {
						value : response[i].eruleid,
						text : response[i].erulename
					}));				
				 $('#EstActiveRule').append($('<option>', {
						value : response[i].eruleid,
						text : response[i].erulename
					}));
				 }		 
		}
	});	
}

function getlatestRuleId() {
	getEstimationActiveRule();
	$.ajax({
			url : './getlatestRuleId',
			type : 'GET',
			success : function(response) {
				 $("#ruleidn").val(response);
			}
		});
}

function editVEERule(ruleName,ruleId,isActive,lwthrlimit,hgthrlimit,universal_v_rule,auto_v_rule,alarm_v_rule,trigger_v_rule,erulename){

	 formreset();
	 getEstimationActiveRule();
	 $("#ruleid").val(ruleId);
	 $("#urulename").val(ruleName);
	
	 if(ruleId=="VEE01"){
		 $("#ulwthrlimit").val("NA").attr('readonly', true);
		 $("#uhgthrlimit").val(hgthrlimit);		
			
		} 
	 else if(ruleId=="VEE02"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val("NA").attr('readonly', true);			
			
		}
	 else if(ruleId=="VEE03"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val("NA").attr('readonly', true);			
			
		}
	 else if(ruleId=="VEE04"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			$("#uhgthrlimit").val("NA").attr('readonly', true);		
			
		}
	 else if(ruleId=="VEE05"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			$("#uhgthrlimit").val("NA").attr('readonly', true);			
			
		}
	 else if(ruleId=="VEE06"){
			 $("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val("NA").attr('readonly', true);		
			
		}
	 else if(ruleId=="VEE07"){
			 $("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val("NA").attr('readonly', true);	
			
		}
	 else if(ruleId=="VEE08"){
			 $("#ulwthrlimit").val(lwthrlimit);
			 $("#uhgthrlimit").val(hgthrlimit);		
			
		}
	 else if(ruleId=="VEE09"){
			 $("#ulwthrlimit").val(lwthrlimit);
			 $("#uhgthrlimit").val(hgthrlimit);		
			
		}
	 else if(ruleId=="VEE10"){
			 $("#ulwthrlimit").val(lwthrlimit);
			 $("#uhgthrlimit").val(hgthrlimit);		
			
		}
	 else if(ruleId=="VEE11"){
			 $("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val(hgthrlimit);			
			
		}
	 else if(ruleId=="VEE12"){
			 $("#ulwthrlimit").val(lwthrlimit);
			 $("#uhgthrlimit").val("NA").attr('readonly', true);		
			
		}
	 else if(ruleId=="VEE13"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			$("#uhgthrlimit").val("NA").attr('readonly', true);		
			
		}
	 else if(ruleId=="VEE14"){
			 $("#ulwthrlimit").val("NA").attr('readonly', true);
			 $("#uhgthrlimit").val(hgthrlimit);			
			
		}
	 else if(ruleId=="VEE15"){
			$("#ulwthrlimit").val("NA").attr('readonly', true);
			$("#uhgthrlimit").val("NA").attr('readonly', true);			
			
		} 
	 else if(ruleId=="VEE19"){
		 $("#ulwthrlimit").val(lwthrlimit);
		 $("#uhgthrlimit").val(hgthrlimit);		
		
	}else{
			 $("#ulwthrlimit").val(lwthrlimit);
			 $("#uhgthrlimit").val(hgthrlimit);
		}
	
 
	if (universal_v_rule == 'true') {
		$('input[name="uv_uni_radio"]').prop('checked', false);
		$('input[name="uv_uni_radio"]').click();
	} 
	if (universal_v_rule == 'false') {
		$('input[name="uv_uni_radio"]').prop('checked', true);
		$('input[name="uv_uni_radio"]').click();
	}
	
	if (auto_v_rule == 'true') {
	   $('input[name="uv_auto_radio"]').prop('checked', false);
	   $('input[name="uv_auto_radio"]').click();
	} 
	if (auto_v_rule == 'false'){
		$('input[name="uv_auto_radio"]').prop('checked', true);
		$('input[name="uv_auto_radio"]').click();
	}
	
	if (alarm_v_rule == 'true') {
		$('input[name="uv_alarm_radio"]').prop('checked', false);
		$('input[name="uv_alarm_radio"]').click();
	} 
	if (alarm_v_rule == 'false') {
		$('input[name="uv_alarm_radio"]').prop('checked', true);
		$('input[name="uv_alarm_radio"]').click();
	}
	
	if (trigger_v_rule == 'true') {
		$('input[name="uv_trigger_radio"]').prop('checked', false);
		$('input[name="uv_trigger_radio"]').click();
	} 
	if (trigger_v_rule == 'false') {
		$('input[name="uv_trigger_radio"]').prop('checked', true);
		$('input[name="uv_trigger_radio"]').click();
	}
	
	if (isActive == 'true') {
		$('input[name="isActive_radio"]').prop('checked', false);
		$('input[name="isActive_radio"]').click();
		setTimeout(function(){
			 $("#EstActiveRule").val(erulename).trigger('change');	
		  },200);
		 
		
	
		
	} 
	if(isActive == 'false'){
		$('input[name="isActive_radio"]').prop('checked', true);
		$('input[name="isActive_radio"]').click();
	}
	
	
}

function getVeeRuleDetails()
{						 
	$.ajax({
		url : "./getAllVeeRuleDetails",
	    	type:'POST',
	    	success:function(response)
	    	{
		    	
	    		if(response!=null && response.length>0 )
				{
			     var html="";
				  for (var i = 0; i < response.length; i++) 
				  {
					  var resp=response[i];
					  html+="<tr>"+
					  "<td>"+resp.ruleid+" </td>"+
					  "<td>"+resp.rulename+" </td>";

					if(resp.ruleid=="VEE01"){
						html += "<td>"+"NA"+" </td>"+
						        "<td>"+resp.hgthrlimit+"(Mins)"+" </td>"		
						
					} 
					else if(resp.ruleid=="VEE02"){
						html += "<td>"+"NA"+" </td>"+
						        "<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE03"){
						html += "<td>"+"NA"+" </td>"+
				       			 "<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE04"){
						html += "<td>"+"NA"+" </td>"+
				        		"<td>"+"NA"+" </td>"	
						
					}
					else if(resp.ruleid=="VEE05"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else if(resp.ruleid=="VEE06"){
						html += "<td>"+"NA"+" </td>"+
						        "<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE07"){
						html += "<td>"+"NA"+" </td>"+
						        "<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE08"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else if(resp.ruleid=="VEE09"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else if(resp.ruleid=="VEE10"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else if(resp.ruleid=="VEE11"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else if(resp.ruleid=="VEE12"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						 "<td>"+resp.hgthrlimit+""+" </td>"	
						
					}
					else if(resp.ruleid=="VEE13"){
						html += "<td>"+"NA"+" </td>"+
				        		"<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE14"){
						html += "<td>"+"NA"+" </td>"+
						        "<td>"+resp.hgthrlimit+"(%)"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE15"){
						html += "<td>"+"NA"+" </td>"+
				        		"<td>"+"NA"+" </td>"		
						
					}
					else if(resp.ruleid=="VEE19"){
						html += "<td>"+resp.lwthrlimit+""+" </td>"+
						        "<td>"+resp.hgthrlimit+""+" </td>"		
						
					}
					else{
						html += "<td>"+resp.lwthrlimit+" </td>"+
		        		       "<td>"+resp.hgthrlimit+" </td>"	
						}	
					html += "<td>"+(resp.universal_v_rule==false?"No":"Yes")+" </td>"+
					  "<td>"+(resp.auto_v_rule==false?"No":"Yes")+" </td>"+
					  "<td>"+(resp.alarm_v_rule==false?"No":"Yes")+" </td>"+
					  "<td>"+(resp.trigger_v_rule==false?"No":"Yes")+" </td>"+
					  "<td>"+((resp.erulename==null)?"":resp.erulename)+" </td>"+
					  "<td>"+(resp.is_active==false?"No":"Yes")+" </td>"+
					  "<td>"+((resp.entry_date==null)?"":new Date(Number(resp.entry_date)).toLocaleString())+" </td>"+
					  "<td>"+((resp.entry_by==null)?"":resp.entry_by)+" </td>"+
					  "<td>"+((resp.update_date==null)?"":new Date(Number(resp.update_date)).toLocaleString())+" </td>"+
					  "<td>"+((resp.update_by==null)?"":resp.update_by)+" </td>"+
					  "<td>"+"<button  onclick='editVEERule(\""+resp.rulename+"\",\""+ resp.ruleid + "\",\""+resp.is_active+"\",\""+resp.lwthrlimit+"\",\""+resp.hgthrlimit+"\",\""+resp.universal_v_rule+"\",\""+resp.auto_v_rule+"\",\""+resp.alarm_v_rule+"\",\""+resp.trigger_v_rule+"\",\""+resp.erulename+"\")' class='btn green' data-toggle='modal'  data-target='#stack2'>Edit</button>"+"</td>"+
					  "</tr>";					
				   }                                
				  $('#sample_1').dataTable().fnClearTable();
				  $("#updateMaster").html(html);
				  loadSearchAndFilter('sample_1'); 
				}
	    	}
		});
}


function formreset(){
	
    $('#ruleidn').val('');
    $('#rulename').val('');
    $('#erulename').val('');
    $('#lwthrlimit').val('');
    $('#hgthrlimit').val('');
    $('input[name="v_uni_radio"]').prop('checked', true);
    $('input[name="v_auto_radio"]').prop('checked', true);
    $('input[name="v_alarm_radio"]').prop('checked', true);
    $('input[name="v_trigger_radio"]').prop('checked', true);      
}

function UpdateAddRule(){
    	
    	var ruleid = $("#ruleid").val();
		var rulename = $("#urulename").val();
		var lwthrlimit = $("#ulwthrlimit").val();
		var hgthrlimit = $("#uhgthrlimit").val();
		var erulename = $("#EstActiveRule").val();
		var rule="VEE01 VEE02 VEE03 VEE04 VEE05 VEE06 VEE07 VEE09 VEE10 VEE11 VEE12 VEE13 VEE14 VEE19";

		/* alert(ruleid);
		alert("lwthrlimit="+lwthrlimit);
		alert("hgthrlimit="+hgthrlimit); */
			if (rulename == '') {
				bootbox.alert("Please Enter Rulename");
				return false;
			}
			if(!rule.includes(ruleid)){
				if (!lwthrlimit =='' || !hgthrlimit == '') {
				 	if (lwthrlimit >= hgthrlimit) {
					bootbox.alert("Please Enter Lower Thresold Limit less than Higher Thresold Limit");
					return false;
					}	
				}
			}

		/* 	if (!lwthrlimit.match(thresoldregex) || lwthrlimit == '') {
				bootbox.alert("Please Enter valid Lower Thresold Limit(Digit Only)");
				return false;
			}
			if (hgthrlimit == '' ||  !hgthrlimit.match(thresoldregex)) {
				bootbox.alert("Please Enter Higher Thresold Limit(Digit Only)");
				return false;
			} */

			if ($("input[name='uv_trigger_radio']").is(":checked") == true)
			{
				if (erulename == '') {
					bootbox.alert("Please select Estimation Rule");
					return false;
				}
			}
	$("#editVEERule").submit();
}

function validationAddRule() {
	/*./addRuledetails  */
	var rulename = $("#rulename").val();
	var lwthrlimit = $("#lwthrlimit").val();
	var hgthrlimit = $("#hgthrlimit").val();
	var eruleid = $("#getActiverule").val();

	if (rulename == '') {
		bootbox.alert("Please Enter Rulename");
		return false;
	}
    if (!lwthrlimit =='' || !hgthrlimit == '') {
	 	if (lwthrlimit >= hgthrlimit) {
		bootbox.alert("Please Enter Lower Thresold Limit less than Higher Thresold Limit");
		return false;
		}	
	}
/* 	if (hgthrlimit == '' ||  !hgthrlimit.match(thresoldregex)) {
		bootbox.alert("Please Enter Higher Thresold Limit(Digit Only)");
		return false;
	}  */
	if ($("input[name='v_trigger_radio']").is(":checked") == true){
		if (eruleid == '') {
			bootbox.alert("Please select Estimation Rule");
			return false;
		}
	}
	$("#addVEERule").submit();
}
</script>


<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${results ne 'notDisplay'}">
				<c:choose>
				<c:when test="${alert_type eq 'success'}">
					<div class="alert alert-success display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: green">${results}</span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger display-show">
						<button class="close" data-close="alert"></button>
						<span style="color: red">${results}</span>
					</div>
				</c:otherwise>
				</c:choose>				
			</c:if>
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Define VEE Validation
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<c:if test = "${userType eq 'ADMIN'}">
					<div class="btn-group">
						<button class="btn green" onclick="getlatestRuleId()" data-target="#stack1" data-toggle="modal">
							Add Rule <i class="fa fa-plus"></i>
						</button>
					</div>
				</c:if>
					<br>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<br>
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample_1', 'VEE_Rules')">Export to Excel</a>
								</li>
							</ul>
						</div>
					</div>

					<div id="stack1" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-body">
									<form action="./addVEERule" id="addVEERule" method="post">
										<div class="row"></div>

										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Rule ID</label> 
													<span style="color: red" class="required">*</span>
													<input
														type="text" id="ruleidn"
														class="form-control placeholder-no-fix"
														placeholder="Enter Rule ID" readonly="readonly" name="RLID" maxlength="12"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Rule Name</label>
													<span style="color: red" class="required">*</span>
													 <input
														type="text" id="rulename"
														class="form-control placeholder-no-fix"
														placeholder="Enter Rule Name" name="RLNAME" maxlength="50"></input>
												</div>
											</div>
											
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Lower Threshold Limit</label>
													<!-- <span style="color: red" class="required">*</span> -->
													<input type="text" id="lwthrlimit"
														class="form-control placeholder-no-fix"
														placeholder="Enter Lower Threshold Limit" name="LWTLMT"
														maxlength="12"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">High Threshold Limit</label> 
													<!-- <span style="color: red" class="required">*</span>	 -->	
													<input
														type="text" id="hgthrlimit"
														class="form-control placeholder-no-fix"
														placeholder="Enter High Threshold Limit" name="HGTLMT"
														maxlength="12"></input>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">									
														<label>Apply Universally : </label>
													
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 11%;">
														<input type="checkbox" id="v_uni_radio" name="v_uni_radio" checked="checked" class="toggle" />
													</div>																	
														<!-- <span style="color: red" class="required">* </span>
														<div class="make-switch" data-on-label="&nbsp;Enable&nbsp;" data-off-label="&nbsp;Disable&nbsp;" data-size="mini">
														<input type="checkbox" name="v_uni_radio" checked class="toggle"/>
													    </div> -->						    						    
														<!-- <br>
														<label><input type="radio" name="v_uni_radio" value="True">Yes</label>
														<label><input type="radio" name="v_uni_radio"  value="False">No</label> -->
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label>Auto apply : </label> 			
													<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 29%;">
														<input type="checkbox" name="v_auto_radio" id="v_auto_radio" checked="checked" class="toggle" />
													</div>
													<!-- <span
														style="color: red" class="required">*</span> <br> <label><input
														type="radio" name="v_auto_radio" value="True">Yes</label>
													<label><input type="radio" name="v_auto_radio"
														value="False">No</label> -->
												</div>
											</div>
											</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">									
														<label>Raise Alarm :</label>
														<div class="make-switch"  data-on="success"
														data-off="warning" style="position:relative; left: 25%;">
														<input type="checkbox" id="v_alarm_radio" name="v_alarm_radio" checked="checked" class="toggle" />
													</div>				
														<!-- <span style="color: red" class="required">*</span><br>
														<label><input type="radio" name="v_alarm_radio" value="True">Yes</label>
														<label><input type="radio" name="v_alarm_radio" value="False">No</label> -->
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>Auto Estimation :</label>
													<div class="make-switch" data-on="success"
														data-off="warning" style="position: relative; left: 15%;">
														<input type="checkbox" id="v_trigger_radio"
															name="v_trigger_radio" checked="checked"  class="toggle" />
													</div>
													<!-- <span style="color: red" class="required">*</span><br>
														<label><input type="radio" name="v_trigger_radio" value="True">Yes</label>
														<label><input type="radio" name="v_trigger_radio" value="False">No</label> -->
												</div>			
											</div>
											</div>
											<div class="row">		
											<div class="col-md-6" id="activeRule">
												<div class="form-group">		
														<select class="form-control select2me input-medium" name="ERLNAME" style="width: 252px;"id="getActiverule">
															<option value="" >Please Select Estimation Rule</option>
		
																<%-- <c:forEach items="${EstimationRule}" var="elements">
																	<option value="${elements.eruleid}">${elements.getErulename()}</option>
																</c:forEach> --%>
													   </select>
													<!-- <label class="control-label">Estimation Rule</label> 
													<input
														type="text" id="erulename"
														class="form-control placeholder-no-fix"
														placeholder="Enter Estimation Rule" name="ERLNAME" maxlength="50"></input> -->
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="addUserOption"
												type="button" onclick="validationAddRule()">Add</button>
										</div>
								</div>
								</form>
							</div>
						</div>
					</div>
					
					
					<div id="stack2" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-body">
									<form action="./editVEERule" id="editVEERule" method="post">
										<div class="row"></div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Rule ID</label> <input
														type="text" id="ruleid"
														class="form-control placeholder-no-fix"
														name="URLID" maxlength="12"  readonly="readonly"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Rule Name</label> <input
														type="text" id="urulename"
														class="form-control placeholder-no-fix"
														name="URLNAME" readonly="readonly" maxlength="50"></input>
												</div>
											</div>											
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Lower Threshold Limit</label>
													<!-- <span style="color: red" class="required">*</span> -->
													<input type="text" id="ulwthrlimit"
														class="form-control placeholder-no-fix"
														placeholder="Enter Lower Threshold Limit" name="ULWTLMT"
														maxlength="12"></input>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">High Threshold Limit</label>
													<!-- <span style="color: red" class="required">*</span> -->
													 <input
														type="text" id="uhgthrlimit"
														class="form-control placeholder-no-fix"
														placeholder="Enter High Threshold Limit" name="UHGTLMT"
														maxlength="12"></input>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
												<label>Apply Universally : </label>		
														<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 12%;">
														<input type="checkbox" id="uv_uni_radio" name="uv_uni_radio" class="toggle" />
													</div>
																					
														<!-- <label>Apply Universally</label><span style="color: red" class="required">*</span><br>
														<label><input type="radio" id="Active_Universally" name="uv_uni_radio" value="True">Yes</label>
														<label><input type="radio" id="DeActive_Universally" name="uv_uni_radio"  value="False">No</label> -->
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">	
												
												<label>Auto apply : </label> 	
													<div class="make-switch" data-on="success"
														data-off="warning" style="position:relative; left: 29%;">
														<input type="checkbox" name="uv_auto_radio" id="uv_auto_radio"  class="toggle" />
													</div>								
														<!-- <label>Auto Apply</label><span style="color: red" class="required">*</span><br>
														<label><input type="radio" id="Active_Auto_Apply" name="uv_auto_radio" value="True">Yes</label>
														<label><input type="radio" id="DeActive_Auto_Apply" name="uv_auto_radio" value="False">No</label> -->
												</div>
											</div>											
										</div>
										<div class="row">										
										<div class="col-md-6">
												<div class="form-group">											
												<label>Raise Alarm :</label>
														<div class="make-switch"  data-on="success"
														data-off="warning" style="position:relative; left: 26%;">
														<input type="checkbox" id="uv_alarm_radio" name="uv_alarm_radio"  class="toggle" />
													</div>																					
														<!-- <label>Raise Alarm</label><span style="color: red" class="required">*</span><br>
														<label><input type="radio" name="uv_alarm_radio" value="True">Yes</label>
														<label><input type="radio" name="uv_alarm_radio" value="False">No</label> -->
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">									
														<label>Is Active</label><span style="color: red" class="required">*</span>
														<div class="make-switch" style="position:relative; left: 35%;" data-on="success" data-off="warning">
														<input type="checkbox" name="isActive_radio" id="isActive_radio" class="toggle"/>
													    </div>	
														
														<!-- <label><input type="radio"  id="Active_radio" name="isActive_radio" value="True">Yes</label>
														<label><input type="radio"  id="DeActive_radio"  name="isActive_radio" value="False">No</label> -->
												</div>
											</div>
										</div>
										<div class="row">
										 <div class="col-md-6">
												<div class="form-group">							
												<label>Auto Estimation :</label>
													<div class="make-switch" data-on="success"
														data-off="warning" style="position: relative; left: 16%;">
														<input type="checkbox" id="uv_trigger_radio"
															name="uv_trigger_radio"  class="toggle" />
													</div>								
														<!-- <label>Auto Trigger Estimation</label><span style="color: red" class="required">*</span><br>
														<label><input type="radio" name="uv_trigger_radio" value="True">Yes</label>
														<label><input type="radio" name="uv_trigger_radio" value="False">No</label> -->
												</div>
										 </div>
										 <div class="col-md-6 form-group" id="uactiveRule">
										 
										 <select class="form-control select2me input-medium" name="UERLNAME" id="EstActiveRule">
											<option value="">Please Select Estimation Rule</option>

											<%-- <c:forEach items="${EstimationRule}" var="elements">
												<option value="${elements.eruleid}">${elements.getErulename()}</option>
											</c:forEach> --%>
										</select>
												<!-- <div class="form-group">
													<label class="control-label">Estimation Rule</label> <input
														type="text" id="uerulename"
														class="form-control placeholder-no-fix"
														placeholder="Enter Estimation Rule" name="UERLNAME" maxlength="50"></input>
												</div> -->
										</div>
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="updateAddOption"
												type="button" onclick="UpdateAddRule()">Modify</button>
									  </div>
									</div>
								</form>
							</div>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Rule ID.</th>
								<th>Rule Name</th>
								<th>Low Threshold</th>
								<th>High Threshold</th>
								<th>Universal</th>
								<th>Auto Apply</th>
								<th>Raise Alarm</th>
								<th>Trigger Auto Estimation</th>
								<th>Estimation Rule</th>
								<th>IsActive</th>
								<th>Entry date</th>
								<th>Entry By</th>
								<th>Updated Date</th>
								<th>Updated By</th>
								<th>Edit</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
s2id_getActiverule{
	width: 252px;
}

/* .select2-choice {
	/* width: 180px; */
	width: 252px;
} */
</style>