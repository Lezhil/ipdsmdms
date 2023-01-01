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
						Index.initMiniCharts();
						TableEditable.init();
						FormComponents.init();
					 
						$('#dash-board2').addClass('start active ,selected');
						$(
								'#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');

					});
	
	
	
	function showCircle(zone) {
		$
				.ajax({
					url : './showCircle' + '/' + zone,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
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
		var zone = $('#zone').val();
		$
				.ajax({
					url : './showDivision' + '/' + zone + '/' + circle,
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
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		$('#sdoCode').find('option').remove();
		$.ajax({
			url : './showSubdivByDiv' + '/' + zone + '/' + circle + '/'
					+ division,
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
			url : './getSubStationsBasedOn' + '/' + zone + '/' + circle + '/' + division + '/' + subdivision,
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
				Power Supply Scenario</label>

		</div>
	</div>

	<div class="portlet box purple" style="margin: 10px 0px;">

		<div class="portlet-body" style="border-top: 1px solid purple;">


			<div class="row" style="margin-left: -1px;">

				<table style="width: 38%">
					<tbody>
						<tr>
							<td><select class="form-control select2me input-medium"
								name="zone" id="zone" onchange="showCircle(this.value);">
									<option value="">Zone</option>
									<option value='All'>ALL</option>
									<c:forEach var="elements" items="${zoneList}">
										<option value="${elements}">${elements}</option>
									</c:forEach>
							</select></td>

							<td id="circleTd"><select
								class="form-control select2me input-medium" id="circle"
								name="circle">
									<option value="">Circle</option>
									<option value='All'>ALL</option>
									<c:forEach items="${circleList}" var="elements">
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
				<div class="col-sm-3">
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
				</div>

				<!-- <div class="col-sm-3" id="feeder" >
							
						</div> -->



				<div class="col-sm-3" id="dateFed" hidden="true">
					<div class="form-group">
						<!-- <label>Date</label> -->
						<div class="input-group input-medium date date-picker"
							data-date-format="dd-mm-yyyy" data-date-viewmode="years">
							<input type="text" class="form-control input-sm"
								id='subStationDate' readonly> <span
								class="input-group-btn">
								<button class="btn default" type="button"
									style="padding-bottom: 5px; padding-top: 5px;">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
						<!-- /input-group -->
					</div>

				</div>
				<div class="col-sm-3" id="grfBtn" hidden="true">
					<button type="submit" class="btn green"
						style="margin-top: 0px; padding-top: 5px; padding-bottom: 5px;"
						onclick="timeDifference();">submit</button>
				</div>
			</div>

		</div>
	</div>
	<br />

	<div style="margin: 10px 0px;">

		<div id="container"
			style="min-width: 310px; max-width: 100%; margin: 0 auto"></div>

	</div>


	<div class="page_legend" hidden="true" id="legend">

		<div class="page_legends">
			<div class="green_legend legends"></div>
			<div class="legend_label">Three Phase Running</div>
			<!-- <div class="orange_legend legends"></div>
			<div class="legend_label">Two Phase Running</div> -->
			<div class="yellow_legend legends"></div>
			<div class="legend_label">Single Phase Running</div>
			<div class="red_legend legends"></div>
			<div class="legend_label">Power Failure</div>
			<div class="gray_legend legends"></div>
			<div class="legend_label">No Data</div>

		</div>
	</div>

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
        	url:'./getTimeAndPhasesMDAS',
        	type:'GET',
        	dataType:'text',
        	data:{
        		date:date,
        		fdName : subStation,
        		zone : zone,
        		circle : circle,
        		division : division,
        		subdiv : subdiv,
        	},
        	asynch:false,
        	cache:false,
        	success:function(response)
        	{
        		response=$.parseJSON(response);
        		 if(res=="")
     	    	{
     	    	bootbox.alert("This Date as no data,Select another date ")
     	    	return false;
     	    	}
        		 else{
        		 fdrs=response[0].fdrArray;
        			res=response[0].dataArray;
        			graph();
        		}
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