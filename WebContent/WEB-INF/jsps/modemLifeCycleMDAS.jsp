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
						loadSearchAndFilter('sample_3');
						$('#MDASSideBarContents,#modemMang,#modemLifeCycle')
						.addClass('start active ,selected');
				$(
						"#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
						.removeClass('start active ,selected');

			});
</script>
<script>

	function getLifeCycleData() {

		var imeiVal = $('#imei').val();
		/* alert('IMEI====='+imeiVal); */
		$
				.ajax({
					url : "./getLifeCycleDataByImeiMDAS/" + imeiVal,
					type : "GET",
					dataType : "json",
					async : false,
					success : function(response) {

						if (response.length == 0 || response.length == null) {
							bootbox
									.alert("Data Not Available for this IMEI No. : "
											+ imeiVal);
						} else {
							var html = "";
							
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								var edate="";
									if(resp.edate!=null){
										edate=moment(resp.edate).format('YYYY-MM-DD');
									}

									html += "<tr>" + "<td>"
											+ (i+1)
											+ "</td>"
											+ "<td>"
											+ resp.imei
											+ "</td>"
											+ "<td>"
											+ resp.events
											+ "</td>"
											+ "<td>"
											+ (resp.location==null?"":resp.location)
											+ "</td>"
											+ "<td>"
											+ edate
											+ "</td>"
											
											+ " </tr>";
								
							}

							/* $('#sample_editable_1').dataTable().fnClearTable(); */
							$('#sample_3').dataTable().fnClearTable();
							
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_3');
							
							
						}

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
						<i class="fa fa-edit"></i>Modem Life Cycle
					</div>

				</div>

				<div class="portlet-body">
					

						<table style="width: 38%">
							<tbody>
								<tr>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter Modem
									IMEI</lable> <input class="form-control" type="text" name="imei"
									id="imei" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button" style="margin-top: -3px;  margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="getLifeCycleData()">SUBMIT</button>


								</td>
							</tr>
							</tbody>
						</table>
						
						<p>&nbsp</p>

					<table class="table table-bordered "
						id="sample_3">
						<!-- 	<table class="table  " id="sample_3">  -->
						<thead>
							<tr style="width: 100%">

								<th style="min-width: 80px;">Sl No</th>
								<th style="min-width: 150px;">MODEM IMEI</th>
								<th style="min-width: 250px;">MODEM LIFECYCLE EVENTS</th>
								<th style="max-width: 250px;">LOCATION</th>
								<th style="min-width: 120px;">DATE</th>

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