<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	$(".page-content").ready(
					function() {

						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						loadSearchAndFilter('sample_serviceOrders');
						//$('#meterChange').addClass('start active ,selected');
						$('#MDMSideBarContents,#exceptionmanagement').addClass(
								'start active ,selected');
						$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');
						loadSearchAndFilter('sample_serviceOrders');
					});
	$(document).ready(function() {
		$('.js-example-basic-multiple').select2();
	});
	
	
</script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		 $("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				
				
				$('#MDMSideBarContents,#MIS-Reports,#dailyConsumption').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
	function showCircle(zone)
	{
		var zone="All";
		$.ajax({
			url : './showCircleMDM' + '/' + zone ,
		    	type:'GET',
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value='%'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					
					$("#circle").html(html);
					$('#circle').select2();
		    	}
			});
	}
	
	function showDivision(circle)
	{
		var zone="All";
		$.ajax({
			url : './showDivisionMDM' + '/' + zone + '/' + circle,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='%'>ALL</option>";
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
		var circle=$('#circle').val();
		var zone="All";
		$.ajax({
			url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/'+ division,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response1)
		    	{alert(response1);
	  			var html='';
		    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value='%'>ALL</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#sdoCode').select2();
		    	}
			});
	}
	
	
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Service Order
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div>
						<a class="btn btn-lg green" data-toggle="modal" href="#wide" onclick="eventsConfigList()">Events
							Configuration</a>&nbsp;&nbsp;<a class="btn btn-lg green"
							data-toggle="modal" href="#wide">Alarm Configuration</a>&nbsp;&nbsp;<a
							class="btn btn-lg green" data-toggle="modal" href="#wide">Communication
							Configuration</a>
					</div>
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<!-- <li><a href="#" id="print">Print</a></li> -->
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_serviceOrders', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					<div>
				
					<table >
					<tbody>
						<tr>
								<th class="block">Zone&nbsp;:</th>
								<th id=""><select class="form-control select2me input-medium" id="zone" name="circle" onchange="showCircle(this.value);">
									<option value=""></option>
									<option value="JVVNL">JVVNL</option>
								</select></th>
										
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select class="form-control select2me input-medium" id="circle" name="circle" onchange="showDivision(this.value);"> 
								<option value='%'>ALL</option>
								</select></th>
								
                                <th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select class="form-control select2me input-medium" id="division" name="division" >
								<option value='%'>ALL</option>
								</select></th>
								
                                <th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select class="form-control select2me input-medium" id="sdoCode" name="sdoCode">
								<option value='%'>ALL</option>
								</select></th>
								
							</tr>
						
							 <tr>
									 <th class="block">From&nbsp;Date&nbsp;:</th>
									 <th><div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
										<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId">
										<span class="input-group-btn"><button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
										</span></div></th>
										
										<th class="block">To&nbsp;Date&nbsp;:</th>
										<th><div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
											<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
											<span class="input-group-btn"><button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
											</span></div></th>
									
										<th colspan="4" >
										<div class="col-md-offset-4 col-md-8">                        
											<button type="button" onclick="validation()" class="btn green">View</button> 
										</div>
										</th>
								</tr> 
								
								</tbody>
							</table>
						</div>
										

					<table class="table table-striped table-hover table-bordered"
						id="sample_serviceOrders" >
						<thead>
						
							<tr>					
								<th>ID</th>
								<th>AccNo</th>
								<th>MeterNo</th>
								<th>Kno</th>
								<th>Occured Time</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>Sdocode</th>
								<th>SO Type</th>
								<th>Request Type</th>
								<th>Comments</th>
								<th>Status</th>

							</tr>
							
						</thead>
						<tbody id="serviceOrderTbodyId">
							<%-- <c:set var="count" value="1" scope="page">
							</c:set>
							<c:forEach var="element" items="${ServiceOrder}">
								<tr>
									<td>${count}</td>
									<td>${element.accno}</td>
									<td>${element.meterno}</td>
									<td>${element.kno}</td>
									<td>${element.transaction_time}</td>
									<td>${element.circle}</td>
									<td>${element.division}</td>
									<td>${element. subdivision}</td>
									<td>${element.sdocode }</td>
									<td>${element.so_type}</td>
									<td>${element.request_type}</td>
									<td>${element.comments}</td>
									<td>${element.status }</td>

								</tr>
								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach> --%>
						</tbody>
					</table>
					
					
				</div>
				</div>
				
		</div>
		
	</div>
	
<div class="modal fade" id="wide" tabindex="-1" role="basic" aria-hidden="true">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			
			<div class="modal-body">
				<div class="portlet box grey">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-cog"></i>Events
						</div>
						<div class="actions">
							<a href="#"  onclick="addEventConfig()" class="btn blue"><i class="fa fa-pencil"></i> Add</a>
							<div class="btn-group">
								
							</div>
						
						</div>
					</div>
					<div class="portlet-body">
					<div class="tab-pane" id="tab_1">
								<div class="portlet box blue" id="eventconconf" style="display: none">
									<div class="portlet-title">
										<div class="caption"><i class="fa fa-reorder"></i>Add Events </div>
										<div class="tools">
											<a href="javascript:;" class="collapse"></a>
											<a href="#portlet-config" data-toggle="modal" class="config"></a>
											<a href="javascript:;" class="reload"></a>
											<a href="javascript:;" class="remove"></a>
										</div>
									</div>
									<div class="portlet-body form">
										<!-- BEGIN FORM-->
										<form action="#" class="horizontal-form">
											<div class="form-body">
												
												<div class="row">
													<div class="col-md-6">
														<label class="control-label">Event Name</label>
															<select
								class="js-example-basic-multiple placeholder-no-fix input-large"
								id="eventID" multiple="multiple" autofocus="autofocus">
									

							</select>
														
													</div>
													<!--/span-->
													<div class="col-md-6">
													<label class="control-label">Activation Date</label>
														
											<input class="form-control form-control-inline input-medium date-picker" id="adate"  size="16" data-date-format="dd-mm-yyyy" type="text" value="" />
											
										
													</div>
													<!--/span-->
												</div>
												    
												
											
											</div>
											<div class="form-actions right">
												<button type="button" class="btn default">Cancel</button>
												<button type="button" onclick="eventConfigSet()" class="btn blue"><i class="fa fa-check"></i> Save</button>
											</div>
										</form>
										<!-- END FORM--> 
									</div>
									
								</div>				
					<table class="table table-striped table-bordered table-hover"
							id="sample_1">
							 <thead>
								<tr>


									<th>Event Description</th>
									<th>Account No</th>
									<th>Meter No</th>
									
								</tr>
							</thead> 
							 <tbody>  
                           <c:set var="count" value="1" scope="page">
							</c:set>
							<c:forEach var="element" items="${ServiceOrder}">
								<tr>
									<td>${count}</td>
									<td>${element.accno}</td>
									<td>${element.meterno}</td>
									<td>${element.kno}</td>
									<td>${element.transaction_time}</td>
									<td>${element.circle}</td>
									<td>${element.division}</td>
									<td>${element. subdivision}</td>
									<td>${element.sdocode }</td>
									<td>${element.so_type}</td>
									<td>${element.request_type}</td>
									<td>${element.comments}</td>
									<td>${element.status }</td>

								</tr>
								<c:set var="count" value="${count + 1}" scope="page" ></c:set>
							</c:forEach>

							</tbody> 
						</table> 
						</div>
						</div>
						</div>
		
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">Close</button>
				<button type="button" class="btn blue">Save changes</button>
			</div>
			<div class="col-md-6 col-sm-12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->

				<!-- END EXAMPLE TABLE PORTLET-->
			</div>
		</div>
		<!-- /.modal-content -->
		
	</div>
	<!-- /.modal-dialog -->

</div>
</div>

<script>
	
	function addEvent() {
		$.ajax({
			type : "GET",
			url : "./inactiveEventsList",

			success : function(response) {
				var html = " " ;
				if (response != null) {
					$.each(response,function(i,value){
						html += " <option value='"+value[0]+"'>" + value[1] + "</option>" ;
					});
					 
							
						
					$("#eventID").empty();
					$("#eventID").html(html);
				}
			}
		});
	}
	function eventsConfigList() {
		$.ajax({
			type : "GET",
			url : "./eventsConfigList",

			success : function(response) {
				var html = " " ;
				if (response != null) {
					$.each(response,function(i,value){
						html+="<tr><td>"+value[1]+"</td><td>"+value[2]+"</td><td>"+value[3]+"</td></tr>"
					});
					 
							
						
					$("#eventConfigId").empty();
					$("#eventConfigId").html(html);
				}
			}
		});
	}
	function addEventConfig(){
		
		 addEvent();
		$("#eventconconf").show();
	}
	function eventConfigSet() {
		var event=$("#eventID").val();
		var adate=$("#adate").val();
		$.ajax({
			type : "GET",
			url : "./eventsConfigSet",
             data:{
            	  event:event,  
            	adate:adate
            }, 
			success : function(response) {
				
			}
		});
	}

	function feedBack(param) {

		if (param == "0") {

		} else {
			bootbox.alert("FeedBack Updated As -- " + param);
		}
		
		
		
	

	}
</script>

<script>	
			
	function validation()
		{
		var zone=$("#zone").val();
			
			var fdate=$("#selectedFromDateId").val();
			var tdate=$("#selectedToDateId").val();
		  	
			if(zone=="")
				{
				bootbox.alert("Please Select Zone");
				return false;
				}
			

			if(fdate=="")
				{
					bootbox.alert("Please Select  From Date");
					return false;
				}
			if(tdate=="")
			{
				bootbox.alert("Please Select  To Date");
				return false;
			}
			if(new Date(fdate)>new Date(tdate))
			{
				bootbox.alert("Wrong Date Inputs");
				return false;
			}
		view();
		}		
	function view(){
		var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var sdoname=$("#sdoCode").val();
		var fdate=$("#selectedFromDateId").val();
		var tdate=$("#selectedToDateId").val();
		clearTabledataContent('sample_serviceOrders');
		$.ajax({
			url:"./getdata",
			type:"get",
			dataType:"JSON",
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdoname:sdoname,
				fdate:fdate,
				tdate:tdate
			},
			success:function(res)
			{
				var html="";
				for (var i = 0; i < res.length; i++) 
				{
					var element=res[i];
					html+='<tr><td>'+(i+1)+'</td>'
					+'<td>'+element.accno+'</td>'
					+'<td>'+element.meterno+'</td>'
					+'<td>'+element.kno+'</td>'
					+'<td>'+ moment(element.transaction_time).format('YYYY-MM-DD')+'</td>'
					+'<td>'+element.circle+'</td>'
					+'<td>'+element.division+'</td>'
					+'<td>'+element.subdivision+'</td>'
					+'<td>'+element.sdocode+'</td>'
					+'<td>'+element.so_type+'</td>'
					+'<td>'+element.request_type+'</td>'
					+'<td>'+element.comments+'</td>'
					+'<td>'+element.status+'</td></tr>'
				}
				
				$("#serviceOrderTbodyId").html(html);
				
				/* $.each(res,function(i,v){
					html+="<tr><td>"+(i+1)+"</td><td>"+v[0]+"</td><td>"+v[1]+"</td><td>"+v[2]+"</td><td>"+v[3]+"</td><td>"+v[4]+"</td><td>"+v[5]+"</td><td>"+v[6]+"</td><td>"+v[7]+"</td><td>"+v[8]+"</td><td>"+v[9]+"</td><td>"+v[10]+"</td></tr>";
				});
				//$("#listID").empty(). */
				//$("#eventID").html(html);
				
				//$("#boxid").show();
			},
			complete:function(res)
			{
				loadSearchAndFilter('sample_serviceOrders');
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


