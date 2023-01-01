
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
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 
		 $('#MDMSideBarContents,#reportsId,#consumptionCurveMDM').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		 
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
	</script>
	<script>
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
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
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
	
	 function showDivision(circle) {
		 var zone = $('#zone').val();
		
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
							html += "<select id='sdoCode' name='sdoCode' onchange='showMeternoBySubDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								//var response=response1[i];
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTd").html(html);
							$('#sdoCode').select2();
						}
					});
		}
	

	function showMeternoBySubDiv(subdivision)
	{
		var circle=$('#circle').val();
		var division=$('#division').val();
		//var subdivision=$('#sdoCode').val();
		var zone=$("#zone").val();
		$.ajax({
			url : './showmeternoBySubDivMDM',
		    	type:'GET',
		    	 dataType:'json',
		    	asynch:false,
		    	cache:false, 
		    	data : {
					zone : zone,
					circle : circle,
					division : division,
					subdivision : subdivision
				},
		    	success:function(response1)
		    	{
		    		
	  			var html='';
		    		html+="<select id='meterNo' name='meterNo' class='form-control input-medium' type='text'><option value=''>Meter No</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#meterTd").html(html);
					$('#meterNo').select2();
		    	}
			});
	}
	

	
/* 	function tdclick() {
		
		    var dataPoints = [];
			var frmDate ="${formatDate}";
			var mtrNo ="${meterNo}";
			
		$.ajax({
			    	url : './getAjaxConsumptionCurveMDM/'+mtrNo+'/'+frmDate,
			    	type:'GET',
			        success:function(data)
			    	{
			    	 for (var i = 0; i < data.length; i++) {
			    	    	
						      dataPoints.push({
						    	
						         x:new Date(data[i].IpInterval) ,
						    	 y: parseFloat(data[i].kwhValue)
						      });
						    } 

						    var chart = new CanvasJS.Chart("chartContainer", {
						      title: {
						        text: "Consumption Curve (Month Wise)"
						      },
						      axisX: {
						        title: "Ip-Interval",
						        	 interval:1,
						             intervalType: "day"
						      
						      },
						      axisY: {
						        includeZero: true,
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
		  
} */
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
							 <%-- <c:if test = "${not empty formatDate}"> 			        
						   <script>	
						   tdclick();
						   </script>
	  					</c:if>	 --%>
	  					
						<div class="portlet-body">
						<form action="" id="cmriId" method="POST">
							<table>
							<tr>
								<th class="block">Zone&nbsp;:</th>
								<th id=""><select class="form-control select2me input-medium"
									id="zone" name="zone" onchange="showCircle(this.value);">
									<option value=""></option>
									<option value="%">ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle" onchange="showDivision(this.value);">
								</select></th>
								
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value);">
								</select></th>
								</tr>
								
								<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode" onchange="showMeternoBySubDiv(this.value);">
									</select></th>
									
									<th class="block">Meter no:</th>
								<th id="meterTd"><select class="form-control select2me input-medium" name="meterNo" id="meterNo" >
									</select></th>
								
							<td><b>Select Month:</b></td>
							<td>
							<div class="input-group">			
									<input type="text" class="form-control from"  id="reportFromDate"
										name="reportFromDate" placeholder="Select Month" value="${readingMonth}" style="cursor: pointer"> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							
							
							
						<%-- 	<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div> --%>
							</td>	
						   </tr>
						</table>
						
							<div class="modal-footer">
							<button type="button" class="btn blue pull-left" onclick="return validation()">Generate Consumption List</button>  
							</div>
                           </form>								
						</div>
						</div>
					</div>
			    </div>
            

        <c:if test="${ConsumerConsumptionError eq 'Consumer Consumption Data Not Found...'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${ConsumerConsumptionError}</span>
			</div>
		</c:if>
		
		<%-- <c:if test="${ ConsumerConsumptionShow eq 'ConsumerConsumptionShow'}"> --%>

					
				 <div class="portlet box blue" id="consumptionlist" hidden="true">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Consumer Consumption List:
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 650px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>&nbsp;&nbsp;
						  <!-- <button class="btn green pull-right" data-dismiss="modal" class="btn">Close</button> -->
						  </div>
						</div>
						
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>METER NO</th>
										<th>Bill Month</th>
										<th>Profile Date</th>
										<th>KWH-VALUE</th>
								   </tr>
								</thead>

						<tbody id="consumptionlistdata">		
						

								</tbody>
							</table>
							
						</div>
						
					</div>
					
				<%-- 	</c:if> --%>
					<div id="chartContainer" style="height: 370px; width: 100%;"></div>
					</div>
				
				
<script>
/* function validation()
{
	
	var circle=$("#circleId").val();
	var rdngMonth=$("#reportFromDate").val();
	var part=$("#part").val();
	var reportName=$("#reportName").val();
	
	if(reportName=="Consumer Consumption Curve")
	{
		$("#cmriId").attr("action","./getConsumptionCurveMDM");
	}
	
  	} */
  	
  	
function validation() {
  		
  		var meterNo=$("#meterNo").val();
  		var reportFromDate=$("#reportFromDate").val();
  		
  		if(meterNo=="") {
  			bootbox.alert("Select Meter No")
  			return false; 
  			}  
  		
  		
  		if(reportFromDate=="") {
  			bootbox.alert("Select Bill Month")
  			return false; 
  			}  
	
  		var mtrno=$("#meterNo").val();
  		var billmonth=$("#reportFromDate").val();
  		 clearTabledataContent('sample_editable_1');
		var html1="";
			$.ajax({
		    	url : './getConsumptionData/'+mtrno+'/'+billmonth,
		    	type:'GET',
		    	success:function(response)
		    	
		    	{
		    	  $("#consumptionlist").show();
		    	  
		    	   for (var i = 0; i < response.length; i++) 
		    	 {
		    		  var res=response[i];
		    		  
		    		  html1+='<tr><td>'+res[0]+'</td>'+
						'<td>'+res[1]+'</td>'+
						'<td>'+res[3]+'</td>'+
						'<td>'+res[2]+'</td></tr>'
		    		 
				}
		    	   $("#consumptionlistdata").html(html1);
		    	 
		    	},
		    	complete: function()
				{  
					loadSearchAndFilter('sample_editable_1');
				} 
				
			  });
	
	var dataPoints = [];
	var mtrno =$("#meterNo").val();
	var mtrNo =$("#reportFromDate")
	$.ajax({
		
		url : './getAjaxConsumptionCurveMDM/'+mtrno+'/'+billmonth,
		type:'GET',
    	success:function(data)
    	{
    	for (var i = 0; i < data.length; i++) {
	    	
		      dataPoints.push({
		    	
		         x:new Date(data[i].IpInterval) ,
		    	 y: parseFloat(data[i].kwhValue)
		      });
		    } 

		    var chart = new CanvasJS.Chart("chartContainer", {
		      title: {
		        text: "Consumption Curve (Month Wise)"
		      },
		      axisX: {
		        title: "Ip-Interval",
		        	 interval:1,
		             intervalType: "day"
		      
		      },
		      axisY: {
		        includeZero: true,
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

  	
  	
  	function clearTabledataContent(tableid)
	{
		 //TO CLEAR THE TABLE DATA
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
	}
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

<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});

</script>
