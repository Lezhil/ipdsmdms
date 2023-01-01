<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	$(".page-content")
			.ready(
					function() {
						App.init();
						TableEditable.init();
						FormComponents.init();
						loadSearchAndFilter('sample_1');
						$('#calenderjsp').addClass('start active ,selected');
						$(
								"#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');

					});
</script>
<script type="text/javascript">
	var mtrNum;
	function mtrDetails(mtrNo) {
		mtrNum = mtrNo;
		window.location.href = "./mtrNoDetailsMDAS?mtrno=" + mtrNo;
	}

	function eventDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getEventData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" + " <td>" + resp[0] + "</td>" + " <td>"
							+ resp[1] + "</td>" + " <td>" + resp[2] + "</td>"
							+ " <td>" + resp[3] + "</td>" + " <td>" + resp[4]
							+ "</td>" + " <td>" + resp[5] + "</td>" + " <td>"
							+ resp[6] + "</td>" + " <td>" + resp[7] + "</td>"
							+ " <td>" + resp[8] + "</td>" + " <td>" + resp[9]
							+ "</td>" + " <td>" + resp[10] + "</td>" + " <td>"
							+ resp[11] + "</td>" + " <td>" + resp[12] + "</td>"
							+ " </tr>";
				}
				$('#sample_2').dataTable().fnClearTable();
				$('#eventTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_2');
			}
		});
	}

	function instansDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getInstansData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" 
						+ " <td>" + resp[0] + "</td>" 
						+ " <td>" + resp[1] + "</td>" 
						+ " <td>" + resp[2] + "</td>"
						+ " <td>" + resp[3] + "</td>" 
						+ " </tr>";
				}
				$('#sample_3').dataTable().fnClearTable();
				$('#instantsTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_3');
			}
		});
	}

	function loadSurveyDetails() {
		var mtrNo = mtrNum;
		$.ajax({
			url : './getLoadSurveyData/' + mtrNo,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					var resp = data[i];
					html += "<tr>" 
						+ " <td>" + resp[0] + "</td>" 
					    + " <td>" + resp[1] + "</td>" 
					    + " <td>" + resp[2] + "</td>"
						+ " <td>" + resp[3] + "</td>" 
						+ " <td>" + resp[4] + "</td>" 
						+ " <td>" + resp[5] + "</td>" 
						+ " <td>" + resp[6] + "</td>" 
						+ " <td>" + resp[7] + "</td>"
						+ " <td>" + resp[8] + "</td>"
						+ " <td>" + resp[9] + "</td>" 
						+ " <td>" + resp[10] + "</td>" 
						+ " <td>" + resp[11] + "</td>" 
						+ " <td>" + resp[12] + "</td>"
					+ " </tr>";
				}
				$('#sample_4').dataTable().fnClearTable();
				$('#loadSurveyTR').html(html);
			},
			complete : function() {
				loadSearchAndFilter('sample_4');
			}
		});
	}
</script>



