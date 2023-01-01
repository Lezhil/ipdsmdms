<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
 
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {    
   	    	      App.init();
   	    	      TableEditable.init();
	   	    	  TableManaged.init();
	   	    	  FormComponents.init();
	   	    	  UIDatepaginator.init();
	   	    	
	   	    	//  $("#selectedDateId").val(getPresentMonthDate('${Month1}'));
	   	    	$('#MDASSideBarContents,#phasor').addClass('start active ,selected');
    	    	 $("#dash-board,#cmri-Manager,#seal-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
    	    	  $('#meterVal').val('${meterNo}');
    	    	 phaseDiagram('${rPhaseAngle}','${yPhaseAngle}','${bPhaseAngle}');
    	    	// phaseDiagram('100','190','270','90','180','260');
	   	    	   });
  
  
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
  
  function downloadReciept()
	 {
	  var meterNo=document.getElementById('meterVal').value;	
		 if(meterNo=="")
		 {
		     alert("Please Enter Meter Number.");
		     return false;
		 }
		 var month = $("#selectedDateId").val();
		 window.open("./downloadReciept?meterNo="+meterNo+"&month="+month);
	 }
	 
  
  //function phaseDiagram(Ia,Ib,Ic,Va,Vb,Vc)
  function phaseDiagram(Ia,Ib,Ic)
  {
	  var iA1 = 180-parseInt(Ia);
	  var iB1 = 180-parseInt(Ib);
	  var iC1 = 180-parseInt(Ic);
	  
	 /*  var vA1 = 180-parseInt(Va);
	  var vB1 = 180-parseInt(Vb);
	  var vC1 = 180-parseInt(Vc); */
	  
	  var c = document.getElementById("myCanvas");
	  var ctx = c.getContext("2d");


	  ctx.beginPath();
	  ctx.arc(110,150,90,0,2*Math.PI);
	  ctx.strokeStyle="green";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.strokeStyle="red";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('V1', (110+90*Math.sin(180* (Math.PI / 180))),(150+90*Math.cos(180* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.strokeStyle="yellow";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('V2', (110+90*Math.sin(60* (Math.PI / 180))),(150+90*Math.cos(60* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(-60* (Math.PI / 180))),(150+90*Math.cos(-60* (Math.PI / 180))));
	  ctx.strokeStyle="blue";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('V3', (110+90*Math.sin(-65* (Math.PI / 180))),(150+90*Math.cos(-55* (Math.PI / 180))));
	  ctx.stroke();

	  //current values

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.strokeStyle="red";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('L1', (110+90*Math.sin(iA1* (Math.PI / 180))),(150+90*Math.cos(iA1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.strokeStyle="yellow";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('L2', (110+90*Math.sin(iB1* (Math.PI / 180))),(150+90*Math.cos(iB1* (Math.PI / 180))));
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.moveTo(110,150);
	  ctx.lineTo((110+90*Math.sin(iC1* (Math.PI / 180))),(150+90*Math.cos(iC1* (Math.PI / 180))));
	  ctx.strokeStyle="blue";
	  ctx.stroke();

	  ctx.beginPath();
	  ctx.font = '20px';
	  ctx.strokeStyle = 'black';
	  ctx.strokeText('L3', (110+90*Math.sin(iC1* (Math.PI / 180))),(150+100*Math.cos(iC1* (Math.PI / 180))));
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
 
  
  </script>
<div class="page-content" >
    
			<div class="portlet box blue">
					<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Phasor Diagram</div>
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
										<tr>
											<td>Meter Number</td>
											<td><input type="text" class="form-control" id="meterVal" name="meterNo" required="required"></td>
										</tr>
										<tr>
											<td>Select Date</td>
											<td><div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="months" id="five">
												<input type="text" class="form-control" name="selectedDateName" id="selectedDateId"  readonly >
												<span class="input-group-btn"><button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
												</span></div></td>
										</tr>
									</table>
								</form>
						</div><br/><br/>
						
						<div class="clearfix"></div>
						<div class="clearfix"></div>
							<button type="button" class="btn green" onclick="submitData('./getPhaseForInstantanousData');">View</button>
							<!-- <button type="submit" class="btn btn-primary"  onclick="downloadReciept();" style="margin-left: 96px;"><strong>Export To PDF</strong></button> -->
					</div>
			</div>
		<!-- Display Error Message -->
		<c:if test = "${not empty msg}">
			<div class="alert alert-danger display-show">
					<button class="close" data-close="alert"></button>
					<span style="color:red" >${msg}</span>
			</div>
	    </c:if>	
		<!-- End Error Message -->
		       
		<!-- Start Intantanous -->
		<c:if test = "${instantanousData.size() gt '0'}">
				<div class="row ">
				<div class="col-md-5">
					<!-- BEGIN BORDERED TABLE PORTLET-->
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
					<!-- END BORDERED TABLE PORTLET-->
				</div>
				<div class="col-md-7">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-cogs"></i> Meter Details</div>
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
											<td>${feederData[0].mtrno}</td>											
										</tr>
										<tr>
											<td>2</td>
											<td>Meter Date & Time</td>
											<td>${feederData[0].installation_date}</td>
											
										</tr>
										<tr>
											<td>3</td>
											<td>Meter Type</td>
											<td>${feederData[0].fdrcategory}</td>
											
										</tr>
										<!-- <tr>
											<td>4</td>
											<td>Meter Processor Family</td>
											<td>3Es</td>
											
										</tr> -->
										<%-- <tr>
											<td>5</td>
											<td>Meter Class</td>
											<td>${instantanousData[0].cdfData.d1data.meterClass}</td>											
										</tr> --%>
										<tr>
											<td>4</td>
											<td>Manufacturer Name</td>
											<td>${feederData[0].mtrmake}</td>											
										</tr>
										
										<%-- <tr>
											<td>7</td>
											<td>Phase Sequence</td>
											<td>${instantanousData[0].phaseSequence}</td>											
										</tr> --%>
										
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- END SAMPLE TABLE PORTLET-->
				</div>
				</div>
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN SAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-info-circle"></i>Instantaneous Parameters</div>
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
											<th>R-PHASE</th>
											<th>Y-PHASE</th>
											<th>B-PHASE</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>Voltages</td>
											<td>${instantanousData[0].vR} V</td>
											<td >${instantanousData[0].vY} v</td>
											<td>${instantanousData[0].vB} v</td>
										</tr>
										 <tr>
											<td>2</td>
											<td>Line Current</td>
											<td>${instantanousData[0].iR} A</td>
											<td >${instantanousData[0].iY} A</td>
											<td>${instantanousData[0].iB} A</td>
										</tr>
										<tr>
											<td>3</td>
											<td>Active Current</td>
											<%-- <td>${instantanousData[0].rPhaseActiveVal} A</td>
											<td >${instantanousData[0].yPhaseActiveVal} A </td>
											<td>${instantanousData[0].bPhaseActiveVal} A</td> --%>
											<td>0</td>
											<td>0</td>
											<td>0</td>
										</tr>
										 <tr>
											<td>4</td>
											<td>Reactive Current</td>
											<%-- <td>${instantanousData[0].rphaseReactiveCurrent} A</td>
											<td>${instantanousData[0].yphaseReactiveCurrent} A </td>
											<td>${instantanousData[0].bphaseReactiveCurrent} A</td> --%>
											<td>0</td>
											<td>0</td>
											<td>0</td>
										</tr> 
										<tr>
											<td>5</td>
											<td>Power Factor</td>
											<td>${instantanousData[0].pfR}</td>
											<td >${instantanousData[0].pfY}</td>
											<td>${instantanousData[0].pfB}</td>
										</tr>
										<tr>
											<td>6</td>
											<td>Phase Angle </td>
											<td>${rPhaseAngle}</td>
											<td>${yPhaseAngle}</td>
											<td>${bPhaseAngle}</td>
										</tr>
										<tr>
											<td>7</td>
											<td>Active Power</td>
										<%-- 	<td>${instantanousData[0].activePowerVal}</td> --%>
										<td>0</td>
											
										</tr>
										<tr>
											<td>8</td>
											<td>Reactive Power</td>
											<td>0</td>
									<%-- 		<td>${instantanousData[0].kvar}</td> --%>
										</tr> 
										<tr>
											<td>9</td>
											<td>Apparent Power</td>
											<td>0</td>
										<%-- 	<td>${instantanousData[0].kva}</td> --%>
										</tr> 
										<tr>
											<td>10</td>
											<td>Phase Sequence</td>
											<td>0</td>
										<%-- 	<td>${instantanousData[0].phaseSequence}</td> --%>
										</tr> 
									</tbody>
								</table>
							</div>
						</div>
						
					</div>
					<!-- END SAMPLE TABLE PORTLET-->
				</div>
				
			</div>
			</c:if>
			 <!-- end Instantanous -->
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