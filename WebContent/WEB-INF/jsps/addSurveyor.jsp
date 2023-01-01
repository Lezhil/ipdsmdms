<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
$(".page-content").ready(function()
    	   {  
		
	App.init();
	TableEditable.init();
	FormComponents.init();
	surveyorData();
	
    $('#SurveyAndInstallation,#addSurveyorId').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
	$("#addSurveyorDetailsFormId").clear();
	
	});
</script>
<div class="page-content">
 <c:if test = "${not empty result}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${result}</span>
			       </div>
	                </c:if>
	                
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Add Surveyor
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
								<div class="btn-group">
								<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" value="addSingle">
									      Add Surveyor <i class="fa fa-plus"></i>
								 </button>
									  </div>
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>Edit</th>
								<th>Surveyor Name</th>
<th>Identity Detail</th>
<th>Surveyor Status </th>
<th>Entry By</th>
<th>Entry Date</th>
<th>Update By</th>
<th>Update Date</th>
							</tr>
						</thead>
							<tbody id="surveyorDetailsId" >
								
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					</div>


<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<span id="addMeterStackId"
						style="color: #474627; font-weight: bold;">Add Surveyor</span>
				</h4>
			</div>
			<span style="color: red; font-size: 16px;" id="accountNotExistMsg"></span>
			<div class="modal-body">
				<form action="" method="" id="addSurveyorDetailsFormId">
					
					<div class="row">
						<div class="col-md-6">
							<label>Surveyor Name</label><span style="color: red">*</span> <input
								style="width: 100%; float: left;" type="text"
								name="surveyorNameId" id="surveyorNameId" onkeyup="surveyorExistOrNot()" class="form-control"
								placeholder="Enter Name"></input><br><span id="surveyorExistOrNotId" class="text-danger"></span>
						</div>
						<div class="col-md-6">
							<label>Surveyor Status</label><span style="color: red">*</span>
							<select   name="surveyorStatusId" id="surveyorStatusId" class="form-control" >
													<option value="0">Select </option>
													<option value="ACTIVE">ACTIVE</option>
													<option value="INACTIVE">INACTIVE</option>
													</select>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Surveyor Mobile/IMEI Number</label><!-- <span style="color: red">*</span> -->
							<input style="width: 100%; float: left;" type="text"
								name="surveyorIdentityId" id="surveyorIdentityId" class="form-control"
								placeholder="9999999999"></input>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn blue pull-right" id="addSurveyor" type="button"  onclick="validateAndInsertion()">ADD</button>  
						<button type="button" data-dismiss="modal" class="btn">Cancel</button>
						</div>	
				</form>
			</div>
		</div>
	</div>
</div>
<div id="stack2" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">
					<span id="addMeterStackId"
						style="color: #474627; font-weight: bold;">Edit Surveyor Data</span>
				</h4>
			</div>
			<span style="color: red; font-size: 16px;" id="accountNotExistMsg"></span>
			<div class="modal-body">
				<form action="" method="" id="addSurveyorDetailsFormId">
					
					<div class="row">
						<div class="col-md-6">
							<label>Surveyor Name</label><span style="color: red">*</span> <input
								style="width: 100%; float: left;" type="text"
								name="eSurveyorNameId" id="eSurveyorNameId" onkeyup="eSurveyorExistOrNot()" class="form-control"
								placeholder="Enter Name"></input><br><span id="eSurveyorExistOrNotId" class="text-danger"></span>
								
						</div>
						<div class="col-md-6">
							<label>Surveyor Status</label><span style="color: red">*</span>
							<select   name="eSurveyorStatusId" id="eSurveyorStatusId" class="form-control" >
													<option value="0">Select </option>
													<option value="ACTIVE">ACTIVE</option>
													<option value="INACTIVE">INACTIVE</option>
													</select>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-6">
							<label>Surveyor Mobile/IMEI Number</label><!-- <span style="color: red">*</span> -->
							<input style="width: 100%; float: left;" type="text"
								name="eSurveyorIdentityId" id="eSurveyorIdentityId" class="form-control"
								placeholder="9999999999"></input>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn blue pull-right" id="editSurveyor" type="button"  onclick="validateAndUpdate()">Update</button>  
						<button type="button" data-dismiss="modal" class="btn">Cancel</button>
						</div>	
				</form>
			</div>
		</div>
	</div>
</div>
<script>
var sname='';
var ename='';
var eid='';
function validateAndInsertion(){

	var name=$("#surveyorNameId").val();
	var status=$("#surveyorStatusId").val();
	var identity=$("#surveyorIdentityId").val();

	if(name=="")
		{
			bootbox.alert("Please enter Surveyor Name");
			return false
		}
	
	if(sname==true){
		bootbox.alert("Surveyor Name is exist.Please enter another Surveyor Name");
		return false
		}
	if(status=="0")
	{
		bootbox.alert("Please Select Surveyor Status");
		return false
	}

	

	/* $("#addMeterDetailsFormId").attr('action','./addBatchMeterInventoryDetails').submit();
	$("#addSurveyorDetailsFormId").attr('action','./addNewSurveyor').submit(); */

	 $.ajax({
        url:"./addNewSurveyor",
        type:"GET",
        data:{
        	surveyorNameId:name,
        	surveyorStatusId:status,
        	surveyorIdentityId:identity
            } ,
        success:function(res){
            bootbox.alert("successfully Saved");
            $("#surveyorNameId").val('');
            $("#surveyorIdentityId").val('');
            $('#stack1').modal('hide');
            surveyorData();
            }
		
		});
	 
}


