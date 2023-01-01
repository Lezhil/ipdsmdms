<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="./resources/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="./resources/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="./resources/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="./resources/assets/scripts/ui-modals.min.js" type="text/javascript"></script>

<script>
$('#MDMSideBarContents,#metermang,#modemInventory')
.addClass('start active ,selected');
</script>

<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Modem Inventory
					</div>
				</div>
				<div class="portlet-body">
					<p>&nbsp</p>
					<table class="table table-striped table-hover table-bordered " id="sample_3">
						<tr><td width="80"><b>Total Number of Modems</b></td><td width="80"><b><a data-toggle='modal' href='#basic' onclick="getModemMaster(0)">${total}</a></b></td></tr>
						<tr><td width="80">Total Modems Installed</td><td width="80"><a data-toggle='modal' href='#basic' onclick="getModemMaster(1)">${installed}</a></td></tr>
						<tr><td width="80">Total Modems in Stock</td><td width="80"><a data-toggle='modal' href='#basic' onclick="getModemMaster(2)">${instock}</a></td></tr>
						<tr><td width="80">Total Modems not Working</td><td width="80"><a data-toggle='modal' href='#basic' onclick="getModemMaster(3)">${notWorking}</a></td></tr>
					</table>
				</div>

				<div class="portlet-body">
			<div class="row" style="margin-left: -1px;">
			
			<div id="imageee" hidden="true" style="text-align: center;">
                         <h3 id="loadingText">Loading..... Please wait. 
						 </h3> 
						 <img alt="image" src="./resources/bsmart.lib.js/loading.gif" style="width:3%;height: 3%;">
						</div>
				</div>
                <div id="modal_form_vertical"  class="modal fade">
				<div  tabindex="-1" role="basic" aria-hidden="true">
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
					</div>
				</div>
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
		loadSearchAndFilter1('sample_1');
	    $("#basic").on('shown.bs.modal', function(){
	    $(this).find('#new_imei_no').focus();
	    });
	
	});

	function getModemMaster(val) {
		 $('#imageee').show();
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
	    	url : './getModeminventoryDataMDAS/'+val,
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
    			var	m="";
    			if(resp[2]=='' || resp[2]=='null' || resp[2]==null)
				{
    					m="";
				}
    			else
    			{
    				 m=resp[2];
    			}
    			
    			if(resp[6]=='null' || resp[6]=='' || resp[6]==null)
				{
					var	phone="";
				}
    			else
    			{
    				var phone=resp[6];
    			}
    			
    			if(resp[5]==null || resp[5]=='' || resp[5]=='null')
				{
					resp[5]="";
				}

    			html1+="<tr>"
					 	+" <td>"+(i+1)+"</td>"
						+" <td>"+(resp[1]==null?"":resp[1])+"</td>"
						+" <td>"+m+"</td>"
						+" <td>"+(resp[3]==null?"":resp[3])+"</td>"
						+" <td>"+(resp[4]==null?"":resp[4])+"</td>"
						+" <td>"+resp[5]+"</td>"
						+" <td>"+phone+"</td>"
						+" </tr>";
    		}
    		$('#sample_1 tbody').empty();
    		 $("#modemMasterTbody").append(html1);
	    	}, 
    		complete: function()
    		{  
    			$('#imageee').hide();
    			loadSearchAndFilter('sample_1');
    			 $('#modal_form_vertical').modal('show');
    		}  
		  }); 
		
	}
	function clearTabledataContent(tableid)
	{
		var oSettings = $('#'+tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) 
		{
			$('#'+tableid).dataTable().fnDeleteRow(0, null, true);
		} 
		
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