
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script type="text/javascript">
	$(".page-content").ready(function() {
		
		//$("#MIS-Reports").addClass('start active , selected');
			 $('#MDMSideBarContents,#MIS-Reports,#consumerConsumption').addClass('start active ,selected');
		 $("#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
			"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	
		App.init();
		TableEditable.init();
		Tasks.initDashboardWidget();
		FormComponents.init();
		TableManaged.init();
		 
		
		 
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

<style>
.col-sm-1 {
	width: 10.3333%;
}
</style>

<div class="page-content" >
<div class="row">
				<div class="col-md-14">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-globe"></i>Consumer Consumption Reports</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="reload"></a>
								<a href="javascript:;" class="remove"></a>								
								</div>
						</div>					
											
						<div class="portlet-body">
							
							<form action="" id="cmriId" method="POST">
							<table>

							<tr>
							<td>Rdngmonth :</td>
							<td>
							<div class="input-group date date-picker" data-date-format="yyyymm" data-date-viewmode="years">
							<input type="text" class="form-control" name="reportFromDate" id="reportFromDate" value="${readingMonth}" readonly>
							<span class="input-group-btn">
							<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
							</span>
							</div>
							</td>	
							
							<td>CIRCLE :</td>
								<td><select id="circleId"
									class="form-control placeholder-no-fix" name="circleId" onchange="return getSdoName(this.value);">
										<option value="0">Select</option>
										<c:forEach var="element" items="${circle}">
											<option value="${element}">${element}</option>
										</c:forEach>
								</select></td>
								
							
								
								<%-- <td>Part :</td>
								<td ><select id="partId"
									class="form-control input-medium" name="partId"  onchange="return getpartIdname();">
									<option value="0">select</option>
									<c:forEach var="element" items="${parts}">
											<option value="${element}">${element}</option>
										</c:forEach>
								</select></td> --%>
								<td>Categorey:</td>
								<td ><select id="categoreyID"
									class="form-control input-medium" name="categoreyID"  onchange="return getcategoreyID();">
									<option value="0">select</option>
									<c:forEach var="element" items="${categories}">
											<option value="${element}">${element}</option>
										</c:forEach>
								</select></td>
							</tr>
							
							
							</table>
							<div class="modal-footer">
							<button type="submit" class="btn blue pull-left" onclick="return validation()">Generate Report</button>  
							
							</div>
							
							</form>								
						</div>
						
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
					 <c:if test = "${cmrierror eq 'CMRI List Report Data Not Found...'}"> 			        
			           <div class="alert alert-danger display-show" id="otherMsg">
							<button class="close" data-close="alert"></button>
							<span style="color:red" >${cmrierror}</span>
						</div>
			        </c:if>
					
					 
					<div class="portlet box blue">
			        
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-edit"></i>Consumer Consumption List:
						  <a href="#" id="excelExport" class="btn green" style="margin-left: 150px;"  onclick="tableToExcel('sample_editable_1', 'DATA STATUS')">Export to Excel</a>
							</div>
							
						</div>
						<div class="portlet-body" id="excelUpload">
							<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
								<thead>
									<tr>
										<th>CIRCLE</th>
										<th>PART</th>
										<th>DIVISION</th>
										<th>SUB-DIVISION</th>
										<th>ACCNO</th>
										<th>METRNO</th>
										<th>NAME</th>
										<th>CATEGORY</th>
										<th>SVOLTAGE</th>
										<th>CD</th>
										<th>SLOAD</th>
										<th>MF</th>
										<th>CONSUMPTION</th>
										<th>UTIL</th>
									</tr>
								</thead>
								<tbody>
							<c:set var="count" value="1" scope="page"> </c:set>
								<c:forEach var="element" items="${consumerList}">
								<tr>
									<td>${element[0]}</td>
									<td>${element[1]}</td>
									<td>${element[2]}</td>
									<td>${element[3]}</td>
									<td>${element[4]}</td>
									<td>${element[5]}</td>
									<td>${element[6]}</td>
									<td>${element[7]}</td>
									<td>${element[8]}</td>
									<td>${element[9]}</td>
									<td>${element[10]}</td>
									<td>${element[11]}</td>
									<td>${element[12]}</td>
									<td>${element[13]}</td>
									
								</tr> 
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:forEach>
								</tbody>
							</table>
						</div>
						
					</div>
					
					
				</div>
			</div>
</div>

<script>
function validation()
{
	
	var circle=$("#circleId").val();
	var rdngMonth=$("#reportFromDate").val();
	var part=$("#part").val();
	var category=$("#reportName").val();
	
  	
	
	/* if(circle=="0")
		{
		bootbox.alert("Please Select Circle");
		return false;
		}

		if(sdoname=="0" || sdoname==null)
		{
		bootbox.alert("Please Select SdoName");
		return false;
		}
		
	    if (mnp == "0" || mnp == null) 
		{
			bootbox.alert("Please Select MNP");
			return false;
		}
		if (reportName == "0" || reportName == null) 
		{
			bootbox.alert("Please Select Report");
			return false;
		}
		 */
		
		
		 $("#cmriId").attr("action","./getConsumerConsumption");
		
		
		
		
		

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