function validateAndUpdate(){

	var name=$("#eSurveyorNameId").val();
	var status=$("#eSurveyorStatusId").val();
	var identity=$("#eSurveyorIdentityId").val();

	if(name=="")
		{
			bootbox.alert("Please enter Surveyor Name");
			return false
		}
	if(name!=ename){
		if(sname==true){
			bootbox.alert("Surveyor Name is exist.Please enter another Surveyor Name");
			return false
			}
		}
	
	if(status=="0")
	{
		bootbox.alert("Please Select Surveyor Status");
		return false
	}

	


	 $.ajax({
        url:"./updateSurveyor",
        type:"GET",
        data:{
        	surveyorNameId:name,
        	surveyorStatusId:status,
        	surveyorIdentityId:identity,
        	id:eid
            } ,
        success:function(res){
            if(res=='SUCC'){
            	 bootbox.alert("successfully Updated");
                 $("#eSurveyorNameId").val('');
                 $("#eSurveyorIdentityId").val('');
                 $('#stack2').modal('hide');
                 surveyorData();
                }
            else{
            	 bootbox.alert("Modified Data is Not Updated.Please try again...");
                }
           
            }
		
		});
	 
}
function surveyorData(){
	$.ajax({
		url:"./surveyorList",
		type:"GET",
		success:function(res){
			var html="";
			$.each(res,function(k,v){
				html+='<tr>';
				html+='<td id='+v.id+' data-target="#stack2" data-toggle="modal" onclick="surveyorDataEdit(this.id)" ><a>Edit</a></td>';
				html+='<td>'+v.surveyorname+'</td>';
				html+='<td>'+v.identity+'</td>';
				html+='<td>'+v.surveyorstatus+'</td>';
				if(v.entryby==null){
					html+='<td></td>';
					}
				else{
					html+='<td>'+v.entryby+'</td>';
					}
				if(v.entrydate==null){
					html+='<td></td>';
				}
			else{
				html+='<td>'+moment(v.entrydate).format("YYYY-MM-DD HH:mm:ss")+'</td>';
				}
				if(v.updateby==null){
					html+='<td></td>';
				}
			else{html+='<td>'+v.updateby+'</td>';
				}
				if(v.updateddate==null){
					html+='<td></td>';
				}
			else{html+='<td>'+moment(v.updateddate).format("YYYY-MM-DD HH:mm:ss")+'</td>';
				}
				html+='</tr>';
				});
            $("#surveyorDetailsId").html(html);
            loadSearchAndFilter('sample_1');
			}
		});
}
function surveyorExistOrNot(){
	var sn=$("#surveyorNameId").val();
	if(sn==''){
		$("#surveyorExistOrNotId").html('');
		}
	
	$.ajax({
      url:"./surveyorExistOrNot/"+sn,
      type:"GET",
      success:function(res){
          if(res==true){
        	  $("#surveyorExistOrNotId").html(sn+" name is exist. Please enter another name");
        	  sname=res;
              }
          else{
        	  $("#surveyorExistOrNotId").html('');
        	  sname=res;
              }
          }
		});
}
function eSurveyorExistOrNot(){
	var sn=$("#eSurveyorNameId").val();
	if(sn==''){
		$("#eSurveyorExistOrNotId").html('');
		}
	
	$.ajax({
      url:"./surveyorExistOrNot/"+sn,
      type:"GET",
      success:function(res){
          if(sn!=ename){
        	  if(res==true){
            	  $("#eSurveyorExistOrNotId").html(sn+" name is exist. Please enter another name");
            	  sname=res;
                  }
        	  else{
            	  $("#eSurveyorExistOrNotId").html('');
            	  sname=res;
                  }
              }
          
         
          }
		});
}
function surveyorDataEdit(id){
    $.ajax({
         url:"./surveyorDetails/"+id,
         type:"GET",
         success:function(res){
var v=res[0];
ename=v.surveyorname;
eid=v.id;
$("#eSurveyorNameId").val(v.surveyorname);
$("#eSurveyorIdentityId").val(v.identity);
$("#eSurveyorStatusId").html('');
if(v.surveyorstatus=='ACTIVE'){
	$("#eSurveyorStatusId").html('<option value="ACTIVE">ACTIVE</option><option value="INACTIVE">INACTIVE</option>');
}
else{
	$("#eSurveyorStatusId").html('<option value="INACTIVE">INACTIVE</option><option value="ACTIVE">ACTIVE</option>');
}

             }
        });
	
	
}

</script>
