<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		 $("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				//allMtrno();
				allAccno(); 
				 $("#check").click(
							function() {
								$(".checkboxes").prop('checked',
										$(this).prop('checked'));
							});
				 $('#MDMSideBarContents,#alarmID,#removealarms').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
</script>

<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-Book"></i><strong>Remove Alarm Setting</strong>
			</div>
		</div>
		
		<div class="portlet-body">
		<!-- <div style="width: 1200px;border: 1px solid blue;box-sizing: border-box;"> -->
		<form>
      <fieldset>
        <legend style="color: blue">Single Location</legend>
			<div class="mt-radio-inline" id='radioid' style="margin-left: 10px;">
					                
					<div class="row" >
					 <div class="col-md-1">
					<label class="mt-radio"><input type="radio" name="optionsRadios" id="accno_radio" value="accno" onchange="setRadioVal(this.value)" checked></label>
					</div> 
					<div class="col-md-3">
						<div id="accountTd" class="form-group">
						<strong>Location  Id</strong>
							<select class="form-control select2me" id="accNo" name="accNo">
						<option value="">Location  Id</option>
											
							</select>
						</div>
					</div>
					<div class="col-md-1">
				<label class="mt-radio"><input type="radio" name="optionsRadios" id="mtrno_radio" value="mtrno" onchange="setRadioVal(this.value)"></label> 
					</div>
					<div class="col-md-3">
						<div id="meterTd" class="form-group">
						<strong>Meter.No</strong>
							<select class="form-control select2me" id="mtrNo" name="mtrNo">
						<option value="">Meter.No</option>
											
							</select>
						</div>
					</div>
					                
					   <button type="button" id="" style="margin-left: 10px; margin-top: 2px;" onclick="return alarmDetailsData()" name="" class="btn green">
										<b>View</b>
						</button>
								    
						<button type="button" id="" style="margin-left: 10px; margin-top: 2px;" onclick="return removeAlarmDetailsData()" name="" class="btn green">
										<b>Remove</b>
						</button>
					     </div>           
			</div><br>
			<div class="portlet box light-grey">
						<div class="portlet-title">
						<div class="caption"><i class="fa fa-globe"></i>Location Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr>
										<th>Town Name</th>
										<th>Location Id</th>
										<th>Location Name</th>
										<th>Meter.No</th>
										<th>Alarm Setting</th>
									</tr>
								</thead>
						 <div id="imageee1" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
								<tbody id="alarmId">
								</tbody>
							</table>
							</div>
						</div>
			</div>
			</fieldset>
    </form>
    <form  style="width: 1200px;">
      <fieldset>
        <legend style="color: blue">Multiple Location</legend>
        <div class="row" >
        <div class="col-md-3">
						<div id="alarmNamediv" class="form-group">
						<strong>Alarm  Name</strong>
							<select class="form-control select2me" id="alarmName" name="alarmName">
						<option value="">Alarm  Name</option>
											
							</select>
						</div>
					</div>
					 <button type="button" id="multiview" style="margin-left: 10px; margin-top: 2px;" onclick="return alarmDetailsDatabyAlarmname()" name="" class="btn green">
										<b>View</b>
						</button>
								    
						<button type="button" id="multiremove" style="margin-left: 10px; margin-top: 2px;" onclick="return removeAlarmDetailsDatabyAlarmname()" name="" class="btn green">
										<b>Remove</b>
						</button>
					     </div>  
					     <br>
					     <div class="portlet box light-grey">
						<div class="portlet-title">
						<div class="caption"><i class="fa fa-globe"></i>Location Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<table class="table table-striped table-bordered table-hover" id="sample_3">
								<thead>
									<tr>
									<th>Select<input type="checkbox" 
							id="check" class="checkboxes" ></th>
										<th>Town Name</th>
										<th>Location Id</th>
										<th>Location Name</th>
										<th>Meter.No</th>
										<th>Alarm Setting</th>
									</tr>
								</thead>
						 <div id="imageee3" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
								<tbody id="alarmnamesId">
								</tbody>
							</table>
							</div>
						</div>
			</div>
        </fieldset>
        </form>
		</div>
		
   <!-- </div> -->
   </div>
</div>

<div id="stack1" class="modal fade" role="dialog" aria-labelledby="myModalLabel10" >
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-dailog">
		<div class="modal-content">
        <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
			<h4 class="modal-title" id="tabTitle"></h4>
        </div>
        <div class="modal-body">
        <div class="portlet-body">
         <div id="imageee2" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
						<!--   <div class="table-toolbar">
								<div class="btn-group pull-right" >
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li>
										<a href="#" id="excelExport2" onclick="tableToExcel2('sample_2', 'MetersData')">Export to Excel</a>
										</li>
									</ul>
								</div>
							</div> --> 
						<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
						<tr>
						    <th>Event Code</th>
							<th>Event Name</th> 
							<th>Priority</th>
							
						</tr>
						</thead>
						<tbody id="priorityId">
						</tbody>
						</table>
						</div>
        </div>
		</div>
		</div>
		</div>
		</div>

<script>

function allAccno(){
    $.ajax({
       url:"./showAccnoDetails",
       type:"GET",
       timeout:20000,
       success:function(res){
           var html="<option value=''>Location Id</option>";
           $.each(res,function(k,v){
               
         	  html+='<option value="'+v+'">'+v+'</option>';
               });
               $("#accNo").html(html);
           }
   
        });
    allMtrno();
 	}
	function allMtrno(){
        $.ajax({
           url:"./showMtrnoDetails",
           type:"GET",
           timeout:20000,
           success:function(res){
               var html="<option value=''>Meter Number</option>";
               $.each(res,function(k,v){
                   
             	  html+='<option value="'+v+'">'+v+'</option>';
                   });
                   $("#mtrNo").html(html);
               }
       
            });
        getAlarmName();
	    	}
	function getAlarmName(){
        $.ajax({
           url:"./getalarmname",
           type:"GET",
           timeout:20000,
           success:function(res){
               var html="<option value=''>Alarm Name</option>";
               $.each(res,function(k,v){
                   
             	  html+='<option value="'+v+'">'+v+'</option>';
                   });
                   $("#alarmName").html(html);
               }
       
            });
	    	}

function alarmDetailsDatabyAlarmname()
{
	var alarmName=$('#alarmName').val();
	if((alarmName == "" ||alarmName == null ||alarmName=='undefined'))
		{
		 bootbox.alert("Please Select Alarm Name");
		 return false;
		} 
	 $('#sample_3').dataTable().fnClearTable();
	$('#imageee3').show();
	$.ajax({
		url : './showAlarmDetailsbyAlarmname',
		type:'POST',
		data:{
			alarmName : alarmName
		},
		success:function(response)
		{
			$('#imageee3').hide();
			if (response.length != 0){
				var html = "";
				for (var i = 0; i < response.length; i++){
					var element = response[i];
					html += "<tr><td><input type='checkbox' id='check' class='checkboxes' name='al' value='"+element[1]+"' />&nbsp;</td>" 
					+ "<td>"+ (element[0]== null ? "": element[0])+"</td>"
					+ "<td>"+ (element[1]== null ? "": element[1])+"</td>"
					+ "<td>"+ (element[2]== null ? "": element[2])+"</td>"
					+ "<td>"+ (element[3]== null ? "": element[3])+"</td>"
					+ '<td><a href="#" data-toggle="modal"  data-target="#stack1" onclick="return eventPrioritybyAlarmName(this.id,\''+ element[4]+ '\')" id="'+element[1]+'">'+(element[4]==null?"":element[4])+'</a></td>'
				}
			    $('#sample_3').dataTable().fnClearTable();
				$('#alarmnamesId').html(html);
				loadSearchAndFilter('sample_3'); 
			}
			else{
				$('#imageee3').hide();
				bootbox.alert("No data Found");
			}
		},
	});
}
function alarmDetailsData()
{
	var accno=$('#accNo').val();
	var mtrno=$('#mtrNo').val();
	var radioval = $("input[name='optionsRadios']:checked").val();
	if((accno == "" ||accno == null ||accno=='undefined')&&(mtrno == "" ||mtrno == null ||mtrno=='undefined'))
		{
		 bootbox.alert("Please Select Accno/Meterno");
		 return false;
		} 
	   $('#sample_1').dataTable().fnClearTable();
	$('#imageee1').show();
	$.ajax({
		url : './showAlarmDetails',
		type:'POST',
		data:{
			accno:accno,
			mtrno:mtrno,
			radioval:radioval
		},
		success:function(response)
		{
			$('#imageee1').hide();
			if (response.length != 0){
				var html = "";
				for (var i = 0; i < response.length; i++){
					var element = response[i];
					html += "<tr>" 
					+ "<td>"+ (element[0]== null ? "": element[0])+"</td>"
					+ "<td>"+ (element[1]== null ? "": element[1])+"</td>"
					+ "<td>"+ (element[2]== null ? "": element[2])+"</td>"
					+ "<td>"+ (element[3]== null ? "": element[3])+"</td>"
					+ "<td><a href='#' data-toggle='modal'  data-target='#stack1' onclick='return eventPriority(this.id)' id='"+element[1]+"'>"+(element[4]==null?"":element[4])+"</a></td>"
				}
			    $('#sample_1').dataTable().fnClearTable();
				$('#alarmId').html(html);
				loadSearchAndFilter('sample_1'); 
			}
			else{
				$('#imageee2').hide();
				bootbox.alert("No data Found");
			}
		},
	});
}


function eventPriority(param)
{ 
	var accnumber=param;
	$('#imageee2').show();
	$.ajax({
		url : './showAlarmPriorityDetails',
		type:'POST',
		data:{
			accnumber:accnumber
		},
		success:function(response)
		{
			$('#imageee2').hide();
			if (response.length != 0){
				var html = "";
				for (var i = 0; i < response.length; i++){
					var element = response[i];
					html += "<tr>" 
					+ "<td>"+ (element[0]== null ? "": element[0])+"</td>"
					+ "<td>"+ (element[1]== null ? "": element[1])+"</td>"
					+ "<td>"+ (element[2]== null ? "": element[2])+"</td>"
				}
			    $('#sample_2').dataTable().fnClearTable();
				$('#priorityId').html(html);
				loadSearchAndFilter('sample_2'); 
			}
			else{
				bootbox.alert("No data Found");
			}
		},
		

	});
}
function eventPrioritybyAlarmName(param,aln)
{ 
	var alarmname=aln;
	var locationId=param;
	$('#imageee2').show();
	$.ajax({
		url : './showAlarmPriorityDetailsbyAlarmName',
		type:'POST',
		data:{
			alarmname:alarmname,
			locationId : locationId
		},
		success:function(response)
		{
			$('#imageee2').hide();
			if (response.length != 0){
				var html = "";
				for (var i = 0; i < response.length; i++){
					var element = response[i];
					html += "<tr>" 
					+ "<td>"+ (element[0]== null ? "": element[0])+"</td>"
					+ "<td>"+ (element[1]== null ? "": element[1])+"</td>"
					+ "<td>"+ (element[2]== null ? "": element[2])+"</td>"
				}
			    $('#sample_2').dataTable().fnClearTable();
				$('#priorityId').html(html);
				loadSearchAndFilter('sample_2'); 
			}
			else{
				bootbox.alert("No data Found");
			}
		},

	});
}
function removeAlarmDetailsData()
{
	var accno=$('#accNo').val();
	var mtrno=$('#mtrNo').val();
	var radioval = $("input[name='optionsRadios']:checked").val();
	$.ajax({
		url : './alarmRemoveDetails',
		type:'POST',
		data:{
			accno:accno,
			mtrno:mtrno,
			radioval:radioval
		},
		success:function(response)
		{
			if(response=="Deleted")
				{
				bootbox.alert("Deleted Successfully");
				return false;
				}
			else{
				bootbox.alert("Failed to Delete");
				return false;
				}
		},
	});
	
}
function removeAlarmDetailsDatabyAlarmname(){
    var locationids = [];

    $.each($("input[name='al']:checked"), function(){

    	locationids.push($(this).val());

    });

	var alarmName=$('#alarmName').val();
	
	if(locationids.length==0){
		bootbox.alert("Please select atleast one alarm to  remove")
		return false;
	}
	$.ajax({
		url : './alarmRemoveDetailsbyAlarmName',
		type:'POST',
		data:{
			alarmName : alarmName,
			locationids : locationids
			
		},
		success:function(response)
		{
			if(response=="Deleted")
				{
				bootbox.alert("Deleted Successfully");
				return false;
				}
			else{
				bootbox.alert("Failed to Delete");
				return false;
				}
			
		},
		complete: function()
  		{  
			alarmDetailsDatabyAlarmname();
			getAlarmName();
  		} 
	});
}
function setRadioVal(val)
{
	if(val == 'accno' )
		{
		  $('#mtrNo').val('').trigger("change");
		}
	else if(val == 'mtrno')
		{
		  $('#accNo').val('').trigger("change");
		}
}

</script>