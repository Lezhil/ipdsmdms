<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


 <style>
.input-medium {
width: 180px !important;
}
a {
color: blue;
text-shadow: none !important;
}
</style>
		<script>
			$(".page-content").ready(function()
			{
			App.init();
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			
			
			//alert(zoneVal);
			/* if(zoneVal!='' && circleVal!=''){
			
			$('#zone').find('option').remove().end().append('<option value="'+ zoneVal +'">'+ zoneVal +'</option>');
			$("#zone").val(zoneVal).trigger("change");
			
			setTimeout(function(){
			$('#circle').find('option').remove().end().append('<option value="'+ circleVal +'">'+ circleVal +'</option>');
			$("#circle").val(circleVal).trigger("change");
			}, 200);
			} */
			
			
			
			$('#MDASSideBarContents,#modemOper,#modemdiagnosis').addClass('start active ,selected');
			$('#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
			
			});
			</script>
			
			
<script>
	function showCircle(zone)
	    {
	         $.ajax({
	                url:'./getCircleByZone',
	                type:'GET',
	                dataType:'json',
	                asynch:false,
	                cache:false,
	                data : {
	                    zone : zone
	                },
	                success:function(response)
	                {
	                  var html='';
	                    html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
	                    for( var i=0;i<response.length;i++)
	                    {
	                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
	                    }
						html+="</select><span></span>";
	                    $("#circleTd").html(html);
	                    $('#circle').select2();
	                }
	            });
	    }

	     function showDivision(circle) {
	   	  var zone = $('#zone').val();
	            $.ajax({
	                url : './getDivByCircle',
	                type : 'GET',
	                dataType : 'json',
	                asynch : false,
	                cache : false,
	                data : {
	                    zone : zone,
	                    circle : circle
	                },
	                        success : function(response) {
	                            var html = '';
	                            html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
	                            for (var i = 0; i < response.length; i++) {
	                                html += "<option value='"+response[i]+"'>"
	                                        + response[i] + "</option>";
	                            }
	                            html += "</select><span></span>";
	                            $("#divisionTd").html(html);
	                            $('#division').select2();
	                        }
	                    });
	        }
	     function showSubdivByDiv(division) {
	            var zone = $('#zone').val();
	            var circle = $('#circle').val();
	            $.ajax({
	                url : './getSubdivByDiv',
	                type : 'GET',
	                dataType : 'json',
	                asynch : false,
	                cache : false,
	                data : {
	                    zone : zone,
	                    circle : circle,
	                    division : division
	                },
	                        success : function(response1) {
	                            var html = '';
	                            html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
	                            for (var i = 0; i < response1.length; i++) {
	                                //var response=response1[i];
	                                html += "<option value='"+response1[i]+"'>"
	                                        + response1[i] + "</option>";
	                            }
	                            html += "</select><span></span>";
	                            $("#subdivTd").html(html);
	                            $('#sdoCode').select2();
	                        }
	                    });
	        }

	</script> 
	<script>
	function modemDiagnosVal(){
		
		var zone =$('#zone').val();
		var circle=$('#circle').val();
		var division =$('#division').val();
		var subdiv=$('#sdoCode').val();
		if(zone==''|| zone==0){
			 bootbox.alert("Please select zone");
			 return false;
		}
		if(circle=='' || circle==0){
			 bootbox.alert("Please select Circle");
			 return false;
		 } 
		if(division=='' || division==0){
			 bootbox.alert("Please select division");
			 return false;
		 } 
		
		if(subdiv=='' || subdiv==0){
			 bootbox.alert("Please select subdivision");
			 return false;
		 } 
		
        $.ajax({
        	url:'./modemcount',
        	type:'POST',
        	data:{
        		zone:zone,
        		circle:circle,
        		division:division,
        		subdiv:subdiv
        	},
        	datatype:'json',
        	success:function(response){
        		var html="";
  			  for (var i = 0; i < response.length; i++) 
  			  {
  				  var resp=response[i];
  					   html+="<tr>"+
 					 "<td style='text-align: center;'><a href=#  onclick='return getValDiagStat(this.id);' id='Today'>"+resp[1]+"</a></td>"+
  					  "<td style='text-align:center;'><a href=#  onclick='return getValDiagStat(this.id);' id='Yesterday'>"+resp[2]+"</a></td>"+
  					  "<td style='text-align:center;'><a href=# onclick='return getValDiagStat(this.id);' id='CurrMonth'>"+resp[3]+"</a></td>"+
  					  "<td style='text-align:center;'><a href=# onclick='return getValDiagStat(this.id);' id='PrevMonth'>"+resp[4]+"</a></td>"+
  					  +"</tr>";						
  			   } 
        		$('#sample_1').dataTable().fnClearTable();
  			  $("#modemcountId").html(html);
  			  loadSearchAndFilter('sample_1'); 
        	},
        	complete: function()
      		{  
      			loadSearchAndFilter('sample_1'); 
      		}
        	
        });
        $("#viewDiagnosisMeter").show("fast");
       
	}
	
	
	function getValDiagStat(param){
	
		var zone =$('#zone').val();
		var circle=$('#circle').val();
		var division =$('#division').val();
		var sdocode=$('#sdoCode').val();
		var id=param;
		$('#category').val(id);
		var category = $("#category").val();
		$.ajax({
			url:"./getValDiagStat",
			type:'POST',
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdocode:sdocode,
				category:category
			},
			
			success:function(response){
				var html="";
	  			  for (var i = 0; i < response.length; i++) 
	  			  {
	  				  var resp=response[i];
 					   html+="<tr>"+
 					   "<td>"+resp[5]+"</td>"+
 					   "<td>"+resp[6]+"</td>"+
		"<td>"+
		"<a style='color:blue;' onclick='return mtrDetails(\""+resp[0]+"\");'>"+resp[0]+"</a>"+
		"</td>"+
  					  "<td>"+resp[1]+"</td>"+ 
  						 "<td>"+ moment(resp[2]).format('hh:mm:ss')+ "</td>"+
 					 /*   "<td>"+resp[2]+"</td>"+ */
 					  "<td>"+resp[3]+"</td>"+
 					  "<td>"+resp[4]+"</td>"+
 					   "</tr>";
	  			  }
	  			$('#sample_2').dataTable().fnClearTable();
	  			  $("#diagnosisdetails").html(html);
	  			  loadSearchAndFilter('sample_2');  
			},
			complete: function()
      		{  
      			loadSearchAndFilter('sample_2'); 
      		}
		});
		 $("#DiagnosisDetails").show("fast");
	}
	
	var mtrNum;
	function mtrDetails(mtrNo)
	{
		mtrNum=mtrNo;
		window.location.href="./viewFeederMeterInfoMDAS?mtrno=" + mtrNo;
	}
	
	
	</script>
	
	
	
	<div  class="page-content" >
	<div class="row">
	<div class="col-md-12">
	<div class="portlet box green">
	<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Modem Diagnosis 
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="reload"></a>
			</div>
		</div>
		<div class="portlet-body">
	<div class="row" style="margin-left: -1px;">
						<table style="width: 38%">
							<tbody>
								<tr>
									<th id="zonelab" class="block">Zone&nbsp;:</th>
									<th id="zones"><select
										class="form-control select2me input-medium" id="zone"
										name="zone" onchange="showCircle(this.value);">
									<option value=""></option>
									<option value="%">All</option>
											 <c:forEach var="elements" items="${zoneList}">
						<option value="${elements}">${elements}</option>
						</c:forEach>
									</select></th>
									<th class="block" id="circlelab">Circle&nbsp;:</th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showDivision(this.value);">
											
											<option value=""></option>
									</select></th>
									<th class="block" id="divlab">Division&nbsp;:</th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">
                                        <option value=""></option>
									</select></th>
									<th class="block" id="sdolab">Sub&nbsp;Division&nbsp;:</th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
										<option value=""></option>
										</select></th>
										<td>
										<button type="button" id="view"
											style="margin-left: 20px;" onclick="return modemDiagnosVal()"
											name="view" class="btn yellow">
											<b>View</b>
										</button> 
									</td>
								</tr>
								
							</tbody>
						</table>
					</div>
		</div>
	</div><!-- green -->
	<input type="text" id="category" hidden="true">
	<div class="portlet box blue" id="viewDiagnosisMeter" style="display: none;">
	<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-globe"></i>Meter Diagnosis Status
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
	<div class="portlet-body">
	<table class="table table-striped table-hover table-bordered" id="sample_1">
	<thead>
	<tr>
						<th style="text-align: center;">Today</th>
						<th style="text-align: center;">Previous Day</th>
						<th style="text-align: center;">Current Month</th>
						<th style="text-align: center;">Previous Month</th>
					</tr>
	</thead>
	<tbody id="modemcountId">
	
	</tbody>
	</table>
	
	
	
	</div>
	</div><!-- blue -->
	
	<div class="portlet box purple" id="DiagnosisDetails" style="display: none;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-globe"></i>Meter Diagnosis Status Details
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_2', 'Modem Diagnosis Stats Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
			<table class="table table-striped table-hover table-bordered" id="sample_2">
				<thead>
					<tr>
						<th style="text-align: center;">Acc No</th>
								<th style="text-align: center;">Consumer Name</th>
								<th style="text-align: center;">Meter No</th>
								<th style="text-align: center;">Modem No</th>
								<th style="text-align: center;">Tracked Time</th>
								<th style="text-align: center;">Diagnosis Type</th>
								<th style="text-align: center;">Status</th>
						
					</tr>
				</thead>
				<tbody id=diagnosisdetails>

					
				</tbody>
			</table>
		</div>
	</div>
	
	
	
	</div>
	</div>
	</div>
	
	
	
	
	
		
			