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
			$('#MDMSideBarContents,#metermang,#meterChange').addClass('start active ,selected');
			$("#MDASSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn").removeClass('start active ,selected');
			
		});
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
						<i class="fa fa-edit"></i>METER CHANGED INFORMATION
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a> <a
							href="javascript:;" class="remove"></a>
					</div>
				</div>

				<div class="portlet-body">
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
								<th>No.</th> 
								<th>CONSUMER NAME</th>
								<th>OLD METER NO</th> 
								<th>NEW METER NUMBER </th> 
								<th>CIRCLE</th> 
								<th>DIVISION</th> 
								<th>SUB-DIVISION</th> 
								<th>Account No.</th> 
								<th>IMEI</th>
								<th>METER CHANGED DATE</th>
								<th>STATUS</th> 
								<th>METER MAKE</th>
								<th>DLMS/NON-DLMS</th> 
								<th>UPDATE THE MASTER MAIN</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="element" items="${mtrChangeInfo}">
								<tr>
								  <td>${element.rowNo}</td>
									 <td>${element.fdName}</td>
									<td>${element.OldmtrNo}</td>
									<td>${element.newMtrNo}</td> 
									<td>${element.circle}</td>
									<td>${element.division}</td>
									<td>${element.subDivision}</td>
									<td>${element.subStation}</td>
									<td>${element.IMEI}</td> 
									<td>${element.maxDate}</td> 
									<td>${element.status}</td>
									<td>${element.mtrMake}</td> 
									<td>${element.dlms}</td>
									<td><button id="viewFdrOnMap" class="btn blue" onclick="return updateMasterTable('${element.OldmtrNo}','${element.maxDate}','${element.newMtrNo}','${element.IMEI}','${element.fdName}');" >UPDATE MASTER</button></td>
								
									
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>
	
</div>