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
		 loadSearchAndFilter('sample_2');
		 $('#MDMSideBarContents,#reportsId,#loadsurveycount').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#zone").val('noVal').trigger("change");
		});
	function showCircle(zone)
	{
		$.ajax({
		    	url:'./showCircleloadavailability'+'/'+zone,
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
		    	url:'./showDivisionloadavailability'+'/'+zone+'/'+circle,
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
		    	url:'./showSubdivByDivloadavailability'+'/'+zone+'/'+circle+'/'+division,
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

    function getloadavailabilityreport() {
    	
        var zone=$("#zone").val();
		var circle=$("#circle").val();
		var division=$("#division").val();
		var subdiv=$("#sdoCode").val();
		var fdate=$("#fromDate").val();
		var tdate=$("#toDate").val();
		
		if(zone=='noVal' || zone==null){
			bootbox.alert('Please Select zone');
			return false;
		} 
		else if(circle=='noVal'||circle==null){
			bootbox.alert('Please Select circle');
			return false;
		}
		else if(division=='noVal'||division==null){
			bootbox.alert('Please Select division');
			return false;
		}
		else if(subdiv==''||subdiv==null){
			bootbox.alert('Please Select subdivision');
			return false;
		}
		else if(fdate==''||fdate==null){
			bootbox.alert('Please Select fromdate');
			return false;
		}
		else if(tdate==''||tdate==null){
			bootbox.alert('Please Select todate'); 
			return false;
		}
		
		$('#imageee').show();
	    $.ajax({
		url : './getAvailabilityReport',
		type :'GET',
		data : {
			zone:zone,
			circle:circle,
			division:division,
			sdoCode:subdiv,
			fromDate:fdate,
			toDate:tdate
		},
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			if (response.length != 0) {
			var html = "";
			for (var i = 0; i < response.length; i++) 
			{
			var resp = response[i];
			var srno=i+1;

				if(resp[4]==null || resp[4]=='null')
				{
				  resp[4]='';						
				}
				if(resp[5]==null || resp[5]=='null')
				{
				  resp[5]='';						
				}
				if(resp[6]==null ||  resp[6]=='null')
				{
				  resp[6]='';						
				}
			
				
				html+="<tr><td>"+srno+"</td><td>"+resp[0]+"</td><td>"+resp[1]+"</td><td>"+resp[2]+"</td><td>"+resp[3]+"</td><td>"+resp[4]+"</td><td>"+resp[5]+"</td><td>"+resp[6]+"</td><td>"+(resp[7])+"</td><td>"+resp[8]+"</td><td>"+resp[9]+"</td></tr>"
				}
				 $('#sample_2').dataTable().fnClearTable(); 
				$('#TbodyID').html(html);
				 loadSearchAndFilter('sample_2');

			} 
			
			else {
				alert("No data");
				 $('#sample_2').dataTable().fnClearTable(); 
			} 

		}
	    
       });
	    function getSummaryReport() {
	    	
	    	
	    	var fromDate=$('#fromDate').val();
	    	var toDate=$('#toDate').val();
	    	$('#loadSummary').empty();
	    	//alert(make+" -- "+dlms+" -- "+mtrno);
	    	
	    	
	    		$('#imageee').show();
	    		 $.ajax({
	    				url : './getLoadSummaryReport',
	    				type :'GET',
	    				data : {
	    					fromDate : fromDate,
	    					toDate : toDate,
	    				},
	    				dataType:'json',
	    		    	asynch:false,
	    		    	cache:false,
	    		    	success:function(data)
	    		    	{
	    		    		var html="";
	    		   		 	for(var i=0;i<data.length; i++)
	    					{
	    						var resp=data[i];
	    						//alert(JSON.stringify(resp));
	    						 html+="<tr>"
	    							 +"<td >"+resp[0]+"</td>"
	    							 +"<td >"+resp[6]+"</td>"
	    							 +"<td >"+resp[7]+"</td>"
	    							 +"<td >"+resp[8]+"</td>"
	    							 +"<td >"+resp[9]+"</td>";
	    							 
	    						html+=" </tr>";
	    			   		}
	    		   		 //$('#discomwiseNotCom').empty();
	    		   		$('#sample_2').dataTable().fnClearTable();
	    		   		$('#imageee').hide();
	    		   		$('#basic').modal('show'); 
	    		   		$("#loadSummary").append(html);
	    		    	},
	    				complete: function()
	    				{  
	    					loadSearchAndFilter('sample_2');
	    				}  
	    			  }); 
	    	}
	    	
    
    }
    
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Load Survey Availability Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
					<div class="portlet-body">
					
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
							<th>Date Selections:</th>
							<td><div class="col-md-3" style="margin-left: -3%;">
							<div class="input-group input-large date-picker input-daterange "
								data-date-format="yyyy-mm-dd">

								<input type="text" autocomplete="off" placeholder="From Date"
									class="form-control" data-date-format="yyyy-mm-dd"
									data-date-viewmode="years" name="fromDate" id="fromDate">
									
								<span class="input-group-addon">To</span> <input type="text"
									autocomplete="off" placeholder="To Date" class="form-control"
									data-date-format="yyyy-mm-dd" data-date-viewmode="years"
									name="toDate" id="toDate">
							</div>
						</div>
                          </td>
							<td><button type="button" id="dataview" class="btn yellow"
									onclick="return getloadavailabilityreport()">
									<b>view</b>
								</button></td>
								<!-- <td style="padding-left: 15px;">
											<button  onclick="return getSummaryReport();"  class="btn green">
												<b>Summary</b>
											</button>
										
										
											<a class="btn red btn-outline sbold" data-toggle="modal" href="#basic"> Summary </a>
										</td> -->
							<!-- <td>
					<div class="btn-group pull-right" style="margin-left: 458px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_2', 'Load Survey Availability Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</td> -->
					
						</tr>
					</table>
					<br>
			<div class="tabbable tabbable-custom">
				<table class="table table-striped table-bordered table-hover"
					id="sample_2">
					<thead>
						<tr>
						<th>Sl No</th>
								<th>Zone</th>
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<th>Substation</th>
								<th>Feeder Name</th>
								<th>Meter No</th>
								<th>Date</th>
								<th>Load Survey Count</th>
								<th>Load Survey Interval</th>
								
								
						</tr>

					</thead>
				 <tbody id="TbodyID">
						<%-- <c:set var="count" value="1" scope="page" />
						<c:forEach var="element" items="${reslutList}">
							<tr>


								<td>${count}</td>
								<td>${element[0]}</td>
								<td>${element[1]}</td>
								<td>${element[2]}</td>
								<td>${element[3]}</td>
								<td>${element[4]}</td>
								<td>${element[5]}</td>
								<td>${element[0]}</td>
								<td>${element[7]}</td>
								<td>${element[8]}</td>
								<td>${element[7]}</td>
								
								<c:set var="count" value="${count+1}" scope="page" />

							</tr>
						</c:forEach> --%>

					</tbody> 
				</table>
</div>
			</div>
		</div>
		<div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
		</div>
	</div>
	</div>
	
	<script>
	 $('.input-daterange').datepicker({
			autoclose : true,
			endDate : '+0d'
		      });
</script>
