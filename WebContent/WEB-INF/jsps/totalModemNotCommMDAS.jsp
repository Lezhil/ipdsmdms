<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			App.init();
			TableEditable.init();
			FormComponents.init();
			//loadSearchAndFilter('sample_2');
			//loadSearchAndFilter('sample_1');
			$('#MDASSideBarContents,#modemMang,#notCommDetails').addClass('start active ,selected');
			$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
			
		});
</script>
<script>

						$('#MDASSideBarContents')
								.addClass('start active ,selected');
					
</script>
<script type="text/javascript">


function updateMasterTable(OldmtrNo, changeDate,newMtrNo,imei,fdrNmae)
{

	

	
	 if(fdrNmae.includes("."))
	  {
		 fdrNmae=fdrNmae.replace(/\./g, "@");
	  }
	  if(fdrNmae.includes("/"))
	  {
		  fdrNmae=fdrNmae.replace(/\//g, "_");
	  } 
	

	    if (confirm("ARE YOU SURE TO UPDATE THE MASTER MAIN TABLE") == true) 
	    {
	       
	  
	var fileDate=moment(changeDate).format('YYYY-MM-DD');
	
	$.ajax({
		
		url : './updateMasterTable'+ '/' + OldmtrNo+'/'+changeDate+'/'+newMtrNo+'/'+imei+'/'+fdrNmae,
		data:"",
		type : "GET",
		dataType : "text",
		async : false,
		success : function(response) {
			if (response == "SUCCESS") {
				alert('Update MASTER TABLE was success');
				
				window.location.reload(true);
			} else {
				alert('Update MASTER TABLE was failed');
			}
			
		},
		complete: function()
		{  
			loadSearchAndFilter('sample_1');
		}
  	});
}
else
	{
	alert('YOU SELECTED CANCLE BUTTON SO MASTER TABLE NOT UPDATED');
	}
	}
</script>



<div class="page-content">
	<div class="row">
		<div class="col-md-12">
		

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>NOT COMMUNICATED MODEMS INFORMATION
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
					<!-- <div class="row">
						<div class="form-group col-md-4">
							<label>Select to Filter</label>
							  <div class="input-group">
								<select class="form-control select2me input-medium" id="sdoCode" onchange="getFilteredData(this.value)"
									name="sdoCode">
									<option value="">Select Duration</option>
									<option value='1'>Last 24 Hours</option>
									<option value='2'>Last 48 Hours</option>
									<option value='7'>Last 7 Days</option>
									<option value='15'>Last 15 Days</option>
									<option value='30'>Last 30 Days</option>
									<option value='31'>More than 30 Days</option>
									<option value='ALL'>ALL</option>
									
								</select>
							</div>

						</div>
						


					</div> -->

					<div class="table-toolbar" >
						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_2', 'Discomwise not Communicated')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					
					<table class="table table-striped table-hover table-bordered"
						id="sample_2">
						<thead>
							<tr>
								<th>DISCOM</th>
								<th>24 Hrs</th>
								<th>24-48 Hrs</th>
								<th>48 Hrs-7 Days</th>
								<th>7 Days-15 Days</th>
								<th>15 Days-30 Days</th>
								<th >More Than 30 Days</th>
								

							</tr>
						</thead>
						<tbody id="discomwiseNotCom">
							<c:forEach var="element" items="${discomWiseList}">
								<tr>
									<td>${element.zone}</td>
									<td><a onclick="getFilteredData('${element.zone}','1')" style="cursor: pointer;">${element.slab1}</a></td>
									<td><a onclick="getFilteredData('${element.zone}','2')" style="cursor: pointer;">${element.slab2}</a></td>
									<td><a onclick="getFilteredData('${element.zone}','3')" style="cursor: pointer;">${element.slab3}</a></td>
									<td><a onclick="getFilteredData('${element.zone}','4')" style="cursor: pointer;">${element.slab4}</a></td>
									<td><a onclick="getFilteredData('${element.zone}','5')" style="cursor: pointer;">${element.slab5}</a></td>
									<td><a onclick="getFilteredData('${element.zone}','6')" style="cursor: pointer;">${element.slab6}</a></td>
									
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<br>
					<div id="imageee" hidden="true" style="text-align: center;">
						<h3 id="loadingText">Loading..... Please wait.</h3>
						<img alt="image" src="./resources/assets/img/loading.gif"
							style="width: 4%; height: 4%; align-content: center;">
					</div>
					<br>
					
					
					
					
					<div class="table-toolbar" id="tableToolId" hidden="true">
						<h3 style=" text-align: center; font-weight: bold; color: #7e0968;" id="dataTableLabel"></h3>
						<hr>
						<div class="btn-group pull-right" style="margin-top: 0px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					
					
					
					<table class="table table-striped table-hover table-bordered" hidden="true"
						id="sample_1">
						<thead>
							<tr>
								<th style="min-width: 80px;">No.</th>
								<th style="min-width: 200px;">IMEI NO</th>
								<th style="min-width: 200px;">NOT COMMU DAYS</th>
								<!-- <th width="300px">Address</th>  -->

							</tr>
						</thead>
						<tbody id="notComTableBody">
							<%-- <c:forEach var="element" items="${totalModemNotComm}">
								<tr>
									<td>${element.rowNo}</td>
									<td>${element.imei}</td>
									<td><a href="./notCommDaysMDAS?imei=${element.imei}">${element.count}
											days</a></td>

									<td><button id="viewFdrOnMap" class="btn blue" onclick="return updateMasterTable('${element.OldmtrNo}','${element.maxDate}','${element.newMtrNo}','${element.IMEI}','${element.fdName}');" >UPDATE MASTER</button></td>
									<td><a  href="./modemDetails?METERNO=${element[2]}&MODEM=${element[3]}" >${ element[3]}</a></td>
									<td>${element.zone}</td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>


				</div>
			</div>
		</div>
	</div>
	
</div>

<script>


		
	function getFilteredData(zone, slab) {

		if (zone != "") {
			//alert(val);
			$('#imageee').show();
			$
					.ajax({
						url : "./getNotCommunicatedDataForFilterMDAS/" + zone+"/"+slab,
						type : "GET",
						dataType : "json",
						async : false,
						success : function(response) {

							if (response.length == 0 || response.length == null) {
								bootbox.alert("Data Not Available");
								$('#sample_1').dataTable().fnClearTable();
							} else {
								var html = "";
								for (var i = 0; i < response.length; i++) {
									var resp = response[i];

									html += "<tr>"
											+ "<td>"
											+ resp.rowNo
											+ "</td>"
											+ "<td>"
											+"<a href='./modemDetailsInactiveMDAS?modem_sl_no="+resp.imei+"&mtrno="+resp.meterNo+"&substation="+resp.substation+"' style='color:blue;'>"+resp.imei+"</a>"
											/* + resp.imei */
											+ "</td>"
											+ "<td>";
									if(resp.count=='1'){
										html +="<a href='./notCommDaysMDAS?imei="+resp.imei+"'>24 Hours</a>";
									} else if(resp.count=='2'){
										html +="<a href='./notCommDaysMDAS?imei="+resp.imei+"'>48 Hours</a>";
									} else{
										html +="<a href='./notCommDaysMDAS?imei="+resp.imei+"'>"+resp.count+" Days</a>";
									}
									/* + "<a href='./notCommDaysMDAS?imei=\""+resp.imei+"\"'>"+resp.count+" days</a>" */
									html += "</td>"
											+ " </tr>";

								}

								/* $('#sample_editable_1').dataTable().fnClearTable(); */
								$('#sample_1').dataTable().fnClearTable();
								var tabLabel="Showing Data for Zone : "+zone+" and Not Communicated in : ";
								//alert(slab);
								if(slab=='1'){
									tabLabel+="24 Hours";
								} else if(slab=='2'){
									tabLabel+="24 Hours - 48 Hours";
								} else if(slab=='3'){
									tabLabel+="48 Hours - 7 Days";
								} else if(slab=='4'){
									tabLabel+="7 Days - 15 days";
								} else if(slab=='5'){
									tabLabel+="15 Days - 30 days";
								} else {
									tabLabel+="More than 30 days";
								}
								
								
								$('#dataTableLabel').html(tabLabel);
								
								$('#tableToolId').show();
								$('#sample_1').show();
								$('#notComTableBody').html(html);
								loadSearchAndFilter('sample_1');

							}

						}, 
						complete:function (){
							$('#imageee').hide();
						}

					});

		}

	}
</script>