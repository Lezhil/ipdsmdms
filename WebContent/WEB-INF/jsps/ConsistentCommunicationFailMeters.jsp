<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		 $("#selectedDateId").val('${month}');
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				
				
				 $('#MDMSideBarContents,#reportsId,#ConsistentCommunicationFailMeters').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
	function showCircle(zone)
	{
		$.ajax({
		    	url:'./showCircleMDAS'+'/'+zone,
		    	type:'GET',
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<label class='control-label'>Circle</label><select id='circle' name='circle' onchange='showDivision(this.value)' class='select2_category form-control' ><option value='All'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#circleTd").html(html);
					$('#circle').select2();
		    	}
			});
	}
	
	function showDivision(circle)
	{
		var zone=$('#zone').val();
		$.ajax({
		    	url:'./showDivisionMDAS'+'/'+zone+'/'+circle,
		    	type:'GET',
		    	
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<label class='control-label'>Division</label><select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='select2_category form-control' ><option value='All'>ALL</option>";
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
		    	url:'./showSubdivByDivMDAS'+'/'+zone+'/'+circle+'/'+division,
		    	type:'GET',
		    	
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<label class='control-label'>Sub Division</label><select id='sdoCode' name='sdoCode' class='select2_category form-control' type='text'><option value='All'>ALL</option>";
					for( var i=0;i<response1.length;i++)
					{
						
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#subdiv').select2();
		    	}
			});
	}


	</script>
<div class="page-content">
	<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-edit"></i>Consistent Communication Fail Meters:
							</div>
						</div>
						<div class="portlet-body">	
						<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 1px;">
					<div id="excelExportDiv2">
					<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport2" 
									onclick="tableToExcel2('sample_2', 'Consistent Fail Meters')">Export
										to Excel</a></li>
							</ul>
										</div>
										</div></div>
						<form action="#" class="horizontal-form">
											<div class="form-body">
												<!-- <h3 class="form-section">Person Info</h3> -->
												<div class="row">
													<div class="col-md-3">
														<div class="form-group">
															<label class="control-label">Zone</label>
															
															<select class="select2_category form-control" name="zone" id="zone" onchange="showCircle(this.value);" data-placeholder="Choose a Zone" tabindex="1">
																<option value='All'>ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
															</select>
														</div>
													</div>
													<!--/span-->
													<div class="col-md-3">
														<div class="form-group" id="circleTd">
															<label class="control-label">Circle</label>
															
															<select class="select2_category form-control" id="circle"
									name="circle"  data-placeholder="Choose a Circle" tabindex="1">
																
															</select>
															
														</div>
													</div>
													<!--/span-->
											
												
													<div class="col-md-3">
														<div class="form-group" id="divisionTd">
															<label class="control-label">Division</label>
															
															<select class="select2_category form-control" id="division"
									name="division"  data-placeholder="Choose a Division" tabindex="1">
																
															</select>
															
														</div>
													</div>
													
													<!--/span-->
													
													<div class="col-md-3">
														<div class="form-group" id="subdivTd">
															<label class="control-label">Sub Division</label>
															
															<select class="select2_category form-control" id="sdoCode"
									name="sdoCode"  data-placeholder="Choose a SubDivision" tabindex="1">
																
															</select>
															
														</div>
													</div>
													<!--/span-->
												</div>    
												<div class="row">
													<div class="col-md-3">
														<div class="form-group" id="">
															<label class="control-label ">From Date</label>
										
											<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedFromDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
											<!-- /input-group -->
									
															
														</div>
													</div>
													<!--/span-->
													<div class="col-md-3">
														<div class="form-group" id="">
															<label class="control-label ">To Date</label>
										
											<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="">
																<input  type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedToDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
											
															
														</div>
													</div>
													<div class="col-md-3"><div class="form-group">
													<label class="control-label "></label><div>
												<button type="button" onclick="validation()" class="btn green">View</button> </div>
											</div></div>
											
											
												</div>
												
												
									
												
												
												
												
												
												
											
											</div>
											
										</form>
									
										
										
						
				
					<br>
					
									
						<table class="table table-striped table-hover table-bordered" id="sample_2" style="display: none">
					<thead>
					
				
					
					
					
						<tr>
						<th>S.no</th>
						<th>Circle</th>
							<th>Division</th>
							<th>Sub Division</th>
							<th>Consumer Name</th>
							<th>Account Number</th>
							<th>Meter Number</th>
							
							



							<!-- <th rowspan="2">SixMonthAvg</th>
										<th rowspan="2">TwelveMonthAvg</th> -->

						</tr>

					</thead>
					<tbody id="faultData">
						
					</tbody>
					</table>
					</div>
					</div>
				
	
	</div>
	</div>
	
					
				
	
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
	
		 dataResponse();
	
	}
	
	
	function dataResponse(){
		var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var subdiv=$("#sdoCode").val();
		if(zone=='All'){
			zone="%";
		}
		if(circle=='All'||circle==null){
			circle="%";
		}
		if(division=='All'||division==null){
			division="%";
		}
		if(subdiv=='All'||subdiv==null){
			subdiv="%";
		}
		$.ajax({
			url:'./consistentCommunicationFailMetersList',
			type:'get',
			data:{
				zone:zone,
				circle:circle,
				division:division,
				sdoCode:subdiv
			},
		success:function(resp){
			html="";
			$.each(resp,function(i,v){
				html+="<tr>";
				html+="<td>"+(i+1)+"</td>";
				html+="<td>"+v[0]+"</td>";
				html+="<td>"+v[1]+"</td>";
				html+="<td>"+v[2]+"</td>";
				html+="<td>"+v[3]+"</td>";
				html+="<td>"+v[4]+"</td>";
				html+="<td>"+v[5]+"</td>";
				html+="</tr>";
			});
			clearTabledataContent('sample_2');
			$("#faultData").html(html);
			$("#sample_2").show();
			 loadSearchAndFilter("sample_2");
			
		}
			
		});
	}
	
	</script>
	
	
	
	
	
