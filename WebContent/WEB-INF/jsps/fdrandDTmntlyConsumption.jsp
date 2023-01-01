<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="resources/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>

<script src="resources/assets/scripts/app.js"></script>
<script src="resources/assets/scripts/ui-extended-modals.js"></script>


<script type="text/javascript">
	$(".page-content")
			.ready(
					function() {

						$("#MIS-Reports").addClass('start active , selected');

						App.init();
						TableEditable.init();
						Tasks.initDashboardWidget();
						FormComponents.init();

						$(
								'#eaId,#fdrandDTmntlyConsumptionId')
								.addClass('start active ,selected');
						$(
								"#MDMSideBarContents,#MDASSideBarContents,#dash-board,#360d-view,#cumulative-Analysis,#cmri-Manager,#cdf-Import,#system-Security,#meter-Observations,#interval-DataAnalyzer,#events-Analyzer,\n"
										+ "#exceptions-Analyzer,#Load-SurveyAnalyzer,#instantaneous-Parameters,#VEE-RulesEngine,#Assessment-Reports,#meterMaster,#newConnectionId,#batchStatusId,#newcdf,#newSealManager")
								.removeClass('start active ,selected');

						Areawise();
						loactionwise();
						reportWise();
						loadSearchAndFilter('sample_3');
						$('#sample_3_wrapper').hide();
						$('#tools').hide();

					});
