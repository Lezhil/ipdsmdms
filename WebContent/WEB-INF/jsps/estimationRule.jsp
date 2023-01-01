<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="<c:url value='/resources/assets/global/css/bootstrap-switch.min.css'/>"  rel="stylesheet" type="text/css"/>
<script>
	$(".page-content").ready(function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						formreset();
						getEstimationRule();
						$('#MDMSideBarContents,#dataValidId,#estimationRule').addClass('start active ,selected');
						$("#MDASSideBarContents,#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#360d-view,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
					});

</script>
<script type="text/javascript">

var thresoldregex= /^[0-9]*$/;

function getEstimationRule(){
	$('#imageee').show();
	$
	.ajax({
		url : './getEstimationRule',
		type : 'POST',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			if(response!=null && response.length>0 )
			{
		     var html="";
			  for (var i = 0; i < response.length; i++) 
			  {
				  var resp=response[i];
				  html+="<tr>"+
				  "<td>"+resp.eruleid+" </td>"+
				  "<td>"+resp.erulename+" </td>"+
				  "<td>"+((resp.condtion==null)?"":resp.condtion)+" </td>"+
				  "<td>"+((resp.condval==null)?"":resp.condval)+" </td>"+
				  "<td>"+((resp.data_type==null)?"":resp.data_type)+" </td>"+
				  "<td>"+((resp.parameter==null)?"":resp.parameter)+" </td>"+
				  "<td>"+(resp.is_active==false?"No":"Yes")+" </td>"+
				  "<td>"+((resp.entry_date==null)?"":new Date(Number(resp.entry_date)).toLocaleString())+" </td>"+
				  "<td>"+((resp.entry_by==null)?"":resp.entry_by)+" </td>"+
				  "<td>"+((resp.update_date==null)?"":new Date(Number(resp.update_date)).toLocaleString())+" </td>"+
				  "<td>"+((resp.update_by==null)?"":resp.update_by)+" </td>"+
				  "<td>"+"<button  onclick='editESTRule(\""+resp.eruleid+"\",\""+ resp.erulename + "\",\""+resp.is_active+"\",\""+((resp.condtion==null)?"":resp.condtion)+"\",\""+((resp.condval==null)?"":resp.condval)+"\",\""+((resp.data_type==null)?"":resp.data_type)+"\",\""+((resp.parameter==null)?"":resp.parameter)+"\")' class='btn green' data-toggle='modal'  data-target='#stack2'>Edit</button>"+"</td>"+
				  "</tr>";					
			   }                                
			  $('#sample_1').dataTable().fnClearTable();
			  $("#updateMaster").html(html);
			  loadSearchAndFilter('sample_1'); 
			}
		}
	});
	
}

function editESTRule(ruleId,ruleName,isActive,condtion,condval,data_type,parameter)
{
	 $("#ueruleid").val(ruleId);
	 $("#uerulename").val(ruleName);
	 $("#ucondname").val(condtion);
	 $("#ucondval").val(condval);
	 $("#udata_type").val(data_type);
	 $("#uparameter").val(parameter);

 if (isActive == 'true') {
	    $('input[name="isActive_radio"]').prop('checked', false);
	 	$('input[name="isActive_radio"]').click();
	} else if(isActive == 'false'){
		$('input[name="isActive_radio"]').prop('checked', true);
	 	$('input[name="isActive_radio"]').click();
	}
}

function getESTlatestRuleId() {
	$
			.ajax({
				url : './getESTlatestRuleId',
				type : 'POST',
				success : function(response) {
					//alert(response);
					 $("#eruleid").val(response);
				}
			});
}

function formreset(){
    	$('#eruleid').val('');
        $('#erulename').val(''); 
        $("#ucondname").val('');
        $("#ucondval").val('');
		$("#udata_type").val();
		$("#uparameter").val('');    
 }

 function UpdateESTRule(){
    	var ruleid = $("#ueruleid").val();
		var rulename = $("#uerulename").val();
		var condname=$("#ucondname").val();
		var condval= $("#ucondval").val();
		var data_type= $("#udata_type").val();
		var parameter= $("#uparameter").val();
		
			if (rulename == '') {
				bootbox.alert("Please Enter Rulename");
				return false;
			}
			$("#editESTRule").submit();
   }	
    
