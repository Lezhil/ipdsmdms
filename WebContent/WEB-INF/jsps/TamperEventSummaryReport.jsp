<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>

<!-- <export pdf> -->
<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#MIS-Reports").addClass('start active , selected');

						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();
						TableManaged.init();
						$('#month').val('');
						loadSearchAndFilter('sample_1');
						console.log('${officeCode}'+" "+'${officeType}');
						   // alert('${officeType}');
							var session='';
							session = "${officeType}";
							if(session=='sub division'){
								//alert(session);
								$('#zonelab').hide();
								$('#zones').hide();
								$('#circlelab').hide();
								$('#circleTd').hide(); 
								$('#divlab').hide();
							    $('#divisionTd').hide(); 
							   showSubdivByDiv('${officeCode}');
								
							}
							  
						$('#MDMSideBarContents,#reportsId,#TamperEventSummaryReportId')
								.addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						$("#analysedPrintId").click(
								function() {
									printcontent($(
											"#excelUpload .table-scrollable")
											.html());
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
						
						    if('${result}' !='')
						    {
						    	alert('${result}');
						    } 

					});
	    

	$("#month").datepicker({
		format : "mm-yyyy",
		startView : "months",
		minViewMode : "months"
	});
</script>

<script>
	/* function showCircle(zone) {
		
				$.ajax({
					url:'./getcirclebyzone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data:{
						zone:zone
					},
					success : function(response) {
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
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

	function showDivision(circle)
	{
		var zone=$('#zone').val();
		$.ajax({
			url : './getdivisionbycircle',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					circle : circle
				},
		    	success:function(response)
		    	{
	  			var html='';
		    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
					for( var i=0;i<response.length;i++)
					{
						html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#divisionTd").html(html);
					$('#division').select2();
		    	}
			});
	}

	function showSubdivByDiv(division)
	{
		
		var zone=$('#zone').val();
		var circle=$('#circle').val();
		$.ajax({
			url : './getSubdivByDiv',
		    	type:'GET',
		    	dataType:'json',
		    	asynch:false,
		    	cache:false,
		    	data : {
					circle : circle,
					division : division
				},
		    	success:function(response1)
		    	{
	  			var html='';
		    		html+="<select id='subdiv' name='subdiv' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='All'>ALL</option>";
					for( var i=0;i<response1.length;i++)
					{
						//var response=response1[i];
						html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>";
					}
					html+="</select><span></span>";
					$("#subdivTd").html(html);
					$('#subdiv').select2();
		    	}
			});
	} */

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
		    		html+="<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control select2me input-medium' type='text'><option value=''>Circle</option><option value='%'>ALL</option>";
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
							html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='%'>ALL</option>";
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
							html += "<select id='subdiv' name='subdiv' onchange='showTownBySubDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Sub-Division</option><option value='%'>ALL</option>";
							for (var i = 0; i < response1.length; i++) {
								html += "<option  value='"+response1[i]+"'>"
										+ response1[i] + "</option>";
							}
							html += "</select><span></span>";
							$("#subdivTd").html(html);
							$('#subdiv').select2();
						}
					});
		}


	 function showTownBySubDiv(Subdivision) {
         var zone = $('#zone').val();
         var circle = $('#circle').val();
         $.ajax({
             url : './getTownNameandCodeBySubDiv',
             type : 'GET',
             dataType : 'json',
             asynch : false,
             cache : false,
             data : {
             	sitecode:Subdivision
             },
                     success : function(response1) {
                         var html = '';
                         html += "<select id='townCode' name='townCode'  class='form-control input-medium' type='text'><option value=''>Select Town</option><option value='%'>ALL</option>";
                         for (var i = 0; i < response1.length; i++) {
                             //var response=response1[i];
                             html += "<option value='"+response1[i][0]+"'>"
                                     + response1[i][0]+"-"+response1[i][1] + "</option>";
                         }
                         html += "</select><span></span>";
                         $("#towndivTd").html(html);
                         $('#townCode').select2();
                     }
                 });
     
  
  }
	
	
	/* function printPdf(){
		var doc = new jsPDF('p', 'pt');
		var elem = document.getElementById("sample_1");
		var res = doc.autoTableHtmlToJson(elem);
		doc.autoTable(res.columns, res.data);
		doc.save("tamper_summary_report.pdf");
   } */
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Tamper Event Summary Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">
						<table style="width: 38%">
							<tbody>
								<tr>
									<th id="zonelab" class="block">Zone&nbsp;:</th>
									<th id="zones"><select
										class="form-control select2me input-medium" id="zone"
										name="circle" onchange="showCircle(this.value);">
										<option value=''></option>
											 <c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>	
									</select></th>
									<th class="block" id="circlelab">Circle&nbsp;:</th>
									<th id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle" onchange="showDivision(this.value);">
											<!-- <option id="getCircles" value=""></option> -->
									</select></th>
									<th class="block" id="divlab">Division&nbsp;:</th>
									<th id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division" onchange="showSubdivByDiv(this.value)">

									</select></th>
									
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th colspan="8"></th>
								</tr>
								<tr>
									<th class="block" id="sdolab">Sub&nbsp;Division&nbsp;:</th>
									<th id="subdivTd"><select
										class="form-control select2me input-medium" id="subdiv"
										name="subdiv">
										</select></th>
										
									<th class="block">Town:</th>
									<th id="towndivTd"><select
									class="form-control select2me input-medium" id="townCode"
									name="townCode"></select></th>	
								
									<th class="block">Report Month</th>
									<td>
									<div class="input-group">			
										<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Report Month" value="${readingMonth}" style="cursor: pointer"> <span
											class="input-group-btn">
											<button class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
									</div>
									
									
										<%--  <div class="input-group date date-picker"
											data-date-format="yyyymm" data-date-end-date="0d" data-date-viewmode="years" >
											<input type="text" class="form-control" name="month"
												id="month" value="${readingMonth}" readonly>
											<span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>  --%>
										 <!-- <div class="input-group">
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"> <span
											class="input-group-btn">
										<button class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div>  -->
									</td>
									<td>
										<button type="button" id="viewFeeder"
											style="margin-left: 20px;" onclick="return getSummaryReport()"
											name="viewconsumers" class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					
								<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
					
					<div class="table-toolbar">
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print" onclick="exportGridData()">Export To Pdf</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('sample_1', 'Tamper_summary_report')">Export to Excel</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_1">
						<thead>
							<tr>
								<th>Month-Year</th>
								<th>Subdivision</th>
								<!-- <th>KNo</th> -->
								<th>Location ID</th>
								<th>Location Name</th>
								<th>Meter Serial number</th>
								<th>Tamper Event Name</th>
								<th>Tamper Event Count</th>
								<!-- <th>Tamper Event Duration</th> -->
							</tr>
						</thead>
						<tbody id="TamperSummaryReportId">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