</script>
<script>
	function showCircle(zone) {
		$
				.ajax({
					url : './getCircleByZone',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone
					},
					success : function(response) {
						var html = '';
						html += "<select id='LFcircle' name='LFcircle' onchange='showTownNameandCode(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
						for (var i = 0; i < response.length; i++) {
							html += "<option  value='"+response[i]+"'>"
									+ response[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#LFcircleTd").html(html);
						$('#LFcircle').select2();
					}
				});
	}

	function showDivision(circle) {
		var zone = $('#zone').val();

		$
				.ajax({
					url : './getDivByCircle',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle
					},
					success : function(response) {
						var html = '';
						html += "<select id='division' name='division' onchange='showSubdivByDiv(this.value)' class='form-control input-medium' type='text'><option value=''>Select Division</option><option value='%'>ALL</option>";
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
		$
				.ajax({
					url : './getSubdivByDiv',
					type : 'GET',
					dataType : 'json',
					asynch : false,
					cache : false,
					data : {
						zone : zone,
						circle : circle,
						division : division
					},
					success : function(response1) {
						var html = '';
						html += "<select id='subdiv' name='subdiv'  class='form-control input-medium' type='text'><option value=''>Select Sub-Division</option><option value='%'>ALL</option>";
						for (var i = 0; i < response1.length; i++) {
							//var response=response1[i];
							html += "<option  value='"+response1[i]+"'>"
									+ response1[i] + "</option>";
						}
						html += "</select><span></span>";
						$("#subdivTd").html(html);
						$('#sdoCode').select2();
					}
				});
	}
	
	function showCircleforFeeder(zoneForFdr) {
		$	.ajax({
				url : './getCircleByZoneForFdr',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zoneForFdr : zoneForFdr
				},
				success : function(response) {
				//	alert(response);
					var html = '';
					html += "<select id='circleForFdr' name='circleForFdr' onchange='showTownNameandCodeforFeeder(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#circleForFdr").html(html);
					$('#circleForFdr').select2();
				}
			});
	} 


	function showTownNameandCodeforFeeder(circleForFdr){

	 var zoneForFdr = $('#zoneForFdr').val();
	// alert(zoneForFdr);
	//var zone =  '${newRegionName}';   
	   $.ajax({
	      	url:'./getTownNameandCodebyCircleForFdr',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	      		zoneForFdr : zoneForFdr,
	      		circleForFdr : circleForFdr
	  		},
	  		success : function(response1) {
		  	//	alert(response1);
	  			
	            var html = '';
	            html += "<select id='townForFdr' name='townForFdr' onchange='showSubStaTionByTown(this.value)' class='form-control  input-medium'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
	           
	            for (var i = 0; i < response1.length; i++) {
	                //var response=response1[i];
	                
	                html += "<option value='"+response1[i][0]+"'>"
	                        +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                           }
	            html += "</select><span></span>";
	            $("#townForFdr").html(html);
	            $('#townForFdr').select2();
	           
	            
	        }
	  	});
	  }
	  
	function showCircleforFeederbyRegion(zoneForFdrbyReg) {
		var zoneForFdrbyReg =  '${newRegionName}';   
		$	.ajax({
				url : './getCircleByZoneForFdrbyReg',
				type : 'GET',
				dataType : 'json',
				asynch : false,
				cache : false,
				data : {
					zoneForFdrbyReg : zoneForFdrbyReg
				},
				success : function(response) {
				//	alert(response);
					var html = '';
					html += "<select id='circleForFdrbyReg' name='circleForFdrbyReg' onchange='showTownNameandCodeforFeederByRegion(this.value)' class='form-control select2me' type='text'><option value=''>Select Circle</option><option value='%'>ALL</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>"
								+ response[i] + "</option>";
					}
					html += "</select><span></span>";
					$("#circleTd7").html(html);
					$('#circleForFdrbyReg').select2();
				}
			});
	} 


	function showTownNameandCodeforFeederByRegion(circleForFdrbyReg){

	 //var zoneForFdrbyReg = $('#zoneForFdrbyReg').val();
	// alert(zoneForFdr);
	var zoneForFdrbyReg =  '${newRegionName}';
	   $.ajax({
	      	url:'./getTownNameandCodebyCircleForFdrbyReg',
	      	type:'GET',
	      	dataType:'json',
	      	asynch:false,
	      	cache:false,
	      	data : {
	      		zoneForFdrbyReg : zoneForFdrbyReg,
	      		circleForFdrbyReg : circleForFdrbyReg
	  		},
	  		success : function(response1) {
		  	//	alert(response1);
	  			
	            var html = '';
	            html += "<select id='townForFdrbyReg' name='townForFdrbyReg' onchange='showSubStaTionByTown(this.value)' class='form-control  input-medium'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
	           
	            for (var i = 0; i < response1.length; i++) {
	                //var response=response1[i];
	                
	                html += "<option value='"+response1[i][0]+"'>"
	                        +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                           }
	            html += "</select><span></span>";
	            $("#townForFdrbyReg").html(html);
	            $('#townForFdrbyReg').select2();
	           
	            
	        }
	  	});
	  }

	function viewData() {
		$('#sample_1').dataTable().fnClearTable();
		var LocationType = $("#LocationTypeId").val();
		var LocIdentity = $("#LocIdentityId").val();
		var meterno = $("#meternoId").val();
		var monthyr = "";
		if (LocationType == '') {

			bootbox.alert("Please select LocationType");
			return false;
		}
		if (LocIdentity == '' && meterno == '') {

			bootbox.alert("Please select Either LocationIdentity/MeterNo ");
			return false;
		}

		$('#sample_1').show();
		$('#sample_3').hide();
		$('#sample_3_wrapper').hide();
		$.ajax({
			url : "./getlocationwisedata",
			type : 'GET',
			data : {
				LocIdentity : LocIdentity,
				meterno : meterno,
				LocationType : LocationType

			},
			success : function(response) {
				//$("#sample_1").show();
				var flag1 = "";
				if (LocationType == "DT") {
					flag1 = "DT id";
					flag2 = "DT Name";

				} else {
					flag1 = "Feeder id";
					flag2 = "Feeder Name";
				}

				$("#thLocfdrId").html(flag1);
				$("#thLocfdrCodeId").html(flag2);
				if (response.length != 0) {

					var html = "";
					for (var i = 0; i < response.length; i++) {
						var resp = response[i];
						html += "<tr>" + "<td>" + resp[0] + "</td>" + "<td>"
								+ resp[1] + "</td>" + "<td>" + resp[2]
								+ "</td>" + "<td>" + resp[3] + "</td>" + "<td>"
								+ resp[4] + "</td>" + "<td>" + resp[5]
								+ "</td>" + "<td>" + resp[6] + "</td>" + "<td>"
								+ resp[7] + "</td>" + "<td>" + resp[8]
								+ "</td>" +
								/*  "<td>Edit</td></tr>"; */
								"<td><a href='#' onclick=EditFeederConsumption('"
								+ resp[9] + "','" + resp[5] + "','" + resp[10]
								+ "','" + monthyr
								+ "','AVAILABLELOC')>Edit</a></td >" + +"</tr>";

					}
					$('#sample_1').dataTable().fnClearTable();
					$("#UpadteLocationwise").html(html);
					loadSearchAndFilter('sample_1');
				} else {
					$('#sample_1').dataTable().fnClearTable();
					$("#UpadteLocationwise").html(html);
					bootbox.alert("No Data Found");

				}
			},
			complete : function() {
				loadSearchAndFilter('sample_1');
			}

		});

	}
	
	function report() {
		
		$('#sample_3').dataTable().fnClearTable();
		var zone=$("#zoneForFdr").val();
		var circle = $('#circleForFdr').val();
		var monthyr = $("#MonthYearIdReport").val();
		var locationtype = $("#locationtypeIdAreaReport").val();
		var town_code = $('#townForFdr').val();
		// alert(town_code);
		 //alert(circle) ;
		 
		 if (zone == '') {
			bootbox.alert("please Select Region");
			return false;
		}
		
		if (circle == '') {
			bootbox.alert("please Select circle");
			return false;
		}
		/* if (division == 0 || division == null) {
			division = "%";

		}
		if (subdiv == 0 || subdiv == null) {
			subdiv = "%";
		} */

		if (town_code == '') {
			bootbox.alert("please Select Town");
			return false;
		}
		
		if (monthyr == '') {
			bootbox.alert("please select Month/Year");
			return false;
		}
		if (locationtype == '') {
			bootbox.alert("please select locationtype");
			return false;
		}

		$('#sample_5').show();
		$('#sample_1').hide();
		$('#sample_3').hide();
		$('#tools').show();
		/* $('#sample_3_wrapper').show(); */
		$
				.ajax({
					url : "./getMonthlyConsumptionReport",
					type : 'POST',
					data : {
						circle : circle,
						townCode:town_code,
						monthyr : monthyr,
						locationtype : locationtype
					},
					success : function(response) {
						var flag1 = "";
						var flag2 = "";
						var flag3 = "";
						if (locationtype == "DT") {
							flag1 = "DT CODE";
							flag2 = "DT NAME";
							flag3 = "FEEDER CODE";

						} else {
							flag1 = "FEEDER CODE";
							flag2 = "FEEDER NAME";
							flag3 = "SUBSTATION CODE";
						}

						$("#thfdrIdReport").html(flag1);
						$("#thfdrCodeIdReport").html(flag2);
						$("#thsubCodeIdReport").html(flag3);

						if (response.length != 0) {

							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>" + "<td>" + resp[0] + "</td>"
										+ "<td>" + resp[1] + "</td>" + "<td>"
										+ resp[2] + "</td>" + "<td>" + resp[3]
										+ "</td>" + "<td>" + resp[4] + "</td>"
										+ "<td>" + resp[5] + "</td>" + "<td>"
										+ resp[6] + "</td>" + "<td>" + resp[7]
										+ "</td>" + "<td>" + (resp[12] == null ? "0" : (resp[12])) 
										+ "</td>"+ "<td>" + resp[8]
										+ "</td>"+ "<td>" + resp[11]
										+ "</td>"+ "<td>" + resp[13]
										+ "</td>"+ "<td>" + ((resp[14] == null) ? "": (moment(resp[14]).format('YYYY-MM-DD HH:MM:SS')))
										+ "</td>";
								

							}
							$('#sample_5').dataTable().fnClearTable();
							$("#AreafeederLocationdataReport").html(html);

							loadSearchAndFilter('sample_5');
						//clearTabledataContent('sample_3');
						} else {
							$('#sample_5').dataTable().fnClearTable();
							$("#AreafeederLocationdataReport").html(html);
							bootbox.alert("No Data");
						}
					},
					complete : function() {
						
						loadSearchAndFilter('sample_5');
					}

				});
	}



	
	var refreshTab; 
	function EditFeederConsumption(id, mtrno, loccode, month, flag) {
		refreshTab= flag;
		document.getElementById('UpdateConsumption').reset();
		$('#Updateareabt').hide();
		$('#Updatedtbt').show();
		//$("#EditRemarkId").val(month);
		$('#mtrnoId').val(mtrno);
		$('#locodeId').val(loccode);
		$("#stack1").modal('show');
		if (id != 0) {
			$.ajax({
				url : "./togetConsumption",
				type : 'POST',
				data : {
					id : id,
				},
				success : function(response) {
					$('#ConsumptionId').val(response);
					$('#oldConsumptionId').val(response);
					$('#monthlyConsId').val(id);
					
				},

			});
		} else {
			$('#ConsumptionId').val(response);
			$('#oldConsumptionId').val("");
			$('#monthlyConsId').val(id);

		}
		
	}

	var refreshTab; 
	function EditFeederConsumpt(id, mtrno, loccode, month, flag) {
	refreshTab= flag;
	document.getElementById('UpdateConsumption').reset();
	$('#Updateareabt').hide();
	$('#Updatedtbt').show();
	//$("#EditRemarkId").val(month);
	$('#mtrnoId').val(mtrno);
	$('#locodeId').val(loccode);
	$("#stack1").modal('show');
	if (id != 0) {
		$.ajax({
			url : "./togetConsumpt",
			type : 'POST',
			data : {
				id : id,
			},
			success : function(response) {
				$('#ConsumptionId').val(response);
				$('#oldConsumptionId').val(response);
				$('#monthlyConsId').val(id);
				
			},

		});
	} else {
		$('#ConsumptionId').val(response);
		$('#oldConsumptionId').val("");
		$('#monthlyConsId').val(id);

	}
}


	
	
	function update() {
		var mtrno = $('#mtrnoId').val();
		var loccode = $('#locodeId').val();
		var month = $('#MonthYearId').val();
	
		var consumption = $("#ConsumptionId").val();
		var oldconsumption = $("#oldConsumptionId").val();

		var consumpt = $("#ConsumptId").val();
		var oldconsumpt = $("#oldConsumptId").val();
		var mid = $('#monthlyConsId').val();

		if (consumption == "") {
			bootbox.alert("Please enter consumption(kWh)");
			return false;
		}
		var remarks = $("#EdittextId").val();
		if (remarks == '') {
			bootbox.alert("Please enter remarks");
			return false;
		}
		$.ajax({
			url : "./updateConsumption",
			type : 'POST',
			data : {
				consumption : consumption,
				oldconsumption : oldconsumption,
				consumpt : consumpt,
				oldconsumpt : oldconsumpt,
				id : mid,
				remarks : remarks,
				loccode : loccode,
				mtrno : mtrno,
				month : month
			},
			success : function(response) {
				if (response == "exist") {
					bootbox.alert("MonthlyConsumption Updated Successfully");
					
					return false;
				} else if (response == "notexist") {
					bootbox.alert("MonthlyConsumption NotSaved ");
					return false;
				}
			},
			complete : function() {
				
				
				if(refreshTab == 'AVAILABLELOC'){
					viewData();
				}else {
					viewArea();
				}
				
			}
		});
		// $("#stack1").modal('close');
		
	}

	

	function viewArea() {
		
		$('#sample_3').dataTable().fnClearTable();
		var zone = $("#LFzone").val();
		var circle = $("#LFcircle").val();
		/* var division = $("#division").val();
		var subdiv = $("#subdiv").val(); */
		var monthyr = $("#MonthYearId").val();
		var locationtype = $("#locationtypeIdArea").val();
		var Consumavail = $("#ConsumavailId").val();
		
		var town_code = $("#LFtown").val();
		// alert(town_code);
		 //alert(circle) ;
		 
		 if (zone == '') {
			bootbox.alert("please Select Region");
			return false;
		}
		
		if (circle == '') {
			bootbox.alert("please Select circle");
			return false;
		}
		/* if (division == 0 || division == null) {
			division = "%";

		}
		if (subdiv == 0 || subdiv == null) {
			subdiv = "%";
		} */

		if (town_code == '') {
			bootbox.alert("please Select Town");
			return false;
		}
		
		if (monthyr == '') {
			bootbox.alert("please select Month/Year");
			return false;
		}
		if (locationtype == '') {
			bootbox.alert("please select locationtype");
			return false;
		}
		if (Consumavail == '') {

			bootbox.alert("please select Consumption Availability");
			return false;
		}


		$('#sample_3').show();
		$('#sample_1').hide();
		$('#sample_3_wrapper').show();
		$
				.ajax({
					url : "./getAreawisefeederdata",
					type : 'POST',
					data : {
						circle : circle,
						townCode:town_code,
						monthyr : monthyr,
						Consumavail : Consumavail,
						locationtype : locationtype
					},
					success : function(response) {
						var flag1 = "";
						var flag2 = "";
						var flag3 = "";
						if (locationtype == "DT") {
							flag1 = "DT CODE";
							flag2 = "DT NAME";
							flag3 = "FEEDER CODE";

						} else {
							flag1 = "FEEDER CODE";
							flag2 = "FEEDER NAME";
							flag3 = "SUBSTATION CODE";
						}

						$("#thfdrId").html(flag1);
						$("#thfdrCodeId").html(flag2);
						$("#thsubCodeId").html(flag3);

						if (response.length != 0) {

							var html = "";
							for (var i = 0; i < response.length; i++) {
								var resp = response[i];
								html += "<tr>" + "<td>" + resp[0] + "</td>"
										+ "<td>" + resp[1] + "</td>" + "<td>"
										+ resp[2] + "</td>" + "<td>" + resp[3]
										+ "</td>" + "<td>" + resp[4] + "</td>"
										+ "<td>" + resp[5] + "</td>" + "<td>"
										+ resp[6] + "</td>" + "<td>" + resp[7]
										+ "</td>";
								if (Consumavail == "Available") {
									html += "<td>" + resp[8] + "</td>"+"<td>" + resp[9] + "</td>"+ "<td>" + (resp[12] == null?"": resp[12])
									+ "</td>";
									
								} else if (Consumavail == "NotAvailable") {
									
									html += "<td>" + resp[8] + "</td>"+"<td>" + resp[9] + "</td>"+ "<td>" + (resp[12] == null?"": resp[12])
									+ "</td>";
								
								}

								
								
								if (Consumavail == "Available") {
									html += "<td><a href='#' onclick=EditFeederConsumption('"
											
											+ resp[10]
											+ "','"
											+ resp[5]
											+ "','"
											+ resp[11]
											+ "','"
											+ monthyr
											+ "','AVAILABLE')>Edit</a></td>"
											+ +"</tr>";
								}else if (Consumavail == "NotAvailable") {
									html += "<td><a href='#' onclick=EditFeederConsumption('0','"
										+ resp[5]
										+ "','"
										+ resp[10]
										+ "','"
										+ monthyr
										+ "','NOT_AVAILABLE')>Edit</a></td >"
										+ +"</tr>";
								}	

								
								
							}
							$('#sample_3').dataTable().fnClearTable();
							$("#AreafeederLocationdata").html(html);

							loadSearchAndFilter('sample_3');
						//clearTabledataContent('sample_3');
						} else {
							bootbox.alert("No Data");
						}
					},
					complete : function() {
						loadSearchAndFilter('sample_3');
					}

				});

	}


	

	function loactionwise() {
		$('#LocIdentityId').val('');
		$('#meternoId').val('');
		$('#sample_3').hide();
		$('#sample_5').hide();
		$('#sample_3_wrapper').hide();
		$('#sample_5_wrapper').hide();
		//$("#")
	}

	function Areawise() {
		
		$('#circle').val('').trigger('change');
		$('#division').val('').trigger('change');
		$('#subdiv').val('').trigger('change');
		$('#locationtypeIdArea').val(0).trigger('change');
		$('#ConsumavailId').val('').trigger('change');
		$('#sample_5').hide();
		$('#sample_4').hide();
		$('#sample_5_wrapper').hide();
		$('#sample_4_wrapper').hide();
	}
	
