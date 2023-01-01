<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value='/resources/assets/scripts/sweetalert.min.js' />" type="text/javascript"></script>
<script  type="text/javascript">
$(".page-content").ready(function()
    	   {  
	
	App.init();
	TableEditable.init();
	FormComponents.init();
	
	
    $("#MDASSideBarContents,#mdmDashId,#pwsumm,#mdmDashId").addClass('start active ,selected');
 	$("#MDMSideBarContents,#ADMINSideBarContents,#DATAEXCHsideBarContents,#metermang,#surveydetails,#360MeterDataViewID,#dataValidId,#DPId,#alarmID,#reportsId,#eaId,#todEconomcsId").removeClass('start active ,selected');
 	//locData();
 	/* $("#level4").hide();
 	$("#level3").hide();
 	$("#level2").hide(); */
 	loadSearchAndFilter('sample_1'); 
 	/* loadSearchAndFilter('sample_2'); 
 	loadSearchAndFilter('sample_3'); 
 	loadSearchAndFilter('sample_4');  */
 	
 			
	});
	</script>
	<script>
	function showCircle(LFzone)
	    {
	         $.ajax({
	                url:'./getCircleByZone',
	                type:'GET',
	                dataType:'json',
	                asynch:false,
	                cache:false,
	                data : {
	                    zone : LFzone
	                },
	                success:function(response)
	                {
	                  var html='';
	                    html+="<select id='LFcircle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
	                    for( var i=0;i<response.length;i++)
	                    {
	                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
	                    }
						html+="</select><span></span>";
	                    $("#LFcircleTd").html(html);
	                    $('#LFcircle').select2();
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
	                            html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium' type='text' onchange='getTownsBySubdiv(this.value)'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
	     
	     function exportPDF()
	     {
	    	var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdivision = $('#sdoCode').val();
			var town = $('#LFtown').val();
			var fromdate = $('#reportFromDate').val();
			var todate = $('#reportToDate').val();
	  		var townnames = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');

			
			var zn="";
			var cr="";
			var dvs="";
			var subdvn="";
			
			if(zone=="%"){
				zn="ALL";
			}else{
				zn=zone;
			}
			if(circle=="%"){
				cr="ALL";
			}else{
				cr=circle;
			}
			if(division=="%"){
				dvs="ALL";
			}else{
			    dvs=division;
			}
			if(subdivision=="%"){
				subdvn="ALL";
			}else{
				subdvn=subdivision;
			}
			if(town=="%"){
				tn="ALL";
			}else{
				tn=town;
			}

			
			window.open("./periodwisecommunicationpdf/"+zn+"/"+cr+"/"+tn+"/"+fromdate+"/"+todate+"/"+townnames)
			
	     }

	</script> 
<div class="page-content">               
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Period Wise Communication Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
</div>

        <jsp:include page="locationFilter.jsp"/> 
	    <div class="row" style="margin-left: -1px;">

						<%-- <table >
							<tbody>
								<tr>
								<th id="zonef" class="block">Region&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
										<option value="">Select Region</option>
										<option value="%">ALL</option>
										<c:forEach items="${ZoneList}" var="elements">
															<option value="${elements}">${elements}</option>
														</c:forEach>
								</select></th>
								
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showTownNameandCode(this.value);">
									<option value="">Select Circle</option>
									<option value="%">ALL</option>
								</select></th>
								
								<!-- <th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">
									<option value="">Select Division</option>

								</select></th> -->
								
								<th class="block">Town&nbsp;:</th>
								<th id="townId">
								<select class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select Town</option>
								</select></th> --%>
							
						   <div class='col-md-3'><strong> From Date</strong>
							<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</div>
					
							 <div class='col-md-3'><strong> To Date</strong>
							<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportToDate" id="reportToDate"  readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</div>
								
								
								<!-- <tr><td class="text-center">
										<button type="button" id="viewFeeder" "
											onclick="return locData()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td></tr> -->
					
					
						
						<div class="col-md-3" style="margin-top:15px;">
						<button type="button" id="viewFeeder" 
											onclick="return locData()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
						</button>
						</div>
						</div>
				
				
<br/><br/>
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								<li><a href="#" id=""
								         onclick="exportPDF('sample_1','Period Wise Summary')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Period Wise Summary')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
						<tr>
					 <th colspan="1" id="Data"></th>
					  <th colspan="1" id="Circle"></th>
					   <th colspan="1" id="Town"></th>
					 
					 <th colspan="3"  id="smry" style="text-align: center ;">TOTAL METERS</th>
					 <th  style="text-align: center;"colspan="3" id="DT">  DT METERS</th>
					 <th  style="text-align: center;"colspan="3" id="Meter"> FEEDER METERS </th>
					 <th  style="text-align: center;"colspan= "3" id="Non_Working"> BOUNDARY METERS</th>
					 
				</tr>
						
						
							<tr>
								<th colspan="1">Date</th>
								<th colspan="1">Circle</th>
								<th colspan="1">Town</th>
								
								<th colspan="1">Total Meters</th>
								<th colspan="1">Comm Mtrs</th>
								<th colspan="1">NonComm Mtrs</th>
								<!-- <th>LT Meters</th>
								<th>LT  Communicating Meters</th>
								<th>LT  Non Communicating Meters</th>
								<th>HT Meters</th>
								<th>HT  Communicating Meters</th>
								<th>HT  Non Communicating Meters</th> -->
								<th colspan="1">DT Meters</th>
								<th colspan="1">DT Communication</th>
								<th colspan="1">DT Non-communication</th>
								<th colspan="1">Feeder Mtrs</th>
								<th colspan="1">FM Communication</th>
								<th colspan="1">FM Non-communication</th>
								<th colspan="1">Boundary Meters</th>
								<th colspan="1">BM Communication</th>
								<th colspan="1">BM Non-communication</th>
								
								
								
							</tr>
						</thead>
									<tbody id="locId">
									
							
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					<!-- <div class="row" id="level2">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Circle Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
<br/><br/>
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_2">
						<thead>
							<tr>
								<th>Zone</th>
								<th>Circle</th>
								<th>LT Meters</th>
								<th>HT Meters</th>
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>
								
								
							</tr>
						</thead>
									<tbody id="cirId">
									
							
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					<div class="row" id="level3">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Division Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
<br/><br/>
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_3">
						<thead>
							<tr>
								<th>Zone</th>
								<th>Circle</th>
								<th>Division</th>
								<th>LT Meters</th>
								<th>HT Meters</th>
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>
								
								
							</tr>
						</thead>
									<tbody id="divId">
									
							
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					<div class="row" id="level4">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>SubDivision Wise Summary
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
<br/><br/>
                   <div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li> 
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Feeder Details')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_4">
						<thead>
							<tr>
								<th>Zone</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>LT Meters</th>
								<th>HT Meters</th>
								<th>DT Meters</th>
								<th>Feeder Meters</th>
								<th>Boundary Meters</th>
								
								
							</tr>
						</thead>
									<tbody id="subdivId">
									
							
									 							
								</tbody>
						
					</table>
					
					</div>
					</div>
					</div>
					</div>
					 --></div>
					
							
		<script>
		function locData(){
			$('#locId').empty();
			var zone = $('#LFzone').val();
			var circle = $('#LFcircle').val();
			var division = $('#division').val();
			var subdivision = $('#sdoCode').val();
			var town = $('#LFtown').val();
			var fromdate = $('#reportFromDate').val();
			var todate = $('#reportToDate').val();
			/* alert(zone);
			alert(circle);
			alert(town); */
			
			if(zone == ''){
				bootbox.alert('Please select Zone.')
				return false;
			}
			
			if(circle == ''){
				bootbox.alert('Please select Circle.')
				return false;
			}
			
			if(division == ''){
				bootbox.alert('Please select Division.')
				return false;
			}
			
			if(subdivision == ''){
				bootbox.alert('Please select Sub-Division.')
				return false;
			}
			if(town == ''){
				bootbox.alert('Please select Town.')
				return false;
			}
			if(fromdate == ''){
				bootbox.alert('Please enter From-Date.')
				return false;
			}
			if(todate == ''){
				bootbox.alert('Please enter To-Date.')
				return false;
			}
			$('#imageee').show();
			//var z=document.getElementById("zone").value;
			//console.log("zone--"+z);
		//	alert("in locdata"+zone+circle+division+subdivision+fromdate+todate+z);
		$.ajax({
			url:'./pCommSummary',
			type:'GET',
			data: {
				zone : zone,
				circle : circle,
				division : division,
				subdivision : subdivision,
				town : town,
				fromdate : fromdate,
				todate : todate,
		
			},
			success:function(res){
				$('#imageee').hide();
				var html='';
				$.each(res,function(i,v){
					html+='<tr><td>'+moment(v[0]).format('DD-MM-YYYY')+'</td>'
					+'<td>'+v[13]+'</td>'
					+'<td>'+v[14]+'</td>'
					+'<td>'+v[1]+'</td>'
					+'<td>'+v[2]+'</td>'
					+'<td>'+v[3]+'</td>'
					/* +'<td>'+v[4]+'</td>'
					+'<td>'+v[5]+'</td>'
					+'<td>'+v[6]+'</td>'
					+'<td>'+v[7]+'</td>'
					+'<td>'+v[8]+'</td>'
					+'<td>'+v[9]+'</td>' */
					+'<td>'+v[4]+'</td>'
					+'<td>'+v[5]+'</td>'
					+'<td>'+v[6]+'</td>'
					+'<td>'+v[7]+'</td>'
					+'<td>'+v[8]+'</td>'
					+'<td>'+v[9]+'</td>'
					+'<td>'+v[10]+'</td>'
					+'<td>'+v[11]+'</td>'
					+'<td>'+v[12]+'</td>';
					
					
					/* html+='<tr><td><a onclick="circleData(this.id)" id="'+v[0]+'">'
					+v[0]+'</a></td><td>'+v[1]+
					'</td><td>'+v[2]+'</td><td>'+v[3]
					+'</td><td>'+v[4]+'</td><td>'
					+v[5]+'</td></tr>'; */
					
					
				});
				$('#sample_1').dataTable().fnClearTable();
				$('#locId').html(html);
				loadSearchAndFilter('sample_1'); 
				
			}
			
		});
		}
		 function circleData(cir){
			$.ajax({
				url:'./circleList',
				type:'GET',
				data:{
					circle:cir
				},
				success:function(res){
					var html='';
					$.each(res,function(i,v){
						html+='<tr><td>'+v[0]+'</td><td><a onclick="divisionData(this.id)" id="'+v[0]+'@'+v[1]+'">'+v[1]+'</a></td><td>'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td></tr>';
						
						
					});
					$('#cirId').html(html);
					$("#level4").hide();
				 	$("#level3").hide();
				 	$("#level2").show();
				}
				
			});
		}
function divisionData(div){
	$.ajax({
		url:'./divisionList',
		type:'GET',
		data:{
			div:div
		},
		success:function(res){
			var html='';
			$.each(res,function(i,v){
				html+='<tr><td>'+v[0]+'</td><td>'+v[1]+'</td><td><a onclick="subdivisionData(this.id)" id="'+v[0]+'@'+v[1]+'@'+v[2]+'">'+v[2]+'</a></td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td><td>'+v[7]+'</td></tr>';
				
				
			});
			$('#divId').html(html);
			$("#level4").hide();
		 	$("#level3").show();
		 	$("#level2").show();
		}
		
	});
		}
function subdivisionData(subdiv){
	$.ajax({
		url:'./subdivList',
		type:'GET',
		data:{
			subdiv:subdiv
			
		},
		success:function(res){
			var html='';
			$.each(res,function(i,v){
				html+='<tr><td>'+v[0]+'</td><td>'+v[1]+'</td><td>'+v[2]+'</td><td>'+v[3]+'</td><td>'+v[4]+'</td><td>'+v[5]+'</td><td>'+v[6]+'</td><td>'+v[7]+'</td><td>'+v[8]+'</td></tr>';
				
				
			});
			$('#subdivId').html(html);
			$("#level4").show();
		 	$("#level3").show();
		 	$("#level2").show();
		}
		
	});
}

function showResultsbasedOntownCode(){
	
}
		
</script>
<script>
function getTownsBySubdiv(subdiv){
	$('#town').find('option').remove();
	$.ajax({
        url : './getTownNameandCodeBySubDiv',
        type : 'GET',
        dataType : 'json',
        asynch : false,
        cache : false,
        data : {
         sitecode : subdiv
        },
           success : function(response) {
               
        	   $('#town').append($('<option>', {
  					value : '%',
  					text : 'ALL',
  				}));
               for (var i = 0; i < response.length; i++) {
                   //var response=response1[i];
                   /* html += "<option value='"+response1[i][0]+"'>"
                   + response1[i][0] + "-" + response1[i][1] + "</option>"; */

                   $('#town').append($('<option>', {
   					value : response[i][0],
   					text : response[i][0] + "-" + response[i][1],
   				}));
               }
               
           }
       });
	
}

function showTownNameandCode(LFcircle){
	var LFzone = $("#LFzone").val();

	
	//var zone =  $('#LFzone').val(); 
	//$('#town').find('option').remove();
	   $.ajax({
	      	url:'./getTownNameandCodebyCircle',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	  			zone : LFzone,
	  			circle:LFcircle
	  		},
	  		success : function(response) {
	  		   $('#LFtown').append($('<option>', {
 					value : '%',
 					text : 'ALL',
 				}));
              for (var i = 0; i < response.length; i++) {
                 
                  $('#LFtown').append($('<option>', {
  					value : response[i][0],
  					text : response[i][0] + "-" + response[i][1],
  				}));
              }
            }
	  	});
	  }

</script>
<style>
.select2-container.form-control {
    height: auto !important;
    padding: 0 !important;
    border: 0 !important;
    width: 220px !important;
}


</style>
