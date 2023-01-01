
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 
		$('#MDMSideBarContents,#consumerConsumptionMDAS').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		 
    	 $("#analysedPrintId").click(function(){ 
	    	 printcontent($("#excelUpload .table-scrollable").html());
		});

    
		 $('#circleId').change(function() { 
	    	    var dropVal = $(this).val();
	    	    sessionStorage.setItem("SelectedItem", dropVal);
	    	});
	    	
	     var selectedItem = sessionStorage.getItem("SelectedItem");  
	   
	   	    if(selectedItem==null)
	   	    {
	   	    	$("#circleId").val("0").trigger("change"); 	 
	   	    }else{
	   	    	$("#circleId").val(selectedItem).trigger("change");
	   	    }
      
	   	    
	});
	
	
	function tdclick() 
	{
		
			  var dataPoints = [];
			  var frmDate=$("#reportFromDate").val();
				console.log("date=="+frmDate);
				//alert(frmDate);
				var mtrNo =$("#meterNo").val();
				//alert(mtrNo);
				console.log("MTR=="+mtrNo);
				$("#excelUpload").show();
				 var session= '@Session["officeType"]';
				 //alert(session);
				console.log(session);
				
				$.ajax({
				    	url : './getAjaxConsumptionCurveMDAS/'+mtrNo+'/'+frmDate,
				    	type:'GET',
				    	dataType:'json',
				    	
				    	
				    	success:function(data)
				    	{
				    	    for (var i = 0; i < data.length; i++) {
				    	    	
							      dataPoints.push({
							    	/* x:new Date(moment(data[i].IpInterval).format("YYYY"), moment(data[i].IpInterval).format("MM"), moment(data[i].IpInterval).format("DD")), */
							         x:new Date(data[i].IpInterval) ,
							    	 y: parseFloat(data[i].kwhValue)/1000
							      });
							    }

							    var chart = new CanvasJS.Chart("chartContainer", {
							      title: {
							        text: "Consumption Curve (Day Wise)"
							      },
							      axisX: {
							        title: "Ip-Interval",
							        	 interval:1,
							             intervalType: "hour"
							      
							      },
							      axisY: 
							      {
							        includeZero: false,
							        title: "kwh-Value", 
							      },
							      data: [{
							        type: "spline",
							        dataPoints: dataPoints
							      }]
							    });

							    chart.render();	
				    		
				    	},
						  
					  });
			  
	}
	
	/* function getConsumptionList(){
		var rdngMonth=$("#reportFormDate").val();
		var meterNo=$("#meterNo").val();
		if(rdngMonth ==''){
			bootbox.alert("Please input Date.");
			return false;
		}
		if (meterNo == ''){
			bootbox.alert("Please select Meter No.");
			return false;
		}
		
		$.ajax({
			url:'./getList',
			type:'get',
			success:function(response){
				if(response!=null){
					
				}
			}
			
		});
		$("#consumptinId").show();
	}
	 */
function consumptionCharges(){
	var rdngMonth=$("#reportFromDate").val();
	var meterNo=$("#meterNo").val();
	if (rdngMonth == ''){
		bootbox.alert("Please input Date.");
		return false;
	}
	if (meterNo == ''){
		bootbox.alert("Please select Meter No.");
		return false;
	}
	
	
	$.ajax({
		url:'./dailyConsumptionCharges/'+meterNo+'/'+rdngMonth,
		type:'get',
		success:function(res){
			if(res!=null){
				
			
			var json = $.parseJSON(res);
			$("#mtrnoid").html(meterNo);
			$("#dateid").html(rdngMonth);
			jQuery.each(json, function (index, value) {
		        if (typeof value == 'object') {
		            if(index==1){
		            	$("#tz1c").html(value.tz1_consumption);
		            	$("#tz1p").html(value.tz1_ecamount);
		            }
		            else if(index==2){
		            	$("#tz2c").html(value.tz2_consumption);
		            	$("#tz2p").html(value.tz2_ecamount);
		            }
		            else if(index==3){
		            	$("#tz3c").html(value.tz3_consumption);
		            	$("#tz3p").html(value.tz3_ecamount);
		            }
		            else if(index==4){
		            	$("#tz4c").html(value.tz4_consumption);
		            	$("#tz4p").html(value.tz4_ecamount);
		            }
		            else if(index==5){
		            	$("#tz5c").html(value.tz5_consumption);
		            	$("#tz5p").html(value.tz5_ecamount);
		            }
		            else if(index==6){
		            	$("#tz6c").html(value.tz6_consumption);
		            	$("#tz6p").html(value.tz6_ecamount);
		            }
		        }
			});
			$("#chargesID").show();
		}
			else{
				bootbox.alert("Wrong inputs or Server down");
			}
		}
	
	});
}	
</script>

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