function AddESTRule() {
		/*./addRuledetails  */
		var rulename = $("#erulename").val();
		if (rulename == '') {
			bootbox.alert("Please Enter Rulename");
			return false;
		}
		$("#addESTRule").submit();
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
						<i class="fa fa-edit"></i>Define Estimation Rule
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>

				<div class="portlet-body">

<!-- 					<div class="btn-group">
						<button class="btn green" onclick="getESTlatestRuleId()" data-target="#stack1" data-toggle="modal">
							Add Rule <i class="fa fa-plus"></i>
						</button>
					</div> -->

				<!-- 	<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<br> -->
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li>
								<a href="#" id="excelExport" onclick="tableToExcel('sample_1', 'EST_Rules')">Export to Excel</a>
								</li>
							</ul>
						</div>
					</div>

					<div id="stack1" class="modal fade" role="dialog"
						aria-labelledby="myModalLabel10" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-body">
									<form action="./addESTRule" id="addESTRule" method="post">
										<div class="row"></div>

										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Rule ID</label> 
													<span style="color: red" class="required">*</span>
													<input
														type="text" id="eruleid"
														class="form-control placeholder-no-fix"
														placeholder="Enter Rule ID" readonly="readonly" name="ERLID" maxlength="12"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Estimation Rule Name</label>
													<span style="color: red" class="required">*</span>
													 <input
														type="text" id="erulename"
														class="form-control placeholder-no-fix"
														placeholder="Enter Rule Name" name="ERLNAME" maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Data Type</label>
													 <select class="form-control" name="data_type" id="data_type">
														    <option value=""></option>
															<option value="Instantaneous">Instantaneous</option>
															<option value="Load_Survey">Load Survey</option>
													</select>
												</div>
											</div>
											
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Parameter</label>
													 <select class="form-control" name="parameter" id="parameter">
													 		<option value=""></option>
															<option value="Energy">Energy</option>
															<option value="Voltage">Voltage</option>
													</select>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Condition</label>
													 <input
														type="text" id="conname"
														class="form-control placeholder-no-fix"
														placeholder="Enter Condition Name" name="CONNAME" maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Condition Value</label>
													 <input
														type="text" id="conval"
														class="form-control placeholder-no-fix"
														placeholder="Enter Condition Value" name="CONVAL" maxlength="50"></input>
												</div>
											</div>
											
										</div>

										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="addESTRule"
												type="button" onclick="AddESTRule()">Add</button>
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
									<form action="./editESTRule" id="editESTRule" method="post">
										<div class="row"></div>

										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Rule ID</label> <input
														type="text" id="ueruleid"
														class="form-control placeholder-no-fix"
														name="UERLID" maxlength="12"  readonly="readonly"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Estimation Rule Name</label> <input
														type="text" id="uerulename"
														class="form-control placeholder-no-fix"
														name="UERLNAME" readonly="readonly" maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Data Type</label>
													 <input type="text" id="udata_type"
														class="form-control placeholder-no-fix"
														name="UDATA_TYPE" readonly="readonly" maxlength="50"> </input>
													 <!-- <select class="form-control" name="udata_type" id="udata_type">
													 		<option value=""></option>
															<option value="Instantaneous">Instantaneous</option>
															<option value="Load_Survey">Load Survey</option>
													</select> -->
												</div>
											</div>
											
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Parameter</label>
													<input type="text" id="uparameter"
														class="form-control placeholder-no-fix"
														name="UPARAMETER" readonly="readonly" maxlength="50"></input>
													 <!-- <select class="form-control" name="uparameter" id="uparameter">
													 		<option value=""></option>
															<option value="Energy">Energy</option>
															<option value="Voltage">Voltage</option>
													</select> -->
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Condition</label>
													 <input
														type="text" id="ucondname"
														class="form-control placeholder-no-fix"
														placeholder="Enter Cond. Name" name="UCONDNAME" maxlength="50"></input>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label">Condition Value</label>
													 <input
														type="text" id="ucondval"
														class="form-control placeholder-no-fix"
														placeholder="Enter Cond. Value" name="UCONDVAL" maxlength="50"></input>
												</div>
											</div>
											
										</div>
									
										<div class="row">					
											<div class="col-md-4">
												<div class="form-group">									
														<label>Is Active</label><span style="color: red" class="required">*</span>
														<div class="make-switch" style="position:relative;" data-on="success" data-off="warning">
														<input type="checkbox" name="isActive_radio" id="isActive_radio" class="toggle"/>
													    </div>	
														
														<!-- <label><input type="radio"  id="Active_radio" name="isActive_radio" value="True">Yes</label>
														<label><input type="radio"  id="DeActive_radio"  name="isActive_radio" value="False">No</label> -->
												</div>
											</div>
										</div>
									
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-left" id="updateAddOption"
												type="button" onclick="UpdateESTRule()">Modify</button>
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
								<th>Estimation Rule Name</th>
								<th>Condition</th>
								<th>Condition Value</th>
								<th>Data type</th>
								<th>Parameter</th>
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
/* .select2-choice {
	width: 180px;
} */
</style>