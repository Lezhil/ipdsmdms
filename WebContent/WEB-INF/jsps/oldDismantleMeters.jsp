
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
  <script src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
	<!-- <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> -->
	<script src="<c:url value='/resources/canvasjs.min.js'/>" type="text/javascript"></script>
	<script src="resources/assets/scripts/app.js"></script>
	<script src="resources/assets/scripts/ui-extended-modals.js"></script>
	

<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
 			loadSearchAndFilter('sample_1');
		 $('#MDMSideBarContents,#reportsId,#oldDismantleMetersId').addClass('start active ,selected');
			$('#MDASSideBarContents,#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected'); 
		});
		
function  showDivision(circle)
{
	$.ajax({
		   url : './divisionbycircle'+'/'+circle,
		  type : 'GET',
	   success : function(response)
	   {
		//alert(response); 
		var html = '';
		html += "<option value='All'>All</option>";
		for (var i = 0; i < response.length; i++) {
			html += "<option  value='"+response[i]+"'>" + response[i]
					+ "</option>";
		}
		$("#division").html(html);
		$('#division').select2();
	   }
	});
}

function showSubdivision(division)
{
	$.ajax({
		   url : './subdivbydivision'+'/'+division,
		  type : 'GET',
	   success : function(response)
	   {
		   //alert(response);
		   var html = '';
			html += "<option value='All'>All</option>";
			for (var i = 0; i < response.length; i++) {
				html += "<option  value='"+response[i]+"'>" + response[i]
						+ "</option>";
			}
			$("#subdivision").html(html);
			$('#subdivision').select2();
	   }
	})
	}


</script>

<div class="page-content">
     <div class="portlet box blue">
          <div class="portlet-title">
          <div class="caption">
          <i class="fa fa-bars"></i>Old Dismantle Meters
          </div>
          </div>
          
 <div class="portlet-body">
				  <table>
					 <tr>
						<th class="block">Circle&nbsp;:</th>
							<th id="circleid" >
							<select class="form-control select2me input-medium"
								id="circle" name="circle" onchange="showDivision(this.value);">
								<option value="">Select Circle</option>
								<option value="All">All</option>
								<c:forEach var="type" items="${circleName}">
								<option value="${type}">${type}</option>
								</c:forEach> 
							</select></th>
								
						<th class="block">Division&nbsp;:</th>
							<th id="divisionid">
							<select class="form-control select2me input-medium" 
							id="division" name="division" onchange="showSubdivision(this.value);">
								<option value="">Select Division</option>
							<option value="All">All</option>
							</select></th>
								
						<th class="block">Sub&nbsp;Division&nbsp;:</th>
							<th id="subdivid">
							<select class="form-control select2me input-medium" 
							id="subdivision" name="subdivision" >
							<option value="">Select SubDivision</option>
							<option value="All">All</option>
							</select></th>
							
						<td>
							<button class="btn green" type="button" name="" id="" onclick="return getOldDismantle()">View</button>&nbsp;
                            <!-- <a href="#" id="excelExport" class="btn green pull-right" type="button"  onclick="tableToExcel('dismantlemtrsid', 'olddismantlemeter')">Export to Excel</a> -->
						</td>
				   </tr> 
			    </table>&nbsp;
			  
<div class="row">
     <div class="col-md-12">
			   <table class="table table-striped table-hover table-bordered" id="sample_1">
			          <thead>
			              <tr>
	                          <th>S.No</th>	
	                          <th>Subdivision</th>
	                          <th>Account Number</th>
	                          <th>K Number</th>
	                          <th>Consumer Name</th>
	                         <!--  <th>Load Unit</th>
	                          <th>Santioned Load</th>
	                          <th>Supply Voltage</th>
	                          <th>Category</th>
	                          <th>Service Status</th> -->
	                          <th>Geo X</th>
	                          <th>Geo Y</th>
	                          <th>Premises Type</th>
	                         <!--  <th>Landmark</th> -->
	                          <th>Connection Type</th>
	                          <!-- <th>Database Meter</th> -->
	                          <!-- <th>Old Metering Status</th> -->
	                         
	                          <th>Old Meter Make</th>
	                           <th>New Meter Make</th>
	                            <th>Old Meter No</th>
	                         <!--  <th>Old Meter Status</th> -->
	                          <th>New Meter No</th>
	                         
	                          <th>Meter Replacement Date</th>
	                          	              
			              </tr>
			          </thead>
			          <tbody id="olddismantleid" >
			          </tbody>
			   </table>
			</div>
	</div>

	</div>
          
  </div>
</div>
<script>
function getOldDismantle(){
	var circle=$("#circle").val();
	var division=$("#division").val();
	var subdivision=$("#subdivision").val();
	$("#olddismantleid").html("");
$.ajax(
		{
				type : "GET",
				url : "./getOldDismantleMeters/" + circle+"/"+division+"/"+subdivision,
				dataType : "json",
				success : function(response)
				{
					if(response.length>0)
					{
						var html="";
						for(var i=0;i<response.length;i++){
							var data=response[i];
							
							html+="<tr>";          
							html+="<td>"+i+"</td>";          
							html+="<td>"+(data[0]==null?"":data[0])+"</td>";
							html+="<td>"+(data[1]==null?"":data[1])+"</td>";
							html+="<td>"+(data[2]==null?"":data[2])+"</td>";
							html+="<td>"+(data[3]==null?"":data[3])+"</td>";
							html+="<td>"+(data[4]==null?"":data[4])+"</td>";
							html+="<td>"+(data[5]==null?"":data[5])+"</td>";
							html+="<td>"+(data[6]==null?"":data[6])+"</td>";
							html+="<td>"+(data[7]==null?"":data[7])+"</td>";
							html+="<td>"+(data[8]==null?"":data[8])+"</td>";
							html+="<td>"+(data[9]==null?"":data[9])+"</td>";
							html+="<td>"+(data[10]==null?"":data[10])+"</td>";
							html+="<td>"+(data[11]==null?"":data[11])+"</td>";
							html+="<td>"+(data[12]==null?"":data[12])+"</td>";
							
							
							html+="</tr>";
							}
						$('#sample_1').dataTable().fnClearTable();
						$("#olddismantleid").html(html);
						loadSearchAndFilter('sample_1');
					}
			}
		});

}
</script>