function getSummaryReport() {
	var zone =$('#zone').val();
	var circle=$('#circle').val();
	var division =$('#division').val();
	var sdocode=$('#subdiv').val();
	var townCode = $('#townCode').val();
	var rdngmnth=$('#month').val();
	/* if(zone=='All'){
		zone="%";
	}
	if(circle=='All'||circle==null){
		circle="%";
	}
	if(division=='All'||division==null){
		division="%";
	} */
	/* if(sdocode==''||sdocode==null){
		sdocode="%";
	} */

	if (zone == "") {
		bootbox.alert("Please Select zone");
		return false;
	}

	if (circle == "") {
		bootbox.alert("Please Select circle");
		return false;
	}

	if (division == "") {
		bootbox.alert("Please Select division");
		return false;
	}

	if (sdocode == "") {
		bootbox.alert("Please Select Subdivision");
		return false;
	}
	if(townCode=="")
	{
	bootbox.alert("Please select Town");
	return false;
	}
	 if(rdngmnth==''|| rdngmnth==0){
		 bootbox.alert("Please Select Month");
		 return false;
	 }
	
	
	$('#TamperSummaryReportId').empty();
	$('#imageee').show();
	$.ajax({
		url : './TamperSummaryReport',
		type : 'GET',
		data : {
			circle:circle,
			division:division,
			sdocode : sdocode,
			rdngmnth : rdngmnth,
			townCode:townCode
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			$('#imageee').hide();
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>" 
						+"<td>"+ element[0]+"</td>"
						+ "<td>"+ element[1]+ "</td>"
						/* + "<td>"+ element[2]+ "</td>" */
						+ "<td>"+ element[3]+ "</td>"
						+ "<td>"+ element[4]+ "</td>"
						+ "<td>"+ element[5]+ "</td>"
						+ "<td>"+ element[6]+ "</td>"
						+ "<td>"+ element[7]+ "</td>"
						/*  + "<td>"+ element[0]+ "</td>"  */
						/* + "<td>"+ (element[5]==null?"":(element[5]))+ "</td>"; */
	                    "</tr>";

				}
				$('#sample_1').dataTable().fnClearTable();
				$('#TamperSummaryReportId').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				bootbox.alert("No meters found");
			}

		}
	});

}

function exportGridData()
{
	//var exportGridFormat=$("#ExportGridFormat").val();
	
	html2canvas(document.getElementById('sample_1'), {
        onrendered: function (canvas) {
            var data = canvas.toDataURL();
            var docDefinition = {
                content: [{
                    image: data,
                    width: 500
                }]
            };
            pdfMake.createPdf(docDefinition).download("TamperSummaryData.pdf");
         }
       });

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

</script>















