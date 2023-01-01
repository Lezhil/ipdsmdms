<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>
<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>

<script>
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						$('#eaId,#lossAnalysis').addClass(
								'start active ,selected');
						$(
								'#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
								.removeClass('start active ,selected');
					});
</script>
<script>
$(document).ready(function(){

var element = $("#table1"); // global variable
var getcanvas1; // global variable
 
    $("#btn-Preview-Image").on('click', function () {
         html2canvas(element, {
         onrendered: function (canvas) {
                $("#previewImage").append(canvas);
                getcanvas1 = canvas;
             }
         });
    });
    
	$("#btn-Convert-Html2Image").on('click', function () {
    var imgageData = getcanvas1.toDataURL("image/jpeg");
    var newData = imgageData.replace(/^data:image\/jpeg/, "data:application/octet-stream");
    $("#btn-Convert-Html2Image").attr("download", "lossanalysis.jpeg").attr("href", newData);
	});

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
    		html+="<select id='LFcircle' name='circle' class='form-control' onchange='showTownNameandCode(this.value)' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
			for( var i=0;i<response.length;i++)
			{
				html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
			}
			html+="</select><span></span>";
			$("#LFcircleTd").html(html);
			$('#LFcircle').select2();
    	}
	});
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
              html += "<select id='LFtown' name='town'  class='form-control  input-medium'  type='text'><option value=''>Select Town</option>";
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
	<div class="portlet box blue" id="table1">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-bars"></i>Loss Analysis
			</div>
		</div>

		<div class="portlet-body" >
		<!-- starting of common filter -->
			<jsp:include page="locationFilter.jsp"/>
		<!-- 	End of common filter  -->
		<div class="row" style="margin-left: -1px;">
		
		<div class="col-md-3" >
		<strong>Report Month</strong><div id="fromDateMonthly" >
		<input type="text" class="form-control from"  name="selectedDateName" id="selectedFromDateId" style="width:200px;" 
										 placeholder="Select From Month" style="cursor: pointer">
										 </div>
										 </div>
										 	 
										 <div class="col-md-3" >Select Period :
						 <div id="periodMonthTh" style="width:100px;">
						 				<select class="form-control select2me"
												id="periodMonth" name="periodMonth"
												onchange="showDivision(this.value);">
												<option value="12">12</option>
												<option value="10">10</option>
												<option value="08">8</option> 
												<option value="06">6</option>
												<option value="04">4</option>
												<option value="02">2</option>
												<option value="00">0</option>
											</select>
											</div>
											</div>
											
											<div class="row" style="margin-left: -1px; padding-top:15px;margin-bottom: 6px;">
											<div class="btn green"  button type="submit" id="generate" onclick="GeneratGraph()" class="btn green">Generate</button> </div>
											  <div class="btn yellow" id="print" onclick="exportGridData()">Download </div>
											</div>
											
											<!-- <div class="row" style="margin-left: -1px;">
											<div class="btn green"  button type="submit" id="generate" onclick="GeneratGraph()" class="btn green">Generate</button> </div>
											  <div class="btn yellow" id="print" onclick="exportGridData()">Download </div>
		</div>
		 -->
		<%-- 	<table>
			   <tbody>
					
							
					<tr>
						 <th class="block">Report Month:</th>
						 <th id="fromDateMonthly">
						 				<input type="text" class="form-control from"  name="selectedDateName" id="selectedFromDateId" style="width:200px;" 
										 placeholder="Select From Month" style="cursor: pointer">
						 
						 </th>
						 <th class="block" style= "padding: 15px";>Select Period :</th>
						 <th id="periodMonthTh" style="width:100px;">
						 
						 				<select class="form-control select2me"
												id="periodMonth" name="periodMonth"
												onchange="showDivision(this.value);">
												<option value="12">12</option>
												<option value="10">10</option>
												<option value="08">8</option> 
												<option value="06">6</option>
												<option value="04">4</option>
												<option value="02">2</option>
												<option value="00">0</option>
											</select>
						 
						 </th>	
									
									
								</tr>
								<tr>	
								
								
															
							 		<div class="row" style="margin-left: -1px;">
							 		<jsp:include page="locationFilter.jsp"/>  --%>
									<%-- <div class="col-md-3">
										<strong>Region:</strong><div id="zoneTd" class="form-group">
											<select class="form-control select2me"
												id="zone" name="zone"
												onchange="showCircle(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option> 
												<c:forEach items="${zoneList}" var="elements">
													<option value="${elements}">${elements}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<strong>Circle:</strong><div id="circleTd" class="form-group">
											 <select class="form-control select2me"
												id="circle" name="circle"
												onchange="showTownNameandCode(this.value);">
												<option value="">Select</option>
												<option value="%">ALL</option> 
												
											</select>
										</div>
									</div>
								
									<div class="col-md-3">
								         <strong>Town:</strong>
												<div id="townTd" class="form-group"><select
													class="form-control select2me input-medium" id="town" 
													name="town">
													<option value="">Select</option>
												
												</select></div>
									</div> --%>
						<!-- 			</div>
								</tr> 
						
								<tr>
								
										<th>                       
												<button type="button" class="btn yellow">Reset</button>  
												<button type="submit" id="generate" onclick="GeneratGraph()" class="btn green">Generate</button> 
											    <a class="btn yellow" id="print" onclick="exportGridData()">Download</a>
											    <a class="btn yellow" id="btn-Convert-Html2Image" href="#">Download</a>
				 								<input id="btn-Preview-Image" style="display:none" type="button" value="Preview"/>
										</th>	
								
							</tr>
							
							
					</tbody>
				</table>
				 -->
			
			<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
			<div class="row">
				<div class="col-md-6">
					<div id="chartContainer1" style="height: 250px; width: 100%;"></div>
				</div>
				<div class="col-md-6">
					<div id="chartcontainer2" style="height: 250px; width: 100%;"></div>
				</div>
			</div>
			<br>
			<br>

			
			<div class="row">
				<div class="col-md-6">
					<div id="chartcontainer3" style="height: 250px; width: 100%;"></div>
				</div>
				<div class="col-md-6">
					<div id="chartcontainer4" style="height: 250px; width: 100%;"></div>
				</div>
			</div>
			
			<div style="display:none" id="previewImage">
    </div>
			<!-- <div class="">
