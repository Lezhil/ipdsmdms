<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
	   	        
	   		  $("#selectedDateId").val('${month}');
	   		  
	   		    $("#meterVal").val('${meterno}');
	   		   
	    
	   			App.init();
				TableEditable.init();
				FormComponents.init();
				loadSearchAndFilter('sample_1');
				
				$('#MDASSideBarContents,#MIS-Reports,#rtcReport').addClass('start active ,selected');
				 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
	  				"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	  		
     	});
	
	
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
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Division</option><option value='All'>ALL</option>";
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
			url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/'
			+ division,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>";
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
	}
	
	
</script>
<div class="page-content" >

<div class="row">
				<div class="col-md-12">
				
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>RTC</div>
							<div class="tools">
								
									
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								
							</div>
						</div>					
											
						<div class="portlet-body">
								<form method="post" id="meterMasterForm" action="./rtcList">
										<table>
										<tr>
								<td id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle" onchange="showDivision(this.value);">
										<option value='noVal'>Circle</option>
										<option value='All'>ALL</option>
										<c:forEach items="${circleList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
										<option value='noVal'>Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${divisionList}" var="elements">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
										<option value='noVal'>Sub-Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select></td>
							</tr>
										<tr><td>Select Month</td><td>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="selectedDateName" id="selectedDateId"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td>
																<!-- <td><input type="text" class="form-control" placeholder="Enter MeterNumber" id="mtr_no" name="meter number"/> -->
																
																<td>
																<div>
															<button name="search"   type="submit" class="btn green" id="getSearch" >view</button>
		                                   </div>
																</td>
																
																</tr>
																
										</table>
										
											
											
											
	                                	<!-- PopUp for DatePicker only for Load Survey -->
		
										
									 <!-- End Popup -->		
										
										</form>
									
								
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
					<!-- BEGUN METERMASTER TABLE DATA -->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Meters List</div>
							
						</div>					
											
						<div class="portlet-body">
					
								<BR><BR>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1" >
									
									<thead>
							<tr>
												<th>SlNo</th>
												<th hidden="true">mtr</th>
												<th>CIRCLE</th>
												<th>DIVISION</th>
												<th>SUBDIVISION</th>
											    <th>ADDRESS</th>
												<th>METERNO</th>
											    <th>METER CLOCK</th>
												<th>DEVICE CLOCK</th>
												
							</tr>
						</thead>
									
									<tbody>
						<c:set var="count" value="1" scope="page"> </c:set>
							<c:forEach var="element" items="${zeroConcmp}">
								<tr>
								   <td >${count}</td>
								    <td hidden="true">${element[0]}</td>
								     <td>${element[1]}</td>
									 <td>${element[2]}</td> 
									 <td>${element[3]}</td>  
									 <td>${element[4]}</td>  
									 <td>${element[5]}</td> 
									 <td>${element[6]}</td>
									 <td>${element[7]}</td>
									 </tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</tbody>
								</table>
								</div>
								</div>
								</div>
								
					
				</div>
				
			</div>
			
			
			
			
			
			