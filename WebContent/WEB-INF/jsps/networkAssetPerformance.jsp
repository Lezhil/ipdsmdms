<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"	type="text/javascript"></script>
<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script src="<c:url value='/highcharts/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/exporting.js'/>" type="text/javascript"></script>
<script src="<c:url value='/highcharts/export-data.js'/>"type="text/javascript"></script>
<script src="<c:url value='/highcharts/accessibility.js'/>" type="text/javascript"></script>




<!-- <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script> 
<script src="https://code.highcharts.com/modules/exporting.js"></script>
-->

<style>
#chart_2 {
	height: 700px;
}

.highcharts-figure, .highcharts-data-table table {
	min-width: 560px;
	max-width: 1020px;
	margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 1000px;
}

.highcharts-data-table caption {
	padding: 1em 0;
	font-size: 1.2em;
	color: #555;
}

.highcharts-data-table th {
	font-weight: 600;
	padding: 0.5em;
}

.highcharts-data-table td, .highcharts-data-table th,
	.highcharts-data-table caption {
	padding: 0.5em;
}

.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even)
	{
	background: #f8f8f8;
}

.highcharts-data-table tr:hover {
	background: #f1f7ff;
}
</style>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#MIS-Reports").addClass('start active , selected');
						$("#month").val('');
						var zone = '%';
						//showCircle(zone);
						//$('#sample1').hide();
						//$('#pagediv').hide();
						
						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						TableManaged.init();
						
						$('#mdmDashBoardId,#reportsId,#networkId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						loadSearchAndFilter('sample1');
						loadSearchAndFilter1('sample2');
						/* loadSearchAndFilter('sample_2');
						loadSearchAndFilter('sample_3'); */
						//getConsumercategory();
						$('#Consumer').click();
						$('#Consumer').click();

					});
</script>