function reportWise() {
		
		$('#circle').val('').trigger('change');
		$('#locationtypeIdAreaReport').val(0).trigger('change');
		$('#sample_3').hide();
		$('#sample_4').hide();
		$('#sample_3_wrapper').hide();
		$('#sample_4_wrapper').hide();
	}
	
	function showTownNameandCode(circle){
		var zone =  $('#LFzone').val(); 
		
		   $.ajax({
		      	url:'./getTownNameandCodebyCircle',
		      	type:'GET',
		      	dataType:'json',
		      	asynch:false,
		      	cache:false,
		      	data : {
		  			zone : zone,
		  			circle:circle
		  		},
		  		success : function(response1) {
		  			
	                var html = '';
	                html += "<select id='LFtown' name='town'  class='form-control select2me'  type='text'><option value=''>Select Town </option><option value='%'>ALL</option>";
	                for (var i = 0; i < response1.length; i++) {
	                    //var response=response1[i];
	                    
	                    html += "<option value='"+response1[i][0]+"'>"
	                            +response1[i][0] +"-"  +response1[i][1] + "</option>";
	                }
	                html += "</select><span></span>";
	                $("#LFtownTd").html(html);
	                $('#LFtown').select2();
	            }
		  	});
		  }
</script>
<div class="page-content">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-reorder"></i>Feeder And DT Monthly Consumption
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a> <a
					href="#portlet-config" data-toggle="modal" class="config"></a> <a
					href="javascript:;" class="reload"></a> <a href="javascript:;"
					class="remove"></a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="modal-body">
				<div class="tabbable tabbable-custom tabbable-full-width">
					<ul class="nav nav-tabs" id="rowTab">
					<li class="active"><a href="#tab_1_2" onclick="return Areawise();"
							data-toggle="tab">Area Wise </a></li>
						<li ><a href="#tab_1_1"
							onclick="return loactionwise()" data-toggle="tab">Location
								Wise </a></li>
								<li ><a href="#tab_1_3"
							onclick="return reportWise()" data-toggle="tab">Edited Data Report
								 </a></li>
						
					</ul>
					<div class="tab-content">
						<div class="tab-pane fade" id="tab_1_1">
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label><strong>Location Type:-</strong><font color="red">*</font></label> <select
											class="form-control select2me" id="LocationTypeId" onclick="resetFields()"
											name="LocationTypeId">
											<option value="">Select</option>
											<option value="Boundary">BOUNDARY
											<option value="Feeder">FEEDER
											<option value="DT">DT
										</select>
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">
										<label><strong>Feeder/DT/Boundary Code :</strong></label> <input type="text"
											class="form-control placeholder-no-fix" id="LocIdentityId"
											name="LocIdentityId" placeholder="Enter Location Identity"></input>
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">
										<label><strong>Meter SrNo:</strong></label> <input type="text"
											class="form-control placeholder-no-fix" id="meternoId"
											name="meternoId" placeholder="Enter Meterno"></input>

									</div>
								</div>
								
								</div>
								
								<div class="row" >
									<div class="col-md-1">
										<div class="form-group">
											<a href="#" type="button" id="viewId" name="viewId" class="btn green" onclick="return viewData()">
												<b>View</b>
											</a>
										</div>
									 </div>
						
									
									<div class="col-md-4" >
										<div class="form-group">
											<a href="#" hidden="true" class="btn green" id="excelExportLocationWise" type="button" onclick="ExportToExcelLoc()"
											>
											Export to Excel</a>
												
										</div>
									</div>
								</div>

							<!-- 	<div class="col-md-3">
									<div class="form-group">
										<button type="button" id="viewId" name="viewId"
											class="btn yellow"  style="margin-bottom: -60px;" onclick="return viewData()">
											<b>View</b>
										</button>
									</div>
								</div>
								<div class="row">
									<br>
									<div class="col-md-3">
										<a href="#" id="excelExportLocationWise" class="btn green pull-right"
											type="button"
											onclick="ExportToExcelLoc()"
											style="position: relative; left: 210%">Export to Excel</a>
									</div>
								</div> -->
							
							<br>
                        
						
							<table class="table table-striped table-hover table-bordered"
								id="sample_1" hidden="true">

								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<th>SubStation Code</th>
										<th id="thLocfdrId">Feeder Code</th>
										<th id="thLocfdrCodeId">Feeder Name</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>MF</th>
										<th>Monthly Consumption(KWH) With MF</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody id="UpadteLocationwise">
								</tbody>
							</table>

							<table class="table table-striped table-hover table-bordered"
								id="sample_2" hidden="true">
								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<th>Subdivision Code</th>
										<th>SubStation Name</th>
										<th>SubStation Code</th>
										<th>DT Name</th>
										<th>DT Code</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>MF</th>
										<th>Monthly Consumption(KWH) With MF</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody id="UpadteLocationdtwise">
								</tbody>
							</table>

						</div>
						<!-- tab_1_1 -->
						<div class="tab-pane active" id="tab_1_2" hidden="true">
                          <jsp:include page="locationFilter.jsp"/> 
							<%-- <div class="row">
								<div class="col-md-3">
								<label>Region:</label>
									<div class="form-group">
										<select class="form-control select2me" name="zone" id="zone"
											onchange="showCircle(this.value);">
											<option value="">Select Region</option>
											<option value="%">ALL</option>
											<c:forEach var="elements" items="${zoneList}">
												<option value="${elements}">${elements}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-3">
								<label>Circle:</label>
									<div id="circleTd" class="form-group">
										<select class="form-control select2me" id="circle" onchange="showTownNameandCode(this.value)"
											name="circle">
											<option value="">Select Circle</option>
											<option value="%">ALL</option>
											<c:forEach items="${circleList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div id="divisionTd" class="form-group">
										<select class="form-control select2me" id="division"
											name="division">
											<option value="">Select Division</option>
											<option value="%">ALL</option>
											<c:forEach items="${divisionList}" var="elements">
												<option value="${elements}">${elements}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div id="subdivTd" class="form-group">
										<select class="form-control select2me" id="subdiv"
											name="subdiv">
											<option value="">Select Sub-Division</option>
											<option value="%">ALL</option>
											<c:forEach items="${subdivList}" var="sdoVal">
												<option value="${sdoVal}">${sdoVal}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								
								<div class="col-md-3">
								<label>Town:</label>
									<div id="townTd" class="form-group">
										<select class="form-control select2me" id="town"
											name="town">
											<option value="">Select Town</option>
											<option value="%">ALL</option>
										</select>
									</div>
								</div>
								
								
							</div> --%>
							<div class="row" >
								<div class="col-md-3" style="margin-right: 8px;padding-right: 4px;padding-left: 30px;">
									<!-- <div class="form-group">
										<label class="control-label">MonthYear:-<font
											color=red>*</font></label>
										<div class="input-group input-medium date date-picker"
											data-date-format="yyyymm" data-date-viewmode="month-year"
											id="fmonth">

											<input type="text" autocomplete="off" class="form-control"
												name="MonthYearId" id="MonthYearId"> <span
												class="input-group-btn">
												<button class="btn default" type="button" id="fmId">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</div> -->
									
									<strong>Select Month Year : </strong>
								    <div class="input-group ">
								
									<input type="text" class="form-control from"  id="MonthYearId"
										name="MonthYearId" placeholder="Select Month Year" style="cursor: pointer"> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								  </div>	
								</div>
								

								<div class="col-md-3">
									<!-- <div class="form-group">
		 <label>Location Type:-<font color="red">*</font></label>
		 <select class="form-control select2me input-large" id="locationtypeId" name="locationtypeId">
		 <option value=""></option>
		 <option value="Feeder">Feeder</option>
		 <option value="DT">DT</option>
		 </select>
		 </div> -->
									<div id="locationId" class="form-group">
										<strong>Location Type:<font color="red">*</font></strong> <select
											class="form-control select2me" id="locationtypeIdArea"
											name="locationtypeIdArea">
											<option value="">Select</option>
											<option value="Boundary">BOUNDARY</option>
											<option value="Feeder">FEEDER</option>
											<option value="DT">DT</option>

										</select>
									</div>
								</div>

								<div class="col-md-3">
									<!-- <div class="form-group">
		 <label>Consumption Availablity:-<font color="red">*</font></label>
		 <select class="form-control select2me input-large" id="ConsumavailId" name="ConsumavailId">
		  <option value=""></option>
		  <option value="ALL">ALL</option>
		 <option value="Available">Available</option>
		 <option value="NotAvailable">Not Available</option>
		 </select>
		 </div> -->
									<div id="divisionTd" class="form-group" style="left: 17px;right: -12px;margin-right: 25px;">
										<strong>Consumption Availablity:-<font color="red">*</font></strong>>
										<select class="form-control select2me" id="ConsumavailId"
											name="ConsumavailId">
											<option value="">select</option>
											<!-- <option value="ALL">ALL</option> -->
											<option value="Available">Available</option>
											<option value="NotAvailable">Not Available</option>

										</select>
									</div>
								</div>
							</div>
							<div class="row" >
								<div class="col-md-1">
								<div class="form-group">
									<a href="#" type="button" id="viewAreaId"
										 name="viewAreaId"
										class="btn green" onclick="viewArea()">
										<b>View</b>
									</a>
								</div></div>
								<!-- div class="col-md-4">
					<a href="#" id="excelExport" class="btn green pull-right"
						type="button"
						onclick="tableToExcel('sample_4', 'Area of Feeder/DT Monthly Consumption')"
						style="position: relative; left: 80%">Export to Excel</a>
				</div>     -->
				  
									
									<div class="col-md-4" >
									<div class="form-group">
										<a href="#"  class="btn green"
											type="button"
											onclick="ExporttoExcelAreawise()" hidden="true"
											>Export to Excel</a>
									</div></div>
								</div>

							</div>
							<div class="tab-pane fade" id="tab_1_3" hidden="true">
                         <%--  <jsp:include page="locationFilter.jsp"/>  --%>
                          <div class="row" style="margin-left: -1px;">
										 <c:if test="${officeType eq 'circle'}">
											<div class="row" style="margin-left: -1px;">


												<div class="col-md-3" id="regid">
													<strong>Region:</strong>
													<div id="LFzoneTd" class="form-group">
														<select class="form-control select2me" id="LFzone"
															name="LFzone" disabled="disabled"
															onchange="showCircle(this.value);">
															<option value="${newRegionName}">${newRegionName}</option>

														</select>
													</div>
												</div>

												<div class="col-md-3" id="circid">
													<strong>Circle:</strong>
													<div id="LFcircleTd" class="form-group">
														<select class="form-control select2me" id="LFcircle"
															name="LFcircle" disabled="disabled">
															<option value="${officeName}">${officeName}</option>

														</select>
													</div>
												</div>

												<div class="col-md-3">
													<strong>Town:</strong><select
														class="form-control select2me input-medium"
														name="townForFdr" id="townForFdr"
														onchange="showResultsbasedOntownCode(this.value);">
														<option value="">Select Town</option>
														<c:forEach items="${townList}" var="elements">
															<option value=${elements[0]}>${elements[0]}-${elements[1]}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</c:if> 
										<c:if test="${officeType eq 'corporate'}">
										<div class="col-md-3" id="regid">
											<strong>Region:</strong>
											<div id="zoneForFdr1" class="form-group">
												<select class="form-control select2me input-medium" id="zoneForFdr"
													name="zoneForFdr" onchange="showCircleforFeeder(this.value);">
													<option value="">Select Region</option>
													<option value="%">ALL</option>
													<c:forEach items="${allRegions}" var="elements">
														<option value="${elements}">${elements}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										
										<div class="col-md-3" >
											<b>Circle:</b> <select
												class="form-control select2me input-medium" name="circleForFdr"
												id="circleForFdr" onchange="showTownNameandCodeforFeeder(this.value);" >
												<option value="">Select Circle</option>
												<option value="%">ALL</option>

											</select>
										</div>
										
										<div class="col-md-3">
											<b>Town:</b> <select
												class="form-control select2me input-medium" name="townForFdr"
												id="townForFdr">
												<option value="">Select Town</option>

											</select>
										</div>
										</c:if>
										<c:if test="${officeType eq 'region'}">
											<div class="row" style="margin-left: -1px;">


												<div class="col-md-3" id="regid">
													<strong>Region:</strong>
													<div id="zoneForFdr2" class="form-group">
														<select class="form-control select2me input-medium"
															id="zoneForFdrbyreg" name="zoneForFdrbyreg"
															onchange="showCircleforFeederbyRegion(this.value);" disabled="disabled">
															<option value="${newRegionName}">${newRegionName}</option>
														</select>
													</div>
												</div>


												<div class="col-md-3" id="circid2">
													<strong>Circle:</strong>
													<div id="circleTd7" class="form-group">
														<select class="form-control select2me" id="circleForFdrbyReg"
															name="circleForFdrbyReg"
															onchange="showTownNameandCodeforFeederByRegion(this.value);">
															<option value="">Select Circle</option>
															<option value="%">ALL</option>
															<c:forEach items="${circleList}" var="elements">
																<option value="${elements}">${elements}</option>
															</c:forEach>
														</select>
													</div>
												</div>

												<div class="col-md-3">
													<strong>Town:</strong><select
														class="form-control select2me input-medium"
														name="townForFdrbyReg" id="townForFdrbyReg"
														onchange="showResultsbasedOntownCode(this.value);">
														<option value="">Select Town</option>
														<c:forEach items="${townList}" var="elements">
															<option value=${elements[0]}>${elements[0]}-${elements[1]}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</c:if>

									</div>
                          
							<div class="row" >
								<div class="col-md-3" style="margin-right: 8px;padding-right: 4px;padding-left: 30px;">
									
									<strong>Select Month Year : </strong>
								    <div class="input-group ">
								
									<input type="text" class="form-control from"  id="MonthYearIdReport"
										name="MonthYearIdReport" placeholder="Select Month Year" style="cursor: pointer"> <span
										class="input-group-btn">
										<button class="btn default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								  </div>	
								</div>
								

								<div class="col-md-3">
									<div id="locationId" class="form-group">
										<strong>Location Type:<font color="red">*</font></strong> <select
											class="form-control select2me" id="locationtypeIdAreaReport"
											name="locationtypeIdAreaReport">
											<option value="">Select</option>
											<option value="BOUNDARY METER">BOUNDARY</option>
											<option value="FEEDER METER">FEEDER</option>
											<option value="DT">DT</option>

										</select>
									</div>
								</div>


							</div>
							<div class="row" >
								<div class="col-md-1">
								<div class="form-group">
									<a href="#" type="button" id="viewAreaId"
										 name="viewAreaId"
										class="btn green" onclick="report()">
										<b>View</b>
									</a>
								</div></div>
	
								</div>
								<div class="table-toolbar">
					<div class="btn-group pull-right" style="margin-top: 35px;">
							<button class="btn dropdown-toggle" data-toggle="dropdown" id="tools">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="excelExport"
									onclick="tableToExcel('sample_5', 'Total Meters')">Export
										to Excel</a></li>
							</ul>
						</div>
					</div>

							</div>
							<br>
							<table class="table table-striped table-hover table-bordered"
								id="sample_3" hidden="true">
								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<th id="thsubCodeId">SubStation Code</th>
										<th id="thfdrId">Feeder Code</th>
										<th id="thfdrCodeId">Feeder Code</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>MF</th>
										<th>Monthly Consumption(KWH) With MF</th>
										<th>Kwh_Exp</th>
										<th id="statusid">Status</th>
										<th>Edit</th>
									
										<%-- <td><a th:href="@{/showFormUpdate/{id}(id=${employee.id})}" class="btn btn-primary">Update</a> --%>
									</tr>
								</thead>
								<tbody id="AreafeederLocationdata">

								</tbody>
							</table>
							
						<!-- 	<br>
							
							
							<table class="table table-striped table-hover table-bordered"
								id="sample_98" hidden="true">
								
								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<th id="thsubCodeId">SubStation Code</th>
										<th id="thfdrId">Feeder id</th>
										<th id="thfdrCodeId">Feeder Code</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>Adjustment Value Imp</th>
										<th>Adjustment Value Exp</th>
										<th>Edit</th>	
									</tr>
								
								
								</thead>			
								<tbody id="tabletenniss">
								
								</tbody>			
						</table> -->
							
							<table class="table table-striped table-hover table-bordered"
								id="sample_4" hidden="true">
								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<!--   <th>Subdivision Code</th> -->
										<!--  <th>SubStation Name</th> -->
										<th>Feeder Code</th>
										<th>DT Code</th>
										<th>DT Code</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>MF</th>
										<th>Monthly Consumption(KWH) With MF</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody id="AreadtLocationdata">

								</tbody>
							</table>
							<table class="table table-striped table-hover table-bordered"
								id="sample_5" hidden="true">
								<thead>
									<tr>
										<th>Month Year</th>
										<th>Subdivision Name</th>
										<th id="thsubCodeIdReport">SubStation Code</th>
										<th id="thfdrIdReport">Feeder id</th>
										<th id="thfdrCodeIdReport">Feeder Code</th>
										<th>Meter SrNo</th>
										<th>Meter Make</th>
										<th>MF</th>
										<th>Old Consumption(KWH) With MF</th>
										<th>Edited Consumption(KWH) With MF</th>
										<th id="statusidReport">Status</th>
										<th>Updated By</th>
										<th>Updated Date</th>
									</tr>
								</thead>
								<tbody id="AreafeederLocationdataReport">

								</tbody>
							</table>
						</div>
						<!-- 	tab_1_2	   -->
					</div>
					<!-- tab-content -->
				</div>
			</div>
		</div>
	</div>
