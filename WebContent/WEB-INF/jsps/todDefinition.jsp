<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css"> 
	 -->
 <script src="<c:url value='/resources/assets/plugins/moment.min.js'/>"  type="text/javascript" ></script>
 <script src="./resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
 <link href="./resources/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        
       
<script  type="text/javascript">
$(".page-content").ready(function()
   {  
		
	App.init();
	TableEditable.init();
	FormComponents.init();
	loadSearchAndFilter('sample_1');
    $('#MDMSideBarContents,#DPId,#todviewid').addClass('start active ,selected');
 	$("#MDASSideBarContents,#MDMSideBarContents#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
 	//$('#startTimeHH').attr('readonly', true);
	//$('#startTimeMM').attr('readonly', true);
	});
</script>
<div class="page-content">
 <c:if test = "${not empty result}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:green" >${result}</span>
			       </div>
	                </c:if>
	                
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>TOD Definition
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
								<div class="btn-group">
										<button class="btn green" data-target="#stack1" data-toggle="modal"  id="addData" value="addSingle" onclick="return clearForm();">
									      Add TOD <i class="fa fa-plus"></i>
									    </button>
									    &nbsp;&nbsp;&nbsp;
								         <br></br>
								         </div>
					<!-- <table style="width: 25%">
						<tbody>
							<tr>
								<td><input class="form-control input-medium"
									placeholder="Enter TOD No" name="meterNumId" autocomplete="off"
									id="meterNumId" /></td>

								<td><button type="button" id="dataview" class="btn yellow"
										 formmethod="post">
										<b>View</b>
									</button></td>
							</tr>
						</tbody>
					</table> -->
								         
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
								
								<th>TOD No</th>
								<th>Start Time</th>
								<th>End Time</th>
								<th>Entry By</th>
								<th>Entry Date</th>
								<th>Edit</th> 
								<th>Delete</th>
								
							</tr>
						</thead>
							<tbody id="meterDetailsId" >
									<c:set var="count" value="1" scope="page" />
								<c:forEach var="element" items="${tods}">
									<tr >
										<%-- <td><a href="#" onclick="editMeterDetails(this.id,${element.id})" id="editData${element.id}">Edit</a></td> --%>
										
										<td>${element.todno}</td>
										<td>${element.start_time}</td>
										<td>${element.end_time}</td>
										<td>${element.entryby}</td>
										<td>${element.entrydate}</td>
										<td><a href='#' onclick='editMeterDetails(this.id,${element.id})' id="editData${element.id}">Edit</a></td>
										<td><a href='#' onclick="deleteTOD(${element.id})" >Delete</a></td> 
									</tr>
									<c:set var="count" value="${count+1}" scope="page"/>
									</c:forEach>
									 							
								</tbody>
						
					</table>
					
					
					</div>
					</div>
					</div>
					</div>
					<div id="stack1" class="modal fade" role="dialog"  aria-labelledby="myModalLabel10" >
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ></button>
											<h4 class="modal-title"><span id="addTodId" style="color: #474627;font-weight: bold;">Add TOD</span></h4>
										</div>
										<span style="color:red;font-size: 16px;"  id="accountNotExistMsg" ></span>
										<div class="modal-body">
						 	          <form action="" id="addTodDefinitionId" method="post">
										<div class="row">
											<div class="col-md-6">
											
												<div class="form-group">
													<label class="control-label">TOD Number</label> 
													<span style="color: red" class="required">*</span>
													<input
														type="text" id="todNoId"
														class="form-control placeholder-no-fix"
														placeholder="Enter TOD NO"  name="todNoId" maxlength="12" onchange="checkTodNameExist(this.value)" autocomplete="on" readonly="readonly"></input>
												</div>
											</div>
											</div>
											<!--  <div class="row">
											 <div class="col-md-6">
												<div class="form-group">
													<label class="control-label">Start Time(HH:MM)</label>
													<span style="color: red" class="required">*</span>
													 <div class='input-group date' id='datetimepicker1'>
												          <input type='text' class="form-control" id="datetimepickerStart" name="datetimepickerStart" placeholder="Enter Start Time" readonly="readonly"/>
												          <span class="input-group-addon">
												            <span class="glyphicon glyphicon-calendar"></span>
												          </span>
												     </div> 
												</div>
											</div>
											
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="control-label">End Time(HH:MM)</label>
													<span style="color: red" class="required">*</span>
													 <div class='input-group date' id='datetimepicker2'>
												          <input type='text' class="form-control" id="datetimepickerEnd" name="datetimepickerEnd" placeholder="Enter End Time"/>
												          <span class="input-group-addon">
												            <span class="glyphicon glyphicon-calendar"></span>
												          </span>
												     </div> 
												</div>
											
										</div> 
										</div>  -->
										
										 <div class="row">
										
										<div class="col-sm-3" > <label>Start Time:HH</label><span style="color: red">*</span>
													<select  name="startTimeHH" id="startTimeHH" class="form-control" >
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													</select>
													</div>
													
													<div class="col-sm-3" > <label>Start Time:MM</label><span style="color: red">*</span>
													<select  name="startTimeMM" id="startTimeMM" class="form-control placeholder-no-fix" >
													<option value="00">00</option>
													<option value="30">30</option>
													
													</select>
													
													</div>
													
													<div class="col-sm-3" > <label>END Time:HH</label><span style="color: red">*</span>
													<select  name="endTimeHH" id="endTimeHH" class="form-control" onchange="checkEndTime(this.value)" >
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													</select>
													</div>
													
													<div class="col-sm-3" > <label>END Time:MM</label><span style="color: red">*</span>
													<select  name="endTimeMM" id="endTimeMM" class="form-control placeholder-no-fix" >
													<option value="00">00</option>
													<option value="30">30</option>
													
													</select>
													
													</div>
										
										</div> 
										
										<div class="row">
										<input type="text" id="datetimepickerStartId" name="datetimepickerStartId" hidden="true">
										<input type="text" id="datetimepickerEndId" name="datetimepickerEndId" hidden="true">
										<input type="text" id="toddelId" name="toddelId" hidden="true">
										<input type="text" id="id" name="id" hidden="true">
										</div>
										
										<div class="modal-footer">
											<button class="btn red pull-right" type="button"
												data-dismiss="modal" onclick="formreset()">Cancel</button>
											<button class="btn green pull-right" id="todAddBtn"
												type="button" value="add" onclick="validation(this.value)">ADD</button>
											<button class="btn green pull-right" id="todUpdateBtn"
												type="button" value="update" onclick="validation(this.value)">Modify</button>
									  </div>
										
								</form>
							
							</div>
							</div>
							</div>
							</div>
							</div>

<script>


/* function validation()
{
	var start=$("#datetimepickerStart").val();
	var end=$("#datetimepickerEnd").val();

	var sttime=start.split(":");
	var endtime=end.split(":");
	var sthr=sttime[0];
	var endhr=endtime[0];
	var endMin=endtime[1];
	
		if(endhr>23 || isNaN(endhr))
		{
			bootbox.alert("End hour should be a Number and Not Greater Than 23");
			return false;
		}
		if((endMin!="00" && endMin!="30") || isNaN(endMin))
		{
			bootbox.alert("End Minute should be a Number and it Accepts only 00 and 30 min");
			return false;
		}
		if(endhr<sthr)
		{
			bootbox.alert("End hour should be greater than Start Hour");
			return false;
		}
	
	$("#addTodDefinitionId").attr('action','./addTod').submit();
	}
 */

 function validation(btnValue)
 {
	 
 	var startHH=$("#startTimeHH").val();
 	var startMM=$("#startTimeMM").val();
 	var endHH=$("#endTimeHH").val();
 	var endMM=$("#endTimeMM").val();

 	//alert("startHH:"+startHH+" startMM:"+startMM);
 	//alert("endHH:"+endHH+" endMM:"+endMM);

 	if(startHH>=endHH){
 		bootbox.alert("End hour should be greater than Start Hour");
 		return false;
 	 	}

	var startTime=startHH+":"+startMM;
	var endTime=endHH+":"+endMM;
	$("#datetimepickerStartId").val(startTime);
	$("#datetimepickerEndId").val(endTime);
	//alert($('#datetimepickerStartId').val());
	//alert($('#datetimepickerEndId').val());
	
	if(btnValue=="add"){
 	$("#addTodDefinitionId").attr('action','./addTod').submit();
	}
	if(btnValue=="update"){
 	$("#addTodDefinitionId").attr('action','./modifyTodDefinition').submit();
	}

 	
 	}


function checkEndTime(param)
{
	if(param=='24')
		{
		$("#endTimeMM").val('00');
		//$('#endTimeMM').attr('readonly', true);
		$('#endTimeMM').prop('disabled', true);
		}
	else if(param<'24')
		{
		$("#endTimeMM").val('00');
		$('#endTimeMM').prop('disabled', false);
		}

}

function checkTodNameExist(todNo){
	$.ajax({
		type:"GET",
		url:"./checkTodNameExist",
		dataType: "json",
		data:{todNo:todNo},
		success: function(response){
			if(response.length>0){
				$("#todNoId").val("");
				bootbox.alert("Entered TOD NUMBER Aleready Exist");
				return false;
			}
		}
	});
	}

/* $(function () {
    $('#datetimepicker1').datetimepicker({
            format: '00:00',
            stepping: 30
    });
});

$(function () {
    $('#datetimepicker2').datetimepicker({
            format: 'HH:mm',
            stepping: 30,
          //  showMeridian:true
    });
});
 */

/* function previuosEndTime(){
	$.ajax({
		type:"GET",
		url:"./getPreviuosEndTime",
		dataType: "json",
		success: function(response){
			if(response.length==0){
				$("#datetimepickerStart").val("00:00");
			}
			else{
				for(var i=response.length-1;i<response.length;i++){
					$("#datetimepickerStart").val(response[i][3]);
					}
				}
			}
		

		});
} */

/* function previuosEndTime(){
	$.ajax({
		type:"GET",
		url:"./getPreviuosEndTime",
		dataType: "json",
		success: function(response){
			
			if(response.length>0){
			for(var k=0;k<response.length;k++){
				var data=response[0];
				var todid=response[1];
			if(data.length==0){
				$("#startTimeHH").val("00");
				$("#startTimeMM").val("00");
				$("#todNoId").val(todid[0]);
			}
			else{
				for(var i=0;i<1;i++){
					var prevstime=data[i][3].split(":");
					//var sttimeHH=prevstime[0];
					//var sttimeMM=prevstime[1];
					$("#startTimeHH").val(prevstime[0]);
					$("#startTimeMM").val(prevstime[1]);
					$('#startTimeHH').attr('readonly', true);
					$('#startTimeMM').attr('readonly', true);
					$("#todNoId").val(todid[0]);
					//$("#datetimepickerStart").val(response[i][3]);
					}
				}
				}
			}
		}
		

		});
} */

function clearForm(){
	$("#todAddBtn").show();
	$("#todUpdateBtn").hide();
	$("#todNoId").val("");
	 $.ajax(
				{
						type : "GET",
						url : "./getTODName",
						dataType : "text",
						success : function(response)
						{
							$("#todNoId").val(response);
						}
				});
	
}

function deleteTOD(param)
{
	//var operation=parseInt(opera);
	$("#toddelId").val(param);
	  bootbox.confirm("Are you sure want to delete this record ?", function(result) {
		  if(result == true)
           {
	          $('form#addTodDefinitionId').attr('action','./detletTodDefination').submit();
           }

	  });
	
}

function editMeterDetails(param,opera){

	/* var startHH=$("#startTimeHH").val();
 	var startMM=$("#startTimeMM").val();
 	var endHH=$("#endTimeHH").val();
 	var endMM=$("#endTimeMM").val(); */
 	$("#todAddBtn").hide();
	$("#todUpdateBtn").show();
	 document.getElementById('addTodId').innerHTML='Modify TOD';
	 var operation=parseInt(opera);
     $.ajax(
			{
					type : "GET",
					url : "./getTodDefinitionData/" + operation,
					dataType : "json",
					success : function(response)
								{
									var starthhmm=(response.start_time).split(":");
									var endhhmm=(response.end_time).split(":");
									
									$("#startTimeHH").val(starthhmm[0]);
								 	$("#startTimeMM").val(starthhmm[1]);
								 	$("#endTimeHH").val(endhhmm[0]);
								 	$("#endTimeMM").val(endhhmm[1]);
								 	$("#todNoId").val(response.todno);
								 	$("#id").val(response.id);
								}
			}); 
		
    $('#'+param).attr("data-toggle", "modal");
	$('#'+param).attr("data-target","#stack1");    
}
function formreset(){
    
$('#addTodDefinitionId').trigger("reset");
}
</script>	

