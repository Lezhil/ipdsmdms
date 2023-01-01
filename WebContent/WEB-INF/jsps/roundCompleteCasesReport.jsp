<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<script  type="text/javascript">
	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();
		 $('#MDMSideBarContents,#MIS-Reports,#roundCompleteCasesReport').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		});
	function showCircle(zone)
	{
		$.ajax({
		    	url:'./showCircleMDAS'+'/'+zone,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value='noVal'>Circle</option><option value='All'>ALL</option>";
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
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		$.ajax({
		    	url:'./showSubdivByDivMDAS'+'/'+zone+'/'+circle+'/'+division,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium'  type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>";
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
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body">

				<form action="" id="statusId" method="POST">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-edit"></i>Round Complete Cases Report:
							</div>
						</div>
					</div>
					<table style="width: 38%">
						<tbody>
							<tr>
								<td><select class="form-control select2me input-medium"
									name="zone" id="zone" onchange="showCircle(this.value);">
										<option value="noVal">Zone</option>
										<option value='All'>ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></td>

								<td id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle">
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

								<td id="subdivTd">
							<!-- 	<select id="filterProjectId" name="projectName" placeholder="select project" name="filterProjectId" class="form-control select2" type="text" multiple="multiple"> -->
								<select class="form-control select2me input-medium" id="sdoCode"  name="sdoCode" id="sdoCode" type="text">
										<option value='noVal'>Sub-Division</option>
										<option value='All'>ALL</option>
										<c:forEach items="${subdivList}" var="sdoVal">
											<option value="${sdoVal}">${sdoVal}</option>
										</c:forEach>
								</select></td>
								
								
								<!-- <td>
								<button type= "button" viewFdrOnMap" onclick="return viewOnMap();" name="viewFdrOnMap" class="btn yellow"><b>View</b></button>
								<button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
								</td> -->
							</tr>
						</tbody>
					</table>
					<br>
					<table>
						<tr>
							<td>Rdngmonth :</td>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	

							<td><button type="submit" id="dataview" class="btn yellow"
									formaction="./roundCompleteCasesReportData"
									formmethod="post">
									<b>Generate</b>
								</button></td>
						</tr>
					</table>
					<br>
				</form>
			</div>
			<div class="tabbable tabbable-custom">
				<table class="table table-striped table-bordered table-hover"
					id="sample_2">
					<thead>
						<tr>
						<th>Sil No</th>
							<!-- <th>Meter No</th> -->
							
							<th>Subdivision</th>
							<th>KNo</th>
							<th>Account Number</th>
							
							<th>Consumer Name</th>
							<th>Consumer Status</th>

							<th>Meter Serial No</th>
							
							<th>Current kWh</th>
							<th>Previous kWh</th>
							<th>Current kVAh</th>
							<th>Previous kVAh</th>
							
							<th>Kwh Round Complete </th>
							<th>kVAh Round Complete </th>
							
							



							<!-- <th rowspan="2">SixMonthAvg</th>
										<th rowspan="2">TwelveMonthAvg</th> -->

						</tr>

					</thead>
					<tbody>
						<c:set var="count" value="1" scope="page" />
						<c:forEach var="element" items="${reslutList1}">
							<tr>


								<td>${count}</td>
								<%-- <td>${element[0]}</td> --%>
								<td>${element[10]}</td>
								<td>${element[12]}</td>
								
								<td>${element[11]}</td>
								<td>${element[13]}</td>

								<td>${element[14]}</td>
								
								<td>${element[8]}</td>
								
								<td>${element[0]/1000}</td>
								<td>${element[1]/1000}</td>
								<td>${element[3]/1000}</td>
								<td>${element[4]/1000}</td>
								
								<td>${element[6]}</td>
								<td>${element[7]}</td>
								
								<c:set var="count" value="${count+1}" scope="page" />

							</tr>
						</c:forEach>

					</tbody>
				</table>

			</div>
		</div>
		</div>
	</div>
