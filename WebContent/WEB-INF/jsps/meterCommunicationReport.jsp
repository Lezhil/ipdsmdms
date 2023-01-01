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
			loadSearchAndFilter('sample_1');
			$('#MDASSideBarContents,#mtrcommstatus').addClass('start active ,selected');
			$('#MDMSideBarContents,#ADMINSideBarContents,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment')
					.removeClass('start active ,selected'); 
		});
		
		
		
function showCircle(zone)
{
	var zone="All";
	$.ajax({
		url : './showCircleMDM' + '/' + zone ,
	    	type:'GET',
	    	
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<option value=''></option>";
				for( var i=0;i<response.length;i++)
				{
					html+="<option  value='"+response[i]+"'>"+response[i]+"</option>";
				}
				
				$("#circle").html(html);
				$('#circle').select2();
	    	}
		});
}

function showDivision(circle)
{
	var zone="All";
	$.ajax({
		url : './showDivisionMDM' + '/' + zone + '/' + circle,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response)
	    	{
  			var html='';
	    		html+="<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option>";
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
	var circle=$('#circle').val();
	var zone="All";
	$.ajax({
		url : './showSubdivByDivMDM' + '/' + zone + '/' + circle + '/'
		+ division,
	    	type:'GET',
	    	dataType:'json',
	    	asynch:false,
	    	cache:false,
	    	success:function(response1)
	    	{
  			var html='';
	    		html+="<select id='sdoCode' name='sdoCode' class='form-control input-medium' type='text'><option value=''>Sub-Division</option>";
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
}

</script>


<div class="page-content">
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Meter Non-Communication Report
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> 
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				<table ><tbody>
										<tr>
										<th class="block">Zone&nbsp;:</th>
								<th id=""><select class="form-control select2me input-medium"
									id="zone" name="zone" onchange="showCircle(this.value);">
									<option value=""></option>
										<option value="JVVNL">JVVNL</option>
								</select></th>
								
								<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle" onchange="showDivision(this.value);">
								</select></th>
								
								<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
								</select></th>
								</tr>
								
								<tr>
								<th class="block">Sub&nbsp;Division&nbsp;:</th>
								<th id="subdivTd"><select
									class="form-control select2me input-medium" id="sdoCode"
									name="sdoCode">
									</select></th>
									<th class="block">From&nbsp;Date&nbsp;:</th>
										 <th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="fromDate" id="fromDate"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th class="block">To&nbsp;Date&nbsp;:</th><th>
										<div class="input-group input-medium date date-picker"  data-date-format="yyyy-mm-dd" data-date-viewmode="years" id="five">
																<input type="text" autocomplete="off" class="form-control" name="toDate" id="toDate"   >
																<span class="input-group-btn">
																<button class="btn default" type="button" id="six" > <i class="fa fa-calendar"></i></button>
																</span>
																</div></th>
																<th colspan="4" >
																<div >
																
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</div>
																</th>
								</tr>
										
								</tbody>
								</table>
					

				<%-- 		<table>
							<tbody>
								<tr>
										<th class="block">Zone&nbsp;:</th>
								<th id=""><select class="form-control select2me input-medium"
									id="zone" name="circle" onchange="showCircle(this.value);">
									<option value=""></option>
										<option value="JVVNL">JVVNL</option>
								</select></th>
								
										<th class="block">Circle&nbsp;:</th>
								<th id="circleTd"><select class="form-control select2me input-medium"
									id="circle" name="circle" onchange="showDivision(this.value);">
									</select></th>
									
									<th class="block">Division&nbsp;:</th>
								<th id="divisionTd"><select
									class="form-control select2me input-medium" id="division"
									name="division">
									</select></th>
									</tr>
									
								<tr>
									<td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value="ALL">ALL</option>
											<c:forEach items="${subdivlist}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>
									
							
									<td>
										<div class="col-md-3">
											<div class="input-group input-large date-picker input-daterange"  data-date-format="yyyy-mm-dd">
											<input type="text" placeholder="From Date" class="form-control" value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />" data-date="${currentDate}"
										    data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="fromDate" id="fromDate">
											<span class="input-group-addon">to</span>
											<input type="text" placeholder="To Date" class="form-control" value="<fmt:formatDate value="${currentDate}" pattern="yyyy-mm-dd" />" data-date="${currentDate}"
											data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="toDate" id="toDate">
											</div>
										</div>
									</td>
	
									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getReport()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
									</tr>
								
							</tbody>
						</table>
						<p>&nbsp;</p> --%>
						
					
				
				
					
					<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 15px;">
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
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Meter No.</th>
								<th>Consumer Name</th>
								<th>Account No</th>
								<th>Kno</th>
								<th>SubDivision</th>
								<th>Division</th>
								<th>Circle</th>
								<th>Last Sync Time</th>
							</tr>
						</thead>
						<tbody id="updateMaster">
						 
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>



<script>


function getReport() {
	var zone   = $('#zone').val();
	var circle = $('#circle').val();
	var div    = $('#division').val();
	var subdiv = $('#sdoCode').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
	//alert(fromDate);
	//alert(toDate);
	
	$('#updateMaster').empty();
	$.ajax({
		url : './getMeterCommunicationReport',
		type : 'GET',
		data : {
			zone  : zone,
			circle: circle,
			div    : div,
			subdiv : subdiv,
			fromDate : fromDate,
			toDate : toDate
		},
		
		success : function(response) {
			if (response.length != 0) {
				var html = "";
				
				for (var i = 0; i < response.length; i++) {
					var element = response[i];
					
					
					html += "<tr><td>"+(i+1)+"</td>" 
					+ "<td>"
					+ "<a style='color:blue;' href='./viewFeederMeterInfoMDAS?mtrno="+element[0]+"');'>"+element[0]+"</a>"
					+ "</td>"
					+ "<td>"+ element[1]+ "</td>"
					+ "<td>"+ element[2]+ "</td>"
					+ "<td>"+ element[3]+ "</td>"
					+ "<td>"+ element[4]+ "</td>"
					+ "<td>"+ element[5]+ "</td>"
					+ "<td>"+ element[6]+ "</td>";
					
					if(element[7]!=null){
						//alert(element[7]);
						html +="<td>"
							+ moment(element[7]).format("YYYY-MM-DD HH:mm:ss")
							+ "</td>";
					} else{
						html +="<td>"
							+ "</td>";
					}
					
					
					
					html + " </tr>";
				}
				$('#sample_1').dataTable().fnClearTable();
				$('#updateMaster').html(html);
				loadSearchAndFilter('sample_1');
				
			} else {
				bootbox
				.alert("Meter not exist in the master main table ");
			}

		}
	});

}

</script>