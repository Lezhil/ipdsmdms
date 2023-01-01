<!-- BEGIN PAGE -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>

<!-- <script src="http://code.highcharts.com/highcharts.js"></script> -->
<!-- <script src="http://github.highcharts.com/master/highcharts.src.js"></script>
<script src="http://github.highcharts.com/master/highcharts-more.js"></script> -->

<style>
.page-content .page-breadcrumb.breadcrumb>li>i {
	color: #fff;
}
</style>
<script>
	$(".page-content")
			.ready(
					function() {

						//alert('${showNotWorkingsModems}');
						if ('${showTotalModems.size()}' > 0) {
							$('#totalFeeder').show();
						}
						if ('${showWorkingsModems.size()}' > 0) {
							$('#workingFeeder').show();
						}
						if ('${showNotWorkingsModems.size()}' > 0) {
							$('#notWorkingFeeder').show();
						}
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
					 
						$('#MDMSideBarContents,#MIS-Reports,#powerStatusReportMDAS').addClass('start active ,selected');
						 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
							"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
						 showCircle();
					});
	
	
	
	function showCircle() {
		$
				.ajax({
					url : './showDistinctCircles',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						//alert(response);
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
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
		/* var zone = $('#zone').val(); */
		$
				.ajax({
					url : './showDivisions/' +circle,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
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
		/* var zone = $('#zone').val(); */
		var circle = $('#circle').val();
		$('#sdoCode').find('option').remove();
		$.ajax({
			url : './showSubdivByDiv' + '/' + circle + '/'+ division,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				$('#sdoCode').append($('<option>', {
					value : "",
					text : "Sub-Division"
				}));
				$('#sdoCode').append($('<option>', {
					value : "All",
					text : "All"
				}));

				/* var html='';
				html+="<select id='sdoCode' name='sdoCode' class='form-control select2me input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>"; */
				for (var i = 0; i < response1.length; i++) {
					//var response=response1[i];
					/* html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>"; */

					$('#sdoCode').append($('<option>', {
						value : response1[i],
						text : response1[i]
					}));
				}
				/* html+="</select><span></span>";
				$("#subdivTd").html(html); */
				//$('#subdiv').select2();
			}
		});
	}
	
	
	
	function showSubStation(subdivision)
	{
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		var division=$('#division').val();
		//alert(zone+", "+circle+" , "+division);
		$.ajax({
			url : './getSubStationsBasedOnMDAS' + '/' + zone + '/' + circle + '/' + division + '/' + subdivision,
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	success:function(response)
		    	{
	  				var html='';
		    		html+="<select id='substation' name='substation' onchange='dateFeeder()' class='form-control input-medium' type='text'><option value='noVal'>Sub-Station</option><option value='All'>ALL</option>";
		    		for (var i = 0; i < response.length; i++) {
						var resp = response[i];
					
						//var response=response1[i];
						html+="<option  value='"+resp+"'>"+resp+"</option>";
					
		    		}
					html+="</select><span></span>";
					$("#subStNames").html(html);
					$('#subdiv').select2();
		    	}
			});
	}
</script>


<style>
.alert {
	border: 1px solid transparent;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
}
</style>

<script type="text/javascript">
	function mtrDetails(mtrId) {
		alert(mtrId);
	}
</script>

<div class="page-content">

	<div class="row">

		<div class="col-xs-12"
			style="padding: 0px 10px; margin: 0px 25px 0px 0px; padding-left: 15px;">
			<label
				style='color: #000000; font-size: 24px; padding-top: 5px; float: left;'>Substation
				Power Supply Status Report</label>

		</div>
	</div>

	<div class="portlet box purple" style="margin: 10px 0px;">

		<div class="portlet-body" style="border-top: 1px solid purple;">


			<div class="row" style="margin-left: -1px;">

				<table style="width: 38%">
					<tbody>
						<tr>
							<%-- <td><select class="form-control select2me input-medium"
								name="zone" id="zone" onchange="showCircle();">
									<option value="">Zone</option>
									<option value='All'>ALL</option>
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
							</select></td> --%>

							<td id="circleTd"><select
								class="form-control select2me input-medium" id="circle"
								name="circle">
									<option value="">Circle</option>
									<option value='All'>ALL</option>
									<c:forEach items="${distinctCircles}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
							</select></td>

							<td id="divisionTd"><select
								class="form-control select2me input-medium" id="division"
								name="division">
									<option value="">Division</option>
									<option value='All'>ALL</option>
									<c:forEach items="${divisionList}" var="elements">
										<option value="${elements}">${elements}</option>
									</c:forEach>
							</select></td>

							<td id="subdivTd"><select
								class="form-control select2me input-medium" id="sdoCode"
								name="sdoCode" onchange="showSubStation(this.value)">
									<option value="">Sub-Division</option>
									<option value='All'>ALL</option>
									<c:forEach items="${subdivList}" var="sdoVal">
										<option value="${sdoVal}">${sdoVal}</option>
									</c:forEach>
							</select></td>

							<td>
								<!-- <button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getFeeder()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> --> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
							</td>
						</tr>
					</tbody>
				</table>

			</div>
			<br>
			<div class="row">
				<%-- <div class="col-sm-3">
					<div class="form-group ">
						<!-- <label >Substation Name</label> -->
						<select id="subStNames"
							class="form-control select2me input-medium"
							onchange="dateFeeder();">
							<option value="0">Sub-Station</option>
							<c:forEach items="${subStations}" var="element">
								<option value="${element}">${element}</option>

							</c:forEach>

						</select>
					</div>
				</div> --%>

				<!-- <div class="col-sm-3" id="feeder" >
							
						</div> -->



						<!-- <label>Date</label> -->
						
						<div class="col-sm-3" id="dateFed">
						<!-- <label>Date</label> -->
						<div class="input-group input-medium date date-picker"
							data-date-format="yyyy-mm-dd" data-date-viewmode="years">
							<input type="text" class="form-control input-sm"
								id='fromdate'> <span
								class="input-group-btn">
								<button class="btn default" type="button"
									style="padding-bottom: 5px; padding-top: 5px;">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
						<!-- /input-group -->

				</div>
						
				<div class="col-sm-3" id="grfBtn">
					<button type="submit" class="btn green"
						style="margin-top: 0px; padding-top: 5px; padding-bottom: 5px;"
						onclick="timeDifference();">submit</button>
				</div>
			</div>

			<br>
			<br>
			
			
			<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Power_status_report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead >
							<tr style="text-align: center;">
								<!-- <th>Zone</th> -->
								<th>Circle</th>
								<th>Division</th>
								<th>SubDivision</th>
								<!-- <th>SubStation</th> -->
								<th>Account No</th>
								<th>Consumer Name</th>
								<!-- <th>Modem IMEI</th> -->
								<th>Meter No</th>
								<th>Date</th>
								<th>Power On</th>
								<th>1 Phase</th>
								<th>3 Phase</th>
								<th>Power Off</th>
								
							</tr>
							
						</thead>
						<tbody id="updateMaster">
							
						</tbody>
					</table>
			
			
			
		</div>
	</div>
	<br />

	

</div>

<script>
	
	 var res=null; 
	 var fdName=null;
	 var fdrs=null;

	 function dateFeeder()
	 {
	 	var date= $('#subStationDate').val();
	 	var subStation= $('#subStNames').val();
	 	getDateFed();
//	 	alert(date);
	 	//alert(subStation);
	 //	
	 	/* $.ajax({
	     	url:'./getFeederEvent',
	     	type:'GET',
	     	dataType:'json',
	     	data:{subStation:subStation},
	     	asynch:false,
	     	cache:false,
	     	success:function(response)
	     	{
	     		//alert(response);
	     		
	     		var html='<div class="form-group "><label >Feeders Name</label><select  id="fdName" class="form-control input-sm" onchange="getDateFed();"><option value=0>Select Feeder</option>';
	     	       
	     		 for( var i=0;i<response.length;i++)
	 			 {
	 				
	 				html+="<option value='"+response[i]+"'>"+response[i]+"</option>"; ;
	 			 }
	 			 html+='</select>';
	 			 
	 			 $("#feeder").empty();
	 			 $("#feeder").append(html);
	     	     
	     	} 
	     		 
	     	
	 	}); */
	 }
	
	
	 function graph() {
			$("#legend").show();
			var fdr=['fdName'];
		   $(function () {
		        Highcharts.setOptions({
		            global: {
		                useUTC: true
		            }
		        });
		        $('#container').highcharts({
		            chart: {
		                type: 'bar',
		                animation: false
		            },
		            credits: { enabled: false },
		            exporting: { enabled: false },
		            title: {
				        text: ''
				    },
		            xAxis: {
		                categories: fdrs //add array of feeders ashwini
		                	 
		            },
		            /*  yAxis: {
		                 min: 30,
		                 tickInterval:30,
		                 max:1440, 
		               
			            title: {
				            text: ''
				        }
		               
		                
		            }, */
		            yAxis: {
		            	max:1440, 
		            	tickInterval:60,
		            	title: {
		                    text: 'Time (HH:MM)'
		                },
		                labels: {
		                    formatter: function () {
		                        var time = this.value;
		                        console.log(time);
		                        var hours1=parseInt(time/60); 
		                        var mins1=parseInt((parseInt(time%60))/10);
		                        return hours1 + ':' + mins1+'0';
		                    }
		                }
		            },
		            
		            legend: {
		                enabled: false,
		                reversed: false
		            },
		            plotOptions: {
		                series: {
		                    stacking: 'normal',
		                    animation: false
		                },
		                bar: {
		                    animation: false,
		                    borderWidth:0
		                }
		            },
		            series:res
		          // series: gres //set [] and $('#chart').highcharts(chartdata); ashwini
		          // series:[[{"name":"threePhase( startTime :23:30:00)","color":"GREEN","data":[30]},{"name":"threePhase( startTime :23:00:00)","color":"GREEN","data":[30]},{"name":"twoPhase( startTime :22:30:00)","color":"YELLOW","data":[30]}],[{"name":"threePhase( startTime :23:30:00)","color":"GREEN","data":[30]},{"name":"threePhase( startTime :23:00:00)","color":"GREEN","data":[30]},{"name":"twoPhase( startTime :22:30:00)","color":"YELLOW","data":[30]}]]
		           //working series:[{data:[{"name":"threePhase( startTime :23:30:00)","color":"GREEN",y:30},{"name":"threePhase( startTime :23:00:00)","color":"GREEN",y:30},{"name":"twoPhase( startTime :22:30:00)","color":"YELLOW",y:30}]},{data:[{"name":"threePhase( startTime :22:00:00)","color":"YELLOW",y:30},{"name":"threePhase( startTime :21:30:00)","color":"YELLOW",y:30},{"name":"twoPhase( startTime :21:00:00)","color":"GREEN",y:30}]}]     
		          
		        });
		    });

	} 
	

	 
	 
  
	 



function getDateFed(){
	$("#dateFed").show();
	$("#grfBtn").show();
}

function timeDifference()
{
	//alert('gh');
	
	
	var date= $('#subStationDate').val();
	var subStation= $('#subStNames').val();
	var zone=$('#zone').val();
	var circle=$('#circle').val();
	var division=$('#division').val();
	var subdiv=$('#sdoCode').val();
	var from=$('#fromdate').val();
	var to=from;
	// fdName= $('#fdName').val();
	//alert(fdName);
	//alert(date);
	
    	 if(fdName==0)
    		{
    		bootbox.alert("Select Feeder");
    		return false;
    		} 
    	if(date=='')
    		{
    		bootbox.alert("Select Date");
    		return false;
    		
    		}
    	
    	
    	$.ajax({
        	url:'./getPowerStatusReportDataMDAS',
        	type:'GET',
        	dataType:'JSON',
        	data:{
        		date:date,
        		fdName : subStation,
        		zone : zone,
        		circle : circle,
        		division : division,
        		subdiv : subdiv,
        		from : from,
        		to: to
        	},
        	asynch:false,
        	cache:false,
        	success:function(response)
        	{
        		var html="";
        		
        		for(var i=0;i<response.length;i++){
        			var val=response[i];
        			
        			html+="<tr>"
						/* +" <td>"+val.zone+"</td>"  */
						+" <td>"+val.circle+"</td>" 
						+" <td>"+val.div+"</td>" 
						+" <td>"+val.subdiv+"</td>" 
						/* +" <td>"+val.substation+"</td>"  */
						+" <td>"+val.accno+"</td>" 
						+" <td>"+val.name+"</td>" 
						/* +" <td>"+val.imei+"</td>"  */
						+" <td>"+val.mtrNo+"</td>" 
						+" <td>"+val.date+"</td>" 
						+" <td>"+val.powerOn+"</td>" 
						+" <td>"+val.phase1+"</td>" 
						+" <td>"+val.phase3+"</td>" 
						+" <td>"+val.powerOff+"</td>" 
						+" </tr>";
        			
        		}
        		
        		$('#sample_1').dataTable().fnClearTable();
	   		 	$('#updateMaster').html(html);
        		
        	},
        	complete: function()
    		{  
    			loadSearchAndFilter('sample_1');
    		} 
        });
        		
        		
        		
        		
        

	}
	
	
	
    		

</script>
<style>
.page_legend {
	margin: 20px 0;
	padding: 0 20px;
}

.page_legends div {
	float: left;
}

.legend_label {
	padding: 0 15px;
}

.page_legend h3 {
	font-size: 18px;
	margin: 15px 0;
}

.page_legend h3 {
	font-size: 18px;
	margin: 15px 0;
}

.page_legends {
	display: table;
	width: 100%
}

.legends {
	width: 30px;
	height: 20px
}

.green_legend {
	background: #00A439;
}

.orange_legend {
	background: #ffa500;
}

.yellow_legend {
	background: #FFFD01;
}

.red_legend {
	background: #f00;
}

.gray_legend {
	background: #CCC;
}
</style>