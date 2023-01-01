<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>
<!-- -----Export Grid Data----- -->
<!-- 	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script> -->
<script src="<c:url value='/resources/assets/scripts/pdfmake.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/assets/scripts/html2canvas.min.js'/>" type="text/javascript"></script>

<script  type="text/javascript">



	$(".page-content").ready(function(){  
		
		
	    
		App.init();
		TableManaged.init();
		FormComponents.init();
		 $('#MDMSideBarContents,#alarmID,#viewalarms').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
		 $("#check").click(
					function() {
						$(".checkboxes").prop('checked',
								$(this).prop('checked'));
					});
		 getReport();
		


		});
	</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>View Alarms
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
								<th id="ack" class="block">Acknowledge&nbsp;Remark&nbsp;:</th>
								<td><input class="form-control input-medium"
									placeholder="Enter Message" name="message" autocomplete="off"
									id="message" /></td>
								
									
							
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return acknowledge()" name="viewconsumers"
											class="btn green">
											<b>Acknowledge</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
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
								<!-- <li><a href="#" id="print" onclick="exportGridData();">Export To Pdf</a></li> -->
								<li><a href="#" id=""
								onclick="exportPDF('sample_1','Alarms')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Alarms')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
							<th>Select<input type="checkbox" 
							id="check" class="checkboxes" ></th>
							<th>Sl No</th>
							<th>Subdivision</th>
							<th>Town</th>
							<th>Location Type</th>
							<th>Location Identity</th>
							<th>Location Name</th>
							<th>Alarm Setting</th>
							<th>Alarm Type</th>
							<th>Alarm Name</th>
							<th>Alarm Priority</th>
							<th>Alarm Date</th>
							
							
							</tr>
						</thead>
						<tbody id="viewalarm">
						 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>



<script>
function getReport() {
	//alert(rdngmnth+ subdiv);
	 $('#imageee').show();
	$('#viewalarm').empty();
	$.ajax({
		url : './viewallalarms',
		type : 'GET',
		dataType : 'JSON',
		asynch : false,
		cache : false,
		success : function(response) {
			//alert(response);
			$('#imageee').hide();
			if (response.length != 0) {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					html += "<tr><td><input type='checkbox' id='check' class='checkboxes' name='al' value='"+element[0]+"' />&nbsp;</td>"
					+"<td>"+(i+1)+"</td>"
					+ "<td>"+ (element[12]== null ? "": (element[12]))+ "</td>"
					+ "<td>"+ (element[13]== null ? "": (element[13]))+ "</td>"
					+ "<td>"+ (element[2]== null ? "": (element[2]))+ "</td>"
					+ "<td>"+ (element[3]== null ? "": (element[3]))+ "</td>"
					+ "<td>"+ (element[4]== null ? "": (element[4]))+ "</td>"
					+ "<td>"+ (element[5]== null ? "": (element[5]))+ "</td>"
					+ "<td>"+ (element[6]== null ? "": (element[6]))+ "</td>"
					+ "<td>"+ (element[7]== null ? "": (element[7]))+ "</td>"
					+ "<td>"+(element[8]== null ? "": (element[8]))+ "</td>"
					+"<td>"+ moment(element[9]).format('DD-MM-YYYY HH:mm:ss') +"</td>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#viewalarm').html(html);
				loadSearchAndFilter('sample_1');
				$('input:checkbox').removeAttr('checked');
				
				
			} else {
				$('#sample_1').dataTable().fnClearTable();
				bootbox.alert("No meters found");
			}

		}
	});

}
function acknowledge(){
	var checkboxes = document.getElementsByName('al');
	var message=document.getElementById('message').value;
	//var selected = new Array();
	var cheeckedboxes = "";
	// toDelete=new Array();
	for (var i = 0; i < checkboxes.length; i++) {

		if (checkboxes[i].checked) {
			//selected.push(checkboxes[i].value);
			cheeckedboxes = cheeckedboxes + checkboxes[i].value + ",";

			//alert(checkboxes[i].value);
		}
	}
	if(cheeckedboxes.length==0){
		bootbox.alert("Please select atleast one alarm to  acknowledge")
		return false;
	}
	$.ajax({
		url : './acknowledgealarms',
		type : 'GET',
		dataType : 'text',
		data : {
			'toDelete' : cheeckedboxes,
			message : message
		},
		asynch : false,
		cache : false,
		success : function(response) {
			$("#message").val('');
			//if(respons)
			bootbox.alert("Alarm Acknowleged Successfully");
			getReport();
			 
			//$('message').clear();
			//alert("success");
		}
	});
	//getReport();
	//alert(cheeckedboxes+message);
}

function selectAll(){
	var items=document.getElementsByName('al');
	for(var i=0; i<items.length; i++){
		if(items[i].type=='checkbox')
			items[i].checked=true;
	}
	
}

function UnSelectAll(){
	var items=document.getElementsByName('acs');
	for(var i=0; i<items.length; i++){
		if(items[i].type=='checkbox')
			items[i].checked=false;
	}
}
/* function exportGridData()
{
	html2canvas(document.getElementById('sample_1'), {
        onrendered: function (canvas) {
            var data = canvas.toDataURL();
            var docDefinition = {
                content: [{
                    image: data,
                    width: 500
                }]
            };
            pdfMake.createPdf(docDefinition).download("Alarm.pdf");
         }
       });

} */

function exportPDF()
 {
	window.location.href=("./ViewAlarmsPDF");
 }
	
	
</script>