<div class="page-content">

	<div class="row">
		<div class="col-md-12">
			<div class="display-show">
				<span style="color: red">${results}</span>
			</div>

			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-edit"></i>${type}
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
									onclick="tableToExcel('sample_editable_1', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>
					
					
					
					
					<c:choose>
						<c:when test="${fn:contains(type, 'Communicating')}">
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>Meter No.</th>
										<th>Consumer Name</th>
										<th>Account No</th>
										<th>SubStation</th>
										<th>SubDivision</th>
										<th>Division</th>
										<th>Circle</th>
										<th>Last Sync Time</th>
									</tr>
								</thead>
								<tbody id="updateMaster">
									<c:forEach var="element" items="${mDetailList}">
										<tr>
											<td><a
												href='./viewFeederMeterInfoMDAS?mtrno=${element[1]}'
												style='color: blue;'
												onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td>
											<td>${element[3]}</td>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td>${element[4]}</td>
											<td>${element[5]}</td>
											<td>${element[7]}</td>
											<td><fmt:formatDate value="${element[8]}"
													pattern="dd-MM-yyyy HH:mm:ss" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</c:when>


						<c:when test="${fn:contains(type, 'Total Meters')}">
							<table class="table table-striped table-hover table-bordered"
								id="sample_1">
								<thead>
									<tr>
										<th>Meter No.</th>
										<th>Consumer Name</th>
										<th>Account No</th>
										<th>SubStation</th>
										<th>SubDivision</th>
										<th>Division</th>
										<th>Circle</th>
										<th>Last Sync Time</th>
									</tr>
								</thead>
								<tbody id="updateMaster">
									<c:forEach var="element" items="${mDetailList}">
										<tr>
											<td><a
												href='./viewFeederMeterInfoMDAS?mtrno=${element[1]}'
												style='color: blue;'
												onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td>
											<td>${element[3]}</td>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td>${element[4]}</td>
											<td>${element[5]}</td>
											<td>${element[7]}</td>
											<td><fmt:formatDate value="${element[8]}"
													pattern="dd-MM-yyyy HH:mm:ss" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</c:when>

						<c:when test="${fn:contains(type, 'Successfully')}">
							<table class="table table-striped table-hover table-bordered"
								id="sample_editable_1">
								<thead>
									<tr>
										<th>Meter No.</th>
										<th>Feeder Code</th>
										<th>SubStation</th>
										<th>Uploaded Time</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach var="element" items="${mDetailList}">
										<tr>

											<td><a style='color: blue;'
												onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td>
											<%-- href='./mtrNoDetails?mtrno=${element[1]}' --%>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td><fmt:formatDate value="${element[4]}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</c:when>
						<c:otherwise>

							<table class="table table-striped table-hover table-bordered"
								id="sample_editable_1">
								<thead>
									<tr>
										<th>Meter No.</th>
										<th>Feeder Code</th>
										<th>SubStation</th>
										<th>Failure Reason</th>
										<th><button id="btnXmlUplad"
												style="font-size: small; background: olive; width: 115px;"
												type="button" class="btn btn-primary"
												onclick="forceUploadXMLAll(); return false;">
												&nbsp;<i class="fa fa-upload"></i>&nbsp;Upload
											</button></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="element" items="${mDetailList}">
										<tr>

											<td><a style='color: blue;'
												onclick="return mtrDetails('${element[1]}');">${element[1]}</a></td>
											<%-- href='./mtrNoDetails?mtrno=${element[1]}' --%>
											<td>${element[2]}</td>
											<td>${element[3]}</td>
											<td>${element[4]}</td>
											<td><button type='button' class='btn btn-primary'
													onclick="forceUploadXML('${element[1]}'); return false;">Force
													Upload</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
					<form id="DeleteForm" method="POST">
						<input type="hidden" name="deviceid" value="" id="deleteID" /> <input
							type="hidden" name="editVal" value="" id="editVal" />
					</form>

				</div>
			</div>
		</div>
	</div>

</div>



<script>
	function forceUploadXML(meterNumber) {
		//d.setDate('${selectedDate}');
		var fileDate = moment('${selectedDate}').format('YYYY-MM-DD');
		var uploadUrl = "./createSingleXmlAndUpload/" + meterNumber + "/"
				+ fileDate; //UPLOAD SINGLE METER XML 
		$.ajax({
			url : uploadUrl,
			data : "",
			type : "GET",
			dataType : "text",
			async : false,
			success : function(response) {
				bootbox.alert(response);
			}
		});
	}

	function forceUploadXMLAll() {
		var d = new Date();
		//d.setDate('${selectedDate}');
		var fileDate = moment('${selectedDate}').format('YYYY-MM-DD');
		var uploadUrl = "./createAllXmlAndUpload/" + fileDate;//UPLOAD ALL XML FILES

		$
				.ajax({
					url : uploadUrl,
					data : "",
					type : "GET",
					dataType : "text",
					async : false,
					beforeSend : function() {
						bootbox
								.alert("XML upload request has been sent.\nPlease refresh after a few seconds.");
					}

				});
	}
</script>