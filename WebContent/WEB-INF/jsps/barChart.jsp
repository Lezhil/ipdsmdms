<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script src="<c:url value='/resources/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/highcharts-more.js'/>" type="text/javascript"></script> 
<script src="<c:url value='/resources/highcharts.src.js'/>" type="text/javascript"></script> 
<script src="<c:url value='/resources/highcharts-more.src.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/css/font-awesome.css'/>" type="text/javascript"></script> 
<script src="<c:url value='/resources/assets/css/jquery-ui.css'/>" type="text/javascript"></script>  
<script src="<c:url value='/resources/assets/css/ref.css'/>" type="text/javascript"></script>  

<!-- <script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script> -->

<style>
#bmdButton
{
	margin-bottom: 10px;
	display:inline-block; 
}
#btnId
{
  font-size: 14px;
 background-color: #AAD4FF;
}
</style>
 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	  TableEditable.init();
   	    	 FormComponents.init();
   	    	   		$('#Bar-graph').addClass('start active ,selected');
   	    	   	 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
   	    	  if('${dateList.size()}'>0)
	   	 	  {
	   	 	    $('#d4ListId' ).show();
	   	 	  }
   	    	  else
   	    	 {
   	    		   $('#d4ListId' ).hide();
   	    	 }
   	    	   
   	    	   });
   
  
  
  var hoursInterval=[];
  
  var L1Intervals = [];
  var L2Intervals = [];
  var L3Intervals = [];
  function getD4LoadData(billDate)
  {
	  L1Intervals.length=0;
	  L2Intervals.length=0;
	  L3Intervals.length=0;
	  var meterNo=$('#mtrNo').val();
	  var billMonth=$('#bMonth').val();
	    $.ajax({
	    	url:'./getIntervalD4LoadData1'+'/'+meterNo+'/'+billMonth+'/'+billDate,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
	    		
	    		for (var i = 0; i < response.l1amp.length; i++)
	    		{
	    			L1Intervals.push(parseFloat(response.l1amp[i]));
	    		}
	    		for (var i = 0; i < response.l2amp.length; i++)
	    		{
	    			L2Intervals.push(parseFloat(response.l2amp[i]));
	    		}
				for (var i = 0; i < response.l3amp.length; i++)
	    		{
					L3Intervals.push(parseFloat(response.l3amp[i]));
	    		}
	    		displayChart(L1Intervals,L2Intervals,L3Intervals,billDate,meterNo);
	    	}
	  });  
	 
	    return false;	
  }
  
  function displayChart(L1Intervals,L2Intervals,L3Intervals,billDate,meterNo)
  {
	  $('#container').empty();
	  for (var i=1; i<=48; i++)
      {
		  /* if(i!=0)
			  {
			    i=i-0.5;
			    hoursInterval.push(i);
			  } */hoursInterval.push(i);
      }
	  var yAxisLabels = [0.0,0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0];
	  $('#container').highcharts({
      chart: {
          type: 'column',
          //marginBottom: 100,
      },
      title: {
          text: 'Graphical Representation'+'  '+'['+billDate+']'+' '+'['+meterNo+']'
      },
     
      xAxis: {
          categories:hoursInterval,
         //min:0,
          max:47,
          tickWidth: 1,
          marginBottom: 100,
          minorTickLength:0,
          crosshair: true,
          
      },
      yAxis: {
    	 // width:1,
    	  tickWidth: 1,
    	  marginBottom: 100,
    	  tickPositioner: function() {
              return yAxisLabels;
          },
          title: {
              text: 'phasewisecurrent (in amp)'
          },
      
      },
      tooltip: {
          headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
          pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
              '<td style="padding:1"><b>{point.y:3f} amp</b></td></tr>',
          footerFormat: '</table>',
          shared: true,
          useHTML: true
      },
      plotOptions: {
          column: {
              pointPadding: 0.1,
              borderWidth: 0
          }
      },
      series: [{
          name: 'L1',
          color: '#FF0000',
          data: L1Intervals

      }, {
          name: 'L2',
          color: '#FFFF00',
        data: L2Intervals

      }, {
          name: 'L3',
          color: '#0000FF',
         data: L3Intervals

      }, ]
  });	
	  
	  
  }
  
  </script>
<div class="page-content" >
<div class="portlet box blue">
	 
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>Graphical Representation</div>   
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						
						
						<div class="portlet-body">
						
						 <div class="col-md-12">
										
										<form method="post" id="meterMasterForm" action="./getIntervalD4LoadData">
										<table>
										<tr><td>Meter Number</td><td><input type="text" class="form-control" id="meterVal" name="meterNo"></td></tr>
										<tr><td>Select YearMonth</td><td>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyymm" data-date-viewmode="years" id="five">
																<input type="text" class="form-control" name="selectedDateName" id="selectedDateId"   readonly>
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></td></tr>
								
										</table>
										<div class="modal-footer">
							        <button class="btn blue pull-left">view</button>  
							</div>
										</form>
						</div>
						</div>
		</div>
		<div class="portlet box blue" id="d4ListId" style="display: none;" >
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-reorder"></i>D4 Load Data &nbsp;&nbsp;[${meterNumber}]&nbsp;&nbsp;[${selectedData}]</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#portlet-config" data-toggle="modal" class="config"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body" >
						<c:forEach var="element" items="${dateList}">
						<form id="bmdButton" action="" method="post">
							<input type="text" hidden="true" id="bMonth" name="bMonth" value="${element[2]}"/> 
							<input type="text" hidden="true" id="mtrNo" name="mtrNo" value="${element[1]}"/>
							<fmt:parseDate value="${element[0]}" pattern="dd-MM-yyyy" var="dayProfileDate"/>
							<button id="btnId" type="submit" title="Click here to see bill details"  style="text-align: left;" onclick="return getD4LoadData('${element[0]}');"><fmt:formatDate value="${dayProfileDate}" pattern="dd-MMM-yyyy"/></button>
						</form>	
						</c:forEach>
						</div>
					</div>
					<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					
</div>