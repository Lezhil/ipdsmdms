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
						$('#MDMSideBarContents,#metermang,#updateMeterTypeTab').addClass('start active ,selected');
						$(
								"#MDASSideBarContents,#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
								.removeClass('start active ,selected');

					});
</script>
<script type="text/javascript">
	function updateMasterMeterReason(col,imei,mtrno) {
		
		//alert(imei+"----"+mtrno);
		var dlms=$('#dlms_'+col).val();
		var mtrmake=$('#mtrmake_'+col).val();
		var model=$('#model_'+col).val();
		var tenderNo=$('#tenderNo_'+col).val();
		//alert("type-----"+type);
		
		

		if (dlms==null||dlms=="") {
			bootbox.alert("Please Select DLMS/Non-DLMS of the Meter.");
		} else if(mtrmake==null || mtrmake==""){
			bootbox.alert("Please Enter Meter Make.");
		}  else {
			
		    bootbox.confirm({
		        message: "Are you confirm you want to submit!",
		        buttons: {
		            confirm: {
		                label: 'Yes',
		                className: 'btn-success'
		            },
		            cancel: {
		                label: 'No',
		                className: 'btn-danger'
		            }
		        },
		        callback: function (result) {
		            if(result){
		            	
		            	 $.ajax({
								url : './updateMeterTypeSubmitMDAS',
								type : "POST",
								data : {
									imei : imei,
									mtrno : mtrno,
									dlms : dlms,
									mtrmake : mtrmake,
									model : model,
									tenderNo : tenderNo,
									
								},
								dataType : "text",
								async : false,
								success : function(response) {

									if (response == "SUCCESS") {
										bootbox
												.alert("Reasons for Data Non-Availability Updated Successfully.");

									} else {
										bootbox
												.alert("OOPS!! Something Went wrong! Try again later!");
									}

								}

							});
		            	
		            	
		            }
		        }
		    });
			
			
			/* */
		} 
	}

	function updateFdrTable() {

		var mterno = $('#mterno').val();
		/* alert('IMEI====='+imeiVal); */
		if (mterno==null||mterno=="") {
			bootbox.alert("Please Enter Meter No");
		} else {
		
			$
					.ajax({
						url : "./getFeedersBasedOnMeterNoMDAS/" + mterno,
						type : "GET",
						dataType : "json",
						async : false,
						success : function(response) {
							var x = JSON.stringify(response);
							/* alert('result====='+x); */
	
							if (response.length == 0 || response.length == null) {
								bootbox
										.alert("Data Not Available for this Meter No. : "
												+ mterno);
							} else {
								var html = "";
								var select=new Array();
								for (var i = 0; i < response.length; i++) {
									var resp = response[i];
										
									html += "<tr>" + "<td id='mtrno' style='font-weight: bold;'>"
									+ resp.mtrno
									+ "</td>"
									+ "<td>"
									+ resp.substation
									+ "</td>"
									/* + "<td>"
									+ resp.fdrname
									+ "</td>" */
									
									+ "<td><select id='dlms_"+i+"' class='form-control select2me input-small' name='frq'>"
									+ "<option selected disabled>Select</option>"
									+ "<option id='DLMS'>DLMS</option>"
									+ "<option id='Non-DLMS'>Non-DLMS</option>"
									+ "</select>	</td>"
									
									
									+ "<td>"
									+ "<input class='form-control' type='text' id='mtrmake_"+i+"' value='"+resp.mtrmake+"'>"
									+ "</td>"
									+ "<td>"
									+ "<input class='form-control' type='text' id='model_"+i+"' value='"+(resp.model_no==null?'':resp.model_no)+"'>"
									+ "</td>"
									+ "<td>"
									+ "<input class='form-control' type='text' id='tenderNo_"+i+"' value='"+(resp.tender_no==null?'':resp.tender_no)+"'>"
									+ "</td>"
									+ "<td><button type='button'  data-dismiss='modal' class='btn blue' onclick='updateMasterMeterReason(\""+i+"\",\""+resp.modem_sl_no+"\",\""+resp.mtrno+"\")'>SUBMIT</button></td>"

									+ " </tr>";
									select.push(resp.dlms);
									
								}
	
								/* $('#sample_editable_1').dataTable().fnClearTable(); */
								$('#sample_3').dataTable().fnClearTable();
								
								$('#updateMaster').html(html);
								loadSearchAndFilter('sample_3');
								for (var i = 0; i < select.length; i++) {
									var data=select[i];
									$('#dlms_'+i).val(data).trigger("change");
								}
								
							}
	
						}
	
					});
		}
	}

	function showCircle(zone) {
		$
				.ajax({
					url : './showCircleMDAS' + '/' + zone,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='circle' name='circle' onchange='showDivision(this.value)' class='form-control input-medium' type='text'><option value=''>Circle</option><option value='All'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#circleTd").html(html);
						$('#circle').select2();
					}
				});
	}

	function showDivision(circle) {
		var zone = $('#zone').val();
		$
				.ajax({
					url : './showDivisionMDAS' + '/' + zone + '/' + circle,
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					success : function(response) {
						var html = '';
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Division</option><option value='All'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#divisionTd").html(html);
						$('#division').select2();
					}
				});
	}

	function showSubdivByDiv(division) {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		$('#sdoCode').find('option').remove();
		$.ajax({
			url : './showSubdivByDivMDAS' + '/' + zone + '/' + circle + '/'
					+ division,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response1) {
				$('#sdoCode').append($('<option>', {
					value : "",
					text : "Sub-Division"
				}));
				$('#sdoCode').append($('<option>', {
					value : "All",
					text : "All"
				}));

				/* var html='';
				html+="<select id='sdoCode' name='sdoCode' class='form-control select2me input-medium' type='text'><option value='noVal'>Sub-Division</option><option value='All'>ALL</option>"; */
				for (var i = 0; i < response1.length; i++) {
					//var response=response1[i];
					/* html+="<option  value='"+response1[i]+"'>"+response1[i]+"</option>"; */

					$('#sdoCode').append($('<option>', {
						value : response1[i],
						text : response1[i]
					}));
				}
				/* html+="</select><span></span>";
				$("#subdivTd").html(html); */
				//$('#subdiv').select2();
			}
		});
	}

	function getFeeder() {
		var zone = $('#zone').val();
		var circle = $('#circle').val();
		var division = $('#division').val();
		var subdiv = $('#sdoCode').val();
		$('#updateMaster').empty();
		$.ajax({
			url : './getFeedersBasedOnMDAS' + '/' + zone + '/' + circle + '/'
					+ division + '/' + subdiv,
			type : 'GET',
			dataType : 'json',
			asynch : false,
			cache : false,
			success : function(response) {
				if (response.length != 0) {
					var html = "";
					var select=new Array();
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];

						html += "<tr>" + "<td id='mtrno' style='font-weight: bold;'>"
						+ resp.mtrno
						+ "</td>"
						+ "<td>"
						+ resp.substation
						+ "</td>"
						/* + "<td>"
						+ resp.fdrname
						+ "</td>" */
						
						+ "<td><select id='dlms_"+i+"' class='form-control select2me input-small' name='frq'>"
						+ "<option selected disabled>Select</option>"
						+ "<option id='DLMS'>DLMS</option>"
						+ "<option id='Non-DLMS'>Non-DLMS</option>"
						+ "</select>	</td>"
						
						
						+ "<td>"
						+ "<input class='form-control' type='text' id='mtrmake_"+i+"' value='"+resp.mtrmake+"'>"
						+ "</td>"
						+ "<td>"
						+ "<input class='form-control' type='text' id='model_"+i+"' value='"+(resp.model_no==null?'':resp.model_no)+"'>"
						+ "</td>"
						+ "<td>"
						+ "<input class='form-control' type='text' id='tenderNo_"+i+"' value='"+(resp.tender_no==null?'':resp.tender_no)+"'>"
						+ "</td>"
						+ "<td><button type='button'  data-dismiss='modal' class='btn blue' onclick='updateMasterMeterReason(\""+i+"\",\""+resp.modem_sl_no+"\",\""+resp.mtrno+"\")'>SUBMIT</button></td>"

						+ " </tr>";
						select.push(resp.dlms);
					}
					$('#sample_3').dataTable().fnClearTable();
					$('#updateMaster').html(html);
					loadSearchAndFilter('sample_3');
					for (var i = 0; i < select.length; i++) {
						var data=select[i];
						$('#dlms_'+i).val(data).trigger("change");
					}
				} else {
					bootbox
					.alert("Meter not exist in the master main table ");
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
						<i class="fa fa-edit"></i>UPDATE METER TYPE
					</div>

				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
									<td><select class="form-control select2me input-medium"
										name="zone" id="zone" onchange="showCircle(this.value);">
											<option value="">Zone</option>
											<option value='All'>ALL</option>
											<c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="circleTd"><select
										class="form-control select2me input-medium" id="circle"
										name="circle">
											<option value="">Circle</option>
											<option value='All'>ALL</option>
											<c:forEach items="${circleList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="divisionTd"><select
										class="form-control select2me input-medium" id="division"
										name="division">
											<option value="">Division</option>
											<option value='All'>ALL</option>
											<c:forEach items="${divisionList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>

									<td id="subdivTd"><select
										class="form-control select2me input-medium" id="sdoCode"
										name="sdoCode">
											<option value="">Sub-Division</option>
											<option value='All'>ALL</option>
											<c:forEach items="${subdivList}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
									</select></td>

									<td>
										<button type="button" id="viewFeeder" style="margin-left: 20px;"
											onclick="return getFeeder()" name="viewFeeder"
											class="btn yellow">
											<b>View</b>
										</button> <!-- <button type="submit" id="viewFdrOnMap" onclick="return modemMngtVal(this.form);"  class="btn purple" formaction="./fdrOnMapdetails" name="viewModems" formmethod="post" >View</button> -->
									</td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>

				<div class="portlet-body">


					<table class="table ">

						<tbody>

							<tr>
								<td
									style="font-size: 13px; width: 208px; height: 42px; padding-right: 0px;">
									<lable style="font-size:18px;font-weight: oblique">Enter
									Meter No</lable> <input class="form-control" type="text" name="rev_charge"
									id="mterno" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button" style="margin-top: -3px;  margin-left: 10px;"
										data-dismiss="modal" class="btn blue"
										onclick="updateFdrTable()">SUBMIT</button>


								</td>
							</tr>
						</tbody>
					</table>


					<p>&nbsp</p>

					<table class="table table-striped table-hover table-bordered "
						id="sample_3">
						<!-- 	<table class="table  " id="sample_3">  -->
						<thead>
							<tr>

								
								<th>METER NO</th>
								<th>SUB-STATION</th>
								<!-- <th>FEEDER NAME</th> -->
								<th>DLMS/Non-DLMS</th>
								<th>METER MAKE</th>
								<th>METER MODEL</th>
								<th>TENDER NO</th>
								<th></th>

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