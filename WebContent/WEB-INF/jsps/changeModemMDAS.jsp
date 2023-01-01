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
						$('#MDASSideBarContents,#modemMang,#modemChange')
						.addClass('start active ,selected');
				$(
						"#dash-board,#user-location,#MIS-Reports,#MIS-Reports-photoBilling,#Cash_Collection,#vigilance-management,#Disconn-Reconn")
						.removeClass('start active ,selected');

			});
</script>
<script type="text/javascript">
	function updateMasterFdrStatusSubmit(col,imei,mtrno) {
		
		//alert(imei+"----"+mtrno);
		var type=$('#masterFdrType_'+col).val();
		//alert("type-----"+type);
		
		

		if (type==null||type=="") {
			bootbox.alert("Select the type of feeder");
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
								url : './updateMasterFdrStatusSubmit',
								type : "POST",
								data : {
									imei : imei,
									mtrno : mtrno,
									type : type,
									
								},
								dataType : "text",
								async : false,
								success : function(response) {

									if (response == "SUCCESS") {
										bootbox
												.alert("Feeder status column in Master Table updated successfully");

									} else {
										bootbox
												.alert("Feeder status column Updation in  MASTER TABLE was failed");
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

		var imeiVal = $('#IMEI').val();
		/* alert('IMEI====='+imeiVal); */
		$
				.ajax({
					url : "./getFeedersBasedOnMDAS/" + imeiVal,
					type : "GET",
					dataType : "json",
					async : false,
					success : function(response) {
						var x = JSON.stringify(response);
						/* alert('result====='+x); */

						if (response.length == 0 || response.length == null) {
							bootbox
									.alert("Data Not Available for this IMEI No. : "
											+ imeiVal);
							$('#updateMaster').empty();
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
											+ "<td>"
											+ resp.mtrno
											+ "</td>"
											+ "<td id='modem_sl_no' style='font-weight: bold;'><a data-toggle='modal' href='#basic' onclick='getMasterDetails(\""+resp.mtrno+"\",\""+resp.modem_sl_no+"\",\""+resp.zone+"\",\""+resp.circle+"\",\""+resp.division+"\",\""+resp.subdivision+"\",\""+resp.substation+"\",\""+resp.fdrname+"\",\""+resp.fdrcode+"\",\""+resp.simno+"\",\""+resp.customer_name+"\");'>"
											+ resp.modem_sl_no
											+ "</a></td>"
											+ " </tr>";
								
							}

							/* $('#sample_editable_1').dataTable().fnClearTable(); */
							$('#sample_3').dataTable().fnClearTable();
							
							$('#updateMaster').html(html);
							loadSearchAndFilter('sample_3');
							for (var i = 0; i < select.length; i++) {
								var data=select[i];
								$('#masterFdrType_'+i).val(data).trigger("change");
							}
							
						}

					}

				});

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
						+ "<td>"
						+ resp.mtrno
						+ "</td>"
						+ "<td id='modem_sl_no' style='font-weight: bold;'><a data-toggle='modal' href='#basic' onclick='getMasterDetails(\""+resp.mtrno+"\",\""+resp.modem_sl_no+"\",\""+resp.zone+"\",\""+resp.circle+"\",\""+resp.division+"\",\""+resp.subdivision+"\",\""+resp.substation+"\",\""+resp.fdrname+"\",\""+resp.fdrcode+"\",\""+resp.simno+"\");'>"
						+ resp.modem_sl_no
						+ "</a></td>"
						+ " </tr>";
						
					}
					$('#sample_3').dataTable().fnClearTable();
					$('#updateMaster').html(html);
					loadSearchAndFilter('sample_3');
					for (var i = 0; i < select.length; i++) {
						var data=select[i];
						$('#masterFdrType_'+i).val(data).trigger("change");
					}
				} else {
					bootbox
					.alert("Feeder not exist in the master main table ");
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
						<i class="fa fa-edit"></i>CHANGE MODEM
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
										<button type="button" id="viewFeeder"
											style="margin-left: 20px;" onclick="return getFeeder()"
											name="viewFeeder" class="btn yellow">
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
									IMEI</lable> <input class="form-control" type="text" name="rev_charge"
									id="IMEI" autocomplete="off" style="width: 206px;">

								</td>
								<td>
									<p>&nbsp</p>

									<button type="button"
										style="margin-top: -3px; margin-left: 10px;"
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

								<th>ZONE</th>
								<th>CIRCLE</th>
								<th>DIVISION</th>
								<th>SUB-DIVISION</th>
								<th>SUB-STATION</th>
								<th>METER NO</th>
								<th>IMEI</th>


							</tr>
						</thead>
						<tbody id="updateMaster">

						</tbody>
					</table>

				</div>


				<div class="modal fade" id="basic" tabindex="-1" role="basic"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #4b8cf8;">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" ><font style="font-weight: bold; color: white;">Change IMEI Number</font></a></h4>
							</div>
							<div class="modal-body">

								<form action="#" id="changeModemDetailsForm">
									
									<div class="row">
										<div class="form-group col-md-6">
											<label>Zone</label>
											<div class="input-group">
												<span class="input-group-addon"> <i class="fa fa-map-marker"></i>
												</span> <input type="text" class="form-control" placeholder="Zone" id="zoneN" name="zoneN" readonly="readonly">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>Ciecle</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-map-marker"></i>
												</span> <input type="text" class="form-control" id="circleN"  name="circleN" readonly="readonly"
													placeholder="Circle">
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="form-group col-md-6">
											<label>Division</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-map-marker"></i>
												</span> <input type="text" class="form-control" id="divisionN" name="divisionN" readonly="readonly"
													placeholder="Division">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>Sub-Division</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-map-marker"></i>
												</span> <input type="text" class="form-control" id="subdivisionN" name="subdivisionN" readonly="readonly"
													placeholder="Sub-Division">
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="form-group col-md-6">
											<label>Sub-Station</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-flash"></i>
												</span> <input type="text" class="form-control" id="substation" name="substation" readonly="readonly"
													placeholder="Sub-Station">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>Consumer Name</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-flash"></i>
												</span> <input type="text" class="form-control" id="feedername" name="feedername" readonly="readonly"
													placeholder="Feeder Name">
											</div>
										</div>
									</div>
									
									<div class="row">
										<!-- <div class="form-group col-md-6">
											<label>Feeder Code</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-flash"></i> 
												</span> <input type="text" class="form-control" id="feedercode" name="feedercode" readonly="readonly"
													placeholder="Meter No">
											</div>
										</div> -->
										<div class="form-group col-md-6" hidden="true">
											<label>Sim No.</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-envelope"></i>
												</span> <input type="text" class="form-control" id="old_sim_no" name="old_sim_no" readonly="readonly"
													placeholder="Sim No">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>Meter No.</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-tachometer"></i>
												</span> <input type="text" class="form-control" id="mtr_no" name="mtr_no" readonly="readonly"
													placeholder="Meter No">
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="form-group col-md-6">
											<label>IMEI No.</label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-mobile"></i>
												</span> <input type="text" class="form-control" id="old_imei_no" name="old_imei_no" readonly="readonly"
													placeholder="IMEI No.">
											</div>
										</div>
										<div class="form-group col-md-6">
											<label>New IMEI No.  <font color="red">*</font></label>
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="fa fa-mobile"></i>
												</span> <input type="text" class="form-control" id="new_imei_no" name="new_imei_no" autofocus 
													placeholder="New IMEI No">
											</div>
										</div>
									</div>






								</form>



							</div>
							<div class="modal-footer">
								<button type="button" class="btn dark btn-outline"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn green" onclick="saveImeiNoChanges();">Save changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>



			</div>
		</div>
	</div>

</div>


<script>

	$(document).ready(function(){
	
	    $("#basic").on('shown.bs.modal', function(){
	
	        $(this).find('#new_imei_no').focus();
	
	    });
	
	});
	
	
	function getMasterDetails(mtrno,imei,zone,circle,division,subdivision,substation,fdrname,fdrcode,simno,customer_name) {
		/* alert(mtrno+"----"+imei);
		alert(mtrno+"----"+imei+"----"+zone+"----"+circle+"----"+division+"----"+subdivision+"----"+substation+"----"+fdrname+"----"+fdrcode+"----"+simno); */
		$("#changeModemDetailsForm")[0].reset();
		
		$("#zoneN").val(zone);
		$("#circleN").val(circle);
		$("#divisionN").val(division);
		$("#subdivisionN").val(subdivision);
		$("#substation").val(substation);
		$("#feedername").val(customer_name);
		$("#feedercode").val(fdrcode);
		$("#old_sim_no").val(simno);
		$("#old_imei_no").val(imei);
		$("#mtr_no").val(mtrno);
		
		
	} 

	
	function saveImeiNoChanges() {
		var new_imei_no=$('#new_imei_no').val();
		if(new_imei_no=="" || new_imei_no==null){
			bootbox.alert("Please Enter New IMEI Number.");
			return false;
		} else{
			
			if($.isNumeric( new_imei_no)){
				if(new_imei_no== parseInt(new_imei_no)){
					
					$.ajax({
				          type: 'GET',
				          url: './checkUniqueIMEINoMDAS/'+new_imei_no,
				          dataType : 'TEXT',
						  asynch : false,
						  cache : false,
				          success: function (response) {
				        	  
				        	  //alert("response : "+response);
					           if(response=="EXIST"){
					        	   bootbox.alert("IMEI NO Already Exist");
					           } else{
					        	   
					        	   $.ajax({
								          type: 'POST',
								          url: './changeModemImeiNoMDAS',
								          data: $('#changeModemDetailsForm').serialize(),
								          dataType : 'TEXT',
										  asynch : false,
										  cache : false,
								          success: function (response1) {
									           if(response1=="SUCCESS"){
									        	   bootbox.alert("IMEI No Changed Successfully.");
									        	   $("#basic .close").click();
									        	   
									           } else{
									        	   bootbox.alert("OOPS!! Something Went wrong, please try again!");
									           }
								        	  
								          }
								        });
					        	   
					        	   
					           }
				        	  
				          }
				        });
					
					
					
					
					
					
					
					
				} else{
					bootbox.alert("IMEI Number Cannot be fractional.");
					return false;
				}
			} else{
				bootbox.alert("IMEI Number Can Contain Number Only.");
				return false;
				
			}
			
			
		}
		
		
		 

        
	}
	

</script>

<style>
a {
    text-shadow: none;
    color: #337ab7;
}


</style>