<div class="page-content" >
<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Consumer Consumption Curve</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>	
						
						<c:if test = "${not empty formatDate}"> 			        
						   <script>	
						   alert('clicked==>');
						   tdclick();
						   </script>
	  					</c:if>				
											
						<div class="portlet-body">
							
							<form action="" id="cmriId" method="POST">
							<table>

							<tr>
							<td>Date and Month :</td>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>
							<!-- <td>  Enter Meter No :</td>
								<td>
							
							<input type="text" class="form-control" name="meterNo" id="meterNo" autocomplete="off" >
							</td> -->
							<td>  Enter Meter No :</td>
							<td><select class="form-control select2me input-medium"
										name="meterNo" id="meterNo" onchange="selectMeter(this.value);">
											<option value=''>Select</option>
											<c:forEach var="elements" items="${meterNoList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>
							</tr>
							
							
							</table>
							<div class="modal-footer">
							<button type="button" class="btn blue pull-left" onclick="return validation()">Generate Consumption Curve</button>  
							<button type="button" class="btn blue pull-left" onclick="return consumptionCharges()">Consumption Charges</button>  
							
							</div>
							
							</form>	
							<div style="display: none;" id="chargesID"><table class="table table-striped table-hover table-bordered" >
								<thead>
									<tr>
										<th>METER NO</th>
										<th>DATE</th>
										<th>TZ1(kWh)</th>
										<th>TZ1(Rs)</th>
										<th>TZ2(kWh)</th>
										<th>TZ2(Rs)</th>
										<th>TZ3(kWh)</th>
										<th>TZ3(Rs)</th>
										<th>TZ4(kWh)</th>
										<th>TZ4(Rs)</th>
										<th>TZ5(kWh)</th>
										<th>TZ5(Rs)</th>
										<th>TZ6(kWh)</th>
										<th>TZ6(Rs)</th>
										
										
									</tr>
								</thead>
								
							<tbody>
							<tr>
							<td id="mtrnoid"></td>
							<td id="dateid"></td>
							<td id="tz1c"></td>
							<td id="tz1p"></td>
							<td id="tz2c"></td>
							<td id="tz2p"></td>
							<td id="tz3c"></td>
							<td id="tz3p"></td>
							<td id="tz4c"></td>
							<td id="tz4p"></td>
							<td id="tz5c"></td>
							<td id="tz5p"></td>
							<td id="tz6c"></td>
							<td id="tz6p"></td>
							</tr>
							</tbody>
							</table></div>

                      <div  style="display: none;" id="consumptinId" class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Consumer Consumption List:
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 150px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body"  id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>METER NO</th>
										<th>DATE</th>
										<th>INTERVAL-PERIOD</th>
										<th>KWH-VALUE</th>
									
									</tr>
								</thead>
								
							<tbody id="listID">
							</tbody>
							</table>
							</div>
							</div>		
					  <div id="chartContainer" style="height: 370px; width: 100%;"></div>
												
						</div>
						
					</div>
					
					 
				</div>
			</div>
</div>

<script>
function validation()
{
	
	var rdngMonth=$("#reportFromDate").val();
	var meterNo=$("#meterNo").val();
	if (rdngMonth == ''){
		bootbox.alert("Please input Date.");
		return false;
	}
	if (meterNo == ''){
		bootbox.alert("Please select Meter No.");
		return false;
	}
	
	
	
	$.ajax({
		url:'./getConsumptionCurveMDAS/'+meterNo+'/'+rdngMonth,
		type:'get',
		success:function(response)
		{

			for(var i=0; i<response.length; i++)
			{
					//alert(response[i]);
					console.log("date....."+response[i][0]);
					//console.log(response[i][1]+"1 "+response[i][2]+" 2 "+response[i][3]+" 3 "+response[i][4]+" 4 "+response[i][5]+" 5 "+response[i][6]+" 6 "+response[i][7]);
					//console.log(response[i][0]+"0 "+response[i][8]+" 2 "+response[i][9]+" 3 "+response[i][10]+" 4 "+response[i][11]+" 5 "+response[i][12]+" 6 "+response[i][13]);
			    var html1;
				html1+='<tr><td>'+response[i][4]+'</td>'+ 
				'<td>'+moment(response[i][3]).format('YYYY-MM-DD HH:mm:ss')+'</td>'+
				'<td>'+30+'</td>'+
				'<td>'+response[i][12]/1000+'</td>'+
				'</tr>';
			}
	
			//$("#sample_editable_1").html(html1);
			$("#listID").html(html1);
			loadSearchAndFilter('sample_editable_1');
	
			//$("#sample_editable_1").empty();
			//$("#sample_editable_1").append();
			$("#consumptinId").show();
			$("#cmriId").attr("action","./getConsumptionCurveMDAS");
			tdclick();
		}

	});
	
		
		
		
		 
		
		

		/* $("#reportName").val('${report_name}'); */
	}




/* --------------  Show Image Method----------------- */
 
 
 
 
   
</script>


<script>


/* document.getElementById('export').addEventListener('click',
		  exportPDF);
 */
		var specialElementHandlers = {
		  // element with id of "bypass" - jQuery style selector
		  '.no-export': function(element, renderer) {
		    // true = "handled elsewhere, bypass text extraction"
		    return true;
		  }
		};

	/* 	function exportPDF() {

		  var doc = new jsPDF('p', 'pt', 'a4');
		  //A4 - 595x842 pts
		  //https://www.gnu.org/software/gv/manual/html_node/Paper-Keywords-and-paper-size-in-points.html


		  //Html source 
		  var source = document.getElementById('sample_editable_1').innerHTML;

		  var margins = {
		    top: 10,
		    bottom: 10,
		    left: 10,
		    width: 1024
		  };

		  doc.fromHTML(
		    source, // HTML string or DOM elem ref.
		    margins.left,
		    margins.top, {
		      'width': margins.width,
		      'elementHandlers': specialElementHandlers
		    },

		    function(dispose) {
		      // dispose: object with X, Y of the last line add to the PDF 
		      //          this allow the insertion of new lines after html
		      doc.save('Test.pdf');
		    }, margins);
		} */


</script>



<style>
imgId
{
margin-left: 50%;
}
</style>
