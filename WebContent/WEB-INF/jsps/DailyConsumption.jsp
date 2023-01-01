<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		 $("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				
				
				$('#MDMSideBarContents,#DPId,#dailyConsumption').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
	/* function showCircle(zone) {
		
		$.ajax({
			url:'./getcirclebyzone',
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			data:{
				zone:zone
			},
			success : function(response) {
				var html = '';
				html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
				for (var i = 0; i < response.length; i++) {
					html += "<option  value='"+response[i]+"'>"
							+ response[i] + "</option>";
				}
				html += "</select><span></span>";
				$("#circleTd").html(html);
				$('#circle').select2();
			}
		});
}
	
	function showDivision(circle)
	{
		var zone=$('#zone').val();
		$.ajax({
			url : './getdivisionbycircle',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					circle : circle
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
		    	}
			});
	}
	
	
	function showSubdivByDiv(division)
	{
		
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		$.ajax({
			url : './getSubdivByDiv',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
		    		zone : zone,
					circle : circle,
					division : division
				},
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='subdiv' name='subdiv' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='All'>ALL</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#subdiv').select2();
		    	}
			});
	} */

	function showCircle(zone) {
		$
				.ajax({
					url : './getCircleByZone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone
					},
					success : function(response) {
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#circleTd").html(html);
						$('#circle').select2();
					}
				});
	}

	function showDivision(circle) {
		var zone = $('#zone').val();

		$
				.ajax({
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
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
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
		$
				.ajax({
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
						html += "<select id='subdiv' name='subdiv'  class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#subdiv').select2();
					}
				});
	}
	
	
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Daily Data Aggregation</div>
							
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body form">
								<form id="dailyAvgLoadId" action="">
										<table ><tbody>
										<tr>
										<th class="block">Zone&nbsp;:</th>
								<th id="zoneTd"><select class="form-control select2me input-medium"
									id="zone" name="circle" onchange="showCircle(this.value);">
									<option value=""></option>
											 <c:forEach var="elements" items="${zoneList}">
						<option value="${elements}">${elements}</option>
						</c:forEach>	
								</select></th>
										<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle" onchange="showDivision(this.value);">
										
								</select></th>
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
										
								</select></th>
								
							</tr><tr><th colspan="8"></th></tr><tr><th colspan="8"></th></tr>
										 <tr>
										 <th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="subdiv"
									name="sdoCode">
										
								</select></th>
									<th class="block">Location Type:</th>
									<th id="locationType"> 
									<select class="form-control select2me" id="consumerCatgry"
									name="consumerCatgry"><option value=""></option>
									<c:forEach var="elements" items="${fdrcatList}">
						<option value="${elements}">${elements}</option>
						</c:forEach>	
													</select></th>
									<th class="block">Location Identity:</th>
									<th class="form-group">
											<input type="text" id="acno"
												class="form-control placeholder-no-fix"
												placeholder="Enter Account No." name="acno" maxlength="12"></input>
										</th>
								</tr>
												
										 
										 
										 
										 <tr><th colspan="8"></th></tr><tr><th colspan="8"></th></tr>
										 <tr>
										 <th class="block">From&nbsp;Date&nbsp;:</th>
										 <th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th class="block">To&nbsp;Date&nbsp;:</th><th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th colspan="4" >
																<div >
																
									<div class="col-md-offset-4 col-md-8">                        
										<!-- <button type="button" class="btn yellow">Reset</button>   -->
										<button type="button" onclick="validation()" class="btn green">View</button> 
									</div>
								</div>
																</th>
																</tr> </tbody>
										</table>
									
										
										</form>
								<br><br>	
								
							
						</div>
					</div>
					
					<div class="portlet box blue" style="display: none" id="boxid">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Result List</div>
							<div class="actions">
								<!-- <a href="#" class="btn blue"><i class="fa fa-pencil"></i> Add</a> -->
								<div class="btn-group">
									<a class="btn green" href="#" data-toggle="dropdown">
									<i class="fa fa-cogs"></i> Tools
									<i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="excelExport2"
																	onclick="tableToExcel2('sample_1', 'Daily Consumption')">Export
																		to Excel</a></li>
									</ul>
								</div>
							</div>
						</div>					
											
						<div class="portlet-body">
					
								<BR><BR>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1" >
									
									<thead>
							<tr>
												
												<th>S.No</th>
												<th>Zone</th>
												<th>Circle</th>
												<th>Division</th>
											    <th>Sub Division</th>
												<th>Kno</th>
											    <th>Meter No</th>
											    <th>Date</th>
												<th>kWh I</th>
												<th>kWh E</th>
												<th>kVah I</th>
												<th>kVah E</th>
												
												
							</tr>
						</thead>
									
									<tbody id="listID">
						
						</tbody>
								</table>
								</div>
								</div>
								</div>
								
					
				</div>
				
			</div>
		
		<script>	
			
	function validation()
		{
		var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var sdoname=$("#subdiv").val();
		var fdate=$("#selectedFromDateId").val();
		var tdate=$("#selectedToDateId").val();
			
		if(zone==""){
			bootbox.alert("Please Select Zone");
			return false;
		} 
		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}

		if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}

		if (sdoname == "") {
			bootbox.alert("Please Select Subdivision");
			return false;
		}
		if(fdate==""){
			bootbox.alert("Please Select  From Date");
			return false;
		}
		if(tdate==""){
			bootbox.alert("Please Select  To Date");
			return false;
		}
		if(new Date(fdate)>new Date(tdate)){
			bootbox.alert("Wrong Date Inputs");
			return false;
		}
		view();
		}		
	function view(){
		var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var sdoname=$("#subdiv").val();
		var fdate=$("#selectedFromDateId").val();
		var tdate=$("#selectedToDateId").val();
		$.ajax({
			url:"./dailyConsumptionList",
			type:"get",
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdoname:sdoname,
				fdate:fdate,
				tdate:tdate
			},
			success:function(res){
				html="";
				$.each(res,function(i,v){
					html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td><td>"+v[7]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td></tr>";
				});
				//$("#listID").empty().
				clearTabledataContent('sample_1');
				$("#listID").html(html);
				loadSearchAndFilter('sample_1');
				$("#boxid").show();
			}
		});
		
	}	
	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
	</script>	
			