</div>


<div id="stack1" class="modal fade" role="dialog"
	aria-labelledby="myModalLabel10">
	<div class="modal-dialog" style="width: 50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick=""></button>
				<h5 class="modal-title" id="">
					<b>Enter/Update Consumption</b>
				</h5>
			</div>
			<div class="modal-body">
				<form action="" method="post" id="UpdateConsumption"
					modelAttribute="UpdateConsumption" commandName="UpdateConsumption">

					<div class="inline-labels">
						<div class="form-group">
							<label>Consumption(kWh) With MF: <input type="hidden"
								id="oldConsumptionId" name="oldConsumptionId" /> <input
								type="hidden" id="monthlyConsId" name="monthlyConsId" /> <input
								type="hidden" id="mtrnoId" name="meternoId" /> <input
								type="hidden" id="locodeId" name="meternoId" /> <input
								type="text" class="form-control placeholder-no-fix"
								id="ConsumptionId" name="ConsumptionId"
								placeholder="Enter Consumption"></input></label>	
									
						</div>
								
					<div class="form-group">
						<label for="comment">Edit Remark:-</label>
						<textarea class="form-control" rows="5" id="EdittextId"
							name="EdittextId"></textarea>
						<input type="text" id="EditRemarkId" hidden="true">
					</div>
					<div class="form-group" hidden="true">
							<label for="comment"></label>
							<textarea class="form-control" rows="5" id="" name=""></textarea>
							<input type="text" id="MeterNo" hidden="true">
					</div>

				</div>



					<div class="modal-footer">
						<button class="btn blue pull-right" id="Updatedtbt" type="button"
							value="update" onclick="return update(this.value)"
							data-dismiss="modal" aria-hidden="true">UPDATE</button>
						<button class="btn red pull-left" type="button"
							data-dismiss="modal">Cancel</button>
					</div>

				</form>
			</div>
			
			
		</div>
	</div>
