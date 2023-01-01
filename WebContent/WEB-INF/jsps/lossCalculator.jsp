<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<%
	if (session.getAttribute("username") == null || session.getAttribute("username") == null) {
%>
<script>
	window.location.href = "./?sessionVal=expired";
</script>
<%
	}
%>

<style>
.spantext{
margin:5px;
font-weight:600;

}
</style>
<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						//Tasks.initDashboardWidget();
						FormComponents.init();
						loadSearchAndFilter('sample1');
						resetForm();
						$('#eaId,#lossCalculator').addClass('start active ,selected');
						$('#MDMSideBarContents,#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
						.removeClass('start active ,selected');				

					
					});
	
	
	function getATandCloss(){
		
		var inputEnergy=$("#inputEnergy").val().trim();
		var billedEnergy=$("#billedEnergy").val().trim();
		var amountBilled=$("#amountBilled").val().trim();
		var amountCollected=$("#amountCollected").val().trim();
		
		if(inputEnergy==""){
			bootbox.alert("Please Enter Input Energy");
			return false;
		}
		
		if(billedEnergy==""){
			bootbox.alert("Please Enter Billed Energy");
			return false;
		}
		
		if(amountBilled==""){
			bootbox.alert("Please Enter Amount Billed");
			return false;
		}
		
		if(amountCollected==""){
			bootbox.alert("Please Enter Amount Collected");
			return false;
		}
		
		$("#imageee").show();
		$
		.ajax({
			url : './getCalculatedValue',
			type : 'POST',
			data : {
				inputEnergy : inputEnergy,
				billedEnergy : billedEnergy,
				amountBilled : amountBilled,
				amountCollected : amountCollected
			},
			dataType : 'json',
			success : function(response) {
				 $("#imageee").hide();

					if (response != null && response.length > 0) {
					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						var j = i + 1;
						html += "<tr>" 
								+ "<td>"+ j + "</td>"
								+ "<td>"+ ((resp[0] == null) ? "": resp[0]) + " </td>"
								+ "<td>"+ ((resp[1] == null) ? "": resp[1]) + " </td>"
								+ "<td>"+ ((resp[2] == null) ? "": resp[2]) + " </td>"
							html +="</tr>";
					}
					$('#sample1').dataTable().fnClearTable();
					$("#getcalculatedValueOfATandC").html(html);
					loadSearchAndFilter('sample1');
				} else {
					bootbox.alert("No Relative Data Found.");
				}
			},
			
			
			complete : function() {
				 $("#imageee").hide();
				loadSearchAndFilter('sample1');
			}
		});
	


	}
	
	function resetForm() {
	    document.getElementById("myForm").reset();
	}
	
	

</script>

<div class="page-content">
 
	<div class="portlet box blue">

		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>AT&C Loss Calculator
			</div>
		</div>

		<div class="portlet-body">
			
		<div>
		<form action="" id="myForm"  method="post">
		
		<table class="table table-striped table-hover table-bordered" id="table">
		
		<tr><td>Report Name:</td><td style="display:flex"><input type="text"  id="anyTextId" autocomplete="off" class="form-control input-medium" placeholder="Enter Any Text"></td></tr>
		
		<tr><td>Input Energy:</td><td style="display:flex"><input type="number" min="0" onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"  id="inputEnergy" autocomplete="off" class="form-control input-medium" placeholder="Kwh"><span class="spantext" >Kwh</span></td></tr>
		
		<tr><td>Billed Energy:</td><td style="display:flex"><input type="number" min="0" onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="billedEnergy" autocomplete="off" class="form-control input-medium" placeholder="Kwh"><span class="spantext">Kwh</span></td></tr>
		
		<tr><td>Amount Billed:</td><td style="display:flex"><input type="number" min="0" onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="amountBilled" autocomplete="off" class="form-control input-medium" placeholder="Rupees"><span class="spantext">Rupees</span></td></tr>
		
		<tr><td>Amount Collected:</td><td style="display:flex"><input type="number" min="0" onkeypress="return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" id="amountCollected" autocomplete="off" class="form-control input-medium" placeholder="Rupees"><span class="spantext">Rupees</span></td></tr>
				
		</table>
		
		<div>		
		<button type="button" id="buttonId" class="btn yellow"  onclick="return getATandCloss();"><b>CALCULATE</b></button>
		<button type="button" id="resetButton" class="btn btn green" onclick="resetForm();"><b>RESET</b></button>
		<!-- //<button type="button" id="btnExport" class="btn btn blue" value="Export"> <b>EXPORT</b></button> -->
		
		</div>
		
		
		</form>
		</div>
		
		<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width: 4%; height: 4%;">
					</div>
		<br>
			<div class="row">
				<div class="col-md-12">
					<div class="btn-group pull-right">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							Tools <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right">
							<!-- <li id="print"><a href="#">Print</a></li> -->
							<li><a href="#" id=""
								onclick="exportPDF('sample1','ATandCLoss Details')">Export to PDF</a></li>
								</ul>
							
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample1">

						<thead>
							<tr>
								<th>Sl No</th>
								<th>Billing Efficiency (%)</th>
								<th>Collection Efficiency (%)</th>
								<th>AT&C Loss (%)</th>
								
							</tr>
						</thead>
						<tbody id="getcalculatedValueOfATandC">
						</tbody>
					</table>
					

				</div>
			</div>
			
		</div>
	</div>

</div>
<script type="text/javascript">
 function exportPDF()
{	 
	var inputEnergy=$("#inputEnergy").val();
	var billedEnergy=$("#billedEnergy").val();
	var amountBilled=$("#amountBilled").val();
	var amountCollected=$("#amountCollected").val();
	var anyTextId=$("#anyTextId").val();
	
	window.location.href=("./lossCalculatorPdf?inputEnergy="+inputEnergy+"&billedEnergy="+billedEnergy+"&amountBilled="+amountBilled+"&amountCollected="+amountCollected+"&anyTextId="+anyTextId);
}
</script>

