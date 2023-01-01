
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	 <!-- <script src="https://rawgit.com/dabeng/OrgChart/master/demo/js/jspdf.min.js"></script>  -->
	<script src="<c:url value='/resources/bsmart.lib.js/jspdf.min.js'/>" type="text/javascript"></script>
	
<!-- -----Export Grid Data----- -->
	<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
 -->
<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>

<script type="text/javascript">

	$(".page-content").ready(function() {
	
		
		$("#MIS-Reports").addClass('start active , selected');
		
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		loadSearchAndFilter('tamper_report');
		$("#month").val('');
		 
		$('#MDMSideBarContents,#reportsId,#analysedTamperReport').addClass('start active ,selected');
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

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

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
	                        html+="<option value='"+response[i]+"'>"+response[i]+"</option>";
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
	                            html += "<select id='sdoCode' name='sdoCode' onchange='showTownBySubDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
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
	                                        + response1[i][1] + "</option>";
	                            }
	                            html += "</select><span></span>";
	                            $("#towndivTd").html(html);
	                            $('#townCode').select2();
	                        }
	                    });
	        
	     
	     }
	    
	function printPdf(){
		var doc = new jsPDF('p', 'pt');
		var elem = document.getElementById("tamper_report");
		var res = doc.autoTableHtmlToJson(elem);
		doc.autoTable(res.columns, res.data);
		doc.save("tamper_report.pdf");
	}
</script> 
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Tamper Data Report
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
								<th id="zone1" class="block">Zone&nbsp;:</th>
								<th id="zones"><select
									class="form-control select2me input-medium" id="zone"
									name="zone" onchange="showCircle(this.value);">
										<option value=""></option>
										<option value="%">ALL</option>
										<c:forEach var="elements" items="${zoneList}">
											<option value="${elements}">${elements}</option>
										</c:forEach>
								</select></th>
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select
									class="form-control select2me input-medium" id="circle"
									name="circle" onchange="showDivision(this.value);">
									<option id="getCircles" value=""></option>
										<option value=""></option>
								</select></th>
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division" onchange="showSubdivByDiv(this.value)">

								</select></th></tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th colspan="8"></th>
							</tr>
							<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode"></select></th>
									
									<th class="block">Town:</th>
								<th id="towndivTd"><select
									class="form-control select2me input-medium" id="townCode"
									name="townCode"></select></th>
											
									<th class="block">Report Month</th>
							<td>
							<!-- <div class="input-group ">
								<input type="text" class="form-control from" id="month"
									name="month" placeholder="Select Month" style="cursor: pointer">
								<span class="input-group-btn">
									<button class="btn default" type="button">
										<i class="fa fa-calendar"></i>
									</button>
								</span>
							</div> -->
							
							
							<div class="input-group">
									<input type="text" class="form-control from"  id="month"
											name="month" placeholder="Select Month" style="cursor: pointer"> <span
											class="input-group-btn">
										<button class="btn default" type="button">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
							</div> 
							
								
					
							</td>
							
									
									<%-- <td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value="ALL">ALL</option>
											<c:forEach items="${subdivlist}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td> --%>
									
							
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewconsumers"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
					</div>
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print" onclick="exportGridData()">Export To Pdf</a></li>
								<li><a href="#" id="excelExport" onclick="tableToExcel('tamper_report', 'Tamper Report')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="tamper_report">
						<thead>
							<tr>
							<th>Month-Year</th>
									<th>Subdivision</th>
									<th>KNo</th>
									<th>Location ID</th>
									<th>Location Name</th>
									<th>Meter Serial number</th>
									<th>Tamper Event Name</th>
									<th>Tamper Event  date</th>
									<th>Tamper Event Duration</th>
							</tr>
						</thead>
						<tbody id="TamperReport">
						 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>
<script type="text/javascript">

function getReport() {
	var zone = $('#zone').val();
	var circle = $('#circle').val();
	var division = $('#division').val();
	var subdiv = $('#sdoCode').val();
	var townCode = $('#townCode').val();
	var rdngmnth=$('#month').val();
	//alert(zone+circle+division+subdiv);
	if(zone=="")
	{
	bootbox.alert("Please Enter Zone");
	return false;
	}
	if(circle=="" || circle== null)
	{
	bootbox.alert("Please Enter circle");
	return false;
	}
	if(division=="")
	{
	bootbox.alert("Please Enter division");
	return false;
	}
	if(subdiv=="")
	{
	bootbox.alert("Please Enter sub division");
	return false;
	}
	if(townCode=="")
	{
	bootbox.alert("Please select Town");
	return false;
	}
	if(rdngmnth=="")
	{
	bootbox.alert("Please Enter rdngmnth");
	return false;
	}
	
	//alert(fromDate);
	//alert(toDate);
	//$('tamper_report').show();
	$('#TamperReport').empty();
	$.ajax({
		url : './analysedDataTamperReport',
		type : 'GET',
		data : {
			circle : circle,
			division : division,
			subdiv : subdiv,
			rdngmnth : rdngmnth,
			townCode:townCode
			
		},
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr>" 
					+"<td>"+ rdngmnth+"</td>"
					+ "<td>"+ (element[8]==null?"":element[8])+ "</td>"
					+ "<td>"+ (element[5]==null?"":element[5])+ "</td>"
					+ "<td>"+(element[6]==null?"": element[6])+ "</td>"
					+ "<td>"+ (element[7]==null?"":element[7])+ "</td>"
					+ "<td>"+(element[0]==null?"": element[0])+ "</td>"
					+ "<td>"+ (element[2]==null?"":element[2])+ "</td>"
					+ "<td>"+ moment(element[3]).format('DD-MM-YYYY HH:mm:ss')+ "</td>"
					+ "<td>"+ (element[4]==null?"":(element[4]))+ "</td>";
					//+ "<td>"+ (element[4]== null? "":moment(element[4]).format('DD-HH-mm-ss'))+ "</td>";

				}
				$('#tamper_report').dataTable().fnClearTable();
				$('#TamperReport').html(html);
				loadSearchAndFilter('tamper_report');
				
			} else {
				bootbox.alert("No meters found");
			}

		}
	});

}
function exportGridData()
{
	html2canvas(document.getElementById('tamper_report'), {
        onrendered: function (canvas) {
            var data = canvas.toDataURL();
            var docDefinition = {
                content: [{
                    image: data,
                    width: 500
                }]
            };
            pdfMake.createPdf(docDefinition).download("TamperData.pdf");
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