</div>
<script>
	function clearTabledataContent(tableid) {
		//TO CLEAR THE TABLE DATA
		var oSettings = $('#' + tableid).dataTable().fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		for (i = 0; i <= iTotalRecords; i++) {
			$('#' + tableid).dataTable().fnDeleteRow(0, null, true);
		}

	}
	/*  function locationWise(param)
	{
	  
	  if(param=='location')
		  {
		  $('#tab_1_1').show();
		  $('#tab_1_2').hide();
		  
		  }
	  if(param=='area')
	  {
	  $('#tab_1_1').hide();
	  $('#tab_1_2').show();
	  
	  } 
	  
	}   */
	
	
	function resetFields(){
		document.getElementById('LocIdentityId').value = '';
		document.getElementById('meternoId').value = '';
	}
</script>

<script>
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();

	$('.from').datepicker
	({
	    format: "yyyymm",
	    minViewMode: 1,
	    autoclose: true,
	    startDate :new Date(new Date().getFullYear()),
	    endDate: new Date(year, month-1, '31')
	});

</script>

<script>

function ExportToExcelLoc()
{
	var LocationType = $("#LocationTypeId").val();
	var LocIdentity = $("#LocIdentityId").val();
	var meterno = $("#meternoId").val();

	window.location.href=("./LocwiseFdrDtMonthlyConsum?LocationType="+LocationType+"&LocIdentity="+LocIdentity+"&meterno="+meterno);
}

function ExporttoExcelAreawise()
{
	var circle = $("#LFcircle").val();
	var monthyr = $("#MonthYearId").val();
	var locationtype = $("#locationtypeIdArea").val();
	var Consumavail = $("#ConsumavailId").val();
	var town_code = $("#LFtown").val();
	var zne="",cir="",townn="";
	
	if(circle=="%"){
		cir="ALL";
	}else{
	    cir=circle;
	} 
	 if(town_code=="%"){
		townn="ALL";
	}else{
		townn=town_code;
	} 
	//alert(townn)

	window.location.href=("./AreawiseFdrDtMonthlyConsum?cir="+cir+"&monthyr="+monthyr+"&locationtype="+locationtype+"&Consumavail="+Consumavail+"&townn="+townn);
}
	
</script>