<script>
var zone='%';
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
                   // html+="<select id='circle' name='circle' onchange='showTownNameandCode(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                    html+="<select id='LFcircle' name='LFcircle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
                   
                    for( var i=0;i<response.length;i++)
                    {
                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
                    }
					html+="</select><span></span>";
                    $("#LFcircle").html(html);
                    $('#LFcircle').select2();
                }
            });
    }

     function showDivision(circle) {
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
                            html += "<select id='sdoCode' name='sdoCode'  class='form-control input-medium'  onchange='showTownBySubdiv(this.value)' type='text'><option value=''>Select Sub Division</option><option value='%'>ALL</option>";
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

     function showTownBySubdiv(subdiv) {
	     
	      var circle = $('#circle').val();
	      var division = $('#division').val();
	      $.ajax({
	          url : './getTownsBaseOnSubdivision',
	          type : 'GET',
	          dataType : 'json',
	          asynch : false,
	          cache : false,
	          data : {
	              zone : zone,
	              circle : circle,
	              division : division,
	              subdivision :subdiv
	          },
	                  success : function(response1) {
	                      var html = '';
	                      html += "<select id='town' name='town'  class='form-control input-medium' type='text'><option value=''>Select</option><option value='%'>ALL</option>";
	                      for (var i = 0; i < response1.length; i++) {
	                          //var response=response1[i];
	                          html += "<option value='"+response1[i][0]+"-"+response1[i][1]+"'>"
	                                  +response1[i][0]+"-"+response1[i][1] + "</option>";
	                      }
	                      html += "</select><span></span>";
	                      $("#townTd").html(html);
	                      $('#town').select2();
	                  }
	              });
	  }
	  
	function getReport() {
		var zone=$('#LFzone').val();
		var circle = $('#LFcircle').val();
		var division = $('#division').val();
		var locType = $("#loctype").val();
		var subdivision = $('#sdoCode').val();
		var towns =$("#LFtown").val();
		//alert(locType);
		var a=[];
			a=towns.split("-");
		//alert(a[0]+"  "+a[1]);
		var town=a[0];
		var townname=a[1];
		var rdngmonth = $('#month').val();


		if (zone == "") {
			bootbox.alert("Please Select Region");
			return false;
		}
		if (circle == "") {
			bootbox.alert("Please Select circle");
			return false;
		}
		 /* if (division == "") {
			bootbox.alert("Please Select division");
			return false;
		}
		if (subdivision == "") {
			bootbox.alert("Please Select subdivision");
			return false;
		} */
		if(town=="")
		{
		bootbox.alert("Please Enter town");
		return false;
		}
 
		if (locType == "") {
			bootbox.alert("Please select Location Type");
			return false;
		}
 		
		if (rdngmonth == "") {
			bootbox.alert("Please select Month Year");
			return false;
		}
		//alert(locType+" "+circle+"  "+division+"  "+subdivision+rdngmonth);
		//$('#sample1').dataTable().fnClearTable();
		$('#imageee').show();
		$('#pagediv').show();
		$.ajax({
			url : './getnetworkperformancedata/'  + locType
					+ '/' + rdngmonth,
			type : 'POST',
			data : {
				zone:zone,
				circle : circle,
				division : '%',
				subdivision : '%',
				town : town,
				townname : townname
				
			},
		
			
			
			dataType : 'json',
			success : function(response) {
				//alert(response);
				 $('#imageee').hide();
				 $('#viewcharts').show();
				if ("FEEDER METER"==locType) {
					//alert("hiii if");
					$('#dtHealth').hide();
					$('#boundaryHealth').hide();
					$('#feederHealth').show();
					$('#sample2').show()
					if (response != null && response.length > 0) {
						var html = "";
						for (var i = 0; i < response.length; i++) {

							var resp = response[i];
							var crdt = '';
							if (resp[6] == 1) {
								crdt = "yes";
							} else {
								crdt = "no";
							}
							html += "<tr>" +
						  "<td>" + (i + 1) + "</td>"+ 
						  "<td>" + (resp[0]==null?"": resp[0]) + " </td>" +
						  "<td>" + (resp[8]==null?"": resp[8]) + " </td>" +
						  "<td>" + (resp[9]==null?"": resp[9]) + " </td>" + 
				       	  "<td>" + (resp[2]==null?"": resp[2]) + " </td>" +
                          "<td>" + (resp[3]==null?"": resp[3]) + " </td>" +
                          "<td>" + (resp[1]==null?"": resp[1]) + " </td>" + 
                          /* "<td>" + (resp[8]==null?"":crdt) + " </td>"+ */
                          /* "<td>" + (resp[8]==null?"":crdt) + " </td>" + */
                          "</tr>";
						}
						//alert(html);
						$('#sample2').dataTable().fnClearTable();
						$("#feederMaster").html(html);
						loadSearchAndFilter('sample2');
						$('#feederHealth').show();
					} else {
						$('#sample2').dataTable().fnClearTable();
						$("#feederMaster").html(html);
						loadSearchAndFilter('sample2');
						$('#viewcharts').hide();
						$('#feederHealth').hide();
						bootbox.alert("No Data Found");
					}
				
			} else if ("BOUNDARY METER"==locType) {
						//alert("hiii if");
						$('#dtHealth').hide();
						$('#feederHealth').hide();
						$('#boundaryHealth').show();
						$('#sample3').show()
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {

								var resp = response[i];
								var crdt = '';
								if (resp[6] == 1) {
									crdt = "yes";
								} else {
									crdt = "no";
								}
								html += "<tr>" +
							  "<td>" + (i + 1) + "</td>"+ 
							  "<td>" + (resp[0]==null?"": resp[0]) + " </td>" +
							  "<td>" + (resp[8]==null?"": resp[8]) + " </td>" +
							  "<td>" + (resp[9]==null?"": resp[9]) + " </td>" + 
					       	  "<td>" + (resp[2]==null?"": resp[2]) + " </td>" +
	                          "<td>" + (resp[3]==null?"": resp[3]) + " </td>" +
	                          "<td>" + (resp[1]==null?"": resp[1]) + " </td>" + 
	                          /* "<td>" + (resp[8]==null?"":crdt) + " </td>"+ */
	                          /* "<td>" + (resp[8]==null?"":crdt) + " </td>" + */
	                          "</tr>";
							}
							//alert(html);
							$('#sample3').dataTable().fnClearTable();
							$("#boundaryMaster").html(html);
							loadSearchAndFilter('sample3');
							$('#boundaryHealth').show();
						} else {
							$('#sample3').dataTable().fnClearTable();
							$("#boundaryMaster").html(html);
							loadSearchAndFilter('sample3');
							$('#boundaryHealth').hide();
							$('#viewcharts').hide();
							bootbox.alert("No Data Found");	
						}
					} else {
						//alert("hiii else");
						$("#feederHealth").hide();
						$('#boundaryHealth').hide();
						$("#dtHealth").show();
						$('#sample1').show()
						if (response != null && response.length > 0) {
							var html = "";
							for (var i = 0; i < response.length; i++) {

								var resp = response[i];
								var crdt = '';
								if (resp[8] == 1) {
									crdt = "yes";
								} else {
									crdt = "no";
								}
								html += "<tr>" +
							  "<td>" + (i + 1) + "</td>"+ 
							  "<td>" + (resp[0]==null?"": resp[0]) + " </td>" +
							  "<td>" + (resp[1]==null?"": resp[1]) + " </td>" +
							  "<td>" + (resp[13]==null?"": resp[13]) + " </td>" + 
							  "<td>" + (resp[2]==null?"": resp[2]) + " </td>" + 
							  "<td>" + (resp[3]==null?"": resp[3]) + " </td>"+
					       	  "<td>" + (resp[4]==null?"": resp[4]) + " </td>" +
					       	 "<td>" + (resp[7]==null?"": resp[7]) + " </td>"+
					       	  "<td>" + (resp[6]==null?"": resp[6]) + " </td>" + 
					          "<td>" + (resp[8]==null?"": resp[8]) + " </td>"+
	                          "<td>" + (resp[5]==null?"": resp[5]) + " </td>" +
	                          "<td>" + (resp[11]==null?"": resp[11]) + " </td>"+
	                          "<td>" + (resp[12]==null?"": resp[12]) + " </td>"+
	                  
	                        
	                        	
	                          /* "<td>" + (resp[8]==null?"":crdt) + " </td>" + */
	                          "</tr>";
		                          
							}
							$('#sample1').dataTable().fnClearTable();
							$("#updateMaster").html(html);
							loadSearchAndFilter('sample1');
							$("#dtHealth").show();
						} 
				
					else {
						$('#sample2').dataTable().fnClearTable();
						$("#feederMaster").html(html);
						$("#dtHealth").hide();
						$('#viewcharts').hide();
						bootbox.alert("No Data Found");
					}
				
				}
			},
			complete : function() {
				//loadSearchAndFilter('sample1');
			}
		});
		getGraphicalReport(rdngmonth, subdivision, locType, circle, division);

	}
	function getGraphicalReport(rdngmonth, subdivision, locType, circle,
			division) {
		//alert(rdngmonth+"  "+ subdivision+"  "+ locType+"  "+ circle+"  "+division);
		var powerFactorTrend="";
		var utilizationFactorTrend="";
		var loadFactorTrend="";
		var yheader='';
		var zone=$('#LFzone').val();
		var town = $('#LFtown').val();

		if (("feeder").localeCompare(locType)) {
			yheader="Network Assets (Dt)";
		}else{
			yheader="Network Assets (Feeder)";
		}
		$.ajax({
					url : './getgraphicalnetwork/' 
							+ locType + '/' + rdngmonth,
					type : 'POST',
					data : {
						zone : zone,
						circle : circle,
						town : town,
						division : '%',
						subdivision : '%'
					},
					dataType : 'json',
					success : function(response) {
						//$("#updateMaster").html('');
						if (response != null && response.length > 0) {
							var html = "";
						 
							 powerFactorTrend = response[0];
							 utilizationFactorTrend = response[1];
							 loadFactorTrend = response[2];
							for (var i = 0; i < powerFactorTrend.length; i++) {

								var resp = powerFactorTrend[i];
								Highcharts
										.chart(
												'container',
												{
													chart : {
														type : 'column'
													},
													title : {
														verticalAlign : 'bottom',
														y : 10,
														text : 'Power Factor Analysis Trend'
													},

													xAxis : {
														title : {
															text : 'Power Factor'
														},
														categories : [
																'0.0-0.1',
																'0.1-0.2',
																'0.2-0.3',
																'0.3-0.4',
																'0.4-0.5',
																'0.5-0.6',
																'0.6-0.7',
																'0.7-0.8',
																'0.8-0.9',
																'0.9-1.0' ],
														crosshair : true
													},
													yAxis : {
														min : 0,
														max : null,
														title : {
															text : yheader
														}
													},
													
													tooltip : {
														headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
														pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
																+ '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
														footerFormat : '</table>',
														
														shared : true,
														useHTML : true
													},
													  
													plotOptions : {
														column : {
															pointPadding : 0.2,
															borderWidth : 0
														},
													 series: {
												            dataLabels: {
												                enabled: true
												            }
												        }
													},
													series : [ {
														name : '',
														data : [  resp[0],
																resp[1],
																resp[2],
																resp[3],
																resp[4],
																resp[5],
																resp[6],
																resp[7],
																resp[8],
																resp[9]  ]

													} ]
												});

							}
							for (var i = 0; i < utilizationFactorTrend.length; i++) {

								var resp = utilizationFactorTrend[i];
								Highcharts
										.chart(
												'utilization',
												{
													chart : {
														type : 'column'
													},
													title : {

														verticalAlign : 'bottom',
														y : 10,

														text : 'Utilization  Factor Analysis Trend'
													},

													xAxis : {
														title : {

															text : 'Utilization Factor'
														},
														categories : [
																'0.0-0.1',
																'0.1-0.2',
																'0.2-o.3',
																'0.3-0.4',
																'0.4-0.5',
																'0.5-0.6',
																'0.6-0.7',
																'0.7-0.8',
																'0.8-0.9',
																'0.9-1.0' ],
														crosshair : true
													},
													yAxis : {
														min : 0,
														max : null,
														title : {
															text : yheader
														},

													},
													tooltip : {
														headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
														pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
																+ '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
														footerFormat : '</table>',
														shared : true,
														useHTML : true
													},
													plotOptions : {
														column : {
															pointPadding : 0.2,
															borderWidth : 0
														},
														 series: {
													            dataLabels: {
													                enabled: true
													            }
													        }
													},
													series : [ {
														name : '',
														data : [ resp[0],
																resp[1],
																resp[2],
																resp[3],
																resp[4],
																resp[5],
																resp[6],
																resp[7],
																resp[8],
																resp[9] ]

													} ]
													
												});
							}
							for (var i = 0; i < loadFactorTrend.length; i++) {

								var resp = loadFactorTrend[i];
								Highcharts
										.chart(
												'loadfactor',
												{
													chart : {
														type : 'column'
													},
													title : {
														verticalAlign : 'bottom',
														y : 10,
														text : 'Load  Factor Analysis Trend'
													},

													xAxis : {
														title : {
															text : 'Load Factor'
														},
														categories : [
																'0.0-0.1',
																'0.1-0.2',
																'0.2-0.3',
																'0.3-0.4',
																'0.4-0.5',
																'0.5-0.6',
																'0.6-0.7',
																'0.7-0.8',
																'0.8-0.9',
																'0.9-1.0' ],
														crosshair : true
													},
													yAxis : {
														min : 0,
														max : null,
														title : {
															text :yheader
														}
													},
													tooltip : {
														headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
														pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
																+ '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
														footerFormat : '</table>',
														shared : true,
														useHTML : true
													},
													
													
													plotOptions : {
														column : {
															pointPadding : 0.2,
															borderWidth : 0
														},
														 series: {
													            dataLabels: {
													                enabled: true
													            }
													        }
													},
													/* series: [{
												        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4]
												    }], */
													
													legends :{
														enabled:true
														},
													series : [ {
														name : '',
														data : [ resp[0],
																resp[1],
																resp[2],
																resp[3],
																resp[4],
																resp[5],
																resp[6],
																resp[7],
																resp[8],
																resp[9] ]

													} ]
													
													
												});
								
							}
							if(!("dt").localeCompare(locType)){
							$("#viewcharts").show();
							$("#utilization").show();
						}
						else if(!("feeder").localeCompare(locType)){
							$("#viewcharts").show();
							$("#utilization").hide();
						}
						} else {
							bootbox.alert("No data");

						}
					}
				});
	}
	function exporttogrid(){
		var locType = $('#loctype').val();
		if ("FEEDER METER"==locType) {
			tableToExcel('sample2', 'Feeder Network Performance');
		}
		else if ("BOUNDARY METER"==locType) {
			tableToExcel('sample3', 'Boundary Network Performance');
		}
		else {
		tableToExcel('sample1', 'Dt Network Performance');
	}
	}

	 function showResultsbasedOntownCode (){
	  		
     }

	 function showTownNameandCode(circle){
 		var zone =  $('#LFzone').val(); 
 		
 		   $.ajax({
 		      	url:'./getTownNameandCodebyCircle',
 		      	type:'GET',
 		      	dataType:'json',
 		      	asynch:false,
 		      	cache:false,
 		      	data : {
 		  			zone : zone,
 		  			circle:circle
 		  		},
 		  		success : function(response1) {
 		  			
 	                var html = '';
 	                html += "<select id='LFtown' name='LFtown'  class='form-control  input-medium'  type='text'><option value=''>Select</option><option value='%'>ALL</option>";
 	               
 	                for (var i = 0; i < response1.length; i++) {
 	                    //var response=response1[i];
 	                    
 	                    html += "<option value='"+response1[i][0]+"'>"
 	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
 	                               }
 	                html += "</select><span></span>";
 	                $("#LFtownTd").html(html);
 	                $('#LFtown').select2();
 	                
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
						<i class="fa fa-edit"></i>Network Asset Performance
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">

			<jsp:include page="locationFilter.jsp"/> 

					<div class="row" style="margin-left: -1px;">
							<div class="col-md-3" id="LocType">
						<strong>Location Type:</strong>
						<div class="form-group">
					<select class="form-control select2me" id="loctype" name="loctype">	
                                            <option value=""></option>
											<option value="FEEDER METER">FEEDER</option>
											<option value="DT">DT</option>
											<option value="BOUNDARY METER">BOUNDARY</option>
											</select>
											</div>
											</div>
								
								
								<div class="col-md-3">
					<div class="input-group ">
					
						<strong>Month Year</strong>
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"><span
											class="input-group-btn">
										<button class="btn default" type="button" style="margin-bottom: -17px;">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div>	
							</div>	
							
											<div class="col-md-3">

										<button type="button" id="viewFeeder" style="margin-top: 19px;"
											onclick="return getReport();" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> 
									</div>
									</div>
									
				<!-- <th id="LocType" class="block">Location&nbsp;Type:<span style="color: red">*</span></th>
									<th id="LocType"><select
										class="form-control select2me input-medium" id="loctype"
										name="loctype">
											<option value=""></option>
											<option value="feeder">Feeder</option>
											<option value="dt">Dt</option>
									</select></th> -->

									<!-- <th class="block">Month&nbsp;Year<span style="color: red">*</span></th>
									<td>
										<div class="input-group ">
											<input type="text" class="form-control from" id="month"
												name="month" placeholder="Select Month"
												style="cursor: pointer"> <span
												class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td> -->
						
		<%-- 						<tr>
								
								<th class="block">Region&nbsp;:&nbsp;<span style="color: red">*</span></th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="zone"
										name="zone" onchange="showCircle(this.value);">
											<option value="">Select</option>
											<option value="%">ALL</option> 
											<c:forEach items="${ZoneList}" var="elements">
											<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></th>
									

									<th class="block">Circle&nbsp;:&nbsp;<span style="color: red">*</span></th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showTownNameandCode(this.value);">
											<option id="getCircles" value=""></option>
											<option value=""></option>
									</select></th>
									
									<th class="block">Town&nbsp;:<span style="color: red">*</span></th>
							<th id="townTd"><select
								class="form-control select2me input-medium" id="town"
								name="town">
								<option value="">Select</option>
							<option value="%">ALL</option>
							</select></th>
							
									<!-- <th class="block">Division&nbsp;:<span style="color: red">*</span></th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">
										<option value=''>Select Division</option>
									</select></th> -->
									<!-- <th class="block">Sub&nbsp;Division&nbsp;:<span style="color: red">*</span></th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"  onchange="showTownBySubdiv(this.value);
										name="sdoCode" ><option value=''>Select Sub-Division</option></select>
										</th> -->
										
								</tr> --%>
							<!-- 	<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
								
					
									<td>
										<button type="button" id="viewFeeder"
											style="margin-left: 20px;" onclick="return getReport()"
											name="viewconsumers" class="btn yellow">
											<b>View</b>
										</button> <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button>
									</td>
								</tr>
							</tbody>
						</table> -->
						<p>&nbsp;</p>
						<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					</div>
					

					<div id="viewcharts" style="display: none;">

						<div id="container" 
							style="min-width: 310px; height: 400px; margin: 0 auto"></div>
						<br>
						<br>
						<div id="utilization"
							style="display:none; min-width: 310px; height: 400px; margin: 0 auto"></div>
						<br>
						<br>
						<div id="loadfactor"
							style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="page-content" id="pagediv" style="display: none;">
	<div class="row">
		<div class="col-md-12">

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Network Asset Details
					</div>
					<!-- <div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div> -->
				</div>

				<div class="portlet-body">

					<div class="row" style="margin-left: -1px;">
						<div class="table-toolbar">
							<div class="btn-group pull-right" style="margin-top: 15px; margin-right: 9px">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
								<li><a href="#" id=""
								onclick="exportPDF()">Export to PDF</a></li>
									<li><a href="#" id="excelExport"
										onclick="exporttogrid();">Export
											to Excel</a></li>
								</ul>
							</div>
						</div>
						<div id="dtHealth" style="display: none; margin-right: 9px">
							<table 
								class="table table-striped table-hover table-bordered"
								id="sample1">
								<thead>
									<tr>
										<th>Sl No</th>
										<th>Region</th>
										<th>Circle</th>
										<th>Town</th>
										<th>subdivision</th>
										<th>Substation Name</th>
										<th>Feeder Name</th>
										<th>Dt Id</th>
										<th>Dt Name</th>
										<th>Dt Capacity</th>
										<th>Meter Serial Number</th>
										<th>load factor</th>
										<th>power factor</th>
										<!-- <th>Cross Dt</th> -->
									</tr>
								</thead>
								<tbody id="updateMaster">

								</tbody>
							</table>
						</div>
						<div id="feederHealth" style="display: none; margin-right: 9px">
							<table class="table table-striped table-hover table-bordered"
								id="sample2">
								<thead>
									<tr>
										<th>Sl No</th>
										<th>Year Month</th>
										<th>subdivision</th>
										<th>Substation Name</th>
										<th>Feeder Name</th>
										<th>Feeder Id</th>
										<th>Meter Serial Number</th>
										<!-- <th>Cross Feeder</th> -->
									</tr>
								</thead>
								<tbody id="feederMaster">

								</tbody>

							</table>
						</div>
						<div id="boundaryHealth" style="display: none; margin-right: 9px">
							<table class="table table-striped table-hover table-bordered"
								id="sample3">
								<thead>
									<tr>
										<th>Sl No</th>
										<th>Year Month</th>
										<th>subdivision</th>
										<th>Substation Name</th>
										<th>Boundary Name</th>
										<th>Boundary Id</th>
										<th>Meter Serial Number</th>
										<!-- <th>Cross Feeder</th> -->
									</tr>
								</thead>
								<tbody id="boundaryMaster">

								</tbody>

							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	 


<script>


	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();

	$('.from').datepicker({
		format : "yyyymm",
		minViewMode : 1,
		autoclose : true,
		startDate : new Date(new Date().getFullYear()),
		endDate : new Date(year, month - 1, '30')
	});
	
	
	function exportPDF()
	{
		
		var zone=$('#LFzone').val();
		var circle = $('#LFcircle').val();
		var locType = $('#loctype').val();
		var rdngmonth = $('#month').val();
		var town1 = $('#LFtown').val();
		var a=[];
		var townname = $("#LFtown option:selected").map(function(){return this.text}).get().join(',');
		
		
		var zne="",cir="",town;

		if(zone=="%"){
			zne="ALL";
		}else{
		    zne=zone;
		}
		if(circle=="%"){
			cir="ALL";
		}else{
		    cir=circle;
		}
		
		if(town1=="%"){
			town="ALL";
			townname="ALL";
		}else{
			//town=town1;
			a=town1.split("-");
		    town=a[0];
		    
		}
		/* alert(zone);
		alert(circle);
		alert(town);
		 */
		

		window.location.href=("./NetworkAssetDetailsPdf?zne="+zne+"&cir="+cir+"&locType="+locType+"&rdngmonth="+rdngmonth+"&town="+town+"&townname="+townname+"&subdvn=All&div=All");
	}
</script>