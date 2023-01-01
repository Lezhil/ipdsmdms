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
						$('#MDASSideBarContents,#MIS-Reports,#reasonforUnavailabilityofDataMDAS').addClass('start active ,selected');
						 $("#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n" +
							"#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager").removeClass('start active ,selected');
					
					});
</script>
<script type="text/javascript">
	function updateMasterMeterReason(col,imei,mtrno) {
		
		//alert(imei+"----"+mtrno);
		var reason=$('#reason_'+col).val();
		//alert("type-----"+type);
		
		

		if (reason==null||reason=="") {
			bootbox.alert("Select the Reason of Non-Availability");
		} else {
			
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
								url : './updateMasterMeterReasonSubmitMDAS',
								type : "POST",
								data : {
									imei : imei,
									mtrno : mtrno,
									reason : reason,
									
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
	
									
	
										html += "<tr>" + "<td>"
												+ resp.zone
												+ "</td>"
												+ "<td>"
												+ resp.circle
												+ "</td>"
												+ "<td>"
												+ resp.division
												+ "</td>"
												+ "<td>"
												+ resp.subdivision
												+ "</td>"
												+ "<td>"
												+ resp.substation
												+ "</td>"
												+ "<td id='mtrno' style='font-weight: bold;'>"
												+ resp.mtrno
												+ "</td>"
												+ "<td>"
												+ resp.customer_name
												+ "</td>"
												+ "<td>"
												+ resp.accno
												+ "</td>"
												+ "<td><select id='reason_"+i+"' class='form-control select2me input-small' name='frq'>"
												+ "<option selected disabled>Select</option>"
												+ "<option id='Meter defective'>Meter defective</option>"
												+ "<option id='Meter port not working'>Meter port not working</option>"
												+ "<option id='Meter to modem cable faulty'>Meter to modem cable faulty</option>"
												+ "<option id='Modem faulty'>Modem faulty</option>"
												+ "<option id='Cable Disconnected'>Cable Disconnected</option>"
												+ "<option id='Modem Power Off'>Modem Power Off</option>"
												+ "</select>	</td>"
												+ "<td><button type='button'  data-dismiss='modal' class='btn blue' onclick='updateMasterMeterReason(\""+i+"\",\""+resp.modem_sl_no+"\",\""+resp.mtrno+"\")'>SUBMIT</button></td>"
	
												+ " </tr>";
									select.push(resp.non_availabilityOf_data);
								}
	
								/* $('#sample_editable_1').dataTable().fnClearTable(); */
								$('#sample_3').dataTable().fnClearTable();
								
								$('#updateMaster').html(html);
								loadSearchAndFilter('sample_3');
								for (var i = 0; i < select.length; i++) {
									var data=select[i];
									$('#reason_'+i).val(data).trigger("change");
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
		$
				.ajax({
					url : './showDivisions/' +circle,
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
		var circle = $('#circle').val();
		$('#sdoCode').find('option').remove();
		$.ajax({
			url : './showSubdivByDiv' + '/' + circle + '/'+ division,
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
		var circle = $('#circle').val();
		var division = $('#division').val();
		var subdiv = $('#sdoCode').val();
		$('#updateMaster').empty();
		$.ajax({
			url : './getAllMeterData',
			type : 'GET',
			dataType : 'json',
			data:{
        		circle : circle,
        		division : division,
        		subdiv : subdiv
        	},
			asynch : false,
			cache : false,
			success : function(response) {
				alert(response);
				if (response.length != 0) {
					var html = "";
					var select=new Array();
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];

						html += "<tr>" + "<td>"
						+ resp.circle
						+ "</td>"
						+ "<td>"
						+ resp.division
						+ "</td>"
						+ "<td>"
						+ resp.subdiv
						+ "</td>"
						+ "<td id='mtrno' style='font-weight: bold;'>"
						+ resp.mtrno
						+ "</td>"
						+ "<td>"
						+ resp.name
						+ "</td>"
						
						+ "<td>"
						+ resp.accno
						+ "</td>"
						
						+ "<td><select id='reason_"+i+"' class='form-control select2me input-small' name='frq'>"
						+ "<option selected disabled>Select</option>"
						+ "<option id='Meter defective'>Meter defective</option>"
						+ "<option id='Meter port not working'>Meter port not working</option>"
						+ "<option id='Meter to modem cable faulty'>Meter to modem cable faulty</option>"
						+ "<option id='Modem faulty'>Modem faulty</option>"
						+ "<option id='Cable Disconnected'>Cable Disconnected</option>"
						+ "<option id='Modem Power Off'>Modem Power Off</option>"
						+ "</select>	</td>"
						+ "<td><button type='button'  data-dismiss='modal' class='btn blue' onclick='updateMasterMeterReason(\""+i+"\",\""+resp.modem_sl_no+"\",\""+resp.mtrno+"\")'>SUBMIT</button></td>"

						+ " </tr>";
						select.push(resp.non_availabilityOf_data);
					}
					$('#sample_3').dataTable().fnClearTable();
					$('#updateMaster').html(html);
					loadSearchAndFilter('sample_3');
					for (var i = 0; i < select.length; i++) {
						var data=select[i];
						$('#reason_'+i).val(data).trigger("change");
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
						<i class="fa fa-edit"></i>UPDATE REASONS FOR DATA NON-AVAILABILITY
					</div>

				</div>

				<div class="portlet-body">
					<div class="row" style="margin-left: -1px;">

						<table style="width: 38%">
							<tbody>
								<tr>
								<%-- 	<td><select class="form-control select2me input-medium"
										name="zone" id="zone" onchange="showCircle(this.value);">
											<option value="">Zone</option>
											<option value='All'>ALL</option>
											<c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
									</select></td>
 --%>
									<td id="circleTd"><select
										class="form-control select2me input-medium" id="circle" onchange="showDivision(this.value)"
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
								<th>CIRCLE</th>
								<th>DIVISION</th>
								<th>SUB-DIVISION</th>
								<th>METER NO</th>
								<th>CONSUMER NAME</th>
								<th>ACCOUNT NO.</th>
								<th>REASON</th>
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