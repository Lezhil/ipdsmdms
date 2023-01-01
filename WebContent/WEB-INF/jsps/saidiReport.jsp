<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="./resources/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="./resources/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="./resources/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="./resources/assets/scripts/ui-modals.min.js" type="text/javascript"></script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>System Average Interruption Duration Report Index Report
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
							</tr>
							
							</table>
							<div class="modal-footer">
							<button type="submit" class="btn blue pull-left" onclick="return validation()">View</button>  
							
							</div>
							
							</form>								
						</div>
						
						<c:if test="${DataNotAvailable eq 'Data Not Available'}">
			<div class="alert alert-danger display-show" id="otherMsg">
				<button class="close" data-close="alert"></button>
				<span style="color: red">${DataNotAvailable}</span>
			</div>
		</c:if>
			<c:if test="${ DataAvailable eq 'DataAvailable'}">
				<div class="portlet-body">
					<p>&nbsp</p>
					<table class="table table-striped table-hover table-bordered " id="sample_3">
						<!-- <thead>
							<tr>
								<th>MODEM SERIAL NUMBER</th>
								<th>Modem IMEI Number</th>
								<th>SIM IMSI</th>
								<th>SIM CCID</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						</tbody> -->
						<tr><td width="80">Total Number of Consumers</td><td width="80">${customers}</td></tr>
						<tr><td width="80">Total Power Interruption Duration</td><td width="80">${duration}</td></tr>
						<tr><td width="80"><b>SAIDI</b></td><td width="80"><b>${saidi}</b></td></tr>
						
					</table>
				</div>
				</c:if>

				<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
					<div class="modal-dialog" style="width: 1000px;">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" ><font style="font-weight: bold; color: white;" id="tabTitle"></font></a></h4>
							</div>
							<div class="modal-body">
								<table class="table table-striped table-hover table-bordered" id="sample_1">
									<thead>
										<tr>
											<th> Sl No.</th>
											<th>Modem Sl No.</th>
											<th>Modem IMEI</th>
											<th>SIM IMSI</th>
											<th>SIM CCID</th>
											<th>ATTACHED METER</th>
											<th>PHONE NO</th>
										</tr>
									</thead>
									<tbody id="modemMasterTbody">
										 
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
			</div>
		</div>
	</div>

</div>


<script>
	$(document).ready(function(){
		
		App.init();
		TableEditable.init();
		FormComponents.init();
		
		
	        
		$('#MDASSideBarContents,#MIS-Reports,#saidiReport').addClass('start active ,selected');
		 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer, #exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
	   
	
	});
	
	function getModemMaster(val) {
		
		clearTabledataContent('sample_1');
		 if(val==0){
			$('#tabTitle').html("ALL MODEMS");
		} else if(val==1){
			$('#tabTitle').html("Total Installed Modems");
		} else if(val==2){
			$('#tabTitle').html("Total Modem in Stock");
		} else if(val==3){
			$('#tabTitle').html("Total Modem Not Working");
		} 
		
		$.ajax({
	    	url : './getModemMasterDataMDAS/'+val,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
    		var html1 ="";
    		for (var i = 0; i < response.length; i++) 
        	{
    			var resp=response[i];
    			if(resp[6]==null)
				{
					var	phone="";
				}
    			if(resp[5]==null)
				{
					resp[5	]="";
				}
    			 html1+="<tr>"
					 	+" <td>"+(i+1)+"</td>"
						+" <td>"+resp[1]+"</td>"
						+" <td>"+resp[2]+"</td>"
						+" <td>"+resp[3]+"</td>"
						+" <td>"+resp[4]+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+phone+"</td>"
						+" </tr>";
    		}
    		//$('#sample_4').dataTable().fnClearTable();
    		
    		$('#sample_1 tbody').empty();
    		 $("#modemMasterTbody").append(html1);
	    	}, 
    		complete: function()
    		{  
    			loadSearchAndFilter('sample_1');
    		}  
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


function validation()
{
	
 $("#cmriId").attr("action","./saidiReportMonthwise");
	

}
	
</script>

<style>
a {
    text-shadow: none;
    color: #337ab7;
}

.modal-body {
    position: relative;
	padding-top: 20px;
	padding-right: 20px;
	padding-bottom: 0px;
	padding-left: 20px;
}

</style>