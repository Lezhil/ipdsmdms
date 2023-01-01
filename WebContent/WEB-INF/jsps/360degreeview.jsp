<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
 
 <script  type="text/javascript">
 
  $(".page-content").ready(function()
   	    	   {    
	  			  //high('${instantanousData[0].rPhaseVal}');
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  FormComponents.init();
	   	    	  UIDatepaginator.init();
	   	    	  
	   	    	
   	    	 	  if('${billedDataList.size()}'>0)
  	    		  {
   	  	    		  
  	    			displayConsuptionChart();
  	    		  }
	   	    	  
	   	    	  $("#selectedDateId").val(getPresentMonthDate('${selectedMonth}'));
	   	    	  $("#loadSurveydateId").val(moment(new Date).format('DD-MM-YYYY'));
	   	    	$('#360d-view').addClass('start active ,selected');
     	    	 $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
     	    	  $('#meterVal').val('${meterNo}');
     	    	 phaseDiagram('${rPhaseAngle}','${yPhaseAngle}','${bPhaseAngle}');
	   	    	   });
  
  
  function displayConsuptionChart()
  {
	  
	  var codesJS=new Array();
	  var codesJS1=new Array();
	  
	  <% 
	  if(request.getAttribute("monthArr")!=null);
	  String[] codes=(String[])request.getAttribute("monthArr");
	  if(codes!=null){
	      for(int i=0; i<codes.length; i++){ %>
	          var code='<%= codes[i] %>';          
	          codesJS[<%= i %>]=(code); 
	      <%}
	  }%>
	  
	  
	  <% 
	  if(request.getAttribute("consuptionArr")!=null);
	  String[] codes1=(String[])request.getAttribute("consuptionArr");
	  
	  
	  if(codes1!=null){
	      for(int i=0; i<codes1.length; i++){ %>
	          var code1='<%= codes1[i] %>';          
	          codesJS1[<%= i %>]=parseFloat(code1); 
	      <%}
	  }%>
	  
	  chart = new Highcharts.Chart({

  		   /* colors: ["#7cb5ec", "#f7a35c", "#90ee7e", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
  		      "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"], */
  		   chart: {
  			 renderTo : 'container',
  			   type: 'column',
  		      backgroundColor: null,
  		      style: {
  		         fontFamily: "Dosis, sans-serif"
  		      }
  		   },
  		   title: {
  			   
  			          text: 'Consumption Representation',
  			      
  		      style: {
  		         fontSize: '16px',
  		         fontWeight: 'bold',
  		         textTransform: 'uppercase',
  		         text:'Phasewise Representation'
  		      }
  		   },
  		   tooltip: {
  		      borderWidth: 0,
  		      backgroundColor: 'rgba(219,219,216,0.8)',
  		      shadow: true
  		   },
  		   legend: {
  		      itemStyle: {
  		         fontWeight: 'bold',
  		         fontSize: '13px'
  		      }
  		   },
  		   xAxis: {
  		      gridLineWidth: 1,
  		      categories: codesJS,
  		      labels: {
  		         style: {
  		            fontSize: '20px'
  		         }
  		      }
  		   },
  		   yAxis: {
  		      minorTickInterval: 'auto',
  		      title: {
  		    	text: 'Consumption',
  		         style: {
  		        	fontSize: '16px',
  	  		         fontWeight: 'bold',
  		            //textTransform: 'uppercase'
  		         }
  		      },
  		      labels: {
  		         style: {
  		            fontSize: '20px'
  		         }
  		      },
  		    
  		   },
  		   /* plotOptions: {
  			 series: {
	                pointWidth: 20
	            },
  		      bar: {
                  dataLabels: {
                      enabled: true
                  }
              },
  		   }, */
  		   
  		 plotOptions: {
  		    column: {
  		        dataLabels: {
  		            enabled: true,
  		            /* y: -20,
  		            verticalAlign: 'top' */
  		           style:{
                      fontSize: '15px'
                  }, 
  		        },
  		     },
  		   
  		   series: {
               pointWidth: 20
           },
  		},


  		   // General
  		   background2: '#F0F0EA',
  		   
  		   
	        series: [{
	            name: 'Reading Month',
	            color:'#009973',
	            data: codesJS1

	        }],
	        exporting: {
	            enabled: true
	        }
	    });
	    return false;
  }
		
  function submitData(path)
  {	 
	var meterNo=document.getElementById('meterVal').value;	
	if(meterNo.trim()=='')
		{
		bootbox.alert('Please enter Meter number');
		return false;
		}
	  $('#meterMasterForm').attr('action',path).submit();
	  $('#datepickerForm').attr('action',path).submit();
	  
  }
  
  function phaseDiagram(Ia,Ib,Ic)
  {
	  var iA1 = 180-parseInt(Ia);
	  var iB1 = 180-parseInt(Ib);
	  var iC1 = 180-parseInt(Ic);
	  
	  var c = document.getElementById("myCanvas");
	  var ctx = c.getContext("2d");


	  ctx.beginPath();
	  ctx.arc(110,150,90,0,2*Math.PI);
	  ctx.strokeStyle="green";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.strokeStyle="blue";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'blue';
	  ctx.strokeText('Va', (110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'blue';
	  ctx.strokeText('Vb', (110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(-60* (Math.PI / 180))),(150+90*Math.cos(-60* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'blue';
	  ctx.strokeText('Vc', (110+90*Math.sin(-65* (Math.PI / 180))),(150+90*Math.cos(-55* (Math.PI / 180))));
	  ctx.stroke();

	  //current values

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.strokeStyle="red";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('ia', (110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('ib', (110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iC1* (Math.PI / 180))),(150+90*Math.cos(iC1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('ic', (110+90*Math.sin(iC1* (Math.PI / 180))),(150+100*Math.cos(iC1* (Math.PI / 180))));
	  ctx.stroke();


	  //Degrees labels
	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('0', (110+90*Math.sin(180* (Math.PI / 180))),(140+90*Math.cos(180* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('90', (120+90*Math.sin(90* (Math.PI / 180))),(150+90*Math.cos(90* (Math.PI / 180))));
	  ctx.stroke();


	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('180', (110+90*Math.sin(0* (Math.PI / 180))),(160+90*Math.cos(0* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'red';
	  ctx.strokeText('270', (90+90*Math.sin(-90* (Math.PI / 180))),(150+90*Math.cos(-90* (Math.PI / 180))));
	  ctx.stroke();
  }
  
  function exportReadingDataDetails()
  {
	 
	  //window.location.href = "./download360ViewPdf/"+$('#meterVal').val()+"/"+$('#selectedDateId').val()+"/"+'LoadSurveyIpwise';
	  if('${viewCategory}'!='')
	  {
	    window.location.href = "./download360ViewPdf/"+$('#meterVal').val()+"/"+$('#selectedDateId').val()+"/"+'${viewCategory}';  
	  }
	  else
	  {
	     alert('Please view data and then export to PDF.');
	  } 
  }
  
  function submitData1()
  {
	var meterNo=document.getElementById('meterVal').value;	
	if(meterNo.trim()=='')
	{
		bootbox.alert('Please enter Meter number');
		return false;
	}
	else
	{
		window.location.href = "./tamperReportPdf/"+$('#meterVal').val()+"/"+$('#selectedDateId').val();	
	}
  }
  
  </script>
<div class="page-content" >
    
	<div class="portlet box blue">
	 
                        
									
          
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>360 Degree View of the Meter</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						 <div class="col-md-12">
										
										<form method="post" id="meterMasterForm" action="">
										<table>
										<tr><td>Meter Number</td><td><input type="text" class="form-control" id="meterVal" name="meterNo" required="required"></td></tr>
										<tr><td>Select YearMonth</td><td>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<input type="text" class="form-control" name="selectedDateName" id="selectedDateId"  readonly >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td></tr>
										</table>
										
											
											
											
	                                	
		
										<div id="stack2" class="modal fade" tabindex="-1" data-width="400">
							              <div class="modal-dialog">
									       <div class="modal-content" >
										     <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							                	<h4 class="modal-title" id="myDiv">Select Date To see LoadSurvey</h4>								
										     </div>
										
										<div class="modal-body">
											<div class="row">
												<div class="col-md-12">	
																						
												<div class="input-group input-medium date date-picker"  data-date-format="dd-mm-yyyy" data-date-viewmode="years" id="five">
																<input type="text" class="form-control" name="loadSurveydate" id="loadSurveydateId"  readonly >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div>
																<div class="modal-footer">
															
															  <button class="btn blue pull-right" id="addOption" onclick="submitData('./getDailyLoadSurvayData');">Submit</button> 
															  <button type="button" data-dismiss="modal" class="btn">Cancel</button></div>
														
														</div>						
											</div>
										</div>
									</div>
								</div>
							</div> 
										
										
										</form>
											
									
										
						</div>
						<br/>
						<br/>
						<div class="clearfix"></div>
						<div class="clearfix"></div>
							<a href="#" class="icon-btn" onclick="submitData('./getInstantanousData');">
								<i class="fa fa-bolt"></i>
								<div>Instantaneous Parameters</div>
								
							</a>
							
							<a href="#" class="icon-btn" onclick="submitData('./getBillingDatas');"> 
								<i class="fa fa-bar-chart-o"></i>
								<div>Billing Parameter</div>
							</a>
							
							<a href="#" class="icon-btn" onclick="submitData('./getEventData');">
								<i class="fa fa-calendar"></i>
								<div>Event History</div>
								
							</a>
							
							<a href="#" class="icon-btn" onclick="submitData('./getTransactions');">
								<i class="fa fa-lightbulb-o"></i>
								<div>Transaction History</div>
								
							</a>
						
							
							 <a href="#" class="icon-btn" onclick="return submitDataLoadExcel()">
								<i class="fa fa-download"></i>
								<div>Load Survey Details</div>
							
							
							<a href="#" class="icon-btn" onclick="submitData('./getReadingsData');">
								<i class="fa fa-check-square-o"><i></i></i>
								<div>Readings</div>
							</a>
							
						
							
							<a href="#" class="icon-btn" onclick="submitData1('./getMeterNoDetails');">
								<i class="fa fa-check-square-o"><i></i></i>
								<div>Tamper Report</div>
							</a>
							
						</div>
		</div>
	
		 <c:if test = "${not empty msg}">
			<%-- <div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${msg}</span>
			</div> --%>
	    </c:if>	
		
		       
	
		<button onclick="exportReadingDataDetails()" class="btn default" type="submit" >
											<b><i class="fa fa-file-pdf-o"></i>&nbsp;<font color="#ff4d4d" >EXPORT TO PDF</font></b>
										</button>
		
		<c:if test = "${instantanousData.size() gt '0'}">
		 <div class="row">
				<div class="col-md-12">
					
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>Name Plate Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>Value</th>
											
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Meter Serial Number</td>
											<td>${instantanousData[0].cdfData.meterNo}</td>											
										</tr>
										<tr>
											<td>2</td>
											<td>Meter Date & Time</td>
											<td>${instantanousData[0].cdfData.d1data.meterDate}</td>
											
										</tr>
										<tr>
											<td>3</td>
											<td>Meter Type</td>
											<td>${instantanousData[0].cdfData.d1data.meterType}</td>
											
										</tr>
									
										<tr>
											<td>5</td>
											<td>Meter Class</td>
											<td>${instantanousData[0].cdfData.d1data.meterClass}</td>											
										</tr>
										<tr>
											<td>6</td>
											<td>Manufacturer Name</td>
											<td>${instantanousData[0].cdfData.d1data.manufacturerName}</td>											
										</tr>
										
										<tr>
											<td>7</td>
											<td>Phase Sequence</td>
											<td>${instantanousData[0].phaseSequence}</td>											
										</tr>
										
										<tr hidden="true">
											<td>6</td>
											<td>Manufacturer Name</td>
											<td>${instantanousData[0].cdfData.d1data.manufacturerName}</td>											
										</tr>
										<tr hidden="true">
											<td>6</td>
											<td>Manufacturer Name</td>
											<td>${instantanousData[0].cdfData.d1data.manufacturerName}</td>											
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				
				</div>
				<div class="col-md-5">
					
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-picture-o"></i>Vector Diagram</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body">
							<!-- <img height="180" width="180" src='/bsmartmdm/resources/assets/img/vector_diagram.JPG'> -->
						<span><canvas id='myCanvas' width='250' height='270' style='border:1px solid #d3d3d3;margin-left:10px'></canvas></span>
						
						<script type="text/javascript">
						</script>
						</div>
					</div>
					
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-info-circle"></i>Line Voltage,Current and PF Parameters</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>L1/Element1</th>
											<th >L2/Element2</th>
											<th>L3/Element3</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Voltages</td>
											<td>${instantanousData[0].rPhaseVal} V</td>
											<td >${instantanousData[0].yPhaseVal} v</td>
											<td>${instantanousData[0].bPhaseVal} v</td>
										</tr>
										<tr>
											<td>2</td>
											<td>Line Current</td>
											<td>${instantanousData[0].rPhaseLineVal} A</td>
											<td >${instantanousData[0].yPhaseLineVal} A</td>
											<td>${instantanousData[0].bPhaseLineVal} A</td>
										</tr>
										<tr>
											<td>3</td>
											<td>Active Current</td>
											<td>-${instantanousData[0].rPhaseActiveVal} A</td>
											<td >-${instantanousData[0].yPhaseActiveVal} A </td>
											<td>-${instantanousData[0].bPhaseActiveVal} A</td>
										</tr>
										
										<tr>
											<td>5</td>
											<td>Power Factor</td>
											<td>${instantanousData[0].rPhasePfVal}</td>
											<td >${instantanousData[0].yPhasePfVal}</td>
											<td>${instantanousData[0].bPhasePfVal}</td>
										</tr>
										<tr>
											<td>6</td>
											<td>Current Angle Factor</td>
											<td>${instantanousData[0].rPhaseCuurentAngle}</td>
											<td >${instantanousData[0].yPhaseCuurentAngle}</td>
											<td>${instantanousData[0].bPhaseCuurentAngle}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						
					</div>
					
				</div>
				
			</div> 
			</c:if>
				<c:if test = "${billingData.size() gt '0'}">
				
				<div class="portlet box green" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>${portletTitle}</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
			          <div class="portlet-body" style="overflow: auto;">
							<div class="table-responsive" >
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>Value</th>
											
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Meter Serial Number</td>
											<td>${billingData[0].metrno}</td>											
										</tr>
										<tr>
											<td>2</td>
											<td>Reading Date</td>
											<td>${billingData[0].readingdate}</td>
											
										</tr>
										<tr>
											<td>3</td>
											<td>Kwh</td>
											<td>${billingData[0].xcurrdngkwh}</td>
											
										</tr>
										
										<tr>
											<td>4</td>
											<td>Kvah</td>
											<td>${billingData[0].xcurrrdngkvah}</td>											
										</tr>
										
										<tr>
											<td>5</td>
											<td>Kva</td>
											<td>${billingData[0].xcurrdngkva}</td>											
										</tr>
										
										<tr>
											<td>6</td>
											<td>PF</td>
											<td>${billingData[0].xpf}</td>											
										</tr>
										
										<tr>
											<td>7</td>
											<td>Month</td>
											<td>${billingData[0].rdngmonth}</td>											
										</tr>
									
										
										
										
									</tbody>
								</table> </div></div></div>
								</c:if>
								<c:if test = "${eventsData.size() gt '0'}">
			<div class="portlet box green" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>${portletTitle}</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
			          <div class="portlet-body" style="overflow: auto;">
							<div class="table-responsive" >
							
							
							
							
			                 <table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>EventCode</th>
											<th>EventDescription</th>
											<th>Status</th>
											<th>EventTime</th>
											
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="element" items="${eventsData}"> 
										<tr>
											<td>${count}</td>
											<td>${element[1]}</td>
											<td>${element[2]}</td>
											<c:choose>
                                               <c:when test="${element[4] eq '0'}">
                                               <td>Occured</td>
                                               </c:when>
                                               <c:otherwise>
                                              <td>Restored</td>
                                               </c:otherwise>
                                             </c:choose>	
											<td>${element[0]}</td>										
											</tr>
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
										
									</tbody>
								</table>
			</div>
			</div>
			</div>
			</c:if>
			
			<c:if test = "${transactionData.size() gt '0'}">
			<div class="portlet box green" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>${portletTitle}</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
			          <div class="portlet-body" style="overflow: auto;">
							<div class="table-responsive" >
		       <table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>TransactionCode</th>
											<th>Transaction Description</th>
											<th>TransactionTime</th>
											
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="element" items="${transactionData}"> 
										<tr>
											<td>${count}</td>
											<td>${element[1]}</td>
											<td>${element[2]}</td>
											<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss" value="${element[3]}"/></td>
											</tr>
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
										
									</tbody>
								</table> </div></div></div>
								</c:if>
								<c:if test = "${readingData.size() gt '0'}">
								<div class="portlet box green" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>${portletTitle}</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
			          <div class="portlet-body" style="overflow: auto;">
							<div class="table-responsive" >
			                    <table class="table table-striped table-hover">
								 <thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>value</th>
											
										</tr>
									</thead>
									<tbody>
										
										<tr>
											<td>1</td>
											<td>D3_01_ENERGY</td>
											<td>${readingData[0].d3_01_Energy}</td>
									   </tr>
									   <tr>
											<td>2</td>
											<td>D3_02_ENERGY</td>
											<td>${readingData[0].d3_02_Energy}</td>
									   </tr>
									   <tr>
											<td>3</td>
											<td>D3_03_ENERGY</td>
											<td>${readingData[0].d3_03_Energy}</td>
									   </tr>
										
									</tbody> 
									
								
								</table> </div></div></div>
								</c:if>
			<c:if test = "${eventsData.size() gt '0' || transactionData.size() gt '0' || readingData.size() gt '0' || loadSurveyData.size() gt '0' || dailyLoadSurveyData.size() gt '0'}" >
			<%-- <div class="portlet box green" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i>${portletTitle}</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
			          <div class="portlet-body" style="overflow: auto;">
							<div class="table-responsive" >
							
							
							
							<c:if test = "${eventsData.size() gt '0'}">
			                 <table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>EventCode</th>
											<th>EventDescription</th>
											<th>Status</th>
											<th>EventTime</th>
											
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="element" items="${eventsData}"> 
										<tr>
											<td>${count}</td>
											<td>${element[1]}</td>
											<td>${element[2]}</td>
											<c:choose>
                                               <c:when test="${element[4] eq '0'}">
                                               <td>Occured</td>
                                               </c:when>
                                               <c:otherwise>
                                              <td>Restored</td>
                                               </c:otherwise>
                                             </c:choose>	
											<td>${element[0]}</td>										
											</tr>
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
										
									</tbody>
								</table> --%>
								</c:if>
								
								
								
								<c:if test = "${transactionData.size() gt '0'}">
			           <%--         <table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>TransactionCode</th>
											<th>Transaction Description</th>
											<th>TransactionTime</th>
											
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="element" items="${transactionData}"> 
										<tr>
											<td>${count}</td>
											<td>${element[1]}</td>
											<td>${element[2]}</td>
											<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss" value="${element[3]}"/></td>
											</tr>
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>
										
									</tbody>
								</table> --%>
								</c:if>
								
								
								
							
														
								<c:if test = "${readingData.size() gt '0'}">
			                   <%-- <table class="table table-striped table-hover">
								 <thead>
										<tr>
											<th>#</th>
											<th>Parameter</th>
											<th>value</th>
											
										</tr>
									</thead>
									<tbody>
										
										<tr>
											<td>1</td>
											<td>D3_01_ENERGY</td>
											<td>${readingData[0].d3_01_Energy}</td>
									   </tr>
									   <tr>
											<td>2</td>
											<td>D3_02_ENERGY</td>
											<td>${readingData[0].d3_02_Energy}</td>
									   </tr>
									   <tr>
											<td>3</td>
											<td>D3_03_ENERGY</td>
											<td>${readingData[0].d3_03_Energy}</td>
									   </tr>
										
									</tbody> 
									
								
								</table> --%>
								</c:if>
								
								<c:if test = "${loadSurveyData.size() gt '0'}">
										 
							<%-- <table class="table table-striped table-hover" >
									<thead>
										<tr>
											<th>#</th>
											<th>KWH</th>
											<th>KVA</th>
											<th >PF</th>
											<th>DayProfileDate</th>
										</tr>
									</thead>
									<tbody>
										
										<c:set var="count" value="1" scope="page" />
										<c:forEach var='d4Dtata' items='${loadSurveyData}'>
										
										<tr>							
										<td>${count}</td>				
											
											
											<td>${d4Dtata[4]}</td>
											<td>${d4Dtata.[3]}</td>
											<td>${d4Dtata.[5]}</td>
											<td>${d4Dtata.[1]}</td>
											
										</tr>
										<c:set var="count" value="${count + 1}" scope="page"/>
										</c:forEach>										 
									</tbody>
								</table>
						 --%>
						
								</c:if>
								
								
								<c:if test = "${dailyLoadSurveyData.size() gt '0'}">
									
							<%--  <table class="table table-striped table-bordered table-hover" >
							
								<tbody>
								     <tr><td>DayProfileDate</td>
									 <c:forEach var='d4Dtata' items='${dailyLoadSurveyData}'>									 									 		
										 	<td><fmt:formatDate pattern="dd-MM-yyyy" value="${d4Dtata.dayProfileDate}"/></td>
										 	</c:forEach>
										 </tr>	
										 
										 <tr><td>KVA</td>
									 <c:forEach var='d4Dtata' items='${dailyLoadSurveyData}'>									 									 		
										 	<td>${d4Dtata.kvaValue}</td>
										 	</c:forEach>
										 </tr>	
										 
										 <tr><td>KWH</td>
									 <c:forEach var='d4Dtata' items='${dailyLoadSurveyData}'>									 									 		
										 	<td>${d4Dtata.kwhValue}</td>
										 	</c:forEach>
										 </tr>	
										 
										 <tr><td>PF</td>
									 <c:forEach var='d4Dtata' items='${dailyLoadSurveyData}'>									 									 		
										 	<td>${d4Dtata.pfValue}</td>
										 	</c:forEach>
										 </tr>	
										 
										 <tr><td>Interval</td>
									 <c:forEach var='d4Dtata' items='${dailyLoadSurveyData}'>									 									 		
										 	<c:choose>
                                               <c:when test="${d4Dtata.ipInterval mod '2' eq '0'}">
                                               <td><fmt:formatNumber value="${(d4Dtata.ipInterval)/2}" minFractionDigits="0" maxFractionDigits="0"/> : 00 </td>
                                               </c:when>
                                               <c:otherwise>
                                               <td><fmt:formatNumber value="${((d4Dtata.ipInterval-0.5)/2)}" minFractionDigits="0" maxFractionDigits="0"/> : 30 </td>
                                               </c:otherwise>
                                             </c:choose>
										 	</c:forEach>
										 </tr>	
										 
									 									 
									 
								</tbody>
							</table> 
						
								</c:if>
								</div>
								
								
								</div>
						</div> --%>
						
						<%-- <c:if test = "${dailyLoadSurveyData.size() gt '0'}">
							  <div class="row">
						<div class="col-md-12">
					
			</div>	 
			
			</c:if> --%>
			</c:if>
			
			<c:if test="${billedDataList.size() gt 0}">
		 <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto;border: 1px solid black;"></div>
			
			<br>
					<div class="col-md-16">
					
					
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user fa-lg"></i>Billing Data</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<!-- <a href="javascript:;" class="reload"></a> -->
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body" >
							
								
									<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								    <th>SI NO.</th>
								    <th>RDNGMONTH</th>
								    <th>METER NO.</th>
								    <th>KVA_DATE</th>
									<th>KWH</th>
								    <th>KVAH</th>
									<th>KVA</th>
									<th>CURRDNGKWH</th>
									<th>KWHE</th>
								    <th>KVHE</th>
									<th>KVAE</th>
									<th>PFE</th>
									<th>PF</th>
									<th>CONSUMPTION</th>
									<th>EXPORT UNITS</th>
									
									
								</tr>
						</thead>
						<tbody>
						<c:set var="count" value="1" scope="page" />
							<c:forEach var="element" items="${billedDataList}">
								<tr>
								<td>${count}</td>
								<c:forEach var="i" begin="0" end="${dataArrLength-1}">
								 <c:if test="${i ne 12 && i ne 13}"> 
								
									<td>${element[i]}</td>
								</c:if>
								</c:forEach>
								</tr>
								<c:set var="count" value="${count+1}" scope="page" />
							</c:forEach>

						</tbody>
					</table>		
						</div>
					</div>
					
					
					
					
			</div> 
			
			</c:if>
			
			<c:if test="${loadSurveyIpWise.size() gt 0}">
					<div class="col-md-16">
					
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-user fa-lg"></i>Load Survey Details</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body" >
							
								
									<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								    <th>SI NO.</th>
								    <th>CIRCLE</th>
								    <th>DIVISION</th>
								    <th>SUBDIVISON</th>
								    <th>MF</th>
								    <th>IP INTERVAL</th>
									<th>DAY PROFILE DATE</th>
									<th>KWH</th>
									<th>KVAV</th>
								</tr>
						</thead>
						<tbody>
						<c:set var="count" value="1" scope="page" />
							<c:forEach var="element" items="${loadSurveyIpWise}">
								<tr>
								<td>${count}</td>
								<c:forEach var="i" begin="0" end="${loadSurveyArrLength-1}">
									<td>${element[i]}</td>
								</c:forEach>
								</tr>
								<c:set var="count" value="${count+1}" scope="page" />
							</c:forEach>

						</tbody>
					</table>		
						</div>
					</div>
					
			</div> 
			
			</c:if> 
			
</div>


<script>
function submitDataLoadExcel()
{
var metrno=$("#meterVal").val();

if(metrno.trim()=='')
{
bootbox.alert('Please enter Meter number');
return false;
}

var Billmonth =$("#selectedDateId").val();

//alert("metrno--"+metrno+"--Billmonth-"+Billmonth);

window.location.href="./downloadLOadSurveyExcel?metrno="+metrno+"&Billmonth="+Billmonth;

/*  /downloadLOadSurveyExcel*/
}


</script>