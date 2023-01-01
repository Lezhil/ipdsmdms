<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<script src="<c:url value='/resources/bsmart.lib.js/highcharts.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/highcharts-more.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/bsmart.lib.js/exporting.js'/>" type="text/javascript"></script>

 <script>

$(".page-content").ready(
		function() {
			
			TableEditable.init();
			FormComponents.init();
			loadSearchAndFilter('sample_1');
			getunmappedmetres();
			$('#MDASSideBarContents,#unmtrs,#mdmDashId').addClass('start active ,selected');
			$('#dash-board2,#dash,#user-location,#admin-employee,#MIS-Reports,#meterData-Acquition,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn,#dash-board,#modemManagment').removeClass('start active ,selected');
			App.init();
		});
</script>



<div class="page-content">
 <c:if test = "${not empty msg}">
			         <div class="alert alert-danger display-show">
					 <button class="close" data-close="alert"></button>
					 <span style="color:red" >${msg}</span>
			       </div>
	                </c:if>
	<div class="row">
		<div class="col-md-12">
			
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>Unmapped Meter's Details
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
				
				<table>
											<tr>
												<td>Total UnMapped Meters : <b> ${totalunm} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
												
											</tr>
										</table><br><br>
				
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
								<!-- <li><a href="#" id="print">Print</a></li> --> 
								
								<li><a href="#" id=""
							onclick="exportPDF('sample_1','Meter Details')">Export to PDF</a></li>
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_1', 'Unmapped Meter Details')">Export
										to Excel</a></li>
										
							</ul>
						</div>
					</div>
					
					<table class="table table-striped table-hover table-bordered" id="sample_1">
						<thead>
							<tr>
							<%-- <c:if test="${officeType eq 'subdivision'}">
								<th>Edit</th>
								</c:if> --%>
								<th>Meter SL No</th>
								<th>Manufacturer</th>
								<th>First Communication</th>
								<th>Last Communication</th>
								
								
								
								
								
								
							</tr>
						</thead>
					 <tbody id="unmappedmeters">
				<%-- 	 <c:set var="count" value="1" scope="page"></c:set> 
						<c:forEach var="element" items="${meterdetails}">
						<tr>
						
						<td>${element[0]}</td>
						<c:choose>
											<c:when test="${element[1]=='null'}">
												<td></td>
											</c:when>
											<c:otherwise>
											<td>${element[1]}</td>
											</c:otherwise>
										</c:choose>
						<td>${element[2]}</td>
						<td>${element[3]}</td>
						
						
						</tr>
					 <c:set var="count" value="{count+1}" scope="page"></c:set> 
						</c:forEach> --%>
						
						
						
						
						</tbody>
						
					</table>
				
					
					</div>
					
					</div>
					</div>
					</div>
					</div>
					
					

<script>
 function handlechange1(param)
{
	
	$("#abc").hide();
	$("#abc123").show();
	$("#abc1234").hide();
	$("#hide1").show();
	$("#hide2").show();
	$("#hide3").show();
    $("#hide5").hide(); 
	 
}
 function handlechange2(param)
{
	
	$("#abc123").hide();
	$("#abc").show();
	$("#abc1234").hide();
	$("#hide5").hide();
}  

 function handlechange3(param)
 {
	 $("#abc123").show(); 
	 $("#hide1").show();
	 $("#hide2").show();
	 $("#hide3").show();
	 $("#abc1234").hide();
	 $("#hide5").hide();
	 $("#hide4").show();
 }
 
 function handlechange4(param)
 {
	 $("#hide1").hide();
	 $("#hide2").hide();
	 $("#hide3").hide();
	 $("#hide4").hide();
	 $("#abc1234").show();
	 $("#hide5").show();
 }

 function hide(param)
 {
	 $("#abc123").hide();
	 $("#abc").show();
	 $("#abc1234").hide();
 }

		 
</script>

					       
  <script>
  function exportPDF()
  {
  	window.location.href=("./unmappedmeterspdf");
  	
  }
</script>      
<script >
function getunmappedmetres(){
	$('#imageee').show();
	$.ajax({
		url : './getunmappedMetersdetails',
		type : 'GET',
		dataType : 'json',
		asynch : false,
		cache : false,
		success : function(data) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var element = data[i];
			//	alert(element[1]);
				html += "<tr>" 
					
				+"<td>"+ (element[0]==null?"":(element[0]))+"</td>"
				+ "<td>"+ (element[1]==null?"":(element[1]))+ "</td>"
				+ "<td>"+ (element[2]==null?"":moment(element[2]).format('DD-MM-YYYY HH:mm:ss'))+ "</td>"
				+ "<td>"+ (element[3]==null?"":moment(element[3]).format('DD-MM-YYYY HH:mm:ss'))+ "</td>";
				
/* 					+ "<td>"+(element[13]!=null?"":moment(element[13]).format('DD-MM-YYYY hh:mm:ss'))+ "</td>"
*/					
			}
			$('#sample_1').dataTable().fnClearTable();
			$('#unmappedmeters').html(html);
		},
		complete : function() {
			loadSearchAndFilter('sample_1');
			$('#imageee').hide();
		}
	});
}

</script>	
					
					
          
        							
					
					