<div id="chartcontainer3" style="height: 370px; width: 100%;"></div>
</div>  

<div class="">
<div id="chartcontainer4" style="height: 370px; width: 100%;"></div>
</div>  -->

		</div>
	</div>
</div>
<script>
	function GeneratGraph() {
		var billmonth = $("#selectedFromDateId").val();
		var period = $("#periodMonth").val();
		var townCode = $("#LFtown").val();
		var region = $("#LFzone").val();
		var circle = $("#LFcircle").val();
		
		 
	/* 	var fmonth ='201910'; 
		//alert("fmonth..."+fmonth);
		var tmonth ='201910';
		//alert("tmonth..."+tmonth);
		var circle = '201910';
		//alert("circles..."+circle) */

		if (region == "") {
			bootbox.alert("Please Select Region.");
			return false;
		}
		
		if (circle == "") {
			bootbox.alert("Please Select Circle.");
			return false;
		}
		
		if (townCode == "") {
			bootbox.alert("Please Select Town");
			return false;
		}
		
		if (billmonth == "") {
			bootbox.alert("Please Select Report Month");
			return false;
		}
		
		if (period == "") {
			bootbox.alert("Please Select Period");
			return false;
		}
		
		
		$('#imageee').show();
		var data2 = [];
		$
				.ajax({
					url : './getAtcLosses',
					type : 'POST',
					data: {
						billmonth:billmonth,
						period:period,
						townCode:townCode
					},
					dataType : 'json',
					success : function(response) {
						$('#imageee').hide();
						for (var i = 0; i < response.length; i++) {
							data2.push(response[i])
						}

						var chart = new CanvasJS.Chart("chartContainer1", {
							animationEnabled : true,

							title : {
								text : "AT&C"
							},
							axisX : {
								interval : 1,
								title : "Feeder Name"
							},
							axisY : {
								gridColor : "rgba(1,77,101,.1)",
								includeZero : true,
								
								
								suffix : "%",
								
							},
							data : [ {
								type : "bar",
								color : "#014D65",
								indexLabel: "{y}",
								indexLabelFontColor: "black",
								indexLabelPlacement: "outside",
								dataPoints : data2
							} ]
						});
						chart.render();
					}
				});
		GenerateGraph1();
	}
	function GenerateGraph1() {
		var billmonth = $("#selectedFromDateId").val();
		var period = $("#periodMonth").val();
		var townCode = $("#LFtown").val();

		var data3 = [];
		$.ajax({
			url : './getTdLosses',
			type : 'POST',
			dataType : 'json',
			data: {
				billmonth:billmonth,
				period:period,
				townCode:townCode
			},
			
			success : function(response) {
				$('#imageee').hide();
				for (var i = 0; i < response.length; i++) {
					data3.push(response[i])
				}

				var chart = new CanvasJS.Chart("chartcontainer2", {
					animationEnabled : true,

					title : {
						text : "T&D"
					},
					axisX : {
						interval : 1,
						title : "Feeder Name"
					},
					axisY : {
						gridColor : "rgba(1,77,101,.1)",
						includeZero : true,
						
						suffix : "%"
					},
					data : [ {
						type : "bar",
						color : "#014D65",
						indexLabel: "{y}",
						indexLabelFontColor: "black",
						indexLabelPlacement: "outside",
						dataPoints : data3
					} ]
				});
				chart.render();
			}
		});
		GenerateGraph2();
	}
	function GenerateGraph2() {
		var billmonth = $("#selectedFromDateId").val();
		var period = $("#periodMonth").val();
		var townCode = $("#LFtown").val();

		var data4 = [];
		$.ajax({
			url : './getBillingEfficiencyLosses',
			type : 'POST',
			dataType : 'json',
			data: {
				billmonth:billmonth,
				period:period,
				townCode:townCode
			},
			success : function(response) {
				$('#imageee').hide();
				for (var i = 0; i < response.length; i++) {
					data4.push(response[i])
				}

				var chart = new CanvasJS.Chart("chartcontainer3", {
					animationEnabled : true,

					title : {
						text : "Billing Efficiency"
					},
					axisX : {
						interval : 1,
						title : "Feeder Name"
					},
					axisY : {
						gridColor : "rgba(1,77,101,.1)",
						includeZero : true,
						suffix : "%"
					},
					data : [ {
						type : "bar",
						color : "#014D65",
						indexLabel: "{y}",
						indexLabelFontColor: "black",
						indexLabelPlacement: "outside",
						dataPoints : data4
					} ]
				});
				chart.render();
			}
		});
		GenerateGraph3();
	}
	function GenerateGraph3() {
		var billmonth = $("#selectedFromDateId").val();
		var period = $("#periodMonth").val();
		var townCode = $("#LFtown").val();
		var data5 = [];
		$.ajax({
			url : './getCollectionEfficiencyLosses',
			type : 'POST',
			dataType : 'json',
			data: {
				billmonth:billmonth,
				period:period,
				townCode:townCode
			},
			success : function(response) {
				$('#imageee').hide();
				for (var i = 0; i < response.length; i++) {
					data5.push(response[i])
				}

				var chart = new CanvasJS.Chart("chartcontainer4", {
					animationEnabled : true,

					title : {
						text : "Collection Efficiency"
					},
					axisX : {
						interval : 1,
						title : "Feeder Name"
					},
					axisY : {
						gridColor : "rgba(1,77,101,.1)",
						includeZero : true,
				
						suffix : "%"
					},
					data : [ {
						type : "bar",
						color : "#014D65",
						indexLabel: "{y}",
						indexLabelFontColor: "black",
						indexLabelPlacement: "outside",
						click : onClick,
						dataPoints : data5

					} ]
				});

				chart.render();
				function onClick(e) {
				//	alert("Hi");

				}
			}
		});
		/* setTimeout(function(){

			$("#btn-Preview-Image").click();
			
	    },1000); */
	}
</script>

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


	 function exportGridData()
	 {
	 	$("#generate").hide();
	 	$("#print").hide();
	 	$("#LFtown").hide();
	 	$("#LFcircle").hide();
	 	$("#LFzone").hide();
	 	$("#periodMonth").hide();
	 	
	 	html2canvas(document.getElementById('table1'), {
	         onrendered: function (canvas) {
	             var data = canvas.toDataURL();
	             var docDefinition = {
	                 content: [{
	                     image: data,
	                     width: 500
	                 }]
	             };
	             pdfMake.createPdf(docDefinition).download("lossanalysis.pdf");
	             $("#generate").show();
	     	 	 $("#print").show();
	          }
	        });

	 }
</script>


