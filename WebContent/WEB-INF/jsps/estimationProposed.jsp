
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 
<script src="<c:url value='/resources/jspdf.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jspdf.plugin.cell.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/FileServer.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jspdf.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

<%-- <script src="<c:url value='/resources/jspdf.plugin.From_html.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jspdf.plugin.split_text_to_size.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jspdf.plugin.standard_fonts_metrics.js'/>" type="text/javascript"></script>

<script src="<c:url value='/resources/jspdf.plugin.table.js'/>" type="text/javascript"></script> --%>




 <script  type="text/javascript">
  $(".page-content").ready(function()
   	    	   {     
   	    	     App.init();
   	    	 	 FormComponents.init();
   	    	$('#addAndUpdateAssessment,#estimationGraphicalView').addClass('start active ,selected');
   	    	   $("#admin-location,#dash-board").removeClass('start active ,selected');
   	    	     
   	    	   });
 
	 </script>


<div class="page-content" >
<div class="row">

<div id="container" style="min-width: 310px; height: 500px; margin: 0 auto">
<%
String totalReads =(String)request.getAttribute("totalReads");
String totalAss = (String)request.getAttribute("totalAss");
String totalAssCon = (String)request.getAttribute("totalAssConsumers");
String billmonth = (String)request.getAttribute("billmonth");
%>
<script type="text/javascript">

$(function () {
	var totReads = [<%=totalReads%>];
	var totalAss = [<%=totalAss%>];
	var totalAssCon = [<%=totalAssCon%>];
	var bill = [<%=billmonth%>];
	var phaseYaxis=[];
	
	for (var i=0; i<12; (i++))
      {
		  if(i==0)
			  {
			    phaseYaxis.push(i*1000);
			  }
			    
			    phaseYaxis.push(i*1000);
      }
	  var yAxisLabels = phaseYaxis;

	  
     $('#container').highcharts({
        chart: 
        {
            type: 'column'
        },
        title: {
            text: 'Monthly Assesment Details'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: bill
        },
        yAxis: {
            min: 0,
            tickWidth: 1,
	    	marginBottom: 100,
	    	 /*  tickPositioner: function() {
	              return yAxisLabels;
	          }, */
	          labels: {
	                format: '{value}'
	            },
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:14px">Month - {point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
            }
        },
        series: [{
            name: 'Total Reads',
            data: totReads

        }, {
            name: 'Total Assesments( In Lakhs)',
            data: totalAss

        }, {
            name: 'Total Assesment Consumers',
            data: totalAssCon

        }]
    }); 

     
     var rdata="";
		var ydata="";
		var bdata="";
		var billdata="";
		for(var i=0;i<totalAssCon.length;i++){
			billdata+="<td style='font-size: 7.4px;'><b>"+bill[i]+"</b></td>";
			rdata+="<td style='font-size: 7.4px;'>"+totReads[i]+"</td>";
			ydata+="<td style='font-size: 7.4px;'>"+totalAss[i]+"</td>";
			bdata+="<td style='font-size: 7.4px;'>"+totalAssCon[i]+"</td>";
			/* hdata+="<td style='font-size: 7.4px;'>"+hoursInterval[i]+"</td>"; */
		}
		var htmldata="<table border='1px' style='width: 100%;'>"
			+"<tr><td><b>Month</b></td>"+billdata+"</tr>"
			+"<tr><td><b>Total Reads</b></td>"+rdata+"</tr>"
			+"<tr><td><b>Total Assesment</b></td>"+ydata+"</tr>"
			+"<tr><td><b>Total Assesment Consumer</b></td>"+bdata+"</tr>"
			/* +"<tr><td>Hours</td>"+hdata+"</tr>" */
			+"</table>";
		$("#table1").html(htmldata);

});
</script>
</div>
<div id="table1"></div>

<!-- <div id="content">
     <h3>Hello, this is a H3 tag</h3>

    <p>a pararaph</p>
</div> -->
</div>
</div>