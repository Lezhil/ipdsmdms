<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(".page-content").ready(
		function() {
			$('#MDASSideBarContents,#modemMang,#totalModemComm').addClass('start active ,selected');
			$("#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
			
		});
</script>
<script type="text/javascript">



function totalModemComm(dateComm) {
	var datecom1 = $('#datecom').val();
alert(datecom1);
	$.ajax({
		url : "./totalModemCommRec/" + datecom1,
		type : "GET",
		dataType : "json",
		async : false,
		success : function(response) {
			var x=JSON.stringify(response);
			alert(x);
			if (response.length == 0 || response.length == null) {
				bootbox.alert("Data Not Available for this Meter No. : ");
			} else {
				var html = "";
				for (var i = 0; i < response.length; i++) {
					var resp = response[i];

					html += "<tr>" + "<td>" + (i + 1) + "</td>" + "<td>"
							+ resp.imei + "</td>" + "<td>"
							+ resp.mtrNo + "</td>" + "<td>"
							+ resp.date + "</td>"
				        	+ " </tr>";
				}

				$('#sample_3').dataTable().fnClearTable();
				;
				$('#totalModemcom').html(html);
			}

		}

	});

}


function fdrOnMapVal(form)
{
	if(form.date.value==0)
	{
		bootbox.alert("Please select from Date");
		return false;
	}
	if(form.todate.value=="")
	{
		bootbox.alert("Please select To Date");
		return false;
	}
	
}
</script>



<div class="page-content">
	<div class="row">

		<div class="col-md-12">
			

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-chain"></i>Total Modem Communicated from ${date} to ${todate}
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<form action="./totalModemCommMDAS" method="post">
							<table style="width: 60%">
								<tbody>
									<tr>
										<!-- <td>Select From Date :</td>
										<td>
											<div class="input-icon">
												<i class="fa fa-calendar"></i> <input
													class="form-control date-picker input-medium"
													style="width: 150px ! important;" type="text"
													 data-date-format="yyyy-mm-dd"
													data-date-viewmode="years" name="date" id="date" />
											</div>
										</td>
										<td>Select To Date :</td>
										<td>
											<div class="input-icon">
												<i class="fa fa-calendar"></i> <input
													class="form-control date-picker input-medium"
													style="width: 150px ! important;" type="text"
													 data-date-format="yyyy-mm-dd"
													data-date-viewmode="years" name="todate" id="todate" />
											</div>
										</td> -->
										
										
										    <td><div class="col-md-3">
											<div class="input-group input-large date-picker input-daterange"  data-date-format="yyyy-mm-dd">
											<input type="date" placeholder="From Date" class="form-control" value="${date}" data-date="${date}"
										    data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="date" id="date">
											<span class="input-group-addon">to</span>
											<input type="date" placeholder="To Date" class="form-control" value="${todate}" data-date="${todate}"
											data-date-format="yyyy-mm-dd" data-date-viewmode="years" name="todate" id="todate">
											</div>
										</div></td>
										
										
										<td>
											<button id="viewFdrOnMap"
												onclick="return fdrOnMapVal(this.form);" name="viewFdrOnMap"
												class="btn yellow">
												<b>View</b>
											</button>
										</td>
									</tr>
								</tbody>
							</table>
						</form>

					</div>

					<div class="table-toolbar">

						<div class="btn-group pull-right" style="margin-top: 15px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="print">Print</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_editable_1', 'Total Communicated')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								<th>No.</th>
								<th>IMEI NO.</th>
								<th>METER NO</th>
								<th>DATE</th>
								<th>ZONE</th>
								<th>CIRCLE</th>
								<th>DIVISION</th>
								<th>SUBDIVISION</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="element" items="${totalModemInfo}">
								<tr>
									<td>${element.rowNo}</td>
									<td>${element.imei}</td>
									<td>${element.mtrNo}</td>
									<td>${element.date}</td>
									<td>${element.zone}</td>
									<td>${element.circle}</td>
									<td>${element.division}</td>
									<td>${element.subdivision}</td>
									
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>




</div> 
		
		
		
		
		

	
