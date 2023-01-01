<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(".page-content").ready(function(){
		$("#datedata").val(getPresentMonthDate('${selectedMonth}'));
	
		
	/* 	App.init();
		TableManaged.init();
		FormComponents.init();
		$('#MDASSideBarContents,#meterOper,#mrd,#mftrd').addClass('start active ,selected');
		$('#MDASSideBarContents,#meterOper,#firmwareupgrade,#frmupgrade').addClass('start active ,selected');
		$("#MDMSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#MIS-Reports").removeClass('start active ,selected');
		 */
		
		 App.init();
			TableEditable.init();
			FormComponents.init();
			$('#MDASSideBarContents,#meterOper,#firmwareupgrade,#frmupgrade').addClass('start active ,selected');
			$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');

		
		
	});

$(document).ready(function() {
	
});

function showModal(){
	$('#modalmtrgroup').html($("#mtrgroup").val());
	$('#modalfrmencdata').html($("#frmimage").val());
	$('#modalfrmhashdata').html($("#frmhash").val());
	$('#modalfrmindent').html($("#frmindent").val());
	$('#basic').modal('show');
}

function firmwareupgrade(){
	//alert("firmwareupgrade");
	var mtrgroup=$("#mtrgroup").val();
	var frmimage=$("#frmimage").val();
	var frmhash=$("#frmhash").val();
	var frmindent=$("#frmindent").val();
	$.ajax({
			type : 'GET',
			url : "./createMeterJob",
			data : {mtrgroup:mtrgroup,frmimage:frmimage,frmhash:frmhash,frmindent:frmindent},
			async : false,
			cache : false,
			success : function(response) {
				$('#basic').modal('hide');
				alert(response);
			}
		});

}
</script>
<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box light-grey">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-globe"></i>FirmWare Upgrade
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> 
						<a href="#portlet-config" data-toggle="modal" class="config"></a> 
						<a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
									<td><select class="form-control select2me input-medium" name="mtrgroup" id="mtrgroup">
											<option value="">Select Meter Group</option>
											<c:forEach var="elements" items="${meters}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr style="height: 100px;">
									<td>FIRMWARE IMAGE (<b>HEX_ENCODED_FIRMWARE_DATA</b>)<textarea class="form-control placeholder-no-fix" id="frmimage" style="width: 1056px; height: 44px;"></textarea></td>
								</tr>
								<tr style="height: 100px;">
									<td>FIRMWARE HASH (<b>HEX_ENCODED_MD5_HASH_OF_FIRMARE_DATA</b>)<textarea class="form-control placeholder-no-fix" id="frmhash" style="width: 1056px; height: 44px;"></textarea></td>
								</tr>
								<tr style="height: 100px;">
									<td>FIRMWARE INDENT (<b>FIRMWAREV1</b>)<textarea class="form-control placeholder-no-fix" id="frmindent" style="width: 1056px; height: 44px;"></textarea></td>
								</tr>
								<tr>
								<td>
									<p>&nbsp</p>
									<button type="button" style="margin-top: -3px; margin-left: 10px;" class="btn blue" onclick="showModal();">SUBMIT</button>
								</td>
							</tr>
							</tbody>
						</table>
						<p>&nbsp;</p>
					</div>
					
					<!-- --------------------------------------------------------------------------------- -->
					<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
						<div class="modal-dialog" style="width: 1000px;">
							<div class="modal-content">
								<div class="modal-header" style="background-color:#4b8cf8;">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
									<h4 class="modal-title">
										<font style="font-weight: bold; color: white;" id="tabTitle">Please confirm firmware upgrade details</font></a>
									</h4>
								</div>
								<div class="modal-body">
									<table class="table table-striped table-hover table-bordered" id="sample_1" style="table-layout:fixed">
										<tr>
											<td><b>Meter_Group</b></td>
											<td id="modalmtrgroup"></td>
										</tr>
										<tr>
											<td><b>HEX_ENCODED_FIRMWARE_DATA</b></td>
											<td id="modalfrmencdata"></td>
										</tr>
										<tr>
											<td><b>HEX_ENCODED_MD5_HASH_OF_FIRMARE_DATA</b></td>
											<td id="modalfrmhashdata"></td>
										</tr>
										<tr>
											<td><b>FIRMWAREV1</b></td>
											<td id="modalfrmindent"></td>
										</tr>
									</table>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn dark btn-outline" onclick="firmwareupgrade();">Confirm</button>
									<button type="button" class="btn dark btn-outline" data-dismiss="modal">Cancel</button>
								</div>
							</div>
						</div>
					</div>
					<!-- --------------------------------------------------------------------------------- -->
					
				</div>
			</div>
		</div>
	</div>
</div>

